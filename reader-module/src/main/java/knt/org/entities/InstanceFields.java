package knt.org.entities;

import lombok.Data;
import lombok.ToString;

@Data
public class InstanceFields {

    @ToString.Exclude
    ClassInstance fatherInstance;
    String type;
    String name;

}
