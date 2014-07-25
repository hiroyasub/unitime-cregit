begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.5 (University Timetabling Application)  * Copyright (C) 2014, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
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
name|CurriculumInterface
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
name|CourseFinderCurricula
extends|extends
name|CourseCurriculaTable
implements|implements
name|CourseFinder
operator|.
name|CourseFinderCourseDetails
argument_list|<
name|TreeSet
argument_list|<
name|CurriculumInterface
argument_list|>
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
name|StudentSectioningMessages
name|SCT_MESSAGES
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
name|String
name|iValue
init|=
literal|null
decl_stmt|;
specifier|private
name|DataProvider
argument_list|<
name|String
argument_list|,
name|TreeSet
argument_list|<
name|CurriculumInterface
argument_list|>
argument_list|>
name|iDataProvider
decl_stmt|;
specifier|public
name|CourseFinderCurricula
parameter_list|()
block|{
name|super
argument_list|(
literal|false
argument_list|,
literal|false
argument_list|)
expr_stmt|;
name|setMessage
argument_list|(
name|SCT_MESSAGES
operator|.
name|courseSelectionNoCourseSelected
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|setValue
parameter_list|(
specifier|final
name|String
name|value
parameter_list|)
block|{
if|if
condition|(
name|value
operator|==
literal|null
operator|||
name|value
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|iValue
operator|=
name|value
expr_stmt|;
name|clear
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|setMessage
argument_list|(
name|SCT_MESSAGES
operator|.
name|courseSelectionNoCourseSelected
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|iValue
operator|=
name|value
expr_stmt|;
name|clear
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|ensureInitialized
argument_list|(
operator|new
name|AsyncCallback
argument_list|<
name|Boolean
argument_list|>
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|onSuccess
parameter_list|(
name|Boolean
name|result
parameter_list|)
block|{
name|iDataProvider
operator|.
name|getData
argument_list|(
name|value
argument_list|,
operator|new
name|AsyncCallback
argument_list|<
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
name|onFailure
parameter_list|(
name|Throwable
name|caught
parameter_list|)
block|{
name|setMessage
argument_list|(
name|MESSAGES
operator|.
name|failedToLoadCurricula
argument_list|(
name|caught
operator|.
name|getMessage
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|CurriculumCookie
operator|.
name|getInstance
argument_list|()
operator|.
name|setCurriculaCoursesDetails
argument_list|(
literal|false
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|onSuccess
parameter_list|(
name|TreeSet
argument_list|<
name|CurriculumInterface
argument_list|>
name|result
parameter_list|)
block|{
if|if
condition|(
name|result
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|setMessage
argument_list|(
name|MESSAGES
operator|.
name|offeringHasNoCurricula
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|populate
argument_list|(
name|result
argument_list|)
expr_stmt|;
block|}
block|}
block|}
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
block|}
block|}
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|String
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
name|void
name|setDataProvider
parameter_list|(
name|DataProvider
argument_list|<
name|String
argument_list|,
name|TreeSet
argument_list|<
name|CurriculumInterface
argument_list|>
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
name|String
name|getName
parameter_list|()
block|{
return|return
name|MESSAGES
operator|.
name|tabCurricula
argument_list|()
return|;
block|}
block|}
end_class

end_unit
