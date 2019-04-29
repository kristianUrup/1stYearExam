/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package endgame.GUI.Model;

import endgame.BE.Department;
import endgame.BLL.OrderManager;
import endgame.BE.Order;
import endgame.BLL.BLLFacade;
import endgame.BLL.Exception.BllException;
import endgame.BLL.IBLLFacade;
import java.util.List;

/**
 *
 * @author Schweizeren
 */
public class OrderModel
{
    
    private final IBLLFacade BF;
    Order order;

    public OrderModel() throws BllException
    {
        BF = new BLLFacade();
    }
    

    public List<Order> getAllOrders(Department department) throws BllException
    {
        return BF.getAllOrders(department);
    }
    
    public void changeOrderState(Order order, Department department) throws BllException {
        BF.changeOrderState(order, department);
    }
    
    public Department getDepartment(String dName) throws BllException {
        return BF.getDepartment(dName);
    }
    
    public List<Department> getDepartments(Order order) throws BllException {
        return BF.getDepartments(order);
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
