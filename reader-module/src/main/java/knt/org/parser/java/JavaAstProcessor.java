package knt.org.parser.java;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseException;
import knt.org.entities.FileEntity;
import knt.org.entities.JavaFile;

import java.io.IOException;

public class JavaAstProcessor {

    JavaParser parser ;

    public JavaAstProcessor() {
         parser = new JavaParser();
    }

    public  JavaFile buildFileAst(FileEntity code) throws IOException, ParseException {

        var ast  = parser.parse(code.getFilePath()).getResult().orElseThrow(ParseException::new);

        ast.accept()


    }




}
