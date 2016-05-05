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
name|util
operator|.
name|Date
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
name|cpsolver
operator|.
name|ifs
operator|.
name|util
operator|.
name|DataProperties
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_interface
specifier|public
interface|interface
name|CommonSolverInterface
block|{
specifier|public
name|Date
name|getLoadedDate
parameter_list|()
function_decl|;
specifier|public
name|void
name|start
parameter_list|()
function_decl|;
specifier|public
name|boolean
name|isRunning
parameter_list|()
function_decl|;
specifier|public
name|void
name|stopSolver
parameter_list|()
function_decl|;
specifier|public
name|void
name|restoreBest
parameter_list|()
function_decl|;
specifier|public
name|void
name|saveBest
parameter_list|()
function_decl|;
specifier|public
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|currentSolutionInfo
parameter_list|()
function_decl|;
specifier|public
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|bestSolutionInfo
parameter_list|()
function_decl|;
specifier|public
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|statusSolutionInfo
parameter_list|()
function_decl|;
specifier|public
name|boolean
name|isWorking
parameter_list|()
function_decl|;
specifier|public
name|void
name|clear
parameter_list|()
function_decl|;
specifier|public
name|Map
name|getProgress
parameter_list|()
function_decl|;
specifier|public
name|String
name|getLog
parameter_list|()
function_decl|;
specifier|public
name|String
name|getLog
parameter_list|(
name|int
name|level
parameter_list|,
name|boolean
name|includeDate
parameter_list|)
function_decl|;
specifier|public
name|String
name|getLog
parameter_list|(
name|int
name|level
parameter_list|,
name|boolean
name|includeDate
parameter_list|,
name|String
name|fromStage
parameter_list|)
function_decl|;
specifier|public
name|DataProperties
name|getProperties
parameter_list|()
function_decl|;
specifier|public
name|void
name|setProperties
parameter_list|(
name|DataProperties
name|config
parameter_list|)
function_decl|;
specifier|public
name|void
name|save
parameter_list|()
function_decl|;
specifier|public
name|byte
index|[]
name|exportXml
parameter_list|()
throws|throws
name|IOException
function_decl|;
block|}
end_interface

end_unit

