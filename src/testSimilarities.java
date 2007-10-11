import java.text.DecimalFormat;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Random;
import java.util.Set;

import uk.ac.bbk.dcs.l4all.vocabulary.EpisodeType;
import uk.ac.shef.wit.simmetrics.similaritymetrics.*;


public class testSimilarities
{

    /**
     * @param args
     */
    public static void main(String[] args)
    {
	// TODO Auto-generated method stub
        final DecimalFormat df = new DecimalFormat("0.00");

        Random generator = new Random();
        
        Hashtable targets = new Hashtable();
	Hashtable metrics = new Hashtable();
	String source=		"Cl-00 Un-00 Mv-00 Wk-00";

	targets.put("ident", 	"Cl-00 Un-00 Mv-00 Wk-00");
	targets.put("reorder", 	"Un-00 Wk-00 Cl-00 Mv-00");

	targets.put("substE", 	"Cl-00 Un-00 Mv-00 Bs-00");
	targets.put("substW1", 	"Cl-00 Un-00 Mv-00 Un-00");
	targets.put("substW2", 	"Cl-00 Un-00 Mv-00 Wk-10");
	
	targets.put("addW", 	"Cl-00 Un-00 Mv-00 Wk-00 Wk-00");
	targets.put("addE", 	"Cl-00 Un-00 Mv-00 Wk-00 Bs-00");
	
	targets.put("removeW", 	"Cl-00 Un-00 Mv-00");
	targets.put("removeU", 	"Cl-00 Mv-00 Wk-00");
	
	targets.put("recom1", 	"Cl-00");
	targets.put("recom2", 	"Cl-00 Wk-01");
	targets.put("recom3", 	"Bs-00");
	targets.put("recom4", 	"Bs-00 Bs-01 Bs-03");
	
	String random = "";
	List mylist = EpisodeType.getAllTypes();
	for (int i=0;i<generator.nextInt(20);i++)
	{
	    int index = generator.nextInt(mylist.size());
	    String dd = EpisodeType.getCoding((String)mylist.get(index));
	    dd += "-" + generator.nextInt(10) + "" + generator.nextInt(10) + " ";
	    random += dd;
	}
	targets.put("random", random);
	
	//metrics.put("Levenshtein", new Levenshtein());
	metrics.put("CosineSimilarity", new CosineSimilarity());
	metrics.put("DiceSimilarity", new DiceSimilarity());
	metrics.put("EuclideanDistance", new EuclideanDistance());
	metrics.put("Needleman-Wunch", new NeedlemanWunchToken());
	metrics.put("Needleman-Wunch (T)", new NeedlemanWunchTimeline());
	metrics.put("BlockDistance", new BlockDistance());
	metrics.put("JaccardSimilarity", new JaccardSimilarity());
	metrics.put("MatchingCoefficient", new MatchingCoefficient());
	metrics.put("OverlapCoefficient", new OverlapCoefficient());
	metrics.put("Jaro", new JaroToken());
	metrics.put("JaroWinkler", new JaroWinklerToken());
	
	System.out.println("source:\t" + source + "\n");
	
        Enumeration tg = targets.keys();
        String cat = "\t";
        String def = "";
        while (tg.hasMoreElements())
        {
            String key = (String) tg.nextElement();
            String target = (String) targets.get(key);
            cat += key + "\t";
            def += key + ":\t" + target + "\n";
        }
        System.out.println(def);
        System.out.println(cat);
	Enumeration m = metrics.keys();
	while (m.hasMoreElements())
	{
	    String name = (String) m.nextElement();
	    System.out.print(name + "\t");
	    String defm = "";
	    Enumeration t = targets.keys();
	    while (t.hasMoreElements())
	    {
		String key = (String) t.nextElement();
		String target = (String) targets.get(key);
		AbstractStringMetric metric = (AbstractStringMetric) metrics.get(name);
		defm = metric.getLongDescriptionString();
		//float res = metric.getUnNormalisedSimilarity(source, target);
		float res = metric.getSimilarity(source, target);
		System.out.print(df.format(res) + "\t");
	    }
	    System.out.println("");
	}
	
	
    }

}
