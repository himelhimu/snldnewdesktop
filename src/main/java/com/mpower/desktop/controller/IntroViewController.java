package com.mpower.desktop.controller;

import com.mpower.clientcollection.controller.FxViewController;
import com.mpower.desktop.config.AppConfiguration;
import com.mpower.desktop.database.InitializeDatabase;
import javafx.event.ActionEvent;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by hemel on 4/18/16.
 */
public class IntroViewController {
    public void startChapterView(ActionEvent actionEvent) {
        //setNewUser(false);
        FxViewController.getInstance().setCurrentView("Chapter View", AppConfiguration.VIEW_TYPE.COURSE_OVERVIEW);
    }


}
