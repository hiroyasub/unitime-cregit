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
name|server
package|;
end_package

begin_import
import|import
name|org
operator|.
name|unitime
operator|.
name|commons
operator|.
name|hibernate
operator|.
name|util
operator|.
name|HibernateUtil
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
name|admin
operator|.
name|ClearHibernateCache
operator|.
name|ClearHibernateCacheRequest
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
name|admin
operator|.
name|ClearHibernateCache
operator|.
name|ClearHibernateCacheResponse
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
name|GwtRpcImplements
argument_list|(
name|ClearHibernateCacheRequest
operator|.
name|class
argument_list|)
specifier|public
class|class
name|ClearHibernateCacheBackend
implements|implements
name|GwtRpcImplementation
argument_list|<
name|ClearHibernateCacheRequest
argument_list|,
name|ClearHibernateCacheResponse
argument_list|>
block|{
annotation|@
name|Override
specifier|public
name|ClearHibernateCacheResponse
name|execute
parameter_list|(
name|ClearHibernateCacheRequest
name|request
parameter_list|,
name|SessionContext
name|context
parameter_list|)
block|{
name|context
operator|.
name|checkPermission
argument_list|(
name|Right
operator|.
name|ClearHibernateCache
argument_list|)
expr_stmt|;
name|HibernateUtil
operator|.
name|clearCache
argument_list|()
expr_stmt|;
return|return
operator|new
name|ClearHibernateCacheResponse
argument_list|()
return|;
block|}
block|}
end_class

end_unit

