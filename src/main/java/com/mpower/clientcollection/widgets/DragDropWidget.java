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
import org.javarosa.core.model.SelectChoice;
import org.javarosa.core.model.data.IAnswerData;
import org.javarosa.form.api.FormEntryCaption;
import org.javarosa.form.api.FormEntryPrompt;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by sabbir on 12/12/16.
 */
public class DragDropWidget extends QuestionWidget{
    private  File testDIrectory=null;
    private  String imageNameFInal="";
    private  String mCurrentPath="";
    public Pane mAnchorPane;
    private FormEntryPrompt mPrompt;
    private int[] buttonValue;
    private Label mAnswerText;
    private int numBerOfImagePicked=1;
    private Button resetButton;

    private int mRowIdx=0;
    private int mColIdx=0;

    private double imgX,imgY;
    private double startX,startY;
    private double distX,distY;
    private List<SelectChoice> mItems;
    private ArrayList<ImageView> formImagesList;

    //ImageViews for holding images
    private javafx.scene.image.ImageView imageView1,imageView2,imageView3,imageView4,imageView5;
    private ArrayList<ImageView> imageViews;

    public DragDropWidget(FormEntryPrompt prompt)
    {
        super(prompt);
        System.out.println("In drag&drop widget #####");
        mAnchorPane=new AnchorPane();
        mAnchorPane.setPrefSize(300,300);
        mPrompt=prompt;
        mItems= prompt.getSelectChoices();
        mCurrentPath=System.getProperty("user.dir");
        String currentFormPath = FormViewController.getInstance().getCurrentFormName();
        String formFileName = currentFormPath.substring(0, currentFormPath.lastIndexOf("."));

        String imageUri =
                mPrompt.getSpecialFormSelectChoiceText(mItems.get(0),
                        FormEntryCaption.TEXT_FORM_IMAGE);
        String imageName = imageUri.substring(imageUri.lastIndexOf("/") + 1);
        System.out.println("****image url = " + imageName);
        imageNameFInal=imageName.substring(0,imageName.indexOf("_"));
        System.out.println("***after substring "+imageNameFInal);
        String directoryName = mCurrentPath + "/forms/" +formFileName+ "-media/";
        System.out.println("*** Currentpath from pictureselect "+directoryName);


        testDIrectory=new File(directoryName);
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
        imageView5=new ImageView();

        Image image1=new Image(this.getClass().getClassLoader().getResourceAsStream("resources/img/dragdrop/c1q20_8.png"));
        imageView1.setImage(image1);
        Image image2=new Image(this.getClass().getClassLoader().getResourceAsStream("resources/img/dragdrop/c1q20_11.png"));
        imageView2.setImage(image2);
        Image image3=new Image(this.getClass().getClassLoader().getResourceAsStream("resources/img/dragdrop/c1q20_13.png"));
        imageView3.setImage(image3);
        Image image4=new Image(this.getClass().getClassLoader().getResourceAsStream("resources/img/dragdrop/c1q20_14.png"));
        imageView4.setImage(image4);
        Image image5=new Image(this.getClass().getClassLoader().getResourceAsStream("resources/img/dragdrop/common.png"));
        imageView5.setImage(image5);

        ArrayList<javafx.scene.image.ImageView> images=new ArrayList<>();

        images.add(imageView1);
        images.add(imageView2);
        images.add(imageView3);
        images.add(imageView4);
        images.add(imageView5);

        imageViews=new ArrayList<>();
        ArrayList<String> allImagesList=new ArrayList<>();
        try {

            allImagesList=getAllImages(testDIrectory);
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i=0;i<allImagesList.size();i++){
            imageViews.add(getImage(allImagesList.get(i)));

        }




        for(int i=0;i<imageViews.size();i++)
        {
            mAnchorPane.getChildren().add(this.getColIndex(), imageViews.get(i));
            this.incColIndex();
            imageViews.get(i).setId(String.valueOf(i));
        }


       /* for(int i=0;i<images.size();i++){
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

        }*/

        FormViewController fvc=FormViewController.getInstance();
        FxViewController.getInstance().getCurrentLayout().add(mAnchorPane,fvc.getColIndex(),fvc.getRowIndex());
       // fvc.incRowIndex();
        FxViewController.getInstance().getCurrentLayout().add(imageView5,fvc.getColIndex(),fvc.getRowIndex());
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
            if (file!=null && file.getName().startsWith(imageNameFInal) && file.getName().toLowerCase().endsWith(".png")){
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

    public void incRowIndex(){
        this.mRowIdx++;
    }
    public void decRowIndex(){
        this.mRowIdx--;
    }
    public void incColIndex(){
        this.mColIdx++;
    }
    public void decColIndex(){
        this.mColIdx--;
    }
    public int getRowIndex(){
        return this.mRowIdx;
    }
    public int getColIndex(){
        return this.mColIdx;
    }
}
