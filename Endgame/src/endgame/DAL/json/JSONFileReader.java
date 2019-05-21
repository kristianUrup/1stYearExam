/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package endgame.DAL.json;

import endgame.BE.Department;
import endgame.BE.Order;
import endgame.DAL.Exception.DalException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Frederik Jensen
 */
public class JSONFileReader
{

    private IOrder jsonOrder;
    private IWorker jsonWorker;
    private IDepartmentTask jsonTask;
    private JSONDAO jdao;

    public JSONFileReader()
    {
        jsonOrder = new JSONOrder();
        jsonWorker = new JSONWorker();
        jsonTask = new JSONDepartmentTask();
        jdao = new JSONDAO();
    }

    public void getJsonFile() throws DalException
    {
        for (File fileEntry : getFiles().listFiles())
        {
            if (getFileExtension(fileEntry).equals("json"))
            {
                readJsonFile(fileEntry.getAbsolutePath());
                fileEntry.delete();
            }
        }
    }

    private static String getFileExtension(File file)
    {
        String fileName = file.getName();
        if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
        {
            return fileName.substring(fileName.lastIndexOf(".") + 1);
        } else
        {
            return "";
        }
    }

    private void readJsonFile(String json) throws DalException
    {
        getWorkersFromJson(json);
        getOrdersFromJson(json);
    }

    private void getWorkersFromJson(String json) throws DalException
    {
        try
        {
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(json));
            JSONArray avalWorkers = (JSONArray) jsonObject.get("AvailableWorkers");
            for (Object object : avalWorkers)
            {
                String name = jsonWorker.getName(object);
                String initials = jsonWorker.getInitials(object);
                int salaryNumber = jsonWorker.getSalaryNumber(object);
                jdao.addWorker(name, initials, salaryNumber);
            }
        } catch (IOException | ParseException ex)
        {
            throw new DalException("Could not read workers from jsonfile");
        }

    }

    private void getOrdersFromJson(String json) throws DalException
    {
        try
        {
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(json));
            JSONArray orders = (JSONArray) jsonObject.get("ProductionOrders");
            for (Object object : orders)
            {
                Date deliveryDate = jsonOrder.getDeliveryTime(object);
                String customer = jsonOrder.getCustomer(object);
                String orderNumber = jsonOrder.getOrderNumber(object);
                Order order = jdao.addOrder(orderNumber, customer, deliveryDate);
                getTasksFromJson(object, order);
            }
        } catch (IOException | ParseException ex)
        {
            Logger.getLogger(JSONFileReader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void getTasksFromJson(Object object, Order order) throws DalException
    {
        JSONObject jObject = (JSONObject) object;
        JSONArray tasks = (JSONArray) jObject.get("DepartmentTasks");
        for (Object object2 : tasks)
        {
            Department dep = null;
            String department = jsonTask.getDepartment(object2);
            if (jdao.departmentExists(department)) {
                dep = jdao.getDepartment(department);
            } else {
                dep = jdao.addDepartment(department);
            }
            Date startDate = jsonTask.getStartDate(object2);
            Date endDate = jsonTask.getEndDate(object2);
            boolean isDone = jsonTask.isOrderFinished(object2);
            jdao.addDepartmentTask(dep, order, startDate, endDate, isDone);
        }
    }
    
    private File getFiles() {
        File folder = new File("src/data");
        return folder;
    }
}
