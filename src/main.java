import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class main {
  //prime key
  static String userPK = "userName";
  static String conferencePK = "topic";
  //for user user connection
  private String UinsertCmd = "INSERT INTO USER VALUES(?,?)";
  private String UdeleteCmd = "DELETE FROM USER WHERE USERNAME=?";
  private String UupdateCmd = "UPDATE USER SET ";
  private String UselectCmd = "SELECT * FROM USER WHERE ";
  //for conference connection
  private String CinsertCmd = "INSERT INTO CONFERENCE VALUES(?,?,?,?,?,?,?,?)";
  private String CdeleteCmd = "DELETE FROM CONFERENCE WHERE ISBN=?";
  private String CupdateCmd = "UPDATE CONFERENCE SET ";
  private String CselectCmd = "SELECT * FROM CONFERENCE WHERE ";
  //for interest connection
  private String IinsertCmd = "INSERT INTO INTEREST VALUES(?,?,?,?,?,?,?,?,?,?,?)";
  private String IdeleteCmd = "DELETE FROM INTEREST WHERE TOPIC=?";
  private String IupdateCmd = "UPDATE INTEREST SET ";
  private String IselectCmd = "SELECT * FROM INTEREST WHERE ";
  //initial three databases
  private DB userDB = new DB();
  private DB interestDB = new DB();
  private DB conferenceDB = new DB();
  
  //test
  public static void main(String[] args) throws ClassNotFoundException, SQLException {
    main console = new main();
    String[] param = new String[1];
    //Scanner in = new Scanner(System.in);
    //String[] aStrings = {"1", "2", "3", "4", "5", "6", "7", "8"};
    //console.addConference(aStrings);
    //System.out.println("done!");
    param[0] = "20171122";
    List<conference> temp = console.selectConference("deadline", param);
    for(conference tConference : temp) {
      System.out.println(tConference.getTopic());
    }
    System.out.println("Done!");
    //console.deleteUser(param);
    //console.addConference(param);
  }
  
  /*
   * user part including 
   * signUp: add a user
   * selectUser: choose a qualified user from the database
   * deleteUser: delete a user from the database
   **/
  //parameter: only include one information userName(PK)
  public void deleteUser(String[] parameter) throws ClassNotFoundException, SQLException{
    //delete user
    userDB.getConnection();
    userDB.executeUpdate(UdeleteCmd, parameter);
    userDB.closeAll();
    //clear interest information
    interestDB.getConnection();
    interestDB.executeUpdate(IdeleteCmd, parameter);
    interestDB.closeAll();
  }
  /*
   * column: one of the database column
   * parameter: the value of the certain column
   * return the qualified data packed in a list 
   */
  public List<user> selectUser(String Column, String[] parameter) throws ClassNotFoundException, SQLException{
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
   * parameter: the user information the order is given in the user class
   * return 0 if succeed 1 when failed
   */
  public int signUp(String [] parameter) throws ClassNotFoundException, SQLException{
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
    interestDB.getConnection();
    float[] weight = new float[5];
    String[] param = new String[6];
    param[0] = parameter[0];
    interestDB.executeUpdate(IinsertCmd, param, weight);
    interestDB.closeAll();
    return 0;
  }
  
  
  /*
   * conference part including:
   * deleteConference: delete a conference data from the database
   * selectConference: select a conference from the database
   * addConference: add a new conference to the database
   */
  //parameter: only include one information userName(PK)
  public void deleteConference(String[] parameter) throws ClassNotFoundException, SQLException{
    //delete conference
    conferenceDB.getConnection();
    conferenceDB.executeUpdate(CdeleteCmd, parameter);
    conferenceDB.closeAll();
  }
  /*
   * column: one of the database column
   * parameter: the value of the certain column
   * return the qualified data packed in a list 
   */
  public List<conference> selectConference(String Column, String[] parameter) throws ClassNotFoundException, SQLException{
    LinkedList<conference> list = new LinkedList<conference>();
    conferenceDB.getConnection();
    ResultSet rs = conferenceDB.executeSelect(CselectCmd + Column + "=?", parameter);
    while(rs.next()){
        conference temp = new conference(rs);
        list.add(temp);
    }
    conferenceDB.closeAll();
    return list;
  }
  /*
   * parameter: the conference information
   * return 0 if succeed 1 when failed
   */
  public int addConference(String[] parameter) throws ClassNotFoundException, SQLException{
    String[] temp = new String[1];
    temp[0] = parameter[0];
    if(selectConference(conferencePK, temp).size()!=0)
        return -1;
    conferenceDB.getConnection();
    conferenceDB.executeUpdate(CinsertCmd, parameter);
    conferenceDB.closeAll();
    return 0;
  }
  
  /*
   * interest part including:
   * deleteInterest: delete a users interest information(when a user is signing out)
   * selectInterest: select a user's interest from the database
   * addInterest: add a user's interest(when adding a user)
   * updateInterest: one for string mode, another for float mode
   */
  //parameter: only include one information userName(PK)
    public void deleteInterest(String[] parameter) throws ClassNotFoundException, SQLException{
      //delete conference
      interestDB.getConnection();
      interestDB.executeUpdate(IdeleteCmd, parameter);
      interestDB.closeAll();
    }
    /*
     * column: one of the database column
     * parameter: the value of the certain column
     * return the qualified data packed in a list 
     */
    public List<interest> selectInterest(String Column, String[] parameter) throws ClassNotFoundException, SQLException{
      LinkedList<interest> list = new LinkedList<interest>();
      interestDB.getConnection();
      ResultSet rs = interestDB.executeSelect(IselectCmd + Column + "=?", parameter);
      while(rs.next()){
          interest temp = new interest(rs);
          list.add(temp);
      }
      interestDB.closeAll();
      return list;
    }
    /*
     * parameter: the intension of the user(based on the parameter in the interest class)
     * return 0 if succeed 1 when failed
     */
    public int addInterest(String[] parameter, float[] weight) throws ClassNotFoundException, SQLException{
      String[] temp = new String[1];
      temp[0] = parameter[3];
      if(selectInterest("USERNAME", temp).size()!=0)
          return -1;
      interestDB.getConnection();
      interestDB.executeUpdate(IinsertCmd, parameter, weight);
      interestDB.closeAll();
      return 0;
    }
    //parameter: the intension of the user(new based on the parameter in the interest class)
    public void updateInterest(String[] parameter) throws ClassNotFoundException, SQLException{
      interestDB.getConnection();
      //update new information
      StringBuffer updateCmd = new StringBuffer(IupdateCmd);
      String[] param = new String[parameter.length/2+1];
      int len = 0;
      //get all the parameter & command 
      for(int i = 1;i<parameter.length;i = i+2){
          updateCmd.append(parameter[i] + "=?, ");
          param[len++] = parameter[i+1];
      }
      param[len] = parameter[0];//USERNAME
      interestDB.executeUpdate(updateCmd.toString().substring(0,updateCmd.length()-2)+" where USERNAME=?", param);
      interestDB.closeAll();
    }
    /*
     * parameter: column of the table start with the PK
     * weight: the value of the corresponding column 
     */
    public void updateInterest(String[] parameter, float[] weight) throws ClassNotFoundException, SQLException{
      interestDB.getConnection();
      //update new information
      StringBuffer updateCmd = new StringBuffer(IupdateCmd);
      //get all the parameter & command 
      for(int i = 1;i<parameter.length;i++){
          updateCmd.append(parameter[i] + "=?, ");
      }
      interestDB.executeUpdate(updateCmd.toString().substring(0,updateCmd.length()-2)+" where USERNAME=" +"'"+ parameter[0] + "'", weight);
      interestDB.closeAll();
  }
}
