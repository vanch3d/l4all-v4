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
 * Created on Jun 28, 2005 by George Papamarkos
 *  
 */
package uk.ac.bbk.dcs.l4all.tests;

import java.util.Vector;

import uk.ac.bbk.dcs.l4all.beans.Trail;
import uk.ac.bbk.dcs.l4all.beans.UserTrailManager;
import uk.ac.bbk.dcs.l4all.db.RDFRepositoryManager;
import uk.ac.bbk.dcs.l4all.util.RDFGraphUtil;

import com.hp.hpl.jena.rdf.model.Model;

public class DBStorageTest {

	public DBStorageTest() {

	}

	public static void main(String[] args) {
		//        UserProfileManager ru = new UserProfileManager();
		//        
		//        
		//// ru.setUsername("gpapam");
		//// ru.setEmail("gpapam@gmail.com");
		//// ru.setName("George Papamarkos");
		//// ru.setAge(26);
		////
		//// ru.createNewUserProfile();
		//        
		//        ru.dump();
		//// ru.setUserFields("gpapam");
		//// System.err.println("Email : " + ru.getEmail() );
		//// System.err.println("Password : " + ru.getPassword() );
		//// ru.setEmail("gpapa05@dcs.bbk.ac.uk");
		////
		//// ru.storeUserProfile("gpapam");

		UserTrailManager userTrail = new UserTrailManager();
//
//		Message msg = userTrail.deleteTrail("gpapam", "Test_Me2");
//		
//		    	Message msg2 = userTrail.addNewTrail("gpapam", "IT Application BKK" ,
//		 " This is a small description", 0, " annotation", "test");
////		    	userTrail.addContentToTrail("gpapam","Test_Me2","http://www.dcs.bbk.ac.uk/~hao",4);
//
//		Trail[] trails = userTrail.getUserTrails("gpapam");
//		
//		System.err.println(msg.toString());
////		LinkedList msg = userTrail.getTrailContents("gpapam", "Test_Me2");
//
////		System.err.println(msg.getErrorDescription());
		
		Model model = RDFRepositoryManager.getModel("l4all_users");
//		QueryResults results = RDFGraphUtil.getBagContents("http://www.dcs.bbk.ac.uk/~gpapa05/l4all/users/ap/Trails", model);
		
		String trailsURI = "http://www.dcs.bbk.ac.uk/~gpapa05/l4all/users/gpapam/Trails";
		
//		UserTrailManager tm = new UserTrailManager();
//		Object[] trails =  tm.getUserTrails("ap");
//		
//		
//		for (int i=0 ; i<trails.length ; i++) {
//			String trailName = ((Trail)trails[i]).getTrailName();
//			
//			System.err.println("Trail Name : " + trailName );
//		}
		
//		String trailURI = "http://www.dcs.bbk.ac.uk/~gpapa05/l4all/users/ap/Trails/" + "My_personal_Trail" ;
		
//		UserRepositoryManager.removeBagMember(trailsURI, trailURI, model);
		
//		for (Iterator iter = results ; iter.hasNext();) {
//			ResultBinding res = (ResultBinding)iter.next();
//			
//			Object x = res.get("x");
//			Object y = res.get("y");
//			System.err.println("= Property : " + x  + "\tTrail: " + y) ;
//		}
	
//		Vector trailNames = RDFGraphUtil.searchByName(trailsURI, "demo", model);
//		
//		trailNames.addAll(RDFGraphUtil.searchByKeyword(trailsURI, "demo", model));
//		trailNames.addAll(RDFGraphUtil.searchByDescription(trailsURI, "demo", model));
//		
//		for (int i=0; i < trailNames.size(); i++) {
//			System.out.println((String)trailNames.get(i));
//		}
//		
//		System.out.println("=========== after =============");
//		
//		trailNames = RDFGraphUtil.eliminateDuplicates(trailNames);
//		
//		for (int i=0; i < trailNames.size(); i++) {
//			System.out.println((String)trailNames.get(i));
//			String [] trailNamesi = ((String)trailNames.get(i)).split(":-:");
//			
//			System.out.println(trailNamesi[0]);
//			System.out.println(trailNamesi[1]);
//		}
		
		
		
//		RDFGraphUtil.printQueryResults(results, System.out);
		
//		Resource trailResource = model.getResource("http://www.dcs.bbk.ac.uk/~gpapa05/l4all/users/gpapam/Trails");
//		
//		for (StmtIterator sIter = trailResource.listProperties(); sIter.hasNext();) {
//			Statement s = (Statement) sIter.next();
//			System.out.println("    Predicate : " + s.getPredicate());
////			trailResource.removeAll(s.getPredicate());
//		}
		
//		 userTrail.dump();
	}
}