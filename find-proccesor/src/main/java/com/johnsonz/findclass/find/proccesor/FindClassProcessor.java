package com.johnsonz.findclass.find.proccesor;

import com.google.auto.service.AutoService;
import com.johnsonz.findclass.find.annotations.FindClass;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import org.apache.commons.collections4.CollectionUtils;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

@AutoService(Processor.class)
public class FindClassProcessor extends AbstractProcessor {
    private static final ClassName bridgeClass = ClassName.get("com.johnsonz.findclass.find.api", "BridgeClass");
    public static final String WARNING_TIPS = "Generated code automatically. Do not modify!";
    private Elements elementUtils;
    private Logger logger;
    Filer filer;
    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        logger = new Logger(processingEnvironment.getMessager());
        elementUtils = processingEnvironment.getElementUtils();
        filer = processingEnvironment.getFiler();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(FindClass.class);
        if (CollectionUtils.isNotEmpty(elements)) {
            MethodSpec.Builder loadClassMethodBuilder = MethodSpec.methodBuilder("loadAllClass")
                    .addModifiers(Modifier.PUBLIC)
                    .addParameter(ClassLoader.class, "classLoader")
                    .addException(ClassName.get(ClassNotFoundException.class));

            for (Element element : elements) {
                FindClass findClass = element.getAnnotation(FindClass.class);
                if (findClass.classPath() == null || "".equals(findClass.classPath())) {
                    throw new IllegalArgumentException("The annotation of FindClass must set up classPath firstly!");
                }
                if (findClass.allowThrowExpection()) {
                    loadClassMethodBuilder.beginControlFlow("try")
                            .addStatement("$T.addClass($T.class.getName(), classLoader.loadClass($S))", bridgeClass, ClassName.get((TypeElement) element), findClass.classPath())
                            .nextControlFlow("catch ($T e)", Throwable.class)
                            .endControlFlow();
                } else {
                    loadClassMethodBuilder.addStatement("$T.addClass($T.class.getName(), classLoader.loadClass($S))", bridgeClass, ClassName.get((TypeElement) element), findClass.classPath());

                }
            }

            try {
                JavaFile.builder("com.johnsonz.findclass.api", TypeSpec.classBuilder("BridgeManager")
                        .addMethod(loadClassMethodBuilder.build())
                        .addModifiers(Modifier.PUBLIC)
                        .addJavadoc(WARNING_TIPS).build())
                        .build()
                        .writeTo(filer);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }



        return true;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        LinkedHashSet<String> types = new LinkedHashSet<>();
        types.add(FindClass.class.getCanonicalName());
        return types;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

}
