package core;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import org.apache.logging.log4j.core.pattern.AbstractStyleNameConverter.Cyan;

public class user {
  //database
  public static final String userPK = "userName";
  public static final String VERTIFY = "TOKEN";
  private static DB userDB = new DB();
  private static String UinsertCmd = "INSERT INTO USER (USERNAME, PASSWARD) VALUES(?,?)";
  private static String UdeleteCmd = "DELETE FROM USER WHERE USERNAME=?";
  private static String UupdateCmd = "UPDATE USER SET ";
  private static String UselectCmd = "SELECT * FROM USER WHERE ";
  //single user
  private String userName;
  private String passWord;
  private List<conference> StoredCon;
  private interest userInterest;
  private String conferences;
  private String token;
  //mode
  private final static String KEY_SHA = "SHA";
  
  //constructor
  public user(String u, String p, List<conference> myCon){
    userName = u;
    passWord = p;
    setStoredCon(myCon);
  }
  
  public user(String u, String p, String con){
    userName = u;
    passWord = p;
    conferences = con;
  }
  
  public user(ResultSet infor) throws SQLException {
    userName = infor.getString(1);
    passWord = infor.getString(2);
    conferences = infor.getString(3);
    token = infor.getString(4);
  }
  
  public user() {}
  
  //getter and setter
  public String getUserName() {
    return userName;
  }
  public void setUserName(String userName) {
    this.userName = userName;
  }
  public String getPassWord() {
    return passWord;
  }
  public void setPassWord(String passWord) {
    this.passWord = passWord;
  }

  public List<conference> getStoredCon() {
    return StoredCon;
  }

  public void setStoredCon(List<conference> storedCon) {
    StoredCon = storedCon;
  }
  
  
  //normal operation
  /*
   * userName: the user's name
   * password: the user's password
   * return: 
   */
  public static int logIn(String userName, String password, String token) throws ClassNotFoundException, SQLException { 
    String[] name = {userName};
    String[] pk = {userPK};
    List<user> tempU = selectUser(pk, name);
    if(tempU.isEmpty()) {
      return -2;
    }
    else if(tempU.get(0).getPassWord().equals(encytp(password))) {
      String[] temp = {encytp(token), userName};
      String[] restriction = {VERTIFY};
      updateUser(restriction, temp);
      return 0;
    }
    else {
      return -1;
    }
  }
  
  public static int addConference(String user, String conf, int force) throws ClassNotFoundException, SQLException {
    String[] column = {"ADDEDCON"};
    //get old information
    String[] username = {user};
    String[] pk = {userPK};
    List<user> users = selectUser(pk, username);
    String oldInfor = users.get(0).getConferences();
    //update information
    if(oldInfor.contains(conf)) {
      return -1;//exist already
    }
    else {
      if(force!=1) {
        //get conf information
        String[] temp = {conf};
        String[] Cpk = {conference.conferencePK};
        conference tempCon = conference.selectConference(Cpk, temp).get(0);
        int begin = Integer.valueOf(tempCon.getStart_date());
        int end = Integer.valueOf(tempCon.getEnd_date());
        //search all the added conference
        String[] conferences = oldInfor.split("/");
        for(String con : conferences){
          String[] temp1 = {con};
          List<conference> existedCon = conference.selectConference(Cpk, temp1);
          tempCon = existedCon.get(0);//get the added conference
          int begin1 = Integer.valueOf(tempCon.getStart_date());
          int end1 = Integer.valueOf(tempCon.getEnd_date());
          if((begin1>=begin&&begin1<=end1)||(end1>=begin&&end1<=end)) {//check time
            return -2;
          }
        }
      }
      oldInfor +="/" + conf;
      String[] parameter = {oldInfor, user};
      updateUser(column, parameter); 
      return 0;
    }
  }
  

  public static void removeCon(String user, String conference) throws ClassNotFoundException, SQLException {
    String[] column = {"ADDEDCON"};
    //get old information
    String[] username = {user};
    String[] pk = {userPK};
    List<user> users = selectUser(pk, username);
    String oldInfor = users.get(0).getConferences();
    //update information
    if(oldInfor.contains(conference + "/")) {
      oldInfor = oldInfor.replaceAll(conference + "/", "");
    }
    else if(oldInfor.contains("/"+conference)){
      oldInfor = oldInfor.replaceAll("/"+conference, "");
    }
    else {
      oldInfor = oldInfor.replaceAll(conference, "");
    }
    String[] parameter = {oldInfor, user};
    updateUser(column, parameter);
  }
  
  //----------database----------
  /*
   * user part including 
   * signUp: add a user
   * selectUser: choose a qualified user from the database
   * deleteUser: delete a user from the database
   **/
  //parameter: only include one information userName(PK)
  public static void deleteUser(String[] parameter) throws ClassNotFoundException, SQLException{
    //delete user
    userDB.getConnection();
    userDB.executeUpdate(UdeleteCmd, parameter);
    userDB.closeAll();
    //clear interest information
    interest.interestDB.getConnection();
    interest.interestDB.executeUpdate(interest.IdeleteCmd, parameter);
    interest.interestDB.closeAll();
  }
  
  /*
   * column: one of the database column
   * parameter: the value of the certain column
   * return the qualified data packed in a list 
   */
  public static List<user> selectUser(String[] Column, String[] parameter) throws ClassNotFoundException, SQLException{
    LinkedList<user> list = new LinkedList<user>();
    userDB.getConnection();
    String restrict = new String();
    for(String col : Column) {
      restrict += (col + "=? and ");
    }
    restrict = restrict.substring(0, restrict.length()-5);
    ResultSet rs = userDB.executeSelect(UselectCmd + restrict, parameter);
    while(rs.next()){
        user temp = new user(rs);
        list.add(temp);
    }
    userDB.closeAll();
    return list;
  }
  
  /*
   * update the users information
   * input column of the database you want to update
   * the last information in the parameter is PK
   */
  public static void updateUser(String[] Column, String[] parameter) throws ClassNotFoundException, SQLException{
    userDB.getConnection();
    String request = new String();
    for(String column : Column) {
      request += (column + "=?,");
    }
    request = request.substring(0, request.length()-1);
    userDB.executeUpdate(UupdateCmd + request + "where USERNAME = ?", parameter);
    userDB.closeAll();
  }
  
  /*
   * parameter: the user information the order is given in the user class
   * return 0 if succeed 1 when failed
   */
  public static int signUp(String [] parameter) throws ClassNotFoundException, SQLException{
    String[] temp = new String[1];
    temp[0] = parameter[0];
    //DatabaseMetaData dbMeta = (DatabaseMetaData) userDB.getConnection().getMetaData();
    //ResultSet pkRSet = dbMeta.getPrimaryKeys(null, null, "user"); 
    //String primeKey = pkRSet.getString(0);
    String[] pk = {userPK};
    if(selectUser(pk, temp).size()!=0)
        return -1;
    userDB.getConnection();
    //change password
    parameter[1] = encytp(parameter[1]);
    userDB.executeUpdate(UinsertCmd, parameter);
    userDB.closeAll();
//    interest.interestDB.getConnection();
//    float[] weight = new float[5];
//    String[] param = new String[6];
//    param[0] = parameter[0];
//    interest.interestDB.executeUpdate(interest.IinsertCmd, param, weight);
//    interest.interestDB.closeAll();
    return 0;
  }
  
  public interest getUserInterest() {
    return userInterest;
  }

  public void setUserInterest(interest userInterest) {
    this.userInterest = userInterest;
  }

  public String getConferences() {
    return conferences;
  }

  public void setConferences(String conferences) {
    this.conferences = conferences;
  }
  
  public static String encytp(String password) {
    BigInteger sha =null;
    byte[] inputData = password.getBytes(); 
    try {
       MessageDigest messageDigest = MessageDigest.getInstance(KEY_SHA); 
       messageDigest.update(inputData);
       sha = new BigInteger(messageDigest.digest()); 
      } catch (Exception e) {e.printStackTrace();}
      return sha.toString();
   }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }
  
}
