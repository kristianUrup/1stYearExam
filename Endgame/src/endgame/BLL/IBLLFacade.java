/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package endgame.BLL;

import endgame.BE.Department;
import endgame.BE.Order;
import endgame.BE.Worker;
import endgame.BLL.Exception.BllException;
import java.io.File;
import java.util.List;
import javafx.collections.ObservableList;

/**
 *
 * @author Kristian Urup laptop
 */
public interface IBLLFacade
{
    // DepartmentManager
    public Department getDepartment(String dName) throws BllException;
    
    public List<Department> getDepartments(Order order) throws BllException;
    
    public List<Department> getManagementDepartments() throws BllException;
    
    // OrderManager
    public List<Order> getAllOrders(Department department, int offset) throws BllException;
    
    public void changeOrderState(Order order, Department department) throws BllException;
    
    public Order getOrder(Department department, Order order) throws BllException;
    
    // LogManager
    public void setLastActivity(Order order, Department department, String messageLog);
            
    public String getLastActivity(Order order) throws BllException;
    
    // FileManager
    public String getConfig();
    
    public int getOffSet();
    
    // JSONManager
    public void getJsonFile() throws BllException;
    
    // WorkerManager
    public List<Worker> getAllWorkers(Department department, Order order) throws BllException;
    
    
    
}
