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
name|GwtRpcException
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
name|ClassAssignmentInterface
operator|.
name|ErrorMessage
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
name|OnlineSectioningInterface
operator|.
name|EligibilityCheck
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
name|SectioningException
extends|extends
name|GwtRpcException
implements|implements
name|IsSerializable
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
name|EligibilityCheck
name|iCheck
init|=
literal|null
decl_stmt|;
specifier|public
specifier|static
enum|enum
name|Type
block|{
name|INFO
block|,
name|WARNING
block|,
name|ERROR
block|}
empty_stmt|;
specifier|private
name|Type
name|iType
init|=
literal|null
decl_stmt|;
specifier|private
name|Map
argument_list|<
name|Long
argument_list|,
name|String
argument_list|>
name|iSectionMessages
init|=
literal|null
decl_stmt|;
specifier|private
name|ArrayList
argument_list|<
name|ErrorMessage
argument_list|>
name|iErrors
init|=
literal|null
decl_stmt|;
specifier|public
name|SectioningException
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
block|}
specifier|public
name|SectioningException
parameter_list|(
name|String
name|message
parameter_list|)
block|{
name|super
argument_list|(
name|message
argument_list|)
expr_stmt|;
block|}
specifier|public
name|SectioningException
parameter_list|(
name|String
name|message
parameter_list|,
name|Throwable
name|cause
parameter_list|)
block|{
name|super
argument_list|(
name|message
argument_list|,
name|cause
argument_list|)
expr_stmt|;
block|}
specifier|public
name|SectioningException
name|withEligibilityCheck
parameter_list|(
name|EligibilityCheck
name|check
parameter_list|)
block|{
name|iCheck
operator|=
name|check
expr_stmt|;
return|return
name|this
return|;
block|}
specifier|public
name|boolean
name|hasEligibilityCheck
parameter_list|()
block|{
return|return
name|iCheck
operator|!=
literal|null
return|;
block|}
specifier|public
name|EligibilityCheck
name|getEligibilityCheck
parameter_list|()
block|{
return|return
name|iCheck
return|;
block|}
specifier|public
name|SectioningException
name|withTypeInfo
parameter_list|()
block|{
name|iType
operator|=
name|Type
operator|.
name|INFO
expr_stmt|;
return|return
name|this
return|;
block|}
specifier|public
name|SectioningException
name|withTypeWarning
parameter_list|()
block|{
name|iType
operator|=
name|Type
operator|.
name|WARNING
expr_stmt|;
return|return
name|this
return|;
block|}
specifier|public
name|SectioningException
name|withTypeError
parameter_list|()
block|{
name|iType
operator|=
name|Type
operator|.
name|ERROR
expr_stmt|;
return|return
name|this
return|;
block|}
specifier|public
name|boolean
name|hasType
parameter_list|()
block|{
return|return
name|iType
operator|!=
literal|null
return|;
block|}
specifier|public
name|boolean
name|isInfo
parameter_list|()
block|{
return|return
name|iType
operator|!=
literal|null
operator|&&
name|iType
operator|==
name|Type
operator|.
name|INFO
return|;
block|}
specifier|public
name|boolean
name|isWarning
parameter_list|()
block|{
return|return
name|iType
operator|!=
literal|null
operator|&&
name|iType
operator|==
name|Type
operator|.
name|WARNING
return|;
block|}
specifier|public
name|boolean
name|isError
parameter_list|()
block|{
return|return
name|iType
operator|!=
literal|null
operator|&&
name|iType
operator|==
name|Type
operator|.
name|ERROR
return|;
block|}
specifier|public
name|boolean
name|hasSectionMessages
parameter_list|()
block|{
return|return
name|iSectionMessages
operator|!=
literal|null
operator|&&
operator|!
name|iSectionMessages
operator|.
name|isEmpty
argument_list|()
return|;
block|}
specifier|public
name|void
name|setSectionMessage
parameter_list|(
name|Long
name|classId
parameter_list|,
name|String
name|message
parameter_list|)
block|{
if|if
condition|(
name|iSectionMessages
operator|==
literal|null
condition|)
name|iSectionMessages
operator|=
operator|new
name|HashMap
argument_list|<
name|Long
argument_list|,
name|String
argument_list|>
argument_list|()
expr_stmt|;
if|if
condition|(
name|classId
operator|!=
literal|null
condition|)
name|iSectionMessages
operator|.
name|put
argument_list|(
name|classId
argument_list|,
name|message
argument_list|)
expr_stmt|;
block|}
specifier|public
name|boolean
name|hasSectionMessage
parameter_list|(
name|Long
name|classId
parameter_list|)
block|{
if|if
condition|(
name|classId
operator|==
literal|null
operator|||
name|iSectionMessages
operator|==
literal|null
condition|)
return|return
literal|false
return|;
name|String
name|message
init|=
name|iSectionMessages
operator|.
name|get
argument_list|(
name|classId
argument_list|)
decl_stmt|;
return|return
name|message
operator|!=
literal|null
operator|&&
operator|!
name|message
operator|.
name|isEmpty
argument_list|()
return|;
block|}
specifier|public
name|String
name|getSectionMessage
parameter_list|(
name|Long
name|classId
parameter_list|)
block|{
if|if
condition|(
name|classId
operator|==
literal|null
operator|||
name|iSectionMessages
operator|==
literal|null
condition|)
return|return
literal|null
return|;
return|return
name|iSectionMessages
operator|.
name|get
argument_list|(
name|classId
argument_list|)
return|;
block|}
specifier|public
name|void
name|addError
parameter_list|(
name|ErrorMessage
name|error
parameter_list|)
block|{
if|if
condition|(
name|iErrors
operator|==
literal|null
condition|)
name|iErrors
operator|=
operator|new
name|ArrayList
argument_list|<
name|ErrorMessage
argument_list|>
argument_list|()
expr_stmt|;
name|iErrors
operator|.
name|add
argument_list|(
name|error
argument_list|)
expr_stmt|;
block|}
specifier|public
name|boolean
name|hasErrors
parameter_list|()
block|{
return|return
name|iErrors
operator|!=
literal|null
operator|&&
operator|!
name|iErrors
operator|.
name|isEmpty
argument_list|()
return|;
block|}
specifier|public
name|ArrayList
argument_list|<
name|ErrorMessage
argument_list|>
name|getErrors
parameter_list|()
block|{
return|return
name|iErrors
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|toString
parameter_list|()
block|{
if|if
condition|(
name|iType
operator|!=
literal|null
condition|)
return|return
name|iType
operator|.
name|name
argument_list|()
operator|+
literal|": "
operator|+
name|getMessage
argument_list|()
return|;
else|else
return|return
name|super
operator|.
name|toString
argument_list|()
return|;
block|}
block|}
end_class

end_unit

