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
name|api
operator|.
name|connectors
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
name|Enumeration
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Iterator
import|;
end_import

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
name|springframework
operator|.
name|stereotype
operator|.
name|Service
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
name|api
operator|.
name|ApiConnector
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
name|api
operator|.
name|ApiHelper
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
name|events
operator|.
name|EventLookupBackend
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
name|events
operator|.
name|EventAction
operator|.
name|EventContext
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
name|EventFilterRpcRequest
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
name|EventLookupRpcRequest
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
name|EventType
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
name|RoomFilterRpcRequest
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
name|model
operator|.
name|Session
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
name|model
operator|.
name|dao
operator|.
name|SessionDAO
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
name|rights
operator|.
name|Right
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
annotation|@
name|Service
argument_list|(
literal|"/api/events"
argument_list|)
specifier|public
class|class
name|EventsConnector
extends|extends
name|ApiConnector
block|{
annotation|@
name|Override
specifier|public
name|void
name|doGet
parameter_list|(
name|ApiHelper
name|helper
parameter_list|)
throws|throws
name|IOException
block|{
name|Long
name|sessionId
init|=
name|helper
operator|.
name|getAcademicSessionId
argument_list|()
decl_stmt|;
if|if
condition|(
name|sessionId
operator|==
literal|null
condition|)
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"Academic session not provided, please set the term parameter."
argument_list|)
throw|;
name|Session
name|session
init|=
name|SessionDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|get
argument_list|(
name|sessionId
argument_list|)
decl_stmt|;
if|if
condition|(
name|session
operator|==
literal|null
condition|)
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"Given academic session no longer exists."
argument_list|)
throw|;
name|helper
operator|.
name|getSessionContext
argument_list|()
operator|.
name|checkPermission
argument_list|(
name|session
argument_list|,
name|Right
operator|.
name|ApiRetrieveEvents
argument_list|)
expr_stmt|;
name|EventLookupRpcRequest
name|request
init|=
operator|new
name|EventLookupRpcRequest
argument_list|()
decl_stmt|;
name|request
operator|.
name|setSessionId
argument_list|(
name|sessionId
argument_list|)
expr_stmt|;
name|String
name|id
init|=
name|helper
operator|.
name|getParameter
argument_list|(
literal|"id"
argument_list|)
decl_stmt|;
if|if
condition|(
name|id
operator|!=
literal|null
condition|)
name|request
operator|.
name|setResourceId
argument_list|(
name|Long
operator|.
name|valueOf
argument_list|(
name|id
argument_list|)
argument_list|)
expr_stmt|;
name|String
name|ext
init|=
name|helper
operator|.
name|getParameter
argument_list|(
literal|"ext"
argument_list|)
decl_stmt|;
if|if
condition|(
name|ext
operator|!=
literal|null
condition|)
name|request
operator|.
name|setResourceExternalId
argument_list|(
name|ext
argument_list|)
expr_stmt|;
name|String
name|type
init|=
name|helper
operator|.
name|getParameter
argument_list|(
literal|"type"
argument_list|)
decl_stmt|;
if|if
condition|(
name|type
operator|==
literal|null
condition|)
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"Resource type not provided, please set the type parameter."
argument_list|)
throw|;
name|request
operator|.
name|setResourceType
argument_list|(
name|ResourceType
operator|.
name|valueOf
argument_list|(
name|type
operator|.
name|toUpperCase
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|EventFilterRpcRequest
name|eventFilter
init|=
operator|new
name|EventFilterRpcRequest
argument_list|()
decl_stmt|;
name|eventFilter
operator|.
name|setSessionId
argument_list|(
name|sessionId
argument_list|)
expr_stmt|;
name|request
operator|.
name|setEventFilter
argument_list|(
name|eventFilter
argument_list|)
expr_stmt|;
name|RoomFilterRpcRequest
name|roomFilter
init|=
operator|new
name|RoomFilterRpcRequest
argument_list|()
decl_stmt|;
name|roomFilter
operator|.
name|setSessionId
argument_list|(
name|sessionId
argument_list|)
expr_stmt|;
for|for
control|(
name|Enumeration
argument_list|<
name|String
argument_list|>
name|e
init|=
name|helper
operator|.
name|getParameterNames
argument_list|()
init|;
name|e
operator|.
name|hasMoreElements
argument_list|()
condition|;
control|)
block|{
name|String
name|command
init|=
name|e
operator|.
name|nextElement
argument_list|()
decl_stmt|;
if|if
condition|(
name|command
operator|.
name|equals
argument_list|(
literal|"e:text"
argument_list|)
condition|)
block|{
name|eventFilter
operator|.
name|setText
argument_list|(
name|helper
operator|.
name|getParameter
argument_list|(
literal|"e:text"
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|command
operator|.
name|startsWith
argument_list|(
literal|"e:"
argument_list|)
condition|)
block|{
for|for
control|(
name|String
name|value
range|:
name|helper
operator|.
name|getParameterValues
argument_list|(
name|command
argument_list|)
control|)
name|eventFilter
operator|.
name|addOption
argument_list|(
name|command
operator|.
name|substring
argument_list|(
literal|2
argument_list|)
argument_list|,
name|value
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|command
operator|.
name|equals
argument_list|(
literal|"r:text"
argument_list|)
condition|)
block|{
name|roomFilter
operator|.
name|setText
argument_list|(
name|helper
operator|.
name|getParameter
argument_list|(
literal|"r:text"
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|command
operator|.
name|startsWith
argument_list|(
literal|"r:"
argument_list|)
condition|)
block|{
for|for
control|(
name|String
name|value
range|:
name|helper
operator|.
name|getParameterValues
argument_list|(
name|command
argument_list|)
control|)
name|roomFilter
operator|.
name|addOption
argument_list|(
name|command
operator|.
name|substring
argument_list|(
literal|2
argument_list|)
argument_list|,
name|value
argument_list|)
expr_stmt|;
block|}
block|}
name|request
operator|.
name|setRoomFilter
argument_list|(
name|roomFilter
argument_list|)
expr_stmt|;
name|EventContext
name|context
init|=
operator|new
name|EventContext
argument_list|(
name|helper
operator|.
name|getSessionContext
argument_list|()
argument_list|,
name|helper
operator|.
name|getSessionContext
argument_list|()
operator|.
name|getUser
argument_list|()
argument_list|,
name|sessionId
argument_list|)
decl_stmt|;
name|List
argument_list|<
name|EventInterface
argument_list|>
name|events
init|=
operator|new
name|EventLookupBackend
argument_list|()
operator|.
name|findEvents
argument_list|(
name|request
argument_list|,
name|context
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
literal|"1"
operator|.
name|equals
argument_list|(
name|helper
operator|.
name|getParameter
argument_list|(
literal|"ua"
argument_list|)
argument_list|)
condition|)
block|{
for|for
control|(
name|Iterator
argument_list|<
name|EventInterface
argument_list|>
name|i
init|=
name|events
operator|.
name|iterator
argument_list|()
init|;
name|i
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|EventInterface
name|event
init|=
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|event
operator|.
name|getType
argument_list|()
operator|==
name|EventType
operator|.
name|Unavailabile
condition|)
name|i
operator|.
name|remove
argument_list|()
expr_stmt|;
block|}
block|}
name|helper
operator|.
name|setResponse
argument_list|(
name|events
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

