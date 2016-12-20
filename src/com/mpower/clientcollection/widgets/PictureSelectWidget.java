package com.mpower.clientcollection.widgets;

import com.mpower.clientcollection.controller.FormViewController;
import com.mpower.clientcollection.controller.FxViewController;
import com.mpower.desktop.config.AppConfiguration;
import javafx.beans.binding.Binding;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
import javafx.event.EventType;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import org.javarosa.core.model.data.IAnswerData;
import org.javarosa.form.api.FormEntryPrompt;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by sabbir on 12/15/16.
 */
public class PictureSelectWidget extends QuestionWidget {
    public AnchorPane mAnchorPane;
    ArrayList<ImageView> imageViews;
    private File DIRECTORY_NEW=new File("/home/sabbir/Downloads/Form Builder/src/resources/img/c1_exam");
   public PictureSelectWidget(FormEntryPrompt prompt)
    {
        super(prompt);
        System.out.println("Im in PictureSelectWidget ###");
        mAnchorPane=new AnchorPane();
        setImages();
    }

   /* public void initialize() {
        System.out.println("in init ####");
        setProgressBar();

        FormViewController fvc=FormViewController.getInstance();
        FxViewController.getInstance().getCurrentLayout().add(,fvc.getColIndex(),fvc.getRowIndex());
        fvc.incRowIndex();
    }

    private void setProgressBar() {
        Button button=new Button("CLICK ME");
        System.out.println("in progressbar @@@@");
        mProgressBar=new ProgressBar();
        mProgressBar.setAccessibleText("Andromeda");

        *//*mGridPane.getChildren().add(mProgressBar);
        mGridPane.getChildren().add(button);*//*
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
    }*/

    public void setImages()
    {
        //mAnchorPane=new AnchorPane();
        imageViews=new ArrayList<>();
        ArrayList<String> allImagesList=new ArrayList<>();
        try {

            allImagesList=getAllImages(DIRECTORY_NEW);
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i=0;i<allImagesList.size();i++){
            imageViews.add(getImage(allImagesList.get(i)));

        }




        for(int i=0;i<imageViews.size();i++)
        {
            mAnchorPane.getChildren().add(imageViews.get(i));
        }

/*        ImageView imageView2=new ImageView(new Image(this.getClass().getClassLoader().getResourceAsStream("resources/img/rearrange/c1q2_2.png")));
        ImageView imageView3=new ImageView(new Image(this.getClass().getClassLoader().getResourceAsStream("resources/img/rearrange/c1q2_3.png")));
        ImageView imageView4=new ImageView(new Image(this.getClass().getClassLoader().getResourceAsStream("resources/img/rearrange/c1q2_4.png")));*/
        for (int i=0;i<mAnchorPane.getChildren().size();i++) {
            //node.setOnMouseDragEntered(mouseDragged());
            Node node=mAnchorPane.getChildren().get(i);
            node.addEventHandler(MouseEvent.MOUSE_DRAGGED, event -> {
                double imgX = event.getX();
                double imgY = event.getY();

                node.setTranslateX(imgX);
                node.setTranslateY(imgY);
                event.consume();
            });
        }

        /*handler= (EventHandler<MouseEvent>) event -> {
            double imgX=event.getX();
            double imgY=event.getY();

            for (Node node: mAnchorPane.getChildren())
            {
                node.setLayoutX(imgX);
                node.setLayoutY(imgY);
            }
            event.consume();
        };
*/
        FormViewController formViewController=FormViewController.getInstance();
        FxViewController.getInstance().getCurrentLayout().add(mAnchorPane,formViewController.getColIndex(),formViewController.getRowIndex());
        formViewController.incRowIndex();
    }


    private ImageView getImage(String s) {
        System.out.println(s);

        Image image=new Image("file:"+s,100,100,false,false);
        image.isPreserveRatio();
        System.out.println(image.impl_getUrl());

        return new ImageView(image);
    }

    private ArrayList<String> getAllImages(File directory) throws IOException
    {
        ArrayList<String> resultList=new ArrayList<>();

        File[] files=directory.listFiles();
        //assert files != null;
        for (File file: files != null ? files : new File[0]){
            if (file!=null && file.getName().startsWith("c1q1") && file.getName().toLowerCase().endsWith(".png")){
                resultList.add(file.getAbsolutePath());
            }
        }

        return resultList;

    }

    @Override
    public IAnswerData getAnswer() {
        return null;
    }

    @Override
    public void clearAnswer() {

    }

    @Override
    public void setFocus() {

    }

    @Override
    public void setOnLongClickListener() {

    }
}
