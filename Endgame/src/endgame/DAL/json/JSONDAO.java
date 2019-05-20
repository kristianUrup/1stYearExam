/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package endgame.DAL.json;

import endgame.BE.Department;
import endgame.DAL.ConnectionDAO;
import endgame.DAL.Exception.DalException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
    
    public Department addDepartment(String department) throws DalException {
        Connection con = null;
        try {
            con = cdao.getConnection();
            String sql = "INSERT INTO Department (name) VALUES (?)";
            PreparedStatement pst = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            
            pst.setString(1, department);
            
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
    
    
}
