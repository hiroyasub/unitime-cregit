begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.2 - 3.5 (University Timetabling Application)  * Copyright (C) 2008 - 2013, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
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
name|HashMap
import|;
end_import

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
name|List
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
name|ApplicationProperties
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
name|ApplicationConfigForm
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
name|ApplicationConfig
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
name|SessionConfig
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
name|ApplicationConfigDAO
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
name|SessionConfigDAO
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
name|rights
operator|.
name|Right
import|;
end_import

begin_comment
comment|/**   * MyEclipse Struts  * Creation date: 08-28-2006  *   * XDoclet definition:  * @struts:action path="/applicationConfig" name="applicationConfigForm" input="/admin/applicationConfig.jsp" scope="request"  *  * @author Tomas Muller  */
end_comment

begin_class
annotation|@
name|Service
argument_list|(
literal|"/applicationConfig"
argument_list|)
specifier|public
class|class
name|ApplicationConfigAction
extends|extends
name|Action
block|{
comment|// --------------------------------------------------------- Instance Variables
annotation|@
name|Autowired
name|SessionContext
name|sessionContext
decl_stmt|;
comment|// --------------------------------------------------------- Methods
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
name|sessionContext
operator|.
name|checkPermission
argument_list|(
name|Right
operator|.
name|ApplicationConfig
argument_list|)
expr_stmt|;
name|ApplicationConfigForm
name|frm
init|=
operator|(
name|ApplicationConfigForm
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
name|String
name|op
init|=
operator|(
name|frm
operator|.
name|getOp
argument_list|()
operator|!=
literal|null
condition|?
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
operator|)
decl_stmt|;
if|if
condition|(
name|op
operator|==
literal|null
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
name|op
operator|=
name|frm
operator|.
name|getOp
argument_list|()
expr_stmt|;
block|}
name|ActionMessages
name|errors
init|=
operator|new
name|ActionMessages
argument_list|()
decl_stmt|;
comment|// Edit Config - Load existing config values to be edited
if|if
condition|(
name|op
operator|.
name|equals
argument_list|(
literal|"edit"
argument_list|)
condition|)
block|{
name|sessionContext
operator|.
name|checkPermission
argument_list|(
name|Right
operator|.
name|ApplicationConfigEdit
argument_list|)
expr_stmt|;
name|String
name|id
init|=
name|request
operator|.
name|getParameter
argument_list|(
literal|"id"
argument_list|)
decl_stmt|;
if|if
condition|(
name|id
operator|==
literal|null
operator|||
name|id
operator|.
name|trim
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|errors
operator|.
name|add
argument_list|(
literal|"key"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.invalid"
argument_list|,
literal|"Name : "
operator|+
name|id
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
block|}
else|else
block|{
name|SessionConfig
name|sessionConfig
init|=
name|SessionConfig
operator|.
name|getConfig
argument_list|(
name|id
argument_list|,
name|sessionContext
operator|.
name|getUser
argument_list|()
operator|.
name|getCurrentAcademicSessionId
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|sessionConfig
operator|==
literal|null
condition|)
block|{
name|ApplicationConfig
name|appConfig
init|=
name|ApplicationConfigDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|get
argument_list|(
name|id
argument_list|)
decl_stmt|;
if|if
condition|(
name|appConfig
operator|==
literal|null
condition|)
block|{
name|errors
operator|.
name|add
argument_list|(
literal|"key"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.invalid"
argument_list|,
literal|"Name : "
operator|+
name|id
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
block|}
else|else
block|{
name|frm
operator|.
name|setKey
argument_list|(
name|appConfig
operator|.
name|getKey
argument_list|()
argument_list|)
expr_stmt|;
name|frm
operator|.
name|setValue
argument_list|(
name|appConfig
operator|.
name|getValue
argument_list|()
argument_list|)
expr_stmt|;
name|frm
operator|.
name|setDescription
argument_list|(
name|appConfig
operator|.
name|getDescription
argument_list|()
argument_list|)
expr_stmt|;
name|frm
operator|.
name|setAllSessions
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
name|frm
operator|.
name|setKey
argument_list|(
name|sessionConfig
operator|.
name|getKey
argument_list|()
argument_list|)
expr_stmt|;
name|frm
operator|.
name|setValue
argument_list|(
name|sessionConfig
operator|.
name|getValue
argument_list|()
argument_list|)
expr_stmt|;
name|frm
operator|.
name|setDescription
argument_list|(
name|sessionConfig
operator|.
name|getDescription
argument_list|()
argument_list|)
expr_stmt|;
name|frm
operator|.
name|setAllSessions
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|List
argument_list|<
name|Long
argument_list|>
name|sessionIds
init|=
name|SessionConfigDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select session.uniqueId from SessionConfig where key = :key and value = :value"
argument_list|)
operator|.
name|setString
argument_list|(
literal|"key"
argument_list|,
name|id
argument_list|)
operator|.
name|setString
argument_list|(
literal|"value"
argument_list|,
name|sessionConfig
operator|.
name|getValue
argument_list|()
argument_list|)
operator|.
name|list
argument_list|()
decl_stmt|;
name|Long
index|[]
name|sessionIdsArry
init|=
operator|new
name|Long
index|[
name|sessionIds
operator|.
name|size
argument_list|()
index|]
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
name|sessionIds
operator|.
name|size
argument_list|()
condition|;
name|i
operator|++
control|)
name|sessionIdsArry
index|[
name|i
index|]
operator|=
name|sessionIds
operator|.
name|get
argument_list|(
name|i
argument_list|)
expr_stmt|;
name|frm
operator|.
name|setSessions
argument_list|(
name|sessionIdsArry
argument_list|)
expr_stmt|;
if|if
condition|(
name|sessionConfig
operator|.
name|getDescription
argument_list|()
operator|==
literal|null
operator|||
name|sessionConfig
operator|.
name|getDescription
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|ApplicationConfig
name|appConfig
init|=
name|ApplicationConfigDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|get
argument_list|(
name|id
argument_list|)
decl_stmt|;
if|if
condition|(
name|appConfig
operator|!=
literal|null
condition|)
name|frm
operator|.
name|setDescription
argument_list|(
name|appConfig
operator|.
name|getDescription
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
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
literal|"button.addAppConfig"
argument_list|)
argument_list|)
condition|)
block|{
name|sessionContext
operator|.
name|checkPermission
argument_list|(
name|Right
operator|.
name|ApplicationConfigEdit
argument_list|)
expr_stmt|;
name|frm
operator|.
name|reset
argument_list|(
name|mapping
argument_list|,
name|request
argument_list|)
expr_stmt|;
name|frm
operator|.
name|setAllSessions
argument_list|(
literal|true
argument_list|)
expr_stmt|;
if|if
condition|(
name|sessionContext
operator|.
name|getUser
argument_list|()
operator|.
name|getCurrentAcademicSessionId
argument_list|()
operator|!=
literal|null
condition|)
name|frm
operator|.
name|setSessions
argument_list|(
operator|new
name|Long
index|[]
block|{
name|sessionContext
operator|.
name|getUser
argument_list|()
operator|.
name|getCurrentAcademicSessionId
argument_list|()
block|}
argument_list|)
expr_stmt|;
name|frm
operator|.
name|setOp
argument_list|(
name|rsc
operator|.
name|getMessage
argument_list|(
literal|"button.createAppConfig"
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|// Save or update config
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
literal|"button.updateAppConfig"
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
literal|"button.createAppConfig"
argument_list|)
argument_list|)
condition|)
block|{
name|sessionContext
operator|.
name|checkPermission
argument_list|(
name|Right
operator|.
name|ApplicationConfigEdit
argument_list|)
expr_stmt|;
comment|// Validate input
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
if|if
condition|(
operator|!
name|errors
operator|.
name|isEmpty
argument_list|()
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
else|else
block|{
try|try
block|{
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
init|=
name|SessionConfigDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|getSession
argument_list|()
decl_stmt|;
name|boolean
name|update
init|=
name|op
operator|.
name|equals
argument_list|(
name|rsc
operator|.
name|getMessage
argument_list|(
literal|"button.updateAppConfig"
argument_list|)
argument_list|)
decl_stmt|;
name|String
name|oldValue
init|=
literal|null
decl_stmt|;
name|boolean
name|wasSession
init|=
literal|false
decl_stmt|;
name|SessionConfig
name|sessionConfig
init|=
name|SessionConfig
operator|.
name|getConfig
argument_list|(
name|frm
operator|.
name|getKey
argument_list|()
argument_list|,
name|sessionContext
operator|.
name|getUser
argument_list|()
operator|.
name|getCurrentAcademicSessionId
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|sessionConfig
operator|==
literal|null
condition|)
block|{
name|ApplicationConfig
name|appConfig
init|=
name|ApplicationConfigDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|get
argument_list|(
name|frm
operator|.
name|getKey
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|appConfig
operator|!=
literal|null
condition|)
name|oldValue
operator|=
name|appConfig
operator|.
name|getValue
argument_list|()
expr_stmt|;
block|}
else|else
block|{
name|oldValue
operator|=
name|sessionConfig
operator|.
name|getValue
argument_list|()
expr_stmt|;
name|wasSession
operator|=
literal|true
expr_stmt|;
block|}
if|if
condition|(
name|frm
operator|.
name|isAllSessions
argument_list|()
condition|)
block|{
if|if
condition|(
name|wasSession
condition|)
block|{
comment|// there was a session config for the current session
if|if
condition|(
name|update
condition|)
block|{
comment|// update --> delete all with the same value
for|for
control|(
name|SessionConfig
name|config
range|:
operator|(
name|List
argument_list|<
name|SessionConfig
argument_list|>
operator|)
name|hibSession
operator|.
name|createQuery
argument_list|(
literal|"from SessionConfig where key = :key and value = :value"
argument_list|)
operator|.
name|setString
argument_list|(
literal|"key"
argument_list|,
name|frm
operator|.
name|getKey
argument_list|()
argument_list|)
operator|.
name|setString
argument_list|(
literal|"value"
argument_list|,
name|oldValue
argument_list|)
operator|.
name|list
argument_list|()
control|)
block|{
name|ApplicationProperties
operator|.
name|getSessionProperties
argument_list|(
name|config
operator|.
name|getSession
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|)
operator|.
name|remove
argument_list|(
name|frm
operator|.
name|getKey
argument_list|()
argument_list|)
expr_stmt|;
name|hibSession
operator|.
name|delete
argument_list|(
name|config
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
comment|// create --> delete just the current one
name|SessionConfig
name|config
init|=
name|SessionConfig
operator|.
name|getConfig
argument_list|(
name|frm
operator|.
name|getKey
argument_list|()
argument_list|,
name|sessionContext
operator|.
name|getUser
argument_list|()
operator|.
name|getCurrentAcademicSessionId
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|config
operator|!=
literal|null
condition|)
block|{
name|ApplicationProperties
operator|.
name|getSessionProperties
argument_list|(
name|config
operator|.
name|getSession
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|)
operator|.
name|remove
argument_list|(
name|frm
operator|.
name|getKey
argument_list|()
argument_list|)
expr_stmt|;
name|hibSession
operator|.
name|delete
argument_list|(
name|config
argument_list|)
expr_stmt|;
block|}
block|}
block|}
name|ApplicationConfig
name|config
init|=
name|ApplicationConfigDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|get
argument_list|(
name|frm
operator|.
name|getKey
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|config
operator|==
literal|null
condition|)
block|{
name|config
operator|=
operator|new
name|ApplicationConfig
argument_list|()
expr_stmt|;
name|config
operator|.
name|setKey
argument_list|(
name|frm
operator|.
name|getKey
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|config
operator|.
name|setValue
argument_list|(
name|frm
operator|.
name|getValue
argument_list|()
argument_list|)
expr_stmt|;
name|config
operator|.
name|setDescription
argument_list|(
name|frm
operator|.
name|getDescription
argument_list|()
argument_list|)
expr_stmt|;
name|ApplicationProperties
operator|.
name|getConfigProperties
argument_list|()
operator|.
name|put
argument_list|(
name|frm
operator|.
name|getKey
argument_list|()
argument_list|,
name|frm
operator|.
name|getValue
argument_list|()
argument_list|)
expr_stmt|;
name|hibSession
operator|.
name|saveOrUpdate
argument_list|(
name|config
argument_list|)
expr_stmt|;
block|}
else|else
block|{
if|if
condition|(
name|update
operator|&&
operator|!
name|wasSession
condition|)
block|{
comment|// update --> delete global value
name|ApplicationConfig
name|config
init|=
name|ApplicationConfigDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|get
argument_list|(
name|frm
operator|.
name|getKey
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|config
operator|!=
literal|null
condition|)
block|{
name|ApplicationProperties
operator|.
name|getConfigProperties
argument_list|()
operator|.
name|remove
argument_list|(
name|frm
operator|.
name|getKey
argument_list|()
argument_list|)
expr_stmt|;
name|hibSession
operator|.
name|delete
argument_list|(
name|config
argument_list|)
expr_stmt|;
block|}
block|}
name|Set
argument_list|<
name|Long
argument_list|>
name|updatedSessionIds
init|=
operator|new
name|HashSet
argument_list|<
name|Long
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|Long
name|sessionId
range|:
name|frm
operator|.
name|getSessions
argument_list|()
control|)
block|{
name|SessionConfig
name|config
init|=
operator|(
name|SessionConfig
operator|)
name|hibSession
operator|.
name|createQuery
argument_list|(
literal|"from SessionConfig where key = :key and session.uniqueId = :sessionId"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"sessionId"
argument_list|,
name|sessionId
argument_list|)
operator|.
name|setString
argument_list|(
literal|"key"
argument_list|,
name|frm
operator|.
name|getKey
argument_list|()
argument_list|)
operator|.
name|uniqueResult
argument_list|()
decl_stmt|;
if|if
condition|(
name|config
operator|==
literal|null
condition|)
block|{
name|config
operator|=
operator|new
name|SessionConfig
argument_list|()
expr_stmt|;
name|config
operator|.
name|setKey
argument_list|(
name|frm
operator|.
name|getKey
argument_list|()
argument_list|)
expr_stmt|;
name|config
operator|.
name|setSession
argument_list|(
name|SessionDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|get
argument_list|(
name|sessionId
argument_list|,
name|hibSession
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|config
operator|.
name|setValue
argument_list|(
name|frm
operator|.
name|getValue
argument_list|()
argument_list|)
expr_stmt|;
name|config
operator|.
name|setDescription
argument_list|(
name|frm
operator|.
name|getDescription
argument_list|()
argument_list|)
expr_stmt|;
name|ApplicationProperties
operator|.
name|getSessionProperties
argument_list|(
name|sessionId
argument_list|)
operator|.
name|put
argument_list|(
name|frm
operator|.
name|getKey
argument_list|()
argument_list|,
name|frm
operator|.
name|getValue
argument_list|()
argument_list|)
expr_stmt|;
name|hibSession
operator|.
name|saveOrUpdate
argument_list|(
name|config
argument_list|)
expr_stmt|;
name|updatedSessionIds
operator|.
name|add
argument_list|(
name|sessionId
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|update
operator|&&
name|oldValue
operator|!=
literal|null
condition|)
block|{
comment|// update --> delete old session values
for|for
control|(
name|SessionConfig
name|other
range|:
operator|(
name|List
argument_list|<
name|SessionConfig
argument_list|>
operator|)
name|hibSession
operator|.
name|createQuery
argument_list|(
literal|"from SessionConfig where key = :key and value = :value"
argument_list|)
operator|.
name|setString
argument_list|(
literal|"key"
argument_list|,
name|frm
operator|.
name|getKey
argument_list|()
argument_list|)
operator|.
name|setString
argument_list|(
literal|"value"
argument_list|,
name|oldValue
argument_list|)
operator|.
name|list
argument_list|()
control|)
block|{
if|if
condition|(
operator|!
name|updatedSessionIds
operator|.
name|contains
argument_list|(
name|other
operator|.
name|getSession
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|)
condition|)
block|{
name|ApplicationProperties
operator|.
name|getSessionProperties
argument_list|(
name|other
operator|.
name|getSession
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|)
operator|.
name|remove
argument_list|(
name|frm
operator|.
name|getKey
argument_list|()
argument_list|)
expr_stmt|;
name|hibSession
operator|.
name|delete
argument_list|(
name|other
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
name|hibSession
operator|.
name|flush
argument_list|()
expr_stmt|;
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
literal|"key"
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
block|}
block|}
block|}
comment|// Delete config
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
literal|"button.deleteAppConfig"
argument_list|)
argument_list|)
condition|)
block|{
name|sessionContext
operator|.
name|checkPermission
argument_list|(
name|Right
operator|.
name|ApplicationConfigEdit
argument_list|)
expr_stmt|;
comment|// Validate input
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
if|if
condition|(
operator|!
name|errors
operator|.
name|isEmpty
argument_list|()
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
else|else
block|{
try|try
block|{
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
init|=
name|SessionConfigDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|getSession
argument_list|()
decl_stmt|;
name|SessionConfig
name|sessionConfig
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|sessionContext
operator|.
name|getUser
argument_list|()
operator|.
name|getCurrentAcademicSessionId
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|sessionConfig
operator|=
operator|(
name|SessionConfig
operator|)
name|hibSession
operator|.
name|createQuery
argument_list|(
literal|"from SessionConfig where key = :key and session.uniqueId = :sessionId"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"sessionId"
argument_list|,
name|sessionContext
operator|.
name|getUser
argument_list|()
operator|.
name|getCurrentAcademicSessionId
argument_list|()
argument_list|)
operator|.
name|setString
argument_list|(
literal|"key"
argument_list|,
name|frm
operator|.
name|getKey
argument_list|()
argument_list|)
operator|.
name|uniqueResult
argument_list|()
expr_stmt|;
block|}
if|if
condition|(
name|sessionConfig
operator|==
literal|null
condition|)
block|{
name|ApplicationConfig
name|appConfig
init|=
name|ApplicationConfigDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|get
argument_list|(
name|frm
operator|.
name|getKey
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|appConfig
operator|!=
literal|null
condition|)
block|{
name|hibSession
operator|.
name|delete
argument_list|(
name|appConfig
argument_list|)
expr_stmt|;
name|ApplicationProperties
operator|.
name|getConfigProperties
argument_list|()
operator|.
name|remove
argument_list|(
name|frm
operator|.
name|getKey
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
name|String
name|oldValue
init|=
name|sessionConfig
operator|.
name|getValue
argument_list|()
decl_stmt|;
name|hibSession
operator|.
name|delete
argument_list|(
name|sessionConfig
argument_list|)
expr_stmt|;
name|ApplicationProperties
operator|.
name|getSessionProperties
argument_list|(
name|sessionContext
operator|.
name|getUser
argument_list|()
operator|.
name|getCurrentAcademicSessionId
argument_list|()
argument_list|)
operator|.
name|remove
argument_list|(
name|frm
operator|.
name|getKey
argument_list|()
argument_list|)
expr_stmt|;
for|for
control|(
name|SessionConfig
name|other
range|:
operator|(
name|List
argument_list|<
name|SessionConfig
argument_list|>
operator|)
name|hibSession
operator|.
name|createQuery
argument_list|(
literal|"from SessionConfig where key = :key and value = :value"
argument_list|)
operator|.
name|setString
argument_list|(
literal|"key"
argument_list|,
name|frm
operator|.
name|getKey
argument_list|()
argument_list|)
operator|.
name|setString
argument_list|(
literal|"value"
argument_list|,
name|oldValue
argument_list|)
operator|.
name|list
argument_list|()
control|)
block|{
name|ApplicationProperties
operator|.
name|getSessionProperties
argument_list|(
name|other
operator|.
name|getSession
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|)
operator|.
name|remove
argument_list|(
name|frm
operator|.
name|getKey
argument_list|()
argument_list|)
expr_stmt|;
name|hibSession
operator|.
name|delete
argument_list|(
name|other
argument_list|)
expr_stmt|;
block|}
block|}
name|hibSession
operator|.
name|flush
argument_list|()
expr_stmt|;
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
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|e
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
name|errors
operator|.
name|add
argument_list|(
literal|"key"
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
block|}
block|}
block|}
comment|// Cancel update
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
literal|"button.cancelUpdateAppConfig"
argument_list|)
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
if|if
condition|(
literal|"list"
operator|.
name|equals
argument_list|(
name|frm
operator|.
name|getOp
argument_list|()
argument_list|)
condition|)
block|{
comment|//Read all existing ApplicationConfig and store in request
name|getApplicationConfigList
argument_list|(
name|request
argument_list|)
expr_stmt|;
return|return
name|mapping
operator|.
name|findForward
argument_list|(
literal|"list"
argument_list|)
return|;
block|}
return|return
name|mapping
operator|.
name|findForward
argument_list|(
name|rsc
operator|.
name|getMessage
argument_list|(
literal|"button.createAppConfig"
argument_list|)
operator|.
name|equals
argument_list|(
name|frm
operator|.
name|getOp
argument_list|()
argument_list|)
condition|?
literal|"add"
else|:
literal|"edit"
argument_list|)
return|;
block|}
comment|/**      * Retrieve all existing defined configs      * @param request Request object      * @throws Exception      */
specifier|private
name|void
name|getApplicationConfigList
parameter_list|(
name|HttpServletRequest
name|request
parameter_list|)
throws|throws
name|Exception
block|{
name|WebTable
operator|.
name|setOrder
argument_list|(
name|sessionContext
argument_list|,
literal|"applicationConfig.ord"
argument_list|,
name|request
operator|.
name|getParameter
argument_list|(
literal|"ord"
argument_list|)
argument_list|,
literal|1
argument_list|)
expr_stmt|;
comment|// Create web table instance
name|WebTable
name|webTable
init|=
operator|new
name|WebTable
argument_list|(
literal|4
argument_list|,
literal|null
argument_list|,
literal|"applicationConfig.do?ord=%%"
argument_list|,
operator|new
name|String
index|[]
block|{
literal|"Name"
block|,
literal|"Value"
block|,
literal|"Description"
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
name|webTable
operator|.
name|enableHR
argument_list|(
literal|"#9CB0CE"
argument_list|)
expr_stmt|;
name|Map
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|properties
init|=
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|ApplicationConfig
name|config
range|:
name|ApplicationConfigDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|findAll
argument_list|()
control|)
block|{
name|properties
operator|.
name|put
argument_list|(
name|config
operator|.
name|getKey
argument_list|()
argument_list|,
name|config
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|sessionContext
operator|.
name|getUser
argument_list|()
operator|.
name|getCurrentAcademicSessionId
argument_list|()
operator|!=
literal|null
condition|)
block|{
for|for
control|(
name|SessionConfig
name|config
range|:
name|SessionConfig
operator|.
name|findAll
argument_list|(
name|sessionContext
operator|.
name|getUser
argument_list|()
operator|.
name|getCurrentAcademicSessionId
argument_list|()
argument_list|)
control|)
block|{
name|properties
operator|.
name|put
argument_list|(
name|config
operator|.
name|getKey
argument_list|()
argument_list|,
name|config
argument_list|)
expr_stmt|;
block|}
block|}
name|boolean
name|editable
init|=
name|sessionContext
operator|.
name|hasPermission
argument_list|(
name|Right
operator|.
name|ApplicationConfigEdit
argument_list|)
decl_stmt|;
if|if
condition|(
name|properties
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|webTable
operator|.
name|addLine
argument_list|(
literal|null
argument_list|,
operator|new
name|String
index|[]
block|{
literal|"No configuration keys found"
block|}
argument_list|,
literal|null
argument_list|,
literal|null
argument_list|)
expr_stmt|;
block|}
else|else
block|{
for|for
control|(
name|String
name|key
range|:
operator|new
name|TreeSet
argument_list|<
name|String
argument_list|>
argument_list|(
name|properties
operator|.
name|keySet
argument_list|()
argument_list|)
control|)
block|{
name|Object
name|o
init|=
name|properties
operator|.
name|get
argument_list|(
name|key
argument_list|)
decl_stmt|;
if|if
condition|(
name|o
operator|instanceof
name|SessionConfig
condition|)
block|{
name|SessionConfig
name|config
init|=
operator|(
name|SessionConfig
operator|)
name|o
decl_stmt|;
name|webTable
operator|.
name|addLine
argument_list|(
name|editable
condition|?
literal|"onClick=\"document.location='applicationConfig.do?op=edit&id="
operator|+
name|config
operator|.
name|getKey
argument_list|()
operator|+
literal|"';\""
else|:
literal|null
argument_list|,
operator|new
name|String
index|[]
block|{
name|config
operator|.
name|getKey
argument_list|()
operator|+
literal|"<sup><font color='#2066CE' title='Applies to "
operator|+
name|config
operator|.
name|getSession
argument_list|()
operator|.
name|getLabel
argument_list|()
operator|+
literal|"'>s)</font></sup>"
block|,
name|config
operator|.
name|getValue
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
name|config
operator|.
name|getValue
argument_list|()
block|,
name|config
operator|.
name|getDescription
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
name|config
operator|.
name|getDescription
argument_list|()
block|}
argument_list|,
operator|new
name|String
index|[]
block|{
name|config
operator|.
name|getKey
argument_list|()
block|,
name|config
operator|.
name|getValue
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
name|config
operator|.
name|getValue
argument_list|()
block|,
name|config
operator|.
name|getDescription
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
name|config
operator|.
name|getDescription
argument_list|()
block|}
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|ApplicationConfig
name|config
init|=
operator|(
name|ApplicationConfig
operator|)
name|o
decl_stmt|;
name|webTable
operator|.
name|addLine
argument_list|(
name|editable
condition|?
literal|"onClick=\"document.location='applicationConfig.do?op=edit&id="
operator|+
name|config
operator|.
name|getKey
argument_list|()
operator|+
literal|"';\""
else|:
literal|null
argument_list|,
operator|new
name|String
index|[]
block|{
name|config
operator|.
name|getKey
argument_list|()
block|,
name|config
operator|.
name|getValue
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
name|config
operator|.
name|getValue
argument_list|()
block|,
name|config
operator|.
name|getDescription
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
name|config
operator|.
name|getDescription
argument_list|()
block|}
argument_list|,
operator|new
name|String
index|[]
block|{
name|config
operator|.
name|getKey
argument_list|()
block|,
name|config
operator|.
name|getValue
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
name|config
operator|.
name|getValue
argument_list|()
block|,
name|config
operator|.
name|getDescription
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
name|config
operator|.
name|getDescription
argument_list|()
block|}
argument_list|)
expr_stmt|;
block|}
block|}
block|}
name|request
operator|.
name|setAttribute
argument_list|(
name|ApplicationConfig
operator|.
name|APP_CFG_ATTR_NAME
argument_list|,
name|webTable
operator|.
name|printTable
argument_list|(
name|WebTable
operator|.
name|getOrder
argument_list|(
name|sessionContext
argument_list|,
literal|"applicationConfig.ord"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

