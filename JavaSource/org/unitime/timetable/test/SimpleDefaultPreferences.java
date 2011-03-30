begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.2 (University Timetabling Application)  * Copyright (C) 2011, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
end_comment

begin_package
package|package
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|test
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
name|apache
operator|.
name|log4j
operator|.
name|BasicConfigurator
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|log4j
operator|.
name|Logger
import|;
end_import

begin_import
import|import
name|org
operator|.
name|unitime
operator|.
name|commons
operator|.
name|hibernate
operator|.
name|util
operator|.
name|HibernateUtil
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
name|ApplicationProperties
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
name|InstrOfferingConfig
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
name|InstructionalOffering
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
name|Location
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
name|PreferenceLevel
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
name|RoomPref
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
name|Session
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
name|model
operator|.
name|TimePatternDays
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
name|TimePatternModel
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
name|TimePatternTime
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
name|TimePref
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
name|_RootDAO
import|;
end_import

begin_comment
comment|/**  * Example Java program that sets default preferences based on the committed (imported) assignment.  * For each class, it strongly prefers its time and room assignment. If there is no matching time  * pattern found, exact time pattern is used.  * Date pattern and preferences are moved to subpart level, if possible. Change limit of unlimited  * configurations to 100. Unlimited classes are changed to have a limit too, but the room ratio is set  * to zero (any room is big enough).  *   * @author Tomas Muller  *  */
end_comment

begin_class
specifier|public
class|class
name|SimpleDefaultPreferences
block|{
specifier|public
specifier|static
name|void
name|main
parameter_list|(
name|String
index|[]
name|args
parameter_list|)
block|{
try|try
block|{
comment|// Configure logging
name|BasicConfigurator
operator|.
name|configure
argument_list|()
expr_stmt|;
name|Logger
name|log
init|=
name|Logger
operator|.
name|getLogger
argument_list|(
name|SimpleDefaultPreferences
operator|.
name|class
argument_list|)
decl_stmt|;
comment|// Configure hibernate
name|HibernateUtil
operator|.
name|configureHibernate
argument_list|(
name|ApplicationProperties
operator|.
name|getProperties
argument_list|()
argument_list|)
expr_stmt|;
comment|// Opens hibernate session
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
init|=
operator|new
name|_RootDAO
argument_list|()
operator|.
name|getSession
argument_list|()
decl_stmt|;
comment|// Finds the academic session
name|Session
name|session
init|=
name|Session
operator|.
name|getSessionUsingInitiativeYearTerm
argument_list|(
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"initiative"
argument_list|,
literal|"woebegon"
argument_list|)
argument_list|,
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"year"
argument_list|,
literal|"2010"
argument_list|)
argument_list|,
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"term"
argument_list|,
literal|"Fal"
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|session
operator|==
literal|null
condition|)
block|{
name|log
operator|.
name|fatal
argument_list|(
literal|"Academic session not found, use properties initiative, year, and term to set academic session."
argument_list|)
expr_stmt|;
name|System
operator|.
name|exit
argument_list|(
literal|0
argument_list|)
expr_stmt|;
block|}
comment|// List all visible time patterns, order by type (standard time patterns first)
name|Set
argument_list|<
name|TimePattern
argument_list|>
name|patterns
init|=
operator|new
name|TreeSet
argument_list|<
name|TimePattern
argument_list|>
argument_list|(
name|TimePattern
operator|.
name|findAll
argument_list|(
name|session
argument_list|,
literal|true
argument_list|)
argument_list|)
decl_stmt|;
comment|// Iterate over all the offerings
for|for
control|(
name|InstructionalOffering
name|offering
range|:
name|session
operator|.
name|getInstructionalOfferings
argument_list|()
control|)
block|{
name|log
operator|.
name|info
argument_list|(
literal|"Processing "
operator|+
name|offering
operator|.
name|getCourseName
argument_list|()
argument_list|)
expr_stmt|;
comment|// Iterate over all courses of the offering (if needed)
for|for
control|(
name|CourseOffering
name|course
range|:
name|offering
operator|.
name|getCourseOfferings
argument_list|()
control|)
block|{
comment|// Here, you can tweak what is set on the course
comment|// Only needed if there is something changed on the course...
name|hibSession
operator|.
name|saveOrUpdate
argument_list|(
name|course
argument_list|)
expr_stmt|;
block|}
comment|// Iterate over all the configs of the offering
for|for
control|(
name|InstrOfferingConfig
name|config
range|:
name|offering
operator|.
name|getInstrOfferingConfigs
argument_list|()
control|)
block|{
comment|// If config is unlimited -> change to limit 100
name|boolean
name|unlimited
init|=
name|config
operator|.
name|isUnlimitedEnrollment
argument_list|()
decl_stmt|;
if|if
condition|(
name|unlimited
condition|)
block|{
name|config
operator|.
name|setLimit
argument_list|(
literal|100
argument_list|)
expr_stmt|;
name|config
operator|.
name|setUnlimitedEnrollment
argument_list|(
literal|false
argument_list|)
expr_stmt|;
block|}
comment|// Iterate over all the subparts of the config
for|for
control|(
name|SchedulingSubpart
name|subpart
range|:
name|config
operator|.
name|getSchedulingSubparts
argument_list|()
control|)
block|{
comment|// Subpart date pattern (to be set if all classes have the same date pattern).
name|DatePattern
name|datePattern
init|=
literal|null
decl_stmt|;
name|boolean
name|sameDatePattern
init|=
literal|true
decl_stmt|;
comment|// Subpart time pattern (to be set if all classes have the same time pattern).
name|TimePattern
name|timePattern
init|=
literal|null
decl_stmt|;
name|boolean
name|sameTimePattern
init|=
literal|true
decl_stmt|;
name|List
argument_list|<
name|int
index|[]
argument_list|>
name|dateTimes
init|=
operator|new
name|ArrayList
argument_list|<
name|int
index|[]
argument_list|>
argument_list|()
decl_stmt|;
comment|// Subpart room preferences (to be set if all classes have the same rooms).
name|Set
argument_list|<
name|Location
argument_list|>
name|rooms
init|=
literal|null
decl_stmt|;
name|boolean
name|sameRooms
init|=
literal|true
decl_stmt|;
comment|// Iterate over all the classes of the subpart
for|for
control|(
name|Class_
name|clazz
range|:
name|subpart
operator|.
name|getClasses
argument_list|()
control|)
block|{
name|log
operator|.
name|info
argument_list|(
literal|"-- "
operator|+
name|clazz
operator|.
name|getClassLabel
argument_list|(
name|hibSession
argument_list|)
argument_list|)
expr_stmt|;
comment|// If config was unlimited, set class limit to 100 and room ratio to 0.0
if|if
condition|(
name|unlimited
condition|)
block|{
name|int
name|limit
init|=
operator|(
name|int
operator|)
name|Math
operator|.
name|ceil
argument_list|(
literal|100.0
operator|/
name|subpart
operator|.
name|getClasses
argument_list|()
operator|.
name|size
argument_list|()
argument_list|)
decl_stmt|;
name|clazz
operator|.
name|setMaxExpectedCapacity
argument_list|(
name|limit
argument_list|)
expr_stmt|;
name|clazz
operator|.
name|setExpectedCapacity
argument_list|(
name|limit
argument_list|)
expr_stmt|;
name|clazz
operator|.
name|setRoomRatio
argument_list|(
literal|0f
argument_list|)
expr_stmt|;
block|}
comment|// Remove class preferences
name|clazz
operator|.
name|getPreferences
argument_list|()
operator|.
name|clear
argument_list|()
expr_stmt|;
comment|// Committed (imported) assignment
name|Assignment
name|assignment
init|=
name|clazz
operator|.
name|getCommittedAssignment
argument_list|()
decl_stmt|;
comment|// Check if this class has the same date pattern as the others
if|if
condition|(
name|datePattern
operator|==
literal|null
condition|)
block|{
name|datePattern
operator|=
name|clazz
operator|.
name|effectiveDatePattern
argument_list|()
expr_stmt|;
block|}
if|else if
condition|(
name|sameDatePattern
operator|&&
operator|!
name|datePattern
operator|.
name|equals
argument_list|(
name|clazz
operator|.
name|effectiveDatePattern
argument_list|()
argument_list|)
condition|)
block|{
name|sameDatePattern
operator|=
literal|false
expr_stmt|;
block|}
comment|// Find matching time pattern
if|if
condition|(
name|assignment
operator|!=
literal|null
condition|)
block|{
name|TimePattern
name|pattern
init|=
literal|null
decl_stmt|;
for|for
control|(
name|TimePattern
name|p
range|:
name|patterns
control|)
block|{
if|if
condition|(
name|p
operator|.
name|getNrMeetings
argument_list|()
operator|*
name|p
operator|.
name|getMinPerMtg
argument_list|()
operator|!=
name|subpart
operator|.
name|getMinutesPerWk
argument_list|()
condition|)
continue|continue;
comment|// minutes per week does not match
comment|// Find matching days
name|TimePatternDays
name|days
init|=
literal|null
decl_stmt|;
for|for
control|(
name|TimePatternDays
name|d
range|:
name|p
operator|.
name|getDays
argument_list|()
control|)
if|if
condition|(
name|d
operator|.
name|getDayCode
argument_list|()
operator|.
name|equals
argument_list|(
name|assignment
operator|.
name|getDays
argument_list|()
argument_list|)
condition|)
block|{
name|days
operator|=
name|d
expr_stmt|;
break|break;
block|}
if|if
condition|(
name|days
operator|==
literal|null
condition|)
continue|continue;
comment|// Find matching time
name|TimePatternTime
name|time
init|=
literal|null
decl_stmt|;
for|for
control|(
name|TimePatternTime
name|t
range|:
name|p
operator|.
name|getTimes
argument_list|()
control|)
if|if
condition|(
name|t
operator|.
name|getStartSlot
argument_list|()
operator|.
name|equals
argument_list|(
name|assignment
operator|.
name|getStartSlot
argument_list|()
argument_list|)
condition|)
block|{
name|time
operator|=
name|t
expr_stmt|;
break|break;
block|}
if|if
condition|(
name|time
operator|==
literal|null
condition|)
continue|continue;
name|pattern
operator|=
name|p
expr_stmt|;
break|break;
block|}
if|if
condition|(
name|pattern
operator|==
literal|null
condition|)
block|{
comment|// No pattern found, use exact time pattern
name|pattern
operator|=
name|TimePattern
operator|.
name|findExactTime
argument_list|(
name|session
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|pattern
operator|!=
literal|null
condition|)
block|{
name|TimePatternModel
name|m
init|=
name|pattern
operator|.
name|getTimePatternModel
argument_list|()
decl_stmt|;
name|m
operator|.
name|setExactDays
argument_list|(
name|assignment
operator|.
name|getDays
argument_list|()
argument_list|)
expr_stmt|;
name|m
operator|.
name|setExactStartSlot
argument_list|(
name|assignment
operator|.
name|getStartSlot
argument_list|()
argument_list|)
expr_stmt|;
name|TimePref
name|tp
init|=
operator|new
name|TimePref
argument_list|()
decl_stmt|;
name|tp
operator|.
name|setPrefLevel
argument_list|(
name|PreferenceLevel
operator|.
name|getPreferenceLevel
argument_list|(
name|PreferenceLevel
operator|.
name|sRequired
argument_list|)
argument_list|)
expr_stmt|;
name|tp
operator|.
name|setTimePatternModel
argument_list|(
name|m
argument_list|)
expr_stmt|;
name|tp
operator|.
name|setOwner
argument_list|(
name|clazz
argument_list|)
expr_stmt|;
name|clazz
operator|.
name|getPreferences
argument_list|()
operator|.
name|add
argument_list|(
name|tp
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
comment|// Strongly prefer the assigned time
name|TimePatternModel
name|m
init|=
name|pattern
operator|.
name|getTimePatternModel
argument_list|()
decl_stmt|;
for|for
control|(
name|int
name|d
init|=
literal|0
init|;
name|d
operator|<
name|m
operator|.
name|getNrDays
argument_list|()
condition|;
name|d
operator|++
control|)
for|for
control|(
name|int
name|t
init|=
literal|0
init|;
name|t
operator|<
name|m
operator|.
name|getNrTimes
argument_list|()
condition|;
name|t
operator|++
control|)
block|{
if|if
condition|(
name|assignment
operator|.
name|getStartSlot
argument_list|()
operator|==
name|m
operator|.
name|getStartSlot
argument_list|(
name|t
argument_list|)
operator|&&
name|assignment
operator|.
name|getDays
argument_list|()
operator|==
name|m
operator|.
name|getDayCode
argument_list|(
name|d
argument_list|)
condition|)
block|{
name|m
operator|.
name|setPreference
argument_list|(
name|d
argument_list|,
name|t
argument_list|,
name|PreferenceLevel
operator|.
name|sStronglyPreferred
argument_list|)
expr_stmt|;
name|dateTimes
operator|.
name|add
argument_list|(
operator|new
name|int
index|[]
block|{
name|d
block|,
name|t
block|}
argument_list|)
expr_stmt|;
block|}
block|}
comment|// only set the time pattern on the class if there are more than one class in the subpart (otherwise it will be set on the subpart)
if|if
condition|(
name|subpart
operator|.
name|getClasses
argument_list|()
operator|.
name|size
argument_list|()
operator|>
literal|1
condition|)
block|{
name|TimePref
name|tp
init|=
operator|new
name|TimePref
argument_list|()
decl_stmt|;
name|tp
operator|.
name|setPrefLevel
argument_list|(
name|PreferenceLevel
operator|.
name|getPreferenceLevel
argument_list|(
name|PreferenceLevel
operator|.
name|sRequired
argument_list|)
argument_list|)
expr_stmt|;
name|tp
operator|.
name|setTimePatternModel
argument_list|(
name|m
argument_list|)
expr_stmt|;
name|tp
operator|.
name|setOwner
argument_list|(
name|clazz
argument_list|)
expr_stmt|;
name|clazz
operator|.
name|getPreferences
argument_list|()
operator|.
name|add
argument_list|(
name|tp
argument_list|)
expr_stmt|;
block|}
block|}
if|if
condition|(
name|timePattern
operator|==
literal|null
condition|)
block|{
name|timePattern
operator|=
name|pattern
expr_stmt|;
block|}
if|else if
condition|(
name|sameTimePattern
operator|&&
operator|!
name|timePattern
operator|.
name|equals
argument_list|(
name|pattern
argument_list|)
condition|)
block|{
name|sameTimePattern
operator|=
literal|false
expr_stmt|;
block|}
block|}
comment|// Strongly prefer assigned room(s)
if|if
condition|(
name|assignment
operator|!=
literal|null
condition|)
block|{
comment|// Set number of rooms to match the assignment
name|clazz
operator|.
name|setNbrRooms
argument_list|(
name|assignment
operator|.
name|getRooms
argument_list|()
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|rooms
operator|==
literal|null
condition|)
block|{
name|rooms
operator|=
operator|new
name|TreeSet
argument_list|<
name|Location
argument_list|>
argument_list|(
name|assignment
operator|.
name|getRooms
argument_list|()
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|sameRooms
operator|&&
operator|!
name|rooms
operator|.
name|equals
argument_list|(
operator|new
name|TreeSet
argument_list|<
name|Location
argument_list|>
argument_list|(
name|assignment
operator|.
name|getRooms
argument_list|()
argument_list|)
argument_list|)
condition|)
block|{
name|sameRooms
operator|=
literal|false
expr_stmt|;
block|}
comment|// only set the time pattern on the class if there are more than one class in the subpart (otherwise it will be set on the subpart)
if|if
condition|(
name|subpart
operator|.
name|getClasses
argument_list|()
operator|.
name|size
argument_list|()
operator|>
literal|1
condition|)
block|{
for|for
control|(
name|Location
name|room
range|:
name|assignment
operator|.
name|getRooms
argument_list|()
control|)
block|{
name|RoomPref
name|rp
init|=
operator|new
name|RoomPref
argument_list|()
decl_stmt|;
name|rp
operator|.
name|setPrefLevel
argument_list|(
name|PreferenceLevel
operator|.
name|getPreferenceLevel
argument_list|(
name|PreferenceLevel
operator|.
name|sStronglyPreferred
argument_list|)
argument_list|)
expr_stmt|;
name|rp
operator|.
name|setOwner
argument_list|(
name|clazz
argument_list|)
expr_stmt|;
name|rp
operator|.
name|setRoom
argument_list|(
name|room
argument_list|)
expr_stmt|;
name|clazz
operator|.
name|getPreferences
argument_list|()
operator|.
name|add
argument_list|(
name|rp
argument_list|)
expr_stmt|;
block|}
block|}
block|}
name|hibSession
operator|.
name|saveOrUpdate
argument_list|(
name|clazz
argument_list|)
expr_stmt|;
block|}
comment|// If all classes have the same (not default) date pattern, set it on subpart instead
if|if
condition|(
name|datePattern
operator|!=
literal|null
operator|&&
name|sameDatePattern
operator|&&
operator|!
name|datePattern
operator|.
name|isDefault
argument_list|()
condition|)
block|{
name|subpart
operator|.
name|setDatePattern
argument_list|(
name|datePattern
argument_list|)
expr_stmt|;
for|for
control|(
name|Class_
name|clazz
range|:
name|subpart
operator|.
name|getClasses
argument_list|()
control|)
block|{
name|clazz
operator|.
name|setDatePattern
argument_list|(
literal|null
argument_list|)
expr_stmt|;
name|hibSession
operator|.
name|saveOrUpdate
argument_list|(
name|clazz
argument_list|)
expr_stmt|;
block|}
block|}
comment|// Remove subpart preferences
name|subpart
operator|.
name|getPreferences
argument_list|()
operator|.
name|clear
argument_list|()
expr_stmt|;
comment|// Set time preferences on the subpart (if all the classes have the same time pattern)
if|if
condition|(
name|timePattern
operator|!=
literal|null
operator|&&
name|sameTimePattern
operator|&&
name|timePattern
operator|.
name|getType
argument_list|()
operator|!=
name|TimePattern
operator|.
name|sTypeExactTime
condition|)
block|{
name|TimePref
name|tp
init|=
operator|new
name|TimePref
argument_list|()
decl_stmt|;
name|tp
operator|.
name|setPrefLevel
argument_list|(
name|PreferenceLevel
operator|.
name|getPreferenceLevel
argument_list|(
name|PreferenceLevel
operator|.
name|sRequired
argument_list|)
argument_list|)
expr_stmt|;
name|TimePatternModel
name|m
init|=
name|timePattern
operator|.
name|getTimePatternModel
argument_list|()
decl_stmt|;
if|if
condition|(
name|dateTimes
operator|.
name|size
argument_list|()
operator|==
literal|1
condition|)
for|for
control|(
name|int
index|[]
name|dt
range|:
name|dateTimes
control|)
name|m
operator|.
name|setPreference
argument_list|(
name|dt
index|[
literal|0
index|]
argument_list|,
name|dt
index|[
literal|1
index|]
argument_list|,
name|PreferenceLevel
operator|.
name|sStronglyPreferred
argument_list|)
expr_stmt|;
name|tp
operator|.
name|setTimePatternModel
argument_list|(
name|m
argument_list|)
expr_stmt|;
name|tp
operator|.
name|setOwner
argument_list|(
name|subpart
argument_list|)
expr_stmt|;
name|subpart
operator|.
name|getPreferences
argument_list|()
operator|.
name|add
argument_list|(
name|tp
argument_list|)
expr_stmt|;
block|}
comment|// Set room preferences on the subpart (if all the classes have the same room prefs)
if|if
condition|(
name|rooms
operator|!=
literal|null
operator|&&
name|sameRooms
condition|)
block|{
for|for
control|(
name|Location
name|room
range|:
name|rooms
control|)
block|{
name|RoomPref
name|rp
init|=
operator|new
name|RoomPref
argument_list|()
decl_stmt|;
name|rp
operator|.
name|setPrefLevel
argument_list|(
name|PreferenceLevel
operator|.
name|getPreferenceLevel
argument_list|(
name|PreferenceLevel
operator|.
name|sStronglyPreferred
argument_list|)
argument_list|)
expr_stmt|;
name|rp
operator|.
name|setOwner
argument_list|(
name|subpart
argument_list|)
expr_stmt|;
name|rp
operator|.
name|setRoom
argument_list|(
name|room
argument_list|)
expr_stmt|;
name|subpart
operator|.
name|getPreferences
argument_list|()
operator|.
name|add
argument_list|(
name|rp
argument_list|)
expr_stmt|;
block|}
comment|// These preferences do not need to be replicated on classes, remove room preferences from all the classes
for|for
control|(
name|Class_
name|clazz
range|:
name|subpart
operator|.
name|getClasses
argument_list|()
control|)
block|{
name|clazz
operator|.
name|getPreferences
argument_list|()
operator|.
name|removeAll
argument_list|(
name|clazz
operator|.
name|getPreferences
argument_list|(
name|RoomPref
operator|.
name|class
argument_list|)
argument_list|)
expr_stmt|;
name|hibSession
operator|.
name|save
argument_list|(
name|clazz
argument_list|)
expr_stmt|;
block|}
block|}
name|hibSession
operator|.
name|saveOrUpdate
argument_list|(
name|subpart
argument_list|)
expr_stmt|;
block|}
name|hibSession
operator|.
name|saveOrUpdate
argument_list|(
name|config
argument_list|)
expr_stmt|;
block|}
name|hibSession
operator|.
name|saveOrUpdate
argument_list|(
name|offering
argument_list|)
expr_stmt|;
block|}
name|log
operator|.
name|info
argument_list|(
literal|"All done."
argument_list|)
expr_stmt|;
comment|// Flush and close the hibernate session
name|hibSession
operator|.
name|flush
argument_list|()
expr_stmt|;
name|hibSession
operator|.
name|close
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|e
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

