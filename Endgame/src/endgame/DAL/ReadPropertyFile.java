/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package endgame.DAL;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 *
 * @author bonde
 */
public class ReadPropertyFile
{
    protected Properties prop = null;
    //System.out.println(System.getProperty("user.dir"));
    protected InputStream input = new FileInputStream("data/config.properties");

    public ReadPropertyFile() throws IOException
    {
        prop = new Properties();
        prop.load(input);
    }
    
    public String getConfig(){
        return prop.getProperty("department");
    }
    
    public static void main(String[] args) throws IOException
    {
        //System.out.println(System.getProperty("user.dir"));        
        
       try (InputStream input = new FileInputStream("data/config.properties")) {

        Properties prop = new Properties();
        prop.load(input);
        
        System.out.println("Department : " + prop.getProperty("department"));
       } catch (IOException ex) {
            ex.printStackTrace();
        } 
        
    }
}
