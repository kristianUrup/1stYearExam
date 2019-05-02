/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package endgame.BLL;

import endgame.BE.Department;
import endgame.BE.Order;
import endgame.BLL.Exception.BllException;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Kristian Urup laptop
 */


public class BLLFacade implements IBLLFacade
{
    private OrderManager OMA;
    private DepartmentManager DMA;
    private FileManager FM;
    private LogManager LM;
    
    public BLLFacade() throws BllException
    {
        try
        {
            
            OMA = new OrderManager();
            DMA = new DepartmentManager();
            FM = new FileManager();
            LM = new LogManager();
        } catch (IOException ex)
        {
            throw new BllException("Could not get files");
        }
    }

    @Override
    public List<Order> getAllOrders(Department department) throws BllException
    {
        return OMA.getAllOrders(department);
    }

    @Override
    public Department getDepartment(String dName) throws BllException
    {
        return DMA.getDepartment(dName);
    }

    @Override
    public List<Department> getDepartments(Order order) throws BllException
    {
        return DMA.getDepartments(order);
    }

    @Override
    public void changeOrderState(Order order, Department department) throws BllException
    {
        OMA.changeOrderState(order, department);
    }

    @Override
    public String getConfig()
    {
        return FM.getConfig();
    }
    
    @Override
    public void setLastActivity(Order order, Department department, String messageLog)
    {
        LM.setLastActivity(order, department, messageLog);
    }

    @Override
    public String getLastActivity(Order order, Department department) throws BllException
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    
}
