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
name|ArrayList
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
name|TreeSet
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
name|Action
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
name|RollForwardSessionForm
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
name|SessionRollForward
import|;
end_import

begin_comment
comment|/**   * MyEclipse Struts  * Creation date: 02-27-2007  *   * XDoclet definition:  * @struts.action path="/exportSessionToMsf" name="exportSessionToMsfForm" input="/form/exportSessionToMsf.jsp" scope="request" validate="true"  */
end_comment

begin_class
specifier|public
class|class
name|RollForwardSessionAction
extends|extends
name|Action
block|{
comment|/* 	 * Generated Methods 	 */
comment|/**  	 * Method execute 	 * @param mapping 	 * @param form 	 * @param request 	 * @param response 	 * @return ActionForward 	 * @throws Exception  	 */
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
name|HttpSession
name|webSession
init|=
name|request
operator|.
name|getSession
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
name|Web
operator|.
name|isLoggedIn
argument_list|(
name|webSession
argument_list|)
condition|)
block|{
throw|throw
operator|new
name|Exception
argument_list|(
literal|"Access Denied."
argument_list|)
throw|;
block|}
name|MessageResources
name|rsc
init|=
name|getResources
argument_list|(
name|request
argument_list|)
decl_stmt|;
name|RollForwardSessionForm
name|rollForwardSessionForm
init|=
operator|(
name|RollForwardSessionForm
operator|)
name|form
decl_stmt|;
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
name|request
operator|.
name|getSession
argument_list|()
argument_list|)
decl_stmt|;
comment|// Get operation
name|String
name|op
init|=
name|request
operator|.
name|getParameter
argument_list|(
literal|"op"
argument_list|)
decl_stmt|;
name|SessionRollForward
name|sessionRollForward
init|=
operator|new
name|SessionRollForward
argument_list|()
decl_stmt|;
if|if
condition|(
name|op
operator|!=
literal|null
operator|&&
name|op
operator|.
name|equals
argument_list|(
name|rsc
operator|.
name|getMessage
argument_list|(
literal|"button.rollForward"
argument_list|)
argument_list|)
condition|)
block|{
name|ActionMessages
name|errors
init|=
name|rollForwardSessionForm
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
operator|==
literal|0
operator|&&
name|rollForwardSessionForm
operator|.
name|getRollForwardDatePatterns
argument_list|()
operator|.
name|booleanValue
argument_list|()
condition|)
block|{
name|sessionRollForward
operator|.
name|rollDatePatternsForward
argument_list|(
name|errors
argument_list|,
name|rollForwardSessionForm
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|errors
operator|.
name|size
argument_list|()
operator|==
literal|0
operator|&&
name|rollForwardSessionForm
operator|.
name|getRollForwardTimePatterns
argument_list|()
operator|.
name|booleanValue
argument_list|()
condition|)
block|{
name|sessionRollForward
operator|.
name|rollTimePatternsForward
argument_list|(
name|errors
argument_list|,
name|rollForwardSessionForm
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|errors
operator|.
name|size
argument_list|()
operator|==
literal|0
operator|&&
name|rollForwardSessionForm
operator|.
name|getRollForwardDepartments
argument_list|()
operator|.
name|booleanValue
argument_list|()
condition|)
block|{
name|sessionRollForward
operator|.
name|rollDepartmentsForward
argument_list|(
name|errors
argument_list|,
name|rollForwardSessionForm
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|errors
operator|.
name|size
argument_list|()
operator|==
literal|0
operator|&&
name|rollForwardSessionForm
operator|.
name|getRollForwardManagers
argument_list|()
operator|.
name|booleanValue
argument_list|()
condition|)
block|{
name|sessionRollForward
operator|.
name|rollManagersForward
argument_list|(
name|errors
argument_list|,
name|rollForwardSessionForm
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|errors
operator|.
name|size
argument_list|()
operator|==
literal|0
operator|&&
name|rollForwardSessionForm
operator|.
name|getRollForwardRoomData
argument_list|()
operator|.
name|booleanValue
argument_list|()
condition|)
block|{
name|sessionRollForward
operator|.
name|rollBuildingAndRoomDataForward
argument_list|(
name|errors
argument_list|,
name|rollForwardSessionForm
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|errors
operator|.
name|size
argument_list|()
operator|==
literal|0
operator|&&
name|rollForwardSessionForm
operator|.
name|getRollForwardSubjectAreas
argument_list|()
operator|.
name|booleanValue
argument_list|()
condition|)
block|{
name|sessionRollForward
operator|.
name|rollSubjectAreasForward
argument_list|(
name|errors
argument_list|,
name|rollForwardSessionForm
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|errors
operator|.
name|size
argument_list|()
operator|==
literal|0
operator|&&
name|rollForwardSessionForm
operator|.
name|getRollForwardInstructorData
argument_list|()
operator|.
name|booleanValue
argument_list|()
condition|)
block|{
name|sessionRollForward
operator|.
name|rollInstructorDataForward
argument_list|(
name|errors
argument_list|,
name|rollForwardSessionForm
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|errors
operator|.
name|size
argument_list|()
operator|==
literal|0
operator|&&
name|rollForwardSessionForm
operator|.
name|getRollForwardCourseOfferings
argument_list|()
operator|.
name|booleanValue
argument_list|()
condition|)
block|{
name|sessionRollForward
operator|.
name|rollCourseOfferingsForward
argument_list|(
name|errors
argument_list|,
name|rollForwardSessionForm
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|errors
operator|.
name|size
argument_list|()
operator|==
literal|0
operator|&&
name|rollForwardSessionForm
operator|.
name|getRollForwardClassInstructors
argument_list|()
operator|.
name|booleanValue
argument_list|()
condition|)
block|{
name|sessionRollForward
operator|.
name|rollClassInstructorsForward
argument_list|(
name|errors
argument_list|,
name|rollForwardSessionForm
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|errors
operator|.
name|size
argument_list|()
operator|==
literal|0
operator|&&
name|rollForwardSessionForm
operator|.
name|getAddNewCourseOfferings
argument_list|()
operator|.
name|booleanValue
argument_list|()
condition|)
block|{
name|sessionRollForward
operator|.
name|addNewCourseOfferings
argument_list|(
name|errors
argument_list|,
name|rollForwardSessionForm
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|errors
operator|.
name|size
argument_list|()
operator|==
literal|0
operator|&&
name|rollForwardSessionForm
operator|.
name|getRollForwardExamConfiguration
argument_list|()
operator|.
name|booleanValue
argument_list|()
condition|)
block|{
name|sessionRollForward
operator|.
name|rollExamConfigurationDataForward
argument_list|(
name|errors
argument_list|,
name|rollForwardSessionForm
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|errors
operator|.
name|size
argument_list|()
operator|==
literal|0
operator|&&
name|rollForwardSessionForm
operator|.
name|getRollForwardMidtermExams
argument_list|()
operator|.
name|booleanValue
argument_list|()
condition|)
block|{
name|sessionRollForward
operator|.
name|rollMidtermExamsForward
argument_list|(
name|errors
argument_list|,
name|rollForwardSessionForm
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|errors
operator|.
name|size
argument_list|()
operator|==
literal|0
operator|&&
name|rollForwardSessionForm
operator|.
name|getRollForwardFinalExams
argument_list|()
operator|.
name|booleanValue
argument_list|()
condition|)
block|{
name|sessionRollForward
operator|.
name|rollFinalExamsForward
argument_list|(
name|errors
argument_list|,
name|rollForwardSessionForm
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|errors
operator|.
name|isEmpty
argument_list|()
operator|&&
name|rollForwardSessionForm
operator|.
name|getRollForwardStudents
argument_list|()
operator|.
name|booleanValue
argument_list|()
condition|)
block|{
name|sessionRollForward
operator|.
name|rollStudentsForward
argument_list|(
name|errors
argument_list|,
name|rollForwardSessionForm
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|errors
operator|.
name|size
argument_list|()
operator|!=
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
block|}
block|}
name|rollForwardSessionForm
operator|.
name|setAdmin
argument_list|(
name|user
operator|.
name|isAdmin
argument_list|()
argument_list|)
expr_stmt|;
name|setToFromSessionsInForm
argument_list|(
name|rollForwardSessionForm
argument_list|)
expr_stmt|;
name|rollForwardSessionForm
operator|.
name|setSubjectAreas
argument_list|(
name|getSubjectAreas
argument_list|(
name|rollForwardSessionForm
operator|.
name|getSessionToRollForwardTo
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|mapping
operator|.
name|findForward
argument_list|(
literal|"displayRollForwardSessionForm"
argument_list|)
return|;
block|}
specifier|protected
name|void
name|setToFromSessionsInForm
parameter_list|(
name|RollForwardSessionForm
name|rollForwardSessionForm
parameter_list|)
block|{
name|List
name|sessionList
init|=
operator|new
name|ArrayList
argument_list|()
decl_stmt|;
name|sessionList
operator|.
name|addAll
argument_list|(
name|Session
operator|.
name|getAllSessions
argument_list|()
argument_list|)
expr_stmt|;
name|rollForwardSessionForm
operator|.
name|setFromSessions
argument_list|(
operator|new
name|ArrayList
argument_list|()
argument_list|)
expr_stmt|;
name|rollForwardSessionForm
operator|.
name|setToSessions
argument_list|(
operator|new
name|ArrayList
argument_list|()
argument_list|)
expr_stmt|;
name|DepartmentStatusType
name|statusType
init|=
name|DepartmentStatusType
operator|.
name|findByRef
argument_list|(
literal|"initial"
argument_list|)
decl_stmt|;
name|Session
name|session
init|=
literal|null
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
operator|(
name|sessionList
operator|.
name|size
argument_list|()
operator|-
literal|1
operator|)
init|;
name|i
operator|>=
literal|0
condition|;
name|i
operator|--
control|)
block|{
name|session
operator|=
operator|(
name|Session
operator|)
name|sessionList
operator|.
name|get
argument_list|(
name|i
argument_list|)
expr_stmt|;
if|if
condition|(
name|session
operator|.
name|getStatusType
argument_list|()
operator|.
name|getUniqueId
argument_list|()
operator|.
name|equals
argument_list|(
name|statusType
operator|.
name|getUniqueId
argument_list|()
argument_list|)
condition|)
block|{
name|rollForwardSessionForm
operator|.
name|getToSessions
argument_list|()
operator|.
name|add
argument_list|(
name|session
argument_list|)
expr_stmt|;
if|if
condition|(
name|rollForwardSessionForm
operator|.
name|getSessionToRollForwardTo
argument_list|()
operator|==
literal|null
condition|)
block|{
name|rollForwardSessionForm
operator|.
name|setSessionToRollForwardTo
argument_list|(
name|session
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
name|rollForwardSessionForm
operator|.
name|getFromSessions
argument_list|()
operator|.
name|add
argument_list|(
name|session
argument_list|)
expr_stmt|;
block|}
block|}
block|}
specifier|protected
name|Set
name|getSubjectAreas
parameter_list|(
name|Long
name|selectedSessionId
parameter_list|)
block|{
name|Set
name|subjects
init|=
operator|new
name|TreeSet
argument_list|()
decl_stmt|;
name|Session
name|session
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|selectedSessionId
operator|==
literal|null
condition|)
block|{
name|DepartmentStatusType
name|statusType
init|=
name|DepartmentStatusType
operator|.
name|findByRef
argument_list|(
literal|"initial"
argument_list|)
decl_stmt|;
name|boolean
name|found
init|=
literal|false
decl_stmt|;
name|TreeSet
name|allSessions
init|=
name|Session
operator|.
name|getAllSessions
argument_list|()
decl_stmt|;
name|List
name|sessionList
init|=
operator|new
name|ArrayList
argument_list|()
decl_stmt|;
name|sessionList
operator|.
name|addAll
argument_list|(
name|Session
operator|.
name|getAllSessions
argument_list|()
argument_list|)
expr_stmt|;
for|for
control|(
name|int
name|i
init|=
operator|(
name|sessionList
operator|.
name|size
argument_list|()
operator|-
literal|1
operator|)
init|;
name|i
operator|>=
literal|0
condition|;
name|i
operator|--
control|)
block|{
name|session
operator|=
operator|(
name|Session
operator|)
name|sessionList
operator|.
name|get
argument_list|(
name|i
argument_list|)
expr_stmt|;
if|if
condition|(
name|session
operator|.
name|getStatusType
argument_list|()
operator|.
name|getUniqueId
argument_list|()
operator|.
name|equals
argument_list|(
name|statusType
operator|.
name|getUniqueId
argument_list|()
argument_list|)
condition|)
block|{
name|found
operator|=
literal|true
expr_stmt|;
block|}
block|}
if|if
condition|(
operator|!
name|found
condition|)
block|{
name|session
operator|=
literal|null
expr_stmt|;
if|if
condition|(
name|allSessions
operator|.
name|size
argument_list|()
operator|>
literal|0
condition|)
block|{
name|session
operator|=
operator|(
name|Session
operator|)
name|allSessions
operator|.
name|last
argument_list|()
expr_stmt|;
block|}
block|}
block|}
else|else
block|{
name|session
operator|=
name|Session
operator|.
name|getSessionById
argument_list|(
name|selectedSessionId
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|session
operator|!=
literal|null
condition|)
name|subjects
operator|=
name|session
operator|.
name|getSubjectAreas
argument_list|()
expr_stmt|;
return|return
operator|(
name|subjects
operator|)
return|;
block|}
block|}
end_class

end_unit

