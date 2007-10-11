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
 * Created on 15-Jul-2005
 *
 * uk.ac.bbk.dcs.l4all.util in l4all
 *
 */
package uk.ac.bbk.dcs.l4all.util;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import com.hp.hpl.jena.rdf.model.Bag;
import com.hp.hpl.jena.rdf.model.Container;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.NodeIterator;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Seq;
import com.hp.hpl.jena.rdql.Query;
import com.hp.hpl.jena.rdql.QueryEngine;
import com.hp.hpl.jena.rdql.QueryExecution;
import com.hp.hpl.jena.rdql.QueryResults;
import com.hp.hpl.jena.rdql.QueryResultsFormatter;
import com.hp.hpl.jena.rdql.ResultBinding;

/**
 * @author George Papamarkos
 * 
 * RDFGraphUtil.java uk.ac.bbk.dcs.l4all.util
 */
public class RDFGraphUtil {

	/**
	 * Returns the size of an RDF container (Bag, Seq or Alt)
	 * 
	 * @param container
	 *            The RDF container (Bag, Seq or Alt) the size of which we want
	 *            to get
	 * @return The size of the container specified
	 */
	public static int getContainerSize(Container container) {
		int i = 0;

		NodeIterator containerIterator = container.iterator();

		while (containerIterator.hasNext()) {
			containerIterator.next();
			++i;
		}

		return i;
	}

	/**
	 * Returns the size of an RDF Bag
	 * 
	 * @param bag
	 *            The RDF bag we want to find the size of
	 * @return The size of the bag
	 */
	public static int getBagSize(Bag bag) {
		return getContainerSize(bag);
	}

	/**
	 * Returns the size of an RDF sequence
	 * 
	 * @param seq
	 *            The sequence we want to get the size of
	 * @return The size of the sequence specified
	 */
	public static int getSeqSize(Seq seq) {
		return getContainerSize(seq);
	}

	/**
	 * 
	 * @param resourceURI
	 * @param model
	 * @return
	 */
	public static boolean hasResource(String resourceURI, Model model) {
		String queryString = "Select ?property where (<" + resourceURI
				+ ">, ?property, ?object)";
		QueryResults results = Query.exec(queryString, model);
		return ((Iterator) results).hasNext();
	}

	/**
	 * Retrieves the contents of an RDF bag
	 * 
	 * @param uri
	 * @param model
	 * @return
	 */
	public static QueryResults getBagContents(String uri, Model model) {
		String queryString = "SELECT ?x, ?y WHERE (<" + uri
				+ ">, ?x, ?y) AND ! (?x eq rdf:type && ?y eq rdf:Bag)";
		Query query = new Query(queryString);
		query.setSource(model);
		QueryExecution qe = new QueryEngine(query);

		QueryResults results = qe.exec();

		return results;
	}

	/**
	 * Retrieves the contents of an RDF bag
	 * 
	 * @param uri
	 * @param model
	 * @return
	 */
	public static QueryResults getSeqContents(String uri, Model model) {
		String queryString = "SELECT ?x, ?y WHERE (<" + uri
				+ ">, ?x, ?y) AND ! (?x eq rdf:type && ?y eq rdf:Seq)";
		Query query = new Query(queryString);
		query.setSource(model);
		QueryExecution qe = new QueryEngine(query);

		QueryResults results = qe.exec();

		return results;
	}

	public static void removeSeqContents(Seq seq) {

		int size = getSeqSize(seq);

		for (int i = 0; i <= size; i++)
			seq.remove(i);
		// Iterator iter = seq.iterator();
		// int i=0;
		//    	
		// while (iter.hasNext()) {
		// seq.remove(i++);
		// }

		return;
	}

	// ///////////////// Course Search methods ////////////////////////////////

	/**
	 * Searchs for courses according to the passed criteria.
	 * 
	 * @param courseSubject
	 *            The subject of the course
	 * @param courseLevel
	 *            The level of the course
	 * @param courseStartDate
	 *            The starting date of the course
	 * @param courseTypeOfStudy
	 *            The type of study of the course
	 * @param model
	 *            The RDF Model to perform the search on
	 * @return The results of the search
	 */
	public static QueryResults advancedSearchCourses(String coursesURI,
			String courseSubject, String courseLevel, String courseStartDate,
			String courseTypeOfStudy, Model model) {

		String queryString = "SELECT ?courseCode , ?title, ?description, ?publisher "
				+ " WHERE (?y dc:subject ?subject) "
				+ " (?y  dc:publisher ?publisher)"
				+ " (?y  dc:coverage ?coverage)"
				+ " (?y  dc:date ?date)"
				+ " (?y  dc:description ?description)"
				+ " (?y  dc:title ?title)"
				+ " (?y  dc-ext:courseType ?courseType) "
				+ " (?y  dc:identifier ?courseCode)"
				+ " and (?subject =~ /"
				+ courseSubject
				+ "/i "
				+ " || ?coverage =~ /"
				+ courseLevel
				+ "/i "
				+ " || ?date =~ /"
				+ courseStartDate
				+ "/i "
				+ " || ?courseType =~ /"
				+ courseTypeOfStudy
				+ "/i ) "
				+ "USING dc-ext FOR <http://www.dcs.bbk.ac.uk/~gpapa05/l4all/rdf/dc-ext#>,"
				+ "      dc FOR   <http://purl.org/dc/elements/1.1/>";

		Query query = new Query(queryString);
		query.setSource(model);
		QueryExecution qe = new QueryEngine(query);

		QueryResults results = qe.exec();
		results.getResultVars();
		return results;
	}

	/**
	 * It performs a keyword-based search on courses
	 * 
	 * @param coursesURI
	 * @param keywords
	 * @param model
	 * @return
	 */
	public static QueryResults[] quickSearchCourses(String coursesURI,
			String[] keywords, Model model) {

		QueryResults[] queryResults = null;

		for (int i = 0; i < keywords.length; i++) {

			String queryString = "SELECT ?courseCode , ?title, ?description, ?publisher "
					+ "WHERE (?y dc:subject ?subject), "
					+ " (?y  dc:publisher ?publisher)"
					+ " (?y  dc:description ?description)"
					+ " (?y  dc:title ?title)"
					+ " (?y  dc:identifier ?courseCode)"
					+ " and (?subject =~ /"
					+ keywords[i]
					+ "/i "
					+ " || ?description =~ /"
					+ keywords[i]
					+ "/i"
					+ " || ?title =~ /"
					+ keywords[i]
					+ "/i"
					+ " || ?publisher =~ /"
					+ keywords[i]
					+ "/i )"
					+ "USING dc-ext FOR <http://www.dcs.bbk.ac.uk/~gpapa05/l4all/rdf/dc-ext#>,"
					+ "      dc FOR   <http://purl.org/dc/elements/1.1/>";

			queryResults[i] = execQuery(queryString, model);
		}

		return queryResults;
	}

	// /////////////////// User Search Functions ////////////////////////////

	/**
	 * Search on the user database for similar users
	 * 
	 * @param userURI
	 * @param age
	 * @param interest
	 * @param goals
	 * @param qual
	 * @return
	 */
	public static QueryResults advancedSearchUsers(String userURI, String age,
			String interest, String goals, String qual, Model model) {
		String queryString = "SELECT ?name, ?age, ?interests, ?goals, ?qual "
				+ " WHERE (<"
				+ userURI
				+ "> ?prop ?y),"
				+ "	   (?y, ims-lip:interest_typename, ?interests) "
				+ "      (?y ims-lip:goal_typename ?goals)"
				+ "      (?y ims-lip:qcl_level ?qual)"
				+ "	   (?y l4all-ext:age ?age)"
				+ "	   (?y ims-lip:name ?name) "
				+ " AND ( ?age eq \""
				+ age
				+ "\" || ?interests =~ /"
				+ interest
				+ "/i "
				+ "       || ?goals =~ /"
				+ goals
				+ "/i "
				+ "		|| ?qual =~ /"
				+ qual
				+ "/i "
				+ ") "
				+ "USING l4all-ext FOR <http://www.dcs.bbk.ac.uk/~gpapa05/l4all/rdf/l4all-ext#>, "
				+ "	   ims-lip FOR <http://www.dcs.bbk.ac.uk/~gpapa05/l4all/rdf/ims-lip#>";

		Query query = new Query(queryString);
		query.setSource(model);
		QueryExecution qe = new QueryEngine(query);

		QueryResults results = qe.exec();

		return results;

	}

	/**
	 * Quick search for users
	 * 
	 * @param userURI
	 * @param keywords
	 * @param model
	 * @return
	 */
	public static QueryResults[] quickSearchUsers(String userURI,
			String[] keywords, Model model) {

		QueryResults[] queryResults = new QueryResults[keywords.length];

		for (int i = 0; i < keywords.length; i++) {
			queryResults[i] = advancedSearchUsers(userURI, keywords[i],
					keywords[i], keywords[i], keywords[i], model);
		}

		return queryResults;
	}

	// /////////////// Utility methods //////////////////////

	/**
	 * 
	 * @param bagUri
	 * @param memberUri
	 * @param model
	 * @return
	 */
	public static QueryResults getBagMembershipProperty(String bagUri,
			String memberUri, Model model) {
		String queryString = "SELECT ?prop WHERE (<" + bagUri + ">, ?prop, <"
				+ memberUri + ">) AND ! (?prop eq rdf:type)";
		return execQuery(queryString, model);
	}

	/**
	 * 
	 * @param queryResults
	 * @param out
	 */
	public static void printQueryResults(QueryResults queryResults,
			OutputStream out) {
		QueryResultsFormatter fmt = new QueryResultsFormatter(queryResults);
		PrintWriter pw = new PrintWriter(out);
		fmt.printAll(pw, "|");
		pw.flush();
		fmt.close();
		queryResults.close();

		return;
	}

	/**
	 * 
	 * @param queryResults
	 * @return
	 */
	public static Vector eliminateDuplicates(Vector queryResults) {

		for (int i = 0; i < queryResults.size(); i++) {
			String value = (String) queryResults.get(i);
			for (int j = i; j < queryResults.size(); j++) {
				String duplicateValue = (String) queryResults.get(j);

				if (value.equals(duplicateValue))
					queryResults.remove(j);
			}
		}

		queryResults.trimToSize();

		return queryResults;
	}

	public static QueryResults execQuery(String queryString, Model model) {
		Query query = new Query(queryString);
		query.setSource(model);
		QueryExecution qe = new QueryEngine(query);

		QueryResults results = null;

		results = qe.exec();

		return results;
	}

	public static String[] getContainerStringContents(Container container) {
		List contents = new ArrayList();
		Seq seq = null;
		if (container instanceof Seq) {
			seq = (Seq) container;
			System.out.println(getSeqSize(seq));
			NodeIterator iter = container.iterator();

			if (iter.hasNext())
				while (iter.hasNext()) {
					Object obj = iter.next();
					String item = null;

					if (obj instanceof Literal) {
						item = ((Literal) obj).getString();
						contents.add(item);
					}
				}
		}

		return (String[]) contents.toArray(new String[contents.size()]);
	}

	public static Object[] getContainerObjectContents(Container container) {
		List contents = new ArrayList();
		NodeIterator iter = container.iterator();

		if (iter.hasNext())
			while (iter.hasNext()) {
				Object obj = iter.next();
				Object item = null;

				if (obj instanceof Literal) {
					item = ((Literal) obj);
					contents.add(item);
				} else if (obj instanceof Resource) {
					item = (Resource)obj;
					contents.add(item);
				}
			}

		return contents.toArray();
	}
}
