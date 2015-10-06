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
name|curricula
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Collection
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
name|gwt
operator|.
name|client
operator|.
name|widgets
operator|.
name|CourseFinder
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
name|CourseFinderClasses
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
name|CourseFinderCourses
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
name|CourseFinderDetails
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
name|CourseFinderDialog
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
name|CourseFinderFactory
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
name|CourseSelectionSuggestBox
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
name|DataProvider
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
name|services
operator|.
name|CurriculaService
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
name|services
operator|.
name|CurriculaServiceAsync
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
name|CurriculumInterface
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
name|ClassAssignmentInterface
operator|.
name|ClassAssignment
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
name|ClassAssignmentInterface
operator|.
name|CourseAssignment
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
name|user
operator|.
name|client
operator|.
name|rpc
operator|.
name|AsyncCallback
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|CurriculaCourseSelectionBox
extends|extends
name|CourseSelectionSuggestBox
block|{
specifier|private
specifier|final
name|CurriculaServiceAsync
name|iCurriculaService
init|=
name|GWT
operator|.
name|create
argument_list|(
name|CurriculaService
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|public
name|CurriculaCourseSelectionBox
parameter_list|()
block|{
name|setCourseFinderFactory
argument_list|(
operator|new
name|CourseFinderFactory
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|CourseFinder
name|createCourseFinder
parameter_list|()
block|{
name|CourseFinder
name|finder
init|=
operator|new
name|CourseFinderDialog
argument_list|()
decl_stmt|;
name|CourseFinderCourses
name|courses
init|=
operator|new
name|CourseFinderCourses
argument_list|()
decl_stmt|;
name|courses
operator|.
name|setDataProvider
argument_list|(
operator|new
name|DataProvider
argument_list|<
name|String
argument_list|,
name|Collection
argument_list|<
name|CourseAssignment
argument_list|>
argument_list|>
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|getData
parameter_list|(
name|String
name|source
parameter_list|,
name|AsyncCallback
argument_list|<
name|Collection
argument_list|<
name|CourseAssignment
argument_list|>
argument_list|>
name|callback
parameter_list|)
block|{
name|iCurriculaService
operator|.
name|listCourseOfferings
argument_list|(
name|source
argument_list|,
literal|null
argument_list|,
name|callback
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|CourseFinderDetails
name|details
init|=
operator|new
name|CourseFinderDetails
argument_list|()
decl_stmt|;
name|details
operator|.
name|setDataProvider
argument_list|(
operator|new
name|DataProvider
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|getData
parameter_list|(
name|String
name|source
parameter_list|,
name|AsyncCallback
argument_list|<
name|String
argument_list|>
name|callback
parameter_list|)
block|{
name|iCurriculaService
operator|.
name|retrieveCourseDetails
argument_list|(
name|source
argument_list|,
name|callback
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|CourseFinderClasses
name|classes
init|=
operator|new
name|CourseFinderClasses
argument_list|()
decl_stmt|;
name|classes
operator|.
name|setDataProvider
argument_list|(
operator|new
name|DataProvider
argument_list|<
name|String
argument_list|,
name|Collection
argument_list|<
name|ClassAssignment
argument_list|>
argument_list|>
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|getData
parameter_list|(
name|String
name|source
parameter_list|,
name|AsyncCallback
argument_list|<
name|Collection
argument_list|<
name|ClassAssignment
argument_list|>
argument_list|>
name|callback
parameter_list|)
block|{
name|iCurriculaService
operator|.
name|listClasses
argument_list|(
name|source
argument_list|,
name|callback
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|CourseFinderCurricula
name|curricula
init|=
operator|new
name|CourseFinderCurricula
argument_list|()
decl_stmt|;
name|curricula
operator|.
name|setDataProvider
argument_list|(
operator|new
name|DataProvider
argument_list|<
name|String
argument_list|,
name|TreeSet
argument_list|<
name|CurriculumInterface
argument_list|>
argument_list|>
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|getData
parameter_list|(
name|String
name|source
parameter_list|,
name|AsyncCallback
argument_list|<
name|TreeSet
argument_list|<
name|CurriculumInterface
argument_list|>
argument_list|>
name|callback
parameter_list|)
block|{
name|iCurriculaService
operator|.
name|findCurriculaForACourse
argument_list|(
name|source
argument_list|,
name|callback
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|courses
operator|.
name|setCourseDetails
argument_list|(
name|details
argument_list|,
name|classes
argument_list|,
name|curricula
argument_list|)
expr_stmt|;
name|finder
operator|.
name|setTabs
argument_list|(
name|courses
argument_list|)
expr_stmt|;
return|return
name|finder
return|;
block|}
block|}
argument_list|)
expr_stmt|;
name|setSuggestions
argument_list|(
operator|new
name|DataProvider
argument_list|<
name|String
argument_list|,
name|Collection
argument_list|<
name|CourseAssignment
argument_list|>
argument_list|>
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|getData
parameter_list|(
name|String
name|source
parameter_list|,
name|AsyncCallback
argument_list|<
name|Collection
argument_list|<
name|CourseAssignment
argument_list|>
argument_list|>
name|callback
parameter_list|)
block|{
name|iCurriculaService
operator|.
name|listCourseOfferings
argument_list|(
name|source
argument_list|,
literal|20
argument_list|,
name|callback
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|setEnabled
parameter_list|(
name|boolean
name|enabled
parameter_list|)
block|{
name|super
operator|.
name|setEnabled
argument_list|(
name|enabled
argument_list|)
expr_stmt|;
if|if
condition|(
name|enabled
condition|)
block|{
name|iFinderButton
operator|.
name|setTabIndex
argument_list|(
literal|0
argument_list|)
expr_stmt|;
name|iFinderButton
operator|.
name|setVisible
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|iSuggest
operator|.
name|getElement
argument_list|()
operator|.
name|getStyle
argument_list|()
operator|.
name|clearBackgroundColor
argument_list|()
expr_stmt|;
name|iSuggest
operator|.
name|getElement
argument_list|()
operator|.
name|getStyle
argument_list|()
operator|.
name|clearBorderColor
argument_list|()
expr_stmt|;
block|}
else|else
block|{
name|iFinderButton
operator|.
name|setTabIndex
argument_list|(
operator|-
literal|1
argument_list|)
expr_stmt|;
name|iFinderButton
operator|.
name|setVisible
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|iSuggest
operator|.
name|getElement
argument_list|()
operator|.
name|getStyle
argument_list|()
operator|.
name|setBorderColor
argument_list|(
literal|"transparent"
argument_list|)
expr_stmt|;
name|iSuggest
operator|.
name|getElement
argument_list|()
operator|.
name|getStyle
argument_list|()
operator|.
name|setBackgroundColor
argument_list|(
literal|"transparent"
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

