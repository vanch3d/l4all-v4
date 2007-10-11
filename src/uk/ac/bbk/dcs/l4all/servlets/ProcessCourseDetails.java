package uk.ac.bbk.dcs.l4all.servlets;

import java.io.IOException;
import java.util.Hashtable;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class for Servlet: ProcessCourseDetails
 *
 */
public class ProcessCourseDetails extends javax.servlet.http.HttpServlet
	implements javax.servlet.Servlet
{

    /* (non-Java-doc)
     * @see javax.servlet.http.HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request,
	    HttpServletResponse response) throws ServletException, IOException
    {
	processRequest(request, response);
    }

    /* (non-Java-doc)
     * @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request,
	    HttpServletResponse response) throws ServletException, IOException
    {
	processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request,
	    HttpServletResponse response) throws ServletException, IOException
    {
	HttpSession session = request.getSession();
	GetCourseDetails search = new GetCourseDetails();
	String username = (String) session.getAttribute(ServletUtilities.SESSION_USERNAME);

	Hashtable map = new Hashtable(request.getParameterMap());
	String res = search.processRequest(username, map);

	ServletContext context = getServletContext();
	RequestDispatcher rd = null;
	rd = context.getRequestDispatcher("/jsp/showCourseDetail.jsp");
	request.setAttribute("data", res);
	try
	{
	    rd.include(request, response);
	} catch (IOException e)
	{
	    e.printStackTrace();
	}
    }

}