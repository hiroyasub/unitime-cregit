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
name|admin
package|;
end_package

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
name|Enumeration
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
name|apache
operator|.
name|log4j
operator|.
name|Level
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|log4j
operator|.
name|LogManager
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|log4j
operator|.
name|Logger
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
name|springframework
operator|.
name|stereotype
operator|.
name|Service
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
name|SimpleEditInterface
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
name|SimpleEditInterface
operator|.
name|Field
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
name|SimpleEditInterface
operator|.
name|FieldType
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
name|SimpleEditInterface
operator|.
name|Flag
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
name|SimpleEditInterface
operator|.
name|ListItem
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
name|SimpleEditInterface
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
name|SimpleEditInterface
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
name|model
operator|.
name|ApplicationConfig
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
name|rights
operator|.
name|Right
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
name|service
operator|.
name|SolverServerService
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
annotation|@
name|Service
argument_list|(
literal|"gwtAdminTable[type=logging]"
argument_list|)
specifier|public
class|class
name|Loggers
implements|implements
name|AdminTable
block|{
specifier|protected
specifier|static
specifier|final
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
name|SolverServerService
name|solverServerService
decl_stmt|;
annotation|@
name|Override
specifier|public
name|PageName
name|name
parameter_list|()
block|{
return|return
operator|new
name|PageName
argument_list|(
name|MESSAGES
operator|.
name|pageLoggingLevel
argument_list|()
argument_list|,
name|MESSAGES
operator|.
name|pageLoggingLevels
argument_list|()
argument_list|)
return|;
block|}
annotation|@
name|Override
annotation|@
name|PreAuthorize
argument_list|(
literal|"checkPermission('ApplicationConfig')"
argument_list|)
specifier|public
name|SimpleEditInterface
name|load
parameter_list|(
name|SessionContext
name|context
parameter_list|,
name|Session
name|hibSession
parameter_list|)
block|{
name|List
argument_list|<
name|ListItem
argument_list|>
name|levels
init|=
operator|new
name|ArrayList
argument_list|<
name|ListItem
argument_list|>
argument_list|()
decl_stmt|;
name|levels
operator|.
name|add
argument_list|(
operator|new
name|ListItem
argument_list|(
name|String
operator|.
name|valueOf
argument_list|(
name|Level
operator|.
name|ALL_INT
argument_list|)
argument_list|,
name|MESSAGES
operator|.
name|levelAll
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|levels
operator|.
name|add
argument_list|(
operator|new
name|ListItem
argument_list|(
name|String
operator|.
name|valueOf
argument_list|(
name|Level
operator|.
name|TRACE_INT
argument_list|)
argument_list|,
name|MESSAGES
operator|.
name|levelTrace
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|levels
operator|.
name|add
argument_list|(
operator|new
name|ListItem
argument_list|(
name|String
operator|.
name|valueOf
argument_list|(
name|Level
operator|.
name|DEBUG_INT
argument_list|)
argument_list|,
name|MESSAGES
operator|.
name|levelDebug
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|levels
operator|.
name|add
argument_list|(
operator|new
name|ListItem
argument_list|(
name|String
operator|.
name|valueOf
argument_list|(
name|Level
operator|.
name|INFO_INT
argument_list|)
argument_list|,
name|MESSAGES
operator|.
name|levelInfo
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|levels
operator|.
name|add
argument_list|(
operator|new
name|ListItem
argument_list|(
name|String
operator|.
name|valueOf
argument_list|(
name|Level
operator|.
name|WARN_INT
argument_list|)
argument_list|,
name|MESSAGES
operator|.
name|levelWarning
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|levels
operator|.
name|add
argument_list|(
operator|new
name|ListItem
argument_list|(
name|String
operator|.
name|valueOf
argument_list|(
name|Level
operator|.
name|ERROR_INT
argument_list|)
argument_list|,
name|MESSAGES
operator|.
name|levelError
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|levels
operator|.
name|add
argument_list|(
operator|new
name|ListItem
argument_list|(
name|String
operator|.
name|valueOf
argument_list|(
name|Level
operator|.
name|FATAL_INT
argument_list|)
argument_list|,
name|MESSAGES
operator|.
name|levelFatal
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|levels
operator|.
name|add
argument_list|(
operator|new
name|ListItem
argument_list|(
name|String
operator|.
name|valueOf
argument_list|(
name|Level
operator|.
name|OFF_INT
argument_list|)
argument_list|,
name|MESSAGES
operator|.
name|levelOff
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|SimpleEditInterface
name|data
init|=
operator|new
name|SimpleEditInterface
argument_list|(
operator|new
name|Field
argument_list|(
name|MESSAGES
operator|.
name|fieldLogger
argument_list|()
argument_list|,
name|FieldType
operator|.
name|text
argument_list|,
literal|400
argument_list|,
literal|1024
argument_list|,
name|Flag
operator|.
name|UNIQUE
argument_list|)
argument_list|,
operator|new
name|Field
argument_list|(
name|MESSAGES
operator|.
name|fieldLevel
argument_list|()
argument_list|,
name|FieldType
operator|.
name|list
argument_list|,
literal|100
argument_list|,
name|levels
argument_list|,
name|Flag
operator|.
name|NOT_EMPTY
argument_list|)
argument_list|)
decl_stmt|;
name|data
operator|.
name|setSortBy
argument_list|(
literal|0
argument_list|,
literal|1
argument_list|)
expr_stmt|;
name|long
name|id
init|=
literal|0
decl_stmt|;
name|SimpleEditInterface
operator|.
name|Record
name|root
init|=
name|data
operator|.
name|addRecord
argument_list|(
name|id
operator|++
argument_list|,
literal|false
argument_list|)
decl_stmt|;
name|root
operator|.
name|setField
argument_list|(
literal|0
argument_list|,
literal|" root"
argument_list|,
literal|false
argument_list|)
expr_stmt|;
name|root
operator|.
name|setField
argument_list|(
literal|1
argument_list|,
name|String
operator|.
name|valueOf
argument_list|(
name|LogManager
operator|.
name|getRootLogger
argument_list|()
operator|.
name|getLevel
argument_list|()
operator|.
name|toInt
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
for|for
control|(
name|Enumeration
name|e
init|=
name|LogManager
operator|.
name|getCurrentLoggers
argument_list|()
init|;
name|e
operator|.
name|hasMoreElements
argument_list|()
condition|;
control|)
block|{
name|Logger
name|logger
init|=
operator|(
name|Logger
operator|)
name|e
operator|.
name|nextElement
argument_list|()
decl_stmt|;
if|if
condition|(
name|logger
operator|.
name|getLevel
argument_list|()
operator|==
literal|null
condition|)
continue|continue;
name|ApplicationConfig
name|config
init|=
name|ApplicationConfig
operator|.
name|getConfig
argument_list|(
literal|"log4j.logger."
operator|+
name|logger
operator|.
name|getName
argument_list|()
argument_list|)
decl_stmt|;
name|SimpleEditInterface
operator|.
name|Record
name|record
init|=
name|data
operator|.
name|addRecord
argument_list|(
name|id
operator|++
argument_list|,
name|ApplicationProperties
operator|.
name|getDefaultProperties
argument_list|()
operator|.
name|getProperty
argument_list|(
literal|"log4j.logger."
operator|+
name|logger
operator|.
name|getName
argument_list|()
argument_list|)
operator|==
literal|null
operator|&&
name|config
operator|!=
literal|null
argument_list|)
decl_stmt|;
name|record
operator|.
name|setField
argument_list|(
literal|0
argument_list|,
name|logger
operator|.
name|getName
argument_list|()
argument_list|,
literal|false
argument_list|)
expr_stmt|;
name|record
operator|.
name|setField
argument_list|(
literal|1
argument_list|,
name|String
operator|.
name|valueOf
argument_list|(
name|logger
operator|.
name|getLevel
argument_list|()
operator|.
name|toInt
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|data
operator|.
name|setEditable
argument_list|(
name|context
operator|.
name|hasPermission
argument_list|(
name|Right
operator|.
name|ApplicationConfig
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|data
return|;
block|}
annotation|@
name|Override
annotation|@
name|PreAuthorize
argument_list|(
literal|"checkPermission('ApplicationConfig')"
argument_list|)
specifier|public
name|void
name|save
parameter_list|(
name|SimpleEditInterface
name|data
parameter_list|,
name|SessionContext
name|context
parameter_list|,
name|Session
name|hibSession
parameter_list|)
block|{
name|Map
argument_list|<
name|String
argument_list|,
name|SimpleEditInterface
operator|.
name|Record
argument_list|>
name|records
init|=
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|SimpleEditInterface
operator|.
name|Record
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|Record
name|r
range|:
name|data
operator|.
name|getRecords
argument_list|()
control|)
block|{
if|if
condition|(
name|r
operator|.
name|isEmpty
argument_list|()
condition|)
continue|continue;
name|boolean
name|root
init|=
name|r
operator|.
name|getUniqueId
argument_list|()
operator|!=
literal|null
operator|&&
name|r
operator|.
name|getUniqueId
argument_list|()
operator|==
literal|0
decl_stmt|;
if|if
condition|(
name|root
condition|)
block|{
name|update
argument_list|(
name|r
argument_list|,
name|context
argument_list|,
name|hibSession
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|records
operator|.
name|put
argument_list|(
name|r
operator|.
name|getField
argument_list|(
literal|0
argument_list|)
argument_list|,
name|r
argument_list|)
expr_stmt|;
block|}
block|}
for|for
control|(
name|Enumeration
name|e
init|=
name|LogManager
operator|.
name|getCurrentLoggers
argument_list|()
init|;
name|e
operator|.
name|hasMoreElements
argument_list|()
condition|;
control|)
block|{
name|Logger
name|logger
init|=
operator|(
name|Logger
operator|)
name|e
operator|.
name|nextElement
argument_list|()
decl_stmt|;
if|if
condition|(
name|logger
operator|.
name|getLevel
argument_list|()
operator|==
literal|null
condition|)
continue|continue;
name|Record
name|r
init|=
name|records
operator|.
name|get
argument_list|(
name|logger
operator|.
name|getName
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|r
operator|==
literal|null
condition|)
name|delete
argument_list|(
name|r
argument_list|,
name|context
argument_list|,
name|hibSession
argument_list|)
expr_stmt|;
else|else
name|update
argument_list|(
name|r
argument_list|,
name|context
argument_list|,
name|hibSession
argument_list|)
expr_stmt|;
block|}
for|for
control|(
name|Record
name|r
range|:
name|data
operator|.
name|getNewRecords
argument_list|()
control|)
name|save
argument_list|(
name|r
argument_list|,
name|context
argument_list|,
name|hibSession
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
annotation|@
name|PreAuthorize
argument_list|(
literal|"checkPermission('ApplicationConfig')"
argument_list|)
specifier|public
name|void
name|save
parameter_list|(
name|Record
name|record
parameter_list|,
name|SessionContext
name|context
parameter_list|,
name|Session
name|hibSession
parameter_list|)
block|{
name|update
argument_list|(
name|record
argument_list|,
name|context
argument_list|,
name|hibSession
argument_list|)
expr_stmt|;
name|record
operator|.
name|setUniqueId
argument_list|(
name|System
operator|.
name|currentTimeMillis
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
annotation|@
name|PreAuthorize
argument_list|(
literal|"checkPermission('ApplicationConfig')"
argument_list|)
specifier|public
name|void
name|update
parameter_list|(
name|Record
name|record
parameter_list|,
name|SessionContext
name|context
parameter_list|,
name|Session
name|hibSession
parameter_list|)
block|{
name|boolean
name|root
init|=
name|record
operator|.
name|getUniqueId
argument_list|()
operator|!=
literal|null
operator|&&
name|record
operator|.
name|getUniqueId
argument_list|()
operator|==
literal|0
decl_stmt|;
name|Level
name|level
init|=
name|Level
operator|.
name|toLevel
argument_list|(
name|Integer
operator|.
name|valueOf
argument_list|(
name|record
operator|.
name|getField
argument_list|(
literal|1
argument_list|)
argument_list|)
argument_list|)
decl_stmt|;
name|solverServerService
operator|.
name|setLoggingLevel
argument_list|(
name|root
condition|?
literal|null
else|:
name|record
operator|.
name|getField
argument_list|(
literal|0
argument_list|)
argument_list|,
name|level
operator|.
name|toInt
argument_list|()
argument_list|)
expr_stmt|;
name|record
operator|.
name|setUniqueId
argument_list|(
name|root
condition|?
literal|0
else|:
name|System
operator|.
name|currentTimeMillis
argument_list|()
argument_list|)
expr_stmt|;
name|String
name|defaultValue
init|=
name|ApplicationProperties
operator|.
name|getDefaultProperties
argument_list|()
operator|.
name|getProperty
argument_list|(
name|root
condition|?
literal|"log4j.rootLogger"
else|:
literal|"log4j.logger."
operator|+
name|record
operator|.
name|getField
argument_list|(
literal|0
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|defaultValue
operator|!=
literal|null
operator|&&
name|defaultValue
operator|.
name|indexOf
argument_list|(
literal|','
argument_list|)
operator|>
literal|0
condition|)
name|defaultValue
operator|=
name|defaultValue
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|defaultValue
operator|.
name|indexOf
argument_list|(
literal|','
argument_list|)
argument_list|)
operator|.
name|trim
argument_list|()
expr_stmt|;
name|ApplicationConfig
name|config
init|=
name|ApplicationConfig
operator|.
name|getConfig
argument_list|(
name|root
condition|?
literal|"log4j.logger.root"
else|:
literal|"log4j.logger."
operator|+
name|record
operator|.
name|getField
argument_list|(
literal|0
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|level
operator|.
name|toString
argument_list|()
operator|.
name|equalsIgnoreCase
argument_list|(
name|defaultValue
argument_list|)
condition|)
block|{
if|if
condition|(
name|config
operator|!=
literal|null
condition|)
name|hibSession
operator|.
name|delete
argument_list|(
name|config
argument_list|)
expr_stmt|;
block|}
else|else
block|{
if|if
condition|(
name|config
operator|==
literal|null
condition|)
block|{
name|config
operator|=
operator|new
name|ApplicationConfig
argument_list|()
expr_stmt|;
name|config
operator|.
name|setKey
argument_list|(
name|root
condition|?
literal|"log4j.logger.root"
else|:
literal|"log4j.logger."
operator|+
name|record
operator|.
name|getField
argument_list|(
literal|0
argument_list|)
argument_list|)
expr_stmt|;
name|config
operator|.
name|setDescription
argument_list|(
name|MESSAGES
operator|.
name|descriptionLoggingLevelFor
argument_list|(
name|record
operator|.
name|getField
argument_list|(
literal|0
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|config
operator|.
name|setValue
argument_list|(
name|level
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
name|hibSession
operator|.
name|saveOrUpdate
argument_list|(
name|config
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Override
annotation|@
name|PreAuthorize
argument_list|(
literal|"checkPermission('ApplicationConfig')"
argument_list|)
specifier|public
name|void
name|delete
parameter_list|(
name|Record
name|record
parameter_list|,
name|SessionContext
name|context
parameter_list|,
name|Session
name|hibSession
parameter_list|)
block|{
name|boolean
name|root
init|=
name|record
operator|.
name|getUniqueId
argument_list|()
operator|!=
literal|null
operator|&&
name|record
operator|.
name|getUniqueId
argument_list|()
operator|==
literal|0
decl_stmt|;
name|solverServerService
operator|.
name|setLoggingLevel
argument_list|(
name|root
condition|?
literal|null
else|:
name|record
operator|.
name|getField
argument_list|(
literal|0
argument_list|)
argument_list|,
literal|null
argument_list|)
expr_stmt|;
name|ApplicationConfig
name|config
init|=
name|ApplicationConfig
operator|.
name|getConfig
argument_list|(
name|root
condition|?
literal|"log4j.logger.root"
else|:
literal|"log4j.logger."
operator|+
name|record
operator|.
name|getField
argument_list|(
literal|0
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|config
operator|!=
literal|null
condition|)
name|hibSession
operator|.
name|delete
argument_list|(
name|config
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

