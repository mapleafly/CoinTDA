package org.cointda.util;

import org.springframework.beans.BeanUtils;
import org.springframework.lang.Nullable;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class CopyUtil {

    public static <T> T copy(Object source, Class<T> c) {
        if (source == null) {
            return null;
        }
     return copy(source, c, null);
    }

    public static <T> T copy(Object source, Class<T> c, @Nullable String... ignoreProperties){
        if (source == null) {
            return null;
        }
        try {
            T instance = c.getDeclaredConstructor().newInstance();
            if(ignoreProperties == null){
                BeanUtils.copyProperties(source, instance);
            }else{
                BeanUtils.copyProperties(source, instance, ignoreProperties);
            }
            return instance;
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    public static <E, T> List<T> copyList(List<E> sources, Class<T> c) {
        if (CollectionUtils.isEmpty(sources)) {
            return new ArrayList<T>();
        }
        List<T> list = new ArrayList<T>();
        for (E source : sources) {
            list.add(copy(source, c));
        }
        return list;
    }
}
