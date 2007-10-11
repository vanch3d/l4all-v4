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

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;

/**
 * @author George Papamarkos
 * 
 * SimpleDBPooling.java uk.ac.bbk.dcs.l4all.db
 */
public class SimpleDBPooling {
	static String className = "com.mysql.jdbc.Driver"; // path of driver class

	static String DB_URL = "jdbc:mysql://localhost:3306/"; // URL of database
														   // server

	static String DB_USER = "l4all"; // database user id

	static String DB_PASSWD = "Ari8mos7"; // database password

	static String DB = "MySQL"; // database type

	static DataSource l4allCoursesDataSource = null;

	static DataSource l4allUsersDataSource = null;

	static Connection l4allCoursesDBConn = null;

	static Connection l4allUsersDBConn = null;

	private static DataSource setupDataSource(String dbname) {
		System.out.println("Ready to initiate another DataSource");
		BasicDataSource ds = new BasicDataSource();
		ds.setDriverClassName(className);
		ds.setUsername(DB_USER);
		ds.setPassword(DB_PASSWD);
		ds.setUrl(DB_URL + dbname);
		return ds;
	}

	private void printDataSourceStats(DataSource ds) throws SQLException {
		BasicDataSource bds = (BasicDataSource) ds;
		System.out.println("NumActive: " + bds.getNumActive());
		System.out.println("NumIdle: " + bds.getNumIdle());
	}

	private void shutdownDataSource(DataSource ds) throws SQLException {
		BasicDataSource bds = (BasicDataSource) ds;
		bds.close();
	}

	public static Connection getDbConnection(String dbname) {
		Connection conn = null;
		try {
			if (dbname.equals("l4all_users")) {
				if (l4allUsersDataSource == null) {
					l4allUsersDataSource = setupDataSource(dbname);
					l4allUsersDBConn = l4allUsersDataSource.getConnection();
					return l4allUsersDBConn;
				} else
					return l4allUsersDBConn;
			}

			if (dbname.equals("l4all_courses")) {
				if (l4allCoursesDataSource == null) {
					l4allCoursesDataSource = setupDataSource(dbname);
					l4allCoursesDBConn = l4allCoursesDataSource.getConnection();
					return l4allCoursesDBConn ;
				} else
					return l4allCoursesDBConn;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
}
