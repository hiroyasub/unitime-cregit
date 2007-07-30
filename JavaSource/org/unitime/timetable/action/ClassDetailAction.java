begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.0 (University Course Timetabling& Student Sectioning Application)  * Copyright (C) 2007, UniTime.org, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 2 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program; if not, write to the Free Software Foundation, Inc.,  * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
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
name|ArrayList
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Collections
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Enumeration
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
name|List
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
name|java
operator|.
name|util
operator|.
name|Vector
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
name|net
operator|.
name|sf
operator|.
name|cpsolver
operator|.
name|coursett
operator|.
name|model
operator|.
name|RoomLocation
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
name|timetable
operator|.
name|form
operator|.
name|ClassEditForm
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
name|CourseOffering
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
name|DistributionPref
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
name|InstrOfferingConfig
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
name|PreferenceLevel
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
name|Reservation
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
name|SchedulingSubpart
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
name|comparators
operator|.
name|InstructorComparator
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
name|Class_DAO
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
name|solver
operator|.
name|TimetableDatabaseLoader
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
name|DistributionPrefsTableBuilder
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
name|ReservationsTableBuilder
import|;
end_import

begin_comment
comment|/**  * MyEclipse Struts  * Creation date: 03-29-2006  *  * XDoclet definition:  * @struts.action path="/classDetail" name="classEditForm" attribute="ClassEditForm" input="/user/classEdit.jsp" scope="request"  */
end_comment

begin_class
specifier|public
class|class
name|ClassDetailAction
extends|extends
name|PreferencesAction
block|{
comment|// --------------------------------------------------------- Class Constants
comment|/** Anchor names **/
specifier|public
specifier|final
name|String
name|HASH_INSTR_PREF
init|=
literal|"InstructorPref"
decl_stmt|;
comment|// --------------------------------------------------------- Methods
comment|/** 	 * Method execute 	 * @param mapping 	 * @param form 	 * @param request 	 * @param response 	 * @return ActionForward 	 */
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
name|ClassEditForm
name|frm
init|=
operator|(
name|ClassEditForm
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
name|classId
init|=
name|request
operator|.
name|getParameter
argument_list|(
literal|"cid"
argument_list|)
operator|==
literal|null
condition|?
name|request
operator|.
name|getAttribute
argument_list|(
literal|"cid"
argument_list|)
operator|!=
literal|null
condition|?
name|request
operator|.
name|getAttribute
argument_list|(
literal|"cid"
argument_list|)
operator|.
name|toString
argument_list|()
else|:
literal|null
else|:
name|request
operator|.
name|getParameter
argument_list|(
literal|"cid"
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
comment|// Read class id from form
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
literal|"button.editPrefsClass"
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
literal|"button.backToInstrOffrDet"
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
literal|"button.nextClass"
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
literal|"button.previousClass"
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
literal|"button.addReservation"
argument_list|)
argument_list|)
condition|)
block|{
name|classId
operator|=
name|frm
operator|.
name|getClassId
argument_list|()
operator|.
name|toString
argument_list|()
expr_stmt|;
block|}
else|else
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
name|Debug
operator|.
name|debug
argument_list|(
literal|"op: "
operator|+
name|op
argument_list|)
expr_stmt|;
name|Debug
operator|.
name|debug
argument_list|(
literal|"class: "
operator|+
name|classId
argument_list|)
expr_stmt|;
comment|// Check class exists
if|if
condition|(
name|classId
operator|==
literal|null
operator|||
name|classId
operator|.
name|trim
argument_list|()
operator|.
name|length
argument_list|()
operator|==
literal|0
condition|)
throw|throw
operator|new
name|Exception
argument_list|(
literal|"Class Info not supplied."
argument_list|)
throw|;
comment|// backToInstrOffr - Go back to Instructional Offering Screen
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
literal|"button.backToInstrOffrDet"
argument_list|)
argument_list|)
operator|&&
name|classId
operator|!=
literal|null
operator|&&
name|classId
operator|.
name|trim
argument_list|()
operator|.
name|length
argument_list|()
operator|!=
literal|0
condition|)
block|{
name|Class_DAO
name|cdao
init|=
operator|new
name|Class_DAO
argument_list|()
decl_stmt|;
name|SchedulingSubpart
name|ss
init|=
name|cdao
operator|.
name|get
argument_list|(
operator|new
name|Long
argument_list|(
name|classId
argument_list|)
argument_list|)
operator|.
name|getSchedulingSubpart
argument_list|()
decl_stmt|;
name|response
operator|.
name|sendRedirect
argument_list|(
name|response
operator|.
name|encodeURL
argument_list|(
literal|"instructionalOfferingDetail.do?op=view&io="
operator|+
name|ss
operator|.
name|getInstrOfferingConfig
argument_list|()
operator|.
name|getInstructionalOffering
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|// If class id is not null - load class info
name|Class_DAO
name|cdao
init|=
operator|new
name|Class_DAO
argument_list|()
decl_stmt|;
name|Class_
name|c
init|=
name|cdao
operator|.
name|get
argument_list|(
operator|new
name|Long
argument_list|(
name|classId
argument_list|)
argument_list|)
decl_stmt|;
comment|// Edit Preference - Redirect to prefs edit screen
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
literal|"button.editPrefsClass"
argument_list|)
argument_list|)
operator|&&
name|classId
operator|!=
literal|null
operator|&&
name|classId
operator|.
name|trim
argument_list|()
operator|!=
literal|""
condition|)
block|{
name|response
operator|.
name|sendRedirect
argument_list|(
name|response
operator|.
name|encodeURL
argument_list|(
literal|"classEdit.do?cid="
operator|+
name|c
operator|.
name|getUniqueId
argument_list|()
operator|.
name|toString
argument_list|()
operator|+
literal|"&sec="
operator|+
name|c
operator|.
name|getSectionNumberString
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
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
literal|"button.nextClass"
argument_list|)
argument_list|)
condition|)
block|{
name|response
operator|.
name|sendRedirect
argument_list|(
name|response
operator|.
name|encodeURL
argument_list|(
literal|"classDetail.do?cid="
operator|+
name|frm
operator|.
name|getNextId
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
block|}
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
literal|"button.previousClass"
argument_list|)
argument_list|)
condition|)
block|{
name|response
operator|.
name|sendRedirect
argument_list|(
name|response
operator|.
name|encodeURL
argument_list|(
literal|"classDetail.do?cid="
operator|+
name|frm
operator|.
name|getPreviousId
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
block|}
comment|// Add Distribution Preference - Redirect to dist prefs screen
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
literal|"button.addDistPref"
argument_list|)
argument_list|)
condition|)
block|{
name|SchedulingSubpart
name|ss
init|=
name|c
operator|.
name|getSchedulingSubpart
argument_list|()
decl_stmt|;
name|CourseOffering
name|cco
init|=
name|ss
operator|.
name|getInstrOfferingConfig
argument_list|()
operator|.
name|getControllingCourseOffering
argument_list|()
decl_stmt|;
name|request
operator|.
name|setAttribute
argument_list|(
literal|"subjectAreaId"
argument_list|,
name|cco
operator|.
name|getSubjectArea
argument_list|()
operator|.
name|getUniqueId
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
name|request
operator|.
name|setAttribute
argument_list|(
literal|"schedSubpartId"
argument_list|,
name|ss
operator|.
name|getUniqueId
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
name|request
operator|.
name|setAttribute
argument_list|(
literal|"courseOffrId"
argument_list|,
name|cco
operator|.
name|getUniqueId
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
name|request
operator|.
name|setAttribute
argument_list|(
literal|"classId"
argument_list|,
name|c
operator|.
name|getUniqueId
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
return|return
name|mapping
operator|.
name|findForward
argument_list|(
literal|"addDistributionPrefs"
argument_list|)
return|;
block|}
comment|// Load form attributes that are constant
name|doLoad
argument_list|(
name|request
argument_list|,
name|frm
argument_list|,
name|c
argument_list|,
name|op
argument_list|)
expr_stmt|;
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
name|frm
operator|.
name|setAvailableTimePatterns
argument_list|(
name|TimePattern
operator|.
name|findApplicable
argument_list|(
name|request
argument_list|,
name|c
operator|.
name|getSchedulingSubpart
argument_list|()
operator|.
name|getMinutesPerWk
argument_list|()
operator|.
name|intValue
argument_list|()
argument_list|,
literal|true
argument_list|,
name|c
operator|.
name|getManagingDept
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|Set
name|timePatterns
init|=
literal|null
decl_stmt|;
name|initPrefs
argument_list|(
name|user
argument_list|,
name|frm
argument_list|,
name|c
argument_list|,
literal|null
argument_list|,
literal|false
argument_list|)
expr_stmt|;
name|timePatterns
operator|=
name|c
operator|.
name|effectiveTimePatterns
argument_list|()
expr_stmt|;
comment|// Dist Prefs are not editable by Sched Dpty Asst
name|String
name|currentRole
init|=
name|user
operator|.
name|getCurrentRole
argument_list|()
decl_stmt|;
name|boolean
name|editable
init|=
literal|true
decl_stmt|;
comment|// Display distribution Prefs
name|DistributionPrefsTableBuilder
name|tbl
init|=
operator|new
name|DistributionPrefsTableBuilder
argument_list|()
decl_stmt|;
name|String
name|html
init|=
name|tbl
operator|.
name|getDistPrefsTableForClass
argument_list|(
name|request
argument_list|,
name|c
argument_list|,
literal|true
argument_list|)
decl_stmt|;
if|if
condition|(
name|html
operator|!=
literal|null
condition|)
name|request
operator|.
name|setAttribute
argument_list|(
name|DistributionPref
operator|.
name|DIST_PREF_REQUEST_ATTR
argument_list|,
name|html
argument_list|)
expr_stmt|;
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
name|c
argument_list|,
name|timePatterns
argument_list|,
literal|"init"
argument_list|,
name|timeVertical
argument_list|,
literal|false
argument_list|,
literal|null
argument_list|)
expr_stmt|;
comment|// Instructors
name|setupInstructors
argument_list|(
name|request
argument_list|,
name|frm
argument_list|,
name|c
argument_list|)
expr_stmt|;
name|LookupTables
operator|.
name|setupDatePatterns
argument_list|(
name|request
argument_list|,
literal|"Default"
argument_list|,
name|c
operator|.
name|getSchedulingSubpart
argument_list|()
operator|.
name|effectiveDatePattern
argument_list|()
argument_list|,
name|c
operator|.
name|getManagingDept
argument_list|()
argument_list|,
name|c
operator|.
name|effectiveDatePattern
argument_list|()
argument_list|)
expr_stmt|;
name|LookupTables
operator|.
name|setupRooms
argument_list|(
name|request
argument_list|,
name|c
argument_list|)
expr_stmt|;
comment|// Room Prefs
name|LookupTables
operator|.
name|setupBldgs
argument_list|(
name|request
argument_list|,
name|c
argument_list|)
expr_stmt|;
comment|// Building Prefs
name|LookupTables
operator|.
name|setupRoomFeatures
argument_list|(
name|request
argument_list|,
name|c
argument_list|)
expr_stmt|;
comment|// Preference Levels
name|LookupTables
operator|.
name|setupRoomGroups
argument_list|(
name|request
argument_list|,
name|c
argument_list|)
expr_stmt|;
comment|// Room Groups
name|BackTracker
operator|.
name|markForBack
argument_list|(
name|request
argument_list|,
literal|"classDetail.do?cid="
operator|+
name|frm
operator|.
name|getClassId
argument_list|()
argument_list|,
literal|"Class ("
operator|+
name|frm
operator|.
name|getClassName
argument_list|()
operator|+
literal|")"
argument_list|,
literal|true
argument_list|,
literal|false
argument_list|)
expr_stmt|;
comment|// Add Reservation
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
literal|"button.addReservation"
argument_list|)
argument_list|)
condition|)
block|{
comment|//TODO Reservations Bypass - to be removed later
name|request
operator|.
name|setAttribute
argument_list|(
literal|"ownerId"
argument_list|,
name|frm
operator|.
name|getClassId
argument_list|()
argument_list|)
expr_stmt|;
name|request
operator|.
name|setAttribute
argument_list|(
literal|"ownerName"
argument_list|,
name|frm
operator|.
name|getClassName
argument_list|()
argument_list|)
expr_stmt|;
name|request
operator|.
name|setAttribute
argument_list|(
literal|"ownerType"
argument_list|,
name|Constants
operator|.
name|RESV_OWNER_CLASS
argument_list|)
expr_stmt|;
name|request
operator|.
name|setAttribute
argument_list|(
literal|"ownerTypeLabel"
argument_list|,
name|Constants
operator|.
name|RESV_OWNER_CLASS_LBL
argument_list|)
expr_stmt|;
name|InstrOfferingConfig
name|ioc
init|=
name|c
operator|.
name|getSchedulingSubpart
argument_list|()
operator|.
name|getInstrOfferingConfig
argument_list|()
decl_stmt|;
name|request
operator|.
name|setAttribute
argument_list|(
literal|"ioLimit"
argument_list|,
name|ioc
operator|.
name|getInstructionalOffering
argument_list|()
operator|.
name|getLimit
argument_list|()
operator|!=
literal|null
condition|?
name|ioc
operator|.
name|getInstructionalOffering
argument_list|()
operator|.
name|getLimit
argument_list|()
operator|.
name|toString
argument_list|()
else|:
literal|null
argument_list|)
expr_stmt|;
name|request
operator|.
name|setAttribute
argument_list|(
literal|"unlimited"
argument_list|,
name|ioc
operator|.
name|isUnlimitedEnrollment
argument_list|()
argument_list|)
expr_stmt|;
return|return
name|mapping
operator|.
name|findForward
argument_list|(
literal|"displayCourseReservation"
argument_list|)
return|;
comment|// End Bypass
comment|//TODO Reservations - functionality to be made visible later
comment|/* 			    request.setAttribute("ownerId", frm.getClassId().toString()); 			    request.setAttribute("ownerClassId", Constants.RESV_OWNER_CLASS); 			    return mapping.findForward("addReservation"); 			    */
block|}
return|return
name|mapping
operator|.
name|findForward
argument_list|(
literal|"displayClass"
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
comment|/** 	     * Loads class info into the form 	     * @param request 	     * @param frm 	     * @param c 	     * @param classId 	     */
specifier|private
name|void
name|doLoad
parameter_list|(
name|HttpServletRequest
name|request
parameter_list|,
name|ClassEditForm
name|frm
parameter_list|,
name|Class_
name|c
parameter_list|,
name|String
name|op
parameter_list|)
block|{
name|HttpSession
name|httpSession
init|=
name|request
operator|.
name|getSession
argument_list|()
decl_stmt|;
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
name|String
name|parentClassName
init|=
literal|"-"
decl_stmt|;
name|Long
name|parentClassId
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|c
operator|.
name|getParentClass
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|parentClassName
operator|=
name|c
operator|.
name|getParentClass
argument_list|()
operator|.
name|toString
argument_list|()
expr_stmt|;
if|if
condition|(
name|c
operator|.
name|getParentClass
argument_list|()
operator|.
name|isViewableBy
argument_list|(
name|user
argument_list|)
condition|)
name|parentClassId
operator|=
name|c
operator|.
name|getParentClass
argument_list|()
operator|.
name|getUniqueId
argument_list|()
expr_stmt|;
block|}
name|CourseOffering
name|cco
init|=
name|c
operator|.
name|getSchedulingSubpart
argument_list|()
operator|.
name|getControllingCourseOffering
argument_list|()
decl_stmt|;
comment|// Set Session Variables
name|httpSession
operator|.
name|setAttribute
argument_list|(
name|Constants
operator|.
name|SUBJ_AREA_ID_ATTR_NAME
argument_list|,
name|cco
operator|.
name|getSubjectArea
argument_list|()
operator|.
name|getUniqueId
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|httpSession
operator|.
name|getAttribute
argument_list|(
name|Constants
operator|.
name|CRS_NBR_ATTR_NAME
argument_list|)
operator|!=
literal|null
operator|&&
name|httpSession
operator|.
name|getAttribute
argument_list|(
name|Constants
operator|.
name|CRS_NBR_ATTR_NAME
argument_list|)
operator|.
name|toString
argument_list|()
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
name|httpSession
operator|.
name|setAttribute
argument_list|(
name|Constants
operator|.
name|CRS_NBR_ATTR_NAME
argument_list|,
name|cco
operator|.
name|getCourseNbr
argument_list|()
argument_list|)
expr_stmt|;
comment|// populate form
name|frm
operator|.
name|setClassId
argument_list|(
name|c
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
name|frm
operator|.
name|setSection
argument_list|(
name|c
operator|.
name|getSectionNumberString
argument_list|()
argument_list|)
expr_stmt|;
name|frm
operator|.
name|setClassName
argument_list|(
name|c
operator|.
name|getClassLabel
argument_list|()
argument_list|)
expr_stmt|;
name|SchedulingSubpart
name|ss
init|=
name|c
operator|.
name|getSchedulingSubpart
argument_list|()
decl_stmt|;
name|String
name|itypeDesc
init|=
name|c
operator|.
name|getItypeDesc
argument_list|()
decl_stmt|;
if|if
condition|(
name|ss
operator|.
name|getInstrOfferingConfig
argument_list|()
operator|.
name|getInstructionalOffering
argument_list|()
operator|.
name|hasMultipleConfigurations
argument_list|()
condition|)
name|itypeDesc
operator|+=
literal|" ["
operator|+
name|ss
operator|.
name|getInstrOfferingConfig
argument_list|()
operator|.
name|getName
argument_list|()
operator|+
literal|"]"
expr_stmt|;
name|frm
operator|.
name|setItypeDesc
argument_list|(
name|itypeDesc
argument_list|)
expr_stmt|;
name|frm
operator|.
name|setParentClassName
argument_list|(
name|parentClassName
argument_list|)
expr_stmt|;
name|frm
operator|.
name|setParentClassId
argument_list|(
name|parentClassId
argument_list|)
expr_stmt|;
name|frm
operator|.
name|setSubjectAreaId
argument_list|(
name|cco
operator|.
name|getSubjectArea
argument_list|()
operator|.
name|getUniqueId
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
name|frm
operator|.
name|setInstrOfferingId
argument_list|(
name|cco
operator|.
name|getInstructionalOffering
argument_list|()
operator|.
name|getUniqueId
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|c
operator|.
name|getSchedulingSubpart
argument_list|()
operator|.
name|isViewableBy
argument_list|(
name|user
argument_list|)
condition|)
name|frm
operator|.
name|setSubpart
argument_list|(
name|c
operator|.
name|getSchedulingSubpart
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
else|else
name|frm
operator|.
name|setSubpart
argument_list|(
literal|null
argument_list|)
expr_stmt|;
name|frm
operator|.
name|setCourseName
argument_list|(
name|cco
operator|.
name|getInstructionalOffering
argument_list|()
operator|.
name|getCourseName
argument_list|()
argument_list|)
expr_stmt|;
comment|//TODO Reservations Bypass - to be removed later
name|frm
operator|.
name|setIsCrosslisted
argument_list|(
operator|new
name|Boolean
argument_list|(
name|cco
operator|.
name|getInstructionalOffering
argument_list|()
operator|.
name|getCourseOfferings
argument_list|()
operator|.
name|size
argument_list|()
operator|>
literal|1
argument_list|)
argument_list|)
expr_stmt|;
comment|// End Bypass
comment|// Load from class
name|frm
operator|.
name|setExpectedCapacity
argument_list|(
name|c
operator|.
name|getExpectedCapacity
argument_list|()
argument_list|)
expr_stmt|;
name|frm
operator|.
name|setDatePattern
argument_list|(
name|c
operator|.
name|getDatePattern
argument_list|()
operator|==
literal|null
condition|?
operator|new
name|Long
argument_list|(
operator|-
literal|1
argument_list|)
else|:
name|c
operator|.
name|getDatePattern
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
name|frm
operator|.
name|setNbrRooms
argument_list|(
name|c
operator|.
name|getNbrRooms
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|c
operator|.
name|getNotes
argument_list|()
operator|==
literal|null
condition|)
name|frm
operator|.
name|setNotes
argument_list|(
literal|""
argument_list|)
expr_stmt|;
else|else
name|frm
operator|.
name|setNotes
argument_list|(
name|c
operator|.
name|getNotes
argument_list|()
operator|.
name|replaceAll
argument_list|(
literal|"\n"
argument_list|,
literal|"<BR>"
argument_list|)
argument_list|)
expr_stmt|;
name|frm
operator|.
name|setManagingDept
argument_list|(
name|c
operator|.
name|getManagingDept
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
name|frm
operator|.
name|setManagingDeptLabel
argument_list|(
name|c
operator|.
name|getManagingDept
argument_list|()
operator|.
name|getManagingDeptLabel
argument_list|()
argument_list|)
expr_stmt|;
name|frm
operator|.
name|setSchedulePrintNote
argument_list|(
name|c
operator|.
name|getSchedulePrintNote
argument_list|()
argument_list|)
expr_stmt|;
name|frm
operator|.
name|setClassSuffix
argument_list|(
name|c
operator|.
name|getDivSecNumber
argument_list|()
argument_list|)
expr_stmt|;
name|frm
operator|.
name|setMaxExpectedCapacity
argument_list|(
name|c
operator|.
name|getMaxExpectedCapacity
argument_list|()
argument_list|)
expr_stmt|;
name|frm
operator|.
name|setRoomRatio
argument_list|(
name|c
operator|.
name|getRoomRatio
argument_list|()
argument_list|)
expr_stmt|;
name|frm
operator|.
name|setDisplayInScheduleBook
argument_list|(
name|c
operator|.
name|isDisplayInScheduleBook
argument_list|()
argument_list|)
expr_stmt|;
name|frm
operator|.
name|setDisplayInstructor
argument_list|(
name|c
operator|.
name|isDisplayInstructor
argument_list|()
argument_list|)
expr_stmt|;
name|frm
operator|.
name|setMinRoomLimit
argument_list|(
name|c
operator|.
name|getMinRoomLimit
argument_list|()
argument_list|)
expr_stmt|;
name|Class_
name|next
init|=
name|c
operator|.
name|getNextClass
argument_list|(
name|request
operator|.
name|getSession
argument_list|()
argument_list|,
name|Web
operator|.
name|getUser
argument_list|(
name|request
operator|.
name|getSession
argument_list|()
argument_list|)
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
name|Class_
name|previous
init|=
name|c
operator|.
name|getPreviousClass
argument_list|(
name|request
operator|.
name|getSession
argument_list|()
argument_list|,
name|Web
operator|.
name|getUser
argument_list|(
name|request
operator|.
name|getSession
argument_list|()
argument_list|)
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
name|List
name|instructors
init|=
operator|new
name|ArrayList
argument_list|(
name|c
operator|.
name|getClassInstructors
argument_list|()
argument_list|)
decl_stmt|;
name|InstructorComparator
name|ic
init|=
operator|new
name|InstructorComparator
argument_list|()
decl_stmt|;
name|ic
operator|.
name|setCompareBy
argument_list|(
name|ic
operator|.
name|COMPARE_BY_LEAD
argument_list|)
expr_stmt|;
name|Collections
operator|.
name|sort
argument_list|(
name|instructors
argument_list|,
name|ic
argument_list|)
expr_stmt|;
for|for
control|(
name|Iterator
name|iter
init|=
name|instructors
operator|.
name|iterator
argument_list|()
init|;
name|iter
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|ClassInstructor
name|classInstr
init|=
operator|(
name|ClassInstructor
operator|)
name|iter
operator|.
name|next
argument_list|()
decl_stmt|;
name|frm
operator|.
name|addToInstructors
argument_list|(
name|classInstr
argument_list|)
expr_stmt|;
block|}
name|ReservationsTableBuilder
name|resvTbl
init|=
operator|new
name|ReservationsTableBuilder
argument_list|()
decl_stmt|;
name|String
name|resvHtml
init|=
name|resvTbl
operator|.
name|htmlTableForReservations
argument_list|(
name|c
operator|.
name|effectiveReservations
argument_list|(
literal|true
argument_list|,
literal|true
argument_list|,
literal|true
argument_list|,
literal|true
argument_list|,
literal|true
argument_list|)
argument_list|,
literal|true
argument_list|,
name|c
operator|.
name|isEditableBy
argument_list|(
name|user
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|resvHtml
operator|!=
literal|null
condition|)
name|request
operator|.
name|setAttribute
argument_list|(
name|Reservation
operator|.
name|RESV_REQUEST_ATTR
argument_list|,
name|resvHtml
argument_list|)
expr_stmt|;
if|if
condition|(
name|c
operator|.
name|getNbrRooms
argument_list|()
operator|.
name|intValue
argument_list|()
operator|>
literal|0
condition|)
block|{
name|Vector
name|roomLocations
init|=
name|TimetableDatabaseLoader
operator|.
name|computeRoomLocations
argument_list|(
name|c
argument_list|)
decl_stmt|;
name|StringBuffer
name|rooms
init|=
operator|new
name|StringBuffer
argument_list|()
decl_stmt|;
if|if
condition|(
name|roomLocations
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|request
operator|.
name|setAttribute
argument_list|(
name|Location
operator|.
name|AVAILABLE_LOCATIONS_ATTR
argument_list|,
literal|"<font color='red'><b>No rooms are available.</b></font>"
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|int
name|idx
init|=
literal|0
decl_stmt|;
for|for
control|(
name|Enumeration
name|e
init|=
name|roomLocations
operator|.
name|elements
argument_list|()
init|;
name|e
operator|.
name|hasMoreElements
argument_list|()
condition|;
name|idx
operator|++
control|)
block|{
name|RoomLocation
name|rl
init|=
operator|(
name|RoomLocation
operator|)
name|e
operator|.
name|nextElement
argument_list|()
decl_stmt|;
if|if
condition|(
name|idx
operator|>
literal|0
condition|)
name|rooms
operator|.
name|append
argument_list|(
literal|", "
argument_list|)
expr_stmt|;
if|if
condition|(
name|idx
operator|==
literal|4
condition|)
name|rooms
operator|.
name|append
argument_list|(
literal|"<span id='room_dots' onMouseOver=\"this.style.cursor='hand';this.style.cursor='pointer';\" style='display:inline'><a onClick=\"document.getElementById('room_dots').style.display='none';document.getElementById('room_rest').style.display='inline';\">...</a></span><span id='room_rest' style='display:none'>"
argument_list|)
expr_stmt|;
name|rooms
operator|.
name|append
argument_list|(
literal|"<span title='"
operator|+
name|PreferenceLevel
operator|.
name|int2string
argument_list|(
name|rl
operator|.
name|getPreference
argument_list|()
argument_list|)
operator|+
literal|" "
operator|+
name|rl
operator|.
name|getName
argument_list|()
operator|+
literal|" ("
operator|+
name|rl
operator|.
name|getRoomSize
argument_list|()
operator|+
literal|" seats)'>"
operator|+
literal|"<font color='"
operator|+
name|PreferenceLevel
operator|.
name|int2color
argument_list|(
name|rl
operator|.
name|getPreference
argument_list|()
argument_list|)
operator|+
literal|"'>"
operator|+
name|rl
operator|.
name|getName
argument_list|()
operator|+
literal|"</font></span>"
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|idx
operator|>=
literal|4
condition|)
name|rooms
operator|.
name|append
argument_list|(
literal|"</span>"
argument_list|)
expr_stmt|;
if|if
condition|(
name|roomLocations
operator|.
name|size
argument_list|()
operator|<
name|c
operator|.
name|getNbrRooms
argument_list|()
operator|.
name|intValue
argument_list|()
condition|)
block|{
name|request
operator|.
name|setAttribute
argument_list|(
name|Location
operator|.
name|AVAILABLE_LOCATIONS_ATTR
argument_list|,
literal|"<font color='red'><b>Not enough rooms are available:</b></font> "
operator|+
name|rooms
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|request
operator|.
name|setAttribute
argument_list|(
name|Location
operator|.
name|AVAILABLE_LOCATIONS_ATTR
argument_list|,
name|roomLocations
operator|.
name|size
argument_list|()
operator|+
literal|" ("
operator|+
name|rooms
operator|+
literal|")"
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
comment|/** 	     * Set up instructor lists 	     * @param request 	     * @param frm 	     * @param errors 	     */
specifier|protected
name|void
name|setupInstructors
parameter_list|(
name|HttpServletRequest
name|request
parameter_list|,
name|ClassEditForm
name|frm
parameter_list|,
name|Class_
name|c
parameter_list|)
throws|throws
name|Exception
block|{
name|List
name|instructors
init|=
name|frm
operator|.
name|getInstructors
argument_list|()
decl_stmt|;
if|if
condition|(
name|instructors
operator|.
name|size
argument_list|()
operator|==
literal|0
condition|)
return|return;
comment|// Get dept instructor list
name|LookupTables
operator|.
name|setupInstructors
argument_list|(
name|request
argument_list|,
name|c
operator|.
name|getDepartmentForSubjectArea
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
name|Vector
name|deptInstrList
init|=
operator|(
name|Vector
operator|)
name|request
operator|.
name|getAttribute
argument_list|(
name|DepartmentalInstructor
operator|.
name|INSTR_LIST_ATTR_NAME
argument_list|)
decl_stmt|;
comment|// For each instructor set the instructor list
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|instructors
operator|.
name|size
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|request
operator|.
name|setAttribute
argument_list|(
name|DepartmentalInstructor
operator|.
name|INSTR_LIST_ATTR_NAME
operator|+
name|i
argument_list|,
name|deptInstrList
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

