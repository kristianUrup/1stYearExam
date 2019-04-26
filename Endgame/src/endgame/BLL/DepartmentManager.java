/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package endgame.BLL;

import endgame.BE.Department;
import endgame.DAL.MockDepartment;

/**
 *
 * @author bonde
 */
public class DepartmentManager
{
    MockDepartment mDep;
    Department dep;
    
    public Department getDepartment(){
        mDep = new MockDepartment();
        return mDep.getDepartment();
    }
}
