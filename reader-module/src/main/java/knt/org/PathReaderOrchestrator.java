package knt.org;



import knt.org.entities.FolderEntity;

import java.io.IOException;
import java.nio.file.Path;

public class PathReaderOrchestrator {


    public void scanPathUntilLastNode(Path basePath) throws IOException {

        var folder = PathReader.scanFolder(basePath);

        if(!folder.getSubFolderEntityList().isEmpty()){
            for (var subFolder :folder.getSubFolderEntityList() ){
                scanPathUntilLastNode(subFolder.getFolderPath());
            }
        }

    }

    public void iterateTroughFolders(FolderEntity folder){
        var folderPath = folder.getFolderPath();



    }



}
