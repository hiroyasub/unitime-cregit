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
name|curricula
operator|.
name|CourseCurriculaTable
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
name|SingleDateSelector
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
name|TeachingRequestsWidget
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
name|page
operator|.
name|UniTimePageLabel
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
name|page
operator|.
name|UniTimeBack
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
name|page
operator|.
name|UniTimePageHeader
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
name|page
operator|.
name|UniTimeMenuBar
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
name|page
operator|.
name|UniTimeSideBar
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
name|page
operator|.
name|UniTimeVersion
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
name|ReservationTable
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
name|PeriodPreferencesWidget
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
name|RoomNoteChanges
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
name|RoomSharingWidget
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
name|CourseDetailsWidget
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
name|EnrollmentTable
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
name|ExaminationEnrollmentTable
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
name|SolverAllocatedMemory
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
name|widgets
operator|.
name|CourseNumbersSuggestBox
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
name|RootPanel
import|;
end_import

begin_comment
comment|/**  * Register GWT components here.  * @author Tomas Muller  *  */
end_comment

begin_enum
specifier|public
enum|enum
name|Components
block|{
name|courseCurricula
argument_list|(
literal|"UniTimeGWT:CourseCurricula"
argument_list|,
operator|new
name|ComponentFactory
argument_list|()
block|{
specifier|public
name|void
name|insert
parameter_list|(
name|RootPanel
name|panel
parameter_list|)
block|{
operator|new
name|CourseCurriculaTable
argument_list|(
literal|true
argument_list|,
literal|true
argument_list|)
operator|.
name|insert
argument_list|(
name|panel
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
block|,
name|title
argument_list|(
literal|"UniTimeGWT:Title"
argument_list|,
operator|new
name|ComponentFactory
argument_list|()
block|{
specifier|public
name|void
name|insert
parameter_list|(
name|RootPanel
name|panel
parameter_list|)
block|{
name|UniTimePageLabel
operator|.
name|getInstance
argument_list|()
operator|.
name|insert
argument_list|(
name|panel
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
block|,
name|sidebar_stack
argument_list|(
literal|"UniTimeGWT:SideStackMenu"
argument_list|,
operator|new
name|ComponentFactory
argument_list|()
block|{
specifier|public
name|void
name|insert
parameter_list|(
name|RootPanel
name|panel
parameter_list|)
block|{
operator|new
name|UniTimeSideBar
argument_list|(
literal|true
argument_list|,
literal|true
argument_list|)
operator|.
name|insert
argument_list|(
name|panel
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
block|,
name|sidebar_tree
argument_list|(
literal|"UniTimeGWT:SideTreeMenu"
argument_list|,
operator|new
name|ComponentFactory
argument_list|()
block|{
specifier|public
name|void
name|insert
parameter_list|(
name|RootPanel
name|panel
parameter_list|)
block|{
operator|new
name|UniTimeSideBar
argument_list|(
literal|false
argument_list|,
literal|true
argument_list|)
operator|.
name|insert
argument_list|(
name|panel
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
block|,
name|sidebar_stack_static
argument_list|(
literal|"UniTimeGWT:StaticSideStackMenu"
argument_list|,
operator|new
name|ComponentFactory
argument_list|()
block|{
specifier|public
name|void
name|insert
parameter_list|(
name|RootPanel
name|panel
parameter_list|)
block|{
operator|new
name|UniTimeSideBar
argument_list|(
literal|true
argument_list|,
literal|false
argument_list|)
operator|.
name|insert
argument_list|(
name|panel
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
block|,
name|sidebar_tree_static
argument_list|(
literal|"UniTimeGWT:StaticSideTreeMenu"
argument_list|,
operator|new
name|ComponentFactory
argument_list|()
block|{
specifier|public
name|void
name|insert
parameter_list|(
name|RootPanel
name|panel
parameter_list|)
block|{
operator|new
name|UniTimeSideBar
argument_list|(
literal|false
argument_list|,
literal|false
argument_list|)
operator|.
name|insert
argument_list|(
name|panel
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
block|,
name|menubar_static
argument_list|(
literal|"UniTimeGWT:TopMenu"
argument_list|,
operator|new
name|ComponentFactory
argument_list|()
block|{
specifier|public
name|void
name|insert
parameter_list|(
name|RootPanel
name|panel
parameter_list|)
block|{
operator|new
name|UniTimeMenuBar
argument_list|(
literal|false
argument_list|)
operator|.
name|insert
argument_list|(
name|panel
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
block|,
name|menubar_dynamic
argument_list|(
literal|"UniTimeGWT:DynamicTopMenu"
argument_list|,
operator|new
name|ComponentFactory
argument_list|()
block|{
specifier|public
name|void
name|insert
parameter_list|(
name|RootPanel
name|panel
parameter_list|)
block|{
operator|new
name|UniTimeMenuBar
argument_list|(
literal|true
argument_list|)
operator|.
name|insert
argument_list|(
name|panel
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
block|,
name|header
argument_list|(
literal|"UniTimeGWT:Header"
argument_list|,
operator|new
name|ComponentFactory
argument_list|()
block|{
specifier|public
name|void
name|insert
parameter_list|(
name|RootPanel
name|panel
parameter_list|)
block|{
name|UniTimePageHeader
operator|.
name|getInstance
argument_list|()
operator|.
name|insert
argument_list|(
name|panel
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
block|,
name|version
argument_list|(
literal|"UniTimeGWT:Version"
argument_list|,
operator|new
name|ComponentFactory
argument_list|()
block|{
specifier|public
name|void
name|insert
parameter_list|(
name|RootPanel
name|panel
parameter_list|)
block|{
operator|new
name|UniTimeVersion
argument_list|()
operator|.
name|insert
argument_list|(
name|panel
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
block|,
name|back
argument_list|(
literal|"UniTimeGWT:Back"
argument_list|,
operator|new
name|ComponentFactory
argument_list|()
block|{
specifier|public
name|void
name|insert
parameter_list|(
name|RootPanel
name|panel
parameter_list|)
block|{
operator|new
name|UniTimeBack
argument_list|()
operator|.
name|insert
argument_list|(
name|panel
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
block|,
name|offeringReservations
argument_list|(
literal|"UniTimeGWT:OfferingReservations"
argument_list|,
operator|new
name|ComponentFactory
argument_list|()
block|{
specifier|public
name|void
name|insert
parameter_list|(
name|RootPanel
name|panel
parameter_list|)
block|{
operator|new
name|ReservationTable
argument_list|(
literal|true
argument_list|,
literal|true
argument_list|)
operator|.
name|insert
argument_list|(
name|panel
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
block|,
name|offeringReservationsReadOnly
argument_list|(
literal|"UniTimeGWT:OfferingReservationsRO"
argument_list|,
operator|new
name|ComponentFactory
argument_list|()
block|{
specifier|public
name|void
name|insert
parameter_list|(
name|RootPanel
name|panel
parameter_list|)
block|{
operator|new
name|ReservationTable
argument_list|(
literal|false
argument_list|,
literal|true
argument_list|)
operator|.
name|insert
argument_list|(
name|panel
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
block|,
name|offeringEnrollments
argument_list|(
literal|"UniTimeGWT:OfferingEnrollments"
argument_list|,
operator|new
name|ComponentFactory
argument_list|()
block|{
specifier|public
name|void
name|insert
parameter_list|(
name|RootPanel
name|panel
parameter_list|)
block|{
operator|new
name|EnrollmentTable
argument_list|(
literal|true
argument_list|,
literal|true
argument_list|,
literal|true
argument_list|)
operator|.
name|insert
argument_list|(
name|panel
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
block|,
name|examEnrollments
argument_list|(
literal|"UniTimeGWT:ExamEnrollments"
argument_list|,
operator|new
name|ComponentFactory
argument_list|()
block|{
specifier|public
name|void
name|insert
parameter_list|(
name|RootPanel
name|panel
parameter_list|)
block|{
operator|new
name|ExaminationEnrollmentTable
argument_list|(
literal|true
argument_list|,
literal|true
argument_list|)
operator|.
name|insert
argument_list|(
name|panel
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
block|,
name|roomSharing
argument_list|(
literal|"UniTimeGWT:RoomSharingWidget"
argument_list|,
operator|new
name|ComponentFactory
argument_list|()
block|{
specifier|public
name|void
name|insert
parameter_list|(
name|RootPanel
name|panel
parameter_list|)
block|{
operator|new
name|RoomSharingWidget
argument_list|(
literal|false
argument_list|)
operator|.
name|insert
argument_list|(
name|panel
argument_list|,
literal|false
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
block|,
name|roomEventAvailability
argument_list|(
literal|"UniTimeGWT:RoomEventAvailabilityWidget"
argument_list|,
operator|new
name|ComponentFactory
argument_list|()
block|{
specifier|public
name|void
name|insert
parameter_list|(
name|RootPanel
name|panel
parameter_list|)
block|{
operator|new
name|RoomSharingWidget
argument_list|(
literal|false
argument_list|)
operator|.
name|insert
argument_list|(
name|panel
argument_list|,
literal|true
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
block|,
name|roomNoteChanges
argument_list|(
literal|"UniTimeGWT:RoomNoteChanges"
argument_list|,
operator|new
name|ComponentFactory
argument_list|()
block|{
specifier|public
name|void
name|insert
parameter_list|(
name|RootPanel
name|panel
parameter_list|)
block|{
operator|new
name|RoomNoteChanges
argument_list|()
operator|.
name|insert
argument_list|(
name|panel
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
block|,
name|instructorAvailability
argument_list|(
literal|"UniTimeGWT:InstructorAvailability"
argument_list|,
operator|new
name|ComponentFactory
argument_list|()
block|{
specifier|public
name|void
name|insert
parameter_list|(
name|RootPanel
name|panel
parameter_list|)
block|{
operator|new
name|InstructorAvailabilityWidget
argument_list|()
operator|.
name|insert
argument_list|(
name|panel
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
block|,
name|courseLink
argument_list|(
literal|"UniTimeGWT:CourseLink"
argument_list|,
literal|true
argument_list|,
operator|new
name|ComponentFactory
argument_list|()
block|{
specifier|public
name|void
name|insert
parameter_list|(
name|RootPanel
name|panel
parameter_list|)
block|{
operator|new
name|CourseDetailsWidget
argument_list|(
literal|true
argument_list|)
operator|.
name|insert
argument_list|(
name|panel
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
block|,
name|courseDetails
argument_list|(
literal|"UniTimeGWT:CourseDetails"
argument_list|,
operator|new
name|ComponentFactory
argument_list|()
block|{
specifier|public
name|void
name|insert
parameter_list|(
name|RootPanel
name|panel
parameter_list|)
block|{
operator|new
name|CourseDetailsWidget
argument_list|(
literal|false
argument_list|)
operator|.
name|insert
argument_list|(
name|panel
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
block|,
name|solverAllocatedMemory
argument_list|(
literal|"UniTimeGWT:SolverAllocatedMem"
argument_list|,
literal|true
argument_list|,
operator|new
name|ComponentFactory
argument_list|()
block|{
specifier|public
name|void
name|insert
parameter_list|(
name|RootPanel
name|panel
parameter_list|)
block|{
operator|new
name|SolverAllocatedMemory
argument_list|()
operator|.
name|insert
argument_list|(
name|panel
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
block|,
name|calendar
argument_list|(
literal|"UniTimeGWT:Calendar"
argument_list|,
literal|true
argument_list|,
operator|new
name|ComponentFactory
argument_list|()
block|{
specifier|public
name|void
name|insert
parameter_list|(
name|RootPanel
name|panel
parameter_list|)
block|{
name|SingleDateSelector
operator|.
name|insert
argument_list|(
name|panel
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
block|,
name|courseNumberSuggestions
argument_list|(
literal|"UniTimeGWT:CourseNumberSuggestBox"
argument_list|,
literal|true
argument_list|,
operator|new
name|ComponentFactory
argument_list|()
block|{
specifier|public
name|void
name|insert
parameter_list|(
name|RootPanel
name|panel
parameter_list|)
block|{
name|CourseNumbersSuggestBox
operator|.
name|insert
argument_list|(
name|panel
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
block|,
name|periodPreferences
argument_list|(
literal|"UniTimeGWT:PeriodPreferences"
argument_list|,
operator|new
name|ComponentFactory
argument_list|()
block|{
specifier|public
name|void
name|insert
parameter_list|(
name|RootPanel
name|panel
parameter_list|)
block|{
operator|new
name|PeriodPreferencesWidget
argument_list|(
literal|true
argument_list|)
operator|.
name|insert
argument_list|(
name|panel
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
block|,
name|teachingRequests
argument_list|(
literal|"UniTimeGWT:TeachingRequests"
argument_list|,
operator|new
name|ComponentFactory
argument_list|()
block|{
specifier|public
name|void
name|insert
parameter_list|(
name|RootPanel
name|panel
parameter_list|)
block|{
operator|new
name|TeachingRequestsWidget
argument_list|()
operator|.
name|insert
argument_list|(
name|panel
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
block|, 	;
specifier|private
name|String
name|iId
decl_stmt|;
specifier|private
name|ComponentFactory
name|iFactory
decl_stmt|;
specifier|private
name|boolean
name|iMultiple
init|=
literal|false
decl_stmt|;
name|Components
parameter_list|(
name|String
name|id
parameter_list|,
name|ComponentFactory
name|factory
parameter_list|)
block|{
name|iId
operator|=
name|id
expr_stmt|;
name|iFactory
operator|=
name|factory
expr_stmt|;
block|}
name|Components
parameter_list|(
name|String
name|id
parameter_list|,
name|boolean
name|multiple
parameter_list|,
name|ComponentFactory
name|factory
parameter_list|)
block|{
name|iId
operator|=
name|id
expr_stmt|;
name|iFactory
operator|=
name|factory
expr_stmt|;
name|iMultiple
operator|=
name|multiple
expr_stmt|;
block|}
specifier|public
name|String
name|id
parameter_list|()
block|{
return|return
name|iId
return|;
block|}
specifier|public
name|void
name|insert
parameter_list|(
name|RootPanel
name|panel
parameter_list|)
block|{
name|iFactory
operator|.
name|insert
argument_list|(
name|panel
argument_list|)
expr_stmt|;
block|}
specifier|public
name|boolean
name|isMultiple
parameter_list|()
block|{
return|return
name|iMultiple
return|;
block|}
specifier|public
interface|interface
name|ComponentFactory
block|{
name|void
name|insert
parameter_list|(
name|RootPanel
name|panel
parameter_list|)
function_decl|;
block|}
block|}
end_enum

end_unit

