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
name|model
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|HashSet
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
name|Set
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
name|Choice
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
name|Config
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
name|Course
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
name|Request
operator|.
name|RequestPriority
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
name|Section
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
name|CourseRequestInterface
operator|.
name|RequestedCourse
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
operator|.
name|Option
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
name|base
operator|.
name|BaseCourseDemand
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
name|CourseDemandDAO
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|CourseDemand
extends|extends
name|BaseCourseDemand
implements|implements
name|Comparable
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|1L
decl_stmt|;
specifier|public
specifier|static
enum|enum
name|Critical
block|{
name|NORMAL
argument_list|(
name|RequestPriority
operator|.
name|Normal
argument_list|)
block|,
name|CRITICAL
argument_list|(
name|RequestPriority
operator|.
name|Critical
argument_list|)
block|,
name|IMPORTANT
argument_list|(
name|RequestPriority
operator|.
name|Important
argument_list|)
block|, 		;
name|RequestPriority
name|iPriority
decl_stmt|;
name|Critical
parameter_list|(
name|RequestPriority
name|rp
parameter_list|)
block|{
name|iPriority
operator|=
name|rp
expr_stmt|;
block|}
specifier|public
name|RequestPriority
name|toRequestPriority
parameter_list|()
block|{
return|return
name|iPriority
return|;
block|}
specifier|public
specifier|static
name|Critical
name|fromRequestPriority
parameter_list|(
name|RequestPriority
name|rp
parameter_list|)
block|{
if|if
condition|(
name|rp
operator|==
literal|null
condition|)
return|return
name|Critical
operator|.
name|NORMAL
return|;
for|for
control|(
name|Critical
name|c
range|:
name|Critical
operator|.
name|values
argument_list|()
control|)
if|if
condition|(
name|c
operator|.
name|toRequestPriority
argument_list|()
operator|==
name|rp
condition|)
return|return
name|c
return|;
return|return
name|Critical
operator|.
name|NORMAL
return|;
block|}
block|}
comment|/*[CONSTRUCTOR MARKER BEGIN]*/
specifier|public
name|CourseDemand
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
block|}
comment|/** 	 * Constructor for primary key 	 */
specifier|public
name|CourseDemand
parameter_list|(
name|java
operator|.
name|lang
operator|.
name|Long
name|uniqueId
parameter_list|)
block|{
name|super
argument_list|(
name|uniqueId
argument_list|)
expr_stmt|;
block|}
comment|/*[CONSTRUCTOR MARKER END]*/
specifier|public
name|int
name|compareTo
parameter_list|(
name|Object
name|o
parameter_list|)
block|{
if|if
condition|(
name|o
operator|==
literal|null
operator|||
operator|!
operator|(
name|o
operator|instanceof
name|CourseDemand
operator|)
condition|)
return|return
operator|-
literal|1
return|;
name|CourseDemand
name|cd
init|=
operator|(
name|CourseDemand
operator|)
name|o
decl_stmt|;
name|int
name|cmp
init|=
operator|(
name|isAlternative
argument_list|()
operator|.
name|booleanValue
argument_list|()
operator|==
name|cd
operator|.
name|isAlternative
argument_list|()
operator|.
name|booleanValue
argument_list|()
condition|?
literal|0
else|:
name|isAlternative
argument_list|()
operator|.
name|booleanValue
argument_list|()
condition|?
literal|1
else|:
operator|-
literal|1
operator|)
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
name|cmp
operator|=
name|getPriority
argument_list|()
operator|.
name|compareTo
argument_list|(
name|cd
operator|.
name|getPriority
argument_list|()
argument_list|)
expr_stmt|;
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
operator|(
name|getUniqueId
argument_list|()
operator|==
literal|null
condition|?
operator|new
name|Long
argument_list|(
operator|-
literal|1
argument_list|)
else|:
name|getUniqueId
argument_list|()
operator|)
operator|.
name|compareTo
argument_list|(
name|cd
operator|.
name|getUniqueId
argument_list|()
operator|==
literal|null
condition|?
operator|-
literal|1
else|:
name|cd
operator|.
name|getUniqueId
argument_list|()
argument_list|)
return|;
block|}
specifier|public
specifier|static
name|List
name|findAll
parameter_list|(
name|Long
name|sessionId
parameter_list|)
block|{
return|return
name|findAll
argument_list|(
name|CourseDemandDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|getSession
argument_list|()
argument_list|,
name|sessionId
argument_list|)
return|;
block|}
specifier|public
specifier|static
name|List
name|findAll
parameter_list|(
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
parameter_list|,
name|Long
name|sessionId
parameter_list|)
block|{
return|return
name|hibSession
operator|.
name|createQuery
argument_list|(
literal|"select c from CourseDemand c where c.student.session.uniqueId=:sessionId"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"sessionId"
argument_list|,
name|sessionId
operator|.
name|longValue
argument_list|()
argument_list|)
operator|.
name|list
argument_list|()
return|;
block|}
specifier|public
name|void
name|updatePreferences
parameter_list|(
name|org
operator|.
name|cpsolver
operator|.
name|studentsct
operator|.
name|model
operator|.
name|CourseRequest
name|request
parameter_list|,
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
parameter_list|)
block|{
if|if
condition|(
name|getCourseRequests
argument_list|()
operator|==
literal|null
operator|||
name|getCourseRequests
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
return|return;
if|if
condition|(
operator|!
name|request
operator|.
name|getSelectedChoices
argument_list|()
operator|.
name|isEmpty
argument_list|()
operator|||
operator|!
name|request
operator|.
name|getRequiredChoices
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
for|for
control|(
name|Course
name|course
range|:
name|request
operator|.
name|getCourses
argument_list|()
control|)
block|{
name|RequestedCourse
name|rc
init|=
operator|new
name|RequestedCourse
argument_list|()
decl_stmt|;
name|Set
argument_list|<
name|Long
argument_list|>
name|im
init|=
operator|new
name|HashSet
argument_list|<
name|Long
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|Choice
name|choice
range|:
name|request
operator|.
name|getSelectedChoices
argument_list|()
control|)
block|{
if|if
condition|(
operator|!
name|course
operator|.
name|getOffering
argument_list|()
operator|.
name|equals
argument_list|(
name|choice
operator|.
name|getOffering
argument_list|()
argument_list|)
condition|)
continue|continue;
if|if
condition|(
name|choice
operator|.
name|getSectionId
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|Section
name|section
init|=
name|choice
operator|.
name|getOffering
argument_list|()
operator|.
name|getSection
argument_list|(
name|choice
operator|.
name|getSectionId
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|section
operator|!=
literal|null
condition|)
name|rc
operator|.
name|setSelectedClass
argument_list|(
name|section
operator|.
name|getId
argument_list|()
argument_list|,
name|section
operator|.
name|getName
argument_list|(
name|course
operator|.
name|getId
argument_list|()
argument_list|)
argument_list|,
literal|false
argument_list|,
literal|true
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|choice
operator|.
name|getConfigId
argument_list|()
operator|!=
literal|null
condition|)
block|{
for|for
control|(
name|Config
name|config
range|:
name|choice
operator|.
name|getOffering
argument_list|()
operator|.
name|getConfigs
argument_list|()
control|)
block|{
if|if
condition|(
name|choice
operator|.
name|getConfigId
argument_list|()
operator|.
name|equals
argument_list|(
name|config
operator|.
name|getId
argument_list|()
argument_list|)
operator|&&
name|config
operator|.
name|getInstructionalMethodId
argument_list|()
operator|!=
literal|null
operator|&&
name|im
operator|.
name|add
argument_list|(
name|config
operator|.
name|getInstructionalMethodId
argument_list|()
argument_list|)
condition|)
block|{
name|rc
operator|.
name|setSelectedIntructionalMethod
argument_list|(
name|config
operator|.
name|getInstructionalMethodId
argument_list|()
argument_list|,
name|config
operator|.
name|getInstructionalMethodName
argument_list|()
argument_list|,
literal|false
argument_list|,
literal|true
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
for|for
control|(
name|Choice
name|choice
range|:
name|request
operator|.
name|getRequiredChoices
argument_list|()
control|)
block|{
if|if
condition|(
operator|!
name|course
operator|.
name|getOffering
argument_list|()
operator|.
name|equals
argument_list|(
name|choice
operator|.
name|getOffering
argument_list|()
argument_list|)
condition|)
continue|continue;
if|if
condition|(
name|choice
operator|.
name|getSectionId
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|Section
name|section
init|=
name|choice
operator|.
name|getOffering
argument_list|()
operator|.
name|getSection
argument_list|(
name|choice
operator|.
name|getSectionId
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|section
operator|!=
literal|null
condition|)
name|rc
operator|.
name|setSelectedClass
argument_list|(
name|section
operator|.
name|getId
argument_list|()
argument_list|,
name|section
operator|.
name|getName
argument_list|(
name|course
operator|.
name|getId
argument_list|()
argument_list|)
argument_list|,
literal|true
argument_list|,
literal|true
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|choice
operator|.
name|getConfigId
argument_list|()
operator|!=
literal|null
condition|)
block|{
for|for
control|(
name|Config
name|config
range|:
name|choice
operator|.
name|getOffering
argument_list|()
operator|.
name|getConfigs
argument_list|()
control|)
block|{
if|if
condition|(
name|choice
operator|.
name|getConfigId
argument_list|()
operator|.
name|equals
argument_list|(
name|config
operator|.
name|getId
argument_list|()
argument_list|)
operator|&&
name|config
operator|.
name|getInstructionalMethodId
argument_list|()
operator|!=
literal|null
operator|&&
name|im
operator|.
name|add
argument_list|(
name|config
operator|.
name|getInstructionalMethodId
argument_list|()
argument_list|)
condition|)
block|{
name|rc
operator|.
name|setSelectedIntructionalMethod
argument_list|(
name|config
operator|.
name|getInstructionalMethodId
argument_list|()
argument_list|,
name|config
operator|.
name|getInstructionalMethodName
argument_list|()
argument_list|,
literal|true
argument_list|,
literal|true
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
for|for
control|(
name|CourseRequest
name|cr
range|:
name|getCourseRequests
argument_list|()
control|)
if|if
condition|(
name|cr
operator|.
name|getCourseOffering
argument_list|()
operator|.
name|getUniqueId
argument_list|()
operator|.
name|equals
argument_list|(
name|course
operator|.
name|getId
argument_list|()
argument_list|)
condition|)
name|cr
operator|.
name|updatePreferences
argument_list|(
name|rc
argument_list|,
name|hibSession
argument_list|)
expr_stmt|;
block|}
block|}
block|}
specifier|public
name|CourseOffering
name|getFirstChoiceCourseOffering
parameter_list|()
block|{
name|CourseRequest
name|ret
init|=
literal|null
decl_stmt|;
for|for
control|(
name|CourseRequest
name|cr
range|:
name|getCourseRequests
argument_list|()
control|)
block|{
if|if
condition|(
name|ret
operator|==
literal|null
operator|||
name|cr
operator|.
name|getOrder
argument_list|()
operator|<
name|ret
operator|.
name|getOrder
argument_list|()
condition|)
name|ret
operator|=
name|cr
expr_stmt|;
block|}
return|return
operator|(
name|ret
operator|==
literal|null
condition|?
literal|null
else|:
name|ret
operator|.
name|getCourseOffering
argument_list|()
operator|)
return|;
block|}
specifier|public
name|Critical
name|getEffectiveCritical
parameter_list|()
block|{
if|if
condition|(
name|getCriticalOverride
argument_list|()
operator|!=
literal|null
condition|)
return|return
name|Critical
operator|.
name|values
argument_list|()
index|[
name|getCriticalOverride
argument_list|()
index|]
return|;
if|if
condition|(
name|getCritical
argument_list|()
operator|!=
literal|null
condition|)
return|return
name|Critical
operator|.
name|values
argument_list|()
index|[
name|getCritical
argument_list|()
index|]
return|;
return|return
name|Critical
operator|.
name|NORMAL
return|;
block|}
specifier|public
name|boolean
name|isCriticalOrImportant
parameter_list|()
block|{
switch|switch
condition|(
name|getEffectiveCritical
argument_list|()
condition|)
block|{
case|case
name|CRITICAL
case|:
return|return
literal|true
return|;
case|case
name|IMPORTANT
case|:
return|return
literal|true
return|;
default|default:
return|return
literal|false
return|;
block|}
block|}
specifier|public
name|boolean
name|effectiveNoSub
parameter_list|()
block|{
if|if
condition|(
name|getNoSub
argument_list|()
operator|==
literal|null
condition|)
return|return
literal|false
return|;
if|if
condition|(
name|getNoSub
argument_list|()
condition|)
block|{
name|StudentSectioningStatus
name|status
init|=
name|getStudent
argument_list|()
operator|.
name|getEffectiveStatus
argument_list|()
decl_stmt|;
return|return
operator|(
name|status
operator|==
literal|null
operator|||
name|status
operator|.
name|hasOption
argument_list|(
name|Option
operator|.
name|nosubs
argument_list|)
operator|)
return|;
block|}
return|return
literal|false
return|;
block|}
specifier|public
name|boolean
name|effectiveWaitList
parameter_list|()
block|{
if|if
condition|(
name|getWaitlist
argument_list|()
operator|==
literal|null
condition|)
return|return
literal|false
return|;
if|if
condition|(
name|getWaitlist
argument_list|()
condition|)
block|{
name|StudentSectioningStatus
name|status
init|=
name|getStudent
argument_list|()
operator|.
name|getEffectiveStatus
argument_list|()
decl_stmt|;
if|if
condition|(
name|status
operator|==
literal|null
operator|||
name|status
operator|.
name|hasOption
argument_list|(
name|Option
operator|.
name|waitlist
argument_list|)
condition|)
block|{
name|CourseRequest
name|firstRequest
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|getCourseRequests
argument_list|()
operator|!=
literal|null
condition|)
for|for
control|(
name|CourseRequest
name|cr
range|:
name|getCourseRequests
argument_list|()
control|)
block|{
if|if
condition|(
name|firstRequest
operator|==
literal|null
operator|||
name|firstRequest
operator|.
name|getOrder
argument_list|()
operator|>
name|cr
operator|.
name|getOrder
argument_list|()
condition|)
name|firstRequest
operator|=
name|cr
expr_stmt|;
block|}
return|return
name|firstRequest
operator|!=
literal|null
operator|&&
name|firstRequest
operator|.
name|getCourseOffering
argument_list|()
operator|.
name|getInstructionalOffering
argument_list|()
operator|.
name|effectiveWaitList
argument_list|()
return|;
block|}
block|}
return|return
literal|false
return|;
block|}
specifier|public
name|boolean
name|isEnrolled
parameter_list|()
block|{
for|for
control|(
name|CourseRequest
name|cr
range|:
name|getCourseRequests
argument_list|()
control|)
for|for
control|(
name|StudentClassEnrollment
name|e
range|:
name|getStudent
argument_list|()
operator|.
name|getClassEnrollments
argument_list|()
control|)
if|if
condition|(
name|cr
operator|.
name|getCourseOffering
argument_list|()
operator|.
name|equals
argument_list|(
name|e
operator|.
name|getCourseOffering
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
block|}
end_class

end_unit

