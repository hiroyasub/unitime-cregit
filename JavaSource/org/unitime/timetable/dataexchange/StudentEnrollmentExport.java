begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.2 - 3.5 (University Timetabling Application)  * Copyright (C) 2008 - 2013, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
end_comment

begin_package
package|package
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|dataexchange
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
name|java
operator|.
name|util
operator|.
name|Properties
import|;
end_import

begin_import
import|import
name|org
operator|.
name|dom4j
operator|.
name|Document
import|;
end_import

begin_import
import|import
name|org
operator|.
name|dom4j
operator|.
name|Element
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
name|Class_
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
name|CourseOffering
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
name|timetable
operator|.
name|model
operator|.
name|StudentClassEnrollment
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|StudentEnrollmentExport
extends|extends
name|BaseExport
block|{
annotation|@
name|Override
specifier|public
name|void
name|saveXml
parameter_list|(
name|Document
name|document
parameter_list|,
name|Session
name|session
parameter_list|,
name|Properties
name|parameters
parameter_list|)
throws|throws
name|Exception
block|{
try|try
block|{
name|beginTransaction
argument_list|()
expr_stmt|;
name|Element
name|root
init|=
name|document
operator|.
name|addElement
argument_list|(
literal|"studentEnrollments"
argument_list|)
decl_stmt|;
name|root
operator|.
name|addAttribute
argument_list|(
literal|"campus"
argument_list|,
name|session
operator|.
name|getAcademicInitiative
argument_list|()
argument_list|)
expr_stmt|;
name|root
operator|.
name|addAttribute
argument_list|(
literal|"year"
argument_list|,
name|session
operator|.
name|getAcademicYear
argument_list|()
argument_list|)
expr_stmt|;
name|root
operator|.
name|addAttribute
argument_list|(
literal|"term"
argument_list|,
name|session
operator|.
name|getAcademicTerm
argument_list|()
argument_list|)
expr_stmt|;
name|document
operator|.
name|addDocType
argument_list|(
literal|"studentEnrollments"
argument_list|,
literal|"-//UniTime//UniTime Student Enrollments DTD/EN"
argument_list|,
literal|"http://www.unitime.org/interface/StudentEnrollment.dtd"
argument_list|)
expr_stmt|;
for|for
control|(
name|Student
name|student
range|:
operator|(
name|List
argument_list|<
name|Student
argument_list|>
operator|)
name|getHibSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select s from Student s where s.session.uniqueId = :sessionId"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"sessionId"
argument_list|,
name|session
operator|.
name|getUniqueId
argument_list|()
argument_list|)
operator|.
name|list
argument_list|()
control|)
block|{
if|if
condition|(
name|student
operator|.
name|getClassEnrollments
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
continue|continue;
name|Element
name|studentEl
init|=
name|root
operator|.
name|addElement
argument_list|(
literal|"student"
argument_list|)
decl_stmt|;
name|studentEl
operator|.
name|addAttribute
argument_list|(
literal|"externalId"
argument_list|,
name|student
operator|.
name|getExternalUniqueId
argument_list|()
operator|==
literal|null
operator|||
name|student
operator|.
name|getExternalUniqueId
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|?
name|student
operator|.
name|getUniqueId
argument_list|()
operator|.
name|toString
argument_list|()
else|:
name|student
operator|.
name|getExternalUniqueId
argument_list|()
argument_list|)
expr_stmt|;
for|for
control|(
name|StudentClassEnrollment
name|enrollment
range|:
name|student
operator|.
name|getClassEnrollments
argument_list|()
control|)
block|{
name|Element
name|classEl
init|=
name|studentEl
operator|.
name|addElement
argument_list|(
literal|"class"
argument_list|)
decl_stmt|;
name|Class_
name|clazz
init|=
name|enrollment
operator|.
name|getClazz
argument_list|()
decl_stmt|;
name|CourseOffering
name|course
init|=
name|enrollment
operator|.
name|getCourseOffering
argument_list|()
decl_stmt|;
name|String
name|extId
init|=
operator|(
name|course
operator|==
literal|null
condition|?
name|clazz
operator|.
name|getExternalUniqueId
argument_list|()
else|:
name|clazz
operator|.
name|getExternalId
argument_list|(
name|course
argument_list|)
operator|)
decl_stmt|;
if|if
condition|(
name|extId
operator|!=
literal|null
operator|&&
operator|!
name|extId
operator|.
name|isEmpty
argument_list|()
condition|)
name|classEl
operator|.
name|addAttribute
argument_list|(
literal|"externalId"
argument_list|,
name|extId
argument_list|)
expr_stmt|;
if|if
condition|(
name|course
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
name|course
operator|.
name|getExternalUniqueId
argument_list|()
operator|!=
literal|null
operator|&&
operator|!
name|course
operator|.
name|getExternalUniqueId
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
name|classEl
operator|.
name|addAttribute
argument_list|(
literal|"courseId"
argument_list|,
name|course
operator|.
name|getExternalUniqueId
argument_list|()
argument_list|)
expr_stmt|;
name|classEl
operator|.
name|addAttribute
argument_list|(
literal|"subject"
argument_list|,
name|course
operator|.
name|getSubjectAreaAbbv
argument_list|()
argument_list|)
expr_stmt|;
name|classEl
operator|.
name|addAttribute
argument_list|(
literal|"courseNbr"
argument_list|,
name|course
operator|.
name|getCourseNbr
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|classEl
operator|.
name|addAttribute
argument_list|(
literal|"type"
argument_list|,
name|clazz
operator|.
name|getSchedulingSubpart
argument_list|()
operator|.
name|getItypeDesc
argument_list|()
operator|.
name|trim
argument_list|()
argument_list|)
expr_stmt|;
name|classEl
operator|.
name|addAttribute
argument_list|(
literal|"suffix"
argument_list|,
name|clazz
operator|.
name|getSectionNumberString
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
name|commitTransaction
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|fatal
argument_list|(
literal|"Exception: "
operator|+
name|e
operator|.
name|getMessage
argument_list|()
argument_list|,
name|e
argument_list|)
expr_stmt|;
name|rollbackTransaction
argument_list|()
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

