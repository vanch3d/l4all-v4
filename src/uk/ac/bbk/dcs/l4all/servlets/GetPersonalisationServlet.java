package uk.ac.bbk.dcs.l4all.servlets;

import java.io.IOException;
import java.util.Hashtable;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class for Servlet: GetPersonalisationServlet
 *
 */
public class GetPersonalisationServlet extends L4ALLServiceServlet
{
    public static final String PERSO_TIMELINE = "TL_Interval";

    /**
     * 
     */
    private static final long serialVersionUID = 8247138511021996548L;

    /* (non-Java-doc)
     * @see javax.servlet.http.HttpServlet#HttpServlet()
     */
    public GetPersonalisationServlet()
    {
	super();
    }

    /** 
     * @todo	Need to implement an XML output for the online request.
     */
    protected void processRequest(HttpServletRequest request,
	    HttpServletResponse response) throws ServletException, IOException
    {
	Cookie[] cookies = request.getCookies();
    }
    
    /**
     * @param cookies
     * @return
     */
    public Hashtable processRequest(Cookie[] cookies)
    {
	String tlInterval = ServletUtilities.getCookieStringValue(cookies,"TL_Interval");

	return null;
    }
}