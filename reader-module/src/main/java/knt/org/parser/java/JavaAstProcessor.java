package knt.org.parser.java;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseException;
import knt.org.entities.FileEntity;
import knt.org.entities.JavaFile;
import knt.org.parser.java.visitor.JavaFileVisitor;

import java.io.IOException;

public class JavaAstProcessor {

    JavaParser parser ;

    public JavaAstProcessor() {
         parser = new JavaParser();
    }
    //TODO: finish logic to create the java file Entity instance
    public  JavaFile buildFileAst(FileEntity code) throws IOException, ParseException {
        var javaFileBuilderVisitor = new JavaFileVisitor();
        var ast  = parser.parse(code.getFilePath()).getResult().orElseThrow(ParseException::new);

        ast.accept(javaFileBuilderVisitor, null);
        return null;
    }




}
