package knt.org.entities;

import knt.org.util.debug.ClassEntityDebugPrinter;
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
    List<MethodCallEntity> methodCalls;

    ConstructorInstance constructor;

    public ClassInstance() {


        this.methods = new ArrayList<>();
        this.instanceFields = new ArrayList<>();
        this.methodCalls = new ArrayList<>();

    }


    @Override
    public String toString(){
        return ClassEntityDebugPrinter.print(this);
    }
}
