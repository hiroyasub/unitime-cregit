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
name|Collections
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
name|springframework
operator|.
name|web
operator|.
name|util
operator|.
name|HtmlUtils
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
name|client
operator|.
name|GwtRpcResponseList
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
name|LoadAllScriptsRpcRequest
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
name|Department
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
name|RefTableEntry
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
name|SubjectArea
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
name|RefTableEntryDAO
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

begin_class
annotation|@
name|Service
argument_list|(
literal|"org.unitime.timetable.gwt.shared.ScriptInterface$LoadAllScriptsRpcRequest"
argument_list|)
specifier|public
class|class
name|LoadAllScriptsBackend
implements|implements
name|GwtRpcImplementation
argument_list|<
name|LoadAllScriptsRpcRequest
argument_list|,
name|GwtRpcResponseList
argument_list|<
name|ScriptInterface
argument_list|>
argument_list|>
block|{
annotation|@
name|Override
annotation|@
name|PreAuthorize
argument_list|(
literal|"checkPermission('Scripts')"
argument_list|)
specifier|public
name|GwtRpcResponseList
argument_list|<
name|ScriptInterface
argument_list|>
name|execute
parameter_list|(
name|LoadAllScriptsRpcRequest
name|request
parameter_list|,
name|SessionContext
name|context
parameter_list|)
block|{
name|GwtRpcResponseList
argument_list|<
name|ScriptInterface
argument_list|>
name|list
init|=
operator|new
name|GwtRpcResponseList
argument_list|<
name|ScriptInterface
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|Script
name|s
range|:
name|ScriptDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|findAll
argument_list|()
control|)
block|{
name|ScriptInterface
name|script
init|=
name|load
argument_list|(
name|s
argument_list|,
name|context
argument_list|)
decl_stmt|;
if|if
condition|(
name|script
operator|!=
literal|null
condition|)
name|list
operator|.
name|add
argument_list|(
name|script
argument_list|)
expr_stmt|;
block|}
name|Collections
operator|.
name|sort
argument_list|(
name|list
argument_list|)
expr_stmt|;
return|return
name|list
return|;
block|}
specifier|public
specifier|static
name|ScriptInterface
name|load
parameter_list|(
name|Script
name|s
parameter_list|,
name|SessionContext
name|context
parameter_list|)
block|{
name|ScriptInterface
name|script
init|=
operator|new
name|ScriptInterface
argument_list|()
decl_stmt|;
name|script
operator|.
name|setId
argument_list|(
name|s
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
name|script
operator|.
name|setName
argument_list|(
name|s
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
name|script
operator|.
name|setEngine
argument_list|(
name|s
operator|.
name|getEngine
argument_list|()
argument_list|)
expr_stmt|;
name|script
operator|.
name|setDescription
argument_list|(
name|s
operator|.
name|getDescription
argument_list|()
argument_list|)
expr_stmt|;
name|script
operator|.
name|setPermission
argument_list|(
name|s
operator|.
name|getPermission
argument_list|()
argument_list|)
expr_stmt|;
name|script
operator|.
name|setScript
argument_list|(
name|s
operator|.
name|getScript
argument_list|()
argument_list|)
expr_stmt|;
name|script
operator|.
name|setCanDelete
argument_list|(
name|context
operator|.
name|hasPermission
argument_list|(
name|Right
operator|.
name|ScriptEdit
argument_list|)
argument_list|)
expr_stmt|;
name|script
operator|.
name|setCanEdit
argument_list|(
name|context
operator|.
name|hasPermission
argument_list|(
name|Right
operator|.
name|ScriptEdit
argument_list|)
argument_list|)
expr_stmt|;
name|Right
name|right
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|s
operator|.
name|getPermission
argument_list|()
operator|==
literal|null
condition|)
block|{
name|script
operator|.
name|setCanExecute
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
else|else
block|{
try|try
block|{
name|right
operator|=
name|Right
operator|.
name|valueOf
argument_list|(
name|s
operator|.
name|getPermission
argument_list|()
operator|.
name|replace
argument_list|(
literal|" "
argument_list|,
literal|""
argument_list|)
argument_list|)
expr_stmt|;
name|script
operator|.
name|setCanExecute
argument_list|(
name|context
operator|.
name|hasPermission
argument_list|(
name|right
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IllegalArgumentException
name|e
parameter_list|)
block|{
name|script
operator|.
name|setCanExecute
argument_list|(
literal|false
argument_list|)
expr_stmt|;
block|}
block|}
for|for
control|(
name|ScriptParameter
name|p
range|:
name|s
operator|.
name|getParameters
argument_list|()
control|)
block|{
name|ScriptParameterInterface
name|parameter
init|=
operator|new
name|ScriptParameterInterface
argument_list|()
decl_stmt|;
name|parameter
operator|.
name|setLabel
argument_list|(
name|p
operator|.
name|getLabel
argument_list|()
operator|==
literal|null
condition|?
name|p
operator|.
name|getName
argument_list|()
else|:
name|p
operator|.
name|getLabel
argument_list|()
argument_list|)
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
name|setDefaultValue
argument_list|(
name|p
operator|.
name|getDefaultValue
argument_list|()
argument_list|)
expr_stmt|;
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
if|if
condition|(
name|p
operator|.
name|getType
argument_list|()
operator|.
name|startsWith
argument_list|(
literal|"enum("
argument_list|)
operator|&&
name|p
operator|.
name|getType
argument_list|()
operator|.
name|endsWith
argument_list|(
literal|")"
argument_list|)
condition|)
block|{
for|for
control|(
name|String
name|option
range|:
name|p
operator|.
name|getType
argument_list|()
operator|.
name|substring
argument_list|(
literal|"enum("
operator|.
name|length
argument_list|()
argument_list|,
name|p
operator|.
name|getType
argument_list|()
operator|.
name|length
argument_list|()
operator|-
literal|1
argument_list|)
operator|.
name|split
argument_list|(
literal|","
argument_list|)
control|)
name|parameter
operator|.
name|addOption
argument_list|(
name|option
argument_list|,
name|option
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|p
operator|.
name|getType
argument_list|()
operator|.
name|startsWith
argument_list|(
literal|"reference("
argument_list|)
operator|&&
name|p
operator|.
name|getType
argument_list|()
operator|.
name|endsWith
argument_list|(
literal|")"
argument_list|)
condition|)
block|{
try|try
block|{
name|String
name|clazz
init|=
name|p
operator|.
name|getType
argument_list|()
operator|.
name|substring
argument_list|(
literal|"reference("
operator|.
name|length
argument_list|()
argument_list|,
name|p
operator|.
name|getType
argument_list|()
operator|.
name|length
argument_list|()
operator|-
literal|1
argument_list|)
decl_stmt|;
for|for
control|(
name|RefTableEntry
name|entry
range|:
operator|(
name|List
argument_list|<
name|RefTableEntry
argument_list|>
operator|)
name|RefTableEntryDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"from "
operator|+
name|clazz
argument_list|)
operator|.
name|setCacheable
argument_list|(
literal|true
argument_list|)
operator|.
name|list
argument_list|()
control|)
block|{
name|parameter
operator|.
name|addOption
argument_list|(
name|entry
operator|.
name|getReference
argument_list|()
argument_list|,
name|entry
operator|.
name|getLabel
argument_list|()
argument_list|)
expr_stmt|;
block|}
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
block|}
block|}
if|else if
condition|(
name|p
operator|.
name|getType
argument_list|()
operator|.
name|equalsIgnoreCase
argument_list|(
literal|"department"
argument_list|)
operator|||
name|p
operator|.
name|getType
argument_list|()
operator|.
name|equalsIgnoreCase
argument_list|(
literal|"departments"
argument_list|)
condition|)
block|{
if|if
condition|(
name|p
operator|.
name|getType
argument_list|()
operator|.
name|equalsIgnoreCase
argument_list|(
literal|"departments"
argument_list|)
condition|)
name|parameter
operator|.
name|setMultiSelect
argument_list|(
literal|true
argument_list|)
expr_stmt|;
for|for
control|(
name|Department
name|department
range|:
name|Department
operator|.
name|getUserDepartments
argument_list|(
name|context
operator|.
name|getUser
argument_list|()
argument_list|)
control|)
block|{
if|if
condition|(
name|right
operator|!=
literal|null
operator|&&
name|Department
operator|.
name|class
operator|.
name|equals
argument_list|(
name|right
operator|.
name|type
argument_list|()
argument_list|)
operator|&&
operator|!
name|context
operator|.
name|hasPermission
argument_list|(
name|department
argument_list|,
name|right
argument_list|)
condition|)
continue|continue;
name|parameter
operator|.
name|addOption
argument_list|(
name|department
operator|.
name|getUniqueId
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|,
name|department
operator|.
name|getDeptCode
argument_list|()
operator|+
literal|" - "
operator|+
name|department
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
if|else if
condition|(
name|p
operator|.
name|getType
argument_list|()
operator|.
name|equalsIgnoreCase
argument_list|(
literal|"subject"
argument_list|)
operator|||
name|p
operator|.
name|getType
argument_list|()
operator|.
name|equalsIgnoreCase
argument_list|(
literal|"subjects"
argument_list|)
condition|)
block|{
if|if
condition|(
name|p
operator|.
name|getType
argument_list|()
operator|.
name|equalsIgnoreCase
argument_list|(
literal|"subjects"
argument_list|)
condition|)
name|parameter
operator|.
name|setMultiSelect
argument_list|(
literal|true
argument_list|)
expr_stmt|;
for|for
control|(
name|SubjectArea
name|subject
range|:
name|SubjectArea
operator|.
name|getUserSubjectAreas
argument_list|(
name|context
operator|.
name|getUser
argument_list|()
argument_list|)
control|)
block|{
if|if
condition|(
name|right
operator|!=
literal|null
operator|&&
name|SubjectArea
operator|.
name|class
operator|.
name|equals
argument_list|(
name|right
operator|.
name|type
argument_list|()
argument_list|)
operator|&&
operator|!
name|context
operator|.
name|hasPermission
argument_list|(
name|subject
argument_list|,
name|right
argument_list|)
condition|)
continue|continue;
name|parameter
operator|.
name|addOption
argument_list|(
name|subject
operator|.
name|getUniqueId
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|,
name|subject
operator|.
name|getSubjectAreaAbbreviation
argument_list|()
operator|+
literal|" - "
operator|+
name|HtmlUtils
operator|.
name|htmlUnescape
argument_list|(
name|subject
operator|.
name|getLongTitle
argument_list|()
operator|==
literal|null
condition|?
name|subject
operator|.
name|getShortTitle
argument_list|()
else|:
name|subject
operator|.
name|getLongTitle
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
name|script
operator|.
name|addParameter
argument_list|(
name|parameter
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|script
operator|.
name|hasParameters
argument_list|()
condition|)
name|Collections
operator|.
name|sort
argument_list|(
name|script
operator|.
name|getParameters
argument_list|()
argument_list|)
expr_stmt|;
return|return
name|script
return|;
block|}
block|}
end_class

end_unit

