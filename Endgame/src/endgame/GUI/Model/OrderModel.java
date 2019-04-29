/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package endgame.GUI.Model;

import endgame.BE.Department;
import endgame.BLL.OrderManager;
import endgame.BE.Order;
import endgame.BLL.Exception.BllException;
import java.util.List;

/**
 *
 * @author Schweizeren
 */
public class OrderModel
{
    
    OrderManager OMA;
    Order order;

    public OrderModel()
    {
        OMA = new OrderManager();
    }
    
    public List<Order> getAllOrders(Department department) throws BllException
    {
        return OMA.getAllOrders(department);
    }
    
    public double getProgressedTimeInProcent()
    {
        
        long endDate = order.getEndDate().getTime();
        long startDate = order.getStartDate().getTime();
                
                
        long difference = (endDate - startDate);
        
        int progressedDays = (int) (difference / (60 * 60 * 24 * 1000));
        
        double procent = (int) ((endDate/(60 * 60 * 24 * 1000))/ progressedDays);
        
        return procent;
        
    }
    
}
