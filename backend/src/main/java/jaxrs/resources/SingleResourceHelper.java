package jaxrs.resources;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class SingleResourceHelper {
    public static Object selectSingleField(@NotEmpty String field, @NotNull Object obj) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String methodName = "get" + field.substring(0,1).toUpperCase() + field.substring(1);
        Method method = obj.getClass().getDeclaredMethod(methodName);
        return method.invoke(obj);
    }
}
