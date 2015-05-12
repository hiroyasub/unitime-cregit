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
name|io
operator|.
name|IOException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|lang
operator|.
name|reflect
operator|.
name|Type
import|;
end_import

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
name|List
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
name|restlet
operator|.
name|Client
import|;
end_import

begin_import
import|import
name|org
operator|.
name|restlet
operator|.
name|Response
import|;
end_import

begin_import
import|import
name|org
operator|.
name|restlet
operator|.
name|data
operator|.
name|ChallengeScheme
import|;
end_import

begin_import
import|import
name|org
operator|.
name|restlet
operator|.
name|data
operator|.
name|MediaType
import|;
end_import

begin_import
import|import
name|org
operator|.
name|restlet
operator|.
name|data
operator|.
name|Protocol
import|;
end_import

begin_import
import|import
name|org
operator|.
name|restlet
operator|.
name|resource
operator|.
name|ClientResource
import|;
end_import

begin_import
import|import
name|org
operator|.
name|restlet
operator|.
name|resource
operator|.
name|ResourceException
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
name|CourseRequestInterface
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
name|SectioningException
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
name|OnlineSectioningLog
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
name|CourseRequestsProvider
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
name|XCourseId
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
name|XStudent
import|;
end_import

begin_import
import|import
name|com
operator|.
name|google
operator|.
name|gson
operator|.
name|Gson
import|;
end_import

begin_import
import|import
name|com
operator|.
name|google
operator|.
name|gson
operator|.
name|GsonBuilder
import|;
end_import

begin_import
import|import
name|com
operator|.
name|google
operator|.
name|gson
operator|.
name|JsonIOException
import|;
end_import

begin_import
import|import
name|com
operator|.
name|google
operator|.
name|gson
operator|.
name|JsonSyntaxException
import|;
end_import

begin_import
import|import
name|com
operator|.
name|google
operator|.
name|gson
operator|.
name|stream
operator|.
name|JsonReader
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|DegreeWorksCourseRequests
implements|implements
name|CourseRequestsProvider
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
name|DegreeWorksCourseRequests
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|private
name|String
name|iDegreeWorksApiUrl
init|=
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"banner.dgw.site"
argument_list|)
decl_stmt|;
specifier|private
name|String
name|iDegreeWorksApiUser
init|=
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"banner.dgw.user"
argument_list|)
decl_stmt|;
specifier|private
name|String
name|iDegreeWorksApiPassword
init|=
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"banner.dgw.password"
argument_list|)
decl_stmt|;
specifier|private
name|String
name|iDegreeWorksApiEffectiveOnly
init|=
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"banner.dgw.effectiveOnly"
argument_list|)
decl_stmt|;
specifier|private
name|Client
name|iClient
decl_stmt|;
specifier|private
name|ExternalTermProvider
name|iExternalTermProvider
decl_stmt|;
specifier|public
name|DegreeWorksCourseRequests
parameter_list|()
block|{
name|List
argument_list|<
name|Protocol
argument_list|>
name|protocols
init|=
operator|new
name|ArrayList
argument_list|<
name|Protocol
argument_list|>
argument_list|()
decl_stmt|;
name|protocols
operator|.
name|add
argument_list|(
name|Protocol
operator|.
name|HTTP
argument_list|)
expr_stmt|;
name|protocols
operator|.
name|add
argument_list|(
name|Protocol
operator|.
name|HTTPS
argument_list|)
expr_stmt|;
name|iClient
operator|=
operator|new
name|Client
argument_list|(
name|protocols
argument_list|)
expr_stmt|;
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
name|getBannerId
parameter_list|(
name|XStudent
name|student
parameter_list|)
block|{
name|String
name|id
init|=
name|student
operator|.
name|getExternalId
argument_list|()
decl_stmt|;
while|while
condition|(
name|id
operator|.
name|length
argument_list|()
operator|<
literal|9
condition|)
name|id
operator|=
literal|"0"
operator|+
name|id
expr_stmt|;
return|return
name|id
return|;
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
name|Gson
name|getGson
parameter_list|(
name|OnlineSectioningHelper
name|helper
parameter_list|)
block|{
name|GsonBuilder
name|builder
init|=
operator|new
name|GsonBuilder
argument_list|()
decl_stmt|;
if|if
condition|(
name|helper
operator|.
name|isDebugEnabled
argument_list|()
condition|)
name|builder
operator|.
name|setPrettyPrinting
argument_list|()
expr_stmt|;
return|return
name|builder
operator|.
name|create
argument_list|()
return|;
block|}
specifier|protected
parameter_list|<
name|T
parameter_list|>
name|T
name|readResponse
parameter_list|(
name|Gson
name|gson
parameter_list|,
name|Response
name|response
parameter_list|,
name|Type
name|typeOfT
parameter_list|)
throws|throws
name|JsonIOException
throws|,
name|JsonSyntaxException
throws|,
name|IOException
block|{
if|if
condition|(
name|response
operator|==
literal|null
condition|)
return|return
literal|null
return|;
name|JsonReader
name|reader
init|=
operator|new
name|JsonReader
argument_list|(
name|response
operator|.
name|getEntity
argument_list|()
operator|.
name|getReader
argument_list|()
argument_list|)
decl_stmt|;
try|try
block|{
return|return
name|gson
operator|.
name|fromJson
argument_list|(
name|reader
argument_list|,
name|typeOfT
argument_list|)
return|;
block|}
finally|finally
block|{
name|reader
operator|.
name|close
argument_list|()
expr_stmt|;
name|response
operator|.
name|release
argument_list|()
expr_stmt|;
block|}
block|}
specifier|protected
name|XCourseId
name|getCourse
parameter_list|(
name|OnlineSectioningServer
name|server
parameter_list|,
name|XEInterface
operator|.
name|Course
name|course
parameter_list|)
block|{
name|Collection
argument_list|<
name|?
extends|extends
name|XCourseId
argument_list|>
name|courses
init|=
name|server
operator|.
name|findCourses
argument_list|(
name|course
operator|.
name|courseDiscipline
operator|+
literal|" "
operator|+
name|course
operator|.
name|courseNumber
argument_list|,
operator|-
literal|1
argument_list|,
literal|null
argument_list|)
decl_stmt|;
for|for
control|(
name|XCourseId
name|c
range|:
name|courses
control|)
if|if
condition|(
name|c
operator|.
name|matchTitle
argument_list|(
name|course
operator|.
name|title
argument_list|)
condition|)
return|return
name|c
return|;
for|for
control|(
name|XCourseId
name|c
range|:
name|courses
control|)
return|return
name|c
return|;
return|return
operator|new
name|XCourseId
argument_list|(
literal|null
argument_list|,
literal|null
argument_list|,
name|course
operator|.
name|courseDiscipline
operator|+
literal|" "
operator|+
name|course
operator|.
name|courseNumber
argument_list|)
return|;
block|}
specifier|protected
name|OnlineSectioningLog
operator|.
name|Entity
name|toEntity
parameter_list|(
name|XEInterface
operator|.
name|Course
name|course
parameter_list|,
name|XCourseId
name|courseId
parameter_list|)
block|{
name|OnlineSectioningLog
operator|.
name|Entity
operator|.
name|Builder
name|builder
init|=
name|OnlineSectioningLog
operator|.
name|Entity
operator|.
name|newBuilder
argument_list|()
decl_stmt|;
if|if
condition|(
name|courseId
operator|.
name|getCourseId
argument_list|()
operator|!=
literal|null
condition|)
name|builder
operator|.
name|setUniqueId
argument_list|(
name|courseId
operator|.
name|getCourseId
argument_list|()
argument_list|)
expr_stmt|;
name|builder
operator|.
name|setName
argument_list|(
name|courseId
operator|.
name|getCourseName
argument_list|()
argument_list|)
expr_stmt|;
name|builder
operator|.
name|setExternalId
argument_list|(
name|course
operator|.
name|courseDiscipline
operator|+
literal|" "
operator|+
name|course
operator|.
name|courseNumber
operator|+
operator|(
name|course
operator|.
name|title
operator|!=
literal|null
operator|&&
operator|!
name|course
operator|.
name|title
operator|.
name|isEmpty
argument_list|()
condition|?
literal|" - "
operator|+
name|course
operator|.
name|title
else|:
literal|""
operator|)
argument_list|)
expr_stmt|;
return|return
name|builder
operator|.
name|build
argument_list|()
return|;
block|}
specifier|protected
name|void
name|fillInRequests
parameter_list|(
name|OnlineSectioningServer
name|server
parameter_list|,
name|OnlineSectioningHelper
name|helper
parameter_list|,
name|CourseRequestInterface
name|request
parameter_list|,
name|XEInterface
operator|.
name|Group
name|group
parameter_list|)
block|{
if|if
condition|(
literal|"CH"
operator|.
name|equals
argument_list|(
name|group
operator|.
name|groupType
operator|.
name|code
argument_list|)
condition|)
block|{
comment|// choice group -- pick one
name|CourseRequestInterface
operator|.
name|Request
name|r
init|=
operator|new
name|CourseRequestInterface
operator|.
name|Request
argument_list|()
decl_stmt|;
name|OnlineSectioningLog
operator|.
name|Request
operator|.
name|Builder
name|b
init|=
name|OnlineSectioningLog
operator|.
name|Request
operator|.
name|newBuilder
argument_list|()
operator|.
name|setPriority
argument_list|(
name|request
operator|.
name|getCourses
argument_list|()
operator|.
name|size
argument_list|()
argument_list|)
operator|.
name|setAlternative
argument_list|(
literal|false
argument_list|)
decl_stmt|;
comment|// try selected course(s) first
for|for
control|(
name|XEInterface
operator|.
name|Course
name|course
range|:
name|group
operator|.
name|plannedClasses
control|)
block|{
if|if
condition|(
name|course
operator|.
name|isGroupSelection
condition|)
block|{
name|XCourseId
name|cid
init|=
name|getCourse
argument_list|(
name|server
argument_list|,
name|course
argument_list|)
decl_stmt|;
if|if
condition|(
name|cid
operator|==
literal|null
condition|)
continue|continue;
if|if
condition|(
operator|!
name|r
operator|.
name|hasRequestedCourse
argument_list|()
condition|)
block|{
name|r
operator|.
name|setRequestedCourse
argument_list|(
name|cid
operator|.
name|getCourseName
argument_list|()
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
operator|!
name|r
operator|.
name|hasFirstAlternative
argument_list|()
condition|)
block|{
name|r
operator|.
name|setFirstAlternative
argument_list|(
name|cid
operator|.
name|getCourseName
argument_list|()
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
operator|!
name|r
operator|.
name|hasSecondAlternative
argument_list|()
condition|)
block|{
name|r
operator|.
name|setSecondAlternative
argument_list|(
name|cid
operator|.
name|getCourseName
argument_list|()
argument_list|)
expr_stmt|;
break|break;
block|}
name|b
operator|.
name|addCourse
argument_list|(
name|toEntity
argument_list|(
name|course
argument_list|,
name|cid
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
comment|// no selection -> check if there is a selected group
if|if
condition|(
operator|!
name|r
operator|.
name|hasRequestedCourse
argument_list|()
condition|)
for|for
control|(
name|XEInterface
operator|.
name|Group
name|g
range|:
name|group
operator|.
name|groups
control|)
block|{
if|if
condition|(
name|g
operator|.
name|isGroupSelection
condition|)
name|fillInRequests
argument_list|(
name|server
argument_list|,
name|helper
argument_list|,
name|request
argument_list|,
name|g
argument_list|)
expr_stmt|;
return|return;
block|}
comment|// no selection -> use the first three courses as alternatives
if|if
condition|(
operator|!
name|r
operator|.
name|hasRequestedCourse
argument_list|()
condition|)
block|{
for|for
control|(
name|XEInterface
operator|.
name|Course
name|course
range|:
name|group
operator|.
name|plannedClasses
control|)
block|{
name|XCourseId
name|cid
init|=
name|getCourse
argument_list|(
name|server
argument_list|,
name|course
argument_list|)
decl_stmt|;
if|if
condition|(
name|cid
operator|==
literal|null
condition|)
continue|continue;
if|if
condition|(
operator|!
name|r
operator|.
name|hasRequestedCourse
argument_list|()
condition|)
block|{
name|r
operator|.
name|setRequestedCourse
argument_list|(
name|cid
operator|.
name|getCourseName
argument_list|()
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
operator|!
name|r
operator|.
name|hasFirstAlternative
argument_list|()
condition|)
block|{
name|r
operator|.
name|setFirstAlternative
argument_list|(
name|cid
operator|.
name|getCourseName
argument_list|()
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
operator|!
name|r
operator|.
name|hasSecondAlternative
argument_list|()
condition|)
block|{
name|r
operator|.
name|setSecondAlternative
argument_list|(
name|cid
operator|.
name|getCourseName
argument_list|()
argument_list|)
expr_stmt|;
break|break;
block|}
name|b
operator|.
name|addCourse
argument_list|(
name|toEntity
argument_list|(
name|course
argument_list|,
name|cid
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
if|if
condition|(
name|r
operator|.
name|hasRequestedCourse
argument_list|()
condition|)
block|{
name|helper
operator|.
name|getAction
argument_list|()
operator|.
name|addRequest
argument_list|(
name|b
argument_list|)
expr_stmt|;
name|request
operator|.
name|getCourses
argument_list|()
operator|.
name|add
argument_list|(
name|r
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
comment|// union group -- take all courses (and sub-groups)
for|for
control|(
name|XEInterface
operator|.
name|Course
name|course
range|:
name|group
operator|.
name|plannedClasses
control|)
block|{
name|XCourseId
name|cid
init|=
name|getCourse
argument_list|(
name|server
argument_list|,
name|course
argument_list|)
decl_stmt|;
if|if
condition|(
name|cid
operator|!=
literal|null
condition|)
block|{
name|CourseRequestInterface
operator|.
name|Request
name|r
init|=
operator|new
name|CourseRequestInterface
operator|.
name|Request
argument_list|()
decl_stmt|;
name|r
operator|.
name|setRequestedCourse
argument_list|(
name|cid
operator|.
name|getCourseName
argument_list|()
argument_list|)
expr_stmt|;
name|request
operator|.
name|getCourses
argument_list|()
operator|.
name|add
argument_list|(
name|r
argument_list|)
expr_stmt|;
name|helper
operator|.
name|getAction
argument_list|()
operator|.
name|addRequestBuilder
argument_list|()
operator|.
name|setPriority
argument_list|(
name|request
operator|.
name|getCourses
argument_list|()
operator|.
name|size
argument_list|()
argument_list|)
operator|.
name|setAlternative
argument_list|(
literal|false
argument_list|)
operator|.
name|addCourse
argument_list|(
name|toEntity
argument_list|(
name|course
argument_list|,
name|cid
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
for|for
control|(
name|XEInterface
operator|.
name|Group
name|g
range|:
name|group
operator|.
name|groups
control|)
name|fillInRequests
argument_list|(
name|server
argument_list|,
name|helper
argument_list|,
name|request
argument_list|,
name|g
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|CourseRequestInterface
name|getCourseRequests
parameter_list|(
name|OnlineSectioningServer
name|server
parameter_list|,
name|OnlineSectioningHelper
name|helper
parameter_list|,
name|XStudent
name|student
parameter_list|)
throws|throws
name|SectioningException
block|{
name|ClientResource
name|resource
init|=
literal|null
decl_stmt|;
try|try
block|{
name|AcademicSessionInfo
name|session
init|=
name|server
operator|.
name|getAcademicSession
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
if|if
condition|(
name|helper
operator|.
name|isDebugEnabled
argument_list|()
condition|)
name|helper
operator|.
name|debug
argument_list|(
literal|"Retrieving student plan for "
operator|+
name|student
operator|.
name|getName
argument_list|()
operator|+
literal|" (term: "
operator|+
name|term
operator|+
literal|", id:"
operator|+
name|getBannerId
argument_list|(
name|student
argument_list|)
operator|+
literal|")"
argument_list|)
expr_stmt|;
name|resource
operator|=
operator|new
name|ClientResource
argument_list|(
name|iDegreeWorksApiUrl
argument_list|)
expr_stmt|;
name|resource
operator|.
name|setNext
argument_list|(
name|iClient
argument_list|)
expr_stmt|;
name|resource
operator|.
name|addQueryParameter
argument_list|(
literal|"terms"
argument_list|,
name|term
argument_list|)
expr_stmt|;
name|resource
operator|.
name|addQueryParameter
argument_list|(
literal|"studentId"
argument_list|,
name|getBannerId
argument_list|(
name|student
argument_list|)
argument_list|)
expr_stmt|;
name|helper
operator|.
name|getAction
argument_list|()
operator|.
name|addOptionBuilder
argument_list|()
operator|.
name|setKey
argument_list|(
literal|"terms"
argument_list|)
operator|.
name|setValue
argument_list|(
name|term
argument_list|)
expr_stmt|;
name|helper
operator|.
name|getAction
argument_list|()
operator|.
name|addOptionBuilder
argument_list|()
operator|.
name|setKey
argument_list|(
literal|"studentId"
argument_list|)
operator|.
name|setValue
argument_list|(
name|getBannerId
argument_list|(
name|student
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|iDegreeWorksApiEffectiveOnly
operator|!=
literal|null
condition|)
block|{
name|resource
operator|.
name|addQueryParameter
argument_list|(
literal|"effectiveOnly"
argument_list|,
name|iDegreeWorksApiEffectiveOnly
argument_list|)
expr_stmt|;
name|helper
operator|.
name|getAction
argument_list|()
operator|.
name|addOptionBuilder
argument_list|()
operator|.
name|setKey
argument_list|(
literal|"effectiveOnly"
argument_list|)
operator|.
name|setValue
argument_list|(
name|iDegreeWorksApiEffectiveOnly
argument_list|)
expr_stmt|;
block|}
name|resource
operator|.
name|setChallengeResponse
argument_list|(
name|ChallengeScheme
operator|.
name|HTTP_BASIC
argument_list|,
name|iDegreeWorksApiUser
argument_list|,
name|iDegreeWorksApiPassword
argument_list|)
expr_stmt|;
name|Gson
name|gson
init|=
name|getGson
argument_list|(
name|helper
argument_list|)
decl_stmt|;
try|try
block|{
name|resource
operator|.
name|get
argument_list|(
name|MediaType
operator|.
name|APPLICATION_JSON
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|ResourceException
name|exception
parameter_list|)
block|{
try|try
block|{
name|XEInterface
operator|.
name|ErrorResponse
name|response
init|=
name|readResponse
argument_list|(
name|gson
argument_list|,
name|resource
operator|.
name|getResponse
argument_list|()
argument_list|,
name|XEInterface
operator|.
name|ErrorResponse
operator|.
name|class
argument_list|)
decl_stmt|;
name|helper
operator|.
name|getAction
argument_list|()
operator|.
name|addOptionBuilder
argument_list|()
operator|.
name|setKey
argument_list|(
literal|"exception"
argument_list|)
operator|.
name|setValue
argument_list|(
name|gson
operator|.
name|toJson
argument_list|(
name|response
argument_list|)
argument_list|)
expr_stmt|;
name|XEInterface
operator|.
name|Error
name|error
init|=
name|response
operator|.
name|getError
argument_list|()
decl_stmt|;
if|if
condition|(
name|error
operator|!=
literal|null
operator|&&
name|error
operator|.
name|message
operator|!=
literal|null
condition|)
block|{
throw|throw
operator|new
name|SectioningException
argument_list|(
name|error
operator|.
name|message
argument_list|)
throw|;
block|}
if|else if
condition|(
name|error
operator|!=
literal|null
operator|&&
name|error
operator|.
name|description
operator|!=
literal|null
condition|)
block|{
throw|throw
operator|new
name|SectioningException
argument_list|(
name|error
operator|.
name|description
argument_list|)
throw|;
block|}
if|else if
condition|(
name|error
operator|!=
literal|null
operator|&&
name|error
operator|.
name|errorMessage
operator|!=
literal|null
condition|)
block|{
throw|throw
operator|new
name|SectioningException
argument_list|(
name|error
operator|.
name|errorMessage
argument_list|)
throw|;
block|}
else|else
block|{
throw|throw
name|exception
throw|;
block|}
block|}
catch|catch
parameter_list|(
name|SectioningException
name|e
parameter_list|)
block|{
throw|throw
name|e
throw|;
block|}
catch|catch
parameter_list|(
name|Throwable
name|t
parameter_list|)
block|{
throw|throw
name|exception
throw|;
block|}
block|}
name|List
argument_list|<
name|XEInterface
operator|.
name|DegreePlan
argument_list|>
name|current
init|=
name|readResponse
argument_list|(
name|gson
argument_list|,
name|resource
operator|.
name|getResponse
argument_list|()
argument_list|,
name|XEInterface
operator|.
name|DegreePlan
operator|.
name|TYPE_LIST
argument_list|)
decl_stmt|;
name|XEInterface
operator|.
name|DegreePlan
name|plan
init|=
operator|(
name|current
operator|.
name|isEmpty
argument_list|()
condition|?
literal|null
else|:
name|current
operator|.
name|get
argument_list|(
literal|0
argument_list|)
operator|)
decl_stmt|;
if|if
condition|(
name|plan
operator|!=
literal|null
condition|)
block|{
name|helper
operator|.
name|getAction
argument_list|()
operator|.
name|addOptionBuilder
argument_list|()
operator|.
name|setKey
argument_list|(
literal|"response"
argument_list|)
operator|.
name|setValue
argument_list|(
name|gson
operator|.
name|toJson
argument_list|(
name|plan
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|helper
operator|.
name|isDebugEnabled
argument_list|()
condition|)
name|helper
operator|.
name|debug
argument_list|(
literal|"Current degree plan: "
operator|+
name|gson
operator|.
name|toJson
argument_list|(
name|plan
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|plan
operator|==
literal|null
operator|||
name|plan
operator|.
name|years
operator|.
name|isEmpty
argument_list|()
operator|||
name|plan
operator|.
name|years
operator|.
name|get
argument_list|(
literal|0
argument_list|)
operator|.
name|terms
operator|.
name|isEmpty
argument_list|()
operator|||
name|plan
operator|.
name|years
operator|.
name|get
argument_list|(
literal|0
argument_list|)
operator|.
name|terms
operator|.
name|get
argument_list|(
literal|0
argument_list|)
operator|.
name|group
operator|==
literal|null
condition|)
block|{
if|if
condition|(
name|helper
operator|.
name|isDebugEnabled
argument_list|()
condition|)
name|helper
operator|.
name|debug
argument_list|(
literal|"No degree plan has been returned."
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
block|}
name|CourseRequestInterface
name|request
init|=
operator|new
name|CourseRequestInterface
argument_list|()
decl_stmt|;
name|request
operator|.
name|setAcademicSessionId
argument_list|(
name|server
operator|.
name|getAcademicSession
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
name|request
operator|.
name|setStudentId
argument_list|(
name|student
operator|.
name|getStudentId
argument_list|()
argument_list|)
expr_stmt|;
name|fillInRequests
argument_list|(
name|server
argument_list|,
name|helper
argument_list|,
name|request
argument_list|,
name|plan
operator|.
name|years
operator|.
name|get
argument_list|(
literal|0
argument_list|)
operator|.
name|terms
operator|.
name|get
argument_list|(
literal|0
argument_list|)
operator|.
name|group
argument_list|)
expr_stmt|;
return|return
name|request
return|;
block|}
catch|catch
parameter_list|(
name|SectioningException
name|e
parameter_list|)
block|{
name|helper
operator|.
name|info
argument_list|(
literal|"Failed to retrieve degree plan: "
operator|+
name|e
operator|.
name|getMessage
argument_list|()
argument_list|)
expr_stmt|;
throw|throw
name|e
throw|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|helper
operator|.
name|warn
argument_list|(
literal|"Failed to retrieve degree plan: "
operator|+
name|e
operator|.
name|getMessage
argument_list|()
argument_list|,
name|e
argument_list|)
expr_stmt|;
throw|throw
operator|new
name|SectioningException
argument_list|(
name|e
operator|.
name|getMessage
argument_list|()
argument_list|)
throw|;
block|}
finally|finally
block|{
if|if
condition|(
name|resource
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
name|resource
operator|.
name|getResponse
argument_list|()
operator|!=
literal|null
condition|)
name|resource
operator|.
name|getResponse
argument_list|()
operator|.
name|release
argument_list|()
expr_stmt|;
name|resource
operator|.
name|release
argument_list|()
expr_stmt|;
block|}
block|}
block|}
annotation|@
name|Override
specifier|public
name|void
name|dispose
parameter_list|()
block|{
try|try
block|{
name|iClient
operator|.
name|stop
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
name|e
operator|.
name|getMessage
argument_list|()
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

