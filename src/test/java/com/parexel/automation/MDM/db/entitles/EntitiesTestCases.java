package com.parexel.automation.MDM.db.entitles;

import com.parexel.automation.Driver.db.DBDriverKeywords;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EntitiesTestCases extends DBDriverKeywords {

    @Parameters({ "testCaseID", "dataSheet" })
    @Test
    public void DBMethod(String TcId, String dataSheet) throws SQLException {

        ResultSet rs= getResultSet(TcId,dataSheet);
        APIClass apiClass = new APIClass();
        String jsonString = apiClass.getEndPointUrl(TcId,dataSheet);

        if(compareResultSetAndJson(rs,jsonString)){
            System.out.println("Equals");
        }
        else {
            System.out.println("not equals");
        }


    }
}
