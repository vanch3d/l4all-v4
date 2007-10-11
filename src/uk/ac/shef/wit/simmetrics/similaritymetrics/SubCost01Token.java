package uk.ac.shef.wit.simmetrics.similaritymetrics;

import java.io.Serializable;
import uk.ac.shef.wit.simmetrics.similaritymetrics.costfunctions.AbstractSubstitutionCost;

public class SubCost01Token extends AbstractSubstitutionCost implements Serializable
{

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
        //if (str1.charAt(string1Index) == str2.charAt(string2Index)) 
        if (str1.equals(str2)) 
        {
            return 0.0f;
        } 
        else 
        {
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
	return "SubCostToken";
    }

}
