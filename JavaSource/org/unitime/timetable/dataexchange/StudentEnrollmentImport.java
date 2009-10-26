begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.1 (University Timetabling Application)  * Copyright (C) 2008-2009, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 2 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program; if not, write to the Free Software Foundation, Inc.,  * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
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
name|HashMap
import|;
end_import

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
name|model
operator|.
name|ChangeLog
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
name|Exam
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
name|TimetableManager
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
name|test
operator|.
name|UpdateExamConflicts
import|;
end_import

begin_class
specifier|public
class|class
name|StudentEnrollmentImport
extends|extends
name|BaseImport
block|{
name|TimetableManager
name|manager
init|=
literal|null
decl_stmt|;
name|HashMap
argument_list|<
name|String
argument_list|,
name|Class_
argument_list|>
name|classes
init|=
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|Class_
argument_list|>
argument_list|()
decl_stmt|;
name|HashMap
argument_list|<
name|String
argument_list|,
name|CourseOffering
argument_list|>
name|controllingCourses
init|=
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|CourseOffering
argument_list|>
argument_list|()
decl_stmt|;
name|boolean
name|trimLeadingZerosFromExternalId
init|=
literal|false
decl_stmt|;
specifier|public
name|StudentEnrollmentImport
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|loadXml
parameter_list|(
name|Element
name|rootElement
parameter_list|)
throws|throws
name|Exception
block|{
name|String
name|rootElementName
init|=
literal|"studentEnrollments"
decl_stmt|;
name|String
name|trimLeadingZeros
init|=
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"tmtbl.data.exchange.trim.externalId"
argument_list|,
literal|"false"
argument_list|)
decl_stmt|;
if|if
condition|(
name|trimLeadingZeros
operator|.
name|equals
argument_list|(
literal|"true"
argument_list|)
condition|)
block|{
name|trimLeadingZerosFromExternalId
operator|=
literal|true
expr_stmt|;
block|}
if|if
condition|(
operator|!
name|rootElement
operator|.
name|getName
argument_list|()
operator|.
name|equalsIgnoreCase
argument_list|(
name|rootElementName
argument_list|)
condition|)
block|{
throw|throw
operator|new
name|Exception
argument_list|(
literal|"Given XML file is not a Student Enrollments load file."
argument_list|)
throw|;
block|}
name|Session
name|session
init|=
literal|null
decl_stmt|;
try|try
block|{
name|String
name|campus
init|=
name|rootElement
operator|.
name|attributeValue
argument_list|(
literal|"campus"
argument_list|)
decl_stmt|;
name|String
name|year
init|=
name|rootElement
operator|.
name|attributeValue
argument_list|(
literal|"year"
argument_list|)
decl_stmt|;
name|String
name|term
init|=
name|rootElement
operator|.
name|attributeValue
argument_list|(
literal|"term"
argument_list|)
decl_stmt|;
name|String
name|created
init|=
name|rootElement
operator|.
name|attributeValue
argument_list|(
literal|"created"
argument_list|)
decl_stmt|;
name|beginTransaction
argument_list|()
expr_stmt|;
name|session
operator|=
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
expr_stmt|;
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
name|loadClasses
argument_list|(
name|session
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
name|info
argument_list|(
literal|"classes loaded"
argument_list|)
expr_stmt|;
if|if
condition|(
name|manager
operator|==
literal|null
condition|)
block|{
name|manager
operator|=
name|findDefaultManager
argument_list|()
expr_stmt|;
block|}
if|if
condition|(
name|created
operator|!=
literal|null
condition|)
block|{
name|ChangeLog
operator|.
name|addChange
argument_list|(
name|getHibSession
argument_list|()
argument_list|,
name|manager
argument_list|,
name|session
argument_list|,
name|session
argument_list|,
name|created
argument_list|,
name|ChangeLog
operator|.
name|Source
operator|.
name|DATA_IMPORT_STUDENT_ENROLLMENTS
argument_list|,
name|ChangeLog
operator|.
name|Operation
operator|.
name|UPDATE
argument_list|,
literal|null
argument_list|,
literal|null
argument_list|)
expr_stmt|;
block|}
comment|/*               * If some records of a table related to students need to be explicitly deleted,               * hibernate can also be used to delete them. For instance, the following query               * deletes all student class enrollments for given academic session:              *                 * delete StudentClassEnrollment sce where sce.student.uniqueId in              *      (select s.uniqueId from Student s where s.session.uniqueId=:sessionId)              */
name|getHibSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"delete StudentClassEnrollment sce where sce.student.uniqueId in (select s.uniqueId from Student s where s.session.uniqueId=:sessionId)"
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
operator|.
name|longValue
argument_list|()
argument_list|)
operator|.
name|executeUpdate
argument_list|()
expr_stmt|;
name|flush
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|String
name|elementName
init|=
literal|"student"
decl_stmt|;
for|for
control|(
name|Iterator
name|it
init|=
name|rootElement
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
name|studentElement
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
name|getRequiredStringAttribute
argument_list|(
name|studentElement
argument_list|,
literal|"externalId"
argument_list|,
name|elementName
argument_list|)
decl_stmt|;
if|if
condition|(
name|trimLeadingZerosFromExternalId
condition|)
block|{
while|while
condition|(
name|externalId
operator|.
name|startsWith
argument_list|(
literal|"0"
argument_list|)
condition|)
name|externalId
operator|=
name|externalId
operator|.
name|substring
argument_list|(
literal|1
argument_list|)
expr_stmt|;
block|}
name|Student
name|student
init|=
name|fetchStudent
argument_list|(
name|externalId
argument_list|,
name|session
operator|.
name|getUniqueId
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|student
operator|==
literal|null
condition|)
block|{
name|student
operator|=
operator|new
name|Student
argument_list|()
expr_stmt|;
name|student
operator|.
name|setSession
argument_list|(
name|session
argument_list|)
expr_stmt|;
name|String
name|firstName
init|=
name|getOptionalStringAttribute
argument_list|(
name|studentElement
argument_list|,
literal|"firstName"
argument_list|)
decl_stmt|;
name|student
operator|.
name|setFirstName
argument_list|(
name|firstName
operator|==
literal|null
condition|?
literal|"Name"
else|:
name|firstName
argument_list|)
expr_stmt|;
name|student
operator|.
name|setMiddleName
argument_list|(
name|getOptionalStringAttribute
argument_list|(
name|studentElement
argument_list|,
literal|"middleName"
argument_list|)
argument_list|)
expr_stmt|;
name|String
name|lastName
init|=
name|getOptionalStringAttribute
argument_list|(
name|studentElement
argument_list|,
literal|"lastName"
argument_list|)
decl_stmt|;
name|student
operator|.
name|setLastName
argument_list|(
name|lastName
operator|==
literal|null
condition|?
literal|"Unknown"
else|:
name|lastName
argument_list|)
expr_stmt|;
name|student
operator|.
name|setEmail
argument_list|(
name|getOptionalStringAttribute
argument_list|(
name|studentElement
argument_list|,
literal|"email"
argument_list|)
argument_list|)
expr_stmt|;
name|student
operator|.
name|setExternalUniqueId
argument_list|(
name|externalId
argument_list|)
expr_stmt|;
name|student
operator|.
name|setFreeTimeCategory
argument_list|(
operator|new
name|Integer
argument_list|(
literal|0
argument_list|)
argument_list|)
expr_stmt|;
name|student
operator|.
name|setSchedulePreference
argument_list|(
operator|new
name|Integer
argument_list|(
literal|0
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|elementClass
argument_list|(
name|studentElement
argument_list|,
name|student
argument_list|)
expr_stmt|;
name|getHibSession
argument_list|()
operator|.
name|save
argument_list|(
name|student
argument_list|)
expr_stmt|;
name|flushIfNeeded
argument_list|(
literal|true
argument_list|)
expr_stmt|;
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
throw|throw
name|e
throw|;
block|}
if|if
condition|(
name|session
operator|!=
literal|null
operator|&&
literal|"true"
operator|.
name|equals
argument_list|(
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"tmtbl.data.import.studentEnrl.finalExam.updateConflicts"
argument_list|,
literal|"false"
argument_list|)
argument_list|)
condition|)
block|{
try|try
block|{
name|beginTransaction
argument_list|()
expr_stmt|;
operator|new
name|UpdateExamConflicts
argument_list|(
name|this
argument_list|)
operator|.
name|update
argument_list|(
name|session
operator|.
name|getUniqueId
argument_list|()
argument_list|,
name|Exam
operator|.
name|sExamTypeFinal
argument_list|,
name|getHibSession
argument_list|()
argument_list|)
expr_stmt|;
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
if|if
condition|(
name|session
operator|!=
literal|null
operator|&&
literal|"true"
operator|.
name|equals
argument_list|(
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"tmtbl.data.import.studentEnrl.midtermExam.updateConflicts"
argument_list|,
literal|"false"
argument_list|)
argument_list|)
condition|)
block|{
try|try
block|{
name|beginTransaction
argument_list|()
expr_stmt|;
operator|new
name|UpdateExamConflicts
argument_list|(
name|this
argument_list|)
operator|.
name|update
argument_list|(
name|session
operator|.
name|getUniqueId
argument_list|()
argument_list|,
name|Exam
operator|.
name|sExamTypeMidterm
argument_list|,
name|getHibSession
argument_list|()
argument_list|)
expr_stmt|;
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
name|info
argument_list|(
literal|"Appliation property: tmtbl.data.import.studentEnrl.class.updateEnrollments = "
operator|+
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"tmtbl.data.import.studentEnrl.class.updateEnrollments"
argument_list|,
literal|"false"
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|session
operator|!=
literal|null
operator|&&
literal|"true"
operator|.
name|equals
argument_list|(
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"tmtbl.data.import.studentEnrl.class.updateEnrollments"
argument_list|,
literal|"false"
argument_list|)
argument_list|)
condition|)
block|{
try|try
block|{
name|beginTransaction
argument_list|()
expr_stmt|;
name|info
argument_list|(
literal|"  Updating class enrollments..."
argument_list|)
expr_stmt|;
name|Class_
operator|.
name|updateClassEnrollmentForSession
argument_list|(
name|session
argument_list|,
name|getHibSession
argument_list|()
argument_list|)
expr_stmt|;
name|info
argument_list|(
literal|"  Updating course offering enrollments..."
argument_list|)
expr_stmt|;
name|CourseOffering
operator|.
name|updateCourseOfferingEnrollmentForSession
argument_list|(
name|session
argument_list|,
name|getHibSession
argument_list|()
argument_list|)
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
block|}
block|}
block|}
specifier|private
name|void
name|elementClass
parameter_list|(
name|Element
name|studentElement
parameter_list|,
name|Student
name|student
parameter_list|)
throws|throws
name|Exception
block|{
name|String
name|elementName
init|=
literal|"class"
decl_stmt|;
for|for
control|(
name|Iterator
name|it
init|=
name|studentElement
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
name|classElement
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
name|getRequiredStringAttribute
argument_list|(
name|classElement
argument_list|,
literal|"externalId"
argument_list|,
name|elementName
argument_list|)
decl_stmt|;
name|Class_
name|clazz
init|=
name|classes
operator|.
name|get
argument_list|(
name|externalId
argument_list|)
decl_stmt|;
if|if
condition|(
name|clazz
operator|!=
literal|null
condition|)
block|{
name|StudentClassEnrollment
name|sce
init|=
operator|new
name|StudentClassEnrollment
argument_list|()
decl_stmt|;
name|sce
operator|.
name|setStudent
argument_list|(
name|student
argument_list|)
expr_stmt|;
name|sce
operator|.
name|setClazz
argument_list|(
name|clazz
argument_list|)
expr_stmt|;
name|sce
operator|.
name|setCourseOffering
argument_list|(
name|controllingCourses
operator|.
name|get
argument_list|(
name|externalId
argument_list|)
argument_list|)
expr_stmt|;
name|sce
operator|.
name|setTimestamp
argument_list|(
operator|new
name|java
operator|.
name|util
operator|.
name|Date
argument_list|()
argument_list|)
expr_stmt|;
name|student
operator|.
name|addToclassEnrollments
argument_list|(
name|sce
argument_list|)
expr_stmt|;
block|}
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
name|loadClasses
parameter_list|(
name|Long
name|sessionId
parameter_list|)
throws|throws
name|Exception
block|{
for|for
control|(
name|Iterator
argument_list|<
name|?
argument_list|>
name|it
init|=
name|Class_
operator|.
name|findAll
argument_list|(
name|sessionId
argument_list|)
operator|.
name|iterator
argument_list|()
init|;
name|it
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|Class_
name|c
init|=
operator|(
name|Class_
operator|)
name|it
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|c
operator|.
name|getExternalUniqueId
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|classes
operator|.
name|put
argument_list|(
name|c
operator|.
name|getExternalUniqueId
argument_list|()
argument_list|,
name|c
argument_list|)
expr_stmt|;
name|controllingCourses
operator|.
name|put
argument_list|(
name|c
operator|.
name|getExternalUniqueId
argument_list|()
argument_list|,
name|c
operator|.
name|getSchedulingSubpart
argument_list|()
operator|.
name|getControllingCourseOffering
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
end_class

end_unit

