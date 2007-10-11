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
import java.util.HashSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import uk.ac.bbk.dcs.l4all.beans.ErrorCodes;
import uk.ac.bbk.dcs.l4all.beans.L4AllTrailSearch;
import uk.ac.bbk.dcs.l4all.beans.Trail;
import uk.ac.bbk.dcs.l4all.beans.TrailEpisode;
import uk.ac.bbk.dcs.l4all.util.MessageBuilder;

/**
 * @author George Papamarkos
 * 
 * SearchTrailsServlet.java uk.ac.bbk.dcs.l4all.servlets
 */
public class SearchTimelinesServlet extends HttpServlet {
	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		boolean myTrails = false;
		boolean otherTrails = false;

		// HttpSession session = request.getSession(false);

		response.setContentType("text/xml");
		response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
		response.setHeader("Pragma","no-cache"); //HTTP 1.0
		PrintWriter out = null;
		try {
			out = response.getWriter();

			ServletContext context = getServletContext();
			RequestDispatcher rd = null;

			String episodeTitle = request.getParameter("episode_title");
			// String minYear = request.getParameter("minYear");
			// String maxYear = request.getParameter("maxYear");
			String keywords = request.getParameter("keywords");
			// String searchSet = request.getParameter("searchSet");
			String username = request.getParameter("username");
			// String username = (String)session.getAttribute("username");

			if (keywords == null) {
				out.println(MessageBuilder.createErrorMessage(
						"searchTimelines", ErrorCodes.EMPTY_FIELD_ERROR,
						"No search keyword specified!"));
				return;
			}

			String[] search_keywords = null;
			if (keywords.contains("+"))
				search_keywords = keywords.split("+");
			else
				search_keywords = new String[] { keywords };

			// if ("0".equals(searchSet)) {
			// myTrails = true;
			// otherTrails = false;
			// }
			//
			// if ("1".equals(searchSet)) {
			// otherTrails = true;
			// myTrails = false;
			// }
			//
			// if ("2".equals(searchSet)) {
			// otherTrails = true;
			// myTrails = true;
			// }
			//
			// if (!myTrails && !otherTrails) {
			// out.println(MessageBuilder.createErrorMessage("searchTimelines",
			// ErrorCodes.INVALID_SEARCH_SET,
			// "No timeline search set was defined"));
			// return;
			// }

			HashSet hs = new HashSet();
			for (int i = 0; i < search_keywords.length; i++) {
				Trail[] trails = L4AllTrailSearch.searchForTrails(
						search_keywords[i], episodeTitle, false, false,
						username);

				for (int j = 0; j < trails.length; j++)
					hs.add(trails[j]);
			}

			Trail[] rs = (Trail[]) hs.toArray(new Trail[hs.size()]);

			if (rs.length > 0) {
				out.println(MessageBuilder
						.createSuccessMessageHeader("searchTimelines"));
				for (int k = 0; k < rs.length; k++) {
					out.println("<tl>");
					out.println("<tl_id>" + rs[k].getTrailID() + "</tl_id>");
					out.println("<tl_title>" + rs[k].getTrailName()
							+ "</tl_title>");
					out.println("<tl_keywords><![CDATA["
							+ rs[k].getTrailKeywords() + "]]></tl_keywords>");
					out.println("<tl_duration>");
					out
							.println("<tl_start>" + rs[k].getStart()
									+ "</tl_start>");
					out.println("<tl_end>" + rs[k].getEnd() + "</tl_end>");
					out.println("</tl_duration>");
					TrailEpisode[] tEpisodes = rs[k].getTrailEpisodes();
					out.println("<tl_episodes cardinality='" + tEpisodes.length
							+ "'>");
					for (int l = 0; l < tEpisodes.length; l++) {
						out.println("<tl_episode order='" + (l+1) + "'>");
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
				}
				out.println(MessageBuilder.createSuccessMessageTail());
			} else
				out.println(MessageBuilder.createErrorMessage(
						"searchTimelines", ErrorCodes.EMPTY_RESULT_SET,
						"No results found"));

			out.close();
		} catch (IOException e) {
			String msg = e.getLocalizedMessage();
			out.println(MessageBuilder.createErrorMessage("searchTimelines",
					ErrorCodes.INVALID_FIELD_FORMAT, msg));
			return;
		} catch (NumberFormatException nfe) {
			String msg = nfe.getLocalizedMessage();
			out.println(MessageBuilder.createErrorMessage("searchTimelines",
					ErrorCodes.INVALID_FIELD_FORMAT, msg));
			return;
		}
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);

	}
}
