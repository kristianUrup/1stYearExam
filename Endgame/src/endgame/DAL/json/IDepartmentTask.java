/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package endgame.DAL.json;

import endgame.DAL.Exception.DalException;
import java.util.Date;

/**
 *
 * @author Frederik Jensen
 */
public interface IDepartmentTask
{
    public String getDepartment(Object object);
    
    public boolean isOrderFinished(Object object);
    
    public Date getStartDate(Object object) throws DalException;
    
    public Date getEndDate(Object object) throws DalException;
}
