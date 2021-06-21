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
name|Set
import|;
end_import

begin_import
import|import
name|org
operator|.
name|cpsolver
operator|.
name|ifs
operator|.
name|util
operator|.
name|DataProperties
import|;
end_import

begin_import
import|import
name|org
operator|.
name|cpsolver
operator|.
name|studentsct
operator|.
name|model
operator|.
name|Student
operator|.
name|StudentPriority
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
name|server
operator|.
name|Query
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
name|custom
operator|.
name|Customization
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
name|WaitListValidationProvider
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
name|XCourse
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
name|XOffering
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
name|XOverride
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
operator|.
name|XGroup
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
name|status
operator|.
name|StatusPageSuggestionsAction
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
specifier|private
name|Map
argument_list|<
name|StudentPriority
argument_list|,
name|String
argument_list|>
name|iPriorityStudentGroupReference
init|=
literal|null
decl_stmt|;
specifier|private
name|Map
argument_list|<
name|StudentPriority
argument_list|,
name|Query
argument_list|>
name|iPriorityStudentQuery
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
name|XOffering
name|offering
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
argument_list|,
name|server
operator|.
name|getAcademicSession
argument_list|()
operator|.
name|getUniqueId
argument_list|()
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
if|if
condition|(
name|Customization
operator|.
name|WaitListValidationProvider
operator|.
name|hasProvider
argument_list|()
condition|)
block|{
for|for
control|(
name|XCourse
name|course
range|:
name|offering
operator|.
name|getCourses
argument_list|()
control|)
block|{
if|if
condition|(
operator|!
name|request
operator|.
name|hasCourse
argument_list|(
name|course
operator|.
name|getCourseId
argument_list|()
argument_list|)
condition|)
continue|continue;
name|XOverride
name|override
init|=
name|request
operator|.
name|getOverride
argument_list|(
name|course
argument_list|)
decl_stmt|;
if|if
condition|(
name|override
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
literal|"TBD"
operator|.
name|equals
argument_list|(
name|override
operator|.
name|getExternalId
argument_list|()
argument_list|)
condition|)
return|return
literal|false
return|;
comment|// override not requested --> ignore
name|WaitListValidationProvider
name|wp
init|=
name|Customization
operator|.
name|WaitListValidationProvider
operator|.
name|getProvider
argument_list|()
decl_stmt|;
if|if
condition|(
name|wp
operator|.
name|updateStudent
argument_list|(
name|server
argument_list|,
name|helper
argument_list|,
name|student
argument_list|,
name|helper
operator|.
name|getAction
argument_list|()
argument_list|)
condition|)
name|override
operator|=
name|request
operator|.
name|getOverride
argument_list|(
name|course
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|override
operator|!=
literal|null
operator|&&
operator|!
name|override
operator|.
name|isApproved
argument_list|()
condition|)
return|return
literal|false
return|;
block|}
block|}
return|return
literal|true
return|;
block|}
specifier|public
name|StudentPriority
name|getStudentPriority
parameter_list|(
name|XStudent
name|student
parameter_list|,
name|OnlineSectioningServer
name|server
parameter_list|,
name|OnlineSectioningHelper
name|helper
parameter_list|)
block|{
if|if
condition|(
name|iPriorityStudentGroupReference
operator|==
literal|null
condition|)
block|{
name|iPriorityStudentGroupReference
operator|=
operator|new
name|HashMap
argument_list|<
name|StudentPriority
argument_list|,
name|String
argument_list|>
argument_list|()
expr_stmt|;
name|iPriorityStudentQuery
operator|=
operator|new
name|HashMap
argument_list|<
name|StudentPriority
argument_list|,
name|Query
argument_list|>
argument_list|()
expr_stmt|;
name|DataProperties
name|config
init|=
name|server
operator|.
name|getConfig
argument_list|()
decl_stmt|;
for|for
control|(
name|StudentPriority
name|priority
range|:
name|StudentPriority
operator|.
name|values
argument_list|()
control|)
block|{
if|if
condition|(
name|priority
operator|==
name|StudentPriority
operator|.
name|Normal
condition|)
break|break;
name|String
name|priorityStudentFilter
init|=
name|config
operator|.
name|getProperty
argument_list|(
literal|"Load."
operator|+
name|priority
operator|.
name|name
argument_list|()
operator|+
literal|"StudentFilter"
argument_list|,
literal|null
argument_list|)
decl_stmt|;
if|if
condition|(
name|priorityStudentFilter
operator|!=
literal|null
operator|&&
operator|!
name|priorityStudentFilter
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|Query
name|q
init|=
operator|new
name|Query
argument_list|(
name|priorityStudentFilter
argument_list|)
decl_stmt|;
name|iPriorityStudentQuery
operator|.
name|put
argument_list|(
name|priority
argument_list|,
name|q
argument_list|)
expr_stmt|;
block|}
name|String
name|groupRef
init|=
name|config
operator|.
name|getProperty
argument_list|(
literal|"Load."
operator|+
name|priority
operator|.
name|name
argument_list|()
operator|+
literal|"StudentGroupReference"
argument_list|,
literal|null
argument_list|)
decl_stmt|;
if|if
condition|(
name|groupRef
operator|!=
literal|null
operator|&&
operator|!
name|groupRef
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|iPriorityStudentGroupReference
operator|.
name|put
argument_list|(
name|priority
argument_list|,
name|groupRef
argument_list|)
expr_stmt|;
block|}
block|}
block|}
for|for
control|(
name|StudentPriority
name|priority
range|:
name|StudentPriority
operator|.
name|values
argument_list|()
control|)
block|{
if|if
condition|(
name|priority
operator|==
name|StudentPriority
operator|.
name|Normal
condition|)
break|break;
name|Query
name|query
init|=
name|iPriorityStudentQuery
operator|.
name|get
argument_list|(
name|priority
argument_list|)
decl_stmt|;
name|String
name|groupRef
init|=
name|iPriorityStudentGroupReference
operator|.
name|get
argument_list|(
name|priority
argument_list|)
decl_stmt|;
if|if
condition|(
name|query
operator|!=
literal|null
operator|&&
name|query
operator|.
name|match
argument_list|(
operator|new
name|StatusPageSuggestionsAction
operator|.
name|StudentMatcher
argument_list|(
name|student
argument_list|,
name|server
operator|.
name|getAcademicSession
argument_list|()
operator|.
name|getDefaultSectioningStatus
argument_list|()
argument_list|,
name|server
argument_list|,
literal|false
argument_list|)
argument_list|)
condition|)
block|{
return|return
name|priority
return|;
block|}
if|else if
condition|(
name|groupRef
operator|!=
literal|null
condition|)
block|{
for|for
control|(
name|XGroup
name|g
range|:
name|student
operator|.
name|getGroups
argument_list|()
control|)
block|{
if|if
condition|(
name|groupRef
operator|.
name|equals
argument_list|(
name|g
operator|.
name|getAbbreviation
argument_list|()
argument_list|)
condition|)
block|{
return|return
name|priority
return|;
block|}
block|}
block|}
block|}
return|return
name|StudentPriority
operator|.
name|Normal
return|;
block|}
block|}
end_class

end_unit

