import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import javax.print.attribute.standard.RequestingUserName;
import org.apache.logging.log4j.core.appender.db.jdbc.ColumnConfig;
import org.omg.CORBA.PUBLIC_MEMBER;

public class user {
  //database
  static String userPK = "userName";
  private static DB userDB = new DB();
  private static String UinsertCmd = "INSERT INTO USER VALUES(?,?,?)";
  private static String UdeleteCmd = "DELETE FROM USER WHERE USERNAME=?";
  private static String UupdateCmd = "UPDATE USER SET ";
  private static String UselectCmd = "SELECT * FROM USER WHERE ";
  //single user
  private String userName;
  private String passWord;
  private List<conference> StoredCon;
  private interest userInterest;
  private String conferences;
  
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
  public static String logIn(String userName, String password) throws ClassNotFoundException, SQLException { 
    String[] name = {userName};
    String warningInfor;
    List<user> tempU = selectUser(userPK, name);
    if(tempU.isEmpty()) {
      warningInfor = "User not exist!";
    }
    else if(tempU.get(0).getPassWord().equals(password)) {
      //System.out.println(tempU.get(0).toString());
      warningInfor = "success!";
    }
    else {
      warningInfor = "wrong password!";
    }
    return warningInfor;   
  }
  
  public static void addConference(String user, String conference) throws ClassNotFoundException, SQLException {
    String[] column = {"ADDEDCON"};
    //get old information
    String[] username = {user};
    List<user> users = selectUser(userPK, username);
    String oldInfor = users.get(0).getConferences();
    //update information
    oldInfor +="/" + conference;
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
  public static List<user> selectUser(String Column, String[] parameter) throws ClassNotFoundException, SQLException{
    LinkedList<user> list = new LinkedList<user>();
    userDB.getConnection();
    ResultSet rs = userDB.executeSelect(UselectCmd + Column + "=?", parameter);
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
    if(selectUser("USERNAME", temp).size()!=0)
        return -1;
    userDB.getConnection();
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
  
}
