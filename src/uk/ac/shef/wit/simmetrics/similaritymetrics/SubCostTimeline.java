package uk.ac.shef.wit.simmetrics.similaritymetrics;

import java.io.Serializable;
import uk.ac.shef.wit.simmetrics.similaritymetrics.costfunctions.AbstractSubstitutionCost;

public class SubCostTimeline extends AbstractSubstitutionCost implements Serializable
{
    private final int EPISODE_TYPE  = 0;
    private final int EPISODE_PRIMARY  = 1;
    private final int EPISODE_SECONDARY  = 2;
    
    /**
     * @param episode
     * @param index
     * @return
     */
    private String getEpisodeCoding(String episode,int index)
    {	
	String res = null;
	if (EPISODE_TYPE == index) 
	    res = episode.substring(0, 2);
	else if (EPISODE_PRIMARY == index) 
	    res = episode.substring(2, 2);
	else if (EPISODE_SECONDARY == index) 
	    res = episode.substring(4, 2);
	    
	return res;
    }
    
    private String getEpisodeType(String episode)
    {
	String[] tt = episode.split("-");
	return (tt!=null) ? tt[0] : null;
    }

    private String getEpisodePrimary(String episode)
    {
	String[] tt = episode.split("-");
	return (tt!=null) ? tt[1] : null;
    }
    
    private String getEpisodeSecondary(String episode)
    {
	String[] tt = episode.split("-");
	return (tt!=null) ? tt[2] : null;
    }
    
    /**
     * get cost between characters where d(i,j) = 1 if i does not equals j, 0 if i equals j.
     *
     * @param str1         - the string1 to evaluate the cost
     * @param string1Index - the index within the string1 to test
     * @param str2         - the string2 to evaluate the cost
     * @param string2Index - the index within the string2 to test
     * @return the cost of a given subsitution d(i,j) where d(i,j) = 1 if i!=j, 0 if i==j
     */
    public float getCost(String str1, int string1Index, String str2,
	    int string2Index)
    {
        if (str1.equals(str2)) 
        {
            return 0.0f;
        } 
        else 
        {
            //String pref1 = getEpisodeCoding(str1,EPISODE_TYPE);
            //String pref2 = getEpisodeCoding(str2,EPISODE_TYPE);
            String type1 = getEpisodeType(str1);
            String type2 = getEpisodeType(str2);

            //String prim1 = getEpisodePrimary(str1);
            //String prim2 = getEpisodePrimary(str2);
            if (type1.equals(type2))
            {
        	return 0.5f;
            }
            else
        	return 1.0f;
        }
    }

    /* (non-Javadoc)
     * @see uk.ac.shef.wit.simmetrics.similaritymetrics.costfunctions.AbstractSubstitutionCost#getMaxCost()
     */
    public float getMaxCost() 
    {
	return 1.0f;
    }

    /* (non-Javadoc)
     * @see uk.ac.shef.wit.simmetrics.similaritymetrics.costfunctions.AbstractSubstitutionCost#getMinCost()
     */
    public float getMinCost()
    {
	return 0.0f;
    }

    /* (non-Javadoc)
     * @see uk.ac.shef.wit.simmetrics.similaritymetrics.costfunctions.AbstractSubstitutionCost#getShortDescriptionString()
     */
    public String getShortDescriptionString()
    {
	return "SubCostTimeline";
    }
}
