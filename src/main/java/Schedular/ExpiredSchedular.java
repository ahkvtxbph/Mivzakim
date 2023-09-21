/*
package Schedular;
//import Service.SubscriptionService;
import lombok.SneakyThrows;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.TimerTask;
import test.mivzakimTest;

@Component
public class ExpiredSchedular extends TimerTask {
    private final mivzakimTest mivzakimTest;

    public ExpiredSchedular(mivzakimTest mivzakimTest) {
        this.mivzakimTest = mivzakimTest;
    }

    @Override
    public void run() {

        System.out.println(new Date());
        try {
            System.out.println("Start"+new Date());
             mivzakimTest.runTest();
             System.out.println("End - "+new Date());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}



*/
