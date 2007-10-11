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
/*
 * Created on 21-Feb-2006
 * 
 * uk.ac.bbk.dcs.l4all.tests in l4all-v2
 *
 */
package uk.ac.bbk.dcs.l4all.tests;

import uk.ac.bbk.dcs.l4all.beans.L4AllUser;
import uk.ac.bbk.dcs.l4all.beans.L4AllUsersSearch;

/**
 * @author George Papamarkos
 *
 * L4AllUserSearchTest.java
 * uk.ac.bbk.dcs.l4all.tests
 */
public class L4AllUserSearchTest {

	public static void main(String[] args) {
		L4AllUsersSearch l4allSearch = new L4AllUsersSearch();
		
		String [] skills = { "Java" } ;
		String [] goals = { "President" } ;
		
		L4AllUser[] users = L4AllUsersSearch.searchL4AllUsers(0, "", skills, new String[]{"Golf"}, goals, false, "gm");
		
		for (int i = 0 ; i < users.length ; i++) {
			
			System.out.println("Name : " + users[i].getName());
			System.out.println("Username : " + users[i].getUsername());
		}
		
	}
}
