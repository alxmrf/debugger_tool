package knt.org.entities;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class JavaFile {


    FileEntity file;
    List<ClassInstance> classList;
    List<ImportInstance> imports;




}
