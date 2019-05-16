/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package endgame.DAL.json;

import endgame.DAL.Exception.DalException;
import java.util.Date;

/**
 *
 * @author Frederik Jensen
 */
public interface IOrder
{
    public Date getDeliveryTime(Object object) throws DalException;
    
    public String getOrderNumber(Object object);
    
    public String getCustomer(Object object);
}
