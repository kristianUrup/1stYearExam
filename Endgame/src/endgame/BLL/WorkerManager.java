/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package endgame.BLL;

import endgame.BE.Department;
import endgame.BE.Order;
import endgame.BE.Worker;
import endgame.BLL.Exception.BllException;
import endgame.DAL.Exception.DalException;
import endgame.DAL.IWorkerDAO;
import endgame.DAL.WorkerDAO;
import java.util.List;

/**
 *
 * @author Frederik Jensen
 */
public class WorkerManager
{
    private IWorkerDAO wdao;
    
    public WorkerManager() {
        wdao = new WorkerDAO();
    }
    
    /**
     * Gets a list containing all workers depending on the departmentid and orderid
     * @param department
     * @param order
     * @return
     * @throws BllException 
     */
    public List<Worker> getWorkers(Department department, Order order) throws BllException {
        try
        {
            return wdao.getWorkers(department, order);
        }catch (DalException ex)
        {
            throw new BllException(ex.getMessage());
        }
    }
    
}
