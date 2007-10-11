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
 * Created on 08-Jul-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package uk.ac.bbk.dcs.l4all.tests;

import java.util.HashMap;

import uk.ac.bbk.dcs.l4all.etools.delta.DELTASearchClient;

/**
 * @author george
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DELTASearchTester {

	public static void main(String [] args) {
		
		DELTASearchClient deltaClient = new DELTASearchClient();
		
		Object[] searchResults = deltaClient.freeSearch("IT", "", "");
		
		HashMap result = new HashMap();
		if (searchResults.length > 0) {
			for (int i=0; i< searchResults.length ; i++) {
				System.out.println("Result [" + i + "] :: " + searchResults[i].toString());
				
//				if (searchResults[i] instanceof HashMap) {
//					result = (HashMap)searchResults[i];
//					System.out.println(result.get("title"));
//					System.out.println(result.get("type"));
//					System.out.println(result.get("rights"));
//					System.out.println(result.get("=============="));
//				}
					
			}
		}
		
	}
}
