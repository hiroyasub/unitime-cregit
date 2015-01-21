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
name|form
package|;
end_package

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
name|org
operator|.
name|apache
operator|.
name|struts
operator|.
name|action
operator|.
name|ActionErrors
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
name|apache
operator|.
name|struts
operator|.
name|action
operator|.
name|ActionMessage
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
name|security
operator|.
name|SessionContext
import|;
end_import

begin_comment
comment|/**   * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|RoomAvailabilityForm
extends|extends
name|ExamReportForm
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
operator|-
literal|7604226806875981047L
decl_stmt|;
specifier|private
name|String
name|iFilter
init|=
literal|null
decl_stmt|;
specifier|private
name|boolean
name|iIncludeExams
init|=
literal|false
decl_stmt|;
specifier|private
name|boolean
name|iCompare
init|=
literal|false
decl_stmt|;
specifier|public
name|ActionErrors
name|validate
parameter_list|(
name|ActionMapping
name|mapping
parameter_list|,
name|HttpServletRequest
name|request
parameter_list|)
block|{
name|ActionErrors
name|errors
init|=
name|super
operator|.
name|validate
argument_list|(
name|mapping
argument_list|,
name|request
argument_list|)
decl_stmt|;
if|if
condition|(
name|getExamType
argument_list|()
operator|<
literal|0
condition|)
name|errors
operator|.
name|add
argument_list|(
literal|"examType"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.required"
argument_list|,
literal|""
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|errors
return|;
block|}
specifier|public
name|void
name|reset
parameter_list|(
name|ActionMapping
name|mapping
parameter_list|,
name|HttpServletRequest
name|request
parameter_list|)
block|{
name|super
operator|.
name|reset
argument_list|(
name|mapping
argument_list|,
name|request
argument_list|)
expr_stmt|;
name|iIncludeExams
operator|=
literal|false
expr_stmt|;
name|iFilter
operator|=
literal|null
expr_stmt|;
name|iCompare
operator|=
literal|false
expr_stmt|;
name|setExamType
argument_list|(
literal|null
argument_list|)
expr_stmt|;
block|}
specifier|public
name|String
name|getFilter
parameter_list|()
block|{
return|return
name|iFilter
return|;
block|}
specifier|public
name|void
name|setFilter
parameter_list|(
name|String
name|filter
parameter_list|)
block|{
name|iFilter
operator|=
name|filter
expr_stmt|;
block|}
specifier|public
name|boolean
name|getIncludeExams
parameter_list|()
block|{
return|return
name|iIncludeExams
return|;
block|}
specifier|public
name|void
name|setIncludeExams
parameter_list|(
name|boolean
name|exams
parameter_list|)
block|{
name|iIncludeExams
operator|=
name|exams
expr_stmt|;
block|}
specifier|public
name|boolean
name|getCompare
parameter_list|()
block|{
return|return
name|iCompare
return|;
block|}
specifier|public
name|void
name|setCompare
parameter_list|(
name|boolean
name|compare
parameter_list|)
block|{
name|iCompare
operator|=
name|compare
expr_stmt|;
block|}
specifier|public
name|void
name|load
parameter_list|(
name|SessionContext
name|session
parameter_list|)
block|{
name|super
operator|.
name|load
argument_list|(
name|session
argument_list|)
expr_stmt|;
name|setFilter
argument_list|(
operator|(
name|String
operator|)
name|session
operator|.
name|getAttribute
argument_list|(
literal|"RoomAvailability.Filter"
argument_list|)
argument_list|)
expr_stmt|;
name|setIncludeExams
argument_list|(
name|Boolean
operator|.
name|TRUE
operator|.
name|equals
argument_list|(
name|session
operator|.
name|getAttribute
argument_list|(
literal|"RoomAvailability.Exams"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|setCompare
argument_list|(
name|Boolean
operator|.
name|TRUE
operator|.
name|equals
argument_list|(
name|session
operator|.
name|getAttribute
argument_list|(
literal|"RoomAvailability.Compare"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|save
parameter_list|(
name|SessionContext
name|session
parameter_list|)
block|{
name|super
operator|.
name|save
argument_list|(
name|session
argument_list|)
expr_stmt|;
if|if
condition|(
name|getFilter
argument_list|()
operator|==
literal|null
condition|)
name|session
operator|.
name|removeAttribute
argument_list|(
literal|"RoomAvailability.Filter"
argument_list|)
expr_stmt|;
else|else
name|session
operator|.
name|setAttribute
argument_list|(
literal|"RoomAvailability.Filter"
argument_list|,
name|getFilter
argument_list|()
argument_list|)
expr_stmt|;
name|session
operator|.
name|setAttribute
argument_list|(
literal|"RoomAvailability.Exams"
argument_list|,
name|getIncludeExams
argument_list|()
argument_list|)
expr_stmt|;
name|session
operator|.
name|setAttribute
argument_list|(
literal|"RoomAvailability.Compare"
argument_list|,
name|getCompare
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

