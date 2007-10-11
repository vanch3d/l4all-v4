package uk.ac.bbk.dcs.l4all.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Constructor;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import uk.ac.bbk.dcs.l4all.beans.ErrorCodes;
import uk.ac.bbk.dcs.l4all.beans.L4AllUser;
import uk.ac.bbk.dcs.l4all.beans.L4AllUserManager;
import uk.ac.bbk.dcs.l4all.beans.L4AllUsersSearch;
import uk.ac.bbk.dcs.l4all.beans.Message;
import uk.ac.bbk.dcs.l4all.beans.Trail;
import uk.ac.bbk.dcs.l4all.beans.TrailEpisode;
import uk.ac.bbk.dcs.l4all.beans.UserTrailManager;
import uk.ac.bbk.dcs.l4all.util.MessageBuilder;
import uk.ac.bbk.dcs.l4all.vocabulary.EpisodeType;
import uk.ac.bbk.dcs.l4all.vocabulary.Trails;
import uk.ac.shef.wit.simmetrics.similaritymetrics.AbstractStringMetric;

/**
 * Servlet implementation class for Servlet: SearchSimilarServlet
 * @author nicolas
 *
 */
public class SearchSimilarServlet extends L4ALLServiceServlet 
{
    private static final long serialVersionUID = -5104882931513909990L;
    
    /**
     * A reference to the trail manager
     */
    protected UserTrailManager trailMgr = new UserTrailManager();


    /**
     * Private comparator used to order the result of the similarity measure on users.
     *
     */
    private class SimilarUserComparator implements java.util.Comparator
    {
	/* (non-Javadoc)
	 * @see java.util.Comparator#compare(T, T)
	 */
	public int compare(Object o1, Object o2)
	{
	    L4AllUser ep1 = (L4AllUser) o1;
	    L4AllUser ep2 = (L4AllUser) o2;
	    if (ep1 == null || ep2 == null)
		throw new ClassCastException();
	    Float fl1 = new Float(ep1.getSimValue());
	    Float fl2 = new Float(ep2.getSimValue());
	    if (fl1.isNaN()) return 1;
	    if (fl2.isNaN()) return -1;
	    return fl2.compareTo(fl1);
	}
    }

    /**
     * Private comparator used to linearise the timeline's episodes
     *
     */
    private class EpisodeDateComparator implements java.util.Comparator
    {
	public int compare(Object o1, Object o2)
	{
	    String dateFormat = "yyyy/MM/dd";
	    String dateSecureFormat = "yyyy";

	    SimpleDateFormat format = new SimpleDateFormat(dateFormat);
	    SimpleDateFormat formatSecure = new SimpleDateFormat(
		    dateSecureFormat);

	    TrailEpisode ep1 = (TrailEpisode) o1;
	    TrailEpisode ep2 = (TrailEpisode) o2;

	    if (ep1 == null || ep2 == null)
		throw new ClassCastException();
	    int res = 0;
	    Date d1 = null;
	    Date d2 = null;

	    try
	    {
		d1 = format.parse(ep1.getStart());
	    } catch (ParseException e)
	    {
		try
		{
		    d1 = formatSecure.parse(ep1.getStart());
		} catch (ParseException e3)
		{
		    e3.printStackTrace();
		    throw new ClassCastException();
		}
	    }
	    try
	    {
		d2 = format.parse(ep2.getStart());
	    } catch (ParseException e)
	    {
		try
		{
		    d2 = formatSecure.parse(ep2.getStart());
		} catch (ParseException e3)
		{
		    e3.printStackTrace();
		    throw new ClassCastException();
		}
	    }
	    if (d1.equals(d2))
		res = 0;
	    else if (d1.before(d2))
		res = -1;
	    else
		res = 1;
	    return res;
	}
    }

    /**
     * @param ep
     * @param rand
     * @return
     */
    private String getEpisodeCoding(TrailEpisode ep,boolean rand)
    {
	//Random generator = new Random();

	String res = null;
	String cat = ep.getCategory();
	res = EpisodeType.getCoding(cat);
	String prim = ep.getPrimary();
	String sec = ep.getSecondary();
	if (prim==null || prim.isEmpty()) prim = "000";
	if (sec==null || sec.isEmpty()) sec = "000";
	if (!rand)
	{
	    prim = "000";
	    sec = "000";
	}
	res += "-" + prim + "-" + sec;
	
	return res;
    }

    /**
     * @param username
     * @param timeline
     * @param full
     * @return
     */
    public String getFormattedTimeline(Trail trail,boolean full)
    {
	String res = "";

	//Message msg = trailMgr.getUserTrailDetails(username, timeline);
	//Trail trail = (Trail) msg.getResultObject();
	if (trail==null) return res;
	TrailEpisode[] tEpisodes = trail.getTrailEpisodes();
	List list = Arrays.asList(tEpisodes);
	Collections.sort(list, new EpisodeDateComparator());
	for (int i = 0; i < list.size(); i++)
	{
	    TrailEpisode ep = (TrailEpisode) list.get(i);
	    res += getEpisodeCoding(ep,full) + " ";
	}
	return res;
	
    }
    /**
     * Extract the episodes from the user's timeline and order them
     * @param username
     * @param full
     * @return
     */
//    public String getFormattedTimeline(String username,boolean full)
//    {
//	return getFormattedTimeline(username, username,full);
//    }

    /**
     * @param name
     * @return
     * @throws Exception
     */
    public AbstractStringMetric getSimilarityMetric(String name)
	    throws Exception
    {
	AbstractStringMetric metric = null;
	String pack = "uk.ac.shef.wit.simmetrics.similaritymetrics";
	String classname = pack + "." + name;

	Class metricclass = Class.forName(classname);
	Constructor cons = metricclass.getConstructor(null);
	Object obj = cons.newInstance(null);
	if (obj instanceof AbstractStringMetric)
	{
	    metric = (AbstractStringMetric) obj;
	} else
	    throw new ClassCastException();

	return metric;
    }

    /* (non-Java-doc)
     * @see javax.servlet.http.HttpServlet#HttpServlet()
     */
    public SearchSimilarServlet()
    {
	super();
    }
    
    /**
     * @param username
     * @param metricname
     * @return
     */
    public String processRequest(String username,String metricname)
    {
	
	L4AllUserManager usrMgr = new L4AllUserManager();
	Message idMsg = usrMgr.getUserIdentificationDetails(username);
	
	if (idMsg.getErrorCode() != ErrorCodes.SUCCESS) 
	{
	    return MessageBuilder.createErrorMessage("searchSimilar", idMsg.getErrorCode(), idMsg
						.getErrorDescription());
	}

	AbstractStringMetric metric = null;
	try
	{
	    metric = getSimilarityMetric(metricname);
	} 
	catch (Exception e)
	{
	    if (metricname==null)
		return MessageBuilder.createErrorMessage("searchSimilar", 
		    ErrorCodes.EMPTY_FIELD_ERROR,
		    "The metric is not specified");
	    else
		return MessageBuilder.createErrorMessage("searchSimilar", 
			    ErrorCodes.EMPTY_FIELD_ERROR,
			    "The metric does not exist");

	}	
	
	String res = null;
	Message msg = trailMgr.getUserTrailDetails(username, username);
	Trail trailuser = (Trail) msg.getResultObject();
	
	String usertl = getFormattedTimeline(trailuser,true);

	// Call the search for people function from the backend
	L4AllUser[] l4allUsers = L4AllUsersSearch.getAllUsers();
	List list = new ArrayList();
	for (int i = 0; i < l4allUsers.length; i++)
	{
	    L4AllUser user = l4allUsers[i];
	    //if (user.getUsername().equals(username))
		//continue;
	    if (Trails.TYPE_TEMPLATE.equals(user.getStatus())) continue;
	    
	    msg = trailMgr.getUserTrailDetails(user.getUsername(), user.getUsername());
	    Trail trailtl = (Trail) msg.getResultObject();
	    int priv =trailtl.getTrailPrivileges(); 
	    if (priv!=1) continue;
	    try
	    {
		String tl = getFormattedTimeline(trailtl,true);
		user.setSimValue(metric.getSimilarity(usertl, tl));
		user.setSimCoding(tl);
		list.add(user);
	    }
	    catch (Exception e)
	    {
	    }
	    
	}
	


	for (int i = 0; i < list.size(); i++)
	{
	    L4AllUser user = (L4AllUser) list.get(i);

	}

	Collections.sort(list, new SimilarUserComparator());

	res = MessageBuilder.createSuccessMessageHeader("searchSimilar") + newline;
	res += "<users cardinality='" + list.size() + "'>" + newline;
	for (int i = 0; i < list.size(); i++)
	{
	    L4AllUser user = (L4AllUser) list.get(i);
	    res += "<user>" + newline;
	    res += "<status>" + user.getStatus() + "</status>"+ newline;
	    res += "<username>" + user.getUsername() + "</username>"+ newline;
	    res += "<name><![CDATA[" + user.getName() + "]]></name>"+ newline;
	    
	    DecimalFormat formatter = new DecimalFormat("0.00");
	    String ff = formatter.format(user.getSimValue());
	    
	    res += "<coding>" + user.getSimCoding() + "</coding>"+ newline;
	    res += "<relevance>" + ff + "</relevance>"+ newline;
	    res += "</user>"+ newline;
	}
	res += "</users>"+ newline;
	res += MessageBuilder.createSuccessMessageTail() + newline;
	return res;
    }

    /* (non-Javadoc)
     * @see uk.ac.bbk.dcs.l4all.servlets.L4ALLServiceServlet#processRequest(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
	String username = request.getParameter("username");
	String metricname = request.getParameter("metric");

	response.setContentType("text/xml");
	response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1
	response.setHeader("Pragma", "no-cache"); //HTTP 1.0
	PrintWriter out = null;

	out = response.getWriter();
	String res =processRequest(username, metricname); 
	out.print(res);    }

}