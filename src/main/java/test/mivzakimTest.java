package test;

//import Flows.SportFiveFlow;
import org.junit.Test;

import test.chatTest;
import org.openqa.selenium.WebDriver;

public class mivzakimTest extends BaseTest {
  chatTest chatTest;
  BaseTest BaseTest;

    public mivzakimTest() {
        super(driver);
        chatTest=new chatTest();
    }

    @Test
    public void runTest() throws Exception {
        BaseTest.testSetup();
        chatTest.createDb(chatTest.dbName1);

        chatTest.test01_ynetChat();
        chatTest.test02_n12Chat();
        chatTest.test03_Rotter();
        chatTest.test04_Hamal();
        chatTest.test05_maarivChat();

        BaseTest.end();

    }
}
