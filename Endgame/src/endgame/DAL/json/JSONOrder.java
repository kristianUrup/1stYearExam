/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package endgame.DAL.json;

import endgame.DAL.Exception.DalException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONObject;

/**
 *
 * @author Frederik Jensen
 */
public class JSONOrder implements IOrder
{

    @Override
    public Date getDeliveryTime(Object object) throws DalException
    {
        try
        {
            JSONObject jObject = (JSONObject) object;
            JSONObject orderDate = (JSONObject) jObject.get("Delivery");
            String date = (String) orderDate.get("DeliveryTime");
            return convertJSONDate(date);
        } catch (ParseException ex)
        {
            throw new DalException("Could not parse date");
        }
    }

    @Override
    public String getOrderNumber(Object object)
    {
        JSONObject jObject = (JSONObject) object;
        JSONObject orderNumber = (JSONObject) jObject.get("Order");
        String number = (String) orderNumber.get("OrderNumber");
        return number;
    }

    @Override
    public String getCustomer(Object object)
    {
        JSONObject jObject = (JSONObject) object;
        JSONObject customer = (JSONObject) jObject.get("Customer");
        String name = (String) customer.get("Name");
        return name;
    }
    
    private Date convertJSONDate(String jsonDate) throws ParseException {
        int timeZoneIndex = jsonDate.indexOf("+");
        String date = "";
        if (timeZoneIndex != -1) {
            date = jsonDate.substring(6, timeZoneIndex);
            System.out.println(date);
        }
        long timeInMillis = Long.parseLong(date);
        Date deliveryDate = new Date(timeInMillis);
        return deliveryDate;
    }
    
}
