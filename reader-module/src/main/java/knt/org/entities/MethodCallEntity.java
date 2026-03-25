package knt.org.entities;

import lombok.Data;

@Data
public class MethodCallEntity {
    String parentClass;
    String methodName;
}
