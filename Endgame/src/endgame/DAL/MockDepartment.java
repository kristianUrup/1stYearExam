/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package endgame.DAL;

import endgame.BE.Department;
import endgame.BE.Order;
import endgame.DAL.Exception.DalException;
import java.util.ArrayList;
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
        ReadPropertyFile rpfile;

        Department halvFab = new Department(1, "Halvfab");
        Department clip = new Department(2, "Clip");
        Department rolling = new Department(3, "Rolling");
        Department painting = new Department(4, "Painting");
        Department mont1 = new Department(5, "Mont1");
        Department mont2 = new Department(6, "Mont2");
        Department shipping = new Department(7, "Shipping");
        List<Department> temp = new ArrayList();
        temp.add(halvFab);
        temp.add(clip);
        temp.add(rolling);
        temp.add(painting);
        temp.add(mont1);
        temp.add(mont2);
        temp.add(shipping);
        for (int i = 0; i < temp.size(); i++)
        {
            if (temp.get(i).getName().equals(dName))
            {
                return temp.get(i);
            }
        }
        return null;
    }

    @Override
    public List<Department> getDepartments(Order order) throws DalException
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Department> getManagementDepartments() throws DalException
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
