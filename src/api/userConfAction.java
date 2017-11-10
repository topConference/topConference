package api;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import com.opensymphony.xwork2.ActionSupport;
import core.conference;
import core.user;

public class userConfAction extends ActionSupport{
  private String username;
  private String token;
  private List<conference> conferences = new LinkedList<conference>();
  
  public String userCon() throws ClassNotFoundException, SQLException {
    String[] restrict = {user.userPK, user.VERTIFY};
    String[] parameter = {username, token};
    user u = user.selectUser(restrict, parameter).get(0);
    if(u!=null) {
    //get all public conference
      if(u.getConferences()!=null) {
        String[] publicConf = u.getConferences().split("/");
        for(String t : publicConf) {
          String[] temp = {t, conference.publicConf};
          String[] r = {conference.conferencePK, conference.GENUS};
          conferences.addAll(conference.selectConference(r, temp));
        }
      }
    //get all private conference
      String[] temp = {username};
      String[] r = {conference.GENUS};
      conferences.addAll(conference.selectConference(r, temp));
    }
    return SUCCESS;
  }
  
  public String updateUserCon() throws ClassNotFoundException, SQLException {
    //if the user is qualified
    String[] r = {user.userPK, user.VERTIFY};
    String[] parameter = {username, token};
    user temp = user.selectUser(r, parameter).get(0);
    if(temp==null) {
      return ERROR;
    }
    String publicCon = temp.getConferences();
    for(conference tConference : conferences) {
      String topic = tConference.getTopic();
      if(publicCon.contains(topic)) {//public conference
        user.removeCon(username, topic);
      }
      else {//private conference
        String[] columns = {conference.GENUS};
        String[] param = {username};
        conference.deleteConference(columns, param);
      }
    }
    return SUCCESS;
  }
  
  public String addConference() throws ClassNotFoundException, SQLException {
    for(conference tConference : conferences) {
      String topic = tConference.getTopic();
      conference.addConference(tConference);
    }
    return SUCCESS;
  }
  
  //getter & setter
  public String getUsername() {
    return username;
  }
  public void setUsername(String username) {
    this.username = username;
  }
  public String getToken() {
    return token;
  }
  public void setToken(String token) {
    this.token = token;
  }
  public List<conference> getConferences() {
    return conferences;
  }
  public void setConferences(List<conference> conferences) {
    this.conferences = conferences;
  }
}
