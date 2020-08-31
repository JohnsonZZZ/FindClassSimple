package com.johnsonz.findclass.find.api;

import java.util.HashMap;
import java.util.Map;

public class BridgeClass {
    private static Map<String, Class> cacheMap = new HashMap<>();

    public static void addClass(String className, Class realClass) {
        cacheMap.put(className, realClass);
    }

    public static Class getClass(String className) {
        return cacheMap.get(className);
    }
}
