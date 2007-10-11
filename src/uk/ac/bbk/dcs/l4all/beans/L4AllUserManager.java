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

import uk.ac.bbk.dcs.l4all.db.RDFRepositoryManager;
import uk.ac.bbk.dcs.l4all.util.RDFGraphUtil;
import uk.ac.bbk.dcs.l4all.vocabulary.L4all_user;
import uk.ac.bbk.dcs.l4all.vocabulary.Trails;

import com.hp.hpl.jena.db.ModelRDB;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Seq;
import com.hp.hpl.jena.rdf.model.Statement;

public class L4AllUserManager {
	// //// GLOBAL CONSTANTS //////////
	private static final String BASE_USER_URL = "http://www.dcs.bbk.ac.uk/~gpapa05/l4all/users/";

	private static final String MODEL_NAME = "l4all_users";

	private ModelRDB model = RDFRepositoryManager.getModel(MODEL_NAME);

	// public void storeUserProfile(String username) {
	// if (!username.equals(""))
	// if (!RDFGraphUtil.hasResource(BASE_USER_URL + username, model))
	// createNewUserProfile();
	// else
	// updateUserProfile(username);
	// }

	/**
	 * Creates a new user
	 * 
	 * @param username
	 * @param password
	 * @param name
	 * @param age
	 * @param gender
	 * @param myLocation
	 * @param travelWillingness
	 * @return
	 */
	public Message createUser(String access, String username, String password, String name,
			int age, String gender, String myLocation, int travelWillingness,
			String email) {
		String USER_URL = BASE_USER_URL + username;

		if (access.trim().equals(""))
			return new Message(ErrorCodes.EMPTY_FIELD_ERROR,
					"Invalid access status");

		if (username.trim().equals(""))
			return new Message(ErrorCodes.INVALID_USERNAME_ERROR,
					"Invalid Username");

		if (password.trim().equals(""))
			return new Message(ErrorCodes.NO_MATCHING_PASSWORDS_ERROR,
					"The passwords do not match");

		if (name.trim().equals(""))
			return new Message(ErrorCodes.EMPTY_FIELD_ERROR, "Empty name field");

		if (age <= 0)
			return new Message(ErrorCodes.EMPTY_FIELD_ERROR,
					"Empty or wrong age field");

		if (RDFGraphUtil.hasResource(USER_URL, model))
			return new Message(
					ErrorCodes.UNIQUE_USERNAME_CONSTRAINT_VIOLATION_ERROR,
					"The username exists already. Please choose another one");

		// Create the Learner resource Node
		Resource learner = model.createResource(USER_URL, L4all_user.Learner);

		// Identification details
		Resource identifier = model.createResource(USER_URL + "/"
				+ "Identifier", L4all_user.Identification);

		identifier.addProperty(L4all_user.status, access);
		identifier.addProperty(L4all_user.username, username);
		identifier.addProperty(L4all_user.password, password);
		identifier.addProperty(L4all_user.name, name);
		identifier.addProperty(L4all_user.age, age);
		identifier.addProperty(L4all_user.gender, gender);
		identifier.addProperty(L4all_user.my_location, myLocation);
		identifier.addProperty(L4all_user.email, email);
		identifier.addProperty(L4all_user.travel_willingness, travelWillingness);

		learner.addProperty(L4all_user.id, identifier);

		// Dump it to the stdout
		dump();

		return new Message(ErrorCodes.SUCCESS, username);
	}

	/**
	 * Add the extra user information regarding his/her learning preferences
	 * 
	 * @param pastOccupation
	 * @param presentOccupation
	 * @param qualifications
	 * @param skills
	 * @param interests
	 * @param goals
	 * @param studyType
	 * @param learningMethod
	 * @param disability
	 * @param budget
	 * @param hoursOfStudy
	 * @param username
	 * @return
	 */
	public Message addUserLearningPref(String pastOccupation,
			String presentOccupation, String[] qualifications, String[] skills,
			String[] interests, String[] goals, String studyType,
			String learningMethod, String disability, int budget,
			int hoursOfStudy, String username) {
		String USER_URL = BASE_USER_URL + username;

		if (!RDFGraphUtil.hasResource(USER_URL, model))
			return new Message(ErrorCodes.INVALID_USERNAME_ERROR,
					"The user specified does not exist!");

		Resource user = model.getResource(USER_URL);

		Resource learningPrefs = model.createResource(USER_URL + "/"
				+ "LearningPrefs", L4all_user.Learning_Prefs);

		learningPrefs.addProperty(L4all_user.past_occupation, pastOccupation);
		learningPrefs.addProperty(L4all_user.present_occupation,
				presentOccupation);
		learningPrefs.addProperty(L4all_user.study_type, studyType);
		learningPrefs.addProperty(L4all_user.budget, budget);
		learningPrefs.addProperty(L4all_user.hours_of_study_pw, hoursOfStudy);
		learningPrefs.addProperty(L4all_user.learning_method, learningMethod);
		learningPrefs.addProperty(L4all_user.disability, disability);
		learningPrefs.addProperty(L4all_user.skills, model.createSeq());
		learningPrefs.addProperty(L4all_user.goals, model.createSeq());
		learningPrefs.addProperty(L4all_user.qualifications, model.createSeq());
		learningPrefs.addProperty(L4all_user.interests, model.createSeq());
		learningPrefs.addProperty(L4all_user.user_trails, model.createBag());

		// Append Qualifications
		Seq userQualSeq = null;
		Statement userQual = learningPrefs
				.getProperty(L4all_user.qualifications);

		if (userQual == null)
			userQualSeq = model.getSeq(USER_URL + "/" + "LearningPrefs" + "/"
					+ "Qualifications");
		else
			userQualSeq = userQual.getSeq();

		if (qualifications.length > 0)
			for (int i = 0; i < qualifications.length; i++) {
				userQualSeq.add(qualifications[i]);
			}

		// Append Goals
		Seq userGoalsSeq = null;
		Statement userGoals = learningPrefs.getProperty(L4all_user.goals);

		if (userGoals == null)
			userGoalsSeq = model.getSeq(USER_URL + "/" + "LearningPrefs" + "/"
					+ "Goals");
		else
			userGoalsSeq = userGoals.getSeq();

		if (goals.length > 0)
			for (int i = 0; i < goals.length; i++) {
				userGoalsSeq.add(goals[i]);
			}

		// Append Skills
		Seq userSkillsSeq = null;
		Statement userSkills = learningPrefs.getProperty(L4all_user.skills);

		if (userSkills == null)
			userSkillsSeq = model.getSeq(USER_URL + "/" + "LearningPrefs" + "/"
					+ "Skills");
		else
			userSkillsSeq = userSkills.getSeq();

		if (skills.length > 0)
			for (int i = 0; i < skills.length; i++) {
				userSkillsSeq.add(skills[i]);
			}

		// Append Interests
		Seq userInterestsSeq = null;
		Statement userInterests = learningPrefs
				.getProperty(L4all_user.interests);

		if (userInterests == null)
			userInterestsSeq = model.getSeq(USER_URL + "/" + "LearningPrefs"
					+ "/" + "Interests");
		else
			userInterestsSeq = userInterests.getSeq();

		if (interests.length > 0)
			for (int i = 0; i < interests.length; i++) {
				userInterestsSeq.add(interests[i]);
			}

		return new Message(ErrorCodes.SUCCESS, username);
	}

	/**
	 * Retrieves back the user identification details
	 * 
	 * @param username
	 * @return
	 */
	public Message getUserIdentificationDetails(String username) {
		String USER_URL = BASE_USER_URL + username;

		if (!RDFGraphUtil.hasResource(USER_URL, model))
			return new Message(ErrorCodes.INVALID_USERNAME_ERROR,
					"The user specified does not exist!");

		Resource learner = model.getResource(USER_URL);
		Resource identifier = model.getResource(USER_URL + "/" + "Identifier");

		L4AllUser user = new L4AllUser();
		
		Statement status = identifier.getProperty(L4all_user.status);
		if (status==null)
		{
		    String strStatus = Trails.TYPE_USER;
		    //TODO	This is to ensure that the missing information if put into
	            //	the database. A better way needs to be found 	
	            identifier.addProperty(L4all_user.status, strStatus);
	            model.commit();
		    
		}
		else
		    user.setStatus(status.getString());

		user.setUsername(identifier.getProperty(L4all_user.username)
				.getString());
		user.setAge(identifier.getProperty(L4all_user.age).getInt());
		user.setName(identifier.getProperty(L4all_user.name).getString());
		user.setGender(identifier.getProperty(L4all_user.gender).getString());
		user.setMyLocation(identifier.getProperty(L4all_user.my_location)
				.getString());
		user.setEmail(identifier.getProperty(L4all_user.email).getString());
		user.setTravelWillingness(identifier.getProperty(
				L4all_user.travel_willingness).getInt());

		return new Message(ErrorCodes.SUCCESS, user);
	}

	/**
	 * Retrieves the user's learning preferences
	 * 
	 * @param username
	 * @return
	 */
	public Message getUserLearningPrefDetails(String username) {
		String USER_URL = BASE_USER_URL + username;

		if (!RDFGraphUtil.hasResource(USER_URL, model))
			return new Message(ErrorCodes.INVALID_USERNAME_ERROR,
					"The user specified does not exist!");

		Resource learnPrefs = model.getResource(USER_URL + "/"
				+ "LearningPrefs");

		if (learnPrefs != null) {
			if (learnPrefs.getProperty(L4all_user.skills) == null)
				return null ;
			Seq skills = learnPrefs.getProperty(L4all_user.skills).getSeq();
			Seq qualifications = learnPrefs.getProperty(
					L4all_user.qualifications).getSeq();
			Seq interests = learnPrefs.getProperty(L4all_user.interests)
					.getSeq();
			Seq goals = learnPrefs.getProperty(L4all_user.goals).getSeq();

			String presentOccupation = learnPrefs.getProperty(
					L4all_user.present_occupation).getString();
			String pastOccupation = learnPrefs.getProperty(
					L4all_user.past_occupation).getString();
			String studyType = learnPrefs.getProperty(L4all_user.study_type)
					.getString();

			String disability = learnPrefs.getProperty(L4all_user.disability)
					.getString();

			String learningMethod = learnPrefs.getProperty(
					L4all_user.learning_method).getString();

			int hofs = learnPrefs.getProperty(L4all_user.hours_of_study_pw)
					.getInt();

			int budget = learnPrefs.getProperty(L4all_user.budget).getInt();

			L4AllUser user = new L4AllUser();

			if (presentOccupation != null
					&& !presentOccupation.trim().equals(""))
				user.setPresentOccupation(presentOccupation);

			if (pastOccupation != null && !pastOccupation.trim().equals(""))
				user.setPastOccupation(pastOccupation);

			if (skills != null)
				user.setSkills(RDFGraphUtil.getContainerStringContents(skills));

			if (goals != null)
				user.setGoals(RDFGraphUtil.getContainerStringContents(goals));

			if (qualifications != null)
				user.setQualifications(RDFGraphUtil
						.getContainerStringContents(qualifications));

			if (interests != null)
				user.setInterests(RDFGraphUtil
						.getContainerStringContents(interests));

			if (studyType != null && !studyType.trim().equals(""))
				user.setStudyType(studyType);

			if (hofs > 0)
				user.setHoursOfStudyPW(hofs);

			if (budget > 0)
				user.setBudget(budget);

			if (disability != null)
				user.setDisability(disability);

			if (learningMethod != null)
				user.setLearningMethod(learningMethod);

			return new Message(ErrorCodes.SUCCESS, user);
		}

		else
			return null;
	}

	/**
	 * Update the user identification details
	 * 
	 * @param username
	 * @param password
	 * @param name
	 * @param age
	 * @param gender
	 * @param myLocation
	 * @param travelWillingness
	 * @param email
	 * @return
	 */
	public Message changeUserIdentificationDetails(String username,
			String password, String name, int age, String gender,
			String myLocation, int travelWillingness, String email) {

		String USER_URL = BASE_USER_URL + username;

		if (!RDFGraphUtil.hasResource(USER_URL, model))
			return new Message(ErrorCodes.INVALID_USERNAME_ERROR,
					"The user specified does not exist!");

		Resource learner = model.getResource(USER_URL);

		// // Identification Details update
		Resource identifier = model.getResource(USER_URL + "/" + "Identifier");
		if (!password.trim().equals("") && password != null)
			identifier.getProperty(L4all_user.password).changeObject(password);

		if (!name.trim().equals("") && name != null)
			identifier.getProperty(L4all_user.name).changeObject(name);

		if (age > 0)
			identifier.getProperty(L4all_user.age).changeObject(age);

		if (!gender.trim().equals("") && gender != null)
			identifier.getProperty(L4all_user.gender).changeObject(gender);

		if (!myLocation.trim().equals("") && myLocation != null)
			identifier.getProperty(L4all_user.my_location).changeObject(
					myLocation);

		if (travelWillingness > 0)
			identifier.getProperty(L4all_user.travel_willingness).changeObject(
					travelWillingness);

		if (!email.trim().equals("") && email != null)
			identifier.getProperty(L4all_user.email).changeObject(email);

		model.commit();

		return new Message(ErrorCodes.SUCCESS, username);
	}

	/**
	 * Updates the user's current learning preferences with the ones prov`ided
	 * 
	 * @param presentOccupation
	 * @param pastOccupation
	 * @param skills
	 * @param goals
	 * @param qualifications
	 * @param interests
	 * @param budget
	 * @param studyType
	 * @param hoursOfStudyPW
	 * @param disability
	 * @param learningMethod
	 * @param username
	 * @return
	 */
	public Message changeUserLearningPreferences(String pastOccupation,
			String presentOccupation, String[] skills, String[] goals,
			String[] qualifications, String[] interests, int budget,
			String studyType, int hoursOfStudyPW, String disability,
			String learningMethod, String username) {
		String USER_URL = BASE_USER_URL + username;

		if (!RDFGraphUtil.hasResource(USER_URL, model))
			return new Message(ErrorCodes.INVALID_USERNAME_ERROR,
					"The user specified does not exist!");

		Resource learner = model.getResource(USER_URL);
		Resource learnPrefs = model.getResource(USER_URL + "/"
				+ "LearningPrefs");

		if (!presentOccupation.trim().equals("") && presentOccupation != null)
			learnPrefs.getProperty(L4all_user.present_occupation).changeObject(
					presentOccupation);

		if (!pastOccupation.trim().equals("") && pastOccupation != null)
			learnPrefs.getProperty(L4all_user.past_occupation).changeObject(
					pastOccupation);

		if (budget > 0)
			learnPrefs.getProperty(L4all_user.budget).changeObject(budget);

		if (!studyType.trim().equals("") && studyType != null)
			learnPrefs.getProperty(L4all_user.study_type).changeObject(
					studyType);

		if (!disability.trim().equals("") && disability != null)
			learnPrefs.getProperty(L4all_user.disability).changeObject(
					disability);

		if (hoursOfStudyPW > 0)
			learnPrefs.getProperty(L4all_user.hours_of_study_pw).changeObject(
					hoursOfStudyPW);

		if (!learningMethod.trim().equals("") && learningMethod != null)
			learnPrefs.getProperty(L4all_user.learning_method).changeObject(
					learningMethod);

		// Append Qualifications
		if (qualifications != null) {
			Seq userQualSeq = null;
			Statement userQual = learnPrefs
					.getProperty(L4all_user.qualifications);

			if (userQual == null)
				userQualSeq = model.getSeq(USER_URL + "/" + "LearningPrefs"
						+ "/" + "Qualifications");
			else
				userQualSeq = userQual.getSeq();

			if (qualifications.length > 0) {
				userQualSeq.removeProperties();
				for (int i = 0; i < qualifications.length; i++) {
					userQualSeq.add(qualifications[i]);
				}
			}
		}

		// Append Goals
		if (goals != null) {
			Seq userGoalsSeq = null;
			Statement userGoals = learnPrefs.getProperty(L4all_user.goals);

			if (userGoals == null)
				userGoalsSeq = model.getSeq(USER_URL + "/" + "LearningPrefs"
						+ "/" + "Goals");
			else
				userGoalsSeq = userGoals.getSeq();

			if (goals.length > 0) {
				userGoalsSeq.removeProperties();
				for (int i = 0; i < goals.length; i++) {
					userGoalsSeq.add(goals[i]);
				}
			}
		}

		// Append Skills
		if (skills != null) {
			Seq userSkillsSeq = null;
			Statement userSkills = learnPrefs.getProperty(L4all_user.skills);

			if (userSkills == null)
				userSkillsSeq = model.getSeq(USER_URL + "/" + "LearningPrefs"
						+ "/" + "Skills");
			else
				userSkillsSeq = userSkills.getSeq();

			if (skills.length > 0) {
				userSkillsSeq.removeProperties();
				for (int i = 0; i < skills.length; i++) {
					userSkillsSeq.add(skills[i]);
				}
			}
		}

		// Append Interests
		if (interests != null) {
			Seq userInterestsSeq = null;
			Statement userInterests = learnPrefs
					.getProperty(L4all_user.interests);

			if (userInterests == null)
				userInterestsSeq = model.getSeq(USER_URL + "/"
						+ "LearningPrefs" + "/" + "Interests");
			else
				userInterestsSeq = userInterests.getSeq();

			userInterestsSeq.removeProperties();

			if (interests.length > 0) {
				userInterestsSeq.removeProperties();
				for (int i = 0; i < interests.length; i++) {
					userInterestsSeq.add(interests[i]);
				}
			}
		}

		return new Message(ErrorCodes.SUCCESS, username);
	}

	public ModelRDB getModel() {
		return model;
	}

	public void dump() {
		model.write(System.out);
	}

}