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

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import uk.ac.bbk.dcs.l4all.db.RDFRepositoryManager;
import uk.ac.bbk.dcs.l4all.vocabulary.Dc_ext;

import com.hp.hpl.jena.db.ModelRDB;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.SimpleSelector;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.vocabulary.DC_11;

/**
 * @author george
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class L4AllCoursesSearch {
	private static final String MODEL_NAME = "l4all_courses";
	private static ModelRDB model = RDFRepositoryManager.getModel(MODEL_NAME);
    
	/**
	 * Search L4All Courses by subject
	 * @param subject
	 * @return
	 */
	public static Set searchBySubject(String subject) {
		final String sbj = subject ;
		Set result = new HashSet();
		
		StmtIterator iter = model.listStatements(new SimpleSelector(null,DC_11.subject,(RDFNode)null) {
			public boolean selects(Statement s) {
				boolean isMatched = false ;
				String obj = s.getString();
				Pattern p = Pattern.compile("(.*)(?i)" + sbj + "(.*)");
				Matcher m = p.matcher(obj);
				isMatched = m.matches();
				return isMatched; 
			}
		});
				
		while (iter.hasNext()) {
//			System.out.println("searchBySubject: " + ((Resource)(iter.nextStatement().getSubject())).toString());
			result.add((Resource)iter.nextStatement().getSubject());
		}
		
		return result;
	}
	
	/**
	 * Search course by type of study
	 * 
	 * @param tos
	 * @return
	 */
	public static Set searchByTypeOfStudy(String tos) {
		final String o = tos ;
		Set result = new HashSet();
		
		StmtIterator iter = model.listStatements(new SimpleSelector(null,Dc_ext.modeOfStudy,(RDFNode)null) {
			public boolean selects(Statement s) {
				boolean isMatched = false ;
				String obj = s.getString();
				Pattern p = Pattern.compile("(.*)(?i)" + o + "(.*)");
				Matcher m = p.matcher(obj);
				isMatched = m.matches();
				return isMatched; 
			}
		});
				
		while (iter.hasNext()) {
//			System.out.println("searchByModeOfStudy: " + ((Resource)(iter.nextStatement().getSubject())).toString());
//			result.add((Resource)iter.nextStatement().getSubject());
		}
		
		return result;
	}
	
	public static Set searchByInstitution(String inst) {
		final String o = inst ;
		Set result = new HashSet();
		
		StmtIterator iter = model.listStatements(new SimpleSelector(null,DC_11.publisher,(RDFNode)null) {
			public boolean selects(Statement s) {
				boolean isMatched = false ;
				String obj = s.getString();
//				System.out.println(obj);
				Pattern p = Pattern.compile("(.*)(?i)" + o + "(.*)");
				Matcher m = p.matcher(obj);
				isMatched = m.matches();
				return isMatched; 
			}
		});
				
		while (iter.hasNext()) {
//			System.out.println("searchByInstitution: " + ((Resource)(iter.nextStatement().getSubject())).toString());
			result.add((Resource)iter.nextStatement().getSubject());
		}
		
		return result;
	}
	
	public static Set searchByLevel(String level) {
		final String lvl = level ;
		Set result = new HashSet();
		
		StmtIterator iter = model.listStatements(new SimpleSelector(null,Dc_ext.level,(RDFNode)null) {
			public boolean selects(Statement s) {
				boolean isMatched = false ;
				String obj = s.getString();
//				System.out.println(obj);
				Pattern p = Pattern.compile("(.*)(?i)" + lvl + "(.*)");
				Matcher m = p.matcher(obj);
				isMatched = m.matches();
				return isMatched; 
			}
		});
				
		while (iter.hasNext()) {
			System.out.println("searchByLevel: " + ((Resource)(iter.nextStatement().getSubject())).toString());
//			result.add((Resource)iter.nextStatement().getSubject());
		}
		
		return result;
	}

	public static Set searchByTitle(String title) {
		final String tl = title ;
		Set result = new HashSet();
		
		StmtIterator iter = model.listStatements(new SimpleSelector(null,DC_11.title,(RDFNode)null) {
			public boolean selects(Statement s) {
				boolean isMatched = false ;
				String obj = s.getString();
//				System.out.println(obj);
				Pattern p = Pattern.compile("(.*)(?i)" + tl + "(.*)");
				Matcher m = p.matcher(obj);
				isMatched = m.matches();
				return isMatched; 
			}
		});
				
		while (iter.hasNext()) {
			//System.out.println("searchByTitle: " + ((Resource)(iter.nextStatement().getSubject())).toString());
			result.add((Resource)iter.nextStatement().getSubject());
		}
		
		return result;
	}
	
	public static Set searchByDescription(String desc) {
		final String description = desc ;
		Set result = new HashSet();
		
		StmtIterator iter = model.listStatements(new SimpleSelector(null,DC_11.description,(RDFNode)null) {
			public boolean selects(Statement s) {
				boolean isMatched = false ;
				String obj = s.getString();
//				System.out.println(obj);
				Pattern p = Pattern.compile("(.*)(?i)" + description + "(.*)");
				Matcher m = p.matcher(obj);
				isMatched = m.matches();
				return isMatched; 
			}
		});
				
		while (iter.hasNext()) {
			//System.out.println("searchByDescription: " + ((Resource)(iter.nextStatement().getSubject())).toString());
			result.add((Resource)iter.nextStatement().getSubject());
		}
		
		return result;
	}
	
	public static Set searchByKeyword(String [] keywords) {
		final String [] kw = keywords ;
		
		Set resultFromTitleSearch = new HashSet();
		Set resultFromDescriptionSearch = new HashSet();
		Set resultFromInstSearch = new HashSet();
		
		resultFromTitleSearch.clear();
		resultFromDescriptionSearch.clear();
		resultFromInstSearch.clear();
		
		for (int i=0; i < kw.length ; i++) {
			if (!resultFromTitleSearch.isEmpty())
				resultFromTitleSearch.retainAll(searchByTitle(kw[i]));
			else
				resultFromTitleSearch = searchByTitle(kw[i]);
		}
		
		for (int i=0; i < kw.length ; i++) {
			if (!resultFromDescriptionSearch.isEmpty())
				resultFromDescriptionSearch.retainAll(searchByDescription(kw[i]));
			else
				resultFromDescriptionSearch = searchByDescription(kw[i]);
		}
		
		for (int i=0; i < kw.length ; i++) {
			if (!resultFromInstSearch.isEmpty())
				resultFromInstSearch.retainAll(searchByInstitution(kw[i]));
			else
				resultFromInstSearch = searchByInstitution(kw[i]);
		}
		
		
		/// Find and remove any duplications
		Set tmpSet = resultFromTitleSearch;
		
		//delete all element from DescSearch that are contained in TitleSearch
		if (tmpSet.retainAll(resultFromDescriptionSearch))
			resultFromDescriptionSearch.removeAll(tmpSet);  
		
		tmpSet = resultFromTitleSearch;
		
		//	delete all element from InstSearch that are contained in TitleSearch
		if (tmpSet.retainAll(resultFromInstSearch))
			resultFromInstSearch.removeAll(tmpSet);
		
		tmpSet = resultFromInstSearch ;
		//	delete all element from DescSearch that are contained in InstSearch
		if (tmpSet.retainAll(resultFromDescriptionSearch))
			resultFromDescriptionSearch.removeAll(tmpSet);
//		
		resultFromTitleSearch.add(resultFromDescriptionSearch);
		resultFromTitleSearch.add(resultFromInstSearch);
		
//		System.out.println(resultFromTitleSearch.toString());
		
		return resultFromTitleSearch; 
	}
	
	/**
	 * 
	 * @param subject
	 * @param typeOfStudy
	 * @param keywords
	 * @param inst
	 * @param level
	 * @param searchMode
	 * @return
	 */
	public static L4AllCourse [] searchL4AllCourse(String subject, String typeOfStudy, String [] keywords, String inst, String level , String searchMode) {
		
		Set rs1 = new HashSet();
		Set rs2 = new HashSet();
		Set rs3 = new HashSet();
		Set rs4 = new HashSet();
		Set rs5 = new HashSet();
		
		if (!subject.trim().equals(""))
			rs1 = searchBySubject(subject);
		
		if (!typeOfStudy.trim().equals(""))
			rs2 = searchByTypeOfStudy(typeOfStudy);
		
		if (keywords != null)
			rs3 = searchByKeyword(keywords);
		else
			rs3 = searchByKeyword(new String[]{" "});
				
		if (!inst.trim().equals(""))
			rs4 = searchByInstitution(inst);
		
		if (!level.trim().equals(""))
			rs5 = searchByLevel(level);
		
		if (searchMode == null || searchMode.trim().equals(""))
			searchMode = "and";
		
		if (searchMode.trim().equalsIgnoreCase("and")) {
			if (!rs2.isEmpty())
				rs3.retainAll(rs2);
			if (!rs1.isEmpty())
				rs3.retainAll(rs1);
			if (!rs4.isEmpty())
				rs3.retainAll(rs4);
			if (!rs5.isEmpty())
				rs3.retainAll(rs5);
		} else if (searchMode.trim().equalsIgnoreCase("or")) {
			rs3.addAll(rs2);
			rs3.addAll(rs1);
			rs3.addAll(rs4);
			rs3.addAll(rs5);
		}
		
		// Get the details 	including ID, title and description of each course in 'rs1'
		Vector l4allCourses = new Vector();
		Iterator iter = rs3.iterator();
		int index = 0 ;
		while (iter.hasNext()) {
			Object o = iter.next();
			
			if (o instanceof HashSet) {
				HashSet hs = (HashSet)o;
				Iterator it = hs.iterator();
				
				while (it.hasNext()) {
					Object obj = it.next();
					if (obj instanceof Resource) {
						index++;
						Resource course = (Resource)obj ;
						String title = course.getProperty(DC_11.title).getString();
						String id = course.getProperty(DC_11.identifier).getString();
						String institution = course.getProperty(DC_11.publisher).getString();
						Statement stat = course.getProperty(DC_11.description);
						String description = stat.getString();
//						System.out.println("Identifier : " + id);
//						System.out.println("Title : " + title);
//						System.out.println("Description : " + description);
						
						L4AllCourse l4allCourse = new L4AllCourse();
						l4allCourse.setTitle(title);
						l4allCourse.setCourseCode(id);
						l4allCourse.setDescription(description);
						l4allCourse.setInstitution(institution);

						l4allCourses.add(l4allCourse);
					}
				}
			} 
		}
		
		return (L4AllCourse[])l4allCourses.toArray(new L4AllCourse[l4allCourses.size()]);
	}
	
	/**
	 * 
	 * @param courseID
	 * @return
	 */
	public static L4AllCourse getL4AllCourseDetails(String courseID) {
		
		final String courseURL = "http://www.dcs.bbk.ac.uk/~gpapa05/l4all/courses/" + courseID ;
		
		Resource course = model.getResource(courseURL);
		
		Statement stat = course.getProperty(DC_11.title);
		if (stat==null) return null;
		
		String title = course.getProperty(DC_11.title).getString();
		String id = course.getProperty(DC_11.identifier).getString();
		String description = course.getProperty(DC_11.description).getString();
		String URL = course.getProperty(DC_11.source).getString();
		
		System.out.println("Identifier : " + id);
		System.out.println("Title : " + title);
		System.out.println("Description : " + description);
		System.out.println("URL : " + URL);
		
		L4AllCourse l4allCourse = new L4AllCourse();
		l4allCourse.setTitle(title);
		l4allCourse.setCourseCode(id);
		l4allCourse.setDescription(description);
		l4allCourse.setUrl(URL);
		l4allCourse.setCost(course.getProperty(Dc_ext.cost).getString());
		l4allCourse.setCourseType(course.getProperty(Dc_ext.courseType).getString());
		l4allCourse.setCreator(course.getProperty(DC_11.creator).getString());
		l4allCourse.setEntryRequirements(course.getProperty(Dc_ext.entry).getString());
		l4allCourse.setInstitution(course.getProperty(DC_11.publisher).getString());
		//l4allCourse.setLanguage(course.getProperty(DC_11.language).getString());
		l4allCourse.setLevel(course.getProperty(Dc_ext.level).getString());
		l4allCourse.setMemberOfStaff(course.getProperty(DC_11.contributor).getString());
		l4allCourse.setModeOfStudy(course.getProperty(Dc_ext.modeOfStudy).getString());
		l4allCourse.setScope(course.getProperty(DC_11.coverage).getString());
		l4allCourse.setSkills(course.getProperty(Dc_ext.skills).getString());
		l4allCourse.setStartDate(course.getProperty(DC_11.date).getString());
		l4allCourse.setSubject(course.getProperty(DC_11.subject).getString());
		
		return l4allCourse ;
	}

}
