/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package endgame.GUI.Controller;

import endgame.BE.Department;
import endgame.BE.Order;
import endgame.BE.Worker;
import endgame.BLL.Exception.BllException;
import endgame.GUI.Model.OrderModel;
import java.awt.Color;
import java.io.IOException;
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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

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
    private TableColumn<Department, String> cellStatus;
    @FXML
    private TableView<Worker> tableWorkersID;
    @FXML
    private TableColumn<Worker, Integer> cellWorkersID;
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
    
    private PlatformController pfcontroller;
    
    private OrderModel OMO;
    
    private Order ordersForDepartment;
    
    private Department department;
    
    private StackPane stackPane;
    @FXML
    private BorderPane borderPane;
    @FXML
    private ImageView crossBtn;

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
            cellWorkersID.setCellValueFactory(new PropertyValueFactory <>("salaryNumber"));
            cellDepartment.setCellValueFactory(new PropertyValueFactory<>("name"));
            tableWorkersID.setItems(OMO.getAllWorkers());
            cellStatus.setCellValueFactory(cellData -> cellData.getValue().getConditionProperty());
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
            String startStringDate = new SimpleDateFormat("ww/u").format(startDate);
            lblStartDate.setText(startStringDate);
            
            Date endDate = order.getEndDate();
            String endStringDate = new SimpleDateFormat("ww/u").format(endDate);
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

    public ImageView getCrossView()
    {
        return crossBtn;
    }
    
    public Button getDoneButton()
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
            Date startDate = ordersForDepartment.getStartDate();
            Date endDate = ordersForDepartment.getEndDate();
            estimatedProgress.setProgress(OMO.getProgressedTimeInProcent(startDate, endDate));
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
            return new TableCell<Department, String>()
            {
                @Override
                protected void updateItem(String item, boolean empty)
                {
                    super.updateItem(item, empty);
                    
                    if (item == null || empty)
                    {
                        setText(null);
                        setStyle("");
                    } else
                    {
                        if (item.equals("finished")) //færdig
                        {
                            setStyle("-fx-background-color: green");
                            setText("Done");
                        } 
                        else if (item.equals("behind"))
                        {
                            setStyle("-fx-background-color: red");
                            setText("Behind");
                        } 
                        else if (item.equals("not started"))
                        {
                            setStyle("-fx-background-color: yellow");
                            setText("Not started");
                        }
                        else if (item.equals("ongoing"))
                        {
                            System.out.println("dsa");
                            setStyle("-fx-background-color: #0080FF");
                            setText("Ongoing");
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
    
    public BorderPane getBorderPane()
    {
        return borderPane;
    }

    @FXML
    private void handlerDepartmentClicked(MouseEvent event)
    {
        Department depClicked = tableDepartmentList.getSelectionModel().getSelectedItem();
        if (depClicked != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/View/DepartmentProgression.fxml"));
                Parent root = (Parent) loader.load();
                DepartmentProgressionController dpcontroller = loader.getController();
                dpcontroller.setDepartment(department);
                
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(ExpandedPostItNoteController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    
}

