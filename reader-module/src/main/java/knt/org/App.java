package knt.org;


import com.github.javaparser.ParseException;
import knt.org.parser.java.JavaParserOrchestrator;

import java.io.IOException;
import java.nio.file.Path;

public class App
{
    //TODO: Create a java file parser orchestrator
    //TODO: Create a variable type resolver
    public static void main( String[] args ) throws IOException, ParseException {
        var basePath  = Path.of(args[0]);
        var baseFolder = PathReaderOrchestrator.scanBaseDirectory(basePath);
        var javaFiles = PathReaderOrchestrator.findJavaFiles(baseFolder);
        var javaFileContents =PathReaderOrchestrator.readFiles(javaFiles);
        JavaParserOrchestrator.parseJavaFiles(javaFiles, baseFolder);
    }
}
