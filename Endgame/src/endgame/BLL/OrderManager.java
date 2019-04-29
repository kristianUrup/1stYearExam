/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package endgame.BLL;

import endgame.BE.Order;
import endgame.DAL.MockOrders;
import java.util.List;

/**
 *
 * @author Schweizeren
 */
public class OrderManager
{
    MockOrders mOrd;
    Order ord;
    
    public Order getOrder()
    {
        mOrd = new MockOrders();
        return mOrd.getOrder();
    }
}
