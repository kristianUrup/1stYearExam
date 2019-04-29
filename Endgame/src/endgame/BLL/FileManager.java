/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package endgame.BLL;

import endgame.DAL.ReadPropertyFile;
import java.io.IOException;

/**
 *
 * @author bonde
 */
public class FileManager
{
    private ReadPropertyFile rpfile;

    public FileManager() throws IOException
    {
        rpfile = new ReadPropertyFile();
    }
    
    public String getConfig(){
        return rpfile.getConfig();
    }
}
