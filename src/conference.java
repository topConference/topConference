import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import org.apache.logging.log4j.core.appender.rolling.SizeBasedTriggeringPolicy;

//时间、地点、主题、URL、deadline
//topic VARCHAR(190) NOT NULL,
//deadline VARCHAR(190) NOT NULL,
//url VARCHAR(190) NOT NULL,
//dates VARCHAR(190) NOT NULL,
//address VARCHAR(190) NOT NULL,
//img VARCHAR(190) NOT NULL,
//h5index VARCHAR(5) NOT NULL,
//info TEXT NOT NULL,
public class conference {
  static String conferencePK = "topic";
  //for conference connection
  private static DB conferenceDB = new DB();
  private static String CinsertCmd = "INSERT INTO CONFERENCE VALUES(?,?,?,?,?,?,?,?)";
  private static String CdeleteCmd = "DELETE FROM CONFERENCE WHERE ISBN=?";
  private static String CupdateCmd = "UPDATE CONFERENCE SET ";
  private static String CselectCmd = "SELECT * FROM CONFERENCE WHERE ";
  
  private String dates;
  private String address;
  private String topic;
  private String url;
  private String deadline;
  private String img;
  private String h5index;
  private String info;
  
  
  //constructor
  public conference(String s, String h, String t, String u, String d, String i, String h5, String in) {
    dates = s;
    address = d;
    topic = t;
    url = u;
    deadline = d;
    img = i;
    h5index = h5;
    info = in;
  }
  
  public conference(ResultSet infor) throws SQLException {
    dates = infor.getString(1);
    address = infor.getString(2);
    topic = infor.getString(3);
    url = infor.getString(4);
    deadline = infor.getString(5);
  }
  
  public conference() {}
  
  //getter and setter
  public String getdates() {
    return dates;
  }
  public void setdates(String startDate) {
    this.dates = startDate;
  }
  public String getaddress() {
    return address;
  }
  public void setaddress(String holdPlace) {
    this.address = holdPlace;
  }
  public String getTopic() {
    return topic;
  }
  public void setTopic(String topic) {
    this.topic = topic;
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

  public String getImg() {
    return img;
  }

  public void setImg(String img) {
    this.img = img;
  }

  public String getH5index() {
    return h5index;
  }

  public void setH5index(String h5index) {
    this.h5index = h5index;
  }

  public String getInfo() {
    return info;
  }

  public void setInfo(String info) {
    this.info = info;
  }
  
  /*
   * conference part including:
   * deleteConference: delete a conference data from the database
   * selectConference: select a conference from the database
   * addConference: add a new conference to the database
   */
  //parameter: only include one information userName(PK)
  public static void deleteConference(String[] parameter) throws ClassNotFoundException, SQLException{
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
  public static List<conference> selectConference(String Column, String[] parameter) throws ClassNotFoundException, SQLException{
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
  public static int addConference(String[] parameter) throws ClassNotFoundException, SQLException{
    String[] temp = new String[1];
    temp[0] = parameter[0];
    if(selectConference(conferencePK, temp).size()!=0)
        return -1;
    conferenceDB.getConnection();
    conferenceDB.executeUpdate(CinsertCmd, parameter);
    conferenceDB.closeAll();
    return 0;
  }

}
