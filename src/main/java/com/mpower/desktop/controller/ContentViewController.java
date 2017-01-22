package com.mpower.desktop.controller;

import com.mpower.clientcollection.controller.FxViewController;
import com.mpower.desktop.config.AppConfiguration;
import com.mpower.desktop.database.InitializeDatabase;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import sample.Main;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by hemel on 4/24/16.
 * @author sabbir sabbir@mpower-social.com
 */
public class ContentViewController {
    @FXML
    private ImageView mImageview1;
    @FXML
    private ImageView mImageview2;
    @FXML
    private ImageView mImageview3;
    @FXML
    private ImageView mImageview4;
    @FXML
    private ImageView mImageview5;
    @FXML
    private ImageView mImageview6;
    @FXML
    private ImageView mImageview7;
    @FXML
    private ImageView mImageview8;

    public static String current_user = "";
    public static String current_session = "1_0";
    public static boolean isBanglaContent=false;
    String currProg="";
    public static boolean isEnd=false;

    public void onMouseClicked(Event event) {
        try {
            currProg = InitializeDatabase.get_instance().getCurrUserProgress(ContentViewController.current_user);
            System.out.println("#### current progress " + currProg);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        current_session = currProg;
        {
            isEnd = current_session == "3_33" || current_session == "3_42";
        }
        isBanglaContent = LoginController.USER_TYPE == 2 || LoginController.USER_TYPE == 3;
        System.out.println("### current session " + current_session);
        if (event.getSource() instanceof ImageView) {
            String imageViewID = ((ImageView) event.getSource()).getId();
            switch (imageViewID) {
                case "session_1_1":
                    current_session = "1_1";
                    FxViewController.getInstance().setCurrentView("1_1", AppConfiguration.VIEW_TYPE.FORM_VIEW);
                    //System.out.print("first Image clicked." + current_user);

                    break;
                case "session_1_2":
                    current_session = "1_2";
                    FxViewController.getInstance().setCurrentView("1_2", AppConfiguration.VIEW_TYPE.FORM_VIEW);
                    //System.out.print("second Image clicked." + current_user);
                    break;
                case "session_1_3":
                    current_session = "1_3";
                    FxViewController.getInstance().setCurrentView("1_3", AppConfiguration.VIEW_TYPE.FORM_VIEW);
                    //System.out.print("Third Image clicked." + current_user);
                    break;
                case "session_1_4":
                    current_session = "1_4";
                    FxViewController.getInstance().setCurrentView("1_4", AppConfiguration.VIEW_TYPE.FORM_VIEW);
                    //System.out.print("Fourth Image clicked." + current_user);
                    break;
                case "session_1_5":
                    current_session = "1_5";
                    FxViewController.getInstance().setCurrentView("1_5", AppConfiguration.VIEW_TYPE.FORM_VIEW);
                    //System.out.print("Fifth Image clicked." + current_user);
                    break;
                case "session_1_6":
                    current_session = "1_6";
                    FxViewController.getInstance().setCurrentView("1_6", AppConfiguration.VIEW_TYPE.FORM_VIEW);
                    //System.out.print("sixth Image clicked." + current_user);
                    break;
                case "session_1_7":
                    if (isBanglaContent) {
                        current_session = "2_0";
                        FxViewController.getInstance().setCurrentView("quiz_1_1", AppConfiguration.VIEW_TYPE.FORM_VIEW);
                        break;
                    } else
                        current_session = "1_7";
                    FxViewController.getInstance().setCurrentView("1_7", AppConfiguration.VIEW_TYPE.FORM_VIEW);
                    //System.out.print("Seventh Image clicked." + current_user);
                    break;
                case "session_1_8":
                    current_session = "1_8";
                    FxViewController.getInstance().setCurrentView("1_8", AppConfiguration.VIEW_TYPE.FORM_VIEW);
                    //System.out.print("Seventh Image clicked." + current_user);
                    break;
                case "session_1_9":
                    current_session = "1_9";
                    FxViewController.getInstance().setCurrentView("1_9", AppConfiguration.VIEW_TYPE.FORM_VIEW);
                    //System.out.print("Seventh Image clicked." + current_user);
                    break;
                case "session_1_10":
                    current_session = "1_10";
                    FxViewController.getInstance().setCurrentView("1_10", AppConfiguration.VIEW_TYPE.FORM_VIEW);
                    //System.out.print("Seventh Image clicked." + current_user);
                    break;
                case "session_1_11":
                    current_session = "1_11";
                    FxViewController.getInstance().setCurrentView("1_11", AppConfiguration.VIEW_TYPE.FORM_VIEW);
                    //System.out.print("Seventh Image clicked." + current_user);
                    break;
                case "session_1_12":
                    current_session = "1_12";
                    FxViewController.getInstance().setCurrentView("1_12", AppConfiguration.VIEW_TYPE.FORM_VIEW);
                    //System.out.print("Seventh Image clicked." + current_user);
                    break;
                case "session_1_13":
                    current_session = "2_0";
                    FxViewController.getInstance().setCurrentView("quiz_1_1", AppConfiguration.VIEW_TYPE.FORM_VIEW);
                    break;

                case "session_1_quiz":
                    current_session = "2_0";
                    FxViewController.getInstance().setCurrentView("quiz_1_1", AppConfiguration.VIEW_TYPE.FORM_VIEW);
                    break;
                case "session_2_1":
                    current_session = "2_1";
                    FxViewController.getInstance().setCurrentView("2_1", AppConfiguration.VIEW_TYPE.FORM_VIEW);
                    //System.out.print("second Image clicked." + current_user);
                    break;
                case "session_2_2":
                    current_session = "2_2";
                    FxViewController.getInstance().setCurrentView("2_2", AppConfiguration.VIEW_TYPE.FORM_VIEW);
                    //System.out.print("Third Image clicked." + current_user);
                    break;
                case "session_2_3":
                    current_session = "2_3";
                    FxViewController.getInstance().setCurrentView("2_3", AppConfiguration.VIEW_TYPE.FORM_VIEW);
                    //System.out.print("Fourth Image clicked." + current_user);
                    break;
                case "session_2_4":
                    current_session = "2_4";
                    FxViewController.getInstance().setCurrentView("2_4", AppConfiguration.VIEW_TYPE.FORM_VIEW);
                    //System.out.print("Fifth Image clicked." + current_user);
                    break;
                case "session_2_5":
                    current_session = "2_5";
                    FxViewController.getInstance().setCurrentView("2_5", AppConfiguration.VIEW_TYPE.FORM_VIEW);
                    //System.out.print("sixth Image clicked." + current_user);
                    break;
                case "session_2_6":
                    current_session = "2_6";
                    FxViewController.getInstance().setCurrentView("2_6", AppConfiguration.VIEW_TYPE.FORM_VIEW);
                    //System.out.print("Seventh Image clicked." + current_user);
                    break;
                case "session_2_7":
                    current_session = "2_7";
                    FxViewController.getInstance().setCurrentView("2_7", AppConfiguration.VIEW_TYPE.FORM_VIEW);
                    //System.out.print("Seventh Image clicked." + current_user);
                    break;
                case "session_2_8":
                    current_session = "2_8";
                    FxViewController.getInstance().setCurrentView("2_8", AppConfiguration.VIEW_TYPE.FORM_VIEW);
                    //System.out.print("second Image clicked." + current_user);
                    break;
                case "session_2_9":
                    current_session = "2_9";
                    FxViewController.getInstance().setCurrentView("2_9", AppConfiguration.VIEW_TYPE.FORM_VIEW);
                    //System.out.print("Third Image clicked." + current_user);
                    break;
                case "session_2_10":
                    current_session = "2_10";
                    FxViewController.getInstance().setCurrentView("2_10", AppConfiguration.VIEW_TYPE.FORM_VIEW);
                    //System.out.print("Fourth Image clicked." + current_user);
                    break;
                case "session_2_11":
                    current_session = "2_11";
                    FxViewController.getInstance().setCurrentView("2_11", AppConfiguration.VIEW_TYPE.FORM_VIEW);
                    //System.out.print("Fifth Image clicked." + current_user);
                    break;
                case "session_2_12":
                    current_session = "2_12";
                    FxViewController.getInstance().setCurrentView("2_12", AppConfiguration.VIEW_TYPE.FORM_VIEW);
                    //System.out.print("sixth Image clicked." + current_user);
                    break;

                case "session_2_quiz_1":
                    current_session = "2_13";
                    FxViewController.getInstance().setCurrentView("quiz_2_1", AppConfiguration.VIEW_TYPE.FORM_VIEW);
                    break;

                case "session_2_13":
                    current_session = "2_13";
                    FxViewController.getInstance().setCurrentView("quiz_2_1", AppConfiguration.VIEW_TYPE.FORM_VIEW);
                    //System.out.print("Seventh Image clicked." + current_user);
                    break;
                case "session_2_14":
                    current_session = "2_14";
                    FxViewController.getInstance().setCurrentView("2_14", AppConfiguration.VIEW_TYPE.FORM_VIEW);
                    //System.out.print("Seventh Image clicked." + current_user);
                    break;
                case "session_2_15":
                    current_session = "2_15";
                    FxViewController.getInstance().setCurrentView("2_15", AppConfiguration.VIEW_TYPE.FORM_VIEW);
                    //System.out.print("Seventh Image clicked." + current_user);
                    break;
                case "session_2_16":
                    current_session = "2_16";
                    FxViewController.getInstance().setCurrentView("2_16", AppConfiguration.VIEW_TYPE.FORM_VIEW);
                    //System.out.print("Seventh Image clicked." + current_user);
                    break;
                case "session_2_17":
                    current_session = "2_17";
                    FxViewController.getInstance().setCurrentView("2_17", AppConfiguration.VIEW_TYPE.FORM_VIEW);
                    //System.out.print("Seventh Image clicked." + current_user);
                    break;
                case "session_2_18":
                    current_session = "2_18";
                    FxViewController.getInstance().setCurrentView("2_18", AppConfiguration.VIEW_TYPE.FORM_VIEW);
                    //System.out.print("Seventh Image clicked." + current_user);
                    break;
                case "session_2_19":
                    current_session = "2_19";
                    FxViewController.getInstance().setCurrentView("2_19", AppConfiguration.VIEW_TYPE.FORM_VIEW);
                    //System.out.print("Seventh Image clicked." + current_user);
                    break;
                case "session_2_20":
                    current_session = "2_20";
                    FxViewController.getInstance().setCurrentView("2_20", AppConfiguration.VIEW_TYPE.FORM_VIEW);
                    //System.out.print("Seventh Image clicked." + current_user);
                    break;
                case "session_2_21":
                    if (isBanglaContent) {
                        current_session = "3_0";
                        FxViewController.getInstance().setCurrentView("quiz_2_2", AppConfiguration.VIEW_TYPE.FORM_VIEW);
                        break;

                    } else {
                        current_session = "2_21";
                        FxViewController.getInstance().setCurrentView("2_21", AppConfiguration.VIEW_TYPE.FORM_VIEW);
                        //System.out.print("Seventh Image clicked." + current_user);
                        break;
                    }

                case "session_2_22":
                    current_session = "2_22";
                    FxViewController.getInstance().setCurrentView("2_22", AppConfiguration.VIEW_TYPE.FORM_VIEW);
                    //System.out.print("Seventh Image clicked." + current_user);
                    break;

                case "session_2_23":
                    current_session = "2_23";
                    FxViewController.getInstance().setCurrentView("2_23", AppConfiguration.VIEW_TYPE.FORM_VIEW);
                    //System.out.print("Seventh Image clicked." + current_user);
                    break;

                case "session_2_24":
                    current_session = "2_24";
                    FxViewController.getInstance().setCurrentView("2_24", AppConfiguration.VIEW_TYPE.FORM_VIEW);
                    //System.out.print("Seventh Image clicked." + current_user);
                    break;
                case "session_2_quiz_2":
                    current_session = "3_0";
                    FxViewController.getInstance().setCurrentView("quiz_2_2", AppConfiguration.VIEW_TYPE.FORM_VIEW);
                    break;

                case "session_2_25":
                    current_session = "3_0";
                    FxViewController.getInstance().setCurrentView("quiz_2_2", AppConfiguration.VIEW_TYPE.FORM_VIEW);
                    //System.out.print("Seventh Image clicked." + current_user);
                    break;

                case "session_3_1":
                    current_session = "3_1";
                    FxViewController.getInstance().setCurrentView("3_1", AppConfiguration.VIEW_TYPE.FORM_VIEW);
                    //System.out.print("second Image clicked." + current_user);
                    break;
                case "session_3_2":
                    current_session = "3_2";
                    FxViewController.getInstance().setCurrentView("3_2", AppConfiguration.VIEW_TYPE.FORM_VIEW);
                    //System.out.print("Third Image clicked." + current_user);
                    break;
                case "session_3_3":
                    if (isBanglaContent) {
                        current_session = "3_50";
                        FxViewController.getInstance().setCurrentView("3_3", AppConfiguration.VIEW_TYPE.FORM_VIEW);
                        break;
                    } else {
                        current_session = "3_50";
                        isEnd = true;
                        FxViewController.getInstance().setCurrentView("quiz_3_1", AppConfiguration.VIEW_TYPE.FORM_VIEW);
                        break;
                    }
                case "session_3_quiz":
                    current_session = "3_4";
                    FxViewController.getInstance().setCurrentView("quiz_3_1", AppConfiguration.VIEW_TYPE.FORM_VIEW);
                    break;
                case "session_3_4":
                    current_session = "3_50";
                    isEnd = true;
                    FxViewController.getInstance().setCurrentView("quiz_3_1", AppConfiguration.VIEW_TYPE.FORM_VIEW);
                    break;
            }
        }else{
                clearTmpData();
                isBanglaContent = false;
                Main.CURRENT_DATE_TIME = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
                try {
                    Main.sendTimeToServer(Main.STARTING_DATE_TIME, Main.CURRENT_DATE_TIME);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                FxViewController.getInstance().setCurrentView(AppConfiguration.LOGIN_NAME, AppConfiguration.VIEW_TYPE.LOGIN_VIEW);
            }
        }


    private void clearTmpData(){
        current_user = "";
        isBanglaContent=false;
        isEnd=false;
        //current_session = "1_0";
    }
}
