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
name|action
operator|.
name|ajax
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
name|PrintWriter
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
name|javax
operator|.
name|servlet
operator|.
name|http
operator|.
name|HttpServletRequest
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|servlet
operator|.
name|http
operator|.
name|HttpServletResponse
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|struts
operator|.
name|action
operator|.
name|Action
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|struts
operator|.
name|action
operator|.
name|ActionForm
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|struts
operator|.
name|action
operator|.
name|ActionForward
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|struts
operator|.
name|action
operator|.
name|ActionMapping
import|;
end_import

begin_import
import|import
name|org
operator|.
name|springframework
operator|.
name|stereotype
operator|.
name|Service
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
name|TimePattern
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
name|TimePatternModel
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
name|InstructionalOfferingDAO
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
name|TimePatternDAO
import|;
end_import

begin_comment
comment|/**  *   * @author Tomas Muller  *  */
end_comment

begin_class
annotation|@
name|Service
argument_list|(
literal|"/sectioningDemoAjax"
argument_list|)
specifier|public
class|class
name|SectioningDemoAjax
extends|extends
name|Action
block|{
specifier|public
name|ActionForward
name|execute
parameter_list|(
name|ActionMapping
name|mapping
parameter_list|,
name|ActionForm
name|form
parameter_list|,
name|HttpServletRequest
name|request
parameter_list|,
name|HttpServletResponse
name|response
parameter_list|)
throws|throws
name|Exception
block|{
name|response
operator|.
name|addHeader
argument_list|(
literal|"Content-Type"
argument_list|,
literal|"text/xml; charset=UTF-8"
argument_list|)
expr_stmt|;
name|request
operator|.
name|setCharacterEncoding
argument_list|(
literal|"UTF-8"
argument_list|)
expr_stmt|;
name|PrintWriter
name|out
init|=
name|response
operator|.
name|getWriter
argument_list|()
decl_stmt|;
name|out
operator|.
name|print
argument_list|(
literal|"<?xml version=\"1.0\" encoding=\"utf-8\" ?>\n"
argument_list|)
expr_stmt|;
name|out
operator|.
name|print
argument_list|(
literal|"<results>"
argument_list|)
expr_stmt|;
name|coumputeSuggestionList
argument_list|(
name|request
argument_list|,
name|out
argument_list|)
expr_stmt|;
name|out
operator|.
name|print
argument_list|(
literal|"</results>"
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
block|}
specifier|protected
name|void
name|print
parameter_list|(
name|PrintWriter
name|out
parameter_list|,
name|String
name|id
parameter_list|,
name|String
name|value
parameter_list|)
throws|throws
name|IOException
block|{
name|out
operator|.
name|print
argument_list|(
literal|"<result id=\""
operator|+
name|id
operator|+
literal|"\" value=\""
operator|+
name|value
operator|+
literal|"\" />"
argument_list|)
expr_stmt|;
block|}
specifier|protected
name|void
name|coumputeSuggestionList
parameter_list|(
name|HttpServletRequest
name|request
parameter_list|,
name|PrintWriter
name|out
parameter_list|)
throws|throws
name|Exception
block|{
if|if
condition|(
literal|"subjectArea"
operator|.
name|equals
argument_list|(
name|request
operator|.
name|getParameter
argument_list|(
literal|"type"
argument_list|)
argument_list|)
condition|)
block|{
name|coumputeCourseNumbers
argument_list|(
name|request
operator|.
name|getParameter
argument_list|(
literal|"id"
argument_list|)
argument_list|,
name|out
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
literal|"timePattern"
operator|.
name|equals
argument_list|(
name|request
operator|.
name|getParameter
argument_list|(
literal|"type"
argument_list|)
argument_list|)
condition|)
block|{
name|out
operator|.
name|print
argument_list|(
literal|"<days>"
argument_list|)
expr_stmt|;
name|coumputeDays
argument_list|(
name|request
operator|.
name|getParameter
argument_list|(
literal|"id"
argument_list|)
argument_list|,
name|out
argument_list|)
expr_stmt|;
name|out
operator|.
name|print
argument_list|(
literal|"</days>"
argument_list|)
expr_stmt|;
name|out
operator|.
name|print
argument_list|(
literal|"<times>"
argument_list|)
expr_stmt|;
name|coumputeTimes
argument_list|(
name|request
operator|.
name|getParameter
argument_list|(
literal|"id"
argument_list|)
argument_list|,
name|out
argument_list|)
expr_stmt|;
name|out
operator|.
name|print
argument_list|(
literal|"</times>"
argument_list|)
expr_stmt|;
block|}
block|}
specifier|protected
name|void
name|coumputeCourseNumbers
parameter_list|(
name|String
name|subjectAreaId
parameter_list|,
name|PrintWriter
name|out
parameter_list|)
throws|throws
name|Exception
block|{
if|if
condition|(
name|subjectAreaId
operator|==
literal|null
operator|||
name|subjectAreaId
operator|.
name|length
argument_list|()
operator|==
literal|0
condition|)
return|return;
name|List
name|courseNumbers
init|=
operator|new
name|InstructionalOfferingDAO
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select co from InstructionalOffering as io , CourseOffering co "
operator|+
literal|"where co.subjectArea.uniqueId = :subjectAreaId "
operator|+
literal|"and io.uniqueId = co.instructionalOffering.uniqueId "
operator|+
literal|"and co.instructionalOffering.notOffered = false "
operator|+
literal|"and io.notOffered = false order by co.courseNbr "
argument_list|)
operator|.
name|setFetchSize
argument_list|(
literal|200
argument_list|)
operator|.
name|setCacheable
argument_list|(
literal|true
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"subjectAreaId"
argument_list|,
name|Long
operator|.
name|parseLong
argument_list|(
name|subjectAreaId
argument_list|)
argument_list|)
operator|.
name|list
argument_list|()
decl_stmt|;
for|for
control|(
name|Iterator
name|i
init|=
name|courseNumbers
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
name|CourseOffering
name|co
init|=
operator|(
name|CourseOffering
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|print
argument_list|(
name|out
argument_list|,
name|co
operator|.
name|getUniqueId
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|,
operator|(
name|co
operator|.
name|getCourseNbr
argument_list|()
operator|+
literal|" - "
operator|+
operator|(
name|co
operator|.
name|getTitle
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
name|co
operator|.
name|getTitle
argument_list|()
operator|.
name|replaceAll
argument_list|(
literal|">"
argument_list|,
literal|"&gt;"
argument_list|)
operator|.
name|replaceAll
argument_list|(
literal|"<"
argument_list|,
literal|"&lt;"
argument_list|)
operator|.
name|replaceAll
argument_list|(
literal|"'"
argument_list|,
literal|"&quot;"
argument_list|)
operator|.
name|replaceAll
argument_list|(
literal|"&"
argument_list|,
literal|"&amp;"
argument_list|)
operator|)
operator|)
argument_list|)
expr_stmt|;
block|}
block|}
specifier|protected
name|void
name|coumputeTimes
parameter_list|(
name|String
name|timePatternId
parameter_list|,
name|PrintWriter
name|out
parameter_list|)
throws|throws
name|Exception
block|{
if|if
condition|(
name|timePatternId
operator|==
literal|null
operator|||
name|timePatternId
operator|.
name|length
argument_list|()
operator|==
literal|0
condition|)
return|return;
name|TimePattern
name|tp
init|=
operator|new
name|TimePatternDAO
argument_list|()
operator|.
name|get
argument_list|(
name|Long
operator|.
name|valueOf
argument_list|(
name|timePatternId
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|tp
operator|==
literal|null
condition|)
return|return;
name|TimePatternModel
name|m
init|=
name|tp
operator|.
name|getTimePatternModel
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
name|m
operator|.
name|getNrTimes
argument_list|()
condition|;
name|i
operator|++
control|)
name|print
argument_list|(
name|out
argument_list|,
name|String
operator|.
name|valueOf
argument_list|(
name|i
argument_list|)
argument_list|,
name|m
operator|.
name|getStartTime
argument_list|(
name|i
argument_list|)
operator|+
literal|" - "
operator|+
name|m
operator|.
name|getEndTime
argument_list|(
name|i
argument_list|)
argument_list|)
expr_stmt|;
block|}
specifier|protected
name|void
name|coumputeDays
parameter_list|(
name|String
name|timePatternId
parameter_list|,
name|PrintWriter
name|out
parameter_list|)
throws|throws
name|Exception
block|{
if|if
condition|(
name|timePatternId
operator|==
literal|null
operator|||
name|timePatternId
operator|.
name|length
argument_list|()
operator|==
literal|0
condition|)
return|return;
name|TimePattern
name|tp
init|=
operator|new
name|TimePatternDAO
argument_list|()
operator|.
name|get
argument_list|(
name|Long
operator|.
name|valueOf
argument_list|(
name|timePatternId
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|tp
operator|==
literal|null
condition|)
return|return;
name|TimePatternModel
name|m
init|=
name|tp
operator|.
name|getTimePatternModel
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
name|m
operator|.
name|getNrDays
argument_list|()
condition|;
name|i
operator|++
control|)
name|print
argument_list|(
name|out
argument_list|,
name|String
operator|.
name|valueOf
argument_list|(
name|i
argument_list|)
argument_list|,
name|m
operator|.
name|getDayHeader
argument_list|(
name|i
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

