/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package endgame.DAL;

import endgame.BE.Department;
import endgame.BE.Order;
import endgame.DAL.Exception.DalException;
import java.util.List;

/**
 *
 * @author bonde
 */
public class MockDepartment implements IDepartmentDAO
{

    @Override
    public Department getDepartment(String dName) throws DalException       
    {
        Department halvFab = new Department(1, "Halvfab");
        return halvFab;
    }

    @Override
    public List<Department> getDepartments(Order order) throws DalException
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
