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
name|solver
operator|.
name|exam
package|;
end_package

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
name|exam
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
name|cpsolver
operator|.
name|exam
operator|.
name|model
operator|.
name|ExamPlacement
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
name|assignment
operator|.
name|Assignment
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
name|Callback
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

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
specifier|abstract
class|class
name|ExamLoader
implements|implements
name|Runnable
block|{
specifier|private
name|ExamModel
name|iModel
init|=
literal|null
decl_stmt|;
specifier|private
name|Assignment
argument_list|<
name|Exam
argument_list|,
name|ExamPlacement
argument_list|>
name|iAssignment
init|=
literal|null
decl_stmt|;
specifier|private
name|Callback
name|iCallback
init|=
literal|null
decl_stmt|;
comment|/** Constructor       * @param model an empty instance of timetable model       */
specifier|public
name|ExamLoader
parameter_list|(
name|ExamModel
name|model
parameter_list|,
name|Assignment
argument_list|<
name|Exam
argument_list|,
name|ExamPlacement
argument_list|>
name|assignment
parameter_list|)
block|{
name|iModel
operator|=
name|model
expr_stmt|;
name|iAssignment
operator|=
name|assignment
expr_stmt|;
block|}
comment|/** Returns provided model.      * @return provided model      */
specifier|protected
name|ExamModel
name|getModel
parameter_list|()
block|{
return|return
name|iModel
return|;
block|}
comment|/**      * Returns provided assignment      */
specifier|protected
name|Assignment
argument_list|<
name|Exam
argument_list|,
name|ExamPlacement
argument_list|>
name|getAssignment
parameter_list|()
block|{
return|return
name|iAssignment
return|;
block|}
comment|/** Load the model.      */
specifier|public
specifier|abstract
name|void
name|load
parameter_list|()
throws|throws
name|Exception
function_decl|;
comment|/** Sets callback class      * @param callback method {@link Callback#execute()} is executed when load is done      */
specifier|public
name|void
name|setCallback
parameter_list|(
name|Callback
name|callback
parameter_list|)
block|{
name|iCallback
operator|=
name|callback
expr_stmt|;
block|}
specifier|public
name|void
name|run
parameter_list|()
block|{
try|try
block|{
if|if
condition|(
name|getModel
argument_list|()
operator|!=
literal|null
condition|)
name|ApplicationProperties
operator|.
name|setSessionId
argument_list|(
name|getModel
argument_list|()
operator|.
name|getProperties
argument_list|()
operator|.
name|getPropertyLong
argument_list|(
literal|"General.SessionId"
argument_list|,
operator|(
name|Long
operator|)
literal|null
argument_list|)
argument_list|)
expr_stmt|;
name|load
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|Logger
operator|.
name|getLogger
argument_list|(
name|this
operator|.
name|getClass
argument_list|()
argument_list|)
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
finally|finally
block|{
if|if
condition|(
name|iCallback
operator|!=
literal|null
condition|)
name|iCallback
operator|.
name|execute
argument_list|()
expr_stmt|;
name|ApplicationProperties
operator|.
name|setSessionId
argument_list|(
literal|null
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

