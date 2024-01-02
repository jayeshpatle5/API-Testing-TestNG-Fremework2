package com.parexel.automation.MDM.API.entities;

import com.parexel.automation.Driver.db.DBDriverKeywords;
import org.testng.annotations.Test;

import java.sql.*;

public class SQLTest extends DBDriverKeywords {

    static String user= "Suresh.anand";
    static String url= "jdbc:mysql://localhost:3306/practice";
    static String password= "S@sql123";
    static String query="select * from library";


    @Test
    public void VerifySQL() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Driver name
            Connection con = DriverManager.getConnection(url, user, password);
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);

            /*ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            while (rs.next()){
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = metaData.getColumnName(i);
                    Object columnValue = rs.getObject(i);

                    System.out.println(columnName + ": " + columnValue);
                }
                System.out.println(); // Separate records with an empty line
            }*/

            String jsonString =getStringFromFilePath("C:\\Users\\Admin\\Desktop\\API Testing\\parexelMDM\\src\\main\\resources\\jsonData\\SampleJson.txt");
            System.out.println(compareResultSetAndJson(rs,jsonString));

            jsonString =getStringFromFilePath("C:\\Users\\Admin\\Desktop\\API Testing\\parexelMDM\\src\\main\\resources\\jsonData\\SingleJson.txt");
            System.out.println(compareResultSetCellValueWithJson(rs,0,jsonString,"BookID"));


            // Close the ResultSet, Statement, and Connection
            rs.close();
            st.close();
            con.close();


        }
        catch (Exception e){
            e.printStackTrace();

        }

    }
    @Test
    public void VerifySQL2() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Driver name
            Connection con = DriverManager.getConnection(url, user, password);
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select * from library where BookID =100");



            String jsonString =getStringFromFilePath("C:\\Users\\Admin\\Desktop\\API Testing\\parexelMDM\\src\\main\\resources\\jsonData\\SingleJson.txt");
            System.out.println(compareResultSetCellValueWithJson(rs,1,jsonString,"BookID"));


            // Close the ResultSet, Statement, and Connection
            rs.close();
            st.close();
            con.close();


        }
        catch (Exception e){
            e.printStackTrace();

        }

    }

    @Test
    public void ExcelTest(){

    }

}
