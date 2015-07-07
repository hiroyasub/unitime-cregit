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
name|rooms
package|;
end_package

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
name|HashMap
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
name|TreeSet
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
name|gwt
operator|.
name|command
operator|.
name|client
operator|.
name|GwtRpcException
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
name|command
operator|.
name|server
operator|.
name|GwtRpcImplementation
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
name|command
operator|.
name|server
operator|.
name|GwtRpcImplements
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
name|server
operator|.
name|UploadServlet
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
name|RoomPictureInterface
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
name|RoomPictureRequest
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
name|RoomPictureRequest
operator|.
name|Apply
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
name|RoomPictureResponse
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
name|LocationPicture
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
name|NonUniversityLocation
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
name|NonUniversityLocationPicture
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
name|RoomPicture
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

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
annotation|@
name|GwtRpcImplements
argument_list|(
name|RoomPictureRequest
operator|.
name|class
argument_list|)
specifier|public
class|class
name|RoomPicturesBackend
implements|implements
name|GwtRpcImplementation
argument_list|<
name|RoomPictureRequest
argument_list|,
name|RoomPictureResponse
argument_list|>
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
name|Override
specifier|public
name|RoomPictureResponse
name|execute
parameter_list|(
name|RoomPictureRequest
name|request
parameter_list|,
name|SessionContext
name|context
parameter_list|)
block|{
name|RoomPictureResponse
name|response
init|=
operator|new
name|RoomPictureResponse
argument_list|()
decl_stmt|;
name|Map
argument_list|<
name|Long
argument_list|,
name|LocationPicture
argument_list|>
name|temp
init|=
operator|(
name|Map
argument_list|<
name|Long
argument_list|,
name|LocationPicture
argument_list|>
operator|)
name|context
operator|.
name|getAttribute
argument_list|(
name|RoomPictureServlet
operator|.
name|TEMP_ROOM_PICTURES
argument_list|)
decl_stmt|;
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
init|=
name|LocationDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|getSession
argument_list|()
decl_stmt|;
if|if
condition|(
name|request
operator|.
name|getOperation
argument_list|()
operator|==
name|RoomPictureRequest
operator|.
name|Operation
operator|.
name|UPLOAD
operator|&&
name|request
operator|.
name|getLocationId
argument_list|()
operator|==
literal|null
condition|)
block|{
specifier|final
name|FileItem
name|file
init|=
operator|(
name|FileItem
operator|)
name|context
operator|.
name|getAttribute
argument_list|(
name|UploadServlet
operator|.
name|SESSION_LAST_FILE
argument_list|)
decl_stmt|;
if|if
condition|(
name|file
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
name|temp
operator|==
literal|null
condition|)
block|{
name|temp
operator|=
operator|new
name|HashMap
argument_list|<
name|Long
argument_list|,
name|LocationPicture
argument_list|>
argument_list|()
expr_stmt|;
name|context
operator|.
name|setAttribute
argument_list|(
name|RoomPictureServlet
operator|.
name|TEMP_ROOM_PICTURES
argument_list|,
name|temp
argument_list|)
expr_stmt|;
block|}
name|LocationPicture
name|picture
init|=
operator|new
name|RoomPicture
argument_list|()
decl_stmt|;
name|picture
operator|.
name|setDataFile
argument_list|(
name|file
operator|.
name|get
argument_list|()
argument_list|)
expr_stmt|;
name|String
name|name
init|=
name|file
operator|.
name|getName
argument_list|()
decl_stmt|;
if|if
condition|(
name|name
operator|.
name|indexOf
argument_list|(
literal|'.'
argument_list|)
operator|>=
literal|0
condition|)
name|name
operator|=
name|name
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|name
operator|.
name|lastIndexOf
argument_list|(
literal|'.'
argument_list|)
argument_list|)
expr_stmt|;
name|picture
operator|.
name|setFileName
argument_list|(
name|name
argument_list|)
expr_stmt|;
name|picture
operator|.
name|setContentType
argument_list|(
name|file
operator|.
name|getContentType
argument_list|()
argument_list|)
expr_stmt|;
name|picture
operator|.
name|setTimeStamp
argument_list|(
operator|new
name|Date
argument_list|()
argument_list|)
expr_stmt|;
name|temp
operator|.
name|put
argument_list|(
operator|-
name|picture
operator|.
name|getTimeStamp
argument_list|()
operator|.
name|getTime
argument_list|()
argument_list|,
name|picture
argument_list|)
expr_stmt|;
name|response
operator|.
name|addPicture
argument_list|(
operator|new
name|RoomPictureInterface
argument_list|(
operator|-
name|picture
operator|.
name|getTimeStamp
argument_list|()
operator|.
name|getTime
argument_list|()
argument_list|,
name|picture
operator|.
name|getFileName
argument_list|()
argument_list|,
name|picture
operator|.
name|getContentType
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
return|return
name|response
return|;
block|}
name|Location
name|location
init|=
name|LocationDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|get
argument_list|(
name|request
operator|.
name|getLocationId
argument_list|()
argument_list|,
name|hibSession
argument_list|)
decl_stmt|;
if|if
condition|(
name|location
operator|==
literal|null
condition|)
throw|throw
operator|new
name|GwtRpcException
argument_list|(
name|MESSAGES
operator|.
name|errorRoomDoesNotExist
argument_list|(
name|request
operator|.
name|getLocationId
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
argument_list|)
throw|;
name|response
operator|.
name|setName
argument_list|(
name|location
operator|.
name|getLabel
argument_list|()
argument_list|)
expr_stmt|;
name|context
operator|.
name|checkPermission
argument_list|(
name|location
argument_list|,
name|Right
operator|.
name|RoomEditChangePicture
argument_list|)
expr_stmt|;
switch|switch
condition|(
name|request
operator|.
name|getOperation
argument_list|()
condition|)
block|{
case|case
name|LOAD
case|:
for|for
control|(
name|LocationPicture
name|p
range|:
operator|new
name|TreeSet
argument_list|<
name|LocationPicture
argument_list|>
argument_list|(
name|location
operator|.
name|getPictures
argument_list|()
argument_list|)
control|)
name|response
operator|.
name|addPicture
argument_list|(
operator|new
name|RoomPictureInterface
argument_list|(
name|p
operator|.
name|getUniqueId
argument_list|()
argument_list|,
name|p
operator|.
name|getFileName
argument_list|()
argument_list|,
name|p
operator|.
name|getContentType
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|boolean
name|samePast
init|=
literal|true
decl_stmt|,
name|sameFuture
init|=
literal|true
decl_stmt|;
for|for
control|(
name|Location
name|other
range|:
operator|(
name|List
argument_list|<
name|Location
argument_list|>
operator|)
name|hibSession
operator|.
name|createQuery
argument_list|(
literal|"from Location loc where permanentId = :permanentId and not uniqueId = :uniqueId"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"uniqueId"
argument_list|,
name|location
operator|.
name|getUniqueId
argument_list|()
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"permanentId"
argument_list|,
name|location
operator|.
name|getPermanentId
argument_list|()
argument_list|)
operator|.
name|list
argument_list|()
control|)
block|{
if|if
condition|(
operator|!
name|samePictures
argument_list|(
name|location
argument_list|,
name|other
argument_list|)
condition|)
block|{
if|if
condition|(
name|other
operator|.
name|getSession
argument_list|()
operator|.
name|getSessionBeginDateTime
argument_list|()
operator|.
name|before
argument_list|(
name|location
operator|.
name|getSession
argument_list|()
operator|.
name|getSessionBeginDateTime
argument_list|()
argument_list|)
condition|)
block|{
name|samePast
operator|=
literal|false
expr_stmt|;
block|}
else|else
block|{
name|sameFuture
operator|=
literal|false
expr_stmt|;
block|}
block|}
if|if
condition|(
operator|!
name|sameFuture
condition|)
break|break;
block|}
if|if
condition|(
name|samePast
operator|&&
name|sameFuture
condition|)
name|response
operator|.
name|setApply
argument_list|(
name|Apply
operator|.
name|ALL_SESSIONS
argument_list|)
expr_stmt|;
if|else if
condition|(
name|sameFuture
condition|)
name|response
operator|.
name|setApply
argument_list|(
name|Apply
operator|.
name|ALL_FUTURE_SESSIONS
argument_list|)
expr_stmt|;
else|else
name|response
operator|.
name|setApply
argument_list|(
name|Apply
operator|.
name|THIS_SESSION_ONLY
argument_list|)
expr_stmt|;
name|context
operator|.
name|setAttribute
argument_list|(
name|RoomPictureServlet
operator|.
name|TEMP_ROOM_PICTURES
argument_list|,
literal|null
argument_list|)
expr_stmt|;
break|break;
case|case
name|SAVE
case|:
name|Map
argument_list|<
name|Long
argument_list|,
name|LocationPicture
argument_list|>
name|pictures
init|=
operator|new
name|HashMap
argument_list|<
name|Long
argument_list|,
name|LocationPicture
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|LocationPicture
name|p
range|:
name|location
operator|.
name|getPictures
argument_list|()
control|)
name|pictures
operator|.
name|put
argument_list|(
name|p
operator|.
name|getUniqueId
argument_list|()
argument_list|,
name|p
argument_list|)
expr_stmt|;
for|for
control|(
name|RoomPictureInterface
name|p
range|:
name|request
operator|.
name|getPictures
argument_list|()
control|)
block|{
name|LocationPicture
name|picture
init|=
name|pictures
operator|.
name|remove
argument_list|(
name|p
operator|.
name|getUniqueId
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|picture
operator|==
literal|null
operator|&&
name|temp
operator|!=
literal|null
condition|)
block|{
name|picture
operator|=
name|temp
operator|.
name|get
argument_list|(
name|p
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|picture
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
name|location
operator|instanceof
name|Room
condition|)
block|{
operator|(
operator|(
name|RoomPicture
operator|)
name|picture
operator|)
operator|.
name|setLocation
argument_list|(
operator|(
name|Room
operator|)
name|location
argument_list|)
expr_stmt|;
operator|(
operator|(
name|Room
operator|)
name|location
operator|)
operator|.
name|getPictures
argument_list|()
operator|.
name|add
argument_list|(
operator|(
name|RoomPicture
operator|)
name|picture
argument_list|)
expr_stmt|;
block|}
else|else
block|{
operator|(
operator|(
name|NonUniversityLocationPicture
operator|)
name|picture
operator|)
operator|.
name|setLocation
argument_list|(
operator|(
name|NonUniversityLocation
operator|)
name|location
argument_list|)
expr_stmt|;
operator|(
operator|(
name|NonUniversityLocation
operator|)
name|location
operator|)
operator|.
name|getPictures
argument_list|()
operator|.
name|add
argument_list|(
operator|(
name|NonUniversityLocationPicture
operator|)
name|picture
argument_list|)
expr_stmt|;
block|}
name|hibSession
operator|.
name|saveOrUpdate
argument_list|(
name|picture
argument_list|)
expr_stmt|;
block|}
block|}
block|}
for|for
control|(
name|LocationPicture
name|picture
range|:
name|pictures
operator|.
name|values
argument_list|()
control|)
block|{
name|location
operator|.
name|getPictures
argument_list|()
operator|.
name|remove
argument_list|(
name|picture
argument_list|)
expr_stmt|;
name|hibSession
operator|.
name|delete
argument_list|(
name|picture
argument_list|)
expr_stmt|;
block|}
name|hibSession
operator|.
name|saveOrUpdate
argument_list|(
name|location
argument_list|)
expr_stmt|;
if|if
condition|(
name|request
operator|.
name|getApply
argument_list|()
operator|!=
name|Apply
operator|.
name|THIS_SESSION_ONLY
condition|)
block|{
for|for
control|(
name|Location
name|other
range|:
operator|(
name|List
argument_list|<
name|Location
argument_list|>
operator|)
name|hibSession
operator|.
name|createQuery
argument_list|(
literal|"from Location loc where permanentId = :permanentId and not uniqueId = :uniqueId"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"uniqueId"
argument_list|,
name|location
operator|.
name|getUniqueId
argument_list|()
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"permanentId"
argument_list|,
name|location
operator|.
name|getPermanentId
argument_list|()
argument_list|)
operator|.
name|list
argument_list|()
control|)
block|{
if|if
condition|(
name|request
operator|.
name|getApply
argument_list|()
operator|==
name|Apply
operator|.
name|ALL_FUTURE_SESSIONS
operator|&&
name|other
operator|.
name|getSession
argument_list|()
operator|.
name|getSessionBeginDateTime
argument_list|()
operator|.
name|before
argument_list|(
name|location
operator|.
name|getSession
argument_list|()
operator|.
name|getSessionBeginDateTime
argument_list|()
argument_list|)
condition|)
continue|continue;
name|Set
argument_list|<
name|LocationPicture
argument_list|>
name|otherPictures
init|=
operator|new
name|HashSet
argument_list|<
name|LocationPicture
argument_list|>
argument_list|(
name|other
operator|.
name|getPictures
argument_list|()
argument_list|)
decl_stmt|;
name|p1
label|:
for|for
control|(
name|LocationPicture
name|p1
range|:
name|location
operator|.
name|getPictures
argument_list|()
control|)
block|{
for|for
control|(
name|Iterator
argument_list|<
name|LocationPicture
argument_list|>
name|i
init|=
name|otherPictures
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
name|LocationPicture
name|p2
init|=
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|samePicture
argument_list|(
name|p1
argument_list|,
name|p2
argument_list|)
condition|)
block|{
name|i
operator|.
name|remove
argument_list|()
expr_stmt|;
continue|continue
name|p1
continue|;
block|}
block|}
if|if
condition|(
name|location
operator|instanceof
name|Room
condition|)
block|{
name|RoomPicture
name|p2
init|=
operator|(
operator|(
name|RoomPicture
operator|)
name|p1
operator|)
operator|.
name|clonePicture
argument_list|()
decl_stmt|;
name|p2
operator|.
name|setLocation
argument_list|(
name|other
argument_list|)
expr_stmt|;
operator|(
operator|(
name|Room
operator|)
name|other
operator|)
operator|.
name|getPictures
argument_list|()
operator|.
name|add
argument_list|(
name|p2
argument_list|)
expr_stmt|;
name|hibSession
operator|.
name|saveOrUpdate
argument_list|(
name|p2
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|NonUniversityLocationPicture
name|p2
init|=
operator|(
operator|(
name|NonUniversityLocationPicture
operator|)
name|p1
operator|)
operator|.
name|clonePicture
argument_list|()
decl_stmt|;
name|p2
operator|.
name|setLocation
argument_list|(
name|other
argument_list|)
expr_stmt|;
operator|(
operator|(
name|NonUniversityLocation
operator|)
name|other
operator|)
operator|.
name|getPictures
argument_list|()
operator|.
name|add
argument_list|(
name|p2
argument_list|)
expr_stmt|;
name|hibSession
operator|.
name|saveOrUpdate
argument_list|(
name|p2
argument_list|)
expr_stmt|;
block|}
block|}
for|for
control|(
name|LocationPicture
name|picture
range|:
name|otherPictures
control|)
block|{
name|other
operator|.
name|getPictures
argument_list|()
operator|.
name|remove
argument_list|(
name|picture
argument_list|)
expr_stmt|;
name|hibSession
operator|.
name|delete
argument_list|(
name|picture
argument_list|)
expr_stmt|;
block|}
name|hibSession
operator|.
name|saveOrUpdate
argument_list|(
name|other
argument_list|)
expr_stmt|;
block|}
block|}
name|hibSession
operator|.
name|flush
argument_list|()
expr_stmt|;
name|context
operator|.
name|setAttribute
argument_list|(
name|RoomPictureServlet
operator|.
name|TEMP_ROOM_PICTURES
argument_list|,
literal|null
argument_list|)
expr_stmt|;
break|break;
case|case
name|UPLOAD
case|:
specifier|final
name|FileItem
name|file
init|=
operator|(
name|FileItem
operator|)
name|context
operator|.
name|getAttribute
argument_list|(
name|UploadServlet
operator|.
name|SESSION_LAST_FILE
argument_list|)
decl_stmt|;
if|if
condition|(
name|file
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
name|temp
operator|==
literal|null
condition|)
block|{
name|temp
operator|=
operator|new
name|HashMap
argument_list|<
name|Long
argument_list|,
name|LocationPicture
argument_list|>
argument_list|()
expr_stmt|;
name|context
operator|.
name|setAttribute
argument_list|(
name|RoomPictureServlet
operator|.
name|TEMP_ROOM_PICTURES
argument_list|,
name|temp
argument_list|)
expr_stmt|;
block|}
name|LocationPicture
name|picture
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|location
operator|instanceof
name|Room
condition|)
name|picture
operator|=
operator|new
name|RoomPicture
argument_list|()
expr_stmt|;
else|else
name|picture
operator|=
operator|new
name|NonUniversityLocationPicture
argument_list|()
expr_stmt|;
name|picture
operator|.
name|setDataFile
argument_list|(
name|file
operator|.
name|get
argument_list|()
argument_list|)
expr_stmt|;
name|String
name|name
init|=
name|file
operator|.
name|getName
argument_list|()
decl_stmt|;
if|if
condition|(
name|name
operator|.
name|indexOf
argument_list|(
literal|'.'
argument_list|)
operator|>=
literal|0
condition|)
name|name
operator|=
name|name
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|name
operator|.
name|lastIndexOf
argument_list|(
literal|'.'
argument_list|)
argument_list|)
expr_stmt|;
name|picture
operator|.
name|setFileName
argument_list|(
name|name
argument_list|)
expr_stmt|;
name|picture
operator|.
name|setContentType
argument_list|(
name|file
operator|.
name|getContentType
argument_list|()
argument_list|)
expr_stmt|;
name|picture
operator|.
name|setTimeStamp
argument_list|(
operator|new
name|Date
argument_list|()
argument_list|)
expr_stmt|;
name|temp
operator|.
name|put
argument_list|(
operator|-
name|picture
operator|.
name|getTimeStamp
argument_list|()
operator|.
name|getTime
argument_list|()
argument_list|,
name|picture
argument_list|)
expr_stmt|;
name|response
operator|.
name|addPicture
argument_list|(
operator|new
name|RoomPictureInterface
argument_list|(
operator|-
name|picture
operator|.
name|getTimeStamp
argument_list|()
operator|.
name|getTime
argument_list|()
argument_list|,
name|picture
operator|.
name|getFileName
argument_list|()
argument_list|,
name|picture
operator|.
name|getContentType
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
break|break;
block|}
return|return
name|response
return|;
block|}
specifier|private
name|boolean
name|samePictures
parameter_list|(
name|Location
name|l1
parameter_list|,
name|Location
name|l2
parameter_list|)
block|{
if|if
condition|(
name|l1
operator|.
name|getPictures
argument_list|()
operator|.
name|size
argument_list|()
operator|!=
name|l2
operator|.
name|getPictures
argument_list|()
operator|.
name|size
argument_list|()
condition|)
return|return
literal|false
return|;
name|p1
label|:
for|for
control|(
name|LocationPicture
name|p1
range|:
name|l1
operator|.
name|getPictures
argument_list|()
control|)
block|{
for|for
control|(
name|LocationPicture
name|p2
range|:
name|l2
operator|.
name|getPictures
argument_list|()
control|)
block|{
if|if
condition|(
name|samePicture
argument_list|(
name|p1
argument_list|,
name|p2
argument_list|)
condition|)
continue|continue
name|p1
continue|;
block|}
return|return
literal|false
return|;
block|}
return|return
literal|true
return|;
block|}
specifier|private
name|boolean
name|samePicture
parameter_list|(
name|LocationPicture
name|p1
parameter_list|,
name|LocationPicture
name|p2
parameter_list|)
block|{
return|return
name|p1
operator|.
name|getFileName
argument_list|()
operator|.
name|equals
argument_list|(
name|p2
operator|.
name|getFileName
argument_list|()
argument_list|)
operator|&&
name|Math
operator|.
name|abs
argument_list|(
name|p1
operator|.
name|getTimeStamp
argument_list|()
operator|.
name|getTime
argument_list|()
operator|-
name|p2
operator|.
name|getTimeStamp
argument_list|()
operator|.
name|getTime
argument_list|()
argument_list|)
operator|<
literal|1000
operator|&&
name|p1
operator|.
name|getContentType
argument_list|()
operator|.
name|equals
argument_list|(
name|p2
operator|.
name|getContentType
argument_list|()
argument_list|)
return|;
block|}
block|}
end_class

end_unit

