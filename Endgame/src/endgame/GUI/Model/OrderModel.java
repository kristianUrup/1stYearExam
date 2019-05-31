/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package endgame.GUI.Model;

import endgame.BE.Department;
import endgame.BE.Order;
import endgame.BE.Worker;
import endgame.BLL.BLLFacade;
import endgame.BLL.Exception.BllException;
import endgame.BLL.IBLLFacade;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Schweizeren
 */
public class OrderModel
{

    private final ObservableList<Department> departmentList;
    private final ObservableList<Worker> workerList;
    private final ObservableList<Department> ManagementDepartmentList;
    private final IBLLFacade BF;

    public OrderModel() throws BllException
    {
        BF = new BLLFacade();
        departmentList = FXCollections.observableArrayList();
        workerList = FXCollections.observableArrayList();
        ManagementDepartmentList = FXCollections.observableArrayList();
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
        return BF.getAllOrders(department, offset);
    }

    /**
     * Updates departmenttask and sets the task as finished, 
     * depending on the departmentid and the orderid
     * @param order
     * @param department
     * @throws BllException 
     */
    public void changeOrderState(Order order, Department department) throws BllException
    {
        BF.changeOrderState(order, department);
    }

    /**
     * Gets a specific department depending on the name
     * @param dName
     * @return
     * @throws BllException 
     */
    public Department getDepartment(String dName) throws BllException
    {
        return BF.getDepartment(dName);
    }

    /**
     * Gets the progressed time and converts it to percentage
     * @param startDate
     * @param endDate
     * @return
     * @throws BllException 
     */
    public double getProgressedTimeInProcent(Date startDate, Date endDate) throws BllException
    {
        long endTime = endDate.getTime();
        long startTime = startDate.getTime();

        double estimatedTime = (endTime - startTime) / (1000 * 60 * 60 * 24);  // Den tilgængelige tid

        double todaysTime = System.currentTimeMillis() / (1000 * 60 * 60 * 24); // Idags tid

        double daysSpent = todaysTime - (startTime / (1000 * 60 * 60 * 24));

        double elapsedTime = daysSpent / estimatedTime;  // Den brugte tid

        return elapsedTime;
    }

    /**
     * Checks if departmentlist is not empty and then clears it
     * Then adds all departments depending on the order to the departmentlist
     * Then returns the departmentlist
     * @param order
     * @return
     * @throws BllException 
     */
    public ObservableList<Department> getAllDepartments(Order order) throws BllException
    {
        if (!departmentList.isEmpty())
        {
            departmentList.clear();
        }
        departmentList.addAll(BF.getDepartments(order));
        return departmentList;
    }

    /**
     * Inserts an acitivitylog containing orderid, departmentid, an exception and the current date
     * @param order
     * @param department
     * @param messageLog 
     */
    public void setLastActivity(Order order, Department department, String messageLog)
    {
        BF.setLastActivity(order, department, messageLog);
    }

    /**
     * Gets the latest acitivtlog depending on orderid
     * @param order
     * @return
     * @throws BllException 
     */
    public String getLastActivity(Order order) throws BllException
    {
        return BF.getLastActivity(order);
    }

    /**
     * Gets the configfile
     * @return 
     */
    public String getConfig()
    {
        return BF.getConfig();
    }

    /**
     * checks if the offset is empty, if not then gets the offset
     * @return 
     */
    public int getOffSet()
    {
        return BF.getOffSet();
    }

    /**
     * Gets an order depending on the departmentid and the orderid
     * @param department
     * @param order
     * @return
     * @throws BllException 
     */
    public Order getOrder(Department department, Order order) throws BllException
    {
        return BF.getOrder(department, order);
    }

    /**
     * Runs the refreshDepartments method, 
     * which runs through every department
     * and inserts them into a list
     * @param order
     * @throws BllException 
     */
    public void refreshDepartments(Order order) throws BllException
    {
        List<Department> departs = BF.getDepartments(order);
        for (Department dep : departs)
        {
            for (int i = 0; i < departmentList.size(); i++)
            {
                if (departmentList.get(i).getName().equals(dep.getName()))
                {
                    departmentList.set(i, dep);
                }
            }
        }
    }
    
    /**
     * Checks if workerlist is not empty and then clears it
     * Then adds all workers, depending on the department and the order, to the departmentlist
     * Then returns the workerlist
     * @param deparment
     * @param order
     * @return
     * @throws BllException 
     */
    public ObservableList<Worker> getAllWorkers(Department deparment, Order order) throws BllException
    {
        if (!workerList.isEmpty()) {
            workerList.clear();
        }
        workerList.addAll(BF.getAllWorkers(deparment, order));
        return workerList;
    }

    /**
     * Sorts the orders by comparing the enddates, and then by ascending enddates
     * @param orders 
     */
    public void endDateSortedByAsc(List<Order> orders)
    {
        orders.sort(Comparator.comparing(Order::getEndDate));
    }

    /**
     * Sorts the orders by comparing the enddates, and then by descending enddates
     * @param orders 
     */
    public void endDateSortedByDesc(List<Order> orders)
    {
        orders.sort(Comparator.comparing(Order::getEndDate).reversed());
    }

    /**
     * Gets the JsonFile
     * @throws BllException 
     */
    public void getJsonFile() throws BllException {
        BF.getJsonFile();
    }

    /**
     * creates a string for Ordermodel
     * @return 
     */
    @Override
    public String toString() {
        return "OrderModel{" + "workerList=" + workerList + '}';
    }
    
    /**
     * Gets a List containing alle the department names except for management
     * Then adding all the department names to another list
     * Then returns the list 
     * @return
     * @throws BllException 
     */
    public ObservableList<Department> getManagementDepartments() throws BllException
    {
        ManagementDepartmentList.addAll(BF.getManagementDepartments());
        return ManagementDepartmentList;
    }
}
