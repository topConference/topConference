package core;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import com.opensymphony.xwork2.util.finder.Test;
import api.AllConfAction;
import api.loginAction;
import api.signupAction;
import api.userConfAction;

public class main {
  public static void main(String[] args) throws ClassNotFoundException, SQLException {
    //-------------test signup----------------
    Scanner in = new Scanner(System.in);
//    System.out.println("input");
//    String username1 = in.nextLine();
//    String password1 = in.nextLine();
//    signupAction test1 = new signupAction();
//    test1.setUsername(username1);
//    test1.setPassword(password1);
//    test1.signUp();
    //-------------test login----------------
    loginAction test = new loginAction();
    String username = "pl";//in.nextLine();
    String password = "666";//in.nextLine();
    test.setUserName(username);
    test.setPassword(password);
    System.out.println(test.login());
  //-------------test get users' con----------------
    userConfAction test2 = new userConfAction();
    test2.setUsername(username);
    test2.setToken(test.getToken());
    test2.userCon();
    List<conference> temp = test2.getConferences();
    for(conference tConference : temp) {
      System.out.println(tConference.getTopic());
    }
    //-------------Test get AllConfAction -----------
//    AllConfAction test3 = new AllConfAction();
//    test3.allCon();
//    List<conference> temp = test3.getConferences();
//    for(conference tConference : temp) {
//      System.out.println(tConference.getTopic());
//    }
    conference temp1 = new conference();
    temp1.setTopic("pl");
    List<conference> tConferences = new ArrayList<>();
    tConferences.add(temp1);
    test2.setConferences(tConferences);
    test2.updateUserCon();
    System.out.println("Done!");
    
  }
}