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
name|commons
operator|.
name|jgroups
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
name|org
operator|.
name|jgroups
operator|.
name|conf
operator|.
name|ConfiguratorFactory
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jgroups
operator|.
name|conf
operator|.
name|ProtocolConfiguration
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jgroups
operator|.
name|conf
operator|.
name|ProtocolStackConfigurator
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jgroups
operator|.
name|util
operator|.
name|Util
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

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|JGroupsUtils
block|{
specifier|public
specifier|static
name|String
name|getProperty
parameter_list|(
name|String
name|s
parameter_list|)
block|{
name|String
name|var
decl_stmt|,
name|default_val
decl_stmt|,
name|retval
init|=
literal|null
decl_stmt|;
name|int
name|index
init|=
name|s
operator|.
name|indexOf
argument_list|(
literal|":"
argument_list|)
decl_stmt|;
if|if
condition|(
name|index
operator|>=
literal|0
condition|)
block|{
name|var
operator|=
name|s
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|index
argument_list|)
expr_stmt|;
name|default_val
operator|=
name|s
operator|.
name|substring
argument_list|(
name|index
operator|+
literal|1
argument_list|)
expr_stmt|;
if|if
condition|(
name|default_val
operator|!=
literal|null
operator|&&
name|default_val
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
name|default_val
operator|=
name|default_val
operator|.
name|trim
argument_list|()
expr_stmt|;
comment|// retval=System.getProperty(var, default_val);
name|retval
operator|=
name|_getProperty
argument_list|(
name|var
argument_list|,
name|default_val
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|var
operator|=
name|s
expr_stmt|;
comment|// retval=System.getProperty(var);
name|retval
operator|=
name|_getProperty
argument_list|(
name|var
argument_list|,
literal|null
argument_list|)
expr_stmt|;
block|}
return|return
name|retval
return|;
block|}
specifier|private
specifier|static
name|String
name|_getProperty
parameter_list|(
name|String
name|var
parameter_list|,
name|String
name|default_value
parameter_list|)
block|{
if|if
condition|(
name|var
operator|==
literal|null
condition|)
return|return
literal|null
return|;
name|List
argument_list|<
name|String
argument_list|>
name|list
init|=
name|Util
operator|.
name|parseCommaDelimitedStrings
argument_list|(
name|var
argument_list|)
decl_stmt|;
if|if
condition|(
name|list
operator|==
literal|null
operator|||
name|list
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|list
operator|=
operator|new
name|ArrayList
argument_list|<
name|String
argument_list|>
argument_list|(
literal|1
argument_list|)
expr_stmt|;
name|list
operator|.
name|add
argument_list|(
name|var
argument_list|)
expr_stmt|;
block|}
name|String
name|retval
init|=
literal|null
decl_stmt|;
for|for
control|(
name|String
name|prop
range|:
name|list
control|)
block|{
try|try
block|{
name|retval
operator|=
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
name|prop
argument_list|)
expr_stmt|;
if|if
condition|(
name|retval
operator|!=
literal|null
condition|)
return|return
name|retval
return|;
block|}
catch|catch
parameter_list|(
name|Throwable
name|e
parameter_list|)
block|{
block|}
block|}
return|return
name|default_value
return|;
block|}
specifier|private
specifier|static
name|String
name|_substituteVar
parameter_list|(
name|String
name|val
parameter_list|)
block|{
name|int
name|start_index
decl_stmt|,
name|end_index
decl_stmt|;
name|start_index
operator|=
name|val
operator|.
name|indexOf
argument_list|(
literal|"${"
argument_list|)
expr_stmt|;
if|if
condition|(
name|start_index
operator|==
operator|-
literal|1
condition|)
return|return
name|val
return|;
name|end_index
operator|=
name|val
operator|.
name|indexOf
argument_list|(
literal|"}"
argument_list|,
name|start_index
operator|+
literal|2
argument_list|)
expr_stmt|;
if|if
condition|(
name|end_index
operator|==
operator|-
literal|1
condition|)
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"missing \"}\" in "
operator|+
name|val
argument_list|)
throw|;
name|String
name|tmp
init|=
name|getProperty
argument_list|(
name|val
operator|.
name|substring
argument_list|(
name|start_index
operator|+
literal|2
argument_list|,
name|end_index
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|tmp
operator|==
literal|null
condition|)
return|return
name|val
return|;
name|StringBuilder
name|sb
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|val
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|start_index
argument_list|)
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|tmp
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|val
operator|.
name|substring
argument_list|(
name|end_index
operator|+
literal|1
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|sb
operator|.
name|toString
argument_list|()
return|;
block|}
specifier|public
specifier|static
name|String
name|substituteVariable
parameter_list|(
name|String
name|val
parameter_list|)
block|{
if|if
condition|(
name|val
operator|==
literal|null
condition|)
return|return
name|val
return|;
name|String
name|retval
init|=
name|val
decl_stmt|,
name|prev
decl_stmt|;
while|while
condition|(
name|retval
operator|.
name|contains
argument_list|(
literal|"${"
argument_list|)
condition|)
block|{
comment|// handle multiple variables in val
name|prev
operator|=
name|retval
expr_stmt|;
name|retval
operator|=
name|_substituteVar
argument_list|(
name|retval
argument_list|)
expr_stmt|;
if|if
condition|(
name|retval
operator|.
name|equals
argument_list|(
name|prev
argument_list|)
condition|)
break|break;
block|}
return|return
name|retval
return|;
block|}
specifier|public
specifier|static
name|void
name|substituteVariables
parameter_list|(
name|ProtocolConfiguration
name|config
parameter_list|)
block|{
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|properties
init|=
name|config
operator|.
name|getProperties
argument_list|()
decl_stmt|;
for|for
control|(
name|Iterator
argument_list|<
name|Map
operator|.
name|Entry
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
argument_list|>
name|it
init|=
name|properties
operator|.
name|entrySet
argument_list|()
operator|.
name|iterator
argument_list|()
init|;
name|it
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|Map
operator|.
name|Entry
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|entry
init|=
name|it
operator|.
name|next
argument_list|()
decl_stmt|;
name|String
name|key
init|=
name|entry
operator|.
name|getKey
argument_list|()
decl_stmt|;
name|String
name|val
init|=
name|entry
operator|.
name|getValue
argument_list|()
decl_stmt|;
name|String
name|tmp
init|=
name|substituteVariable
argument_list|(
name|val
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|val
operator|.
name|equals
argument_list|(
name|tmp
argument_list|)
condition|)
block|{
name|properties
operator|.
name|put
argument_list|(
name|key
argument_list|,
name|tmp
argument_list|)
expr_stmt|;
block|}
block|}
block|}
specifier|public
specifier|static
name|void
name|substituteVariables
parameter_list|(
name|ProtocolStackConfigurator
name|configurator
parameter_list|)
block|{
for|for
control|(
name|ProtocolConfiguration
name|config
range|:
name|configurator
operator|.
name|getProtocolStack
argument_list|()
control|)
name|substituteVariables
argument_list|(
name|config
argument_list|)
expr_stmt|;
block|}
specifier|public
specifier|static
name|ProtocolStackConfigurator
name|getConfigurator
parameter_list|(
name|String
name|properties
parameter_list|)
throws|throws
name|Exception
block|{
name|ProtocolStackConfigurator
name|configurator
init|=
name|ConfiguratorFactory
operator|.
name|getStackConfigurator
argument_list|(
name|properties
argument_list|)
decl_stmt|;
name|JGroupsUtils
operator|.
name|substituteVariables
argument_list|(
name|configurator
argument_list|)
expr_stmt|;
return|return
name|configurator
return|;
block|}
specifier|public
specifier|static
name|String
name|getConfigurationString
parameter_list|(
name|String
name|properties
parameter_list|)
throws|throws
name|Exception
block|{
name|ProtocolStackConfigurator
name|configurator
init|=
name|ConfiguratorFactory
operator|.
name|getStackConfigurator
argument_list|(
name|properties
argument_list|)
decl_stmt|;
name|StringBuffer
name|ret
init|=
operator|new
name|StringBuffer
argument_list|()
decl_stmt|;
for|for
control|(
name|ProtocolConfiguration
name|config
range|:
name|configurator
operator|.
name|getProtocolStack
argument_list|()
control|)
block|{
name|substituteVariables
argument_list|(
name|config
argument_list|)
expr_stmt|;
if|if
condition|(
name|ret
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
name|ret
operator|.
name|append
argument_list|(
literal|":"
argument_list|)
expr_stmt|;
name|ret
operator|.
name|append
argument_list|(
name|config
operator|.
name|getProtocolString
argument_list|()
argument_list|)
expr_stmt|;
block|}
return|return
name|ret
operator|.
name|toString
argument_list|()
return|;
block|}
block|}
end_class

end_unit

