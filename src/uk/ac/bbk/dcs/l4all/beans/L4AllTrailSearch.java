/*
* Copyright (C) 2005 Birkbeck College University of London
*
* This program is free software; you can redistribute it and/or
* modify it under the terms of the GNU General Public License
* as published by the Free Software Foundation; either version 2
* of the License, or (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program; if not, write to the Free Software
* Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
*/

package uk.ac.bbk.dcs.l4all.beans;

//import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import uk.ac.bbk.dcs.l4all.db.RDFRepositoryManager;
import uk.ac.bbk.dcs.l4all.util.RDFGraphUtil;
import uk.ac.bbk.dcs.l4all.vocabulary.Episode;
import uk.ac.bbk.dcs.l4all.vocabulary.L4all_user;
import uk.ac.bbk.dcs.l4all.vocabulary.Trails;

import com.hp.hpl.jena.db.ModelRDB;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Seq;
import com.hp.hpl.jena.rdf.model.SimpleSelector;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;

public class L4AllTrailSearch {
	private static final String	MODEL_NAME	= "l4all_users";

	private static ModelRDB		model		= RDFRepositoryManager.getModel(MODEL_NAME);

	// ////////////////////// Trail Search methods
	// //////////////////////////////

	/**
	 * Searchs for timelines given a part of their name
	 * 
	 * @param userTrailsURI
	 * @param trailNamePart
	 * @param model
	 * @return
	 */
	public static Set searchByName(String _name, String username) {
		final String name = _name;
		Set result = new HashSet();

		StmtIterator iter = model.listStatements(new SimpleSelector(null, Trails.trail_name, (RDFNode) null) {
			public boolean selects(Statement s) {
				boolean isMatched = false;
				String obj = s.getString();
				Pattern p = Pattern.compile("(.*)(?i)" + name + "(.*)");
				Matcher m = p.matcher(obj);
				isMatched = m.matches();
				return isMatched;
			}
		});

		while (iter.hasNext()) {
			// System.out.println("searchByDescription: " +
			// ((Resource)(iter.nextStatement().getSubject())).toString());
			result.add((Resource) iter.nextStatement().getSubject());
		}

		return result;
	}

	/**
	 * Searches for timelines given a keyword. The keyword is matched against
	 * the name and the description and the keywords of the timeline
	 * 
	 * @param userTrailsURI
	 * @param trailKeyword
	 * @param model
	 * @return
	 */
	public static Set searchByTrailsKeywords(String _trailKeyword, String username) {
		final String trailKeyword = _trailKeyword;
		Set result = new HashSet();

		StmtIterator iter = model.listStatements(new SimpleSelector(null, Trails.trail_keywords, (RDFNode) null) {
			public boolean selects(Statement s) {
				boolean isMatched = false;
				String obj = s.getString();
				Pattern p = Pattern.compile("(.*)(?i)" + trailKeyword + "(.*)");
				Matcher m = p.matcher(obj);
				isMatched = m.matches();
				return isMatched;
			}
		});

		while (iter.hasNext()) {
			// System.out.println("searchByDescription: " +
			// ((Resource)(iter.nextStatement().getSubject())).toString());
			result.add((Resource) iter.nextStatement().getSubject());
		}

		return result;
	}

	/**
	 * Search the user trails by the description given
	 * 
	 * @param userTrailsURI
	 * @param trailDescriptionPart
	 * @param model
	 * @return
	 */
	public static Set searchByDescription(String _desc, String username) {
		final String desc = _desc;
		Set result = new HashSet();

		StmtIterator iter = model.listStatements(new SimpleSelector(null, Trails.trail_description, (RDFNode) null) {
			public boolean selects(Statement s) {
				boolean isMatched = false;
				String obj = s.getString();
				Pattern p = Pattern.compile("(.*)(?i)" + desc + "(.*)");
				Matcher m = p.matcher(obj);
				isMatched = m.matches();
				return isMatched;
			}
		});

		while (iter.hasNext()) {
			// System.out.println("searchByDescription: " +
			// ((Resource)(iter.nextStatement().getSubject())).toString());
			result.add((Resource) iter.nextStatement().getSubject());
		}

		return result;
	}

	/**
	 * Search for trails containing an episode with the given name
	 * 
	 * @param episodeName
	 * @param username
	 * @return
	 */
	public static Set searchByEpisodeName(String _episodeName, String username) {
		final String episodeName = _episodeName;
		Set result = new HashSet();

		StmtIterator iter = model.listStatements(new SimpleSelector(null, Episode.category, (RDFNode) null) {
			public boolean selects(Statement s) {
				boolean isMatched = false;
				String obj = s.getString();
//				Pattern p = Pattern.compile("(.*)(?i)" + episodeName + "(.*)");
				Pattern p = Pattern.compile("(?i)" + episodeName + "");
				Matcher m = p.matcher(obj);
				isMatched = m.matches();
				return isMatched;
			}
		});

		while (iter.hasNext()) {
			// System.out.println("searchByEpisodeTitle: " +
			// ((Resource)(iter.nextStatement().getSubject())).toString());
			result.add((Resource) iter.nextStatement().getSubject());
		}

		return result;
	}

	/**
	 * The wrapper method for searching the trails
	 * 
	 * @param name
	 * @param desc
	 * @param keywords
	 * @param episodeName
	 * @param myTrails
	 * @param otherTrails
	 * @param username
	 * @return
	 */
	public static Trail[] searchForTrails(String keywords, String episodeName, boolean myTrails, boolean otherTrails,
			String username) {

		if (!keywords.trim().equals("") && episodeName.trim().equals(""))
			episodeName = keywords;
		
		// Set nameResults = new HashSet();
		// Set descResults = new HashSet();
		// Set keywordResults = new HashSet();
		Set episodeResults = new HashSet();

		Set totalResults = new HashSet();

		// Perform the search
		// if (!keywords.trim().equals(""))
		// nameResults = searchByName(keywords, username);

		// if (!keywords.trim().equals(""))
		// descResults = searchByDescription(keywords, username);

		// if (!keywords.trim().equals(""))
		// keywordResults = searchByTrailsKeywords(keywords, username);
		// else
		// keywordResults = searchByTrailsKeywords(" ", username);

		if (!episodeName.trim().equals(""))
			episodeResults = searchByEpisodeName(episodeName, username);

		// Accumulate the results
		// if (descResults != null && !descResults.isEmpty()) {
		// if (!episodeResults.isEmpty())
		// descResults.retainAll(episodeResults);
		//			
		// totalResults = descResults ;
		// } else
		if (episodeResults != null && !episodeResults.isEmpty())
			totalResults = episodeResults;

		// Extra the trail details from the results
		Vector trails = new Vector();
		Iterator iter = totalResults.iterator();
		int index = 0;

		while (iter.hasNext()) {
			Object obj = iter.next();

			if (obj instanceof HashSet) {
				HashSet hs = (HashSet) obj;
				Iterator it = hs.iterator();

				while (it.hasNext()) {
					Object _obj = it.next();
					if (_obj instanceof Resource) {
						index++;
						Resource trail = (Resource) _obj;
						String tName = trail.getProperty(Trails.trail_name).getString();
						String tDescription = trail.getProperty(Trails.trail_description).getString();
						String tKeywords = trail.getProperty(Trails.trail_keywords).getString();
						String tStart = trail.getProperty(Trails.trail_start).getString();
						String tEnd = trail.getProperty(Trails.trail_end).getString();
						String tType = trail.getProperty(Trails.trail_type).getString();
						Seq tEpisodes = trail.getProperty(Trails.trail_episodes).getSeq();
						long tID = trail.getProperty(Trails.trail_id).getLong();

						Trail cTrail = new Trail();
						cTrail.setTrailID(tID);
						cTrail.setTrailName(tName);
						cTrail.setTrailDescription(tDescription);
						cTrail.setTrailKeywords(tKeywords);
						cTrail.setStart(tStart);
						cTrail.setEnd(tEnd);
						cTrail.setTrailType(tType);
						cTrail.setTrailEpisodes(getEpisodes(tEpisodes));

						trails.add(cTrail);
					}
				}
			} else if (obj instanceof Resource) {
				index++;
				Resource episode = (Resource) obj;

				RDFNode _trail = episode.getProperty(Episode.owner_tmln).getObject();

				if (_trail instanceof Literal) {
					Literal trailName = (Literal)_trail;
					Set trailSet = searchByName(trailName.getString(), username);
					Iterator trailIter = trailSet.iterator();
					
					while (trailIter.hasNext()) {
						Object _obj = trailIter.next();
						
						if (_obj instanceof Resource) {
							Resource trail = (Resource)_obj;
							String tDescription = trail.getProperty(Trails.trail_description).getString();
							String tName = trail.getProperty(Trails.trail_name).getString();
							String tKeywords = trail.getProperty(Trails.trail_keywords).getString();
							String tStart = trail.getProperty(Trails.trail_start).getString();
							String tEnd = trail.getProperty(Trails.trail_end).getString();
							String tType = trail.getProperty(Trails.trail_type).getString();
							Seq tEpisodes = trail.getProperty(Trails.trail_episodes).getSeq();
							long tID = trail.getProperty(Trails.trail_id).getLong();

							Trail cTrail = new Trail();
							cTrail.setTrailID(tID);
							cTrail.setTrailName(tName);
							cTrail.setTrailDescription(tDescription);
							cTrail.setTrailKeywords(tKeywords);
							cTrail.setStart(tStart);
							cTrail.setEnd(tEnd);
							cTrail.setTrailType(tType);
							cTrail.setTrailEpisodes(getEpisodes(tEpisodes));

							boolean flag = false;
							for (int j=0; j < trails.size() ; j++) {
								if (((Trail)trails.get(j)).getTrailName().equals(tName)) {
									flag=true;
									break;
								}
							}
							
							if (!flag)
								trails.add(cTrail);
						}
					}
				}
				
			}
		}

		return (Trail[]) trails.toArray(new Trail[trails.size()]);
	}

	private static TrailEpisode getEpisodeDetails(Resource episode) {

		String title = episode.getProperty(Episode.title).getString();
		String category = episode.getProperty(Episode.category).getString();
		String start = episode.getProperty(Episode.start).getString();
		String end = episode.getProperty(Episode.end).getString();
		String url = episode.getProperty(Episode.url).getString();
		String description = episode.getProperty(Episode.description).getString();
		String ownerTmln = episode.getProperty(Episode.owner_tmln).getString();
		String strNat = null;
		String prim = null;
		String sec = null;
		
		Statement stat = episode.getProperty(Episode.nature);
		if (stat==null)
		{
		    strNat = Episode.NATURE_FACT;
		    //TODO	This is to ensure that the missing information if put into
	            //	the database. A better way needs to be found 	
		    episode.addProperty(Episode.nature, strNat);
	            model.commit();
		}
		else
		    strNat = stat.getString();

		stat = null;
		stat = episode.getProperty(Episode.prm_classif);
		if (stat==null)
		{
		    prim = "000";
		    episode.addProperty(Episode.prm_classif, strNat);
	            model.commit();
		}
		else
		    prim = stat.getString();
		
		stat = null;
		stat = episode.getProperty(Episode.sec_classif);
		if (stat==null)
		{
		    sec = "000";
		    episode.addProperty(Episode.sec_classif, strNat);
	            model.commit();
		}
		else
		    sec = stat.getString();
		
		TrailEpisode tEpisode = new TrailEpisode();

		tEpisode.setTitle(title);
		tEpisode.setNature(strNat);
		tEpisode.setCategory(category);
		tEpisode.setStart(start);
		tEpisode.setEnd(end);
		tEpisode.setUrl(url);
		tEpisode.setDescription(description);
		tEpisode.setOwnerTmln(ownerTmln);
		tEpisode.setPrimary(prim);
		tEpisode.setSecondary(sec);

		return tEpisode;
	}

	public static TrailEpisode[] getEpisodes(Seq episodeSeq) {
		Vector vEpisodes = new Vector();
		Object[] seqContents = RDFGraphUtil.getContainerObjectContents(episodeSeq);

		for (int i = 0; i < seqContents.length; i++) {
			if (seqContents[i] instanceof Resource) {
				Resource episode = (Resource) seqContents[i];
				TrailEpisode tEpisode = getEpisodeDetails(episode);
				vEpisodes.add(tEpisode);
			}
		}

		return (TrailEpisode[]) vEpisodes.toArray(new TrailEpisode[vEpisodes.size()]);
	}
}
