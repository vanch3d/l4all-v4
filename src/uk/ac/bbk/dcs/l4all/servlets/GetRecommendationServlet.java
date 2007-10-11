/*
 * Copyright (C) 2005 Birkbeck College University of London
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package uk.ac.bbk.dcs.l4all.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import uk.ac.bbk.dcs.l4all.beans.ErrorCodes;
import uk.ac.bbk.dcs.l4all.beans.L4AllUser;
import uk.ac.bbk.dcs.l4all.beans.L4AllUsersSearch;
import uk.ac.bbk.dcs.l4all.beans.Message;
import uk.ac.bbk.dcs.l4all.beans.Trail;
import uk.ac.bbk.dcs.l4all.beans.TrailEpisode;
import uk.ac.bbk.dcs.l4all.util.MessageBuilder;
import uk.ac.bbk.dcs.l4all.vocabulary.Trails;
import uk.ac.shef.wit.simmetrics.similaritymetrics.AbstractStringMetric;

/**
 *
 * @author George
 * @version
 */
public class GetRecommendationServlet extends SearchSimilarServlet
{
    public String processRequest(String username, Hashtable params)
    {
	String res = null;
	String occupation = getParam(params, "occupation");
	String searh = getParam(params, "search");

	boolean isStrong = ("strong".equals(searh));
	
	L4AllUser[] list = L4AllUsersSearch.getAllUsers();
	List templates = new ArrayList();
	for (int i = 0; i < list.length; i++)
	{
	    L4AllUser user = list[i];
	    if (user == null)
		continue;
	    //if (!Trails.TYPE_TEMPLATE.equals(user.getStatus()))
		//continue;
	    //templates.add(user);
	    Message msg = trailMgr.getAllTrails(user.getUsername());
	    ArrayList trails = (ArrayList)msg.getResultObject();
	    for (int j = 0; j < trails.size(); j++)
	    {
		String trailname = (String)trails.get(j);
		Message msgTrail = trailMgr.getUserTrailDetails(user.getUsername(),trailname);
		Trail trail = (Trail)msgTrail.getResultObject();	
		if (trail.getTrailPrivileges()!=1) continue;
		if (!Trails.TYPE_TEMPLATE.equals(trail.getTrailType())) continue;
		templates.add(trail);
	    }
	}
	
	AbstractStringMetric metric = null;
	try
	{
	    metric = getSimilarityMetric("OverlapCoefficient");
	} 
	catch (Exception e)
	{
            return MessageBuilder.createErrorMessage("searchRecommendations", 
        	    	ErrorCodes.INVALID_FIELD_FORMAT,
            		"The metric cannot be loaded");
	}	
	int a = (int)'A';
	int zero = (int)'0';
	res = MessageBuilder.createSuccessMessageHeader("searchRecommendations") + newline;
	res +=("<advices cardinality='"+templates.size()+"'>");

	Message msg = trailMgr.getUserTrailDetails(username, username);
	Trail trailuser = (Trail) msg.getResultObject();

	String usertl = getFormattedTimeline(trailuser,isStrong);
	DecimalFormat formatter = new DecimalFormat("0.00");
	res +=("<target>" + usertl + "</target>");

	for (int i = 0; i < templates.size(); i++)
	{
	    Trail trail = (Trail) templates.get(i);

	    try
	    {
		msg = trailMgr.getUserTrailDetails(trail.getTrailOwner(),trail.getTrailName());
		Trail trailrecom = (Trail) msg.getResultObject();
		String tl = getFormattedTimeline(trailrecom,isStrong);
		float sim = metric.getSimilarity(usertl, tl);
		String ff = formatter.format(sim);
		System.out.println(tl);

		res +=("<advice score='"+ff+"'>");
		res +=("<advice_id>" + trail.getTrailID() + "</advice_id>");
		res +=("<advice_title>" + trail.getTrailName() + "</advice_title>");
		res +=("<advice_owner>" + trail.getTrailOwner() + "</advice_owner>");
		res +=("<advice_format><![CDATA[" + tl + "]]></advice_format>");
		res +=("</advice>");
		
	    }
	    catch (Exception e)
	    {
	            return MessageBuilder.createErrorMessage("searchRecommendations", 
	        	    	ErrorCodes.INVALID_FIELD_FORMAT,
	            		"Data corruption");
	    }
	}
	res+=("</advices>");
	res+=(MessageBuilder.createSuccessMessageTail());
	

	return res;
    }

    /* (non-Javadoc)
     * @see uk.ac.bbk.dcs.l4all.servlets.SearchSimilarServlet#processRequest(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    protected void processRequest(HttpServletRequest request,
	    HttpServletResponse response) throws IOException
    {
	response.setContentType("text/xml");
	response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1
	response.setHeader("Pragma", "no-cache"); //HTTP 1.0
	PrintWriter out = null;

	HttpSession session = request.getSession();
	String username = (String) session.getAttribute(ServletUtilities.SESSION_USERNAME);
	
	out = response.getWriter();
	String res = processRequest(username, new Hashtable());
	out.print(res);

    }

}
