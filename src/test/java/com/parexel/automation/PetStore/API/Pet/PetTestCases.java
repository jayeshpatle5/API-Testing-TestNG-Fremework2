package com.parexel.automation.PetStore.API.Pet;

import com.parexel.automation.Driver.API.customKeywords.BaseTest;
import com.parexel.automation.Testdata.excel.ExcelWriter;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.IOException;

public class PetTestCases extends BaseTest {       // extends DriverKeywords

    ExcelWriter write;

    @Parameters({ "testCaseID", "dataSheet" })
    @Test
    public void retrievePetDetailsByStatus( String testCaseID, String dataSheet ) {

        System.out.println(getEndPointUrl(testCaseID,dataSheet));
        assertIt(200);
       // assertIt("status","available", ValidatorOperation.valueOf("EQUALS"));

    }

    @Parameters({ "testCaseID", "dataSheet" })
    @Test
    public void addNewPetDetails(String testCaseID, String dataSheet ) throws IOException {
        System.out.println(postEndPointUrl(testCaseID,dataSheet));
        assertIt(200);

        String petid = Long.toString(extractInt("id"));
        System.out.println(petid);

        write = new ExcelWriter("C:\\Users\\Admin\\Desktop\\API Testing\\parexelMDM\\Execution\\RunManager.xlsx",dataSheet);
        write.writeTOCell("PathParams","TC0003","/"+petid);

    }

    @Parameters({ "testCaseID", "dataSheet" })
    @Test(groups = "Smoke")
    public void fetchPetById(String testCaseID, String dataSheet )  {
        System.out.println(getEndPointUrl(testCaseID,dataSheet));
        assertIt(200);
    }


    @Parameters({ "testCaseID", "dataSheet" })
    @Test
    public void removePetWithId(String testCaseID, String dataSheet )  {
        System.out.println(deleteEndPointUrl(testCaseID,dataSheet));
        assertIt(200);
    }

}
