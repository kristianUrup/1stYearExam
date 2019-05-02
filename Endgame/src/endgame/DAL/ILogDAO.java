/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package endgame.DAL;

import endgame.BE.Department;
import endgame.BE.Order;
import endgame.DAL.Exception.DalException;

/**
 *
 * @author Schweizeren
 */
public interface ILogDAO
{
    public void setLastActivity(Order order, Department department, String messageLog);
    
    public String getLastActivity(Order order, Department department) throws DalException;
    
}
