begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.0 (University Course Timetabling& Student Sectioning Application)  * Copyright (C) 2007, UniTime.org, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 2 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program; if not, write to the Free Software Foundation, Inc.,  * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
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
name|Iterator
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
name|ChangeLog
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
name|ExternalBuilding
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
name|ExternalRoom
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
name|ExternalRoomDepartment
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
name|ExternalRoomFeature
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
name|TimetableManager
import|;
end_import

begin_comment
comment|/**  *   * @author Timothy Almon  *  */
end_comment

begin_class
specifier|public
class|class
name|BuildingRoomImport
extends|extends
name|BaseImport
block|{
name|TimetableManager
name|manager
init|=
literal|null
decl_stmt|;
specifier|private
specifier|static
name|int
name|BATCH_SIZE
init|=
literal|100
decl_stmt|;
specifier|public
name|BuildingRoomImport
parameter_list|()
block|{
block|}
specifier|public
name|void
name|loadXml
parameter_list|(
name|Element
name|root
parameter_list|)
throws|throws
name|Exception
block|{
try|try
block|{
name|beginTransaction
argument_list|()
expr_stmt|;
name|String
name|campus
init|=
name|root
operator|.
name|attributeValue
argument_list|(
literal|"campus"
argument_list|)
decl_stmt|;
name|String
name|year
init|=
name|root
operator|.
name|attributeValue
argument_list|(
literal|"year"
argument_list|)
decl_stmt|;
name|String
name|term
init|=
name|root
operator|.
name|attributeValue
argument_list|(
literal|"term"
argument_list|)
decl_stmt|;
name|String
name|created
init|=
name|root
operator|.
name|attributeValue
argument_list|(
literal|"created"
argument_list|)
decl_stmt|;
name|Session
name|session
init|=
name|Session
operator|.
name|getSessionUsingInitiativeYearTerm
argument_list|(
name|campus
argument_list|,
name|year
argument_list|,
name|term
argument_list|)
decl_stmt|;
if|if
condition|(
name|session
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|Exception
argument_list|(
literal|"No session found for the given campus, year, and term."
argument_list|)
throw|;
block|}
if|if
condition|(
name|manager
operator|==
literal|null
condition|)
block|{
name|manager
operator|=
name|findDefaultManager
argument_list|()
expr_stmt|;
block|}
if|if
condition|(
name|created
operator|!=
literal|null
condition|)
block|{
name|ChangeLog
operator|.
name|addChange
argument_list|(
name|getHibSession
argument_list|()
argument_list|,
name|manager
argument_list|,
name|session
argument_list|,
name|session
argument_list|,
name|created
argument_list|,
name|ChangeLog
operator|.
name|Source
operator|.
name|DATA_IMPORT_EXT_BUILDING_ROOM
argument_list|,
name|ChangeLog
operator|.
name|Operation
operator|.
name|UPDATE
argument_list|,
literal|null
argument_list|,
literal|null
argument_list|)
expr_stmt|;
block|}
comment|/*               * Remove all buildings and rooms for the given session and reload them using the xml               */
name|getHibSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"delete ExternalBuilding eb where eb.session.uniqueId=:sessionId"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"sessionId"
argument_list|,
name|session
operator|.
name|getUniqueId
argument_list|()
argument_list|)
operator|.
name|executeUpdate
argument_list|()
expr_stmt|;
name|flush
argument_list|(
literal|true
argument_list|)
expr_stmt|;
for|for
control|(
name|Iterator
name|i
init|=
name|root
operator|.
name|elementIterator
argument_list|()
init|;
name|i
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|importBuildings
argument_list|(
operator|(
name|Element
operator|)
name|i
operator|.
name|next
argument_list|()
argument_list|,
name|session
argument_list|)
expr_stmt|;
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
throw|throw
name|e
throw|;
block|}
block|}
specifier|public
name|void
name|importBuildings
parameter_list|(
name|Element
name|element
parameter_list|,
name|Session
name|session
parameter_list|)
throws|throws
name|Exception
block|{
name|int
name|batchIdx
init|=
literal|0
decl_stmt|;
name|ExternalBuilding
name|building
init|=
operator|new
name|ExternalBuilding
argument_list|()
decl_stmt|;
name|building
operator|.
name|setExternalUniqueId
argument_list|(
name|element
operator|.
name|attributeValue
argument_list|(
literal|"externalId"
argument_list|)
argument_list|)
expr_stmt|;
name|building
operator|.
name|setAbbreviation
argument_list|(
name|element
operator|.
name|attributeValue
argument_list|(
literal|"abbreviation"
argument_list|)
argument_list|)
expr_stmt|;
name|building
operator|.
name|setCoordinateX
argument_list|(
name|Integer
operator|.
name|decode
argument_list|(
name|element
operator|.
name|attributeValue
argument_list|(
literal|"locationX"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|building
operator|.
name|setCoordinateY
argument_list|(
name|Integer
operator|.
name|decode
argument_list|(
name|element
operator|.
name|attributeValue
argument_list|(
literal|"locationY"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|building
operator|.
name|setDisplayName
argument_list|(
name|element
operator|.
name|attributeValue
argument_list|(
literal|"name"
argument_list|)
argument_list|)
expr_stmt|;
name|building
operator|.
name|setSession
argument_list|(
name|session
argument_list|)
expr_stmt|;
name|getHibSession
argument_list|()
operator|.
name|save
argument_list|(
name|building
argument_list|)
expr_stmt|;
for|for
control|(
name|Iterator
name|i
init|=
name|element
operator|.
name|elementIterator
argument_list|(
literal|"room"
argument_list|)
init|;
name|i
operator|.
name|hasNext
argument_list|()
condition|;
control|)
name|importRoom
argument_list|(
operator|(
name|Element
operator|)
name|i
operator|.
name|next
argument_list|()
argument_list|,
name|building
argument_list|)
expr_stmt|;
name|getHibSession
argument_list|()
operator|.
name|save
argument_list|(
name|building
argument_list|)
expr_stmt|;
if|if
condition|(
operator|++
name|batchIdx
operator|==
name|BATCH_SIZE
condition|)
block|{
name|getHibSession
argument_list|()
operator|.
name|flush
argument_list|()
expr_stmt|;
name|getHibSession
argument_list|()
operator|.
name|clear
argument_list|()
expr_stmt|;
name|batchIdx
operator|=
literal|0
expr_stmt|;
block|}
block|}
specifier|private
name|void
name|importRoom
parameter_list|(
name|Element
name|element
parameter_list|,
name|ExternalBuilding
name|building
parameter_list|)
throws|throws
name|Exception
block|{
name|ExternalRoom
name|room
init|=
operator|new
name|ExternalRoom
argument_list|()
decl_stmt|;
name|room
operator|.
name|setExternalUniqueId
argument_list|(
name|element
operator|.
name|attributeValue
argument_list|(
literal|"externalId"
argument_list|)
argument_list|)
expr_stmt|;
name|room
operator|.
name|setCoordinateX
argument_list|(
name|Integer
operator|.
name|decode
argument_list|(
name|element
operator|.
name|attributeValue
argument_list|(
literal|"locationX"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|room
operator|.
name|setCoordinateY
argument_list|(
name|Integer
operator|.
name|decode
argument_list|(
name|element
operator|.
name|attributeValue
argument_list|(
literal|"locationY"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|room
operator|.
name|setRoomNumber
argument_list|(
name|element
operator|.
name|attributeValue
argument_list|(
literal|"roomNumber"
argument_list|)
argument_list|)
expr_stmt|;
name|room
operator|.
name|setClassification
argument_list|(
name|element
operator|.
name|attributeValue
argument_list|(
literal|"roomClassification"
argument_list|)
argument_list|)
expr_stmt|;
name|room
operator|.
name|setCapacity
argument_list|(
name|Integer
operator|.
name|decode
argument_list|(
name|element
operator|.
name|attributeValue
argument_list|(
literal|"capacity"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|String
name|examCapacityStr
init|=
name|element
operator|.
name|attributeValue
argument_list|(
literal|"examCapacity"
argument_list|)
decl_stmt|;
if|if
condition|(
name|examCapacityStr
operator|!=
literal|null
operator|&&
name|examCapacityStr
operator|.
name|trim
argument_list|()
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
block|{
name|room
operator|.
name|setExamCapacity
argument_list|(
name|Integer
operator|.
name|decode
argument_list|(
name|examCapacityStr
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|room
operator|.
name|setExamCapacity
argument_list|(
operator|new
name|Integer
argument_list|(
literal|0
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|room
operator|.
name|setIsInstructional
argument_list|(
name|Boolean
operator|.
name|valueOf
argument_list|(
name|element
operator|.
name|attributeValue
argument_list|(
literal|"instructional"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|room
operator|.
name|setScheduledRoomType
argument_list|(
name|element
operator|.
name|attributeValue
argument_list|(
literal|"scheduledRoomType"
argument_list|)
argument_list|)
expr_stmt|;
name|room
operator|.
name|setBuilding
argument_list|(
name|building
argument_list|)
expr_stmt|;
name|building
operator|.
name|addTorooms
argument_list|(
name|room
argument_list|)
expr_stmt|;
name|getHibSession
argument_list|()
operator|.
name|save
argument_list|(
name|room
argument_list|)
expr_stmt|;
if|if
condition|(
name|element
operator|.
name|element
argument_list|(
literal|"roomDepartments"
argument_list|)
operator|!=
literal|null
condition|)
block|{
for|for
control|(
name|Iterator
name|i
init|=
name|element
operator|.
name|element
argument_list|(
literal|"roomDepartments"
argument_list|)
operator|.
name|elementIterator
argument_list|()
init|;
name|i
operator|.
name|hasNext
argument_list|()
condition|;
control|)
name|importDepts
argument_list|(
operator|(
name|Element
operator|)
name|i
operator|.
name|next
argument_list|()
argument_list|,
name|room
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|element
operator|.
name|element
argument_list|(
literal|"roomFeatures"
argument_list|)
operator|!=
literal|null
condition|)
block|{
for|for
control|(
name|Iterator
name|i
init|=
name|element
operator|.
name|element
argument_list|(
literal|"roomFeatures"
argument_list|)
operator|.
name|elementIterator
argument_list|()
init|;
name|i
operator|.
name|hasNext
argument_list|()
condition|;
control|)
name|importFeatures
argument_list|(
operator|(
name|Element
operator|)
name|i
operator|.
name|next
argument_list|()
argument_list|,
name|room
argument_list|)
expr_stmt|;
block|}
block|}
specifier|private
name|void
name|importDepts
parameter_list|(
name|Element
name|element
parameter_list|,
name|ExternalRoom
name|room
parameter_list|)
throws|throws
name|Exception
block|{
name|ExternalRoomDepartment
name|dept
init|=
operator|new
name|ExternalRoomDepartment
argument_list|()
decl_stmt|;
name|dept
operator|.
name|setAssignmentType
argument_list|(
name|element
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
name|String
name|deptCode
init|=
name|element
operator|.
name|attributeValue
argument_list|(
literal|"departmentNumber"
argument_list|)
decl_stmt|;
if|if
condition|(
name|deptCode
operator|==
literal|null
condition|)
block|{
name|deptCode
operator|=
literal|"0000"
expr_stmt|;
block|}
name|dept
operator|.
name|setDepartmentCode
argument_list|(
name|deptCode
argument_list|)
expr_stmt|;
name|String
name|percent
init|=
name|element
operator|.
name|attributeValue
argument_list|(
literal|"percent"
argument_list|)
decl_stmt|;
if|if
condition|(
name|percent
operator|==
literal|null
condition|)
block|{
name|dept
operator|.
name|setPercent
argument_list|(
operator|new
name|Integer
argument_list|(
literal|100
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|dept
operator|.
name|setPercent
argument_list|(
name|Integer
operator|.
name|decode
argument_list|(
name|percent
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|dept
operator|.
name|setRoom
argument_list|(
name|room
argument_list|)
expr_stmt|;
name|room
operator|.
name|addToroomDepartments
argument_list|(
name|dept
argument_list|)
expr_stmt|;
name|getHibSession
argument_list|()
operator|.
name|save
argument_list|(
name|dept
argument_list|)
expr_stmt|;
block|}
specifier|private
name|void
name|importFeatures
parameter_list|(
name|Element
name|element
parameter_list|,
name|ExternalRoom
name|room
parameter_list|)
throws|throws
name|Exception
block|{
name|ExternalRoomFeature
name|feature
init|=
operator|new
name|ExternalRoomFeature
argument_list|()
decl_stmt|;
name|feature
operator|.
name|setName
argument_list|(
name|element
operator|.
name|attributeValue
argument_list|(
literal|"feature"
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|feature
operator|.
name|getName
argument_list|()
operator|==
literal|null
condition|)
throw|throw
operator|new
name|Exception
argument_list|(
literal|"Room feature name for room "
operator|+
name|room
operator|.
name|getExternalUniqueId
argument_list|()
operator|+
literal|" not present."
argument_list|)
throw|;
name|feature
operator|.
name|setValue
argument_list|(
name|element
operator|.
name|attributeValue
argument_list|(
literal|"value"
argument_list|)
argument_list|)
expr_stmt|;
name|feature
operator|.
name|setRoom
argument_list|(
name|room
argument_list|)
expr_stmt|;
name|room
operator|.
name|addToroomFeatures
argument_list|(
name|feature
argument_list|)
expr_stmt|;
name|getHibSession
argument_list|()
operator|.
name|save
argument_list|(
name|feature
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

