/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package endgame.GUI.Model;

import endgame.BLL.OrderManager;
import endgame.BE.Order;

/**
 *
 * @author Schweizeren
 */
public class OrderModel
{
    OrderManager OMA;
    
    
    public Order getOrder()
    {
        return OMA.getOrder();
    }
    
}
