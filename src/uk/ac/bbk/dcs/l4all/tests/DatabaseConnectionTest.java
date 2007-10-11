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

import com.hp.hpl.jena.db.ModelRDB;

import uk.ac.bbk.dcs.l4all.db.RDFRepositoryManager;
import uk.ac.bbk.dcs.l4all.db.SimpleDBPooling;

/**
 * @author George Papamarkos
 *
 * DatabaseConnectionTest.java
 * uk.ac.bbk.dcs.l4all.tests
 */
public class DatabaseConnectionTest {
	public static void main(String [] args) {
//		SimpleDBPooling [] sdb = new SimpleDBPooling[200] ;
//		for (int i=0 ; i < 200 ; i++) {
//			sdb[i] = new SimpleDBPooling();
//			sdb[i].getDbConnection("l4all_courses");
//		}
		
		ModelRDB [] model = new ModelRDB[200];
		ModelRDB [] model1 = new ModelRDB[200];
		
		for (int j=0; j < 200 ; j++) {
			model[j] = RDFRepositoryManager.getModel("l4all_courses");
			model1[j] = RDFRepositoryManager.getModel("l4all_users");
			System.out.println("[" + j + "]");
		}
	}
}
