package com.johnsonz.findclass.find.api;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class FindClassManager {

    public static void findAllClasses(ClassLoader classLoader) {
        try {
            Class classOb = Class.forName("com.johnsonz.findclass.api.BridgeManager");
            Method findMethod = classOb.getMethod("loadAllClass");
            Object object = classOb.newInstance();
            findMethod.invoke(object, classLoader);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

}
