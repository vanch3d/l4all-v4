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




package uk.ac.bbk.dcs.l4all.etools.delta;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Vector;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;




/**
 * @author Catherine Fung KarSin by Chimera, University of Essex
 * <p>
 * @version 2.0
 * <p>
 * @since 11/03/2005
 * <p>
 * Delta web service client that invoke the remote delta web service through axis interface
 * <p>
 *
 *
 */
public class DELTASearchClient {

	private uk.ac.bbk.dcs.l4all.etools.delta.ws.Delta deltaService;
	private String id;

	/**
	 * Delta client constructor initialises delta web service servant,
	 * each client instance has a session delta servant with id, which is
	 * the member id initial ""
	 *
	 */
	public DELTASearchClient(){
		uk.ac.bbk.dcs.l4all.etools.delta.ws.DeltaService service =
		    new uk.ac.bbk.dcs.l4all.etools.delta.ws.DeltaServiceLocator();
		((uk.ac.bbk.dcs.l4all.etools.delta.ws.DeltaServiceLocator)service).setMaintainSession(true);
		System.out.println("service got");
		id ="";
		try{
		deltaService = service.getdelta();
		System.out.println("instance got");
		}catch(Exception e){e.printStackTrace();}
	}

	/**
	 * @param chosen	concat string of options chosen in the web interface wizard with the step at the end
	 * 					e.g. "hasGenericLearningActivities+GatherFacts"
	 * @return			array of possible choice for next step with step at the beginning of array
	 * 					e.g. {"Learner Directed", "Learner Defined", "Learner Feedback", "hasDesirableRoles"}
	 */
	public Object[] searchByWizard(String chosen, String relationship){
		Object[] result =null;
		try{
				result = deltaService.searchByWizard(chosen, relationship);
		}catch(Exception e){e.printStackTrace();}
		return result;
	}

	public Object[] firstStepSearch(){
		Object[] result = null;
		try{
				result = deltaService.searchByWizard();

		}catch(Exception e){e.printStackTrace();}
		return result;
	}


	/**
	 * @param keywords	concat string of keywords for free text search with delimiter "+"
	 * 					e.g. "Learner Directed+physics"
	 * @param level		level of the e-learn resource e.g. "Higher Education"
	 * @param type		type of the e-learn resource e.g. "Case Study"
	 * @return			array of e-learn resources, each resource object return as a hashtable with all
	 * 					meta-data e.g. {{subject=maths, type=Case Study}, {subject=physics, type=Case Study}}
	 *
	 */
	public Object[] freeSearch(String keywords, String level, String type){
		Object[] result = null;
		try{
			result = deltaService.freeSearch(keywords, level, type);
			}catch(Exception e){e.printStackTrace();}
			return result;
	}
	
	/**
	 * @param resValues			concat String of metadata and its search values with delimiter '+'
	 * 							e.g. "dc+subject+physics+dc+language+english"
	 * @param deltaCreator		member name who created the e-learn resource
	 * @param dateOption		lowest bound of the date the resource created four levels provided
	 * 							0 - Any, no date constraint,
	 * 							1 - last week,
	 * 							2 - last month,
	 * 							3 - last 6 months
	 * @param annContext		annotation about the resource
	 * @param rating			lowest bound of resource average rating
	 * @return					array of e-learn resource, each in hashtable type
	 * 							e.g. {{subject=maths, type=Case Study  ..}, {subject=physics, type=Case Study   ..}}
	 */
	public Object[] advancedSearch(String resValues, String deltaCreator, int dateOption,
			String annContext,String annEntity, double rating){
		Object[] result = null;
		try{
		Date date = new Date();

		GregorianCalendar calendar = new GregorianCalendar();

		switch(dateOption){
		case 0: //any
			break;
		case 1: //a week
			calendar.add(GregorianCalendar.DAY_OF_MONTH, -7);
			date= calendar.getTime();
			break;
		case 2: //1 month
			calendar.add(GregorianCalendar.MONTH, -1);
			date= calendar.getTime();
			break;
		case 3: // 6 months
			calendar.add(GregorianCalendar.MONTH, -6);
			date= calendar.getTime();
			break;
		default:
			break;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String dateS = sdf.format(date);
			result = deltaService.advancedSearch(resValues, deltaCreator, dateS, annContext, annEntity, rating);
			
			}catch(Exception e){}
			return result;
	}
	/**
	 * @param input		new e-learn resource metadata in vector e.g.
	 * 					[uri, classification, restype, title, subject, description, creator, contributor, publisher, language, role, context, age];
	 * @return			true if the resource is created, false otherwise
	 */
	public boolean createResource(Vector input){
		boolean isCreated = false;
		try{
			String uri = input.elementAt(0).toString().replaceAll("\\s", "%20");
			// creator, date, contributor, publisher, language, rights, role, context, age


		//	System.out.println("uri : "+ uri);
			isCreated = deltaService.createELearnResource(uri,
					input.elementAt(1).toString(),
					input.elementAt(2).toString(),
					id,
					input.elementAt(3).toString(),
					input.elementAt(4).toString(),
					input.elementAt(5).toString(),
					input.elementAt(6).toString(),
					input.elementAt(7).toString()
					);

		}catch(Exception e){e.printStackTrace();}
		return isCreated;
	}
	/**
	 * @param olduri		the current URI of the e-learn resource
	 * @param newuri		the new URI of the e-learn resource
	 * @param metadata		concat string of metadata that may need to change e.g.
	 * 						"dc+title+physic"
	 * @return				true if the resource is edited, false otherwise
	 */
	public boolean editResource(String olduri, String newuri, String metadata){
		boolean isEdited = false;
		try{
			isEdited = deltaService.editELearnResource(olduri, newuri, metadata);
		}catch(Exception e){e.printStackTrace();}
		return isEdited;
	}


	/**
	 * @param uri		The URI of e-learn resource
	 * @return			true if the resource is deleted, false otherwise
	 */
	public boolean deleteResource(String uri){
		boolean isDeleted = false;
		try{
			isDeleted = deltaService.deleteELearnResource(uri);
		}catch(Exception e){e.printStackTrace();}
		return isDeleted;
	}


	/**
	 * @param email			the email of the member
	 * @param name			the concat string of member name e.g. "prefix+firstname+given"
	 * @param adr			the concat string of member address e.g. "extadd+street+district+region+pcode+country"
	 * @param metadata		the concat string of member metadata e.g.
	 * 						"Org+org.input+Title+title.input+Role+role.input+Tel+tel.input+
							Url+url.input+Bday+bdate.input"
	 * @param password		the password of the member
	 * @return				true if the member is register, false otherwise
	 */
	public boolean register(String email, String name, String adr,
			String metadata, String password){
		boolean isRegister = false;
		try{
			isRegister = deltaService.createMember(email, name, adr, metadata, password);
		}catch(Exception e){e.printStackTrace();}

	return isRegister;
	}


	/**
	 * @param metadata		concat string of metadata of the member that has to be edited
	 * 						e.g. "Family+Fung+Given+Catherine"
	 * @return				true if the member details is updated, false otherwise
	 */

	public boolean editMember(String metadata){
		boolean isEdited = false;
		try{
			isEdited = deltaService.editMember(id, metadata);
		}catch(Exception e){e.printStackTrace();}
		return isEdited;
	}

	/**
	 * @param memberID	the memberID (email) of the member
	 * @return			true if the member is deleted, false otherwise
	 */
	public boolean deleteMember(String memberID){
		boolean isDeleted = false;
		try{
			isDeleted = deltaService.deleteMember(memberID);
		}catch(Exception e){e.printStackTrace();}
		return isDeleted;
	}


	/**
	 * @param uri		the URI of the resource being annotated
	 * @param context	the keywords about the annotation can be searched on
	 * @param body		the annotation content
	 * @return			true if the annotation is created, false otherwise
	 */
	public boolean annotate(String uri, String context, String body){
		boolean isAnnotated = false;
		try{
			isAnnotated = deltaService.createAnnotation(uri, context, id, body);
		}catch(Exception e){e.printStackTrace();}
		return isAnnotated;
	}



	/**
	 * @param uri	the URI of the resource being bookmarked
	 * @return		true if the resource being added to the bookmark, false otherwise
	 */
	public boolean bookmark(String uri){
		boolean isAdded = false;
		try{
			isAdded = deltaService.addFavorite(id, uri);
		}catch(Exception e){e.printStackTrace();}
		return isAdded;
	}



	/**
	 * @param uri	The URI of the resource being deleted
	 * @return		true if the resource is deleted, false otherwise
	 */
	public boolean deleteBookmark(String uri){
		boolean isDeleted = false;
		try{
			isDeleted =deltaService.deleteFavorite(id, uri);
		}catch(Exception e){e.printStackTrace();}
		return isDeleted;
	}



	/**
	 * @param cst	the resource type that has the pedagogical approach e.g. "CaseStudyType1"
	 * @return		String of pedagogical definition of that resource type
	 */
	public String getDefinition(String cst){
		try{
		return deltaService.getPedagogicalDefinition(cst);

		}catch(Exception e){e.printStackTrace();}
		return null;
	}



	/**
	 * @param resourceURI	the resource URI that has annotations
	 * @return				array of annotation, each annotation represented in hashtable object
	 * 						e.g. {{Annotates=http://www.resource1, Body=This is a annotation .. }, {..}, ..}
	 */

	public Object[] getAnnotations(String resourceURI){
		Object[] result =null;
		Vector vec = new Vector();
		try{
			result = deltaService.getAnnotationsByResource(resourceURI);

			for( int i = 0 ; i< result.length ; i ++)

				System.out.println(result[i]);
			/*	String hashtable = result[i].toString();
				int start = hashtable.indexOf("URI=");
				int end = hashtable.indexOf(',', start);
				String uri = hashtable.substring(start+4, end);
				System.out.println(uri);*/

				//vec.addElement(d.getAnnotationsByResource(uri));
			//		}vec.addElement(result);
		}catch(Exception e){e.printStackTrace();}
		return result;
	}



	/**
	 * @return	member metadata in hashtable object e.g.
	 * 			{Family=Fung, Given=Catherine ..}
	 */
	public Object getMemberDetails(){
		Object details = null;
		try{
			details = deltaService.getMemberDetails(id);
		}catch(Exception e){e.printStackTrace();}
		return details;
	}



	/**
	 * @return	array of five most recent e-learn resource objects,
	 * 			sorted by average rating and creation date in descending order
	 */
	public Object[] listMostRecentByResDate(){
		Object[] result = null;
		try{
			result = deltaService.listMostRecentDC();
		}catch(Exception e){e.printStackTrace();}
		return result;
	}

	public Object[] listMostRecentByMDDate(){
			Object[] result = null;
			try{
				result = deltaService.listMostRecentLOMMETA();
			}catch(Exception e){e.printStackTrace();}
			return result;
	}



	/**
	 * @return	array of five highest rate e-learn resource objects with
	 * 			lowest bound 3.0 average rating
	 * 			sorted by average rating and creation date in descending order
	 */
	public Object[] listHighestRate(){
		Object[] result = null;
		try{
			result = deltaService.listHighestRate();
		}catch(Exception e){e.printStackTrace();}
		return result;
	}



	/**
	 * @return	array of e-learn resource created by a member
	 */
	public Object[] listMemberResources(){
		Object[] result = null;
		try{
			result = deltaService.listMemberResources(id);
			}catch(Exception e){e.printStackTrace();}
		return result;
	}



	/**
	 * @return	array of e-learn resource, which are favorites of a member
	 */
	public Object[] listFavorite(){
		Object[] result = null;
		try{
			result = deltaService.listFavorite(id);
		}catch(Exception e){e.printStackTrace();}
		return result;
	}



	/**
	 * @param memberID	email of a registered member
	 * @param password	password of the registered member
	 * @return		true if login successfully, false otherwise
	 */
	public boolean login(String memberID, String password){
		try{
		id = deltaService.login(memberID, password);
		//System.out.println("id : "+ id);
		if(id.equals("invalid"))
			return false;
		}catch(Exception e){e.printStackTrace();}
		return true;
	}



	/**
	 * @param resourceURI	URI of the resource being rated
	 * @param rating		the new rating being added, there are 5 level
	 * 						1- very useful, 2- useful, 3- no comment, 4- not really useful, 5- not useful at all
	 * @return				the new average rating of the e-learn resource
	 */
	public double rating(String resourceURI, int rating){
		try{
			return deltaService.addRating(resourceURI, rating);
		}catch(Exception e){e.printStackTrace();}
		return 0.0;
	}



	/**
	 * @param email		the email of a registered member
	 * @return			true if a password reminder is sent to the member email
	 */
	public boolean passwordReminder(String email){
		try {
			System.out.println(email);
			String pw = deltaService.getPassword(email);
			// Ask the user for the from, to, and subject lines
			String from = "kscfun@essex.ac.uk";
			String to = email;
			String subject = "Delta system password reminder";
			// Read message line by line and send it out.
			String body="Thank you for using Delta e-learning resource sharing system.\n" +
					"Here is your login information: \n" +
					"	login name: "+ id +"\n"+
					"	password  : " +pw + "\n" +
					"\n\nRegards\nDelta System Administrator";
			String mailHost = "smtp.essex.ac.uk";

			 //Get system properties
            Properties props = System.getProperties();

            //Specify the desired SMTP server
            props.put("mail.smtp.host", mailHost);

            // create a new Session object
            Session session = Session.getInstance(props,null);

            // create a new MimeMessage object (using the Session created above)
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO, new InternetAddress[] { new InternetAddress(to) });
            message.setSubject(subject);
            message.setContent(body, "text/plain");

            Transport.send(message);

			// Tell the user it was successfully sent.
			//System.out.println("Message sent.");
			return true;
		}
		catch (Exception e) {  // Handle any exceptions, print error message.
			System.err.println(e);
			System.err.println("Usage: java SendMail [<mailhost>]");
			return false;
		}
	}

}



