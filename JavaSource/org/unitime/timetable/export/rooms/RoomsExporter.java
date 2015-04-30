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
name|export
operator|.
name|rooms
package|;
end_package

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
name|Collection
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Collections
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
name|List
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
name|defaults
operator|.
name|CommonValues
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
name|UserProperty
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
name|events
operator|.
name|EventAction
operator|.
name|EventContext
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
name|export
operator|.
name|ExportHelper
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
name|export
operator|.
name|Exporter
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
name|client
operator|.
name|rooms
operator|.
name|RoomsComparator
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
name|GwtConstants
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
name|EventInterface
operator|.
name|FilterRpcRequest
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
name|EventInterface
operator|.
name|FilterRpcResponse
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
name|EventInterface
operator|.
name|FilterRpcResponse
operator|.
name|Entity
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
name|RoomInterface
operator|.
name|DepartmentInterface
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
name|RoomInterface
operator|.
name|ExamTypeInterface
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
name|RoomInterface
operator|.
name|FeatureInterface
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
name|RoomInterface
operator|.
name|GroupInterface
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
name|RoomInterface
operator|.
name|RoomDetailInterface
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
name|RoomInterface
operator|.
name|RoomFilterRpcRequest
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
name|RoomInterface
operator|.
name|RoomFlag
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
name|RoomInterface
operator|.
name|RoomsPageMode
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
name|Session
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
name|security
operator|.
name|UserAuthority
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
name|UserContext
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
name|context
operator|.
name|UniTimeUserContext
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
name|server
operator|.
name|rooms
operator|.
name|RoomDetailsBackend
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
name|webutil
operator|.
name|RequiredTimeTable
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
specifier|abstract
class|class
name|RoomsExporter
implements|implements
name|Exporter
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
specifier|protected
specifier|static
specifier|final
name|GwtConstants
name|CONSTANTS
init|=
name|Localization
operator|.
name|create
argument_list|(
name|GwtConstants
operator|.
name|class
argument_list|)
decl_stmt|;
annotation|@
name|Override
specifier|public
name|void
name|export
parameter_list|(
name|ExportHelper
name|helper
parameter_list|)
throws|throws
name|IOException
block|{
name|Long
name|sessionId
init|=
name|helper
operator|.
name|getAcademicSessionId
argument_list|()
decl_stmt|;
if|if
condition|(
name|sessionId
operator|==
literal|null
condition|)
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"Academic session not provided, please set the term parameter."
argument_list|)
throw|;
name|Session
name|session
init|=
name|SessionDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|get
argument_list|(
name|sessionId
argument_list|)
decl_stmt|;
if|if
condition|(
name|session
operator|==
literal|null
condition|)
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"Given academic session no longer exists."
argument_list|)
throw|;
name|RoomFilterRpcRequest
name|request
init|=
operator|new
name|RoomFilterRpcRequest
argument_list|()
decl_stmt|;
name|request
operator|.
name|setCommand
argument_list|(
name|FilterRpcRequest
operator|.
name|Command
operator|.
name|ENUMERATE
argument_list|)
expr_stmt|;
name|request
operator|.
name|setSessionId
argument_list|(
name|sessionId
argument_list|)
expr_stmt|;
for|for
control|(
name|Enumeration
argument_list|<
name|String
argument_list|>
name|e
init|=
name|helper
operator|.
name|getParameterNames
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
name|command
init|=
name|e
operator|.
name|nextElement
argument_list|()
decl_stmt|;
if|if
condition|(
name|command
operator|.
name|equals
argument_list|(
literal|"r:text"
argument_list|)
condition|)
block|{
name|request
operator|.
name|setText
argument_list|(
name|helper
operator|.
name|getParameter
argument_list|(
literal|"r:text"
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|command
operator|.
name|startsWith
argument_list|(
literal|"r:"
argument_list|)
condition|)
block|{
for|for
control|(
name|String
name|value
range|:
name|helper
operator|.
name|getParameterValues
argument_list|(
name|command
argument_list|)
control|)
name|request
operator|.
name|addOption
argument_list|(
name|command
operator|.
name|substring
argument_list|(
literal|2
argument_list|)
argument_list|,
name|value
argument_list|)
expr_stmt|;
block|}
block|}
name|request
operator|.
name|setOption
argument_list|(
literal|"flag"
argument_list|,
literal|"gridAsText"
argument_list|)
expr_stmt|;
name|UserContext
name|u
init|=
name|helper
operator|.
name|getSessionContext
argument_list|()
operator|.
name|getUser
argument_list|()
decl_stmt|;
name|String
name|user
init|=
name|helper
operator|.
name|getParameter
argument_list|(
literal|"user"
argument_list|)
decl_stmt|;
if|if
condition|(
name|u
operator|==
literal|null
operator|&&
name|user
operator|!=
literal|null
operator|&&
operator|!
name|checkRights
argument_list|(
name|helper
argument_list|)
condition|)
block|{
name|u
operator|=
operator|new
name|UniTimeUserContext
argument_list|(
name|user
argument_list|,
literal|null
argument_list|,
literal|null
argument_list|,
literal|null
argument_list|)
expr_stmt|;
name|String
name|role
init|=
name|helper
operator|.
name|getParameter
argument_list|(
literal|"role"
argument_list|)
decl_stmt|;
if|if
condition|(
name|role
operator|!=
literal|null
condition|)
block|{
for|for
control|(
name|UserAuthority
name|a
range|:
name|u
operator|.
name|getAuthorities
argument_list|()
control|)
block|{
if|if
condition|(
name|a
operator|.
name|getAcademicSession
argument_list|()
operator|!=
literal|null
operator|&&
name|a
operator|.
name|getAcademicSession
argument_list|()
operator|.
name|getQualifierId
argument_list|()
operator|.
name|equals
argument_list|(
name|sessionId
argument_list|)
operator|&&
name|role
operator|.
name|equals
argument_list|(
name|a
operator|.
name|getRole
argument_list|()
argument_list|)
condition|)
block|{
name|u
operator|.
name|setCurrentAuthority
argument_list|(
name|a
argument_list|)
expr_stmt|;
break|break;
block|}
block|}
block|}
block|}
name|EventContext
name|context
init|=
operator|new
name|EventContext
argument_list|(
name|helper
operator|.
name|getSessionContext
argument_list|()
argument_list|,
name|u
argument_list|,
name|sessionId
argument_list|)
decl_stmt|;
if|if
condition|(
name|u
operator|!=
literal|null
operator|&&
name|u
operator|.
name|getExternalUserId
argument_list|()
operator|!=
literal|null
condition|)
name|request
operator|.
name|setOption
argument_list|(
literal|"user"
argument_list|,
name|u
operator|.
name|getExternalUserId
argument_list|()
argument_list|)
expr_stmt|;
name|int
name|deptMode
init|=
literal|0
decl_stmt|;
if|if
condition|(
name|helper
operator|.
name|getParameter
argument_list|(
literal|"dm"
argument_list|)
operator|!=
literal|null
condition|)
name|deptMode
operator|=
name|Integer
operator|.
name|parseInt
argument_list|(
name|helper
operator|.
name|getParameter
argument_list|(
literal|"dm"
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
name|helper
operator|.
name|isRequestEncoded
argument_list|()
condition|)
name|context
operator|.
name|checkPermission
argument_list|(
name|Right
operator|.
name|Rooms
argument_list|)
expr_stmt|;
name|FilterRpcResponse
name|response
init|=
operator|new
name|FilterRpcResponse
argument_list|()
decl_stmt|;
operator|new
name|RoomDetailsBackend
argument_list|()
operator|.
name|enumarate
argument_list|(
name|request
argument_list|,
name|response
argument_list|,
name|context
argument_list|)
expr_stmt|;
name|List
argument_list|<
name|RoomDetailInterface
argument_list|>
name|rooms
init|=
operator|new
name|ArrayList
argument_list|<
name|RoomDetailInterface
argument_list|>
argument_list|()
decl_stmt|;
if|if
condition|(
name|response
operator|.
name|hasResults
argument_list|()
condition|)
block|{
for|for
control|(
name|Entity
name|e
range|:
name|response
operator|.
name|getResults
argument_list|()
control|)
name|rooms
operator|.
name|add
argument_list|(
operator|(
name|RoomDetailInterface
operator|)
name|e
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|helper
operator|.
name|getParameter
argument_list|(
literal|"sort"
argument_list|)
operator|!=
literal|null
condition|)
block|{
name|RoomsComparator
name|cmp
init|=
literal|null
decl_stmt|;
try|try
block|{
name|int
name|sort
init|=
name|Integer
operator|.
name|parseInt
argument_list|(
name|helper
operator|.
name|getParameter
argument_list|(
literal|"sort"
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|sort
operator|>
literal|0
condition|)
block|{
name|cmp
operator|=
operator|new
name|RoomsComparator
argument_list|(
name|RoomsComparator
operator|.
name|Column
operator|.
name|values
argument_list|()
index|[
name|sort
operator|-
literal|1
index|]
argument_list|,
literal|true
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|sort
operator|<
literal|0
condition|)
block|{
name|cmp
operator|=
operator|new
name|RoomsComparator
argument_list|(
name|RoomsComparator
operator|.
name|Column
operator|.
name|values
argument_list|()
index|[
operator|-
literal|1
operator|-
name|sort
index|]
argument_list|,
literal|false
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
block|}
if|if
condition|(
name|cmp
operator|!=
literal|null
condition|)
name|Collections
operator|.
name|sort
argument_list|(
name|rooms
argument_list|,
name|cmp
argument_list|)
expr_stmt|;
block|}
name|int
name|roomCookieFlags
init|=
operator|(
name|helper
operator|.
name|getParameter
argument_list|(
literal|"flags"
argument_list|)
operator|==
literal|null
condition|?
name|RoomsPageMode
operator|.
name|COURSES
operator|.
name|getFlags
argument_list|()
else|:
name|Integer
operator|.
name|parseInt
argument_list|(
name|helper
operator|.
name|getParameter
argument_list|(
literal|"flags"
argument_list|)
argument_list|)
operator|)
decl_stmt|;
name|boolean
name|vertical
init|=
literal|true
decl_stmt|;
if|if
condition|(
name|helper
operator|.
name|getParameter
argument_list|(
literal|"horizontal"
argument_list|)
operator|!=
literal|null
condition|)
name|vertical
operator|=
literal|"0"
operator|.
name|equals
argument_list|(
name|helper
operator|.
name|getParameter
argument_list|(
literal|"horizontal"
argument_list|)
argument_list|)
expr_stmt|;
if|else if
condition|(
name|context
operator|.
name|getUser
argument_list|()
operator|!=
literal|null
condition|)
name|vertical
operator|=
name|CommonValues
operator|.
name|VerticalGrid
operator|.
name|eq
argument_list|(
name|context
operator|.
name|getUser
argument_list|()
operator|.
name|getProperty
argument_list|(
name|UserProperty
operator|.
name|GridOrientation
argument_list|)
argument_list|)
expr_stmt|;
name|String
name|mode
init|=
name|helper
operator|.
name|getParameter
argument_list|(
literal|"mode"
argument_list|)
decl_stmt|;
if|if
condition|(
name|mode
operator|==
literal|null
operator|&&
name|context
operator|.
name|getUser
argument_list|()
operator|!=
literal|null
condition|)
name|mode
operator|=
name|RequiredTimeTable
operator|.
name|getTimeGridSize
argument_list|(
name|context
operator|.
name|getUser
argument_list|()
argument_list|)
expr_stmt|;
name|boolean
name|gridAsText
init|=
operator|(
name|context
operator|.
name|getUser
argument_list|()
operator|==
literal|null
condition|?
literal|false
else|:
name|CommonValues
operator|.
name|TextGrid
operator|.
name|eq
argument_list|(
name|UserProperty
operator|.
name|GridOrientation
operator|.
name|get
argument_list|(
name|context
operator|.
name|getUser
argument_list|()
argument_list|)
argument_list|)
operator|)
decl_stmt|;
name|print
argument_list|(
name|helper
argument_list|,
name|rooms
argument_list|,
name|request
operator|.
name|getOption
argument_list|(
literal|"department"
argument_list|)
argument_list|,
name|roomCookieFlags
argument_list|,
name|deptMode
argument_list|,
name|gridAsText
argument_list|,
name|vertical
argument_list|,
name|mode
argument_list|)
expr_stmt|;
block|}
specifier|protected
specifier|abstract
name|void
name|print
parameter_list|(
name|ExportHelper
name|helper
parameter_list|,
name|List
argument_list|<
name|RoomDetailInterface
argument_list|>
name|rooms
parameter_list|,
name|String
name|department
parameter_list|,
name|int
name|roomCookieFlags
parameter_list|,
name|int
name|deptMode
parameter_list|,
name|boolean
name|gridAsText
parameter_list|,
name|boolean
name|vertical
parameter_list|,
name|String
name|mode
parameter_list|)
throws|throws
name|IOException
function_decl|;
specifier|protected
name|boolean
name|checkRights
parameter_list|(
name|ExportHelper
name|helper
parameter_list|)
block|{
return|return
operator|!
name|helper
operator|.
name|isRequestEncoded
argument_list|()
return|;
block|}
specifier|protected
name|void
name|hideColumns
parameter_list|(
name|Printer
name|out
parameter_list|,
name|List
argument_list|<
name|RoomDetailInterface
argument_list|>
name|rooms
parameter_list|,
name|int
name|roomCookieFlags
parameter_list|)
block|{
for|for
control|(
name|RoomFlag
name|flag
range|:
name|RoomFlag
operator|.
name|values
argument_list|()
control|)
block|{
if|if
condition|(
operator|!
name|flag
operator|.
name|in
argument_list|(
name|roomCookieFlags
argument_list|)
condition|)
name|hideColumn
argument_list|(
name|out
argument_list|,
name|rooms
argument_list|,
name|flag
argument_list|)
expr_stmt|;
block|}
block|}
specifier|protected
name|void
name|hideColumn
parameter_list|(
name|Printer
name|out
parameter_list|,
name|List
argument_list|<
name|RoomDetailInterface
argument_list|>
name|rooms
parameter_list|,
name|RoomFlag
name|flag
parameter_list|)
block|{
block|}
specifier|protected
name|String
name|dept2string
parameter_list|(
name|DepartmentInterface
name|d
parameter_list|,
name|int
name|deptMode
parameter_list|)
block|{
if|if
condition|(
name|d
operator|==
literal|null
condition|)
return|return
literal|""
return|;
switch|switch
condition|(
name|deptMode
condition|)
block|{
case|case
literal|0
case|:
return|return
name|d
operator|.
name|getDeptCode
argument_list|()
return|;
case|case
literal|1
case|:
return|return
name|d
operator|.
name|getExtAbbreviationWhenExist
argument_list|()
return|;
case|case
literal|2
case|:
return|return
name|d
operator|.
name|getExtLabelWhenExist
argument_list|()
return|;
case|case
literal|3
case|:
return|return
name|d
operator|.
name|getExtAbbreviationWhenExist
argument_list|()
operator|+
literal|" - "
operator|+
name|d
operator|.
name|getExtLabelWhenExist
argument_list|()
return|;
case|case
literal|4
case|:
return|return
name|d
operator|.
name|getDeptCode
argument_list|()
operator|+
literal|" - "
operator|+
name|d
operator|.
name|getExtLabelWhenExist
argument_list|()
return|;
default|default:
return|return
name|d
operator|.
name|getDeptCode
argument_list|()
return|;
block|}
block|}
specifier|protected
name|String
name|dept2string
parameter_list|(
name|Collection
argument_list|<
name|DepartmentInterface
argument_list|>
name|departments
parameter_list|,
name|int
name|deptMode
parameter_list|,
name|String
name|separator
parameter_list|)
block|{
if|if
condition|(
name|departments
operator|==
literal|null
operator|||
name|departments
operator|.
name|isEmpty
argument_list|()
condition|)
return|return
literal|""
return|;
name|String
name|ret
init|=
literal|""
decl_stmt|;
for|for
control|(
name|DepartmentInterface
name|d
range|:
name|departments
control|)
block|{
name|ret
operator|+=
operator|(
name|ret
operator|.
name|isEmpty
argument_list|()
condition|?
literal|""
else|:
name|separator
operator|)
operator|+
name|dept2string
argument_list|(
name|d
argument_list|,
name|deptMode
argument_list|)
expr_stmt|;
block|}
return|return
name|ret
return|;
block|}
specifier|protected
name|String
name|pref2string
parameter_list|(
name|Collection
argument_list|<
name|DepartmentInterface
argument_list|>
name|departments
parameter_list|,
name|int
name|deptMode
parameter_list|,
name|String
name|separator
parameter_list|)
block|{
if|if
condition|(
name|departments
operator|==
literal|null
operator|||
name|departments
operator|.
name|isEmpty
argument_list|()
condition|)
return|return
literal|""
return|;
name|String
name|ret
init|=
literal|""
decl_stmt|;
for|for
control|(
name|DepartmentInterface
name|d
range|:
name|departments
control|)
block|{
if|if
condition|(
name|d
operator|.
name|getPreference
argument_list|()
operator|==
literal|null
condition|)
continue|continue;
name|ret
operator|+=
operator|(
name|ret
operator|.
name|isEmpty
argument_list|()
condition|?
literal|""
else|:
name|separator
operator|)
operator|+
name|d
operator|.
name|getPreference
argument_list|()
operator|.
name|getName
argument_list|()
operator|+
literal|" "
operator|+
name|dept2string
argument_list|(
name|d
argument_list|,
name|deptMode
argument_list|)
expr_stmt|;
block|}
return|return
name|ret
return|;
block|}
specifier|protected
name|String
name|examTypes2string
parameter_list|(
name|Collection
argument_list|<
name|ExamTypeInterface
argument_list|>
name|types
parameter_list|,
name|String
name|separator
parameter_list|)
block|{
if|if
condition|(
name|types
operator|==
literal|null
operator|||
name|types
operator|.
name|isEmpty
argument_list|()
condition|)
return|return
literal|""
return|;
name|String
name|ret
init|=
literal|""
decl_stmt|;
for|for
control|(
name|ExamTypeInterface
name|t
range|:
name|types
control|)
block|{
name|ret
operator|+=
operator|(
name|ret
operator|.
name|isEmpty
argument_list|()
condition|?
literal|""
else|:
name|separator
operator|)
operator|+
name|t
operator|.
name|getLabel
argument_list|()
expr_stmt|;
block|}
return|return
name|ret
return|;
block|}
specifier|protected
name|String
name|features2string
parameter_list|(
name|Collection
argument_list|<
name|FeatureInterface
argument_list|>
name|features
parameter_list|,
name|String
name|separator
parameter_list|)
block|{
if|if
condition|(
name|features
operator|==
literal|null
operator|||
name|features
operator|.
name|isEmpty
argument_list|()
condition|)
return|return
literal|""
return|;
name|String
name|ret
init|=
literal|""
decl_stmt|;
for|for
control|(
name|FeatureInterface
name|f
range|:
name|features
control|)
block|{
if|if
condition|(
name|f
operator|.
name|getType
argument_list|()
operator|!=
literal|null
condition|)
name|ret
operator|+=
operator|(
name|ret
operator|.
name|isEmpty
argument_list|()
condition|?
literal|""
else|:
name|separator
operator|)
operator|+
name|f
operator|.
name|getLabel
argument_list|()
operator|+
literal|" ("
operator|+
name|f
operator|.
name|getType
argument_list|()
operator|.
name|getAbbreviation
argument_list|()
operator|+
literal|")"
expr_stmt|;
if|else if
condition|(
name|f
operator|.
name|getDepartment
argument_list|()
operator|!=
literal|null
condition|)
name|ret
operator|+=
operator|(
name|ret
operator|.
name|isEmpty
argument_list|()
condition|?
literal|""
else|:
name|separator
operator|)
operator|+
name|f
operator|.
name|getLabel
argument_list|()
operator|+
literal|" ("
operator|+
name|f
operator|.
name|getDepartment
argument_list|()
operator|.
name|getExtAbbreviationWhenExist
argument_list|()
operator|+
literal|")"
expr_stmt|;
else|else
name|ret
operator|+=
operator|(
name|ret
operator|.
name|isEmpty
argument_list|()
condition|?
literal|""
else|:
name|separator
operator|)
operator|+
name|f
operator|.
name|getLabel
argument_list|()
expr_stmt|;
block|}
return|return
name|ret
return|;
block|}
specifier|protected
name|String
name|groups2string
parameter_list|(
name|Collection
argument_list|<
name|GroupInterface
argument_list|>
name|groups
parameter_list|,
name|String
name|separator
parameter_list|)
block|{
if|if
condition|(
name|groups
operator|==
literal|null
operator|||
name|groups
operator|.
name|isEmpty
argument_list|()
condition|)
return|return
literal|""
return|;
name|String
name|ret
init|=
literal|""
decl_stmt|;
for|for
control|(
name|GroupInterface
name|g
range|:
name|groups
control|)
block|{
name|ret
operator|+=
operator|(
name|ret
operator|.
name|isEmpty
argument_list|()
condition|?
literal|""
else|:
name|separator
operator|)
operator|+
name|g
operator|.
name|getLabel
argument_list|()
operator|+
operator|(
name|g
operator|.
name|getDepartment
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
literal|" ("
operator|+
name|g
operator|.
name|getDepartment
argument_list|()
operator|.
name|getExtAbbreviationWhenExist
argument_list|()
operator|+
literal|")"
operator|)
expr_stmt|;
block|}
return|return
name|ret
return|;
block|}
block|}
end_class

end_unit

