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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
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
import java.util.Vector;

/**
 * Created by sabbir on 12/15/16.
 */
public class PictureSelectWidget extends QuestionWidget {
    public FlowPane mFlowPane;
    private List<SelectChoice> mItems;
    ArrayList<ImageView> imageViews;
    private String mCurrentPath="";
    private File CURRENT_DIRECTORY=null;
    //TODO
    private ArrayList<String> imagesToShow=null;
    private String imageNameFInal="";
    private boolean q5=false;
    private String ques5file="";
    private String ques12File="";
    private File testDIrectory=null;
    private String answerId;
    private File DIRECTORY_1=new File("/home/sabbir/Downloads/Form Builder/src/resources/img/c1_exam");
    private String theImage=null;
   public PictureSelectWidget(FormEntryPrompt prompt)
    {
        super(prompt);
        System.out.println("Im in PictureSelectWidget ###");
        mFlowPane=new FlowPane();
        imagesToShow=new ArrayList<>();
        mItems= prompt.getSelectChoices();
        mCurrentPath=System.getProperty("user.dir");
        String currentFormPath = FormViewController.getInstance().getCurrentFormName();
        String formFileName = currentFormPath.substring(0, currentFormPath.lastIndexOf("."));

        for (int i=0;i<mItems.size();i++)
        {
            String imageUri =
                    mPrompt.getSpecialFormSelectChoiceText(mItems.get(0),
                            FormEntryCaption.TEXT_FORM_IMAGE);
            String imageName = imageUri.substring(imageUri.lastIndexOf("/") + 1);
            theImage=imageName;
            System.out.println("****image url = " + imageName);
            imagesToShow.add(imageName);
        }
        System.out.println("***after substring "+imageNameFInal);
        String directoryName = mCurrentPath + "/forms/" +formFileName+ "-media/";
        System.out.println("*** Currentpath from pictureselect "+directoryName);


        testDIrectory=new File(directoryName);
        answerId = null;
        setImages();

    }

    public void setImages()
    {
        FormViewController formViewController=FormViewController.getInstance();
        String fileName=mPrompt.getSelectItemText(mItems.get(0).selection());
        imageViews=new ArrayList<>();
        ArrayList<String> allImagesList=new ArrayList<>();
        try {

            allImagesList=getAllImages(testDIrectory);
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i=0;i<allImagesList.size();i++){
            imageViews.add(getImage(allImagesList.get(i)));
            imageViews.get(i).setId(String.valueOf(i));

        }

        for(int i=0;i<imageViews.size();i++)
        {
            mFlowPane.getChildren().add(imageViews.get(i));

            mFlowPane.getChildren().get(i).addEventFilter(MouseEvent.MOUSE_CLICKED,event -> {
                Node node=(Node) event.getSource();
                if (node instanceof ImageView){
                    answerId = node.getId();
                    System.out.println("** mItems "+mItems.size());

                    SelectChoice sc = mItems.get(Integer.valueOf(answerId));
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
                    System.out.println("***status = " + status);

                }
            });
        }




        FxViewController.getInstance().getCurrentLayout().add(mFlowPane,formViewController.getColIndex(),formViewController.getRowIndex());
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
            for (int i=0;i<imagesToShow.size();i++)
            if (file!=null && file.getName().equals(imagesToShow.get(i)) && file.getName().toLowerCase().endsWith(".png")){
                resultList.add(file.getAbsolutePath());
            }
        }

        return resultList;

    }

    @Override
    public IAnswerData getAnswer() {
       if(answerId != null) {
           SelectChoice sc = mItems.get(Integer.valueOf(answerId));
           return new SelectOneData(new Selection(sc));
       }
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
