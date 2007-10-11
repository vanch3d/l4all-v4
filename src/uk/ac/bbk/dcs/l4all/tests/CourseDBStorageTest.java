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
package uk.ac.bbk.dcs.l4all.tests;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdql.QueryResults;

import uk.ac.bbk.dcs.l4all.beans.LearnDirectCourse;
import uk.ac.bbk.dcs.l4all.beans.L4AllCourse;
import uk.ac.bbk.dcs.l4all.beans.LearnDirectCourseSearch;
import uk.ac.bbk.dcs.l4all.db.RDFRepositoryManager;
import uk.ac.bbk.dcs.l4all.util.RDFGraphUtil;

/**
 * @author George Papamarkos
 *
 * CourseDBStorageTest.java
 * uk.ac.bbk.dcs.l4all.tests
 */
public class CourseDBStorageTest {

	public static void main(String[] args) {
		
		Model model = RDFRepositoryManager.getModel("l4all_courses");
		
		String courseURL = "http://www.dcs.bbk.ac.uk/~gpapa05/l4all/courses/";
		QueryResults qr = RDFGraphUtil.advancedSearchCourses(courseURL, "", "", "", "", model);
		
		
		
		RDFGraphUtil.printQueryResults(qr, System.out);
		
		System.out.println("\n****************************************************\n");
//		L4AllSearch l4allSearch = new L4AllSearch();
//		Course [] courses = l4allSearch.quickSearchCourses("Music", courseURL);
//		
//		for (int i=0; i < courses.length ; i++) {
//			System.out.println(courses[i].getTitle());
//		}
		
//		model = RDFRepositoryManager.getModel("l4all_users");
//		model.write(System.out);
//		QueryResults res = RDFGraphUtil.advancedSearchUsers("http://www.dcs.bbk.ac.uk/~gpapa05/l4all/users/", "", "", "", "MSc", model);
//		
//		System.out.println("===========================================");
//		RDFGraphUtil.printQueryResults(res, System.out);
	}
	
	public static void storeData() {
		L4AllCourse courseManager = new L4AllCourse();
		Model model = RDFRepositoryManager.getModel("l4all_courses");
		courseManager.setTitle("Secondary PGCE in Music");
		courseManager.setCreator("FM");
		courseManager.setSubject("This course is particularly suited to musicians who want to combine professional work with instrumental or vocal teaching or classroom-based teaching. The course is designed to make the most of your education and community links.");
		courseManager.setInstitution("Institute of Education");
		courseManager.setMemberOfStaff("Pauline Adams");
		courseManager.setCourseType("Part-time");
		courseManager.setStartDate("September");
		courseManager.setUrl("");
		courseManager.setLanguage("English");
		courseManager.setModeOfStudy("Mixed");
		courseManager.setScope("PGCE Secondary (Music)");
		courseManager.setEntryRequirements("You should either have a single honours degree in music, or have spent at least 50 per cent of your undergraduate studies in the study of music. You should also be able to demonstrate:" +
				"a)  some awareness of current issues in education" +
				"b)  evidence of having worked with young people" +
				"c)  a high level of practical musicianship and musical interests – for example, playing/singing in a group, band or orchestra" +
				"d)  an openness to expanding your personal musical knowledge and skills.");
		courseManager.setSkills("Music, communication");
		courseManager.setCost("");
		
		courseManager.setCourseCode("IOE-" + 2);
		
		courseManager.createNewCourse();
	}
	

}
