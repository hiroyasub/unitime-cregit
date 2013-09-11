begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.2 (University Timetabling Application)  * Copyright (C) 2008 - 2010, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
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
name|remote
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
name|ui
operator|.
name|AssignmentPreferenceInfo
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|RemoteSolverProxyFactory
implements|implements
name|InvocationHandler
block|{
specifier|private
name|RemoteSolverServerProxy
name|iProxy
decl_stmt|;
specifier|private
name|String
name|iPuid
decl_stmt|;
specifier|private
name|RemoteSolverProxy
name|iRemoteSolverProxy
decl_stmt|;
specifier|private
name|CommitedClassAssignmentProxy
name|iCommitedClassAssignmentProxy
init|=
literal|null
decl_stmt|;
specifier|private
name|RemoteSolverProxyFactory
parameter_list|(
name|RemoteSolverServerProxy
name|proxy
parameter_list|,
name|String
name|puid
parameter_list|)
block|{
name|iProxy
operator|=
name|proxy
expr_stmt|;
name|iPuid
operator|=
name|puid
expr_stmt|;
name|iCommitedClassAssignmentProxy
operator|=
operator|new
name|CommitedClassAssignmentProxy
argument_list|()
expr_stmt|;
block|}
specifier|public
name|void
name|setRemoteSolverProxy
parameter_list|(
name|RemoteSolverProxy
name|proxy
parameter_list|)
block|{
name|iRemoteSolverProxy
operator|=
name|proxy
expr_stmt|;
block|}
specifier|public
specifier|static
name|RemoteSolverProxy
name|create
parameter_list|(
name|RemoteSolverServerProxy
name|proxy
parameter_list|,
name|String
name|puid
parameter_list|)
block|{
name|RemoteSolverProxyFactory
name|handler
init|=
operator|new
name|RemoteSolverProxyFactory
argument_list|(
name|proxy
argument_list|,
name|puid
argument_list|)
decl_stmt|;
name|RemoteSolverProxy
name|px
init|=
operator|(
name|RemoteSolverProxy
operator|)
name|Proxy
operator|.
name|newProxyInstance
argument_list|(
name|RemoteSolverProxyFactory
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
name|RemoteSolverProxy
operator|.
name|class
block|}
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
name|String
name|getPuid
parameter_list|()
block|{
return|return
name|iPuid
return|;
block|}
specifier|public
name|RemoteSolverServerProxy
name|getServerProxy
parameter_list|()
block|{
return|return
name|iProxy
return|;
block|}
specifier|public
name|String
name|getHost
parameter_list|()
block|{
return|return
name|iProxy
operator|.
name|getAddress
argument_list|()
operator|.
name|getHostName
argument_list|()
operator|+
literal|":"
operator|+
name|iProxy
operator|.
name|getPort
argument_list|()
return|;
block|}
specifier|public
name|String
name|getHostLabel
parameter_list|()
block|{
name|String
name|hostName
init|=
name|iProxy
operator|.
name|getAddress
argument_list|()
operator|.
name|getHostName
argument_list|()
decl_stmt|;
if|if
condition|(
name|hostName
operator|.
name|indexOf
argument_list|(
literal|'.'
argument_list|)
operator|>=
literal|0
condition|)
name|hostName
operator|=
name|hostName
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|hostName
operator|.
name|indexOf
argument_list|(
literal|'.'
argument_list|)
argument_list|)
expr_stmt|;
try|try
block|{
name|Integer
operator|.
name|parseInt
argument_list|(
name|hostName
argument_list|)
expr_stmt|;
comment|//hostName is an IP address -> return that IP address
name|hostName
operator|=
name|iProxy
operator|.
name|getAddress
argument_list|()
operator|.
name|getHostName
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|NumberFormatException
name|x
parameter_list|)
block|{
block|}
return|return
name|hostName
operator|+
literal|":"
operator|+
name|iProxy
operator|.
name|getPort
argument_list|()
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
name|Object
index|[]
name|params
init|=
operator|new
name|Object
index|[
literal|2
operator|*
operator|(
name|args
operator|==
literal|null
condition|?
literal|0
else|:
name|args
operator|.
name|length
operator|)
operator|+
literal|2
index|]
decl_stmt|;
name|params
index|[
literal|0
index|]
operator|=
name|method
operator|.
name|getName
argument_list|()
expr_stmt|;
name|params
index|[
literal|1
index|]
operator|=
name|iPuid
expr_stmt|;
if|if
condition|(
name|args
operator|!=
literal|null
condition|)
block|{
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|args
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|params
index|[
literal|2
operator|*
name|i
operator|+
literal|2
index|]
operator|=
name|method
operator|.
name|getParameterTypes
argument_list|()
index|[
name|i
index|]
expr_stmt|;
name|params
index|[
literal|2
operator|*
name|i
operator|+
literal|3
index|]
operator|=
name|args
index|[
name|i
index|]
expr_stmt|;
block|}
block|}
return|return
name|iProxy
operator|.
name|query
argument_list|(
name|params
argument_list|)
return|;
block|}
block|}
end_class

end_unit

