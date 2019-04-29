/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package endgame.DAL;

import endgame.BE.Order;
import java.util.Date;

/**
 *
 * @author Schweizeren
 */
public class MockOrders
{
    Date startDate = new Date();
    Date endDate = new Date();
    Date deliveryDate = new Date();
    Order order = new Order(10, "1010", "Frederik A/S", startDate , endDate , false, deliveryDate);
    
    public Order getOrder()
    {
        return order;
    }
}
