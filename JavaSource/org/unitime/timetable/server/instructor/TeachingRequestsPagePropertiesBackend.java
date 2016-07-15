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
name|client
operator|.
name|instructor
operator|.
name|InstructorAvailabilityWidget
operator|.
name|InstructorAvailabilityModel
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
name|GwtConstants
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
name|RoomInterface
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
name|InstructorInterface
operator|.
name|SubjectAreaInterface
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
name|TeachingRequestsPagePropertiesRequest
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
name|TeachingRequestsPagePropertiesResponse
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
name|RoomInterface
operator|.
name|RoomSharingOption
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
name|InstructorAttributeType
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
name|dao
operator|.
name|InstructorAttributeTypeDAO
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
name|instructor
operator|.
name|InstructorSchedulingProxy
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
name|SolverService
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

begin_import
import|import
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|webutil
operator|.
name|RequiredTimeTable
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
annotation|@
name|GwtRpcImplements
argument_list|(
name|TeachingRequestsPagePropertiesRequest
operator|.
name|class
argument_list|)
specifier|public
class|class
name|TeachingRequestsPagePropertiesBackend
implements|implements
name|GwtRpcImplementation
argument_list|<
name|TeachingRequestsPagePropertiesRequest
argument_list|,
name|TeachingRequestsPagePropertiesResponse
argument_list|>
block|{
specifier|protected
specifier|static
name|GwtConstants
name|CONSTANTS
init|=
name|Localization
operator|.
name|create
argument_list|(
name|GwtConstants
operator|.
name|class
argument_list|)
decl_stmt|;
annotation|@
name|Autowired
name|SolverService
argument_list|<
name|InstructorSchedulingProxy
argument_list|>
name|instructorSchedulingSolverService
decl_stmt|;
annotation|@
name|Override
specifier|public
name|TeachingRequestsPagePropertiesResponse
name|execute
parameter_list|(
name|TeachingRequestsPagePropertiesRequest
name|request
parameter_list|,
name|SessionContext
name|context
parameter_list|)
block|{
name|context
operator|.
name|checkPermission
argument_list|(
name|Right
operator|.
name|InstructorSchedulingSolver
argument_list|)
expr_stmt|;
name|InstructorSchedulingProxy
name|solver
init|=
name|instructorSchedulingSolverService
operator|.
name|getSolver
argument_list|()
decl_stmt|;
name|Long
name|ownerId
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|solver
operator|!=
literal|null
condition|)
name|ownerId
operator|=
name|solver
operator|.
name|getProperties
argument_list|()
operator|.
name|getPropertyLong
argument_list|(
literal|"General.SolverGroupId"
argument_list|,
literal|null
argument_list|)
expr_stmt|;
name|TeachingRequestsPagePropertiesResponse
name|ret
init|=
operator|new
name|TeachingRequestsPagePropertiesResponse
argument_list|()
decl_stmt|;
for|for
control|(
name|SubjectArea
name|sa
range|:
name|SubjectArea
operator|.
name|getUserSubjectAreas
argument_list|(
name|context
operator|.
name|getUser
argument_list|()
argument_list|,
literal|true
argument_list|)
control|)
block|{
if|if
condition|(
name|ownerId
operator|!=
literal|null
operator|&&
operator|(
name|sa
operator|.
name|getDepartment
argument_list|()
operator|.
name|getSolverGroup
argument_list|()
operator|==
literal|null
operator|||
operator|!
name|ownerId
operator|.
name|equals
argument_list|(
name|sa
operator|.
name|getDepartment
argument_list|()
operator|.
name|getSolverGroup
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|)
operator|)
condition|)
continue|continue;
name|boolean
name|hasTeachingPreference
init|=
literal|false
decl_stmt|;
for|for
control|(
name|DepartmentalInstructor
name|di
range|:
name|sa
operator|.
name|getDepartment
argument_list|()
operator|.
name|getInstructors
argument_list|()
control|)
if|if
condition|(
name|di
operator|.
name|getTeachingPreference
argument_list|()
operator|!=
literal|null
operator|&&
operator|!
name|PreferenceLevel
operator|.
name|sProhibited
operator|.
name|equals
argument_list|(
name|di
operator|.
name|getTeachingPreference
argument_list|()
operator|.
name|getPrefProlog
argument_list|()
argument_list|)
condition|)
block|{
name|hasTeachingPreference
operator|=
literal|true
expr_stmt|;
break|break;
block|}
if|if
condition|(
operator|!
name|hasTeachingPreference
condition|)
continue|continue;
name|SubjectAreaInterface
name|subject
init|=
operator|new
name|SubjectAreaInterface
argument_list|()
decl_stmt|;
name|subject
operator|.
name|setId
argument_list|(
name|sa
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
name|subject
operator|.
name|setAbbreviation
argument_list|(
name|sa
operator|.
name|getSubjectAreaAbbreviation
argument_list|()
argument_list|)
expr_stmt|;
name|subject
operator|.
name|setLabel
argument_list|(
name|sa
operator|.
name|getTitle
argument_list|()
argument_list|)
expr_stmt|;
name|ret
operator|.
name|addSubjectArea
argument_list|(
name|subject
argument_list|)
expr_stmt|;
block|}
for|for
control|(
name|Department
name|d
range|:
name|Department
operator|.
name|getUserDepartments
argument_list|(
name|context
operator|.
name|getUser
argument_list|()
argument_list|)
control|)
block|{
if|if
condition|(
name|ownerId
operator|!=
literal|null
operator|&&
operator|(
name|d
operator|.
name|getSolverGroup
argument_list|()
operator|==
literal|null
operator|||
operator|!
name|ownerId
operator|.
name|equals
argument_list|(
name|d
operator|.
name|getSolverGroup
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|)
operator|)
condition|)
continue|continue;
name|boolean
name|hasTeachingPreference
init|=
literal|false
decl_stmt|;
for|for
control|(
name|DepartmentalInstructor
name|di
range|:
name|d
operator|.
name|getInstructors
argument_list|()
control|)
if|if
condition|(
name|di
operator|.
name|getTeachingPreference
argument_list|()
operator|!=
literal|null
operator|&&
operator|!
name|PreferenceLevel
operator|.
name|sProhibited
operator|.
name|equals
argument_list|(
name|di
operator|.
name|getTeachingPreference
argument_list|()
operator|.
name|getPrefProlog
argument_list|()
argument_list|)
condition|)
block|{
name|hasTeachingPreference
operator|=
literal|true
expr_stmt|;
break|break;
block|}
if|if
condition|(
operator|!
name|hasTeachingPreference
condition|)
continue|continue;
name|DepartmentInterface
name|department
init|=
operator|new
name|DepartmentInterface
argument_list|()
decl_stmt|;
name|department
operator|.
name|setId
argument_list|(
name|d
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
name|department
operator|.
name|setDeptCode
argument_list|(
name|d
operator|.
name|getDeptCode
argument_list|()
argument_list|)
expr_stmt|;
name|department
operator|.
name|setLabel
argument_list|(
name|d
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
name|department
operator|.
name|setTitle
argument_list|(
name|d
operator|.
name|getLabel
argument_list|()
argument_list|)
expr_stmt|;
name|department
operator|.
name|setAbbreviation
argument_list|(
name|d
operator|.
name|getAbbreviation
argument_list|()
argument_list|)
expr_stmt|;
name|ret
operator|.
name|addDepartment
argument_list|(
name|department
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
name|ret
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
name|String
name|sa
init|=
operator|(
name|String
operator|)
name|context
operator|.
name|getAttribute
argument_list|(
name|SessionAttribute
operator|.
name|OfferingsSubjectArea
argument_list|)
decl_stmt|;
if|if
condition|(
name|Constants
operator|.
name|ALL_OPTION_VALUE
operator|.
name|equals
argument_list|(
name|sa
argument_list|)
condition|)
name|ret
operator|.
name|setLastSubjectAreaId
argument_list|(
operator|-
literal|1l
argument_list|)
expr_stmt|;
if|else if
condition|(
name|sa
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
name|sa
operator|.
name|indexOf
argument_list|(
literal|','
argument_list|)
operator|>=
literal|0
condition|)
name|sa
operator|=
name|sa
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|sa
operator|.
name|indexOf
argument_list|(
literal|','
argument_list|)
argument_list|)
expr_stmt|;
name|ret
operator|.
name|setLastSubjectAreaId
argument_list|(
name|Long
operator|.
name|valueOf
argument_list|(
name|sa
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|String
name|deptId
init|=
operator|(
name|String
operator|)
name|context
operator|.
name|getAttribute
argument_list|(
name|SessionAttribute
operator|.
name|DepartmentId
argument_list|)
decl_stmt|;
if|if
condition|(
name|deptId
operator|!=
literal|null
condition|)
block|{
try|try
block|{
name|ret
operator|.
name|setLastDepartmentId
argument_list|(
name|Long
operator|.
name|valueOf
argument_list|(
name|deptId
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|NumberFormatException
name|e
parameter_list|)
block|{
block|}
block|}
for|for
control|(
name|InstructorAttributeType
name|type
range|:
operator|(
name|List
argument_list|<
name|InstructorAttributeType
argument_list|>
operator|)
name|InstructorAttributeTypeDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"from InstructorAttributeType order by label"
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
name|type
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
name|t
operator|.
name|setAbbreviation
argument_list|(
name|type
operator|.
name|getReference
argument_list|()
argument_list|)
expr_stmt|;
name|t
operator|.
name|setLabel
argument_list|(
name|type
operator|.
name|getLabel
argument_list|()
argument_list|)
expr_stmt|;
name|t
operator|.
name|setConjunctive
argument_list|(
name|type
operator|.
name|isConjunctive
argument_list|()
argument_list|)
expr_stmt|;
name|t
operator|.
name|setRequired
argument_list|(
name|type
operator|.
name|isRequired
argument_list|()
argument_list|)
expr_stmt|;
name|ret
operator|.
name|addAttributeType
argument_list|(
name|t
argument_list|)
expr_stmt|;
block|}
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
literal|true
condition|;
name|i
operator|++
control|)
block|{
name|String
name|mode
init|=
name|ApplicationProperty
operator|.
name|RoomSharingMode
operator|.
name|value
argument_list|(
name|String
operator|.
name|valueOf
argument_list|(
literal|1
operator|+
name|i
argument_list|)
argument_list|,
name|i
operator|<
name|CONSTANTS
operator|.
name|roomSharingModes
argument_list|()
operator|.
name|length
condition|?
name|CONSTANTS
operator|.
name|roomSharingModes
argument_list|()
index|[
name|i
index|]
else|:
literal|null
argument_list|)
decl_stmt|;
if|if
condition|(
name|mode
operator|==
literal|null
operator|||
name|mode
operator|.
name|isEmpty
argument_list|()
condition|)
break|break;
name|ret
operator|.
name|addMode
argument_list|(
operator|new
name|RoomInterface
operator|.
name|RoomSharingDisplayMode
argument_list|(
name|mode
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|ret
operator|.
name|setHasSolver
argument_list|(
name|solver
operator|!=
literal|null
argument_list|)
expr_stmt|;
name|InstructorAvailabilityModel
name|model
init|=
operator|new
name|InstructorAvailabilityModel
argument_list|()
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
literal|true
condition|;
name|i
operator|++
control|)
block|{
name|String
name|mode
init|=
name|ApplicationProperty
operator|.
name|RoomSharingMode
operator|.
name|value
argument_list|(
name|String
operator|.
name|valueOf
argument_list|(
literal|1
operator|+
name|i
argument_list|)
argument_list|,
name|i
operator|<
name|CONSTANTS
operator|.
name|roomSharingModes
argument_list|()
operator|.
name|length
condition|?
name|CONSTANTS
operator|.
name|roomSharingModes
argument_list|()
index|[
name|i
index|]
else|:
literal|null
argument_list|)
decl_stmt|;
if|if
condition|(
name|mode
operator|==
literal|null
operator|||
name|mode
operator|.
name|isEmpty
argument_list|()
condition|)
break|break;
name|model
operator|.
name|addMode
argument_list|(
operator|new
name|RoomInterface
operator|.
name|RoomSharingDisplayMode
argument_list|(
name|mode
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|model
operator|.
name|setDefaultEditable
argument_list|(
literal|true
argument_list|)
expr_stmt|;
for|for
control|(
name|PreferenceLevel
name|pref
range|:
name|PreferenceLevel
operator|.
name|getPreferenceLevelList
argument_list|(
literal|true
argument_list|)
control|)
block|{
if|if
condition|(
name|PreferenceLevel
operator|.
name|sRequired
operator|.
name|equals
argument_list|(
name|pref
operator|.
name|getPrefProlog
argument_list|()
argument_list|)
condition|)
continue|continue;
name|RoomSharingOption
name|option
init|=
operator|new
name|RoomSharingOption
argument_list|(
name|model
operator|.
name|char2id
argument_list|(
name|PreferenceLevel
operator|.
name|prolog2char
argument_list|(
name|pref
operator|.
name|getPrefProlog
argument_list|()
argument_list|)
argument_list|)
argument_list|,
name|pref
operator|.
name|prefcolor
argument_list|()
argument_list|,
literal|""
argument_list|,
name|pref
operator|.
name|getPrefName
argument_list|()
argument_list|,
literal|true
argument_list|)
decl_stmt|;
name|model
operator|.
name|addOption
argument_list|(
name|option
argument_list|)
expr_stmt|;
if|if
condition|(
name|PreferenceLevel
operator|.
name|sNeutral
operator|.
name|equals
argument_list|(
name|pref
operator|.
name|getPrefProlog
argument_list|()
argument_list|)
condition|)
name|model
operator|.
name|setDefaultOption
argument_list|(
name|option
argument_list|)
expr_stmt|;
block|}
name|String
name|defaultGridSize
init|=
name|RequiredTimeTable
operator|.
name|getTimeGridSize
argument_list|(
name|context
operator|.
name|getUser
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|defaultGridSize
operator|!=
literal|null
condition|)
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|model
operator|.
name|getModes
argument_list|()
operator|.
name|size
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
if|if
condition|(
name|model
operator|.
name|getModes
argument_list|()
operator|.
name|get
argument_list|(
name|i
argument_list|)
operator|.
name|getName
argument_list|()
operator|.
name|equals
argument_list|(
name|defaultGridSize
argument_list|)
condition|)
block|{
name|model
operator|.
name|setDefaultMode
argument_list|(
name|i
argument_list|)
expr_stmt|;
break|break;
block|}
block|}
name|model
operator|.
name|setDefaultHorizontal
argument_list|(
name|CommonValues
operator|.
name|HorizontalGrid
operator|.
name|eq
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
name|GridOrientation
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|model
operator|.
name|setNoteEditable
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|ret
operator|.
name|setInstructorAvailabilityModel
argument_list|(
name|model
argument_list|)
expr_stmt|;
return|return
name|ret
return|;
block|}
block|}
end_class

end_unit
