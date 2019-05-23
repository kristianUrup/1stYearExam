/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package endgame.BLL;

import endgame.BE.Department;
import endgame.BE.Order;
import endgame.BE.Worker;
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
    
    public List<Worker> getWorkers(Department department, Order order) throws DalException {
        return wdao.getWorkers(department, order);
    }
    
}
