begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.4 (University Timetabling Application)  * Copyright (C) 2012, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
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
name|net
operator|.
name|sf
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
name|PageAccessException
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
name|model
operator|.
name|dao
operator|.
name|RolesDAO
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

begin_class
annotation|@
name|Service
argument_list|(
literal|"gwtAdminTable[type=roles]"
argument_list|)
specifier|public
class|class
name|UserRoles
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
name|pageRole
argument_list|()
argument_list|,
name|MESSAGES
operator|.
name|pageRoles
argument_list|()
argument_list|)
return|;
block|}
annotation|@
name|Override
annotation|@
name|PreAuthorize
argument_list|(
literal|"checkPermission('Roles')"
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
name|fieldReference
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
literal|250
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
name|fieldInstructor
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
name|fieldEnabled
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
name|fieldSortOrder
argument_list|()
argument_list|,
name|FieldType
operator|.
name|text
argument_list|,
literal|80
argument_list|,
literal|10
argument_list|,
name|Flag
operator|.
name|READ_ONLY
argument_list|,
name|Flag
operator|.
name|HIDDEN
argument_list|)
argument_list|)
decl_stmt|;
name|data
operator|.
name|setSortBy
argument_list|(
literal|4
argument_list|)
expr_stmt|;
name|int
name|idx
init|=
literal|0
decl_stmt|;
for|for
control|(
name|Roles
name|role
range|:
name|Roles
operator|.
name|findAll
argument_list|(
literal|false
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
name|role
operator|.
name|getRoleId
argument_list|()
argument_list|,
operator|(
name|role
operator|.
name|isManager
argument_list|()
operator|||
name|role
operator|.
name|isInstructor
argument_list|()
operator|)
operator|&&
operator|!
name|role
operator|.
name|isUsed
argument_list|()
argument_list|)
decl_stmt|;
name|r
operator|.
name|setField
argument_list|(
literal|0
argument_list|,
name|role
operator|.
name|getReference
argument_list|()
argument_list|,
name|role
operator|.
name|isManager
argument_list|()
operator|||
name|role
operator|.
name|isInstructor
argument_list|()
argument_list|)
expr_stmt|;
name|r
operator|.
name|setField
argument_list|(
literal|1
argument_list|,
name|role
operator|.
name|getAbbv
argument_list|()
argument_list|)
expr_stmt|;
name|r
operator|.
name|setField
argument_list|(
literal|2
argument_list|,
name|role
operator|.
name|isInstructor
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
name|role
operator|.
name|isEnabled
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
literal|4
argument_list|,
name|String
operator|.
name|valueOf
argument_list|(
name|idx
operator|++
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
name|RoleEdit
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
literal|"checkPermission('RoleEdit')"
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
name|Roles
name|role
range|:
name|RolesDAO
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
name|role
operator|.
name|getRoleId
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
block|}
else|else
name|update
argument_list|(
name|role
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
literal|"checkPermission('RoleEdit')"
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
name|Roles
name|role
init|=
operator|new
name|Roles
argument_list|()
decl_stmt|;
name|role
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
name|role
operator|.
name|setAbbv
argument_list|(
name|record
operator|.
name|getField
argument_list|(
literal|1
argument_list|)
argument_list|)
expr_stmt|;
name|role
operator|.
name|setInstructor
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
name|role
operator|.
name|setEnabled
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
name|role
operator|.
name|setManager
argument_list|(
literal|true
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
name|role
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
name|role
argument_list|,
name|role
operator|.
name|getAbbv
argument_list|()
operator|+
literal|" role"
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
name|Roles
name|role
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
name|role
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
name|role
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
name|role
operator|.
name|getAbbv
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
name|role
operator|.
name|isInstructor
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
name|role
operator|.
name|isEnabled
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
name|role
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
name|role
operator|.
name|setAbbv
argument_list|(
name|record
operator|.
name|getField
argument_list|(
literal|1
argument_list|)
argument_list|)
expr_stmt|;
name|role
operator|.
name|setInstructor
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
name|role
operator|.
name|setEnabled
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
literal|" role"
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
literal|"checkPermission('RoleEdit')"
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
name|RolesDAO
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
name|Roles
name|role
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
name|role
operator|==
literal|null
condition|)
return|return;
if|if
condition|(
operator|!
name|role
operator|.
name|isManager
argument_list|()
condition|)
throw|throw
operator|new
name|PageAccessException
argument_list|(
name|MESSAGES
operator|.
name|failedDeleteRole
argument_list|(
name|role
operator|.
name|getAbbv
argument_list|()
argument_list|)
argument_list|)
throw|;
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
literal|" role"
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
name|role
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
annotation|@
name|PreAuthorize
argument_list|(
literal|"checkPermission('RoleEdit')"
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
name|RolesDAO
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

