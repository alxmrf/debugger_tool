package knt.org.entities;

import lombok.Data;

import java.util.List;
@Data

public class ImportInstance {
    JavaFile fatherInstance;
    String packageName;
    List<Number> lineLocation;
}
