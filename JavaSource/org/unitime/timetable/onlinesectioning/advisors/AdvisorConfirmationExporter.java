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
name|onlinesectioning
operator|.
name|advisors
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|IOException
import|;
end_import

begin_import
import|import
name|org
operator|.
name|hibernate
operator|.
name|CacheMode
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
name|export
operator|.
name|ExportHelper
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
name|export
operator|.
name|Exporter
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
name|StudentSectioningMessages
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
name|OnlineSectioningInterface
operator|.
name|AdvisingStudentDetails
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
name|OnlineSectioningInterface
operator|.
name|StudentStatusInfo
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
name|OnlineSectioningInterface
operator|.
name|WaitListMode
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
name|Advisor
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
name|AdvisorCourseRequest
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
name|dao
operator|.
name|AdvisorCourseRequestDAO
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

begin_import
import|import
name|com
operator|.
name|lowagie
operator|.
name|text
operator|.
name|DocumentException
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
annotation|@
name|Service
argument_list|(
literal|"org.unitime.timetable.export.Exporter:acrf.pdf"
argument_list|)
specifier|public
class|class
name|AdvisorConfirmationExporter
implements|implements
name|Exporter
block|{
specifier|protected
specifier|static
name|StudentSectioningMessages
name|MSG
init|=
name|Localization
operator|.
name|create
argument_list|(
name|StudentSectioningMessages
operator|.
name|class
argument_list|)
decl_stmt|;
annotation|@
name|Override
specifier|public
name|String
name|reference
parameter_list|()
block|{
return|return
literal|"acrf.pdf"
return|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|export
parameter_list|(
name|ExportHelper
name|helper
parameter_list|)
throws|throws
name|IOException
block|{
name|boolean
name|isAdvisor
init|=
literal|false
decl_stmt|;
name|String
name|externalId
init|=
name|helper
operator|.
name|getParameter
argument_list|(
literal|"id"
argument_list|)
decl_stmt|;
if|if
condition|(
name|externalId
operator|==
literal|null
condition|)
block|{
if|if
condition|(
operator|!
name|helper
operator|.
name|getSessionContext
argument_list|()
operator|.
name|isAuthenticated
argument_list|()
condition|)
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
name|MSG
operator|.
name|exceptionUserNotLoggedIn
argument_list|()
argument_list|)
throw|;
name|externalId
operator|=
name|helper
operator|.
name|getSessionContext
argument_list|()
operator|.
name|getUser
argument_list|()
operator|.
name|getExternalUserId
argument_list|()
expr_stmt|;
block|}
else|else
block|{
name|helper
operator|.
name|getSessionContext
argument_list|()
operator|.
name|checkPermissionAnyAuthority
argument_list|(
name|helper
operator|.
name|getAcademicSessionId
argument_list|()
argument_list|,
name|Right
operator|.
name|AdvisorCourseRequests
argument_list|)
expr_stmt|;
name|isAdvisor
operator|=
literal|true
expr_stmt|;
block|}
if|if
condition|(
name|externalId
operator|==
literal|null
condition|)
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
name|MSG
operator|.
name|exceptionBadStudentId
argument_list|()
argument_list|)
throw|;
if|if
condition|(
name|externalId
operator|.
name|isEmpty
argument_list|()
condition|)
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
name|MSG
operator|.
name|exceptionUserNotLoggedIn
argument_list|()
argument_list|)
throw|;
name|Long
name|sessionId
init|=
name|helper
operator|.
name|getAcademicSessionId
argument_list|()
decl_stmt|;
if|if
condition|(
name|sessionId
operator|==
literal|null
condition|)
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"Academic session not provided, please set the term parameter."
argument_list|)
throw|;
name|Student
name|student
init|=
name|Student
operator|.
name|findByExternalId
argument_list|(
name|helper
operator|.
name|getAcademicSessionId
argument_list|()
argument_list|,
name|externalId
argument_list|)
decl_stmt|;
if|if
condition|(
name|student
operator|==
literal|null
condition|)
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
name|MSG
operator|.
name|exceptionNoStudent
argument_list|()
argument_list|)
throw|;
name|SessionDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|setCacheMode
argument_list|(
name|CacheMode
operator|.
name|REFRESH
argument_list|)
expr_stmt|;
name|AdvisingStudentDetails
name|details
init|=
operator|new
name|AdvisingStudentDetails
argument_list|()
decl_stmt|;
name|details
operator|.
name|setSessionId
argument_list|(
name|sessionId
argument_list|)
expr_stmt|;
name|details
operator|.
name|setStudentId
argument_list|(
name|student
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
name|details
operator|.
name|setStudentName
argument_list|(
name|student
operator|.
name|getName
argument_list|(
name|NameFormat
operator|.
name|LAST_FIRST_MIDDLE
operator|.
name|reference
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|details
operator|.
name|setStudentExternalId
argument_list|(
name|student
operator|.
name|getExternalUniqueId
argument_list|()
argument_list|)
expr_stmt|;
name|details
operator|.
name|setSessionName
argument_list|(
name|student
operator|.
name|getSession
argument_list|()
operator|.
name|getLabel
argument_list|()
argument_list|)
expr_stmt|;
name|details
operator|.
name|setStudentEmail
argument_list|(
name|student
operator|.
name|getEmail
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|isAdvisor
condition|)
block|{
name|Advisor
name|advisor
init|=
name|Advisor
operator|.
name|findByExternalId
argument_list|(
name|helper
operator|.
name|getSessionContext
argument_list|()
operator|.
name|getUser
argument_list|()
operator|.
name|getExternalUserId
argument_list|()
argument_list|,
name|sessionId
argument_list|)
decl_stmt|;
if|if
condition|(
name|advisor
operator|!=
literal|null
condition|)
name|details
operator|.
name|setAdvisorEmail
argument_list|(
name|advisor
operator|.
name|getEmail
argument_list|()
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
operator|!
name|details
operator|.
name|hasAdvisorEmail
argument_list|()
condition|)
block|{
name|AdvisorCourseRequest
name|lastAcr
init|=
literal|null
decl_stmt|;
for|for
control|(
name|AdvisorCourseRequest
name|acr
range|:
name|student
operator|.
name|getAdvisorCourseRequests
argument_list|()
control|)
block|{
if|if
condition|(
name|lastAcr
operator|==
literal|null
operator|||
name|lastAcr
operator|.
name|getTimestamp
argument_list|()
operator|.
name|before
argument_list|(
name|acr
operator|.
name|getTimestamp
argument_list|()
argument_list|)
condition|)
name|lastAcr
operator|=
name|acr
expr_stmt|;
block|}
if|if
condition|(
name|lastAcr
operator|!=
literal|null
condition|)
block|{
name|Advisor
name|advisor
init|=
name|Advisor
operator|.
name|findByExternalId
argument_list|(
name|lastAcr
operator|.
name|getChangedBy
argument_list|()
argument_list|,
name|sessionId
argument_list|)
decl_stmt|;
if|if
condition|(
name|advisor
operator|!=
literal|null
condition|)
name|details
operator|.
name|setAdvisorEmail
argument_list|(
name|advisor
operator|.
name|getEmail
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
if|if
condition|(
operator|!
name|details
operator|.
name|hasAdvisorEmail
argument_list|()
condition|)
block|{
name|String
name|email
init|=
literal|null
decl_stmt|;
for|for
control|(
name|Advisor
name|a
range|:
name|student
operator|.
name|getAdvisors
argument_list|()
control|)
block|{
if|if
condition|(
name|a
operator|.
name|getEmail
argument_list|()
operator|!=
literal|null
operator|&&
operator|!
name|a
operator|.
name|getEmail
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|email
operator|=
operator|(
name|email
operator|==
literal|null
condition|?
literal|""
else|:
name|email
operator|+
literal|"\n"
operator|)
operator|+
name|a
operator|.
name|getEmail
argument_list|()
expr_stmt|;
block|}
block|}
name|details
operator|.
name|setAdvisorEmail
argument_list|(
name|email
argument_list|)
expr_stmt|;
empty_stmt|;
block|}
if|if
condition|(
operator|!
name|details
operator|.
name|hasAdvisorEmail
argument_list|()
operator|&&
name|isAdvisor
condition|)
block|{
name|TimetableManager
name|manager
init|=
name|TimetableManager
operator|.
name|findByExternalId
argument_list|(
name|helper
operator|.
name|getSessionContext
argument_list|()
operator|.
name|getUser
argument_list|()
operator|.
name|getExternalUserId
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|manager
operator|!=
literal|null
condition|)
name|details
operator|.
name|setAdvisorEmail
argument_list|(
name|manager
operator|.
name|getEmailAddress
argument_list|()
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|student
operator|.
name|getSectioningStatus
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|StudentStatusInfo
name|status
init|=
operator|new
name|StudentStatusInfo
argument_list|()
decl_stmt|;
name|status
operator|.
name|setUniqueId
argument_list|(
name|student
operator|.
name|getSectioningStatus
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
name|status
operator|.
name|setReference
argument_list|(
name|student
operator|.
name|getSectioningStatus
argument_list|()
operator|.
name|getReference
argument_list|()
argument_list|)
expr_stmt|;
name|status
operator|.
name|setLabel
argument_list|(
name|student
operator|.
name|getSectioningStatus
argument_list|()
operator|.
name|getLabel
argument_list|()
argument_list|)
expr_stmt|;
name|details
operator|.
name|setStatus
argument_list|(
name|status
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|student
operator|.
name|getSession
argument_list|()
operator|.
name|getDefaultSectioningStatus
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|StudentStatusInfo
name|info
init|=
operator|new
name|StudentStatusInfo
argument_list|()
decl_stmt|;
name|info
operator|.
name|setUniqueId
argument_list|(
literal|null
argument_list|)
expr_stmt|;
name|info
operator|.
name|setReference
argument_list|(
literal|""
argument_list|)
expr_stmt|;
name|info
operator|.
name|setLabel
argument_list|(
name|MSG
operator|.
name|studentStatusSessionDefault
argument_list|(
name|student
operator|.
name|getSession
argument_list|()
operator|.
name|getDefaultSectioningStatus
argument_list|()
operator|.
name|getLabel
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|info
operator|.
name|setEffectiveStart
argument_list|(
literal|null
argument_list|)
expr_stmt|;
name|info
operator|.
name|setEffectiveStop
argument_list|(
literal|null
argument_list|)
expr_stmt|;
name|details
operator|.
name|setStatus
argument_list|(
name|info
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|StudentStatusInfo
name|info
init|=
operator|new
name|StudentStatusInfo
argument_list|()
decl_stmt|;
name|info
operator|.
name|setReference
argument_list|(
literal|""
argument_list|)
expr_stmt|;
name|info
operator|.
name|setLabel
argument_list|(
name|MSG
operator|.
name|studentStatusSystemDefault
argument_list|()
argument_list|)
expr_stmt|;
name|info
operator|.
name|setAllEnabled
argument_list|()
expr_stmt|;
name|details
operator|.
name|setStatus
argument_list|(
name|info
argument_list|)
expr_stmt|;
block|}
name|details
operator|.
name|setRequest
argument_list|(
name|AdvisorGetCourseRequests
operator|.
name|getRequest
argument_list|(
name|student
argument_list|,
name|AdvisorCourseRequestDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|getSession
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|String
name|advWlMode
init|=
name|ApplicationProperty
operator|.
name|AdvisorRecommendationsWaitListMode
operator|.
name|value
argument_list|(
name|student
operator|.
name|getSession
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
literal|"Student"
operator|.
name|equalsIgnoreCase
argument_list|(
name|advWlMode
argument_list|)
condition|)
name|details
operator|.
name|setWaitListMode
argument_list|(
name|student
operator|.
name|getWaitListMode
argument_list|()
argument_list|)
expr_stmt|;
else|else
name|details
operator|.
name|setWaitListMode
argument_list|(
name|WaitListMode
operator|.
name|valueOf
argument_list|(
name|advWlMode
argument_list|)
argument_list|)
expr_stmt|;
name|helper
operator|.
name|setup
argument_list|(
literal|"application/pdf"
argument_list|,
literal|"crf-"
operator|+
name|student
operator|.
name|getSession
argument_list|()
operator|.
name|getAcademicTerm
argument_list|()
operator|+
name|student
operator|.
name|getSession
argument_list|()
operator|.
name|getAcademicYear
argument_list|()
operator|+
operator|(
name|isAdvisor
condition|?
literal|"-"
operator|+
name|student
operator|.
name|getName
argument_list|(
name|NameFormat
operator|.
name|LAST_FIRST_MIDDLE
operator|.
name|reference
argument_list|()
argument_list|)
operator|.
name|replaceAll
argument_list|(
literal|"[&$\\+,/:;=\\?@<>\\[\\]\\{\\}\\|\\^\\~%#`\\t\\s\\n\\r \\\\]"
argument_list|,
literal|""
argument_list|)
operator|+
literal|"-"
operator|+
name|student
operator|.
name|getExternalUniqueId
argument_list|()
else|:
literal|""
operator|)
operator|+
literal|".pdf"
argument_list|,
literal|false
argument_list|)
expr_stmt|;
try|try
block|{
operator|new
name|AdvisorConfirmationPDF
argument_list|(
name|details
argument_list|)
operator|.
name|generatePdfConfirmation
argument_list|(
name|helper
operator|.
name|getOutputStream
argument_list|()
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|DocumentException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|IOException
argument_list|(
name|e
operator|.
name|getMessage
argument_list|()
argument_list|,
name|e
argument_list|)
throw|;
block|}
block|}
block|}
end_class

end_unit

