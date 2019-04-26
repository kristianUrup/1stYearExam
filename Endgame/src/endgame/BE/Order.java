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
    private double status;

    public Order(String orderNumber, String customer, Date startDate, Date endDate, boolean isDone, Date deliveryDate, double status)
    {
        this.orderNumber = new SimpleStringProperty(orderNumber);
        this.customer = new SimpleStringProperty(customer);
        this.isDone = new SimpleBooleanProperty(isDone);
        this.status = status;
        this.startDate = startDate;
        this.endDate = endDate;
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

    public BooleanProperty getIsDone()
    {
        return isDone;
    }

    public Date getDeliveryDate()
    {
        return deliveryDate;
    }
    
    public void setIsDone(BooleanProperty isDone)
    {
        this.isDone = isDone;
    }

    public double getStatus()
    {
        return status;
    }

    public void setStatus(double status)
    {
        this.status = status;
    }
    
    
    
    
    
    
    
    
    
    
    
    
}
