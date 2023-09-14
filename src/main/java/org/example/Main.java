package org.example;


import test.chatTest;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class Main {

    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(chatTest.class);
        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());

        }
    }
}