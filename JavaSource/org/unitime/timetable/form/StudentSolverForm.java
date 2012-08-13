begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.2 (University Timetabling Application)  * Copyright (C) 2008 - 2010, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
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
name|Collection
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
name|Hashtable
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
name|Map
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|StringTokenizer
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
name|hibernate
operator|.
name|Transaction
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
name|Restrictions
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
name|model
operator|.
name|SolverParameter
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
name|SolverParameterDef
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
name|SolverPredefinedSetting
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
name|SolverPredefinedSettingDAO
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
name|WebSolver
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
name|studentsct
operator|.
name|StudentSolverProxy
import|;
end_import

begin_comment
comment|/**   * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|StudentSolverForm
extends|extends
name|ActionForm
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|3831509516652149101L
decl_stmt|;
specifier|private
name|String
name|iOp
init|=
literal|null
decl_stmt|;
specifier|private
name|Long
name|iSetting
init|=
literal|null
decl_stmt|;
specifier|private
name|Vector
name|iSettings
init|=
operator|new
name|Vector
argument_list|()
decl_stmt|;
specifier|private
name|Hashtable
name|iParamValues
init|=
operator|new
name|Hashtable
argument_list|()
decl_stmt|;
specifier|private
name|Hashtable
name|iDefaults
init|=
operator|new
name|Hashtable
argument_list|()
decl_stmt|;
specifier|private
specifier|static
name|Long
name|sEmpty
init|=
operator|new
name|Long
argument_list|(
operator|-
literal|1
argument_list|)
decl_stmt|;
specifier|private
specifier|static
name|Long
name|sDefault
init|=
operator|new
name|Long
argument_list|(
operator|-
literal|2
argument_list|)
decl_stmt|;
specifier|private
specifier|static
name|Long
name|sSolver
init|=
operator|new
name|Long
argument_list|(
operator|-
literal|3
argument_list|)
decl_stmt|;
specifier|private
name|Vector
name|iParams
init|=
operator|new
name|Vector
argument_list|()
decl_stmt|;
specifier|private
name|String
name|iHost
init|=
literal|null
decl_stmt|;
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
if|if
condition|(
name|iSetting
operator|==
literal|null
operator|||
name|iSetting
operator|.
name|intValue
argument_list|()
operator|<
literal|0
condition|)
name|errors
operator|.
name|add
argument_list|(
literal|"setting"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.lookup.config.required"
argument_list|,
literal|""
argument_list|)
argument_list|)
expr_stmt|;
for|for
control|(
name|Iterator
name|i
init|=
name|iParamValues
operator|.
name|entrySet
argument_list|()
operator|.
name|iterator
argument_list|()
init|;
name|i
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|Map
operator|.
name|Entry
name|entry
init|=
operator|(
name|Map
operator|.
name|Entry
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|Long
name|parm
init|=
operator|(
name|Long
operator|)
name|entry
operator|.
name|getKey
argument_list|()
decl_stmt|;
name|String
name|val
init|=
operator|(
name|String
operator|)
name|entry
operator|.
name|getValue
argument_list|()
decl_stmt|;
if|if
condition|(
name|val
operator|==
literal|null
operator|||
name|val
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
literal|"parameterValue["
operator|+
name|parm
operator|+
literal|"]"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.required"
argument_list|,
literal|""
argument_list|)
argument_list|)
expr_stmt|;
block|}
return|return
name|errors
return|;
block|}
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
name|iOp
operator|=
literal|null
expr_stmt|;
name|iSettings
operator|.
name|clear
argument_list|()
expr_stmt|;
name|iSetting
operator|=
name|sEmpty
expr_stmt|;
name|StudentSolverProxy
name|solver
init|=
name|WebSolver
operator|.
name|getStudentSolver
argument_list|(
name|request
operator|.
name|getSession
argument_list|()
argument_list|)
decl_stmt|;
name|Transaction
name|tx
init|=
literal|null
decl_stmt|;
name|iParams
operator|.
name|clear
argument_list|()
expr_stmt|;
name|iDefaults
operator|.
name|clear
argument_list|()
expr_stmt|;
name|iParamValues
operator|.
name|clear
argument_list|()
expr_stmt|;
try|try
block|{
name|SolverPredefinedSettingDAO
name|dao
init|=
operator|new
name|SolverPredefinedSettingDAO
argument_list|()
decl_stmt|;
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
init|=
name|dao
operator|.
name|getSession
argument_list|()
decl_stmt|;
if|if
condition|(
name|hibSession
operator|.
name|getTransaction
argument_list|()
operator|==
literal|null
operator|||
operator|!
name|hibSession
operator|.
name|getTransaction
argument_list|()
operator|.
name|isActive
argument_list|()
condition|)
name|tx
operator|=
name|hibSession
operator|.
name|beginTransaction
argument_list|()
expr_stmt|;
name|List
name|defaultsList
init|=
name|hibSession
operator|.
name|createCriteria
argument_list|(
name|SolverParameterDef
operator|.
name|class
argument_list|)
operator|.
name|setCacheable
argument_list|(
literal|true
argument_list|)
operator|.
name|list
argument_list|()
decl_stmt|;
name|Hashtable
name|defaults
init|=
operator|new
name|Hashtable
argument_list|()
decl_stmt|;
name|Hashtable
name|empty
init|=
operator|new
name|Hashtable
argument_list|()
decl_stmt|;
for|for
control|(
name|Iterator
name|i
init|=
name|defaultsList
operator|.
name|iterator
argument_list|()
init|;
name|i
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|SolverParameterDef
name|def
init|=
operator|(
name|SolverParameterDef
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
literal|"StudentSctBasic"
operator|.
name|equals
argument_list|(
name|def
operator|.
name|getGroup
argument_list|()
operator|.
name|getName
argument_list|()
argument_list|)
condition|)
continue|continue;
if|if
condition|(
operator|!
name|def
operator|.
name|isVisible
argument_list|()
operator|.
name|booleanValue
argument_list|()
condition|)
continue|continue;
if|if
condition|(
literal|"boolean"
operator|.
name|equals
argument_list|(
name|def
operator|.
name|getType
argument_list|()
argument_list|)
condition|)
block|{
name|iParamValues
operator|.
name|put
argument_list|(
name|def
operator|.
name|getUniqueId
argument_list|()
argument_list|,
literal|"false"
argument_list|)
expr_stmt|;
name|empty
operator|.
name|put
argument_list|(
name|def
operator|.
name|getUniqueId
argument_list|()
argument_list|,
literal|"false"
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|iParamValues
operator|.
name|put
argument_list|(
name|def
operator|.
name|getUniqueId
argument_list|()
argument_list|,
literal|""
argument_list|)
expr_stmt|;
name|empty
operator|.
name|put
argument_list|(
name|def
operator|.
name|getUniqueId
argument_list|()
argument_list|,
literal|""
argument_list|)
expr_stmt|;
block|}
name|iParams
operator|.
name|add
argument_list|(
operator|new
name|SolverPredefinedSetting
operator|.
name|IdValue
argument_list|(
name|def
operator|.
name|getUniqueId
argument_list|()
argument_list|,
name|def
operator|.
name|getDescription
argument_list|()
argument_list|,
name|def
operator|.
name|getType
argument_list|()
argument_list|,
literal|true
argument_list|)
argument_list|)
expr_stmt|;
name|defaults
operator|.
name|put
argument_list|(
name|def
operator|.
name|getUniqueId
argument_list|()
argument_list|,
name|def
operator|.
name|getDefault
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|iDefaults
operator|.
name|put
argument_list|(
name|sEmpty
argument_list|,
name|empty
argument_list|)
expr_stmt|;
name|iDefaults
operator|.
name|put
argument_list|(
name|sDefault
argument_list|,
name|defaults
argument_list|)
expr_stmt|;
name|List
name|settingsList
init|=
name|hibSession
operator|.
name|createCriteria
argument_list|(
name|SolverPredefinedSetting
operator|.
name|class
argument_list|)
operator|.
name|add
argument_list|(
name|Restrictions
operator|.
name|eq
argument_list|(
literal|"appearance"
argument_list|,
name|SolverPredefinedSetting
operator|.
name|APPEARANCE_STUDENT_SOLVER
argument_list|)
argument_list|)
operator|.
name|setCacheable
argument_list|(
literal|true
argument_list|)
operator|.
name|list
argument_list|()
decl_stmt|;
for|for
control|(
name|Iterator
name|i
init|=
name|settingsList
operator|.
name|iterator
argument_list|()
init|;
name|i
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|SolverPredefinedSetting
name|setting
init|=
operator|(
name|SolverPredefinedSetting
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|Hashtable
name|settings
init|=
operator|new
name|Hashtable
argument_list|()
decl_stmt|;
name|boolean
name|skip
init|=
literal|false
decl_stmt|;
for|for
control|(
name|Iterator
name|j
init|=
name|setting
operator|.
name|getParameters
argument_list|()
operator|.
name|iterator
argument_list|()
init|;
name|j
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|SolverParameter
name|param
init|=
operator|(
name|SolverParameter
operator|)
name|j
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
literal|"StudentSctBasic"
operator|.
name|equals
argument_list|(
name|param
operator|.
name|getDefinition
argument_list|()
operator|.
name|getGroup
argument_list|()
operator|.
name|getName
argument_list|()
argument_list|)
condition|)
continue|continue;
if|if
condition|(
name|param
operator|.
name|getDefinition
argument_list|()
operator|.
name|getName
argument_list|()
operator|.
name|equals
argument_list|(
literal|"StudentSctBasic.WhenFinished"
argument_list|)
condition|)
name|skip
operator||=
operator|!
literal|"No Action"
operator|.
name|equals
argument_list|(
name|param
operator|.
name|getValue
argument_list|()
operator|==
literal|null
condition|?
name|param
operator|.
name|getDefinition
argument_list|()
operator|.
name|getDefault
argument_list|()
else|:
name|param
operator|.
name|getValue
argument_list|()
argument_list|)
expr_stmt|;
name|settings
operator|.
name|put
argument_list|(
name|param
operator|.
name|getDefinition
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|,
name|param
operator|.
name|getValue
argument_list|()
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
operator|!
name|skip
condition|)
block|{
name|iSettings
operator|.
name|add
argument_list|(
operator|new
name|SolverPredefinedSetting
operator|.
name|IdValue
argument_list|(
name|setting
operator|.
name|getUniqueId
argument_list|()
argument_list|,
name|setting
operator|.
name|getDescription
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|iDefaults
operator|.
name|put
argument_list|(
name|setting
operator|.
name|getUniqueId
argument_list|()
argument_list|,
name|settings
argument_list|)
expr_stmt|;
block|}
block|}
if|if
condition|(
name|solver
operator|!=
literal|null
condition|)
block|{
name|iSetting
operator|=
name|solver
operator|.
name|getProperties
argument_list|()
operator|.
name|getPropertyLong
argument_list|(
literal|"General.SettingsId"
argument_list|,
literal|null
argument_list|)
expr_stmt|;
name|Hashtable
name|settings
init|=
operator|new
name|Hashtable
argument_list|()
decl_stmt|;
for|for
control|(
name|Iterator
name|i
init|=
name|defaultsList
operator|.
name|iterator
argument_list|()
init|;
name|i
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|SolverParameterDef
name|def
init|=
operator|(
name|SolverParameterDef
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
literal|"StudentSctBasic"
operator|.
name|equals
argument_list|(
name|def
operator|.
name|getGroup
argument_list|()
operator|.
name|getName
argument_list|()
argument_list|)
condition|)
continue|continue;
if|if
condition|(
operator|!
name|def
operator|.
name|isVisible
argument_list|()
operator|.
name|booleanValue
argument_list|()
condition|)
continue|continue;
name|String
name|value
init|=
name|solver
operator|.
name|getProperties
argument_list|()
operator|.
name|getProperty
argument_list|(
name|def
operator|.
name|getName
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|value
operator|!=
literal|null
condition|)
name|settings
operator|.
name|put
argument_list|(
name|def
operator|.
name|getUniqueId
argument_list|()
argument_list|,
name|value
argument_list|)
expr_stmt|;
block|}
name|iDefaults
operator|.
name|put
argument_list|(
name|sSolver
argument_list|,
name|settings
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|iSetting
operator|!=
literal|null
condition|)
block|{
name|boolean
name|contains
init|=
literal|false
decl_stmt|;
for|for
control|(
name|Enumeration
name|e
init|=
name|iSettings
operator|.
name|elements
argument_list|()
init|;
operator|!
name|contains
operator|&&
name|e
operator|.
name|hasMoreElements
argument_list|()
condition|;
control|)
block|{
name|SolverPredefinedSetting
operator|.
name|IdValue
name|x
init|=
operator|(
name|SolverPredefinedSetting
operator|.
name|IdValue
operator|)
name|e
operator|.
name|nextElement
argument_list|()
decl_stmt|;
if|if
condition|(
name|x
operator|.
name|getId
argument_list|()
operator|.
name|equals
argument_list|(
name|iSetting
argument_list|)
condition|)
name|contains
operator|=
literal|true
expr_stmt|;
block|}
if|if
condition|(
operator|!
name|contains
condition|)
block|{
name|SolverPredefinedSetting
name|setting
init|=
operator|(
operator|new
name|SolverPredefinedSettingDAO
argument_list|()
operator|)
operator|.
name|get
argument_list|(
name|iSetting
argument_list|)
decl_stmt|;
if|if
condition|(
name|setting
operator|!=
literal|null
condition|)
block|{
name|iSettings
operator|.
name|add
argument_list|(
operator|new
name|SolverPredefinedSetting
operator|.
name|IdValue
argument_list|(
name|setting
operator|.
name|getUniqueId
argument_list|()
argument_list|,
name|setting
operator|.
name|getDescription
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|Hashtable
name|settings
init|=
operator|new
name|Hashtable
argument_list|()
decl_stmt|;
for|for
control|(
name|Iterator
name|j
init|=
name|setting
operator|.
name|getParameters
argument_list|()
operator|.
name|iterator
argument_list|()
init|;
name|j
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|SolverParameter
name|param
init|=
operator|(
name|SolverParameter
operator|)
name|j
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
literal|"StudentSctBasic"
operator|.
name|equals
argument_list|(
name|param
operator|.
name|getDefinition
argument_list|()
operator|.
name|getGroup
argument_list|()
operator|.
name|getName
argument_list|()
argument_list|)
condition|)
continue|continue;
name|settings
operator|.
name|put
argument_list|(
name|param
operator|.
name|getDefinition
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|,
name|param
operator|.
name|getValue
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|iDefaults
operator|.
name|put
argument_list|(
name|setting
operator|.
name|getUniqueId
argument_list|()
argument_list|,
name|settings
argument_list|)
expr_stmt|;
block|}
block|}
block|}
if|if
condition|(
name|tx
operator|!=
literal|null
condition|)
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
name|Debug
operator|.
name|error
argument_list|(
name|e
argument_list|)
expr_stmt|;
block|}
name|iHost
operator|=
operator|(
name|solver
operator|==
literal|null
condition|?
literal|"auto"
else|:
name|solver
operator|.
name|getHost
argument_list|()
operator|)
expr_stmt|;
block|}
specifier|public
name|void
name|init
parameter_list|()
block|{
if|if
condition|(
name|iDefaults
operator|.
name|containsKey
argument_list|(
name|sSolver
argument_list|)
condition|)
name|iParamValues
operator|.
name|putAll
argument_list|(
operator|(
name|Hashtable
operator|)
name|iDefaults
operator|.
name|get
argument_list|(
name|sSolver
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|iSetting
operator|==
literal|null
operator|||
name|iSetting
operator|.
name|equals
argument_list|(
name|sEmpty
argument_list|)
condition|)
block|{
for|for
control|(
name|Enumeration
name|e
init|=
name|iSettings
operator|.
name|elements
argument_list|()
init|;
name|e
operator|.
name|hasMoreElements
argument_list|()
condition|;
control|)
block|{
name|SolverPredefinedSetting
operator|.
name|IdValue
name|x
init|=
operator|(
name|SolverPredefinedSetting
operator|.
name|IdValue
operator|)
name|e
operator|.
name|nextElement
argument_list|()
decl_stmt|;
if|if
condition|(
literal|"default"
operator|.
name|equalsIgnoreCase
argument_list|(
name|x
operator|.
name|getValue
argument_list|()
argument_list|)
condition|)
block|{
name|iSetting
operator|=
name|x
operator|.
name|getId
argument_list|()
expr_stmt|;
name|change
argument_list|()
expr_stmt|;
block|}
block|}
block|}
if|if
condition|(
name|iSetting
operator|==
literal|null
operator|||
name|iSetting
operator|.
name|equals
argument_list|(
name|sEmpty
argument_list|)
condition|)
block|{
name|iSettings
operator|.
name|insertElementAt
argument_list|(
operator|new
name|SolverPredefinedSetting
operator|.
name|IdValue
argument_list|(
name|sEmpty
argument_list|,
literal|""
argument_list|)
argument_list|,
literal|0
argument_list|)
expr_stmt|;
block|}
block|}
specifier|public
name|void
name|setOp
parameter_list|(
name|String
name|op
parameter_list|)
block|{
name|iOp
operator|=
name|op
expr_stmt|;
block|}
specifier|public
name|String
name|getOp
parameter_list|()
block|{
return|return
name|iOp
return|;
block|}
specifier|public
name|Vector
name|getSettings
parameter_list|()
block|{
return|return
name|iSettings
return|;
block|}
specifier|public
name|void
name|setSettings
parameter_list|(
name|Vector
name|settings
parameter_list|)
block|{
name|iSettings
operator|=
name|settings
expr_stmt|;
block|}
specifier|public
name|Long
name|getSetting
parameter_list|()
block|{
return|return
name|iSetting
return|;
block|}
specifier|public
name|void
name|setSetting
parameter_list|(
name|Long
name|setting
parameter_list|)
block|{
name|iSetting
operator|=
name|setting
expr_stmt|;
block|}
specifier|public
name|void
name|change
parameter_list|()
block|{
for|for
control|(
name|Enumeration
name|e
init|=
name|iParams
operator|.
name|elements
argument_list|()
init|;
name|e
operator|.
name|hasMoreElements
argument_list|()
condition|;
control|)
block|{
name|SolverPredefinedSetting
operator|.
name|IdValue
name|p
init|=
operator|(
name|SolverPredefinedSetting
operator|.
name|IdValue
operator|)
name|e
operator|.
name|nextElement
argument_list|()
decl_stmt|;
if|if
condition|(
name|p
operator|.
name|getDisabled
argument_list|()
condition|)
continue|continue;
name|Hashtable
name|d
init|=
operator|(
name|Hashtable
operator|)
name|iDefaults
operator|.
name|get
argument_list|(
name|iSetting
argument_list|)
decl_stmt|;
name|Object
name|value
init|=
operator|(
name|d
operator|==
literal|null
condition|?
literal|null
else|:
name|d
operator|.
name|get
argument_list|(
name|p
operator|.
name|getId
argument_list|()
argument_list|)
operator|)
decl_stmt|;
if|if
condition|(
name|value
operator|==
literal|null
condition|)
name|value
operator|=
operator|(
operator|(
name|Hashtable
operator|)
name|iDefaults
operator|.
name|get
argument_list|(
name|sDefault
argument_list|)
operator|)
operator|.
name|get
argument_list|(
name|p
operator|.
name|getId
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|value
operator|!=
literal|null
condition|)
name|iParamValues
operator|.
name|put
argument_list|(
name|p
operator|.
name|getId
argument_list|()
argument_list|,
name|value
argument_list|)
expr_stmt|;
block|}
block|}
specifier|public
name|Vector
name|getParameters
parameter_list|()
block|{
return|return
name|iParams
return|;
block|}
specifier|public
name|void
name|setParamters
parameter_list|(
name|Vector
name|parameters
parameter_list|)
block|{
name|iParams
operator|=
name|parameters
expr_stmt|;
block|}
specifier|public
name|SolverPredefinedSetting
operator|.
name|IdValue
name|getParameter
parameter_list|(
name|Long
name|id
parameter_list|)
block|{
for|for
control|(
name|Enumeration
name|e
init|=
name|iParams
operator|.
name|elements
argument_list|()
init|;
name|e
operator|.
name|hasMoreElements
argument_list|()
condition|;
control|)
block|{
name|SolverPredefinedSetting
operator|.
name|IdValue
name|p
init|=
operator|(
name|SolverPredefinedSetting
operator|.
name|IdValue
operator|)
name|e
operator|.
name|nextElement
argument_list|()
decl_stmt|;
if|if
condition|(
name|p
operator|.
name|getId
argument_list|()
operator|.
name|equals
argument_list|(
name|id
argument_list|)
condition|)
return|return
name|p
return|;
block|}
return|return
literal|null
return|;
block|}
specifier|public
name|String
name|getParameterDefault
parameter_list|(
name|Long
name|settingId
parameter_list|,
name|Long
name|parameterId
parameter_list|)
block|{
name|String
name|ret
init|=
operator|(
name|String
operator|)
operator|(
operator|(
name|Hashtable
operator|)
name|iDefaults
operator|.
name|get
argument_list|(
name|settingId
argument_list|)
operator|)
operator|.
name|get
argument_list|(
name|parameterId
argument_list|)
decl_stmt|;
if|if
condition|(
name|ret
operator|==
literal|null
condition|)
name|ret
operator|=
operator|(
name|String
operator|)
operator|(
operator|(
name|Hashtable
operator|)
name|iDefaults
operator|.
name|get
argument_list|(
name|sDefault
argument_list|)
operator|)
operator|.
name|get
argument_list|(
name|parameterId
argument_list|)
expr_stmt|;
return|return
name|ret
return|;
block|}
specifier|public
name|String
name|getParameterValue
parameter_list|(
name|Long
name|id
parameter_list|)
block|{
return|return
operator|(
name|String
operator|)
name|iParamValues
operator|.
name|get
argument_list|(
name|id
argument_list|)
return|;
block|}
specifier|public
name|void
name|setParameterValue
parameter_list|(
name|Long
name|id
parameter_list|,
name|String
name|value
parameter_list|)
block|{
name|iParamValues
operator|.
name|put
argument_list|(
name|id
argument_list|,
name|value
argument_list|)
expr_stmt|;
block|}
specifier|public
name|Hashtable
name|getParameterValues
parameter_list|()
block|{
return|return
name|iParamValues
return|;
block|}
specifier|public
name|String
name|getParameterValue
parameter_list|(
name|long
name|id
parameter_list|)
block|{
return|return
name|getParameterValue
argument_list|(
operator|new
name|Long
argument_list|(
name|id
argument_list|)
argument_list|)
return|;
block|}
specifier|public
name|String
name|getParameterValue
parameter_list|(
name|int
name|id
parameter_list|)
block|{
return|return
name|getParameterValue
argument_list|(
operator|new
name|Long
argument_list|(
name|id
argument_list|)
argument_list|)
return|;
block|}
specifier|public
name|void
name|setParameterValue
parameter_list|(
name|long
name|id
parameter_list|,
name|String
name|value
parameter_list|)
block|{
name|setParameterValue
argument_list|(
operator|new
name|Long
argument_list|(
name|id
argument_list|)
argument_list|,
name|value
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|setParameterValue
parameter_list|(
name|int
name|id
parameter_list|,
name|String
name|value
parameter_list|)
block|{
name|setParameterValue
argument_list|(
operator|new
name|Long
argument_list|(
name|id
argument_list|)
argument_list|,
name|value
argument_list|)
expr_stmt|;
block|}
specifier|public
name|Collection
name|getEnum
parameter_list|(
name|String
name|type
parameter_list|)
block|{
name|Vector
name|options
init|=
operator|new
name|Vector
argument_list|()
decl_stmt|;
name|options
operator|.
name|add
argument_list|(
literal|""
argument_list|)
expr_stmt|;
name|StringTokenizer
name|stk
init|=
operator|new
name|StringTokenizer
argument_list|(
name|type
argument_list|,
literal|","
argument_list|)
decl_stmt|;
while|while
condition|(
name|stk
operator|.
name|hasMoreTokens
argument_list|()
condition|)
name|options
operator|.
name|add
argument_list|(
name|stk
operator|.
name|nextToken
argument_list|()
argument_list|)
expr_stmt|;
return|return
name|options
return|;
block|}
specifier|public
name|String
name|getHost
parameter_list|()
block|{
return|return
name|iHost
return|;
block|}
specifier|public
name|void
name|setHost
parameter_list|(
name|String
name|host
parameter_list|)
block|{
name|iHost
operator|=
name|host
expr_stmt|;
block|}
block|}
end_class

end_unit

