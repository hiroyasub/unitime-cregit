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
name|model
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
name|List
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|TreeSet
import|;
end_import

begin_import
import|import
name|org
operator|.
name|hibernate
operator|.
name|HibernateException
import|;
end_import

begin_import
import|import
name|org
operator|.
name|hibernate
operator|.
name|Session
import|;
end_import

begin_import
import|import
name|org
operator|.
name|hibernate
operator|.
name|criterion
operator|.
name|Restrictions
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
name|NaturalOrderComparator
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
name|defaults
operator|.
name|ApplicationProperty
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
name|interfaces
operator|.
name|AcademicSessionLookup
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
name|BaseSubjectArea
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

begin_import
import|import
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|security
operator|.
name|UserContext
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller, Heston Fernandes  */
end_comment

begin_class
specifier|public
class|class
name|SubjectArea
extends|extends
name|BaseSubjectArea
implements|implements
name|Comparable
argument_list|<
name|SubjectArea
argument_list|>
block|{
comment|/** 	 *  	 */
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|3256439188198207794L
decl_stmt|;
comment|/** Request attribute name for available subject areas **/
specifier|public
specifier|static
name|String
name|SUBJ_AREA_ATTR_NAME
init|=
literal|"subjectAreaList"
decl_stmt|;
comment|/*[CONSTRUCTOR MARKER BEGIN]*/
specifier|public
name|SubjectArea
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
block|}
comment|/** 	 * Constructor for primary key 	 */
specifier|public
name|SubjectArea
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
comment|/*[CONSTRUCTOR MARKER END]*/
comment|/** 	 * Retrieves all subject areas academic session 	 * @param sessionId academic session 	 * @return List of SubjectArea objects 	 */
specifier|public
specifier|static
name|List
argument_list|<
name|SubjectArea
argument_list|>
name|getSubjectAreaList
parameter_list|(
name|Long
name|sessionId
parameter_list|)
throws|throws
name|HibernateException
block|{
name|SubjectAreaDAO
name|subjDAO
init|=
operator|new
name|SubjectAreaDAO
argument_list|()
decl_stmt|;
name|Session
name|hibSession
init|=
name|subjDAO
operator|.
name|getSession
argument_list|()
decl_stmt|;
annotation|@
name|SuppressWarnings
argument_list|(
literal|"unchecked"
argument_list|)
name|List
argument_list|<
name|SubjectArea
argument_list|>
name|subjs
init|=
operator|(
name|List
argument_list|<
name|SubjectArea
argument_list|>
operator|)
name|hibSession
operator|.
name|createCriteria
argument_list|(
name|SubjectArea
operator|.
name|class
argument_list|)
operator|.
name|add
argument_list|(
name|Restrictions
operator|.
name|eq
argument_list|(
literal|"session.uniqueId"
argument_list|,
name|sessionId
argument_list|)
argument_list|)
operator|.
name|list
argument_list|()
decl_stmt|;
return|return
name|subjs
return|;
block|}
comment|/** 	 * Retrieve a subject area for a given abbreviation and academic session 	 * @param sessionId 	 * @param subjectAreaAbbr 	 * @return null if no matches found 	 */
specifier|public
specifier|static
name|SubjectArea
name|findByAbbv
parameter_list|(
name|Long
name|sessionId
parameter_list|,
name|String
name|subjectAreaAbbr
parameter_list|)
block|{
return|return
name|findByAbbv
argument_list|(
name|SubjectAreaDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|getSession
argument_list|()
argument_list|,
name|sessionId
argument_list|,
name|subjectAreaAbbr
argument_list|)
return|;
block|}
specifier|public
specifier|static
name|SubjectArea
name|findByAbbv
parameter_list|(
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
parameter_list|,
name|Long
name|sessionId
parameter_list|,
name|String
name|subjectAreaAbbr
parameter_list|)
block|{
return|return
operator|(
name|SubjectArea
operator|)
operator|(
name|hibSession
operator|==
literal|null
condition|?
name|SubjectAreaDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|getSession
argument_list|()
else|:
name|hibSession
operator|)
operator|.
name|createQuery
argument_list|(
literal|"from SubjectArea where session.uniqueId = :sessionId and subjectAreaAbbreviation = :subjectAreaAbbr"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"sessionId"
argument_list|,
name|sessionId
argument_list|)
operator|.
name|setString
argument_list|(
literal|"subjectAreaAbbr"
argument_list|,
name|subjectAreaAbbr
argument_list|)
operator|.
name|setMaxResults
argument_list|(
literal|1
argument_list|)
operator|.
name|uniqueResult
argument_list|()
return|;
block|}
specifier|public
specifier|static
name|SubjectArea
name|findUsingInitiativeYearTermSubjectAbbreviation
parameter_list|(
name|String
name|academicInitiative
parameter_list|,
name|String
name|academicYear
parameter_list|,
name|String
name|term
parameter_list|,
name|String
name|subjectAreaAbbreviation
parameter_list|,
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
parameter_list|)
block|{
return|return
operator|(
name|SubjectArea
operator|)
name|hibSession
operator|.
name|createQuery
argument_list|(
literal|"from SubjectArea sa where sa.session.academicInitiative = :campus and sa.session.academicYear = :year and sa.session.academicTerm = :term and sa.subjectAreaAbbreviation = :subj"
argument_list|)
operator|.
name|setString
argument_list|(
literal|"campus"
argument_list|,
name|academicInitiative
argument_list|)
operator|.
name|setString
argument_list|(
literal|"year"
argument_list|,
name|academicYear
argument_list|)
operator|.
name|setString
argument_list|(
literal|"term"
argument_list|,
name|term
argument_list|)
operator|.
name|setString
argument_list|(
literal|"subj"
argument_list|,
name|subjectAreaAbbreviation
argument_list|)
operator|.
name|setCacheable
argument_list|(
literal|true
argument_list|)
operator|.
name|setMaxResults
argument_list|(
literal|1
argument_list|)
operator|.
name|uniqueResult
argument_list|()
return|;
block|}
specifier|public
specifier|static
name|SubjectArea
name|findUsingCampusYearTermExternalSubjectAbbreviation
parameter_list|(
name|String
name|campus
parameter_list|,
name|String
name|year
parameter_list|,
name|String
name|term
parameter_list|,
name|String
name|externalSubjectAreaAbbreviation
parameter_list|,
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
parameter_list|)
block|{
name|String
name|className
init|=
name|ApplicationProperty
operator|.
name|AcademicSessionLookupImplementation
operator|.
name|value
argument_list|()
decl_stmt|;
name|AcademicSessionLookup
name|academicSessionLookup
init|=
literal|null
decl_stmt|;
try|try
block|{
name|academicSessionLookup
operator|=
operator|(
name|AcademicSessionLookup
operator|)
name|Class
operator|.
name|forName
argument_list|(
name|className
argument_list|)
operator|.
name|getDeclaredConstructor
argument_list|()
operator|.
name|newInstance
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
return|return
name|findByAbbv
argument_list|(
name|hibSession
argument_list|,
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|Session
operator|.
name|getSessionUsingInitiativeYearTerm
argument_list|(
name|campus
argument_list|,
name|year
argument_list|,
name|term
argument_list|,
name|hibSession
argument_list|)
operator|.
name|getUniqueId
argument_list|()
argument_list|,
name|externalSubjectAreaAbbreviation
argument_list|)
return|;
block|}
return|return
name|academicSessionLookup
operator|.
name|findSubjectAreaForCampusYearTerm
argument_list|(
name|campus
argument_list|,
name|year
argument_list|,
name|term
argument_list|,
name|externalSubjectAreaAbbreviation
argument_list|,
name|hibSession
argument_list|)
return|;
block|}
specifier|public
name|ArrayList
argument_list|<
name|TimetableManager
argument_list|>
name|getManagers
parameter_list|()
block|{
if|if
condition|(
name|getDepartment
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|ArrayList
argument_list|<
name|TimetableManager
argument_list|>
name|al
init|=
operator|new
name|ArrayList
argument_list|<
name|TimetableManager
argument_list|>
argument_list|()
decl_stmt|;
name|al
operator|.
name|addAll
argument_list|(
name|getDepartment
argument_list|()
operator|.
name|getTimetableManagers
argument_list|()
argument_list|)
expr_stmt|;
return|return
operator|(
name|al
operator|)
return|;
block|}
else|else
block|{
return|return
operator|(
operator|new
name|ArrayList
argument_list|<
name|TimetableManager
argument_list|>
argument_list|()
operator|)
return|;
block|}
block|}
specifier|public
name|int
name|compareTo
parameter_list|(
name|SubjectArea
name|s
parameter_list|)
block|{
name|int
name|cmp
init|=
operator|new
name|NaturalOrderComparator
argument_list|()
operator|.
name|compare
argument_list|(
name|getSubjectAreaAbbreviation
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
name|getSubjectAreaAbbreviation
argument_list|()
argument_list|,
name|s
operator|.
name|getSubjectAreaAbbreviation
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
name|s
operator|.
name|getSubjectAreaAbbreviation
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|cmp
operator|!=
literal|0
condition|)
return|return
name|cmp
return|;
return|return
operator|(
name|getUniqueId
argument_list|()
operator|==
literal|null
condition|?
operator|new
name|Long
argument_list|(
operator|-
literal|1
argument_list|)
else|:
name|getUniqueId
argument_list|()
operator|)
operator|.
name|compareTo
argument_list|(
name|s
operator|.
name|getUniqueId
argument_list|()
operator|==
literal|null
condition|?
operator|-
literal|1
else|:
name|s
operator|.
name|getUniqueId
argument_list|()
argument_list|)
return|;
block|}
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
name|this
operator|.
name|getSubjectAreaAbbreviation
argument_list|()
operator|+
literal|" - "
operator|+
name|this
operator|.
name|getTitle
argument_list|()
return|;
block|}
specifier|public
name|Long
name|getSessionId
parameter_list|()
block|{
if|if
condition|(
name|this
operator|.
name|getSession
argument_list|()
operator|!=
literal|null
condition|)
block|{
return|return
operator|(
name|this
operator|.
name|getSession
argument_list|()
operator|.
name|getUniqueId
argument_list|()
operator|)
return|;
block|}
else|else
block|{
return|return
operator|(
literal|null
operator|)
return|;
block|}
block|}
specifier|public
name|Object
name|clone
parameter_list|()
block|{
name|SubjectArea
name|newSubjectArea
init|=
operator|new
name|SubjectArea
argument_list|()
decl_stmt|;
name|newSubjectArea
operator|.
name|setDepartment
argument_list|(
name|getDepartment
argument_list|()
argument_list|)
expr_stmt|;
name|newSubjectArea
operator|.
name|setExternalUniqueId
argument_list|(
name|getExternalUniqueId
argument_list|()
argument_list|)
expr_stmt|;
name|newSubjectArea
operator|.
name|setTitle
argument_list|(
name|getTitle
argument_list|()
argument_list|)
expr_stmt|;
name|newSubjectArea
operator|.
name|setSession
argument_list|(
name|getSession
argument_list|()
argument_list|)
expr_stmt|;
name|newSubjectArea
operator|.
name|setSubjectAreaAbbreviation
argument_list|(
name|getSubjectAreaAbbreviation
argument_list|()
argument_list|)
expr_stmt|;
return|return
operator|(
name|newSubjectArea
operator|)
return|;
block|}
specifier|public
name|SubjectArea
name|findSameSubjectAreaInSession
parameter_list|(
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|Session
name|session
parameter_list|)
block|{
return|return
name|SubjectArea
operator|.
name|findByAbbv
argument_list|(
name|session
operator|.
name|getUniqueId
argument_list|()
argument_list|,
name|this
operator|.
name|getSubjectAreaAbbreviation
argument_list|()
argument_list|)
return|;
block|}
comment|/** 	 * Check if a subject area has offered classes 	 * @return 	 */
specifier|public
name|boolean
name|hasOfferedCourses
parameter_list|()
block|{
for|for
control|(
name|CourseOffering
name|co
range|:
name|getCourseOfferings
argument_list|()
control|)
block|{
if|if
condition|(
operator|!
name|co
operator|.
name|getInstructionalOffering
argument_list|()
operator|.
name|isNotOffered
argument_list|()
condition|)
return|return
literal|true
return|;
block|}
return|return
literal|false
return|;
block|}
annotation|@
name|SuppressWarnings
argument_list|(
literal|"unchecked"
argument_list|)
specifier|public
specifier|static
name|TreeSet
argument_list|<
name|SubjectArea
argument_list|>
name|getAllSubjectAreas
parameter_list|(
name|Long
name|sessionId
parameter_list|)
block|{
return|return
operator|new
name|TreeSet
argument_list|<
name|SubjectArea
argument_list|>
argument_list|(
name|SubjectAreaDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|getQuery
argument_list|(
literal|"from SubjectArea where session.uniqueId = :sessionId"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"sessionId"
argument_list|,
name|sessionId
argument_list|)
operator|.
name|setCacheable
argument_list|(
literal|true
argument_list|)
operator|.
name|list
argument_list|()
argument_list|)
return|;
block|}
specifier|public
specifier|static
name|TreeSet
argument_list|<
name|SubjectArea
argument_list|>
name|getUserSubjectAreas
parameter_list|(
name|UserContext
name|user
parameter_list|)
block|{
return|return
name|getUserSubjectAreas
argument_list|(
name|user
argument_list|,
literal|true
argument_list|)
return|;
block|}
specifier|public
specifier|static
name|TreeSet
argument_list|<
name|SubjectArea
argument_list|>
name|getUserSubjectAreas
parameter_list|(
name|UserContext
name|user
parameter_list|,
name|boolean
name|allSubjectsIfExternalManager
parameter_list|)
block|{
name|TreeSet
argument_list|<
name|SubjectArea
argument_list|>
name|subjectAreas
init|=
operator|new
name|TreeSet
argument_list|<
name|SubjectArea
argument_list|>
argument_list|()
decl_stmt|;
if|if
condition|(
name|user
operator|==
literal|null
operator|||
name|user
operator|.
name|getCurrentAuthority
argument_list|()
operator|==
literal|null
condition|)
return|return
name|subjectAreas
return|;
for|for
control|(
name|Department
name|department
range|:
name|Department
operator|.
name|getUserDepartments
argument_list|(
name|user
argument_list|)
control|)
block|{
if|if
condition|(
name|department
operator|.
name|isExternalManager
argument_list|()
operator|&&
name|allSubjectsIfExternalManager
condition|)
block|{
name|subjectAreas
operator|.
name|addAll
argument_list|(
name|department
operator|.
name|getSession
argument_list|()
operator|.
name|getSubjectAreas
argument_list|()
argument_list|)
expr_stmt|;
break|break;
block|}
else|else
block|{
name|subjectAreas
operator|.
name|addAll
argument_list|(
name|department
operator|.
name|getSubjectAreas
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|subjectAreas
return|;
block|}
block|}
end_class

end_unit

