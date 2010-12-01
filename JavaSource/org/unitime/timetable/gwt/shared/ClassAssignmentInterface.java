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
name|ClassAssignmentInterface
implements|implements
name|IsSerializable
block|{
specifier|private
name|ArrayList
argument_list|<
name|CourseAssignment
argument_list|>
name|iAssignments
init|=
operator|new
name|ArrayList
argument_list|<
name|CourseAssignment
argument_list|>
argument_list|()
decl_stmt|;
specifier|private
name|ArrayList
argument_list|<
name|String
argument_list|>
name|iMessages
init|=
literal|null
decl_stmt|;
specifier|public
name|ClassAssignmentInterface
parameter_list|()
block|{
block|}
specifier|public
name|ArrayList
argument_list|<
name|CourseAssignment
argument_list|>
name|getCourseAssignments
parameter_list|()
block|{
return|return
name|iAssignments
return|;
block|}
specifier|public
name|void
name|add
parameter_list|(
name|CourseAssignment
name|a
parameter_list|)
block|{
name|iAssignments
operator|.
name|add
argument_list|(
name|a
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|clear
parameter_list|()
block|{
name|iAssignments
operator|.
name|clear
argument_list|()
expr_stmt|;
if|if
condition|(
name|iMessages
operator|!=
literal|null
condition|)
name|iMessages
operator|.
name|clear
argument_list|()
expr_stmt|;
block|}
specifier|public
name|void
name|addMessage
parameter_list|(
name|String
name|message
parameter_list|)
block|{
if|if
condition|(
name|iMessages
operator|==
literal|null
condition|)
name|iMessages
operator|=
operator|new
name|ArrayList
argument_list|<
name|String
argument_list|>
argument_list|()
expr_stmt|;
name|iMessages
operator|.
name|add
argument_list|(
name|message
argument_list|)
expr_stmt|;
block|}
specifier|public
name|boolean
name|hasMessages
parameter_list|()
block|{
return|return
name|iMessages
operator|!=
literal|null
operator|&&
operator|!
name|iMessages
operator|.
name|isEmpty
argument_list|()
return|;
block|}
specifier|public
name|ArrayList
argument_list|<
name|String
argument_list|>
name|getMessages
parameter_list|()
block|{
return|return
name|iMessages
return|;
block|}
specifier|public
name|String
name|getMessages
parameter_list|(
name|String
name|delim
parameter_list|)
block|{
name|String
name|ret
init|=
literal|""
decl_stmt|;
if|if
condition|(
name|iMessages
operator|==
literal|null
condition|)
return|return
name|ret
return|;
for|for
control|(
name|String
name|message
range|:
name|iMessages
control|)
block|{
if|if
condition|(
operator|!
name|ret
operator|.
name|isEmpty
argument_list|()
condition|)
name|ret
operator|+=
name|delim
expr_stmt|;
name|ret
operator|+=
name|message
expr_stmt|;
block|}
return|return
name|ret
return|;
block|}
specifier|public
specifier|static
class|class
name|CourseAssignment
implements|implements
name|IsSerializable
block|{
specifier|private
name|Long
name|iCourseId
init|=
literal|null
decl_stmt|;
specifier|private
name|boolean
name|iAssigned
init|=
literal|true
decl_stmt|;
specifier|private
name|String
name|iSubject
decl_stmt|,
name|iCourseNbr
decl_stmt|,
name|iTitle
decl_stmt|,
name|iNote
decl_stmt|;
specifier|private
name|boolean
name|iHasUniqueName
init|=
literal|true
decl_stmt|;
specifier|private
name|Integer
name|iLimit
init|=
literal|null
decl_stmt|,
name|iProjected
init|=
literal|null
decl_stmt|,
name|iEnrollment
init|=
literal|null
decl_stmt|,
name|iLastLike
init|=
literal|null
decl_stmt|;
specifier|private
name|ArrayList
argument_list|<
name|String
argument_list|>
name|iOverlaps
init|=
literal|null
decl_stmt|;
specifier|private
name|boolean
name|iNotAvailable
init|=
literal|false
decl_stmt|;
specifier|private
name|String
name|iInstead
decl_stmt|;
specifier|private
name|ArrayList
argument_list|<
name|ClassAssignment
argument_list|>
name|iAssignments
init|=
operator|new
name|ArrayList
argument_list|<
name|ClassAssignment
argument_list|>
argument_list|()
decl_stmt|;
specifier|public
name|Long
name|getCourseId
parameter_list|()
block|{
return|return
name|iCourseId
return|;
block|}
specifier|public
name|void
name|setCourseId
parameter_list|(
name|Long
name|courseId
parameter_list|)
block|{
name|iCourseId
operator|=
name|courseId
expr_stmt|;
block|}
specifier|public
name|boolean
name|isFreeTime
parameter_list|()
block|{
return|return
operator|(
name|iCourseId
operator|==
literal|null
operator|)
return|;
block|}
specifier|public
name|boolean
name|isAssigned
parameter_list|()
block|{
return|return
name|iAssigned
return|;
block|}
specifier|public
name|void
name|setAssigned
parameter_list|(
name|boolean
name|assigned
parameter_list|)
block|{
name|iAssigned
operator|=
name|assigned
expr_stmt|;
block|}
specifier|public
name|String
name|getSubject
parameter_list|()
block|{
return|return
name|iSubject
return|;
block|}
specifier|public
name|void
name|setSubject
parameter_list|(
name|String
name|subject
parameter_list|)
block|{
name|iSubject
operator|=
name|subject
expr_stmt|;
block|}
specifier|public
name|String
name|getCourseNbr
parameter_list|()
block|{
return|return
name|iCourseNbr
return|;
block|}
specifier|public
name|void
name|setCourseNbr
parameter_list|(
name|String
name|courseNbr
parameter_list|)
block|{
name|iCourseNbr
operator|=
name|courseNbr
expr_stmt|;
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
name|getNote
parameter_list|()
block|{
return|return
name|iNote
return|;
block|}
specifier|public
name|void
name|setNote
parameter_list|(
name|String
name|note
parameter_list|)
block|{
name|iNote
operator|=
name|note
expr_stmt|;
block|}
specifier|public
name|boolean
name|hasUniqueName
parameter_list|()
block|{
return|return
name|iHasUniqueName
return|;
block|}
specifier|public
name|void
name|setHasUniqueName
parameter_list|(
name|boolean
name|hasUniqueName
parameter_list|)
block|{
name|iHasUniqueName
operator|=
name|hasUniqueName
expr_stmt|;
block|}
specifier|public
name|void
name|addOverlap
parameter_list|(
name|String
name|overlap
parameter_list|)
block|{
if|if
condition|(
name|iOverlaps
operator|==
literal|null
condition|)
name|iOverlaps
operator|=
operator|new
name|ArrayList
argument_list|<
name|String
argument_list|>
argument_list|()
expr_stmt|;
name|iOverlaps
operator|.
name|add
argument_list|(
name|overlap
argument_list|)
expr_stmt|;
block|}
specifier|public
name|ArrayList
argument_list|<
name|String
argument_list|>
name|getOverlaps
parameter_list|()
block|{
return|return
name|iOverlaps
return|;
block|}
specifier|public
name|boolean
name|isNotAvailable
parameter_list|()
block|{
return|return
name|iNotAvailable
return|;
block|}
specifier|public
name|void
name|setNotAvailable
parameter_list|(
name|boolean
name|notAvailable
parameter_list|)
block|{
name|iNotAvailable
operator|=
name|notAvailable
expr_stmt|;
block|}
specifier|public
name|void
name|setInstead
parameter_list|(
name|String
name|instead
parameter_list|)
block|{
name|iInstead
operator|=
name|instead
expr_stmt|;
block|}
specifier|public
name|String
name|getInstead
parameter_list|()
block|{
return|return
name|iInstead
return|;
block|}
specifier|public
name|ArrayList
argument_list|<
name|ClassAssignment
argument_list|>
name|getClassAssignments
parameter_list|()
block|{
return|return
name|iAssignments
return|;
block|}
specifier|public
name|ClassAssignment
name|addClassAssignment
parameter_list|()
block|{
name|ClassAssignment
name|a
init|=
operator|new
name|ClassAssignment
argument_list|(
name|this
argument_list|)
decl_stmt|;
name|iAssignments
operator|.
name|add
argument_list|(
name|a
argument_list|)
expr_stmt|;
return|return
name|a
return|;
block|}
specifier|public
name|Integer
name|getLimit
parameter_list|()
block|{
return|return
name|iLimit
return|;
block|}
specifier|public
name|void
name|setLimit
parameter_list|(
name|Integer
name|limit
parameter_list|)
block|{
name|iLimit
operator|=
name|limit
expr_stmt|;
block|}
specifier|public
name|String
name|getLimitString
parameter_list|()
block|{
if|if
condition|(
name|iLimit
operator|==
literal|null
condition|)
return|return
literal|""
return|;
if|if
condition|(
name|iLimit
operator|<
literal|0
condition|)
return|return
literal|"&infin;"
return|;
return|return
name|iLimit
operator|.
name|toString
argument_list|()
return|;
block|}
specifier|public
name|Integer
name|getProjected
parameter_list|()
block|{
return|return
name|iProjected
return|;
block|}
specifier|public
name|void
name|setProjected
parameter_list|(
name|Integer
name|projected
parameter_list|)
block|{
name|iProjected
operator|=
name|projected
expr_stmt|;
block|}
specifier|public
name|String
name|getProjectedString
parameter_list|()
block|{
if|if
condition|(
name|iProjected
operator|==
literal|null
operator|||
name|iProjected
operator|==
literal|0
condition|)
return|return
literal|""
return|;
if|if
condition|(
name|iProjected
operator|<
literal|0
condition|)
return|return
literal|"&infin;"
return|;
return|return
name|iProjected
operator|.
name|toString
argument_list|()
return|;
block|}
specifier|public
name|Integer
name|getLastLike
parameter_list|()
block|{
return|return
name|iLastLike
return|;
block|}
specifier|public
name|void
name|setLastLike
parameter_list|(
name|Integer
name|lastLike
parameter_list|)
block|{
name|iLastLike
operator|=
name|lastLike
expr_stmt|;
block|}
specifier|public
name|String
name|getLastLikeString
parameter_list|()
block|{
if|if
condition|(
name|iLastLike
operator|==
literal|null
operator|||
name|iLastLike
operator|==
literal|0
condition|)
return|return
literal|""
return|;
if|if
condition|(
name|iLastLike
operator|<
literal|0
condition|)
return|return
literal|"&infin;"
return|;
return|return
name|iLastLike
operator|.
name|toString
argument_list|()
return|;
block|}
specifier|public
name|Integer
name|getEnrollment
parameter_list|()
block|{
return|return
name|iEnrollment
return|;
block|}
specifier|public
name|void
name|setEnrollment
parameter_list|(
name|Integer
name|enrollment
parameter_list|)
block|{
name|iEnrollment
operator|=
name|enrollment
expr_stmt|;
block|}
specifier|public
name|String
name|getEnrollmentString
parameter_list|()
block|{
if|if
condition|(
name|iEnrollment
operator|==
literal|null
operator|||
name|iEnrollment
operator|==
literal|0
condition|)
return|return
literal|""
return|;
if|if
condition|(
name|iEnrollment
operator|<
literal|0
condition|)
return|return
literal|"&infin;"
return|;
return|return
name|iEnrollment
operator|.
name|toString
argument_list|()
return|;
block|}
block|}
specifier|public
specifier|static
class|class
name|ClassAssignment
implements|implements
name|IsSerializable
block|{
specifier|private
name|boolean
name|iCourseAssigned
init|=
literal|true
decl_stmt|;
specifier|private
name|Long
name|iCourseId
decl_stmt|,
name|iClassId
decl_stmt|,
name|iSubpartId
decl_stmt|;
specifier|private
name|ArrayList
argument_list|<
name|Integer
argument_list|>
name|iDays
init|=
operator|new
name|ArrayList
argument_list|<
name|Integer
argument_list|>
argument_list|()
decl_stmt|;
specifier|private
name|int
name|iStart
decl_stmt|,
name|iLength
decl_stmt|,
name|iBreakTime
init|=
literal|0
decl_stmt|;
specifier|private
name|ArrayList
argument_list|<
name|String
argument_list|>
name|iInstructos
init|=
operator|new
name|ArrayList
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
specifier|private
name|ArrayList
argument_list|<
name|String
argument_list|>
name|iInstructoEmails
init|=
operator|new
name|ArrayList
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
specifier|private
name|ArrayList
argument_list|<
name|String
argument_list|>
name|iRooms
init|=
operator|new
name|ArrayList
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
specifier|private
name|boolean
name|iAlternative
init|=
literal|false
decl_stmt|,
name|iHasAlternatives
init|=
literal|true
decl_stmt|,
name|iDistanceConflict
init|=
literal|false
decl_stmt|;
specifier|private
name|String
name|iDatePattern
init|=
literal|null
decl_stmt|;
specifier|private
name|String
name|iSubject
decl_stmt|,
name|iCourseNbr
decl_stmt|,
name|iSubpart
decl_stmt|,
name|iSection
decl_stmt|,
name|iParentSection
decl_stmt|;
specifier|private
name|int
index|[]
name|iLimit
init|=
literal|null
decl_stmt|;
specifier|private
name|boolean
name|iPin
init|=
literal|false
decl_stmt|;
specifier|private
name|int
name|iBackToBackDistance
init|=
literal|0
decl_stmt|;
specifier|private
name|String
name|iBackToBackRooms
init|=
literal|null
decl_stmt|;
specifier|private
name|boolean
name|iSaved
init|=
literal|false
decl_stmt|;
specifier|private
name|Integer
name|iExpected
init|=
literal|null
decl_stmt|;
specifier|public
name|ClassAssignment
parameter_list|()
block|{
block|}
specifier|public
name|ClassAssignment
parameter_list|(
name|CourseAssignment
name|course
parameter_list|)
block|{
name|iCourseId
operator|=
name|course
operator|.
name|getCourseId
argument_list|()
expr_stmt|;
name|iSubject
operator|=
name|course
operator|.
name|getSubject
argument_list|()
expr_stmt|;
name|iCourseNbr
operator|=
name|course
operator|.
name|getCourseNbr
argument_list|()
expr_stmt|;
name|iCourseAssigned
operator|=
name|course
operator|.
name|isAssigned
argument_list|()
expr_stmt|;
block|}
specifier|public
name|Long
name|getCourseId
parameter_list|()
block|{
return|return
name|iCourseId
return|;
block|}
specifier|public
name|void
name|setCourseId
parameter_list|(
name|Long
name|courseId
parameter_list|)
block|{
name|iCourseId
operator|=
name|courseId
expr_stmt|;
block|}
specifier|public
name|boolean
name|isFreeTime
parameter_list|()
block|{
return|return
operator|(
name|iCourseId
operator|==
literal|null
operator|)
return|;
block|}
specifier|public
name|boolean
name|isCourseAssigned
parameter_list|()
block|{
return|return
name|iCourseAssigned
return|;
block|}
specifier|public
name|void
name|setCourseAssigned
parameter_list|(
name|boolean
name|courseAssigned
parameter_list|)
block|{
name|iCourseAssigned
operator|=
name|courseAssigned
expr_stmt|;
block|}
specifier|public
name|String
name|getSubject
parameter_list|()
block|{
return|return
name|iSubject
return|;
block|}
specifier|public
name|void
name|setSubject
parameter_list|(
name|String
name|subject
parameter_list|)
block|{
name|iSubject
operator|=
name|subject
expr_stmt|;
block|}
specifier|public
name|String
name|getCourseNbr
parameter_list|()
block|{
return|return
name|iCourseNbr
return|;
block|}
specifier|public
name|void
name|setCourseNbr
parameter_list|(
name|String
name|courseNbr
parameter_list|)
block|{
name|iCourseNbr
operator|=
name|courseNbr
expr_stmt|;
block|}
specifier|public
name|String
name|getSubpart
parameter_list|()
block|{
return|return
name|iSubpart
return|;
block|}
specifier|public
name|void
name|setSubpart
parameter_list|(
name|String
name|subpart
parameter_list|)
block|{
name|iSubpart
operator|=
name|subpart
expr_stmt|;
block|}
specifier|public
name|String
name|getSection
parameter_list|()
block|{
return|return
name|iSection
return|;
block|}
specifier|public
name|void
name|setSection
parameter_list|(
name|String
name|section
parameter_list|)
block|{
name|iSection
operator|=
name|section
expr_stmt|;
block|}
specifier|public
name|String
name|getParentSection
parameter_list|()
block|{
return|return
name|iParentSection
return|;
block|}
specifier|public
name|void
name|setParentSection
parameter_list|(
name|String
name|parentSection
parameter_list|)
block|{
name|iParentSection
operator|=
name|parentSection
expr_stmt|;
block|}
specifier|public
name|boolean
name|isAlternative
parameter_list|()
block|{
return|return
name|iAlternative
return|;
block|}
specifier|public
name|void
name|setAlternative
parameter_list|(
name|boolean
name|alternative
parameter_list|)
block|{
name|iAlternative
operator|=
name|alternative
expr_stmt|;
block|}
specifier|public
name|Long
name|getClassId
parameter_list|()
block|{
return|return
name|iClassId
return|;
block|}
specifier|public
name|void
name|setClassId
parameter_list|(
name|Long
name|classId
parameter_list|)
block|{
name|iClassId
operator|=
name|classId
expr_stmt|;
block|}
specifier|public
name|Long
name|getSubpartId
parameter_list|()
block|{
return|return
name|iSubpartId
return|;
block|}
specifier|public
name|void
name|setSubpartId
parameter_list|(
name|Long
name|subpartId
parameter_list|)
block|{
name|iSubpartId
operator|=
name|subpartId
expr_stmt|;
block|}
specifier|public
name|void
name|addDay
parameter_list|(
name|int
name|day
parameter_list|)
block|{
if|if
condition|(
name|iDays
operator|==
literal|null
condition|)
name|iDays
operator|=
operator|new
name|ArrayList
argument_list|<
name|Integer
argument_list|>
argument_list|()
expr_stmt|;
name|iDays
operator|.
name|add
argument_list|(
name|day
argument_list|)
expr_stmt|;
block|}
specifier|public
name|ArrayList
argument_list|<
name|Integer
argument_list|>
name|getDays
parameter_list|()
block|{
return|return
name|iDays
return|;
block|}
specifier|public
name|String
name|getDaysString
parameter_list|(
name|String
index|[]
name|shortDays
parameter_list|)
block|{
if|if
condition|(
name|iDays
operator|==
literal|null
condition|)
return|return
literal|""
return|;
name|String
name|ret
init|=
literal|""
decl_stmt|;
for|for
control|(
name|int
name|day
range|:
name|iDays
control|)
name|ret
operator|+=
name|shortDays
index|[
name|day
index|]
expr_stmt|;
return|return
name|ret
return|;
block|}
specifier|public
name|boolean
name|isAssigned
parameter_list|()
block|{
return|return
name|iDays
operator|!=
literal|null
operator|&&
operator|!
name|iDays
operator|.
name|isEmpty
argument_list|()
return|;
block|}
specifier|public
name|int
name|getStart
parameter_list|()
block|{
return|return
name|iStart
return|;
block|}
specifier|public
name|void
name|setStart
parameter_list|(
name|int
name|start
parameter_list|)
block|{
name|iStart
operator|=
name|start
expr_stmt|;
block|}
specifier|public
name|String
name|getStartString
parameter_list|()
block|{
if|if
condition|(
operator|!
name|isAssigned
argument_list|()
condition|)
return|return
literal|""
return|;
name|int
name|h
init|=
name|iStart
operator|/
literal|12
decl_stmt|;
name|int
name|m
init|=
literal|5
operator|*
operator|(
name|iStart
operator|%
literal|12
operator|)
decl_stmt|;
return|return
operator|(
name|h
operator|>
literal|12
condition|?
name|h
operator|-
literal|12
else|:
name|h
operator|)
operator|+
literal|":"
operator|+
operator|(
name|m
operator|<
literal|10
condition|?
literal|"0"
else|:
literal|""
operator|)
operator|+
name|m
operator|+
operator|(
name|h
operator|==
literal|24
condition|?
literal|"a"
else|:
name|h
operator|>=
literal|12
condition|?
literal|"p"
else|:
literal|"a"
operator|)
return|;
block|}
specifier|public
name|int
name|getLength
parameter_list|()
block|{
return|return
name|iLength
return|;
block|}
specifier|public
name|void
name|setLength
parameter_list|(
name|int
name|length
parameter_list|)
block|{
name|iLength
operator|=
name|length
expr_stmt|;
block|}
specifier|public
name|String
name|getEndString
parameter_list|()
block|{
if|if
condition|(
operator|!
name|isAssigned
argument_list|()
condition|)
return|return
literal|""
return|;
name|int
name|h
init|=
operator|(
literal|5
operator|*
operator|(
name|iStart
operator|+
name|iLength
operator|)
operator|-
name|iBreakTime
operator|)
operator|/
literal|60
decl_stmt|;
name|int
name|m
init|=
operator|(
literal|5
operator|*
operator|(
name|iStart
operator|+
name|iLength
operator|)
operator|-
name|iBreakTime
operator|)
operator|%
literal|60
decl_stmt|;
return|return
operator|(
name|h
operator|>
literal|12
condition|?
name|h
operator|-
literal|12
else|:
name|h
operator|)
operator|+
literal|":"
operator|+
operator|(
name|m
operator|<
literal|10
condition|?
literal|"0"
else|:
literal|""
operator|)
operator|+
name|m
operator|+
operator|(
name|h
operator|==
literal|24
condition|?
literal|"a"
else|:
name|h
operator|>=
literal|12
condition|?
literal|"p"
else|:
literal|"a"
operator|)
return|;
block|}
specifier|public
name|String
name|getTimeString
parameter_list|(
name|String
index|[]
name|shortDays
parameter_list|)
block|{
if|if
condition|(
operator|!
name|isAssigned
argument_list|()
condition|)
return|return
literal|""
return|;
return|return
name|getDaysString
argument_list|(
name|shortDays
argument_list|)
operator|+
literal|" "
operator|+
name|getStartString
argument_list|()
operator|+
literal|" - "
operator|+
name|getEndString
argument_list|()
return|;
block|}
specifier|public
name|int
name|getBreakTime
parameter_list|()
block|{
return|return
name|iBreakTime
return|;
block|}
specifier|public
name|void
name|setBreakTime
parameter_list|(
name|int
name|breakTime
parameter_list|)
block|{
name|iBreakTime
operator|=
name|breakTime
expr_stmt|;
block|}
specifier|public
name|boolean
name|hasDatePattern
parameter_list|()
block|{
return|return
name|iDatePattern
operator|!=
literal|null
operator|&&
operator|!
name|iDatePattern
operator|.
name|isEmpty
argument_list|()
return|;
block|}
specifier|public
name|String
name|getDatePattern
parameter_list|()
block|{
return|return
name|iDatePattern
return|;
block|}
specifier|public
name|void
name|setDatePattern
parameter_list|(
name|String
name|datePattern
parameter_list|)
block|{
name|iDatePattern
operator|=
name|datePattern
expr_stmt|;
block|}
specifier|public
name|boolean
name|hasInstructors
parameter_list|()
block|{
return|return
name|iInstructos
operator|!=
literal|null
operator|&&
operator|!
name|iInstructos
operator|.
name|isEmpty
argument_list|()
return|;
block|}
specifier|public
name|void
name|addInstructor
parameter_list|(
name|String
name|instructor
parameter_list|)
block|{
if|if
condition|(
name|iInstructos
operator|==
literal|null
condition|)
name|iInstructos
operator|=
operator|new
name|ArrayList
argument_list|<
name|String
argument_list|>
argument_list|()
expr_stmt|;
name|iInstructos
operator|.
name|add
argument_list|(
name|instructor
argument_list|)
expr_stmt|;
block|}
specifier|public
name|ArrayList
argument_list|<
name|String
argument_list|>
name|getInstructors
parameter_list|()
block|{
return|return
name|iInstructos
return|;
block|}
specifier|public
name|String
name|getInstructors
parameter_list|(
name|String
name|delim
parameter_list|)
block|{
if|if
condition|(
name|iInstructos
operator|==
literal|null
condition|)
return|return
literal|""
return|;
name|String
name|ret
init|=
literal|""
decl_stmt|;
for|for
control|(
name|String
name|instructor
range|:
name|iInstructos
control|)
block|{
if|if
condition|(
operator|!
name|ret
operator|.
name|isEmpty
argument_list|()
condition|)
name|ret
operator|+=
name|delim
expr_stmt|;
name|ret
operator|+=
name|instructor
expr_stmt|;
block|}
return|return
name|ret
return|;
block|}
specifier|public
name|String
name|getInstructorWithEmails
parameter_list|(
name|String
name|delim
parameter_list|)
block|{
if|if
condition|(
name|iInstructos
operator|==
literal|null
condition|)
return|return
literal|""
return|;
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
name|iInstructos
operator|.
name|size
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
if|if
condition|(
operator|!
name|ret
operator|.
name|isEmpty
argument_list|()
condition|)
name|ret
operator|+=
name|delim
expr_stmt|;
name|String
name|email
init|=
operator|(
name|iInstructoEmails
operator|!=
literal|null
operator|&&
name|i
operator|<
name|iInstructoEmails
operator|.
name|size
argument_list|()
condition|?
name|iInstructoEmails
operator|.
name|get
argument_list|(
name|i
argument_list|)
else|:
literal|null
operator|)
decl_stmt|;
if|if
condition|(
name|email
operator|!=
literal|null
operator|&&
operator|!
name|email
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|ret
operator|+=
literal|"<A class=\"unitime-SimpleLink\" href=\"mailto:"
operator|+
name|email
operator|+
literal|"\">"
operator|+
name|iInstructos
operator|.
name|get
argument_list|(
name|i
argument_list|)
operator|+
literal|"</A>"
expr_stmt|;
block|}
else|else
name|ret
operator|+=
name|iInstructos
operator|.
name|get
argument_list|(
name|i
argument_list|)
expr_stmt|;
block|}
return|return
name|ret
return|;
block|}
specifier|public
name|boolean
name|hasInstructorEmails
parameter_list|()
block|{
return|return
name|iInstructoEmails
operator|!=
literal|null
operator|&&
operator|!
name|iInstructoEmails
operator|.
name|isEmpty
argument_list|()
return|;
block|}
specifier|public
name|void
name|addInstructoEmailr
parameter_list|(
name|String
name|instructorEmail
parameter_list|)
block|{
if|if
condition|(
name|iInstructoEmails
operator|==
literal|null
condition|)
name|iInstructoEmails
operator|=
operator|new
name|ArrayList
argument_list|<
name|String
argument_list|>
argument_list|()
expr_stmt|;
name|iInstructoEmails
operator|.
name|add
argument_list|(
name|instructorEmail
argument_list|)
expr_stmt|;
block|}
specifier|public
name|ArrayList
argument_list|<
name|String
argument_list|>
name|getInstructorEmails
parameter_list|()
block|{
return|return
name|iInstructoEmails
return|;
block|}
specifier|public
name|boolean
name|hasRoom
parameter_list|()
block|{
return|return
name|iRooms
operator|!=
literal|null
operator|&&
operator|!
name|iRooms
operator|.
name|isEmpty
argument_list|()
return|;
block|}
specifier|public
name|void
name|addRoom
parameter_list|(
name|String
name|room
parameter_list|)
block|{
if|if
condition|(
name|iRooms
operator|==
literal|null
condition|)
name|iRooms
operator|=
operator|new
name|ArrayList
argument_list|<
name|String
argument_list|>
argument_list|()
expr_stmt|;
name|iRooms
operator|.
name|add
argument_list|(
name|room
argument_list|)
expr_stmt|;
block|}
specifier|public
name|ArrayList
argument_list|<
name|String
argument_list|>
name|getRooms
parameter_list|()
block|{
return|return
name|iRooms
return|;
block|}
specifier|public
name|String
name|getRooms
parameter_list|(
name|String
name|delim
parameter_list|)
block|{
if|if
condition|(
name|iRooms
operator|==
literal|null
condition|)
return|return
literal|""
return|;
name|String
name|ret
init|=
literal|""
decl_stmt|;
for|for
control|(
name|String
name|room
range|:
name|iRooms
control|)
block|{
if|if
condition|(
operator|!
name|ret
operator|.
name|isEmpty
argument_list|()
condition|)
name|ret
operator|+=
name|delim
expr_stmt|;
name|ret
operator|+=
name|room
expr_stmt|;
block|}
return|return
name|ret
return|;
block|}
specifier|public
name|boolean
name|isUnlimited
parameter_list|()
block|{
return|return
name|iLimit
operator|!=
literal|null
operator|&&
name|iLimit
index|[
literal|1
index|]
operator|>=
literal|9999
return|;
block|}
specifier|public
name|int
index|[]
name|getLimit
parameter_list|()
block|{
return|return
name|iLimit
return|;
block|}
specifier|public
name|void
name|setLimit
parameter_list|(
name|int
index|[]
name|limit
parameter_list|)
block|{
name|iLimit
operator|=
name|limit
expr_stmt|;
block|}
specifier|public
name|String
name|getLimitString
parameter_list|()
block|{
if|if
condition|(
name|iLimit
operator|==
literal|null
condition|)
return|return
literal|""
return|;
if|if
condition|(
name|iLimit
index|[
literal|1
index|]
operator|>=
literal|9999
operator|||
name|iLimit
index|[
literal|1
index|]
operator|<
literal|0
condition|)
return|return
literal|"&infin;"
return|;
if|if
condition|(
name|iLimit
index|[
literal|0
index|]
operator|<
literal|0
condition|)
return|return
name|String
operator|.
name|valueOf
argument_list|(
name|iLimit
index|[
literal|1
index|]
argument_list|)
return|;
return|return
operator|(
name|iLimit
index|[
literal|1
index|]
operator|-
name|iLimit
index|[
literal|0
index|]
operator|)
operator|+
literal|" / "
operator|+
name|iLimit
index|[
literal|1
index|]
return|;
block|}
specifier|public
name|boolean
name|isAvailable
parameter_list|()
block|{
if|if
condition|(
name|iLimit
operator|==
literal|null
condition|)
return|return
literal|true
return|;
if|if
condition|(
name|iLimit
index|[
literal|0
index|]
operator|<
literal|0
condition|)
return|return
operator|(
name|iLimit
index|[
literal|1
index|]
operator|==
literal|0
operator|)
return|;
return|return
name|iLimit
index|[
literal|0
index|]
operator|<
name|iLimit
index|[
literal|1
index|]
return|;
block|}
specifier|public
name|int
name|getAvailableLimit
parameter_list|()
block|{
if|if
condition|(
name|iLimit
operator|==
literal|null
condition|)
return|return
literal|9999
return|;
if|if
condition|(
name|iLimit
index|[
literal|0
index|]
operator|<
literal|0
condition|)
return|return
literal|9999
return|;
return|return
name|iLimit
index|[
literal|1
index|]
operator|-
name|iLimit
index|[
literal|0
index|]
return|;
block|}
specifier|public
name|boolean
name|isPinned
parameter_list|()
block|{
return|return
name|iPin
return|;
block|}
specifier|public
name|void
name|setPinned
parameter_list|(
name|boolean
name|pin
parameter_list|)
block|{
name|iPin
operator|=
name|pin
expr_stmt|;
block|}
specifier|public
name|boolean
name|hasAlternatives
parameter_list|()
block|{
return|return
name|iHasAlternatives
return|;
block|}
specifier|public
name|void
name|setHasAlternatives
parameter_list|(
name|boolean
name|alternatives
parameter_list|)
block|{
name|iHasAlternatives
operator|=
name|alternatives
expr_stmt|;
block|}
specifier|public
name|boolean
name|hasDistanceConflict
parameter_list|()
block|{
return|return
name|iDistanceConflict
return|;
block|}
specifier|public
name|void
name|setDistanceConflict
parameter_list|(
name|boolean
name|distanceConflict
parameter_list|)
block|{
name|iDistanceConflict
operator|=
name|distanceConflict
expr_stmt|;
block|}
specifier|public
name|int
name|getBackToBackDistance
parameter_list|()
block|{
return|return
name|iBackToBackDistance
return|;
block|}
specifier|public
name|void
name|setBackToBackDistance
parameter_list|(
name|int
name|backToBackDistance
parameter_list|)
block|{
name|iBackToBackDistance
operator|=
name|backToBackDistance
expr_stmt|;
block|}
specifier|public
name|String
name|getBackToBackRooms
parameter_list|()
block|{
return|return
name|iBackToBackRooms
return|;
block|}
specifier|public
name|void
name|setBackToBackRooms
parameter_list|(
name|String
name|backToBackRooms
parameter_list|)
block|{
name|iBackToBackRooms
operator|=
name|backToBackRooms
expr_stmt|;
block|}
specifier|public
name|boolean
name|isSaved
parameter_list|()
block|{
return|return
name|iSaved
return|;
block|}
specifier|public
name|void
name|setSaved
parameter_list|(
name|boolean
name|saved
parameter_list|)
block|{
name|iSaved
operator|=
name|saved
expr_stmt|;
block|}
specifier|public
name|void
name|setExpected
parameter_list|(
name|int
name|expected
parameter_list|)
block|{
name|iExpected
operator|=
name|expected
expr_stmt|;
block|}
specifier|public
name|void
name|setExpected
parameter_list|(
name|double
name|expected
parameter_list|)
block|{
name|iExpected
operator|=
operator|(
name|int
operator|)
name|Math
operator|.
name|ceil
argument_list|(
name|expected
argument_list|)
expr_stmt|;
block|}
specifier|public
name|boolean
name|hasExpected
parameter_list|()
block|{
return|return
name|iExpected
operator|!=
literal|null
return|;
block|}
specifier|public
name|int
name|getExpected
parameter_list|()
block|{
return|return
operator|(
name|iExpected
operator|==
literal|null
condition|?
literal|0
else|:
name|iExpected
operator|)
return|;
block|}
specifier|public
name|boolean
name|isOfHighDemand
parameter_list|()
block|{
return|return
name|isAvailable
argument_list|()
operator|&&
operator|!
name|isUnlimited
argument_list|()
operator|&&
name|hasExpected
argument_list|()
operator|&&
name|getExpected
argument_list|()
operator|>
name|getAvailableLimit
argument_list|()
return|;
block|}
block|}
block|}
end_class

end_unit

