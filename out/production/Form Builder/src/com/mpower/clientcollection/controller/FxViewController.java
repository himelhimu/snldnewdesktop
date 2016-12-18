package com.mpower.clientcollection.controller;

/**
 * Created by hemel on 4/11/16.
 */

import com.mpower.clientcollection.widgets.DragDropWidget;
import com.mpower.clientcollection.widgets.PictureSelectWidget;
import com.mpower.clientcollection.widgets.ReArrangeWidget;
import com.mpower.desktop.config.AppConfiguration;
import com.mpower.desktop.config.AppLogger;
import com.mpower.desktop.constants.Constants;
import com.mpower.desktop.controller.ContentViewController;
import com.mpower.desktop.database.InitializeDatabase;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.*;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Screen;
import javafx.stage.Stage;

import javafx.stage.StageStyle;
import javafx.util.Duration;
import org.javarosa.form.api.FormEntryModel;
import sample.Main;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static sample.Main.showSplash;
import static sample.Main.splashLayout;


public class FxViewController implements Initializable {

    private static FxViewController _VCInstance = null;


    //UI related variables
    private Stage curStage;
    Parent root = null;

    private ScrollPane scrollPane;
    private GridPane mGridMainLayout;
    @FXML
    private Pane splashlayout;
    private Pane splashPane;
    //operation Variables
    //public boolean INITIALIZED = false;

    private FxViewController(){
        this.curStage =  Main.getMainStage();
        //if (!isExam)
        setCurrentLayout();
        //else
        //setCurrentLayoutExam();
        AppLogger.getLoggerInstance().writeLog("\n"+getClass().getName()+": Initialized",AppConfiguration.APPLICATION_DEBUG);
    }

    public void showCurStage(){
        this.curStage.show();
    }

    public Stage getCurStage(){return this.curStage;}

    private void setCurrentLayout() {

        mGridMainLayout = new GridPane();
        //mGridMainLayout.setAlignment(Pos.CENTER);
        mGridMainLayout.setHgap(5);
        mGridMainLayout.setVgap(5);
        mGridMainLayout.setPadding(new Insets(5));
       // mGridMainLayout.setScaleShape(true);



        scrollPane=new ScrollPane();
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setContent(mGridMainLayout);

        /*anchorPane = new AnchorPane();*//*
        scrollPane.setAlignment(Pos.CENTER);
        scrollPane.setHgap(5);
        scrollPane.setVgap(5);*//*
        anchorPane.setPadding(new Insets(5));*/
        /*ColumnConstraints column1 = new ColumnConstraints(100);
        ColumnConstraints column2 = new ColumnConstraints(50, 150, 300);
        column2.setHgrow(Priority.ALWAYS);
        mGridMainLayout.getColumnConstraints().addAll(column1, column2);*/
    }

    private void addToMainLayout() {
        scrollPane=new ScrollPane();
        scrollPane.setPrefSize(600,600);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setContent(mGridMainLayout);
        //scrollPane.setFitToWidth(true);
        //scrollPane.setFitToHeight(true);
        //scrollPane.setPannable(true);
        //scrollPane.getChildrenUnmodifiable().setAll(mGridMainLayout);
    }
    /*public GridPane getCurrentLayout(){
        return mGridMainLayout;
    }*/

    public GridPane getCurrentLayout()
    {
        return mGridMainLayout;
    }

    public void setCurrentView(String title,AppConfiguration.VIEW_TYPE view_type) {
        curStage.setTitle(title);

        if( view_type == AppConfiguration.VIEW_TYPE.FORM_VIEW ) {
            showFormViewStage(title);
        }
        else if( view_type == AppConfiguration.VIEW_TYPE.REG_VIEW){
            showMainStage();
        }
        else if ( view_type == AppConfiguration.VIEW_TYPE.LOGIN_VIEW ){
            showLoginStage();
        }
        else if ( view_type == AppConfiguration.VIEW_TYPE.INTRO_VIEW ){
            showIntroStage();
        }
        else if ( view_type == AppConfiguration.VIEW_TYPE.COURSE_OVERVIEW ){
            showCourseOverviewStage();
        }else if (view_type==AppConfiguration.VIEW_TYPE.TEST){
            showTestStage();
        }
    }

    private void showTestStage() {
      /*  Parent tempRoot=null;
        try {
            tempRoot=FXMLLoader.load(getClass().getResource(AppConfiguration.FXML_PATH+"test.fxml"));


        } catch (IOException e) {
            e.printStackTrace();
        }
        if (tempRoot!=null) {
            //DragDropWidget dragDropWidgetNew=new DragDropWidget();
            *//*ReArrangeWidget reArrangeWidget=new ReArrangeWidget();
            reArrangeWidget.setImages();*//*
            Scene scene=new Scene(tempRoot,AppConfiguration.SCREEN_WIDTH,AppConfiguration.SCREEN_HEIGHT);
            this.curStage.setScene(scene);
            showCurStage();
        }else {
            //TODO
        }*/
        PictureSelectWidget pictureSelectWidget=new PictureSelectWidget();
        FXMLLoader fxmlLoader=new FXMLLoader(PictureSelectWidget.class.getResource(AppConfiguration.FXML_PATH+"htmlview.fxml"));
        fxmlLoader.setController(pictureSelectWidget);
        /*try {
            pictureSelectWidget.mGridPane=fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        Scene scene=new Scene(pictureSelectWidget.mGridPane,AppConfiguration.SCREEN_WIDTH,AppConfiguration.SCREEN_HEIGHT);
        this.curStage.setScene(scene);
        showCurStage();


    }

    private void showFormViewStage(String title) {
      //  mGridMainLayout = null;
        setCurrentLayout();
        Scene scene = new Scene(scrollPane, AppConfiguration.SCREEN_WIDTH, AppConfiguration.SCREEN_HEIGHT);
        curStage.setScene(scene);


        FormViewController fvc = FormViewController.getInstance();
        fvc.clearInstance();
        boolean isLoaded = false;
        String xml_path = "";
        if(title.equals("1_1")){
            xml_path = AppConfiguration.FORM_XML_PATH+Constants.FIRST_SESSION_FIRST_CHAPTER;
            System.out.println("Form Path:"+xml_path);
        }else if(title.equals("1_2")) {
            xml_path = AppConfiguration.FORM_XML_PATH+Constants.SECOND_SESSION_FIRST_CHAPTER;
        }else{
            xml_path = AppConfiguration.FORM_XML_PATH+Constants.EXAM_1ST;
        }

        isLoaded = fvc.loadformFromXML(xml_path);
        if(isLoaded)
        {
            FormEntryModel currModel = fvc.getCurrentModel();
            if(currModel != null && fvc.isFormValid(currModel,xml_path))
            {
                AppLogger.getLoggerInstance().writeLog("\n"+this.getClass().getName()+": Successfully load: "+ xml_path,AppConfiguration.APPLICATION_DEBUG);
            }
        }
    }

  /*  private void clearCurrentLayout() {
       FxViewController.getInstance().getCurrentLayout().clearConstraints(getCurrentLayout());
    }*/

    private void setCurrentLayoutExam() {
        scrollPane=new ScrollPane();
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);

    }

    private void showCourseOverviewStage() {
        curStage.setTitle(AppConfiguration.COURSE_OVERVIEW_WINDOW);
        Parent tmpRoot = null;
        try{
            tmpRoot = FXMLLoader.load(getClass().getResource(AppConfiguration.FXML_PATH+"chapter_content.fxml"));
            String currProg = InitializeDatabase.get_instance().getCurrUserProgress(ContentViewController.current_user);

            if( currProg != "" ){
                int chapter = Integer.parseInt(currProg.split("_")[0]);
                int session = Integer.parseInt(currProg.split("_")[1]);
                Image unlock_image = new javafx.scene.image.Image(Main.class.getResourceAsStream(AppConfiguration.IMG_PATH+"unlock.jpg"));
                for(int i=1;i<=chapter;i++){
                    for(int j=1;j<=session+1;j++){
                        Node progressImage = tmpRoot.lookup("#session_"+i+"_"+j);
                        if(progressImage != null){
                            ((ImageView) progressImage).setImage(unlock_image);
                            progressImage.setDisable(false);
                        }
                    }
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        this.curStage.setScene(new Scene(tmpRoot, AppConfiguration.SCREEN_WIDTH, AppConfiguration.SCREEN_HEIGHT));

        showCurStage();
    }

    private void showIntroStage(){
        curStage.setTitle(AppConfiguration.INTRO_WINDOW);
        Parent tmproot = null;
        try {

           tmproot = FXMLLoader.load(getClass().getResource(AppConfiguration.FXML_PATH+"introductory_video.fxml"));
            Media intro_vid = new Media(getClass().getResource(AppConfiguration.VIDEO_PATH+"test_video.mp4").toExternalForm());
            MediaPlayer player = new MediaPlayer(intro_vid);
            player.play();
            Node mediaviewNode = tmproot.lookup("#intro_media_view");
            ((MediaView) mediaviewNode).setMediaPlayer(player);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.curStage.setScene(new Scene(tmproot, AppConfiguration.SCREEN_WIDTH, AppConfiguration.SCREEN_HEIGHT));
        showCurStage();
    }

    private void showLoginStage(){
       curStage.setTitle(AppConfiguration.LOGIN_NAME);
        try {
            root = FXMLLoader.load(getClass().getResource(AppConfiguration.FXML_PATH+"Login.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.curStage.setScene(new Scene(root, AppConfiguration.SCREEN_WIDTH, AppConfiguration.SCREEN_HEIGHT));
        showCurStage();

    }

    public void showMainStage(){
        if (!Main.isSplashLoaded) {
            //loadSplashScreen();
            /*Stage splashStage=new Stage();
            showSplash(splashStage);
            Main.isSplashLoaded=true;*/
        }else
        curStage.setTitle(AppConfiguration.APPLICATION_NAME);
        //curStage.setIconified(true);
        // create a WebView.
       /* webView = new WebView();
        webView.getEngine().load("http://fxexperience.com/");
        loadProgress.progressProperty().bind(webView.getEngine().getLoadWorker().workDoneProperty().divide(100));*/
        try {
            root = FXMLLoader.load(getClass().getResource(AppConfiguration.FXML_PATH+"registration.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.curStage.setScene(new Scene(root, AppConfiguration.SCREEN_WIDTH, AppConfiguration.SCREEN_HEIGHT));
        //curStage.setMinWidth(750);
        showCurStage();
    }

    //Sabbir
    private void loadSplashScreen() {
        try {
            splashPane= FXMLLoader.load(getClass().getResource("/resources/fxml/splash_screen.fxml"));
            //root.getChildrenUnmodifiable().setAll(splashLayout);
            mGridMainLayout.getChildren().addAll(splashPane);


            FadeTransition fadeIn=new FadeTransition(Duration.seconds(10),splashPane);
            fadeIn.setFromValue(0);
            fadeIn.setToValue(1);
            fadeIn.setCycleCount(1);

            FadeTransition fadeOut=new FadeTransition(Duration.seconds(10),splashPane);
            fadeOut.setFromValue(1);
            fadeOut.setToValue(0);
            fadeOut.setCycleCount(1);

            fadeIn.play();

            fadeIn.setOnFinished(event -> {
                System.out.println("Inside fade in");
                fadeOut.play();
                //FxViewController.getInstance().showMainStage();

            });

            fadeOut.setOnFinished(event -> {
                System.out.println("inside fadeout######");
                FxViewController.getInstance().showMainStage();
            });

            /*Scene splashScene=new Scene(mGridMainLayout,AppConfiguration.SCREEN_WIDTH,AppConfiguration.SCREEN_HEIGHT);
            this.curStage.setScene(splashScene);
            curStage.show();*/
            Main.isSplashLoaded=true;
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //Sabbir

   /* private void showSplash(Stage initStage) {
        Scene splashScene = new Scene(splashLayout);
        initStage.initStyle(StageStyle.UNDECORATED);
        final Rectangle2D bounds = Screen.getPrimary().getBounds();
        initStage.setScene(splashScene);
        initStage.setX(bounds.getMinX() + bounds.getWidth() / 2 - AppConfiguration.SPLASH_WIDTH / 2);
        initStage.setY(bounds.getMinY() + bounds.getHeight() / 2 - AppConfiguration.SPLASH_HEIGHT / 2);
        initStage.show();
    }*/


    public static FxViewController getInstance(){
        if (_VCInstance == null)
            _VCInstance = new FxViewController();
        return _VCInstance;
    }
    public static void destroyInstance(){
        if(_VCInstance != null)
            _VCInstance = null;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        AnchorPane anchorPane=new AnchorPane(splashPane);
        //loadSplashScreen(splashPane,curStage);
    }
}
