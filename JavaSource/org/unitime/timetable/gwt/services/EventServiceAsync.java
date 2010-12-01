begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.2 (University Timetabling Application)  * Copyright (C) 2010, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
end_comment

begin_package
package|package
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|gwt
operator|.
name|services
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|List
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
name|EventException
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
name|IdValueInterface
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
name|ResourceInterface
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
name|ResourceType
import|;
end_import

begin_import
import|import
name|com
operator|.
name|google
operator|.
name|gwt
operator|.
name|user
operator|.
name|client
operator|.
name|rpc
operator|.
name|AsyncCallback
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_interface
specifier|public
interface|interface
name|EventServiceAsync
block|{
specifier|public
name|void
name|findResource
parameter_list|(
name|String
name|session
parameter_list|,
name|ResourceType
name|type
parameter_list|,
name|String
name|name
parameter_list|,
name|AsyncCallback
argument_list|<
name|ResourceInterface
argument_list|>
name|callback
parameter_list|)
throws|throws
name|EventException
function_decl|;
specifier|public
name|void
name|findEvents
parameter_list|(
name|ResourceInterface
name|resource
parameter_list|,
name|AsyncCallback
argument_list|<
name|List
argument_list|<
name|EventInterface
argument_list|>
argument_list|>
name|callback
parameter_list|)
throws|throws
name|EventException
function_decl|;
specifier|public
name|void
name|findSessions
parameter_list|(
name|String
name|session
parameter_list|,
name|AsyncCallback
argument_list|<
name|List
argument_list|<
name|IdValueInterface
argument_list|>
argument_list|>
name|callback
parameter_list|)
throws|throws
name|EventException
function_decl|;
specifier|public
name|void
name|findResources
parameter_list|(
name|String
name|session
parameter_list|,
name|ResourceType
name|type
parameter_list|,
name|String
name|query
parameter_list|,
name|int
name|limit
parameter_list|,
name|AsyncCallback
argument_list|<
name|List
argument_list|<
name|ResourceInterface
argument_list|>
argument_list|>
name|callback
parameter_list|)
throws|throws
name|EventException
function_decl|;
specifier|public
name|void
name|canLookupPeople
parameter_list|(
name|AsyncCallback
argument_list|<
name|Boolean
argument_list|>
name|callback
parameter_list|)
throws|throws
name|EventException
function_decl|;
block|}
end_interface

end_unit

