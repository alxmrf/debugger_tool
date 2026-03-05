package knt.org;

import knt.org.entities.FileEntity;
import knt.org.entities.FolderEntity;
import knt.org.mapper.FileEntityMapper;
import knt.org.mapper.FolderEntityMapper;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.nio.file.Files;

//TODO: break this into 2 classes FolderReader and FileReader
public class PathReader {

    public static String readFile(FileEntity file){

        try{
            return Files.readString( file.getFilePath());
        }catch (IOException io){
            throw  new RuntimeException("unnable to read file " + file.getFileName()+file.getFileExtension());
        }

    }


    //TODO: look into raising a warning when no name and no extension
    public static FileEntity scanFilePath(FileEntity entity){
        var fileName = StringUtils.split( entity.getFilePath().toString() , ".") ;
        if(fileName.length == 2){
            entity.setFileName(fileName[0]);
            entity.setFileExtension(fileName[1]);

        }
        else if(fileName.length == 1){
            entity.setFileName(fileName[0]);
            entity.setFileExtension("");
        }
        else {
            entity.setFileName("");
            entity.setFileExtension("");
        }

        return entity;
    }

    public static void scanFolder(FolderEntity folderEntity) throws IOException{

        try (var pathStream  = Files.list(folderEntity.getFolderPath())){
            pathStream.forEach((path -> {
                if(Files.isDirectory(path)){
                    folderEntity.getSubFolderEntityList().add(FolderEntityMapper.of(path, folderEntity));
                }
                if(Files.isReadable(path)){
                    folderEntity.getProgramsList().add(FileEntityMapper.of(path,folderEntity));
                }
                System.out.println(path);
            }));
        }
    }




}