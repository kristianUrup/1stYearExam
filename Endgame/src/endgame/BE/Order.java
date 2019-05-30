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

    
    /**
     *  constructs an order with an id, an ordernumber, a customer, a startdate, an enddate, a condition and a deliverydate
     * @param id
     * @param orderNumber
     * @param customer
     * @param startDate
     * @param endDate
     * @param condition
     * @param deliveryDate 
     */
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
    /**
     * constructs an order with an id, an ordernumber a customer and a deliverydate
     * @param id
     * @param orderNumber
     * @param customer
     * @param deliveryDate 
     */
    public Order(int id, String orderNumber, String customer, Date deliveryDate) {
        this.ID = new SimpleIntegerProperty(id);
        this.deliveryDate = deliveryDate;
        this.orderNumber = new SimpleStringProperty(orderNumber);
        this.customer = new SimpleStringProperty(customer);
    }

    /**
     * Gets the order's id
     * @return 
     */
    public int getId() {
        return ID.get();
    }
    
    /**
     * Gets the order's ordernumber
     * @return 
     */
    public String getOrderNumber()
    {
        return orderNumber.get();
    }

    /**
     * Gets the customer of the order
     * @return 
     */
    public String getCustomer()
    {
        return customer.get();
    }

    /**
     * Gets the startdate of the order
     * @return 
     */
    public Date getStartDate()
    {
        return startDate;
    }

    /**
     * Gets the Enddate of the order
     * @return 
     */
    public Date getEndDate()
    {
        return endDate;
    }

    /**
     * Gets the condition of the order
     * @return 
     */
    public String getCondition()
    {
        return condition.get();
    }

    /**
     * Gets the deliverydate of the order
     * @return 
     */
    public Date getDeliveryDate()
    {
        return deliveryDate;
    }
    
    /**
     * Sets the condition of the order as an StringProperty
     * @param condition 
     */
    public void setCondition(StringProperty condition)
    {
        this.condition = condition;
    }
}
