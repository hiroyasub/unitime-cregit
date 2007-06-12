begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.0 (University Course Timetabling& Student Sectioning Application)  * Copyright (C) 2007, UniTime.org, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 2 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program; if not, write to the Free Software Foundation, Inc.,  * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
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

begin_comment
comment|/**  * This is an object that contains data related to the DEPARTMENT table.  * Do not modify this class because it will be overwritten if the configuration file  * related to this class is modified.  *  * @hibernate.class  *  table="DEPARTMENT"  */
end_comment

begin_class
specifier|public
specifier|abstract
class|class
name|BaseDepartment
extends|extends
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|PreferenceGroup
implements|implements
name|Serializable
block|{
specifier|public
specifier|static
name|String
name|REF
init|=
literal|"Department"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_EXTERNAL_UNIQUE_ID
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
name|PROP_ROOM_SHARING_COLOR
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
name|PROP_DISTRIBUTION_PREF_PRIORITY
init|=
literal|"distributionPrefPriority"
decl_stmt|;
comment|// constructors
specifier|public
name|BaseDepartment
parameter_list|()
block|{
name|initialize
argument_list|()
expr_stmt|;
block|}
comment|/** 	 * Constructor for primary key 	 */
specifier|public
name|BaseDepartment
parameter_list|(
name|java
operator|.
name|lang
operator|.
name|Long
name|uniqueId
parameter_list|)
block|{
name|super
argument_list|(
name|uniqueId
argument_list|)
expr_stmt|;
block|}
specifier|private
name|int
name|hashCode
init|=
name|Integer
operator|.
name|MIN_VALUE
decl_stmt|;
comment|// fields
specifier|private
name|java
operator|.
name|lang
operator|.
name|String
name|externalUniqueId
decl_stmt|;
specifier|private
name|java
operator|.
name|lang
operator|.
name|String
name|deptCode
decl_stmt|;
specifier|private
name|java
operator|.
name|lang
operator|.
name|String
name|abbreviation
decl_stmt|;
specifier|private
name|java
operator|.
name|lang
operator|.
name|String
name|name
decl_stmt|;
specifier|private
name|java
operator|.
name|lang
operator|.
name|Boolean
name|allowReqTime
decl_stmt|;
specifier|private
name|java
operator|.
name|lang
operator|.
name|Boolean
name|allowReqRoom
decl_stmt|;
specifier|private
name|java
operator|.
name|lang
operator|.
name|String
name|roomSharingColor
decl_stmt|;
specifier|private
name|java
operator|.
name|lang
operator|.
name|Boolean
name|externalManager
decl_stmt|;
specifier|private
name|java
operator|.
name|lang
operator|.
name|String
name|externalMgrLabel
decl_stmt|;
specifier|private
name|java
operator|.
name|lang
operator|.
name|String
name|externalMgrAbbv
decl_stmt|;
specifier|private
name|java
operator|.
name|lang
operator|.
name|Integer
name|distributionPrefPriority
decl_stmt|;
comment|// many to one
specifier|private
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|Session
name|session
decl_stmt|;
specifier|private
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|DepartmentStatusType
name|statusType
decl_stmt|;
specifier|private
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|SolverGroup
name|solverGroup
decl_stmt|;
comment|// collections
specifier|private
name|java
operator|.
name|util
operator|.
name|Set
name|subjectAreas
decl_stmt|;
specifier|private
name|java
operator|.
name|util
operator|.
name|Set
name|roomDepts
decl_stmt|;
specifier|private
name|java
operator|.
name|util
operator|.
name|Set
name|datePatterns
decl_stmt|;
specifier|private
name|java
operator|.
name|util
operator|.
name|Set
name|timePatterns
decl_stmt|;
specifier|private
name|java
operator|.
name|util
operator|.
name|Set
name|timetableManagers
decl_stmt|;
specifier|private
name|java
operator|.
name|util
operator|.
name|Set
name|instructors
decl_stmt|;
comment|/** 	 * Return the value associated with the column: EXTERNAL_UID 	 */
specifier|public
name|java
operator|.
name|lang
operator|.
name|String
name|getExternalUniqueId
parameter_list|()
block|{
return|return
name|externalUniqueId
return|;
block|}
comment|/** 	 * Set the value related to the column: EXTERNAL_UID 	 * @param externalUniqueId the EXTERNAL_UID value 	 */
specifier|public
name|void
name|setExternalUniqueId
parameter_list|(
name|java
operator|.
name|lang
operator|.
name|String
name|externalUniqueId
parameter_list|)
block|{
name|this
operator|.
name|externalUniqueId
operator|=
name|externalUniqueId
expr_stmt|;
block|}
comment|/** 	 * Return the value associated with the column: DEPT_CODE 	 */
specifier|public
name|java
operator|.
name|lang
operator|.
name|String
name|getDeptCode
parameter_list|()
block|{
return|return
name|deptCode
return|;
block|}
comment|/** 	 * Set the value related to the column: DEPT_CODE 	 * @param deptCode the DEPT_CODE value 	 */
specifier|public
name|void
name|setDeptCode
parameter_list|(
name|java
operator|.
name|lang
operator|.
name|String
name|deptCode
parameter_list|)
block|{
name|this
operator|.
name|deptCode
operator|=
name|deptCode
expr_stmt|;
block|}
comment|/** 	 * Return the value associated with the column: ABBREVIATION 	 */
specifier|public
name|java
operator|.
name|lang
operator|.
name|String
name|getAbbreviation
parameter_list|()
block|{
return|return
name|abbreviation
return|;
block|}
comment|/** 	 * Set the value related to the column: ABBREVIATION 	 * @param abbreviation the ABBREVIATION value 	 */
specifier|public
name|void
name|setAbbreviation
parameter_list|(
name|java
operator|.
name|lang
operator|.
name|String
name|abbreviation
parameter_list|)
block|{
name|this
operator|.
name|abbreviation
operator|=
name|abbreviation
expr_stmt|;
block|}
comment|/** 	 * Return the value associated with the column: NAME 	 */
specifier|public
name|java
operator|.
name|lang
operator|.
name|String
name|getName
parameter_list|()
block|{
return|return
name|name
return|;
block|}
comment|/** 	 * Set the value related to the column: NAME 	 * @param name the NAME value 	 */
specifier|public
name|void
name|setName
parameter_list|(
name|java
operator|.
name|lang
operator|.
name|String
name|name
parameter_list|)
block|{
name|this
operator|.
name|name
operator|=
name|name
expr_stmt|;
block|}
comment|/** 	 * Return the value associated with the column: ALLOW_REQ_TIME 	 */
specifier|public
name|java
operator|.
name|lang
operator|.
name|Boolean
name|isAllowReqTime
parameter_list|()
block|{
return|return
name|allowReqTime
return|;
block|}
comment|/** 	 * Set the value related to the column: ALLOW_REQ_TIME 	 * @param allowReqTime the ALLOW_REQ_TIME value 	 */
specifier|public
name|void
name|setAllowReqTime
parameter_list|(
name|java
operator|.
name|lang
operator|.
name|Boolean
name|allowReqTime
parameter_list|)
block|{
name|this
operator|.
name|allowReqTime
operator|=
name|allowReqTime
expr_stmt|;
block|}
comment|/** 	 * Return the value associated with the column: ALLOW_REQ_ROOM 	 */
specifier|public
name|java
operator|.
name|lang
operator|.
name|Boolean
name|isAllowReqRoom
parameter_list|()
block|{
return|return
name|allowReqRoom
return|;
block|}
comment|/** 	 * Set the value related to the column: ALLOW_REQ_ROOM 	 * @param allowReqRoom the ALLOW_REQ_ROOM value 	 */
specifier|public
name|void
name|setAllowReqRoom
parameter_list|(
name|java
operator|.
name|lang
operator|.
name|Boolean
name|allowReqRoom
parameter_list|)
block|{
name|this
operator|.
name|allowReqRoom
operator|=
name|allowReqRoom
expr_stmt|;
block|}
comment|/** 	 * Return the value associated with the column: RS_COLOR 	 */
specifier|public
name|java
operator|.
name|lang
operator|.
name|String
name|getRoomSharingColor
parameter_list|()
block|{
return|return
name|roomSharingColor
return|;
block|}
comment|/** 	 * Set the value related to the column: RS_COLOR 	 * @param roomSharingColor the RS_COLOR value 	 */
specifier|public
name|void
name|setRoomSharingColor
parameter_list|(
name|java
operator|.
name|lang
operator|.
name|String
name|roomSharingColor
parameter_list|)
block|{
name|this
operator|.
name|roomSharingColor
operator|=
name|roomSharingColor
expr_stmt|;
block|}
comment|/** 	 * Return the value associated with the column: EXTERNAL_MANAGER 	 */
specifier|public
name|java
operator|.
name|lang
operator|.
name|Boolean
name|isExternalManager
parameter_list|()
block|{
return|return
name|externalManager
return|;
block|}
comment|/** 	 * Set the value related to the column: EXTERNAL_MANAGER 	 * @param externalManager the EXTERNAL_MANAGER value 	 */
specifier|public
name|void
name|setExternalManager
parameter_list|(
name|java
operator|.
name|lang
operator|.
name|Boolean
name|externalManager
parameter_list|)
block|{
name|this
operator|.
name|externalManager
operator|=
name|externalManager
expr_stmt|;
block|}
comment|/** 	 * Return the value associated with the column: EXTERNAL_MGR_LABEL 	 */
specifier|public
name|java
operator|.
name|lang
operator|.
name|String
name|getExternalMgrLabel
parameter_list|()
block|{
return|return
name|externalMgrLabel
return|;
block|}
comment|/** 	 * Set the value related to the column: EXTERNAL_MGR_LABEL 	 * @param externalMgrLabel the EXTERNAL_MGR_LABEL value 	 */
specifier|public
name|void
name|setExternalMgrLabel
parameter_list|(
name|java
operator|.
name|lang
operator|.
name|String
name|externalMgrLabel
parameter_list|)
block|{
name|this
operator|.
name|externalMgrLabel
operator|=
name|externalMgrLabel
expr_stmt|;
block|}
comment|/** 	 * Return the value associated with the column: EXTERNAL_MGR_ABBV 	 */
specifier|public
name|java
operator|.
name|lang
operator|.
name|String
name|getExternalMgrAbbv
parameter_list|()
block|{
return|return
name|externalMgrAbbv
return|;
block|}
comment|/** 	 * Set the value related to the column: EXTERNAL_MGR_ABBV 	 * @param externalMgrAbbv the EXTERNAL_MGR_ABBV value 	 */
specifier|public
name|void
name|setExternalMgrAbbv
parameter_list|(
name|java
operator|.
name|lang
operator|.
name|String
name|externalMgrAbbv
parameter_list|)
block|{
name|this
operator|.
name|externalMgrAbbv
operator|=
name|externalMgrAbbv
expr_stmt|;
block|}
comment|/** 	 * Return the value associated with the column: DIST_PRIORITY 	 */
specifier|public
name|java
operator|.
name|lang
operator|.
name|Integer
name|getDistributionPrefPriority
parameter_list|()
block|{
return|return
name|distributionPrefPriority
return|;
block|}
comment|/** 	 * Set the value related to the column: DIST_PRIORITY 	 * @param distributionPrefPriority the DIST_PRIORITY value 	 */
specifier|public
name|void
name|setDistributionPrefPriority
parameter_list|(
name|java
operator|.
name|lang
operator|.
name|Integer
name|distributionPrefPriority
parameter_list|)
block|{
name|this
operator|.
name|distributionPrefPriority
operator|=
name|distributionPrefPriority
expr_stmt|;
block|}
comment|/** 	 * Return the value associated with the column: SESSION_ID 	 */
specifier|public
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|Session
name|getSession
parameter_list|()
block|{
return|return
name|session
return|;
block|}
comment|/** 	 * Set the value related to the column: SESSION_ID 	 * @param session the SESSION_ID value 	 */
specifier|public
name|void
name|setSession
parameter_list|(
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|Session
name|session
parameter_list|)
block|{
name|this
operator|.
name|session
operator|=
name|session
expr_stmt|;
block|}
comment|/** 	 * Return the value associated with the column: STATUS_TYPE 	 */
specifier|public
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|DepartmentStatusType
name|getStatusType
parameter_list|()
block|{
return|return
name|statusType
return|;
block|}
comment|/** 	 * Set the value related to the column: STATUS_TYPE 	 * @param statusType the STATUS_TYPE value 	 */
specifier|public
name|void
name|setStatusType
parameter_list|(
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|DepartmentStatusType
name|statusType
parameter_list|)
block|{
name|this
operator|.
name|statusType
operator|=
name|statusType
expr_stmt|;
block|}
comment|/** 	 * Return the value associated with the column: SOLVER_GROUP_ID 	 */
specifier|public
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|SolverGroup
name|getSolverGroup
parameter_list|()
block|{
return|return
name|solverGroup
return|;
block|}
comment|/** 	 * Set the value related to the column: SOLVER_GROUP_ID 	 * @param solverGroup the SOLVER_GROUP_ID value 	 */
specifier|public
name|void
name|setSolverGroup
parameter_list|(
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|SolverGroup
name|solverGroup
parameter_list|)
block|{
name|this
operator|.
name|solverGroup
operator|=
name|solverGroup
expr_stmt|;
block|}
comment|/** 	 * Return the value associated with the column: subjectAreas 	 */
specifier|public
name|java
operator|.
name|util
operator|.
name|Set
name|getSubjectAreas
parameter_list|()
block|{
return|return
name|subjectAreas
return|;
block|}
comment|/** 	 * Set the value related to the column: subjectAreas 	 * @param subjectAreas the subjectAreas value 	 */
specifier|public
name|void
name|setSubjectAreas
parameter_list|(
name|java
operator|.
name|util
operator|.
name|Set
name|subjectAreas
parameter_list|)
block|{
name|this
operator|.
name|subjectAreas
operator|=
name|subjectAreas
expr_stmt|;
block|}
specifier|public
name|void
name|addTosubjectAreas
parameter_list|(
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|SubjectArea
name|subjectArea
parameter_list|)
block|{
if|if
condition|(
literal|null
operator|==
name|getSubjectAreas
argument_list|()
condition|)
name|setSubjectAreas
argument_list|(
operator|new
name|java
operator|.
name|util
operator|.
name|HashSet
argument_list|()
argument_list|)
expr_stmt|;
name|getSubjectAreas
argument_list|()
operator|.
name|add
argument_list|(
name|subjectArea
argument_list|)
expr_stmt|;
block|}
comment|/** 	 * Return the value associated with the column: roomDepts 	 */
specifier|public
name|java
operator|.
name|util
operator|.
name|Set
name|getRoomDepts
parameter_list|()
block|{
return|return
name|roomDepts
return|;
block|}
comment|/** 	 * Set the value related to the column: roomDepts 	 * @param roomDepts the roomDepts value 	 */
specifier|public
name|void
name|setRoomDepts
parameter_list|(
name|java
operator|.
name|util
operator|.
name|Set
name|roomDepts
parameter_list|)
block|{
name|this
operator|.
name|roomDepts
operator|=
name|roomDepts
expr_stmt|;
block|}
specifier|public
name|void
name|addToroomDepts
parameter_list|(
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|RoomDept
name|roomDept
parameter_list|)
block|{
if|if
condition|(
literal|null
operator|==
name|getRoomDepts
argument_list|()
condition|)
name|setRoomDepts
argument_list|(
operator|new
name|java
operator|.
name|util
operator|.
name|HashSet
argument_list|()
argument_list|)
expr_stmt|;
name|getRoomDepts
argument_list|()
operator|.
name|add
argument_list|(
name|roomDept
argument_list|)
expr_stmt|;
block|}
comment|/** 	 * Return the value associated with the column: datePatterns 	 */
specifier|public
name|java
operator|.
name|util
operator|.
name|Set
name|getDatePatterns
parameter_list|()
block|{
return|return
name|datePatterns
return|;
block|}
comment|/** 	 * Set the value related to the column: datePatterns 	 * @param datePatterns the datePatterns value 	 */
specifier|public
name|void
name|setDatePatterns
parameter_list|(
name|java
operator|.
name|util
operator|.
name|Set
name|datePatterns
parameter_list|)
block|{
name|this
operator|.
name|datePatterns
operator|=
name|datePatterns
expr_stmt|;
block|}
comment|/** 	 * Return the value associated with the column: timePatterns 	 */
specifier|public
name|java
operator|.
name|util
operator|.
name|Set
name|getTimePatterns
parameter_list|()
block|{
return|return
name|timePatterns
return|;
block|}
comment|/** 	 * Set the value related to the column: timePatterns 	 * @param timePatterns the timePatterns value 	 */
specifier|public
name|void
name|setTimePatterns
parameter_list|(
name|java
operator|.
name|util
operator|.
name|Set
name|timePatterns
parameter_list|)
block|{
name|this
operator|.
name|timePatterns
operator|=
name|timePatterns
expr_stmt|;
block|}
comment|/** 	 * Return the value associated with the column: timetableManagers 	 */
specifier|public
name|java
operator|.
name|util
operator|.
name|Set
name|getTimetableManagers
parameter_list|()
block|{
return|return
name|timetableManagers
return|;
block|}
comment|/** 	 * Set the value related to the column: timetableManagers 	 * @param timetableManagers the timetableManagers value 	 */
specifier|public
name|void
name|setTimetableManagers
parameter_list|(
name|java
operator|.
name|util
operator|.
name|Set
name|timetableManagers
parameter_list|)
block|{
name|this
operator|.
name|timetableManagers
operator|=
name|timetableManagers
expr_stmt|;
block|}
comment|/** 	 * Return the value associated with the column: instructors 	 */
specifier|public
name|java
operator|.
name|util
operator|.
name|Set
name|getInstructors
parameter_list|()
block|{
return|return
name|instructors
return|;
block|}
comment|/** 	 * Set the value related to the column: instructors 	 * @param instructors the instructors value 	 */
specifier|public
name|void
name|setInstructors
parameter_list|(
name|java
operator|.
name|util
operator|.
name|Set
name|instructors
parameter_list|)
block|{
name|this
operator|.
name|instructors
operator|=
name|instructors
expr_stmt|;
block|}
specifier|public
name|void
name|addToinstructors
parameter_list|(
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|DepartmentalInstructor
name|departmentalInstructor
parameter_list|)
block|{
if|if
condition|(
literal|null
operator|==
name|getInstructors
argument_list|()
condition|)
name|setInstructors
argument_list|(
operator|new
name|java
operator|.
name|util
operator|.
name|HashSet
argument_list|()
argument_list|)
expr_stmt|;
name|getInstructors
argument_list|()
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
name|obj
parameter_list|)
block|{
if|if
condition|(
literal|null
operator|==
name|obj
condition|)
return|return
literal|false
return|;
if|if
condition|(
operator|!
operator|(
name|obj
operator|instanceof
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|Department
operator|)
condition|)
return|return
literal|false
return|;
else|else
block|{
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|Department
name|department
init|=
operator|(
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|Department
operator|)
name|obj
decl_stmt|;
if|if
condition|(
literal|null
operator|==
name|this
operator|.
name|getUniqueId
argument_list|()
operator|||
literal|null
operator|==
name|department
operator|.
name|getUniqueId
argument_list|()
condition|)
return|return
literal|false
return|;
else|else
return|return
operator|(
name|this
operator|.
name|getUniqueId
argument_list|()
operator|.
name|equals
argument_list|(
name|department
operator|.
name|getUniqueId
argument_list|()
argument_list|)
operator|)
return|;
block|}
block|}
specifier|public
name|int
name|hashCode
parameter_list|()
block|{
if|if
condition|(
name|Integer
operator|.
name|MIN_VALUE
operator|==
name|this
operator|.
name|hashCode
condition|)
block|{
if|if
condition|(
literal|null
operator|==
name|this
operator|.
name|getUniqueId
argument_list|()
condition|)
return|return
name|super
operator|.
name|hashCode
argument_list|()
return|;
else|else
block|{
name|String
name|hashStr
init|=
name|this
operator|.
name|getClass
argument_list|()
operator|.
name|getName
argument_list|()
operator|+
literal|":"
operator|+
name|this
operator|.
name|getUniqueId
argument_list|()
operator|.
name|hashCode
argument_list|()
decl_stmt|;
name|this
operator|.
name|hashCode
operator|=
name|hashStr
operator|.
name|hashCode
argument_list|()
expr_stmt|;
block|}
block|}
return|return
name|this
operator|.
name|hashCode
return|;
block|}
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
name|super
operator|.
name|toString
argument_list|()
return|;
block|}
block|}
end_class

end_unit

