package com.parexel.automation.Driver.API.customKeywords;

import com.parexel.automation.Driver.API.actions.HttpOperation;
import com.parexel.automation.commonUtils.ConfigReader;

public class BaseTest  extends APIDriverKeywords {

    /**
     * The Base Test Class is used to set up of Environment variables which can be used throughout the tests
     */

    String tenantURL = ConfigReader.loadProperties("tenantURL");
    String urlAuth = ConfigReader.loadProperties("urlAuth");
    String clientID = ConfigReader.loadProperties("clientID");
    String login =  ConfigReader.loadProperties("login");
    String password = ConfigReader.loadProperties("password");


   // @BeforeSuite
    public void Authorization(){
        initEndPointURL(HttpOperation.POST,urlAuth,"/token");
        setPathParam("login",login);
        setPathParam("password",password);
        setQueryParam("username={{login}}&password={{password}}&grant_type=password&client=dnb-connector");

        System.out.println(sendRequest());

        System.out.println("in before suite" );

    }

}
