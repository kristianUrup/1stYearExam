/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package endgame.DAL;

import endgame.BE.Department;
import endgame.BE.Order;
import endgame.DAL.Exception.DalException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    
    public OrderDAO() {
       cdao = new ConnectionDAO(); 
    }

    @Override
    public List<Order> getAllOrders(Department department) throws DalException
    {
        Connection con = null;
        try {
            con = cdao.getConnection();
            List<Order> orders = new ArrayList<>();
            String sql = "SELECT * FROM DepartmentTask dt"
                    + " INNER JOIN \"Order\" d ON dt.orderID = d.id"
                    + " INNER JOIN Customer c ON d.customerid = c.id"
                    + " WHERE dt.departmentID = ?";
            
            PreparedStatement pst = con.prepareStatement(sql);
            
            pst.setInt(0, department.getId());
            
            ResultSet rs = pst.executeQuery();
            
            while(rs.next()) {
                int id = rs.getInt("orderID");
                String ordernumber = rs.getString("ordernumber");
                String customer = rs.getString("name");
                Boolean isDone = rs.getInt("finished") == 1;
                Date startDate = new Date(rs.getString("startDate"));
                Date endDate = new Date(rs.getString("endDate"));
                Date deliveryDate = new Date(rs.getString("deliverydate"));
                if (startDate.equals(new Date()) || startDate.after(new Date()) && !isDone) {
                    Order order = new Order(id, ordernumber, customer, startDate, endDate, isDone, deliveryDate);
                    orders.add(order);
                }
            }
            
            return orders; 
        } catch (SQLException ex)
        {
            throw new DalException("Could not get all orders for this department");
        }
    }

    @Override
    public void changeOrderState(Boolean state)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
