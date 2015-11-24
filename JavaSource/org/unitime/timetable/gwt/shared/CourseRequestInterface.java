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
name|gwt
operator|.
name|shared
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|Serializable
import|;
end_import

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
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|gwt
operator|.
name|resources
operator|.
name|StudentSectioningMessages
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
name|CourseRequestInterface
implements|implements
name|IsSerializable
implements|,
name|Serializable
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
name|Long
name|iSessionId
decl_stmt|,
name|iStudentId
decl_stmt|;
specifier|private
name|ArrayList
argument_list|<
name|Request
argument_list|>
name|iCourses
init|=
operator|new
name|ArrayList
argument_list|<
name|Request
argument_list|>
argument_list|()
decl_stmt|;
specifier|private
name|ArrayList
argument_list|<
name|Request
argument_list|>
name|iAlternatives
init|=
operator|new
name|ArrayList
argument_list|<
name|Request
argument_list|>
argument_list|()
decl_stmt|;
specifier|private
name|boolean
name|iSaved
init|=
literal|false
decl_stmt|;
specifier|private
name|boolean
name|iNoChange
init|=
literal|false
decl_stmt|;
specifier|private
name|Boolean
name|iUpdateLastRequest
init|=
literal|null
decl_stmt|;
specifier|public
name|CourseRequestInterface
parameter_list|()
block|{
block|}
specifier|public
name|Long
name|getAcademicSessionId
parameter_list|()
block|{
return|return
name|iSessionId
return|;
block|}
specifier|public
name|void
name|setAcademicSessionId
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
name|Long
name|getStudentId
parameter_list|()
block|{
return|return
name|iStudentId
return|;
block|}
specifier|public
name|void
name|setStudentId
parameter_list|(
name|Long
name|studentId
parameter_list|)
block|{
name|iStudentId
operator|=
name|studentId
expr_stmt|;
block|}
specifier|public
name|ArrayList
argument_list|<
name|Request
argument_list|>
name|getCourses
parameter_list|()
block|{
return|return
name|iCourses
return|;
block|}
specifier|public
name|ArrayList
argument_list|<
name|Request
argument_list|>
name|getAlternatives
parameter_list|()
block|{
return|return
name|iAlternatives
return|;
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
name|boolean
name|isNoChange
parameter_list|()
block|{
return|return
name|iNoChange
return|;
block|}
specifier|public
name|void
name|setNoChange
parameter_list|(
name|boolean
name|noChange
parameter_list|)
block|{
name|iNoChange
operator|=
name|noChange
expr_stmt|;
block|}
specifier|public
name|boolean
name|isUpdateLastRequest
parameter_list|()
block|{
return|return
name|iUpdateLastRequest
operator|==
literal|null
operator|||
name|iUpdateLastRequest
operator|.
name|booleanValue
argument_list|()
return|;
block|}
specifier|public
name|void
name|setUpdateLastRequest
parameter_list|(
name|boolean
name|updateLastRequest
parameter_list|)
block|{
name|iUpdateLastRequest
operator|=
name|updateLastRequest
expr_stmt|;
block|}
specifier|public
name|RequestPriority
name|getRequestPriority
parameter_list|(
name|String
name|course
parameter_list|)
block|{
if|if
condition|(
name|course
operator|==
literal|null
operator|||
name|course
operator|.
name|isEmpty
argument_list|()
condition|)
return|return
literal|null
return|;
name|int
name|priority
init|=
literal|1
decl_stmt|;
for|for
control|(
name|CourseRequestInterface
operator|.
name|Request
name|r
range|:
name|getCourses
argument_list|()
control|)
block|{
if|if
condition|(
name|course
operator|.
name|equalsIgnoreCase
argument_list|(
name|r
operator|.
name|getRequestedCourse
argument_list|()
argument_list|)
condition|)
return|return
operator|new
name|RequestPriority
argument_list|(
literal|false
argument_list|,
name|priority
argument_list|,
literal|0
argument_list|,
name|r
argument_list|)
return|;
if|if
condition|(
name|course
operator|.
name|equalsIgnoreCase
argument_list|(
name|r
operator|.
name|getFirstAlternative
argument_list|()
argument_list|)
condition|)
return|return
operator|new
name|RequestPriority
argument_list|(
literal|false
argument_list|,
name|priority
argument_list|,
literal|1
argument_list|,
name|r
argument_list|)
return|;
if|if
condition|(
name|course
operator|.
name|equalsIgnoreCase
argument_list|(
name|r
operator|.
name|getSecondAlternative
argument_list|()
argument_list|)
condition|)
return|return
operator|new
name|RequestPriority
argument_list|(
literal|false
argument_list|,
name|priority
argument_list|,
literal|2
argument_list|,
name|r
argument_list|)
return|;
name|priority
operator|++
expr_stmt|;
block|}
name|priority
operator|=
literal|1
expr_stmt|;
for|for
control|(
name|CourseRequestInterface
operator|.
name|Request
name|r
range|:
name|getAlternatives
argument_list|()
control|)
block|{
if|if
condition|(
name|course
operator|.
name|equalsIgnoreCase
argument_list|(
name|r
operator|.
name|getRequestedCourse
argument_list|()
argument_list|)
condition|)
return|return
operator|new
name|RequestPriority
argument_list|(
literal|true
argument_list|,
name|priority
argument_list|,
literal|0
argument_list|,
name|r
argument_list|)
return|;
if|if
condition|(
name|course
operator|.
name|equalsIgnoreCase
argument_list|(
name|r
operator|.
name|getFirstAlternative
argument_list|()
argument_list|)
condition|)
return|return
operator|new
name|RequestPriority
argument_list|(
literal|true
argument_list|,
name|priority
argument_list|,
literal|1
argument_list|,
name|r
argument_list|)
return|;
if|if
condition|(
name|course
operator|.
name|equalsIgnoreCase
argument_list|(
name|r
operator|.
name|getSecondAlternative
argument_list|()
argument_list|)
condition|)
return|return
operator|new
name|RequestPriority
argument_list|(
literal|true
argument_list|,
name|priority
argument_list|,
literal|2
argument_list|,
name|r
argument_list|)
return|;
name|priority
operator|++
expr_stmt|;
block|}
return|return
literal|null
return|;
block|}
specifier|public
specifier|static
class|class
name|FreeTime
implements|implements
name|IsSerializable
implements|,
name|Serializable
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
decl_stmt|;
specifier|private
name|int
name|iLength
decl_stmt|;
specifier|public
name|FreeTime
parameter_list|()
block|{
block|}
specifier|public
name|void
name|addDay
parameter_list|(
name|int
name|day
parameter_list|)
block|{
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
parameter_list|,
name|String
name|separator
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
operator|(
name|ret
operator|.
name|isEmpty
argument_list|()
condition|?
literal|""
else|:
name|separator
operator|)
operator|+
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
name|startSlot
parameter_list|)
block|{
name|iStart
operator|=
name|startSlot
expr_stmt|;
block|}
specifier|public
name|String
name|getStartString
parameter_list|(
name|boolean
name|useAmPm
parameter_list|)
block|{
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
if|if
condition|(
name|useAmPm
condition|)
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
else|else
return|return
name|h
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
parameter_list|(
name|boolean
name|useAmPm
parameter_list|)
block|{
name|int
name|h
init|=
operator|(
name|iStart
operator|+
name|iLength
operator|)
operator|/
literal|12
decl_stmt|;
name|int
name|m
init|=
literal|5
operator|*
operator|(
operator|(
name|iStart
operator|+
name|iLength
operator|)
operator|%
literal|12
operator|)
decl_stmt|;
if|if
condition|(
name|useAmPm
condition|)
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
else|else
return|return
name|h
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
return|;
block|}
specifier|public
name|String
name|toString
parameter_list|(
name|String
index|[]
name|shortDays
parameter_list|,
name|boolean
name|useAmPm
parameter_list|)
block|{
return|return
name|getDaysString
argument_list|(
name|shortDays
argument_list|,
literal|""
argument_list|)
operator|+
literal|" "
operator|+
name|getStartString
argument_list|(
name|useAmPm
argument_list|)
operator|+
literal|" - "
operator|+
name|getEndString
argument_list|(
name|useAmPm
argument_list|)
return|;
block|}
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
literal|"Free "
operator|+
name|toString
argument_list|(
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
literal|"R"
block|,
literal|"F"
block|,
literal|"S"
block|,
literal|"U"
block|}
argument_list|,
literal|true
argument_list|)
return|;
block|}
specifier|public
name|String
name|toAriaString
parameter_list|(
name|String
index|[]
name|longDays
parameter_list|,
name|boolean
name|useAmPm
parameter_list|)
block|{
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
name|String
name|ret
init|=
name|getDaysString
argument_list|(
name|longDays
argument_list|,
literal|" "
argument_list|)
operator|+
literal|" from "
decl_stmt|;
if|if
condition|(
name|useAmPm
condition|)
name|ret
operator|+=
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
operator|(
name|m
operator|==
literal|0
condition|?
literal|""
else|:
operator|(
name|m
operator|<
literal|10
condition|?
literal|" 0"
else|:
literal|" "
operator|)
operator|+
name|m
operator|)
operator|+
operator|(
name|h
operator|==
literal|24
condition|?
literal|" AM"
else|:
name|h
operator|>=
literal|12
condition|?
literal|" PM"
else|:
literal|" AM"
operator|)
expr_stmt|;
else|else
name|ret
operator|+=
name|h
operator|+
literal|" "
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
expr_stmt|;
name|h
operator|=
operator|(
name|iStart
operator|+
name|iLength
operator|)
operator|/
literal|12
expr_stmt|;
name|m
operator|=
literal|5
operator|*
operator|(
operator|(
name|iStart
operator|+
name|iLength
operator|)
operator|%
literal|12
operator|)
expr_stmt|;
name|ret
operator|+=
literal|" to "
expr_stmt|;
if|if
condition|(
name|useAmPm
condition|)
name|ret
operator|+=
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
operator|(
name|m
operator|==
literal|0
condition|?
literal|""
else|:
operator|(
name|m
operator|<
literal|10
condition|?
literal|" 0"
else|:
literal|" "
operator|)
operator|+
name|m
operator|)
operator|+
operator|(
name|h
operator|==
literal|24
condition|?
literal|" AM"
else|:
name|h
operator|>=
literal|12
condition|?
literal|" PM"
else|:
literal|" AM"
operator|)
expr_stmt|;
else|else
name|ret
operator|+=
name|h
operator|+
literal|" "
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
expr_stmt|;
return|return
name|ret
return|;
block|}
block|}
specifier|public
specifier|static
class|class
name|Request
implements|implements
name|IsSerializable
implements|,
name|Serializable
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
name|ArrayList
argument_list|<
name|FreeTime
argument_list|>
name|iRequestedFreeTime
init|=
literal|null
decl_stmt|;
specifier|private
name|String
name|iRequestedCourse
init|=
literal|null
decl_stmt|;
specifier|private
name|String
name|iFirstAlternative
init|=
literal|null
decl_stmt|;
specifier|private
name|String
name|iSecondAlternative
init|=
literal|null
decl_stmt|;
specifier|private
name|Boolean
name|iWaitList
init|=
literal|false
decl_stmt|;
specifier|private
name|Boolean
name|iReadOnly
init|=
literal|false
decl_stmt|;
specifier|public
name|Request
parameter_list|()
block|{
block|}
specifier|public
name|String
name|getRequestedCourse
parameter_list|()
block|{
return|return
name|iRequestedCourse
return|;
block|}
specifier|public
name|void
name|setRequestedCourse
parameter_list|(
name|String
name|requestedCourse
parameter_list|)
block|{
name|iRequestedCourse
operator|=
name|requestedCourse
expr_stmt|;
block|}
specifier|public
name|boolean
name|hasRequestedCourse
parameter_list|()
block|{
return|return
name|iRequestedCourse
operator|!=
literal|null
operator|&&
operator|!
name|iRequestedCourse
operator|.
name|isEmpty
argument_list|()
return|;
block|}
specifier|public
name|ArrayList
argument_list|<
name|FreeTime
argument_list|>
name|getRequestedFreeTime
parameter_list|()
block|{
return|return
name|iRequestedFreeTime
return|;
block|}
specifier|public
name|void
name|addRequestedFreeTime
parameter_list|(
name|FreeTime
name|ft
parameter_list|)
block|{
if|if
condition|(
name|iRequestedFreeTime
operator|==
literal|null
condition|)
name|iRequestedFreeTime
operator|=
operator|new
name|ArrayList
argument_list|<
name|FreeTime
argument_list|>
argument_list|()
expr_stmt|;
name|iRequestedFreeTime
operator|.
name|add
argument_list|(
name|ft
argument_list|)
expr_stmt|;
block|}
specifier|public
name|boolean
name|hasRequestedFreeTime
parameter_list|()
block|{
return|return
name|iRequestedFreeTime
operator|!=
literal|null
operator|&&
operator|!
name|iRequestedFreeTime
operator|.
name|isEmpty
argument_list|()
return|;
block|}
specifier|public
name|String
name|getFirstAlternative
parameter_list|()
block|{
return|return
name|iFirstAlternative
return|;
block|}
specifier|public
name|void
name|setFirstAlternative
parameter_list|(
name|String
name|firstAlternative
parameter_list|)
block|{
name|iFirstAlternative
operator|=
name|firstAlternative
expr_stmt|;
block|}
specifier|public
name|boolean
name|hasFirstAlternative
parameter_list|()
block|{
return|return
name|iFirstAlternative
operator|!=
literal|null
operator|&&
operator|!
name|iFirstAlternative
operator|.
name|isEmpty
argument_list|()
return|;
block|}
specifier|public
name|String
name|getSecondAlternative
parameter_list|()
block|{
return|return
name|iSecondAlternative
return|;
block|}
specifier|public
name|void
name|setSecondAlternative
parameter_list|(
name|String
name|secondAlternative
parameter_list|)
block|{
name|iSecondAlternative
operator|=
name|secondAlternative
expr_stmt|;
block|}
specifier|public
name|boolean
name|hasSecondAlternative
parameter_list|()
block|{
return|return
name|iSecondAlternative
operator|!=
literal|null
operator|&&
operator|!
name|iSecondAlternative
operator|.
name|isEmpty
argument_list|()
return|;
block|}
specifier|public
name|boolean
name|hasWaitList
parameter_list|()
block|{
return|return
name|iWaitList
operator|!=
literal|null
return|;
block|}
specifier|public
name|boolean
name|isWaitList
parameter_list|()
block|{
return|return
name|iWaitList
operator|!=
literal|null
operator|&&
name|iWaitList
operator|.
name|booleanValue
argument_list|()
return|;
block|}
specifier|public
name|void
name|setWaitList
parameter_list|(
name|Boolean
name|waitList
parameter_list|)
block|{
name|iWaitList
operator|=
name|waitList
expr_stmt|;
block|}
specifier|public
name|boolean
name|hasReadOnly
parameter_list|()
block|{
return|return
name|iReadOnly
operator|!=
literal|null
return|;
block|}
specifier|public
name|boolean
name|isReadOnly
parameter_list|()
block|{
return|return
name|iReadOnly
operator|!=
literal|null
operator|&&
name|iReadOnly
operator|.
name|booleanValue
argument_list|()
return|;
block|}
specifier|public
name|void
name|setReadOnly
parameter_list|(
name|boolean
name|readOnly
parameter_list|)
block|{
name|iReadOnly
operator|=
name|readOnly
expr_stmt|;
block|}
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
operator|(
name|hasRequestedFreeTime
argument_list|()
condition|?
name|iRequestedFreeTime
operator|.
name|toString
argument_list|()
else|:
name|hasRequestedCourse
argument_list|()
condition|?
name|iRequestedCourse
else|:
literal|"-"
operator|)
operator|+
operator|(
name|hasFirstAlternative
argument_list|()
condition|?
literal|", "
operator|+
name|iFirstAlternative
else|:
literal|""
operator|)
operator|+
operator|(
name|hasSecondAlternative
argument_list|()
condition|?
literal|", "
operator|+
name|iSecondAlternative
else|:
literal|""
operator|)
operator|+
operator|(
name|isWaitList
argument_list|()
condition|?
literal|" (w)"
else|:
literal|""
operator|)
return|;
block|}
block|}
specifier|public
name|String
name|toString
parameter_list|()
block|{
name|String
name|ret
init|=
literal|"CourseRequests(student = "
operator|+
name|iStudentId
operator|+
literal|", session = "
operator|+
name|iSessionId
operator|+
literal|", requests = {"
decl_stmt|;
name|int
name|idx
init|=
literal|1
decl_stmt|;
for|for
control|(
name|Request
name|r
range|:
name|iCourses
control|)
name|ret
operator|+=
literal|"\n   "
operator|+
operator|(
name|idx
operator|++
operator|)
operator|+
literal|". "
operator|+
name|r
expr_stmt|;
name|idx
operator|=
literal|1
expr_stmt|;
for|for
control|(
name|Request
name|r
range|:
name|iAlternatives
control|)
name|ret
operator|+=
literal|"\n  A"
operator|+
operator|(
name|idx
operator|++
operator|)
operator|+
literal|". "
operator|+
name|r
expr_stmt|;
return|return
name|ret
operator|+
literal|"\n})"
return|;
block|}
specifier|public
specifier|static
class|class
name|RequestPriority
implements|implements
name|IsSerializable
block|{
specifier|private
name|boolean
name|iAlternative
init|=
literal|false
decl_stmt|;
specifier|private
name|int
name|iPriority
init|=
literal|0
decl_stmt|;
specifier|private
name|int
name|iChoice
init|=
literal|0
decl_stmt|;
specifier|private
name|CourseRequestInterface
operator|.
name|Request
name|iRequest
decl_stmt|;
name|RequestPriority
parameter_list|(
name|boolean
name|alternative
parameter_list|,
name|int
name|priority
parameter_list|,
name|int
name|choice
parameter_list|,
name|CourseRequestInterface
operator|.
name|Request
name|request
parameter_list|)
block|{
name|iAlternative
operator|=
name|alternative
expr_stmt|;
name|iPriority
operator|=
name|priority
expr_stmt|;
name|iChoice
operator|=
name|choice
expr_stmt|;
name|iRequest
operator|=
name|request
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
name|int
name|getPriority
parameter_list|()
block|{
return|return
name|iPriority
return|;
block|}
specifier|public
name|int
name|getChoice
parameter_list|()
block|{
return|return
name|iChoice
return|;
block|}
specifier|public
name|CourseRequestInterface
operator|.
name|Request
name|getRequest
parameter_list|()
block|{
return|return
name|iRequest
return|;
block|}
specifier|public
name|String
name|toString
parameter_list|()
block|{
if|if
condition|(
name|iAlternative
condition|)
block|{
switch|switch
condition|(
name|iChoice
condition|)
block|{
case|case
literal|0
case|:
return|return
name|iPriority
operator|+
literal|"."
return|;
case|case
literal|1
case|:
return|return
name|iPriority
operator|+
literal|"A."
return|;
default|default:
return|return
name|iPriority
operator|+
literal|"B."
return|;
block|}
block|}
else|else
block|{
switch|switch
condition|(
name|iChoice
condition|)
block|{
case|case
literal|0
case|:
return|return
literal|"Alt "
operator|+
name|iPriority
operator|+
literal|"."
return|;
case|case
literal|1
case|:
return|return
literal|"Alt "
operator|+
name|iPriority
operator|+
literal|"A."
return|;
default|default:
return|return
literal|"Alt "
operator|+
name|iPriority
operator|+
literal|"B."
return|;
block|}
block|}
block|}
specifier|public
name|String
name|toString
parameter_list|(
name|StudentSectioningMessages
name|MESSAGES
parameter_list|)
block|{
if|if
condition|(
name|iAlternative
condition|)
block|{
switch|switch
condition|(
name|iChoice
condition|)
block|{
case|case
literal|0
case|:
return|return
name|MESSAGES
operator|.
name|degreeRequestedCourse
argument_list|(
name|iPriority
argument_list|)
return|;
case|case
literal|1
case|:
return|return
name|MESSAGES
operator|.
name|degreeRequestedCourseFirstAlt
argument_list|(
name|iPriority
argument_list|)
return|;
default|default:
return|return
name|MESSAGES
operator|.
name|degreeRequestedCourseSecondAlt
argument_list|(
name|iPriority
argument_list|)
return|;
block|}
block|}
else|else
block|{
switch|switch
condition|(
name|iChoice
condition|)
block|{
case|case
literal|0
case|:
return|return
name|MESSAGES
operator|.
name|degreeRequestedAlternative
argument_list|(
name|iPriority
argument_list|)
return|;
case|case
literal|1
case|:
return|return
name|MESSAGES
operator|.
name|degreeRequestedAlternativeFirstAlt
argument_list|(
name|iPriority
argument_list|)
return|;
default|default:
return|return
name|MESSAGES
operator|.
name|degreeRequestedAlternativeSecondAlt
argument_list|(
name|iPriority
argument_list|)
return|;
block|}
block|}
block|}
block|}
block|}
end_class

end_unit

