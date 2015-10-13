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
name|ArrayList
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
name|Map
import|;
end_import

begin_import
import|import
name|org
operator|.
name|cpsolver
operator|.
name|ifs
operator|.
name|util
operator|.
name|ToolBox
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
name|commons
operator|.
name|hibernate
operator|.
name|util
operator|.
name|HibernateUtil
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
name|Curriculum
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
name|CurriculumClassification
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
name|CurriculumCourse
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
name|CurriculumCourseGroup
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
name|Department
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
name|util
operator|.
name|Constants
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|CurriculaImport
extends|extends
name|BaseImport
block|{
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
if|if
condition|(
operator|!
name|root
operator|.
name|getName
argument_list|()
operator|.
name|equalsIgnoreCase
argument_list|(
literal|"curricula"
argument_list|)
condition|)
block|{
throw|throw
operator|new
name|Exception
argument_list|(
literal|"Given XML file is not a Curricula load file."
argument_list|)
throw|;
block|}
try|try
block|{
name|beginTransaction
argument_list|()
expr_stmt|;
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
name|String
name|created
init|=
name|root
operator|.
name|attributeValue
argument_list|(
literal|"created"
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
name|DATA_IMPORT_CURRICULA
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
name|info
argument_list|(
literal|"Deleting existing curricula..."
argument_list|)
expr_stmt|;
for|for
control|(
name|Iterator
argument_list|<
name|Curriculum
argument_list|>
name|i
init|=
name|getHibSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select c from Curriculum c where c.department.session=:sessionId"
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
name|getHibSession
argument_list|()
operator|.
name|delete
argument_list|(
name|i
operator|.
name|next
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|flush
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|info
argument_list|(
literal|"Loading areas, departments, majors, and classifications..."
argument_list|)
expr_stmt|;
name|Hashtable
argument_list|<
name|String
argument_list|,
name|AcademicArea
argument_list|>
name|areasByAbbv
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
name|Hashtable
argument_list|<
name|String
argument_list|,
name|AcademicArea
argument_list|>
name|areasByExtId
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
literal|"select a from AcademicArea a where a.session.uniqueId = :sessionId"
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
name|areasByAbbv
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
if|if
condition|(
name|area
operator|.
name|getExternalUniqueId
argument_list|()
operator|!=
literal|null
condition|)
name|areasByExtId
operator|.
name|put
argument_list|(
name|area
operator|.
name|getExternalUniqueId
argument_list|()
argument_list|,
name|area
argument_list|)
expr_stmt|;
block|}
name|Hashtable
argument_list|<
name|String
argument_list|,
name|Department
argument_list|>
name|departmentsByCode
init|=
operator|new
name|Hashtable
argument_list|<
name|String
argument_list|,
name|Department
argument_list|>
argument_list|()
decl_stmt|;
name|Hashtable
argument_list|<
name|String
argument_list|,
name|Department
argument_list|>
name|departmentsByExtId
init|=
operator|new
name|Hashtable
argument_list|<
name|String
argument_list|,
name|Department
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|Department
name|dept
range|:
operator|(
name|List
argument_list|<
name|Department
argument_list|>
operator|)
name|getHibSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select a from Department a where a.session.uniqueId = :sessionId"
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
name|departmentsByCode
operator|.
name|put
argument_list|(
name|dept
operator|.
name|getDeptCode
argument_list|()
argument_list|,
name|dept
argument_list|)
expr_stmt|;
if|if
condition|(
name|dept
operator|.
name|getExternalUniqueId
argument_list|()
operator|!=
literal|null
condition|)
name|departmentsByExtId
operator|.
name|put
argument_list|(
name|dept
operator|.
name|getExternalUniqueId
argument_list|()
argument_list|,
name|dept
argument_list|)
expr_stmt|;
block|}
name|Hashtable
argument_list|<
name|String
argument_list|,
name|PosMajor
argument_list|>
name|majorsByCode
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
name|Hashtable
argument_list|<
name|String
argument_list|,
name|PosMajor
argument_list|>
name|majorsByExtId
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
literal|"select a from PosMajor a where a.session.uniqueId = :sessionId"
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
block|{
name|majorsByCode
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
if|if
condition|(
name|major
operator|.
name|getExternalUniqueId
argument_list|()
operator|!=
literal|null
condition|)
name|majorsByExtId
operator|.
name|put
argument_list|(
name|major
operator|.
name|getExternalUniqueId
argument_list|()
argument_list|,
name|major
argument_list|)
expr_stmt|;
block|}
name|Hashtable
argument_list|<
name|String
argument_list|,
name|AcademicClassification
argument_list|>
name|clasfsByCode
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
name|Hashtable
argument_list|<
name|String
argument_list|,
name|AcademicClassification
argument_list|>
name|clasfsByExtId
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
literal|"select a from AcademicClassification a where a.session.uniqueId = :sessionId"
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
name|clasfsByCode
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
if|if
condition|(
name|clasf
operator|.
name|getExternalUniqueId
argument_list|()
operator|!=
literal|null
condition|)
name|clasfsByExtId
operator|.
name|put
argument_list|(
name|clasf
operator|.
name|getExternalUniqueId
argument_list|()
argument_list|,
name|clasf
argument_list|)
expr_stmt|;
block|}
name|info
argument_list|(
literal|"Loading courses..."
argument_list|)
expr_stmt|;
name|Hashtable
argument_list|<
name|String
argument_list|,
name|CourseOffering
argument_list|>
name|corusesByExtId
init|=
operator|new
name|Hashtable
argument_list|<
name|String
argument_list|,
name|CourseOffering
argument_list|>
argument_list|()
decl_stmt|;
name|Hashtable
argument_list|<
name|String
argument_list|,
name|CourseOffering
argument_list|>
name|corusesBySubjectCourseNbr
init|=
operator|new
name|Hashtable
argument_list|<
name|String
argument_list|,
name|CourseOffering
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|CourseOffering
name|course
range|:
operator|(
name|List
argument_list|<
name|CourseOffering
argument_list|>
operator|)
name|getHibSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select a from CourseOffering a where a.subjectArea.session.uniqueId = :sessionId"
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
name|corusesBySubjectCourseNbr
operator|.
name|put
argument_list|(
name|course
operator|.
name|getSubjectArea
argument_list|()
operator|.
name|getSubjectAreaAbbreviation
argument_list|()
operator|+
literal|"|"
operator|+
name|course
operator|.
name|getCourseNbr
argument_list|()
argument_list|,
name|course
argument_list|)
expr_stmt|;
if|if
condition|(
name|course
operator|.
name|getExternalUniqueId
argument_list|()
operator|!=
literal|null
condition|)
name|corusesByExtId
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
block|}
name|info
argument_list|(
literal|"Importing curricula..."
argument_list|)
expr_stmt|;
name|curricula
label|:
for|for
control|(
name|Iterator
name|i
init|=
name|root
operator|.
name|elementIterator
argument_list|()
init|;
name|i
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|Element
name|curriculumElement
init|=
operator|(
name|Element
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|Curriculum
name|curriculum
init|=
operator|new
name|Curriculum
argument_list|()
decl_stmt|;
name|String
name|abbv
init|=
name|curriculumElement
operator|.
name|attributeValue
argument_list|(
literal|"abbreviation"
argument_list|)
decl_stmt|;
name|String
name|name
init|=
name|curriculumElement
operator|.
name|attributeValue
argument_list|(
literal|"name"
argument_list|)
decl_stmt|;
name|AcademicArea
name|area
init|=
literal|null
decl_stmt|;
for|for
control|(
name|Iterator
name|j
init|=
name|curriculumElement
operator|.
name|elementIterator
argument_list|(
literal|"academicArea"
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
name|areaElement
init|=
operator|(
name|Element
operator|)
name|j
operator|.
name|next
argument_list|()
decl_stmt|;
name|String
name|externalId
init|=
name|areaElement
operator|.
name|attributeValue
argument_list|(
literal|"externalId"
argument_list|)
decl_stmt|;
name|String
name|abbreviation
init|=
name|areaElement
operator|.
name|attributeValue
argument_list|(
literal|"abbreviation"
argument_list|)
decl_stmt|;
name|area
operator|=
operator|(
name|externalId
operator|!=
literal|null
condition|?
name|areasByExtId
operator|.
name|get
argument_list|(
name|externalId
argument_list|)
else|:
name|areasByAbbv
operator|.
name|get
argument_list|(
name|abbreviation
argument_list|)
operator|)
expr_stmt|;
if|if
condition|(
name|area
operator|==
literal|null
condition|)
block|{
name|error
argument_list|(
literal|"Academic area "
operator|+
name|areaElement
operator|.
name|asXML
argument_list|()
operator|+
literal|" does not exist."
argument_list|)
expr_stmt|;
continue|continue
name|curricula
continue|;
block|}
block|}
if|if
condition|(
name|area
operator|==
literal|null
condition|)
block|{
name|error
argument_list|(
literal|"No academic area provided for a curriculum."
argument_list|)
expr_stmt|;
continue|continue
name|curricula
continue|;
block|}
name|curriculum
operator|.
name|setAcademicArea
argument_list|(
name|area
argument_list|)
expr_stmt|;
name|Department
name|dept
init|=
literal|null
decl_stmt|;
for|for
control|(
name|Iterator
name|j
init|=
name|curriculumElement
operator|.
name|elementIterator
argument_list|(
literal|"department"
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
name|deptElement
init|=
operator|(
name|Element
operator|)
name|j
operator|.
name|next
argument_list|()
decl_stmt|;
name|String
name|externalId
init|=
name|deptElement
operator|.
name|attributeValue
argument_list|(
literal|"externalId"
argument_list|)
decl_stmt|;
name|String
name|code
init|=
name|deptElement
operator|.
name|attributeValue
argument_list|(
literal|"code"
argument_list|)
decl_stmt|;
name|dept
operator|=
operator|(
name|externalId
operator|!=
literal|null
condition|?
name|departmentsByExtId
operator|.
name|get
argument_list|(
name|externalId
argument_list|)
else|:
name|departmentsByCode
operator|.
name|get
argument_list|(
name|code
argument_list|)
operator|)
expr_stmt|;
if|if
condition|(
name|dept
operator|==
literal|null
condition|)
block|{
name|error
argument_list|(
literal|"Department "
operator|+
name|deptElement
operator|.
name|asXML
argument_list|()
operator|+
literal|" does not exist."
argument_list|)
expr_stmt|;
block|}
block|}
name|curriculum
operator|.
name|setMajors
argument_list|(
operator|new
name|HashSet
argument_list|<
name|PosMajor
argument_list|>
argument_list|()
argument_list|)
expr_stmt|;
name|List
argument_list|<
name|PosMajor
argument_list|>
name|majors
init|=
operator|new
name|ArrayList
argument_list|<
name|PosMajor
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|Iterator
name|j
init|=
name|curriculumElement
operator|.
name|elementIterator
argument_list|(
literal|"major"
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
name|majorElement
init|=
operator|(
name|Element
operator|)
name|j
operator|.
name|next
argument_list|()
decl_stmt|;
name|String
name|externalId
init|=
name|majorElement
operator|.
name|attributeValue
argument_list|(
literal|"externalId"
argument_list|)
decl_stmt|;
name|String
name|code
init|=
name|majorElement
operator|.
name|attributeValue
argument_list|(
literal|"code"
argument_list|)
decl_stmt|;
name|PosMajor
name|major
init|=
operator|(
name|externalId
operator|!=
literal|null
condition|?
name|majorsByExtId
operator|.
name|get
argument_list|(
name|externalId
argument_list|)
else|:
name|majorsByCode
operator|.
name|get
argument_list|(
name|area
operator|.
name|getAcademicAreaAbbreviation
argument_list|()
operator|+
literal|":"
operator|+
name|code
argument_list|)
operator|)
decl_stmt|;
if|if
condition|(
name|major
operator|==
literal|null
condition|)
block|{
name|error
argument_list|(
literal|"Major "
operator|+
name|majorElement
operator|.
name|asXML
argument_list|()
operator|+
literal|" does not exist."
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|curriculum
operator|.
name|getMajors
argument_list|()
operator|.
name|add
argument_list|(
name|major
argument_list|)
expr_stmt|;
name|majors
operator|.
name|add
argument_list|(
name|major
argument_list|)
expr_stmt|;
block|}
block|}
if|if
condition|(
name|abbv
operator|==
literal|null
condition|)
block|{
name|abbv
operator|=
name|area
operator|.
name|getAcademicAreaAbbreviation
argument_list|()
operator|+
operator|(
name|majors
operator|.
name|isEmpty
argument_list|()
condition|?
literal|""
else|:
literal|"/"
operator|)
expr_stmt|;
for|for
control|(
name|PosMajor
name|major
range|:
name|majors
control|)
block|{
if|if
condition|(
operator|!
name|abbv
operator|.
name|endsWith
argument_list|(
literal|"/"
argument_list|)
condition|)
name|abbv
operator|+=
literal|","
expr_stmt|;
name|abbv
operator|+=
name|major
operator|.
name|getCode
argument_list|()
expr_stmt|;
block|}
block|}
if|if
condition|(
name|abbv
operator|.
name|length
argument_list|()
operator|>
literal|20
condition|)
name|abbv
operator|=
name|abbv
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
literal|20
argument_list|)
expr_stmt|;
name|curriculum
operator|.
name|setAbbv
argument_list|(
name|abbv
argument_list|)
expr_stmt|;
if|if
condition|(
name|name
operator|==
literal|null
condition|)
block|{
name|name
operator|=
name|Constants
operator|.
name|curriculaToInitialCase
argument_list|(
name|area
operator|.
name|getTitle
argument_list|()
argument_list|)
operator|+
operator|(
name|majors
operator|.
name|isEmpty
argument_list|()
condition|?
literal|""
else|:
literal|" / "
operator|)
expr_stmt|;
for|for
control|(
name|PosMajor
name|major
range|:
name|majors
control|)
block|{
if|if
condition|(
operator|!
name|name
operator|.
name|endsWith
argument_list|(
literal|" / "
argument_list|)
condition|)
name|name
operator|+=
literal|", "
expr_stmt|;
name|name
operator|+=
name|Constants
operator|.
name|curriculaToInitialCase
argument_list|(
name|major
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
if|if
condition|(
name|name
operator|.
name|length
argument_list|()
operator|>
literal|60
condition|)
name|name
operator|=
name|name
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
literal|60
argument_list|)
expr_stmt|;
name|curriculum
operator|.
name|setName
argument_list|(
name|name
argument_list|)
expr_stmt|;
name|curriculum
operator|.
name|setMultipleMajors
argument_list|(
literal|"true"
operator|.
name|equalsIgnoreCase
argument_list|(
name|curriculumElement
operator|.
name|attributeValue
argument_list|(
literal|"multipleMajors"
argument_list|,
literal|"false"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|Hashtable
argument_list|<
name|String
argument_list|,
name|CurriculumCourseGroup
argument_list|>
name|groups
init|=
operator|new
name|Hashtable
argument_list|<
name|String
argument_list|,
name|CurriculumCourseGroup
argument_list|>
argument_list|()
decl_stmt|;
name|int
name|clasfOrd
init|=
literal|0
decl_stmt|;
name|curriculum
operator|.
name|setClassifications
argument_list|(
operator|new
name|HashSet
argument_list|<
name|CurriculumClassification
argument_list|>
argument_list|()
argument_list|)
expr_stmt|;
name|classifications
label|:
for|for
control|(
name|Iterator
name|j
init|=
name|curriculumElement
operator|.
name|elementIterator
argument_list|(
literal|"classification"
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
name|clasfElement
init|=
operator|(
name|Element
operator|)
name|j
operator|.
name|next
argument_list|()
decl_stmt|;
name|String
name|clasfName
init|=
name|clasfElement
operator|.
name|attributeValue
argument_list|(
literal|"name"
argument_list|)
decl_stmt|;
name|CurriculumClassification
name|clasf
init|=
operator|new
name|CurriculumClassification
argument_list|()
decl_stmt|;
name|AcademicClassification
name|acadClasf
init|=
literal|null
decl_stmt|;
for|for
control|(
name|Iterator
name|k
init|=
name|clasfElement
operator|.
name|elementIterator
argument_list|(
literal|"academicClassification"
argument_list|)
init|;
name|k
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|Element
name|acadClasfElement
init|=
operator|(
name|Element
operator|)
name|k
operator|.
name|next
argument_list|()
decl_stmt|;
name|String
name|externalId
init|=
name|acadClasfElement
operator|.
name|attributeValue
argument_list|(
literal|"externalId"
argument_list|)
decl_stmt|;
name|String
name|code
init|=
name|acadClasfElement
operator|.
name|attributeValue
argument_list|(
literal|"code"
argument_list|)
decl_stmt|;
name|acadClasf
operator|=
operator|(
name|externalId
operator|!=
literal|null
condition|?
name|clasfsByExtId
operator|.
name|get
argument_list|(
name|externalId
argument_list|)
else|:
name|clasfsByCode
operator|.
name|get
argument_list|(
name|code
argument_list|)
operator|)
expr_stmt|;
if|if
condition|(
name|acadClasf
operator|==
literal|null
condition|)
block|{
name|error
argument_list|(
literal|"Academic classification "
operator|+
name|acadClasfElement
operator|.
name|asXML
argument_list|()
operator|+
literal|" does not exist."
argument_list|)
expr_stmt|;
continue|continue
name|classifications
continue|;
block|}
block|}
if|if
condition|(
name|acadClasf
operator|==
literal|null
condition|)
block|{
name|error
argument_list|(
literal|"No academic classification provided for a curriculum classification."
argument_list|)
expr_stmt|;
continue|continue
name|classifications
continue|;
block|}
name|clasf
operator|.
name|setAcademicClassification
argument_list|(
name|acadClasf
argument_list|)
expr_stmt|;
if|if
condition|(
name|clasfName
operator|==
literal|null
condition|)
name|clasfName
operator|=
name|acadClasf
operator|.
name|getCode
argument_list|()
expr_stmt|;
name|clasf
operator|.
name|setName
argument_list|(
name|clasfName
argument_list|)
expr_stmt|;
name|clasf
operator|.
name|setOrd
argument_list|(
name|clasfOrd
operator|++
argument_list|)
expr_stmt|;
name|curriculum
operator|.
name|getClassifications
argument_list|()
operator|.
name|add
argument_list|(
name|clasf
argument_list|)
expr_stmt|;
name|clasf
operator|.
name|setCurriculum
argument_list|(
name|curriculum
argument_list|)
expr_stmt|;
name|String
name|enrollment
init|=
name|clasfElement
operator|.
name|attributeValue
argument_list|(
literal|"enrollment"
argument_list|,
literal|"0"
argument_list|)
decl_stmt|;
name|clasf
operator|.
name|setNrStudents
argument_list|(
name|Integer
operator|.
name|parseInt
argument_list|(
name|enrollment
argument_list|)
argument_list|)
expr_stmt|;
name|clasf
operator|.
name|setCourses
argument_list|(
operator|new
name|HashSet
argument_list|<
name|CurriculumCourse
argument_list|>
argument_list|()
argument_list|)
expr_stmt|;
name|int
name|courseOrd
init|=
literal|0
decl_stmt|;
for|for
control|(
name|Iterator
name|k
init|=
name|clasfElement
operator|.
name|elementIterator
argument_list|(
literal|"course"
argument_list|)
init|;
name|k
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|Element
name|courseElement
init|=
operator|(
name|Element
operator|)
name|k
operator|.
name|next
argument_list|()
decl_stmt|;
name|String
name|externalId
init|=
name|courseElement
operator|.
name|attributeValue
argument_list|(
literal|"externalId"
argument_list|)
decl_stmt|;
name|String
name|subjectCourseNbr
init|=
name|courseElement
operator|.
name|attributeValue
argument_list|(
literal|"subject"
argument_list|)
operator|+
literal|"|"
operator|+
name|courseElement
operator|.
name|attributeValue
argument_list|(
literal|"courseNbr"
argument_list|)
decl_stmt|;
name|CourseOffering
name|courseOffering
init|=
operator|(
name|externalId
operator|!=
literal|null
condition|?
name|corusesByExtId
operator|.
name|get
argument_list|(
name|externalId
argument_list|)
else|:
name|corusesBySubjectCourseNbr
operator|.
name|get
argument_list|(
name|subjectCourseNbr
argument_list|)
operator|)
decl_stmt|;
if|if
condition|(
name|courseOffering
operator|==
literal|null
condition|)
block|{
name|error
argument_list|(
literal|"Course "
operator|+
name|courseElement
operator|.
name|asXML
argument_list|()
operator|+
literal|" does not exist."
argument_list|)
expr_stmt|;
continue|continue;
block|}
name|CurriculumCourse
name|course
init|=
operator|new
name|CurriculumCourse
argument_list|()
decl_stmt|;
name|course
operator|.
name|setCourse
argument_list|(
name|courseOffering
argument_list|)
expr_stmt|;
name|course
operator|.
name|setOrd
argument_list|(
name|courseOrd
operator|++
argument_list|)
expr_stmt|;
name|String
name|share
init|=
name|courseElement
operator|.
name|attributeValue
argument_list|(
literal|"share"
argument_list|,
literal|"1.0"
argument_list|)
decl_stmt|;
name|course
operator|.
name|setPercShare
argument_list|(
name|Float
operator|.
name|parseFloat
argument_list|(
name|share
argument_list|)
argument_list|)
expr_stmt|;
name|course
operator|.
name|setClassification
argument_list|(
name|clasf
argument_list|)
expr_stmt|;
name|clasf
operator|.
name|getCourses
argument_list|()
operator|.
name|add
argument_list|(
name|course
argument_list|)
expr_stmt|;
name|course
operator|.
name|setGroups
argument_list|(
operator|new
name|HashSet
argument_list|<
name|CurriculumCourseGroup
argument_list|>
argument_list|()
argument_list|)
expr_stmt|;
for|for
control|(
name|Iterator
name|l
init|=
name|courseElement
operator|.
name|elementIterator
argument_list|(
literal|"group"
argument_list|)
init|;
name|l
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|Element
name|groupElement
init|=
operator|(
name|Element
operator|)
name|l
operator|.
name|next
argument_list|()
decl_stmt|;
name|CurriculumCourseGroup
name|group
init|=
name|groups
operator|.
name|get
argument_list|(
name|groupElement
operator|.
name|attributeValue
argument_list|(
literal|"id"
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|group
operator|==
literal|null
condition|)
block|{
name|group
operator|=
operator|new
name|CurriculumCourseGroup
argument_list|()
expr_stmt|;
name|group
operator|.
name|setCurriculum
argument_list|(
name|curriculum
argument_list|)
expr_stmt|;
name|groups
operator|.
name|put
argument_list|(
name|groupElement
operator|.
name|attributeValue
argument_list|(
literal|"id"
argument_list|)
argument_list|,
name|group
argument_list|)
expr_stmt|;
name|group
operator|.
name|setType
argument_list|(
literal|0
argument_list|)
expr_stmt|;
name|group
operator|.
name|setName
argument_list|(
name|groupElement
operator|.
name|attributeValue
argument_list|(
literal|"id"
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|course
operator|.
name|getGroups
argument_list|()
operator|.
name|add
argument_list|(
name|group
argument_list|)
expr_stmt|;
name|String
name|grName
init|=
name|groupElement
operator|.
name|attributeValue
argument_list|(
literal|"name"
argument_list|)
decl_stmt|;
name|String
name|grType
init|=
name|groupElement
operator|.
name|attributeValue
argument_list|(
literal|"type"
argument_list|)
decl_stmt|;
if|if
condition|(
name|grName
operator|!=
literal|null
condition|)
name|group
operator|.
name|setName
argument_list|(
name|grName
argument_list|)
expr_stmt|;
if|if
condition|(
name|grType
operator|!=
literal|null
condition|)
name|group
operator|.
name|setType
argument_list|(
literal|"OPT"
operator|.
name|equalsIgnoreCase
argument_list|(
name|grType
argument_list|)
condition|?
literal|0
else|:
literal|1
argument_list|)
expr_stmt|;
block|}
block|}
block|}
if|if
condition|(
name|curriculum
operator|.
name|getDepartment
argument_list|()
operator|==
literal|null
condition|)
block|{
name|Hashtable
argument_list|<
name|Department
argument_list|,
name|Float
argument_list|>
name|dept2enrl
init|=
operator|new
name|Hashtable
argument_list|<
name|Department
argument_list|,
name|Float
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|Iterator
name|j
init|=
name|curriculum
operator|.
name|getClassifications
argument_list|()
operator|.
name|iterator
argument_list|()
init|;
name|j
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|CurriculumClassification
name|clasf
init|=
operator|(
name|CurriculumClassification
operator|)
name|j
operator|.
name|next
argument_list|()
decl_stmt|;
for|for
control|(
name|Iterator
name|k
init|=
name|clasf
operator|.
name|getCourses
argument_list|()
operator|.
name|iterator
argument_list|()
init|;
name|k
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|CurriculumCourse
name|course
init|=
operator|(
name|CurriculumCourse
operator|)
name|k
operator|.
name|next
argument_list|()
decl_stmt|;
name|Department
name|d
init|=
name|course
operator|.
name|getCourse
argument_list|()
operator|.
name|getSubjectArea
argument_list|()
operator|.
name|getDepartment
argument_list|()
decl_stmt|;
name|Float
name|x
init|=
name|dept2enrl
operator|.
name|get
argument_list|(
name|d
argument_list|)
decl_stmt|;
name|dept2enrl
operator|.
name|put
argument_list|(
name|d
argument_list|,
name|course
operator|.
name|getPercShare
argument_list|()
operator|*
name|clasf
operator|.
name|getNrStudents
argument_list|()
operator|+
operator|(
name|x
operator|==
literal|null
condition|?
literal|0.0f
else|:
name|x
operator|)
argument_list|)
expr_stmt|;
block|}
block|}
name|float
name|best
init|=
literal|0.0f
decl_stmt|;
for|for
control|(
name|Map
operator|.
name|Entry
argument_list|<
name|Department
argument_list|,
name|Float
argument_list|>
name|entry
range|:
name|dept2enrl
operator|.
name|entrySet
argument_list|()
control|)
block|{
if|if
condition|(
name|dept
operator|==
literal|null
operator|||
name|entry
operator|.
name|getValue
argument_list|()
operator|>
name|best
condition|)
block|{
name|dept
operator|=
name|entry
operator|.
name|getKey
argument_list|()
expr_stmt|;
name|best
operator|=
name|entry
operator|.
name|getValue
argument_list|()
expr_stmt|;
block|}
block|}
if|if
condition|(
name|dept
operator|==
literal|null
condition|)
block|{
name|error
argument_list|(
literal|"Unable to guess department for curriculum "
operator|+
name|curriculum
operator|.
name|getName
argument_list|()
operator|+
literal|", it has no courses."
argument_list|)
expr_stmt|;
continue|continue;
block|}
name|curriculum
operator|.
name|setDepartment
argument_list|(
name|dept
argument_list|)
expr_stmt|;
block|}
name|getHibSession
argument_list|()
operator|.
name|saveOrUpdate
argument_list|(
name|curriculum
argument_list|)
expr_stmt|;
for|for
control|(
name|CurriculumCourseGroup
name|group
range|:
name|groups
operator|.
name|values
argument_list|()
control|)
block|{
name|getHibSession
argument_list|()
operator|.
name|saveOrUpdate
argument_list|(
name|group
argument_list|)
expr_stmt|;
block|}
name|flushIfNeeded
argument_list|(
literal|false
argument_list|)
expr_stmt|;
block|}
name|info
argument_list|(
literal|"All done."
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
specifier|public
specifier|static
name|void
name|main
parameter_list|(
name|String
index|[]
name|args
parameter_list|)
block|{
try|try
block|{
if|if
condition|(
name|args
operator|.
name|length
operator|==
literal|0
condition|)
name|args
operator|=
operator|new
name|String
index|[]
block|{
literal|"curricula.xml"
block|}
expr_stmt|;
name|ToolBox
operator|.
name|configureLogging
argument_list|()
expr_stmt|;
name|HibernateUtil
operator|.
name|configureHibernate
argument_list|(
name|ApplicationProperties
operator|.
name|getProperties
argument_list|()
argument_list|)
expr_stmt|;
operator|new
name|CurriculaImport
argument_list|()
operator|.
name|loadXml
argument_list|(
name|args
index|[
literal|0
index|]
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|e
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

