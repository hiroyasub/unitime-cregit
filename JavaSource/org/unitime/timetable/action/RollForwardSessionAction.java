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
name|rollForwardSessionForm
operator|.
name|setSubjectAreas
argument_list|(
name|Session
operator|.
name|getCurrentAcadSession
argument_list|(
name|user
argument_list|)
operator|.
name|getSubjectAreas
argument_list|()
argument_list|)
expr_stmt|;
name|rollForwardSessionForm
operator|.
name|setSessions
argument_list|(
name|Session
operator|.
name|getAllSessions
argument_list|()
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
block|}
end_class

end_unit

