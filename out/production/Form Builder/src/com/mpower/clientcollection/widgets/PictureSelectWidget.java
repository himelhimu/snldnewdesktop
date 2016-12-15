package com.mpower.clientcollection.widgets;

import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by sabbir on 12/15/16.
 */
public class PictureSelectWidget extends GridPane {
    public GridPane mGridPane;
    private ProgressBar mProgressBar;
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

    }
}
