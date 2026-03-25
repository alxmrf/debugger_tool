package knt.org.entities;

import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
@Data
public class MethodInstance {
    @ToString.Exclude
    ClassInstance fatherInstance;
    String methodName;
    List<VariableInstance> parameters;
    List<VariableInstance> variables;
    List<MethodCallEntity> methodCalls;
    List<Integer> lineLocation;

    public MethodInstance(){
        this.parameters =  new ArrayList<>();
        this.variables  = new ArrayList<>();
        this.methodCalls = new ArrayList<>();
    }

}

