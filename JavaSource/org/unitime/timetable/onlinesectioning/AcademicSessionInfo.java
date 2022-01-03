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
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|Serializable
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|BitSet
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
name|security
operator|.
name|Qualifiable
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
name|util
operator|.
name|Constants
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
name|util
operator|.
name|DateUtils
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|AcademicSessionInfo
implements|implements
name|Comparable
argument_list|<
name|AcademicSessionInfo
argument_list|>
implements|,
name|Serializable
implements|,
name|Qualifiable
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
name|iUniqueId
decl_stmt|;
specifier|private
name|String
name|iYear
decl_stmt|,
name|iTerm
decl_stmt|,
name|iCampus
decl_stmt|;
specifier|private
name|Long
name|iDatePatternId
init|=
literal|null
decl_stmt|;
specifier|private
name|BitSet
name|iWeekPattern
init|=
literal|null
decl_stmt|;
specifier|private
name|BitSet
name|iFreeTimePattern
init|=
literal|null
decl_stmt|;
specifier|private
name|Date
name|iSessionBegin
init|=
literal|null
decl_stmt|;
specifier|private
name|Date
name|iDatePatternFirstDate
init|=
literal|null
decl_stmt|;
specifier|private
name|boolean
name|iSectioningEnabled
init|=
literal|false
decl_stmt|;
specifier|private
name|int
name|iWkEnroll
init|=
literal|1
decl_stmt|,
name|iWkChange
init|=
literal|1
decl_stmt|,
name|iWkDrop
init|=
literal|4
decl_stmt|;
specifier|private
name|String
name|iDefaultStatus
init|=
literal|null
decl_stmt|;
specifier|private
name|String
name|iDefaultInstructionalMethod
init|=
literal|null
decl_stmt|;
specifier|private
name|int
name|iDayOfWeekOffset
decl_stmt|;
specifier|private
name|Date
name|iDefaultStartDate
init|=
literal|null
decl_stmt|,
name|iDefaultEndDate
init|=
literal|null
decl_stmt|;
specifier|public
name|AcademicSessionInfo
parameter_list|(
name|Session
name|session
parameter_list|)
block|{
name|update
argument_list|(
name|session
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|update
parameter_list|(
name|Session
name|session
parameter_list|)
block|{
name|iUniqueId
operator|=
name|session
operator|.
name|getUniqueId
argument_list|()
expr_stmt|;
name|iYear
operator|=
name|session
operator|.
name|getAcademicYear
argument_list|()
expr_stmt|;
name|iTerm
operator|=
name|session
operator|.
name|getAcademicTerm
argument_list|()
expr_stmt|;
name|iCampus
operator|=
name|session
operator|.
name|getAcademicInitiative
argument_list|()
expr_stmt|;
if|if
condition|(
name|session
operator|.
name|getDefaultDatePattern
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|iDatePatternId
operator|=
name|session
operator|.
name|getDefaultDatePattern
argument_list|()
operator|.
name|getUniqueId
argument_list|()
expr_stmt|;
name|iWeekPattern
operator|=
name|session
operator|.
name|getDefaultDatePattern
argument_list|()
operator|.
name|getPatternBitSet
argument_list|()
expr_stmt|;
name|iDefaultStartDate
operator|=
name|session
operator|.
name|getDefaultDatePattern
argument_list|()
operator|.
name|getStartDate
argument_list|()
expr_stmt|;
name|iDefaultEndDate
operator|=
name|session
operator|.
name|getDefaultDatePattern
argument_list|()
operator|.
name|getEndDate
argument_list|()
expr_stmt|;
block|}
else|else
block|{
name|iDefaultStartDate
operator|=
name|session
operator|.
name|getSessionBeginDateTime
argument_list|()
expr_stmt|;
name|iDefaultEndDate
operator|=
name|session
operator|.
name|getClassesEndDateTime
argument_list|()
expr_stmt|;
block|}
name|iFreeTimePattern
operator|=
name|getFreeTimeBitSet
argument_list|(
name|session
argument_list|)
expr_stmt|;
name|iSessionBegin
operator|=
name|session
operator|.
name|getSessionBeginDateTime
argument_list|()
expr_stmt|;
name|iDatePatternFirstDate
operator|=
name|getDatePatternFirstDay
argument_list|(
name|session
argument_list|)
expr_stmt|;
name|iDayOfWeekOffset
operator|=
name|Constants
operator|.
name|getDayOfWeek
argument_list|(
name|iDatePatternFirstDate
argument_list|)
expr_stmt|;
name|iSectioningEnabled
operator|=
name|session
operator|.
name|getStatusType
argument_list|()
operator|.
name|canOnlineSectionStudents
argument_list|()
expr_stmt|;
name|iWkEnroll
operator|=
name|session
operator|.
name|getLastWeekToEnroll
argument_list|()
expr_stmt|;
name|iWkChange
operator|=
name|session
operator|.
name|getLastWeekToChange
argument_list|()
expr_stmt|;
name|iWkDrop
operator|=
name|session
operator|.
name|getLastWeekToDrop
argument_list|()
expr_stmt|;
name|iDefaultStatus
operator|=
name|session
operator|.
name|getDefaultSectioningStatus
argument_list|()
operator|==
literal|null
condition|?
literal|null
else|:
name|session
operator|.
name|getDefaultSectioningStatus
argument_list|()
operator|.
name|getReference
argument_list|()
expr_stmt|;
name|iDefaultInstructionalMethod
operator|=
name|session
operator|.
name|getDefaultInstructionalMethod
argument_list|()
operator|==
literal|null
condition|?
literal|null
else|:
name|session
operator|.
name|getDefaultInstructionalMethod
argument_list|()
operator|.
name|getReference
argument_list|()
expr_stmt|;
block|}
specifier|public
name|AcademicSessionInfo
parameter_list|(
name|Long
name|uniqueId
parameter_list|,
name|String
name|year
parameter_list|,
name|String
name|term
parameter_list|,
name|String
name|campus
parameter_list|)
block|{
name|iUniqueId
operator|=
name|uniqueId
expr_stmt|;
name|iYear
operator|=
name|year
expr_stmt|;
name|iTerm
operator|=
name|term
expr_stmt|;
name|iCampus
operator|=
name|campus
expr_stmt|;
block|}
specifier|public
specifier|static
name|Date
name|getDatePatternFirstDay
parameter_list|(
name|Session
name|s
parameter_list|)
block|{
return|return
name|DateUtils
operator|.
name|getDate
argument_list|(
literal|1
argument_list|,
name|s
operator|.
name|getPatternStartMonth
argument_list|()
argument_list|,
name|s
operator|.
name|getSessionStartYear
argument_list|()
argument_list|)
return|;
block|}
specifier|public
specifier|static
name|BitSet
name|getFreeTimeBitSet
parameter_list|(
name|Session
name|session
parameter_list|)
block|{
name|int
name|startMonth
init|=
name|session
operator|.
name|getPatternStartMonth
argument_list|()
decl_stmt|;
name|int
name|endMonth
init|=
name|session
operator|.
name|getPatternEndMonth
argument_list|()
decl_stmt|;
name|int
name|size
init|=
name|DateUtils
operator|.
name|getDayOfYear
argument_list|(
literal|0
argument_list|,
name|endMonth
operator|+
literal|1
argument_list|,
name|session
operator|.
name|getSessionStartYear
argument_list|()
argument_list|)
operator|-
name|DateUtils
operator|.
name|getDayOfYear
argument_list|(
literal|1
argument_list|,
name|startMonth
argument_list|,
name|session
operator|.
name|getSessionStartYear
argument_list|()
argument_list|)
decl_stmt|;
name|BitSet
name|ret
init|=
operator|new
name|BitSet
argument_list|(
name|size
argument_list|)
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|size
condition|;
name|i
operator|++
control|)
name|ret
operator|.
name|set
argument_list|(
name|i
argument_list|)
expr_stmt|;
return|return
name|ret
return|;
block|}
specifier|public
name|Long
name|getUniqueId
parameter_list|()
block|{
return|return
name|iUniqueId
return|;
block|}
specifier|public
name|String
name|getTerm
parameter_list|()
block|{
return|return
name|iTerm
return|;
block|}
specifier|public
name|String
name|getCampus
parameter_list|()
block|{
return|return
name|iCampus
return|;
block|}
specifier|public
name|String
name|getYear
parameter_list|()
block|{
return|return
name|iYear
return|;
block|}
specifier|public
name|Long
name|getDefaultDatePatternId
parameter_list|()
block|{
return|return
name|iDatePatternId
return|;
block|}
specifier|public
name|BitSet
name|getDefaultWeekPattern
parameter_list|()
block|{
return|return
name|iWeekPattern
return|;
block|}
specifier|public
name|BitSet
name|getFreeTimePattern
parameter_list|()
block|{
return|return
name|iFreeTimePattern
return|;
block|}
specifier|public
name|Date
name|getDatePatternFirstDate
parameter_list|()
block|{
return|return
name|iDatePatternFirstDate
return|;
block|}
specifier|public
name|Date
name|getSessionBeginDate
parameter_list|()
block|{
return|return
name|iSessionBegin
return|;
block|}
specifier|public
name|int
name|getDayOfWeekOffset
parameter_list|()
block|{
return|return
name|iDayOfWeekOffset
return|;
block|}
specifier|public
name|int
name|compareTo
parameter_list|(
name|AcademicSessionInfo
name|a
parameter_list|)
block|{
name|int
name|cmp
init|=
name|iSessionBegin
operator|.
name|compareTo
argument_list|(
name|a
operator|.
name|iSessionBegin
argument_list|)
decl_stmt|;
if|if
condition|(
name|cmp
operator|!=
literal|0
condition|)
return|return
name|cmp
return|;
name|cmp
operator|=
name|getYear
argument_list|()
operator|.
name|compareToIgnoreCase
argument_list|(
name|a
operator|.
name|getYear
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|cmp
operator|!=
literal|0
condition|)
return|return
name|cmp
return|;
name|cmp
operator|=
name|getTerm
argument_list|()
operator|.
name|compareToIgnoreCase
argument_list|(
name|a
operator|.
name|getTerm
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|cmp
operator|!=
literal|0
condition|)
return|return
name|cmp
return|;
name|cmp
operator|=
name|getCampus
argument_list|()
operator|.
name|compareToIgnoreCase
argument_list|(
name|a
operator|.
name|getCampus
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|cmp
operator|!=
literal|0
condition|)
return|return
name|cmp
return|;
return|return
operator|(
name|getUniqueId
argument_list|()
operator|==
literal|null
condition|?
name|Long
operator|.
name|valueOf
argument_list|(
operator|-
literal|1
argument_list|)
else|:
name|getUniqueId
argument_list|()
operator|)
operator|.
name|compareTo
argument_list|(
name|a
operator|.
name|getUniqueId
argument_list|()
operator|==
literal|null
condition|?
operator|-
literal|1
else|:
name|a
operator|.
name|getUniqueId
argument_list|()
argument_list|)
return|;
block|}
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
name|getYear
argument_list|()
operator|+
literal|" "
operator|+
name|getTerm
argument_list|()
operator|+
literal|" ("
operator|+
name|getCampus
argument_list|()
operator|+
literal|")"
return|;
block|}
specifier|public
name|String
name|toCompactString
parameter_list|()
block|{
return|return
name|getTerm
argument_list|()
operator|+
name|getYear
argument_list|()
operator|+
name|getCampus
argument_list|()
return|;
block|}
specifier|public
name|boolean
name|isSectioningEnabled
parameter_list|()
block|{
return|return
name|iSectioningEnabled
return|;
block|}
specifier|public
name|void
name|setSectioningEnabled
parameter_list|(
name|boolean
name|enable
parameter_list|)
block|{
name|iSectioningEnabled
operator|=
name|enable
expr_stmt|;
block|}
specifier|public
name|int
name|getLastWeekToEnroll
parameter_list|()
block|{
return|return
name|iWkEnroll
return|;
block|}
specifier|public
name|int
name|getLastWeekToChange
parameter_list|()
block|{
return|return
name|iWkChange
return|;
block|}
specifier|public
name|int
name|getLastWeekToDrop
parameter_list|()
block|{
return|return
name|iWkDrop
return|;
block|}
specifier|public
name|String
name|getDefaultSectioningStatus
parameter_list|()
block|{
return|return
name|iDefaultStatus
return|;
block|}
specifier|public
name|String
name|getDefaultInstructionalMethod
parameter_list|()
block|{
return|return
name|iDefaultInstructionalMethod
return|;
block|}
annotation|@
name|Override
specifier|public
name|Serializable
name|getQualifierId
parameter_list|()
block|{
return|return
name|getUniqueId
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getQualifierType
parameter_list|()
block|{
return|return
name|Session
operator|.
name|class
operator|.
name|getSimpleName
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getQualifierReference
parameter_list|()
block|{
return|return
name|toCompactString
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getQualifierLabel
parameter_list|()
block|{
return|return
name|toString
argument_list|()
return|;
block|}
specifier|public
name|Date
name|getDefaultStartDate
parameter_list|()
block|{
return|return
name|iDefaultStartDate
return|;
block|}
specifier|public
name|Date
name|getDefaultEndDate
parameter_list|()
block|{
return|return
name|iDefaultEndDate
return|;
block|}
block|}
end_class

end_unit

