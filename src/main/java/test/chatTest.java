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
import org.openqa.selenium.interactions.Actions;

import java.io.IOException;
import java.lang.constant.DynamicConstantDesc;
import java.text.SimpleDateFormat;

import java.time.Duration;
import java.util.*;
import java.util.List;


import org.bson.types.ObjectId;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.openqa.selenium.By.cssSelector;
import static org.openqa.selenium.By.tagName;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class chatTest extends BaseTest {
    BaseTest BaseTest;
    MongoClient mongoClient;
    MongoDatabase db;
    int count = 0;
    String password = "a72Y53vXKjhNDAJn";
    String userName = "shilo";
    String h1 = "";
    String dbName1 = "GQ-Dashboard";
    /*String src1="Ynet";
    String src2="N12";*/
    String src1 = "mivzakims";
    String src2 = "mivzakims";
    String replace="עוד…";


    private String ynetChat = "https://www.ynet.co.il/news/category/184";
    private String n12Chat = "https://www.n12.co.il/";
    private String geekTime = "https://www.one.co.il/Soccer/League/1";

    By ynetMainChat = By.cssSelector("div.Accordion");
    By ynetAd = By.cssSelector("div#closemaavron");

    By ynetOpenDetail = By.cssSelector("div.radioViewsPreference input[value='expanded']");//תצוגה מורחבת
    // By ynetAd=By.cssSelector("img#closeimg");
    By n12AllChat = By.cssSelector("button.mc-enter-btn");
    By n12OpenCase = By.cssSelector("div.mc-feed.mc-feed_shown.default-root-close-chat button.mc-enter-btn");
    By n12ChatCase = By.cssSelector("div#side-chat");//"div.mc-feed.mc-feed_shown.default-root-close-chat");
    By n12SubChat = By.cssSelector("div.mc-reporter__messages");
    By n12ChatOpen = By.cssSelector("div.mc-app.mc-topic.active");
    By XButton=By.cssSelector("button.mc-glr-btn-close");

    //  By n12FullChatButton=By.cssSelector("div.mc-feed.mc-feed_shown.default-root-close-chat button.mc-enter-btn");
    By n12FullChatButton = By.cssSelector("div.mc-feed.mc-feed_shown.default-root-close-chat button.mc-enter-btn");


    public chatTest() {
        super(driver);
        BaseTest = new BaseTest(driver);
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
        //  String connectionString = "mongodb+srv://shilo:a72Y53vXKjhNDAJn@chatnews.uaripa9.mongodb.net/?retryWrites=true&w=majority";
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

    public Boolean dropTable(int i, String site) {
        MongoCollection<Document> collection = db.getCollection(site);
        if (collection.countDocuments() < i) {
            return true;
        }
        return false;
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
        db.getCollection(site).updateOne(searchQuery, updateQuery);
    }

    public String getDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        return String.copyValueOf(formatter.format(date).toCharArray());
    }

    public void articleDetails(WebElement article) {
        String articleBody = "";
        String mainTitle = driver.findElement(By.cssSelector("h1.head-title")).getText();
        String subTitle = driver.findElement(By.cssSelector("p.head-sub-title")).getText();
        String category = driver.findElement(By.cssSelector("div.card_meta_cats")).getText();
        String reporter = driver.findElement(By.cssSelector("div.author.sp")).getText();
        String articleDate = driver.findElement(By.cssSelector("div.date.sp")).getText();
        WebElement art = driver.findElement(cssSelector("div#content"));
        List<WebElement> fullArticle = art.findElements(tagName("p"));

        for (int i = 0; i < fullArticle.size(); i++) {
            articleBody += fullArticle.get(i).getText() + '\n';
        }
        System.out.println(mainTitle);
        System.out.println(subTitle);
        System.out.println(category);
        System.out.println(reporter);
        System.out.println(articleDate);
        System.out.println(articleBody);
    }


    @Test
    public void test01_ynetChat() throws Exception {

        driver.get(ynetChat);
        String date = getDate();
        String chatDate = "";
        WebElement industries = driver.findElement(ynetMainChat);
        List<WebElement> links = industries.findElements(cssSelector("div.titleRow   "));
        System.out.println(links.size());
        if (dropTable(5, src1)) {
            for (int i = 0; i < 5; i++) {
                WebElement chat = links.get(i);
                String chatTime = chat.findElement(cssSelector("div.date")).getText();
                String chatTitle = chat.findElement(cssSelector("div.title")).getText();
                if (chatTitle.contains(replace))
                {
                    chatTitle.replace(replace,"");
                }
                chatDate = date + " " + chatTime;
                System.out.println("chatDate " + chatDate);
                mongoInsertData("Ynet", date, chatTime, chatTitle, i + 1,"NULL", "NULL", src1);
            }
        } else {
            for (int i = 0; i < 5; i++) {
                WebElement chat = links.get(i);
                String chatTime = chat.findElement(cssSelector("div.date")).getText();
                String chatTitle = chat.findElement(cssSelector("div.title")).getText();
                if (chatTitle.contains(replace))
                {
                    chatTitle.replace(replace,"");
                }
                chatDate = date + " " + chatTime;
                System.out.println("chatDate " + chatDate);
                mongoUpdateData("Ynet", date, chatTime, chatTitle, i + 1,"NULL", "NULL", src1);
            }
        }

    }


    @Test
    public void test03_n12Chat_Ver_2() throws InterruptedException {

        String currHandle = driver.getWindowHandle();
        driver.get(n12Chat);
        String date = getDate();
        String vidSrc = "";
        Thread.sleep(750);
        driver.findElement(n12FullChatButton).click();
        Thread.sleep(750);
        WebElement industriesN12 = driver.findElement(n12ChatCase);//n12ChatCase);n12ChatCase
        List<WebElement> linksN12 = industriesN12.findElements(n12SubChat);//"div.mc-message-wrap")); "div.mc-reporter__messages"
        System.out.println("linksN12 Size " + linksN12.size());
        System.out.println(dropTable(10, src2));

        if (dropTable(10,src2)) {
        for (int i = 0; i < 5; i++) {
            WebElement chat = linksN12.get(i);
            String chatTime = chat.findElement(cssSelector("p.mc-message-footer__time")).getText();//chat.findElement(cssSelector("p.mc-message-footer__time")).getText();//mc-message-footer
            String chatTitle = chat.findElement(cssSelector("div.mc-message-content.mc-message-content_open")).getText();//chat.findElement(cssSelector("div.mc-message-content.mc-message-content_open")).getText();//mc-extendable-text__content
            if (chatTitle.contains(replace))
            {
                chatTitle.replace(replace,"");
            }
            System.out.println(chatTitle+" chatTitle");
            List<WebElement> linksImgN12Vid = linksN12.get(i).findElements((By.cssSelector("div.mc-play-btn")));
            System.out.println(linksImgN12Vid.size() + " vid");
            if (linksImgN12Vid.size() > 0) {
              //  System.out.println("VIdeo Shilo 5");
                Thread.sleep(1000);
             //   System.out.println("7 " + linksImgN12Vid.get(0));
                WebElement vidClick = linksImgN12Vid.get(0);
              //  System.out.println("8 " + vidClick);
                vidClick.click();
                By streamSource = By.cssSelector("div.mc-glr-video-wrap");
                WebElement streamSrc = driver.findElement(streamSource);
                List<WebElement> st = streamSrc.findElements(By.cssSelector("video source"));
                vidSrc = st.get(0).getAttribute("src").toString();
             //   System.out.println("9 " + vidSrc);
                mongoInsertData("N12",date, chatTime, chatTitle,i+6, vidSrc, "NULL",src2);
                driver.findElement(XButton).click();
            }
            else
            {
                List<WebElement> linksImgN12 = linksN12.get(i).findElements((By.cssSelector("div.mc-content-media-item.mc-content-media-item_picture")));
                if (linksImgN12.size()>0) {
                    String img = linksImgN12.get(0).getAttribute("style");
                    img=img.substring(23, img.length()-3);
                    System.out.println(i+" "+date+" "+chatTime+" "+chatTitle+" "+img);
                    mongoInsertData("N12",date, chatTime, chatTitle,i+6,"NULL", img,src2);
                }
                else
                {
                    mongoInsertData("N12",date, chatTime, chatTitle,i+6, "NULL", "NULL",src2);
                }
            }
        }


 }
        else
        {
            for (int i = 0; i < 5; i++) {
                WebElement chat = linksN12.get(i);
                String chatTime = chat.findElement(cssSelector("p.mc-message-footer__time")).getText();//chat.findElement(cssSelector("p.mc-message-footer__time")).getText();//mc-message-footer
                String chatTitle = chat.findElement(cssSelector("div.mc-message-content.mc-message-content_open")).getText();//chat.findElement(cssSelector("div.mc-message-content.mc-message-content_open")).getText();//mc-extendable-text__content
                if (chatTitle.contains(replace))
                {
                    chatTitle.replace(replace,"");
                }
                List<WebElement> linksImgN12Vid = linksN12.get(i).findElements((By.cssSelector("div.mc-play-btn")));
                System.out.println(linksImgN12Vid.size() + " vid");
                if (linksImgN12Vid.size() > 0) {
                    //  System.out.println("VIdeo Shilo 5");
                    Thread.sleep(1000);
                    //   System.out.println("7 " + linksImgN12Vid.get(0));
                    WebElement vidClick = linksImgN12Vid.get(0);
                    //  System.out.println("8 " + vidClick);
                    vidClick.click();
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
                    List<WebElement> linksImgN12 = linksN12.get(i).findElements((By.cssSelector("div.mc-content-media-item.mc-content-media-item_picture")));
                    if (linksImgN12.size()>0) {
                        String img = linksImgN12.get(0).getAttribute("style");
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
}
