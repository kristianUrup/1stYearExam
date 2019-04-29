/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package endgame.BLL;

import endgame.BE.Department;
import endgame.DAL.DepartmentDAO;
import endgame.DAL.Exception.DalException;
import endgame.DAL.IDepartmentDAO;

/**
 *
 * @author bonde
 */
public class DepartmentManager
{
    private IDepartmentDAO dep;
    
    public DepartmentManager() {
        dep = new DepartmentDAO();
    }
    
    public Department getDepartment(String dName) throws DalException{
        return dep.getDepartment(dName);
    }
}
