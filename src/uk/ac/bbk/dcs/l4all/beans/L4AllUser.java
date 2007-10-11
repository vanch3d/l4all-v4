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

import java.util.Arrays;

/**
 * @author George Papamarkos
 *
 * UserProfile.java
 * uk.ac.bbk.dcs.l4all.beans
 */
public class L4AllUser
{

    // Support for Similarity measure
    public float simValue = 0.f; 	///< Value of the similarity measure 
    public String simCoding = ""; 	///< String Coding for the timeline

    // Personal Info
    String id = "";
    String username = "";
    String password = "";
    String status = "";
    String name = "";

    int age = 0;

    String gender = "";

    String myLocation = "";

    int travelWillingness; // in miles

    String email = "";

    // Learning Preferences
    String pastOccupation = "";

    String presentOccupation = "";

    String[] qualifications = null;

    String[] skills = null;

    String studyType = "";

    int hoursOfStudyPW;

    int budget;

    String learningMethod = "";

    String[] interests = null;

    String[] goals = null;

    String disability = "";

    public int getAge()
    {
	return age;
    }

    public void setAge(int age)
    {
	this.age = age;
    }

    public int getBudget()
    {
	return budget;
    }

    public void setBudget(int budget)
    {
	this.budget = budget;
    }

    public String getDisability()
    {
	return disability;
    }

    public void setDisability(String disability)
    {
	this.disability = disability;
    }

    public String getGender()
    {
	return gender;
    }

    public void setGender(String gender)
    {
	this.gender = gender;
    }

    public int getHoursOfStudyPW()
    {
	return hoursOfStudyPW;
    }

    public void setHoursOfStudyPW(int hoursOfStudyPW)
    {
	this.hoursOfStudyPW = hoursOfStudyPW;
    }

    public String getId()
    {
	return id;
    }

    public void setId(String id)
    {
	this.id = id;
    }

    public String[] getInterests()
    {
	return interests;
    }

    public void setInterests(String[] interests)
    {
	this.interests = interests;
    }

    public String getLearningMethod()
    {
	return learningMethod;
    }

    public void setLearningMethod(String learningMethod)
    {
	this.learningMethod = learningMethod;
    }

    public String getMyLocation()
    {
	return myLocation;
    }

    public void setMyLocation(String myLocation)
    {
	this.myLocation = myLocation;
    }

    public String getName()
    {
	return name;
    }

    public void setName(String name)
    {
	this.name = name;
    }

    public String getPassword()
    {
	return password;
    }

    public void setPassword(String password)
    {
	this.password = password;
    }

    public String getPastOccupation()
    {
	return pastOccupation;
    }

    public void setPastOccupation(String pastOccupation)
    {
	this.pastOccupation = pastOccupation;
    }

    public String getPresentOccupation()
    {
	return presentOccupation;
    }

    public void setPresentOccupation(String presentOccupation)
    {
	this.presentOccupation = presentOccupation;
    }

    public String[] getSkills()
    {
	return skills;
    }

    public void setSkills(String[] skills)
    {
	this.skills = skills;
    }

    public String getStudyType()
    {
	return studyType;
    }

    public void setStudyType(String studyType)
    {
	this.studyType = studyType;
    }

    public int getTravelWillingness()
    {
	return travelWillingness;
    }

    public void setTravelWillingness(int travelWillingness)
    {
	this.travelWillingness = travelWillingness;
    }

    public String getUsername()
    {
	return username;
    }

    public void setUsername(String username)
    {
	this.username = username;
    }

    public String getEmail()
    {
	return email;
    }

    public void setEmail(String email)
    {
	this.email = email;
    }

    public String[] getGoals()
    {
	return goals;
    }

    public void setGoals(String[] goals)
    {
	this.goals = goals;
    }

    public String[] getQualifications()
    {
	return qualifications;
    }

    public void setQualifications(String[] qualifications)
    {
	this.qualifications = qualifications;
    }

    public String getInAString(String[] tbl)
    {
	StringBuffer strBuff = new StringBuffer();
	for (int i = 0; i < tbl.length; i++)
	{
	    strBuff.append(tbl[i]);
	    if (i <= tbl.length - 1)
		strBuff.append(",");
	}

	return strBuff.toString();
    }

    public String getSimCoding()
    {
	return simCoding;
    }

    public void setSimCoding(String simCoding)
    {
	this.simCoding = simCoding;
    }

    public float getSimValue()
    {
	return simValue;
    }

    public void setSimValue(float simIndex)
    {
	this.simValue = simIndex;
    }

    public String getStatus()
    {
	return status;
    }

    public void setStatus(String status)
    {
	this.status = status;
    }
    
    /**
     * Since User Identification and Learning Preferences are coming from two different sources,
     * this method adds the LPs to the current L4ALL bean.
     *    
     * @param lpUser	A reference to the user bean containing the learning prefs.
     */
    public void addLearningPref(final L4AllUser lpUser)
    {
	if (lpUser==null) return;
	pastOccupation = lpUser.pastOccupation;
	presentOccupation = lpUser.presentOccupation;
	qualifications = (String[])Arrays.copyOf(
		lpUser.qualifications, lpUser.qualifications.length);
	skills = (String[])Arrays.copyOf(lpUser.skills,lpUser.skills.length);
	studyType = lpUser.studyType;
	hoursOfStudyPW = lpUser.hoursOfStudyPW;
	budget = lpUser.budget;
	learningMethod = lpUser.learningMethod;
	interests = (String[])Arrays.copyOf(
		lpUser.interests, lpUser.interests.length);
	goals = (String[])Arrays.copyOf(
		lpUser.goals, lpUser.goals.length);
	disability = lpUser.disability;
    }

    /**
     * Return the user as an XML structure
     * @return
     */
    public String toXML()
    {
	String res = null;
	res = ("<user>" + "\n");
	
        res += ("</user>" + "\n");

	return res;
    }

}
