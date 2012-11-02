begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.4 (University Timetabling Application)  * Copyright (C) 2012, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
end_comment

begin_package
package|package
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|security
operator|.
name|rights
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Date
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
name|Class_
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
name|Curriculum
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
name|DepartmentRoomFeature
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
name|DistributionPref
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
name|Event
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
name|Exam
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
name|ExternalRoom
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
name|GlobalRoomFeature
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
name|InstrOfferingConfig
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
name|Location
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
name|Meeting
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
name|NonUniversityLocation
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
name|Reservation
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
name|SavedHQL
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
name|Solution
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

begin_enum
specifier|public
enum|enum
name|Right
block|{
comment|/** Session default: current session */
name|SessionDefaultCurrent
block|,
comment|// -- DEFAULT SESSION SELECTION
comment|/** Session default: first future session */
name|SessionDefaultFirstFuture
block|,
comment|/** Session default: first examination session */
name|SessionDefaultFirstExamination
block|,
comment|/** Session dependency -- if independent the role applies to all academic session */
name|SessionIndependent
block|,
name|SessionIndependentIfNoSessionGiven
block|,
comment|/** Session dependency -- test sessions are allowed */
name|AllowTestSessions
block|,
comment|/** Department dependency -- department must match */
name|DepartmentIndependent
block|,
comment|/** Status dependency -- session / department status must match */
name|StatusIndependent
block|,
comment|/** For some old (backward compatible) checks */
name|HasRole
block|,
name|IsAdmin
block|,
comment|/** Can register UniTime */
name|Registration
block|,
comment|/** Individual page rights: Courses Input Data */
name|InstructionalOfferings
argument_list|(
name|Department
operator|.
name|class
argument_list|)
block|,
name|InstructionalOfferingsExportPDF
argument_list|(
name|Department
operator|.
name|class
argument_list|)
block|,
name|InstructionalOfferingsWorksheetPDF
argument_list|(
name|Department
operator|.
name|class
argument_list|)
block|,
name|Classes
argument_list|(
name|Department
operator|.
name|class
argument_list|)
block|,
name|ClassesExportPDF
argument_list|(
name|Department
operator|.
name|class
argument_list|)
block|,
name|ClassAssignments
argument_list|(
name|Session
operator|.
name|class
argument_list|)
block|,
name|ClassAssignmentsExportPdf
argument_list|(
name|Session
operator|.
name|class
argument_list|)
block|,
name|ClassAssignmentsExportCsv
argument_list|(
name|Session
operator|.
name|class
argument_list|)
block|,
name|InstructionalOfferingDetail
argument_list|(
name|InstructionalOffering
operator|.
name|class
argument_list|)
block|,
name|AddCourseOffering
argument_list|(
name|SubjectArea
operator|.
name|class
argument_list|)
block|,
name|EditCourseOffering
argument_list|(
name|CourseOffering
operator|.
name|class
argument_list|)
block|,
name|OfferingCanLock
argument_list|(
name|InstructionalOffering
operator|.
name|class
argument_list|)
block|,
name|OfferingCanUnlock
argument_list|(
name|InstructionalOffering
operator|.
name|class
argument_list|)
block|,
name|OfferingMakeNotOffered
argument_list|(
name|InstructionalOffering
operator|.
name|class
argument_list|)
block|,
name|OfferingMakeOffered
argument_list|(
name|InstructionalOffering
operator|.
name|class
argument_list|)
block|,
name|OfferingDelete
argument_list|(
name|InstructionalOffering
operator|.
name|class
argument_list|)
block|,
name|InstructionalOfferingCrossLists
argument_list|(
name|InstructionalOffering
operator|.
name|class
argument_list|)
block|,
name|InstrOfferingConfigAdd
argument_list|(
name|InstructionalOffering
operator|.
name|class
argument_list|)
block|,
name|InstrOfferingConfigEdit
argument_list|(
name|InstrOfferingConfig
operator|.
name|class
argument_list|)
block|,
name|InstrOfferingConfigEditDepartment
argument_list|(
name|Department
operator|.
name|class
argument_list|)
block|,
name|InstrOfferingConfigEditSubpart
argument_list|(
name|SchedulingSubpart
operator|.
name|class
argument_list|)
block|,
name|InstrOfferingConfigDelete
argument_list|(
name|InstrOfferingConfig
operator|.
name|class
argument_list|)
block|,
name|MultipleClassSetup
argument_list|(
name|InstrOfferingConfig
operator|.
name|class
argument_list|)
block|,
name|MultipleClassSetupDepartment
argument_list|(
name|Department
operator|.
name|class
argument_list|)
block|,
name|MultipleClassSetupClass
argument_list|(
name|Class_
operator|.
name|class
argument_list|)
block|,
name|AssignInstructors
argument_list|(
name|InstrOfferingConfig
operator|.
name|class
argument_list|)
block|,
name|AssignInstructorsClass
argument_list|(
name|Class_
operator|.
name|class
argument_list|)
block|,
name|SchedulingSubpartDetail
argument_list|(
name|SchedulingSubpart
operator|.
name|class
argument_list|)
block|,
name|SchedulingSubpartDetailClearClassPreferences
argument_list|(
name|SchedulingSubpart
operator|.
name|class
argument_list|)
block|,
name|SchedulingSubpartEdit
argument_list|(
name|SchedulingSubpart
operator|.
name|class
argument_list|)
block|,
name|SchedulingSubpartEditClearPreferences
argument_list|(
name|SchedulingSubpart
operator|.
name|class
argument_list|)
block|,
name|ClassDetail
argument_list|(
name|Class_
operator|.
name|class
argument_list|)
block|,
name|ClassEdit
argument_list|(
name|Class_
operator|.
name|class
argument_list|)
block|,
name|ClassEditClearPreferences
argument_list|(
name|Class_
operator|.
name|class
argument_list|)
block|,
name|ExtendedDatePatterns
block|,
name|ExtendedTimePatterns
block|,
name|CanUseHardTimePrefs
argument_list|(
name|PreferenceGroup
operator|.
name|class
argument_list|)
block|,
name|CanUseHardRoomPrefs
argument_list|(
name|PreferenceGroup
operator|.
name|class
argument_list|)
block|,
name|CanUseHardDistributionPrefs
argument_list|(
name|PreferenceGroup
operator|.
name|class
argument_list|)
block|,
name|CanUseHardPeriodPrefs
argument_list|(
name|PreferenceGroup
operator|.
name|class
argument_list|)
block|,
name|ClassAssignment
argument_list|(
name|Class_
operator|.
name|class
argument_list|)
block|,
name|CurriculumView
argument_list|(
name|Session
operator|.
name|class
argument_list|)
block|,
name|CurriculumDetail
argument_list|(
name|Curriculum
operator|.
name|class
argument_list|)
block|,
name|CurriculumAdd
argument_list|(
name|Department
operator|.
name|class
argument_list|)
block|,
name|CurriculumEdit
argument_list|(
name|Curriculum
operator|.
name|class
argument_list|)
block|,
name|CurriculumDelete
argument_list|(
name|Curriculum
operator|.
name|class
argument_list|)
block|,
name|CurriculumMerge
argument_list|(
name|Curriculum
operator|.
name|class
argument_list|)
block|,
name|CurriculumAdmin
argument_list|(
name|Session
operator|.
name|class
argument_list|)
block|,
name|CurriculumProjectionRulesDetail
argument_list|(
name|Session
operator|.
name|class
argument_list|)
block|,
name|CurriculumProjectionRulesEdit
argument_list|(
name|Session
operator|.
name|class
argument_list|)
block|,
name|Instructors
argument_list|(
name|Department
operator|.
name|class
argument_list|)
block|,
name|InstructorsExportPdf
argument_list|(
name|Department
operator|.
name|class
argument_list|)
block|,
name|ManageInstructors
argument_list|(
name|Department
operator|.
name|class
argument_list|)
block|,
name|InstructorDetail
argument_list|(
name|DepartmentalInstructor
operator|.
name|class
argument_list|)
block|,
name|InstructorAdd
argument_list|(
name|Department
operator|.
name|class
argument_list|)
block|,
name|InstructorEdit
argument_list|(
name|DepartmentalInstructor
operator|.
name|class
argument_list|)
block|,
name|InstructorEditClearPreferences
argument_list|(
name|DepartmentalInstructor
operator|.
name|class
argument_list|)
block|,
name|InstructorDelete
argument_list|(
name|DepartmentalInstructor
operator|.
name|class
argument_list|)
block|,
name|InstructorPreferences
argument_list|(
name|DepartmentalInstructor
operator|.
name|class
argument_list|)
block|,
name|Rooms
argument_list|(
name|Department
operator|.
name|class
argument_list|)
block|,
name|RoomsExportPdf
argument_list|(
name|Department
operator|.
name|class
argument_list|)
block|,
name|RoomsExportCsv
argument_list|(
name|Department
operator|.
name|class
argument_list|)
block|,
name|RoomDetail
argument_list|(
name|Location
operator|.
name|class
argument_list|)
block|,
name|RoomEdit
argument_list|(
name|Room
operator|.
name|class
argument_list|)
block|,
name|RoomEditChangeControll
argument_list|(
name|Location
operator|.
name|class
argument_list|)
block|,
name|RoomEditChangeExternalId
argument_list|(
name|Location
operator|.
name|class
argument_list|)
block|,
name|RoomEditChangeType
argument_list|(
name|Location
operator|.
name|class
argument_list|)
block|,
name|RoomEditChangeCapacity
argument_list|(
name|Location
operator|.
name|class
argument_list|)
block|,
name|RoomEditChangeExaminationStatus
argument_list|(
name|Location
operator|.
name|class
argument_list|)
block|,
name|RoomEditChangeRoomProperties
argument_list|(
name|Location
operator|.
name|class
argument_list|)
block|,
name|RoomAvailability
argument_list|(
name|Session
operator|.
name|class
argument_list|)
block|,
name|RoomDepartments
argument_list|(
name|Department
operator|.
name|class
argument_list|)
block|,
name|EditRoomDepartments
argument_list|(
name|Department
operator|.
name|class
argument_list|)
block|,
name|EditRoomDepartmentsExams
argument_list|(
name|Session
operator|.
name|class
argument_list|)
block|,
name|AddRoom
argument_list|(
name|Department
operator|.
name|class
argument_list|)
block|,
name|AddSpecialUseRoom
argument_list|(
name|Department
operator|.
name|class
argument_list|)
block|,
name|AddSpecialUseRoomExternalRoom
argument_list|(
name|ExternalRoom
operator|.
name|class
argument_list|)
block|,
name|RoomDelete
argument_list|(
name|Room
operator|.
name|class
argument_list|)
block|,
name|RoomDetailAvailability
argument_list|(
name|Location
operator|.
name|class
argument_list|)
block|,
name|RoomDetailPeriodPreferences
argument_list|(
name|Location
operator|.
name|class
argument_list|)
block|,
name|RoomEditAvailability
argument_list|(
name|Location
operator|.
name|class
argument_list|)
block|,
name|RoomEditPreference
argument_list|(
name|Location
operator|.
name|class
argument_list|)
block|,
name|RoomEditGroups
argument_list|(
name|Location
operator|.
name|class
argument_list|)
block|,
name|RoomEditGlobalGroups
argument_list|(
name|Location
operator|.
name|class
argument_list|)
block|,
name|RoomEditFeatures
argument_list|(
name|Location
operator|.
name|class
argument_list|)
block|,
name|RoomEditGlobalFeatures
argument_list|(
name|Location
operator|.
name|class
argument_list|)
block|,
name|AddNonUnivLocation
argument_list|(
name|Department
operator|.
name|class
argument_list|)
block|,
name|NonUniversityLocationEdit
argument_list|(
name|NonUniversityLocation
operator|.
name|class
argument_list|)
block|,
name|NonUniversityLocationDelete
argument_list|(
name|NonUniversityLocation
operator|.
name|class
argument_list|)
block|,
name|RoomFeatures
argument_list|(
name|Department
operator|.
name|class
argument_list|)
block|,
name|RoomFeaturesExportPdf
argument_list|(
name|Department
operator|.
name|class
argument_list|)
block|,
name|GlobalRoomFeatureAdd
argument_list|(
name|Session
operator|.
name|class
argument_list|)
block|,
name|DepartmentRoomFeatureAdd
argument_list|(
name|Department
operator|.
name|class
argument_list|)
block|,
name|DepartmenalRoomFeatureEdit
argument_list|(
name|DepartmentRoomFeature
operator|.
name|class
argument_list|)
block|,
name|GlobalRoomFeatureEdit
argument_list|(
name|GlobalRoomFeature
operator|.
name|class
argument_list|)
block|,
name|DepartmenalRoomFeatureDelete
argument_list|(
name|DepartmentRoomFeature
operator|.
name|class
argument_list|)
block|,
name|GlobalRoomFeatureDelete
argument_list|(
name|GlobalRoomFeature
operator|.
name|class
argument_list|)
block|,
name|RoomGroups
argument_list|(
name|Department
operator|.
name|class
argument_list|)
block|,
name|RoomGroupsExportPdf
argument_list|(
name|Department
operator|.
name|class
argument_list|)
block|,
name|GlobalRoomGroupAdd
argument_list|(
name|Session
operator|.
name|class
argument_list|)
block|,
name|DepartmentRoomGroupAdd
argument_list|(
name|Department
operator|.
name|class
argument_list|)
block|,
name|DepartmenalRoomGroupEdit
argument_list|(
name|RoomGroup
operator|.
name|class
argument_list|)
block|,
name|GlobalRoomGroupEdit
argument_list|(
name|RoomGroup
operator|.
name|class
argument_list|)
block|,
name|GlobalRoomGroupEditSetDefault
argument_list|(
name|RoomGroup
operator|.
name|class
argument_list|)
block|,
name|DepartmenalRoomGroupDelete
argument_list|(
name|RoomGroup
operator|.
name|class
argument_list|)
block|,
name|GlobalRoomGroupDelete
argument_list|(
name|RoomGroup
operator|.
name|class
argument_list|)
block|,
name|TravelTimesLoad
argument_list|(
name|Session
operator|.
name|class
argument_list|)
block|,
name|TravelTimesSave
argument_list|(
name|Session
operator|.
name|class
argument_list|)
block|,
name|DistributionPreferences
argument_list|(
name|Department
operator|.
name|class
argument_list|)
block|,
name|DistributionPreferenceAdd
argument_list|(
name|Department
operator|.
name|class
argument_list|)
block|,
name|DistributionPreferenceDetail
argument_list|(
name|DistributionPref
operator|.
name|class
argument_list|)
block|,
name|DistributionPreferenceEdit
argument_list|(
name|DistributionPref
operator|.
name|class
argument_list|)
block|,
name|DistributionPreferenceDelete
argument_list|(
name|DistributionPref
operator|.
name|class
argument_list|)
block|,
name|DistributionPreferenceClass
argument_list|(
name|Class_
operator|.
name|class
argument_list|)
block|,
name|DistributionPreferenceSubpart
argument_list|(
name|SchedulingSubpart
operator|.
name|class
argument_list|)
block|,
name|DistributionPreferenceExam
argument_list|(
name|Exam
operator|.
name|class
argument_list|)
block|,
name|Reservations
argument_list|(
name|Department
operator|.
name|class
argument_list|)
block|,
name|ReservationOffering
argument_list|(
name|InstructionalOffering
operator|.
name|class
argument_list|)
block|,
name|ReservationAdd
argument_list|(
name|Department
operator|.
name|class
argument_list|)
block|,
name|ReservationEdit
argument_list|(
name|Reservation
operator|.
name|class
argument_list|)
block|,
name|ReservationDelete
argument_list|(
name|Reservation
operator|.
name|class
argument_list|)
block|,
comment|/** Individual page rights: Course Timetabling */
name|CourseTimetabling
argument_list|(
name|SolverGroup
operator|.
name|class
argument_list|)
block|,
name|CourseTimetablingAudit
argument_list|(
name|SolverGroup
operator|.
name|class
argument_list|)
block|,
name|Timetables
argument_list|(
name|SolverGroup
operator|.
name|class
argument_list|)
block|,
name|TimetablesSolutionExportCsv
argument_list|(
name|Solution
operator|.
name|class
argument_list|)
block|,
name|TimetablesSolutionChangeNote
argument_list|(
name|Solution
operator|.
name|class
argument_list|)
block|,
name|TimetablesSolutionCommit
argument_list|(
name|SolverGroup
operator|.
name|class
argument_list|)
block|,
name|TimetablesSolutionLoad
argument_list|(
name|Solution
operator|.
name|class
argument_list|)
block|,
name|TimetablesSolutionLoadEmpty
argument_list|(
name|SolverGroup
operator|.
name|class
argument_list|)
block|,
name|TimetablesSolutionDelete
argument_list|(
name|Solution
operator|.
name|class
argument_list|)
block|,
name|Solver
argument_list|(
name|SolverGroup
operator|.
name|class
argument_list|)
block|,
name|SolverSolutionSave
argument_list|(
name|SolverGroup
operator|.
name|class
argument_list|)
block|,
name|SolverSolutionExportCsv
argument_list|(
name|SolverGroup
operator|.
name|class
argument_list|)
block|,
name|SolverSolutionExportXml
argument_list|(
name|SolverGroup
operator|.
name|class
argument_list|)
block|,
name|CanSelectSolverServer
block|,
name|Suggestions
argument_list|(
name|SolverGroup
operator|.
name|class
argument_list|)
block|,
name|TimetableGrid
argument_list|(
name|SolverGroup
operator|.
name|class
argument_list|)
block|,
name|AssignedClasses
argument_list|(
name|SolverGroup
operator|.
name|class
argument_list|)
block|,
name|NotAssignedClasses
argument_list|(
name|SolverGroup
operator|.
name|class
argument_list|)
block|,
name|SolutionChanges
argument_list|(
name|SolverGroup
operator|.
name|class
argument_list|)
block|,
name|AssignmentHistory
argument_list|(
name|SolverGroup
operator|.
name|class
argument_list|)
block|,
name|ConflictStatistics
argument_list|(
name|SolverGroup
operator|.
name|class
argument_list|)
block|,
name|SolverLog
argument_list|(
name|SolverGroup
operator|.
name|class
argument_list|)
block|,
name|SolutionReports
argument_list|(
name|SolverGroup
operator|.
name|class
argument_list|)
block|,
comment|/** Individual page rights: Examinations */
name|Examinations
argument_list|(
name|Session
operator|.
name|class
argument_list|)
block|,
name|ExaminationDetail
argument_list|(
name|Exam
operator|.
name|class
argument_list|)
block|,
name|ExaminationEdit
argument_list|(
name|Exam
operator|.
name|class
argument_list|)
block|,
name|ExaminationEditClearPreferences
argument_list|(
name|Exam
operator|.
name|class
argument_list|)
block|,
name|ExaminationDelete
argument_list|(
name|Exam
operator|.
name|class
argument_list|)
block|,
name|ExaminationClone
argument_list|(
name|Exam
operator|.
name|class
argument_list|)
block|,
name|ExaminationAdd
argument_list|(
name|Session
operator|.
name|class
argument_list|)
block|,
name|ExaminationAssignment
argument_list|(
name|Exam
operator|.
name|class
argument_list|)
block|,
name|ExaminationDistributionPreferences
argument_list|(
name|Session
operator|.
name|class
argument_list|)
block|,
name|ExaminationDistributionPreferenceAdd
argument_list|(
name|Session
operator|.
name|class
argument_list|)
block|,
name|ExaminationDistributionPreferenceDetail
argument_list|(
name|DistributionPref
operator|.
name|class
argument_list|)
block|,
name|ExaminationDistributionPreferenceEdit
argument_list|(
name|DistributionPref
operator|.
name|class
argument_list|)
block|,
name|ExaminationDistributionPreferenceDelete
argument_list|(
name|DistributionPref
operator|.
name|class
argument_list|)
block|,
name|ExaminationSchedule
argument_list|(
name|Session
operator|.
name|class
argument_list|)
block|,
comment|/** Individual page rights: Examination Timetabling */
name|ExaminationTimetabling
argument_list|(
name|Session
operator|.
name|class
argument_list|)
block|,
name|ExaminationSolver
argument_list|(
name|Session
operator|.
name|class
argument_list|)
block|,
name|ExaminationTimetable
argument_list|(
name|Session
operator|.
name|class
argument_list|)
block|,
name|AssignedExaminations
argument_list|(
name|Session
operator|.
name|class
argument_list|)
block|,
name|NotAssignedExaminations
argument_list|(
name|Session
operator|.
name|class
argument_list|)
block|,
name|ExaminationAssignmentChanges
argument_list|(
name|Session
operator|.
name|class
argument_list|)
block|,
name|ExaminationConflictStatistics
argument_list|(
name|Session
operator|.
name|class
argument_list|)
block|,
name|ExaminationSolverLog
argument_list|(
name|Session
operator|.
name|class
argument_list|)
block|,
name|ExaminationReports
argument_list|(
name|Session
operator|.
name|class
argument_list|)
block|,
name|ExaminationPdfReports
argument_list|(
name|Session
operator|.
name|class
argument_list|)
block|,
comment|/** Individual page rights: Students Scheduling */
name|StudentScheduling
argument_list|(
name|Session
operator|.
name|class
argument_list|)
block|,
name|EnrollmentAuditPDFReports
argument_list|(
name|Session
operator|.
name|class
argument_list|)
block|,
name|StudentSectioningSolver
argument_list|(
name|Session
operator|.
name|class
argument_list|)
block|,
name|StudentSectioningSolverLog
argument_list|(
name|Session
operator|.
name|class
argument_list|)
block|,
name|StudentSectioningSolverDashboard
argument_list|(
name|Session
operator|.
name|class
argument_list|)
block|,
comment|/** Individual page rights: Online Students Scheduling */
name|CourseRequests
argument_list|(
name|Session
operator|.
name|class
argument_list|)
block|,
name|SchedulingAssistant
argument_list|(
name|Session
operator|.
name|class
argument_list|)
block|,
name|SchedulingDashboard
argument_list|(
name|Session
operator|.
name|class
argument_list|)
block|,
name|ConsentApproval
argument_list|(
name|InstructionalOffering
operator|.
name|class
argument_list|)
block|,
name|StudentSchedulingAdvisor
block|,
name|StudentSchedulingAdmin
block|,
name|OfferingEnrollments
argument_list|(
name|InstructionalOffering
operator|.
name|class
argument_list|)
block|,
name|StudentEnrollments
argument_list|(
name|Student
operator|.
name|class
argument_list|)
block|,
comment|/** Individual page rights: Events */
name|Events
argument_list|(
name|Session
operator|.
name|class
argument_list|)
block|,
name|EventAddSpecial
argument_list|(
name|Session
operator|.
name|class
argument_list|)
block|,
name|EventAddCourseRelated
argument_list|(
name|Session
operator|.
name|class
argument_list|)
block|,
name|EventLookupContact
block|,
name|EventLookupSchedule
block|,
name|EventDetail
argument_list|(
name|Event
operator|.
name|class
argument_list|)
block|,
name|EventEdit
argument_list|(
name|Event
operator|.
name|class
argument_list|)
block|,
name|EventDate
argument_list|(
name|Date
operator|.
name|class
argument_list|)
block|,
name|EventLocation
argument_list|(
name|Location
operator|.
name|class
argument_list|)
block|,
name|EventLocationApprove
argument_list|(
name|Location
operator|.
name|class
argument_list|)
block|,
name|EventLocationOverbook
argument_list|(
name|Location
operator|.
name|class
argument_list|)
block|,
name|EventMeetingEdit
argument_list|(
name|Meeting
operator|.
name|class
argument_list|)
block|,
name|EventMeetingApprove
argument_list|(
name|Meeting
operator|.
name|class
argument_list|)
block|,
name|EventApprovePast
block|,
name|EventAnyLocation
block|,
name|EventEditPast
block|,
comment|/** Administration: Academic Sessions */
name|AcademicSessions
block|,
name|AcademicSessionAdd
block|,
name|AcademicSessionEdit
argument_list|(
name|Session
operator|.
name|class
argument_list|)
block|,
name|AcademicSessionDelete
argument_list|(
name|Session
operator|.
name|class
argument_list|)
block|,
name|TimetableManagers
block|,
name|TimetableManagerAdd
block|,
name|TimetableManagerEdit
argument_list|(
name|TimetableManager
operator|.
name|class
argument_list|)
block|,
name|TimetableManagerDelete
argument_list|(
name|TimetableManager
operator|.
name|class
argument_list|)
block|,
name|Departments
argument_list|(
name|Session
operator|.
name|class
argument_list|)
block|,
name|DepartmentAdd
argument_list|(
name|Session
operator|.
name|class
argument_list|)
block|,
name|DepartmentEdit
argument_list|(
name|Department
operator|.
name|class
argument_list|)
block|,
name|DepartmentEditChangeExternalManager
argument_list|(
name|Department
operator|.
name|class
argument_list|)
block|,
name|DepartmentDelete
argument_list|(
name|Department
operator|.
name|class
argument_list|)
block|,
name|SolverGroups
argument_list|(
name|Session
operator|.
name|class
argument_list|)
block|,
name|SubjectAreas
argument_list|(
name|Session
operator|.
name|class
argument_list|)
block|,
name|SubjectAreaAdd
argument_list|(
name|Session
operator|.
name|class
argument_list|)
block|,
name|SubjectAreaEdit
argument_list|(
name|SubjectArea
operator|.
name|class
argument_list|)
block|,
name|SubjectAreaDelete
argument_list|(
name|SubjectArea
operator|.
name|class
argument_list|)
block|,
name|SubjectAreaChangeDepartment
argument_list|(
name|SubjectArea
operator|.
name|class
argument_list|)
block|,
name|BuildingList
argument_list|(
name|Session
operator|.
name|class
argument_list|)
block|,
name|BuildingAdd
argument_list|(
name|Session
operator|.
name|class
argument_list|)
block|,
name|BuildingEdit
argument_list|(
name|Building
operator|.
name|class
argument_list|)
block|,
name|BuildingDelete
argument_list|(
name|Building
operator|.
name|class
argument_list|)
block|,
name|BuildingUpdateData
argument_list|(
name|Session
operator|.
name|class
argument_list|)
block|,
name|BuildingExportPdf
argument_list|(
name|Session
operator|.
name|class
argument_list|)
block|,
name|DatePatterns
argument_list|(
name|Session
operator|.
name|class
argument_list|)
block|,
name|TimePatterns
argument_list|(
name|Session
operator|.
name|class
argument_list|)
block|,
name|ExactTimes
block|,
name|AcademicAreas
argument_list|(
name|Session
operator|.
name|class
argument_list|)
block|,
name|AcademicAreaEdit
argument_list|(
name|Session
operator|.
name|class
argument_list|)
block|,
name|AcademicClassifications
argument_list|(
name|Session
operator|.
name|class
argument_list|)
block|,
name|AcademicClassificationEdit
argument_list|(
name|Session
operator|.
name|class
argument_list|)
block|,
name|Majors
argument_list|(
name|Session
operator|.
name|class
argument_list|)
block|,
name|MajorEdit
argument_list|(
name|Session
operator|.
name|class
argument_list|)
block|,
name|Minors
argument_list|(
name|Session
operator|.
name|class
argument_list|)
block|,
name|MinorEdit
argument_list|(
name|Session
operator|.
name|class
argument_list|)
block|,
name|StudentGroups
argument_list|(
name|Session
operator|.
name|class
argument_list|)
block|,
name|StudentGroupEdit
argument_list|(
name|Session
operator|.
name|class
argument_list|)
block|,
name|ExaminationPeriods
argument_list|(
name|Session
operator|.
name|class
argument_list|)
block|,
name|DataExchange
argument_list|(
name|Session
operator|.
name|class
argument_list|)
block|,
name|SessionRollForward
block|,
name|LastChanges
argument_list|(
name|Session
operator|.
name|class
argument_list|)
block|,
comment|/** Administration: Solver */
name|ManageSolvers
argument_list|(
name|Session
operator|.
name|class
argument_list|)
block|,
name|SolverParameterGroups
block|,
name|SolverParameters
block|,
name|SolverConfigurations
block|,
name|DistributionTypes
block|,
name|DistributionTypeEdit
argument_list|(
name|Session
operator|.
name|class
argument_list|)
block|,
comment|/** Administration: Other */
name|InstructionalTypes
block|,
name|InstructionalTypeAdd
block|,
name|InstructionalTypeEdit
argument_list|(
name|ItypeDesc
operator|.
name|class
argument_list|)
block|,
name|InstructionalTypeDelete
argument_list|(
name|ItypeDesc
operator|.
name|class
argument_list|)
block|,
name|StatusTypes
block|,
name|RoomTypes
block|,
name|SponsoringOrganizations
block|,
name|SponsoringOrganizationAdd
block|,
name|SponsoringOrganizationEdit
argument_list|(
name|SponsoringOrganization
operator|.
name|class
argument_list|)
block|,
name|SponsoringOrganizationDelete
argument_list|(
name|SponsoringOrganization
operator|.
name|class
argument_list|)
block|,
name|StandardEventNotes
block|,
name|Users
block|,
name|OfferingConsentTypes
block|,
name|OfferingConsentTypeEdit
block|,
name|CourseCreditFormats
block|,
name|CourseCreditFormatEdit
block|,
name|CourseCreditTypes
block|,
name|CourseCreditTypeEdit
block|,
name|CourseCreditUnits
block|,
name|CourseCreditUnitEdit
block|,
name|PositionTypes
block|,
name|PositionTypeEdit
block|,
name|StudentSchedulingStatusTypes
block|,
name|StudentSchedulingStatusTypeEdit
block|,
name|Roles
block|,
name|RoleEdit
block|,
name|Permissions
block|,
name|PermissionEdit
block|,
name|ExamTypes
block|,
name|ExamTypeEdit
block|,
comment|/** Administration: Defaults */
name|ApplicationConfig
block|,
name|SettingsAdmin
block|,
comment|/** Administration: Utilities */
name|PageStatistics
block|,
name|HibernateStatistics
block|,
name|TestHQL
block|,
comment|/** Preferences */
name|Chameleon
argument_list|(
name|Session
operator|.
name|class
argument_list|)
block|,
name|SettingsUser
block|,
comment|/** Help */
name|Inquiry
block|,
comment|/** Other */
name|PersonalSchedule
argument_list|(
name|Session
operator|.
name|class
argument_list|)
block|,
name|PersonalScheduleLookup
argument_list|(
name|Session
operator|.
name|class
argument_list|)
block|,
comment|/** Reports */
name|HQLReports
argument_list|(
name|Session
operator|.
name|class
argument_list|)
block|,
name|HQLReportAdd
argument_list|(
name|Session
operator|.
name|class
argument_list|)
block|,
name|HQLReportEdit
argument_list|(
name|SavedHQL
operator|.
name|class
argument_list|)
block|,
name|HQLReportDelete
argument_list|(
name|SavedHQL
operator|.
name|class
argument_list|)
block|,
name|HQLReportsCourses
argument_list|(
name|Session
operator|.
name|class
argument_list|)
block|,
name|HQLReportsExaminations
argument_list|(
name|Session
operator|.
name|class
argument_list|)
block|,
name|HQLReportsStudents
argument_list|(
name|Session
operator|.
name|class
argument_list|)
block|,
name|HQLReportsEvents
argument_list|(
name|Session
operator|.
name|class
argument_list|)
block|,
name|HQLReportsAdministration
argument_list|(
name|Session
operator|.
name|class
argument_list|)
block|,
name|HQLReportsAdminOnly
argument_list|(
name|Session
operator|.
name|class
argument_list|)
block|,
comment|/** Obsolete */
name|SolutionInformationDefinitions
block|,  	;
specifier|private
name|Class
argument_list|<
name|?
argument_list|>
name|iType
decl_stmt|;
name|Right
parameter_list|(
name|Class
argument_list|<
name|?
argument_list|>
name|type
parameter_list|)
block|{
name|iType
operator|=
name|type
expr_stmt|;
block|}
name|Right
parameter_list|()
block|{
name|this
argument_list|(
literal|null
argument_list|)
expr_stmt|;
block|}
specifier|public
name|Class
argument_list|<
name|?
argument_list|>
name|type
parameter_list|()
block|{
return|return
name|iType
return|;
block|}
specifier|public
name|boolean
name|hasType
parameter_list|()
block|{
return|return
name|iType
operator|!=
literal|null
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
name|name
argument_list|()
operator|.
name|replaceAll
argument_list|(
literal|"(\\p{Ll})(\\p{Lu})"
argument_list|,
literal|"$1 $2"
argument_list|)
return|;
block|}
block|}
end_enum

end_unit

