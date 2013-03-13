begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.4 (University Timetabling Application)  * Copyright (C) 2013, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
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
name|shared
operator|.
name|EventInterface
operator|.
name|FilterRpcRequest
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
name|shared
operator|.
name|EventInterface
operator|.
name|FilterRpcResponse
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

begin_class
specifier|public
specifier|abstract
class|class
name|FilterBoxBackend
implements|implements
name|GwtRpcImplementation
argument_list|<
name|FilterRpcRequest
argument_list|,
name|FilterRpcResponse
argument_list|>
block|{
annotation|@
name|Override
specifier|public
name|FilterRpcResponse
name|execute
parameter_list|(
name|FilterRpcRequest
name|request
parameter_list|,
name|SessionContext
name|context
parameter_list|)
block|{
name|FilterRpcResponse
name|response
init|=
operator|new
name|FilterRpcResponse
argument_list|()
decl_stmt|;
if|if
condition|(
name|context
operator|.
name|isAuthenticated
argument_list|()
condition|)
name|request
operator|.
name|setOption
argument_list|(
literal|"user"
argument_list|,
name|context
operator|.
name|getUser
argument_list|()
operator|.
name|getExternalUserId
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|request
operator|.
name|getSessionId
argument_list|()
operator|==
literal|null
operator|&&
name|context
operator|.
name|isAuthenticated
argument_list|()
condition|)
name|request
operator|.
name|setSessionId
argument_list|(
name|context
operator|.
name|getUser
argument_list|()
operator|.
name|getCurrentAcademicSessionId
argument_list|()
argument_list|)
expr_stmt|;
switch|switch
condition|(
name|request
operator|.
name|getCommand
argument_list|()
condition|)
block|{
case|case
name|LOAD
case|:
name|load
argument_list|(
name|request
argument_list|,
name|response
argument_list|,
name|context
argument_list|)
expr_stmt|;
break|break;
case|case
name|SUGGESTIONS
case|:
name|suggestions
argument_list|(
name|request
argument_list|,
name|response
argument_list|,
name|context
argument_list|)
expr_stmt|;
break|break;
case|case
name|ENUMERATE
case|:
name|enumarate
argument_list|(
name|request
argument_list|,
name|response
argument_list|,
name|context
argument_list|)
expr_stmt|;
break|break;
block|}
return|return
name|response
return|;
block|}
specifier|public
specifier|abstract
name|void
name|load
parameter_list|(
name|FilterRpcRequest
name|request
parameter_list|,
name|FilterRpcResponse
name|response
parameter_list|,
name|SessionContext
name|context
parameter_list|)
function_decl|;
specifier|public
specifier|abstract
name|void
name|suggestions
parameter_list|(
name|FilterRpcRequest
name|request
parameter_list|,
name|FilterRpcResponse
name|response
parameter_list|,
name|SessionContext
name|context
parameter_list|)
function_decl|;
specifier|public
specifier|abstract
name|void
name|enumarate
parameter_list|(
name|FilterRpcRequest
name|request
parameter_list|,
name|FilterRpcResponse
name|response
parameter_list|,
name|SessionContext
name|context
parameter_list|)
function_decl|;
block|}
end_class

end_unit

