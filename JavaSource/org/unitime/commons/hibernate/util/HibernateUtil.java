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
name|commons
operator|.
name|hibernate
operator|.
name|util
package|;
end_package

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
name|Iterator
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
name|java
operator|.
name|util
operator|.
name|Properties
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|xml
operator|.
name|parsers
operator|.
name|DocumentBuilder
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|xml
operator|.
name|parsers
operator|.
name|DocumentBuilderFactory
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
name|MappingException
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
name|mapping
operator|.
name|Formula
import|;
end_import

begin_import
import|import
name|org
operator|.
name|hibernate
operator|.
name|mapping
operator|.
name|PersistentClass
import|;
end_import

begin_import
import|import
name|org
operator|.
name|hibernate
operator|.
name|mapping
operator|.
name|Property
import|;
end_import

begin_import
import|import
name|org
operator|.
name|hibernate
operator|.
name|mapping
operator|.
name|Selectable
import|;
end_import

begin_import
import|import
name|org
operator|.
name|hibernate
operator|.
name|metadata
operator|.
name|ClassMetadata
import|;
end_import

begin_import
import|import
name|org
operator|.
name|hibernate
operator|.
name|util
operator|.
name|ConfigHelper
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
name|id
operator|.
name|UniqueIdGenerator
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
name|_RootDAO
import|;
end_import

begin_import
import|import
name|org
operator|.
name|w3c
operator|.
name|dom
operator|.
name|Document
import|;
end_import

begin_import
import|import
name|org
operator|.
name|w3c
operator|.
name|dom
operator|.
name|NodeList
import|;
end_import

begin_import
import|import
name|org
operator|.
name|w3c
operator|.
name|dom
operator|.
name|Text
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|HibernateUtil
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
name|HibernateUtil
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|private
specifier|static
name|SessionFactory
name|sSessionFactory
init|=
literal|null
decl_stmt|;
specifier|private
specifier|static
name|void
name|setProperty
parameter_list|(
name|org
operator|.
name|w3c
operator|.
name|dom
operator|.
name|Document
name|document
parameter_list|,
name|String
name|name
parameter_list|,
name|String
name|value
parameter_list|)
block|{
if|if
condition|(
name|value
operator|==
literal|null
condition|)
block|{
name|removeProperty
argument_list|(
name|document
argument_list|,
name|name
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|org
operator|.
name|w3c
operator|.
name|dom
operator|.
name|Element
name|hibConfiguration
init|=
operator|(
name|org
operator|.
name|w3c
operator|.
name|dom
operator|.
name|Element
operator|)
name|document
operator|.
name|getElementsByTagName
argument_list|(
literal|"hibernate-configuration"
argument_list|)
operator|.
name|item
argument_list|(
literal|0
argument_list|)
decl_stmt|;
name|org
operator|.
name|w3c
operator|.
name|dom
operator|.
name|Element
name|sessionFactoryConfig
init|=
operator|(
name|org
operator|.
name|w3c
operator|.
name|dom
operator|.
name|Element
operator|)
name|hibConfiguration
operator|.
name|getElementsByTagName
argument_list|(
literal|"session-factory"
argument_list|)
operator|.
name|item
argument_list|(
literal|0
argument_list|)
decl_stmt|;
name|NodeList
name|properties
init|=
name|sessionFactoryConfig
operator|.
name|getElementsByTagName
argument_list|(
literal|"property"
argument_list|)
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|properties
operator|.
name|getLength
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|org
operator|.
name|w3c
operator|.
name|dom
operator|.
name|Element
name|property
init|=
operator|(
name|org
operator|.
name|w3c
operator|.
name|dom
operator|.
name|Element
operator|)
name|properties
operator|.
name|item
argument_list|(
name|i
argument_list|)
decl_stmt|;
if|if
condition|(
name|name
operator|.
name|equals
argument_list|(
name|property
operator|.
name|getAttribute
argument_list|(
literal|"name"
argument_list|)
argument_list|)
condition|)
block|{
name|Text
name|text
init|=
operator|(
name|Text
operator|)
name|property
operator|.
name|getFirstChild
argument_list|()
decl_stmt|;
if|if
condition|(
name|text
operator|==
literal|null
condition|)
block|{
name|property
operator|.
name|appendChild
argument_list|(
name|document
operator|.
name|createTextNode
argument_list|(
name|value
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|text
operator|.
name|setData
argument_list|(
name|value
argument_list|)
expr_stmt|;
block|}
return|return;
block|}
block|}
name|org
operator|.
name|w3c
operator|.
name|dom
operator|.
name|Element
name|property
init|=
name|document
operator|.
name|createElement
argument_list|(
literal|"property"
argument_list|)
decl_stmt|;
name|property
operator|.
name|setAttribute
argument_list|(
literal|"name"
argument_list|,
name|name
argument_list|)
expr_stmt|;
name|property
operator|.
name|appendChild
argument_list|(
name|document
operator|.
name|createTextNode
argument_list|(
name|value
argument_list|)
argument_list|)
expr_stmt|;
name|sessionFactoryConfig
operator|.
name|appendChild
argument_list|(
name|property
argument_list|)
expr_stmt|;
block|}
block|}
specifier|private
specifier|static
name|void
name|removeProperty
parameter_list|(
name|org
operator|.
name|w3c
operator|.
name|dom
operator|.
name|Document
name|document
parameter_list|,
name|String
name|name
parameter_list|)
block|{
name|org
operator|.
name|w3c
operator|.
name|dom
operator|.
name|Element
name|hibConfiguration
init|=
operator|(
name|org
operator|.
name|w3c
operator|.
name|dom
operator|.
name|Element
operator|)
name|document
operator|.
name|getElementsByTagName
argument_list|(
literal|"hibernate-configuration"
argument_list|)
operator|.
name|item
argument_list|(
literal|0
argument_list|)
decl_stmt|;
name|org
operator|.
name|w3c
operator|.
name|dom
operator|.
name|Element
name|sessionFactoryConfig
init|=
operator|(
name|org
operator|.
name|w3c
operator|.
name|dom
operator|.
name|Element
operator|)
name|hibConfiguration
operator|.
name|getElementsByTagName
argument_list|(
literal|"session-factory"
argument_list|)
operator|.
name|item
argument_list|(
literal|0
argument_list|)
decl_stmt|;
name|org
operator|.
name|w3c
operator|.
name|dom
operator|.
name|NodeList
name|properties
init|=
name|sessionFactoryConfig
operator|.
name|getElementsByTagName
argument_list|(
literal|"property"
argument_list|)
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|properties
operator|.
name|getLength
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|org
operator|.
name|w3c
operator|.
name|dom
operator|.
name|Element
name|property
init|=
operator|(
name|org
operator|.
name|w3c
operator|.
name|dom
operator|.
name|Element
operator|)
name|properties
operator|.
name|item
argument_list|(
name|i
argument_list|)
decl_stmt|;
if|if
condition|(
name|name
operator|.
name|equals
argument_list|(
name|property
operator|.
name|getAttribute
argument_list|(
literal|"name"
argument_list|)
argument_list|)
condition|)
block|{
name|sessionFactoryConfig
operator|.
name|removeChild
argument_list|(
name|property
argument_list|)
expr_stmt|;
return|return;
block|}
block|}
block|}
specifier|private
specifier|static
name|String
name|getProperty
parameter_list|(
name|org
operator|.
name|w3c
operator|.
name|dom
operator|.
name|Document
name|document
parameter_list|,
name|String
name|name
parameter_list|,
name|String
name|defaultValue
parameter_list|)
block|{
name|org
operator|.
name|w3c
operator|.
name|dom
operator|.
name|Element
name|hibConfiguration
init|=
operator|(
name|org
operator|.
name|w3c
operator|.
name|dom
operator|.
name|Element
operator|)
name|document
operator|.
name|getElementsByTagName
argument_list|(
literal|"hibernate-configuration"
argument_list|)
operator|.
name|item
argument_list|(
literal|0
argument_list|)
decl_stmt|;
name|org
operator|.
name|w3c
operator|.
name|dom
operator|.
name|Element
name|sessionFactoryConfig
init|=
operator|(
name|org
operator|.
name|w3c
operator|.
name|dom
operator|.
name|Element
operator|)
name|hibConfiguration
operator|.
name|getElementsByTagName
argument_list|(
literal|"session-factory"
argument_list|)
operator|.
name|item
argument_list|(
literal|0
argument_list|)
decl_stmt|;
name|NodeList
name|properties
init|=
name|sessionFactoryConfig
operator|.
name|getElementsByTagName
argument_list|(
literal|"property"
argument_list|)
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|properties
operator|.
name|getLength
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|org
operator|.
name|w3c
operator|.
name|dom
operator|.
name|Element
name|property
init|=
operator|(
name|org
operator|.
name|w3c
operator|.
name|dom
operator|.
name|Element
operator|)
name|properties
operator|.
name|item
argument_list|(
name|i
argument_list|)
decl_stmt|;
if|if
condition|(
name|name
operator|.
name|equals
argument_list|(
name|property
operator|.
name|getAttribute
argument_list|(
literal|"name"
argument_list|)
argument_list|)
condition|)
block|{
name|Text
name|text
init|=
operator|(
name|Text
operator|)
name|property
operator|.
name|getFirstChild
argument_list|()
decl_stmt|;
if|if
condition|(
name|text
operator|==
literal|null
operator|||
name|text
operator|.
name|getData
argument_list|()
operator|==
literal|null
condition|)
return|return
name|defaultValue
return|;
return|return
name|text
operator|.
name|getData
argument_list|()
return|;
block|}
block|}
return|return
name|defaultValue
return|;
block|}
specifier|public
specifier|static
name|void
name|configureHibernate
parameter_list|(
name|String
name|connectionUrl
parameter_list|)
throws|throws
name|Exception
block|{
name|Properties
name|properties
init|=
name|ApplicationProperties
operator|.
name|getProperties
argument_list|()
decl_stmt|;
name|properties
operator|.
name|setProperty
argument_list|(
literal|"connection.url"
argument_list|,
name|connectionUrl
argument_list|)
expr_stmt|;
name|configureHibernate
argument_list|(
name|properties
argument_list|)
expr_stmt|;
block|}
specifier|public
specifier|static
name|String
name|getProperty
parameter_list|(
name|Properties
name|properties
parameter_list|,
name|String
name|name
parameter_list|)
block|{
name|String
name|value
init|=
name|properties
operator|.
name|getProperty
argument_list|(
name|name
argument_list|)
decl_stmt|;
if|if
condition|(
name|value
operator|!=
literal|null
condition|)
return|return
name|value
return|;
return|return
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
name|name
argument_list|)
return|;
block|}
specifier|public
specifier|static
name|void
name|fixSchemaInFormulas
parameter_list|(
name|Configuration
name|cfg
parameter_list|)
block|{
name|String
name|schema
init|=
name|cfg
operator|.
name|getProperty
argument_list|(
literal|"default_schema"
argument_list|)
decl_stmt|;
if|if
condition|(
name|schema
operator|!=
literal|null
condition|)
block|{
for|for
control|(
name|Iterator
name|i
init|=
name|cfg
operator|.
name|getClassMappings
argument_list|()
init|;
name|i
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|PersistentClass
name|pc
init|=
operator|(
name|PersistentClass
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
for|for
control|(
name|Iterator
name|j
init|=
name|pc
operator|.
name|getPropertyIterator
argument_list|()
init|;
name|j
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|Property
name|p
init|=
operator|(
name|Property
operator|)
name|j
operator|.
name|next
argument_list|()
decl_stmt|;
for|for
control|(
name|Iterator
name|k
init|=
name|p
operator|.
name|getColumnIterator
argument_list|()
init|;
name|k
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|Selectable
name|c
init|=
operator|(
name|Selectable
operator|)
name|k
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|c
operator|instanceof
name|Formula
condition|)
block|{
name|Formula
name|f
init|=
operator|(
name|Formula
operator|)
name|c
decl_stmt|;
if|if
condition|(
name|f
operator|.
name|getFormula
argument_list|()
operator|!=
literal|null
operator|&&
name|f
operator|.
name|getFormula
argument_list|()
operator|.
name|indexOf
argument_list|(
literal|"%SCHEMA%"
argument_list|)
operator|>=
literal|0
condition|)
block|{
name|f
operator|.
name|setFormula
argument_list|(
name|f
operator|.
name|getFormula
argument_list|()
operator|.
name|replaceAll
argument_list|(
literal|"%SCHEMA%"
argument_list|,
name|schema
argument_list|)
argument_list|)
expr_stmt|;
name|sLog
operator|.
name|debug
argument_list|(
literal|"Schema updated in "
operator|+
name|pc
operator|.
name|getClassName
argument_list|()
operator|+
literal|"."
operator|+
name|p
operator|.
name|getName
argument_list|()
operator|+
literal|" to "
operator|+
name|f
operator|.
name|getFormula
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
block|}
block|}
block|}
specifier|public
specifier|static
name|void
name|configureHibernate
parameter_list|(
name|Properties
name|properties
parameter_list|)
throws|throws
name|Exception
block|{
if|if
condition|(
name|sSessionFactory
operator|!=
literal|null
condition|)
block|{
name|sSessionFactory
operator|.
name|close
argument_list|()
expr_stmt|;
name|sSessionFactory
operator|=
literal|null
expr_stmt|;
block|}
name|sLog
operator|.
name|info
argument_list|(
literal|"Connecting to "
operator|+
name|getProperty
argument_list|(
name|properties
argument_list|,
literal|"connection.url"
argument_list|)
argument_list|)
expr_stmt|;
name|ClassLoader
name|classLoader
init|=
name|HibernateUtil
operator|.
name|class
operator|.
name|getClassLoader
argument_list|()
decl_stmt|;
name|sLog
operator|.
name|debug
argument_list|(
literal|"  -- class loader retrieved"
argument_list|)
expr_stmt|;
name|DocumentBuilderFactory
name|factory
init|=
name|DocumentBuilderFactory
operator|.
name|newInstance
argument_list|()
decl_stmt|;
name|sLog
operator|.
name|debug
argument_list|(
literal|"  -- document factory created"
argument_list|)
expr_stmt|;
name|DocumentBuilder
name|builder
init|=
name|factory
operator|.
name|newDocumentBuilder
argument_list|()
decl_stmt|;
name|sLog
operator|.
name|debug
argument_list|(
literal|"  -- document builder created"
argument_list|)
expr_stmt|;
name|Document
name|document
init|=
name|builder
operator|.
name|parse
argument_list|(
name|classLoader
operator|.
name|getResource
argument_list|(
literal|"hibernate.cfg.xml"
argument_list|)
operator|.
name|openStream
argument_list|()
argument_list|)
decl_stmt|;
name|sLog
operator|.
name|debug
argument_list|(
literal|"  -- hibernate.cfg.xml parsed"
argument_list|)
expr_stmt|;
if|if
condition|(
name|getProperty
argument_list|(
name|properties
argument_list|,
literal|"connection.url"
argument_list|)
operator|!=
literal|null
condition|)
block|{
name|removeProperty
argument_list|(
name|document
argument_list|,
literal|"connection.datasource"
argument_list|)
expr_stmt|;
name|String
name|driver
init|=
name|getProperty
argument_list|(
name|properties
argument_list|,
literal|"connection.driver_class"
argument_list|)
decl_stmt|;
if|if
condition|(
name|driver
operator|!=
literal|null
condition|)
name|setProperty
argument_list|(
name|document
argument_list|,
literal|"connection.driver_class"
argument_list|,
name|driver
argument_list|)
expr_stmt|;
name|setProperty
argument_list|(
name|document
argument_list|,
literal|"connection.url"
argument_list|,
name|getProperty
argument_list|(
name|properties
argument_list|,
literal|"connection.url"
argument_list|)
argument_list|)
expr_stmt|;
name|String
name|userName
init|=
name|getProperty
argument_list|(
name|properties
argument_list|,
literal|"connection.username"
argument_list|)
decl_stmt|;
if|if
condition|(
name|userName
operator|!=
literal|null
condition|)
name|setProperty
argument_list|(
name|document
argument_list|,
literal|"connection.username"
argument_list|,
name|userName
argument_list|)
expr_stmt|;
name|String
name|password
init|=
name|getProperty
argument_list|(
name|properties
argument_list|,
literal|"connection.password"
argument_list|)
decl_stmt|;
if|if
condition|(
name|password
operator|!=
literal|null
condition|)
name|setProperty
argument_list|(
name|document
argument_list|,
literal|"connection.password"
argument_list|,
name|password
argument_list|)
expr_stmt|;
name|setProperty
argument_list|(
name|document
argument_list|,
literal|"hibernate.jdbc.batch_size"
argument_list|,
literal|"100"
argument_list|)
expr_stmt|;
name|setProperty
argument_list|(
name|document
argument_list|,
literal|"hibernate.cache.use_second_level_cache"
argument_list|,
literal|"false"
argument_list|)
expr_stmt|;
name|String
name|dialect
init|=
name|getProperty
argument_list|(
name|properties
argument_list|,
literal|"dialect"
argument_list|)
decl_stmt|;
if|if
condition|(
name|dialect
operator|!=
literal|null
condition|)
name|setProperty
argument_list|(
name|document
argument_list|,
literal|"dialect"
argument_list|,
name|dialect
argument_list|)
expr_stmt|;
name|String
name|idgen
init|=
name|getProperty
argument_list|(
name|properties
argument_list|,
literal|"tmtbl.uniqueid.generator"
argument_list|)
decl_stmt|;
if|if
condition|(
name|idgen
operator|!=
literal|null
condition|)
name|setProperty
argument_list|(
name|document
argument_list|,
literal|"tmtbl.uniqueid.generator"
argument_list|,
name|idgen
argument_list|)
expr_stmt|;
comment|/*// JDBC Pool              setProperty(document, "connection.pool_size", "5");             setProperty(document, "connection.release_mode", "on_close");             */
comment|/*// C3P0 Pool             setProperty(document, "hibernate.c3p0.min_size", "0");             setProperty(document, "hibernate.c3p0.max_size", "5");             setProperty(document, "hibernate.c3p0.timeout", "1800");             setProperty(document, "hibernate.c3p0.max_statements", "50");             setProperty(document, "hibernate.c3p0.validate", "true");             */
comment|// Apache DBCP Pool
name|setProperty
argument_list|(
name|document
argument_list|,
literal|"hibernate.connection.provider_class"
argument_list|,
literal|"org.unitime.commons.hibernate.connection.DBCPConnectionProvider"
argument_list|)
expr_stmt|;
name|setProperty
argument_list|(
name|document
argument_list|,
literal|"hibernate.dbcp.maxIdle"
argument_list|,
literal|"2"
argument_list|)
expr_stmt|;
name|setProperty
argument_list|(
name|document
argument_list|,
literal|"hibernate.dbcp.maxActive"
argument_list|,
literal|"5"
argument_list|)
expr_stmt|;
name|setProperty
argument_list|(
name|document
argument_list|,
literal|"hibernate.dbcp.whenExhaustedAction"
argument_list|,
literal|"1"
argument_list|)
expr_stmt|;
name|setProperty
argument_list|(
name|document
argument_list|,
literal|"hibernate.dbcp.maxWait"
argument_list|,
literal|"180000"
argument_list|)
expr_stmt|;
name|setProperty
argument_list|(
name|document
argument_list|,
literal|"hibernate.dbcp.testOnBorrow"
argument_list|,
literal|"true"
argument_list|)
expr_stmt|;
name|setProperty
argument_list|(
name|document
argument_list|,
literal|"hibernate.dbcp.testOnReturn"
argument_list|,
literal|"false"
argument_list|)
expr_stmt|;
name|setProperty
argument_list|(
name|document
argument_list|,
literal|"hibernate.dbcp.validationQuery"
argument_list|,
literal|"select 1 from dual"
argument_list|)
expr_stmt|;
block|}
name|String
name|default_schema
init|=
name|getProperty
argument_list|(
name|properties
argument_list|,
literal|"default_schema"
argument_list|)
decl_stmt|;
if|if
condition|(
name|default_schema
operator|!=
literal|null
condition|)
name|setProperty
argument_list|(
name|document
argument_list|,
literal|"default_schema"
argument_list|,
name|default_schema
argument_list|)
expr_stmt|;
name|sLog
operator|.
name|debug
argument_list|(
literal|"  -- hibernate.cfg.xml altered"
argument_list|)
expr_stmt|;
name|Configuration
name|cfg
init|=
operator|new
name|Configuration
argument_list|()
decl_stmt|;
name|sLog
operator|.
name|debug
argument_list|(
literal|"  -- configuration object created"
argument_list|)
expr_stmt|;
name|cfg
operator|.
name|configure
argument_list|(
name|document
argument_list|)
expr_stmt|;
name|sLog
operator|.
name|debug
argument_list|(
literal|"  -- hibernate configured"
argument_list|)
expr_stmt|;
name|fixSchemaInFormulas
argument_list|(
name|cfg
argument_list|)
expr_stmt|;
name|UniqueIdGenerator
operator|.
name|configure
argument_list|(
name|cfg
argument_list|)
expr_stmt|;
name|sSessionFactory
operator|=
name|cfg
operator|.
name|buildSessionFactory
argument_list|()
expr_stmt|;
name|sLog
operator|.
name|debug
argument_list|(
literal|"  -- session factory created"
argument_list|)
expr_stmt|;
operator|(
operator|new
name|_BaseRootDAO
argument_list|()
block|{
name|void
name|setSF
parameter_list|(
name|SessionFactory
name|fact
parameter_list|,
name|Configuration
name|cfg
parameter_list|)
block|{
name|_BaseRootDAO
operator|.
name|sessionFactory
operator|=
name|fact
expr_stmt|;
name|_BaseRootDAO
operator|.
name|configuration
operator|=
name|cfg
expr_stmt|;
block|}
specifier|protected
name|Class
name|getReferenceClass
parameter_list|()
block|{
return|return
literal|null
return|;
block|}
block|}
operator|)
operator|.
name|setSF
argument_list|(
name|sSessionFactory
argument_list|,
name|cfg
argument_list|)
expr_stmt|;
name|sLog
operator|.
name|debug
argument_list|(
literal|"  -- session factory set to _BaseRootDAO"
argument_list|)
expr_stmt|;
name|DatabaseUpdate
operator|.
name|update
argument_list|()
expr_stmt|;
block|}
specifier|public
specifier|static
name|void
name|closeHibernate
parameter_list|()
block|{
if|if
condition|(
name|sSessionFactory
operator|!=
literal|null
condition|)
block|{
name|sSessionFactory
operator|.
name|close
argument_list|()
expr_stmt|;
name|sSessionFactory
operator|=
literal|null
expr_stmt|;
block|}
block|}
specifier|public
specifier|static
name|void
name|configureHibernateFromRootDAO
parameter_list|(
name|String
name|cfgName
parameter_list|,
name|Configuration
name|cfg
parameter_list|)
block|{
try|try
block|{
name|DocumentBuilderFactory
name|factory
init|=
name|DocumentBuilderFactory
operator|.
name|newInstance
argument_list|()
decl_stmt|;
name|sLog
operator|.
name|debug
argument_list|(
literal|"  -- document factory created"
argument_list|)
expr_stmt|;
name|DocumentBuilder
name|builder
init|=
name|factory
operator|.
name|newDocumentBuilder
argument_list|()
decl_stmt|;
name|sLog
operator|.
name|debug
argument_list|(
literal|"  -- document builder created"
argument_list|)
expr_stmt|;
name|Document
name|document
init|=
name|builder
operator|.
name|parse
argument_list|(
name|ConfigHelper
operator|.
name|getConfigStream
argument_list|(
name|cfgName
operator|==
literal|null
condition|?
literal|"hibernate.cfg.xml"
else|:
name|cfgName
argument_list|)
argument_list|)
decl_stmt|;
name|String
name|dialect
init|=
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"dialect"
argument_list|)
decl_stmt|;
if|if
condition|(
name|dialect
operator|!=
literal|null
condition|)
name|setProperty
argument_list|(
name|document
argument_list|,
literal|"dialect"
argument_list|,
name|dialect
argument_list|)
expr_stmt|;
name|String
name|default_schema
init|=
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"default_schema"
argument_list|)
decl_stmt|;
if|if
condition|(
name|default_schema
operator|!=
literal|null
condition|)
name|setProperty
argument_list|(
name|document
argument_list|,
literal|"default_schema"
argument_list|,
name|default_schema
argument_list|)
expr_stmt|;
name|String
name|idgen
init|=
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"tmtbl.uniqueid.generator"
argument_list|)
decl_stmt|;
if|if
condition|(
name|idgen
operator|!=
literal|null
condition|)
name|setProperty
argument_list|(
name|document
argument_list|,
literal|"tmtbl.uniqueid.generator"
argument_list|,
name|idgen
argument_list|)
expr_stmt|;
for|for
control|(
name|Enumeration
name|e
init|=
name|ApplicationProperties
operator|.
name|getProperties
argument_list|()
operator|.
name|propertyNames
argument_list|()
init|;
name|e
operator|.
name|hasMoreElements
argument_list|()
condition|;
control|)
block|{
name|String
name|name
init|=
operator|(
name|String
operator|)
name|e
operator|.
name|nextElement
argument_list|()
decl_stmt|;
if|if
condition|(
name|name
operator|.
name|startsWith
argument_list|(
literal|"hibernate."
argument_list|)
operator|||
name|name
operator|.
name|startsWith
argument_list|(
literal|"connection."
argument_list|)
operator|||
name|name
operator|.
name|startsWith
argument_list|(
literal|"tmtbl.hibernate."
argument_list|)
condition|)
block|{
name|String
name|value
init|=
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
name|name
argument_list|)
decl_stmt|;
if|if
condition|(
literal|"NULL"
operator|.
name|equals
argument_list|(
name|value
argument_list|)
condition|)
name|removeProperty
argument_list|(
name|document
argument_list|,
name|name
argument_list|)
expr_stmt|;
else|else
name|setProperty
argument_list|(
name|document
argument_list|,
name|name
argument_list|,
name|value
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
name|name
operator|.
name|equals
argument_list|(
literal|"connection.password"
argument_list|)
condition|)
name|sLog
operator|.
name|debug
argument_list|(
literal|"  -- set "
operator|+
name|name
operator|+
literal|": "
operator|+
name|value
argument_list|)
expr_stmt|;
else|else
name|sLog
operator|.
name|debug
argument_list|(
literal|"  -- set "
operator|+
name|name
operator|+
literal|": *****"
argument_list|)
expr_stmt|;
block|}
block|}
name|cfg
operator|.
name|configure
argument_list|(
name|document
argument_list|)
expr_stmt|;
name|sLog
operator|.
name|debug
argument_list|(
literal|"  -- hibernate configured"
argument_list|)
expr_stmt|;
name|HibernateUtil
operator|.
name|fixSchemaInFormulas
argument_list|(
name|cfg
argument_list|)
expr_stmt|;
name|sLog
operator|.
name|debug
argument_list|(
literal|"  -- %SCHEMA% in formulas changed to "
operator|+
name|cfg
operator|.
name|getProperty
argument_list|(
literal|"default_schema"
argument_list|)
argument_list|)
expr_stmt|;
name|UniqueIdGenerator
operator|.
name|configure
argument_list|(
name|cfg
argument_list|)
expr_stmt|;
name|sLog
operator|.
name|debug
argument_list|(
literal|"  -- UniquId generator configured"
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
name|error
argument_list|(
literal|"Unable to configure hibernate, reason: "
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
specifier|private
specifier|static
name|String
name|sConnectionUrl
init|=
literal|null
decl_stmt|;
specifier|public
specifier|static
name|String
name|getConnectionUrl
parameter_list|()
block|{
if|if
condition|(
name|sConnectionUrl
operator|==
literal|null
condition|)
block|{
try|try
block|{
name|Session
name|session
init|=
operator|(
operator|new
name|_BaseRootDAO
argument_list|()
block|{
specifier|protected
name|Class
name|getReferenceClass
parameter_list|()
block|{
return|return
literal|null
return|;
block|}
block|}
operator|)
operator|.
name|createNewSession
argument_list|()
decl_stmt|;
name|sConnectionUrl
operator|=
name|session
operator|.
name|connection
argument_list|()
operator|.
name|getMetaData
argument_list|()
operator|.
name|getURL
argument_list|()
expr_stmt|;
name|session
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
name|error
argument_list|(
literal|"Unable to get connection string, reason: "
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
return|return
name|sConnectionUrl
return|;
block|}
specifier|public
specifier|static
name|String
name|getDatabaseName
parameter_list|()
block|{
name|String
name|schema
init|=
name|_RootDAO
operator|.
name|getConfiguration
argument_list|()
operator|.
name|getProperty
argument_list|(
literal|"default_schema"
argument_list|)
decl_stmt|;
name|String
name|url
init|=
name|getConnectionUrl
argument_list|()
decl_stmt|;
if|if
condition|(
name|url
operator|==
literal|null
condition|)
return|return
literal|"N/A"
return|;
if|if
condition|(
name|url
operator|.
name|startsWith
argument_list|(
literal|"jdbc:oracle:"
argument_list|)
condition|)
block|{
return|return
name|schema
operator|+
literal|"@"
operator|+
name|url
operator|.
name|substring
argument_list|(
literal|1
operator|+
name|url
operator|.
name|lastIndexOf
argument_list|(
literal|':'
argument_list|)
argument_list|)
return|;
block|}
return|return
name|schema
return|;
block|}
specifier|public
specifier|static
name|void
name|clearCache
parameter_list|()
block|{
name|clearCache
argument_list|(
literal|null
argument_list|,
literal|true
argument_list|)
expr_stmt|;
block|}
specifier|public
specifier|static
name|void
name|clearCache
parameter_list|(
name|Class
name|persistentClass
parameter_list|)
block|{
name|clearCache
argument_list|(
name|persistentClass
argument_list|,
literal|false
argument_list|)
expr_stmt|;
block|}
specifier|public
specifier|static
name|void
name|clearCache
parameter_list|(
name|Class
name|persistentClass
parameter_list|,
name|boolean
name|evictQueries
parameter_list|)
block|{
name|_RootDAO
name|dao
init|=
operator|new
name|_RootDAO
argument_list|()
decl_stmt|;
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
init|=
name|dao
operator|.
name|getSession
argument_list|()
decl_stmt|;
name|SessionFactory
name|hibSessionFactory
init|=
name|hibSession
operator|.
name|getSessionFactory
argument_list|()
decl_stmt|;
if|if
condition|(
name|persistentClass
operator|==
literal|null
condition|)
block|{
for|for
control|(
name|Iterator
name|i
init|=
name|hibSessionFactory
operator|.
name|getAllClassMetadata
argument_list|()
operator|.
name|entrySet
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
block|{
name|Map
operator|.
name|Entry
name|entry
init|=
operator|(
name|Map
operator|.
name|Entry
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|String
name|className
init|=
operator|(
name|String
operator|)
name|entry
operator|.
name|getKey
argument_list|()
decl_stmt|;
name|ClassMetadata
name|classMetadata
init|=
operator|(
name|ClassMetadata
operator|)
name|entry
operator|.
name|getValue
argument_list|()
decl_stmt|;
try|try
block|{
name|hibSessionFactory
operator|.
name|evict
argument_list|(
name|Class
operator|.
name|forName
argument_list|(
name|className
argument_list|)
argument_list|)
expr_stmt|;
for|for
control|(
name|int
name|j
init|=
literal|0
init|;
name|j
operator|<
name|classMetadata
operator|.
name|getPropertyNames
argument_list|()
operator|.
name|length
condition|;
name|j
operator|++
control|)
block|{
if|if
condition|(
name|classMetadata
operator|.
name|getPropertyTypes
argument_list|()
index|[
name|j
index|]
operator|.
name|isCollectionType
argument_list|()
condition|)
block|{
try|try
block|{
name|hibSessionFactory
operator|.
name|evictCollection
argument_list|(
name|className
operator|+
literal|"."
operator|+
name|classMetadata
operator|.
name|getPropertyNames
argument_list|()
index|[
name|j
index|]
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|MappingException
name|e
parameter_list|)
block|{
block|}
block|}
block|}
block|}
catch|catch
parameter_list|(
name|ClassNotFoundException
name|e
parameter_list|)
block|{
block|}
block|}
block|}
else|else
block|{
name|ClassMetadata
name|classMetadata
init|=
name|hibSessionFactory
operator|.
name|getClassMetadata
argument_list|(
name|persistentClass
argument_list|)
decl_stmt|;
name|hibSessionFactory
operator|.
name|evict
argument_list|(
name|persistentClass
argument_list|)
expr_stmt|;
if|if
condition|(
name|classMetadata
operator|!=
literal|null
condition|)
block|{
for|for
control|(
name|int
name|j
init|=
literal|0
init|;
name|j
operator|<
name|classMetadata
operator|.
name|getPropertyNames
argument_list|()
operator|.
name|length
condition|;
name|j
operator|++
control|)
block|{
if|if
condition|(
name|classMetadata
operator|.
name|getPropertyTypes
argument_list|()
index|[
name|j
index|]
operator|.
name|isCollectionType
argument_list|()
condition|)
block|{
try|try
block|{
name|hibSessionFactory
operator|.
name|evictCollection
argument_list|(
name|persistentClass
operator|.
name|getClass
argument_list|()
operator|.
name|getName
argument_list|()
operator|+
literal|"."
operator|+
name|classMetadata
operator|.
name|getPropertyNames
argument_list|()
index|[
name|j
index|]
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|MappingException
name|e
parameter_list|)
block|{
block|}
block|}
block|}
block|}
block|}
if|if
condition|(
name|evictQueries
condition|)
name|hibSessionFactory
operator|.
name|evictQueries
argument_list|()
expr_stmt|;
block|}
block|}
end_class

end_unit

