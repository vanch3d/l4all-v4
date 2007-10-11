package uk.ac.shef.wit.simmetrics.similaritymetrics;

import java.io.Serializable;
import java.util.ArrayList;

import uk.ac.shef.wit.simmetrics.tokenisers.InterfaceTokeniser;
import uk.ac.shef.wit.simmetrics.tokenisers.TokeniserWhitespace;

/**
 * @author nicolas
 *
 */
public class JaroToken extends AbstractStringMetric implements Serializable
{
    /**
     * a constant for calculating the estimated timing cost.
     */
    private final float ESTIMATEDTIMINGCONST = 4.12e-5f;

    /**
     * private tokeniser for tokenisation of the query strings.
     */
    final InterfaceTokeniser tokeniser;
    
    
    public JaroToken()
    {
	tokeniser = new TokeniserWhitespace();
    }

    public JaroToken(final InterfaceTokeniser tokeniser)
    {
	this.tokeniser = tokeniser;
    }

    public String getLongDescriptionString()
    {
        return "Implements the Jaro algorithm providing a similarity measure between two strings allowing character transpositions to a degree";
    }

    public String getShortDescriptionString()
    {
	return "Jaro (T)";
    }

    public float getSimilarity(String string1, String string2)
    {
	// TODO Auto-generated method stub
	return getUnNormalisedSimilarity(string1, string2);
    }

    public String getSimilarityExplained(String string1, String string2)
    {
	// TODO Auto-generated method stub
	return null;
    }

    public float getSimilarityTimingEstimated(String string1, String string2)
    {
        final float str1Length = tokeniser.tokenizeToArrayList(string1).size();
        final float str2Length = tokeniser.tokenizeToArrayList(string2).size();
        return (str1Length * str2Length) * ESTIMATEDTIMINGCONST;
    }

    public float getUnNormalisedSimilarity(String string1, String string2)
    {
	int str1Length = tokeniser.tokenizeToArrayList(string1).size();
	int str2Length = tokeniser.tokenizeToArrayList(string2).size();
	
	//get half the length of the string rounded up - (this is the distance used for acceptable transpositions)
	int a = Math.min(str1Length, str2Length);
	int b = a/2;
	int c = a%2;
        final int halflen = ((Math.min(str1Length, str2Length)) / 2) + ((Math.min(str1Length, str2Length)) % 2);
	//final int halflen = Math.max(b+c+1, a);

        String s1 = string1.trim();
        String s2 = string2.trim();
        final int halflen2 = ((Math.min(s1.length(), s2.length())) / 2) + ((Math.min(s1.length(), s2.length())) % 2);

        //get common characters
        ArrayList common1 = getCommonTokens(string1, string2, halflen);
        ArrayList common2 = getCommonTokens(string2, string1, halflen);

        //check for zero in common
        if (common1.size() == 0 || common2.size() == 0) 
        {
            return 0.0f;
        }
        //check for same length common strings returning 0.0f is not the same
        if (common1.size() != common2.size()) 
        {
            return 0.0f;
        }
        //get the number of transpositions
        int transpositions = 0;
        for (int i = 0; i < common1.size(); i++) 
        {
            String c1 = (String)common1.get(i);
            String c2 = (String)common2.get(i);
            if (!c1.equals(c2)) 
        	transpositions++;
        }
        transpositions /= 2.0f;
        
        //calculate jaro metric
        return (common1.size() / ((float) str1Length) +
                common2.size() / ((float) str2Length) +
                (common1.size() - transpositions) / ((float) common1.size())) / 3.0f;

    }
    
    private ArrayList getCommonTokens(final String string1, final String string2, final int distanceSep) 
    {
	ArrayList list = new ArrayList();
        ArrayList str1Tokens = tokeniser.tokenizeToArrayList(string1);
        ArrayList str2Tokens = tokeniser.tokenizeToArrayList(string2);
	
        for (int i=0;i<str1Tokens.size();i++)
        {
            String ch = (String)str1Tokens.get(i);
            // set boolean for quick loop exit if found
            boolean foundIt = false;
            //compare char with range of characters to either side
            for (int j = Math.max(0, i - distanceSep); 
            	 !foundIt && j < Math.min(i + distanceSep, str2Tokens.size()); j++) 
            {
        	 String ch2 = (String)str2Tokens.get(j);
        	//check if found
                if (ch.equals(ch2))
                {
                    foundIt = true;
                    //append character found
                    list.add(ch);
                    //alter copied string2 for processing
                    str2Tokens.set(j," ");
                }
        	
            }
            
        }
	return list;
    }
    

}
