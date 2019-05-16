/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package endgame;

import endgame.DAL.Exception.DalException;
import endgame.DAL.json.JSONFileReader;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author bonde
 */
public class Tester
{
    public static void main(String[] args)
    {
        try
        {
            File file = new File("src/data");
            JSONFileReader json = new JSONFileReader();
            json.getJsonFile(file);
        } catch (DalException ex)
        {
            Logger.getLogger(Tester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
