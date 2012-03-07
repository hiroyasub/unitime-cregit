begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.4 (University Timetabling Application)  * Copyright (C) 2012, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
end_comment

begin_package
package|package
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|events
package|;
end_package

begin_import
import|import
name|org
operator|.
name|unitime
operator|.
name|localization
operator|.
name|impl
operator|.
name|Localization
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
name|events
operator|.
name|UniTimeFilterBox
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
name|client
operator|.
name|events
operator|.
name|UniTimeFilterBox
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
name|gwt
operator|.
name|command
operator|.
name|server
operator|.
name|GwtRpcHelper
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
name|resources
operator|.
name|GwtMessages
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
name|PageAccessException
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
specifier|protected
specifier|static
name|GwtMessages
name|MESSAGES
init|=
name|Localization
operator|.
name|create
argument_list|(
name|GwtMessages
operator|.
name|class
argument_list|)
decl_stmt|;
annotation|@
name|Override
specifier|public
name|FilterRpcResponse
name|execute
parameter_list|(
name|FilterRpcRequest
name|request
parameter_list|,
name|GwtRpcHelper
name|helper
parameter_list|)
block|{
name|checkAuthorization
argument_list|(
name|request
argument_list|,
name|helper
argument_list|)
expr_stmt|;
if|if
condition|(
name|helper
operator|.
name|getUser
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|request
operator|.
name|addOption
argument_list|(
literal|"user"
argument_list|,
name|helper
operator|.
name|getUser
argument_list|()
operator|.
name|getId
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|helper
operator|.
name|getUser
argument_list|()
operator|.
name|getCurrentRole
argument_list|()
operator|!=
literal|null
condition|)
name|request
operator|.
name|addOption
argument_list|(
literal|"role"
argument_list|,
name|helper
operator|.
name|getUser
argument_list|()
operator|.
name|getCurrentRole
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|FilterRpcResponse
name|response
init|=
operator|new
name|FilterRpcResponse
argument_list|()
decl_stmt|;
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
parameter_list|)
function_decl|;
specifier|protected
name|void
name|checkAuthorization
parameter_list|(
name|FilterRpcRequest
name|request
parameter_list|,
name|GwtRpcHelper
name|helper
parameter_list|)
throws|throws
name|PageAccessException
block|{
if|if
condition|(
name|helper
operator|.
name|getUser
argument_list|()
operator|==
literal|null
condition|)
throw|throw
operator|new
name|PageAccessException
argument_list|(
name|MESSAGES
operator|.
name|authenticationRequired
argument_list|()
argument_list|)
throw|;
block|}
block|}
end_class

end_unit

