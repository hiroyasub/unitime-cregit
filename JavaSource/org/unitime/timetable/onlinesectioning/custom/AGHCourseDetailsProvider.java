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
name|io
operator|.
name|InputStream
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|InputStreamReader
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|PrintWriter
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|StringWriter
import|;
end_import

begin_import
import|import
name|java
operator|.
name|net
operator|.
name|HttpURLConnection
import|;
end_import

begin_import
import|import
name|java
operator|.
name|net
operator|.
name|URL
import|;
end_import

begin_import
import|import
name|java
operator|.
name|net
operator|.
name|URLEncoder
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
name|HashMap
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
name|apache
operator|.
name|log4j
operator|.
name|BasicConfigurator
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
name|unitime
operator|.
name|commons
operator|.
name|Debug
import|;
end_import

begin_import
import|import
name|org
operator|.
name|unitime
operator|.
name|localization
operator|.
name|impl
operator|.
name|Localization
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
name|resources
operator|.
name|StudentSectioningConstants
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
name|resources
operator|.
name|StudentSectioningMessages
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
name|CurriculumCourseDAO
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
name|JsonArray
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
name|JsonObject
import|;
end_import

begin_import
import|import
name|freemarker
operator|.
name|template
operator|.
name|Configuration
import|;
end_import

begin_import
import|import
name|freemarker
operator|.
name|template
operator|.
name|DefaultObjectWrapperBuilder
import|;
end_import

begin_import
import|import
name|freemarker
operator|.
name|template
operator|.
name|SimpleSequence
import|;
end_import

begin_import
import|import
name|freemarker
operator|.
name|template
operator|.
name|Template
import|;
end_import

begin_import
import|import
name|freemarker
operator|.
name|template
operator|.
name|TemplateException
import|;
end_import

begin_import
import|import
name|freemarker
operator|.
name|template
operator|.
name|TemplateSequenceModel
import|;
end_import

begin_comment
comment|/**  * @author Maciej Zygmunt  */
end_comment

begin_class
specifier|public
class|class
name|AGHCourseDetailsProvider
implements|implements
name|CourseDetailsProvider
implements|,
name|CourseUrlProvider
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
name|AGHCourseDetailsProvider
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|private
specifier|static
name|StudentSectioningMessages
name|MSG
init|=
name|Localization
operator|.
name|create
argument_list|(
name|StudentSectioningMessages
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|private
specifier|static
name|StudentSectioningConstants
name|CONST
init|=
name|Localization
operator|.
name|create
argument_list|(
name|StudentSectioningConstants
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|1L
decl_stmt|;
annotation|@
name|Override
specifier|public
name|URL
name|getCourseUrl
parameter_list|(
name|AcademicSessionInfo
name|session
parameter_list|,
name|String
name|subject
parameter_list|,
name|String
name|courseNbr
parameter_list|)
throws|throws
name|SectioningException
block|{
name|CourseOffering
name|course
init|=
name|CourseOffering
operator|.
name|findBySessionSubjAreaAbbvCourseNbr
argument_list|(
name|session
operator|.
name|getUniqueId
argument_list|()
argument_list|,
name|subject
argument_list|,
name|courseNbr
argument_list|)
decl_stmt|;
if|if
condition|(
name|course
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|SectioningException
argument_list|(
literal|"Course not found: "
operator|+
name|subject
operator|+
literal|" "
operator|+
name|courseNbr
argument_list|)
throw|;
block|}
try|try
block|{
name|String
name|years
init|=
name|findYears
argument_list|(
name|session
argument_list|,
name|subject
argument_list|,
name|courseNbr
argument_list|)
decl_stmt|;
name|String
name|url
init|=
name|ApplicationProperty
operator|.
name|CustomizationDefaultCourseUrl
operator|.
name|value
argument_list|()
decl_stmt|;
if|if
condition|(
name|url
operator|==
literal|null
operator|||
name|url
operator|.
name|isEmpty
argument_list|()
condition|)
return|return
literal|null
return|;
return|return
operator|new
name|URL
argument_list|(
name|url
operator|.
name|replace
argument_list|(
literal|":years"
argument_list|,
name|URLEncoder
operator|.
name|encode
argument_list|(
name|years
argument_list|,
literal|"utf-8"
argument_list|)
argument_list|)
operator|.
name|replace
argument_list|(
literal|":term"
argument_list|,
name|URLEncoder
operator|.
name|encode
argument_list|(
name|session
operator|.
name|getTerm
argument_list|()
argument_list|,
literal|"utf-8"
argument_list|)
argument_list|)
operator|.
name|replace
argument_list|(
literal|":courseNbr"
argument_list|,
name|URLEncoder
operator|.
name|encode
argument_list|(
name|course
operator|.
name|getExternalUniqueId
argument_list|()
argument_list|,
literal|"utf-8"
argument_list|)
argument_list|)
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|SectioningException
argument_list|(
literal|"Failed to get course URL: "
operator|+
name|e
operator|.
name|getMessage
argument_list|()
argument_list|,
name|e
argument_list|)
throw|;
block|}
block|}
specifier|private
name|String
name|findClassificationBySessionSubjAreaAbbvCourseNbr
parameter_list|(
name|Long
name|sessionId
parameter_list|,
name|String
name|subject
parameter_list|,
name|String
name|courseNbr
parameter_list|)
block|{
comment|/* 		select cu from CurriculumCourse as cu 		where cu.course.courseNbr=101 		and cu.course.subjectAreaAbbv='BAND' 		and cu.course.instructionalOffering.session.uniqueId=231379 		*/
return|return
operator|(
name|String
operator|)
name|CurriculumCourseDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|createNewSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select min(cu.classification.academicClassification.code) "
operator|+
literal|"from CurriculumCourse as cu "
operator|+
literal|"where cu.course.subjectAreaAbbv = :subjArea "
operator|+
literal|"and cu.course.courseNbr = :crsNbr "
operator|+
literal|"and cu.course.instructionalOffering.session.uniqueId = :acadSessionId"
argument_list|)
operator|.
name|setString
argument_list|(
literal|"crsNbr"
argument_list|,
name|courseNbr
argument_list|)
operator|.
name|setString
argument_list|(
literal|"subjArea"
argument_list|,
name|subject
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"acadSessionId"
argument_list|,
name|sessionId
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
annotation|@
name|Override
specifier|public
name|String
name|getDetails
parameter_list|(
name|AcademicSessionInfo
name|session
parameter_list|,
name|String
name|subject
parameter_list|,
name|String
name|courseNbr
parameter_list|)
throws|throws
name|SectioningException
block|{
name|CourseOffering
name|course
init|=
name|CourseOffering
operator|.
name|findBySessionSubjAreaAbbvCourseNbr
argument_list|(
name|session
operator|.
name|getUniqueId
argument_list|()
argument_list|,
name|subject
argument_list|,
name|courseNbr
argument_list|)
decl_stmt|;
if|if
condition|(
name|ApplicationProperty
operator|.
name|CustomizationDefaultCourseDetailsDownload
operator|.
name|isTrue
argument_list|()
condition|)
block|{
name|URL
name|url
init|=
name|getApiUrl
argument_list|(
name|session
argument_list|,
name|subject
argument_list|,
name|courseNbr
argument_list|)
decl_stmt|;
name|URL
name|courseUrl
init|=
name|getCourseUrl
argument_list|(
name|session
argument_list|,
name|subject
argument_list|,
name|courseNbr
argument_list|)
decl_stmt|;
if|if
condition|(
name|url
operator|!=
literal|null
condition|)
return|return
name|downloadDetails
argument_list|(
name|url
argument_list|,
name|courseUrl
argument_list|,
name|course
argument_list|)
return|;
block|}
if|if
condition|(
name|course
operator|==
literal|null
condition|)
return|return
name|MSG
operator|.
name|infoCourseDetailsNotAvailable
argument_list|(
name|subject
argument_list|,
name|courseNbr
argument_list|)
return|;
try|try
block|{
name|Configuration
name|cfg
init|=
operator|new
name|Configuration
argument_list|(
name|Configuration
operator|.
name|VERSION_2_3_0
argument_list|)
decl_stmt|;
name|cfg
operator|.
name|setClassForTemplateLoading
argument_list|(
name|AGHCourseDetailsProvider
operator|.
name|class
argument_list|,
literal|""
argument_list|)
expr_stmt|;
name|cfg
operator|.
name|setLocale
argument_list|(
name|Localization
operator|.
name|getJavaLocale
argument_list|()
argument_list|)
expr_stmt|;
name|cfg
operator|.
name|setOutputEncoding
argument_list|(
literal|"utf-8"
argument_list|)
expr_stmt|;
name|Template
name|template
init|=
name|cfg
operator|.
name|getTemplate
argument_list|(
literal|"details.ftl"
argument_list|)
decl_stmt|;
name|Map
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|input
init|=
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
argument_list|()
decl_stmt|;
name|input
operator|.
name|put
argument_list|(
literal|"msg"
argument_list|,
name|MSG
argument_list|)
expr_stmt|;
name|input
operator|.
name|put
argument_list|(
literal|"const"
argument_list|,
name|CONST
argument_list|)
expr_stmt|;
name|input
operator|.
name|put
argument_list|(
literal|"session"
argument_list|,
name|session
argument_list|)
expr_stmt|;
name|input
operator|.
name|put
argument_list|(
literal|"course"
argument_list|,
name|course
argument_list|)
expr_stmt|;
name|StringWriter
name|s
init|=
operator|new
name|StringWriter
argument_list|()
decl_stmt|;
name|template
operator|.
name|process
argument_list|(
name|input
argument_list|,
operator|new
name|PrintWriter
argument_list|(
name|s
argument_list|)
argument_list|)
expr_stmt|;
name|s
operator|.
name|flush
argument_list|()
expr_stmt|;
name|s
operator|.
name|close
argument_list|()
expr_stmt|;
return|return
name|s
operator|.
name|toString
argument_list|()
return|;
block|}
catch|catch
parameter_list|(
name|TemplateException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|SectioningException
argument_list|(
name|MSG
operator|.
name|failedLoadCourseDetails
argument_list|(
name|e
operator|.
name|getMessage
argument_list|()
argument_list|)
argument_list|)
throw|;
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|SectioningException
argument_list|(
name|MSG
operator|.
name|failedLoadCourseDetails
argument_list|(
name|e
operator|.
name|getMessage
argument_list|()
argument_list|)
argument_list|)
throw|;
block|}
block|}
specifier|private
name|URL
name|getApiUrl
parameter_list|(
name|AcademicSessionInfo
name|session
parameter_list|,
name|String
name|subject
parameter_list|,
name|String
name|courseNbr
parameter_list|)
block|{
name|CourseOffering
name|course
init|=
name|CourseOffering
operator|.
name|findBySessionSubjAreaAbbvCourseNbr
argument_list|(
name|session
operator|.
name|getUniqueId
argument_list|()
argument_list|,
name|subject
argument_list|,
name|courseNbr
argument_list|)
decl_stmt|;
if|if
condition|(
name|course
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|SectioningException
argument_list|(
literal|"Course not found: "
operator|+
name|subject
operator|+
literal|" "
operator|+
name|courseNbr
argument_list|)
throw|;
block|}
try|try
block|{
name|String
name|url
init|=
name|ApplicationProperties
operator|.
name|getConfigProperties
argument_list|()
operator|.
name|getProperty
argument_list|(
literal|"unitime.custom.default.course_api_url"
argument_list|,
literal|"http://syllabuskrk.agh.edu.pl/api/:years/modules/:courseNbr"
argument_list|)
decl_stmt|;
if|if
condition|(
name|url
operator|==
literal|null
operator|||
name|url
operator|.
name|isEmpty
argument_list|()
condition|)
return|return
literal|null
return|;
name|String
name|years
init|=
name|findYears
argument_list|(
name|session
argument_list|,
name|subject
argument_list|,
name|courseNbr
argument_list|)
decl_stmt|;
return|return
operator|new
name|URL
argument_list|(
name|url
operator|.
name|replace
argument_list|(
literal|":years"
argument_list|,
name|URLEncoder
operator|.
name|encode
argument_list|(
name|years
argument_list|,
literal|"utf-8"
argument_list|)
argument_list|)
operator|.
name|replace
argument_list|(
literal|":term"
argument_list|,
name|URLEncoder
operator|.
name|encode
argument_list|(
name|session
operator|.
name|getTerm
argument_list|()
argument_list|,
literal|"utf-8"
argument_list|)
argument_list|)
operator|.
name|replace
argument_list|(
literal|":courseNbr"
argument_list|,
name|URLEncoder
operator|.
name|encode
argument_list|(
name|course
operator|.
name|getExternalUniqueId
argument_list|()
argument_list|,
literal|"utf-8"
argument_list|)
argument_list|)
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|SectioningException
argument_list|(
literal|"Failed to get course URL: "
operator|+
name|e
operator|.
name|getMessage
argument_list|()
argument_list|,
name|e
argument_list|)
throw|;
block|}
block|}
specifier|private
name|String
name|findYears
parameter_list|(
name|AcademicSessionInfo
name|session
parameter_list|,
name|String
name|subject
parameter_list|,
name|String
name|courseNbr
parameter_list|)
block|{
name|String
name|classificationSt
init|=
name|findClassificationBySessionSubjAreaAbbvCourseNbr
argument_list|(
name|session
operator|.
name|getUniqueId
argument_list|()
argument_list|,
name|subject
argument_list|,
name|courseNbr
argument_list|)
decl_stmt|;
name|Integer
name|classification
init|=
operator|new
name|Integer
argument_list|(
name|classificationSt
operator|.
name|substring
argument_list|(
name|classificationSt
operator|.
name|length
argument_list|()
operator|-
literal|1
argument_list|)
argument_list|)
decl_stmt|;
name|String
name|years
init|=
name|syllabusLink
argument_list|(
operator|new
name|Integer
argument_list|(
name|session
operator|.
name|getYear
argument_list|()
argument_list|)
argument_list|,
name|session
operator|.
name|getTerm
argument_list|()
argument_list|,
name|classification
argument_list|)
decl_stmt|;
return|return
name|years
return|;
block|}
specifier|public
name|String
name|syllabusLink
parameter_list|(
name|Integer
name|year
parameter_list|,
name|String
name|term
parameter_list|,
name|Integer
name|classification
parameter_list|)
block|{
name|Integer
name|yearShift
init|=
literal|0
decl_stmt|,
name|yearLink
decl_stmt|,
name|academicShift
init|=
literal|0
decl_stmt|;
name|Boolean
name|winterApplication
init|=
literal|false
decl_stmt|;
name|String
name|syllabusLinkRw
init|=
literal|"current_annual"
decl_stmt|;
comment|// newest syllabus
if|if
condition|(
name|term
operator|.
name|toLowerCase
argument_list|()
operator|.
name|endsWith
argument_list|(
literal|"zimowy"
argument_list|)
condition|)
block|{
if|if
condition|(
operator|(
name|classification
operator|%
literal|2
operator|)
operator|==
literal|0
condition|)
block|{
comment|// even
name|winterApplication
operator|=
literal|true
expr_stmt|;
block|}
else|else
block|{
name|winterApplication
operator|=
literal|false
expr_stmt|;
block|}
block|}
else|else
block|{
if|if
condition|(
operator|(
name|classification
operator|%
literal|2
operator|)
operator|==
literal|1
condition|)
block|{
comment|// odd
name|winterApplication
operator|=
literal|true
expr_stmt|;
block|}
else|else
block|{
name|winterApplication
operator|=
literal|false
expr_stmt|;
block|}
block|}
if|if
condition|(
name|winterApplication
condition|)
block|{
name|academicShift
operator|=
literal|1
expr_stmt|;
block|}
else|else
block|{
name|academicShift
operator|=
literal|0
expr_stmt|;
block|}
comment|// div 2 (two semesters per year) add shift for winter applications
name|yearShift
operator|=
operator|(
name|classification
operator|+
name|academicShift
operator|)
operator|/
literal|2
expr_stmt|;
comment|// current year minus shift
name|yearLink
operator|=
name|year
operator|-
name|yearShift
expr_stmt|;
comment|// link = beginning + next year
name|syllabusLinkRw
operator|=
name|yearLink
operator|.
name|toString
argument_list|()
operator|+
literal|"-"
operator|+
operator|new
name|Integer
argument_list|(
name|yearLink
operator|+
literal|1
argument_list|)
operator|.
name|toString
argument_list|()
expr_stmt|;
return|return
name|syllabusLinkRw
return|;
block|}
specifier|private
name|String
name|downloadDetails
parameter_list|(
name|URL
name|url
parameter_list|,
name|URL
name|courseUrl
parameter_list|,
name|CourseOffering
name|utCourse
parameter_list|)
throws|throws
name|SectioningException
block|{
try|try
block|{
name|HttpURLConnection
name|con
init|=
operator|(
name|HttpURLConnection
operator|)
name|url
operator|.
name|openConnection
argument_list|()
decl_stmt|;
name|con
operator|.
name|setRequestProperty
argument_list|(
literal|"Accept"
argument_list|,
literal|"application/json"
argument_list|)
expr_stmt|;
name|Gson
name|gson
init|=
operator|new
name|Gson
argument_list|()
decl_stmt|;
name|InputStream
name|is
init|=
name|con
operator|.
name|getInputStream
argument_list|()
decl_stmt|;
name|InputStreamReader
name|reader
init|=
operator|new
name|InputStreamReader
argument_list|(
name|is
argument_list|)
decl_stmt|;
name|JsonObject
name|syllabusCourse
init|=
name|gson
operator|.
name|fromJson
argument_list|(
name|reader
argument_list|,
name|JsonObject
operator|.
name|class
argument_list|)
decl_stmt|;
name|JsonObject
name|owner
init|=
name|syllabusCourse
operator|.
name|get
argument_list|(
literal|"owner"
argument_list|)
operator|.
name|getAsJsonObject
argument_list|()
decl_stmt|;
name|JsonArray
name|modules
init|=
name|syllabusCourse
operator|.
name|get
argument_list|(
literal|"module_activities"
argument_list|)
operator|.
name|getAsJsonArray
argument_list|()
decl_stmt|;
name|JsonArray
name|instructors
init|=
name|syllabusCourse
operator|.
name|get
argument_list|(
literal|"lecturers"
argument_list|)
operator|.
name|getAsJsonArray
argument_list|()
decl_stmt|;
name|ArrayList
argument_list|<
name|JsonObject
argument_list|>
name|cList
init|=
operator|new
name|ArrayList
argument_list|<
name|JsonObject
argument_list|>
argument_list|()
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
name|modules
operator|.
name|size
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|cList
operator|.
name|add
argument_list|(
name|modules
operator|.
name|get
argument_list|(
name|i
argument_list|)
operator|.
name|getAsJsonObject
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|ArrayList
argument_list|<
name|JsonObject
argument_list|>
name|iList
init|=
operator|new
name|ArrayList
argument_list|<
name|JsonObject
argument_list|>
argument_list|()
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
name|instructors
operator|.
name|size
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|iList
operator|.
name|add
argument_list|(
name|instructors
operator|.
name|get
argument_list|(
name|i
argument_list|)
operator|.
name|getAsJsonObject
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|JsonArray
name|study_plans
init|=
name|syllabusCourse
operator|.
name|get
argument_list|(
literal|"study_plans"
argument_list|)
operator|.
name|getAsJsonArray
argument_list|()
decl_stmt|;
name|ArrayList
argument_list|<
name|JsonObject
argument_list|>
name|pList
init|=
operator|new
name|ArrayList
argument_list|<
name|JsonObject
argument_list|>
argument_list|()
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
name|study_plans
operator|.
name|size
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|pList
operator|.
name|add
argument_list|(
name|study_plans
operator|.
name|get
argument_list|(
name|i
argument_list|)
operator|.
name|getAsJsonObject
argument_list|()
argument_list|)
expr_stmt|;
block|}
try|try
block|{
name|DefaultObjectWrapperBuilder
name|builder
init|=
operator|new
name|DefaultObjectWrapperBuilder
argument_list|(
name|Configuration
operator|.
name|VERSION_2_3_23
argument_list|)
decl_stmt|;
name|TemplateSequenceModel
name|classList
init|=
operator|new
name|SimpleSequence
argument_list|(
name|cList
argument_list|,
name|builder
operator|.
name|build
argument_list|()
argument_list|)
decl_stmt|;
name|TemplateSequenceModel
name|instructorsList
init|=
operator|new
name|SimpleSequence
argument_list|(
name|iList
argument_list|,
name|builder
operator|.
name|build
argument_list|()
argument_list|)
decl_stmt|;
name|TemplateSequenceModel
name|plansList
init|=
operator|new
name|SimpleSequence
argument_list|(
name|pList
argument_list|,
name|builder
operator|.
name|build
argument_list|()
argument_list|)
decl_stmt|;
name|Configuration
name|cfg
init|=
operator|new
name|Configuration
argument_list|(
name|Configuration
operator|.
name|VERSION_2_3_0
argument_list|)
decl_stmt|;
name|cfg
operator|.
name|setClassForTemplateLoading
argument_list|(
name|AGHCourseDetailsProvider
operator|.
name|class
argument_list|,
literal|""
argument_list|)
expr_stmt|;
name|cfg
operator|.
name|setLocale
argument_list|(
name|Localization
operator|.
name|getJavaLocale
argument_list|()
argument_list|)
expr_stmt|;
name|cfg
operator|.
name|setOutputEncoding
argument_list|(
literal|"utf-8"
argument_list|)
expr_stmt|;
name|Template
name|template
init|=
name|cfg
operator|.
name|getTemplate
argument_list|(
literal|"agh_details.ftl"
argument_list|)
decl_stmt|;
name|Map
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|input
init|=
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
argument_list|()
decl_stmt|;
name|input
operator|.
name|put
argument_list|(
literal|"msg"
argument_list|,
name|MSG
argument_list|)
expr_stmt|;
name|input
operator|.
name|put
argument_list|(
literal|"const"
argument_list|,
name|CONST
argument_list|)
expr_stmt|;
name|input
operator|.
name|put
argument_list|(
literal|"url"
argument_list|,
name|courseUrl
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
name|input
operator|.
name|put
argument_list|(
literal|"syllabusCourse"
argument_list|,
name|syllabusCourse
argument_list|)
expr_stmt|;
name|input
operator|.
name|put
argument_list|(
literal|"utCourse"
argument_list|,
name|utCourse
argument_list|)
expr_stmt|;
name|input
operator|.
name|put
argument_list|(
literal|"owner"
argument_list|,
name|owner
argument_list|)
expr_stmt|;
name|input
operator|.
name|put
argument_list|(
literal|"classList"
argument_list|,
name|classList
argument_list|)
expr_stmt|;
name|input
operator|.
name|put
argument_list|(
literal|"instructorsList"
argument_list|,
name|instructorsList
argument_list|)
expr_stmt|;
name|input
operator|.
name|put
argument_list|(
literal|"plansList"
argument_list|,
name|plansList
argument_list|)
expr_stmt|;
name|StringWriter
name|s
init|=
operator|new
name|StringWriter
argument_list|()
decl_stmt|;
name|template
operator|.
name|process
argument_list|(
name|input
argument_list|,
operator|new
name|PrintWriter
argument_list|(
name|s
argument_list|)
argument_list|)
expr_stmt|;
name|s
operator|.
name|flush
argument_list|()
expr_stmt|;
name|s
operator|.
name|close
argument_list|()
expr_stmt|;
return|return
name|s
operator|.
name|toString
argument_list|()
return|;
block|}
catch|catch
parameter_list|(
name|TemplateException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|SectioningException
argument_list|(
name|MSG
operator|.
name|failedLoadCourseDetails
argument_list|(
name|e
operator|.
name|getMessage
argument_list|()
argument_list|)
argument_list|)
throw|;
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|SectioningException
argument_list|(
name|MSG
operator|.
name|failedLoadCourseDetails
argument_list|(
name|e
operator|.
name|getMessage
argument_list|()
argument_list|)
argument_list|)
throw|;
block|}
block|}
catch|catch
parameter_list|(
name|IOException
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
throw|throw
operator|new
name|SectioningException
argument_list|(
name|MSG
operator|.
name|exceptionCustomCourseDetailsFailed
argument_list|(
literal|"unable to read<a href='"
operator|+
name|url
operator|+
literal|"'>course detail page</a>"
argument_list|)
argument_list|,
name|e
argument_list|)
throw|;
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
throw|throw
operator|new
name|SectioningException
argument_list|(
name|MSG
operator|.
name|exceptionCustomCourseDetailsFailed
argument_list|(
literal|"unable to read<a href='"
operator|+
name|url
operator|+
literal|"'>course detail page</a>"
argument_list|)
argument_list|,
name|e
argument_list|)
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
name|BasicConfigurator
operator|.
name|configure
argument_list|()
expr_stmt|;
name|Debug
operator|.
name|info
argument_list|(
literal|" - Initializing Hibernate ... "
argument_list|)
expr_stmt|;
name|_RootDAO
operator|.
name|initialize
argument_list|()
expr_stmt|;
name|ApplicationProperties
operator|.
name|getConfigProperties
argument_list|()
operator|.
name|setProperty
argument_list|(
name|ApplicationProperty
operator|.
name|CustomizationDefaultCourseUrl
operator|.
name|key
argument_list|()
argument_list|,
literal|"http://syllabuskrk.agh.edu.pl/:years/pl/magnesite/modules/:courseNbr"
argument_list|)
expr_stmt|;
name|ApplicationProperties
operator|.
name|getConfigProperties
argument_list|()
operator|.
name|setProperty
argument_list|(
literal|"unitime.custom.default.course_api_url"
argument_list|,
literal|"http://syllabuskrk.agh.edu.pl/api/:years/modules/:courseNbr"
argument_list|)
expr_stmt|;
name|ApplicationProperties
operator|.
name|getDefaultProperties
argument_list|()
operator|.
name|setProperty
argument_list|(
name|ApplicationProperty
operator|.
name|CustomizationDefaultCourseDetailsDownload
operator|.
name|key
argument_list|()
argument_list|,
literal|"true"
argument_list|)
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"URL:"
operator|+
operator|new
name|AGHCourseDetailsProvider
argument_list|()
operator|.
name|getCourseUrl
argument_list|(
operator|new
name|AcademicSessionInfo
argument_list|(
literal|231379l
argument_list|,
literal|"2015"
argument_list|,
literal|"Semestr zimowy"
argument_list|,
literal|"AGH"
argument_list|)
argument_list|,
literal|"BAND"
argument_list|,
literal|"101"
argument_list|)
argument_list|)
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"Details:\n"
operator|+
operator|new
name|AGHCourseDetailsProvider
argument_list|()
operator|.
name|getDetails
argument_list|(
operator|new
name|AcademicSessionInfo
argument_list|(
literal|231379l
argument_list|,
literal|"2015"
argument_list|,
literal|"Semestr zimowy"
argument_list|,
literal|"AGH"
argument_list|)
argument_list|,
literal|"BAND"
argument_list|,
literal|"101"
argument_list|)
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
