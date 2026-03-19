package knt.org.parser.java.visitor;


import com.github.javaparser.JavaParser;
import com.github.javaparser.ParserConfiguration;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.body.*;
import com.github.javaparser.ast.expr.*;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.resolution.UnsolvedSymbolException;
import com.github.javaparser.symbolsolver.JavaSymbolSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.CombinedTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.JavaParserTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.ReflectionTypeSolver;
import knt.org.entities.*;
import lombok.Getter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;


//TODO: look into using future and thread safe operations for this
//TODO: fix the dependency solver later :)
public class JavaFileVisitor extends VoidVisitorAdapter<Void> {



    @Getter
    private final JavaFile javaFileInstance;

    private MethodInstance currentMethod;

    private final Deque<ClassInstance> classInstanceDeque;

    public JavaFileVisitor(){
        this.javaFileInstance = new JavaFile();
        this.classInstanceDeque =  new ArrayDeque<>();
    }



    @Override
    public void visit(ClassOrInterfaceDeclaration n, Void arg) {

        var classInstance  = new ClassInstance();
        classInstance.setClassName(n.getNameAsString());
        classInstance.setFatherFile(javaFileInstance);
        classInstanceDeque.push(classInstance);
        javaFileInstance.getClassList().add(classInstance);
        super.visit(n, arg);
        classInstanceDeque.pop();
    }


    @Override
    public void visit(ImportDeclaration n, Void arg) {
        var importInstance = new ImportInstance();
        importInstance.setFatherInstance(javaFileInstance);
        importInstance.setPackageName(n.getNameAsString());
        javaFileInstance.getImports().add(importInstance);
        super.visit(n, arg);
    }


    @Override
    public void visit(ConstructorDeclaration n, Void arg) {
        var methodInstance = new MethodInstance();
        this.currentMethod = methodInstance;
        methodInstance.setMethodName(n.getNameAsString());
        super.visit(n, arg);
        this.currentMethod = null;
    }

    //TODO: implement method call visitor code
    @Override
    public void visit(MethodCallExpr n, Void arg) {
        var methodCall = new MethodCallEntity();

        methodCall.setMethodName(n.getNameAsString());

        try{
            var resolvedMethod = n.resolve();
            methodCall.setParentClass(resolvedMethod.getClassName());

        }catch (UnsolvedSymbolException e) {
            // The TypeSolver could not find the type in the configured source paths
            methodCall.setParentClass("UNRESOLVED");
        }
        if(this.currentMethod != null ) {
            this.currentMethod.getMethodCalls().add(methodCall);
        }
        else {

            this.classInstanceDeque.peek().getMethodCalls().add(methodCall);
        }
        super.visit(n, arg);
    }

    @Override
    public void visit(MethodDeclaration n, Void arg) {
        var methodInstance = new MethodInstance();
        this.currentMethod = methodInstance;
        methodInstance.setMethodName(n.getNameAsString());
        super.visit(n, arg);
        assert this.classInstanceDeque.peek() != null;
        this.classInstanceDeque.peek().getMethods().add(methodInstance);
        this.currentMethod = null;
    }

    @Override
    //TODO: add the ability to decypher lib variables
    public void visit(VariableDeclarationExpr n, Void arg) {
        var variableInstanceList =  new ArrayList<>(n.getVariables().stream().map( variableDeclarator -> {
            var variableInstance = new VariableInstance();
            variableInstance.setName(variableDeclarator.getNameAsString());
            var type  = variableDeclarator.getType().asString() ;
            if(type.equals("var")){
                try {
                    type = n.calculateResolvedType().toString();
                }
                catch (UnsolvedSymbolException e) {
                    // The TypeSolver could not find the type in the configured source paths
                    type ="LIB TYPE";
                }
            }
            variableInstance.setType(type);
            variableInstance.setFatherInstance(this.currentMethod);
            return variableInstance;
        }).toList());

        if(this.currentMethod !=null){
             currentMethod.getVariables().addAll( variableInstanceList);
        }
        super.visit(n, arg);
    }

    @Override
    public void visit(Parameter n, Void arg) {

        var variableInstance =  new VariableInstance();

        variableInstance.setName(n.getNameAsString());
        variableInstance.setType(n.getTypeAsString());
        variableInstance.setFatherInstance(this.currentMethod);
        if(this.currentMethod !=null){
            currentMethod.getParameters().add(variableInstance);
        }
        super.visit(n, arg);
    }

    @Override
    public void visit(FieldDeclaration n, Void arg) {

        var fatherClass = this.classInstanceDeque.peek();

        if (fatherClass == null){
            throw  new IllegalStateException("Filed instance declared outside class");
        }
        var variableInstanceList =  new ArrayList<>(n.getVariables().stream().map( variableDeclarator -> {
            var variableInstance = new InstanceFields();
            variableInstance.setName(variableDeclarator.getNameAsString());
            variableInstance.setType(variableDeclarator.getType().asString());
            variableInstance.setFatherInstance(fatherClass);
            return variableInstance;
        }).toList());

        fatherClass.getInstanceFields().addAll(variableInstanceList);
        super.visit(n, arg);
    }
}
