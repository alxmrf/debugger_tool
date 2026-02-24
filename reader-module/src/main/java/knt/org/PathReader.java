package knt.org;

import knt.org.entities.FileEntity;
import knt.org.entities.FolderEntity;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class PathReader {




    public static FolderEntity scanFolder(Path folderPath) throws IOException{
        var files = new ArrayList<FileEntity>();
        try (var pathStream  = Files.list(folderPath)){
            pathStream.
        }
    }




}