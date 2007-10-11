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
 * Created on Jun 10, 2005 by George Papamarkos
 *
 */
package uk.ac.bbk.dcs.l4all.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

import com.hp.hpl.jena.db.DBConnection;
import com.hp.hpl.jena.db.IDBConnection;
import com.hp.hpl.jena.db.ModelRDB;
import com.hp.hpl.jena.shared.AlreadyExistsException;

public class LoadRDFSchemas {
    
    // / MySQL Connection Details
    // Load the Driver
    static String className = ""; // path of driver class
    
    static String DB_URL = ""; // URL of database server
    
    static String DB_USER = ""; // database user id
    
    static String DB_PASSWD = ""; // database password
    
    static String DB = "MySQL"; // database type
    
    String dbname = "";
    
    static {
        Properties dbproperties = new Properties();
        try {
            dbproperties.load(new FileInputStream("conf/mysql.conf"));
            
            className = (String) dbproperties.get("driver");
            DB_URL = (String) dbproperties.get("url");
            DB_USER = (String) dbproperties.get("username");
            DB_PASSWD = (String) dbproperties.get("password");
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public LoadRDFSchemas(String dbname) {
        
        if (dbname.equals("l4all_users") || dbname.equals("l4all_courses"))
            this.dbname = dbname;
        else {
            System.err.println("No such database : \"" + dbname + "\"");
            System.exit(0);
        }
        
    }
    
    public IDBConnection connectToDB() {
        
        try {
            Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        // Create database connection
        return new DBConnection(DB_URL + dbname, DB_USER, DB_PASSWD, DB);
        
    }
    
    public void createModel(IDBConnection conn, String modelName) {
        // Create a model in the database with name the name of the relational
        // database schema
        ModelRDB m = ModelRDB.createModel(conn, modelName);
        
        if (modelName.equals("l4all_users")) {
//			m.setNsPrefix("ims-lip",
//					"http://www.dcs.bbk.ac.uk/~gpapa05/l4all/rdf/ims-lip#");
//			m.setNsPrefix("l4all-ext",
//					"http://www.dcs.bbk.ac.uk/~gpapa05/l4all/rdf/l4all-ext#");
            
            m.setNsPrefix("l4all-user",
                    "http://www.dcs.bbk.ac.uk/~gpapa05/l4all/rdf/l4all-user#");
            m.setNsPrefix("trails",
                    "http://www.dcs.bbk.ac.uk/~gpapa05/l4all/rdf/trails#");
            m.setNsPrefix("episode",
            "http://www.dcs.bbk.ac.uk/~gpapa05/l4all/rdf/episode#");
            
            
        } else if (modelName.equals("l4all_courses")) {
            m.setNsPrefix("dc-ext",
                    "http://www.dcs.bbk.ac.uk/~gpapa05/l4all/rdf/dc-ext#");
            m.setNsPrefix("dc",
                    "http://purl.org/dc/elements/1.1/");
        }
        
        m.write(System.out);
        ((ModelRDB) m).setDoDuplicateCheck(true);
    }
    
    public void removeModel(IDBConnection conn, String modelName) {
        try {
            ModelRDB m = null;
            if ((m = ModelRDB.open(conn, dbname)) != null)
                m.remove();
            else
                System.err.println("The model '" + modelName
                        + "'  does not exist");
            
        } catch (AlreadyExistsException e) {
            
        }
    }
    
    public void cleanModel(IDBConnection conn, String modelName) {
        
        ModelRDB m = null;
        if ((m = ModelRDB.open(conn, dbname)) != null)
			try {
				conn.cleanDB();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		else
            System.err.println("The model '" + modelName + "'  does not exist");
        
    }
    
    public static void main(String[] args) {
        
        if (args.length < 1) {
            System.out.println("Usage: java LoadRDFSchemata <dbname> <action> \n"
                    + "       'action' can be remove or create or clean or show\n"
                    + "       E.g. java LoadRDFSchemata <dbname> remove \n"
                    + "       Available database names : 'l4all_users' and 'l4all_courses'");
            
            System.exit(0);
        }
        
        LoadRDFSchemas loadRDF = new LoadRDFSchemas(args[0]);
           IDBConnection conn = loadRDF.connectToDB();
        
        if ("remove".equalsIgnoreCase(args[1]))
            loadRDF.removeModel(conn, loadRDF.dbname);
        else if ("create".equalsIgnoreCase(args[1]))
            loadRDF.createModel(conn, loadRDF.dbname);
        else if ("clean".equalsIgnoreCase(args[1]))
            loadRDF.cleanModel(conn,loadRDF.dbname );
        else if ("show".equalsIgnoreCase(args[1])){
        	ModelRDB m = ModelRDB.open(conn,loadRDF.dbname);
        	m.write(System.out);
        }
        	
    }}
