begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.2 - 3.5 (University Timetabling Application)  * Copyright (C) 2008 - 2013, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
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
name|interactive
operator|.
name|SuggestionsModel
import|;
end_import

begin_comment
comment|/**   * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|SolutionChangesForm
extends|extends
name|ActionForm
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|7768482825453181893L
decl_stmt|;
specifier|private
name|String
name|iOp
init|=
literal|null
decl_stmt|;
specifier|private
specifier|static
name|String
index|[]
name|sReferences
init|=
block|{
literal|"Best Solution"
block|,
literal|"Initial Solution"
block|,
literal|"Selected Solution"
block|}
decl_stmt|;
specifier|public
specifier|static
name|int
name|sReferenceBest
init|=
literal|0
decl_stmt|;
specifier|public
specifier|static
name|int
name|sReferenceInitial
init|=
literal|1
decl_stmt|;
specifier|public
specifier|static
name|int
name|sReferenceSelected
init|=
literal|2
decl_stmt|;
specifier|private
name|String
name|iReference
init|=
literal|null
decl_stmt|;
specifier|private
name|boolean
name|iSimpleMode
init|=
literal|false
decl_stmt|;
specifier|private
name|boolean
name|iReversedMode
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
operator|new
name|ActionErrors
argument_list|()
decl_stmt|;
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
name|iReference
operator|=
name|sReferences
index|[
name|sReferenceInitial
index|]
expr_stmt|;
name|iSimpleMode
operator|=
literal|false
expr_stmt|;
name|iReversedMode
operator|=
literal|false
expr_stmt|;
block|}
specifier|public
name|void
name|load
parameter_list|(
name|SuggestionsModel
name|model
parameter_list|)
block|{
name|iSimpleMode
operator|=
name|model
operator|.
name|getSimpleMode
argument_list|()
expr_stmt|;
name|iReversedMode
operator|=
name|model
operator|.
name|getReversedMode
argument_list|()
expr_stmt|;
block|}
specifier|public
name|void
name|save
parameter_list|(
name|SuggestionsModel
name|model
parameter_list|)
block|{
name|model
operator|.
name|setSimpleMode
argument_list|(
name|getSimpleMode
argument_list|()
argument_list|)
expr_stmt|;
name|model
operator|.
name|setReversedMode
argument_list|(
name|getReversedMode
argument_list|()
argument_list|)
expr_stmt|;
block|}
specifier|public
name|boolean
name|getSimpleMode
parameter_list|()
block|{
return|return
name|iSimpleMode
return|;
block|}
specifier|public
name|void
name|setSimpleMode
parameter_list|(
name|boolean
name|simpleMode
parameter_list|)
block|{
name|iSimpleMode
operator|=
name|simpleMode
expr_stmt|;
block|}
specifier|public
name|boolean
name|getReversedMode
parameter_list|()
block|{
return|return
name|iReversedMode
return|;
block|}
specifier|public
name|void
name|setReversedMode
parameter_list|(
name|boolean
name|reversedMode
parameter_list|)
block|{
name|iReversedMode
operator|=
name|reversedMode
expr_stmt|;
block|}
specifier|public
name|String
name|getReference
parameter_list|()
block|{
return|return
name|iReference
return|;
block|}
specifier|public
name|void
name|setReference
parameter_list|(
name|String
name|reference
parameter_list|)
block|{
name|iReference
operator|=
name|reference
expr_stmt|;
block|}
specifier|public
name|int
name|getReferenceInt
parameter_list|()
block|{
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|sReferences
operator|.
name|length
condition|;
name|i
operator|++
control|)
if|if
condition|(
name|sReferences
index|[
name|i
index|]
operator|.
name|equals
argument_list|(
name|iReference
argument_list|)
condition|)
return|return
name|i
return|;
return|return
name|sReferenceBest
return|;
block|}
specifier|public
name|void
name|setReferenceInt
parameter_list|(
name|int
name|reference
parameter_list|)
block|{
name|iReference
operator|=
name|sReferences
index|[
name|reference
index|]
expr_stmt|;
block|}
specifier|public
name|String
index|[]
name|getReferences
parameter_list|()
block|{
return|return
name|sReferences
return|;
block|}
specifier|public
name|String
name|getOp
parameter_list|()
block|{
return|return
name|iOp
return|;
block|}
specifier|public
name|void
name|setOp
parameter_list|(
name|String
name|op
parameter_list|)
block|{
name|iOp
operator|=
name|op
expr_stmt|;
block|}
block|}
end_class

end_unit

