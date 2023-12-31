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
name|reports
operator|.
name|pointintimedata
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
name|Date
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
name|org
operator|.
name|hibernate
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
name|PitClass
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
name|PointInTimeData
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

begin_class
specifier|public
class|class
name|WSCHBySubjectAreaDayOfWeekHourOfDay
extends|extends
name|WSCHByDayOfWeekAndHourOfDay
block|{
name|TreeSet
argument_list|<
name|SubjectArea
argument_list|>
name|subjects
init|=
operator|new
name|TreeSet
argument_list|<
name|SubjectArea
argument_list|>
argument_list|()
decl_stmt|;
annotation|@
name|Override
specifier|public
name|String
name|reportName
parameter_list|()
block|{
return|return
operator|(
name|MSG
operator|.
name|wseBySubjectAreaDayOfWeekAndHourOfDayReport
argument_list|()
operator|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|reportDescription
parameter_list|()
block|{
return|return
operator|(
name|MSG
operator|.
name|wseBySubjectAreaDayOfWeekAndHourOfDayReportNote
argument_list|()
operator|)
return|;
block|}
annotation|@
name|Override
specifier|protected
name|void
name|intializeHeader
parameter_list|()
block|{
name|ArrayList
argument_list|<
name|String
argument_list|>
name|hdr
init|=
operator|new
name|ArrayList
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
name|hdr
operator|.
name|add
argument_list|(
name|MSG
operator|.
name|columnSubjectArea
argument_list|()
argument_list|)
expr_stmt|;
name|hdr
operator|.
name|add
argument_list|(
name|MSG
operator|.
name|columnDayOfWeek
argument_list|()
argument_list|)
expr_stmt|;
name|addTimeColumns
argument_list|(
name|hdr
argument_list|)
expr_stmt|;
name|setHeader
argument_list|(
name|hdr
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|createRoomUtilizationReportFor
parameter_list|(
name|PointInTimeData
name|pointInTimeData
parameter_list|,
name|Session
name|hibSession
parameter_list|)
block|{
name|calculatePeriodsWithEnrollments
argument_list|(
name|pointInTimeData
argument_list|,
name|hibSession
argument_list|)
expr_stmt|;
name|int
name|minute
init|=
operator|(
name|startOnHalfHour
condition|?
literal|30
else|:
literal|0
operator|)
decl_stmt|;
for|for
control|(
name|SubjectArea
name|subjectArea
range|:
name|subjects
control|)
block|{
for|for
control|(
name|int
name|dayOfWeek
init|=
literal|1
init|;
name|dayOfWeek
operator|<
literal|8
condition|;
name|dayOfWeek
operator|++
control|)
block|{
name|ArrayList
argument_list|<
name|String
argument_list|>
name|row
init|=
operator|new
name|ArrayList
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
name|row
operator|.
name|add
argument_list|(
name|subjectArea
operator|.
name|getSubjectAreaAbbreviation
argument_list|()
argument_list|)
expr_stmt|;
name|row
operator|.
name|add
argument_list|(
name|getDayOfWeekLabel
argument_list|(
name|periodDayOfWeek
argument_list|(
name|dayOfWeek
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
for|for
control|(
name|int
name|hourOfDay
init|=
literal|0
init|;
name|hourOfDay
operator|<
literal|24
condition|;
name|hourOfDay
operator|++
control|)
block|{
name|String
name|key
init|=
name|getPeriodTag
argument_list|(
name|subjectArea
operator|.
name|getUniqueId
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|,
name|dayOfWeek
argument_list|,
name|hourOfDay
argument_list|,
name|minute
argument_list|)
decl_stmt|;
name|row
operator|.
name|add
argument_list|(
name|periodEnrollmentMap
operator|.
name|get
argument_list|(
name|key
argument_list|)
operator|==
literal|null
condition|?
literal|"0"
else|:
literal|""
operator|+
name|periodEnrollmentMap
operator|.
name|get
argument_list|(
name|key
argument_list|)
operator|.
name|getWeeklyStudentEnrollment
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|addDataRow
argument_list|(
name|row
argument_list|)
expr_stmt|;
block|}
block|}
block|}
annotation|@
name|SuppressWarnings
argument_list|(
literal|"unchecked"
argument_list|)
specifier|private
name|void
name|calculatePeriodsWithEnrollments
parameter_list|(
name|PointInTimeData
name|pointInTimeData
parameter_list|,
name|Session
name|hibSession
parameter_list|)
block|{
name|StringBuilder
name|sb
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|"select distinct pc"
argument_list|)
operator|.
name|append
argument_list|(
literal|"	from PitClass pc"
argument_list|)
operator|.
name|append
argument_list|(
literal|" inner join pc.pitClassEvents as pce"
argument_list|)
operator|.
name|append
argument_list|(
literal|" inner join pce.pitClassMeetings as pcm"
argument_list|)
operator|.
name|append
argument_list|(
literal|" inner join pcm.pitClassMeetingUtilPeriods as pcmup"
argument_list|)
operator|.
name|append
argument_list|(
literal|"	inner join pc.pitSchedulingSubpart.pitInstrOfferingConfig.pitInstructionalOffering.pitCourseOfferings as pco"
argument_list|)
operator|.
name|append
argument_list|(
literal|"	where pc.pitSchedulingSubpart.pitInstrOfferingConfig.pitInstructionalOffering.pointInTimeData.uniqueId = :sessId"
argument_list|)
operator|.
name|append
argument_list|(
literal|" and pco.subjectArea.uniqueId = :saId"
argument_list|)
operator|.
name|append
argument_list|(
literal|"	and pco.isControl = true"
argument_list|)
operator|.
name|append
argument_list|(
literal|" and  pc.pitSchedulingSubpart.itype.organized = true"
argument_list|)
expr_stmt|;
name|HashSet
argument_list|<
name|Long
argument_list|>
name|processedClasses
init|=
operator|new
name|HashSet
argument_list|<
name|Long
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|SubjectArea
name|subjectArea
range|:
name|pointInTimeData
operator|.
name|getSession
argument_list|()
operator|.
name|getSubjectAreas
argument_list|()
control|)
block|{
for|for
control|(
name|PitClass
name|pc
range|:
operator|(
name|List
argument_list|<
name|PitClass
argument_list|>
operator|)
name|hibSession
operator|.
name|createQuery
argument_list|(
name|sb
operator|.
name|toString
argument_list|()
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"sessId"
argument_list|,
name|pointInTimeData
operator|.
name|getUniqueId
argument_list|()
operator|.
name|longValue
argument_list|()
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"saId"
argument_list|,
name|subjectArea
operator|.
name|getUniqueId
argument_list|()
operator|.
name|longValue
argument_list|()
argument_list|)
operator|.
name|setCacheable
argument_list|(
literal|true
argument_list|)
operator|.
name|list
argument_list|()
control|)
block|{
if|if
condition|(
name|processedClasses
operator|.
name|contains
argument_list|(
name|pc
operator|.
name|getUniqueId
argument_list|()
argument_list|)
condition|)
block|{
continue|continue;
block|}
name|processedClasses
operator|.
name|add
argument_list|(
name|pc
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
name|subjects
operator|.
name|add
argument_list|(
name|subjectArea
argument_list|)
expr_stmt|;
for|for
control|(
name|Date
name|meetingPeriod
range|:
name|pc
operator|.
name|getUniquePeriods
argument_list|()
control|)
block|{
name|String
name|label
init|=
name|getPeriodTag
argument_list|(
name|subjectArea
operator|.
name|getUniqueId
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|,
name|meetingPeriod
argument_list|)
decl_stmt|;
name|PeriodEnrollment
name|pe
init|=
name|periodEnrollmentMap
operator|.
name|get
argument_list|(
name|label
argument_list|)
decl_stmt|;
if|if
condition|(
name|pe
operator|==
literal|null
condition|)
block|{
name|pe
operator|=
operator|new
name|PeriodEnrollment
argument_list|(
name|label
argument_list|,
name|getStandardMinutesInReportingHour
argument_list|()
argument_list|,
name|getStandardWeeksInReportingTerm
argument_list|()
argument_list|)
expr_stmt|;
name|periodEnrollmentMap
operator|.
name|put
argument_list|(
name|label
argument_list|,
name|pe
argument_list|)
expr_stmt|;
block|}
name|pe
operator|.
name|addEnrollment
argument_list|(
name|pc
operator|.
name|getEnrollment
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
block|}
end_class

end_unit

