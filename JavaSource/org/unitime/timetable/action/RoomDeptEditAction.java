begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.2 - 3.5 (University Timetabling Application)  * Copyright (C) 2008 - 2013, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
end_comment

begin_package
package|package
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|action
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
name|java
operator|.
name|util
operator|.
name|TreeSet
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|servlet
operator|.
name|http
operator|.
name|HttpServletRequest
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|servlet
operator|.
name|http
operator|.
name|HttpServletResponse
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|struts
operator|.
name|action
operator|.
name|Action
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|struts
operator|.
name|action
operator|.
name|ActionForm
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|struts
operator|.
name|action
operator|.
name|ActionForward
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|struts
operator|.
name|action
operator|.
name|ActionMapping
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|struts
operator|.
name|action
operator|.
name|ActionMessages
import|;
end_import

begin_import
import|import
name|org
operator|.
name|hibernate
operator|.
name|Transaction
import|;
end_import

begin_import
import|import
name|org
operator|.
name|springframework
operator|.
name|beans
operator|.
name|factory
operator|.
name|annotation
operator|.
name|Autowired
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
name|commons
operator|.
name|web
operator|.
name|WebTable
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
name|defaults
operator|.
name|SessionAttribute
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
name|form
operator|.
name|RoomDeptEditForm
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
name|ExamType
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
name|GlobalRoomFeature
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
name|RoomGroup
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
name|ExamTypeDAO
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
name|RoomDeptDAO
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
literal|"/roomDeptEdit"
argument_list|)
specifier|public
class|class
name|RoomDeptEditAction
extends|extends
name|Action
block|{
annotation|@
name|Autowired
name|SessionContext
name|sessionContext
decl_stmt|;
specifier|public
name|ActionForward
name|execute
parameter_list|(
name|ActionMapping
name|mapping
parameter_list|,
name|ActionForm
name|form
parameter_list|,
name|HttpServletRequest
name|request
parameter_list|,
name|HttpServletResponse
name|response
parameter_list|)
throws|throws
name|Exception
block|{
name|RoomDeptEditForm
name|myForm
init|=
operator|(
name|RoomDeptEditForm
operator|)
name|form
decl_stmt|;
name|Department
name|d
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|sessionContext
operator|.
name|getAttribute
argument_list|(
name|SessionAttribute
operator|.
name|DepartmentCodeRoom
argument_list|)
operator|!=
literal|null
condition|)
block|{
name|String
name|deptCode
init|=
operator|(
name|String
operator|)
name|sessionContext
operator|.
name|getAttribute
argument_list|(
name|SessionAttribute
operator|.
name|DepartmentCodeRoom
argument_list|)
decl_stmt|;
if|if
condition|(
name|deptCode
operator|!=
literal|null
operator|&&
name|deptCode
operator|.
name|matches
argument_list|(
literal|"Exam[0-9]*"
argument_list|)
condition|)
block|{
name|myForm
operator|.
name|setId
argument_list|(
literal|null
argument_list|)
expr_stmt|;
name|myForm
operator|.
name|setExamType
argument_list|(
name|Long
operator|.
name|valueOf
argument_list|(
name|deptCode
operator|.
name|substring
argument_list|(
literal|4
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|sessionContext
operator|.
name|checkPermission
argument_list|(
name|Right
operator|.
name|EditRoomDepartmentsExams
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|sessionContext
operator|.
name|checkPermission
argument_list|(
name|deptCode
argument_list|,
literal|"Department"
argument_list|,
name|Right
operator|.
name|EditRoomDepartments
argument_list|)
expr_stmt|;
name|d
operator|=
name|Department
operator|.
name|findByDeptCode
argument_list|(
name|deptCode
argument_list|,
name|sessionContext
operator|.
name|getUser
argument_list|()
operator|.
name|getCurrentAcademicSessionId
argument_list|()
argument_list|)
expr_stmt|;
name|myForm
operator|.
name|setId
argument_list|(
name|d
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
name|myForm
operator|.
name|setExamType
argument_list|(
literal|null
argument_list|)
expr_stmt|;
block|}
block|}
if|if
condition|(
name|request
operator|.
name|getParameter
argument_list|(
literal|"deptId"
argument_list|)
operator|!=
literal|null
condition|)
block|{
name|String
name|id
init|=
name|request
operator|.
name|getParameter
argument_list|(
literal|"deptId"
argument_list|)
decl_stmt|;
if|if
condition|(
name|id
operator|!=
literal|null
operator|&&
name|id
operator|.
name|matches
argument_list|(
literal|"Exam[0-9]*"
argument_list|)
condition|)
block|{
name|sessionContext
operator|.
name|checkPermission
argument_list|(
name|Right
operator|.
name|EditRoomDepartmentsExams
argument_list|)
expr_stmt|;
name|myForm
operator|.
name|setId
argument_list|(
literal|null
argument_list|)
expr_stmt|;
name|myForm
operator|.
name|setExamType
argument_list|(
name|Long
operator|.
name|valueOf
argument_list|(
name|id
operator|.
name|substring
argument_list|(
literal|4
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|d
operator|=
operator|new
name|DepartmentDAO
argument_list|()
operator|.
name|get
argument_list|(
name|Long
operator|.
name|valueOf
argument_list|(
name|id
argument_list|)
argument_list|)
expr_stmt|;
name|sessionContext
operator|.
name|checkPermission
argument_list|(
name|d
argument_list|,
name|Right
operator|.
name|EditRoomDepartments
argument_list|)
expr_stmt|;
name|myForm
operator|.
name|setId
argument_list|(
name|d
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
name|myForm
operator|.
name|setExamType
argument_list|(
literal|null
argument_list|)
expr_stmt|;
block|}
block|}
if|if
condition|(
name|d
operator|==
literal|null
operator|&&
name|myForm
operator|.
name|getId
argument_list|()
operator|!=
literal|null
operator|&&
name|myForm
operator|.
name|getExamType
argument_list|()
operator|<
literal|0
condition|)
name|d
operator|=
operator|new
name|DepartmentDAO
argument_list|()
operator|.
name|get
argument_list|(
name|myForm
operator|.
name|getId
argument_list|()
argument_list|)
expr_stmt|;
name|TreeSet
argument_list|<
name|Room
argument_list|>
name|rooms
init|=
operator|new
name|TreeSet
argument_list|<
name|Room
argument_list|>
argument_list|()
decl_stmt|;
if|if
condition|(
name|sessionContext
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
condition|)
block|{
name|rooms
operator|.
name|addAll
argument_list|(
name|Location
operator|.
name|findAllRooms
argument_list|(
name|sessionContext
operator|.
name|getUser
argument_list|()
operator|.
name|getCurrentAcademicSessionId
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
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
name|sessionContext
operator|.
name|getUser
argument_list|()
argument_list|)
control|)
block|{
for|for
control|(
name|RoomDept
name|rd
range|:
name|department
operator|.
name|getRoomDepts
argument_list|()
control|)
block|{
if|if
condition|(
name|rd
operator|.
name|getRoom
argument_list|()
operator|instanceof
name|Room
condition|)
name|rooms
operator|.
name|add
argument_list|(
operator|(
name|Room
operator|)
name|rd
operator|.
name|getRoom
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
name|ExamType
name|examType
init|=
operator|(
name|myForm
operator|.
name|getExamType
argument_list|()
operator|==
literal|null
condition|?
literal|null
else|:
name|ExamTypeDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|get
argument_list|(
name|myForm
operator|.
name|getExamType
argument_list|()
argument_list|)
operator|)
decl_stmt|;
if|if
condition|(
name|d
operator|!=
literal|null
condition|)
name|myForm
operator|.
name|setName
argument_list|(
name|d
operator|.
name|getDeptCode
argument_list|()
operator|+
literal|" "
operator|+
name|d
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
if|else if
condition|(
name|examType
operator|!=
literal|null
condition|)
name|myForm
operator|.
name|setName
argument_list|(
name|examType
operator|.
name|getLabel
argument_list|()
operator|+
literal|"Examination Rooms"
argument_list|)
expr_stmt|;
else|else
name|myForm
operator|.
name|setName
argument_list|(
literal|"Unknown"
argument_list|)
expr_stmt|;
name|String
name|op
init|=
name|myForm
operator|.
name|getOp
argument_list|()
decl_stmt|;
if|if
condition|(
name|request
operator|.
name|getParameter
argument_list|(
literal|"op"
argument_list|)
operator|!=
literal|null
condition|)
name|op
operator|=
name|request
operator|.
name|getParameter
argument_list|(
literal|"op"
argument_list|)
expr_stmt|;
if|if
condition|(
name|request
operator|.
name|getParameter
argument_list|(
literal|"ord"
argument_list|)
operator|!=
literal|null
operator|&&
name|request
operator|.
name|getParameter
argument_list|(
literal|"ord"
argument_list|)
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
name|op
operator|=
literal|"ord"
expr_stmt|;
if|if
condition|(
name|op
operator|==
literal|null
condition|)
block|{
name|myForm
operator|.
name|getAssignedSet
argument_list|()
operator|.
name|clear
argument_list|()
expr_stmt|;
if|if
condition|(
name|d
operator|==
literal|null
condition|)
block|{
for|for
control|(
name|Iterator
name|i
init|=
name|Location
operator|.
name|findAllExamLocations
argument_list|(
name|sessionContext
operator|.
name|getUser
argument_list|()
operator|.
name|getCurrentAcademicSessionId
argument_list|()
argument_list|,
name|examType
argument_list|)
operator|.
name|iterator
argument_list|()
init|;
name|i
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|Location
name|location
init|=
operator|(
name|Location
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|myForm
operator|.
name|getAssignedSet
argument_list|()
operator|.
name|add
argument_list|(
name|location
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
for|for
control|(
name|Iterator
name|i
init|=
name|d
operator|.
name|getRoomDepts
argument_list|()
operator|.
name|iterator
argument_list|()
init|;
name|i
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|RoomDept
name|rd
init|=
operator|(
name|RoomDept
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|myForm
operator|.
name|getAssignedSet
argument_list|()
operator|.
name|add
argument_list|(
name|rd
operator|.
name|getRoom
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
if|if
condition|(
literal|"Back"
operator|.
name|equals
argument_list|(
name|op
argument_list|)
condition|)
block|{
return|return
name|mapping
operator|.
name|findForward
argument_list|(
literal|"back"
argument_list|)
return|;
block|}
if|if
condition|(
literal|"Update"
operator|.
name|equals
argument_list|(
name|op
argument_list|)
condition|)
block|{
name|ActionMessages
name|errors
init|=
name|myForm
operator|.
name|validate
argument_list|(
name|mapping
argument_list|,
name|request
argument_list|)
decl_stmt|;
if|if
condition|(
name|errors
operator|.
name|size
argument_list|()
operator|==
literal|0
condition|)
block|{
name|Transaction
name|tx
init|=
literal|null
decl_stmt|;
try|try
block|{
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
init|=
operator|new
name|RoomDeptDAO
argument_list|()
operator|.
name|getSession
argument_list|()
decl_stmt|;
name|tx
operator|=
name|hibSession
operator|.
name|beginTransaction
argument_list|()
expr_stmt|;
for|for
control|(
name|Iterator
name|i
init|=
name|rooms
operator|.
name|iterator
argument_list|()
init|;
name|i
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|Location
name|location
init|=
operator|(
name|Location
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|boolean
name|checked
init|=
name|myForm
operator|.
name|getAssignedSet
argument_list|()
operator|.
name|contains
argument_list|(
name|location
operator|.
name|getUniqueId
argument_list|()
argument_list|)
decl_stmt|;
name|boolean
name|current
init|=
operator|(
name|d
operator|==
literal|null
condition|?
name|location
operator|.
name|isExamEnabled
argument_list|(
name|examType
argument_list|)
else|:
name|location
operator|.
name|hasRoomDept
argument_list|(
name|d
argument_list|)
operator|)
decl_stmt|;
if|if
condition|(
name|current
operator|!=
name|checked
condition|)
block|{
if|if
condition|(
name|d
operator|==
literal|null
condition|)
block|{
name|location
operator|.
name|setExamEnabled
argument_list|(
name|examType
argument_list|,
name|checked
argument_list|)
expr_stmt|;
name|hibSession
operator|.
name|update
argument_list|(
name|location
argument_list|)
expr_stmt|;
name|ChangeLog
operator|.
name|addChange
argument_list|(
name|hibSession
argument_list|,
name|sessionContext
argument_list|,
name|location
argument_list|,
name|ChangeLog
operator|.
name|Source
operator|.
name|ROOM_DEPT_EDIT
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
if|else if
condition|(
name|checked
condition|)
block|{
name|RoomDept
name|rd
init|=
operator|new
name|RoomDept
argument_list|()
decl_stmt|;
name|rd
operator|.
name|setDepartment
argument_list|(
name|d
argument_list|)
expr_stmt|;
name|rd
operator|.
name|setRoom
argument_list|(
name|location
argument_list|)
expr_stmt|;
name|rd
operator|.
name|setControl
argument_list|(
name|Boolean
operator|.
name|FALSE
argument_list|)
expr_stmt|;
name|d
operator|.
name|getRoomDepts
argument_list|()
operator|.
name|add
argument_list|(
name|rd
argument_list|)
expr_stmt|;
name|location
operator|.
name|getRoomDepts
argument_list|()
operator|.
name|add
argument_list|(
name|rd
argument_list|)
expr_stmt|;
name|hibSession
operator|.
name|saveOrUpdate
argument_list|(
name|location
argument_list|)
expr_stmt|;
name|hibSession
operator|.
name|saveOrUpdate
argument_list|(
name|rd
argument_list|)
expr_stmt|;
name|ChangeLog
operator|.
name|addChange
argument_list|(
name|hibSession
argument_list|,
name|sessionContext
argument_list|,
name|location
argument_list|,
name|ChangeLog
operator|.
name|Source
operator|.
name|ROOM_DEPT_EDIT
argument_list|,
name|ChangeLog
operator|.
name|Operation
operator|.
name|CREATE
argument_list|,
literal|null
argument_list|,
name|d
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|RoomDept
name|rd
init|=
literal|null
decl_stmt|;
for|for
control|(
name|Iterator
name|j
init|=
name|location
operator|.
name|getRoomDepts
argument_list|()
operator|.
name|iterator
argument_list|()
init|;
name|rd
operator|==
literal|null
operator|&&
name|j
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|RoomDept
name|x
init|=
operator|(
name|RoomDept
operator|)
name|j
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|x
operator|.
name|getDepartment
argument_list|()
operator|.
name|equals
argument_list|(
name|d
argument_list|)
condition|)
name|rd
operator|=
name|x
expr_stmt|;
block|}
name|ChangeLog
operator|.
name|addChange
argument_list|(
name|hibSession
argument_list|,
name|sessionContext
argument_list|,
name|location
argument_list|,
name|ChangeLog
operator|.
name|Source
operator|.
name|ROOM_DEPT_EDIT
argument_list|,
name|ChangeLog
operator|.
name|Operation
operator|.
name|DELETE
argument_list|,
literal|null
argument_list|,
name|d
argument_list|)
expr_stmt|;
name|d
operator|.
name|getRoomDepts
argument_list|()
operator|.
name|remove
argument_list|(
name|rd
argument_list|)
expr_stmt|;
name|location
operator|.
name|getRoomDepts
argument_list|()
operator|.
name|remove
argument_list|(
name|rd
argument_list|)
expr_stmt|;
name|hibSession
operator|.
name|saveOrUpdate
argument_list|(
name|rd
operator|.
name|getRoom
argument_list|()
argument_list|)
expr_stmt|;
name|hibSession
operator|.
name|delete
argument_list|(
name|rd
argument_list|)
expr_stmt|;
name|location
operator|.
name|removedFromDepartment
argument_list|(
name|d
argument_list|,
name|hibSession
argument_list|)
expr_stmt|;
block|}
block|}
block|}
if|if
condition|(
name|d
operator|!=
literal|null
condition|)
name|hibSession
operator|.
name|saveOrUpdate
argument_list|(
name|d
argument_list|)
expr_stmt|;
name|tx
operator|.
name|commit
argument_list|()
expr_stmt|;
if|if
condition|(
name|d
operator|!=
literal|null
condition|)
name|hibSession
operator|.
name|refresh
argument_list|(
name|d
argument_list|)
expr_stmt|;
return|return
name|mapping
operator|.
name|findForward
argument_list|(
literal|"back"
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
if|if
condition|(
name|tx
operator|!=
literal|null
condition|)
name|tx
operator|.
name|rollback
argument_list|()
expr_stmt|;
throw|throw
name|e
throw|;
block|}
block|}
else|else
block|{
name|saveErrors
argument_list|(
name|request
argument_list|,
name|errors
argument_list|)
expr_stmt|;
block|}
block|}
name|WebTable
name|table
init|=
operator|(
name|d
operator|==
literal|null
condition|?
operator|new
name|WebTable
argument_list|(
literal|7
argument_list|,
literal|null
argument_list|,
literal|"javascript:document.getElementsByName('ord')[0].value=%%;roomDeptEditForm.submit();"
argument_list|,
operator|new
name|String
index|[]
block|{
literal|"Use"
block|,
literal|"Room"
block|,
literal|"Capacity"
block|,
literal|"Exam Capacity"
block|,
literal|"Type"
block|,
literal|"Global<br>Groups"
block|,
literal|"Global<br>Features"
block|}
argument_list|,
operator|new
name|String
index|[]
block|{
literal|"left"
block|,
literal|"left"
block|,
literal|"right"
block|,
literal|"right"
block|,
literal|"left"
block|,
literal|"left"
block|,
literal|"left"
block|,
literal|"left"
block|}
argument_list|,
operator|new
name|boolean
index|[]
block|{
literal|true
block|,
literal|true
block|,
literal|true
block|,
literal|true
block|,
literal|true
block|,
literal|true
block|,
literal|true
block|}
argument_list|)
else|:
operator|new
name|WebTable
argument_list|(
literal|6
argument_list|,
literal|null
argument_list|,
literal|"javascript:document.getElementsByName('ord')[0].value=%%;roomDeptEditForm.submit();"
argument_list|,
operator|new
name|String
index|[]
block|{
literal|"Use"
block|,
literal|"Room"
block|,
literal|"Capacity"
block|,
literal|"Type"
block|,
literal|"Global<br>Groups"
block|,
literal|"Global<br>Features"
block|}
argument_list|,
operator|new
name|String
index|[]
block|{
literal|"left"
block|,
literal|"left"
block|,
literal|"right"
block|,
literal|"left"
block|,
literal|"left"
block|,
literal|"left"
block|}
argument_list|,
operator|new
name|boolean
index|[]
block|{
literal|true
block|,
literal|true
block|,
literal|true
block|,
literal|true
block|,
literal|true
block|,
literal|true
block|}
argument_list|)
operator|)
decl_stmt|;
for|for
control|(
name|Iterator
name|i
init|=
name|rooms
operator|.
name|iterator
argument_list|()
init|;
name|i
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|Location
name|location
init|=
operator|(
name|Location
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|boolean
name|checked
init|=
name|myForm
operator|.
name|getAssignedSet
argument_list|()
operator|.
name|contains
argument_list|(
name|location
operator|.
name|getUniqueId
argument_list|()
argument_list|)
decl_stmt|;
name|String
name|g
init|=
literal|""
decl_stmt|,
name|f
init|=
literal|""
decl_stmt|;
for|for
control|(
name|Iterator
name|j
init|=
name|location
operator|.
name|getGlobalRoomFeatures
argument_list|()
operator|.
name|iterator
argument_list|()
init|;
name|j
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|GlobalRoomFeature
name|grf
init|=
operator|(
name|GlobalRoomFeature
operator|)
name|j
operator|.
name|next
argument_list|()
decl_stmt|;
name|f
operator|+=
name|grf
operator|.
name|getAbbv
argument_list|()
expr_stmt|;
if|if
condition|(
name|j
operator|.
name|hasNext
argument_list|()
condition|)
name|f
operator|+=
literal|", "
expr_stmt|;
block|}
for|for
control|(
name|Iterator
name|j
init|=
name|location
operator|.
name|getRoomGroups
argument_list|()
operator|.
name|iterator
argument_list|()
init|;
name|j
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|RoomGroup
name|rg
init|=
operator|(
name|RoomGroup
operator|)
name|j
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|rg
operator|.
name|isGlobal
argument_list|()
condition|)
block|{
if|if
condition|(
name|g
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
name|g
operator|+=
literal|", "
expr_stmt|;
name|g
operator|+=
name|rg
operator|.
name|getAbbv
argument_list|()
expr_stmt|;
block|}
block|}
if|if
condition|(
name|d
operator|==
literal|null
condition|)
name|table
operator|.
name|addLine
argument_list|(
operator|new
name|String
index|[]
block|{
literal|"<input type='checkbox' name='assigned' value='"
operator|+
name|location
operator|.
name|getUniqueId
argument_list|()
operator|+
literal|"' "
operator|+
operator|(
name|checked
condition|?
literal|"checked='checked'"
else|:
literal|""
operator|)
operator|+
literal|">"
block|,
name|location
operator|.
name|getLabel
argument_list|()
block|,
name|String
operator|.
name|valueOf
argument_list|(
name|location
operator|.
name|getCapacity
argument_list|()
argument_list|)
block|,
name|String
operator|.
name|valueOf
argument_list|(
name|location
operator|.
name|getExamCapacity
argument_list|()
argument_list|)
block|,
name|location
operator|.
name|getRoomTypeLabel
argument_list|()
block|,
name|g
block|,
name|f
block|}
argument_list|,
operator|new
name|Comparable
index|[]
block|{
operator|(
operator|!
name|checked
condition|?
literal|1
else|:
literal|0
operator|)
block|,
name|location
operator|.
name|getLabel
argument_list|()
block|,
name|location
operator|.
name|getCapacity
argument_list|()
block|,
name|location
operator|.
name|getExamCapacity
argument_list|()
block|,
name|location
operator|.
name|getRoomTypeLabel
argument_list|()
block|,
literal|null
block|,
literal|null
block|}
argument_list|)
expr_stmt|;
else|else
name|table
operator|.
name|addLine
argument_list|(
operator|new
name|String
index|[]
block|{
literal|"<input type='checkbox' name='assigned' value='"
operator|+
name|location
operator|.
name|getUniqueId
argument_list|()
operator|+
literal|"' "
operator|+
operator|(
name|checked
condition|?
literal|"checked='checked'"
else|:
literal|""
operator|)
operator|+
literal|">"
block|,
name|location
operator|.
name|getLabel
argument_list|()
block|,
name|String
operator|.
name|valueOf
argument_list|(
name|location
operator|.
name|getCapacity
argument_list|()
argument_list|)
block|,
name|location
operator|.
name|getRoomTypeLabel
argument_list|()
block|,
name|g
block|,
name|f
block|}
argument_list|,
operator|new
name|Comparable
index|[]
block|{
operator|(
operator|!
name|checked
condition|?
literal|1
else|:
literal|0
operator|)
block|,
name|location
operator|.
name|getLabel
argument_list|()
block|,
name|location
operator|.
name|getCapacity
argument_list|()
block|,
name|location
operator|.
name|getRoomTypeLabel
argument_list|()
block|,
literal|null
block|,
literal|null
block|}
argument_list|)
expr_stmt|;
block|}
name|WebTable
operator|.
name|setOrder
argument_list|(
name|sessionContext
argument_list|,
literal|"RoomDeptEdit.ord"
argument_list|,
name|request
operator|.
name|getParameter
argument_list|(
literal|"ord"
argument_list|)
argument_list|,
operator|(
name|d
operator|==
literal|null
condition|?
literal|4
else|:
literal|5
operator|)
argument_list|)
expr_stmt|;
name|myForm
operator|.
name|setTable
argument_list|(
name|table
operator|.
name|printTable
argument_list|(
name|WebTable
operator|.
name|getOrder
argument_list|(
name|sessionContext
argument_list|,
literal|"RoomDeptEdit.ord"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|mapping
operator|.
name|findForward
argument_list|(
literal|"show"
argument_list|)
return|;
block|}
block|}
end_class

end_unit

