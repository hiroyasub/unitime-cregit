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
name|client
operator|.
name|instructor
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
name|Map
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
name|client
operator|.
name|widgets
operator|.
name|P
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
name|resources
operator|.
name|GwtConstants
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
name|InstructorInterface
operator|.
name|TeachingRequestsPagePropertiesResponse
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
name|core
operator|.
name|client
operator|.
name|GWT
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
name|i18n
operator|.
name|client
operator|.
name|NumberFormat
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
name|TakesValue
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|ObjectivesCell
extends|extends
name|P
implements|implements
name|TakesValue
argument_list|<
name|Map
argument_list|<
name|String
argument_list|,
name|Double
argument_list|>
argument_list|>
block|{
specifier|protected
specifier|static
specifier|final
name|GwtConstants
name|CONSTANTS
init|=
name|GWT
operator|.
name|create
argument_list|(
name|GwtConstants
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|protected
specifier|static
name|NumberFormat
name|sTeachingLoadFormat
init|=
name|NumberFormat
operator|.
name|getFormat
argument_list|(
name|CONSTANTS
operator|.
name|teachingLoadFormat
argument_list|()
argument_list|)
decl_stmt|;
specifier|protected
name|TeachingRequestsPagePropertiesResponse
name|iProperties
decl_stmt|;
specifier|private
name|Map
argument_list|<
name|String
argument_list|,
name|Double
argument_list|>
name|iValue
init|=
literal|null
decl_stmt|;
specifier|public
name|ObjectivesCell
parameter_list|(
name|TeachingRequestsPagePropertiesResponse
name|properties
parameter_list|)
block|{
name|super
argument_list|(
literal|"objectives"
argument_list|)
expr_stmt|;
name|iProperties
operator|=
name|properties
expr_stmt|;
block|}
specifier|public
name|ObjectivesCell
parameter_list|(
name|TeachingRequestsPagePropertiesResponse
name|properties
parameter_list|,
name|Map
argument_list|<
name|String
argument_list|,
name|Double
argument_list|>
name|values
parameter_list|)
block|{
name|this
argument_list|(
name|properties
argument_list|)
expr_stmt|;
name|setValue
argument_list|(
name|values
argument_list|)
expr_stmt|;
block|}
specifier|public
name|ObjectivesCell
parameter_list|(
name|TeachingRequestsPagePropertiesResponse
name|properties
parameter_list|,
name|Map
argument_list|<
name|String
argument_list|,
name|Double
argument_list|>
name|initial
parameter_list|,
name|Map
argument_list|<
name|String
argument_list|,
name|Double
argument_list|>
name|current
parameter_list|)
block|{
name|this
argument_list|(
name|properties
argument_list|)
expr_stmt|;
name|setValue
argument_list|(
name|initial
argument_list|,
name|current
argument_list|)
expr_stmt|;
block|}
specifier|public
specifier|static
name|Map
argument_list|<
name|String
argument_list|,
name|Double
argument_list|>
name|diff
parameter_list|(
name|Map
argument_list|<
name|String
argument_list|,
name|Double
argument_list|>
name|initial
parameter_list|,
name|Map
argument_list|<
name|String
argument_list|,
name|Double
argument_list|>
name|current
parameter_list|)
block|{
name|Map
argument_list|<
name|String
argument_list|,
name|Double
argument_list|>
name|ret
init|=
operator|(
name|current
operator|==
literal|null
condition|?
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|Double
argument_list|>
argument_list|()
else|:
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|Double
argument_list|>
argument_list|(
name|current
argument_list|)
operator|)
decl_stmt|;
if|if
condition|(
name|initial
operator|!=
literal|null
condition|)
block|{
for|for
control|(
name|Map
operator|.
name|Entry
argument_list|<
name|String
argument_list|,
name|Double
argument_list|>
name|e
range|:
name|initial
operator|.
name|entrySet
argument_list|()
control|)
block|{
name|String
name|key
init|=
name|e
operator|.
name|getKey
argument_list|()
decl_stmt|;
name|Double
name|base
init|=
name|e
operator|.
name|getValue
argument_list|()
decl_stmt|;
name|Double
name|value
init|=
operator|(
name|current
operator|==
literal|null
condition|?
literal|null
else|:
name|current
operator|.
name|get
argument_list|(
name|key
argument_list|)
operator|)
decl_stmt|;
if|if
condition|(
name|value
operator|==
literal|null
condition|)
name|ret
operator|.
name|put
argument_list|(
name|key
argument_list|,
operator|-
name|base
argument_list|)
expr_stmt|;
else|else
name|ret
operator|.
name|put
argument_list|(
name|key
argument_list|,
name|value
operator|-
name|base
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|ret
return|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|setValue
parameter_list|(
name|Map
argument_list|<
name|String
argument_list|,
name|Double
argument_list|>
name|values
parameter_list|)
block|{
name|iValue
operator|=
name|values
expr_stmt|;
name|clear
argument_list|()
expr_stmt|;
if|if
condition|(
name|values
operator|!=
literal|null
condition|)
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
name|values
operator|.
name|keySet
argument_list|()
argument_list|)
control|)
block|{
name|Double
name|value
init|=
name|values
operator|.
name|get
argument_list|(
name|key
argument_list|)
decl_stmt|;
if|if
condition|(
name|value
operator|==
literal|null
operator|||
name|Math
operator|.
name|abs
argument_list|(
name|value
argument_list|)
operator|<
literal|0.001
condition|)
continue|continue;
name|P
name|obj
init|=
operator|new
name|P
argument_list|(
literal|"objective"
argument_list|)
decl_stmt|;
name|obj
operator|.
name|setText
argument_list|(
name|key
operator|+
literal|": "
operator|+
operator|(
name|value
operator|>
literal|0.0
condition|?
literal|"+"
else|:
literal|""
operator|)
operator|+
name|sTeachingLoadFormat
operator|.
name|format
argument_list|(
name|value
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|key
operator|.
name|endsWith
argument_list|(
literal|" Preferences"
argument_list|)
condition|)
block|{
if|if
condition|(
name|value
operator|<=
operator|-
literal|50.0
condition|)
block|{
name|obj
operator|.
name|getElement
argument_list|()
operator|.
name|getStyle
argument_list|()
operator|.
name|setColor
argument_list|(
name|iProperties
operator|.
name|getPreference
argument_list|(
literal|"R"
argument_list|)
operator|.
name|getColor
argument_list|()
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|value
operator|<=
operator|-
literal|2.0
condition|)
block|{
name|obj
operator|.
name|getElement
argument_list|()
operator|.
name|getStyle
argument_list|()
operator|.
name|setColor
argument_list|(
name|iProperties
operator|.
name|getPreference
argument_list|(
literal|"-2"
argument_list|)
operator|.
name|getColor
argument_list|()
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|value
operator|<
literal|0.0
condition|)
block|{
name|obj
operator|.
name|getElement
argument_list|()
operator|.
name|getStyle
argument_list|()
operator|.
name|setColor
argument_list|(
name|iProperties
operator|.
name|getPreference
argument_list|(
literal|"-1"
argument_list|)
operator|.
name|getColor
argument_list|()
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|value
operator|>=
literal|50.0
condition|)
block|{
name|obj
operator|.
name|getElement
argument_list|()
operator|.
name|getStyle
argument_list|()
operator|.
name|setColor
argument_list|(
name|iProperties
operator|.
name|getPreference
argument_list|(
literal|"P"
argument_list|)
operator|.
name|getColor
argument_list|()
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|value
operator|>=
literal|2.0
condition|)
block|{
name|obj
operator|.
name|getElement
argument_list|()
operator|.
name|getStyle
argument_list|()
operator|.
name|setColor
argument_list|(
name|iProperties
operator|.
name|getPreference
argument_list|(
literal|"2"
argument_list|)
operator|.
name|getColor
argument_list|()
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|value
operator|>
literal|0.0
condition|)
block|{
name|obj
operator|.
name|getElement
argument_list|()
operator|.
name|getStyle
argument_list|()
operator|.
name|setColor
argument_list|(
name|iProperties
operator|.
name|getPreference
argument_list|(
literal|"1"
argument_list|)
operator|.
name|getColor
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
if|else if
condition|(
name|value
operator|<
literal|0.0
condition|)
block|{
name|obj
operator|.
name|getElement
argument_list|()
operator|.
name|getStyle
argument_list|()
operator|.
name|setColor
argument_list|(
literal|"green"
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|value
operator|>
literal|0.0
condition|)
block|{
name|obj
operator|.
name|getElement
argument_list|()
operator|.
name|getStyle
argument_list|()
operator|.
name|setColor
argument_list|(
literal|"red"
argument_list|)
expr_stmt|;
block|}
name|add
argument_list|(
name|obj
argument_list|)
expr_stmt|;
block|}
block|}
block|}
specifier|public
name|void
name|setValue
parameter_list|(
name|Map
argument_list|<
name|String
argument_list|,
name|Double
argument_list|>
name|initial
parameter_list|,
name|Map
argument_list|<
name|String
argument_list|,
name|Double
argument_list|>
name|current
parameter_list|)
block|{
name|setValue
argument_list|(
name|diff
argument_list|(
name|initial
argument_list|,
name|current
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|Map
argument_list|<
name|String
argument_list|,
name|Double
argument_list|>
name|getValue
parameter_list|()
block|{
return|return
name|iValue
return|;
block|}
block|}
end_class

end_unit

