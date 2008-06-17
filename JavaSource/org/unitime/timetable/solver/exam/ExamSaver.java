begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.1 (University Timetabling Application)  * Copyright (C) 2008, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 2 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program; if not, write to the Free Software Foundation, Inc.,  * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
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
name|net
operator|.
name|sf
operator|.
name|cpsolver
operator|.
name|ifs
operator|.
name|solution
operator|.
name|Solution
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
name|ifs
operator|.
name|solver
operator|.
name|Solver
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
name|apache
operator|.
name|log4j
operator|.
name|Logger
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
specifier|abstract
class|class
name|ExamSaver
implements|implements
name|Runnable
block|{
specifier|private
name|Solver
name|iSolver
init|=
literal|null
decl_stmt|;
specifier|private
name|Callback
name|iCallback
init|=
literal|null
decl_stmt|;
comment|/** Constructor      */
specifier|public
name|ExamSaver
parameter_list|(
name|Solver
name|solver
parameter_list|)
block|{
name|iSolver
operator|=
name|solver
expr_stmt|;
block|}
comment|/** Solver */
specifier|public
name|Solver
name|getSolver
parameter_list|()
block|{
return|return
name|iSolver
return|;
block|}
comment|/** Solution to be saved */
specifier|protected
name|Solution
name|getSolution
parameter_list|()
block|{
return|return
name|iSolver
operator|.
name|currentSolution
argument_list|()
return|;
block|}
comment|/** Model of the solution */
specifier|protected
name|ExamModel
name|getModel
parameter_list|()
block|{
return|return
operator|(
name|ExamModel
operator|)
name|iSolver
operator|.
name|currentSolution
argument_list|()
operator|.
name|getModel
argument_list|()
return|;
block|}
comment|/** Save the solution*/
specifier|public
specifier|abstract
name|void
name|save
parameter_list|()
throws|throws
name|Exception
function_decl|;
comment|/** Sets callback class      * @param callback method {@link Callback#execute()} is executed when save is done      */
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
name|save
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
block|}
block|}
block|}
end_class

end_unit

