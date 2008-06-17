begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.1 (University Timetabling Application)  * Copyright (C) 2008, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 2 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program; if not, write to the Free Software Foundation, Inc.,  * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
package|package
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|action
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|HashSet
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Iterator
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Set
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
name|javax
operator|.
name|servlet
operator|.
name|http
operator|.
name|HttpServletResponse
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
name|HttpSession
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
name|ActionForm
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
name|ActionForward
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
name|ActionMessages
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
name|util
operator|.
name|MessageResources
import|;
end_import

begin_import
import|import
name|org
operator|.
name|unitime
operator|.
name|commons
operator|.
name|Debug
import|;
end_import

begin_import
import|import
name|org
operator|.
name|unitime
operator|.
name|commons
operator|.
name|User
import|;
end_import

begin_import
import|import
name|org
operator|.
name|unitime
operator|.
name|commons
operator|.
name|web
operator|.
name|Web
import|;
end_import

begin_import
import|import
name|org
operator|.
name|unitime
operator|.
name|commons
operator|.
name|web
operator|.
name|WebTable
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
name|form
operator|.
name|InstructorEditForm
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
name|ChangeLog
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
name|Class_
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
name|TimePattern
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
name|util
operator|.
name|Constants
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
name|LookupTables
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
name|webutil
operator|.
name|BackTracker
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
name|webutil
operator|.
name|RequiredTimeTable
import|;
end_import

begin_comment
comment|/**   * MyEclipse Struts  * Creation date: 07-24-2006  *   * XDoclet definition:  * @struts.action path="/instructorPrefEdit" name="instructorEditForm" input="/user/instructorPrefsEdit.jsp" scope="request"  * @struts.action-forward name="showEdit" path="instructorPrefsEditTile"  */
end_comment

begin_class
specifier|public
class|class
name|InstructorPrefEditAction
extends|extends
name|PreferencesAction
block|{
comment|// --------------------------------------------------------- Instance Variables
comment|// --------------------------------------------------------- Methods
comment|/**  	 * Method execute 	 * @param mapping 	 * @param form 	 * @param request 	 * @param response 	 * @return ActionForward 	 */
specifier|public
name|ActionForward
name|execute
parameter_list|(
name|ActionMapping
name|mapping
parameter_list|,
name|ActionForm
name|form
parameter_list|,
name|HttpServletRequest
name|request
parameter_list|,
name|HttpServletResponse
name|response
parameter_list|)
throws|throws
name|Exception
block|{
try|try
block|{
comment|// Set common lookup tables
name|super
operator|.
name|execute
argument_list|(
name|mapping
argument_list|,
name|form
argument_list|,
name|request
argument_list|,
name|response
argument_list|)
expr_stmt|;
name|HttpSession
name|httpSession
init|=
name|request
operator|.
name|getSession
argument_list|()
decl_stmt|;
name|InstructorEditForm
name|frm
init|=
operator|(
name|InstructorEditForm
operator|)
name|form
decl_stmt|;
name|MessageResources
name|rsc
init|=
name|getResources
argument_list|(
name|request
argument_list|)
decl_stmt|;
name|ActionMessages
name|errors
init|=
operator|new
name|ActionMessages
argument_list|()
decl_stmt|;
comment|// Read parameters
name|String
name|instructorId
init|=
name|request
operator|.
name|getParameter
argument_list|(
literal|"instructorId"
argument_list|)
decl_stmt|;
name|String
name|op
init|=
name|frm
operator|.
name|getOp
argument_list|()
decl_stmt|;
name|String
name|reloadCause
init|=
name|request
operator|.
name|getParameter
argument_list|(
literal|"reloadCause"
argument_list|)
decl_stmt|;
name|String
name|deleteType
init|=
name|request
operator|.
name|getParameter
argument_list|(
literal|"deleteType"
argument_list|)
decl_stmt|;
name|boolean
name|timeVertical
init|=
name|RequiredTimeTable
operator|.
name|getTimeGridVertical
argument_list|(
name|Web
operator|.
name|getUser
argument_list|(
name|httpSession
argument_list|)
argument_list|)
decl_stmt|;
comment|// Read subpart id from form
if|if
condition|(
name|op
operator|.
name|equals
argument_list|(
name|rsc
operator|.
name|getMessage
argument_list|(
literal|"button.reload"
argument_list|)
argument_list|)
operator|||
name|op
operator|.
name|equals
argument_list|(
name|rsc
operator|.
name|getMessage
argument_list|(
literal|"button.addTimePattern"
argument_list|)
argument_list|)
operator|||
name|op
operator|.
name|equals
argument_list|(
name|rsc
operator|.
name|getMessage
argument_list|(
literal|"button.addRoomPref"
argument_list|)
argument_list|)
operator|||
name|op
operator|.
name|equals
argument_list|(
name|rsc
operator|.
name|getMessage
argument_list|(
literal|"button.addBldgPref"
argument_list|)
argument_list|)
operator|||
name|op
operator|.
name|equals
argument_list|(
name|rsc
operator|.
name|getMessage
argument_list|(
literal|"button.addRoomFeaturePref"
argument_list|)
argument_list|)
operator|||
name|op
operator|.
name|equals
argument_list|(
name|rsc
operator|.
name|getMessage
argument_list|(
literal|"button.addDistPref"
argument_list|)
argument_list|)
operator|||
name|op
operator|.
name|equals
argument_list|(
name|rsc
operator|.
name|getMessage
argument_list|(
literal|"button.addRoomGroupPref"
argument_list|)
argument_list|)
operator|||
name|op
operator|.
name|equals
argument_list|(
name|rsc
operator|.
name|getMessage
argument_list|(
literal|"button.updatePrefs"
argument_list|)
argument_list|)
operator|||
name|op
operator|.
name|equals
argument_list|(
name|rsc
operator|.
name|getMessage
argument_list|(
literal|"button.cancel"
argument_list|)
argument_list|)
operator|||
name|op
operator|.
name|equals
argument_list|(
name|rsc
operator|.
name|getMessage
argument_list|(
literal|"button.clearInstrPrefs"
argument_list|)
argument_list|)
operator|||
name|op
operator|.
name|equals
argument_list|(
name|rsc
operator|.
name|getMessage
argument_list|(
literal|"button.delete"
argument_list|)
argument_list|)
operator|||
name|op
operator|.
name|equals
argument_list|(
name|rsc
operator|.
name|getMessage
argument_list|(
literal|"button.returnToDetail"
argument_list|)
argument_list|)
operator|||
name|op
operator|.
name|equals
argument_list|(
name|rsc
operator|.
name|getMessage
argument_list|(
literal|"button.nextInstructor"
argument_list|)
argument_list|)
operator|||
name|op
operator|.
name|equals
argument_list|(
name|rsc
operator|.
name|getMessage
argument_list|(
literal|"button.previousInstructor"
argument_list|)
argument_list|)
condition|)
block|{
name|instructorId
operator|=
name|frm
operator|.
name|getInstructorId
argument_list|()
expr_stmt|;
block|}
comment|// Determine if initial load
if|if
condition|(
name|op
operator|==
literal|null
operator|||
name|op
operator|.
name|trim
argument_list|()
operator|.
name|length
argument_list|()
operator|==
literal|0
operator|||
operator|(
name|op
operator|.
name|equals
argument_list|(
name|rsc
operator|.
name|getMessage
argument_list|(
literal|"button.reload"
argument_list|)
argument_list|)
operator|&&
operator|(
name|reloadCause
operator|==
literal|null
operator|||
name|reloadCause
operator|.
name|trim
argument_list|()
operator|.
name|length
argument_list|()
operator|==
literal|0
operator|)
operator|)
condition|)
block|{
name|op
operator|=
literal|"init"
expr_stmt|;
block|}
comment|// Check op exists
if|if
condition|(
name|op
operator|==
literal|null
operator|||
name|op
operator|.
name|trim
argument_list|()
operator|==
literal|""
condition|)
throw|throw
operator|new
name|Exception
argument_list|(
literal|"Null Operation not supported."
argument_list|)
throw|;
comment|//Check instructor exists
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
operator|==
literal|""
condition|)
throw|throw
operator|new
name|Exception
argument_list|(
literal|"Instructor Info not supplied."
argument_list|)
throw|;
comment|// Set screen name
name|frm
operator|.
name|setScreenName
argument_list|(
literal|"instructorPref"
argument_list|)
expr_stmt|;
comment|// If subpart id is not null - load subpart info
name|DepartmentalInstructorDAO
name|idao
init|=
operator|new
name|DepartmentalInstructorDAO
argument_list|()
decl_stmt|;
name|DepartmentalInstructor
name|inst
init|=
name|idao
operator|.
name|get
argument_list|(
operator|new
name|Long
argument_list|(
name|instructorId
argument_list|)
argument_list|)
decl_stmt|;
comment|// Cancel - Go back to Instructors Detail Screen
if|if
condition|(
name|op
operator|.
name|equals
argument_list|(
name|rsc
operator|.
name|getMessage
argument_list|(
literal|"button.returnToDetail"
argument_list|)
argument_list|)
operator|&&
name|instructorId
operator|!=
literal|null
operator|&&
name|instructorId
operator|.
name|trim
argument_list|()
operator|!=
literal|""
condition|)
block|{
name|request
operator|.
name|setAttribute
argument_list|(
literal|"instructorId"
argument_list|,
name|instructorId
argument_list|)
expr_stmt|;
name|request
operator|.
name|setAttribute
argument_list|(
literal|"fromChildScreen"
argument_list|,
literal|"true"
argument_list|)
expr_stmt|;
return|return
name|mapping
operator|.
name|findForward
argument_list|(
literal|"showDetail"
argument_list|)
return|;
block|}
comment|// Clear all preferences
if|if
condition|(
name|op
operator|.
name|equals
argument_list|(
name|rsc
operator|.
name|getMessage
argument_list|(
literal|"button.clearInstrPrefs"
argument_list|)
argument_list|)
condition|)
block|{
name|Set
name|s
init|=
name|inst
operator|.
name|getPreferences
argument_list|()
decl_stmt|;
name|s
operator|.
name|clear
argument_list|()
expr_stmt|;
name|inst
operator|.
name|setPreferences
argument_list|(
name|s
argument_list|)
expr_stmt|;
name|idao
operator|.
name|update
argument_list|(
name|inst
argument_list|)
expr_stmt|;
name|op
operator|=
literal|"init"
expr_stmt|;
name|request
operator|.
name|setAttribute
argument_list|(
literal|"instructorId"
argument_list|,
name|instructorId
argument_list|)
expr_stmt|;
name|request
operator|.
name|setAttribute
argument_list|(
literal|"fromChildScreen"
argument_list|,
literal|"true"
argument_list|)
expr_stmt|;
name|ChangeLog
operator|.
name|addChange
argument_list|(
literal|null
argument_list|,
name|request
argument_list|,
name|inst
argument_list|,
name|ChangeLog
operator|.
name|Source
operator|.
name|INSTRUCTOR_PREF_EDIT
argument_list|,
name|ChangeLog
operator|.
name|Operation
operator|.
name|CLEAR_PREF
argument_list|,
literal|null
argument_list|,
name|inst
operator|.
name|getDepartment
argument_list|()
argument_list|)
expr_stmt|;
return|return
name|mapping
operator|.
name|findForward
argument_list|(
literal|"showDetail"
argument_list|)
return|;
block|}
comment|// Reset form for initial load
if|if
condition|(
name|op
operator|.
name|equals
argument_list|(
literal|"init"
argument_list|)
condition|)
block|{
name|frm
operator|.
name|reset
argument_list|(
name|mapping
argument_list|,
name|request
argument_list|)
expr_stmt|;
block|}
comment|// Load form attributes that are constant
name|doLoad
argument_list|(
name|request
argument_list|,
name|frm
argument_list|,
name|inst
argument_list|,
name|instructorId
argument_list|)
expr_stmt|;
comment|// Update Preferences for InstructorDept
if|if
condition|(
name|op
operator|.
name|equals
argument_list|(
name|rsc
operator|.
name|getMessage
argument_list|(
literal|"button.update"
argument_list|)
argument_list|)
operator|||
name|op
operator|.
name|equals
argument_list|(
name|rsc
operator|.
name|getMessage
argument_list|(
literal|"button.nextInstructor"
argument_list|)
argument_list|)
operator|||
name|op
operator|.
name|equals
argument_list|(
name|rsc
operator|.
name|getMessage
argument_list|(
literal|"button.previousInstructor"
argument_list|)
argument_list|)
operator|||
name|op
operator|.
name|equals
argument_list|(
name|rsc
operator|.
name|getMessage
argument_list|(
literal|"button.updatePrefs"
argument_list|)
argument_list|)
condition|)
block|{
comment|// Validate input prefs
name|errors
operator|=
name|frm
operator|.
name|validate
argument_list|(
name|mapping
argument_list|,
name|request
argument_list|)
expr_stmt|;
comment|// No errors - Add to instructorDept and update
if|if
condition|(
name|errors
operator|.
name|size
argument_list|()
operator|==
literal|0
condition|)
block|{
name|Set
name|s
init|=
name|inst
operator|.
name|getPreferences
argument_list|()
decl_stmt|;
comment|// Clear all old prefs
name|s
operator|.
name|clear
argument_list|()
expr_stmt|;
name|super
operator|.
name|doUpdate
argument_list|(
name|request
argument_list|,
name|frm
argument_list|,
name|inst
argument_list|,
name|s
argument_list|,
name|timeVertical
argument_list|)
expr_stmt|;
name|ChangeLog
operator|.
name|addChange
argument_list|(
literal|null
argument_list|,
name|request
argument_list|,
name|inst
argument_list|,
name|ChangeLog
operator|.
name|Source
operator|.
name|INSTRUCTOR_PREF_EDIT
argument_list|,
name|ChangeLog
operator|.
name|Operation
operator|.
name|UPDATE
argument_list|,
literal|null
argument_list|,
name|inst
operator|.
name|getDepartment
argument_list|()
argument_list|)
expr_stmt|;
name|idao
operator|.
name|saveOrUpdate
argument_list|(
name|inst
argument_list|)
expr_stmt|;
name|request
operator|.
name|setAttribute
argument_list|(
literal|"instructorId"
argument_list|,
name|instructorId
argument_list|)
expr_stmt|;
name|request
operator|.
name|setAttribute
argument_list|(
literal|"fromChildScreen"
argument_list|,
literal|"true"
argument_list|)
expr_stmt|;
name|request
operator|.
name|setAttribute
argument_list|(
literal|"showPrefs"
argument_list|,
literal|"true"
argument_list|)
expr_stmt|;
if|if
condition|(
name|op
operator|.
name|equals
argument_list|(
name|rsc
operator|.
name|getMessage
argument_list|(
literal|"button.nextInstructor"
argument_list|)
argument_list|)
condition|)
name|response
operator|.
name|sendRedirect
argument_list|(
name|response
operator|.
name|encodeURL
argument_list|(
literal|"instructorPrefEdit.do?instructorId="
operator|+
name|frm
operator|.
name|getNextId
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|op
operator|.
name|equals
argument_list|(
name|rsc
operator|.
name|getMessage
argument_list|(
literal|"button.previousInstructor"
argument_list|)
argument_list|)
condition|)
name|response
operator|.
name|sendRedirect
argument_list|(
name|response
operator|.
name|encodeURL
argument_list|(
literal|"instructorPrefEdit.do?instructorId="
operator|+
name|frm
operator|.
name|getPreviousId
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|mapping
operator|.
name|findForward
argument_list|(
literal|"showDetail"
argument_list|)
return|;
block|}
else|else
block|{
name|saveErrors
argument_list|(
name|request
argument_list|,
name|errors
argument_list|)
expr_stmt|;
block|}
block|}
name|User
name|user
init|=
name|Web
operator|.
name|getUser
argument_list|(
name|httpSession
argument_list|)
decl_stmt|;
comment|// Initialize Preferences for initial load
name|Set
name|timePatterns
init|=
operator|new
name|HashSet
argument_list|()
decl_stmt|;
name|frm
operator|.
name|setAvailableTimePatterns
argument_list|(
literal|null
argument_list|)
expr_stmt|;
if|if
condition|(
name|op
operator|.
name|equals
argument_list|(
literal|"init"
argument_list|)
condition|)
block|{
name|initPrefs
argument_list|(
name|user
argument_list|,
name|frm
argument_list|,
name|inst
argument_list|,
literal|null
argument_list|,
literal|true
argument_list|)
expr_stmt|;
name|timePatterns
operator|.
name|add
argument_list|(
operator|new
name|TimePattern
argument_list|(
operator|new
name|Long
argument_list|(
operator|-
literal|1
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
comment|//timePatterns.addAll(TimePattern.findApplicable(request,30,false));
block|}
comment|//load class assignments
if|if
condition|(
operator|!
name|inst
operator|.
name|getClasses
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|WebTable
name|classTable
init|=
operator|new
name|WebTable
argument_list|(
literal|3
argument_list|,
literal|null
argument_list|,
operator|new
name|String
index|[]
block|{
literal|"class"
block|,
literal|"Type"
block|,
literal|"Limit"
block|}
argument_list|,
operator|new
name|String
index|[]
block|{
literal|"left"
block|,
literal|"left"
block|,
literal|"left"
block|}
argument_list|,
literal|null
argument_list|)
decl_stmt|;
comment|//Get class assignment information
for|for
control|(
name|Iterator
name|iterInst
init|=
name|inst
operator|.
name|getClasses
argument_list|()
operator|.
name|iterator
argument_list|()
init|;
name|iterInst
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|ClassInstructor
name|ci
init|=
operator|(
name|ClassInstructor
operator|)
name|iterInst
operator|.
name|next
argument_list|()
decl_stmt|;
name|Class_
name|c
init|=
name|ci
operator|.
name|getClassInstructing
argument_list|()
decl_stmt|;
name|classTable
operator|.
name|addLine
argument_list|(
literal|null
argument_list|,
operator|new
name|String
index|[]
block|{
name|c
operator|.
name|getClassLabel
argument_list|()
block|,
name|c
operator|.
name|getItypeDesc
argument_list|()
block|,
name|c
operator|.
name|getExpectedCapacity
argument_list|()
operator|.
name|toString
argument_list|()
block|,     					}
argument_list|,
literal|null
argument_list|,
literal|null
argument_list|)
expr_stmt|;
block|}
name|String
name|tblData
init|=
name|classTable
operator|.
name|printTable
argument_list|()
decl_stmt|;
name|request
operator|.
name|setAttribute
argument_list|(
literal|"classTable"
argument_list|,
name|tblData
argument_list|)
expr_stmt|;
block|}
comment|//// Set display distribution to Not Applicable
comment|/*     		request.setAttribute(DistributionPref.DIST_PREF_REQUEST_ATTR,      				"<FONT color=696969>Distribution Preferences Not Applicable</FONT>");     				*/
comment|// Process Preferences Action
name|processPrefAction
argument_list|(
name|request
argument_list|,
name|frm
argument_list|,
name|errors
argument_list|)
expr_stmt|;
comment|// Generate Time Pattern Grids
name|super
operator|.
name|generateTimePatternGrids
argument_list|(
name|request
argument_list|,
name|frm
argument_list|,
name|inst
argument_list|,
name|timePatterns
argument_list|,
name|op
argument_list|,
name|timeVertical
argument_list|,
literal|true
argument_list|,
literal|null
argument_list|)
expr_stmt|;
name|LookupTables
operator|.
name|setupRooms
argument_list|(
name|request
argument_list|,
name|inst
argument_list|)
expr_stmt|;
comment|// Room Prefs
name|LookupTables
operator|.
name|setupBldgs
argument_list|(
name|request
argument_list|,
name|inst
argument_list|)
expr_stmt|;
comment|// Building Prefs
name|LookupTables
operator|.
name|setupRoomFeatures
argument_list|(
name|request
argument_list|,
name|inst
argument_list|)
expr_stmt|;
comment|// Preference Levels
name|LookupTables
operator|.
name|setupRoomGroups
argument_list|(
name|request
argument_list|,
name|inst
argument_list|)
expr_stmt|;
comment|// Room Groups
name|BackTracker
operator|.
name|markForBack
argument_list|(
name|request
argument_list|,
literal|"instructorDetail.do?instructorId="
operator|+
name|frm
operator|.
name|getInstructorId
argument_list|()
argument_list|,
literal|"Instructor ("
operator|+
operator|(
name|frm
operator|.
name|getName
argument_list|()
operator|==
literal|null
condition|?
literal|"null"
else|:
name|frm
operator|.
name|getName
argument_list|()
operator|.
name|trim
argument_list|()
operator|)
operator|+
literal|")"
argument_list|,
literal|true
argument_list|,
literal|false
argument_list|)
expr_stmt|;
return|return
name|mapping
operator|.
name|findForward
argument_list|(
literal|"showEdit"
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|Debug
operator|.
name|error
argument_list|(
name|e
argument_list|)
expr_stmt|;
throw|throw
name|e
throw|;
block|}
block|}
comment|/** 	 * Loads the non-editable instructor info into the form 	 * @param request 	 * @param frm 	 * @param inst 	 * @param instructorId 	 */
specifier|private
name|void
name|doLoad
parameter_list|(
name|HttpServletRequest
name|request
parameter_list|,
name|InstructorEditForm
name|frm
parameter_list|,
name|DepartmentalInstructor
name|inst
parameter_list|,
name|String
name|instructorId
parameter_list|)
block|{
comment|// populate form
name|frm
operator|.
name|setInstructorId
argument_list|(
name|instructorId
argument_list|)
expr_stmt|;
name|frm
operator|.
name|setName
argument_list|(
name|Constants
operator|.
name|toInitialCase
argument_list|(
name|inst
operator|.
name|getFirstName
argument_list|()
argument_list|,
literal|"-"
operator|.
name|toCharArray
argument_list|()
argument_list|)
operator|+
literal|" "
operator|+
operator|(
operator|(
name|inst
operator|.
name|getMiddleName
argument_list|()
operator|==
literal|null
operator|)
condition|?
literal|""
else|:
name|Constants
operator|.
name|toInitialCase
argument_list|(
name|inst
operator|.
name|getMiddleName
argument_list|()
argument_list|,
literal|"-"
operator|.
name|toCharArray
argument_list|()
argument_list|)
operator|)
operator|+
literal|" "
operator|+
name|Constants
operator|.
name|toInitialCase
argument_list|(
name|inst
operator|.
name|getLastName
argument_list|()
argument_list|,
literal|"-"
operator|.
name|toCharArray
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|inst
operator|.
name|getExternalUniqueId
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|frm
operator|.
name|setPuId
argument_list|(
name|inst
operator|.
name|getExternalUniqueId
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|frm
operator|.
name|setDeptName
argument_list|(
name|inst
operator|.
name|getDepartment
argument_list|()
operator|.
name|getName
argument_list|()
operator|.
name|trim
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|inst
operator|.
name|getPositionType
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|frm
operator|.
name|setPosType
argument_list|(
name|inst
operator|.
name|getPositionType
argument_list|()
operator|.
name|getUniqueId
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|inst
operator|.
name|getCareerAcct
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|frm
operator|.
name|setCareerAcct
argument_list|(
name|inst
operator|.
name|getCareerAcct
argument_list|()
operator|.
name|trim
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|frm
operator|.
name|setEmail
argument_list|(
name|inst
operator|.
name|getEmail
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|inst
operator|.
name|getNote
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|frm
operator|.
name|setNote
argument_list|(
name|inst
operator|.
name|getNote
argument_list|()
operator|.
name|trim
argument_list|()
argument_list|)
expr_stmt|;
block|}
try|try
block|{
name|User
name|user
init|=
name|Web
operator|.
name|getUser
argument_list|(
name|request
operator|.
name|getSession
argument_list|()
argument_list|)
decl_stmt|;
name|DepartmentalInstructor
name|previous
init|=
name|inst
operator|.
name|getPreviousDepartmentalInstructor
argument_list|(
name|request
operator|.
name|getSession
argument_list|()
argument_list|,
name|user
argument_list|,
literal|false
argument_list|,
literal|true
argument_list|)
decl_stmt|;
name|frm
operator|.
name|setPreviousId
argument_list|(
name|previous
operator|==
literal|null
condition|?
literal|null
else|:
name|previous
operator|.
name|getUniqueId
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
name|DepartmentalInstructor
name|next
init|=
name|inst
operator|.
name|getNextDepartmentalInstructor
argument_list|(
name|request
operator|.
name|getSession
argument_list|()
argument_list|,
name|user
argument_list|,
literal|false
argument_list|,
literal|true
argument_list|)
decl_stmt|;
name|frm
operator|.
name|setNextId
argument_list|(
name|next
operator|==
literal|null
condition|?
literal|null
else|:
name|next
operator|.
name|getUniqueId
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|Debug
operator|.
name|error
argument_list|(
name|e
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

