/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package endgame.DAL.Exception;

/**
 *
 * @author bonde
 */
public class DalException extends Exception
{
    /**
     * A cunstructor of the exception class DalException
     */
    public DalException() 
    {
        super();
    }
    
    /**
     * A cunstructor of the exception class DalException class. 
     * Throws a message if an exception has been catched
     * @param message the message getting thrown
     */
    public DalException(String message)
    {
        super(message);
    }
    
    /**
     * A cunstructor of the exception class DalExceptio class.
     * Throws a message and the exception if an exception has been catched
     * @param message the message getting thrown
     * @param ex the exception getting thrown
     */
    public DalException(String message, Exception ex)
    {
        super(message, ex);
    }    
}
