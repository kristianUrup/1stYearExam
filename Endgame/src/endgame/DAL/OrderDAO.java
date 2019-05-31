/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package endgame.DAL;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import endgame.BE.Department;
import endgame.BE.Order;
import endgame.DAL.Exception.DalException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Frederik Jensen
 */
public class OrderDAO implements IOrderDAO
{

    private ConnectionDAO cdao;

    public OrderDAO()
    {
        cdao = new ConnectionDAO();
    }

    /**
     * Gets a list containing all orders depending on the departmentid
     * @param department
     * @param offset
     * @return
     * @throws DalException 
     */
    @Override
    public List<Order> getAllOrders(Department department, int offset) throws DalException
    {
        Connection con = null;
        try
        {
            con = cdao.getConnection();
            List<Order> orders = new ArrayList<>();
            String sql = "SELECT * FROM DepartmentTask dt"
                    + " INNER JOIN \"Order\" d ON dt.orderID = d.id"
                    + " WHERE dt.departmentID = ?";

            PreparedStatement pst = con.prepareStatement(sql);

            pst.setInt(1, department.getId());

            ResultSet rs = pst.executeQuery();

            while (rs.next())
            {
                int id = rs.getInt("orderID");
                String ordernumber = rs.getString("ordernumber");
                String customer = rs.getString("costumer");
                Boolean isDone = rs.getInt("finished") == 1;

                String startDateString = rs.getString("startDate");
                String endDateString = rs.getString("endDate");
                String deliveryDateString = rs.getString("deliverydate");

                Date startDate = new SimpleDateFormat("dd/MM/yyyy").parse(startDateString);
                Date endDate = new SimpleDateFormat("dd/MM/yyyy").parse(endDateString);
                Date deliveryDate = new SimpleDateFormat("dd/MM/yyyy").parse(deliveryDateString);

                String condition = getCondition(isDone, endDate, startDate);
                
                Date today = calculateOffSetDate(offset);
                if (startDate.equals(today) || startDate.before(today))
                {
                    if (!isDone)
                    {
                        Order order = new Order(id, ordernumber, customer, startDate, endDate, condition, deliveryDate);
                        orders.add(order);
                    }
                }
            }

            return orders;
        } catch (SQLException ex)
        {
            throw new DalException("Could not get all orders for this department");
        } catch (ParseException ex)
        {
            throw new DalException("Could not parse date correctly");
        } finally
        {
            if (con != null)
            {
                try
                {
                    con.close();
                } catch (SQLException ex)
                {
                    throw new DalException("Could not close connection");
                }
            }
        }
    }

    /**
     * Updates departmenttask and sets the task as finished, 
     * depending on the departmentid and the orderid
     * @param order
     * @param department 
     */
    @Override
    public void changeOrderState(Order order, Department department)
    {
        Connection con = null;
        try
        {
            con = cdao.getConnection();
            String sql = "UPDATE DepartmentTask SET finished = ? WHERE departmentID = ? AND orderID = ?";
            PreparedStatement pst = con.prepareStatement(sql);

            pst.setInt(1, 1);
            pst.setInt(2, department.getId());
            pst.setInt(3, order.getId());

            pst.execute();
        } catch (SQLException ex)
        {
            Logger.getLogger(OrderDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Gets todays offset in days
     * @param offset
     * @return 
     */
    private Date calculateOffSetDate(int offset)
    {
        Date today = new Date();
        long milliOffSet = today.getTime() + (offset * (25 * 60 * 60 * 1000));
        Date todayOffSet = new Date(milliOffSet);
        return todayOffSet;
    }

    /**
     * Gets an order depending on the departmentid and the orderid
     * @param department
     * @param order
     * @return
     * @throws DalException 
     */
    @Override
    public Order getOrder(Department department, Order order) throws DalException
    {
        Order newOrder = null;
        Connection con = null;
        try {
            con = cdao.getConnection();
            String sql = "SELECT * FROM DepartmentTask dt"
                    + " INNER JOIN \"Order\" d ON dt.orderID = d.id"
                    + " WHERE dt.departmentID = ? AND dt.orderID = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            
            pst.setInt(1, department.getId());
            pst.setInt(2, order.getId());
            
            ResultSet rs = pst.executeQuery();
            
            while(rs.next()) {
                int id = rs.getInt("orderID");
                String ordernumber = rs.getString("ordernumber");
                String customer = rs.getString("costumer");
                Boolean isDone = rs.getInt("finished") == 1;
                
                String startDateString = rs.getString("startDate");
                String endDateString = rs.getString("endDate");
                String deliveryDateString = rs.getString("deliverydate");

                Date startDate = new SimpleDateFormat("dd/MM/yyyy").parse(startDateString);
                Date endDate = new SimpleDateFormat("dd/MM/yyyy").parse(endDateString);
                Date deliveryDate = new SimpleDateFormat("dd/MM/yyyy").parse(deliveryDateString);
                
                String condition = getCondition(isDone, endDate, startDate);
                
                newOrder = new Order(id, ordernumber, customer, startDate, endDate, condition, deliveryDate);
            }
            return newOrder;
            
        } catch (SQLException ex)
        {
            throw new DalException("Could not get order from database");
        } catch (ParseException ex)
        {
            throw new DalException("Could not parse date on order");
        }
    }
    
    /**
     * Gets the condition of the order
     * @param isDone
     * @param endDate
     * @param startDate
     * @return 
     */
    private String getCondition(Boolean isDone, Date endDate, Date startDate)
    {
        
        if(isDone)
        {
            return "finished";
        }else {
            if (System.currentTimeMillis() > endDate.getTime()) {
                return "behind";
            }
            else if (System.currentTimeMillis() > startDate.getTime() && System.currentTimeMillis() < endDate.getTime()) {
                return "ongoing";
            }
            else if (System.currentTimeMillis() < startDate.getTime()){
                return "not started";
            }
        }
        return null;
    }
    
//    private Order getManagementOrders()
//    {
//        Connection con = null;
//        
//        try {
//            con = cdao.getConnection();
//            String sql = 
//            
//            PreparedStatement pst = con.prepareStatement(sql);
//            
//            pst.setString(1, Order.);
//    }
}
