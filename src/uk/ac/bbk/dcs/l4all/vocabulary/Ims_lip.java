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
 * Vocabulary definitions from ims-lip.rdf 
 * @author Auto-generated by schemagen on 04 Jul 2005 16:33 
 */
public class Ims_lip {
    /** <p>The RDF model that holds the vocabulary terms</p> */
    private static Model m_model = ModelFactory.createDefaultModel();
    
    /** <p>The namespace of the vocabulary as a string</p> */
    public static final String NS = "http://www.dcs.bbk.ac.uk/~gpapa05/l4all/rdf/ims-lip#";
    
    /** <p>The namespace of the vocabulary as a string</p>
     *  @see #NS */
    public static String getURI() {return NS;}
    
    /** <p>The namespace of the vocabulary as a resource</p> */
    public static final Resource NAMESPACE = m_model.createResource( NS );
    
    public static final Property qcl_organisation = m_model.createProperty( "http://www.dcs.bbk.ac.uk/~gpapa05/l4all/rdf/ims-lip#qcl_organisation" );
    
    public static final Property interest_description = m_model.createProperty( "http://www.dcs.bbk.ac.uk/~gpapa05/l4all/rdf/ims-lip#interest_description" );
    
    public static final Property qcl_description = m_model.createProperty( "http://www.dcs.bbk.ac.uk/~gpapa05/l4all/rdf/ims-lip#qcl_description" );
    
    public static final Property qcl_date = m_model.createProperty( "http://www.dcs.bbk.ac.uk/~gpapa05/l4all/rdf/ims-lip#qcl_date" );
    
    public static final Property name = m_model.createProperty( "http://www.dcs.bbk.ac.uk/~gpapa05/l4all/rdf/ims-lip#name" );
    
    public static final Property qcl_title = m_model.createProperty( "http://www.dcs.bbk.ac.uk/~gpapa05/l4all/rdf/ims-lip#qcl_title" );
    
    public static final Property activity_date = m_model.createProperty( "http://www.dcs.bbk.ac.uk/~gpapa05/l4all/rdf/ims-lip#activity_date" );
    
    public static final Property disability_comment = m_model.createProperty( "http://www.dcs.bbk.ac.uk/~gpapa05/l4all/rdf/ims-lip#disability_comment" );
    
    public static final Property id = m_model.createProperty( "http://www.dcs.bbk.ac.uk/~gpapa05/l4all/rdf/ims-lip#id" );
    
    public static final Property access_disability_details = m_model.createProperty( "http://www.dcs.bbk.ac.uk/~gpapa05/l4all/rdf/ims-lip#access_disability_details" );
    
    public static final Property language_typename = m_model.createProperty( "http://www.dcs.bbk.ac.uk/~gpapa05/l4all/rdf/ims-lip#language_typename" );
    
    public static final Property goal_priority = m_model.createProperty( "http://www.dcs.bbk.ac.uk/~gpapa05/l4all/rdf/ims-lip#goal_priority" );
    
    public static final Property learner_qcl = m_model.createProperty( "http://www.dcs.bbk.ac.uk/~gpapa05/l4all/rdf/ims-lip#learner_qcl" );
    
    public static final Property qcl_level = m_model.createProperty( "http://www.dcs.bbk.ac.uk/~gpapa05/l4all/rdf/ims-lip#qcl_level" );
    
    public static final Property learning_style = m_model.createProperty( "http://www.dcs.bbk.ac.uk/~gpapa05/l4all/rdf/ims-lip#learning_style" );
    
    public static final Property email = m_model.createProperty( "http://www.dcs.bbk.ac.uk/~gpapa05/l4all/rdf/ims-lip#email" );
    
    public static final Property goal_date = m_model.createProperty( "http://www.dcs.bbk.ac.uk/~gpapa05/l4all/rdf/ims-lip#goal_date" );
    
    public static final Property disability_typename = m_model.createProperty( "http://www.dcs.bbk.ac.uk/~gpapa05/l4all/rdf/ims-lip#disability_typename" );
    
    public static final Property access_language_details = m_model.createProperty( "http://www.dcs.bbk.ac.uk/~gpapa05/l4all/rdf/ims-lip#access_language_details" );
    
    public static final Property activity_typename = m_model.createProperty( "http://www.dcs.bbk.ac.uk/~gpapa05/l4all/rdf/ims-lip#activity_typename" );
    
    public static final Property learner_goal = m_model.createProperty( "http://www.dcs.bbk.ac.uk/~gpapa05/l4all/rdf/ims-lip#learner_goal" );
    
    public static final Property learner_activity = m_model.createProperty( "http://www.dcs.bbk.ac.uk/~gpapa05/l4all/rdf/ims-lip#learner_activity" );
    
    public static final Property access_comment = m_model.createProperty( "http://www.dcs.bbk.ac.uk/~gpapa05/l4all/rdf/ims-lip#access_comment" );
    
    public static final Property goal_description = m_model.createProperty( "http://www.dcs.bbk.ac.uk/~gpapa05/l4all/rdf/ims-lip#goal_description" );
    
    public static final Property learner_accessibility = m_model.createProperty( "http://www.dcs.bbk.ac.uk/~gpapa05/l4all/rdf/ims-lip#learner_accessibility" );
    
    public static final Property learner_interest = m_model.createProperty( "http://www.dcs.bbk.ac.uk/~gpapa05/l4all/rdf/ims-lip#learner_interest" );
    
    public static final Property activity_description = m_model.createProperty( "http://www.dcs.bbk.ac.uk/~gpapa05/l4all/rdf/ims-lip#activity_description" );
    
    public static final Property interest_typename = m_model.createProperty( "http://www.dcs.bbk.ac.uk/~gpapa05/l4all/rdf/ims-lip#interest_typename" );
    
    public static final Property learner_trails = m_model.createProperty( "http://www.dcs.bbk.ac.uk/~gpapa05/l4all/rdf/ims-lip#learner_trails" );
    
    public static final Property goal_typename = m_model.createProperty( "http://www.dcs.bbk.ac.uk/~gpapa05/l4all/rdf/ims-lip#goal_typename" );
    
    /** <p>A class representing details about the language</p> */
    public static final Resource Language = m_model.createResource( "http://www.dcs.bbk.ac.uk/~gpapa05/l4all/rdf/ims-lip#Language" );
    
    /** <p>IMS LIP identification</p> */
    public static final Resource Identification = m_model.createResource( "http://www.dcs.bbk.ac.uk/~gpapa05/l4all/rdf/ims-lip#Identification" );
    
    /** <p>A class representing details about a learner''s disability</p> */
    public static final Resource Disability = m_model.createResource( "http://www.dcs.bbk.ac.uk/~gpapa05/l4all/rdf/ims-lip#Disability" );
    
    /** <p>IMS LIP QCL Class</p> */
    public static final Resource QCL = m_model.createResource( "http://www.dcs.bbk.ac.uk/~gpapa05/l4all/rdf/ims-lip#QCL" );
    
    /** <p>IMS LIP Accessibility Class</p> */
    public static final Resource Accessibility = m_model.createResource( "http://www.dcs.bbk.ac.uk/~gpapa05/l4all/rdf/ims-lip#Accessibility" );
    
    /** <p>IMS LIP Goal Class</p> */
    public static final Resource Goal = m_model.createResource( "http://www.dcs.bbk.ac.uk/~gpapa05/l4all/rdf/ims-lip#Goal" );
    
    /** <p>The class representing the learner on the system</p> */
    public static final Resource Learner = m_model.createResource( "http://www.dcs.bbk.ac.uk/~gpapa05/l4all/rdf/ims-lip#Learner" );
    
    /** <p>IMS LIP interest class</p> */
    public static final Resource Interest = m_model.createResource( "http://www.dcs.bbk.ac.uk/~gpapa05/l4all/rdf/ims-lip#Interest" );
    
    /** <p>IMS LIP identification</p> */
    public static final Resource Activity = m_model.createResource( "http://www.dcs.bbk.ac.uk/~gpapa05/l4all/rdf/ims-lip#Activity" );
    
}
