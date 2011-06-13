begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.2 (University Timetabling Application)  * Copyright (C) 2010, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
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
name|shared
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
name|Collection
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
name|TreeSet
import|;
end_import

begin_import
import|import
name|com
operator|.
name|google
operator|.
name|gwt
operator|.
name|user
operator|.
name|client
operator|.
name|rpc
operator|.
name|IsSerializable
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|EventInterface
implements|implements
name|Comparable
argument_list|<
name|EventInterface
argument_list|>
implements|,
name|IsSerializable
block|{
specifier|private
name|Long
name|iEventId
decl_stmt|;
specifier|private
name|String
name|iEventName
decl_stmt|;
specifier|private
name|String
name|iEventType
decl_stmt|;
specifier|private
name|TreeSet
argument_list|<
name|MeetingInterface
argument_list|>
name|iMeetings
init|=
literal|null
decl_stmt|;
specifier|private
name|String
name|iSponsor
decl_stmt|,
name|iInstructor
decl_stmt|,
name|iContact
decl_stmt|,
name|iEmail
decl_stmt|;
specifier|private
name|List
argument_list|<
name|String
argument_list|>
name|iCourseNames
init|=
literal|null
decl_stmt|;
specifier|private
name|String
name|iInstruction
init|=
literal|null
decl_stmt|;
specifier|private
name|Integer
name|iInstructionType
init|=
literal|null
decl_stmt|;
specifier|private
name|List
argument_list|<
name|String
argument_list|>
name|iExternalIds
init|=
literal|null
decl_stmt|;
specifier|public
specifier|static
enum|enum
name|ResourceType
block|{
name|ROOM
argument_list|(
literal|"room"
argument_list|,
literal|"Room Timetable"
argument_list|)
block|,
name|SUBJECT
argument_list|(
literal|"subject"
argument_list|,
literal|"Subject Timetable"
argument_list|)
block|,
name|CURRICULUM
argument_list|(
literal|"curriculum"
argument_list|,
literal|"Curriculum Timetable"
argument_list|)
block|,
name|DEPARTMENT
argument_list|(
literal|"department"
argument_list|,
literal|"Departmental Timetable"
argument_list|)
block|,
name|PERSON
argument_list|(
literal|"person"
argument_list|,
literal|"Personal Timetable"
argument_list|)
block|;
specifier|private
name|String
name|iLabel
decl_stmt|;
specifier|private
name|String
name|iPageTitle
decl_stmt|;
name|ResourceType
parameter_list|(
name|String
name|label
parameter_list|,
name|String
name|title
parameter_list|)
block|{
name|iLabel
operator|=
name|label
expr_stmt|;
name|iPageTitle
operator|=
name|title
expr_stmt|;
block|}
specifier|public
name|String
name|getLabel
parameter_list|()
block|{
return|return
name|iLabel
return|;
block|}
specifier|public
name|String
name|getPageTitle
parameter_list|()
block|{
return|return
name|iPageTitle
return|;
block|}
block|}
specifier|public
name|EventInterface
parameter_list|()
block|{
block|}
specifier|public
name|Long
name|getId
parameter_list|()
block|{
return|return
name|iEventId
return|;
block|}
specifier|public
name|void
name|setId
parameter_list|(
name|Long
name|id
parameter_list|)
block|{
name|iEventId
operator|=
name|id
expr_stmt|;
block|}
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
name|iEventName
return|;
block|}
specifier|public
name|void
name|setName
parameter_list|(
name|String
name|name
parameter_list|)
block|{
name|iEventName
operator|=
name|name
expr_stmt|;
block|}
specifier|public
name|String
name|getType
parameter_list|()
block|{
return|return
name|iEventType
return|;
block|}
specifier|public
name|void
name|setType
parameter_list|(
name|String
name|type
parameter_list|)
block|{
name|iEventType
operator|=
name|type
expr_stmt|;
block|}
specifier|public
name|String
name|getSponsor
parameter_list|()
block|{
return|return
name|iSponsor
return|;
block|}
specifier|public
name|void
name|setSponsor
parameter_list|(
name|String
name|sponsor
parameter_list|)
block|{
name|iSponsor
operator|=
name|sponsor
expr_stmt|;
block|}
specifier|public
name|boolean
name|hasSponsor
parameter_list|()
block|{
return|return
name|iSponsor
operator|!=
literal|null
operator|&&
operator|!
name|iSponsor
operator|.
name|isEmpty
argument_list|()
return|;
block|}
specifier|public
name|String
name|getInstructor
parameter_list|()
block|{
return|return
name|iInstructor
return|;
block|}
specifier|public
name|void
name|setInstructor
parameter_list|(
name|String
name|instructor
parameter_list|)
block|{
name|iInstructor
operator|=
name|instructor
expr_stmt|;
block|}
specifier|public
name|boolean
name|hasInstructor
parameter_list|()
block|{
return|return
name|iInstructor
operator|!=
literal|null
operator|&&
operator|!
name|iInstructor
operator|.
name|isEmpty
argument_list|()
return|;
block|}
specifier|public
name|String
name|getEmail
parameter_list|()
block|{
return|return
name|iEmail
return|;
block|}
specifier|public
name|void
name|setEmail
parameter_list|(
name|String
name|email
parameter_list|)
block|{
name|iEmail
operator|=
name|email
expr_stmt|;
block|}
specifier|public
name|String
name|getContact
parameter_list|()
block|{
return|return
name|iContact
return|;
block|}
specifier|public
name|void
name|setContact
parameter_list|(
name|String
name|contact
parameter_list|)
block|{
name|iContact
operator|=
name|contact
expr_stmt|;
block|}
specifier|public
name|boolean
name|hasContact
parameter_list|()
block|{
return|return
name|iContact
operator|!=
literal|null
operator|&&
operator|!
name|iContact
operator|.
name|isEmpty
argument_list|()
return|;
block|}
specifier|public
name|boolean
name|hasMeetings
parameter_list|()
block|{
return|return
name|iMeetings
operator|!=
literal|null
operator|&&
operator|!
name|iMeetings
operator|.
name|isEmpty
argument_list|()
return|;
block|}
specifier|public
name|void
name|addMeeting
parameter_list|(
name|MeetingInterface
name|meeting
parameter_list|)
block|{
if|if
condition|(
name|iMeetings
operator|==
literal|null
condition|)
name|iMeetings
operator|=
operator|new
name|TreeSet
argument_list|<
name|MeetingInterface
argument_list|>
argument_list|()
expr_stmt|;
name|iMeetings
operator|.
name|add
argument_list|(
name|meeting
argument_list|)
expr_stmt|;
block|}
specifier|public
name|TreeSet
argument_list|<
name|MeetingInterface
argument_list|>
name|getMeetings
parameter_list|()
block|{
return|return
name|iMeetings
return|;
block|}
specifier|public
name|boolean
name|hasCourseNames
parameter_list|()
block|{
return|return
name|iCourseNames
operator|!=
literal|null
operator|&&
operator|!
name|iCourseNames
operator|.
name|isEmpty
argument_list|()
return|;
block|}
specifier|public
name|void
name|addCourseName
parameter_list|(
name|String
name|name
parameter_list|)
block|{
if|if
condition|(
name|iCourseNames
operator|==
literal|null
condition|)
name|iCourseNames
operator|=
operator|new
name|ArrayList
argument_list|<
name|String
argument_list|>
argument_list|()
expr_stmt|;
name|iCourseNames
operator|.
name|add
argument_list|(
name|name
argument_list|)
expr_stmt|;
block|}
specifier|public
name|List
argument_list|<
name|String
argument_list|>
name|getCourseNames
parameter_list|()
block|{
return|return
name|iCourseNames
return|;
block|}
specifier|public
name|boolean
name|hasInstruction
parameter_list|()
block|{
return|return
name|iInstruction
operator|!=
literal|null
operator|&&
operator|!
name|iInstruction
operator|.
name|isEmpty
argument_list|()
return|;
block|}
specifier|public
name|String
name|getInstruction
parameter_list|()
block|{
return|return
name|iInstruction
return|;
block|}
specifier|public
name|void
name|setInstruction
parameter_list|(
name|String
name|instruction
parameter_list|)
block|{
name|iInstruction
operator|=
name|instruction
expr_stmt|;
block|}
specifier|public
name|boolean
name|hasInstructionType
parameter_list|()
block|{
return|return
name|iInstructionType
operator|!=
literal|null
return|;
block|}
specifier|public
name|Integer
name|getInstructionType
parameter_list|()
block|{
return|return
name|iInstructionType
return|;
block|}
specifier|public
name|void
name|setInstructionType
parameter_list|(
name|Integer
name|type
parameter_list|)
block|{
name|iInstructionType
operator|=
name|type
expr_stmt|;
block|}
specifier|public
name|boolean
name|hasExternalIds
parameter_list|()
block|{
return|return
name|iExternalIds
operator|!=
literal|null
operator|&&
operator|!
name|iExternalIds
operator|.
name|isEmpty
argument_list|()
return|;
block|}
specifier|public
name|List
argument_list|<
name|String
argument_list|>
name|getExternalIds
parameter_list|()
block|{
return|return
name|iExternalIds
return|;
block|}
specifier|public
name|void
name|addExternalId
parameter_list|(
name|String
name|externalId
parameter_list|)
block|{
if|if
condition|(
name|iExternalIds
operator|==
literal|null
condition|)
name|iExternalIds
operator|=
operator|new
name|ArrayList
argument_list|<
name|String
argument_list|>
argument_list|()
expr_stmt|;
name|iExternalIds
operator|.
name|add
argument_list|(
name|externalId
argument_list|)
expr_stmt|;
block|}
specifier|public
name|int
name|hashCode
parameter_list|()
block|{
return|return
name|getId
argument_list|()
operator|.
name|hashCode
argument_list|()
return|;
block|}
specifier|public
name|boolean
name|equals
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
name|EventInterface
operator|)
condition|)
return|return
literal|false
return|;
return|return
name|getId
argument_list|()
operator|.
name|equals
argument_list|(
operator|(
operator|(
name|EventInterface
operator|)
name|o
operator|)
operator|.
name|getId
argument_list|()
argument_list|)
return|;
block|}
specifier|public
name|int
name|compareTo
parameter_list|(
name|EventInterface
name|event
parameter_list|)
block|{
name|int
name|cmp
init|=
name|getType
argument_list|()
operator|.
name|compareTo
argument_list|(
name|event
operator|.
name|getType
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
if|if
condition|(
name|hasInstructionType
argument_list|()
condition|)
block|{
name|cmp
operator|=
name|getInstructionType
argument_list|()
operator|.
name|compareTo
argument_list|(
name|event
operator|.
name|getInstructionType
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
block|}
name|cmp
operator|=
name|getName
argument_list|()
operator|.
name|compareTo
argument_list|(
name|event
operator|.
name|getName
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
name|getId
argument_list|()
operator|.
name|compareTo
argument_list|(
name|event
operator|.
name|getId
argument_list|()
argument_list|)
return|;
block|}
specifier|public
specifier|static
class|class
name|IdValueInterface
implements|implements
name|IsSerializable
block|{
specifier|private
name|String
name|iId
decl_stmt|,
name|iValue
decl_stmt|;
specifier|private
name|boolean
name|iSelected
init|=
literal|false
decl_stmt|;
specifier|public
name|IdValueInterface
parameter_list|()
block|{
block|}
specifier|public
name|IdValueInterface
parameter_list|(
name|String
name|id
parameter_list|,
name|String
name|value
parameter_list|)
block|{
name|iId
operator|=
name|id
expr_stmt|;
name|iValue
operator|=
name|value
expr_stmt|;
block|}
specifier|public
name|String
name|getId
parameter_list|()
block|{
return|return
name|iId
return|;
block|}
specifier|public
name|void
name|setId
parameter_list|(
name|String
name|id
parameter_list|)
block|{
name|iId
operator|=
name|id
expr_stmt|;
block|}
specifier|public
name|String
name|getValue
parameter_list|()
block|{
return|return
name|iValue
return|;
block|}
specifier|public
name|void
name|setValue
parameter_list|(
name|String
name|value
parameter_list|)
block|{
name|iValue
operator|=
name|value
expr_stmt|;
block|}
specifier|public
name|boolean
name|isSelected
parameter_list|()
block|{
return|return
name|iSelected
return|;
block|}
specifier|public
name|void
name|setSelected
parameter_list|(
name|boolean
name|selected
parameter_list|)
block|{
name|iSelected
operator|=
name|selected
expr_stmt|;
block|}
block|}
specifier|public
specifier|static
class|class
name|ResourceInterface
implements|implements
name|IsSerializable
implements|,
name|Comparable
argument_list|<
name|ResourceInterface
argument_list|>
block|{
specifier|private
name|ResourceType
name|iResourceType
decl_stmt|;
specifier|private
name|Long
name|iResourceId
decl_stmt|;
specifier|private
name|String
name|iExternalId
decl_stmt|;
specifier|private
name|String
name|iAbbreviation
decl_stmt|;
specifier|private
name|String
name|iResourceName
decl_stmt|;
specifier|private
name|String
name|iTitle
decl_stmt|;
specifier|private
name|Long
name|iSessionId
decl_stmt|;
specifier|private
name|String
name|iSessionName
decl_stmt|;
specifier|private
name|String
name|iSessionAbbv
decl_stmt|;
specifier|private
name|List
argument_list|<
name|WeekInterface
argument_list|>
name|iWeeks
init|=
literal|null
decl_stmt|;
specifier|private
name|String
name|iCalendar
decl_stmt|;
specifier|private
name|String
name|iHint
init|=
literal|null
decl_stmt|;
specifier|public
name|ResourceInterface
parameter_list|()
block|{
block|}
specifier|public
name|ResourceType
name|getType
parameter_list|()
block|{
return|return
name|iResourceType
return|;
block|}
specifier|public
name|void
name|setType
parameter_list|(
name|ResourceType
name|type
parameter_list|)
block|{
name|iResourceType
operator|=
name|type
expr_stmt|;
block|}
specifier|public
name|Long
name|getId
parameter_list|()
block|{
return|return
name|iResourceId
return|;
block|}
specifier|public
name|void
name|setId
parameter_list|(
name|Long
name|id
parameter_list|)
block|{
name|iResourceId
operator|=
name|id
expr_stmt|;
block|}
specifier|public
name|String
name|getExternalId
parameter_list|()
block|{
return|return
name|iExternalId
return|;
block|}
specifier|public
name|void
name|setExternalId
parameter_list|(
name|String
name|id
parameter_list|)
block|{
name|iExternalId
operator|=
name|id
expr_stmt|;
block|}
specifier|public
name|boolean
name|hasAbbreviation
parameter_list|()
block|{
return|return
name|iAbbreviation
operator|!=
literal|null
operator|&&
operator|!
name|iAbbreviation
operator|.
name|isEmpty
argument_list|()
return|;
block|}
specifier|public
name|String
name|getAbbreviation
parameter_list|()
block|{
return|return
name|iAbbreviation
return|;
block|}
specifier|public
name|void
name|setAbbreviation
parameter_list|(
name|String
name|abbv
parameter_list|)
block|{
name|iAbbreviation
operator|=
name|abbv
expr_stmt|;
block|}
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
name|iResourceName
return|;
block|}
specifier|public
name|void
name|setName
parameter_list|(
name|String
name|name
parameter_list|)
block|{
name|iResourceName
operator|=
name|name
expr_stmt|;
block|}
specifier|public
name|Long
name|getSessionId
parameter_list|()
block|{
return|return
name|iSessionId
return|;
block|}
specifier|public
name|void
name|setSessionId
parameter_list|(
name|Long
name|sessionId
parameter_list|)
block|{
name|iSessionId
operator|=
name|sessionId
expr_stmt|;
block|}
specifier|public
name|String
name|getSessionName
parameter_list|()
block|{
return|return
name|iSessionName
return|;
block|}
specifier|public
name|void
name|setSessionName
parameter_list|(
name|String
name|sessionName
parameter_list|)
block|{
name|iSessionName
operator|=
name|sessionName
expr_stmt|;
block|}
specifier|public
name|String
name|getSessionAbbv
parameter_list|()
block|{
return|return
name|iSessionAbbv
return|;
block|}
specifier|public
name|void
name|setSessionAbbv
parameter_list|(
name|String
name|sessionAbbv
parameter_list|)
block|{
name|iSessionAbbv
operator|=
name|sessionAbbv
expr_stmt|;
block|}
specifier|public
name|boolean
name|hasCalendar
parameter_list|()
block|{
return|return
name|iCalendar
operator|!=
literal|null
operator|&&
operator|!
name|iCalendar
operator|.
name|isEmpty
argument_list|()
return|;
block|}
specifier|public
name|String
name|getCalendar
parameter_list|()
block|{
return|return
name|iCalendar
return|;
block|}
specifier|public
name|void
name|setCalendar
parameter_list|(
name|String
name|calendar
parameter_list|)
block|{
name|iCalendar
operator|=
name|calendar
expr_stmt|;
block|}
specifier|public
name|boolean
name|hasTitle
parameter_list|()
block|{
return|return
name|iTitle
operator|!=
literal|null
operator|&&
operator|!
name|iTitle
operator|.
name|isEmpty
argument_list|()
return|;
block|}
specifier|public
name|String
name|getTitle
parameter_list|()
block|{
return|return
name|iTitle
return|;
block|}
specifier|public
name|void
name|setTitle
parameter_list|(
name|String
name|title
parameter_list|)
block|{
name|iTitle
operator|=
name|title
expr_stmt|;
block|}
specifier|public
name|String
name|getHint
parameter_list|()
block|{
return|return
name|iHint
return|;
block|}
specifier|public
name|boolean
name|hasHint
parameter_list|()
block|{
return|return
name|iHint
operator|!=
literal|null
operator|&&
operator|!
name|iHint
operator|.
name|isEmpty
argument_list|()
return|;
block|}
specifier|public
name|void
name|setHint
parameter_list|(
name|String
name|hint
parameter_list|)
block|{
name|iHint
operator|=
name|hint
expr_stmt|;
block|}
specifier|public
name|String
name|getNameWithHint
parameter_list|()
block|{
if|if
condition|(
name|iResourceName
operator|==
literal|null
operator|||
name|iResourceName
operator|.
name|isEmpty
argument_list|()
condition|)
return|return
literal|""
return|;
if|if
condition|(
name|iHint
operator|==
literal|null
operator|||
name|iHint
operator|.
name|isEmpty
argument_list|()
condition|)
return|return
name|iResourceName
return|;
return|return
literal|"<span onmouseover=\"showGwtHint(this, '"
operator|+
name|iHint
operator|+
literal|"');\" onmouseout=\"hideGwtHint();\">"
operator|+
name|iResourceName
operator|+
literal|"</span>"
return|;
block|}
specifier|public
name|boolean
name|hasWeeks
parameter_list|()
block|{
return|return
name|iWeeks
operator|!=
literal|null
operator|&&
operator|!
name|iWeeks
operator|.
name|isEmpty
argument_list|()
return|;
block|}
specifier|public
name|List
argument_list|<
name|WeekInterface
argument_list|>
name|getWeeks
parameter_list|()
block|{
return|return
name|iWeeks
return|;
block|}
specifier|public
name|void
name|addWeek
parameter_list|(
name|WeekInterface
name|week
parameter_list|)
block|{
if|if
condition|(
name|iWeeks
operator|==
literal|null
condition|)
name|iWeeks
operator|=
operator|new
name|ArrayList
argument_list|<
name|WeekInterface
argument_list|>
argument_list|()
expr_stmt|;
name|iWeeks
operator|.
name|add
argument_list|(
name|week
argument_list|)
expr_stmt|;
block|}
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
name|getType
argument_list|()
operator|.
name|getLabel
argument_list|()
operator|+
literal|" "
operator|+
name|getName
argument_list|()
return|;
block|}
specifier|public
name|boolean
name|equals
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
name|ResourceInterface
operator|)
condition|)
return|return
literal|false
return|;
return|return
operator|(
operator|(
name|ResourceInterface
operator|)
name|o
operator|)
operator|.
name|getId
argument_list|()
operator|.
name|equals
argument_list|(
name|getId
argument_list|()
argument_list|)
return|;
block|}
specifier|public
name|int
name|hashCode
parameter_list|()
block|{
return|return
name|getId
argument_list|()
operator|.
name|hashCode
argument_list|()
return|;
block|}
specifier|public
name|int
name|compareTo
parameter_list|(
name|ResourceInterface
name|r
parameter_list|)
block|{
if|if
condition|(
name|hasAbbreviation
argument_list|()
condition|)
block|{
name|int
name|cmp
init|=
name|getAbbreviation
argument_list|()
operator|.
name|compareTo
argument_list|(
name|r
operator|.
name|getAbbreviation
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
name|int
name|cmp
init|=
name|getName
argument_list|()
operator|.
name|compareTo
argument_list|(
name|r
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
return|return
name|getId
argument_list|()
operator|.
name|compareTo
argument_list|(
name|r
operator|.
name|getId
argument_list|()
argument_list|)
return|;
block|}
block|}
specifier|public
specifier|static
class|class
name|WeekInterface
implements|implements
name|IsSerializable
block|{
specifier|private
name|int
name|iDayOfYear
decl_stmt|;
specifier|private
name|List
argument_list|<
name|String
argument_list|>
name|iDayNames
init|=
operator|new
name|ArrayList
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
specifier|public
name|WeekInterface
parameter_list|()
block|{
block|}
specifier|public
name|int
name|getDayOfYear
parameter_list|()
block|{
return|return
name|iDayOfYear
return|;
block|}
specifier|public
name|void
name|setDayOfYear
parameter_list|(
name|int
name|dayOfYear
parameter_list|)
block|{
name|iDayOfYear
operator|=
name|dayOfYear
expr_stmt|;
block|}
specifier|public
name|void
name|addDayName
parameter_list|(
name|String
name|name
parameter_list|)
block|{
name|iDayNames
operator|.
name|add
argument_list|(
name|name
argument_list|)
expr_stmt|;
block|}
specifier|public
name|List
argument_list|<
name|String
argument_list|>
name|getDayNames
parameter_list|()
block|{
return|return
name|iDayNames
return|;
block|}
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
name|getDayNames
argument_list|()
operator|.
name|get
argument_list|(
literal|0
argument_list|)
operator|+
literal|" - "
operator|+
name|getDayNames
argument_list|()
operator|.
name|get
argument_list|(
name|getDayNames
argument_list|()
operator|.
name|size
argument_list|()
operator|-
literal|1
argument_list|)
return|;
block|}
block|}
specifier|public
specifier|static
class|class
name|MeetingInterface
implements|implements
name|Comparable
argument_list|<
name|MeetingInterface
argument_list|>
implements|,
name|IsSerializable
block|{
specifier|private
name|ResourceInterface
name|iLocation
decl_stmt|;
specifier|private
name|Long
name|iMeetingId
decl_stmt|;
specifier|private
name|String
name|iMeetingTime
decl_stmt|;
specifier|private
name|String
name|iMeetingDate
decl_stmt|;
specifier|private
name|int
name|iStartSlot
decl_stmt|;
specifier|private
name|int
name|iEndSlot
decl_stmt|;
specifier|private
name|int
name|iDayOfWeek
decl_stmt|;
specifier|private
name|int
name|iDayOfYear
decl_stmt|;
specifier|private
name|boolean
name|iPast
decl_stmt|;
specifier|private
name|String
name|iApprovalDate
init|=
literal|null
decl_stmt|;
specifier|private
name|Long
name|iStartTime
decl_stmt|,
name|iStopTime
decl_stmt|;
specifier|public
name|MeetingInterface
parameter_list|()
block|{
block|}
specifier|public
name|Long
name|getId
parameter_list|()
block|{
return|return
name|iMeetingId
return|;
block|}
specifier|public
name|void
name|setId
parameter_list|(
name|Long
name|id
parameter_list|)
block|{
name|iMeetingId
operator|=
name|id
expr_stmt|;
block|}
specifier|public
name|String
name|getMeetingDate
parameter_list|()
block|{
return|return
name|iMeetingDate
return|;
block|}
specifier|public
name|void
name|setMeetingDate
parameter_list|(
name|String
name|date
parameter_list|)
block|{
name|iMeetingDate
operator|=
name|date
expr_stmt|;
block|}
specifier|public
name|int
name|getStartSlot
parameter_list|()
block|{
return|return
name|iStartSlot
return|;
block|}
specifier|public
name|void
name|setStartSlot
parameter_list|(
name|int
name|slot
parameter_list|)
block|{
name|iStartSlot
operator|=
name|slot
expr_stmt|;
block|}
specifier|public
name|int
name|getEndSlot
parameter_list|()
block|{
return|return
name|iEndSlot
return|;
block|}
specifier|public
name|void
name|setEndSlot
parameter_list|(
name|int
name|slot
parameter_list|)
block|{
name|iEndSlot
operator|=
name|slot
expr_stmt|;
block|}
specifier|public
name|int
name|getDayOfWeek
parameter_list|()
block|{
return|return
name|iDayOfWeek
return|;
block|}
specifier|public
name|void
name|setDayOfWeek
parameter_list|(
name|int
name|dayOfWeek
parameter_list|)
block|{
name|iDayOfWeek
operator|=
name|dayOfWeek
expr_stmt|;
block|}
specifier|public
name|int
name|getDayOfYear
parameter_list|()
block|{
return|return
name|iDayOfYear
return|;
block|}
specifier|public
name|void
name|setDayOfYear
parameter_list|(
name|int
name|dayOfYear
parameter_list|)
block|{
name|iDayOfYear
operator|=
name|dayOfYear
expr_stmt|;
block|}
specifier|public
name|String
name|getMeetingTime
parameter_list|()
block|{
return|return
name|iMeetingTime
return|;
block|}
specifier|public
name|void
name|setMeetingTime
parameter_list|(
name|String
name|time
parameter_list|)
block|{
name|iMeetingTime
operator|=
name|time
expr_stmt|;
block|}
specifier|public
name|ResourceInterface
name|getLocation
parameter_list|()
block|{
return|return
name|iLocation
return|;
block|}
specifier|public
name|String
name|getLocationName
parameter_list|()
block|{
return|return
operator|(
name|iLocation
operator|==
literal|null
condition|?
literal|""
else|:
name|iLocation
operator|.
name|getName
argument_list|()
operator|)
return|;
block|}
specifier|public
name|String
name|getLocationNameWithHint
parameter_list|()
block|{
return|return
operator|(
name|iLocation
operator|==
literal|null
condition|?
literal|""
else|:
name|iLocation
operator|.
name|getNameWithHint
argument_list|()
operator|)
return|;
block|}
specifier|public
name|void
name|setLocation
parameter_list|(
name|ResourceInterface
name|resource
parameter_list|)
block|{
name|iLocation
operator|=
name|resource
expr_stmt|;
block|}
specifier|public
name|boolean
name|isPast
parameter_list|()
block|{
return|return
name|iPast
return|;
block|}
specifier|public
name|void
name|setPast
parameter_list|(
name|boolean
name|past
parameter_list|)
block|{
name|iPast
operator|=
name|past
expr_stmt|;
block|}
specifier|public
name|boolean
name|isApproved
parameter_list|()
block|{
return|return
name|iApprovalDate
operator|!=
literal|null
return|;
block|}
specifier|public
name|String
name|getApprovalDate
parameter_list|()
block|{
return|return
name|iApprovalDate
return|;
block|}
specifier|public
name|void
name|setApprovalDate
parameter_list|(
name|String
name|date
parameter_list|)
block|{
name|iApprovalDate
operator|=
name|date
expr_stmt|;
block|}
specifier|public
name|Long
name|getStopTime
parameter_list|()
block|{
return|return
name|iStopTime
return|;
block|}
specifier|public
name|void
name|setStopTime
parameter_list|(
name|Long
name|stopTime
parameter_list|)
block|{
name|iStopTime
operator|=
name|stopTime
expr_stmt|;
block|}
specifier|public
name|Long
name|getStartTime
parameter_list|()
block|{
return|return
name|iStartTime
return|;
block|}
specifier|public
name|void
name|setStartTime
parameter_list|(
name|Long
name|startTime
parameter_list|)
block|{
name|iStartTime
operator|=
name|startTime
expr_stmt|;
block|}
specifier|public
name|int
name|compareTo
parameter_list|(
name|MeetingInterface
name|meeting
parameter_list|)
block|{
name|int
name|cmp
init|=
operator|new
name|Integer
argument_list|(
name|getDayOfYear
argument_list|()
argument_list|)
operator|.
name|compareTo
argument_list|(
name|meeting
operator|.
name|getDayOfYear
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
name|cmp
operator|=
name|getLocationName
argument_list|()
operator|.
name|compareTo
argument_list|(
name|meeting
operator|.
name|getLocationName
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
name|getId
argument_list|()
operator|.
name|compareTo
argument_list|(
name|meeting
operator|.
name|getId
argument_list|()
argument_list|)
return|;
block|}
specifier|public
name|int
name|hashCode
parameter_list|()
block|{
return|return
name|getId
argument_list|()
operator|.
name|hashCode
argument_list|()
return|;
block|}
specifier|public
name|boolean
name|equals
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
name|MeetingInterface
operator|)
condition|)
return|return
literal|false
return|;
return|return
name|getId
argument_list|()
operator|.
name|equals
argument_list|(
operator|(
operator|(
name|MeetingInterface
operator|)
name|o
operator|)
operator|.
name|getId
argument_list|()
argument_list|)
return|;
block|}
block|}
specifier|public
specifier|static
name|boolean
name|equals
parameter_list|(
name|Object
name|o1
parameter_list|,
name|Object
name|o2
parameter_list|)
block|{
return|return
operator|(
name|o1
operator|==
literal|null
condition|?
name|o2
operator|==
literal|null
else|:
name|o1
operator|.
name|equals
argument_list|(
name|o2
argument_list|)
operator|)
return|;
block|}
specifier|public
specifier|static
class|class
name|MultiMeetingInterface
implements|implements
name|Comparable
argument_list|<
name|MultiMeetingInterface
argument_list|>
implements|,
name|IsSerializable
block|{
specifier|private
name|TreeSet
argument_list|<
name|MeetingInterface
argument_list|>
name|iMeetings
decl_stmt|;
specifier|private
name|boolean
name|iPast
init|=
literal|false
decl_stmt|;
specifier|public
name|MultiMeetingInterface
parameter_list|(
name|TreeSet
argument_list|<
name|MeetingInterface
argument_list|>
name|meetings
parameter_list|,
name|boolean
name|past
parameter_list|)
block|{
name|iMeetings
operator|=
name|meetings
expr_stmt|;
name|iPast
operator|=
name|past
expr_stmt|;
block|}
specifier|public
name|boolean
name|isPast
parameter_list|()
block|{
return|return
name|iPast
return|;
block|}
specifier|public
name|TreeSet
argument_list|<
name|MeetingInterface
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
name|MultiMeetingInterface
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
operator|new
name|String
index|[]
block|{
literal|"Mon"
block|,
literal|"Tue"
block|,
literal|"Wed"
block|,
literal|"Thu"
block|,
literal|"Fri"
block|,
literal|"Sat"
block|,
literal|"Sun"
block|}
argument_list|,
operator|new
name|String
index|[]
block|{
literal|"M"
block|,
literal|"T"
block|,
literal|"W"
block|,
literal|"Th"
block|,
literal|"F"
block|,
literal|"S"
block|,
literal|"Su"
block|}
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
name|MeetingInterface
name|meeting
range|:
name|getMeetings
argument_list|()
control|)
block|{
name|int
name|dc
init|=
operator|(
literal|1
operator|<<
name|meeting
operator|.
name|getDayOfWeek
argument_list|()
operator|)
decl_stmt|;
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
literal|7
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
operator|(
literal|1
operator|<<
name|i
operator|)
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
name|getMeetingTime
parameter_list|()
block|{
return|return
name|getDays
argument_list|()
operator|+
literal|" "
operator|+
name|iMeetings
operator|.
name|first
argument_list|()
operator|.
name|getMeetingTime
argument_list|()
return|;
block|}
specifier|public
name|String
name|getMeetingDates
parameter_list|()
block|{
if|if
condition|(
name|iMeetings
operator|.
name|size
argument_list|()
operator|==
literal|1
condition|)
return|return
name|iMeetings
operator|.
name|first
argument_list|()
operator|.
name|getMeetingDate
argument_list|()
return|;
return|return
name|iMeetings
operator|.
name|first
argument_list|()
operator|.
name|getMeetingDate
argument_list|()
operator|+
literal|" - "
operator|+
name|iMeetings
operator|.
name|last
argument_list|()
operator|.
name|getMeetingDate
argument_list|()
return|;
block|}
specifier|public
name|String
name|getLocationName
parameter_list|()
block|{
return|return
name|iMeetings
operator|.
name|first
argument_list|()
operator|.
name|getLocationName
argument_list|()
return|;
block|}
specifier|public
name|String
name|getLocationNameWithHint
parameter_list|()
block|{
return|return
name|iMeetings
operator|.
name|first
argument_list|()
operator|.
name|getLocationNameWithHint
argument_list|()
return|;
block|}
block|}
specifier|public
specifier|static
name|TreeSet
argument_list|<
name|MultiMeetingInterface
argument_list|>
name|getMultiMeetings
parameter_list|(
name|Collection
argument_list|<
name|MeetingInterface
argument_list|>
name|meetings
parameter_list|,
name|boolean
name|checkApproval
parameter_list|,
name|boolean
name|checkPast
parameter_list|)
block|{
name|TreeSet
argument_list|<
name|MultiMeetingInterface
argument_list|>
name|ret
init|=
operator|new
name|TreeSet
argument_list|<
name|MultiMeetingInterface
argument_list|>
argument_list|()
decl_stmt|;
name|HashSet
argument_list|<
name|MeetingInterface
argument_list|>
name|meetingSet
init|=
operator|new
name|HashSet
argument_list|<
name|MeetingInterface
argument_list|>
argument_list|(
name|meetings
argument_list|)
decl_stmt|;
while|while
condition|(
operator|!
name|meetingSet
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|MeetingInterface
name|meeting
init|=
literal|null
decl_stmt|;
for|for
control|(
name|MeetingInterface
name|m
range|:
name|meetingSet
control|)
if|if
condition|(
name|meeting
operator|==
literal|null
operator|||
name|meeting
operator|.
name|compareTo
argument_list|(
name|m
argument_list|)
operator|>
literal|0
condition|)
name|meeting
operator|=
name|m
expr_stmt|;
name|meetingSet
operator|.
name|remove
argument_list|(
name|meeting
argument_list|)
expr_stmt|;
name|HashMap
argument_list|<
name|Integer
argument_list|,
name|MeetingInterface
argument_list|>
name|similar
init|=
operator|new
name|HashMap
argument_list|<
name|Integer
argument_list|,
name|MeetingInterface
argument_list|>
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
name|MeetingInterface
name|m
range|:
name|meetingSet
control|)
block|{
if|if
condition|(
name|m
operator|.
name|getMeetingTime
argument_list|()
operator|.
name|equals
argument_list|(
name|meeting
operator|.
name|getMeetingTime
argument_list|()
argument_list|)
operator|&&
name|m
operator|.
name|getLocationName
argument_list|()
operator|.
name|equals
argument_list|(
name|meeting
operator|.
name|getLocationName
argument_list|()
argument_list|)
operator|&&
operator|(
operator|!
name|checkPast
operator|||
name|m
operator|.
name|isPast
argument_list|()
operator|==
name|meeting
operator|.
name|isPast
argument_list|()
operator|)
operator|&&
operator|(
operator|!
name|checkApproval
operator|||
name|m
operator|.
name|isApproved
argument_list|()
operator|==
name|meeting
operator|.
name|isApproved
argument_list|()
operator|)
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
name|getDayOfYear
argument_list|()
argument_list|,
name|m
argument_list|)
expr_stmt|;
block|}
block|}
name|TreeSet
argument_list|<
name|MeetingInterface
argument_list|>
name|multi
init|=
operator|new
name|TreeSet
argument_list|<
name|MeetingInterface
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
name|int
name|w
init|=
name|meeting
operator|.
name|getDayOfWeek
argument_list|()
decl_stmt|;
name|int
name|y
init|=
name|meeting
operator|.
name|getDayOfYear
argument_list|()
decl_stmt|;
while|while
condition|(
literal|true
condition|)
block|{
do|do
block|{
name|y
operator|++
expr_stmt|;
name|w
operator|=
operator|(
name|w
operator|+
literal|1
operator|)
operator|%
literal|7
expr_stmt|;
block|}
do|while
condition|(
operator|!
name|dow
operator|.
name|contains
argument_list|(
name|w
argument_list|)
condition|)
do|;
name|MeetingInterface
name|m
init|=
name|similar
operator|.
name|get
argument_list|(
name|y
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
name|meetingSet
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
name|MultiMeetingInterface
argument_list|(
name|multi
argument_list|,
name|meeting
operator|.
name|isPast
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
return|return
name|ret
return|;
block|}
block|}
end_class

end_unit

