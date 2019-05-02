/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package endgame.GUI.Model;

import endgame.BE.Department;
import endgame.BE.Order;
import endgame.BLL.BLLFacade;
import endgame.BLL.Exception.BllException;
import endgame.BLL.IBLLFacade;
import java.util.List;
import javafx.collections.ObservableList;

/**
 *
 * @author Schweizeren
 */
public class OrderModel
{
    private ObservableList<Department> departmentList;
    private final IBLLFacade BF;

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
    
    public double getProgressedTimeInProcent(Order order) throws BllException
    {
        long endDate = order.getEndDate().getTime();
        long startDate = order.getStartDate().getTime();
        
        double estimatedTime = (endDate - startDate) /(1000*60*60*24);  // Den tilgængelige tid
        
        double todaysTime = System.currentTimeMillis() /(1000*60*60*24); // Idags tid
        
        double daysSpent = todaysTime - (startDate/(1000*60*60*24));
        
        double elapsedTime = daysSpent / estimatedTime;  // Den brugte tid
        
        return elapsedTime;
        
        
        
    }
    
    public ObservableList<Department> getAllDepartments()
    {
        return departmentList;
    }
    
}
