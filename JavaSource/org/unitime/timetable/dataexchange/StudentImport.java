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
name|Map
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
name|AcademicArea
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
name|AcademicAreaClassification
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
name|AcademicClassification
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
name|CourseDemand
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
name|PosMajor
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
name|PosMinor
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
name|StudentAccomodation
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
name|StudentGroup
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

begin_comment
comment|/**  * @author Tomas Muller, Timothy Almon  */
end_comment

begin_class
specifier|public
class|class
name|StudentImport
extends|extends
name|BaseImport
block|{
specifier|public
name|StudentImport
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
try|try
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
throw|throw
operator|new
name|Exception
argument_list|(
literal|"No session found for the given campus, year, and term."
argument_list|)
throw|;
name|beginTransaction
argument_list|()
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
literal|"from Student s where s.session.uniqueId=:sessionId and s.externalUniqueId is not null"
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
name|Map
argument_list|<
name|String
argument_list|,
name|AcademicArea
argument_list|>
name|abbv2area
init|=
operator|new
name|Hashtable
argument_list|<
name|String
argument_list|,
name|AcademicArea
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|AcademicArea
name|area
range|:
operator|(
name|List
argument_list|<
name|AcademicArea
argument_list|>
operator|)
name|getHibSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"from AcademicArea where session.uniqueId=:sessionId"
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
name|abbv2area
operator|.
name|put
argument_list|(
name|area
operator|.
name|getAcademicAreaAbbreviation
argument_list|()
argument_list|,
name|area
argument_list|)
expr_stmt|;
block|}
name|Map
argument_list|<
name|String
argument_list|,
name|AcademicClassification
argument_list|>
name|code2clasf
init|=
operator|new
name|Hashtable
argument_list|<
name|String
argument_list|,
name|AcademicClassification
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|AcademicClassification
name|clasf
range|:
operator|(
name|List
argument_list|<
name|AcademicClassification
argument_list|>
operator|)
name|getHibSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"from AcademicClassification where session.uniqueId=:sessionId"
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
name|code2clasf
operator|.
name|put
argument_list|(
name|clasf
operator|.
name|getCode
argument_list|()
argument_list|,
name|clasf
argument_list|)
expr_stmt|;
block|}
name|Map
argument_list|<
name|String
argument_list|,
name|PosMajor
argument_list|>
name|code2major
init|=
operator|new
name|Hashtable
argument_list|<
name|String
argument_list|,
name|PosMajor
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|PosMajor
name|major
range|:
operator|(
name|List
argument_list|<
name|PosMajor
argument_list|>
operator|)
name|getHibSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"from PosMajor where session.uniqueId=:sessionId"
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
for|for
control|(
name|AcademicArea
name|area
range|:
name|major
operator|.
name|getAcademicAreas
argument_list|()
control|)
name|code2major
operator|.
name|put
argument_list|(
name|area
operator|.
name|getAcademicAreaAbbreviation
argument_list|()
operator|+
literal|":"
operator|+
name|major
operator|.
name|getCode
argument_list|()
argument_list|,
name|major
argument_list|)
expr_stmt|;
block|}
name|Map
argument_list|<
name|String
argument_list|,
name|PosMinor
argument_list|>
name|code2minor
init|=
operator|new
name|Hashtable
argument_list|<
name|String
argument_list|,
name|PosMinor
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|PosMinor
name|minor
range|:
operator|(
name|List
argument_list|<
name|PosMinor
argument_list|>
operator|)
name|getHibSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"from PosMinor where session.uniqueId=:sessionId"
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
for|for
control|(
name|AcademicArea
name|area
range|:
name|minor
operator|.
name|getAcademicAreas
argument_list|()
control|)
name|code2minor
operator|.
name|put
argument_list|(
name|area
operator|.
name|getAcademicAreaAbbreviation
argument_list|()
operator|+
literal|":"
operator|+
name|minor
operator|.
name|getCode
argument_list|()
argument_list|,
name|minor
argument_list|)
expr_stmt|;
block|}
name|Map
argument_list|<
name|String
argument_list|,
name|StudentGroup
argument_list|>
name|code2group
init|=
operator|new
name|Hashtable
argument_list|<
name|String
argument_list|,
name|StudentGroup
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|StudentGroup
name|group
range|:
operator|(
name|List
argument_list|<
name|StudentGroup
argument_list|>
operator|)
name|getHibSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"from StudentGroup where session.uniqueId=:sessionId"
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
name|code2group
operator|.
name|put
argument_list|(
name|group
operator|.
name|getGroupAbbreviation
argument_list|()
argument_list|,
name|group
argument_list|)
expr_stmt|;
block|}
name|Map
argument_list|<
name|String
argument_list|,
name|StudentAccomodation
argument_list|>
name|code2accomodation
init|=
operator|new
name|Hashtable
argument_list|<
name|String
argument_list|,
name|StudentAccomodation
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|StudentAccomodation
name|accomodation
range|:
operator|(
name|List
argument_list|<
name|StudentAccomodation
argument_list|>
operator|)
name|getHibSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"from StudentAccomodation where session.uniqueId=:sessionId"
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
name|code2accomodation
operator|.
name|put
argument_list|(
name|accomodation
operator|.
name|getAbbreviation
argument_list|()
argument_list|,
name|accomodation
argument_list|)
expr_stmt|;
block|}
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
for|for
control|(
name|Iterator
name|i1
init|=
name|rootElement
operator|.
name|elementIterator
argument_list|()
init|;
name|i1
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
name|i1
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
name|String
name|fName
init|=
name|element
operator|.
name|attributeValue
argument_list|(
literal|"firstName"
argument_list|,
literal|"Name"
argument_list|)
decl_stmt|;
name|String
name|mName
init|=
name|element
operator|.
name|attributeValue
argument_list|(
literal|"middleName"
argument_list|)
decl_stmt|;
name|String
name|lName
init|=
name|element
operator|.
name|attributeValue
argument_list|(
literal|"lastName"
argument_list|,
literal|"Unknown"
argument_list|)
decl_stmt|;
name|String
name|email
init|=
name|element
operator|.
name|attributeValue
argument_list|(
literal|"email"
argument_list|)
decl_stmt|;
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
name|student
operator|.
name|setCourseDemands
argument_list|(
operator|new
name|HashSet
argument_list|<
name|CourseDemand
argument_list|>
argument_list|()
argument_list|)
expr_stmt|;
name|student
operator|.
name|setFirstName
argument_list|(
name|fName
argument_list|)
expr_stmt|;
name|student
operator|.
name|setMiddleName
argument_list|(
name|mName
argument_list|)
expr_stmt|;
name|student
operator|.
name|setLastName
argument_list|(
name|lName
argument_list|)
expr_stmt|;
name|student
operator|.
name|setEmail
argument_list|(
name|email
argument_list|)
expr_stmt|;
name|student
operator|.
name|setAcademicAreaClassifications
argument_list|(
operator|new
name|HashSet
argument_list|<
name|AcademicAreaClassification
argument_list|>
argument_list|()
argument_list|)
expr_stmt|;
name|student
operator|.
name|setPosMajors
argument_list|(
operator|new
name|HashSet
argument_list|<
name|PosMajor
argument_list|>
argument_list|()
argument_list|)
expr_stmt|;
name|student
operator|.
name|setPosMinors
argument_list|(
operator|new
name|HashSet
argument_list|<
name|PosMinor
argument_list|>
argument_list|()
argument_list|)
expr_stmt|;
name|student
operator|.
name|setGroups
argument_list|(
operator|new
name|HashSet
argument_list|<
name|StudentGroup
argument_list|>
argument_list|()
argument_list|)
expr_stmt|;
name|student
operator|.
name|setAccomodations
argument_list|(
operator|new
name|HashSet
argument_list|<
name|StudentAccomodation
argument_list|>
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
if|if
condition|(
operator|!
name|eq
argument_list|(
name|fName
argument_list|,
name|student
operator|.
name|getFirstName
argument_list|()
argument_list|)
condition|)
block|{
name|student
operator|.
name|setFirstName
argument_list|(
name|fName
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
if|if
condition|(
operator|!
name|eq
argument_list|(
name|mName
argument_list|,
name|student
operator|.
name|getMiddleName
argument_list|()
argument_list|)
condition|)
block|{
name|student
operator|.
name|setMiddleName
argument_list|(
name|mName
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
if|if
condition|(
operator|!
name|eq
argument_list|(
name|lName
argument_list|,
name|student
operator|.
name|getLastName
argument_list|()
argument_list|)
condition|)
block|{
name|student
operator|.
name|setLastName
argument_list|(
name|lName
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
if|if
condition|(
operator|!
name|eq
argument_list|(
name|email
argument_list|,
name|student
operator|.
name|getEmail
argument_list|()
argument_list|)
condition|)
block|{
name|student
operator|.
name|setEmail
argument_list|(
name|email
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
name|element
operator|.
name|element
argument_list|(
literal|"studentAcadAreaClass"
argument_list|)
operator|!=
literal|null
condition|)
block|{
name|Map
argument_list|<
name|String
argument_list|,
name|AcademicAreaClassification
argument_list|>
name|sAreaClasf
init|=
operator|new
name|Hashtable
argument_list|<
name|String
argument_list|,
name|AcademicAreaClassification
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|AcademicAreaClassification
name|aac
range|:
name|student
operator|.
name|getAcademicAreaClassifications
argument_list|()
control|)
name|sAreaClasf
operator|.
name|put
argument_list|(
name|aac
operator|.
name|getAcademicArea
argument_list|()
operator|.
name|getAcademicAreaAbbreviation
argument_list|()
operator|+
literal|":"
operator|+
name|aac
operator|.
name|getAcademicClassification
argument_list|()
operator|.
name|getCode
argument_list|()
argument_list|,
name|aac
argument_list|)
expr_stmt|;
for|for
control|(
name|Iterator
name|i2
init|=
name|element
operator|.
name|element
argument_list|(
literal|"studentAcadAreaClass"
argument_list|)
operator|.
name|elementIterator
argument_list|(
literal|"acadAreaClass"
argument_list|)
init|;
name|i2
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|Element
name|e
init|=
operator|(
name|Element
operator|)
name|i2
operator|.
name|next
argument_list|()
decl_stmt|;
name|String
name|area
init|=
name|e
operator|.
name|attributeValue
argument_list|(
literal|"academicArea"
argument_list|)
decl_stmt|;
name|String
name|clasf
init|=
name|e
operator|.
name|attributeValue
argument_list|(
literal|"academicClass"
argument_list|)
decl_stmt|;
if|if
condition|(
name|sAreaClasf
operator|.
name|remove
argument_list|(
name|area
operator|+
literal|":"
operator|+
name|clasf
argument_list|)
operator|==
literal|null
condition|)
block|{
name|AcademicAreaClassification
name|aac
init|=
operator|new
name|AcademicAreaClassification
argument_list|()
decl_stmt|;
if|if
condition|(
name|abbv2area
operator|.
name|get
argument_list|(
name|area
argument_list|)
operator|==
literal|null
condition|)
block|{
name|warn
argument_list|(
literal|"Academic area "
operator|+
name|area
operator|+
literal|" not known."
argument_list|)
expr_stmt|;
continue|continue;
block|}
name|aac
operator|.
name|setAcademicArea
argument_list|(
name|abbv2area
operator|.
name|get
argument_list|(
name|area
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|code2clasf
operator|.
name|get
argument_list|(
name|clasf
argument_list|)
operator|==
literal|null
condition|)
block|{
name|warn
argument_list|(
literal|"Academic classification "
operator|+
name|clasf
operator|+
literal|" not known."
argument_list|)
expr_stmt|;
continue|continue;
block|}
name|aac
operator|.
name|setAcademicClassification
argument_list|(
name|code2clasf
operator|.
name|get
argument_list|(
name|clasf
argument_list|)
argument_list|)
expr_stmt|;
name|aac
operator|.
name|setStudent
argument_list|(
name|student
argument_list|)
expr_stmt|;
name|student
operator|.
name|getAcademicAreaClassifications
argument_list|()
operator|.
name|add
argument_list|(
name|aac
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
block|}
for|for
control|(
name|AcademicAreaClassification
name|aac
range|:
name|sAreaClasf
operator|.
name|values
argument_list|()
control|)
block|{
name|student
operator|.
name|getAcademicAreaClassifications
argument_list|()
operator|.
name|remove
argument_list|(
name|aac
argument_list|)
expr_stmt|;
name|getHibSession
argument_list|()
operator|.
name|delete
argument_list|(
name|aac
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
block|}
if|if
condition|(
name|element
operator|.
name|element
argument_list|(
literal|"studentMajors"
argument_list|)
operator|!=
literal|null
condition|)
block|{
name|Map
argument_list|<
name|String
argument_list|,
name|PosMajor
argument_list|>
name|sMajors
init|=
operator|new
name|Hashtable
argument_list|<
name|String
argument_list|,
name|PosMajor
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|PosMajor
name|major
range|:
name|student
operator|.
name|getPosMajors
argument_list|()
control|)
for|for
control|(
name|AcademicArea
name|area
range|:
name|major
operator|.
name|getAcademicAreas
argument_list|()
control|)
name|sMajors
operator|.
name|put
argument_list|(
name|area
operator|.
name|getAcademicAreaAbbreviation
argument_list|()
operator|+
literal|":"
operator|+
name|major
operator|.
name|getCode
argument_list|()
argument_list|,
name|major
argument_list|)
expr_stmt|;
for|for
control|(
name|Iterator
name|i2
init|=
name|element
operator|.
name|element
argument_list|(
literal|"studentMajors"
argument_list|)
operator|.
name|elementIterator
argument_list|(
literal|"major"
argument_list|)
init|;
name|i2
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|Element
name|e
init|=
operator|(
name|Element
operator|)
name|i2
operator|.
name|next
argument_list|()
decl_stmt|;
name|String
name|area
init|=
name|e
operator|.
name|attributeValue
argument_list|(
literal|"academicArea"
argument_list|)
decl_stmt|;
name|String
name|code
init|=
name|e
operator|.
name|attributeValue
argument_list|(
literal|"code"
argument_list|)
decl_stmt|;
if|if
condition|(
name|sMajors
operator|.
name|remove
argument_list|(
name|area
operator|+
literal|":"
operator|+
name|code
argument_list|)
operator|==
literal|null
condition|)
block|{
name|PosMajor
name|major
init|=
name|code2major
operator|.
name|get
argument_list|(
name|area
operator|+
literal|":"
operator|+
name|code
argument_list|)
decl_stmt|;
if|if
condition|(
name|major
operator|==
literal|null
condition|)
block|{
name|warn
argument_list|(
literal|"Major"
operator|+
name|area
operator|+
literal|" "
operator|+
name|code
operator|+
literal|" not known."
argument_list|)
expr_stmt|;
continue|continue;
block|}
name|student
operator|.
name|getPosMajors
argument_list|()
operator|.
name|add
argument_list|(
name|major
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
block|}
for|for
control|(
name|PosMajor
name|major
range|:
name|sMajors
operator|.
name|values
argument_list|()
control|)
block|{
name|student
operator|.
name|getPosMajors
argument_list|()
operator|.
name|remove
argument_list|(
name|major
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
block|}
if|if
condition|(
name|element
operator|.
name|element
argument_list|(
literal|"studentMinors"
argument_list|)
operator|!=
literal|null
condition|)
block|{
name|Map
argument_list|<
name|String
argument_list|,
name|PosMinor
argument_list|>
name|sMinors
init|=
operator|new
name|Hashtable
argument_list|<
name|String
argument_list|,
name|PosMinor
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|PosMinor
name|minor
range|:
name|student
operator|.
name|getPosMinors
argument_list|()
control|)
for|for
control|(
name|AcademicArea
name|area
range|:
name|minor
operator|.
name|getAcademicAreas
argument_list|()
control|)
name|sMinors
operator|.
name|put
argument_list|(
name|area
operator|.
name|getAcademicAreaAbbreviation
argument_list|()
operator|+
literal|":"
operator|+
name|minor
operator|.
name|getCode
argument_list|()
argument_list|,
name|minor
argument_list|)
expr_stmt|;
for|for
control|(
name|Iterator
name|i2
init|=
name|element
operator|.
name|element
argument_list|(
literal|"studentMinors"
argument_list|)
operator|.
name|elementIterator
argument_list|(
literal|"minor"
argument_list|)
init|;
name|i2
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|Element
name|e
init|=
operator|(
name|Element
operator|)
name|i2
operator|.
name|next
argument_list|()
decl_stmt|;
name|String
name|area
init|=
name|e
operator|.
name|attributeValue
argument_list|(
literal|"academicArea"
argument_list|)
decl_stmt|;
name|String
name|code
init|=
name|e
operator|.
name|attributeValue
argument_list|(
literal|"code"
argument_list|)
decl_stmt|;
if|if
condition|(
name|sMinors
operator|.
name|remove
argument_list|(
name|area
operator|+
literal|":"
operator|+
name|code
argument_list|)
operator|==
literal|null
condition|)
block|{
name|PosMinor
name|minor
init|=
name|code2minor
operator|.
name|get
argument_list|(
name|area
operator|+
literal|":"
operator|+
name|code
argument_list|)
decl_stmt|;
if|if
condition|(
name|minor
operator|==
literal|null
condition|)
block|{
name|warn
argument_list|(
literal|"Minor"
operator|+
name|area
operator|+
literal|" "
operator|+
name|code
operator|+
literal|" not known."
argument_list|)
expr_stmt|;
continue|continue;
block|}
name|student
operator|.
name|getPosMinors
argument_list|()
operator|.
name|add
argument_list|(
name|minor
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
block|}
for|for
control|(
name|PosMinor
name|minor
range|:
name|sMinors
operator|.
name|values
argument_list|()
control|)
block|{
name|student
operator|.
name|getPosMinors
argument_list|()
operator|.
name|remove
argument_list|(
name|minor
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
block|}
if|if
condition|(
name|element
operator|.
name|element
argument_list|(
literal|"studentGroups"
argument_list|)
operator|!=
literal|null
condition|)
block|{
name|Map
argument_list|<
name|String
argument_list|,
name|StudentGroup
argument_list|>
name|sGroups
init|=
operator|new
name|Hashtable
argument_list|<
name|String
argument_list|,
name|StudentGroup
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|StudentGroup
name|group
range|:
name|student
operator|.
name|getGroups
argument_list|()
control|)
name|sGroups
operator|.
name|put
argument_list|(
name|group
operator|.
name|getGroupAbbreviation
argument_list|()
argument_list|,
name|group
argument_list|)
expr_stmt|;
for|for
control|(
name|Iterator
name|i2
init|=
name|element
operator|.
name|element
argument_list|(
literal|"studentGroups"
argument_list|)
operator|.
name|elementIterator
argument_list|(
literal|"studentGroup"
argument_list|)
init|;
name|i2
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|Element
name|e
init|=
operator|(
name|Element
operator|)
name|i2
operator|.
name|next
argument_list|()
decl_stmt|;
name|String
name|code
init|=
name|e
operator|.
name|attributeValue
argument_list|(
literal|"group"
argument_list|)
decl_stmt|;
if|if
condition|(
name|sGroups
operator|.
name|remove
argument_list|(
name|code
argument_list|)
operator|==
literal|null
condition|)
block|{
name|StudentGroup
name|group
init|=
name|code2group
operator|.
name|get
argument_list|(
name|code
argument_list|)
decl_stmt|;
if|if
condition|(
name|group
operator|==
literal|null
condition|)
block|{
name|warn
argument_list|(
literal|"Student group "
operator|+
name|code
operator|+
literal|" not known."
argument_list|)
expr_stmt|;
continue|continue;
block|}
name|student
operator|.
name|getGroups
argument_list|()
operator|.
name|add
argument_list|(
name|group
argument_list|)
expr_stmt|;
name|group
operator|.
name|getStudents
argument_list|()
operator|.
name|add
argument_list|(
name|student
argument_list|)
expr_stmt|;
name|getHibSession
argument_list|()
operator|.
name|saveOrUpdate
argument_list|(
name|group
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
block|}
for|for
control|(
name|StudentGroup
name|group
range|:
name|sGroups
operator|.
name|values
argument_list|()
control|)
block|{
if|if
condition|(
name|group
operator|.
name|getExternalUniqueId
argument_list|()
operator|==
literal|null
condition|)
continue|continue;
name|student
operator|.
name|getGroups
argument_list|()
operator|.
name|remove
argument_list|(
name|group
argument_list|)
expr_stmt|;
name|group
operator|.
name|getStudents
argument_list|()
operator|.
name|remove
argument_list|(
name|student
argument_list|)
expr_stmt|;
name|getHibSession
argument_list|()
operator|.
name|saveOrUpdate
argument_list|(
name|group
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
block|}
if|if
condition|(
name|element
operator|.
name|element
argument_list|(
literal|"studentAccomodations"
argument_list|)
operator|!=
literal|null
condition|)
block|{
name|Map
argument_list|<
name|String
argument_list|,
name|StudentAccomodation
argument_list|>
name|sAccomodations
init|=
operator|new
name|Hashtable
argument_list|<
name|String
argument_list|,
name|StudentAccomodation
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|StudentAccomodation
name|accomodation
range|:
name|student
operator|.
name|getAccomodations
argument_list|()
control|)
name|sAccomodations
operator|.
name|put
argument_list|(
name|accomodation
operator|.
name|getAbbreviation
argument_list|()
argument_list|,
name|accomodation
argument_list|)
expr_stmt|;
for|for
control|(
name|Iterator
name|i2
init|=
name|element
operator|.
name|element
argument_list|(
literal|"studentAccomodations"
argument_list|)
operator|.
name|elementIterator
argument_list|(
literal|"studentAccomodation"
argument_list|)
init|;
name|i2
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|Element
name|e
init|=
operator|(
name|Element
operator|)
name|i2
operator|.
name|next
argument_list|()
decl_stmt|;
name|String
name|code
init|=
name|e
operator|.
name|attributeValue
argument_list|(
literal|"accomodation"
argument_list|)
decl_stmt|;
if|if
condition|(
name|sAccomodations
operator|.
name|remove
argument_list|(
name|code
argument_list|)
operator|==
literal|null
condition|)
block|{
name|StudentAccomodation
name|accomodation
init|=
name|code2accomodation
operator|.
name|get
argument_list|(
name|code
argument_list|)
decl_stmt|;
if|if
condition|(
name|accomodation
operator|==
literal|null
condition|)
block|{
name|warn
argument_list|(
literal|"Student accomodation "
operator|+
name|code
operator|+
literal|" not known."
argument_list|)
expr_stmt|;
continue|continue;
block|}
name|student
operator|.
name|getAccomodations
argument_list|()
operator|.
name|add
argument_list|(
name|accomodation
argument_list|)
expr_stmt|;
name|accomodation
operator|.
name|getStudents
argument_list|()
operator|.
name|add
argument_list|(
name|student
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
block|}
for|for
control|(
name|StudentAccomodation
name|accomodation
range|:
name|sAccomodations
operator|.
name|values
argument_list|()
control|)
block|{
name|student
operator|.
name|getAccomodations
argument_list|()
operator|.
name|remove
argument_list|(
name|accomodation
argument_list|)
expr_stmt|;
name|accomodation
operator|.
name|getStudents
argument_list|()
operator|.
name|remove
argument_list|(
name|student
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
name|info
argument_list|(
name|updatedStudents
operator|.
name|size
argument_list|()
operator|+
literal|" students changed"
argument_list|)
expr_stmt|;
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
literal|null
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
block|}
specifier|private
name|boolean
name|eq
parameter_list|(
name|String
name|a
parameter_list|,
name|String
name|b
parameter_list|)
block|{
return|return
operator|(
name|a
operator|==
literal|null
condition|?
name|b
operator|==
literal|null
else|:
name|a
operator|.
name|equals
argument_list|(
name|b
argument_list|)
operator|)
return|;
block|}
block|}
end_class

end_unit

