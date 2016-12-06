package com.mpower.desktop.controller;


import com.mpower.clientcollection.controller.FxViewController;
import com.mpower.desktop.config.AppConfiguration;
import com.mpower.desktop.database.InitializeDatabase;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Login Controller.
 */
public class LoginController extends AnchorPane {

    @FXML
    TextField userId;
    @FXML
    PasswordField password;
    @FXML
    Button login;
    @FXML
    Label errorMessage;
    private boolean isNewUser = false;

    public void processLogin(ActionEvent event) {
        String username = userId.getText();
        boolean isvalid = isUserValid(userId.getText(),password.getText());
        if(isvalid){
            isNewUser = isUserNew(username);
            if(isNewUser) {
                setUserStatus(false, username);
                FxViewController.getInstance().setCurrentView("Intro Video", AppConfiguration.VIEW_TYPE.INTRO_VIEW);
            }
            else
                FxViewController.getInstance().setCurrentView("Course Content", AppConfiguration.VIEW_TYPE.COURSE_OVERVIEW);
        } else{
            FxViewController.getInstance().setCurrentView("Login", AppConfiguration.VIEW_TYPE.LOGIN_VIEW);
        }


        }

    private boolean isUserNew(String username) {
        try{
            InitializeDatabase id = InitializeDatabase.get_instance();
            Statement st = id.getConnection().createStatement();
            ResultSet rs = st.executeQuery("SELECT isNew FROM "+AppConfiguration.REGISTRATION_INFO+" WHERE user_name like \""+username+"\" LIMIT 1;");
            if(rs.next()){
                if(rs.getInt(1) == 1)
                    return true;
                else
                    return false;
            }
            rs.close();
            st.close();
        }catch (SQLException e){
                e.printStackTrace();
        }
        return false;
    }

    private void setUserStatus(boolean b,String username) {
        try{
            InitializeDatabase id = InitializeDatabase.get_instance();
            Statement stmt = id.getConnection().createStatement();
            String sql = "UPDATE "+AppConfiguration.REGISTRATION_INFO+" set isNew = 0 where user_name like \""+username+"\";";
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
        try {
            InitializeDatabase id = InitializeDatabase.get_instance();
            Statement st = id.getConnection().createStatement();
            ResultSet rs = st.executeQuery("SELECT password FROM "+AppConfiguration.LOGIN_INFO+" WHERE username like \""+username+"\" LIMIT 1;");
            if(rs.next()) {
                if (rs.getString(1).equals(password)){
                    System.out.print("user is Valid.");
                    ContentViewController.current_user = username;
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


    public void processRegister(ActionEvent actionEvent) {
        FxViewController.getInstance().setCurrentView("register", AppConfiguration.VIEW_TYPE.REG_VIEW);
    }
}

