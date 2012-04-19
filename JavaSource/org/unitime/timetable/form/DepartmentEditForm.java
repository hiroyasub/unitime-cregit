begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.2 (University Timetabling Application)  * Copyright (C) 2008 - 2010, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
end_comment

begin_package
package|package
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|form
package|;
end_package

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
name|org
operator|.
name|apache
operator|.
name|struts
operator|.
name|action
operator|.
name|ActionErrors
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
name|ActionMessage
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
name|unitime
operator|.
name|commons
operator|.
name|Debug
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
name|User
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
name|Web
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
name|DepartmentStatusType
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
name|util
operator|.
name|ReferenceList
import|;
end_import

begin_class
specifier|public
class|class
name|DepartmentEditForm
extends|extends
name|ActionForm
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
operator|-
literal|6614766002463228171L
decl_stmt|;
specifier|public
name|Long
name|iId
init|=
literal|null
decl_stmt|;
specifier|public
name|String
name|iName
init|=
literal|null
decl_stmt|;
specifier|public
name|String
name|iDeptCode
init|=
literal|null
decl_stmt|;
specifier|public
name|String
name|iStatusType
init|=
literal|null
decl_stmt|;
specifier|public
name|String
name|iOp
init|=
literal|null
decl_stmt|;
specifier|public
name|String
name|iAbbv
init|=
literal|null
decl_stmt|;
specifier|public
name|String
name|iExternalId
init|=
literal|null
decl_stmt|;
specifier|public
name|Boolean
name|canDelete
init|=
name|Boolean
operator|.
name|TRUE
decl_stmt|;
specifier|public
name|Boolean
name|canChangeExternalManagement
init|=
name|Boolean
operator|.
name|TRUE
decl_stmt|;
specifier|public
name|int
name|iDistPrefPriority
init|=
literal|0
decl_stmt|;
specifier|public
name|boolean
name|iIsExternal
init|=
literal|false
decl_stmt|;
specifier|public
name|String
name|iExtAbbv
init|=
literal|null
decl_stmt|;
specifier|public
name|String
name|iExtName
init|=
literal|null
decl_stmt|;
specifier|public
name|boolean
name|iAllowReqTime
init|=
literal|false
decl_stmt|;
specifier|public
name|boolean
name|iAllowReqRoom
init|=
literal|false
decl_stmt|;
specifier|public
name|boolean
name|iAllowReqDist
init|=
literal|false
decl_stmt|;
specifier|public
name|ActionErrors
name|validate
parameter_list|(
name|ActionMapping
name|mapping
parameter_list|,
name|HttpServletRequest
name|request
parameter_list|)
block|{
name|ActionErrors
name|errors
init|=
operator|new
name|ActionErrors
argument_list|()
decl_stmt|;
if|if
condition|(
name|iName
operator|==
literal|null
operator|||
name|iName
operator|.
name|trim
argument_list|()
operator|.
name|equalsIgnoreCase
argument_list|(
literal|""
argument_list|)
condition|)
block|{
name|errors
operator|.
name|add
argument_list|(
literal|"name"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.required"
argument_list|,
literal|"Name"
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|iName
operator|!=
literal|null
operator|&&
name|iName
operator|.
name|trim
argument_list|()
operator|.
name|length
argument_list|()
operator|>
literal|100
condition|)
block|{
name|errors
operator|.
name|add
argument_list|(
literal|"name"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.maxlength"
argument_list|,
literal|"Name"
argument_list|,
literal|"100"
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|iAbbv
operator|==
literal|null
operator|||
name|iAbbv
operator|.
name|trim
argument_list|()
operator|.
name|equalsIgnoreCase
argument_list|(
literal|""
argument_list|)
condition|)
block|{
name|errors
operator|.
name|add
argument_list|(
literal|"abbv"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.required"
argument_list|,
literal|"Abbreviation"
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|iAbbv
operator|!=
literal|null
operator|&&
name|iAbbv
operator|.
name|trim
argument_list|()
operator|.
name|length
argument_list|()
operator|>
literal|20
condition|)
block|{
name|errors
operator|.
name|add
argument_list|(
literal|"abbv"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.maxlength"
argument_list|,
literal|"Abbreviation"
argument_list|,
literal|"20"
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|iDeptCode
operator|==
literal|null
operator|||
name|iDeptCode
operator|.
name|trim
argument_list|()
operator|.
name|equalsIgnoreCase
argument_list|(
literal|""
argument_list|)
condition|)
block|{
name|errors
operator|.
name|add
argument_list|(
literal|"deptCode"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.required"
argument_list|,
literal|"Code"
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|iDeptCode
operator|!=
literal|null
operator|&&
name|iDeptCode
operator|.
name|trim
argument_list|()
operator|.
name|length
argument_list|()
operator|>
literal|50
condition|)
block|{
name|errors
operator|.
name|add
argument_list|(
literal|"deptCode"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.maxlength"
argument_list|,
literal|"Code"
argument_list|,
literal|"50"
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|iIsExternal
operator|&&
operator|(
name|iExtName
operator|==
literal|null
operator|||
name|iExtName
operator|.
name|trim
argument_list|()
operator|.
name|length
argument_list|()
operator|==
literal|0
operator|)
condition|)
block|{
name|errors
operator|.
name|add
argument_list|(
literal|"extName"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.required"
argument_list|,
literal|"External Manager Name"
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
operator|!
name|iIsExternal
operator|&&
name|iExtName
operator|!=
literal|null
operator|&&
name|iExtName
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
name|errors
operator|.
name|add
argument_list|(
literal|"extName"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.generic"
argument_list|,
literal|"External Manager Name should only be used when the department is marked as 'External Manager'"
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|iIsExternal
operator|&&
operator|(
name|iExtName
operator|!=
literal|null
operator|&&
name|iExtName
operator|.
name|trim
argument_list|()
operator|.
name|length
argument_list|()
operator|>
literal|30
operator|)
condition|)
block|{
name|errors
operator|.
name|add
argument_list|(
literal|"extName"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.maxlength"
argument_list|,
literal|"External Manager Name"
argument_list|,
literal|"30"
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|iIsExternal
operator|&&
operator|(
name|iExtAbbv
operator|==
literal|null
operator|||
name|iExtAbbv
operator|.
name|trim
argument_list|()
operator|.
name|length
argument_list|()
operator|==
literal|0
operator|)
condition|)
block|{
name|errors
operator|.
name|add
argument_list|(
literal|"extAbbv"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.required"
argument_list|,
literal|"External Manager Abbreviation"
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
operator|!
name|iIsExternal
operator|&&
name|iExtAbbv
operator|!=
literal|null
operator|&&
name|iExtAbbv
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
name|errors
operator|.
name|add
argument_list|(
literal|"extName"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.generic"
argument_list|,
literal|"External Manager Abbreviation should only be used when the department is marked as 'External Manager'"
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|iIsExternal
operator|&&
operator|(
name|iExtAbbv
operator|!=
literal|null
operator|&&
name|iExtAbbv
operator|.
name|trim
argument_list|()
operator|.
name|length
argument_list|()
operator|>
literal|10
operator|)
condition|)
block|{
name|errors
operator|.
name|add
argument_list|(
literal|"extAbbv"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.maxlength"
argument_list|,
literal|"External Manager Abbreviation"
argument_list|,
literal|"10"
argument_list|)
argument_list|)
expr_stmt|;
block|}
try|try
block|{
name|Department
name|dept
init|=
name|Department
operator|.
name|findByDeptCode
argument_list|(
name|iDeptCode
argument_list|,
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|Session
operator|.
name|getCurrentAcadSession
argument_list|(
name|Web
operator|.
name|getUser
argument_list|(
name|request
operator|.
name|getSession
argument_list|()
argument_list|)
argument_list|)
operator|.
name|getSessionId
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|dept
operator|!=
literal|null
operator|&&
operator|!
name|dept
operator|.
name|getUniqueId
argument_list|()
operator|.
name|equals
argument_list|(
name|iId
argument_list|)
condition|)
block|{
name|errors
operator|.
name|add
argument_list|(
literal|"deptCode"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.exists"
argument_list|,
name|iDeptCode
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|Debug
operator|.
name|error
argument_list|(
name|e
argument_list|)
expr_stmt|;
name|errors
operator|.
name|add
argument_list|(
literal|"deptCode"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.generic"
argument_list|,
name|e
operator|.
name|getMessage
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
return|return
name|errors
return|;
block|}
specifier|public
name|void
name|reset
parameter_list|(
name|ActionMapping
name|mapping
parameter_list|,
name|HttpServletRequest
name|request
parameter_list|)
block|{
name|setCanDelete
argument_list|(
name|Boolean
operator|.
name|TRUE
argument_list|)
expr_stmt|;
name|canChangeExternalManagement
operator|=
name|Boolean
operator|.
name|TRUE
expr_stmt|;
name|iId
operator|=
literal|null
expr_stmt|;
name|iName
operator|=
literal|null
expr_stmt|;
name|iDeptCode
operator|=
literal|null
expr_stmt|;
name|iStatusType
operator|=
literal|null
expr_stmt|;
name|iAbbv
operator|=
literal|null
expr_stmt|;
name|iDistPrefPriority
operator|=
literal|0
expr_stmt|;
name|iIsExternal
operator|=
literal|false
expr_stmt|;
name|iExtName
operator|=
literal|null
expr_stmt|;
name|iExtAbbv
operator|=
literal|null
expr_stmt|;
name|iAllowReqTime
operator|=
literal|false
expr_stmt|;
name|iAllowReqRoom
operator|=
literal|false
expr_stmt|;
name|iAllowReqDist
operator|=
literal|false
expr_stmt|;
block|}
specifier|public
name|Long
name|getId
parameter_list|()
block|{
return|return
name|iId
return|;
block|}
specifier|public
name|void
name|setId
parameter_list|(
name|Long
name|id
parameter_list|)
block|{
name|iId
operator|=
name|id
expr_stmt|;
block|}
specifier|public
name|String
name|getExternalId
parameter_list|()
block|{
return|return
name|iExternalId
return|;
block|}
specifier|public
name|void
name|setExternalId
parameter_list|(
name|String
name|externalId
parameter_list|)
block|{
name|iExternalId
operator|=
name|externalId
expr_stmt|;
block|}
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
name|iName
return|;
block|}
specifier|public
name|void
name|setName
parameter_list|(
name|String
name|name
parameter_list|)
block|{
name|iName
operator|=
name|name
expr_stmt|;
block|}
specifier|public
name|String
name|getAbbv
parameter_list|()
block|{
return|return
name|iAbbv
return|;
block|}
specifier|public
name|void
name|setAbbv
parameter_list|(
name|String
name|abbv
parameter_list|)
block|{
name|iAbbv
operator|=
name|abbv
expr_stmt|;
block|}
specifier|public
name|int
name|getDistPrefPriority
parameter_list|()
block|{
return|return
name|iDistPrefPriority
return|;
block|}
specifier|public
name|void
name|setDistPrefPriority
parameter_list|(
name|int
name|distPrefPriority
parameter_list|)
block|{
name|iDistPrefPriority
operator|=
name|distPrefPriority
expr_stmt|;
block|}
specifier|public
name|String
name|getDeptCode
parameter_list|()
block|{
return|return
name|iDeptCode
return|;
block|}
specifier|public
name|void
name|setDeptCode
parameter_list|(
name|String
name|deptCode
parameter_list|)
block|{
name|iDeptCode
operator|=
name|deptCode
expr_stmt|;
block|}
specifier|public
name|String
name|getStatusType
parameter_list|()
block|{
return|return
name|iStatusType
return|;
block|}
specifier|public
name|void
name|setStatusType
parameter_list|(
name|String
name|statusType
parameter_list|)
block|{
name|iStatusType
operator|=
name|statusType
expr_stmt|;
block|}
specifier|public
name|String
name|getOp
parameter_list|()
block|{
return|return
name|iOp
return|;
block|}
specifier|public
name|void
name|setOp
parameter_list|(
name|String
name|op
parameter_list|)
block|{
name|iOp
operator|=
name|op
expr_stmt|;
block|}
specifier|public
name|Boolean
name|getCanDelete
parameter_list|()
block|{
return|return
name|canDelete
return|;
block|}
specifier|public
name|void
name|setCanDelete
parameter_list|(
name|Boolean
name|canDelete
parameter_list|)
block|{
name|this
operator|.
name|canDelete
operator|=
name|canDelete
expr_stmt|;
block|}
specifier|public
name|Boolean
name|getCanChangeExternalManagement
parameter_list|()
block|{
return|return
name|canChangeExternalManagement
return|;
block|}
specifier|public
name|void
name|setCanChangeExternalManagement
parameter_list|(
name|Boolean
name|canChangeExternalManagement
parameter_list|)
block|{
name|this
operator|.
name|canChangeExternalManagement
operator|=
name|canChangeExternalManagement
expr_stmt|;
block|}
specifier|public
name|boolean
name|getIsExternal
parameter_list|()
block|{
return|return
name|iIsExternal
return|;
block|}
specifier|public
name|void
name|setIsExternal
parameter_list|(
name|boolean
name|isExternal
parameter_list|)
block|{
name|iIsExternal
operator|=
name|isExternal
expr_stmt|;
block|}
specifier|public
name|boolean
name|getAllowReqTime
parameter_list|()
block|{
return|return
name|iAllowReqTime
return|;
block|}
specifier|public
name|void
name|setAllowReqTime
parameter_list|(
name|boolean
name|allowReqTime
parameter_list|)
block|{
name|iAllowReqTime
operator|=
name|allowReqTime
expr_stmt|;
block|}
specifier|public
name|boolean
name|getAllowReqRoom
parameter_list|()
block|{
return|return
name|iAllowReqRoom
return|;
block|}
specifier|public
name|void
name|setAllowReqRoom
parameter_list|(
name|boolean
name|allowReqRoom
parameter_list|)
block|{
name|iAllowReqRoom
operator|=
name|allowReqRoom
expr_stmt|;
block|}
specifier|public
name|boolean
name|getAllowReqDist
parameter_list|()
block|{
return|return
name|iAllowReqDist
return|;
block|}
specifier|public
name|void
name|setAllowReqDist
parameter_list|(
name|boolean
name|allowReqDist
parameter_list|)
block|{
name|iAllowReqDist
operator|=
name|allowReqDist
expr_stmt|;
block|}
specifier|public
name|String
name|getExtAbbv
parameter_list|()
block|{
return|return
name|iExtAbbv
return|;
block|}
specifier|public
name|void
name|setExtAbbv
parameter_list|(
name|String
name|extAbbv
parameter_list|)
block|{
name|iExtAbbv
operator|=
name|extAbbv
expr_stmt|;
block|}
specifier|public
name|String
name|getExtName
parameter_list|()
block|{
return|return
name|iExtName
return|;
block|}
specifier|public
name|void
name|setExtName
parameter_list|(
name|String
name|extName
parameter_list|)
block|{
name|iExtName
operator|=
name|extName
expr_stmt|;
block|}
specifier|public
name|ReferenceList
name|getStatusOptions
parameter_list|()
block|{
name|ReferenceList
name|ref
init|=
operator|new
name|ReferenceList
argument_list|()
decl_stmt|;
name|ref
operator|.
name|addAll
argument_list|(
name|DepartmentStatusType
operator|.
name|findAllForDepartment
argument_list|()
argument_list|)
expr_stmt|;
return|return
name|ref
return|;
block|}
specifier|public
name|void
name|load
parameter_list|(
name|Department
name|department
parameter_list|)
block|{
name|setId
argument_list|(
name|department
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
name|setName
argument_list|(
name|department
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
name|setAbbv
argument_list|(
name|department
operator|.
name|getAbbreviation
argument_list|()
argument_list|)
expr_stmt|;
name|setDistPrefPriority
argument_list|(
name|department
operator|.
name|getDistributionPrefPriority
argument_list|()
operator|==
literal|null
condition|?
literal|0
else|:
name|department
operator|.
name|getDistributionPrefPriority
argument_list|()
operator|.
name|intValue
argument_list|()
argument_list|)
expr_stmt|;
name|setDeptCode
argument_list|(
name|department
operator|.
name|getDeptCode
argument_list|()
argument_list|)
expr_stmt|;
name|setStatusType
argument_list|(
name|department
operator|.
name|getStatusType
argument_list|()
operator|==
literal|null
condition|?
literal|null
else|:
name|department
operator|.
name|getStatusType
argument_list|()
operator|.
name|getReference
argument_list|()
argument_list|)
expr_stmt|;
name|setExternalId
argument_list|(
name|department
operator|.
name|getExternalUniqueId
argument_list|()
argument_list|)
expr_stmt|;
name|setIsExternal
argument_list|(
name|department
operator|.
name|isExternalManager
argument_list|()
operator|.
name|booleanValue
argument_list|()
argument_list|)
expr_stmt|;
name|setExtAbbv
argument_list|(
name|department
operator|.
name|getExternalMgrAbbv
argument_list|()
argument_list|)
expr_stmt|;
name|setExtName
argument_list|(
name|department
operator|.
name|getExternalMgrLabel
argument_list|()
argument_list|)
expr_stmt|;
name|setCanDelete
argument_list|(
name|Boolean
operator|.
name|TRUE
argument_list|)
expr_stmt|;
if|if
condition|(
name|department
operator|.
name|getSolverGroup
argument_list|()
operator|!=
literal|null
condition|)
name|setCanDelete
argument_list|(
name|Boolean
operator|.
name|FALSE
argument_list|)
expr_stmt|;
if|if
condition|(
name|getCanDelete
argument_list|()
condition|)
block|{
name|int
name|nrOffered
init|=
operator|(
operator|(
name|Number
operator|)
operator|new
name|DepartmentDAO
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select count(io) from CourseOffering co inner join co.instructionalOffering io "
operator|+
literal|"where co.subjectArea.department.uniqueId=:deptId and io.notOffered = 0"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"deptId"
argument_list|,
name|department
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
if|if
condition|(
name|nrOffered
operator|>
literal|0
condition|)
name|setCanDelete
argument_list|(
name|Boolean
operator|.
name|FALSE
argument_list|)
expr_stmt|;
block|}
name|setCanChangeExternalManagement
argument_list|(
name|Boolean
operator|.
name|TRUE
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
name|department
operator|.
name|getSubjectAreas
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|setCanChangeExternalManagement
argument_list|(
name|Boolean
operator|.
name|FALSE
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|department
operator|.
name|isExternalManager
argument_list|()
condition|)
block|{
name|int
name|nrExtManaged
init|=
operator|(
operator|(
name|Number
operator|)
operator|new
name|DepartmentDAO
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select count(c) from Class_ c where c.managingDept.uniqueId=:deptId"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"deptId"
argument_list|,
name|department
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
if|if
condition|(
name|nrExtManaged
operator|>
literal|0
condition|)
name|setCanChangeExternalManagement
argument_list|(
name|Boolean
operator|.
name|FALSE
argument_list|)
expr_stmt|;
block|}
name|setAllowReqRoom
argument_list|(
name|department
operator|.
name|isAllowReqRoom
argument_list|()
operator|!=
literal|null
operator|&&
name|department
operator|.
name|isAllowReqRoom
argument_list|()
operator|.
name|booleanValue
argument_list|()
argument_list|)
expr_stmt|;
name|setAllowReqTime
argument_list|(
name|department
operator|.
name|isAllowReqTime
argument_list|()
operator|!=
literal|null
operator|&&
name|department
operator|.
name|isAllowReqTime
argument_list|()
operator|.
name|booleanValue
argument_list|()
argument_list|)
expr_stmt|;
name|setAllowReqDist
argument_list|(
name|department
operator|.
name|isAllowReqDistribution
argument_list|()
operator|!=
literal|null
operator|&&
name|department
operator|.
name|isAllowReqDistribution
argument_list|()
operator|.
name|booleanValue
argument_list|()
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|save
parameter_list|(
name|User
name|user
parameter_list|,
name|HttpServletRequest
name|request
parameter_list|)
throws|throws
name|Exception
block|{
name|DepartmentDAO
name|dao
init|=
operator|new
name|DepartmentDAO
argument_list|()
decl_stmt|;
name|Session
name|session
init|=
name|dao
operator|.
name|getSession
argument_list|()
decl_stmt|;
name|Department
name|department
decl_stmt|;
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|Session
name|acadSession
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|getId
argument_list|()
operator|.
name|equals
argument_list|(
operator|new
name|Long
argument_list|(
literal|0
argument_list|)
argument_list|)
condition|)
block|{
name|department
operator|=
operator|new
name|Department
argument_list|()
expr_stmt|;
name|acadSession
operator|=
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|Session
operator|.
name|getCurrentAcadSession
argument_list|(
name|user
argument_list|)
expr_stmt|;
name|department
operator|.
name|setSession
argument_list|(
name|acadSession
argument_list|)
expr_stmt|;
name|department
operator|.
name|setDistributionPrefPriority
argument_list|(
operator|new
name|Integer
argument_list|(
literal|0
argument_list|)
argument_list|)
expr_stmt|;
name|acadSession
operator|.
name|addTodepartments
argument_list|(
name|department
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|department
operator|=
name|dao
operator|.
name|get
argument_list|(
name|getId
argument_list|()
argument_list|,
name|session
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|department
operator|!=
literal|null
condition|)
block|{
name|department
operator|.
name|setStatusType
argument_list|(
name|getStatusType
argument_list|()
operator|==
literal|null
operator|||
name|getStatusType
argument_list|()
operator|.
name|length
argument_list|()
operator|==
literal|0
condition|?
literal|null
else|:
name|DepartmentStatusType
operator|.
name|findByRef
argument_list|(
name|getStatusType
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|department
operator|.
name|setName
argument_list|(
name|getName
argument_list|()
argument_list|)
expr_stmt|;
name|department
operator|.
name|setDeptCode
argument_list|(
name|getDeptCode
argument_list|()
argument_list|)
expr_stmt|;
name|department
operator|.
name|setAbbreviation
argument_list|(
name|getAbbv
argument_list|()
argument_list|)
expr_stmt|;
name|department
operator|.
name|setExternalUniqueId
argument_list|(
name|getExternalId
argument_list|()
argument_list|)
expr_stmt|;
name|department
operator|.
name|setDistributionPrefPriority
argument_list|(
operator|new
name|Integer
argument_list|(
name|getDistPrefPriority
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|department
operator|.
name|setExternalManager
argument_list|(
operator|new
name|Boolean
argument_list|(
name|getIsExternal
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|department
operator|.
name|setExternalMgrLabel
argument_list|(
name|getExtName
argument_list|()
argument_list|)
expr_stmt|;
name|department
operator|.
name|setExternalMgrAbbv
argument_list|(
name|getExtAbbv
argument_list|()
argument_list|)
expr_stmt|;
name|department
operator|.
name|setAllowReqRoom
argument_list|(
operator|new
name|Boolean
argument_list|(
name|getAllowReqRoom
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|department
operator|.
name|setAllowReqTime
argument_list|(
operator|new
name|Boolean
argument_list|(
name|getAllowReqTime
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|department
operator|.
name|setAllowReqDistribution
argument_list|(
operator|new
name|Boolean
argument_list|(
name|getAllowReqDist
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|dao
operator|.
name|saveOrUpdate
argument_list|(
name|department
argument_list|)
expr_stmt|;
comment|//			if( acadSession != null) {
comment|//				session.saveOrUpdate(acadSession);
comment|//			}
name|ChangeLog
operator|.
name|addChange
argument_list|(
name|session
argument_list|,
name|request
argument_list|,
name|department
argument_list|,
name|ChangeLog
operator|.
name|Source
operator|.
name|DEPARTMENT_EDIT
argument_list|,
operator|(
name|getId
argument_list|()
operator|.
name|equals
argument_list|(
operator|new
name|Long
argument_list|(
literal|0
argument_list|)
argument_list|)
condition|?
name|ChangeLog
operator|.
name|Operation
operator|.
name|CREATE
else|:
name|ChangeLog
operator|.
name|Operation
operator|.
name|UPDATE
operator|)
argument_list|,
literal|null
argument_list|,
name|department
argument_list|)
expr_stmt|;
name|session
operator|.
name|flush
argument_list|()
expr_stmt|;
if|if
condition|(
name|acadSession
operator|!=
literal|null
condition|)
block|{
name|session
operator|.
name|refresh
argument_list|(
name|acadSession
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
end_class

end_unit

