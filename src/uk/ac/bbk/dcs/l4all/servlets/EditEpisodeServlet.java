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
import javax.servlet.http.HttpSession;

import uk.ac.bbk.dcs.l4all.beans.ErrorCodes;
import uk.ac.bbk.dcs.l4all.beans.Message;
import uk.ac.bbk.dcs.l4all.beans.UserTrailManager;
import uk.ac.bbk.dcs.l4all.util.MessageBuilder;

public class EditEpisodeServlet extends HttpServlet {

	private void processRequest(HttpServletRequest request,
			HttpServletResponse response) {
		response.setContentType("text/xml");
		response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
		response.setHeader("Pragma","no-cache"); //HTTP 1.0
		PrintWriter out = null;
		try {
			out = response.getWriter();
			
			HttpSession session = request.getSession();
			String username = (String)session.getAttribute(ServletUtilities.SESSION_USERNAME);
			
			String timelineName = request.getParameter("timelineName");
			String eTitle = request.getParameter("title");
			String eCategory = request.getParameter("category");
			String eStart = request.getParameter("start");
			String eEnd = request.getParameter("end");
			String eURL = request.getParameter("url");
			String eDesc = request.getParameter("desc");
			String ePosition = request.getParameter("position");
			String enature = request.getParameter("nature");
			String prim = request.getParameter("prm_class");
			String sec = request.getParameter("sec_class");
			
			
			if (ePosition == null || ePosition.trim().equals("")) {
				out.println(MessageBuilder.createErrorMessage("editEpisode",
						ErrorCodes.EMPTY_FIELD_ERROR,
						"No episode position specified"));
				return;
			}
				
			int position = Integer.parseInt(ePosition);

			if (eDesc == null || eDesc.trim().equals(""))
				eDesc = "";
			
			if (prim == null || prim.trim().equals(""))
			    prim = "000";
			if (sec == null || sec.trim().equals(""))
			    sec = "000";

			if (username.trim().equals("") || username == null) {
				out.println(MessageBuilder.createErrorMessage("addEpisode",
						ErrorCodes.EMPTY_FIELD_ERROR,
						"No username was specified"));
				return;
			}
			
			UserTrailManager trlMgr = new UserTrailManager();
			Message msg = trlMgr.editEpisode(username,timelineName,
				eTitle,eCategory,enature,eStart,eEnd,eURL,eDesc,position,
				prim,sec);
			
			if (msg.getErrorCode() == ErrorCodes.SUCCESS) {
//				out.println(MessageBuilder.createSuccessMessageHeader("editEpisode"));
//				out.println("<episode>" +  eTitle + "</episode>");
//				out.println(MessageBuilder.createSuccessMessageTail());
				response.setContentType("text/html");
				out.println("<script type=\"text/javascript\">");
				out.println("function SaveSetting(){");
				out.println("	if (window.opener && !window.opener.closed)");
				out.println("	window.opener.updateTimeline();");
				out.println("	self.close();");
				out.println("}");
				out.println("");
				out.println("</script>");
				out.println("<body bgcolor=\"#BABA96\" onload='SaveSetting();'>");
				out.println("<h2>You have registered successfully.</h2>\n");
				out.println("</body>");

				out.close();
				return ;
			} else {
				out.println(MessageBuilder.createErrorMessage("editEpisode",msg.getErrorCode(),msg.getErrorDescription()));
			}

		} catch (IOException e) {
			String msg = e.getLocalizedMessage();
			out.println(MessageBuilder.createErrorMessage(
					"editEpisode", ErrorCodes.INVALID_FIELD_FORMAT,
					msg));
			return;
		} catch (NumberFormatException nfe) {
			String msg = nfe.getLocalizedMessage();
			out.println(MessageBuilder.createErrorMessage(
					"editEpisode", ErrorCodes.INVALID_FIELD_FORMAT,
					msg));
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
			HttpServletResponse response) throws ServletException, IOException {
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
			HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}
}
