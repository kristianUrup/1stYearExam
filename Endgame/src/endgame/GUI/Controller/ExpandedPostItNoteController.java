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
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

/**
 * FXML Controller class
 *
 * @author Kristian Urup laptop
 */
public class ExpandedPostItNoteController implements Initializable
{

    @FXML
    private AnchorPane topAnchorPane;
    @FXML
    private Label topLabel;
    @FXML
    private Button done;
    @FXML
    private TableView<Department> tableDepartmentList;
    @FXML
    private TableColumn<Department, String> cellDepartment;
    @FXML
    private TableColumn<Department, Boolean> cellStatus;
    @FXML
    private TableView<Department> tableWorkersID;
    @FXML
    private TableColumn<Department, Integer> cellWorkersID;
    @FXML
    private ProgressBar estimatedProgress;
    @FXML
    private Label lblOrderNumber;
    @FXML
    private Label lblCustomer;
    @FXML
    private Label lblDeliveryDate;
    @FXML
    private Label lblLastActive;
    @FXML
    private Label lblEndDate;
    @FXML
    private Label lblStartDate;
    PlatformController pfcontroller;
    OrderModel OMO;
    Order ordersForDepartment;
    Department department;
    @FXML
    private StackPane stackPane;

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
            //   cellDepartment.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
            //  cellStatus.setCellValueFactory(new PropertyValueFactory<>("isDone"));
            cellStatus.setCellValueFactory(cellData -> cellData.getValue().getIsDoneProperty());
        } catch (BllException ex)
        {
            OMO.setLastActivity(ordersForDepartment, department, ex.getMessage());
        }

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
            
            Date startDate = order.getStartDate();
            String startStringDate = new SimpleDateFormat("dd/MM/yyyy").format(startDate);
            lblStartDate.setText(startStringDate);
            
            Date endDate = order.getEndDate();
            String endStringDate = new SimpleDateFormat("dd/MM/yyyy").format(endDate);
            lblEndDate.setText(endStringDate);

            lblDeliveryDate.setText(output);

            setProgressBar();

            tableDepartmentList.setItems(OMO.getAllDepartments(ordersForDepartment));
            setStatusColor();

            getLastActive();
            updateOrder(ordersForDepartment);
            updateDepartmentList();

        } catch (BllException ex)
        {
            OMO.setLastActivity(ordersForDepartment, department, ex.getMessage());
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
        OMO.setLastActivity(ordersForDepartment, department, "Task was marked as done");
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
        TimerTask repeatedTask = new TimerTask()
        {
            @Override
            public void run()
            {
                try
                {
                    ordersForDepartment = OMO.getOrder(department, order);
                    Platform.runLater(() -> lblOrderNumber.setText(ordersForDepartment.getOrderNumber()));
                    Platform.runLater(() -> lblCustomer.setText(ordersForDepartment.getCustomer()));
                    Date date = ordersForDepartment.getDeliveryDate();

                    DateFormat outputFormatter = new SimpleDateFormat("dd/MM/yyyy");
                    String output = outputFormatter.format(date);

                    Platform.runLater(() -> lblDeliveryDate.setText(output));

                    Platform.runLater(() -> setProgressBar());
                    
                    String lastActive = OMO.getLastActivity(ordersForDepartment);
                    
                    Platform.runLater(() -> lblLastActive.setText(lastActive));
                } catch (BllException ex)
                {
                    OMO.setLastActivity(ordersForDepartment, department, ex.getMessage());
                }
            }
        };
        Timer timer = new Timer();

        long delay = 5000L;
        long period = 5000L;
        timer.scheduleAtFixedRate(repeatedTask, delay, period);
    }

    public void updateDepartmentList()
    {
        TimerTask repeatedTask = new TimerTask()
        {
            @Override
            public void run()
            {
                try
                {
                    OMO.refreshDepartments(ordersForDepartment);
                } catch (BllException ex)
                {
                    Logger.getLogger(PostItController.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        };
        Timer timer = new Timer();

        long delay = 5000L;
        long period = 5000L;

        timer.scheduleAtFixedRate(repeatedTask, delay, period);
    }

    public void setStatusColor() throws BllException
    {
        cellStatus.setCellFactory(column ->
        {
            return new TableCell<Department, Boolean>()
            {
                @Override
                protected void updateItem(Boolean item, boolean empty)
                {
                    super.updateItem(item, empty);
                    
                    if (item == null || empty)
                    {
                        setText(null);
                        setStyle("");
                    } else
                    {
                        if (item) //færdig
                        {
                            setStyle("-fx-background-color: green");
                            setText("Done");
                        } 
                        else if (!item && System.currentTimeMillis() > ordersForDepartment.getEndDate().getTime()) 
                        {
                            setStyle("-fx-background-color: red");
                            setText("Behind");
                        } 
                        else if (!item && System.currentTimeMillis() > ordersForDepartment.getStartDate().getTime()                 
                            && (ordersForDepartment.getEndDate().getTime()) < System.currentTimeMillis())
                        {
                            setStyle("-fx-background-color: blue");
                            setText("Ongoing");
                        }
                        else if (!item && System.currentTimeMillis() < ordersForDepartment.getStartDate().getTime())
                        {
                            setStyle("-fx-background-color: yellow");
                            setText("Not started");
                        }
                                 

                    }
                }
            };
        });}

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
    
    public StackPane getStackPane()
    {
        return stackPane;
    }

    @FXML
    private void handleStackPaneClicked(MouseEvent event)
    {
//        pfcontroller.getFlowPane().getChildren().remove(stackPane);
    }
    
}

