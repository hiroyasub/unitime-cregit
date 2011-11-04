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
name|model
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Collection
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
name|Iterator
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
name|java
operator|.
name|util
operator|.
name|TreeSet
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Vector
import|;
end_import

begin_import
import|import
name|org
operator|.
name|hibernate
operator|.
name|Query
import|;
end_import

begin_import
import|import
name|org
operator|.
name|hibernate
operator|.
name|criterion
operator|.
name|Order
import|;
end_import

begin_import
import|import
name|org
operator|.
name|hibernate
operator|.
name|criterion
operator|.
name|Restrictions
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
name|timetable
operator|.
name|model
operator|.
name|base
operator|.
name|BaseTimetableManager
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
name|util
operator|.
name|Constants
import|;
end_import

begin_class
specifier|public
class|class
name|TimetableManager
extends|extends
name|BaseTimetableManager
implements|implements
name|Comparable
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|1L
decl_stmt|;
comment|/*[CONSTRUCTOR MARKER BEGIN]*/
specifier|public
name|TimetableManager
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
block|}
comment|/** 	 * Constructor for primary key 	 */
specifier|public
name|TimetableManager
parameter_list|(
name|java
operator|.
name|lang
operator|.
name|Long
name|uniqueId
parameter_list|)
block|{
name|super
argument_list|(
name|uniqueId
argument_list|)
expr_stmt|;
block|}
comment|/*[CONSTRUCTOR MARKER END]*/
specifier|public
specifier|static
name|TimetableManager
name|findByExternalId
parameter_list|(
name|String
name|externalId
parameter_list|)
block|{
if|if
condition|(
name|externalId
operator|==
literal|null
operator|||
name|externalId
operator|.
name|length
argument_list|()
operator|==
literal|0
condition|)
block|{
return|return
operator|(
literal|null
operator|)
return|;
block|}
name|TimetableManagerDAO
name|tmDao
init|=
operator|new
name|TimetableManagerDAO
argument_list|()
decl_stmt|;
name|List
name|mgrs
init|=
name|tmDao
operator|.
name|getSession
argument_list|()
operator|.
name|createCriteria
argument_list|(
name|TimetableManager
operator|.
name|class
argument_list|)
operator|.
name|add
argument_list|(
name|Restrictions
operator|.
name|eq
argument_list|(
literal|"externalUniqueId"
argument_list|,
name|externalId
argument_list|)
argument_list|)
operator|.
name|setCacheable
argument_list|(
literal|true
argument_list|)
operator|.
name|list
argument_list|()
decl_stmt|;
if|if
condition|(
name|mgrs
operator|!=
literal|null
operator|&&
name|mgrs
operator|.
name|size
argument_list|()
operator|==
literal|1
condition|)
block|{
return|return
operator|(
operator|(
name|TimetableManager
operator|)
name|mgrs
operator|.
name|get
argument_list|(
literal|0
argument_list|)
operator|)
return|;
block|}
else|else
return|return
operator|(
literal|null
operator|)
return|;
block|}
specifier|public
specifier|static
name|TimetableManager
name|getWithUniqueId
parameter_list|(
name|Long
name|uniqueId
parameter_list|)
block|{
name|TimetableManagerDAO
name|tmDao
init|=
operator|new
name|TimetableManagerDAO
argument_list|()
decl_stmt|;
return|return
operator|(
name|tmDao
operator|.
name|get
argument_list|(
name|uniqueId
argument_list|)
operator|)
return|;
block|}
specifier|public
specifier|static
name|TimetableManager
name|getManager
parameter_list|(
name|User
name|user
parameter_list|)
block|{
if|if
condition|(
name|user
operator|==
literal|null
condition|)
return|return
literal|null
return|;
name|String
name|idString
init|=
operator|(
name|String
operator|)
name|user
operator|.
name|getAttribute
argument_list|(
name|Constants
operator|.
name|TMTBL_MGR_ID_ATTR_NAME
argument_list|)
decl_stmt|;
if|if
condition|(
name|idString
operator|!=
literal|null
operator|&&
name|idString
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
block|{
return|return
operator|(
name|getWithUniqueId
argument_list|(
operator|new
name|Long
argument_list|(
name|idString
argument_list|)
argument_list|)
operator|)
return|;
block|}
else|else
block|{
return|return
operator|(
name|findByExternalId
argument_list|(
name|user
operator|.
name|getId
argument_list|()
argument_list|)
operator|)
return|;
block|}
block|}
specifier|public
specifier|static
name|Set
name|getSubjectAreas
parameter_list|(
name|User
name|user
parameter_list|)
throws|throws
name|Exception
block|{
name|Set
name|saList
init|=
operator|new
name|TreeSet
argument_list|()
decl_stmt|;
name|Session
name|session
init|=
name|Session
operator|.
name|getCurrentAcadSession
argument_list|(
name|user
argument_list|)
decl_stmt|;
if|if
condition|(
name|user
operator|.
name|isAdmin
argument_list|()
operator|||
name|Roles
operator|.
name|VIEW_ALL_ROLE
operator|.
name|equals
argument_list|(
name|user
operator|.
name|getCurrentRole
argument_list|()
argument_list|)
operator|||
name|Roles
operator|.
name|EXAM_MGR_ROLE
operator|.
name|equals
argument_list|(
name|user
operator|.
name|getCurrentRole
argument_list|()
argument_list|)
condition|)
block|{
return|return
operator|(
name|session
operator|.
name|getSubjectAreas
argument_list|()
operator|)
return|;
block|}
name|TimetableManager
name|tm
init|=
name|TimetableManager
operator|.
name|getManager
argument_list|(
name|user
argument_list|)
decl_stmt|;
if|if
condition|(
name|tm
operator|!=
literal|null
operator|&&
name|tm
operator|.
name|getDepartments
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|Department
name|d
init|=
literal|null
decl_stmt|;
for|for
control|(
name|Iterator
name|it
init|=
name|tm
operator|.
name|departmentsForSession
argument_list|(
name|session
operator|.
name|getUniqueId
argument_list|()
argument_list|)
operator|.
name|iterator
argument_list|()
init|;
name|it
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|d
operator|=
operator|(
name|Department
operator|)
name|it
operator|.
name|next
argument_list|()
expr_stmt|;
if|if
condition|(
name|d
operator|.
name|isExternalManager
argument_list|()
operator|.
name|booleanValue
argument_list|()
condition|)
block|{
return|return
operator|(
name|session
operator|.
name|getSubjectAreas
argument_list|()
operator|)
return|;
block|}
else|else
block|{
name|saList
operator|.
name|addAll
argument_list|(
name|d
operator|.
name|getSubjectAreas
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
return|return
name|saList
return|;
block|}
specifier|public
name|boolean
name|isExternalManager
parameter_list|()
block|{
name|boolean
name|isExternal
init|=
literal|false
decl_stmt|;
name|Department
name|d
init|=
literal|null
decl_stmt|;
for|for
control|(
name|Iterator
name|it
init|=
name|this
operator|.
name|getDepartments
argument_list|()
operator|.
name|iterator
argument_list|()
init|;
name|it
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|d
operator|=
operator|(
name|Department
operator|)
name|it
operator|.
name|next
argument_list|()
expr_stmt|;
if|if
condition|(
name|d
operator|.
name|isExternalManager
argument_list|()
operator|.
name|booleanValue
argument_list|()
condition|)
block|{
name|isExternal
operator|=
literal|true
expr_stmt|;
block|}
block|}
return|return
operator|(
name|isExternal
operator|)
return|;
block|}
specifier|public
name|Set
name|departmentsForSession
parameter_list|(
name|Long
name|sessionId
parameter_list|)
block|{
name|HashSet
name|l
init|=
operator|new
name|HashSet
argument_list|()
decl_stmt|;
if|if
condition|(
name|this
operator|.
name|getDepartments
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|Department
name|d
init|=
literal|null
decl_stmt|;
for|for
control|(
name|Iterator
name|it
init|=
name|this
operator|.
name|getDepartments
argument_list|()
operator|.
name|iterator
argument_list|()
init|;
name|it
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|d
operator|=
operator|(
name|Department
operator|)
name|it
operator|.
name|next
argument_list|()
expr_stmt|;
if|if
condition|(
name|d
operator|.
name|getSessionId
argument_list|()
operator|.
name|equals
argument_list|(
name|sessionId
argument_list|)
condition|)
block|{
name|l
operator|.
name|add
argument_list|(
name|d
argument_list|)
expr_stmt|;
block|}
block|}
block|}
return|return
operator|(
name|l
operator|)
return|;
block|}
specifier|public
name|Set
name|sessionsCanManage
parameter_list|()
block|{
name|TreeSet
name|sessions
init|=
operator|new
name|TreeSet
argument_list|()
decl_stmt|;
name|Department
name|dept
init|=
literal|null
decl_stmt|;
for|for
control|(
name|Iterator
name|it
init|=
name|getDepartments
argument_list|()
operator|.
name|iterator
argument_list|()
init|;
name|it
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|dept
operator|=
operator|(
name|Department
operator|)
name|it
operator|.
name|next
argument_list|()
expr_stmt|;
name|sessions
operator|.
name|add
argument_list|(
name|dept
operator|.
name|getSession
argument_list|()
argument_list|)
expr_stmt|;
block|}
return|return
operator|(
name|sessions
operator|)
return|;
block|}
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
operator|(
name|getLastName
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
name|getLastName
argument_list|()
operator|+
literal|", "
operator|)
operator|+
operator|(
name|getFirstName
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
name|getFirstName
argument_list|()
operator|)
operator|+
operator|(
name|getMiddleName
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
literal|" "
operator|+
name|getMiddleName
argument_list|()
operator|)
return|;
block|}
specifier|public
name|boolean
name|canAudit
parameter_list|(
name|Session
name|session
parameter_list|,
name|User
name|user
parameter_list|)
block|{
if|if
condition|(
name|user
operator|.
name|isAdmin
argument_list|()
condition|)
return|return
literal|true
return|;
if|if
condition|(
name|user
operator|.
name|getCurrentRole
argument_list|()
operator|.
name|equals
argument_list|(
name|Roles
operator|.
name|VIEW_ALL_ROLE
argument_list|)
condition|)
return|return
literal|false
return|;
if|if
condition|(
name|user
operator|.
name|getCurrentRole
argument_list|()
operator|.
name|equals
argument_list|(
name|Roles
operator|.
name|EXAM_MGR_ROLE
argument_list|)
condition|)
return|return
literal|false
return|;
for|for
control|(
name|Iterator
name|i
init|=
name|getSolverGroups
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
name|SolverGroup
name|solverGroup
init|=
operator|(
name|SolverGroup
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
name|solverGroup
operator|.
name|getSession
argument_list|()
operator|.
name|equals
argument_list|(
name|session
argument_list|)
condition|)
continue|continue;
if|if
condition|(
name|solverGroup
operator|.
name|canAudit
argument_list|()
condition|)
return|return
literal|true
return|;
block|}
return|return
literal|false
return|;
block|}
specifier|public
name|boolean
name|canSeeTimetable
parameter_list|(
name|Session
name|session
parameter_list|,
name|User
name|user
parameter_list|)
block|{
if|if
condition|(
name|user
operator|.
name|isAdmin
argument_list|()
condition|)
return|return
literal|true
return|;
if|if
condition|(
name|user
operator|.
name|getCurrentRole
argument_list|()
operator|.
name|equals
argument_list|(
name|Roles
operator|.
name|VIEW_ALL_ROLE
argument_list|)
condition|)
return|return
literal|true
return|;
if|if
condition|(
name|user
operator|.
name|getCurrentRole
argument_list|()
operator|.
name|equals
argument_list|(
name|Roles
operator|.
name|EXAM_MGR_ROLE
argument_list|)
condition|)
return|return
literal|true
return|;
if|if
condition|(
name|canDoTimetable
argument_list|(
name|session
argument_list|,
name|user
argument_list|)
condition|)
return|return
literal|true
return|;
for|for
control|(
name|Iterator
name|i
init|=
name|getDepartments
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
name|Department
name|department
init|=
operator|(
name|Department
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|department
operator|.
name|getSolverGroup
argument_list|()
operator|!=
literal|null
operator|&&
operator|!
name|department
operator|.
name|getSolverGroup
argument_list|()
operator|.
name|getSolutions
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
return|return
literal|true
return|;
block|}
for|for
control|(
name|Iterator
name|i
init|=
name|Department
operator|.
name|findAllExternal
argument_list|(
name|session
operator|.
name|getUniqueId
argument_list|()
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
name|Department
name|department
init|=
operator|(
name|Department
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|department
operator|.
name|getSolverGroup
argument_list|()
operator|!=
literal|null
operator|&&
name|department
operator|.
name|getSolverGroup
argument_list|()
operator|.
name|getCommittedSolution
argument_list|()
operator|!=
literal|null
condition|)
return|return
literal|true
return|;
block|}
return|return
literal|false
return|;
block|}
specifier|public
name|boolean
name|canDoTimetable
parameter_list|(
name|Session
name|session
parameter_list|,
name|User
name|user
parameter_list|)
block|{
if|if
condition|(
name|user
operator|.
name|isAdmin
argument_list|()
condition|)
return|return
literal|true
return|;
if|if
condition|(
name|user
operator|.
name|getCurrentRole
argument_list|()
operator|.
name|equals
argument_list|(
name|Roles
operator|.
name|VIEW_ALL_ROLE
argument_list|)
condition|)
return|return
literal|false
return|;
if|if
condition|(
name|user
operator|.
name|getCurrentRole
argument_list|()
operator|.
name|equals
argument_list|(
name|Roles
operator|.
name|EXAM_MGR_ROLE
argument_list|)
condition|)
return|return
literal|false
return|;
for|for
control|(
name|Iterator
name|i
init|=
name|getSolverGroups
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
name|SolverGroup
name|solverGroup
init|=
operator|(
name|SolverGroup
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
name|solverGroup
operator|.
name|getSession
argument_list|()
operator|.
name|equals
argument_list|(
name|session
argument_list|)
condition|)
continue|continue;
if|if
condition|(
name|solverGroup
operator|.
name|canTimetable
argument_list|()
condition|)
return|return
literal|true
return|;
block|}
return|return
literal|false
return|;
block|}
specifier|public
name|boolean
name|canEditExams
parameter_list|(
name|Session
name|session
parameter_list|,
name|User
name|user
parameter_list|)
block|{
comment|//admin
if|if
condition|(
name|Roles
operator|.
name|ADMIN_ROLE
operator|.
name|equals
argument_list|(
name|user
operator|.
name|getCurrentRole
argument_list|()
argument_list|)
condition|)
return|return
literal|true
return|;
comment|//timetable manager
if|if
condition|(
name|Roles
operator|.
name|DEPT_SCHED_MGR_ROLE
operator|.
name|equals
argument_list|(
name|user
operator|.
name|getCurrentRole
argument_list|()
argument_list|)
condition|)
return|return
name|session
operator|.
name|getStatusType
argument_list|()
operator|.
name|canExamEdit
argument_list|()
return|;
comment|//exam manager
if|if
condition|(
name|Roles
operator|.
name|EXAM_MGR_ROLE
operator|.
name|equals
argument_list|(
name|user
operator|.
name|getCurrentRole
argument_list|()
argument_list|)
condition|)
return|return
name|session
operator|.
name|getStatusType
argument_list|()
operator|.
name|canExamTimetable
argument_list|()
return|;
return|return
literal|false
return|;
block|}
specifier|public
name|boolean
name|canSeeCourses
parameter_list|(
name|Session
name|session
parameter_list|,
name|User
name|user
parameter_list|)
block|{
comment|//admin or exam manager
if|if
condition|(
name|Roles
operator|.
name|ADMIN_ROLE
operator|.
name|equals
argument_list|(
name|user
operator|.
name|getCurrentRole
argument_list|()
argument_list|)
operator|||
name|Roles
operator|.
name|VIEW_ALL_ROLE
operator|.
name|equals
argument_list|(
name|user
operator|.
name|getCurrentRole
argument_list|()
argument_list|)
operator|||
name|Roles
operator|.
name|EXAM_MGR_ROLE
operator|.
name|equals
argument_list|(
name|user
operator|.
name|getCurrentRole
argument_list|()
argument_list|)
condition|)
return|return
literal|true
return|;
if|if
condition|(
name|Roles
operator|.
name|DEPT_SCHED_MGR_ROLE
operator|.
name|equals
argument_list|(
name|user
operator|.
name|getCurrentRole
argument_list|()
argument_list|)
condition|)
block|{
for|for
control|(
name|Iterator
name|i
init|=
name|getDepartments
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
name|Department
name|d
init|=
operator|(
name|Department
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
name|d
operator|.
name|getSession
argument_list|()
operator|.
name|equals
argument_list|(
name|session
argument_list|)
condition|)
continue|continue;
if|if
condition|(
name|d
operator|.
name|isExternalManager
argument_list|()
operator|&&
name|d
operator|.
name|effectiveStatusType
argument_list|()
operator|.
name|canManagerView
argument_list|()
condition|)
return|return
literal|true
return|;
if|if
condition|(
operator|!
name|d
operator|.
name|isExternalManager
argument_list|()
operator|&&
name|d
operator|.
name|effectiveStatusType
argument_list|()
operator|.
name|canOwnerView
argument_list|()
condition|)
return|return
literal|true
return|;
block|}
block|}
return|return
literal|false
return|;
block|}
specifier|public
name|boolean
name|canAddCourses
parameter_list|(
name|Session
name|session
parameter_list|,
name|User
name|user
parameter_list|)
block|{
if|if
condition|(
name|Roles
operator|.
name|ADMIN_ROLE
operator|.
name|equals
argument_list|(
name|user
operator|.
name|getCurrentRole
argument_list|()
argument_list|)
condition|)
return|return
literal|true
return|;
if|if
condition|(
name|Roles
operator|.
name|DEPT_SCHED_MGR_ROLE
operator|.
name|equals
argument_list|(
name|user
operator|.
name|getCurrentRole
argument_list|()
argument_list|)
condition|)
block|{
for|for
control|(
name|Iterator
name|i
init|=
name|getDepartments
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
name|Department
name|d
init|=
operator|(
name|Department
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
name|d
operator|.
name|getSession
argument_list|()
operator|.
name|equals
argument_list|(
name|session
argument_list|)
condition|)
continue|continue;
if|if
condition|(
name|d
operator|.
name|isExternalManager
argument_list|()
operator|&&
name|d
operator|.
name|effectiveStatusType
argument_list|()
operator|.
name|canManagerEdit
argument_list|()
condition|)
return|return
literal|true
return|;
if|if
condition|(
operator|!
name|d
operator|.
name|isExternalManager
argument_list|()
operator|&&
name|d
operator|.
name|effectiveStatusType
argument_list|()
operator|.
name|canOwnerEdit
argument_list|()
condition|)
return|return
literal|true
return|;
block|}
block|}
return|return
literal|false
return|;
block|}
specifier|public
name|boolean
name|canSeeExams
parameter_list|(
name|Session
name|session
parameter_list|,
name|User
name|user
parameter_list|)
block|{
comment|//can edit -> can view
if|if
condition|(
name|canEditExams
argument_list|(
name|session
argument_list|,
name|user
argument_list|)
condition|)
return|return
literal|true
return|;
comment|//admin or exam manager
if|if
condition|(
name|Roles
operator|.
name|ADMIN_ROLE
operator|.
name|equals
argument_list|(
name|user
operator|.
name|getCurrentRole
argument_list|()
argument_list|)
operator|||
name|Roles
operator|.
name|EXAM_MGR_ROLE
operator|.
name|equals
argument_list|(
name|user
operator|.
name|getCurrentRole
argument_list|()
argument_list|)
condition|)
return|return
literal|true
return|;
comment|//timetable manager or view all
if|if
condition|(
name|Roles
operator|.
name|DEPT_SCHED_MGR_ROLE
operator|.
name|equals
argument_list|(
name|user
operator|.
name|getCurrentRole
argument_list|()
argument_list|)
operator|||
name|Roles
operator|.
name|VIEW_ALL_ROLE
operator|.
name|equals
argument_list|(
name|user
operator|.
name|getCurrentRole
argument_list|()
argument_list|)
condition|)
return|return
name|session
operator|.
name|getStatusType
argument_list|()
operator|.
name|canExamView
argument_list|()
return|;
return|return
literal|false
return|;
block|}
specifier|public
name|boolean
name|canTimetableExams
parameter_list|(
name|Session
name|session
parameter_list|,
name|User
name|user
parameter_list|)
block|{
if|if
condition|(
name|Roles
operator|.
name|ADMIN_ROLE
operator|.
name|equals
argument_list|(
name|user
operator|.
name|getCurrentRole
argument_list|()
argument_list|)
condition|)
return|return
literal|true
return|;
if|if
condition|(
name|Roles
operator|.
name|EXAM_MGR_ROLE
operator|.
name|equals
argument_list|(
name|user
operator|.
name|getCurrentRole
argument_list|()
argument_list|)
condition|)
return|return
name|session
operator|.
name|getStatusType
argument_list|()
operator|.
name|canExamTimetable
argument_list|()
return|;
return|return
literal|false
return|;
block|}
specifier|public
name|boolean
name|canSectionStudents
parameter_list|(
name|Session
name|session
parameter_list|,
name|User
name|user
parameter_list|)
block|{
if|if
condition|(
name|Roles
operator|.
name|ADMIN_ROLE
operator|.
name|equals
argument_list|(
name|user
operator|.
name|getCurrentRole
argument_list|()
argument_list|)
condition|)
return|return
literal|true
return|;
return|return
literal|false
return|;
block|}
specifier|public
name|boolean
name|hasASolverGroup
parameter_list|(
name|Session
name|session
parameter_list|,
name|User
name|user
parameter_list|)
block|{
if|if
condition|(
name|user
operator|.
name|isAdmin
argument_list|()
operator|||
name|user
operator|.
name|getCurrentRole
argument_list|()
operator|.
name|equals
argument_list|(
name|Roles
operator|.
name|VIEW_ALL_ROLE
argument_list|)
operator|||
name|user
operator|.
name|getCurrentRole
argument_list|()
operator|.
name|equals
argument_list|(
name|Roles
operator|.
name|EXAM_MGR_ROLE
argument_list|)
condition|)
block|{
return|return
operator|!
name|SolverGroup
operator|.
name|findBySessionId
argument_list|(
name|session
operator|.
name|getUniqueId
argument_list|()
argument_list|)
operator|.
name|isEmpty
argument_list|()
return|;
block|}
else|else
block|{
return|return
operator|!
name|getSolverGroups
argument_list|(
name|session
argument_list|)
operator|.
name|isEmpty
argument_list|()
return|;
block|}
block|}
comment|//needs to be implemented
specifier|public
specifier|static
name|boolean
name|canSeeEvents
parameter_list|(
name|User
name|user
parameter_list|)
block|{
for|for
control|(
name|RoomType
name|roomType
range|:
name|RoomType
operator|.
name|findAll
argument_list|()
control|)
block|{
if|if
condition|(
name|roomType
operator|.
name|countManagableRooms
argument_list|()
operator|>
literal|0
condition|)
return|return
literal|true
return|;
block|}
return|return
literal|false
return|;
block|}
specifier|public
name|Collection
name|getClasses
parameter_list|(
name|Session
name|session
parameter_list|)
block|{
name|Vector
name|classes
init|=
operator|new
name|Vector
argument_list|()
decl_stmt|;
for|for
control|(
name|Iterator
name|i
init|=
name|departmentsForSession
argument_list|(
name|session
operator|.
name|getUniqueId
argument_list|()
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
name|Department
name|d
init|=
operator|(
name|Department
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|classes
operator|.
name|addAll
argument_list|(
name|d
operator|.
name|getClasses
argument_list|()
argument_list|)
expr_stmt|;
block|}
return|return
name|classes
return|;
block|}
specifier|public
name|Collection
name|getNotAssignedClasses
parameter_list|(
name|Solution
name|solution
parameter_list|)
block|{
name|Vector
name|classes
init|=
operator|new
name|Vector
argument_list|()
decl_stmt|;
for|for
control|(
name|Iterator
name|i
init|=
name|departmentsForSession
argument_list|(
name|solution
operator|.
name|getSession
argument_list|()
operator|.
name|getUniqueId
argument_list|()
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
name|Department
name|d
init|=
operator|(
name|Department
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|classes
operator|.
name|addAll
argument_list|(
name|d
operator|.
name|getNotAssignedClasses
argument_list|(
name|solution
argument_list|)
argument_list|)
expr_stmt|;
block|}
return|return
name|classes
return|;
block|}
specifier|public
name|Set
name|getDistributionPreferences
parameter_list|(
name|Session
name|session
parameter_list|)
block|{
name|TreeSet
name|prefs
init|=
operator|new
name|TreeSet
argument_list|()
decl_stmt|;
for|for
control|(
name|Iterator
name|i
init|=
name|departmentsForSession
argument_list|(
name|session
operator|.
name|getUniqueId
argument_list|()
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
name|Department
name|d
init|=
operator|(
name|Department
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|prefs
operator|.
name|addAll
argument_list|(
name|d
operator|.
name|getDistributionPreferences
argument_list|()
argument_list|)
expr_stmt|;
block|}
return|return
name|prefs
return|;
block|}
specifier|public
name|Set
name|getSolverGroups
parameter_list|(
name|Session
name|session
parameter_list|)
block|{
name|TreeSet
name|groups
init|=
operator|new
name|TreeSet
argument_list|()
decl_stmt|;
for|for
control|(
name|Iterator
name|i
init|=
name|getSolverGroups
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
name|SolverGroup
name|g
init|=
operator|(
name|SolverGroup
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|session
operator|.
name|equals
argument_list|(
name|g
operator|.
name|getSession
argument_list|()
argument_list|)
condition|)
name|groups
operator|.
name|add
argument_list|(
name|g
argument_list|)
expr_stmt|;
block|}
return|return
name|groups
return|;
block|}
specifier|public
name|String
name|getShortName
parameter_list|()
block|{
name|StringBuffer
name|sb
init|=
operator|new
name|StringBuffer
argument_list|()
decl_stmt|;
if|if
condition|(
name|getFirstName
argument_list|()
operator|!=
literal|null
operator|&&
name|getFirstName
argument_list|()
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
name|getFirstName
argument_list|()
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
literal|1
argument_list|)
operator|.
name|toUpperCase
argument_list|()
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|". "
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|getLastName
argument_list|()
operator|!=
literal|null
operator|&&
name|getLastName
argument_list|()
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
name|getLastName
argument_list|()
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
literal|1
argument_list|)
operator|.
name|toUpperCase
argument_list|()
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|getLastName
argument_list|()
operator|.
name|substring
argument_list|(
literal|1
argument_list|,
name|Math
operator|.
name|min
argument_list|(
literal|10
argument_list|,
name|getLastName
argument_list|()
operator|.
name|length
argument_list|()
argument_list|)
argument_list|)
operator|.
name|toLowerCase
argument_list|()
operator|.
name|trim
argument_list|()
argument_list|)
expr_stmt|;
block|}
return|return
name|sb
operator|.
name|toString
argument_list|()
return|;
block|}
specifier|public
name|int
name|compareTo
parameter_list|(
name|Object
name|o
parameter_list|)
block|{
if|if
condition|(
name|o
operator|==
literal|null
operator|||
operator|!
operator|(
name|o
operator|instanceof
name|TimetableManager
operator|)
condition|)
return|return
operator|-
literal|1
return|;
name|TimetableManager
name|m
init|=
operator|(
name|TimetableManager
operator|)
name|o
decl_stmt|;
name|int
name|cmp
init|=
name|getName
argument_list|()
operator|.
name|compareTo
argument_list|(
name|m
operator|.
name|getName
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|cmp
operator|!=
literal|0
condition|)
return|return
name|cmp
return|;
return|return
name|getUniqueId
argument_list|()
operator|.
name|compareTo
argument_list|(
name|m
operator|.
name|getUniqueId
argument_list|()
argument_list|)
return|;
block|}
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
name|getName
argument_list|()
return|;
block|}
comment|/** Request attribute name for manager list **/
specifier|public
specifier|static
name|String
name|MGR_LIST_ATTR_NAME
init|=
literal|"managerList"
decl_stmt|;
comment|/** 	 * Retrieves all consent types in the database 	 * ordered by column last name, first name      * @return Vector of TimetableManager objects      */
specifier|public
specifier|static
specifier|synchronized
name|Vector
name|getManagerList
parameter_list|()
block|{
name|TimetableManagerDAO
name|tdao
init|=
operator|new
name|TimetableManagerDAO
argument_list|()
decl_stmt|;
name|List
name|l
init|=
name|tdao
operator|.
name|findAll
argument_list|(
name|Order
operator|.
name|asc
argument_list|(
literal|"lastName"
argument_list|)
argument_list|,
name|Order
operator|.
name|asc
argument_list|(
literal|"firstName"
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|l
operator|!=
literal|null
condition|)
return|return
operator|new
name|Vector
argument_list|(
name|l
argument_list|)
return|;
return|return
literal|null
return|;
block|}
specifier|public
name|Roles
name|getPrimaryRole
parameter_list|()
block|{
for|for
control|(
name|Iterator
name|i
init|=
name|getManagerRoles
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
name|ManagerRole
name|role
init|=
operator|(
name|ManagerRole
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|role
operator|.
name|isPrimary
argument_list|()
condition|)
return|return
name|role
operator|.
name|getRole
argument_list|()
return|;
block|}
if|if
condition|(
name|getManagerRoles
argument_list|()
operator|.
name|size
argument_list|()
operator|==
literal|1
condition|)
return|return
operator|(
operator|(
name|ManagerRole
operator|)
name|getManagerRoles
argument_list|()
operator|.
name|iterator
argument_list|()
operator|.
name|next
argument_list|()
operator|)
operator|.
name|getRole
argument_list|()
return|;
return|return
literal|null
return|;
block|}
specifier|public
name|Vector
argument_list|<
name|RoomType
argument_list|>
name|findDefaultEventManagerRoomTimesFor
parameter_list|(
name|String
name|currentRole
parameter_list|,
name|Long
name|acadSessionId
parameter_list|)
block|{
name|Vector
argument_list|<
name|RoomType
argument_list|>
name|roomTypes
init|=
operator|new
name|Vector
argument_list|<
name|RoomType
argument_list|>
argument_list|()
decl_stmt|;
if|if
condition|(
name|Roles
operator|.
name|EVENT_MGR_ROLE
operator|.
name|equals
argument_list|(
name|currentRole
argument_list|)
condition|)
block|{
name|StringBuilder
name|sb
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|"select distinct rt from TimetableManager tm inner join tm.departments d"
argument_list|)
operator|.
name|append
argument_list|(
literal|" inner join d.roomDepts rd inner join rd.room.roomType rt"
argument_list|)
operator|.
name|append
argument_list|(
literal|" where tm.uniqueId = :managerId "
argument_list|)
operator|.
name|append
argument_list|(
literal|" and rd.control = true "
argument_list|)
operator|.
name|append
argument_list|(
literal|" and 1 = (select rto.status from RoomTypeOption rto where rto.session.uniqueId = :sessionId and rto.roomType.uniqueId = rt.uniqueId )"
argument_list|)
expr_stmt|;
name|Query
name|query
init|=
name|TimetableManagerDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
name|sb
operator|.
name|toString
argument_list|()
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"managerId"
argument_list|,
name|getUniqueId
argument_list|()
operator|.
name|longValue
argument_list|()
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"sessionId"
argument_list|,
name|acadSessionId
operator|.
name|longValue
argument_list|()
argument_list|)
decl_stmt|;
for|for
control|(
name|Iterator
name|it
init|=
name|query
operator|.
name|iterate
argument_list|()
init|;
name|it
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|roomTypes
operator|.
name|add
argument_list|(
operator|(
name|RoomType
operator|)
name|it
operator|.
name|next
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
return|return
operator|(
name|roomTypes
operator|)
return|;
block|}
block|}
end_class

end_unit

