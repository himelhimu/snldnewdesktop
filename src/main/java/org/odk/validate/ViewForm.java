package org.odk.validate;

import org.javarosa.core.model.Constants;
import org.javarosa.core.model.FormIndex;
import org.javarosa.core.model.GroupDef;
import org.javarosa.core.model.SelectChoice;
import org.javarosa.core.model.instance.InvalidReferenceException;
import org.javarosa.form.api.FormEntryCaption;
import org.javarosa.form.api.FormEntryController;
import org.javarosa.form.api.FormEntryModel;
import org.javarosa.form.api.FormEntryPrompt;

import javax.swing.*;
import java.awt.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by hemel on 3/29/16.
 */
public class ViewForm {
    private JFrame viewFormFrame = null;
    private  JLabel questionLabel = null;
    private JTextArea questionTextArea = null;
    private JTextField questionTextField = null;
    private JButton mtestButton = null;
    private GridBagConstraints c = new GridBagConstraints();
    public static ViewForm _viewFormInstance = null;
    private ViewForm(FormEntryModel formModel){
        viewFormFrame = new JFrame("View A Form");

        viewFormFrame.setResizable(true);

        // Exit when the window is closed.
        viewFormFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        viewFormFrame.setLayout(new GridBagLayout());

        // Show the converter.
        viewFormFrame.pack();
        try {
            stepThroughEntireForm(formModel);
        } catch (InvalidReferenceException e) {
            e.printStackTrace();
        }
        viewFormFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        viewFormFrame.setSize(400,400);
        viewFormFrame.setLocationRelativeTo(null);
        viewFormFrame.setVisible(true);
    }

    public static ViewForm getInstance(FormEntryModel tmpModel){
        _viewFormInstance = new ViewForm(tmpModel);
        return _viewFormInstance;
    }

    boolean stepThroughEntireForm(FormEntryModel model) throws InvalidReferenceException {
        boolean outcome = false;
        Set<String> loops = new HashSet<String>();
        // step through every value in the form
        FormIndex idx = FormIndex.createBeginningOfFormIndex();
        int event;
        for (;;) {
            idx = model.incrementIndex(idx);
            event = model.getEvent(idx);
            if ( event == FormEntryController.EVENT_END_OF_FORM ) break;

            if (event == FormEntryController.EVENT_PROMPT_NEW_REPEAT) {
                String elementPath = idx.getReference().toString().replaceAll("\\[\\d+\\]", "");
                if ( !loops.contains(elementPath) ) {
                    loops.add(elementPath);
                    model.getForm().createNewRepeat(idx);
                    idx = model.getFormIndex();
                }
            } else if (event == FormEntryController.EVENT_GROUP) {
                GroupDef gd = (GroupDef) model.getForm().getChild(idx);
                if ( gd.getChildren() == null || gd.getChildren().size() == 0 ) {
                    outcome = true;
                    //setError(true);
                    String elementPath = idx.getReference().toString().replaceAll("\\[\\d+\\]", "");
                    System.err.println("Group has no children! Group: " + elementPath + ". The XML is invalid.\n");
                }
            } else if (event != FormEntryController.EVENT_QUESTION) {
                FormEntryPrompt prompt = model.getQuestionPrompt(idx);
                int dataType = prompt.getDataType();
                int controlType = prompt.getControlType();
                if(controlType == Constants.CONTROL_INPUT){
                    questionLabel = new JLabel(prompt.getQuestionText());
                    questionTextField = new JTextField();

                    c.fill = GridBagConstraints.HORIZONTAL;
                    c.gridx = 0;
                    c.gridy = 1;
                    c.insets = new Insets(0, 7, 0, 0);
                    viewFormFrame.add(questionLabel, c);

                    c.fill = GridBagConstraints.HORIZONTAL;
                    c.gridx = 0;
                    c.gridy = 3;
                    c.insets = new Insets(0, 10, 10, 10);
                    viewFormFrame.add(questionTextField, c);
                }
                //continue;
            } else {
                FormEntryPrompt prompt = model.getQuestionPrompt(idx);
                if ( prompt.getControlType() == Constants.CONTROL_SELECT_MULTI ||
                        prompt.getControlType() == Constants.CONTROL_SELECT_ONE ) {
                    String elementPath = idx.getReference().toString().replaceAll("\\[\\d+\\]", "");
                    List<SelectChoice> items;
                    items = prompt.getSelectChoices();
                    // check for null values...
                    for ( int i = 0 ; i < items.size() ; ++i ) {
                        SelectChoice s = items.get(i);
                        String text = prompt.getSelectChoiceText(s);
                        String image = prompt.getSpecialFormSelectChoiceText(s,
                                FormEntryCaption.TEXT_FORM_IMAGE);
                        if ((text == null || text.trim().length() == 0 ) &&
                                (image == null || image.trim().length() == 0)) {
                            System.err.println("Selection choice label text and image uri are both missing for: " + elementPath + " choice: " + (i+1) + ".\n");
                        }
                        if ( s.getValue() == null || s.getValue().trim().length() == 0) {
                            outcome = true;
                            //setError(true);
                            System.err.println("Selection value is missing for: " + elementPath + " choice: " + (i+1) + ". The XML is invalid.\n");
                        }
                        createQuestion(text,1);
                    }
                }else{
                    createQuestion(prompt.getQuestionText(),1);
                }
            }
        }
        return outcome;
    }
    private void createQuestion(String questionText,int question_type){

        questionLabel = new JLabel(questionText);
        questionTextField = new JTextField();

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 1;
        viewFormFrame.add(questionLabel, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 3;
        c.insets = new Insets(0, 10, 10, 10);
        viewFormFrame.add(questionTextField, c);

    }
}
