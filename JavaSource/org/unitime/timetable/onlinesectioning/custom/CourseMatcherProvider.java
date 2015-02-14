begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.5 (University Timetabling Application)  * Copyright (C) 2015, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
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
name|timetable
operator|.
name|onlinesectioning
operator|.
name|match
operator|.
name|CourseMatcher
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
name|SessionContext
import|;
end_import

begin_comment
comment|/**  * {@link CourseMatcher} provider interface. Such provider can be used to filter out what courses  * a student can see / select in the Scheduling Assistant.  */
end_comment

begin_interface
specifier|public
interface|interface
name|CourseMatcherProvider
block|{
comment|/** 	 * Create a course matcher instance 	 * @param context current session context (e.g., can be used to check permission, current user role etc.) 	 * @param studentId current student unique id 	 * @return course matcher instance, null if no additional filtering is to be made 	 */
specifier|public
name|CourseMatcher
name|getCourseMatcher
parameter_list|(
name|SessionContext
name|context
parameter_list|,
name|Long
name|studentId
parameter_list|)
function_decl|;
block|}
end_interface

end_unit

