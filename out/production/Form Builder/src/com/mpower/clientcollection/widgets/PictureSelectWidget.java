package com.mpower.clientcollection.widgets;

import com.mpower.clientcollection.controller.FxViewController;
import com.mpower.desktop.config.AppConfiguration;
import javafx.beans.binding.Binding;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
import javafx.event.EventType;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by sabbir on 12/15/16.
 */
public class PictureSelectWidget extends GridPane {
    public GridPane mGridPane;
    private ProgressBar mProgressBar;
    private StackPane stackPane;

    //public Parent root;
   public PictureSelectWidget()
    {
        System.out.println("Im in PictureSelectWidget ###");
        initialize();
    }

    public void initialize() {
        System.out.println("in init ####");
        mGridPane=new GridPane();
        mGridPane.setVgap(5);
        mGridPane.setHgap(5);
        mGridPane.setPadding(new Insets(5));
        setProgressBar();
    }

    private void setProgressBar() {
        Button button=new Button("CLICK ME");
        System.out.println("in progressbar @@@@");
        mProgressBar=new ProgressBar();
        mProgressBar.setAccessibleText("Andromeda");

        /*mGridPane.getChildren().add(mProgressBar);
        mGridPane.getChildren().add(button);*/
        mGridPane.add(mProgressBar,0,1);
        mGridPane.add(button,1,1);

        HBox hBox=addHbox();
        addStackPane(hBox);

        addToGridPane(stackPane);

    }

    private void addToGridPane(StackPane stackPane) {
       mGridPane.getChildren().add(stackPane);
    }

    private void addStackPane(HBox hBox) {
       stackPane=stackpane();
       stackPane.getChildren().addAll(hBox);
    }

    private StackPane stackpane() {
       StackPane stackPane=new StackPane();
       return stackPane;
    }

    private HBox addHbox() {
        HBox hBox =new HBox();
        Button newButton=new Button("Main Menu");
        hBox.getChildren().add(newButton);

        newButton.addEventHandler(MouseEvent.MOUSE_CLICKED,event -> {

            FxViewController.getInstance().setCurrentView(AppConfiguration.COURSE_OVERVIEW_WINDOW, AppConfiguration.VIEW_TYPE.COURSE_OVERVIEW);
        });
        return hBox;
    }
}
