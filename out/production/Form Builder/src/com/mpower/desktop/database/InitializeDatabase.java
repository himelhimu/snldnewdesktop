package com.mpower.desktop.database;

/**
 * Created by hemel on 4/17/16.
 */

import com.mpower.desktop.config.AppConfiguration;
import com.mpower.desktop.config.AppLogger;

import java.sql.*;

public class InitializeDatabase {
    private static InitializeDatabase _instance = null;
    private Statement mStatement = null;
    private Connection mConn = null;
    private PreparedStatement mPrepRegister = null;
    private PreparedStatement mPrepLogin = null;

    private InitializeDatabase()  {
        try {
            mConn = DriverManager.getConnection("jdbc:sqlite:./"+AppConfiguration.DB_FILE_NAME);
            if (mConn == null)
                System.err.print("connection cannot be established.");
            mStatement = mConn.createStatement();
            createTableIfNotExist();
            createRegisterStatement();
            createLoginStatement();
        } catch (SQLException e){
            AppLogger.getLoggerInstance().writeLog("\n"+getClass().getName()+" "+e.getMessage(),AppConfiguration.APPLICATION_DEBUG);
        }
    }

    private void createTableIfNotExist() {
        try {
            mStatement.executeUpdate("CREATE TABLE IF NOT EXISTS "+AppConfiguration.REGISTRATION_INFO+" (isNew NUMERIC,name varchar(200), gender smallint, Address varchar(200), mobile_no varchar(20), email varchar(35), user_name varchar(200), password varchar(200));");
            mStatement.executeUpdate("CREATE TABLE IF NOT EXISTS "+AppConfiguration.LOGIN_INFO+" (username TEXT,user_type NUMERIC,password TEXT);");
            mStatement.executeUpdate("CREATE TABLE IF NOT EXISTS "+AppConfiguration.PROGRESS_INFO+" (user_name TEXT,completed_no TEXT);");
        } catch (SQLException e) {
            //e.printStackTrace();
            AppLogger.getLoggerInstance().writeLog("\n"+getClass().getName()+" "+e.getMessage(),AppConfiguration.APPLICATION_DEBUG);
        }
    }
    private void createLoginStatement() throws SQLException {
        mPrepLogin = this.mConn.prepareStatement(
                "insert into "+AppConfiguration.LOGIN_INFO+" values (?,?,?);");
    }

    public PreparedStatement getLoginStatement()
    {
        return this.mPrepLogin;
    }

    private void createRegisterStatement() throws SQLException {
        mPrepRegister = this.mConn.prepareStatement(
                "insert into "+AppConfiguration.REGISTRATION_INFO+" values (?,?,?,?,?,?,?,?);");
    }
    public PreparedStatement getRegisterStatement()
    {
        return this.mPrepRegister;
    }

    public boolean isUserExistAndValid(String username,String password){
        boolean isExist = false;
        Statement tmpst = null;
        ResultSet rs = null;
        try {

            tmpst = this.mConn.createStatement();
            rs = tmpst.executeQuery("SELECT EXISTS(SELECT password FROM "+AppConfiguration.LOGIN_INFO+" WHERE username=\""+username+"\" LIMIT 1);");
            while(rs.next()){
                int exist = rs.getInt(1);
                if(exist==1)
                    isExist = true;
                break;

            }

            //this.mConn.close();
        } catch (SQLException e) {
            //e.printStackTrace();
            AppLogger.getLoggerInstance().writeLog("\n"+getClass().getName()+" "+e.getMessage(),AppConfiguration.APPLICATION_DEBUG);
        } finally {
            try {
                tmpst.close();
                rs.close();
            } catch (SQLException e) {
                //e.printStackTrace();
                AppLogger.getLoggerInstance().writeLog("\n"+getClass().getName()+" "+e.getMessage(),AppConfiguration.APPLICATION_DEBUG);
            }

        }
        return isExist;
    }

    public void SaveProgressToDatabase(String current_user, String current_session) {
        PreparedStatement tmpst = null;

        try {
            String sql = "SELECT EXISTS(SELECT completed_no FROM "+AppConfiguration.PROGRESS_INFO+" WHERE user_name like \""+current_user+"\" LIMIT 1);";
            Statement stmt = this.mConn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            int isExist = rs.getInt(1);
            rs.close();
            stmt.close();
            //stmt.close();

                if(isExist == 1){
                    Statement updatestmt = this.mConn.createStatement();
                    String updatesql = "UPDATE "+AppConfiguration.PROGRESS_INFO+" set completed_no = \""+current_session+"\" where user_name like \""+current_user+"\";";
                    updatestmt.executeUpdate(updatesql);
                    updatestmt.close();
                }else{

                    tmpst = this.mConn.prepareStatement(
                            "insert into "+ AppConfiguration.PROGRESS_INFO+" values (?,?);");
                    tmpst.setString(1,current_user);
                    tmpst.setString(2,current_session);
                    tmpst.addBatch();
                    this.getConnection().setAutoCommit(false);
                    tmpst.executeBatch();
                    this.getConnection().setAutoCommit(true);
                    tmpst.close();
                }

        } catch (SQLException e) {
            //e.printStackTrace();
            AppLogger.getLoggerInstance().writeLog("\n"+getClass().getName()+" "+e.getMessage(),AppConfiguration.APPLICATION_DEBUG);
        }
    }

    public String getCurrUserProgress(String username){
        Statement progst = null;
        ResultSet rs = null;
        String currentprogress = "";
        try{
            progst = this.mConn.createStatement();
            rs = progst.executeQuery("select completed_no from "+AppConfiguration.PROGRESS_INFO+" where user_name like \""+username+"\"");
            if (rs.next()){
                currentprogress = rs.getString(1);
            }
            rs.close();
            progst.close();
        } catch (SQLException e){
            //e.printStackTrace();
            AppLogger.getLoggerInstance().writeLog("\n"+getClass().getName()+" "+e.getMessage(),AppConfiguration.APPLICATION_DEBUG);
        }
        return currentprogress;
    }


    public Connection getConnection()
    {
        try {
            if(this.mConn==null || this.mConn.isClosed()) {
                this.mConn = DriverManager.getConnection("jdbc:sqlite:./"+AppConfiguration.DB_FILE_NAME);
            }
        } catch (SQLException e) {
            //e.printStackTrace();
            AppLogger.getLoggerInstance().writeLog("\n"+getClass().getName()+" "+e.getMessage(),AppConfiguration.APPLICATION_DEBUG);
        }
        return this.mConn;
    }
    public static InitializeDatabase get_instance() throws SQLException {
        if(_instance == null)
        {
            _instance = new InitializeDatabase();
        }
        return _instance;
    }

    public void closeDBConnection() throws SQLException {
        this.mConn.close();
    }
}
