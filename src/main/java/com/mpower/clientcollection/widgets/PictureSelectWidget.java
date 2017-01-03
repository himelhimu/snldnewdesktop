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
    private String imageNameFInal="";
    private boolean q5=false;
    private String ques5file="";
    private String ques12File="";
    private File testDIrectory=null;
    private File DIRECTORY_1=new File("/home/sabbir/Downloads/Form Builder/src/resources/img/c1_exam");
   public PictureSelectWidget(FormEntryPrompt prompt)
    {
        super(prompt);
        System.out.println("Im in PictureSelectWidget ###");
        mFlowPane=new FlowPane();
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

    public void setImages()
    {
        //String fileName=mPrompt.getSelectChoiceText(mItems.get(1));
        String fileName=mPrompt.getSelectItemText(mItems.get(0).selection());
        //System.out.println("### after substring "+fileName);
        //String directoryName = mCurrentPath + "/forms/" +formFileName+ "-media/"+ mPrompt.getSelectChoiceText(mItems.get(0));
        //mAnchorPane=new AnchorPane();
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
            mFlowPane.getChildren().add(imageViews.get(i));
        }


        FormViewController formViewController=FormViewController.getInstance();
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
}
