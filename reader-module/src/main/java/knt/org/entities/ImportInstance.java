package knt.org.entities;

import lombok.Data;
import lombok.ToString;

import java.util.List;
@Data

public class ImportInstance {
    @ToString.Exclude
    JavaFile fatherInstance;
    String packageName;
    List<Number> lineLocation;
}
