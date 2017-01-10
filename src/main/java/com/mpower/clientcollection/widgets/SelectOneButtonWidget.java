/*
 * Copyright (C) 2009 University of Washington
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package com.mpower.clientcollection.widgets;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import com.mpower.clientcollection.controller.FormViewController;
import com.mpower.clientcollection.controller.FxViewController;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import org.javarosa.core.model.SelectChoice;
import org.javarosa.core.model.data.IAnswerData;
import org.javarosa.core.model.data.SelectOneData;
import org.javarosa.core.model.data.helper.Selection;
import org.javarosa.form.api.FormEntryCaption;
import org.javarosa.form.api.FormEntryPrompt;


/**
 * SelectOneWidgets handles select-one fields using radio buttons.
 *
 * @author Carl Hartung (carlhartung@gmail.com)
 * @author Carl Hartung (carlhartung@gmail.com)
 * @author Yaw Anokwa (yanokwa@gmail.com)
 * @author Ratna Halder (ratnacse06@gmail.com)
 * @author Sabbir
 */
public class SelectOneButtonWidget extends QuestionWidget {

    List<SelectChoice> mItems; // may take a while to compute
    ArrayList<Button> buttons;
    String clickedButtonText;

    public Button newButton;

    public SelectOneButtonWidget(FormEntryPrompt prompt) {
        super(prompt);
        System.out.println("In the SelectOneButton####");
        // mItems = prompt.getSelectChoices();
        mItems = prompt.getSelectChoices();
        buttons = new ArrayList<>();


        // Layout holds the vertical list of buttons

        String s = null;
        if (prompt.getAnswerValue() != null) {
            s = ((Selection) prompt.getAnswerValue().getValue()).getValue();
        }

        if (mItems != null) {
            for (int i = 0; i < mItems.size(); i++) {
               /* Button newButton = new Button(getContext());
                newButton.setText(prompt.getSelectChoiceText(mItems.get(i)));
                newButton.setTextSize(TypedValue.COMPLEX_UNIT_DIP, mAnswerFontsize);
                newButton.setTag(Integer.valueOf(i));
                newButton.setId(QuestionWidget.newUniqueId());
                LogUtils.informationLog(this, "first_id = " + newButton.getId());
                newButton.setEnabled(!prompt.isReadOnly());
                newButton.setFocusable(!prompt.isReadOnly());
                newButton.setOnClickListener(onButtonClicked);*/

                newButton = new Button();
                newButton.setText(prompt.getSelectChoiceText(mItems.get(i)));
                newButton.setId("" + QuestionWidget.newUniqueId());
                newButton.setFocusTraversable(!prompt.isReadOnly());
                newButton.setOnAction(event -> {
                    onButtonClicked();
                });

                buttons.add(newButton);

                String audioURI = null;
                audioURI = prompt.getSpecialFormSelectChoiceText(mItems.get(i),
                        FormEntryCaption.TEXT_FORM_AUDIO);

                String imageURI = null;
                imageURI = prompt.getSpecialFormSelectChoiceText(mItems.get(i),
                        FormEntryCaption.TEXT_FORM_IMAGE);

                String videoURI = null;
                videoURI = prompt.getSpecialFormSelectChoiceText(mItems.get(i),
                        "video");

                String bigImageURI = null;
                bigImageURI = prompt.getSpecialFormSelectChoiceText(
                        mItems.get(i), "big-image");

                FormViewController formViewController = FormViewController.getInstance();
                /// FxViewController.getInstance().getCurrentLayout().setContent(newButton);
                FxViewController.getInstance().getCurrentLayout().add(newButton, formViewController.getColIndex(), formViewController.getRowIndex());
                formViewController.incColIndex();
            }
        }
    }

    private void onButtonClicked() {
        for(Button button:this.buttons){
            if (button.isArmed()){
                button.setStyle("-fx-base: #b6e7c9");
            }
        }
    }

    @Override
    public void clearAnswer() {
        for (Button button : this.buttons) {
            /*if (button.isChecked()) {
                button.setChecked(false);
                return;
            }*/ //TODO
        }
    }

    @Override
    public void setFocus() {
       for(Button button:this.buttons){
           if (button.isFocused()){
               button.setStyle("-fx-font: 22 arial; -fx-base: #b6e7c9;");
           }
       }

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
            Button button = buttons.get(i);
            if (button.getText().equalsIgnoreCase(clickedButtonText)) {
                return i;
            }
        }
        return -1;
    }


}
