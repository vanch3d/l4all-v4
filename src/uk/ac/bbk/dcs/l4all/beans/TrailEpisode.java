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

/**
 * Represent an episode of a given timeline
 * @author nicolas
 *
 */
public class TrailEpisode
{
    /** The title of the episode */
    private String title = "";

    /** The category of the episode */
    private String category = "";
    
    /** The start date of the episode */
    private String start = "";

    /** The end date of the episode */
    private String end = "";

    /** The URL associated with the episode */
    private String url = "";

    /** The description of the episode */
    private String description = "";

    /** The timeline to which the episode belongs */
    private String ownerTmln = "";

    /** The nature of the episode */
    private String nature = "";

    /** The code for the primary classification of the episode */
    private String primary = "";

    /** The code for the secondary classification of the episode */
    private String secondary = "";

    public String getCategory()
    {
	return category;
    }

    public void setCategory(String category)
    {
	this.category = category;
    }

    public String getDescription()
    {
	return description;
    }

    public void setDescription(String description)
    {
	this.description = description;
    }

    public String getEnd()
    {
	return end;
    }

    public void setEnd(String end)
    {
	this.end = end;
    }

    public String getOwnerTmln()
    {
	return ownerTmln;
    }

    public void setOwnerTmln(String ownerTmln)
    {
	this.ownerTmln = ownerTmln;
    }

    public String getStart()
    {
	return start;
    }

    public void setStart(String start)
    {
	this.start = start;
    }

    public String getTitle()
    {
	return title;
    }

    public void setTitle(String title)
    {
	this.title = title;
    }

    public String getUrl()
    {
	return url;
    }

    public void setUrl(String url)
    {
	this.url = url;
    }

    public String getNature()
    {
	return nature;
    }

    public void setNature(String nature)
    {
	this.nature = nature;
    }

    public String getPrimary()
    {
        return primary;
    }

    public void setPrimary(String primary)
    {
        this.primary = primary;
    }

    public String getSecondary()
    {
        return secondary;
    }

    public void setSecondary(String secondary)
    {
        this.secondary = secondary;
    }

    
}
