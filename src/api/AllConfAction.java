package api;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.opensymphony.xwork2.ActionSupport;
import core.conference;

public class AllConfAction extends ActionSupport{
  private List<conference> conferences; 
  
  public String allCon() throws ClassNotFoundException, SQLException {
    String[] temp = {conference.publicConf};
    String[] r = {conference.GENUS};
    setConferences(conference.selectConference(r, temp));
    return SUCCESS;
  }

  public List<conference> getConferences() {
    return conferences;
  }

  public void setConferences(List<conference> conferences) {
    this.conferences = conferences;
  } 
}
