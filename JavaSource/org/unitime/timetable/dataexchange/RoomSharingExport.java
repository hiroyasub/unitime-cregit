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
name|dataexchange
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Collections
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Date
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
name|java
operator|.
name|util
operator|.
name|Properties
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
name|Constants
import|;
end_import

begin_import
import|import
name|org
operator|.
name|dom4j
operator|.
name|Document
import|;
end_import

begin_import
import|import
name|org
operator|.
name|dom4j
operator|.
name|Element
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
name|Department
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
name|Room
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
name|RoomDept
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
name|RoomSharingModel
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
name|util
operator|.
name|Formats
import|;
end_import

begin_comment
comment|/**  *   * @author Tomas Muller  *  */
end_comment

begin_class
specifier|public
class|class
name|RoomSharingExport
extends|extends
name|BaseExport
block|{
specifier|protected
specifier|static
name|Formats
operator|.
name|Format
argument_list|<
name|Number
argument_list|>
name|sTwoNumbersDF
init|=
name|Formats
operator|.
name|getNumberFormat
argument_list|(
literal|"00"
argument_list|)
decl_stmt|;
annotation|@
name|Override
specifier|public
name|void
name|saveXml
parameter_list|(
name|Document
name|document
parameter_list|,
name|Session
name|session
parameter_list|,
name|Properties
name|parameters
parameter_list|)
throws|throws
name|Exception
block|{
try|try
block|{
name|beginTransaction
argument_list|()
expr_stmt|;
name|Element
name|root
init|=
name|document
operator|.
name|addElement
argument_list|(
literal|"roomSharing"
argument_list|)
decl_stmt|;
name|root
operator|.
name|addAttribute
argument_list|(
literal|"campus"
argument_list|,
name|session
operator|.
name|getAcademicInitiative
argument_list|()
argument_list|)
expr_stmt|;
name|root
operator|.
name|addAttribute
argument_list|(
literal|"year"
argument_list|,
name|session
operator|.
name|getAcademicYear
argument_list|()
argument_list|)
expr_stmt|;
name|root
operator|.
name|addAttribute
argument_list|(
literal|"term"
argument_list|,
name|session
operator|.
name|getAcademicTerm
argument_list|()
argument_list|)
expr_stmt|;
name|root
operator|.
name|addAttribute
argument_list|(
literal|"created"
argument_list|,
operator|new
name|Date
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
name|root
operator|.
name|addAttribute
argument_list|(
literal|"timeFormat"
argument_list|,
literal|"HHmm"
argument_list|)
expr_stmt|;
name|document
operator|.
name|addDocType
argument_list|(
literal|"roomSharing"
argument_list|,
literal|"-//UniTime//UniTime Room Sharing DTD/EN"
argument_list|,
literal|"http://www.unitime.org/interface/RoomSharing.dtd"
argument_list|)
expr_stmt|;
name|List
argument_list|<
name|Location
argument_list|>
name|locations
init|=
name|Location
operator|.
name|findAll
argument_list|(
name|session
operator|.
name|getUniqueId
argument_list|()
argument_list|)
decl_stmt|;
name|Collections
operator|.
name|sort
argument_list|(
name|locations
argument_list|)
expr_stmt|;
for|for
control|(
name|Location
name|location
range|:
name|locations
control|)
block|{
name|Element
name|locEl
init|=
name|root
operator|.
name|addElement
argument_list|(
literal|"location"
argument_list|)
decl_stmt|;
name|fillLocationData
argument_list|(
name|location
argument_list|,
name|locEl
argument_list|)
expr_stmt|;
if|if
condition|(
name|location
operator|.
name|getShareNote
argument_list|()
operator|!=
literal|null
condition|)
name|locEl
operator|.
name|addAttribute
argument_list|(
literal|"note"
argument_list|,
name|location
operator|.
name|getShareNote
argument_list|()
argument_list|)
expr_stmt|;
name|Map
argument_list|<
name|Long
argument_list|,
name|Department
argument_list|>
name|id2dept
init|=
operator|new
name|HashMap
argument_list|<
name|Long
argument_list|,
name|Department
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|RoomDept
name|rd
range|:
name|location
operator|.
name|getRoomDepts
argument_list|()
control|)
block|{
name|Element
name|deptEl
init|=
name|locEl
operator|.
name|addElement
argument_list|(
literal|"department"
argument_list|)
decl_stmt|;
name|fillDepartmentData
argument_list|(
name|rd
argument_list|,
name|deptEl
argument_list|)
expr_stmt|;
name|id2dept
operator|.
name|put
argument_list|(
name|rd
operator|.
name|getDepartment
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|,
name|rd
operator|.
name|getDepartment
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|RoomSharingModel
name|model
init|=
name|location
operator|.
name|getRoomSharingModel
argument_list|()
decl_stmt|;
if|if
condition|(
name|model
operator|!=
literal|null
operator|&&
operator|!
name|model
operator|.
name|allAvailable
argument_list|(
literal|null
argument_list|)
condition|)
block|{
name|Element
name|sharingEl
init|=
name|locEl
operator|.
name|addElement
argument_list|(
literal|"sharing"
argument_list|)
decl_stmt|;
name|boolean
name|out
index|[]
index|[]
init|=
operator|new
name|boolean
index|[
name|model
operator|.
name|getNrDays
argument_list|()
index|]
index|[
name|model
operator|.
name|getNrTimes
argument_list|()
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
name|model
operator|.
name|getNrDays
argument_list|()
condition|;
name|i
operator|++
control|)
for|for
control|(
name|int
name|j
init|=
literal|0
init|;
name|j
operator|<
name|model
operator|.
name|getNrTimes
argument_list|()
condition|;
name|j
operator|++
control|)
name|out
index|[
name|i
index|]
index|[
name|j
index|]
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
name|model
operator|.
name|getNrDays
argument_list|()
condition|;
name|i
operator|++
control|)
for|for
control|(
name|int
name|j
init|=
literal|0
init|;
name|j
operator|<
name|model
operator|.
name|getNrTimes
argument_list|()
condition|;
name|j
operator|++
control|)
block|{
if|if
condition|(
name|out
index|[
name|i
index|]
index|[
name|j
index|]
condition|)
continue|continue;
name|out
index|[
name|i
index|]
index|[
name|j
index|]
operator|=
literal|true
expr_stmt|;
if|if
condition|(
name|model
operator|.
name|isFreeForAll
argument_list|(
name|i
argument_list|,
name|j
argument_list|)
condition|)
continue|continue;
name|int
name|endDay
init|=
name|i
decl_stmt|,
name|endTime
init|=
name|j
decl_stmt|;
name|String
name|p
init|=
name|model
operator|.
name|getPreference
argument_list|(
name|i
argument_list|,
name|j
argument_list|)
decl_stmt|;
while|while
condition|(
name|endTime
operator|+
literal|1
operator|<
name|model
operator|.
name|getNrTimes
argument_list|()
operator|&&
operator|!
name|out
index|[
name|i
index|]
index|[
name|endTime
operator|+
literal|1
index|]
operator|&&
name|model
operator|.
name|getPreference
argument_list|(
name|i
argument_list|,
name|endTime
operator|+
literal|1
argument_list|)
operator|.
name|equals
argument_list|(
name|p
argument_list|)
condition|)
name|endTime
operator|++
expr_stmt|;
while|while
condition|(
name|endDay
operator|+
literal|1
operator|<
name|model
operator|.
name|getNrDays
argument_list|()
condition|)
block|{
name|boolean
name|same
init|=
literal|true
decl_stmt|;
for|for
control|(
name|int
name|x
init|=
name|j
init|;
name|x
operator|<=
name|endTime
condition|;
name|x
operator|++
control|)
if|if
condition|(
operator|!
name|out
index|[
name|endDay
operator|+
literal|1
index|]
index|[
name|x
index|]
operator|&&
operator|!
name|model
operator|.
name|getPreference
argument_list|(
name|endDay
operator|+
literal|1
argument_list|,
name|x
argument_list|)
operator|.
name|equals
argument_list|(
name|p
argument_list|)
condition|)
block|{
name|same
operator|=
literal|false
expr_stmt|;
break|break;
block|}
if|if
condition|(
operator|!
name|same
condition|)
break|break;
name|endDay
operator|++
expr_stmt|;
block|}
for|for
control|(
name|int
name|a
init|=
name|i
init|;
name|a
operator|<=
name|endDay
condition|;
name|a
operator|++
control|)
for|for
control|(
name|int
name|b
init|=
name|j
init|;
name|b
operator|<=
name|endTime
condition|;
name|b
operator|++
control|)
name|out
index|[
name|a
index|]
index|[
name|b
index|]
operator|=
literal|true
expr_stmt|;
name|Element
name|el
init|=
literal|null
decl_stmt|;
name|Department
name|dept
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|model
operator|.
name|isNotAvailable
argument_list|(
name|i
argument_list|,
name|j
argument_list|)
condition|)
block|{
name|el
operator|=
name|sharingEl
operator|.
name|addElement
argument_list|(
literal|"unavailable"
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|dept
operator|=
name|id2dept
operator|.
name|get
argument_list|(
name|model
operator|.
name|getDepartmentId
argument_list|(
name|i
argument_list|,
name|j
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|dept
operator|==
literal|null
condition|)
continue|continue;
name|el
operator|=
name|sharingEl
operator|.
name|addElement
argument_list|(
literal|"assigned"
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|i
operator|==
literal|0
operator|&&
name|endDay
operator|+
literal|1
operator|==
name|model
operator|.
name|getNrDays
argument_list|()
condition|)
block|{
comment|// all week
block|}
else|else
block|{
name|String
name|day
init|=
literal|""
decl_stmt|;
for|for
control|(
name|int
name|a
init|=
name|i
init|;
name|a
operator|<=
name|endDay
condition|;
name|a
operator|++
control|)
name|day
operator|+=
name|Constants
operator|.
name|DAY_NAMES_SHORT
index|[
name|a
index|]
expr_stmt|;
name|el
operator|.
name|addAttribute
argument_list|(
literal|"days"
argument_list|,
name|day
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|j
operator|==
literal|0
operator|&&
name|endTime
operator|+
literal|1
operator|==
name|model
operator|.
name|getNrTimes
argument_list|()
condition|)
block|{
comment|// all day
block|}
else|else
block|{
name|el
operator|.
name|addAttribute
argument_list|(
literal|"start"
argument_list|,
name|slot2time
argument_list|(
name|j
argument_list|)
argument_list|)
expr_stmt|;
name|el
operator|.
name|addAttribute
argument_list|(
literal|"end"
argument_list|,
name|slot2time
argument_list|(
name|endTime
operator|+
literal|1
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|dept
operator|!=
literal|null
condition|)
name|fillDepartmentData
argument_list|(
name|dept
argument_list|,
name|el
argument_list|)
expr_stmt|;
block|}
block|}
block|}
name|commitTransaction
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|fatal
argument_list|(
literal|"Exception: "
operator|+
name|e
operator|.
name|getMessage
argument_list|()
argument_list|,
name|e
argument_list|)
expr_stmt|;
name|rollbackTransaction
argument_list|()
expr_stmt|;
block|}
block|}
specifier|protected
name|void
name|fillLocationData
parameter_list|(
name|Location
name|location
parameter_list|,
name|Element
name|element
parameter_list|)
block|{
if|if
condition|(
name|location
operator|instanceof
name|Room
condition|)
block|{
name|Room
name|room
init|=
operator|(
name|Room
operator|)
name|location
decl_stmt|;
name|element
operator|.
name|addAttribute
argument_list|(
literal|"building"
argument_list|,
name|room
operator|.
name|getBuilding
argument_list|()
operator|.
name|getAbbreviation
argument_list|()
argument_list|)
expr_stmt|;
name|element
operator|.
name|addAttribute
argument_list|(
literal|"roomNbr"
argument_list|,
name|room
operator|.
name|getRoomNumber
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|element
operator|.
name|addAttribute
argument_list|(
literal|"name"
argument_list|,
name|location
operator|.
name|getLabel
argument_list|()
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|location
operator|.
name|getExternalUniqueId
argument_list|()
operator|!=
literal|null
operator|&&
operator|!
name|location
operator|.
name|getExternalUniqueId
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
name|element
operator|.
name|addAttribute
argument_list|(
literal|"id"
argument_list|,
name|location
operator|.
name|getExternalUniqueId
argument_list|()
argument_list|)
expr_stmt|;
block|}
specifier|protected
name|void
name|fillDepartmentData
parameter_list|(
name|RoomDept
name|rd
parameter_list|,
name|Element
name|element
parameter_list|)
block|{
name|fillDepartmentData
argument_list|(
name|rd
operator|.
name|getDepartment
argument_list|()
argument_list|,
name|element
argument_list|)
expr_stmt|;
if|if
condition|(
name|rd
operator|.
name|isControl
argument_list|()
condition|)
name|element
operator|.
name|addAttribute
argument_list|(
literal|"control"
argument_list|,
literal|"true"
argument_list|)
expr_stmt|;
block|}
specifier|protected
name|void
name|fillDepartmentData
parameter_list|(
name|Department
name|dept
parameter_list|,
name|Element
name|element
parameter_list|)
block|{
if|if
condition|(
name|dept
operator|.
name|getExternalUniqueId
argument_list|()
operator|!=
literal|null
condition|)
name|element
operator|.
name|addAttribute
argument_list|(
literal|"id"
argument_list|,
name|dept
operator|.
name|getExternalUniqueId
argument_list|()
argument_list|)
expr_stmt|;
name|element
operator|.
name|addAttribute
argument_list|(
literal|"code"
argument_list|,
name|dept
operator|.
name|getDeptCode
argument_list|()
argument_list|)
expr_stmt|;
block|}
specifier|protected
name|String
name|slot2time
parameter_list|(
name|int
name|slot
parameter_list|)
block|{
name|int
name|minutesSinceMidnight
init|=
name|Constants
operator|.
name|SLOT_LENGTH_MIN
operator|*
name|slot
operator|+
name|Constants
operator|.
name|FIRST_SLOT_TIME_MIN
decl_stmt|;
return|return
name|sTwoNumbersDF
operator|.
name|format
argument_list|(
name|minutesSinceMidnight
operator|/
literal|60
argument_list|)
operator|+
name|sTwoNumbersDF
operator|.
name|format
argument_list|(
name|minutesSinceMidnight
operator|%
literal|60
argument_list|)
return|;
block|}
block|}
end_class

end_unit

