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
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollBar;
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

            lblDeliveryDate.setText(output);

            setProgressBar();
            tableDepartmentList.setItems(OMO.getAllDepartments(ordersForDepartment));
            setStatusColor();
            
            getLastActive();
            updateOrder(ordersForDepartment);

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
//                    Platform.runLater(() ->
//                    {
//                        try
//                        {
//                            tableDepartmentList.setItems(OMO.getAllDepartments(ordersForDepartment));
//                        } catch (BllException ex)
//                        {
//                            Logger.getLogger(PostItController.class.getName()).log(Level.SEVERE, null, ex);
//                        }
//                    });
                } catch (BllException ex)
                {
                    Logger.getLogger(PostItController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        Timer timer = new Timer();

        long delay = 2000L;
        long period = 2000L;
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
                        if (item)
                        {
                            setStyle("-fx-background-color: green");
                        } else
                        {
                            setStyle("-fx-background-color: red");
                        }
                    }
                }
            };
        });
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

    /*  public void bindScrollBars() {
        //ScrollBar scrollBarPane = getScrollBar(scrollPane);
        //ScrollBar scrollBarTable = getScrollBar(table);
        //scrollBarPane.valueProperty().bind(scrollBarTable.valueProperty());
        tableDepartmentList.setOnScrollTo(tableStatus.getOnScrollTo());
    }
    
    private static ScrollBar getScrollBar(Control source) {
        ScrollBar scrollBar = null;
        for (Node node : source.lookupAll(".scroll-bar")) {
            if (node instanceof ScrollBar && ((ScrollBar) node).getOrientation().equals(Orientation.VERTICAL)) {
                scrollBar = (ScrollBar) node;
            }
        }
        return scrollBar;
}*/
}
