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
import java.io.FileReader;
import java.io.IOException;
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

    /**
     * Gets the JsonFile
     * @throws DalException 
     */
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

    /**
     * Gets the extension of the file and checks if the 
     * @param file
     * @return 
     */
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

    /**
     * Uses the methods getWorkersfromJson and getOrdersFromJson to read the Json file
     * @param json
     * @throws DalException 
     */
    private void readJsonFile(String json) throws DalException
    {
        getWorkersFromJson(json);
        getOrdersFromJson(json);
    }

    /**
     * Gets all available workers from the Json file containing name, initials and salary number
     * Then the workers are added to the database by using the addWorker method from JsonDAO
     * @param json
     * @throws DalException 
     */
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
    /**
     * Gets all production orders from the Json file containing ordernumber, customer and deliverydate
     * Then the orders are added to the database by using the addOrder method from JsonDAO
     * @param json
     * @throws DalException 
     */
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

    /**
     * Gets all department tasks from the Json file containing department, order, startdate, enddate and isDone
     * Then the tasks are added to the database by using the addDepartmentTask method from JsonDAO
     * @param object
     * @param order
     * @throws DalException 
     */
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
    
    /**
     * Creates a file object with the filedestination of the data folder
     * Then returns the folder
     * @return 
     */
    private File getFiles() {
        File folder = new File("src/data");
        return folder;
    }
}
