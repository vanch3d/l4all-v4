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

import java.util.Set;

import uk.ac.bbk.dcs.l4all.beans.L4AllTrailSearch;
import uk.ac.bbk.dcs.l4all.beans.Trail;
import uk.ac.bbk.dcs.l4all.beans.UserTrailManager;

public class TrailFeaturesTest {
	public static void main (String [] args) {
		UserTrailManager trailMgr = new UserTrailManager();
		
//		trailMgr.addNewTrail("gm", "GM's Trail","This a timeline concerning GM's higher education",1,"","life",1968,2040);
		
//		trailMgr.addNewTrail("gm", "GM's Trail","This a timeline concerning GM's higher education",1,"","life",1997,2007);
//		trailMgr.addEpisodeToTrail("gm","GM's Trail","The birth of my second child","Child Birth","1989","1999","","My IT Manager Career",1);
//		trailMgr.addEpisodeToTrail("gpapam","Testing trail","Nursery School","Education","1983","1984","","Nursery School",2);
//		trailMgr.addEpisodeToTrail("gpapam","My Higher Education","Nursery School","Education","1983","1984","","Nursery School",2);
//		trailMgr.addEpisodeToTrail("gpapam","My Higher Education","University","Education","1997","2002","","NTUA",1);
//		trailMgr.addEpisodeToTrail("gpapam","My Higher Education","Primary School","Education","1984","1989","","Primary School",2);
//		trailMgr.deleteEpisodeFromTrail("gpapam","My Higher Education",1);
		
//		trailMgr.editEpisode("gm", "Midnight", "PAPAMARKOS", null, null, null, null, null, 9);
		
//		L4AllTrailSearch.searchByName("Education","gpapam");
//		L4AllTrailSearch.searchByDescription("higher","gpapam");
//		L4AllTrailSearch.searchByTrailsKeywords("life","gpapam");
//		Set res = L4AllTrailSearch.searchByEpisodeName("Computer","gpapam");
//		Trail [] trails = L4AllTrailSearch.searchForTrails("","Computer",false,false,"gpapam");
		
		Trail[] trails = L4AllTrailSearch.searchForTrails("", "work", false, false,"gpapam");
//		
		for (int i = 0 ; i < trails.length ; i++) {
			String name = trails[i].getTrailName();
			System.out.println("[" + i + "] " + name);
		}
		
//		trailMgr.dump();
		
//		Message msg = trailMgr.getUserTrailDetails("gm");
//		
//		Trail tr = (Trail)msg.getResultObject();
//		
//		String name = tr.getTrailName();
//		
//		System.out.println(name);
//		
//		Trail trail = (Trail)msg.getResultObject();
//		TrailEpisode [] episodes = trail.getTrailEpisodes();
		
//		System.out.println(trails.length);
		
		
	}
}
