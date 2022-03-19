package jdbctests;

import java.sql.*;

public class Main {
    public static void main(String[] args) throws SQLException {

        String dbUrl="jdbc:oracle:thin:@18.233.164.111:1521:xe";
        String dbUsername="hr";
        String dbPassword="hr";

        // create connection
        Connection connection= DriverManager.getConnection(dbUrl,dbUsername,dbPassword);
        // creating statement object
        Statement statement=connection.createStatement();
        // run query and get the result in resultset object

        ResultSet resultSet=statement.executeQuery("Select * from regions");

        // move pointer to first row

//        resultSet.next();
//        System.out.println(resultSet.getString("region_name"));
//        System.out.println(resultSet.getString(2));
//        System.out.println(resultSet.getInt(1)+"-"+resultSet.getString(2));
//        // move pointer to second row
//        resultSet.next();
//        System.out.println(resultSet.getString("region_name"));
//        System.out.println(resultSet.getString(2));
//        System.out.println(resultSet.getInt(1)+"-"+resultSet.getString(2));
//
//        //close all connections

        while(resultSet.next()){
            System.out.println(resultSet.getInt(1)+"-"+resultSet.getString(2));

        }

        ResultSet resultSets=statement.executeQuery("Select first_name, last_name, salary from employees");

        while(resultSets.next()){
            System.out.println(resultSets.getString(1)+"-"+resultSets.getString(2)+"-"+resultSets.getInt(3));

        }

        resultSet.close();
        statement.close();
        connection.close();



    }
}
