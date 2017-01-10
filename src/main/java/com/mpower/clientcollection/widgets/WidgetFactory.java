/*
 * Copyright (C) 2009 University of Washington
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

import java.util.Locale;

import com.mpower.clientcollection.controller.FormViewController;
import org.javarosa.core.model.Constants;
import org.javarosa.form.api.FormEntryPrompt;


/**
 * Convenience class that handles creation of widgets.
 *
 * @author Carl Hartung (carlhartung@gmail.com)
 */
public class WidgetFactory {

    /**
     * Returns the appropriate QuestionWidget for the given FormEntryPrompt.
     *
     * @param fep prompt element to be rendered
     *
     */
    static public QuestionWidget createWidgetFromPrompt(FormEntryPrompt fep) {

    	// get appearance hint and clean it up so it is lower case and never null...
        String appearance = fep.getAppearanceHint();
        if ( appearance == null ) appearance = "";
        // for now, all appearance tags are in english...
        appearance = appearance.toLowerCase(Locale.ENGLISH);

        QuestionWidget questionWidget = null;

        switch (fep.getControlType()) {

            case Constants.CONTROL_INPUT:
                switch (fep.getDataType()) {                 
                
                    case Constants.DATATYPE_DATE_TIME:
                       // questionWidget = new DateTimeWidget(context, fep);
                        break;
                    case Constants.DATATYPE_DATE:
                       // questionWidget = new DateWidget(context, fep);
                        break;
                    case Constants.DATATYPE_TIME:
                        //questionWidget = new TimeWidget(context, fep);
                        break;
                    case Constants.DATATYPE_DECIMAL:
                    	if ( appearance.startsWith("ex:") ) {
                           // questionWidget = new ExDecimalWidget(context, fep);
                        } else if (appearance.equals("bearing")) {
                           // questionWidget = new BearingWidget(context, fep);
                        } else {
                           // questionWidget = new DecimalWidget(context, fep);
                        }
                        break;
                    case Constants.DATATYPE_INTEGER:
                    	if ( appearance.startsWith("ex:") ) {
                    		//questionWidget = new ExIntegerWidget(context, fep);
                    	} else {
                    		//questionWidget = new IntegerWidget(context, fep);
                    	}
                        break;
                    case Constants.DATATYPE_GEOPOINT:
                       // questionWidget = new GeoPointWidget(context, fep);
                        break;
                    case Constants.DATATYPE_BARCODE:
                       // questionWidget = new BarcodeWidget(context, fep);
                        break;
                    case Constants.DATATYPE_TEXT:
                    	String query = fep.getQuestion().getAdditionalAttribute(null, "query");

                        if (query != null) {
                           // questionWidget = new ItemsetWidget(context, fep);
                        } else if (appearance.startsWith("printer")) {
                            //questionWidget = new ExPrinterWidget(context, fep);
                        } else if (appearance.startsWith("ex:")) {
                           // questionWidget = new ExStringWidget(context, fep);
                        } else if (appearance.equals("numbers")) {
                            //questionWidget = new StringNumberWidget(context, fep);
                        } else if (appearance.equals("url")) {
                            //questionWidget = new UrlWidget(context, fep);
                            questionWidget=new HtmlWidget(fep);
                        }else if(appearance.equals("html")){
                            System.out.println("I'm here in html");
                        	questionWidget = new HtmlWidget(fep);
                        }else {
                            System.out.println("****Calling String Widget***");
                            questionWidget = new StringWidget(fep);
                        }
                        break;
                    default:
                        questionWidget = new StringWidget(fep);
                        break;
                }
                //if(questionWidget != null)
                //    FormViewController.getInstance().setWidgets(questionWidget);
                break;
            case Constants.CONTROL_IMAGE_CHOOSE:
            	if (appearance.equals("web")) {
            		//questionWidget = new ImageWebViewWidget(context, fep);
        		} else if(appearance.equals("signature")) {
            		//questionWidget = new SignatureWidget(context, fep);
            	} else if(appearance.equals("annotate")) {
            		//questionWidget = new AnnotateWidget(context, fep);
            	} else if(appearance.equals("draw")) {
            		//questionWidget = new DrawWidget(context, fep);
            	} else if(appearance.startsWith("align:")) {
            		//questionWidget = new AlignedImageWidget(context, fep);
            	} else {
            		//questionWidget = new ImageWidget(context, fep);
            	}
                break;
            case Constants.CONTROL_AUDIO_CAPTURE:
                //questionWidget = new AudioWidget(context, fep);
                break;
            case Constants.CONTROL_VIDEO_CAPTURE:
               // questionWidget = new VideoWidget(context, fep);
                break;
            case Constants.CONTROL_SELECT_ONE:
                if (appearance.contains("compact")) {
                    int numColumns = -1;
                    try {
                    	int idx = appearance.indexOf("-");
                    	if ( idx != -1 ) {
                    		numColumns = Integer.parseInt(appearance.substring(idx + 1));
                    	}
                    } catch (Exception e) {
                        // Do nothing, leave numColumns as -1
                       // Log.e("WidgetFactory", "Exception parsing numColumns");
                    }

                    if (appearance.contains("quick")) {
                       // questionWidget = new GridWidget(context, fep, numColumns, true);
                    } else {
                        //questionWidget = new GridWidget(context, fep, numColumns, false);
                        System.out.println("## Calling PictureSelectWidget ###");
                        questionWidget=new PictureSelectWidget(fep);
                    }
                } else if (appearance.equals("minimal")) {
                    //questionWidget = new SpinnerWidget(context, fep);
                }
                // else if (appearance != null && appearance.contains("autocomplete")) {
                // String filterType = null;
                // try {
                // filterType = appearance.substring(appearance.indexOf('-') + 1);
                // } catch (Exception e) {
                // // Do nothing, leave filerType null
                // Log.e("WidgetFactory", "Exception parsing filterType");
                // }
                // questionWidget = new AutoCompleteWidget(context, fep, filterType);
                //
                // }
                else if (appearance.equals("quick")) {
                    //questionWidget = new SelectOneAutoAdvanceWidget(context, fep);
                } else if (appearance.equals("list-nolabel")) {
                    //questionWidget = new ListWidget(context, fep, false);
                } else if (appearance.equals("list")) {
                    //questionWidget = new ListWidget(context, fep, true);
                } else if (appearance.equals("label")) {
                    //questionWidget = new LabelWidget(context, fep);
                }/*else if (appearance.equals("button")) {
            questionWidget = new SelectOneButtonWidget(fep);
        }*/else if (appearance.equals("picture_selection")) {
                    System.out.println("@@@ Calling PictureSelectWidget ####");
                    questionWidget = new PictureSelectWidget(fep);
                    //questionWidget=new ReArrangeWidget(fep);

        }else if(appearance.equals("html")){
            //Log.d("LOG",fep.toString());
            questionWidget = new HtmlWidget(fep);
            System.out.println("WidgetFactory html Call  "+fep.toString());
        } else {
                    questionWidget = new SelectOneWidget(fep);
                }

                break;
            case Constants.CONTROL_SELECT_MULTI:
                if (appearance.contains("compact")) {
                    int numColumns = -1;
                    try {
                    	int idx = appearance.indexOf("-");
                    	if ( idx != -1 ) {
                    		numColumns =
                    				Integer.parseInt(appearance.substring(idx + 1));
                    	}
                    } catch (Exception e) {
                        // Do nothing, leave numColumns as -1
                       // Log.e("WidgetFactory", "Exception parsing numColumns");
                    }

                    //questionWidget = new GridMultiWidget(context, fep, numColumns);
                }else if (appearance.equals("minimal")) {
                   // questionWidget = new SpinnerMultiWidget(context, fep);
                } else if (appearance.equals("list")) {
                    //questionWidget = new ListMultiWidget(context, fep, true);
                } else if (appearance.equals("list-nolabel")) {
                   // questionWidget = new ListMultiWidget(context, fep, false);
                } else if (appearance.equals("label")) {
                   // questionWidget = new LabelWidget(context, fep);
                }else if (appearance.equals("rearrange")) {
                   // questionWidget = new ReArrangeWidget(context, fep);
                    System.out.println("In widget factory Rearrange##");
                    questionWidget=new ReArrangeWidget(fep);

                }else if (appearance.equals("rearrange_four")) {
                    //questionWidget = new ReArrangeFourWidget(context, fep);
                    System.out.println("##Calling ReArrangeFourWidget ###");
                    questionWidget=new ReArrangeFourWidget(fep);
                }else if (appearance.equals("drag-drop")) {
                   // questionWidget=new DragDropWidgetNew(fep);
                    //LogUtils.informationLog(new WidgetFactory(), " Found Drag-dropWidget");
                    System.out.println("In dragDrop #####");
                    questionWidget = new DragDropWidget(fep);
                }else if(appearance.equals("line_draw")){
                    //LogUtils.informationLog(new WidgetFactory(), " Found LineDrawWidget");
                    //questionWidget = new MatchingWithLineWidget(fep);
                    questionWidget=new MatchingWidget(fep);
                } else {
                    System.out.println("** Calling SelectMultiWidget###");
                    questionWidget = new SelectMultiWidget(fep);
                }
                break;
            case Constants.CONTROL_TRIGGER:
                //questionWidget = new TriggerWidget(context, fep);
                break;
            default:
                System.out.println("In WidgetFactory String********");
                questionWidget = new StringWidget(fep);
                break;
        }
        if(questionWidget != null)
            FormViewController.getInstance().setWidgets(questionWidget);
        return questionWidget;
    }

}
