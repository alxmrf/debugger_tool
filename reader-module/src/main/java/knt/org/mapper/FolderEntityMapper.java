package knt.org.mapper;

import knt.org.entities.FolderEntity;

import java.nio.file.Path;
import java.util.ArrayList;

public class FolderEntityMapper {
    public static FolderEntity of(Path folderPath, FolderEntity fatherFolder){
        var folderEntity =  new FolderEntity();
        folderEntity.setFolderPath(folderPath);
        folderEntity.setProgramsList(new ArrayList<>());
        folderEntity.setSubFolderEntityList(new ArrayList<>());
        folderEntity.setFatherFolder(fatherFolder);
        return folderEntity;
    }

    //method only intended to be used to create the root folder entity,always fill the referal to the father folder
    // in the entities
    public static FolderEntity of(Path folderPath){
        var folderEntity =  new FolderEntity();
        folderEntity.setFolderPath(folderPath);
        folderEntity.setFatherFolder(null);
        folderEntity.setProgramsList(new ArrayList<>());
        folderEntity.setSubFolderEntityList(new ArrayList<>());
        return folderEntity;
    }
}
