package knt.org.parser.java;

import com.github.javaparser.ParseException;
import com.sun.source.util.JavacTask;
import com.sun.source.util.Trees;
import knt.org.entities.FileEntity;
import knt.org.entities.FolderEntity;
import knt.org.entities.JavaFile;

import javax.tools.DiagnosticListener;
import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JavaParserOrchestrator {



    public static void parseJavaFiles(List<FileEntity> javaFiles, FolderEntity baseFile) throws IOException, ParseException {
        var astProcessor =  new JavaAstProcessor(javaFiles);
        var parsedJavaFiles =  astProcessor.process();

    }





}
