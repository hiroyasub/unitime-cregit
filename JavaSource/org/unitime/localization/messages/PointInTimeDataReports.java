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
name|localization
operator|.
name|messages
package|;
end_package

begin_comment
comment|/**  * @author Stephanie Schluttenhofer  */
end_comment

begin_interface
specifier|public
interface|interface
name|PointInTimeDataReports
extends|extends
name|Messages
block|{
annotation|@
name|DefaultMessage
argument_list|(
literal|"Weekly Student Class Hour Report For Department By Class"
argument_list|)
name|String
name|deptWSCHReportAllHoursForDepartmentByClass
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"This report lists organized, not organized and total class hours for each class that occur between the session start date and the session class end date for the selected department.<br>"
argument_list|)
name|String
name|deptWSCBReportAllHoursForDepartmentByClassNote
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Weekly Student Class Hour Report For Department By Class and Instructor"
argument_list|)
name|String
name|deptWSCHReportAllHoursForDepartmentByClassAndInstructor
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"This report lists organized, not organized and total class hours for each class and instructor that occur between the session start date and the session class end date for the selected department.<br>"
argument_list|)
name|String
name|deptWSCBReportAllHoursForDepartmentByClassAndInstructorNote
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Weekly Student Class Hour Report For Department By Instructor Position"
argument_list|)
name|String
name|deptWSCHReportAllHoursForDepartmentByPosition
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"This report lists organized, not organized and total class hours and student class hours for instructor position that occur between the session start date and the session class end date for the selected departments.<br>"
argument_list|)
name|String
name|deptWSCBReportAllHoursForDepartmentByPositionNote
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Weekly Student Class Hour Report For Department By Instructor"
argument_list|)
name|String
name|deptWSCHReportAllHoursForDepartmentByInstructor
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"This report lists organized, not organized and total class hours and student class hours for instructor that occur between the session start date and the session class end date for the selected departments.<br>"
argument_list|)
name|String
name|deptWSCHReportAllHoursForDepartmentByInstructorNote
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Departmental Weekly Student Class Hour Report"
argument_list|)
name|String
name|deptWSCHReportAllHours
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"This report lists organized, not organized and total class and student class hours for each department that occur between the session start date and the session class end date.<br><br><b>Note:</b> this report will take a very long time to run.<br>"
argument_list|)
name|String
name|deptWSCHReportAllHoursNote
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Room Utilization"
argument_list|)
name|String
name|roomUtilizationReport
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"This report lists organized, not organized and total room and student class hours for each location that is used between the session start date and the session class end date.<br><b>Note:</b> this report may take a very long time to run when selecting all departments and room types.<br>"
argument_list|)
name|String
name|roomUtilizationReportNote
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Room Type Utilization by Department"
argument_list|)
name|String
name|roomTypeUtilizationByDepartmentReport
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"This report lists organized, not organized and total room and student class hours for each room type controled by a department that is used between the session start date and the session class end date.<br><b>Note:</b> this report may take a very long time to run when selecting all departments and room types.<br>"
argument_list|)
name|String
name|roomTypeUtilizationByDepartmentReportNote
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Weekly Student Enrollment by Day Of Week and Period"
argument_list|)
name|String
name|wseByDayOfWeekAndPeriodReport
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"This report lists organized, not organized and total student enrollments for each period collectively for all classes that meet the report criteria that fall between the session start date and the session class end date.<br><br><b>Note:</b> this report may take a very long time to run when selecting all departments and room types.<br>"
argument_list|)
name|String
name|wseByDayOfWeekAndPeriodReportNote
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Organized Weekly Student Class Hours by Day Of Week and Hour of Day"
argument_list|)
name|String
name|wseByDayOfWeekAndHourOfDayReport
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"This report lists organized student class hours for each hour in the day collectively for all classes that fall between the session start date and the session class end date.<br><br><b>Note:</b> this report will take a very long time to run.<br>"
argument_list|)
name|String
name|wseByDayOfWeekAndHourOfDayReportNote
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Organized Weekly Student Class Hours by Building by Day Of Week and Hour of Day"
argument_list|)
name|String
name|wseByBuildingDayOfWeekAndHourOfDayReport
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"This report lists organized student class hours by building for each hour in the day collectively for all classes that fall between the session start date and the session class end date.<br><br><b>Note:</b> this report will take a very long time to run.<br>"
argument_list|)
name|String
name|wseByBuildingDayOfWeekAndHourOfDayReportNote
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Weekly Student Class Hours by Instructional Type by Day Of Week and Hour of Day"
argument_list|)
name|String
name|wseByItypeDayOfWeekAndHourOfDayReport
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"This report lists student class hours by instructional type for each hour in the day collectively for all classes that fall between the session start date and the session class end date.<br><br><b>Note:</b> this report will take a very long time to run.<br>"
argument_list|)
name|String
name|wseByItypeDayOfWeekAndHourOfDayReportNote
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Organized Weekly Student Class Hours by Department by Day Of Week and Hour of Day"
argument_list|)
name|String
name|wseByDeptDayOfWeekAndHourOfDayReport
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"This report lists organized student class hours by department for each hour in the day collectively for all classes that fall between the session start date and the session class end date.<br><br><b>Note:</b> this report will take a very long time to run.<br>"
argument_list|)
name|String
name|wseByDeptDayOfWeekAndHourOfDayReportNote
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Organized Weekly Student Class Hours by Subject Area by Day Of Week and Hour of Day"
argument_list|)
name|String
name|wseBySubjectAreaDayOfWeekAndHourOfDayReport
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"This report lists organized student class hours by subject area for each hour in the day collectively for all classes that fall between the session start date and the session class end date.<br><br><b>Note:</b> this report will take a very long time to run.<br>"
argument_list|)
name|String
name|wseBySubjectAreaDayOfWeekAndHourOfDayReportNote
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Department Code"
argument_list|)
name|String
name|columnDepartmentCode
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Department Abbreviation"
argument_list|)
name|String
name|columnDepartmentAbbreviation
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Department Name"
argument_list|)
name|String
name|columnDepartmentName
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Room Department Code"
argument_list|)
name|String
name|columnRoomDepartmentCode
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Room Department Abbr"
argument_list|)
name|String
name|columnRoomDepartmentAbbreviation
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Room Department Name"
argument_list|)
name|String
name|columnRoomDepartmentName
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Building / Location"
argument_list|)
name|String
name|columnBuilding
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Room"
argument_list|)
name|String
name|columnRoom
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Room Type"
argument_list|)
name|String
name|columnRoomType
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Subject Area"
argument_list|)
name|String
name|columnSubjectArea
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Course Number"
argument_list|)
name|String
name|columnCourseNumber
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Instructional Type"
argument_list|)
name|String
name|columnItype
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Organized"
argument_list|)
name|String
name|columnOrganized
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Section Number"
argument_list|)
name|String
name|columnSectionNumber
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"External Id"
argument_list|)
name|String
name|columnExternalId
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Weekly Room Hours"
argument_list|)
name|String
name|columnWeeklyRoomHours
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Weekly Class Hours"
argument_list|)
name|String
name|columnWeeklyClassHours
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Weekly Student Enrollment Per Period"
argument_list|)
name|String
name|columnWeeklyStudentEnrollmentPerPeriod
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Weekly Student Enrollment Per Hour"
argument_list|)
name|String
name|columnWeeklyStudentEnrollmentPerHour
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Weekly Student Class Hours"
argument_list|)
name|String
name|columnWeeklyStudentClassHours
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Organized Weekly Room Hours"
argument_list|)
name|String
name|columnOrganizedWeeklyRoomHours
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Organized Weekly Class Hours"
argument_list|)
name|String
name|columnOrganizedWeeklyClassHours
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Organized Weekly Student Class Hours"
argument_list|)
name|String
name|columnOrganizedWeeklyStudentClassHours
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Organized Weekly Student Enrollment Per Period"
argument_list|)
name|String
name|columnOrganizedWeeklyStudentEnrollmentPerPeriod
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Organized Weekly Student Enrollment Per Hour"
argument_list|)
name|String
name|columnOrganizedWeeklyStudentEnrollmentPerHour
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Not Organized Weekly Room Hours"
argument_list|)
name|String
name|columnNotOrganizedWeeklyRoomHours
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Not Organized Weekly Class Hours"
argument_list|)
name|String
name|columnNotOrganizedWeeklyClassHours
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Not Organized Weekly Student Class Hours"
argument_list|)
name|String
name|columnNotOrganizedWeeklyStudentClassHours
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Not Organized Weekly Student Enrollment Per Period"
argument_list|)
name|String
name|columnNotOrganizedWeeklyStudentEnrollmentPerPeriod
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Not Organized Weekly Student Enrollment Per Hour"
argument_list|)
name|String
name|columnNotOrganizedWeeklyStudentEnrollmentPerHour
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Instructor"
argument_list|)
name|String
name|columnInstructor
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"External Id"
argument_list|)
name|String
name|columnInstructorExternalId
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Position"
argument_list|)
name|String
name|columnPosition
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Number of Class Meetings"
argument_list|)
name|String
name|columnNumberOfClassMeetings
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Day of Week"
argument_list|)
name|String
name|columnDayOfWeek
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Period"
argument_list|)
name|String
name|columnPeriod
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Total"
argument_list|)
name|String
name|labelTotal
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Unknown"
argument_list|)
name|String
name|labelUnknown
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Capacity"
argument_list|)
name|String
name|columnCapacity
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Station Hours"
argument_list|)
name|String
name|columnStationHours
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Occupancy"
argument_list|)
name|String
name|columnOccupancy
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Normalized Percent Share"
argument_list|)
name|String
name|columnNormalizedPercentShare
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Gradable Itype Credit"
argument_list|)
name|String
name|columnGradableItypeCredit
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

