package knt.org.entities;

import lombok.Data;
import lombok.ToString;

@Data
public class VariableInstance {
    @ToString.Exclude
    MethodInstance fatherInstance;
    String type;
    String name;
}
