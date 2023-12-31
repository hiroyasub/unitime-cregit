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
name|List
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
name|FilterInterface
implements|implements
name|GwtRpcResponse
implements|,
name|Serializable
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|0l
decl_stmt|;
specifier|private
name|List
argument_list|<
name|FilterParameterInterface
argument_list|>
name|iParameters
decl_stmt|;
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
name|FilterParameterInterface
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
name|FilterParameterInterface
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
name|FilterParameterInterface
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
name|FilterParameterInterface
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
name|FilterParameterInterface
name|param
range|:
name|iParameters
control|)
if|if
condition|(
name|name
operator|.
name|equals
argument_list|(
name|param
operator|.
name|getName
argument_list|()
argument_list|)
condition|)
return|return
name|param
return|;
return|return
literal|null
return|;
block|}
specifier|public
name|String
name|getParameterValue
parameter_list|(
name|String
name|name
parameter_list|)
block|{
name|FilterParameterInterface
name|param
init|=
name|getParameter
argument_list|(
name|name
argument_list|)
decl_stmt|;
return|return
operator|(
name|param
operator|==
literal|null
condition|?
literal|null
else|:
name|param
operator|.
name|getValue
argument_list|()
operator|==
literal|null
condition|?
name|param
operator|.
name|getDefaultValue
argument_list|()
else|:
name|param
operator|.
name|getValue
argument_list|()
operator|)
return|;
block|}
specifier|public
name|String
name|getParameterValue
parameter_list|(
name|String
name|name
parameter_list|,
name|String
name|defaultValue
parameter_list|)
block|{
name|String
name|value
init|=
name|getParameterValue
argument_list|(
name|name
argument_list|)
decl_stmt|;
return|return
name|value
operator|==
literal|null
condition|?
name|defaultValue
else|:
name|value
return|;
block|}
specifier|public
specifier|static
class|class
name|FilterParameterInterface
implements|implements
name|IsSerializable
implements|,
name|Comparable
argument_list|<
name|FilterParameterInterface
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
decl_stmt|,
name|iSuffix
decl_stmt|;
specifier|private
name|List
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
specifier|private
name|boolean
name|iCollapsible
init|=
literal|true
decl_stmt|;
specifier|public
name|FilterParameterInterface
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
name|boolean
name|hasSuffix
parameter_list|()
block|{
return|return
name|iSuffix
operator|!=
literal|null
operator|&&
operator|!
name|iSuffix
operator|.
name|isEmpty
argument_list|()
return|;
block|}
specifier|public
name|String
name|getSuffix
parameter_list|()
block|{
return|return
name|iSuffix
return|;
block|}
specifier|public
name|void
name|setSuffix
parameter_list|(
name|String
name|suffix
parameter_list|)
block|{
name|iSuffix
operator|=
name|suffix
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
name|boolean
name|isSelectedItem
parameter_list|(
name|ListItem
name|item
parameter_list|)
block|{
if|if
condition|(
name|iValue
operator|==
literal|null
condition|)
return|return
literal|false
return|;
if|if
condition|(
name|isMultiSelect
argument_list|()
condition|)
block|{
for|for
control|(
name|String
name|val
range|:
name|iValue
operator|.
name|split
argument_list|(
literal|","
argument_list|)
control|)
if|if
condition|(
name|val
operator|.
name|equalsIgnoreCase
argument_list|(
name|item
operator|.
name|getValue
argument_list|()
argument_list|)
condition|)
return|return
literal|true
return|;
return|return
literal|false
return|;
block|}
else|else
block|{
return|return
name|iValue
operator|.
name|equalsIgnoreCase
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
name|boolean
name|hasValue
parameter_list|()
block|{
return|return
name|iValue
operator|!=
literal|null
return|;
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
if|if
condition|(
name|hasOptions
argument_list|()
operator|&&
name|defaultValue
operator|!=
literal|null
operator|&&
operator|!
name|isMultiSelect
argument_list|()
condition|)
block|{
for|for
control|(
name|ListItem
name|option
range|:
name|getOptions
argument_list|()
control|)
if|if
condition|(
name|defaultValue
operator|.
name|equals
argument_list|(
name|option
operator|.
name|getValue
argument_list|()
argument_list|)
condition|)
return|return;
name|iDefault
operator|=
name|getOptions
argument_list|()
operator|.
name|get
argument_list|(
literal|0
argument_list|)
operator|.
name|getValue
argument_list|()
expr_stmt|;
block|}
block|}
specifier|public
name|boolean
name|hasDefaultValue
parameter_list|()
block|{
return|return
name|iDefault
operator|!=
literal|null
return|;
block|}
specifier|public
name|boolean
name|isDefaultItem
parameter_list|(
name|ListItem
name|item
parameter_list|)
block|{
if|if
condition|(
name|iDefault
operator|==
literal|null
condition|)
return|return
literal|false
return|;
if|if
condition|(
name|isMultiSelect
argument_list|()
condition|)
block|{
for|for
control|(
name|String
name|val
range|:
name|iDefault
operator|.
name|split
argument_list|(
literal|","
argument_list|)
control|)
if|if
condition|(
name|val
operator|.
name|equalsIgnoreCase
argument_list|(
name|item
operator|.
name|getValue
argument_list|()
argument_list|)
condition|)
return|return
literal|true
return|;
return|return
literal|false
return|;
block|}
else|else
block|{
return|return
name|iDefault
operator|.
name|equalsIgnoreCase
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
name|ArrayList
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
name|List
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
name|getOptionText
parameter_list|(
name|String
name|value
parameter_list|)
block|{
if|if
condition|(
name|iOptions
operator|==
literal|null
condition|)
return|return
literal|null
return|;
for|for
control|(
name|ListItem
name|option
range|:
name|getOptions
argument_list|()
control|)
if|if
condition|(
name|value
operator|.
name|equals
argument_list|(
name|option
operator|.
name|getValue
argument_list|()
argument_list|)
condition|)
return|return
name|option
operator|.
name|getText
argument_list|()
return|;
return|return
literal|null
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
specifier|public
name|boolean
name|isCollapsible
parameter_list|()
block|{
return|return
name|iCollapsible
return|;
block|}
specifier|public
name|void
name|setCollapsible
parameter_list|(
name|boolean
name|collapsible
parameter_list|)
block|{
name|iCollapsible
operator|=
name|collapsible
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
name|FilterParameterInterface
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
annotation|@
name|Override
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
name|getValue
argument_list|()
operator|+
literal|": "
operator|+
name|getText
argument_list|()
return|;
block|}
block|}
block|}
end_class

end_unit

