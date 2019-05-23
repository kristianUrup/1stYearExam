/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package endgame.DAL;

import endgame.BE.Worker;
import endgame.DAL.Exception.DalException;
import java.util.List;

/**
 *
 * @author KristianUrup
 */
public interface IWorkerDAO {
    public List<Worker> getAllWorkers(Worker worker) throws DalException;
}
