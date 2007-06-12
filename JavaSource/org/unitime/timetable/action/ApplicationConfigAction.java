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
name|hibernate
operator|.
name|criterion
operator|.
name|Order
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
name|dao
operator|.
name|ApplicationConfigDAO
import|;
end_import

begin_comment
comment|/**   * MyEclipse Struts  * Creation date: 08-28-2006  *   * XDoclet definition:  * @struts:action path="/applicationConfig" name="applicationConfigForm" input="/admin/applicationConfig.jsp" scope="request"  */
end_comment

begin_class
specifier|public
class|class
name|ApplicationConfigAction
extends|extends
name|Action
block|{
comment|// --------------------------------------------------------- Instance Variables
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
comment|// Check Access
if|if
condition|(
operator|!
name|Web
operator|.
name|isLoggedIn
argument_list|(
name|request
operator|.
name|getSession
argument_list|()
argument_list|)
operator|||
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
name|Roles
operator|.
name|getAdminRoles
argument_list|()
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
name|frm
operator|.
name|getOp
argument_list|()
decl_stmt|;
if|if
condition|(
name|op
operator|==
literal|null
condition|)
block|{
name|op
operator|=
name|request
operator|.
name|getParameter
argument_list|(
literal|"op"
argument_list|)
expr_stmt|;
if|if
condition|(
name|op
operator|==
literal|null
condition|)
block|{
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
block|}
block|}
comment|// Edit Config - Load existing config values to be edited
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
literal|"op.edit"
argument_list|)
argument_list|)
condition|)
block|{
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
name|length
argument_list|()
operator|==
literal|0
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
literal|"Key : "
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
name|ApplicationConfigDAO
name|sdao
init|=
operator|new
name|ApplicationConfigDAO
argument_list|()
decl_stmt|;
name|ApplicationConfig
name|s
init|=
name|sdao
operator|.
name|get
argument_list|(
name|id
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
literal|"key"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.invalid"
argument_list|,
literal|"Key : "
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
name|s
operator|.
name|getKey
argument_list|()
argument_list|)
expr_stmt|;
name|frm
operator|.
name|setValue
argument_list|(
name|s
operator|.
name|getValue
argument_list|()
argument_list|)
expr_stmt|;
name|frm
operator|.
name|setDescription
argument_list|(
name|s
operator|.
name|getDescription
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
comment|// Update config
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
condition|)
block|{
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
block|}
else|else
block|{
try|try
block|{
name|ApplicationConfigDAO
name|sdao
init|=
operator|new
name|ApplicationConfigDAO
argument_list|()
decl_stmt|;
name|ApplicationConfig
name|s
init|=
name|sdao
operator|.
name|get
argument_list|(
name|frm
operator|.
name|getKey
argument_list|()
argument_list|)
decl_stmt|;
name|s
operator|.
name|setValue
argument_list|(
name|frm
operator|.
name|getValue
argument_list|()
argument_list|)
expr_stmt|;
name|s
operator|.
name|setDescription
argument_list|(
name|frm
operator|.
name|getDescription
argument_list|()
argument_list|)
expr_stmt|;
name|sdao
operator|.
name|saveOrUpdate
argument_list|(
name|s
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
block|}
else|else
block|{
try|try
block|{
name|ApplicationConfigDAO
name|sdao
init|=
operator|new
name|ApplicationConfigDAO
argument_list|()
decl_stmt|;
name|sdao
operator|.
name|delete
argument_list|(
name|frm
operator|.
name|getKey
argument_list|()
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
comment|// Create config
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
literal|"button.createAppConfig"
argument_list|)
argument_list|)
condition|)
block|{
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
block|}
else|else
block|{
try|try
block|{
name|ApplicationConfigDAO
name|sdao
init|=
operator|new
name|ApplicationConfigDAO
argument_list|()
decl_stmt|;
if|if
condition|(
name|sdao
operator|.
name|get
argument_list|(
name|frm
operator|.
name|getKey
argument_list|()
argument_list|)
operator|!=
literal|null
condition|)
throw|throw
operator|new
name|Exception
argument_list|(
literal|"A property with this key already exists."
argument_list|)
throw|;
name|ApplicationConfig
name|s
init|=
operator|new
name|ApplicationConfig
argument_list|()
decl_stmt|;
name|s
operator|.
name|setKey
argument_list|(
name|frm
operator|.
name|getKey
argument_list|()
argument_list|)
expr_stmt|;
name|s
operator|.
name|setValue
argument_list|(
name|frm
operator|.
name|getValue
argument_list|()
argument_list|)
expr_stmt|;
name|s
operator|.
name|setDescription
argument_list|(
name|frm
operator|.
name|getDescription
argument_list|()
argument_list|)
expr_stmt|;
name|sdao
operator|.
name|saveOrUpdate
argument_list|(
name|s
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
comment|// Create config
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
comment|// Read all existing ApplicationConfig and store in request
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
literal|"displayApplicationConfig"
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
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
init|=
literal|null
decl_stmt|;
name|MessageResources
name|rsc
init|=
name|getResources
argument_list|(
name|request
argument_list|)
decl_stmt|;
comment|// Create web table instance
name|WebTable
name|webTable
init|=
operator|new
name|WebTable
argument_list|(
literal|3
argument_list|,
literal|"Application Configuration Settings"
argument_list|,
operator|new
name|String
index|[]
block|{
literal|"Key"
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
try|try
block|{
name|ApplicationConfigDAO
name|sDao
init|=
operator|new
name|ApplicationConfigDAO
argument_list|()
decl_stmt|;
name|hibSession
operator|=
name|sDao
operator|.
name|getSession
argument_list|()
expr_stmt|;
name|List
name|configList
init|=
name|hibSession
operator|.
name|createCriteria
argument_list|(
name|ApplicationConfig
operator|.
name|class
argument_list|)
operator|.
name|addOrder
argument_list|(
name|Order
operator|.
name|asc
argument_list|(
literal|"key"
argument_list|)
argument_list|)
operator|.
name|list
argument_list|()
decl_stmt|;
if|if
condition|(
name|configList
operator|.
name|size
argument_list|()
operator|==
literal|0
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
name|Iterator
name|iterConfigs
init|=
name|configList
operator|.
name|iterator
argument_list|()
decl_stmt|;
while|while
condition|(
name|iterConfigs
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|ApplicationConfig
name|s
init|=
operator|(
name|ApplicationConfig
operator|)
name|iterConfigs
operator|.
name|next
argument_list|()
decl_stmt|;
name|String
name|key
init|=
name|s
operator|.
name|getKey
argument_list|()
decl_stmt|;
name|String
name|value
init|=
name|s
operator|.
name|getValue
argument_list|()
decl_stmt|;
name|String
name|description
init|=
name|s
operator|.
name|getDescription
argument_list|()
decl_stmt|;
name|String
name|onClick
init|=
literal|"onClick=\"document.location='applicationConfig.do?op="
operator|+
name|rsc
operator|.
name|getMessage
argument_list|(
literal|"op.edit"
argument_list|)
operator|+
literal|"&id="
operator|+
name|s
operator|.
name|getKey
argument_list|()
operator|+
literal|"';\""
decl_stmt|;
name|webTable
operator|.
name|addLine
argument_list|(
name|onClick
argument_list|,
operator|new
name|String
index|[]
block|{
name|key
block|,
name|value
block|,
name|description
block|}
argument_list|,
literal|null
argument_list|,
literal|null
argument_list|)
expr_stmt|;
block|}
block|}
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|Exception
argument_list|(
name|e
argument_list|)
throw|;
block|}
finally|finally
block|{
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
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

