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
import javafx.scene.control.CheckBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import org.javarosa.core.model.SelectChoice;
import org.javarosa.core.model.data.IAnswerData;
import org.javarosa.core.model.data.SelectMultiData;
import org.javarosa.core.model.data.helper.Selection;
import org.javarosa.form.api.FormEntryCaption;
import org.javarosa.form.api.FormEntryPrompt;


/**
 * SelctMultiWidget handles multiple selection fields using checkboxes.
 * 
 * @author Carl Hartung (carlhartung@gmail.com)
 * @author Yaw Anokwa (yanokwa@gmail.com)
 * @author Sabbir (sabbir@mpower-social.com)
 */
public class  SelectMultiWidget extends QuestionWidget {
    private boolean mCheckboxInit = true;
    List<SelectChoice> mItems;

    private ArrayList<CheckBox> mCheckboxes;

    private GridPane mAnchorPane;
    private int n=1,r=1;


    @SuppressWarnings("unchecked")
    public SelectMultiWidget(FormEntryPrompt prompt) {
        super(prompt);
        mAnchorPane=new GridPane();
        mAnchorPane.setPrefSize(200,200);
        mPrompt = prompt;
        mCheckboxes = new ArrayList<CheckBox>();
        //mItems =(Vector<SelectChoice>) prompt.getSelectChoices();
        mItems=prompt.getSelectChoices();


        Vector<Selection> ve = new Vector<Selection>();
        if (prompt.getAnswerValue() != null) {
            ve = (Vector<Selection>) prompt.getAnswerValue().getValue();
        }

        if (mItems != null) {
            for (SelectChoice mItem : mItems) {
                // no checkbox group so id by answer + offset
                CheckBox c = new CheckBox();
                c.setText(prompt.getSelectChoiceText(mItem));

                for (int vi = 0; vi < ve.size(); vi++) {
                    // match based on value, not key
                    if (mItem.getValue().equals(ve.elementAt(vi).getValue())) {
                        c.setSelected(true);
                        break;
                    }

                }
                mCheckboxes.add(c);
                String audioURI = null;
                audioURI =
                        prompt.getSpecialFormSelectChoiceText(mItem,
                                FormEntryCaption.TEXT_FORM_AUDIO);

                String imageURI = null;
                imageURI =
                        prompt.getSpecialFormSelectChoiceText(mItem,
                                FormEntryCaption.TEXT_FORM_IMAGE);

                String videoURI = null;
                videoURI = prompt.getSpecialFormSelectChoiceText(mItem, "video");

                String bigImageURI = null;
                bigImageURI = prompt.getSpecialFormSelectChoiceText(mItem, "big-image");


                mCheckboxInit = false;

            }




        }
        for (CheckBox mCheckboxe : mCheckboxes) {

            // mAnchorPane.getChildren().add(mCheckboxes.get(i));
            mAnchorPane.add(mCheckboxe, n, r);
            r++;
        }

        FormViewController fvc= FormViewController.getInstance();
        FxViewController.getInstance().getCurrentLayout().add(mAnchorPane,fvc.getColIndex(),fvc.getRowIndex());
        fvc.incRowIndex();
    }

    @Override
    public void clearAnswer () {
        for (CheckBox c : mCheckboxes) {
            if (c.isSelected()) {
                c.setSelected(false);
            }
        }
    }

    @Override
    public void setFocus () {

    }

    @Override
    public void setOnLongClickListener () {

    }


    @Override
    public IAnswerData getAnswer () {
        Vector<Selection> vc = new Vector<Selection>();
        for (int i = 0; i < mCheckboxes.size(); ++i) {
            CheckBox c = mCheckboxes.get(i);
            if (c.isSelected()) {
                SelectChoice test = mItems.get(i);
                vc.add(new Selection(mItems.get(i)));
            }
        }

        if (vc.size() == 0) {
            return null;
        } else {
            return new SelectMultiData(vc);
        }

    }
}
