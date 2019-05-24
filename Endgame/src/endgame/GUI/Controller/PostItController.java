/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package endgame.GUI.Controller;

import endgame.BE.Department;
import endgame.BE.Order;
import endgame.BLL.Exception.BllException;
import endgame.GUI.Model.OrderModel;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Kristian Urup laptop
 */
public class PostItController implements Initializable
{

    private Department department;
    
    private Order ordersForDepartment;
    
    private OrderModel OMO;

    @FXML
    private Label lblOrderNumber;
    @FXML
    private Label lblCustomer;
    @FXML
    private Label lblDeliveryDate;
    @FXML
    private AnchorPane anchorPane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {

        try
        {
            OMO = new OrderModel();
        } catch (BllException ex)
        {
            OMO.setLastActivity(ordersForDepartment, department, ex.getMessage());
        }

    }

    public void setOrderInfo(Order order)
    {
        ordersForDepartment = order;
        lblOrderNumber.setText(order.getOrderNumber());
        lblCustomer.setText(order.getCustomer());
        Date date = order.getDeliveryDate();
        DateFormat outputFormatter = new SimpleDateFormat("dd/MM/yyyy");
        String output = outputFormatter.format(date);
        lblDeliveryDate.setText(output);
    }

    public void setDepartment(Department department)
    {
        this.department = department;
    }

    public void showDeliveryDate(Order order) throws BllException
    {
        lblDeliveryDate.setText(ordersForDepartment.getDeliveryDate().toString());

        Date date = ordersForDepartment.getDeliveryDate();

        DateFormat outputFormatter = new SimpleDateFormat("dd/MM/yyyy");
        String output = outputFormatter.format(date);

        lblDeliveryDate.setText(output);
    }
    
    public AnchorPane getAnchorPane() {
        return anchorPane;
    }
    


}
