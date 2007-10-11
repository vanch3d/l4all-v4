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

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hp.hpl.jena.shared.PropertyNotFoundException;

import uk.ac.bbk.dcs.l4all.beans.ErrorCodes;
import uk.ac.bbk.dcs.l4all.beans.Message;
import uk.ac.bbk.dcs.l4all.beans.UserTrailManager;
import uk.ac.bbk.dcs.l4all.util.MessageBuilder;

public class RemoveEpisodeFromTrailServlet extends HttpServlet
{
    private void processRequest(HttpServletRequest request,
	    HttpServletResponse response)
    {
	response.setContentType("text/xml");
	response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1
	response.setHeader("Pragma", "no-cache"); //HTTP 1.0
	PrintWriter out = null;
	try
	{
	    out = response.getWriter();
	    String timelineName = request.getParameter("timelineName");
	    String episodeOrder = request.getParameter("position");
	    String username = request.getParameter("username");

	    if (timelineName == null || timelineName.trim().equals(""))
	    {
		out.println(MessageBuilder.createErrorMessage("removeEpisode",
			ErrorCodes.EMPTY_FIELD_ERROR,
			"No timeline name was specified"));
		return;
	    }

	    if (episodeOrder == null || episodeOrder.trim().equals(""))
	    {
		out.println(MessageBuilder.createErrorMessage("removeEpisode",
			ErrorCodes.EMPTY_FIELD_ERROR,
			"The position of the episode within the timeline was not specified"));
		return;
	    }

	    int eOrder = Integer.parseInt(episodeOrder);

	    if (username == null || username.trim().equals(""))
	    {
		out.println(MessageBuilder.createErrorMessage("removeEpisode",
			ErrorCodes.EMPTY_FIELD_ERROR,
			"No username was specified"));
		return;
	    }

	    UserTrailManager mgr = new UserTrailManager();
	    Message msg = mgr.deleteEpisodeFromTrail(username, timelineName,eOrder);

	    if (msg.getErrorCode() == ErrorCodes.SUCCESS)
	    {
		out.println(MessageBuilder.createSuccessMessageHeader("removeEpisode"));
		out.println("<tl_name>" + timelineName + "</tl_name>");
		out.println(MessageBuilder.createSuccessMessageTail());
		out.close();
		return;
	    }
	    else
	    {
		out.println(MessageBuilder.createErrorMessage("removeEpisode",
			msg.getErrorCode(), msg.getErrorDescription()));
		out.close();

		return;
	    }

	} catch (IOException e)
	{
	    String msg = e.getLocalizedMessage();
	    out.println(MessageBuilder.createErrorMessage("removeEpisode",
		    ErrorCodes.INVALID_FIELD_FORMAT, msg));
	    return;
	} catch (NumberFormatException nfe)
	{
	    String msg = nfe.getLocalizedMessage();
	    out.println(MessageBuilder.createErrorMessage("removeEpisode",
		    ErrorCodes.INVALID_FIELD_FORMAT, msg));
	    return;
	}
	catch (PropertyNotFoundException pnfe)
	{
	    String msg = pnfe.getLocalizedMessage();
	    out.println(MessageBuilder.createErrorMessage("removeEpisode",
		    ErrorCodes.INVALID_FIELD_FORMAT, "Cannot find the episode"));
	    return;
	    
	}
    }

    /**
     * Handles the HTTP <code>GET</code> method.
     * 
     * @param request
     *            servlet request
     * @param response
     *            servlet response
     */
    protected void doGet(HttpServletRequest request,
	    HttpServletResponse response) throws ServletException, IOException
    {
	processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     * 
     * @param request
     *            servlet request
     * @param response
     *            servlet response
     */
    protected void doPost(HttpServletRequest request,
	    HttpServletResponse response) throws ServletException, IOException
    {
	processRequest(request, response);
    }
}
