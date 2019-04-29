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
 * @author Frederik Jensen
 */
public interface IOrderDAO
{
    public List<Order> getAllOrders(Department department) throws DalException;
    
    public void changeOrderState(Order order, Department department) throws DalException;
    
    
}
