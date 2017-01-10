package com.mpower.clientcollection.widgets;

import com.mpower.clientcollection.controller.FormViewController;
import com.mpower.clientcollection.controller.FxViewController;

import com.sun.jndi.toolkit.url.Uri;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import org.javarosa.core.model.SelectChoice;
import org.javarosa.core.model.data.IAnswerData;
import org.javarosa.core.reference.InvalidReferenceException;
import org.javarosa.core.reference.ReferenceManager;
import org.javarosa.form.api.FormEntryPrompt;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Vector;

/**
 * Created by hemel on 4/18/16.
 * @author Sabbir
 */
public class HtmlWidget extends QuestionWidget{
    @FXML
    private GridPane webGridPane;
    public String url;
    List<SelectChoice> mItems;

    public HtmlWidget( FormEntryPrompt prompt) {
        this(prompt, true);

    }

    protected HtmlWidget(FormEntryPrompt prompt, boolean derived) {
        super(prompt);
        mItems= mPrompt.getSelectChoices();
        final WebView browser = new WebView();
        final WebEngine webEngine = browser.getEngine();
        webEngine.setJavaScriptEnabled(true);

        System.out.println("******Showing html view**********");

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(browser);
        String mCurrentPath=System.getProperty("user.dir");
        String currentFormPath = FormViewController.getInstance().getCurrentFormName();
        String formFileName = currentFormPath.substring(0, currentFormPath.lastIndexOf("."));
        String htmlPath = "file:"+mCurrentPath + "/forms/" +formFileName+ "-media/"+ mPrompt.getSelectChoiceText(mItems.get(0)) ;

        String htmlPathOld="file:"+"/home/sabbir/Downloads/snlForms/cncp/forms/1 1 Preparation for birth-media/session_one.html";
        System.out.println("htmlPath"+htmlPath);
        webEngine.load(htmlPath);

        FormViewController fvc = FormViewController.getInstance();
        FxViewController.getInstance().getCurrentLayout().add(scrollPane,fvc.getColIndex(),fvc.getRowIndex());
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

}
