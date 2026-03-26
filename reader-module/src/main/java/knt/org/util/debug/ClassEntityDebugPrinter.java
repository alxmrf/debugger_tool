package knt.org.util.debug;

import knt.org.entities.*;

public class ClassEntityDebugPrinter {

    private static final String INDENT = "    "; // 4 spaces per level

    public static String print(JavaFile file) {
        StringBuilder sb = new StringBuilder();
        String baseIndent = INDENT.repeat(1);
        String childIndent = INDENT.repeat(1 + 1);
        sb.append("\n\n\n\n");
        if (file.getImports() != null && !file.getImports().isEmpty()) {
            sb.append("Imports:\n");
            for (var import_ : file.getImports()) {
                sb.append(childIndent).append("  ")
                        .append(import_.getPackageName()).append("\n");
            }
        }
        for(var clazz:  file.getClassList() ) {

            sb.append("Class: ").append(clazz.getClassName()).append("\n");

            if (clazz.getInstanceFields() != null && !clazz.getInstanceFields().isEmpty()) {
                sb.append(INDENT).append("Fields:\n");
                for (VariableInstance field : clazz.getInstanceFields()) {
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
            if (clazz.getMethodCalls() != null && !clazz.getMethodCalls().isEmpty()) {
                sb.append(INDENT).append("Method Calls:\n");
                for (MethodCallEntity call : clazz.getMethodCalls()) {
                    sb.append(INDENT).append(INDENT)
                            .append(call.getParentClass()).append(".").append(call.getMethodName()).append("()\n");
                }
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