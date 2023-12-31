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
name|server
operator|.
name|admin
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
name|Set
import|;
end_import

begin_import
import|import
name|org
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
name|hibernate
operator|.
name|Session
import|;
end_import

begin_import
import|import
name|org
operator|.
name|springframework
operator|.
name|security
operator|.
name|access
operator|.
name|prepost
operator|.
name|PreAuthorize
import|;
end_import

begin_import
import|import
name|org
operator|.
name|springframework
operator|.
name|stereotype
operator|.
name|Service
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
name|timetable
operator|.
name|gwt
operator|.
name|resources
operator|.
name|GwtMessages
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
name|SimpleEditInterface
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
name|SimpleEditInterface
operator|.
name|Field
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
name|SimpleEditInterface
operator|.
name|FieldType
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
name|SimpleEditInterface
operator|.
name|Flag
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
name|SimpleEditInterface
operator|.
name|ListItem
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
name|SimpleEditInterface
operator|.
name|PageName
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
name|SimpleEditInterface
operator|.
name|Record
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
name|AcademicArea
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
name|PosMinor
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
operator|.
name|Operation
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
operator|.
name|Source
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
name|AcademicAreaDAO
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
name|PosMinorDAO
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
name|SessionDAO
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
name|security
operator|.
name|SessionContext
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
name|security
operator|.
name|rights
operator|.
name|Right
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
annotation|@
name|Service
argument_list|(
literal|"gwtAdminTable[type=minor]"
argument_list|)
specifier|public
class|class
name|Minors
implements|implements
name|AdminTable
block|{
specifier|protected
specifier|static
specifier|final
name|GwtMessages
name|MESSAGES
init|=
name|Localization
operator|.
name|create
argument_list|(
name|GwtMessages
operator|.
name|class
argument_list|)
decl_stmt|;
annotation|@
name|Override
specifier|public
name|PageName
name|name
parameter_list|()
block|{
return|return
operator|new
name|PageName
argument_list|(
name|MESSAGES
operator|.
name|pageMinor
argument_list|()
argument_list|,
name|MESSAGES
operator|.
name|pageMinors
argument_list|()
argument_list|)
return|;
block|}
annotation|@
name|Override
annotation|@
name|PreAuthorize
argument_list|(
literal|"checkPermission('Minors')"
argument_list|)
specifier|public
name|SimpleEditInterface
name|load
parameter_list|(
name|SessionContext
name|context
parameter_list|,
name|Session
name|hibSession
parameter_list|)
block|{
name|List
argument_list|<
name|ListItem
argument_list|>
name|areas
init|=
operator|new
name|ArrayList
argument_list|<
name|ListItem
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|AcademicArea
name|area
range|:
name|AcademicAreaDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|findBySession
argument_list|(
name|hibSession
argument_list|,
name|context
operator|.
name|getUser
argument_list|()
operator|.
name|getCurrentAcademicSessionId
argument_list|()
argument_list|)
control|)
block|{
name|areas
operator|.
name|add
argument_list|(
operator|new
name|ListItem
argument_list|(
name|area
operator|.
name|getUniqueId
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|,
name|area
operator|.
name|getAcademicAreaAbbreviation
argument_list|()
operator|+
literal|" - "
operator|+
name|area
operator|.
name|getTitle
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|SimpleEditInterface
name|data
init|=
operator|new
name|SimpleEditInterface
argument_list|(
operator|new
name|Field
argument_list|(
name|MESSAGES
operator|.
name|fieldExternalId
argument_list|()
argument_list|,
name|FieldType
operator|.
name|text
argument_list|,
literal|120
argument_list|,
literal|40
argument_list|,
name|Flag
operator|.
name|READ_ONLY
argument_list|)
argument_list|,
operator|new
name|Field
argument_list|(
name|MESSAGES
operator|.
name|fieldCode
argument_list|()
argument_list|,
name|FieldType
operator|.
name|text
argument_list|,
literal|120
argument_list|,
literal|40
argument_list|,
name|Flag
operator|.
name|UNIQUE
argument_list|)
argument_list|,
operator|new
name|Field
argument_list|(
name|MESSAGES
operator|.
name|fieldName
argument_list|()
argument_list|,
name|FieldType
operator|.
name|text
argument_list|,
literal|300
argument_list|,
literal|100
argument_list|,
name|Flag
operator|.
name|UNIQUE
argument_list|)
argument_list|,
operator|new
name|Field
argument_list|(
name|MESSAGES
operator|.
name|fieldAcademicArea
argument_list|()
argument_list|,
name|FieldType
operator|.
name|list
argument_list|,
literal|300
argument_list|,
name|areas
argument_list|)
argument_list|)
decl_stmt|;
name|data
operator|.
name|setSortBy
argument_list|(
literal|3
argument_list|,
literal|1
argument_list|,
literal|2
argument_list|)
expr_stmt|;
for|for
control|(
name|PosMinor
name|minor
range|:
name|PosMinorDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|findBySession
argument_list|(
name|hibSession
argument_list|,
name|context
operator|.
name|getUser
argument_list|()
operator|.
name|getCurrentAcademicSessionId
argument_list|()
argument_list|)
control|)
block|{
name|Record
name|r
init|=
name|data
operator|.
name|addRecord
argument_list|(
name|minor
operator|.
name|getUniqueId
argument_list|()
argument_list|)
decl_stmt|;
name|r
operator|.
name|setField
argument_list|(
literal|0
argument_list|,
name|minor
operator|.
name|getExternalUniqueId
argument_list|()
argument_list|)
expr_stmt|;
name|r
operator|.
name|setField
argument_list|(
literal|1
argument_list|,
name|minor
operator|.
name|getCode
argument_list|()
argument_list|)
expr_stmt|;
name|r
operator|.
name|setField
argument_list|(
literal|2
argument_list|,
name|minor
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
for|for
control|(
name|AcademicArea
name|area
range|:
name|minor
operator|.
name|getAcademicAreas
argument_list|()
control|)
name|r
operator|.
name|setField
argument_list|(
literal|3
argument_list|,
name|area
operator|.
name|getUniqueId
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
name|r
operator|.
name|setDeletable
argument_list|(
name|minor
operator|.
name|getExternalUniqueId
argument_list|()
operator|==
literal|null
argument_list|)
expr_stmt|;
block|}
name|data
operator|.
name|setEditable
argument_list|(
name|context
operator|.
name|hasPermission
argument_list|(
name|Right
operator|.
name|MinorEdit
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|data
return|;
block|}
annotation|@
name|Override
annotation|@
name|PreAuthorize
argument_list|(
literal|"checkPermission('MinorEdit')"
argument_list|)
specifier|public
name|void
name|save
parameter_list|(
name|SimpleEditInterface
name|data
parameter_list|,
name|SessionContext
name|context
parameter_list|,
name|Session
name|hibSession
parameter_list|)
block|{
for|for
control|(
name|PosMinor
name|minor
range|:
name|PosMinorDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|findBySession
argument_list|(
name|hibSession
argument_list|,
name|context
operator|.
name|getUser
argument_list|()
operator|.
name|getCurrentAcademicSessionId
argument_list|()
argument_list|)
control|)
block|{
name|Record
name|r
init|=
name|data
operator|.
name|getRecord
argument_list|(
name|minor
operator|.
name|getUniqueId
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|r
operator|==
literal|null
condition|)
block|{
name|ChangeLog
operator|.
name|addChange
argument_list|(
name|hibSession
argument_list|,
name|context
argument_list|,
name|minor
argument_list|,
name|minor
operator|.
name|getCode
argument_list|()
operator|+
literal|" "
operator|+
name|minor
operator|.
name|getName
argument_list|()
argument_list|,
name|Source
operator|.
name|SIMPLE_EDIT
argument_list|,
name|Operation
operator|.
name|DELETE
argument_list|,
literal|null
argument_list|,
literal|null
argument_list|)
expr_stmt|;
name|hibSession
operator|.
name|delete
argument_list|(
name|minor
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|boolean
name|changed
init|=
operator|!
name|ToolBox
operator|.
name|equals
argument_list|(
name|minor
operator|.
name|getExternalUniqueId
argument_list|()
argument_list|,
name|r
operator|.
name|getField
argument_list|(
literal|0
argument_list|)
argument_list|)
operator|||
operator|!
name|ToolBox
operator|.
name|equals
argument_list|(
name|minor
operator|.
name|getCode
argument_list|()
argument_list|,
name|r
operator|.
name|getField
argument_list|(
literal|1
argument_list|)
argument_list|)
operator|||
operator|!
name|ToolBox
operator|.
name|equals
argument_list|(
name|minor
operator|.
name|getName
argument_list|()
argument_list|,
name|r
operator|.
name|getField
argument_list|(
literal|2
argument_list|)
argument_list|)
decl_stmt|;
name|minor
operator|.
name|setExternalUniqueId
argument_list|(
name|r
operator|.
name|getField
argument_list|(
literal|0
argument_list|)
argument_list|)
expr_stmt|;
name|minor
operator|.
name|setCode
argument_list|(
name|r
operator|.
name|getField
argument_list|(
literal|1
argument_list|)
argument_list|)
expr_stmt|;
name|minor
operator|.
name|setName
argument_list|(
name|r
operator|.
name|getField
argument_list|(
literal|2
argument_list|)
argument_list|)
expr_stmt|;
name|Set
argument_list|<
name|AcademicArea
argument_list|>
name|delete
init|=
operator|new
name|HashSet
argument_list|<
name|AcademicArea
argument_list|>
argument_list|(
name|minor
operator|.
name|getAcademicAreas
argument_list|()
argument_list|)
decl_stmt|;
for|for
control|(
name|String
name|areaId
range|:
name|r
operator|.
name|getValues
argument_list|(
literal|3
argument_list|)
control|)
block|{
name|AcademicArea
name|area
init|=
name|AcademicAreaDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|get
argument_list|(
name|Long
operator|.
name|valueOf
argument_list|(
name|areaId
argument_list|)
argument_list|,
name|hibSession
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|delete
operator|.
name|remove
argument_list|(
name|area
argument_list|)
condition|)
block|{
name|minor
operator|.
name|getAcademicAreas
argument_list|()
operator|.
name|add
argument_list|(
name|area
argument_list|)
expr_stmt|;
name|area
operator|.
name|getPosMinors
argument_list|()
operator|.
name|add
argument_list|(
name|minor
argument_list|)
expr_stmt|;
name|changed
operator|=
literal|true
expr_stmt|;
block|}
block|}
for|for
control|(
name|AcademicArea
name|area
range|:
name|delete
control|)
block|{
name|minor
operator|.
name|getAcademicAreas
argument_list|()
operator|.
name|remove
argument_list|(
name|area
argument_list|)
expr_stmt|;
name|area
operator|.
name|getPosMinors
argument_list|()
operator|.
name|remove
argument_list|(
name|minor
argument_list|)
expr_stmt|;
name|changed
operator|=
literal|true
expr_stmt|;
block|}
name|hibSession
operator|.
name|saveOrUpdate
argument_list|(
name|minor
argument_list|)
expr_stmt|;
if|if
condition|(
name|changed
condition|)
name|ChangeLog
operator|.
name|addChange
argument_list|(
name|hibSession
argument_list|,
name|context
argument_list|,
name|minor
argument_list|,
name|minor
operator|.
name|getCode
argument_list|()
operator|+
literal|" "
operator|+
name|minor
operator|.
name|getName
argument_list|()
argument_list|,
name|Source
operator|.
name|SIMPLE_EDIT
argument_list|,
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
block|}
for|for
control|(
name|Record
name|r
range|:
name|data
operator|.
name|getNewRecords
argument_list|()
control|)
block|{
name|PosMinor
name|minor
init|=
operator|new
name|PosMinor
argument_list|()
decl_stmt|;
name|minor
operator|.
name|setExternalUniqueId
argument_list|(
name|r
operator|.
name|getField
argument_list|(
literal|0
argument_list|)
argument_list|)
expr_stmt|;
name|minor
operator|.
name|setCode
argument_list|(
name|r
operator|.
name|getField
argument_list|(
literal|1
argument_list|)
argument_list|)
expr_stmt|;
name|minor
operator|.
name|setName
argument_list|(
name|r
operator|.
name|getField
argument_list|(
literal|2
argument_list|)
argument_list|)
expr_stmt|;
name|minor
operator|.
name|setSession
argument_list|(
name|SessionDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|get
argument_list|(
name|context
operator|.
name|getUser
argument_list|()
operator|.
name|getCurrentAcademicSessionId
argument_list|()
argument_list|,
name|hibSession
argument_list|)
argument_list|)
expr_stmt|;
name|minor
operator|.
name|setAcademicAreas
argument_list|(
operator|new
name|HashSet
argument_list|<
name|AcademicArea
argument_list|>
argument_list|()
argument_list|)
expr_stmt|;
for|for
control|(
name|String
name|areaId
range|:
name|r
operator|.
name|getValues
argument_list|(
literal|3
argument_list|)
control|)
block|{
name|AcademicArea
name|area
init|=
name|AcademicAreaDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|get
argument_list|(
name|Long
operator|.
name|valueOf
argument_list|(
name|areaId
argument_list|)
argument_list|,
name|hibSession
argument_list|)
decl_stmt|;
name|minor
operator|.
name|getAcademicAreas
argument_list|()
operator|.
name|add
argument_list|(
name|area
argument_list|)
expr_stmt|;
name|area
operator|.
name|getPosMinors
argument_list|()
operator|.
name|add
argument_list|(
name|minor
argument_list|)
expr_stmt|;
block|}
name|r
operator|.
name|setUniqueId
argument_list|(
operator|(
name|Long
operator|)
name|hibSession
operator|.
name|save
argument_list|(
name|minor
argument_list|)
argument_list|)
expr_stmt|;
name|ChangeLog
operator|.
name|addChange
argument_list|(
name|hibSession
argument_list|,
name|context
argument_list|,
name|minor
argument_list|,
name|minor
operator|.
name|getCode
argument_list|()
operator|+
literal|" "
operator|+
name|minor
operator|.
name|getName
argument_list|()
argument_list|,
name|Source
operator|.
name|SIMPLE_EDIT
argument_list|,
name|Operation
operator|.
name|CREATE
argument_list|,
literal|null
argument_list|,
literal|null
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Override
annotation|@
name|PreAuthorize
argument_list|(
literal|"checkPermission('MinorEdit')"
argument_list|)
specifier|public
name|void
name|save
parameter_list|(
name|Record
name|record
parameter_list|,
name|SessionContext
name|context
parameter_list|,
name|Session
name|hibSession
parameter_list|)
block|{
name|PosMinor
name|minor
init|=
operator|new
name|PosMinor
argument_list|()
decl_stmt|;
name|minor
operator|.
name|setExternalUniqueId
argument_list|(
name|record
operator|.
name|getField
argument_list|(
literal|0
argument_list|)
argument_list|)
expr_stmt|;
name|minor
operator|.
name|setCode
argument_list|(
name|record
operator|.
name|getField
argument_list|(
literal|1
argument_list|)
argument_list|)
expr_stmt|;
name|minor
operator|.
name|setName
argument_list|(
name|record
operator|.
name|getField
argument_list|(
literal|2
argument_list|)
argument_list|)
expr_stmt|;
name|minor
operator|.
name|setSession
argument_list|(
name|SessionDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|get
argument_list|(
name|context
operator|.
name|getUser
argument_list|()
operator|.
name|getCurrentAcademicSessionId
argument_list|()
argument_list|,
name|hibSession
argument_list|)
argument_list|)
expr_stmt|;
name|minor
operator|.
name|setAcademicAreas
argument_list|(
operator|new
name|HashSet
argument_list|<
name|AcademicArea
argument_list|>
argument_list|()
argument_list|)
expr_stmt|;
for|for
control|(
name|String
name|areaId
range|:
name|record
operator|.
name|getValues
argument_list|(
literal|3
argument_list|)
control|)
block|{
name|AcademicArea
name|area
init|=
name|AcademicAreaDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|get
argument_list|(
name|Long
operator|.
name|valueOf
argument_list|(
name|areaId
argument_list|)
argument_list|,
name|hibSession
argument_list|)
decl_stmt|;
name|minor
operator|.
name|getAcademicAreas
argument_list|()
operator|.
name|add
argument_list|(
name|area
argument_list|)
expr_stmt|;
name|area
operator|.
name|getPosMinors
argument_list|()
operator|.
name|add
argument_list|(
name|minor
argument_list|)
expr_stmt|;
block|}
name|record
operator|.
name|setUniqueId
argument_list|(
operator|(
name|Long
operator|)
name|hibSession
operator|.
name|save
argument_list|(
name|minor
argument_list|)
argument_list|)
expr_stmt|;
name|ChangeLog
operator|.
name|addChange
argument_list|(
name|hibSession
argument_list|,
name|context
argument_list|,
name|minor
argument_list|,
name|minor
operator|.
name|getCode
argument_list|()
operator|+
literal|" "
operator|+
name|minor
operator|.
name|getName
argument_list|()
argument_list|,
name|Source
operator|.
name|SIMPLE_EDIT
argument_list|,
name|Operation
operator|.
name|CREATE
argument_list|,
literal|null
argument_list|,
literal|null
argument_list|)
expr_stmt|;
block|}
specifier|protected
name|void
name|update
parameter_list|(
name|PosMinor
name|minor
parameter_list|,
name|Record
name|record
parameter_list|,
name|SessionContext
name|context
parameter_list|,
name|Session
name|hibSession
parameter_list|)
block|{
if|if
condition|(
name|minor
operator|==
literal|null
condition|)
return|return;
name|boolean
name|changed
init|=
operator|!
name|ToolBox
operator|.
name|equals
argument_list|(
name|minor
operator|.
name|getExternalUniqueId
argument_list|()
argument_list|,
name|record
operator|.
name|getField
argument_list|(
literal|0
argument_list|)
argument_list|)
operator|||
operator|!
name|ToolBox
operator|.
name|equals
argument_list|(
name|minor
operator|.
name|getCode
argument_list|()
argument_list|,
name|record
operator|.
name|getField
argument_list|(
literal|1
argument_list|)
argument_list|)
operator|||
operator|!
name|ToolBox
operator|.
name|equals
argument_list|(
name|minor
operator|.
name|getName
argument_list|()
argument_list|,
name|record
operator|.
name|getField
argument_list|(
literal|2
argument_list|)
argument_list|)
decl_stmt|;
name|minor
operator|.
name|setExternalUniqueId
argument_list|(
name|record
operator|.
name|getField
argument_list|(
literal|0
argument_list|)
argument_list|)
expr_stmt|;
name|minor
operator|.
name|setCode
argument_list|(
name|record
operator|.
name|getField
argument_list|(
literal|1
argument_list|)
argument_list|)
expr_stmt|;
name|minor
operator|.
name|setName
argument_list|(
name|record
operator|.
name|getField
argument_list|(
literal|2
argument_list|)
argument_list|)
expr_stmt|;
name|Set
argument_list|<
name|AcademicArea
argument_list|>
name|delete
init|=
operator|new
name|HashSet
argument_list|<
name|AcademicArea
argument_list|>
argument_list|(
name|minor
operator|.
name|getAcademicAreas
argument_list|()
argument_list|)
decl_stmt|;
for|for
control|(
name|String
name|areaId
range|:
name|record
operator|.
name|getValues
argument_list|(
literal|3
argument_list|)
control|)
block|{
name|AcademicArea
name|area
init|=
name|AcademicAreaDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|get
argument_list|(
name|Long
operator|.
name|valueOf
argument_list|(
name|areaId
argument_list|)
argument_list|,
name|hibSession
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|delete
operator|.
name|remove
argument_list|(
name|area
argument_list|)
condition|)
block|{
name|minor
operator|.
name|getAcademicAreas
argument_list|()
operator|.
name|add
argument_list|(
name|area
argument_list|)
expr_stmt|;
name|area
operator|.
name|getPosMinors
argument_list|()
operator|.
name|add
argument_list|(
name|minor
argument_list|)
expr_stmt|;
name|changed
operator|=
literal|true
expr_stmt|;
block|}
block|}
for|for
control|(
name|AcademicArea
name|area
range|:
name|delete
control|)
block|{
name|minor
operator|.
name|getAcademicAreas
argument_list|()
operator|.
name|remove
argument_list|(
name|area
argument_list|)
expr_stmt|;
name|area
operator|.
name|getPosMinors
argument_list|()
operator|.
name|remove
argument_list|(
name|minor
argument_list|)
expr_stmt|;
name|changed
operator|=
literal|true
expr_stmt|;
block|}
name|hibSession
operator|.
name|saveOrUpdate
argument_list|(
name|minor
argument_list|)
expr_stmt|;
if|if
condition|(
name|changed
condition|)
name|ChangeLog
operator|.
name|addChange
argument_list|(
name|hibSession
argument_list|,
name|context
argument_list|,
name|minor
argument_list|,
name|minor
operator|.
name|getCode
argument_list|()
operator|+
literal|" "
operator|+
name|minor
operator|.
name|getName
argument_list|()
argument_list|,
name|Source
operator|.
name|SIMPLE_EDIT
argument_list|,
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
annotation|@
name|Override
annotation|@
name|PreAuthorize
argument_list|(
literal|"checkPermission('MinorEdit')"
argument_list|)
specifier|public
name|void
name|update
parameter_list|(
name|Record
name|record
parameter_list|,
name|SessionContext
name|context
parameter_list|,
name|Session
name|hibSession
parameter_list|)
block|{
name|update
argument_list|(
name|PosMinorDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|get
argument_list|(
name|record
operator|.
name|getUniqueId
argument_list|()
argument_list|,
name|hibSession
argument_list|)
argument_list|,
name|record
argument_list|,
name|context
argument_list|,
name|hibSession
argument_list|)
expr_stmt|;
block|}
specifier|protected
name|void
name|delete
parameter_list|(
name|PosMinor
name|minor
parameter_list|,
name|SessionContext
name|context
parameter_list|,
name|Session
name|hibSession
parameter_list|)
block|{
if|if
condition|(
name|minor
operator|==
literal|null
condition|)
return|return;
name|ChangeLog
operator|.
name|addChange
argument_list|(
name|hibSession
argument_list|,
name|context
argument_list|,
name|minor
argument_list|,
name|minor
operator|.
name|getCode
argument_list|()
operator|+
literal|" "
operator|+
name|minor
operator|.
name|getName
argument_list|()
argument_list|,
name|Source
operator|.
name|SIMPLE_EDIT
argument_list|,
name|Operation
operator|.
name|DELETE
argument_list|,
literal|null
argument_list|,
literal|null
argument_list|)
expr_stmt|;
name|hibSession
operator|.
name|delete
argument_list|(
name|minor
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
annotation|@
name|PreAuthorize
argument_list|(
literal|"checkPermission('MinorEdit')"
argument_list|)
specifier|public
name|void
name|delete
parameter_list|(
name|Record
name|record
parameter_list|,
name|SessionContext
name|context
parameter_list|,
name|Session
name|hibSession
parameter_list|)
block|{
name|delete
argument_list|(
name|PosMinorDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|get
argument_list|(
name|record
operator|.
name|getUniqueId
argument_list|()
argument_list|,
name|hibSession
argument_list|)
argument_list|,
name|context
argument_list|,
name|hibSession
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

