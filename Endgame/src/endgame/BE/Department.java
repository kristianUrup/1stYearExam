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
public class Department
{
    private final IntegerProperty ID;
    private final StringProperty NAME;
    private StringProperty condition;
    private Date startDate;
    private Date endDate;

    /**
     * Constructs a department with an id and a name.
     * @param id
     * @param name 
     */
    public Department(int id, String name)
    {
        this.ID = new SimpleIntegerProperty(id);
        this.NAME = new SimpleStringProperty(name);
    }
    /**
     *  constructs a department with an id, a name, a condtion, a startdate and an enddate
     * @param id
     * @param name
     * @param condition
     * @param startDate
     * @param endDate 
     */
    public Department(int id, String name, String condition, Date startDate, Date endDate) {
        this.ID = new SimpleIntegerProperty(id);
        this.NAME = new SimpleStringProperty(name);
        this.condition = new SimpleStringProperty(condition);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    /**
     * Gets the name of the created department.
     * @return 
     */
    public String getName()
    {
        return NAME.get();
    }

    /**
     * Gets the id of the created department.
     * @return 
     */
    public int getId()
    {
        return ID.get();
    }
    /**
     * Gets the Condition of the Department
     * @return 
     */
    public String getCondition() {
        return condition.get();
    }
    
    /**
     * Sets the Condition of the Department
     * @param value 
     */
    public void setCondition(String value)
    {
        condition.set(value);
        
    }
    
    /**
     * Gets the departments' startdate
     * @return 
     */
    public Date getStartDate()
    {
        return startDate;
    }
    
    /**
     * Gets the departments' enddate
     * @return 
     */
    public Date getEndDate()
    {
        return endDate;
    }
    
    /**
     * Gets the StringProperty of the departments' condition
     * @return 
     */
    public StringProperty getConditionProperty()
    {
        return condition;
    }
    /**
     * Gets the StringProperty of the departments' name
     * @return 
     */
    public StringProperty getNameProperty()
    {
        return NAME;
    }

    /**
     * Gets the name of the Department in a string format
     * @return 
     */
    @Override
    public String toString()
    {
        return NAME.getValue();
    }
    
}
