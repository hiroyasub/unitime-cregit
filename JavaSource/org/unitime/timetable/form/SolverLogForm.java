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
name|solver
operator|.
name|ui
operator|.
name|LogInfo
import|;
end_import

begin_comment
comment|/**   * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|SolverLogForm
extends|extends
name|ActionForm
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
operator|-
literal|1547826903057198096L
decl_stmt|;
specifier|public
specifier|static
name|String
index|[]
name|sLevels
init|=
operator|new
name|String
index|[]
block|{
literal|"Trace"
block|,
literal|"Debug"
block|,
literal|"Progress"
block|,
literal|"Info"
block|,
literal|"Stage"
block|,
literal|"Warn"
block|,
literal|"Error"
block|,
literal|"Fatal"
block|}
decl_stmt|;
specifier|private
name|String
name|iLevel
decl_stmt|;
specifier|private
name|String
name|iOp
decl_stmt|;
specifier|private
name|LogInfo
index|[]
name|iLogInfo
init|=
literal|null
decl_stmt|;
specifier|private
name|String
index|[]
name|iOwnerName
init|=
literal|null
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
if|if
condition|(
name|iLevel
operator|==
literal|null
operator|||
name|iLevel
operator|.
name|trim
argument_list|()
operator|.
name|length
argument_list|()
operator|==
literal|0
condition|)
name|errors
operator|.
name|add
argument_list|(
literal|"level"
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
name|iLevel
operator|=
literal|null
expr_stmt|;
name|iOp
operator|=
literal|null
expr_stmt|;
name|iLogInfo
operator|=
literal|null
expr_stmt|;
name|iOwnerName
operator|=
literal|null
expr_stmt|;
block|}
specifier|public
name|String
name|getLevel
parameter_list|()
block|{
return|return
operator|(
name|iLevel
operator|==
literal|null
condition|?
literal|"Info"
else|:
name|iLevel
operator|)
return|;
block|}
specifier|public
name|String
name|getLevelNoDefault
parameter_list|()
block|{
return|return
name|iLevel
return|;
block|}
specifier|public
name|int
name|getLevelInt
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
name|sLevels
operator|.
name|length
condition|;
name|i
operator|++
control|)
if|if
condition|(
name|getLevel
argument_list|()
operator|.
name|equals
argument_list|(
name|sLevels
index|[
name|i
index|]
argument_list|)
condition|)
return|return
name|i
return|;
return|return
literal|0
return|;
block|}
specifier|public
name|void
name|setLevel
parameter_list|(
name|String
name|level
parameter_list|)
block|{
name|iLevel
operator|=
name|level
expr_stmt|;
block|}
specifier|public
name|String
index|[]
name|getLevels
parameter_list|()
block|{
return|return
name|sLevels
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
specifier|public
name|String
name|getLog
parameter_list|(
name|int
name|idx
parameter_list|)
block|{
if|if
condition|(
name|iLogInfo
operator|==
literal|null
operator|||
name|idx
operator|<
literal|0
operator|||
name|idx
operator|>=
name|iLogInfo
operator|.
name|length
operator|||
name|iLogInfo
index|[
name|idx
index|]
operator|==
literal|null
condition|)
return|return
literal|""
return|;
return|return
name|iLogInfo
index|[
name|idx
index|]
operator|.
name|getHtmlLog
argument_list|(
name|getLevelInt
argument_list|()
argument_list|,
literal|true
argument_list|)
return|;
block|}
specifier|public
name|String
index|[]
name|getOwnerNames
parameter_list|()
block|{
return|return
name|iOwnerName
return|;
block|}
specifier|public
name|void
name|setOwnerNames
parameter_list|(
name|String
index|[]
name|ownerName
parameter_list|)
block|{
name|iOwnerName
operator|=
name|ownerName
expr_stmt|;
block|}
specifier|public
name|int
name|getNrLogs
parameter_list|()
block|{
return|return
operator|(
name|iLogInfo
operator|==
literal|null
condition|?
literal|0
else|:
name|iLogInfo
operator|.
name|length
operator|)
return|;
block|}
specifier|public
name|void
name|setLogs
parameter_list|(
name|LogInfo
index|[]
name|logs
parameter_list|)
block|{
name|iLogInfo
operator|=
name|logs
expr_stmt|;
block|}
block|}
end_class

end_unit

