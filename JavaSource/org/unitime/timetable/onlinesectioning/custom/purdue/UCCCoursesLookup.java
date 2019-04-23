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
name|Collection
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
name|java
operator|.
name|util
operator|.
name|TreeSet
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
name|hibernate
operator|.
name|type
operator|.
name|LongType
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
name|gwt
operator|.
name|shared
operator|.
name|EventInterface
operator|.
name|FilterRpcResponse
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
specifier|private
name|Map
argument_list|<
name|Long
argument_list|,
name|CourseAttributeCache
argument_list|>
name|iCache
init|=
operator|new
name|HashMap
argument_list|<
name|Long
argument_list|,
name|CourseAttributeCache
argument_list|>
argument_list|()
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
name|getCourseLookupFullSQL
parameter_list|()
block|{
return|return
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"banner.ucc.lookupSQLfull"
argument_list|,
literal|"select distinct co.uniqueid "
operator|+
literal|"from timetable.szv_utm_attr a, timetable.course_offering co, timetable.instructional_offering io, timetable.subject_area sa "
operator|+
literal|"where a.term_start<= :term and a.term_end>= :term and "
operator|+
literal|"(lower(a.attribute_description) = concat('uc-', :query) or lower(a.course_attribute) = :query) and "
operator|+
literal|"co.instr_offr_id = io.uniqueid and co.subject_area_id = sa.uniqueid and io.session_id = :sessionId and "
operator|+
literal|"io.not_offered = 0 and sa.subject_area_abbreviation = a.subject and co.course_nbr like concat(a.course_number, '%')"
argument_list|)
return|;
block|}
specifier|protected
name|String
name|getCourseLookupPartialSQL
parameter_list|()
block|{
return|return
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"banner.ucc.lookupSQLpartial"
argument_list|,
literal|"select distinct co.uniqueid "
operator|+
literal|"from timetable.szv_utm_attr a, timetable.course_offering co, timetable.instructional_offering io, timetable.subject_area sa "
operator|+
literal|"where a.term_start<= :term and a.term_end>= :term and "
operator|+
literal|"(lower(a.attribute_description) like concat(concat('uc-', :query), '%') or lower(a.attribute_description) like concat(concat('% ', :query), '%')) and "
operator|+
literal|"co.instr_offr_id = io.uniqueid and co.subject_area_id = sa.uniqueid and io.session_id = :sessionId and "
operator|+
literal|"io.not_offered = 0 and sa.subject_area_abbreviation = a.subject and co.course_nbr like concat(a.course_number, '%')"
argument_list|)
return|;
block|}
specifier|protected
name|String
name|getSuggestionSQL
parameter_list|()
block|{
return|return
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"banner.ucc.suggestionsSQL"
argument_list|,
literal|"select distinct a.course_attribute, a.attribute_description "
operator|+
literal|"from timetable.szv_utm_attr a where a.term_start<= :term and a.term_end>= :term and "
operator|+
literal|"(lower(a.attribute_description) like concat(concat('uc-', :query), '%') or lower(a.attribute_description) like concat(concat('% ', :query), '%') or lower(a.course_attribute) = :query)"
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
literal|"( ?UCC:? ?)?([^-]*[^- ]+)( ?-.*)?"
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
literal|"(?i:Behavioral/Social Science)|Behavior/Social Science\n"
operator|+
literal|"(?i:Science,? Tech \\& Society( Selective)?)|Science, Tech& Society\n"
operator|+
literal|"(?i:Oral Communication)|Oral Communications\n"
operator|+
literal|"(?i:Written Communications)|Written Communication\n"
operator|+
literal|"(?i:Oral/Written Communications)|Communication"
argument_list|)
return|;
block|}
specifier|protected
name|String
name|getListAttributesSQL
parameter_list|()
block|{
return|return
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"banner.ucc.cachedSQL"
argument_list|,
literal|"select distinct a.course_attribute, a.attribute_description, co.uniqueId "
operator|+
literal|"from timetable.szv_utm_attr a, timetable.course_offering co, timetable.instructional_offering io, timetable.subject_area sa "
operator|+
literal|"where a.term_start<= :term and a.term_end>= :term and "
operator|+
literal|"co.instr_offr_id = io.uniqueid and co.subject_area_id = sa.uniqueid and io.session_id = :sessionId and "
operator|+
literal|"io.not_offered = 0 and sa.subject_area_abbreviation = a.subject and co.course_nbr like concat(a.course_number, '%')"
argument_list|)
return|;
block|}
specifier|protected
name|boolean
name|useCache
parameter_list|()
block|{
return|return
literal|"true"
operator|.
name|equalsIgnoreCase
argument_list|(
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"banner.ucc.useCache"
argument_list|,
literal|"false"
argument_list|)
argument_list|)
return|;
block|}
specifier|protected
name|long
name|getCourseAttributesTTL
parameter_list|()
block|{
return|return
literal|1000l
operator|*
name|Long
operator|.
name|valueOf
argument_list|(
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"banner.ucc.ttlSeconds"
argument_list|,
literal|"900"
argument_list|)
argument_list|)
return|;
block|}
specifier|protected
specifier|synchronized
name|Collection
argument_list|<
name|CourseAttribute
argument_list|>
name|getCourseAttributes
parameter_list|(
name|AcademicSessionInfo
name|session
parameter_list|,
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
parameter_list|)
block|{
name|CourseAttributeCache
name|cache
init|=
name|iCache
operator|.
name|get
argument_list|(
name|session
operator|.
name|getUniqueId
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|cache
operator|==
literal|null
operator|||
operator|!
name|cache
operator|.
name|isActive
argument_list|(
name|getCourseAttributesTTL
argument_list|()
argument_list|)
condition|)
block|{
name|Map
argument_list|<
name|String
argument_list|,
name|CourseAttribute
argument_list|>
name|attributes
init|=
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|CourseAttribute
argument_list|>
argument_list|()
decl_stmt|;
name|String
name|term
init|=
name|getBannerTerm
argument_list|(
name|session
argument_list|)
decl_stmt|;
for|for
control|(
name|Object
index|[]
name|data
range|:
operator|(
name|List
argument_list|<
name|Object
index|[]
argument_list|>
operator|)
operator|(
name|hibSession
operator|==
literal|null
condition|?
operator|new
name|_RootDAO
argument_list|()
operator|.
name|getSession
argument_list|()
else|:
name|hibSession
operator|)
operator|.
name|createSQLQuery
argument_list|(
name|getListAttributesSQL
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
name|term
argument_list|)
operator|.
name|list
argument_list|()
control|)
block|{
name|String
name|course_attribute
init|=
operator|(
name|String
operator|)
name|data
index|[
literal|0
index|]
decl_stmt|;
name|String
name|attribute_description
init|=
operator|(
name|String
operator|)
name|data
index|[
literal|1
index|]
decl_stmt|;
name|Long
name|courseId
init|=
operator|(
operator|(
name|Number
operator|)
name|data
index|[
literal|2
index|]
operator|)
operator|.
name|longValue
argument_list|()
decl_stmt|;
name|CourseAttribute
name|att
init|=
name|attributes
operator|.
name|get
argument_list|(
name|course_attribute
argument_list|)
decl_stmt|;
if|if
condition|(
name|att
operator|==
literal|null
condition|)
block|{
name|att
operator|=
operator|new
name|CourseAttribute
argument_list|(
name|course_attribute
argument_list|,
name|attribute_description
argument_list|)
expr_stmt|;
name|attributes
operator|.
name|put
argument_list|(
name|course_attribute
argument_list|,
name|att
argument_list|)
expr_stmt|;
block|}
name|att
operator|.
name|addCourse
argument_list|(
name|courseId
argument_list|)
expr_stmt|;
block|}
name|cache
operator|=
operator|new
name|CourseAttributeCache
argument_list|(
name|attributes
operator|.
name|values
argument_list|()
argument_list|)
expr_stmt|;
name|iCache
operator|.
name|put
argument_list|(
name|session
operator|.
name|getUniqueId
argument_list|()
argument_list|,
name|cache
argument_list|)
expr_stmt|;
name|sLog
operator|.
name|info
argument_list|(
literal|"UCC Cache ["
operator|+
name|session
operator|+
literal|"]: "
operator|+
name|ToolBox
operator|.
name|col2string
argument_list|(
name|cache
operator|.
name|getAttributes
argument_list|()
argument_list|,
literal|2
argument_list|)
argument_list|)
expr_stmt|;
block|}
return|return
name|cache
operator|.
name|getAttributes
argument_list|()
return|;
block|}
specifier|protected
name|String
name|fixQuery
parameter_list|(
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
literal|"oral communications"
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
literal|3
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
literal|2
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
return|return
name|query
operator|.
name|toLowerCase
argument_list|()
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
parameter_list|,
name|boolean
name|allowPartialMatch
parameter_list|)
block|{
name|String
name|q
init|=
name|fixQuery
argument_list|(
name|query
argument_list|)
decl_stmt|;
if|if
condition|(
name|q
operator|==
literal|null
condition|)
return|return
literal|null
return|;
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
if|if
condition|(
name|useCache
argument_list|()
condition|)
block|{
name|boolean
name|fullMatch
init|=
literal|false
decl_stmt|;
for|for
control|(
name|CourseAttribute
name|ca
range|:
name|getCourseAttributes
argument_list|(
name|server
operator|.
name|getAcademicSession
argument_list|()
argument_list|,
name|helper
operator|.
name|getHibSession
argument_list|()
argument_list|)
control|)
block|{
if|if
condition|(
name|ca
operator|.
name|isFullMatch
argument_list|(
name|q
argument_list|)
condition|)
block|{
if|if
condition|(
operator|!
name|fullMatch
condition|)
block|{
name|fullMatch
operator|=
literal|true
expr_stmt|;
name|ret
operator|.
name|clear
argument_list|()
expr_stmt|;
block|}
for|for
control|(
name|Long
name|courseId
range|:
name|ca
operator|.
name|getCourseIds
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
name|courseId
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
block|}
if|else if
condition|(
name|allowPartialMatch
operator|&&
operator|!
name|fullMatch
operator|&&
name|ca
operator|.
name|isPartialMatch
argument_list|(
name|q
argument_list|)
condition|)
block|{
for|for
control|(
name|Long
name|courseId
range|:
name|ca
operator|.
name|getCourseIds
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
name|courseId
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
block|}
block|}
block|}
else|else
block|{
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
name|getCourseLookupFullSQL
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
name|q
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
if|if
condition|(
name|allowPartialMatch
operator|&&
name|ret
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
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
name|getCourseLookupPartialSQL
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
name|q
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
block|}
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
parameter_list|,
name|boolean
name|allowPartialMatch
parameter_list|)
block|{
name|String
name|q
init|=
name|fixQuery
argument_list|(
name|query
argument_list|)
decl_stmt|;
if|if
condition|(
name|q
operator|==
literal|null
condition|)
return|return
literal|null
return|;
if|if
condition|(
name|useCache
argument_list|()
condition|)
block|{
name|List
argument_list|<
name|Long
argument_list|>
name|courseIds
init|=
operator|new
name|ArrayList
argument_list|<
name|Long
argument_list|>
argument_list|()
decl_stmt|;
name|boolean
name|fullMatch
init|=
literal|false
decl_stmt|;
for|for
control|(
name|CourseAttribute
name|ca
range|:
name|getCourseAttributes
argument_list|(
name|session
argument_list|,
name|hibSession
argument_list|)
control|)
block|{
if|if
condition|(
name|ca
operator|.
name|isFullMatch
argument_list|(
name|q
argument_list|)
condition|)
block|{
if|if
condition|(
operator|!
name|fullMatch
condition|)
block|{
name|fullMatch
operator|=
literal|true
expr_stmt|;
name|courseIds
operator|.
name|clear
argument_list|()
expr_stmt|;
block|}
name|courseIds
operator|.
name|addAll
argument_list|(
name|ca
operator|.
name|getCourseIds
argument_list|()
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|allowPartialMatch
operator|&&
operator|!
name|fullMatch
operator|&&
name|ca
operator|.
name|isPartialMatch
argument_list|(
name|q
argument_list|)
condition|)
block|{
name|courseIds
operator|.
name|addAll
argument_list|(
name|ca
operator|.
name|getCourseIds
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
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
name|LongType
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
else|else
block|{
name|List
name|courseIds
init|=
name|hibSession
operator|.
name|createSQLQuery
argument_list|(
name|getCourseLookupFullSQL
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
name|q
argument_list|)
operator|.
name|list
argument_list|()
decl_stmt|;
if|if
condition|(
name|allowPartialMatch
operator|&&
operator|(
name|courseIds
operator|==
literal|null
operator|||
name|courseIds
operator|.
name|isEmpty
argument_list|()
operator|)
condition|)
block|{
name|courseIds
operator|=
name|hibSession
operator|.
name|createSQLQuery
argument_list|(
name|getCourseLookupPartialSQL
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
name|q
argument_list|)
operator|.
name|list
argument_list|()
expr_stmt|;
block|}
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
annotation|@
name|Override
specifier|public
name|Set
argument_list|<
name|Long
argument_list|>
name|getCourseIds
parameter_list|(
name|AcademicSessionInfo
name|session
parameter_list|,
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
parameter_list|,
name|String
name|query
parameter_list|,
name|boolean
name|allowPartialMatch
parameter_list|)
block|{
name|String
name|q
init|=
name|fixQuery
argument_list|(
name|query
argument_list|)
decl_stmt|;
if|if
condition|(
name|q
operator|==
literal|null
condition|)
return|return
literal|null
return|;
if|if
condition|(
name|useCache
argument_list|()
condition|)
block|{
name|Set
argument_list|<
name|Long
argument_list|>
name|courseIds
init|=
operator|new
name|HashSet
argument_list|<
name|Long
argument_list|>
argument_list|()
decl_stmt|;
name|boolean
name|fullMatch
init|=
literal|false
decl_stmt|;
for|for
control|(
name|CourseAttribute
name|ca
range|:
name|getCourseAttributes
argument_list|(
name|session
argument_list|,
name|hibSession
argument_list|)
control|)
block|{
if|if
condition|(
name|ca
operator|.
name|isFullMatch
argument_list|(
name|q
argument_list|)
condition|)
block|{
if|if
condition|(
operator|!
name|fullMatch
condition|)
block|{
name|fullMatch
operator|=
literal|true
expr_stmt|;
name|courseIds
operator|.
name|clear
argument_list|()
expr_stmt|;
block|}
name|courseIds
operator|.
name|addAll
argument_list|(
name|ca
operator|.
name|getCourseIds
argument_list|()
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|allowPartialMatch
operator|&&
operator|!
name|fullMatch
operator|&&
name|ca
operator|.
name|isPartialMatch
argument_list|(
name|q
argument_list|)
condition|)
block|{
name|courseIds
operator|.
name|addAll
argument_list|(
name|ca
operator|.
name|getCourseIds
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|courseIds
return|;
block|}
else|else
block|{
if|if
condition|(
name|hibSession
operator|==
literal|null
condition|)
name|hibSession
operator|=
operator|new
name|_RootDAO
argument_list|()
operator|.
name|getSession
argument_list|()
expr_stmt|;
name|List
name|courseIds
init|=
name|hibSession
operator|.
name|createSQLQuery
argument_list|(
name|getCourseLookupFullSQL
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
name|q
argument_list|)
operator|.
name|list
argument_list|()
decl_stmt|;
if|if
condition|(
name|allowPartialMatch
operator|&&
operator|(
name|courseIds
operator|==
literal|null
operator|||
name|courseIds
operator|.
name|isEmpty
argument_list|()
operator|)
condition|)
block|{
name|courseIds
operator|=
name|hibSession
operator|.
name|createSQLQuery
argument_list|(
name|getCourseLookupPartialSQL
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
name|q
argument_list|)
operator|.
name|list
argument_list|()
expr_stmt|;
block|}
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
name|Set
argument_list|<
name|Long
argument_list|>
name|ret
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
name|Object
name|courseId
range|:
name|courseIds
control|)
name|ret
operator|.
name|add
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
expr_stmt|;
return|return
name|ret
return|;
block|}
block|}
specifier|private
specifier|static
class|class
name|CourseAttributeCache
block|{
name|Set
argument_list|<
name|CourseAttribute
argument_list|>
name|iAttributes
decl_stmt|;
name|long
name|iTimeStamp
decl_stmt|;
specifier|public
name|CourseAttributeCache
parameter_list|(
name|Collection
argument_list|<
name|CourseAttribute
argument_list|>
name|attributes
parameter_list|)
block|{
name|iAttributes
operator|=
operator|new
name|TreeSet
argument_list|<
name|CourseAttribute
argument_list|>
argument_list|(
name|attributes
argument_list|)
expr_stmt|;
name|iTimeStamp
operator|=
name|System
operator|.
name|currentTimeMillis
argument_list|()
expr_stmt|;
block|}
specifier|public
name|Collection
argument_list|<
name|CourseAttribute
argument_list|>
name|getAttributes
parameter_list|()
block|{
return|return
name|iAttributes
return|;
block|}
specifier|public
name|boolean
name|isActive
parameter_list|(
name|long
name|ttl
parameter_list|)
block|{
return|return
operator|(
name|System
operator|.
name|currentTimeMillis
argument_list|()
operator|-
name|iTimeStamp
operator|)
operator|<=
name|ttl
return|;
block|}
block|}
specifier|private
specifier|static
class|class
name|CourseAttribute
implements|implements
name|Comparable
argument_list|<
name|CourseAttribute
argument_list|>
block|{
name|String
name|iCourseAttribute
decl_stmt|,
name|iAttributeDescription
decl_stmt|,
name|iAttributeDescriptionLowCase
decl_stmt|;
name|List
argument_list|<
name|Long
argument_list|>
name|iCourseIds
init|=
operator|new
name|ArrayList
argument_list|<
name|Long
argument_list|>
argument_list|()
decl_stmt|;
specifier|private
name|CourseAttribute
parameter_list|(
name|String
name|courseAttribute
parameter_list|,
name|String
name|attributeDescription
parameter_list|)
block|{
name|iCourseAttribute
operator|=
name|courseAttribute
expr_stmt|;
name|iAttributeDescription
operator|=
name|attributeDescription
expr_stmt|;
name|iAttributeDescriptionLowCase
operator|=
name|attributeDescription
operator|.
name|toLowerCase
argument_list|()
expr_stmt|;
block|}
name|void
name|addCourse
parameter_list|(
name|Long
name|courseId
parameter_list|)
block|{
name|iCourseIds
operator|.
name|add
argument_list|(
name|courseId
argument_list|)
expr_stmt|;
block|}
name|List
argument_list|<
name|Long
argument_list|>
name|getCourseIds
parameter_list|()
block|{
return|return
name|iCourseIds
return|;
block|}
specifier|public
name|boolean
name|isFullMatch
parameter_list|(
name|String
name|query
parameter_list|)
block|{
return|return
name|iCourseAttribute
operator|.
name|equalsIgnoreCase
argument_list|(
name|query
argument_list|)
operator|||
name|iAttributeDescription
operator|.
name|equalsIgnoreCase
argument_list|(
literal|"uc-"
operator|+
name|query
argument_list|)
return|;
block|}
specifier|public
name|boolean
name|isPartialMatch
parameter_list|(
name|String
name|query
parameter_list|)
block|{
return|return
name|iAttributeDescriptionLowCase
operator|.
name|startsWith
argument_list|(
literal|"uc-"
operator|+
name|query
argument_list|)
operator|||
name|iAttributeDescriptionLowCase
operator|.
name|contains
argument_list|(
literal|" "
operator|+
name|query
argument_list|)
return|;
block|}
specifier|public
name|String
name|getCourseAttribute
parameter_list|()
block|{
return|return
name|iCourseAttribute
return|;
block|}
specifier|public
name|String
name|getAttributeDescription
parameter_list|()
block|{
return|return
name|iAttributeDescription
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
name|iCourseAttribute
operator|+
literal|" - "
operator|+
name|iAttributeDescription
operator|+
literal|": "
operator|+
name|iCourseIds
operator|.
name|size
argument_list|()
operator|+
literal|" courses"
return|;
block|}
annotation|@
name|Override
specifier|public
name|int
name|compareTo
parameter_list|(
name|CourseAttribute
name|ca
parameter_list|)
block|{
return|return
name|iCourseAttribute
operator|.
name|compareTo
argument_list|(
name|ca
operator|.
name|iCourseAttribute
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|int
name|hashCode
parameter_list|()
block|{
return|return
name|iCourseAttribute
operator|.
name|hashCode
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|equals
parameter_list|(
name|Object
name|o
parameter_list|)
block|{
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
name|CourseAttribute
operator|)
condition|)
return|return
literal|false
return|;
return|return
name|iCourseAttribute
operator|.
name|equals
argument_list|(
operator|(
operator|(
name|CourseAttribute
operator|)
name|o
operator|)
operator|.
name|iCourseAttribute
argument_list|)
return|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|void
name|addSuggestions
parameter_list|(
name|OnlineSectioningServer
name|server
parameter_list|,
name|OnlineSectioningHelper
name|helper
parameter_list|,
name|String
name|query
parameter_list|,
name|FilterRpcResponse
name|filter
parameter_list|)
block|{
name|String
name|q
init|=
name|fixQuery
argument_list|(
name|query
argument_list|)
decl_stmt|;
if|if
condition|(
name|q
operator|==
literal|null
condition|)
return|return;
if|if
condition|(
name|useCache
argument_list|()
condition|)
block|{
for|for
control|(
name|CourseAttribute
name|ca
range|:
name|getCourseAttributes
argument_list|(
name|server
operator|.
name|getAcademicSession
argument_list|()
argument_list|,
name|helper
operator|.
name|getHibSession
argument_list|()
argument_list|)
control|)
block|{
if|if
condition|(
name|ca
operator|.
name|isFullMatch
argument_list|(
name|q
argument_list|)
operator|||
name|ca
operator|.
name|isPartialMatch
argument_list|(
name|q
argument_list|)
condition|)
block|{
name|filter
operator|.
name|addSuggestion
argument_list|(
name|ca
operator|.
name|getAttributeDescription
argument_list|()
argument_list|,
name|ca
operator|.
name|getCourseAttribute
argument_list|()
argument_list|,
literal|"UCC Attribute"
argument_list|,
literal|"lookup"
argument_list|,
literal|true
argument_list|)
expr_stmt|;
block|}
block|}
block|}
else|else
block|{
for|for
control|(
name|Object
index|[]
name|data
range|:
operator|(
name|List
argument_list|<
name|Object
index|[]
argument_list|>
operator|)
name|helper
operator|.
name|getHibSession
argument_list|()
operator|.
name|createSQLQuery
argument_list|(
name|getSuggestionSQL
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
name|q
argument_list|)
operator|.
name|list
argument_list|()
control|)
block|{
name|String
name|course_attribute
init|=
operator|(
name|String
operator|)
name|data
index|[
literal|0
index|]
decl_stmt|;
name|String
name|attribute_description
init|=
operator|(
name|String
operator|)
name|data
index|[
literal|1
index|]
decl_stmt|;
name|filter
operator|.
name|addSuggestion
argument_list|(
name|attribute_description
argument_list|,
name|course_attribute
argument_list|,
literal|"UCC Attribute"
argument_list|,
literal|"lookup"
argument_list|,
literal|true
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
end_class

end_unit
