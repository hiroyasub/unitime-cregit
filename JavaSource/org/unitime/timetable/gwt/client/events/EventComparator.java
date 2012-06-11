begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.4 (University Timetabling Application)  * Copyright (C) 2012, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
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
name|events
package|;
end_package

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
name|EventInterface
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
name|EventInterface
operator|.
name|EventType
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
name|EventInterface
operator|.
name|MeetingConglictInterface
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
name|EventInterface
operator|.
name|MeetingInterface
import|;
end_import

begin_class
specifier|public
class|class
name|EventComparator
block|{
specifier|public
specifier|static
enum|enum
name|EventMeetingSortBy
block|{
name|NAME
block|,
name|SECTION
block|,
name|TYPE
block|,
name|DATE
block|,
name|PUBLISHED_TIME
block|,
name|ALLOCATED_TIME
block|,
name|SETUP_TIME
block|,
name|TEARDOWN_TIME
block|,
name|LOCATION
block|,
name|CAPACITY
block|,
name|SPONSOR
block|,
name|MAIN_CONTACT
block|,
name|APPROVAL
block|,
name|LIMIT
block|,
name|ENROLLMENT
block|}
specifier|protected
specifier|static
name|int
name|compareByName
parameter_list|(
name|EventInterface
name|e1
parameter_list|,
name|EventInterface
name|e2
parameter_list|)
block|{
return|return
name|compare
argument_list|(
name|e1
operator|.
name|getName
argument_list|()
argument_list|,
name|e2
operator|.
name|getName
argument_list|()
argument_list|)
return|;
block|}
specifier|protected
specifier|static
name|int
name|compareBySection
parameter_list|(
name|EventInterface
name|e1
parameter_list|,
name|EventInterface
name|e2
parameter_list|)
block|{
if|if
condition|(
name|e1
operator|.
name|hasExternalIds
argument_list|()
condition|)
block|{
if|if
condition|(
name|e2
operator|.
name|hasExternalIds
argument_list|()
condition|)
block|{
name|int
name|cmp
init|=
name|e1
operator|.
name|getExternalIds
argument_list|()
operator|.
name|get
argument_list|(
literal|0
argument_list|)
operator|.
name|compareTo
argument_list|(
name|e2
operator|.
name|getExternalIds
argument_list|()
operator|.
name|get
argument_list|(
literal|0
argument_list|)
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
return|return
operator|-
literal|1
return|;
block|}
if|else if
condition|(
name|e2
operator|.
name|hasExternalIds
argument_list|()
condition|)
return|return
literal|1
return|;
return|return
name|compare
argument_list|(
name|e1
operator|.
name|getSectionNumber
argument_list|()
argument_list|,
name|e2
operator|.
name|getSectionNumber
argument_list|()
argument_list|)
return|;
block|}
specifier|protected
specifier|static
name|int
name|compareByType
parameter_list|(
name|EventInterface
name|e1
parameter_list|,
name|EventInterface
name|e2
parameter_list|)
block|{
name|int
name|cmp
init|=
operator|(
name|e1
operator|.
name|getType
argument_list|()
operator|==
literal|null
condition|?
name|e2
operator|.
name|getType
argument_list|()
operator|==
literal|null
condition|?
literal|0
else|:
operator|-
literal|1
else|:
name|e2
operator|.
name|getType
argument_list|()
operator|==
literal|null
condition|?
literal|1
else|:
name|e1
operator|.
name|getType
argument_list|()
operator|.
name|compareTo
argument_list|(
name|e2
operator|.
name|getType
argument_list|()
argument_list|)
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
return|return
name|compare
argument_list|(
name|e1
operator|.
name|getInstructionType
argument_list|()
argument_list|,
name|e2
operator|.
name|getInstructionType
argument_list|()
argument_list|)
return|;
block|}
specifier|protected
specifier|static
name|int
name|compareBySponsor
parameter_list|(
name|EventInterface
name|e1
parameter_list|,
name|EventInterface
name|e2
parameter_list|)
block|{
name|int
name|cmp
init|=
name|compare
argument_list|(
name|e1
operator|.
name|getInstructorNames
argument_list|(
literal|"|"
argument_list|)
argument_list|,
name|e2
operator|.
name|getInstructorNames
argument_list|(
literal|"|"
argument_list|)
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
name|compare
argument_list|(
name|e1
operator|.
name|hasSponsor
argument_list|()
condition|?
name|e1
operator|.
name|getSponsor
argument_list|()
operator|.
name|getName
argument_list|()
else|:
literal|null
argument_list|,
name|e2
operator|.
name|hasSponsor
argument_list|()
condition|?
name|e2
operator|.
name|getSponsor
argument_list|()
operator|.
name|getName
argument_list|()
else|:
literal|null
argument_list|)
return|;
block|}
specifier|protected
specifier|static
name|int
name|compareByMainContact
parameter_list|(
name|EventInterface
name|e1
parameter_list|,
name|EventInterface
name|e2
parameter_list|)
block|{
return|return
name|compare
argument_list|(
name|e1
operator|.
name|hasContact
argument_list|()
condition|?
name|e1
operator|.
name|getContact
argument_list|()
operator|.
name|getName
argument_list|()
else|:
literal|null
argument_list|,
name|e2
operator|.
name|hasContact
argument_list|()
condition|?
name|e2
operator|.
name|getContact
argument_list|()
operator|.
name|getName
argument_list|()
else|:
literal|null
argument_list|)
return|;
block|}
specifier|protected
specifier|static
name|int
name|compareByLimit
parameter_list|(
name|EventInterface
name|e1
parameter_list|,
name|EventInterface
name|e2
parameter_list|)
block|{
return|return
operator|-
operator|(
name|e1
operator|.
name|hasMaxCapacity
argument_list|()
condition|?
name|e1
operator|.
name|getMaxCapacity
argument_list|()
else|:
operator|new
name|Integer
argument_list|(
literal|0
argument_list|)
operator|)
operator|.
name|compareTo
argument_list|(
name|e2
operator|.
name|hasMaxCapacity
argument_list|()
condition|?
name|e2
operator|.
name|getMaxCapacity
argument_list|()
else|:
operator|new
name|Integer
argument_list|(
literal|0
argument_list|)
argument_list|)
return|;
block|}
specifier|protected
specifier|static
name|int
name|compareByEnrollment
parameter_list|(
name|EventInterface
name|e1
parameter_list|,
name|EventInterface
name|e2
parameter_list|)
block|{
return|return
operator|-
operator|(
name|e1
operator|.
name|hasEnrollment
argument_list|()
condition|?
name|e1
operator|.
name|getEnrollment
argument_list|()
else|:
operator|new
name|Integer
argument_list|(
literal|0
argument_list|)
operator|)
operator|.
name|compareTo
argument_list|(
name|e2
operator|.
name|hasEnrollment
argument_list|()
condition|?
name|e2
operator|.
name|getEnrollment
argument_list|()
else|:
operator|new
name|Integer
argument_list|(
literal|0
argument_list|)
argument_list|)
return|;
block|}
specifier|public
specifier|static
name|int
name|compareFallback
parameter_list|(
name|EventInterface
name|e1
parameter_list|,
name|EventInterface
name|e2
parameter_list|)
block|{
name|int
name|cmp
init|=
name|compareByName
argument_list|(
name|e1
argument_list|,
name|e2
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
name|cmp
operator|=
name|compareBySection
argument_list|(
name|e1
argument_list|,
name|e2
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
name|cmp
operator|=
name|compareByType
argument_list|(
name|e1
argument_list|,
name|e2
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
name|e1
operator|.
name|compareTo
argument_list|(
name|e2
argument_list|)
return|;
block|}
specifier|public
specifier|static
name|int
name|compareEvents
parameter_list|(
name|EventInterface
name|e1
parameter_list|,
name|EventInterface
name|e2
parameter_list|,
name|EventMeetingSortBy
name|sortBy
parameter_list|)
block|{
switch|switch
condition|(
name|sortBy
condition|)
block|{
case|case
name|NAME
case|:
return|return
name|compareByName
argument_list|(
name|e1
argument_list|,
name|e2
argument_list|)
return|;
case|case
name|SECTION
case|:
return|return
name|compareBySection
argument_list|(
name|e1
argument_list|,
name|e2
argument_list|)
return|;
case|case
name|TYPE
case|:
return|return
name|compareByType
argument_list|(
name|e1
argument_list|,
name|e2
argument_list|)
return|;
case|case
name|SPONSOR
case|:
return|return
name|compareBySponsor
argument_list|(
name|e1
argument_list|,
name|e2
argument_list|)
return|;
case|case
name|MAIN_CONTACT
case|:
return|return
name|compareByMainContact
argument_list|(
name|e1
argument_list|,
name|e2
argument_list|)
return|;
case|case
name|LIMIT
case|:
return|return
name|compareByLimit
argument_list|(
name|e1
argument_list|,
name|e2
argument_list|)
return|;
case|case
name|ENROLLMENT
case|:
return|return
name|compareByEnrollment
argument_list|(
name|e1
argument_list|,
name|e2
argument_list|)
return|;
block|}
return|return
literal|0
return|;
block|}
specifier|protected
specifier|static
name|int
name|compateByApproval
parameter_list|(
name|MeetingInterface
name|m1
parameter_list|,
name|MeetingInterface
name|m2
parameter_list|)
block|{
if|if
condition|(
name|m1
operator|.
name|getId
argument_list|()
operator|==
literal|null
operator|&&
name|m2
operator|.
name|getId
argument_list|()
operator|!=
literal|null
condition|)
return|return
operator|-
literal|1
return|;
if|if
condition|(
name|m1
operator|.
name|getId
argument_list|()
operator|!=
literal|null
operator|&&
name|m2
operator|.
name|getId
argument_list|()
operator|==
literal|null
condition|)
return|return
literal|1
return|;
if|if
condition|(
name|m1
operator|.
name|getApprovalDate
argument_list|()
operator|==
literal|null
condition|)
block|{
return|return
operator|(
name|m2
operator|.
name|getApprovalDate
argument_list|()
operator|==
literal|null
condition|?
literal|0
else|:
operator|-
literal|1
operator|)
return|;
block|}
else|else
block|{
return|return
operator|(
name|m2
operator|.
name|getApprovalDate
argument_list|()
operator|==
literal|null
condition|?
literal|1
else|:
name|m1
operator|.
name|getApprovalDate
argument_list|()
operator|.
name|compareTo
argument_list|(
name|m2
operator|.
name|getApprovalDate
argument_list|()
argument_list|)
operator|)
return|;
block|}
block|}
specifier|protected
specifier|static
name|int
name|compareByName
parameter_list|(
name|MeetingInterface
name|m1
parameter_list|,
name|MeetingInterface
name|m2
parameter_list|)
block|{
return|return
name|compare
argument_list|(
name|m1
operator|instanceof
name|MeetingConglictInterface
condition|?
operator|(
operator|(
name|MeetingConglictInterface
operator|)
name|m1
operator|)
operator|.
name|getName
argument_list|()
else|:
literal|null
argument_list|,
name|m2
operator|instanceof
name|MeetingConglictInterface
condition|?
operator|(
operator|(
name|MeetingConglictInterface
operator|)
name|m2
operator|)
operator|.
name|getName
argument_list|()
else|:
literal|null
argument_list|)
return|;
block|}
specifier|protected
specifier|static
name|int
name|compareByType
parameter_list|(
name|MeetingInterface
name|m1
parameter_list|,
name|MeetingInterface
name|m2
parameter_list|)
block|{
name|EventType
name|t1
init|=
operator|(
name|m1
operator|instanceof
name|MeetingConglictInterface
condition|?
operator|(
operator|(
name|MeetingConglictInterface
operator|)
name|m1
operator|)
operator|.
name|getType
argument_list|()
else|:
literal|null
operator|)
decl_stmt|;
name|EventType
name|t2
init|=
operator|(
name|m2
operator|instanceof
name|MeetingConglictInterface
condition|?
operator|(
operator|(
name|MeetingConglictInterface
operator|)
name|m2
operator|)
operator|.
name|getType
argument_list|()
else|:
literal|null
operator|)
decl_stmt|;
return|return
operator|(
name|t1
operator|==
literal|null
condition|?
name|t2
operator|==
literal|null
condition|?
literal|0
else|:
operator|-
literal|1
else|:
name|t2
operator|==
literal|null
condition|?
literal|1
else|:
name|t1
operator|.
name|compareTo
argument_list|(
name|t2
argument_list|)
operator|)
return|;
block|}
specifier|protected
specifier|static
name|int
name|compareByDate
parameter_list|(
name|MeetingInterface
name|m1
parameter_list|,
name|MeetingInterface
name|m2
parameter_list|)
block|{
if|if
condition|(
name|m1
operator|instanceof
name|MeetingConglictInterface
operator|&&
name|m2
operator|instanceof
name|MeetingConglictInterface
condition|)
block|{
name|int
name|cmp
init|=
operator|(
operator|(
name|MeetingConglictInterface
operator|)
name|m1
operator|)
operator|.
name|getName
argument_list|()
operator|.
name|compareTo
argument_list|(
operator|(
operator|(
name|MeetingConglictInterface
operator|)
name|m2
operator|)
operator|.
name|getName
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
return|return
operator|(
name|m1
operator|.
name|getMeetingDate
argument_list|()
operator|==
literal|null
condition|?
name|m2
operator|.
name|getMeetingDate
argument_list|()
operator|==
literal|null
condition|?
literal|0
else|:
literal|1
else|:
name|m2
operator|.
name|getMeetingDate
argument_list|()
operator|==
literal|null
condition|?
operator|-
literal|1
else|:
name|m1
operator|.
name|getMeetingDate
argument_list|()
operator|.
name|compareTo
argument_list|(
name|m2
operator|.
name|getMeetingDate
argument_list|()
argument_list|)
operator|)
return|;
block|}
specifier|protected
specifier|static
name|int
name|compareByAllocatedTime
parameter_list|(
name|MeetingInterface
name|m1
parameter_list|,
name|MeetingInterface
name|m2
parameter_list|)
block|{
name|int
name|cmp
init|=
operator|new
name|Integer
argument_list|(
name|m1
operator|.
name|getStartSlot
argument_list|()
argument_list|)
operator|.
name|compareTo
argument_list|(
name|m2
operator|.
name|getStartSlot
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
operator|new
name|Integer
argument_list|(
name|m1
operator|.
name|getEndSlot
argument_list|()
argument_list|)
operator|.
name|compareTo
argument_list|(
name|m2
operator|.
name|getEndSlot
argument_list|()
argument_list|)
return|;
block|}
specifier|protected
specifier|static
name|int
name|compareByPublishedTime
parameter_list|(
name|MeetingInterface
name|m1
parameter_list|,
name|MeetingInterface
name|m2
parameter_list|)
block|{
name|int
name|cmp
init|=
operator|new
name|Integer
argument_list|(
operator|(
literal|5
operator|*
name|m1
operator|.
name|getStartSlot
argument_list|()
operator|)
operator|+
name|m1
operator|.
name|getStartOffset
argument_list|()
argument_list|)
operator|.
name|compareTo
argument_list|(
operator|(
literal|5
operator|*
name|m2
operator|.
name|getStartSlot
argument_list|()
operator|)
operator|+
name|m2
operator|.
name|getStartOffset
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
operator|new
name|Integer
argument_list|(
operator|(
literal|5
operator|*
name|m1
operator|.
name|getEndSlot
argument_list|()
operator|)
operator|+
name|m2
operator|.
name|getEndOffset
argument_list|()
argument_list|)
operator|.
name|compareTo
argument_list|(
operator|(
literal|5
operator|*
name|m2
operator|.
name|getEndSlot
argument_list|()
operator|)
operator|+
name|m2
operator|.
name|getEndOffset
argument_list|()
argument_list|)
return|;
block|}
specifier|protected
specifier|static
name|int
name|compareBySetupTime
parameter_list|(
name|MeetingInterface
name|m1
parameter_list|,
name|MeetingInterface
name|m2
parameter_list|)
block|{
return|return
operator|new
name|Integer
argument_list|(
name|m1
operator|.
name|getStartOffset
argument_list|()
argument_list|)
operator|.
name|compareTo
argument_list|(
name|m2
operator|.
name|getStartOffset
argument_list|()
argument_list|)
return|;
block|}
specifier|protected
specifier|static
name|int
name|compareByTeardownTime
parameter_list|(
name|MeetingInterface
name|m1
parameter_list|,
name|MeetingInterface
name|m2
parameter_list|)
block|{
return|return
operator|new
name|Integer
argument_list|(
name|m2
operator|.
name|getEndOffset
argument_list|()
argument_list|)
operator|.
name|compareTo
argument_list|(
name|m1
operator|.
name|getEndOffset
argument_list|()
argument_list|)
return|;
block|}
specifier|protected
specifier|static
name|int
name|compareByLocation
parameter_list|(
name|MeetingInterface
name|m1
parameter_list|,
name|MeetingInterface
name|m2
parameter_list|)
block|{
return|return
name|m1
operator|.
name|getLocationName
argument_list|()
operator|.
name|compareTo
argument_list|(
name|m2
operator|.
name|getLocationName
argument_list|()
argument_list|)
return|;
block|}
specifier|protected
specifier|static
name|int
name|compareByCapacity
parameter_list|(
name|MeetingInterface
name|m1
parameter_list|,
name|MeetingInterface
name|m2
parameter_list|)
block|{
return|return
operator|(
name|m1
operator|.
name|getLocation
argument_list|()
operator|==
literal|null
condition|?
operator|new
name|Integer
argument_list|(
operator|-
literal|1
argument_list|)
else|:
name|m1
operator|.
name|getLocation
argument_list|()
operator|.
name|getSize
argument_list|()
operator|)
operator|.
name|compareTo
argument_list|(
name|m2
operator|.
name|getLocation
argument_list|()
operator|==
literal|null
condition|?
operator|new
name|Integer
argument_list|(
operator|-
literal|1
argument_list|)
else|:
name|m2
operator|.
name|getLocation
argument_list|()
operator|.
name|getSize
argument_list|()
argument_list|)
return|;
block|}
specifier|public
specifier|static
name|int
name|compareFallback
parameter_list|(
name|MeetingInterface
name|m1
parameter_list|,
name|MeetingInterface
name|m2
parameter_list|)
block|{
name|int
name|cmp
init|=
name|compareByDate
argument_list|(
name|m1
argument_list|,
name|m2
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
name|cmp
operator|=
name|compareByPublishedTime
argument_list|(
name|m1
argument_list|,
name|m2
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
name|cmp
operator|=
name|compareByLocation
argument_list|(
name|m1
argument_list|,
name|m2
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
name|m1
operator|.
name|compareTo
argument_list|(
name|m2
argument_list|)
return|;
block|}
specifier|public
specifier|static
name|int
name|compareMeetings
parameter_list|(
name|MeetingInterface
name|m1
parameter_list|,
name|MeetingInterface
name|m2
parameter_list|,
name|EventMeetingSortBy
name|sortBy
parameter_list|)
block|{
switch|switch
condition|(
name|sortBy
condition|)
block|{
case|case
name|NAME
case|:
return|return
name|compareByName
argument_list|(
name|m1
argument_list|,
name|m2
argument_list|)
return|;
case|case
name|TYPE
case|:
return|return
name|compareByType
argument_list|(
name|m1
argument_list|,
name|m2
argument_list|)
return|;
case|case
name|APPROVAL
case|:
return|return
name|compateByApproval
argument_list|(
name|m1
argument_list|,
name|m2
argument_list|)
return|;
case|case
name|DATE
case|:
return|return
name|compareByDate
argument_list|(
name|m1
argument_list|,
name|m2
argument_list|)
return|;
case|case
name|SETUP_TIME
case|:
return|return
name|compareBySetupTime
argument_list|(
name|m1
argument_list|,
name|m2
argument_list|)
return|;
case|case
name|TEARDOWN_TIME
case|:
return|return
name|compareByTeardownTime
argument_list|(
name|m1
argument_list|,
name|m2
argument_list|)
return|;
case|case
name|PUBLISHED_TIME
case|:
return|return
name|compareByPublishedTime
argument_list|(
name|m1
argument_list|,
name|m2
argument_list|)
return|;
case|case
name|ALLOCATED_TIME
case|:
return|return
name|compareByAllocatedTime
argument_list|(
name|m1
argument_list|,
name|m2
argument_list|)
return|;
case|case
name|LOCATION
case|:
return|return
name|compareByLocation
argument_list|(
name|m1
argument_list|,
name|m2
argument_list|)
return|;
case|case
name|CAPACITY
case|:
return|return
name|compareByCapacity
argument_list|(
name|m1
argument_list|,
name|m2
argument_list|)
return|;
block|}
return|return
literal|0
return|;
block|}
specifier|protected
specifier|static
name|int
name|compare
parameter_list|(
name|String
name|s1
parameter_list|,
name|String
name|s2
parameter_list|)
block|{
if|if
condition|(
name|s1
operator|==
literal|null
operator|||
name|s1
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
return|return
operator|(
name|s2
operator|==
literal|null
operator|||
name|s2
operator|.
name|isEmpty
argument_list|()
condition|?
literal|0
else|:
literal|1
operator|)
return|;
block|}
else|else
block|{
return|return
operator|(
name|s2
operator|==
literal|null
operator|||
name|s2
operator|.
name|isEmpty
argument_list|()
condition|?
operator|-
literal|1
else|:
name|s1
operator|.
name|compareToIgnoreCase
argument_list|(
name|s2
argument_list|)
operator|)
return|;
block|}
block|}
specifier|protected
specifier|static
name|int
name|compare
parameter_list|(
name|Number
name|n1
parameter_list|,
name|Number
name|n2
parameter_list|)
block|{
return|return
operator|(
name|n1
operator|==
literal|null
condition|?
name|n2
operator|==
literal|null
condition|?
literal|0
else|:
operator|-
literal|1
else|:
name|n2
operator|==
literal|null
condition|?
operator|-
literal|1
else|:
name|Double
operator|.
name|compare
argument_list|(
name|n1
operator|.
name|doubleValue
argument_list|()
argument_list|,
name|n2
operator|.
name|doubleValue
argument_list|()
argument_list|)
operator|)
return|;
block|}
block|}
end_class

end_unit

