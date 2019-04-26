/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package endgame.DAL;

import endgame.BE.Department;

/**
 *
 * @author bonde
 */
public class MockDepartment
{
    Department halvFab = new Department(1, "Halvfab");
    
    
    public Department getDepartment()
    {
        return halvFab;
    }
}
