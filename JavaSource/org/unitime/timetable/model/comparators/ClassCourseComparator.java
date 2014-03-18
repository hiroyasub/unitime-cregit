begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.3 - 3.5 (University Timetabling Application)  * Copyright (C) 2011 - 2013, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
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
operator|.
name|comparators
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
name|org
operator|.
name|cpsolver
operator|.
name|coursett
operator|.
name|model
operator|.
name|TimeLocation
import|;
end_import

begin_import
import|import
name|org
operator|.
name|unitime
operator|.
name|localization
operator|.
name|impl
operator|.
name|Localization
import|;
end_import

begin_import
import|import
name|org
operator|.
name|unitime
operator|.
name|localization
operator|.
name|messages
operator|.
name|CourseMessages
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
name|Assignment
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
name|ClassInstructor
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
name|Class_
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
name|CourseOffering
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
name|DatePattern
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
name|DepartmentalInstructor
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
name|SchedulingSubpart
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
name|TimePattern
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
name|solver
operator|.
name|ClassAssignmentProxy
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|ClassCourseComparator
implements|implements
name|Comparator
block|{
specifier|static
name|CourseMessages
name|MSG
init|=
name|Localization
operator|.
name|create
argument_list|(
name|CourseMessages
operator|.
name|class
argument_list|)
decl_stmt|;
name|SortBy
name|iSortyBy
decl_stmt|;
name|ClassAssignmentProxy
name|iClassAssignmentProxy
decl_stmt|;
name|boolean
name|iKeepSubpart
decl_stmt|;
specifier|public
specifier|static
enum|enum
name|SortBy
block|{
name|NAME
block|,
name|DIV_SEC
block|,
name|ENROLLMENT
block|,
name|LIMIT
block|,
name|ROOM_SIZE
block|,
name|DATE_PATTERN
block|,
name|TIME_PATTERN
block|,
name|INSTRUCTOR
block|,
name|ASSIGNED_TIME
block|,
name|ASSIGNED_ROOM
block|,
name|ASSIGNED_ROOM_CAP
block|}
empty_stmt|;
specifier|public
specifier|static
name|String
name|getName
parameter_list|(
name|SortBy
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
name|MSG
operator|.
name|sortByName
argument_list|()
return|;
case|case
name|DIV_SEC
case|:
return|return
name|MSG
operator|.
name|sortByDivSec
argument_list|()
return|;
case|case
name|ENROLLMENT
case|:
return|return
name|MSG
operator|.
name|sortByEnrollment
argument_list|()
return|;
case|case
name|LIMIT
case|:
return|return
name|MSG
operator|.
name|sortByLimit
argument_list|()
return|;
case|case
name|ROOM_SIZE
case|:
return|return
name|MSG
operator|.
name|sortByRoomSize
argument_list|()
return|;
case|case
name|DATE_PATTERN
case|:
return|return
name|MSG
operator|.
name|sortByDatePattern
argument_list|()
return|;
case|case
name|TIME_PATTERN
case|:
return|return
name|MSG
operator|.
name|sortByTimePattern
argument_list|()
return|;
case|case
name|INSTRUCTOR
case|:
return|return
name|MSG
operator|.
name|sortByInstructor
argument_list|()
return|;
case|case
name|ASSIGNED_TIME
case|:
return|return
name|MSG
operator|.
name|sortByAssignedTime
argument_list|()
return|;
case|case
name|ASSIGNED_ROOM
case|:
return|return
name|MSG
operator|.
name|sortByAssignedRoom
argument_list|()
return|;
case|case
name|ASSIGNED_ROOM_CAP
case|:
return|return
name|MSG
operator|.
name|sortByAssignedRoomCapacity
argument_list|()
return|;
default|default:
return|return
name|MSG
operator|.
name|sortByName
argument_list|()
return|;
block|}
block|}
specifier|public
specifier|static
name|SortBy
name|getSortBy
parameter_list|(
name|String
name|name
parameter_list|)
block|{
for|for
control|(
name|SortBy
name|s
range|:
name|SortBy
operator|.
name|values
argument_list|()
control|)
if|if
condition|(
name|getName
argument_list|(
name|s
argument_list|)
operator|.
name|equals
argument_list|(
name|name
argument_list|)
condition|)
return|return
name|s
return|;
return|return
name|SortBy
operator|.
name|NAME
return|;
block|}
specifier|public
specifier|static
name|String
index|[]
name|getNames
parameter_list|()
block|{
name|String
index|[]
name|names
init|=
operator|new
name|String
index|[
name|SortBy
operator|.
name|values
argument_list|()
operator|.
name|length
index|]
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
name|SortBy
operator|.
name|values
argument_list|()
operator|.
name|length
condition|;
name|i
operator|++
control|)
name|names
index|[
name|i
index|]
operator|=
name|getName
argument_list|(
name|SortBy
operator|.
name|values
argument_list|()
index|[
name|i
index|]
argument_list|)
expr_stmt|;
return|return
name|names
return|;
block|}
specifier|public
name|ClassCourseComparator
parameter_list|(
name|String
name|sortBy
parameter_list|,
name|ClassAssignmentProxy
name|classAssignmentProxy
parameter_list|,
name|boolean
name|keepSubparts
parameter_list|)
block|{
name|iSortyBy
operator|=
name|getSortBy
argument_list|(
name|sortBy
argument_list|)
expr_stmt|;
name|iClassAssignmentProxy
operator|=
name|classAssignmentProxy
expr_stmt|;
name|iKeepSubpart
operator|=
name|keepSubparts
expr_stmt|;
block|}
specifier|public
name|boolean
name|isParent
parameter_list|(
name|SchedulingSubpart
name|s1
parameter_list|,
name|SchedulingSubpart
name|s2
parameter_list|)
block|{
name|SchedulingSubpart
name|p1
init|=
name|s1
operator|.
name|getParentSubpart
argument_list|()
decl_stmt|;
if|if
condition|(
name|p1
operator|==
literal|null
condition|)
return|return
literal|false
return|;
if|if
condition|(
name|p1
operator|.
name|equals
argument_list|(
name|s2
argument_list|)
condition|)
return|return
literal|true
return|;
return|return
name|isParent
argument_list|(
name|p1
argument_list|,
name|s2
argument_list|)
return|;
block|}
specifier|public
name|int
name|compareSubparts
parameter_list|(
name|SchedulingSubpart
name|s1
parameter_list|,
name|SchedulingSubpart
name|s2
parameter_list|)
block|{
if|if
condition|(
name|isParent
argument_list|(
name|s1
argument_list|,
name|s2
argument_list|)
condition|)
return|return
literal|1
return|;
if|if
condition|(
name|isParent
argument_list|(
name|s2
argument_list|,
name|s1
argument_list|)
condition|)
return|return
operator|-
literal|1
return|;
if|if
condition|(
name|s1
operator|.
name|getParentSubpart
argument_list|()
operator|!=
literal|null
operator|||
name|s2
operator|.
name|getParentSubpart
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|SchedulingSubpart
name|p1
init|=
name|s1
decl_stmt|;
name|int
name|d1
init|=
literal|0
decl_stmt|;
while|while
condition|(
name|p1
operator|.
name|getParentSubpart
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|p1
operator|=
name|p1
operator|.
name|getParentSubpart
argument_list|()
expr_stmt|;
name|d1
operator|++
expr_stmt|;
block|}
name|SchedulingSubpart
name|p2
init|=
name|s2
decl_stmt|;
name|int
name|d2
init|=
literal|0
decl_stmt|;
while|while
condition|(
name|p2
operator|.
name|getParentSubpart
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|p2
operator|=
name|p2
operator|.
name|getParentSubpart
argument_list|()
expr_stmt|;
name|d2
operator|++
expr_stmt|;
block|}
if|if
condition|(
name|d1
operator|<
name|d2
condition|)
block|{
name|int
name|cmp
init|=
name|compareSubparts
argument_list|(
name|s1
argument_list|,
name|s2
operator|.
name|getParentSubpart
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
if|else if
condition|(
name|d1
operator|>
name|d2
condition|)
block|{
name|int
name|cmp
init|=
name|compareSubparts
argument_list|(
name|s1
operator|.
name|getParentSubpart
argument_list|()
argument_list|,
name|s2
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
name|int
name|cmp
init|=
name|compareSubparts
argument_list|(
name|s1
operator|.
name|getParentSubpart
argument_list|()
argument_list|,
name|s2
operator|.
name|getParentSubpart
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
block|}
name|int
name|cmp
init|=
name|s1
operator|.
name|getItype
argument_list|()
operator|.
name|getItype
argument_list|()
operator|.
name|compareTo
argument_list|(
name|s2
operator|.
name|getItype
argument_list|()
operator|.
name|getItype
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
name|s1
operator|.
name|getUniqueId
argument_list|()
operator|.
name|compareTo
argument_list|(
name|s2
operator|.
name|getUniqueId
argument_list|()
argument_list|)
return|;
block|}
specifier|public
name|boolean
name|isParentSameIType
parameter_list|(
name|SchedulingSubpart
name|s1
parameter_list|,
name|SchedulingSubpart
name|s2
parameter_list|)
block|{
name|SchedulingSubpart
name|p1
init|=
name|s1
operator|.
name|getParentSubpart
argument_list|()
decl_stmt|;
if|if
condition|(
name|p1
operator|==
literal|null
condition|)
return|return
literal|false
return|;
if|if
condition|(
name|p1
operator|.
name|equals
argument_list|(
name|s2
argument_list|)
condition|)
return|return
literal|true
return|;
if|if
condition|(
operator|!
name|p1
operator|.
name|getItype
argument_list|()
operator|.
name|equals
argument_list|(
name|s2
operator|.
name|getItype
argument_list|()
argument_list|)
condition|)
return|return
literal|false
return|;
return|return
name|isParentSameIType
argument_list|(
name|p1
argument_list|,
name|s2
argument_list|)
return|;
block|}
specifier|public
name|int
name|compareClasses
parameter_list|(
name|CourseOffering
name|co1
parameter_list|,
name|CourseOffering
name|co2
parameter_list|,
name|Class_
name|c1
parameter_list|,
name|Class_
name|c2
parameter_list|)
block|{
name|int
name|cmp
init|=
literal|0
decl_stmt|;
name|Assignment
name|a1
decl_stmt|,
name|a2
decl_stmt|;
try|try
block|{
switch|switch
condition|(
name|iSortyBy
condition|)
block|{
case|case
name|NAME
case|:
if|if
condition|(
operator|!
name|co1
operator|.
name|equals
argument_list|(
name|co2
argument_list|)
condition|)
block|{
name|cmp
operator|=
name|compareComparable
argument_list|(
name|co1
argument_list|,
name|co2
argument_list|)
expr_stmt|;
block|}
if|else  if
condition|(
operator|!
name|c1
operator|.
name|getSchedulingSubpart
argument_list|()
operator|.
name|equals
argument_list|(
name|c2
operator|.
name|getSchedulingSubpart
argument_list|()
argument_list|)
condition|)
name|cmp
operator|=
name|compareSubparts
argument_list|(
name|c1
operator|.
name|getSchedulingSubpart
argument_list|()
argument_list|,
name|c2
operator|.
name|getSchedulingSubpart
argument_list|()
argument_list|)
expr_stmt|;
else|else
name|cmp
operator|=
name|c1
operator|.
name|getSectionNumber
argument_list|()
operator|.
name|compareTo
argument_list|(
name|c2
operator|.
name|getSectionNumber
argument_list|()
argument_list|)
expr_stmt|;
break|break;
case|case
name|DIV_SEC
case|:
name|String
name|sx1
init|=
name|c1
operator|.
name|getClassSuffix
argument_list|(
name|co1
argument_list|)
decl_stmt|;
name|String
name|sx2
init|=
name|c2
operator|.
name|getClassSuffix
argument_list|(
name|co2
argument_list|)
decl_stmt|;
if|if
condition|(
name|sx1
operator|==
literal|null
condition|)
block|{
if|if
condition|(
name|sx2
operator|==
literal|null
condition|)
block|{
name|cmp
operator|=
name|c1
operator|.
name|getSectionNumber
argument_list|()
operator|.
name|compareTo
argument_list|(
name|c2
operator|.
name|getSectionNumber
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
return|return
operator|-
literal|1
return|;
block|}
block|}
else|else
block|{
if|if
condition|(
name|sx2
operator|==
literal|null
condition|)
block|{
return|return
literal|1
return|;
block|}
else|else
block|{
name|cmp
operator|=
name|sx1
operator|.
name|compareTo
argument_list|(
name|sx2
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
name|c1
operator|.
name|getSectionNumber
argument_list|()
operator|.
name|compareTo
argument_list|(
name|c2
operator|.
name|getSectionNumber
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
break|break;
case|case
name|TIME_PATTERN
case|:
name|Set
name|t1s
init|=
name|c1
operator|.
name|effectiveTimePatterns
argument_list|()
decl_stmt|;
name|Set
name|t2s
init|=
name|c2
operator|.
name|effectiveTimePatterns
argument_list|()
decl_stmt|;
name|TimePattern
name|p1
init|=
operator|(
name|t1s
operator|==
literal|null
operator|||
name|t1s
operator|.
name|isEmpty
argument_list|()
condition|?
literal|null
else|:
operator|(
name|TimePattern
operator|)
name|t1s
operator|.
name|iterator
argument_list|()
operator|.
name|next
argument_list|()
operator|)
decl_stmt|;
name|TimePattern
name|p2
init|=
operator|(
name|t2s
operator|==
literal|null
operator|||
name|t2s
operator|.
name|isEmpty
argument_list|()
condition|?
literal|null
else|:
operator|(
name|TimePattern
operator|)
name|t2s
operator|.
name|iterator
argument_list|()
operator|.
name|next
argument_list|()
operator|)
decl_stmt|;
name|cmp
operator|=
name|compareComparable
argument_list|(
name|p1
argument_list|,
name|p2
argument_list|)
expr_stmt|;
break|break;
case|case
name|LIMIT
case|:
name|cmp
operator|=
name|compareComparable
argument_list|(
name|c1
operator|.
name|getExpectedCapacity
argument_list|()
argument_list|,
name|c2
operator|.
name|getExpectedCapacity
argument_list|()
argument_list|)
expr_stmt|;
break|break;
case|case
name|ROOM_SIZE
case|:
name|cmp
operator|=
name|compareComparable
argument_list|(
name|c1
operator|.
name|getMinRoomLimit
argument_list|()
argument_list|,
name|c2
operator|.
name|getMinRoomLimit
argument_list|()
argument_list|)
expr_stmt|;
break|break;
case|case
name|DATE_PATTERN
case|:
name|a1
operator|=
operator|(
name|iClassAssignmentProxy
operator|==
literal|null
condition|?
literal|null
else|:
name|iClassAssignmentProxy
operator|.
name|getAssignment
argument_list|(
name|c1
argument_list|)
operator|)
expr_stmt|;
name|a2
operator|=
operator|(
name|iClassAssignmentProxy
operator|==
literal|null
condition|?
literal|null
else|:
name|iClassAssignmentProxy
operator|.
name|getAssignment
argument_list|(
name|c2
argument_list|)
operator|)
expr_stmt|;
name|DatePattern
name|d1
init|=
operator|(
name|a1
operator|==
literal|null
condition|?
name|c1
operator|.
name|effectiveDatePattern
argument_list|()
else|:
name|a1
operator|.
name|getDatePattern
argument_list|()
operator|)
decl_stmt|;
name|DatePattern
name|d2
init|=
operator|(
name|a2
operator|==
literal|null
condition|?
name|c2
operator|.
name|effectiveDatePattern
argument_list|()
else|:
name|a2
operator|.
name|getDatePattern
argument_list|()
operator|)
decl_stmt|;
name|cmp
operator|=
name|compareComparable
argument_list|(
name|d1
argument_list|,
name|d2
argument_list|)
expr_stmt|;
break|break;
case|case
name|INSTRUCTOR
case|:
name|cmp
operator|=
name|compareInstructors
argument_list|(
name|c1
argument_list|,
name|c2
argument_list|)
expr_stmt|;
break|break;
case|case
name|ASSIGNED_TIME
case|:
name|a1
operator|=
operator|(
name|iClassAssignmentProxy
operator|==
literal|null
condition|?
literal|null
else|:
name|iClassAssignmentProxy
operator|.
name|getAssignment
argument_list|(
name|c1
argument_list|)
operator|)
expr_stmt|;
name|a2
operator|=
operator|(
name|iClassAssignmentProxy
operator|==
literal|null
condition|?
literal|null
else|:
name|iClassAssignmentProxy
operator|.
name|getAssignment
argument_list|(
name|c2
argument_list|)
operator|)
expr_stmt|;
if|if
condition|(
name|a1
operator|==
literal|null
condition|)
block|{
name|cmp
operator|=
operator|(
name|a2
operator|==
literal|null
condition|?
literal|0
else|:
operator|-
literal|1
operator|)
expr_stmt|;
block|}
else|else
block|{
if|if
condition|(
name|a2
operator|==
literal|null
condition|)
name|cmp
operator|=
literal|1
expr_stmt|;
else|else
block|{
name|TimeLocation
name|t1
init|=
name|a1
operator|.
name|getPlacement
argument_list|()
operator|.
name|getTimeLocation
argument_list|()
decl_stmt|;
name|TimeLocation
name|t2
init|=
name|a2
operator|.
name|getPlacement
argument_list|()
operator|.
name|getTimeLocation
argument_list|()
decl_stmt|;
name|cmp
operator|=
name|t1
operator|.
name|getStartSlots
argument_list|()
operator|.
name|nextElement
argument_list|()
operator|.
name|compareTo
argument_list|(
name|t2
operator|.
name|getStartSlots
argument_list|()
operator|.
name|nextElement
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|cmp
operator|==
literal|0
condition|)
name|cmp
operator|=
name|Double
operator|.
name|compare
argument_list|(
name|t1
operator|.
name|getDayCode
argument_list|()
argument_list|,
name|t2
operator|.
name|getDayCode
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|cmp
operator|==
literal|0
condition|)
name|cmp
operator|=
name|Double
operator|.
name|compare
argument_list|(
name|t1
operator|.
name|getLength
argument_list|()
argument_list|,
name|t2
operator|.
name|getLength
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
break|break;
case|case
name|ASSIGNED_ROOM
case|:
name|a1
operator|=
operator|(
name|iClassAssignmentProxy
operator|==
literal|null
condition|?
literal|null
else|:
name|iClassAssignmentProxy
operator|.
name|getAssignment
argument_list|(
name|c1
argument_list|)
operator|)
expr_stmt|;
name|a2
operator|=
operator|(
name|iClassAssignmentProxy
operator|==
literal|null
condition|?
literal|null
else|:
name|iClassAssignmentProxy
operator|.
name|getAssignment
argument_list|(
name|c2
argument_list|)
operator|)
expr_stmt|;
if|if
condition|(
name|a1
operator|==
literal|null
condition|)
block|{
name|cmp
operator|=
operator|(
name|a2
operator|==
literal|null
condition|?
literal|0
else|:
operator|-
literal|1
operator|)
expr_stmt|;
block|}
else|else
block|{
if|if
condition|(
name|a2
operator|==
literal|null
condition|)
name|cmp
operator|=
literal|1
expr_stmt|;
else|else
name|cmp
operator|=
name|a1
operator|.
name|getPlacement
argument_list|()
operator|.
name|getRoomName
argument_list|(
literal|","
argument_list|)
operator|.
name|compareTo
argument_list|(
name|a2
operator|.
name|getPlacement
argument_list|()
operator|.
name|getRoomName
argument_list|(
literal|","
argument_list|)
argument_list|)
expr_stmt|;
block|}
break|break;
case|case
name|ASSIGNED_ROOM_CAP
case|:
name|a1
operator|=
operator|(
name|iClassAssignmentProxy
operator|==
literal|null
condition|?
literal|null
else|:
name|iClassAssignmentProxy
operator|.
name|getAssignment
argument_list|(
name|c1
argument_list|)
operator|)
expr_stmt|;
name|a2
operator|=
operator|(
name|iClassAssignmentProxy
operator|==
literal|null
condition|?
literal|null
else|:
name|iClassAssignmentProxy
operator|.
name|getAssignment
argument_list|(
name|c2
argument_list|)
operator|)
expr_stmt|;
if|if
condition|(
name|a1
operator|==
literal|null
condition|)
block|{
name|cmp
operator|=
operator|(
name|a2
operator|==
literal|null
condition|?
literal|0
else|:
operator|-
literal|1
operator|)
expr_stmt|;
block|}
else|else
block|{
if|if
condition|(
name|a2
operator|==
literal|null
condition|)
name|cmp
operator|=
literal|1
expr_stmt|;
else|else
name|cmp
operator|=
name|Double
operator|.
name|compare
argument_list|(
name|a1
operator|.
name|getPlacement
argument_list|()
operator|.
name|getRoomSize
argument_list|()
argument_list|,
name|a2
operator|.
name|getPlacement
argument_list|()
operator|.
name|getRoomSize
argument_list|()
argument_list|)
expr_stmt|;
block|}
break|break;
block|}
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
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
if|if
condition|(
operator|!
name|co1
operator|.
name|equals
argument_list|(
name|co2
argument_list|)
condition|)
name|cmp
operator|=
name|compareCourses
argument_list|(
name|co1
argument_list|,
name|co2
argument_list|)
expr_stmt|;
if|else if
condition|(
operator|!
name|c1
operator|.
name|getSchedulingSubpart
argument_list|()
operator|.
name|equals
argument_list|(
name|c2
operator|.
name|getSchedulingSubpart
argument_list|()
argument_list|)
condition|)
name|cmp
operator|=
name|compareSubparts
argument_list|(
name|c1
operator|.
name|getSchedulingSubpart
argument_list|()
argument_list|,
name|c2
operator|.
name|getSchedulingSubpart
argument_list|()
argument_list|)
expr_stmt|;
else|else
name|cmp
operator|=
name|c1
operator|.
name|getSectionNumber
argument_list|()
operator|.
name|compareTo
argument_list|(
name|c2
operator|.
name|getSectionNumber
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
name|cmp
operator|=
name|c1
operator|.
name|getSectionNumber
argument_list|()
operator|.
name|compareTo
argument_list|(
name|c2
operator|.
name|getSectionNumber
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
name|c1
operator|.
name|getUniqueId
argument_list|()
operator|.
name|compareTo
argument_list|(
name|c2
operator|.
name|getUniqueId
argument_list|()
argument_list|)
return|;
block|}
specifier|public
name|int
name|compareByParentChildSameIType
parameter_list|(
name|CourseOffering
name|co
parameter_list|,
name|Class_
name|c1
parameter_list|,
name|Class_
name|c2
parameter_list|)
block|{
name|SchedulingSubpart
name|s1
init|=
name|c1
operator|.
name|getSchedulingSubpart
argument_list|()
decl_stmt|;
name|SchedulingSubpart
name|s2
init|=
name|c2
operator|.
name|getSchedulingSubpart
argument_list|()
decl_stmt|;
if|if
condition|(
name|s1
operator|.
name|equals
argument_list|(
name|s2
argument_list|)
condition|)
block|{
while|while
condition|(
name|s1
operator|.
name|getParentSubpart
argument_list|()
operator|!=
literal|null
operator|&&
name|s1
operator|.
name|getParentSubpart
argument_list|()
operator|.
name|getItype
argument_list|()
operator|.
name|equals
argument_list|(
name|s1
operator|.
name|getItype
argument_list|()
argument_list|)
operator|&&
operator|!
name|c1
operator|.
name|getParentClass
argument_list|()
operator|.
name|equals
argument_list|(
name|c2
operator|.
name|getParentClass
argument_list|()
argument_list|)
condition|)
block|{
name|s1
operator|=
name|s1
operator|.
name|getParentSubpart
argument_list|()
expr_stmt|;
name|c1
operator|=
name|c1
operator|.
name|getParentClass
argument_list|()
expr_stmt|;
name|s2
operator|=
name|s2
operator|.
name|getParentSubpart
argument_list|()
expr_stmt|;
name|c2
operator|=
name|c2
operator|.
name|getParentClass
argument_list|()
expr_stmt|;
block|}
return|return
name|compareClasses
argument_list|(
name|co
argument_list|,
name|co
argument_list|,
name|c1
argument_list|,
name|c2
argument_list|)
return|;
block|}
if|if
condition|(
name|s1
operator|.
name|getItype
argument_list|()
operator|.
name|equals
argument_list|(
name|s2
operator|.
name|getItype
argument_list|()
argument_list|)
condition|)
block|{
if|if
condition|(
name|isParentSameIType
argument_list|(
name|s1
argument_list|,
name|s2
argument_list|)
condition|)
block|{
while|while
condition|(
operator|!
name|s1
operator|.
name|equals
argument_list|(
name|s2
argument_list|)
condition|)
block|{
name|s1
operator|=
name|s1
operator|.
name|getParentSubpart
argument_list|()
expr_stmt|;
name|c1
operator|=
name|c1
operator|.
name|getParentClass
argument_list|()
expr_stmt|;
block|}
while|while
condition|(
name|s1
operator|.
name|getParentSubpart
argument_list|()
operator|!=
literal|null
operator|&&
name|s1
operator|.
name|getParentSubpart
argument_list|()
operator|.
name|getItype
argument_list|()
operator|.
name|equals
argument_list|(
name|s1
operator|.
name|getItype
argument_list|()
argument_list|)
condition|)
block|{
name|s1
operator|=
name|s1
operator|.
name|getParentSubpart
argument_list|()
expr_stmt|;
name|c1
operator|=
name|c1
operator|.
name|getParentClass
argument_list|()
expr_stmt|;
name|s2
operator|=
name|s2
operator|.
name|getParentSubpart
argument_list|()
expr_stmt|;
name|c2
operator|=
name|c2
operator|.
name|getParentClass
argument_list|()
expr_stmt|;
block|}
name|int
name|cmp
init|=
name|compareClasses
argument_list|(
name|co
argument_list|,
name|co
argument_list|,
name|c1
argument_list|,
name|c2
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
literal|1
return|;
block|}
if|else if
condition|(
name|isParentSameIType
argument_list|(
name|s2
argument_list|,
name|s1
argument_list|)
condition|)
block|{
while|while
condition|(
operator|!
name|s2
operator|.
name|equals
argument_list|(
name|s1
argument_list|)
condition|)
block|{
name|s2
operator|=
name|s2
operator|.
name|getParentSubpart
argument_list|()
expr_stmt|;
name|c2
operator|=
name|c2
operator|.
name|getParentClass
argument_list|()
expr_stmt|;
block|}
while|while
condition|(
name|s1
operator|.
name|getParentSubpart
argument_list|()
operator|!=
literal|null
operator|&&
name|s1
operator|.
name|getParentSubpart
argument_list|()
operator|.
name|getItype
argument_list|()
operator|.
name|equals
argument_list|(
name|s1
operator|.
name|getItype
argument_list|()
argument_list|)
condition|)
block|{
name|s1
operator|=
name|s1
operator|.
name|getParentSubpart
argument_list|()
expr_stmt|;
name|c1
operator|=
name|c1
operator|.
name|getParentClass
argument_list|()
expr_stmt|;
name|s2
operator|=
name|s2
operator|.
name|getParentSubpart
argument_list|()
expr_stmt|;
name|c2
operator|=
name|c2
operator|.
name|getParentClass
argument_list|()
expr_stmt|;
block|}
name|int
name|cmp
init|=
name|compareClasses
argument_list|(
name|co
argument_list|,
name|co
argument_list|,
name|c1
argument_list|,
name|c2
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
operator|-
literal|1
return|;
block|}
block|}
return|return
name|compareSubparts
argument_list|(
name|s1
argument_list|,
name|s2
argument_list|)
return|;
block|}
specifier|public
specifier|static
name|int
name|compareComparable
parameter_list|(
name|Comparable
name|c1
parameter_list|,
name|Comparable
name|c2
parameter_list|)
block|{
return|return
operator|(
name|c1
operator|==
literal|null
condition|?
operator|(
name|c2
operator|==
literal|null
condition|?
literal|0
else|:
operator|-
literal|1
operator|)
else|:
operator|(
name|c2
operator|==
literal|null
condition|?
literal|1
else|:
name|c1
operator|.
name|compareTo
argument_list|(
name|c2
argument_list|)
operator|)
operator|)
return|;
block|}
specifier|public
specifier|static
name|int
name|compareInstructors
parameter_list|(
name|Class_
name|c1
parameter_list|,
name|Class_
name|c2
parameter_list|)
block|{
name|TreeSet
argument_list|<
name|DepartmentalInstructor
argument_list|>
name|s1
init|=
operator|new
name|TreeSet
argument_list|<
name|DepartmentalInstructor
argument_list|>
argument_list|()
decl_stmt|;
name|TreeSet
argument_list|<
name|DepartmentalInstructor
argument_list|>
name|s2
init|=
operator|new
name|TreeSet
argument_list|<
name|DepartmentalInstructor
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|ClassInstructor
name|i
range|:
name|c1
operator|.
name|getClassInstructors
argument_list|()
control|)
name|s1
operator|.
name|add
argument_list|(
name|i
operator|.
name|getInstructor
argument_list|()
argument_list|)
expr_stmt|;
for|for
control|(
name|ClassInstructor
name|i
range|:
name|c2
operator|.
name|getClassInstructors
argument_list|()
control|)
name|s2
operator|.
name|add
argument_list|(
name|i
operator|.
name|getInstructor
argument_list|()
argument_list|)
expr_stmt|;
name|Iterator
argument_list|<
name|DepartmentalInstructor
argument_list|>
name|i1
init|=
name|s1
operator|.
name|iterator
argument_list|()
decl_stmt|;
name|Iterator
argument_list|<
name|DepartmentalInstructor
argument_list|>
name|i2
init|=
name|s2
operator|.
name|iterator
argument_list|()
decl_stmt|;
while|while
condition|(
name|i1
operator|.
name|hasNext
argument_list|()
operator|||
name|i2
operator|.
name|hasNext
argument_list|()
condition|)
block|{
if|if
condition|(
operator|!
name|i1
operator|.
name|hasNext
argument_list|()
condition|)
return|return
operator|-
literal|1
return|;
if|if
condition|(
operator|!
name|i2
operator|.
name|hasNext
argument_list|()
condition|)
return|return
literal|1
return|;
name|int
name|cmp
init|=
name|i1
operator|.
name|next
argument_list|()
operator|.
name|compareTo
argument_list|(
name|i2
operator|.
name|next
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
literal|0
return|;
block|}
specifier|public
name|int
name|compareCourses
parameter_list|(
name|CourseOffering
name|co1
parameter_list|,
name|CourseOffering
name|co2
parameter_list|)
block|{
name|int
name|cmp
init|=
name|co1
operator|.
name|getCourseName
argument_list|()
operator|.
name|compareTo
argument_list|(
name|co2
operator|.
name|getCourseName
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
name|co1
operator|.
name|getUniqueId
argument_list|()
operator|.
name|compareTo
argument_list|(
name|co2
operator|.
name|getUniqueId
argument_list|()
argument_list|)
return|;
block|}
specifier|public
name|int
name|compare
parameter_list|(
name|Object
name|o1
parameter_list|,
name|Object
name|o2
parameter_list|)
block|{
name|Class_
name|c1
decl_stmt|,
name|c2
decl_stmt|;
name|CourseOffering
name|co1
decl_stmt|,
name|co2
decl_stmt|;
if|if
condition|(
name|o1
operator|instanceof
name|Class_
condition|)
block|{
name|c1
operator|=
operator|(
name|Class_
operator|)
name|o1
expr_stmt|;
name|c2
operator|=
operator|(
name|Class_
operator|)
name|o2
expr_stmt|;
name|co1
operator|=
name|c1
operator|.
name|getSchedulingSubpart
argument_list|()
operator|.
name|getControllingCourseOffering
argument_list|()
expr_stmt|;
name|co2
operator|=
name|c2
operator|.
name|getSchedulingSubpart
argument_list|()
operator|.
name|getControllingCourseOffering
argument_list|()
expr_stmt|;
block|}
else|else
block|{
name|c1
operator|=
operator|(
name|Class_
operator|)
operator|(
operator|(
name|Object
index|[]
operator|)
name|o1
operator|)
index|[
literal|0
index|]
expr_stmt|;
name|c2
operator|=
operator|(
name|Class_
operator|)
operator|(
operator|(
name|Object
index|[]
operator|)
name|o2
operator|)
index|[
literal|0
index|]
expr_stmt|;
name|co1
operator|=
operator|(
name|CourseOffering
operator|)
operator|(
operator|(
name|Object
index|[]
operator|)
name|o1
operator|)
index|[
literal|1
index|]
expr_stmt|;
name|co2
operator|=
operator|(
name|CourseOffering
operator|)
operator|(
operator|(
name|Object
index|[]
operator|)
name|o2
operator|)
index|[
literal|1
index|]
expr_stmt|;
block|}
if|if
condition|(
operator|!
name|co1
operator|.
name|getSubjectArea
argument_list|()
operator|.
name|equals
argument_list|(
name|co2
operator|.
name|getSubjectArea
argument_list|()
argument_list|)
condition|)
block|{
name|int
name|cmp
init|=
name|co1
operator|.
name|getSubjectAreaAbbv
argument_list|()
operator|.
name|compareToIgnoreCase
argument_list|(
name|co2
operator|.
name|getSubjectAreaAbbv
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
name|co1
operator|.
name|getSubjectArea
argument_list|()
operator|.
name|getUniqueId
argument_list|()
operator|.
name|compareTo
argument_list|(
name|co2
operator|.
name|getSubjectArea
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|)
return|;
block|}
if|if
condition|(
name|iKeepSubpart
condition|)
block|{
if|if
condition|(
operator|!
name|co1
operator|.
name|equals
argument_list|(
name|co2
argument_list|)
condition|)
return|return
name|compareCourses
argument_list|(
name|co1
argument_list|,
name|co2
argument_list|)
return|;
return|return
name|compareByParentChildSameIType
argument_list|(
name|co1
argument_list|,
name|c1
argument_list|,
name|c2
argument_list|)
return|;
block|}
return|return
name|compareClasses
argument_list|(
name|co1
argument_list|,
name|co2
argument_list|,
name|c1
argument_list|,
name|c2
argument_list|)
return|;
block|}
block|}
end_class

end_unit

