begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.0 (University Course Timetabling& Student Sectioning Application)  * Copyright (C) 2007, UniTime.org, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 2 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program; if not, write to the Free Software Foundation, Inc.,  * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
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
name|net
operator|.
name|InetAddress
import|;
end_import

begin_import
import|import
name|java
operator|.
name|net
operator|.
name|Socket
import|;
end_import

begin_import
import|import
name|java
operator|.
name|net
operator|.
name|SocketException
import|;
end_import

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
name|exam
operator|.
name|ExamSolverProxy
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
name|exam
operator|.
name|ExamSolverProxyFactory
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
name|remote
operator|.
name|SolverRegisterService
operator|.
name|SolverConnection
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
name|remote
operator|.
name|core
operator|.
name|ConnectionFactory
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
name|remote
operator|.
name|core
operator|.
name|RemoteIo
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
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

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|RemoteSolverServerProxy
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
name|RemoteSolverServerProxy
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|int
name|sMaxPoolSize
init|=
literal|10
decl_stmt|;
specifier|private
name|InetAddress
name|iAddress
decl_stmt|;
specifier|private
name|int
name|iPort
decl_stmt|;
specifier|private
name|Vector
name|iSocketPool
init|=
operator|new
name|Vector
argument_list|()
decl_stmt|;
specifier|private
name|Vector
name|iLeasedSockets
init|=
operator|new
name|Vector
argument_list|()
decl_stmt|;
specifier|private
name|SolverConnection
name|iConnection
decl_stmt|;
specifier|public
name|RemoteSolverServerProxy
parameter_list|(
name|InetAddress
name|address
parameter_list|,
name|int
name|port
parameter_list|,
name|SolverConnection
name|connection
parameter_list|)
block|{
name|iPort
operator|=
name|port
expr_stmt|;
name|iAddress
operator|=
name|address
expr_stmt|;
name|iConnection
operator|=
name|connection
expr_stmt|;
block|}
specifier|public
name|InetAddress
name|getAddress
parameter_list|()
block|{
return|return
name|iAddress
return|;
block|}
specifier|public
name|int
name|getPort
parameter_list|()
block|{
return|return
name|iPort
return|;
block|}
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
name|iAddress
operator|.
name|getHostName
argument_list|()
operator|+
literal|":"
operator|+
name|iPort
return|;
block|}
specifier|public
name|int
name|hashCode
parameter_list|()
block|{
return|return
name|toString
argument_list|()
operator|.
name|hashCode
argument_list|()
return|;
block|}
specifier|public
name|boolean
name|isActive
parameter_list|()
block|{
return|return
operator|(
name|iConnection
operator|!=
literal|null
operator|&&
name|iConnection
operator|.
name|isActive
argument_list|()
operator|)
return|;
block|}
specifier|public
name|boolean
name|equals
parameter_list|(
name|Object
name|o
parameter_list|)
block|{
if|if
condition|(
name|o
operator|==
literal|null
operator|||
operator|!
operator|(
name|o
operator|instanceof
name|RemoteSolverServerProxy
operator|)
condition|)
return|return
literal|false
return|;
return|return
name|iAddress
operator|.
name|equals
argument_list|(
operator|(
operator|(
name|RemoteSolverServerProxy
operator|)
name|o
operator|)
operator|.
name|getAddress
argument_list|()
argument_list|)
operator|&&
name|iPort
operator|==
operator|(
operator|(
name|RemoteSolverServerProxy
operator|)
name|o
operator|)
operator|.
name|getPort
argument_list|()
return|;
block|}
specifier|public
name|Socket
name|leaseConnection
parameter_list|()
throws|throws
name|Exception
block|{
synchronized|synchronized
init|(
name|iSocketPool
init|)
block|{
if|if
condition|(
name|iSocketPool
operator|.
name|isEmpty
argument_list|()
operator|&&
name|iSocketPool
operator|.
name|size
argument_list|()
operator|+
name|iLeasedSockets
operator|.
name|size
argument_list|()
operator|<
name|sMaxPoolSize
condition|)
block|{
name|Socket
name|socket
init|=
name|ConnectionFactory
operator|.
name|getSocketFactory
argument_list|()
operator|.
name|createSocket
argument_list|(
name|iAddress
operator|.
name|getHostName
argument_list|()
argument_list|,
name|iPort
argument_list|)
decl_stmt|;
empty_stmt|;
name|sLog
operator|.
name|debug
argument_list|(
literal|"-- connection "
operator|+
name|this
operator|+
literal|"@"
operator|+
name|socket
operator|.
name|getLocalPort
argument_list|()
operator|+
literal|" created"
argument_list|)
expr_stmt|;
name|iLeasedSockets
operator|.
name|addElement
argument_list|(
name|socket
argument_list|)
expr_stmt|;
return|return
name|socket
return|;
block|}
while|while
condition|(
literal|true
condition|)
block|{
if|if
condition|(
operator|!
name|iSocketPool
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|Socket
name|socket
init|=
operator|(
name|Socket
operator|)
name|iSocketPool
operator|.
name|firstElement
argument_list|()
decl_stmt|;
name|iSocketPool
operator|.
name|removeElement
argument_list|(
name|socket
argument_list|)
expr_stmt|;
if|if
condition|(
name|socket
operator|.
name|isClosed
argument_list|()
condition|)
block|{
name|socket
operator|=
name|ConnectionFactory
operator|.
name|getSocketFactory
argument_list|()
operator|.
name|createSocket
argument_list|(
name|iAddress
operator|.
name|getHostName
argument_list|()
argument_list|,
name|iPort
argument_list|)
expr_stmt|;
empty_stmt|;
name|sLog
operator|.
name|debug
argument_list|(
literal|"-- connection "
operator|+
name|this
operator|+
literal|"@"
operator|+
name|socket
operator|.
name|getLocalPort
argument_list|()
operator|+
literal|" created (reconnect)"
argument_list|)
expr_stmt|;
block|}
name|iLeasedSockets
operator|.
name|addElement
argument_list|(
name|socket
argument_list|)
expr_stmt|;
return|return
name|socket
return|;
block|}
name|iSocketPool
operator|.
name|wait
argument_list|()
expr_stmt|;
block|}
block|}
block|}
specifier|public
name|void
name|releaseConnection
parameter_list|(
name|Socket
name|socket
parameter_list|)
throws|throws
name|Exception
block|{
synchronized|synchronized
init|(
name|iSocketPool
init|)
block|{
name|iSocketPool
operator|.
name|addElement
argument_list|(
name|socket
argument_list|)
expr_stmt|;
name|iLeasedSockets
operator|.
name|removeElement
argument_list|(
name|socket
argument_list|)
expr_stmt|;
name|iSocketPool
operator|.
name|notify
argument_list|()
expr_stmt|;
block|}
block|}
specifier|public
name|void
name|disconnectProxy
parameter_list|()
block|{
try|try
block|{
name|iConnection
operator|.
name|stopConnection
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
block|}
try|try
block|{
name|iConnection
operator|.
name|interrupt
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
block|}
name|sLog
operator|.
name|info
argument_list|(
literal|"server "
operator|+
name|this
operator|+
literal|" disconnected"
argument_list|)
expr_stmt|;
name|disconnect
argument_list|()
expr_stmt|;
block|}
specifier|public
name|void
name|disconnect
parameter_list|()
block|{
synchronized|synchronized
init|(
name|iSocketPool
init|)
block|{
for|for
control|(
name|Enumeration
name|e
init|=
name|iSocketPool
operator|.
name|elements
argument_list|()
init|;
name|e
operator|.
name|hasMoreElements
argument_list|()
condition|;
control|)
block|{
name|Socket
name|socket
init|=
operator|(
name|Socket
operator|)
name|e
operator|.
name|nextElement
argument_list|()
decl_stmt|;
try|try
block|{
name|sLog
operator|.
name|debug
argument_list|(
literal|"-- connection "
operator|+
name|this
operator|+
literal|"@"
operator|+
name|socket
operator|.
name|getLocalPort
argument_list|()
operator|+
literal|" closed"
argument_list|)
expr_stmt|;
name|socket
operator|.
name|close
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|x
parameter_list|)
block|{
block|}
block|}
name|iSocketPool
operator|.
name|clear
argument_list|()
expr_stmt|;
for|for
control|(
name|Enumeration
name|e
init|=
name|iLeasedSockets
operator|.
name|elements
argument_list|()
init|;
name|e
operator|.
name|hasMoreElements
argument_list|()
condition|;
control|)
block|{
name|Socket
name|socket
init|=
operator|(
name|Socket
operator|)
name|e
operator|.
name|nextElement
argument_list|()
decl_stmt|;
try|try
block|{
name|sLog
operator|.
name|debug
argument_list|(
literal|"-- connection "
operator|+
name|this
operator|+
literal|"@"
operator|+
name|socket
operator|.
name|getLocalPort
argument_list|()
operator|+
literal|" closed"
argument_list|)
expr_stmt|;
name|socket
operator|.
name|close
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|x
parameter_list|)
block|{
block|}
block|}
name|iLeasedSockets
operator|.
name|clear
argument_list|()
expr_stmt|;
block|}
block|}
specifier|public
name|Object
name|query
parameter_list|(
name|Object
name|command
parameter_list|)
throws|throws
name|Exception
block|{
name|Socket
name|socket
init|=
literal|null
decl_stmt|;
try|try
block|{
name|socket
operator|=
name|leaseConnection
argument_list|()
expr_stmt|;
name|sLog
operator|.
name|debug
argument_list|(
literal|"-- connection ("
operator|+
name|iLeasedSockets
operator|.
name|size
argument_list|()
operator|+
literal|") "
operator|+
name|this
operator|+
literal|"@"
operator|+
name|socket
operator|.
name|getLocalPort
argument_list|()
operator|+
literal|" leased"
argument_list|)
expr_stmt|;
name|Object
name|answer
init|=
literal|null
decl_stmt|;
name|RemoteIo
operator|.
name|writeObject
argument_list|(
name|socket
argument_list|,
name|command
argument_list|)
expr_stmt|;
comment|//sLog.debug("Q:"+(command instanceof Object[]?((Object[])command)[0]:command));
try|try
block|{
name|answer
operator|=
name|RemoteIo
operator|.
name|readObject
argument_list|(
name|socket
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|java
operator|.
name|io
operator|.
name|EOFException
name|ex
parameter_list|)
block|{
block|}
empty_stmt|;
comment|//sLog.debug("A:"+answer);
comment|//disconnect();
if|if
condition|(
name|answer
operator|!=
literal|null
operator|&&
name|answer
operator|instanceof
name|Exception
condition|)
throw|throw
operator|(
name|Exception
operator|)
name|answer
throw|;
return|return
name|answer
return|;
block|}
catch|catch
parameter_list|(
name|SocketException
name|e
parameter_list|)
block|{
name|disconnectProxy
argument_list|()
expr_stmt|;
name|sLog
operator|.
name|error
argument_list|(
literal|"Unable to query, reason: "
operator|+
name|e
operator|.
name|getMessage
argument_list|()
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
block|}
finally|finally
block|{
if|if
condition|(
name|socket
operator|!=
literal|null
condition|)
block|{
name|releaseConnection
argument_list|(
name|socket
argument_list|)
expr_stmt|;
name|sLog
operator|.
name|debug
argument_list|(
literal|"-- connection ("
operator|+
name|iLeasedSockets
operator|.
name|size
argument_list|()
operator|+
literal|") "
operator|+
name|this
operator|+
literal|"@"
operator|+
name|socket
operator|.
name|getLocalPort
argument_list|()
operator|+
literal|" released"
argument_list|)
expr_stmt|;
block|}
block|}
block|}
specifier|public
name|RemoteSolverProxy
name|createSolver
parameter_list|(
name|String
name|puid
parameter_list|,
name|DataProperties
name|properties
parameter_list|)
throws|throws
name|Exception
block|{
name|RemoteSolverProxy
name|solver
init|=
name|RemoteSolverProxyFactory
operator|.
name|create
argument_list|(
name|this
argument_list|,
name|puid
argument_list|)
decl_stmt|;
if|if
condition|(
name|solver
operator|.
name|exists
argument_list|()
condition|)
name|solver
operator|.
name|dispose
argument_list|()
expr_stmt|;
name|solver
operator|.
name|create
argument_list|(
name|properties
argument_list|)
expr_stmt|;
return|return
name|solver
return|;
block|}
specifier|public
name|SolverProxy
name|getSolver
parameter_list|(
name|String
name|puid
parameter_list|)
throws|throws
name|Exception
block|{
name|RemoteSolverProxy
name|solver
init|=
name|RemoteSolverProxyFactory
operator|.
name|create
argument_list|(
name|this
argument_list|,
name|puid
argument_list|)
decl_stmt|;
return|return
operator|(
name|solver
operator|.
name|exists
argument_list|()
condition|?
name|solver
else|:
literal|null
operator|)
return|;
block|}
specifier|public
name|void
name|removeSolver
parameter_list|(
name|String
name|puid
parameter_list|)
throws|throws
name|Exception
block|{
name|RemoteSolverProxy
name|solver
init|=
name|RemoteSolverProxyFactory
operator|.
name|create
argument_list|(
name|this
argument_list|,
name|puid
argument_list|)
decl_stmt|;
if|if
condition|(
name|solver
operator|.
name|exists
argument_list|()
condition|)
name|solver
operator|.
name|dispose
argument_list|()
expr_stmt|;
block|}
specifier|public
name|Hashtable
argument_list|<
name|String
argument_list|,
name|RemoteSolverProxy
argument_list|>
name|getSolvers
parameter_list|()
throws|throws
name|Exception
block|{
name|Set
name|puids
init|=
operator|(
name|Set
operator|)
name|query
argument_list|(
operator|new
name|Object
index|[]
block|{
literal|"getSolvers"
block|}
argument_list|)
decl_stmt|;
if|if
condition|(
name|puids
operator|==
literal|null
condition|)
return|return
operator|new
name|Hashtable
argument_list|()
return|;
name|Hashtable
name|solvers
init|=
operator|new
name|Hashtable
argument_list|()
decl_stmt|;
for|for
control|(
name|Iterator
name|i
init|=
name|puids
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
name|String
name|puid
init|=
operator|(
name|String
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|solvers
operator|.
name|put
argument_list|(
name|puid
argument_list|,
name|RemoteSolverProxyFactory
operator|.
name|create
argument_list|(
name|this
argument_list|,
name|puid
argument_list|)
argument_list|)
expr_stmt|;
block|}
return|return
name|solvers
return|;
block|}
specifier|public
name|Hashtable
argument_list|<
name|String
argument_list|,
name|ExamSolverProxy
argument_list|>
name|getExamSolvers
parameter_list|()
throws|throws
name|Exception
block|{
name|Set
name|puids
init|=
operator|(
name|Set
operator|)
name|query
argument_list|(
operator|new
name|Object
index|[]
block|{
literal|"getExamSolvers"
block|}
argument_list|)
decl_stmt|;
if|if
condition|(
name|puids
operator|==
literal|null
condition|)
return|return
operator|new
name|Hashtable
argument_list|()
return|;
name|Hashtable
argument_list|<
name|String
argument_list|,
name|ExamSolverProxy
argument_list|>
name|solvers
init|=
operator|new
name|Hashtable
argument_list|()
decl_stmt|;
for|for
control|(
name|Iterator
name|i
init|=
name|puids
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
name|String
name|puid
init|=
operator|(
name|String
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|solvers
operator|.
name|put
argument_list|(
name|puid
argument_list|,
name|ExamSolverProxyFactory
operator|.
name|create
argument_list|(
name|this
argument_list|,
name|puid
argument_list|)
argument_list|)
expr_stmt|;
block|}
return|return
name|solvers
return|;
block|}
specifier|public
name|ExamSolverProxy
name|getExamSolver
parameter_list|(
name|String
name|puid
parameter_list|)
throws|throws
name|Exception
block|{
if|if
condition|(
operator|(
operator|(
name|Boolean
operator|)
name|query
argument_list|(
operator|new
name|Object
index|[]
block|{
literal|"hasExamSolver"
operator|,
name|puid
block|}
block_content|)
block|)
operator|.
name|booleanValue
argument_list|()
end_class

begin_block
unit|)
block|{
return|return
name|ExamSolverProxyFactory
operator|.
name|create
argument_list|(
name|this
argument_list|,
name|puid
argument_list|)
return|;
block|}
end_block

begin_if_stmt
else|else
return|return
literal|null
return|;
end_if_stmt

begin_function
unit|} 	     public
name|ExamSolverProxy
name|createExamSolver
parameter_list|(
name|String
name|puid
parameter_list|,
name|DataProperties
name|properties
parameter_list|)
throws|throws
name|Exception
block|{
name|query
argument_list|(
operator|new
name|Object
index|[]
block|{
literal|"createExamSolver"
block|,
name|puid
block|,
name|properties
block|}
argument_list|)
expr_stmt|;
return|return
name|ExamSolverProxyFactory
operator|.
name|create
argument_list|(
name|this
argument_list|,
name|puid
argument_list|)
return|;
block|}
end_function

begin_function
specifier|public
name|String
name|getVersion
parameter_list|()
throws|throws
name|Exception
block|{
return|return
operator|(
name|String
operator|)
name|query
argument_list|(
operator|new
name|Object
index|[]
block|{
literal|"getVersion"
block|}
argument_list|)
return|;
block|}
end_function

begin_function
specifier|public
name|Date
name|getStartTime
parameter_list|()
throws|throws
name|Exception
block|{
return|return
operator|(
name|Date
operator|)
name|query
argument_list|(
operator|new
name|Object
index|[]
block|{
literal|"getStartTime"
block|}
argument_list|)
return|;
block|}
end_function

begin_function
specifier|public
name|long
name|getAvailableMemory
parameter_list|()
throws|throws
name|Exception
block|{
name|Long
name|mem
init|=
operator|(
name|Long
operator|)
name|query
argument_list|(
operator|new
name|Object
index|[]
block|{
literal|"getAvailableMemory"
block|}
argument_list|)
decl_stmt|;
return|return
operator|(
name|mem
operator|==
literal|null
condition|?
literal|0
else|:
name|mem
operator|.
name|longValue
argument_list|()
operator|)
return|;
block|}
end_function

begin_function
specifier|public
name|long
name|getUsage
parameter_list|()
throws|throws
name|Exception
block|{
name|Long
name|usage
init|=
operator|(
name|Long
operator|)
name|query
argument_list|(
operator|new
name|Object
index|[]
block|{
literal|"getUsage"
block|}
argument_list|)
decl_stmt|;
return|return
operator|(
name|usage
operator|==
literal|null
condition|?
literal|0
else|:
name|usage
operator|.
name|longValue
argument_list|()
operator|)
return|;
block|}
end_function

begin_function
specifier|public
name|void
name|startUsing
parameter_list|()
throws|throws
name|Exception
block|{
name|query
argument_list|(
operator|new
name|Object
index|[]
block|{
literal|"startUsing"
block|}
argument_list|)
expr_stmt|;
block|}
end_function

begin_function
specifier|public
name|void
name|stopUsing
parameter_list|()
throws|throws
name|Exception
block|{
name|query
argument_list|(
operator|new
name|Object
index|[]
block|{
literal|"stopUsing"
block|}
argument_list|)
expr_stmt|;
block|}
end_function

begin_function
specifier|public
name|void
name|shutdown
parameter_list|()
throws|throws
name|Exception
block|{
name|query
argument_list|(
literal|"quit"
argument_list|)
expr_stmt|;
name|disconnectProxy
argument_list|()
expr_stmt|;
name|Set
name|servers
init|=
name|SolverRegisterService
operator|.
name|getInstance
argument_list|()
operator|.
name|getServers
argument_list|()
decl_stmt|;
synchronized|synchronized
init|(
name|servers
init|)
block|{
name|servers
operator|.
name|remove
argument_list|(
name|this
argument_list|)
expr_stmt|;
block|}
block|}
end_function

begin_function
specifier|public
name|void
name|kill
parameter_list|()
throws|throws
name|Exception
block|{
name|query
argument_list|(
literal|"kill"
argument_list|)
expr_stmt|;
name|disconnectProxy
argument_list|()
expr_stmt|;
name|Set
name|servers
init|=
name|SolverRegisterService
operator|.
name|getInstance
argument_list|()
operator|.
name|getServers
argument_list|()
decl_stmt|;
synchronized|synchronized
init|(
name|servers
init|)
block|{
name|servers
operator|.
name|remove
argument_list|(
name|this
argument_list|)
expr_stmt|;
block|}
block|}
end_function

begin_function
specifier|protected
name|void
name|finalize
parameter_list|()
throws|throws
name|Throwable
block|{
name|disconnectProxy
argument_list|()
expr_stmt|;
name|super
operator|.
name|finalize
argument_list|()
expr_stmt|;
block|}
end_function

unit|}
end_unit

