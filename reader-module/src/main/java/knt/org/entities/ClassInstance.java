package knt.org.entities;

import lombok.Data;
import lombok.ToString;
import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
@Data

public class ClassInstance {

    String className;
    @ToString.Exclude
    JavaFile fatherFile;
    List<MethodInstance> methods;
    List<InstanceFields> instanceFields;

    ConstructorInstance constructor;

    public ClassInstance() {


        this.methods = new ArrayList<>();
        this.instanceFields = new ArrayList<>();

    }
}
