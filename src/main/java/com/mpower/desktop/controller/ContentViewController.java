package com.mpower.desktop.controller;

import com.mpower.clientcollection.controller.FxViewController;
import com.mpower.desktop.config.AppConfiguration;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

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

    public void onMouseClicked(Event event) {
        if(event.getSource() instanceof ImageView) {
            String imageViewID = ((ImageView) event.getSource()).getId();
            if(imageViewID.equals("session_1_1")) {
                current_session = "1_1";
                FxViewController.getInstance().setCurrentView("1_1", AppConfiguration.VIEW_TYPE.FORM_VIEW);
                //System.out.print("first Image clicked." + current_user);

            } else if(imageViewID.equals("session_1_2")) {
                current_session = "1_2";
                FxViewController.getInstance().setCurrentView("1_2", AppConfiguration.VIEW_TYPE.FORM_VIEW);
                //System.out.print("second Image clicked." + current_user);
            } else if(imageViewID.equals("session_1_3")) {
                current_session = "1_3";
                FxViewController.getInstance().setCurrentView("1_3", AppConfiguration.VIEW_TYPE.FORM_VIEW);
                //System.out.print("Third Image clicked." + current_user);
            } else if(imageViewID.equals("session_1_4")) {
                current_session = "1_4";
                FxViewController.getInstance().setCurrentView("1_4", AppConfiguration.VIEW_TYPE.FORM_VIEW);
                //System.out.print("Fourth Image clicked." + current_user);
            } else if(imageViewID.equals("session_1_5")) {
                current_session = "1_5";
                FxViewController.getInstance().setCurrentView("1_5", AppConfiguration.VIEW_TYPE.FORM_VIEW);
                //System.out.print("Fifth Image clicked." + current_user);
            } else if(imageViewID.equals("session_1_6")) {
                current_session = "1_6";
                FxViewController.getInstance().setCurrentView("1_6", AppConfiguration.VIEW_TYPE.FORM_VIEW);
                //System.out.print("sixth Image clicked." + current_user);
            } else if(imageViewID.equals("session_1_7")) {
                current_session = "1_7";
                FxViewController.getInstance().setCurrentView("1_7", AppConfiguration.VIEW_TYPE.FORM_VIEW);
                //System.out.print("Seventh Image clicked." + current_user);
            }else if (imageViewID.equals("session_1_quiz"))
            {
                current_session="quiz_1_1";
                FxViewController.getInstance().setCurrentView("quiz_1_1", AppConfiguration.VIEW_TYPE.FORM_VIEW);
            }else if(imageViewID.equals("session_2_1")) {
                current_session = "2_1";
                FxViewController.getInstance().setCurrentView("1_2", AppConfiguration.VIEW_TYPE.FORM_VIEW);
                //System.out.print("second Image clicked." + current_user);
            } else if(imageViewID.equals("session_2_2")) {
                current_session = "2_2";
                FxViewController.getInstance().setCurrentView("1_3", AppConfiguration.VIEW_TYPE.FORM_VIEW);
                //System.out.print("Third Image clicked." + current_user);
            } else if(imageViewID.equals("session_2_3")) {
                current_session = "2_3";
                FxViewController.getInstance().setCurrentView("1_4", AppConfiguration.VIEW_TYPE.FORM_VIEW);
                //System.out.print("Fourth Image clicked." + current_user);
            } else if(imageViewID.equals("session_2_4")) {
                current_session = "2_4";
                FxViewController.getInstance().setCurrentView("1_5", AppConfiguration.VIEW_TYPE.FORM_VIEW);
                //System.out.print("Fifth Image clicked." + current_user);
            } else if(imageViewID.equals("session_2_5")) {
                current_session = "2_5";
                FxViewController.getInstance().setCurrentView("1_6", AppConfiguration.VIEW_TYPE.FORM_VIEW);
                //System.out.print("sixth Image clicked." + current_user);
            } else if(imageViewID.equals("session_2_6")) {
                current_session = "2_6";
                FxViewController.getInstance().setCurrentView("1_7", AppConfiguration.VIEW_TYPE.FORM_VIEW);
                //System.out.print("Seventh Image clicked." + current_user);
            }else if(imageViewID.equals("session_2_7")) {
                current_session = "2_7";
                FxViewController.getInstance().setCurrentView("1_7", AppConfiguration.VIEW_TYPE.FORM_VIEW);
                //System.out.print("Seventh Image clicked." + current_user);
            }
            else if (imageViewID.equals("session_2_quiz"))
            {
                current_session="quiz_2_2";
                FxViewController.getInstance().setCurrentView("quiz_1_1", AppConfiguration.VIEW_TYPE.FORM_VIEW);
            } else if(imageViewID.equals("session_2_1")) {
                current_session = "2_1";
                FxViewController.getInstance().setCurrentView("1_2", AppConfiguration.VIEW_TYPE.FORM_VIEW);
                //System.out.print("second Image clicked." + current_user);
            } else if(imageViewID.equals("session_2_2")) {
                current_session = "2_2";
                FxViewController.getInstance().setCurrentView("1_3", AppConfiguration.VIEW_TYPE.FORM_VIEW);
                //System.out.print("Third Image clicked." + current_user);
            } else if(imageViewID.equals("session_2_3")) {
                current_session = "2_3";
                FxViewController.getInstance().setCurrentView("1_4", AppConfiguration.VIEW_TYPE.FORM_VIEW);
                //System.out.print("Fourth Image clicked." + current_user);
            } else if(imageViewID.equals("session_2_4")) {
                current_session = "2_4";
                FxViewController.getInstance().setCurrentView("1_5", AppConfiguration.VIEW_TYPE.FORM_VIEW);
                //System.out.print("Fifth Image clicked." + current_user);
            } else if(imageViewID.equals("session_2_5")) {
                current_session = "2_5";
                FxViewController.getInstance().setCurrentView("1_6", AppConfiguration.VIEW_TYPE.FORM_VIEW);
                //System.out.print("sixth Image clicked." + current_user);
            } else if(imageViewID.equals("session_2_6")) {
                current_session = "2_6";
                FxViewController.getInstance().setCurrentView("1_7", AppConfiguration.VIEW_TYPE.FORM_VIEW);
                //System.out.print("Seventh Image clicked." + current_user);
            }else if(imageViewID.equals("session_2_7")) {
                current_session = "2_7";
                FxViewController.getInstance().setCurrentView("1_7", AppConfiguration.VIEW_TYPE.FORM_VIEW);
                //System.out.print("Seventh Image clicked." + current_user);
            }
            else if (imageViewID.equals("session_2_quiz"))
            {
                current_session="quiz_1_1";
                FxViewController.getInstance().setCurrentView("quiz_1_1", AppConfiguration.VIEW_TYPE.FORM_VIEW);
            }
            else if(imageViewID.equals("session_3_1")) {
                current_session = "3_1";
                FxViewController.getInstance().setCurrentView("1_2", AppConfiguration.VIEW_TYPE.FORM_VIEW);
                //System.out.print("second Image clicked." + current_user);
            } else if(imageViewID.equals("session_3_2")) {
                current_session = "3_2";
                FxViewController.getInstance().setCurrentView("1_3", AppConfiguration.VIEW_TYPE.FORM_VIEW);
                //System.out.print("Third Image clicked." + current_user);
            } else if(imageViewID.equals("session_3_3")) {
                current_session = "2_3";
                FxViewController.getInstance().setCurrentView("1_4", AppConfiguration.VIEW_TYPE.FORM_VIEW);
                //System.out.print("Fourth Image clicked." + current_user);
            }
            else if (imageViewID.equals("session_3_quiz"))
            {
                current_session="quiz_3_3";
                FxViewController.getInstance().setCurrentView("quiz_1_1", AppConfiguration.VIEW_TYPE.FORM_VIEW);
            }


        }else{
            clearTmpData();
            FxViewController.getInstance().setCurrentView("", AppConfiguration.VIEW_TYPE.LOGIN_VIEW);
        }
    }
    public void clearTmpData(){
        current_user = "";
        current_session = "1_0";
    }
}
