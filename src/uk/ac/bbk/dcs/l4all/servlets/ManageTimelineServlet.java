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

import java.io.*;
import java.util.Hashtable;
import java.util.Map;

import javax.servlet.*;
import javax.servlet.http.*;

import uk.ac.bbk.dcs.l4all.beans.ErrorCodes;
import uk.ac.bbk.dcs.l4all.beans.Message;
import uk.ac.bbk.dcs.l4all.beans.UserTrailManager;
import uk.ac.bbk.dcs.l4all.util.MessageBuilder;
import uk.ac.bbk.dcs.l4all.vocabulary.Trails;

/**
 * 
 * @author George
 * @version
 */
public class ManageTimelineServlet extends L4ALLServiceServlet
{
    public static final String ACTION_EDT = "edit";

    public static final String ACTION_DEL = "delete";

    public static final String ACTION_NEW = "new";

    /**
     * @param action
     * @param username
     * @param params
     * @return
     */
    public String processRequest(String action, String username,
	    Hashtable params)
    {
	String res = null;

	String timelineName = getParam(params, "name");
	String timelineDescription = getParam(params, "description");
	String timelinePrivileges = getParam(params, "privileges");
	String timelineKeywords = getParam(params, "keywords");
	String timelineStart = getParam(params, "start");
	String timelineEnd = getParam(params, "end");
	String timelineType = getParam(params, "type");

	UserTrailManager userTrails = new UserTrailManager();

	// check for necessary parameters: action/username/timelinename
	if (action == null || action.isEmpty())
	{
	    res = MessageBuilder
		    .createErrorMessage("manageTimeline",
			    ErrorCodes.EMPTY_FIELD_ERROR,
			    "The 'action' parameter is not defined. Timeline management denied.");
	    return res;
	}
	if (username == null || username.isEmpty())
	{
	    res = MessageBuilder
		    .createErrorMessage("manageTimeline",
			    ErrorCodes.EMPTY_FIELD_ERROR,
			    "Invalid 'username' parameter. Timeline management denied.");
	    return res;
	}
	if (timelineName == null || timelineName.isEmpty())
	{
	    res = MessageBuilder.createErrorMessage("manageTimeline",
		    ErrorCodes.EMPTY_FIELD_ERROR,
		    "No timeline name specified. Timeline management denied.");
	    return res;
	}

	Message msg = null;

	if (ACTION_DEL.equals(action))
	{
	    // if delete, proceed
	    msg = userTrails.deleteTrail(username, timelineName);
	} else
	{
	    // if edit or create, check for more requirements
	    if (timelineType == null || timelineType.isEmpty())
	    {
		res = MessageBuilder.createErrorMessage("manageTimeline",
			ErrorCodes.EMPTY_FIELD_ERROR,
			"No timeline type specified. Timeline management denied.");
		return res;
	    }

	    if (timelinePrivileges == null)
	    {
		res = MessageBuilder.createErrorMessage("manageTimeline",
			ErrorCodes.EMPTY_FIELD_ERROR,
			"No timeline privileges specified");
		return res;
	    }
	    int timelinePrivilegesNumber = 0;
	    try
	    {
		timelinePrivilegesNumber = Integer.parseInt(timelinePrivileges);
	    } catch (NumberFormatException nfe)
	    {
		String err = nfe.getLocalizedMessage();
		res = MessageBuilder.createErrorMessage("manageTimeline",
			ErrorCodes.INVALID_FIELD_FORMAT, err);
		return res;
	    }
	    
	    if (timelineDescription == null || timelineDescription.isEmpty())
		timelineDescription = "";
//	    if (timelineType == null)
//		timelineType = Trails.TYPE_USER;
	    if (timelineStart == null  || timelineStart.isEmpty())
		timelineStart = "1960";
	    if (timelineEnd == null || timelineEnd.isEmpty())
		timelineEnd = "2060";
	    if (timelineKeywords == null || timelineKeywords.isEmpty())
		timelineKeywords = "";

	    int tStart = Integer.parseInt(timelineStart);
	    int tEnd = Integer.parseInt(timelineEnd);

	    if (ACTION_NEW.equals(action))
	    {
		// add the new timeline to the user model 
		msg = userTrails.addNewTrail(username, timelineName,
			timelineDescription, timelineType,
			timelinePrivilegesNumber, "", timelineKeywords, tStart,
			tEnd);
	    } 
	    else if (ACTION_EDT.equals(action))
	    {
		// modify the timeline in the user model 
		msg = userTrails.editTrail(username, timelineName,
			timelineDescription, timelineType,
			timelinePrivilegesNumber, "", timelineKeywords, tStart,
			tEnd);
	    } 
	    else
	    {
		// the action is not recognised
		res = MessageBuilder.createErrorMessage("ManageTimeline",
			ErrorCodes.INVALID_FIELD_FORMAT,
			"The action '" + action + 
			"' is not recognised. Timeline management denied.");
		return res;
	    }
	}
	
	// check the outcome of the action
	if (msg.getErrorCode() == ErrorCodes.SUCCESS)
	{
	    res = MessageBuilder.createSuccessMessageHeader("manageTimeline");
	    res += "<timeline_id>" + timelineName + "</timeline_id>\n";
	    res += MessageBuilder.createSuccessMessageTail();
	} 
	else
	{
	    res = MessageBuilder.createErrorMessage("manageTimeline", msg
		    .getErrorCode(), msg.getErrorDescription());
	}
	return res;
    }





    /**
     * Returns a short description of the servlet.
     */
    public String getServletInfo()
    {
	return "Short description";
    }
    // </editor-fold>


    /* (non-Javadoc)
     * @see uk.ac.bbk.dcs.l4all.servlets.L4ALLServiceServlet#processRequest(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
	response.setContentType("text/xml");
	response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1
	response.setHeader("Pragma", "no-cache"); //HTTP 1.0

	PrintWriter out = null;
	try
	{
	    out = response.getWriter();
	} catch (IOException e)
	{
	    e.printStackTrace();
	    return;
	}

	String username = request.getParameter("username");
	String action = request.getParameter("action");
	Hashtable map = new Hashtable(request.getParameterMap());
	String res = processRequest(action, username, map);
	out.println(res);
	//out.close();	
    }
}
