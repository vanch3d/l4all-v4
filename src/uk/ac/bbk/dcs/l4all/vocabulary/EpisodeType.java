package uk.ac.bbk.dcs.l4all.vocabulary;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

/**
 * @author nicolas
 * @version 
 * 
 * This class represents a dummy wrapper for the ontology-generated description
 * of Episode Types.
 * 
 * TODO Need to find a better way of doing this.
 */
public class EpisodeType 
{
	// The different categories of episodes
	public static String CAT_LEARNING = "learning";
	public static String CAT_OCCUPATION = "occupation";
	public static String CAT_PERSONAL = "personal";
	public static String CAT_OTHER = "other";
	
	// The different types of episodes
	public static String SCHOOL = "school";
	public static String COLLEGE = "college";
	public static String UNIVERSITY = "university";
	public static String COURSE = "course";
	public static String WORK = "work";
	public static String BUSINESS = "business";
	public static String VOLUNTARY = "voluntary";
	public static String MILITARY = "military";
	public static String RETIRED = "retired";
	public static String UNEMPLOYED = "unemployed";
	public static String MOVED = "moved";
	public static String TRAVEL = "travel";
	public static String CHILD = "child";
	public static String ADOPTION = "adoption";
	public static String DEATH = "death";
	public static String MARRIED = "married";
	public static String SEPARATED = "separated";
	public static String DISABILITY = "disability";
	public static String ILLNESS = "illness";
	public static String OTHER = "other";
	
	private static Hashtable descriptions = new Hashtable();
	private static Hashtable types = new Hashtable();
	private static Hashtable coding = new Hashtable();
	
	static {
		descriptions.put(CAT_LEARNING, "Learning Episodes");
		descriptions.put(CAT_OCCUPATION, "Occupational Episodes");
		descriptions.put(CAT_PERSONAL, "Personal Episodes");
		descriptions.put(CAT_OTHER, "Other Episodes");
		descriptions.put(SCHOOL, "Attended school");
		descriptions.put(COLLEGE, "Attended college");
		descriptions.put(UNIVERSITY, "Attended university");
		descriptions.put(COURSE, "Attended a particular course");
		descriptions.put(WORK, "Work");
		descriptions.put(BUSINESS, "Started business");
		descriptions.put(RETIRED, "Retired from work");
		descriptions.put(VOLUNTARY, "Working in voluntary sector");
		descriptions.put(UNEMPLOYED, "Unemployed");

		coding.put(SCHOOL, "Sc");
		coding.put(COLLEGE, "Cl");
		coding.put(UNIVERSITY, "Un");
		coding.put(COURSE, "Cs");
		coding.put(WORK, "Wk");
		coding.put(BUSINESS, "Bs");
		coding.put(VOLUNTARY, "Vl");
		coding.put(MILITARY, "Ml");
		coding.put(RETIRED, "Re");
		coding.put(UNEMPLOYED, "Ue");
		coding.put(MOVED, "Mv");
		coding.put(TRAVEL, "Tv");
		coding.put(CHILD, "Ch");
		coding.put(ADOPTION, "Ad");
		coding.put(DEATH, "De");
		coding.put(MARRIED, "Ma");
		coding.put(SEPARATED, "Se");
		coding.put(DISABILITY, "Ds");
		coding.put(ILLNESS, "Il");
		coding.put(OTHER, "Ot");
		
		ArrayList mylist = new ArrayList();
		mylist.add(SCHOOL);
		mylist.add(COLLEGE);
		mylist.add(UNIVERSITY);
		mylist.add(COURSE);
		types.put(CAT_LEARNING, mylist);

		mylist = new ArrayList();
		mylist.add(WORK);
		mylist.add(BUSINESS);
		mylist.add(VOLUNTARY);
		mylist.add(MILITARY);
		mylist.add(RETIRED);
		mylist.add(UNEMPLOYED);
		types.put(CAT_OCCUPATION, mylist);

		mylist = new ArrayList();
		mylist.add(MOVED);
		mylist.add(TRAVEL);
		mylist.add(CHILD);
		mylist.add(ADOPTION);
		mylist.add(DEATH);
		mylist.add(MARRIED);
		mylist.add(SEPARATED);
		mylist.add(DISABILITY);
		mylist.add(ILLNESS);
		types.put(CAT_PERSONAL, mylist);

		mylist = new ArrayList();
		mylist.add(OTHER);
		types.put(CAT_OTHER, mylist);
		
	}

	public static List getAllTypes()
	{
	    List mylist = new ArrayList();
	    Enumeration tg = types.keys();
	    while (tg.hasMoreElements())
	    {	
		String key = (String) tg.nextElement();
		List sec = (List)types.get(key);
		mylist.addAll(sec);
	    }
	    return mylist;
	}

	public static List getTypes(String cat)
	{
		List mylist = (List)types.get(cat);
		if (mylist==null) mylist = new ArrayList();
		return mylist;
	}

	public static String getCoding(String type)
	{
		String res = (String) coding.get(type);
		if (res==null) res = "Xx";
		return res;
	}

	public static List getCategories()
	{
		ArrayList mylist = new ArrayList();
		mylist.add(CAT_LEARNING);
		mylist.add(CAT_OCCUPATION);
		mylist.add(CAT_PERSONAL);
		mylist.add(CAT_OTHER);
		return mylist;
	}
	
	public static boolean isDuration(String type)
	{
		if (RETIRED.equals(type) || MOVED.equals(type) || CHILD.equals(type) ||
			ADOPTION.equals(type) || DEATH.equals(type) || MARRIED.equals(type) ||
			SEPARATED.equals(type) || MOVED.equals(type))
			return false;
		return true;
	}
	
	public static String getDescription(String type)
	{
		String res = (String) descriptions.get(type);
		if (res==null) res = " ";
		return res;
	}
	
	public static String getImage(String type)
	{
		return "../images/" + type + ".png";
	}
	
	public static String getSmallImage(String type)
	{
		return "../images/" + type + "-small.png";
	}
	
	public static String getIcon(String type)
	{
		return "../images/" + type + "-icon.png";
	}
}
