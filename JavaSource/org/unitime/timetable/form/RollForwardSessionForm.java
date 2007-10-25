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
name|form
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
name|Collection
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
name|timetable
operator|.
name|model
operator|.
name|DatePattern
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
name|Session
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
name|CourseOfferingDAO
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
name|RoomFeatureDAO
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
name|RoomGroupDAO
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
name|TimetableManagerDAO
import|;
end_import

begin_comment
comment|/**   * MyEclipse Struts  * Creation date: 02-27-2007  *   * XDoclet definition:  * @struts.form name="exportSessionToMsfForm"  */
end_comment

begin_class
specifier|public
class|class
name|RollForwardSessionForm
extends|extends
name|ActionForm
block|{
comment|/** 	 *  	 */
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|7553214589949959977L
decl_stmt|;
specifier|private
name|Collection
name|subjectAreas
decl_stmt|;
specifier|private
name|String
index|[]
name|subjectAreaIds
decl_stmt|;
specifier|private
name|String
name|buttonAction
decl_stmt|;
specifier|private
name|boolean
name|isAdmin
decl_stmt|;
specifier|private
name|Collection
name|sessions
decl_stmt|;
specifier|private
name|Long
name|sessionToRollForwardTo
decl_stmt|;
specifier|private
name|Boolean
name|rollForwardDatePatterns
decl_stmt|;
specifier|private
name|Long
name|sessionToRollDatePatternsForwardFrom
decl_stmt|;
specifier|private
name|Boolean
name|rollForwardTimePatterns
decl_stmt|;
specifier|private
name|Long
name|sessionToRollTimePatternsForwardFrom
decl_stmt|;
specifier|private
name|Boolean
name|rollForwardDepartments
decl_stmt|;
specifier|private
name|Long
name|sessionToRollDeptsFowardFrom
decl_stmt|;
specifier|private
name|Boolean
name|rollForwardManagers
decl_stmt|;
specifier|private
name|Long
name|sessionToRollManagersForwardFrom
decl_stmt|;
specifier|private
name|Boolean
name|rollForwardRoomData
decl_stmt|;
specifier|private
name|Long
name|sessionToRollRoomDataForwardFrom
decl_stmt|;
specifier|private
name|Boolean
name|rollForwardSubjectAreas
decl_stmt|;
specifier|private
name|Long
name|sessionToRollSubjectAreasForwardFrom
decl_stmt|;
specifier|private
name|Boolean
name|rollForwardInstructorData
decl_stmt|;
specifier|private
name|Long
name|sessionToRollInstructorDataForwardFrom
decl_stmt|;
specifier|private
name|Boolean
name|rollForwardCourseOfferings
decl_stmt|;
specifier|private
name|Long
name|sessionToRollCourseOfferingsForwardFrom
decl_stmt|;
specifier|private
name|Collection
name|availableRollForwardSubjectAreas
decl_stmt|;
specifier|private
name|String
index|[]
name|rollForwardSubjectAreaIds
decl_stmt|;
specifier|private
name|Boolean
name|rollForwardClassPreferences
decl_stmt|;
specifier|private
name|Collection
name|availableClassSubjectAreas
decl_stmt|;
specifier|private
name|String
index|[]
name|rollForwardClassPrefsSubjectIds
decl_stmt|;
specifier|private
name|Boolean
name|rollForwardClassInstructors
decl_stmt|;
specifier|private
name|String
index|[]
name|rollForwardClassInstrSubjectIds
decl_stmt|;
comment|/**  	 * Method validate 	 * @param mapping 	 * @param request 	 * @return ActionErrors 	 */
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
name|ActionErrors
name|errors
init|=
operator|new
name|ActionErrors
argument_list|()
decl_stmt|;
name|validateSessionToRollForwardTo
argument_list|(
name|errors
argument_list|)
expr_stmt|;
return|return
name|errors
return|;
block|}
specifier|private
name|void
name|validateRollForwardSessionHasNoDataOfType
parameter_list|(
name|ActionErrors
name|errors
parameter_list|,
name|Session
name|sessionToRollForwardTo
parameter_list|,
name|String
name|rollForwardType
parameter_list|,
name|Collection
name|checkCollection
parameter_list|)
block|{
if|if
condition|(
name|checkCollection
operator|!=
literal|null
operator|&&
operator|!
name|checkCollection
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|errors
operator|.
name|add
argument_list|(
literal|"sessionHasData"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.rollForward.sessionHasData"
argument_list|,
name|rollForwardType
argument_list|,
name|sessionToRollForwardTo
operator|.
name|getLabel
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
specifier|private
name|void
name|validateRollForward
parameter_list|(
name|ActionErrors
name|errors
parameter_list|,
name|Session
name|sessionToRollForwardTo
parameter_list|,
name|Long
name|sessionIdToRollForwardFrom
parameter_list|,
name|String
name|rollForwardType
parameter_list|,
name|Collection
name|checkCollection
parameter_list|)
block|{
name|validateRollForwardSessionHasNoDataOfType
argument_list|(
name|errors
argument_list|,
name|sessionToRollForwardTo
argument_list|,
name|rollForwardType
argument_list|,
name|checkCollection
argument_list|)
expr_stmt|;
name|Session
name|sessionToRollForwardFrom
init|=
name|Session
operator|.
name|getSessionById
argument_list|(
name|sessionIdToRollForwardFrom
argument_list|)
decl_stmt|;
if|if
condition|(
name|sessionToRollForwardFrom
operator|==
literal|null
condition|)
block|{
name|errors
operator|.
name|add
argument_list|(
literal|"mustSelectSession"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.rollForward.missingFromSession"
argument_list|,
name|rollForwardType
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|sessionToRollForwardFrom
operator|.
name|equals
argument_list|(
name|sessionToRollForwardTo
argument_list|)
condition|)
block|{
name|errors
operator|.
name|add
argument_list|(
literal|"sessionsMustBeDifferent"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.rollForward.sessionsMustBeDifferent"
argument_list|,
name|rollForwardType
argument_list|,
name|sessionToRollForwardTo
operator|.
name|getLabel
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
specifier|public
name|void
name|validateSessionToRollForwardTo
parameter_list|(
name|ActionErrors
name|errors
parameter_list|)
block|{
name|Session
name|s
init|=
name|Session
operator|.
name|getSessionById
argument_list|(
name|getSessionToRollForwardTo
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|s
operator|==
literal|null
condition|)
block|{
name|errors
operator|.
name|add
argument_list|(
literal|"mustSelectSession"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.rollForward.missingToSession"
argument_list|)
argument_list|)
expr_stmt|;
return|return;
block|}
if|if
condition|(
name|getRollForwardDatePatterns
argument_list|()
operator|.
name|booleanValue
argument_list|()
condition|)
block|{
name|validateRollForward
argument_list|(
name|errors
argument_list|,
name|s
argument_list|,
name|getSessionToRollDatePatternsForwardFrom
argument_list|()
argument_list|,
literal|"Date Patterns"
argument_list|,
name|DatePattern
operator|.
name|findAll
argument_list|(
name|s
argument_list|,
literal|true
argument_list|,
literal|null
argument_list|,
literal|null
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|getRollForwardTimePatterns
argument_list|()
operator|.
name|booleanValue
argument_list|()
condition|)
block|{
name|validateRollForward
argument_list|(
name|errors
argument_list|,
name|s
argument_list|,
name|getSessionToRollTimePatternsForwardFrom
argument_list|()
argument_list|,
literal|"Time Patterns"
argument_list|,
name|TimePattern
operator|.
name|findAll
argument_list|(
name|s
argument_list|,
literal|null
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|getRollForwardDepartments
argument_list|()
operator|.
name|booleanValue
argument_list|()
condition|)
block|{
name|validateRollForward
argument_list|(
name|errors
argument_list|,
name|s
argument_list|,
name|getSessionToRollDeptsFowardFrom
argument_list|()
argument_list|,
literal|"Departments"
argument_list|,
name|s
operator|.
name|getDepartments
argument_list|()
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|getRollForwardManagers
argument_list|()
operator|.
name|booleanValue
argument_list|()
condition|)
block|{
name|TimetableManagerDAO
name|tmDao
init|=
operator|new
name|TimetableManagerDAO
argument_list|()
decl_stmt|;
name|validateRollForward
argument_list|(
name|errors
argument_list|,
name|s
argument_list|,
name|getSessionToRollManagersForwardFrom
argument_list|()
argument_list|,
literal|"Managers"
argument_list|,
name|tmDao
operator|.
name|getQuery
argument_list|(
literal|"from TimetableManager tm inner join tm.departments d where d.session.uniqueId ="
operator|+
name|s
operator|.
name|getUniqueId
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
operator|.
name|list
argument_list|()
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|getRollForwardRoomData
argument_list|()
operator|.
name|booleanValue
argument_list|()
condition|)
block|{
name|validateRollForward
argument_list|(
name|errors
argument_list|,
name|s
argument_list|,
name|getSessionToRollRoomDataForwardFrom
argument_list|()
argument_list|,
literal|"Buildings"
argument_list|,
operator|new
name|ArrayList
argument_list|()
argument_list|)
expr_stmt|;
name|validateRollForwardSessionHasNoDataOfType
argument_list|(
name|errors
argument_list|,
name|s
argument_list|,
literal|"Buildings"
argument_list|,
name|s
operator|.
name|getBuildings
argument_list|()
argument_list|)
expr_stmt|;
name|validateRollForwardSessionHasNoDataOfType
argument_list|(
name|errors
argument_list|,
name|s
argument_list|,
literal|"Rooms"
argument_list|,
name|s
operator|.
name|getRooms
argument_list|()
argument_list|)
expr_stmt|;
name|RoomFeatureDAO
name|rfDao
init|=
operator|new
name|RoomFeatureDAO
argument_list|()
decl_stmt|;
name|validateRollForwardSessionHasNoDataOfType
argument_list|(
name|errors
argument_list|,
name|s
argument_list|,
literal|"Room Features"
argument_list|,
name|rfDao
operator|.
name|getQuery
argument_list|(
literal|"from RoomFeature rf where rf.department.session.uniqueId = "
operator|+
name|s
operator|.
name|getUniqueId
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
operator|.
name|list
argument_list|()
argument_list|)
expr_stmt|;
name|RoomGroupDAO
name|rgDao
init|=
operator|new
name|RoomGroupDAO
argument_list|()
decl_stmt|;
name|validateRollForwardSessionHasNoDataOfType
argument_list|(
name|errors
argument_list|,
name|s
argument_list|,
literal|"Room Groups"
argument_list|,
name|rgDao
operator|.
name|getQuery
argument_list|(
literal|"from RoomGroup rg where rg.session.uniqueId = "
operator|+
name|s
operator|.
name|getUniqueId
argument_list|()
operator|.
name|toString
argument_list|()
operator|+
literal|" and rg.global = 0"
argument_list|)
operator|.
name|list
argument_list|()
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|getRollForwardSubjectAreas
argument_list|()
operator|.
name|booleanValue
argument_list|()
condition|)
block|{
name|validateRollForward
argument_list|(
name|errors
argument_list|,
name|s
argument_list|,
name|getSessionToRollSubjectAreasForwardFrom
argument_list|()
argument_list|,
literal|"Subject Areas"
argument_list|,
name|s
operator|.
name|getSubjectAreas
argument_list|()
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|getRollForwardInstructorData
argument_list|()
operator|.
name|booleanValue
argument_list|()
condition|)
block|{
name|DepartmentalInstructorDAO
name|diDao
init|=
operator|new
name|DepartmentalInstructorDAO
argument_list|()
decl_stmt|;
name|validateRollForward
argument_list|(
name|errors
argument_list|,
name|s
argument_list|,
name|getSessionToRollInstructorDataForwardFrom
argument_list|()
argument_list|,
literal|"Instructors"
argument_list|,
name|diDao
operator|.
name|getQuery
argument_list|(
literal|"from DepartmentalInstructor di where di.department.session.uniqueId = "
operator|+
name|s
operator|.
name|getUniqueId
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
operator|.
name|list
argument_list|()
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|getRollForwardCourseOfferings
argument_list|()
operator|.
name|booleanValue
argument_list|()
condition|)
block|{
name|validateRollForward
argument_list|(
name|errors
argument_list|,
name|s
argument_list|,
name|getSessionToRollCourseOfferingsForwardFrom
argument_list|()
argument_list|,
literal|"Course Offerings"
argument_list|,
operator|new
name|ArrayList
argument_list|()
argument_list|)
expr_stmt|;
name|CourseOfferingDAO
name|coDao
init|=
operator|new
name|CourseOfferingDAO
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
name|getRollForwardSubjectAreaIds
argument_list|()
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|String
name|queryStr
init|=
literal|"from CourseOffering co where co.subjectArea.session.uniqueId = "
operator|+
name|s
operator|.
name|getUniqueId
argument_list|()
operator|.
name|toString
argument_list|()
operator|+
literal|" and co.isControl = 1 and co.subjectArea.subjectAreaAbbreviation  = '"
operator|+
name|getRollForwardSubjectAreaIds
argument_list|()
index|[
name|i
index|]
operator|+
literal|"'"
decl_stmt|;
name|validateRollForwardSessionHasNoDataOfType
argument_list|(
name|errors
argument_list|,
name|s
argument_list|,
operator|(
literal|"Course Offerings - "
operator|+
name|getRollForwardSubjectAreaIds
argument_list|()
index|[
name|i
index|]
operator|)
argument_list|,
name|coDao
operator|.
name|getQuery
argument_list|(
name|queryStr
argument_list|)
operator|.
name|list
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
comment|/**  	 * Method init 	 */
specifier|public
name|void
name|init
parameter_list|()
block|{
name|subjectAreas
operator|=
operator|new
name|ArrayList
argument_list|()
expr_stmt|;
name|subjectAreaIds
operator|=
operator|new
name|String
index|[
literal|0
index|]
expr_stmt|;
name|isAdmin
operator|=
literal|false
expr_stmt|;
name|sessions
operator|=
literal|null
expr_stmt|;
name|sessionToRollForwardTo
operator|=
literal|null
expr_stmt|;
name|rollForwardDatePatterns
operator|=
operator|new
name|Boolean
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|sessionToRollDatePatternsForwardFrom
operator|=
literal|null
expr_stmt|;
name|rollForwardTimePatterns
operator|=
operator|new
name|Boolean
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|sessionToRollTimePatternsForwardFrom
operator|=
literal|null
expr_stmt|;
name|rollForwardDepartments
operator|=
operator|new
name|Boolean
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|sessionToRollDeptsFowardFrom
operator|=
literal|null
expr_stmt|;
name|rollForwardManagers
operator|=
operator|new
name|Boolean
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|sessionToRollManagersForwardFrom
operator|=
literal|null
expr_stmt|;
name|rollForwardRoomData
operator|=
operator|new
name|Boolean
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|sessionToRollRoomDataForwardFrom
operator|=
literal|null
expr_stmt|;
name|rollForwardSubjectAreas
operator|=
operator|new
name|Boolean
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|sessionToRollSubjectAreasForwardFrom
operator|=
literal|null
expr_stmt|;
name|rollForwardInstructorData
operator|=
operator|new
name|Boolean
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|sessionToRollInstructorDataForwardFrom
operator|=
literal|null
expr_stmt|;
name|rollForwardCourseOfferings
operator|=
operator|new
name|Boolean
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|sessionToRollCourseOfferingsForwardFrom
operator|=
literal|null
expr_stmt|;
name|availableRollForwardSubjectAreas
operator|=
operator|new
name|ArrayList
argument_list|()
expr_stmt|;
name|rollForwardSubjectAreaIds
operator|=
operator|new
name|String
index|[
literal|0
index|]
expr_stmt|;
name|rollForwardClassPreferences
operator|=
operator|new
name|Boolean
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|availableClassSubjectAreas
operator|=
operator|new
name|ArrayList
argument_list|()
expr_stmt|;
name|rollForwardClassPrefsSubjectIds
operator|=
operator|new
name|String
index|[
literal|0
index|]
expr_stmt|;
name|rollForwardClassInstructors
operator|=
operator|new
name|Boolean
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|rollForwardClassInstrSubjectIds
operator|=
operator|new
name|String
index|[
literal|0
index|]
expr_stmt|;
block|}
comment|/**  	 * Method reset 	 * @param mapping 	 * @param request 	 */
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
name|init
argument_list|()
expr_stmt|;
block|}
specifier|public
name|String
name|getButtonAction
parameter_list|()
block|{
return|return
name|buttonAction
return|;
block|}
specifier|public
name|void
name|setButtonAction
parameter_list|(
name|String
name|buttonAction
parameter_list|)
block|{
name|this
operator|.
name|buttonAction
operator|=
name|buttonAction
expr_stmt|;
block|}
specifier|public
name|String
index|[]
name|getSubjectAreaIds
parameter_list|()
block|{
return|return
name|subjectAreaIds
return|;
block|}
specifier|public
name|void
name|setSubjectAreaIds
parameter_list|(
name|String
index|[]
name|subjectAreaIds
parameter_list|)
block|{
name|this
operator|.
name|subjectAreaIds
operator|=
name|subjectAreaIds
expr_stmt|;
block|}
specifier|public
name|Collection
name|getSubjectAreas
parameter_list|()
block|{
return|return
name|subjectAreas
return|;
block|}
specifier|public
name|void
name|setSubjectAreas
parameter_list|(
name|Collection
name|subjectAreas
parameter_list|)
block|{
name|this
operator|.
name|subjectAreas
operator|=
name|subjectAreas
expr_stmt|;
block|}
specifier|public
name|boolean
name|isAdmin
parameter_list|()
block|{
return|return
name|isAdmin
return|;
block|}
specifier|public
name|void
name|setAdmin
parameter_list|(
name|boolean
name|isAdmin
parameter_list|)
block|{
name|this
operator|.
name|isAdmin
operator|=
name|isAdmin
expr_stmt|;
block|}
specifier|public
name|Collection
name|getAvailableClassSubjectAreas
parameter_list|()
block|{
return|return
name|availableClassSubjectAreas
return|;
block|}
specifier|public
name|void
name|setAvailableClassSubjectAreas
parameter_list|(
name|Collection
name|availableClassSubjectAreas
parameter_list|)
block|{
name|this
operator|.
name|availableClassSubjectAreas
operator|=
name|availableClassSubjectAreas
expr_stmt|;
block|}
specifier|public
name|Collection
name|getAvailableRollForwardSubjectAreas
parameter_list|()
block|{
return|return
name|availableRollForwardSubjectAreas
return|;
block|}
specifier|public
name|void
name|setAvailableRollForwardSubjectAreas
parameter_list|(
name|Collection
name|availableRollForwardSubjectAreas
parameter_list|)
block|{
name|this
operator|.
name|availableRollForwardSubjectAreas
operator|=
name|availableRollForwardSubjectAreas
expr_stmt|;
block|}
specifier|public
name|Boolean
name|getRollForwardClassPreferences
parameter_list|()
block|{
return|return
name|rollForwardClassPreferences
return|;
block|}
specifier|public
name|void
name|setRollForwardClassPreferences
parameter_list|(
name|Boolean
name|rollForwardClassPreferences
parameter_list|)
block|{
name|this
operator|.
name|rollForwardClassPreferences
operator|=
name|rollForwardClassPreferences
expr_stmt|;
block|}
specifier|public
name|String
index|[]
name|getRollForwardClassPrefsSubjectIds
parameter_list|()
block|{
return|return
name|rollForwardClassPrefsSubjectIds
return|;
block|}
specifier|public
name|void
name|setRollForwardClassPrefsSubjectIds
parameter_list|(
name|String
index|[]
name|rollForwardClassPrefsSubjectIds
parameter_list|)
block|{
name|this
operator|.
name|rollForwardClassPrefsSubjectIds
operator|=
name|rollForwardClassPrefsSubjectIds
expr_stmt|;
block|}
specifier|public
name|Boolean
name|getRollForwardCourseOfferings
parameter_list|()
block|{
return|return
name|rollForwardCourseOfferings
return|;
block|}
specifier|public
name|void
name|setRollForwardCourseOfferings
parameter_list|(
name|Boolean
name|rollForwardCourseOfferings
parameter_list|)
block|{
name|this
operator|.
name|rollForwardCourseOfferings
operator|=
name|rollForwardCourseOfferings
expr_stmt|;
block|}
specifier|public
name|Boolean
name|getRollForwardDatePatterns
parameter_list|()
block|{
return|return
name|rollForwardDatePatterns
return|;
block|}
specifier|public
name|void
name|setRollForwardDatePatterns
parameter_list|(
name|Boolean
name|rollForwardDatePatterns
parameter_list|)
block|{
name|this
operator|.
name|rollForwardDatePatterns
operator|=
name|rollForwardDatePatterns
expr_stmt|;
block|}
specifier|public
name|Boolean
name|getRollForwardDepartments
parameter_list|()
block|{
return|return
name|rollForwardDepartments
return|;
block|}
specifier|public
name|void
name|setRollForwardDepartments
parameter_list|(
name|Boolean
name|rollForwardDepartments
parameter_list|)
block|{
name|this
operator|.
name|rollForwardDepartments
operator|=
name|rollForwardDepartments
expr_stmt|;
block|}
specifier|public
name|Boolean
name|getRollForwardInstructorData
parameter_list|()
block|{
return|return
name|rollForwardInstructorData
return|;
block|}
specifier|public
name|void
name|setRollForwardInstructorData
parameter_list|(
name|Boolean
name|rollForwardInstructorData
parameter_list|)
block|{
name|this
operator|.
name|rollForwardInstructorData
operator|=
name|rollForwardInstructorData
expr_stmt|;
block|}
specifier|public
name|Boolean
name|getRollForwardManagers
parameter_list|()
block|{
return|return
name|rollForwardManagers
return|;
block|}
specifier|public
name|void
name|setRollForwardManagers
parameter_list|(
name|Boolean
name|rollForwardManagers
parameter_list|)
block|{
name|this
operator|.
name|rollForwardManagers
operator|=
name|rollForwardManagers
expr_stmt|;
block|}
specifier|public
name|Boolean
name|getRollForwardRoomData
parameter_list|()
block|{
return|return
name|rollForwardRoomData
return|;
block|}
specifier|public
name|void
name|setRollForwardRoomData
parameter_list|(
name|Boolean
name|rollForwardRoomData
parameter_list|)
block|{
name|this
operator|.
name|rollForwardRoomData
operator|=
name|rollForwardRoomData
expr_stmt|;
block|}
specifier|public
name|String
index|[]
name|getRollForwardSubjectAreaIds
parameter_list|()
block|{
return|return
name|rollForwardSubjectAreaIds
return|;
block|}
specifier|public
name|void
name|setRollForwardSubjectAreaIds
parameter_list|(
name|String
index|[]
name|rollForwardSubjectAreaIds
parameter_list|)
block|{
name|this
operator|.
name|rollForwardSubjectAreaIds
operator|=
name|rollForwardSubjectAreaIds
expr_stmt|;
block|}
specifier|public
name|Boolean
name|getRollForwardSubjectAreas
parameter_list|()
block|{
return|return
name|rollForwardSubjectAreas
return|;
block|}
specifier|public
name|void
name|setRollForwardSubjectAreas
parameter_list|(
name|Boolean
name|rollForwardSubjectAreas
parameter_list|)
block|{
name|this
operator|.
name|rollForwardSubjectAreas
operator|=
name|rollForwardSubjectAreas
expr_stmt|;
block|}
specifier|public
name|Long
name|getSessionToRollCourseOfferingsForwardFrom
parameter_list|()
block|{
return|return
name|sessionToRollCourseOfferingsForwardFrom
return|;
block|}
specifier|public
name|void
name|setSessionToRollCourseOfferingsForwardFrom
parameter_list|(
name|Long
name|sessionToRollCourseOfferingsForwardFrom
parameter_list|)
block|{
name|this
operator|.
name|sessionToRollCourseOfferingsForwardFrom
operator|=
name|sessionToRollCourseOfferingsForwardFrom
expr_stmt|;
block|}
specifier|public
name|Long
name|getSessionToRollDatePatternsForwardFrom
parameter_list|()
block|{
return|return
name|sessionToRollDatePatternsForwardFrom
return|;
block|}
specifier|public
name|void
name|setSessionToRollDatePatternsForwardFrom
parameter_list|(
name|Long
name|sessionToRollDatePatternsForwardFrom
parameter_list|)
block|{
name|this
operator|.
name|sessionToRollDatePatternsForwardFrom
operator|=
name|sessionToRollDatePatternsForwardFrom
expr_stmt|;
block|}
specifier|public
name|Long
name|getSessionToRollDeptsFowardFrom
parameter_list|()
block|{
return|return
name|sessionToRollDeptsFowardFrom
return|;
block|}
specifier|public
name|void
name|setSessionToRollDeptsFowardFrom
parameter_list|(
name|Long
name|sessionToRollDeptsFowardFrom
parameter_list|)
block|{
name|this
operator|.
name|sessionToRollDeptsFowardFrom
operator|=
name|sessionToRollDeptsFowardFrom
expr_stmt|;
block|}
specifier|public
name|Long
name|getSessionToRollForwardTo
parameter_list|()
block|{
return|return
name|sessionToRollForwardTo
return|;
block|}
specifier|public
name|void
name|setSessionToRollForwardTo
parameter_list|(
name|Long
name|sessionToRollForwardTo
parameter_list|)
block|{
name|this
operator|.
name|sessionToRollForwardTo
operator|=
name|sessionToRollForwardTo
expr_stmt|;
block|}
specifier|public
name|Long
name|getSessionToRollInstructorDataForwardFrom
parameter_list|()
block|{
return|return
name|sessionToRollInstructorDataForwardFrom
return|;
block|}
specifier|public
name|void
name|setSessionToRollInstructorDataForwardFrom
parameter_list|(
name|Long
name|sessionToRollInstructorDataForwardFrom
parameter_list|)
block|{
name|this
operator|.
name|sessionToRollInstructorDataForwardFrom
operator|=
name|sessionToRollInstructorDataForwardFrom
expr_stmt|;
block|}
specifier|public
name|Long
name|getSessionToRollManagersForwardFrom
parameter_list|()
block|{
return|return
name|sessionToRollManagersForwardFrom
return|;
block|}
specifier|public
name|void
name|setSessionToRollManagersForwardFrom
parameter_list|(
name|Long
name|sessionToRollManagersForwardFrom
parameter_list|)
block|{
name|this
operator|.
name|sessionToRollManagersForwardFrom
operator|=
name|sessionToRollManagersForwardFrom
expr_stmt|;
block|}
specifier|public
name|Long
name|getSessionToRollRoomDataForwardFrom
parameter_list|()
block|{
return|return
name|sessionToRollRoomDataForwardFrom
return|;
block|}
specifier|public
name|void
name|setSessionToRollRoomDataForwardFrom
parameter_list|(
name|Long
name|sessionToRollRoomDataForwardFrom
parameter_list|)
block|{
name|this
operator|.
name|sessionToRollRoomDataForwardFrom
operator|=
name|sessionToRollRoomDataForwardFrom
expr_stmt|;
block|}
specifier|public
name|Long
name|getSessionToRollSubjectAreasForwardFrom
parameter_list|()
block|{
return|return
name|sessionToRollSubjectAreasForwardFrom
return|;
block|}
specifier|public
name|void
name|setSessionToRollSubjectAreasForwardFrom
parameter_list|(
name|Long
name|sessionToRollSubjectAreasForwardFrom
parameter_list|)
block|{
name|this
operator|.
name|sessionToRollSubjectAreasForwardFrom
operator|=
name|sessionToRollSubjectAreasForwardFrom
expr_stmt|;
block|}
specifier|public
name|Collection
name|getSessions
parameter_list|()
block|{
return|return
name|sessions
return|;
block|}
specifier|public
name|void
name|setSessions
parameter_list|(
name|Collection
name|sessions
parameter_list|)
block|{
name|this
operator|.
name|sessions
operator|=
name|sessions
expr_stmt|;
block|}
specifier|public
name|Boolean
name|getRollForwardTimePatterns
parameter_list|()
block|{
return|return
name|rollForwardTimePatterns
return|;
block|}
specifier|public
name|void
name|setRollForwardTimePatterns
parameter_list|(
name|Boolean
name|rollForwardTimePatterns
parameter_list|)
block|{
name|this
operator|.
name|rollForwardTimePatterns
operator|=
name|rollForwardTimePatterns
expr_stmt|;
block|}
specifier|public
name|Long
name|getSessionToRollTimePatternsForwardFrom
parameter_list|()
block|{
return|return
name|sessionToRollTimePatternsForwardFrom
return|;
block|}
specifier|public
name|void
name|setSessionToRollTimePatternsForwardFrom
parameter_list|(
name|Long
name|sessionToRollTimePatternsForwardFrom
parameter_list|)
block|{
name|this
operator|.
name|sessionToRollTimePatternsForwardFrom
operator|=
name|sessionToRollTimePatternsForwardFrom
expr_stmt|;
block|}
specifier|public
name|Boolean
name|getRollForwardClassInstructors
parameter_list|()
block|{
return|return
name|rollForwardClassInstructors
return|;
block|}
specifier|public
name|void
name|setRollForwardClassInstructors
parameter_list|(
name|Boolean
name|rollForwardClassInstructors
parameter_list|)
block|{
name|this
operator|.
name|rollForwardClassInstructors
operator|=
name|rollForwardClassInstructors
expr_stmt|;
block|}
specifier|public
name|String
index|[]
name|getRollForwardClassInstrSubjectIds
parameter_list|()
block|{
return|return
name|rollForwardClassInstrSubjectIds
return|;
block|}
specifier|public
name|void
name|setRollForwardClassInstrSubjectIds
parameter_list|(
name|String
index|[]
name|rollForwardClassInstrSubjectIds
parameter_list|)
block|{
name|this
operator|.
name|rollForwardClassInstrSubjectIds
operator|=
name|rollForwardClassInstrSubjectIds
expr_stmt|;
block|}
block|}
end_class

end_unit

