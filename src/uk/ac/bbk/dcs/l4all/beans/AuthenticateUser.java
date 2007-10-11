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

import uk.ac.bbk.dcs.l4all.util.RDFGraphUtil;
import uk.ac.bbk.dcs.l4all.vocabulary.L4all_user;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;

public class AuthenticateUser {
	private static final String BASE_USER_URL = "http://www.dcs.bbk.ac.uk/~gpapa05/l4all/users/";
	
    public AuthenticateUser() {
        super();
    }

    public boolean validate(String username, String password) {
        L4AllUserManager userInfo = new L4AllUserManager();
        Model model = userInfo.getModel();
        
        if (RDFGraphUtil.hasResource(BASE_USER_URL + username, model)) {
        	Resource learner = model.getResource(BASE_USER_URL + username);
        	
        	Resource identifier = model.getResource(BASE_USER_URL + username + "/"+ "Identifier");
        	
        	if (identifier == null)
        		return false ;
        	
    		Statement storedPassword = identifier.getProperty(L4all_user.password);
    		String passwd = storedPassword.getString();
    		
    		if (passwd.equals(password)) {
    			
    			return true ;
    		}
    		else 
    			return false ;
        } else 
        	return false ;
    }
    
}
