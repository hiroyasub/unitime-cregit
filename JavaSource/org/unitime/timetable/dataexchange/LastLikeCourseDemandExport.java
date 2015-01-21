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
name|Date
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

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|LastLikeCourseDemandExport
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
literal|"lastLikeCourseDemand"
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
name|root
operator|.
name|addAttribute
argument_list|(
literal|"created"
argument_list|,
operator|new
name|Date
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
name|document
operator|.
name|addDocType
argument_list|(
literal|"lastLikeCourseDemand"
argument_list|,
literal|"-//UniTime//DTD University Course Timetabling/EN"
argument_list|,
literal|"http://www.unitime.org/interface/StudentCourse.dtd"
argument_list|)
expr_stmt|;
name|String
name|lastExternalId
init|=
literal|null
decl_stmt|;
name|Element
name|studentEl
init|=
literal|null
decl_stmt|;
name|Map
argument_list|<
name|String
argument_list|,
name|CourseOffering
argument_list|>
name|permId2course
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
literal|"from CourseOffering co where co.subjectArea.session.uniqueId = :sessionId"
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
name|course
operator|.
name|getPermId
argument_list|()
operator|!=
literal|null
condition|)
name|permId2course
operator|.
name|put
argument_list|(
name|course
operator|.
name|getPermId
argument_list|()
argument_list|,
name|course
argument_list|)
expr_stmt|;
block|}
for|for
control|(
name|LastLikeCourseDemand
name|demand
range|:
operator|(
name|List
argument_list|<
name|LastLikeCourseDemand
argument_list|>
operator|)
name|getHibSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"from LastLikeCourseDemand d where d.subjectArea.session.uniqueId = :sessionId "
operator|+
literal|"order by d.student.externalUniqueId, d.priority, d.subjectArea.subjectAreaAbbreviation, d.courseNbr"
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
operator|!
name|demand
operator|.
name|getStudent
argument_list|()
operator|.
name|getExternalUniqueId
argument_list|()
operator|.
name|equals
argument_list|(
name|lastExternalId
argument_list|)
condition|)
block|{
name|lastExternalId
operator|=
name|demand
operator|.
name|getStudent
argument_list|()
operator|.
name|getExternalUniqueId
argument_list|()
expr_stmt|;
name|studentEl
operator|=
name|root
operator|.
name|addElement
argument_list|(
literal|"student"
argument_list|)
expr_stmt|;
name|studentEl
operator|.
name|addAttribute
argument_list|(
literal|"externalId"
argument_list|,
name|lastExternalId
argument_list|)
expr_stmt|;
block|}
name|Element
name|demandEl
init|=
name|studentEl
operator|.
name|addElement
argument_list|(
literal|"studentCourse"
argument_list|)
decl_stmt|;
name|CourseOffering
name|course
init|=
operator|(
name|demand
operator|.
name|getCoursePermId
argument_list|()
operator|==
literal|null
condition|?
literal|null
else|:
name|permId2course
operator|.
name|get
argument_list|(
name|demand
operator|.
name|getCoursePermId
argument_list|()
argument_list|)
operator|)
decl_stmt|;
if|if
condition|(
name|course
operator|==
literal|null
condition|)
block|{
name|demandEl
operator|.
name|addAttribute
argument_list|(
literal|"subject"
argument_list|,
name|demand
operator|.
name|getSubjectArea
argument_list|()
operator|.
name|getSubjectAreaAbbreviation
argument_list|()
argument_list|)
expr_stmt|;
name|demandEl
operator|.
name|addAttribute
argument_list|(
literal|"courseNumber"
argument_list|,
name|demand
operator|.
name|getCourseNbr
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|demandEl
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
name|demandEl
operator|.
name|addAttribute
argument_list|(
literal|"courseNumber"
argument_list|,
name|course
operator|.
name|getCourseNbr
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

