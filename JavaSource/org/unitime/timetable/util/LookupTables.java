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
name|util
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
name|org
operator|.
name|apache
operator|.
name|struts
operator|.
name|util
operator|.
name|LabelValueBean
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
name|Building
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
name|CourseCreditFormat
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
name|CourseCreditType
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
name|CourseCreditUnitType
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
name|CourseOffering
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
name|DatePattern
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
name|DepartmentStatusType
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
name|DistributionType
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
name|ExamPeriod
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
name|ExamType
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
name|ItypeDesc
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
name|OfferingConsentType
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
name|PreferenceGroup
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
name|Room
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
name|RoomFeature
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
name|RoomGroup
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
name|CourseOfferingComparator
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
name|CourseTypeDAO
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
name|UserContext
import|;
end_import

begin_comment
comment|/**  * Contains methods on static read-only lookup tables  *   * @author Heston Fernandes, Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|LookupTables
block|{
comment|/**      * Get Itypes and store it in request object      * @param request      * @throws Exception      */
specifier|public
specifier|static
name|void
name|setupItypes
parameter_list|(
name|HttpServletRequest
name|request
parameter_list|,
name|boolean
name|basic
parameter_list|)
block|{
name|request
operator|.
name|setAttribute
argument_list|(
name|ItypeDesc
operator|.
name|ITYPE_ATTR_NAME
argument_list|,
name|ItypeDesc
operator|.
name|findAll
argument_list|(
name|basic
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|/**      * Get ExternalDepts and store it in request object      * @param request      * @throws Exception      */
specifier|public
specifier|static
name|void
name|setupExternalDepts
parameter_list|(
name|HttpServletRequest
name|request
parameter_list|,
name|Long
name|sessionId
parameter_list|)
throws|throws
name|Exception
block|{
name|request
operator|.
name|setAttribute
argument_list|(
name|Department
operator|.
name|EXTERNAL_DEPT_ATTR_NAME
argument_list|,
name|Department
operator|.
name|findAllExternal
argument_list|(
name|sessionId
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|/**      * Get all departments that are not external and store it in request object      * @param request      * @throws Exception      */
specifier|public
specifier|static
name|void
name|setupNonExternalDepts
parameter_list|(
name|HttpServletRequest
name|request
parameter_list|,
name|Long
name|sessionId
parameter_list|)
throws|throws
name|Exception
block|{
name|request
operator|.
name|setAttribute
argument_list|(
name|Department
operator|.
name|DEPT_ATTR_NAME
argument_list|,
name|Department
operator|.
name|findAllNonExternal
argument_list|(
name|sessionId
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|/**      * Get All Depts and store it in request object      * @param request      * @throws Exception      */
specifier|public
specifier|static
name|void
name|setupDepts
parameter_list|(
name|HttpServletRequest
name|request
parameter_list|,
name|Long
name|sessionId
parameter_list|)
throws|throws
name|Exception
block|{
name|request
operator|.
name|setAttribute
argument_list|(
name|Department
operator|.
name|DEPT_ATTR_NAME
argument_list|,
name|Department
operator|.
name|findAll
argument_list|(
name|sessionId
argument_list|)
argument_list|)
expr_stmt|;
block|}
specifier|public
specifier|static
name|void
name|setupDepartments
parameter_list|(
name|HttpServletRequest
name|request
parameter_list|,
name|SessionContext
name|context
parameter_list|,
name|boolean
name|includeExternal
parameter_list|)
throws|throws
name|Exception
block|{
name|TreeSet
argument_list|<
name|Department
argument_list|>
name|departments
init|=
name|Department
operator|.
name|getUserDepartments
argument_list|(
name|context
operator|.
name|getUser
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|includeExternal
condition|)
name|departments
operator|.
name|addAll
argument_list|(
name|Department
operator|.
name|findAllExternal
argument_list|(
name|context
operator|.
name|getUser
argument_list|()
operator|.
name|getCurrentAcademicSessionId
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|List
argument_list|<
name|LabelValueBean
argument_list|>
name|deptList
init|=
operator|new
name|ArrayList
argument_list|<
name|LabelValueBean
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|Department
name|d
range|:
name|departments
control|)
block|{
name|String
name|code
init|=
name|d
operator|.
name|getDeptCode
argument_list|()
operator|.
name|trim
argument_list|()
decl_stmt|;
name|String
name|abbv
init|=
name|d
operator|.
name|getName
argument_list|()
operator|.
name|trim
argument_list|()
decl_stmt|;
if|if
condition|(
name|d
operator|.
name|isExternalManager
argument_list|()
condition|)
name|deptList
operator|.
name|add
argument_list|(
operator|new
name|LabelValueBean
argument_list|(
name|code
operator|+
literal|" - "
operator|+
name|abbv
operator|+
literal|" ("
operator|+
name|d
operator|.
name|getExternalMgrLabel
argument_list|()
operator|+
literal|")"
argument_list|,
name|code
argument_list|)
argument_list|)
expr_stmt|;
else|else
name|deptList
operator|.
name|add
argument_list|(
operator|new
name|LabelValueBean
argument_list|(
name|code
operator|+
literal|" - "
operator|+
name|abbv
argument_list|,
name|code
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|request
operator|.
name|setAttribute
argument_list|(
name|Department
operator|.
name|DEPT_ATTR_NAME
argument_list|,
name|deptList
argument_list|)
expr_stmt|;
block|}
specifier|public
specifier|static
name|void
name|setupRooms
parameter_list|(
name|HttpServletRequest
name|request
parameter_list|,
name|PreferenceGroup
name|pg
parameter_list|)
throws|throws
name|Exception
block|{
name|request
operator|.
name|setAttribute
argument_list|(
name|Room
operator|.
name|ROOM_LIST_ATTR_NAME
argument_list|,
name|pg
operator|.
name|getAvailableRooms
argument_list|()
argument_list|)
expr_stmt|;
block|}
specifier|public
specifier|static
name|void
name|setupBldgs
parameter_list|(
name|HttpServletRequest
name|request
parameter_list|,
name|PreferenceGroup
name|pg
parameter_list|)
throws|throws
name|Exception
block|{
name|request
operator|.
name|setAttribute
argument_list|(
name|Building
operator|.
name|BLDG_LIST_ATTR_NAME
argument_list|,
name|pg
operator|.
name|getAvailableBuildings
argument_list|()
argument_list|)
expr_stmt|;
block|}
comment|/**      * Get Preference Levels and store it in request object      * @param request      * @throws Exception      */
specifier|public
specifier|static
name|void
name|setupPrefLevels
parameter_list|(
name|HttpServletRequest
name|request
parameter_list|)
throws|throws
name|Exception
block|{
name|request
operator|.
name|setAttribute
argument_list|(
name|PreferenceLevel
operator|.
name|PREF_LEVEL_ATTR_NAME
argument_list|,
name|PreferenceLevel
operator|.
name|getPreferenceLevelList
argument_list|()
argument_list|)
expr_stmt|;
block|}
comment|/**      * Get Preference Levels and store it in request object (soft preferences only)      * @param request      * @throws Exception      */
specifier|public
specifier|static
name|void
name|setupPrefLevelsSoftOnly
parameter_list|(
name|HttpServletRequest
name|request
parameter_list|)
throws|throws
name|Exception
block|{
name|request
operator|.
name|setAttribute
argument_list|(
name|PreferenceLevel
operator|.
name|PREF_LEVEL_ATTR_NAME
argument_list|,
name|PreferenceLevel
operator|.
name|getPreferenceLevelListSoftOnly
argument_list|()
argument_list|)
expr_stmt|;
block|}
comment|/**      * Get Room Features and store it in request object      * @param request      * @param preferenceGroup      * @throws Exception      */
specifier|public
specifier|static
name|void
name|setupRoomFeatures
parameter_list|(
name|HttpServletRequest
name|request
parameter_list|,
name|PreferenceGroup
name|pg
parameter_list|)
throws|throws
name|Exception
block|{
name|request
operator|.
name|setAttribute
argument_list|(
name|RoomFeature
operator|.
name|FEATURE_LIST_ATTR_NAME
argument_list|,
name|pg
operator|.
name|getAvailableRoomFeatures
argument_list|()
argument_list|)
expr_stmt|;
block|}
comment|/**      * Get Distribution Types and store it in request object      * @param request      * @throws Exception      */
specifier|public
specifier|static
name|void
name|setupDistribTypes
parameter_list|(
name|HttpServletRequest
name|request
parameter_list|,
name|SessionContext
name|context
parameter_list|)
throws|throws
name|Exception
block|{
name|request
operator|.
name|setAttribute
argument_list|(
name|DistributionType
operator|.
name|DIST_TYPE_ATTR_NAME
argument_list|,
name|DistributionType
operator|.
name|findApplicable
argument_list|(
name|context
argument_list|,
literal|false
argument_list|,
literal|false
argument_list|)
argument_list|)
expr_stmt|;
block|}
specifier|public
specifier|static
name|void
name|setupExamDistribTypes
parameter_list|(
name|HttpServletRequest
name|request
parameter_list|,
name|SessionContext
name|context
parameter_list|)
throws|throws
name|Exception
block|{
name|request
operator|.
name|setAttribute
argument_list|(
name|DistributionType
operator|.
name|DIST_TYPE_ATTR_NAME
argument_list|,
name|DistributionType
operator|.
name|findApplicable
argument_list|(
name|context
argument_list|,
literal|false
argument_list|,
literal|true
argument_list|)
argument_list|)
expr_stmt|;
block|}
specifier|public
specifier|static
name|void
name|setupInstructorDistribTypes
parameter_list|(
name|HttpServletRequest
name|request
parameter_list|,
name|SessionContext
name|context
parameter_list|)
throws|throws
name|Exception
block|{
name|request
operator|.
name|setAttribute
argument_list|(
name|DistributionType
operator|.
name|DIST_TYPE_ATTR_NAME
argument_list|,
name|DistributionType
operator|.
name|findApplicable
argument_list|(
name|context
argument_list|,
literal|true
argument_list|,
literal|false
argument_list|)
argument_list|)
expr_stmt|;
block|}
specifier|public
specifier|static
name|void
name|setupExaminationPeriods
parameter_list|(
name|HttpServletRequest
name|request
parameter_list|,
name|Long
name|sessionId
parameter_list|,
name|Long
name|examType
parameter_list|)
throws|throws
name|Exception
block|{
name|request
operator|.
name|setAttribute
argument_list|(
name|ExamPeriod
operator|.
name|PERIOD_ATTR_NAME
argument_list|,
name|ExamPeriod
operator|.
name|findAll
argument_list|(
name|sessionId
argument_list|,
name|examType
argument_list|)
argument_list|)
expr_stmt|;
block|}
specifier|public
specifier|static
name|void
name|setupRoomGroups
parameter_list|(
name|HttpServletRequest
name|request
parameter_list|,
name|PreferenceGroup
name|pg
parameter_list|)
throws|throws
name|Exception
block|{
name|request
operator|.
name|setAttribute
argument_list|(
name|RoomGroup
operator|.
name|GROUP_LIST_ATTR_NAME
argument_list|,
name|pg
operator|.
name|getAvailableRoomGroups
argument_list|()
argument_list|)
expr_stmt|;
block|}
specifier|public
specifier|static
name|void
name|setupDatePatterns
parameter_list|(
name|HttpServletRequest
name|request
parameter_list|,
name|UserContext
name|user
parameter_list|,
name|String
name|inheritString
parameter_list|,
name|DatePattern
name|inheritedDatePattern
parameter_list|,
name|Department
name|department
parameter_list|,
name|DatePattern
name|currentDatePattern
parameter_list|)
block|{
name|Vector
name|list
init|=
operator|new
name|Vector
argument_list|()
decl_stmt|;
name|list
operator|.
name|addElement
argument_list|(
operator|new
name|IdValue
argument_list|(
operator|new
name|Long
argument_list|(
operator|-
literal|1
argument_list|)
argument_list|,
name|inheritString
operator|+
operator|(
name|inheritedDatePattern
operator|==
literal|null
condition|?
literal|""
else|:
literal|" ("
operator|+
name|inheritedDatePattern
operator|.
name|getName
argument_list|()
operator|+
literal|")"
operator|)
argument_list|)
argument_list|)
expr_stmt|;
try|try
block|{
for|for
control|(
name|DatePattern
name|dp
range|:
name|DatePattern
operator|.
name|findAll
argument_list|(
name|user
argument_list|,
name|department
argument_list|,
name|currentDatePattern
argument_list|)
control|)
name|list
operator|.
name|addElement
argument_list|(
operator|new
name|IdValue
argument_list|(
name|dp
operator|.
name|getUniqueId
argument_list|()
argument_list|,
name|dp
operator|.
name|getName
argument_list|()
argument_list|)
argument_list|)
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
block|}
name|request
operator|.
name|setAttribute
argument_list|(
name|DatePattern
operator|.
name|DATE_PATTERN_LIST_ATTR
argument_list|,
name|list
argument_list|)
expr_stmt|;
block|}
comment|/**      * Get date patterns for a particular session      * @param request      * @param inheritString      * @param inheritedDatePattern      * @param department      * @param currentDatePattern      */
specifier|public
specifier|static
name|void
name|setupDatePatterns
parameter_list|(
name|HttpServletRequest
name|request
parameter_list|,
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|Session
name|acadSession
parameter_list|,
name|boolean
name|includeExtended
parameter_list|,
name|String
name|inheritString
parameter_list|,
name|DatePattern
name|inheritedDatePattern
parameter_list|,
name|Department
name|department
parameter_list|,
name|DatePattern
name|currentDatePattern
parameter_list|)
block|{
name|Vector
name|list
init|=
operator|new
name|Vector
argument_list|()
decl_stmt|;
name|list
operator|.
name|addElement
argument_list|(
operator|new
name|IdValue
argument_list|(
operator|new
name|Long
argument_list|(
operator|-
literal|1
argument_list|)
argument_list|,
name|inheritString
operator|+
operator|(
name|inheritedDatePattern
operator|==
literal|null
condition|?
literal|""
else|:
literal|" ("
operator|+
name|inheritedDatePattern
operator|.
name|getName
argument_list|()
operator|+
literal|")"
operator|)
argument_list|)
argument_list|)
expr_stmt|;
try|try
block|{
for|for
control|(
name|DatePattern
name|dp
range|:
name|DatePattern
operator|.
name|findAll
argument_list|(
name|acadSession
argument_list|,
name|includeExtended
argument_list|,
name|department
argument_list|,
name|currentDatePattern
argument_list|)
control|)
name|list
operator|.
name|addElement
argument_list|(
operator|new
name|IdValue
argument_list|(
name|dp
operator|.
name|getUniqueId
argument_list|()
argument_list|,
name|dp
operator|.
name|getName
argument_list|()
argument_list|)
argument_list|)
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
block|}
if|if
condition|(
name|inheritedDatePattern
operator|==
literal|null
operator|&&
name|list
operator|.
name|size
argument_list|()
operator|==
literal|1
condition|)
name|request
operator|.
name|setAttribute
argument_list|(
name|DatePattern
operator|.
name|DATE_PATTERN_LIST_ATTR
argument_list|,
literal|null
argument_list|)
expr_stmt|;
else|else
name|request
operator|.
name|setAttribute
argument_list|(
name|DatePattern
operator|.
name|DATE_PATTERN_LIST_ATTR
argument_list|,
name|list
argument_list|)
expr_stmt|;
block|}
comment|/**      * Get Instructors and store it in request object      * @param request      * @param deptUid department id, (null/blank if ALL instructors to be retrieved)      * @throws Exception      */
specifier|public
specifier|static
name|void
name|setupInstructors
parameter_list|(
name|HttpServletRequest
name|request
parameter_list|,
name|SessionContext
name|context
parameter_list|,
name|Long
name|deptUid
parameter_list|)
throws|throws
name|Exception
block|{
name|StringBuffer
name|query
init|=
operator|new
name|StringBuffer
argument_list|(
literal|""
argument_list|)
decl_stmt|;
if|if
condition|(
name|deptUid
operator|!=
literal|null
condition|)
name|query
operator|.
name|append
argument_list|(
literal|" and i.department.uniqueId = "
operator|+
name|deptUid
argument_list|)
expr_stmt|;
name|getInstructors
argument_list|(
name|request
argument_list|,
name|context
argument_list|,
name|query
argument_list|)
expr_stmt|;
block|}
comment|/**      * Get Instructors and store it in request object      * @param request      * @param deptUids department ids, (null if ALL instructors to be retrieved)      * @throws Exception      */
specifier|public
specifier|static
name|void
name|setupInstructors
parameter_list|(
name|HttpServletRequest
name|request
parameter_list|,
name|SessionContext
name|context
parameter_list|,
name|Long
index|[]
name|deptUids
parameter_list|)
throws|throws
name|Exception
block|{
name|StringBuffer
name|query
init|=
operator|new
name|StringBuffer
argument_list|(
literal|""
argument_list|)
decl_stmt|;
if|if
condition|(
name|deptUids
operator|!=
literal|null
operator|&&
name|deptUids
operator|.
name|length
operator|>
literal|0
condition|)
block|{
name|query
operator|.
name|append
argument_list|(
literal|" and i.department.uniqueId in ( "
operator|+
name|Constants
operator|.
name|arrayToStr
argument_list|(
name|deptUids
argument_list|,
literal|""
argument_list|,
literal|", "
argument_list|)
operator|+
literal|" )"
argument_list|)
expr_stmt|;
block|}
name|getInstructors
argument_list|(
name|request
argument_list|,
name|context
argument_list|,
name|query
argument_list|)
expr_stmt|;
block|}
comment|/**      * Executes the query to retrieve instructors      * @param request      * @param clause      * @throws Exception      */
specifier|private
specifier|static
name|void
name|getInstructors
parameter_list|(
name|HttpServletRequest
name|request
parameter_list|,
name|SessionContext
name|context
parameter_list|,
name|StringBuffer
name|clause
parameter_list|)
throws|throws
name|Exception
block|{
name|String
name|instructorNameFormat
init|=
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
decl_stmt|;
name|Long
name|acadSessionId
init|=
name|context
operator|.
name|getUser
argument_list|()
operator|.
name|getCurrentAcademicSessionId
argument_list|()
decl_stmt|;
name|StringBuffer
name|query
init|=
operator|new
name|StringBuffer
argument_list|()
decl_stmt|;
name|query
operator|.
name|append
argument_list|(
literal|"select distinct i from DepartmentalInstructor i "
argument_list|)
expr_stmt|;
name|query
operator|.
name|append
argument_list|(
literal|" where i.department.session.uniqueId = :acadSessionId "
argument_list|)
expr_stmt|;
name|query
operator|.
name|append
argument_list|(
name|clause
argument_list|)
expr_stmt|;
name|query
operator|.
name|append
argument_list|(
literal|" order by upper(i.lastName), upper(i.firstName) "
argument_list|)
expr_stmt|;
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
name|Query
name|q
init|=
name|hibSession
operator|.
name|createQuery
argument_list|(
name|query
operator|.
name|toString
argument_list|()
argument_list|)
decl_stmt|;
name|q
operator|.
name|setFetchSize
argument_list|(
literal|5000
argument_list|)
expr_stmt|;
name|q
operator|.
name|setCacheable
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|q
operator|.
name|setLong
argument_list|(
literal|"acadSessionId"
argument_list|,
name|acadSessionId
argument_list|)
expr_stmt|;
name|List
name|result
init|=
name|q
operator|.
name|list
argument_list|()
decl_stmt|;
name|Vector
name|v
init|=
operator|new
name|Vector
argument_list|(
name|result
operator|.
name|size
argument_list|()
argument_list|)
decl_stmt|;
name|Vector
name|h
init|=
operator|new
name|Vector
argument_list|(
name|result
operator|.
name|size
argument_list|()
argument_list|)
decl_stmt|;
for|for
control|(
name|Iterator
name|i
init|=
name|result
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
name|DepartmentalInstructor
name|di
init|=
operator|(
name|DepartmentalInstructor
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|String
name|name
init|=
name|di
operator|.
name|getName
argument_list|(
name|instructorNameFormat
argument_list|)
decl_stmt|;
name|v
operator|.
name|addElement
argument_list|(
operator|new
name|ComboBoxLookup
argument_list|(
name|name
argument_list|,
name|di
operator|.
name|getUniqueId
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|di
operator|.
name|hasPreferences
argument_list|()
condition|)
name|h
operator|.
name|add
argument_list|(
name|di
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|request
operator|.
name|setAttribute
argument_list|(
name|DepartmentalInstructor
operator|.
name|INSTR_LIST_ATTR_NAME
argument_list|,
name|v
argument_list|)
expr_stmt|;
name|request
operator|.
name|setAttribute
argument_list|(
name|DepartmentalInstructor
operator|.
name|INSTR_HAS_PREF_ATTR_NAME
argument_list|,
name|h
argument_list|)
expr_stmt|;
block|}
comment|/**      * Read the roles from lookup table and store it in an array list      * to be used to generate a drop down list of roles      * @param request HttpServletRequest object      * @throws Exception      */
specifier|public
specifier|static
name|void
name|setupRoles
parameter_list|(
name|HttpServletRequest
name|request
parameter_list|)
throws|throws
name|Exception
block|{
name|request
operator|.
name|setAttribute
argument_list|(
name|Roles
operator|.
name|ROLES_ATTR_NAME
argument_list|,
name|Roles
operator|.
name|findAll
argument_list|(
literal|true
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|/**      * Gets course offereings based on parameters      * @param request      * @param onlyOffered true indicates only retrieve offered courses       * @param onlyControlling true indicates retrieve only controlling courses      * @throws Exception      */
specifier|public
specifier|static
name|void
name|setupCourseOfferings
parameter_list|(
name|HttpServletRequest
name|request
parameter_list|,
name|SessionContext
name|context
parameter_list|,
name|CourseFilter
name|filter
parameter_list|)
throws|throws
name|Exception
block|{
name|List
argument_list|<
name|CourseOffering
argument_list|>
name|list
init|=
operator|new
name|ArrayList
argument_list|<
name|CourseOffering
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|SubjectArea
name|subject
range|:
name|SubjectArea
operator|.
name|getUserSubjectAreas
argument_list|(
name|context
operator|.
name|getUser
argument_list|()
argument_list|)
control|)
block|{
for|for
control|(
name|CourseOffering
name|co
range|:
name|subject
operator|.
name|getCourseOfferings
argument_list|()
control|)
block|{
if|if
condition|(
name|filter
operator|==
literal|null
operator|||
name|filter
operator|.
name|accept
argument_list|(
name|co
argument_list|)
condition|)
name|list
operator|.
name|add
argument_list|(
name|co
argument_list|)
expr_stmt|;
block|}
block|}
name|Collections
operator|.
name|sort
argument_list|(
name|list
argument_list|,
operator|new
name|CourseOfferingComparator
argument_list|()
argument_list|)
expr_stmt|;
name|request
operator|.
name|setAttribute
argument_list|(
name|CourseOffering
operator|.
name|CRS_OFFERING_LIST_ATTR_NAME
argument_list|,
name|list
argument_list|)
expr_stmt|;
block|}
specifier|public
specifier|static
interface|interface
name|CourseFilter
block|{
specifier|public
name|boolean
name|accept
parameter_list|(
name|CourseOffering
name|course
parameter_list|)
function_decl|;
block|}
specifier|public
specifier|static
name|void
name|setupCourseCreditFormats
parameter_list|(
name|HttpServletRequest
name|request
parameter_list|)
throws|throws
name|Exception
block|{
name|request
operator|.
name|setAttribute
argument_list|(
name|CourseCreditFormat
operator|.
name|COURSE_CREDIT_FORMAT_ATTR_NAME
argument_list|,
name|CourseCreditFormat
operator|.
name|getCourseCreditFormatList
argument_list|()
argument_list|)
expr_stmt|;
block|}
specifier|public
specifier|static
name|void
name|setupCourseCreditTypes
parameter_list|(
name|HttpServletRequest
name|request
parameter_list|)
throws|throws
name|Exception
block|{
name|request
operator|.
name|setAttribute
argument_list|(
name|CourseCreditType
operator|.
name|COURSE_CREDIT_TYPE_ATTR_NAME
argument_list|,
name|CourseCreditType
operator|.
name|getCourseCreditTypeList
argument_list|()
argument_list|)
expr_stmt|;
block|}
specifier|public
specifier|static
name|void
name|setupCourseCreditUnitTypes
parameter_list|(
name|HttpServletRequest
name|request
parameter_list|)
throws|throws
name|Exception
block|{
name|request
operator|.
name|setAttribute
argument_list|(
name|CourseCreditUnitType
operator|.
name|COURSE_CREDIT_UNIT_TYPE_ATTR_NAME
argument_list|,
name|CourseCreditUnitType
operator|.
name|getCourseCreditUnitTypeList
argument_list|()
argument_list|)
expr_stmt|;
block|}
comment|/**      * Retrieves list of position types       * @param request      * @throws Exception      */
specifier|public
specifier|static
name|void
name|setupPositionTypes
parameter_list|(
name|HttpServletRequest
name|request
parameter_list|)
throws|throws
name|Exception
block|{
name|request
operator|.
name|setAttribute
argument_list|(
name|PositionType
operator|.
name|POSTYPE_ATTR_NAME
argument_list|,
name|PositionType
operator|.
name|getPositionTypeList
argument_list|()
argument_list|)
expr_stmt|;
block|}
comment|/**      * Retrieves list of consent types      * @param request      */
specifier|public
specifier|static
name|void
name|setupConsentType
parameter_list|(
name|HttpServletRequest
name|request
parameter_list|)
block|{
name|request
operator|.
name|setAttribute
argument_list|(
name|OfferingConsentType
operator|.
name|CONSENT_TYPE_ATTR_NAME
argument_list|,
name|OfferingConsentType
operator|.
name|getConsentTypeList
argument_list|()
argument_list|)
expr_stmt|;
block|}
comment|/**      * Retrieves list of timetable managers      * @param request      */
specifier|public
specifier|static
name|void
name|setupTimetableManagers
parameter_list|(
name|HttpServletRequest
name|request
parameter_list|)
block|{
name|Vector
name|v
init|=
operator|new
name|Vector
argument_list|(
name|TimetableManager
operator|.
name|getManagerList
argument_list|()
argument_list|)
decl_stmt|;
name|request
operator|.
name|setAttribute
argument_list|(
name|TimetableManager
operator|.
name|MGR_LIST_ATTR_NAME
argument_list|,
name|v
argument_list|)
expr_stmt|;
block|}
specifier|public
specifier|static
name|void
name|setupExamTypes
parameter_list|(
name|HttpServletRequest
name|request
parameter_list|,
name|Long
name|sessionId
parameter_list|)
block|{
name|request
operator|.
name|setAttribute
argument_list|(
literal|"examTypes"
argument_list|,
name|sessionId
operator|==
literal|null
condition|?
name|ExamType
operator|.
name|findAll
argument_list|()
else|:
name|ExamType
operator|.
name|findAllUsed
argument_list|(
name|sessionId
argument_list|)
argument_list|)
expr_stmt|;
block|}
specifier|public
specifier|static
name|void
name|setupExamTypes
parameter_list|(
name|HttpServletRequest
name|request
parameter_list|,
name|UserContext
name|user
parameter_list|,
name|DepartmentStatusType
operator|.
name|Status
modifier|...
name|status
parameter_list|)
block|{
name|request
operator|.
name|setAttribute
argument_list|(
literal|"examTypes"
argument_list|,
name|ExamType
operator|.
name|findAllUsedApplicable
argument_list|(
name|user
argument_list|,
name|status
argument_list|)
argument_list|)
expr_stmt|;
block|}
specifier|public
specifier|static
name|void
name|setupCourseTypes
parameter_list|(
name|HttpServletRequest
name|request
parameter_list|)
block|{
name|request
operator|.
name|setAttribute
argument_list|(
literal|"courseTypes"
argument_list|,
name|CourseTypeDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|findAll
argument_list|(
name|Order
operator|.
name|asc
argument_list|(
literal|"reference"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

