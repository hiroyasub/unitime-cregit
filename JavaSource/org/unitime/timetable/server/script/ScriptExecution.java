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
name|script
package|;
end_package

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
name|IOException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|Writer
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
name|Date
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
name|javax
operator|.
name|script
operator|.
name|Compilable
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|script
operator|.
name|CompiledScript
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|script
operator|.
name|ScriptEngine
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|script
operator|.
name|ScriptEngineManager
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
name|fileupload
operator|.
name|FileItem
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
name|unitime
operator|.
name|commons
operator|.
name|Email
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
name|defaults
operator|.
name|ApplicationProperty
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
name|defaults
operator|.
name|SessionAttribute
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
name|ScriptInterface
operator|.
name|ExecuteScriptRpcRequest
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
name|Building
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
name|Location
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
name|Room
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
name|Script
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
name|ScriptParameter
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
name|SubjectArea
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
name|BuildingDAO
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
name|DepartmentDAO
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
name|RoomDAO
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
name|ScriptDAO
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
name|model
operator|.
name|dao
operator|.
name|SubjectAreaDAO
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
name|util
operator|.
name|Formats
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
name|queue
operator|.
name|QueueItem
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|ScriptExecution
extends|extends
name|QueueItem
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
name|ExecuteScriptRpcRequest
name|iRequest
decl_stmt|;
specifier|private
name|FileItem
name|iFile
init|=
literal|null
decl_stmt|;
specifier|public
name|ScriptExecution
parameter_list|(
name|ExecuteScriptRpcRequest
name|request
parameter_list|,
name|SessionContext
name|context
parameter_list|)
block|{
name|super
argument_list|(
name|context
operator|.
name|getUser
argument_list|()
argument_list|)
expr_stmt|;
name|iRequest
operator|=
name|request
expr_stmt|;
name|Script
name|script
init|=
name|ScriptDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|get
argument_list|(
name|request
operator|.
name|getScriptId
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|script
operator|.
name|getPermission
argument_list|()
operator|!=
literal|null
condition|)
name|context
operator|.
name|checkPermission
argument_list|(
name|Right
operator|.
name|valueOf
argument_list|(
name|script
operator|.
name|getPermission
argument_list|()
operator|.
name|replace
argument_list|(
literal|" "
argument_list|,
literal|""
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
for|for
control|(
name|ScriptParameter
name|parameter
range|:
name|script
operator|.
name|getParameters
argument_list|()
control|)
if|if
condition|(
literal|"file"
operator|.
name|equals
argument_list|(
name|parameter
operator|.
name|getType
argument_list|()
argument_list|)
condition|)
name|iFile
operator|=
operator|(
name|FileItem
operator|)
name|context
operator|.
name|getAttribute
argument_list|(
name|SessionAttribute
operator|.
name|LastUploadedFile
argument_list|)
expr_stmt|;
block|}
specifier|public
name|ExecuteScriptRpcRequest
name|getRequest
parameter_list|()
block|{
return|return
name|iRequest
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|type
parameter_list|()
block|{
return|return
literal|"Script"
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|name
parameter_list|()
block|{
return|return
name|iRequest
operator|.
name|getScriptName
argument_list|()
return|;
block|}
specifier|public
name|void
name|debug
parameter_list|(
name|String
name|message
parameter_list|)
block|{
name|super
operator|.
name|debug
argument_list|(
name|message
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|info
parameter_list|(
name|String
name|message
parameter_list|)
block|{
name|super
operator|.
name|info
argument_list|(
name|message
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|warn
parameter_list|(
name|String
name|message
parameter_list|)
block|{
name|super
operator|.
name|warn
argument_list|(
name|message
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|error
parameter_list|(
name|String
name|message
parameter_list|)
block|{
name|super
operator|.
name|error
argument_list|(
name|message
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|error
parameter_list|(
name|String
name|message
parameter_list|,
name|Throwable
name|t
parameter_list|)
block|{
name|super
operator|.
name|error
argument_list|(
name|message
argument_list|,
name|t
argument_list|)
expr_stmt|;
name|setError
argument_list|(
name|t
argument_list|)
expr_stmt|;
block|}
specifier|public
name|File
name|createOutput
parameter_list|(
name|String
name|prefix
parameter_list|,
name|String
name|ext
parameter_list|)
block|{
return|return
name|super
operator|.
name|createOutput
argument_list|(
name|prefix
argument_list|,
name|ext
argument_list|)
return|;
block|}
specifier|protected
name|Department
name|lookupDepartment
parameter_list|(
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
parameter_list|,
name|String
name|value
parameter_list|)
block|{
try|try
block|{
name|Department
name|d
init|=
name|DepartmentDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|get
argument_list|(
name|Long
operator|.
name|valueOf
argument_list|(
name|value
argument_list|)
argument_list|,
name|hibSession
argument_list|)
decl_stmt|;
if|if
condition|(
name|d
operator|!=
literal|null
condition|)
return|return
name|d
return|;
block|}
catch|catch
parameter_list|(
name|NumberFormatException
name|e
parameter_list|)
block|{
block|}
return|return
name|Department
operator|.
name|findByDeptCode
argument_list|(
name|value
argument_list|,
name|getSessionId
argument_list|()
argument_list|,
name|hibSession
argument_list|)
return|;
block|}
specifier|protected
name|SubjectArea
name|lookupSubjectArea
parameter_list|(
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
parameter_list|,
name|String
name|value
parameter_list|)
block|{
try|try
block|{
name|SubjectArea
name|s
init|=
name|SubjectAreaDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|get
argument_list|(
name|Long
operator|.
name|valueOf
argument_list|(
name|value
argument_list|)
argument_list|,
name|hibSession
argument_list|)
decl_stmt|;
if|if
condition|(
name|s
operator|!=
literal|null
condition|)
return|return
name|s
return|;
block|}
catch|catch
parameter_list|(
name|NumberFormatException
name|e
parameter_list|)
block|{
block|}
return|return
name|SubjectArea
operator|.
name|findByAbbv
argument_list|(
name|hibSession
argument_list|,
name|getSessionId
argument_list|()
argument_list|,
name|value
argument_list|)
return|;
block|}
specifier|protected
name|Building
name|lookupBuilding
parameter_list|(
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
parameter_list|,
name|String
name|value
parameter_list|)
block|{
try|try
block|{
name|Building
name|b
init|=
name|BuildingDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|get
argument_list|(
name|Long
operator|.
name|valueOf
argument_list|(
name|value
argument_list|)
argument_list|,
name|hibSession
argument_list|)
decl_stmt|;
if|if
condition|(
name|b
operator|!=
literal|null
condition|)
return|return
name|b
return|;
block|}
catch|catch
parameter_list|(
name|NumberFormatException
name|e
parameter_list|)
block|{
block|}
return|return
name|Building
operator|.
name|findByBldgAbbv
argument_list|(
name|hibSession
argument_list|,
name|getSessionId
argument_list|()
argument_list|,
name|value
argument_list|)
return|;
block|}
specifier|protected
name|Location
name|lookupLocation
parameter_list|(
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
parameter_list|,
name|String
name|value
parameter_list|)
block|{
try|try
block|{
name|Location
name|l
init|=
name|LocationDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|get
argument_list|(
name|Long
operator|.
name|valueOf
argument_list|(
name|value
argument_list|)
argument_list|,
name|hibSession
argument_list|)
decl_stmt|;
if|if
condition|(
name|l
operator|!=
literal|null
condition|)
return|return
name|l
return|;
block|}
catch|catch
parameter_list|(
name|NumberFormatException
name|e
parameter_list|)
block|{
block|}
return|return
name|Location
operator|.
name|findByName
argument_list|(
name|hibSession
argument_list|,
name|getSessionId
argument_list|()
argument_list|,
name|value
argument_list|)
return|;
block|}
specifier|protected
name|Room
name|lookupRoom
parameter_list|(
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
parameter_list|,
name|String
name|value
parameter_list|)
block|{
try|try
block|{
name|Room
name|r
init|=
name|RoomDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|get
argument_list|(
name|Long
operator|.
name|valueOf
argument_list|(
name|value
argument_list|)
argument_list|,
name|hibSession
argument_list|)
decl_stmt|;
if|if
condition|(
name|r
operator|!=
literal|null
condition|)
return|return
name|r
return|;
block|}
catch|catch
parameter_list|(
name|NumberFormatException
name|e
parameter_list|)
block|{
block|}
name|Location
name|l
init|=
name|Location
operator|.
name|findByName
argument_list|(
name|hibSession
argument_list|,
name|getSessionId
argument_list|()
argument_list|,
name|value
argument_list|)
decl_stmt|;
return|return
operator|(
name|l
operator|!=
literal|null
operator|&&
name|l
operator|instanceof
name|Room
condition|?
operator|(
name|Room
operator|)
name|l
else|:
literal|null
operator|)
return|;
block|}
annotation|@
name|Override
specifier|protected
name|void
name|execute
parameter_list|()
throws|throws
name|Exception
block|{
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
init|=
name|ScriptDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|getSession
argument_list|()
decl_stmt|;
name|Transaction
name|tx
init|=
name|hibSession
operator|.
name|beginTransaction
argument_list|()
decl_stmt|;
try|try
block|{
name|setStatus
argument_list|(
name|MSG
operator|.
name|scriptStatusStartingUp
argument_list|()
argument_list|,
literal|3
argument_list|)
expr_stmt|;
name|Script
name|script
init|=
name|ScriptDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|get
argument_list|(
name|iRequest
operator|.
name|getScriptId
argument_list|()
argument_list|,
name|hibSession
argument_list|)
decl_stmt|;
name|ScriptEngineManager
name|manager
init|=
operator|new
name|ScriptEngineManager
argument_list|()
decl_stmt|;
name|ScriptEngine
name|engine
init|=
name|manager
operator|.
name|getEngineByName
argument_list|(
name|script
operator|.
name|getEngine
argument_list|()
argument_list|)
decl_stmt|;
name|engine
operator|.
name|put
argument_list|(
literal|"hibSession"
argument_list|,
name|hibSession
argument_list|)
expr_stmt|;
name|engine
operator|.
name|put
argument_list|(
literal|"session"
argument_list|,
name|SessionDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|get
argument_list|(
name|getSessionId
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|engine
operator|.
name|put
argument_list|(
literal|"log"
argument_list|,
name|this
argument_list|)
expr_stmt|;
name|incProgress
argument_list|()
expr_stmt|;
name|engine
operator|.
name|getContext
argument_list|()
operator|.
name|setWriter
argument_list|(
operator|new
name|Writer
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|write
parameter_list|(
name|char
index|[]
name|cbuf
parameter_list|,
name|int
name|off
parameter_list|,
name|int
name|len
parameter_list|)
throws|throws
name|IOException
block|{
name|String
name|line
init|=
name|String
operator|.
name|valueOf
argument_list|(
name|cbuf
argument_list|,
name|off
argument_list|,
name|len
argument_list|)
decl_stmt|;
if|if
condition|(
name|line
operator|.
name|endsWith
argument_list|(
literal|"\n"
argument_list|)
condition|)
name|line
operator|=
name|line
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|line
operator|.
name|length
argument_list|()
operator|-
literal|1
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
name|line
operator|.
name|isEmpty
argument_list|()
condition|)
name|info
argument_list|(
name|line
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|flush
parameter_list|()
throws|throws
name|IOException
block|{
block|}
annotation|@
name|Override
specifier|public
name|void
name|close
parameter_list|()
throws|throws
name|IOException
block|{
block|}
block|}
argument_list|)
expr_stmt|;
name|engine
operator|.
name|getContext
argument_list|()
operator|.
name|setErrorWriter
argument_list|(
operator|new
name|Writer
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|write
parameter_list|(
name|char
index|[]
name|cbuf
parameter_list|,
name|int
name|off
parameter_list|,
name|int
name|len
parameter_list|)
throws|throws
name|IOException
block|{
name|String
name|line
init|=
name|String
operator|.
name|valueOf
argument_list|(
name|cbuf
argument_list|,
name|off
argument_list|,
name|len
argument_list|)
decl_stmt|;
if|if
condition|(
name|line
operator|.
name|endsWith
argument_list|(
literal|"\n"
argument_list|)
condition|)
name|line
operator|=
name|line
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|line
operator|.
name|length
argument_list|()
operator|-
literal|1
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
name|line
operator|.
name|isEmpty
argument_list|()
condition|)
name|warn
argument_list|(
name|line
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|flush
parameter_list|()
throws|throws
name|IOException
block|{
block|}
annotation|@
name|Override
specifier|public
name|void
name|close
parameter_list|()
throws|throws
name|IOException
block|{
block|}
block|}
argument_list|)
expr_stmt|;
name|incProgress
argument_list|()
expr_stmt|;
name|debug
argument_list|(
literal|"Engine: "
operator|+
name|engine
operator|.
name|getFactory
argument_list|()
operator|.
name|getEngineName
argument_list|()
operator|+
literal|" (ver. "
operator|+
name|engine
operator|.
name|getFactory
argument_list|()
operator|.
name|getEngineVersion
argument_list|()
operator|+
literal|")"
argument_list|)
expr_stmt|;
name|debug
argument_list|(
literal|"Language: "
operator|+
name|engine
operator|.
name|getFactory
argument_list|()
operator|.
name|getLanguageName
argument_list|()
operator|+
literal|" (ver. "
operator|+
name|engine
operator|.
name|getFactory
argument_list|()
operator|.
name|getLanguageVersion
argument_list|()
operator|+
literal|")"
argument_list|)
expr_stmt|;
for|for
control|(
name|ScriptParameter
name|parameter
range|:
name|script
operator|.
name|getParameters
argument_list|()
control|)
block|{
name|String
name|value
init|=
name|iRequest
operator|.
name|getParameters
argument_list|()
operator|.
name|get
argument_list|(
name|parameter
operator|.
name|getName
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
literal|"file"
operator|.
name|equals
argument_list|(
name|parameter
operator|.
name|getType
argument_list|()
argument_list|)
operator|&&
name|iFile
operator|!=
literal|null
condition|)
block|{
name|debug
argument_list|(
name|parameter
operator|.
name|getName
argument_list|()
operator|+
literal|": "
operator|+
name|iFile
operator|.
name|getName
argument_list|()
operator|+
literal|" ("
operator|+
name|iFile
operator|.
name|getSize
argument_list|()
operator|+
literal|" bytes)"
argument_list|)
expr_stmt|;
name|engine
operator|.
name|put
argument_list|(
name|parameter
operator|.
name|getName
argument_list|()
argument_list|,
name|iFile
argument_list|)
expr_stmt|;
continue|continue;
block|}
if|if
condition|(
name|value
operator|==
literal|null
condition|)
name|value
operator|=
name|parameter
operator|.
name|getDefaultValue
argument_list|()
expr_stmt|;
if|if
condition|(
name|value
operator|==
literal|null
condition|)
block|{
name|engine
operator|.
name|put
argument_list|(
name|parameter
operator|.
name|getName
argument_list|()
argument_list|,
literal|null
argument_list|)
expr_stmt|;
continue|continue;
block|}
name|debug
argument_list|(
name|parameter
operator|.
name|getName
argument_list|()
operator|+
literal|": "
operator|+
name|value
argument_list|)
expr_stmt|;
if|if
condition|(
name|parameter
operator|.
name|getType
argument_list|()
operator|.
name|equalsIgnoreCase
argument_list|(
literal|"boolean"
argument_list|)
condition|)
block|{
name|engine
operator|.
name|put
argument_list|(
name|parameter
operator|.
name|getName
argument_list|()
argument_list|,
literal|"true"
operator|.
name|equalsIgnoreCase
argument_list|(
name|value
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|parameter
operator|.
name|getType
argument_list|()
operator|.
name|equalsIgnoreCase
argument_list|(
literal|"long"
argument_list|)
condition|)
block|{
name|engine
operator|.
name|put
argument_list|(
name|parameter
operator|.
name|getName
argument_list|()
argument_list|,
name|value
operator|==
literal|null
operator|||
name|value
operator|.
name|isEmpty
argument_list|()
condition|?
literal|null
else|:
name|Long
operator|.
name|valueOf
argument_list|(
name|value
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|parameter
operator|.
name|getType
argument_list|()
operator|.
name|equalsIgnoreCase
argument_list|(
literal|"int"
argument_list|)
operator|||
name|parameter
operator|.
name|getType
argument_list|()
operator|.
name|equalsIgnoreCase
argument_list|(
literal|"integer"
argument_list|)
operator|||
name|parameter
operator|.
name|getType
argument_list|()
operator|.
name|equalsIgnoreCase
argument_list|(
literal|"time"
argument_list|)
operator|||
name|parameter
operator|.
name|getType
argument_list|()
operator|.
name|equalsIgnoreCase
argument_list|(
literal|"slot"
argument_list|)
condition|)
block|{
name|engine
operator|.
name|put
argument_list|(
name|parameter
operator|.
name|getName
argument_list|()
argument_list|,
name|value
operator|==
literal|null
operator|||
name|value
operator|.
name|isEmpty
argument_list|()
condition|?
literal|null
else|:
name|Integer
operator|.
name|valueOf
argument_list|(
name|value
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|parameter
operator|.
name|getType
argument_list|()
operator|.
name|equalsIgnoreCase
argument_list|(
literal|"double"
argument_list|)
condition|)
block|{
name|engine
operator|.
name|put
argument_list|(
name|parameter
operator|.
name|getName
argument_list|()
argument_list|,
name|value
operator|==
literal|null
operator|||
name|value
operator|.
name|isEmpty
argument_list|()
condition|?
literal|null
else|:
name|Double
operator|.
name|valueOf
argument_list|(
name|value
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|parameter
operator|.
name|getType
argument_list|()
operator|.
name|equalsIgnoreCase
argument_list|(
literal|"float"
argument_list|)
condition|)
block|{
name|engine
operator|.
name|put
argument_list|(
name|parameter
operator|.
name|getName
argument_list|()
argument_list|,
name|value
operator|==
literal|null
operator|||
name|value
operator|.
name|isEmpty
argument_list|()
condition|?
literal|null
else|:
name|Float
operator|.
name|valueOf
argument_list|(
name|value
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|parameter
operator|.
name|getType
argument_list|()
operator|.
name|equalsIgnoreCase
argument_list|(
literal|"short"
argument_list|)
condition|)
block|{
name|engine
operator|.
name|put
argument_list|(
name|parameter
operator|.
name|getName
argument_list|()
argument_list|,
name|value
operator|==
literal|null
operator|||
name|value
operator|.
name|isEmpty
argument_list|()
condition|?
literal|null
else|:
name|Short
operator|.
name|valueOf
argument_list|(
name|value
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|parameter
operator|.
name|getType
argument_list|()
operator|.
name|equalsIgnoreCase
argument_list|(
literal|"byte"
argument_list|)
condition|)
block|{
name|engine
operator|.
name|put
argument_list|(
name|parameter
operator|.
name|getName
argument_list|()
argument_list|,
name|value
operator|==
literal|null
operator|||
name|value
operator|.
name|isEmpty
argument_list|()
condition|?
literal|null
else|:
name|Byte
operator|.
name|valueOf
argument_list|(
name|value
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|parameter
operator|.
name|getType
argument_list|()
operator|.
name|equalsIgnoreCase
argument_list|(
literal|"date"
argument_list|)
condition|)
block|{
name|Formats
operator|.
name|Format
argument_list|<
name|Date
argument_list|>
name|dateFormat
init|=
name|Formats
operator|.
name|getDateFormat
argument_list|(
name|Formats
operator|.
name|Pattern
operator|.
name|DATE_EVENT
argument_list|)
decl_stmt|;
name|engine
operator|.
name|put
argument_list|(
name|parameter
operator|.
name|getName
argument_list|()
argument_list|,
name|dateFormat
operator|.
name|parse
argument_list|(
name|value
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|parameter
operator|.
name|getType
argument_list|()
operator|.
name|equalsIgnoreCase
argument_list|(
literal|"datetime"
argument_list|)
operator|||
name|parameter
operator|.
name|getType
argument_list|()
operator|.
name|equalsIgnoreCase
argument_list|(
literal|"timestamp"
argument_list|)
condition|)
block|{
name|Formats
operator|.
name|Format
argument_list|<
name|Date
argument_list|>
name|dateFormat
init|=
name|Formats
operator|.
name|getDateFormat
argument_list|(
name|Formats
operator|.
name|Pattern
operator|.
name|DATE_TIME_STAMP
argument_list|)
decl_stmt|;
name|engine
operator|.
name|put
argument_list|(
name|parameter
operator|.
name|getName
argument_list|()
argument_list|,
name|dateFormat
operator|.
name|parse
argument_list|(
name|value
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|parameter
operator|.
name|getType
argument_list|()
operator|.
name|equalsIgnoreCase
argument_list|(
literal|"department"
argument_list|)
condition|)
block|{
name|engine
operator|.
name|put
argument_list|(
name|parameter
operator|.
name|getName
argument_list|()
argument_list|,
name|lookupDepartment
argument_list|(
name|hibSession
argument_list|,
name|value
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|parameter
operator|.
name|getType
argument_list|()
operator|.
name|equalsIgnoreCase
argument_list|(
literal|"departments"
argument_list|)
condition|)
block|{
name|List
argument_list|<
name|Department
argument_list|>
name|departments
init|=
operator|new
name|ArrayList
argument_list|<
name|Department
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|String
name|id
range|:
name|value
operator|.
name|split
argument_list|(
literal|","
argument_list|)
control|)
if|if
condition|(
operator|!
name|id
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|Department
name|d
init|=
name|lookupDepartment
argument_list|(
name|hibSession
argument_list|,
name|id
argument_list|)
decl_stmt|;
if|if
condition|(
name|d
operator|!=
literal|null
condition|)
name|departments
operator|.
name|add
argument_list|(
name|d
argument_list|)
expr_stmt|;
block|}
name|engine
operator|.
name|put
argument_list|(
name|parameter
operator|.
name|getName
argument_list|()
argument_list|,
name|departments
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|parameter
operator|.
name|getType
argument_list|()
operator|.
name|equalsIgnoreCase
argument_list|(
literal|"subject"
argument_list|)
condition|)
block|{
name|engine
operator|.
name|put
argument_list|(
name|parameter
operator|.
name|getName
argument_list|()
argument_list|,
name|lookupSubjectArea
argument_list|(
name|hibSession
argument_list|,
name|value
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|parameter
operator|.
name|getType
argument_list|()
operator|.
name|equalsIgnoreCase
argument_list|(
literal|"subjects"
argument_list|)
condition|)
block|{
name|List
argument_list|<
name|SubjectArea
argument_list|>
name|subjects
init|=
operator|new
name|ArrayList
argument_list|<
name|SubjectArea
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|String
name|id
range|:
name|value
operator|.
name|split
argument_list|(
literal|","
argument_list|)
control|)
if|if
condition|(
operator|!
name|id
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|SubjectArea
name|s
init|=
name|lookupSubjectArea
argument_list|(
name|hibSession
argument_list|,
name|id
argument_list|)
decl_stmt|;
if|if
condition|(
name|s
operator|!=
literal|null
condition|)
name|subjects
operator|.
name|add
argument_list|(
name|s
argument_list|)
expr_stmt|;
block|}
name|engine
operator|.
name|put
argument_list|(
name|parameter
operator|.
name|getName
argument_list|()
argument_list|,
name|subjects
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|parameter
operator|.
name|getType
argument_list|()
operator|.
name|equalsIgnoreCase
argument_list|(
literal|"building"
argument_list|)
condition|)
block|{
name|engine
operator|.
name|put
argument_list|(
name|parameter
operator|.
name|getName
argument_list|()
argument_list|,
name|lookupBuilding
argument_list|(
name|hibSession
argument_list|,
name|value
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|parameter
operator|.
name|getType
argument_list|()
operator|.
name|equalsIgnoreCase
argument_list|(
literal|"buildings"
argument_list|)
condition|)
block|{
name|List
argument_list|<
name|Building
argument_list|>
name|buildings
init|=
operator|new
name|ArrayList
argument_list|<
name|Building
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|String
name|id
range|:
name|value
operator|.
name|split
argument_list|(
literal|","
argument_list|)
control|)
if|if
condition|(
operator|!
name|id
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|Building
name|b
init|=
name|lookupBuilding
argument_list|(
name|hibSession
argument_list|,
name|id
argument_list|)
decl_stmt|;
if|if
condition|(
name|b
operator|!=
literal|null
condition|)
name|buildings
operator|.
name|add
argument_list|(
name|b
argument_list|)
expr_stmt|;
block|}
name|engine
operator|.
name|put
argument_list|(
name|parameter
operator|.
name|getName
argument_list|()
argument_list|,
name|buildings
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|parameter
operator|.
name|getType
argument_list|()
operator|.
name|equalsIgnoreCase
argument_list|(
literal|"room"
argument_list|)
condition|)
block|{
name|engine
operator|.
name|put
argument_list|(
name|parameter
operator|.
name|getName
argument_list|()
argument_list|,
name|lookupRoom
argument_list|(
name|hibSession
argument_list|,
name|value
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|parameter
operator|.
name|getType
argument_list|()
operator|.
name|equalsIgnoreCase
argument_list|(
literal|"rooms"
argument_list|)
condition|)
block|{
name|List
argument_list|<
name|Room
argument_list|>
name|rooms
init|=
operator|new
name|ArrayList
argument_list|<
name|Room
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|String
name|id
range|:
name|value
operator|.
name|split
argument_list|(
literal|","
argument_list|)
control|)
if|if
condition|(
operator|!
name|id
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|Room
name|r
init|=
name|lookupRoom
argument_list|(
name|hibSession
argument_list|,
name|id
argument_list|)
decl_stmt|;
if|if
condition|(
name|r
operator|!=
literal|null
condition|)
name|rooms
operator|.
name|add
argument_list|(
name|r
argument_list|)
expr_stmt|;
block|}
name|engine
operator|.
name|put
argument_list|(
name|parameter
operator|.
name|getName
argument_list|()
argument_list|,
name|rooms
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|parameter
operator|.
name|getType
argument_list|()
operator|.
name|equalsIgnoreCase
argument_list|(
literal|"location"
argument_list|)
condition|)
block|{
name|engine
operator|.
name|put
argument_list|(
name|parameter
operator|.
name|getName
argument_list|()
argument_list|,
name|lookupLocation
argument_list|(
name|hibSession
argument_list|,
name|value
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|parameter
operator|.
name|getType
argument_list|()
operator|.
name|equalsIgnoreCase
argument_list|(
literal|"locations"
argument_list|)
condition|)
block|{
name|List
argument_list|<
name|Location
argument_list|>
name|locations
init|=
operator|new
name|ArrayList
argument_list|<
name|Location
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|String
name|id
range|:
name|value
operator|.
name|split
argument_list|(
literal|","
argument_list|)
control|)
if|if
condition|(
operator|!
name|id
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|Location
name|l
init|=
name|lookupLocation
argument_list|(
name|hibSession
argument_list|,
name|id
argument_list|)
decl_stmt|;
if|if
condition|(
name|l
operator|!=
literal|null
condition|)
name|locations
operator|.
name|add
argument_list|(
name|l
argument_list|)
expr_stmt|;
block|}
name|engine
operator|.
name|put
argument_list|(
name|parameter
operator|.
name|getName
argument_list|()
argument_list|,
name|locations
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|engine
operator|.
name|put
argument_list|(
name|parameter
operator|.
name|getName
argument_list|()
argument_list|,
name|value
argument_list|)
expr_stmt|;
block|}
block|}
name|incProgress
argument_list|()
expr_stmt|;
if|if
condition|(
name|engine
operator|instanceof
name|Compilable
condition|)
block|{
name|setStatus
argument_list|(
name|MSG
operator|.
name|scriptStatusCompiling
argument_list|()
argument_list|,
literal|1
argument_list|)
expr_stmt|;
name|CompiledScript
name|compiled
init|=
operator|(
operator|(
name|Compilable
operator|)
name|engine
operator|)
operator|.
name|compile
argument_list|(
name|script
operator|.
name|getScript
argument_list|()
argument_list|)
decl_stmt|;
name|incProgress
argument_list|()
expr_stmt|;
name|setStatus
argument_list|(
name|MSG
operator|.
name|scriptStatusRunning
argument_list|()
argument_list|,
literal|100
argument_list|)
expr_stmt|;
name|compiled
operator|.
name|eval
argument_list|()
expr_stmt|;
block|}
else|else
block|{
name|setStatus
argument_list|(
name|MSG
operator|.
name|scriptStatusRunning
argument_list|()
argument_list|,
literal|100
argument_list|)
expr_stmt|;
name|engine
operator|.
name|eval
argument_list|(
name|script
operator|.
name|getScript
argument_list|()
argument_list|)
expr_stmt|;
block|}
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
name|setStatus
argument_list|(
name|MSG
operator|.
name|scriptStatusAllDone
argument_list|()
argument_list|,
literal|1
argument_list|)
expr_stmt|;
name|incProgress
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|tx
operator|.
name|rollback
argument_list|()
expr_stmt|;
name|error
argument_list|(
name|MSG
operator|.
name|failedExecution
argument_list|(
name|e
operator|.
name|getMessage
argument_list|()
argument_list|)
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
finally|finally
block|{
name|hibSession
operator|.
name|close
argument_list|()
expr_stmt|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|void
name|executeItem
parameter_list|()
block|{
name|super
operator|.
name|executeItem
argument_list|()
expr_stmt|;
name|sendEmail
argument_list|()
expr_stmt|;
block|}
specifier|protected
name|boolean
name|sendEmail
parameter_list|()
block|{
if|if
condition|(
operator|!
name|getRequest
argument_list|()
operator|.
name|hasEmail
argument_list|()
condition|)
return|return
literal|false
return|;
try|try
block|{
name|Email
name|email
init|=
name|Email
operator|.
name|createEmail
argument_list|()
decl_stmt|;
name|String
name|suffix
init|=
name|ApplicationProperty
operator|.
name|EmailDefaultAddressSuffix
operator|.
name|value
argument_list|()
decl_stmt|;
for|for
control|(
name|String
name|address
range|:
name|getRequest
argument_list|()
operator|.
name|getEmail
argument_list|()
operator|.
name|split
argument_list|(
literal|"[\n,]"
argument_list|)
control|)
block|{
if|if
condition|(
operator|!
name|address
operator|.
name|trim
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
if|if
condition|(
name|suffix
operator|!=
literal|null
operator|&&
name|address
operator|.
name|indexOf
argument_list|(
literal|'@'
argument_list|)
operator|<
literal|0
condition|)
name|email
operator|.
name|addRecipientCC
argument_list|(
name|address
operator|.
name|trim
argument_list|()
operator|+
name|suffix
argument_list|,
literal|null
argument_list|)
expr_stmt|;
else|else
name|email
operator|.
name|addRecipientCC
argument_list|(
name|address
operator|.
name|trim
argument_list|()
argument_list|,
literal|null
argument_list|)
expr_stmt|;
block|}
block|}
name|email
operator|.
name|setHTML
argument_list|(
name|log
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|hasOutput
argument_list|()
condition|)
name|email
operator|.
name|addAttachment
argument_list|(
name|output
argument_list|()
argument_list|,
name|getOutputName
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|hasOwnerEmail
argument_list|()
condition|)
name|email
operator|.
name|setReplyTo
argument_list|(
name|getOwnerEmail
argument_list|()
argument_list|,
name|getOwnerName
argument_list|()
argument_list|)
expr_stmt|;
name|email
operator|.
name|setSubject
argument_list|(
name|name
argument_list|()
operator|+
operator|(
name|hasError
argument_list|()
condition|?
literal|" -- "
operator|+
name|error
argument_list|()
operator|.
name|getMessage
argument_list|()
else|:
literal|""
operator|)
argument_list|)
expr_stmt|;
name|email
operator|.
name|send
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|Throwable
name|t
init|=
name|error
argument_list|()
decl_stmt|;
name|error
argument_list|(
name|MSG
operator|.
name|failedEmail
argument_list|(
name|e
operator|.
name|getMessage
argument_list|()
argument_list|)
argument_list|,
name|e
argument_list|)
expr_stmt|;
if|if
condition|(
name|t
operator|!=
literal|null
condition|)
name|setError
argument_list|(
name|t
argument_list|)
expr_stmt|;
block|}
return|return
literal|true
return|;
block|}
block|}
end_class

end_unit

