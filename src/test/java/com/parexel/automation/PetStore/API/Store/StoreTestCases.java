package com.parexel.automation.PetStore.API.Store;

import org.testng.annotations.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class StoreTestCases {

    @Test
    public void sample() throws IOException {
        System.out.println("sample test");
        System.out.println(readFile("C:\\Users\\Admin\\Desktop\\API Testing\\parexelMDM\\src\\main\\resources\\jsonData\\PostPayload.txt"));

    }

    public  String readFile(String filePath) throws IOException {
        StringBuilder out= new StringBuilder();
        String line;
        FileReader fileReader = new FileReader(filePath);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        while ((line = bufferedReader.readLine()) != null) {
            out.append(line);
        }
        bufferedReader.close();
        fileReader.close();
        return out.toString();
    }
}
