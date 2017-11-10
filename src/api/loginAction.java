package api;
import java.sql.SQLException;
import com.opensymphony.xwork2.ActionSupport;
import core.*;

public class loginAction extends ActionSupport{
  private String userName;
  private String password;
  private String token;
  private String warningInfor;
  
  //login
 public String login() throws ClassNotFoundException, SQLException {
   java.util.Random r=new java.util.Random(); 
   token = user.encytp(String.valueOf(r.nextInt()));
   int result = user.logIn(userName, password, token);
   if(result == 0) {
     return SUCCESS;
   }
   if(result == -2) {
     setWarningInfor("User not exist!");
   }
   else {
     setWarningInfor("Wrong password!");
   }
   return ERROR;
  }
  
  //getter & setter
  public String getUserName() {
    return userName;
  }
  public void setUserName(String userName) {
    this.userName = userName;
  }
  public String getPassword() {
    return password;
  }
  public void setPassword(String password) {
    this.password = password;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public String getWarningInfor() {
    return warningInfor;
  }

  public void setWarningInfor(String warningInfor) {
    this.warningInfor = warningInfor;
  }
  

}
