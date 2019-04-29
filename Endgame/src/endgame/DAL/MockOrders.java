/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package endgame.DAL;

import endgame.BE.Department;
import endgame.BE.Order;
import endgame.DAL.Exception.DalException;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Schweizeren
 */
public class MockOrders implements IOrderDAO
{
    Date startDate = new Date();
    Date endDate = new Date();
    Date deliveryDate = new Date();
    

    Order order = new Order(10, "1010", "Frederik A/S", startDate , endDate , false, deliveryDate);
    
    public Order getOrder()
    {
        return order;
    }

    @Override
    public List<Order> getAllOrders(Department department) throws DalException
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void changeOrderState(Boolean state) throws DalException
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
