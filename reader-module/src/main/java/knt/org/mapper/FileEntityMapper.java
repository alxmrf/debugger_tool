package knt.org.mapper;

import knt.org.PathReader;
import knt.org.entities.FileEntity;
import knt.org.entities.FolderEntity;

import java.nio.file.Path;

public class FileEntityMapper {

    public static FileEntity of(Path filePath, FolderEntity fatherFolder){
        var fileEntity =  new FileEntity();
        fileEntity.setFilePath(filePath);
        fileEntity.setFatherFolder(fatherFolder);
        PathReader.scanFile(fileEntity);
        return fileEntity;
    }

}
