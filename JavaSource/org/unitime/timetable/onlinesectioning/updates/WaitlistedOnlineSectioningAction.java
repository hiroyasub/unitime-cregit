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
name|updates
package|;
end_package

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
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|StudentSectioningStatus
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
name|OnlineSectioningAction
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
name|OnlineSectioningHelper
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
name|OnlineSectioningServer
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
name|model
operator|.
name|XCourseRequest
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
name|model
operator|.
name|XStudent
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
specifier|abstract
class|class
name|WaitlistedOnlineSectioningAction
parameter_list|<
name|T
parameter_list|>
implements|implements
name|OnlineSectioningAction
argument_list|<
name|T
argument_list|>
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
name|Set
argument_list|<
name|String
argument_list|>
name|iWaitlistStatuses
init|=
literal|null
decl_stmt|;
specifier|public
name|boolean
name|isWaitListed
parameter_list|(
name|XStudent
name|student
parameter_list|,
name|XCourseRequest
name|request
parameter_list|,
name|OnlineSectioningServer
name|server
parameter_list|,
name|OnlineSectioningHelper
name|helper
parameter_list|)
block|{
comment|// Check wait-list toggle first
if|if
condition|(
name|request
operator|==
literal|null
operator|||
operator|!
name|request
operator|.
name|isWaitlist
argument_list|()
condition|)
return|return
literal|false
return|;
comment|// Check student status
name|String
name|status
init|=
name|student
operator|.
name|getStatus
argument_list|()
decl_stmt|;
if|if
condition|(
name|status
operator|==
literal|null
condition|)
name|status
operator|=
name|server
operator|.
name|getAcademicSession
argument_list|()
operator|.
name|getDefaultSectioningStatus
argument_list|()
expr_stmt|;
if|if
condition|(
name|status
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
name|iWaitlistStatuses
operator|==
literal|null
condition|)
name|iWaitlistStatuses
operator|=
name|StudentSectioningStatus
operator|.
name|getMatchingStatuses
argument_list|(
name|StudentSectioningStatus
operator|.
name|Option
operator|.
name|waitlist
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
name|iWaitlistStatuses
operator|.
name|contains
argument_list|(
name|status
argument_list|)
condition|)
return|return
literal|false
return|;
block|}
return|return
literal|true
return|;
block|}
block|}
end_class

end_unit

