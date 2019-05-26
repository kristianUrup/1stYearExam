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

    public List<Order> getAllOrders(Department department, int offset) throws BllException
    {
        return BF.getAllOrders(department, offset);
    }

    public void changeOrderState(Order order, Department department) throws BllException
    {
        BF.changeOrderState(order, department);
    }

    public Department getDepartment(String dName) throws BllException
    {
        return BF.getDepartment(dName);
    }

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

    public ObservableList<Department> getAllDepartments(Order order) throws BllException
    {
        if (!departmentList.isEmpty())
        {
            departmentList.clear();
        }
        departmentList.addAll(BF.getDepartments(order));
        return departmentList;
    }

    public void setLastActivity(Order order, Department department, String messageLog)
    {
        BF.setLastActivity(order, department, messageLog);
    }

    public String getLastActivity(Order order) throws BllException
    {
        return BF.getLastActivity(order);
    }

    public String getConfig()
    {
        return BF.getConfig();
    }

    public int getOffSet()
    {
        return BF.getOffSet();
    }

    public Order getOrder(Department department, Order order) throws BllException
    {
        return BF.getOrder(department, order);
    }

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
    
    public ObservableList<Worker> getAllWorkers(Department deparment, Order order) throws BllException
    {
        if (!workerList.isEmpty()) {
            workerList.clear();
        }
        workerList.addAll(BF.getAllWorkers(deparment, order));
        return workerList;
    }

    public void endDateSortedByAsc(List<Order> orders)
    {
        orders.sort(Comparator.comparing(Order::getEndDate));
    }

    /**
     * Sorts the list of orders by comparing the end dates with the other orders
     * @param orders 
     */
    public void endDateSortedByDesc(List<Order> orders)
    {
        orders.sort(Comparator.comparing(Order::getEndDate).reversed());
    }

    public void getJsonFile() throws BllException {
        BF.getJsonFile();
    }

    @Override
    public String toString() {
        return "OrderModel{" + "workerList=" + workerList + '}';
    }
    
    public ObservableList<Department> getManagementDepartments() throws BllException
    {
        ManagementDepartmentList.addAll(BF.getManagementDepartments());
        return ManagementDepartmentList;
    }
}
