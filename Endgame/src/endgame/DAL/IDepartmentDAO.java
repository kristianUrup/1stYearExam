/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package endgame.DAL;

import endgame.BE.Department;
import endgame.BE.Order;
import endgame.BLL.Exception.BllException;
import endgame.DAL.Exception.DalException;
import java.util.List;

/**
 *
 * @author Frederik Jensen
 */
public interface IDepartmentDAO
{
    public Department getDepartment(String dName) throws DalException;
    
    public List<Department> getDepartments(Order order) throws DalException;
    
    public List<Department> getManagementDepartments(Department department) throws DalException;
}
