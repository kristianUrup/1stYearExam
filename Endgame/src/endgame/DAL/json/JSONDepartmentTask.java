/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package endgame.DAL.json;

import endgame.DAL.Exception.DalException;
import java.text.ParseException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONObject;

/**
 *
 * @author Frederik Jensen
 */
public class JSONDepartmentTask implements IDepartmentTask
{

    @Override
    public String getDepartment(Object object)
    {
        JSONObject jObject = (JSONObject) object;
        JSONObject department = (JSONObject) jObject.get("Department");
        String name = (String) department.get("Name");
        return name;
    }

    @Override
    public boolean isOrderFinished(Object object)
    {
        JSONObject jObject = (JSONObject) object;
        boolean isOrderDone = (boolean) jObject.get("FinishedOrder");
        return isOrderDone;
    }

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
    
    private Date convertJSONDate(String jsonDate) throws ParseException {
        int timeZoneIndex = jsonDate.indexOf("+");
        String date = "";
        if (timeZoneIndex != -1) {
            date = jsonDate.substring(6, timeZoneIndex);
            System.out.println(date);
        }
        long timeInMillis = Long.parseLong(date);
        Date deliveryDate = new Date(timeInMillis);
        return deliveryDate;
    }
    
}
