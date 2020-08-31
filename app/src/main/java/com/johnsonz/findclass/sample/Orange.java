package com.johnsonz.findclass.sample;

import com.johnsonz.findclass.find.annotations.FindClass;

@FindClass(classPath = "com.johnsonz.findclass.sample.OrangeImp", allowThrowExpection = true)
public interface Orange {

    String getFruitName();

}
