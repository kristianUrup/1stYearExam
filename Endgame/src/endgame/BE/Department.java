/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package endgame.BE;

import java.util.Date;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
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
    private BooleanProperty isDone;
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
    
    public Department(int id, String name, Boolean isDone, Date startDate, Date endDate) {
        this.ID = new SimpleIntegerProperty(id);
        this.NAME = new SimpleStringProperty(name);
        this.isDone = new SimpleBooleanProperty(isDone);
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
    
    public boolean getIsDone() {
        return isDone.get();
    }

    public void setIsDone(Boolean value)
    {
        isDone.set(value);
        
    }

    public Date getStartDate()
    {
        return startDate;
    }

    public Date getEndDate()
    {
        return endDate;
    }
    
    public BooleanProperty getIsDoneProperty()
    {
        return isDone;
    }
    
    public StringProperty getNameProperty()
    {
        return NAME;
    }
    
}
