package com.mpower.clientcollection.widgets;




import org.javarosa.core.model.SelectChoice;
import org.javarosa.core.model.data.IAnswerData;
import org.javarosa.core.model.data.SelectOneData;
import org.javarosa.core.model.data.helper.Selection;
import org.javarosa.core.reference.ReferenceManager;
import org.javarosa.form.api.FormEntryCaption;
import org.javarosa.form.api.FormEntryPrompt;

import java.io.File;
import java.util.ArrayList;
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

    Vector<SelectChoice> mItems; // may take a while to compute
    //ArrayList<ImageView> buttons;
    //Context context;
    String clickedButtonText;

    public PictureSelectionWidget(FormEntryPrompt prompt) {

        super(prompt);
        System.out.println("In the PictureSelectioin Widget################");

        //mItems = prompt.getSelectChoices();
        //buttons = new ArrayList<ImageView>();
        //this.context = context;

        /*View mainVIew = createCurrentQusView(context);
        LinearLayout pictureLayout = (LinearLayout)mainVIew.findViewById(R.id.pictureOptionsLayout);
        setOrientation(LinearLayout.VERTICAL);
        setTitleView(mainVIew, prompt);*/

        // Layout holds the vertical list of buttons
        // LinearLayout buttonLayout = new LinearLayout(context);

        String s = null;
        if (prompt.getAnswerValue() != null) {
            s = ((Selection) prompt.getAnswerValue().getValue()).getValue();
        }

        if (mItems != null) {
            /*View childView = View.inflate(context, R.layout.picture_options_layout, null);
            LinearLayout firstLayout = (LinearLayout)childView.findViewById(R.id.firstPictureLayout);
            LinearLayout secondLayout = (LinearLayout)childView.findViewById(R.id.secondPictureLayout);*/
           /* int itemNumber = 1;

            for (int i = 0; i < mItems.size(); i++) {
                ImageView newButton = new ImageView(getContext());
                newButton.setId(QuestionWidget.newUniqueId());
                LogUtils.informationLog(this, "first_id = " + newButton.getId());
                newButton.setEnabled(!prompt.isReadOnly());
                newButton.setFocusable(!prompt.isReadOnly());
                newButton.setOnClickListener(onButtonClicked);

               newButton.setImageBitmap(imageView(prompt.getSpecialFormSelectChoiceText(mItems.get(i),
                        FormEntryCaption.TEXT_FORM_IMAGE)));
                newButton.setScaleType(ImageView.ScaleType.FIT_START);


                buttons.add(newButton);

                //TODO mPower SNl project
                if(itemNumber == 1){
                    firstLayout.addView(newButton);
                    itemNumber++;
                }else if (itemNumber == 2){
                    secondLayout.addView(newButton);
                    pictureLayout.addView(childView);
                    //reset item number
                    childView = View.inflate(context, R.layout.picture_options_layout, null);
                    itemNumber = 1;
                }
            }

         *//*   LinearLayout testLayout = new LinearLayout(context);
            LayoutParams testParams = new LayoutParams(LayoutParams.FILL_PARENT,
                    LayoutParams.FILL_PARENT);
            testParams.setMargins(40, 35, 40, 5);
            testLayout.setLayoutParams(testParams);
            //buttonLayout.addView(testLayout);
            addView(testLayout);*//*
        }
        addView(mainVIew);*/
        }

   /* private View createCurrentQusView(Context context){
        View childView = View.inflate(context, R.layout.picture_view_layout, null);
        return childView;
    }

    private void setTitleView(View view, FormEntryPrompt prompt){
        String title = prompt.getQuestionText();
        ((TextView)view.findViewById(R.id.textViewTitle)).setText(title);
    }*/

    /*private Bitmap imageView(String imageURI){

        try {
            String imageFilename = ReferenceManager._().DeriveReference(imageURI).getLocalURI();
            final File imageFile = new File(imageFilename);
            if (imageFile.exists()){
                Display display =
                        ((WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE))
                                .getDefaultDisplay();
                int screenWidth = display.getWidth();
                int screenHeight = display.getHeight();
                Bitmap b = FileUtils.getBitmapScaledToDisplay(imageFile, screenWidth, screenHeight);
                //Drawable d = new BitmapDrawable(getResources(), b);
                return b;
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }*/


   /* @Override
    public void clearAnswer() {
        for (ImageView button : this.buttons) {
            *//*if (button.isChecked()) {
                button.setChecked(false);
                return;
            }*//* //TODO
        }
    }

    @Override
    public IAnswerData getAnswer() {
        int i = getCheckedId();
        if (i == -1) {
            return null;
        } else {
            SelectChoice sc = mItems.elementAt(i);
            return new SelectOneData(new Selection(sc));
        }
    }

    @Override
    public void setFocus(Context context) {
        // Hide the soft keyboard if it's showing.
        InputMethodManager inputManager = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(this.getWindowToken(), 0);
    }

    public int getCheckedId() {
        for (int i = 0; i < buttons.size(); ++i) {
            ImageView button = buttons.get(i);
            *//*if (button.getText().toString().equalsIgnoreCase(clickedButtonText)) {
                return i;
            }*//* //TODO
        }
        return -1;
    }

    @Override
    public void setOnLongClickListener(OnLongClickListener l) {
        for (ImageView r : buttons) {
            r.setOnLongClickListener(l);
        }
    }

    @Override
    public void cancelLongPress() {
        super.cancelLongPress();
        for (ImageView button : this.buttons) {
            button.cancelLongPress();
        }
    }

    @Override
    public void setOnClickListener(OnClickListener l) {
        super.setOnClickListener(l);
        LogUtils.informationLog(this, "Changing button color....setOnClickListener");
    }

    OnClickListener onButtonClicked = new OnClickListener() {
        @Override
        public void onClick(View v) {
            LogUtils.informationLog(this, "Changing button color....");
            ImageButton buttonView = (ImageButton)v;
            for (ImageView button : buttons ) {
                if (!(buttonView == button)) {
                    button.setBackgroundColor(Color.LTGRAY);
                }else {
                    LogUtils.informationLog(this, "Changing button color....2222");
                    button.setBackgroundColor(Color.BLUE);
                    //clickedButtonText = button.getText().toString();
                }
            }

            ClientCollection.getInstance().getActivityLogger().logInstanceAction(this, "onCheckedChanged",
                    mItems.get((Integer)buttonView.getTag()).getValue(), mPrompt.getIndex());
        }
    };*/
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
