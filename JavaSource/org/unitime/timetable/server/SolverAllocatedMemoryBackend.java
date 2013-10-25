begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.5 (University Timetabling Application)  * Copyright (C) 2013, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
end_comment

begin_package
package|package
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|server
package|;
end_package

begin_import
import|import
name|java
operator|.
name|text
operator|.
name|DecimalFormat
import|;
end_import

begin_import
import|import
name|org
operator|.
name|springframework
operator|.
name|beans
operator|.
name|factory
operator|.
name|annotation
operator|.
name|Autowired
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
name|client
operator|.
name|solver
operator|.
name|SolverAllocatedMemory
operator|.
name|SolverAllocatedMemoryRpcRequest
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
name|client
operator|.
name|solver
operator|.
name|SolverAllocatedMemory
operator|.
name|SolverAllocatedMemoryRpcResponse
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
name|command
operator|.
name|server
operator|.
name|GwtRpcImplementation
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
name|command
operator|.
name|server
operator|.
name|GwtRpcImplements
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
name|service
operator|.
name|SolverServerService
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
annotation|@
name|GwtRpcImplements
argument_list|(
name|SolverAllocatedMemoryRpcRequest
operator|.
name|class
argument_list|)
specifier|public
class|class
name|SolverAllocatedMemoryBackend
implements|implements
name|GwtRpcImplementation
argument_list|<
name|SolverAllocatedMemoryRpcRequest
argument_list|,
name|SolverAllocatedMemoryRpcResponse
argument_list|>
block|{
annotation|@
name|Autowired
name|SolverServerService
name|solverServerService
decl_stmt|;
annotation|@
name|Override
specifier|public
name|SolverAllocatedMemoryRpcResponse
name|execute
parameter_list|(
name|SolverAllocatedMemoryRpcRequest
name|request
parameter_list|,
name|SessionContext
name|context
parameter_list|)
block|{
name|long
name|memUsage
init|=
literal|0
decl_stmt|;
switch|switch
condition|(
name|request
operator|.
name|getSolverId
argument_list|()
operator|.
name|charAt
argument_list|(
literal|0
argument_list|)
condition|)
block|{
case|case
literal|'C'
case|:
name|memUsage
operator|=
name|solverServerService
operator|.
name|getCourseSolverContainer
argument_list|()
operator|.
name|getMemUsage
argument_list|(
name|request
operator|.
name|getSolverId
argument_list|()
operator|.
name|substring
argument_list|(
literal|1
argument_list|)
argument_list|)
expr_stmt|;
break|break;
case|case
literal|'X'
case|:
name|memUsage
operator|=
name|solverServerService
operator|.
name|getExamSolverContainer
argument_list|()
operator|.
name|getMemUsage
argument_list|(
name|request
operator|.
name|getSolverId
argument_list|()
operator|.
name|substring
argument_list|(
literal|1
argument_list|)
argument_list|)
expr_stmt|;
break|break;
case|case
literal|'S'
case|:
name|memUsage
operator|=
name|solverServerService
operator|.
name|getStudentSolverContainer
argument_list|()
operator|.
name|getMemUsage
argument_list|(
name|request
operator|.
name|getSolverId
argument_list|()
operator|.
name|substring
argument_list|(
literal|1
argument_list|)
argument_list|)
expr_stmt|;
break|break;
case|case
literal|'O'
case|:
if|if
condition|(
name|request
operator|.
name|getSolverId
argument_list|()
operator|.
name|indexOf
argument_list|(
literal|':'
argument_list|)
operator|>=
literal|0
condition|)
block|{
name|String
index|[]
name|idHost
init|=
name|request
operator|.
name|getSolverId
argument_list|()
operator|.
name|substring
argument_list|(
literal|1
argument_list|)
operator|.
name|split
argument_list|(
literal|":"
argument_list|)
decl_stmt|;
name|memUsage
operator|=
name|solverServerService
operator|.
name|getServer
argument_list|(
name|idHost
index|[
literal|0
index|]
argument_list|)
operator|.
name|getOnlineStudentSchedulingContainer
argument_list|()
operator|.
name|getSolver
argument_list|(
name|idHost
index|[
literal|1
index|]
argument_list|)
operator|.
name|getMemUsage
argument_list|()
expr_stmt|;
block|}
else|else
block|{
name|memUsage
operator|=
name|solverServerService
operator|.
name|getOnlineStudentSchedulingContainer
argument_list|()
operator|.
name|getMemUsage
argument_list|(
name|request
operator|.
name|getSolverId
argument_list|()
operator|.
name|substring
argument_list|(
literal|1
argument_list|)
argument_list|)
expr_stmt|;
block|}
break|break;
block|}
if|if
condition|(
name|memUsage
operator|==
literal|0
condition|)
return|return
literal|null
return|;
else|else
return|return
operator|new
name|SolverAllocatedMemoryRpcResponse
argument_list|(
operator|new
name|DecimalFormat
argument_list|(
literal|"0.00"
argument_list|)
operator|.
name|format
argument_list|(
name|memUsage
operator|/
literal|1048576.0
argument_list|)
operator|+
literal|"M"
argument_list|)
return|;
block|}
block|}
end_class

end_unit

