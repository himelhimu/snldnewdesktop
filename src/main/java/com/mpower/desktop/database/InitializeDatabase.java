package com.mpower.desktop.database;

/**
 * Created by hemel on 4/17/16.
 * @author Sabbir
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

    private String mCurrentPath="";

    private InitializeDatabase()  {
        try {
            Class.forName("org.sqlite.JDBC");
            mCurrentPath=System.getProperty("user.dir");
            System.out.println("*** Current Path "+mCurrentPath);
            System.out.println("####"+"jdbc:sqlite:"+mCurrentPath+"/test_snl_db.db");
           // mConn = DriverManager.getConnection("jdbc:sqlite::"+mCurrentPath+"/"+AppConfiguration.DB_FILE_NAME);
           // mConn = DriverManager.getConnection("jdbc:sqlite:./"+AppConfiguration.DB_FILE_NAME);

            //****RATNA***//TODO
            //mConn = DriverManager.getConnection("jdbc:sqlite::/home/sabbir/Desktop/test/test_snl_db.db"+AppConfiguration.DB_FILE_NAME);
           //mConn = DriverManager.getConnection("jdbc:sqlite:/home/sabbir/Desktop/test/test_snl_db.db");
            mConn=DriverManager.getConnection("jdbc:sqlite:"+mCurrentPath+"/test_snl_db.db");
            System.out.println("Testing = " + mConn.toString());

            if (mConn == null || mConn.isClosed()) System.err.print("connection cannot be established.");
            mStatement = mConn.createStatement();
            System.out.println("Testing1 = " + mStatement.toString());
            createTableIfNotExist();
            createRegisterStatement();
            createLoginStatement();
        } catch (SQLException e){
            System.out.println("*** Error*** ");
            e.printStackTrace();
            AppLogger.getLoggerInstance().writeLog("\n"+getClass().getName()+" "+e.getMessage(),AppConfiguration.APPLICATION_DEBUG);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void createTableIfNotExist() {
        try {
            //***RATNA TODO
            String regTableSql = "CREATE TABLE IF NOT EXISTS "+AppConfiguration.REGISTRATION_INFO+" (isNew NUMERIC,name varchar(200), gender smallint, Address varchar(200), mobile_no varchar(20), email varchar(35), user_name varchar(200), password varchar(200));";
            System.out.println("**Table statement = " + regTableSql);
            //mStatement.executeUpdate("CREATE TABLE IF NOT EXISTS "+AppConfiguration.REGISTRATION_INFO+" (isNew NUMERIC,name varchar(200), gender smallint, Address varchar(200), mobile_no varchar(20), email varchar(35), user_name varchar(200), password varchar(200));");
            mStatement.executeUpdate(regTableSql);

            String loginTableSql = "CREATE TABLE IF NOT EXISTS "+AppConfiguration.LOGIN_INFO+" (username TEXT,user_type NUMERIC,password TEXT);";
            System.out.println("Login table sql = " + loginTableSql);
            mStatement.executeUpdate(loginTableSql);
            mStatement.executeUpdate("CREATE TABLE IF NOT EXISTS "+AppConfiguration.PROGRESS_INFO+" (user_name TEXT,completed_no TEXT);");
        } catch (SQLException e) {
            System.out.println("*** Error when creating table*** ");
            e.printStackTrace();
            AppLogger.getLoggerInstance().writeLog("\n"+getClass().getName()+" "+e.getMessage(),AppConfiguration.APPLICATION_DEBUG);
        }
    }

    private void createTableIfNotExist_old() {
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
        System.out.println("login schema = " + mPrepLogin);
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
            System.out.println("***Connection = "+mConn);
            //tmpst = this.mConn.createStatement();
            mConn = DriverManager.getConnection("jdbc:sqlite:"+mCurrentPath+"/test_snl_db.db");
            tmpst = mConn.createStatement();
            String loginQuery = "SELECT EXISTS(SELECT password FROM "+AppConfiguration.LOGIN_INFO+" WHERE username=\""+username+"\" LIMIT 1);";
            System.out.println("Login query = " + loginQuery);
            rs = tmpst.executeQuery(loginQuery);
            while(rs.next()){
                int exist = rs.getInt(1);
                if(exist==1)
                    isExist = true;
                break;
            }
            //TODO Sabbir 27
            //this.mConn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            AppLogger.getLoggerInstance().writeLog("\n"+getClass().getName()+" "+e.getMessage(),AppConfiguration.APPLICATION_DEBUG);
        } finally {
            try {
                if (tmpst!=null)
                tmpst.close();
                if (rs!=null)
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
                AppLogger.getLoggerInstance().writeLog("\n"+getClass().getName()+" "+e.getMessage(),AppConfiguration.APPLICATION_DEBUG);
            }

        }
        return isExist;
    }


    public boolean isUserNew(String username) {
        try{

            Statement st = this.mConn.createStatement();
            String query = "SELECT isNew FROM "+AppConfiguration.REGISTRATION_INFO+" WHERE user_name like \""+username+"\" LIMIT 1;";

            ResultSet rs = st.executeQuery(query);
            if(rs.next()){
                if(rs.getInt(1) == 1)
                    return true;
                else
                    return false;
            }
            rs.close();
            st.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
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
                this.mConn = DriverManager.getConnection("jdbc:sqlite:"+mCurrentPath+AppConfiguration.DB_FILE_NAME);
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
