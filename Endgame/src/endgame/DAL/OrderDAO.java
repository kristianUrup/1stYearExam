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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
                
                Date today = new Date();
                if (startDate.equals(today) || startDate.before(today))
                {
                    if (!isDone)
                    {
                        Order order = new Order(id, ordernumber, customer, startDate, endDate, isDone, deliveryDate);
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
}
