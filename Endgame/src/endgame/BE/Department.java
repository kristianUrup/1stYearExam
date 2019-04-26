/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package endgame.BE;

/**
 *
 * @author bonde
 */
public class Department
{
    private int id;
    private String name;

    /**
     * Constructs a department with an id and a name.
     * @param id
     * @param name 
     */
    public Department(int id, String name)
    {
        this.id = id;
        this.name = name;
    }

    /**
     * Gets the name of the created department.
     * @return 
     */
    public String getName()
    {
        return name;
    }

    /**
     * Gets the id of the created department.
     * @return 
     */
    public int getId()
    {
        return id;
    }
}
