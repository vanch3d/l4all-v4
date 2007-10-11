package uk.ac.shef.wit.simmetrics.similaritymetrics;

import java.io.Serializable;
import java.util.ArrayList;

import uk.ac.shef.wit.simmetrics.math.MathFuncs;
import uk.ac.shef.wit.simmetrics.similaritymetrics.costfunctions.AbstractSubstitutionCost;
import uk.ac.shef.wit.simmetrics.tokenisers.InterfaceTokeniser;
import uk.ac.shef.wit.simmetrics.tokenisers.TokeniserWhitespace;

public class NeedlemanWunchToken extends AbstractStringMetric implements Serializable
{
    /**
     * a constant for calculating the estimated timing cost.
     */
    private final float ESTIMATEDTIMINGCONST = 1.842e-4f;

    /**
     * the private cost function used in the levenstein distance.
     */
    private AbstractSubstitutionCost dCostFunc;

    /**
     * private tokeniser for tokenisation of the query strings.
     */
    final InterfaceTokeniser tokeniser;

    /**
     * the cost of a gap.
     */
    private float gapCost;

    /**
     * constructor - default (empty).
     */
    public NeedlemanWunchToken()
    {
	// set the gapCost to a default value
	gapCost = 1.0f;
	// set the default cost func
	dCostFunc = new SubCost01Token();
	// set the default tokeniser
	tokeniser = new TokeniserWhitespace();
    }

    /**
     * constructor.
     * 
     * @param costG -
     *                the cost of a gap
     */
    public NeedlemanWunchToken(final float costG)
    {
	// set the gapCost to a given value
	gapCost = costG;
	// set the cost func to a default function
	dCostFunc = new SubCost01Token();
	// set the default tokeniser
	tokeniser = new TokeniserWhitespace();
    }

    /**
     * constructor.
     * 
     * @param costG -
     *                the cost of a gap
     * @param costFunc -
     *                the cost function to use
     */
    public NeedlemanWunchToken(final float costG,
	    final AbstractSubstitutionCost costFunc)
    {
	// set the gapCost to the given value
	gapCost = costG;
	// set the cost func
	dCostFunc = costFunc;
	// set the default tokeniser
	tokeniser = new TokeniserWhitespace();
    }

    /**
     * constructor.
     * 
     * @param costFunc -
     *                the cost function to use
     */
    public NeedlemanWunchToken(final AbstractSubstitutionCost costFunc)
    {
	// set the gapCost to a default value
	gapCost = 1.0f;
	// set the cost func
	dCostFunc = costFunc;
	// set the default tokeniser
	tokeniser = new TokeniserWhitespace();
    }

    /**
     * gets the gap cost for the distance function.
     * 
     * @return the gap cost for the distance function
     */
    public float getGapCost()
    {
	return gapCost;
    }

    /**
     * sets the gap cost for the distance function.
     * 
     * @param gapCost	the cost of a gap
     */
    public void setGapCost(final float gapCost)
    {
	this.gapCost = gapCost;
    }

    /**
     * get the d(i,j) cost function.
     * 
     * @return AbstractSubstitutionCost cost function used
     */
    public AbstractSubstitutionCost getdCostFunc()
    {
	return dCostFunc;
    }

    /**
     * sets the d(i,j) cost function used .
     * 
     * @param dCostFunc	the cost function to use
     */
    public void setdCostFunc(final AbstractSubstitutionCost dCostFunc)
    {
	this.dCostFunc = dCostFunc;
    }

    /* (non-Javadoc)
     * @see uk.ac.shef.wit.simmetrics.similaritymetrics.AbstractStringMetric#getLongDescriptionString()
     */
    public String getLongDescriptionString()
    {
	return "Implements the Needleman-Wunch algorithm providing an edit distance based "
		+ "similarity measure between two strings";
    }

    /* (non-Javadoc)
     * @see uk.ac.shef.wit.simmetrics.similaritymetrics.AbstractStringMetric#getShortDescriptionString()
     */
    public String getShortDescriptionString()
    {
	return "NeedlemanWunch (T)";
    }

    /* (non-Javadoc)
     * @see uk.ac.shef.wit.simmetrics.similaritymetrics.AbstractStringMetric#getSimilarity(java.lang.String, java.lang.String)
     */
    public float getSimilarity(String string1, String string2)
    {
	float needlemanWunch = getUnNormalisedSimilarity(string1, string2);

	// normalise into zero to one region from min max possible
        final float str1Length = tokeniser.tokenizeToArrayList(string1).size();
        final float str2Length = tokeniser.tokenizeToArrayList(string2).size();
	
	//float maxValue = Math.max(string1.length(), string2.length());
	float maxValue = Math.max(str1Length, str2Length);
	float minValue = maxValue;
	if (dCostFunc.getMaxCost() > gapCost)
	{
	    maxValue *= dCostFunc.getMaxCost();
	} 
	else
	{
	    maxValue *= gapCost;
	}
	if (dCostFunc.getMinCost() < gapCost)
	{
	    minValue *= dCostFunc.getMinCost();
	} 
	else
	{
	    minValue *= gapCost;
	}
	if (minValue < 0.0f)
	{
	    maxValue -= minValue;
	    needlemanWunch -= minValue;
	}

	// check for 0 maxLen
	if (maxValue == 0)
	{
	    return 1.0f; // as both strings identically zero length
	} else
	{
	    // return actual / possible NeedlemanWunch distance to get 0-1
	    // range
	    return 1.0f - (needlemanWunch / maxValue);
	}
    }

    /* (non-Javadoc)
     * @see uk.ac.shef.wit.simmetrics.similaritymetrics.AbstractStringMetric#getSimilarityExplained(java.lang.String, java.lang.String)
     */
    public String getSimilarityExplained(String string1, String string2)
    {
	// TODO Auto-generated method stub
	return null;
    }

    /* (non-Javadoc)
     * @see uk.ac.shef.wit.simmetrics.similaritymetrics.AbstractStringMetric#getSimilarityTimingEstimated(java.lang.String, java.lang.String)
     */
    public float getSimilarityTimingEstimated(String string1, String string2)
    {
	// TODO Auto-generated method stub
	//final float str1Length = string1.length();
	//final float str2Length = string2.length();
        final float str1Length = tokeniser.tokenizeToArrayList(string1).size();
        final float str2Length = tokeniser.tokenizeToArrayList(string2).size();
	
	return (str1Length * str2Length) * ESTIMATEDTIMINGCONST;
    }

    /* (non-Javadoc)
     * @see uk.ac.shef.wit.simmetrics.similaritymetrics.AbstractStringMetric#getUnNormalisedSimilarity(java.lang.String, java.lang.String)
     */
    public float getUnNormalisedSimilarity(String s, String t)
    {
	final float[][] d; // matrix
	final int n; // length of s
	final int m; // length of t
	int i; // iterates through s
	int j; // iterates through t
	float cost; // cost
	
        final ArrayList str1Tokens = tokeniser.tokenizeToArrayList(s);
        final ArrayList str2Tokens = tokeniser.tokenizeToArrayList(t);


	// check for zero length input
	n = str1Tokens.size();
	m = str2Tokens.size();
	if (n == 0)
	{
	    return m;
	}
	if (m == 0)
	{
	    return n;
	}

	// create matrix (n+1)x(m+1)
	d = new float[n + 1][m + 1];

	// put row and column numbers in place
	for (i = 0; i <= n; i++)
	{
	    d[i][0] = i;
	}
	for (j = 0; j <= m; j++)
	{
	    d[0][j] = j;
	}

	// cycle through rest of table filling values from the lowest cost value
	// of the three part cost function
	for (i = 1; i <= n; i++)
	{
	    for (j = 1; j <= m; j++)
	    {
		// get the substution cost
		//cost = dCostFunc.getCost(s, i - 1, t, j - 1);
		cost = dCostFunc.getCost((String)str1Tokens.get(i-1), i - 1, 
					 (String)str2Tokens.get(j-1), j - 1);

		// find lowest cost at point from three possible
		d[i][j] = MathFuncs.min3(
			d[i - 1][j] + gapCost, 
			d[i][j - 1] + gapCost, 
			d[i - 1][j - 1] + cost);
	    }
	}

	// return bottom right of matrix as holds the maximum edit score
	return d[n][m];
    }

}
