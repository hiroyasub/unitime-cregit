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
name|io
operator|.
name|ByteArrayOutputStream
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|File
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|FileOutputStream
import|;
end_import

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
name|io
operator|.
name|InputStream
import|;
end_import

begin_import
import|import
name|java
operator|.
name|net
operator|.
name|BindException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|net
operator|.
name|ServerSocket
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
name|URL
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
name|HashSet
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
name|dom4j
operator|.
name|Document
import|;
end_import

begin_import
import|import
name|org
operator|.
name|dom4j
operator|.
name|Element
import|;
end_import

begin_import
import|import
name|org
operator|.
name|dom4j
operator|.
name|io
operator|.
name|OutputFormat
import|;
end_import

begin_import
import|import
name|org
operator|.
name|dom4j
operator|.
name|io
operator|.
name|SAXReader
import|;
end_import

begin_import
import|import
name|org
operator|.
name|dom4j
operator|.
name|io
operator|.
name|XMLWriter
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
name|ApplicationProperties
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
name|ExamType
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
name|Solution
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
name|base
operator|.
name|_BaseRootDAO
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
name|LocationDAO
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
name|TimetableInfoUtil
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
name|util
operator|.
name|RoomAvailability
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
name|Callback
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|SolverRegisterService
extends|extends
name|Thread
block|{
specifier|static
name|Log
name|sLog
init|=
name|LogFactory
operator|.
name|getLog
argument_list|(
name|SolverRegisterService
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|private
specifier|static
name|SolverRegisterService
name|sInstance
init|=
literal|null
decl_stmt|;
specifier|private
name|ServerSocket
name|iServerSocket
init|=
literal|null
decl_stmt|;
specifier|private
name|Set
name|iServers
init|=
operator|new
name|HashSet
argument_list|()
decl_stmt|;
specifier|public
specifier|static
name|File
name|sBackupDir
init|=
name|ApplicationProperties
operator|.
name|getRestoreFolder
argument_list|()
decl_stmt|;
specifier|public
specifier|static
name|File
name|sPassivationDir
init|=
name|ApplicationProperties
operator|.
name|getPassivationFolder
argument_list|()
decl_stmt|;
specifier|private
specifier|static
name|ShutdownHook
name|sHook
init|=
literal|null
decl_stmt|;
specifier|private
specifier|static
name|int
name|sTimeout
init|=
literal|300000
decl_stmt|;
comment|//5 minutes
specifier|private
specifier|static
name|boolean
name|sLocalSolverInitialized
init|=
literal|false
decl_stmt|;
specifier|private
name|Date
name|iStartTime
init|=
literal|null
decl_stmt|;
specifier|private
name|SolverRegisterService
parameter_list|()
block|{
name|setDaemon
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|setName
argument_list|(
literal|"SolverRegister.Service"
argument_list|)
expr_stmt|;
block|}
specifier|public
specifier|static
name|int
name|getPort
parameter_list|()
block|{
if|if
condition|(
name|sInstance
operator|==
literal|null
operator|||
name|sInstance
operator|.
name|iServerSocket
operator|==
literal|null
condition|)
return|return
operator|-
literal|1
return|;
return|return
name|sInstance
operator|.
name|iServerSocket
operator|.
name|getLocalPort
argument_list|()
return|;
block|}
specifier|public
specifier|static
specifier|synchronized
name|void
name|startService
parameter_list|()
block|{
if|if
condition|(
name|sInstance
operator|!=
literal|null
condition|)
name|stopService
argument_list|()
expr_stmt|;
name|sInstance
operator|=
operator|new
name|SolverRegisterService
argument_list|()
expr_stmt|;
name|sInstance
operator|.
name|start
argument_list|()
expr_stmt|;
name|sLog
operator|.
name|info
argument_list|(
literal|"service started"
argument_list|)
expr_stmt|;
block|}
specifier|public
specifier|static
specifier|synchronized
name|void
name|stopService
parameter_list|()
block|{
if|if
condition|(
name|sInstance
operator|==
literal|null
condition|)
return|return;
try|try
block|{
synchronized|synchronized
init|(
name|sInstance
operator|.
name|iServers
init|)
block|{
for|for
control|(
name|Iterator
name|i
init|=
name|sInstance
operator|.
name|iServers
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
name|RemoteSolverServerProxy
name|server
init|=
operator|(
name|RemoteSolverServerProxy
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|execute
argument_list|(
operator|new
name|DisconnectProxyCallback
argument_list|(
name|server
argument_list|)
argument_list|,
literal|10000
argument_list|)
expr_stmt|;
block|}
name|sInstance
operator|.
name|iServers
operator|.
name|clear
argument_list|()
expr_stmt|;
block|}
if|if
condition|(
name|sInstance
operator|.
name|iServerSocket
operator|!=
literal|null
condition|)
name|sInstance
operator|.
name|iServerSocket
operator|.
name|close
argument_list|()
expr_stmt|;
name|sInstance
operator|.
name|join
argument_list|(
literal|30000
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|sLog
operator|.
name|warn
argument_list|(
literal|"Unable to stop service, reason: "
operator|+
name|e
operator|.
name|getMessage
argument_list|()
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
name|sInstance
operator|=
literal|null
expr_stmt|;
name|sLog
operator|.
name|info
argument_list|(
literal|"service stopped"
argument_list|)
expr_stmt|;
block|}
specifier|public
specifier|static
name|void
name|setupLocalSolver
parameter_list|(
name|String
name|codeBase
parameter_list|,
name|String
name|host
parameter_list|,
name|int
name|port
parameter_list|)
block|{
if|if
condition|(
name|sInstance
operator|==
literal|null
operator|||
name|sLocalSolverInitialized
condition|)
return|return;
synchronized|synchronized
init|(
name|sInstance
init|)
block|{
try|try
block|{
name|File
name|webInfDir
init|=
operator|new
name|File
argument_list|(
name|ApplicationProperties
operator|.
name|getBasePath
argument_list|()
argument_list|)
decl_stmt|;
name|File
name|timetablingDir
init|=
name|webInfDir
operator|.
name|getParentFile
argument_list|()
decl_stmt|;
name|File
name|solverDir
init|=
operator|new
name|File
argument_list|(
name|timetablingDir
argument_list|,
literal|"solver"
argument_list|)
decl_stmt|;
name|File
name|solverJnlp
init|=
operator|new
name|File
argument_list|(
name|solverDir
argument_list|,
literal|"solver.jnlp"
argument_list|)
decl_stmt|;
name|Document
name|document
init|=
operator|(
operator|new
name|SAXReader
argument_list|()
operator|)
operator|.
name|read
argument_list|(
name|solverJnlp
argument_list|)
decl_stmt|;
name|Element
name|root
init|=
name|document
operator|.
name|getRootElement
argument_list|()
decl_stmt|;
name|root
operator|.
name|attribute
argument_list|(
literal|"codebase"
argument_list|)
operator|.
name|setValue
argument_list|(
name|codeBase
operator|+
operator|(
name|codeBase
operator|.
name|endsWith
argument_list|(
literal|"/"
argument_list|)
condition|?
literal|""
else|:
literal|"/"
operator|)
operator|+
literal|"solver"
argument_list|)
expr_stmt|;
name|boolean
name|hostSet
init|=
literal|false
decl_stmt|,
name|portSet
init|=
literal|false
decl_stmt|;
name|Element
name|resources
init|=
name|root
operator|.
name|element
argument_list|(
literal|"resources"
argument_list|)
decl_stmt|;
for|for
control|(
name|Iterator
name|i
init|=
name|resources
operator|.
name|elementIterator
argument_list|(
literal|"property"
argument_list|)
init|;
name|i
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|Element
name|property
init|=
operator|(
name|Element
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
literal|"tmtbl.solver.register.host"
operator|.
name|equals
argument_list|(
name|property
operator|.
name|attributeValue
argument_list|(
literal|"name"
argument_list|)
argument_list|)
condition|)
block|{
name|property
operator|.
name|attribute
argument_list|(
literal|"value"
argument_list|)
operator|.
name|setValue
argument_list|(
name|host
argument_list|)
expr_stmt|;
name|hostSet
operator|=
literal|true
expr_stmt|;
block|}
if|if
condition|(
literal|"tmtbl.solver.register.port"
operator|.
name|equals
argument_list|(
name|property
operator|.
name|attributeValue
argument_list|(
literal|"name"
argument_list|)
argument_list|)
condition|)
block|{
name|property
operator|.
name|attribute
argument_list|(
literal|"value"
argument_list|)
operator|.
name|setValue
argument_list|(
name|String
operator|.
name|valueOf
argument_list|(
name|port
argument_list|)
argument_list|)
expr_stmt|;
name|portSet
operator|=
literal|true
expr_stmt|;
block|}
block|}
if|if
condition|(
operator|!
name|hostSet
condition|)
block|{
name|resources
operator|.
name|addElement
argument_list|(
literal|"property"
argument_list|)
operator|.
name|addAttribute
argument_list|(
literal|"name"
argument_list|,
literal|"tmtbl.solver.register.host"
argument_list|)
operator|.
name|addAttribute
argument_list|(
literal|"value"
argument_list|,
name|host
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
operator|!
name|portSet
condition|)
block|{
name|resources
operator|.
name|addElement
argument_list|(
literal|"property"
argument_list|)
operator|.
name|addAttribute
argument_list|(
literal|"name"
argument_list|,
literal|"tmtbl.solver.register.port"
argument_list|)
operator|.
name|addAttribute
argument_list|(
literal|"value"
argument_list|,
name|String
operator|.
name|valueOf
argument_list|(
name|port
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|FileOutputStream
name|fos
init|=
literal|null
decl_stmt|;
try|try
block|{
name|fos
operator|=
operator|new
name|FileOutputStream
argument_list|(
name|solverJnlp
argument_list|)
expr_stmt|;
operator|(
operator|new
name|XMLWriter
argument_list|(
name|fos
argument_list|,
name|OutputFormat
operator|.
name|createPrettyPrint
argument_list|()
argument_list|)
operator|)
operator|.
name|write
argument_list|(
name|document
argument_list|)
expr_stmt|;
name|fos
operator|.
name|flush
argument_list|()
expr_stmt|;
name|fos
operator|.
name|close
argument_list|()
expr_stmt|;
name|fos
operator|=
literal|null
expr_stmt|;
block|}
finally|finally
block|{
try|try
block|{
if|if
condition|(
name|fos
operator|!=
literal|null
condition|)
name|fos
operator|.
name|close
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
block|}
block|}
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|sLog
operator|.
name|debug
argument_list|(
literal|"Unable to alter solver.jnlp, reason: "
operator|+
name|e
operator|.
name|getMessage
argument_list|()
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
name|sLocalSolverInitialized
operator|=
literal|true
expr_stmt|;
block|}
block|}
specifier|public
name|void
name|run
parameter_list|()
block|{
name|iStartTime
operator|=
operator|new
name|Date
argument_list|()
expr_stmt|;
try|try
block|{
name|ConnectionFactory
operator|.
name|init
argument_list|(
name|ApplicationProperties
operator|.
name|getProperties
argument_list|()
argument_list|,
name|ApplicationProperties
operator|.
name|getDataFolder
argument_list|()
argument_list|)
expr_stmt|;
try|try
block|{
while|while
condition|(
literal|true
condition|)
block|{
try|try
block|{
name|iServerSocket
operator|=
name|ConnectionFactory
operator|.
name|getServerSocketFactory
argument_list|()
operator|.
name|createServerSocket
argument_list|(
name|Integer
operator|.
name|parseInt
argument_list|(
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"tmtbl.solver.register.port"
argument_list|,
literal|"9998"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|sLog
operator|.
name|info
argument_list|(
literal|"Service is running at "
operator|+
name|iServerSocket
operator|.
name|getInetAddress
argument_list|()
operator|.
name|getHostName
argument_list|()
operator|+
literal|":"
operator|+
name|iServerSocket
operator|.
name|getLocalPort
argument_list|()
argument_list|)
expr_stmt|;
break|break;
block|}
catch|catch
parameter_list|(
name|BindException
name|e
parameter_list|)
block|{
try|try
block|{
name|sLog
operator|.
name|warn
argument_list|(
literal|"Prior instance of service still running"
argument_list|)
expr_stmt|;
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
literal|"localhost"
argument_list|,
name|Integer
operator|.
name|parseInt
argument_list|(
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"tmtbl.solver.register.port"
argument_list|,
literal|"9998"
argument_list|)
argument_list|)
argument_list|)
decl_stmt|;
name|RemoteIo
operator|.
name|writeObject
argument_list|(
name|socket
argument_list|,
literal|"quit"
argument_list|)
expr_stmt|;
name|Object
name|answer
init|=
name|RemoteIo
operator|.
name|readObject
argument_list|(
name|socket
argument_list|)
decl_stmt|;
name|sLog
operator|.
name|warn
argument_list|(
literal|"quit command sent, answer: "
operator|+
name|answer
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
name|f
parameter_list|)
block|{
name|sLog
operator|.
name|warn
argument_list|(
literal|"Unable to connect to prior instance"
argument_list|,
name|f
argument_list|)
expr_stmt|;
block|}
name|sleep
argument_list|(
literal|1000
argument_list|)
expr_stmt|;
block|}
block|}
block|}
catch|catch
parameter_list|(
name|IOException
name|io
parameter_list|)
block|{
name|sLog
operator|.
name|error
argument_list|(
literal|"Unable to start service, reason: "
operator|+
name|io
operator|.
name|getMessage
argument_list|()
argument_list|,
name|io
argument_list|)
expr_stmt|;
return|return;
block|}
while|while
condition|(
operator|!
name|iServerSocket
operator|.
name|isClosed
argument_list|()
condition|)
block|{
try|try
block|{
name|Socket
name|socket
init|=
name|iServerSocket
operator|.
name|accept
argument_list|()
decl_stmt|;
name|socket
operator|.
name|setKeepAlive
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|sLog
operator|.
name|debug
argument_list|(
literal|"Client "
operator|+
name|socket
operator|.
name|getInetAddress
argument_list|()
operator|+
literal|" connected."
argument_list|)
expr_stmt|;
operator|(
operator|new
name|Thread
argument_list|(
operator|new
name|SolverConnection
argument_list|(
name|socket
argument_list|)
argument_list|)
operator|)
operator|.
name|start
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
if|if
condition|(
operator|!
name|iServerSocket
operator|.
name|isClosed
argument_list|()
condition|)
name|sLog
operator|.
name|warn
argument_list|(
literal|"Unable to accept new connection, reason:"
operator|+
name|e
operator|.
name|getMessage
argument_list|()
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
block|}
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
literal|"Unexpected exception occured, reason: "
operator|+
name|e
operator|.
name|getMessage
argument_list|()
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
finally|finally
block|{
try|try
block|{
if|if
condition|(
name|iServerSocket
operator|!=
literal|null
operator|&&
operator|!
name|iServerSocket
operator|.
name|isClosed
argument_list|()
condition|)
name|iServerSocket
operator|.
name|close
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|sLog
operator|.
name|warn
argument_list|(
literal|"Unable to close socket, reason: "
operator|+
name|e
operator|.
name|getMessage
argument_list|()
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
block|}
block|}
specifier|public
name|Set
name|getServers
parameter_list|()
block|{
return|return
name|iServers
return|;
block|}
specifier|public
specifier|static
name|SolverRegisterService
name|getInstance
parameter_list|()
block|{
return|return
name|sInstance
return|;
block|}
specifier|public
specifier|static
class|class
name|ShutdownHook
extends|extends
name|Thread
block|{
specifier|public
name|ShutdownHook
parameter_list|()
block|{
name|setName
argument_list|(
literal|"SolverRegister.ShutdownHook"
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|run
parameter_list|()
block|{
name|sLog
operator|.
name|info
argument_list|(
literal|"shutdown"
argument_list|)
expr_stmt|;
name|stopService
argument_list|()
expr_stmt|;
block|}
block|}
specifier|public
class|class
name|SolverConnection
extends|extends
name|Thread
block|{
specifier|private
name|Socket
name|iSocket
decl_stmt|;
specifier|private
name|boolean
name|iFinish
init|=
literal|false
decl_stmt|;
specifier|private
name|RemoteSolverServerProxy
name|iProxy
init|=
literal|null
decl_stmt|;
specifier|private
name|long
name|iLastPing
init|=
operator|-
literal|1
decl_stmt|;
specifier|protected
name|SolverConnection
parameter_list|(
name|Socket
name|socket
parameter_list|)
block|{
name|iSocket
operator|=
name|socket
expr_stmt|;
name|setName
argument_list|(
literal|"SolverRegister.SolverConnection"
argument_list|)
expr_stmt|;
block|}
specifier|public
name|Socket
name|getSocket
parameter_list|()
block|{
return|return
name|iSocket
return|;
block|}
specifier|public
name|void
name|stopConnection
parameter_list|()
block|{
name|iFinish
operator|=
literal|true
expr_stmt|;
block|}
specifier|public
name|void
name|unregister
parameter_list|()
block|{
if|if
condition|(
name|iProxy
operator|!=
literal|null
condition|)
block|{
synchronized|synchronized
init|(
name|iServers
init|)
block|{
if|if
condition|(
name|iServers
operator|.
name|remove
argument_list|(
name|iProxy
argument_list|)
condition|)
name|sLog
operator|.
name|info
argument_list|(
literal|"Sever "
operator|+
name|iProxy
operator|+
literal|" disconnected."
argument_list|)
expr_stmt|;
block|}
block|}
name|iProxy
operator|=
literal|null
expr_stmt|;
block|}
specifier|public
name|boolean
name|isConnected
parameter_list|()
block|{
return|return
operator|(
name|iSocket
operator|!=
literal|null
operator|&&
operator|!
name|iSocket
operator|.
name|isClosed
argument_list|()
operator|)
return|;
block|}
specifier|public
name|void
name|run
parameter_list|()
block|{
try|try
block|{
while|while
condition|(
operator|!
name|iFinish
condition|)
block|{
name|Object
name|command
init|=
literal|null
decl_stmt|;
try|try
block|{
name|command
operator|=
name|RemoteIo
operator|.
name|readObject
argument_list|(
name|iSocket
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
if|if
condition|(
name|command
operator|==
literal|null
condition|)
continue|continue;
name|Object
name|ret
init|=
literal|null
decl_stmt|;
try|try
block|{
name|ret
operator|=
name|answer
argument_list|(
name|command
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|ret
operator|=
name|e
expr_stmt|;
block|}
name|RemoteIo
operator|.
name|writeObject
argument_list|(
name|iSocket
argument_list|,
name|ret
argument_list|)
expr_stmt|;
block|}
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
block|}
name|unregister
argument_list|()
expr_stmt|;
try|try
block|{
name|iSocket
operator|.
name|close
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
block|}
specifier|public
name|long
name|lastActive
parameter_list|()
block|{
return|return
operator|(
name|System
operator|.
name|currentTimeMillis
argument_list|()
operator|-
name|iLastPing
operator|)
return|;
block|}
specifier|public
name|boolean
name|isActive
parameter_list|()
block|{
return|return
operator|(
name|lastActive
argument_list|()
operator|<
name|sTimeout
operator|)
return|;
block|}
specifier|private
name|Object
name|answer
parameter_list|(
name|Object
name|command
parameter_list|)
throws|throws
name|Exception
block|{
if|if
condition|(
literal|"quit"
operator|.
name|equals
argument_list|(
name|command
argument_list|)
condition|)
block|{
name|stopConnection
argument_list|()
expr_stmt|;
name|stopService
argument_list|()
expr_stmt|;
return|return
literal|"ack"
return|;
block|}
if|if
condition|(
literal|"ping"
operator|.
name|equals
argument_list|(
name|command
argument_list|)
condition|)
block|{
name|iLastPing
operator|=
name|System
operator|.
name|currentTimeMillis
argument_list|()
expr_stmt|;
if|if
condition|(
name|iProxy
operator|!=
literal|null
operator|&&
operator|!
name|iServers
operator|.
name|contains
argument_list|(
name|iProxy
argument_list|)
condition|)
block|{
name|sLog
operator|.
name|warn
argument_list|(
literal|"Server "
operator|+
name|iProxy
operator|+
literal|" is alive, but it is not registered."
argument_list|)
expr_stmt|;
name|iServers
operator|.
name|add
argument_list|(
name|iProxy
argument_list|)
expr_stmt|;
block|}
return|return
literal|"ack"
return|;
block|}
if|if
condition|(
literal|"url"
operator|.
name|equals
argument_list|(
name|command
argument_list|)
condition|)
block|{
return|return
name|HibernateUtil
operator|.
name|getConnectionUrl
argument_list|()
return|;
block|}
if|if
condition|(
literal|"properties"
operator|.
name|equals
argument_list|(
name|command
argument_list|)
condition|)
block|{
return|return
name|ApplicationProperties
operator|.
name|getProperties
argument_list|()
return|;
block|}
if|if
condition|(
literal|"disconnect"
operator|.
name|equals
argument_list|(
name|command
argument_list|)
condition|)
block|{
name|unregister
argument_list|()
expr_stmt|;
name|stopConnection
argument_list|()
expr_stmt|;
return|return
literal|"ack"
return|;
block|}
if|if
condition|(
name|command
operator|instanceof
name|Object
index|[]
condition|)
block|{
name|Object
name|cmd
index|[]
init|=
operator|(
name|Object
index|[]
operator|)
name|command
decl_stmt|;
if|if
condition|(
literal|"connect"
operator|.
name|equals
argument_list|(
name|cmd
index|[
literal|0
index|]
argument_list|)
condition|)
block|{
name|int
name|port
init|=
operator|(
operator|(
name|Integer
operator|)
name|cmd
index|[
literal|1
index|]
operator|)
operator|.
name|intValue
argument_list|()
decl_stmt|;
name|iProxy
operator|=
operator|new
name|RemoteSolverServerProxy
argument_list|(
name|iSocket
operator|.
name|getInetAddress
argument_list|()
argument_list|,
name|port
argument_list|,
name|this
argument_list|)
expr_stmt|;
name|sLog
operator|.
name|debug
argument_list|(
literal|"Sever "
operator|+
name|iProxy
operator|+
literal|" connected."
argument_list|)
expr_stmt|;
synchronized|synchronized
init|(
name|iServers
init|)
block|{
if|if
condition|(
name|iServers
operator|.
name|contains
argument_list|(
name|iProxy
argument_list|)
condition|)
block|{
name|sLog
operator|.
name|warn
argument_list|(
literal|"Previous run of the server "
operator|+
name|iProxy
operator|+
literal|" was not properly disconnected."
argument_list|)
expr_stmt|;
for|for
control|(
name|Iterator
name|i
init|=
name|iServers
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
name|RemoteSolverServerProxy
name|oldProxy
init|=
operator|(
name|RemoteSolverServerProxy
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|oldProxy
operator|.
name|equals
argument_list|(
name|iProxy
argument_list|)
condition|)
block|{
try|try
block|{
name|execute
argument_list|(
operator|new
name|DisconnectProxyCallback
argument_list|(
name|oldProxy
argument_list|)
argument_list|,
literal|10000
argument_list|)
expr_stmt|;
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
name|iServers
operator|.
name|remove
argument_list|(
name|iProxy
argument_list|)
expr_stmt|;
block|}
name|iServers
operator|.
name|add
argument_list|(
name|iProxy
argument_list|)
expr_stmt|;
block|}
return|return
literal|"ack"
return|;
block|}
if|if
condition|(
literal|"saveToFile"
operator|.
name|equals
argument_list|(
name|cmd
index|[
literal|0
index|]
argument_list|)
condition|)
block|{
name|TimetableInfoUtil
operator|.
name|getInstance
argument_list|()
operator|.
name|saveToFile
argument_list|(
operator|(
name|String
operator|)
name|cmd
index|[
literal|1
index|]
argument_list|,
operator|(
name|TimetableInfo
operator|)
name|cmd
index|[
literal|2
index|]
argument_list|)
expr_stmt|;
return|return
literal|"ack"
return|;
block|}
if|if
condition|(
literal|"loadFromFile"
operator|.
name|equals
argument_list|(
name|cmd
index|[
literal|0
index|]
argument_list|)
condition|)
block|{
return|return
name|TimetableInfoUtil
operator|.
name|getInstance
argument_list|()
operator|.
name|loadFromFile
argument_list|(
operator|(
name|String
operator|)
name|cmd
index|[
literal|1
index|]
argument_list|)
return|;
block|}
if|if
condition|(
literal|"deleteFile"
operator|.
name|equals
argument_list|(
name|cmd
index|[
literal|0
index|]
argument_list|)
condition|)
block|{
name|TimetableInfoUtil
operator|.
name|getInstance
argument_list|()
operator|.
name|deleteFile
argument_list|(
operator|(
name|String
operator|)
name|cmd
index|[
literal|1
index|]
argument_list|)
expr_stmt|;
return|return
literal|"ack"
return|;
block|}
if|if
condition|(
literal|"resource"
operator|.
name|equals
argument_list|(
name|cmd
index|[
literal|0
index|]
argument_list|)
condition|)
block|{
name|URL
name|resource
init|=
name|SolverRegisterService
operator|.
name|class
operator|.
name|getClassLoader
argument_list|()
operator|.
name|getResource
argument_list|(
operator|(
name|String
operator|)
name|cmd
index|[
literal|1
index|]
argument_list|)
decl_stmt|;
if|if
condition|(
name|resource
operator|==
literal|null
condition|)
return|return
literal|null
return|;
name|ByteArrayOutputStream
name|out
init|=
operator|new
name|ByteArrayOutputStream
argument_list|()
decl_stmt|;
name|InputStream
name|in
init|=
name|resource
operator|.
name|openStream
argument_list|()
decl_stmt|;
name|byte
index|[]
name|buffer
init|=
operator|new
name|byte
index|[
literal|1024
index|]
decl_stmt|;
name|int
name|read
init|=
literal|0
decl_stmt|;
while|while
condition|(
operator|(
name|read
operator|=
name|in
operator|.
name|read
argument_list|(
name|buffer
argument_list|)
operator|)
operator|>=
literal|0
condition|)
name|out
operator|.
name|write
argument_list|(
name|buffer
argument_list|,
literal|0
argument_list|,
name|read
argument_list|)
expr_stmt|;
name|out
operator|.
name|flush
argument_list|()
expr_stmt|;
name|out
operator|.
name|close
argument_list|()
expr_stmt|;
name|in
operator|.
name|close
argument_list|()
expr_stmt|;
return|return
name|out
operator|.
name|toByteArray
argument_list|()
return|;
block|}
if|if
condition|(
literal|"refreshSolution"
operator|.
name|equals
argument_list|(
name|cmd
index|[
literal|0
index|]
argument_list|)
condition|)
block|{
try|try
block|{
name|Solution
operator|.
name|refreshSolution
argument_list|(
operator|(
name|Long
operator|)
name|cmd
index|[
literal|1
index|]
argument_list|)
expr_stmt|;
block|}
finally|finally
block|{
name|_BaseRootDAO
operator|.
name|closeCurrentThreadSessions
argument_list|()
expr_stmt|;
block|}
return|return
literal|null
return|;
block|}
if|if
condition|(
literal|"refreshExamSolution"
operator|.
name|equals
argument_list|(
name|cmd
index|[
literal|0
index|]
argument_list|)
condition|)
block|{
try|try
block|{
name|ExamType
operator|.
name|refreshSolution
argument_list|(
operator|(
name|Long
operator|)
name|cmd
index|[
literal|1
index|]
argument_list|,
operator|(
name|Long
operator|)
name|cmd
index|[
literal|2
index|]
argument_list|)
expr_stmt|;
block|}
finally|finally
block|{
name|_BaseRootDAO
operator|.
name|closeCurrentThreadSessions
argument_list|()
expr_stmt|;
block|}
return|return
literal|null
return|;
block|}
if|if
condition|(
literal|"hasRoomAvailability"
operator|.
name|equals
argument_list|(
name|cmd
index|[
literal|0
index|]
argument_list|)
condition|)
block|{
return|return
operator|new
name|Boolean
argument_list|(
name|RoomAvailability
operator|.
name|getInstance
argument_list|()
operator|!=
literal|null
argument_list|)
return|;
block|}
if|if
condition|(
literal|"activateRoomAvailability"
operator|.
name|equals
argument_list|(
name|cmd
index|[
literal|0
index|]
argument_list|)
condition|)
block|{
if|if
condition|(
name|RoomAvailability
operator|.
name|getInstance
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|RoomAvailability
operator|.
name|getInstance
argument_list|()
operator|.
name|activate
argument_list|(
operator|new
name|SessionDAO
argument_list|()
operator|.
name|get
argument_list|(
operator|(
name|Long
operator|)
name|cmd
index|[
literal|1
index|]
argument_list|)
argument_list|,
operator|(
name|Date
operator|)
name|cmd
index|[
literal|2
index|]
argument_list|,
operator|(
name|Date
operator|)
name|cmd
index|[
literal|3
index|]
argument_list|,
operator|(
name|String
operator|)
name|cmd
index|[
literal|4
index|]
argument_list|,
literal|"true"
operator|.
name|equals
argument_list|(
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"tmtbl.room.availability.solver.waitForSync"
argument_list|,
literal|"true"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
return|return
literal|"ack"
return|;
block|}
return|return
literal|null
return|;
block|}
if|if
condition|(
literal|"getRoomAvailability"
operator|.
name|equals
argument_list|(
name|cmd
index|[
literal|0
index|]
argument_list|)
condition|)
block|{
if|if
condition|(
name|RoomAvailability
operator|.
name|getInstance
argument_list|()
operator|!=
literal|null
condition|)
block|{
return|return
name|RoomAvailability
operator|.
name|getInstance
argument_list|()
operator|.
name|getRoomAvailability
argument_list|(
operator|new
name|LocationDAO
argument_list|()
operator|.
name|get
argument_list|(
operator|(
name|Long
operator|)
name|cmd
index|[
literal|1
index|]
argument_list|)
argument_list|,
operator|(
name|Date
operator|)
name|cmd
index|[
literal|2
index|]
argument_list|,
operator|(
name|Date
operator|)
name|cmd
index|[
literal|3
index|]
argument_list|,
operator|(
name|String
operator|)
name|cmd
index|[
literal|4
index|]
argument_list|)
return|;
block|}
return|return
literal|null
return|;
block|}
if|if
condition|(
literal|"getRoomAvailabilityTimeStamp"
operator|.
name|equals
argument_list|(
name|cmd
index|[
literal|0
index|]
argument_list|)
condition|)
block|{
if|if
condition|(
name|RoomAvailability
operator|.
name|getInstance
argument_list|()
operator|!=
literal|null
condition|)
block|{
return|return
name|RoomAvailability
operator|.
name|getInstance
argument_list|()
operator|.
name|getTimeStamp
argument_list|(
operator|(
name|Date
operator|)
name|cmd
index|[
literal|1
index|]
argument_list|,
operator|(
name|Date
operator|)
name|cmd
index|[
literal|2
index|]
argument_list|,
operator|(
name|String
operator|)
name|cmd
index|[
literal|3
index|]
argument_list|)
return|;
block|}
return|return
literal|null
return|;
block|}
block|}
name|sLog
operator|.
name|warn
argument_list|(
literal|"Unknown command "
operator|+
name|command
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
block|}
block|}
specifier|protected
name|void
name|finalize
parameter_list|()
throws|throws
name|Throwable
block|{
try|try
block|{
if|if
condition|(
name|iServerSocket
operator|!=
literal|null
condition|)
name|iServerSocket
operator|.
name|close
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
name|iServerSocket
operator|=
literal|null
expr_stmt|;
name|super
operator|.
name|finalize
argument_list|()
expr_stmt|;
block|}
specifier|public
specifier|static
name|void
name|addShutdownHook
parameter_list|()
block|{
if|if
condition|(
name|sHook
operator|!=
literal|null
condition|)
name|removeShutdownHook
argument_list|()
expr_stmt|;
name|sHook
operator|=
operator|new
name|ShutdownHook
argument_list|()
expr_stmt|;
name|Runtime
operator|.
name|getRuntime
argument_list|()
operator|.
name|addShutdownHook
argument_list|(
name|sHook
argument_list|)
expr_stmt|;
block|}
specifier|public
specifier|static
name|void
name|removeShutdownHook
parameter_list|()
block|{
if|if
condition|(
name|sHook
operator|==
literal|null
condition|)
return|return;
name|Runtime
operator|.
name|getRuntime
argument_list|()
operator|.
name|removeShutdownHook
argument_list|(
name|sHook
argument_list|)
expr_stmt|;
name|sHook
operator|=
literal|null
expr_stmt|;
block|}
specifier|public
specifier|static
name|Object
name|execute
parameter_list|(
name|Callback
name|callback
parameter_list|,
name|long
name|timeout
parameter_list|)
throws|throws
name|Exception
block|{
name|Exec
name|ex
init|=
operator|new
name|Exec
argument_list|(
name|callback
argument_list|)
decl_stmt|;
if|if
condition|(
name|timeout
operator|<=
literal|0
condition|)
block|{
name|ex
operator|.
name|run
argument_list|()
expr_stmt|;
block|}
else|else
block|{
name|Thread
name|et
init|=
operator|new
name|Thread
argument_list|(
name|ex
argument_list|)
decl_stmt|;
name|et
operator|.
name|start
argument_list|()
expr_stmt|;
name|et
operator|.
name|join
argument_list|(
name|timeout
argument_list|)
expr_stmt|;
if|if
condition|(
name|et
operator|.
name|isAlive
argument_list|()
condition|)
name|et
operator|.
name|interrupt
argument_list|()
expr_stmt|;
block|}
if|if
condition|(
name|ex
operator|.
name|getAnswer
argument_list|()
operator|!=
literal|null
operator|&&
name|ex
operator|.
name|getAnswer
argument_list|()
operator|instanceof
name|Exception
condition|)
throw|throw
operator|(
name|Exception
operator|)
name|ex
operator|.
name|getAnswer
argument_list|()
throw|;
return|return
name|ex
operator|.
name|getAnswer
argument_list|()
return|;
block|}
specifier|static
class|class
name|Exec
implements|implements
name|Runnable
block|{
name|Callback
name|iCallback
init|=
literal|null
decl_stmt|;
name|Object
name|iAnswer
init|=
literal|null
decl_stmt|;
name|Exec
parameter_list|(
name|Callback
name|callback
parameter_list|)
block|{
name|iCallback
operator|=
name|callback
expr_stmt|;
block|}
specifier|public
name|void
name|run
parameter_list|()
block|{
try|try
block|{
name|iCallback
operator|.
name|execute
argument_list|()
expr_stmt|;
name|iAnswer
operator|=
literal|null
expr_stmt|;
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
literal|"Unable to execute a callback, reason: "
operator|+
name|e
operator|.
name|getMessage
argument_list|()
argument_list|,
name|e
argument_list|)
expr_stmt|;
name|iAnswer
operator|=
name|e
expr_stmt|;
block|}
block|}
specifier|public
name|Object
name|getAnswer
parameter_list|()
block|{
return|return
name|iAnswer
return|;
block|}
block|}
specifier|static
class|class
name|DisconnectProxyCallback
implements|implements
name|Callback
block|{
name|RemoteSolverServerProxy
name|iProxy
init|=
literal|null
decl_stmt|;
name|DisconnectProxyCallback
parameter_list|(
name|RemoteSolverServerProxy
name|proxy
parameter_list|)
block|{
name|iProxy
operator|=
name|proxy
expr_stmt|;
block|}
specifier|public
name|void
name|execute
parameter_list|()
block|{
name|iProxy
operator|.
name|disconnectProxy
argument_list|()
expr_stmt|;
block|}
block|}
specifier|public
name|Date
name|getStartTime
parameter_list|()
block|{
return|return
name|iStartTime
return|;
block|}
block|}
end_class

end_unit

