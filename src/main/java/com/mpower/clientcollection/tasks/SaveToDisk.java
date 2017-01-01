package com.mpower.clientcollection.tasks;

import com.mpower.clientcollection.controller.FormViewController;
import com.mpower.clientcollection.logic.FormController;
import org.javarosa.core.services.transport.payload.ByteArrayPayload;

import java.io.*;

/**
 * Created by hemel on 3/31/16.
 *  @author sabbir sabbir@mpower-social.com
 */
public class SaveToDisk {

    /**
     * Write's the data to the sdcard, and updates the instances content provider.
     * In theory we don't have to write to disk, and this is where you'd add
     * other methods.
     * @param markCompleted
     * @return
     */
    public boolean exportData(boolean markCompleted) {
        FormController formController = FormViewController.getInstance().getFormController();

        ByteArrayPayload payload;
        try {
            payload = formController.getFilledInFormXml();
            // write out xml
            String instancePath = formController.getInstancePath().getAbsolutePath();
            exportXmlFile(payload, instancePath);

        } catch (IOException e) {
            System.out.println("Error creating serialized payload");
            e.printStackTrace();
            return false;
        }

        // update the mUri. We have exported the reloadable instance, so update status...
        // Since we saved a reloadable instance, it is flagged as re-openable so that if any error
        // occurs during the packaging of the data for the server fails (e.g., encryption),
        // we can still reopen the filled-out form and re-save it at a later time.
        //updateInstanceDatabase(true, true);

        if ( markCompleted ) {
            // now see if the packaging of the data for the server would make it
            // non-reopenable (e.g., encryption or send an SMS or other fraction of the form).
            boolean canEditAfterCompleted = formController.isSubmissionEntireForm();
            boolean isEncrypted = false;

            // build a submission.xml to hold the data being submitted
            // and (if appropriate) encrypt the files on the side

            // pay attention to the ref attribute of the submission profile...
            try {
                payload = formController.getSubmissionXml();
            } catch (IOException e) {
                System.out.println( "Error creating serialized payload");
                e.printStackTrace();
                return false;
            }

            File instanceXml = formController.getInstancePath();
            File submissionXml = new File(instanceXml.getParentFile(), "submission.xml");
            // write out submission.xml -- the data to actually submit to aggregate
            exportXmlFile(payload, submissionXml.getAbsolutePath());



            // At this point, we have:
            // 1. the saved original instanceXml,
            // 2. all the plaintext attachments
            // 2. the submission.xml that is the completed xml (whether encrypting or not)
            // 3. all the encrypted attachments if encrypting (isEncrypted = true).
            //
            // NEXT:
            // 1. Update the instance database (with status complete).
            // 2. Overwrite the instanceXml with the submission.xml
            //    and remove the plaintext attachments if encrypting

            //updateInstanceDatabase(false, canEditAfterCompleted);

            if (  !canEditAfterCompleted ) {
                // AT THIS POINT, there is no going back.  We are committed
                // to returning "success" (true) whether or not we can
                // rename "submission.xml" to instanceXml and whether or
                // not we can delete the plaintext media files.
                //
                // Handle the fall-out for a failed "submission.xml" rename
                // in the InstanceUploader task.  Leftover plaintext media
                // files are handled during form deletion.

                // delete the restore Xml file.
                if ( !instanceXml.delete() ) {
                    System.out.println( "Error deleting " + instanceXml.getAbsolutePath()
                            + " prior to renaming submission.xml");
                    return true;
                }

                // rename the submission.xml to be the instanceXml
                if ( !submissionXml.renameTo(instanceXml) ) {
                    System.out.println( "Error renaming submission.xml to " + instanceXml.getAbsolutePath());
                    return true;
                }
            } else {
                // try to delete the submissionXml file, since it is
                // identical to the existing instanceXml file
                // (we don't need to delete and rename anything).
                if ( !submissionXml.delete() ) {
                    System.out.println( "Error deleting " + submissionXml.getAbsolutePath()
                            + " (instance is re-openable)");
                }
            }

            // if encrypted, delete all plaintext files
            // (anything not named instanceXml or anything not ending in .enc)
            /*if ( isEncrypted ) {
                if ( !EncryptionUtils.deletePlaintextFiles(instanceXml) ) {
                    Log.e(t, "Error deleting plaintext files for " + instanceXml.getAbsolutePath());
                }
            }*/
        }
        return true;
    }

    /**
     * This method actually writes the xml to disk.
     * @param payload
     * @param path
     * @return
     */
    private static boolean exportXmlFile(ByteArrayPayload payload, String path) {
        // create data stream
        InputStream is = payload.getPayloadStream();

        FileOutputStream fout = null;
        byte[] buffer = new byte[2048];
        try {
            fout = new FileOutputStream(path);
            BufferedOutputStream out = new BufferedOutputStream(fout);

            // read from data stream
            int len = is.read(buffer);
            while ( len != -1 ) {
                out.write(buffer, 0, len);
                len = is.read(buffer);
            }

            out.flush();
            fout.getChannel().force(false);
            out.close();
            fout = null;

            return true;

        } catch (IOException e) {
            System.out.println( "Error reading from payload data stream or writing to storage " + e.toString());
            e.printStackTrace();
            return false;
        } finally {
            if ( fout != null ) {
                try {
                    fout.close();
                } catch (IOException e) {
                    // ignored
                }
            }
        }
    }
}
