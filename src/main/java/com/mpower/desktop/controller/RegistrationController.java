package com.mpower.desktop.controller;

import com.mpower.clientcollection.controller.FxViewController;
import com.mpower.desktop.config.AppConfiguration;
import com.mpower.desktop.config.AppLogger;
import com.mpower.desktop.database.InitializeDatabase;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.InputMethodEvent;
import javafx.stage.Stage;
import sample.Main;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by hemel on 4/11/16.
 *  @author sabbir sabbir@mpower-social.com
 */
public class RegistrationController {
    private Stage regStage;
    private Stage logInStage;
    private Parent root = null;

    @FXML
    private ToggleButton button_doctor;
    @FXML
    private ToggleButton button_nurse;
    @FXML
    private ToggleButton button_fwd;
    @FXML
    private ToggleButton button_sacmo;
    @FXML
    private Button button_reg,button_login;

    @FXML
    private TextField tf_name;
    @FXML
    private TextField tf_addr;
    @FXML
    private TextField tf_email;
    @FXML
    private PasswordField tf_pass;
    @FXML
    private TextField tf_mobile;
    @FXML
    private TextField tf_username;
    @FXML
    private PasswordField tf_retypass;
    @FXML
    private RadioButton rb_male;
    @FXML
    private RadioButton rb_female;

    @FXML
    private ImageView iv_profession;

    public static int current_user_type = -1;

    public void initRegistrationPage(ActionEvent actionEvent) {
            //current_user_type = -1;
            viewRegistration();

    }

    private void viewRegistration() {
        regStage = Main.getMainStage();
        try {
            root = FXMLLoader.load(getClass().getResource("/registration_profession.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Image prof_image = null;

        if (button_doctor.isSelected()) {
            prof_image = new Image(Main.class.getResourceAsStream("/doctor.png"));
            current_user_type = 0;
        } else if(button_nurse.isSelected()){
            prof_image = new Image(Main.class.getResourceAsStream("/nurse.png"));
            current_user_type = 1;
        } else if(button_fwd.isSelected()){
            prof_image = new Image(Main.class.getResourceAsStream("/fwv.png"));
            current_user_type = 2;
        }
        Node imageview = root.lookup("#iv_profession");
        ((ImageView) imageview).setImage(prof_image);
        imageview.setId(""+current_user_type);

        regStage.setScene(new Scene(root));
        regStage.show();


    }

    @FXML
    protected void registeruser(ActionEvent actionEvent) {

        if (validateData(tf_mobile.getText(),tf_email.getText())){
            String name = tf_name.getText();
            String address = tf_addr.getText();
            String email = tf_email.getText();
            String mobile =tf_mobile.getText();
            String password = tf_pass.getText();
            String retypepassword = tf_retypass.getText();
            String username = tf_username.getText();
            try {
                InitializeDatabase id = InitializeDatabase.get_instance();
                if(id.isUserExistAndValid(username,password)){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("User Exists");
                    alert.setHeaderText("Already Registered");
                    String s ="User Already Exists.. Please Login to view..";
                    alert.setContentText(s);
                    alert.show();
                    //System.out.print("User Already Exists.. Please Login to view..");
                } else {
                    PreparedStatement tmpPrepS = id.getRegisterStatement();
                    tmpPrepS.setInt(1, 1);
                    tmpPrepS.setString(2, name);
                    tmpPrepS.setInt(3, rb_male.isSelected() ? 1 : 2);
                    tmpPrepS.setString(4, address);
                    tmpPrepS.setString(5, mobile);
                    tmpPrepS.setString(6, email);
                    tmpPrepS.setString(7, username);
                    tmpPrepS.setString(8, password);
                    tmpPrepS.addBatch();
                    id.getConnection().setAutoCommit(false);
                    //TODO Sabbir
                /*id.getConnection().close();
                id.getConnection();*/
                    tmpPrepS.executeBatch();
                    id.getConnection().setAutoCommit(true);
                    tmpPrepS.close();

                    current_user_type = Integer.parseInt(iv_profession.getId());

                    PreparedStatement tmpLogin = id.getLoginStatement();
                    tmpLogin.setString(1,username);
                    tmpLogin.setInt(2,current_user_type);
                    tmpLogin.setString(3,password);
                    tmpLogin.addBatch();
                    id.getConnection().setAutoCommit(false);
                    tmpLogin.executeBatch();
                    id.getConnection().setAutoCommit(true);
                    tmpLogin.close();
                    id.closeDBConnection();

                }

            } catch (SQLException e) {
                e.printStackTrace();
                AppLogger.getLoggerInstance().writeLog("\n"+getClass().getName()+e.getMessage(),AppConfiguration.APPLICATION_DEBUG);
            }
            FxViewController.getInstance().setCurrentView(AppConfiguration.LOGIN_NAME, AppConfiguration.VIEW_TYPE.LOGIN_VIEW);
        }else {
            Alert alert =new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Email or Mobile Not Valid");
            alert.setContentText("Mobile should be 11 digit & Email should be valid");
            alert.showAndWait();

            FxViewController.getInstance().setCurrentView(AppConfiguration.REGISTRATION_INFO, AppConfiguration.VIEW_TYPE.REG_VIEW);
        }

    }
    
    /*@FXML
    protected void registeruser(ActionEvent actionEvent) {

        if (validateData(tf_mobile.getText(),tf_email.getText())){

        }
        String name = tf_name.getText();
        String address = tf_addr.getText();
        String email = tf_email.getText();
        String mobile =tf_mobile.getText();
        String password = tf_pass.getText();
        String retypepassword = tf_retypass.getText();
        String username = tf_username.getText();
        try {
            InitializeDatabase id = InitializeDatabase.get_instance();
            if(id.isUserExistAndValid(username,password)){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("User Exists");
                alert.setHeaderText("Already Registered");
                String s ="User Already Exists.. Please Login to view..";
                alert.setContentText(s);
                alert.show();
                //System.out.print("User Already Exists.. Please Login to view..");
            } else {
                PreparedStatement tmpPrepS = id.getRegisterStatement();
                tmpPrepS.setInt(1, 1);
                tmpPrepS.setString(2, name);
                tmpPrepS.setInt(3, rb_male.isSelected() ? 1 : 2);
                tmpPrepS.setString(4, address);
                tmpPrepS.setString(5, mobile);
                tmpPrepS.setString(6, email);
                tmpPrepS.setString(7, username);
                tmpPrepS.setString(8, password);
                tmpPrepS.addBatch();
                id.getConnection().setAutoCommit(false);
                //TODO Sabbir
                *//*id.getConnection().close();
                id.getConnection();*//*
                tmpPrepS.executeBatch();
                id.getConnection().setAutoCommit(true);
                tmpPrepS.close();

                current_user_type = Integer.parseInt(iv_profession.getId());

                PreparedStatement tmpLogin = id.getLoginStatement();
                tmpLogin.setString(1,username);
                tmpLogin.setInt(2,current_user_type);
                tmpLogin.setString(3,password);
                tmpLogin.addBatch();
                id.getConnection().setAutoCommit(false);
                tmpLogin.executeBatch();
                id.getConnection().setAutoCommit(true);
                tmpLogin.close();
                id.closeDBConnection();

            }

        } catch (SQLException e) {
            e.printStackTrace();
            AppLogger.getLoggerInstance().writeLog("\n"+getClass().getName()+e.getMessage(),AppConfiguration.APPLICATION_DEBUG);
        }
        FxViewController.getInstance().setCurrentView(AppConfiguration.LOGIN_NAME, AppConfiguration.VIEW_TYPE.LOGIN_VIEW);
    }*/

    private boolean validateData(String mobile, String email) {
       // return !mobile.isEmpty() && mobile.length() == 11 && validateEmail(email);
        return !mobile.isEmpty() && mobile.length() == 11 && validateMobile(mobile) && validateEmail(email);
    }

    private boolean validateMobile(String mobile) {
        Pattern pattern = Pattern.compile("\\d{11}");
        Matcher matcher = pattern.matcher(mobile);

        return matcher.find();
    }

    private boolean validateEmail(String email) {
         final Pattern VALID_EMAIL_ADDRESS_REGEX =
                Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher=VALID_EMAIL_ADDRESS_REGEX.matcher(email);

        return matcher.find();
    }


    public void gotoPreviousScreen(ActionEvent actionEvent) {
        FxViewController.getInstance().setCurrentView(AppConfiguration.APPLICATION_NAME, AppConfiguration.VIEW_TYPE.REG_VIEW);
    }

    public void onImageClicked(Event event) {
        button_reg.setDisable(false);
    }

    @FXML
    public void initLogin(ActionEvent event) {
        //callLoginPage();
        FxViewController.getInstance().setCurrentView(AppConfiguration.APPLICATION_NAME, AppConfiguration.VIEW_TYPE.LOGIN_VIEW);
    }

    private void callLoginPage() {
        try {
            root=FXMLLoader.load(getClass().getResource("/Login.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene logInScene=new Scene(root);
        logInStage.setScene(logInScene);
        logInStage.show();
    }

    void mobile_Number(){
        tf_mobile.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) {
                    tf_mobile.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
    }

}
