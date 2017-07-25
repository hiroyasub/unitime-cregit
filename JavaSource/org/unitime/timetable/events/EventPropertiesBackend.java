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
name|events
package|;
end_package

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
name|command
operator|.
name|server
operator|.
name|GwtRpcImplements
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
name|command
operator|.
name|server
operator|.
name|GwtRpcServlet
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
name|PersonInterface
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
name|EventInterface
operator|.
name|ContactInterface
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
name|EventInterface
operator|.
name|EventPropertiesRpcRequest
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
name|EventInterface
operator|.
name|EventPropertiesRpcResponse
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
name|EventInterface
operator|.
name|EventServiceProviderInterface
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
name|EventInterface
operator|.
name|SponsoringOrganizationInterface
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
name|EventInterface
operator|.
name|StandardEventNoteInterface
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
name|EventContact
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
name|EventServiceProvider
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
name|Staff
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
name|StandardEventNote
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
name|EventContactDAO
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
name|EventServiceProviderDAO
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
name|StandardEventNoteDepartmentDAO
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
name|StandardEventNoteGlobalDAO
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
name|StandardEventNoteSessionDAO
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
name|Qualifiable
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

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
annotation|@
name|GwtRpcImplements
argument_list|(
name|EventPropertiesRpcRequest
operator|.
name|class
argument_list|)
specifier|public
class|class
name|EventPropertiesBackend
extends|extends
name|EventAction
argument_list|<
name|EventPropertiesRpcRequest
argument_list|,
name|EventPropertiesRpcResponse
argument_list|>
block|{
specifier|private
annotation|@
name|Autowired
name|ApplicationContext
name|applicationContext
decl_stmt|;
annotation|@
name|Override
specifier|public
name|EventPropertiesRpcResponse
name|execute
parameter_list|(
name|EventPropertiesRpcRequest
name|request
parameter_list|,
name|EventContext
name|context
parameter_list|)
block|{
name|EventPropertiesRpcResponse
name|response
init|=
operator|new
name|EventPropertiesRpcResponse
argument_list|()
decl_stmt|;
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
name|request
operator|.
name|getSessionId
argument_list|()
argument_list|)
decl_stmt|;
name|response
operator|.
name|setCanLookupPeople
argument_list|(
name|context
operator|.
name|hasPermission
argument_list|(
name|Right
operator|.
name|EventLookupSchedule
argument_list|)
argument_list|)
expr_stmt|;
name|response
operator|.
name|setCanLookupMainContact
argument_list|(
name|context
operator|.
name|hasPermission
argument_list|(
name|Right
operator|.
name|EventLookupContact
argument_list|)
argument_list|)
expr_stmt|;
name|response
operator|.
name|setCanLookupAdditionalContacts
argument_list|(
name|response
operator|.
name|isCanLookupMainContact
argument_list|()
operator|||
name|context
operator|.
name|hasPermission
argument_list|(
name|Right
operator|.
name|EventLookupContactAdditional
argument_list|)
argument_list|)
expr_stmt|;
name|response
operator|.
name|setCanAddSpecialEvent
argument_list|(
name|context
operator|.
name|hasPermission
argument_list|(
name|Right
operator|.
name|EventAddSpecial
argument_list|)
argument_list|)
expr_stmt|;
name|response
operator|.
name|setCanAddCourseEvent
argument_list|(
name|context
operator|.
name|hasPermission
argument_list|(
name|Right
operator|.
name|EventAddCourseRelated
argument_list|)
argument_list|)
expr_stmt|;
name|response
operator|.
name|setCanAddUnavailableEvent
argument_list|(
name|context
operator|.
name|hasPermission
argument_list|(
name|Right
operator|.
name|EventAddUnavailable
argument_list|)
argument_list|)
expr_stmt|;
name|response
operator|.
name|setCanSetExpirationDate
argument_list|(
name|context
operator|.
name|hasPermission
argument_list|(
name|Right
operator|.
name|EventSetExpiration
argument_list|)
argument_list|)
expr_stmt|;
name|response
operator|.
name|setCanEditAcademicTitle
argument_list|(
name|context
operator|.
name|hasPermission
argument_list|(
name|Right
operator|.
name|EventCanEditAcademicTitle
argument_list|)
argument_list|)
expr_stmt|;
name|response
operator|.
name|setCanViewMeetingContacts
argument_list|(
name|context
operator|.
name|hasPermission
argument_list|(
name|Right
operator|.
name|EventCanViewMeetingContacts
argument_list|)
argument_list|)
expr_stmt|;
name|response
operator|.
name|setCanEditMeetingContacts
argument_list|(
name|context
operator|.
name|hasPermission
argument_list|(
name|Right
operator|.
name|EventCanEditMeetingContacts
argument_list|)
argument_list|)
expr_stmt|;
name|response
operator|.
name|setCanExportCSV
argument_list|(
literal|true
argument_list|)
expr_stmt|;
comment|// rights.canSeeSchedule(null) || rights.canLookupContacts());
if|if
condition|(
name|response
operator|.
name|isCanLookupMainContact
argument_list|()
operator|&&
name|ApplicationProperty
operator|.
name|EmailConfirmationEvents
operator|.
name|isTrue
argument_list|()
condition|)
block|{
comment|// email confirmations are enabled and user has enough permissions
comment|// use unitime.email.confirm.default to determine the default value of the "Send email confirmation" toggle
name|response
operator|.
name|setEmailConfirmation
argument_list|(
literal|"true"
operator|.
name|equalsIgnoreCase
argument_list|(
name|context
operator|.
name|getUser
argument_list|()
operator|.
name|getProperty
argument_list|(
literal|"unitime.email.confirm.default"
argument_list|,
literal|"true"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|setupSponsoringOrganizations
argument_list|(
name|session
argument_list|,
name|response
argument_list|)
expr_stmt|;
name|setupEventServiceProviders
argument_list|(
name|session
argument_list|,
name|response
argument_list|)
expr_stmt|;
if|if
condition|(
name|context
operator|.
name|getUser
argument_list|()
operator|!=
literal|null
condition|)
name|response
operator|.
name|setMainContact
argument_list|(
name|lookupMainContact
argument_list|(
name|request
operator|.
name|getSessionId
argument_list|()
argument_list|,
name|context
argument_list|)
argument_list|)
expr_stmt|;
name|setupStandardNotes
argument_list|(
name|request
operator|.
name|getSessionId
argument_list|()
argument_list|,
name|context
operator|.
name|getUser
argument_list|()
argument_list|,
name|response
argument_list|)
expr_stmt|;
name|response
operator|.
name|setCanSaveFilterDefaults
argument_list|(
name|context
operator|.
name|hasPermission
argument_list|(
name|Right
operator|.
name|HasRole
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|context
operator|.
name|isAuthenticated
argument_list|()
operator|&&
name|response
operator|.
name|isCanSaveFilterDefaults
argument_list|()
operator|&&
name|request
operator|.
name|getPageName
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|response
operator|.
name|setFilterDefault
argument_list|(
literal|"rooms"
argument_list|,
name|context
operator|.
name|getUser
argument_list|()
operator|.
name|getProperty
argument_list|(
literal|"Default["
operator|+
name|request
operator|.
name|getPageName
argument_list|()
operator|+
literal|".rooms]"
argument_list|)
argument_list|)
expr_stmt|;
name|response
operator|.
name|setFilterDefault
argument_list|(
literal|"events"
argument_list|,
name|context
operator|.
name|getUser
argument_list|()
operator|.
name|getProperty
argument_list|(
literal|"Default["
operator|+
name|request
operator|.
name|getPageName
argument_list|()
operator|+
literal|".events]"
argument_list|)
argument_list|)
expr_stmt|;
name|response
operator|.
name|setFilterDefault
argument_list|(
literal|"emails"
argument_list|,
name|context
operator|.
name|getUser
argument_list|()
operator|.
name|getProperty
argument_list|(
literal|"Defaults[AddEvent.emails]"
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|int
name|tooEarly
init|=
name|ApplicationProperty
operator|.
name|EventTooEarlySlot
operator|.
name|intValue
argument_list|()
decl_stmt|;
if|if
condition|(
name|tooEarly
operator|>
literal|0
condition|)
name|response
operator|.
name|setTooEarlySlot
argument_list|(
name|tooEarly
argument_list|)
expr_stmt|;
name|response
operator|.
name|setGridDisplayTitle
argument_list|(
name|ApplicationProperty
operator|.
name|EventGridDisplayTitle
operator|.
name|isTrue
argument_list|()
argument_list|)
expr_stmt|;
name|response
operator|.
name|setStudent
argument_list|(
name|context
operator|.
name|getUser
argument_list|()
operator|!=
literal|null
operator|&&
name|context
operator|.
name|getUser
argument_list|()
operator|.
name|hasRole
argument_list|(
name|Roles
operator|.
name|ROLE_STUDENT
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|response
return|;
block|}
specifier|public
name|void
name|setupSponsoringOrganizations
parameter_list|(
name|Session
name|session
parameter_list|,
name|EventPropertiesRpcResponse
name|response
parameter_list|)
block|{
for|for
control|(
name|SponsoringOrganization
name|s
range|:
name|SponsoringOrganization
operator|.
name|findAll
argument_list|()
control|)
block|{
name|SponsoringOrganizationInterface
name|sponsor
init|=
operator|new
name|SponsoringOrganizationInterface
argument_list|()
decl_stmt|;
name|sponsor
operator|.
name|setUniqueId
argument_list|(
name|s
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
name|sponsor
operator|.
name|setName
argument_list|(
name|s
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
name|sponsor
operator|.
name|setEmail
argument_list|(
name|s
operator|.
name|getEmail
argument_list|()
argument_list|)
expr_stmt|;
name|response
operator|.
name|addSponsoringOrganization
argument_list|(
name|sponsor
argument_list|)
expr_stmt|;
block|}
block|}
specifier|public
name|void
name|setupEventServiceProviders
parameter_list|(
name|Session
name|session
parameter_list|,
name|EventPropertiesRpcResponse
name|response
parameter_list|)
block|{
for|for
control|(
name|EventServiceProvider
name|p
range|:
name|EventServiceProviderDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|findAll
argument_list|()
control|)
block|{
name|EventServiceProviderInterface
name|provider
init|=
operator|new
name|EventServiceProviderInterface
argument_list|()
decl_stmt|;
name|provider
operator|.
name|setId
argument_list|(
name|p
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
name|provider
operator|.
name|setReference
argument_list|(
name|p
operator|.
name|getReference
argument_list|()
argument_list|)
expr_stmt|;
name|provider
operator|.
name|setLabel
argument_list|(
name|p
operator|.
name|getLabel
argument_list|()
argument_list|)
expr_stmt|;
name|provider
operator|.
name|setMessage
argument_list|(
name|p
operator|.
name|getNote
argument_list|()
argument_list|)
expr_stmt|;
name|provider
operator|.
name|setEmail
argument_list|(
name|p
operator|.
name|getEmail
argument_list|()
argument_list|)
expr_stmt|;
name|provider
operator|.
name|setOptions
argument_list|(
name|p
operator|.
name|getOptions
argument_list|()
argument_list|)
expr_stmt|;
name|response
operator|.
name|addEventServiceProvider
argument_list|(
name|provider
argument_list|)
expr_stmt|;
block|}
block|}
specifier|public
name|ContactInterface
name|lookupMainContact
parameter_list|(
name|Long
name|sessionId
parameter_list|,
name|SessionContext
name|context
parameter_list|)
block|{
name|UserContext
name|user
init|=
name|context
operator|.
name|getUser
argument_list|()
decl_stmt|;
name|String
name|nameFormat
init|=
name|user
operator|.
name|getProperty
argument_list|(
name|UserProperty
operator|.
name|NameFormat
argument_list|)
decl_stmt|;
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
init|=
name|EventContactDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|getSession
argument_list|()
decl_stmt|;
name|EventContact
name|contact
init|=
operator|(
name|EventContact
operator|)
name|hibSession
operator|.
name|createQuery
argument_list|(
literal|"from EventContact where externalUniqueId = :userId"
argument_list|)
operator|.
name|setString
argument_list|(
literal|"userId"
argument_list|,
name|user
operator|.
name|getExternalUserId
argument_list|()
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
name|contact
operator|!=
literal|null
condition|)
block|{
name|ContactInterface
name|c
init|=
operator|new
name|ContactInterface
argument_list|()
decl_stmt|;
name|c
operator|.
name|setFirstName
argument_list|(
name|contact
operator|.
name|getFirstName
argument_list|()
argument_list|)
expr_stmt|;
name|c
operator|.
name|setMiddleName
argument_list|(
name|contact
operator|.
name|getMiddleName
argument_list|()
argument_list|)
expr_stmt|;
name|c
operator|.
name|setLastName
argument_list|(
name|contact
operator|.
name|getLastName
argument_list|()
argument_list|)
expr_stmt|;
name|c
operator|.
name|setAcademicTitle
argument_list|(
name|contact
operator|.
name|getAcademicTitle
argument_list|()
argument_list|)
expr_stmt|;
name|c
operator|.
name|setEmail
argument_list|(
name|contact
operator|.
name|getEmailAddress
argument_list|()
argument_list|)
expr_stmt|;
name|c
operator|.
name|setPhone
argument_list|(
name|contact
operator|.
name|getPhone
argument_list|()
argument_list|)
expr_stmt|;
name|c
operator|.
name|setExternalId
argument_list|(
name|contact
operator|.
name|getExternalUniqueId
argument_list|()
argument_list|)
expr_stmt|;
name|c
operator|.
name|setFormattedName
argument_list|(
name|contact
operator|.
name|getName
argument_list|(
name|nameFormat
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|c
return|;
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
literal|"from TimetableManager where externalUniqueId = :userId"
argument_list|)
operator|.
name|setString
argument_list|(
literal|"userId"
argument_list|,
name|user
operator|.
name|getExternalUserId
argument_list|()
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
name|ContactInterface
name|c
init|=
operator|new
name|ContactInterface
argument_list|()
decl_stmt|;
name|c
operator|.
name|setExternalId
argument_list|(
name|manager
operator|.
name|getExternalUniqueId
argument_list|()
argument_list|)
expr_stmt|;
name|c
operator|.
name|setFirstName
argument_list|(
name|manager
operator|.
name|getFirstName
argument_list|()
argument_list|)
expr_stmt|;
name|c
operator|.
name|setMiddleName
argument_list|(
name|manager
operator|.
name|getMiddleName
argument_list|()
argument_list|)
expr_stmt|;
name|c
operator|.
name|setLastName
argument_list|(
name|manager
operator|.
name|getLastName
argument_list|()
argument_list|)
expr_stmt|;
name|c
operator|.
name|setAcademicTitle
argument_list|(
name|manager
operator|.
name|getAcademicTitle
argument_list|()
argument_list|)
expr_stmt|;
name|c
operator|.
name|setEmail
argument_list|(
name|manager
operator|.
name|getEmailAddress
argument_list|()
argument_list|)
expr_stmt|;
name|c
operator|.
name|setFormattedName
argument_list|(
name|manager
operator|.
name|getName
argument_list|(
name|nameFormat
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|c
return|;
block|}
name|DepartmentalInstructor
name|instructor
init|=
operator|(
name|DepartmentalInstructor
operator|)
name|hibSession
operator|.
name|createQuery
argument_list|(
literal|"from DepartmentalInstructor where department.session.uniqueId = :sessionId and externalUniqueId = :userId"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"sessionId"
argument_list|,
name|sessionId
argument_list|)
operator|.
name|setString
argument_list|(
literal|"userId"
argument_list|,
name|user
operator|.
name|getExternalUserId
argument_list|()
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
name|instructor
operator|!=
literal|null
condition|)
block|{
name|ContactInterface
name|c
init|=
operator|new
name|ContactInterface
argument_list|()
decl_stmt|;
name|c
operator|.
name|setExternalId
argument_list|(
name|instructor
operator|.
name|getExternalUniqueId
argument_list|()
argument_list|)
expr_stmt|;
name|c
operator|.
name|setFirstName
argument_list|(
name|instructor
operator|.
name|getFirstName
argument_list|()
argument_list|)
expr_stmt|;
name|c
operator|.
name|setMiddleName
argument_list|(
name|instructor
operator|.
name|getMiddleName
argument_list|()
argument_list|)
expr_stmt|;
name|c
operator|.
name|setLastName
argument_list|(
name|instructor
operator|.
name|getLastName
argument_list|()
argument_list|)
expr_stmt|;
name|c
operator|.
name|setAcademicTitle
argument_list|(
name|instructor
operator|.
name|getAcademicTitle
argument_list|()
argument_list|)
expr_stmt|;
name|c
operator|.
name|setEmail
argument_list|(
name|instructor
operator|.
name|getEmail
argument_list|()
argument_list|)
expr_stmt|;
name|c
operator|.
name|setFormattedName
argument_list|(
name|instructor
operator|.
name|getName
argument_list|(
name|nameFormat
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|c
return|;
block|}
name|Staff
name|staff
init|=
operator|(
name|Staff
operator|)
name|hibSession
operator|.
name|createQuery
argument_list|(
literal|"from Staff where externalUniqueId = :userId"
argument_list|)
operator|.
name|setString
argument_list|(
literal|"userId"
argument_list|,
name|user
operator|.
name|getExternalUserId
argument_list|()
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
name|staff
operator|!=
literal|null
condition|)
block|{
name|ContactInterface
name|c
init|=
operator|new
name|ContactInterface
argument_list|()
decl_stmt|;
name|c
operator|.
name|setExternalId
argument_list|(
name|staff
operator|.
name|getExternalUniqueId
argument_list|()
argument_list|)
expr_stmt|;
name|c
operator|.
name|setFirstName
argument_list|(
name|staff
operator|.
name|getFirstName
argument_list|()
argument_list|)
expr_stmt|;
name|c
operator|.
name|setMiddleName
argument_list|(
name|staff
operator|.
name|getMiddleName
argument_list|()
argument_list|)
expr_stmt|;
name|c
operator|.
name|setLastName
argument_list|(
name|staff
operator|.
name|getLastName
argument_list|()
argument_list|)
expr_stmt|;
name|c
operator|.
name|setAcademicTitle
argument_list|(
name|staff
operator|.
name|getAcademicTitle
argument_list|()
argument_list|)
expr_stmt|;
name|c
operator|.
name|setEmail
argument_list|(
name|staff
operator|.
name|getEmail
argument_list|()
argument_list|)
expr_stmt|;
name|c
operator|.
name|setFormattedName
argument_list|(
name|staff
operator|.
name|getName
argument_list|(
name|nameFormat
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|c
return|;
block|}
name|Student
name|student
init|=
operator|(
name|Student
operator|)
name|hibSession
operator|.
name|createQuery
argument_list|(
literal|"from Student where session.uniqueId = :sessionId and externalUniqueId = :userId"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"sessionId"
argument_list|,
name|sessionId
argument_list|)
operator|.
name|setString
argument_list|(
literal|"userId"
argument_list|,
name|user
operator|.
name|getExternalUserId
argument_list|()
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
name|student
operator|!=
literal|null
condition|)
block|{
name|ContactInterface
name|c
init|=
operator|new
name|ContactInterface
argument_list|()
decl_stmt|;
name|c
operator|.
name|setExternalId
argument_list|(
name|student
operator|.
name|getExternalUniqueId
argument_list|()
argument_list|)
expr_stmt|;
name|c
operator|.
name|setFirstName
argument_list|(
name|student
operator|.
name|getFirstName
argument_list|()
argument_list|)
expr_stmt|;
name|c
operator|.
name|setMiddleName
argument_list|(
name|student
operator|.
name|getMiddleName
argument_list|()
argument_list|)
expr_stmt|;
name|c
operator|.
name|setLastName
argument_list|(
name|student
operator|.
name|getLastName
argument_list|()
argument_list|)
expr_stmt|;
name|c
operator|.
name|setAcademicTitle
argument_list|(
name|student
operator|.
name|getAcademicTitle
argument_list|()
argument_list|)
expr_stmt|;
name|c
operator|.
name|setEmail
argument_list|(
name|student
operator|.
name|getEmail
argument_list|()
argument_list|)
expr_stmt|;
name|c
operator|.
name|setFormattedName
argument_list|(
name|student
operator|.
name|getName
argument_list|(
name|nameFormat
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|c
return|;
block|}
if|if
condition|(
name|user
operator|.
name|getName
argument_list|()
operator|!=
literal|null
operator|&&
operator|!
name|user
operator|.
name|getName
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|List
argument_list|<
name|PersonInterface
argument_list|>
name|people
init|=
name|GwtRpcServlet
operator|.
name|execute
argument_list|(
operator|new
name|PersonInterface
operator|.
name|LookupRequest
argument_list|(
name|user
operator|.
name|getName
argument_list|()
argument_list|,
literal|"mustHaveExternalId,session="
operator|+
name|sessionId
argument_list|)
argument_list|,
name|applicationContext
argument_list|,
literal|null
argument_list|)
decl_stmt|;
if|if
condition|(
name|people
operator|!=
literal|null
condition|)
block|{
for|for
control|(
name|PersonInterface
name|person
range|:
name|people
control|)
block|{
if|if
condition|(
name|user
operator|.
name|getExternalUserId
argument_list|()
operator|.
name|equals
argument_list|(
name|person
operator|.
name|getId
argument_list|()
argument_list|)
condition|)
block|{
name|ContactInterface
name|c
init|=
operator|new
name|ContactInterface
argument_list|()
decl_stmt|;
name|c
operator|.
name|setFirstName
argument_list|(
name|person
operator|.
name|getFirstName
argument_list|()
argument_list|)
expr_stmt|;
name|c
operator|.
name|setMiddleName
argument_list|(
name|person
operator|.
name|getMiddleName
argument_list|()
argument_list|)
expr_stmt|;
name|c
operator|.
name|setLastName
argument_list|(
name|person
operator|.
name|getLastName
argument_list|()
argument_list|)
expr_stmt|;
name|c
operator|.
name|setAcademicTitle
argument_list|(
name|person
operator|.
name|getAcademicTitle
argument_list|()
argument_list|)
expr_stmt|;
name|c
operator|.
name|setEmail
argument_list|(
name|person
operator|.
name|getEmail
argument_list|()
argument_list|)
expr_stmt|;
name|c
operator|.
name|setPhone
argument_list|(
name|person
operator|.
name|getPhone
argument_list|()
argument_list|)
expr_stmt|;
name|c
operator|.
name|setExternalId
argument_list|(
name|person
operator|.
name|getId
argument_list|()
argument_list|)
expr_stmt|;
return|return
name|c
return|;
block|}
block|}
block|}
block|}
name|ContactInterface
name|c
init|=
operator|new
name|ContactInterface
argument_list|()
decl_stmt|;
if|if
condition|(
name|user
operator|.
name|getName
argument_list|()
operator|!=
literal|null
operator|&&
operator|!
name|user
operator|.
name|getName
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|String
name|name
index|[]
init|=
name|user
operator|.
name|getName
argument_list|()
operator|.
name|split
argument_list|(
literal|" "
argument_list|)
decl_stmt|;
if|if
condition|(
name|name
operator|.
name|length
operator|==
literal|1
condition|)
block|{
name|c
operator|.
name|setLastName
argument_list|(
name|name
index|[
literal|0
index|]
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|name
operator|.
name|length
operator|==
literal|2
condition|)
block|{
name|c
operator|.
name|setFirstName
argument_list|(
name|name
index|[
literal|0
index|]
argument_list|)
expr_stmt|;
name|c
operator|.
name|setLastName
argument_list|(
name|name
index|[
literal|1
index|]
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|c
operator|.
name|setFirstName
argument_list|(
name|name
index|[
literal|0
index|]
argument_list|)
expr_stmt|;
name|String
name|mName
init|=
literal|""
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|1
init|;
name|i
operator|<
name|name
operator|.
name|length
operator|-
literal|1
condition|;
name|i
operator|++
control|)
name|mName
operator|+=
operator|(
name|mName
operator|.
name|isEmpty
argument_list|()
condition|?
literal|""
else|:
literal|" "
operator|)
operator|+
name|name
index|[
name|i
index|]
expr_stmt|;
name|c
operator|.
name|setFirstName
argument_list|(
name|mName
argument_list|)
expr_stmt|;
name|c
operator|.
name|setLastName
argument_list|(
name|name
index|[
name|name
operator|.
name|length
operator|-
literal|1
index|]
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
name|c
operator|.
name|setLastName
argument_list|(
name|user
operator|.
name|getUsername
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|c
operator|.
name|setExternalId
argument_list|(
name|user
operator|.
name|getExternalUserId
argument_list|()
argument_list|)
expr_stmt|;
return|return
name|c
return|;
block|}
specifier|public
name|void
name|setupStandardNotes
parameter_list|(
name|Long
name|sessionId
parameter_list|,
name|UserContext
name|user
parameter_list|,
name|EventPropertiesRpcResponse
name|response
parameter_list|)
block|{
for|for
control|(
name|StandardEventNote
name|note
range|:
name|StandardEventNoteGlobalDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|findAll
argument_list|()
control|)
block|{
name|StandardEventNoteInterface
name|n
init|=
operator|new
name|StandardEventNoteInterface
argument_list|()
decl_stmt|;
name|n
operator|.
name|setId
argument_list|(
name|note
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
name|n
operator|.
name|setReference
argument_list|(
name|note
operator|.
name|getReference
argument_list|()
argument_list|)
expr_stmt|;
name|n
operator|.
name|setNote
argument_list|(
name|note
operator|.
name|getNote
argument_list|()
argument_list|)
expr_stmt|;
name|response
operator|.
name|addStandardNote
argument_list|(
name|n
argument_list|)
expr_stmt|;
block|}
for|for
control|(
name|StandardEventNote
name|note
range|:
operator|(
name|List
argument_list|<
name|StandardEventNote
argument_list|>
operator|)
name|StandardEventNoteSessionDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"from StandardEventNoteSession where session.uniqueId = :sessionId"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"sessionId"
argument_list|,
name|sessionId
argument_list|)
operator|.
name|setCacheable
argument_list|(
literal|true
argument_list|)
operator|.
name|list
argument_list|()
control|)
block|{
name|StandardEventNoteInterface
name|n
init|=
operator|new
name|StandardEventNoteInterface
argument_list|()
decl_stmt|;
name|n
operator|.
name|setId
argument_list|(
name|note
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
name|n
operator|.
name|setReference
argument_list|(
name|note
operator|.
name|getReference
argument_list|()
argument_list|)
expr_stmt|;
name|n
operator|.
name|setNote
argument_list|(
name|note
operator|.
name|getNote
argument_list|()
argument_list|)
expr_stmt|;
name|response
operator|.
name|addStandardNote
argument_list|(
name|n
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|user
operator|!=
literal|null
condition|)
block|{
name|String
name|departments
init|=
literal|""
decl_stmt|;
name|boolean
name|allDepartments
init|=
literal|false
decl_stmt|;
for|for
control|(
name|UserAuthority
name|auth
range|:
name|user
operator|.
name|getAuthorities
argument_list|(
name|user
operator|.
name|getCurrentAuthority
argument_list|()
operator|!=
literal|null
condition|?
name|user
operator|.
name|getCurrentAuthority
argument_list|()
operator|.
name|getRole
argument_list|()
else|:
name|Roles
operator|.
name|ROLE_ANONYMOUS
argument_list|,
operator|new
name|SimpleQualifier
argument_list|(
literal|"Session"
argument_list|,
name|sessionId
argument_list|)
argument_list|)
control|)
block|{
if|if
condition|(
name|auth
operator|.
name|hasRight
argument_list|(
name|Right
operator|.
name|DepartmentIndependent
argument_list|)
condition|)
block|{
name|allDepartments
operator|=
literal|true
expr_stmt|;
break|break;
block|}
else|else
block|{
for|for
control|(
name|Qualifiable
name|q
range|:
name|auth
operator|.
name|getQualifiers
argument_list|(
literal|"Department"
argument_list|)
control|)
name|departments
operator|+=
operator|(
name|departments
operator|.
name|isEmpty
argument_list|()
condition|?
literal|""
else|:
literal|","
operator|)
operator|+
name|q
operator|.
name|getQualifierId
argument_list|()
expr_stmt|;
block|}
block|}
if|if
condition|(
name|allDepartments
condition|)
block|{
for|for
control|(
name|StandardEventNote
name|note
range|:
operator|(
name|List
argument_list|<
name|StandardEventNote
argument_list|>
operator|)
name|StandardEventNoteDepartmentDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"from StandardEventNoteDepartment where department.session.uniqueId = :sessionId"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"sessionId"
argument_list|,
name|sessionId
argument_list|)
operator|.
name|setCacheable
argument_list|(
literal|true
argument_list|)
operator|.
name|list
argument_list|()
control|)
block|{
name|StandardEventNoteInterface
name|n
init|=
operator|new
name|StandardEventNoteInterface
argument_list|()
decl_stmt|;
name|n
operator|.
name|setId
argument_list|(
name|note
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
name|n
operator|.
name|setReference
argument_list|(
name|note
operator|.
name|getReference
argument_list|()
argument_list|)
expr_stmt|;
name|n
operator|.
name|setNote
argument_list|(
name|note
operator|.
name|getNote
argument_list|()
argument_list|)
expr_stmt|;
name|response
operator|.
name|addStandardNote
argument_list|(
name|n
argument_list|)
expr_stmt|;
block|}
block|}
if|else if
condition|(
operator|!
name|departments
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
for|for
control|(
name|StandardEventNote
name|note
range|:
operator|(
name|List
argument_list|<
name|StandardEventNote
argument_list|>
operator|)
name|StandardEventNoteDepartmentDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"from StandardEventNoteDepartment where department.uniqueId in ("
operator|+
name|departments
operator|+
literal|")"
argument_list|)
operator|.
name|setCacheable
argument_list|(
literal|true
argument_list|)
operator|.
name|list
argument_list|()
control|)
block|{
name|StandardEventNoteInterface
name|n
init|=
operator|new
name|StandardEventNoteInterface
argument_list|()
decl_stmt|;
name|n
operator|.
name|setId
argument_list|(
name|note
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
name|n
operator|.
name|setReference
argument_list|(
name|note
operator|.
name|getReference
argument_list|()
argument_list|)
expr_stmt|;
name|n
operator|.
name|setNote
argument_list|(
name|note
operator|.
name|getNote
argument_list|()
argument_list|)
expr_stmt|;
name|response
operator|.
name|addStandardNote
argument_list|(
name|n
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
block|}
end_class

end_unit

