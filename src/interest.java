import java.sql.ResultSet;
import java.sql.SQLException;

public class interest {
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
}
