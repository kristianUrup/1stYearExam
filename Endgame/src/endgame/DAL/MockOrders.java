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
import java.util.Date;
import java.util.List;

/**
 *
 * @author Schweizeren
 */
public class MockOrders implements IOrderDAO
{
    
    @Override
    public List<Order> getAllOrders(Department department) throws DalException
    {
        List<Order> orders = new ArrayList<>();
        
        Date startDate = new Date("29/04/2019");
        Date endDate = new Date("01/05/2019");
        Date deliveryDate = new Date("02/05/2019");
        Order order = new Order(10, "1010", "Frederik A/S", startDate , endDate , false, deliveryDate);
        
        orders.add(order);
        return orders;
        
        
    }

    @Override
    public void changeOrderState(Order order, Department department) throws DalException
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
