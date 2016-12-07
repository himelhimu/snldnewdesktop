package sample;

import com.mpower.clientcollection.controller.FxViewController;
import com.mpower.desktop.config.AppConfiguration;
import com.mpower.desktop.config.AppLogger;
import com.mpower.desktop.database.InitializeDatabase;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import org.w3c.dom.Document;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Properties;

public class Main extends Application {
    private static Pane splashLayout;
    private ProgressBar loadProgress;
    private Label progressText;
    private WebView webView;
    private static Stage mainStage;
    private final static String APP_CONFIGURATION_FILE =  "App_configuration.xml";
    public static boolean isSpalshLoaded=false;



    @Override public void init() {
        //initSplashScreen();
        initAppProperties();
        try {
            InitializeDatabase.get_instance();
        } catch (SQLException e) {
            AppLogger.getLoggerInstance().writeLog("Database Initialization failed: "+e.getMessage(),AppConfiguration.APPLICATION_DEBUG);
        }
    }

    private void initAppProperties() {
        Properties props = new Properties();

        InputStream is = null;
        try {
            is = new FileInputStream("./"+APP_CONFIGURATION_FILE);
            //load the xml file into properties format
            props.loadFromXML(is);
            AppConfiguration.setValueFromProperties(props);
        } catch (Exception e) {
            AppLogger.getLoggerInstance().writeLog("\n"+this.getClass().getName()+": "+e.getMessage(),AppConfiguration.APPLICATION_DEBUG);
        }
    }

    private void initSplashScreen() {
        ImageView splash = new ImageView(new Image("http://fxexperience.com/wp-content/uploads/2010/06/logo.png"));
        loadProgress = new ProgressBar();
        loadProgress.setPrefWidth(AppConfiguration.SPLASH_WIDTH - 20);
        progressText = new Label("Loading hobbits with pie . . .");
        splashLayout = new VBox();
        splashLayout.getChildren().addAll(splash, loadProgress, progressText);
        progressText.setAlignment(Pos.CENTER);
        splashLayout.setStyle("-fx-padding: 5; -fx-background-color: cornsilk; -fx-border-width:5; -fx-border-color: linear-gradient(to bottom, chocolate, derive(chocolate, 50%));");
        splashLayout.setEffect(new DropShadow());
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
       // showSplash(primaryStage);
        mainStage = primaryStage;
        mainStage.setAlwaysOnTop(false);
        mainStage.initStyle(StageStyle.DECORATED);
        FxViewController.getInstance().showMainStage();
        //showMainStage();

        /*webView.getEngine().documentProperty().addListener(new ChangeListener<Document>() {
            @Override public void changed(ObservableValue<? extends Document> observableValue, Document document, Document document1) {
                if (primaryStage.isShowing()) {
                    loadProgress.progressProperty().unbind();
                    loadProgress.setProgress(1);
                    progressText.setText("All hobbits are full.");
                    mainStage.setIconified(false);
                    primaryStage.toFront();
                    FadeTransition fadeSplash = new FadeTransition(Duration.seconds(1.2), splashLayout);
                    fadeSplash.setFromValue(1.0);
                    fadeSplash.setToValue(0.0);
                    fadeSplash.setOnFinished(new EventHandler<ActionEvent>() {
                        @Override public void handle(ActionEvent actionEvent) {
                            primaryStage.hide();
                        }
                    });
                    fadeSplash.play();
                }
            }
        });*/

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
}