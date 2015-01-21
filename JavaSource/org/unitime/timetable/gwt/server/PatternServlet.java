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
name|gwt
operator|.
name|server
package|;
end_package

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|image
operator|.
name|BufferedImage
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
name|javax
operator|.
name|imageio
operator|.
name|ImageIO
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|servlet
operator|.
name|ServletException
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
name|HttpServlet
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
name|cpsolver
operator|.
name|coursett
operator|.
name|model
operator|.
name|TimeLocation
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
name|Exam
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
name|ExamPeriod
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
name|Location
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
name|PeriodPreferenceModel
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
name|dao
operator|.
name|ExamDAO
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
name|ExamPeriodDAO
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
name|LocationDAO
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

begin_import
import|import
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|webutil
operator|.
name|RequiredTimeTable
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|PatternServlet
extends|extends
name|HttpServlet
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|1L
decl_stmt|;
specifier|public
name|void
name|doGet
parameter_list|(
name|HttpServletRequest
name|request
parameter_list|,
name|HttpServletResponse
name|response
parameter_list|)
throws|throws
name|ServletException
throws|,
name|IOException
block|{
name|boolean
name|vertical
init|=
literal|"1"
operator|.
name|equals
argument_list|(
name|request
operator|.
name|getParameter
argument_list|(
literal|"v"
argument_list|)
argument_list|)
decl_stmt|;
name|RequiredTimeTable
name|rtt
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|request
operator|.
name|getParameter
argument_list|(
literal|"tp"
argument_list|)
operator|!=
literal|null
condition|)
block|{
name|TimePattern
name|p
init|=
name|TimePatternDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|get
argument_list|(
name|Long
operator|.
name|valueOf
argument_list|(
name|request
operator|.
name|getParameter
argument_list|(
literal|"tp"
argument_list|)
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|p
operator|!=
literal|null
condition|)
block|{
name|TimeLocation
name|t
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|request
operator|.
name|getParameter
argument_list|(
literal|"as"
argument_list|)
operator|!=
literal|null
operator|&&
name|request
operator|.
name|getParameter
argument_list|(
literal|"ad"
argument_list|)
operator|!=
literal|null
condition|)
block|{
name|t
operator|=
operator|new
name|TimeLocation
argument_list|(
name|Integer
operator|.
name|parseInt
argument_list|(
name|request
operator|.
name|getParameter
argument_list|(
literal|"ad"
argument_list|)
argument_list|)
argument_list|,
name|Integer
operator|.
name|parseInt
argument_list|(
name|request
operator|.
name|getParameter
argument_list|(
literal|"as"
argument_list|)
argument_list|)
argument_list|,
literal|1
argument_list|,
literal|0
argument_list|,
literal|0
argument_list|,
literal|null
argument_list|,
literal|null
argument_list|,
literal|null
argument_list|,
literal|0
argument_list|)
expr_stmt|;
block|}
name|rtt
operator|=
operator|new
name|RequiredTimeTable
argument_list|(
name|p
operator|.
name|getTimePatternModel
argument_list|(
name|t
argument_list|,
literal|true
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
if|else if
condition|(
name|request
operator|.
name|getParameter
argument_list|(
literal|"loc"
argument_list|)
operator|!=
literal|null
condition|)
block|{
name|Location
name|location
init|=
name|LocationDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|get
argument_list|(
name|Long
operator|.
name|valueOf
argument_list|(
name|request
operator|.
name|getParameter
argument_list|(
literal|"loc"
argument_list|)
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|location
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
name|request
operator|.
name|getParameter
argument_list|(
literal|"xt"
argument_list|)
operator|!=
literal|null
condition|)
block|{
name|PeriodPreferenceModel
name|px
init|=
operator|new
name|PeriodPreferenceModel
argument_list|(
name|location
operator|.
name|getSession
argument_list|()
argument_list|,
name|Long
operator|.
name|valueOf
argument_list|(
name|request
operator|.
name|getParameter
argument_list|(
literal|"xt"
argument_list|)
argument_list|)
argument_list|)
decl_stmt|;
name|px
operator|.
name|load
argument_list|(
name|location
argument_list|)
expr_stmt|;
name|rtt
operator|=
operator|new
name|RequiredTimeTable
argument_list|(
name|px
argument_list|)
expr_stmt|;
block|}
else|else
block|{
if|if
condition|(
literal|"1"
operator|.
name|equals
argument_list|(
name|request
operator|.
name|getParameter
argument_list|(
literal|"e"
argument_list|)
argument_list|)
condition|)
name|rtt
operator|=
name|location
operator|.
name|getEventAvailabilityTable
argument_list|()
expr_stmt|;
else|else
name|rtt
operator|=
name|location
operator|.
name|getRoomSharingTable
argument_list|()
expr_stmt|;
block|}
block|}
block|}
if|else if
condition|(
name|request
operator|.
name|getParameter
argument_list|(
literal|"x"
argument_list|)
operator|!=
literal|null
condition|)
block|{
name|Exam
name|exam
init|=
name|ExamDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|get
argument_list|(
name|Long
operator|.
name|valueOf
argument_list|(
name|request
operator|.
name|getParameter
argument_list|(
literal|"x"
argument_list|)
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|exam
operator|!=
literal|null
condition|)
block|{
name|ExamPeriod
name|p
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|request
operator|.
name|getParameter
argument_list|(
literal|"ap"
argument_list|)
operator|!=
literal|null
condition|)
name|p
operator|=
name|ExamPeriodDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|get
argument_list|(
name|Long
operator|.
name|valueOf
argument_list|(
name|request
operator|.
name|getParameter
argument_list|(
literal|"ap"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|PeriodPreferenceModel
name|px
init|=
operator|new
name|PeriodPreferenceModel
argument_list|(
name|exam
operator|.
name|getSession
argument_list|()
argument_list|,
name|p
argument_list|,
name|exam
operator|.
name|getExamType
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|)
decl_stmt|;
name|px
operator|.
name|load
argument_list|(
name|exam
argument_list|)
expr_stmt|;
name|rtt
operator|=
operator|new
name|RequiredTimeTable
argument_list|(
name|px
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
name|rtt
operator|=
operator|new
name|RequiredTimeTable
argument_list|(
operator|new
name|TimePattern
argument_list|()
operator|.
name|getTimePatternModel
argument_list|()
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|rtt
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
name|request
operator|.
name|getParameter
argument_list|(
literal|"s"
argument_list|)
operator|!=
literal|null
condition|)
block|{
try|try
block|{
name|rtt
operator|.
name|getModel
argument_list|()
operator|.
name|setDefaultSelection
argument_list|(
name|Integer
operator|.
name|parseInt
argument_list|(
name|request
operator|.
name|getParameter
argument_list|(
literal|"s"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|NumberFormatException
name|e
parameter_list|)
block|{
name|rtt
operator|.
name|getModel
argument_list|()
operator|.
name|setDefaultSelection
argument_list|(
name|request
operator|.
name|getParameter
argument_list|(
literal|"s"
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
if|if
condition|(
name|request
operator|.
name|getParameter
argument_list|(
literal|"p"
argument_list|)
operator|!=
literal|null
condition|)
name|rtt
operator|.
name|getModel
argument_list|()
operator|.
name|setPreferences
argument_list|(
name|request
operator|.
name|getParameter
argument_list|(
literal|"p"
argument_list|)
argument_list|)
expr_stmt|;
name|boolean
name|hc
init|=
operator|(
literal|"1"
operator|.
name|equals
argument_list|(
name|request
operator|.
name|getParameter
argument_list|(
literal|"hc"
argument_list|)
argument_list|)
operator|)
decl_stmt|;
name|response
operator|.
name|setContentType
argument_list|(
literal|"image/png"
argument_list|)
expr_stmt|;
name|response
operator|.
name|setHeader
argument_list|(
literal|"Content-Disposition"
argument_list|,
literal|"attachment; filename=\"pattern.png\""
argument_list|)
expr_stmt|;
name|BufferedImage
name|image
init|=
name|rtt
operator|.
name|createBufferedImage
argument_list|(
name|vertical
argument_list|,
name|hc
argument_list|)
decl_stmt|;
if|if
condition|(
name|image
operator|!=
literal|null
condition|)
name|ImageIO
operator|.
name|write
argument_list|(
name|image
argument_list|,
literal|"PNG"
argument_list|,
name|response
operator|.
name|getOutputStream
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

