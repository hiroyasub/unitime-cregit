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
name|List
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
name|org
operator|.
name|joda
operator|.
name|time
operator|.
name|DateTime
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|SpecialRegistrationInterface
block|{
specifier|public
specifier|static
class|class
name|SpecialRegistrationRequest
block|{
specifier|public
name|String
name|requestId
decl_stmt|;
specifier|public
name|String
name|studentId
decl_stmt|;
specifier|public
name|String
name|term
decl_stmt|;
specifier|public
name|String
name|campus
decl_stmt|;
specifier|public
name|String
name|status
decl_stmt|;
specifier|public
name|String
name|mode
decl_stmt|;
specifier|public
name|List
argument_list|<
name|Change
argument_list|>
name|changes
decl_stmt|;
specifier|public
name|DateTime
name|dateCreated
decl_stmt|;
specifier|public
name|Float
name|maxCredit
decl_stmt|;
block|}
specifier|public
specifier|static
enum|enum
name|RequestStatus
block|{
name|mayEdit
block|,
name|mayNotEdit
block|,
name|maySubmit
block|,
name|newRequest
block|,
name|draft
block|,
name|inProgress
block|,
name|approved
block|,
name|denied
block|,
name|cancelled
block|, 		 ; 	}
specifier|public
specifier|static
class|class
name|SpecialRegistrationResponse
block|{
specifier|public
name|SpecialRegistrationRequest
name|data
decl_stmt|;
specifier|public
name|String
name|status
decl_stmt|;
specifier|public
name|String
name|message
decl_stmt|;
block|}
specifier|public
specifier|static
class|class
name|SpecialRegistrationResponseList
block|{
specifier|public
name|List
argument_list|<
name|SpecialRegistrationRequest
argument_list|>
name|data
decl_stmt|;
specifier|public
name|String
name|status
decl_stmt|;
specifier|public
name|String
name|message
decl_stmt|;
block|}
specifier|public
specifier|static
class|class
name|SpecialRegistrationStatusResponse
block|{
specifier|public
name|SpecialRegistrationStatus
name|data
decl_stmt|;
specifier|public
name|String
name|status
decl_stmt|;
specifier|public
name|String
name|message
decl_stmt|;
block|}
specifier|public
specifier|static
class|class
name|SpecialRegistrationStatus
block|{
specifier|public
name|Set
argument_list|<
name|String
argument_list|>
name|overrides
decl_stmt|;
specifier|public
name|List
argument_list|<
name|SpecialRegistrationRequest
argument_list|>
name|requests
decl_stmt|;
specifier|public
name|Float
name|maxCredit
decl_stmt|;
block|}
specifier|public
specifier|static
enum|enum
name|ResponseStatus
block|{
name|success
block|,
name|failure
block|; 	}
specifier|public
specifier|static
enum|enum
name|ChangeOperation
block|{
name|ADD
block|,
name|DROP
block|, 	}
specifier|public
specifier|static
class|class
name|Change
block|{
specifier|public
name|String
name|subject
decl_stmt|;
specifier|public
name|String
name|courseNbr
decl_stmt|;
specifier|public
name|String
name|crn
decl_stmt|;
specifier|public
name|String
name|operation
decl_stmt|;
specifier|public
name|List
argument_list|<
name|ChangeError
argument_list|>
name|errors
decl_stmt|;
specifier|public
name|List
argument_list|<
name|Override
argument_list|>
name|overrides
decl_stmt|;
block|}
specifier|public
specifier|static
class|class
name|ChangeError
block|{
name|String
name|code
decl_stmt|;
name|String
name|message
decl_stmt|;
block|}
specifier|public
specifier|static
class|class
name|Override
block|{
name|String
name|code
decl_stmt|;
name|String
name|message
decl_stmt|;
name|String
name|needsAction
decl_stmt|;
name|String
name|needsOverride
decl_stmt|;
name|String
name|overrideApplied
decl_stmt|;
block|}
specifier|public
specifier|static
class|class
name|ValidationCheckRequest
block|{
specifier|public
name|String
name|studentId
decl_stmt|;
specifier|public
name|String
name|term
decl_stmt|;
specifier|public
name|String
name|campus
decl_stmt|;
specifier|public
name|String
name|includeReg
decl_stmt|;
specifier|public
name|String
name|mode
decl_stmt|;
specifier|public
name|List
argument_list|<
name|Schedule
argument_list|>
name|schedule
decl_stmt|;
specifier|public
name|List
argument_list|<
name|Schedule
argument_list|>
name|alternatives
decl_stmt|;
block|}
specifier|public
specifier|static
class|class
name|Schedule
block|{
specifier|public
name|String
name|subject
decl_stmt|;
specifier|public
name|String
name|courseNbr
decl_stmt|;
specifier|public
name|Set
argument_list|<
name|String
argument_list|>
name|crns
decl_stmt|;
block|}
specifier|public
specifier|static
class|class
name|ValidationCheckResponse
block|{
specifier|public
name|ScheduleRestrictions
name|scheduleRestrictions
decl_stmt|;
specifier|public
name|ScheduleRestrictions
name|alternativesRestrictions
decl_stmt|;
block|}
specifier|public
specifier|static
class|class
name|ScheduleRestrictions
block|{
specifier|public
name|List
argument_list|<
name|Problem
argument_list|>
name|problems
decl_stmt|;
specifier|public
name|String
name|sisId
decl_stmt|;
specifier|public
name|String
name|status
decl_stmt|;
specifier|public
name|String
name|term
decl_stmt|;
block|}
specifier|public
specifier|static
class|class
name|Problem
block|{
name|String
name|code
decl_stmt|;
name|String
name|crn
decl_stmt|;
name|String
name|message
decl_stmt|;
block|}
block|}
end_class

end_unit

