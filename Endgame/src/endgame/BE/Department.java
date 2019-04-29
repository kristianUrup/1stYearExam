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
 * @author bonde
 */
public class Department
{
    private final IntegerProperty ID;
    private final StringProperty NAME;

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
}
