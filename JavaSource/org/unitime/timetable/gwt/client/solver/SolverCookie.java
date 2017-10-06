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
name|solver
package|;
end_package

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
name|gwt
operator|.
name|shared
operator|.
name|SolverInterface
operator|.
name|ProgressLogLevel
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
name|Cookies
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|SolverCookie
block|{
specifier|private
specifier|static
name|SolverCookie
name|sInstance
init|=
literal|null
decl_stmt|;
specifier|private
name|int
name|iLogLevel
init|=
name|ProgressLogLevel
operator|.
name|INFO
operator|.
name|ordinal
argument_list|()
decl_stmt|;
specifier|private
name|boolean
name|iTimeGridFilter
init|=
literal|true
decl_stmt|;
specifier|private
name|boolean
name|iAssignedClassesFilter
init|=
literal|true
decl_stmt|;
specifier|private
name|int
name|iAssignedClassesSort
init|=
literal|0
decl_stmt|;
specifier|private
name|boolean
name|iNotAssignedClassesFilter
init|=
literal|true
decl_stmt|;
specifier|private
name|int
name|iNotAssignedClassesSort
init|=
literal|0
decl_stmt|;
specifier|private
name|int
name|iSelectedAssignmentsSort
init|=
literal|0
decl_stmt|,
name|iConflictingAssignmentsSort
init|=
literal|0
decl_stmt|,
name|iSuggestionsSort
init|=
literal|0
decl_stmt|,
name|iPlacementsSort
init|=
literal|0
decl_stmt|,
name|iConflictsSort
init|=
literal|0
decl_stmt|,
name|iSolutionChangesSort
init|=
literal|0
decl_stmt|,
name|iAssignmentHistorySort
init|=
literal|0
decl_stmt|,
name|iListSolutionsSort
init|=
literal|0
decl_stmt|;
specifier|private
name|boolean
name|iShowSuggestions
init|=
literal|true
decl_stmt|;
specifier|private
name|String
name|iSuggestionsFilter
init|=
literal|""
decl_stmt|;
specifier|private
name|boolean
name|iShowConflicts
init|=
literal|false
decl_stmt|;
specifier|private
name|boolean
name|iShowAllStudentConflicts
init|=
literal|false
decl_stmt|,
name|iShowAllDistributionConflicts
init|=
literal|false
decl_stmt|;
specifier|private
name|boolean
name|iShowCBS
init|=
literal|false
decl_stmt|,
name|iShowCBSFilter
init|=
literal|true
decl_stmt|,
name|iSolutionChangesFilter
init|=
literal|true
decl_stmt|,
name|iAssignmentHistoryFilter
init|=
literal|true
decl_stmt|;
specifier|private
name|SolverCookie
parameter_list|()
block|{
try|try
block|{
name|String
name|cookie
init|=
name|Cookies
operator|.
name|getCookie
argument_list|(
literal|"UniTime:Solver"
argument_list|)
decl_stmt|;
if|if
condition|(
name|cookie
operator|!=
literal|null
condition|)
block|{
name|String
index|[]
name|params
init|=
name|cookie
operator|.
name|split
argument_list|(
literal|"\\|"
argument_list|)
decl_stmt|;
name|int
name|idx
init|=
literal|0
decl_stmt|;
name|iLogLevel
operator|=
name|Integer
operator|.
name|valueOf
argument_list|(
name|params
index|[
name|idx
operator|++
index|]
argument_list|)
expr_stmt|;
name|iTimeGridFilter
operator|=
literal|"1"
operator|.
name|equals
argument_list|(
name|params
index|[
name|idx
operator|++
index|]
argument_list|)
expr_stmt|;
name|iAssignedClassesFilter
operator|=
literal|"1"
operator|.
name|equals
argument_list|(
name|params
index|[
name|idx
operator|++
index|]
argument_list|)
expr_stmt|;
name|iAssignedClassesSort
operator|=
name|Integer
operator|.
name|parseInt
argument_list|(
name|params
index|[
name|idx
operator|++
index|]
argument_list|)
expr_stmt|;
name|iNotAssignedClassesFilter
operator|=
literal|"1"
operator|.
name|equals
argument_list|(
name|params
index|[
name|idx
operator|++
index|]
argument_list|)
expr_stmt|;
name|iNotAssignedClassesSort
operator|=
name|Integer
operator|.
name|parseInt
argument_list|(
name|params
index|[
name|idx
operator|++
index|]
argument_list|)
expr_stmt|;
name|iSelectedAssignmentsSort
operator|=
name|Integer
operator|.
name|parseInt
argument_list|(
name|params
index|[
name|idx
operator|++
index|]
argument_list|)
expr_stmt|;
name|iConflictingAssignmentsSort
operator|=
name|Integer
operator|.
name|parseInt
argument_list|(
name|params
index|[
name|idx
operator|++
index|]
argument_list|)
expr_stmt|;
name|iSuggestionsSort
operator|=
name|Integer
operator|.
name|parseInt
argument_list|(
name|params
index|[
name|idx
operator|++
index|]
argument_list|)
expr_stmt|;
name|iPlacementsSort
operator|=
name|Integer
operator|.
name|parseInt
argument_list|(
name|params
index|[
name|idx
operator|++
index|]
argument_list|)
expr_stmt|;
name|iShowSuggestions
operator|=
literal|"1"
operator|.
name|equals
argument_list|(
name|params
index|[
name|idx
operator|++
index|]
argument_list|)
expr_stmt|;
name|iShowConflicts
operator|=
literal|"1"
operator|.
name|equals
argument_list|(
name|params
index|[
name|idx
operator|++
index|]
argument_list|)
expr_stmt|;
name|iConflictsSort
operator|=
name|Integer
operator|.
name|parseInt
argument_list|(
name|params
index|[
name|idx
operator|++
index|]
argument_list|)
expr_stmt|;
name|iShowAllStudentConflicts
operator|=
literal|"1"
operator|.
name|equals
argument_list|(
name|params
index|[
name|idx
operator|++
index|]
argument_list|)
expr_stmt|;
name|iShowAllDistributionConflicts
operator|=
literal|"1"
operator|.
name|equals
argument_list|(
name|params
index|[
name|idx
operator|++
index|]
argument_list|)
expr_stmt|;
name|iShowCBS
operator|=
literal|"1"
operator|.
name|equals
argument_list|(
name|params
index|[
name|idx
operator|++
index|]
argument_list|)
expr_stmt|;
name|iShowCBSFilter
operator|=
literal|"1"
operator|.
name|equals
argument_list|(
name|params
index|[
name|idx
operator|++
index|]
argument_list|)
expr_stmt|;
name|iSolutionChangesFilter
operator|=
literal|"1"
operator|.
name|equals
argument_list|(
name|params
index|[
name|idx
operator|++
index|]
argument_list|)
expr_stmt|;
name|iSolutionChangesSort
operator|=
name|Integer
operator|.
name|parseInt
argument_list|(
name|params
index|[
name|idx
operator|++
index|]
argument_list|)
expr_stmt|;
name|iAssignmentHistoryFilter
operator|=
literal|"1"
operator|.
name|equals
argument_list|(
name|params
index|[
name|idx
operator|++
index|]
argument_list|)
expr_stmt|;
name|iAssignmentHistorySort
operator|=
name|Integer
operator|.
name|parseInt
argument_list|(
name|params
index|[
name|idx
operator|++
index|]
argument_list|)
expr_stmt|;
name|iListSolutionsSort
operator|=
name|Integer
operator|.
name|parseInt
argument_list|(
name|params
index|[
name|idx
operator|++
index|]
argument_list|)
expr_stmt|;
name|iSuggestionsFilter
operator|=
name|params
index|[
name|idx
operator|++
index|]
expr_stmt|;
block|}
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
block|}
block|}
specifier|private
name|void
name|save
parameter_list|()
block|{
name|String
name|cookie
init|=
name|iLogLevel
operator|+
literal|"|"
operator|+
operator|(
name|iTimeGridFilter
condition|?
literal|"1"
else|:
literal|"0"
operator|)
operator|+
literal|"|"
operator|+
operator|(
name|iAssignedClassesFilter
condition|?
literal|"1"
else|:
literal|"0"
operator|)
operator|+
literal|"|"
operator|+
name|iAssignedClassesSort
operator|+
literal|"|"
operator|+
operator|(
name|iNotAssignedClassesFilter
condition|?
literal|"1"
else|:
literal|"0"
operator|)
operator|+
literal|"|"
operator|+
name|iNotAssignedClassesSort
operator|+
literal|"|"
operator|+
name|iSelectedAssignmentsSort
operator|+
literal|"|"
operator|+
name|iConflictingAssignmentsSort
operator|+
literal|"|"
operator|+
name|iSuggestionsSort
operator|+
literal|"|"
operator|+
name|iPlacementsSort
operator|+
literal|"|"
operator|+
operator|(
name|iShowSuggestions
condition|?
literal|"1"
else|:
literal|"0"
operator|)
operator|+
literal|"|"
operator|+
operator|(
name|iShowConflicts
condition|?
literal|"1"
else|:
literal|"0"
operator|)
operator|+
literal|"|"
operator|+
name|iConflictsSort
operator|+
literal|"|"
operator|+
operator|(
name|iShowAllStudentConflicts
condition|?
literal|"1"
else|:
literal|"0"
operator|)
operator|+
literal|"|"
operator|+
operator|(
name|iShowAllDistributionConflicts
condition|?
literal|"1"
else|:
literal|"0"
operator|)
operator|+
literal|"|"
operator|+
operator|(
name|iShowCBS
condition|?
literal|"1"
else|:
literal|"0"
operator|)
operator|+
literal|"|"
operator|+
operator|(
name|iShowCBSFilter
condition|?
literal|"1"
else|:
literal|"0"
operator|)
operator|+
literal|"|"
operator|+
operator|(
name|iSolutionChangesFilter
condition|?
literal|"1"
else|:
literal|"0"
operator|)
operator|+
literal|"|"
operator|+
name|iSolutionChangesSort
operator|+
literal|"|"
operator|+
operator|(
name|iAssignmentHistoryFilter
condition|?
literal|"1"
else|:
literal|"0"
operator|)
operator|+
literal|"|"
operator|+
name|iAssignmentHistorySort
operator|+
literal|"|"
operator|+
name|iListSolutionsSort
operator|+
literal|"|"
operator|+
operator|(
name|iSuggestionsFilter
operator|==
literal|null
condition|?
literal|""
else|:
name|iSuggestionsFilter
operator|)
decl_stmt|;
name|Date
name|expires
init|=
operator|new
name|Date
argument_list|(
operator|new
name|Date
argument_list|()
operator|.
name|getTime
argument_list|()
operator|+
literal|604800000l
argument_list|)
decl_stmt|;
comment|// expires in 7 days
name|Cookies
operator|.
name|setCookie
argument_list|(
literal|"UniTime:Solver"
argument_list|,
name|cookie
argument_list|,
name|expires
argument_list|)
expr_stmt|;
block|}
specifier|public
specifier|static
name|SolverCookie
name|getInstance
parameter_list|()
block|{
if|if
condition|(
name|sInstance
operator|==
literal|null
condition|)
name|sInstance
operator|=
operator|new
name|SolverCookie
argument_list|()
expr_stmt|;
return|return
name|sInstance
return|;
block|}
specifier|public
name|int
name|getLogLevel
parameter_list|()
block|{
return|return
name|iLogLevel
return|;
block|}
specifier|public
name|void
name|setLogLevel
parameter_list|(
name|int
name|level
parameter_list|)
block|{
name|iLogLevel
operator|=
name|level
expr_stmt|;
name|save
argument_list|()
expr_stmt|;
block|}
specifier|public
name|boolean
name|isTimeGridFilter
parameter_list|()
block|{
return|return
name|iTimeGridFilter
return|;
block|}
specifier|public
name|void
name|setTimeGridFilter
parameter_list|(
name|boolean
name|filter
parameter_list|)
block|{
name|iTimeGridFilter
operator|=
name|filter
expr_stmt|;
name|save
argument_list|()
expr_stmt|;
block|}
specifier|public
name|boolean
name|isAssignedClassesFilter
parameter_list|()
block|{
return|return
name|iAssignedClassesFilter
return|;
block|}
specifier|public
name|void
name|setAssignedClassesFilter
parameter_list|(
name|boolean
name|filter
parameter_list|)
block|{
name|iAssignedClassesFilter
operator|=
name|filter
expr_stmt|;
name|save
argument_list|()
expr_stmt|;
block|}
specifier|public
name|int
name|getAssignedClassesSort
parameter_list|()
block|{
return|return
name|iAssignedClassesSort
return|;
block|}
specifier|public
name|void
name|setAssignedClassesSort
parameter_list|(
name|int
name|sort
parameter_list|)
block|{
name|iAssignedClassesSort
operator|=
name|sort
expr_stmt|;
name|save
argument_list|()
expr_stmt|;
block|}
specifier|public
name|boolean
name|isNotAssignedClassesFilter
parameter_list|()
block|{
return|return
name|iNotAssignedClassesFilter
return|;
block|}
specifier|public
name|void
name|setNotAssignedClassesFilter
parameter_list|(
name|boolean
name|filter
parameter_list|)
block|{
name|iNotAssignedClassesFilter
operator|=
name|filter
expr_stmt|;
name|save
argument_list|()
expr_stmt|;
block|}
specifier|public
name|int
name|getNotAssignedClassesSort
parameter_list|()
block|{
return|return
name|iNotAssignedClassesSort
return|;
block|}
specifier|public
name|void
name|setNotAssignedClassesSort
parameter_list|(
name|int
name|sort
parameter_list|)
block|{
name|iNotAssignedClassesSort
operator|=
name|sort
expr_stmt|;
name|save
argument_list|()
expr_stmt|;
block|}
specifier|public
name|int
name|getSelectedAssignmentsSort
parameter_list|()
block|{
return|return
name|iSelectedAssignmentsSort
return|;
block|}
specifier|public
name|void
name|setSelectedAssignmentsSort
parameter_list|(
name|int
name|sort
parameter_list|)
block|{
name|iSelectedAssignmentsSort
operator|=
name|sort
expr_stmt|;
name|save
argument_list|()
expr_stmt|;
block|}
specifier|public
name|int
name|getConflictingAssignmentsSort
parameter_list|()
block|{
return|return
name|iConflictingAssignmentsSort
return|;
block|}
specifier|public
name|void
name|setConflictingAssignmentsSort
parameter_list|(
name|int
name|sort
parameter_list|)
block|{
name|iConflictingAssignmentsSort
operator|=
name|sort
expr_stmt|;
name|save
argument_list|()
expr_stmt|;
block|}
specifier|public
name|int
name|getSuggestionsSort
parameter_list|()
block|{
return|return
name|iSuggestionsSort
return|;
block|}
specifier|public
name|void
name|setSuggestionsSort
parameter_list|(
name|int
name|sort
parameter_list|)
block|{
name|iSuggestionsSort
operator|=
name|sort
expr_stmt|;
name|save
argument_list|()
expr_stmt|;
block|}
specifier|public
name|int
name|getPlacementsSort
parameter_list|()
block|{
return|return
name|iPlacementsSort
return|;
block|}
specifier|public
name|void
name|setPlacementsSort
parameter_list|(
name|int
name|sort
parameter_list|)
block|{
name|iPlacementsSort
operator|=
name|sort
expr_stmt|;
name|save
argument_list|()
expr_stmt|;
block|}
specifier|public
name|boolean
name|isShowSuggestions
parameter_list|()
block|{
return|return
name|iShowSuggestions
return|;
block|}
specifier|public
name|void
name|setShowSuggestions
parameter_list|(
name|boolean
name|showSuggestions
parameter_list|)
block|{
name|iShowSuggestions
operator|=
name|showSuggestions
expr_stmt|;
name|save
argument_list|()
expr_stmt|;
block|}
specifier|public
name|String
name|getSuggestionsFilter
parameter_list|()
block|{
return|return
name|iSuggestionsFilter
operator|==
literal|null
condition|?
literal|""
else|:
name|iSuggestionsFilter
return|;
block|}
specifier|public
name|void
name|setSuggestionsFilter
parameter_list|(
name|String
name|filter
parameter_list|)
block|{
name|iSuggestionsFilter
operator|=
name|filter
expr_stmt|;
name|save
argument_list|()
expr_stmt|;
block|}
specifier|public
name|boolean
name|isShowConflicts
parameter_list|()
block|{
return|return
name|iShowConflicts
return|;
block|}
specifier|public
name|void
name|setShowConflicts
parameter_list|(
name|boolean
name|showConflicts
parameter_list|)
block|{
name|iShowConflicts
operator|=
name|showConflicts
expr_stmt|;
name|save
argument_list|()
expr_stmt|;
block|}
specifier|public
name|int
name|getConflictsSort
parameter_list|()
block|{
return|return
name|iConflictsSort
return|;
block|}
specifier|public
name|void
name|setConflictsSort
parameter_list|(
name|int
name|sort
parameter_list|)
block|{
name|iConflictsSort
operator|=
name|sort
expr_stmt|;
name|save
argument_list|()
expr_stmt|;
block|}
specifier|public
name|boolean
name|isShowAllStudentConflicts
parameter_list|()
block|{
return|return
name|iShowAllStudentConflicts
return|;
block|}
specifier|public
name|void
name|setShowAllStudentConflicts
parameter_list|(
name|boolean
name|showAllStudentConflicts
parameter_list|)
block|{
name|iShowAllStudentConflicts
operator|=
name|showAllStudentConflicts
expr_stmt|;
name|save
argument_list|()
expr_stmt|;
block|}
specifier|public
name|boolean
name|isShowAllDistributionConflicts
parameter_list|()
block|{
return|return
name|iShowAllDistributionConflicts
return|;
block|}
specifier|public
name|void
name|setShowAllDistributionConflicts
parameter_list|(
name|boolean
name|showAllDistributionConflicts
parameter_list|)
block|{
name|iShowAllDistributionConflicts
operator|=
name|showAllDistributionConflicts
expr_stmt|;
name|save
argument_list|()
expr_stmt|;
block|}
specifier|public
name|boolean
name|isShowCBS
parameter_list|()
block|{
return|return
name|iShowCBS
return|;
block|}
specifier|public
name|void
name|setShowCBS
parameter_list|(
name|boolean
name|showCBS
parameter_list|)
block|{
name|iShowCBS
operator|=
name|showCBS
expr_stmt|;
name|save
argument_list|()
expr_stmt|;
block|}
specifier|public
name|boolean
name|isShowCBSFilter
parameter_list|()
block|{
return|return
name|iShowCBSFilter
return|;
block|}
specifier|public
name|void
name|setShowCBSFilter
parameter_list|(
name|boolean
name|filter
parameter_list|)
block|{
name|iShowCBSFilter
operator|=
name|filter
expr_stmt|;
name|save
argument_list|()
expr_stmt|;
block|}
specifier|public
name|boolean
name|isSolutionChangesFilter
parameter_list|()
block|{
return|return
name|iSolutionChangesFilter
return|;
block|}
specifier|public
name|void
name|setSolutionChangesFilter
parameter_list|(
name|boolean
name|filter
parameter_list|)
block|{
name|iSolutionChangesFilter
operator|=
name|filter
expr_stmt|;
name|save
argument_list|()
expr_stmt|;
block|}
specifier|public
name|int
name|getSolutionChangesSort
parameter_list|()
block|{
return|return
name|iSolutionChangesSort
return|;
block|}
specifier|public
name|void
name|setSolutionChangesSort
parameter_list|(
name|int
name|sort
parameter_list|)
block|{
name|iSolutionChangesSort
operator|=
name|sort
expr_stmt|;
name|save
argument_list|()
expr_stmt|;
block|}
specifier|public
name|boolean
name|isAssignmentHistoryFilter
parameter_list|()
block|{
return|return
name|iAssignmentHistoryFilter
return|;
block|}
specifier|public
name|void
name|setAssignmentHistoryFilter
parameter_list|(
name|boolean
name|filter
parameter_list|)
block|{
name|iAssignmentHistoryFilter
operator|=
name|filter
expr_stmt|;
name|save
argument_list|()
expr_stmt|;
block|}
specifier|public
name|int
name|getAssignmentHistorySort
parameter_list|()
block|{
return|return
name|iAssignmentHistorySort
return|;
block|}
specifier|public
name|void
name|setAssignmentHistorySort
parameter_list|(
name|int
name|sort
parameter_list|)
block|{
name|iAssignmentHistorySort
operator|=
name|sort
expr_stmt|;
name|save
argument_list|()
expr_stmt|;
block|}
specifier|public
name|int
name|getListSolutionsSort
parameter_list|()
block|{
return|return
name|iListSolutionsSort
return|;
block|}
specifier|public
name|void
name|setListSolutionsSort
parameter_list|(
name|int
name|sort
parameter_list|)
block|{
name|iListSolutionsSort
operator|=
name|sort
expr_stmt|;
name|save
argument_list|()
expr_stmt|;
block|}
block|}
end_class

end_unit

