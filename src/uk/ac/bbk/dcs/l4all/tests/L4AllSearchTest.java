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
 * Created on 09-Jan-2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package uk.ac.bbk.dcs.l4all.tests;

import uk.ac.bbk.dcs.l4all.beans.L4AllCourse;
import uk.ac.bbk.dcs.l4all.beans.L4AllCoursesSearch;

/**
 * @author george
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class L4AllSearchTest {
	public static void main(String[] args) {
//		L4AllCoursesSearch.searchByInstitution("Birkbeck");
//		L4AllCoursesSearch.getL4AllCourseDetails("1136831711625");
//		Object[] cr = L4AllCoursesSearch.searchL4AllCourse("", "",
//				"", "", "", "or");
//
//		System.out.println(cr.length);
//
//		if (cr[0].getClass().equals(L4AllCourse.class))
//			System.out.print("Instance of L4AllCourse");
//		else
//			System.err.println("Wrong instance");
		
		L4AllCourse[] courses = L4AllCoursesSearch.searchL4AllCourse("", "", new String[]{"Birkbeck College"}, "", "", "and");
		
		for (int i = 0 ; i < courses.length ; i++) {
			String institution = courses[i].getInstitution();
			System.out.println("Institution :" + institution);
		}

	}
}
