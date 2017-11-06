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
name|util
operator|.
name|queue
package|;
end_package

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
name|Serializable
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
name|util
operator|.
name|Date
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|lang
operator|.
name|StringEscapeUtils
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|lang
operator|.
name|StringUtils
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
name|Formats
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|QueueMessage
implements|implements
name|Serializable
implements|,
name|Comparable
argument_list|<
name|QueueMessage
argument_list|>
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
specifier|static
enum|enum
name|Level
block|{
name|TRACE
block|,
name|DEBUG
block|,
name|PROGRESS
block|,
name|INFO
block|,
name|STAGE
block|,
name|WARN
block|,
name|ERROR
block|,
name|FATAL
block|,
name|HTML
block|}
specifier|private
name|Level
name|iLevel
init|=
name|Level
operator|.
name|INFO
decl_stmt|;
specifier|private
name|String
name|iMessage
decl_stmt|;
specifier|private
name|Date
name|iDate
init|=
literal|null
decl_stmt|;
specifier|private
name|String
name|iError
init|=
literal|null
decl_stmt|;
specifier|public
name|QueueMessage
parameter_list|(
name|Level
name|level
parameter_list|,
name|String
name|message
parameter_list|)
block|{
name|iLevel
operator|=
name|level
expr_stmt|;
name|iMessage
operator|=
name|message
expr_stmt|;
name|iDate
operator|=
operator|new
name|Date
argument_list|()
expr_stmt|;
block|}
specifier|public
name|QueueMessage
parameter_list|(
name|Level
name|level
parameter_list|,
name|Object
name|message
parameter_list|)
block|{
name|this
argument_list|(
name|level
argument_list|,
name|message
operator|==
literal|null
condition|?
literal|""
else|:
name|message
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
specifier|public
name|QueueMessage
parameter_list|(
name|Level
name|level
parameter_list|,
name|String
name|message
parameter_list|,
name|Throwable
name|error
parameter_list|)
block|{
name|this
argument_list|(
name|level
argument_list|,
name|message
argument_list|)
expr_stmt|;
if|if
condition|(
name|error
operator|!=
literal|null
condition|)
block|{
name|StringWriter
name|writer
init|=
operator|new
name|StringWriter
argument_list|()
decl_stmt|;
name|PrintWriter
name|pw
init|=
operator|new
name|PrintWriter
argument_list|(
name|writer
argument_list|)
decl_stmt|;
name|error
operator|.
name|printStackTrace
argument_list|(
operator|new
name|PrintWriter
argument_list|(
name|writer
argument_list|)
argument_list|)
expr_stmt|;
name|pw
operator|.
name|flush
argument_list|()
expr_stmt|;
name|pw
operator|.
name|close
argument_list|()
expr_stmt|;
name|iError
operator|=
name|writer
operator|.
name|toString
argument_list|()
expr_stmt|;
block|}
block|}
specifier|public
name|QueueMessage
parameter_list|(
name|Level
name|level
parameter_list|,
name|Object
name|message
parameter_list|,
name|Throwable
name|error
parameter_list|)
block|{
name|this
argument_list|(
name|level
argument_list|,
name|message
operator|==
literal|null
condition|?
literal|""
else|:
name|message
operator|.
name|toString
argument_list|()
argument_list|,
name|error
argument_list|)
expr_stmt|;
block|}
specifier|public
name|Level
name|getLevel
parameter_list|()
block|{
return|return
name|iLevel
return|;
block|}
specifier|public
name|String
name|getMessage
parameter_list|()
block|{
return|return
name|iMessage
return|;
block|}
specifier|public
name|Date
name|getDate
parameter_list|()
block|{
return|return
name|iDate
return|;
block|}
specifier|public
name|boolean
name|hasError
parameter_list|()
block|{
return|return
name|iError
operator|!=
literal|null
operator|&&
operator|!
name|iError
operator|.
name|isEmpty
argument_list|()
return|;
block|}
specifier|public
name|String
name|getError
parameter_list|()
block|{
return|return
name|iError
return|;
block|}
specifier|protected
name|String
name|formatMessagePlain
parameter_list|()
block|{
name|Formats
operator|.
name|Format
argument_list|<
name|Date
argument_list|>
name|df
init|=
name|Formats
operator|.
name|getDateFormat
argument_list|(
name|Formats
operator|.
name|Pattern
operator|.
name|DATE_TIME_STAMP
argument_list|)
decl_stmt|;
return|return
name|df
operator|.
name|format
argument_list|(
name|getDate
argument_list|()
argument_list|)
operator|+
literal|" "
operator|+
name|StringUtils
operator|.
name|leftPad
argument_list|(
name|getLevel
argument_list|()
operator|.
name|name
argument_list|()
argument_list|,
literal|5
argument_list|)
operator|+
literal|" "
operator|+
name|getMessage
argument_list|()
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
name|formatMessagePlain
argument_list|()
operator|+
operator|(
name|hasError
argument_list|()
condition|?
literal|"\n"
operator|+
name|getError
argument_list|()
else|:
literal|""
operator|)
return|;
block|}
specifier|protected
name|String
name|formatMessageHTML
parameter_list|()
block|{
switch|switch
condition|(
name|getLevel
argument_list|()
condition|)
block|{
case|case
name|TRACE
case|:
return|return
literal|"&nbsp;&nbsp;&nbsp;&nbsp;<i><font color='gray'> "
operator|+
name|StringEscapeUtils
operator|.
name|escapeHtml
argument_list|(
name|getMessage
argument_list|()
argument_list|)
operator|+
literal|"</font></i>"
return|;
case|case
name|DEBUG
case|:
return|return
literal|"&nbsp;&nbsp;<i><font color='gray'> "
operator|+
name|StringEscapeUtils
operator|.
name|escapeHtml
argument_list|(
name|getMessage
argument_list|()
argument_list|)
operator|+
literal|"</font></i>"
return|;
case|case
name|INFO
case|:
return|return
literal|"&nbsp;&nbsp;"
operator|+
name|StringEscapeUtils
operator|.
name|escapeHtml
argument_list|(
name|getMessage
argument_list|()
argument_list|)
return|;
case|case
name|WARN
case|:
return|return
literal|"<font color='orange'>"
operator|+
name|StringEscapeUtils
operator|.
name|escapeHtml
argument_list|(
name|getMessage
argument_list|()
argument_list|)
operator|+
literal|"</font>"
return|;
case|case
name|ERROR
case|:
return|return
literal|"<font color='red'>"
operator|+
name|StringEscapeUtils
operator|.
name|escapeHtml
argument_list|(
name|getMessage
argument_list|()
argument_list|)
operator|+
literal|"</font>"
return|;
case|case
name|FATAL
case|:
return|return
literal|"<font color='red'><b>"
operator|+
name|StringEscapeUtils
operator|.
name|escapeHtml
argument_list|(
name|getMessage
argument_list|()
argument_list|)
operator|+
literal|"</b></font>"
return|;
case|case
name|PROGRESS
case|:
return|return
literal|"<b>"
operator|+
name|StringEscapeUtils
operator|.
name|escapeHtml
argument_list|(
name|getMessage
argument_list|()
argument_list|)
operator|+
literal|"</b>"
return|;
case|case
name|STAGE
case|:
return|return
literal|"<b>"
operator|+
name|StringEscapeUtils
operator|.
name|escapeHtml
argument_list|(
name|getMessage
argument_list|()
argument_list|)
operator|+
literal|"</b>"
return|;
case|case
name|HTML
case|:
return|return
name|getMessage
argument_list|()
return|;
default|default:
return|return
name|StringEscapeUtils
operator|.
name|escapeHtml
argument_list|(
name|getMessage
argument_list|()
argument_list|)
return|;
block|}
block|}
specifier|public
name|String
name|toHTML
parameter_list|()
block|{
return|return
name|formatMessageHTML
argument_list|()
operator|+
operator|(
name|hasError
argument_list|()
condition|?
literal|"<br><font color='red'><pre>"
operator|+
name|StringEscapeUtils
operator|.
name|escapeHtml
argument_list|(
name|getError
argument_list|()
argument_list|)
operator|+
literal|"</pre></font>"
else|:
literal|""
operator|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|int
name|compareTo
parameter_list|(
name|QueueMessage
name|m
parameter_list|)
block|{
return|return
name|getDate
argument_list|()
operator|.
name|compareTo
argument_list|(
name|m
operator|.
name|getDate
argument_list|()
argument_list|)
return|;
block|}
block|}
end_class

end_unit
