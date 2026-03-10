package knt.org.entities;

import lombok.Data;

@Data
public class VariableInstance {

    JavaFile fatherInstance;
    String type;
    String name;
}
