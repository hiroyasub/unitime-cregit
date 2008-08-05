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
name|form
package|;
end_package

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
name|Hashtable
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
name|DepartmentStatusType
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
name|RoomType
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
name|util
operator|.
name|CalendarUtils
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
name|ReferenceList
import|;
end_import

begin_comment
comment|/**   * MyEclipse Struts  * Creation date: 02-18-2005  *   * XDoclet definition:  * @struts:form name="sessionEditForm"  */
end_comment

begin_class
specifier|public
class|class
name|SessionEditForm
extends|extends
name|ActionForm
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|3258410646873060656L
decl_stmt|;
comment|// --------------------------------------------------------- Instance Variables
name|Session
name|session
init|=
operator|new
name|Session
argument_list|()
decl_stmt|;
name|ReferenceList
name|statusOptions
decl_stmt|;
name|String
name|academicInitiative
decl_stmt|;
name|String
name|academicYear
decl_stmt|;
name|String
name|academicTerm
decl_stmt|;
name|String
name|sessionStart
decl_stmt|;
name|String
name|sessionEnd
decl_stmt|;
name|String
name|classesEnd
decl_stmt|;
name|String
name|examStart
decl_stmt|;
name|String
name|eventStart
decl_stmt|;
name|String
name|eventEnd
decl_stmt|;
name|String
name|defaultDatePatternId
decl_stmt|;
name|String
name|defaultDatePatternLabel
decl_stmt|;
name|Hashtable
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|roomOptionMessage
init|=
operator|new
name|Hashtable
argument_list|()
decl_stmt|;
name|Hashtable
argument_list|<
name|String
argument_list|,
name|Boolean
argument_list|>
name|roomOptionScheduleEvents
init|=
operator|new
name|Hashtable
argument_list|()
decl_stmt|;
comment|// --------------------------------------------------------- Methods
specifier|public
name|ActionErrors
name|validate
parameter_list|(
name|ActionMapping
name|arg0
parameter_list|,
name|HttpServletRequest
name|arg1
parameter_list|)
block|{
name|ActionErrors
name|errors
init|=
operator|new
name|ActionErrors
argument_list|()
decl_stmt|;
comment|// Check data fields
if|if
condition|(
name|academicInitiative
operator|==
literal|null
operator|||
name|academicInitiative
operator|.
name|trim
argument_list|()
operator|.
name|length
argument_list|()
operator|==
literal|0
condition|)
name|errors
operator|.
name|add
argument_list|(
literal|"academicInitiative"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.required"
argument_list|,
literal|"Academic Initiative"
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|academicTerm
operator|==
literal|null
operator|||
name|academicTerm
operator|.
name|trim
argument_list|()
operator|.
name|length
argument_list|()
operator|==
literal|0
condition|)
name|errors
operator|.
name|add
argument_list|(
literal|"academicTerm"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.required"
argument_list|,
literal|"Academic Term"
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|academicYear
operator|==
literal|null
operator|||
name|academicYear
operator|.
name|trim
argument_list|()
operator|.
name|length
argument_list|()
operator|==
literal|0
condition|)
name|errors
operator|.
name|add
argument_list|(
literal|"academicYear"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.required"
argument_list|,
literal|"Academic Year"
argument_list|)
argument_list|)
expr_stmt|;
else|else
block|{
try|try
block|{
name|int
name|year
init|=
name|Integer
operator|.
name|parseInt
argument_list|(
name|academicYear
argument_list|)
decl_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|errors
operator|.
name|add
argument_list|(
literal|"academicYear"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.numeric"
argument_list|,
literal|"Academic Year"
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
name|validateDates
argument_list|(
name|errors
argument_list|)
expr_stmt|;
if|if
condition|(
name|getStatus
argument_list|()
operator|==
literal|null
operator|||
name|getStatus
argument_list|()
operator|.
name|trim
argument_list|()
operator|.
name|length
argument_list|()
operator|==
literal|0
condition|)
name|errors
operator|.
name|add
argument_list|(
literal|"status"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.required"
argument_list|,
literal|"Session Status"
argument_list|)
argument_list|)
expr_stmt|;
comment|// Check for duplicate academic initiative, year& term
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
name|Session
name|sessn
init|=
name|Session
operator|.
name|getSessionUsingInitiativeYearTerm
argument_list|(
name|academicInitiative
argument_list|,
name|academicYear
argument_list|,
name|academicTerm
argument_list|)
decl_stmt|;
if|if
condition|(
name|session
operator|.
name|getSessionId
argument_list|()
operator|==
literal|null
operator|&&
name|sessn
operator|!=
literal|null
condition|)
name|errors
operator|.
name|add
argument_list|(
literal|"sessionId"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.generic"
argument_list|,
literal|"An academic session for the initiative, year and term already exists"
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|session
operator|.
name|getSessionId
argument_list|()
operator|!=
literal|null
operator|&&
name|sessn
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
operator|!
name|session
operator|.
name|getSessionId
argument_list|()
operator|.
name|equals
argument_list|(
name|sessn
operator|.
name|getSessionId
argument_list|()
argument_list|)
condition|)
name|errors
operator|.
name|add
argument_list|(
literal|"sessionId"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.generic"
argument_list|,
literal|"Another academic session for the same initiative, year and term already exists"
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|errors
return|;
block|}
comment|/** 	 * Validates all the dates 	 * @param errors 	 */
specifier|public
name|void
name|validateDates
parameter_list|(
name|ActionErrors
name|errors
parameter_list|)
block|{
name|String
name|df
init|=
literal|"MM/dd/yyyy"
decl_stmt|;
if|if
condition|(
operator|!
name|CalendarUtils
operator|.
name|isValidDate
argument_list|(
name|sessionStart
argument_list|,
name|df
argument_list|)
condition|)
name|errors
operator|.
name|add
argument_list|(
literal|"sessionStart"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.invalidDate"
argument_list|,
literal|"Session Start Date"
argument_list|)
argument_list|)
expr_stmt|;
else|else
block|{
name|Date
name|d1
init|=
name|CalendarUtils
operator|.
name|getDate
argument_list|(
name|sessionStart
argument_list|,
name|df
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|CalendarUtils
operator|.
name|isValidDate
argument_list|(
name|sessionEnd
argument_list|,
name|df
argument_list|)
condition|)
name|errors
operator|.
name|add
argument_list|(
literal|"sessionEnd"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.invalidDate"
argument_list|,
literal|"Session End Date"
argument_list|)
argument_list|)
expr_stmt|;
else|else
block|{
name|Date
name|d2
init|=
name|CalendarUtils
operator|.
name|getDate
argument_list|(
name|sessionEnd
argument_list|,
name|df
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|d2
operator|.
name|after
argument_list|(
name|d1
argument_list|)
condition|)
name|errors
operator|.
name|add
argument_list|(
literal|"sessionEnd"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.generic"
argument_list|,
literal|"Session End Date must occur AFTER Session Start Date"
argument_list|)
argument_list|)
expr_stmt|;
else|else
block|{
if|if
condition|(
operator|!
name|CalendarUtils
operator|.
name|isValidDate
argument_list|(
name|classesEnd
argument_list|,
name|df
argument_list|)
condition|)
name|errors
operator|.
name|add
argument_list|(
literal|"classesEnd"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.invalidDate"
argument_list|,
literal|"Classes End Date"
argument_list|)
argument_list|)
expr_stmt|;
else|else
block|{
name|Date
name|d3
init|=
name|CalendarUtils
operator|.
name|getDate
argument_list|(
name|classesEnd
argument_list|,
name|df
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|d3
operator|.
name|after
argument_list|(
name|d1
argument_list|)
condition|)
block|{
name|errors
operator|.
name|add
argument_list|(
literal|"classesEnd"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.generic"
argument_list|,
literal|"Classes End Date must occur AFTER Session Start Date"
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
operator|!
operator|(
name|d3
operator|.
name|before
argument_list|(
name|d2
argument_list|)
operator|||
name|d3
operator|.
name|equals
argument_list|(
name|d2
argument_list|)
operator|)
condition|)
block|{
name|errors
operator|.
name|add
argument_list|(
literal|"classesEnd"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.generic"
argument_list|,
literal|"Classes End Date must occur ON or BEFORE Session End Date"
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
if|if
condition|(
operator|!
name|CalendarUtils
operator|.
name|isValidDate
argument_list|(
name|examStart
argument_list|,
name|df
argument_list|)
condition|)
name|errors
operator|.
name|add
argument_list|(
literal|"examStart"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.invalidDate"
argument_list|,
literal|"Examinations Start Date"
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
name|CalendarUtils
operator|.
name|isValidDate
argument_list|(
name|eventStart
argument_list|,
name|df
argument_list|)
condition|)
name|errors
operator|.
name|add
argument_list|(
literal|"eventStart"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.invalidDate"
argument_list|,
literal|"Event Start Date"
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
name|CalendarUtils
operator|.
name|isValidDate
argument_list|(
name|eventEnd
argument_list|,
name|df
argument_list|)
condition|)
name|errors
operator|.
name|add
argument_list|(
literal|"eventEnd"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.invalidDate"
argument_list|,
literal|"Event End Date"
argument_list|)
argument_list|)
expr_stmt|;
name|Date
name|d4
init|=
name|CalendarUtils
operator|.
name|getDate
argument_list|(
name|eventStart
argument_list|,
name|df
argument_list|)
decl_stmt|;
name|Date
name|d5
init|=
name|CalendarUtils
operator|.
name|getDate
argument_list|(
name|eventEnd
argument_list|,
name|df
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|d4
operator|.
name|before
argument_list|(
name|d5
argument_list|)
condition|)
block|{
name|errors
operator|.
name|add
argument_list|(
literal|"eventEnd"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.generic"
argument_list|,
literal|"Event End Date must occur AFTER Event Start Date"
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
block|}
block|}
block|}
comment|/** 	 * @return Returns the session. 	 */
specifier|public
name|Session
name|getSession
parameter_list|()
block|{
return|return
name|session
return|;
block|}
comment|/** 	 * @param session The session to set. 	 */
specifier|public
name|void
name|setSession
parameter_list|(
name|Session
name|session
parameter_list|)
block|{
name|this
operator|.
name|session
operator|=
name|session
expr_stmt|;
block|}
specifier|public
name|boolean
name|equals
parameter_list|(
name|Object
name|arg0
parameter_list|)
block|{
return|return
name|session
operator|.
name|equals
argument_list|(
name|arg0
argument_list|)
return|;
block|}
specifier|public
name|String
name|getAcademicInitiative
parameter_list|()
block|{
return|return
name|academicInitiative
return|;
block|}
specifier|public
name|int
name|hashCode
parameter_list|()
block|{
return|return
name|session
operator|.
name|hashCode
argument_list|()
return|;
block|}
specifier|public
name|void
name|setAcademicInitiative
parameter_list|(
name|String
name|academicInitiative
parameter_list|)
block|{
name|this
operator|.
name|academicInitiative
operator|=
name|academicInitiative
expr_stmt|;
block|}
comment|/** 	 * @return 	 */
specifier|public
name|Long
name|getSessionId
parameter_list|()
block|{
return|return
name|session
operator|.
name|getSessionId
argument_list|()
return|;
block|}
comment|/** 	 * @param sessionId 	 */
specifier|public
name|void
name|setSessionId
parameter_list|(
name|Long
name|sessionId
parameter_list|)
block|{
if|if
condition|(
name|sessionId
operator|!=
literal|null
operator|&&
name|sessionId
operator|.
name|longValue
argument_list|()
operator|<=
literal|0
condition|)
name|session
operator|.
name|setSessionId
argument_list|(
literal|null
argument_list|)
expr_stmt|;
else|else
name|session
operator|.
name|setSessionId
argument_list|(
name|sessionId
argument_list|)
expr_stmt|;
block|}
comment|/** 	 * @return 	 */
specifier|public
name|String
name|getStatus
parameter_list|()
block|{
return|return
operator|(
name|session
operator|.
name|getStatusType
argument_list|()
operator|==
literal|null
condition|?
literal|null
else|:
name|session
operator|.
name|getStatusType
argument_list|()
operator|.
name|getReference
argument_list|()
operator|)
return|;
block|}
specifier|public
name|DepartmentStatusType
name|getStatusType
parameter_list|()
block|{
return|return
name|session
operator|.
name|getStatusType
argument_list|()
return|;
block|}
comment|/** 	 * @param status 	 */
specifier|public
name|void
name|setStatus
parameter_list|(
name|String
name|status
parameter_list|)
throws|throws
name|Exception
block|{
name|session
operator|.
name|setStatusType
argument_list|(
name|status
operator|==
literal|null
operator|||
name|status
operator|.
name|length
argument_list|()
operator|==
literal|0
condition|?
literal|null
else|:
name|DepartmentStatusType
operator|.
name|findByRef
argument_list|(
name|status
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|/** 	 * @return Returns the statusOptions. 	 */
specifier|public
name|ReferenceList
name|getStatusOptions
parameter_list|()
block|{
if|if
condition|(
name|statusOptions
operator|==
literal|null
condition|)
name|statusOptions
operator|=
name|Session
operator|.
name|getSessionStatusList
argument_list|()
expr_stmt|;
return|return
name|statusOptions
return|;
block|}
comment|/** 	 * @param statusOptions The statusOptions to set. 	 */
specifier|public
name|void
name|setStatusOptions
parameter_list|(
name|ReferenceList
name|statusOptions
parameter_list|)
block|{
name|this
operator|.
name|statusOptions
operator|=
name|statusOptions
expr_stmt|;
block|}
comment|/** 	 * @return 	 */
specifier|public
name|String
name|getAcademicInitiativeDisplayString
parameter_list|()
block|{
return|return
name|session
operator|.
name|academicInitiativeDisplayString
argument_list|()
return|;
block|}
comment|/** 	 * @return 	 */
specifier|public
name|String
name|getLabel
parameter_list|()
block|{
return|return
name|session
operator|.
name|getLabel
argument_list|()
return|;
block|}
specifier|public
name|String
name|getAcademicTerm
parameter_list|()
block|{
return|return
name|academicTerm
return|;
block|}
specifier|public
name|void
name|setAcademicTerm
parameter_list|(
name|String
name|academicTerm
parameter_list|)
block|{
name|this
operator|.
name|academicTerm
operator|=
name|academicTerm
expr_stmt|;
block|}
specifier|public
name|String
name|getAcademicYear
parameter_list|()
block|{
return|return
name|academicYear
return|;
block|}
specifier|public
name|void
name|setAcademicYear
parameter_list|(
name|String
name|academicYear
parameter_list|)
block|{
name|this
operator|.
name|academicYear
operator|=
name|academicYear
expr_stmt|;
block|}
specifier|public
name|String
name|getDefaultDatePatternId
parameter_list|()
block|{
return|return
name|defaultDatePatternId
return|;
block|}
specifier|public
name|void
name|setDefaultDatePatternId
parameter_list|(
name|String
name|defaultDatePatternId
parameter_list|)
block|{
name|this
operator|.
name|defaultDatePatternId
operator|=
name|defaultDatePatternId
expr_stmt|;
block|}
specifier|public
name|String
name|getDefaultDatePatternLabel
parameter_list|()
block|{
return|return
name|defaultDatePatternLabel
return|;
block|}
specifier|public
name|void
name|setDefaultDatePatternLabel
parameter_list|(
name|String
name|defaultDatePatternLabel
parameter_list|)
block|{
name|this
operator|.
name|defaultDatePatternLabel
operator|=
name|defaultDatePatternLabel
expr_stmt|;
block|}
specifier|public
name|String
name|getClassesEnd
parameter_list|()
block|{
return|return
name|classesEnd
return|;
block|}
specifier|public
name|void
name|setClassesEnd
parameter_list|(
name|String
name|classesEnd
parameter_list|)
block|{
name|this
operator|.
name|classesEnd
operator|=
name|classesEnd
expr_stmt|;
block|}
specifier|public
name|String
name|getSessionEnd
parameter_list|()
block|{
return|return
name|sessionEnd
return|;
block|}
specifier|public
name|void
name|setSessionEnd
parameter_list|(
name|String
name|sessionEnd
parameter_list|)
block|{
name|this
operator|.
name|sessionEnd
operator|=
name|sessionEnd
expr_stmt|;
block|}
specifier|public
name|String
name|getSessionStart
parameter_list|()
block|{
return|return
name|sessionStart
return|;
block|}
specifier|public
name|void
name|setSessionStart
parameter_list|(
name|String
name|sessionStart
parameter_list|)
block|{
name|this
operator|.
name|sessionStart
operator|=
name|sessionStart
expr_stmt|;
block|}
specifier|public
name|String
name|getExamStart
parameter_list|()
block|{
return|return
name|examStart
return|;
block|}
specifier|public
name|void
name|setExamStart
parameter_list|(
name|String
name|examStart
parameter_list|)
block|{
name|this
operator|.
name|examStart
operator|=
name|examStart
expr_stmt|;
block|}
specifier|public
name|String
name|getEventStart
parameter_list|()
block|{
return|return
name|eventStart
return|;
block|}
specifier|public
name|void
name|setEventStart
parameter_list|(
name|String
name|eventStart
parameter_list|)
block|{
name|this
operator|.
name|eventStart
operator|=
name|eventStart
expr_stmt|;
block|}
specifier|public
name|String
name|getEventEnd
parameter_list|()
block|{
return|return
name|eventEnd
return|;
block|}
specifier|public
name|void
name|setEventEnd
parameter_list|(
name|String
name|eventEnd
parameter_list|)
block|{
name|this
operator|.
name|eventEnd
operator|=
name|eventEnd
expr_stmt|;
block|}
specifier|public
name|Set
argument_list|<
name|RoomType
argument_list|>
name|getRoomTypes
parameter_list|()
block|{
return|return
name|RoomType
operator|.
name|findAll
argument_list|()
return|;
block|}
specifier|public
name|String
name|getRoomOptionMessage
parameter_list|(
name|String
name|roomType
parameter_list|)
block|{
return|return
name|roomOptionMessage
operator|.
name|get
argument_list|(
name|roomType
argument_list|)
return|;
block|}
specifier|public
name|void
name|setRoomOptionMessage
parameter_list|(
name|String
name|roomType
parameter_list|,
name|String
name|message
parameter_list|)
block|{
if|if
condition|(
name|message
operator|==
literal|null
condition|)
name|roomOptionMessage
operator|.
name|remove
argument_list|(
name|roomType
argument_list|)
expr_stmt|;
else|else
name|roomOptionMessage
operator|.
name|put
argument_list|(
name|roomType
argument_list|,
name|message
argument_list|)
expr_stmt|;
block|}
specifier|public
name|boolean
name|getRoomOptionScheduleEvents
parameter_list|(
name|String
name|roomType
parameter_list|)
block|{
name|Boolean
name|ret
init|=
name|roomOptionScheduleEvents
operator|.
name|get
argument_list|(
name|roomType
argument_list|)
decl_stmt|;
return|return
operator|(
name|ret
operator|!=
literal|null
operator|&&
name|ret
operator|.
name|booleanValue
argument_list|()
operator|)
return|;
block|}
specifier|public
name|void
name|setRoomOptionScheduleEvents
parameter_list|(
name|String
name|roomType
parameter_list|,
name|boolean
name|enable
parameter_list|)
block|{
name|roomOptionScheduleEvents
operator|.
name|put
argument_list|(
name|roomType
argument_list|,
name|enable
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

