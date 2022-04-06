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
name|classinstructors
package|;
end_package

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
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|logging
operator|.
name|Log
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|logging
operator|.
name|LogFactory
import|;
end_import

begin_import
import|import
name|org
operator|.
name|hibernate
operator|.
name|Transaction
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
name|context
operator|.
name|ApplicationContext
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
name|AccessDeniedException
import|;
end_import

begin_import
import|import
name|org
operator|.
name|unitime
operator|.
name|commons
operator|.
name|Debug
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
name|shared
operator|.
name|AssignClassInstructorsInterface
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
name|AssignClassInstructorsInterface
operator|.
name|PageName
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
name|AssignClassInstructorsInterface
operator|.
name|Record
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
name|model
operator|.
name|dao
operator|.
name|_RootDAO
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
comment|/**  * @author Tomas Muller, Stephanie Schluttenhofer  */
end_comment

begin_class
specifier|public
class|class
name|AssignClassInstructorsBackend
block|{
specifier|private
specifier|static
name|Log
name|sLog
init|=
name|LogFactory
operator|.
name|getLog
argument_list|(
name|AssignClassInstructorsBackend
operator|.
name|class
argument_list|)
decl_stmt|;
annotation|@
name|GwtRpcImplements
argument_list|(
name|AssignClassInstructorsInterface
operator|.
name|GetPageNameRpcRequest
operator|.
name|class
argument_list|)
specifier|public
specifier|static
class|class
name|PageNameBackend
implements|implements
name|GwtRpcImplementation
argument_list|<
name|AssignClassInstructorsInterface
operator|.
name|GetPageNameRpcRequest
argument_list|,
name|AssignClassInstructorsInterface
operator|.
name|PageName
argument_list|>
block|{
annotation|@
name|Autowired
name|ApplicationContext
name|applicationContext
decl_stmt|;
annotation|@
name|Override
specifier|public
name|PageName
name|execute
parameter_list|(
name|AssignClassInstructorsInterface
operator|.
name|GetPageNameRpcRequest
name|request
parameter_list|,
name|SessionContext
name|context
parameter_list|)
block|{
name|Debug
operator|.
name|info
argument_list|(
literal|"In PageName execute(AssignClassInstructorsInterface.GetPageNameRpcRequest request"
argument_list|)
expr_stmt|;
return|return
name|getTable
argument_list|(
name|applicationContext
argument_list|)
operator|.
name|name
argument_list|()
return|;
block|}
block|}
annotation|@
name|GwtRpcImplements
argument_list|(
name|AssignClassInstructorsInterface
operator|.
name|LoadDataRpcRequest
operator|.
name|class
argument_list|)
specifier|public
specifier|static
class|class
name|LoadDataBackend
implements|implements
name|GwtRpcImplementation
argument_list|<
name|AssignClassInstructorsInterface
operator|.
name|LoadDataRpcRequest
argument_list|,
name|AssignClassInstructorsInterface
argument_list|>
block|{
annotation|@
name|Autowired
name|ApplicationContext
name|applicationContext
decl_stmt|;
annotation|@
name|Override
specifier|public
name|AssignClassInstructorsInterface
name|execute
parameter_list|(
name|AssignClassInstructorsInterface
operator|.
name|LoadDataRpcRequest
name|request
parameter_list|,
name|SessionContext
name|context
parameter_list|)
block|{
annotation|@
name|SuppressWarnings
argument_list|(
literal|"rawtypes"
argument_list|)
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
init|=
operator|new
name|_RootDAO
argument_list|()
operator|.
name|getSession
argument_list|()
decl_stmt|;
name|Transaction
name|tx
init|=
literal|null
decl_stmt|;
try|try
block|{
name|tx
operator|=
name|hibSession
operator|.
name|beginTransaction
argument_list|()
expr_stmt|;
name|AssignClassInstructorsInterface
name|data
init|=
literal|null
decl_stmt|;
name|AssignClassInstructorsTable
name|at
init|=
name|getTable
argument_list|(
name|applicationContext
argument_list|)
decl_stmt|;
name|data
operator|=
name|at
operator|.
name|load
argument_list|(
name|request
operator|.
name|getConfigIdStr
argument_list|()
argument_list|,
name|context
argument_list|,
name|hibSession
argument_list|)
expr_stmt|;
name|hibSession
operator|.
name|flush
argument_list|()
expr_stmt|;
name|tx
operator|.
name|commit
argument_list|()
expr_stmt|;
name|tx
operator|=
literal|null
expr_stmt|;
return|return
name|data
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
name|GwtRpcException
name|e
parameter_list|)
block|{
throw|throw
name|e
throw|;
block|}
catch|catch
parameter_list|(
name|AccessDeniedException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|PageAccessException
argument_list|(
name|e
operator|.
name|getMessage
argument_list|()
argument_list|,
name|e
argument_list|)
throw|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|sLog
operator|.
name|error
argument_list|(
name|e
operator|.
name|getMessage
argument_list|()
argument_list|,
name|e
argument_list|)
expr_stmt|;
throw|throw
operator|new
name|GwtRpcException
argument_list|(
name|e
operator|.
name|getMessage
argument_list|()
argument_list|,
name|e
argument_list|)
throw|;
block|}
finally|finally
block|{
try|try
block|{
if|if
condition|(
name|tx
operator|!=
literal|null
operator|&&
name|tx
operator|.
name|isActive
argument_list|()
condition|)
block|{
name|tx
operator|.
name|rollback
argument_list|()
expr_stmt|;
block|}
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
block|}
block|}
block|}
block|}
annotation|@
name|GwtRpcImplements
argument_list|(
name|AssignClassInstructorsInterface
operator|.
name|SaveDataRpcRequest
operator|.
name|class
argument_list|)
specifier|public
specifier|static
class|class
name|SaveDataBackend
implements|implements
name|GwtRpcImplementation
argument_list|<
name|AssignClassInstructorsInterface
operator|.
name|SaveDataRpcRequest
argument_list|,
name|AssignClassInstructorsInterface
argument_list|>
block|{
annotation|@
name|Autowired
name|ApplicationContext
name|applicationContext
decl_stmt|;
annotation|@
name|Override
specifier|public
name|AssignClassInstructorsInterface
name|execute
parameter_list|(
name|AssignClassInstructorsInterface
operator|.
name|SaveDataRpcRequest
name|request
parameter_list|,
name|SessionContext
name|context
parameter_list|)
block|{
name|Debug
operator|.
name|info
argument_list|(
literal|"In AssignClassInstructorsInterface execute(AssignClassInstructorsInterface.SaveDataRpcRequest request, "
argument_list|)
expr_stmt|;
annotation|@
name|SuppressWarnings
argument_list|(
literal|"rawtypes"
argument_list|)
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
init|=
operator|new
name|_RootDAO
argument_list|()
operator|.
name|getSession
argument_list|()
decl_stmt|;
name|Transaction
name|tx
init|=
literal|null
decl_stmt|;
try|try
block|{
name|tx
operator|=
name|hibSession
operator|.
name|beginTransaction
argument_list|()
expr_stmt|;
name|AssignClassInstructorsInterface
name|data
init|=
name|request
operator|.
name|getData
argument_list|()
decl_stmt|;
name|AssignClassInstructorsTable
name|at
init|=
name|getTable
argument_list|(
name|applicationContext
argument_list|)
decl_stmt|;
name|at
operator|.
name|save
argument_list|(
name|request
operator|.
name|getData
argument_list|()
argument_list|,
name|context
argument_list|,
name|hibSession
argument_list|)
expr_stmt|;
if|if
condition|(
name|data
operator|.
name|isSaveSuccessful
argument_list|()
condition|)
block|{
name|hibSession
operator|.
name|flush
argument_list|()
expr_stmt|;
name|tx
operator|.
name|commit
argument_list|()
expr_stmt|;
name|tx
operator|=
literal|null
expr_stmt|;
for|for
control|(
name|Iterator
argument_list|<
name|Record
argument_list|>
name|i
init|=
name|data
operator|.
name|getRecords
argument_list|()
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
if|if
condition|(
name|i
operator|.
name|next
argument_list|()
operator|.
name|getUniqueId
argument_list|()
operator|==
literal|null
condition|)
name|i
operator|.
name|remove
argument_list|()
expr_stmt|;
block|}
return|return
name|data
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
name|GwtRpcException
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
name|sLog
operator|.
name|error
argument_list|(
name|e
operator|.
name|getMessage
argument_list|()
argument_list|,
name|e
argument_list|)
expr_stmt|;
throw|throw
operator|new
name|GwtRpcException
argument_list|(
name|e
operator|.
name|getMessage
argument_list|()
argument_list|,
name|e
argument_list|)
throw|;
block|}
finally|finally
block|{
try|try
block|{
if|if
condition|(
name|tx
operator|!=
literal|null
operator|&&
name|tx
operator|.
name|isActive
argument_list|()
condition|)
block|{
name|tx
operator|.
name|rollback
argument_list|()
expr_stmt|;
block|}
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
block|}
block|}
block|}
block|}
annotation|@
name|GwtRpcImplements
argument_list|(
name|AssignClassInstructorsInterface
operator|.
name|SaveDataGoToPreviousRpcRequest
operator|.
name|class
argument_list|)
specifier|public
specifier|static
class|class
name|SaveDataGoToPreviousRpcRequest
implements|implements
name|GwtRpcImplementation
argument_list|<
name|AssignClassInstructorsInterface
operator|.
name|SaveDataGoToPreviousRpcRequest
argument_list|,
name|AssignClassInstructorsInterface
argument_list|>
block|{
annotation|@
name|Autowired
name|ApplicationContext
name|applicationContext
decl_stmt|;
annotation|@
name|Override
specifier|public
name|AssignClassInstructorsInterface
name|execute
parameter_list|(
name|AssignClassInstructorsInterface
operator|.
name|SaveDataGoToPreviousRpcRequest
name|request
parameter_list|,
name|SessionContext
name|context
parameter_list|)
block|{
name|Debug
operator|.
name|info
argument_list|(
literal|"In AssignClassInstructorsInterface execute(AssignClassInstructorsInterface.SaveDataGoToPreviousRpcRequest request, "
argument_list|)
expr_stmt|;
annotation|@
name|SuppressWarnings
argument_list|(
literal|"rawtypes"
argument_list|)
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
init|=
operator|new
name|_RootDAO
argument_list|()
operator|.
name|getSession
argument_list|()
decl_stmt|;
name|Transaction
name|tx
init|=
literal|null
decl_stmt|;
try|try
block|{
name|tx
operator|=
name|hibSession
operator|.
name|beginTransaction
argument_list|()
expr_stmt|;
name|AssignClassInstructorsInterface
name|data
init|=
name|request
operator|.
name|getData
argument_list|()
decl_stmt|;
name|AssignClassInstructorsTable
name|at
init|=
name|getTable
argument_list|(
name|applicationContext
argument_list|)
decl_stmt|;
name|at
operator|.
name|save
argument_list|(
name|request
operator|.
name|getData
argument_list|()
argument_list|,
name|context
argument_list|,
name|hibSession
argument_list|)
expr_stmt|;
if|if
condition|(
name|data
operator|.
name|isSaveSuccessful
argument_list|()
condition|)
block|{
name|hibSession
operator|.
name|flush
argument_list|()
expr_stmt|;
name|tx
operator|.
name|commit
argument_list|()
expr_stmt|;
name|tx
operator|=
literal|null
expr_stmt|;
name|tx
operator|=
name|hibSession
operator|.
name|beginTransaction
argument_list|()
expr_stmt|;
name|data
operator|=
name|at
operator|.
name|load
argument_list|(
name|data
operator|.
name|getPreviousConfigId
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|,
name|context
argument_list|,
name|hibSession
argument_list|)
expr_stmt|;
name|hibSession
operator|.
name|flush
argument_list|()
expr_stmt|;
name|tx
operator|.
name|commit
argument_list|()
expr_stmt|;
name|tx
operator|=
literal|null
expr_stmt|;
block|}
return|return
name|data
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
name|GwtRpcException
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
name|sLog
operator|.
name|error
argument_list|(
name|e
operator|.
name|getMessage
argument_list|()
argument_list|,
name|e
argument_list|)
expr_stmt|;
throw|throw
operator|new
name|GwtRpcException
argument_list|(
name|e
operator|.
name|getMessage
argument_list|()
argument_list|,
name|e
argument_list|)
throw|;
block|}
finally|finally
block|{
try|try
block|{
if|if
condition|(
name|tx
operator|!=
literal|null
operator|&&
name|tx
operator|.
name|isActive
argument_list|()
condition|)
block|{
name|tx
operator|.
name|rollback
argument_list|()
expr_stmt|;
block|}
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
block|}
block|}
block|}
block|}
annotation|@
name|GwtRpcImplements
argument_list|(
name|AssignClassInstructorsInterface
operator|.
name|SaveDataGoToNextRpcRequest
operator|.
name|class
argument_list|)
specifier|public
specifier|static
class|class
name|SaveDataGoToNextRpcRequest
implements|implements
name|GwtRpcImplementation
argument_list|<
name|AssignClassInstructorsInterface
operator|.
name|SaveDataGoToNextRpcRequest
argument_list|,
name|AssignClassInstructorsInterface
argument_list|>
block|{
annotation|@
name|Autowired
name|ApplicationContext
name|applicationContext
decl_stmt|;
annotation|@
name|Override
specifier|public
name|AssignClassInstructorsInterface
name|execute
parameter_list|(
name|AssignClassInstructorsInterface
operator|.
name|SaveDataGoToNextRpcRequest
name|request
parameter_list|,
name|SessionContext
name|context
parameter_list|)
block|{
name|Debug
operator|.
name|info
argument_list|(
literal|"In AssignClassInstructorsInterface execute(AssignClassInstructorsInterface.SaveDataGoToPreviousRpcRequest request, "
argument_list|)
expr_stmt|;
annotation|@
name|SuppressWarnings
argument_list|(
literal|"rawtypes"
argument_list|)
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
init|=
operator|new
name|_RootDAO
argument_list|()
operator|.
name|getSession
argument_list|()
decl_stmt|;
name|Transaction
name|tx
init|=
literal|null
decl_stmt|;
try|try
block|{
name|tx
operator|=
name|hibSession
operator|.
name|beginTransaction
argument_list|()
expr_stmt|;
name|AssignClassInstructorsInterface
name|data
init|=
name|request
operator|.
name|getData
argument_list|()
decl_stmt|;
name|AssignClassInstructorsTable
name|at
init|=
name|getTable
argument_list|(
name|applicationContext
argument_list|)
decl_stmt|;
name|at
operator|.
name|save
argument_list|(
name|request
operator|.
name|getData
argument_list|()
argument_list|,
name|context
argument_list|,
name|hibSession
argument_list|)
expr_stmt|;
if|if
condition|(
name|data
operator|.
name|isSaveSuccessful
argument_list|()
condition|)
block|{
name|hibSession
operator|.
name|flush
argument_list|()
expr_stmt|;
name|tx
operator|.
name|commit
argument_list|()
expr_stmt|;
name|tx
operator|=
literal|null
expr_stmt|;
name|data
operator|=
name|at
operator|.
name|load
argument_list|(
name|data
operator|.
name|getNextConfigId
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|,
name|context
argument_list|,
name|hibSession
argument_list|)
expr_stmt|;
block|}
return|return
name|data
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
name|GwtRpcException
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
name|sLog
operator|.
name|error
argument_list|(
name|e
operator|.
name|getMessage
argument_list|()
argument_list|,
name|e
argument_list|)
expr_stmt|;
throw|throw
operator|new
name|GwtRpcException
argument_list|(
name|e
operator|.
name|getMessage
argument_list|()
argument_list|,
name|e
argument_list|)
throw|;
block|}
finally|finally
block|{
try|try
block|{
if|if
condition|(
name|tx
operator|!=
literal|null
operator|&&
name|tx
operator|.
name|isActive
argument_list|()
condition|)
block|{
name|tx
operator|.
name|rollback
argument_list|()
expr_stmt|;
block|}
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
block|}
block|}
block|}
block|}
annotation|@
name|GwtRpcImplements
argument_list|(
name|AssignClassInstructorsInterface
operator|.
name|RemoveAllClassInstructorsDataRpcRequest
operator|.
name|class
argument_list|)
specifier|public
specifier|static
class|class
name|RemoveAllClassInstructorsDataBackend
implements|implements
name|GwtRpcImplementation
argument_list|<
name|AssignClassInstructorsInterface
operator|.
name|RemoveAllClassInstructorsDataRpcRequest
argument_list|,
name|AssignClassInstructorsInterface
argument_list|>
block|{
annotation|@
name|Autowired
name|ApplicationContext
name|applicationContext
decl_stmt|;
annotation|@
name|Override
specifier|public
name|AssignClassInstructorsInterface
name|execute
parameter_list|(
name|AssignClassInstructorsInterface
operator|.
name|RemoveAllClassInstructorsDataRpcRequest
name|request
parameter_list|,
name|SessionContext
name|context
parameter_list|)
block|{
annotation|@
name|SuppressWarnings
argument_list|(
literal|"rawtypes"
argument_list|)
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
init|=
operator|new
name|_RootDAO
argument_list|()
operator|.
name|getSession
argument_list|()
decl_stmt|;
name|Transaction
name|tx
init|=
literal|null
decl_stmt|;
try|try
block|{
name|tx
operator|=
name|hibSession
operator|.
name|beginTransaction
argument_list|()
expr_stmt|;
name|AssignClassInstructorsInterface
name|data
init|=
name|request
operator|.
name|getData
argument_list|()
decl_stmt|;
name|AssignClassInstructorsTable
name|at
init|=
name|getTable
argument_list|(
name|applicationContext
argument_list|)
decl_stmt|;
name|at
operator|.
name|removeAllInstructors
argument_list|(
name|request
operator|.
name|getData
argument_list|()
argument_list|,
name|context
argument_list|,
name|hibSession
argument_list|)
expr_stmt|;
name|hibSession
operator|.
name|flush
argument_list|()
expr_stmt|;
name|tx
operator|.
name|commit
argument_list|()
expr_stmt|;
name|tx
operator|=
literal|null
expr_stmt|;
for|for
control|(
name|Iterator
argument_list|<
name|Record
argument_list|>
name|i
init|=
name|data
operator|.
name|getRecords
argument_list|()
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
if|if
condition|(
name|i
operator|.
name|next
argument_list|()
operator|.
name|getUniqueId
argument_list|()
operator|==
literal|null
condition|)
name|i
operator|.
name|remove
argument_list|()
expr_stmt|;
return|return
name|data
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
name|GwtRpcException
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
name|sLog
operator|.
name|error
argument_list|(
name|e
operator|.
name|getMessage
argument_list|()
argument_list|,
name|e
argument_list|)
expr_stmt|;
throw|throw
operator|new
name|GwtRpcException
argument_list|(
name|e
operator|.
name|getMessage
argument_list|()
argument_list|,
name|e
argument_list|)
throw|;
block|}
finally|finally
block|{
try|try
block|{
if|if
condition|(
name|tx
operator|!=
literal|null
operator|&&
name|tx
operator|.
name|isActive
argument_list|()
condition|)
block|{
name|tx
operator|.
name|rollback
argument_list|()
expr_stmt|;
block|}
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
block|}
block|}
block|}
block|}
specifier|public
specifier|static
name|AssignClassInstructorsTable
name|getTable
parameter_list|(
name|ApplicationContext
name|context
parameter_list|)
block|{
return|return
operator|(
name|AssignClassInstructorsTable
operator|)
name|context
operator|.
name|getBean
argument_list|(
literal|"gwtAssignClassInstrs"
argument_list|,
name|AssignClassInstructorsTable
operator|.
name|class
argument_list|)
return|;
block|}
block|}
end_class

end_unit

