/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package endgame.GUI.Controller;

import endgame.BE.Department;
import endgame.BLL.Exception.BllException;
import endgame.GUI.Model.OrderModel;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Frederik Jensen
 */
public class DepartmentProgressionController implements Initializable
{

    private OrderModel OMO;
    
    private Department currentDep;
    
    @FXML
    private ProgressBar prgDepProg;
    @FXML
    private Label lblDepartment;
    @FXML
    private Label lblStartDate;
    @FXML
    private Label lblEndDate;
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
            Logger.getLogger(DepartmentProgressionController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void setDepartment(Department department) {
        currentDep = department;
        setAttributes();
    }
    
    private void setAttributes() {
        try
        {
            Date startDate = currentDep.getStartDate();
            Date endDate = currentDep.getEndDate();
            prgDepProg.setProgress(OMO.getProgressedTimeInProcent(startDate, endDate));
            
            String startDateText = new SimpleDateFormat("[ww:u]").format(startDate);
            String endDateText = new SimpleDateFormat("[ww:u]").format(endDate);
            
            lblStartDate.setText(startDateText);
            lblEndDate.setText(endDateText);
            lblDepartment.setText(currentDep.getName());
        } catch (BllException ex)
        {
            Logger.getLogger(DepartmentProgressionController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void handlerClosePostIt(MouseEvent event)
    {
        Stage stage = (Stage) anchorPane.getScene().getWindow();
        stage.close();
    }
    
}
