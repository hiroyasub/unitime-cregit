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
name|security
operator|.
name|permissions
package|;
end_package

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
name|ItypeDesc
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
name|SavedHQL
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
name|SavedHQL
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
name|model
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
name|timetable
operator|.
name|model
operator|.
name|SponsoringOrganization
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
name|SubjectArea
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
name|ItypeDescDAO
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
name|UserContext
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
specifier|public
class|class
name|AdministrationPermissions
block|{
annotation|@
name|PermissionForRight
argument_list|(
name|Right
operator|.
name|Chameleon
argument_list|)
specifier|public
specifier|static
class|class
name|Chameleon
extends|extends
name|SimpleSessionPermission
block|{}
annotation|@
name|PermissionForRight
argument_list|(
name|Right
operator|.
name|DatePatterns
argument_list|)
specifier|public
specifier|static
class|class
name|DatePatterns
extends|extends
name|SimpleSessionPermission
block|{}
annotation|@
name|PermissionForRight
argument_list|(
name|Right
operator|.
name|TimePatterns
argument_list|)
specifier|public
specifier|static
class|class
name|TimePatterns
extends|extends
name|SimpleSessionPermission
block|{}
annotation|@
name|PermissionForRight
argument_list|(
name|Right
operator|.
name|ExaminationPeriods
argument_list|)
specifier|public
specifier|static
class|class
name|ExaminationPeriods
extends|extends
name|SimpleSessionPermission
block|{}
annotation|@
name|PermissionForRight
argument_list|(
name|Right
operator|.
name|DataExchange
argument_list|)
specifier|public
specifier|static
class|class
name|DataExchange
extends|extends
name|SimpleSessionPermission
block|{}
annotation|@
name|PermissionForRight
argument_list|(
name|Right
operator|.
name|SessionRollForward
argument_list|)
specifier|public
specifier|static
class|class
name|SessionRollForward
extends|extends
name|SimpleSessionPermission
block|{}
annotation|@
name|PermissionForRight
argument_list|(
name|Right
operator|.
name|Departments
argument_list|)
specifier|public
specifier|static
class|class
name|Departments
extends|extends
name|SimpleSessionPermission
block|{}
annotation|@
name|PermissionForRight
argument_list|(
name|Right
operator|.
name|DepartmentAdd
argument_list|)
specifier|public
specifier|static
class|class
name|DepartmentAdd
extends|extends
name|SimpleSessionPermission
block|{}
annotation|@
name|PermissionForRight
argument_list|(
name|Right
operator|.
name|DepartmentEdit
argument_list|)
specifier|public
specifier|static
class|class
name|DepartmentEdit
extends|extends
name|SimpleDepartmentPermission
block|{}
annotation|@
name|PermissionForRight
argument_list|(
name|Right
operator|.
name|DepartmentDelete
argument_list|)
specifier|public
specifier|static
class|class
name|DepartmentDelete
implements|implements
name|Permission
argument_list|<
name|Department
argument_list|>
block|{
annotation|@
name|Autowired
name|Permission
argument_list|<
name|Department
argument_list|>
name|permissionDepartment
decl_stmt|;
annotation|@
name|Override
specifier|public
name|boolean
name|check
parameter_list|(
name|UserContext
name|user
parameter_list|,
name|Department
name|source
parameter_list|)
block|{
if|if
condition|(
operator|!
name|permissionDepartment
operator|.
name|check
argument_list|(
name|user
argument_list|,
name|source
argument_list|)
condition|)
return|return
literal|false
return|;
if|if
condition|(
name|source
operator|.
name|getSolverGroup
argument_list|()
operator|!=
literal|null
condition|)
return|return
literal|false
return|;
name|int
name|nrOffered
init|=
operator|(
operator|(
name|Number
operator|)
name|DepartmentDAO
operator|.
name|getInstance
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
name|source
operator|.
name|getUniqueId
argument_list|()
argument_list|)
operator|.
name|setCacheable
argument_list|(
literal|true
argument_list|)
operator|.
name|uniqueResult
argument_list|()
operator|)
operator|.
name|intValue
argument_list|()
decl_stmt|;
return|return
name|nrOffered
operator|==
literal|0
return|;
block|}
annotation|@
name|Override
specifier|public
name|Class
argument_list|<
name|Department
argument_list|>
name|type
parameter_list|()
block|{
return|return
name|Department
operator|.
name|class
return|;
block|}
block|}
annotation|@
name|PermissionForRight
argument_list|(
name|Right
operator|.
name|DepartmentEditChangeExternalManager
argument_list|)
specifier|public
specifier|static
class|class
name|DepartmentEditChangeExternalManager
implements|implements
name|Permission
argument_list|<
name|Department
argument_list|>
block|{
annotation|@
name|Autowired
name|Permission
argument_list|<
name|Department
argument_list|>
name|permissionDepartmentEdit
decl_stmt|;
annotation|@
name|Override
specifier|public
name|boolean
name|check
parameter_list|(
name|UserContext
name|user
parameter_list|,
name|Department
name|source
parameter_list|)
block|{
if|if
condition|(
operator|!
name|permissionDepartmentEdit
operator|.
name|check
argument_list|(
name|user
argument_list|,
name|source
argument_list|)
condition|)
return|return
literal|false
return|;
if|if
condition|(
name|source
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
name|DepartmentDAO
operator|.
name|getInstance
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
name|source
operator|.
name|getUniqueId
argument_list|()
argument_list|)
operator|.
name|setCacheable
argument_list|(
literal|true
argument_list|)
operator|.
name|uniqueResult
argument_list|()
operator|)
operator|.
name|intValue
argument_list|()
decl_stmt|;
return|return
name|nrExtManaged
operator|==
literal|0
return|;
block|}
else|else
block|{
return|return
name|source
operator|.
name|getSubjectAreas
argument_list|()
operator|.
name|isEmpty
argument_list|()
return|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|Class
argument_list|<
name|Department
argument_list|>
name|type
parameter_list|()
block|{
return|return
name|Department
operator|.
name|class
return|;
block|}
block|}
annotation|@
name|PermissionForRight
argument_list|(
name|Right
operator|.
name|AcademicSessionEdit
argument_list|)
specifier|public
specifier|static
class|class
name|AcademicSessionEdit
extends|extends
name|SimpleSessionPermission
block|{}
annotation|@
name|PermissionForRight
argument_list|(
name|Right
operator|.
name|AcademicSessionDelete
argument_list|)
specifier|public
specifier|static
class|class
name|AcademicSessionDelete
implements|implements
name|Permission
argument_list|<
name|Session
argument_list|>
block|{
annotation|@
name|Autowired
name|Permission
argument_list|<
name|Session
argument_list|>
name|permissionSession
decl_stmt|;
annotation|@
name|Override
specifier|public
name|boolean
name|check
parameter_list|(
name|UserContext
name|user
parameter_list|,
name|Session
name|source
parameter_list|)
block|{
if|if
condition|(
operator|!
name|permissionSession
operator|.
name|check
argument_list|(
name|user
argument_list|,
name|source
argument_list|)
condition|)
return|return
literal|false
return|;
return|return
name|source
operator|.
name|getStatusType
argument_list|()
operator|==
literal|null
operator|||
operator|!
name|source
operator|.
name|getStatusType
argument_list|()
operator|.
name|isActive
argument_list|()
operator|||
name|source
operator|.
name|getStatusType
argument_list|()
operator|.
name|isTestSession
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|Class
argument_list|<
name|Session
argument_list|>
name|type
parameter_list|()
block|{
return|return
name|Session
operator|.
name|class
return|;
block|}
block|}
annotation|@
name|PermissionForRight
argument_list|(
name|Right
operator|.
name|TimetableManagerEdit
argument_list|)
specifier|public
specifier|static
class|class
name|TimetableManagerEdit
implements|implements
name|Permission
argument_list|<
name|TimetableManager
argument_list|>
block|{
annotation|@
name|Autowired
name|Permission
argument_list|<
name|Session
argument_list|>
name|permissionSession
decl_stmt|;
annotation|@
name|Override
specifier|public
name|boolean
name|check
parameter_list|(
name|UserContext
name|user
parameter_list|,
name|TimetableManager
name|source
parameter_list|)
block|{
for|for
control|(
name|Department
name|d
range|:
name|source
operator|.
name|getDepartments
argument_list|()
control|)
block|{
if|if
condition|(
name|d
operator|.
name|getSessionId
argument_list|()
operator|.
name|equals
argument_list|(
name|user
operator|.
name|getCurrentAcademicSessionId
argument_list|()
argument_list|)
condition|)
block|{
return|return
name|permissionSession
operator|.
name|check
argument_list|(
name|user
argument_list|,
name|d
operator|.
name|getSession
argument_list|()
argument_list|)
return|;
block|}
block|}
return|return
literal|true
return|;
block|}
annotation|@
name|Override
specifier|public
name|Class
argument_list|<
name|TimetableManager
argument_list|>
name|type
parameter_list|()
block|{
return|return
name|TimetableManager
operator|.
name|class
return|;
block|}
block|}
annotation|@
name|PermissionForRight
argument_list|(
name|Right
operator|.
name|TimetableManagerDelete
argument_list|)
specifier|public
specifier|static
class|class
name|TimetableManagerDelete
extends|extends
name|TimetableManagerEdit
block|{
annotation|@
name|Override
specifier|public
name|boolean
name|check
parameter_list|(
name|UserContext
name|user
parameter_list|,
name|TimetableManager
name|source
parameter_list|)
block|{
for|for
control|(
name|Department
name|d
range|:
name|source
operator|.
name|getDepartments
argument_list|()
control|)
if|if
condition|(
operator|!
name|permissionSession
operator|.
name|check
argument_list|(
name|user
argument_list|,
name|d
operator|.
name|getSession
argument_list|()
argument_list|)
condition|)
return|return
literal|false
return|;
return|return
literal|true
return|;
block|}
block|}
annotation|@
name|PermissionForRight
argument_list|(
name|Right
operator|.
name|SolverGroups
argument_list|)
specifier|public
specifier|static
class|class
name|SolverGroups
extends|extends
name|SimpleSessionPermission
block|{}
annotation|@
name|PermissionForRight
argument_list|(
name|Right
operator|.
name|SubjectAreas
argument_list|)
specifier|public
specifier|static
class|class
name|SubjectAreas
extends|extends
name|SimpleSessionPermission
block|{}
annotation|@
name|PermissionForRight
argument_list|(
name|Right
operator|.
name|SubjectAreaAdd
argument_list|)
specifier|public
specifier|static
class|class
name|SubjectAreaAdd
extends|extends
name|SubjectAreas
block|{}
annotation|@
name|PermissionForRight
argument_list|(
name|Right
operator|.
name|SubjectAreaEdit
argument_list|)
specifier|public
specifier|static
class|class
name|SubjectAreaEdit
implements|implements
name|Permission
argument_list|<
name|SubjectArea
argument_list|>
block|{
annotation|@
name|Autowired
name|Permission
argument_list|<
name|Session
argument_list|>
name|permissionSession
decl_stmt|;
annotation|@
name|Override
specifier|public
name|boolean
name|check
parameter_list|(
name|UserContext
name|user
parameter_list|,
name|SubjectArea
name|source
parameter_list|)
block|{
return|return
name|permissionSession
operator|.
name|check
argument_list|(
name|user
argument_list|,
name|source
operator|.
name|getSession
argument_list|()
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|Class
argument_list|<
name|SubjectArea
argument_list|>
name|type
parameter_list|()
block|{
return|return
name|SubjectArea
operator|.
name|class
return|;
block|}
block|}
annotation|@
name|PermissionForRight
argument_list|(
name|Right
operator|.
name|SubjectAreaDelete
argument_list|)
specifier|public
specifier|static
class|class
name|SubjectAreaDelete
extends|extends
name|SubjectAreaEdit
block|{
annotation|@
name|Override
specifier|public
name|boolean
name|check
parameter_list|(
name|UserContext
name|user
parameter_list|,
name|SubjectArea
name|source
parameter_list|)
block|{
if|if
condition|(
operator|!
name|super
operator|.
name|check
argument_list|(
name|user
argument_list|,
name|source
argument_list|)
condition|)
return|return
literal|false
return|;
return|return
operator|!
name|source
operator|.
name|hasOfferedCourses
argument_list|()
return|;
block|}
block|}
annotation|@
name|PermissionForRight
argument_list|(
name|Right
operator|.
name|SubjectAreaChangeDepartment
argument_list|)
specifier|public
specifier|static
class|class
name|SubjectAreaChangeDepartment
extends|extends
name|SubjectAreaEdit
block|{
annotation|@
name|Override
specifier|public
name|boolean
name|check
parameter_list|(
name|UserContext
name|user
parameter_list|,
name|SubjectArea
name|source
parameter_list|)
block|{
if|if
condition|(
operator|!
name|super
operator|.
name|check
argument_list|(
name|user
argument_list|,
name|source
argument_list|)
condition|)
return|return
literal|false
return|;
return|return
operator|!
name|source
operator|.
name|hasOfferedCourses
argument_list|()
operator|||
name|source
operator|.
name|getDepartment
argument_list|()
operator|==
literal|null
operator|||
name|source
operator|.
name|getDepartment
argument_list|()
operator|.
name|getSolverGroup
argument_list|()
operator|==
literal|null
operator|||
name|source
operator|.
name|getDepartment
argument_list|()
operator|.
name|getSolverGroup
argument_list|()
operator|.
name|getSolutions
argument_list|()
operator|==
literal|null
operator|||
name|source
operator|.
name|getDepartment
argument_list|()
operator|.
name|getSolverGroup
argument_list|()
operator|.
name|getSolutions
argument_list|()
operator|.
name|isEmpty
argument_list|()
return|;
block|}
block|}
annotation|@
name|PermissionForRight
argument_list|(
name|Right
operator|.
name|LastChanges
argument_list|)
specifier|public
specifier|static
class|class
name|LastChanges
extends|extends
name|SimpleSessionPermission
block|{}
annotation|@
name|PermissionForRight
argument_list|(
name|Right
operator|.
name|InstructionalTypeEdit
argument_list|)
specifier|public
specifier|static
class|class
name|InstructionalTypeEdit
implements|implements
name|Permission
argument_list|<
name|ItypeDesc
argument_list|>
block|{
annotation|@
name|Override
specifier|public
name|boolean
name|check
parameter_list|(
name|UserContext
name|user
parameter_list|,
name|ItypeDesc
name|source
parameter_list|)
block|{
return|return
literal|true
return|;
block|}
annotation|@
name|Override
specifier|public
name|Class
argument_list|<
name|ItypeDesc
argument_list|>
name|type
parameter_list|()
block|{
return|return
name|ItypeDesc
operator|.
name|class
return|;
block|}
block|}
annotation|@
name|PermissionForRight
argument_list|(
name|Right
operator|.
name|InstructionalTypeDelete
argument_list|)
specifier|public
specifier|static
class|class
name|InstructionalTypeDelete
extends|extends
name|InstructionalTypeEdit
block|{
annotation|@
name|Override
specifier|public
name|boolean
name|check
parameter_list|(
name|UserContext
name|user
parameter_list|,
name|ItypeDesc
name|source
parameter_list|)
block|{
name|int
name|nrUsed
init|=
operator|(
operator|(
name|Number
operator|)
name|ItypeDescDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select count(s) from SchedulingSubpart s where s.itype.itype=:itype"
argument_list|)
operator|.
name|setInteger
argument_list|(
literal|"itype"
argument_list|,
name|source
operator|.
name|getItype
argument_list|()
argument_list|)
operator|.
name|setCacheable
argument_list|(
literal|true
argument_list|)
operator|.
name|uniqueResult
argument_list|()
operator|)
operator|.
name|intValue
argument_list|()
decl_stmt|;
name|int
name|nrChildren
init|=
operator|(
operator|(
name|Number
operator|)
name|ItypeDescDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select count(i) from ItypeDesc i where i.parent.itype=:itype"
argument_list|)
operator|.
name|setInteger
argument_list|(
literal|"itype"
argument_list|,
name|source
operator|.
name|getItype
argument_list|()
argument_list|)
operator|.
name|setCacheable
argument_list|(
literal|true
argument_list|)
operator|.
name|uniqueResult
argument_list|()
operator|)
operator|.
name|intValue
argument_list|()
decl_stmt|;
return|return
name|nrUsed
operator|==
literal|0
operator|&&
name|nrChildren
operator|==
literal|0
return|;
block|}
block|}
annotation|@
name|PermissionForRight
argument_list|(
name|Right
operator|.
name|SponsoringOrganizationEdit
argument_list|)
specifier|public
specifier|static
class|class
name|SponsoringOrganizationEdit
implements|implements
name|Permission
argument_list|<
name|SponsoringOrganization
argument_list|>
block|{
annotation|@
name|Autowired
name|Permission
argument_list|<
name|Session
argument_list|>
name|permissionSession
decl_stmt|;
annotation|@
name|Override
specifier|public
name|boolean
name|check
parameter_list|(
name|UserContext
name|user
parameter_list|,
name|SponsoringOrganization
name|source
parameter_list|)
block|{
return|return
literal|true
return|;
block|}
annotation|@
name|Override
specifier|public
name|Class
argument_list|<
name|SponsoringOrganization
argument_list|>
name|type
parameter_list|()
block|{
return|return
name|SponsoringOrganization
operator|.
name|class
return|;
block|}
block|}
annotation|@
name|PermissionForRight
argument_list|(
name|Right
operator|.
name|SponsoringOrganizationDelete
argument_list|)
specifier|public
specifier|static
class|class
name|SponsoringOrganizationDelete
extends|extends
name|SponsoringOrganizationEdit
block|{}
annotation|@
name|PermissionForRight
argument_list|(
name|Right
operator|.
name|DistributionTypeEdit
argument_list|)
specifier|public
specifier|static
class|class
name|DistributionTypeEdit
extends|extends
name|SimpleSessionPermission
block|{}
annotation|@
name|PermissionForRight
argument_list|(
name|Right
operator|.
name|HQLReports
argument_list|)
specifier|public
specifier|static
class|class
name|HQLReports
extends|extends
name|SimpleSessionPermission
block|{}
annotation|@
name|PermissionForRight
argument_list|(
name|Right
operator|.
name|HQLReportAdd
argument_list|)
specifier|public
specifier|static
class|class
name|HQLReportAdd
extends|extends
name|HQLReports
block|{}
annotation|@
name|PermissionForRight
argument_list|(
name|Right
operator|.
name|HQLReportEdit
argument_list|)
specifier|public
specifier|static
class|class
name|HQLReportEdit
implements|implements
name|Permission
argument_list|<
name|SavedHQL
argument_list|>
block|{
annotation|@
name|Override
specifier|public
name|boolean
name|check
parameter_list|(
name|UserContext
name|user
parameter_list|,
name|SavedHQL
name|source
parameter_list|)
block|{
if|if
condition|(
name|source
operator|.
name|isSet
argument_list|(
name|SavedHQL
operator|.
name|Flag
operator|.
name|ADMIN_ONLY
argument_list|)
condition|)
return|return
name|user
operator|.
name|getCurrentAuthority
argument_list|()
operator|.
name|hasRight
argument_list|(
name|Right
operator|.
name|HQLReportsAdminOnly
argument_list|)
return|;
return|return
literal|true
return|;
block|}
annotation|@
name|Override
specifier|public
name|Class
argument_list|<
name|SavedHQL
argument_list|>
name|type
parameter_list|()
block|{
return|return
name|SavedHQL
operator|.
name|class
return|;
block|}
block|}
annotation|@
name|PermissionForRight
argument_list|(
name|Right
operator|.
name|HQLReportDelete
argument_list|)
specifier|public
specifier|static
class|class
name|HQLReportDelete
extends|extends
name|HQLReportEdit
block|{}
annotation|@
name|PermissionForRight
argument_list|(
name|Right
operator|.
name|HQLReportsCourses
argument_list|)
specifier|public
specifier|static
class|class
name|HQLReportsCourses
extends|extends
name|HQLReports
block|{
annotation|@
name|Override
specifier|public
name|boolean
name|check
parameter_list|(
name|UserContext
name|user
parameter_list|,
name|Session
name|source
parameter_list|)
block|{
return|return
name|super
operator|.
name|check
argument_list|(
name|user
argument_list|,
name|source
argument_list|)
operator|&&
name|SavedHQL
operator|.
name|hasQueries
argument_list|(
name|Flag
operator|.
name|APPEARANCE_COURSES
argument_list|,
name|user
operator|.
name|getCurrentAuthority
argument_list|()
operator|.
name|hasRight
argument_list|(
name|Right
operator|.
name|HQLReportsAdminOnly
argument_list|)
argument_list|)
return|;
block|}
block|}
annotation|@
name|PermissionForRight
argument_list|(
name|Right
operator|.
name|HQLReportsExaminations
argument_list|)
specifier|public
specifier|static
class|class
name|HQLReportsExaminations
extends|extends
name|HQLReports
block|{
annotation|@
name|Override
specifier|public
name|boolean
name|check
parameter_list|(
name|UserContext
name|user
parameter_list|,
name|Session
name|source
parameter_list|)
block|{
return|return
name|super
operator|.
name|check
argument_list|(
name|user
argument_list|,
name|source
argument_list|)
operator|&&
name|SavedHQL
operator|.
name|hasQueries
argument_list|(
name|Flag
operator|.
name|APPEARANCE_EXAMS
argument_list|,
name|user
operator|.
name|getCurrentAuthority
argument_list|()
operator|.
name|hasRight
argument_list|(
name|Right
operator|.
name|HQLReportsAdminOnly
argument_list|)
argument_list|)
return|;
block|}
block|}
annotation|@
name|PermissionForRight
argument_list|(
name|Right
operator|.
name|HQLReportsEvents
argument_list|)
specifier|public
specifier|static
class|class
name|HQLReportsEvents
extends|extends
name|HQLReports
block|{
annotation|@
name|Override
specifier|public
name|boolean
name|check
parameter_list|(
name|UserContext
name|user
parameter_list|,
name|Session
name|source
parameter_list|)
block|{
return|return
name|super
operator|.
name|check
argument_list|(
name|user
argument_list|,
name|source
argument_list|)
operator|&&
name|SavedHQL
operator|.
name|hasQueries
argument_list|(
name|Flag
operator|.
name|APPEARANCE_EVENTS
argument_list|,
name|user
operator|.
name|getCurrentAuthority
argument_list|()
operator|.
name|hasRight
argument_list|(
name|Right
operator|.
name|HQLReportsAdminOnly
argument_list|)
argument_list|)
return|;
block|}
block|}
annotation|@
name|PermissionForRight
argument_list|(
name|Right
operator|.
name|HQLReportsStudents
argument_list|)
specifier|public
specifier|static
class|class
name|HQLReportsStudents
extends|extends
name|HQLReports
block|{
annotation|@
name|Override
specifier|public
name|boolean
name|check
parameter_list|(
name|UserContext
name|user
parameter_list|,
name|Session
name|source
parameter_list|)
block|{
return|return
name|super
operator|.
name|check
argument_list|(
name|user
argument_list|,
name|source
argument_list|)
operator|&&
name|SavedHQL
operator|.
name|hasQueries
argument_list|(
name|Flag
operator|.
name|APPEARANCE_SECTIONING
argument_list|,
name|user
operator|.
name|getCurrentAuthority
argument_list|()
operator|.
name|hasRight
argument_list|(
name|Right
operator|.
name|HQLReportsAdminOnly
argument_list|)
argument_list|)
return|;
block|}
block|}
annotation|@
name|PermissionForRight
argument_list|(
name|Right
operator|.
name|HQLReportsAdministration
argument_list|)
specifier|public
specifier|static
class|class
name|HQLReportsAdministration
extends|extends
name|HQLReports
block|{
annotation|@
name|Override
specifier|public
name|boolean
name|check
parameter_list|(
name|UserContext
name|user
parameter_list|,
name|Session
name|source
parameter_list|)
block|{
return|return
name|super
operator|.
name|check
argument_list|(
name|user
argument_list|,
name|source
argument_list|)
operator|&&
operator|(
name|user
operator|.
name|getCurrentAuthority
argument_list|()
operator|.
name|hasRight
argument_list|(
name|Right
operator|.
name|HQLReportAdd
argument_list|)
operator|||
name|SavedHQL
operator|.
name|hasQueries
argument_list|(
name|Flag
operator|.
name|APPEARANCE_ADMINISTRATION
argument_list|,
name|user
operator|.
name|getCurrentAuthority
argument_list|()
operator|.
name|hasRight
argument_list|(
name|Right
operator|.
name|HQLReportsAdminOnly
argument_list|)
argument_list|)
operator|)
return|;
block|}
block|}
annotation|@
name|PermissionForRight
argument_list|(
name|Right
operator|.
name|HQLReportsAdminOnly
argument_list|)
specifier|public
specifier|static
class|class
name|HQLReportsAdminOnly
extends|extends
name|HQLReports
block|{}
annotation|@
name|PermissionForRight
argument_list|(
name|Right
operator|.
name|AcademicAreas
argument_list|)
specifier|public
specifier|static
class|class
name|AcademicAreas
extends|extends
name|SimpleSessionPermission
block|{}
annotation|@
name|PermissionForRight
argument_list|(
name|Right
operator|.
name|AcademicClassifications
argument_list|)
specifier|public
specifier|static
class|class
name|AcademicClassifications
extends|extends
name|SimpleSessionPermission
block|{}
annotation|@
name|PermissionForRight
argument_list|(
name|Right
operator|.
name|Majors
argument_list|)
specifier|public
specifier|static
class|class
name|Majors
extends|extends
name|SimpleSessionPermission
block|{}
annotation|@
name|PermissionForRight
argument_list|(
name|Right
operator|.
name|Minors
argument_list|)
specifier|public
specifier|static
class|class
name|Minors
extends|extends
name|SimpleSessionPermission
block|{}
annotation|@
name|PermissionForRight
argument_list|(
name|Right
operator|.
name|StudentGroups
argument_list|)
specifier|public
specifier|static
class|class
name|StudentGroups
extends|extends
name|SimpleSessionPermission
block|{}
annotation|@
name|PermissionForRight
argument_list|(
name|Right
operator|.
name|AcademicAreaEdit
argument_list|)
specifier|public
specifier|static
class|class
name|AcademicAreaEdit
extends|extends
name|AcademicAreas
block|{}
annotation|@
name|PermissionForRight
argument_list|(
name|Right
operator|.
name|AcademicClassificationEdit
argument_list|)
specifier|public
specifier|static
class|class
name|AcademicClassificationEdit
extends|extends
name|AcademicClassifications
block|{}
annotation|@
name|PermissionForRight
argument_list|(
name|Right
operator|.
name|MajorEdit
argument_list|)
specifier|public
specifier|static
class|class
name|MajorEdit
extends|extends
name|Majors
block|{}
annotation|@
name|PermissionForRight
argument_list|(
name|Right
operator|.
name|MinorEdit
argument_list|)
specifier|public
specifier|static
class|class
name|MinorEdit
extends|extends
name|MajorEdit
block|{}
annotation|@
name|PermissionForRight
argument_list|(
name|Right
operator|.
name|StudentGroupEdit
argument_list|)
specifier|public
specifier|static
class|class
name|StudentGroupEdit
extends|extends
name|StudentGroups
block|{}
annotation|@
name|PermissionForRight
argument_list|(
name|Right
operator|.
name|EventRoomTypes
argument_list|)
specifier|public
specifier|static
class|class
name|EventRoomTypes
implements|implements
name|Permission
argument_list|<
name|Department
argument_list|>
block|{
annotation|@
name|Autowired
name|Permission
argument_list|<
name|Department
argument_list|>
name|permissionDepartment
decl_stmt|;
annotation|@
name|Override
specifier|public
name|boolean
name|check
parameter_list|(
name|UserContext
name|user
parameter_list|,
name|Department
name|source
parameter_list|)
block|{
if|if
condition|(
operator|!
name|permissionDepartment
operator|.
name|check
argument_list|(
name|user
argument_list|,
name|source
argument_list|)
condition|)
return|return
literal|false
return|;
if|if
condition|(
operator|!
name|source
operator|.
name|isAllowEvents
argument_list|()
condition|)
return|return
literal|false
return|;
name|int
name|nrRooms
init|=
operator|(
operator|(
name|Number
operator|)
name|DepartmentDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select count(r) from Room r "
operator|+
literal|"where r.eventDepartment.uniqueId=:deptId"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"deptId"
argument_list|,
name|source
operator|.
name|getUniqueId
argument_list|()
argument_list|)
operator|.
name|setCacheable
argument_list|(
literal|true
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
name|nrRooms
operator|>
literal|0
condition|)
return|return
literal|true
return|;
name|int
name|nrLocations
init|=
operator|(
operator|(
name|Number
operator|)
name|DepartmentDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select count(r) from NonUniversityLocation r "
operator|+
literal|"where r.eventDepartment.uniqueId=:deptId"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"deptId"
argument_list|,
name|source
operator|.
name|getUniqueId
argument_list|()
argument_list|)
operator|.
name|setCacheable
argument_list|(
literal|true
argument_list|)
operator|.
name|uniqueResult
argument_list|()
operator|)
operator|.
name|intValue
argument_list|()
decl_stmt|;
return|return
name|nrLocations
operator|>
literal|0
return|;
block|}
annotation|@
name|Override
specifier|public
name|Class
argument_list|<
name|Department
argument_list|>
name|type
parameter_list|()
block|{
return|return
name|Department
operator|.
name|class
return|;
block|}
block|}
annotation|@
name|PermissionForRight
argument_list|(
name|Right
operator|.
name|EventRoomTypeEdit
argument_list|)
specifier|public
specifier|static
class|class
name|EventRoomTypeEdit
extends|extends
name|EventRoomTypes
block|{}
block|}
end_class

end_unit

