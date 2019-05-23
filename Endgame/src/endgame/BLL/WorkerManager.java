/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package endgame.BLL;

import endgame.BE.Worker;
import endgame.BLL.Exception.BllException;
import endgame.DAL.Exception.DalException;
import endgame.DAL.IWorkerDAO;
import java.util.List;

/**
 *
 * @author KristianUrup
 */
public class WorkerManager {
    IWorkerDAO wdao;

    public WorkerManager(IWorkerDAO wdao) {
        this.wdao = wdao;
    }

    WorkerManager() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public List<Worker> getAllWorkers(Worker worker) throws BllException
    {
        try{
            return wdao.getAllWorkers(worker);
        }
        catch(DalException ex)
        {
            throw new BllException(ex.getMessage());
        }
    }
}
