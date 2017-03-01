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
operator|.
name|hql
package|;
end_package

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
name|springframework
operator|.
name|security
operator|.
name|access
operator|.
name|prepost
operator|.
name|PreAuthorize
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
name|client
operator|.
name|GwtRpcResponseList
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
name|gwt
operator|.
name|shared
operator|.
name|SavedHQLInterface
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
name|SavedHQLInterface
operator|.
name|HQLQueriesRpcRequest
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
name|SavedHQLInterface
operator|.
name|Query
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
name|SavedHQL
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
name|HQLQueriesRpcRequest
operator|.
name|class
argument_list|)
specifier|public
class|class
name|HQLQueriesBackend
implements|implements
name|GwtRpcImplementation
argument_list|<
name|HQLQueriesRpcRequest
argument_list|,
name|GwtRpcResponseList
argument_list|<
name|Query
argument_list|>
argument_list|>
block|{
annotation|@
name|Autowired
specifier|private
name|SessionContext
name|sessionContext
decl_stmt|;
annotation|@
name|Override
annotation|@
name|PreAuthorize
argument_list|(
literal|"checkPermission('HQLReports')"
argument_list|)
specifier|public
name|GwtRpcResponseList
argument_list|<
name|Query
argument_list|>
name|execute
parameter_list|(
name|HQLQueriesRpcRequest
name|request
parameter_list|,
name|SessionContext
name|context
parameter_list|)
block|{
name|SavedHQL
operator|.
name|Flag
name|ap
init|=
name|getAppearanceFlag
argument_list|(
name|request
operator|.
name|getAppearance
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|ap
operator|.
name|getPermission
argument_list|()
operator|!=
literal|null
condition|)
name|sessionContext
operator|.
name|checkPermission
argument_list|(
name|ap
operator|.
name|getPermission
argument_list|()
argument_list|)
expr_stmt|;
name|GwtRpcResponseList
argument_list|<
name|SavedHQLInterface
operator|.
name|Query
argument_list|>
name|ret
init|=
operator|new
name|GwtRpcResponseList
argument_list|<
name|SavedHQLInterface
operator|.
name|Query
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|SavedHQL
name|hql
range|:
name|SavedHQL
operator|.
name|listAll
argument_list|(
literal|null
argument_list|,
name|ap
argument_list|,
name|sessionContext
operator|.
name|hasPermission
argument_list|(
name|Right
operator|.
name|HQLReportsAdminOnly
argument_list|)
argument_list|)
control|)
block|{
name|SavedHQLInterface
operator|.
name|Query
name|query
init|=
operator|new
name|SavedHQLInterface
operator|.
name|Query
argument_list|()
decl_stmt|;
name|query
operator|.
name|setName
argument_list|(
name|hql
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
name|query
operator|.
name|setDescription
argument_list|(
name|hql
operator|.
name|getDescription
argument_list|()
argument_list|)
expr_stmt|;
name|query
operator|.
name|setQuery
argument_list|(
name|hql
operator|.
name|getQuery
argument_list|()
argument_list|)
expr_stmt|;
name|query
operator|.
name|setFlags
argument_list|(
name|hql
operator|.
name|getType
argument_list|()
argument_list|)
expr_stmt|;
name|query
operator|.
name|setId
argument_list|(
name|hql
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
name|ret
operator|.
name|add
argument_list|(
name|query
argument_list|)
expr_stmt|;
block|}
return|return
name|ret
return|;
block|}
specifier|private
name|SavedHQL
operator|.
name|Flag
name|getAppearanceFlag
parameter_list|(
name|String
name|appearance
parameter_list|)
block|{
for|for
control|(
name|SavedHQL
operator|.
name|Flag
name|flag
range|:
name|SavedHQL
operator|.
name|Flag
operator|.
name|values
argument_list|()
control|)
if|if
condition|(
name|flag
operator|.
name|getAppearance
argument_list|()
operator|!=
literal|null
operator|&&
name|flag
operator|.
name|getAppearance
argument_list|()
operator|.
name|equalsIgnoreCase
argument_list|(
name|appearance
argument_list|)
condition|)
return|return
name|flag
return|;
return|return
name|SavedHQL
operator|.
name|Flag
operator|.
name|APPEARANCE_COURSES
return|;
block|}
block|}
end_class

end_unit

