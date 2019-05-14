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
public class JSONOrder implements IOrder
{
    private final String jsonFile;
    
    public JSONOrder(String jsonFile) {
        this.jsonFile = jsonFile;
    }

    @Override
    public Date getDeliveryTime()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getOrderNumber()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getCustomer()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
