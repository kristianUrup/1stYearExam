/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package endgame.GUI.Controller;

import com.sun.prism.paint.Color;
import endgame.BE.Department;
import endgame.BE.Order;
import endgame.BLL.Exception.BllException;
import endgame.GUI.Model.OrderModel;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Kristian Urup laptop
 */
public class PostItController implements Initializable
{
    
    Department department;
    Order ordersForDepartment;
    PlatformController pfcontroller;
    OrderModel OMO;

    @FXML
    private Label lblOrderNumber;
    @FXML
    private Label lblCustomer;
    @FXML
    private Label lblDeliveryDate;
    @FXML
    private Label lblLastActive;
    @FXML
    private ProgressBar estimatedProgress;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Button done;
    @FXML
    private TableView<Department> tableDepartmentList;
    @FXML
    private TableColumn<Department, String> cellDepartment;
    @FXML
    private TableColumn<Department, Boolean> cellStatus;
    @FXML
    private TableView<Department> tableStatus;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        try
        {
            pfcontroller = new PlatformController();
            OMO = new OrderModel();
            cellDepartment.setCellValueFactory(new PropertyValueFactory<>("name"));
            cellStatus.setCellValueFactory(new PropertyValueFactory<>("isDone"));
        } catch (BllException ex)
        {
        }
        
        cellDepartment.setCellValueFactory(new PropertyValueFactory<>("name"));
        cellStatus.setCellValueFactory(new PropertyValueFactory<>("isDone"));
//        updateOrder(ordersForDepartment);
        tableDepartmentList.setItems(departments());
        setStatusColor();
    }

    public void setOrderInfo(Order order)
    {

        try
        {
            ordersForDepartment = order;
            lblOrderNumber.setText(order.getOrderNumber());
            lblCustomer.setText(order.getCustomer());
            
            Date date = order.getDeliveryDate();
            
            DateFormat outputFormatter = new SimpleDateFormat("dd/MM/yyyy");
            String output = outputFormatter.format(date);
            
            lblDeliveryDate.setText(output);
            
            setProgressBar();
            
            tableDepartmentList.setItems(OMO.getAllDepartments(ordersForDepartment));
            setStatusColor();
        } catch (BllException ex)
        {
            Logger.getLogger(PostItController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    public void setDepartment(Department department)
    {
        this.department = department;
    }

    public Button getButton()
    {
        return done;
    }

    public void setDone() throws BllException
    {
        OMO.changeOrderState(ordersForDepartment, department);
    }

    public void showDeliveryDate(Order order) throws BllException
    {
        lblDeliveryDate.setText(ordersForDepartment.getDeliveryDate().toString());

        Date date = ordersForDepartment.getDeliveryDate();

        DateFormat outputFormatter = new SimpleDateFormat("dd/MM/yyyy");
        String output = outputFormatter.format(date);

        lblDeliveryDate.setText(output);
    }

    private void setProgressBar()
    {
        try
        {
            estimatedProgress.setProgress(OMO.getProgressedTimeInProcent(ordersForDepartment));
        } catch (BllException ex)
        {
            OMO.setLastActivity(ordersForDepartment, department, ex.getMessage());
        }
    }

    public void updateOrder(Order order)
    {
        ordersForDepartment = order;
        TimerTask repeatedTask = new TimerTask()
        {
            @Override
            public void run()
            {
                Date date = ordersForDepartment.getDeliveryDate();

                DateFormat outputFormatter = new SimpleDateFormat("dd/MM/yyyy");
                String output = outputFormatter.format(date);

                lblDeliveryDate.setText(output);
                System.out.println("Updated post it note");
                //cancel();   
            }
        };
        Timer timer = new Timer();

        long delay = 1000L;
        long period = 1000L;
        timer.scheduleAtFixedRate(repeatedTask, delay, period);
    }

    public void setStatusColor()
    {

        tableStatus.setRowFactory(tv-> new TableRow<Department>(){
            @Override
            public void updateItem(Department department, boolean empty)
            {
                super.updateItem(department, empty);
                if (department == null)
                {
                    setStyle("");
                } else if (department.getIsDone())
                {
                    setStyle("-fx-background-color: #7CFC00;");
                } else
                {
                    setStyle("-fx-background-color: #Ea3c53;");
                }
            }
        });

        /*
            cellStatus.setCellFactory(column -> new TableCell<Department, Boolean>(){
                @Override
                protected void updateItem(Department department, boolean empty)
                {
                    super.updateItem(department, empty);
                    if(department == null)
                    {
                        setText(null);
                        setStyle("");
                    }
                    else if(department.getIsDone())
                    {
                        setText(null);
                        setStyle("-fx-background-color: green;");
                    }
                    else
                    {
                        setText(null);
                        setStyle("-fx-background-color: red");
                    }
                }
            });*/
    }

    public ObservableList<Department> departments()
    {
        ObservableList<Department> departments = FXCollections.observableArrayList();

        Department d1 = new Department(1, "Fisk", true, new Date("20/02/2019"), new Date("20/05/2019"));
        Department d2 = new Department(2, "Funky", false, new Date("20/01/2019"), new Date("23/06/2019"));
        Department d3 = new Department(3, "Frederik", false, new Date("01/03/2019"), new Date("20/12/2019"));

        departments.add(d1);
        departments.add(d2);
        departments.add(d3);

        return departments;
    }
    
    @FXML
    public void getLastActive()
    {
        try
        {
            lblLastActive.setText(OMO.getLastActivity(ordersForDepartment));
        } catch (BllException ex)
        {
            OMO.setLastActivity(ordersForDepartment, department, ex.getMessage());
        }
    }
}
