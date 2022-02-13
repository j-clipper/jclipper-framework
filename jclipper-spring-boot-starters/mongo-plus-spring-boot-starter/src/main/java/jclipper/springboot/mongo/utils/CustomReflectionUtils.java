package jclipper.springboot.mongo.utils;

import org.springframework.lang.Nullable;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;

public class CustomReflectionUtils extends ReflectionUtils {
    public static Object getField(String fieldName,@Nullable Object target){
        Field field = findField(target.getClass(),fieldName);
        ReflectionUtils.makeAccessible(field);
        return getField(field,target);
    }
}
