package com.mpower.desktop.config;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE;

/**
 * Created by hemel on 4/26/16.
 * @author sabbir sabbir@mpower-social.com
 */
public class AppLogger {
    private Path p = null;
    private static AppLogger _loginstance = null;

    private AppLogger(){
        p = Paths.get("./appLog.txt");
    }
    public static AppLogger getLoggerInstance(){
        if(_loginstance == null){
            _loginstance = new AppLogger();
        }
        return _loginstance;
    }

    public void writeLog(String s,boolean debug){
        if(debug){
            byte data[] = s.getBytes();
            try (OutputStream out = new BufferedOutputStream(
                    Files.newOutputStream(p, CREATE, APPEND))) {
                out.write(data, 0, data.length);
            } catch (IOException x) {
                System.err.println(x);
            }
        }
    }
}
