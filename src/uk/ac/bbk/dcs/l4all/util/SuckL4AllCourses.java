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
package uk.ac.bbk.dcs.l4all.util;

import java.util.regex.* ;
import java.io.* ;
import uk.ac.bbk.dcs.l4all.beans.L4AllCourse;

public class SuckL4AllCourses {
    String inFile = "" ;
    BufferedReader in = null ;
    
    public SuckL4AllCourses() {
        
    }
    
    /**
     *  Parses the file containing the courses and loads them on the RDF repository
     */
    public void getAndLoadCourses(String filename) {
        try {
            L4AllCourse cmm = new L4AllCourse() ;
            
            in = new BufferedReader(new FileReader(filename));
            
            Pattern linePattern = Pattern.compile("(((.)*\t) | (\t){2}){15,}") ;
            Matcher lineMatcher = null ;
                        
            String fileLine = "" ;
            
           
            while ((fileLine = in.readLine()) != null) {
                lineMatcher = linePattern.matcher(fileLine);
                
                
                String [] fields = fileLine.split("\t") ;
                
               /* Course properties order
               (1) Element_name
               (2) Title
               (3) Creator
               (4) Subject and Keywords	(5) Description (6) Institution	(7) Member of Staff (Course Director)               (8) Start Date	(9) Type (10) Mode of Study	(11) Course Code	(12) URL	
               (13) Language	
               (14) Scope 
               (15) Entry Requirement	
               (16) Skills	
               (17) Costs
               */
                                
                String courseLevel = fields[0].replace('"',' ') ;
                String courseTitle = fields[1].replace('"',' ') ;
                String courseCreator = fields[2].replace('"',' ') ;
                String courseSubject = fields[3].replace('"',' ') ;
                String courseDescription = fields[4].replace('"',' ');
                String courseInstitution = fields[5].replace('"',' ') ;
                String courseMemberOfStaff = fields[6].replace('"',' ') ;
                String courseStartDate = fields[7].replace('"',' ') ;
                String courseType = fields[8].replace('"',' ') ;
                String courseModeOfStudy = fields[9].replace('"',' ') ;
                String courseCode = fields[10].replace('"',' ') ;
                String courseURL = fields[11].replace('"',' ') ;
                String courseLanguage = fields[12].replace('"',' ') ;
                String courseScope = fields[13].replace('"',' ') ;
                String courseEntryReq = fields[14].replace('"',' ');
                
                String courseSkills = " " ;
                if (fields.length == 16) {
                    courseSkills = fields[15] ;
                }
                
                String courseCost = " "  ;
                if (fields.length == 17)
                    courseCost = fields[16] ;
                System.out.println("Level: " + courseLevel + "\tTitle:" + courseTitle + "\tScope:" + courseScope + "\tURL:" + courseURL + "\tCost:" + courseCost + "\tStart date:" + courseStartDate + "\tMember of Staff:" + courseMemberOfStaff + "\tMode of Study:" + courseModeOfStudy + "\tEntry requirements:" + courseEntryReq);
                
                cmm.setLevel(courseLevel);
                cmm.setTitle(courseTitle);
                cmm.setCreator(courseCreator);
                cmm.setSubject(courseSubject);
                cmm.setDescription(courseDescription);
                cmm.setInstitution(courseInstitution);
                cmm.setMemberOfStaff(courseMemberOfStaff);
                cmm.setStartDate(courseStartDate);
                cmm.setCourseType(courseType);
                cmm.setModeOfStudy(courseModeOfStudy);
               
                if ("".equals(courseCode) || null == courseCode)
                    cmm.setCourseCode(String.valueOf(System.currentTimeMillis()));
                else 
                    cmm.setCourseCode(courseCode);
                
                cmm.setUrl(courseURL);
                cmm.setLanguage(courseLanguage);
                cmm.setScope(courseScope);
                cmm.setEntryRequirements(courseEntryReq);
                
                cmm.createNewCourse();
            }
            
        }
        
        catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
    public static void main(String [] args) {
        if (args.length < 1) {
            System.err.println("Please specify the name of the file to suck the courses from\n");
            System.exit(0);
        }
        
        SuckL4AllCourses l4allCourses = new SuckL4AllCourses() ;
        l4allCourses.getAndLoadCourses(args[0]) ;
        
    }
}
