package knt.org;


import java.io.IOException;
import java.nio.file.Path;

public class App
{

    public static void main( String[] args ) throws IOException {
        var basePath  = Path.of(args[0]);
        var folderEntity = PathReaderOrchestrator.scanBaseDirectory(basePath);
        var javaFiles = PathReaderOrchestrator.findJavaFiles(folderEntity);
        System.out.println(javaFiles);
    }
}
