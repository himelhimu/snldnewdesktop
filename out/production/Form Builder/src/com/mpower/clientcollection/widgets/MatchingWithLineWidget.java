package com.mpower.clientcollection.widgets;

/**
 * Created by hemel on 4/20/16.
 */

import com.mpower.clientcollection.controller.FormViewController;
import com.mpower.clientcollection.controller.FxViewController;
import com.mpower.desktop.config.AppConfiguration;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeLineCap;
import javafx.stage.Stage;
import org.javarosa.core.model.data.IAnswerData;
import org.javarosa.form.api.FormEntryPrompt;
import sample.Main;

public class MatchingWithLineWidget extends QuestionWidget {

    private Line mFirstLine = null;
    private Line mSecLine = null;
    private Line mThirdLine = null;
    private Line mFourthLine = null;
    private ImageView mFirstImage = null;
    private ImageView mSecondImage = null;
    private ImageView mThirdImage = null;
    private ImageView mFourthImage = null;
    private final int IMAGE_HEIGHT = 100;
    private final int IMAGE_WIDTH = 100;

    private Pane mDrawPane = null;
    private int mLineCount = 1;

    public MatchingWithLineWidget(FormEntryPrompt fep) {
        super(fep);
        mDrawPane = new Pane();
        mDrawPane.setStyle("-fx-border-color: blue;");
        mDrawPane.setPrefWidth(400);
        mDrawPane.setPrefHeight(360);

        initLine();
        initImage();

        EventHandler<MouseEvent> drawLineHandler = new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                if (event.getEventType() == MouseEvent.MOUSE_DRAGGED) {
                    //System.out.println("drag:" + event.getX() + ", " + event.getY());
                    // drag, TODO:
                    // get the last line from out app ??
                    // add current point to the line
                    switch ( mLineCount ){
                        case 1:
                            setLineEndPoint(mFirstLine,event.getX(),event.getY());
                            break;
                        case 2:
                            setLineEndPoint(mSecLine,event.getX(),event.getY());
                            break;
                        case 3:
                            setLineEndPoint(mThirdLine,event.getX(),event.getY());
                            break;
                        case 4:
                            setLineEndPoint(mFourthLine,event.getX(),event.getY());
                            break;
                    }
                } else if (event.getEventType() == MouseEvent.MOUSE_PRESSED) {
                    System.out.println("press"+mLineCount);

                    // press, TODO:
                    // create a new line
                    // add current point to the line
                    // add line to our application (into draw pane)
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
                        default:
                            showLines(false);
                            mLineCount = 0;
                            break;
                    }

                } else if(event.getEventType() == MouseEvent.MOUSE_RELEASED){
                    //System.out.print("mouse released"+mLineCount);
                    checkIntersect();
                    mLineCount++;

                }
                else if(event.getEventType() == MouseEvent.MOUSE_EXITED){
                    System.out.print("mouse released"+mLineCount);
                }
            }
        };

        // let's add event handlers to the drawing pane
        mDrawPane.addEventHandler(MouseEvent.MOUSE_DRAGGED, drawLineHandler);
        mDrawPane.addEventHandler(MouseEvent.MOUSE_PRESSED, drawLineHandler);
        mDrawPane.addEventHandler(MouseEvent.MOUSE_RELEASED, drawLineHandler);
        //if(mLine != null)

        mDrawPane.getChildren().addAll(mFirstLine,mSecLine,mThirdLine,mFourthLine);
        mDrawPane.getChildren().addAll(mFirstImage,mSecondImage,mThirdImage,mFourthImage);



        FormViewController fvc = FormViewController.getInstance();
        //FxViewController.getInstance().getCurrentLayout().getChildren().add(mDrawPane);
       // FxViewController.getInstance().getCurrentLayout().add(mDrawPane, fvc.getColIndex(), fvc.getRowIndex());
        fvc.incRowIndex();

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
        //mFirstLine.setStrokeLineCap(StrokeLineCap.ROUND);

        mSecLine = new Line();
        mSecLine.setStrokeWidth(5);
        mSecLine.setId("l2");

        mThirdLine = new Line();
        mThirdLine.setStrokeWidth(5);
        mThirdLine.setId("l3");

        mFourthLine = new Line();
        mFourthLine.setStrokeWidth(5);
        mFourthLine.setId("l4");
    }

    private void initImage() {
        Image firstImg = new Image(Main.class.getResourceAsStream(AppConfiguration.IMG_PATH+"h.png"));
        Image secondImg = new Image(Main.class.getResourceAsStream(AppConfiguration.IMG_PATH+"i.png"));
        Image thirdImg = new Image(Main.class.getResourceAsStream(AppConfiguration.IMG_PATH+"m.png"));
        Image fourthImg = new Image(Main.class.getResourceAsStream(AppConfiguration.IMG_PATH+"e.png"));

        mFirstImage = new ImageView(firstImg);
        mSecondImage = new ImageView(secondImg);
        mThirdImage = new ImageView(thirdImg);
        mFourthImage = new ImageView(fourthImg);

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
    }

}
