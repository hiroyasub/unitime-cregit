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
name|EventServiceProvider
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
name|dao
operator|.
name|EventServiceProviderDAO
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
literal|"gwtAdminTable[type=serviceProviders]"
argument_list|)
specifier|public
class|class
name|ServiceProviders
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
name|pageServiceProvider
argument_list|()
argument_list|,
name|MESSAGES
operator|.
name|pageServiceProviders
argument_list|()
argument_list|)
return|;
block|}
annotation|@
name|Override
annotation|@
name|PreAuthorize
argument_list|(
literal|"checkPermission('EventServiceProviders')"
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
name|Long
name|sessionId
init|=
name|context
operator|.
name|getUser
argument_list|()
operator|.
name|getCurrentAcademicSessionId
argument_list|()
decl_stmt|;
name|List
argument_list|<
name|ListItem
argument_list|>
name|appliesTo
init|=
operator|new
name|ArrayList
argument_list|<
name|ListItem
argument_list|>
argument_list|()
decl_stmt|;
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
name|fieldMessage
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
literal|1000
argument_list|)
argument_list|,
operator|new
name|Field
argument_list|(
name|MESSAGES
operator|.
name|fieldEmailAddress
argument_list|()
argument_list|,
name|FieldType
operator|.
name|text
argument_list|,
literal|300
argument_list|,
literal|200
argument_list|)
argument_list|,
operator|new
name|Field
argument_list|(
name|MESSAGES
operator|.
name|fieldAppliesTo
argument_list|()
argument_list|,
name|FieldType
operator|.
name|list
argument_list|,
literal|300
argument_list|,
name|appliesTo
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
name|fieldAllRooms
argument_list|()
argument_list|,
name|FieldType
operator|.
name|toggle
argument_list|,
literal|40
argument_list|,
name|Flag
operator|.
name|DEFAULT_CHECKED
argument_list|)
argument_list|,
operator|new
name|Field
argument_list|(
name|MESSAGES
operator|.
name|fieldVisible
argument_list|()
argument_list|,
name|FieldType
operator|.
name|toggle
argument_list|,
literal|40
argument_list|,
name|Flag
operator|.
name|DEFAULT_CHECKED
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
name|boolean
name|editGlobal
init|=
name|context
operator|.
name|hasPermission
argument_list|(
name|Right
operator|.
name|EventServiceProviderEditGlobal
argument_list|)
decl_stmt|;
if|if
condition|(
name|editGlobal
condition|)
name|appliesTo
operator|.
name|add
argument_list|(
operator|new
name|ListItem
argument_list|(
literal|"_global"
argument_list|,
name|MESSAGES
operator|.
name|levelGlobal
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|data
operator|.
name|setSessionId
argument_list|(
name|sessionId
argument_list|)
expr_stmt|;
name|data
operator|.
name|setSessionName
argument_list|(
name|SessionDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|get
argument_list|(
name|sessionId
argument_list|)
operator|.
name|getLabel
argument_list|()
argument_list|)
expr_stmt|;
name|boolean
name|editSession
init|=
name|context
operator|.
name|hasPermission
argument_list|(
name|sessionId
argument_list|,
literal|"Session"
argument_list|,
name|Right
operator|.
name|EventServiceProviderEditSession
argument_list|)
decl_stmt|;
if|if
condition|(
name|editSession
condition|)
name|appliesTo
operator|.
name|add
argument_list|(
operator|new
name|ListItem
argument_list|(
literal|"_session"
argument_list|,
name|data
operator|.
name|getSessionName
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|data
operator|.
name|setEditable
argument_list|(
name|editGlobal
operator|||
name|editSession
argument_list|)
expr_stmt|;
name|Set
argument_list|<
name|Department
argument_list|>
name|departments
init|=
name|Department
operator|.
name|getUserDepartments
argument_list|(
name|context
operator|.
name|getUser
argument_list|()
argument_list|)
decl_stmt|;
for|for
control|(
name|Department
name|department
range|:
name|departments
control|)
block|{
if|if
condition|(
operator|!
name|department
operator|.
name|isAllowEvents
argument_list|()
condition|)
continue|continue;
name|boolean
name|editDept
init|=
name|context
operator|.
name|hasPermission
argument_list|(
name|department
argument_list|,
name|Right
operator|.
name|EventServiceProviderEditDepartment
argument_list|)
decl_stmt|;
if|if
condition|(
name|editDept
condition|)
block|{
name|appliesTo
operator|.
name|add
argument_list|(
operator|new
name|ListItem
argument_list|(
name|department
operator|.
name|getDeptCode
argument_list|()
argument_list|,
name|department
operator|.
name|getDeptCode
argument_list|()
operator|+
literal|" - "
operator|+
name|department
operator|.
name|getName
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|data
operator|.
name|setEditable
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
block|}
for|for
control|(
name|EventServiceProvider
name|provider
range|:
operator|(
name|List
argument_list|<
name|EventServiceProvider
argument_list|>
operator|)
name|EventServiceProviderDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"from EventServiceProvider where session is null or session = :sessionId"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"sessionId"
argument_list|,
name|sessionId
argument_list|)
operator|.
name|setCacheable
argument_list|(
literal|true
argument_list|)
operator|.
name|list
argument_list|()
control|)
block|{
if|if
condition|(
name|provider
operator|.
name|getSession
argument_list|()
operator|==
literal|null
condition|)
block|{
comment|// global
name|Record
name|r
init|=
name|data
operator|.
name|addRecord
argument_list|(
name|provider
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
name|provider
operator|.
name|getReference
argument_list|()
argument_list|,
name|editGlobal
argument_list|)
expr_stmt|;
name|r
operator|.
name|setField
argument_list|(
literal|1
argument_list|,
name|provider
operator|.
name|getLabel
argument_list|()
argument_list|,
name|editGlobal
argument_list|)
expr_stmt|;
name|r
operator|.
name|setField
argument_list|(
literal|2
argument_list|,
name|provider
operator|.
name|getNote
argument_list|()
argument_list|,
name|editGlobal
argument_list|)
expr_stmt|;
name|r
operator|.
name|setField
argument_list|(
literal|3
argument_list|,
name|provider
operator|.
name|getEmail
argument_list|()
argument_list|,
name|editGlobal
argument_list|)
expr_stmt|;
name|r
operator|.
name|setField
argument_list|(
literal|4
argument_list|,
name|editGlobal
condition|?
literal|"_global"
else|:
name|MESSAGES
operator|.
name|levelGlobal
argument_list|()
argument_list|,
literal|false
argument_list|)
expr_stmt|;
name|r
operator|.
name|setField
argument_list|(
literal|5
argument_list|,
name|provider
operator|.
name|isAllRooms
argument_list|()
condition|?
literal|"true"
else|:
literal|"false"
argument_list|,
name|editGlobal
argument_list|)
expr_stmt|;
name|r
operator|.
name|setField
argument_list|(
literal|6
argument_list|,
name|provider
operator|.
name|isVisible
argument_list|()
condition|?
literal|"true"
else|:
literal|"false"
argument_list|,
name|editGlobal
argument_list|)
expr_stmt|;
name|r
operator|.
name|setDeletable
argument_list|(
name|editGlobal
operator|&&
operator|!
name|provider
operator|.
name|isUsed
argument_list|()
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|provider
operator|.
name|getDepartment
argument_list|()
operator|==
literal|null
condition|)
block|{
comment|// session
name|Record
name|r
init|=
name|data
operator|.
name|addRecord
argument_list|(
name|provider
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
name|provider
operator|.
name|getReference
argument_list|()
argument_list|,
name|editSession
argument_list|)
expr_stmt|;
name|r
operator|.
name|setField
argument_list|(
literal|1
argument_list|,
name|provider
operator|.
name|getLabel
argument_list|()
argument_list|,
name|editSession
argument_list|)
expr_stmt|;
name|r
operator|.
name|setField
argument_list|(
literal|2
argument_list|,
name|provider
operator|.
name|getNote
argument_list|()
argument_list|,
name|editSession
argument_list|)
expr_stmt|;
name|r
operator|.
name|setField
argument_list|(
literal|3
argument_list|,
name|provider
operator|.
name|getEmail
argument_list|()
argument_list|,
name|editSession
argument_list|)
expr_stmt|;
name|r
operator|.
name|setField
argument_list|(
literal|4
argument_list|,
name|editSession
condition|?
literal|"_session"
else|:
name|data
operator|.
name|getSessionName
argument_list|()
argument_list|,
literal|false
argument_list|)
expr_stmt|;
name|r
operator|.
name|setField
argument_list|(
literal|5
argument_list|,
name|provider
operator|.
name|isAllRooms
argument_list|()
condition|?
literal|"true"
else|:
literal|"false"
argument_list|,
name|editSession
argument_list|)
expr_stmt|;
name|r
operator|.
name|setField
argument_list|(
literal|6
argument_list|,
name|provider
operator|.
name|isVisible
argument_list|()
condition|?
literal|"true"
else|:
literal|"false"
argument_list|,
name|editSession
argument_list|)
expr_stmt|;
name|r
operator|.
name|setDeletable
argument_list|(
name|editSession
operator|&&
operator|!
name|provider
operator|.
name|isUsed
argument_list|()
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|departments
operator|.
name|contains
argument_list|(
name|provider
operator|.
name|getDepartment
argument_list|()
argument_list|)
condition|)
block|{
comment|// departmental
name|boolean
name|editDept
init|=
name|context
operator|.
name|hasPermission
argument_list|(
name|provider
operator|.
name|getDepartment
argument_list|()
argument_list|,
name|Right
operator|.
name|EventServiceProviderEditDepartment
argument_list|)
decl_stmt|;
name|Record
name|r
init|=
name|data
operator|.
name|addRecord
argument_list|(
name|provider
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
name|provider
operator|.
name|getReference
argument_list|()
argument_list|,
name|editDept
argument_list|)
expr_stmt|;
name|r
operator|.
name|setField
argument_list|(
literal|1
argument_list|,
name|provider
operator|.
name|getLabel
argument_list|()
argument_list|,
name|editDept
argument_list|)
expr_stmt|;
name|r
operator|.
name|setField
argument_list|(
literal|2
argument_list|,
name|provider
operator|.
name|getNote
argument_list|()
argument_list|,
name|editDept
argument_list|)
expr_stmt|;
name|r
operator|.
name|setField
argument_list|(
literal|3
argument_list|,
name|provider
operator|.
name|getEmail
argument_list|()
argument_list|,
name|editDept
argument_list|)
expr_stmt|;
name|r
operator|.
name|setField
argument_list|(
literal|4
argument_list|,
name|editDept
condition|?
name|provider
operator|.
name|getDepartment
argument_list|()
operator|.
name|getDeptCode
argument_list|()
else|:
name|provider
operator|.
name|getDepartment
argument_list|()
operator|.
name|getDeptCode
argument_list|()
operator|+
literal|" - "
operator|+
name|provider
operator|.
name|getDepartment
argument_list|()
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
literal|5
argument_list|,
name|provider
operator|.
name|isAllRooms
argument_list|()
condition|?
literal|"true"
else|:
literal|"false"
argument_list|,
name|editDept
argument_list|)
expr_stmt|;
name|r
operator|.
name|setField
argument_list|(
literal|6
argument_list|,
name|provider
operator|.
name|isVisible
argument_list|()
condition|?
literal|"true"
else|:
literal|"false"
argument_list|,
name|editDept
argument_list|)
expr_stmt|;
name|r
operator|.
name|setDeletable
argument_list|(
name|editDept
operator|&&
operator|!
name|provider
operator|.
name|isUsed
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|data
return|;
block|}
annotation|@
name|Override
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
name|EventServiceProvider
name|provider
range|:
operator|(
name|List
argument_list|<
name|EventServiceProvider
argument_list|>
operator|)
name|EventServiceProviderDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"from EventServiceProvider where session is null or session = :sessionId"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"sessionId"
argument_list|,
name|context
operator|.
name|getUser
argument_list|()
operator|.
name|getCurrentAcademicSessionId
argument_list|()
argument_list|)
operator|.
name|setCacheable
argument_list|(
literal|true
argument_list|)
operator|.
name|list
argument_list|()
control|)
block|{
if|if
condition|(
name|provider
operator|.
name|getSession
argument_list|()
operator|==
literal|null
condition|)
block|{
comment|// global
if|if
condition|(
operator|!
name|context
operator|.
name|hasPermission
argument_list|(
name|Right
operator|.
name|EventServiceProviderEditGlobal
argument_list|)
condition|)
continue|continue;
block|}
if|else if
condition|(
name|provider
operator|.
name|getDepartment
argument_list|()
operator|==
literal|null
condition|)
block|{
comment|// session
if|if
condition|(
operator|!
name|context
operator|.
name|hasPermission
argument_list|(
name|context
operator|.
name|getUser
argument_list|()
operator|.
name|getCurrentAcademicSessionId
argument_list|()
argument_list|,
literal|"Session"
argument_list|,
name|Right
operator|.
name|EventServiceProviderEditSession
argument_list|)
condition|)
continue|continue;
block|}
else|else
block|{
if|if
condition|(
operator|!
name|context
operator|.
name|hasPermission
argument_list|(
name|provider
operator|.
name|getDepartment
argument_list|()
argument_list|,
name|Right
operator|.
name|EventServiceProviderEditDepartment
argument_list|)
condition|)
continue|continue;
block|}
name|Record
name|r
init|=
name|data
operator|.
name|getRecord
argument_list|(
name|provider
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
name|provider
argument_list|,
name|context
argument_list|,
name|hibSession
argument_list|)
expr_stmt|;
else|else
name|update
argument_list|(
name|provider
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
name|EventServiceProvider
name|provider
init|=
operator|new
name|EventServiceProvider
argument_list|()
decl_stmt|;
name|provider
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
name|provider
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
name|provider
operator|.
name|setNote
argument_list|(
name|record
operator|.
name|getField
argument_list|(
literal|2
argument_list|)
argument_list|)
expr_stmt|;
name|provider
operator|.
name|setEmail
argument_list|(
name|record
operator|.
name|getField
argument_list|(
literal|3
argument_list|)
argument_list|)
expr_stmt|;
name|provider
operator|.
name|setAllRooms
argument_list|(
operator|!
literal|"false"
operator|.
name|equalsIgnoreCase
argument_list|(
name|record
operator|.
name|getField
argument_list|(
literal|5
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|provider
operator|.
name|setVisible
argument_list|(
operator|!
literal|"false"
operator|.
name|equalsIgnoreCase
argument_list|(
name|record
operator|.
name|getField
argument_list|(
literal|6
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
literal|"_global"
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
condition|)
block|{
name|context
operator|.
name|checkPermission
argument_list|(
name|Right
operator|.
name|EventServiceProviderEditGlobal
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
literal|"_session"
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
condition|)
block|{
name|provider
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
argument_list|)
argument_list|)
expr_stmt|;
name|context
operator|.
name|checkPermission
argument_list|(
name|provider
operator|.
name|getSession
argument_list|()
argument_list|,
name|Right
operator|.
name|EventServiceProviderEditSession
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|provider
operator|.
name|setDepartment
argument_list|(
name|Department
operator|.
name|findByDeptCode
argument_list|(
name|record
operator|.
name|getField
argument_list|(
literal|4
argument_list|)
argument_list|,
name|context
operator|.
name|getUser
argument_list|()
operator|.
name|getCurrentAcademicSessionId
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|provider
operator|.
name|setSession
argument_list|(
name|provider
operator|.
name|getDepartment
argument_list|()
operator|.
name|getSession
argument_list|()
argument_list|)
expr_stmt|;
name|context
operator|.
name|checkPermission
argument_list|(
name|provider
operator|.
name|getDepartment
argument_list|()
argument_list|,
name|Right
operator|.
name|EventServiceProviderEditDepartment
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
name|provider
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
name|provider
argument_list|,
name|provider
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
name|EventServiceProvider
name|provider
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
name|provider
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
name|provider
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
name|provider
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
name|provider
operator|.
name|getNote
argument_list|()
argument_list|,
name|record
operator|.
name|getField
argument_list|(
literal|2
argument_list|)
argument_list|)
operator|&&
name|ToolBox
operator|.
name|equals
argument_list|(
name|provider
operator|.
name|getEmail
argument_list|()
argument_list|,
name|record
operator|.
name|getField
argument_list|(
literal|3
argument_list|)
argument_list|)
operator|&&
name|ToolBox
operator|.
name|equals
argument_list|(
name|provider
operator|.
name|isAllRooms
argument_list|()
argument_list|,
literal|"true"
operator|.
name|equalsIgnoreCase
argument_list|(
name|record
operator|.
name|getField
argument_list|(
literal|5
argument_list|)
argument_list|)
argument_list|)
operator|&&
name|ToolBox
operator|.
name|equals
argument_list|(
name|provider
operator|.
name|isAllRooms
argument_list|()
argument_list|,
literal|"true"
operator|.
name|equalsIgnoreCase
argument_list|(
name|record
operator|.
name|getField
argument_list|(
literal|6
argument_list|)
argument_list|)
argument_list|)
condition|)
return|return;
name|provider
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
name|provider
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
name|provider
operator|.
name|setNote
argument_list|(
name|record
operator|.
name|getField
argument_list|(
literal|2
argument_list|)
argument_list|)
expr_stmt|;
name|provider
operator|.
name|setEmail
argument_list|(
name|record
operator|.
name|getField
argument_list|(
literal|3
argument_list|)
argument_list|)
expr_stmt|;
name|provider
operator|.
name|setAllRooms
argument_list|(
literal|"true"
operator|.
name|equalsIgnoreCase
argument_list|(
name|record
operator|.
name|getField
argument_list|(
literal|5
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|provider
operator|.
name|setVisible
argument_list|(
literal|"true"
operator|.
name|equalsIgnoreCase
argument_list|(
name|record
operator|.
name|getField
argument_list|(
literal|6
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|hibSession
operator|.
name|saveOrUpdate
argument_list|(
name|provider
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
name|provider
argument_list|,
name|provider
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
name|EventServiceProviderDAO
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
name|EventServiceProvider
name|provider
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
name|provider
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
name|provider
argument_list|,
name|provider
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
name|provider
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
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
name|EventServiceProviderDAO
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
