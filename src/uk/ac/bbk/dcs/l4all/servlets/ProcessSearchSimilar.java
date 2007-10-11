package uk.ac.bbk.dcs.l4all.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class for Servlet: processSearchSimilar
 *
 */
public class ProcessSearchSimilar extends HttpServlet implements Servlet
{
    
    /* (non-Java-doc)
     * @see javax.servlet.http.HttpServlet#HttpServlet()
     */
    public ProcessSearchSimilar()
    {
	super();
    }

    /* (non-Java-doc)
     * @see javax.servlet.http.HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request,
	    HttpServletResponse response) throws ServletException, IOException
    {
	processRequest(request,response);
    }

    /* (non-Java-doc)
     * @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request,
	    HttpServletResponse response) throws ServletException, IOException
    {
	processRequest(request,response);
    }
    
     
    protected void processRequest(HttpServletRequest request,HttpServletResponse response) throws ServletException 
    {
	HttpSession session = request.getSession();

	String username = (String) session.getAttribute(ServletUtilities.SESSION_USERNAME);
	String metricname = request.getParameter("metric");

	SearchSimilarServlet searxh = new SearchSimilarServlet();
        String xml = searxh.processRequest(username, metricname);
	
        ServletContext context = getServletContext();
	RequestDispatcher rd = null ;
	rd = context.getRequestDispatcher("/jsp/searchTimeline.jsp");
	request.setAttribute("metric", metricname);
	request.setAttribute("data", xml);
	try
	{
	    rd.include(request, response);
	} catch (IOException e)
	{
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}	


	
//	
//	ServletContext context = getServletContext();
//	
//	javax.xml.transform.TransformerFactory tFactory = 
//            javax.xml.transform.TransformerFactory.newInstance();
//	
//	response.setContentType("text/html");
//	response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
//	response.setHeader("Pragma","no-cache"); //HTTP 1.0
//
//	PrintWriter out = null;
//	try
//	{
//	    out = response.getWriter();
//	} catch (IOException e1)
//	{
//	    // TODO Auto-generated catch block
//	    e1.printStackTrace();
//	}
//	
//	
//        SearchSimilarServlet searxh = new SearchSimilarServlet();
//        String xml = searxh.processRequest("nicolas", "NeedlemanWunchTimeline");
//        String FS = System.getProperty("file.separator");
//        String ctx = getServletContext().getRealPath("") + FS;	
//        	
// 	out.println("<body bgcolor=\"#BABA96\" onload='SaveSetting();'>");
//	out.println("<h2>You have registered successfully.</h2>\n");
//        Source xmlSource = new StreamSource(new StringBufferInputStream(xml));
//        try
//        {
//            Source xslSource = new StreamSource(new URL("file", "", ctx+"test.xsl").openStream());
//            // Generate the transformer.
//            Transformer transformer = tFactory.newTransformer(xslSource);
//            // Perform the transformation, sending the output to the response.
//            transformer.transform(xmlSource, new StreamResult(out));
//            
//        } catch (MalformedURLException e)
//        {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (IOException e)
//        {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (TransformerConfigurationException e)
//        {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (TransformerException e)
//        {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//	out.println("</body>\n");
	

//	String serverURL = "http://" + request.getServerName() + ":" + request.getServerPort() + 
//	request.getContextPath();// + "/";
//	 String surl = "/jsp/SearchSimilar?metric=NeedlemanWunchTimeline&username=nicolas";
//         URL url;
//	try
//	{
//	    url = new URL(serverURL+surl);
//	    URLConnection con = url.openConnection();
//	    con.setDoOutput(true);
//		InputStream is = con.getInputStream();
//		String FS = System.getProperty("file.separator");
//		String ctx = getServletContext().getRealPath("") + FS;		
//	      // Get the XML input document and the stylesheet.
//		Source xmlSource = new StreamSource(is);
//		Source xslSource = new StreamSource(new URL("file", "", ctx+"test.xsl").openStream());
////		 Generate the transformer.
//		      Transformer transformer = tFactory.newTransformer(xslSource);
//		      // Perform the transformation, sending the output to the response.
//		      transformer.transform(xmlSource, new StreamResult(out));
////	        // any response?
////	        InputStreamReader isr = new InputStreamReader(is);
////	        BufferedReader br = new BufferedReader(isr);
////	        String line = null;
////	        while ( (line = br.readLine()) != null)
////	        {
////	            System.out.println("line: " + line);
////	        }
//	} catch (MalformedURLException e1)
//	{
//	    // TODO Auto-generated catch block
//	    e1.printStackTrace();
//	}
//	catch (IOException e1)
//	{
//	    // TODO Auto-generated catch block
//	    e1.printStackTrace();
//	} catch (TransformerConfigurationException e)
//	{
//	    // TODO Auto-generated catch block
//	    e.printStackTrace();
//	} catch (TransformerException e)
//	{
//	    // TODO Auto-generated catch block
//	    e.printStackTrace();
//	}
// 	out.println("<body bgcolor=\"#BABA96\" onload='SaveSetting();'>");
//	out.println("<h2>You have registered successfully.</h2>\n");

//	RequestDispatcher rd = request.getRequestDispatcher("/jsp/SearchSimilar?metric=NeedlemanWunchTimeline");
////	try
////	    {
////		//rd.include(request, response);
////	    } catch (IOException e)
////	    {
////		// TODO Auto-generated catch block
////		e.printStackTrace();
////	    }
//	
//
//	    rd = request.getRequestDispatcher("/getTimeline?username=nicolas");
//		try
//		    {
//			rd.include(request, response);
//		    } catch (IOException e)
//		    {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		    }

	
//	out.println("</body>");
	
   }
}