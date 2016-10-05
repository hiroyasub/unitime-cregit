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
name|widgets
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
name|ClassAssignmentInterface
operator|.
name|CourseAssignment
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
name|CourseRequestInterface
operator|.
name|RequestedCourse
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
name|Widget
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|CourseFinderDetails
extends|extends
name|HTML
implements|implements
name|CourseFinder
operator|.
name|CourseFinderCourseDetails
argument_list|<
name|CourseAssignment
argument_list|,
name|String
argument_list|>
block|{
specifier|protected
specifier|static
specifier|final
name|StudentSectioningMessages
name|MESSAGES
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
specifier|private
name|DataProvider
argument_list|<
name|CourseAssignment
argument_list|,
name|String
argument_list|>
name|iDataProvider
init|=
literal|null
decl_stmt|;
specifier|private
name|CourseAssignment
name|iValue
init|=
literal|null
decl_stmt|;
specifier|public
name|CourseFinderDetails
parameter_list|()
block|{
name|setHTML
argument_list|(
name|MESSAGES
operator|.
name|courseSelectionNoCourseSelected
argument_list|()
argument_list|)
expr_stmt|;
name|setStyleName
argument_list|(
literal|"unitime-Message"
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|setDataProvider
parameter_list|(
name|DataProvider
argument_list|<
name|CourseAssignment
argument_list|,
name|String
argument_list|>
name|provider
parameter_list|)
block|{
name|iDataProvider
operator|=
name|provider
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|Widget
name|asWidget
parameter_list|()
block|{
return|return
name|this
return|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|setValue
parameter_list|(
name|CourseAssignment
name|value
parameter_list|)
block|{
if|if
condition|(
name|value
operator|==
literal|null
condition|)
block|{
name|iValue
operator|=
name|value
expr_stmt|;
name|setHTML
argument_list|(
name|MESSAGES
operator|.
name|courseSelectionNoCourseSelected
argument_list|()
argument_list|)
expr_stmt|;
name|setStyleName
argument_list|(
literal|"unitime-Message"
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
operator|!
name|value
operator|.
name|equals
argument_list|(
name|iValue
argument_list|)
condition|)
block|{
name|iValue
operator|=
name|value
expr_stmt|;
name|setHTML
argument_list|(
name|MESSAGES
operator|.
name|courseSelectionLoadingDetails
argument_list|()
argument_list|)
expr_stmt|;
name|setStyleName
argument_list|(
literal|"unitime-Message"
argument_list|)
expr_stmt|;
name|iDataProvider
operator|.
name|getData
argument_list|(
name|iValue
argument_list|,
operator|new
name|AsyncCallback
argument_list|<
name|String
argument_list|>
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|onSuccess
parameter_list|(
name|String
name|result
parameter_list|)
block|{
name|setHTML
argument_list|(
name|result
argument_list|)
expr_stmt|;
name|setStyleName
argument_list|(
literal|""
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|onFailure
parameter_list|(
name|Throwable
name|caught
parameter_list|)
block|{
name|setHTML
argument_list|(
name|caught
operator|.
name|getMessage
argument_list|()
argument_list|)
expr_stmt|;
name|setStyleName
argument_list|(
literal|"unitime-ErrorMessage"
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|CourseAssignment
name|getValue
parameter_list|()
block|{
return|return
name|iValue
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
name|MESSAGES
operator|.
name|courseSelectionDetails
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|onSetValue
parameter_list|(
name|RequestedCourse
name|course
parameter_list|)
block|{
block|}
annotation|@
name|Override
specifier|public
name|void
name|onGetValue
parameter_list|(
name|RequestedCourse
name|course
parameter_list|)
block|{
block|}
block|}
end_class

end_unit

