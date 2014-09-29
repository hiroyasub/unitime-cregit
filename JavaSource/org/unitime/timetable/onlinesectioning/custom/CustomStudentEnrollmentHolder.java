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
name|onlinesectioning
operator|.
name|custom
package|;
end_package

begin_import
import|import
name|org
operator|.
name|unitime
operator|.
name|localization
operator|.
name|impl
operator|.
name|Localization
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
name|SectioningException
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
name|custom
operator|.
name|StudentEnrollmentProvider
import|;
end_import

begin_class
specifier|public
class|class
name|CustomStudentEnrollmentHolder
block|{
specifier|private
specifier|static
name|StudentSectioningMessages
name|MSG
init|=
name|Localization
operator|.
name|create
argument_list|(
name|StudentSectioningMessages
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|private
specifier|static
name|StudentEnrollmentProvider
name|sProvider
init|=
literal|null
decl_stmt|;
specifier|public
specifier|synchronized
specifier|static
name|StudentEnrollmentProvider
name|getProvider
parameter_list|()
block|{
if|if
condition|(
name|sProvider
operator|==
literal|null
condition|)
block|{
try|try
block|{
name|sProvider
operator|=
operator|(
operator|(
name|StudentEnrollmentProvider
operator|)
name|Class
operator|.
name|forName
argument_list|(
name|ApplicationProperty
operator|.
name|CustomizationStudentEnrollments
operator|.
name|value
argument_list|()
argument_list|)
operator|.
name|newInstance
argument_list|()
operator|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|SectioningException
argument_list|(
name|MSG
operator|.
name|exceptionStudentEnrollmentProvider
argument_list|(
name|e
operator|.
name|getMessage
argument_list|()
argument_list|)
argument_list|,
name|e
argument_list|)
throw|;
block|}
block|}
return|return
name|sProvider
return|;
block|}
specifier|public
specifier|synchronized
specifier|static
name|void
name|release
parameter_list|()
block|{
if|if
condition|(
name|sProvider
operator|!=
literal|null
condition|)
block|{
name|sProvider
operator|.
name|dispose
argument_list|()
expr_stmt|;
name|sProvider
operator|=
literal|null
expr_stmt|;
block|}
block|}
specifier|public
specifier|synchronized
specifier|static
name|boolean
name|hasProvider
parameter_list|()
block|{
return|return
name|sProvider
operator|!=
literal|null
operator|||
name|ApplicationProperty
operator|.
name|CustomizationStudentEnrollments
operator|.
name|value
argument_list|()
operator|!=
literal|null
return|;
block|}
specifier|public
specifier|synchronized
specifier|static
name|boolean
name|isAllowWaitListing
parameter_list|()
block|{
return|return
operator|!
name|hasProvider
argument_list|()
operator|||
name|getProvider
argument_list|()
operator|.
name|isAllowWaitListing
argument_list|()
return|;
block|}
specifier|public
specifier|synchronized
specifier|static
name|boolean
name|isCanRequestUpdates
parameter_list|()
block|{
return|return
operator|!
name|hasProvider
argument_list|()
operator|||
name|getProvider
argument_list|()
operator|.
name|isCanRequestUpdates
argument_list|()
return|;
block|}
block|}
end_class

end_unit

