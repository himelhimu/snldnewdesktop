package com.mpower.clientcollection.widgets;

import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import org.javarosa.core.model.data.IAnswerData;
import org.javarosa.form.api.FormEntryPrompt;

/**
 * Created by sabbir on 12/19/16.
 *  @author sabbir sabbir@mpower-social.com
 */
public class TextWidget extends QuestionWidget {
    private AnchorPane mAnchorPane;
    private Label mLabel;

    TextWidget(FormEntryPrompt prompt){
        super(prompt);
        mAnchorPane=new AnchorPane();
        mLabel=new Label();






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
