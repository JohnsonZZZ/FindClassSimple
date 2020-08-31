package com.johnsonz.findclass.sample;

import com.johnsonz.findclass.find.annotations.FindClass;

@FindClass(classPath = "com.johnsonz.findclass.sample.AppleImp")
public interface Apple {

    String getFruitName();

}
