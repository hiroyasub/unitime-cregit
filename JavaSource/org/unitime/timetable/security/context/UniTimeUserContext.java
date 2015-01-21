begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * Licensed to The Apereo Foundation under one or more contributor license  * agreements. See the NOTICE file distributed with this work for  * additional information regarding copyright ownership.  *  * The Apereo Foundation licenses this file to you under the Apache License,  * Version 2.0 (the "License"); you may not use this file except in  * compliance with the License. You may obtain a copy of the License at:  *  * http://www.apache.org/licenses/LICENSE-2.0  *  * Unless required by applicable law or agreed to in writing, software  * distributed under the License is distributed on an "AS IS" BASIS,  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  *  * See the License for the specific language governing permissions and  * limitations under the License.  *   */
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
name|context
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Calendar
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Date
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
name|TreeSet
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
name|ApplicationProperties
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
name|ApplicationProperty
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
name|ManagerSettings
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
name|Settings
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
name|Student
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
name|model
operator|.
name|dao
operator|.
name|UserDataDAO
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
name|UserAuthority
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
name|authority
operator|.
name|RoleAuthority
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
name|qualifiers
operator|.
name|SimpleQualifier
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
name|HasRights
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
name|LoginManager
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|UniTimeUserContext
extends|extends
name|AbstractUserContext
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|1L
decl_stmt|;
specifier|private
name|String
name|iId
decl_stmt|,
name|iPassword
decl_stmt|,
name|iName
decl_stmt|,
name|iLogin
decl_stmt|,
name|iEmail
decl_stmt|;
specifier|public
name|UniTimeUserContext
parameter_list|(
name|String
name|userId
parameter_list|,
name|String
name|login
parameter_list|,
name|String
name|name
parameter_list|,
name|String
name|password
parameter_list|)
block|{
name|iLogin
operator|=
name|login
expr_stmt|;
name|iPassword
operator|=
name|password
expr_stmt|;
name|iId
operator|=
name|userId
expr_stmt|;
name|iName
operator|=
name|name
expr_stmt|;
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
init|=
name|TimetableManagerDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|createNewSession
argument_list|()
decl_stmt|;
try|try
block|{
for|for
control|(
name|UserData
name|data
range|:
operator|(
name|List
argument_list|<
name|UserData
argument_list|>
operator|)
name|hibSession
operator|.
name|createQuery
argument_list|(
literal|"from UserData where externalUniqueId = :id"
argument_list|)
operator|.
name|setString
argument_list|(
literal|"id"
argument_list|,
name|userId
argument_list|)
operator|.
name|list
argument_list|()
control|)
block|{
name|getProperties
argument_list|()
operator|.
name|put
argument_list|(
name|data
operator|.
name|getName
argument_list|()
argument_list|,
name|data
operator|.
name|getValue
argument_list|()
argument_list|)
expr_stmt|;
block|}
for|for
control|(
name|Settings
name|setting
range|:
operator|(
name|List
argument_list|<
name|Settings
argument_list|>
operator|)
name|hibSession
operator|.
name|createQuery
argument_list|(
literal|"from Settings"
argument_list|)
operator|.
name|list
argument_list|()
control|)
block|{
if|if
condition|(
name|setting
operator|.
name|getDefaultValue
argument_list|()
operator|!=
literal|null
condition|)
name|getProperties
argument_list|()
operator|.
name|put
argument_list|(
name|setting
operator|.
name|getKey
argument_list|()
argument_list|,
name|setting
operator|.
name|getDefaultValue
argument_list|()
argument_list|)
expr_stmt|;
block|}
for|for
control|(
name|ManagerSettings
name|setting
range|:
operator|(
name|List
argument_list|<
name|ManagerSettings
argument_list|>
operator|)
name|hibSession
operator|.
name|createQuery
argument_list|(
literal|"from ManagerSettings where manager.externalUniqueId = :id"
argument_list|)
operator|.
name|setString
argument_list|(
literal|"id"
argument_list|,
name|userId
argument_list|)
operator|.
name|list
argument_list|()
control|)
block|{
if|if
condition|(
name|setting
operator|.
name|getValue
argument_list|()
operator|!=
literal|null
condition|)
name|getProperties
argument_list|()
operator|.
name|put
argument_list|(
name|setting
operator|.
name|getKey
argument_list|()
operator|.
name|getKey
argument_list|()
argument_list|,
name|setting
operator|.
name|getValue
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|Long
name|sessionId
init|=
literal|null
decl_stmt|;
if|if
condition|(
literal|"true"
operator|.
name|equals
argument_list|(
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
name|ApplicationProperty
operator|.
name|KeepLastUsedAcademicSession
argument_list|)
argument_list|)
condition|)
block|{
name|String
name|lastSessionId
init|=
name|getProperty
argument_list|(
name|UserProperty
operator|.
name|LastAcademicSession
argument_list|)
decl_stmt|;
if|if
condition|(
name|lastSessionId
operator|!=
literal|null
condition|)
name|sessionId
operator|=
name|Long
operator|.
name|valueOf
argument_list|(
name|lastSessionId
argument_list|)
expr_stmt|;
block|}
name|TimetableManager
name|manager
init|=
operator|(
name|TimetableManager
operator|)
name|hibSession
operator|.
name|createQuery
argument_list|(
literal|"from TimetableManager where externalUniqueId = :id"
argument_list|)
operator|.
name|setString
argument_list|(
literal|"id"
argument_list|,
name|userId
argument_list|)
operator|.
name|setMaxResults
argument_list|(
literal|1
argument_list|)
operator|.
name|uniqueResult
argument_list|()
decl_stmt|;
if|if
condition|(
name|manager
operator|!=
literal|null
condition|)
block|{
name|iName
operator|=
name|manager
operator|.
name|getName
argument_list|()
expr_stmt|;
name|iEmail
operator|=
name|manager
operator|.
name|getEmailAddress
argument_list|()
expr_stmt|;
name|Roles
name|primary
init|=
literal|null
decl_stmt|;
name|TreeSet
argument_list|<
name|Session
argument_list|>
name|primarySessions
init|=
literal|null
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
operator|!
name|role
operator|.
name|getRole
argument_list|()
operator|.
name|isEnabled
argument_list|()
condition|)
continue|continue;
name|TreeSet
argument_list|<
name|Session
argument_list|>
name|sessions
init|=
operator|new
name|TreeSet
argument_list|<
name|Session
argument_list|>
argument_list|()
decl_stmt|;
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
name|SessionIndependent
argument_list|)
operator|||
operator|(
name|sessions
operator|.
name|isEmpty
argument_list|()
operator|&&
name|role
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
operator|)
condition|)
name|sessions
operator|.
name|addAll
argument_list|(
name|SessionDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|findAll
argument_list|(
name|hibSession
argument_list|)
argument_list|)
expr_stmt|;
else|else
for|for
control|(
name|Department
name|department
range|:
name|manager
operator|.
name|getDepartments
argument_list|()
control|)
name|sessions
operator|.
name|add
argument_list|(
name|department
operator|.
name|getSession
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|role
operator|.
name|isPrimary
argument_list|()
operator|&&
name|primary
operator|==
literal|null
condition|)
block|{
name|primary
operator|=
name|role
operator|.
name|getRole
argument_list|()
expr_stmt|;
name|primarySessions
operator|=
name|sessions
expr_stmt|;
block|}
for|for
control|(
name|Session
name|session
range|:
name|sessions
control|)
block|{
if|if
condition|(
name|session
operator|.
name|getStatusType
argument_list|()
operator|==
literal|null
operator|||
name|session
operator|.
name|getStatusType
argument_list|()
operator|.
name|isTestSession
argument_list|()
condition|)
block|{
if|if
condition|(
operator|!
name|role
operator|.
name|getRole
argument_list|()
operator|.
name|hasRight
argument_list|(
name|Right
operator|.
name|AllowTestSessions
argument_list|)
condition|)
continue|continue;
block|}
name|UserAuthority
name|authority
init|=
operator|new
name|RoleAuthority
argument_list|(
name|manager
operator|.
name|getUniqueId
argument_list|()
argument_list|,
name|role
operator|.
name|getRole
argument_list|()
argument_list|)
decl_stmt|;
name|authority
operator|.
name|addQualifier
argument_list|(
name|session
argument_list|)
expr_stmt|;
name|authority
operator|.
name|addQualifier
argument_list|(
name|manager
argument_list|)
expr_stmt|;
for|for
control|(
name|Department
name|department
range|:
name|manager
operator|.
name|getDepartments
argument_list|()
control|)
if|if
condition|(
name|department
operator|.
name|getSession
argument_list|()
operator|.
name|equals
argument_list|(
name|session
argument_list|)
condition|)
name|authority
operator|.
name|addQualifier
argument_list|(
name|department
argument_list|)
expr_stmt|;
for|for
control|(
name|SolverGroup
name|group
range|:
name|manager
operator|.
name|getSolverGroups
argument_list|()
control|)
for|for
control|(
name|Department
name|department
range|:
name|group
operator|.
name|getDepartments
argument_list|()
control|)
if|if
condition|(
name|department
operator|.
name|getSession
argument_list|()
operator|.
name|equals
argument_list|(
name|session
argument_list|)
condition|)
block|{
name|authority
operator|.
name|addQualifier
argument_list|(
name|group
argument_list|)
expr_stmt|;
break|break;
block|}
name|addAuthority
argument_list|(
name|authority
argument_list|)
expr_stmt|;
block|}
block|}
if|if
condition|(
name|sessionId
operator|==
literal|null
operator|&&
name|primary
operator|!=
literal|null
condition|)
block|{
name|Session
name|session
init|=
name|defaultSession
argument_list|(
name|primarySessions
argument_list|,
name|primary
argument_list|)
decl_stmt|;
if|if
condition|(
name|session
operator|!=
literal|null
condition|)
name|sessionId
operator|=
name|session
operator|.
name|getUniqueId
argument_list|()
expr_stmt|;
block|}
if|if
condition|(
name|sessionId
operator|!=
literal|null
operator|&&
name|primary
operator|!=
literal|null
condition|)
block|{
name|List
argument_list|<
name|?
extends|extends
name|UserAuthority
argument_list|>
name|authorities
init|=
name|getAuthorities
argument_list|(
name|primary
operator|.
name|getReference
argument_list|()
argument_list|,
operator|new
name|SimpleQualifier
argument_list|(
literal|"Session"
argument_list|,
name|sessionId
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|authorities
operator|.
name|isEmpty
argument_list|()
condition|)
name|setCurrentAuthority
argument_list|(
name|authorities
operator|.
name|get
argument_list|(
literal|0
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
name|TreeSet
argument_list|<
name|Session
argument_list|>
name|sessions
init|=
operator|new
name|TreeSet
argument_list|<
name|Session
argument_list|>
argument_list|()
decl_stmt|;
name|Roles
name|instructorRole
init|=
name|Roles
operator|.
name|getRole
argument_list|(
name|Roles
operator|.
name|ROLE_INSTRUCTOR
argument_list|,
name|hibSession
argument_list|)
decl_stmt|;
if|if
condition|(
name|instructorRole
operator|!=
literal|null
operator|&&
name|instructorRole
operator|.
name|isEnabled
argument_list|()
condition|)
block|{
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
literal|"from DepartmentalInstructor where externalUniqueId = :id"
argument_list|)
operator|.
name|setString
argument_list|(
literal|"id"
argument_list|,
name|userId
argument_list|)
operator|.
name|list
argument_list|()
control|)
block|{
if|if
condition|(
name|iName
operator|==
literal|null
condition|)
name|iName
operator|=
name|instructor
operator|.
name|getName
argument_list|(
name|DepartmentalInstructor
operator|.
name|sNameFormatLastFirstMiddle
argument_list|)
expr_stmt|;
if|if
condition|(
name|iEmail
operator|==
literal|null
condition|)
name|iEmail
operator|=
name|instructor
operator|.
name|getEmail
argument_list|()
expr_stmt|;
name|List
argument_list|<
name|?
extends|extends
name|UserAuthority
argument_list|>
name|authorities
init|=
name|getAuthorities
argument_list|(
name|Roles
operator|.
name|ROLE_INSTRUCTOR
argument_list|,
name|instructor
operator|.
name|getDepartment
argument_list|()
operator|.
name|getSession
argument_list|()
argument_list|)
decl_stmt|;
name|UserAuthority
name|authority
init|=
operator|(
name|authorities
operator|.
name|isEmpty
argument_list|()
condition|?
literal|null
else|:
name|authorities
operator|.
name|get
argument_list|(
literal|0
argument_list|)
operator|)
decl_stmt|;
if|if
condition|(
name|authority
operator|==
literal|null
condition|)
block|{
name|authority
operator|=
operator|new
name|RoleAuthority
argument_list|(
name|instructor
operator|.
name|getUniqueId
argument_list|()
argument_list|,
name|instructorRole
argument_list|)
expr_stmt|;
name|authority
operator|.
name|addQualifier
argument_list|(
name|instructor
operator|.
name|getDepartment
argument_list|()
operator|.
name|getSession
argument_list|()
argument_list|)
expr_stmt|;
name|addAuthority
argument_list|(
name|authority
argument_list|)
expr_stmt|;
name|sessions
operator|.
name|add
argument_list|(
name|instructor
operator|.
name|getDepartment
argument_list|()
operator|.
name|getSession
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|authority
operator|.
name|addQualifier
argument_list|(
name|instructor
operator|.
name|getDepartment
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|instructor
operator|.
name|getRole
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|List
argument_list|<
name|?
extends|extends
name|UserAuthority
argument_list|>
name|instrRoleAuthorities
init|=
name|getAuthorities
argument_list|(
name|instructor
operator|.
name|getRole
argument_list|()
operator|.
name|getReference
argument_list|()
argument_list|,
name|instructor
operator|.
name|getDepartment
argument_list|()
operator|.
name|getSession
argument_list|()
argument_list|)
decl_stmt|;
name|UserAuthority
name|instrRoleAuthority
init|=
operator|(
name|instrRoleAuthorities
operator|.
name|isEmpty
argument_list|()
condition|?
literal|null
else|:
name|instrRoleAuthorities
operator|.
name|get
argument_list|(
literal|0
argument_list|)
operator|)
decl_stmt|;
if|if
condition|(
name|instrRoleAuthority
operator|==
literal|null
condition|)
block|{
name|instrRoleAuthority
operator|=
operator|new
name|RoleAuthority
argument_list|(
name|instructor
operator|.
name|getUniqueId
argument_list|()
argument_list|,
name|instructor
operator|.
name|getRole
argument_list|()
argument_list|)
expr_stmt|;
name|instrRoleAuthority
operator|.
name|addQualifier
argument_list|(
name|instructor
operator|.
name|getDepartment
argument_list|()
operator|.
name|getSession
argument_list|()
argument_list|)
expr_stmt|;
name|addAuthority
argument_list|(
name|instrRoleAuthority
argument_list|)
expr_stmt|;
block|}
name|instrRoleAuthority
operator|.
name|addQualifier
argument_list|(
name|instructor
operator|.
name|getDepartment
argument_list|()
argument_list|)
expr_stmt|;
name|instrRoleAuthority
operator|.
name|addQualifier
argument_list|(
operator|new
name|SimpleQualifier
argument_list|(
literal|"Role"
argument_list|,
name|Roles
operator|.
name|ROLE_INSTRUCTOR
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
name|Roles
name|studentRole
init|=
name|Roles
operator|.
name|getRole
argument_list|(
name|Roles
operator|.
name|ROLE_STUDENT
argument_list|,
name|hibSession
argument_list|)
decl_stmt|;
if|if
condition|(
name|studentRole
operator|!=
literal|null
operator|&&
name|studentRole
operator|.
name|isEnabled
argument_list|()
condition|)
block|{
for|for
control|(
name|Student
name|student
range|:
operator|(
name|List
argument_list|<
name|Student
argument_list|>
operator|)
name|hibSession
operator|.
name|createQuery
argument_list|(
literal|"from Student where externalUniqueId = :id"
argument_list|)
operator|.
name|setString
argument_list|(
literal|"id"
argument_list|,
name|userId
argument_list|)
operator|.
name|list
argument_list|()
control|)
block|{
if|if
condition|(
name|iName
operator|==
literal|null
condition|)
name|iName
operator|=
name|student
operator|.
name|getName
argument_list|(
name|DepartmentalInstructor
operator|.
name|sNameFormatLastFirstMiddle
argument_list|)
expr_stmt|;
if|if
condition|(
name|iEmail
operator|==
literal|null
condition|)
name|iEmail
operator|=
name|student
operator|.
name|getEmail
argument_list|()
expr_stmt|;
name|UserAuthority
name|authority
init|=
operator|new
name|RoleAuthority
argument_list|(
name|student
operator|.
name|getUniqueId
argument_list|()
argument_list|,
name|studentRole
argument_list|)
decl_stmt|;
name|authority
operator|.
name|addQualifier
argument_list|(
name|student
operator|.
name|getSession
argument_list|()
argument_list|)
expr_stmt|;
name|addAuthority
argument_list|(
name|authority
argument_list|)
expr_stmt|;
name|sessions
operator|.
name|add
argument_list|(
name|student
operator|.
name|getSession
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
if|if
condition|(
name|sessionId
operator|==
literal|null
condition|)
block|{
name|Session
name|session
init|=
name|defaultSession
argument_list|(
name|sessions
argument_list|,
literal|null
argument_list|)
decl_stmt|;
if|if
condition|(
name|session
operator|!=
literal|null
condition|)
name|sessionId
operator|=
name|session
operator|.
name|getUniqueId
argument_list|()
expr_stmt|;
block|}
if|if
condition|(
name|getCurrentAuthority
argument_list|()
operator|==
literal|null
operator|&&
name|sessionId
operator|!=
literal|null
condition|)
block|{
name|List
argument_list|<
name|?
extends|extends
name|UserAuthority
argument_list|>
name|authorities
init|=
name|getAuthorities
argument_list|(
name|Roles
operator|.
name|ROLE_INSTRUCTOR
argument_list|,
operator|new
name|SimpleQualifier
argument_list|(
literal|"Session"
argument_list|,
name|sessionId
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|authorities
operator|.
name|isEmpty
argument_list|()
condition|)
name|setCurrentAuthority
argument_list|(
name|authorities
operator|.
name|get
argument_list|(
literal|0
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|getCurrentAuthority
argument_list|()
operator|==
literal|null
operator|&&
name|sessionId
operator|!=
literal|null
condition|)
block|{
name|List
argument_list|<
name|?
extends|extends
name|UserAuthority
argument_list|>
name|authorities
init|=
name|getAuthorities
argument_list|(
name|Roles
operator|.
name|ROLE_STUDENT
argument_list|,
operator|new
name|SimpleQualifier
argument_list|(
literal|"Session"
argument_list|,
name|sessionId
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|authorities
operator|.
name|isEmpty
argument_list|()
condition|)
name|setCurrentAuthority
argument_list|(
name|authorities
operator|.
name|get
argument_list|(
literal|0
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|Roles
name|noRole
init|=
name|Roles
operator|.
name|getRole
argument_list|(
name|Roles
operator|.
name|ROLE_NONE
argument_list|,
name|hibSession
argument_list|)
decl_stmt|;
if|if
condition|(
name|noRole
operator|!=
literal|null
operator|&&
name|noRole
operator|.
name|isEnabled
argument_list|()
condition|)
block|{
for|for
control|(
name|Session
name|session
range|:
operator|new
name|TreeSet
argument_list|<
name|Session
argument_list|>
argument_list|(
name|SessionDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|findAll
argument_list|(
name|hibSession
argument_list|)
argument_list|)
control|)
block|{
if|if
condition|(
name|session
operator|.
name|getStatusType
argument_list|()
operator|==
literal|null
operator|||
operator|!
name|session
operator|.
name|getStatusType
argument_list|()
operator|.
name|isAllowNoRole
argument_list|()
operator|||
name|session
operator|.
name|getStatusType
argument_list|()
operator|.
name|isTestSession
argument_list|()
condition|)
continue|continue;
name|List
argument_list|<
name|?
extends|extends
name|UserAuthority
argument_list|>
name|authorities
init|=
name|getAuthorities
argument_list|(
literal|null
argument_list|,
operator|new
name|SimpleQualifier
argument_list|(
literal|"Session"
argument_list|,
name|session
operator|.
name|getUniqueId
argument_list|()
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|authorities
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|UserAuthority
name|authority
init|=
operator|new
name|RoleAuthority
argument_list|(
operator|-
literal|1l
argument_list|,
name|noRole
argument_list|)
decl_stmt|;
name|authority
operator|.
name|addQualifier
argument_list|(
name|session
argument_list|)
expr_stmt|;
name|addAuthority
argument_list|(
name|authority
argument_list|)
expr_stmt|;
name|sessions
operator|.
name|add
argument_list|(
name|session
argument_list|)
expr_stmt|;
block|}
block|}
block|}
if|if
condition|(
name|getCurrentAuthority
argument_list|()
operator|==
literal|null
condition|)
block|{
name|Session
name|session
init|=
name|defaultSession
argument_list|(
name|sessions
argument_list|,
literal|null
argument_list|)
decl_stmt|;
if|if
condition|(
name|session
operator|!=
literal|null
condition|)
block|{
name|List
argument_list|<
name|?
extends|extends
name|UserAuthority
argument_list|>
name|authorities
init|=
name|getAuthorities
argument_list|(
literal|null
argument_list|,
operator|new
name|SimpleQualifier
argument_list|(
literal|"Session"
argument_list|,
name|session
operator|.
name|getUniqueId
argument_list|()
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|authorities
operator|.
name|isEmpty
argument_list|()
condition|)
name|setCurrentAuthority
argument_list|(
name|authorities
operator|.
name|get
argument_list|(
literal|0
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
finally|finally
block|{
name|hibSession
operator|.
name|close
argument_list|()
expr_stmt|;
block|}
if|if
condition|(
name|iName
operator|==
literal|null
condition|)
name|iName
operator|=
name|iLogin
expr_stmt|;
block|}
specifier|public
specifier|static
name|Session
name|defaultSession
parameter_list|(
name|TreeSet
argument_list|<
name|Session
argument_list|>
name|sessions
parameter_list|,
name|HasRights
name|role
parameter_list|)
block|{
if|if
condition|(
name|sessions
operator|==
literal|null
operator|||
name|sessions
operator|.
name|isEmpty
argument_list|()
condition|)
return|return
literal|null
return|;
comment|// no session -> no default
comment|//try to pick among active sessions first (check that all active sessions are of the same initiative)
name|String
name|initiative
init|=
literal|null
decl_stmt|;
name|Session
name|lastActive
init|=
literal|null
decl_stmt|;
name|Session
name|currentActive
init|=
literal|null
decl_stmt|;
name|Session
name|firstFutureSession
init|=
literal|null
decl_stmt|;
name|Calendar
name|cal
init|=
name|Calendar
operator|.
name|getInstance
argument_list|(
name|Localization
operator|.
name|getJavaLocale
argument_list|()
argument_list|)
decl_stmt|;
name|cal
operator|.
name|set
argument_list|(
name|Calendar
operator|.
name|HOUR_OF_DAY
argument_list|,
literal|0
argument_list|)
expr_stmt|;
name|cal
operator|.
name|set
argument_list|(
name|Calendar
operator|.
name|MINUTE
argument_list|,
literal|0
argument_list|)
expr_stmt|;
name|cal
operator|.
name|set
argument_list|(
name|Calendar
operator|.
name|SECOND
argument_list|,
literal|0
argument_list|)
expr_stmt|;
name|cal
operator|.
name|set
argument_list|(
name|Calendar
operator|.
name|MILLISECOND
argument_list|,
literal|0
argument_list|)
expr_stmt|;
name|Date
name|today
init|=
name|cal
operator|.
name|getTime
argument_list|()
decl_stmt|;
for|for
control|(
name|Session
name|session
range|:
name|sessions
control|)
block|{
if|if
condition|(
name|session
operator|.
name|getStatusType
argument_list|()
operator|==
literal|null
operator|||
operator|!
name|session
operator|.
name|getStatusType
argument_list|()
operator|.
name|isActive
argument_list|()
operator|||
name|session
operator|.
name|getStatusType
argument_list|()
operator|.
name|isTestSession
argument_list|()
condition|)
continue|continue;
if|if
condition|(
name|initiative
operator|==
literal|null
condition|)
name|initiative
operator|=
name|session
operator|.
name|getAcademicInitiative
argument_list|()
expr_stmt|;
if|else if
condition|(
operator|!
name|initiative
operator|.
name|equals
argument_list|(
name|session
operator|.
name|getAcademicInitiative
argument_list|()
argument_list|)
condition|)
return|return
literal|null
return|;
comment|// multiple initiatives -> no default
name|Date
name|begin
init|=
name|session
operator|.
name|getEventBeginDate
argument_list|()
decl_stmt|;
name|cal
operator|.
name|setTime
argument_list|(
name|session
operator|.
name|getEventEndDate
argument_list|()
argument_list|)
expr_stmt|;
name|cal
operator|.
name|add
argument_list|(
name|Calendar
operator|.
name|DAY_OF_YEAR
argument_list|,
literal|1
argument_list|)
expr_stmt|;
name|Date
name|end
init|=
name|cal
operator|.
name|getTime
argument_list|()
decl_stmt|;
if|if
condition|(
name|currentActive
operator|==
literal|null
operator|&&
operator|!
name|begin
operator|.
name|after
argument_list|(
name|today
argument_list|)
operator|&&
name|today
operator|.
name|before
argument_list|(
name|end
argument_list|)
condition|)
name|currentActive
operator|=
name|session
expr_stmt|;
if|if
condition|(
name|currentActive
operator|!=
literal|null
operator|&&
name|firstFutureSession
operator|==
literal|null
operator|&&
operator|!
name|currentActive
operator|.
name|equals
argument_list|(
name|session
argument_list|)
condition|)
name|firstFutureSession
operator|=
name|session
expr_stmt|;
if|if
condition|(
name|currentActive
operator|==
literal|null
operator|&&
name|firstFutureSession
operator|==
literal|null
operator|&&
name|today
operator|.
name|before
argument_list|(
name|begin
argument_list|)
condition|)
name|firstFutureSession
operator|=
name|session
expr_stmt|;
name|lastActive
operator|=
name|session
expr_stmt|;
block|}
if|if
condition|(
name|role
operator|!=
literal|null
operator|&&
name|role
operator|.
name|hasRight
argument_list|(
name|Right
operator|.
name|SessionDefaultFirstFuture
argument_list|)
condition|)
block|{
if|if
condition|(
name|firstFutureSession
operator|!=
literal|null
condition|)
return|return
name|firstFutureSession
return|;
if|if
condition|(
name|currentActive
operator|!=
literal|null
condition|)
return|return
name|currentActive
return|;
block|}
if|if
condition|(
name|role
operator|!=
literal|null
operator|&&
name|role
operator|.
name|hasRight
argument_list|(
name|Right
operator|.
name|SessionDefaultFirstExamination
argument_list|)
condition|)
block|{
if|if
condition|(
name|currentActive
operator|!=
literal|null
operator|&&
operator|!
name|currentActive
operator|.
name|getStatusType
argument_list|()
operator|.
name|canNoRoleReportExamFinal
argument_list|()
condition|)
return|return
name|currentActive
return|;
if|if
condition|(
name|firstFutureSession
operator|!=
literal|null
condition|)
return|return
name|firstFutureSession
return|;
block|}
if|if
condition|(
name|currentActive
operator|!=
literal|null
condition|)
return|return
name|currentActive
return|;
if|if
condition|(
name|firstFutureSession
operator|!=
literal|null
condition|)
return|return
name|firstFutureSession
return|;
if|if
condition|(
name|lastActive
operator|!=
literal|null
condition|)
return|return
name|lastActive
return|;
name|Session
name|lastNoTest
init|=
literal|null
decl_stmt|;
for|for
control|(
name|Session
name|session
range|:
name|sessions
control|)
block|{
if|if
condition|(
name|session
operator|.
name|getStatusType
argument_list|()
operator|==
literal|null
operator|||
name|session
operator|.
name|getStatusType
argument_list|()
operator|.
name|isTestSession
argument_list|()
condition|)
continue|continue;
name|Date
name|begin
init|=
name|session
operator|.
name|getEventBeginDate
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
name|begin
operator|.
name|after
argument_list|(
name|today
argument_list|)
condition|)
return|return
name|session
return|;
name|lastNoTest
operator|=
name|session
expr_stmt|;
block|}
return|return
name|lastNoTest
return|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|setCurrentAuthority
parameter_list|(
name|UserAuthority
name|authority
parameter_list|)
block|{
name|super
operator|.
name|setCurrentAuthority
argument_list|(
name|authority
argument_list|)
expr_stmt|;
if|if
condition|(
name|authority
operator|.
name|getAcademicSession
argument_list|()
operator|!=
literal|null
condition|)
name|setProperty
argument_list|(
name|UserProperty
operator|.
name|LastAcademicSession
argument_list|,
name|authority
operator|.
name|getAcademicSession
argument_list|()
operator|.
name|getQualifierId
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getExternalUserId
parameter_list|()
block|{
return|return
name|iId
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
name|iName
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getEmail
parameter_list|()
block|{
return|return
name|iEmail
return|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|setProperty
parameter_list|(
name|String
name|key
parameter_list|,
name|String
name|value
parameter_list|)
block|{
if|if
condition|(
name|value
operator|!=
literal|null
operator|&&
name|value
operator|.
name|isEmpty
argument_list|()
condition|)
name|value
operator|=
literal|null
expr_stmt|;
name|super
operator|.
name|setProperty
argument_list|(
name|key
argument_list|,
name|value
argument_list|)
expr_stmt|;
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
init|=
name|UserDataDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|createNewSession
argument_list|()
decl_stmt|;
try|try
block|{
name|Settings
name|settings
init|=
operator|(
name|Settings
operator|)
name|hibSession
operator|.
name|createQuery
argument_list|(
literal|"from Settings where key = :key"
argument_list|)
operator|.
name|setString
argument_list|(
literal|"key"
argument_list|,
name|key
argument_list|)
operator|.
name|setCacheable
argument_list|(
literal|true
argument_list|)
operator|.
name|setMaxResults
argument_list|(
literal|1
argument_list|)
operator|.
name|uniqueResult
argument_list|()
decl_stmt|;
if|if
condition|(
name|settings
operator|!=
literal|null
operator|&&
name|getCurrentAuthority
argument_list|()
operator|!=
literal|null
operator|&&
operator|!
name|getCurrentAuthority
argument_list|()
operator|.
name|getQualifiers
argument_list|(
literal|"TimetableManager"
argument_list|)
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|ManagerSettings
name|managerData
init|=
operator|(
name|ManagerSettings
operator|)
name|hibSession
operator|.
name|createQuery
argument_list|(
literal|"from ManagerSettings where key.key = :key and manager.externalUniqueId = :id"
argument_list|)
operator|.
name|setString
argument_list|(
literal|"key"
argument_list|,
name|key
argument_list|)
operator|.
name|setString
argument_list|(
literal|"id"
argument_list|,
name|getExternalUserId
argument_list|()
argument_list|)
operator|.
name|setCacheable
argument_list|(
literal|true
argument_list|)
operator|.
name|setMaxResults
argument_list|(
literal|1
argument_list|)
operator|.
name|uniqueResult
argument_list|()
decl_stmt|;
if|if
condition|(
name|value
operator|==
literal|null
operator|&&
name|managerData
operator|==
literal|null
condition|)
return|return;
if|if
condition|(
name|value
operator|!=
literal|null
operator|&&
name|managerData
operator|!=
literal|null
operator|&&
name|value
operator|.
name|equals
argument_list|(
name|managerData
operator|.
name|getValue
argument_list|()
argument_list|)
condition|)
return|return;
if|if
condition|(
name|managerData
operator|==
literal|null
condition|)
block|{
name|managerData
operator|=
operator|new
name|ManagerSettings
argument_list|()
expr_stmt|;
name|managerData
operator|.
name|setKey
argument_list|(
name|settings
argument_list|)
expr_stmt|;
name|managerData
operator|.
name|setManager
argument_list|(
name|TimetableManagerDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|get
argument_list|(
operator|(
name|Long
operator|)
name|getCurrentAuthority
argument_list|()
operator|.
name|getQualifiers
argument_list|(
literal|"TimetableManager"
argument_list|)
operator|.
name|get
argument_list|(
literal|0
argument_list|)
operator|.
name|getQualifierId
argument_list|()
argument_list|,
name|hibSession
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|managerData
operator|.
name|setValue
argument_list|(
name|value
argument_list|)
expr_stmt|;
if|if
condition|(
name|value
operator|==
literal|null
condition|)
name|hibSession
operator|.
name|delete
argument_list|(
name|managerData
argument_list|)
expr_stmt|;
else|else
name|hibSession
operator|.
name|saveOrUpdate
argument_list|(
name|managerData
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|UserData
name|userData
init|=
name|UserDataDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|get
argument_list|(
operator|new
name|UserData
argument_list|(
name|getExternalUserId
argument_list|()
argument_list|,
name|key
argument_list|)
argument_list|,
name|hibSession
argument_list|)
decl_stmt|;
if|if
condition|(
name|userData
operator|==
literal|null
operator|&&
name|value
operator|==
literal|null
condition|)
return|return;
if|if
condition|(
name|userData
operator|!=
literal|null
operator|&&
name|value
operator|!=
literal|null
operator|&&
name|value
operator|.
name|equals
argument_list|(
name|userData
operator|.
name|getValue
argument_list|()
argument_list|)
condition|)
return|return;
if|if
condition|(
name|userData
operator|==
literal|null
condition|)
name|userData
operator|=
operator|new
name|UserData
argument_list|(
name|getExternalUserId
argument_list|()
argument_list|,
name|key
argument_list|)
expr_stmt|;
if|if
condition|(
name|value
operator|==
literal|null
condition|)
block|{
name|hibSession
operator|.
name|delete
argument_list|(
name|userData
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|userData
operator|.
name|setValue
argument_list|(
name|value
argument_list|)
expr_stmt|;
name|hibSession
operator|.
name|saveOrUpdate
argument_list|(
name|userData
argument_list|)
expr_stmt|;
block|}
block|}
name|hibSession
operator|.
name|flush
argument_list|()
expr_stmt|;
block|}
finally|finally
block|{
name|hibSession
operator|.
name|close
argument_list|()
expr_stmt|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|String
name|getPassword
parameter_list|()
block|{
return|return
name|iPassword
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getUsername
parameter_list|()
block|{
return|return
name|iLogin
return|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|isAccountNonLocked
parameter_list|()
block|{
return|return
operator|!
name|LoginManager
operator|.
name|isUserLockedOut
argument_list|(
name|getUsername
argument_list|()
argument_list|,
operator|new
name|Date
argument_list|()
argument_list|)
return|;
block|}
block|}
end_class

end_unit

