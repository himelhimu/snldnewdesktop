package com.mpower.clientcollection.controller;

import com.mpower.clientcollection.logic.FormController;
import com.mpower.clientcollection.tasks.SaveToDisk;
import com.mpower.clientcollection.utilities.FileUtils;
import com.mpower.clientcollection.widgets.QuestionWidget;
import com.mpower.clientcollection.widgets.WidgetFactory;

import com.mpower.desktop.config.AppConfiguration;
import com.mpower.desktop.controller.ContentViewController;
import com.mpower.desktop.database.InitializeDatabase;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import org.javarosa.core.model.FormDef;
import org.javarosa.core.model.FormIndex;
import org.javarosa.core.model.GroupDef;
import org.javarosa.core.model.condition.EvaluationContext;
import org.javarosa.core.model.condition.IFunctionHandler;
import org.javarosa.core.model.data.IAnswerData;
import org.javarosa.core.model.instance.InstanceInitializationFactory;
import org.javarosa.core.model.instance.InvalidReferenceException;
import org.javarosa.core.services.PrototypeManager;
import org.javarosa.form.api.FormEntryController;
import org.javarosa.form.api.FormEntryModel;
import org.javarosa.form.api.FormEntryPrompt;

import javax.swing.*;
import javax.swing.text.View;
import javax.xml.parsers.DocumentBuilderFactory;



import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

import javafx.scene.control.Button;
import org.javarosa.model.xform.XFormsModule;
import org.javarosa.xform.parse.XFormParseException;
import org.javarosa.xform.util.XFormUtils;
import org.odk.validate.StubPropertyManager;

/**
 * Created by hemel on 3/30/16.
 */
public class FormViewController {

    /**
     * Classes needed to serialize objects. Need to put anything from JR in here.
     */
    public final static String[] SERIALIABLE_CLASSES = {
            "org.javarosa.core.services.locale.ResourceFileDataSource", // JavaRosaCoreModule
            "org.javarosa.core.services.locale.TableLocaleSource", // JavaRosaCoreModule
            "org.javarosa.core.model.FormDef",
            "org.javarosa.core.model.SubmissionProfile", // CoreModelModule
            "org.javarosa.core.model.QuestionDef", // CoreModelModule
            "org.javarosa.core.model.GroupDef", // CoreModelModule
            "org.javarosa.core.model.instance.FormInstance", // CoreModelModule
            "org.javarosa.core.model.data.BooleanData", //l  CoreModelModule
            "org.javarosa.core.model.data.DateData", // CoreModelModule
            "org.javarosa.core.model.data.DateTimeData", // CoreModelModule
            "org.javarosa.core.model.data.DecimalData", // CoreModelModule
            "org.javarosa.core.model.data.GeoPointData", // CoreModelModule
            "org.javarosa.core.model.data.GeoShapeData", // CoreModelModule
            "org.javarosa.core.model.data.GeoTraceData", // CoreModelModule
            "org.javarosa.core.model.data.IntegerData", // CoreModelModule
            "org.javarosa.core.model.data.LongData", // CoreModelModule
            "org.javarosa.core.model.data.MultiPointerAnswerData", // CoreModelModule
            "org.javarosa.core.model.data.PointerAnswerData", // CoreModelModule
            "org.javarosa.core.model.data.SelectMultiData", // CoreModelModule
            "org.javarosa.core.model.data.SelectOneData", // CoreModelModule
            "org.javarosa.core.model.data.StringData", // CoreModelModule
            "org.javarosa.core.model.data.TimeData", // CoreModelModule
            "org.javarosa.core.model.data.UncastData", // CoreModelModule
            "org.javarosa.core.model.data.helper.BasicDataPointer", // CoreModelModule
            "org.javarosa.core.model.Action", // CoreModelModule
            "org.javarosa.core.model.actions.SetValueAction" //CoreModelModule
    };
    private static FormViewController _VCInstance = null;
    private WidgetFactory mWFactory = null;


    //operation Variables
    public boolean INITIALIZED = false;
    //UI variables
    private Button mSubmitButton = null;
    public static Button mNextButton=null;
    private int mRowIdx = 0;
    private int mColIdx = 0;

    //Form Related Variables
    private FormEntryModel mCurrentModel = null;
    private ArrayList<QuestionWidget> widgets = null;
    private FormEntryController fec = null;
    private FormController fc = null;
    private Label mFormTitle = null;

    private HashMap ansMap;

    public static final class FormsDirectory {
        FormsDirectory (){}

        public static final String APPLICATION_ROOT =  new File(".").getAbsolutePath();

        public static final String FORMS_PATH = APPLICATION_ROOT + File.separator + "forms";
        public static final String INSTANCES_PATH = APPLICATION_ROOT + File.separator + "instances";
        public static final String CACHE_PATH = APPLICATION_ROOT + File.separator + ".cache";
        public static final String METADATA_PATH = APPLICATION_ROOT + File.separator + "metadata";
        public static final String TMPFILE_PATH = CACHE_PATH + File.separator + "tmp.jpg";
        public static final String TMPDRAWFILE_PATH = CACHE_PATH + File.separator + "tmpDraw.jpg";
        public static final String TMPXML_PATH = CACHE_PATH + File.separator + "tmp.xml";
        public static final String LOG_PATH = APPLICATION_ROOT + File.separator + "log";

    }


    private FormViewController(){
        widgets = new ArrayList<>();
        INITIALIZED = true;

    }

    public static FormViewController getInstance(){
        if (_VCInstance == null)
            _VCInstance = new FormViewController();
        return _VCInstance;
    }
    public static void destroyInstance(){
        if(_VCInstance != null)
            _VCInstance = null;
    }
    public void clearInstance(){
        widgets.clear();
        INITIALIZED = false;
        this.getAnswers().clear();
        mRowIdx = 0;
        mColIdx = 0;

        //Form Related Variables
       // mCurrentModel. = null;
       // widgets = null;
       // fec = null;
       // fc = null;
       // mFormTitle = null;
    }

    public void setCurrentModel(FormEntryModel model){
        this.mCurrentModel = model;
    }

    public FormEntryModel getCurrentModel(){
        return mCurrentModel;
    }

    public boolean isFormValid_old(FormEntryModel model,String formPath)  {
        INITIALIZED = false;
        try {
            INITIALIZED = parseEntireForm(model);
            if (INITIALIZED)
               // setCurrentModel(model);
                setFormEntryController(model,formPath);
                setFormController(formPath);


        } catch (InvalidReferenceException e) {
            e.printStackTrace();
        }
        return INITIALIZED;
    }

    public boolean isFormValid(FormEntryModel model,String formPath)  {
        INITIALIZED = false;
        try {

            INITIALIZED = parseEntireForm(model);

            if (INITIALIZED)
                // setCurrentModel(model);
                setFormEntryController(model,formPath);
                setFormController(formPath);
          /*  FormController formController = FormViewController.getInstance()
                    .getFormController();
            formController.stepToNextScreenEvent();
            INITIALIZED = parseEntireFormNew();*/

        } catch (InvalidReferenceException e) {
            e.printStackTrace();
        }
        return INITIALIZED;
    }

    public void setFormController(String formPath) {
        File formXml = new File(formPath);
        String formHash = FileUtils.getMd5Hash(formXml);
        File formBin = new File(FormsDirectory.CACHE_PATH + File.separator + formHash + ".formdef");
        // set paths to /sdcard/mintel/forms/formfilename-media/
        String formFileName = formXml.getName().substring(0, formXml.getName().lastIndexOf("."));
        File formMediaDir = new File( formXml.getParent(), formFileName + "-media");
        fc = new FormController(formMediaDir, fec == null? null : fec, null);

        // Set saved answer path
        if (fc.getInstancePath() == null) {

            // Create new answer folder.
            String time = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss",
                    Locale.ENGLISH).format(Calendar.getInstance().getTime());
            String file = formPath.substring(formPath.lastIndexOf('/') + 1,
                    formPath.lastIndexOf('.'));
            String path = FormsDirectory.INSTANCES_PATH + File.separator + ContentViewController.current_user+File.separator+file + "_"
                    + time;
            if (FileUtils.createFolder(path)) {
                fc.setInstancePath(new File(path + File.separator
                        + file + "_" + time + ".xml"));
            }
        }
        // clean up vars . cleaning will take place after all works with these variables has been done.
        formBin = null;
        formXml = null;
        formPath = null;
    }

    public FormController getFormController(){
        return fc == null? null: fc;
    }

    public void setFormEntryController(FormEntryModel fem,String formPath) {
        this.fec = new FormEntryController(fem);
    }

    public FormEntryController getFormEntryController() {
        if(fec!=null)
            return fec;
        return null;
    }

    private boolean parseEntireForm(FormEntryModel model) throws InvalidReferenceException {
        INITIALIZED = true;
        Set<String> loops = new HashSet<String>();
        // step through every value in the form
        FormIndex idx = FormIndex.createBeginningOfFormIndex();
        int event;
       createFormTitle(model.getFormTitle());
        for (;;) {
            idx = model.incrementIndex(idx);
            event = model.getEvent(idx);
            if ( event == FormEntryController.EVENT_BEGINNING_OF_FORM ) {
                //createNextButton();
               // createNextView();
                break;
            }
            if ( event == FormEntryController.EVENT_END_OF_FORM ) {
                createSubmitButton();
                break;
            }

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
                    INITIALIZED = false;
                    //setError(true);
                    String elementPath = idx.getReference().toString().replaceAll("\\[\\d+\\]", "");
                    System.err.println("Group has no children! Group: " + elementPath + ". The XML is invalid.\n");

                }
            } else if (event != FormEntryController.EVENT_QUESTION) {
                continue;
            } else {
                FormEntryPrompt prompt = model.getQuestionPrompt(idx);
                WidgetFactory.createWidgetFromPrompt(prompt);

            }
        }

        return INITIALIZED;
    }


    private boolean parseEntireFormNew() throws InvalidReferenceException {
        INITIALIZED = true;

        FormIndex idx = FormViewController.getInstance().getFormController().getFormIndex();
        FormEntryModel model = FormViewController.getInstance().getCurrentModel();

        Set<String> loops = new HashSet<String>();
        // step through every value in the form
       // FormIndex idx = FormIndex.createBeginningOfFormIndex();
        int event;
        createFormTitle(model.getFormTitle());
        for (;;) {
            idx = model.incrementIndex(idx);
            event = model.getEvent(idx);
            if ( event == FormEntryController.EVENT_BEGINNING_OF_FORM ) {
                //createNextButton();
                // createNextView();
                break;
            }
            if ( event == FormEntryController.EVENT_END_OF_FORM ) {
                createSubmitButton();
                break;
            }

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
                    INITIALIZED = false;
                    //setError(true);
                    String elementPath = idx.getReference().toString().replaceAll("\\[\\d+\\]", "");
                    System.err.println("Group has no children! Group: " + elementPath + ". The XML is invalid.\n");

                }
            } else if (event != FormEntryController.EVENT_QUESTION) {
                continue;
            } else {

                FormEntryPrompt prompt = model.getQuestionPrompt(idx);
                WidgetFactory.createWidgetFromPrompt(prompt);

            }
        }
        return INITIALIZED;
    }

    public void createNextButton() {

        mNextButton=new Button("Next");
        mNextButton.setOnAction(event -> {
            System.out.println("Next Button CLicked");

            FormController formController=FormViewController.getInstance().getFormController();
            formController.stepToNextScreenEvent();


        });

    }

    private void createNextEvent() {
        System.out.println("Inside th createNextEvent method");
        /*FormController formController=FormViewController.getInstance().getFormController();
        formController.stepToNextScreenEvent();*/
        FormEntryController formEntryController=FormViewController.getInstance().getFormEntryController();
        formEntryController.stepToNextEvent();
    }

    public void createSubmitButton(){
         ansMap=new HashMap();
        mSubmitButton = new Button("Submit");
        mSubmitButton.setOnAction(e -> {
            System.out.print("submitt button clicked\n");
            FormController.FailedConstraint constraint = FormViewController.getInstance().getFormController().saveAllScreenAnswers(
                    FormViewController.getInstance().getAnswers(), false);
            SaveToDisk mSavetoDiskTask = new SaveToDisk();
            boolean saveOutcome = mSavetoDiskTask.exportData(true);
            Iterator<FormIndex> it = FormViewController.getInstance().getAnswers().keySet().iterator();
            FormController formController=getFormController();
            FormIndex formIndex=formController.getFormIndex().createBeginningOfFormIndex();
            FormEntryModel formEntryModel=getCurrentModel();
            System.out.println("Form Prompt ="+formEntryModel.toString());
            System.out.println("Form Index ="+formEntryModel.getFormIndex());
            FormIndex newIndex;
            /*//newIndex=formEntryModel.incrementIndex(formIndex);
            formEntryModel.setQuestionIndex(newIndex);
            FormEntryPrompt formEntryPrompt=formEntryModel.getQuestionPrompt();*/
           // System.out.println("himel ="+FormViewController.getInstance().getFormController().saveAllScreenAnswers(FormViewController.getInstance().getAnswers(),false));

            while (it.hasNext()) {
                FormIndex index = it.next();

                formEntryModel.setQuestionIndex(index);

                FormEntryPrompt formEntryPrompt=formEntryModel.getQuestionPrompt();
                IAnswerData answer = FormViewController.getInstance().getAnswers().get(index);
                if(answer!=null) System.out.println("sabbir ="+answer.getDisplayText().toString()+"Q Text :: "+formEntryPrompt.getLongText());


            }

            createResultDialog();
            try {
                InitializeDatabase.get_instance().SaveProgressToDatabase(ContentViewController.current_user,ContentViewController.current_session);
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            FxViewController.getInstance().setCurrentView("Course Content", AppConfiguration.VIEW_TYPE.COURSE_OVERVIEW);

        });

        FxViewController.getInstance().getCurrentLayout().add(mSubmitButton,getColIndex(),getRowIndex());


    }

    private void createResultDialog() {
        FormController formController=getFormController();
        formController.getCaptionPrompt();
        FormEntryModel model=getCurrentModel();
       // model.decrementIndex(formController.getFormIndex().getTerminal());
        FormEntryPrompt prompt =model.getQuestionPrompt();
        System.out.println("Test Value = " + prompt.getLongText());
        Alert resultAlert=new Alert(Alert.AlertType.INFORMATION);
        resultAlert.setHeaderText("Result");
        resultAlert.setContentText(prompt.getLongText());
        /*widgets.clear();
        FormController formViewController=FormViewController.getInstance().getFormController();
        FormEntryPrompt formEntryPrompt=formViewController.getQuestionPrompt(formViewController.getFormIndex().getTerminal());
        WidgetFactory.createWidgetFromPrompt(formEntryPrompt);
        widgets.add(WidgetFactory.createWidgetFromPrompt(formEntryPrompt));
        AnchorPane anchorPane=new AnchorPane();
        anchorPane.getChildren().add((Node) getWidget().get(1));*/
        // FxViewController.getInstance().getCurrentLayout().add(anchorPane,getColIndex(),getRowIndex());
        //WidgetFactory.createWidgetFromPrompt(prompt);
        //resultAlert.setContentText(getAnswers().toString());
        // resultAlert.setContentText(formController.getQuestionPrompt(formController.getFormIndex().getTerminal().toString()));
        resultAlert.showAndWait();

    }


    private void createResultDialog_old() {
        Alert resultAlert=new Alert(Alert.AlertType.INFORMATION);
        resultAlert.setHeaderText("Your answerwes");
        /*for(int i=0;i<ansMap.size();i++){
          resultAlert.setContentText(ansMap.get(i).toString());
        }*/
        resultAlert.setContentText(getAnswers().toString());
        /*widgets.clear();
        FormController formViewController=FormViewController.getInstance().getFormController();
        FormEntryPrompt formEntryPrompt=formViewController.getQuestionPrompt(formViewController.getFormIndex().getTerminal());
        WidgetFactory.createWidgetFromPrompt(formEntryPrompt);
        widgets.add(WidgetFactory.createWidgetFromPrompt(formEntryPrompt));
        AnchorPane anchorPane=new AnchorPane();
        anchorPane.getChildren().add((Node) getWidget().get(1));*/
       // FxViewController.getInstance().getCurrentLayout().add(anchorPane,getColIndex(),getRowIndex());
        //WidgetFactory.createWidgetFromPrompt(prompt);
        //resultAlert.setContentText(getAnswers().toString());
       // resultAlert.setContentText(formController.getQuestionPrompt(formController.getFormIndex().getTerminal().toString()));
        resultAlert.showAndWait();

    }


    public  ArrayList getWidget(){
        return widgets;
    }
    public  void setWidgets(QuestionWidget q){
        widgets.add(q);
    }
    /**
     * @return a HashMap of answers entered by the user for this set of widgets
     */
    public LinkedHashMap<FormIndex, IAnswerData> getAnswers() {
        LinkedHashMap<FormIndex, IAnswerData> answers = new LinkedHashMap<FormIndex, IAnswerData>();
        Iterator<QuestionWidget> i = widgets.iterator();
        while (i.hasNext()) {
            /*
             * The FormEntryPrompt has the FormIndex, which is where the answer gets stored. The
             * QuestionWidget has the answer the user has entered.
             */
            QuestionWidget q = i.next();
            FormEntryPrompt p = q.getPrompt();

            answers.put(p.getIndex(), q.getAnswer());
        }

        return answers;
    }

    public boolean loadformFromXML(String path){
        System.out.println("Working Directory = " +
                System.getProperty("user.dir"));
        URL url = getClass().getResource(path);
        File src = null;
        try {
            src = new File(path);//url.toURI()
        } catch (Exception e) {
            System.err.println("File: " + src.getAbsolutePath() + " does not exist.");
            e.printStackTrace();
        }
        if ( !src.exists() ) {
            System.err.println("File: " + src.getAbsolutePath() + " does not exist.");
            return false;
        }

        FileInputStream fis = null;
        try {
            fis = new FileInputStream(src);

            // validate well formed xml
            // System.out.println("Checking form...");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            try {
                factory.newDocumentBuilder().parse(new File(path));
            } catch (Exception e) {

                System.err.println("\n\n\n>> XML is invalid. See above for the errors.");
                return false;
            }

            // need a list of classes that formdef uses
            // unfortunately, the JR registerModule() functions do more than this.
            // register just the classes that would have been registered by:
            // new JavaRosaCoreModule().registerModule();
            // new CoreModelModule().registerModule();
            // replace with direct call to PrototypeManager
            PrototypeManager.registerPrototypes(SERIALIABLE_CLASSES);
            // initialize XForms module
            new XFormsModule().registerModule();

            // needed to override rms property manager
            org.javarosa.core.services.PropertyManager
                    .setPropertyManager(new StubPropertyManager());

            // validate if the xform can be parsed.
            try {
                FormDef fd = XFormUtils.getFormFromInputStream(fis);
                if (fd == null) {

                    System.err.println("\n\n\n>> Something broke the parser. Try again.");
                    return false;
                }
                // check for runtime errors
                fd.initialize(true, new InstanceInitializationFactory());

                System.out.println("\n\n>> Xform parsing completed! See above for any warnings.\n");

                // create FormEntryController from formdef
                FormEntryModel fem = new FormEntryModel(fd);
                setCurrentModel(fem);


            } catch (XFormParseException e) {

                System.err.println(e.toString());
                e.printStackTrace();
                System.err.println("\n\n>> XForm is invalid. See above for the errors.");

            } catch (Exception e) {

                System.err.println(e.toString());
                e.printStackTrace();
                System.err.println("\n\n>> Something broke the parser. See above for a hint.");

            }
        } catch (FileNotFoundException e) {

            System.err.println("Please choose a file before attempting to validate.");
            return false;
        } finally {
            if ( fis != null ) {
                try {
                    fis.close();
                } catch (IOException e) {
                    // ignore
                    e.printStackTrace();
                }
            }
        }
        return true;
    }

    private void createFormTitle(String formTitle) {
        mFormTitle = new Label(formTitle);
        FxViewController.getInstance().getCurrentLayout().add(mFormTitle,this.getRowIndex(),this.getColIndex());
        this.incRowIndex();
    }

    public void incRowIndex(){
        this.mRowIdx++;
    }
    public void decRowIndex(){
        this.mRowIdx--;
    }
    public void incColIndex(){
        this.mColIdx++;
    }
    public void decColIndex(){
        this.mColIdx--;
    }
    public int getRowIndex(){
        return this.mRowIdx;
    }
    public int getColIndex(){
        return this.mColIdx;
    }

}
