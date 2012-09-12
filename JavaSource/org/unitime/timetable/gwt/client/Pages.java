begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.2 (University Timetabling Application)  * Copyright (C) 2010, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
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
name|test
operator|.
name|OnlineSectioningTest
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
literal|"Curricula"
argument_list|,
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
block|}
argument_list|)
block|,
name|curprojrules
argument_list|(
literal|"Curriculum Projection Rules"
argument_list|,
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
block|}
argument_list|)
block|,
name|sectioning
argument_list|(
literal|"Student Scheduling Assistant"
argument_list|,
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
block|}
argument_list|)
block|,
name|requests
argument_list|(
literal|"Student Course Requests"
argument_list|,
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
block|}
argument_list|)
block|,
name|admin
argument_list|(
literal|"Administration"
argument_list|,
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
block|}
argument_list|)
block|,
name|events
argument_list|(
literal|"Events"
argument_list|,
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
block|}
argument_list|)
block|,
name|timetable
argument_list|(
literal|"Event Timetable"
argument_list|,
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
block|}
argument_list|)
block|,
name|roomtable
argument_list|(
literal|"Room Timetable"
argument_list|,
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
block|}
argument_list|)
block|,
name|reservation
argument_list|(
literal|"Reservation"
argument_list|,
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
block|}
argument_list|)
block|,
name|reservations
argument_list|(
literal|"Reservations"
argument_list|,
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
block|}
argument_list|)
block|,
name|sectioningtest
argument_list|(
literal|"Online Student Sectioning Test"
argument_list|,
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
block|}
argument_list|)
block|,
name|hql
argument_list|(
literal|"Simple Reports"
argument_list|,
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
block|}
argument_list|)
block|,
name|onlinesctdash
argument_list|(
literal|"Online Student Scheduling Dashboard"
argument_list|,
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
block|}
argument_list|)
block|,
name|batchsctdash
argument_list|(
literal|"Student Sectioning Dashboard"
argument_list|,
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
block|}
argument_list|)
block|,
name|traveltimes
argument_list|(
literal|"Travel Times"
argument_list|,
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
block|}
argument_list|)
block|,
name|classes
argument_list|(
literal|"Classes"
argument_list|,
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
block|}
argument_list|)
block|,
name|exams
argument_list|(
literal|"Examinations"
argument_list|,
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
block|}
argument_list|)
block|,
name|personal
argument_list|(
literal|"Personal Timetable"
argument_list|,
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
block|}
argument_list|)
block|,
name|roomavailability
argument_list|(
literal|"Edit Room Availability"
argument_list|,
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
block|}
argument_list|)
block|, 	;
specifier|private
name|String
name|iTitle
decl_stmt|;
specifier|private
name|PageFactory
name|iFactory
decl_stmt|;
name|Pages
parameter_list|(
name|String
name|title
parameter_list|,
name|PageFactory
name|factory
parameter_list|)
block|{
name|iTitle
operator|=
name|title
expr_stmt|;
name|iFactory
operator|=
name|factory
expr_stmt|;
block|}
specifier|public
name|String
name|title
parameter_list|()
block|{
return|return
name|iTitle
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
block|}
block|}
end_enum

end_unit

