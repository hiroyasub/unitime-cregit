begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * Licensed to The Apereo Foundation under one or more contributor license  * agreements. See the NOTICE file distributed with this work for  * additional information regarding copyright ownership.  *  * The Apereo Foundation licenses this file to you under the Apache License,  * Version 2.0 (the "License"); you may not use this file except in  * compliance with the License. You may obtain a copy of the License at:  *  * http://www.apache.org/licenses/LICENSE-2.0  *  * Unless required by applicable law or agreed to in writing, software  * distributed under the License is distributed on an "AS IS" BASIS,  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  *  * See the License for the specific language governing permissions and  * limitations under the License.  *  */
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
name|util
operator|.
name|MessageResources
import|;
end_import

begin_import
import|import
name|org
operator|.
name|springframework
operator|.
name|beans
operator|.
name|factory
operator|.
name|annotation
operator|.
name|Autowired
import|;
end_import

begin_import
import|import
name|org
operator|.
name|springframework
operator|.
name|security
operator|.
name|core
operator|.
name|Authentication
import|;
end_import

begin_import
import|import
name|org
operator|.
name|springframework
operator|.
name|security
operator|.
name|core
operator|.
name|GrantedAuthority
import|;
end_import

begin_import
import|import
name|org
operator|.
name|springframework
operator|.
name|security
operator|.
name|core
operator|.
name|context
operator|.
name|SecurityContextHolder
import|;
end_import

begin_import
import|import
name|org
operator|.
name|springframework
operator|.
name|stereotype
operator|.
name|Service
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
name|timetable
operator|.
name|defaults
operator|.
name|ApplicationProperty
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
name|defaults
operator|.
name|SessionAttribute
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
name|ChameleonForm
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
name|security
operator|.
name|SessionContext
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
name|security
operator|.
name|UserContext
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
name|security
operator|.
name|context
operator|.
name|ChameleonUserContext
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
name|security
operator|.
name|rights
operator|.
name|Right
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
comment|/**   * MyEclipse Struts  * Creation date: 10-23-2006  *   * XDoclet definition:  * @struts:action path="/chameleon" name="chameleonForm" input="/admin/chameleon.jsp" scope="request"  *  * @author Tomas Muller  */
end_comment

begin_class
annotation|@
name|Service
argument_list|(
literal|"/chameleon"
argument_list|)
specifier|public
class|class
name|ChameleonAction
extends|extends
name|Action
block|{
comment|// --------------------------------------------------------- Instance Variables
comment|// --------------------------------------------------------- Methods
annotation|@
name|Autowired
name|SessionContext
name|sessionContext
decl_stmt|;
comment|/**       * Method execute      * @param mapping      * @param form      * @param request      * @param response      * @return ActionForward      */
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
name|UserContext
name|user
init|=
name|sessionContext
operator|.
name|getUser
argument_list|()
decl_stmt|;
if|if
condition|(
name|user
operator|!=
literal|null
operator|&&
name|user
operator|instanceof
name|UserContext
operator|.
name|Chameleon
condition|)
name|user
operator|=
operator|(
operator|(
name|UserContext
operator|.
name|Chameleon
operator|)
name|user
operator|)
operator|.
name|getOriginalUserContext
argument_list|()
expr_stmt|;
else|else
name|sessionContext
operator|.
name|checkPermission
argument_list|(
name|Right
operator|.
name|Chameleon
argument_list|)
expr_stmt|;
name|MessageResources
name|rsc
init|=
name|getResources
argument_list|(
name|request
argument_list|)
decl_stmt|;
name|ChameleonForm
name|frm
init|=
operator|(
name|ChameleonForm
operator|)
name|form
decl_stmt|;
name|frm
operator|.
name|setCanLookup
argument_list|(
name|sessionContext
operator|.
name|hasPermission
argument_list|(
name|Right
operator|.
name|HasRole
argument_list|)
argument_list|)
expr_stmt|;
name|ActionMessages
name|errors
init|=
operator|new
name|ActionMessages
argument_list|()
decl_stmt|;
name|String
name|op
init|=
operator|(
name|request
operator|.
name|getParameter
argument_list|(
literal|"op"
argument_list|)
operator|==
literal|null
operator|)
condition|?
operator|(
name|frm
operator|.
name|getOp
argument_list|()
operator|==
literal|null
operator|||
name|frm
operator|.
name|getOp
argument_list|()
operator|.
name|length
argument_list|()
operator|==
literal|0
operator|)
condition|?
operator|(
name|request
operator|.
name|getAttribute
argument_list|(
literal|"op"
argument_list|)
operator|==
literal|null
operator|)
condition|?
literal|null
else|:
name|request
operator|.
name|getAttribute
argument_list|(
literal|"op"
argument_list|)
operator|.
name|toString
argument_list|()
else|:
name|frm
operator|.
name|getOp
argument_list|()
else|:
name|request
operator|.
name|getParameter
argument_list|(
literal|"op"
argument_list|)
decl_stmt|;
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
condition|)
name|op
operator|=
name|rsc
operator|.
name|getMessage
argument_list|(
literal|"op.view"
argument_list|)
expr_stmt|;
name|frm
operator|.
name|setOp
argument_list|(
name|op
argument_list|)
expr_stmt|;
comment|// Lookup
name|String
name|uid
init|=
name|request
operator|.
name|getParameter
argument_list|(
literal|"uid"
argument_list|)
decl_stmt|;
if|if
condition|(
name|uid
operator|!=
literal|null
operator|&&
operator|!
name|uid
operator|.
name|isEmpty
argument_list|()
operator|&&
name|ApplicationProperty
operator|.
name|ChameleonAllowLookup
operator|.
name|isTrue
argument_list|()
condition|)
block|{
name|frm
operator|.
name|setPuid
argument_list|(
name|uid
argument_list|)
expr_stmt|;
name|frm
operator|.
name|setName
argument_list|(
name|request
operator|.
name|getParameter
argument_list|(
literal|"uname"
argument_list|)
argument_list|)
expr_stmt|;
name|op
operator|=
name|rsc
operator|.
name|getMessage
argument_list|(
literal|"button.changeUser"
argument_list|)
expr_stmt|;
block|}
comment|// First Access - display blank form
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
literal|"op.view"
argument_list|)
argument_list|)
condition|)
block|{
name|LookupTables
operator|.
name|setupTimetableManagers
argument_list|(
name|request
argument_list|)
expr_stmt|;
if|if
condition|(
name|user
operator|!=
literal|null
condition|)
name|frm
operator|.
name|setPuid
argument_list|(
name|user
operator|.
name|getExternalUserId
argument_list|()
argument_list|)
expr_stmt|;
block|}
comment|// Change User
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
literal|"button.changeUser"
argument_list|)
argument_list|)
condition|)
block|{
try|try
block|{
name|doSwitch
argument_list|(
name|request
argument_list|,
name|frm
argument_list|,
name|user
argument_list|)
expr_stmt|;
return|return
name|mapping
operator|.
name|findForward
argument_list|(
literal|"reload"
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
name|errors
operator|.
name|add
argument_list|(
literal|"exception"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.generic"
argument_list|,
name|e
operator|.
name|getMessage
argument_list|()
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
name|LookupTables
operator|.
name|setupTimetableManagers
argument_list|(
name|request
argument_list|)
expr_stmt|;
return|return
name|mapping
operator|.
name|findForward
argument_list|(
literal|"displayForm"
argument_list|)
return|;
block|}
block|}
return|return
name|mapping
operator|.
name|findForward
argument_list|(
literal|"displayForm"
argument_list|)
return|;
block|}
comment|/**      * Reads in new user attributes and reloads Timetabling for the new user      * @param request      * @param frm      * @param u      */
specifier|private
name|void
name|doSwitch
parameter_list|(
name|HttpServletRequest
name|request
parameter_list|,
name|ChameleonForm
name|frm
parameter_list|,
name|UserContext
name|user
parameter_list|)
throws|throws
name|Exception
block|{
for|for
control|(
name|SessionAttribute
name|a
range|:
name|SessionAttribute
operator|.
name|values
argument_list|()
control|)
name|request
operator|.
name|getSession
argument_list|()
operator|.
name|removeAttribute
argument_list|(
name|a
operator|.
name|key
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|user
operator|instanceof
name|UserContext
operator|.
name|Chameleon
condition|)
name|user
operator|=
operator|(
operator|(
name|UserContext
operator|.
name|Chameleon
operator|)
name|user
operator|)
operator|.
name|getOriginalUserContext
argument_list|()
expr_stmt|;
name|Authentication
name|authentication
init|=
name|SecurityContextHolder
operator|.
name|getContext
argument_list|()
operator|.
name|getAuthentication
argument_list|()
decl_stmt|;
if|if
condition|(
name|authentication
operator|instanceof
name|ChameleonAuthentication
condition|)
name|authentication
operator|=
operator|(
operator|(
name|ChameleonAuthentication
operator|)
name|authentication
operator|)
operator|.
name|getOriginalAuthentication
argument_list|()
expr_stmt|;
if|if
condition|(
name|user
operator|.
name|getExternalUserId
argument_list|()
operator|.
name|equals
argument_list|(
name|frm
operator|.
name|getPuid
argument_list|()
argument_list|)
condition|)
block|{
name|SecurityContextHolder
operator|.
name|getContext
argument_list|()
operator|.
name|setAuthentication
argument_list|(
name|authentication
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|SecurityContextHolder
operator|.
name|getContext
argument_list|()
operator|.
name|setAuthentication
argument_list|(
operator|new
name|ChameleonAuthentication
argument_list|(
name|authentication
argument_list|,
operator|new
name|ChameleonUserContext
argument_list|(
name|frm
operator|.
name|getPuid
argument_list|()
argument_list|,
name|frm
operator|.
name|getName
argument_list|()
argument_list|,
name|user
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
specifier|public
specifier|static
class|class
name|ChameleonAuthentication
implements|implements
name|Authentication
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|1L
decl_stmt|;
specifier|private
name|Authentication
name|iOriginalAuthentication
decl_stmt|;
specifier|private
name|UserContext
name|iUserContext
decl_stmt|;
specifier|public
name|ChameleonAuthentication
parameter_list|(
name|Authentication
name|authentication
parameter_list|,
name|UserContext
name|user
parameter_list|)
block|{
name|iOriginalAuthentication
operator|=
name|authentication
expr_stmt|;
name|iUserContext
operator|=
name|user
expr_stmt|;
if|if
condition|(
name|iOriginalAuthentication
operator|instanceof
name|ChameleonAuthentication
condition|)
name|iOriginalAuthentication
operator|=
operator|(
operator|(
name|ChameleonAuthentication
operator|)
name|iOriginalAuthentication
operator|)
operator|.
name|getOriginalAuthentication
argument_list|()
expr_stmt|;
block|}
specifier|public
name|Authentication
name|getOriginalAuthentication
parameter_list|()
block|{
return|return
name|iOriginalAuthentication
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
name|iUserContext
operator|.
name|getName
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|Collection
argument_list|<
name|?
extends|extends
name|GrantedAuthority
argument_list|>
name|getAuthorities
parameter_list|()
block|{
return|return
name|iUserContext
operator|.
name|getAuthorities
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|Object
name|getCredentials
parameter_list|()
block|{
return|return
name|iOriginalAuthentication
operator|.
name|getCredentials
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|Object
name|getDetails
parameter_list|()
block|{
return|return
name|iOriginalAuthentication
operator|.
name|getDetails
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|Object
name|getPrincipal
parameter_list|()
block|{
return|return
name|iUserContext
return|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|isAuthenticated
parameter_list|()
block|{
return|return
name|iOriginalAuthentication
operator|.
name|isAuthenticated
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|setAuthenticated
parameter_list|(
name|boolean
name|isAuthenticated
parameter_list|)
throws|throws
name|IllegalArgumentException
block|{
name|iOriginalAuthentication
operator|.
name|setAuthenticated
argument_list|(
name|isAuthenticated
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

