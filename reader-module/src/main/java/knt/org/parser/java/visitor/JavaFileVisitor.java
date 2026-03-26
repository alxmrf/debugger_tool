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
import com.sun.source.tree.*;
import com.sun.source.util.TreePathScanner;
import com.sun.source.util.Trees;
import knt.org.entities.*;
import lombok.Getter;

import javax.lang.model.type.TypeMirror;
import java.io.File;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;


//TODO: look into using future and thread safe operations for this
//TODO: fix the dependency solver later :)
public class JavaFileVisitor extends TreePathScanner<Void,Void> {



    Trees analyzedTrees;



    @Getter
    private JavaFile javaFileInstance;

    private MethodInstance currentMethod;

    private Deque<ClassInstance> classInstanceDeque;


    @Override
    public Void scan(Iterable<? extends Tree> nodes, Void unused) {
        return super.scan(nodes, unused);
    }

    public JavaFileVisitor(Trees analyzedTrees) {
        this.analyzedTrees = analyzedTrees;
    }

    @Override
    public Void visitImport(ImportTree node, Void unused) {
        var importInstance = new ImportInstance();
        importInstance.setFatherInstance(javaFileInstance);
        importInstance.setPackageName(node.getClass().getPackageName());
        javaFileInstance.getImports().add(importInstance);
        return super.visitImport(node, unused);
    }

    @Override
    public Void visitClass(ClassTree node, Void unused) {

        var classInstance  = new ClassInstance();
        classInstance.setClassName(node.getSimpleName().toString());
        classInstance.setFatherFile(javaFileInstance);
        classInstanceDeque.push(classInstance);
        javaFileInstance.getClassList().add(classInstance);
        super.visitClass(node, unused);
        classInstanceDeque.pop();
        return null;

    }

    @Override
    public Void visitMethod(MethodTree node, Void unused) {

        var methodInstance = new MethodInstance();
        this.currentMethod = methodInstance;
        methodInstance.setMethodName(node.getName().toString());


        var methodParameters =  node.getParameters();
        for (var parameter : methodParameters){
            var parameterEntity = new VariableInstance();
            parameterEntity.setType(parameter.getType().toString());
            parameterEntity.setName(parameter.getName().toString());
            methodInstance.getParameters().add(parameterEntity);
        }


        super.visitMethod(node, unused);
        assert this.classInstanceDeque.peek() != null;
        this.classInstanceDeque.peek().getMethods().add(methodInstance);
        this.currentMethod = null;

        return null;
    }

    @Override
    public Void visitMethodInvocation(MethodInvocationTree node, Void unused) {
        var path = getCurrentPath();
        var methodCall = new MethodCallEntity();

        var typeMirror =  this.analyzedTrees.getTypeMirror(path);
        methodCall.setParentClass(node.toString());
        methodCall.setMethodName(typeMirror.toString());
        if(this.currentMethod != null ) {
            this.currentMethod.getMethodCalls().add(methodCall);
        }
        else {
            this.classInstanceDeque.peek().getMethodCalls().add(methodCall);
        }

        return super.visitMethodInvocation(node, unused);
    }

    @Override
    public Void visitVariable(VariableTree node, Void unused) {

        var path =  getCurrentPath();
        var typeMirror =  this.analyzedTrees.getTypeMirror(path);
        var variableInstance =  new VariableInstance();
        variableInstance.setType(typeMirror.toString());
        variableInstance.setName(node.toString());
        if(this.currentMethod != null ) {

            this.currentMethod.getVariables().add(variableInstance);
        }
        else {
            this.classInstanceDeque.peek().getInstanceFields().add(variableInstance);
        }

        return super.visitVariable(node, unused);
    }




    public void prepareForNewScan() {
        this.javaFileInstance = new JavaFile();
        this.classInstanceDeque =  new ArrayDeque<>();

    }
}
