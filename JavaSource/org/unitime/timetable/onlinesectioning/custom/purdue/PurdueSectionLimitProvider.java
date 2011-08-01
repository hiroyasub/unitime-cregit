begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.2 (University Timetabling Application)  * Copyright (C) 2010, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
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
name|net
operator|.
name|sf
operator|.
name|cpsolver
operator|.
name|studentsct
operator|.
name|model
operator|.
name|Section
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
name|StudentSectioningExceptions
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
name|SectionLimitProvider
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
name|SectionUrlProvider
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|PurdueSectionLimitProvider
implements|implements
name|SectionLimitProvider
implements|,
name|SectionUrlProvider
block|{
specifier|private
specifier|static
name|StudentSectioningExceptions
name|EXCEPTIONS
init|=
name|Localization
operator|.
name|create
argument_list|(
name|StudentSectioningExceptions
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
name|PurdueSectionLimitProvider
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|public
specifier|static
name|String
name|sUrl
init|=
literal|"https://esa-oas-prod-wl.itap.purdue.edu/prod/bzwsrch.p_schedule_detail?term=:year:term&crn=:crn"
decl_stmt|;
specifier|public
specifier|static
name|String
name|sDummyUrl
init|=
literal|"https://esa-oas-prod-wl.itap.purdue.edu/prod/bzwsrch.p_schedule_detail?term=201010&crn=10001"
decl_stmt|;
specifier|public
specifier|static
name|String
name|sContentRE
init|=
literal|"(<table [ ]*class=\"[a-z]*\" summary=\"This layout table is used to present the seating numbers.\" .*</table>)"
decl_stmt|;
specifier|public
specifier|static
name|String
name|sTableRE
init|=
literal|"<td class=\"dddefault\">(\\-?[0-9]*)</td>"
decl_stmt|;
specifier|private
name|Pattern
name|iContentRE
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
specifier|private
name|Pattern
name|iTableRE
init|=
name|Pattern
operator|.
name|compile
argument_list|(
name|sTableRE
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
specifier|public
specifier|static
name|int
name|sConcurrencyLimit
init|=
literal|10
decl_stmt|;
specifier|private
name|Hashtable
argument_list|<
name|Long
argument_list|,
name|int
index|[]
argument_list|>
name|iCache
init|=
operator|new
name|Hashtable
argument_list|<
name|Long
argument_list|,
name|int
index|[]
argument_list|>
argument_list|()
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
name|EXCEPTIONS
operator|.
name|customSectionLimitsFailed
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
name|getSectionUrl
parameter_list|(
name|AcademicSessionInfo
name|session
parameter_list|,
name|Long
name|courseId
parameter_list|,
name|Section
name|section
parameter_list|)
block|{
return|return
name|getSectionUrl
argument_list|(
name|session
argument_list|,
name|courseId
argument_list|,
name|section
operator|.
name|getId
argument_list|()
argument_list|,
name|section
operator|.
name|getName
argument_list|(
name|courseId
argument_list|)
argument_list|)
return|;
block|}
specifier|protected
name|URL
name|getSectionUrl
parameter_list|(
name|AcademicSessionInfo
name|session
parameter_list|,
name|Long
name|courseId
parameter_list|,
name|Long
name|classId
parameter_list|,
name|String
name|className
parameter_list|)
block|{
try|try
block|{
if|if
condition|(
name|className
operator|==
literal|null
operator|||
name|className
operator|.
name|isEmpty
argument_list|()
condition|)
throw|throw
operator|new
name|SectioningException
argument_list|(
name|EXCEPTIONS
operator|.
name|customSectionLimitsFailed
argument_list|(
literal|"class CRN not provided"
argument_list|)
argument_list|)
throw|;
name|String
name|crn
init|=
name|className
decl_stmt|;
if|if
condition|(
name|className
operator|.
name|indexOf
argument_list|(
literal|'-'
argument_list|)
operator|>=
literal|0
condition|)
name|crn
operator|=
name|className
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|className
operator|.
name|indexOf
argument_list|(
literal|'-'
argument_list|)
argument_list|)
expr_stmt|;
name|URL
name|url
init|=
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
literal|":crn"
argument_list|,
name|crn
argument_list|)
argument_list|)
decl_stmt|;
return|return
name|url
return|;
block|}
catch|catch
parameter_list|(
name|MalformedURLException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|SectioningException
argument_list|(
name|EXCEPTIONS
operator|.
name|customSectionLimitsFailed
argument_list|(
literal|"course detail url is wrong"
argument_list|)
argument_list|)
throw|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|int
index|[]
name|getSectionLimit
parameter_list|(
name|AcademicSessionInfo
name|session
parameter_list|,
name|Long
name|courseId
parameter_list|,
name|Section
name|section
parameter_list|)
throws|throws
name|SectioningException
block|{
return|return
name|getSectionLimit
argument_list|(
name|session
argument_list|,
name|courseId
argument_list|,
name|section
operator|.
name|getId
argument_list|()
argument_list|,
name|section
operator|.
name|getName
argument_list|(
name|courseId
argument_list|)
argument_list|)
return|;
block|}
specifier|protected
name|int
index|[]
name|getSectionLimit
parameter_list|(
name|AcademicSessionInfo
name|session
parameter_list|,
name|Long
name|courseId
parameter_list|,
name|Long
name|classId
parameter_list|,
name|String
name|className
parameter_list|)
throws|throws
name|SectioningException
block|{
name|int
index|[]
name|ret
init|=
name|getSectionLimit
argument_list|(
name|getSectionUrl
argument_list|(
name|session
argument_list|,
name|courseId
argument_list|,
name|classId
argument_list|,
name|className
argument_list|)
argument_list|)
decl_stmt|;
name|iCache
operator|.
name|put
argument_list|(
name|classId
argument_list|,
name|ret
argument_list|)
expr_stmt|;
return|return
name|ret
return|;
block|}
specifier|protected
name|int
index|[]
name|getSectionLimit
parameter_list|(
name|URL
name|secionUrl
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
name|secionUrl
operator|.
name|openStream
argument_list|()
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
name|Matcher
name|match
init|=
name|iContentRE
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
name|EXCEPTIONS
operator|.
name|customSectionLimitsFailed
argument_list|(
literal|"unable to parse<a href='"
operator|+
name|secionUrl
operator|+
literal|"'>class detial page</a>"
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
name|match
operator|=
name|iTableRE
operator|.
name|matcher
argument_list|(
name|table
argument_list|)
expr_stmt|;
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
name|EXCEPTIONS
operator|.
name|customSectionLimitsFailed
argument_list|(
literal|"unable to parse<a href='"
operator|+
name|secionUrl
operator|+
literal|"'>class detial page</a>"
argument_list|)
argument_list|)
throw|;
name|int
name|capacity
init|=
name|Integer
operator|.
name|parseInt
argument_list|(
name|match
operator|.
name|group
argument_list|(
literal|1
argument_list|)
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
name|EXCEPTIONS
operator|.
name|customSectionLimitsFailed
argument_list|(
literal|"unable to parse<a href='"
operator|+
name|secionUrl
operator|+
literal|"'>class detial page</a>"
argument_list|)
argument_list|)
throw|;
name|int
name|actual
init|=
name|Integer
operator|.
name|parseInt
argument_list|(
name|match
operator|.
name|group
argument_list|(
literal|1
argument_list|)
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
name|EXCEPTIONS
operator|.
name|customSectionLimitsFailed
argument_list|(
literal|"unable to parse<a href='"
operator|+
name|secionUrl
operator|+
literal|"'>class detial page</a>"
argument_list|)
argument_list|)
throw|;
comment|//			int remaning = Integer.parseInt(match.group(1));
return|return
operator|new
name|int
index|[]
block|{
name|actual
block|,
name|capacity
block|}
return|;
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
name|EXCEPTIONS
operator|.
name|customSectionLimitsFailed
argument_list|(
literal|"unable to read<a href='"
operator|+
name|secionUrl
operator|+
literal|"'>class detial page</a>"
argument_list|)
argument_list|)
throw|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|Map
argument_list|<
name|Long
argument_list|,
name|int
index|[]
argument_list|>
name|getSectionLimits
parameter_list|(
name|AcademicSessionInfo
name|session
parameter_list|,
name|Long
name|courseId
parameter_list|,
name|Collection
argument_list|<
name|Section
argument_list|>
name|sections
parameter_list|)
block|{
name|Hashtable
argument_list|<
name|Long
argument_list|,
name|int
index|[]
argument_list|>
name|ret
init|=
operator|new
name|Hashtable
argument_list|<
name|Long
argument_list|,
name|int
index|[]
argument_list|>
argument_list|()
decl_stmt|;
name|ThreadPool
name|pool
init|=
operator|new
name|ThreadPool
argument_list|()
decl_stmt|;
for|for
control|(
name|Section
name|section
range|:
name|sections
control|)
block|{
name|pool
operator|.
name|retrieveLimit
argument_list|(
name|session
argument_list|,
name|courseId
argument_list|,
name|section
operator|.
name|getId
argument_list|()
argument_list|,
name|section
operator|.
name|getName
argument_list|(
name|courseId
argument_list|)
argument_list|,
name|ret
argument_list|)
expr_stmt|;
block|}
name|pool
operator|.
name|waitForAll
argument_list|()
expr_stmt|;
return|return
name|ret
return|;
block|}
annotation|@
name|Override
specifier|public
name|Map
argument_list|<
name|Long
argument_list|,
name|int
index|[]
argument_list|>
name|getSectionLimitsFromCache
parameter_list|(
name|AcademicSessionInfo
name|session
parameter_list|,
name|Long
name|courseId
parameter_list|,
name|Collection
argument_list|<
name|Section
argument_list|>
name|sections
parameter_list|)
block|{
name|Hashtable
argument_list|<
name|Long
argument_list|,
name|int
index|[]
argument_list|>
name|ret
init|=
operator|new
name|Hashtable
argument_list|<
name|Long
argument_list|,
name|int
index|[]
argument_list|>
argument_list|()
decl_stmt|;
name|ThreadPool
name|pool
init|=
operator|new
name|ThreadPool
argument_list|()
decl_stmt|;
for|for
control|(
name|Section
name|section
range|:
name|sections
control|)
block|{
name|int
index|[]
name|limits
init|=
name|iCache
operator|.
name|get
argument_list|(
name|section
operator|.
name|getId
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|limits
operator|!=
literal|null
condition|)
block|{
name|ret
operator|.
name|put
argument_list|(
name|section
operator|.
name|getId
argument_list|()
argument_list|,
name|limits
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|pool
operator|.
name|retrieveLimit
argument_list|(
name|session
argument_list|,
name|courseId
argument_list|,
name|section
operator|.
name|getId
argument_list|()
argument_list|,
name|section
operator|.
name|getName
argument_list|(
name|courseId
argument_list|)
argument_list|,
name|ret
argument_list|)
expr_stmt|;
block|}
block|}
name|pool
operator|.
name|waitForAll
argument_list|()
expr_stmt|;
return|return
name|ret
return|;
block|}
specifier|private
class|class
name|ThreadPool
block|{
specifier|private
name|Set
argument_list|<
name|Worker
argument_list|>
name|iWorkers
init|=
operator|new
name|HashSet
argument_list|<
name|Worker
argument_list|>
argument_list|()
decl_stmt|;
specifier|private
name|void
name|retrieveLimit
parameter_list|(
name|AcademicSessionInfo
name|session
parameter_list|,
name|Long
name|courseId
parameter_list|,
name|Long
name|classId
parameter_list|,
name|String
name|customClassSuffix
parameter_list|,
name|Hashtable
argument_list|<
name|Long
argument_list|,
name|int
index|[]
argument_list|>
name|ret
parameter_list|)
block|{
synchronized|synchronized
init|(
name|iWorkers
init|)
block|{
while|while
condition|(
name|iWorkers
operator|.
name|size
argument_list|()
operator|>
name|sConcurrencyLimit
condition|)
block|{
try|try
block|{
name|iWorkers
operator|.
name|wait
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|InterruptedException
name|e
parameter_list|)
block|{
block|}
block|}
name|Worker
name|w
init|=
operator|new
name|Worker
argument_list|(
name|session
argument_list|,
name|courseId
argument_list|,
name|classId
argument_list|,
name|customClassSuffix
argument_list|,
name|ret
argument_list|)
decl_stmt|;
name|iWorkers
operator|.
name|add
argument_list|(
name|w
argument_list|)
expr_stmt|;
name|w
operator|.
name|start
argument_list|()
expr_stmt|;
block|}
block|}
specifier|private
name|void
name|done
parameter_list|(
name|Worker
name|w
parameter_list|)
block|{
synchronized|synchronized
init|(
name|iWorkers
init|)
block|{
name|iWorkers
operator|.
name|remove
argument_list|(
name|w
argument_list|)
expr_stmt|;
name|iWorkers
operator|.
name|notify
argument_list|()
expr_stmt|;
block|}
block|}
specifier|private
name|void
name|waitForAll
parameter_list|()
block|{
synchronized|synchronized
init|(
name|iWorkers
init|)
block|{
while|while
condition|(
operator|!
name|iWorkers
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
try|try
block|{
name|iWorkers
operator|.
name|wait
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|InterruptedException
name|e
parameter_list|)
block|{
block|}
block|}
block|}
block|}
specifier|private
class|class
name|Worker
extends|extends
name|Thread
block|{
specifier|private
name|AcademicSessionInfo
name|iSession
decl_stmt|;
specifier|private
name|Long
name|iCourseId
decl_stmt|,
name|iClassId
decl_stmt|;
specifier|private
name|String
name|iClassName
decl_stmt|;
specifier|private
name|Hashtable
argument_list|<
name|Long
argument_list|,
name|int
index|[]
argument_list|>
name|iResults
decl_stmt|;
specifier|private
name|Worker
parameter_list|(
name|AcademicSessionInfo
name|session
parameter_list|,
name|Long
name|courseId
parameter_list|,
name|Long
name|classId
parameter_list|,
name|String
name|className
parameter_list|,
name|Hashtable
argument_list|<
name|Long
argument_list|,
name|int
index|[]
argument_list|>
name|ret
parameter_list|)
block|{
name|iSession
operator|=
name|session
expr_stmt|;
name|iCourseId
operator|=
name|courseId
expr_stmt|;
name|iClassId
operator|=
name|classId
expr_stmt|;
name|iClassName
operator|=
name|className
expr_stmt|;
name|iResults
operator|=
name|ret
expr_stmt|;
name|setName
argument_list|(
literal|"PuSectLimitP-"
operator|+
name|classId
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|run
parameter_list|()
block|{
try|try
block|{
name|int
index|[]
name|limit
init|=
name|getSectionLimit
argument_list|(
name|iSession
argument_list|,
name|iCourseId
argument_list|,
name|iClassId
argument_list|,
name|iClassName
argument_list|)
decl_stmt|;
name|iResults
operator|.
name|put
argument_list|(
name|iClassId
argument_list|,
name|limit
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|SectioningException
name|e
parameter_list|)
block|{
name|sLog
operator|.
name|warn
argument_list|(
literal|"Failed to retrieve section limit for "
operator|+
name|iClassName
operator|+
literal|" ("
operator|+
name|iSession
operator|.
name|getTerm
argument_list|()
operator|+
literal|" "
operator|+
name|iSession
operator|.
name|getYear
argument_list|()
operator|+
literal|"): "
operator|+
name|e
operator|.
name|getMessage
argument_list|()
argument_list|)
expr_stmt|;
block|}
finally|finally
block|{
name|done
argument_list|(
name|this
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
block|}
end_class

end_unit

