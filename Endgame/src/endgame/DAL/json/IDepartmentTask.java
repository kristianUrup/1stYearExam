/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package endgame.DAL.json;

import endgame.BE.Department;
import java.util.Date;

/**
 *
 * @author Frederik Jensen
 */
public interface IDepartmentTask
{
    public Department getDepartment();
    
    public boolean isOrderFinished();
    
    public Date getStartDate();
    
    public Date getEndDate();
}
