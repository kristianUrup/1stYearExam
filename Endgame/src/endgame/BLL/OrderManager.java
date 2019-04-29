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
import endgame.DAL.IOrderDAO;
import endgame.DAL.MockOrders;
import endgame.DAL.OrderDAO;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Schweizeren
 */
public class OrderManager
{
    IOrderDAO iOrd;
    Order ord;
    
    public OrderManager() {
        iOrd = new MockOrders();
    }
    
    public List<Order> getAllOrders(Department department) throws BllException
    {
        try
        {
            return iOrd.getAllOrders(department);
        } catch (DalException ex)
        {
            throw new BllException(ex.getMessage());
        }
        
    }
    
    public void changeOrderState(Order order, Department department) throws BllException {
        try
        {
            iOrd.changeOrderState(order, department);
        } catch (DalException ex)
        {
            throw new BllException(ex.getMessage());
        }
    }
    
}
