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
operator|.
name|instructor
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
name|List
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
operator|.
name|InstructorAvailabilityModel
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
name|P
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
name|SimpleForm
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
name|UniTimeTable
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
name|UniTimeTableHeader
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
name|GwtConstants
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
name|StudentSectioningMessages
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
name|shared
operator|.
name|InstructorInterface
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
name|shared
operator|.
name|InstructorInterface
operator|.
name|ClassInfo
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
name|shared
operator|.
name|InstructorInterface
operator|.
name|InstructorInfo
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
name|shared
operator|.
name|InstructorInterface
operator|.
name|SectionInfo
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
name|shared
operator|.
name|InstructorInterface
operator|.
name|TeachingRequestInfo
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
name|shared
operator|.
name|InstructorInterface
operator|.
name|TeachingRequestsPagePropertiesResponse
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
name|core
operator|.
name|client
operator|.
name|GWT
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
name|event
operator|.
name|logical
operator|.
name|shared
operator|.
name|ValueChangeEvent
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
name|event
operator|.
name|logical
operator|.
name|shared
operator|.
name|ValueChangeHandler
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
name|event
operator|.
name|shared
operator|.
name|HandlerRegistration
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
name|i18n
operator|.
name|client
operator|.
name|NumberFormat
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
name|HTML
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
name|HasValue
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
name|Label
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
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|InstructorDetails
extends|extends
name|SimpleForm
implements|implements
name|HasValue
argument_list|<
name|Integer
argument_list|>
block|{
specifier|protected
specifier|static
specifier|final
name|GwtMessages
name|MESSAGES
init|=
name|GWT
operator|.
name|create
argument_list|(
name|GwtMessages
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|protected
specifier|static
specifier|final
name|GwtConstants
name|CONSTANTS
init|=
name|GWT
operator|.
name|create
argument_list|(
name|GwtConstants
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|protected
specifier|static
specifier|final
name|StudentSectioningMessages
name|SECTMSG
init|=
name|GWT
operator|.
name|create
argument_list|(
name|StudentSectioningMessages
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|protected
specifier|static
name|NumberFormat
name|sTeachingLoadFormat
init|=
name|NumberFormat
operator|.
name|getFormat
argument_list|(
name|CONSTANTS
operator|.
name|teachingLoadFormat
argument_list|()
argument_list|)
decl_stmt|;
specifier|protected
name|TeachingRequestsPagePropertiesResponse
name|iProperties
decl_stmt|;
specifier|private
name|InstructorExternalIdCell
name|iExternalId
decl_stmt|;
specifier|private
name|InstructorNameCell
name|iName
decl_stmt|;
specifier|private
name|Label
name|iAssignedLoad
decl_stmt|;
specifier|private
name|AttributesCell
name|iAttributes
decl_stmt|;
specifier|private
name|PreferenceCell
name|iCoursePrefs
decl_stmt|,
name|iDistPrefs
decl_stmt|;
specifier|private
name|InstructorAvailabilityWidget
name|iTimePrefs
decl_stmt|;
specifier|private
name|ObjectivesCell
name|iObjectives
decl_stmt|;
specifier|private
name|UniTimeTable
argument_list|<
name|ClassInfo
argument_list|>
name|iEnrollmentsTable
decl_stmt|;
specifier|private
name|UniTimeTable
argument_list|<
name|TeachingRequestInfo
argument_list|>
name|iRequestsTable
decl_stmt|;
specifier|private
name|int
name|iAttributesRow
decl_stmt|,
name|iCoursePrefsRow
decl_stmt|,
name|iEnrollmentsRow
decl_stmt|,
name|iDistPrefsRow
decl_stmt|,
name|iRequestsRow
decl_stmt|,
name|iObjectivesRow
decl_stmt|;
specifier|public
name|InstructorDetails
parameter_list|(
name|TeachingRequestsPagePropertiesResponse
name|properties
parameter_list|)
block|{
name|iProperties
operator|=
name|properties
expr_stmt|;
name|removeStyleName
argument_list|(
literal|"unitime-NotPrintableBottomLine"
argument_list|)
expr_stmt|;
name|addHeaderRow
argument_list|(
name|MESSAGES
operator|.
name|headerInstructor
argument_list|()
argument_list|)
expr_stmt|;
name|iExternalId
operator|=
operator|new
name|InstructorExternalIdCell
argument_list|(
name|properties
argument_list|)
expr_stmt|;
name|addRow
argument_list|(
name|MESSAGES
operator|.
name|propExternalId
argument_list|()
argument_list|,
name|iExternalId
argument_list|)
expr_stmt|;
name|iName
operator|=
operator|new
name|InstructorNameCell
argument_list|(
name|properties
argument_list|)
expr_stmt|;
name|addRow
argument_list|(
name|MESSAGES
operator|.
name|propInstructorName
argument_list|()
argument_list|,
name|iName
argument_list|)
expr_stmt|;
name|iAssignedLoad
operator|=
operator|new
name|Label
argument_list|()
expr_stmt|;
name|addRow
argument_list|(
name|MESSAGES
operator|.
name|propAssignedLoad
argument_list|()
argument_list|,
name|iAssignedLoad
argument_list|)
expr_stmt|;
name|iAttributes
operator|=
operator|new
name|AttributesCell
argument_list|()
expr_stmt|;
name|iAttributesRow
operator|=
name|addRow
argument_list|(
name|MESSAGES
operator|.
name|propInstructorAttributes
argument_list|()
argument_list|,
name|iAttributes
argument_list|)
expr_stmt|;
name|iCoursePrefs
operator|=
operator|new
name|PreferenceCell
argument_list|(
name|properties
argument_list|)
expr_stmt|;
name|iCoursePrefsRow
operator|=
name|addRow
argument_list|(
name|MESSAGES
operator|.
name|propCoursePreferences
argument_list|()
argument_list|,
name|iCoursePrefs
argument_list|)
expr_stmt|;
name|iTimePrefs
operator|=
operator|new
name|InstructorAvailabilityWidget
argument_list|()
expr_stmt|;
name|addRow
argument_list|(
name|MESSAGES
operator|.
name|propTimePreferences
argument_list|()
argument_list|,
name|iTimePrefs
argument_list|)
expr_stmt|;
name|iEnrollmentsTable
operator|=
operator|new
name|UniTimeTable
argument_list|<
name|ClassInfo
argument_list|>
argument_list|()
expr_stmt|;
name|iEnrollmentsTable
operator|.
name|addStyleName
argument_list|(
literal|"enrollments"
argument_list|)
expr_stmt|;
name|List
argument_list|<
name|UniTimeTableHeader
argument_list|>
name|enrlHeader
init|=
operator|new
name|ArrayList
argument_list|<
name|UniTimeTableHeader
argument_list|>
argument_list|()
decl_stmt|;
name|enrlHeader
operator|.
name|add
argument_list|(
operator|new
name|UniTimeTableHeader
argument_list|(
name|MESSAGES
operator|.
name|colCourse
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|enrlHeader
operator|.
name|add
argument_list|(
operator|new
name|UniTimeTableHeader
argument_list|(
name|MESSAGES
operator|.
name|colSection
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|enrlHeader
operator|.
name|add
argument_list|(
operator|new
name|UniTimeTableHeader
argument_list|(
name|MESSAGES
operator|.
name|colTime
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|enrlHeader
operator|.
name|add
argument_list|(
operator|new
name|UniTimeTableHeader
argument_list|(
name|MESSAGES
operator|.
name|colDate
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|enrlHeader
operator|.
name|add
argument_list|(
operator|new
name|UniTimeTableHeader
argument_list|(
name|MESSAGES
operator|.
name|colRoom
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|enrlHeader
operator|.
name|add
argument_list|(
operator|new
name|UniTimeTableHeader
argument_list|(
name|MESSAGES
operator|.
name|colRole
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|iEnrollmentsTable
operator|.
name|addRow
argument_list|(
literal|null
argument_list|,
name|enrlHeader
argument_list|)
expr_stmt|;
name|iEnrollmentsRow
operator|=
name|addRow
argument_list|(
name|MESSAGES
operator|.
name|propEnrollments
argument_list|()
argument_list|,
name|iEnrollmentsTable
argument_list|)
expr_stmt|;
name|iDistPrefs
operator|=
operator|new
name|PreferenceCell
argument_list|(
name|properties
argument_list|)
expr_stmt|;
name|iDistPrefsRow
operator|=
name|addRow
argument_list|(
name|MESSAGES
operator|.
name|propDistributionPreferences
argument_list|()
argument_list|,
name|iDistPrefs
argument_list|)
expr_stmt|;
name|iRequestsTable
operator|=
operator|new
name|UniTimeTable
argument_list|<
name|TeachingRequestInfo
argument_list|>
argument_list|()
expr_stmt|;
name|iRequestsTable
operator|.
name|addStyleName
argument_list|(
literal|"assignments"
argument_list|)
expr_stmt|;
name|List
argument_list|<
name|UniTimeTableHeader
argument_list|>
name|reqHeader
init|=
operator|new
name|ArrayList
argument_list|<
name|UniTimeTableHeader
argument_list|>
argument_list|()
decl_stmt|;
name|reqHeader
operator|.
name|add
argument_list|(
operator|new
name|UniTimeTableHeader
argument_list|(
name|MESSAGES
operator|.
name|colCourse
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|reqHeader
operator|.
name|add
argument_list|(
operator|new
name|UniTimeTableHeader
argument_list|(
name|MESSAGES
operator|.
name|colSection
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|reqHeader
operator|.
name|add
argument_list|(
operator|new
name|UniTimeTableHeader
argument_list|(
name|MESSAGES
operator|.
name|colTime
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|reqHeader
operator|.
name|add
argument_list|(
operator|new
name|UniTimeTableHeader
argument_list|(
name|MESSAGES
operator|.
name|colDate
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|reqHeader
operator|.
name|add
argument_list|(
operator|new
name|UniTimeTableHeader
argument_list|(
name|MESSAGES
operator|.
name|colRoom
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|reqHeader
operator|.
name|add
argument_list|(
operator|new
name|UniTimeTableHeader
argument_list|(
name|MESSAGES
operator|.
name|colTeachingLoad
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|reqHeader
operator|.
name|add
argument_list|(
operator|new
name|UniTimeTableHeader
argument_list|(
name|MESSAGES
operator|.
name|colAttributePreferences
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|reqHeader
operator|.
name|add
argument_list|(
operator|new
name|UniTimeTableHeader
argument_list|(
name|MESSAGES
operator|.
name|colInstructorPreferences
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|reqHeader
operator|.
name|add
argument_list|(
operator|new
name|UniTimeTableHeader
argument_list|(
name|MESSAGES
operator|.
name|colObjectives
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|iRequestsTable
operator|.
name|addRow
argument_list|(
literal|null
argument_list|,
name|reqHeader
argument_list|)
expr_stmt|;
name|iRequestsRow
operator|=
name|addRow
argument_list|(
name|MESSAGES
operator|.
name|propAssignments
argument_list|()
argument_list|,
name|iRequestsTable
argument_list|)
expr_stmt|;
name|iObjectives
operator|=
operator|new
name|ObjectivesCell
argument_list|(
name|properties
argument_list|)
expr_stmt|;
name|iObjectivesRow
operator|=
name|addRow
argument_list|(
name|MESSAGES
operator|.
name|propObjectives
argument_list|()
argument_list|,
name|iObjectives
argument_list|)
expr_stmt|;
name|iRequestsTable
operator|.
name|setAllowSelection
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|iRequestsTable
operator|.
name|setAllowMultiSelect
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|iRequestsTable
operator|.
name|addMouseClickListener
argument_list|(
operator|new
name|UniTimeTable
operator|.
name|MouseClickListener
argument_list|<
name|InstructorInterface
operator|.
name|TeachingRequestInfo
argument_list|>
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|onMouseClick
parameter_list|(
name|UniTimeTable
operator|.
name|TableEvent
argument_list|<
name|InstructorInterface
operator|.
name|TeachingRequestInfo
argument_list|>
name|event
parameter_list|)
block|{
if|if
condition|(
name|event
operator|.
name|getRow
argument_list|()
operator|>
literal|0
condition|)
block|{
name|iRequestsTable
operator|.
name|setSelected
argument_list|(
name|event
operator|.
name|getRow
argument_list|()
argument_list|,
literal|true
argument_list|)
expr_stmt|;
name|ValueChangeEvent
operator|.
name|fire
argument_list|(
name|InstructorDetails
operator|.
name|this
argument_list|,
name|event
operator|.
name|getRow
argument_list|()
operator|-
literal|1
argument_list|)
expr_stmt|;
block|}
block|}
block|}
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|setInstructor
parameter_list|(
name|InstructorInfo
name|instructor
parameter_list|)
block|{
name|iExternalId
operator|.
name|setValue
argument_list|(
name|instructor
argument_list|)
expr_stmt|;
name|iName
operator|.
name|setValue
argument_list|(
name|instructor
argument_list|)
expr_stmt|;
name|iAssignedLoad
operator|.
name|setText
argument_list|(
name|sTeachingLoadFormat
operator|.
name|format
argument_list|(
name|instructor
operator|.
name|getAssignedLoad
argument_list|()
argument_list|)
operator|+
literal|" / "
operator|+
name|sTeachingLoadFormat
operator|.
name|format
argument_list|(
name|instructor
operator|.
name|getMaxLoad
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|iAttributes
operator|.
name|clear
argument_list|()
expr_stmt|;
if|if
condition|(
name|instructor
operator|.
name|getAttributes
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|getRowFormatter
argument_list|()
operator|.
name|setVisible
argument_list|(
name|iAttributesRow
argument_list|,
literal|false
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|iAttributes
operator|.
name|setValue
argument_list|(
name|instructor
operator|.
name|getAttributes
argument_list|()
argument_list|)
expr_stmt|;
name|getRowFormatter
argument_list|()
operator|.
name|setVisible
argument_list|(
name|iAttributesRow
argument_list|,
literal|true
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|instructor
operator|.
name|getCoursePreferences
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|iCoursePrefs
operator|.
name|clear
argument_list|()
expr_stmt|;
name|getRowFormatter
argument_list|()
operator|.
name|setVisible
argument_list|(
name|iCoursePrefsRow
argument_list|,
literal|false
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|iCoursePrefs
operator|.
name|setValue
argument_list|(
name|instructor
operator|.
name|getCoursePreferences
argument_list|()
argument_list|)
expr_stmt|;
name|getRowFormatter
argument_list|()
operator|.
name|setVisible
argument_list|(
name|iCoursePrefsRow
argument_list|,
literal|true
argument_list|)
expr_stmt|;
block|}
name|InstructorAvailabilityModel
name|model
init|=
name|iProperties
operator|.
name|getInstructorAvailabilityModel
argument_list|()
decl_stmt|;
name|model
operator|.
name|setPattern
argument_list|(
name|instructor
operator|.
name|getAvailability
argument_list|()
argument_list|)
expr_stmt|;
name|iTimePrefs
operator|.
name|setValue
argument_list|(
name|model
argument_list|)
expr_stmt|;
if|if
condition|(
name|instructor
operator|.
name|getDistributionPreferences
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|iDistPrefs
operator|.
name|clear
argument_list|()
expr_stmt|;
name|getRowFormatter
argument_list|()
operator|.
name|setVisible
argument_list|(
name|iDistPrefsRow
argument_list|,
literal|false
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|iDistPrefs
operator|.
name|setValue
argument_list|(
name|instructor
operator|.
name|getDistributionPreferences
argument_list|()
argument_list|)
expr_stmt|;
name|getRowFormatter
argument_list|()
operator|.
name|setVisible
argument_list|(
name|iDistPrefsRow
argument_list|,
literal|true
argument_list|)
expr_stmt|;
block|}
name|iEnrollmentsTable
operator|.
name|clearTable
argument_list|(
literal|1
argument_list|)
expr_stmt|;
if|if
condition|(
name|instructor
operator|.
name|getEnrollments
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|getRowFormatter
argument_list|()
operator|.
name|setVisible
argument_list|(
name|iEnrollmentsRow
argument_list|,
literal|false
argument_list|)
expr_stmt|;
block|}
else|else
block|{
for|for
control|(
name|ClassInfo
name|e
range|:
name|instructor
operator|.
name|getEnrollments
argument_list|()
control|)
block|{
name|List
argument_list|<
name|Widget
argument_list|>
name|line
init|=
operator|new
name|ArrayList
argument_list|<
name|Widget
argument_list|>
argument_list|()
decl_stmt|;
name|line
operator|.
name|add
argument_list|(
operator|new
name|Label
argument_list|(
name|e
operator|.
name|getCourse
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|line
operator|.
name|add
argument_list|(
operator|new
name|Label
argument_list|(
name|e
operator|.
name|getType
argument_list|()
operator|+
operator|(
name|e
operator|.
name|getExternalId
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
literal|" "
operator|+
name|e
operator|.
name|getExternalId
argument_list|()
operator|)
argument_list|)
argument_list|)
expr_stmt|;
name|line
operator|.
name|add
argument_list|(
operator|new
name|HTML
argument_list|(
name|e
operator|.
name|getTime
argument_list|()
operator|==
literal|null
condition|?
name|SECTMSG
operator|.
name|arrangeHours
argument_list|()
else|:
name|e
operator|.
name|getTime
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|line
operator|.
name|add
argument_list|(
operator|new
name|HTML
argument_list|(
name|e
operator|.
name|getDate
argument_list|()
operator|==
literal|null
condition|?
name|SECTMSG
operator|.
name|noDate
argument_list|()
else|:
name|e
operator|.
name|getDate
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|line
operator|.
name|add
argument_list|(
operator|new
name|HTML
argument_list|(
name|e
operator|.
name|getRoom
argument_list|()
operator|==
literal|null
condition|?
name|SECTMSG
operator|.
name|noRoom
argument_list|()
else|:
name|e
operator|.
name|getRoom
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|line
operator|.
name|add
argument_list|(
operator|new
name|Label
argument_list|(
name|e
operator|.
name|isInstructor
argument_list|()
condition|?
name|MESSAGES
operator|.
name|enrollmentRoleInstructor
argument_list|()
else|:
name|MESSAGES
operator|.
name|enrollmentRoleStudent
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|iEnrollmentsTable
operator|.
name|addRow
argument_list|(
name|e
argument_list|,
name|line
argument_list|)
expr_stmt|;
block|}
name|getRowFormatter
argument_list|()
operator|.
name|setVisible
argument_list|(
name|iEnrollmentsRow
argument_list|,
literal|true
argument_list|)
expr_stmt|;
block|}
name|iRequestsTable
operator|.
name|clearTable
argument_list|(
literal|1
argument_list|)
expr_stmt|;
name|iObjectives
operator|.
name|clear
argument_list|()
expr_stmt|;
if|if
condition|(
name|instructor
operator|.
name|getAssignedRequests
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|getRowFormatter
argument_list|()
operator|.
name|setVisible
argument_list|(
name|iRequestsRow
argument_list|,
literal|false
argument_list|)
expr_stmt|;
name|getRowFormatter
argument_list|()
operator|.
name|setVisible
argument_list|(
name|iObjectivesRow
argument_list|,
literal|false
argument_list|)
expr_stmt|;
block|}
else|else
block|{
for|for
control|(
name|TeachingRequestInfo
name|request
range|:
name|instructor
operator|.
name|getAssignedRequests
argument_list|()
control|)
block|{
name|List
argument_list|<
name|Widget
argument_list|>
name|line
init|=
operator|new
name|ArrayList
argument_list|<
name|Widget
argument_list|>
argument_list|()
decl_stmt|;
name|P
name|course
init|=
operator|new
name|P
argument_list|(
literal|"course"
argument_list|)
decl_stmt|;
name|course
operator|.
name|setText
argument_list|(
name|request
operator|.
name|getCourse
argument_list|()
operator|.
name|getCourseName
argument_list|()
argument_list|)
expr_stmt|;
name|line
operator|.
name|add
argument_list|(
name|course
argument_list|)
expr_stmt|;
name|P
name|section
init|=
operator|new
name|P
argument_list|(
literal|"sections"
argument_list|)
decl_stmt|,
name|time
init|=
operator|new
name|P
argument_list|(
literal|"times"
argument_list|)
decl_stmt|,
name|date
init|=
operator|new
name|P
argument_list|(
literal|"dates"
argument_list|)
decl_stmt|,
name|room
init|=
operator|new
name|P
argument_list|(
literal|"rooms"
argument_list|)
decl_stmt|;
for|for
control|(
name|SectionInfo
name|s
range|:
name|request
operator|.
name|getSections
argument_list|()
control|)
block|{
name|P
name|p
init|=
operator|new
name|P
argument_list|(
literal|"section"
argument_list|)
decl_stmt|;
name|p
operator|.
name|setText
argument_list|(
name|s
operator|.
name|getSectionType
argument_list|()
operator|+
operator|(
name|s
operator|.
name|getExternalId
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
literal|" "
operator|+
name|s
operator|.
name|getExternalId
argument_list|()
operator|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|s
operator|.
name|isCommon
argument_list|()
condition|)
name|p
operator|.
name|addStyleName
argument_list|(
literal|"common"
argument_list|)
expr_stmt|;
name|section
operator|.
name|add
argument_list|(
name|p
argument_list|)
expr_stmt|;
name|P
name|t
init|=
operator|new
name|P
argument_list|(
literal|"time"
argument_list|)
decl_stmt|;
name|t
operator|.
name|setHTML
argument_list|(
name|s
operator|.
name|getTime
argument_list|()
operator|==
literal|null
condition|?
name|SECTMSG
operator|.
name|arrangeHours
argument_list|()
else|:
name|s
operator|.
name|getTime
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|s
operator|.
name|isCommon
argument_list|()
condition|)
name|t
operator|.
name|addStyleName
argument_list|(
literal|"common"
argument_list|)
expr_stmt|;
name|time
operator|.
name|add
argument_list|(
name|t
argument_list|)
expr_stmt|;
name|P
name|d
init|=
operator|new
name|P
argument_list|(
literal|"date"
argument_list|)
decl_stmt|;
name|d
operator|.
name|setHTML
argument_list|(
name|s
operator|.
name|getDate
argument_list|()
operator|==
literal|null
condition|?
name|SECTMSG
operator|.
name|noDate
argument_list|()
else|:
name|s
operator|.
name|getDate
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|s
operator|.
name|isCommon
argument_list|()
condition|)
name|d
operator|.
name|addStyleName
argument_list|(
literal|"common"
argument_list|)
expr_stmt|;
name|date
operator|.
name|add
argument_list|(
name|d
argument_list|)
expr_stmt|;
name|P
name|r
init|=
operator|new
name|P
argument_list|(
literal|"room"
argument_list|)
decl_stmt|;
name|r
operator|.
name|setHTML
argument_list|(
name|s
operator|.
name|getRoom
argument_list|()
operator|==
literal|null
condition|?
name|SECTMSG
operator|.
name|noRoom
argument_list|()
else|:
name|s
operator|.
name|getRoom
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|s
operator|.
name|isCommon
argument_list|()
condition|)
name|r
operator|.
name|addStyleName
argument_list|(
literal|"common"
argument_list|)
expr_stmt|;
name|room
operator|.
name|add
argument_list|(
name|r
argument_list|)
expr_stmt|;
block|}
name|line
operator|.
name|add
argument_list|(
name|section
argument_list|)
expr_stmt|;
name|line
operator|.
name|add
argument_list|(
name|time
argument_list|)
expr_stmt|;
name|line
operator|.
name|add
argument_list|(
name|date
argument_list|)
expr_stmt|;
name|line
operator|.
name|add
argument_list|(
name|room
argument_list|)
expr_stmt|;
name|line
operator|.
name|add
argument_list|(
operator|new
name|Label
argument_list|(
name|sTeachingLoadFormat
operator|.
name|format
argument_list|(
name|request
operator|.
name|getLoad
argument_list|()
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|line
operator|.
name|add
argument_list|(
operator|new
name|PreferenceCell
argument_list|(
name|iProperties
argument_list|,
name|request
operator|.
name|getAttributePreferences
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|line
operator|.
name|add
argument_list|(
operator|new
name|PreferenceCell
argument_list|(
name|iProperties
argument_list|,
name|request
operator|.
name|getInstructorPreferences
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|line
operator|.
name|add
argument_list|(
operator|new
name|ObjectivesCell
argument_list|(
name|iProperties
argument_list|,
name|request
operator|.
name|getValues
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|iRequestsTable
operator|.
name|addRow
argument_list|(
name|request
argument_list|,
name|line
argument_list|)
expr_stmt|;
block|}
name|iObjectives
operator|.
name|setValue
argument_list|(
name|instructor
operator|.
name|getValues
argument_list|()
argument_list|)
expr_stmt|;
name|getRowFormatter
argument_list|()
operator|.
name|setVisible
argument_list|(
name|iRequestsRow
argument_list|,
literal|true
argument_list|)
expr_stmt|;
name|getRowFormatter
argument_list|()
operator|.
name|setVisible
argument_list|(
name|iObjectivesRow
argument_list|,
literal|true
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|HandlerRegistration
name|addValueChangeHandler
parameter_list|(
name|ValueChangeHandler
argument_list|<
name|Integer
argument_list|>
name|handler
parameter_list|)
block|{
return|return
name|addHandler
argument_list|(
name|handler
argument_list|,
name|ValueChangeEvent
operator|.
name|getType
argument_list|()
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|Integer
name|getValue
parameter_list|()
block|{
name|int
name|row
init|=
name|iRequestsTable
operator|.
name|getSelectedRow
argument_list|()
decl_stmt|;
if|if
condition|(
name|row
operator|<
literal|1
condition|)
return|return
literal|null
return|;
return|return
name|row
operator|-
literal|1
return|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|setValue
parameter_list|(
name|Integer
name|value
parameter_list|)
block|{
name|setValue
argument_list|(
name|value
argument_list|,
literal|false
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|setValue
parameter_list|(
name|Integer
name|value
parameter_list|,
name|boolean
name|fireEvents
parameter_list|)
block|{
if|if
condition|(
name|value
operator|==
literal|null
condition|)
block|{
name|int
name|row
init|=
name|iRequestsTable
operator|.
name|getSelectedRow
argument_list|()
decl_stmt|;
if|if
condition|(
name|row
operator|>=
literal|0
condition|)
name|iRequestsTable
operator|.
name|setSelected
argument_list|(
name|row
argument_list|,
literal|false
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|iRequestsTable
operator|.
name|setSelected
argument_list|(
name|value
operator|+
literal|1
argument_list|,
literal|true
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|fireEvents
condition|)
name|ValueChangeEvent
operator|.
name|fire
argument_list|(
name|this
argument_list|,
name|getValue
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit
