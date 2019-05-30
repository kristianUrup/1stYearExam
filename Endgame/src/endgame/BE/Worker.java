/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package endgame.BE;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Frederik Jensen
 */
public class Worker
{
    private IntegerProperty id;
    private StringProperty name;
    private IntegerProperty salaryNumber;
    private StringProperty initials;

    /**
     * constructs a worker with an id, a name, a salarynumber and initials
     * @param id
     * @param name
     * @param salaryNumber
     * @param initials 
     */
    public Worker(int id, String name, int salaryNumber, String initials)
    {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.salaryNumber = new SimpleIntegerProperty(salaryNumber);
        this.initials = new SimpleStringProperty(initials);
    }

    /**
     * Gets the workers' id
     * @return 
     */
    public int getId()
    {
        return id.get();
    }
    /**
     * Sets the workers id
     * @param id 
     */
    public void setId(IntegerProperty id)
    {
        this.id = id;
    }

    /**
     * Gets the workers' name
     * @return 
     */
    public String getName()
    {
        return name.get();
    }

    /**
     * Sets the worker's name
     * @param name 
     */
    public void setName(StringProperty name)
    {
        this.name = name;
    }

    /**
     * Gets the worker's salary number
     * @return 
     */
    public int getSalaryNumber()
    {
        return salaryNumber.get();
    }

    /**
     * Sets the worker's salarynumber as an IntegerProperty
     * @param salaryNumber 
     */
    public void setSalaryNumber(IntegerProperty salaryNumber)
    {
        this.salaryNumber = salaryNumber;
    }
    
    /**
     * Gets the workers' initials
     * @return 
     */
    public String getInitials()
    {
        return initials.get();
    }

    /**
     * Sets the worker's initials as an StringProperty
     * @param initials 
     */
    public void setInitials(StringProperty initials)
    {
        this.initials = initials;
    }
    
    /**
     * Gets the workers' salarynumber in an IntegerProperty 
     * @return 
     */
    public IntegerProperty getSalaryNumberProperty()
    {
        return salaryNumber;
    }
}
