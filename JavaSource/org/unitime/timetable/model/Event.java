begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*   * UniTime 3.1 (University Course Timetabling& Student Sectioning Application)  * Copyright (C) 2008, UniTime.org  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 2 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program; if not, write to the Free Software Foundation, Inc.,  * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.  */
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
name|text
operator|.
name|SimpleDateFormat
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Calendar
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Hashtable
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Iterator
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
name|Locale
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
name|java
operator|.
name|util
operator|.
name|TreeSet
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|cpsolver
operator|.
name|ifs
operator|.
name|util
operator|.
name|ToolBox
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
name|BaseEvent
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
name|EventDAO
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

begin_class
specifier|public
specifier|abstract
class|class
name|Event
extends|extends
name|BaseEvent
implements|implements
name|Comparable
argument_list|<
name|Event
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
comment|/*[CONSTRUCTOR MARKER BEGIN]*/
specifier|public
name|Event
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
block|}
comment|/** 	 * Constructor for primary key 	 */
specifier|public
name|Event
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
comment|/** 	 * Constructor for required fields 	 */
specifier|public
name|Event
parameter_list|(
name|java
operator|.
name|lang
operator|.
name|Long
name|uniqueId
parameter_list|,
name|java
operator|.
name|lang
operator|.
name|Integer
name|minCapacity
parameter_list|,
name|java
operator|.
name|lang
operator|.
name|Integer
name|maxCapacity
parameter_list|)
block|{
name|super
argument_list|(
name|uniqueId
argument_list|,
name|minCapacity
argument_list|,
name|maxCapacity
argument_list|)
expr_stmt|;
block|}
comment|/*[CONSTRUCTOR MARKER END]*/
specifier|public
specifier|static
specifier|final
name|int
name|sEventTypeClass
init|=
literal|0
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|int
name|sEventTypeFinalExam
init|=
literal|1
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|int
name|sEventTypeMidtermExam
init|=
literal|2
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|int
name|sEventTypeCourse
init|=
literal|3
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|int
name|sEventTypeSpecial
init|=
literal|4
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
index|[]
name|sEventTypes
init|=
operator|new
name|String
index|[]
block|{
literal|"Class Event"
block|,
literal|"Final Examination Event"
block|,
literal|"Midterm Examination Event"
block|,
literal|"Course Event"
block|,
literal|"Special Event"
block|}
decl_stmt|;
specifier|public
specifier|abstract
name|int
name|getEventType
parameter_list|()
function_decl|;
specifier|public
name|String
name|getEventTypeLabel
parameter_list|()
block|{
return|return
name|sEventTypes
index|[
name|getEventType
argument_list|()
index|]
return|;
block|}
specifier|public
specifier|abstract
name|Set
argument_list|<
name|Student
argument_list|>
name|getStudents
parameter_list|()
function_decl|;
specifier|public
specifier|abstract
name|Set
argument_list|<
name|DepartmentalInstructor
argument_list|>
name|getInstructors
parameter_list|()
function_decl|;
specifier|public
specifier|static
name|void
name|deleteFromEvents
parameter_list|(
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
parameter_list|,
name|Integer
name|ownerType
parameter_list|,
name|Long
name|ownerId
parameter_list|)
block|{
for|for
control|(
name|Iterator
name|i
init|=
name|hibSession
operator|.
name|createQuery
argument_list|(
literal|"select r from CourseEvent e inner join e.relatedCourses r where "
operator|+
literal|"r.ownerType=:ownerType and r.ownerId=:ownerId"
argument_list|)
operator|.
name|setInteger
argument_list|(
literal|"ownerType"
argument_list|,
name|ownerType
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"ownerId"
argument_list|,
name|ownerId
argument_list|)
operator|.
name|iterate
argument_list|()
init|;
name|i
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|RelatedCourseInfo
name|relatedCourse
init|=
operator|(
name|RelatedCourseInfo
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|CourseEvent
name|event
init|=
name|relatedCourse
operator|.
name|getEvent
argument_list|()
decl_stmt|;
name|event
operator|.
name|getRelatedCourses
argument_list|()
operator|.
name|remove
argument_list|(
name|relatedCourse
argument_list|)
expr_stmt|;
name|relatedCourse
operator|.
name|setOwnerId
argument_list|(
literal|null
argument_list|)
expr_stmt|;
name|relatedCourse
operator|.
name|setCourse
argument_list|(
literal|null
argument_list|)
expr_stmt|;
name|hibSession
operator|.
name|delete
argument_list|(
name|relatedCourse
argument_list|)
expr_stmt|;
if|if
condition|(
name|event
operator|.
name|getRelatedCourses
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|hibSession
operator|.
name|delete
argument_list|(
name|event
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|hibSession
operator|.
name|saveOrUpdate
argument_list|(
name|event
argument_list|)
expr_stmt|;
block|}
block|}
block|}
specifier|public
specifier|static
name|void
name|deleteFromEvents
parameter_list|(
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
parameter_list|,
name|Class_
name|clazz
parameter_list|)
block|{
name|deleteFromEvents
argument_list|(
name|hibSession
argument_list|,
name|ExamOwner
operator|.
name|sOwnerTypeClass
argument_list|,
name|clazz
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
block|}
specifier|public
specifier|static
name|void
name|deleteFromEvents
parameter_list|(
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
parameter_list|,
name|InstrOfferingConfig
name|config
parameter_list|)
block|{
name|deleteFromEvents
argument_list|(
name|hibSession
argument_list|,
name|ExamOwner
operator|.
name|sOwnerTypeConfig
argument_list|,
name|config
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
block|}
specifier|public
specifier|static
name|void
name|deleteFromEvents
parameter_list|(
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
parameter_list|,
name|InstructionalOffering
name|offering
parameter_list|)
block|{
name|deleteFromEvents
argument_list|(
name|hibSession
argument_list|,
name|ExamOwner
operator|.
name|sOwnerTypeOffering
argument_list|,
name|offering
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
block|}
specifier|public
specifier|static
name|void
name|deleteFromEvents
parameter_list|(
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
parameter_list|,
name|CourseOffering
name|course
parameter_list|)
block|{
name|deleteFromEvents
argument_list|(
name|hibSession
argument_list|,
name|ExamOwner
operator|.
name|sOwnerTypeCourse
argument_list|,
name|course
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
block|}
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
operator|(
name|this
operator|.
name|getEventName
argument_list|()
operator|)
return|;
block|}
specifier|public
name|int
name|compareTo
parameter_list|(
name|Event
name|e
parameter_list|)
block|{
if|if
condition|(
name|getEventName
argument_list|()
operator|!=
name|e
operator|.
name|getEventName
argument_list|()
condition|)
block|{
return|return
name|getEventName
argument_list|()
operator|.
name|compareTo
argument_list|(
name|e
operator|.
name|getEventName
argument_list|()
argument_list|)
return|;
block|}
else|else
return|return
name|getUniqueId
argument_list|()
operator|.
name|compareTo
argument_list|(
name|e
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
parameter_list|()
block|{
return|return
operator|new
name|EventDAO
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select e from Event e"
argument_list|)
operator|.
name|setCacheable
argument_list|(
literal|true
argument_list|)
operator|.
name|list
argument_list|()
return|;
block|}
specifier|public
name|TreeSet
argument_list|<
name|MultiMeeting
argument_list|>
name|getMultiMeetings
parameter_list|()
block|{
name|TreeSet
argument_list|<
name|MultiMeeting
argument_list|>
name|ret
init|=
operator|new
name|TreeSet
argument_list|<
name|MultiMeeting
argument_list|>
argument_list|()
decl_stmt|;
name|TreeSet
argument_list|<
name|Meeting
argument_list|>
name|meetings
init|=
operator|new
name|TreeSet
argument_list|<
name|Meeting
argument_list|>
argument_list|()
decl_stmt|;
name|meetings
operator|.
name|addAll
argument_list|(
name|getMeetings
argument_list|()
argument_list|)
expr_stmt|;
while|while
condition|(
operator|!
name|meetings
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|Meeting
name|meeting
init|=
name|meetings
operator|.
name|first
argument_list|()
decl_stmt|;
name|meetings
operator|.
name|remove
argument_list|(
name|meeting
argument_list|)
expr_stmt|;
name|Hashtable
argument_list|<
name|Long
argument_list|,
name|Meeting
argument_list|>
name|similar
init|=
operator|new
name|Hashtable
argument_list|()
decl_stmt|;
name|TreeSet
argument_list|<
name|Integer
argument_list|>
name|dow
init|=
operator|new
name|TreeSet
argument_list|<
name|Integer
argument_list|>
argument_list|()
decl_stmt|;
name|dow
operator|.
name|add
argument_list|(
name|meeting
operator|.
name|getDayOfWeek
argument_list|()
argument_list|)
expr_stmt|;
for|for
control|(
name|Meeting
name|m
range|:
name|meetings
control|)
block|{
if|if
condition|(
name|ToolBox
operator|.
name|equals
argument_list|(
name|m
operator|.
name|getStartPeriod
argument_list|()
argument_list|,
name|meeting
operator|.
name|getStartPeriod
argument_list|()
argument_list|)
operator|&&
name|ToolBox
operator|.
name|equals
argument_list|(
name|m
operator|.
name|getStartOffset
argument_list|()
argument_list|,
name|meeting
operator|.
name|getStartOffset
argument_list|()
argument_list|)
operator|&&
name|ToolBox
operator|.
name|equals
argument_list|(
name|m
operator|.
name|getStopPeriod
argument_list|()
argument_list|,
name|meeting
operator|.
name|getStopPeriod
argument_list|()
argument_list|)
operator|&&
name|ToolBox
operator|.
name|equals
argument_list|(
name|m
operator|.
name|getStopOffset
argument_list|()
argument_list|,
name|meeting
operator|.
name|getStopOffset
argument_list|()
argument_list|)
operator|&&
name|ToolBox
operator|.
name|equals
argument_list|(
name|m
operator|.
name|getLocationPermanentId
argument_list|()
argument_list|,
name|meeting
operator|.
name|getLocationPermanentId
argument_list|()
argument_list|)
operator|&&
name|m
operator|.
name|isApproved
argument_list|()
operator|==
name|meeting
operator|.
name|isApproved
argument_list|()
condition|)
block|{
name|dow
operator|.
name|add
argument_list|(
name|m
operator|.
name|getDayOfWeek
argument_list|()
argument_list|)
expr_stmt|;
name|similar
operator|.
name|put
argument_list|(
name|m
operator|.
name|getMeetingDate
argument_list|()
operator|.
name|getTime
argument_list|()
argument_list|,
name|m
argument_list|)
expr_stmt|;
block|}
block|}
name|TreeSet
argument_list|<
name|Meeting
argument_list|>
name|multi
init|=
operator|new
name|TreeSet
argument_list|<
name|Meeting
argument_list|>
argument_list|()
decl_stmt|;
name|multi
operator|.
name|add
argument_list|(
name|meeting
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
name|similar
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|Calendar
name|c
init|=
name|Calendar
operator|.
name|getInstance
argument_list|(
name|Locale
operator|.
name|US
argument_list|)
decl_stmt|;
name|c
operator|.
name|setTimeInMillis
argument_list|(
name|meeting
operator|.
name|getMeetingDate
argument_list|()
operator|.
name|getTime
argument_list|()
argument_list|)
expr_stmt|;
while|while
condition|(
literal|true
condition|)
block|{
do|do
block|{
name|c
operator|.
name|add
argument_list|(
name|Calendar
operator|.
name|DAY_OF_YEAR
argument_list|,
literal|1
argument_list|)
expr_stmt|;
block|}
do|while
condition|(
operator|!
name|dow
operator|.
name|contains
argument_list|(
name|c
operator|.
name|get
argument_list|(
name|Calendar
operator|.
name|DAY_OF_WEEK
argument_list|)
argument_list|)
condition|)
do|;
name|Meeting
name|m
init|=
name|similar
operator|.
name|get
argument_list|(
name|c
operator|.
name|getTimeInMillis
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|m
operator|==
literal|null
condition|)
break|break;
name|multi
operator|.
name|add
argument_list|(
name|m
argument_list|)
expr_stmt|;
name|meetings
operator|.
name|remove
argument_list|(
name|m
argument_list|)
expr_stmt|;
block|}
block|}
name|ret
operator|.
name|add
argument_list|(
operator|new
name|MultiMeeting
argument_list|(
name|multi
argument_list|)
argument_list|)
expr_stmt|;
block|}
return|return
name|ret
return|;
block|}
specifier|public
specifier|static
class|class
name|MultiMeeting
implements|implements
name|Comparable
argument_list|<
name|MultiMeeting
argument_list|>
block|{
specifier|private
name|TreeSet
argument_list|<
name|Meeting
argument_list|>
name|iMeetings
decl_stmt|;
specifier|public
name|MultiMeeting
parameter_list|(
name|TreeSet
argument_list|<
name|Meeting
argument_list|>
name|meetings
parameter_list|)
block|{
name|iMeetings
operator|=
name|meetings
expr_stmt|;
block|}
specifier|public
name|TreeSet
argument_list|<
name|Meeting
argument_list|>
name|getMeetings
parameter_list|()
block|{
return|return
name|iMeetings
return|;
block|}
specifier|public
name|int
name|compareTo
parameter_list|(
name|MultiMeeting
name|m
parameter_list|)
block|{
return|return
name|getMeetings
argument_list|()
operator|.
name|first
argument_list|()
operator|.
name|compareTo
argument_list|(
name|m
operator|.
name|getMeetings
argument_list|()
operator|.
name|first
argument_list|()
argument_list|)
return|;
block|}
specifier|public
name|String
name|getDays
parameter_list|()
block|{
return|return
name|getDays
argument_list|(
name|Constants
operator|.
name|DAY_NAME
argument_list|,
name|Constants
operator|.
name|DAY_NAMES_SHORT
argument_list|)
return|;
block|}
specifier|public
name|String
name|getDays
parameter_list|(
name|String
index|[]
name|dayNames
parameter_list|,
name|String
index|[]
name|shortDyNames
parameter_list|)
block|{
name|int
name|nrDays
init|=
literal|0
decl_stmt|;
name|int
name|dayCode
init|=
literal|0
decl_stmt|;
for|for
control|(
name|Meeting
name|meeting
range|:
name|getMeetings
argument_list|()
control|)
block|{
name|int
name|dc
init|=
literal|0
decl_stmt|;
switch|switch
condition|(
name|meeting
operator|.
name|getDayOfWeek
argument_list|()
condition|)
block|{
case|case
name|Calendar
operator|.
name|MONDAY
case|:
name|dc
operator|=
name|Constants
operator|.
name|DAY_CODES
index|[
name|Constants
operator|.
name|DAY_MON
index|]
expr_stmt|;
break|break;
case|case
name|Calendar
operator|.
name|TUESDAY
case|:
name|dc
operator|=
name|Constants
operator|.
name|DAY_CODES
index|[
name|Constants
operator|.
name|DAY_TUE
index|]
expr_stmt|;
break|break;
case|case
name|Calendar
operator|.
name|WEDNESDAY
case|:
name|dc
operator|=
name|Constants
operator|.
name|DAY_CODES
index|[
name|Constants
operator|.
name|DAY_WED
index|]
expr_stmt|;
break|break;
case|case
name|Calendar
operator|.
name|THURSDAY
case|:
name|dc
operator|=
name|Constants
operator|.
name|DAY_CODES
index|[
name|Constants
operator|.
name|DAY_THU
index|]
expr_stmt|;
break|break;
case|case
name|Calendar
operator|.
name|FRIDAY
case|:
name|dc
operator|=
name|Constants
operator|.
name|DAY_CODES
index|[
name|Constants
operator|.
name|DAY_FRI
index|]
expr_stmt|;
break|break;
case|case
name|Calendar
operator|.
name|SATURDAY
case|:
name|dc
operator|=
name|Constants
operator|.
name|DAY_CODES
index|[
name|Constants
operator|.
name|DAY_SAT
index|]
expr_stmt|;
break|break;
case|case
name|Calendar
operator|.
name|SUNDAY
case|:
name|dc
operator|=
name|Constants
operator|.
name|DAY_CODES
index|[
name|Constants
operator|.
name|DAY_SUN
index|]
expr_stmt|;
break|break;
block|}
if|if
condition|(
operator|(
name|dayCode
operator|&
name|dc
operator|)
operator|==
literal|0
condition|)
name|nrDays
operator|++
expr_stmt|;
name|dayCode
operator||=
name|dc
expr_stmt|;
block|}
name|String
name|ret
init|=
literal|""
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|Constants
operator|.
name|DAY_CODES
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
if|if
condition|(
operator|(
name|dayCode
operator|&
name|Constants
operator|.
name|DAY_CODES
index|[
name|i
index|]
operator|)
operator|!=
literal|0
condition|)
name|ret
operator|+=
operator|(
name|nrDays
operator|==
literal|1
condition|?
name|dayNames
else|:
name|shortDyNames
operator|)
index|[
name|i
index|]
expr_stmt|;
block|}
return|return
name|ret
return|;
block|}
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
name|getDays
argument_list|()
operator|+
literal|" "
operator|+
operator|new
name|SimpleDateFormat
argument_list|(
literal|"MM/dd"
argument_list|)
operator|.
name|format
argument_list|(
name|getMeetings
argument_list|()
operator|.
name|first
argument_list|()
operator|.
name|getMeetingDate
argument_list|()
argument_list|)
operator|+
operator|(
name|getMeetings
argument_list|()
operator|.
name|size
argument_list|()
operator|>
literal|1
condition|?
literal|" - "
operator|+
operator|new
name|SimpleDateFormat
argument_list|(
literal|"MM/dd"
argument_list|)
operator|.
name|format
argument_list|(
name|getMeetings
argument_list|()
operator|.
name|last
argument_list|()
operator|.
name|getMeetingDate
argument_list|()
argument_list|)
else|:
literal|""
operator|)
operator|+
literal|" "
operator|+
name|getMeetings
argument_list|()
operator|.
name|first
argument_list|()
operator|.
name|startTime
argument_list|()
operator|+
literal|" - "
operator|+
name|getMeetings
argument_list|()
operator|.
name|first
argument_list|()
operator|.
name|stopTime
argument_list|()
operator|+
operator|(
name|getMeetings
argument_list|()
operator|.
name|first
argument_list|()
operator|.
name|getLocation
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
literal|" "
operator|+
name|getMeetings
argument_list|()
operator|.
name|first
argument_list|()
operator|.
name|getLocation
argument_list|()
operator|.
name|getLabel
argument_list|()
operator|)
return|;
block|}
block|}
block|}
end_class

end_unit

