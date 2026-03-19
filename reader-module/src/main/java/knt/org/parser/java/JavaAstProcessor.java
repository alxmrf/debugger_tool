package knt.org.parser.java;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseException;
import com.github.javaparser.ParserConfiguration;
import com.github.javaparser.symbolsolver.JavaSymbolSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.CombinedTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.JavaParserTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.ReflectionTypeSolver;
import knt.org.entities.FileEntity;
import knt.org.entities.JavaFile;
import knt.org.parser.java.visitor.JavaFileVisitor;

import java.io.File;
import java.io.IOException;

public class JavaAstProcessor {

    JavaParser parser ;

    public void setParser(        String sourceDir){

            CombinedTypeSolver combinedSolver = new CombinedTypeSolver();

            // 1. Resolve classes do JDK base (String, List, etc)
            combinedSolver.add(new ReflectionTypeSolver());

            // 2. Resolve as classes do próprio projeto
            combinedSolver.add(new JavaParserTypeSolver(new File(sourceDir)));

            var symbolSolver = new JavaSymbolSolver(combinedSolver);

            var parserConfig = new ParserConfiguration();
            parserConfig.setSymbolResolver(symbolSolver);

            this.parser = new JavaParser(parserConfig);
    }

    public JavaAstProcessor(String basePath) {
         setParser(basePath);
    }
    //TODO: finish logic to create the java file Entity instance
    public  JavaFile buildFileAst(FileEntity code) throws IOException, ParseException {
        var javaFileBuilderVisitor = new JavaFileVisitor();
        var ast  = parser.parse(code.getFilePath()).getResult().orElseThrow(ParseException::new);

        ast.accept(javaFileBuilderVisitor, null);

        return javaFileBuilderVisitor.getJavaFileInstance();
    }




}
