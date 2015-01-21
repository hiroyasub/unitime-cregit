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
name|command
operator|.
name|client
operator|.
name|GwtRpcException
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
name|Roles
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
literal|"gwtAdminTable[type=permissions]"
argument_list|)
specifier|public
class|class
name|Permissions
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
name|pagePermission
argument_list|()
argument_list|,
name|MESSAGES
operator|.
name|pagePermissions
argument_list|()
argument_list|)
return|;
block|}
annotation|@
name|Override
annotation|@
name|PreAuthorize
argument_list|(
literal|"checkPermission('Permissions')"
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
name|Roles
argument_list|>
name|roles
init|=
operator|new
name|ArrayList
argument_list|<
name|Roles
argument_list|>
argument_list|(
name|Roles
operator|.
name|findAll
argument_list|(
literal|false
argument_list|)
argument_list|)
decl_stmt|;
name|Field
index|[]
name|fields
init|=
operator|new
name|Field
index|[
literal|2
operator|+
name|roles
operator|.
name|size
argument_list|()
index|]
decl_stmt|;
name|fields
index|[
literal|0
index|]
operator|=
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
literal|160
argument_list|,
literal|200
argument_list|,
name|Flag
operator|.
name|READ_ONLY
argument_list|)
expr_stmt|;
name|fields
index|[
literal|1
index|]
operator|=
operator|new
name|Field
argument_list|(
name|MESSAGES
operator|.
name|fieldLevel
argument_list|()
argument_list|,
name|FieldType
operator|.
name|text
argument_list|,
literal|160
argument_list|,
literal|200
argument_list|,
name|Flag
operator|.
name|READ_ONLY
argument_list|)
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
name|roles
operator|.
name|size
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|fields
index|[
literal|2
operator|+
name|i
index|]
operator|=
operator|new
name|Field
argument_list|(
name|roles
operator|.
name|get
argument_list|(
name|i
argument_list|)
operator|.
name|getReference
argument_list|()
argument_list|,
name|FieldType
operator|.
name|toggle
argument_list|,
literal|40
argument_list|,
name|roles
operator|.
name|get
argument_list|(
name|i
argument_list|)
operator|.
name|isEnabled
argument_list|()
condition|?
literal|null
else|:
name|Flag
operator|.
name|HIDDEN
argument_list|)
expr_stmt|;
block|}
name|SimpleEditInterface
name|data
init|=
operator|new
name|SimpleEditInterface
argument_list|(
name|fields
argument_list|)
decl_stmt|;
name|data
operator|.
name|setSortBy
argument_list|(
operator|-
literal|1
argument_list|)
expr_stmt|;
name|data
operator|.
name|setAddable
argument_list|(
literal|false
argument_list|)
expr_stmt|;
for|for
control|(
name|Right
name|right
range|:
name|Right
operator|.
name|values
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
operator|(
name|long
operator|)
name|right
operator|.
name|ordinal
argument_list|()
argument_list|,
literal|false
argument_list|)
decl_stmt|;
name|r
operator|.
name|setField
argument_list|(
literal|0
argument_list|,
name|right
operator|.
name|toString
argument_list|()
argument_list|,
literal|false
argument_list|)
expr_stmt|;
name|r
operator|.
name|setField
argument_list|(
literal|1
argument_list|,
name|right
operator|.
name|hasType
argument_list|()
condition|?
name|right
operator|.
name|type
argument_list|()
operator|.
name|getSimpleName
argument_list|()
operator|.
name|replaceAll
argument_list|(
literal|"(\\p{Ll})(\\p{Lu})"
argument_list|,
literal|"$1 $2"
argument_list|)
operator|.
name|replace
argument_list|(
literal|"_"
argument_list|,
literal|" "
argument_list|)
else|:
name|MESSAGES
operator|.
name|levelGlobal
argument_list|()
argument_list|,
literal|false
argument_list|)
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
name|roles
operator|.
name|size
argument_list|()
condition|;
name|i
operator|++
control|)
name|r
operator|.
name|setField
argument_list|(
literal|2
operator|+
name|i
argument_list|,
name|roles
operator|.
name|get
argument_list|(
name|i
argument_list|)
operator|.
name|getRights
argument_list|()
operator|.
name|contains
argument_list|(
name|right
operator|.
name|name
argument_list|()
argument_list|)
condition|?
literal|"true"
else|:
literal|"false"
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
name|PermissionEdit
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
literal|"checkPermission('PermissionEdit')"
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
name|List
argument_list|<
name|Roles
argument_list|>
name|roles
init|=
operator|new
name|ArrayList
argument_list|<
name|Roles
argument_list|>
argument_list|(
name|Roles
operator|.
name|findAll
argument_list|(
literal|false
argument_list|)
argument_list|)
decl_stmt|;
name|Set
argument_list|<
name|Roles
argument_list|>
name|changed
init|=
operator|new
name|HashSet
argument_list|<
name|Roles
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|Record
name|r
range|:
name|data
operator|.
name|getRecords
argument_list|()
control|)
block|{
name|Right
name|right
init|=
name|Right
operator|.
name|values
argument_list|()
index|[
operator|(
name|int
operator|)
name|r
operator|.
name|getUniqueId
argument_list|()
operator|.
name|longValue
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
name|roles
operator|.
name|size
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|boolean
name|newValue
init|=
literal|"true"
operator|.
name|equals
argument_list|(
name|r
operator|.
name|getField
argument_list|(
literal|2
operator|+
name|i
argument_list|)
argument_list|)
decl_stmt|;
name|boolean
name|oldValue
init|=
name|roles
operator|.
name|get
argument_list|(
name|i
argument_list|)
operator|.
name|getRights
argument_list|()
operator|.
name|contains
argument_list|(
name|right
operator|.
name|name
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|newValue
operator|!=
name|oldValue
condition|)
block|{
name|changed
operator|.
name|add
argument_list|(
name|roles
operator|.
name|get
argument_list|(
name|i
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|newValue
condition|)
name|roles
operator|.
name|get
argument_list|(
name|i
argument_list|)
operator|.
name|getRights
argument_list|()
operator|.
name|add
argument_list|(
name|right
operator|.
name|name
argument_list|()
argument_list|)
expr_stmt|;
else|else
name|roles
operator|.
name|get
argument_list|(
name|i
argument_list|)
operator|.
name|getRights
argument_list|()
operator|.
name|remove
argument_list|(
name|right
operator|.
name|name
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
for|for
control|(
name|Roles
name|role
range|:
name|changed
control|)
block|{
name|hibSession
operator|.
name|saveOrUpdate
argument_list|(
name|role
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
name|role
argument_list|,
name|role
operator|.
name|getAbbv
argument_list|()
operator|+
literal|" permissions"
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
annotation|@
name|Override
annotation|@
name|PreAuthorize
argument_list|(
literal|"checkPermission('PermissionEdit')"
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
throw|throw
operator|new
name|GwtRpcException
argument_list|(
name|MESSAGES
operator|.
name|errorOperationNotSupported
argument_list|()
argument_list|)
throw|;
block|}
annotation|@
name|Override
annotation|@
name|PreAuthorize
argument_list|(
literal|"checkPermission('PermissionEdit')"
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
name|List
argument_list|<
name|Roles
argument_list|>
name|roles
init|=
operator|new
name|ArrayList
argument_list|<
name|Roles
argument_list|>
argument_list|(
name|Roles
operator|.
name|findAll
argument_list|(
literal|false
argument_list|)
argument_list|)
decl_stmt|;
name|Set
argument_list|<
name|Roles
argument_list|>
name|changed
init|=
operator|new
name|HashSet
argument_list|<
name|Roles
argument_list|>
argument_list|()
decl_stmt|;
name|Right
name|right
init|=
name|Right
operator|.
name|values
argument_list|()
index|[
operator|(
name|int
operator|)
name|record
operator|.
name|getUniqueId
argument_list|()
operator|.
name|longValue
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
name|roles
operator|.
name|size
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|boolean
name|newValue
init|=
literal|"true"
operator|.
name|equals
argument_list|(
name|record
operator|.
name|getField
argument_list|(
literal|2
operator|+
name|i
argument_list|)
argument_list|)
decl_stmt|;
name|boolean
name|oldValue
init|=
name|roles
operator|.
name|get
argument_list|(
name|i
argument_list|)
operator|.
name|getRights
argument_list|()
operator|.
name|contains
argument_list|(
name|right
operator|.
name|name
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|newValue
operator|!=
name|oldValue
condition|)
block|{
name|changed
operator|.
name|add
argument_list|(
name|roles
operator|.
name|get
argument_list|(
name|i
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|newValue
condition|)
name|roles
operator|.
name|get
argument_list|(
name|i
argument_list|)
operator|.
name|getRights
argument_list|()
operator|.
name|add
argument_list|(
name|right
operator|.
name|name
argument_list|()
argument_list|)
expr_stmt|;
else|else
name|roles
operator|.
name|get
argument_list|(
name|i
argument_list|)
operator|.
name|getRights
argument_list|()
operator|.
name|remove
argument_list|(
name|right
operator|.
name|name
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
for|for
control|(
name|Roles
name|role
range|:
name|changed
control|)
block|{
name|hibSession
operator|.
name|saveOrUpdate
argument_list|(
name|role
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
name|role
argument_list|,
name|role
operator|.
name|getAbbv
argument_list|()
operator|+
literal|" permissions"
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
annotation|@
name|Override
annotation|@
name|PreAuthorize
argument_list|(
literal|"checkPermission('PermissionEdit')"
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
throw|throw
operator|new
name|GwtRpcException
argument_list|(
name|MESSAGES
operator|.
name|errorOperationNotSupported
argument_list|()
argument_list|)
throw|;
block|}
block|}
end_class

end_unit

