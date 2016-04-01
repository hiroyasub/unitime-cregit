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
name|InstructorAttributeType
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
name|InstructorAttributeTypeDAO
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
literal|"gwtAdminTable[type=attributeType]"
argument_list|)
specifier|public
class|class
name|InstructorAttributeTypes
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
name|pageInstructorAttributeType
argument_list|()
argument_list|,
name|MESSAGES
operator|.
name|pageInstructorAttributeTypes
argument_list|()
argument_list|)
return|;
block|}
annotation|@
name|Override
annotation|@
name|PreAuthorize
argument_list|(
literal|"checkPermission('InstructorAttributeTypes')"
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
name|fieldAbbreviation
argument_list|()
argument_list|,
name|FieldType
operator|.
name|text
argument_list|,
literal|160
argument_list|,
literal|20
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
literal|60
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
name|fieldConjunctive
argument_list|()
argument_list|,
name|FieldType
operator|.
name|toggle
argument_list|,
literal|40
argument_list|)
argument_list|,
operator|new
name|Field
argument_list|(
name|MESSAGES
operator|.
name|fieldRequiredAttribute
argument_list|()
argument_list|,
name|FieldType
operator|.
name|toggle
argument_list|,
literal|40
argument_list|)
argument_list|)
decl_stmt|;
name|data
operator|.
name|setSortBy
argument_list|(
literal|2
argument_list|,
literal|1
argument_list|)
expr_stmt|;
for|for
control|(
name|InstructorAttributeType
name|atype
range|:
name|InstructorAttributeTypeDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|findAll
argument_list|()
control|)
block|{
name|Record
name|r
init|=
name|data
operator|.
name|addRecord
argument_list|(
name|atype
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
name|atype
operator|.
name|getReference
argument_list|()
argument_list|)
expr_stmt|;
name|r
operator|.
name|setField
argument_list|(
literal|1
argument_list|,
name|atype
operator|.
name|getLabel
argument_list|()
argument_list|)
expr_stmt|;
name|r
operator|.
name|setField
argument_list|(
literal|2
argument_list|,
name|atype
operator|.
name|isConjunctive
argument_list|()
condition|?
literal|"true"
else|:
literal|"false"
argument_list|)
expr_stmt|;
name|r
operator|.
name|setField
argument_list|(
literal|3
argument_list|,
name|atype
operator|.
name|isRequired
argument_list|()
condition|?
literal|"true"
else|:
literal|"false"
argument_list|)
expr_stmt|;
name|int
name|used
init|=
operator|(
operator|(
name|Number
operator|)
name|hibSession
operator|.
name|createQuery
argument_list|(
literal|"select count(a) from InstructorAttribute a where a.type.uniqueId = :uniqueId"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"uniqueId"
argument_list|,
name|atype
operator|.
name|getUniqueId
argument_list|()
argument_list|)
operator|.
name|uniqueResult
argument_list|()
operator|)
operator|.
name|intValue
argument_list|()
decl_stmt|;
name|r
operator|.
name|setDeletable
argument_list|(
name|used
operator|==
literal|0
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
name|InstructorAttributeTypeEdit
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
literal|"checkPermission('InstructorAttributeTypeEdit')"
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
name|InstructorAttributeType
name|type
range|:
name|InstructorAttributeTypeDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|findAll
argument_list|(
name|hibSession
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
name|type
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
name|delete
argument_list|(
name|type
argument_list|,
name|context
argument_list|,
name|hibSession
argument_list|)
expr_stmt|;
else|else
name|update
argument_list|(
name|type
argument_list|,
name|r
argument_list|,
name|context
argument_list|,
name|hibSession
argument_list|)
expr_stmt|;
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
name|save
argument_list|(
name|r
argument_list|,
name|context
argument_list|,
name|hibSession
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
annotation|@
name|PreAuthorize
argument_list|(
literal|"checkPermission('InstructorAttributeTypeEdit')"
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
name|InstructorAttributeType
name|type
init|=
operator|new
name|InstructorAttributeType
argument_list|()
decl_stmt|;
name|type
operator|.
name|setReference
argument_list|(
name|record
operator|.
name|getField
argument_list|(
literal|0
argument_list|)
argument_list|)
expr_stmt|;
name|type
operator|.
name|setLabel
argument_list|(
name|record
operator|.
name|getField
argument_list|(
literal|1
argument_list|)
argument_list|)
expr_stmt|;
name|type
operator|.
name|setConjunctive
argument_list|(
literal|"true"
operator|.
name|equals
argument_list|(
name|record
operator|.
name|getField
argument_list|(
literal|2
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|type
operator|.
name|setRequired
argument_list|(
literal|"true"
operator|.
name|equals
argument_list|(
name|record
operator|.
name|getField
argument_list|(
literal|3
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
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
name|type
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
name|type
argument_list|,
name|type
operator|.
name|getReference
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
name|InstructorAttributeType
name|type
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
name|type
operator|==
literal|null
condition|)
return|return;
if|if
condition|(
name|ToolBox
operator|.
name|equals
argument_list|(
name|type
operator|.
name|getReference
argument_list|()
argument_list|,
name|record
operator|.
name|getField
argument_list|(
literal|0
argument_list|)
argument_list|)
operator|&&
name|ToolBox
operator|.
name|equals
argument_list|(
name|type
operator|.
name|getLabel
argument_list|()
argument_list|,
name|record
operator|.
name|getField
argument_list|(
literal|1
argument_list|)
argument_list|)
operator|&&
name|ToolBox
operator|.
name|equals
argument_list|(
name|type
operator|.
name|isConjunctive
argument_list|()
argument_list|,
literal|"true"
operator|.
name|equals
argument_list|(
name|record
operator|.
name|getField
argument_list|(
literal|2
argument_list|)
argument_list|)
argument_list|)
operator|&&
name|ToolBox
operator|.
name|equals
argument_list|(
name|type
operator|.
name|isRequired
argument_list|()
argument_list|,
literal|"true"
operator|.
name|equals
argument_list|(
name|record
operator|.
name|getField
argument_list|(
literal|3
argument_list|)
argument_list|)
argument_list|)
condition|)
return|return;
name|type
operator|.
name|setReference
argument_list|(
name|record
operator|.
name|getField
argument_list|(
literal|0
argument_list|)
argument_list|)
expr_stmt|;
name|type
operator|.
name|setLabel
argument_list|(
name|record
operator|.
name|getField
argument_list|(
literal|1
argument_list|)
argument_list|)
expr_stmt|;
name|type
operator|.
name|setConjunctive
argument_list|(
literal|"true"
operator|.
name|equals
argument_list|(
name|record
operator|.
name|getField
argument_list|(
literal|2
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|type
operator|.
name|setRequired
argument_list|(
literal|"true"
operator|.
name|equals
argument_list|(
name|record
operator|.
name|getField
argument_list|(
literal|3
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|hibSession
operator|.
name|saveOrUpdate
argument_list|(
name|type
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
name|type
argument_list|,
name|type
operator|.
name|getReference
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
literal|"checkPermission('InstructorAttributeTypeEdit')"
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
name|InstructorAttributeTypeDAO
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
name|InstructorAttributeType
name|type
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
name|type
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
name|type
argument_list|,
name|type
operator|.
name|getReference
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
name|type
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
annotation|@
name|PreAuthorize
argument_list|(
literal|"checkPermission('InstructorAttributeTypeEdit')"
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
name|InstructorAttributeTypeDAO
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

