/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package endgame.DAL.json;

import java.util.Date;

/**
 *
 * @author Frederik Jensen
 */
public interface IOrder
{
    public Date getDeliveryTime();
    
    public String getOrderNumber();
    
    public String getCustomer();
}
