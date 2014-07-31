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
name|StudentGroup
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|StudentExport
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
literal|"students"
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
literal|"students"
argument_list|,
literal|"-//UniTime//UniTime Students DTD/EN"
argument_list|,
literal|"http://www.unitime.org/interface/Student.dtd"
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
if|if
condition|(
name|student
operator|.
name|getFirstName
argument_list|()
operator|!=
literal|null
condition|)
name|studentEl
operator|.
name|addAttribute
argument_list|(
literal|"firstName"
argument_list|,
name|student
operator|.
name|getFirstName
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|student
operator|.
name|getMiddleName
argument_list|()
operator|!=
literal|null
condition|)
name|studentEl
operator|.
name|addAttribute
argument_list|(
literal|"middleName"
argument_list|,
name|student
operator|.
name|getMiddleName
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|student
operator|.
name|getLastName
argument_list|()
operator|!=
literal|null
condition|)
name|studentEl
operator|.
name|addAttribute
argument_list|(
literal|"lastName"
argument_list|,
name|student
operator|.
name|getLastName
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|student
operator|.
name|getEmail
argument_list|()
operator|!=
literal|null
condition|)
name|studentEl
operator|.
name|addAttribute
argument_list|(
literal|"email"
argument_list|,
name|student
operator|.
name|getEmail
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
name|student
operator|.
name|getAcademicAreaClassifications
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|Element
name|e
init|=
name|studentEl
operator|.
name|addElement
argument_list|(
literal|"studentAcadAreaClass"
argument_list|)
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
block|{
name|e
operator|.
name|addElement
argument_list|(
literal|"acadAreaClass"
argument_list|)
operator|.
name|addAttribute
argument_list|(
literal|"academicArea"
argument_list|,
name|aac
operator|.
name|getAcademicArea
argument_list|()
operator|.
name|getAcademicAreaAbbreviation
argument_list|()
argument_list|)
operator|.
name|addAttribute
argument_list|(
literal|"academicClass"
argument_list|,
name|aac
operator|.
name|getAcademicClassification
argument_list|()
operator|.
name|getCode
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
if|if
condition|(
operator|!
name|student
operator|.
name|getPosMajors
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|Element
name|e
init|=
name|studentEl
operator|.
name|addElement
argument_list|(
literal|"studentMajors"
argument_list|)
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
block|{
name|e
operator|.
name|addElement
argument_list|(
literal|"major"
argument_list|)
operator|.
name|addAttribute
argument_list|(
literal|"academicArea"
argument_list|,
name|area
operator|.
name|getAcademicAreaAbbreviation
argument_list|()
argument_list|)
operator|.
name|addAttribute
argument_list|(
literal|"code"
argument_list|,
name|major
operator|.
name|getCode
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
if|if
condition|(
operator|!
name|student
operator|.
name|getPosMinors
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|Element
name|e
init|=
name|studentEl
operator|.
name|addElement
argument_list|(
literal|"studentMinors"
argument_list|)
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
block|{
name|e
operator|.
name|addElement
argument_list|(
literal|"minor"
argument_list|)
operator|.
name|addAttribute
argument_list|(
literal|"academicArea"
argument_list|,
name|area
operator|.
name|getAcademicAreaAbbreviation
argument_list|()
argument_list|)
operator|.
name|addAttribute
argument_list|(
literal|"code"
argument_list|,
name|minor
operator|.
name|getCode
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
if|if
condition|(
operator|!
name|student
operator|.
name|getGroups
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|Element
name|e
init|=
name|studentEl
operator|.
name|addElement
argument_list|(
literal|"studentGroups"
argument_list|)
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
block|{
name|e
operator|.
name|addElement
argument_list|(
literal|"studentGroup"
argument_list|)
operator|.
name|addAttribute
argument_list|(
literal|"group"
argument_list|,
name|group
operator|.
name|getGroupAbbreviation
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
if|if
condition|(
operator|!
name|student
operator|.
name|getAccomodations
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|Element
name|e
init|=
name|studentEl
operator|.
name|addElement
argument_list|(
literal|"studentAccomodations"
argument_list|)
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
block|{
name|e
operator|.
name|addElement
argument_list|(
literal|"studentAccomodation"
argument_list|)
operator|.
name|addAttribute
argument_list|(
literal|"accomodation"
argument_list|,
name|accomodation
operator|.
name|getAbbreviation
argument_list|()
argument_list|)
expr_stmt|;
block|}
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

