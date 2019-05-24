/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package endgame.BE;

import java.util.Date;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author bonde
 */
public class Order
{
    private IntegerProperty ID;
    private StringProperty orderNumber;
    private StringProperty customer;
    private StringProperty condition;
    
    
    private Date startDate;
    private Date endDate;
    private Date deliveryDate;

    
    
    public Order(int id, String orderNumber, String customer, Date startDate, Date endDate, String condition, Date deliveryDate)
    {
        this.ID = new SimpleIntegerProperty(id);
        this.orderNumber = new SimpleStringProperty(orderNumber);
        this.customer = new SimpleStringProperty(customer);
        this.condition = new SimpleStringProperty(condition);
        this.startDate = startDate;
        this.endDate = endDate;
        this.deliveryDate = deliveryDate;
    }
    
    public Order(int id, String orderNumber, String customer, Date deliveryDate) {
        this.ID = new SimpleIntegerProperty(id);
        this.deliveryDate = deliveryDate;
        this.orderNumber = new SimpleStringProperty(orderNumber);
        this.customer = new SimpleStringProperty(customer);
    }

    public int getId() {
        return ID.get();
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

    public String getCondition()
    {
        return condition.get();
    }

    public Date getDeliveryDate()
    {
        return deliveryDate;
    }
    
    public void setCondition(StringProperty condition)
    {
        this.condition = condition;
    }
}
