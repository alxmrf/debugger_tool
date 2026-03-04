package knt.org;


import knt.org.entities.FileEntity;
import knt.org.entities.FolderEntity;
import knt.org.mapper.FolderEntityMapper;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;

public class PathReaderOrchestrator {



    public static ArrayList<FileEntity> findJavaFiles(FolderEntity folder){

        var javaFiles = new ArrayList<FileEntity>(folder.getProgramsList().stream().map(PathReader::scanFile)
                .filter(file -> file.getFileExtension().equals("java")).toList());

        for( var subFolder : folder.getSubFolderEntityList()){
            javaFiles.addAll(findJavaFiles(subFolder));
        }
        return javaFiles;
    }

    public static FolderEntity scanBaseDirectory(Path basePath) throws IOException {
        var baseFolder = FolderEntityMapper.of(basePath);

        PathReader.scanFolder(baseFolder);

        if(!baseFolder.getSubFolderEntityList().isEmpty()){
            scanSubFolders(baseFolder);
        }
        return  baseFolder;

    }



    public static void  scanSubFolders(FolderEntity folder) throws IOException {

        if(folder.getSubFolderEntityList().isEmpty()){
            return;
        }

        for( var subFolder : folder.getSubFolderEntityList()){
            PathReader.scanFolder(subFolder);
            scanSubFolders(subFolder);
        }


    }


}
