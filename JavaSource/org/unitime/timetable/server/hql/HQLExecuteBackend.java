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
name|ArrayList
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Collection
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
name|export
operator|.
name|Exporter
operator|.
name|Printer
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
name|export
operator|.
name|hql
operator|.
name|SavedHqlExportToCSV
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
name|GwtRpcException
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
name|HQLExecuteRpcRequest
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
name|Table
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
name|model
operator|.
name|SavedHQLParameter
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
name|SavedHQLDAO
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

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
annotation|@
name|GwtRpcImplements
argument_list|(
name|HQLExecuteRpcRequest
operator|.
name|class
argument_list|)
specifier|public
class|class
name|HQLExecuteBackend
implements|implements
name|GwtRpcImplementation
argument_list|<
name|HQLExecuteRpcRequest
argument_list|,
name|Table
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
name|Table
name|execute
parameter_list|(
name|HQLExecuteRpcRequest
name|request
parameter_list|,
name|SessionContext
name|context
parameter_list|)
block|{
try|try
block|{
specifier|final
name|Table
name|ret
init|=
operator|new
name|Table
argument_list|()
decl_stmt|;
name|Printer
name|out
init|=
operator|new
name|Printer
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|printLine
parameter_list|(
name|String
modifier|...
name|fields
parameter_list|)
throws|throws
name|IOException
block|{
name|ret
operator|.
name|add
argument_list|(
name|fields
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|printHeader
parameter_list|(
name|String
modifier|...
name|fields
parameter_list|)
throws|throws
name|IOException
block|{
name|ret
operator|.
name|add
argument_list|(
name|fields
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|hideColumn
parameter_list|(
name|int
name|col
parameter_list|)
block|{
block|}
annotation|@
name|Override
specifier|public
name|String
name|getContentType
parameter_list|()
block|{
return|return
literal|null
return|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|flush
parameter_list|()
throws|throws
name|IOException
block|{
block|}
annotation|@
name|Override
specifier|public
name|void
name|close
parameter_list|()
throws|throws
name|IOException
block|{
block|}
block|}
decl_stmt|;
name|String
name|query
init|=
literal|null
decl_stmt|;
name|Collection
argument_list|<
name|SavedHQLParameter
argument_list|>
name|parameters
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|request
operator|.
name|getQuery
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|query
operator|=
name|request
operator|.
name|getQuery
argument_list|()
operator|.
name|getQuery
argument_list|()
expr_stmt|;
name|parameters
operator|=
operator|new
name|ArrayList
argument_list|<
name|SavedHQLParameter
argument_list|>
argument_list|()
expr_stmt|;
if|if
condition|(
name|request
operator|.
name|getQuery
argument_list|()
operator|.
name|hasParameters
argument_list|()
condition|)
block|{
for|for
control|(
name|SavedHQLInterface
operator|.
name|Parameter
name|p
range|:
name|request
operator|.
name|getQuery
argument_list|()
operator|.
name|getParameters
argument_list|()
control|)
block|{
name|SavedHQLParameter
name|parameter
init|=
operator|new
name|SavedHQLParameter
argument_list|()
decl_stmt|;
name|parameter
operator|.
name|setDefaultValue
argument_list|(
name|p
operator|.
name|getDefaultValue
argument_list|()
argument_list|)
expr_stmt|;
name|parameter
operator|.
name|setLabel
argument_list|(
name|p
operator|.
name|getLabel
argument_list|()
argument_list|)
expr_stmt|;
name|parameter
operator|.
name|setName
argument_list|(
name|p
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
name|parameter
operator|.
name|setType
argument_list|(
name|p
operator|.
name|getType
argument_list|()
argument_list|)
expr_stmt|;
name|parameters
operator|.
name|add
argument_list|(
name|parameter
argument_list|)
expr_stmt|;
block|}
block|}
block|}
else|else
block|{
name|SavedHQL
name|hql
init|=
name|SavedHQLDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|get
argument_list|(
name|request
operator|.
name|getQuery
argument_list|()
operator|.
name|getId
argument_list|()
argument_list|)
decl_stmt|;
name|query
operator|=
name|hql
operator|.
name|getQuery
argument_list|()
expr_stmt|;
name|parameters
operator|=
name|hql
operator|.
name|getParameters
argument_list|()
expr_stmt|;
block|}
name|SavedHqlExportToCSV
operator|.
name|execute
argument_list|(
name|sessionContext
operator|.
name|getUser
argument_list|()
argument_list|,
name|out
argument_list|,
name|query
argument_list|,
name|request
operator|.
name|getOptions
argument_list|()
argument_list|,
name|request
operator|.
name|getFromRow
argument_list|()
argument_list|,
name|request
operator|.
name|getMaxRows
argument_list|()
argument_list|,
name|parameters
argument_list|)
expr_stmt|;
return|return
name|ret
return|;
block|}
catch|catch
parameter_list|(
name|PageAccessException
name|e
parameter_list|)
block|{
throw|throw
name|e
throw|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|GwtRpcException
argument_list|(
name|MESSAGES
operator|.
name|failedExecution
argument_list|(
name|e
operator|.
name|getMessage
argument_list|()
operator|+
operator|(
name|e
operator|.
name|getCause
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
literal|" ("
operator|+
name|e
operator|.
name|getCause
argument_list|()
operator|.
name|getMessage
argument_list|()
operator|+
literal|")"
operator|)
argument_list|)
argument_list|)
throw|;
block|}
block|}
block|}
end_class

end_unit

