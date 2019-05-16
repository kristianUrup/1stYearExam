/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package endgame.DAL.json;

/**
 *
 * @author Frederik Jensen
 */
public interface IWorker
{
    public String getName(Object object);
    
    public String getInitials(Object object);
    
    public int getSalaryNumber(Object object);
}
