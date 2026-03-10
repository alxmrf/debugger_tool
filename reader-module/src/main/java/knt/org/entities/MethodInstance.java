package knt.org.entities;

import lombok.Data;

import java.util.List;
@Data
public class MethodInstance {

    JavaFile fatherInstance;
    String methodName;
    List<VariableInstance> parameters;
    List<VariableInstance> methodVariables;
    List<Integer> lineLocation;

}

