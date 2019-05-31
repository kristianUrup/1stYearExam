/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package endgame.DAL.json;

import org.json.simple.JSONObject;

/**
 *
 * @author Frederik Jensen
 */
public class JSONWorker implements IWorker
{

    /**
     * Gets the name of the worker and then returns it
     * @param object
     * @return 
     */
    @Override
    public String getName(Object object)
    {
        JSONObject jObject = (JSONObject) object;
        String workerName = (String) jObject.get("Name");
        return workerName;
    }

    /**
     * Gets the initials of the worker and then returns it
     * @param object
     * @return 
     */
    @Override
    public String getInitials(Object object)
    {
        JSONObject jObject = (JSONObject) object;
        String workerInitials = (String) jObject.get("Initials");
        return workerInitials;
    }

    /**
     * Gets the salarynumber of the worker and then returns it
     * @param object
     * @return 
     */
    @Override
    public int getSalaryNumber(Object object)
    {
        JSONObject jObject = (JSONObject) object;
        long salaryNumber = (long) jObject.get("SalaryNumber");
        int number = (int) salaryNumber;
        return number;
    }
    
}
