/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package endgame.BLL;

import endgame.BE.Department;
import endgame.DAL.Exception.DalException;
import endgame.DAL.IDepartmentDAO;
import endgame.DAL.MockDepartment;

/**
 *
 * @author bonde
 */
public class DepartmentManager
{
    private IDepartmentDAO mDep;
    
    public DepartmentManager() {
        mDep = new MockDepartment();
    }
    
    public Department getDepartment(String dName) throws DalException{
        return mDep.getDepartment(dName);
    }
}
