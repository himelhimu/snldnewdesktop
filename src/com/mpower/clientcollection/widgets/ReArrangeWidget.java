package com.mpower.clientcollection.widgets;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by sabbir on 12/15/16.
 */
public class ReArrangeWidget implements Initializable {
    @FXML
    private AnchorPane mAnchorPane;

    private ArrayList<ImageView> imageViews;
    private File DIRECTORY=new File("/home/sabbir/Downloads/Form Builder/src/resources/img/rearrange");
    public void setImages()
    {
        //mAnchorPane=new AnchorPane();
        imageViews=new ArrayList<>();
        ArrayList<String> allImagesList=new ArrayList<>();
        try {

            allImagesList=getAllImages(DIRECTORY);
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i=0;i<allImagesList.size();i++){
                imageViews.add(getImage(allImagesList.get(i)));

            }




        for(int i=0;i<imageViews.size();i++)
        {
            mAnchorPane.getChildren().add(imageViews.get(i));
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
    public void initialize(URL location, ResourceBundle resources) {
        setImages();
    }
}
