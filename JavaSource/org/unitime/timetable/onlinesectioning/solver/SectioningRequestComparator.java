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
name|solver
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Comparator
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|SectioningRequestComparator
implements|implements
name|Comparator
argument_list|<
name|SectioningRequest
argument_list|>
block|{
specifier|protected
name|int
name|compareBothAssignedOrNotAssigned
parameter_list|(
name|SectioningRequest
name|s
parameter_list|,
name|SectioningRequest
name|r
parameter_list|)
block|{
comment|// Request Priority
if|if
condition|(
name|s
operator|.
name|getRequestPriority
argument_list|()
operator|!=
name|r
operator|.
name|getRequestPriority
argument_list|()
condition|)
return|return
name|s
operator|.
name|getRequestPriority
argument_list|()
operator|.
name|ordinal
argument_list|()
operator|<
name|r
operator|.
name|getRequestPriority
argument_list|()
operator|.
name|ordinal
argument_list|()
condition|?
operator|-
literal|1
else|:
literal|1
return|;
comment|// Student Priority
if|if
condition|(
name|s
operator|.
name|getStudentPriority
argument_list|()
operator|!=
name|r
operator|.
name|getStudentPriority
argument_list|()
condition|)
return|return
name|s
operator|.
name|getStudentPriority
argument_list|()
operator|.
name|ordinal
argument_list|()
operator|<
name|r
operator|.
name|getStudentPriority
argument_list|()
operator|.
name|ordinal
argument_list|()
condition|?
operator|-
literal|1
else|:
literal|1
return|;
return|return
literal|0
return|;
block|}
specifier|protected
name|int
name|compareBothNotAssigned
parameter_list|(
name|SectioningRequest
name|s
parameter_list|,
name|SectioningRequest
name|r
parameter_list|)
block|{
comment|// Alternativity (first choice before first alternative, etc.)
if|if
condition|(
name|s
operator|.
name|getAlternativity
argument_list|()
operator|!=
name|r
operator|.
name|getAlternativity
argument_list|()
condition|)
return|return
name|s
operator|.
name|getAlternativity
argument_list|()
operator|<
name|r
operator|.
name|getAlternativity
argument_list|()
condition|?
operator|-
literal|1
else|:
literal|1
return|;
comment|// Use wait-listed time stamp
if|if
condition|(
name|s
operator|.
name|getRequest
argument_list|()
operator|.
name|getWaitListedTimeStamp
argument_list|()
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
name|r
operator|.
name|getRequest
argument_list|()
operator|.
name|getWaitListedTimeStamp
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|int
name|cmp
init|=
name|s
operator|.
name|getRequest
argument_list|()
operator|.
name|getWaitListedTimeStamp
argument_list|()
operator|.
name|compareTo
argument_list|(
name|r
operator|.
name|getRequest
argument_list|()
operator|.
name|getWaitListedTimeStamp
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
block|}
else|else
block|{
return|return
literal|1
return|;
block|}
block|}
if|else if
condition|(
name|r
operator|.
name|getRequest
argument_list|()
operator|.
name|getWaitListedTimeStamp
argument_list|()
operator|!=
literal|null
condition|)
block|{
return|return
operator|-
literal|1
return|;
block|}
return|return
literal|0
return|;
block|}
specifier|protected
name|int
name|compareBothAssigned
parameter_list|(
name|SectioningRequest
name|s
parameter_list|,
name|SectioningRequest
name|r
parameter_list|)
block|{
comment|// Check individual reservations
if|if
condition|(
name|s
operator|.
name|hasIndividualReservation
argument_list|()
operator|&&
operator|!
name|r
operator|.
name|hasIndividualReservation
argument_list|()
condition|)
return|return
operator|-
literal|1
return|;
if|if
condition|(
operator|!
name|s
operator|.
name|hasIndividualReservation
argument_list|()
operator|&&
name|r
operator|.
name|hasIndividualReservation
argument_list|()
condition|)
return|return
literal|1
return|;
comment|// Substitute requests last
if|if
condition|(
name|s
operator|.
name|getRequest
argument_list|()
operator|.
name|isAlternative
argument_list|()
operator|&&
operator|!
name|r
operator|.
name|getRequest
argument_list|()
operator|.
name|isAlternative
argument_list|()
condition|)
return|return
literal|1
return|;
if|if
condition|(
operator|!
name|s
operator|.
name|getRequest
argument_list|()
operator|.
name|isAlternative
argument_list|()
operator|&&
name|r
operator|.
name|getRequest
argument_list|()
operator|.
name|isAlternative
argument_list|()
condition|)
return|return
operator|-
literal|1
return|;
comment|// Use priority
name|int
name|cmp
init|=
name|Integer
operator|.
name|compare
argument_list|(
name|s
operator|.
name|getRequest
argument_list|()
operator|.
name|getPriority
argument_list|()
argument_list|,
name|r
operator|.
name|getRequest
argument_list|()
operator|.
name|getPriority
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
comment|// Alternativity (first choice before first alternative, etc.)
if|if
condition|(
name|s
operator|.
name|getAlternativity
argument_list|()
operator|!=
name|r
operator|.
name|getAlternativity
argument_list|()
condition|)
return|return
name|s
operator|.
name|getAlternativity
argument_list|()
operator|<
name|r
operator|.
name|getAlternativity
argument_list|()
condition|?
operator|-
literal|1
else|:
literal|1
return|;
return|return
literal|0
return|;
block|}
specifier|protected
name|int
name|compareFallBack
parameter_list|(
name|SectioningRequest
name|s
parameter_list|,
name|SectioningRequest
name|r
parameter_list|)
block|{
comment|// Use request time stamp
if|if
condition|(
name|s
operator|.
name|getRequest
argument_list|()
operator|.
name|getTimeStamp
argument_list|()
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
name|r
operator|.
name|getRequest
argument_list|()
operator|.
name|getTimeStamp
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|int
name|cmp
init|=
name|s
operator|.
name|getRequest
argument_list|()
operator|.
name|getTimeStamp
argument_list|()
operator|.
name|compareTo
argument_list|(
name|r
operator|.
name|getRequest
argument_list|()
operator|.
name|getTimeStamp
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
block|}
else|else
block|{
return|return
literal|1
return|;
block|}
block|}
if|else if
condition|(
name|r
operator|.
name|getRequest
argument_list|()
operator|.
name|getTimeStamp
argument_list|()
operator|!=
literal|null
condition|)
block|{
return|return
operator|-
literal|1
return|;
block|}
return|return
name|Long
operator|.
name|compare
argument_list|(
name|s
operator|.
name|getRequest
argument_list|()
operator|.
name|getStudentId
argument_list|()
argument_list|,
name|r
operator|.
name|getRequest
argument_list|()
operator|.
name|getStudentId
argument_list|()
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|int
name|compare
parameter_list|(
name|SectioningRequest
name|s
parameter_list|,
name|SectioningRequest
name|r
parameter_list|)
block|{
comment|// Requests with last enrollment (recently unassigned requests) have priority
if|if
condition|(
name|s
operator|.
name|getLastEnrollment
argument_list|()
operator|==
literal|null
operator|&&
name|r
operator|.
name|getLastEnrollment
argument_list|()
operator|!=
literal|null
condition|)
return|return
literal|1
return|;
if|if
condition|(
name|s
operator|.
name|getLastEnrollment
argument_list|()
operator|!=
literal|null
operator|&&
name|r
operator|.
name|getLastEnrollment
argument_list|()
operator|==
literal|null
condition|)
return|return
operator|-
literal|1
return|;
name|int
name|cmp
init|=
name|compareBothAssignedOrNotAssigned
argument_list|(
name|s
argument_list|,
name|r
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
if|if
condition|(
name|s
operator|.
name|getLastEnrollment
argument_list|()
operator|==
literal|null
condition|)
block|{
name|cmp
operator|=
name|compareBothNotAssigned
argument_list|(
name|s
argument_list|,
name|r
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|cmp
operator|=
name|compareBothAssigned
argument_list|(
name|s
argument_list|,
name|r
argument_list|)
expr_stmt|;
block|}
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
name|compareFallBack
argument_list|(
name|s
argument_list|,
name|r
argument_list|)
return|;
block|}
block|}
end_class

end_unit

