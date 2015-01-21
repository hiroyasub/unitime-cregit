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
name|List
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
name|ManagerRole
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
name|RoomTypeOption
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
name|UserData
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
name|TimetableManagerDAO
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
literal|"gwtAdminTable[type=eventDefault]"
argument_list|)
specifier|public
class|class
name|EventDefaults
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
name|pageEventDefault
argument_list|()
argument_list|,
name|MESSAGES
operator|.
name|pageEventDefaults
argument_list|()
argument_list|)
return|;
block|}
annotation|@
name|Override
annotation|@
name|PreAuthorize
argument_list|(
literal|"checkPermission('EventDefaults')"
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
name|states
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
name|RoomTypeOption
operator|.
name|Status
name|state
range|:
name|RoomTypeOption
operator|.
name|Status
operator|.
name|values
argument_list|()
control|)
block|{
name|states
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
name|state
operator|.
name|ordinal
argument_list|()
argument_list|)
argument_list|,
name|state
operator|.
name|toString
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
name|fieldManager
argument_list|()
argument_list|,
name|FieldType
operator|.
name|text
argument_list|,
literal|100
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
name|fieldReference
argument_list|()
argument_list|,
name|FieldType
operator|.
name|text
argument_list|,
literal|100
argument_list|,
name|Flag
operator|.
name|READ_ONLY
argument_list|,
name|Flag
operator|.
name|HIDDEN
argument_list|)
argument_list|,
operator|new
name|Field
argument_list|(
name|MESSAGES
operator|.
name|fieldAdditionalEmails
argument_list|()
argument_list|,
name|FieldType
operator|.
name|textarea
argument_list|,
literal|50
argument_list|,
literal|3
argument_list|,
literal|4000
argument_list|)
argument_list|)
decl_stmt|;
name|data
operator|.
name|setSortBy
argument_list|(
literal|0
argument_list|)
expr_stmt|;
name|data
operator|.
name|setAddable
argument_list|(
literal|false
argument_list|)
expr_stmt|;
if|if
condition|(
name|context
operator|.
name|hasPermission
argument_list|(
name|Right
operator|.
name|EventDefaultsEditOther
argument_list|)
condition|)
block|{
for|for
control|(
name|TimetableManager
name|manager
range|:
name|TimetableManagerDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|findAll
argument_list|()
control|)
block|{
if|if
condition|(
operator|!
name|context
operator|.
name|hasPermission
argument_list|(
name|Right
operator|.
name|SessionIndependent
argument_list|)
condition|)
block|{
name|boolean
name|hasDepartment
init|=
literal|false
decl_stmt|;
for|for
control|(
name|Department
name|dept
range|:
name|manager
operator|.
name|getDepartments
argument_list|()
control|)
block|{
if|if
condition|(
name|context
operator|.
name|getUser
argument_list|()
operator|.
name|getCurrentAuthority
argument_list|()
operator|.
name|hasQualifier
argument_list|(
name|dept
argument_list|)
condition|)
block|{
name|hasDepartment
operator|=
literal|true
expr_stmt|;
break|break;
block|}
block|}
if|if
condition|(
operator|!
name|hasDepartment
condition|)
continue|continue;
block|}
name|boolean
name|hasRole
init|=
literal|false
decl_stmt|;
for|for
control|(
name|ManagerRole
name|role
range|:
name|manager
operator|.
name|getManagerRoles
argument_list|()
control|)
block|{
if|if
condition|(
name|role
operator|.
name|getRole
argument_list|()
operator|.
name|hasRight
argument_list|(
name|Right
operator|.
name|EventDefaults
argument_list|)
condition|)
block|{
name|hasRole
operator|=
literal|true
expr_stmt|;
break|break;
block|}
block|}
if|if
condition|(
operator|!
name|hasRole
condition|)
continue|continue;
name|Record
name|r
init|=
name|data
operator|.
name|addRecord
argument_list|(
name|manager
operator|.
name|getUniqueId
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
name|manager
operator|.
name|getName
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
name|manager
operator|.
name|getExternalUniqueId
argument_list|()
argument_list|,
literal|false
argument_list|)
expr_stmt|;
name|r
operator|.
name|setField
argument_list|(
literal|2
argument_list|,
name|UserData
operator|.
name|getProperty
argument_list|(
name|manager
operator|.
name|getExternalUniqueId
argument_list|()
argument_list|,
literal|"Defaults[AddEvent.emails]"
argument_list|,
literal|""
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
name|data
operator|.
name|getFields
argument_list|()
index|[
literal|0
index|]
operator|=
operator|new
name|Field
argument_list|(
name|MESSAGES
operator|.
name|fieldManager
argument_list|()
argument_list|,
name|FieldType
operator|.
name|text
argument_list|,
literal|100
argument_list|,
name|Flag
operator|.
name|READ_ONLY
argument_list|,
name|Flag
operator|.
name|HIDDEN
argument_list|)
expr_stmt|;
name|TimetableManager
name|manager
init|=
name|TimetableManager
operator|.
name|findByExternalId
argument_list|(
name|context
operator|.
name|getUser
argument_list|()
operator|.
name|getExternalUserId
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|manager
operator|!=
literal|null
condition|)
block|{
name|Record
name|r
init|=
name|data
operator|.
name|addRecord
argument_list|(
name|manager
operator|.
name|getUniqueId
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
name|manager
operator|.
name|getName
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
name|manager
operator|.
name|getExternalUniqueId
argument_list|()
argument_list|,
literal|false
argument_list|)
expr_stmt|;
name|r
operator|.
name|setField
argument_list|(
literal|2
argument_list|,
name|UserData
operator|.
name|getProperty
argument_list|(
name|manager
operator|.
name|getExternalUniqueId
argument_list|()
argument_list|,
literal|"Defaults[AddEvent.emails]"
argument_list|,
literal|""
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
name|data
operator|.
name|setEditable
argument_list|(
literal|true
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
literal|"checkPermission('EventDefaults')"
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
name|TimetableManager
name|manager
range|:
name|TimetableManagerDAO
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
name|manager
operator|.
name|getUniqueId
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|r
operator|!=
literal|null
condition|)
name|update
argument_list|(
name|r
argument_list|,
name|context
argument_list|,
name|hibSession
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Override
annotation|@
name|PreAuthorize
argument_list|(
literal|"checkPermission('HasRole')"
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
literal|"checkPermission('EventDefaults')"
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
name|String
name|extId
init|=
name|record
operator|.
name|getField
argument_list|(
literal|1
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|context
operator|.
name|getUser
argument_list|()
operator|.
name|getExternalUserId
argument_list|()
operator|.
name|equals
argument_list|(
name|extId
argument_list|)
condition|)
name|context
operator|.
name|checkPermission
argument_list|(
name|Right
operator|.
name|EventDefaultsEditOther
argument_list|)
expr_stmt|;
name|UserData
operator|.
name|setProperty
argument_list|(
name|extId
argument_list|,
literal|"Defaults[AddEvent.emails]"
argument_list|,
name|record
operator|.
name|getField
argument_list|(
literal|2
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
annotation|@
name|PreAuthorize
argument_list|(
literal|"checkPermission('HasRole')"
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

