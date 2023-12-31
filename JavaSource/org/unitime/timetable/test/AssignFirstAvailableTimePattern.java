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
name|test
package|;
end_package

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
name|apache
operator|.
name|commons
operator|.
name|logging
operator|.
name|Log
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|logging
operator|.
name|LogFactory
import|;
end_import

begin_import
import|import
name|org
operator|.
name|unitime
operator|.
name|commons
operator|.
name|hibernate
operator|.
name|util
operator|.
name|HibernateUtil
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
name|model
operator|.
name|PreferenceLevel
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
name|TimePattern
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
name|TimePref
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
name|_RootDAO
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|AssignFirstAvailableTimePattern
block|{
specifier|protected
specifier|static
name|Log
name|sLog
init|=
name|LogFactory
operator|.
name|getLog
argument_list|(
name|AssignFirstAvailableTimePattern
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|public
specifier|static
name|void
name|main
parameter_list|(
name|String
index|[]
name|args
parameter_list|)
block|{
try|try
block|{
name|HibernateUtil
operator|.
name|configureHibernate
argument_list|(
name|ApplicationProperties
operator|.
name|getProperties
argument_list|()
argument_list|)
expr_stmt|;
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
init|=
operator|new
name|_RootDAO
argument_list|()
operator|.
name|getSession
argument_list|()
decl_stmt|;
name|Session
name|session
init|=
name|Session
operator|.
name|getSessionUsingInitiativeYearTerm
argument_list|(
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"initiative"
argument_list|,
literal|"MUNI FI"
argument_list|)
argument_list|,
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"year"
argument_list|,
literal|"2010"
argument_list|)
argument_list|,
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"term"
argument_list|,
literal|"Podzim"
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|session
operator|==
literal|null
condition|)
block|{
name|sLog
operator|.
name|error
argument_list|(
literal|"Academic session not found, use properties initiative, year, and term to set academic session."
argument_list|)
expr_stmt|;
name|System
operator|.
name|exit
argument_list|(
literal|0
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|sLog
operator|.
name|info
argument_list|(
literal|"Session: "
operator|+
name|session
argument_list|)
expr_stmt|;
block|}
for|for
control|(
name|SchedulingSubpart
name|s
range|:
operator|(
name|List
argument_list|<
name|SchedulingSubpart
argument_list|>
operator|)
name|hibSession
operator|.
name|createQuery
argument_list|(
literal|"select distinct s from SchedulingSubpart s inner join s.instrOfferingConfig.instructionalOffering.courseOfferings co where "
operator|+
literal|"co.subjectArea.department.session.uniqueId = :sessionId"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"sessionId"
argument_list|,
name|session
operator|.
name|getUniqueId
argument_list|()
argument_list|)
operator|.
name|list
argument_list|()
control|)
block|{
if|if
condition|(
name|s
operator|.
name|getTimePreferences
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|List
argument_list|<
name|TimePattern
argument_list|>
name|patterns
init|=
name|TimePattern
operator|.
name|findApplicable
argument_list|(
name|session
argument_list|,
literal|false
argument_list|,
literal|false
argument_list|,
literal|false
argument_list|,
name|s
operator|.
name|getMinutesPerWk
argument_list|()
argument_list|,
name|s
operator|.
name|effectiveDatePattern
argument_list|()
argument_list|,
name|s
operator|.
name|getInstrOfferingConfig
argument_list|()
operator|.
name|getDurationModel
argument_list|()
argument_list|,
literal|null
argument_list|)
decl_stmt|;
if|if
condition|(
name|patterns
operator|.
name|isEmpty
argument_list|()
condition|)
continue|continue;
name|TimePattern
name|pattern
init|=
name|patterns
operator|.
name|get
argument_list|(
literal|0
argument_list|)
decl_stmt|;
name|TimePref
name|tp
init|=
operator|new
name|TimePref
argument_list|()
decl_stmt|;
name|tp
operator|.
name|setPrefLevel
argument_list|(
name|PreferenceLevel
operator|.
name|getPreferenceLevel
argument_list|(
name|PreferenceLevel
operator|.
name|sRequired
argument_list|)
argument_list|)
expr_stmt|;
name|tp
operator|.
name|setTimePatternModel
argument_list|(
name|pattern
operator|.
name|getTimePatternModel
argument_list|()
argument_list|)
expr_stmt|;
name|tp
operator|.
name|setOwner
argument_list|(
name|s
argument_list|)
expr_stmt|;
name|hibSession
operator|.
name|save
argument_list|(
name|tp
argument_list|)
expr_stmt|;
name|sLog
operator|.
name|info
argument_list|(
name|s
operator|.
name|getInstrOfferingConfig
argument_list|()
operator|.
name|getInstructionalOffering
argument_list|()
operator|.
name|getControllingCourseOffering
argument_list|()
operator|.
name|getCourseName
argument_list|()
operator|+
literal|" "
operator|+
name|s
operator|.
name|getItypeDesc
argument_list|()
operator|+
literal|" := "
operator|+
name|pattern
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
name|hibSession
operator|.
name|flush
argument_list|()
expr_stmt|;
name|sLog
operator|.
name|info
argument_list|(
literal|"All done."
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|e
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

