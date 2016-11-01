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
name|model
operator|.
name|base
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|Serializable
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
name|Set
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
name|ExternalDepartmentStatusType
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
name|RoomDept
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
name|TimePattern
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

begin_comment
comment|/**  * Do not change this class. It has been automatically generated using ant create-model.  * @see org.unitime.commons.ant.CreateBaseModelFromXml  */
end_comment

begin_class
specifier|public
specifier|abstract
class|class
name|BaseDepartment
extends|extends
name|PreferenceGroup
implements|implements
name|Serializable
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
name|iExternalUniqueId
decl_stmt|;
specifier|private
name|String
name|iDeptCode
decl_stmt|;
specifier|private
name|String
name|iAbbreviation
decl_stmt|;
specifier|private
name|String
name|iName
decl_stmt|;
specifier|private
name|Boolean
name|iAllowReqTime
decl_stmt|;
specifier|private
name|Boolean
name|iAllowReqRoom
decl_stmt|;
specifier|private
name|Boolean
name|iAllowReqDistribution
decl_stmt|;
specifier|private
name|Boolean
name|iAllowEvents
decl_stmt|;
specifier|private
name|Boolean
name|iAllowStudentScheduling
decl_stmt|;
specifier|private
name|Boolean
name|iInheritInstructorPreferences
decl_stmt|;
specifier|private
name|String
name|iRoomSharingColor
decl_stmt|;
specifier|private
name|Boolean
name|iExternalManager
decl_stmt|;
specifier|private
name|String
name|iExternalMgrLabel
decl_stmt|;
specifier|private
name|String
name|iExternalMgrAbbv
decl_stmt|;
specifier|private
name|Integer
name|iDistributionPrefPriority
decl_stmt|;
specifier|private
name|Session
name|iSession
decl_stmt|;
specifier|private
name|DepartmentStatusType
name|iStatusType
decl_stmt|;
specifier|private
name|SolverGroup
name|iSolverGroup
decl_stmt|;
specifier|private
name|Set
argument_list|<
name|SubjectArea
argument_list|>
name|iSubjectAreas
decl_stmt|;
specifier|private
name|Set
argument_list|<
name|RoomDept
argument_list|>
name|iRoomDepts
decl_stmt|;
specifier|private
name|Set
argument_list|<
name|DatePattern
argument_list|>
name|iDatePatterns
decl_stmt|;
specifier|private
name|Set
argument_list|<
name|TimePattern
argument_list|>
name|iTimePatterns
decl_stmt|;
specifier|private
name|Set
argument_list|<
name|ExternalDepartmentStatusType
argument_list|>
name|iExternalStatusTypes
decl_stmt|;
specifier|private
name|Set
argument_list|<
name|TimetableManager
argument_list|>
name|iTimetableManagers
decl_stmt|;
specifier|private
name|Set
argument_list|<
name|DepartmentalInstructor
argument_list|>
name|iInstructors
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_EXTERNAL_UID
init|=
literal|"externalUniqueId"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_DEPT_CODE
init|=
literal|"deptCode"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_ABBREVIATION
init|=
literal|"abbreviation"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_NAME
init|=
literal|"name"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_ALLOW_REQ_TIME
init|=
literal|"allowReqTime"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_ALLOW_REQ_ROOM
init|=
literal|"allowReqRoom"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_ALLOW_REQ_DIST
init|=
literal|"allowReqDistribution"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_ALLOW_EVENTS
init|=
literal|"allowEvents"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_ALLOW_STUDENT_SCHD
init|=
literal|"allowStudentScheduling"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_INSTRUCTOR_PREF
init|=
literal|"inheritInstructorPreferences"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_RS_COLOR
init|=
literal|"roomSharingColor"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_EXTERNAL_MANAGER
init|=
literal|"externalManager"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_EXTERNAL_MGR_LABEL
init|=
literal|"externalMgrLabel"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_EXTERNAL_MGR_ABBV
init|=
literal|"externalMgrAbbv"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_DIST_PRIORITY
init|=
literal|"distributionPrefPriority"
decl_stmt|;
specifier|public
name|BaseDepartment
parameter_list|()
block|{
name|initialize
argument_list|()
expr_stmt|;
block|}
specifier|public
name|BaseDepartment
parameter_list|(
name|Long
name|uniqueId
parameter_list|)
block|{
name|setUniqueId
argument_list|(
name|uniqueId
argument_list|)
expr_stmt|;
name|initialize
argument_list|()
expr_stmt|;
block|}
specifier|protected
name|void
name|initialize
parameter_list|()
block|{
block|}
specifier|public
name|String
name|getExternalUniqueId
parameter_list|()
block|{
return|return
name|iExternalUniqueId
return|;
block|}
specifier|public
name|void
name|setExternalUniqueId
parameter_list|(
name|String
name|externalUniqueId
parameter_list|)
block|{
name|iExternalUniqueId
operator|=
name|externalUniqueId
expr_stmt|;
block|}
specifier|public
name|String
name|getDeptCode
parameter_list|()
block|{
return|return
name|iDeptCode
return|;
block|}
specifier|public
name|void
name|setDeptCode
parameter_list|(
name|String
name|deptCode
parameter_list|)
block|{
name|iDeptCode
operator|=
name|deptCode
expr_stmt|;
block|}
specifier|public
name|String
name|getAbbreviation
parameter_list|()
block|{
return|return
name|iAbbreviation
return|;
block|}
specifier|public
name|void
name|setAbbreviation
parameter_list|(
name|String
name|abbreviation
parameter_list|)
block|{
name|iAbbreviation
operator|=
name|abbreviation
expr_stmt|;
block|}
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
name|iName
return|;
block|}
specifier|public
name|void
name|setName
parameter_list|(
name|String
name|name
parameter_list|)
block|{
name|iName
operator|=
name|name
expr_stmt|;
block|}
specifier|public
name|Boolean
name|isAllowReqTime
parameter_list|()
block|{
return|return
name|iAllowReqTime
return|;
block|}
specifier|public
name|Boolean
name|getAllowReqTime
parameter_list|()
block|{
return|return
name|iAllowReqTime
return|;
block|}
specifier|public
name|void
name|setAllowReqTime
parameter_list|(
name|Boolean
name|allowReqTime
parameter_list|)
block|{
name|iAllowReqTime
operator|=
name|allowReqTime
expr_stmt|;
block|}
specifier|public
name|Boolean
name|isAllowReqRoom
parameter_list|()
block|{
return|return
name|iAllowReqRoom
return|;
block|}
specifier|public
name|Boolean
name|getAllowReqRoom
parameter_list|()
block|{
return|return
name|iAllowReqRoom
return|;
block|}
specifier|public
name|void
name|setAllowReqRoom
parameter_list|(
name|Boolean
name|allowReqRoom
parameter_list|)
block|{
name|iAllowReqRoom
operator|=
name|allowReqRoom
expr_stmt|;
block|}
specifier|public
name|Boolean
name|isAllowReqDistribution
parameter_list|()
block|{
return|return
name|iAllowReqDistribution
return|;
block|}
specifier|public
name|Boolean
name|getAllowReqDistribution
parameter_list|()
block|{
return|return
name|iAllowReqDistribution
return|;
block|}
specifier|public
name|void
name|setAllowReqDistribution
parameter_list|(
name|Boolean
name|allowReqDistribution
parameter_list|)
block|{
name|iAllowReqDistribution
operator|=
name|allowReqDistribution
expr_stmt|;
block|}
specifier|public
name|Boolean
name|isAllowEvents
parameter_list|()
block|{
return|return
name|iAllowEvents
return|;
block|}
specifier|public
name|Boolean
name|getAllowEvents
parameter_list|()
block|{
return|return
name|iAllowEvents
return|;
block|}
specifier|public
name|void
name|setAllowEvents
parameter_list|(
name|Boolean
name|allowEvents
parameter_list|)
block|{
name|iAllowEvents
operator|=
name|allowEvents
expr_stmt|;
block|}
specifier|public
name|Boolean
name|isAllowStudentScheduling
parameter_list|()
block|{
return|return
name|iAllowStudentScheduling
return|;
block|}
specifier|public
name|Boolean
name|getAllowStudentScheduling
parameter_list|()
block|{
return|return
name|iAllowStudentScheduling
return|;
block|}
specifier|public
name|void
name|setAllowStudentScheduling
parameter_list|(
name|Boolean
name|allowStudentScheduling
parameter_list|)
block|{
name|iAllowStudentScheduling
operator|=
name|allowStudentScheduling
expr_stmt|;
block|}
specifier|public
name|Boolean
name|isInheritInstructorPreferences
parameter_list|()
block|{
return|return
name|iInheritInstructorPreferences
return|;
block|}
specifier|public
name|Boolean
name|getInheritInstructorPreferences
parameter_list|()
block|{
return|return
name|iInheritInstructorPreferences
return|;
block|}
specifier|public
name|void
name|setInheritInstructorPreferences
parameter_list|(
name|Boolean
name|inheritInstructorPreferences
parameter_list|)
block|{
name|iInheritInstructorPreferences
operator|=
name|inheritInstructorPreferences
expr_stmt|;
block|}
specifier|public
name|String
name|getRoomSharingColor
parameter_list|()
block|{
return|return
name|iRoomSharingColor
return|;
block|}
specifier|public
name|void
name|setRoomSharingColor
parameter_list|(
name|String
name|roomSharingColor
parameter_list|)
block|{
name|iRoomSharingColor
operator|=
name|roomSharingColor
expr_stmt|;
block|}
specifier|public
name|Boolean
name|isExternalManager
parameter_list|()
block|{
return|return
name|iExternalManager
return|;
block|}
specifier|public
name|Boolean
name|getExternalManager
parameter_list|()
block|{
return|return
name|iExternalManager
return|;
block|}
specifier|public
name|void
name|setExternalManager
parameter_list|(
name|Boolean
name|externalManager
parameter_list|)
block|{
name|iExternalManager
operator|=
name|externalManager
expr_stmt|;
block|}
specifier|public
name|String
name|getExternalMgrLabel
parameter_list|()
block|{
return|return
name|iExternalMgrLabel
return|;
block|}
specifier|public
name|void
name|setExternalMgrLabel
parameter_list|(
name|String
name|externalMgrLabel
parameter_list|)
block|{
name|iExternalMgrLabel
operator|=
name|externalMgrLabel
expr_stmt|;
block|}
specifier|public
name|String
name|getExternalMgrAbbv
parameter_list|()
block|{
return|return
name|iExternalMgrAbbv
return|;
block|}
specifier|public
name|void
name|setExternalMgrAbbv
parameter_list|(
name|String
name|externalMgrAbbv
parameter_list|)
block|{
name|iExternalMgrAbbv
operator|=
name|externalMgrAbbv
expr_stmt|;
block|}
specifier|public
name|Integer
name|getDistributionPrefPriority
parameter_list|()
block|{
return|return
name|iDistributionPrefPriority
return|;
block|}
specifier|public
name|void
name|setDistributionPrefPriority
parameter_list|(
name|Integer
name|distributionPrefPriority
parameter_list|)
block|{
name|iDistributionPrefPriority
operator|=
name|distributionPrefPriority
expr_stmt|;
block|}
specifier|public
name|Session
name|getSession
parameter_list|()
block|{
return|return
name|iSession
return|;
block|}
specifier|public
name|void
name|setSession
parameter_list|(
name|Session
name|session
parameter_list|)
block|{
name|iSession
operator|=
name|session
expr_stmt|;
block|}
specifier|public
name|DepartmentStatusType
name|getStatusType
parameter_list|()
block|{
return|return
name|iStatusType
return|;
block|}
specifier|public
name|void
name|setStatusType
parameter_list|(
name|DepartmentStatusType
name|statusType
parameter_list|)
block|{
name|iStatusType
operator|=
name|statusType
expr_stmt|;
block|}
specifier|public
name|SolverGroup
name|getSolverGroup
parameter_list|()
block|{
return|return
name|iSolverGroup
return|;
block|}
specifier|public
name|void
name|setSolverGroup
parameter_list|(
name|SolverGroup
name|solverGroup
parameter_list|)
block|{
name|iSolverGroup
operator|=
name|solverGroup
expr_stmt|;
block|}
specifier|public
name|Set
argument_list|<
name|SubjectArea
argument_list|>
name|getSubjectAreas
parameter_list|()
block|{
return|return
name|iSubjectAreas
return|;
block|}
specifier|public
name|void
name|setSubjectAreas
parameter_list|(
name|Set
argument_list|<
name|SubjectArea
argument_list|>
name|subjectAreas
parameter_list|)
block|{
name|iSubjectAreas
operator|=
name|subjectAreas
expr_stmt|;
block|}
specifier|public
name|void
name|addTosubjectAreas
parameter_list|(
name|SubjectArea
name|subjectArea
parameter_list|)
block|{
if|if
condition|(
name|iSubjectAreas
operator|==
literal|null
condition|)
name|iSubjectAreas
operator|=
operator|new
name|HashSet
argument_list|<
name|SubjectArea
argument_list|>
argument_list|()
expr_stmt|;
name|iSubjectAreas
operator|.
name|add
argument_list|(
name|subjectArea
argument_list|)
expr_stmt|;
block|}
specifier|public
name|Set
argument_list|<
name|RoomDept
argument_list|>
name|getRoomDepts
parameter_list|()
block|{
return|return
name|iRoomDepts
return|;
block|}
specifier|public
name|void
name|setRoomDepts
parameter_list|(
name|Set
argument_list|<
name|RoomDept
argument_list|>
name|roomDepts
parameter_list|)
block|{
name|iRoomDepts
operator|=
name|roomDepts
expr_stmt|;
block|}
specifier|public
name|void
name|addToroomDepts
parameter_list|(
name|RoomDept
name|roomDept
parameter_list|)
block|{
if|if
condition|(
name|iRoomDepts
operator|==
literal|null
condition|)
name|iRoomDepts
operator|=
operator|new
name|HashSet
argument_list|<
name|RoomDept
argument_list|>
argument_list|()
expr_stmt|;
name|iRoomDepts
operator|.
name|add
argument_list|(
name|roomDept
argument_list|)
expr_stmt|;
block|}
specifier|public
name|Set
argument_list|<
name|DatePattern
argument_list|>
name|getDatePatterns
parameter_list|()
block|{
return|return
name|iDatePatterns
return|;
block|}
specifier|public
name|void
name|setDatePatterns
parameter_list|(
name|Set
argument_list|<
name|DatePattern
argument_list|>
name|datePatterns
parameter_list|)
block|{
name|iDatePatterns
operator|=
name|datePatterns
expr_stmt|;
block|}
specifier|public
name|void
name|addTodatePatterns
parameter_list|(
name|DatePattern
name|datePattern
parameter_list|)
block|{
if|if
condition|(
name|iDatePatterns
operator|==
literal|null
condition|)
name|iDatePatterns
operator|=
operator|new
name|HashSet
argument_list|<
name|DatePattern
argument_list|>
argument_list|()
expr_stmt|;
name|iDatePatterns
operator|.
name|add
argument_list|(
name|datePattern
argument_list|)
expr_stmt|;
block|}
specifier|public
name|Set
argument_list|<
name|TimePattern
argument_list|>
name|getTimePatterns
parameter_list|()
block|{
return|return
name|iTimePatterns
return|;
block|}
specifier|public
name|void
name|setTimePatterns
parameter_list|(
name|Set
argument_list|<
name|TimePattern
argument_list|>
name|timePatterns
parameter_list|)
block|{
name|iTimePatterns
operator|=
name|timePatterns
expr_stmt|;
block|}
specifier|public
name|void
name|addTotimePatterns
parameter_list|(
name|TimePattern
name|timePattern
parameter_list|)
block|{
if|if
condition|(
name|iTimePatterns
operator|==
literal|null
condition|)
name|iTimePatterns
operator|=
operator|new
name|HashSet
argument_list|<
name|TimePattern
argument_list|>
argument_list|()
expr_stmt|;
name|iTimePatterns
operator|.
name|add
argument_list|(
name|timePattern
argument_list|)
expr_stmt|;
block|}
specifier|public
name|Set
argument_list|<
name|ExternalDepartmentStatusType
argument_list|>
name|getExternalStatusTypes
parameter_list|()
block|{
return|return
name|iExternalStatusTypes
return|;
block|}
specifier|public
name|void
name|setExternalStatusTypes
parameter_list|(
name|Set
argument_list|<
name|ExternalDepartmentStatusType
argument_list|>
name|externalStatusTypes
parameter_list|)
block|{
name|iExternalStatusTypes
operator|=
name|externalStatusTypes
expr_stmt|;
block|}
specifier|public
name|void
name|addToexternalStatusTypes
parameter_list|(
name|ExternalDepartmentStatusType
name|externalDepartmentStatusType
parameter_list|)
block|{
if|if
condition|(
name|iExternalStatusTypes
operator|==
literal|null
condition|)
name|iExternalStatusTypes
operator|=
operator|new
name|HashSet
argument_list|<
name|ExternalDepartmentStatusType
argument_list|>
argument_list|()
expr_stmt|;
name|iExternalStatusTypes
operator|.
name|add
argument_list|(
name|externalDepartmentStatusType
argument_list|)
expr_stmt|;
block|}
specifier|public
name|Set
argument_list|<
name|TimetableManager
argument_list|>
name|getTimetableManagers
parameter_list|()
block|{
return|return
name|iTimetableManagers
return|;
block|}
specifier|public
name|void
name|setTimetableManagers
parameter_list|(
name|Set
argument_list|<
name|TimetableManager
argument_list|>
name|timetableManagers
parameter_list|)
block|{
name|iTimetableManagers
operator|=
name|timetableManagers
expr_stmt|;
block|}
specifier|public
name|void
name|addTotimetableManagers
parameter_list|(
name|TimetableManager
name|timetableManager
parameter_list|)
block|{
if|if
condition|(
name|iTimetableManagers
operator|==
literal|null
condition|)
name|iTimetableManagers
operator|=
operator|new
name|HashSet
argument_list|<
name|TimetableManager
argument_list|>
argument_list|()
expr_stmt|;
name|iTimetableManagers
operator|.
name|add
argument_list|(
name|timetableManager
argument_list|)
expr_stmt|;
block|}
specifier|public
name|Set
argument_list|<
name|DepartmentalInstructor
argument_list|>
name|getInstructors
parameter_list|()
block|{
return|return
name|iInstructors
return|;
block|}
specifier|public
name|void
name|setInstructors
parameter_list|(
name|Set
argument_list|<
name|DepartmentalInstructor
argument_list|>
name|instructors
parameter_list|)
block|{
name|iInstructors
operator|=
name|instructors
expr_stmt|;
block|}
specifier|public
name|void
name|addToinstructors
parameter_list|(
name|DepartmentalInstructor
name|departmentalInstructor
parameter_list|)
block|{
if|if
condition|(
name|iInstructors
operator|==
literal|null
condition|)
name|iInstructors
operator|=
operator|new
name|HashSet
argument_list|<
name|DepartmentalInstructor
argument_list|>
argument_list|()
expr_stmt|;
name|iInstructors
operator|.
name|add
argument_list|(
name|departmentalInstructor
argument_list|)
expr_stmt|;
block|}
specifier|public
name|boolean
name|equals
parameter_list|(
name|Object
name|o
parameter_list|)
block|{
if|if
condition|(
name|o
operator|==
literal|null
operator|||
operator|!
operator|(
name|o
operator|instanceof
name|Department
operator|)
condition|)
return|return
literal|false
return|;
if|if
condition|(
name|getUniqueId
argument_list|()
operator|==
literal|null
operator|||
operator|(
operator|(
name|Department
operator|)
name|o
operator|)
operator|.
name|getUniqueId
argument_list|()
operator|==
literal|null
condition|)
return|return
literal|false
return|;
return|return
name|getUniqueId
argument_list|()
operator|.
name|equals
argument_list|(
operator|(
operator|(
name|Department
operator|)
name|o
operator|)
operator|.
name|getUniqueId
argument_list|()
argument_list|)
return|;
block|}
specifier|public
name|int
name|hashCode
parameter_list|()
block|{
if|if
condition|(
name|getUniqueId
argument_list|()
operator|==
literal|null
condition|)
return|return
name|super
operator|.
name|hashCode
argument_list|()
return|;
return|return
name|getUniqueId
argument_list|()
operator|.
name|hashCode
argument_list|()
return|;
block|}
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
literal|"Department["
operator|+
name|getUniqueId
argument_list|()
operator|+
literal|" "
operator|+
name|getName
argument_list|()
operator|+
literal|"]"
return|;
block|}
specifier|public
name|String
name|toDebugString
parameter_list|()
block|{
return|return
literal|"Department["
operator|+
literal|"\n	Abbreviation: "
operator|+
name|getAbbreviation
argument_list|()
operator|+
literal|"\n	AllowEvents: "
operator|+
name|getAllowEvents
argument_list|()
operator|+
literal|"\n	AllowReqDistribution: "
operator|+
name|getAllowReqDistribution
argument_list|()
operator|+
literal|"\n	AllowReqRoom: "
operator|+
name|getAllowReqRoom
argument_list|()
operator|+
literal|"\n	AllowReqTime: "
operator|+
name|getAllowReqTime
argument_list|()
operator|+
literal|"\n	AllowStudentScheduling: "
operator|+
name|getAllowStudentScheduling
argument_list|()
operator|+
literal|"\n	DeptCode: "
operator|+
name|getDeptCode
argument_list|()
operator|+
literal|"\n	DistributionPrefPriority: "
operator|+
name|getDistributionPrefPriority
argument_list|()
operator|+
literal|"\n	ExternalManager: "
operator|+
name|getExternalManager
argument_list|()
operator|+
literal|"\n	ExternalMgrAbbv: "
operator|+
name|getExternalMgrAbbv
argument_list|()
operator|+
literal|"\n	ExternalMgrLabel: "
operator|+
name|getExternalMgrLabel
argument_list|()
operator|+
literal|"\n	ExternalUniqueId: "
operator|+
name|getExternalUniqueId
argument_list|()
operator|+
literal|"\n	InheritInstructorPreferences: "
operator|+
name|getInheritInstructorPreferences
argument_list|()
operator|+
literal|"\n	Name: "
operator|+
name|getName
argument_list|()
operator|+
literal|"\n	RoomSharingColor: "
operator|+
name|getRoomSharingColor
argument_list|()
operator|+
literal|"\n	Session: "
operator|+
name|getSession
argument_list|()
operator|+
literal|"\n	SolverGroup: "
operator|+
name|getSolverGroup
argument_list|()
operator|+
literal|"\n	StatusType: "
operator|+
name|getStatusType
argument_list|()
operator|+
literal|"\n	UniqueId: "
operator|+
name|getUniqueId
argument_list|()
operator|+
literal|"]"
return|;
block|}
block|}
end_class

end_unit

