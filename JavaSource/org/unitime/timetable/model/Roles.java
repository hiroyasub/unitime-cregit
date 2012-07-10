begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.2 (University Timetabling Application)  * Copyright (C) 2008 - 2010, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
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
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Enumeration
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
name|Vector
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
name|timetable
operator|.
name|model
operator|.
name|base
operator|.
name|BaseRoles
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
name|RolesDAO
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
name|HasRights
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

begin_class
specifier|public
class|class
name|Roles
extends|extends
name|BaseRoles
implements|implements
name|HasRights
block|{
comment|/** 	 * 	 */
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|3256722879445154100L
decl_stmt|;
comment|/*[CONSTRUCTOR MARKER BEGIN]*/
specifier|public
name|Roles
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
block|}
comment|/** 	 * Constructor for primary key 	 */
specifier|public
name|Roles
parameter_list|(
name|java
operator|.
name|lang
operator|.
name|Long
name|roleId
parameter_list|)
block|{
name|super
argument_list|(
name|roleId
argument_list|)
expr_stmt|;
block|}
comment|/*[CONSTRUCTOR MARKER END]*/
specifier|public
specifier|static
name|String
name|ADMIN_ROLE
init|=
literal|"Administrator"
decl_stmt|;
specifier|public
specifier|static
name|String
name|DEPT_SCHED_MGR_ROLE
init|=
literal|"Dept Sched Mgr"
decl_stmt|;
specifier|public
specifier|static
name|String
name|VIEW_ALL_ROLE
init|=
literal|"View All"
decl_stmt|;
specifier|public
specifier|static
name|String
name|EXAM_MGR_ROLE
init|=
literal|"Exam Mgr"
decl_stmt|;
specifier|public
specifier|static
name|String
name|EVENT_MGR_ROLE
init|=
literal|"Event Mgr"
decl_stmt|;
specifier|public
specifier|static
name|String
name|CURRICULUM_MGR_ROLE
init|=
literal|"Curriculum Mgr"
decl_stmt|;
specifier|public
specifier|static
name|String
name|STUDENT_ADVISOR
init|=
literal|"Advisor"
decl_stmt|;
specifier|public
specifier|static
name|String
name|USER_ROLES_ATTR_NAME
init|=
literal|"userRoles"
decl_stmt|;
specifier|public
specifier|static
name|String
name|ROLES_ATTR_NAME
init|=
literal|"rolesList"
decl_stmt|;
comment|/** 	 * Define Admin and non - admin roles 	 */
specifier|private
specifier|static
name|String
index|[]
name|adminRoles
init|=
operator|new
name|String
index|[]
block|{
name|Roles
operator|.
name|ADMIN_ROLE
block|}
decl_stmt|;
specifier|private
specifier|static
name|String
index|[]
name|nonAdminRoles
init|=
operator|new
name|String
index|[]
block|{
name|Roles
operator|.
name|DEPT_SCHED_MGR_ROLE
block|,
name|Roles
operator|.
name|VIEW_ALL_ROLE
block|,
name|Roles
operator|.
name|EXAM_MGR_ROLE
block|,
name|Roles
operator|.
name|EVENT_MGR_ROLE
block|}
decl_stmt|;
comment|/**      * Retrieve admin roles      * @return String Array of admin roles (defined in Roles class)       * @see Roles      */
specifier|public
specifier|static
name|String
index|[]
name|getAdminRoles
parameter_list|()
block|{
return|return
name|adminRoles
return|;
block|}
comment|/**      * Retrieve non-admin roles      * @return String Array of admin roles (defined in Roles class)       * @see Roles      */
specifier|public
specifier|static
name|String
index|[]
name|getNonAdminRoles
parameter_list|()
block|{
return|return
name|nonAdminRoles
return|;
block|}
comment|/** Roles List **/
specifier|private
specifier|static
name|Vector
name|rolesList
init|=
literal|null
decl_stmt|;
comment|/** 	 * Retrieves all roles in the database 	 * ordered by column label 	 * @param refresh true - refreshes the list from database 	 * @return Vector of Roles objects 	 */
specifier|public
specifier|static
specifier|synchronized
name|Vector
name|getRolesList
parameter_list|(
name|boolean
name|refresh
parameter_list|)
block|{
if|if
condition|(
name|rolesList
operator|!=
literal|null
operator|&&
operator|!
name|refresh
condition|)
return|return
name|rolesList
return|;
name|RolesDAO
name|rdao
init|=
operator|new
name|RolesDAO
argument_list|()
decl_stmt|;
name|List
name|l
init|=
name|rdao
operator|.
name|findAll
argument_list|(
name|Order
operator|.
name|asc
argument_list|(
literal|"abbv"
argument_list|)
argument_list|)
decl_stmt|;
name|rolesList
operator|=
operator|new
name|Vector
argument_list|(
name|l
argument_list|)
expr_stmt|;
return|return
name|rolesList
return|;
block|}
comment|/**      * Get icon file name corresponding to role      * @param roleRef      * @return icon file name      */
specifier|public
specifier|static
name|String
name|getRoleIcon
parameter_list|(
name|String
name|roleRef
parameter_list|)
block|{
if|if
condition|(
name|roleRef
operator|.
name|equals
argument_list|(
name|Roles
operator|.
name|ADMIN_ROLE
argument_list|)
condition|)
return|return
literal|"admin-icon.gif"
return|;
if|if
condition|(
name|roleRef
operator|.
name|equals
argument_list|(
name|Roles
operator|.
name|DEPT_SCHED_MGR_ROLE
argument_list|)
condition|)
return|return
literal|"dept-mgr-icon.gif"
return|;
if|if
condition|(
name|roleRef
operator|.
name|equals
argument_list|(
name|Roles
operator|.
name|VIEW_ALL_ROLE
argument_list|)
condition|)
return|return
literal|"view-all-icon.gif"
return|;
return|return
literal|"other-role-icon.gif"
return|;
block|}
specifier|public
specifier|static
name|Roles
name|getRole
parameter_list|(
name|String
name|roleRef
parameter_list|)
block|{
for|for
control|(
name|Enumeration
name|e
init|=
name|getRolesList
argument_list|(
literal|false
argument_list|)
operator|.
name|elements
argument_list|()
init|;
name|e
operator|.
name|hasMoreElements
argument_list|()
condition|;
control|)
block|{
name|Roles
name|role
init|=
operator|(
name|Roles
operator|)
name|e
operator|.
name|nextElement
argument_list|()
decl_stmt|;
if|if
condition|(
name|roleRef
operator|.
name|equals
argument_list|(
name|role
operator|.
name|getReference
argument_list|()
argument_list|)
condition|)
return|return
name|role
return|;
block|}
return|return
literal|null
return|;
block|}
annotation|@
name|Override
comment|//TODO: get this information from the database
specifier|public
name|boolean
name|hasRight
parameter_list|(
name|Right
name|right
parameter_list|)
block|{
switch|switch
condition|(
name|right
condition|)
block|{
comment|/* session defaults */
case|case
name|SessionDefaultFirstFuture
case|:
return|return
name|DEPT_SCHED_MGR_ROLE
operator|.
name|equals
argument_list|(
name|getReference
argument_list|()
argument_list|)
return|;
case|case
name|SessionDefaultFirstExamination
case|:
return|return
name|EXAM_MGR_ROLE
operator|.
name|equals
argument_list|(
name|getReference
argument_list|()
argument_list|)
return|;
case|case
name|SessionDefaultCurrent
case|:
return|return
operator|!
name|DEPT_SCHED_MGR_ROLE
operator|.
name|equals
argument_list|(
name|getReference
argument_list|()
argument_list|)
operator|&&
operator|!
name|EXAM_MGR_ROLE
operator|.
name|equals
argument_list|(
name|getReference
argument_list|()
argument_list|)
return|;
comment|/* session / department / status dependency */
case|case
name|SessionIndependent
case|:
return|return
name|ADMIN_ROLE
operator|.
name|equals
argument_list|(
name|getReference
argument_list|()
argument_list|)
return|;
case|case
name|SessionIndependentIfNoSessionGiven
case|:
return|return
name|VIEW_ALL_ROLE
operator|.
name|equals
argument_list|(
name|getReference
argument_list|()
argument_list|)
return|;
case|case
name|AllowTestSessions
case|:
return|return
name|ADMIN_ROLE
operator|.
name|equals
argument_list|(
name|getReference
argument_list|()
argument_list|)
return|;
case|case
name|DepartmentIndependent
case|:
return|return
operator|!
name|DEPT_SCHED_MGR_ROLE
operator|.
name|equals
argument_list|(
name|getReference
argument_list|()
argument_list|)
operator|&&
operator|!
name|EVENT_MGR_ROLE
operator|.
name|equals
argument_list|(
name|getReference
argument_list|()
argument_list|)
return|;
case|case
name|StatusIndependent
case|:
return|return
name|ADMIN_ROLE
operator|.
name|equals
argument_list|(
name|getReference
argument_list|()
argument_list|)
return|;
case|case
name|AddNonUnivLocation
case|:
case|case
name|AddSpecialUseRoom
case|:
return|return
name|ADMIN_ROLE
operator|.
name|equals
argument_list|(
name|getReference
argument_list|()
argument_list|)
operator|||
name|DEPT_SCHED_MGR_ROLE
operator|.
name|equals
argument_list|(
name|getReference
argument_list|()
argument_list|)
operator|||
name|EVENT_MGR_ROLE
operator|.
name|endsWith
argument_list|(
name|getReference
argument_list|()
argument_list|)
return|;
case|case
name|ApplicationConfig
case|:
return|return
name|ADMIN_ROLE
operator|.
name|equals
argument_list|(
name|getReference
argument_list|()
argument_list|)
return|;
case|case
name|CourseTimetabling
case|:
case|case
name|AssignedClasses
case|:
case|case
name|AssignmentHistory
case|:
case|case
name|AddCourseOffering
case|:
case|case
name|ClassEdit
case|:
case|case
name|ClassEditClearPreferences
case|:
case|case
name|OfferingCanLock
case|:
case|case
name|OfferingCanUnlock
case|:
case|case
name|AssignInstructors
case|:
case|case
name|MultipleClassSetup
case|:
case|case
name|MultipleClassSetupClass
case|:
case|case
name|MultipleClassSetupDepartment
case|:
case|case
name|InstrOfferingConfigAdd
case|:
case|case
name|InstrOfferingConfigDelete
case|:
case|case
name|InstrOfferingConfigEdit
case|:
case|case
name|InstrOfferingConfigEditDepartment
case|:
case|case
name|InstrOfferingConfigEditSubpart
case|:
case|case
name|AddReservation
case|:
case|case
name|EditCourseOffering
case|:
case|case
name|InstructionalOfferingCrossLists
case|:
case|case
name|OfferingMakeNotOffered
case|:
case|case
name|OfferingMakeOffered
case|:
case|case
name|Reservations
case|:
case|case
name|SchedulingSubpartEdit
case|:
case|case
name|SchedulingSubpartEditClearPreferences
case|:
case|case
name|SchedulingSubpartDetailClearClassPreferences
case|:
case|case
name|InstructorPreferences
case|:
case|case
name|InstructorDelete
case|:
case|case
name|InstructorEdit
case|:
case|case
name|InstructorAdd
case|:
case|case
name|InstructorAddDesignator
case|:
case|case
name|ManageInstructors
case|:
case|case
name|InstructorEditClearPreferences
case|:
case|case
name|SolutionChanges
case|:
case|case
name|Suggestions
case|:
case|case
name|DistributionPreferenceClass
case|:
case|case
name|DistributionPreferenceSubpart
case|:
return|return
name|ADMIN_ROLE
operator|.
name|equals
argument_list|(
name|getReference
argument_list|()
argument_list|)
operator|||
name|DEPT_SCHED_MGR_ROLE
operator|.
name|equals
argument_list|(
name|getReference
argument_list|()
argument_list|)
return|;
case|case
name|AssignedExams
case|:
return|return
name|ADMIN_ROLE
operator|.
name|equals
argument_list|(
name|getReference
argument_list|()
argument_list|)
operator|||
name|EXAM_MGR_ROLE
operator|.
name|equals
argument_list|(
name|getReference
argument_list|()
argument_list|)
return|;
case|case
name|InstructionalOfferings
case|:
case|case
name|InstructionalOfferingsExportPDF
case|:
case|case
name|InstructionalOfferingsWorksheetPDF
case|:
case|case
name|InstructionalOfferingDetail
case|:
case|case
name|Classes
case|:
case|case
name|ClassesExportPDF
case|:
case|case
name|SchedulingSubpartDetail
case|:
case|case
name|ClassDetail
case|:
return|return
name|ADMIN_ROLE
operator|.
name|equals
argument_list|(
name|getReference
argument_list|()
argument_list|)
operator|||
name|DEPT_SCHED_MGR_ROLE
operator|.
name|equals
argument_list|(
name|getReference
argument_list|()
argument_list|)
operator|||
name|VIEW_ALL_ROLE
operator|.
name|equals
argument_list|(
name|getReference
argument_list|()
argument_list|)
return|;
case|case
name|ClassAssignments
case|:
case|case
name|ClassAssignmentsExportCSV
case|:
case|case
name|ClassAssignmentsExportPDF
case|:
case|case
name|Examinations
case|:
case|case
name|ExaminationDetail
case|:
case|case
name|InstructorDetail
case|:
case|case
name|Instructors
case|:
case|case
name|InstructorsExportPdf
case|:
return|return
name|ADMIN_ROLE
operator|.
name|equals
argument_list|(
name|getReference
argument_list|()
argument_list|)
operator|||
name|EXAM_MGR_ROLE
operator|.
name|equals
argument_list|(
name|getReference
argument_list|()
argument_list|)
operator|||
name|DEPT_SCHED_MGR_ROLE
operator|.
name|equals
argument_list|(
name|getReference
argument_list|()
argument_list|)
operator|||
name|VIEW_ALL_ROLE
operator|.
name|equals
argument_list|(
name|getReference
argument_list|()
argument_list|)
return|;
case|case
name|ExaminationAdd
case|:
case|case
name|ExaminationEdit
case|:
case|case
name|ExaminationEditClearPreferences
case|:
case|case
name|ExaminationDelete
case|:
case|case
name|ExaminationClone
case|:
case|case
name|DistributionPreferenceExam
case|:
return|return
name|ADMIN_ROLE
operator|.
name|equals
argument_list|(
name|getReference
argument_list|()
argument_list|)
operator|||
name|EXAM_MGR_ROLE
operator|.
name|equals
argument_list|(
name|getReference
argument_list|()
argument_list|)
operator|||
name|DEPT_SCHED_MGR_ROLE
operator|.
name|equals
argument_list|(
name|getReference
argument_list|()
argument_list|)
return|;
case|case
name|ExaminationAssignment
case|:
return|return
name|ADMIN_ROLE
operator|.
name|equals
argument_list|(
name|getReference
argument_list|()
argument_list|)
operator|||
name|EXAM_MGR_ROLE
operator|.
name|equals
argument_list|(
name|getReference
argument_list|()
argument_list|)
return|;
case|case
name|OfferingDelete
case|:
case|case
name|ClassAssignment
case|:
case|case
name|Registration
case|:
return|return
name|ADMIN_ROLE
operator|.
name|equals
argument_list|(
name|getReference
argument_list|()
argument_list|)
return|;
comment|/* curriculum rights */
case|case
name|CurriculumAdd
case|:
case|case
name|CurriculumEdit
case|:
case|case
name|CurriculumDelete
case|:
return|return
name|ADMIN_ROLE
operator|.
name|equals
argument_list|(
name|getReference
argument_list|()
argument_list|)
operator|||
name|CURRICULUM_MGR_ROLE
operator|.
name|equals
argument_list|(
name|getReference
argument_list|()
argument_list|)
operator|||
name|DEPT_SCHED_MGR_ROLE
operator|.
name|equals
argument_list|(
name|getReference
argument_list|()
argument_list|)
return|;
case|case
name|CurriculumDetail
case|:
case|case
name|CurriculumView
case|:
return|return
name|ADMIN_ROLE
operator|.
name|equals
argument_list|(
name|getReference
argument_list|()
argument_list|)
operator|||
name|CURRICULUM_MGR_ROLE
operator|.
name|equals
argument_list|(
name|getReference
argument_list|()
argument_list|)
operator|||
name|DEPT_SCHED_MGR_ROLE
operator|.
name|equals
argument_list|(
name|getReference
argument_list|()
argument_list|)
operator|||
name|VIEW_ALL_ROLE
operator|.
name|equals
argument_list|(
name|getReference
argument_list|()
argument_list|)
return|;
case|case
name|CurriculumMerge
case|:
return|return
name|ADMIN_ROLE
operator|.
name|equals
argument_list|(
name|getReference
argument_list|()
argument_list|)
operator|||
name|CURRICULUM_MGR_ROLE
operator|.
name|equals
argument_list|(
name|getReference
argument_list|()
argument_list|)
return|;
case|case
name|CurriculumAdmin
case|:
return|return
name|ADMIN_ROLE
operator|.
name|equals
argument_list|(
name|getReference
argument_list|()
argument_list|)
return|;
case|case
name|ExaminationSchedule
case|:
case|case
name|CanUseHardDistributionPrefs
case|:
case|case
name|CanUseHardPeriodPrefs
case|:
case|case
name|CanUseHardRoomPrefs
case|:
case|case
name|CanUseHardTimePrefs
case|:
return|return
literal|true
return|;
default|default:
return|return
literal|false
return|;
block|}
block|}
block|}
end_class

end_unit

