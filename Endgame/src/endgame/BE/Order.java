/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package endgame.BE;

import java.util.Date;
import javafx.beans.property.StringProperty;

/**
 *
 * @author bonde
 */
public class Order
{
    private final StringProperty orderNumber;
    private final StringProperty customer;
    
    private Date startDate;
    private Date endDate;
    private boolean isDone;
    private Date deliveryDate;

    public Order(StringProperty orderNumber, StringProperty customer, Date startDate, Date endDate, boolean isDone, Date deliveryDate)
    {
        this.orderNumber = orderNumber;
        this.customer = customer;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isDone = isDone;
        this.deliveryDate = deliveryDate;
    }

    public StringProperty getOrderNumber()
    {
        return orderNumber;
    }

    public StringProperty getCustomer()
    {
        return customer;
    }

    public Date getStartDate()
    {
        return startDate;
    }

    public Date getEndDate()
    {
        return endDate;
    }

    public boolean isIsDone()
    {
        return isDone;
    }

    public Date getDeliveryDate()
    {
        return deliveryDate;
    }

    public void setIsDone(boolean isDone)
    {
        this.isDone = isDone;
    }
    
    
    
    
    
    
}
