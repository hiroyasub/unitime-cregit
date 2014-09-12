begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.2 - 3.5 (University Timetabling Application)  * Copyright (C) 2010 - 2013, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
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
name|BufferedReader
import|;
end_import

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
name|InputStreamReader
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|UnsupportedEncodingException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|net
operator|.
name|MalformedURLException
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
name|custom
operator|.
name|CourseDetailsProvider
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
name|CourseUrlProvider
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller, Stephanie Schluttenhofer  */
end_comment

begin_class
specifier|public
class|class
name|PurdueCourseDetailsProvider
implements|implements
name|CourseDetailsProvider
implements|,
name|CourseUrlProvider
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|1L
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
name|Logger
name|sLog
init|=
name|Logger
operator|.
name|getLogger
argument_list|(
name|PurdueCourseDetailsProvider
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|public
specifier|static
name|String
name|sUrl
init|=
literal|"https://selfservice.mypurdue.purdue.edu/prod/bzwsrch.p_catalog_detail?term=:year:term&subject=:subject&cnbr=:courseNbr&enhanced=Y"
decl_stmt|;
specifier|public
specifier|static
name|String
name|sDummyUrl
init|=
literal|"https://selfservice.mypurdue.purdue.edu/prod/bzwsrch.p_catalog_detail?term=201020&subject=AAE&cnbr=20300&enhanced=Y"
decl_stmt|;
specifier|public
specifier|static
name|String
name|sContentRE
init|=
literal|"(<table [ ]*class=\"[a-z]*\" summary=\"This table lists the course detail for the selected term.\" .*)<table [ ]*class=\"[a-z]*\" summary=\"This is table displays line separator at end of the page.\""
decl_stmt|;
specifier|public
specifier|static
name|String
index|[]
index|[]
name|sRemoveRE
init|=
operator|new
name|String
index|[]
index|[]
block|{
block|{
literal|"(?i)<a href=\"[^>]*\">"
block|,
literal|"<b>"
block|}
block|,
block|{
literal|"(?i)</a>"
block|,
literal|"</b>"
block|}
block|,
block|{
literal|"(?i)<span class=[\"]?fieldlabeltext[\"]?>"
block|,
literal|"<b>"
block|}
block|,
block|{
literal|"(?i)</span>"
block|,
literal|"</b>"
block|}
block|,
block|{
literal|"(?i) class=\"nttitle\" "
block|,
literal|" class=\"unitime-MainTableHeader\" "
block|}
block|,
block|{
literal|"(?i) class=\"datadisplaytable\" "
block|,
literal|" class=\"unitime-MainTable\" "
block|}
block|, 	}
decl_stmt|;
specifier|private
name|String
name|getTerm
parameter_list|(
name|AcademicSessionInfo
name|session
parameter_list|)
throws|throws
name|SectioningException
block|{
if|if
condition|(
name|session
operator|.
name|getTerm
argument_list|()
operator|.
name|toLowerCase
argument_list|()
operator|.
name|startsWith
argument_list|(
literal|"spr"
argument_list|)
condition|)
return|return
literal|"20"
return|;
if|if
condition|(
name|session
operator|.
name|getTerm
argument_list|()
operator|.
name|toLowerCase
argument_list|()
operator|.
name|startsWith
argument_list|(
literal|"sum"
argument_list|)
condition|)
return|return
literal|"30"
return|;
if|if
condition|(
name|session
operator|.
name|getTerm
argument_list|()
operator|.
name|toLowerCase
argument_list|()
operator|.
name|startsWith
argument_list|(
literal|"fal"
argument_list|)
condition|)
return|return
literal|"10"
return|;
throw|throw
operator|new
name|SectioningException
argument_list|(
name|MSG
operator|.
name|exceptionCustomCourseDetailsFailed
argument_list|(
literal|"academic term "
operator|+
name|session
operator|.
name|getTerm
argument_list|()
operator|+
literal|" not known"
argument_list|)
argument_list|)
throw|;
block|}
specifier|private
name|String
name|getYear
parameter_list|(
name|AcademicSessionInfo
name|session
parameter_list|)
throws|throws
name|SectioningException
block|{
if|if
condition|(
name|session
operator|.
name|getTerm
argument_list|()
operator|.
name|toLowerCase
argument_list|()
operator|.
name|startsWith
argument_list|(
literal|"fal"
argument_list|)
condition|)
return|return
name|String
operator|.
name|valueOf
argument_list|(
name|Integer
operator|.
name|parseInt
argument_list|(
name|session
operator|.
name|getYear
argument_list|()
argument_list|)
operator|+
literal|1
argument_list|)
return|;
return|return
name|session
operator|.
name|getYear
argument_list|()
return|;
block|}
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
try|try
block|{
if|if
condition|(
name|courseNbr
operator|.
name|length
argument_list|()
operator|>
literal|5
condition|)
name|courseNbr
operator|=
name|courseNbr
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
literal|5
argument_list|)
expr_stmt|;
return|return
operator|new
name|URL
argument_list|(
name|sUrl
operator|.
name|replace
argument_list|(
literal|":year"
argument_list|,
name|getYear
argument_list|(
name|session
argument_list|)
argument_list|)
operator|.
name|replace
argument_list|(
literal|":term"
argument_list|,
name|getTerm
argument_list|(
name|session
argument_list|)
argument_list|)
operator|.
name|replace
argument_list|(
literal|":initiative"
argument_list|,
name|session
operator|.
name|getCampus
argument_list|()
argument_list|)
operator|.
name|replace
argument_list|(
literal|":subject"
argument_list|,
name|URLEncoder
operator|.
name|encode
argument_list|(
name|subject
argument_list|,
literal|"utf-8"
argument_list|)
argument_list|)
operator|.
name|replace
argument_list|(
literal|":courseNbr"
argument_list|,
name|courseNbr
argument_list|)
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|MalformedURLException
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
literal|"course detail url is wrong"
argument_list|)
argument_list|,
name|e
argument_list|)
throw|;
block|}
catch|catch
parameter_list|(
name|UnsupportedEncodingException
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
literal|"course detail url is wrong"
argument_list|)
argument_list|,
name|e
argument_list|)
throw|;
block|}
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
return|return
name|getDetails
argument_list|(
name|getCourseUrl
argument_list|(
name|session
argument_list|,
name|subject
argument_list|,
name|courseNbr
argument_list|)
argument_list|)
return|;
block|}
specifier|protected
name|String
name|getDetails
parameter_list|(
name|URL
name|courseUrl
parameter_list|)
throws|throws
name|SectioningException
block|{
try|try
block|{
name|BufferedReader
name|in
init|=
operator|new
name|BufferedReader
argument_list|(
operator|new
name|InputStreamReader
argument_list|(
name|courseUrl
operator|.
name|openStream
argument_list|()
argument_list|,
literal|"utf-8"
argument_list|)
argument_list|)
decl_stmt|;
name|StringBuffer
name|content
init|=
operator|new
name|StringBuffer
argument_list|()
decl_stmt|;
name|String
name|line
decl_stmt|;
while|while
condition|(
operator|(
name|line
operator|=
name|in
operator|.
name|readLine
argument_list|()
operator|)
operator|!=
literal|null
condition|)
name|content
operator|.
name|append
argument_list|(
name|line
argument_list|)
expr_stmt|;
name|in
operator|.
name|close
argument_list|()
expr_stmt|;
name|Pattern
name|pattern
init|=
name|Pattern
operator|.
name|compile
argument_list|(
name|sContentRE
argument_list|,
name|Pattern
operator|.
name|CASE_INSENSITIVE
operator||
name|Pattern
operator|.
name|MULTILINE
operator||
name|Pattern
operator|.
name|UNIX_LINES
argument_list|)
decl_stmt|;
name|Matcher
name|match
init|=
name|pattern
operator|.
name|matcher
argument_list|(
name|content
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|match
operator|.
name|find
argument_list|()
condition|)
throw|throw
operator|new
name|SectioningException
argument_list|(
name|MSG
operator|.
name|exceptionCustomCourseDetailsFailed
argument_list|(
literal|"unable to parse<a href='"
operator|+
name|courseUrl
operator|+
literal|"'>course detial page</a>"
argument_list|)
argument_list|)
throw|;
name|String
name|table
init|=
name|match
operator|.
name|group
argument_list|(
literal|1
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
name|sRemoveRE
operator|.
name|length
condition|;
name|i
operator|++
control|)
name|table
operator|=
name|table
operator|.
name|replaceAll
argument_list|(
name|sRemoveRE
index|[
name|i
index|]
index|[
literal|0
index|]
argument_list|,
name|sRemoveRE
index|[
name|i
index|]
index|[
literal|1
index|]
argument_list|)
expr_stmt|;
return|return
name|table
return|;
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
name|courseUrl
operator|+
literal|"'>course detail page</a>"
argument_list|)
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
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
operator|new
name|PurdueCourseDetailsProvider
argument_list|()
operator|.
name|getDetails
argument_list|(
operator|new
name|URL
argument_list|(
name|sDummyUrl
argument_list|)
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

