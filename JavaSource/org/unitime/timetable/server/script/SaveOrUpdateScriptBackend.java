begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.4 (University Timetabling Application)  * Copyright (C) 2013, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
end_comment

begin_package
package|package
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|server
operator|.
name|script
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
name|Iterator
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
name|org
operator|.
name|springframework
operator|.
name|security
operator|.
name|access
operator|.
name|prepost
operator|.
name|PreAuthorize
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
name|timetable
operator|.
name|gwt
operator|.
name|command
operator|.
name|server
operator|.
name|GwtRpcImplementation
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
name|gwt
operator|.
name|shared
operator|.
name|ScriptInterface
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
name|gwt
operator|.
name|shared
operator|.
name|ScriptInterface
operator|.
name|SaveOrUpdateScriptRpcRequest
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
name|gwt
operator|.
name|shared
operator|.
name|ScriptInterface
operator|.
name|ScriptParameterInterface
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
name|Script
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
name|ScriptParameter
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
name|ScriptDAO
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

begin_class
annotation|@
name|Service
argument_list|(
literal|"org.unitime.timetable.gwt.shared.ScriptInterface$SaveOrUpdateScriptRpcRequest"
argument_list|)
specifier|public
class|class
name|SaveOrUpdateScriptBackend
implements|implements
name|GwtRpcImplementation
argument_list|<
name|SaveOrUpdateScriptRpcRequest
argument_list|,
name|ScriptInterface
argument_list|>
block|{
annotation|@
name|Override
annotation|@
name|PreAuthorize
argument_list|(
literal|"checkPermission('ScriptEdit')"
argument_list|)
specifier|public
name|ScriptInterface
name|execute
parameter_list|(
name|SaveOrUpdateScriptRpcRequest
name|request
parameter_list|,
name|SessionContext
name|context
parameter_list|)
block|{
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
init|=
name|ScriptDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|getSession
argument_list|()
decl_stmt|;
name|Long
name|scriptId
init|=
name|request
operator|.
name|getScript
argument_list|()
operator|.
name|getId
argument_list|()
decl_stmt|;
name|Script
name|script
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|request
operator|.
name|getScript
argument_list|()
operator|.
name|getId
argument_list|()
operator|==
literal|null
condition|)
block|{
name|script
operator|=
operator|new
name|Script
argument_list|()
expr_stmt|;
name|script
operator|.
name|setParameters
argument_list|(
operator|new
name|HashSet
argument_list|<
name|ScriptParameter
argument_list|>
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|script
operator|=
name|ScriptDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|get
argument_list|(
name|scriptId
argument_list|,
name|hibSession
argument_list|)
expr_stmt|;
block|}
name|script
operator|.
name|setName
argument_list|(
name|request
operator|.
name|getScript
argument_list|()
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
name|script
operator|.
name|setDescription
argument_list|(
name|request
operator|.
name|getScript
argument_list|()
operator|.
name|getDescription
argument_list|()
argument_list|)
expr_stmt|;
name|script
operator|.
name|setPermission
argument_list|(
name|request
operator|.
name|getScript
argument_list|()
operator|.
name|getPermission
argument_list|()
argument_list|)
expr_stmt|;
name|script
operator|.
name|setEngine
argument_list|(
name|request
operator|.
name|getScript
argument_list|()
operator|.
name|getEngine
argument_list|()
argument_list|)
expr_stmt|;
name|script
operator|.
name|setScript
argument_list|(
name|request
operator|.
name|getScript
argument_list|()
operator|.
name|getScript
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|request
operator|.
name|getScript
argument_list|()
operator|.
name|hasParameters
argument_list|()
condition|)
block|{
name|Map
argument_list|<
name|String
argument_list|,
name|ScriptParameter
argument_list|>
name|params
init|=
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|ScriptParameter
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|ScriptParameter
name|parameter
range|:
name|script
operator|.
name|getParameters
argument_list|()
control|)
name|params
operator|.
name|put
argument_list|(
name|parameter
operator|.
name|getName
argument_list|()
argument_list|,
name|parameter
argument_list|)
expr_stmt|;
for|for
control|(
name|ScriptParameterInterface
name|p
range|:
name|request
operator|.
name|getScript
argument_list|()
operator|.
name|getParameters
argument_list|()
control|)
block|{
name|ScriptParameter
name|parameter
init|=
name|params
operator|.
name|remove
argument_list|(
name|p
operator|.
name|getName
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|parameter
operator|==
literal|null
condition|)
block|{
name|parameter
operator|=
operator|new
name|ScriptParameter
argument_list|()
expr_stmt|;
name|parameter
operator|.
name|setName
argument_list|(
name|p
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
name|parameter
operator|.
name|setScript
argument_list|(
name|script
argument_list|)
expr_stmt|;
name|script
operator|.
name|getParameters
argument_list|()
operator|.
name|add
argument_list|(
name|parameter
argument_list|)
expr_stmt|;
block|}
name|parameter
operator|.
name|setType
argument_list|(
name|p
operator|.
name|getType
argument_list|()
argument_list|)
expr_stmt|;
name|parameter
operator|.
name|setLabel
argument_list|(
name|p
operator|.
name|getLabel
argument_list|()
argument_list|)
expr_stmt|;
name|parameter
operator|.
name|setDefaultValue
argument_list|(
name|p
operator|.
name|getDefaultValue
argument_list|()
argument_list|)
expr_stmt|;
block|}
for|for
control|(
name|ScriptParameter
name|parameter
range|:
name|params
operator|.
name|values
argument_list|()
control|)
block|{
name|hibSession
operator|.
name|delete
argument_list|(
name|parameter
argument_list|)
expr_stmt|;
name|script
operator|.
name|getParameters
argument_list|()
operator|.
name|remove
argument_list|(
name|parameter
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
for|for
control|(
name|Iterator
argument_list|<
name|ScriptParameter
argument_list|>
name|i
init|=
name|script
operator|.
name|getParameters
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
name|hibSession
operator|.
name|delete
argument_list|(
name|i
operator|.
name|next
argument_list|()
argument_list|)
expr_stmt|;
name|i
operator|.
name|remove
argument_list|()
expr_stmt|;
block|}
block|}
name|hibSession
operator|.
name|saveOrUpdate
argument_list|(
name|script
argument_list|)
expr_stmt|;
name|hibSession
operator|.
name|flush
argument_list|()
expr_stmt|;
return|return
name|LoadAllScriptsBackend
operator|.
name|load
argument_list|(
name|script
argument_list|,
name|context
argument_list|)
return|;
block|}
block|}
end_class

end_unit

