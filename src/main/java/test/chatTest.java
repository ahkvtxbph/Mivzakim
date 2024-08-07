package test;

import ch.qos.logback.core.joran.conditional.ThenAction;
import com.mongodb.*;
import com.mongodb.BasicDBObject;
//import com.mongodb.MongoClient;
import com.mongodb.client.*;
import com.mongodb.client.result.*;
import org.bson.Document;
import org.junit.*;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import java.io.IOException;
//import java.lang.constant.DynamicConstantDesc;
import java.text.SimpleDateFormat;

import java.time.Duration;
import java.util.*;
import java.util.List;


import org.bson.types.ObjectId;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.apache.commons.lang3.StringUtils.substring;
import static org.openqa.selenium.By.cssSelector;
import static org.openqa.selenium.By.tagName;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class chatTest extends BaseTest {
    BaseTest BaseTest;

 //   WebElement industriesN12B;
  //  List<WebElement> linksB;
//    chatTest chatTest;
    MongoClient mongoClient;
    MongoDatabase db;
    int count = 0;
    int sumTotal=0;
    String password = "a72Y53vXKjhNDAJn";
    String userName = "shilo";
    String h1 = "";
    String dbName1 = "GQ-Dashboard";
    /*String src1="Ynet";
    String src2="N12";*/
    String src1 = "mivzakims";
    String src2 = "mivzakims";
    String src3 = "mivzakims";
    String src4 = "mivzakims";
    String replace="קרא עוד…";
    String replace2="עוד…";
    String replace3="…";



    private String ynetChat = "https://www.ynet.co.il/news/category/184";
    private String n12Chat = "https://www.n12.co.il/";
    private String hamalSite = "https://hamal.co.il/";
    private String RotterScoop="https://rotter.net/forum/listforum.php";
    private String maarivChat = "https://www.maariv.co.il/breaking-news";

    By ynetMainChat = By.cssSelector("div.Accordion");
    By ynetAd = By.cssSelector("div#closemaavron");

    By maarivMainChat=By.cssSelector("div.breaking-news-body");

    By RotterMainScoop=By.xpath("/html/body/table[5]/tbody/tr/td[2]/table/tbody/tr/td/table[2]");
            // By.cssSelector("table[cellspacing='0'] table[cellspacing='1']");

    By rotterScoop=By.cssSelector("td[width='70%']");
    By rotterTime=By.cssSelector("td[align='center'] font.text13b");
    By rotterDate=By.cssSelector("td[align='center'] font[color='000000']");

    By ynetOpenDetail = By.cssSelector("div.radioViewsPreference input[value='expanded']");//תצוגה מורחבת
    // By ynetAd=By.cssSelector("img#closeimg");
    By n12AllChat = By.cssSelector("button.mc-enter-btn");
    By n12OpenCase = By.cssSelector("div.mc-feed.mc-feed_shown.default-root-close-chat button.mc-enter-btn");
    By n12ChatCase = By.cssSelector("div#side-chat");//"div.mc-feed.mc-feed_shown.default-root-close-chat");
    By n12SubChat = By.cssSelector("div.mc-reporter__messages");
    By hamalMain=By.cssSelector("div#__next");
    By hamalSecond=By.tagName("article");
    By n12ChatOpen = By.cssSelector("div.mc-app.mc-topic.active");
    By XButton=By.cssSelector("button.mc-glr-btn-close");

  //   By n12FullChatButton=By.cssSelector("div.mc-feed.mc-feed_shown.default-root-close-chat button.mc-enter-btn");
    By n12FullChatButton = By.cssSelector("div.mc-feed.mc-feed_shown.default-root-close-chat button.mc-enter-btn");


    public chatTest() {
        super(driver);
        BaseTest = new BaseTest(driver);
    }

    public String replaceMore(String chatTitle) {
        String temp = "";
        int status = 0;
        System.out.println("Temp " + chatTitle + " " + chatTitle.length());
        temp = chatTitle.substring(chatTitle.length() - 8);
        System.out.println("Temp " + temp + " " + temp.length());
        if (temp.contains(replace)) {
            status = 1;
            System.out.println("status - " + status);
            chatTitle = chatTitle.substring(0, chatTitle.length() - 8);
            System.out.println(chatTitle);
        } else if (status == 0) {
            temp = chatTitle.substring(chatTitle.length() - 4);
            System.out.println("Temp " + temp + " " + temp.length());
            if (temp.contains(replace2)) {
                if (temp.contains(replace2)) {
                    status = 2;
                    System.out.println("status - " + status);
                    chatTitle = chatTitle.substring(0, chatTitle.length() - 4);
                    System.out.println(chatTitle);
                }
            }

            } else if (status == 0) {
                temp = chatTitle.substring(chatTitle.length() - 1);
                System.out.println("Temp " + temp + " " + temp.length());
                if (chatTitle.contains(replace3)) {
                    status = 3;
                    System.out.println("status - " + status);
                    chatTitle = chatTitle.substring(0, chatTitle.length() - 1);
                    System.out.println(chatTitle);
                }
            }
            status = 0;
            return chatTitle;
    }
    public String getUrl() {
        return ynetChat;
    }

    /*    @BeforeClass
        public static void before()
        {
            driver.get("https://www.ynet.co.il/news/category/184");

        }*/
    @Before
    public void beforeTest() throws BaseTest {

        createDb(dbName1);

    }

    public void waitElement(By by) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(by));
        driver.findElement(by).click();
      /*  Thread.sleep(3500);
     WebElement expend=driver.findElement(by);
    Actions actions = new Actions(driver);
    actions.moveToElement(expend).moveToElement(expend).click().build().perform();*/

    }

    public void createDb(String site) {
         // String connectionString = "mongodb+srv://shilo:a72Y53vXKjhNDAJn@chatnews.uaripa9.mongodb.net/?retryWrites=true&w=majority";
         String connectionString = "mongodb+srv://yaal-2122:wsmJQ3ggbFxFtHX@cluster0.qnlfmxm.mongodb.net/GQ-Dashboard?retryWrites=true&w=majority";

        ServerApi serverApi = ServerApi.builder()
                .version(ServerApiVersion.V1)
                .build();
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(connectionString))
                .serverApi(serverApi)
                .build();
        //  mongoClient = new MongoClient(settings);//"localhost" , 27017 );

        System.out.println("Created Mongo Connection successfully");

        MongoClient mongoClient = MongoClients.create(connectionString);
        db = mongoClient.getDatabase(site);

        System.out.println("Get database is successful");
    }



    public boolean dropTable(int i, String site,String val) {
        boolean status=false;
        int count=0;
        int temp=0;
        String db1=this.dbName1;
        MongoCollection<Document> collection = db.getCollection(site);
        temp=(int)collection.countDocuments();
        sumTotal=temp;
        System.out.println("sumTotal DropTable- "+sumTotal);
       System.out.println("DB COUNT - "+temp);
       if (temp<i)
       {

           return false;
       }
       else
       {
           return true;
       }
/*
        switch(i) {
            case (5): {
                if ((temp < 5)&&(temp > 0))
                {
                    count=temp;
                }
                else {
                        count=5;
                }
                break;
            }
            case (10):
            {
                if (temp == 5)
                {
                    count=10;
                }
                else if (temp <10){
                    if (collection.countDocuments() < i)
                    {
                        count=(i-temp);
                    }
                }
                else
              {

              }

                break;
        }
            case (20):
            {
                if (collection.countDocuments() == 10)
                {
                    status=true;
                    count=20;
                }
                else {
                    if (collection.countDocuments() < i)
                    {
                        count=(i-(int)collection.countDocuments());
                    }
                }

                break;
            }
            default:
                break;
                // code block
        }

        return count;*/

    }


    public void mongoInsertData(String author, String date, String time, String title, int count,String video, String imageLink, String site) {
        System.out.println("Shilo insert " + count + " " + author + " " + date + " " +time+ " "+ count + " " + title + " " + imageLink);
        MongoCollection<Document> collection = db.getCollection(site);
        InsertOneResult result = collection.insertOne(new Document()
                .append("_id", new ObjectId())
                .append("num", count)
                .append("author", author)
                .append("date", date)
                .append("time", time)
                .append("title", title)
                .append("image", imageLink)
                .append("video", video));

    }

    public void mongoUpdateData(String author, String date, String time, String title, int count,String video, String imageLink, String site) {
        System.out.println("Shilo Update " + count + " " + author + " " + date +" "+time+ " " + count + " " + title + "" + imageLink);
        BasicDBObject searchQuery = new BasicDBObject();
        searchQuery.append("num", count)
                .append("author", author);


        BasicDBObject updateQuery = new BasicDBObject();
        updateQuery.append("$set",
                new BasicDBObject().append("author", author)
                        .append("date", date)
                        .append("time", time)
                        .append("title", title)
                        .append("image", imageLink)
                        .append("video", video));
      /*  try {
            db.getCollection(site).updateOne(searchQuery, updateQuery);
        }
        catch(Exception e) {
                         //(String author, String date, String time, String title, int count,String video, String imageLink, String site);
            mongoInsertData(author, date, time, title,  count,video, imageLink,  site);
            //  Block of code to handle errors
        }*/
        db.getCollection(site).updateOne(searchQuery, updateQuery);
    }

    public String getDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        return String.copyValueOf(formatter.format(date).toCharArray());
    }
/*   @Test
        public  void test00_dropTable()  {
           db.getCollection(src2).drop();
    }*/

    @Test
    public  void test01_ynetChat()  {

      // db.getCollection(src2).drop();
        boolean bTemp;
        BaseTest.driver.get(ynetChat);
        String date = getDate();
        String chatDate = "";
        WebElement industries = driver.findElement(ynetMainChat);
        List<WebElement> links = industries.findElements(cssSelector("div.titleRow   "));
        System.out.println(links.size());
        bTemp=dropTable(5, src1,"Ynet");
        bTemp=dropTable(5, src1,"Ynet");
        if ((sumTotal>25))
        {System.out.println("sumTotal 1 - "+sumTotal);
            db.getCollection(src2).drop();};
        if (!dropTable(5, src1,"Ynet")) {

            for (int i=0; i < 5; i++) {
                WebElement chat = links.get(4-i);
                String chatTime = chat.findElement(cssSelector("div.date")).getText();
                String chatTitle = chat.findElement(cssSelector("div.title")).getText();

                chatTitle=replaceMore(chatTitle);

                chatDate = date + " " + chatTime;
                System.out.println("chatDate " + chatDate);
                mongoInsertData("Ynet", date, chatTime, chatTitle, i + 1,"NULL", "NULL", src1);
            }
        } else {
            for (int i = 0; i < 5; i++) {
                WebElement chat = links.get(4-i);
                String chatTime = chat.findElement(cssSelector("div.date")).getText();
                String chatTitle = chat.findElement(cssSelector("div.title")).getText();
                chatTitle=replaceMore(chatTitle);
                chatDate = date + " " + chatTime;
                System.out.println("chatDate " + chatDate);
                mongoUpdateData("Ynet", date, chatTime, chatTitle, i + 1,"NULL", "NULL", src1);
            }
        }
    }


    @Test
    public void test02_n12Chat() throws Exception {
        String vidSrc = "";

        int temp=0;
        String img="NULL";
        driver.get(n12Chat);
        String date = getDate();
        String chatDate = "";
        driver.findElement(n12FullChatButton).click();
        Thread.sleep(750);
        WebElement industriesN12 = driver.findElement(n12ChatCase);
        List<WebElement> links = industriesN12.findElements(n12SubChat);
        System.out.println("links.size() - "+links.size());

        if (!dropTable(10,src2,"N12")) {
            System.out.println("sumTotal 2 - "+sumTotal);
            if ((sumTotal<10)&&(sumTotal>5)) {
                db.getCollection(src2).drop();
                test01_ynetChat();
                driver.get(n12Chat);
                Thread.sleep(1000);
                //Thread.sleep(750);
                driver.findElement(n12FullChatButton).click();
                temp=1;
            }


            //    Thread.sleep(1000);
            for (int i = 0; i < 5; i++) {
                //  driver.get(n12Chat);
                Thread.sleep(750);
                // driver.findElement(n12FullChatButton).click();
                if (temp==1) {
                    industriesN12 = driver.findElement(n12ChatCase);//n12ChatCase);n12ChatCase
                    links = industriesN12.findElements(n12SubChat);//"div.mc-message-wrap")); "div.mc-reporter__messages"
                    temp=0;
                }
                System.out.println("links.size()2 - "+links.size());
                img="NULL";
                Thread.sleep(1000);

                WebElement chat = links.get(4-i);//p.mc-message-footer__time
                System.out.println(i+" chat.getText() - "+chat.getText());
                String chatTime = chat.findElement(cssSelector("div.mc-message-footer")).getText();//chat.findElement(cssSelector("p.mc-message-footer__time")).getText();//mc-message-footer
                String chatTitle = chat.findElement(cssSelector("div.mc-extendable-text__content")).getText();//div#side-chat div.mc-extendable-text__content  chat.findElement(csmc-extendable-text__content")).getText();//mc-extendable-text__content
                System.out.println("chatTime - "+chatTime+ " chatTitle "+chatTitle);
                System.out.println("links Size " + links.size());//div.mc-message-content.mc-message-content_open

                chatTitle=replaceMore(chatTitle);
                System.out.println(chatTitle+" chatTitle");
                List<WebElement> linksImgN12Vid =chat.findElements((By.cssSelector("div.mc-play-btn")));
                System.out.println(linksImgN12Vid.size() + " vid");
                if (linksImgN12Vid.size() > 0) {
                    Thread.sleep(1000);
                    //   System.out.println("7 " + linksImgN12Vid.get(0));
                    //WebElement vidClick = linksImgN12Vid.get(0);
                    linksImgN12Vid.get(0).click();
                    //  System.out.println("8 " + vidClick);
                    //vidClick.click();
                    By streamSource = By.cssSelector("div.mc-glr-video-wrap");
                    WebElement streamSrc = driver.findElement(streamSource);
                    List<WebElement> st = streamSrc.findElements(By.cssSelector("video source"));
                    vidSrc = st.get(0).getAttribute("src").toString();
                    //   System.out.println("9 " + vidSrc);
                    mongoInsertData("N12",date, chatTime, chatTitle,i+6, vidSrc, "NULL",src2);
                    System.out.println("02+1");
                    driver.findElement(XButton).click();
                }
                else
                {
                    List<WebElement> linksImgN12 = links.get(4-i).findElements((By.cssSelector("div.mc-content-media-item.mc-content-media-item_picture")));
                    if (linksImgN12.size()>0) {
                        img = linksImgN12.get(0).getAttribute("style");
                        img=img.substring(23, img.length()-3);
                        System.out.println(i+" "+date+" "+chatTime+" "+chatTitle+" "+img);
                        mongoInsertData("N12",date, chatTime, chatTitle,i+6,"NULL", img,src2);
                        System.out.println("02+2");
                    }
                    else
                    {
                        mongoInsertData("N12",date, chatTime, chatTitle,i+6, "NULL", "NULL",src2);
                        System.out.println("02+3");
                    }
                }
            }

        }
        else
        {    if (temp==1) {
            WebElement industriesN12B  = driver.findElement(n12ChatCase);//n12ChatCase);n12ChatCase
            List<WebElement> linksB = industriesN12B.findElements(n12SubChat);
            temp=0;
        }
            for (int i = 0; i < 5; i++) {
                int position=4-i;
                img="NULL";
                Thread.sleep(750);
                WebElement industriesN12B  = driver.findElement(n12ChatCase);//n12ChatCase);n12ChatCase
                List<WebElement> linksB = industriesN12B.findElements(n12SubChat);
                System.out.println("linksB- "+linksB.size()+" "+position);

                WebElement chat = linksB.get(position);
                Thread.sleep(750);

              //  String chatTime = chat.findElement(cssSelector("p.mc-message-footer__time")).getText();//chat.findElement(cssSelector("p.mc-message-footer__time")).getText();//mc-message-footer
               // String chatTitle = chat.findElement(cssSelector("div.mc-message-content.mc-message-content_open")).getText();//div#side-chat div.mc-extendable-text__content  chat.findElement(csxtendable-text__contentxtendable-text__content
                String chatTime = chat.findElement(cssSelector("div.mc-message-footer")).getText();//chat.findElement(cssSelector("p.mc-message-footer__time")).getText();//mc-message-footer
                String chatTitle = chat.findElement(cssSelector("div.mc-extendable-text__content")).getText();//div#side-chat div.mc-extendable-text__content  chat.findElement(div.mc-extendable-text__content")).getText();//mc-extendable-text__content
                System.out.println("chatTitle - "+i+" "+chatTitle);
                if (chatTitle.length()>0)
                {chatTitle=replaceMore(chatTitle);}
                List<WebElement> linksImgN12Vid = links.get((4-i)).findElements((By.cssSelector("div.mc-play-btn")));
                System.out.println(linksImgN12Vid.size() + " vid");
                if (linksImgN12Vid.size() > 0) {
                    //  System.out.println("VIdeo Shilo 5");
                    Thread.sleep(1000);
                    //   System.out.println("7 " + linksImgN12Vid.get(0));
                    //WebElement vidClick = linksImgN12Vid.get(0);
                    //  System.out.println("8 " + vidClick);
                    linksImgN12Vid.get(0).click();
                    //vidClick.click();
                    By streamSource = By.cssSelector("div.mc-glr-video-wrap");
                    WebElement streamSrc = driver.findElement(streamSource);
                    List<WebElement> st = streamSrc.findElements(By.cssSelector("video source"));
                    vidSrc = st.get(0).getAttribute("src").toString();
                    //   System.out.println("9 " + vidSrc);
                    mongoUpdateData("N12",date, chatTime, chatTitle,i+6, vidSrc, "NULL",src2);
                    driver.findElement(XButton).click();

                }
                else
                {
                    List<WebElement> linksImgN12 = links.get((4-i)).findElements((By.cssSelector("div.mc-content-media-item.mc-content-media-item_picture")));
                    if (linksImgN12.size()>0) {
                        img = linksImgN12.get(0).getAttribute("style");
                        img=img.substring(23, img.length()-3);
                        System.out.println(i+" "+date+" "+chatTime+" "+chatTitle+" "+img);
                        mongoUpdateData("N12",date, chatTime, chatTitle,i+6,"NULL", img,src2);
                    }
                    else
                    {
                        mongoUpdateData("N12",date, chatTime, chatTitle,i+6, "NULL", "NULL",src2);
                    }
                }
            }
        }
    }

   @Test
    public void test03_Rotter() throws Exception {
        driver.get(RotterScoop);
        Thread.sleep(1500);
   System.out.println("Rotter - "+driver.getCurrentUrl());

        WebElement rotterMain = driver.findElement(RotterMainScoop);
        List<WebElement> rotterScoopTitle = rotterMain.findElements(rotterScoop);
        List<WebElement> rotterScoopTime=rotterMain.findElements(rotterTime);
        List<WebElement> rotterScoopDate=rotterMain.findElements(rotterDate);
       System.out.println("rotterScoopTitle - "+rotterScoopTitle.size());
      // System.out.println("rotterScoopTime - "+rotterScoopTime.size());
      // System.out.println("rotterScoopDate - "+rotterScoopDate.size());
       System.out.println("Rotter Size " + rotterScoopTitle.size());
       System.out.println("sumTotal 3 - "+sumTotal);
       if ((sumTotal<15)&&(sumTotal>10)) {
           db.getCollection(src2).drop();
           test01_ynetChat();
           test02_n12Chat();
           driver.get(RotterScoop);
           Thread.sleep(1000);

       }
   System.out.println("Rotte - "+dropTable(15, src3,"Rotter"));
       if (!dropTable(15, src3,"Rotter")) {

           for (int i=0; i < 5; i++) {
               WebElement chat = rotterScoopTitle.get(4-i);
               String chatTime = rotterScoopTime.get(4-i).getText();
               String chatDate = rotterScoopDate.get(4-i).getText();
               String chatTitle = rotterScoopTitle.get(4-i).getText();

               if (chatTitle.length()>0)
               {chatTitle=replaceMore(chatTitle);}

             //  chatDate = date + " " + chatTime;
               System.out.println("chatDate " + chatDate+" "+"chatTime " + chatTime);
               mongoInsertData("Rotter", chatDate, chatTime, chatTitle, i + 11,"NULL", "NULL", src3);
           }
       } else {
           for (int i = 0; i < 5; i++) {
               String chatTime = rotterScoopTime.get(4-i).getText();
               String chatDate = rotterScoopDate.get(4-i).getText();
               String chatTitle = rotterScoopTitle.get(4-i).getText();

               chatTitle=replaceMore(chatTitle);

               //  chatDate = date + " " + chatTime;
               System.out.println("chatDate " + chatDate+" "+"chatTime " + chatTime);
               mongoUpdateData("Rotter", chatDate, chatTime, chatTitle, i + 11,"NULL", "NULL", src3);
           }
       }
    }
    @Test
    public void test04_Hamal() throws Exception {
        driver.get(hamalSite);
        String date = getDate();
        String chatDate = "";
        String img="NULL";
        Thread.sleep(750);
        WebElement hamal = driver.findElement(hamalMain);
        List<WebElement> hamaList = hamal.findElements(hamalSecond);
        System.out.println("hamaList Size " + hamaList.size());
        if (!dropTable(20, src4,"Hamal")) {
           System.out.println("sumTotal 4- "+sumTotal);
            if ((sumTotal<20)&&(sumTotal>15))
            { db.getCollection(src2).drop();
                test01_ynetChat();
                test02_n12Chat();
                test03_Rotter();
                driver.get(hamalSite);}

            Thread.sleep(1000);
            for (int i = 0; i < 5; i++) {
                img="NULL";
                WebElement chat = hamaList.get(4-i);
                String chatTime = chat.findElement(cssSelector("span.styles_span__I9y9v.styles_date__Jyh31")).getText();
                String chatTitle = chat.findElement(cssSelector("h2.styles_title__WrHVK")).getText();
                String chatMain = chat.findElement(cssSelector("h2.styles_title__WrHVK")).getText();
                chatTime=chatTime.substring(0, 5);
                chatTitle=replaceMore(chatTitle);
                  List<WebElement> linksImgHamal = hamaList.get(4-i).findElements((By.cssSelector("div.styles_imageContainer__7yyQf img")));//"img[alt='image-widget']"

              //  List<WebElement> linksImgHamal = hamaList.get(4-i).findElements((By.cssSelector("img[alt='gallery main picture']")));

                System.out.println("linksImgHamal "+linksImgHamal.size());
                if (linksImgHamal.size() > 0) {
                    img = linksImgHamal.get(0).getAttribute("src");
                    System.out.println("img insert -"+img);
                    mongoInsertData("Hamal",date, chatTime, chatTitle,i+16,"NULL", img,src3);

                }
                else
                {
                    List<WebElement> linksImgHamalB = hamaList.get(4-i).findElements((By.cssSelector("div.styles_imageContainer__7yyQf img")));//"img[alt='gallery main picture']"
                    if (linksImgHamalB.size() > 0) {
                        img = linksImgHamalB.get(0).getAttribute("src");
                        System.out.println("img insert B-"+img);
                        mongoInsertData("Hamal",date, chatTime, chatTitle,i+16,"NULL", img,src3);

                    }
                    else {
                        mongoInsertData("Hamal", date, chatTime, chatTitle, i + 16, "NULL", "NULL", src3);
                    }
                }
            }
        }
        else
        {
            for (int i = 0; i < 5; i++) {
                WebElement chat = hamaList.get(4-i);
                String chatTime = chat.findElement(cssSelector("span.styles_span__I9y9v.styles_date__Jyh31")).getText();
                chatTime=chatTime.substring(0, 5);
                img="NULL";
                String chatTitle = chat.findElement(cssSelector("h2.styles_title__WrHVK")).getText();
                String chatMain = chat.findElement(cssSelector("h2.styles_title__WrHVK")).getText();
                chatTitle=replaceMore(chatTitle);
                List<WebElement> linksImgHamal = hamaList.get(4-i).findElements((By.cssSelector("div.styles_imageContainer__7yyQf img")));//img[alt='image-widget']")));
                List<WebElement> linksImgHamalB = hamaList.get(4-i).findElements((By.cssSelector("div.styles_imageContainer__7yyQf img")));//"img[alt='gallery main picture']"
                System.out.println("linksImgHamal "+linksImgHamal.size());
                System.out.println("linksImgHamalB "+linksImgHamalB.size());
                if (linksImgHamal.size() > 0) {
                    img = linksImgHamal.get(0).getAttribute("src");
                    System.out.println("img update -"+img);
                    mongoUpdateData("Hamal",date, chatTime, chatTitle,i+16,"NULL", img,src2);
                }
                else if (linksImgHamalB.size() > 0)
                {
                   // List<WebElement> linksImgHamalB = hamaList.get(4-i).findElements((By.cssSelector("img[alt='gallery main picture']")));
                    //if (linksImgHamalB.size() > 0) {
                        img = linksImgHamalB.get(0).getAttribute("src");
                        System.out.println("img update B-"+img);
                        mongoUpdateData("Hamal",date, chatTime, chatTitle,i+16,"NULL", img,src2);

                    //}
                    }
                else {
                    mongoUpdateData("Hamal", date, chatTime, chatTitle, i + 16, "NULL", "NULL", src2);
                }
            }
        }

    }

    @Test
    public  void test05_maarivChat() throws Exception {
        // db.getCollection(src2).drop();
        boolean bTemp;
        driver.get(maarivChat);
        String date = getDate();
        String chatDate = "";
        WebElement industries = driver.findElement(maarivMainChat);
        List<WebElement> links = industries.findElements(cssSelector("div.breaking-news-item"));
        System.out.println(links.size());
        bTemp=dropTable(5, src1,"Ynet");
        if ((sumTotal>25)) {
            System.out.println("sumTotal 6 - " + sumTotal);
            db.getCollection(src2).drop();
            if ((sumTotal < 25) && (sumTotal > 20)) {
                db.getCollection(src2).drop();
                test01_ynetChat();
                test02_n12Chat();
                test03_Rotter();
                test04_Hamal();
                driver.get(maarivChat);
            }
        }
        if (!dropTable(25, src1,"Maariv")) {

            for (int i=0; i < 5; i++) {
                WebElement chat = links.get(4-i);
                String chatTime = chat.findElement(cssSelector("div.breaking-news-item-time")).getText();
                String chatTitle = chat.findElement(cssSelector("div.breaking-news-item-title-container span.breaking-news-item-title")).getText();
                date=chat.findElement(cssSelector("div.breaking-news-item-date")).getText();
                chatTitle=replaceMore(chatTitle);

                chatDate = date + " " + chatTime;
                System.out.println("chatDate " + chatDate);
                mongoInsertData("Maariv", date, chatTime, chatTitle, i + 21,"NULL", "NULL", src1);
            }
        } else {
            for (int i = 0; i < 5; i++) {
                WebElement chat = links.get(4-i);
                String chatTime = chat.findElement(cssSelector("div.breaking-news-item-time")).getText();
                String chatTitle = chat.findElement(cssSelector("div.breaking-news-item-title-container span.breaking-news-item-title")).getText();
                date=chat.findElement(cssSelector("div.breaking-news-item-date")).getText();
                chatTitle=replaceMore(chatTitle);

                chatDate = date + " " + chatTime;
                System.out.println("chatDate " + chatDate);
                mongoUpdateData("Maariv", date, chatTime, chatTitle, i + 21,"NULL", "NULL", src1);
            }
        }
    }
}
