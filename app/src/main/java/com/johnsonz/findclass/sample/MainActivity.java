package com.johnsonz.findclass.sample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.johnsonz.findclass.find.api.BridgeClass;
import com.johnsonz.findclass.find.api.FindClassManager;

import java.lang.reflect.Method;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FindClassManager.findAllClasses(getClassLoader());
//        Apple apple = BridgeClass.getClass(Apple.class.getName());

        Log.i(TAG, BridgeClass.getClass(Apple.class.getName()).getCanonicalName());
    }
}
