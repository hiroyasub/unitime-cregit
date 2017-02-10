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
name|server
operator|.
name|instructor
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
name|HashMap
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
name|gwt
operator|.
name|command
operator|.
name|client
operator|.
name|GwtRpcException
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
name|GwtRpcImplementation
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
name|resources
operator|.
name|GwtMessages
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
name|server
operator|.
name|ReservationServlet
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
name|InstructorInterface
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
name|InstructorInterface
operator|.
name|AttributeInterface
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
name|InstructorInterface
operator|.
name|AttributeTypeInterface
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
name|InstructorInterface
operator|.
name|DepartmentInterface
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
name|InstructorInterface
operator|.
name|PositionInterface
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
name|InstructorInterface
operator|.
name|PreferenceInterface
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
name|TeachingRequestInterface
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
name|TeachingRequestInterface
operator|.
name|GetRequestsRpcRequest
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
name|TeachingRequestInterface
operator|.
name|GetRequestsRpcResponse
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
name|TeachingRequestInterface
operator|.
name|IncludeLine
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
name|TeachingRequestInterface
operator|.
name|MultiRequest
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
name|TeachingRequestInterface
operator|.
name|RequestedClass
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
name|TeachingRequestInterface
operator|.
name|Responsibility
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
name|TeachingRequestInterface
operator|.
name|SingleRequest
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
name|InstructionalOffering
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
name|InstructorAttribute
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
name|InstructorAttributePref
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
name|InstructorPref
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
name|Preference
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
name|PreferenceLevel
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
name|SchedulingSubpart
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
name|TeachingClassRequest
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
name|TeachingRequest
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
name|TeachingResponsibility
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
name|InstructionalOfferingDAO
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
name|InstructorAttributeDAO
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
name|TeachingResponsibilityDAO
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
name|solver
operator|.
name|ClassAssignmentProxy
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
name|AssignmentService
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
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
annotation|@
name|GwtRpcImplements
argument_list|(
name|GetRequestsRpcRequest
operator|.
name|class
argument_list|)
specifier|public
class|class
name|GetTeachingRequestBackend
implements|implements
name|GwtRpcImplementation
argument_list|<
name|GetRequestsRpcRequest
argument_list|,
name|GetRequestsRpcResponse
argument_list|>
block|{
specifier|protected
specifier|static
name|GwtMessages
name|MESSAGES
init|=
name|Localization
operator|.
name|create
argument_list|(
name|GwtMessages
operator|.
name|class
argument_list|)
decl_stmt|;
annotation|@
name|Autowired
name|AssignmentService
argument_list|<
name|ClassAssignmentProxy
argument_list|>
name|classAssignmentService
decl_stmt|;
annotation|@
name|Override
specifier|public
name|GetRequestsRpcResponse
name|execute
parameter_list|(
name|GetRequestsRpcRequest
name|request
parameter_list|,
name|SessionContext
name|context
parameter_list|)
block|{
name|InstructionalOffering
name|offering
init|=
name|InstructionalOfferingDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|get
argument_list|(
name|request
operator|.
name|getOfferingId
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|offering
operator|==
literal|null
condition|)
throw|throw
operator|new
name|GwtRpcException
argument_list|(
name|MESSAGES
operator|.
name|errorOfferingDoesNotExist
argument_list|(
name|request
operator|.
name|getOfferingId
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
argument_list|)
throw|;
name|context
operator|.
name|checkPermission
argument_list|(
name|offering
operator|.
name|getDepartment
argument_list|()
argument_list|,
name|Right
operator|.
name|InstructorAssignmentPreferences
argument_list|)
expr_stmt|;
name|GetRequestsRpcResponse
name|response
init|=
operator|new
name|GetRequestsRpcResponse
argument_list|()
decl_stmt|;
name|response
operator|.
name|setOffering
argument_list|(
name|ReservationServlet
operator|.
name|convert
argument_list|(
name|offering
argument_list|,
name|InstructionalOfferingDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|getSession
argument_list|()
argument_list|,
literal|null
argument_list|,
name|context
argument_list|,
name|classAssignmentService
operator|.
name|getAssignment
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
for|for
control|(
name|InstructorAttribute
name|attribute
range|:
operator|(
name|List
argument_list|<
name|InstructorAttribute
argument_list|>
operator|)
name|InstructorAttributeDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"from InstructorAttribute a where a.session.uniqueId = :sessionId and (a.department is null or a.department.uniqueId = :departmentId) order by a.name"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"sessionId"
argument_list|,
name|context
operator|.
name|getUser
argument_list|()
operator|.
name|getCurrentAcademicSessionId
argument_list|()
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"departmentId"
argument_list|,
name|offering
operator|.
name|getDepartment
argument_list|()
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
name|list
argument_list|()
control|)
block|{
name|AttributeInterface
name|a
init|=
operator|new
name|AttributeInterface
argument_list|()
decl_stmt|;
name|a
operator|.
name|setId
argument_list|(
name|attribute
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
name|a
operator|.
name|setParentId
argument_list|(
name|attribute
operator|.
name|getParentAttribute
argument_list|()
operator|==
literal|null
condition|?
literal|null
else|:
name|attribute
operator|.
name|getParentAttribute
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
name|a
operator|.
name|setParentName
argument_list|(
name|attribute
operator|.
name|getParentAttribute
argument_list|()
operator|==
literal|null
condition|?
literal|null
else|:
name|attribute
operator|.
name|getParentAttribute
argument_list|()
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
name|a
operator|.
name|setCode
argument_list|(
name|attribute
operator|.
name|getCode
argument_list|()
argument_list|)
expr_stmt|;
name|a
operator|.
name|setName
argument_list|(
name|attribute
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
name|a
operator|.
name|setCanDelete
argument_list|(
name|context
operator|.
name|hasPermission
argument_list|(
name|attribute
argument_list|,
name|Right
operator|.
name|InstructorAttributeDelete
argument_list|)
argument_list|)
expr_stmt|;
name|a
operator|.
name|setCanEdit
argument_list|(
name|context
operator|.
name|hasPermission
argument_list|(
name|attribute
argument_list|,
name|Right
operator|.
name|InstructorAttributeEdit
argument_list|)
argument_list|)
expr_stmt|;
name|a
operator|.
name|setCanAssign
argument_list|(
name|context
operator|.
name|hasPermission
argument_list|(
name|attribute
argument_list|,
name|Right
operator|.
name|InstructorAttributeAssign
argument_list|)
argument_list|)
expr_stmt|;
name|a
operator|.
name|setCanChangeType
argument_list|(
name|attribute
operator|.
name|getChildAttributes
argument_list|()
operator|.
name|isEmpty
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|attribute
operator|.
name|getType
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|AttributeTypeInterface
name|t
init|=
operator|new
name|AttributeTypeInterface
argument_list|()
decl_stmt|;
name|t
operator|.
name|setId
argument_list|(
name|attribute
operator|.
name|getType
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
name|t
operator|.
name|setAbbreviation
argument_list|(
name|attribute
operator|.
name|getType
argument_list|()
operator|.
name|getReference
argument_list|()
argument_list|)
expr_stmt|;
name|t
operator|.
name|setLabel
argument_list|(
name|attribute
operator|.
name|getType
argument_list|()
operator|.
name|getLabel
argument_list|()
argument_list|)
expr_stmt|;
name|t
operator|.
name|setConjunctive
argument_list|(
name|attribute
operator|.
name|getType
argument_list|()
operator|.
name|isConjunctive
argument_list|()
argument_list|)
expr_stmt|;
name|t
operator|.
name|setRequired
argument_list|(
name|attribute
operator|.
name|getType
argument_list|()
operator|.
name|isRequired
argument_list|()
argument_list|)
expr_stmt|;
name|a
operator|.
name|setType
argument_list|(
name|t
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|attribute
operator|.
name|getDepartment
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|DepartmentInterface
name|d
init|=
operator|new
name|DepartmentInterface
argument_list|()
decl_stmt|;
name|d
operator|.
name|setId
argument_list|(
name|attribute
operator|.
name|getDepartment
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
name|d
operator|.
name|setAbbreviation
argument_list|(
name|attribute
operator|.
name|getDepartment
argument_list|()
operator|.
name|getAbbreviation
argument_list|()
argument_list|)
expr_stmt|;
name|d
operator|.
name|setDeptCode
argument_list|(
name|attribute
operator|.
name|getDepartment
argument_list|()
operator|.
name|getDeptCode
argument_list|()
argument_list|)
expr_stmt|;
name|d
operator|.
name|setLabel
argument_list|(
name|attribute
operator|.
name|getDepartment
argument_list|()
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
name|d
operator|.
name|setTitle
argument_list|(
name|attribute
operator|.
name|getDepartment
argument_list|()
operator|.
name|getLabel
argument_list|()
argument_list|)
expr_stmt|;
name|a
operator|.
name|setDepartment
argument_list|(
name|d
argument_list|)
expr_stmt|;
block|}
name|response
operator|.
name|addAttribute
argument_list|(
name|a
argument_list|)
expr_stmt|;
block|}
name|NameFormat
name|instructorNameFormat
init|=
name|NameFormat
operator|.
name|fromReference
argument_list|(
name|UserProperty
operator|.
name|NameFormat
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
name|boolean
name|sortByLastName
init|=
name|CommonValues
operator|.
name|SortByLastName
operator|.
name|eq
argument_list|(
name|UserProperty
operator|.
name|SortNames
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
name|DepartmentalInstructorDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"from DepartmentalInstructor i where i.department.uniqueId = :departmentId"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"departmentId"
argument_list|,
name|offering
operator|.
name|getDepartment
argument_list|()
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
name|list
argument_list|()
control|)
block|{
name|InstructorInterface
name|i
init|=
operator|new
name|InstructorInterface
argument_list|()
decl_stmt|;
name|i
operator|.
name|setId
argument_list|(
name|instructor
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
name|i
operator|.
name|setFirstName
argument_list|(
name|instructor
operator|.
name|getFirstName
argument_list|()
argument_list|)
expr_stmt|;
name|i
operator|.
name|setMiddleName
argument_list|(
name|instructor
operator|.
name|getMiddleName
argument_list|()
argument_list|)
expr_stmt|;
name|i
operator|.
name|setLastName
argument_list|(
name|instructor
operator|.
name|getLastName
argument_list|()
argument_list|)
expr_stmt|;
name|i
operator|.
name|setFormattedName
argument_list|(
name|instructorNameFormat
operator|.
name|format
argument_list|(
name|instructor
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|sortByLastName
condition|)
name|i
operator|.
name|setOrderName
argument_list|(
name|instructor
operator|.
name|nameLastNameFirst
argument_list|()
argument_list|)
expr_stmt|;
name|i
operator|.
name|setExternalId
argument_list|(
name|instructor
operator|.
name|getExternalUniqueId
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|instructor
operator|.
name|getPositionType
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|PositionInterface
name|p
init|=
operator|new
name|PositionInterface
argument_list|()
decl_stmt|;
name|p
operator|.
name|setId
argument_list|(
name|instructor
operator|.
name|getPositionType
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
name|p
operator|.
name|setAbbreviation
argument_list|(
name|instructor
operator|.
name|getPositionType
argument_list|()
operator|.
name|getReference
argument_list|()
argument_list|)
expr_stmt|;
name|p
operator|.
name|setLabel
argument_list|(
name|instructor
operator|.
name|getPositionType
argument_list|()
operator|.
name|getLabel
argument_list|()
argument_list|)
expr_stmt|;
name|p
operator|.
name|setSortOrder
argument_list|(
name|instructor
operator|.
name|getPositionType
argument_list|()
operator|.
name|getSortOrder
argument_list|()
argument_list|)
expr_stmt|;
name|i
operator|.
name|setPosition
argument_list|(
name|p
argument_list|)
expr_stmt|;
block|}
name|PreferenceLevel
name|pref
init|=
name|instructor
operator|.
name|getTeachingPreference
argument_list|()
decl_stmt|;
if|if
condition|(
name|pref
operator|==
literal|null
condition|)
name|pref
operator|=
name|PreferenceLevel
operator|.
name|getPreferenceLevel
argument_list|(
name|PreferenceLevel
operator|.
name|sProhibited
argument_list|)
expr_stmt|;
name|i
operator|.
name|setTeachingPreference
argument_list|(
operator|new
name|PreferenceInterface
argument_list|(
name|pref
operator|.
name|getUniqueId
argument_list|()
argument_list|,
name|PreferenceLevel
operator|.
name|prolog2color
argument_list|(
name|pref
operator|.
name|getPrefProlog
argument_list|()
argument_list|)
argument_list|,
name|pref
operator|.
name|getPrefProlog
argument_list|()
argument_list|,
name|pref
operator|.
name|getPrefName
argument_list|()
argument_list|,
name|pref
operator|.
name|getAbbreviation
argument_list|()
argument_list|,
literal|true
argument_list|)
argument_list|)
expr_stmt|;
name|i
operator|.
name|setMaxLoad
argument_list|(
name|instructor
operator|.
name|getMaxLoad
argument_list|()
argument_list|)
expr_stmt|;
name|response
operator|.
name|addInstructor
argument_list|(
name|i
argument_list|)
expr_stmt|;
block|}
for|for
control|(
name|PreferenceLevel
name|pref
range|:
name|PreferenceLevel
operator|.
name|getPreferenceLevelList
argument_list|()
control|)
block|{
name|response
operator|.
name|addPreference
argument_list|(
operator|new
name|PreferenceInterface
argument_list|(
name|pref
operator|.
name|getUniqueId
argument_list|()
argument_list|,
name|PreferenceLevel
operator|.
name|prolog2bgColor
argument_list|(
name|pref
operator|.
name|getPrefProlog
argument_list|()
argument_list|)
argument_list|,
name|pref
operator|.
name|getPrefProlog
argument_list|()
argument_list|,
name|pref
operator|.
name|getPrefName
argument_list|()
argument_list|,
name|pref
operator|.
name|getAbbreviation
argument_list|()
argument_list|,
literal|true
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|Map
argument_list|<
name|Long
argument_list|,
name|Responsibility
argument_list|>
name|responsibilities
init|=
operator|new
name|HashMap
argument_list|<
name|Long
argument_list|,
name|Responsibility
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|TeachingResponsibility
name|responsibility
range|:
operator|(
name|List
argument_list|<
name|TeachingResponsibility
argument_list|>
operator|)
name|TeachingResponsibilityDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"from TeachingResponsibility order by label"
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
name|Responsibility
name|r
init|=
operator|new
name|Responsibility
argument_list|()
decl_stmt|;
name|r
operator|.
name|setId
argument_list|(
name|responsibility
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
name|r
operator|.
name|setAbbv
argument_list|(
name|responsibility
operator|.
name|getAbbreviation
argument_list|()
argument_list|)
expr_stmt|;
name|r
operator|.
name|setName
argument_list|(
name|responsibility
operator|.
name|getLabel
argument_list|()
argument_list|)
expr_stmt|;
name|r
operator|.
name|setCoordinator
argument_list|(
name|responsibility
operator|.
name|isCoordinator
argument_list|()
argument_list|)
expr_stmt|;
name|r
operator|.
name|setInstructor
argument_list|(
name|responsibility
operator|.
name|isInstructor
argument_list|()
argument_list|)
expr_stmt|;
name|response
operator|.
name|addResponsibility
argument_list|(
name|r
argument_list|)
expr_stmt|;
name|responsibilities
operator|.
name|put
argument_list|(
name|responsibility
operator|.
name|getUniqueId
argument_list|()
argument_list|,
name|r
argument_list|)
expr_stmt|;
block|}
name|Map
argument_list|<
name|TeachingRequest
argument_list|,
name|MultiRequest
argument_list|>
name|requests
init|=
operator|new
name|HashMap
argument_list|<
name|TeachingRequest
argument_list|,
name|MultiRequest
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|TeachingRequest
name|r
range|:
name|offering
operator|.
name|getTeachingRequests
argument_list|()
control|)
block|{
name|TeachingClassRequest
name|master
init|=
name|r
operator|.
name|getMasterRequest
argument_list|(
literal|true
argument_list|)
decl_stmt|;
if|if
condition|(
name|r
operator|.
name|isStandard
argument_list|(
name|master
argument_list|)
operator|&&
operator|!
name|r
operator|.
name|isAssignCoordinator
argument_list|()
condition|)
block|{
name|MultiRequest
name|req
init|=
literal|null
decl_stmt|;
for|for
control|(
name|Map
operator|.
name|Entry
argument_list|<
name|TeachingRequest
argument_list|,
name|MultiRequest
argument_list|>
name|e
range|:
name|requests
operator|.
name|entrySet
argument_list|()
control|)
block|{
if|if
condition|(
name|e
operator|.
name|getKey
argument_list|()
operator|.
name|canCombine
argument_list|(
name|r
argument_list|)
condition|)
block|{
name|req
operator|=
name|e
operator|.
name|getValue
argument_list|()
expr_stmt|;
break|break;
block|}
block|}
if|if
condition|(
name|req
operator|==
literal|null
condition|)
block|{
name|req
operator|=
operator|new
name|MultiRequest
argument_list|()
expr_stmt|;
name|req
operator|.
name|setAssignCoordinator
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|req
operator|.
name|setPercentShare
argument_list|(
literal|0
argument_list|)
expr_stmt|;
name|req
operator|.
name|setSameCommonPreference
argument_list|(
name|r
operator|.
name|getSameCommonPart
argument_list|()
operator|==
literal|null
condition|?
literal|null
else|:
name|r
operator|.
name|getSameCommonPart
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
name|req
operator|.
name|setSameCoursePreference
argument_list|(
name|r
operator|.
name|getSameCoursePreference
argument_list|()
operator|==
literal|null
condition|?
literal|null
else|:
name|r
operator|.
name|getSameCoursePreference
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
name|req
operator|.
name|setTeachingLoad
argument_list|(
name|r
operator|.
name|getTeachingLoad
argument_list|()
argument_list|)
expr_stmt|;
name|req
operator|.
name|setTeachingResponsibility
argument_list|(
name|r
operator|.
name|getResponsibility
argument_list|()
operator|==
literal|null
condition|?
literal|null
else|:
name|responsibilities
operator|.
name|get
argument_list|(
name|r
operator|.
name|getResponsibility
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|response
operator|.
name|addRequest
argument_list|(
name|req
argument_list|)
expr_stmt|;
name|Set
argument_list|<
name|SchedulingSubpart
argument_list|>
name|subparts
init|=
operator|new
name|HashSet
argument_list|<
name|SchedulingSubpart
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|TeachingClassRequest
name|cr
range|:
name|r
operator|.
name|getClassRequests
argument_list|()
control|)
block|{
if|if
condition|(
name|subparts
operator|.
name|add
argument_list|(
name|cr
operator|.
name|getTeachingClass
argument_list|()
operator|.
name|getSchedulingSubpart
argument_list|()
argument_list|)
condition|)
block|{
name|IncludeLine
name|include
init|=
operator|new
name|IncludeLine
argument_list|()
decl_stmt|;
name|include
operator|.
name|setAssign
argument_list|(
name|cr
operator|.
name|isAssignInstructor
argument_list|()
argument_list|)
expr_stmt|;
name|include
operator|.
name|setCommon
argument_list|(
name|cr
operator|.
name|isCommon
argument_list|()
argument_list|)
expr_stmt|;
name|include
operator|.
name|setCanOverlap
argument_list|(
name|cr
operator|.
name|isCanOverlap
argument_list|()
argument_list|)
expr_stmt|;
name|include
operator|.
name|setLead
argument_list|(
name|cr
operator|.
name|isLead
argument_list|()
argument_list|)
expr_stmt|;
name|include
operator|.
name|setShare
argument_list|(
name|cr
operator|.
name|getPercentShare
argument_list|()
operator|==
literal|null
condition|?
literal|0
else|:
name|cr
operator|.
name|getPercentShare
argument_list|()
operator|.
name|intValue
argument_list|()
argument_list|)
expr_stmt|;
name|include
operator|.
name|setOwnerId
argument_list|(
name|cr
operator|.
name|getTeachingClass
argument_list|()
operator|.
name|getSchedulingSubpart
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
name|req
operator|.
name|addSubpart
argument_list|(
name|include
argument_list|)
expr_stmt|;
block|}
block|}
for|for
control|(
name|Preference
name|pref
range|:
operator|new
name|TreeSet
argument_list|<
name|Preference
argument_list|>
argument_list|(
name|r
operator|.
name|getPreferences
argument_list|()
argument_list|)
control|)
block|{
if|if
condition|(
name|pref
operator|instanceof
name|InstructorPref
condition|)
block|{
name|TeachingRequestInterface
operator|.
name|Preference
name|p
init|=
operator|new
name|TeachingRequestInterface
operator|.
name|Preference
argument_list|()
decl_stmt|;
name|p
operator|.
name|setPreferenceId
argument_list|(
name|pref
operator|.
name|getPrefLevel
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
name|p
operator|.
name|setOwnerId
argument_list|(
operator|(
operator|(
name|InstructorPref
operator|)
name|pref
operator|)
operator|.
name|getInstructor
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
name|req
operator|.
name|addInstructorPreference
argument_list|(
name|p
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|pref
operator|instanceof
name|InstructorAttributePref
condition|)
block|{
name|TeachingRequestInterface
operator|.
name|Preference
name|p
init|=
operator|new
name|TeachingRequestInterface
operator|.
name|Preference
argument_list|()
decl_stmt|;
name|p
operator|.
name|setPreferenceId
argument_list|(
name|pref
operator|.
name|getPrefLevel
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
name|p
operator|.
name|setOwnerId
argument_list|(
operator|(
operator|(
name|InstructorAttributePref
operator|)
name|pref
operator|)
operator|.
name|getAttribute
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
name|req
operator|.
name|addAttributePreference
argument_list|(
name|p
argument_list|)
expr_stmt|;
block|}
block|}
name|requests
operator|.
name|put
argument_list|(
name|r
argument_list|,
name|req
argument_list|)
expr_stmt|;
block|}
name|RequestedClass
name|rc
init|=
operator|new
name|RequestedClass
argument_list|()
decl_stmt|;
name|rc
operator|.
name|setNbrInstructors
argument_list|(
name|r
operator|.
name|getNbrInstructors
argument_list|()
argument_list|)
expr_stmt|;
name|rc
operator|.
name|setClassId
argument_list|(
name|master
operator|.
name|getTeachingClass
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
name|rc
operator|.
name|setInstructorIds
argument_list|(
name|toInstructorIds
argument_list|(
name|r
argument_list|)
argument_list|)
expr_stmt|;
name|rc
operator|.
name|setRequestId
argument_list|(
name|r
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
name|req
operator|.
name|addClass
argument_list|(
name|rc
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|SingleRequest
name|req
init|=
operator|new
name|SingleRequest
argument_list|()
decl_stmt|;
name|req
operator|.
name|setRequestId
argument_list|(
name|r
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
name|req
operator|.
name|setInstructorIds
argument_list|(
name|toInstructorIds
argument_list|(
name|r
argument_list|)
argument_list|)
expr_stmt|;
name|req
operator|.
name|setNbrInstructors
argument_list|(
name|r
operator|.
name|getNbrInstructors
argument_list|()
argument_list|)
expr_stmt|;
name|req
operator|.
name|setAssignCoordinator
argument_list|(
name|r
operator|.
name|isAssignCoordinator
argument_list|()
argument_list|)
expr_stmt|;
name|req
operator|.
name|setPercentShare
argument_list|(
name|r
operator|.
name|getPercentShare
argument_list|()
argument_list|)
expr_stmt|;
name|req
operator|.
name|setSameCommonPreference
argument_list|(
name|r
operator|.
name|getSameCommonPart
argument_list|()
operator|==
literal|null
condition|?
literal|null
else|:
name|r
operator|.
name|getSameCommonPart
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
name|req
operator|.
name|setSameCoursePreference
argument_list|(
name|r
operator|.
name|getSameCoursePreference
argument_list|()
operator|==
literal|null
condition|?
literal|null
else|:
name|r
operator|.
name|getSameCoursePreference
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
name|req
operator|.
name|setTeachingLoad
argument_list|(
name|r
operator|.
name|getTeachingLoad
argument_list|()
argument_list|)
expr_stmt|;
name|req
operator|.
name|setTeachingResponsibility
argument_list|(
name|r
operator|.
name|getResponsibility
argument_list|()
operator|==
literal|null
condition|?
literal|null
else|:
name|responsibilities
operator|.
name|get
argument_list|(
name|r
operator|.
name|getResponsibility
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
for|for
control|(
name|TeachingClassRequest
name|cr
range|:
name|r
operator|.
name|getClassRequests
argument_list|()
control|)
block|{
name|IncludeLine
name|include
init|=
operator|new
name|IncludeLine
argument_list|()
decl_stmt|;
name|include
operator|.
name|setAssign
argument_list|(
name|cr
operator|.
name|isAssignInstructor
argument_list|()
argument_list|)
expr_stmt|;
name|include
operator|.
name|setCommon
argument_list|(
name|cr
operator|.
name|isCommon
argument_list|()
argument_list|)
expr_stmt|;
name|include
operator|.
name|setCanOverlap
argument_list|(
name|cr
operator|.
name|isCanOverlap
argument_list|()
argument_list|)
expr_stmt|;
name|include
operator|.
name|setLead
argument_list|(
name|cr
operator|.
name|isLead
argument_list|()
argument_list|)
expr_stmt|;
name|include
operator|.
name|setShare
argument_list|(
name|cr
operator|.
name|getPercentShare
argument_list|()
operator|==
literal|null
condition|?
literal|0
else|:
name|cr
operator|.
name|getPercentShare
argument_list|()
operator|.
name|intValue
argument_list|()
argument_list|)
expr_stmt|;
name|include
operator|.
name|setOwnerId
argument_list|(
name|cr
operator|.
name|getTeachingClass
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
name|req
operator|.
name|addClass
argument_list|(
name|include
argument_list|)
expr_stmt|;
block|}
for|for
control|(
name|Preference
name|pref
range|:
operator|new
name|TreeSet
argument_list|<
name|Preference
argument_list|>
argument_list|(
name|r
operator|.
name|getPreferences
argument_list|()
argument_list|)
control|)
block|{
if|if
condition|(
name|pref
operator|instanceof
name|InstructorPref
condition|)
block|{
name|TeachingRequestInterface
operator|.
name|Preference
name|p
init|=
operator|new
name|TeachingRequestInterface
operator|.
name|Preference
argument_list|()
decl_stmt|;
name|p
operator|.
name|setPreferenceId
argument_list|(
name|pref
operator|.
name|getPrefLevel
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
name|p
operator|.
name|setOwnerId
argument_list|(
operator|(
operator|(
name|InstructorPref
operator|)
name|pref
operator|)
operator|.
name|getInstructor
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
name|req
operator|.
name|addInstructorPreference
argument_list|(
name|p
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|pref
operator|instanceof
name|InstructorAttributePref
condition|)
block|{
name|TeachingRequestInterface
operator|.
name|Preference
name|p
init|=
operator|new
name|TeachingRequestInterface
operator|.
name|Preference
argument_list|()
decl_stmt|;
name|p
operator|.
name|setPreferenceId
argument_list|(
name|pref
operator|.
name|getPrefLevel
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
name|p
operator|.
name|setOwnerId
argument_list|(
operator|(
operator|(
name|InstructorAttributePref
operator|)
name|pref
operator|)
operator|.
name|getAttribute
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
name|req
operator|.
name|addAttributePreference
argument_list|(
name|p
argument_list|)
expr_stmt|;
block|}
block|}
name|response
operator|.
name|addRequest
argument_list|(
name|req
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|response
return|;
block|}
specifier|public
specifier|static
name|List
argument_list|<
name|Long
argument_list|>
name|toInstructorIds
parameter_list|(
name|TeachingRequest
name|request
parameter_list|)
block|{
if|if
condition|(
name|request
operator|.
name|getAssignedInstructors
argument_list|()
operator|==
literal|null
operator|||
name|request
operator|.
name|getAssignedInstructors
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
return|return
literal|null
return|;
name|List
argument_list|<
name|DepartmentalInstructor
argument_list|>
name|instructors
init|=
operator|new
name|ArrayList
argument_list|<
name|DepartmentalInstructor
argument_list|>
argument_list|(
name|request
operator|.
name|getAssignedInstructors
argument_list|()
argument_list|)
decl_stmt|;
name|Collections
operator|.
name|sort
argument_list|(
name|instructors
argument_list|)
expr_stmt|;
name|List
argument_list|<
name|Long
argument_list|>
name|ret
init|=
operator|new
name|ArrayList
argument_list|<
name|Long
argument_list|>
argument_list|(
name|instructors
operator|.
name|size
argument_list|()
argument_list|)
decl_stmt|;
for|for
control|(
name|DepartmentalInstructor
name|instructor
range|:
name|instructors
control|)
name|ret
operator|.
name|add
argument_list|(
name|instructor
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
return|return
name|ret
return|;
block|}
block|}
end_class

end_unit

