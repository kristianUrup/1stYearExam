/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package endgame.BLL;

import endgame.BLL.Exception.BllException;
import endgame.DAL.Exception.DalException;
import endgame.DAL.json.JSONFileReader;
import java.io.File;

/**
 *
 * @author Frederik Jensen
 */
public class JSONManager
{
    private JSONFileReader jfr;
    
    public JSONManager() {
        jfr = new JSONFileReader();
    }
    
    public void getJsonFile() throws BllException {
        try
        {
            jfr.getJsonFile();
        } catch (DalException ex)
        {
            throw new BllException(ex.getMessage());
        }
    }
}
