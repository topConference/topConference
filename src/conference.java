import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.logging.log4j.core.appender.rolling.SizeBasedTriggeringPolicy;

//时间、地点、主题、URL、deadline
public class conference {
  private String startDate;
  private String holdPlace;
  private String topic;
  private String url;
  private String deadline;
  
  //constructor
  public conference(String s, String h, String t, String u, String d) {
    startDate = s;
    holdPlace = d;
    topic = t;
    url = u;
    deadline = d;
  }
  
  public conference(ResultSet infor) throws SQLException {
    startDate = infor.getString(1);
    holdPlace = infor.getString(2);
    topic = infor.getString(3);
    url = infor.getString(4);
    deadline = infor.getString(5);
  }
  
  public conference() {}
  
  //getter and setter
  public String getStartDate() {
    return startDate;
  }
  public void setStartDate(String startDate) {
    this.startDate = startDate;
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

}
