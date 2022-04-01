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
name|onlinesectioning
operator|.
name|basic
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
name|ApplicationProperties
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
name|defaults
operator|.
name|ApplicationProperty
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
name|StudentSchedulingPreferencesInterface
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
name|onlinesectioning
operator|.
name|OnlineSectioningAction
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
name|onlinesectioning
operator|.
name|OnlineSectioningHelper
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
name|onlinesectioning
operator|.
name|OnlineSectioningServer
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
name|onlinesectioning
operator|.
name|model
operator|.
name|XStudent
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
name|onlinesectioning
operator|.
name|server
operator|.
name|CheckMaster
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
name|onlinesectioning
operator|.
name|server
operator|.
name|CheckMaster
operator|.
name|Master
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
annotation|@
name|CheckMaster
argument_list|(
name|Master
operator|.
name|REQUIRED
argument_list|)
specifier|public
class|class
name|GetStudentPreferences
implements|implements
name|OnlineSectioningAction
argument_list|<
name|StudentSchedulingPreferencesInterface
argument_list|>
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
name|Long
name|iStudentId
init|=
literal|null
decl_stmt|;
specifier|public
name|GetStudentPreferences
name|forStudent
parameter_list|(
name|Long
name|studentId
parameter_list|)
block|{
name|iStudentId
operator|=
name|studentId
expr_stmt|;
return|return
name|this
return|;
block|}
annotation|@
name|Override
specifier|public
name|StudentSchedulingPreferencesInterface
name|execute
parameter_list|(
name|OnlineSectioningServer
name|server
parameter_list|,
name|OnlineSectioningHelper
name|helper
parameter_list|)
block|{
name|StudentSchedulingPreferencesInterface
name|ret
init|=
operator|new
name|StudentSchedulingPreferencesInterface
argument_list|()
decl_stmt|;
name|ApplicationProperties
operator|.
name|setSessionId
argument_list|(
name|server
operator|.
name|getAcademicSession
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
name|ret
operator|.
name|setAllowClassDates
argument_list|(
name|ApplicationProperty
operator|.
name|OnlineSchedulingStudentPreferencesDatesAllowed
operator|.
name|isTrue
argument_list|()
argument_list|)
expr_stmt|;
name|ret
operator|.
name|setAllowRequireOnline
argument_list|(
name|ApplicationProperty
operator|.
name|OnlineSchedulingStudentPreferencesReqOnlineAllowed
operator|.
name|isTrue
argument_list|()
argument_list|)
expr_stmt|;
name|ret
operator|.
name|setCustomNote
argument_list|(
name|ApplicationProperty
operator|.
name|OnlineSchedulingStudentPreferencesNote
operator|.
name|value
argument_list|()
argument_list|)
expr_stmt|;
name|XStudent
name|student
init|=
name|server
operator|.
name|getStudent
argument_list|(
name|iStudentId
argument_list|)
decl_stmt|;
if|if
condition|(
name|student
operator|!=
literal|null
condition|)
block|{
name|ret
operator|.
name|setClassModality
argument_list|(
name|student
operator|.
name|getPreferredClassModality
argument_list|()
argument_list|)
expr_stmt|;
name|ret
operator|.
name|setScheduleGaps
argument_list|(
name|student
operator|.
name|getPreferredScheduleGaps
argument_list|()
argument_list|)
expr_stmt|;
name|ret
operator|.
name|setClassDateFrom
argument_list|(
name|student
operator|.
name|getClassStartDate
argument_list|(
name|server
operator|.
name|getAcademicSession
argument_list|()
operator|.
name|getDatePatternFirstDate
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|ret
operator|.
name|setClassDateTo
argument_list|(
name|student
operator|.
name|getClassEndDate
argument_list|(
name|server
operator|.
name|getAcademicSession
argument_list|()
operator|.
name|getDatePatternFirstDate
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
return|return
name|ret
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|name
parameter_list|()
block|{
return|return
literal|"get-schedule-prefs"
return|;
block|}
block|}
end_class

end_unit
