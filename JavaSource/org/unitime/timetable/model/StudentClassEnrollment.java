begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.1 (University Timetabling Application)  * Copyright (C) 2008, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 2 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program; if not, write to the Free Software Foundation, Inc.,  * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
package|package
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Iterator
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
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|base
operator|.
name|BaseStudentClassEnrollment
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
name|StudentClassEnrollmentDAO
import|;
end_import

begin_class
specifier|public
class|class
name|StudentClassEnrollment
extends|extends
name|BaseStudentClassEnrollment
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|1L
decl_stmt|;
comment|/*[CONSTRUCTOR MARKER BEGIN]*/
specifier|public
name|StudentClassEnrollment
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
block|}
comment|/** 	 * Constructor for primary key 	 */
specifier|public
name|StudentClassEnrollment
parameter_list|(
name|java
operator|.
name|lang
operator|.
name|Long
name|uniqueId
parameter_list|)
block|{
name|super
argument_list|(
name|uniqueId
argument_list|)
expr_stmt|;
block|}
comment|/** 	 * Constructor for required fields 	 */
specifier|public
name|StudentClassEnrollment
parameter_list|(
name|java
operator|.
name|lang
operator|.
name|Long
name|uniqueId
parameter_list|,
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
parameter_list|,
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|Class_
name|clazz
parameter_list|,
name|java
operator|.
name|util
operator|.
name|Date
name|timestamp
parameter_list|)
block|{
name|super
argument_list|(
name|uniqueId
argument_list|,
name|student
argument_list|,
name|clazz
argument_list|,
name|timestamp
argument_list|)
expr_stmt|;
block|}
comment|/*[CONSTRUCTOR MARKER END]*/
specifier|public
specifier|static
name|List
name|findAll
parameter_list|(
name|Long
name|sessionId
parameter_list|)
block|{
return|return
operator|new
name|StudentClassEnrollmentDAO
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select e from StudentClassEnrollment e where "
operator|+
literal|"e.student.session.uniqueId=:sessionId"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"sessionId"
argument_list|,
name|sessionId
operator|.
name|longValue
argument_list|()
argument_list|)
operator|.
name|list
argument_list|()
return|;
block|}
specifier|public
specifier|static
name|Iterator
name|findAllForStudent
parameter_list|(
name|Long
name|studentId
parameter_list|)
block|{
return|return
operator|new
name|StudentClassEnrollmentDAO
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select e from StudentClassEnrollment e where "
operator|+
literal|"e.student.uniqueId=:studentId"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"studentId"
argument_list|,
name|studentId
operator|.
name|longValue
argument_list|()
argument_list|)
operator|.
name|setCacheable
argument_list|(
literal|true
argument_list|)
operator|.
name|list
argument_list|()
operator|.
name|iterator
argument_list|()
return|;
block|}
specifier|public
specifier|static
name|boolean
name|sessionHasEnrollments
parameter_list|(
name|Long
name|sessionId
parameter_list|)
block|{
if|if
condition|(
name|sessionId
operator|!=
literal|null
condition|)
block|{
return|return
operator|(
operator|(
operator|(
name|Number
operator|)
operator|new
name|StudentClassEnrollmentDAO
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select count(e) from StudentClassEnrollment e where "
operator|+
literal|"e.student.session.uniqueId=:sessionId"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"sessionId"
argument_list|,
name|sessionId
operator|.
name|longValue
argument_list|()
argument_list|)
operator|.
name|setCacheable
argument_list|(
literal|true
argument_list|)
operator|.
name|uniqueResult
argument_list|()
operator|)
operator|.
name|longValue
argument_list|()
operator|>
literal|0
operator|)
return|;
block|}
return|return
literal|false
return|;
block|}
block|}
end_class

end_unit

