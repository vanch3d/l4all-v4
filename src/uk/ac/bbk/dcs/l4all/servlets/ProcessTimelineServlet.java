package uk.ac.bbk.dcs.l4all.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Hashtable;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class for Servlet: ProcessTimelineServlet
 *
 */
public class ProcessTimelineServlet extends javax.servlet.http.HttpServlet
	implements javax.servlet.Servlet
{
    /* (non-Java-doc)
     * @see javax.servlet.http.HttpServlet#HttpServlet()
     */
    public ProcessTimelineServlet()
    {
	super();
    }

    private void processRequest(HttpServletRequest request,HttpServletResponse response)
    {
	response.setContentType("text/html");
	response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1
	response.setHeader("Pragma", "no-cache"); //HTTP 1.0
	
	HttpSession session = request.getSession();
	String username = (String) session.getAttribute(ServletUtilities.SESSION_USERNAME);
	
	ManageTimelineServlet tmlMng = new ManageTimelineServlet();
	Hashtable map = new Hashtable(request.getParameterMap());
	String action = request.getParameter("action");
	String res = tmlMng.processRequest(action,username, map);
	
	String tlScale = request.getParameter("band_scale");
	String tlInterval = request.getParameter("band_width");
        if (tlInterval!=null)
        {
     	    Cookie searchStringCookie = new Cookie("TL_Interval", tlInterval);
    	    searchStringCookie.setMaxAge(ServletUtilities.SECONDS_PER_YEAR);
    	    response.addCookie(searchStringCookie);
        }
        if (tlScale!=null)
        {
    	    Cookie searchStringCookie = new Cookie("TL_Scale", tlScale);
    	    searchStringCookie.setMaxAge(ServletUtilities.SECONDS_PER_YEAR);
    	    response.addCookie(searchStringCookie);
        }

        ServletContext context = getServletContext();
	RequestDispatcher rd = null ;
	rd = context.getRequestDispatcher("/jsp/editTimeline.jsp");
	request.setAttribute("result", res);
	try
	{
	    rd.include(request, response);
	} catch (ServletException e1)
	{
	    // TODO Auto-generated catch block
	    e1.printStackTrace();
	} catch (IOException e1)
	{
	    // TODO Auto-generated catch block
	    e1.printStackTrace();
	}

    }

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
}