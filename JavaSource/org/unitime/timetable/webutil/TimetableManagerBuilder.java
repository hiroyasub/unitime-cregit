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
name|webutil
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
name|Collections
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
name|unitime
operator|.
name|timetable
operator|.
name|defaults
operator|.
name|CommonValues
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
name|UserProperty
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
name|SolverGroup
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
name|comparators
operator|.
name|RolesComparator
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
name|NameFormat
import|;
end_import

begin_comment
comment|/**  * Build list of Managers for the currently selected academic session  *   * @author Heston Fernandes, Tomas Muller, Stephanie Schluttenhofer  */
end_comment

begin_class
specifier|public
class|class
name|TimetableManagerBuilder
block|{
specifier|public
name|PdfWebTable
name|getManagersTable
parameter_list|(
name|SessionContext
name|context
parameter_list|,
name|boolean
name|html
parameter_list|,
name|boolean
name|showAll
parameter_list|)
block|{
name|int
name|cols
init|=
literal|7
decl_stmt|;
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
init|=
literal|null
decl_stmt|;
name|boolean
name|dispLastChanges
init|=
name|CommonValues
operator|.
name|Yes
operator|.
name|eq
argument_list|(
name|UserProperty
operator|.
name|DisplayLastChanges
operator|.
name|get
argument_list|(
name|context
operator|.
name|getUser
argument_list|()
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|dispLastChanges
condition|)
name|cols
operator|++
expr_stmt|;
name|Long
name|currentAcadSession
init|=
name|context
operator|.
name|getUser
argument_list|()
operator|.
name|getCurrentAcademicSessionId
argument_list|()
decl_stmt|;
comment|// Create new table
name|PdfWebTable
name|webTable
init|=
operator|new
name|PdfWebTable
argument_list|(
name|cols
argument_list|,
operator|(
name|html
condition|?
literal|""
else|:
literal|"Manager List - "
operator|+
operator|(
name|currentAcadSession
operator|==
literal|null
condition|?
literal|""
else|:
literal|" - "
operator|+
name|context
operator|.
name|getUser
argument_list|()
operator|.
name|getCurrentAuthority
argument_list|()
operator|.
name|getQualifiers
argument_list|(
literal|"Session"
argument_list|)
operator|.
name|get
argument_list|(
literal|0
argument_list|)
operator|.
name|getQualifierLabel
argument_list|()
operator|)
operator|)
argument_list|,
literal|"timetableManagerList.do?order=%%"
argument_list|,
operator|(
name|dispLastChanges
condition|?
operator|new
name|String
index|[]
block|{
literal|"Roles"
block|,
literal|"External ID"
block|,
literal|"Name"
block|,
literal|"Email Address"
block|,
literal|"Department"
block|,
literal|"Subject Area"
block|,
literal|"Solver Group"
block|,
literal|"Last Change"
block|}
else|:
operator|new
name|String
index|[]
block|{
literal|"Roles"
block|,
literal|"External ID"
block|,
literal|"Name"
block|,
literal|"Email Address"
block|,
literal|"Department"
block|,
literal|"Subject Area"
block|,
literal|"Solver Group"
block|}
operator|)
argument_list|,
operator|new
name|String
index|[]
block|{
literal|"left"
block|,
literal|"left"
block|,
literal|"left"
block|,
literal|"left"
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
block|,
literal|false
block|}
argument_list|)
decl_stmt|;
name|webTable
operator|.
name|enableHR
argument_list|(
literal|"#9CB0CE"
argument_list|)
expr_stmt|;
name|webTable
operator|.
name|setRowStyle
argument_list|(
literal|"white-space: nowrap"
argument_list|)
expr_stmt|;
name|TimetableManagerDAO
name|empDao
init|=
operator|new
name|TimetableManagerDAO
argument_list|()
decl_stmt|;
name|hibSession
operator|=
name|empDao
operator|.
name|getSession
argument_list|()
expr_stmt|;
name|List
name|empList
init|=
name|hibSession
operator|.
name|createCriteria
argument_list|(
name|TimetableManager
operator|.
name|class
argument_list|)
operator|.
name|addOrder
argument_list|(
name|Order
operator|.
name|asc
argument_list|(
literal|"managerRoles"
argument_list|)
argument_list|)
operator|.
name|addOrder
argument_list|(
name|Order
operator|.
name|asc
argument_list|(
literal|"lastName"
argument_list|)
argument_list|)
operator|.
name|addOrder
argument_list|(
name|Order
operator|.
name|asc
argument_list|(
literal|"firstName"
argument_list|)
argument_list|)
operator|.
name|list
argument_list|()
decl_stmt|;
name|Iterator
name|iterEmp
init|=
name|empList
operator|.
name|iterator
argument_list|()
decl_stmt|;
name|NameFormat
name|nameFormat
init|=
name|NameFormat
operator|.
name|fromReference
argument_list|(
name|context
operator|.
name|getUser
argument_list|()
operator|.
name|getProperty
argument_list|(
name|UserProperty
operator|.
name|NameFormat
argument_list|)
argument_list|)
decl_stmt|;
while|while
condition|(
name|iterEmp
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|TimetableManager
name|manager
init|=
operator|(
name|TimetableManager
operator|)
name|iterEmp
operator|.
name|next
argument_list|()
decl_stmt|;
name|String
name|puid
init|=
name|manager
operator|.
name|getExternalUniqueId
argument_list|()
decl_stmt|;
name|String
name|email
init|=
name|manager
operator|.
name|getEmailAddress
argument_list|()
operator|!=
literal|null
condition|?
name|manager
operator|.
name|getEmailAddress
argument_list|()
else|:
literal|" "
decl_stmt|;
name|String
name|fullName
init|=
name|nameFormat
operator|.
name|format
argument_list|(
name|manager
argument_list|)
decl_stmt|;
name|String
name|subjectList
init|=
literal|""
decl_stmt|;
name|String
name|roleStr
init|=
literal|""
decl_stmt|;
name|String
name|deptStr
init|=
literal|""
decl_stmt|;
name|Set
name|depts
init|=
name|manager
operator|.
name|getDepartments
argument_list|()
decl_stmt|;
name|Set
name|mgrRolesSet
init|=
name|manager
operator|.
name|getManagerRoles
argument_list|()
decl_stmt|;
name|String
name|onClick
init|=
operator|(
name|context
operator|.
name|hasPermission
argument_list|(
name|manager
argument_list|,
name|Right
operator|.
name|TimetableManagerEdit
argument_list|)
condition|?
literal|"onClick=\"document.location='timetableManagerEdit.do?op=Edit&id="
operator|+
name|manager
operator|.
name|getUniqueId
argument_list|()
operator|+
literal|"';\""
else|:
literal|null
operator|)
decl_stmt|;
comment|// Determine role type
name|String
name|roleOrd
init|=
literal|""
decl_stmt|;
name|ArrayList
name|mgrRoles
init|=
operator|new
name|ArrayList
argument_list|(
name|mgrRolesSet
argument_list|)
decl_stmt|;
name|Collections
operator|.
name|sort
argument_list|(
name|mgrRoles
argument_list|,
operator|new
name|RolesComparator
argument_list|()
argument_list|)
expr_stmt|;
name|boolean
name|sessionIndependent
init|=
literal|false
decl_stmt|;
for|for
control|(
name|Iterator
name|i
init|=
name|mgrRoles
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
name|mgrRole
init|=
operator|(
name|ManagerRole
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|String
name|roleRef
init|=
name|mgrRole
operator|.
name|getRole
argument_list|()
operator|.
name|getAbbv
argument_list|()
decl_stmt|;
name|String
name|title
init|=
name|roleRef
decl_stmt|;
name|boolean
name|receivesEmail
init|=
operator|(
name|mgrRole
operator|.
name|isReceiveEmails
argument_list|()
operator|==
literal|null
condition|?
literal|false
else|:
name|mgrRole
operator|.
name|isReceiveEmails
argument_list|()
operator|.
name|booleanValue
argument_list|()
operator|)
decl_stmt|;
if|if
condition|(
name|roleStr
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
name|roleStr
operator|+=
literal|","
operator|+
operator|(
name|html
condition|?
literal|"<br>"
else|:
literal|"\n"
operator|)
expr_stmt|;
if|if
condition|(
name|mgrRoles
operator|.
name|size
argument_list|()
operator|>
literal|1
operator|&&
name|mgrRole
operator|.
name|isPrimary
argument_list|()
operator|.
name|booleanValue
argument_list|()
condition|)
block|{
name|roleStr
operator|+=
operator|(
name|html
condition|?
literal|"<span title='"
operator|+
name|roleRef
operator|+
literal|" - Primary Role"
operator|+
operator|(
name|receivesEmail
condition|?
literal|""
else|:
literal|", * No Email for this Role"
operator|)
operator|+
literal|"' style='font-weight:bold;'>"
operator|+
name|roleRef
operator|+
operator|(
name|receivesEmail
condition|?
literal|""
else|:
literal|"*"
operator|)
operator|+
literal|"</span>"
else|:
literal|"@@BOLD "
operator|+
name|roleRef
operator|+
operator|(
name|receivesEmail
condition|?
literal|""
else|:
literal|"*"
operator|)
operator|+
literal|"@@END_BOLD "
operator|)
expr_stmt|;
block|}
else|else
block|{
name|roleStr
operator|+=
operator|(
name|html
condition|?
operator|(
operator|!
name|receivesEmail
condition|?
literal|"<span title='"
operator|+
name|roleRef
operator|+
operator|(
name|receivesEmail
condition|?
literal|""
else|:
literal|", * No Email for this Role"
operator|)
operator|+
literal|"' style='font-weight:normal;'>"
operator|+
name|roleRef
operator|+
operator|(
name|receivesEmail
condition|?
literal|""
else|:
literal|"*"
operator|)
operator|+
literal|"</span>"
else|:
name|roleRef
operator|)
else|:
name|roleRef
operator|+
operator|(
name|receivesEmail
condition|?
literal|""
else|:
literal|"*"
operator|)
operator|)
expr_stmt|;
block|}
name|roleOrd
operator|+=
name|title
expr_stmt|;
if|if
condition|(
name|mgrRole
operator|.
name|getRole
argument_list|()
operator|.
name|hasRight
argument_list|(
name|Right
operator|.
name|SessionIndependent
argument_list|)
operator|||
operator|(
name|mgrRole
operator|.
name|getRole
argument_list|()
operator|.
name|hasRight
argument_list|(
name|Right
operator|.
name|SessionIndependentIfNoSessionGiven
argument_list|)
operator|&&
name|manager
operator|.
name|getDepartments
argument_list|()
operator|.
name|isEmpty
argument_list|()
operator|)
condition|)
name|sessionIndependent
operator|=
literal|true
expr_stmt|;
block|}
comment|// Departments
name|boolean
name|departmental
init|=
literal|false
decl_stmt|;
for|for
control|(
name|Iterator
name|di
init|=
name|depts
operator|.
name|iterator
argument_list|()
init|;
name|di
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|Department
name|dept
init|=
operator|(
name|Department
operator|)
name|di
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
name|dept
operator|.
name|getSession
argument_list|()
operator|.
name|getUniqueId
argument_list|()
operator|.
name|equals
argument_list|(
name|currentAcadSession
argument_list|)
condition|)
continue|continue;
name|departmental
operator|=
literal|true
expr_stmt|;
if|if
condition|(
name|deptStr
operator|.
name|trim
argument_list|()
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
name|deptStr
operator|+=
literal|", "
operator|+
operator|(
name|html
condition|?
literal|"<br>"
else|:
literal|"\n"
operator|)
expr_stmt|;
name|deptStr
operator|+=
operator|(
name|html
condition|?
literal|"<span title='"
operator|+
name|dept
operator|.
name|getHtmlTitle
argument_list|()
operator|+
literal|"'>"
operator|+
operator|(
name|dept
operator|.
name|isExternalManager
argument_list|()
condition|?
literal|"<b>"
else|:
literal|""
operator|)
operator|+
name|dept
operator|.
name|getDeptCode
argument_list|()
operator|+
operator|(
name|dept
operator|.
name|getAbbreviation
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
literal|": "
operator|+
name|dept
operator|.
name|getAbbreviation
argument_list|()
operator|.
name|trim
argument_list|()
operator|)
operator|+
operator|(
name|dept
operator|.
name|isExternalManager
argument_list|()
condition|?
literal|"</b>"
else|:
literal|""
operator|)
operator|+
literal|"</span>"
else|:
operator|(
name|dept
operator|.
name|isExternalManager
argument_list|()
condition|?
literal|"@@BOLD "
else|:
literal|""
operator|)
operator|+
name|dept
operator|.
name|getDeptCode
argument_list|()
operator|+
operator|(
name|dept
operator|.
name|getAbbreviation
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
literal|": "
operator|+
name|dept
operator|.
name|getAbbreviation
argument_list|()
operator|.
name|trim
argument_list|()
operator|)
operator|+
operator|(
name|dept
operator|.
name|isExternalManager
argument_list|()
condition|?
literal|"@@END_BOLD "
else|:
literal|""
operator|)
operator|)
expr_stmt|;
comment|// Construct SubjectArea List
name|Set
name|saList
init|=
name|dept
operator|.
name|getSubjectAreas
argument_list|()
decl_stmt|;
if|if
condition|(
name|saList
operator|!=
literal|null
operator|&&
name|saList
operator|.
name|size
argument_list|()
operator|>
literal|0
condition|)
block|{
for|for
control|(
name|Iterator
name|si
init|=
name|saList
operator|.
name|iterator
argument_list|()
init|;
name|si
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|SubjectArea
name|sa
init|=
operator|(
name|SubjectArea
operator|)
name|si
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|subjectList
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
name|subjectList
operator|+=
literal|","
operator|+
operator|(
name|html
condition|?
literal|"<br>"
else|:
literal|"\n"
operator|)
expr_stmt|;
name|subjectList
operator|+=
operator|(
name|html
condition|?
literal|"<span title='"
operator|+
name|sa
operator|.
name|getTitle
argument_list|()
operator|+
literal|"'>"
operator|+
name|sa
operator|.
name|getSubjectAreaAbbreviation
argument_list|()
operator|.
name|trim
argument_list|()
operator|+
literal|"</span>"
else|:
name|sa
operator|.
name|getSubjectAreaAbbreviation
argument_list|()
operator|.
name|trim
argument_list|()
operator|)
expr_stmt|;
block|}
block|}
block|}
if|if
condition|(
operator|!
name|showAll
operator|&&
operator|!
name|sessionIndependent
operator|&&
operator|!
name|departmental
condition|)
continue|continue;
if|if
condition|(
name|html
operator|&&
name|deptStr
operator|.
name|trim
argument_list|()
operator|.
name|length
argument_list|()
operator|==
literal|0
condition|)
name|deptStr
operator|=
literal|"&nbsp;"
expr_stmt|;
if|if
condition|(
name|html
operator|&&
name|subjectList
operator|.
name|trim
argument_list|()
operator|.
name|length
argument_list|()
operator|==
literal|0
condition|)
name|subjectList
operator|=
literal|"&nbsp;"
expr_stmt|;
name|String
name|solverGroupStr
init|=
literal|""
decl_stmt|;
for|for
control|(
name|Iterator
name|i
init|=
name|manager
operator|.
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
name|sg
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
name|sg
operator|.
name|getSession
argument_list|()
operator|.
name|getUniqueId
argument_list|()
operator|.
name|equals
argument_list|(
name|currentAcadSession
argument_list|)
condition|)
continue|continue;
if|if
condition|(
name|solverGroupStr
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
name|solverGroupStr
operator|+=
literal|","
operator|+
operator|(
name|html
condition|?
literal|"<br>"
else|:
literal|"\n"
operator|)
expr_stmt|;
name|solverGroupStr
operator|+=
operator|(
name|html
condition|?
literal|"<span title='"
operator|+
name|sg
operator|.
name|getName
argument_list|()
operator|+
literal|"'>"
operator|+
name|sg
operator|.
name|getAbbv
argument_list|()
operator|+
literal|"</span>"
else|:
name|sg
operator|.
name|getAbbv
argument_list|()
operator|)
expr_stmt|;
block|}
if|if
condition|(
name|html
operator|&&
name|solverGroupStr
operator|.
name|length
argument_list|()
operator|==
literal|0
condition|)
name|solverGroupStr
operator|=
literal|"&nbsp;"
expr_stmt|;
name|String
name|lastChangeStr
init|=
literal|null
decl_stmt|;
name|Long
name|lastChangeCmp
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|dispLastChanges
condition|)
block|{
name|List
name|changes
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|currentAcadSession
operator|!=
literal|null
condition|)
name|changes
operator|=
name|ChangeLog
operator|.
name|findLastNChanges
argument_list|(
name|currentAcadSession
argument_list|,
name|manager
operator|.
name|getUniqueId
argument_list|()
argument_list|,
literal|null
argument_list|,
literal|null
argument_list|,
literal|1
argument_list|)
expr_stmt|;
name|ChangeLog
name|lastChange
init|=
operator|(
name|changes
operator|==
literal|null
operator|||
name|changes
operator|.
name|isEmpty
argument_list|()
condition|?
literal|null
else|:
operator|(
name|ChangeLog
operator|)
name|changes
operator|.
name|get
argument_list|(
literal|0
argument_list|)
operator|)
decl_stmt|;
if|if
condition|(
name|html
condition|)
name|lastChangeStr
operator|=
operator|(
name|lastChange
operator|==
literal|null
condition|?
literal|"&nbsp;"
else|:
literal|"<span title='"
operator|+
name|lastChange
operator|.
name|getLabel
argument_list|()
operator|+
literal|"'>"
operator|+
name|lastChange
operator|.
name|getSourceTitle
argument_list|()
operator|+
literal|" ("
operator|+
name|lastChange
operator|.
name|getOperationTitle
argument_list|()
operator|+
literal|") on "
operator|+
name|ChangeLog
operator|.
name|sDFdate
operator|.
name|format
argument_list|(
name|lastChange
operator|.
name|getTimeStamp
argument_list|()
argument_list|)
operator|+
literal|"</span>"
operator|)
expr_stmt|;
else|else
name|lastChangeStr
operator|=
operator|(
name|lastChange
operator|==
literal|null
condition|?
literal|""
else|:
name|lastChange
operator|.
name|getSourceTitle
argument_list|()
operator|+
literal|" ("
operator|+
name|lastChange
operator|.
name|getOperationTitle
argument_list|()
operator|+
literal|") on "
operator|+
name|ChangeLog
operator|.
name|sDFdate
operator|.
name|format
argument_list|(
name|lastChange
operator|.
name|getTimeStamp
argument_list|()
argument_list|)
operator|)
expr_stmt|;
name|lastChangeCmp
operator|=
name|Long
operator|.
name|valueOf
argument_list|(
name|lastChange
operator|==
literal|null
condition|?
literal|0
else|:
name|lastChange
operator|.
name|getTimeStamp
argument_list|()
operator|.
name|getTime
argument_list|()
argument_list|)
expr_stmt|;
block|}
comment|// Add to web table
name|webTable
operator|.
name|addLine
argument_list|(
name|onClick
argument_list|,
operator|new
name|String
index|[]
block|{
name|roleStr
block|,
operator|(
name|html
condition|?
literal|"<A name='"
operator|+
name|manager
operator|.
name|getUniqueId
argument_list|()
operator|+
literal|"'>"
operator|+
name|puid
operator|+
literal|"&nbsp;</A>"
else|:
name|puid
operator|)
block|,
name|fullName
block|,
name|email
block|,
name|deptStr
block|,
name|subjectList
block|,
name|solverGroupStr
block|,
name|lastChangeStr
block|}
argument_list|,
operator|new
name|Comparable
index|[]
block|{
name|roleOrd
block|,
name|puid
block|,
name|fullName
block|,
name|email
block|,
name|deptStr
block|,
name|subjectList
block|,
name|solverGroupStr
block|,
name|lastChangeCmp
block|}
argument_list|)
expr_stmt|;
block|}
return|return
name|webTable
return|;
block|}
block|}
end_class

end_unit

