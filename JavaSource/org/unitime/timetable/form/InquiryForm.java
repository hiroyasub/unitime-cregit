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
name|form
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
name|List
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
name|apache
operator|.
name|struts
operator|.
name|upload
operator|.
name|FormFile
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
name|ContactCategory
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
name|ContactCategoryDAO
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
name|util
operator|.
name|Constants
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
name|util
operator|.
name|DynamicList
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
name|util
operator|.
name|DynamicListObjectFactory
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
name|util
operator|.
name|IdValue
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|InquiryForm
extends|extends
name|ActionForm
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
operator|-
literal|2461671741219768003L
decl_stmt|;
specifier|private
name|String
name|iOp
decl_stmt|;
specifier|private
name|String
name|iSubject
decl_stmt|;
specifier|private
name|String
name|iMessage
decl_stmt|;
specifier|private
name|long
name|iType
decl_stmt|;
specifier|private
name|List
name|carbonCopy
decl_stmt|;
specifier|private
name|String
name|puid
init|=
literal|null
decl_stmt|;
specifier|private
name|boolean
name|iNoRole
decl_stmt|;
specifier|private
specifier|transient
name|FormFile
name|iFile
decl_stmt|;
comment|// --------------------------------------------------------- Classes
comment|/** Factory to create dynamic list element for email addresses */
specifier|protected
name|DynamicListObjectFactory
name|factoryEmails
init|=
operator|new
name|DynamicListObjectFactory
argument_list|()
block|{
specifier|public
name|Object
name|create
parameter_list|()
block|{
return|return
operator|new
name|String
argument_list|(
name|Constants
operator|.
name|BLANK_OPTION_VALUE
argument_list|)
return|;
block|}
block|}
decl_stmt|;
comment|// --------------------------------------------------------- Methods
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
name|iType
operator|<
literal|0
condition|)
name|errors
operator|.
name|add
argument_list|(
literal|"type"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.generic"
argument_list|,
literal|"Please specify category of this inquiry:"
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|iMessage
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
literal|"message"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.generic"
argument_list|,
literal|"Message is required."
argument_list|)
argument_list|)
expr_stmt|;
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
name|iSubject
operator|=
literal|null
expr_stmt|;
name|iMessage
operator|=
literal|null
expr_stmt|;
name|iType
operator|=
operator|-
literal|1
expr_stmt|;
name|puid
operator|=
literal|null
expr_stmt|;
name|carbonCopy
operator|=
name|DynamicList
operator|.
name|getInstance
argument_list|(
operator|new
name|ArrayList
argument_list|()
argument_list|,
name|factoryEmails
argument_list|)
expr_stmt|;
name|iFile
operator|=
literal|null
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
name|getSubject
parameter_list|()
block|{
return|return
name|iSubject
return|;
block|}
specifier|public
name|void
name|setSubject
parameter_list|(
name|String
name|subject
parameter_list|)
block|{
name|iSubject
operator|=
name|subject
expr_stmt|;
block|}
specifier|public
name|String
name|getMessage
parameter_list|()
block|{
return|return
name|iMessage
return|;
block|}
specifier|public
name|void
name|setMessage
parameter_list|(
name|String
name|message
parameter_list|)
block|{
name|iMessage
operator|=
name|message
expr_stmt|;
block|}
specifier|public
name|long
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
name|long
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
name|getTypeMsg
parameter_list|(
name|long
name|type
parameter_list|)
block|{
name|ContactCategory
name|cc
init|=
name|ContactCategoryDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|get
argument_list|(
name|type
argument_list|)
decl_stmt|;
return|return
name|cc
operator|.
name|getLabel
argument_list|()
return|;
block|}
specifier|public
name|Vector
name|getTypeOptions
parameter_list|()
block|{
name|Vector
name|ret
init|=
operator|new
name|Vector
argument_list|()
decl_stmt|;
for|for
control|(
name|ContactCategory
name|cc
range|:
operator|(
name|List
argument_list|<
name|ContactCategory
argument_list|>
operator|)
name|ContactCategoryDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"from ContactCategory order by reference"
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
if|if
condition|(
name|cc
operator|.
name|getHasRole
argument_list|()
operator|&&
name|iNoRole
condition|)
continue|continue;
name|ret
operator|.
name|add
argument_list|(
operator|new
name|IdValue
argument_list|(
name|cc
operator|.
name|getUniqueId
argument_list|()
argument_list|,
name|cc
operator|.
name|getLabel
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
return|return
name|ret
return|;
block|}
specifier|public
name|void
name|updateMessage
parameter_list|()
block|{
if|if
condition|(
name|iMessage
operator|!=
literal|null
operator|&&
operator|!
name|iMessage
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|String
name|message
init|=
literal|null
decl_stmt|;
name|boolean
name|eq
init|=
literal|false
decl_stmt|;
for|for
control|(
name|ContactCategory
name|cc
range|:
operator|(
name|List
argument_list|<
name|ContactCategory
argument_list|>
operator|)
name|ContactCategoryDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"from ContactCategory order by reference"
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
if|if
condition|(
name|cc
operator|.
name|getMessage
argument_list|()
operator|!=
literal|null
operator|&&
name|cc
operator|.
name|getMessage
argument_list|()
operator|.
name|replaceAll
argument_list|(
literal|"\\s+"
argument_list|,
literal|""
argument_list|)
operator|.
name|equals
argument_list|(
name|iMessage
operator|.
name|replaceAll
argument_list|(
literal|"\\s+"
argument_list|,
literal|""
argument_list|)
argument_list|)
condition|)
name|eq
operator|=
literal|true
expr_stmt|;
if|if
condition|(
name|cc
operator|.
name|getUniqueId
argument_list|()
operator|.
name|equals
argument_list|(
name|iType
argument_list|)
condition|)
name|message
operator|=
name|cc
operator|.
name|getMessage
argument_list|()
expr_stmt|;
block|}
if|if
condition|(
operator|!
name|eq
condition|)
return|return;
name|iMessage
operator|=
name|message
expr_stmt|;
block|}
else|else
block|{
name|ContactCategory
name|cc
init|=
name|ContactCategoryDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|get
argument_list|(
name|iType
argument_list|)
decl_stmt|;
name|iMessage
operator|=
operator|(
name|cc
operator|==
literal|null
condition|?
literal|null
else|:
name|cc
operator|.
name|getMessage
argument_list|()
operator|)
expr_stmt|;
block|}
block|}
specifier|public
name|String
name|getPuid
parameter_list|()
block|{
return|return
name|puid
return|;
block|}
specifier|public
name|void
name|setPuid
parameter_list|(
name|String
name|puid
parameter_list|)
block|{
if|if
condition|(
name|puid
operator|!=
literal|null
operator|&&
name|puid
operator|.
name|trim
argument_list|()
operator|.
name|length
argument_list|()
operator|==
literal|0
condition|)
name|this
operator|.
name|puid
operator|=
literal|null
expr_stmt|;
else|else
name|this
operator|.
name|puid
operator|=
name|puid
expr_stmt|;
block|}
specifier|public
name|boolean
name|getNoRole
parameter_list|()
block|{
return|return
name|iNoRole
return|;
block|}
specifier|public
name|void
name|setNoRole
parameter_list|(
name|boolean
name|noRole
parameter_list|)
block|{
name|iNoRole
operator|=
name|noRole
expr_stmt|;
block|}
specifier|public
name|List
name|getCarbonCopy
parameter_list|()
block|{
return|return
name|carbonCopy
return|;
block|}
specifier|public
name|void
name|setCarbonCopy
parameter_list|(
name|List
name|carbonCopy
parameter_list|)
block|{
name|this
operator|.
name|carbonCopy
operator|=
name|carbonCopy
expr_stmt|;
block|}
specifier|public
name|String
name|getCarbonCopy
parameter_list|(
name|int
name|key
parameter_list|)
block|{
return|return
name|carbonCopy
operator|.
name|get
argument_list|(
name|key
argument_list|)
operator|.
name|toString
argument_list|()
return|;
block|}
specifier|public
name|void
name|setCarbonCopy
parameter_list|(
name|int
name|key
parameter_list|,
name|Object
name|value
parameter_list|)
block|{
name|this
operator|.
name|carbonCopy
operator|.
name|set
argument_list|(
name|key
argument_list|,
name|value
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|addToCarbonCopy
parameter_list|(
name|String
name|carbonCopy
parameter_list|)
block|{
name|this
operator|.
name|carbonCopy
operator|.
name|add
argument_list|(
name|carbonCopy
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|removeCarbonCopy
parameter_list|(
name|int
name|rowNum
parameter_list|)
block|{
if|if
condition|(
name|rowNum
operator|>=
literal|0
condition|)
block|{
name|carbonCopy
operator|.
name|remove
argument_list|(
name|rowNum
argument_list|)
expr_stmt|;
block|}
block|}
specifier|public
name|FormFile
name|getFile
parameter_list|()
block|{
return|return
name|iFile
return|;
block|}
specifier|public
name|void
name|setFile
parameter_list|(
name|FormFile
name|file
parameter_list|)
block|{
name|iFile
operator|=
name|file
expr_stmt|;
block|}
block|}
end_class

end_unit

