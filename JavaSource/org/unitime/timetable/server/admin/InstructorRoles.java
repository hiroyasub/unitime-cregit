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
name|DepartmentalInstructor
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
name|DepartmentDAO
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
name|DepartmentalInstructorDAO
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
literal|"gwtAdminTable[type=instructorRole]"
argument_list|)
specifier|public
class|class
name|InstructorRoles
implements|implements
name|AdminTable
block|{
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
literal|"Instructor Role"
argument_list|)
return|;
block|}
annotation|@
name|Override
annotation|@
name|PreAuthorize
argument_list|(
literal|"checkPermission('InstructorRoles')"
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
name|departments
init|=
operator|new
name|ArrayList
argument_list|<
name|ListItem
argument_list|>
argument_list|()
decl_stmt|;
name|List
argument_list|<
name|ListItem
argument_list|>
name|instructorRoles
init|=
operator|new
name|ArrayList
argument_list|<
name|ListItem
argument_list|>
argument_list|()
decl_stmt|;
name|instructorRoles
operator|.
name|add
argument_list|(
operator|new
name|ListItem
argument_list|(
literal|""
argument_list|,
literal|""
argument_list|)
argument_list|)
expr_stmt|;
for|for
control|(
name|Roles
name|role
range|:
name|Roles
operator|.
name|findAllInstructorRoles
argument_list|()
control|)
block|{
name|instructorRoles
operator|.
name|add
argument_list|(
operator|new
name|ListItem
argument_list|(
name|role
operator|.
name|getUniqueId
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|,
name|role
operator|.
name|getAbbv
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
literal|"Department"
argument_list|,
name|FieldType
operator|.
name|list
argument_list|,
literal|160
argument_list|,
name|departments
argument_list|)
argument_list|,
operator|new
name|Field
argument_list|(
literal|"Instructor"
argument_list|,
name|FieldType
operator|.
name|person
argument_list|,
literal|300
argument_list|)
argument_list|,
operator|new
name|Field
argument_list|(
literal|"Role"
argument_list|,
name|FieldType
operator|.
name|list
argument_list|,
literal|300
argument_list|,
name|instructorRoles
argument_list|)
argument_list|)
decl_stmt|;
name|data
operator|.
name|setSortBy
argument_list|(
literal|0
argument_list|,
literal|1
argument_list|)
expr_stmt|;
name|boolean
name|deptIndep
init|=
name|context
operator|.
name|getUser
argument_list|()
operator|.
name|getCurrentAuthority
argument_list|()
operator|.
name|hasRight
argument_list|(
name|Right
operator|.
name|DepartmentIndependent
argument_list|)
decl_stmt|;
for|for
control|(
name|Department
name|department
range|:
name|Department
operator|.
name|getUserDepartments
argument_list|(
name|context
operator|.
name|getUser
argument_list|()
argument_list|)
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
name|departments
operator|.
name|add
argument_list|(
operator|new
name|ListItem
argument_list|(
name|department
operator|.
name|getUniqueId
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|,
name|department
operator|.
name|getLabel
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
for|for
control|(
name|DepartmentalInstructor
name|instructor
range|:
operator|(
name|List
argument_list|<
name|DepartmentalInstructor
argument_list|>
operator|)
name|hibSession
operator|.
name|createQuery
argument_list|(
literal|"from DepartmentalInstructor i where i.department.uniqueId = :departmentId and i.externalUniqueId is not null order by i.lastName, i.firstName"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"departmentId"
argument_list|,
name|department
operator|.
name|getUniqueId
argument_list|()
argument_list|)
operator|.
name|list
argument_list|()
control|)
block|{
if|if
condition|(
name|deptIndep
operator|&&
name|instructor
operator|.
name|getRole
argument_list|()
operator|==
literal|null
condition|)
continue|continue;
name|Record
name|r
init|=
name|data
operator|.
name|addRecord
argument_list|(
name|instructor
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
name|instructor
operator|.
name|getDepartment
argument_list|()
operator|.
name|getUniqueId
argument_list|()
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
literal|null
argument_list|,
literal|false
argument_list|)
expr_stmt|;
name|r
operator|.
name|addToField
argument_list|(
literal|1
argument_list|,
name|instructor
operator|.
name|getLastName
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
name|instructor
operator|.
name|getLastName
argument_list|()
argument_list|)
expr_stmt|;
name|r
operator|.
name|addToField
argument_list|(
literal|1
argument_list|,
name|instructor
operator|.
name|getFirstName
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
name|instructor
operator|.
name|getFirstName
argument_list|()
argument_list|)
expr_stmt|;
name|r
operator|.
name|addToField
argument_list|(
literal|1
argument_list|,
name|instructor
operator|.
name|getMiddleName
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
name|instructor
operator|.
name|getMiddleName
argument_list|()
argument_list|)
expr_stmt|;
name|r
operator|.
name|addToField
argument_list|(
literal|1
argument_list|,
name|instructor
operator|.
name|getExternalUniqueId
argument_list|()
argument_list|)
expr_stmt|;
name|r
operator|.
name|addToField
argument_list|(
literal|1
argument_list|,
name|instructor
operator|.
name|getEmail
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
name|instructor
operator|.
name|getEmail
argument_list|()
argument_list|)
expr_stmt|;
name|r
operator|.
name|setField
argument_list|(
literal|2
argument_list|,
name|instructor
operator|.
name|getRole
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
name|instructor
operator|.
name|getRole
argument_list|()
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
name|deptIndep
argument_list|)
expr_stmt|;
block|}
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
name|InstructorRoleEdit
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
literal|"checkPermission('InstructorRoleEdit')"
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
name|Department
name|department
range|:
name|Department
operator|.
name|getUserDepartments
argument_list|(
name|context
operator|.
name|getUser
argument_list|()
argument_list|)
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
name|List
argument_list|<
name|DepartmentalInstructor
argument_list|>
name|instructors
init|=
operator|(
name|List
argument_list|<
name|DepartmentalInstructor
argument_list|>
operator|)
name|hibSession
operator|.
name|createQuery
argument_list|(
literal|"from DepartmentalInstructor i where i.department.uniqueId = :departmentId and i.externalUniqueId is not null order by i.lastName, i.firstName"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"departmentId"
argument_list|,
name|department
operator|.
name|getUniqueId
argument_list|()
argument_list|)
operator|.
name|list
argument_list|()
decl_stmt|;
for|for
control|(
name|DepartmentalInstructor
name|instructor
range|:
name|instructors
control|)
block|{
name|Record
name|r
init|=
name|data
operator|.
name|getRecord
argument_list|(
name|instructor
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
name|instructor
argument_list|,
name|context
argument_list|,
name|hibSession
argument_list|)
expr_stmt|;
else|else
name|update
argument_list|(
name|instructor
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
if|if
condition|(
name|department
operator|.
name|getUniqueId
argument_list|()
operator|.
name|toString
argument_list|()
operator|.
name|equals
argument_list|(
name|r
operator|.
name|getField
argument_list|(
literal|0
argument_list|)
argument_list|)
condition|)
name|save
argument_list|(
name|department
argument_list|,
name|instructors
argument_list|,
name|r
argument_list|,
name|context
argument_list|,
name|hibSession
argument_list|)
expr_stmt|;
block|}
block|}
specifier|protected
name|void
name|save
parameter_list|(
name|Department
name|department
parameter_list|,
name|List
argument_list|<
name|DepartmentalInstructor
argument_list|>
name|instructors
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
name|department
operator|==
literal|null
condition|)
return|return;
if|if
condition|(
name|record
operator|.
name|getField
argument_list|(
literal|1
argument_list|)
operator|==
literal|null
operator|||
name|record
operator|.
name|getField
argument_list|(
literal|1
argument_list|)
operator|.
name|isEmpty
argument_list|()
condition|)
return|return;
name|String
index|[]
name|name
init|=
name|record
operator|.
name|getValues
argument_list|(
literal|1
argument_list|)
decl_stmt|;
name|DepartmentalInstructor
name|instructor
init|=
literal|null
decl_stmt|;
name|boolean
name|add
init|=
literal|true
decl_stmt|;
if|if
condition|(
name|instructors
operator|==
literal|null
condition|)
block|{
name|instructor
operator|=
name|DepartmentalInstructor
operator|.
name|findByPuidDepartmentId
argument_list|(
name|name
index|[
literal|3
index|]
argument_list|,
name|department
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
for|for
control|(
name|DepartmentalInstructor
name|i
range|:
name|instructors
control|)
if|if
condition|(
name|name
index|[
literal|3
index|]
operator|.
name|equals
argument_list|(
name|i
operator|.
name|getExternalUniqueId
argument_list|()
argument_list|)
condition|)
block|{
name|instructor
operator|=
name|i
expr_stmt|;
name|add
operator|=
literal|false
expr_stmt|;
break|break;
block|}
block|}
if|if
condition|(
name|instructor
operator|==
literal|null
condition|)
block|{
name|instructor
operator|=
operator|new
name|DepartmentalInstructor
argument_list|()
expr_stmt|;
name|instructor
operator|.
name|setExternalUniqueId
argument_list|(
name|name
index|[
literal|3
index|]
argument_list|)
expr_stmt|;
name|instructor
operator|.
name|setLastName
argument_list|(
name|name
index|[
literal|0
index|]
argument_list|)
expr_stmt|;
name|instructor
operator|.
name|setFirstName
argument_list|(
name|name
index|[
literal|1
index|]
argument_list|)
expr_stmt|;
name|instructor
operator|.
name|setMiddleName
argument_list|(
name|name
index|[
literal|2
index|]
operator|.
name|isEmpty
argument_list|()
condition|?
literal|null
else|:
name|name
index|[
literal|2
index|]
argument_list|)
expr_stmt|;
name|instructor
operator|.
name|setEmail
argument_list|(
name|name
operator|.
name|length
operator|<=
literal|4
operator|||
name|name
index|[
literal|4
index|]
operator|.
name|isEmpty
argument_list|()
condition|?
literal|null
else|:
name|name
index|[
literal|4
index|]
argument_list|)
expr_stmt|;
name|instructor
operator|.
name|setIgnoreToFar
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|instructor
operator|.
name|setDepartment
argument_list|(
name|department
argument_list|)
expr_stmt|;
name|instructor
operator|.
name|setRole
argument_list|(
name|record
operator|.
name|getField
argument_list|(
literal|2
argument_list|)
operator|==
literal|null
operator|||
name|record
operator|.
name|getField
argument_list|(
literal|2
argument_list|)
operator|.
name|isEmpty
argument_list|()
condition|?
literal|null
else|:
name|RolesDAO
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
name|record
operator|.
name|getField
argument_list|(
literal|2
argument_list|)
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
name|instructor
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|record
operator|.
name|setUniqueId
argument_list|(
name|instructor
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
name|instructor
operator|.
name|setRole
argument_list|(
name|record
operator|.
name|getField
argument_list|(
literal|2
argument_list|)
operator|==
literal|null
operator|||
name|record
operator|.
name|getField
argument_list|(
literal|2
argument_list|)
operator|.
name|isEmpty
argument_list|()
condition|?
literal|null
else|:
name|RolesDAO
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
name|record
operator|.
name|getField
argument_list|(
literal|2
argument_list|)
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|hibSession
operator|.
name|update
argument_list|(
name|instructor
argument_list|)
expr_stmt|;
block|}
name|record
operator|.
name|setDeletable
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|record
operator|.
name|setField
argument_list|(
literal|0
argument_list|,
name|record
operator|.
name|getField
argument_list|(
literal|0
argument_list|)
argument_list|,
literal|false
argument_list|)
expr_stmt|;
name|record
operator|.
name|setField
argument_list|(
literal|1
argument_list|,
name|record
operator|.
name|getField
argument_list|(
literal|1
argument_list|)
argument_list|,
literal|false
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
name|instructor
argument_list|,
name|instructor
operator|.
name|getName
argument_list|(
name|DepartmentalInstructor
operator|.
name|sNameFormatLastInitial
argument_list|)
operator|+
literal|": "
operator|+
operator|(
name|instructor
operator|.
name|getRole
argument_list|()
operator|==
literal|null
condition|?
literal|"No Role"
else|:
name|instructor
operator|.
name|getRole
argument_list|()
operator|.
name|getAbbv
argument_list|()
operator|)
argument_list|,
name|Source
operator|.
name|SIMPLE_EDIT
argument_list|,
operator|(
name|add
condition|?
name|Operation
operator|.
name|CREATE
else|:
name|Operation
operator|.
name|UPDATE
operator|)
argument_list|,
literal|null
argument_list|,
name|instructor
operator|.
name|getDepartment
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
annotation|@
name|PreAuthorize
argument_list|(
literal|"checkPermission('InstructorRoleEdit')"
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
name|save
argument_list|(
name|DepartmentDAO
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
name|record
operator|.
name|getField
argument_list|(
literal|0
argument_list|)
argument_list|)
argument_list|)
argument_list|,
literal|null
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
name|update
parameter_list|(
name|DepartmentalInstructor
name|instructor
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
name|instructor
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
name|instructor
operator|.
name|getRole
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
name|instructor
operator|.
name|getRole
argument_list|()
operator|.
name|getUniqueId
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|,
name|record
operator|.
name|getField
argument_list|(
literal|2
argument_list|)
argument_list|)
condition|)
return|return;
name|instructor
operator|.
name|setRole
argument_list|(
name|record
operator|.
name|getField
argument_list|(
literal|2
argument_list|)
operator|==
literal|null
operator|||
name|record
operator|.
name|getField
argument_list|(
literal|2
argument_list|)
operator|.
name|isEmpty
argument_list|()
condition|?
literal|null
else|:
name|RolesDAO
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
name|record
operator|.
name|getField
argument_list|(
literal|2
argument_list|)
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|hibSession
operator|.
name|update
argument_list|(
name|instructor
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
name|instructor
argument_list|,
name|instructor
operator|.
name|getName
argument_list|(
name|DepartmentalInstructor
operator|.
name|sNameFormatLastInitial
argument_list|)
operator|+
literal|": "
operator|+
operator|(
name|instructor
operator|.
name|getRole
argument_list|()
operator|==
literal|null
condition|?
literal|"No Role"
else|:
name|instructor
operator|.
name|getRole
argument_list|()
operator|.
name|getAbbv
argument_list|()
operator|)
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
name|instructor
operator|.
name|getDepartment
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
annotation|@
name|PreAuthorize
argument_list|(
literal|"checkPermission('InstructorRoleEdit')"
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
name|DepartmentalInstructorDAO
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
name|DepartmentalInstructor
name|instructor
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
name|instructor
operator|==
literal|null
condition|)
return|return;
if|if
condition|(
name|instructor
operator|.
name|getRole
argument_list|()
operator|==
literal|null
condition|)
return|return;
name|instructor
operator|.
name|setRole
argument_list|(
literal|null
argument_list|)
expr_stmt|;
name|hibSession
operator|.
name|update
argument_list|(
name|instructor
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
name|instructor
argument_list|,
name|instructor
operator|.
name|getName
argument_list|(
name|DepartmentalInstructor
operator|.
name|sNameFormatLastInitial
argument_list|)
operator|+
literal|": No Role"
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
name|instructor
operator|.
name|getDepartment
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
annotation|@
name|PreAuthorize
argument_list|(
literal|"checkPermission('InstructorRoleEdit')"
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
name|DepartmentalInstructorDAO
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

