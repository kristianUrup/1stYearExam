/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package endgame.DAL;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import endgame.BE.Worker;
import endgame.DAL.Exception.DalException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author KristianUrup
 */
public class WorkerDAO {
    
    private ConnectionDAO cdao;
    
    public WorkerDAO()
    {
        cdao = new ConnectionDAO();
    }
    
    
    public List<Worker> getAllWorkers(Worker worker) throws DalException
    {
        Connection con = null;
        
        try
        {
            con = cdao.getConnection();
            List<Worker> workers = new ArrayList<>();
            String sql = "SELECT * FROM Worker";
            
            PreparedStatement pst = con.prepareStatement(sql);
            
            pst.setInt(1, worker.getId());
            
            ResultSet rs = pst.executeQuery();
            
            while(rs.next())
            {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String initials = rs.getString("initials");
                int salarynumber = rs.getInt("salarynumber");
                
                Worker newWorker = new Worker(id,name,initials,salarynumber);
                workers.add(newWorker);
            }
            return workers;
        } catch (SQLException ex)
        {
            throw new DalException("Could not get all orders for this department");
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
}
