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
/*
 * MessageBuilder.java
 *
 * Created on 15 Δεκέμβριος 2005, 12:52 πμ
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package uk.ac.bbk.dcs.l4all.util;

import java.io.PrintWriter;

/**
 *
 * @author George
 */
public class MessageBuilder {
    
    /**
     * Creates the header of a successful response message
     * @param methodName The name of the method this message corresponds
     * @return A string representing the header of the message
     */
    public static String createSuccessMessageHeader(String methodName) {
        StringBuffer sb = new StringBuffer();
        sb.append("<main_message>\n");
        sb.append("<method_name>" + methodName + "</method_name>\n");
        sb.append("<message_type>SUCCESS</message_type>\n");
        sb.append("<message_code>0</message_code>\n");
        sb.append("<message_content>\n");
        
        return sb.toString();
    }
    
    /**
     * Creates the tail of a success response message
     * @return The tail of a success response XML message
     */
    public static String createSuccessMessageTail() {
        StringBuffer sb = new StringBuffer();
        
        sb.append("</message_content>\n");
        sb.append("</main_message>\n");
        
        return sb.toString();
    }
    
    /**
     * Creates an XML message containing an error message along with its code and description
     * @param methodName The name of the method, during the invocation of which, the error occurred
     * @param errorCode The code of the error
     * @param errorDescription A short description of the error
     * @return A string containing representing the XML message
     */
    public static String createErrorMessage(String methodName, int errorCode, String errorDescription) {
        StringBuffer sb = new StringBuffer();
        sb.append("<main_message>\n");
        sb.append("<method_name>" + methodName + "</method_name>\n");
        sb.append("<message_type>ERROR</message_type>\n");
        sb.append("<message_code>"+errorCode+"</message_code>\n");
        sb.append("<message_content>\n");
        sb.append("<error_description>" + errorDescription + "</error_description>\n");
        sb.append("</message_content>\n");
        sb.append("</main_message>\n");
        return sb.toString();
    }
}
