begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.2 (University Timetabling Application)  * Copyright (C) 2008 - 2010, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
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
name|HashMap
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

begin_class
specifier|public
class|class
name|SubjectArea
extends|extends
name|BaseSubjectArea
implements|implements
name|Comparable
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
specifier|private
specifier|static
name|HashMap
name|subjectAreas
init|=
operator|new
name|HashMap
argument_list|(
literal|150
argument_list|)
decl_stmt|;
specifier|private
specifier|static
name|List
name|subjChanges
init|=
literal|null
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
name|List
name|subjs
init|=
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
comment|/** 	 * Load subject abbreviation changes 	 * @param sessionId 	 */
specifier|public
specifier|static
name|void
name|loadSubjAbbvChanges
parameter_list|(
name|Long
name|sessionId
parameter_list|)
block|{
name|subjChanges
operator|=
name|SubjectHistory
operator|.
name|getSubjectHistoryList
argument_list|(
name|sessionId
argument_list|)
expr_stmt|;
block|}
comment|/** 	 * Load Subject Areas 	 */
specifier|public
specifier|static
name|void
name|loadSubjectAreas
parameter_list|(
name|Long
name|sessionId
parameter_list|)
block|{
name|List
name|subjAreas
init|=
name|getSubjectAreaList
argument_list|(
name|sessionId
argument_list|)
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|subjAreas
operator|.
name|size
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|SubjectArea
name|subjArea
init|=
operator|(
name|SubjectArea
operator|)
name|subjAreas
operator|.
name|get
argument_list|(
name|i
argument_list|)
decl_stmt|;
name|String
name|subjAreaAbbreviation
init|=
name|subjArea
operator|.
name|getSubjectAreaAbbreviation
argument_list|()
decl_stmt|;
name|subjectAreas
operator|.
name|put
argument_list|(
name|subjAreaAbbreviation
argument_list|,
name|subjArea
argument_list|)
expr_stmt|;
block|}
block|}
comment|/** 	 * Get the Subject Area 	 * @param subjectAreaAbbr 	 * @return SubjectArea 	 */
specifier|public
specifier|static
name|SubjectArea
name|getSubjectArea
parameter_list|(
name|String
name|subjectAreaAbbr
parameter_list|)
block|{
return|return
operator|(
name|SubjectArea
operator|)
name|subjectAreas
operator|.
name|get
argument_list|(
name|subjectAreaAbbr
argument_list|)
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
name|List
name|subjs
init|=
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
name|add
argument_list|(
name|Restrictions
operator|.
name|eq
argument_list|(
literal|"subjectAreaAbbreviation"
argument_list|,
name|subjectAreaAbbr
argument_list|)
argument_list|)
operator|.
name|list
argument_list|()
decl_stmt|;
if|if
condition|(
name|subjs
operator|==
literal|null
operator|||
name|subjs
operator|.
name|size
argument_list|()
operator|==
literal|0
condition|)
return|return
literal|null
return|;
return|return
operator|(
name|SubjectArea
operator|)
name|subjs
operator|.
name|get
argument_list|(
literal|0
argument_list|)
return|;
block|}
comment|/** 	 * Get the current (updated) subject area abbreviation 	 * @param subjectAreaAbbr 	 * @return SubjectArea 	 */
specifier|public
specifier|static
name|SubjectArea
name|getUpdatedSubjectArea
parameter_list|(
name|String
name|subjectAreaAbbr
parameter_list|)
block|{
name|String
name|sa
init|=
name|subjectAreaAbbr
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|subjChanges
operator|.
name|size
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|SubjectHistory
name|sh
init|=
operator|(
name|SubjectHistory
operator|)
name|subjChanges
operator|.
name|get
argument_list|(
name|i
argument_list|)
decl_stmt|;
if|if
condition|(
name|sa
operator|.
name|equalsIgnoreCase
argument_list|(
name|sh
operator|.
name|getOldValue
argument_list|()
argument_list|)
condition|)
block|{
name|sa
operator|=
name|sh
operator|.
name|getNewValue
argument_list|()
expr_stmt|;
block|}
block|}
return|return
name|getSubjectArea
argument_list|(
name|sa
argument_list|)
return|;
block|}
specifier|public
name|ArrayList
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
name|al
init|=
operator|new
name|ArrayList
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
argument_list|()
operator|)
return|;
block|}
block|}
comment|/* (non-Javadoc) 	 * @see java.lang.Comparable#compareTo(java.lang.Object) 	 */
specifier|public
name|int
name|compareTo
parameter_list|(
name|Object
name|o
parameter_list|)
block|{
comment|// Check if objects are of class Subject Area
if|if
condition|(
name|o
operator|==
literal|null
operator|||
operator|!
operator|(
name|o
operator|instanceof
name|SubjectArea
operator|)
condition|)
return|return
operator|(
operator|-
literal|1
operator|)
return|;
name|SubjectArea
name|s
init|=
operator|(
name|SubjectArea
operator|)
name|o
decl_stmt|;
name|String
name|key10
init|=
name|this
operator|.
name|getExternalUniqueId
argument_list|()
decl_stmt|;
if|if
condition|(
name|key10
operator|==
literal|null
condition|)
name|key10
operator|=
name|getUniqueId
argument_list|()
operator|.
name|toString
argument_list|()
expr_stmt|;
name|String
name|key11
decl_stmt|;
try|try
block|{
name|key11
operator|=
name|Constants
operator|.
name|leftPad
argument_list|(
name|this
operator|.
name|getSessionId
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|,
literal|20
argument_list|,
literal|"0"
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|key11
operator|=
name|this
operator|.
name|getSessionId
argument_list|()
operator|.
name|toString
argument_list|()
expr_stmt|;
block|}
name|String
name|key20
init|=
name|s
operator|.
name|getExternalUniqueId
argument_list|()
decl_stmt|;
if|if
condition|(
name|key20
operator|==
literal|null
condition|)
name|key20
operator|=
name|s
operator|.
name|getUniqueId
argument_list|()
operator|.
name|toString
argument_list|()
expr_stmt|;
name|String
name|key21
decl_stmt|;
try|try
block|{
name|key21
operator|=
name|Constants
operator|.
name|leftPad
argument_list|(
name|s
operator|.
name|getSessionId
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|,
literal|20
argument_list|,
literal|"0"
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|key21
operator|=
name|s
operator|.
name|getSessionId
argument_list|()
operator|.
name|toString
argument_list|()
expr_stmt|;
block|}
comment|// Compare unique id's and session id's
if|if
condition|(
name|key10
operator|.
name|equals
argument_list|(
name|key20
argument_list|)
operator|&&
name|key11
operator|.
name|equals
argument_list|(
name|key21
argument_list|)
condition|)
return|return
literal|0
return|;
if|if
condition|(
operator|!
name|key10
operator|.
name|equals
argument_list|(
name|key20
argument_list|)
condition|)
block|{
return|return
operator|(
name|this
operator|.
name|getSubjectAreaAbbreviation
argument_list|()
operator|.
name|compareTo
argument_list|(
name|s
operator|.
name|getSubjectAreaAbbreviation
argument_list|()
argument_list|)
operator|)
return|;
block|}
comment|// If not equal then return lexiographical comparison
return|return
operator|(
name|key10
operator|+
name|key11
operator|)
operator|.
name|compareTo
argument_list|(
operator|(
name|key10
operator|+
name|key11
operator|)
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
name|getLongTitle
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
name|setLongTitle
argument_list|(
name|getLongTitle
argument_list|()
argument_list|)
expr_stmt|;
name|newSubjectArea
operator|.
name|setPseudoSubjectArea
argument_list|(
name|isPseudoSubjectArea
argument_list|()
argument_list|)
expr_stmt|;
name|newSubjectArea
operator|.
name|setScheduleBookOnly
argument_list|(
name|isScheduleBookOnly
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
name|setShortTitle
argument_list|(
name|getShortTitle
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
name|String
name|query
init|=
literal|"select sa from SubjectArea sa where sa.department.session.uniqueId = "
operator|+
name|session
operator|.
name|getUniqueId
argument_list|()
operator|.
name|toString
argument_list|()
decl_stmt|;
name|query
operator|+=
literal|" and sa.subjectAreaAbbreviation = '"
operator|+
name|this
operator|.
name|getSubjectAreaAbbreviation
argument_list|()
operator|+
literal|"'"
expr_stmt|;
name|SubjectAreaDAO
name|saDao
init|=
operator|new
name|SubjectAreaDAO
argument_list|()
decl_stmt|;
name|List
name|l
init|=
name|saDao
operator|.
name|getQuery
argument_list|(
name|query
argument_list|)
operator|.
name|list
argument_list|()
decl_stmt|;
if|if
condition|(
name|l
operator|!=
literal|null
operator|&&
name|l
operator|.
name|size
argument_list|()
operator|==
literal|1
condition|)
block|{
return|return
operator|(
operator|(
name|SubjectArea
operator|)
name|l
operator|.
name|get
argument_list|(
literal|0
argument_list|)
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
comment|/** 	 * Check if a subject area has offered classes 	 * @return 	 */
specifier|public
name|boolean
name|hasOfferedCourses
parameter_list|()
block|{
name|Set
name|courses
init|=
name|getCourseOfferings
argument_list|()
decl_stmt|;
for|for
control|(
name|Object
name|co
range|:
name|courses
control|)
block|{
if|if
condition|(
operator|!
operator|(
operator|(
name|CourseOffering
operator|)
name|co
operator|)
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

