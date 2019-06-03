/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package endgame.DAL.json;

import endgame.DAL.Exception.DalException;
import java.text.ParseException;
import java.util.Date;
import org.json.simple.JSONObject;

/**
 *
 * @author Frederik Jensen
 */
public class JSONDepartmentTask implements IDepartmentTask
{

    /**
     * Gets the department object and then returns it
     * @param object
     * @return name
     */
    @Override
    public String getDepartment(Object object)
    {
        JSONObject jObject = (JSONObject) object;
        JSONObject department = (JSONObject) jObject.get("Department");
        String name = (String) department.get("Name");
        return name;
    }

    /**
     * Gets the condition of the JsonObject(finished || !finished) and then returns it
     * @param object
     * @return 
     */
    @Override
    public boolean isOrderFinished(Object object)
    {
        JSONObject jObject = (JSONObject) object;
        boolean isOrderDone = (boolean) jObject.get("FinishedOrder");
        return isOrderDone;
    }

    /**
     * Gets the startdate of the json object, converts it and then returns it
     * @param object
     * @return
     * @throws DalException 
     */
    @Override
    public Date getStartDate(Object object) throws DalException
    {
        try
        {
            JSONObject jObject = (JSONObject) object;
            String startDate = (String) jObject.get("StartDate");
            return convertJSONDate(startDate);
        } catch (ParseException ex)
        {
            throw new DalException("Could not parse date");
        }
    }

    /**
     * Gets the enddate of the json object, converts it and then returns it
     * @param object
     * @return
     * @throws DalException 
     */
    @Override
    public Date getEndDate(Object object) throws DalException
    {
        try
        {
            JSONObject jObject = (JSONObject) object;
            String endDate = (String) jObject.get("EndDate");
            return convertJSONDate(endDate);
        } catch (ParseException ex)
        {
            throw new DalException("Could not parse date");
        }
    }
    
    /**
     * Gets the json Object as a string, converts it and then returns it
     * @param jsonDate
     * @return
     * @throws ParseException 
     */
    private Date convertJSONDate(String jsonDate) throws ParseException {
        int timeZoneIndex = jsonDate.indexOf("+");
        String date = "";
        if (timeZoneIndex != -1) {
            date = jsonDate.substring(6, timeZoneIndex);
        }
        long timeInMillis = Long.parseLong(date);
        Date deliveryDate = new Date(timeInMillis);
        return deliveryDate;
    }
    
}
