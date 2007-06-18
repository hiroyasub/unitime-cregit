begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.0 (University Course Timetabling& Student Sectioning Application)  * Copyright (C) 2007, UniTime.org, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 2 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program; if not, write to the Free Software Foundation, Inc.,  * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
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
name|Iterator
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
name|LastLikeCourseDemand
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
name|SubjectArea
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
name|CourseOfferingDAO
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
name|SubjectAreaDAO
import|;
end_import

begin_comment
comment|/**  *   * @author Timothy Almon  *  */
end_comment

begin_class
specifier|public
class|class
name|LastLikeCourseDemandImport
extends|extends
name|BaseImport
block|{
specifier|public
name|LastLikeCourseDemandImport
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
block|}
specifier|public
name|void
name|loadXml
parameter_list|(
name|Element
name|root
parameter_list|)
throws|throws
name|Exception
block|{
try|try
block|{
name|String
name|campus
init|=
name|root
operator|.
name|attributeValue
argument_list|(
literal|"campus"
argument_list|)
decl_stmt|;
name|String
name|year
init|=
name|root
operator|.
name|attributeValue
argument_list|(
literal|"year"
argument_list|)
decl_stmt|;
name|String
name|term
init|=
name|root
operator|.
name|attributeValue
argument_list|(
literal|"term"
argument_list|)
decl_stmt|;
name|Session
name|session
init|=
name|Session
operator|.
name|getSessionUsingInitiativeYearTerm
argument_list|(
name|campus
argument_list|,
name|year
argument_list|,
name|term
argument_list|)
decl_stmt|;
if|if
condition|(
name|session
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|Exception
argument_list|(
literal|"No session found for the given campus, year, and term."
argument_list|)
throw|;
block|}
name|beginTransaction
argument_list|()
expr_stmt|;
for|for
control|(
name|Iterator
name|it
init|=
name|root
operator|.
name|elementIterator
argument_list|()
init|;
name|it
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|Element
name|element
init|=
operator|(
name|Element
operator|)
name|it
operator|.
name|next
argument_list|()
decl_stmt|;
name|String
name|externalId
init|=
name|element
operator|.
name|attributeValue
argument_list|(
literal|"externalId"
argument_list|)
decl_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"Loading "
operator|+
name|externalId
argument_list|)
expr_stmt|;
name|Student
name|student
init|=
name|fetchStudent
argument_list|(
name|externalId
argument_list|,
name|session
operator|.
name|getSessionId
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|student
operator|==
literal|null
condition|)
continue|continue;
name|loadCourses
argument_list|(
name|element
argument_list|,
name|student
argument_list|,
name|session
argument_list|)
expr_stmt|;
name|flushIfNeeded
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
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
throw|throw
name|e
throw|;
block|}
finally|finally
block|{
name|flush
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
block|}
name|Student
name|fetchStudent
parameter_list|(
name|String
name|externalId
parameter_list|,
name|Long
name|sessionId
parameter_list|)
block|{
return|return
operator|(
name|Student
operator|)
name|this
operator|.
name|getHibSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select distinct a from Student as a where a.externalUniqueId=:externalId and a.session.uniqueId=:sessionId"
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
name|setString
argument_list|(
literal|"externalId"
argument_list|,
name|externalId
argument_list|)
operator|.
name|setCacheable
argument_list|(
literal|true
argument_list|)
operator|.
name|uniqueResult
argument_list|()
return|;
block|}
specifier|private
name|void
name|loadCourses
parameter_list|(
name|Element
name|studentEl
parameter_list|,
name|Student
name|student
parameter_list|,
name|Session
name|session
parameter_list|)
throws|throws
name|Exception
block|{
for|for
control|(
name|Iterator
name|it
init|=
name|studentEl
operator|.
name|elementIterator
argument_list|()
init|;
name|it
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|Element
name|el
init|=
operator|(
name|Element
operator|)
name|it
operator|.
name|next
argument_list|()
decl_stmt|;
name|String
name|subject
init|=
name|el
operator|.
name|attributeValue
argument_list|(
literal|"subject"
argument_list|)
decl_stmt|;
if|if
condition|(
name|subject
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|Exception
argument_list|(
literal|"Subject is required."
argument_list|)
throw|;
block|}
name|String
name|courseNumber
init|=
name|el
operator|.
name|attributeValue
argument_list|(
literal|"courseNumber"
argument_list|)
decl_stmt|;
if|if
condition|(
name|courseNumber
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|Exception
argument_list|(
literal|"Course Number is required."
argument_list|)
throw|;
block|}
name|SubjectArea
name|area
init|=
name|fetchSubjectArea
argument_list|(
name|subject
argument_list|,
name|session
operator|.
name|getSessionId
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|area
operator|==
literal|null
condition|)
block|{
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"Subject area "
operator|+
name|subject
operator|+
literal|" not found"
argument_list|)
expr_stmt|;
continue|continue;
block|}
name|LastLikeCourseDemand
name|demand
init|=
operator|new
name|LastLikeCourseDemand
argument_list|()
decl_stmt|;
name|CourseOffering
name|courseOffering
init|=
name|fetchCourseOffering
argument_list|(
name|courseNumber
argument_list|,
name|area
operator|.
name|getUniqueId
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|courseOffering
operator|==
literal|null
condition|)
block|{
name|demand
operator|.
name|setCoursePermId
argument_list|(
literal|null
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|demand
operator|.
name|setCoursePermId
argument_list|(
name|courseOffering
operator|.
name|getPermId
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|demand
operator|.
name|setCourseNbr
argument_list|(
name|courseNumber
argument_list|)
expr_stmt|;
name|demand
operator|.
name|setStudent
argument_list|(
name|student
argument_list|)
expr_stmt|;
name|demand
operator|.
name|setSubjectArea
argument_list|(
name|area
argument_list|)
expr_stmt|;
name|demand
operator|.
name|setPriority
argument_list|(
name|Integer
operator|.
name|decode
argument_list|(
name|el
operator|.
name|attributeValue
argument_list|(
literal|"priority"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|getHibSession
argument_list|()
operator|.
name|save
argument_list|(
name|demand
argument_list|)
expr_stmt|;
block|}
block|}
name|SubjectArea
name|fetchSubjectArea
parameter_list|(
name|String
name|subjectAreaAbbv
parameter_list|,
name|Long
name|sessionId
parameter_list|)
block|{
return|return
operator|(
name|SubjectArea
operator|)
operator|new
name|SubjectAreaDAO
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select distinct a from SubjectArea as a where a.session.uniqueId=:sessionId and a.subjectAreaAbbreviation=:subjectAreaAbbv"
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
name|setString
argument_list|(
literal|"subjectAreaAbbv"
argument_list|,
name|subjectAreaAbbv
argument_list|)
operator|.
name|setCacheable
argument_list|(
literal|true
argument_list|)
operator|.
name|uniqueResult
argument_list|()
return|;
block|}
name|CourseOffering
name|fetchCourseOffering
parameter_list|(
name|String
name|courseNbr
parameter_list|,
name|Long
name|subjectAreaId
parameter_list|)
block|{
return|return
operator|(
name|CourseOffering
operator|)
operator|new
name|CourseOfferingDAO
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select distinct a from CourseOffering as a where a.courseNbr=:courseNbr and a.subjectArea=:subjectAreaId"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"subjectAreaId"
argument_list|,
name|subjectAreaId
operator|.
name|longValue
argument_list|()
argument_list|)
operator|.
name|setString
argument_list|(
literal|"courseNbr"
argument_list|,
name|courseNbr
argument_list|)
operator|.
name|setCacheable
argument_list|(
literal|true
argument_list|)
operator|.
name|uniqueResult
argument_list|()
return|;
block|}
block|}
end_class

end_unit

