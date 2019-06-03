/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package endgame.DAL.json;

import endgame.BE.Department;
import endgame.BE.Order;
import endgame.DAL.ConnectionDAO;
import endgame.DAL.Exception.DalException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Frederik Jensen
 */
public class JSONDAO
{
    private final ConnectionDAO cdao;
    
    public JSONDAO() {
        cdao = new ConnectionDAO();
    }
    
    /**
     * Gets a name from a department depending on the name
     * @param department
     * @return
     * @throws DalException 
     */
    public boolean departmentExists(String department) throws DalException {
        Connection con = null;
        try {
            con = cdao.getConnection();
            String sql = "SELECT name FROM department WHERE name = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            
            pst.setString(1, department);
            
            ResultSet rs = pst.executeQuery();
            
            while(rs.next()) {
                String name = rs.getString("name");
                if (department.equals(name)) {
                    return true;
                }
            }
            return false;
        } catch (SQLException ex)
        {
            throw new DalException("Could not find department");
        } finally {
            if (con != null) {
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
     * Inserts a name into a department
     * @param department
     * @return
     * @throws DalException 
     */
    public Department addDepartment(String department) throws DalException {
        Connection con = null;
        try {
            con = cdao.getConnection();
            String sql = "INSERT INTO Department (name) VALUES (?)";
            PreparedStatement pst = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            
            pst.setString(1, department);
            
            pst.execute();
            
            ResultSet rs = pst.getGeneratedKeys();
            
            int id = 0;
            while(rs.next()) {
                id = rs.getInt(1);
            }
            Department dep = new Department(id, department);
            return dep;
        } catch (SQLException ex)
        {
            throw new DalException("Could not add department to database");
        } finally {
            if(con != null) {
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
     * Inserts an order containing ordernumber, customer and deliverydate
     * @param orderNumber
     * @param customer
     * @param deliveryDate
     * @return
     * @throws DalException 
     */
    public Order addOrder(String orderNumber, String customer, Date deliveryDate) throws DalException {
        Connection con = null;
        try {
            con = cdao.getConnection();
            String sql = "INSERT INTO \"Order\" (ordernumber, deliverydate, costumer) VALUES (?,?,?)";
            PreparedStatement pst = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            
            pst.setString(1, orderNumber);
            String date = new SimpleDateFormat("dd/MM/yyyy").format(deliveryDate);
            pst.setString(2, date);
            pst.setString(3, customer);
            
            pst.execute();
            
            ResultSet rs = pst.getGeneratedKeys();
            
            int id = 0;
            while(rs.next()) {
                id = rs.getInt(1);
            }
            Order order = new Order(id, orderNumber, customer, deliveryDate);
            return order;
        } catch (SQLException ex)
        {
            throw new DalException("Could not add order to database");
        } finally {
            if (con != null) {
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
     * Inserts a departmenttask containing departmentid, orderid, startDate, endDate and finished
     * @param department
     * @param order
     * @param startDate
     * @param endDate
     * @param isDone
     * @throws DalException 
     */
    public void addDepartmentTask(Department department, Order order, Date startDate, Date endDate, boolean isDone) throws DalException {
        Connection con = null;
        try {
            con = cdao.getConnection();
            String sql = "INSERT INTO DepartmentTask(orderID, departmentID, startDate, endDate, finished) VALUES (?,?,?,?,?)";
            PreparedStatement pst = con.prepareStatement(sql);
            
            pst.setInt(1, order.getId());
            pst.setInt(2, department.getId());
            
            String stringSDate = new SimpleDateFormat("dd/MM/yyyy").format(startDate);
            pst.setString(3, stringSDate);
            
            String stringEDate = new SimpleDateFormat("dd/MM/yyyy").format(endDate);
            pst.setString(4, stringEDate);
            
            if (isDone) {
                pst.setInt(5,1);
            } else {
                pst.setInt(5,0);
            }
            pst.execute();
        } catch (SQLException ex)
        {
            throw new DalException("Could not add departmentTask to database");
        } finally {
            if (con != null) {
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
     * Gets all from department depending on the name
     * @param department
     * @return
     * @throws DalException 
     */
    public Department getDepartment(String department) throws DalException {
        Connection con = null;
        try {
            con = cdao.getConnection();
            String sql = "SELECT * FROM Department WHERE name = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            
            pst.setString(1, department);
            
            ResultSet rs = pst.executeQuery();
            int id = 0;
            while(rs.next()) {
                id = rs.getInt("id");
                department = rs.getString("name");
            }
            Department dep = new Department(id, department);
            return dep;
        } catch (SQLException ex)
        {
            throw new DalException("Could not add order to database");
        } finally {
            if (con != null) {
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
     * Inserts a worker containing name, initials and salarynumber
     * @param name
     * @param initials
     * @param salaryNumber
     * @throws DalException 
     */
    public void addWorker(String name, String initials, int salaryNumber) throws DalException {
        Connection con = null;
        try {
            con = cdao.getConnection();
            String sql = "INSERT INTO Worker(name, initials, salarynumber) VALUES (?,?,?)";
            PreparedStatement pst = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            
            pst.setString(1, name);
            pst.setString(2, initials);
            pst.setInt(3, salaryNumber);
            
            pst.execute();
            
        } catch (SQLException ex)
        {
            throw new DalException("Could not add worker to database");
        } finally {
            if (con != null) {
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
}
