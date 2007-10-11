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
import uk.ac.bbk.dcs.l4all.util.RDFGraphUtil;
import uk.ac.bbk.dcs.l4all.vocabulary.L4all_user;

import com.hp.hpl.jena.db.ModelRDB;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Seq;
import com.hp.hpl.jena.rdf.model.SimpleSelector;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;

/**
 * @author george
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class L4AllUsersSearch {
	private static final String MODEL_NAME = "l4all_users";

	private static ModelRDB model = RDFRepositoryManager.getModel(MODEL_NAME);

	public static L4AllUser[] getAllUsers()
	{
		Set result = new HashSet();
		StmtIterator iter2 = model.listStatements(new SimpleSelector(null,L4all_user.username,(RDFNode)null));
		while (iter2.hasNext()) 
		{
			Statement stat = iter2.nextStatement();
			if (stat==null) continue;
			Resource res = stat.getSubject();
			if (res==null) continue;
			String URI = res.getURI();
			if (URI==null) continue;
			String test = URI.split("LearningPrefs")[0];
			Resource user = model.getResource(test);
			result.add(user);		
		}
		
		Vector l4allUsers = new Vector();
		Iterator iter = result.iterator();
		int index = 0;
		while (iter.hasNext()) {
			Object o = iter.next();

			if (o instanceof HashSet) {
				HashSet hs = (HashSet) o;
				Iterator it = hs.iterator();

				while (it.hasNext()) {
					Object obj = it.next();
					if (obj instanceof Resource) {
						index++;
						Resource user = (Resource) obj;
						String uName = user.getProperty(L4all_user.name)
								.getString();
						String uUsername = user
								.getProperty(L4all_user.username).getString();

						L4AllUser usr = new L4AllUser();
						usr.setName(uName);
						usr.setUsername(uUsername);

						l4allUsers.add(usr);
					}
				}
			} else if (o instanceof Resource) {
				index++;
				Resource user = (Resource) o;
				Resource Identification = model.getResource(user.getURI());
				
				Statement stat = Identification.getProperty(L4all_user.name);
				if (stat==null) continue;
				String uName = stat.getString();
				String uUsername = Identification
				.getProperty(L4all_user.username).getString();
				String uStatus = Identification
				.getProperty(L4all_user.status).getString();

				L4AllUser usr = new L4AllUser();
				usr.setName(uName);
				usr.setUsername(uUsername);
				usr.setStatus(uStatus);

				l4allUsers.add(usr);
			}
		}
		
		return (L4AllUser[]) l4allUsers.toArray(new L4AllUser[l4allUsers.size()]);

	}
	
	/**
	 * Seach people according to their age
	 * 
	 * @param _age
	 * @return
	 */
	public static Set searchByAge(int _age) {
		final int age = _age;
		Set result = new HashSet();

		if (age < 0)
			return result;

		StmtIterator iter = model.listStatements(new SimpleSelector(null,
				L4all_user.age, (RDFNode) null) {
			public boolean selects(Statement s) {
				boolean isMatched = false;
				String obj = s.getString();
				Pattern p = Pattern.compile("(.*)(?i)" + age + "(.*)");
				Matcher m = p.matcher(obj);
				isMatched = m.matches();
				return isMatched;
			}
		});

		while (iter.hasNext()) {
			String URI = iter.nextStatement().getSubject().getURI().split("LearningPrefs")[0];
			Resource user = model.getResource(URI);
			result.add(user);		}

		return result;
	}

	/**
	 * Search people according to their occupation
	 * 
	 * @param occupation
	 * @return
	 */
	public static Set searchByOccupation(String occupation) {
		final String occ = occupation;
		Set result = new HashSet();

		if (occ.trim().equals(""))
			return result;

		StmtIterator iter = model.listStatements(new SimpleSelector(null,
				L4all_user.present_occupation, (RDFNode) null) {
			public boolean selects(Statement s) {
				boolean isMatched = false;
				String obj = s.getString();
				Pattern p = Pattern.compile("(.*)(?i)" + occ + "(.*)");
				Matcher m = p.matcher(obj);
				isMatched = m.matches();
				return isMatched;
			}
		});

		while (iter.hasNext()) {
			String URI = iter.nextStatement().getSubject().getURI().split("LearningPrefs")[0];
			Resource user = model.getResource(URI);
			result.add(user);
		}

		return result;
	}

	/**
	 * Search people according to their skills
	 * 
	 * @param skills
	 * @return
	 */
	public static Set searchBySkills(String [] skills) {
		final String [] sk = skills;
		Set result = new HashSet();

		// FIXME Apply search for all of the contents of the skills array
		if (sk[0].trim().equals(""))
			return result;

		StmtIterator iter = model.listStatements(new SimpleSelector(null,
				L4all_user.skills, (RDFNode) null) {
			public boolean selects(Statement s) {
				Seq sq = s.getSeq();

				String[] uSkills = RDFGraphUtil.getContainerStringContents(sq);

				for (int i = 0; i < uSkills.length; i++) {
					if (uSkills[i].contains(sk[0]))
						return true;
				}

				return false;
			}
		});

		while (iter.hasNext()) {
			String URI = iter.nextStatement().getSubject().getURI().split("LearningPrefs")[0];
			Resource user = model.getResource(URI);
			result.add(user);
		}

		return result;
	}

	/**
	 * Search people according to their goals
	 * 
	 * @param goals
	 * @return
	 */
	public static Set searchByGoal(String [] goals) {
		final String [] gls = goals;
		Set result = new HashSet();

		// FIXME Apply search for all the contents of the 'goals' table
		if (gls[0].trim().equals(""))
			return result;

		StmtIterator iter = model.listStatements(new SimpleSelector(null,
				L4all_user.goals, (RDFNode) null) {
			public boolean selects(Statement s) {
				Seq sq = s.getSeq();
				RDFNode skillSeq = s.getObject();

				String[] uSkills = RDFGraphUtil.getContainerStringContents(sq);

				for (int i = 0; i < uSkills.length; i++) {
					if (uSkills[i].contains(gls[0]))
						return true;
				}

				return false;
			}
		});

		while (iter.hasNext()) {
			String URI = iter.nextStatement().getSubject().getURI().split("LearningPrefs")[0];
			Resource user = model.getResource(URI);
			result.add(user);
		}

		return result;
	}

	/**
	 * Search people according to their interests
	 * 
	 * @param interests
	 * @return
	 */
	public static Set searchByInterests(String [] interests) {
		final String [] intr = interests;
		Set result = new HashSet();

		// FIXME Apply search for all the contents of the 'interests' table
		if (intr[0].trim().equals(""))
			return result;

		StmtIterator iter = model.listStatements(new SimpleSelector(null,
				L4all_user.interests, (RDFNode) null) {
			public boolean selects(Statement s) {
				Seq sq = s.getSeq();

				String[] uSkills = RDFGraphUtil.getContainerStringContents(sq);

				for (int i = 0; i < uSkills.length; i++) {
					if (uSkills[i].contains(intr[0]))
						return true;
				}

				return false;
			}
		});

		while (iter.hasNext()) {
			String URI = iter.nextStatement().getSubject().getURI().split("LearningPrefs")[0];
			Resource user = model.getResource(URI);
			result.add(user);		}

		return result;
	}

	/**
	 * Searches users according to their qualifications
	 * 
	 * @param qual
	 * @return
	 */
	public static Set searchByQualifications(String qual) {
		final String intr = qual;
		Set result = new HashSet();

		if (intr.trim().equals(""))
			return result;

		StmtIterator iter = model.listStatements(new SimpleSelector(null,
				L4all_user.qualifications, (RDFNode) null) {
			public boolean selects(Statement s) {
				Seq sq = s.getSeq();

				String[] uSkills = RDFGraphUtil.getContainerStringContents(sq);

				for (int i = 0; i < uSkills.length; i++) {
					if (uSkills[i].contains(intr))
						return true;
				}

				return false;
			}
		});

		while (iter.hasNext()) {
			String URI = iter.nextStatement().getSubject().getURI().split("LearningPrefs")[0];
			Resource user = model.getResource(URI);
			result.add(user);		}

		return result;
	}

	/**
	 * Searches for people within L4All that fullfix the given criteria
	 * 
	 * @param age
	 * @param occupation
	 * @param skills
	 * @param interests
	 * @param goals
	 * @param disability
	 * @param username
	 * @return
	 */
	public static L4AllUser[] searchL4AllUsers(int age, String occupation,
			String [] skills, String [] interests, String [] goals, boolean disability,
			String username) {
		Set ageResults = new HashSet();
		Set occResults = new HashSet();
		Set skillsResults = new HashSet();
		Set interestsResults = new HashSet();
		Set goalsResults = new HashSet();
		Set disabilityResults = new HashSet();

		if (age > 0)
			ageResults = searchByAge(age);

		if (skills != null)
			skillsResults = searchBySkills(skills);

		if (!occupation.trim().equals(""))
			occResults = searchByOccupation(occupation);

		if (interests != null)
			interestsResults = searchByInterests(interests);

		if (goals != null)
			goalsResults = searchByGoal(goals);

		skillsResults.addAll(disabilityResults);
		skillsResults.addAll(occResults);
		skillsResults.addAll(interestsResults);
		skillsResults.addAll(goalsResults);
		skillsResults.addAll(ageResults);

		// /

		Vector l4allUsers = new Vector();
		Iterator iter = skillsResults.iterator();
		int index = 0;

		while (iter.hasNext()) {
			Object o = iter.next();

			if (o instanceof HashSet) {
				HashSet hs = (HashSet) o;
				Iterator it = hs.iterator();

				while (it.hasNext()) {
					Object obj = it.next();
					if (obj instanceof Resource) {
						index++;
						Resource user = (Resource) obj;
						String uName = user.getProperty(L4all_user.name)
								.getString();
						String uUsername = user
								.getProperty(L4all_user.username).getString();

						L4AllUser usr = new L4AllUser();
						usr.setName(uName);
						usr.setUsername(uUsername);

						l4allUsers.add(usr);
					}
				}
			} else if (o instanceof Resource) {
				index++;
				Resource user = (Resource) o;
				Resource Identification = model.getResource(user.getURI() +  "Identifier");
				
				String uName = Identification.getProperty(L4all_user.name).getString();
				String uUsername = Identification
						.getProperty(L4all_user.username).getString();

				L4AllUser usr = new L4AllUser();
				usr.setName(uName);
				usr.setUsername(uUsername);

				l4allUsers.add(usr);
			}
		}
		
		return (L4AllUser[]) l4allUsers.toArray(new L4AllUser[l4allUsers.size()]);
	}
}
