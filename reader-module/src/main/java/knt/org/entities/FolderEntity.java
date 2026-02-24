package knt.org.entities;

import lombok.Getter;
import lombok.Setter;

import java.nio.file.Path;
import java.util.List;
@Getter
@Setter
public class FolderEntity{
    Path folderPath;
    String folderName;
    List<FolderEntity> subFolderEntityList;
    List<FileEntity> programsList;
}
