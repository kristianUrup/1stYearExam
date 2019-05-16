/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package endgame.DAL.json;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Frederik Jensen
 */
public class JSONFileReader
{
    
    public void getJsonFile(File folder) {
        for (File fileEntry : folder.listFiles()) {
            if (getFileExtension(fileEntry).equals("json")) {
                readJsonFile("src/data/" + fileEntry.getName());
            }
        }
    }
    
    private static String getFileExtension(File file) {
        String fileName = file.getName();
        if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
        return fileName.substring(fileName.lastIndexOf(".")+1);
        else return "";
    }
    
    public void readJsonFile(String json) {
        JSONParser parser = new JSONParser();
        try {
            System.out.println(json);
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(json));
            JSONArray productorders = (JSONArray) jsonObject.get("ProductionOrders");
            for (Object object : productorders) {
                JSONObject jObject = (JSONObject) object;
                JSONObject customer = (JSONObject) jObject.get("Customer");
                String name = (String) customer.get("Name");
                System.out.println(name);
            }
        } catch (IOException ex)
        {
            Logger.getLogger(JSONFileReader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex)
        {
            Logger.getLogger(JSONFileReader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
