package com.mpower.clientcollection.widgets;

/**
 * Created by sabbir on 1/10/17.
 */

import com.mpower.clientcollection.controller.FormViewController;
import com.mpower.clientcollection.controller.FxViewController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeLineCap;
import org.javarosa.core.model.SelectChoice;
import org.javarosa.core.model.data.IAnswerData;
import org.javarosa.form.api.FormEntryCaption;
import org.javarosa.form.api.FormEntryPrompt;
import sample.Main;

/**
 * Created by hemel on 4/20/16.
 */

import javafx.scene.layout.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MatchingWidget extends QuestionWidget {
    private  File testDIrectory=null;
    private  String mCurrentPath="";
    private FormEntryPrompt mPrompt;
    private List<SelectChoice> mItems;
    private ArrayList<String> formImagesList;
    private String answerId;
    private ArrayList<ImageView> imageViews;
    private Line mFirstLine = null;
    private Line mSecLine = null;
    private Line mThirdLine = null;
    private Line mFourthLine = null;
    private Line mFifthLine = null;
    private Line mSixthLine=null;
    private Line mSeventhLine=null;
    private Line mEightLine=null;
    private Line mNineLine=null;
    private Line mTenLine=null;
    private ImageView mFirstImage = null;
    private ImageView mSecondImage = null;
    private ImageView mThirdImage = null;
    private ImageView mFourthImage = null;
    private final int IMAGE_HEIGHT = 100;
    private final int IMAGE_WIDTH = 100;
    private FlowPane mDrawPane = null;
    private Pane mFlowPane=null;
    private int mLineCount = 1;
    private AnchorPane anchorPane=null;
    private Pane myPane=null;
    private String answer="";
    public ImageView imageView5,imageView6,imageView7,imageView8,imageView9,imageView10,imageView11,imageView12,imageView13,imageView14,imageView15,imageView16,imageView17,imageView18;

    public MatchingWidget(FormEntryPrompt fep) {
        super(fep);
        System.out.println("in MatchingWidget #####");
        mFlowPane=new AnchorPane();
        //mFlowPane.setPrefWrapLength(400);
        mDrawPane = new FlowPane();
        mDrawPane.setStyle("-fx-border-color: blue;");
        formImagesList=new ArrayList<>();
        /*String url=AppConfiguration.IMG_PATH+"bg.png";
        Image image=new Image(Main.class.getResourceAsStream(AppConfiguration.IMG_PATH+"bg.png"));
        BackgroundImage myBI= new BackgroundImage(new Image(url,32,32,false,true),
                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
       mDrawPane.setBackground(new Background(myBI));*/
        //mDrawPane.setBackground(new ImageView(new Image(Main.class.getResourceAsStream(AppConfiguration.IMG_PATH+"bg.png"))));

        mPrompt=fep;
        mItems= fep.getSelectChoices();
        mCurrentPath=System.getProperty("user.dir");
        String currentFormPath = FormViewController.getInstance().getCurrentFormName();
        String formFileName = currentFormPath.substring(0, currentFormPath.lastIndexOf("."));

        for (int i=0;i<mItems.size();i++){
            String imageUri =
                    mPrompt.getSpecialFormSelectChoiceText(mItems.get(i),
                            FormEntryCaption.TEXT_FORM_IMAGE);
            String imageName = imageUri.substring(imageUri.lastIndexOf("/") + 1);
            formImagesList.add(imageName);
            System.out.println("****image url = " + imageName);
        }

        //System.out.println("***after substring "+imageNameFInal);
        String directoryName = mCurrentPath + "/forms/" +formFileName+ "-media/";
        System.out.println("*** Currentpath from pictureselect "+directoryName);

        answerId="";
        testDIrectory=new File(directoryName);
        setImages();

        initLine();
        //initImage();

        EventHandler<MouseEvent> drawLineHandler = event -> {
            if (event.getEventType() == MouseEvent.MOUSE_DRAGGED) {
                //System.out.println("drag:" + event.getX() + ", " + event.getY());
                // drag, TODO:
                // get the last line from out app ??
                // add current point to the line
//               ImageView imageView=(ImageView) event.getPickResult().getIntersectedNode();
                Node node=event.getPickResult().getIntersectedNode();
                if (node instanceof ImageView) {
                    double x1 = ((ImageView) node).getX();
                    double y1 = ((ImageView) node).getY();
                    System.out.println("### Node :" + node);

                    switch ( mLineCount ){

                        case 1:
                            //ImageView imageView=(ImageView) event.getSource();


                            //ImageView imageView1= new ImageView(node);
                            //System.out.println("### ImageHeight ##"+imageView.getFitHeight());

                            //System.out.println("Event PickResult :"+event.getPickResult());
                            setLineEndPoint(mFirstLine,event.getX(),event.getY());
                            break;
                        case 2:
                            // ImageView imageView=(ImageView) event.getSource();
                            setLineEndPoint(mSecLine,event.getX(),event.getY());
                            break;
                        case 3:
                            setLineEndPoint(mThirdLine,event.getX(),event.getY());
                            break;
                        case 4:
                            setLineEndPoint(mFourthLine,event.getX(),event.getY());
                            break;
                        case 5:
                            setLineEndPoint(mFifthLine,event.getX(),event.getY());
                            break;
                        case 6:
                            setLineEndPoint(mSixthLine,event.getX(),event.getY());
                            break;
                        case 7:
                            setLineEndPoint(mSeventhLine,event.getX(),event.getY());

                            break;
                        case 8:
                            setLineEndPoint(mEightLine,event.getX(),event.getY());

                            break;
                        case 9:
                            setLineEndPoint(mNineLine,event.getX(),event.getY());

                            break;
                        case 10:
                            setLineEndPoint(mTenLine,event.getX(),event.getY());

                            break;
                    }

                }else {
                    event.consume();
                }
            } else if (event.getEventType() == MouseEvent.MOUSE_PRESSED) {
                System.out.println("press"+mLineCount);

                // press, TODO:
                // create a new line
                // add current point to the line
                // add line to our application (into draw pane)
                // ImageView imageView=(ImageView) event.getSource();
                Node node=event.getPickResult().getIntersectedNode();

                if (node instanceof ImageView)
                {
                    double x1=((ImageView) node).getX();
                    double y1=((ImageView) node).getY();
                    System.out.println("### Node :"+node);

                    switch ( mLineCount ){
                        case 1:


                            setLineStartPoint(mFirstLine,event.getX(),event.getY());
                            mFirstLine.setVisible(true);

                            break;
                        case 2:
                            setLineStartPoint(mSecLine,event.getX(),event.getY());
                            mSecLine.setVisible(true);
                            break;
                        case 3:
                            setLineStartPoint(mThirdLine,event.getX(),event.getY());
                            mThirdLine.setVisible(true);
                            break;
                        case 4:
                            setLineStartPoint(mFourthLine,event.getX(),event.getY());
                            mFourthLine.setVisible(true);
                            break;
                        case 5:
                            setLineStartPoint(mFifthLine,event.getX(),event.getY());
                            mFifthLine.setVisible(true);
                            break;
                        case 6:
                            setLineStartPoint(mSixthLine,event.getX(),event.getY());
                            mSixthLine.setVisible(true);
                            break;
                        case 7:
                            setLineStartPoint(mSeventhLine,event.getX(),event.getY());
                            mSeventhLine.setVisible(true);
                            break;
                        case 8:
                            setLineStartPoint(mEightLine,event.getX(),event.getY());
                            mEightLine.setVisible(true);
                            break;
                        case 9:
                            setLineStartPoint(mNineLine,event.getX(),event.getY());
                            mNineLine.setVisible(true);
                            break;
                        case 10:
                            setLineStartPoint(mTenLine,event.getX(),event.getY());
                            mTenLine.setVisible(true);
                            break;
                        default:
                            showLines(false);
                            mLineCount = 0;
                            break;
                    }


                } else{
                    System.out.println("@@@@ Sorry Bro###");
                    event.consume();
                }

            } else if(event.getEventType() == MouseEvent.MOUSE_RELEASED){
                //System.out.print("mouse released"+mLineCount);
                //checkIntersect();
                mLineCount++;

            }
            else if(event.getEventType() == MouseEvent.MOUSE_EXITED){
                System.out.print("mouse released"+mLineCount);
            }
        };

        // let's add event handlers to the drawing pane
        mDrawPane.addEventHandler(MouseEvent.MOUSE_DRAGGED, drawLineHandler);
        mDrawPane.addEventHandler(MouseEvent.MOUSE_PRESSED, drawLineHandler);
        mDrawPane.addEventHandler(MouseEvent.MOUSE_RELEASED, drawLineHandler);
        //if(mLine != null)
        /*mFlowPane.addEventHandler(MouseEvent.MOUSE_DRAGGED, drawLineHandler);
        mFlowPane.addEventHandler(MouseEvent.MOUSE_PRESSED, drawLineHandler);
        mFlowPane.addEventHandler(MouseEvent.MOUSE_RELEASED, drawLineHandler);*/



       mFlowPane.getChildren().addAll(mFirstLine,mSecLine,mThirdLine,mFourthLine,mFifthLine,mSixthLine,mSeventhLine,mEightLine,mNineLine, mTenLine);

    }

    private void setImages() {
        System.out.println("Inside setImages ###");

        imageViews=new ArrayList<>();
        ArrayList<String> allImagesList=new ArrayList<>();
        try {

            allImagesList=getAllImages(testDIrectory);
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i=0;i<allImagesList.size();i++){
            imageViews.add(getImage(allImagesList.get(i)));

        }




        for(int i=0;i<imageViews.size();i++)
        {
            mDrawPane.getChildren().add(imageViews.get(i));
            imageViews.get(i).setId(String.valueOf(i));
            /*imageViews.get(i).setOnDragDetected(this::imageDragged);
            imageViews.get(i).setOnDragDropped(this::imageDropped);
            imageViews.get(i).setOnDragOver(this::dragOver);*/
        }

        /*FormViewController fvc=FormViewController.getInstance();
        FxViewController.getInstance().getCurrentLayout().add(mFlowPane,fvc.getColIndex(),fvc.getRowIndex());*/
        /*fvc.incRowIndex();
        FxViewController.getInstance().getCurrentLayout().add(imageView5,fvc.getColIndex(),fvc.getRowIndex());
        fvc.incRowIndex();*/
        //createOkButton();

        mFlowPane.getChildren().addAll(mDrawPane);
        FormViewController fvc = FormViewController.getInstance();
        FxViewController.getInstance().getCurrentLayout().add(mFlowPane, fvc.getColIndex(), fvc.getRowIndex());
        fvc.incRowIndex();

        createOkButton();


    }

    private void createOkButton() {
        Button okButton=new Button("OK");
        okButton.addEventHandler(ActionEvent.ACTION, event -> {
            Alert alert=new Alert(Alert.AlertType.INFORMATION);
            if ( answer.equals("0123456789") || answer.contains("0123456789")){
                alert.setHeaderText("ANSWER CORRECT");
                alert.setContentText("OK");
                answer="";
            }else {
                alert.setHeaderText("ANSWER WRONG");
                alert.setContentText("Start Again");
                answer="";
                mLineCount=0;
            }
            alert.showAndWait();
        });

        mDrawPane.getChildren().add(okButton);
    }

    private ImageView getImage(String s) {
        System.out.println(s);

        Image image=new Image("file:"+s,100,100,false,false);
        image.isPreserveRatio();
        System.out.println("** Loaded Image "+s);

        return new ImageView(image);
    }

    private ArrayList<String> getAllImages(File directory) throws IOException
    {
        ArrayList<String> resultList=new ArrayList<>();

        File[] files=directory.listFiles();
        //assert files != null;
        for (File file: files != null ? files : new File[0]){
            for (int i=0;i<formImagesList.size();i++)
            {
                if (file!=null && file.getName().equals(formImagesList.get(i)) && file.getName().toLowerCase().endsWith(".png")){
                    resultList.add(file.getAbsolutePath());
                }
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

    private void checkIntersect() {
        if(mFirstLine.getBoundsInParent().intersects(mFirstImage.getBoundsInParent()) && mFirstLine.getBoundsInParent().intersects(mSecondImage.getBoundsInParent()))
            mFirstLine.setStroke(Color.GREEN);
        if(mFirstLine.getBoundsInParent().intersects(mFirstImage.getBoundsInParent()) && mFirstLine.getBoundsInParent().intersects(mThirdImage.getBoundsInParent()))
            mFirstLine.setStroke(Color.RED);
        if(mFirstLine.getBoundsInParent().intersects(mFirstImage.getBoundsInParent()) && mFirstLine.getBoundsInParent().intersects(mFourthImage.getBoundsInParent()))
            mFirstLine.setStroke(Color.RED);

        if(mSecLine.getBoundsInParent().intersects(mSecondImage.getBoundsInParent()) && mSecLine.getBoundsInParent().intersects(mThirdImage.getBoundsInParent()))
            mSecLine.setStroke(Color.GREEN);
        if(mSecLine.getBoundsInParent().intersects(mSecondImage.getBoundsInParent()) && mSecLine.getBoundsInParent().intersects(mFirstImage.getBoundsInParent()))
            mSecLine.setStroke(Color.RED);
        if(mSecLine.getBoundsInParent().intersects(mSecondImage.getBoundsInParent()) && mSecLine.getBoundsInParent().intersects(mFourthImage.getBoundsInParent()))
            mSecLine.setStroke(Color.RED);

        if(mThirdLine.getBoundsInParent().intersects(mThirdImage.getBoundsInParent()) && mThirdLine.getBoundsInParent().intersects(mFourthImage.getBoundsInParent()))
            mThirdLine.setStroke(Color.GREEN);
        if(mThirdLine.getBoundsInParent().intersects(mThirdImage.getBoundsInParent()) && mThirdLine.getBoundsInParent().intersects(mFirstImage.getBoundsInParent()))
            mThirdLine.setStroke(Color.RED);
        if(mThirdLine.getBoundsInParent().intersects(mThirdImage.getBoundsInParent()) && mThirdLine.getBoundsInParent().intersects(mSecondImage.getBoundsInParent()))
            mThirdLine.setStroke(Color.RED);

        if(mFourthLine.getBoundsInParent().intersects(mFourthImage.getBoundsInParent()) && mFourthLine.getBoundsInParent().intersects(mFirstImage.getBoundsInParent()))
            mFourthLine.setStroke(Color.GREEN);
        if(mFourthLine.getBoundsInParent().intersects(mFourthImage.getBoundsInParent()) && mFourthLine.getBoundsInParent().intersects(mSecondImage.getBoundsInParent()))
            mFourthLine.setStroke(Color.RED);
        if(mFourthLine.getBoundsInParent().intersects(mFourthImage.getBoundsInParent()) && mFourthLine.getBoundsInParent().intersects(mThirdImage.getBoundsInParent()))
            mFourthLine.setStroke(Color.RED);


    }

    private void initLine() {
        mFirstLine = new Line();
        mFirstLine.setId("l1");
        mFirstLine.setStrokeWidth(5);
        mFirstLine.setStrokeLineCap(StrokeLineCap.ROUND);

        mSecLine = new Line();
        mSecLine.setStrokeWidth(5);
        mSecLine.setId("l2");

        mThirdLine = new Line();
        mThirdLine.setStrokeWidth(5);
        mThirdLine.setId("l3");

        mFourthLine = new Line();
        mFourthLine.setStrokeWidth(5);
        mFourthLine.setId("l4");

        mFifthLine=new Line();
        mFifthLine.setStrokeWidth(5);
        mFifthLine.setId("l5");

        mSixthLine=new Line();
        mSixthLine.setStrokeWidth(5);
        mSixthLine.setId("l6");

        mSeventhLine=new Line();
        mSeventhLine.setId("l7");
        mSeventhLine.setStrokeWidth(5);

        mEightLine=new Line();
        mSeventhLine.setStrokeWidth(5);
        mEightLine.setId("l8");

        mNineLine=new Line();
        mNineLine.setStrokeWidth(5);
        mNineLine.setId("l9");

        mTenLine=new Line();
        mTenLine.setStrokeWidth(5);
        mTenLine.setId("l10");
    }

    private void initImage() {
        Image firstImg = new Image(Main.class.getResourceAsStream("/c1_exam/c1q20_1.png"));
        Image secondImg = new Image(Main.class.getResourceAsStream("/c1_exam/c1q20_2.png"));
        Image thirdImg = new Image(Main.class.getResourceAsStream("/c1_exam/c1q20_3.png"));
        Image fourthImg = new Image(Main.class.getResourceAsStream("/c1_exam/c1q20_4.png"));
        Image image5 = new Image(Main.class.getResourceAsStream("/c1_exam/c1q20_5.png"));
        Image image6 = new Image(Main.class.getResourceAsStream("/c1_exam/c1q20_6.png"));
        Image image7 = new Image(Main.class.getResourceAsStream("/c1_exam/c1q20_7.png"));
        Image image8 = new Image(Main.class.getResourceAsStream("/c1_exam/c1q20_8.png"));
        Image image9 = new Image(Main.class.getResourceAsStream("/c1_exam/c1q20_9.png"));
        Image image10 = new Image(Main.class.getResourceAsStream("/c1_exam/c1q20_10.png"));
        Image image11 = new Image(Main.class.getResourceAsStream("/c1_exam/c1q20_11.png"));
        Image image12 = new Image(Main.class.getResourceAsStream("/c1_exam/c1q20_12.png"));
        Image image13 = new Image(Main.class.getResourceAsStream("/c1_exam/c1q20_13.png"));
        Image image14 = new Image(Main.class.getResourceAsStream("/c1_exam/c1q20_14.png"));
        Image image15 = new Image(Main.class.getResourceAsStream("/c1_exam/c1q20_15.png"));
        Image image16 = new Image(Main.class.getResourceAsStream("/c1_exam/c1q20_16.png"));
        Image image17 = new Image(Main.class.getResourceAsStream("/c1_exam/c1q20_17.png"));
        Image image18 = new Image(Main.class.getResourceAsStream("/c1_exam/c1q20_18.png"));

        mFirstImage = new ImageView(firstImg);
        mSecondImage = new ImageView(secondImg);
        mThirdImage = new ImageView(thirdImg);
        mFourthImage = new ImageView(fourthImg);

        imageView5=new ImageView(image5);
        imageView6=new ImageView(image6);
        imageView7=new ImageView(image7);
        imageView8=new ImageView(image8);
        imageView9=new ImageView(image9);
        imageView10=new ImageView(image10);
        imageView11=new ImageView(image11);
        imageView12=new ImageView(image12);
        imageView13=new ImageView(image13);
        imageView14=new ImageView(image14);
        imageView15=new ImageView(image15);
        imageView16=new ImageView(image16);
        imageView17=new ImageView(image17);
        imageView18=new ImageView(image18);

        mFirstImage.setFitHeight(IMAGE_HEIGHT);
        mFirstImage.setFitWidth(IMAGE_WIDTH);
        // mFirstImage.fitWidthProperty().bind(mDrawPane.widthProperty());
        mFirstImage.relocate(10,10);


        mSecondImage.setFitHeight(IMAGE_HEIGHT);
        mSecondImage.setFitWidth(IMAGE_WIDTH);
        mSecondImage.relocate(310,10);

        mThirdImage.setFitHeight(IMAGE_HEIGHT);
        mThirdImage.setFitWidth(IMAGE_WIDTH);
        mThirdImage.relocate(300,200);

        mFourthImage.setFitHeight(IMAGE_HEIGHT);
        mFourthImage.setFitWidth(IMAGE_WIDTH);
        mFourthImage.relocate(10,200);

        imageView5.relocate(0,0);
        imageView6.relocate(10,30);
        imageView7.relocate(40,60);
        imageView8.relocate(80,90);
        imageView9.relocate(120,195);
        imageView10.relocate(290,110);
        imageView11.relocate(170,140);
        imageView12.relocate(145,170);
        imageView13.relocate(195,220);
        imageView14.relocate(210,250);
        imageView15.relocate(330,270);
        imageView16.relocate(250,300);
        imageView17.relocate(270,350);
        imageView18.relocate(0,325);

    }


    private void setLineStartPoint(Line lineobj,double startX,double startY){
        lineobj.setStartX(startX);
        lineobj.setStartY(startY);
    }
    private void setLineEndPoint(Line lineobj,double endX,double endY){
        lineobj.setEndX(endX);
        lineobj.setEndY(endY);
    }
    private void showLines(boolean isvisible){
        mFirstLine.setVisible(isvisible);
        mSecLine.setVisible(isvisible);
        mThirdLine.setVisible(isvisible);
        mFourthLine.setVisible(isvisible);
        mFifthLine.setVisible(isvisible);
        mSixthLine.setVisible(isvisible);
        mSeventhLine.setVisible(isvisible);
        mEightLine.setVisible(isvisible);
        mNineLine.setVisible(isvisible);
        mTenLine.setVisible(isvisible);
    }

}
