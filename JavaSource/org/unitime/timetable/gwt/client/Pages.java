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
name|gwt
operator|.
name|client
package|;
end_package

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
name|admin
operator|.
name|PasswordPage
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
name|admin
operator|.
name|ScriptPage
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
name|admin
operator|.
name|SimpleEditPage
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
name|admin
operator|.
name|TasksPage
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
name|courseofferings
operator|.
name|CourseOfferingEdit
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
name|curricula
operator|.
name|CurriculaPage
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
name|curricula
operator|.
name|CurriculumProjectionRulesPage
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
name|departments
operator|.
name|DepartmentsPage
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
name|events
operator|.
name|EventResourceTimetable
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
name|events
operator|.
name|EventRoomAvailability
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
name|hql
operator|.
name|SavedHQLPage
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
name|InstructorAttributesPage
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
name|SetupTeachingRequestsPage
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
name|TeachingAssignmentsChangesPage
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
name|TeachingAssignmentsPage
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
name|TeachingRequestsPage
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
name|limitandprojectionsnapshot
operator|.
name|LimitAndProjectionSnapshotPage
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
name|offerings
operator|.
name|AssignClassInstructorsPage
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
name|pointintimedata
operator|.
name|PointInTimeDataReportsPage
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
name|reservations
operator|.
name|ReservationEdit
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
name|reservations
operator|.
name|ReservationsPage
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
name|rooms
operator|.
name|BuildingsPage
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
name|rooms
operator|.
name|RoomFeaturesPage
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
name|rooms
operator|.
name|RoomGroupsPage
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
name|rooms
operator|.
name|RoomPicturesPage
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
name|rooms
operator|.
name|RoomSharingPage
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
name|rooms
operator|.
name|RoomsPage
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
name|rooms
operator|.
name|TravelTimes
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
name|sectioning
operator|.
name|AdvisorCourseRequestsPage
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
name|sectioning
operator|.
name|PublishedSectioningSolutionsPage
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
name|sectioning
operator|.
name|SectioningReports
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
name|sectioning
operator|.
name|SectioningStatusPage
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
name|sectioning
operator|.
name|StudentSectioningPage
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
name|solver
operator|.
name|AssignedClassesPage
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
name|solver
operator|.
name|AssignmentHistoryPage
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
name|solver
operator|.
name|ConflictBasedStatisticsPage
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
name|solver
operator|.
name|ListSolutionsPage
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
name|solver
operator|.
name|NotAssignedClassesPage
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
name|solver
operator|.
name|SolutionChangesPage
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
name|solver
operator|.
name|SolutionReportsPage
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
name|solver
operator|.
name|SolverLogPage
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
name|solver
operator|.
name|SolverPage
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
name|solver
operator|.
name|SuggestionsPage
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
name|solver
operator|.
name|TimetablePage
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
name|test
operator|.
name|OnlineSectioningTest
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
name|com
operator|.
name|google
operator|.
name|gwt
operator|.
name|user
operator|.
name|client
operator|.
name|ui
operator|.
name|Widget
import|;
end_import

begin_comment
comment|/**  * Register GWT pages here.  * @author Tomas Muller  *  */
end_comment

begin_enum
specifier|public
enum|enum
name|Pages
block|{
name|curricula
argument_list|(
operator|new
name|PageFactory
argument_list|()
block|{
specifier|public
name|Widget
name|create
parameter_list|()
block|{
return|return
operator|new
name|CurriculaPage
argument_list|()
return|;
block|}
specifier|public
name|String
name|name
parameter_list|(
name|GwtMessages
name|messages
parameter_list|)
block|{
return|return
name|messages
operator|.
name|pageCurricula
argument_list|()
return|;
block|}
block|}
argument_list|)
block|,
name|curprojrules
argument_list|(
operator|new
name|PageFactory
argument_list|()
block|{
specifier|public
name|Widget
name|create
parameter_list|()
block|{
return|return
operator|new
name|CurriculumProjectionRulesPage
argument_list|()
return|;
block|}
specifier|public
name|String
name|name
parameter_list|(
name|GwtMessages
name|messages
parameter_list|)
block|{
return|return
name|messages
operator|.
name|pageCurriculumProjectionRules
argument_list|()
return|;
block|}
block|}
argument_list|)
block|,
name|sectioning
argument_list|(
operator|new
name|PageFactory
argument_list|()
block|{
specifier|public
name|Widget
name|create
parameter_list|()
block|{
return|return
operator|new
name|StudentSectioningPage
argument_list|(
name|StudentSectioningPage
operator|.
name|Mode
operator|.
name|SECTIONING
argument_list|)
return|;
block|}
specifier|public
name|String
name|name
parameter_list|(
name|GwtMessages
name|messages
parameter_list|)
block|{
return|return
name|messages
operator|.
name|pageStudentSchedulingAssistant
argument_list|()
return|;
block|}
block|}
argument_list|)
block|,
name|requests
argument_list|(
operator|new
name|PageFactory
argument_list|()
block|{
specifier|public
name|Widget
name|create
parameter_list|()
block|{
return|return
operator|new
name|StudentSectioningPage
argument_list|(
name|StudentSectioningPage
operator|.
name|Mode
operator|.
name|REQUESTS
argument_list|)
return|;
block|}
specifier|public
name|String
name|name
parameter_list|(
name|GwtMessages
name|messages
parameter_list|)
block|{
return|return
name|messages
operator|.
name|pageStudentCourseRequests
argument_list|()
return|;
block|}
block|}
argument_list|)
block|,
name|admin
argument_list|(
operator|new
name|PageFactory
argument_list|()
block|{
specifier|public
name|Widget
name|create
parameter_list|()
block|{
return|return
operator|new
name|SimpleEditPage
argument_list|()
return|;
block|}
specifier|public
name|String
name|name
parameter_list|(
name|GwtMessages
name|messages
parameter_list|)
block|{
return|return
name|messages
operator|.
name|pageAdministration
argument_list|()
return|;
block|}
block|}
argument_list|)
block|,
name|events
argument_list|(
operator|new
name|PageFactory
argument_list|()
block|{
specifier|public
name|Widget
name|create
parameter_list|()
block|{
return|return
operator|new
name|EventResourceTimetable
argument_list|(
name|EventResourceTimetable
operator|.
name|PageType
operator|.
name|Events
argument_list|)
return|;
block|}
specifier|public
name|String
name|name
parameter_list|(
name|GwtMessages
name|messages
parameter_list|)
block|{
return|return
name|messages
operator|.
name|pageEvents
argument_list|()
return|;
block|}
block|}
argument_list|)
block|,
name|timetable
argument_list|(
operator|new
name|PageFactory
argument_list|()
block|{
specifier|public
name|Widget
name|create
parameter_list|()
block|{
return|return
operator|new
name|EventResourceTimetable
argument_list|(
name|EventResourceTimetable
operator|.
name|PageType
operator|.
name|Timetable
argument_list|)
return|;
block|}
specifier|public
name|String
name|name
parameter_list|(
name|GwtMessages
name|messages
parameter_list|)
block|{
return|return
name|messages
operator|.
name|pageEventTimetable
argument_list|()
return|;
block|}
block|}
argument_list|)
block|,
name|roomtable
argument_list|(
operator|new
name|PageFactory
argument_list|()
block|{
specifier|public
name|Widget
name|create
parameter_list|()
block|{
return|return
operator|new
name|EventResourceTimetable
argument_list|(
name|EventResourceTimetable
operator|.
name|PageType
operator|.
name|RoomTimetable
argument_list|)
return|;
block|}
specifier|public
name|String
name|name
parameter_list|(
name|GwtMessages
name|messages
parameter_list|)
block|{
return|return
name|messages
operator|.
name|pageRoomTimetable
argument_list|()
return|;
block|}
block|}
argument_list|)
block|,
name|reservation
argument_list|(
operator|new
name|PageFactory
argument_list|()
block|{
specifier|public
name|Widget
name|create
parameter_list|()
block|{
return|return
operator|new
name|ReservationEdit
argument_list|(
literal|true
argument_list|)
return|;
block|}
specifier|public
name|String
name|name
parameter_list|(
name|GwtMessages
name|messages
parameter_list|)
block|{
return|return
name|messages
operator|.
name|pageEditReservation
argument_list|()
return|;
block|}
block|}
argument_list|)
block|,
name|courseOffering
argument_list|(
operator|new
name|PageFactory
argument_list|()
block|{
specifier|public
name|Widget
name|create
parameter_list|()
block|{
return|return
operator|new
name|CourseOfferingEdit
argument_list|()
return|;
block|}
specifier|public
name|String
name|name
parameter_list|(
name|GwtMessages
name|messages
parameter_list|)
block|{
return|return
name|messages
operator|.
name|pageEditCourseOffering
argument_list|()
return|;
block|}
block|}
argument_list|)
block|,
name|reservations
argument_list|(
operator|new
name|PageFactory
argument_list|()
block|{
specifier|public
name|Widget
name|create
parameter_list|()
block|{
return|return
operator|new
name|ReservationsPage
argument_list|()
return|;
block|}
specifier|public
name|String
name|name
parameter_list|(
name|GwtMessages
name|messages
parameter_list|)
block|{
return|return
name|messages
operator|.
name|pageReservations
argument_list|()
return|;
block|}
block|}
argument_list|)
block|,
name|sectioningtest
argument_list|(
operator|new
name|PageFactory
argument_list|()
block|{
specifier|public
name|Widget
name|create
parameter_list|()
block|{
return|return
operator|new
name|OnlineSectioningTest
argument_list|()
return|;
block|}
specifier|public
name|String
name|name
parameter_list|(
name|GwtMessages
name|messages
parameter_list|)
block|{
return|return
name|messages
operator|.
name|pageOnlineStudentSectioningTest
argument_list|()
return|;
block|}
block|}
argument_list|)
block|,
name|hql
argument_list|(
operator|new
name|PageFactory
argument_list|()
block|{
specifier|public
name|Widget
name|create
parameter_list|()
block|{
return|return
operator|new
name|SavedHQLPage
argument_list|()
return|;
block|}
specifier|public
name|String
name|name
parameter_list|(
name|GwtMessages
name|messages
parameter_list|)
block|{
return|return
name|messages
operator|.
name|pageCourseReports
argument_list|()
return|;
block|}
block|}
argument_list|)
block|,
name|onlinesctdash
argument_list|(
operator|new
name|PageFactory
argument_list|()
block|{
specifier|public
name|Widget
name|create
parameter_list|()
block|{
return|return
operator|new
name|SectioningStatusPage
argument_list|(
literal|true
argument_list|)
return|;
block|}
specifier|public
name|String
name|name
parameter_list|(
name|GwtMessages
name|messages
parameter_list|)
block|{
return|return
name|messages
operator|.
name|pageOnlineStudentSchedulingDashboard
argument_list|()
return|;
block|}
block|}
argument_list|)
block|,
name|batchsctdash
argument_list|(
operator|new
name|PageFactory
argument_list|()
block|{
specifier|public
name|Widget
name|create
parameter_list|()
block|{
return|return
operator|new
name|SectioningStatusPage
argument_list|(
literal|false
argument_list|)
return|;
block|}
specifier|public
name|String
name|name
parameter_list|(
name|GwtMessages
name|messages
parameter_list|)
block|{
return|return
name|messages
operator|.
name|pageStudentSectioningDashboard
argument_list|()
return|;
block|}
block|}
argument_list|)
block|,
name|traveltimes
argument_list|(
operator|new
name|PageFactory
argument_list|()
block|{
specifier|public
name|Widget
name|create
parameter_list|()
block|{
return|return
operator|new
name|TravelTimes
argument_list|()
return|;
block|}
specifier|public
name|String
name|name
parameter_list|(
name|GwtMessages
name|messages
parameter_list|)
block|{
return|return
name|messages
operator|.
name|pageTravelTimes
argument_list|()
return|;
block|}
block|}
argument_list|)
block|,
name|classes
argument_list|(
operator|new
name|PageFactory
argument_list|()
block|{
specifier|public
name|Widget
name|create
parameter_list|()
block|{
return|return
operator|new
name|EventResourceTimetable
argument_list|(
name|EventResourceTimetable
operator|.
name|PageType
operator|.
name|Classes
argument_list|)
return|;
block|}
specifier|public
name|String
name|name
parameter_list|(
name|GwtMessages
name|messages
parameter_list|)
block|{
return|return
name|messages
operator|.
name|pageClasses
argument_list|()
return|;
block|}
block|}
argument_list|)
block|,
name|exams
argument_list|(
operator|new
name|PageFactory
argument_list|()
block|{
specifier|public
name|Widget
name|create
parameter_list|()
block|{
return|return
operator|new
name|EventResourceTimetable
argument_list|(
name|EventResourceTimetable
operator|.
name|PageType
operator|.
name|Exams
argument_list|)
return|;
block|}
specifier|public
name|String
name|name
parameter_list|(
name|GwtMessages
name|messages
parameter_list|)
block|{
return|return
name|messages
operator|.
name|pageExaminations
argument_list|()
return|;
block|}
block|}
argument_list|)
block|,
name|personal
argument_list|(
operator|new
name|PageFactory
argument_list|()
block|{
specifier|public
name|Widget
name|create
parameter_list|()
block|{
return|return
operator|new
name|EventResourceTimetable
argument_list|(
name|EventResourceTimetable
operator|.
name|PageType
operator|.
name|Personal
argument_list|)
return|;
block|}
specifier|public
name|String
name|name
parameter_list|(
name|GwtMessages
name|messages
parameter_list|)
block|{
return|return
name|messages
operator|.
name|pagePersonalTimetable
argument_list|()
return|;
block|}
block|}
argument_list|)
block|,
name|roomavailability
argument_list|(
operator|new
name|PageFactory
argument_list|()
block|{
specifier|public
name|Widget
name|create
parameter_list|()
block|{
return|return
operator|new
name|RoomSharingPage
argument_list|()
return|;
block|}
specifier|public
name|String
name|name
parameter_list|(
name|GwtMessages
name|messages
parameter_list|)
block|{
return|return
name|messages
operator|.
name|pageEditRoomAvailability
argument_list|()
return|;
block|}
block|}
argument_list|)
block|,
name|scripts
argument_list|(
operator|new
name|PageFactory
argument_list|()
block|{
specifier|public
name|Widget
name|create
parameter_list|()
block|{
return|return
operator|new
name|ScriptPage
argument_list|()
return|;
block|}
specifier|public
name|String
name|name
parameter_list|(
name|GwtMessages
name|messages
parameter_list|)
block|{
return|return
name|messages
operator|.
name|pageScripts
argument_list|()
return|;
block|}
block|}
argument_list|)
block|,
name|availability
argument_list|(
operator|new
name|PageFactory
argument_list|()
block|{
specifier|public
name|Widget
name|create
parameter_list|()
block|{
return|return
operator|new
name|EventRoomAvailability
argument_list|()
return|;
block|}
specifier|public
name|String
name|name
parameter_list|(
name|GwtMessages
name|messages
parameter_list|)
block|{
return|return
name|messages
operator|.
name|pageEventRoomAvailability
argument_list|()
return|;
block|}
block|}
argument_list|)
block|,
name|password
argument_list|(
operator|new
name|PageFactory
argument_list|()
block|{
specifier|public
name|Widget
name|create
parameter_list|()
block|{
return|return
operator|new
name|PasswordPage
argument_list|()
return|;
block|}
specifier|public
name|String
name|name
parameter_list|(
name|GwtMessages
name|messages
parameter_list|)
block|{
return|return
name|messages
operator|.
name|pageChangePassword
argument_list|()
return|;
block|}
block|}
argument_list|)
block|,
name|sctreport
argument_list|(
operator|new
name|PageFactory
argument_list|()
block|{
specifier|public
name|Widget
name|create
parameter_list|()
block|{
return|return
operator|new
name|SectioningReports
argument_list|(
literal|false
argument_list|)
return|;
block|}
specifier|public
name|String
name|name
parameter_list|(
name|GwtMessages
name|messages
parameter_list|)
block|{
return|return
name|messages
operator|.
name|pageBatchSectioningReports
argument_list|()
return|;
block|}
block|}
argument_list|)
block|,
name|onlinereport
argument_list|(
operator|new
name|PageFactory
argument_list|()
block|{
specifier|public
name|Widget
name|create
parameter_list|()
block|{
return|return
operator|new
name|SectioningReports
argument_list|(
literal|true
argument_list|)
return|;
block|}
specifier|public
name|String
name|name
parameter_list|(
name|GwtMessages
name|messages
parameter_list|)
block|{
return|return
name|messages
operator|.
name|pageOnlineSectioningReports
argument_list|()
return|;
block|}
block|}
argument_list|)
block|,
name|roompictures
argument_list|(
operator|new
name|PageFactory
argument_list|()
block|{
specifier|public
name|Widget
name|create
parameter_list|()
block|{
return|return
operator|new
name|RoomPicturesPage
argument_list|()
return|;
block|}
specifier|public
name|String
name|name
parameter_list|(
name|GwtMessages
name|messages
parameter_list|)
block|{
return|return
name|messages
operator|.
name|pageRoomPictures
argument_list|()
return|;
block|}
block|}
argument_list|)
block|,
name|rooms
argument_list|(
operator|new
name|PageFactory
argument_list|()
block|{
specifier|public
name|Widget
name|create
parameter_list|()
block|{
return|return
operator|new
name|RoomsPage
argument_list|()
return|;
block|}
specifier|public
name|String
name|name
parameter_list|(
name|GwtMessages
name|messages
parameter_list|)
block|{
return|return
name|messages
operator|.
name|pageRooms
argument_list|()
return|;
block|}
block|}
argument_list|)
block|,
name|roomgroups
argument_list|(
operator|new
name|PageFactory
argument_list|()
block|{
specifier|public
name|Widget
name|create
parameter_list|()
block|{
return|return
operator|new
name|RoomGroupsPage
argument_list|()
return|;
block|}
specifier|public
name|String
name|name
parameter_list|(
name|GwtMessages
name|messages
parameter_list|)
block|{
return|return
name|messages
operator|.
name|pageRoomGroups
argument_list|()
return|;
block|}
block|}
argument_list|)
block|,
name|roomfeatures
argument_list|(
operator|new
name|PageFactory
argument_list|()
block|{
specifier|public
name|Widget
name|create
parameter_list|()
block|{
return|return
operator|new
name|RoomFeaturesPage
argument_list|()
return|;
block|}
specifier|public
name|String
name|name
parameter_list|(
name|GwtMessages
name|messages
parameter_list|)
block|{
return|return
name|messages
operator|.
name|pageRoomFeatures
argument_list|()
return|;
block|}
block|}
argument_list|)
block|,
name|instructorattributes
argument_list|(
operator|new
name|PageFactory
argument_list|()
block|{
specifier|public
name|Widget
name|create
parameter_list|()
block|{
return|return
operator|new
name|InstructorAttributesPage
argument_list|()
return|;
block|}
specifier|public
name|String
name|name
parameter_list|(
name|GwtMessages
name|messages
parameter_list|)
block|{
return|return
name|messages
operator|.
name|pageInstructorAttributes
argument_list|()
return|;
block|}
block|}
argument_list|)
block|,
name|solver
argument_list|(
operator|new
name|PageFactory
argument_list|()
block|{
specifier|public
name|Widget
name|create
parameter_list|()
block|{
return|return
operator|new
name|SolverPage
argument_list|()
return|;
block|}
specifier|public
name|String
name|name
parameter_list|(
name|GwtMessages
name|messages
parameter_list|)
block|{
return|return
name|messages
operator|.
name|pageSolver
argument_list|()
return|;
block|}
block|}
argument_list|)
block|,
name|solverlog
argument_list|(
operator|new
name|PageFactory
argument_list|()
block|{
specifier|public
name|Widget
name|create
parameter_list|()
block|{
return|return
operator|new
name|SolverLogPage
argument_list|()
return|;
block|}
specifier|public
name|String
name|name
parameter_list|(
name|GwtMessages
name|messages
parameter_list|)
block|{
return|return
name|messages
operator|.
name|pageSolverLog
argument_list|()
return|;
block|}
block|}
argument_list|)
block|,
name|teachingRequests
argument_list|(
operator|new
name|PageFactory
argument_list|()
block|{
specifier|public
name|Widget
name|create
parameter_list|()
block|{
return|return
operator|new
name|TeachingRequestsPage
argument_list|()
return|;
block|}
specifier|public
name|String
name|name
parameter_list|(
name|GwtMessages
name|messages
parameter_list|)
block|{
return|return
name|messages
operator|.
name|pageAssignedTeachingRequests
argument_list|()
return|;
block|}
block|}
argument_list|)
block|,
name|teachingAssignments
argument_list|(
operator|new
name|PageFactory
argument_list|()
block|{
specifier|public
name|Widget
name|create
parameter_list|()
block|{
return|return
operator|new
name|TeachingAssignmentsPage
argument_list|()
return|;
block|}
specifier|public
name|String
name|name
parameter_list|(
name|GwtMessages
name|messages
parameter_list|)
block|{
return|return
name|messages
operator|.
name|pageTeachingAssignments
argument_list|()
return|;
block|}
block|}
argument_list|)
block|,
name|teachingAssignmentChanges
argument_list|(
operator|new
name|PageFactory
argument_list|()
block|{
specifier|public
name|Widget
name|create
parameter_list|()
block|{
return|return
operator|new
name|TeachingAssignmentsChangesPage
argument_list|()
return|;
block|}
specifier|public
name|String
name|name
parameter_list|(
name|GwtMessages
name|messages
parameter_list|)
block|{
return|return
name|messages
operator|.
name|pageTeachingAssignmentChanges
argument_list|()
return|;
block|}
block|}
argument_list|)
block|,
name|setupTeachingRequests
argument_list|(
operator|new
name|PageFactory
argument_list|()
block|{
specifier|public
name|Widget
name|create
parameter_list|()
block|{
return|return
operator|new
name|SetupTeachingRequestsPage
argument_list|()
return|;
block|}
specifier|public
name|String
name|name
parameter_list|(
name|GwtMessages
name|messages
parameter_list|)
block|{
return|return
name|messages
operator|.
name|pageSetupTeachingRequests
argument_list|()
return|;
block|}
block|}
argument_list|)
block|,
name|timetableGrid
argument_list|(
operator|new
name|PageFactory
argument_list|()
block|{
specifier|public
name|Widget
name|create
parameter_list|()
block|{
return|return
operator|new
name|TimetablePage
argument_list|()
return|;
block|}
specifier|public
name|String
name|name
parameter_list|(
name|GwtMessages
name|messages
parameter_list|)
block|{
return|return
name|messages
operator|.
name|pageTimetableGrid
argument_list|()
return|;
block|}
block|}
argument_list|)
block|,
name|pointInTimeDataReports
argument_list|(
operator|new
name|PageFactory
argument_list|()
block|{
specifier|public
name|Widget
name|create
parameter_list|()
block|{
return|return
operator|new
name|PointInTimeDataReportsPage
argument_list|()
return|;
block|}
specifier|public
name|String
name|name
parameter_list|(
name|GwtMessages
name|messages
parameter_list|)
block|{
return|return
name|messages
operator|.
name|pageCourseReports
argument_list|()
return|;
block|}
block|}
argument_list|)
block|,
name|assignedClasses
argument_list|(
operator|new
name|PageFactory
argument_list|()
block|{
specifier|public
name|Widget
name|create
parameter_list|()
block|{
return|return
operator|new
name|AssignedClassesPage
argument_list|()
return|;
block|}
specifier|public
name|String
name|name
parameter_list|(
name|GwtMessages
name|messages
parameter_list|)
block|{
return|return
name|messages
operator|.
name|pageAssignedClasses
argument_list|()
return|;
block|}
block|}
argument_list|)
block|,
name|notAssignedClasses
argument_list|(
operator|new
name|PageFactory
argument_list|()
block|{
specifier|public
name|Widget
name|create
parameter_list|()
block|{
return|return
operator|new
name|NotAssignedClassesPage
argument_list|()
return|;
block|}
specifier|public
name|String
name|name
parameter_list|(
name|GwtMessages
name|messages
parameter_list|)
block|{
return|return
name|messages
operator|.
name|pageNotAssignedClasses
argument_list|()
return|;
block|}
block|}
argument_list|)
block|,
name|limitAndProjectionSnapshot
argument_list|(
operator|new
name|PageFactory
argument_list|()
block|{
specifier|public
name|Widget
name|create
parameter_list|()
block|{
return|return
operator|new
name|LimitAndProjectionSnapshotPage
argument_list|()
return|;
block|}
specifier|public
name|String
name|name
parameter_list|(
name|GwtMessages
name|messages
parameter_list|)
block|{
return|return
name|messages
operator|.
name|pageLimitAndProjectionSnapshot
argument_list|()
return|;
block|}
block|}
argument_list|)
block|,
name|suggestions
argument_list|(
operator|new
name|PageFactory
argument_list|()
block|{
specifier|public
name|Widget
name|create
parameter_list|()
block|{
return|return
operator|new
name|SuggestionsPage
argument_list|()
return|;
block|}
specifier|public
name|String
name|name
parameter_list|(
name|GwtMessages
name|messages
parameter_list|)
block|{
return|return
name|messages
operator|.
name|pageSuggestions
argument_list|()
return|;
block|}
block|}
argument_list|)
block|,
name|cbs
argument_list|(
operator|new
name|PageFactory
argument_list|()
block|{
specifier|public
name|Widget
name|create
parameter_list|()
block|{
return|return
operator|new
name|ConflictBasedStatisticsPage
argument_list|()
return|;
block|}
specifier|public
name|String
name|name
parameter_list|(
name|GwtMessages
name|messages
parameter_list|)
block|{
return|return
name|messages
operator|.
name|pageConflictBasedStatistics
argument_list|()
return|;
block|}
block|}
argument_list|)
block|,
name|solutionChanges
argument_list|(
operator|new
name|PageFactory
argument_list|()
block|{
specifier|public
name|Widget
name|create
parameter_list|()
block|{
return|return
operator|new
name|SolutionChangesPage
argument_list|()
return|;
block|}
specifier|public
name|String
name|name
parameter_list|(
name|GwtMessages
name|messages
parameter_list|)
block|{
return|return
name|messages
operator|.
name|pageSolutionChanges
argument_list|()
return|;
block|}
block|}
argument_list|)
block|,
name|assignmentHistory
argument_list|(
operator|new
name|PageFactory
argument_list|()
block|{
specifier|public
name|Widget
name|create
parameter_list|()
block|{
return|return
operator|new
name|AssignmentHistoryPage
argument_list|()
return|;
block|}
specifier|public
name|String
name|name
parameter_list|(
name|GwtMessages
name|messages
parameter_list|)
block|{
return|return
name|messages
operator|.
name|pageAssignmentHistory
argument_list|()
return|;
block|}
block|}
argument_list|)
block|,
name|listSolutions
argument_list|(
operator|new
name|PageFactory
argument_list|()
block|{
specifier|public
name|Widget
name|create
parameter_list|()
block|{
return|return
operator|new
name|ListSolutionsPage
argument_list|()
return|;
block|}
specifier|public
name|String
name|name
parameter_list|(
name|GwtMessages
name|messages
parameter_list|)
block|{
return|return
name|messages
operator|.
name|pageListSolutions
argument_list|()
return|;
block|}
block|}
argument_list|)
block|,
name|solutionReports
argument_list|(
operator|new
name|PageFactory
argument_list|()
block|{
specifier|public
name|Widget
name|create
parameter_list|()
block|{
return|return
operator|new
name|SolutionReportsPage
argument_list|()
return|;
block|}
specifier|public
name|String
name|name
parameter_list|(
name|GwtMessages
name|messages
parameter_list|)
block|{
return|return
name|messages
operator|.
name|pageSolutionReports
argument_list|()
return|;
block|}
block|}
argument_list|)
block|,
name|tasks
argument_list|(
operator|new
name|PageFactory
argument_list|()
block|{
specifier|public
name|Widget
name|create
parameter_list|()
block|{
return|return
operator|new
name|TasksPage
argument_list|()
return|;
block|}
specifier|public
name|String
name|name
parameter_list|(
name|GwtMessages
name|messages
parameter_list|)
block|{
return|return
name|messages
operator|.
name|pageTasks
argument_list|()
return|;
block|}
block|}
argument_list|)
block|,
name|publishedSolutions
argument_list|(
operator|new
name|PageFactory
argument_list|()
block|{
specifier|public
name|Widget
name|create
parameter_list|()
block|{
return|return
operator|new
name|PublishedSectioningSolutionsPage
argument_list|()
return|;
block|}
specifier|public
name|String
name|name
parameter_list|(
name|GwtMessages
name|messages
parameter_list|)
block|{
return|return
name|messages
operator|.
name|pagePublishedSectioningSolutions
argument_list|()
return|;
block|}
block|}
argument_list|)
block|,
name|acrf
argument_list|(
operator|new
name|PageFactory
argument_list|()
block|{
specifier|public
name|Widget
name|create
parameter_list|()
block|{
return|return
operator|new
name|AdvisorCourseRequestsPage
argument_list|()
return|;
block|}
specifier|public
name|String
name|name
parameter_list|(
name|GwtMessages
name|messages
parameter_list|)
block|{
return|return
name|messages
operator|.
name|pageAdvisorCourseRequests
argument_list|()
return|;
block|}
block|}
argument_list|)
block|,
name|buildings
argument_list|(
operator|new
name|PageFactory
argument_list|()
block|{
specifier|public
name|Widget
name|create
parameter_list|()
block|{
return|return
operator|new
name|BuildingsPage
argument_list|()
return|;
block|}
specifier|public
name|String
name|name
parameter_list|(
name|GwtMessages
name|messages
parameter_list|)
block|{
return|return
name|messages
operator|.
name|pageBuildings
argument_list|()
return|;
block|}
block|}
argument_list|)
block|,
name|departments
argument_list|(
operator|new
name|PageFactory
argument_list|()
block|{
specifier|public
name|Widget
name|create
parameter_list|()
block|{
return|return
operator|new
name|DepartmentsPage
argument_list|()
return|;
block|}
specifier|public
name|String
name|name
parameter_list|(
name|GwtMessages
name|messages
parameter_list|)
block|{
return|return
name|messages
operator|.
name|pageDepartments
argument_list|()
return|;
block|}
block|}
argument_list|)
block|,
name|assignClassInstructors
argument_list|(
operator|new
name|PageFactory
argument_list|()
block|{
specifier|public
name|Widget
name|create
parameter_list|()
block|{
return|return
operator|new
name|AssignClassInstructorsPage
argument_list|()
return|;
block|}
specifier|public
name|String
name|name
parameter_list|(
name|GwtMessages
name|messages
parameter_list|)
block|{
return|return
name|messages
operator|.
name|pageAdministration
argument_list|()
return|;
block|}
block|}
argument_list|)
block|, 	;
specifier|private
name|PageFactory
name|iFactory
decl_stmt|;
name|Pages
parameter_list|(
name|String
name|oldTitle
parameter_list|,
name|PageFactory
name|factory
parameter_list|)
block|{
name|iFactory
operator|=
name|factory
expr_stmt|;
block|}
name|Pages
parameter_list|(
name|PageFactory
name|factory
parameter_list|)
block|{
name|iFactory
operator|=
name|factory
expr_stmt|;
block|}
specifier|public
name|String
name|name
parameter_list|(
name|GwtMessages
name|messages
parameter_list|)
block|{
return|return
name|iFactory
operator|.
name|name
argument_list|(
name|messages
argument_list|)
return|;
block|}
specifier|public
name|Widget
name|widget
parameter_list|()
block|{
return|return
name|iFactory
operator|.
name|create
argument_list|()
return|;
block|}
specifier|public
interface|interface
name|PageFactory
block|{
name|Widget
name|create
parameter_list|()
function_decl|;
name|String
name|name
parameter_list|(
name|GwtMessages
name|messages
parameter_list|)
function_decl|;
block|}
block|}
end_enum

end_unit

