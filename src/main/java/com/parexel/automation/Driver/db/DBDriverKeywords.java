package com.parexel.automation.Driver.db;

import com.parexel.automation.Driver.API.customKeywords.APIDriverKeywords;
import com.parexel.automation.Testdata.excel.ExcelDataReader;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.*;
import java.util.LinkedHashMap;

public class DBDriverKeywords extends APIDriverKeywords implements DBDriverInterface {
    Connection con ;
    LinkedHashMap<String, String> data ;
    public void getConntion(String serverName) throws SQLException {

        String connectionUrl = "jdbc:sqlserver://<"+serverName+">:<port>;encrypt=true;databaseName=AdventureWorks;user=<user>;password=<password>";

        try  {
           // Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection(connectionUrl);
           /* Statement stmt = con.createStatement();
            String SQL = "SELECT TOP 10 * FROM Person.Contact";
            ResultSet rs = stmt.executeQuery(SQL);

            // Iterate through the data in the result set and display it.
            while (rs.next()) {
                System.out.println(rs.getString("FirstName") + " " + rs.getString("LastName"));
            }*/
        }
        // Handle any errors that may have occurred.
        catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public void closeConnection(){
        try {
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public ResultSet getResultSet(String tcID, String dataSheet) {

        ResultSet rs = null;
        try {
            getConntion("bronze");
            Statement stmt = con.createStatement();
            String query = buildQuery(tcID,dataSheet);//"select * from ..."
            rs = stmt.executeQuery(query);

            // Iterate through the data in the result set and display it.
            while (rs.next()) {
                //System.out.println(rs.getString("FirstName") + " " + rs.getString("LastName"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

        public String buildQuery(String tcID, String dataSheet){
        ExcelDataReader dataReader = new ExcelDataReader();
        data= dataReader.readTCBinding(tcID,dataSheet);
        return data.get("SQLQuery");

        //get it from excel
    }
    public boolean compareResultSetAndJson(ResultSet resultSet, String jsonString) throws SQLException {
        try {
            // Parse the JSON string into a JSONArray
            JSONArray jsonArray = new JSONArray(jsonString);

            // Get the metadata of the ResultSet to determine column names
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            int rowCount = 0;                       // RowCount to verify that the size of the ResultSet is equal to the Json array size

            // Iterate through the ResultSet
            while (resultSet.next()) {
                rowCount++;                         //while the result set has a new row, incrementing the row count
                JSONObject jsonItem = null;

                // Iterate through the JSON array to find a matching record
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    // Compare each column value in the ResultSet to the JSON object
                    boolean match = true;
                    for (int j = 1; j <= columnCount; j++) {
                        String columnName = metaData.getColumnName(j);
                        Object resultSetValue = resultSet.getObject(j).toString();
                        Object jsonValue = jsonObject.opt(columnName).toString();

                        // Compare the ResultSet value to the JSON value
                        if (resultSetValue == null && jsonValue != null) {
                            match = false;
                            break;
                        } else if (resultSetValue != null && !resultSetValue.equals(jsonValue)) {
                            match = false;
                            break;
                        }
                    }

                    if (match) {
                        jsonItem = jsonObject;
                        break;
                    }
                }

                if (jsonItem == null) {
                    // No matching record found in JSON
                    return false;
                }
            }

            // Check if there are any remaining JSON objects that weren't matched
            return jsonArray.length() == rowCount;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean compareResultSetCellValueWithJson(ResultSet resultSet, int columnIndex, String jsonString, String jsonKey) throws SQLException {
        try {
            // Parse the JSON string into a JSONObject
            JSONObject jsonObject = new JSONObject(jsonString);

            // Move to the desired row in the ResultSet (if needed)
            if (resultSet.next()) {
                // Get the cell value from the ResultSet at the specified column index
                Object resultSetValue = resultSet.getObject(columnIndex);

                // Get the value from the JSON object using the provided key
                Object jsonValue = jsonObject.opt(jsonKey);

                // Compare the ResultSet value to the JSON value
                return (resultSetValue == null && jsonValue == null) || (resultSetValue != null && resultSetValue.equals(jsonValue));
            } else {
                // No matching row found in the ResultSet
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private APIDriverKeywords apiKeywords = new APIDriverKeywords() {}; // Anonymous class instance

    void callGet(String TcId, String dataSheet) {
        apiKeywords.getEndPointUrl(TcId,dataSheet);
    }
    void callPost(String TcId, String dataSheet) {
        try {
            apiKeywords.postEndPointUrl(TcId,dataSheet);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    public Object cleanseResultSet(Object resultSetValue){
        String str = null;
        //if(resultSetValue.toString().charAt(0) == ' '  ||  resultSetValue.toString().charAt(resultSetValue.toString().length()-1) == ' ')

        String[] strarr = str.split(" ");
        for (String s :strarr) {
            if(s.length()>=1)
            {
                str = s.substring(0,1).toUpperCase()+ s.substring(1).toLowerCase()+ " ";
            }
        }
        str = str.trim();
        return str;
    }



}
