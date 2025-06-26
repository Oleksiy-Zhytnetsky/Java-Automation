package ua.edu.ukma.Zhytnetsky;

import lombok.SneakyThrows;

import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Method;

public final class DisplayUtils {

    private DisplayUtils() {
    }

    public static char mapOperationToSign(final Operation<?> op) {
        return mapOperationNameToSign(extractOperationName(op));
    }

    @SneakyThrows
    private static String extractOperationName(final Operation<?> op) {
        final Method writeReplace = op.getClass().getDeclaredMethod("writeReplace");
        writeReplace.setAccessible(true);

        final SerializedLambda lambda = (SerializedLambda) writeReplace.invoke(op);
        return lambda.getImplMethodName();
    }

    private static char mapOperationNameToSign(final String opName) {
        return switch (opName) {
            case "add" -> '+';
            case "sub" -> '-';
            case "mult" -> '*';
            case "div" -> '/';
            default -> '?';
        };
    }

}
