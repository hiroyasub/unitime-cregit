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
name|gwt
operator|.
name|shared
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|Serializable
import|;
end_import

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
name|Date
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
name|GwtRpcRequest
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
name|GwtRpcResponse
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
name|com
operator|.
name|google
operator|.
name|gwt
operator|.
name|user
operator|.
name|client
operator|.
name|rpc
operator|.
name|IsSerializable
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|ScriptInterface
implements|implements
name|GwtRpcResponse
implements|,
name|Comparable
argument_list|<
name|ScriptInterface
argument_list|>
block|{
specifier|private
name|Long
name|iId
decl_stmt|;
specifier|private
name|String
name|iName
decl_stmt|,
name|iDescription
decl_stmt|,
name|iEngine
decl_stmt|,
name|iPermission
decl_stmt|,
name|iScript
decl_stmt|;
specifier|private
name|List
argument_list|<
name|ScriptParameterInterface
argument_list|>
name|iParameters
decl_stmt|;
specifier|private
name|boolean
name|iCanEdit
init|=
literal|false
decl_stmt|,
name|iCanDelete
init|=
literal|false
decl_stmt|,
name|iCanExecute
decl_stmt|;
specifier|public
name|ScriptInterface
parameter_list|()
block|{
block|}
specifier|public
name|Long
name|getId
parameter_list|()
block|{
return|return
name|iId
return|;
block|}
specifier|public
name|void
name|setId
parameter_list|(
name|Long
name|id
parameter_list|)
block|{
name|iId
operator|=
name|id
expr_stmt|;
block|}
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
name|iName
return|;
block|}
specifier|public
name|void
name|setName
parameter_list|(
name|String
name|name
parameter_list|)
block|{
name|iName
operator|=
name|name
expr_stmt|;
block|}
specifier|public
name|String
name|getDescription
parameter_list|()
block|{
return|return
name|iDescription
return|;
block|}
specifier|public
name|void
name|setDescription
parameter_list|(
name|String
name|description
parameter_list|)
block|{
name|iDescription
operator|=
name|description
expr_stmt|;
block|}
specifier|public
name|String
name|getEngine
parameter_list|()
block|{
return|return
name|iEngine
return|;
block|}
specifier|public
name|void
name|setEngine
parameter_list|(
name|String
name|engine
parameter_list|)
block|{
name|iEngine
operator|=
name|engine
expr_stmt|;
block|}
specifier|public
name|String
name|getPermission
parameter_list|()
block|{
return|return
name|iPermission
return|;
block|}
specifier|public
name|void
name|setPermission
parameter_list|(
name|String
name|permission
parameter_list|)
block|{
name|iPermission
operator|=
name|permission
expr_stmt|;
block|}
specifier|public
name|String
name|getScript
parameter_list|()
block|{
return|return
name|iScript
return|;
block|}
specifier|public
name|void
name|setScript
parameter_list|(
name|String
name|script
parameter_list|)
block|{
name|iScript
operator|=
name|script
expr_stmt|;
block|}
specifier|public
name|boolean
name|hasParameters
parameter_list|()
block|{
return|return
name|iParameters
operator|!=
literal|null
operator|&&
operator|!
name|iParameters
operator|.
name|isEmpty
argument_list|()
return|;
block|}
specifier|public
name|List
argument_list|<
name|ScriptParameterInterface
argument_list|>
name|getParameters
parameter_list|()
block|{
return|return
name|iParameters
return|;
block|}
specifier|public
name|void
name|addParameter
parameter_list|(
name|ScriptParameterInterface
name|parameter
parameter_list|)
block|{
if|if
condition|(
name|iParameters
operator|==
literal|null
condition|)
name|iParameters
operator|=
operator|new
name|ArrayList
argument_list|<
name|ScriptParameterInterface
argument_list|>
argument_list|()
expr_stmt|;
name|iParameters
operator|.
name|add
argument_list|(
name|parameter
argument_list|)
expr_stmt|;
block|}
specifier|public
name|ScriptParameterInterface
name|getParameter
parameter_list|(
name|String
name|name
parameter_list|)
block|{
if|if
condition|(
name|iParameters
operator|==
literal|null
condition|)
return|return
literal|null
return|;
for|for
control|(
name|ScriptParameterInterface
name|p
range|:
name|iParameters
control|)
if|if
condition|(
name|name
operator|.
name|equals
argument_list|(
name|p
operator|.
name|getName
argument_list|()
argument_list|)
condition|)
return|return
name|p
return|;
return|return
literal|null
return|;
block|}
specifier|public
name|boolean
name|isFileParameter
parameter_list|(
name|String
name|name
parameter_list|)
block|{
if|if
condition|(
name|iParameters
operator|==
literal|null
condition|)
return|return
literal|false
return|;
for|for
control|(
name|ScriptParameterInterface
name|p
range|:
name|iParameters
control|)
if|if
condition|(
name|name
operator|.
name|equals
argument_list|(
name|p
operator|.
name|getName
argument_list|()
argument_list|)
condition|)
return|return
literal|"file"
operator|.
name|equalsIgnoreCase
argument_list|(
name|p
operator|.
name|getType
argument_list|()
argument_list|)
return|;
return|return
literal|false
return|;
block|}
specifier|public
name|boolean
name|canEdit
parameter_list|()
block|{
return|return
name|iCanEdit
return|;
block|}
specifier|public
name|void
name|setCanEdit
parameter_list|(
name|boolean
name|canEdit
parameter_list|)
block|{
name|iCanEdit
operator|=
name|canEdit
expr_stmt|;
block|}
specifier|public
name|boolean
name|canDelete
parameter_list|()
block|{
return|return
name|iCanDelete
return|;
block|}
specifier|public
name|void
name|setCanDelete
parameter_list|(
name|boolean
name|canDelete
parameter_list|)
block|{
name|iCanDelete
operator|=
name|canDelete
expr_stmt|;
block|}
specifier|public
name|boolean
name|canExecute
parameter_list|()
block|{
return|return
name|iCanExecute
return|;
block|}
specifier|public
name|void
name|setCanExecute
parameter_list|(
name|boolean
name|canExecute
parameter_list|)
block|{
name|iCanExecute
operator|=
name|canExecute
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|int
name|compareTo
parameter_list|(
name|ScriptInterface
name|o
parameter_list|)
block|{
return|return
name|getName
argument_list|()
operator|.
name|compareTo
argument_list|(
name|o
operator|.
name|getName
argument_list|()
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
name|getName
argument_list|()
return|;
block|}
specifier|public
specifier|static
class|class
name|ScriptParameterInterface
implements|implements
name|IsSerializable
implements|,
name|Comparable
argument_list|<
name|ScriptParameterInterface
argument_list|>
block|{
specifier|private
name|String
name|iName
decl_stmt|,
name|iLabel
decl_stmt|,
name|iType
decl_stmt|,
name|iValue
decl_stmt|,
name|iDefault
decl_stmt|;
specifier|private
name|Set
argument_list|<
name|ListItem
argument_list|>
name|iOptions
init|=
literal|null
decl_stmt|;
specifier|private
name|boolean
name|iMultiSelect
init|=
literal|false
decl_stmt|;
specifier|public
name|ScriptParameterInterface
parameter_list|()
block|{
block|}
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
name|iName
return|;
block|}
specifier|public
name|void
name|setName
parameter_list|(
name|String
name|name
parameter_list|)
block|{
name|iName
operator|=
name|name
expr_stmt|;
block|}
specifier|public
name|String
name|getLabel
parameter_list|()
block|{
return|return
name|iLabel
return|;
block|}
specifier|public
name|void
name|setLabel
parameter_list|(
name|String
name|label
parameter_list|)
block|{
name|iLabel
operator|=
name|label
expr_stmt|;
block|}
specifier|public
name|String
name|getType
parameter_list|()
block|{
return|return
name|iType
return|;
block|}
specifier|public
name|void
name|setType
parameter_list|(
name|String
name|type
parameter_list|)
block|{
name|iType
operator|=
name|type
expr_stmt|;
block|}
specifier|public
name|String
name|getValue
parameter_list|()
block|{
return|return
name|iValue
return|;
block|}
specifier|public
name|void
name|setValue
parameter_list|(
name|String
name|value
parameter_list|)
block|{
name|iValue
operator|=
name|value
expr_stmt|;
block|}
specifier|public
name|String
name|getDefaultValue
parameter_list|()
block|{
return|return
name|iDefault
return|;
block|}
specifier|public
name|void
name|setDefaultValue
parameter_list|(
name|String
name|defaultValue
parameter_list|)
block|{
name|iDefault
operator|=
name|defaultValue
expr_stmt|;
block|}
specifier|public
name|boolean
name|hasOptions
parameter_list|()
block|{
return|return
name|iOptions
operator|!=
literal|null
operator|&&
operator|!
name|iOptions
operator|.
name|isEmpty
argument_list|()
return|;
block|}
specifier|public
name|void
name|addOption
parameter_list|(
name|String
name|value
parameter_list|,
name|String
name|text
parameter_list|)
block|{
if|if
condition|(
name|iOptions
operator|==
literal|null
condition|)
name|iOptions
operator|=
operator|new
name|TreeSet
argument_list|<
name|ListItem
argument_list|>
argument_list|()
expr_stmt|;
name|iOptions
operator|.
name|add
argument_list|(
operator|new
name|ListItem
argument_list|(
name|value
argument_list|,
name|text
argument_list|)
argument_list|)
expr_stmt|;
block|}
specifier|public
name|Set
argument_list|<
name|ListItem
argument_list|>
name|getOptions
parameter_list|()
block|{
return|return
name|iOptions
return|;
block|}
specifier|public
name|String
name|getOption
parameter_list|(
name|String
name|key
parameter_list|)
block|{
if|if
condition|(
name|iOptions
operator|==
literal|null
operator|||
name|key
operator|==
literal|null
condition|)
return|return
name|key
return|;
for|for
control|(
name|ListItem
name|o
range|:
name|iOptions
control|)
if|if
condition|(
name|key
operator|.
name|equals
argument_list|(
name|o
operator|.
name|getValue
argument_list|()
argument_list|)
condition|)
return|return
name|o
operator|.
name|getText
argument_list|()
return|;
return|return
name|key
return|;
block|}
specifier|public
name|boolean
name|isMultiSelect
parameter_list|()
block|{
return|return
name|iMultiSelect
return|;
block|}
specifier|public
name|void
name|setMultiSelect
parameter_list|(
name|boolean
name|multiSelect
parameter_list|)
block|{
name|iMultiSelect
operator|=
name|multiSelect
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
name|getName
argument_list|()
operator|+
literal|"="
operator|+
operator|(
name|getValue
argument_list|()
operator|==
literal|null
condition|?
name|getDefaultValue
argument_list|()
else|:
name|getValue
argument_list|()
operator|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|int
name|compareTo
parameter_list|(
name|ScriptParameterInterface
name|o
parameter_list|)
block|{
name|int
name|cmp
init|=
name|getLabel
argument_list|()
operator|.
name|compareTo
argument_list|(
name|o
operator|.
name|getLabel
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|cmp
operator|!=
literal|0
condition|)
return|return
name|cmp
return|;
return|return
name|getName
argument_list|()
operator|.
name|compareTo
argument_list|(
name|o
operator|.
name|getName
argument_list|()
argument_list|)
return|;
block|}
block|}
specifier|public
specifier|static
class|class
name|ScriptOptionsInterface
implements|implements
name|GwtRpcResponse
block|{
specifier|private
name|List
argument_list|<
name|String
argument_list|>
name|iEngines
init|=
operator|new
name|ArrayList
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
specifier|private
name|List
argument_list|<
name|String
argument_list|>
name|iPermissions
init|=
operator|new
name|ArrayList
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
specifier|private
name|boolean
name|iCanAdd
decl_stmt|;
specifier|private
name|String
name|iEmail
decl_stmt|;
specifier|public
name|ScriptOptionsInterface
parameter_list|()
block|{
block|}
specifier|public
name|void
name|addEngine
parameter_list|(
name|String
name|engine
parameter_list|)
block|{
name|iEngines
operator|.
name|add
argument_list|(
name|engine
argument_list|)
expr_stmt|;
block|}
specifier|public
name|List
argument_list|<
name|String
argument_list|>
name|getEngines
parameter_list|()
block|{
return|return
name|iEngines
return|;
block|}
specifier|public
name|void
name|addPermission
parameter_list|(
name|String
name|permission
parameter_list|)
block|{
name|iPermissions
operator|.
name|add
argument_list|(
name|permission
argument_list|)
expr_stmt|;
block|}
specifier|public
name|List
argument_list|<
name|String
argument_list|>
name|getPermissions
parameter_list|()
block|{
return|return
name|iPermissions
return|;
block|}
specifier|public
name|boolean
name|canAdd
parameter_list|()
block|{
return|return
name|iCanAdd
return|;
block|}
specifier|public
name|void
name|setCanAdd
parameter_list|(
name|boolean
name|canAdd
parameter_list|)
block|{
name|iCanAdd
operator|=
name|canAdd
expr_stmt|;
block|}
specifier|public
name|void
name|setEmail
parameter_list|(
name|String
name|email
parameter_list|)
block|{
name|iEmail
operator|=
name|email
expr_stmt|;
block|}
specifier|public
name|boolean
name|hasEmail
parameter_list|()
block|{
return|return
name|iEmail
operator|!=
literal|null
operator|&&
operator|!
name|iEmail
operator|.
name|isEmpty
argument_list|()
return|;
block|}
specifier|public
name|String
name|getEmail
parameter_list|()
block|{
return|return
name|iEmail
return|;
block|}
block|}
specifier|public
specifier|static
class|class
name|GetScriptOptionsRpcRequest
implements|implements
name|GwtRpcRequest
argument_list|<
name|ScriptOptionsInterface
argument_list|>
block|{
specifier|public
name|GetScriptOptionsRpcRequest
parameter_list|()
block|{
block|}
annotation|@
name|Override
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
literal|""
return|;
block|}
block|}
specifier|public
specifier|static
class|class
name|LoadAllScriptsRpcRequest
implements|implements
name|GwtRpcRequest
argument_list|<
name|GwtRpcResponseList
argument_list|<
name|ScriptInterface
argument_list|>
argument_list|>
block|{
specifier|public
name|LoadAllScriptsRpcRequest
parameter_list|()
block|{
block|}
annotation|@
name|Override
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
literal|""
return|;
block|}
block|}
specifier|public
specifier|static
class|class
name|SaveOrUpdateScriptRpcRequest
implements|implements
name|GwtRpcRequest
argument_list|<
name|ScriptInterface
argument_list|>
block|{
specifier|private
name|ScriptInterface
name|iScript
decl_stmt|;
specifier|public
name|SaveOrUpdateScriptRpcRequest
parameter_list|()
block|{
block|}
specifier|public
name|SaveOrUpdateScriptRpcRequest
parameter_list|(
name|ScriptInterface
name|script
parameter_list|)
block|{
name|iScript
operator|=
name|script
expr_stmt|;
block|}
specifier|public
name|ScriptInterface
name|getScript
parameter_list|()
block|{
return|return
name|iScript
return|;
block|}
specifier|public
name|void
name|setScript
parameter_list|(
name|ScriptInterface
name|script
parameter_list|)
block|{
name|iScript
operator|=
name|script
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
name|getScript
argument_list|()
operator|.
name|toString
argument_list|()
return|;
block|}
block|}
specifier|public
specifier|static
class|class
name|DeleteScriptRpcRequest
implements|implements
name|GwtRpcRequest
argument_list|<
name|ScriptInterface
argument_list|>
block|{
specifier|private
name|Long
name|iId
decl_stmt|;
specifier|private
name|String
name|iName
decl_stmt|;
specifier|public
name|DeleteScriptRpcRequest
parameter_list|()
block|{
block|}
specifier|public
name|DeleteScriptRpcRequest
parameter_list|(
name|Long
name|id
parameter_list|,
name|String
name|name
parameter_list|)
block|{
name|iId
operator|=
name|id
expr_stmt|;
name|iName
operator|=
name|name
expr_stmt|;
block|}
specifier|public
name|Long
name|getScriptId
parameter_list|()
block|{
return|return
name|iId
return|;
block|}
specifier|public
name|void
name|setScriptId
parameter_list|(
name|Long
name|id
parameter_list|)
block|{
name|iId
operator|=
name|id
expr_stmt|;
block|}
specifier|public
name|String
name|getScriptName
parameter_list|()
block|{
return|return
name|iName
return|;
block|}
specifier|public
name|void
name|setScriptName
parameter_list|(
name|String
name|name
parameter_list|)
block|{
name|iName
operator|=
name|name
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
name|getScriptName
argument_list|()
return|;
block|}
block|}
specifier|public
specifier|static
class|class
name|ExecuteScriptRpcRequest
implements|implements
name|GwtRpcRequest
argument_list|<
name|QueueItemInterface
argument_list|>
implements|,
name|Serializable
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
name|Long
name|iId
decl_stmt|;
specifier|private
name|String
name|iName
decl_stmt|;
specifier|private
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|iParameters
init|=
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
argument_list|()
decl_stmt|;
specifier|private
name|String
name|iEmail
decl_stmt|;
specifier|public
name|ExecuteScriptRpcRequest
parameter_list|()
block|{
block|}
specifier|public
name|Long
name|getScriptId
parameter_list|()
block|{
return|return
name|iId
return|;
block|}
specifier|public
name|void
name|setScriptId
parameter_list|(
name|Long
name|id
parameter_list|)
block|{
name|iId
operator|=
name|id
expr_stmt|;
block|}
specifier|public
name|String
name|getScriptName
parameter_list|()
block|{
return|return
name|iName
return|;
block|}
specifier|public
name|void
name|setScriptName
parameter_list|(
name|String
name|name
parameter_list|)
block|{
name|iName
operator|=
name|name
expr_stmt|;
block|}
specifier|public
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|getParameters
parameter_list|()
block|{
return|return
name|iParameters
return|;
block|}
specifier|public
name|void
name|setParameter
parameter_list|(
name|String
name|name
parameter_list|,
name|String
name|value
parameter_list|)
block|{
name|iParameters
operator|.
name|put
argument_list|(
name|name
argument_list|,
name|value
argument_list|)
expr_stmt|;
block|}
specifier|public
name|boolean
name|hasEmail
parameter_list|()
block|{
return|return
name|iEmail
operator|!=
literal|null
operator|&&
operator|!
name|iEmail
operator|.
name|isEmpty
argument_list|()
return|;
block|}
specifier|public
name|String
name|getEmail
parameter_list|()
block|{
return|return
name|iEmail
return|;
block|}
specifier|public
name|void
name|setEmail
parameter_list|(
name|String
name|email
parameter_list|)
block|{
name|iEmail
operator|=
name|email
expr_stmt|;
block|}
specifier|public
specifier|static
name|ExecuteScriptRpcRequest
name|executeScript
parameter_list|(
name|Long
name|scriptId
parameter_list|,
name|String
name|scriptName
parameter_list|,
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|parameters
parameter_list|,
name|String
name|email
parameter_list|)
block|{
name|ExecuteScriptRpcRequest
name|request
init|=
operator|new
name|ExecuteScriptRpcRequest
argument_list|()
decl_stmt|;
name|request
operator|.
name|setScriptId
argument_list|(
name|scriptId
argument_list|)
expr_stmt|;
name|request
operator|.
name|setScriptName
argument_list|(
name|scriptName
argument_list|)
expr_stmt|;
if|if
condition|(
name|parameters
operator|!=
literal|null
condition|)
for|for
control|(
name|Map
operator|.
name|Entry
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|e
range|:
name|parameters
operator|.
name|entrySet
argument_list|()
control|)
name|request
operator|.
name|setParameter
argument_list|(
name|e
operator|.
name|getKey
argument_list|()
argument_list|,
name|e
operator|.
name|getValue
argument_list|()
argument_list|)
expr_stmt|;
name|request
operator|.
name|setEmail
argument_list|(
name|email
argument_list|)
expr_stmt|;
return|return
name|request
return|;
block|}
specifier|public
specifier|static
name|ExecuteScriptRpcRequest
name|executeScript
parameter_list|(
name|ScriptInterface
name|script
parameter_list|)
block|{
name|ExecuteScriptRpcRequest
name|request
init|=
operator|new
name|ExecuteScriptRpcRequest
argument_list|()
decl_stmt|;
name|request
operator|.
name|setScriptId
argument_list|(
name|script
operator|.
name|getId
argument_list|()
argument_list|)
expr_stmt|;
name|request
operator|.
name|setScriptName
argument_list|(
name|script
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|script
operator|.
name|hasParameters
argument_list|()
condition|)
for|for
control|(
name|ScriptParameterInterface
name|parameter
range|:
name|script
operator|.
name|getParameters
argument_list|()
control|)
if|if
condition|(
name|parameter
operator|.
name|getValue
argument_list|()
operator|!=
literal|null
operator|||
name|parameter
operator|.
name|getDefaultValue
argument_list|()
operator|!=
literal|null
condition|)
name|request
operator|.
name|setParameter
argument_list|(
name|parameter
operator|.
name|getName
argument_list|()
argument_list|,
name|parameter
operator|.
name|getValue
argument_list|()
operator|==
literal|null
condition|?
name|parameter
operator|.
name|getDefaultValue
argument_list|()
else|:
name|parameter
operator|.
name|getValue
argument_list|()
argument_list|)
expr_stmt|;
return|return
name|request
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
name|getScriptName
argument_list|()
operator|+
name|getParameters
argument_list|()
return|;
block|}
block|}
specifier|public
specifier|static
class|class
name|ListItem
implements|implements
name|IsSerializable
implements|,
name|Comparable
argument_list|<
name|ListItem
argument_list|>
block|{
specifier|private
name|String
name|iValue
decl_stmt|,
name|iText
decl_stmt|;
specifier|public
name|ListItem
parameter_list|()
block|{
block|}
specifier|public
name|ListItem
parameter_list|(
name|String
name|value
parameter_list|,
name|String
name|text
parameter_list|)
block|{
name|iValue
operator|=
name|value
expr_stmt|;
name|iText
operator|=
name|text
expr_stmt|;
block|}
specifier|public
name|String
name|getValue
parameter_list|()
block|{
return|return
name|iValue
return|;
block|}
specifier|public
name|String
name|getText
parameter_list|()
block|{
return|return
name|iText
return|;
block|}
annotation|@
name|Override
specifier|public
name|int
name|compareTo
parameter_list|(
name|ListItem
name|item
parameter_list|)
block|{
name|int
name|cmp
init|=
name|getText
argument_list|()
operator|.
name|compareTo
argument_list|(
name|item
operator|.
name|getText
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|cmp
operator|!=
literal|0
condition|)
return|return
name|cmp
return|;
return|return
name|getValue
argument_list|()
operator|.
name|compareTo
argument_list|(
name|item
operator|.
name|getValue
argument_list|()
argument_list|)
return|;
block|}
block|}
specifier|public
specifier|static
class|class
name|QueueItemInterface
implements|implements
name|GwtRpcResponse
block|{
specifier|private
name|String
name|iId
decl_stmt|;
specifier|private
name|String
name|iName
decl_stmt|,
name|iStatus
decl_stmt|,
name|iProgress
decl_stmt|,
name|iOwner
decl_stmt|,
name|iSession
decl_stmt|,
name|iOutput
decl_stmt|,
name|iLog
decl_stmt|,
name|iHost
decl_stmt|,
name|iOutputLink
decl_stmt|;
specifier|private
name|Date
name|iCreated
decl_stmt|,
name|iStarted
decl_stmt|,
name|iFinished
decl_stmt|;
specifier|private
name|boolean
name|iCanDelete
init|=
literal|false
decl_stmt|;
specifier|private
name|ExecuteScriptRpcRequest
name|iExecutionRequest
decl_stmt|;
specifier|public
name|QueueItemInterface
parameter_list|()
block|{
block|}
specifier|public
name|String
name|getId
parameter_list|()
block|{
return|return
name|iId
return|;
block|}
specifier|public
name|void
name|setId
parameter_list|(
name|String
name|id
parameter_list|)
block|{
name|iId
operator|=
name|id
expr_stmt|;
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
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
name|iName
return|;
block|}
specifier|public
name|void
name|setName
parameter_list|(
name|String
name|name
parameter_list|)
block|{
name|iName
operator|=
name|name
expr_stmt|;
block|}
specifier|public
name|String
name|getStatus
parameter_list|()
block|{
return|return
name|iStatus
return|;
block|}
specifier|public
name|void
name|setStatus
parameter_list|(
name|String
name|status
parameter_list|)
block|{
name|iStatus
operator|=
name|status
expr_stmt|;
block|}
specifier|public
name|String
name|getProgress
parameter_list|()
block|{
return|return
name|iProgress
return|;
block|}
specifier|public
name|void
name|setProgress
parameter_list|(
name|String
name|progress
parameter_list|)
block|{
name|iProgress
operator|=
name|progress
expr_stmt|;
block|}
specifier|public
name|String
name|getOwner
parameter_list|()
block|{
return|return
name|iOwner
return|;
block|}
specifier|public
name|void
name|setOwner
parameter_list|(
name|String
name|owner
parameter_list|)
block|{
name|iOwner
operator|=
name|owner
expr_stmt|;
block|}
specifier|public
name|String
name|getSession
parameter_list|()
block|{
return|return
name|iSession
return|;
block|}
specifier|public
name|void
name|setSession
parameter_list|(
name|String
name|session
parameter_list|)
block|{
name|iSession
operator|=
name|session
expr_stmt|;
block|}
specifier|public
name|String
name|getOtuput
parameter_list|()
block|{
return|return
name|iOutput
return|;
block|}
specifier|public
name|void
name|setOutput
parameter_list|(
name|String
name|output
parameter_list|)
block|{
name|iOutput
operator|=
name|output
expr_stmt|;
block|}
specifier|public
name|String
name|getOtuputLink
parameter_list|()
block|{
return|return
name|iOutputLink
return|;
block|}
specifier|public
name|void
name|setOutputLink
parameter_list|(
name|String
name|outputLink
parameter_list|)
block|{
name|iOutputLink
operator|=
name|outputLink
expr_stmt|;
block|}
specifier|public
name|Date
name|getCreated
parameter_list|()
block|{
return|return
name|iCreated
return|;
block|}
specifier|public
name|void
name|setCreated
parameter_list|(
name|Date
name|created
parameter_list|)
block|{
name|iCreated
operator|=
name|created
expr_stmt|;
block|}
specifier|public
name|Date
name|getStarted
parameter_list|()
block|{
return|return
name|iStarted
return|;
block|}
specifier|public
name|void
name|setStarted
parameter_list|(
name|Date
name|started
parameter_list|)
block|{
name|iStarted
operator|=
name|started
expr_stmt|;
block|}
specifier|public
name|Date
name|getFinished
parameter_list|()
block|{
return|return
name|iFinished
return|;
block|}
specifier|public
name|void
name|setFinished
parameter_list|(
name|Date
name|finished
parameter_list|)
block|{
name|iFinished
operator|=
name|finished
expr_stmt|;
block|}
specifier|public
name|String
name|getLog
parameter_list|()
block|{
return|return
name|iLog
return|;
block|}
specifier|public
name|void
name|setLog
parameter_list|(
name|String
name|log
parameter_list|)
block|{
name|iLog
operator|=
name|log
expr_stmt|;
block|}
specifier|public
name|void
name|setCanDelete
parameter_list|(
name|boolean
name|canDelete
parameter_list|)
block|{
name|iCanDelete
operator|=
name|canDelete
expr_stmt|;
block|}
specifier|public
name|boolean
name|isCanDelete
parameter_list|()
block|{
return|return
name|iCanDelete
return|;
block|}
specifier|public
name|void
name|setExecutionRequest
parameter_list|(
name|ExecuteScriptRpcRequest
name|request
parameter_list|)
block|{
name|iExecutionRequest
operator|=
name|request
expr_stmt|;
block|}
specifier|public
name|ExecuteScriptRpcRequest
name|getExecutionRequest
parameter_list|()
block|{
return|return
name|iExecutionRequest
return|;
block|}
block|}
specifier|public
specifier|static
class|class
name|GetQueueTableRpcRequest
implements|implements
name|GwtRpcRequest
argument_list|<
name|GwtRpcResponseList
argument_list|<
name|QueueItemInterface
argument_list|>
argument_list|>
block|{
specifier|private
name|String
name|iDeleteId
init|=
literal|null
decl_stmt|;
specifier|public
name|GetQueueTableRpcRequest
parameter_list|()
block|{
block|}
specifier|public
name|GetQueueTableRpcRequest
parameter_list|(
name|String
name|deleteId
parameter_list|)
block|{
name|iDeleteId
operator|=
name|deleteId
expr_stmt|;
block|}
specifier|public
name|String
name|getDeleteId
parameter_list|()
block|{
return|return
name|iDeleteId
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
operator|(
name|iDeleteId
operator|==
literal|null
condition|?
literal|""
else|:
name|iDeleteId
operator|)
return|;
block|}
block|}
block|}
end_class

end_unit

