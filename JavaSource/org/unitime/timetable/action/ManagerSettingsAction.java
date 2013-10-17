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
name|form
operator|.
name|ManagerSettingsForm
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
name|Settings
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
name|SettingsDAO
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
comment|/**   * MyEclipse Struts  * Creation date: 10-17-2005  *   * XDoclet definition:  * @struts:action path="/managerSettings" name="managerSettingsForm" input="/user/managerSettings.jsp" scope="request"  *  * @author Tomas Muller  */
end_comment

begin_class
annotation|@
name|Service
argument_list|(
literal|"/managerSettings"
argument_list|)
specifier|public
class|class
name|ManagerSettingsAction
extends|extends
name|Action
block|{
annotation|@
name|Autowired
name|SessionContext
name|sessionContext
decl_stmt|;
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
name|sessionContext
operator|.
name|checkPermission
argument_list|(
name|Right
operator|.
name|SettingsUser
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
name|ManagerSettingsForm
name|frm
init|=
operator|(
name|ManagerSettingsForm
operator|)
name|form
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
name|frm
operator|.
name|setOp
argument_list|(
literal|"List"
argument_list|)
expr_stmt|;
name|op
operator|=
literal|"List"
expr_stmt|;
block|}
block|}
comment|// Reset Form
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
literal|"button.cancelUpdateSetting"
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
name|frm
operator|.
name|setOp
argument_list|(
literal|"List"
argument_list|)
expr_stmt|;
block|}
comment|// Edit - Load setting with allowed values for user to update
if|if
condition|(
name|op
operator|.
name|equals
argument_list|(
literal|"Edit"
argument_list|)
condition|)
block|{
comment|// Load Settings object
name|Settings
name|s
init|=
name|SettingsDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|get
argument_list|(
name|Long
operator|.
name|valueOf
argument_list|(
name|request
operator|.
name|getParameter
argument_list|(
literal|"id"
argument_list|)
argument_list|)
argument_list|)
decl_stmt|;
comment|// Set Form values
name|frm
operator|.
name|setOp
argument_list|(
literal|"Edit"
argument_list|)
expr_stmt|;
name|frm
operator|.
name|setAllowedValues
argument_list|(
name|s
operator|.
name|getAllowedValues
argument_list|()
argument_list|)
expr_stmt|;
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
name|setName
argument_list|(
name|s
operator|.
name|getDescription
argument_list|()
argument_list|)
expr_stmt|;
name|frm
operator|.
name|setDefaultValue
argument_list|(
name|s
operator|.
name|getDefaultValue
argument_list|()
argument_list|)
expr_stmt|;
name|frm
operator|.
name|setValue
argument_list|(
name|sessionContext
operator|.
name|getUser
argument_list|()
operator|.
name|getProperty
argument_list|(
name|s
operator|.
name|getKey
argument_list|()
argument_list|,
name|s
operator|.
name|getDefaultValue
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|mapping
operator|.
name|findForward
argument_list|(
literal|"editManagerSettings"
argument_list|)
return|;
block|}
comment|// Save changes made by the user
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
literal|"button.updateSetting"
argument_list|)
argument_list|)
condition|)
block|{
name|ActionMessages
name|errors
init|=
name|frm
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
name|frm
operator|.
name|setOp
argument_list|(
literal|"Edit"
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|sessionContext
operator|.
name|getUser
argument_list|()
operator|.
name|setProperty
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
block|}
block|}
comment|// Read all existing settings and store in request
name|getSettingsList
argument_list|(
name|request
argument_list|)
expr_stmt|;
return|return
name|mapping
operator|.
name|findForward
argument_list|(
literal|"showManagerSettings"
argument_list|)
return|;
block|}
comment|/**      * Retrieve all existing defined settings      * @param request Request object      * @throws Exception      */
specifier|private
name|void
name|getSettingsList
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
literal|"managerSettings.ord"
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
literal|2
argument_list|,
literal|"Manager Settings"
argument_list|,
literal|"managerSettings.do?ord=%%"
argument_list|,
operator|new
name|String
index|[]
block|{
literal|"Setting"
block|,
literal|"Value"
block|}
argument_list|,
operator|new
name|String
index|[]
block|{
literal|"left"
block|,
literal|"left"
block|}
argument_list|,
literal|null
argument_list|)
decl_stmt|;
for|for
control|(
name|Settings
name|s
range|:
name|SettingsDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|findAll
argument_list|(
name|Order
operator|.
name|asc
argument_list|(
literal|"key"
argument_list|)
argument_list|)
control|)
block|{
name|String
name|onClick
init|=
literal|"onClick=\"document.location='managerSettings.do?op=Edit&id="
operator|+
name|s
operator|.
name|getUniqueId
argument_list|()
operator|+
literal|"';\""
decl_stmt|;
name|String
name|value
init|=
name|sessionContext
operator|.
name|getUser
argument_list|()
operator|.
name|getProperty
argument_list|(
name|s
operator|.
name|getKey
argument_list|()
argument_list|,
name|s
operator|.
name|getDefaultValue
argument_list|()
argument_list|)
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
name|s
operator|.
name|getDescription
argument_list|()
block|,
name|value
block|}
argument_list|,
operator|new
name|String
index|[]
block|{
name|s
operator|.
name|getDescription
argument_list|()
block|,
name|value
block|}
argument_list|)
expr_stmt|;
block|}
name|request
operator|.
name|setAttribute
argument_list|(
literal|"table"
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
literal|"managerSettings.ord"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

