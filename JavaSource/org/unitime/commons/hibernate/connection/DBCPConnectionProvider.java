begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * Copyright 2004 The Apache Software Foundation.  *   * Licensed under the Apache License, Version 2.0 (the "License");  * you may not use this file except in compliance with the License.  * You may obtain a copy of the License at  *   *      http://www.apache.org/licenses/LICENSE-2.0  *   * Unless required by applicable law or agreed to in writing, software  * distributed under the License is distributed on an "AS IS" BASIS,  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  * See the License for the specific language governing permissions and  * limitations under the License.  */
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
name|Properties
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
name|dbcp
operator|.
name|BasicDataSource
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
name|dbcp
operator|.
name|BasicDataSourceFactory
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
name|HibernateException
import|;
end_import

begin_import
import|import
name|org
operator|.
name|hibernate
operator|.
name|cfg
operator|.
name|Environment
import|;
end_import

begin_import
import|import
name|org
operator|.
name|hibernate
operator|.
name|engine
operator|.
name|jdbc
operator|.
name|connections
operator|.
name|spi
operator|.
name|ConnectionProvider
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

begin_comment
comment|/**  *<p>A connection provider that uses an Apache commons DBCP connection pool.</p>  *   *<p>To use this connection provider set:<br>  *<code>hibernate.connection.provider_class&nbsp;org.hibernate.connection.DBCPConnectionProvider</code></p>  *  *<pre>Supported Hibernate properties:  *   hibernate.connection.driver_class  *   hibernate.connection.url  *   hibernate.connection.username  *   hibernate.connection.password  *   hibernate.connection.isolation  *   hibernate.connection.autocommit  *   hibernate.connection.pool_size  *   hibernate.connection (JDBC driver properties)</pre>  *<br>  * All DBCP properties are also supported by using the hibernate.dbcp prefix.  * A complete list can be found on the DBCP configuration page:  *<a href="http://jakarta.apache.org/commons/dbcp/configuration.html">http://jakarta.apache.org/commons/dbcp/configuration.html</a>.  *<br>  *<pre>Example:  *   hibernate.connection.provider_class org.hibernate.connection.DBCPConnectionProvider  *   hibernate.connection.driver_class org.hsqldb.jdbcDriver  *   hibernate.connection.username sa  *   hibernate.connection.password  *   hibernate.connection.url jdbc:hsqldb:test  *   hibernate.connection.pool_size 20  *   hibernate.dbcp.initialSize 10  *   hibernate.dbcp.maxWait 3000  *   hibernate.dbcp.validationQuery select 1 from dual</pre>  *   *<p>More information about configuring/using DBCP can be found on the   *<a href="http://jakarta.apache.org/commons/dbcp/">DBCP website</a>.  * There you will also find the DBCP wiki, mailing lists, issue tracking   * and other support facilities</p>    *   * @see org.hibernate.connection.ConnectionProvider  * @author Dirk Verbeeck  */
end_comment

begin_class
specifier|public
class|class
name|DBCPConnectionProvider
implements|implements
name|ConnectionProvider
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
name|log
init|=
name|LogFactory
operator|.
name|getLog
argument_list|(
name|DBCPConnectionProvider
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
name|PREFIX
init|=
literal|"hibernate.dbcp."
decl_stmt|;
specifier|private
name|BasicDataSource
name|ds
decl_stmt|;
comment|// Old Environment property for backward-compatibility (property removed in Hibernate3)
specifier|private
specifier|static
specifier|final
name|String
name|DBCP_PS_MAXACTIVE
init|=
literal|"hibernate.dbcp.ps.maxActive"
decl_stmt|;
comment|// Property doesn't exists in Hibernate2
specifier|private
specifier|static
specifier|final
name|String
name|AUTOCOMMIT
init|=
literal|"hibernate.connection.autocommit"
decl_stmt|;
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
try|try
block|{
name|log
operator|.
name|debug
argument_list|(
literal|"Configure DBCPConnectionProvider"
argument_list|)
expr_stmt|;
comment|// DBCP properties used to create the BasicDataSource
name|Properties
name|dbcpProperties
init|=
operator|new
name|Properties
argument_list|()
decl_stmt|;
comment|// DriverClass& url
name|String
name|jdbcDriverClass
init|=
name|props
operator|.
name|getProperty
argument_list|(
name|Environment
operator|.
name|DRIVER
argument_list|)
decl_stmt|;
name|String
name|jdbcUrl
init|=
name|props
operator|.
name|getProperty
argument_list|(
name|Environment
operator|.
name|URL
argument_list|)
decl_stmt|;
name|dbcpProperties
operator|.
name|put
argument_list|(
literal|"driverClassName"
argument_list|,
name|jdbcDriverClass
argument_list|)
expr_stmt|;
name|dbcpProperties
operator|.
name|put
argument_list|(
literal|"url"
argument_list|,
name|jdbcUrl
argument_list|)
expr_stmt|;
comment|// Username / password
name|String
name|username
init|=
name|props
operator|.
name|getProperty
argument_list|(
name|Environment
operator|.
name|USER
argument_list|)
decl_stmt|;
name|String
name|password
init|=
name|props
operator|.
name|getProperty
argument_list|(
name|Environment
operator|.
name|PASS
argument_list|)
decl_stmt|;
name|dbcpProperties
operator|.
name|put
argument_list|(
literal|"username"
argument_list|,
name|username
argument_list|)
expr_stmt|;
name|dbcpProperties
operator|.
name|put
argument_list|(
literal|"password"
argument_list|,
name|password
argument_list|)
expr_stmt|;
comment|// Isolation level
name|String
name|isolationLevel
init|=
name|props
operator|.
name|getProperty
argument_list|(
name|Environment
operator|.
name|ISOLATION
argument_list|)
decl_stmt|;
if|if
condition|(
operator|(
name|isolationLevel
operator|!=
literal|null
operator|)
operator|&&
operator|(
name|isolationLevel
operator|.
name|trim
argument_list|()
operator|.
name|length
argument_list|()
operator|>
literal|0
operator|)
condition|)
block|{
name|dbcpProperties
operator|.
name|put
argument_list|(
literal|"defaultTransactionIsolation"
argument_list|,
name|isolationLevel
argument_list|)
expr_stmt|;
block|}
comment|// Turn off autocommit (unless autocommit property is set)
name|String
name|autocommit
init|=
name|props
operator|.
name|getProperty
argument_list|(
name|AUTOCOMMIT
argument_list|)
decl_stmt|;
if|if
condition|(
operator|(
name|autocommit
operator|!=
literal|null
operator|)
operator|&&
operator|(
name|autocommit
operator|.
name|trim
argument_list|()
operator|.
name|length
argument_list|()
operator|>
literal|0
operator|)
condition|)
block|{
name|dbcpProperties
operator|.
name|put
argument_list|(
literal|"defaultAutoCommit"
argument_list|,
name|autocommit
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|dbcpProperties
operator|.
name|put
argument_list|(
literal|"defaultAutoCommit"
argument_list|,
name|String
operator|.
name|valueOf
argument_list|(
name|Boolean
operator|.
name|FALSE
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|// Pool size
name|String
name|poolSize
init|=
name|props
operator|.
name|getProperty
argument_list|(
name|Environment
operator|.
name|POOL_SIZE
argument_list|)
decl_stmt|;
if|if
condition|(
operator|(
name|poolSize
operator|!=
literal|null
operator|)
operator|&&
operator|(
name|poolSize
operator|.
name|trim
argument_list|()
operator|.
name|length
argument_list|()
operator|>
literal|0
operator|)
operator|&&
operator|(
name|Integer
operator|.
name|parseInt
argument_list|(
name|poolSize
argument_list|)
operator|>
literal|0
operator|)
condition|)
block|{
name|dbcpProperties
operator|.
name|put
argument_list|(
literal|"maxActive"
argument_list|,
name|poolSize
argument_list|)
expr_stmt|;
block|}
comment|// Copy all "driver" properties into "connectionProperties"
name|Properties
name|driverProps
init|=
name|getConnectionProperties
argument_list|(
name|props
argument_list|)
decl_stmt|;
if|if
condition|(
name|driverProps
operator|.
name|size
argument_list|()
operator|>
literal|0
condition|)
block|{
name|StringBuffer
name|connectionProperties
init|=
operator|new
name|StringBuffer
argument_list|()
decl_stmt|;
for|for
control|(
name|Iterator
name|iter
init|=
name|driverProps
operator|.
name|keySet
argument_list|()
operator|.
name|iterator
argument_list|()
init|;
name|iter
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|String
name|key
init|=
operator|(
name|String
operator|)
name|iter
operator|.
name|next
argument_list|()
decl_stmt|;
name|String
name|value
init|=
name|driverProps
operator|.
name|getProperty
argument_list|(
name|key
argument_list|)
decl_stmt|;
name|connectionProperties
operator|.
name|append
argument_list|(
name|key
argument_list|)
operator|.
name|append
argument_list|(
literal|'='
argument_list|)
operator|.
name|append
argument_list|(
name|value
argument_list|)
expr_stmt|;
if|if
condition|(
name|iter
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|connectionProperties
operator|.
name|append
argument_list|(
literal|';'
argument_list|)
expr_stmt|;
block|}
block|}
name|dbcpProperties
operator|.
name|put
argument_list|(
literal|"connectionProperties"
argument_list|,
name|connectionProperties
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
comment|// Copy all DBCP properties removing the prefix
for|for
control|(
name|Iterator
name|iter
init|=
name|props
operator|.
name|keySet
argument_list|()
operator|.
name|iterator
argument_list|()
init|;
name|iter
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|String
name|key
init|=
name|String
operator|.
name|valueOf
argument_list|(
name|iter
operator|.
name|next
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|key
operator|.
name|startsWith
argument_list|(
name|PREFIX
argument_list|)
condition|)
block|{
name|String
name|property
init|=
name|key
operator|.
name|substring
argument_list|(
name|PREFIX
operator|.
name|length
argument_list|()
argument_list|)
decl_stmt|;
name|String
name|value
init|=
name|props
operator|.
name|getProperty
argument_list|(
name|key
argument_list|)
decl_stmt|;
name|dbcpProperties
operator|.
name|put
argument_list|(
name|property
argument_list|,
name|value
argument_list|)
expr_stmt|;
block|}
block|}
comment|// Backward-compatibility
if|if
condition|(
name|props
operator|.
name|getProperty
argument_list|(
name|DBCP_PS_MAXACTIVE
argument_list|)
operator|!=
literal|null
condition|)
block|{
name|dbcpProperties
operator|.
name|put
argument_list|(
literal|"poolPreparedStatements"
argument_list|,
name|String
operator|.
name|valueOf
argument_list|(
name|Boolean
operator|.
name|TRUE
argument_list|)
argument_list|)
expr_stmt|;
name|dbcpProperties
operator|.
name|put
argument_list|(
literal|"maxOpenPreparedStatements"
argument_list|,
name|props
operator|.
name|getProperty
argument_list|(
name|DBCP_PS_MAXACTIVE
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|// Let the factory create the pool
name|ds
operator|=
operator|(
name|BasicDataSource
operator|)
name|BasicDataSourceFactory
operator|.
name|createDataSource
argument_list|(
name|dbcpProperties
argument_list|)
expr_stmt|;
comment|// The BasicDataSource has lazy initialization
comment|// borrowing a connection will start the DataSource
comment|// and make sure it is configured correctly.
name|Connection
name|conn
init|=
name|ds
operator|.
name|getConnection
argument_list|()
decl_stmt|;
name|conn
operator|.
name|close
argument_list|()
expr_stmt|;
comment|// Log pool statistics before continuing.
name|logStatistics
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|String
name|message
init|=
literal|"Could not create a DBCP pool"
decl_stmt|;
name|log
operator|.
name|fatal
argument_list|(
name|message
argument_list|,
name|e
argument_list|)
expr_stmt|;
if|if
condition|(
name|ds
operator|!=
literal|null
condition|)
block|{
try|try
block|{
name|ds
operator|.
name|close
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e2
parameter_list|)
block|{
comment|// ignore
block|}
name|ds
operator|=
literal|null
expr_stmt|;
block|}
throw|throw
operator|new
name|HibernateException
argument_list|(
name|message
argument_list|,
name|e
argument_list|)
throw|;
block|}
name|log
operator|.
name|debug
argument_list|(
literal|"Configure DBCPConnectionProvider complete"
argument_list|)
expr_stmt|;
block|}
specifier|public
specifier|static
name|Properties
name|getConnectionProperties
parameter_list|(
name|Properties
name|properties
parameter_list|)
block|{
name|Iterator
name|iter
init|=
name|properties
operator|.
name|keySet
argument_list|()
operator|.
name|iterator
argument_list|()
decl_stmt|;
name|Properties
name|result
init|=
operator|new
name|Properties
argument_list|()
decl_stmt|;
while|while
condition|(
name|iter
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|String
name|prop
init|=
operator|(
name|String
operator|)
name|iter
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|prop
operator|.
name|indexOf
argument_list|(
name|Environment
operator|.
name|CONNECTION_PREFIX
argument_list|)
operator|>
operator|-
literal|1
operator|&&
operator|!
name|SPECIAL_PROPERTIES
operator|.
name|contains
argument_list|(
name|prop
argument_list|)
condition|)
block|{
name|result
operator|.
name|setProperty
argument_list|(
name|prop
operator|.
name|substring
argument_list|(
name|Environment
operator|.
name|CONNECTION_PREFIX
operator|.
name|length
argument_list|()
operator|+
literal|1
argument_list|)
argument_list|,
name|properties
operator|.
name|getProperty
argument_list|(
name|prop
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
name|String
name|userName
init|=
name|properties
operator|.
name|getProperty
argument_list|(
name|Environment
operator|.
name|USER
argument_list|)
decl_stmt|;
if|if
condition|(
name|userName
operator|!=
literal|null
condition|)
name|result
operator|.
name|setProperty
argument_list|(
literal|"user"
argument_list|,
name|userName
argument_list|)
expr_stmt|;
return|return
name|result
return|;
block|}
specifier|private
specifier|static
specifier|final
name|Set
name|SPECIAL_PROPERTIES
decl_stmt|;
static|static
block|{
name|SPECIAL_PROPERTIES
operator|=
operator|new
name|HashSet
argument_list|()
expr_stmt|;
name|SPECIAL_PROPERTIES
operator|.
name|add
argument_list|(
name|Environment
operator|.
name|DATASOURCE
argument_list|)
expr_stmt|;
name|SPECIAL_PROPERTIES
operator|.
name|add
argument_list|(
name|Environment
operator|.
name|URL
argument_list|)
expr_stmt|;
name|SPECIAL_PROPERTIES
operator|.
name|add
argument_list|(
name|Environment
operator|.
name|CONNECTION_PROVIDER
argument_list|)
expr_stmt|;
name|SPECIAL_PROPERTIES
operator|.
name|add
argument_list|(
name|Environment
operator|.
name|POOL_SIZE
argument_list|)
expr_stmt|;
name|SPECIAL_PROPERTIES
operator|.
name|add
argument_list|(
name|Environment
operator|.
name|ISOLATION
argument_list|)
expr_stmt|;
name|SPECIAL_PROPERTIES
operator|.
name|add
argument_list|(
name|Environment
operator|.
name|DRIVER
argument_list|)
expr_stmt|;
name|SPECIAL_PROPERTIES
operator|.
name|add
argument_list|(
name|Environment
operator|.
name|USER
argument_list|)
expr_stmt|;
block|}
specifier|public
name|Connection
name|getConnection
parameter_list|()
throws|throws
name|SQLException
block|{
if|if
condition|(
name|ds
operator|==
literal|null
condition|)
name|configure
argument_list|(
name|_BaseRootDAO
operator|.
name|getConfiguration
argument_list|()
operator|.
name|getProperties
argument_list|()
argument_list|)
expr_stmt|;
name|Connection
name|conn
init|=
literal|null
decl_stmt|;
try|try
block|{
name|conn
operator|=
name|ds
operator|.
name|getConnection
argument_list|()
expr_stmt|;
block|}
finally|finally
block|{
name|logStatistics
argument_list|()
expr_stmt|;
block|}
return|return
name|conn
return|;
block|}
specifier|public
name|void
name|closeConnection
parameter_list|(
name|Connection
name|conn
parameter_list|)
throws|throws
name|SQLException
block|{
try|try
block|{
name|conn
operator|.
name|close
argument_list|()
expr_stmt|;
block|}
finally|finally
block|{
name|logStatistics
argument_list|()
expr_stmt|;
block|}
block|}
specifier|public
name|void
name|close
parameter_list|()
throws|throws
name|HibernateException
block|{
name|log
operator|.
name|debug
argument_list|(
literal|"Close DBCPConnectionProvider"
argument_list|)
expr_stmt|;
name|logStatistics
argument_list|()
expr_stmt|;
try|try
block|{
if|if
condition|(
name|ds
operator|!=
literal|null
condition|)
block|{
name|ds
operator|.
name|close
argument_list|()
expr_stmt|;
name|ds
operator|=
literal|null
expr_stmt|;
block|}
else|else
block|{
name|log
operator|.
name|warn
argument_list|(
literal|"Cannot close DBCP pool (not initialized)"
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
throw|throw
operator|new
name|HibernateException
argument_list|(
literal|"Could not close DBCP pool"
argument_list|,
name|e
argument_list|)
throw|;
block|}
name|log
operator|.
name|debug
argument_list|(
literal|"Close DBCPConnectionProvider complete"
argument_list|)
expr_stmt|;
block|}
specifier|protected
name|void
name|logStatistics
parameter_list|()
block|{
if|if
condition|(
name|log
operator|.
name|isDebugEnabled
argument_list|()
condition|)
block|{
name|log
operator|.
name|debug
argument_list|(
literal|"active: "
operator|+
name|ds
operator|.
name|getNumActive
argument_list|()
operator|+
literal|" (max: "
operator|+
name|ds
operator|.
name|getMaxActive
argument_list|()
operator|+
literal|")   "
operator|+
literal|"idle: "
operator|+
name|ds
operator|.
name|getNumIdle
argument_list|()
operator|+
literal|"(max: "
operator|+
name|ds
operator|.
name|getMaxIdle
argument_list|()
operator|+
literal|")"
argument_list|)
expr_stmt|;
block|}
block|}
specifier|public
name|boolean
name|supportsAggressiveRelease
parameter_list|()
block|{
return|return
literal|false
return|;
block|}
specifier|public
name|boolean
name|isUnwrappableAs
parameter_list|(
name|Class
name|arg0
parameter_list|)
block|{
return|return
literal|false
return|;
block|}
specifier|public
parameter_list|<
name|T
parameter_list|>
name|T
name|unwrap
parameter_list|(
name|Class
argument_list|<
name|T
argument_list|>
name|arg0
parameter_list|)
block|{
return|return
literal|null
return|;
block|}
block|}
end_class

end_unit

