package sample;

/** @author sabbir sabbir@mpower-social.com
* */

import com.mpower.clientcollection.controller.FxViewController;
import com.mpower.desktop.config.AppConfiguration;
import com.mpower.desktop.config.AppLogger;
import com.mpower.desktop.database.InitializeDatabase;
import com.sun.javafx.tk.Toolkit;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.w3c.dom.Document;


import java.io.*;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Main extends Application {
    public static Pane splashLayout;
    private ProgressBar loadProgress;
    private Label progressText;
    private WebView webView;
    private static Stage mainStage;
    private final static String APP_CONFIGURATION_FILE =  "App_configuration.xml";
    private static String JAR_PATH="";
    private String mCurrentPath="";

    private static String SERVER_URL="http://demo.mpower-social.com:8080/usermodule/update-log-time";
    //Sabbir
    public static boolean isSplashLoaded=false;
    public static long START_TIME=System.currentTimeMillis();


    public static boolean exit=false;

    @Override public void init() {
        AppLogger.getLoggerInstance().writeLog("*** In Main Init **",true);
        try {
            InitializeDatabase.get_instance();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        mCurrentPath=System.getProperty("user.dir");
        //initSplashScreen();
        initAppProperties();
        //JAR_PATH=getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
       // System.out.println("****jar path = " + JAR_PATH);
        //File file =new File(String.valueOf(this.getClass().getClassLoader().getResourceAsStream("test_snl_db.db")));
       // String dbSource="";
        //dbSource=file.getAbsolutePath();
       // System.out.println("@@@ absoultue path "+dbSource);
        //String source=getClass().getResourceAsStream("/resources/test_snl_db.db").toString();
        //UnZip.copy(JAR_PATH,dbSource);
      /*  try {
            InitializeDatabase.get_instance();
        } catch (SQLException e) {
            e.printStackTrace();
            AppLogger.getLoggerInstance().writeLog("Database Initialization failed: "+e.getMessage(),AppConfiguration.APPLICATION_DEBUG);
        }*/
    }

    private void initAppProperties() {
        //System.out.println("***initial value from xml***");
        Properties props = new Properties();

        InputStream is = null;
        try {
            is = new FileInputStream(mCurrentPath+"/"+APP_CONFIGURATION_FILE);
            //is = new FileInputStream("./"+APP_CONFIGURATION_FILE);//TODO ratn
           // System.out.println("***initial value from xml2222222222***");
            //load the xml file into properties format
            props.loadFromXML(is);
            //System.out.println("where props = "+ props);
            AppConfiguration.setValueFromProperties(props);
        } catch (Exception e) {
            e.printStackTrace();
            AppLogger.getLoggerInstance().writeLog("\n"+this.getClass().getName()+": "+e.getMessage(),AppConfiguration.APPLICATION_DEBUG);
        }
    }

    private void initSplashScreen() {
        /*ImageView splash = new ImageView(new Image("http://fxexperience.com/wp-content/uploads/2010/06/logo.png"));
        loadProgress = new ProgressBar();
        loadProgress.setPrefWidth(AppConfiguration.SPLASH_WIDTH - 20);
        progressText = new Label("Loading hobbits with pie . . .");*/
        try {
            AnchorPane anchorPane=FXMLLoader.load(getClass().getResource("/resources/fxml/splash_screen.fxml"));
            splashLayout = new VBox();
            splashLayout.getChildren().addAll(anchorPane, loadProgress, progressText);
            progressText.setAlignment(Pos.CENTER);
            splashLayout.setStyle("-fx-padding: 5; -fx-background-color: cornsilk; -fx-border-width:5; -fx-border-color: linear-gradient(to bottom, chocolate, derive(chocolate, 50%));");
            splashLayout.setEffect(new DropShadow());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        //showSplash(primaryStage);
        mainStage = primaryStage;
        //mainStage.sizeToScene();
        //mainStage.setAlwaysOnTop(true);
        //final boolean exit=false;
        mainStage.initStyle(StageStyle.DECORATED);
        mainStage.setMinWidth(640);
        mainStage.setMinHeight(500);
        mainStage.getIcons().add(new Image(Main.class.getResourceAsStream("/icon.png")));
        FxViewController.getInstance().showMainStage();
        mainStage.setOnCloseRequest(event -> {
            Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Application Closing");
            alert.setContentText("Do you really want to exit?");
            Optional<ButtonType> result=alert.showAndWait();
            if (result.get() ==ButtonType.OK){
                long duration=(System.currentTimeMillis()-START_TIME)/1000;
                Thread thread=new Thread(new Runnable() {
                    @Override
                    public void run() {
                       sendTimeToServer(duration);
                    }
                });
                thread.start();
                //sendTimeToServer(duration);
                System.out.println("** Current time "+duration);
                if (exit)  Platform.exit();
                else thread.run();
            }else if (result.get()==ButtonType.CANCEL){
                event.consume();
                alert.close();
            }
        });

    }

    private void sendTimeToServer(long duration) {
        HttpClient httpClient= HttpClients.createDefault();
        HttpPost httpPost=new HttpPost(SERVER_URL);

        exit=true;

    }

    /*private void showMainStage(){
        //mainStage = new Stage(StageStyle.DECORATED);
        mainStage.setTitle(AppConfiguration.APPLICATION_NAME);
        mainStage.setIconified(true);
        Parent root = null;
        // create a WebView.
       *//* webView = new WebView();
        webView.getEngine().load("http://fxexperience.com/");
        loadProgress.progressProperty().bind(webView.getEngine().getLoadWorker().workDoneProperty().divide(100));*//*
        try {
            root = FXMLLoader.load(getClass().getResource("/resources/fxml/registration.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        mainStage.setScene(new Scene(root, AppConfiguration.SCREEN_WIDTH, AppConfiguration.SCREEN_HEIGHT));
        mainStage.show();
    }*/

    public static void showSplash(Stage initStage) {
        Scene splashScene = new Scene(splashLayout);
        initStage.initStyle(StageStyle.UNDECORATED);
        final Rectangle2D bounds = Screen.getPrimary().getBounds();
        initStage.setScene(splashScene);
        initStage.setX(bounds.getMinX() + bounds.getWidth() / 2 - AppConfiguration.SPLASH_WIDTH / 2);
        initStage.setY(bounds.getMinY() + bounds.getHeight() / 2 - AppConfiguration.SPLASH_HEIGHT / 2);
        initStage.show();
    }
    public static Stage getMainStage(){
        return mainStage;
    }

    public static void main(String[] args) {
        launch(args);
    }


    //Sabbir
    public static class UnZip {

        public static void copy(String path,String sorce) {
           /* String destDir = "/tmp/";
            String sourceJar = "your_src.jar";*/

            String destDir = path;
            String sourceJar = sorce;

            try (ZipInputStream zis = new ZipInputStream(new BufferedInputStream(new FileInputStream(sourceJar)))) {
                ZipEntry zipEntry;
                while ((zipEntry = zis.getNextEntry()) != null) {
                    File newDestination = new File(destDir + zipEntry.getName());
                    if (zipEntry.isDirectory()) {
                        unzipDir(newDestination);
                    } else {
                        unzipFile(newDestination, zis);
                    }
                }
            } catch (IOException ex) {
                System.err.println("input file coud not be read " + ex.getMessage());
            }
        }

        private static void unzipFile(File file, final ZipInputStream zis) {
            System.out.printf("extract to: %s - ", file.getAbsoluteFile());
            if (file.exists()) {
                System.out.println("already exist");
                return;
            }
            int count;
            try (BufferedOutputStream dest = new BufferedOutputStream(new FileOutputStream(file), BUFFER_SIZE)) {
                while ((count = zis.read(BUFFER, 0, BUFFER_SIZE)) != -1) {
                    dest.write(BUFFER, 0, count);
                }
                dest.flush();
                System.out.println("");
            } catch (IOException ex) {
                System.err.println("file could not be created " + ex.getMessage());
            }
        }

        private static void unzipDir(File dir) {
            System.out.printf("create directory: %s - ", dir);
            if (dir.exists()) {
                System.out.println("already exist");
            } else if (dir.mkdirs()) {
                System.out.println("successful");
            } else {
                System.out.println("failed");
            }
        }

        static final int BUFFER_SIZE = 2048;
        static byte[] BUFFER = new byte[BUFFER_SIZE];
    }
}
