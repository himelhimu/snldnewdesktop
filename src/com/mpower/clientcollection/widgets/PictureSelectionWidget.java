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
    Vector<SelectChoice> mItems; // may take a while to compute
    //ArrayList<ImageView> buttons;
    //Context context;
    String clickedButtonText;

    private File DIRECTORY=new File("/home/sabbir/Downloads/Form Builder/src/resources/img/rearrange");

    ArrayList<ImageView> imageViewButton;

    public PictureSelectionWidget(FormEntryPrompt prompt) {

        super(prompt);
        System.out.println("In the PictureSelectioin Widget################");
        mAnchorPane=new AnchorPane();
        mItems=(Vector<SelectChoice>) prompt.getSelectChoices();
        imageViewButton=new ArrayList<>();

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

    public void setImages()
    {
        //mAnchorPane=new AnchorPane();
       // imageViews=new ArrayList<>();
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

/*        ImageView imageView2=new ImageView(new Image(this.getClass().getClassLoader().getResourceAsStream("resources/img/rearrange/c1q2_2.png")));
        ImageView imageView3=new ImageView(new Image(this.getClass().getClassLoader().getResourceAsStream("resources/img/rearrange/c1q2_3.png")));
        ImageView imageView4=new ImageView(new Image(this.getClass().getClassLoader().getResourceAsStream("resources/img/rearrange/c1q2_4.png")));*/
        for (int i=0;i<mAnchorPane.getChildren().size();i++) {
            //node.setOnMouseDragEntered(mouseDragged());
            Node node=mAnchorPane.getChildren().get(i);
            node.addEventHandler(MouseEvent.MOUSE_DRAGGED, event -> {
                double imgX = event.getX();
                double imgY = event.getY();

                node.setTranslateX(imgX);
                node.setTranslateY(imgY);
                //event.consume();
            });
        }

        /*handler= (EventHandler<MouseEvent>) event -> {
            double imgX=event.getX();
            double imgY=event.getY();

            for (Node node: mAnchorPane.getChildren())
            {
                node.setLayoutX(imgX);
                node.setLayoutY(imgY);
            }
            event.consume();
        };
*/
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
        //assert files != null;
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
