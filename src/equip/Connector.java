/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package equip;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author 3
 */
public class Connector {

    Statement statement,sm_updatable;
    
    public static String driverName = "com.mysql.jdbc.Driver";
    public static String userName = "root";
    public static String userPwd = "123456";
    public static String dbName = "test_data";

    public Connector() {
        try {
            String url = "jdbc:mysql://localhost/" + dbName + "?user=" + userName + "&password=" + userPwd;

            Class.forName("com.mysql.jdbc.Driver").newInstance();

             Connection connection = DriverManager.getConnection(url);

            statement = connection.createStatement();
            sm_updatable=connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);




        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
