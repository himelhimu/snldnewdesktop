package com.mpower.clientcollection.controller;

/**
 * Created by hemel on 4/11/16.
 * @author sabbir sabbir@mpower-social.com
 */

import com.mpower.desktop.config.AppConfiguration;
import com.mpower.desktop.config.AppLogger;
import com.mpower.desktop.constants.Constants;
import com.mpower.desktop.controller.ContentViewController;
import com.mpower.desktop.controller.LoginController;
import com.mpower.desktop.controller.RegistrationController;
import com.mpower.desktop.database.InitializeDatabase;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.*;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

import javafx.util.Duration;
import org.javarosa.form.api.FormEntryModel;
import sample.Main;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


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
        setCurrentLayout();
        AppLogger.getLoggerInstance().writeLog("\n"+getClass().getName()+": Initialized",AppConfiguration.APPLICATION_DEBUG);
    }

    public void showCurStage(){
        this.curStage.setResizable(true);
        this.curStage.show();
    }

    public Stage getCurStage(){return this.curStage;}

    private void setCurrentLayout() {

        mGridMainLayout = new GridPane();
        //mGridMainLayout.setAlignment(Pos.CENTER);
        mGridMainLayout.setHgap(5);
        mGridMainLayout.setVgap(5);
        mGridMainLayout.setPadding(new Insets(20));
        mGridMainLayout.setScaleShape(true);



        scrollPane=new ScrollPane();
        scrollPane.setPrefHeight(600);
        scrollPane.setPrefWidth(700);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setContent(mGridMainLayout);

    }


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
        }
    }


    private void showFormViewStage(String title) {
        mGridMainLayout = null;
        setCurrentLayout();
        Scene scene = new Scene(scrollPane);
        curStage.setScene(scene);


        FormViewController fvc = FormViewController.getInstance();
        fvc.clearInstance();
        boolean isLoaded = false;
        String xml_path = "";
        switch (title) {
            case "1_1":
                xml_path = Constants.FIRST_SESSION_FIRST_CHAPTER_EN;
                System.out.println("Form Path:" + xml_path);
                break;
            case "1_2":
               xml_path = Constants.SECOND_SESSION_FIRST_CHAPTER_EN;
                break;
            case "1_3":
                xml_path = Constants.THIRD_SESSION_FIRST_CHAPTER_EN;
                break;
            case "1_4":
                xml_path = Constants.FOURTH_SESSION_FIRST_CHAPTER_EN;
                break;
            case "1_5":
                xml_path = Constants.FIFTH_SESSION_FIRST_CHAPTER_EN;
                break;
            case "1_6":
                xml_path = Constants.SIXTH_SESSION_FIRST_CHAPTER_EN;
                break;
            case "1_7":
                xml_path = Constants.SEVENTH_SESSION_FIRST_CHAPTER_EN;
                break;
            case "1_8":
                xml_path = Constants.EIGHTH_SESSION_FIRST_CHAPTER_EN;
                break;
            case "1_9":
                xml_path = Constants.NINETH_SESSION_FIRST_CHAPTER_EN;
                break;
            case "1_10":
                xml_path = Constants.TENTH_SESSION_FIRST_CHAPTER_EN;
                break;
            case "1_11":
                xml_path = Constants.ELEVENTH_SESSION_FIRST_CHAPTER_EN;
                break;
            case "1_12":
                xml_path = Constants.TWELVTH_SESSION_FIRST_CHAPTER_EN;
                break;
            case "quiz_1_1":
                xml_path = Constants.EXAM_1ST;
                break;
            case "2_1":
                xml_path = Constants.FIRST_SESSION_SECOND_CHAPTER_EN;
                break;
            case "2_2":
                xml_path = Constants.SECOND_SESSION_SECOND_CHAPTER_EN;
                break;
            case "2_3":
                xml_path = Constants.THIRD_SESSION_SECOND_CHAPTER_EN;
                break;
            case "2_4":
                xml_path = Constants.FOURTH_SESSION_SECOND_CHAPTER_EN;
                break;
            case "2_5":
                xml_path = Constants.FIFTH_SESSION_SECOND_CHAPTER_EN;
                break;
            case "2_6":
                xml_path = Constants.SIXTH_SESSION_SECOND_CHAPTER_EN;
                break;
            case "2_7":
                xml_path = Constants.SEVENTH_SESSION_SECOND_CHAPTER_EN;
                break;
            case "2_8":
                xml_path = Constants.EIGHT_SESSION_SECOND_CHAPTER_EN;
                break;
            case "2_9":
                xml_path = Constants.NINE_SESSION_SECOND_CHAPTER_EN;
                break;
            case "2_10":
                xml_path = Constants.TENTH_SESSION_SECOND_CHAPTER_EN;
                break;
            case "2_11":
                xml_path = Constants.ELEVEN_SESSION_SECOND_CHAPTER_EN;
                break;
            case "2_12":
                xml_path = Constants.TWELVE_SESSION_SECOND_CHAPTER_EN;
                break;
            case "quiz_2_1":
                xml_path = Constants.FIRST_EXAM_SECOND_CHAPTER_EN;
                break;
            case "2_14":
                xml_path = Constants.THIRTEEN_SESSION_SECOND_CHAPTER_EN;
                break;
            case "2_15":
                xml_path = Constants.FOURTEEN_SESSION_SECOND_CHAPTER_EN;
                break;
            case "2_16":
                xml_path = Constants.FIFTEEN_SESSION_SECOND_CHAPTER_EN;
                break;
            case "2_17":
                xml_path = Constants.SIXTEEN_SESSION_SECOND_CHAPTER_EN;
                break;
            case "2_18":
                xml_path = Constants.SEVENTEEN_SESSION_SECOND_CHAPTER_EN;
                break;
            case "2_19":
                xml_path = Constants.EIGHTEEN_SESSION_SECOND_CHAPTER_EN;
                break;
            case "2_20":
                xml_path = Constants.NINETEEN_SESSION_SECOND_CHAPTER_EN;
                break;
            case "2_21":
                xml_path = Constants.TWENTY_SESSION_SECOND_CHAPTTER_EN;
                break;
            case "2_22":
                xml_path = Constants.TWENTY_ONE_SESSION_SECOND_CHAPTER_EN;
                break;
            case "2_23":
                xml_path = Constants.TWENTY_TWO_SESSION_CHAPTER_EN;
                break;
            case "quiz_2_2":
                xml_path = Constants.SECOND_EXAM_SECOND_CHAPTER_EN;
                break;
            case "3_1":
                xml_path = Constants.FIRST_SESSION_THIRD_CHAPTER_EN;
                break;
            case "3_2":
                xml_path = Constants.SECOND_SESSION_THIRD_CHAPTER_EN;
                break;
            case "quiz_3_1":
                xml_path = Constants.EXAM_SESSION_THIRD_CHAPTER_EN;
                break;
        }

        //TODO
        fvc.setCurrentFormName(xml_path);
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




    private void showCourseOverviewStage() {
        curStage.setTitle(AppConfiguration.COURSE_OVERVIEW_WINDOW);
        Parent tmpRoot = null;
        boolean removeENfromImageName=false;
        String englishChapterContent="/chapter_content_new.fxml";
        String banglaChapterContent="/bangla_chapter_content.fxml";
        String ENimageNameSuffix="_l_en.png";
        String BNimageNameSuffix="_l.png";
        //String chapterImageName="";
        try{
            if (LoginController.USER_TYPE==0 || LoginController.USER_TYPE==1){
                 removeENfromImageName=false;
                 tmpRoot = FXMLLoader.load(getClass().getResource(englishChapterContent));
            }else {
                 removeENfromImageName=true;
                 tmpRoot = FXMLLoader.load(getClass().getResource(banglaChapterContent));
            }

            String currProg = InitializeDatabase.get_instance().getCurrUserProgress(ContentViewController.current_user);

            if( currProg != "" ){
                int chapter = Integer.parseInt(currProg.split("_")[0]);
                int session = Integer.parseInt(currProg.split("_")[1]);

                //System.out.println("** ChapterImageName "+chapterImageName1);
               // Image unlock_image1 = new javafx.scene.image.Image(this.getClass().getClassLoader().getResourceAsStream(chapterImageName1));
                for(int i=1;i<=chapter;i++){
                    for(int j=1;j<=session+1;j++){
                        Node progressImage = tmpRoot.lookup("#session_"+i+"_"+j);
                        if(progressImage != null){
                            if (!removeENfromImageName){

                                //String chapterImageName1="ch_"+String.valueOf(chapter)+"_"+String.valueOf(session)+"_l_en.png";
                              String   chapterImageName="ch_"+String.valueOf(i)+"_"+String.valueOf(j)+ENimageNameSuffix;
                                System.out.println("*** ChapterImageName "+chapterImageName);
                                Image unlock_image = new javafx.scene.image.Image(this.getClass().getClassLoader().getResourceAsStream(chapterImageName));
                                ((ImageView) progressImage).setImage(unlock_image);
                                progressImage.setDisable(false);
                            }else {
                                String chapterImageName="ch_"+String.valueOf(i)+"_"+String.valueOf(j)+BNimageNameSuffix;
                                System.out.println("*** ChapterImageName "+chapterImageName);
                                Image unlock_image = new javafx.scene.image.Image(this.getClass().getClassLoader().getResourceAsStream(chapterImageName));
                                ((ImageView) progressImage).setImage(unlock_image);
                                progressImage.setDisable(false);
                            }
                           /* String chapterImageName="ch_"+String.valueOf(i)+"_"+String.valueOf(j)+"_l_en.png";
                            System.out.println("** ChapterImageName "+chapterImageName);
                            Image unlock_image = new javafx.scene.image.Image(this.getClass().getClassLoader().getResourceAsStream(chapterImageName));
                            ((ImageView) progressImage).setImage(unlock_image);
                            progressImage.setDisable(false);*/
                        }
                    }
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        assert tmpRoot != null;
        ScrollPane scrollPane1=new ScrollPane(tmpRoot);
        scrollPane1.setPrefHeight(600);
        this.curStage.setScene(new Scene(scrollPane1));

        showCurStage();
    }

    private void showIntroStage(){
        curStage.setTitle(AppConfiguration.INTRO_WINDOW);
        Parent tmproot = null;
        try {

            tmproot = FXMLLoader.load(getClass().getResource("/introductory_video.fxml"));
            //Media intro_vid = new Media(getClass().getResource("/intro.mp4").toExternalForm());
            Media intro_vid = new Media(getClass().getResource("/intro.mp4").toExternalForm());
            System.out.println("**Video Path "+intro_vid);
            MediaPlayer player = new MediaPlayer(intro_vid);
            player.play();
            Node mediaviewNode = tmproot.lookup("#intro_media_view");
            ((MediaView) mediaviewNode).setMediaPlayer(player);
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert tmproot != null;
        this.curStage.setScene(new Scene(tmproot));
        showCurStage();
    }

    private void showLoginStage(){
       curStage.setTitle(AppConfiguration.LOGIN_NAME);
        try {
            root = FXMLLoader.load(getClass().getResource("/Login.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.curStage.setScene(new Scene(root));
        showCurStage();

    }

    public void showMainStage(){
        curStage.setTitle(AppConfiguration.APPLICATION_NAME);
        curStage.setIconified(true);
        try {
           // root = FXMLLoader.load(getClass().getResource(AppConfiguration.FXML_PATH+"registration.fxml"));
            root = FXMLLoader.load(getClass().getResource("/registration.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //this.curStage.setScene(new Scene(root, AppConfiguration.SCREEN_WIDTH, AppConfiguration.SCREEN_HEIGHT));
        this.curStage.setScene(new Scene(root));
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
        //AnchorPane anchorPane=new AnchorPane(splashPane);
        //loadSplashScreen(splashPane,curStage);
    }
}
