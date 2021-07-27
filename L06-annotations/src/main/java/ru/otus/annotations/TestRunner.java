package ru.otus.annotations;

import java.lang.reflect.InvocationTargetException;

public class TestRunner {
    public static void main(String[] args) throws ClassNotFoundException, InvocationTargetException, IllegalAccessException, NoSuchMethodException, InstantiationException {
        AnnotationProcessor processor = new AnnotationProcessor();
        processor.processAnnotations("ru.otus.annotations.AnnotationProcessorTest");
    }
}
