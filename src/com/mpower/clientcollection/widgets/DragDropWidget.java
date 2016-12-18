package com.mpower.clientcollection.widgets;

import com.mpower.clientcollection.controller.FormViewController;

import com.mpower.clientcollection.controller.FxViewController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import org.javarosa.core.model.data.IAnswerData;
import org.javarosa.form.api.FormEntryPrompt;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by sabbir on 12/12/16.
 */
public class DragDropWidget extends QuestionWidget{
    public AnchorPane mAnchorPane;
    private FormEntryPrompt mPrompt;
    private int[] buttonValue;
    private Label mAnswerText;
    private int numBerOfImagePicked=1;
    private Button resetButton;

    private double imgX,imgY;
    private double startX,startY;
    private double distX,distY;

    private ArrayList<ImageView> formImagesList;

    //ImageViews for holding images
    private javafx.scene.image.ImageView imageView1,imageView2,imageView3,imageView4;

    public DragDropWidget(FormEntryPrompt prompt)
    {
        super(prompt);
        mAnchorPane=new AnchorPane();
        mPrompt=prompt;
        initialize();
    }


    public void initialize() {
       // mPane.autosize();

        setImages();

    }

    private void imageDropprd(MouseEvent event) {
        System.out.println("inside dropped  ####");
        /*imgX= event.getX()-distX;
        imgY=event.getY()-distY;*/

        imgX= event.getX();
        imgY=event.getY();


        ImageView imageView= (ImageView) event.getSource();

       /* int newIndex=mGridPane.getColumnIndex(imageView);
        int newRow=mGridPane.getRowIndex(imageView);*/

       /* mGridPane.setLayoutY(imgX);
        mGridPane.setLayoutY(imgY);*/
       // mGridPane.add(imageView,newIndex,newRow);

     /*   imageView.setLayoutY(imgX);
        imageView.setLayoutY(imgY)*/;

        imageView.setX(imgX);
        imageView.setY(imgY);

//        mFlowPane.getChildren().add(imageView);


    }

    private void imageDragged(MouseEvent event) {
        System.out.println("inside dragged ####");
        startX= event.getX();
        startY=event.getY();


        ImageView  imageView=(ImageView) event.getSource();

        distX=startX-imgX;
        distY=startY-imgY;

    }

    private void setImages() {
        System.out.println("Inside setImages ###");
        //mAnchorPane=new AnchorPane();
       // mTilePane=new TilePane();
        //mPane.setPadding(new Insets(5));
        imageView1=new javafx.scene.image.ImageView();
        imageView2=new javafx.scene.image.ImageView();
        imageView3=new javafx.scene.image.ImageView();
        imageView4=new javafx.scene.image.ImageView();

        Image image1=new Image(this.getClass().getClassLoader().getResourceAsStream("resources/img/rearrange/c1q2_1.png"));
        imageView1.setImage(image1);
        Image image2=new Image(this.getClass().getClassLoader().getResourceAsStream("resources/img/rearrange/c1q2_2.png"));
        imageView2.setImage(image2);
        Image image3=new Image(this.getClass().getClassLoader().getResourceAsStream("resources/img/rearrange/c1q2_3.png"));
        imageView3.setImage(image3);
        Image image4=new Image(this.getClass().getClassLoader().getResourceAsStream("resources/img/rearrange/c1q2_4.png"));
        imageView4.setImage(image4);

        ArrayList<javafx.scene.image.ImageView> images=new ArrayList<>();

        images.add(imageView1);
        images.add(imageView2);
        images.add(imageView3);
        images.add(imageView4);


        for(int i=0;i<images.size();i++){
            //HBox hBox=new HBox();
            //mGridPane.getChildren().add(images.get(i));
           FormViewController formViewController=FormViewController.getInstance();
           // mGridPane.add(images.get(i),formViewController.getColIndex(),formViewController.getRowIndex());
            //formViewController.incColIndex();
           // hBox.getChildren().add(images.get(i));

           // mFlowPane.getChildren().add(images.get(i));
            mAnchorPane.getChildren().add(images.get(i));
            mAnchorPane.borderProperty();

           // mPane.borderProperty();

        }

        FormViewController fvc=FormViewController.getInstance();
        FxViewController.getInstance().getCurrentLayout().add(mAnchorPane,fvc.getRowIndex(),fvc.getColIndex());
        fvc.incRowIndex();
        imageView1.setOnMousePressed(this::imageDragged);
        imageView2.setOnMousePressed(this::imageDragged);
        imageView3.setOnMousePressed(this::imageDragged);
        imageView4.setOnMousePressed(this::imageDragged);

        imageView1.setOnMouseDragged(this::imageDropprd);
        imageView2.setOnMouseDragged(this::imageDropprd);
        imageView3.setOnMouseDragged(this::imageDropprd);
        imageView4.setOnMouseDragged(this::imageDropprd);



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
