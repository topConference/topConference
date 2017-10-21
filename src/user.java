import java.sql.ResultSet;
import java.sql.SQLException;

public class user {
  private String userName;
  private String passWord;
  
  //constructor
  public user(String u, String p){
    userName = u;
    passWord = p;
  }
  
  public user(ResultSet infor) throws SQLException {
    userName = infor.getString(1);
    passWord = infor.getString(2);
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
  
}
