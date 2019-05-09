/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package endgame.DAL;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import endgame.BE.Department;
import endgame.BE.Order;
import endgame.BLL.Exception.BllException;
import endgame.BLL.IBLLFacade;
import endgame.DAL.Exception.DalException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.jar.Attributes;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Schweizeren
 */
public class LogDAO implements ILogDAO
{

    private ConnectionDAO cdao;

    public LogDAO()
    {
        cdao = new ConnectionDAO();
    }

    @Override
    public void setLastActivity(Order order, Department department, String messageLog)
    {
        Connection con = null;
        {
            try
            {
                con = cdao.getConnection();
                String sql = "INSERT INTO ActivityLog VALUES(?,?,?,?)";
                PreparedStatement pst = con.prepareStatement(sql);

                pst.setInt(1, order.getId());
                pst.setInt(2, department.getId());
                pst.setString(3, messageLog);

                LocalDate localdate = LocalDate.now();
                pst.setString(4, DateTimeFormatter.ofPattern("dd/MM/yyyy").format(localdate));

                pst.execute();
            } catch (SQLException ex)
            {
                Logger.getLogger(LogDAO.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    @Override
    public String getLastActivity(Order order) throws DalException
    {
        Connection con = null;
        String lastDep = null;
        {

            try
            {
                con = cdao.getConnection();
                String sql = "SELECT *"
                        + " FROM Department INNER JOIN ActivityLog ON Department.id = ActivityLog.departmentID"
                        + " WHERE ActivityLog.orderID = ?";
                
                PreparedStatement pst = con.prepareStatement(sql);

                pst.setInt(1, order.getId());
                ResultSet rs = pst.executeQuery();

                while (rs.next())
                {
                    lastDep = rs.getString("name");
                }
                if (lastDep == null || lastDep.isEmpty())
                {
                    return "No one is active";
                } else
                {
                    return lastDep;
                }

            } catch (SQLException ex)
            {
                throw new DalException("Could not get last active department");
            } finally
            {
                try
                {
                    if (con != null)
                    {
                        con.close();
                    }
                } catch (SQLException ex)
                {
                    throw new DalException("Could not close connection");
                }
            }
        }
    }
}
