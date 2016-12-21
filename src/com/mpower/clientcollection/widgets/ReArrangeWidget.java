package com.mpower.clientcollection.widgets;

import com.mpower.clientcollection.controller.FormViewController;
import com.mpower.clientcollection.controller.FxViewController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import org.javarosa.core.model.data.IAnswerData;
import org.javarosa.form.api.FormEntryPrompt;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by sabbir on 12/15/16.
 */
public class ReArrangeWidget extends QuestionWidget{
    private FlowPane mAnchorPane;

    private ArrayList<ImageView> imageViews;
    private File DIRECTORY=new File("/home/sabbir/Downloads/Form Builder/src/resources/img/rearrange");
    private static String ANSWER="";
    private int mRowIdx=0;
    private int mColIdx=0;

    public ReArrangeWidget(FormEntryPrompt p) {
        super(p);
        System.out.println("In ReArrangeWidget ###");
        mAnchorPane=new FlowPane();
        mAnchorPane.setPrefSize(400,400);
        initialize();
    }

    public void setImages()
    {
        //mAnchorPane=new AnchorPane();
        imageViews=new ArrayList<>();
        ArrayList<String> allImagesList=new ArrayList<>();
        try {

            allImagesList=getAllImages(DIRECTORY);
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

/*        ImageView imageView2=new ImageView(new Image(this.getClass().getClassLoader().getResourceAsStream("resources/img/rearrange/c1q2_2.png")));
        ImageView imageView3=new ImageView(new Image(this.getClass().getClassLoader().getResourceAsStream("resources/img/rearrange/c1q2_3.png")));
        ImageView imageView4=new ImageView(new Image(this.getClass().getClassLoader().getResourceAsStream("resources/img/rearrange/c1q2_4.png")));*/
        for (int i=0;i<mAnchorPane.getChildren().size();i++) {
            //node.setOnMouseDragEntered(mouseDragged());
            Node node = mAnchorPane.getChildren().get(i);

            if (node instanceof ImageView) {

                final ImageView imageView = (ImageView) node;
                // imageView.getId();
                /*imageView.setId(String.valueOf(i));
                ANSWER =ANSWER+imageView.getId();*/
                imageView.setId(String.valueOf(i));

                node.addEventHandler(MouseEvent.MOUSE_DRAGGED, event -> {
                    double imgX = event.getX();
                    double imgY = event.getY();

                   /* imageView.setX(imgX);
                    imageView.setY(imgY);*/

                    /*node.setLayoutX(imgX);
                    node.setLayoutY(imgY);*/
                    node.setTranslateX(imgX);
                    node.setTranslateY(imgY);
                    ((ImageView) node).setX(imgX);
                    ((ImageView) node).setY(imgY);
                    event.consume();
                    System.out.println("### Answer Till Now :" + ANSWER);

                });

                node.addEventHandler(MouseEvent.MOUSE_RELEASED,event -> {

                    ANSWER =ANSWER+imageView.getId();
                    System.out.println("## Answer Till Now :" + ANSWER);

                });
            } else System.out.println("########################NO");
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

        createOkButton();
    }

    private void createOkButton() {
        Button okButton=new Button("OK");
        okButton.addEventHandler(ActionEvent.ACTION, event -> {
            Alert alert=new Alert(Alert.AlertType.INFORMATION);
            if (ANSWER.contains("01234567891011")){
                alert.setHeaderText("ANSWER CORRECT");
                alert.setContentText("OK");
                ANSWER="";
            }else {
                alert.setHeaderText("ANSWER WRONG");
                alert.setContentText("Start Again");
                ANSWER="";
            }
            alert.showAndWait();

        });

        mAnchorPane.getChildren().add(this.getColIndex(), okButton);
        this.incColIndex();
    }


    private ImageView getImage(String s) {
        System.out.println(s);

        Image image=new Image("file:"+s,100,100,false,false);
        System.out.println(image.impl_getUrl());

        return new ImageView(image);
    }

    private ArrayList<String> getAllImages(File directory) throws IOException
    {
        ArrayList<String> resultList=new ArrayList<>();

        File[] files=directory.listFiles();
        //assert files != null;
        for (File file: files != null ? files : new File[0]){
            if (file!=null && file.getName().toLowerCase().endsWith(".png")){
                resultList.add(file.getAbsolutePath());
            }
        }

        return resultList;

    }


    public void initialize() {
        setImages();
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
