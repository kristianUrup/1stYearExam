/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package endgame.DAL.json;

import java.io.File;

/**
 *
 * @author Frederik Jensen
 */
public class JSONFileReader
{
    
    public void getJsonFile(File folder) {
        for (File fileEntry : folder.listFiles()) {
            if (getFileExtension(fileEntry).equals("json")) {
                System.out.println(fileEntry.getName());
            }
        }
    }
    
    private static String getFileExtension(File file) {
        String fileName = file.getName();
        if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
        return fileName.substring(fileName.lastIndexOf(".")+1);
        else return "";
    }
}
