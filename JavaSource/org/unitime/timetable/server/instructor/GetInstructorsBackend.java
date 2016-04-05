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
name|GwtRpcResponseList
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
name|GetInstructorsRequest
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
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
annotation|@
name|GwtRpcImplements
argument_list|(
name|GetInstructorsRequest
operator|.
name|class
argument_list|)
specifier|public
class|class
name|GetInstructorsBackend
implements|implements
name|GwtRpcImplementation
argument_list|<
name|GetInstructorsRequest
argument_list|,
name|GwtRpcResponseList
argument_list|<
name|InstructorInterface
argument_list|>
argument_list|>
block|{
annotation|@
name|Override
specifier|public
name|GwtRpcResponseList
argument_list|<
name|InstructorInterface
argument_list|>
name|execute
parameter_list|(
name|GetInstructorsRequest
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
name|request
operator|.
name|getDepartmentId
argument_list|()
argument_list|,
literal|"Department"
argument_list|,
name|Right
operator|.
name|InstructorAttributes
argument_list|)
expr_stmt|;
name|GwtRpcResponseList
argument_list|<
name|InstructorInterface
argument_list|>
name|result
init|=
operator|new
name|GwtRpcResponseList
argument_list|<
name|InstructorInterface
argument_list|>
argument_list|()
decl_stmt|;
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
name|request
operator|.
name|getDepartmentId
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
for|for
control|(
name|InstructorAttribute
name|attribute
range|:
name|instructor
operator|.
name|getAttributes
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
name|i
operator|.
name|addAttribute
argument_list|(
name|a
argument_list|)
expr_stmt|;
block|}
name|result
operator|.
name|add
argument_list|(
name|i
argument_list|)
expr_stmt|;
block|}
return|return
name|result
return|;
block|}
block|}
end_class

end_unit

