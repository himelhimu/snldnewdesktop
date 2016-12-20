/*
 * Copyright (C) 2011 University of Washington
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
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import org.javarosa.core.model.data.IAnswerData;
import org.javarosa.form.api.FormEntryPrompt;



public abstract class QuestionWidget {

    @SuppressWarnings("unused")
    private final static String t = "QuestionWidget";

	private static int idGenerator = 1211322;

	/**
	 * Generate a unique ID to keep Android UI happy when the screen orientation
	 * changes.
	 *
	 * @return
	 */
	public static int newUniqueId() {
		return ++idGenerator;
	}


    protected FormEntryPrompt mPrompt;

    protected final int mQuestionFontsize;
    protected final int mAnswerFontsize;

    private Label mQuestionText;

    private Label mHelpText;


    public QuestionWidget( FormEntryPrompt p) {


        mQuestionFontsize = 20;//ClientCollection.getInstance().getQuestionFontsize();
        
        mAnswerFontsize = mQuestionFontsize + 2;

        mPrompt = p;

        addQuestionText(p);
        addHelpText(p);
    }
    public FormEntryPrompt getPrompt() {
        return mPrompt;
    }

    // Abstract methods
    public abstract IAnswerData getAnswer();


    public abstract void clearAnswer();


    public abstract void setFocus();


    public abstract void setOnLongClickListener();


    /**
     * Add a Views containing the question text, audio (if applicable), and image (if applicable).
     * To satisfy the RelativeLayout constraints, we add the audio first if it exists, then the
     * TextView to fit the rest of the space, then the image if applicable.
     */
    protected void addQuestionText(FormEntryPrompt p) {
        System.out.println("Long Text = " + p.getLongText());
        String imageURI = p.getImageText();
        String audioURI = p.getAudioText();
        String videoURI = p.getSpecialFormQuestionText("video");

        // shown when image is clicked
        String bigImageURI = p.getSpecialFormQuestionText("big-image");

        // Add the text view. Textview always exists, regardless of whether there's text.
        mQuestionText = new Label();
        mQuestionText.setText(p.getLongText());
        mQuestionText.setId(p.getLongText());
        mQuestionText.setFont(new Font("SANS_SERIF", mQuestionFontsize));
        //Border used as padding

        /*mQuestionText.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));*/

        if (p.getLongText() == null || p.getLongText().length() == 0) {
            mQuestionText.setVisible(false);
        }

        System.out.print("constraint-------------------"+p.getIndex());
        FormViewController fvc = FormViewController.getInstance();

        //FxViewController.getInstance().getCurrentLayout().setContent(mQuestionText);
        FxViewController.getInstance().getCurrentLayout().add(mQuestionText,fvc.getColIndex(),fvc.getRowIndex());
        //FxViewController.getInstance().getCurrentLayout().getChildren().add(mQuestionText);
        //fvc.incColIndex();
        //FormViewController.getInstance().addComponent(mQuestionText,0,1,GridBagConstraints.HORIZONTAL);
        fvc.incRowIndex();

    }

    /**
     * Add a TextView containing the help text.
     */
    private void addHelpText(FormEntryPrompt p) {

        String s = p.getHelpText();

        if (s != null && !s.equals("")) {
            mHelpText = new Label();
            mHelpText.setFont(new Font("SERIF", mQuestionFontsize - 3));
            //Border used as padding
            mHelpText.setBorder(new Border(new BorderStroke(Color.BLACK,
                    BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

            // wrap to the widget of view
            mHelpText.setText(s);


            //addView(mHelpText, mLayout);
        }
    }

}
