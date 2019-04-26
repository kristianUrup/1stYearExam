/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package endgame.BE;

import java.util.Date;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author bonde
 */
public class Order
{
    private final StringProperty orderNumber;
    private final StringProperty customer;
    private BooleanProperty isDone;
    
    private Date startDate;
    private Date endDate;
    private Date deliveryDate;

    public Order(String orderNumber, String customer, Date startDate, Date endDate, boolean isDone, Date deliveryDate)
    {
        this.orderNumber = new SimpleStringProperty(orderNumber);
        this.customer = new SimpleStringProperty(customer);
        this.isDone = new SimpleBooleanProperty(isDone);
        this.startDate = startDate;
        this.endDate = endDate;
        this.deliveryDate = deliveryDate;
    }

    public String getOrderNumber()
    {
        return orderNumber.get();
    }

    public String getCustomer()
    {
        return customer.get();
    }

    public Date getStartDate()
    {
        return startDate;
    }

    public Date getEndDate()
    {
        return endDate;
    }

    public boolean getIsDone()
    {
        return isDone.get();
    }

    public Date getDeliveryDate()
    {
        return deliveryDate;
    }
    
    public void setIsDone(BooleanProperty isDone)
    {
        this.isDone = isDone;
    }
    
    
    
    
    
    
    
    
    
    
    
}
