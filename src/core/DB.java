package core;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DB {
  private String driver = "com.mysql.jdbc.Driver";
  private String url = "jdbc:mysql://127.0.0.1:3306/top_conference?useUnicode=true&characterEncoding=utf-8&useSSL=false";
  //private String url = "jdbc:mysql://do1.bilabila.tk:3306/top_conference";
  private Connection conn = null;
  private PreparedStatement ps = null;
  //private String userName = "root";
  //private String password = "dilidili";
  private String userName = "root";
  private String password = "Pengle1997.";
  private ResultSet rs = null;
  
  //build connection and return it
  public Connection getConnection() throws ClassNotFoundException, SQLException {
    Class.forName(driver);
    conn = DriverManager.getConnection(url, userName, password);
    return conn;
  }
  
  //close database
  public void closeAll() throws SQLException {
    if(rs != null) {
      rs.close();
    }
    if(ps!=null) {
      ps.close();
    }
    if(conn!=null) {
      conn.close();
    }
  }
  
  //execute the select query and return the result
  public ResultSet executeSelect(String cmd, String[] param) throws SQLException {
    ps = conn.prepareStatement(cmd);
    if(param!=null) {
      for(int i = 0;i<param.length;i++) {
        ps.setString(i+1, param[i]);
      }
    }
    rs = ps.executeQuery();
    return rs;
  }
  
  public int executeUpdate(String cmd, String[] param) throws SQLException {
    int affectRowNum = 0;
    ps = conn.prepareStatement(cmd);
    if(param!=null) {
       for(int i =0;i<param.length;i++) {
         ps.setString(i+1, param[i]);
       }
    }
    affectRowNum = ps.executeUpdate();
    return affectRowNum;
  }
  
  public int executeUpdate(String cmd, String[] param, float[] weight) throws SQLException {
    int affectRowNum = 0;
    ps = conn.prepareStatement(cmd);
    if(param!=null) {
       ps.setString(1, param[0]);
       for(int i =1;i<param.length;i++) {
         ps.setString(i*2, param[i]);
         ps.setFloat(i*2+1, weight[i-1]);
       }
    }
    affectRowNum = ps.executeUpdate();
    return affectRowNum;
  }
  
  public int executeUpdate(String cmd, float[] param) throws SQLException {
    int affectRowNum = 0;
    ps = conn.prepareStatement(cmd);
    if(param!=null) {
       for(int i =0;i<param.length;i++) {
         ps.setFloat(i+1, param[i]);
       }
    }
    affectRowNum = ps.executeUpdate();
    return affectRowNum;
  }
}
