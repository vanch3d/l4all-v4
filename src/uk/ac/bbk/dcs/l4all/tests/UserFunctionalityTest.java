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

import uk.ac.bbk.dcs.l4all.beans.AuthenticateUser;
import uk.ac.bbk.dcs.l4all.beans.L4AllUserManager;
import uk.ac.bbk.dcs.l4all.beans.Message;

public class UserFunctionalityTest {
	
	public static void main(String [] args) {
		L4AllUserManager userManager = new L4AllUserManager();
		
//		userManager.createUser("gm","gm","George Magoulas",35,"Male","EC2A 4NP",40,"gpapam@gmail.com");
//		userManager.addUserLearningPref("Student","Software Engineer",new String[]{"MSc","PhD"},new String[]{"Java","C"},new String[]{"Chess","Computer Programming"},new String[]{"Manager"},"Full-time","Reading","None",2000,10,"gpapam");
//		userManager.changeUserIdentificationDetails("gpapam","Snaiper2404","",0,"","",0,"");
//		userManager.changeUserIdentificationDetails("gpapam","","Georgios Papamarkos",27,"","",50,"");
//		userManager.changeUserLearningPreferences("","",new String[]{"MSc","PhD"},new String[]{"Java","C"},new String[]{"Chess","Computer Programming"},new String[]{"Manager"},55000,"",40,"","","gpapam");
//		userManager.changeUserLearningPreferences("Patomatzis","",null,null,null,null,55000,"",40,"","","gpapam");
		
//		Message msg = userManager.getUserLearningPrefDetails("ap");
//		L4AllUser l4allUser = (L4AllUser)msg.getResultObject();
//		
//		String [] quals = l4allUser.getQualifications();
//		
//		for (int i=0; i<quals.length;i++)
//			System.out.println(quals[i]);
//		
//		String [] skills = l4allUser.getSkills();
//		
//		for (int i=0; i<skills.length;i++)
//			System.out.println(skills[i]);
//		
//		String [] intr = l4allUser.getInterests();
//		
//		for (int i=0; i<intr.length;i++)
//			System.out.println(intr[i]);
		
		
//		userManager.dump();
		
		
//		AuthenticateUser au = new AuthenticateUser();
//		boolean auth = au.validate("gm", "gm");
//		
//		System.out.println(auth);
		
//		Message msg = userManager.getUserIdentificationDetails("gm");
//		
//		uk.ac.bbk.dcs.l4all.beans.L4AllUser user = (uk.ac.bbk.dcs.l4all.beans.L4AllUser)msg.getResultObject();
//		
//		System.err.println(user.getName());
		
		
//		Message msg = userManager.getUserLearningPrefDetails("gpapam");
//		
//		L4AllUser user = (L4AllUser)msg.getResultObject();
//		System.out.println("Budget : " + user.getBudget());
//		System.out.println("Qualifications : " + user.getQualifications().length);
//		System.out.println("Interests : " + user.getInterests().length);

//		L4AllUsersSearch.searchByOccupation("Software");
//		L4AllUsersSearch.searchByGoal("Manager");
//		L4AllUsersSearch.searchByInterests("Chess");
//		Set st = L4AllUsersSearch.searchBySkills(new String[]{"Java"});
		
//		Set set = L4AllUsersSearch.searchByQualifications(" ");
		
//		if (set == null)
//			System.out.println("null");
	}
	
}
