/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package endgame.BLL;

import endgame.BE.Department;
import endgame.BE.Order;
import endgame.BLL.Exception.BllException;
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
    
    /**
     * Inserts an acitivitylog containing orderid, departmentid, an exception and the current date
     * @param order
     * @param department
     * @param messageLog 
     */
    public void setLastActivity(Order order, Department department, String messageLog)
    {
        iLog.setLastActivity(order, department, messageLog);
    }
    
    /**
     * Gets the latest acitivtlog depending on orderid
     * @param order
     * @return
     * @throws BllException 
     */
    public String getLastActivity(Order order) throws BllException
    {
        try
        {
            return iLog.getLastActivity(order);
        } catch (DalException ex)
        {
            throw new BllException(ex.getMessage());
        }
    }

    
    
    
}
