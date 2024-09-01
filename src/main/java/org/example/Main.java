package org.example;


import org.openqa.selenium.WebDriver;
import test.BaseTest;
import test.chatTest;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import test.mivzakimTest;

public class Main {
    BaseTest BaseTest;



    public static void main(String[] args) throws Exception {
       // Result result = JUnitCore.runClasses(chatTest.class);
       System.out.println("Main");

         Result result = JUnitCore.runClasses(mivzakimTest.class);
        for (Failure failure : result.getFailures()) {

            System.out.println(failure.toString());

        }
    }
}