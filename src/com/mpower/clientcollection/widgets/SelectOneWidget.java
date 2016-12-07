package com.mpower.clientcollection.widgets;

import com.mpower.clientcollection.controller.FormViewController;
import com.mpower.clientcollection.controller.FxViewController;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.javarosa.core.model.SelectChoice;
import org.javarosa.core.model.data.IAnswerData;
import org.javarosa.core.model.data.SelectOneData;
import org.javarosa.core.model.data.helper.Selection;
import org.javarosa.form.api.FormEntryCaption;
import org.javarosa.form.api.FormEntryPrompt;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * Created by hemel on 4/19/16.
 */
public class SelectOneWidget extends QuestionWidget {
    List<SelectChoice> mItems; // may take a while to compute
    ArrayList<RadioButton> buttons;

    public SelectOneWidget(FormEntryPrompt prompt) {
        super(prompt);

        mItems = prompt.getSelectChoices();
        buttons = new ArrayList<RadioButton>();

        final ToggleGroup rb_group = new ToggleGroup();


        String s = null;
        if (prompt.getAnswerValue() != null) {
            s = ((Selection) prompt.getAnswerValue().getValue()).getValue();
        }

        if (mItems != null) {
            VBox rbContainer = new VBox();
            for (int i = 0; i < mItems.size(); i++) {
                RadioButton r = new RadioButton();
                r.setToggleGroup(rb_group);
                r.setText(prompt.getSelectChoiceText(mItems.get(i)));
                //r.setTextSize(TypedValue.COMPLEX_UNIT_DIP, mAnswerFontsize);
                r.setId(String.valueOf(i));
                //r.setId(QuestionWidget.newUniqueId());
                buttons.add(r);

                if (mItems.get(i).getValue().equals(s)) {
                    r.setSelected(true);
                }

                rbContainer.getChildren().add(r);
                rbContainer.setVgrow(r, Priority.ALWAYS);

               // r.setOnCheckedChangeListener(this);
            }
            FormViewController fvc = FormViewController.getInstance();
           FxViewController.getInstance().getCurrentLayout().add(rbContainer,fvc.getColIndex(),fvc.getRowIndex());
            //FxViewController.getInstance().getCurrentLayout().getChildren().add(rbContainer);
           // fvc.incRowIndex();
            fvc.incRowIndex();
        }
    }

    @Override
    public void clearAnswer() {
        for (RadioButton button : this.buttons) {
            if (button.isSelected()) {
                button.setSelected(false);
                return;
            }
        }
    }

    @Override
    public void setFocus() {

    }

    @Override
    public void setOnLongClickListener() {

    }

    @Override
    public IAnswerData getAnswer() {
        int i = getCheckedId();
        if (i == -1) {
            return null;
        } else {
            SelectChoice sc = mItems.get(i);
            return new SelectOneData(new Selection(sc));
        }
    }



    public int getCheckedId() {
        for (int i = 0; i < buttons.size(); ++i) {
            RadioButton button = buttons.get(i);
            if (button.isSelected()) {
                return i;
            }
        }
        return -1;
    }
}