package ru.otus.annotations;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class AnnotationProcessor {

    private final List<Method> markedByTest;

    private final List<Method> markedByBefore;

    private final List<Method> markedByAfter;

    private int numberOfSuccessfulTests;

    private int numberOfFailedTests;


    public AnnotationProcessor() {
        this.markedByTest = new ArrayList<>();
        this.markedByBefore = new ArrayList<>();
        this.markedByAfter = new ArrayList<>();
        int numberOfSuccessfulTests = 0;
        int numberOfFailedTests = 0;
    }

    public void processAnnotations(String className) throws InvocationTargetException, IllegalAccessException, ClassNotFoundException, NoSuchMethodException, InstantiationException {
        Class<?> clazz = Class.forName(className);

        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Test.class)) {
                method.setAccessible(true);
                markedByTest.add(method);
            } else if (method.isAnnotationPresent(Before.class)) {
                method.setAccessible(true);
                markedByBefore.add(method);
            } else if (method.isAnnotationPresent(After.class)) {
                method.setAccessible(true);
                markedByAfter.add(method);
            }

        }

        try {
            for (Method testMethod : markedByTest) {
                Object instance = clazz.getDeclaredConstructor().newInstance();
                for (Method beforeMethod : markedByBefore) {
                    beforeMethod.invoke(instance);
                }
                testMethod.invoke(instance);
                numberOfSuccessfulTests++;
                for (Method afterMethod : markedByAfter) {
                    afterMethod.invoke(instance);
                }
            }
        } catch (Exception e) {
            numberOfFailedTests++;
        } finally {
            System.out.printf("Number of tests: %d \n", markedByTest.size());
            System.out.printf("Number of successful tests %d \n", numberOfSuccessfulTests);
            System.out.printf("Number of failed tests %d \n", numberOfFailedTests);
        }
    }
}
