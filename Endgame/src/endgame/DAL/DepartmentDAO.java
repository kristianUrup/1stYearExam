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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Frederik Jensen
 */
public class DepartmentDAO implements IDepartmentDAO
{
    private ConnectionDAO cdao;
    
    public DepartmentDAO() {
        cdao = new ConnectionDAO();
    }

    @Override
    public Department getDepartment(String dName) throws DalException
    {
        Connection con = null;
        Department department = null;
        try {
            con = cdao.getConnection();
            String sql = "SELECT * FROM Department WHERE name = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            
            pst.setString(1, dName);
            
            ResultSet rs = pst.executeQuery();
            
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                department = new Department(id, name);
            }
            return department;
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
                    throw new DalException("Could not close database connection");
                }
            }
        }
    }

    @Override
    public List<Department> getDepartments(Order order) throws DalException
    {
        Connection con = null;
        try  {
            con = cdao.getConnection();
            List<Department> departments = new ArrayList<>();
            String sql = "SELECT * FROM DepartmentTask INNER JOIN Department ON DepartmentTask.departmentID = Department.id WHERE orderID = ?";

            PreparedStatement pst = con.prepareStatement(sql);
            
            pst.setInt(1, order.getId());
            
            ResultSet rs = pst.executeQuery();
            
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                Department department = new Department(id, name);
                departments.add(department);
            }
            return departments;
        } catch (SQLException ex)
        {
            throw new DalException("Could not get departments");
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
