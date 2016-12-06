package com.mpower.desktop.config;

import java.util.Properties;

/**
 * Created by hemel on 4/13/16.
 */
public class AppConfiguration {
    public enum VIEW_TYPE  {FORM_VIEW,TUT_VIEW,REG_VIEW,LOGIN_VIEW,INTRO_VIEW,COURSE_OVERVIEW}

    public static boolean APPLICATION_DEBUG = false;

    public static int SCREEN_WIDTH = 0;
    public static int SCREEN_HEIGHT = 0;
    public static int SPLASH_WIDTH = 0;
    public static int SPLASH_HEIGHT = 0;
    //Window Titles
    public static String APPLICATION_NAME = "";
    public static String LOGIN_NAME = "";
    public static String INTRO_WINDOW = "";
    public static String COURSE_OVERVIEW_WINDOW = "";

    public static String IMG_PATH = "";
    public static String FXML_PATH = "";
    public static String VIDEO_PATH = "";

    public static String XFORM_XML_PATH = "";
    public static String FORM_XML_PATH = "/home/sabbir/Downloads/snlForms/";
    public static String XFORM_CHAPTER_1_1_XML = "";
    public static String XFORM_CHAPTER_1_2_XML = "";
    public static String XFORM_CHAPTER_1_3_XML = "";

    //DATABASE TABLE NAME
    public static String DB_FILE_NAME = "";
    public static String REGISTRATION_INFO = "";
    public static String LOGIN_INFO = "";
    public static String PROGRESS_INFO = "";

    //Sabbir
    //Chapter 1 english
    public static String CHAPTER_1_1="";



    public static void setValueFromProperties(Properties prop){

        APPLICATION_NAME        = prop.getProperty("APPLICATION_NAME","SNL");
        APPLICATION_DEBUG       = Boolean.valueOf(prop.getProperty("APPLICATION_DEBUG","TRUE"));

        SCREEN_WIDTH            = Integer.parseInt(prop.getProperty("SCREEN_WIDTH","400"));
        SCREEN_HEIGHT           = Integer.parseInt(prop.getProperty("SCREEN_HEIGHT","450"));
        SPLASH_WIDTH            = Integer.parseInt(prop.getProperty("SPLASH_WIDTH","676"));
        SPLASH_HEIGHT           = Integer.parseInt(prop.getProperty("SPLASH_HEIGHT","227"));

        DB_FILE_NAME            = prop.getProperty("DB_FILE_NAME","");
        REGISTRATION_INFO       = prop.getProperty("DB_REGISTRATION_INFO","");
        LOGIN_INFO              = prop.getProperty("DB_LOGIN_INFO","");
        PROGRESS_INFO           = prop.getProperty("DB_PROGRESS_INFO","");

        LOGIN_NAME              = prop.getProperty("LOGIN_NAME","SNL");
        INTRO_WINDOW            = prop.getProperty("INTRO_WINDOW","SNL");
        COURSE_OVERVIEW_WINDOW  = prop.getProperty("COURSE_OVERVIEW_WINDOW","SNL");

        IMG_PATH                = prop.getProperty("IMG_PATH","/resources/img/");
        FXML_PATH               = prop.getProperty("FXML_PATH","/resources/fxml/");
        VIDEO_PATH              = prop.getProperty("VIDEO_PATH","/resources/video/");

        XFORM_XML_PATH          = prop.getProperty("XFORM_XML_PATH","/home/sabbir/Downloads/Form Builder/src/resources/form_xml/test_html_with_ques.xml");
        FORM_XML_PATH           = prop.getProperty("FORM_XML_PATH","/home/sabbir/Downloads/snlForms/");


        XFORM_CHAPTER_1_1_XML   = prop.getProperty("XFORM_CHAPTER_1_1_XML","test_html_with_ques.xml");
        XFORM_CHAPTER_1_2_XML   = prop.getProperty("XFORM_CHAPTER_1_2_XML","test_html_with_ques.xml");
        XFORM_CHAPTER_1_3_XML   = prop.getProperty("XFORM_CHAPTER_1_3_XML","test_html_with_ques.xml");

        //Sabbir
        CHAPTER_1_1=prop.getProperty("CHAPTER_1_1","question_wth_calculation.xml");




    }
}
