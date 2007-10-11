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
/* CVS $Id: $ */
package uk.ac.bbk.dcs.l4all.vocabulary; 
import com.hp.hpl.jena.rdf.model.*;
 
/**
 * Vocabulary definitions from dc-ext.rdf 
 * @author Auto-generated by schemagen on 16 Dec 2005 18:10 
 */
public class Dc_ext {
    /** <p>The RDF model that holds the vocabulary terms</p> */
    private static Model m_model = ModelFactory.createDefaultModel();
    
    /** <p>The namespace of the vocabulary as a string</p> */
    public static final String NS = "http://www.dcs.bbk.ac.uk/~gpapa05/l4all/rdf/dc-ext#";
    
    /** <p>The namespace of the vocabulary as a string</p>
     *  @see #NS */
    public static String getURI() {return NS;}
    
    /** <p>The namespace of the vocabulary as a resource</p> */
    public static final Resource NAMESPACE = m_model.createResource( NS );
    
    public static final Property level = m_model.createProperty( "http://www.dcs.bbk.ac.uk/~gpapa05/l4all/rdf/dc-ext#level" );
    
    public static final Property cost = m_model.createProperty( "http://www.dcs.bbk.ac.uk/~gpapa05/l4all/rdf/dc-ext#cost" );
    
    public static final Property skills = m_model.createProperty( "http://www.dcs.bbk.ac.uk/~gpapa05/l4all/rdf/dc-ext#skills" );
    
    public static final Property entry = m_model.createProperty( "http://www.dcs.bbk.ac.uk/~gpapa05/l4all/rdf/dc-ext#entry" );
    
    public static final Property modeOfStudy = m_model.createProperty( "http://www.dcs.bbk.ac.uk/~gpapa05/l4all/rdf/dc-ext#modeOfStudy" );
    
    public static final Property courseType = m_model.createProperty( "http://www.dcs.bbk.ac.uk/~gpapa05/l4all/rdf/dc-ext#courseType" );
    
    /** <p>This the extension regarding L4All courses</p> */
    public static final Resource Course = m_model.createResource( "http://www.dcs.bbk.ac.uk/~gpapa05/l4all/rdf/dc-ext#Course" );
    
}
