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
name|tags
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
name|text
operator|.
name|NumberFormat
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|servlet
operator|.
name|jsp
operator|.
name|JspException
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|servlet
operator|.
name|jsp
operator|.
name|tagext
operator|.
name|BodyTagSupport
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
name|JProf
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
name|GwtMessages
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|TimeStamp
extends|extends
name|BodyTagSupport
block|{
specifier|private
specifier|static
specifier|final
name|GwtMessages
name|MESSAGES
init|=
name|Localization
operator|.
name|create
argument_list|(
name|GwtMessages
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
operator|-
literal|1l
decl_stmt|;
specifier|public
name|int
name|doStartTag
parameter_list|()
throws|throws
name|JspException
block|{
return|return
name|EVAL_BODY_BUFFERED
return|;
block|}
specifier|public
name|int
name|doEndTag
parameter_list|()
throws|throws
name|JspException
block|{
try|try
block|{
name|Double
name|startTime
init|=
operator|(
name|Double
operator|)
name|pageContext
operator|.
name|getRequest
argument_list|()
operator|.
name|getAttribute
argument_list|(
literal|"TimeStamp"
argument_list|)
decl_stmt|;
if|if
condition|(
name|startTime
operator|!=
literal|null
condition|)
block|{
name|double
name|endTime
init|=
name|JProf
operator|.
name|currentTimeSec
argument_list|()
decl_stmt|;
name|double
name|diff
init|=
name|endTime
operator|-
name|startTime
decl_stmt|;
name|NumberFormat
name|nf
init|=
name|NumberFormat
operator|.
name|getInstance
argument_list|()
decl_stmt|;
name|nf
operator|.
name|setMaximumFractionDigits
argument_list|(
literal|2
argument_list|)
expr_stmt|;
name|pageContext
operator|.
name|getOut
argument_list|()
operator|.
name|print
argument_list|(
name|MESSAGES
operator|.
name|pageGeneratedIn
argument_list|(
name|nf
operator|.
name|format
argument_list|(
name|diff
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
block|}
return|return
name|EVAL_PAGE
return|;
block|}
block|}
end_class

end_unit

