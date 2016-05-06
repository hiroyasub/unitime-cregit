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
name|instructor
package|;
end_package

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
name|solver
operator|.
name|Solver
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
name|DataProperties
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
name|ProblemLoader
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
name|ProblemSaver
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
name|Progress
import|;
end_import

begin_import
import|import
name|org
operator|.
name|cpsolver
operator|.
name|instructor
operator|.
name|model
operator|.
name|InstructorSchedulingModel
import|;
end_import

begin_import
import|import
name|org
operator|.
name|cpsolver
operator|.
name|instructor
operator|.
name|model
operator|.
name|TeachingAssignment
import|;
end_import

begin_import
import|import
name|org
operator|.
name|cpsolver
operator|.
name|instructor
operator|.
name|model
operator|.
name|TeachingRequest
import|;
end_import

begin_import
import|import
name|org
operator|.
name|dom4j
operator|.
name|Document
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
name|SolverParameterGroup
operator|.
name|SolverType
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
name|solver
operator|.
name|AbstractSolver
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
name|solver
operator|.
name|SolverDisposeListener
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|InstructorSchedulingSolver
extends|extends
name|AbstractSolver
argument_list|<
name|TeachingRequest
argument_list|,
name|TeachingAssignment
argument_list|,
name|InstructorSchedulingModel
argument_list|>
implements|implements
name|InstructorSchedulingProxy
block|{
specifier|public
name|InstructorSchedulingSolver
parameter_list|(
name|DataProperties
name|properties
parameter_list|,
name|SolverDisposeListener
name|disposeListener
parameter_list|)
block|{
name|super
argument_list|(
name|properties
argument_list|,
name|disposeListener
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|SolverType
name|getType
parameter_list|()
block|{
return|return
name|SolverType
operator|.
name|INSTRUCTOR
return|;
block|}
annotation|@
name|Override
specifier|protected
name|ProblemSaver
argument_list|<
name|TeachingRequest
argument_list|,
name|TeachingAssignment
argument_list|,
name|InstructorSchedulingModel
argument_list|>
name|getDatabaseSaver
parameter_list|(
name|Solver
argument_list|<
name|TeachingRequest
argument_list|,
name|TeachingAssignment
argument_list|>
name|solver
parameter_list|)
block|{
comment|// FIXME: Write database saver
return|return
literal|null
return|;
block|}
annotation|@
name|Override
specifier|protected
name|ProblemLoader
argument_list|<
name|TeachingRequest
argument_list|,
name|TeachingAssignment
argument_list|,
name|InstructorSchedulingModel
argument_list|>
name|getDatabaseLoader
parameter_list|(
name|InstructorSchedulingModel
name|model
parameter_list|,
name|Assignment
argument_list|<
name|TeachingRequest
argument_list|,
name|TeachingAssignment
argument_list|>
name|assignment
parameter_list|)
block|{
return|return
operator|new
name|InstructorSchedulingDatabaseLoader
argument_list|(
name|model
argument_list|,
name|assignment
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|protected
name|InstructorSchedulingModel
name|createModel
parameter_list|(
name|DataProperties
name|properties
parameter_list|)
block|{
return|return
operator|new
name|InstructorSchedulingModel
argument_list|(
name|properties
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|protected
name|Document
name|createCurrentSolutionBackup
parameter_list|(
name|boolean
name|anonymize
parameter_list|,
name|boolean
name|idconv
parameter_list|)
block|{
if|if
condition|(
name|anonymize
condition|)
block|{
name|getProperties
argument_list|()
operator|.
name|setProperty
argument_list|(
literal|"Xml.Anonymize"
argument_list|,
literal|"true"
argument_list|)
expr_stmt|;
name|getProperties
argument_list|()
operator|.
name|setProperty
argument_list|(
literal|"Xml.ShowNames"
argument_list|,
literal|"false"
argument_list|)
expr_stmt|;
name|getProperties
argument_list|()
operator|.
name|setProperty
argument_list|(
literal|"Xml.ConvertIds"
argument_list|,
name|idconv
condition|?
literal|"true"
else|:
literal|"false"
argument_list|)
expr_stmt|;
name|getProperties
argument_list|()
operator|.
name|setProperty
argument_list|(
literal|"Xml.SaveInitial"
argument_list|,
literal|"false"
argument_list|)
expr_stmt|;
name|getProperties
argument_list|()
operator|.
name|setProperty
argument_list|(
literal|"Xml.SaveBest"
argument_list|,
literal|"false"
argument_list|)
expr_stmt|;
name|getProperties
argument_list|()
operator|.
name|setProperty
argument_list|(
literal|"Xml.SaveSolution"
argument_list|,
literal|"true"
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|getProperties
argument_list|()
operator|.
name|setProperty
argument_list|(
literal|"Xml.Anonymize"
argument_list|,
literal|"false"
argument_list|)
expr_stmt|;
name|getProperties
argument_list|()
operator|.
name|setProperty
argument_list|(
literal|"Xml.ShowNames"
argument_list|,
literal|"true"
argument_list|)
expr_stmt|;
name|getProperties
argument_list|()
operator|.
name|setProperty
argument_list|(
literal|"Xml.ConvertIds"
argument_list|,
literal|"false"
argument_list|)
expr_stmt|;
name|getProperties
argument_list|()
operator|.
name|setProperty
argument_list|(
literal|"Xml.SaveInitial"
argument_list|,
literal|"true"
argument_list|)
expr_stmt|;
name|getProperties
argument_list|()
operator|.
name|setProperty
argument_list|(
literal|"Xml.SaveBest"
argument_list|,
literal|"true"
argument_list|)
expr_stmt|;
name|getProperties
argument_list|()
operator|.
name|setProperty
argument_list|(
literal|"Xml.SaveSolution"
argument_list|,
literal|"true"
argument_list|)
expr_stmt|;
block|}
name|InstructorSchedulingModel
name|model
init|=
operator|(
name|InstructorSchedulingModel
operator|)
name|currentSolution
argument_list|()
operator|.
name|getModel
argument_list|()
decl_stmt|;
name|Document
name|document
init|=
name|model
operator|.
name|save
argument_list|(
name|currentSolution
argument_list|()
operator|.
name|getAssignment
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|document
operator|==
literal|null
condition|)
return|return
literal|null
return|;
if|if
condition|(
operator|!
name|anonymize
condition|)
block|{
name|Progress
name|p
init|=
name|Progress
operator|.
name|getInstance
argument_list|(
name|model
argument_list|)
decl_stmt|;
if|if
condition|(
name|p
operator|!=
literal|null
condition|)
name|Progress
operator|.
name|getInstance
argument_list|(
name|this
argument_list|)
operator|.
name|save
argument_list|(
name|document
operator|.
name|getRootElement
argument_list|()
argument_list|)
expr_stmt|;
block|}
return|return
name|document
return|;
block|}
annotation|@
name|Override
specifier|protected
name|void
name|restureCurrentSolutionFromBackup
parameter_list|(
name|Document
name|document
parameter_list|)
block|{
name|InstructorSchedulingModel
name|model
init|=
operator|(
name|InstructorSchedulingModel
operator|)
name|currentSolution
argument_list|()
operator|.
name|getModel
argument_list|()
decl_stmt|;
name|model
operator|.
name|load
argument_list|(
name|document
argument_list|,
name|currentSolution
argument_list|()
operator|.
name|getAssignment
argument_list|()
argument_list|)
expr_stmt|;
name|Progress
name|p
init|=
name|Progress
operator|.
name|getInstance
argument_list|(
name|model
argument_list|)
decl_stmt|;
if|if
condition|(
name|p
operator|!=
literal|null
condition|)
block|{
name|p
operator|.
name|load
argument_list|(
name|document
operator|.
name|getRootElement
argument_list|()
argument_list|,
literal|true
argument_list|)
expr_stmt|;
name|p
operator|.
name|message
argument_list|(
name|Progress
operator|.
name|MSGLEVEL_STAGE
argument_list|,
literal|"Restoring from backup ..."
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

