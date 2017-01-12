package com.mpower.desktop.controller;

import com.mpower.clientcollection.controller.FxViewController;
import com.mpower.desktop.config.AppConfiguration;
import com.mpower.desktop.database.InitializeDatabase;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.media.MediaView;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by hemel on 4/18/16.
 * @author sabbir sabbir@mpower-social.com
 */
public class IntroViewController {
    @FXML
    MediaView intro_media_view;
    public void startChapterView(ActionEvent actionEvent) {
        //setNewUser(false);
        intro_media_view.getMediaPlayer().stop();
        FxViewController.getInstance().player.stop();
        FxViewController.getInstance().setCurrentView("Chapter View", AppConfiguration.VIEW_TYPE.COURSE_OVERVIEW);
    }


}
