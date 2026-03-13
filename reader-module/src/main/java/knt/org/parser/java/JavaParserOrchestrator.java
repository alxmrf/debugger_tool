package knt.org.parser.java;

import com.github.javaparser.ParseException;
import knt.org.entities.FileEntity;
import knt.org.entities.FolderEntity;
import knt.org.entities.JavaFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JavaParserOrchestrator {




    public static void parseJavaFiles(List<FileEntity> javaFiles, FolderEntity baseFile) throws IOException, ParseException {
        var astProcessor =  new JavaAstProcessor();
        var parsedJavaFiles =  new ArrayList<JavaFile>();

        for(var file : javaFiles){
            parsedJavaFiles.add( astProcessor.buildFileAst(file,baseFile.getFolderPath().toString()));
        }

        for(var file2 :  parsedJavaFiles) {
            System.out.println("\n");
            System.out.println(file2);
        }


    }





}
