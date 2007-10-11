package uk.ac.bbk.dcs.l4all.servlets;

import java.io.IOException;

import javax.mail.Session;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import uk.ac.bbk.dcs.l4all.vocabulary.Trails;

/**
 * Servlet implementation class for Servlet: CheckAdminServlet
 *
 */
 public class CheckAdminServlet extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
    /* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public CheckAdminServlet() {
		super();
	}   	
	
	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	    String username = request.getParameter("username");
	    String password = request.getParameter("password");
	    String redirect = request.getParameter("redirect");

	    RequestDispatcher rd = null;
	    ServletContext context = getServletContext();
	    
	    if ("administrator".equals(username) && 
		"administrator".equals(password))
	    {
		HttpSession session = request.getSession();
		session.setAttribute(ServletUtilities.SESSION_ADMIN, "admin");
		String serverURL = "http://" + request.getServerName() + ":" + 
	    		request.getServerPort() + request.getContextPath() + "/";
		if (redirect!=null && !redirect.equals(""))
		    response.sendRedirect(response.encodeRedirectURL(serverURL + redirect));
		else
		    response.sendRedirect(response.encodeRedirectURL(serverURL + "admin/index.jsp"));
	    }
	    else
	    {
		request.setAttribute("LOGIN_MSG","Invalid Username or password. Please try again.");
		rd = context.getRequestDispatcher("/admin/login.jsp");
		rd.include(request, response);
		
	    }
		
	    
	}   	  	    
}