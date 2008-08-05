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
name|HashMap
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Map
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
name|ActionMessage
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
name|actions
operator|.
name|LookupDispatchAction
import|;
end_import

begin_import
import|import
name|org
operator|.
name|hibernate
operator|.
name|HibernateException
import|;
end_import

begin_import
import|import
name|org
operator|.
name|hibernate
operator|.
name|Transaction
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
name|SessionEditForm
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
name|Roles
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
name|RoomTypeOption
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
name|dao
operator|.
name|DatePatternDAO
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
name|SessionDAO
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

begin_comment
comment|/**   * MyEclipse Struts  * Creation date: 02-18-2005  *   * XDoclet definition:  * @struts:action path="/sessionEdit" name="sessionEditForm" parameter="do" scope="request" validate="true"  * @struts:action-forward name="showEdit" path="/admin/sessionEdit.jsp"  * @struts:action-forward name="showAdd" path="/admin/sessionAdd.jsp"  * @struts:action-forward name="showSessionList" path="/sessionList.do" redirect="true"  */
end_comment

begin_class
specifier|public
class|class
name|SessionEditAction
extends|extends
name|LookupDispatchAction
block|{
comment|// --------------------------------------------------------- Instance Variables
comment|// --------------------------------------------------------- Methods
comment|/**  	 * Method execute 	 * @param mapping 	 * @param form 	 * @param request 	 * @param response 	 * @return ActionForward 	 * @throws HibernateException 	 */
specifier|protected
name|Map
name|getKeyMethodMap
parameter_list|()
block|{
name|Map
name|map
init|=
operator|new
name|HashMap
argument_list|()
decl_stmt|;
name|map
operator|.
name|put
argument_list|(
literal|"editSession"
argument_list|,
literal|"editSession"
argument_list|)
expr_stmt|;
name|map
operator|.
name|put
argument_list|(
literal|"button.addSession"
argument_list|,
literal|"addSession"
argument_list|)
expr_stmt|;
name|map
operator|.
name|put
argument_list|(
literal|"button.saveSession"
argument_list|,
literal|"saveSession"
argument_list|)
expr_stmt|;
name|map
operator|.
name|put
argument_list|(
literal|"button.updateSession"
argument_list|,
literal|"saveSession"
argument_list|)
expr_stmt|;
name|map
operator|.
name|put
argument_list|(
literal|"button.deleteSession"
argument_list|,
literal|"deleteSession"
argument_list|)
expr_stmt|;
name|map
operator|.
name|put
argument_list|(
literal|"button.cancelSessionEdit"
argument_list|,
literal|"cancelSessionEdit"
argument_list|)
expr_stmt|;
name|map
operator|.
name|put
argument_list|(
literal|"button.loadCrsOffrDemand"
argument_list|,
literal|"loadCrsOffrDemand"
argument_list|)
expr_stmt|;
name|map
operator|.
name|put
argument_list|(
literal|"button.viewCrsOffrDemand"
argument_list|,
literal|"viewCrsOffrDemand"
argument_list|)
expr_stmt|;
return|return
name|map
return|;
block|}
specifier|public
name|ActionForward
name|editSession
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
comment|// Check access
if|if
condition|(
operator|!
name|Web
operator|.
name|hasRole
argument_list|(
name|request
operator|.
name|getSession
argument_list|()
argument_list|,
operator|new
name|String
index|[]
block|{
name|Roles
operator|.
name|ADMIN_ROLE
block|}
block_content|)
block|)
block|{
throw|throw
operator|new
name|Exception
argument_list|(
literal|"Access Denied."
argument_list|)
throw|;
block|}
end_class

begin_decl_stmt
name|SessionEditForm
name|sessionEditForm
init|=
operator|(
name|SessionEditForm
operator|)
name|form
decl_stmt|;
end_decl_stmt

begin_decl_stmt
name|Long
name|id
init|=
operator|new
name|Long
argument_list|(
name|Long
operator|.
name|parseLong
argument_list|(
name|request
operator|.
name|getParameter
argument_list|(
literal|"sessionId"
argument_list|)
argument_list|)
argument_list|)
decl_stmt|;
end_decl_stmt

begin_decl_stmt
name|Session
name|acadSession
init|=
name|Session
operator|.
name|getSessionById
argument_list|(
name|id
argument_list|)
decl_stmt|;
end_decl_stmt

begin_expr_stmt
name|sessionEditForm
operator|.
name|setSession
argument_list|(
name|acadSession
argument_list|)
expr_stmt|;
end_expr_stmt

begin_decl_stmt
name|DatePattern
name|d
init|=
name|acadSession
operator|.
name|getDefaultDatePattern
argument_list|()
decl_stmt|;
end_decl_stmt

begin_if_stmt
if|if
condition|(
name|d
operator|!=
literal|null
condition|)
block|{
name|sessionEditForm
operator|.
name|setDefaultDatePatternId
argument_list|(
name|d
operator|.
name|getUniqueId
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
name|sessionEditForm
operator|.
name|setDefaultDatePatternLabel
argument_list|(
name|d
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|sessionEditForm
operator|.
name|setDefaultDatePatternId
argument_list|(
literal|""
argument_list|)
expr_stmt|;
name|sessionEditForm
operator|.
name|setDefaultDatePatternLabel
argument_list|(
literal|"Default date pattern not set"
argument_list|)
expr_stmt|;
block|}
end_if_stmt

begin_expr_stmt
name|sessionEditForm
operator|.
name|setAcademicInitiative
argument_list|(
name|acadSession
operator|.
name|getAcademicInitiative
argument_list|()
argument_list|)
expr_stmt|;
end_expr_stmt

begin_expr_stmt
name|sessionEditForm
operator|.
name|setAcademicYear
argument_list|(
name|acadSession
operator|.
name|getAcademicYear
argument_list|()
argument_list|)
expr_stmt|;
end_expr_stmt

begin_expr_stmt
name|sessionEditForm
operator|.
name|setAcademicTerm
argument_list|(
name|acadSession
operator|.
name|getAcademicTerm
argument_list|()
argument_list|)
expr_stmt|;
end_expr_stmt

begin_decl_stmt
name|SimpleDateFormat
name|sdf
init|=
operator|new
name|SimpleDateFormat
argument_list|(
literal|"MM/dd/yyyy"
argument_list|)
decl_stmt|;
end_decl_stmt

begin_expr_stmt
name|sessionEditForm
operator|.
name|setSessionStart
argument_list|(
name|sdf
operator|.
name|format
argument_list|(
name|acadSession
operator|.
name|getSessionBeginDateTime
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
end_expr_stmt

begin_expr_stmt
name|sessionEditForm
operator|.
name|setSessionEnd
argument_list|(
name|sdf
operator|.
name|format
argument_list|(
name|acadSession
operator|.
name|getSessionEndDateTime
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
end_expr_stmt

begin_expr_stmt
name|sessionEditForm
operator|.
name|setClassesEnd
argument_list|(
name|sdf
operator|.
name|format
argument_list|(
name|acadSession
operator|.
name|getClassesEndDateTime
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
end_expr_stmt

begin_expr_stmt
name|sessionEditForm
operator|.
name|setExamStart
argument_list|(
name|acadSession
operator|.
name|getExamBeginDate
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
name|sdf
operator|.
name|format
argument_list|(
name|acadSession
operator|.
name|getExamBeginDate
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
end_expr_stmt

begin_expr_stmt
name|sessionEditForm
operator|.
name|setEventStart
argument_list|(
name|acadSession
operator|.
name|getEventBeginDate
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
name|sdf
operator|.
name|format
argument_list|(
name|acadSession
operator|.
name|getEventBeginDate
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
end_expr_stmt

begin_expr_stmt
name|sessionEditForm
operator|.
name|setEventEnd
argument_list|(
name|acadSession
operator|.
name|getEventEndDate
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
name|sdf
operator|.
name|format
argument_list|(
name|acadSession
operator|.
name|getEventEndDate
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
end_expr_stmt

begin_for
for|for
control|(
name|RoomType
name|t
range|:
name|RoomType
operator|.
name|findAll
argument_list|()
control|)
block|{
name|RoomTypeOption
name|o
init|=
name|t
operator|.
name|getOption
argument_list|(
name|acadSession
argument_list|)
decl_stmt|;
name|sessionEditForm
operator|.
name|setRoomOptionScheduleEvents
argument_list|(
name|t
operator|.
name|getReference
argument_list|()
argument_list|,
name|o
operator|.
name|canScheduleEvents
argument_list|()
argument_list|)
expr_stmt|;
name|sessionEditForm
operator|.
name|setRoomOptionMessage
argument_list|(
name|t
operator|.
name|getReference
argument_list|()
argument_list|,
name|o
operator|.
name|getMessage
argument_list|()
argument_list|)
expr_stmt|;
block|}
end_for

begin_decl_stmt
name|Session
name|sessn
init|=
name|Session
operator|.
name|getSessionById
argument_list|(
name|id
argument_list|)
decl_stmt|;
end_decl_stmt

begin_expr_stmt
name|LookupTables
operator|.
name|setupDatePatterns
argument_list|(
name|request
argument_list|,
name|sessn
argument_list|,
literal|false
argument_list|,
name|Constants
operator|.
name|BLANK_OPTION_LABEL
argument_list|,
literal|null
argument_list|,
literal|null
argument_list|,
literal|null
argument_list|)
expr_stmt|;
end_expr_stmt

begin_expr_stmt
name|request
operator|.
name|setAttribute
argument_list|(
literal|"Sessions.holidays"
argument_list|,
name|sessionEditForm
operator|.
name|getSession
argument_list|()
operator|.
name|getHolidaysHtml
argument_list|()
argument_list|)
expr_stmt|;
end_expr_stmt

begin_return
return|return
name|mapping
operator|.
name|findForward
argument_list|(
literal|"showEdit"
argument_list|)
return|;
end_return

begin_function
unit|} 	 	public
name|ActionForward
name|deleteSession
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
comment|// Check access
if|if
condition|(
operator|!
name|Web
operator|.
name|hasRole
argument_list|(
name|request
operator|.
name|getSession
argument_list|()
argument_list|,
operator|new
name|String
index|[]
block|{
name|Roles
operator|.
name|ADMIN_ROLE
block|}
block_content|)
end_function

begin_block
unit|)
block|{
throw|throw
operator|new
name|Exception
argument_list|(
literal|"Access Denied."
argument_list|)
throw|;
block|}
end_block

begin_if_stmt
if|if
condition|(
name|Session
operator|.
name|getAllSessions
argument_list|()
operator|.
name|size
argument_list|()
operator|==
literal|1
condition|)
block|{
name|ActionMessages
name|errors
init|=
operator|new
name|ActionMessages
argument_list|()
decl_stmt|;
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
literal|"Last academic session cannot be deleted -- there needs to be at least one academic session present."
argument_list|)
argument_list|)
expr_stmt|;
name|saveErrors
argument_list|(
name|request
argument_list|,
name|errors
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
end_if_stmt

begin_decl_stmt
name|Session
name|current
init|=
name|Session
operator|.
name|getCurrentAcadSession
argument_list|(
name|Web
operator|.
name|getUser
argument_list|(
name|request
operator|.
name|getSession
argument_list|()
argument_list|)
argument_list|)
decl_stmt|;
end_decl_stmt

begin_decl_stmt
name|SessionEditForm
name|sessionEditForm
init|=
operator|(
name|SessionEditForm
operator|)
name|form
decl_stmt|;
end_decl_stmt

begin_decl_stmt
name|Long
name|id
init|=
operator|new
name|Long
argument_list|(
name|Long
operator|.
name|parseLong
argument_list|(
name|request
operator|.
name|getParameter
argument_list|(
literal|"sessionId"
argument_list|)
argument_list|)
argument_list|)
decl_stmt|;
end_decl_stmt

begin_if_stmt
if|if
condition|(
name|current
operator|!=
literal|null
operator|&&
name|id
operator|.
name|equals
argument_list|(
name|current
operator|.
name|getUniqueId
argument_list|()
argument_list|)
condition|)
block|{
name|ActionMessages
name|errors
init|=
operator|new
name|ActionMessages
argument_list|()
decl_stmt|;
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
literal|"Current academic session cannot be deleted -- please change your session first."
argument_list|)
argument_list|)
expr_stmt|;
name|saveErrors
argument_list|(
name|request
argument_list|,
name|errors
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
end_if_stmt

begin_decl_stmt
name|Session
name|sessn
init|=
name|Session
operator|.
name|getSessionById
argument_list|(
name|id
argument_list|)
decl_stmt|;
end_decl_stmt

begin_expr_stmt
name|Session
operator|.
name|deleteSessionById
argument_list|(
name|id
argument_list|)
expr_stmt|;
end_expr_stmt

begin_return
return|return
name|mapping
operator|.
name|findForward
argument_list|(
literal|"showSessionList"
argument_list|)
return|;
end_return

begin_function
unit|} 	 	public
name|ActionForward
name|addSession
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
comment|// Check access
if|if
condition|(
operator|!
name|Web
operator|.
name|hasRole
argument_list|(
name|request
operator|.
name|getSession
argument_list|()
argument_list|,
operator|new
name|String
index|[]
block|{
name|Roles
operator|.
name|ADMIN_ROLE
block|}
block_content|)
end_function

begin_block
unit|)
block|{
throw|throw
operator|new
name|Exception
argument_list|(
literal|"Access Denied."
argument_list|)
throw|;
block|}
end_block

begin_decl_stmt
name|SessionEditForm
name|sessionEditForm
init|=
operator|(
name|SessionEditForm
operator|)
name|form
decl_stmt|;
end_decl_stmt

begin_return
return|return
name|mapping
operator|.
name|findForward
argument_list|(
literal|"showAdd"
argument_list|)
return|;
end_return

begin_function
unit|} 	 	public
name|ActionForward
name|saveSession
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
comment|// Check access
if|if
condition|(
operator|!
name|Web
operator|.
name|hasRole
argument_list|(
name|request
operator|.
name|getSession
argument_list|()
argument_list|,
operator|new
name|String
index|[]
block|{
name|Roles
operator|.
name|ADMIN_ROLE
block|}
block_content|)
end_function

begin_block
unit|)
block|{
throw|throw
operator|new
name|Exception
argument_list|(
literal|"Access Denied."
argument_list|)
throw|;
block|}
end_block

begin_decl_stmt
name|Transaction
name|tx
init|=
literal|null
decl_stmt|;
end_decl_stmt

begin_decl_stmt
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
init|=
name|SessionDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|getSession
argument_list|()
decl_stmt|;
end_decl_stmt

begin_try
try|try
block|{
name|tx
operator|=
name|hibSession
operator|.
name|beginTransaction
argument_list|()
expr_stmt|;
name|SessionEditForm
name|sessionEditForm
init|=
operator|(
name|SessionEditForm
operator|)
name|form
decl_stmt|;
name|Session
name|sessn
init|=
name|sessionEditForm
operator|.
name|getSession
argument_list|()
decl_stmt|;
if|if
condition|(
name|sessionEditForm
operator|.
name|getSessionId
argument_list|()
operator|!=
literal|null
operator|&&
name|sessn
operator|.
name|getSessionId
argument_list|()
operator|.
name|intValue
argument_list|()
operator|!=
literal|0
condition|)
name|sessn
operator|=
operator|(
operator|new
name|SessionDAO
argument_list|()
operator|)
operator|.
name|get
argument_list|(
name|sessionEditForm
operator|.
name|getSessionId
argument_list|()
argument_list|,
name|hibSession
argument_list|)
expr_stmt|;
else|else
name|sessn
operator|.
name|setSessionId
argument_list|(
literal|null
argument_list|)
expr_stmt|;
name|String
name|refresh
init|=
name|request
operator|.
name|getParameter
argument_list|(
literal|"refresh"
argument_list|)
decl_stmt|;
if|if
condition|(
name|refresh
operator|!=
literal|null
operator|&&
name|refresh
operator|.
name|equals
argument_list|(
literal|"1"
argument_list|)
condition|)
block|{
name|ActionErrors
name|errors
init|=
operator|new
name|ActionErrors
argument_list|()
decl_stmt|;
name|setHolidays
argument_list|(
name|request
argument_list|,
name|sessionEditForm
argument_list|,
name|errors
argument_list|,
name|sessn
argument_list|)
expr_stmt|;
if|if
condition|(
name|sessn
operator|.
name|getSessionId
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|LookupTables
operator|.
name|setupDatePatterns
argument_list|(
name|request
argument_list|,
name|sessn
argument_list|,
literal|false
argument_list|,
name|Constants
operator|.
name|BLANK_OPTION_LABEL
argument_list|,
literal|null
argument_list|,
literal|null
argument_list|,
literal|null
argument_list|)
expr_stmt|;
name|request
operator|.
name|setAttribute
argument_list|(
literal|"Sessions.holidays"
argument_list|,
name|sessn
operator|.
name|getHolidaysHtml
argument_list|()
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
else|else
return|return
name|mapping
operator|.
name|findForward
argument_list|(
literal|"showAdd"
argument_list|)
return|;
block|}
name|ActionMessages
name|errors
init|=
name|sessionEditForm
operator|.
name|validate
argument_list|(
name|mapping
argument_list|,
name|request
argument_list|)
decl_stmt|;
if|if
condition|(
name|errors
operator|.
name|size
argument_list|()
operator|>
literal|0
condition|)
block|{
name|saveErrors
argument_list|(
name|request
argument_list|,
name|errors
argument_list|)
expr_stmt|;
if|if
condition|(
name|sessn
operator|.
name|getSessionId
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|LookupTables
operator|.
name|setupDatePatterns
argument_list|(
name|request
argument_list|,
name|sessn
argument_list|,
literal|false
argument_list|,
name|Constants
operator|.
name|BLANK_OPTION_LABEL
argument_list|,
literal|null
argument_list|,
literal|null
argument_list|,
literal|null
argument_list|)
expr_stmt|;
name|request
operator|.
name|setAttribute
argument_list|(
literal|"Sessions.holidays"
argument_list|,
name|sessn
operator|.
name|getHolidaysHtml
argument_list|()
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
else|else
block|{
name|ActionErrors
name|errors2
init|=
operator|new
name|ActionErrors
argument_list|()
decl_stmt|;
name|setHolidays
argument_list|(
name|request
argument_list|,
name|sessionEditForm
argument_list|,
name|errors2
argument_list|,
name|sessn
argument_list|)
expr_stmt|;
return|return
name|mapping
operator|.
name|findForward
argument_list|(
literal|"showAdd"
argument_list|)
return|;
block|}
block|}
if|if
condition|(
name|sessionEditForm
operator|.
name|getDefaultDatePatternId
argument_list|()
operator|!=
literal|null
operator|&&
name|sessionEditForm
operator|.
name|getDefaultDatePatternId
argument_list|()
operator|.
name|trim
argument_list|()
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
block|{
name|DatePattern
name|d
init|=
operator|new
name|DatePatternDAO
argument_list|()
operator|.
name|get
argument_list|(
operator|new
name|Long
argument_list|(
name|sessionEditForm
operator|.
name|getDefaultDatePatternId
argument_list|()
argument_list|)
argument_list|)
decl_stmt|;
name|sessn
operator|.
name|setDefaultDatePattern
argument_list|(
name|d
argument_list|)
expr_stmt|;
block|}
name|sessn
operator|.
name|setAcademicInitiative
argument_list|(
name|sessionEditForm
operator|.
name|getAcademicInitiative
argument_list|()
argument_list|)
expr_stmt|;
name|sessn
operator|.
name|setStatusType
argument_list|(
name|sessionEditForm
operator|.
name|getStatusType
argument_list|()
argument_list|)
expr_stmt|;
name|setSessionData
argument_list|(
name|request
argument_list|,
name|sessionEditForm
argument_list|,
name|sessn
argument_list|)
expr_stmt|;
name|hibSession
operator|.
name|saveOrUpdate
argument_list|(
name|sessn
argument_list|)
expr_stmt|;
for|for
control|(
name|RoomType
name|t
range|:
name|RoomType
operator|.
name|findAll
argument_list|()
control|)
block|{
name|RoomTypeOption
name|o
init|=
name|t
operator|.
name|getOption
argument_list|(
name|sessn
argument_list|)
decl_stmt|;
name|o
operator|.
name|setScheduleEvents
argument_list|(
name|sessionEditForm
operator|.
name|getRoomOptionScheduleEvents
argument_list|(
name|t
operator|.
name|getReference
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|o
operator|.
name|setMessage
argument_list|(
name|sessionEditForm
operator|.
name|getRoomOptionMessage
argument_list|(
name|t
operator|.
name|getReference
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|hibSession
operator|.
name|saveOrUpdate
argument_list|(
name|o
argument_list|)
expr_stmt|;
block|}
name|ChangeLog
operator|.
name|addChange
argument_list|(
name|hibSession
argument_list|,
name|request
argument_list|,
name|sessn
argument_list|,
name|ChangeLog
operator|.
name|Source
operator|.
name|SESSION_EDIT
argument_list|,
name|ChangeLog
operator|.
name|Operation
operator|.
name|UPDATE
argument_list|,
literal|null
argument_list|,
literal|null
argument_list|)
expr_stmt|;
name|tx
operator|.
name|commit
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
if|if
condition|(
name|tx
operator|!=
literal|null
condition|)
name|tx
operator|.
name|rollback
argument_list|()
expr_stmt|;
throw|throw
name|e
throw|;
block|}
end_try

begin_return
return|return
name|mapping
operator|.
name|findForward
argument_list|(
literal|"showSessionList"
argument_list|)
return|;
end_return

begin_comment
unit|}
comment|/** 	 *  	 */
end_comment

begin_function
unit|private
name|void
name|setHolidays
parameter_list|(
name|HttpServletRequest
name|request
parameter_list|,
name|SessionEditForm
name|sessionEditForm
parameter_list|,
name|ActionErrors
name|errors
parameter_list|,
name|Session
name|sessn
parameter_list|)
throws|throws
name|ParseException
block|{
name|sessionEditForm
operator|.
name|validateDates
argument_list|(
name|errors
argument_list|)
expr_stmt|;
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
name|setSessionData
argument_list|(
name|request
argument_list|,
name|sessionEditForm
argument_list|,
name|sessn
argument_list|)
expr_stmt|;
name|SimpleDateFormat
name|sdf
init|=
operator|new
name|SimpleDateFormat
argument_list|(
literal|"MM/dd/yyyy"
argument_list|)
decl_stmt|;
name|request
operator|.
name|setAttribute
argument_list|(
literal|"Sessions.holidays"
argument_list|,
name|sessn
operator|.
name|getHolidaysHtml
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
name|saveErrors
argument_list|(
name|request
argument_list|,
operator|new
name|ActionMessages
argument_list|(
name|errors
argument_list|)
argument_list|)
expr_stmt|;
block|}
end_function

begin_comment
comment|/** 	 *  	 */
end_comment

begin_function
specifier|private
name|void
name|setSessionData
parameter_list|(
name|HttpServletRequest
name|request
parameter_list|,
name|SessionEditForm
name|sessionEditForm
parameter_list|,
name|Session
name|sessn
parameter_list|)
throws|throws
name|ParseException
block|{
name|SimpleDateFormat
name|sdf
init|=
operator|new
name|SimpleDateFormat
argument_list|(
literal|"MM/dd/yyyy"
argument_list|)
decl_stmt|;
name|sessn
operator|.
name|setAcademicYear
argument_list|(
name|sessionEditForm
operator|.
name|getAcademicYear
argument_list|()
argument_list|)
expr_stmt|;
name|sessn
operator|.
name|setAcademicTerm
argument_list|(
name|sessionEditForm
operator|.
name|getAcademicTerm
argument_list|()
argument_list|)
expr_stmt|;
name|sessn
operator|.
name|setSessionBeginDateTime
argument_list|(
name|sdf
operator|.
name|parse
argument_list|(
name|sessionEditForm
operator|.
name|getSessionStart
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|sessn
operator|.
name|setSessionEndDateTime
argument_list|(
name|sdf
operator|.
name|parse
argument_list|(
name|sessionEditForm
operator|.
name|getSessionEnd
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|sessn
operator|.
name|setClassesEndDateTime
argument_list|(
name|sdf
operator|.
name|parse
argument_list|(
name|sessionEditForm
operator|.
name|getClassesEnd
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|sessn
operator|.
name|setExamBeginDate
argument_list|(
name|sdf
operator|.
name|parse
argument_list|(
name|sessionEditForm
operator|.
name|getExamStart
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|sessn
operator|.
name|setEventBeginDate
argument_list|(
name|sdf
operator|.
name|parse
argument_list|(
name|sessionEditForm
operator|.
name|getEventStart
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|sessn
operator|.
name|setEventEndDate
argument_list|(
name|sdf
operator|.
name|parse
argument_list|(
name|sessionEditForm
operator|.
name|getEventEnd
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|sessn
operator|.
name|setHolidays
argument_list|(
name|request
argument_list|)
expr_stmt|;
block|}
end_function

begin_function
specifier|public
name|ActionForward
name|cancelSessionEdit
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
block|{
return|return
name|mapping
operator|.
name|findForward
argument_list|(
literal|"showSessionList"
argument_list|)
return|;
block|}
end_function

unit|}
end_unit

