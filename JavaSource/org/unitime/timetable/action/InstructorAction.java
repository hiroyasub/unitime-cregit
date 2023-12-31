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
name|Transaction
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
name|Debug
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
name|localization
operator|.
name|messages
operator|.
name|CourseMessages
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
name|form
operator|.
name|InstructorEditForm
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
name|interfaces
operator|.
name|ExternalUidLookup
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
name|interfaces
operator|.
name|ExternalUidLookup
operator|.
name|UserInfo
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
name|PositionType
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
name|StaffDAO
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
name|util
operator|.
name|LookupTables
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
comment|/**  * Methods common to Instructor Add and Edit  *  * @author Tomas Muller, Zuzana Mullerova, Heston Fernandes, Stephanie Schluttenhofer  */
end_comment

begin_class
specifier|public
class|class
name|InstructorAction
extends|extends
name|UniTimeAction
argument_list|<
name|InstructorEditForm
argument_list|>
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
operator|-
literal|3849156971109264456L
decl_stmt|;
specifier|protected
specifier|final
specifier|static
name|CourseMessages
name|MSG
init|=
name|Localization
operator|.
name|create
argument_list|(
name|CourseMessages
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|public
name|String
name|execute
parameter_list|()
throws|throws
name|Exception
block|{
if|if
condition|(
name|form
operator|==
literal|null
condition|)
name|form
operator|=
operator|new
name|InstructorEditForm
argument_list|()
expr_stmt|;
name|LookupTables
operator|.
name|setupPositionTypes
argument_list|(
name|request
argument_list|)
expr_stmt|;
name|form
operator|.
name|setNameFormat
argument_list|(
name|NameFormat
operator|.
name|fromReference
argument_list|(
name|sessionContext
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
argument_list|)
expr_stmt|;
name|form
operator|.
name|setLookupEnabled
argument_list|(
name|ApplicationProperty
operator|.
name|InstructorExternalIdLookup
operator|.
name|isTrue
argument_list|()
operator|&&
name|ApplicationProperty
operator|.
name|InstructorExternalIdLookupClass
operator|.
name|value
argument_list|()
operator|!=
literal|null
argument_list|)
expr_stmt|;
if|if
condition|(
name|op
operator|==
literal|null
condition|)
name|op
operator|=
name|form
operator|.
name|getOp
argument_list|()
expr_stmt|;
else|else
name|form
operator|.
name|setOp
argument_list|(
name|op
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
block|}
specifier|protected
name|void
name|fillStaffInfo
parameter_list|()
throws|throws
name|Exception
block|{
name|Staff
name|staff
init|=
operator|new
name|StaffDAO
argument_list|()
operator|.
name|get
argument_list|(
name|Long
operator|.
name|valueOf
argument_list|(
name|form
operator|.
name|getSearchSelect
argument_list|()
argument_list|)
argument_list|)
decl_stmt|;
name|form
operator|.
name|setPuId
argument_list|(
name|staff
operator|.
name|getExternalUniqueId
argument_list|()
argument_list|)
expr_stmt|;
name|form
operator|.
name|setFname
argument_list|(
name|staff
operator|.
name|getFirstName
argument_list|()
operator|!=
literal|null
condition|?
name|staff
operator|.
name|getFirstName
argument_list|()
operator|.
name|trim
argument_list|()
else|:
literal|""
argument_list|)
expr_stmt|;
name|form
operator|.
name|setMname
argument_list|(
name|staff
operator|.
name|getMiddleName
argument_list|()
operator|!=
literal|null
condition|?
name|staff
operator|.
name|getMiddleName
argument_list|()
operator|.
name|trim
argument_list|()
else|:
literal|""
argument_list|)
expr_stmt|;
name|form
operator|.
name|setLname
argument_list|(
name|staff
operator|.
name|getLastName
argument_list|()
operator|!=
literal|null
condition|?
name|staff
operator|.
name|getLastName
argument_list|()
operator|.
name|trim
argument_list|()
else|:
literal|""
argument_list|)
expr_stmt|;
name|form
operator|.
name|setTitle
argument_list|(
name|staff
operator|.
name|getAcademicTitle
argument_list|()
operator|!=
literal|null
condition|?
name|staff
operator|.
name|getAcademicTitle
argument_list|()
operator|.
name|trim
argument_list|()
else|:
literal|""
argument_list|)
expr_stmt|;
name|form
operator|.
name|setEmail
argument_list|(
name|staff
operator|.
name|getEmail
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|staff
operator|.
name|getPositionType
argument_list|()
operator|!=
literal|null
operator|&&
operator|(
name|form
operator|.
name|getPosType
argument_list|()
operator|==
literal|null
operator|||
name|form
operator|.
name|getPosType
argument_list|()
operator|.
name|trim
argument_list|()
operator|.
name|length
argument_list|()
operator|==
literal|0
operator|)
condition|)
name|form
operator|.
name|setPosType
argument_list|(
name|staff
operator|.
name|getPositionType
argument_list|()
operator|.
name|getUniqueId
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
comment|/**      * Fills form with selected i2a2 info      */
specifier|protected
name|void
name|fillI2A2Info
parameter_list|()
throws|throws
name|Exception
block|{
name|String
name|login
init|=
name|form
operator|.
name|getCareerAcct
argument_list|()
decl_stmt|;
if|if
condition|(
name|login
operator|!=
literal|null
operator|&&
name|login
operator|.
name|trim
argument_list|()
operator|.
name|length
argument_list|()
operator|>
literal|0
operator|&&
name|form
operator|.
name|getLookupEnabled
argument_list|()
condition|)
block|{
name|UserInfo
name|results
init|=
name|lookupInstructor
argument_list|()
decl_stmt|;
if|if
condition|(
name|results
operator|!=
literal|null
condition|)
block|{
name|form
operator|.
name|setPuId
argument_list|(
name|results
operator|.
name|getExternalId
argument_list|()
argument_list|)
expr_stmt|;
name|form
operator|.
name|setCareerAcct
argument_list|(
name|results
operator|.
name|getUserName
argument_list|()
argument_list|)
expr_stmt|;
name|form
operator|.
name|setFname
argument_list|(
name|results
operator|.
name|getFirstName
argument_list|()
argument_list|)
expr_stmt|;
name|form
operator|.
name|setMname
argument_list|(
name|results
operator|.
name|getMiddleName
argument_list|()
argument_list|)
expr_stmt|;
name|form
operator|.
name|setLname
argument_list|(
name|results
operator|.
name|getLastName
argument_list|()
argument_list|)
expr_stmt|;
name|form
operator|.
name|setEmail
argument_list|(
name|results
operator|.
name|getEmail
argument_list|()
argument_list|)
expr_stmt|;
name|form
operator|.
name|setTitle
argument_list|(
name|results
operator|.
name|getAcademicTitle
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
comment|/** 	 * Searches STAFF for matches on name / career account 	 * Searches I2A2 for matching career account 	 */
specifier|protected
name|void
name|findMatchingInstructor
parameter_list|()
throws|throws
name|Exception
block|{
name|form
operator|.
name|setMatchFound
argument_list|(
name|Boolean
operator|.
name|valueOf
argument_list|(
literal|false
argument_list|)
argument_list|)
expr_stmt|;
name|String
name|fname
init|=
name|form
operator|.
name|getFname
argument_list|()
decl_stmt|;
name|String
name|lname
init|=
name|form
operator|.
name|getLname
argument_list|()
decl_stmt|;
name|String
name|login
init|=
name|form
operator|.
name|getCareerAcct
argument_list|()
decl_stmt|;
comment|// Check I2A2
if|if
condition|(
name|login
operator|!=
literal|null
operator|&&
name|login
operator|.
name|trim
argument_list|()
operator|.
name|length
argument_list|()
operator|>
literal|0
operator|&&
name|form
operator|.
name|getLookupEnabled
argument_list|()
condition|)
block|{
name|UserInfo
name|results
init|=
name|lookupInstructor
argument_list|()
decl_stmt|;
if|if
condition|(
name|results
operator|!=
literal|null
condition|)
block|{
name|form
operator|.
name|setI2a2Match
argument_list|(
name|results
argument_list|)
expr_stmt|;
name|form
operator|.
name|setMatchFound
argument_list|(
name|Boolean
operator|.
name|TRUE
argument_list|)
expr_stmt|;
block|}
block|}
comment|// Check Staff
name|List
name|staffList
init|=
name|Staff
operator|.
name|findMatchingName
argument_list|(
name|fname
argument_list|,
name|lname
argument_list|)
decl_stmt|;
name|form
operator|.
name|setStaffMatch
argument_list|(
name|staffList
argument_list|)
expr_stmt|;
if|if
condition|(
name|staffList
operator|!=
literal|null
operator|&&
name|staffList
operator|.
name|size
argument_list|()
operator|>
literal|0
condition|)
name|form
operator|.
name|setMatchFound
argument_list|(
name|Boolean
operator|.
name|valueOf
argument_list|(
literal|true
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|/**      * Lookup instructor details       */
specifier|private
name|UserInfo
name|lookupInstructor
parameter_list|()
throws|throws
name|Exception
block|{
name|String
name|id
init|=
name|form
operator|.
name|getCareerAcct
argument_list|()
decl_stmt|;
if|if
condition|(
name|id
operator|!=
literal|null
operator|&&
name|id
operator|.
name|trim
argument_list|()
operator|.
name|length
argument_list|()
operator|>
literal|0
operator|&&
name|form
operator|.
name|getLookupEnabled
argument_list|()
operator|.
name|booleanValue
argument_list|()
condition|)
block|{
name|String
name|className
init|=
name|ApplicationProperty
operator|.
name|InstructorExternalIdLookupClass
operator|.
name|value
argument_list|()
decl_stmt|;
name|ExternalUidLookup
name|lookup
init|=
operator|(
name|ExternalUidLookup
operator|)
operator|(
name|Class
operator|.
name|forName
argument_list|(
name|className
argument_list|)
operator|.
name|getDeclaredConstructor
argument_list|()
operator|.
name|newInstance
argument_list|()
operator|)
decl_stmt|;
return|return
name|lookup
operator|.
name|doLookup
argument_list|(
name|id
argument_list|)
return|;
block|}
return|return
literal|null
return|;
block|}
comment|/** 	 * Inserts / Updates Instructor Info 	 */
specifier|protected
name|void
name|doUpdate
parameter_list|()
throws|throws
name|Exception
block|{
name|DepartmentalInstructorDAO
name|idao
init|=
operator|new
name|DepartmentalInstructorDAO
argument_list|()
decl_stmt|;
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
init|=
name|idao
operator|.
name|getSession
argument_list|()
decl_stmt|;
name|Transaction
name|tx
init|=
literal|null
decl_stmt|;
try|try
block|{
name|tx
operator|=
name|hibSession
operator|.
name|beginTransaction
argument_list|()
expr_stmt|;
name|DepartmentalInstructor
name|inst
init|=
literal|null
decl_stmt|;
name|String
name|instrId
init|=
name|form
operator|.
name|getInstructorId
argument_list|()
decl_stmt|;
if|if
condition|(
name|instrId
operator|!=
literal|null
operator|&&
name|instrId
operator|.
name|trim
argument_list|()
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
block|{
name|inst
operator|=
operator|new
name|DepartmentalInstructorDAO
argument_list|()
operator|.
name|get
argument_list|(
name|Long
operator|.
name|valueOf
argument_list|(
name|instrId
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|inst
operator|=
operator|new
name|DepartmentalInstructor
argument_list|()
expr_stmt|;
name|inst
operator|.
name|setAttributes
argument_list|(
operator|new
name|HashSet
argument_list|<
name|InstructorAttribute
argument_list|>
argument_list|()
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|form
operator|.
name|getFname
argument_list|()
operator|!=
literal|null
operator|&&
name|form
operator|.
name|getFname
argument_list|()
operator|.
name|trim
argument_list|()
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
block|{
name|inst
operator|.
name|setFirstName
argument_list|(
name|form
operator|.
name|getFname
argument_list|()
operator|.
name|trim
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|inst
operator|.
name|setFirstName
argument_list|(
literal|null
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|form
operator|.
name|getMname
argument_list|()
operator|!=
literal|null
operator|&&
name|form
operator|.
name|getMname
argument_list|()
operator|.
name|trim
argument_list|()
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
block|{
name|inst
operator|.
name|setMiddleName
argument_list|(
name|form
operator|.
name|getMname
argument_list|()
operator|.
name|trim
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|inst
operator|.
name|setMiddleName
argument_list|(
literal|null
argument_list|)
expr_stmt|;
block|}
name|inst
operator|.
name|setLastName
argument_list|(
name|form
operator|.
name|getLname
argument_list|()
operator|.
name|trim
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|form
operator|.
name|getTitle
argument_list|()
operator|!=
literal|null
operator|&&
name|form
operator|.
name|getTitle
argument_list|()
operator|.
name|trim
argument_list|()
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
block|{
name|inst
operator|.
name|setAcademicTitle
argument_list|(
name|form
operator|.
name|getTitle
argument_list|()
operator|.
name|trim
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|inst
operator|.
name|setAcademicTitle
argument_list|(
literal|null
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|form
operator|.
name|getPuId
argument_list|()
operator|!=
literal|null
operator|&&
name|form
operator|.
name|getPuId
argument_list|()
operator|.
name|trim
argument_list|()
operator|.
name|length
argument_list|()
operator|>
literal|0
operator|&&
operator|!
name|form
operator|.
name|getPuId
argument_list|()
operator|.
name|equalsIgnoreCase
argument_list|(
literal|"null"
argument_list|)
condition|)
block|{
name|inst
operator|.
name|setExternalUniqueId
argument_list|(
name|form
operator|.
name|getPuId
argument_list|()
operator|.
name|trim
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|inst
operator|.
name|setExternalUniqueId
argument_list|(
literal|null
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|form
operator|.
name|getCareerAcct
argument_list|()
operator|!=
literal|null
operator|&&
name|form
operator|.
name|getCareerAcct
argument_list|()
operator|.
name|trim
argument_list|()
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
block|{
name|inst
operator|.
name|setCareerAcct
argument_list|(
name|form
operator|.
name|getCareerAcct
argument_list|()
operator|.
name|trim
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|inst
operator|.
name|setCareerAcct
argument_list|(
literal|null
argument_list|)
expr_stmt|;
block|}
name|inst
operator|.
name|setEmail
argument_list|(
name|form
operator|.
name|getEmail
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|form
operator|.
name|getPosType
argument_list|()
operator|!=
literal|null
operator|&&
name|form
operator|.
name|getPosType
argument_list|()
operator|.
name|trim
argument_list|()
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
block|{
name|PositionType
name|pt
init|=
name|PositionType
operator|.
name|findById
argument_list|(
name|Long
operator|.
name|valueOf
argument_list|(
name|form
operator|.
name|getPosType
argument_list|()
operator|.
name|trim
argument_list|()
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|pt
operator|!=
literal|null
condition|)
block|{
name|inst
operator|.
name|setPositionType
argument_list|(
name|pt
argument_list|)
expr_stmt|;
block|}
block|}
if|if
condition|(
name|form
operator|.
name|getNote
argument_list|()
operator|!=
literal|null
operator|&&
operator|!
name|form
operator|.
name|getNote
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
if|if
condition|(
name|form
operator|.
name|getNote
argument_list|()
operator|.
name|length
argument_list|()
operator|>
literal|2048
condition|)
name|inst
operator|.
name|setNote
argument_list|(
name|form
operator|.
name|getNote
argument_list|()
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
literal|2048
argument_list|)
argument_list|)
expr_stmt|;
else|else
name|inst
operator|.
name|setNote
argument_list|(
name|form
operator|.
name|getNote
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
name|inst
operator|.
name|setNote
argument_list|(
literal|null
argument_list|)
expr_stmt|;
name|Department
name|d
init|=
literal|null
decl_stmt|;
comment|//get department
if|if
condition|(
name|sessionContext
operator|.
name|getAttribute
argument_list|(
name|SessionAttribute
operator|.
name|DepartmentId
argument_list|)
operator|!=
literal|null
condition|)
block|{
name|String
name|deptId
init|=
operator|(
name|String
operator|)
name|sessionContext
operator|.
name|getAttribute
argument_list|(
name|SessionAttribute
operator|.
name|DepartmentId
argument_list|)
decl_stmt|;
name|d
operator|=
operator|new
name|DepartmentDAO
argument_list|()
operator|.
name|get
argument_list|(
name|Long
operator|.
name|valueOf
argument_list|(
name|deptId
argument_list|)
argument_list|)
expr_stmt|;
name|inst
operator|.
name|setDepartment
argument_list|(
name|d
argument_list|)
expr_stmt|;
name|d
operator|.
name|getInstructors
argument_list|()
operator|.
name|add
argument_list|(
name|inst
argument_list|)
expr_stmt|;
block|}
else|else
throw|throw
operator|new
name|Exception
argument_list|(
literal|"Department Id could not be retrieved from session"
argument_list|)
throw|;
name|inst
operator|.
name|setIgnoreToFar
argument_list|(
name|Boolean
operator|.
name|valueOf
argument_list|(
name|form
operator|.
name|getIgnoreDist
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|hibSession
operator|.
name|saveOrUpdate
argument_list|(
name|inst
argument_list|)
expr_stmt|;
name|ChangeLog
operator|.
name|addChange
argument_list|(
name|hibSession
argument_list|,
name|sessionContext
argument_list|,
name|inst
argument_list|,
name|ChangeLog
operator|.
name|Source
operator|.
name|INSTRUCTOR_EDIT
argument_list|,
operator|(
name|instrId
operator|==
literal|null
operator|||
name|instrId
operator|.
name|trim
argument_list|()
operator|.
name|length
argument_list|()
operator|<=
literal|0
condition|?
name|ChangeLog
operator|.
name|Operation
operator|.
name|CREATE
else|:
name|ChangeLog
operator|.
name|Operation
operator|.
name|UPDATE
operator|)
argument_list|,
literal|null
argument_list|,
name|inst
operator|.
name|getDepartment
argument_list|()
argument_list|)
expr_stmt|;
name|tx
operator|.
name|commit
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|Debug
operator|.
name|error
argument_list|(
name|e
argument_list|)
expr_stmt|;
try|try
block|{
if|if
condition|(
name|tx
operator|!=
literal|null
operator|&&
name|tx
operator|.
name|isActive
argument_list|()
condition|)
name|tx
operator|.
name|rollback
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e1
parameter_list|)
block|{
block|}
throw|throw
name|e
throw|;
block|}
block|}
comment|/**      * Checks that combination of Instructor/Dept       * does not already exist      */
specifier|protected
name|boolean
name|isDeptInstructorUnique
parameter_list|()
block|{
name|String
name|query
init|=
literal|"from DepartmentalInstructor "
operator|+
literal|"where externalUniqueId=:puid and department.uniqueId=:deptId"
decl_stmt|;
if|if
condition|(
name|form
operator|.
name|getInstructorId
argument_list|()
operator|!=
literal|null
operator|&&
name|form
operator|.
name|getInstructorId
argument_list|()
operator|.
name|trim
argument_list|()
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
block|{
name|query
operator|+=
literal|" and uniqueId!=:uniqueId"
expr_stmt|;
block|}
name|DepartmentalInstructorDAO
name|ddao
init|=
operator|new
name|DepartmentalInstructorDAO
argument_list|()
decl_stmt|;
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
init|=
name|ddao
operator|.
name|getSession
argument_list|()
decl_stmt|;
name|String
name|deptId
init|=
operator|(
name|String
operator|)
name|request
operator|.
name|getSession
argument_list|()
operator|.
name|getAttribute
argument_list|(
name|Constants
operator|.
name|DEPT_ID_ATTR_NAME
argument_list|)
decl_stmt|;
name|Query
name|q
init|=
name|hibSession
operator|.
name|createQuery
argument_list|(
name|query
argument_list|)
decl_stmt|;
name|q
operator|.
name|setString
argument_list|(
literal|"puid"
argument_list|,
name|form
operator|.
name|getPuId
argument_list|()
operator|.
name|trim
argument_list|()
argument_list|)
expr_stmt|;
name|q
operator|.
name|setLong
argument_list|(
literal|"deptId"
argument_list|,
name|Long
operator|.
name|parseLong
argument_list|(
name|deptId
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|form
operator|.
name|getInstructorId
argument_list|()
operator|!=
literal|null
operator|&&
name|form
operator|.
name|getInstructorId
argument_list|()
operator|.
name|trim
argument_list|()
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
block|{
name|q
operator|.
name|setLong
argument_list|(
literal|"uniqueId"
argument_list|,
name|Long
operator|.
name|parseLong
argument_list|(
name|form
operator|.
name|getInstructorId
argument_list|()
operator|.
name|trim
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
return|return
operator|(
name|q
operator|.
name|list
argument_list|()
operator|.
name|size
argument_list|()
operator|==
literal|0
operator|)
return|;
block|}
block|}
end_class

end_unit

