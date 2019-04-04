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
name|onlinesectioning
operator|.
name|custom
operator|.
name|purdue
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
name|Collections
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
name|regex
operator|.
name|Matcher
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|regex
operator|.
name|Pattern
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|log4j
operator|.
name|Logger
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
name|type
operator|.
name|BigDecimalType
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
name|onlinesectioning
operator|.
name|AcademicSessionInfo
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
name|onlinesectioning
operator|.
name|OnlineSectioningHelper
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
name|onlinesectioning
operator|.
name|OnlineSectioningServer
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
name|onlinesectioning
operator|.
name|custom
operator|.
name|CustomCourseLookup
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
name|onlinesectioning
operator|.
name|custom
operator|.
name|ExternalTermProvider
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
name|onlinesectioning
operator|.
name|model
operator|.
name|XCourse
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
name|onlinesectioning
operator|.
name|model
operator|.
name|XCourseId
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|UCCCoursesLookup
implements|implements
name|CustomCourseLookup
block|{
specifier|private
specifier|static
name|Logger
name|sLog
init|=
name|Logger
operator|.
name|getLogger
argument_list|(
name|UCCCoursesLookup
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|private
name|ExternalTermProvider
name|iExternalTermProvider
decl_stmt|;
specifier|public
name|UCCCoursesLookup
parameter_list|()
block|{
try|try
block|{
name|String
name|clazz
init|=
name|ApplicationProperty
operator|.
name|CustomizationExternalTerm
operator|.
name|value
argument_list|()
decl_stmt|;
if|if
condition|(
name|clazz
operator|==
literal|null
operator|||
name|clazz
operator|.
name|isEmpty
argument_list|()
condition|)
name|iExternalTermProvider
operator|=
operator|new
name|BannerTermProvider
argument_list|()
expr_stmt|;
else|else
name|iExternalTermProvider
operator|=
operator|(
name|ExternalTermProvider
operator|)
name|Class
operator|.
name|forName
argument_list|(
name|clazz
argument_list|)
operator|.
name|getConstructor
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
name|sLog
operator|.
name|error
argument_list|(
literal|"Failed to create external term provider, using the default one instead."
argument_list|,
name|e
argument_list|)
expr_stmt|;
name|iExternalTermProvider
operator|=
operator|new
name|BannerTermProvider
argument_list|()
expr_stmt|;
block|}
block|}
specifier|protected
name|String
name|getBannerTerm
parameter_list|(
name|AcademicSessionInfo
name|session
parameter_list|)
block|{
return|return
name|iExternalTermProvider
operator|.
name|getExternalTerm
argument_list|(
name|session
argument_list|)
return|;
block|}
specifier|protected
name|String
name|getCourseLookupSQL
parameter_list|()
block|{
return|return
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"banner.ucc.lookupSQL"
argument_list|,
literal|"select distinct co.uniqueid "
operator|+
literal|"from timetable.szv_utm_attr a, course_offering co, instructional_offering io, subject_area sa "
operator|+
literal|"where a.term_start<= :term and a.term_end>= :term and "
operator|+
literal|"(lower(a.attribute_description) like concat(concat('uc-', :query), '%') or lower(a.attribute_description) like concat(concat('% ', :query), '%') or lower(a.course_attribute) = :query) and "
operator|+
literal|"co.instr_offr_id = io.uniqueid and co.subject_area_id = sa.uniqueid and io.session_id = :sessionId and "
operator|+
literal|"io.not_offered = 0 and sa.subject_area_abbreviation = a.subject and co.course_nbr like concat(a.course_number, '%')"
argument_list|)
return|;
block|}
specifier|protected
name|String
name|getPlaceHolderRegExp
parameter_list|()
block|{
return|return
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"banner.ucc.placeholder.regExp"
argument_list|,
literal|" ?UCC:? ?([^-]*[^- ]+)( ?-.*)?"
argument_list|)
return|;
block|}
specifier|protected
name|String
name|getPlaceHolderRenames
parameter_list|()
block|{
return|return
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"banner.ucc.placeholder.replacements"
argument_list|,
literal|"(?i:Behavioral/Social Science)|Behavior/Social Science\n(?i:Science,? Tech \\& Society( Selective)?)|Science, Tech& Society"
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|List
argument_list|<
name|XCourseId
argument_list|>
name|getCourses
parameter_list|(
name|OnlineSectioningServer
name|server
parameter_list|,
name|OnlineSectioningHelper
name|helper
parameter_list|,
name|String
name|query
parameter_list|)
block|{
if|if
condition|(
literal|"oc"
operator|.
name|equalsIgnoreCase
argument_list|(
name|query
argument_list|)
condition|)
name|query
operator|=
literal|"oral communication"
expr_stmt|;
if|if
condition|(
literal|"wc"
operator|.
name|equalsIgnoreCase
argument_list|(
name|query
argument_list|)
condition|)
name|query
operator|=
literal|"written communication"
expr_stmt|;
if|if
condition|(
name|query
operator|==
literal|null
operator|||
name|query
operator|.
name|length
argument_list|()
operator|<=
literal|2
condition|)
return|return
literal|null
return|;
name|String
name|regExp
init|=
name|getPlaceHolderRegExp
argument_list|()
decl_stmt|;
if|if
condition|(
name|regExp
operator|!=
literal|null
operator|&&
operator|!
name|regExp
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|Matcher
name|m
init|=
name|Pattern
operator|.
name|compile
argument_list|(
name|regExp
argument_list|)
operator|.
name|matcher
argument_list|(
name|query
argument_list|)
decl_stmt|;
if|if
condition|(
name|m
operator|.
name|matches
argument_list|()
condition|)
name|query
operator|=
name|m
operator|.
name|group
argument_list|(
literal|1
argument_list|)
expr_stmt|;
block|}
name|String
name|replacements
init|=
name|getPlaceHolderRenames
argument_list|()
decl_stmt|;
if|if
condition|(
name|replacements
operator|!=
literal|null
operator|&&
operator|!
name|replacements
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
for|for
control|(
name|String
name|rep
range|:
name|replacements
operator|.
name|split
argument_list|(
literal|"\n"
argument_list|)
control|)
block|{
name|int
name|idx
init|=
name|rep
operator|.
name|indexOf
argument_list|(
literal|'|'
argument_list|)
decl_stmt|;
if|if
condition|(
name|idx
operator|<=
literal|0
condition|)
continue|continue;
if|if
condition|(
name|query
operator|.
name|matches
argument_list|(
name|rep
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|idx
argument_list|)
argument_list|)
condition|)
block|{
name|query
operator|=
name|rep
operator|.
name|substring
argument_list|(
name|idx
operator|+
literal|1
argument_list|)
expr_stmt|;
break|break;
block|}
block|}
block|}
name|List
argument_list|<
name|XCourseId
argument_list|>
name|ret
init|=
operator|new
name|ArrayList
argument_list|<
name|XCourseId
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|Object
name|courseId
range|:
name|helper
operator|.
name|getHibSession
argument_list|()
operator|.
name|createSQLQuery
argument_list|(
name|getCourseLookupSQL
argument_list|()
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"sessionId"
argument_list|,
name|server
operator|.
name|getAcademicSession
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|)
operator|.
name|setString
argument_list|(
literal|"term"
argument_list|,
name|getBannerTerm
argument_list|(
name|server
operator|.
name|getAcademicSession
argument_list|()
argument_list|)
argument_list|)
operator|.
name|setString
argument_list|(
literal|"query"
argument_list|,
name|query
operator|.
name|toLowerCase
argument_list|()
argument_list|)
operator|.
name|list
argument_list|()
control|)
block|{
name|XCourse
name|course
init|=
name|server
operator|.
name|getCourse
argument_list|(
operator|(
operator|(
name|Number
operator|)
name|courseId
operator|)
operator|.
name|longValue
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|course
operator|!=
literal|null
condition|)
name|ret
operator|.
name|add
argument_list|(
name|course
argument_list|)
expr_stmt|;
block|}
name|Collections
operator|.
name|sort
argument_list|(
name|ret
argument_list|)
expr_stmt|;
return|return
name|ret
return|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|dispose
parameter_list|()
block|{
block|}
annotation|@
name|Override
specifier|public
name|List
argument_list|<
name|CourseOffering
argument_list|>
name|getCourses
parameter_list|(
name|AcademicSessionInfo
name|session
parameter_list|,
name|Session
name|hibSession
parameter_list|,
name|String
name|query
parameter_list|)
block|{
if|if
condition|(
literal|"oc"
operator|.
name|equalsIgnoreCase
argument_list|(
name|query
argument_list|)
condition|)
name|query
operator|=
literal|"oral communication"
expr_stmt|;
if|if
condition|(
literal|"wc"
operator|.
name|equalsIgnoreCase
argument_list|(
name|query
argument_list|)
condition|)
name|query
operator|=
literal|"written communication"
expr_stmt|;
if|if
condition|(
name|query
operator|==
literal|null
operator|||
name|query
operator|.
name|length
argument_list|()
operator|<=
literal|2
condition|)
return|return
literal|null
return|;
name|String
name|regExp
init|=
name|getPlaceHolderRegExp
argument_list|()
decl_stmt|;
if|if
condition|(
name|regExp
operator|!=
literal|null
operator|&&
operator|!
name|regExp
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|Matcher
name|m
init|=
name|Pattern
operator|.
name|compile
argument_list|(
name|regExp
argument_list|)
operator|.
name|matcher
argument_list|(
name|query
argument_list|)
decl_stmt|;
if|if
condition|(
name|m
operator|.
name|matches
argument_list|()
condition|)
name|query
operator|=
name|m
operator|.
name|group
argument_list|(
literal|1
argument_list|)
expr_stmt|;
block|}
name|String
name|replacements
init|=
name|getPlaceHolderRenames
argument_list|()
decl_stmt|;
if|if
condition|(
name|replacements
operator|!=
literal|null
operator|&&
operator|!
name|replacements
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
for|for
control|(
name|String
name|rep
range|:
name|replacements
operator|.
name|split
argument_list|(
literal|"\n"
argument_list|)
control|)
block|{
name|int
name|idx
init|=
name|rep
operator|.
name|indexOf
argument_list|(
literal|'|'
argument_list|)
decl_stmt|;
if|if
condition|(
name|idx
operator|<=
literal|0
condition|)
continue|continue;
if|if
condition|(
name|query
operator|.
name|matches
argument_list|(
name|rep
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|idx
argument_list|)
argument_list|)
condition|)
block|{
name|query
operator|=
name|rep
operator|.
name|substring
argument_list|(
name|idx
operator|+
literal|1
argument_list|)
expr_stmt|;
break|break;
block|}
block|}
block|}
name|List
name|courseIds
init|=
name|hibSession
operator|.
name|createSQLQuery
argument_list|(
name|getCourseLookupSQL
argument_list|()
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
name|setString
argument_list|(
literal|"term"
argument_list|,
name|getBannerTerm
argument_list|(
name|session
argument_list|)
argument_list|)
operator|.
name|setString
argument_list|(
literal|"query"
argument_list|,
name|query
operator|.
name|toLowerCase
argument_list|()
argument_list|)
operator|.
name|list
argument_list|()
decl_stmt|;
if|if
condition|(
name|courseIds
operator|==
literal|null
operator|||
name|courseIds
operator|.
name|isEmpty
argument_list|()
condition|)
return|return
literal|null
return|;
return|return
operator|(
name|List
argument_list|<
name|CourseOffering
argument_list|>
operator|)
name|hibSession
operator|.
name|createQuery
argument_list|(
literal|"from CourseOffering where uniqueId in :courseIds order by subjectAreaAbbv, courseNbr"
argument_list|)
operator|.
name|setParameterList
argument_list|(
literal|"courseIds"
argument_list|,
name|courseIds
argument_list|,
name|BigDecimalType
operator|.
name|INSTANCE
argument_list|)
operator|.
name|setCacheable
argument_list|(
literal|true
argument_list|)
operator|.
name|list
argument_list|()
return|;
block|}
block|}
end_class

end_unit

