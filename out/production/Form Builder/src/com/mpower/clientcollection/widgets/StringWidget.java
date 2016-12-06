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

import com.mpower.clientcollection.controller.FormViewController;
import com.mpower.clientcollection.controller.FxViewController;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import org.javarosa.core.model.data.IAnswerData;
import org.javarosa.core.model.data.StringData;
import org.javarosa.form.api.FormEntryPrompt;





/**
 * The most basic widget that allows for entry of any text.
 *
 * @author Carl Hartung (carlhartung@gmail.com)
 * @author Yaw Anokwa (yanokwa@gmail.com)
 */
public class StringWidget extends QuestionWidget {
	private static final String ROWS = "rows";

    boolean mReadOnly = false;
    protected TextField mAnswer;

    public StringWidget( FormEntryPrompt prompt) {
    	this(prompt, true);

    }

    protected StringWidget(FormEntryPrompt prompt, boolean derived) {
        super(prompt);
        mAnswer = new TextField();
        mAnswer.setId(""+QuestionWidget.newUniqueId());
        mReadOnly = prompt.isReadOnly();
        mAnswer.setFont(new Font("SANS_SERIF",  mAnswerFontsize));
        mAnswer.setPrefWidth(200);
        mAnswer.setPrefHeight(20);

        String s = prompt.getAnswerText();
        if (s != null) mAnswer.setText(s);
        FormViewController fvc = FormViewController.getInstance();
        FxViewController.getInstance().getCurrentLayout().add(mAnswer,fvc.getColIndex(),fvc.getRowIndex());
        //FxViewController.getInstance().getCurrentLayout().getChildren().add(mAnswer);
        //fvc.incRowIndex();
        //fvc.decColIndex();

        //FormViewController.getInstance().addComponent(mAnswer,2,1,GridBagConstraints.HORIZONTAL);

    }



    @Override
    public void clearAnswer() {
        mAnswer.setText(null);
    }

    @Override
    public void setFocus() {

    }

    @Override
    public void setOnLongClickListener() {

    }


    @Override
    public IAnswerData getAnswer() {

    	String s = mAnswer.getText().toString();
        if (s == null || s.equals("")) {
            return null;
        } else {
            return new StringData(s);
        }
    }




}
