package core;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;
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
    System.out.println("Done!");
  }
}