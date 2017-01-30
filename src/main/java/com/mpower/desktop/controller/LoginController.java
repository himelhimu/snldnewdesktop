package com.mpower.desktop.controller;


import com.mpower.clientcollection.controller.FormViewController;
import com.mpower.clientcollection.controller.FxViewController;
import com.mpower.desktop.config.AppConfiguration;
import com.mpower.desktop.database.InitializeDatabase;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import sample.Main;

import java.io.*;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * Login Controller.
 * @author sabbir sabbir@mpower-social.com
 */
public class LoginController extends AnchorPane implements Initializable {

    @FXML
    TextField userId;
    @FXML
    PasswordField password;
    @FXML
    Button login;
    @FXML
    Label errorMessage;
    private boolean isNewUser = false;

    //For detecting user type
    public static int USER_TYPE=-1;

    public static String CURRENT_PROFFESION="";



    public void processLogin(ActionEvent event) {
       // jumpBackIn();
        String username = userId.getText();
        String passWord = password.getText();
        if (username.equals("") || passWord.equals("")) {
            createErrorDIalog("Please provide username & password");
            FxViewController.getInstance().setCurrentView("Login", AppConfiguration.VIEW_TYPE.LOGIN_VIEW);
        } else {
            boolean isvalid = isUserValid(userId.getText(),password.getText());
           // boolean isvalid = false;
           /* try {
                isvalid = InitializeDatabase.get_instance().isUserExistAndValid(userId.getText(), password.getText());
            } catch (SQLException e) {
                e.printStackTrace();
            }*/

            if (isvalid) {
                try {
                    isNewUser = InitializeDatabase.get_instance().isUserNew(username);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                if (isNewUser) {
                    System.out.println("***NEW USER***");
                    setUserStatus(false, username);
                    FxViewController.getInstance().setCurrentView("Intro Video", AppConfiguration.VIEW_TYPE.INTRO_VIEW);
                } else
                    FxViewController.getInstance().setCurrentView("Course Content", AppConfiguration.VIEW_TYPE.COURSE_OVERVIEW);
            } else {
               // createErrorDIalog("UserName or Password Incorrect");
                FxViewController.getInstance().setCurrentView("Login", AppConfiguration.VIEW_TYPE.LOGIN_VIEW);
            }


        }
    }

    private void jumpBackIn(){
        System.out.println("*** In JumpBackIn ");
        InputStream is=null;
        try {
            FileInputStream fileInputStream=new FileInputStream("./config.properties");
            is=fileInputStream;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Properties props=new Properties();
        try {
            props.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (!props.isEmpty()){
            String userName= props.getProperty("username");
            String passWord=props.getProperty("password");

            userId.setText(userName);
            password.setText(passWord);

            /*Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Log in as  "+userName);

            Optional<ButtonType> result=alert.showAndWait();
            if (result.get()== ButtonType.OK){
                int userType= Integer.parseInt(props.getProperty("user_type"));
                LoginController.USER_TYPE=userType;
                ContentViewController.current_user=userName;
                Main.isLoggedIn=true;
                FxViewController.getInstance().setCurrentView(AppConfiguration.COURSE_OVERVIEW_WINDOW, AppConfiguration.VIEW_TYPE.COURSE_OVERVIEW);

            }else {
                //TODO do nothing
                alert.close();
            }*/
        }
    }



    private void setUserStatus(boolean b,String username) {
        try{
            InitializeDatabase id = InitializeDatabase.get_instance();
            Statement stmt = id.getConnection().createStatement();
            String stauts="0";
            String sql = "UPDATE "+AppConfiguration.REGISTRATION_INFO+" set isNew = 0 where user_name like \""+username+"\";";
            //String sql = "UPDATE "+AppConfiguration.REGISTRATION_INFO+" set isNew ="+stauts+"where user_name like \""+username+"\";";
            stmt.executeUpdate(sql);
            id.getConnection().setAutoCommit(false);
            id.getConnection().commit();
            id.getConnection().setAutoCommit(true);
            stmt.close();
        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    private boolean isUserValid(String username, String password) {
        if (username.isEmpty() || password.isEmpty()) {createErrorDIalog("Please provide credentials"); return false;}
        else
        try {
            InitializeDatabase id = InitializeDatabase.get_instance();
            Statement st = id.getConnection().createStatement();
            String loginSql = "SELECT password,user_type FROM "+AppConfiguration.LOGIN_INFO+" WHERE username like \""+username+"\" LIMIT 1;";
            System.out.println("**LoginSql = " + loginSql);
            ResultSet rs = st.executeQuery(loginSql);

            if(rs.next()) {
                if (rs.getString(1).equals(password)){
                    System.out.println("** Return form Database query "+rs.getString(1)+" 2nd Column "+rs.getString(2));
                    System.out.print("user is Valid.");
                    USER_TYPE=Integer.valueOf(rs.getString(2));
                    switch (USER_TYPE){
                        case 0:
                            CURRENT_PROFFESION="Doctor";
                        case 1:
                            CURRENT_PROFFESION="Nurse";
                        case 2:
                            CURRENT_PROFFESION="FWV";
                        case 3:
                            CURRENT_PROFFESION="SACMO";
                    }
                    System.out.println("*** UserType "+USER_TYPE);
                    ContentViewController.current_user = username;

                    Main.isLoggedIn=true;
                    File configFile = new File("config.properties");

                    try {
                        Properties props = new Properties();
                        props.setProperty("username", username);
                        props.setProperty("password",password);
                        props.setProperty("user_type",String.valueOf(USER_TYPE));
                        FileWriter writer = new FileWriter(configFile);
                        props.store(writer, "user settings");
                        writer.close();
                    } catch (FileNotFoundException ex) {
                        // file does not exist
                    } catch (IOException ex) {
                        // I/O error
                    }

                   /* Thread thread=new Thread(new Runnable() {
                        @Override
                        public void run() {
                            FormViewController.sendAllFilesToServer();
                        }
                    });
                    thread.start();*/
                    return true;
                }else{
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Invalid");
                    alert.setHeaderText("Invalid user");
                    String s ="User is invalid. either username or password incorrect.";
                    alert.setContentText(s);
                    alert.show();
                    System.out.print("User is Invalid.");
                    return false;
                }
            }else{
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("User Not Found");
                alert.setHeaderText("No Such User");
                String s ="No Username found with given username. please try again.";
                alert.setContentText(s);
                alert.show();
                System.out.print("No Username found with given username. please try again.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    private void createErrorDIalog(String s) {
        Alert alert=new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(s);
        alert.setContentText(s);
        alert.showAndWait();
    }


    public void processRegister(ActionEvent actionEvent) {
        FxViewController.getInstance().setCurrentView("Register", AppConfiguration.VIEW_TYPE.REG_VIEW);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        jumpBackIn();
    }
}

