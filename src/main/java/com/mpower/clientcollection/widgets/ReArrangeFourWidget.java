package com.mpower.clientcollection.widgets;

import com.mpower.clientcollection.controller.FormViewController;
import com.mpower.clientcollection.controller.FxViewController;
import com.mpower.desktop.config.AppConfiguration;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;
import org.javarosa.core.model.data.IAnswerData;
import org.javarosa.form.api.FormEntryPrompt;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by sabbir on 12/20/16.
 */
public class ReArrangeFourWidget extends QuestionWidget {
    private FlowPane mAnchorPane;
    ArrayList<ImageView> imageViews;

    private Canvas mCanvas;
    private double INC_X=20,INC_W=150;
    private File DIRECTORY=new File(AppConfiguration.IMG_PATH+"rearrangefour/");
    private File DIRECTORY_NEW=new File("/home/sabbir/Downloads/Form Builder/src/resources/img/rearrangefour");
    private static String ANSWER="";
    private int j=1;

    public ReArrangeFourWidget(FormEntryPrompt prompt){
        super(prompt);
        mAnchorPane=new FlowPane();
        mAnchorPane.setPrefSize(400,400);
        setImages();
       // drawCircle();

    }

    private void drawCircle() {
        mCanvas = new Canvas(300, 250);
        GraphicsContext gc = mCanvas.getGraphicsContext2D();
        drawShapes(gc,4);
        setImages();
        /*Circle circle = new Circle();
        circle.setCenterX(100.0);
        circle.setCenterY(100.0);
        circle.setRadius(50.0);
        circle.setFill(Color.WHITE);
        mAnchorPane.getChildren().add(circle);*/
        mAnchorPane.getChildren().add(mCanvas);
    }

    private void drawShapes(GraphicsContext gc,int n) {
        for (int i=0;i<=n;i++) {
            System.out.println("in Drawshape###");
            gc.setFill(Color.BLACK);
            gc.setStroke(Color.BLACK);
            gc.setLineWidth(2);
            //gc.strokeRoundRect(160, 60, 30, 30, 30, 30);
            gc.strokeOval(INC_X,INC_W, 30, 100);
            INC_X+=40;
        }
    }

    public void setImages()
    {
        //mAnchorPane=new AnchorPane();
        imageViews=new ArrayList<>();
        ArrayList<String> allImagesList=new ArrayList<>();
        try {

            allImagesList=getAllImages(DIRECTORY_NEW);
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i=0;i<allImagesList.size();i++){
            imageViews.add(getImage(allImagesList.get(i)));

        }




        for(int i=0;i<imageViews.size();i++)
        {
            //mAnchorPane.getChildren().add(imageViews.get(i));
            mAnchorPane.getChildren().add(imageViews.get(i));
            imageViews.get(i).setId(String.valueOf(i));
            j++;
        }

/*        ImageView imageView2=new ImageView(new Image(this.getClass().getClassLoader().getResourceAsStream("resources/img/rearrange/c1q2_2.png")));
        ImageView imageView3=new ImageView(new Image(this.getClass().getClassLoader().getResourceAsStream("resources/img/rearrange/c1q2_3.png")));
        ImageView imageView4=new ImageView(new Image(this.getClass().getClassLoader().getResourceAsStream("resources/img/rearrange/c1q2_4.png")));*/
        for (int i=0;i<mAnchorPane.getChildren().size();i++) {
            //node.setOnMouseDragEntered(mouseDragged());
            Node node = mAnchorPane.getChildren().get(i);

            if (node instanceof ImageView) {

               final ImageView imageView = (ImageView) node;
               // imageView.getId();
                /*imageView.setId(String.valueOf(i));
                ANSWER =ANSWER+imageView.getId();*/
                imageView.setId(String.valueOf(i));

                node.addEventHandler(MouseEvent.MOUSE_DRAGGED, event -> {
                    double imgX = event.getX();
                    double imgY = event.getY();

                    /*imageView.setX(imgX);
                    imageView.setY(imgY);*/
                    node.setTranslateX(imgX);
                    node.setTranslateY(imgY);
                    /*node.setLayoutX(imgX);
                    node.setLayoutY(imgY);*/

                    ((ImageView) node).setX(imgX);
                    ((ImageView) node).setY(imgY);
                    event.consume();
                    //System.out.println("### Answer Till Now :" + ANSWER);

                });

                node.addEventHandler(MouseEvent.MOUSE_RELEASED,event -> {

                    ANSWER =ANSWER+imageView.getId();
                    System.out.println("## Answer Till Now :" + ANSWER);

                });
            } else System.out.println("########################NO");
        }

        /*mAnchorPane.addEventHandler(MouseEvent.MOUSE_DRAGGED,event -> {
            Node node= (Node) mAnchorPane.getChildren().get(1);
            ImageView imageView=null;
            if (node instanceof ImageView) {
                imageView = (ImageView) node;
                imageView.getId();
                ANSWER += imageView.getId();
                System.out.println("## Answer Till Now :" + ANSWER);

                node.addEventHandler(MouseEvent.MOUSE_DRAGGED,event1 -> {
                    double imgX = event1.getX();
                    double imgY = event1.getY();

                    node.setTranslateX(imgX);
                    node.setTranslateY(imgY);
                    event.consume();
                    event1.consume();


                });
            }else System.out.println("########################NO");
        });*/

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

        createOkButton();
    }

    private void createOkButton() {
        Button okButton=new Button("OK");
        okButton.addEventHandler(ActionEvent.ACTION,event -> {
            Alert alert=new Alert(Alert.AlertType.INFORMATION);
            if (ANSWER.endsWith("0123") || ANSWER.contains("0123")){
                alert.setHeaderText("ANSWER CORRECT");
                alert.setContentText("OK");
                ANSWER="";
            }else {
                alert.setHeaderText("ANSWER WRONG");
                alert.setContentText("Start Again");
                ANSWER="";
            }
            alert.showAndWait();

        });

        mAnchorPane.getChildren().add(okButton);
    }


    private ImageView getImage(String s) {
        System.out.println(s);

        Image image=new Image("file:"+s,100,100,false,false);
        image.isPreserveRatio();
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
