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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import uk.ac.bbk.dcs.l4all.beans.ErrorCodes;
import uk.ac.bbk.dcs.l4all.beans.Message;
import uk.ac.bbk.dcs.l4all.beans.UserTrailManager;
import uk.ac.bbk.dcs.l4all.util.MessageBuilder;

/**
 * This is a simple description
 * This is a long description
 * 
 * @author Nicolas Van Labeke (nicolas@dcs.bbk.ac.uk)
 * @version 1.4
 */
public class AddNewEpisodeServlet extends L4ALLServiceServlet
{
    private static final long serialVersionUID = 4249897652666377090L;

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

	    String timelineName = request.getParameter("timelineName");
	    String eTitle = request.getParameter("title");
	    String eCategory = request.getParameter("category");
	    String eStart = request.getParameter("start");
	    String eEnd = request.getParameter("end");
	    String eURL = request.getParameter("url");
	    String eDesc = request.getParameter("desc");
	    String ePosition = request.getParameter("position");
	    String username = request.getParameter("username");
	    String enature = request.getParameter("nature");
	    String eprim = request.getParameter("prm_class");
	    String esec = request.getParameter("sec_class");

	    if (timelineName == null || timelineName.trim().equals(""))
	    {
		out.println("<?xml-stylesheet type=\"text/xsl\" href=\"xsl/error.xsl\"?>");
		out.println(MessageBuilder.createErrorMessage("addEpisode",
			ErrorCodes.EMPTY_FIELD_ERROR,
			"No timeline name was specified"));
		return;
	    }

	    if (eTitle == null || eTitle.trim().equals(""))
	    {
		out.println("<?xml-stylesheet type=\"text/xsl\" href=\"xsl/error.xsl\"?>");
		out.println(MessageBuilder.createErrorMessage("addEpisode",
			ErrorCodes.EMPTY_FIELD_ERROR,
			"No episode title was specified"));
		return;
	    }

	    if (eStart == null || eStart.trim().equals(""))
	    {
		out.println("<?xml-stylesheet type=\"text/xsl\" href=\"xsl/error.xsl\"?>");
		out.println(MessageBuilder.createErrorMessage("addEpisode",
			ErrorCodes.EMPTY_FIELD_ERROR,
			"No episode start date was specified"));
		return;
	    }

	    if (eEnd == null || eEnd.trim().equals(""))
	    {
//		out.println("<?xml-stylesheet type=\"text/xsl\" href=\"xsl/error.xsl\"?>");
//		out.println(MessageBuilder.createErrorMessage("addEpisode",
//			ErrorCodes.EMPTY_FIELD_ERROR,
//			"No episode end date was specified"));
//		return;
		eEnd = eStart;
	    }

	    if (ePosition == null || ePosition.trim().equals(""))
	    {
		out.println("<?xml-stylesheet type=\"text/xsl\" href=\"xsl/error.xsl\"?>");
		out.println(MessageBuilder.createErrorMessage("addEpisode",
			ErrorCodes.EMPTY_FIELD_ERROR,
			"No episode order on timeline was specified"));
		return;
	    }

	    int position = Integer.parseInt(ePosition);

	    if (eDesc == null || eDesc.trim().equals(""))
		eDesc = "";

	    if (username == null || username.trim().equals(""))
	    {
		out.println("<?xml-stylesheet type=\"text/xsl\" href=\"xsl/error.xsl\"?>");
		out.println(MessageBuilder.createErrorMessage("addEpisode",
			ErrorCodes.EMPTY_FIELD_ERROR,
			"No username was specified"));
		return;
	    }

	    UserTrailManager trlMgr = new UserTrailManager();
	    Message msg = trlMgr.addEpisodeToTrail(username, timelineName,
		    eTitle, eCategory, enature, eStart, eEnd, eURL, eDesc, position,
		    eprim,esec);

	    if (msg.getErrorCode() == ErrorCodes.SUCCESS)
	    {
		out.println("<?xml-stylesheet type=\"text/xsl\" href=\"xsl/success.xsl\"?>");
		out.println(MessageBuilder.createSuccessMessageHeader("addEpisode"));
		out.println("<episode>" + eTitle + "</episode>");
		out.println(MessageBuilder.createSuccessMessageTail());
		out.close();
		return;
	    } else
	    {
		out.println("<?xml-stylesheet type=\"text/xsl\" href=\"xsl/error.xsl\"?>");
		out.println(MessageBuilder.createErrorMessage("addEpisode", 
			msg.getErrorCode(), msg.getErrorDescription()));
	    }

	} catch (IOException e)
	{
	    String msg = e.getLocalizedMessage();
	    out.println(MessageBuilder.createErrorMessage("addEpisode",
		    ErrorCodes.INVALID_FIELD_FORMAT, msg));
	    return;
	} catch (NumberFormatException nfe)
	{
	    String msg = nfe.getLocalizedMessage();
	    out.println(MessageBuilder.createErrorMessage("addEpisode",
		    ErrorCodes.INVALID_FIELD_FORMAT, msg));
	    return;
	}
    }
}
