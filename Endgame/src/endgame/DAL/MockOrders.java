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
    Date startDate = new Date(2018,3,3);
    Date endDate = new Date(2019,4,3);
    Date deliveryDate = new Date(2019,4,4);
    
    Date startDate1 = new Date(2019,2,1);
    Date endDate1 = new Date(2019,3,1);
    Date deliveryDate1 = new Date(2019,3,2);
    
    Order order = new Order("1010", "Frederik A/S", startDate , endDate , false, deliveryDate ,0.0);
    Order order1 = new Order("1010", "Frederik A/S", startDate1 , endDate1 , false, deliveryDate1 ,0.0);
    
    public Order getOrder()
    {
        return order1;
    }
}
