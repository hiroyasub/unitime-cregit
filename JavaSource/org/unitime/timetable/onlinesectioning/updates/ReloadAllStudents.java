begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.2 (University Timetabling Application)  * Copyright (C) 2011, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
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
name|updates
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
name|net
operator|.
name|sf
operator|.
name|cpsolver
operator|.
name|studentsct
operator|.
name|model
operator|.
name|Student
import|;
end_import

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
name|gwt
operator|.
name|resources
operator|.
name|StudentSectioningExceptions
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
operator|.
name|Lock
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|ReloadAllStudents
extends|extends
name|ReloadAllData
block|{
specifier|private
specifier|static
name|StudentSectioningExceptions
name|EXCEPTIONS
init|=
name|Localization
operator|.
name|create
argument_list|(
name|StudentSectioningExceptions
operator|.
name|class
argument_list|)
decl_stmt|;
annotation|@
name|Override
specifier|public
name|Boolean
name|execute
parameter_list|(
name|OnlineSectioningServer
name|server
parameter_list|,
name|OnlineSectioningHelper
name|helper
parameter_list|)
block|{
if|if
condition|(
operator|!
literal|"true"
operator|.
name|equals
argument_list|(
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"unitime.enrollment.load"
argument_list|,
literal|"true"
argument_list|)
argument_list|)
condition|)
return|return
literal|false
return|;
name|Lock
name|lock
init|=
name|server
operator|.
name|lockAll
argument_list|()
decl_stmt|;
try|try
block|{
name|helper
operator|.
name|beginTransaction
argument_list|()
expr_stmt|;
try|try
block|{
name|server
operator|.
name|clearAllStudents
argument_list|()
expr_stmt|;
name|List
argument_list|<
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|Student
argument_list|>
name|students
init|=
name|helper
operator|.
name|getHibSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select distinct s from Student s "
operator|+
literal|"left join fetch s.courseDemands as cd "
operator|+
literal|"left join fetch cd.courseRequests as cr "
operator|+
literal|"left join fetch s.classEnrollments as e "
operator|+
literal|"where s.session.uniqueId=:sessionId"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"sessionId"
argument_list|,
name|server
operator|.
name|getAcademicSession
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|)
operator|.
name|list
argument_list|()
decl_stmt|;
for|for
control|(
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|Student
name|student
range|:
name|students
control|)
block|{
name|Student
name|s
init|=
name|loadStudent
argument_list|(
name|student
argument_list|,
name|server
argument_list|,
name|helper
argument_list|)
decl_stmt|;
if|if
condition|(
name|s
operator|!=
literal|null
condition|)
name|server
operator|.
name|update
argument_list|(
name|s
argument_list|)
expr_stmt|;
block|}
name|helper
operator|.
name|commitTransaction
argument_list|()
expr_stmt|;
return|return
literal|true
return|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|helper
operator|.
name|rollbackTransaction
argument_list|()
expr_stmt|;
if|if
condition|(
name|e
operator|instanceof
name|SectioningException
condition|)
throw|throw
operator|(
name|SectioningException
operator|)
name|e
throw|;
throw|throw
operator|new
name|SectioningException
argument_list|(
name|EXCEPTIONS
operator|.
name|unknown
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
finally|finally
block|{
name|lock
operator|.
name|release
argument_list|()
expr_stmt|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|String
name|name
parameter_list|()
block|{
return|return
literal|"reload-all-students"
return|;
block|}
block|}
end_class

end_unit

