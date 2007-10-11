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
package uk.ac.bbk.dcs.l4all.beans;

import uk.ac.bbk.dcs.l4all.vocabulary.Trails;

/**
 * @author George Papamarkos
 *
 * Trail.java
 * uk.ac.bbk.dcs.l4all.beans
 */
public class Trail {
	
    	private String trailType = "";
	private String trailName = "";
	private String trailDescription = "";
	private String trailAnnotation = "";
	private String trailOwner = "";
	private int trailPrivileges = 0;
	private String trailKeywords = "";
	private String start = "undefined" ;
	private String end  = "undefined" ;
	private long trailID = 0 ;
	private TrailEpisode [] trailEpisodes = null ;
	
	///////// GETTERS AND SETTERS /////////
	
	public String getTrailAnnotation() {
		return trailAnnotation;
	}
	
	public void setTrailAnnotation(String trailAnnotation) {
		this.trailAnnotation = trailAnnotation;
	}
	
	public TrailEpisode [] getTrailEpisodes() {
		return trailEpisodes;
	}
	
	public void setTrailEpisodes(TrailEpisode [] trailContents) {
		this.trailEpisodes = trailContents;
	}
	
	public String getTrailDescription() {
		return trailDescription;
	}
	
	public void setTrailDescription(String trailDescription) {
		this.trailDescription = trailDescription;
	}
	
	public String getTrailKeywords() {
		return trailKeywords;
	}
	
	public void setTrailKeywords(String trailKeywords) {
		this.trailKeywords = trailKeywords;
	}
	
	public String getTrailName() {
		return trailName;
	}
	
	public void setTrailName(String trailName) {
		this.trailName = trailName;
	}
	
	public String getTrailOwner() {
		return trailOwner;
	}
	
	public void setTrailOwner(String trailOwner) {
		this.trailOwner = trailOwner;
	}
	
	public int getTrailPrivileges() {
		return trailPrivileges;
	}
	
	public void setTrailPrivileges(int trailPrivileges) {
		this.trailPrivileges = trailPrivileges;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public long getTrailID() {
		return trailID;
	}

	public void setTrailID(long trailID) {
		this.trailID = trailID;
	}

	public String getTrailType()
	{
	    return trailType;
	}

	/**
	 * Set the type of the current trail.
	 * @see Trails#TYPE_EXPERT
	 * @see Trails#TYPE_TEMPLATE
	 * @see Trails#TYPE_USER
	 * @param trailType	The type of the trail
	 */
	public void setTrailType(String trailType)
	{
	    this.trailType = trailType;
	}
	
	

}
