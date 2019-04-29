/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package endgame.BLL;

import endgame.BE.Department;
import endgame.BE.Order;

/**
 *
 * @author Kristian Urup laptop
 */
public class BLLFacade implements IBLLFacade
{
    OrderManager OMA;
    DepartmentManager DMA;
    
    public BLLFacade()
    {
        OMA = new OrderManager();
        DMA = new DepartmentManager();
    }
    
    public Order getOrder()
    {
        return OMA.getOrder();
    }
    
    public Department getDepartment()
    {
        return DMA.getDepartment();
    }
}
