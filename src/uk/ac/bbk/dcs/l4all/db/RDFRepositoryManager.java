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
package uk.ac.bbk.dcs.l4all.db;

import java.util.Iterator;

import uk.ac.bbk.dcs.l4all.util.RDFGraphUtil;

import com.hp.hpl.jena.db.DBConnection;
import com.hp.hpl.jena.db.IDBConnection;
import com.hp.hpl.jena.db.ModelRDB;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.SimpleSelector;
import com.hp.hpl.jena.rdql.QueryResults;
import com.hp.hpl.jena.rdql.ResultBinding;

public class RDFRepositoryManager {

	/// MySQL Connection Details
	// Load the Driver
//	static String className = "com.mysql.jdbc.Driver"; // path of driver class
//
//	static String DB_URL = "jdbc:mysql://localhost:3306/"; // URL of database
//														   // server
//	static String DB_USER = "l4all"; // database user id
//	static String DB_PASSWD = "Ari8mos7"; // database password
//	static String DB = "MySQL"; // database type
	static String dbname = null;
	static DBConnection usersDBConn = null;
	static DBConnection coursesDBConn = null;

//	static {
//		connectToDatabases();
//	}
	
	private static void connectToDatabases() {
//		try {
//			Class.forName(className);
//		} catch (ClassNotFoundException e) {
//			e.printStackTrace();
//		}
//
//		if (usersDBConn == null)
//			usersDBConn = new DBConnection(DB_URL + "l4all_users", DB_USER,
//					DB_PASSWD, DB);
//
//		if (coursesDBConn == null)
//			coursesDBConn = new DBConnection(DB_URL + "l4all_courses", DB_USER,
//					DB_PASSWD, DB);

	}

	public static IDBConnection getDBConnection(String dbname) {

		if (dbname.equals("l4all_users")) {
			if (usersDBConn == null) 
				usersDBConn = new DBConnection(SimpleDBPooling.getDbConnection(dbname),"MySQL");
			
			return usersDBConn;
		}
		 
		if (dbname.equals("l4all_courses")) {
			if (coursesDBConn == null) 
				coursesDBConn = new DBConnection(SimpleDBPooling.getDbConnection(dbname),"MySQL");
			
			return coursesDBConn;
		}
		
		
		return null ;
//		if (dbname.equalsIgnoreCase("l4all_users"))
//			return usersDBConn;
//		
//		if (dbname.equalsIgnoreCase("l4all_courses"))
//			return coursesDBConn;

//		return null ;
	}

	public static ModelRDB getModel(String modelName) {
//		if (dbname == null)
//			return ModelRDB.open(getDBConnection(modelName), modelName);
//		else
			return ModelRDB.open(getDBConnection(modelName), modelName);
	}

	/**
	 * Delete a e-learn resource meta-data from the RDF repository model
	 * 
	 * @param uri
	 *            e-learn resource meta-data URI being deleted
	 * @return true if the resource meta-data is deleted, false otherwise.
	 *         Exception thrown in attempt to delete a non-exist e-learn
	 *         resource meta-data.
	 */
	public static boolean deleteResource(String uri, Model model) {
		Resource r = model.getResource(uri);
		model.remove(model.listStatements(new SimpleSelector(r, null,
				(RDFNode) null)));
		return true;
	}

	public static void removeBagMember(String bagUri, String memberUri,
			Model model) {
		Resource trailResource = model.getResource(bagUri);
		QueryResults results = RDFGraphUtil.getBagMembershipProperty(bagUri,
				memberUri, model);

		for (Iterator iter = results; iter.hasNext();) {
			ResultBinding res = (ResultBinding) iter.next();
			RDFNode membershipPropertyNode = (RDFNode) res.get("prop");
			Property membershipProperty = null;

			if (membershipPropertyNode.canAs(Property.class)) {
				membershipPropertyNode = membershipPropertyNode
						.as(Property.class);
				membershipProperty = (Property) membershipPropertyNode;
			}

			if (membershipProperty != null)
				trailResource.removeAll(membershipProperty);

			break;
		}

		return;
	}

	public static String getDbname() {
		return dbname;
	}

	public static void setDbname(String dbname) {
		RDFRepositoryManager.dbname = dbname;
	}
}
