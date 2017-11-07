import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class interest {
  //for interest connection
  public static DB interestDB = new DB();
  public static String IinsertCmd = "INSERT INTO INTEREST VALUES(?,?,?,?,?,?,?,?,?,?,?)";
  public static String IdeleteCmd = "DELETE FROM INTEREST WHERE TOPIC=?";
  private static String IupdateCmd = "UPDATE INTEREST SET ";
  private static String IselectCmd = "SELECT * FROM INTEREST WHERE ";
  
  //single interest
  private String user;
  private String startDate;
  private float sw;
  private String holdPlace;
  private float hw;
  private String topic;
  private float tw;
  private String url;
  private float uw;
  private String deadline;
  private float dw;
  
  //constructor
  public interest(String user, String s, String h, String t, String u, String d) {
    this.user = user;
    startDate = s;
    holdPlace = h;
    topic = t;
    url = u;
    deadline = d;
    sw = 0;
    hw = 0;
    tw = 0;
    uw = 0;
    dw = 0;
  }
  
  public interest(ResultSet infor) throws SQLException {
    user = infor.getString(1);
    startDate = infor.getString(2);
    sw = Float.parseFloat(infor.getString(3));
    holdPlace = infor.getString(4);
    hw = Float.parseFloat(infor.getString(5));
    topic = infor.getString(6);
    tw = Float.parseFloat(infor.getString(7));
    url = infor.getString(8);
    uw = Float.parseFloat(infor.getString(9));
    deadline = infor.getString(10);
    dw = Float.parseFloat(infor.getString(11));
  }
  
  //getter and setter;
  public String getStartDate() {
    return startDate;
  }
  public void setStartDate(String startDate) {
    this.startDate = startDate;
  }
  public float getSw() {
    return sw;
  }
  public void setSw(float sw) {
    this.sw = sw;
  }
  public String getHoldPlace() {
    return holdPlace;
  }
  public void setHoldPlace(String holdPlace) {
    this.holdPlace = holdPlace;
  }
  public String getTopic() {
    return topic;
  }
  public void setTopic(String topic) {
    this.topic = topic;
  }
  public float getHw() {
    return hw;
  }
  public void setHw(float hw) {
    this.hw = hw;
  }
  public String getUrl() {
    return url;
  }
  public void setUrl(String url) {
    this.url = url;
  }
  public String getDeadline() {
    return deadline;
  }
  public void setDeadline(String deadline) {
    this.deadline = deadline;
  }
  public float getUw() {
    return uw;
  }
  public void setUw(float uw) {
    this.uw = uw;
  }
  public float getTw() {
    return tw;
  }
  public void setTw(float tw) {
    this.tw = tw;
  }
  public float getDw() {
    return dw;
  }
  public void setDw(float dw) {
    this.dw = dw;
  }

  public String getUser() {
    return user;
  }

  public void setUser(String user) {
    this.user = user;
  }
  
  /*
   * interest part including:
   * deleteInterest: delete a users interest information(when a user is signing out)
   * selectInterest: select a user's interest from the database
   * addInterest: add a user's interest(when adding a user)
   * updateInterest: one for string mode, another for float mode
   */
  //parameter: only include one information userName(PK)
    public static void deleteInterest(String[] parameter) throws ClassNotFoundException, SQLException{
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
    public static List<interest> selectInterest(String Column, String[] parameter) throws ClassNotFoundException, SQLException{
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
    public static int addInterest(String[] parameter, float[] weight) throws ClassNotFoundException, SQLException{
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
    public static void updateInterest(String[] parameter) throws ClassNotFoundException, SQLException{
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
    public static void updateInterest(String[] parameter, float[] weight) throws ClassNotFoundException, SQLException{
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
