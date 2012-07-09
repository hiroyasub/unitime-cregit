begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.2 (University Timetabling Application)  * Copyright (C) 2008 - 2010, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
end_comment

begin_package
package|package
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|form
package|;
end_package

begin_import
import|import
name|java
operator|.
name|text
operator|.
name|ParseException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|text
operator|.
name|SimpleDateFormat
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|ArrayList
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Date
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|List
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|servlet
operator|.
name|http
operator|.
name|HttpServletRequest
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|struts
operator|.
name|action
operator|.
name|ActionErrors
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|struts
operator|.
name|action
operator|.
name|ActionMapping
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|struts
operator|.
name|action
operator|.
name|ActionMessage
import|;
end_import

begin_import
import|import
name|org
operator|.
name|unitime
operator|.
name|localization
operator|.
name|impl
operator|.
name|Localization
import|;
end_import

begin_import
import|import
name|org
operator|.
name|unitime
operator|.
name|localization
operator|.
name|messages
operator|.
name|CourseMessages
import|;
end_import

begin_import
import|import
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|ClassInstructor
import|;
end_import

begin_import
import|import
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|DepartmentalInstructor
import|;
end_import

begin_import
import|import
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|Location
import|;
end_import

begin_import
import|import
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|Preference
import|;
end_import

begin_import
import|import
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|dao
operator|.
name|DepartmentalInstructorDAO
import|;
end_import

begin_import
import|import
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|dao
operator|.
name|LocationDAO
import|;
end_import

begin_import
import|import
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|util
operator|.
name|DynamicList
import|;
end_import

begin_import
import|import
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|util
operator|.
name|DynamicListObjectFactory
import|;
end_import

begin_comment
comment|/**   * MyEclipse Struts  * Creation date: 12-08-2005  *   * XDoclet definition:  * @struts:form name="classEditForm"  */
end_comment

begin_class
specifier|public
class|class
name|ClassEditForm
extends|extends
name|PreferencesForm
block|{
comment|// --------------------------------------------------------- Class Constants
comment|/** 	 * Comment for<code>serialVersionUID</code> 	 */
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|3257849883023915058L
decl_stmt|;
comment|// Messages
specifier|protected
specifier|final
specifier|static
name|CourseMessages
name|MSG
init|=
name|Localization
operator|.
name|create
argument_list|(
name|CourseMessages
operator|.
name|class
argument_list|)
decl_stmt|;
comment|/** Class Start/End Date Format **/
specifier|private
specifier|final
name|SimpleDateFormat
name|dateFormat
init|=
operator|new
name|SimpleDateFormat
argument_list|(
literal|"MM/dd/yyyy"
argument_list|)
decl_stmt|;
comment|// --------------------------------------------------------- Instance Variables
specifier|private
name|Integer
name|nbrRooms
decl_stmt|;
specifier|private
name|Integer
name|expectedCapacity
decl_stmt|;
specifier|private
name|Long
name|classId
decl_stmt|;
specifier|private
name|Long
name|parentClassId
decl_stmt|;
specifier|private
name|String
name|section
decl_stmt|;
specifier|private
name|Long
name|managingDept
decl_stmt|;
specifier|private
name|Long
name|subpart
decl_stmt|;
specifier|private
name|String
name|className
decl_stmt|;
specifier|private
name|String
name|parentClassName
decl_stmt|;
specifier|private
name|String
name|itypeDesc
decl_stmt|;
specifier|private
name|List
name|instrLead
decl_stmt|;
specifier|private
name|String
name|managingDeptLabel
decl_stmt|;
specifier|private
name|String
name|notes
decl_stmt|;
specifier|private
name|List
name|instructors
decl_stmt|;
specifier|private
name|List
name|instrPctShare
decl_stmt|;
specifier|private
name|List
name|assignments
decl_stmt|;
specifier|private
name|Long
name|datePattern
decl_stmt|;
specifier|private
name|String
name|subjectAreaId
decl_stmt|;
specifier|private
name|String
name|instrOfferingId
decl_stmt|;
specifier|private
name|String
name|courseName
decl_stmt|;
specifier|private
name|String
name|courseTitle
decl_stmt|;
specifier|private
name|Boolean
name|displayInstructor
decl_stmt|;
specifier|private
name|String
name|schedulePrintNote
decl_stmt|;
specifier|private
name|String
name|classSuffix
decl_stmt|;
specifier|private
name|Boolean
name|displayInScheduleBook
decl_stmt|;
specifier|private
name|Integer
name|maxExpectedCapacity
decl_stmt|;
specifier|private
name|Float
name|roomRatio
decl_stmt|;
specifier|private
name|Integer
name|minRoomLimit
decl_stmt|;
specifier|private
name|Boolean
name|unlimitedEnroll
decl_stmt|;
specifier|private
name|Integer
name|enrollment
decl_stmt|;
comment|//TODO Reservations Bypass - to be removed later
specifier|private
name|Boolean
name|isCrosslisted
decl_stmt|;
comment|//End Bypass
comment|// --------------------------------------------------------- Classes
comment|/** Factory to create dynamic list element for Instructors */
specifier|protected
name|DynamicListObjectFactory
name|factoryInstructors
init|=
operator|new
name|DynamicListObjectFactory
argument_list|()
block|{
specifier|public
name|Object
name|create
parameter_list|()
block|{
return|return
operator|new
name|String
argument_list|(
name|Preference
operator|.
name|BLANK_PREF_VALUE
argument_list|)
return|;
block|}
block|}
decl_stmt|;
comment|// --------------------------------------------------------- Methods
comment|/**       * Validate input data      * @param mapping      * @param request      * @return ActionErrors      */
specifier|public
name|ActionErrors
name|validate
parameter_list|(
name|ActionMapping
name|mapping
parameter_list|,
name|HttpServletRequest
name|request
parameter_list|)
block|{
name|int
name|iRoomCapacity
init|=
operator|-
literal|1
decl_stmt|;
name|ActionErrors
name|errors
init|=
operator|new
name|ActionErrors
argument_list|()
decl_stmt|;
if|if
condition|(
name|nbrRooms
operator|!=
literal|null
operator|&&
name|nbrRooms
operator|.
name|intValue
argument_list|()
operator|<
literal|0
condition|)
name|errors
operator|.
name|add
argument_list|(
literal|"nbrRooms"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.generic"
argument_list|,
name|MSG
operator|.
name|errorNumberOfRoomsNegative
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|roomRatio
operator|==
literal|null
operator|||
name|roomRatio
operator|.
name|floatValue
argument_list|()
operator|<
literal|0.0f
condition|)
name|errors
operator|.
name|add
argument_list|(
literal|"nbrRooms"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.generic"
argument_list|,
name|MSG
operator|.
name|errorRoomRatioNegative
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|expectedCapacity
operator|==
literal|null
operator|||
name|expectedCapacity
operator|.
name|intValue
argument_list|()
operator|<
literal|0
condition|)
name|errors
operator|.
name|add
argument_list|(
literal|"expectedCapacity"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.generic"
argument_list|,
name|MSG
operator|.
name|errorMinimumExpectedCapacityNegative
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|maxExpectedCapacity
operator|==
literal|null
operator|||
name|maxExpectedCapacity
operator|.
name|intValue
argument_list|()
operator|<
literal|0
condition|)
name|errors
operator|.
name|add
argument_list|(
literal|"maxExpectedCapacity"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.generic"
argument_list|,
name|MSG
operator|.
name|errorMaximumExpectedCapacityNegative
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
if|else              if
condition|(
name|maxExpectedCapacity
operator|.
name|intValue
argument_list|()
operator|<
name|expectedCapacity
operator|.
name|intValue
argument_list|()
condition|)
name|errors
operator|.
name|add
argument_list|(
literal|"maxExpectedCapacity"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.generic"
argument_list|,
name|MSG
operator|.
name|errorMaximumExpectedCapacityLessThanMinimum
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|managingDept
operator|==
literal|null
operator|||
name|managingDept
operator|.
name|longValue
argument_list|()
operator|<=
literal|0
condition|)
name|errors
operator|.
name|add
argument_list|(
literal|"managingDept"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.generic"
argument_list|,
name|MSG
operator|.
name|errorRequiredClassOwner
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
comment|// Schedule print note has 2000 character limit
if|if
condition|(
name|schedulePrintNote
operator|!=
literal|null
operator|&&
name|schedulePrintNote
operator|.
name|length
argument_list|()
operator|>
literal|1999
condition|)
name|errors
operator|.
name|add
argument_list|(
literal|"notes"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.generic"
argument_list|,
name|MSG
operator|.
name|errorSchedulePrintNoteLongerThan1999
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
comment|// Notes has 1000 character limit
if|if
condition|(
name|notes
operator|!=
literal|null
operator|&&
name|notes
operator|.
name|length
argument_list|()
operator|>
literal|999
condition|)
name|errors
operator|.
name|add
argument_list|(
literal|"notes"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.generic"
argument_list|,
name|MSG
operator|.
name|errorNotesLongerThan999
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
comment|// At least one instructor is selected
if|if
condition|(
name|instructors
operator|.
name|size
argument_list|()
operator|>
literal|0
condition|)
block|{
comment|// Check no duplicates or blank instructors
if|if
condition|(
operator|!
name|super
operator|.
name|checkPrefs
argument_list|(
name|instructors
argument_list|)
condition|)
name|errors
operator|.
name|add
argument_list|(
literal|"instrPrefs"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.generic"
argument_list|,
name|MSG
operator|.
name|errorInvalidInstructorPreference
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
comment|/* -- 1 lead instructor not required             // Check Lead Instructor is set 	        if(instrLead==null  	                || instrLead.trim().length()==0 	                || !(new LongValidator().isValid(instrLead)) )  	            errors.add("instrLead",  	                    new ActionMessage("errors.required", "Lead Instructor") ); 	        */
comment|/* -- 100% percent share not required 	        // Check sum of all percent share = 100% 	        try { 	            int total = 0; 		        for (Iterator iter=instrPctShare.iterator(); iter.hasNext(); ) {	             		            String pctShare = iter.next().toString(); 		            if(Integer.parseInt(pctShare)<=0) { 			            errors.add("instrPctShare",  			                    new ActionMessage( 			                            "errors.integerGt", "Percent Share", "0") ); 			        } 		            total += Integer.parseInt(pctShare); 		        } 		        if(total!=100) { 		            errors.add("instrPctShare",  		                    new ActionMessage( 		                            "errors.generic", 		                            "Sum of all instructor percent shares must equal 100%") ); 		        } 	        } 	        catch (Exception ex) { 	            errors.add("instrPctShare",                      new ActionMessage(                             "errors.generic",                             "Invalid instructor percent shares specified.") ); 	        }	    	        */
block|}
comment|// Check that any room with a preference required has capacity>= room capacity for the class
if|if
condition|(
name|iRoomCapacity
operator|>
literal|0
condition|)
block|{
name|List
name|rp
init|=
name|this
operator|.
name|getRoomPrefs
argument_list|()
decl_stmt|;
name|List
name|rpl
init|=
name|this
operator|.
name|getRoomPrefLevels
argument_list|()
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|rpl
operator|.
name|size
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|String
name|pl
init|=
name|rpl
operator|.
name|get
argument_list|(
name|i
argument_list|)
operator|.
name|toString
argument_list|()
decl_stmt|;
if|if
condition|(
name|pl
operator|.
name|trim
argument_list|()
operator|.
name|equalsIgnoreCase
argument_list|(
literal|"1"
argument_list|)
condition|)
block|{
name|String
name|roomId
init|=
name|rp
operator|.
name|get
argument_list|(
name|i
argument_list|)
operator|.
name|toString
argument_list|()
decl_stmt|;
name|Location
name|room
init|=
operator|new
name|LocationDAO
argument_list|()
operator|.
name|get
argument_list|(
operator|new
name|Long
argument_list|(
name|roomId
argument_list|)
argument_list|)
decl_stmt|;
name|int
name|rCap
init|=
name|room
operator|.
name|getCapacity
argument_list|()
operator|.
name|intValue
argument_list|()
decl_stmt|;
if|if
condition|(
name|rCap
operator|<
name|iRoomCapacity
condition|)
block|{
name|errors
operator|.
name|add
argument_list|(
literal|"roomPref"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.generic"
argument_list|,
name|MSG
operator|.
name|errorRequiredRoomTooSmall
argument_list|(
name|room
operator|.
name|getLabel
argument_list|()
argument_list|,
name|rCap
argument_list|,
name|iRoomCapacity
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
comment|// Check Other Preferences
name|errors
operator|.
name|add
argument_list|(
name|super
operator|.
name|validate
argument_list|(
name|mapping
argument_list|,
name|request
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|errors
return|;
block|}
comment|/**       * Method reset      * @param mapping      * @param request      */
specifier|public
name|void
name|reset
parameter_list|(
name|ActionMapping
name|mapping
parameter_list|,
name|HttpServletRequest
name|request
parameter_list|)
block|{
name|nbrRooms
operator|=
literal|null
expr_stmt|;
name|expectedCapacity
operator|=
literal|null
expr_stmt|;
name|classId
operator|=
literal|null
expr_stmt|;
name|section
operator|=
literal|null
expr_stmt|;
name|managingDept
operator|=
literal|null
expr_stmt|;
name|subpart
operator|=
literal|null
expr_stmt|;
name|className
operator|=
literal|""
expr_stmt|;
name|courseName
operator|=
literal|""
expr_stmt|;
name|courseTitle
operator|=
literal|""
expr_stmt|;
name|parentClassName
operator|=
literal|"-"
expr_stmt|;
name|itypeDesc
operator|=
literal|""
expr_stmt|;
name|datePattern
operator|=
literal|null
expr_stmt|;
name|instrLead
operator|=
name|DynamicList
operator|.
name|getInstance
argument_list|(
operator|new
name|ArrayList
argument_list|()
argument_list|,
name|factoryInstructors
argument_list|)
expr_stmt|;
name|managingDeptLabel
operator|=
literal|"-"
expr_stmt|;
name|notes
operator|=
literal|""
expr_stmt|;
name|displayInstructor
operator|=
literal|null
expr_stmt|;
name|schedulePrintNote
operator|=
literal|null
expr_stmt|;
name|classSuffix
operator|=
literal|null
expr_stmt|;
name|displayInScheduleBook
operator|=
literal|null
expr_stmt|;
name|maxExpectedCapacity
operator|=
literal|null
expr_stmt|;
name|roomRatio
operator|=
literal|null
expr_stmt|;
name|unlimitedEnroll
operator|=
literal|null
expr_stmt|;
comment|//TODO Reservations Bypass - to be removed later
name|isCrosslisted
operator|=
literal|null
expr_stmt|;
comment|// End Bypass
name|instructors
operator|=
name|DynamicList
operator|.
name|getInstance
argument_list|(
operator|new
name|ArrayList
argument_list|()
argument_list|,
name|factoryInstructors
argument_list|)
expr_stmt|;
name|instrPctShare
operator|=
name|DynamicList
operator|.
name|getInstance
argument_list|(
operator|new
name|ArrayList
argument_list|()
argument_list|,
name|factoryInstructors
argument_list|)
expr_stmt|;
name|assignments
operator|=
literal|null
expr_stmt|;
name|enrollment
operator|=
literal|null
expr_stmt|;
name|super
operator|.
name|reset
argument_list|(
name|mapping
argument_list|,
name|request
argument_list|)
expr_stmt|;
block|}
comment|/**      * @return Returns the classId.      */
specifier|public
name|Long
name|getClassId
parameter_list|()
block|{
return|return
name|classId
return|;
block|}
comment|/**      * @param classId The classId to set.      */
specifier|public
name|void
name|setClassId
parameter_list|(
name|Long
name|classId
parameter_list|)
block|{
name|this
operator|.
name|classId
operator|=
name|classId
expr_stmt|;
block|}
comment|/**      * @return Returns the section.      */
specifier|public
name|String
name|getSection
parameter_list|()
block|{
return|return
name|section
return|;
block|}
comment|/**      * @param section The section to set.      */
specifier|public
name|void
name|setSection
parameter_list|(
name|String
name|section
parameter_list|)
block|{
name|this
operator|.
name|section
operator|=
name|section
expr_stmt|;
block|}
comment|/**      * @return Returns the className.      */
specifier|public
name|String
name|getClassName
parameter_list|()
block|{
return|return
name|className
return|;
block|}
comment|/**      * @param className The className to set.      */
specifier|public
name|void
name|setClassName
parameter_list|(
name|String
name|className
parameter_list|)
block|{
name|this
operator|.
name|className
operator|=
name|className
expr_stmt|;
block|}
comment|/**      * @return Returns the assignments.      */
specifier|public
name|List
name|getAssignments
parameter_list|()
block|{
return|return
name|assignments
return|;
block|}
comment|/**      * @return Returns the assignments.      */
specifier|public
name|String
name|getAssignments
parameter_list|(
name|int
name|key
parameter_list|)
block|{
return|return
name|assignments
operator|.
name|get
argument_list|(
name|key
argument_list|)
operator|.
name|toString
argument_list|()
return|;
block|}
comment|/**      * @param key The key to set.      * @param value The value to set.      */
specifier|public
name|void
name|setAssignments
parameter_list|(
name|int
name|key
parameter_list|,
name|Object
name|value
parameter_list|)
block|{
name|this
operator|.
name|assignments
operator|.
name|set
argument_list|(
name|key
argument_list|,
name|value
argument_list|)
expr_stmt|;
block|}
comment|/**      * @param assignments The assignments to set.      */
specifier|public
name|void
name|setAssignments
parameter_list|(
name|List
name|assignments
parameter_list|)
block|{
name|this
operator|.
name|assignments
operator|=
name|assignments
expr_stmt|;
block|}
specifier|public
name|Long
name|getDatePattern
parameter_list|()
block|{
return|return
name|datePattern
return|;
block|}
specifier|public
name|void
name|setDatePattern
parameter_list|(
name|Long
name|datePattern
parameter_list|)
block|{
name|this
operator|.
name|datePattern
operator|=
name|datePattern
expr_stmt|;
block|}
comment|/**      * @return Returns the expectedCapacity.      */
specifier|public
name|Integer
name|getExpectedCapacity
parameter_list|()
block|{
return|return
name|expectedCapacity
return|;
block|}
comment|/**      * @param expectedCapacity The expectedCapacity to set.      */
specifier|public
name|void
name|setExpectedCapacity
parameter_list|(
name|Integer
name|expectedCapacity
parameter_list|)
block|{
name|this
operator|.
name|expectedCapacity
operator|=
name|expectedCapacity
expr_stmt|;
block|}
comment|/**      * @return Returns the instructors.      */
specifier|public
name|List
name|getInstructors
parameter_list|()
block|{
return|return
name|instructors
return|;
block|}
comment|/**      * @return Returns the instructors.      */
specifier|public
name|String
name|getInstructors
parameter_list|(
name|int
name|key
parameter_list|)
block|{
return|return
name|instructors
operator|.
name|get
argument_list|(
name|key
argument_list|)
operator|.
name|toString
argument_list|()
return|;
block|}
comment|/**      * @param key The key to set.      * @param value The value to set.      */
specifier|public
name|void
name|setInstructors
parameter_list|(
name|int
name|key
parameter_list|,
name|Object
name|value
parameter_list|)
block|{
name|this
operator|.
name|instructors
operator|.
name|set
argument_list|(
name|key
argument_list|,
name|value
argument_list|)
expr_stmt|;
block|}
comment|/**      * @param instructors The instructors to set.      */
specifier|public
name|void
name|setInstructors
parameter_list|(
name|List
name|instructors
parameter_list|)
block|{
name|this
operator|.
name|instructors
operator|=
name|instructors
expr_stmt|;
block|}
comment|/**      * @return Returns the instrLead.      */
specifier|public
name|List
name|getInstrLead
parameter_list|()
block|{
return|return
name|instrLead
return|;
block|}
comment|/**      * @param instrLead The instrLead to set.      */
specifier|public
name|void
name|setInstrLead
parameter_list|(
name|List
name|instrLead
parameter_list|)
block|{
name|this
operator|.
name|instrLead
operator|=
name|instrLead
expr_stmt|;
block|}
specifier|public
name|void
name|addInstrLead
parameter_list|(
name|String
name|instructorId
parameter_list|)
block|{
name|instrLead
operator|.
name|add
argument_list|(
name|instructorId
argument_list|)
expr_stmt|;
block|}
specifier|public
name|String
name|getInstrLead
parameter_list|(
name|int
name|key
parameter_list|)
block|{
return|return
name|instrLead
operator|.
name|get
argument_list|(
name|key
argument_list|)
operator|.
name|toString
argument_list|()
return|;
block|}
specifier|public
name|void
name|setInstrLead
parameter_list|(
name|int
name|key
parameter_list|,
name|Object
name|value
parameter_list|)
block|{
name|this
operator|.
name|instrLead
operator|.
name|set
argument_list|(
name|key
argument_list|,
name|value
argument_list|)
expr_stmt|;
block|}
specifier|public
name|boolean
name|getInstrHasPref
parameter_list|(
name|int
name|key
parameter_list|)
block|{
if|if
condition|(
operator|!
literal|"true"
operator|.
name|equals
argument_list|(
name|getInstrLead
argument_list|(
name|key
argument_list|)
argument_list|)
operator|&&
operator|!
literal|"on"
operator|.
name|equals
argument_list|(
name|getInstrLead
argument_list|(
name|key
argument_list|)
argument_list|)
condition|)
return|return
literal|false
return|;
name|String
name|instructorId
init|=
name|getInstructors
argument_list|(
name|key
argument_list|)
decl_stmt|;
if|if
condition|(
name|instructorId
operator|==
literal|null
operator|||
name|instructorId
operator|.
name|trim
argument_list|()
operator|.
name|length
argument_list|()
operator|==
literal|0
operator|||
name|instructorId
operator|.
name|equals
argument_list|(
literal|"-"
argument_list|)
condition|)
return|return
literal|false
return|;
name|DepartmentalInstructor
name|di
init|=
operator|new
name|DepartmentalInstructorDAO
argument_list|()
operator|.
name|get
argument_list|(
name|Long
operator|.
name|valueOf
argument_list|(
name|instructorId
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|di
operator|!=
literal|null
operator|&&
name|di
operator|.
name|hasPreferences
argument_list|()
condition|)
return|return
literal|true
return|;
return|return
literal|false
return|;
block|}
comment|/**      * @return Returns the instrPctShare.      */
specifier|public
name|List
name|getInstrPctShare
parameter_list|()
block|{
return|return
name|instrPctShare
return|;
block|}
comment|/**      * @return Returns the instrPctShare.      */
specifier|public
name|String
name|getInstrPctShare
parameter_list|(
name|int
name|key
parameter_list|)
block|{
return|return
name|instrPctShare
operator|.
name|get
argument_list|(
name|key
argument_list|)
operator|.
name|toString
argument_list|()
return|;
block|}
comment|/**      * @param key The key to set.      * @param value The value to set.      */
specifier|public
name|void
name|setInstrPctShare
parameter_list|(
name|int
name|key
parameter_list|,
name|Object
name|value
parameter_list|)
block|{
name|this
operator|.
name|instrPctShare
operator|.
name|set
argument_list|(
name|key
argument_list|,
name|value
argument_list|)
expr_stmt|;
block|}
comment|/**      * @param instrPctShare The instrPctShare to set.      */
specifier|public
name|void
name|setInstrPctShare
parameter_list|(
name|List
name|instrPctShare
parameter_list|)
block|{
name|this
operator|.
name|instrPctShare
operator|=
name|instrPctShare
expr_stmt|;
block|}
comment|/**      * @return Returns the nbrRooms.      */
specifier|public
name|Integer
name|getNbrRooms
parameter_list|()
block|{
return|return
name|nbrRooms
return|;
block|}
comment|/**      * @param nbrRooms The nbrRooms to set.      */
specifier|public
name|void
name|setNbrRooms
parameter_list|(
name|Integer
name|nbrRooms
parameter_list|)
block|{
name|this
operator|.
name|nbrRooms
operator|=
name|nbrRooms
expr_stmt|;
block|}
comment|/**      * @return Returns the managingDept.      */
specifier|public
name|Long
name|getManagingDept
parameter_list|()
block|{
return|return
name|managingDept
return|;
block|}
comment|/**      * @param managingDept The managingDept to set.      */
specifier|public
name|void
name|setManagingDept
parameter_list|(
name|Long
name|owner
parameter_list|)
block|{
name|this
operator|.
name|managingDept
operator|=
name|owner
expr_stmt|;
block|}
comment|/**      * @return Returns the parent.      */
specifier|public
name|String
name|getParentClassName
parameter_list|()
block|{
return|return
name|parentClassName
return|;
block|}
comment|/**      * @param parent The parent to set.      */
specifier|public
name|void
name|setParentClassName
parameter_list|(
name|String
name|parentClassName
parameter_list|)
block|{
name|this
operator|.
name|parentClassName
operator|=
name|parentClassName
expr_stmt|;
block|}
comment|/**      * @return Returns the subpart.      */
specifier|public
name|Long
name|getSubpart
parameter_list|()
block|{
return|return
name|subpart
return|;
block|}
comment|/**      * @param subpart The subpart to set.      */
specifier|public
name|void
name|setSubpart
parameter_list|(
name|Long
name|subpart
parameter_list|)
block|{
name|this
operator|.
name|subpart
operator|=
name|subpart
expr_stmt|;
block|}
comment|/**      * @return Returns the itypeDesc.      */
specifier|public
name|String
name|getItypeDesc
parameter_list|()
block|{
return|return
name|itypeDesc
return|;
block|}
comment|/**      * @param itypeDesc The itypeDesc to set.      */
specifier|public
name|void
name|setItypeDesc
parameter_list|(
name|String
name|itypeDesc
parameter_list|)
block|{
name|this
operator|.
name|itypeDesc
operator|=
name|itypeDesc
expr_stmt|;
block|}
comment|/**      * @return Returns the notes.      */
specifier|public
name|String
name|getNotes
parameter_list|()
block|{
return|return
name|notes
return|;
block|}
comment|/**      * @param notes The notes to set.      */
specifier|public
name|void
name|setNotes
parameter_list|(
name|String
name|notes
parameter_list|)
block|{
name|this
operator|.
name|notes
operator|=
name|notes
expr_stmt|;
block|}
comment|/**      * @return Returns the managingDeptLabel.      */
specifier|public
name|String
name|getManagingDeptLabel
parameter_list|()
block|{
return|return
name|managingDeptLabel
return|;
block|}
comment|/**      * @param managingDeptLabel The managingDeptLabel to set.      */
specifier|public
name|void
name|setManagingDeptLabel
parameter_list|(
name|String
name|ownerLabel
parameter_list|)
block|{
name|this
operator|.
name|managingDeptLabel
operator|=
name|ownerLabel
expr_stmt|;
block|}
comment|/**      * @return Returns the parentClassId.      */
specifier|public
name|Long
name|getParentClassId
parameter_list|()
block|{
return|return
name|parentClassId
return|;
block|}
specifier|public
name|String
name|getSubjectAreaId
parameter_list|()
block|{
return|return
name|subjectAreaId
return|;
block|}
specifier|public
name|void
name|setSubjectAreaId
parameter_list|(
name|String
name|subjectAreaId
parameter_list|)
block|{
name|this
operator|.
name|subjectAreaId
operator|=
name|subjectAreaId
expr_stmt|;
block|}
specifier|public
name|String
name|getInstrOfferingId
parameter_list|()
block|{
return|return
name|instrOfferingId
return|;
block|}
specifier|public
name|void
name|setInstrOfferingId
parameter_list|(
name|String
name|instrOfferingId
parameter_list|)
block|{
name|this
operator|.
name|instrOfferingId
operator|=
name|instrOfferingId
expr_stmt|;
block|}
comment|/**      * @param parentClassId The parentClassId to set.      */
specifier|public
name|void
name|setParentClassId
parameter_list|(
name|Long
name|parentClassId
parameter_list|)
block|{
name|this
operator|.
name|parentClassId
operator|=
name|parentClassId
expr_stmt|;
block|}
specifier|public
name|String
name|getClassSuffix
parameter_list|()
block|{
return|return
name|classSuffix
return|;
block|}
specifier|public
name|void
name|setClassSuffix
parameter_list|(
name|String
name|classSuffix
parameter_list|)
block|{
name|this
operator|.
name|classSuffix
operator|=
name|classSuffix
expr_stmt|;
block|}
specifier|public
name|Boolean
name|getDisplayInScheduleBook
parameter_list|()
block|{
return|return
name|displayInScheduleBook
return|;
block|}
specifier|public
name|void
name|setDisplayInScheduleBook
parameter_list|(
name|Boolean
name|displayInScheduleBook
parameter_list|)
block|{
name|this
operator|.
name|displayInScheduleBook
operator|=
name|displayInScheduleBook
expr_stmt|;
block|}
specifier|public
name|Boolean
name|getDisplayInstructor
parameter_list|()
block|{
return|return
name|displayInstructor
return|;
block|}
specifier|public
name|void
name|setDisplayInstructor
parameter_list|(
name|Boolean
name|displayInstructor
parameter_list|)
block|{
name|this
operator|.
name|displayInstructor
operator|=
name|displayInstructor
expr_stmt|;
block|}
specifier|public
name|Integer
name|getMaxExpectedCapacity
parameter_list|()
block|{
return|return
name|maxExpectedCapacity
return|;
block|}
specifier|public
name|void
name|setMaxExpectedCapacity
parameter_list|(
name|Integer
name|maxExpectedCapacity
parameter_list|)
block|{
name|this
operator|.
name|maxExpectedCapacity
operator|=
name|maxExpectedCapacity
expr_stmt|;
block|}
specifier|public
name|Float
name|getRoomRatio
parameter_list|()
block|{
return|return
name|roomRatio
return|;
block|}
specifier|public
name|void
name|setRoomRatio
parameter_list|(
name|Float
name|roomRatio
parameter_list|)
block|{
name|this
operator|.
name|roomRatio
operator|=
name|roomRatio
expr_stmt|;
block|}
specifier|public
name|String
name|getSchedulePrintNote
parameter_list|()
block|{
return|return
name|schedulePrintNote
return|;
block|}
specifier|public
name|void
name|setSchedulePrintNote
parameter_list|(
name|String
name|schedulePrintNote
parameter_list|)
block|{
name|this
operator|.
name|schedulePrintNote
operator|=
name|schedulePrintNote
expr_stmt|;
block|}
specifier|public
name|Integer
name|getMinRoomLimit
parameter_list|()
block|{
return|return
name|minRoomLimit
return|;
block|}
specifier|public
name|void
name|setMinRoomLimit
parameter_list|(
name|Integer
name|minRoomLimit
parameter_list|)
block|{
name|this
operator|.
name|minRoomLimit
operator|=
name|minRoomLimit
expr_stmt|;
block|}
specifier|public
name|Boolean
name|getUnlimitedEnroll
parameter_list|()
block|{
return|return
name|unlimitedEnroll
return|;
block|}
specifier|public
name|void
name|setUnlimitedEnroll
parameter_list|(
name|Boolean
name|unlimitedEnroll
parameter_list|)
block|{
name|this
operator|.
name|unlimitedEnroll
operator|=
name|unlimitedEnroll
expr_stmt|;
block|}
comment|//TODO Reservations Bypass - to be removed later
specifier|public
name|Boolean
name|getIsCrosslisted
parameter_list|()
block|{
return|return
name|isCrosslisted
return|;
block|}
specifier|public
name|void
name|setIsCrosslisted
parameter_list|(
name|Boolean
name|isCrosslisted
parameter_list|)
block|{
name|this
operator|.
name|isCrosslisted
operator|=
name|isCrosslisted
expr_stmt|;
block|}
comment|// End Bypass
comment|/**      * @param date      * @return String representation of the date formatted as mm/dd/yyyy      */
specifier|public
name|String
name|dateToStr
parameter_list|(
name|Date
name|date
parameter_list|)
block|{
if|if
condition|(
name|date
operator|==
literal|null
condition|)
return|return
literal|""
return|;
else|else
return|return
name|dateFormat
operator|.
name|format
argument_list|(
name|date
argument_list|)
return|;
block|}
comment|/**      * @param date String representation of the date ( mm/dd/yyyy )      * @return java.sql.Date object for the given string      * @throws ParseException      */
specifier|public
name|java
operator|.
name|sql
operator|.
name|Date
name|strToDate
parameter_list|(
name|String
name|date
parameter_list|)
throws|throws
name|ParseException
block|{
name|java
operator|.
name|sql
operator|.
name|Date
name|dt
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|date
operator|==
literal|null
operator|||
name|date
operator|.
name|trim
argument_list|()
operator|.
name|length
argument_list|()
operator|==
literal|0
condition|)
return|return
literal|null
return|;
else|else
name|dt
operator|=
operator|new
name|java
operator|.
name|sql
operator|.
name|Date
argument_list|(
name|dateFormat
operator|.
name|parse
argument_list|(
name|date
argument_list|)
operator|.
name|getTime
argument_list|()
argument_list|)
expr_stmt|;
return|return
name|dt
return|;
block|}
comment|/**      * Add Instructor Data to List      * If class instructor is null, a blank row is added      * @param classInstr      */
specifier|public
name|void
name|addToInstructors
parameter_list|(
name|ClassInstructor
name|classInstr
parameter_list|)
block|{
comment|// Default values
name|String
name|id
init|=
literal|""
decl_stmt|;
name|String
name|pctShare
init|=
literal|"0"
decl_stmt|;
name|boolean
name|isLead
init|=
literal|false
decl_stmt|;
comment|// Class Instructor Specified
if|if
condition|(
name|classInstr
operator|!=
literal|null
condition|)
block|{
name|id
operator|=
name|classInstr
operator|.
name|getInstructor
argument_list|()
operator|.
name|getUniqueId
argument_list|()
operator|.
name|toString
argument_list|()
expr_stmt|;
name|pctShare
operator|=
name|classInstr
operator|.
name|getPercentShare
argument_list|()
operator|.
name|toString
argument_list|()
expr_stmt|;
name|isLead
operator|=
name|classInstr
operator|.
name|isLead
argument_list|()
operator|.
name|booleanValue
argument_list|()
expr_stmt|;
block|}
else|else
block|{
comment|// If this is the only record - set 100% share and make lead
if|if
condition|(
name|this
operator|.
name|instructors
operator|.
name|size
argument_list|()
operator|==
literal|0
condition|)
block|{
name|pctShare
operator|=
literal|"100"
expr_stmt|;
name|isLead
operator|=
literal|true
expr_stmt|;
block|}
block|}
comment|// Add row
name|this
operator|.
name|instructors
operator|.
name|add
argument_list|(
name|id
argument_list|)
expr_stmt|;
name|this
operator|.
name|instrPctShare
operator|.
name|add
argument_list|(
name|pctShare
argument_list|)
expr_stmt|;
name|this
operator|.
name|instrLead
operator|.
name|add
argument_list|(
name|isLead
condition|?
literal|"true"
else|:
literal|"false"
argument_list|)
expr_stmt|;
block|}
comment|/**      * Remove Instructor from List      * @param deleteId      */
specifier|public
name|void
name|removeInstructor
parameter_list|(
name|int
name|deleteId
parameter_list|)
block|{
comment|// Remove from lists
name|this
operator|.
name|instructors
operator|.
name|remove
argument_list|(
name|deleteId
argument_list|)
expr_stmt|;
name|this
operator|.
name|instrPctShare
operator|.
name|remove
argument_list|(
name|deleteId
argument_list|)
expr_stmt|;
if|if
condition|(
name|this
operator|.
name|instrLead
operator|.
name|size
argument_list|()
operator|>
name|deleteId
condition|)
name|this
operator|.
name|instrLead
operator|.
name|remove
argument_list|(
name|deleteId
argument_list|)
expr_stmt|;
block|}
comment|/**      * Clears all preference lists      */
specifier|public
name|void
name|clearPrefs
parameter_list|()
block|{
name|this
operator|.
name|instructors
operator|.
name|clear
argument_list|()
expr_stmt|;
name|this
operator|.
name|instrPctShare
operator|.
name|clear
argument_list|()
expr_stmt|;
name|this
operator|.
name|instrLead
operator|.
name|clear
argument_list|()
expr_stmt|;
block|}
specifier|public
name|String
name|getCourseName
parameter_list|()
block|{
return|return
name|courseName
return|;
block|}
specifier|public
name|void
name|setCourseName
parameter_list|(
name|String
name|courseName
parameter_list|)
block|{
name|this
operator|.
name|courseName
operator|=
name|courseName
expr_stmt|;
block|}
specifier|public
name|String
name|getCourseTitle
parameter_list|()
block|{
return|return
name|courseTitle
return|;
block|}
specifier|public
name|void
name|setCourseTitle
parameter_list|(
name|String
name|courseTitle
parameter_list|)
block|{
name|this
operator|.
name|courseTitle
operator|=
name|courseTitle
expr_stmt|;
block|}
specifier|public
name|Integer
name|getEnrollment
parameter_list|()
block|{
return|return
name|enrollment
return|;
block|}
specifier|public
name|void
name|setEnrollment
parameter_list|(
name|Integer
name|enrollment
parameter_list|)
block|{
name|this
operator|.
name|enrollment
operator|=
name|enrollment
expr_stmt|;
block|}
block|}
end_class

end_unit

