package knt.org.entities;

import lombok.Getter;
import lombok.Setter;

import java.nio.file.Path;
@Getter
@Setter
public class FileEntity {
    Path filePath;
    String fileName;
    String fileExtension;
    String metadata;
}
