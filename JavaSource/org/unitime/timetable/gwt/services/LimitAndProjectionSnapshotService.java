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
name|Date
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
name|LimitAndProjectionSnapshotException
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
name|RemoteService
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
name|RemoteServiceRelativePath
import|;
end_import

begin_comment
comment|/**  * @author Stephanie Schluttenhofer  */
end_comment

begin_interface
annotation|@
name|RemoteServiceRelativePath
argument_list|(
literal|"snapshot.gwt"
argument_list|)
specifier|public
interface|interface
name|LimitAndProjectionSnapshotService
extends|extends
name|RemoteService
block|{
specifier|public
name|Boolean
name|canTakeSnapshot
parameter_list|()
throws|throws
name|LimitAndProjectionSnapshotException
throws|,
name|PageAccessException
function_decl|;
specifier|public
name|Date
name|getCurrentSnapshotDate
parameter_list|()
throws|throws
name|LimitAndProjectionSnapshotException
throws|,
name|PageAccessException
function_decl|;
specifier|public
name|Date
name|takeSnapshot
parameter_list|()
throws|throws
name|LimitAndProjectionSnapshotException
throws|,
name|PageAccessException
function_decl|;
block|}
end_interface

end_unit

