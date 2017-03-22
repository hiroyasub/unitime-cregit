begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * Licensed to The Apereo Foundation under one or more contributor license  * agreements. See the NOTICE file distributed with this work for  * additional information regarding copyright ownership.  *  * The Apereo Foundation licenses this file to you under the Apache License,  * Version 2.0 (the "License"); you may not use this file except in  * compliance with the License. You may obtain a copy of the License at:  *  * http://www.apache.org/licenses/LICENSE-2.0  *  * Unless required by applicable law or agreed to in writing, software  * distributed under the License is distributed on an "AS IS" BASIS,  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  *  * See the License for the specific language governing permissions and  * limitations under the License.  *  */
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
name|StudentAreaClassificationMajor
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
name|StudentAreaClassificationMinor
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
name|exportStudent
argument_list|(
name|studentEl
argument_list|,
name|student
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
block|}
block|}
specifier|protected
name|void
name|exportStudent
parameter_list|(
name|Element
name|studentEl
parameter_list|,
name|Student
name|student
parameter_list|)
block|{
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
name|getAreaClasfMajors
argument_list|()
operator|.
name|isEmpty
argument_list|()
operator|||
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
literal|"studentAcadAreaClass"
argument_list|)
decl_stmt|;
name|Set
argument_list|<
name|String
argument_list|>
name|ac
init|=
operator|new
name|HashSet
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|StudentAreaClassificationMajor
name|aac
range|:
name|student
operator|.
name|getAreaClasfMajors
argument_list|()
control|)
block|{
if|if
condition|(
name|ac
operator|.
name|add
argument_list|(
name|aac
operator|.
name|getAcademicArea
argument_list|()
operator|.
name|getAcademicAreaAbbreviation
argument_list|()
operator|+
literal|"|"
operator|+
name|aac
operator|.
name|getAcademicClassification
argument_list|()
operator|.
name|getCode
argument_list|()
argument_list|)
condition|)
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
for|for
control|(
name|StudentAreaClassificationMinor
name|aac
range|:
name|student
operator|.
name|getAreaClasfMinors
argument_list|()
control|)
block|{
if|if
condition|(
name|ac
operator|.
name|add
argument_list|(
name|aac
operator|.
name|getAcademicArea
argument_list|()
operator|.
name|getAcademicAreaAbbreviation
argument_list|()
operator|+
literal|"|"
operator|+
name|aac
operator|.
name|getAcademicClassification
argument_list|()
operator|.
name|getCode
argument_list|()
argument_list|)
condition|)
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
name|getAreaClasfMajors
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
name|StudentAreaClassificationMajor
name|aac
range|:
name|student
operator|.
name|getAreaClasfMajors
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
operator|.
name|addAttribute
argument_list|(
literal|"code"
argument_list|,
name|aac
operator|.
name|getMajor
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
name|getAreaClasfMinors
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
name|StudentAreaClassificationMinor
name|aac
range|:
name|student
operator|.
name|getAreaClasfMinors
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
operator|.
name|addAttribute
argument_list|(
literal|"code"
argument_list|,
name|aac
operator|.
name|getMinor
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
block|}
end_class

end_unit

