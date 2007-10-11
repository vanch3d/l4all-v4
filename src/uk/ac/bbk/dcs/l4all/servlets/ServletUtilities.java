package uk.ac.bbk.dcs.l4all.servlets;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * A coupple of time-saver routines for Servlet and Cookie management.
 * @author nicolas
 *
 */
public class ServletUtilities
{
    public final static String SESSION_USERNAME = "username";
    public final static String SESSION_TIMELINE = "timeline";
    public final static String SESSION_ADMIN = "admin";
    
    /**
     * The default document type declaration used for HTML documents 
     */
    public static final String DOCTYPE_HTML = 
	"<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">";

    /**
     * Default values for the expiration of cookies
     */
    public static final int SECONDS_PER_MONTH = 60*60*24*30,
    			    SECONDS_PER_YEAR = 60*60*24*365;
    
    /**
     * Read a parameter with the specified name, convert it to an int, and return it. 
     * Return the designated default value if the parameter doesn't exist or if it is 
     * an illegal integer format.
     * 
     * @param request
     * @param paramName
     * @param defaultValue
     * @return
     */
    public static int getIntParameter(HttpServletRequest request,
	    String paramName, int defaultValue)
    {
	String paramString = request.getParameter(paramName);
	int paramValue;
	try
	{
	    paramValue = Integer.parseInt(paramString);
	} 
	catch (NumberFormatException nfe)
	{ 
	    // Handles null and bad format
	    paramValue = defaultValue;
	}
	return (paramValue);
    }

    /**
     * Read the value of a cookie with the specified name and return it. 
     * Return null if the parameter doesn't exist.
     * 
     * @param cookies		The list of all available cookies
     * @param cookieName	The name of the cookie to look for
     * @return			
     */
    public static String getCookieStringValue(Cookie[] cookies, String cookieName)
    {
	return getCookieStringValue(cookies,cookieName,null);
    }
    
    /**
     * Read the value of a cookie with the specified name and return it. 
     * Return the designated default value if the parameter doesn't exist.
     * 
     * @param cookies		The list of all available cookies
     * @param cookieName	The name of the cookie to look for
     * @param defaultValue	The default value to use if the cookie does not exist
     * @return			
     */
    public static String getCookieStringValue(Cookie[] cookies, String cookieName,
	    String defaultValue)
    {
	for (int i = 0; i < cookies.length; i++)
	{
	    Cookie cookie = cookies[i];
	    if (cookieName.equals(cookie.getName()))
		return (cookie.getValue());
	}
	return (defaultValue);
    }

    /**
     * Read the value of a cookie with the specified name and return it. 
     * Return the designated default value if the parameter doesn't exist.
     * 
     * @param cookies		The list of all available cookies
     * @param cookieName	The name of the cookie to look for
     * @param defaultValue	The default value to use if the cookie does not exist
     * @return			
     */
    public static int getCookieIntValue(Cookie[] cookies, String cookieName,
	    int defaultValue)
    {
	String cookieString = getCookieStringValue(cookies, cookieName, ""+defaultValue);
	int paramValue;
	try
	{
	    paramValue = Integer.parseInt(cookieString);
	} 
	catch (NumberFormatException nfe)
	{ 
	    // Handles null and bad format
	    paramValue = defaultValue;
	}
	return (paramValue);
    }
    
}
