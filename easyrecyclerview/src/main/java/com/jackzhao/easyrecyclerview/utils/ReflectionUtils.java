package com.jackzhao.easyrecyclerview.utils;


import android.util.Log;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by jack_zhao on 2018/8/15.
 */
public class ReflectionUtils {

    private static final String TAG = ReflectionUtils.class.getSimpleName();

    public static Object getField(Class clazz, String fieldName, Object instance) {
        try {
            Field field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(instance);
        } catch (NoSuchFieldException e) {
            Class superClass = clazz.getSuperclass();
            if (superClass != null) {
                return getField(superClass, fieldName, instance);
            }
            Log.e(TAG, "getField(clazz) reached super Object but still can't find field", e);
            return null;
        } catch (Exception e) {
            Log.e(TAG, "getField(clazz) error", e);
            return null;
        }
    }

    public static Object getField(String className, String fieldName, Object instance) {
        try {
            Class clazz = Class.forName(className);
            return getField(clazz, fieldName, instance);
        } catch (Exception e) {
            Log.e(TAG, "getField(className) error", e);
            return null;
        }
    }

    public static Object newInstance(Class clazz, Object[] parameters, Class[] parameterTypes) {
        try {
            Constructor constructor = clazz.getDeclaredConstructor(parameterTypes);
            constructor.setAccessible(true);
            return constructor.newInstance(parameters);
        } catch (Exception e) {
            Log.e(TAG, "newInstance(clazz) error", e);
            return null;
        }
    }

    public static Object newInstance(Class clazz) {
        try {
            Constructor constructor = clazz.getDeclaredConstructor();
            constructor.setAccessible(true);
            return constructor.newInstance();
        } catch (Exception e) {
            Log.e(TAG, "newInstance(clazz) error", e);
            return null;
        }
    }

    public static Object newInstance(String className) {
        try {
            Class clazz = Class.forName(className);
            return newInstance(clazz);
        } catch (Exception e) {
            Log.e(TAG, "newInstance(className) error", e);
            return null;
        }
    }


    public static Object invoke(Class clazz, String methodName, Object instance) {
        try {
            Method method = clazz.getDeclaredMethod(methodName);
            method.setAccessible(true);
            return method.invoke(instance);
        } catch (Exception e) {
            Log.e(TAG, "invoke(clazz) error", e);
            return null;
        }
    }

    public static Object invoke(String className, String methodName, Object instance) {
        try {
            Class clazz = Class.forName(className);
            return invoke(clazz, methodName, instance);
        } catch (Exception e) {
            Log.e(TAG, "invoke(className) error", e);
            return null;
        }
    }

    public static Object invoke(Class clazz, String methodName, Object instance, Object[] parameters, Class[] parameterTypes) {
        try {
            Method method = clazz.getDeclaredMethod(methodName, parameterTypes);
            method.setAccessible(true);
            return method.invoke(instance, parameters);
        } catch (Exception e) {
            Log.e(TAG, "invoke(clazz=" + clazz + ") error", e);
            return null;
        }
    }

    public static Object invoke(String className, String methodName, Object instance, Object[] parameters, Class[] parameterTypes) {
        try {
            Class clazz = Class.forName(className);
            Method method = clazz.getDeclaredMethod(methodName, parameterTypes);
            method.setAccessible(true);
            return method.invoke(instance, parameters);
        } catch (Exception e) {
            Log.e(TAG, "invoke(clazz=" + className + ") error", e);
            return null;
        }
    }

    public static Object invokePublicStatic(Class clazz, String methodName, Object[] parameters, Class[] parameterTypes) {
        try {
            Method method = clazz.getMethod(methodName, parameterTypes);
            return method.invoke(null, parameters);
        } catch (Exception e) {
            Log.e(TAG, "invoke(clazz=" + clazz + ") error", e);
            return null;
        }
    }

    public static Object invokePublic(Class clazz, String methodName, Object instance, Object[] parameters, Class[] parameterTypes) {
        try {
            Method method = clazz.getMethod(methodName, parameterTypes);
            return method.invoke(instance, parameters);
        } catch (Exception e) {
            Log.e(TAG, "invoke(clazz=" + clazz + ") error", e);
            return null;
        }
    }

    public static Object invokePublic(String className, String methodName, Object instance, Object[] parameters, Class[] parameterTypes) {
        try {
            Class clazz = Class.forName(className);
            Method method = clazz.getMethod(methodName, parameterTypes);

            return method.invoke(instance, parameters);
        } catch (Exception e) {
            Log.e(TAG, "invoke(clazz=" + className + ") error", e);
            return null;
        }
    }

    public static void setField(Class clazz, String fieldName, Object instance, Object newValue) {
        try {
            Field field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(instance, newValue);
        } catch (NoSuchFieldException e) {
            Class superClass = clazz.getSuperclass();
            if (superClass != null) {
                setField(superClass, fieldName, instance, newValue);
                return;
            }
            Log.e(TAG, "setField(clazz) reached super Object but still can't find field", e);
        } catch (Exception e) {
            Log.e(TAG, "setField(clazz) error", e);
        }
    }

    public static void setField(String className, String fieldName, Object instance, Object newValue) {
        try {
            Class clazz = Class.forName(className);
            setField(clazz, fieldName, instance, newValue);
        } catch (Exception e) {
            Log.e(TAG, "setField(className) error", e);
        }
    }

    public static List<Class<?>> getAllInterfaces(Class<?> clazz) {
        if (clazz == null) {
            return new ArrayList();
        }
        Set<Class<?>> interfaces = new HashSet();
        while (clazz != null && clazz != Object.class) {
            for (Class<?> interf : clazz.getInterfaces()) {
                interfaces.add(interf);
                interfaces.addAll(getAllInterfaces(interf));
            }
            clazz = clazz.getSuperclass();
        }
        return new ArrayList<>(interfaces);
    }
}
