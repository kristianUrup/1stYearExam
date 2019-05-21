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
    private StringProperty initials;
    private IntegerProperty salarynumber;

    public Worker(int id, String name, String initials, int salarynumber) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.initials = new SimpleStringProperty(initials);
        this.salarynumber = new SimpleIntegerProperty(salarynumber);
    }

    public int getId() {
        return id.get();
    }

    public void setId(IntegerProperty id) {
        this.id = id;
    }

    public String getName() {
        return name.get();
    }

    public void setName(StringProperty name) {
        this.name = name;
    }

    public String getInitials() {
        return initials.get();
    }

    public void setInitials(StringProperty initials) {
        this.initials = initials;
    }

    public int getSalarynumber() {
        return salarynumber.get();
    }

    public void setSalarynumber(IntegerProperty salarynumber) {
        this.salarynumber = salarynumber;
    }
    
    
}
