package com.mpower.clientcollection.widgets;

import com.mpower.clientcollection.controller.FormViewController;

import com.mpower.clientcollection.controller.FxViewController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import org.javarosa.core.model.SelectChoice;
import org.javarosa.core.model.data.IAnswerData;
import org.javarosa.core.model.data.SelectOneData;
import org.javarosa.core.model.data.helper.Selection;
import org.javarosa.form.api.FormEntryCaption;
import org.javarosa.form.api.FormEntryController;
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
    private ArrayList<String> formImagesList;
    private String answerId;
    private String theImageName=null;
    //ImageViews for holding images
    private javafx.scene.image.ImageView imageView1,imageView2,imageView3,imageView4,imageView5;
    private ArrayList<ImageView> imageViews;

    public DragDropWidget(FormEntryPrompt prompt)
    {
        super(prompt);
        System.out.println("In drag&drop widget #####");
        mAnchorPane=new FlowPane();
        formImagesList=new ArrayList<>();
        mAnchorPane.setPrefSize(300,300);
        mPrompt=prompt;
        mItems= prompt.getSelectChoices();
        mCurrentPath=System.getProperty("user.dir");
        String currentFormPath = FormViewController.getInstance().getCurrentFormName();
        String formFileName = currentFormPath.substring(0, currentFormPath.lastIndexOf("."));

        for (int i=0;i<mItems.size();i++){
            String imageUri =
                    mPrompt.getSpecialFormSelectChoiceText(mItems.get(i),
                            FormEntryCaption.TEXT_FORM_IMAGE);
            String imageName = imageUri.substring(imageUri.lastIndexOf("/") + 1);
            formImagesList.add(imageName);
            System.out.println("****image url = " + imageName);
        }

       /* String imageUri =
                mPrompt.getSpecialFormSelectChoiceText(mItems.get(0),
                        FormEntryCaption.TEXT_FORM_IMAGE);
        String imageName = imageUri.substring(imageUri.lastIndexOf("/") + 1);
        System.out.println("****image url = " + imageName);*/

       // imageNameFInal=imageName.substring(0,imageName.indexOf("_"));
        System.out.println("***after substring "+imageNameFInal);
        String directoryName = mCurrentPath + "/forms/" +formFileName+ "-media/";
        System.out.println("*** Currentpath from pictureselect "+directoryName);

        answerId="";
        testDIrectory=new File(directoryName);
        setImages();
    }




    private void imageDropped(DragEvent event) {
        FormViewController formViewController=FormViewController.getInstance();
        System.out.println("inside dropped  ####");
        /*imgX= event.getX()-distX;
        imgY=event.getY()-distY;*/

        imgX= event.getX();
        imgY=event.getY();




        ImageView imageView= (ImageView) event.getSource();
        imageView.setOnDragOver(this::dragOver);

        Node node=(Node) event.getSource();

        Dragboard dragboard=event.getDragboard();

        if (dragboard.hasImage())
        {
            imageView.setImage(dragboard.getImage());
            event.setDropCompleted(true);
        }
        answerId += imageView.getId();

       // SelectChoice sc = mItems.get(Integer.valueOf(answerId));

        /*int status = formViewController.getFormController().answerQuestion(mPrompt.getIndex(),new SelectOneData(new Selection(sc)));
        if (status == FormEntryController.ANSWER_OK) {
            // correct
            Alert alert=new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Answer Correct ");
            alert.show();
        }else {
            Alert alert=new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Answer Wrong ");
            alert.show();
        }
        System.out.println("***status = " + status);

        if (node instanceof ImageView){
            answerId = node.getId();
            System.out.println("** mItems "+mItems.size());*/

           /* Dragboard dragboard=event.getDragboard();

            if (dragboard.hasImage())
            {
                ((ImageView) node).setImage(dragboard.getImage());
            }*/

            /*SelectChoice sc = mItems.get(Integer.valueOf(answerId));
            System.out.println("** Testing ");
            int status = formViewController.getFormController().answerQuestion(mPrompt.getIndex(),new SelectOneData(new Selection(sc)));
            if (status == FormEntryController.ANSWER_OK) {
                // correct
                Alert alert=new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Answer Correct ");
                alert.show();
            }else {
                Alert alert=new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Answer Wrong ");
                alert.show();
            }
            System.out.println("***status = " + status);*/

      //  }



    }

    private void dragOver(DragEvent event)
    {
        ImageView imageView=(ImageView) event.getSource();



        if (event.getGestureSource() != imageView &&
                event.getDragboard().hasImage()) {
            /* allow for both copying and moving, whatever user chooses */
            event.acceptTransferModes(TransferMode.NONE);
        }

    }

    private void createOkButton() {
        Button okButton=new Button("OK");
        okButton.addEventHandler(ActionEvent.ACTION, event -> {
            Alert alert=new Alert(Alert.AlertType.INFORMATION);
            if (answerId.endsWith("0123") || answerId.contains("0123")){
                alert.setHeaderText("ANSWER CORRECT");
                alert.setContentText("OK");
                answerId="";
            }else {
                alert.setHeaderText("ANSWER WRONG");
                alert.setContentText("Start Again");
                answerId="";
            }
            alert.showAndWait();
        });

        mAnchorPane.getChildren().add(okButton);
    }

    private void imageDragged(MouseEvent event) {
        System.out.println("inside dragged ####");

        ImageView imageView=(ImageView) event.getSource();
        FormViewController formViewController=FormViewController.getInstance();
        Dragboard db = imageView.startDragAndDrop(TransferMode.ANY);

        imageView.setVisible(false);

        ClipboardContent clipboardContent=new ClipboardContent();
        clipboardContent.putImage(imageView.getImage());
        db.setContent(clipboardContent);
        event.consume();


        imageView.setOnDragDropped(this::imageDropped);


        answerId += imageView.getId();


        startX= event.getX();
        startY=event.getY();




        distX=startX-imgX;
        distY=startY-imgY;

    }

    private void setImages() {
        System.out.println("Inside setImages ###");

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
            mAnchorPane.getChildren().add(imageViews.get(i));
            imageViews.get(i).setId(String.valueOf(i));
            imageViews.get(i).setOnDragDetected(this::imageDragged);
            imageViews.get(i).setOnDragDropped(this::imageDropped);
            imageViews.get(i).setOnDragOver(this::dragOver);
        }


       imageView5=new ImageView();
       Image image=new Image(this.getClass().getResourceAsStream("/common.png"));
       imageView5.setImage(image);

        FormViewController fvc=FormViewController.getInstance();
        FxViewController.getInstance().getCurrentLayout().add(mAnchorPane,fvc.getColIndex(),fvc.getRowIndex());
        /*fvc.incRowIndex();
        FxViewController.getInstance().getCurrentLayout().add(imageView5,fvc.getColIndex(),fvc.getRowIndex());
        fvc.incRowIndex();*/
        createOkButton();




    }

    private ImageView getImage(String s) {
        System.out.println(s);

        Image image=new Image("file:"+s,100,100,false,false);
        image.isPreserveRatio();
        System.out.println("** Loaded Image "+s);

        return new ImageView(image);
    }

    private ArrayList<String> getAllImages(File directory) throws IOException
    {
        ArrayList<String> resultList=new ArrayList<>();

        File[] files=directory.listFiles();
        //assert files != null;
        for (File file: files != null ? files : new File[0]){
            for (int i=0;i<formImagesList.size();i++)
            {
                if (file!=null && file.getName().equals(formImagesList.get(i)) && file.getName().toLowerCase().endsWith(".png")){
                    resultList.add(file.getAbsolutePath());
                }
            }

        }

        return resultList;

    }

    @Override
    public IAnswerData getAnswer() {
        /*if(answerId != null) {
            SelectChoice sc = mItems.get(Integer.valueOf(answerId));
            return new SelectOneData(new Selection(sc));
        }*/
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
