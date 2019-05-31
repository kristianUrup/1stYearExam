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
    protected InputStream input = new FileInputStream("src/data/config.properties");

    public ReadPropertyFile() throws IOException
    {
        prop = new Properties();
        prop.load(input);
    }

    /**
     * Gets the configfile
     * @return 
     */
    public String getConfig()
    {
        return prop.getProperty("department");
    }

    /**
     * checks if the offset is null, if not then gets the offset
     * @return 
     */
    public int getOffSet()
    {
        if (prop.getProperty("offset").equals(""))
        {
            return 0;
        } else
        {
            return Integer.parseInt(prop.getProperty("offset"));
        }
    }
}
