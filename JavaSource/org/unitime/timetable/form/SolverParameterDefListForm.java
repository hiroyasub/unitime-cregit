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
name|form
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|ArrayList
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
name|ActionMapping
import|;
end_import

begin_comment
comment|/**   * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|SolverParameterDefListForm
extends|extends
name|ActionForm
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|4159291816177340774L
decl_stmt|;
comment|// --------------------------------------------------------- Instance Variables
comment|/** solverParamDefs property */
specifier|private
name|Collection
name|solverParamDefs
decl_stmt|;
comment|// --------------------------------------------------------- Methods
comment|/**  	 * Method reset 	 * @param mapping 	 * @param request 	 */
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
name|solverParamDefs
operator|=
operator|new
name|ArrayList
argument_list|()
expr_stmt|;
block|}
comment|/**  	 * Returns the solverParamDefs. 	 * @return Collection 	 */
specifier|public
name|Collection
name|getSolverParamDefs
parameter_list|()
block|{
return|return
name|solverParamDefs
return|;
block|}
comment|/**  	 * Set the solverParamDefs. 	 * @param solverParamDefs The solverParamDefs to set 	 */
specifier|public
name|void
name|setSolverParamDefs
parameter_list|(
name|Collection
name|solverParamDefs
parameter_list|)
block|{
name|this
operator|.
name|solverParamDefs
operator|=
name|solverParamDefs
expr_stmt|;
block|}
block|}
end_class

end_unit

