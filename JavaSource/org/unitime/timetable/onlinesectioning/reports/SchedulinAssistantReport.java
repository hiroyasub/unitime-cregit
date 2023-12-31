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
name|reports
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|File
import|;
end_import

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
name|Set
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
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|dao
operator|.
name|StudentDAO
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
name|OnlineSectioningLog
operator|.
name|Action
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
name|OnlineSectioningLog
operator|.
name|Enrollment
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
name|OnlineSectioningLog
operator|.
name|Enrollment
operator|.
name|EnrollmentType
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
name|OnlineSectioningLog
operator|.
name|Section
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|SchedulinAssistantReport
implements|implements
name|OnlineSectioningReport
operator|.
name|Report
block|{
annotation|@
name|Override
specifier|public
name|String
name|getYear
parameter_list|()
block|{
return|return
name|System
operator|.
name|getProperty
argument_list|(
literal|"year"
argument_list|,
literal|"2012"
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getTerm
parameter_list|()
block|{
return|return
name|System
operator|.
name|getProperty
argument_list|(
literal|"term"
argument_list|,
literal|"Fall"
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getCampus
parameter_list|()
block|{
return|return
name|System
operator|.
name|getProperty
argument_list|(
literal|"campus"
argument_list|,
literal|"PWL"
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|File
name|getReportFolder
parameter_list|()
block|{
return|return
operator|new
name|File
argument_list|(
name|System
operator|.
name|getProperty
argument_list|(
literal|"user.home"
argument_list|,
literal|"~"
argument_list|)
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
index|[]
name|getExcludeUsers
parameter_list|()
block|{
return|return
name|System
operator|.
name|getProperty
argument_list|(
literal|"exclude"
argument_list|,
literal|"TEST"
argument_list|)
operator|.
name|split
argument_list|(
literal|","
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getLastTimeStamp
parameter_list|()
block|{
return|return
name|System
operator|.
name|getProperty
argument_list|(
literal|"before"
argument_list|,
literal|null
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
index|[]
name|getOperations
parameter_list|()
block|{
return|return
operator|new
name|String
index|[]
block|{
literal|"section"
block|,
literal|"suggestions"
block|,
literal|"reload-student"
block|}
return|;
block|}
specifier|public
name|double
name|diff
parameter_list|(
name|Enrollment
name|e1
parameter_list|,
name|Enrollment
name|e2
parameter_list|)
block|{
if|if
condition|(
name|e1
operator|.
name|getSectionCount
argument_list|()
operator|==
literal|0
condition|)
return|return
name|e2
operator|.
name|getSectionCount
argument_list|()
operator|==
literal|0
condition|?
literal|1.0
else|:
literal|0.0
return|;
name|double
name|cnt
init|=
literal|0.0
decl_stmt|;
for|for
control|(
name|Section
name|s1
range|:
name|e1
operator|.
name|getSectionList
argument_list|()
control|)
block|{
name|boolean
name|match
init|=
literal|false
decl_stmt|;
for|for
control|(
name|Section
name|s2
range|:
name|e2
operator|.
name|getSectionList
argument_list|()
control|)
block|{
if|if
condition|(
name|s1
operator|.
name|getClazz
argument_list|()
operator|.
name|getExternalId
argument_list|()
operator|.
name|equals
argument_list|(
name|s2
operator|.
name|getClazz
argument_list|()
operator|.
name|getExternalId
argument_list|()
argument_list|)
condition|)
block|{
name|match
operator|=
literal|true
expr_stmt|;
break|break;
block|}
block|}
if|if
condition|(
name|match
condition|)
name|cnt
operator|+=
literal|1.0
expr_stmt|;
block|}
return|return
name|cnt
operator|/
name|Math
operator|.
name|max
argument_list|(
name|e1
operator|.
name|getSectionCount
argument_list|()
argument_list|,
name|e2
operator|.
name|getSectionCount
argument_list|()
argument_list|)
return|;
block|}
specifier|public
name|double
name|diff
parameter_list|(
name|List
argument_list|<
name|Enrollment
argument_list|>
name|computed
parameter_list|,
name|Enrollment
name|saved
parameter_list|)
block|{
name|double
name|diff
init|=
literal|0.0
decl_stmt|;
for|for
control|(
name|Enrollment
name|e
range|:
name|computed
control|)
name|diff
operator|=
name|Math
operator|.
name|max
argument_list|(
name|diff
argument_list|,
name|diff
argument_list|(
name|e
argument_list|,
name|saved
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|diff
return|;
block|}
specifier|public
name|double
name|diff
parameter_list|(
name|Enrollment
name|e1
parameter_list|,
name|Set
argument_list|<
name|Long
argument_list|>
name|e2
parameter_list|)
block|{
if|if
condition|(
name|e1
operator|.
name|getSectionCount
argument_list|()
operator|==
literal|0
condition|)
return|return
name|e2
operator|.
name|size
argument_list|()
operator|==
literal|0
condition|?
literal|1.0
else|:
literal|0.0
return|;
name|double
name|cnt
init|=
literal|0.0
decl_stmt|;
for|for
control|(
name|Section
name|s1
range|:
name|e1
operator|.
name|getSectionList
argument_list|()
control|)
block|{
if|if
condition|(
name|e2
operator|.
name|contains
argument_list|(
name|s1
operator|.
name|getClazz
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|)
condition|)
name|cnt
operator|++
expr_stmt|;
block|}
return|return
name|cnt
operator|/
name|Math
operator|.
name|max
argument_list|(
name|e1
operator|.
name|getSectionCount
argument_list|()
argument_list|,
name|e2
operator|.
name|size
argument_list|()
argument_list|)
return|;
block|}
specifier|public
name|double
name|diff
parameter_list|(
name|List
argument_list|<
name|Enrollment
argument_list|>
name|computed
parameter_list|,
name|Set
argument_list|<
name|Long
argument_list|>
name|enrollment
parameter_list|)
block|{
name|double
name|diff
init|=
literal|0.0
decl_stmt|;
for|for
control|(
name|Enrollment
name|e
range|:
name|computed
control|)
name|diff
operator|=
name|Math
operator|.
name|max
argument_list|(
name|diff
argument_list|,
name|diff
argument_list|(
name|e
argument_list|,
name|enrollment
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|diff
return|;
block|}
specifier|private
name|double
name|minutes
parameter_list|(
name|Action
name|a1
parameter_list|,
name|Action
name|a2
parameter_list|)
block|{
return|return
name|Math
operator|.
name|abs
argument_list|(
name|a1
operator|.
name|getStartTime
argument_list|()
operator|-
name|a2
operator|.
name|getStartTime
argument_list|()
argument_list|)
operator|/
literal|60000.0
return|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|process
parameter_list|(
name|OnlineSectioningReport
name|report
parameter_list|,
name|String
name|student
parameter_list|,
name|List
argument_list|<
name|Action
argument_list|>
name|actions
parameter_list|)
block|{
name|boolean
name|hasSection
init|=
literal|false
decl_stmt|;
for|for
control|(
name|Action
name|action
range|:
name|actions
control|)
block|{
if|if
condition|(
literal|"section"
operator|.
name|equals
argument_list|(
name|action
operator|.
name|getOperation
argument_list|()
argument_list|)
condition|)
block|{
name|hasSection
operator|=
literal|true
expr_stmt|;
break|break;
block|}
block|}
if|if
condition|(
operator|!
name|hasSection
condition|)
return|return;
name|long
name|done
init|=
name|Math
operator|.
name|round
argument_list|(
name|report
operator|.
name|inc
argument_list|(
literal|"Students"
argument_list|,
literal|1.0
argument_list|)
argument_list|)
decl_stmt|;
name|Action
name|firstSection
init|=
literal|null
decl_stmt|;
name|Action
name|lastSectionOrSuggestion
init|=
literal|null
decl_stmt|;
name|int
name|nrSections
init|=
literal|0
decl_stmt|;
name|int
name|nrSuggestions
init|=
literal|0
decl_stmt|;
name|int
name|nrCycles
init|=
literal|0
decl_stmt|;
name|Set
argument_list|<
name|Long
argument_list|>
name|enrollment
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
name|Number
name|classId
range|:
operator|(
name|List
argument_list|<
name|Number
argument_list|>
operator|)
name|StudentDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select e.clazz.uniqueId from StudentClassEnrollment e where e.student.uniqueId = :studentId"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"studentId"
argument_list|,
name|actions
operator|.
name|get
argument_list|(
literal|0
argument_list|)
operator|.
name|getStudent
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|)
operator|.
name|list
argument_list|()
control|)
name|enrollment
operator|.
name|add
argument_list|(
name|classId
operator|.
name|longValue
argument_list|()
argument_list|)
expr_stmt|;
name|List
argument_list|<
name|Enrollment
argument_list|>
name|last
init|=
operator|new
name|ArrayList
argument_list|<
name|Enrollment
argument_list|>
argument_list|()
decl_stmt|;
name|List
argument_list|<
name|Enrollment
argument_list|>
name|all
init|=
operator|new
name|ArrayList
argument_list|<
name|Enrollment
argument_list|>
argument_list|()
decl_stmt|;
name|Enrollment
name|first
init|=
literal|null
decl_stmt|;
name|Enrollment
name|lastBanner
init|=
literal|null
decl_stmt|;
name|Action
name|previous
init|=
literal|null
decl_stmt|;
for|for
control|(
name|Action
name|action
range|:
name|actions
control|)
block|{
if|if
condition|(
name|previous
operator|!=
literal|null
operator|&&
name|minutes
argument_list|(
name|previous
argument_list|,
name|action
argument_list|)
operator|>
literal|120.0
condition|)
block|{
name|firstSection
operator|=
literal|null
expr_stmt|;
name|lastSectionOrSuggestion
operator|=
literal|null
expr_stmt|;
name|nrSections
operator|=
literal|0
expr_stmt|;
name|nrSuggestions
operator|=
literal|0
expr_stmt|;
name|first
operator|=
literal|null
expr_stmt|;
name|last
operator|.
name|clear
argument_list|()
expr_stmt|;
block|}
name|previous
operator|=
name|action
expr_stmt|;
if|if
condition|(
literal|"section"
operator|.
name|equals
argument_list|(
name|action
operator|.
name|getOperation
argument_list|()
argument_list|)
condition|)
block|{
name|Enrollment
name|computed
init|=
literal|null
decl_stmt|;
for|for
control|(
name|Enrollment
name|e
range|:
name|action
operator|.
name|getEnrollmentList
argument_list|()
control|)
block|{
if|if
condition|(
name|e
operator|.
name|getType
argument_list|()
operator|==
name|EnrollmentType
operator|.
name|COMPUTED
operator|&&
name|e
operator|.
name|getSectionCount
argument_list|()
operator|>
literal|0
condition|)
block|{
name|computed
operator|=
name|e
expr_stmt|;
break|break;
block|}
block|}
if|if
condition|(
name|computed
operator|==
literal|null
condition|)
continue|continue;
if|if
condition|(
name|first
operator|==
literal|null
condition|)
name|first
operator|=
name|computed
expr_stmt|;
name|last
operator|.
name|clear
argument_list|()
expr_stmt|;
name|last
operator|.
name|add
argument_list|(
name|computed
argument_list|)
expr_stmt|;
name|all
operator|.
name|add
argument_list|(
name|computed
argument_list|)
expr_stmt|;
name|nrSections
operator|++
expr_stmt|;
name|lastSectionOrSuggestion
operator|=
name|action
expr_stmt|;
if|if
condition|(
name|firstSection
operator|==
literal|null
condition|)
name|firstSection
operator|=
name|action
expr_stmt|;
block|}
if|if
condition|(
literal|"suggestions"
operator|.
name|equals
argument_list|(
name|action
operator|.
name|getOperation
argument_list|()
argument_list|)
condition|)
block|{
name|Enrollment
name|computed
init|=
literal|null
decl_stmt|;
for|for
control|(
name|Enrollment
name|e
range|:
name|action
operator|.
name|getEnrollmentList
argument_list|()
control|)
block|{
if|if
condition|(
name|e
operator|.
name|getType
argument_list|()
operator|==
name|EnrollmentType
operator|.
name|COMPUTED
operator|&&
name|e
operator|.
name|getSectionCount
argument_list|()
operator|>
literal|0
condition|)
block|{
name|computed
operator|=
name|e
expr_stmt|;
name|last
operator|.
name|add
argument_list|(
name|e
argument_list|)
expr_stmt|;
name|all
operator|.
name|add
argument_list|(
name|e
argument_list|)
expr_stmt|;
block|}
block|}
if|if
condition|(
name|computed
operator|==
literal|null
condition|)
continue|continue;
name|nrSuggestions
operator|++
expr_stmt|;
name|lastSectionOrSuggestion
operator|=
name|action
expr_stmt|;
block|}
if|if
condition|(
literal|"reload-student"
operator|.
name|equals
argument_list|(
name|action
operator|.
name|getOperation
argument_list|()
argument_list|)
condition|)
block|{
if|if
condition|(
name|firstSection
operator|==
literal|null
condition|)
continue|continue;
name|Enrollment
name|stored
init|=
literal|null
decl_stmt|;
for|for
control|(
name|Enrollment
name|e
range|:
name|action
operator|.
name|getEnrollmentList
argument_list|()
control|)
block|{
if|if
condition|(
name|e
operator|.
name|getType
argument_list|()
operator|==
name|EnrollmentType
operator|.
name|STORED
operator|&&
name|e
operator|.
name|getSectionCount
argument_list|()
operator|>
literal|0
condition|)
block|{
name|stored
operator|=
name|e
expr_stmt|;
break|break;
block|}
block|}
if|if
condition|(
name|stored
operator|==
literal|null
condition|)
continue|continue;
name|lastBanner
operator|=
name|stored
expr_stmt|;
name|report
operator|.
name|inc
argument_list|(
literal|"Sections per cycle"
argument_list|,
name|nrSections
argument_list|)
expr_stmt|;
name|report
operator|.
name|inc
argument_list|(
literal|"Suggestions per cycle"
argument_list|,
name|nrSuggestions
argument_list|)
expr_stmt|;
name|double
name|assistantTime
init|=
name|minutes
argument_list|(
name|lastSectionOrSuggestion
argument_list|,
name|firstSection
argument_list|)
decl_stmt|;
name|double
name|assistantDiff
init|=
name|diff
argument_list|(
name|last
argument_list|,
name|first
argument_list|)
decl_stmt|;
if|if
condition|(
name|assistantDiff
operator|>
literal|0.0
condition|)
block|{
name|report
operator|.
name|inc
argument_list|(
literal|"Assistant time [min]"
argument_list|,
name|assistantTime
argument_list|)
expr_stmt|;
name|report
operator|.
name|inc
argument_list|(
literal|"Difference [first - last]"
argument_list|,
name|assistantDiff
argument_list|)
expr_stmt|;
block|}
name|double
name|bannerTime
init|=
name|minutes
argument_list|(
name|action
argument_list|,
name|lastSectionOrSuggestion
argument_list|)
decl_stmt|;
name|double
name|bannerDiff
init|=
name|diff
argument_list|(
name|last
argument_list|,
name|stored
argument_list|)
decl_stmt|;
if|if
condition|(
name|bannerDiff
operator|>
literal|0.0
condition|)
block|{
name|report
operator|.
name|inc
argument_list|(
literal|"Banner time [min]"
argument_list|,
name|bannerTime
argument_list|)
expr_stmt|;
name|report
operator|.
name|inc
argument_list|(
literal|"Difference [last - Banner]"
argument_list|,
name|bannerDiff
argument_list|)
expr_stmt|;
block|}
name|double
name|bannerDiff2
init|=
name|diff
argument_list|(
name|all
argument_list|,
name|stored
argument_list|)
decl_stmt|;
if|if
condition|(
name|bannerDiff2
operator|>
literal|0.0
condition|)
name|report
operator|.
name|inc
argument_list|(
literal|"Difference [all - Banner]"
argument_list|,
name|bannerDiff2
argument_list|)
expr_stmt|;
name|firstSection
operator|=
literal|null
expr_stmt|;
name|lastSectionOrSuggestion
operator|=
literal|null
expr_stmt|;
name|first
operator|=
literal|null
expr_stmt|;
name|last
operator|.
name|clear
argument_list|()
expr_stmt|;
name|nrSections
operator|=
literal|0
expr_stmt|;
name|nrSuggestions
operator|=
literal|0
expr_stmt|;
name|nrCycles
operator|++
expr_stmt|;
block|}
block|}
name|report
operator|.
name|inc
argument_list|(
literal|"Cycles per student"
argument_list|,
name|nrCycles
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
name|all
operator|.
name|isEmpty
argument_list|()
operator|&&
operator|!
name|enrollment
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|report
operator|.
name|inc
argument_list|(
literal|"Difference [last - enrollment]"
argument_list|,
name|diff
argument_list|(
name|all
argument_list|,
name|enrollment
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|nrCycles
operator|>
literal|0
condition|)
name|report
operator|.
name|inc
argument_list|(
literal|"Difference [last - enrollment] (cycles> 0)"
argument_list|,
name|diff
argument_list|(
name|all
argument_list|,
name|enrollment
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|lastBanner
operator|!=
literal|null
operator|&&
operator|!
name|all
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|report
operator|.
name|inc
argument_list|(
literal|"Difference [all - last Banner]"
argument_list|,
name|diff
argument_list|(
name|all
argument_list|,
name|lastBanner
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
operator|(
name|done
operator|%
literal|1
operator|)
operator|==
literal|0
condition|)
block|{
name|OnlineSectioningReport
operator|.
name|sLog
operator|.
name|info
argument_list|(
literal|"---- after "
operator|+
name|done
operator|+
literal|" students"
argument_list|)
expr_stmt|;
for|for
control|(
name|String
name|name
range|:
operator|new
name|TreeSet
argument_list|<
name|String
argument_list|>
argument_list|(
name|report
operator|.
name|iCounters
operator|.
name|keySet
argument_list|()
argument_list|)
control|)
block|{
name|OnlineSectioningReport
operator|.
name|sLog
operator|.
name|info
argument_list|(
name|name
operator|+
literal|": "
operator|+
name|report
operator|.
name|iCounters
operator|.
name|get
argument_list|(
name|name
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
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
operator|new
name|OnlineSectioningReport
argument_list|(
operator|new
name|SchedulinAssistantReport
argument_list|()
argument_list|)
operator|.
name|run
argument_list|()
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

