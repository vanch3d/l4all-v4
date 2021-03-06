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
package uk.ac.bbk.dcs.l4all.vocabulary; 
import com.hp.hpl.jena.rdf.model.*;
 
/**
 * Vocabulary definitions from trails.rdf 
 * @author Auto-generated by schemagen on 09 Jan 2006 18:58 
 */
public class Trails {
    
    
    /**
     * Constants used for the type of timeline
     */
    public static final String TYPE_USER = "user";		//< represent a user timeline
    public static final String TYPE_EXPERT = "expert";		//< represent an expert timeline
    public static final String TYPE_TEMPLATE = "template";	//< represent a template (recommendation) timeline
    
    
    /** <p>The RDF model that holds the vocabulary terms</p> */
    private static Model m_model = ModelFactory.createDefaultModel();
    
    /** <p>The namespace of the vocabulary as a string</p> */
    public static final String NS = "http://www.dcs.bbk.ac.uk/~gpapa05/l4all/rdf/trails#";
    
    /** <p>The namespace of the vocabulary as a string</p>
     *  @see #NS */
    public static String getURI() {return NS;}
    
    /** <p>The namespace of the vocabulary as a resource</p> */
    public static final Resource NAMESPACE = m_model.createResource( NS );
    
    public static final Property trail_end = m_model.createProperty( "http://www.dcs.bbk.ac.uk/~gpapa05/l4all/rdf/trails#trail_end" );
    
    public static final Property trail_episodes = m_model.createProperty( "http://www.dcs.bbk.ac.uk/~gpapa05/l4all/rdf/trails#trail_episodes" );
    
    public static final Property trail_keywords = m_model.createProperty( "http://www.dcs.bbk.ac.uk/~gpapa05/l4all/rdf/trails#trail_keywords" );
    
    public static final Property trail_owner = m_model.createProperty( "http://www.dcs.bbk.ac.uk/~gpapa05/l4all/rdf/trails#trail_owner" );
    
    public static final Property trail_annotation = m_model.createProperty( "http://www.dcs.bbk.ac.uk/~gpapa05/l4all/rdf/trails#trail_annotation" );
    
    public static final Property trail_description = m_model.createProperty( "http://www.dcs.bbk.ac.uk/~gpapa05/l4all/rdf/trails#trail_description" );
    
    public static final Property trail_name = m_model.createProperty( "http://www.dcs.bbk.ac.uk/~gpapa05/l4all/rdf/trails#trail_name" );
    
    public static final Property trail_id = m_model.createProperty( "http://www.dcs.bbk.ac.uk/~gpapa05/l4all/rdf/trails#trail_id" );
    
    public static final Property trail_start = m_model.createProperty( "http://www.dcs.bbk.ac.uk/~gpapa05/l4all/rdf/trails#trail_start" );
    
    public static final Property trail_privileges = m_model.createProperty( "http://www.dcs.bbk.ac.uk/~gpapa05/l4all/rdf/trails#trail_privileges" );

    public static final Property trail_type = m_model.createProperty( "http://www.dcs.bbk.ac.uk/~gpapa05/l4all/rdf/trails#trail_type" );

    /** <p>An RDFS class representing a user learning trail</p> */
    public static final Resource Trail = m_model.createResource( "http://www.dcs.bbk.ac.uk/~gpapa05/l4all/rdf/trails#Trail" );
    
}
