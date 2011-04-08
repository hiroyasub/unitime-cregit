begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.2 (University Timetabling Application)  * Copyright (C) 2008-2009, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
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
name|HashSet
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Hashtable
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
name|Set
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
name|StudentSectioningQueue
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
name|StudentDAO
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
name|_RootDAO
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
name|boolean
name|trimLeadingZerosFromExternalId
init|=
literal|"true"
operator|.
name|equals
argument_list|(
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"tmtbl.data.exchange.trim.externalId"
argument_list|,
literal|"false"
argument_list|)
argument_list|)
decl_stmt|;
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
literal|"studentEnrollments"
argument_list|)
condition|)
throw|throw
operator|new
name|Exception
argument_list|(
literal|"Given XML file is not a Student Enrollments load file."
argument_list|)
throw|;
name|Session
name|session
init|=
literal|null
decl_stmt|;
name|Set
argument_list|<
name|Long
argument_list|>
name|updatedStudents
init|=
operator|new
name|HashSet
argument_list|<
name|Long
argument_list|>
argument_list|()
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
throw|throw
operator|new
name|Exception
argument_list|(
literal|"No session found for the given campus, year, and term."
argument_list|)
throw|;
name|HashMap
argument_list|<
name|String
argument_list|,
name|Class_
argument_list|>
name|extId2class
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
name|Class_
argument_list|>
name|name2class
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
name|extId2course
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
name|HashMap
argument_list|<
name|String
argument_list|,
name|CourseOffering
argument_list|>
name|name2course
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
name|HashMap
argument_list|<
name|String
argument_list|,
name|CourseOffering
argument_list|>
name|cextId2course
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
name|HashMap
argument_list|<
name|String
argument_list|,
name|CourseOffering
argument_list|>
name|cname2course
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
name|HashMap
argument_list|<
name|Long
argument_list|,
name|Set
argument_list|<
name|CourseOffering
argument_list|>
argument_list|>
name|class2courses
init|=
operator|new
name|HashMap
argument_list|<
name|Long
argument_list|,
name|Set
argument_list|<
name|CourseOffering
argument_list|>
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|Object
index|[]
name|o
range|:
operator|(
name|List
argument_list|<
name|Object
index|[]
argument_list|>
operator|)
name|getHibSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select c, co from Class_ c inner join c.schedulingSubpart.instrOfferingConfig.instructionalOffering.courseOfferings co where "
operator|+
literal|"c.schedulingSubpart.instrOfferingConfig.instructionalOffering.session.uniqueId = :sessionId"
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
name|Class_
name|clazz
init|=
operator|(
name|Class_
operator|)
name|o
index|[
literal|0
index|]
decl_stmt|;
name|CourseOffering
name|course
init|=
operator|(
name|CourseOffering
operator|)
name|o
index|[
literal|1
index|]
decl_stmt|;
name|String
name|extId
init|=
name|clazz
operator|.
name|getExternalId
argument_list|(
name|course
argument_list|)
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
name|extId2class
operator|.
name|put
argument_list|(
name|extId
argument_list|,
name|clazz
argument_list|)
expr_stmt|;
name|String
name|name
init|=
name|clazz
operator|.
name|getClassLabel
argument_list|(
name|course
argument_list|)
decl_stmt|;
name|name2class
operator|.
name|put
argument_list|(
name|name
argument_list|,
name|clazz
argument_list|)
expr_stmt|;
name|name2course
operator|.
name|put
argument_list|(
name|name
argument_list|,
name|course
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
name|extId2course
operator|.
name|containsKey
argument_list|(
name|extId
argument_list|)
operator|||
name|course
operator|.
name|isIsControl
argument_list|()
condition|)
name|extId2course
operator|.
name|put
argument_list|(
name|extId
argument_list|,
name|course
argument_list|)
expr_stmt|;
name|Set
argument_list|<
name|CourseOffering
argument_list|>
name|courses
init|=
name|class2courses
operator|.
name|get
argument_list|(
name|clazz
operator|.
name|getUniqueId
argument_list|()
argument_list|)
decl_stmt|;
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
name|cextId2course
operator|.
name|put
argument_list|(
name|course
operator|.
name|getExternalUniqueId
argument_list|()
argument_list|,
name|course
argument_list|)
expr_stmt|;
name|cname2course
operator|.
name|put
argument_list|(
name|course
operator|.
name|getCourseName
argument_list|()
argument_list|,
name|course
argument_list|)
expr_stmt|;
if|if
condition|(
name|courses
operator|==
literal|null
condition|)
block|{
name|courses
operator|=
operator|new
name|HashSet
argument_list|<
name|CourseOffering
argument_list|>
argument_list|()
expr_stmt|;
name|class2courses
operator|.
name|put
argument_list|(
name|clazz
operator|.
name|getUniqueId
argument_list|()
argument_list|,
name|courses
argument_list|)
expr_stmt|;
block|}
name|courses
operator|.
name|add
argument_list|(
name|course
argument_list|)
expr_stmt|;
block|}
name|debug
argument_list|(
literal|"classes loaded"
argument_list|)
expr_stmt|;
if|if
condition|(
name|created
operator|!=
literal|null
condition|)
name|ChangeLog
operator|.
name|addChange
argument_list|(
name|getHibSession
argument_list|()
argument_list|,
name|getManager
argument_list|()
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
name|Hashtable
argument_list|<
name|String
argument_list|,
name|Student
argument_list|>
name|students
init|=
operator|new
name|Hashtable
argument_list|<
name|String
argument_list|,
name|Student
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|Student
name|student
range|:
name|StudentDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|findBySession
argument_list|(
name|getHibSession
argument_list|()
argument_list|,
name|session
operator|.
name|getUniqueId
argument_list|()
argument_list|)
control|)
block|{
if|if
condition|(
name|student
operator|.
name|getExternalUniqueId
argument_list|()
operator|!=
literal|null
condition|)
name|students
operator|.
name|put
argument_list|(
name|student
operator|.
name|getExternalUniqueId
argument_list|()
argument_list|,
name|student
argument_list|)
expr_stmt|;
block|}
for|for
control|(
name|Iterator
name|i
init|=
name|rootElement
operator|.
name|elementIterator
argument_list|(
literal|"student"
argument_list|)
init|;
name|i
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
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|String
name|externalId
init|=
name|studentElement
operator|.
name|attributeValue
argument_list|(
literal|"externalId"
argument_list|)
decl_stmt|;
if|if
condition|(
name|externalId
operator|==
literal|null
condition|)
continue|continue;
while|while
condition|(
name|trimLeadingZerosFromExternalId
operator|&&
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
name|Student
name|student
init|=
name|students
operator|.
name|remove
argument_list|(
name|externalId
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
name|student
operator|.
name|setFirstName
argument_list|(
name|studentElement
operator|.
name|attributeValue
argument_list|(
literal|"firstName"
argument_list|,
literal|"Name"
argument_list|)
argument_list|)
expr_stmt|;
name|student
operator|.
name|setMiddleName
argument_list|(
name|studentElement
operator|.
name|attributeValue
argument_list|(
literal|"middleName"
argument_list|)
argument_list|)
expr_stmt|;
name|student
operator|.
name|setLastName
argument_list|(
name|studentElement
operator|.
name|attributeValue
argument_list|(
literal|"lastName"
argument_list|,
literal|"Unknown"
argument_list|)
argument_list|)
expr_stmt|;
name|student
operator|.
name|setEmail
argument_list|(
name|studentElement
operator|.
name|attributeValue
argument_list|(
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
literal|0
argument_list|)
expr_stmt|;
name|student
operator|.
name|setSchedulePreference
argument_list|(
literal|0
argument_list|)
expr_stmt|;
name|student
operator|.
name|setClassEnrollments
argument_list|(
operator|new
name|HashSet
argument_list|<
name|StudentClassEnrollment
argument_list|>
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|Hashtable
argument_list|<
name|Long
argument_list|,
name|StudentClassEnrollment
argument_list|>
name|enrollments
init|=
operator|new
name|Hashtable
argument_list|<
name|Long
argument_list|,
name|StudentClassEnrollment
argument_list|>
argument_list|()
decl_stmt|;
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
name|enrollments
operator|.
name|put
argument_list|(
name|enrollment
operator|.
name|getClazz
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|,
name|enrollment
argument_list|)
expr_stmt|;
block|}
for|for
control|(
name|Iterator
name|j
init|=
name|studentElement
operator|.
name|elementIterator
argument_list|(
literal|"class"
argument_list|)
init|;
name|j
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
name|j
operator|.
name|next
argument_list|()
decl_stmt|;
name|Class_
name|clazz
init|=
literal|null
decl_stmt|;
name|CourseOffering
name|course
init|=
literal|null
decl_stmt|;
name|String
name|classExternalId
init|=
name|classElement
operator|.
name|attributeValue
argument_list|(
literal|"externalId"
argument_list|)
decl_stmt|;
if|if
condition|(
name|classExternalId
operator|!=
literal|null
condition|)
block|{
name|clazz
operator|=
name|extId2class
operator|.
name|get
argument_list|(
name|classExternalId
argument_list|)
expr_stmt|;
name|course
operator|=
name|extId2course
operator|.
name|get
argument_list|(
name|classExternalId
argument_list|)
expr_stmt|;
if|if
condition|(
name|clazz
operator|==
literal|null
condition|)
block|{
name|clazz
operator|=
name|name2class
operator|.
name|get
argument_list|(
name|classExternalId
argument_list|)
expr_stmt|;
name|course
operator|=
name|name2course
operator|.
name|get
argument_list|(
name|classExternalId
argument_list|)
expr_stmt|;
block|}
block|}
if|if
condition|(
name|clazz
operator|==
literal|null
operator|&&
name|classElement
operator|.
name|attributeValue
argument_list|(
literal|"name"
argument_list|)
operator|!=
literal|null
condition|)
block|{
name|String
name|className
init|=
name|classElement
operator|.
name|attributeValue
argument_list|(
literal|"name"
argument_list|)
decl_stmt|;
name|clazz
operator|=
name|name2class
operator|.
name|get
argument_list|(
name|className
argument_list|)
expr_stmt|;
name|course
operator|=
name|name2course
operator|.
name|get
argument_list|(
name|className
argument_list|)
expr_stmt|;
block|}
name|String
name|courseName
init|=
name|classElement
operator|.
name|attributeValue
argument_list|(
literal|"course"
argument_list|)
decl_stmt|;
if|if
condition|(
name|courseName
operator|!=
literal|null
condition|)
block|{
name|course
operator|=
name|cname2course
operator|.
name|get
argument_list|(
name|courseName
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|String
name|subject
init|=
name|classElement
operator|.
name|attributeValue
argument_list|(
literal|"subject"
argument_list|)
decl_stmt|;
name|String
name|courseNbr
init|=
name|classElement
operator|.
name|attributeValue
argument_list|(
literal|"courseNbr"
argument_list|)
decl_stmt|;
if|if
condition|(
name|subject
operator|!=
literal|null
operator|&&
name|courseNbr
operator|!=
literal|null
condition|)
name|course
operator|=
name|cname2course
operator|.
name|get
argument_list|(
name|subject
operator|+
literal|" "
operator|+
name|courseNbr
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|course
operator|!=
literal|null
operator|&&
name|clazz
operator|==
literal|null
condition|)
block|{
name|String
name|type
init|=
name|classElement
operator|.
name|attributeValue
argument_list|(
literal|"type"
argument_list|)
decl_stmt|;
name|String
name|suffix
init|=
name|classElement
operator|.
name|attributeValue
argument_list|(
literal|"suffix"
argument_list|)
decl_stmt|;
if|if
condition|(
name|type
operator|!=
literal|null
operator|&&
name|suffix
operator|!=
literal|null
condition|)
name|clazz
operator|=
name|name2class
operator|.
name|get
argument_list|(
name|course
operator|.
name|getCourseName
argument_list|()
operator|+
literal|" "
operator|+
name|type
operator|.
name|trim
argument_list|()
operator|+
literal|" "
operator|+
name|suffix
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|clazz
operator|==
literal|null
condition|)
block|{
name|warn
argument_list|(
literal|"Class "
operator|+
operator|(
name|classExternalId
operator|!=
literal|null
condition|?
name|classExternalId
else|:
name|classElement
operator|.
name|attributeValue
argument_list|(
literal|"name"
argument_list|,
name|classElement
operator|.
name|attributeValue
argument_list|(
literal|"course"
argument_list|,
name|classElement
operator|.
name|attributeValue
argument_list|(
literal|"subject"
argument_list|)
operator|+
literal|" "
operator|+
name|classElement
operator|.
name|attributeValue
argument_list|(
literal|"courseNbr"
argument_list|)
argument_list|)
operator|+
literal|" "
operator|+
name|classElement
operator|.
name|attributeValue
argument_list|(
literal|"type"
argument_list|)
operator|+
literal|" "
operator|+
name|classElement
operator|.
name|attributeValue
argument_list|(
literal|"suffix"
argument_list|)
argument_list|)
operator|)
operator|+
literal|" not found."
argument_list|)
expr_stmt|;
continue|continue;
block|}
name|Set
argument_list|<
name|CourseOffering
argument_list|>
name|courses
init|=
name|class2courses
operator|.
name|get
argument_list|(
name|clazz
operator|.
name|getUniqueId
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|course
operator|==
literal|null
operator|||
operator|!
name|courses
operator|.
name|contains
argument_list|(
name|course
argument_list|)
condition|)
block|{
for|for
control|(
name|CourseOffering
name|co
range|:
name|courses
control|)
if|if
condition|(
name|co
operator|.
name|isIsControl
argument_list|()
condition|)
block|{
name|course
operator|=
name|co
expr_stmt|;
break|break;
block|}
block|}
name|StudentClassEnrollment
name|enrollment
init|=
name|enrollments
operator|.
name|remove
argument_list|(
name|clazz
operator|.
name|getUniqueId
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|enrollment
operator|!=
literal|null
condition|)
continue|continue;
comment|// enrollment already exists
name|enrollment
operator|=
operator|new
name|StudentClassEnrollment
argument_list|()
expr_stmt|;
name|enrollment
operator|.
name|setStudent
argument_list|(
name|student
argument_list|)
expr_stmt|;
name|enrollment
operator|.
name|setClazz
argument_list|(
name|clazz
argument_list|)
expr_stmt|;
name|enrollment
operator|.
name|setCourseOffering
argument_list|(
name|course
argument_list|)
expr_stmt|;
name|enrollment
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
name|getClassEnrollments
argument_list|()
operator|.
name|add
argument_list|(
name|enrollment
argument_list|)
expr_stmt|;
if|if
condition|(
name|student
operator|.
name|getUniqueId
argument_list|()
operator|!=
literal|null
condition|)
name|updatedStudents
operator|.
name|add
argument_list|(
name|student
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
operator|!
name|enrollments
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
for|for
control|(
name|StudentClassEnrollment
name|enrollment
range|:
name|enrollments
operator|.
name|values
argument_list|()
control|)
block|{
name|student
operator|.
name|getClassEnrollments
argument_list|()
operator|.
name|remove
argument_list|(
name|enrollment
argument_list|)
expr_stmt|;
name|getHibSession
argument_list|()
operator|.
name|delete
argument_list|(
name|enrollment
argument_list|)
expr_stmt|;
name|updatedStudents
operator|.
name|add
argument_list|(
name|student
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
if|if
condition|(
name|student
operator|.
name|getUniqueId
argument_list|()
operator|==
literal|null
condition|)
block|{
name|updatedStudents
operator|.
name|add
argument_list|(
operator|(
name|Long
operator|)
name|getHibSession
argument_list|()
operator|.
name|save
argument_list|(
name|student
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|getHibSession
argument_list|()
operator|.
name|update
argument_list|(
name|student
argument_list|)
expr_stmt|;
block|}
name|flushIfNeeded
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
for|for
control|(
name|Student
name|student
range|:
name|students
operator|.
name|values
argument_list|()
control|)
block|{
for|for
control|(
name|Iterator
argument_list|<
name|StudentClassEnrollment
argument_list|>
name|i
init|=
name|student
operator|.
name|getClassEnrollments
argument_list|()
operator|.
name|iterator
argument_list|()
init|;
name|i
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|StudentClassEnrollment
name|enrollment
init|=
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|getHibSession
argument_list|()
operator|.
name|delete
argument_list|(
name|enrollment
argument_list|)
expr_stmt|;
name|i
operator|.
name|remove
argument_list|()
expr_stmt|;
name|updatedStudents
operator|.
name|add
argument_list|(
name|student
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|getHibSession
argument_list|()
operator|.
name|update
argument_list|(
name|student
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
operator|!
name|updatedStudents
operator|.
name|isEmpty
argument_list|()
condition|)
name|StudentSectioningQueue
operator|.
name|studentChanged
argument_list|(
name|getHibSession
argument_list|()
argument_list|,
name|session
operator|.
name|getUniqueId
argument_list|()
argument_list|,
name|updatedStudents
argument_list|)
expr_stmt|;
name|commitTransaction
argument_list|()
expr_stmt|;
name|debug
argument_list|(
name|updatedStudents
operator|.
name|size
argument_list|()
operator|+
literal|" students changed"
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
literal|"true"
argument_list|)
argument_list|)
condition|)
block|{
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
init|=
operator|new
name|_RootDAO
argument_list|()
operator|.
name|createNewSession
argument_list|()
decl_stmt|;
try|try
block|{
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
name|hibSession
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
name|hibSession
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
finally|finally
block|{
name|hibSession
operator|.
name|close
argument_list|()
expr_stmt|;
block|}
block|}
block|}
block|}
end_class

end_unit

