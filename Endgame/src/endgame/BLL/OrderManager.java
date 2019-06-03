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
import endgame.DAL.OrderDAO;
import java.util.List;

/**
 *
 * @author Schweizeren
 */
public class OrderManager
{
    private IOrderDAO iOrd;
    
    public OrderManager() {
        iOrd = new OrderDAO();
    }
    
    /**
     * Gets a list containing all orders depending on the departmentid
     * @param department
     * @param offset
     * @return
     * @throws BllException 
     */
    public List<Order> getAllOrders(Department department, int offset) throws BllException
    {
        try
        {
            return iOrd.getAllOrders(department, offset);
        } catch (DalException ex)
        {
            throw new BllException(ex.getMessage());
        }
        
    }
    
    /**
     * Updates departmenttask and sets the task as finished, 
     * depending on the departmentid and the orderid
     * @param order
     * @param department
     * @throws BllException 
     */
    public void changeOrderState(Order order, Department department) throws BllException {
        try
        {
            iOrd.changeOrderState(order, department);
        } catch (DalException ex)
        {
            throw new BllException(ex.getMessage());
        }
    }
    
    /**
     * Gets an order depending on the departmentid and the orderid
     * @param department
     * @param order
     * @return
     * @throws BllException 
     */
    public Order getOrder(Department department, Order order) throws BllException {
        try
        {
            return iOrd.getOrder(department, order);
        } catch (DalException ex)
        {
            throw new BllException(ex.getMessage());
        }
    }
    
}
