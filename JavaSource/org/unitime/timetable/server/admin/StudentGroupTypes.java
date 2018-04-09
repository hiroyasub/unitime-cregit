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
name|StudentGroupType
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
name|StudentGroupTypeDAO
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
literal|"gwtAdminTable[type=stdgrtypes]"
argument_list|)
specifier|public
class|class
name|StudentGroupTypes
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
name|pageStudentGroupType
argument_list|()
argument_list|,
name|MESSAGES
operator|.
name|pageStudentGroupTypes
argument_list|()
argument_list|)
return|;
block|}
annotation|@
name|Override
annotation|@
name|PreAuthorize
argument_list|(
literal|"checkPermission('StudentGroupTypes')"
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
name|allow
init|=
operator|new
name|ArrayList
argument_list|<
name|ListItem
argument_list|>
argument_list|()
decl_stmt|;
name|allow
operator|.
name|add
argument_list|(
operator|new
name|ListItem
argument_list|(
name|String
operator|.
name|valueOf
argument_list|(
name|StudentGroupType
operator|.
name|AllowDisabledSection
operator|.
name|NotAllowed
operator|.
name|ordinal
argument_list|()
argument_list|)
argument_list|,
name|MESSAGES
operator|.
name|itemAllowDisabledSectionsNotAllowed
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|allow
operator|.
name|add
argument_list|(
operator|new
name|ListItem
argument_list|(
name|String
operator|.
name|valueOf
argument_list|(
name|StudentGroupType
operator|.
name|AllowDisabledSection
operator|.
name|WithGroupReservation
operator|.
name|ordinal
argument_list|()
argument_list|)
argument_list|,
name|MESSAGES
operator|.
name|itemAllowDisabledSectionsAllowedReservation
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|allow
operator|.
name|add
argument_list|(
operator|new
name|ListItem
argument_list|(
name|String
operator|.
name|valueOf
argument_list|(
name|StudentGroupType
operator|.
name|AllowDisabledSection
operator|.
name|AlwaysAllowed
operator|.
name|ordinal
argument_list|()
argument_list|)
argument_list|,
name|MESSAGES
operator|.
name|itemAllowDisabledSectionsAlwaysAllowed
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
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
name|fieldCode
argument_list|()
argument_list|,
name|FieldType
operator|.
name|text
argument_list|,
literal|150
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
literal|400
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
name|fieldKeepTogether
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
name|fieldAllowDisabledSections
argument_list|()
argument_list|,
name|FieldType
operator|.
name|list
argument_list|,
literal|100
argument_list|,
name|allow
argument_list|,
name|Flag
operator|.
name|NOT_EMPTY
argument_list|)
argument_list|,
operator|new
name|Field
argument_list|(
name|MESSAGES
operator|.
name|fieldAdvisorsCanSet
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
literal|1
argument_list|)
expr_stmt|;
name|Set
argument_list|<
name|Long
argument_list|>
name|used
init|=
operator|new
name|HashSet
argument_list|<
name|Long
argument_list|>
argument_list|(
name|StudentGroupTypeDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select distinct g.type.uniqueId from StudentGroup g"
argument_list|)
operator|.
name|setCacheable
argument_list|(
literal|true
argument_list|)
operator|.
name|list
argument_list|()
argument_list|)
decl_stmt|;
for|for
control|(
name|StudentGroupType
name|type
range|:
name|StudentGroupTypeDAO
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
name|type
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
name|type
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
name|type
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
name|type
operator|.
name|isKeepTogether
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
name|type
operator|.
name|getAllowDisabled
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
name|r
operator|.
name|setField
argument_list|(
literal|4
argument_list|,
name|type
operator|.
name|isAdvisorsCanSet
argument_list|()
condition|?
literal|"true"
else|:
literal|"false"
argument_list|)
expr_stmt|;
name|r
operator|.
name|setDeletable
argument_list|(
operator|!
name|used
operator|.
name|contains
argument_list|(
name|type
operator|.
name|getUniqueId
argument_list|()
argument_list|)
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
name|StudentGroupTypeEdit
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
literal|"checkPermission('StudentGroupTypeEdit')"
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
name|StudentGroupType
name|type
range|:
name|StudentGroupTypeDAO
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
literal|"checkPermission('StudentGroupTypeEdit')"
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
name|StudentGroupType
name|type
init|=
operator|new
name|StudentGroupType
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
name|setKeepTogether
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
name|setAllowDisabled
argument_list|(
name|record
operator|.
name|getField
argument_list|(
literal|3
argument_list|)
operator|==
literal|null
condition|?
literal|0
else|:
name|Short
operator|.
name|valueOf
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
name|type
operator|.
name|setAdvisorsCanSet
argument_list|(
literal|"true"
operator|.
name|equals
argument_list|(
name|record
operator|.
name|getField
argument_list|(
literal|4
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
operator|+
literal|" "
operator|+
name|type
operator|.
name|getLabel
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
name|StudentGroupType
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
name|boolean
name|changed
init|=
operator|!
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
operator|||
operator|!
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
operator|||
operator|!
name|ToolBox
operator|.
name|equals
argument_list|(
name|type
operator|.
name|isKeepTogether
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
operator|||
operator|!
name|ToolBox
operator|.
name|equals
argument_list|(
name|type
operator|.
name|getAllowDisabled
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|,
name|record
operator|.
name|getField
argument_list|(
literal|3
argument_list|)
argument_list|)
operator|||
operator|!
name|ToolBox
operator|.
name|equals
argument_list|(
name|type
operator|.
name|isAdvisorsCanSet
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
literal|4
argument_list|)
argument_list|)
argument_list|)
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
name|setKeepTogether
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
name|setAllowDisabled
argument_list|(
name|record
operator|.
name|getField
argument_list|(
literal|3
argument_list|)
operator|==
literal|null
condition|?
literal|0
else|:
name|Short
operator|.
name|valueOf
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
name|type
operator|.
name|setAdvisorsCanSet
argument_list|(
literal|"true"
operator|.
name|equals
argument_list|(
name|record
operator|.
name|getField
argument_list|(
literal|4
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
name|type
argument_list|,
name|type
operator|.
name|getReference
argument_list|()
operator|+
literal|" "
operator|+
name|type
operator|.
name|getLabel
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
literal|"checkPermission('StudentGroupTypeEdit')"
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
name|StudentGroupTypeDAO
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
name|StudentGroupType
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
operator|+
literal|" "
operator|+
name|type
operator|.
name|getLabel
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
literal|"checkPermission('StudentGroupTypeEdit')"
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
name|StudentGroupTypeDAO
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

