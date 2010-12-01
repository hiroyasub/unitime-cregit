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

begin_comment
comment|/**   * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|LastChangesForm
extends|extends
name|ActionForm
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|3633681949556250656L
decl_stmt|;
specifier|private
name|String
name|iOp
decl_stmt|;
specifier|private
name|int
name|iN
decl_stmt|;
specifier|private
name|Long
name|iDepartmentId
decl_stmt|,
name|iSubjAreaId
decl_stmt|,
name|iManagerId
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
name|iOp
operator|=
literal|null
expr_stmt|;
name|iN
operator|=
literal|100
expr_stmt|;
name|iDepartmentId
operator|=
operator|new
name|Long
argument_list|(
operator|-
literal|1
argument_list|)
expr_stmt|;
name|iSubjAreaId
operator|=
operator|new
name|Long
argument_list|(
operator|-
literal|1
argument_list|)
expr_stmt|;
name|iManagerId
operator|=
operator|new
name|Long
argument_list|(
operator|-
literal|1
argument_list|)
expr_stmt|;
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
specifier|public
name|int
name|getN
parameter_list|()
block|{
return|return
name|iN
return|;
block|}
specifier|public
name|void
name|setN
parameter_list|(
name|int
name|n
parameter_list|)
block|{
name|iN
operator|=
name|n
expr_stmt|;
block|}
specifier|public
name|Long
name|getDepartmentId
parameter_list|()
block|{
return|return
name|iDepartmentId
return|;
block|}
specifier|public
name|void
name|setDepartmentId
parameter_list|(
name|Long
name|departmentId
parameter_list|)
block|{
name|iDepartmentId
operator|=
name|departmentId
expr_stmt|;
block|}
specifier|public
name|Long
name|getSubjAreaId
parameter_list|()
block|{
return|return
name|iSubjAreaId
return|;
block|}
specifier|public
name|void
name|setSubjAreaId
parameter_list|(
name|Long
name|subjAreaId
parameter_list|)
block|{
name|iSubjAreaId
operator|=
name|subjAreaId
expr_stmt|;
block|}
specifier|public
name|Long
name|getManagerId
parameter_list|()
block|{
return|return
name|iManagerId
return|;
block|}
specifier|public
name|void
name|setManagerId
parameter_list|(
name|Long
name|managerId
parameter_list|)
block|{
name|iManagerId
operator|=
name|managerId
expr_stmt|;
block|}
specifier|public
name|void
name|load
parameter_list|(
name|HttpServletRequest
name|request
parameter_list|)
block|{
name|Integer
name|n
init|=
operator|(
name|Integer
operator|)
name|request
operator|.
name|getSession
argument_list|()
operator|.
name|getAttribute
argument_list|(
literal|"LastChanges.N"
argument_list|)
decl_stmt|;
name|setN
argument_list|(
name|n
operator|==
literal|null
condition|?
literal|100
else|:
name|n
operator|.
name|intValue
argument_list|()
argument_list|)
expr_stmt|;
name|setDepartmentId
argument_list|(
operator|(
name|Long
operator|)
name|request
operator|.
name|getSession
argument_list|()
operator|.
name|getAttribute
argument_list|(
literal|"LastChanges.DepartmentId"
argument_list|)
argument_list|)
expr_stmt|;
name|setSubjAreaId
argument_list|(
operator|(
name|Long
operator|)
name|request
operator|.
name|getSession
argument_list|()
operator|.
name|getAttribute
argument_list|(
literal|"LastChanges.SubjAreaId"
argument_list|)
argument_list|)
expr_stmt|;
name|setManagerId
argument_list|(
operator|(
name|Long
operator|)
name|request
operator|.
name|getSession
argument_list|()
operator|.
name|getAttribute
argument_list|(
literal|"LastChanges.ManagerId"
argument_list|)
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|save
parameter_list|(
name|HttpServletRequest
name|request
parameter_list|)
block|{
name|request
operator|.
name|getSession
argument_list|()
operator|.
name|setAttribute
argument_list|(
literal|"LastChanges.N"
argument_list|,
operator|new
name|Integer
argument_list|(
name|getN
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|getDepartmentId
argument_list|()
operator|==
literal|null
condition|)
name|request
operator|.
name|getSession
argument_list|()
operator|.
name|removeAttribute
argument_list|(
literal|"LastChanges.DepartmentId"
argument_list|)
expr_stmt|;
else|else
name|request
operator|.
name|getSession
argument_list|()
operator|.
name|setAttribute
argument_list|(
literal|"LastChanges.DepartmentId"
argument_list|,
name|getDepartmentId
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|getSubjAreaId
argument_list|()
operator|==
literal|null
condition|)
name|request
operator|.
name|getSession
argument_list|()
operator|.
name|removeAttribute
argument_list|(
literal|"LastChanges.SubjAreaId"
argument_list|)
expr_stmt|;
else|else
name|request
operator|.
name|getSession
argument_list|()
operator|.
name|setAttribute
argument_list|(
literal|"LastChanges.SubjAreaId"
argument_list|,
name|getSubjAreaId
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|getManagerId
argument_list|()
operator|==
literal|null
condition|)
name|request
operator|.
name|getSession
argument_list|()
operator|.
name|removeAttribute
argument_list|(
literal|"LastChanges.ManagerId"
argument_list|)
expr_stmt|;
else|else
name|request
operator|.
name|getSession
argument_list|()
operator|.
name|setAttribute
argument_list|(
literal|"LastChanges.ManagerId"
argument_list|,
name|getManagerId
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

