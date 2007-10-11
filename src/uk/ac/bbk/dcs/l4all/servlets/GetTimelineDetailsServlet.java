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

import uk.ac.bbk.dcs.l4all.beans.ErrorCodes;
import uk.ac.bbk.dcs.l4all.beans.Message;
import uk.ac.bbk.dcs.l4all.beans.Trail;
import uk.ac.bbk.dcs.l4all.beans.TrailEpisode;
import uk.ac.bbk.dcs.l4all.beans.UserTrailManager;
import uk.ac.bbk.dcs.l4all.util.MessageBuilder;
import uk.ac.bbk.dcs.l4all.vocabulary.Episode;

/**
 * 
 * @author George
 * @version
 */
public class GetTimelineDetailsServlet extends HttpServlet
{

    private String getSecureString(String str)
    {
	String d = str.replaceAll("'", "&#39;");
	return d;
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     * 
     * @param request
     *            servlet request
     * @param response
     *            servlet response
     */
    protected void processRequest(HttpServletRequest request,
	    HttpServletResponse response)
    {
	//		String tlName = request.getParameter("tl_name");
	String username = request.getParameter("username");
	String fmt = request.getParameter("format");
	String edit = request.getParameter("edit");

	try
	{
	    PrintWriter out = null;

	    UserTrailManager usrTrailMgr = new UserTrailManager();
	    //			Message msg = usrTrailMgr.getUserTrailDetails(username, tlName);
	    Message msg = usrTrailMgr.getUserTrailDetails(username, username);
	    out = response.getWriter();

	    if (msg.getErrorCode() != ErrorCodes.SUCCESS)
	    {
		out.println(MessageBuilder.createErrorMessage("getTimeline",
			msg.getErrorCode(), 
			msg.getErrorDescription()));
		out.close();
		return;
	    }

	    if (fmt != null && fmt.equals("JSON"))
	    {
		if (edit == null) edit = "true";
		
		Trail trl = (Trail) msg.getResultObject();

		response.setContentType("text/xml");
		response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1
		response.setHeader("Pragma", "no-cache"); //HTTP 1.0
		out.println("<data ");
		out.println("	description='"+trl.getTrailDescription()+"'");
		//out.println("	'dateTimeFormat': 'iso8601',");
		//out.println("	'wikiURL': 'http://simile.mit.edu/shelf/',");
		//out.println("	'wikiSection': 'Simile Cubism Timeline',");
		out.println(">");
		TrailEpisode[] tEpisodes = trl.getTrailEpisodes();
		for (int l = 0; l < tEpisodes.length; l++)
		{
		    String category = tEpisodes[l].getCategory();
		    String image = null;
		    String icon = null;
		    if (category == null || category.isEmpty())
			image = "images/other.png";
		    else
		    {
			image = "images/" + tEpisodes[l].getCategory() + ".png";
			icon = "images/" + tEpisodes[l].getCategory()
				+ "-icon.png";
		    }
		    out.println("<event ");
		    out.println("	isDuration='false'");
		    out.println("	isEditable='" + edit + "'");
		    out.println("	start='" + tEpisodes[l].getStart() + "'");
		    out.println("	end='" + tEpisodes[l].getEnd() + "'");
		    //out.println("	end='"+tEpisodes[l].getEnd()+"'");
		    out.println("	position='" + l + "'");
		    out.println("   title='" + getSecureString(tEpisodes[l].getTitle()) + "'");
			    //+ " [" + tEpisodes[l].getCategory() + "]'");
		    if (image != null)
			out.println("   image='" + image + "'");
		    if (icon != null)
			out.println("   icon='" + icon + "'");
		    if (Episode.NATURE_WISH.equals(tEpisodes[l].getNature()))
			out.println("   color='#DB9957'");
		    out.println("   link='" + tEpisodes[l].getUrl()+ "'");
		    out.println("   >");
		    out.println(tEpisodes[l].getDescription());
		    out.println("</event>");
		}
		out.println("</data>");

	    } 
	    else 
	    {
		out = response.getWriter();

		response.setContentType("text/xml");
		response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1
		response.setHeader("Pragma", "no-cache"); //HTTP 1.0

		Trail trl = (Trail) msg.getResultObject();
		out.println(MessageBuilder.createSuccessMessageHeader("getTimeline"));
		out.println("<tl>");
		out.println("<tl_id>" + trl.getTrailID() + "</tl_id>");
		out.println("<tl_title>" + trl.getTrailName() + "</tl_title>");
		out.println("<tl_keywords><![CDATA[" + trl.getTrailKeywords()
			+ "]]></tl_keywords>");
		out.println("<tl_duration>");
		out.println("<tl_start>" + trl.getStart() + "</tl_start>");
		out.println("<tl_end>" + trl.getEnd() + "</tl_end>");
		out.println("</tl_duration>");
		TrailEpisode[] tEpisodes = trl.getTrailEpisodes();
		out.println("<tl_episodes cardinality='" + tEpisodes.length
			+ "'>");
		for (int l = 0; l < tEpisodes.length; l++)
		{
		    out.println("<tl_episode order='" + (l + 1) + "'>");
		    out.println("<title>" + tEpisodes[l].getTitle()
			    + "</title>");
		    out.println("<URL>" + tEpisodes[l].getUrl() + "</URL>");
		    out.println("<description><![CDATA["
			    + tEpisodes[l].getDescription()
			    + "]]></description>");
		    out.println("<category>" + tEpisodes[l].getCategory()
			    + "</category>");
		    out.println("<start>" + tEpisodes[l].getStart()
			    + "</start>");
		    out.println("<end>" + tEpisodes[l].getEnd() + "</end>");
		    out.println("</tl_episode>");
		}
		out.println("</tl_episodes>");
		out.println("</tl>");
		out.println(MessageBuilder.createSuccessMessageTail());
	    } 
	    out.close();

	} 
	catch (IOException e)
	{
	    e.printStackTrace();
	    //			String msg = e.getLocalizedMessage();
	    //			out.println(MessageBuilder.createErrorMessage("getTimeline",
	    //					ErrorCodes.INVALID_FIELD_FORMAT, msg));
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

    /**
     * Returns a short description of the servlet.
     */
    public String getServletInfo()
    {
	return "Short description";
    }
}
