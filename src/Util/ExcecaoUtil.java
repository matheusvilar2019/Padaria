package Util;

public class ExcecaoUtil {

    public static RuntimeException erro(String classeMetodo, Exception e) {
        return new RuntimeException("Erro em " + classeMetodo, e);
    }

    public static RuntimeException erro(Exception e) {
        StackTraceElement caller = Thread.currentThread().getStackTrace()[2];
        String local = caller.getClassName() + "." + caller.getMethodName() + "()";
        return new RuntimeException("Erro em " + local, e);
    }
}
