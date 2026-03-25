package knt.org.parser.java;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseException;
import com.github.javaparser.ParserConfiguration;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.symbolsolver.JavaSymbolSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.CombinedTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.JavaParserTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.ReflectionTypeSolver;
import com.sun.source.tree.CompilationUnitTree;
import com.sun.source.util.JavacTask;
import com.sun.source.util.TreePathScanner;
import com.sun.source.util.Trees;
import knt.org.entities.FileEntity;
import knt.org.entities.JavaFile;
import knt.org.parser.java.visitor.JavaFileVisitor;

import javax.tools.ToolProvider;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class JavaAstProcessor {

    JavaParser parser ;
    //TODO: the java Parser i was using is not enough for the application , so i will switch it out with the javac compiler`s
    // parsing to be 100% precise`
    JavacTask compilerTask;

    JavaFileVisitor visitor;

    Iterable<? extends  CompilationUnitTree> compilationUnits;

    //TODO: the first implementation will not allow for external dependencies, this must be added latter
    JavaAstProcessor (List<FileEntity> codes) throws IOException {
        var compiler  = ToolProvider.getSystemJavaCompiler();
        if (compiler == null) {
            throw new IllegalStateException("System JavaCompiler is not available. Ensure you are running on a JDK, not a JRE.");
        }

        var javaFileManager =  compiler.getStandardFileManager(null,null,null);
        var javaFiles = codes.stream().map(entity-> {
            return entity.getFilePath().toFile();
        }).toList();
        var compilationUnits = javaFileManager.getJavaFileObjectsFromFiles(javaFiles);

        var rawJavaTask = (JavacTask) compiler.getTask(
                null,
                javaFileManager,
                null,
                List.of("-proc:none"),
                null,
                compilationUnits
        );
        this.compilationUnits = rawJavaTask.parse();
        rawJavaTask.analyze();
        this.compilerTask = rawJavaTask;
    }


    public List<JavaFile> process() throws IOException {
        var trees =  Trees.instance( compilerTask);
        this.visitor = new JavaFileVisitor(trees);
        var listFiles =  new ArrayList<JavaFile>();
        for(var compilationUnit : this.compilationUnits) {
            listFiles.add(this.scan(compilationUnit));
        }
        System.out.println(listFiles + "\n\n\n\n");
        return null;
    }


    public JavaFile scan(CompilationUnitTree compilationUnit){
        visitor.prepareForNewScan();
        visitor.scan(compilationUnit,null);
        return visitor.getJavaFileInstance();
    }




}
