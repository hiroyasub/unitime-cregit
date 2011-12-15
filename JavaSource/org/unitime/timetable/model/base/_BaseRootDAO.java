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
name|model
operator|.
name|base
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|Serializable
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|HashMap
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
name|Map
import|;
end_import

begin_import
import|import
name|org
operator|.
name|hibernate
operator|.
name|Criteria
import|;
end_import

begin_import
import|import
name|org
operator|.
name|hibernate
operator|.
name|Hibernate
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
name|Query
import|;
end_import

begin_import
import|import
name|org
operator|.
name|hibernate
operator|.
name|Session
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
name|hibernate
operator|.
name|SessionFactory
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
name|Configuration
import|;
end_import

begin_import
import|import
name|org
operator|.
name|hibernate
operator|.
name|criterion
operator|.
name|Order
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
name|DatabaseUpdate
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

begin_class
specifier|public
specifier|abstract
class|class
name|_BaseRootDAO
parameter_list|<
name|T
parameter_list|,
name|K
extends|extends
name|Serializable
parameter_list|>
block|{
specifier|protected
specifier|static
name|Map
argument_list|<
name|String
argument_list|,
name|SessionFactory
argument_list|>
name|sSessionFactoryMap
decl_stmt|;
specifier|protected
specifier|static
name|SessionFactory
name|sSessionFactory
decl_stmt|;
specifier|protected
specifier|static
name|ThreadLocal
argument_list|<
name|HashMap
argument_list|<
name|String
argument_list|,
name|Session
argument_list|>
argument_list|>
name|sMappedSessions
decl_stmt|;
specifier|protected
specifier|static
name|ThreadLocal
argument_list|<
name|Session
argument_list|>
name|sSessions
decl_stmt|;
specifier|protected
specifier|static
name|Configuration
name|sConfiguration
decl_stmt|;
comment|/** 	 * Configure the session factory by reading hibernate config file 	 */
specifier|public
specifier|static
name|void
name|initialize
parameter_list|()
block|{
name|initialize
argument_list|(
literal|null
argument_list|)
expr_stmt|;
block|}
comment|/** 	 * Configure the session factory by reading hibernate config file 	 * @param configFileName the name of the configuration file 	 */
specifier|public
specifier|static
name|void
name|initialize
parameter_list|(
name|String
name|configFileName
parameter_list|)
block|{
name|initialize
argument_list|(
name|configFileName
argument_list|,
name|getNewConfiguration
argument_list|(
literal|null
argument_list|)
argument_list|)
expr_stmt|;
block|}
specifier|public
specifier|static
name|void
name|initialize
parameter_list|(
name|String
name|configFileName
parameter_list|,
name|Configuration
name|configuration
parameter_list|)
block|{
if|if
condition|(
name|configFileName
operator|==
literal|null
operator|&&
name|sSessionFactory
operator|!=
literal|null
condition|)
return|return;
if|if
condition|(
name|sSessionFactoryMap
operator|!=
literal|null
operator|&&
name|sSessionFactoryMap
operator|.
name|get
argument_list|(
name|configFileName
argument_list|)
operator|!=
literal|null
condition|)
return|return;
name|HibernateUtil
operator|.
name|configureHibernateFromRootDAO
argument_list|(
name|configFileName
argument_list|,
name|configuration
argument_list|)
expr_stmt|;
name|setSessionFactory
argument_list|(
name|configuration
operator|.
name|buildSessionFactory
argument_list|()
argument_list|)
expr_stmt|;
name|sConfiguration
operator|=
name|configuration
expr_stmt|;
name|DatabaseUpdate
operator|.
name|update
argument_list|()
expr_stmt|;
block|}
comment|/** 	 * Set the session factory 	 */
specifier|protected
specifier|static
name|void
name|setSessionFactory
parameter_list|(
name|SessionFactory
name|sessionFactory
parameter_list|)
block|{
name|setSessionFactory
argument_list|(
literal|null
argument_list|,
name|sessionFactory
argument_list|)
expr_stmt|;
block|}
comment|/** 	 * Set the session factory 	 */
specifier|protected
specifier|static
name|void
name|setSessionFactory
parameter_list|(
name|String
name|configFileName
parameter_list|,
name|SessionFactory
name|sessionFactory
parameter_list|)
block|{
if|if
condition|(
name|configFileName
operator|==
literal|null
condition|)
block|{
name|sSessionFactory
operator|=
name|sessionFactory
expr_stmt|;
block|}
else|else
block|{
if|if
condition|(
name|sSessionFactoryMap
operator|==
literal|null
condition|)
name|sSessionFactoryMap
operator|=
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|SessionFactory
argument_list|>
argument_list|()
expr_stmt|;
name|sSessionFactoryMap
operator|.
name|put
argument_list|(
name|configFileName
argument_list|,
name|sessionFactory
argument_list|)
expr_stmt|;
block|}
block|}
comment|/** 	 * Return the SessionFactory that is to be used by these DAOs.  Change this 	 * and implement your own strategy if you, for example, want to pull the SessionFactory 	 * from the JNDI tree. 	 */
specifier|protected
name|SessionFactory
name|getSessionFactory
parameter_list|()
block|{
return|return
name|getSessionFactory
argument_list|(
name|getConfigurationFileName
argument_list|()
argument_list|)
return|;
block|}
specifier|protected
name|SessionFactory
name|getSessionFactory
parameter_list|(
name|String
name|configFile
parameter_list|)
block|{
if|if
condition|(
name|configFile
operator|==
literal|null
condition|)
block|{
if|if
condition|(
name|sSessionFactory
operator|==
literal|null
condition|)
throw|throw
operator|new
name|RuntimeException
argument_list|(
literal|"The session factory has not been initialized (or an error occured during initialization)"
argument_list|)
throw|;
else|else
return|return
name|sSessionFactory
return|;
block|}
else|else
block|{
if|if
condition|(
name|sSessionFactoryMap
operator|==
literal|null
condition|)
throw|throw
operator|new
name|RuntimeException
argument_list|(
literal|"The session factory for '"
operator|+
name|configFile
operator|+
literal|"' has not been initialized (or an error occured during initialization)"
argument_list|)
throw|;
else|else
block|{
name|SessionFactory
name|sessionFactory
init|=
operator|(
name|SessionFactory
operator|)
name|sSessionFactoryMap
operator|.
name|get
argument_list|(
name|configFile
argument_list|)
decl_stmt|;
if|if
condition|(
name|sessionFactory
operator|==
literal|null
condition|)
throw|throw
operator|new
name|RuntimeException
argument_list|(
literal|"The session factory for '"
operator|+
name|configFile
operator|+
literal|"' has not been initialized (or an error occured during initialization)"
argument_list|)
throw|;
else|else
return|return
name|sessionFactory
return|;
block|}
block|}
block|}
comment|/** 	 * Return a new Session object that must be closed when the work has been completed. 	 * @return the active Session 	 */
specifier|public
name|Session
name|getSession
parameter_list|()
block|{
return|return
name|getSession
argument_list|(
name|getConfigurationFileName
argument_list|()
argument_list|,
literal|false
argument_list|)
return|;
block|}
comment|/** 	 * Return a new Session object that must be closed when the work has been completed. 	 * @return the active Session 	 */
specifier|public
name|Session
name|createNewSession
parameter_list|()
block|{
return|return
name|getSession
argument_list|(
name|getConfigurationFileName
argument_list|()
argument_list|,
literal|true
argument_list|)
return|;
block|}
comment|/** 	 * Return a new Session object that must be closed when the work has been completed. 	 * @param configFile the config file must match the meta attribute "config-file" in the hibernate mapping file 	 * @return the active Session 	 */
specifier|private
name|Session
name|getSession
parameter_list|(
name|String
name|configFile
parameter_list|,
name|boolean
name|createNew
parameter_list|)
block|{
if|if
condition|(
name|createNew
condition|)
block|{
return|return
name|getSessionFactory
argument_list|(
name|configFile
argument_list|)
operator|.
name|openSession
argument_list|()
return|;
block|}
else|else
block|{
if|if
condition|(
name|configFile
operator|==
literal|null
condition|)
block|{
if|if
condition|(
name|sSessions
operator|==
literal|null
condition|)
name|sSessions
operator|=
operator|new
name|ThreadLocal
argument_list|<
name|Session
argument_list|>
argument_list|()
expr_stmt|;
name|Session
name|session
init|=
name|sSessions
operator|.
name|get
argument_list|()
decl_stmt|;
if|if
condition|(
name|session
operator|==
literal|null
operator|||
operator|!
name|session
operator|.
name|isOpen
argument_list|()
condition|)
block|{
name|session
operator|=
name|getSessionFactory
argument_list|(
literal|null
argument_list|)
operator|.
name|openSession
argument_list|()
expr_stmt|;
name|sSessions
operator|.
name|set
argument_list|(
name|session
argument_list|)
expr_stmt|;
block|}
return|return
name|session
return|;
block|}
else|else
block|{
if|if
condition|(
name|sMappedSessions
operator|==
literal|null
condition|)
name|sMappedSessions
operator|=
operator|new
name|ThreadLocal
argument_list|<
name|HashMap
argument_list|<
name|String
argument_list|,
name|Session
argument_list|>
argument_list|>
argument_list|()
expr_stmt|;
name|HashMap
argument_list|<
name|String
argument_list|,
name|Session
argument_list|>
name|map
init|=
name|sMappedSessions
operator|.
name|get
argument_list|()
decl_stmt|;
if|if
condition|(
name|map
operator|==
literal|null
condition|)
block|{
name|map
operator|=
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|Session
argument_list|>
argument_list|()
expr_stmt|;
name|sMappedSessions
operator|.
name|set
argument_list|(
name|map
argument_list|)
expr_stmt|;
block|}
name|Session
name|session
init|=
name|map
operator|.
name|get
argument_list|(
name|configFile
argument_list|)
decl_stmt|;
if|if
condition|(
name|session
operator|==
literal|null
operator|||
operator|!
name|session
operator|.
name|isOpen
argument_list|()
condition|)
block|{
name|session
operator|=
name|getSessionFactory
argument_list|(
name|configFile
argument_list|)
operator|.
name|openSession
argument_list|()
expr_stmt|;
name|map
operator|.
name|put
argument_list|(
name|configFile
argument_list|,
name|session
argument_list|)
expr_stmt|;
block|}
return|return
name|session
return|;
block|}
block|}
block|}
comment|/** 	 * Get current thread opened session, if there is any 	 */
specifier|public
name|Session
name|getCurrentThreadSession
parameter_list|()
block|{
return|return
name|getCurrentThreadSession
argument_list|(
name|getConfigurationFileName
argument_list|()
argument_list|)
return|;
block|}
comment|/** 	 * Get current thread opened session, if there is any 	 */
specifier|private
name|Session
name|getCurrentThreadSession
parameter_list|(
name|String
name|configFile
parameter_list|)
block|{
if|if
condition|(
name|configFile
operator|==
literal|null
condition|)
block|{
if|if
condition|(
name|sSessions
operator|!=
literal|null
condition|)
block|{
name|Session
name|session
init|=
name|sSessions
operator|.
name|get
argument_list|()
decl_stmt|;
if|if
condition|(
name|session
operator|!=
literal|null
condition|)
return|return
name|session
return|;
block|}
block|}
else|else
block|{
if|if
condition|(
name|sMappedSessions
operator|!=
literal|null
condition|)
block|{
name|HashMap
argument_list|<
name|String
argument_list|,
name|Session
argument_list|>
name|map
init|=
name|sMappedSessions
operator|.
name|get
argument_list|()
decl_stmt|;
if|if
condition|(
name|map
operator|!=
literal|null
condition|)
return|return
name|map
operator|.
name|get
argument_list|(
name|configFile
argument_list|)
return|;
block|}
block|}
return|return
literal|null
return|;
block|}
comment|/** 	 * Close all sessions for the current thread 	 */
specifier|public
specifier|static
name|boolean
name|closeCurrentThreadSessions
parameter_list|()
block|{
name|boolean
name|ret
init|=
literal|false
decl_stmt|;
if|if
condition|(
name|sSessions
operator|!=
literal|null
condition|)
block|{
name|Session
name|session
init|=
name|sSessions
operator|.
name|get
argument_list|()
decl_stmt|;
if|if
condition|(
name|session
operator|!=
literal|null
operator|&&
name|session
operator|.
name|isOpen
argument_list|()
condition|)
block|{
name|session
operator|.
name|close
argument_list|()
expr_stmt|;
name|ret
operator|=
literal|true
expr_stmt|;
block|}
block|}
if|if
condition|(
name|sMappedSessions
operator|!=
literal|null
condition|)
block|{
name|HashMap
argument_list|<
name|String
argument_list|,
name|Session
argument_list|>
name|map
init|=
name|sMappedSessions
operator|.
name|get
argument_list|()
decl_stmt|;
if|if
condition|(
name|map
operator|!=
literal|null
condition|)
block|{
name|HibernateException
name|thrownException
init|=
literal|null
decl_stmt|;
for|for
control|(
name|Session
name|session
range|:
name|map
operator|.
name|values
argument_list|()
control|)
block|{
try|try
block|{
if|if
condition|(
literal|null
operator|!=
name|session
operator|&&
name|session
operator|.
name|isOpen
argument_list|()
condition|)
block|{
name|session
operator|.
name|close
argument_list|()
expr_stmt|;
name|ret
operator|=
literal|true
expr_stmt|;
block|}
block|}
catch|catch
parameter_list|(
name|HibernateException
name|e
parameter_list|)
block|{
name|thrownException
operator|=
name|e
expr_stmt|;
block|}
block|}
name|map
operator|.
name|clear
argument_list|()
expr_stmt|;
if|if
condition|(
literal|null
operator|!=
name|thrownException
condition|)
throw|throw
name|thrownException
throw|;
block|}
block|}
return|return
name|ret
return|;
block|}
comment|/** 	 * Begin the transaction related to the session 	 */
specifier|public
name|Transaction
name|beginTransaction
parameter_list|(
name|Session
name|s
parameter_list|)
block|{
comment|// already in a transaction, do not create a new one
if|if
condition|(
name|s
operator|.
name|getTransaction
argument_list|()
operator|!=
literal|null
operator|&&
name|s
operator|.
name|getTransaction
argument_list|()
operator|.
name|isActive
argument_list|()
condition|)
return|return
literal|null
return|;
return|return
name|s
operator|.
name|beginTransaction
argument_list|()
return|;
block|}
comment|/** 	 * Commit the given transaction 	 */
specifier|public
name|void
name|commitTransaction
parameter_list|(
name|Transaction
name|t
parameter_list|)
block|{
if|if
condition|(
name|t
operator|!=
literal|null
condition|)
name|t
operator|.
name|commit
argument_list|()
expr_stmt|;
block|}
comment|/** 	 * Return a new Configuration to use 	 */
specifier|public
specifier|static
name|Configuration
name|getNewConfiguration
parameter_list|(
name|String
name|configFileName
parameter_list|)
block|{
return|return
operator|new
name|Configuration
argument_list|()
return|;
block|}
comment|/** 	 * @return Returns the configuration. 	 */
specifier|public
specifier|static
name|Configuration
name|getConfiguration
parameter_list|()
block|{
return|return
name|sConfiguration
return|;
block|}
comment|/** 	 * Return the name of the configuration file to be used with this DAO or null if default 	 */
specifier|public
name|String
name|getConfigurationFileName
parameter_list|()
block|{
return|return
literal|null
return|;
block|}
comment|/** 	 * Return the specific Object class that will be used for class-specific 	 * implementation of this DAO. 	 * @return the reference Class 	 */
specifier|protected
specifier|abstract
name|Class
argument_list|<
name|T
argument_list|>
name|getReferenceClass
parameter_list|()
function_decl|;
comment|/** 	 * Used by the base DAO classes but here for your modification 	 * Get object matching the given key and return it. 	 */
specifier|protected
name|T
name|get
parameter_list|(
name|Class
argument_list|<
name|T
argument_list|>
name|refClass
parameter_list|,
name|K
name|key
parameter_list|)
block|{
return|return
name|get
argument_list|(
name|refClass
argument_list|,
name|key
argument_list|,
name|getSession
argument_list|()
argument_list|)
return|;
block|}
comment|/** 	 * Get object matching the given key and return it. 	 */
specifier|public
name|T
name|get
parameter_list|(
name|K
name|key
parameter_list|)
block|{
return|return
name|get
argument_list|(
name|getReferenceClass
argument_list|()
argument_list|,
name|key
argument_list|)
return|;
block|}
comment|/** 	 * Used by the base DAO classes but here for your modification 	 * Get object matching the given key and return it. 	 */
annotation|@
name|SuppressWarnings
argument_list|(
literal|"unchecked"
argument_list|)
specifier|protected
name|T
name|get
parameter_list|(
name|Class
argument_list|<
name|T
argument_list|>
name|refClass
parameter_list|,
name|K
name|key
parameter_list|,
name|Session
name|s
parameter_list|)
block|{
return|return
operator|(
name|T
operator|)
name|s
operator|.
name|get
argument_list|(
name|refClass
argument_list|,
name|key
argument_list|)
return|;
block|}
comment|/** 	 * Get object matching the given key and return it. 	 */
specifier|public
name|T
name|get
parameter_list|(
name|K
name|key
parameter_list|,
name|Session
name|s
parameter_list|)
block|{
return|return
name|get
argument_list|(
name|getReferenceClass
argument_list|()
argument_list|,
name|key
argument_list|,
name|s
argument_list|)
return|;
block|}
comment|/** 	 * Used by the base DAO classes but here for your modification 	 * Load object matching the given key and return it. 	 */
specifier|protected
name|T
name|load
parameter_list|(
name|Class
argument_list|<
name|T
argument_list|>
name|refClass
parameter_list|,
name|K
name|key
parameter_list|)
block|{
return|return
name|load
argument_list|(
name|refClass
argument_list|,
name|key
argument_list|,
name|getSession
argument_list|()
argument_list|)
return|;
block|}
comment|/** 	 * Load object matching the given key and return it. 	 */
specifier|public
name|T
name|load
parameter_list|(
name|K
name|key
parameter_list|)
block|{
return|return
name|load
argument_list|(
name|getReferenceClass
argument_list|()
argument_list|,
name|key
argument_list|)
return|;
block|}
comment|/** 	 * Used by the base DAO classes but here for your modification 	 * Load object matching the given key and return it. 	 */
annotation|@
name|SuppressWarnings
argument_list|(
literal|"unchecked"
argument_list|)
specifier|protected
name|T
name|load
parameter_list|(
name|Class
argument_list|<
name|T
argument_list|>
name|refClass
parameter_list|,
name|K
name|key
parameter_list|,
name|Session
name|s
parameter_list|)
block|{
return|return
operator|(
name|T
operator|)
name|s
operator|.
name|load
argument_list|(
name|refClass
argument_list|,
name|key
argument_list|)
return|;
block|}
comment|/** 	 * Load object matching the given key and return it. 	 */
specifier|public
name|T
name|load
parameter_list|(
name|K
name|key
parameter_list|,
name|Session
name|s
parameter_list|)
block|{
return|return
name|load
argument_list|(
name|getReferenceClass
argument_list|()
argument_list|,
name|key
argument_list|,
name|s
argument_list|)
return|;
block|}
comment|/** 	 * Load and initialize object matching the given key and return it. 	 */
specifier|public
name|T
name|loadInitialize
parameter_list|(
name|K
name|key
parameter_list|,
name|Session
name|s
parameter_list|)
block|{
name|T
name|obj
init|=
name|load
argument_list|(
name|key
argument_list|,
name|s
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|Hibernate
operator|.
name|isInitialized
argument_list|(
name|obj
argument_list|)
condition|)
name|Hibernate
operator|.
name|initialize
argument_list|(
name|obj
argument_list|)
expr_stmt|;
return|return
name|obj
return|;
block|}
comment|/** 	 * Return all objects related to the implementation of this DAO with no filter. 	 */
specifier|public
name|List
argument_list|<
name|T
argument_list|>
name|findAll
parameter_list|()
block|{
return|return
name|findAll
argument_list|(
name|getSession
argument_list|()
argument_list|)
return|;
block|}
comment|/** 	 * Return all objects related to the implementation of this DAO with no filter. 	 * Use the session given. 	 * @param s the Session 	 */
specifier|public
name|List
argument_list|<
name|T
argument_list|>
name|findAll
parameter_list|(
name|Session
name|s
parameter_list|)
block|{
return|return
name|findAll
argument_list|(
name|s
argument_list|,
name|getDefaultOrder
argument_list|()
argument_list|)
return|;
block|}
comment|/** 	 * Return all objects related to the implementation of this DAO with no filter. 	 * The results are ordered by the order specified 	 * Use the session given. 	 */
specifier|public
name|List
argument_list|<
name|T
argument_list|>
name|findAll
parameter_list|(
name|Order
modifier|...
name|orders
parameter_list|)
block|{
return|return
name|findAll
argument_list|(
name|getSession
argument_list|()
argument_list|,
name|orders
argument_list|)
return|;
block|}
comment|/** 	 * Return all objects related to the implementation of this DAO with no filter. 	 * Use the session given. 	 */
annotation|@
name|SuppressWarnings
argument_list|(
literal|"unchecked"
argument_list|)
specifier|public
name|List
argument_list|<
name|T
argument_list|>
name|findAll
parameter_list|(
name|Session
name|s
parameter_list|,
name|Order
modifier|...
name|orders
parameter_list|)
block|{
name|Criteria
name|crit
init|=
name|s
operator|.
name|createCriteria
argument_list|(
name|getReferenceClass
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|orders
operator|!=
literal|null
condition|)
block|{
for|for
control|(
name|Order
name|order
range|:
name|orders
control|)
block|{
if|if
condition|(
name|order
operator|!=
literal|null
condition|)
name|crit
operator|.
name|addOrder
argument_list|(
name|order
argument_list|)
expr_stmt|;
block|}
block|}
name|crit
operator|.
name|setCacheable
argument_list|(
literal|true
argument_list|)
expr_stmt|;
return|return
operator|(
name|List
argument_list|<
name|T
argument_list|>
operator|)
name|crit
operator|.
name|list
argument_list|()
return|;
block|}
comment|/** 	 * Execute a query.  	 * @param queryStr a query expressed in Hibernate's query language 	 * @return a distinct list of instances (or arrays of instances) 	 */
specifier|public
name|Query
name|getQuery
parameter_list|(
name|String
name|queryStr
parameter_list|)
block|{
return|return
name|getQuery
argument_list|(
name|queryStr
argument_list|,
name|getSession
argument_list|()
argument_list|)
return|;
block|}
comment|/** 	 * Execute a query but use the session given instead of creating a new one. 	 * @param queryStr a query expressed in Hibernate's query language 	 * @param s the Session to use 	 */
specifier|public
name|Query
name|getQuery
parameter_list|(
name|String
name|queryStr
parameter_list|,
name|Session
name|s
parameter_list|)
block|{
return|return
name|s
operator|.
name|createQuery
argument_list|(
name|queryStr
argument_list|)
return|;
block|}
specifier|protected
name|Order
name|getDefaultOrder
parameter_list|()
block|{
return|return
literal|null
return|;
block|}
comment|/** 	 * Persist the given transient instance, first assigning a generated identifier.  	 * (Or using the current value of the identifier property if the assigned generator is used.)  	 */
specifier|public
name|K
name|save
parameter_list|(
name|T
name|obj
parameter_list|)
block|{
name|Transaction
name|t
init|=
literal|null
decl_stmt|;
name|Session
name|s
init|=
literal|null
decl_stmt|;
try|try
block|{
name|s
operator|=
name|getSession
argument_list|()
expr_stmt|;
name|t
operator|=
name|beginTransaction
argument_list|(
name|s
argument_list|)
expr_stmt|;
name|K
name|rtn
init|=
name|save
argument_list|(
name|obj
argument_list|,
name|s
argument_list|)
decl_stmt|;
name|commitTransaction
argument_list|(
name|t
argument_list|)
expr_stmt|;
return|return
name|rtn
return|;
block|}
catch|catch
parameter_list|(
name|HibernateException
name|e
parameter_list|)
block|{
if|if
condition|(
literal|null
operator|!=
name|t
condition|)
name|t
operator|.
name|rollback
argument_list|()
expr_stmt|;
throw|throw
name|e
throw|;
block|}
block|}
comment|/** 	 * Persist the given transient instance, first assigning a generated identifier.  	 * (Or using the current value of the identifier property if the assigned generator is used.)  	 */
annotation|@
name|SuppressWarnings
argument_list|(
literal|"unchecked"
argument_list|)
specifier|public
name|K
name|save
parameter_list|(
name|T
name|obj
parameter_list|,
name|Session
name|s
parameter_list|)
block|{
return|return
operator|(
name|K
operator|)
name|s
operator|.
name|save
argument_list|(
name|obj
argument_list|)
return|;
block|}
comment|/** 	 * Either save() or update() the given instance, depending upon the value of its 	 * identifier property. 	 */
specifier|public
name|void
name|saveOrUpdate
parameter_list|(
name|T
name|obj
parameter_list|)
block|{
name|Transaction
name|t
init|=
literal|null
decl_stmt|;
name|Session
name|s
init|=
literal|null
decl_stmt|;
try|try
block|{
name|s
operator|=
name|getSession
argument_list|()
expr_stmt|;
name|t
operator|=
name|beginTransaction
argument_list|(
name|s
argument_list|)
expr_stmt|;
name|saveOrUpdate
argument_list|(
name|obj
argument_list|,
name|s
argument_list|)
expr_stmt|;
name|commitTransaction
argument_list|(
name|t
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|HibernateException
name|e
parameter_list|)
block|{
if|if
condition|(
literal|null
operator|!=
name|t
condition|)
name|t
operator|.
name|rollback
argument_list|()
expr_stmt|;
throw|throw
name|e
throw|;
block|}
block|}
comment|/** 	 * Either save() or update() the given instance, depending upon the value of its 	 * identifier property. 	 */
specifier|public
name|void
name|saveOrUpdate
parameter_list|(
name|T
name|obj
parameter_list|,
name|Session
name|s
parameter_list|)
block|{
name|s
operator|.
name|saveOrUpdate
argument_list|(
name|obj
argument_list|)
expr_stmt|;
block|}
comment|/** 	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent 	 * instance with the same identifier in the current session. 	 * @param obj a transient instance containing updated state 	 */
specifier|public
name|void
name|update
parameter_list|(
name|T
name|obj
parameter_list|)
block|{
name|Transaction
name|t
init|=
literal|null
decl_stmt|;
name|Session
name|s
init|=
literal|null
decl_stmt|;
try|try
block|{
name|s
operator|=
name|getSession
argument_list|()
expr_stmt|;
name|t
operator|=
name|beginTransaction
argument_list|(
name|s
argument_list|)
expr_stmt|;
name|update
argument_list|(
name|obj
argument_list|,
name|s
argument_list|)
expr_stmt|;
name|commitTransaction
argument_list|(
name|t
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|HibernateException
name|e
parameter_list|)
block|{
if|if
condition|(
literal|null
operator|!=
name|t
condition|)
name|t
operator|.
name|rollback
argument_list|()
expr_stmt|;
throw|throw
name|e
throw|;
block|}
block|}
comment|/** 	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent 	 * instance with the same identifier in the current session. 	 * @param obj a transient instance containing updated state 	 * @param s the Session 	 */
specifier|public
name|void
name|update
parameter_list|(
name|T
name|obj
parameter_list|,
name|Session
name|s
parameter_list|)
block|{
name|s
operator|.
name|update
argument_list|(
name|obj
argument_list|)
expr_stmt|;
block|}
comment|/** 	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving 	 * Session or a transient instance with an identifier associated with existing persistent state.  	 */
specifier|public
name|void
name|delete
parameter_list|(
name|T
name|obj
parameter_list|)
block|{
name|Transaction
name|t
init|=
literal|null
decl_stmt|;
name|Session
name|s
init|=
literal|null
decl_stmt|;
try|try
block|{
name|s
operator|=
name|getSession
argument_list|()
expr_stmt|;
name|t
operator|=
name|beginTransaction
argument_list|(
name|s
argument_list|)
expr_stmt|;
name|delete
argument_list|(
name|obj
argument_list|,
name|s
argument_list|)
expr_stmt|;
name|commitTransaction
argument_list|(
name|t
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|HibernateException
name|e
parameter_list|)
block|{
if|if
condition|(
literal|null
operator|!=
name|t
condition|)
name|t
operator|.
name|rollback
argument_list|()
expr_stmt|;
throw|throw
name|e
throw|;
block|}
block|}
comment|/** 	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving 	 * Session or a transient instance with an identifier associated with existing persistent state.  	 */
specifier|public
name|void
name|delete
parameter_list|(
name|T
name|obj
parameter_list|,
name|Session
name|s
parameter_list|)
block|{
name|s
operator|.
name|delete
argument_list|(
name|obj
argument_list|)
expr_stmt|;
block|}
comment|/** 	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving 	 * Session or a transient instance with an identifier associated with existing persistent state.  	 */
specifier|public
name|void
name|delete
parameter_list|(
name|K
name|key
parameter_list|)
block|{
name|delete
argument_list|(
name|load
argument_list|(
name|key
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|/** 	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving 	 * Session or a transient instance with an identifier associated with existing persistent state.  	 */
specifier|public
name|void
name|delete
parameter_list|(
name|K
name|key
parameter_list|,
name|Session
name|s
parameter_list|)
block|{
name|s
operator|.
name|delete
argument_list|(
name|load
argument_list|(
name|key
argument_list|,
name|s
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|/** 	 * Re-read the state of the given instance from the underlying database. It is inadvisable to use this to implement 	 * long-running sessions that span many business tasks. This method is, however, useful in certain special circumstances. 	 */
specifier|public
name|void
name|refresh
parameter_list|(
name|T
name|obj
parameter_list|,
name|Session
name|s
parameter_list|)
block|{
name|s
operator|.
name|refresh
argument_list|(
name|obj
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

