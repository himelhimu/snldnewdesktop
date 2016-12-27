package com.mpower.clientcollection.widgets;




import com.mpower.clientcollection.controller.FormViewController;
import com.mpower.clientcollection.controller.FxViewController;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import org.javarosa.core.model.SelectChoice;
import org.javarosa.core.model.data.IAnswerData;
import org.javarosa.core.model.data.SelectOneData;
import org.javarosa.core.model.data.helper.Selection;
import org.javarosa.core.reference.ReferenceManager;
import org.javarosa.form.api.FormEntryCaption;
import org.javarosa.form.api.FormEntryPrompt;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * SelectOnePictureWidgets handles select-one picture fields using radio buttons.
 *
 * @author Carl Hartung (carlhartung@gmail.com)
 * @author Carl Hartung (carlhartung@gmail.com)
 * @author Yaw Anokwa (yanokwa@gmail.com)
 * @author Ratna Halder (ratnacse06@gmail.com)
 */

public class PictureSelectionWidget extends QuestionWidget {
    private AnchorPane mAnchorPane;
    List<SelectChoice> mItems; // may take a while to compute
    //ArrayList<ImageView> buttons;
    //Context context;
    String clickedButtonText;

    private String mCUrrentPath="";
    private File DIRECTORY=new File("/home/sabbir/Downloads/Form Builder/src/resources/img/rearrange");
    private File CURRENT_DIRECTORY=null;

    ArrayList<ImageView> imageViewButton;

    public PictureSelectionWidget(FormEntryPrompt prompt) {

        super(prompt);
        System.out.println("In the PictureSelection Widget ################");
        mAnchorPane=new AnchorPane();
        mItems=(Vector<SelectChoice>) prompt.getSelectChoices();
        imageViewButton=new ArrayList<>();

        setImages();
        String s = null;
        if (prompt.getAnswerValue() != null) {
            s = ((Selection) prompt.getAnswerValue().getValue()).getValue();
        }

        if (mItems != null) {

        }


        }

    public void setImages()
    {
        ArrayList<String> allImagesList=new ArrayList<>();
        try {

            allImagesList=getAllImages(DIRECTORY);
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i=0;i<allImagesList.size();i++){
            imageViewButton.add(getImage(allImagesList.get(i)));

        }




        for(int i=0;i<imageViewButton.size();i++)
        {
            mAnchorPane.getChildren().add(imageViewButton.get(i));
        }

        FormViewController formViewController=FormViewController.getInstance();
        FxViewController.getInstance().getCurrentLayout().add(mAnchorPane,formViewController.getColIndex(),formViewController.getRowIndex());
        formViewController.incRowIndex();
    }


    private ImageView getImage(String s) {
        System.out.println(s);

        Image image=new Image("file:"+s);
        System.out.println(image.impl_getUrl());

        return new ImageView(image);
    }

    private ArrayList<String> getAllImages(File directory) throws IOException
    {
        ArrayList<String> resultList=new ArrayList<>();

        File[] files=directory.listFiles();
        for (File file: files != null ? files : new File[0]){
            if (file!=null && file.getName().toLowerCase().endsWith(".png")){
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
