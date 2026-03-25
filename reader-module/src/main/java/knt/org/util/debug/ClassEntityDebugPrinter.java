package knt.org.util.debug;

import knt.org.entities.*;

public class ClassEntityDebugPrinter {

    private static final String INDENT = "    "; // 4 spaces per level

    public static String print(ClassInstance clazz) {
        StringBuilder sb = new StringBuilder();

        sb.append("Class: ").append(clazz.getClassName()).append("\n");

        if (clazz.getInstanceFields() != null && !clazz.getInstanceFields().isEmpty()) {
            sb.append(INDENT).append("Fields:\n");
            for (InstanceFields field : clazz.getInstanceFields()) {
                sb.append(INDENT).append(INDENT)
                        .append(field.getType()).append(" ").append(field.getName()).append("\n");
            }
        }

        if (clazz.getConstructor() != null) {
            sb.append(INDENT).append("Constructor:\n");
            // Assuming ConstructorInstance shares a similar structure, implement accordingly
            sb.append(INDENT).append(INDENT).append(clazz.getConstructor().toString()).append("\n");
        }

        if (clazz.getMethods() != null && !clazz.getMethods().isEmpty()) {
            sb.append(INDENT).append("Methods:\n");
            for (MethodInstance method : clazz.getMethods()) {
                printMethod(sb, method, 2);
            }
        }

        return sb.toString();
    }

    private static void printMethod(StringBuilder sb, MethodInstance method, int depth) {
        String baseIndent = INDENT.repeat(depth);
        String childIndent = INDENT.repeat(depth + 1);

        sb.append(baseIndent).append("Method: ").append(method.getMethodName()).append("\n");

        if (method.getParameters() != null && !method.getParameters().isEmpty()) {
            sb.append(childIndent).append("Parameters: ");
            method.getParameters().forEach(p -> sb.append(p.getType()).append(" ").append(p.getName()).append(", "));
            sb.setLength(sb.length() - 2); // Remove trailing comma
            sb.append("\n");
        }

        if (method.getVariables() != null && !method.getVariables().isEmpty()) {
            sb.append(childIndent).append("Local Variables:\n");
            for (VariableInstance var : method.getVariables()) {
                sb.append(childIndent).append(INDENT)
                        .append(var.getType()).append(" ").append(var.getName()).append("\n");
            }
        }

        if (method.getMethodCalls() != null && !method.getMethodCalls().isEmpty()) {
            sb.append(childIndent).append("Method Calls:\n");
            for (MethodCallEntity call : method.getMethodCalls()) {
                sb.append(childIndent).append(INDENT)
                        .append(call.getParentClass()).append(".").append(call.getMethodName()).append("()\n");
            }
        }
    }
}