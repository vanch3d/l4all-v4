package uk.ac.bbk.dcs.l4all.servlets;

import java.io.IOException;
import java.util.Map;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author nicolas
 *
 */
public abstract class L4ALLServiceServlet extends HttpServlet implements Servlet
{
    /**
     * 
     */
    protected static String newline = System.getProperty("line.separator");
    
    /**
     * Return the value of the given parameter from the Map.
     * The trick is to assume that there is no duplication of parameters in the request and
     * therefore there is either no value or a single one.
     * @param map	The map in which to search the parameter 
     * @param key	The key of the parameter to retrieve
     * @return		The value of the given parameter
     */
    public String getParam(Map map, String key)
    {
	String res[] = (String[]) map.get(key);
	if (res == null)
	    return null;
	else
	    return res[0];
    }
    
    /**
     * Check if a servlet parameter is empty (empty string or <code>null</code>).
     * @param param	The value of the parameter
     * @return		<code>true</code> if the parameter if empty, <code>false</code> otherwise
     */
    public boolean isParamEmpty(String param)
    {
	return (param == null || param.trim().equals(""));
    }

    

    /**
     * Performs the HTTP POST operation. Overridden to call the abstract 
     * {@link #processRequest(HttpServletRequest, HttpServletResponse)} method.
     * 
     * @param request	HttpServletRequest that encapsulates the request to the servlet
     * @param response	HttpServletResponse that encapsulates the response from the servlet
     * @throws IOException	if detected when handling the request
     * @throws ServletException	if the request could not be handled 
     */
    protected void doPost(HttpServletRequest request,
	    HttpServletResponse response) throws ServletException, IOException
    {
	processRequest(request,response);
    }

    /**
     * Performs the HTTP GET operation. Overridden to call the abstract 
     * {@link #processRequest(HttpServletRequest, HttpServletResponse)} method.
     * 
     * @param request	HttpServletRequest that encapsulates the request to the servlet
     * @param response	HttpServletResponse that encapsulates the response from the servlet
     * @throws IOException	if detected when handling the request
     * @throws ServletException	if the request could not be handled 
     */
    protected void doGet(HttpServletRequest request,
	    HttpServletResponse response) throws ServletException, IOException
    {
	processRequest(request,response);
    }
    
    /**
     * @param request
     * @param response
     * @throws IOException
     */
    protected abstract void processRequest(HttpServletRequest request,
	    HttpServletResponse response) throws  ServletException, IOException  ;  
    
}
