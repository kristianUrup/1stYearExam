/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package endgame.BLL;

import endgame.BE.Department;
import endgame.BE.Order;
import endgame.DAL.Exception.DalException;
import endgame.DAL.ILogDAO;
import endgame.DAL.LogDAO;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Schweizeren
 */
public class LogManager
{
    private ILogDAO iLog;

    public LogManager()
    {
        iLog = new LogDAO();
    }
    
    public void setLastActivity(Order order, Department department, String messageLog)
    {
        iLog.setLastActivity(order, department, messageLog);
    }
    
    public String getLastActivity(Order order, Department department)
    {
        try
        {
            iLog.getLastActivity(order, department);
        } catch (DalException ex)
        {
            Logger.getLogger(LogManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    
    
    
}
