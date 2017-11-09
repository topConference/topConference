import java.sql.SQLException;
import java.util.Scanner;

public class main {
  public static void main(String[] args) throws ClassNotFoundException, SQLException {
    //login test
//    String []temp = {"pengle", "pl", "asdf"};
//    user.signUp(temp);
    Scanner in = new Scanner(System.in);
    //String userName = in.nextLine();
    //String password = in.nextLine();
    String userName = "pengle";
    System.out.println("input password:");
    String password = in.nextLine();
    String result = user.logIn(userName, password);
    System.out.println(result);
    if(result.equals("success!")) {
      System.out.println("add new conference:");
      //String newCon = in.nextLine();
      String newCon = in.nextLine();
      //user.removeCon(userName, newCon);
      int result1 = user.addConference(userName, newCon, 1);
      if(result1 == -2) {
        System.out.println("time conflict");
      }
      else if(result1 == -1) {
        System.out.println("conference has existed");
      }
      else {
        System.out.println("success");
      }
    } 
  }
}