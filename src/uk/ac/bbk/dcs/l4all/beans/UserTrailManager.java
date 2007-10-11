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
package uk.ac.bbk.dcs.l4all.beans;

import java.util.ArrayList;

import uk.ac.bbk.dcs.l4all.db.RDFRepositoryManager;
import uk.ac.bbk.dcs.l4all.util.RDFGraphUtil;
import uk.ac.bbk.dcs.l4all.vocabulary.Episode;
import uk.ac.bbk.dcs.l4all.vocabulary.L4all_user;
import uk.ac.bbk.dcs.l4all.vocabulary.Trails;

import com.hp.hpl.jena.db.ModelRDB;
import com.hp.hpl.jena.rdf.model.Bag;
import com.hp.hpl.jena.rdf.model.NodeIterator;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Seq;
import com.hp.hpl.jena.rdf.model.Statement;

/**
 * @author George Papamarkos
 * 
 * UserTrailManager.java uk.ac.bbk.dcs.l4all.beans
 */
public class UserTrailManager
{

    //////GLOBAL CONSTANTS //////////
    private static final String BASE_USER_URL = "http://www.dcs.bbk.ac.uk/~gpapa05/l4all/users/";

    private static final String MODEL_NAME = "l4all_users";

    ModelRDB model = RDFRepositoryManager.getModel(MODEL_NAME);

    /**
     * Adds a new trail to the user's trails
     *
     * @param username		The id of the user
     * @param trailName		The id of the trail
     * @param trailDescription	The description of the trail
     * @param trailType		The type of the trail (@see {@link Trails#TYPE_EXPERT})
     * @param trailPrivileges	The privilege of the trail (1 public, 0 private)
     * @param trailAnnotation	The annotations of the trail
     * @param trailKeywords	The keywords associated with the trail
     * @param trailStart	The start year of the trail
     * @param trailEnd		The end year of the trail
     * @return
     */
    public Message editTrail(String username, String trailName,
	    String trailDescription, String trailType, int trailPrivileges,
	    String trailAnnotation, String trailKeywords, int trailStart,
	    int trailEnd)
    {
	String urlTrailName = trailName.trim().replace(' ', '_');

	String USER_URL = BASE_USER_URL + username;
	String USER_TRAILS_URL = USER_URL + "/" + "LearningPrefs" + "/"
		+ "Trails";
	String USER_TRAIL_NAME_URL = USER_URL + "/" + "LearningPrefs" + "/"
		+ "Trails" + "/" + urlTrailName;

	if (!RDFGraphUtil.hasResource(USER_URL, model))
	    return new Message(ErrorCodes.INVALID_USERNAME_ERROR,
		    "I cannot edit a trail for a user that does not exist!!");

	if (!RDFGraphUtil.hasResource(USER_TRAIL_NAME_URL, model))
	    return new Message(ErrorCodes.INVALID_TRAIL_NAME_ERROR,
		    "The trail does not exist. Please Choose another trail name");

	Resource trails = model.getResource(USER_URL + "/" + "LearningPrefs");
	Bag user_trails = null;
	Statement learner_trails = trails.getProperty(L4all_user.user_trails);
	if (learner_trails == null)
	    user_trails = model.getBag(USER_TRAILS_URL);
	else
	    user_trails = learner_trails.getBag();

	NodeIterator trails_iterator = user_trails.iterator();

	Resource trail = null;
	while (trails_iterator.hasNext())
	{
	    trail = (Resource) trails_iterator.next();
	    String curTrailName = trail.getProperty(Trails.trail_name)
		    .getString();

	    if (curTrailName.trim().equals(trailName.trim()))
		break;
	}
	if (trailDescription != null && !trailDescription.equals(""))
	{
	    Statement stat = trail.getProperty(Trails.trail_description);
	    stat.changeObject(trailDescription);
	}

	//
	//	trail.addProperty(Trails.trail_id, String.valueOf(System.currentTimeMillis()));
	//	trail.addProperty(Trails.trail_name, trailName);
	//	trail.addProperty(Trails.trail_description, trailDescription);
	//	trail.addProperty(Trails.trail_type, trailType);
	//	trail.addProperty(Trails.trail_privileges, trailPrivileges);
	//	trail.addProperty(Trails.trail_annotation, trailAnnotation);
	//	trail.addProperty(Trails.trail_keywords, trailKeywords);
	//	trail.addProperty(Trails.trail_owner, username);
	//	trail.addProperty(Trails.trail_start, trailStart);
	//	trail.addProperty(Trails.trail_end, trailEnd);
	//	trail.addProperty(Trails.trail_episodes, model.createSeq());
	model.commit();

	return new Message(ErrorCodes.SUCCESS,
		"Successful Trail edition with name: " + trailName);
    }

    /**
     * Adds a new trail to the user's trails
     *
     * @param username		The id of the user
     * @param trailName		The id of the trail
     * @param trailDescription	The description of the trail
     * @param trailType		The type of the trail (@see {@link Trails#TYPE_EXPERT})
     * @param trailPrivileges	The privilege of the trail (1 public, 0 private)
     * @param trailAnnotation	The annotations of the trail
     * @param trailKeywords	The keywords associated with the trail
     * @param trailStart	The start year of the trail
     * @param trailEnd		The end year of the trail
     * @return
     */
    public Message addNewTrail(String username, String trailName,
	    String trailDescription, String trailType, int trailPrivileges,
	    String trailAnnotation, String trailKeywords, int trailStart,
	    int trailEnd)
    {
	String urlTrailName = trailName.trim().replace(' ', '_');

	String USER_URL = BASE_USER_URL + username;
	String USER_TRAILS_URL = USER_URL + "/" + "LearningPrefs" + "/"
		+ "Trails";
	String USER_TRAIL_NAME_URL = USER_URL + "/" + "LearningPrefs" + "/"
		+ "Trails" + "/" + urlTrailName;

	if (!RDFGraphUtil.hasResource(USER_URL, model))
	    return new Message(ErrorCodes.INVALID_USERNAME_ERROR,
		    "I cannot add a trail to a user that does not exist!!");

	if (RDFGraphUtil.hasResource(USER_TRAIL_NAME_URL, model))
	    return new Message(
		    ErrorCodes.UNIQUE_TRAIL_NAME_CONSTRAINT_VIOLATION_ERROR,
		    "Unique Trail Name contrainst violation. Please Choose another trail name");

	Resource trails = model.getResource(USER_URL + "/" + "LearningPrefs");

	Bag user_trails = null;
	Statement learner_trails = trails.getProperty(L4all_user.user_trails);

	if (learner_trails == null)
	    user_trails = model.getBag(USER_TRAILS_URL);
	else
	    user_trails = learner_trails.getBag();

	Resource trail = model
		.createResource(USER_TRAIL_NAME_URL, Trails.Trail);

	trail.addProperty(Trails.trail_id, String.valueOf(System
		.currentTimeMillis()));
	trail.addProperty(Trails.trail_name, trailName);
	trail.addProperty(Trails.trail_description, trailDescription);
	trail.addProperty(Trails.trail_type, trailType);
	trail.addProperty(Trails.trail_privileges, trailPrivileges);
	trail.addProperty(Trails.trail_annotation, trailAnnotation);
	trail.addProperty(Trails.trail_keywords, trailKeywords);
	trail.addProperty(Trails.trail_owner, username);
	trail.addProperty(Trails.trail_start, trailStart);
	trail.addProperty(Trails.trail_end, trailEnd);
	trail.addProperty(Trails.trail_episodes, model.createSeq());

	user_trails.add(trail);
	model.commit();

	return new Message(ErrorCodes.SUCCESS,
		"Successful Trail creation with name: " + trailName);
    }

    /**
     * Removes a trail from the user's trails
     * 
     * @param username		The id of the user
     * @param trailName		The id of the trail
     */
    public Message deleteTrail(String username, String trailName)
    {
	String urlTrailName = trailName.trim().replace(' ', '_');

	//String USER_URL = BASE_USER_URL + username;
	//String USER_TRAILS_URL = USER_URL + "/" + "Trails";
	//String USER_TRAIL_NAME_URL = USER_URL + "/" + "Trails" + "/" + urlTrailName;

	String USER_URL = BASE_USER_URL + username;
	String USER_TRAILS_URL = USER_URL + "/" + "LearningPrefs" + "/"
		+ "Trails";
	String USER_TRAIL_NAME_URL = USER_URL + "/" + "LearningPrefs" + "/"
		+ "Trails" + "/" + trailName;

	//Resource trail = null;

	if (!RDFGraphUtil.hasResource(USER_URL, model))
	    return new Message(ErrorCodes.INVALID_USERNAME_ERROR,
		    "I cannot add a trail to a user that does not exist!!");

	if (!RDFGraphUtil.hasResource(USER_TRAIL_NAME_URL, model))
	    return new Message(
		    ErrorCodes.INVALID_TRAIL_NAME_ERROR,
		    "The trail '"
			    + trailName
			    + "' does not exist. Please create it beforehand or choose another trail.");

	if (!RDFGraphUtil.hasResource(USER_URL, model))
	    return new Message(ErrorCodes.INVALID_USERNAME_ERROR,
		    "I cannot delete a trail from a user that does not exist!!");

	Resource trails = model.getResource(USER_URL + "/" + "LearningPrefs");
	Bag user_trails = null;
	Statement learner_trails = trails.getProperty(L4all_user.user_trails);
	if (learner_trails == null)
	    user_trails = model.getBag(USER_TRAILS_URL);
	else
	    user_trails = learner_trails.getBag();

	NodeIterator trails_iterator = user_trails.iterator();

	Resource delsrc = null;
	Statement trail = null;
	while (trails_iterator.hasNext())
	{
	    Resource item = (Resource) trails_iterator.next();
	    Statement temp = item.getProperty(Trails.trail_name);
	    String curTrailName = temp.getString();
	    if (curTrailName.trim().equals(trailName.trim()))
	    {
		trail = temp;
		delsrc = item;
		break;
	    }
	}

	int nBefore = -1, nAfter = -1;
	if (trail != null)
	{
	    nBefore = nAfter = user_trails.size();
	    user_trails.remove(trail);
	    //model.remove(trail);
	    //trail.removeReification();
	    //trail.remove();
	    nAfter = user_trails.size();
	    if (delsrc!=null) delsrc.removeProperties();
	    model.commit();
	}
	//RDFRepositoryManager.removeBagMember(USER_TRAILS_URL,USER_TRAIL_NAME_URL, model);
	if (nBefore == nAfter)
	    return new Message(ErrorCodes.UNKNOWN_ERROR, "The trail '"
		    + trailName + "' cannot be delete.");
	else
	    return new Message(ErrorCodes.SUCCESS, "Trail : " + trailName
		    + " deleted successfully");
    }

    /**
     * Add a new episode to the trail specified with trail name
     * 
     * @param username		The id of the user
     * @param trailName		The id of the trail
     * @param contentURL
     * @param contentPosition
     */
    public Message addEpisodeToTrail(String username, String trailName,
	    String episodeTitle, String episodeCategory, String episodeNature,
	    String episodeStartDate, String episodeEndDate, String episodeURL,
	    String episodeDescription, int episodePositionOnTrail,
	    String prim, String sec)
    {
	String URLTrailName = trailName.trim().replace(' ', '_');
	String episodeTitleWithNoSpaces = episodeTitle.trim().replace(' ', '_');

	String USER_URL = BASE_USER_URL + username;
	String USER_TRAILS_URL = USER_URL + "/" + "LearningPrefs" + "/"
		+ "Trails";
	String USER_TRAIL_NAME_URL = USER_URL + "/" + "LearningPrefs" + "/"
		+ "Trails" + "/" + URLTrailName;

	Resource trail = null;

	if (!RDFGraphUtil.hasResource(USER_URL, model))
	    return new Message(ErrorCodes.INVALID_USERNAME_ERROR,
		    "I cannot add a trail to a user that does not exist!!");

	if (!RDFGraphUtil.hasResource(USER_TRAIL_NAME_URL, model))
	    return new Message(
		    ErrorCodes.INVALID_TRAIL_NAME_ERROR,
		    "The trail '"
			    + trailName
			    + "' does not exist. Please create it beforehand or choose another trail.");

	Resource trails = model.getResource(USER_URL + "/" + "LearningPrefs");

	Bag user_trails = null;
	Statement learner_trails = trails.getProperty(L4all_user.user_trails);
	if (learner_trails == null)
	    user_trails = model.getBag(USER_TRAILS_URL);
	else
	    user_trails = learner_trails.getBag();

	NodeIterator trails_iterator = user_trails.iterator();

	while (trails_iterator.hasNext())
	{
	    trail = (Resource) trails_iterator.next();
	    String curTrailName = trail.getProperty(Trails.trail_name)
		    .getString();

	    if (curTrailName.trim().equals(trailName.trim()))
		break;
	}

	Seq trail_contents_seq = null;
	Statement trail_contents = trail.getProperty(Trails.trail_episodes);

	if (trail_contents == null)
	    trail_contents_seq = model.getSeq(USER_TRAILS_URL + "/"
		    + "Episodes");
	else
	    trail_contents_seq = trail_contents.getSeq();

	// Build the episode
	Resource episode = model.createResource(USER_TRAIL_NAME_URL + "/"
		+ "Episodes" + "/" + episodeTitleWithNoSpaces + "_"
		+ episodePositionOnTrail, Episode.Episode);
	episode.addProperty(Episode.title, episodeTitle);
	episode.addProperty(Episode.category, episodeCategory);
	episode.addProperty(Episode.nature, episodeNature);
	episode.addProperty(Episode.start, episodeStartDate);
	episode.addProperty(Episode.end, episodeEndDate);
	episode.addProperty(Episode.url, episodeURL);
	episode.addProperty(Episode.description, episodeDescription);
	episode.addProperty(Episode.owner_tmln, trailName);
	episode.addProperty(Episode.prm_classif, prim);
	episode.addProperty(Episode.sec_classif, sec);

	trail_contents_seq.add(episodePositionOnTrail, episode);

	model.commit();

	return new Message(ErrorCodes.SUCCESS,
		"New content was add successfully to trail '" + trailName + "'");
    }

    /**
     * Edits the episode lying in the specified position on the timeline (former trail)
     * 
     * @param username
     * @param trailName
     * @param episodeTitle
     * @param episodeCategory
     * @param episodeStartDate
     * @param episodeEndDate
     * @param episodeURL
     * @param episodeDescription
     * @param episodePositionOnTrail
     * @param prim, String sec 
     * @return
     */
    public Message editEpisode(String username, String trailName,
	    String episodeTitle, String episodeCategory, String episodeNature,
	    String episodeStartDate, String episodeEndDate, String episodeURL,
	    String episodeDescription, int episodePositionOnTrail, 
	    String prim, String sec)
    {
	String URLTrailName = trailName.trim().replace(' ', '_');
	String episodeTitleWithNoSpaces = episodeTitle.trim().replace(' ', '_');

	String USER_URL = BASE_USER_URL + username;
	String USER_TRAILS_URL = USER_URL + "/" + "LearningPrefs" + "/"
		+ "Trails";
	String USER_TRAIL_NAME_URL = USER_URL + "/" + "LearningPrefs" + "/"
		+ "Trails" + "/" + URLTrailName;

	Resource trail = null;

	if (!RDFGraphUtil.hasResource(USER_URL, model))
	    return new Message(ErrorCodes.INVALID_USERNAME_ERROR,
		    "I cannot add a trail to a user that does not exist!!");

	if (!RDFGraphUtil.hasResource(USER_TRAIL_NAME_URL, model))
	    return new Message(
		    ErrorCodes.INVALID_TRAIL_NAME_ERROR,
		    "The trail '"
			    + trailName
			    + "' does not exist. Please create it beforehand or choose another trail.");

	Resource trails = model.getResource(USER_URL + "/" + "LearningPrefs");

	Bag user_trails = null;
	Statement learner_trails = trails.getProperty(L4all_user.user_trails);
	if (learner_trails == null)
	    user_trails = model.getBag(USER_TRAILS_URL);
	else
	    user_trails = learner_trails.getBag();

	NodeIterator trails_iterator = user_trails.iterator();

	while (trails_iterator.hasNext())
	{
	    trail = (Resource) trails_iterator.next();
	    String curTrailName = trail.getProperty(Trails.trail_name)
		    .getString();

	    if (curTrailName.trim().equals(trailName.trim()))
		break;
	}

	Seq trail_contents_seq = null;
	Statement trail_contents = trail.getProperty(Trails.trail_episodes);

	if (trail_contents == null)
	    trail_contents_seq = model.getSeq(USER_TRAILS_URL + "/"
		    + "Episodes");
	else
	    trail_contents_seq = trail_contents.getSeq();

	// Build the episode
	Object[] seqContents = RDFGraphUtil
		.getContainerObjectContents(trail_contents_seq);
	Resource episode = (Resource) seqContents[episodePositionOnTrail - 1];

	if (episodeTitle != null && !episodeTitle.equals(""))
	    episode.getProperty(Episode.title).changeObject(episodeTitle);

	if (episodeCategory != null && !episodeCategory.equals(""))
	    episode.getProperty(Episode.category).changeObject(episodeCategory);

	if (episodeStartDate != null && !episodeStartDate.equals(""))
	    episode.getProperty(Episode.start).changeObject(episodeStartDate);

	if (episodeEndDate != null && !episodeEndDate.equals(""))
	    episode.getProperty(Episode.end).changeObject(episodeEndDate);

	if (episodeURL != null && !episodeURL.equals(""))
	    episode.getProperty(Episode.url).changeObject(episodeURL);

	if (episodeNature != null && !episodeNature.equals(""))
	    episode.getProperty(Episode.nature).changeObject(episodeNature);

	if (prim != null && !prim.equals(""))
	    episode.getProperty(Episode.prm_classif).changeObject(prim);

	if (sec != null && !sec.equals(""))
	    episode.getProperty(Episode.sec_classif).changeObject(sec);
	
	if (episodeDescription != null && !episodeDescription.equals(""))
	    episode.getProperty(Episode.description).changeObject(
		    episodeDescription);
	//		episode.addProperty(Episode.owner_tmln, trailName);

	//		trail_contents_seq.add(episodePositionOnTrail, episode);

	model.commit();

	return new Message(ErrorCodes.SUCCESS,
		"The episode was updated successfully!");
    }

    /**
     * Deletes a content with <code>contentURL</code> from the trail with
     * <code>trailName</code>.
     * 
     * @param username
     * @param trailName
     * @param contentURL
     */
    public Message deleteEpisodeFromTrail(String username, String trailName,
	    int episodeOrder)
    {
	String URLTrailName = trailName.trim().replace(' ', '_');

	String USER_URL = BASE_USER_URL + username;
	String USER_TRAILS_URL = USER_URL + "/" + "LearningPrefs" + "/"
		+ "Trails";
	String USER_TRAIL_NAME_URL = USER_URL + "/" + "LearningPrefs" + "/"
		+ "Trails" + "/" + URLTrailName;

	Resource trail = null;

	if (!RDFGraphUtil.hasResource(USER_URL, model))
	    return new Message(ErrorCodes.INVALID_USERNAME_ERROR,
		    "I cannot add a trail to a user that does not exist!!");

	if (!RDFGraphUtil.hasResource(USER_TRAIL_NAME_URL, model))
	    return new Message(
		    ErrorCodes.INVALID_TRAIL_NAME_ERROR,
		    "The trail '"
			    + trailName
			    + "' does not exist. Please create it beforehand or choose another trail.");

	Resource trails = model.getResource(USER_URL + "/" + "LearningPrefs");

	Bag user_trails = null;
	Statement learner_trails = trails.getProperty(L4all_user.user_trails);

	if (learner_trails == null)
	    user_trails = model.getBag(USER_TRAILS_URL);
	else
	    user_trails = learner_trails.getBag();

	NodeIterator trails_iterator = user_trails.iterator();

	while (trails_iterator.hasNext())
	{
	    trail = (Resource) trails_iterator.next();
	    String curTrailName = trail.getProperty(Trails.trail_name)
		    .getString();

	    if (curTrailName.trim().equals(trailName.trim()))
		break;
	}

	Seq trail_contents_seq = null;
	Statement trail_contents = trail.getProperty(Trails.trail_episodes);

	if (trail_contents == null)
	    trail_contents_seq = model.getSeq(USER_TRAIL_NAME_URL + "/"
		    + "Episodes");
	else
	    trail_contents_seq = trail_contents.getSeq();

	trail_contents_seq.remove(episodeOrder);

	model.commit();

	return new Message(ErrorCodes.SUCCESS,
		"The episode was successfully deleted from the timeline '"
			+ trailName + "'");
    }

    /**
     * Retrieve the trail associated with the given user
     * @param username		The id of the user
     * @param trailName		The id of the trail
     * @return	A {@link Message} object containing the relevant {@link Trail} instance
     */
    public Message getAllTrails(String username)
    {
	Trail userTrail = null;

	String USER_URL = BASE_USER_URL + username;
	String USER_TRAILS_URL = USER_URL + "/" + "LearningPrefs" + "/"
		+ "Trails";

	if (!RDFGraphUtil.hasResource(USER_URL, model))
	    return new Message(ErrorCodes.INVALID_USERNAME_ERROR,
		    "The user you specified does not exist!");

	Resource learnerLearningPref = model.getResource(USER_URL + "/"
		+ "LearningPrefs");
	Bag user_trails = null;
	Resource trail = null;
	Statement learner_trails = learnerLearningPref
		.getProperty(L4all_user.user_trails);

	if (learner_trails == null)
	    user_trails = model.getBag(USER_TRAILS_URL);
	else
	    user_trails = learner_trails.getBag();

	NodeIterator trails_iterator = user_trails.iterator();
	ArrayList list = new ArrayList();

	while (trails_iterator.hasNext())
	{
	    trail = (Resource) trails_iterator.next();

	    if (trail.getProperty(Trails.trail_name) != null)
	    {
		Statement stat =  trail.getProperty(Trails.trail_name);
		if (stat !=null) 
		    {
		    try 
		    	{
		    	    String curTrailName =stat.getString();
		    	list.add(curTrailName);
		    	}
		    	catch (Exception e)
		    	{
		    	    
		    	}
		    
		//String curTrailType = trail.getProperty(Trails.trail_type).getString();
		//curTrailName += " ["  + curTrailType + "]";
		
		    }
		
	    }

	}
	return new Message(ErrorCodes.SUCCESS, list);
    }

    /**
     * Retrieve the trail associated with the given user
     * @param username		The id of the user
     * @param trailName		The id of the trail
     * @return	A {@link Message} object containing the relevant {@link Trail} instance
     */
    public Message getUserTrailDetails(String username, String trailName)
    {
	Trail userTrail = null;
	String URLTrailName = trailName.trim().replace(' ', '_');

	String USER_URL = BASE_USER_URL + username;
	String USER_TRAILS_URL = USER_URL + "/" + "LearningPrefs" + "/"
		+ "Trails";
	String USER_TRAIL_NAME_URL = USER_URL + "/" + "LearningPrefs" + "/"
		+ "Trails" + "/" + URLTrailName;

	if (!RDFGraphUtil.hasResource(USER_URL, model))
	    return new Message(ErrorCodes.INVALID_USERNAME_ERROR,
		    "The user you specified does not exist!");

	if (!RDFGraphUtil.hasResource(USER_TRAIL_NAME_URL, model))
	    return new Message(
		    ErrorCodes.INVALID_TRAIL_NAME_ERROR,
		    "The trail '"
			    + trailName
			    + "' does not exist. Please create it beforehand or choose another trail.");

	///// FIXME Check if the user with 'username' has priviliges of viewing
	///// each trail

	Resource learnerLearningPref = model.getResource(USER_URL + "/"
		+ "LearningPrefs");
	Bag user_trails = null;
	Resource trail = null;
	Statement learner_trails = learnerLearningPref
		.getProperty(L4all_user.user_trails);

	if (learner_trails == null)
	    user_trails = model.getBag(USER_TRAILS_URL);
	else
	    user_trails = learner_trails.getBag();

	NodeIterator trails_iterator = user_trails.iterator();
	long trailID = 0;
	String curTrailName = "";
	String trailDescription = "";
	int trailPrivileges = 0;
	String trailAnnotation = "";
	String trailKeywords = "";
	String tType = "";

	while (trails_iterator.hasNext())
	{
	    trail = (Resource) trails_iterator.next();

	    if (trail.getProperty(Trails.trail_name) != null)
		curTrailName = trail.getProperty(Trails.trail_name).getString();

	    if (!curTrailName.trim().equals(trailName.trim()))
		continue;

	    if (trail.getProperty(Trails.trail_description) != null)
		trailDescription = trail.getProperty(Trails.trail_description)
			.getString();

	    if (trail.getProperty(Trails.trail_annotation) != null)
		trailAnnotation = trail.getProperty(Trails.trail_annotation)
			.getString();

	    if (trail.getProperty(Trails.trail_privileges) != null)
		trailPrivileges = trail.getProperty(Trails.trail_privileges)
			.getInt();

	    if (trail.getProperty(Trails.trail_keywords) != null)
		trailKeywords = trail.getProperty(Trails.trail_keywords)
			.getString();

	    if (trail.getProperty(Trails.trail_id) != null)
		trailID = trail.getProperty(Trails.trail_id).getLong();

	    String tStart = trail.getProperty(Trails.trail_start).getString();
	    String tEnd = trail.getProperty(Trails.trail_end).getString();

	    if (trail.getProperty(Trails.trail_type) != null)
		tType = trail.getProperty(Trails.trail_type).getString();
	    else
	    {
		tType = Trails.TYPE_USER;

		//TODO	This is to ensure that the missing information if put into
		//	the database. A better way needs to be found 	
		trail.addProperty(Trails.trail_type, tType);
		model.commit();
	    }

	    Seq tEpisodes = trail.getProperty(Trails.trail_episodes).getSeq();

	    userTrail = new Trail();
	    userTrail.setTrailID(trailID);
	    userTrail.setTrailName(trailName);
	    userTrail.setTrailDescription(trailDescription);
	    userTrail.setTrailAnnotation(trailAnnotation);
	    userTrail.setTrailPrivileges(trailPrivileges);
	    userTrail.setTrailKeywords(trailKeywords);
	    userTrail.setTrailOwner(username);
	    userTrail.setStart(tStart);
	    userTrail.setEnd(tEnd);
	    userTrail.setTrailType(tType);
	    userTrail.setTrailEpisodes(L4AllTrailSearch.getEpisodes(tEpisodes));
	    break;
	}
	return new Message(ErrorCodes.SUCCESS, userTrail);
    }

    /**
     * Retrieve the trail associated with the given user
     * @param username		The id of the user
     * @return	A {@link Message} object containing the relevant {@link Trail} instance
     */
    public Message getUserTrailDetails(String username)
    {
	return getUserTrailDetails(username, username);
    }

    public void dump()
    {
	model.write(System.out);
    }
}
