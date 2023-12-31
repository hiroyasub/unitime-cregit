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
name|action
package|;
end_package

begin_import
import|import
name|java
operator|.
name|net
operator|.
name|URLEncoder
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
name|Map
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
name|context
operator|.
name|ApplicationContext
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
name|gwt
operator|.
name|services
operator|.
name|SectioningService
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
name|AcademicSessionProvider
operator|.
name|AcademicSessionInfo
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
name|StudentSectioningStatus
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
name|CourseOfferingDAO
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
name|StudentDAO
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
name|onlinesectioning
operator|.
name|OnlineSectioningServer
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
name|SectioningException
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
name|UserQualifier
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
name|context
operator|.
name|UniTimeUserContext
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
name|solver
operator|.
name|service
operator|.
name|SolverServerService
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
annotation|@
name|Service
argument_list|(
literal|"/studentScheduling"
argument_list|)
specifier|public
class|class
name|StudentSchedulingAction
extends|extends
name|Action
block|{
annotation|@
name|Autowired
name|SessionContext
name|sessionContext
decl_stmt|;
annotation|@
name|Autowired
name|SolverServerService
name|solverServerService
decl_stmt|;
annotation|@
name|Autowired
name|ApplicationContext
name|applicationContext
decl_stmt|;
specifier|protected
name|boolean
name|matchCampus
parameter_list|(
name|AcademicSessionInfo
name|info
parameter_list|,
name|String
name|campus
parameter_list|)
block|{
if|if
condition|(
name|info
operator|.
name|hasExternalCampus
argument_list|()
operator|&&
name|campus
operator|.
name|equalsIgnoreCase
argument_list|(
name|info
operator|.
name|getExternalCampus
argument_list|()
argument_list|)
condition|)
return|return
literal|true
return|;
return|return
name|campus
operator|.
name|equalsIgnoreCase
argument_list|(
name|info
operator|.
name|getCampus
argument_list|()
argument_list|)
return|;
block|}
specifier|protected
name|boolean
name|matchTerm
parameter_list|(
name|AcademicSessionInfo
name|info
parameter_list|,
name|String
name|term
parameter_list|)
block|{
if|if
condition|(
name|info
operator|.
name|hasExternalTerm
argument_list|()
operator|&&
name|term
operator|.
name|equalsIgnoreCase
argument_list|(
name|info
operator|.
name|getExternalTerm
argument_list|()
argument_list|)
condition|)
return|return
literal|true
return|;
return|return
name|term
operator|.
name|equalsIgnoreCase
argument_list|(
name|info
operator|.
name|getTerm
argument_list|()
operator|+
name|info
operator|.
name|getYear
argument_list|()
argument_list|)
operator|||
name|term
operator|.
name|equalsIgnoreCase
argument_list|(
name|info
operator|.
name|getYear
argument_list|()
operator|+
name|info
operator|.
name|getTerm
argument_list|()
argument_list|)
operator|||
name|term
operator|.
name|equalsIgnoreCase
argument_list|(
name|info
operator|.
name|getTerm
argument_list|()
operator|+
name|info
operator|.
name|getYear
argument_list|()
operator|+
name|info
operator|.
name|getCampus
argument_list|()
argument_list|)
return|;
block|}
specifier|protected
name|boolean
name|matchSession
parameter_list|(
name|AcademicSessionInfo
name|info
parameter_list|,
name|String
name|session
parameter_list|)
block|{
if|if
condition|(
name|info
operator|.
name|hasExternalTerm
argument_list|()
operator|&&
name|info
operator|.
name|hasExternalCampus
argument_list|()
operator|&&
name|session
operator|.
name|equalsIgnoreCase
argument_list|(
name|info
operator|.
name|getExternalTerm
argument_list|()
operator|+
name|info
operator|.
name|hasExternalCampus
argument_list|()
argument_list|)
condition|)
return|return
literal|true
return|;
return|return
name|session
operator|.
name|equalsIgnoreCase
argument_list|(
name|info
operator|.
name|getTerm
argument_list|()
operator|+
name|info
operator|.
name|getYear
argument_list|()
operator|+
name|info
operator|.
name|getCampus
argument_list|()
argument_list|)
operator|||
name|session
operator|.
name|equalsIgnoreCase
argument_list|(
name|info
operator|.
name|getTerm
argument_list|()
operator|+
name|info
operator|.
name|getYear
argument_list|()
argument_list|)
operator|||
name|session
operator|.
name|equals
argument_list|(
name|info
operator|.
name|getSessionId
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
return|;
block|}
specifier|public
name|boolean
name|match
parameter_list|(
name|HttpServletRequest
name|request
parameter_list|,
name|AcademicSessionInfo
name|info
parameter_list|,
name|boolean
name|useDefault
parameter_list|)
block|{
name|String
name|campus
init|=
name|request
operator|.
name|getParameter
argument_list|(
literal|"campus"
argument_list|)
decl_stmt|;
if|if
condition|(
name|campus
operator|!=
literal|null
operator|&&
operator|!
name|matchCampus
argument_list|(
name|info
argument_list|,
name|campus
argument_list|)
condition|)
return|return
literal|false
return|;
name|String
name|term
init|=
name|request
operator|.
name|getParameter
argument_list|(
literal|"term"
argument_list|)
decl_stmt|;
if|if
condition|(
name|term
operator|!=
literal|null
operator|&&
operator|!
name|matchTerm
argument_list|(
name|info
argument_list|,
name|term
argument_list|)
condition|)
return|return
literal|false
return|;
name|String
name|session
init|=
name|request
operator|.
name|getParameter
argument_list|(
literal|"session"
argument_list|)
decl_stmt|;
if|if
condition|(
name|session
operator|!=
literal|null
operator|&&
operator|!
name|matchSession
argument_list|(
name|info
argument_list|,
name|session
argument_list|)
condition|)
return|return
literal|false
return|;
if|if
condition|(
name|useDefault
operator|&&
name|campus
operator|==
literal|null
operator|&&
name|term
operator|==
literal|null
operator|&&
name|session
operator|==
literal|null
condition|)
return|return
name|info
operator|.
name|getSessionId
argument_list|()
operator|.
name|equals
argument_list|(
name|sessionContext
operator|.
name|getUser
argument_list|()
operator|.
name|getCurrentAcademicSessionId
argument_list|()
argument_list|)
return|;
else|else
return|return
literal|true
return|;
block|}
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
name|String
name|target
init|=
literal|null
decl_stmt|;
for|for
control|(
name|Map
operator|.
name|Entry
argument_list|<
name|String
argument_list|,
name|String
index|[]
argument_list|>
name|entry
range|:
name|request
operator|.
name|getParameterMap
argument_list|()
operator|.
name|entrySet
argument_list|()
control|)
block|{
for|for
control|(
name|String
name|value
range|:
name|entry
operator|.
name|getValue
argument_list|()
control|)
block|{
if|if
condition|(
literal|"prefer"
operator|.
name|equals
argument_list|(
name|entry
operator|.
name|getKey
argument_list|()
argument_list|)
condition|)
continue|continue;
if|if
condition|(
name|target
operator|==
literal|null
condition|)
name|target
operator|=
name|entry
operator|.
name|getKey
argument_list|()
operator|+
literal|"="
operator|+
name|URLEncoder
operator|.
name|encode
argument_list|(
name|value
argument_list|,
literal|"UTF-8"
argument_list|)
expr_stmt|;
else|else
name|target
operator|+=
literal|"&"
operator|+
name|entry
operator|.
name|getKey
argument_list|()
operator|+
literal|"="
operator|+
name|URLEncoder
operator|.
name|encode
argument_list|(
name|value
argument_list|,
literal|"UTF-8"
argument_list|)
expr_stmt|;
block|}
block|}
name|boolean
name|useDefault
init|=
name|ApplicationProperty
operator|.
name|StudentSchedulingUseDefaultSession
operator|.
name|isTrue
argument_list|()
decl_stmt|;
comment|// if instructor role is assigned, prefer student role
if|if
condition|(
name|sessionContext
operator|.
name|isAuthenticated
argument_list|()
operator|&&
name|Roles
operator|.
name|ROLE_INSTRUCTOR
operator|.
name|equals
argument_list|(
name|sessionContext
operator|.
name|getUser
argument_list|()
operator|.
name|getCurrentAuthority
argument_list|()
operator|.
name|getRole
argument_list|()
argument_list|)
condition|)
block|{
comment|// Student role of the same session
for|for
control|(
name|UserAuthority
name|auth
range|:
name|sessionContext
operator|.
name|getUser
argument_list|()
operator|.
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
name|sessionContext
operator|.
name|getUser
argument_list|()
operator|.
name|getCurrentAcademicSessionId
argument_list|()
argument_list|)
argument_list|)
control|)
block|{
name|sessionContext
operator|.
name|getUser
argument_list|()
operator|.
name|setCurrentAuthority
argument_list|(
name|auth
argument_list|)
expr_stmt|;
break|break;
block|}
comment|// Student role of different sessions
if|if
condition|(
name|Roles
operator|.
name|ROLE_INSTRUCTOR
operator|.
name|equals
argument_list|(
name|sessionContext
operator|.
name|getUser
argument_list|()
operator|.
name|getCurrentAuthority
argument_list|()
operator|.
name|getRole
argument_list|()
argument_list|)
condition|)
block|{
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
name|UserAuthority
name|firstStudentAuth
init|=
literal|null
decl_stmt|;
for|for
control|(
name|UserAuthority
name|auth
range|:
name|sessionContext
operator|.
name|getUser
argument_list|()
operator|.
name|getAuthorities
argument_list|(
name|Roles
operator|.
name|ROLE_STUDENT
argument_list|)
control|)
block|{
name|Session
name|session
init|=
name|SessionDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|get
argument_list|(
operator|(
name|Long
operator|)
name|auth
operator|.
name|getAcademicSession
argument_list|()
operator|.
name|getQualifierId
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|session
operator|!=
literal|null
condition|)
name|sessions
operator|.
name|add
argument_list|(
name|session
argument_list|)
expr_stmt|;
if|if
condition|(
name|firstStudentAuth
operator|==
literal|null
condition|)
name|firstStudentAuth
operator|=
name|auth
expr_stmt|;
block|}
if|if
condition|(
operator|!
name|sessions
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|Session
name|session
init|=
name|UniTimeUserContext
operator|.
name|defaultSession
argument_list|(
name|sessions
argument_list|,
name|firstStudentAuth
argument_list|,
name|UserProperty
operator|.
name|PrimaryCampus
operator|.
name|get
argument_list|(
name|sessionContext
operator|.
name|getUser
argument_list|()
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|session
operator|!=
literal|null
condition|)
for|for
control|(
name|UserAuthority
name|auth
range|:
name|sessionContext
operator|.
name|getUser
argument_list|()
operator|.
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
name|session
operator|.
name|getUniqueId
argument_list|()
argument_list|)
argument_list|)
control|)
block|{
name|sessionContext
operator|.
name|getUser
argument_list|()
operator|.
name|setCurrentAuthority
argument_list|(
name|auth
argument_list|)
expr_stmt|;
break|break;
block|}
block|}
block|}
block|}
comment|// Select current role -> prefer advisor, than student in the matching academic session
name|SectioningService
name|service
init|=
operator|(
name|SectioningService
operator|)
name|applicationContext
operator|.
name|getBean
argument_list|(
literal|"sectioning.gwt"
argument_list|)
decl_stmt|;
if|if
condition|(
name|sessionContext
operator|.
name|isAuthenticated
argument_list|()
condition|)
block|{
name|UserAuthority
name|preferredAuthority
init|=
literal|null
decl_stmt|;
try|try
block|{
for|for
control|(
name|AcademicSessionInfo
name|session
range|:
name|service
operator|.
name|listAcademicSessions
argument_list|(
literal|true
argument_list|)
control|)
block|{
if|if
condition|(
name|match
argument_list|(
name|request
argument_list|,
name|session
argument_list|,
name|useDefault
argument_list|)
condition|)
block|{
for|for
control|(
name|UserAuthority
name|auth
range|:
name|sessionContext
operator|.
name|getUser
argument_list|()
operator|.
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
name|getSessionId
argument_list|()
argument_list|)
argument_list|)
control|)
block|{
if|if
condition|(
name|preferredAuthority
operator|==
literal|null
operator|&&
name|Roles
operator|.
name|ROLE_STUDENT
operator|.
name|equals
argument_list|(
name|auth
operator|.
name|getRole
argument_list|()
argument_list|)
condition|)
block|{
name|preferredAuthority
operator|=
name|auth
expr_stmt|;
block|}
if|else if
condition|(
operator|(
name|preferredAuthority
operator|==
literal|null
operator|||
operator|!
name|preferredAuthority
operator|.
name|hasRight
argument_list|(
name|Right
operator|.
name|StudentSchedulingAdmin
argument_list|)
operator|)
operator|&&
name|auth
operator|.
name|hasRight
argument_list|(
name|Right
operator|.
name|StudentSchedulingAdvisor
argument_list|)
condition|)
block|{
name|preferredAuthority
operator|=
name|auth
expr_stmt|;
block|}
if|else if
condition|(
name|auth
operator|.
name|hasRight
argument_list|(
name|Right
operator|.
name|StudentSchedulingAdmin
argument_list|)
condition|)
block|{
name|preferredAuthority
operator|=
name|auth
expr_stmt|;
block|}
block|}
block|}
block|}
comment|// no authority selected --> also check the session for which the course requests are enabled
if|if
condition|(
name|preferredAuthority
operator|==
literal|null
condition|)
for|for
control|(
name|AcademicSessionInfo
name|session
range|:
name|service
operator|.
name|listAcademicSessions
argument_list|(
literal|false
argument_list|)
control|)
block|{
if|if
condition|(
name|match
argument_list|(
name|request
argument_list|,
name|session
argument_list|,
name|useDefault
argument_list|)
condition|)
block|{
for|for
control|(
name|UserAuthority
name|auth
range|:
name|sessionContext
operator|.
name|getUser
argument_list|()
operator|.
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
name|getSessionId
argument_list|()
argument_list|)
argument_list|)
control|)
block|{
if|if
condition|(
name|preferredAuthority
operator|==
literal|null
operator|&&
name|Roles
operator|.
name|ROLE_STUDENT
operator|.
name|equals
argument_list|(
name|auth
operator|.
name|getRole
argument_list|()
argument_list|)
condition|)
block|{
name|preferredAuthority
operator|=
name|auth
expr_stmt|;
block|}
if|else if
condition|(
operator|(
name|preferredAuthority
operator|==
literal|null
operator|||
operator|!
name|preferredAuthority
operator|.
name|hasRight
argument_list|(
name|Right
operator|.
name|StudentSchedulingAdmin
argument_list|)
operator|)
operator|&&
name|auth
operator|.
name|hasRight
argument_list|(
name|Right
operator|.
name|StudentSchedulingAdvisor
argument_list|)
condition|)
block|{
name|preferredAuthority
operator|=
name|auth
expr_stmt|;
block|}
if|else if
condition|(
name|auth
operator|.
name|hasRight
argument_list|(
name|Right
operator|.
name|StudentSchedulingAdmin
argument_list|)
condition|)
block|{
name|preferredAuthority
operator|=
name|auth
expr_stmt|;
block|}
block|}
block|}
block|}
block|}
catch|catch
parameter_list|(
name|SectioningException
name|e
parameter_list|)
block|{
block|}
if|if
condition|(
name|preferredAuthority
operator|==
literal|null
operator|&&
name|sessionContext
operator|.
name|getUser
argument_list|()
operator|.
name|getCurrentAuthority
argument_list|()
operator|!=
literal|null
condition|)
block|{
for|for
control|(
name|UserAuthority
name|auth
range|:
name|sessionContext
operator|.
name|getUser
argument_list|()
operator|.
name|getAuthorities
argument_list|(
literal|null
argument_list|,
name|sessionContext
operator|.
name|getUser
argument_list|()
operator|.
name|getCurrentAuthority
argument_list|()
operator|.
name|getAcademicSession
argument_list|()
argument_list|)
control|)
block|{
if|if
condition|(
name|preferredAuthority
operator|==
literal|null
operator|&&
name|Roles
operator|.
name|ROLE_STUDENT
operator|.
name|equals
argument_list|(
name|auth
operator|.
name|getRole
argument_list|()
argument_list|)
condition|)
block|{
name|preferredAuthority
operator|=
name|auth
expr_stmt|;
block|}
if|else if
condition|(
operator|(
name|preferredAuthority
operator|==
literal|null
operator|||
operator|!
name|preferredAuthority
operator|.
name|hasRight
argument_list|(
name|Right
operator|.
name|StudentSchedulingAdmin
argument_list|)
operator|)
operator|&&
name|auth
operator|.
name|hasRight
argument_list|(
name|Right
operator|.
name|StudentSchedulingAdvisor
argument_list|)
condition|)
block|{
name|preferredAuthority
operator|=
name|auth
expr_stmt|;
block|}
if|else if
condition|(
name|auth
operator|.
name|hasRight
argument_list|(
name|Right
operator|.
name|StudentSchedulingAdmin
argument_list|)
condition|)
block|{
name|preferredAuthority
operator|=
name|auth
expr_stmt|;
block|}
block|}
block|}
if|if
condition|(
name|preferredAuthority
operator|!=
literal|null
condition|)
name|sessionContext
operator|.
name|getUser
argument_list|()
operator|.
name|setCurrentAuthority
argument_list|(
name|preferredAuthority
argument_list|)
expr_stmt|;
block|}
comment|// Admins and advisors go to the scheduling dashboard
if|if
condition|(
name|sessionContext
operator|.
name|hasPermission
argument_list|(
name|Right
operator|.
name|SchedulingDashboard
argument_list|)
condition|)
block|{
if|if
condition|(
operator|!
name|sessionContext
operator|.
name|hasPermission
argument_list|(
name|Right
operator|.
name|StudentSchedulingAdmin
argument_list|)
condition|)
block|{
name|Number
name|myStudents
init|=
operator|(
name|Number
operator|)
name|CourseOfferingDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select count(s) from Advisor a inner join a.students s where "
operator|+
literal|"a.externalUniqueId = :user and a.role.reference = :role and a.session.uniqueId = :sessionId"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"sessionId"
argument_list|,
name|sessionContext
operator|.
name|getUser
argument_list|()
operator|.
name|getCurrentAcademicSessionId
argument_list|()
argument_list|)
operator|.
name|setString
argument_list|(
literal|"user"
argument_list|,
name|sessionContext
operator|.
name|getUser
argument_list|()
operator|.
name|getExternalUserId
argument_list|()
argument_list|)
operator|.
name|setString
argument_list|(
literal|"role"
argument_list|,
name|sessionContext
operator|.
name|getUser
argument_list|()
operator|.
name|getCurrentAuthority
argument_list|()
operator|.
name|getRole
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
decl_stmt|;
name|response
operator|.
name|sendRedirect
argument_list|(
literal|"gwt.jsp?page=onlinesctdash"
operator|+
operator|(
name|target
operator|==
literal|null
condition|?
literal|""
else|:
literal|"&"
operator|+
name|target
operator|)
operator|+
operator|(
name|myStudents
operator|.
name|intValue
argument_list|()
operator|>
literal|0
condition|?
literal|"#mode:%22My%20Students%22@"
else|:
literal|""
operator|)
argument_list|)
expr_stmt|;
block|}
else|else
name|response
operator|.
name|sendRedirect
argument_list|(
literal|"gwt.jsp?page=onlinesctdash"
operator|+
operator|(
name|target
operator|==
literal|null
condition|?
literal|""
else|:
literal|"&"
operator|+
name|target
operator|)
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
block|}
comment|// Only for students (check status)
if|if
condition|(
name|Roles
operator|.
name|ROLE_STUDENT
operator|.
name|equals
argument_list|(
name|sessionContext
operator|.
name|getUser
argument_list|()
operator|.
name|getCurrentAuthority
argument_list|()
operator|.
name|getRole
argument_list|()
argument_list|)
condition|)
block|{
name|List
argument_list|<
name|?
extends|extends
name|UserQualifier
argument_list|>
name|q
init|=
name|sessionContext
operator|.
name|getUser
argument_list|()
operator|.
name|getCurrentAuthority
argument_list|()
operator|.
name|getQualifiers
argument_list|(
literal|"Student"
argument_list|)
decl_stmt|;
if|if
condition|(
name|q
operator|!=
literal|null
operator|&&
operator|!
name|q
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|UserQualifier
name|studentQualifier
init|=
name|q
operator|.
name|get
argument_list|(
literal|0
argument_list|)
decl_stmt|;
name|boolean
name|preferCourseRequests
init|=
name|ApplicationProperty
operator|.
name|StudentSchedulingPreferCourseRequests
operator|.
name|isTrue
argument_list|()
decl_stmt|;
if|if
condition|(
name|request
operator|.
name|getParameter
argument_list|(
literal|"prefer"
argument_list|)
operator|!=
literal|null
condition|)
name|preferCourseRequests
operator|=
literal|"cr"
operator|.
name|equalsIgnoreCase
argument_list|(
name|request
operator|.
name|getParameter
argument_list|(
literal|"prefer"
argument_list|)
argument_list|)
operator|||
literal|"crf"
operator|.
name|equalsIgnoreCase
argument_list|(
name|request
operator|.
name|getParameter
argument_list|(
literal|"prefer"
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|preferCourseRequests
condition|)
block|{
comment|// 1. Course Requests with the registration enabled
try|try
block|{
for|for
control|(
name|AcademicSessionInfo
name|session
range|:
name|service
operator|.
name|listAcademicSessions
argument_list|(
literal|false
argument_list|)
control|)
block|{
if|if
condition|(
name|match
argument_list|(
name|request
argument_list|,
name|session
argument_list|,
name|useDefault
argument_list|)
condition|)
block|{
name|Student
name|student
init|=
name|Student
operator|.
name|findByExternalId
argument_list|(
name|session
operator|.
name|getSessionId
argument_list|()
argument_list|,
name|studentQualifier
operator|.
name|getQualifierReference
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|student
operator|==
literal|null
condition|)
name|student
operator|=
name|StudentDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|get
argument_list|(
operator|(
name|Long
operator|)
name|studentQualifier
operator|.
name|getQualifierId
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|student
operator|==
literal|null
condition|)
continue|continue;
name|StudentSectioningStatus
name|status
init|=
name|student
operator|.
name|getEffectiveStatus
argument_list|()
decl_stmt|;
if|if
condition|(
name|status
operator|==
literal|null
operator|||
operator|!
name|status
operator|.
name|hasOption
argument_list|(
name|StudentSectioningStatus
operator|.
name|Option
operator|.
name|regenabled
argument_list|)
condition|)
continue|continue;
name|response
operator|.
name|sendRedirect
argument_list|(
literal|"gwt.jsp?page=requests"
operator|+
operator|(
name|target
operator|==
literal|null
condition|?
literal|""
else|:
literal|"&"
operator|+
name|target
operator|)
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
block|}
block|}
block|}
catch|catch
parameter_list|(
name|SectioningException
name|e
parameter_list|)
block|{
block|}
comment|// 2. Scheduling Assistant with the enrollment enabled
try|try
block|{
for|for
control|(
name|AcademicSessionInfo
name|session
range|:
name|service
operator|.
name|listAcademicSessions
argument_list|(
literal|true
argument_list|)
control|)
block|{
if|if
condition|(
name|match
argument_list|(
name|request
argument_list|,
name|session
argument_list|,
name|useDefault
argument_list|)
condition|)
block|{
name|OnlineSectioningServer
name|server
init|=
name|solverServerService
operator|.
name|getOnlineStudentSchedulingContainer
argument_list|()
operator|.
name|getSolver
argument_list|(
name|session
operator|.
name|getSessionId
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|server
operator|==
literal|null
operator|||
operator|!
name|server
operator|.
name|getAcademicSession
argument_list|()
operator|.
name|isSectioningEnabled
argument_list|()
condition|)
continue|continue;
name|Student
name|student
init|=
name|Student
operator|.
name|findByExternalId
argument_list|(
name|session
operator|.
name|getSessionId
argument_list|()
argument_list|,
name|studentQualifier
operator|.
name|getQualifierReference
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|student
operator|==
literal|null
condition|)
name|student
operator|=
name|StudentDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|get
argument_list|(
operator|(
name|Long
operator|)
name|studentQualifier
operator|.
name|getQualifierId
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|student
operator|==
literal|null
condition|)
continue|continue;
name|StudentSectioningStatus
name|status
init|=
name|student
operator|.
name|getEffectiveStatus
argument_list|()
decl_stmt|;
if|if
condition|(
name|status
operator|!=
literal|null
operator|&&
operator|!
name|status
operator|.
name|hasOption
argument_list|(
name|StudentSectioningStatus
operator|.
name|Option
operator|.
name|enrollment
argument_list|)
condition|)
continue|continue;
name|response
operator|.
name|sendRedirect
argument_list|(
literal|"gwt.jsp?page=sectioning"
operator|+
operator|(
name|target
operator|==
literal|null
condition|?
literal|""
else|:
literal|"&"
operator|+
name|target
operator|)
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
block|}
block|}
block|}
catch|catch
parameter_list|(
name|SectioningException
name|e
parameter_list|)
block|{
block|}
block|}
else|else
block|{
comment|// 1. Scheduling Assistant with the enrollment enabled
try|try
block|{
for|for
control|(
name|AcademicSessionInfo
name|session
range|:
name|service
operator|.
name|listAcademicSessions
argument_list|(
literal|true
argument_list|)
control|)
block|{
if|if
condition|(
name|match
argument_list|(
name|request
argument_list|,
name|session
argument_list|,
name|useDefault
argument_list|)
condition|)
block|{
name|OnlineSectioningServer
name|server
init|=
name|solverServerService
operator|.
name|getOnlineStudentSchedulingContainer
argument_list|()
operator|.
name|getSolver
argument_list|(
name|session
operator|.
name|getSessionId
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|server
operator|==
literal|null
operator|||
operator|!
name|server
operator|.
name|getAcademicSession
argument_list|()
operator|.
name|isSectioningEnabled
argument_list|()
condition|)
continue|continue;
name|Student
name|student
init|=
name|Student
operator|.
name|findByExternalId
argument_list|(
name|session
operator|.
name|getSessionId
argument_list|()
argument_list|,
name|studentQualifier
operator|.
name|getQualifierReference
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|student
operator|==
literal|null
condition|)
name|student
operator|=
name|StudentDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|get
argument_list|(
operator|(
name|Long
operator|)
name|studentQualifier
operator|.
name|getQualifierId
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|student
operator|==
literal|null
condition|)
continue|continue;
name|StudentSectioningStatus
name|status
init|=
name|student
operator|.
name|getEffectiveStatus
argument_list|()
decl_stmt|;
if|if
condition|(
name|status
operator|!=
literal|null
operator|&&
operator|!
name|status
operator|.
name|hasOption
argument_list|(
name|StudentSectioningStatus
operator|.
name|Option
operator|.
name|enrollment
argument_list|)
condition|)
continue|continue;
name|response
operator|.
name|sendRedirect
argument_list|(
literal|"gwt.jsp?page=sectioning"
operator|+
operator|(
name|target
operator|==
literal|null
condition|?
literal|""
else|:
literal|"&"
operator|+
name|target
operator|)
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
block|}
block|}
block|}
catch|catch
parameter_list|(
name|SectioningException
name|e
parameter_list|)
block|{
block|}
comment|// 2. Course Requests with the registration enabled
try|try
block|{
for|for
control|(
name|AcademicSessionInfo
name|session
range|:
name|service
operator|.
name|listAcademicSessions
argument_list|(
literal|false
argument_list|)
control|)
block|{
if|if
condition|(
name|match
argument_list|(
name|request
argument_list|,
name|session
argument_list|,
name|useDefault
argument_list|)
condition|)
block|{
name|Student
name|student
init|=
name|Student
operator|.
name|findByExternalId
argument_list|(
name|session
operator|.
name|getSessionId
argument_list|()
argument_list|,
name|studentQualifier
operator|.
name|getQualifierReference
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|student
operator|==
literal|null
condition|)
name|student
operator|=
name|StudentDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|get
argument_list|(
operator|(
name|Long
operator|)
name|studentQualifier
operator|.
name|getQualifierId
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|student
operator|==
literal|null
condition|)
continue|continue;
name|StudentSectioningStatus
name|status
init|=
name|student
operator|.
name|getEffectiveStatus
argument_list|()
decl_stmt|;
if|if
condition|(
name|status
operator|==
literal|null
operator|||
operator|!
name|status
operator|.
name|hasOption
argument_list|(
name|StudentSectioningStatus
operator|.
name|Option
operator|.
name|regenabled
argument_list|)
condition|)
continue|continue;
name|response
operator|.
name|sendRedirect
argument_list|(
literal|"gwt.jsp?page=requests"
operator|+
operator|(
name|target
operator|==
literal|null
condition|?
literal|""
else|:
literal|"&"
operator|+
name|target
operator|)
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
block|}
block|}
block|}
catch|catch
parameter_list|(
name|SectioningException
name|e
parameter_list|)
block|{
block|}
block|}
block|}
block|}
comment|// 3. Scheduling Assistant
try|try
block|{
for|for
control|(
name|AcademicSessionInfo
name|session
range|:
name|service
operator|.
name|listAcademicSessions
argument_list|(
literal|true
argument_list|)
control|)
block|{
if|if
condition|(
name|match
argument_list|(
name|request
argument_list|,
name|session
argument_list|,
name|useDefault
argument_list|)
condition|)
block|{
name|response
operator|.
name|sendRedirect
argument_list|(
literal|"gwt.jsp?page=sectioning"
operator|+
operator|(
name|target
operator|==
literal|null
condition|?
literal|""
else|:
literal|"&"
operator|+
name|target
operator|)
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
block|}
block|}
block|}
catch|catch
parameter_list|(
name|SectioningException
name|e
parameter_list|)
block|{
block|}
comment|// 4. Course Requests
try|try
block|{
for|for
control|(
name|AcademicSessionInfo
name|session
range|:
name|service
operator|.
name|listAcademicSessions
argument_list|(
literal|false
argument_list|)
control|)
block|{
if|if
condition|(
name|match
argument_list|(
name|request
argument_list|,
name|session
argument_list|,
name|useDefault
argument_list|)
condition|)
block|{
name|response
operator|.
name|sendRedirect
argument_list|(
literal|"gwt.jsp?page=requests"
operator|+
operator|(
name|target
operator|==
literal|null
condition|?
literal|""
else|:
literal|"&"
operator|+
name|target
operator|)
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
block|}
block|}
block|}
catch|catch
parameter_list|(
name|SectioningException
name|e
parameter_list|)
block|{
block|}
comment|// 5. Main page fallback
name|response
operator|.
name|sendRedirect
argument_list|(
literal|"main.jsp"
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
block|}
block|}
end_class

end_unit

