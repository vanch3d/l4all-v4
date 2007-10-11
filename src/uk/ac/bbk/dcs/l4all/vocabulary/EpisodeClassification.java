package uk.ac.bbk.dcs.l4all.vocabulary;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * @author nicolas
 * 
 * Note that this implementation is a short-term solution and something more
 * scalable (eg XML document) will have to be done.    
 */
public class EpisodeClassification
{
    /** The (unique) identifier of the classification */
    private String identifer;

    /** The title of the classification */
    private String title;

    /** A description of the classification */
    private String description;

    /** The XML file containing the classification elements/codings */
    private String file;

    /** The default coding for an element of this classification*/
    private String defaultCode;

    /**
     * Represent the default empty classification. Contains no file and a unique
     * element coding.
     */
    public static final EpisodeClassification NONE = new EpisodeClassification(
	    "NONE", 
	    "Classification", 
	    "No classification available for this episode", 
	    null);

    /**
     * 
     */
    public static final EpisodeClassification WORK_SIC = new EpisodeClassification(
	    "WORK_SIC", 
	    "Sector", 
	    "The activity classification", 
	    "classif_SIC.xml");

    /**
     * 
     */
    public static final EpisodeClassification WORK_SOC = new EpisodeClassification(
	    "WORK_SOC", 
	    "Occupation", 
	    "The occupational classification", 
	    "classif_SOC.xml");

    /**
     * 
     */
    public static final EpisodeClassification EDUC_NQF = new EpisodeClassification(
	    "EDUC_NQF", 
	    "Qualification", 
	    "The qualification classification", 
	    "classif_NQF.xml");

    /**
     * 
     */
    public static final EpisodeClassification EDUC_SBJ = new EpisodeClassification(
	    "EDUC_SBJ", 
	    "Subject", 
	    "The subject classification", 
	    "classif_SDS.xml");

    private EpisodeClassification(String id,String title, String description,String file)
    {
	this.identifer = id;
	this.title = title;
	this.file = file;
	this.description = description;
	this.defaultCode = "0.0.0.0";
    }

    /**
     * Return the default coding for an element of this classification
     * @return	A String containing the default coding 
     */
    public String getDefaultCode()
    {
	return this.defaultCode;
    }

    /**
     * Return the unique identifier of this classification
     * @return	A String containing the identifier 
     */
    public String getID()
    {
	return this.identifer;
    }
    
    /**
     * Return the description of this classification
     * @return A String containing the description
     */
    public String getDescription()
    {
	return this.description;
    }

    /**
     * @return
     */
    public String getTitle()
    {
	return title;
    }

    /**
     * @return
     */
    public String getFile()
    {
	return this.file;
    }

    public static EpisodeClassification getPrimaryFromType(String epType)
    {
	if (EpisodeType.WORK.equals(epType)
		|| EpisodeType.BUSINESS.equals(epType)
		|| EpisodeType.VOLUNTARY.equals(epType))
	    return WORK_SIC;
	else if (EpisodeType.SCHOOL.equals(epType)
		|| EpisodeType.COLLEGE.equals(epType)
		|| EpisodeType.UNIVERSITY.equals(epType)
		|| EpisodeType.COURSE.equals(epType))
	    return EDUC_SBJ;
	return NONE;
    }

    public static EpisodeClassification getSecondaryFromType(String epType)
    {
	if (EpisodeType.WORK.equals(epType)
		|| EpisodeType.VOLUNTARY.equals(epType))
	    return WORK_SOC;
	else if (EpisodeType.SCHOOL.equals(epType)
		|| EpisodeType.COLLEGE.equals(epType)
		|| EpisodeType.UNIVERSITY.equals(epType)
		|| EpisodeType.COURSE.equals(epType))
	    return EDUC_NQF;
	return NONE;
    }
}
