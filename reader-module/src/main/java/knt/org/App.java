package knt.org;


import knt.org.parser.java.JavaAstProcessor;

import java.io.IOException;
import java.nio.file.Path;

public class App
{
    //TODO: Create a java file parser orchestrator
    //TODO: Create a variable type resolver
    public static void main( String[] args ) throws IOException {
        var basePath  = Path.of(args[0]);
        var folderEntity = PathReaderOrchestrator.scanBaseDirectory(basePath);
        var javaFiles = PathReaderOrchestrator.findJavaFiles(folderEntity);
        var javaFileContents =PathReaderOrchestrator.readFiles(javaFiles);
        var asts = processor.buildFileAst(javaFiles.getFirst());
    }
}
