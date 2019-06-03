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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Frederik Jensen
 */
public class DepartmentDAO implements IDepartmentDAO
{

    private ConnectionDAO cdao;

    public DepartmentDAO()
    {
        cdao = new ConnectionDAO();
    }

    /**
     * Gets a specific department depending on the name
     * @param dName
     * @return
     * @throws DalException 
     */
    @Override
    public Department getDepartment(String dName) throws DalException
    {
        Connection con = null;
        Department department = null;
        try
        {
            con = cdao.getConnection();
            String sql = "SELECT * FROM Department WHERE name = ?";
            PreparedStatement pst = con.prepareStatement(sql);

            pst.setString(1, dName);

            ResultSet rs = pst.executeQuery();

            while (rs.next())
            {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                department = new Department(id, name);
            }
            return department;
        } catch (SQLException ex)
        {
            throw new DalException("Could not find department");
        } finally
        {
            if (con != null)
            {
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

    /**
     * Gets a list containing all departments
     * @param order
     * @return
     * @throws DalException 
     */
    @Override
    public List<Department> getDepartments(Order order) throws DalException
    {
        Connection con = null;
        try
        {
            con = cdao.getConnection();
            List<Department> departments = new ArrayList<>();
            String sql = "SELECT * FROM DepartmentTask INNER JOIN Department ON DepartmentTask.departmentID = Department.id WHERE orderID = ?";

            PreparedStatement pst = con.prepareStatement(sql);

            pst.setInt(1, order.getId());

            ResultSet rs = pst.executeQuery();

            while (rs.next())
            {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                Boolean isDone = rs.getInt("finished") == 1;
                Date startDate = new SimpleDateFormat("dd/MM/yyyy").parse(rs.getString("startDate"));
                Date endDate = new SimpleDateFormat("dd/MM/yyyy").parse(rs.getString("endDate"));

                String condition = getCondition(isDone, endDate, startDate);

                Department department = new Department(id, name, condition, startDate, endDate);
                departments.add(department);
            }
            return departments;
        } catch (SQLException ex)
        {
            throw new DalException("Could not get departments");
        } catch (ParseException ex)
        {
            throw new DalException("Could not parse start/end date for departments");
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
    
    /**
     * Gets a List containing alle the department names except for management
     * @return
     * @throws DalException 
     */
    @Override
    public List<Department> getManagementDepartments() throws DalException
    {
        Connection con = null;
        
        List<Department> departments = new ArrayList<>();
        try {
            con = cdao.getConnection();
            
            String sql = "SELECT * FROM Department WHERE NOT name = 'management'";
            
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while(rs.next())
            {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                
                Department department = new Department(id,name);
                departments.add(department);
            }
        } catch (SQLException ex)
        {
            throw new DalException("Could not get departments");
        }
        return departments;
    }
}
