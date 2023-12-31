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
name|client
operator|.
name|widgets
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
name|Map
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
name|StudentSectioningConstants
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
name|FreeTime
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
name|core
operator|.
name|client
operator|.
name|GWT
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
name|AsyncCallback
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|FreeTimeParser
implements|implements
name|DataProvider
argument_list|<
name|String
argument_list|,
name|List
argument_list|<
name|CourseRequestInterface
operator|.
name|FreeTime
argument_list|>
argument_list|>
block|{
specifier|protected
specifier|static
specifier|final
name|StudentSectioningConstants
name|CONSTANTS
init|=
name|GWT
operator|.
name|create
argument_list|(
name|StudentSectioningConstants
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|protected
specifier|static
specifier|final
name|StudentSectioningMessages
name|MESSAGES
init|=
name|GWT
operator|.
name|create
argument_list|(
name|StudentSectioningMessages
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|private
name|Map
argument_list|<
name|String
argument_list|,
name|?
argument_list|>
name|iValidCourseNames
init|=
literal|null
decl_stmt|;
annotation|@
name|Override
specifier|public
name|void
name|getData
parameter_list|(
name|String
name|source
parameter_list|,
name|AsyncCallback
argument_list|<
name|List
argument_list|<
name|FreeTime
argument_list|>
argument_list|>
name|callback
parameter_list|)
block|{
try|try
block|{
name|callback
operator|.
name|onSuccess
argument_list|(
name|parseFreeTime
argument_list|(
name|source
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IllegalArgumentException
name|e
parameter_list|)
block|{
name|callback
operator|.
name|onFailure
argument_list|(
name|e
argument_list|)
expr_stmt|;
block|}
block|}
specifier|public
name|void
name|setValidCourseNames
parameter_list|(
name|Map
argument_list|<
name|String
argument_list|,
name|?
argument_list|>
name|validCourseNames
parameter_list|)
block|{
name|iValidCourseNames
operator|=
name|validCourseNames
expr_stmt|;
block|}
specifier|public
name|ArrayList
argument_list|<
name|CourseRequestInterface
operator|.
name|FreeTime
argument_list|>
name|parseFreeTime
parameter_list|(
name|String
name|text
parameter_list|)
throws|throws
name|IllegalArgumentException
block|{
if|if
condition|(
name|iValidCourseNames
operator|!=
literal|null
operator|&&
name|iValidCourseNames
operator|.
name|containsKey
argument_list|(
name|text
operator|.
name|toLowerCase
argument_list|()
argument_list|)
condition|)
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
name|MESSAGES
operator|.
name|notFreeTimeIsCourse
argument_list|(
name|text
argument_list|)
argument_list|)
throw|;
if|if
condition|(
name|text
operator|.
name|isEmpty
argument_list|()
condition|)
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
name|MESSAGES
operator|.
name|courseSelectionNoFreeTime
argument_list|()
argument_list|)
throw|;
for|for
control|(
name|String
name|dnp
range|:
name|CONSTANTS
operator|.
name|freeTimeDoNotParse
argument_list|()
control|)
if|if
condition|(
name|text
operator|.
name|matches
argument_list|(
name|dnp
argument_list|)
condition|)
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
name|MESSAGES
operator|.
name|courseSelectionNoFreeTime
argument_list|()
argument_list|)
throw|;
name|ArrayList
argument_list|<
name|CourseRequestInterface
operator|.
name|FreeTime
argument_list|>
name|ret
init|=
operator|new
name|ArrayList
argument_list|<
name|CourseRequestInterface
operator|.
name|FreeTime
argument_list|>
argument_list|()
decl_stmt|;
name|ArrayList
argument_list|<
name|Integer
argument_list|>
name|lastDays
init|=
operator|new
name|ArrayList
argument_list|<
name|Integer
argument_list|>
argument_list|()
decl_stmt|;
name|String
name|tokens
index|[]
init|=
name|text
operator|.
name|split
argument_list|(
literal|"[,;]"
argument_list|)
decl_stmt|;
for|for
control|(
name|String
name|token
range|:
name|tokens
control|)
block|{
name|String
name|original
init|=
name|token
decl_stmt|;
if|if
condition|(
name|token
operator|.
name|toLowerCase
argument_list|()
operator|.
name|startsWith
argument_list|(
name|CONSTANTS
operator|.
name|freePrefix
argument_list|()
operator|.
name|toLowerCase
argument_list|()
argument_list|)
condition|)
name|token
operator|=
name|token
operator|.
name|substring
argument_list|(
name|CONSTANTS
operator|.
name|freePrefix
argument_list|()
operator|.
name|length
argument_list|()
argument_list|)
expr_stmt|;
name|ArrayList
argument_list|<
name|Integer
argument_list|>
name|days
init|=
operator|new
name|ArrayList
argument_list|<
name|Integer
argument_list|>
argument_list|()
decl_stmt|;
while|while
condition|(
name|token
operator|.
name|startsWith
argument_list|(
literal|" "
argument_list|)
condition|)
name|token
operator|=
name|token
operator|.
name|substring
argument_list|(
literal|1
argument_list|)
expr_stmt|;
name|boolean
name|found
init|=
literal|false
decl_stmt|;
do|do
block|{
name|found
operator|=
literal|false
expr_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|CONSTANTS
operator|.
name|longDays
argument_list|()
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
if|if
condition|(
name|token
operator|.
name|toLowerCase
argument_list|()
operator|.
name|startsWith
argument_list|(
name|CONSTANTS
operator|.
name|longDays
argument_list|()
index|[
name|i
index|]
operator|.
name|toLowerCase
argument_list|()
argument_list|)
condition|)
block|{
name|days
operator|.
name|add
argument_list|(
name|i
argument_list|)
expr_stmt|;
name|token
operator|=
name|token
operator|.
name|substring
argument_list|(
name|CONSTANTS
operator|.
name|longDays
argument_list|()
index|[
name|i
index|]
operator|.
name|length
argument_list|()
argument_list|)
expr_stmt|;
while|while
condition|(
name|token
operator|.
name|startsWith
argument_list|(
literal|" "
argument_list|)
condition|)
name|token
operator|=
name|token
operator|.
name|substring
argument_list|(
literal|1
argument_list|)
expr_stmt|;
name|found
operator|=
literal|true
expr_stmt|;
block|}
block|}
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|CONSTANTS
operator|.
name|days
argument_list|()
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
if|if
condition|(
name|token
operator|.
name|toLowerCase
argument_list|()
operator|.
name|startsWith
argument_list|(
name|CONSTANTS
operator|.
name|days
argument_list|()
index|[
name|i
index|]
operator|.
name|toLowerCase
argument_list|()
argument_list|)
condition|)
block|{
name|days
operator|.
name|add
argument_list|(
name|i
argument_list|)
expr_stmt|;
name|token
operator|=
name|token
operator|.
name|substring
argument_list|(
name|CONSTANTS
operator|.
name|days
argument_list|()
index|[
name|i
index|]
operator|.
name|length
argument_list|()
argument_list|)
expr_stmt|;
while|while
condition|(
name|token
operator|.
name|startsWith
argument_list|(
literal|" "
argument_list|)
condition|)
name|token
operator|=
name|token
operator|.
name|substring
argument_list|(
literal|1
argument_list|)
expr_stmt|;
name|found
operator|=
literal|true
expr_stmt|;
block|}
block|}
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|CONSTANTS
operator|.
name|days
argument_list|()
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
if|if
condition|(
name|token
operator|.
name|toLowerCase
argument_list|()
operator|.
name|startsWith
argument_list|(
name|CONSTANTS
operator|.
name|days
argument_list|()
index|[
name|i
index|]
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
literal|2
argument_list|)
operator|.
name|toLowerCase
argument_list|()
argument_list|)
condition|)
block|{
name|boolean
name|first
init|=
literal|false
decl_stmt|,
name|second
init|=
literal|false
decl_stmt|;
for|for
control|(
name|String
name|d
range|:
name|CONSTANTS
operator|.
name|shortDays
argument_list|()
control|)
block|{
if|if
condition|(
name|token
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
literal|1
argument_list|)
operator|.
name|equalsIgnoreCase
argument_list|(
name|d
argument_list|)
condition|)
name|first
operator|=
literal|true
expr_stmt|;
if|if
condition|(
name|token
operator|.
name|substring
argument_list|(
literal|1
argument_list|,
literal|2
argument_list|)
operator|.
name|equalsIgnoreCase
argument_list|(
name|d
argument_list|)
condition|)
name|second
operator|=
literal|true
expr_stmt|;
block|}
for|for
control|(
name|String
name|d
range|:
name|CONSTANTS
operator|.
name|freeTimeShortDays
argument_list|()
control|)
block|{
if|if
condition|(
name|token
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
literal|1
argument_list|)
operator|.
name|equalsIgnoreCase
argument_list|(
name|d
argument_list|)
condition|)
name|first
operator|=
literal|true
expr_stmt|;
if|if
condition|(
name|token
operator|.
name|substring
argument_list|(
literal|1
argument_list|,
literal|2
argument_list|)
operator|.
name|equalsIgnoreCase
argument_list|(
name|d
argument_list|)
condition|)
name|second
operator|=
literal|true
expr_stmt|;
block|}
if|if
condition|(
operator|!
name|first
operator|||
operator|!
name|second
condition|)
block|{
name|days
operator|.
name|add
argument_list|(
name|i
argument_list|)
expr_stmt|;
name|token
operator|=
name|token
operator|.
name|substring
argument_list|(
literal|2
argument_list|)
expr_stmt|;
while|while
condition|(
name|token
operator|.
name|startsWith
argument_list|(
literal|" "
argument_list|)
condition|)
name|token
operator|=
name|token
operator|.
name|substring
argument_list|(
literal|1
argument_list|)
expr_stmt|;
name|found
operator|=
literal|true
expr_stmt|;
block|}
block|}
block|}
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|CONSTANTS
operator|.
name|shortDays
argument_list|()
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
if|if
condition|(
name|token
operator|.
name|toLowerCase
argument_list|()
operator|.
name|startsWith
argument_list|(
name|CONSTANTS
operator|.
name|shortDays
argument_list|()
index|[
name|i
index|]
operator|.
name|toLowerCase
argument_list|()
argument_list|)
condition|)
block|{
name|days
operator|.
name|add
argument_list|(
name|i
argument_list|)
expr_stmt|;
name|token
operator|=
name|token
operator|.
name|substring
argument_list|(
name|CONSTANTS
operator|.
name|shortDays
argument_list|()
index|[
name|i
index|]
operator|.
name|length
argument_list|()
argument_list|)
expr_stmt|;
while|while
condition|(
name|token
operator|.
name|startsWith
argument_list|(
literal|" "
argument_list|)
condition|)
name|token
operator|=
name|token
operator|.
name|substring
argument_list|(
literal|1
argument_list|)
expr_stmt|;
name|found
operator|=
literal|true
expr_stmt|;
block|}
block|}
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|CONSTANTS
operator|.
name|freeTimeShortDays
argument_list|()
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
if|if
condition|(
name|token
operator|.
name|toLowerCase
argument_list|()
operator|.
name|startsWith
argument_list|(
name|CONSTANTS
operator|.
name|freeTimeShortDays
argument_list|()
index|[
name|i
index|]
operator|.
name|toLowerCase
argument_list|()
argument_list|)
condition|)
block|{
name|days
operator|.
name|add
argument_list|(
name|i
argument_list|)
expr_stmt|;
name|token
operator|=
name|token
operator|.
name|substring
argument_list|(
name|CONSTANTS
operator|.
name|freeTimeShortDays
argument_list|()
index|[
name|i
index|]
operator|.
name|length
argument_list|()
argument_list|)
expr_stmt|;
while|while
condition|(
name|token
operator|.
name|startsWith
argument_list|(
literal|" "
argument_list|)
condition|)
name|token
operator|=
name|token
operator|.
name|substring
argument_list|(
literal|1
argument_list|)
expr_stmt|;
name|found
operator|=
literal|true
expr_stmt|;
block|}
block|}
block|}
do|while
condition|(
name|found
condition|)
do|;
name|int
name|startHour
init|=
literal|0
decl_stmt|,
name|startMin
init|=
literal|0
decl_stmt|;
while|while
condition|(
name|token
operator|.
name|startsWith
argument_list|(
literal|" "
argument_list|)
condition|)
name|token
operator|=
name|token
operator|.
name|substring
argument_list|(
literal|1
argument_list|)
expr_stmt|;
name|String
name|number
init|=
literal|""
decl_stmt|;
while|while
condition|(
operator|!
name|token
operator|.
name|isEmpty
argument_list|()
operator|&&
name|token
operator|.
name|charAt
argument_list|(
literal|0
argument_list|)
operator|>=
literal|'0'
operator|&&
name|token
operator|.
name|charAt
argument_list|(
literal|0
argument_list|)
operator|<=
literal|'9'
condition|)
block|{
name|number
operator|+=
name|token
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
literal|1
argument_list|)
expr_stmt|;
name|token
operator|=
name|token
operator|.
name|substring
argument_list|(
literal|1
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|number
operator|.
name|isEmpty
argument_list|()
condition|)
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
name|MESSAGES
operator|.
name|invalidFreeTimeExpectedDayOrNumber
argument_list|(
name|original
argument_list|,
literal|1
operator|+
name|original
operator|.
name|lastIndexOf
argument_list|(
name|token
argument_list|)
argument_list|)
argument_list|)
throw|;
if|if
condition|(
name|number
operator|.
name|length
argument_list|()
operator|>
literal|2
condition|)
block|{
name|startHour
operator|=
name|Integer
operator|.
name|parseInt
argument_list|(
name|number
argument_list|)
operator|/
literal|100
expr_stmt|;
name|startMin
operator|=
name|Integer
operator|.
name|parseInt
argument_list|(
name|number
argument_list|)
operator|%
literal|100
expr_stmt|;
block|}
else|else
block|{
name|startHour
operator|=
name|Integer
operator|.
name|parseInt
argument_list|(
name|number
argument_list|)
expr_stmt|;
block|}
while|while
condition|(
name|token
operator|.
name|startsWith
argument_list|(
literal|" "
argument_list|)
condition|)
name|token
operator|=
name|token
operator|.
name|substring
argument_list|(
literal|1
argument_list|)
expr_stmt|;
if|if
condition|(
name|token
operator|.
name|startsWith
argument_list|(
literal|":"
argument_list|)
condition|)
block|{
name|token
operator|=
name|token
operator|.
name|substring
argument_list|(
literal|1
argument_list|)
expr_stmt|;
while|while
condition|(
name|token
operator|.
name|startsWith
argument_list|(
literal|" "
argument_list|)
condition|)
name|token
operator|=
name|token
operator|.
name|substring
argument_list|(
literal|1
argument_list|)
expr_stmt|;
name|number
operator|=
literal|""
expr_stmt|;
while|while
condition|(
operator|!
name|token
operator|.
name|isEmpty
argument_list|()
operator|&&
name|token
operator|.
name|charAt
argument_list|(
literal|0
argument_list|)
operator|>=
literal|'0'
operator|&&
name|token
operator|.
name|charAt
argument_list|(
literal|0
argument_list|)
operator|<=
literal|'9'
condition|)
block|{
name|number
operator|+=
name|token
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
literal|1
argument_list|)
expr_stmt|;
name|token
operator|=
name|token
operator|.
name|substring
argument_list|(
literal|1
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|number
operator|.
name|isEmpty
argument_list|()
condition|)
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
name|MESSAGES
operator|.
name|invalidFreeTimeExpectedNumber
argument_list|(
name|original
argument_list|,
literal|1
operator|+
name|original
operator|.
name|lastIndexOf
argument_list|(
name|token
argument_list|)
argument_list|)
argument_list|)
throw|;
name|startMin
operator|=
name|Integer
operator|.
name|parseInt
argument_list|(
name|number
argument_list|)
expr_stmt|;
block|}
while|while
condition|(
name|token
operator|.
name|startsWith
argument_list|(
literal|" "
argument_list|)
condition|)
name|token
operator|=
name|token
operator|.
name|substring
argument_list|(
literal|1
argument_list|)
expr_stmt|;
name|boolean
name|hasAmOrPm
init|=
literal|false
decl_stmt|;
if|if
condition|(
name|token
operator|.
name|toLowerCase
argument_list|()
operator|.
name|startsWith
argument_list|(
literal|"am"
argument_list|)
condition|)
block|{
name|token
operator|=
name|token
operator|.
name|substring
argument_list|(
literal|2
argument_list|)
expr_stmt|;
name|hasAmOrPm
operator|=
literal|true
expr_stmt|;
block|}
if|if
condition|(
name|token
operator|.
name|toLowerCase
argument_list|()
operator|.
name|startsWith
argument_list|(
literal|"a"
argument_list|)
condition|)
block|{
name|token
operator|=
name|token
operator|.
name|substring
argument_list|(
literal|1
argument_list|)
expr_stmt|;
name|hasAmOrPm
operator|=
literal|true
expr_stmt|;
block|}
if|if
condition|(
name|token
operator|.
name|toLowerCase
argument_list|()
operator|.
name|startsWith
argument_list|(
literal|"pm"
argument_list|)
condition|)
block|{
name|token
operator|=
name|token
operator|.
name|substring
argument_list|(
literal|2
argument_list|)
expr_stmt|;
name|hasAmOrPm
operator|=
literal|true
expr_stmt|;
if|if
condition|(
name|startHour
operator|<
literal|12
condition|)
name|startHour
operator|+=
literal|12
expr_stmt|;
block|}
if|if
condition|(
name|token
operator|.
name|toLowerCase
argument_list|()
operator|.
name|startsWith
argument_list|(
literal|"p"
argument_list|)
condition|)
block|{
name|token
operator|=
name|token
operator|.
name|substring
argument_list|(
literal|1
argument_list|)
expr_stmt|;
name|hasAmOrPm
operator|=
literal|true
expr_stmt|;
if|if
condition|(
name|startHour
operator|<
literal|12
condition|)
name|startHour
operator|+=
literal|12
expr_stmt|;
block|}
if|if
condition|(
name|startHour
operator|<
literal|7
operator|&&
operator|!
name|hasAmOrPm
condition|)
name|startHour
operator|+=
literal|12
expr_stmt|;
comment|//if (startMin< 29) startMin = 0; else startMin = 30;
if|if
condition|(
name|startMin
operator|%
literal|5
operator|!=
literal|0
condition|)
name|startMin
operator|=
literal|5
operator|*
operator|(
operator|(
name|startMin
operator|+
literal|2
operator|)
operator|/
literal|5
operator|)
expr_stmt|;
if|if
condition|(
name|startHour
operator|==
literal|7
operator|&&
name|startMin
operator|==
literal|0
operator|&&
operator|!
name|hasAmOrPm
condition|)
name|startHour
operator|+=
literal|12
expr_stmt|;
name|int
name|startTime
init|=
operator|(
literal|60
operator|*
name|startHour
operator|+
name|startMin
operator|)
operator|/
literal|5
decl_stmt|;
comment|// (60 * startHour + startMin) / 30 - 15
name|int
name|endTime
init|=
name|startTime
decl_stmt|;
while|while
condition|(
name|token
operator|.
name|startsWith
argument_list|(
literal|" "
argument_list|)
condition|)
name|token
operator|=
name|token
operator|.
name|substring
argument_list|(
literal|1
argument_list|)
expr_stmt|;
if|if
condition|(
name|token
operator|.
name|startsWith
argument_list|(
literal|"-"
argument_list|)
condition|)
block|{
name|int
name|endHour
init|=
literal|0
decl_stmt|,
name|endMin
init|=
literal|0
decl_stmt|;
name|token
operator|=
name|token
operator|.
name|substring
argument_list|(
literal|1
argument_list|)
expr_stmt|;
while|while
condition|(
name|token
operator|.
name|startsWith
argument_list|(
literal|" "
argument_list|)
condition|)
name|token
operator|=
name|token
operator|.
name|substring
argument_list|(
literal|1
argument_list|)
expr_stmt|;
name|number
operator|=
literal|""
expr_stmt|;
while|while
condition|(
operator|!
name|token
operator|.
name|isEmpty
argument_list|()
operator|&&
name|token
operator|.
name|charAt
argument_list|(
literal|0
argument_list|)
operator|>=
literal|'0'
operator|&&
name|token
operator|.
name|charAt
argument_list|(
literal|0
argument_list|)
operator|<=
literal|'9'
condition|)
block|{
name|number
operator|+=
name|token
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
literal|1
argument_list|)
expr_stmt|;
name|token
operator|=
name|token
operator|.
name|substring
argument_list|(
literal|1
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|number
operator|.
name|isEmpty
argument_list|()
condition|)
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
name|MESSAGES
operator|.
name|invalidFreeTimeExpectedNumber
argument_list|(
name|original
argument_list|,
literal|1
operator|+
name|original
operator|.
name|lastIndexOf
argument_list|(
name|token
argument_list|)
argument_list|)
argument_list|)
throw|;
if|if
condition|(
name|number
operator|.
name|length
argument_list|()
operator|>
literal|2
condition|)
block|{
name|endHour
operator|=
name|Integer
operator|.
name|parseInt
argument_list|(
name|number
argument_list|)
operator|/
literal|100
expr_stmt|;
name|endMin
operator|=
name|Integer
operator|.
name|parseInt
argument_list|(
name|number
argument_list|)
operator|%
literal|100
expr_stmt|;
block|}
else|else
block|{
name|endHour
operator|=
name|Integer
operator|.
name|parseInt
argument_list|(
name|number
argument_list|)
expr_stmt|;
block|}
while|while
condition|(
name|token
operator|.
name|startsWith
argument_list|(
literal|" "
argument_list|)
condition|)
name|token
operator|=
name|token
operator|.
name|substring
argument_list|(
literal|1
argument_list|)
expr_stmt|;
if|if
condition|(
name|token
operator|.
name|startsWith
argument_list|(
literal|":"
argument_list|)
condition|)
block|{
name|token
operator|=
name|token
operator|.
name|substring
argument_list|(
literal|1
argument_list|)
expr_stmt|;
while|while
condition|(
name|token
operator|.
name|startsWith
argument_list|(
literal|" "
argument_list|)
condition|)
name|token
operator|=
name|token
operator|.
name|substring
argument_list|(
literal|1
argument_list|)
expr_stmt|;
name|number
operator|=
literal|""
expr_stmt|;
while|while
condition|(
operator|!
name|token
operator|.
name|isEmpty
argument_list|()
operator|&&
name|token
operator|.
name|charAt
argument_list|(
literal|0
argument_list|)
operator|>=
literal|'0'
operator|&&
name|token
operator|.
name|charAt
argument_list|(
literal|0
argument_list|)
operator|<=
literal|'9'
condition|)
block|{
name|number
operator|+=
name|token
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
literal|1
argument_list|)
expr_stmt|;
name|token
operator|=
name|token
operator|.
name|substring
argument_list|(
literal|1
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|number
operator|.
name|isEmpty
argument_list|()
condition|)
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
name|MESSAGES
operator|.
name|invalidFreeTimeExpectedNumber
argument_list|(
name|original
argument_list|,
literal|1
operator|+
name|original
operator|.
name|lastIndexOf
argument_list|(
name|token
argument_list|)
argument_list|)
argument_list|)
throw|;
name|endMin
operator|=
name|Integer
operator|.
name|parseInt
argument_list|(
name|number
argument_list|)
expr_stmt|;
block|}
while|while
condition|(
name|token
operator|.
name|startsWith
argument_list|(
literal|" "
argument_list|)
condition|)
name|token
operator|=
name|token
operator|.
name|substring
argument_list|(
literal|1
argument_list|)
expr_stmt|;
name|hasAmOrPm
operator|=
literal|false
expr_stmt|;
if|if
condition|(
name|token
operator|.
name|toLowerCase
argument_list|()
operator|.
name|startsWith
argument_list|(
literal|"am"
argument_list|)
condition|)
block|{
name|token
operator|=
name|token
operator|.
name|substring
argument_list|(
literal|2
argument_list|)
expr_stmt|;
name|hasAmOrPm
operator|=
literal|true
expr_stmt|;
if|if
condition|(
name|endHour
operator|==
literal|12
condition|)
name|endHour
operator|+=
literal|12
expr_stmt|;
block|}
if|if
condition|(
name|token
operator|.
name|toLowerCase
argument_list|()
operator|.
name|startsWith
argument_list|(
literal|"a"
argument_list|)
condition|)
block|{
name|token
operator|=
name|token
operator|.
name|substring
argument_list|(
literal|1
argument_list|)
expr_stmt|;
name|hasAmOrPm
operator|=
literal|true
expr_stmt|;
if|if
condition|(
name|endHour
operator|==
literal|12
condition|)
name|endHour
operator|+=
literal|12
expr_stmt|;
block|}
if|if
condition|(
name|token
operator|.
name|toLowerCase
argument_list|()
operator|.
name|startsWith
argument_list|(
literal|"pm"
argument_list|)
condition|)
block|{
name|token
operator|=
name|token
operator|.
name|substring
argument_list|(
literal|2
argument_list|)
expr_stmt|;
name|hasAmOrPm
operator|=
literal|true
expr_stmt|;
if|if
condition|(
name|endHour
operator|<
literal|12
condition|)
name|endHour
operator|+=
literal|12
expr_stmt|;
block|}
if|if
condition|(
name|token
operator|.
name|toLowerCase
argument_list|()
operator|.
name|startsWith
argument_list|(
literal|"p"
argument_list|)
condition|)
block|{
name|token
operator|=
name|token
operator|.
name|substring
argument_list|(
literal|1
argument_list|)
expr_stmt|;
name|hasAmOrPm
operator|=
literal|true
expr_stmt|;
if|if
condition|(
name|endHour
operator|<
literal|12
condition|)
name|endHour
operator|+=
literal|12
expr_stmt|;
block|}
if|if
condition|(
name|endHour
operator|<=
literal|7
operator|&&
operator|!
name|hasAmOrPm
condition|)
name|endHour
operator|+=
literal|12
expr_stmt|;
comment|// if (endMin< 29) endMin = 0; else endMin = 30;
if|if
condition|(
name|endMin
operator|%
literal|5
operator|!=
literal|0
condition|)
name|endMin
operator|=
literal|5
operator|*
operator|(
operator|(
name|endMin
operator|+
literal|2
operator|)
operator|/
literal|5
operator|)
expr_stmt|;
name|endTime
operator|=
operator|(
literal|60
operator|*
name|endHour
operator|+
name|endMin
operator|)
operator|/
literal|5
expr_stmt|;
comment|// (60 * endHour + endMin) / 30 - 15
block|}
while|while
condition|(
name|token
operator|.
name|startsWith
argument_list|(
literal|" "
argument_list|)
condition|)
name|token
operator|=
name|token
operator|.
name|substring
argument_list|(
literal|1
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
name|token
operator|.
name|isEmpty
argument_list|()
condition|)
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
name|MESSAGES
operator|.
name|invalidFreeTimeGeneric
argument_list|(
name|original
argument_list|,
literal|1
operator|+
name|original
operator|.
name|lastIndexOf
argument_list|(
name|token
argument_list|)
argument_list|)
argument_list|)
throw|;
if|if
condition|(
name|days
operator|.
name|isEmpty
argument_list|()
condition|)
name|days
operator|=
name|lastDays
expr_stmt|;
if|if
condition|(
name|days
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|CONSTANTS
operator|.
name|freeTimeDays
argument_list|()
operator|.
name|length
condition|;
name|i
operator|++
control|)
name|days
operator|.
name|add
argument_list|(
name|i
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|startTime
operator|==
name|endTime
condition|)
block|{
name|endTime
operator|+=
literal|6
expr_stmt|;
if|if
condition|(
operator|(
name|days
operator|.
name|contains
argument_list|(
literal|0
argument_list|)
operator|||
name|days
operator|.
name|contains
argument_list|(
literal|2
argument_list|)
operator|||
name|days
operator|.
name|contains
argument_list|(
literal|4
argument_list|)
operator|)
operator|&&
operator|!
name|days
operator|.
name|contains
argument_list|(
literal|1
argument_list|)
operator|&&
operator|!
name|days
operator|.
name|contains
argument_list|(
literal|3
argument_list|)
condition|)
block|{
if|if
condition|(
name|startTime
operator|%
literal|12
operator|==
literal|6
condition|)
name|endTime
operator|+=
literal|6
expr_stmt|;
block|}
if|else if
condition|(
operator|(
name|days
operator|.
name|contains
argument_list|(
literal|1
argument_list|)
operator|||
name|days
operator|.
name|contains
argument_list|(
literal|3
argument_list|)
operator|)
operator|&&
operator|!
name|days
operator|.
name|contains
argument_list|(
literal|0
argument_list|)
operator|&&
operator|!
name|days
operator|.
name|contains
argument_list|(
literal|2
argument_list|)
operator|&&
operator|!
name|days
operator|.
name|contains
argument_list|(
literal|4
argument_list|)
condition|)
block|{
if|if
condition|(
name|startTime
operator|%
literal|18
operator|==
literal|0
condition|)
name|endTime
operator|+=
literal|12
expr_stmt|;
if|else if
condition|(
name|startTime
operator|%
literal|18
operator|==
literal|6
condition|)
name|endTime
operator|+=
literal|6
expr_stmt|;
block|}
block|}
if|if
condition|(
name|startTime
operator|<
literal|0
operator|||
name|startTime
operator|>
literal|24
operator|*
literal|12
condition|)
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
name|MESSAGES
operator|.
name|invalidFreeTimeInvalidStartTime
argument_list|(
name|original
argument_list|)
argument_list|)
throw|;
if|if
condition|(
name|endTime
operator|<
literal|0
operator|||
name|endTime
operator|>
literal|24
operator|*
literal|12
condition|)
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
name|MESSAGES
operator|.
name|invalidFreeTimeInvalidEndTime
argument_list|(
name|original
argument_list|)
argument_list|)
throw|;
comment|/* 			if (startTime< 0) 				throw new IllegalArgumentException(MESSAGES.invalidFreeTimeStartBeforeFirst(original, CONSTANTS.freeTimePeriods()[0])); 			if (startTime>= CONSTANTS.freeTimePeriods().length - 1) 				throw new IllegalArgumentException(MESSAGES.invalidFreeTimeStartAfterLast(original, CONSTANTS.freeTimePeriods()[CONSTANTS.freeTimePeriods().length - 2])); 			if (endTime< 0) 				throw new IllegalArgumentException(MESSAGES.invalidFreeTimeEndBeforeFirst(original, CONSTANTS.freeTimePeriods()[0])); 			if (endTime>= CONSTANTS.freeTimePeriods().length)  				throw new IllegalArgumentException(MESSAGES.invalidFreeTimeEndAfterLast(original, CONSTANTS.freeTimePeriods()[CONSTANTS.freeTimePeriods().length - 1])); 			*/
if|if
condition|(
name|startTime
operator|>=
name|endTime
condition|)
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
name|MESSAGES
operator|.
name|invalidFreeTimeStartNotBeforeEnd
argument_list|(
name|original
argument_list|)
argument_list|)
throw|;
name|CourseRequestInterface
operator|.
name|FreeTime
name|f
init|=
operator|new
name|CourseRequestInterface
operator|.
name|FreeTime
argument_list|()
decl_stmt|;
for|for
control|(
name|int
name|day
range|:
name|days
control|)
name|f
operator|.
name|addDay
argument_list|(
name|day
argument_list|)
expr_stmt|;
name|f
operator|.
name|setStart
argument_list|(
name|startTime
argument_list|)
expr_stmt|;
comment|// 6 * (startTime + 15));
name|f
operator|.
name|setLength
argument_list|(
name|endTime
operator|-
name|startTime
argument_list|)
expr_stmt|;
comment|// 6 * (endTime - startTime));
name|ret
operator|.
name|add
argument_list|(
name|f
argument_list|)
expr_stmt|;
name|lastDays
operator|=
name|days
expr_stmt|;
block|}
return|return
name|ret
return|;
block|}
specifier|public
name|String
name|freeTimesToString
parameter_list|(
name|List
argument_list|<
name|CourseRequestInterface
operator|.
name|FreeTime
argument_list|>
name|freeTimes
parameter_list|)
block|{
name|String
name|ret
init|=
literal|""
decl_stmt|;
name|String
name|lastDays
init|=
literal|null
decl_stmt|;
for|for
control|(
name|CourseRequestInterface
operator|.
name|FreeTime
name|ft
range|:
name|freeTimes
control|)
block|{
if|if
condition|(
name|ret
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
name|ret
operator|+=
literal|", "
expr_stmt|;
name|String
name|days
init|=
name|ft
operator|.
name|getDaysString
argument_list|(
name|CONSTANTS
operator|.
name|shortDays
argument_list|()
argument_list|,
literal|""
argument_list|)
decl_stmt|;
if|if
condition|(
name|ft
operator|.
name|getDays
argument_list|()
operator|.
name|size
argument_list|()
operator|==
name|CONSTANTS
operator|.
name|freeTimeDays
argument_list|()
operator|.
name|length
operator|&&
operator|!
name|ft
operator|.
name|getDays
argument_list|()
operator|.
name|contains
argument_list|(
literal|5
argument_list|)
operator|&&
operator|!
name|ft
operator|.
name|getDays
argument_list|()
operator|.
name|contains
argument_list|(
literal|6
argument_list|)
condition|)
name|days
operator|=
literal|""
expr_stmt|;
name|ret
operator|+=
operator|(
name|days
operator|.
name|isEmpty
argument_list|()
operator|||
name|days
operator|.
name|equals
argument_list|(
name|lastDays
argument_list|)
condition|?
literal|""
else|:
name|days
operator|+
literal|" "
operator|)
operator|+
name|ft
operator|.
name|getStartString
argument_list|(
name|CONSTANTS
operator|.
name|useAmPm
argument_list|()
argument_list|)
operator|+
literal|" - "
operator|+
name|ft
operator|.
name|getEndString
argument_list|(
name|CONSTANTS
operator|.
name|useAmPm
argument_list|()
argument_list|)
expr_stmt|;
name|lastDays
operator|=
name|days
expr_stmt|;
block|}
return|return
name|CONSTANTS
operator|.
name|freePrefix
argument_list|()
operator|+
name|ret
return|;
block|}
block|}
end_class

end_unit

