begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*< * Licensed to The Apereo Foundation under one or more contributor license  * agreements. See the NOTICE file distributed with this work for  * additional information regarding copyright ownership.  *  * The Apereo Foundation licenses this file to you under the Apache License,  * Version 2.0 (the "License"); you may not use this file except in  * compliance with the License. You may obtain a copy of the License at:  *  * http://www.apache.org/licenses/LICENSE-2.0  *  * Unless required by applicable law or agreed to in writing, software  * distributed under the License is distributed on an "AS IS" BASIS,  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  *  * See the License for the specific language governing permissions and  * limitations under the License.  *  */
end_comment

begin_package
package|package
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|onlinesectioning
operator|.
name|custom
operator|.
name|purdue
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
name|onlinesectioning
operator|.
name|custom
operator|.
name|purdue
operator|.
name|SpecialRegistrationInterface
operator|.
name|Change
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
name|onlinesectioning
operator|.
name|custom
operator|.
name|purdue
operator|.
name|SpecialRegistrationInterface
operator|.
name|ChangeNote
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
name|onlinesectioning
operator|.
name|custom
operator|.
name|purdue
operator|.
name|SpecialRegistrationInterface
operator|.
name|ChangeStatus
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
name|onlinesectioning
operator|.
name|custom
operator|.
name|purdue
operator|.
name|SpecialRegistrationInterface
operator|.
name|CheckRestrictionsRequest
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
name|onlinesectioning
operator|.
name|custom
operator|.
name|purdue
operator|.
name|SpecialRegistrationInterface
operator|.
name|Crn
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
name|onlinesectioning
operator|.
name|custom
operator|.
name|purdue
operator|.
name|SpecialRegistrationInterface
operator|.
name|IncludeReg
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
name|onlinesectioning
operator|.
name|custom
operator|.
name|purdue
operator|.
name|SpecialRegistrationInterface
operator|.
name|RestrictionsCheckRequest
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
name|onlinesectioning
operator|.
name|custom
operator|.
name|purdue
operator|.
name|SpecialRegistrationInterface
operator|.
name|SpecialRegistration
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
name|onlinesectioning
operator|.
name|custom
operator|.
name|purdue
operator|.
name|SpecialRegistrationInterface
operator|.
name|ValidationMode
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
name|onlinesectioning
operator|.
name|custom
operator|.
name|purdue
operator|.
name|SpecialRegistrationInterface
operator|.
name|ValidationOperation
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|SpecialRegistrationHelper
block|{
specifier|public
specifier|static
name|boolean
name|hasLastNote
parameter_list|(
name|Change
name|change
parameter_list|)
block|{
if|if
condition|(
name|change
operator|.
name|notes
operator|==
literal|null
operator|||
name|change
operator|.
name|notes
operator|.
name|isEmpty
argument_list|()
condition|)
return|return
literal|false
return|;
for|for
control|(
name|ChangeNote
name|n
range|:
name|change
operator|.
name|notes
control|)
if|if
condition|(
name|n
operator|.
name|notes
operator|!=
literal|null
operator|&&
operator|!
name|n
operator|.
name|notes
operator|.
name|isEmpty
argument_list|()
condition|)
return|return
literal|true
return|;
return|return
literal|false
return|;
block|}
specifier|public
specifier|static
name|String
name|getLastNote
parameter_list|(
name|Change
name|change
parameter_list|)
block|{
if|if
condition|(
name|change
operator|.
name|notes
operator|==
literal|null
operator|||
name|change
operator|.
name|notes
operator|.
name|isEmpty
argument_list|()
condition|)
return|return
literal|null
return|;
name|ChangeNote
name|note
init|=
literal|null
decl_stmt|;
for|for
control|(
name|ChangeNote
name|n
range|:
name|change
operator|.
name|notes
control|)
if|if
condition|(
name|n
operator|.
name|notes
operator|!=
literal|null
operator|&&
operator|!
name|n
operator|.
name|notes
operator|.
name|isEmpty
argument_list|()
operator|&&
operator|(
name|note
operator|==
literal|null
operator|||
name|note
operator|.
name|dateCreated
operator|.
name|isBefore
argument_list|(
name|n
operator|.
name|dateCreated
argument_list|)
operator|)
condition|)
name|note
operator|=
name|n
expr_stmt|;
return|return
operator|(
name|note
operator|==
literal|null
condition|?
literal|null
else|:
name|note
operator|.
name|notes
operator|)
return|;
block|}
specifier|public
specifier|static
name|String
name|note
parameter_list|(
name|SpecialRegistration
name|reg
parameter_list|,
name|boolean
name|credit
parameter_list|)
block|{
name|String
name|note
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|reg
operator|.
name|changes
operator|!=
literal|null
condition|)
for|for
control|(
name|Change
name|ch
range|:
name|reg
operator|.
name|changes
control|)
block|{
if|if
condition|(
name|credit
operator|&&
name|ch
operator|.
name|subject
operator|==
literal|null
operator|&&
name|ch
operator|.
name|courseNbr
operator|==
literal|null
operator|&&
name|hasLastNote
argument_list|(
name|ch
argument_list|)
condition|)
name|note
operator|=
operator|(
name|note
operator|==
literal|null
condition|?
literal|""
else|:
name|note
operator|+
literal|"\n"
operator|)
operator|+
name|getLastNote
argument_list|(
name|ch
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
name|credit
operator|&&
name|ch
operator|.
name|subject
operator|!=
literal|null
operator|&&
name|ch
operator|.
name|courseNbr
operator|!=
literal|null
operator|&&
name|hasLastNote
argument_list|(
name|ch
argument_list|)
operator|&&
name|ch
operator|.
name|status
operator|!=
name|ChangeStatus
operator|.
name|approved
condition|)
name|note
operator|=
operator|(
name|note
operator|==
literal|null
condition|?
literal|""
else|:
name|note
operator|+
literal|"\n"
operator|)
operator|+
name|getLastNote
argument_list|(
name|ch
argument_list|)
expr_stmt|;
block|}
return|return
name|note
return|;
block|}
specifier|public
specifier|static
name|RestrictionsCheckRequest
name|createValidationRequest
parameter_list|(
name|CheckRestrictionsRequest
name|req
parameter_list|,
name|ValidationMode
name|mode
parameter_list|,
name|boolean
name|includeRegistration
parameter_list|)
block|{
name|RestrictionsCheckRequest
name|ret
init|=
operator|new
name|RestrictionsCheckRequest
argument_list|()
decl_stmt|;
name|ret
operator|.
name|sisId
operator|=
name|req
operator|.
name|studentId
expr_stmt|;
name|ret
operator|.
name|term
operator|=
name|req
operator|.
name|term
expr_stmt|;
name|ret
operator|.
name|campus
operator|=
name|req
operator|.
name|campus
expr_stmt|;
name|ret
operator|.
name|mode
operator|=
name|mode
expr_stmt|;
name|ret
operator|.
name|includeReg
operator|=
operator|(
name|includeRegistration
condition|?
name|IncludeReg
operator|.
name|Y
else|:
name|IncludeReg
operator|.
name|N
operator|)
expr_stmt|;
name|ret
operator|.
name|actions
operator|=
operator|new
name|HashMap
argument_list|<
name|ValidationOperation
argument_list|,
name|List
argument_list|<
name|Crn
argument_list|>
argument_list|>
argument_list|()
expr_stmt|;
name|ret
operator|.
name|actions
operator|.
name|put
argument_list|(
name|ValidationOperation
operator|.
name|ADD
argument_list|,
operator|new
name|ArrayList
argument_list|<
name|Crn
argument_list|>
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|includeRegistration
condition|)
name|ret
operator|.
name|actions
operator|.
name|put
argument_list|(
name|ValidationOperation
operator|.
name|DROP
argument_list|,
operator|new
name|ArrayList
argument_list|<
name|Crn
argument_list|>
argument_list|()
argument_list|)
expr_stmt|;
return|return
name|ret
return|;
block|}
specifier|public
specifier|static
name|void
name|addOperation
parameter_list|(
name|RestrictionsCheckRequest
name|request
parameter_list|,
name|ValidationOperation
name|op
parameter_list|,
name|String
name|crn
parameter_list|)
block|{
if|if
condition|(
name|request
operator|.
name|actions
operator|==
literal|null
condition|)
name|request
operator|.
name|actions
operator|=
operator|new
name|HashMap
argument_list|<
name|ValidationOperation
argument_list|,
name|List
argument_list|<
name|Crn
argument_list|>
argument_list|>
argument_list|()
expr_stmt|;
name|List
argument_list|<
name|Crn
argument_list|>
name|crns
init|=
name|request
operator|.
name|actions
operator|.
name|get
argument_list|(
name|op
argument_list|)
decl_stmt|;
if|if
condition|(
name|crns
operator|==
literal|null
condition|)
block|{
name|crns
operator|=
operator|new
name|ArrayList
argument_list|<
name|Crn
argument_list|>
argument_list|()
expr_stmt|;
name|request
operator|.
name|actions
operator|.
name|put
argument_list|(
name|op
argument_list|,
name|crns
argument_list|)
expr_stmt|;
block|}
else|else
block|{
for|for
control|(
name|Crn
name|c
range|:
name|crns
control|)
block|{
if|if
condition|(
name|crn
operator|.
name|equals
argument_list|(
name|c
operator|.
name|crn
argument_list|)
condition|)
return|return;
block|}
block|}
name|Crn
name|c
init|=
operator|new
name|Crn
argument_list|()
decl_stmt|;
name|c
operator|.
name|crn
operator|=
name|crn
expr_stmt|;
name|crns
operator|.
name|add
argument_list|(
name|c
argument_list|)
expr_stmt|;
block|}
specifier|public
specifier|static
name|void
name|addCrn
parameter_list|(
name|RestrictionsCheckRequest
name|request
parameter_list|,
name|String
name|crn
parameter_list|)
block|{
name|addOperation
argument_list|(
name|request
argument_list|,
name|ValidationOperation
operator|.
name|ADD
argument_list|,
name|crn
argument_list|)
expr_stmt|;
block|}
specifier|public
specifier|static
name|void
name|dropCrn
parameter_list|(
name|RestrictionsCheckRequest
name|request
parameter_list|,
name|String
name|crn
parameter_list|)
block|{
name|addOperation
argument_list|(
name|request
argument_list|,
name|ValidationOperation
operator|.
name|DROP
argument_list|,
name|crn
argument_list|)
expr_stmt|;
block|}
specifier|public
specifier|static
name|boolean
name|isEmpty
parameter_list|(
name|RestrictionsCheckRequest
name|request
parameter_list|)
block|{
return|return
name|request
operator|.
name|actions
operator|==
literal|null
operator|||
name|request
operator|.
name|actions
operator|.
name|isEmpty
argument_list|()
return|;
block|}
specifier|public
specifier|static
name|void
name|addCrn
parameter_list|(
name|CheckRestrictionsRequest
name|req
parameter_list|,
name|String
name|crn
parameter_list|)
block|{
if|if
condition|(
name|req
operator|.
name|changes
operator|==
literal|null
condition|)
name|req
operator|.
name|changes
operator|=
name|createValidationRequest
argument_list|(
name|req
argument_list|,
name|ValidationMode
operator|.
name|REG
argument_list|,
literal|false
argument_list|)
expr_stmt|;
name|addCrn
argument_list|(
name|req
operator|.
name|changes
argument_list|,
name|crn
argument_list|)
expr_stmt|;
block|}
specifier|public
specifier|static
name|void
name|addAltCrn
parameter_list|(
name|CheckRestrictionsRequest
name|req
parameter_list|,
name|String
name|crn
parameter_list|)
block|{
if|if
condition|(
name|req
operator|.
name|alternatives
operator|==
literal|null
condition|)
name|req
operator|.
name|alternatives
operator|=
name|createValidationRequest
argument_list|(
name|req
argument_list|,
name|ValidationMode
operator|.
name|ALT
argument_list|,
literal|false
argument_list|)
expr_stmt|;
name|addCrn
argument_list|(
name|req
operator|.
name|alternatives
argument_list|,
name|crn
argument_list|)
expr_stmt|;
block|}
specifier|public
specifier|static
name|boolean
name|isEmpty
parameter_list|(
name|CheckRestrictionsRequest
name|req
parameter_list|)
block|{
return|return
operator|(
name|req
operator|.
name|changes
operator|==
literal|null
operator|||
name|isEmpty
argument_list|(
name|req
operator|.
name|changes
argument_list|)
operator|)
operator|&&
operator|(
name|req
operator|.
name|alternatives
operator|==
literal|null
operator|||
name|isEmpty
argument_list|(
name|req
operator|.
name|alternatives
argument_list|)
operator|)
return|;
block|}
block|}
end_class

end_unit

