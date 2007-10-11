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
 * @author george
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class ErrorCodes {
	public final static int SUCCESS = 1;

	public final static int UNIQUE_USERNAME_CONSTRAINT_VIOLATION_ERROR = -100;

	public final static int INVALID_USERNAME_ERROR = -101;

	public final static int NO_MATCHING_PASSWORDS_ERROR = -102;

	public final static int EMPTY_FIELD_ERROR = -103;

	public final static int UNIQUE_TRAIL_NAME_CONSTRAINT_VIOLATION_ERROR = -104;

	public final static int DUBLICATE_RESOURCE_CONSTRAINT_VIOLATION_ERROR = -105;

	public final static int INVALID_TRAIL_NAME_ERROR = -106;

	public final static int UNKNOWN_ERROR = -107;

	public final static int INVALID_SEARCH_SET = -108;

	public final static int INVALID_FIELD_FORMAT = -109;
	
	public final static int EMPTY_RESULT_SET = -110;
}
