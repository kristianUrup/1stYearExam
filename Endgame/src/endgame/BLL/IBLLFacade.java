/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package endgame.BLL;

import endgame.BE.Department;
import endgame.BE.Order;

/**
 *
 * @author Kristian Urup laptop
 */
public interface IBLLFacade
{
    public Order getOrder();
    
    public Department getDepartment();
}
