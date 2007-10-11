import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/*import com.wcohen.ss.api.Token;
import com.wcohen.ss.api.Tokenizer;
import com.wcohen.ss.tokens.SimpleTokenizer;*/

import uk.ac.bbk.dcs.l4all.servlets.GetTimelineDetailsServlet;
import uk.ac.bbk.dcs.l4all.vocabulary.EpisodeType;
import uk.ac.shef.wit.simmetrics.similaritymetrics.AbstractStringMetric;
import uk.ac.shef.wit.simmetrics.similaritymetrics.Jaro;
import uk.ac.shef.wit.simmetrics.similaritymetrics.JaroToken;
import uk.ac.shef.wit.simmetrics.similaritymetrics.Levenshtein;
import uk.ac.shef.wit.simmetrics.similaritymetrics.MongeElkan;
import uk.ac.shef.wit.simmetrics.similaritymetrics.NeedlemanWunchToken;


public class testStuff {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	    Calendar cal = Calendar.getInstance(TimeZone.getDefault());
	    System.out.println(cal.get(Calendar.YEAR));
	    
		String dateString = "2006/06/05c";
		String dateFormatString = "yyyy/MM/dd";
		
		String DATE_FORMAT = "yyyy/MM/dd";
		    SimpleDateFormat sdf = 
		          new SimpleDateFormat(DATE_FORMAT);
		    
		    System.out.println("Now : " + sdf.format(cal.getTime()));
		    cal.add(Calendar.YEAR, 100);
		    System.out.println("later : " + sdf.format(cal.getTime()));
		    
		SimpleDateFormat format = new SimpleDateFormat(dateFormatString);

		try {
			Date newDate = format.parse(dateString);
			//System.out.println(newDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		AbstractStringMetric metric = new NeedlemanWunchToken();
		//AbstractStringMetric metric = new Levenshtein();

		//this single line performs the similarity test
	        float result = metric.getSimilarity("A1 B1 C1 D1 E1 F1", "A1 B1 C1 D1 G2 H2");
	        System.out.println(result);
	        
	        /*SimpleTokenizer tk = new SimpleTokenizer(false, false);
	        Token[] ff = tk.tokenize("Wc00 Wc01 C D E F");
	        for (int i=0;i<ff.length;i++)
	            System.out.print(ff[i].toString() + " ");*/
	        
	        List mylist = EpisodeType.getAllTypes();
	        for (int i=0;i<mylist.size();i++)
	            System.out.println(mylist.get(i));
		
	        metric = new JaroToken();
	        float f1 = metric.getSimilarity("A B C D","B D C A");
	        metric = new Jaro();
	        float f2 = metric.getSimilarity("ABCD","BDCA");
	        System.out.println("f1 = " + f1 + "\nf2 = " + f2);
	        
	}
//		SearchSimilarServlet pp = new SearchSimilarServlet();
//		String ss = pp.getFormattedTimeline("nicolas");
//		
//		AbstractStringMetric metric = null;//new Levenshtein();
//		try 
//		{
//			metric = pp.getSimilarityMetric("Levenshtein");
//			float ff = metric.getSimilarity("nicolas", "nicole");
//			System.out.println(ff);
//		} 
//		catch (Exception e)
//		{
//			e.printStackTrace();
//		}
//	}

}
