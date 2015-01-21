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
name|solver
operator|.
name|jgroups
package|;
end_package

begin_import
import|import
name|java
operator|.
name|lang
operator|.
name|reflect
operator|.
name|InvocationHandler
import|;
end_import

begin_import
import|import
name|java
operator|.
name|lang
operator|.
name|reflect
operator|.
name|InvocationTargetException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|lang
operator|.
name|reflect
operator|.
name|Method
import|;
end_import

begin_import
import|import
name|java
operator|.
name|lang
operator|.
name|reflect
operator|.
name|Proxy
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
name|java
operator|.
name|util
operator|.
name|Hashtable
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
name|Set
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Vector
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
name|cpsolver
operator|.
name|ifs
operator|.
name|util
operator|.
name|DataProperties
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jgroups
operator|.
name|Address
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jgroups
operator|.
name|JChannel
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jgroups
operator|.
name|SuspectedException
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jgroups
operator|.
name|blocks
operator|.
name|RpcDispatcher
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jgroups
operator|.
name|blocks
operator|.
name|mux
operator|.
name|MuxRpcDispatcher
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
name|Assignment
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
name|Class_
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
name|Department
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
name|Class_DAO
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
name|solver
operator|.
name|CommitedClassAssignmentProxy
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
name|SolverProxy
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
name|TimetableSolver
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
name|ui
operator|.
name|AssignmentPreferenceInfo
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
name|ui
operator|.
name|TimetableInfo
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
name|ui
operator|.
name|TimetableInfoFileProxy
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
name|ui
operator|.
name|TimetableInfoUtil
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|CourseSolverContainerRemote
extends|extends
name|CourseSolverContainer
implements|implements
name|RemoteSolverContainer
argument_list|<
name|SolverProxy
argument_list|>
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
name|CourseSolverContainerRemote
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|private
name|RpcDispatcher
name|iDispatcher
decl_stmt|;
specifier|public
name|CourseSolverContainerRemote
parameter_list|(
name|JChannel
name|channel
parameter_list|,
name|short
name|scope
parameter_list|)
block|{
name|iDispatcher
operator|=
operator|new
name|MuxRpcDispatcher
argument_list|(
name|scope
argument_list|,
name|channel
argument_list|,
literal|null
argument_list|,
literal|null
argument_list|,
name|this
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|RpcDispatcher
name|getDispatcher
parameter_list|()
block|{
return|return
name|iDispatcher
return|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|createRemoteSolver
parameter_list|(
name|String
name|user
parameter_list|,
name|DataProperties
name|config
parameter_list|,
name|Address
name|caller
parameter_list|)
block|{
name|TimetableSolver
name|solver
init|=
operator|(
name|TimetableSolver
operator|)
name|super
operator|.
name|createSolver
argument_list|(
name|user
argument_list|,
name|config
argument_list|)
decl_stmt|;
name|solver
operator|.
name|setFileProxy
argument_list|(
operator|new
name|FileProxy
argument_list|(
name|caller
argument_list|)
argument_list|)
expr_stmt|;
return|return
literal|true
return|;
block|}
annotation|@
name|Override
specifier|public
name|Object
name|invoke
parameter_list|(
name|String
name|method
parameter_list|,
name|String
name|user
parameter_list|,
name|Class
index|[]
name|types
parameter_list|,
name|Object
index|[]
name|args
parameter_list|)
throws|throws
name|Exception
block|{
try|try
block|{
name|SolverProxy
name|solver
init|=
name|iCourseSolvers
operator|.
name|get
argument_list|(
name|user
argument_list|)
decl_stmt|;
if|if
condition|(
literal|"exists"
operator|.
name|equals
argument_list|(
name|method
argument_list|)
operator|&&
name|types
operator|.
name|length
operator|==
literal|0
condition|)
return|return
name|solver
operator|!=
literal|null
return|;
if|if
condition|(
name|solver
operator|==
literal|null
condition|)
throw|throw
operator|new
name|Exception
argument_list|(
literal|"Solver "
operator|+
name|user
operator|+
literal|" does not exist."
argument_list|)
throw|;
return|return
name|solver
operator|.
name|getClass
argument_list|()
operator|.
name|getMethod
argument_list|(
name|method
argument_list|,
name|types
argument_list|)
operator|.
name|invoke
argument_list|(
name|solver
argument_list|,
name|args
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|InvocationTargetException
name|e
parameter_list|)
block|{
throw|throw
operator|(
name|Exception
operator|)
name|e
operator|.
name|getTargetException
argument_list|()
throw|;
block|}
finally|finally
block|{
name|_RootDAO
operator|.
name|closeCurrentThreadSessions
argument_list|()
expr_stmt|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|Object
name|dispatch
parameter_list|(
name|Address
name|address
parameter_list|,
name|String
name|user
parameter_list|,
name|Method
name|method
parameter_list|,
name|Object
index|[]
name|args
parameter_list|)
throws|throws
name|Exception
block|{
try|try
block|{
return|return
name|iDispatcher
operator|.
name|callRemoteMethod
argument_list|(
name|address
argument_list|,
literal|"invoke"
argument_list|,
operator|new
name|Object
index|[]
block|{
name|method
operator|.
name|getName
argument_list|()
block|,
name|user
block|,
name|method
operator|.
name|getParameterTypes
argument_list|()
block|,
name|args
block|}
argument_list|,
operator|new
name|Class
index|[]
block|{
name|String
operator|.
name|class
block|,
name|String
operator|.
name|class
block|,
name|Class
index|[]
operator|.
expr|class
block|,
name|Object
index|[]
operator|.
expr|class
block|}
argument_list|,
name|SolverServerImplementation
operator|.
name|sFirstResponse
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|InvocationTargetException
name|e
parameter_list|)
block|{
throw|throw
operator|(
name|Exception
operator|)
name|e
operator|.
name|getTargetException
argument_list|()
throw|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
if|if
condition|(
literal|"exists"
operator|.
name|equals
argument_list|(
name|method
operator|.
name|getName
argument_list|()
argument_list|)
operator|&&
name|e
operator|instanceof
name|SuspectedException
condition|)
return|return
literal|false
return|;
name|sLog
operator|.
name|error
argument_list|(
literal|"Excution of "
operator|+
name|method
operator|.
name|getName
argument_list|()
operator|+
literal|" on solver "
operator|+
name|user
operator|+
literal|" failed: "
operator|+
name|e
operator|.
name|getMessage
argument_list|()
argument_list|,
name|e
argument_list|)
expr_stmt|;
throw|throw
name|e
throw|;
block|}
block|}
specifier|public
name|void
name|saveToFile
parameter_list|(
name|String
name|name
parameter_list|,
name|TimetableInfo
name|info
parameter_list|)
throws|throws
name|Exception
block|{
name|TimetableInfoUtil
operator|.
name|getInstance
argument_list|()
operator|.
name|saveToFile
argument_list|(
name|name
argument_list|,
name|info
argument_list|)
expr_stmt|;
block|}
specifier|public
name|TimetableInfo
name|loadFromFile
parameter_list|(
name|String
name|name
parameter_list|)
throws|throws
name|Exception
block|{
return|return
name|TimetableInfoUtil
operator|.
name|getInstance
argument_list|()
operator|.
name|loadFromFile
argument_list|(
name|name
argument_list|)
return|;
block|}
specifier|public
name|void
name|deleteFile
parameter_list|(
name|String
name|name
parameter_list|)
throws|throws
name|Exception
block|{
name|TimetableInfoUtil
operator|.
name|getInstance
argument_list|()
operator|.
name|deleteFile
argument_list|(
name|name
argument_list|)
expr_stmt|;
block|}
specifier|private
class|class
name|FileProxy
implements|implements
name|TimetableInfoFileProxy
block|{
specifier|private
name|Address
name|iAddress
decl_stmt|;
specifier|private
name|FileProxy
parameter_list|(
name|Address
name|address
parameter_list|)
block|{
name|iAddress
operator|=
name|address
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|saveToFile
parameter_list|(
name|String
name|name
parameter_list|,
name|TimetableInfo
name|info
parameter_list|)
throws|throws
name|Exception
block|{
name|iDispatcher
operator|.
name|callRemoteMethod
argument_list|(
name|iAddress
argument_list|,
literal|"saveToFile"
argument_list|,
operator|new
name|Object
index|[]
block|{
name|name
block|,
name|info
block|}
argument_list|,
operator|new
name|Class
index|[]
block|{
name|String
operator|.
name|class
block|,
name|TimetableInfo
operator|.
name|class
block|}
argument_list|,
name|SolverServerImplementation
operator|.
name|sFirstResponse
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|TimetableInfo
name|loadFromFile
parameter_list|(
name|String
name|name
parameter_list|)
throws|throws
name|Exception
block|{
return|return
name|iDispatcher
operator|.
name|callRemoteMethod
argument_list|(
name|iAddress
argument_list|,
literal|"loadFromFile"
argument_list|,
operator|new
name|Object
index|[]
block|{
name|name
block|}
argument_list|,
operator|new
name|Class
index|[]
block|{
name|String
operator|.
name|class
block|}
argument_list|,
name|SolverServerImplementation
operator|.
name|sFirstResponse
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|deleteFile
parameter_list|(
name|String
name|name
parameter_list|)
throws|throws
name|Exception
block|{
name|iDispatcher
operator|.
name|callRemoteMethod
argument_list|(
name|iAddress
argument_list|,
literal|"deleteFile"
argument_list|,
operator|new
name|Object
index|[]
block|{
name|name
block|}
argument_list|,
operator|new
name|Class
index|[]
block|{
name|String
operator|.
name|class
block|}
argument_list|,
name|SolverServerImplementation
operator|.
name|sFirstResponse
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|SolverProxy
name|createProxy
parameter_list|(
name|Address
name|address
parameter_list|,
name|String
name|user
parameter_list|)
block|{
name|SolverInvocationHandler
name|handler
init|=
operator|new
name|SolverInvocationHandler
argument_list|(
name|address
argument_list|,
name|user
argument_list|)
decl_stmt|;
name|SolverProxy
name|px
init|=
operator|(
name|SolverProxy
operator|)
name|Proxy
operator|.
name|newProxyInstance
argument_list|(
name|SolverProxy
operator|.
name|class
operator|.
name|getClassLoader
argument_list|()
argument_list|,
operator|new
name|Class
index|[]
block|{
name|SolverProxy
operator|.
name|class
block|,
name|RemoteSolver
operator|.
name|class
block|, }
argument_list|,
name|handler
argument_list|)
decl_stmt|;
name|handler
operator|.
name|setRemoteSolverProxy
argument_list|(
name|px
argument_list|)
expr_stmt|;
return|return
name|px
return|;
block|}
specifier|public
class|class
name|SolverInvocationHandler
implements|implements
name|InvocationHandler
block|{
specifier|private
name|Address
name|iAddress
decl_stmt|;
specifier|private
name|String
name|iUser
decl_stmt|;
specifier|private
name|SolverProxy
name|iRemoteSolverProxy
decl_stmt|;
specifier|private
name|CommitedClassAssignmentProxy
name|iCommitedClassAssignmentProxy
init|=
literal|null
decl_stmt|;
specifier|private
name|SolverInvocationHandler
parameter_list|(
name|Address
name|address
parameter_list|,
name|String
name|user
parameter_list|)
block|{
name|iAddress
operator|=
name|address
expr_stmt|;
name|iUser
operator|=
name|user
expr_stmt|;
name|iCommitedClassAssignmentProxy
operator|=
operator|new
name|CommitedClassAssignmentProxy
argument_list|()
expr_stmt|;
block|}
specifier|private
name|void
name|setRemoteSolverProxy
parameter_list|(
name|SolverProxy
name|proxy
parameter_list|)
block|{
name|iRemoteSolverProxy
operator|=
name|proxy
expr_stmt|;
block|}
specifier|public
name|String
name|getHost
parameter_list|()
block|{
return|return
name|iAddress
operator|.
name|toString
argument_list|()
return|;
block|}
specifier|public
name|String
name|getUser
parameter_list|()
block|{
return|return
name|iUser
return|;
block|}
specifier|public
name|AssignmentPreferenceInfo
name|getAssignmentInfo
parameter_list|(
name|Class_
name|clazz
parameter_list|)
throws|throws
name|Exception
block|{
name|Department
name|dept
init|=
name|clazz
operator|.
name|getManagingDept
argument_list|()
decl_stmt|;
if|if
condition|(
name|dept
operator|!=
literal|null
operator|&&
name|iRemoteSolverProxy
operator|.
name|getDepartmentIds
argument_list|()
operator|.
name|contains
argument_list|(
name|dept
operator|.
name|getUniqueId
argument_list|()
argument_list|)
condition|)
return|return
name|iRemoteSolverProxy
operator|.
name|getAssignmentInfo
argument_list|(
name|clazz
operator|.
name|getUniqueId
argument_list|()
argument_list|)
return|;
return|return
name|iCommitedClassAssignmentProxy
operator|.
name|getAssignmentInfo
argument_list|(
name|clazz
argument_list|)
return|;
block|}
specifier|public
name|Assignment
name|getAssignment
parameter_list|(
name|Class_
name|clazz
parameter_list|)
throws|throws
name|Exception
block|{
name|Department
name|dept
init|=
name|clazz
operator|.
name|getManagingDept
argument_list|()
decl_stmt|;
if|if
condition|(
name|dept
operator|!=
literal|null
operator|&&
name|iRemoteSolverProxy
operator|.
name|getDepartmentIds
argument_list|()
operator|.
name|contains
argument_list|(
name|dept
operator|.
name|getUniqueId
argument_list|()
argument_list|)
condition|)
return|return
name|iRemoteSolverProxy
operator|.
name|getAssignment
argument_list|(
name|clazz
operator|.
name|getUniqueId
argument_list|()
argument_list|)
return|;
return|return
name|iCommitedClassAssignmentProxy
operator|.
name|getAssignment
argument_list|(
name|clazz
argument_list|)
return|;
block|}
specifier|public
name|Hashtable
name|getAssignmentTable
parameter_list|(
name|Collection
name|classesOrClassIds
parameter_list|)
throws|throws
name|Exception
block|{
name|Set
name|deptIds
init|=
name|iRemoteSolverProxy
operator|.
name|getDepartmentIds
argument_list|()
decl_stmt|;
name|Hashtable
name|assignments
init|=
operator|new
name|Hashtable
argument_list|()
decl_stmt|;
name|Vector
name|solverClassesOrClassIds
init|=
operator|new
name|Vector
argument_list|(
name|classesOrClassIds
operator|.
name|size
argument_list|()
argument_list|)
decl_stmt|;
for|for
control|(
name|Iterator
name|i
init|=
name|classesOrClassIds
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
name|Object
name|classOrClassId
init|=
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|classOrClassId
operator|instanceof
name|Object
index|[]
condition|)
name|classOrClassId
operator|=
operator|(
operator|(
name|Object
index|[]
operator|)
name|classOrClassId
operator|)
index|[
literal|0
index|]
expr_stmt|;
name|Class_
name|clazz
init|=
operator|(
name|classOrClassId
operator|instanceof
name|Class_
condition|?
operator|(
name|Class_
operator|)
name|classOrClassId
else|:
operator|(
operator|new
name|Class_DAO
argument_list|()
operator|)
operator|.
name|get
argument_list|(
operator|(
name|Long
operator|)
name|classOrClassId
argument_list|)
operator|)
decl_stmt|;
if|if
condition|(
name|clazz
operator|.
name|getManagingDept
argument_list|()
operator|==
literal|null
operator|||
operator|!
name|deptIds
operator|.
name|contains
argument_list|(
name|clazz
operator|.
name|getManagingDept
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|)
condition|)
block|{
name|Assignment
name|assignment
init|=
name|iCommitedClassAssignmentProxy
operator|.
name|getAssignment
argument_list|(
name|clazz
argument_list|)
decl_stmt|;
if|if
condition|(
name|assignment
operator|!=
literal|null
condition|)
name|assignments
operator|.
name|put
argument_list|(
name|clazz
operator|.
name|getUniqueId
argument_list|()
argument_list|,
name|assignment
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|solverClassesOrClassIds
operator|.
name|add
argument_list|(
name|clazz
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
if|if
condition|(
operator|!
name|solverClassesOrClassIds
operator|.
name|isEmpty
argument_list|()
condition|)
name|assignments
operator|.
name|putAll
argument_list|(
name|iRemoteSolverProxy
operator|.
name|getAssignmentTable2
argument_list|(
name|solverClassesOrClassIds
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|assignments
return|;
block|}
specifier|public
name|Hashtable
name|getAssignmentInfoTable
parameter_list|(
name|Collection
name|classesOrClassIds
parameter_list|)
throws|throws
name|Exception
block|{
name|Set
name|deptIds
init|=
name|iRemoteSolverProxy
operator|.
name|getDepartmentIds
argument_list|()
decl_stmt|;
name|Hashtable
name|infos
init|=
operator|new
name|Hashtable
argument_list|()
decl_stmt|;
name|Vector
name|solverClassesOrClassIds
init|=
operator|new
name|Vector
argument_list|(
name|classesOrClassIds
operator|.
name|size
argument_list|()
argument_list|)
decl_stmt|;
for|for
control|(
name|Iterator
name|i
init|=
name|classesOrClassIds
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
name|Object
name|classOrClassId
init|=
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|classOrClassId
operator|instanceof
name|Object
index|[]
condition|)
name|classOrClassId
operator|=
operator|(
operator|(
name|Object
index|[]
operator|)
name|classOrClassId
operator|)
index|[
literal|0
index|]
expr_stmt|;
name|Class_
name|clazz
init|=
operator|(
name|classOrClassId
operator|instanceof
name|Class_
condition|?
operator|(
name|Class_
operator|)
name|classOrClassId
else|:
operator|(
operator|new
name|Class_DAO
argument_list|()
operator|)
operator|.
name|get
argument_list|(
operator|(
name|Long
operator|)
name|classOrClassId
argument_list|)
operator|)
decl_stmt|;
if|if
condition|(
name|clazz
operator|.
name|getManagingDept
argument_list|()
operator|==
literal|null
operator|||
operator|!
name|deptIds
operator|.
name|contains
argument_list|(
name|clazz
operator|.
name|getManagingDept
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|)
condition|)
block|{
name|AssignmentPreferenceInfo
name|info
init|=
name|iCommitedClassAssignmentProxy
operator|.
name|getAssignmentInfo
argument_list|(
name|clazz
argument_list|)
decl_stmt|;
if|if
condition|(
name|info
operator|!=
literal|null
condition|)
name|infos
operator|.
name|put
argument_list|(
name|clazz
operator|.
name|getUniqueId
argument_list|()
argument_list|,
name|info
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|solverClassesOrClassIds
operator|.
name|add
argument_list|(
name|clazz
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
if|if
condition|(
operator|!
name|solverClassesOrClassIds
operator|.
name|isEmpty
argument_list|()
condition|)
name|infos
operator|.
name|putAll
argument_list|(
name|iRemoteSolverProxy
operator|.
name|getAssignmentInfoTable2
argument_list|(
name|solverClassesOrClassIds
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|infos
return|;
block|}
annotation|@
name|Override
specifier|public
name|Object
name|invoke
parameter_list|(
name|Object
name|proxy
parameter_list|,
name|Method
name|method
parameter_list|,
name|Object
index|[]
name|args
parameter_list|)
throws|throws
name|Throwable
block|{
try|try
block|{
return|return
name|getClass
argument_list|()
operator|.
name|getMethod
argument_list|(
name|method
operator|.
name|getName
argument_list|()
argument_list|,
name|method
operator|.
name|getParameterTypes
argument_list|()
argument_list|)
operator|.
name|invoke
argument_list|(
name|this
argument_list|,
name|args
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|NoSuchMethodException
name|e
parameter_list|)
block|{
block|}
return|return
name|dispatch
argument_list|(
name|iAddress
argument_list|,
name|iUser
argument_list|,
name|method
argument_list|,
name|args
argument_list|)
return|;
block|}
block|}
block|}
end_class

end_unit

