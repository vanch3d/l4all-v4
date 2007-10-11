package uk.ac.shef.wit.simmetrics.similaritymetrics;

import java.io.Serializable;
import java.util.ArrayList;

import uk.ac.shef.wit.simmetrics.math.MathFuncs;
import uk.ac.shef.wit.simmetrics.tokenisers.InterfaceTokeniser;
import uk.ac.shef.wit.simmetrics.tokenisers.TokeniserWhitespace;

public class JaroWinklerToken extends AbstractStringMetric implements Serializable
{
    /**
     * a constant for calculating the estimated timing cost.
     */
    private final float ESTIMATEDTIMINGCONST = 4.342e-5f;

    /**
     * maximum prefix length to use.
     */
    private static final int MINPREFIXTESTLENGTH = 6;

    /**
     * prefix adjustment scale.
     */
    private static final float PREFIXADUSTMENTSCALE = 0.1f;

    /**
     * private string metric allowing internal metric to be composed.
     */
    private final AbstractStringMetric internalStringMetric;

    /**
     * private tokeniser for tokenisation of the query strings.
     */
    final InterfaceTokeniser tokeniser;

 
    
    
    public JaroWinklerToken()
    {
	this.internalStringMetric = new Jaro();
	this.tokeniser = new TokeniserWhitespace();
    }

    public JaroWinklerToken(final InterfaceTokeniser tokeniser)
    {
	this.internalStringMetric = new Jaro();
	this.tokeniser = tokeniser;
    }
    
    public String getLongDescriptionString()
    {
        return "Implements the Jaro-Winkler algorithm providing a similarity measure between two strings allowing character transpositions to a degree adjusting the weighting for common prefixes";
    }

    public String getShortDescriptionString()
    {
	 return "JaroWinkler (T)";
    }

    public float getSimilarity(String string1, String string2)
    {
        //gets normal Jaro Score
        final float dist = internalStringMetric.getSimilarity(string1, string2);

        // This extension modifies the weights of poorly matching pairs string1, string2 which share a common prefix
        final int prefixLength = getPrefixLength(string1, string2);
        return dist + ((float) prefixLength * PREFIXADUSTMENTSCALE * (1.0f - dist));
    }

    public String getSimilarityExplained(String arg0, String arg1)
    {
	// TODO Auto-generated method stub
	return null;
    }

    /* (non-Javadoc)
     * @see uk.ac.shef.wit.simmetrics.similaritymetrics.AbstractStringMetric#getSimilarityTimingEstimated(java.lang.String, java.lang.String)
     */
    public float getSimilarityTimingEstimated(String string1, String string2)
    {
        final float str1Length = tokeniser.tokenizeToArrayList(string1).size();
        final float str2Length = tokeniser.tokenizeToArrayList(string2).size();
        return (str1Length * str2Length) * ESTIMATEDTIMINGCONST;
    }

    public float getUnNormalisedSimilarity(String string1, String string2)
    {
        //gets normal Jaro Score
        final float dist = internalStringMetric.getSimilarity(string1, string2);

        // This extension modifies the weights of poorly matching pairs string1, string2 which share a common prefix
        final int prefixLength = getPrefixLength(string1, string2);
        return dist + ((float) prefixLength * PREFIXADUSTMENTSCALE * (1.0f - dist));
    }
    
    /**
     * gets the prefix length found of common characters at the begining of the strings.
     *
     * @param string1
     * @param string2
     * @return the prefix length found of common characters at the begining of the strings
     */
    private int getPrefixLength(final String string1, final String string2) 
    {
        ArrayList str1 = tokeniser.tokenizeToArrayList(string1);
        ArrayList str2 = tokeniser.tokenizeToArrayList(string2);
	
        final int n = MathFuncs.min3(MINPREFIXTESTLENGTH, str1.size(), str2.size());
        //check for prefix similarity of length n
        for (int i = 0; i < n; i++) 
        {
            //check the prefix is the same so far
            String ch1 = (String)str1.get(i);
            String ch2 = (String)str2.get(i);
            if (!ch1.equals(ch2))
            {
        	// not the same so return as far as got
                return i;
            }
        }
        return n; // first n characters are the same
    }

}
