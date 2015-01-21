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
name|commons
operator|.
name|hibernate
operator|.
name|connection
package|;
end_package

begin_import
import|import
name|java
operator|.
name|sql
operator|.
name|Connection
import|;
end_import

begin_import
import|import
name|java
operator|.
name|sql
operator|.
name|SQLException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|text
operator|.
name|DecimalFormat
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
name|java
operator|.
name|util
operator|.
name|Properties
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
name|ToolBox
import|;
end_import

begin_import
import|import
name|org
operator|.
name|hibernate
operator|.
name|HibernateException
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|LoggingDBCPConnectionProvider
extends|extends
name|DBCPConnectionProvider
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|1L
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|Log
name|sLog
init|=
name|LogFactory
operator|.
name|getLog
argument_list|(
name|LoggingDBCPConnectionProvider
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|DecimalFormat
name|sDF
init|=
operator|new
name|DecimalFormat
argument_list|(
literal|"#,##0.00"
argument_list|)
decl_stmt|;
specifier|private
name|List
argument_list|<
name|Lease
argument_list|>
name|iLeases
init|=
operator|new
name|ArrayList
argument_list|<
name|Lease
argument_list|>
argument_list|()
decl_stmt|;
specifier|private
name|LeaseLogger
name|iLogger
init|=
literal|null
decl_stmt|;
annotation|@
name|Override
specifier|public
name|Connection
name|getConnection
parameter_list|()
throws|throws
name|SQLException
block|{
name|Connection
name|connection
init|=
name|super
operator|.
name|getConnection
argument_list|()
decl_stmt|;
synchronized|synchronized
init|(
name|iLeases
init|)
block|{
name|iLeases
operator|.
name|add
argument_list|(
operator|new
name|Lease
argument_list|(
name|connection
argument_list|)
argument_list|)
expr_stmt|;
block|}
return|return
name|connection
return|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|closeConnection
parameter_list|(
name|Connection
name|connection
parameter_list|)
throws|throws
name|SQLException
block|{
synchronized|synchronized
init|(
name|iLeases
init|)
block|{
for|for
control|(
name|Iterator
argument_list|<
name|Lease
argument_list|>
name|i
init|=
name|iLeases
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
name|Lease
name|lease
init|=
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|lease
operator|.
name|getConnection
argument_list|()
operator|.
name|equals
argument_list|(
name|connection
argument_list|)
condition|)
name|i
operator|.
name|remove
argument_list|()
expr_stmt|;
block|}
block|}
name|super
operator|.
name|closeConnection
argument_list|(
name|connection
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|configure
parameter_list|(
name|Properties
name|props
parameter_list|)
throws|throws
name|HibernateException
block|{
name|super
operator|.
name|configure
argument_list|(
name|props
argument_list|)
expr_stmt|;
name|iLogger
operator|=
operator|new
name|LeaseLogger
argument_list|()
expr_stmt|;
name|iLogger
operator|.
name|start
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|close
parameter_list|()
throws|throws
name|HibernateException
block|{
name|super
operator|.
name|close
argument_list|()
expr_stmt|;
name|iLogger
operator|.
name|interrupt
argument_list|()
expr_stmt|;
block|}
specifier|public
specifier|static
class|class
name|Lease
block|{
specifier|private
name|Connection
name|iConnection
decl_stmt|;
specifier|private
name|Thread
name|iThread
decl_stmt|;
specifier|private
name|StackTraceElement
index|[]
name|iTrace
decl_stmt|;
specifier|private
name|long
name|iTimeStamp
decl_stmt|;
specifier|public
name|Lease
parameter_list|(
name|Connection
name|connection
parameter_list|)
block|{
name|iConnection
operator|=
name|connection
expr_stmt|;
name|iThread
operator|=
name|Thread
operator|.
name|currentThread
argument_list|()
expr_stmt|;
name|iTrace
operator|=
name|Thread
operator|.
name|currentThread
argument_list|()
operator|.
name|getStackTrace
argument_list|()
expr_stmt|;
name|iTimeStamp
operator|=
name|System
operator|.
name|currentTimeMillis
argument_list|()
expr_stmt|;
block|}
specifier|public
name|Connection
name|getConnection
parameter_list|()
block|{
return|return
name|iConnection
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
condition|)
return|return
literal|false
return|;
if|if
condition|(
name|o
operator|instanceof
name|Connection
condition|)
return|return
name|getConnection
argument_list|()
operator|.
name|equals
argument_list|(
name|o
argument_list|)
return|;
if|if
condition|(
name|o
operator|instanceof
name|Lease
condition|)
return|return
name|getConnection
argument_list|()
operator|.
name|equals
argument_list|(
operator|(
operator|(
name|Lease
operator|)
name|o
operator|)
operator|.
name|getConnection
argument_list|()
argument_list|)
return|;
return|return
literal|false
return|;
block|}
specifier|public
name|double
name|getLeaseTime
parameter_list|()
block|{
return|return
operator|(
name|System
operator|.
name|currentTimeMillis
argument_list|()
operator|-
name|iTimeStamp
operator|)
operator|/
literal|1000.0
return|;
block|}
specifier|public
name|Thread
operator|.
name|State
name|getState
parameter_list|()
block|{
return|return
name|iThread
operator|.
name|getState
argument_list|()
return|;
block|}
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
name|iThread
operator|.
name|getName
argument_list|()
return|;
block|}
specifier|public
name|String
name|getStackTrace
parameter_list|()
block|{
name|int
name|first
init|=
literal|0
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|3
init|;
name|i
operator|<
name|iTrace
operator|.
name|length
condition|;
name|i
operator|++
control|)
if|if
condition|(
name|iTrace
index|[
name|i
index|]
operator|.
name|getClassName
argument_list|()
operator|.
name|startsWith
argument_list|(
literal|"org.unitime."
argument_list|)
operator|&&
operator|!
name|iTrace
index|[
name|i
index|]
operator|.
name|getClassName
argument_list|()
operator|.
name|endsWith
argument_list|(
literal|"._BaseRootDAO"
argument_list|)
condition|)
block|{
name|first
operator|=
name|i
expr_stmt|;
break|break;
block|}
name|StringBuffer
name|ret
init|=
operator|new
name|StringBuffer
argument_list|()
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
name|first
init|;
name|i
operator|<
name|iTrace
operator|.
name|length
condition|;
name|i
operator|++
control|)
name|ret
operator|.
name|append
argument_list|(
literal|"\n  "
operator|+
name|iTrace
index|[
name|i
index|]
argument_list|)
expr_stmt|;
return|return
name|ret
operator|.
name|toString
argument_list|()
return|;
block|}
specifier|public
name|String
name|toString
parameter_list|()
block|{
name|StackTraceElement
name|trace
init|=
literal|null
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|3
init|;
name|i
operator|<
name|iTrace
operator|.
name|length
condition|;
name|i
operator|++
control|)
if|if
condition|(
name|iTrace
index|[
name|i
index|]
operator|.
name|getClassName
argument_list|()
operator|.
name|startsWith
argument_list|(
literal|"org.unitime."
argument_list|)
operator|&&
operator|!
name|iTrace
index|[
name|i
index|]
operator|.
name|getClassName
argument_list|()
operator|.
name|endsWith
argument_list|(
literal|"._BaseRootDAO"
argument_list|)
condition|)
block|{
name|trace
operator|=
name|iTrace
index|[
name|i
index|]
expr_stmt|;
break|break;
block|}
return|return
name|sDF
operator|.
name|format
argument_list|(
name|getLeaseTime
argument_list|()
argument_list|)
operator|+
literal|" "
operator|+
name|getState
argument_list|()
operator|+
literal|" "
operator|+
name|getName
argument_list|()
operator|+
literal|" "
operator|+
name|trace
return|;
block|}
block|}
specifier|public
class|class
name|LeaseLogger
extends|extends
name|Thread
block|{
specifier|private
name|boolean
name|iActive
init|=
literal|true
decl_stmt|;
specifier|public
name|LeaseLogger
parameter_list|()
block|{
name|super
argument_list|(
literal|"DBCP:Logger"
argument_list|)
expr_stmt|;
name|setDaemon
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|run
parameter_list|()
block|{
name|sLog
operator|.
name|info
argument_list|(
literal|"Database connection pool logging is enabled."
argument_list|)
expr_stmt|;
while|while
condition|(
name|iActive
condition|)
block|{
try|try
block|{
try|try
block|{
name|sleep
argument_list|(
literal|60000
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|InterruptedException
name|e
parameter_list|)
block|{
block|}
synchronized|synchronized
init|(
name|iLeases
init|)
block|{
name|List
argument_list|<
name|Lease
argument_list|>
name|suspicious
init|=
operator|new
name|ArrayList
argument_list|<
name|Lease
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|Lease
name|lease
range|:
name|iLeases
control|)
if|if
condition|(
name|lease
operator|.
name|getLeaseTime
argument_list|()
operator|>
literal|60.0
operator|||
name|lease
operator|.
name|getState
argument_list|()
operator|==
name|State
operator|.
name|TERMINATED
condition|)
name|suspicious
operator|.
name|add
argument_list|(
name|lease
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
name|suspicious
operator|.
name|isEmpty
argument_list|()
condition|)
name|sLog
operator|.
name|info
argument_list|(
literal|"Suspicious leases:"
operator|+
name|ToolBox
operator|.
name|col2string
argument_list|(
name|iLeases
argument_list|,
literal|2
argument_list|)
argument_list|)
expr_stmt|;
for|for
control|(
name|Lease
name|lease
range|:
name|suspicious
control|)
if|if
condition|(
name|lease
operator|.
name|getState
argument_list|()
operator|==
name|State
operator|.
name|TERMINATED
condition|)
block|{
name|sLog
operator|.
name|fatal
argument_list|(
literal|"Releasing connection of a terminated thread "
operator|+
name|lease
operator|.
name|getName
argument_list|()
operator|+
literal|"."
operator|+
name|lease
operator|.
name|getStackTrace
argument_list|()
argument_list|)
expr_stmt|;
name|closeConnection
argument_list|(
name|lease
operator|.
name|getConnection
argument_list|()
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
name|warn
argument_list|(
literal|"Logging failed: "
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
annotation|@
name|Override
specifier|public
name|void
name|interrupt
parameter_list|()
block|{
name|iActive
operator|=
literal|false
expr_stmt|;
name|super
operator|.
name|interrupt
argument_list|()
expr_stmt|;
try|try
block|{
name|join
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|InterruptedException
name|e
parameter_list|)
block|{
block|}
block|}
block|}
block|}
end_class

end_unit

