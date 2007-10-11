package uk.ac.shef.wit.simmetrics.similaritymetrics;

public class NeedlemanWunchTimeline extends NeedlemanWunchToken
{

    public NeedlemanWunchTimeline()
    {
	super(new SubCostTimeline());
	// TODO Auto-generated constructor stub
    }

    public String getShortDescriptionString()
    {
	return "NeedlemanWunch (Timeline)";
    }
}
