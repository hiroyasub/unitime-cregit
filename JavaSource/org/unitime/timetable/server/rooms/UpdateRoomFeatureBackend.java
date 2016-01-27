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
name|FeatureTypeInterface
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
name|UpdateRoomFeatureRequest
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
name|ChangeLog
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
name|DepartmentRoomFeature
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
name|GlobalRoomFeature
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
name|RoomFeature
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
name|RoomFeaturePref
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
name|RoomDeptDAO
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
name|RoomFeatureDAO
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
name|RoomFeatureTypeDAO
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
name|UpdateRoomFeatureRequest
operator|.
name|class
argument_list|)
specifier|public
class|class
name|UpdateRoomFeatureBackend
implements|implements
name|GwtRpcImplementation
argument_list|<
name|UpdateRoomFeatureRequest
argument_list|,
name|FeatureInterface
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
name|FeatureInterface
name|execute
parameter_list|(
name|UpdateRoomFeatureRequest
name|request
parameter_list|,
name|SessionContext
name|context
parameter_list|)
block|{
if|if
condition|(
name|request
operator|.
name|hasSessionId
argument_list|()
condition|)
name|context
operator|=
operator|new
name|EventContext
argument_list|(
name|context
argument_list|,
name|request
operator|.
name|getSessionId
argument_list|()
argument_list|)
expr_stmt|;
name|Transaction
name|tx
init|=
literal|null
decl_stmt|;
name|RoomFeature
name|f
init|=
literal|null
decl_stmt|;
try|try
block|{
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
init|=
operator|new
name|RoomDeptDAO
argument_list|()
operator|.
name|getSession
argument_list|()
decl_stmt|;
name|tx
operator|=
name|hibSession
operator|.
name|beginTransaction
argument_list|()
expr_stmt|;
if|if
condition|(
name|request
operator|.
name|hasFeature
argument_list|()
condition|)
block|{
if|if
condition|(
name|request
operator|.
name|hasFutureSessions
argument_list|()
condition|)
for|for
control|(
name|Long
name|id
range|:
name|request
operator|.
name|getFutureSessions
argument_list|()
control|)
name|createOrUpdateFeature
argument_list|(
name|request
operator|.
name|getFeature
argument_list|()
argument_list|,
name|request
operator|.
name|getAddLocations
argument_list|()
argument_list|,
name|request
operator|.
name|getDropLocations
argument_list|()
argument_list|,
name|id
argument_list|,
name|hibSession
argument_list|,
operator|new
name|EventContext
argument_list|(
name|context
argument_list|,
name|context
operator|.
name|getUser
argument_list|()
argument_list|,
name|id
argument_list|)
argument_list|,
literal|true
argument_list|)
expr_stmt|;
name|f
operator|=
name|createOrUpdateFeature
argument_list|(
name|request
operator|.
name|getFeature
argument_list|()
argument_list|,
name|request
operator|.
name|getAddLocations
argument_list|()
argument_list|,
name|request
operator|.
name|getDropLocations
argument_list|()
argument_list|,
name|context
operator|.
name|getUser
argument_list|()
operator|.
name|getCurrentAcademicSessionId
argument_list|()
argument_list|,
name|hibSession
argument_list|,
name|context
argument_list|,
literal|false
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|request
operator|.
name|getDeleteFeatureId
argument_list|()
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
name|request
operator|.
name|hasFutureSessions
argument_list|()
condition|)
for|for
control|(
name|Long
name|id
range|:
name|request
operator|.
name|getFutureSessions
argument_list|()
control|)
name|dropFeature
argument_list|(
name|request
operator|.
name|getDeleteFeatureId
argument_list|()
argument_list|,
name|id
argument_list|,
name|hibSession
argument_list|,
operator|new
name|EventContext
argument_list|(
name|context
argument_list|,
name|context
operator|.
name|getUser
argument_list|()
argument_list|,
name|id
argument_list|)
argument_list|,
literal|true
argument_list|)
expr_stmt|;
name|dropFeature
argument_list|(
name|request
operator|.
name|getDeleteFeatureId
argument_list|()
argument_list|,
name|context
operator|.
name|getUser
argument_list|()
operator|.
name|getCurrentAcademicSessionId
argument_list|()
argument_list|,
name|hibSession
argument_list|,
name|context
argument_list|,
literal|false
argument_list|)
expr_stmt|;
block|}
else|else
block|{
throw|throw
operator|new
name|GwtRpcException
argument_list|(
literal|"Bad request."
argument_list|)
throw|;
block|}
name|FeatureInterface
name|feature
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|f
operator|!=
literal|null
condition|)
block|{
name|feature
operator|=
operator|new
name|FeatureInterface
argument_list|(
name|f
operator|.
name|getUniqueId
argument_list|()
argument_list|,
name|f
operator|.
name|getAbbv
argument_list|()
argument_list|,
name|f
operator|.
name|getLabel
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|f
operator|.
name|getFeatureType
argument_list|()
operator|!=
literal|null
condition|)
name|feature
operator|.
name|setType
argument_list|(
operator|new
name|FeatureTypeInterface
argument_list|(
name|f
operator|.
name|getFeatureType
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|,
name|f
operator|.
name|getFeatureType
argument_list|()
operator|.
name|getReference
argument_list|()
argument_list|,
name|f
operator|.
name|getFeatureType
argument_list|()
operator|.
name|getLabel
argument_list|()
argument_list|,
name|f
operator|.
name|getFeatureType
argument_list|()
operator|.
name|isShowInEventManagement
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|f
operator|instanceof
name|DepartmentRoomFeature
condition|)
block|{
name|Department
name|d
init|=
operator|(
operator|(
name|DepartmentRoomFeature
operator|)
name|f
operator|)
operator|.
name|getDepartment
argument_list|()
decl_stmt|;
name|feature
operator|.
name|setDepartment
argument_list|(
name|RoomDetailsBackend
operator|.
name|wrap
argument_list|(
name|d
argument_list|,
literal|null
argument_list|,
literal|null
argument_list|)
argument_list|)
expr_stmt|;
name|feature
operator|.
name|setTitle
argument_list|(
name|f
operator|.
name|getLabel
argument_list|()
operator|+
literal|" ("
operator|+
name|d
operator|.
name|getName
argument_list|()
operator|+
operator|(
name|f
operator|.
name|getFeatureType
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
literal|", "
operator|+
name|f
operator|.
name|getFeatureType
argument_list|()
operator|.
name|getLabel
argument_list|()
operator|)
operator|+
literal|")"
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|feature
operator|.
name|setTitle
argument_list|(
name|f
operator|.
name|getLabel
argument_list|()
operator|+
operator|(
name|f
operator|.
name|getFeatureType
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
literal|" ("
operator|+
name|f
operator|.
name|getFeatureType
argument_list|()
operator|.
name|getLabel
argument_list|()
operator|+
literal|")"
operator|)
argument_list|)
expr_stmt|;
block|}
block|}
name|tx
operator|.
name|commit
argument_list|()
expr_stmt|;
return|return
name|feature
return|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|e
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
if|if
condition|(
name|tx
operator|!=
literal|null
condition|)
name|tx
operator|.
name|rollback
argument_list|()
expr_stmt|;
if|if
condition|(
name|e
operator|instanceof
name|GwtRpcException
condition|)
throw|throw
operator|(
name|GwtRpcException
operator|)
name|e
throw|;
throw|throw
operator|new
name|GwtRpcException
argument_list|(
name|e
operator|.
name|getMessage
argument_list|()
argument_list|)
throw|;
block|}
block|}
specifier|protected
name|Department
name|lookuDepartment
parameter_list|(
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
parameter_list|,
name|DepartmentInterface
name|original
parameter_list|,
name|boolean
name|future
parameter_list|,
name|Long
name|sessionId
parameter_list|)
block|{
if|if
condition|(
name|original
operator|==
literal|null
condition|)
return|return
literal|null
return|;
if|if
condition|(
name|future
condition|)
block|{
return|return
name|Department
operator|.
name|findByDeptCode
argument_list|(
name|original
operator|.
name|getDeptCode
argument_list|()
argument_list|,
name|sessionId
argument_list|,
name|hibSession
argument_list|)
return|;
block|}
else|else
block|{
return|return
name|DepartmentDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|get
argument_list|(
name|original
operator|.
name|getId
argument_list|()
argument_list|,
name|hibSession
argument_list|)
return|;
block|}
block|}
specifier|protected
name|RoomFeature
name|lookupFeature
parameter_list|(
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
parameter_list|,
name|FeatureInterface
name|original
parameter_list|,
name|boolean
name|future
parameter_list|,
name|Long
name|sessionId
parameter_list|)
block|{
if|if
condition|(
name|original
operator|==
literal|null
condition|)
return|return
literal|null
return|;
if|if
condition|(
name|future
condition|)
block|{
if|if
condition|(
name|original
operator|.
name|isDepartmental
argument_list|()
condition|)
return|return
operator|(
name|DepartmentRoomFeature
operator|)
name|hibSession
operator|.
name|createQuery
argument_list|(
literal|"select f from DepartmentRoomFeature f, DepartmentRoomFeature o where o.uniqueId = :originalId and f.department.session.uniqueId = :sessionId "
operator|+
literal|"and f.abbv = o.abbv and f.department.deptCode = o.department.deptCode"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"sessionId"
argument_list|,
name|sessionId
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"originalId"
argument_list|,
name|original
operator|.
name|getId
argument_list|()
argument_list|)
operator|.
name|setCacheable
argument_list|(
literal|true
argument_list|)
operator|.
name|setMaxResults
argument_list|(
literal|1
argument_list|)
operator|.
name|uniqueResult
argument_list|()
return|;
else|else
return|return
operator|(
name|GlobalRoomFeature
operator|)
name|hibSession
operator|.
name|createQuery
argument_list|(
literal|"select f from GlobalRoomFeature f, GlobalRoomFeature o where o.uniqueId = :originalId and f.session.uniqueId = :sessionId "
operator|+
literal|"and f.abbv = o.abbv"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"sessionId"
argument_list|,
name|sessionId
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"originalId"
argument_list|,
name|original
operator|.
name|getId
argument_list|()
argument_list|)
operator|.
name|setCacheable
argument_list|(
literal|true
argument_list|)
operator|.
name|setMaxResults
argument_list|(
literal|1
argument_list|)
operator|.
name|uniqueResult
argument_list|()
return|;
block|}
else|else
block|{
return|return
name|RoomFeatureDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|get
argument_list|(
name|original
operator|.
name|getId
argument_list|()
argument_list|,
name|hibSession
argument_list|)
return|;
block|}
block|}
specifier|protected
name|RoomFeature
name|lookupFeature
parameter_list|(
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
parameter_list|,
name|Long
name|featureId
parameter_list|,
name|boolean
name|future
parameter_list|,
name|Long
name|sessionId
parameter_list|)
block|{
if|if
condition|(
name|featureId
operator|==
literal|null
condition|)
return|return
literal|null
return|;
if|if
condition|(
name|future
condition|)
block|{
name|RoomFeature
name|feature
init|=
operator|(
name|DepartmentRoomFeature
operator|)
name|hibSession
operator|.
name|createQuery
argument_list|(
literal|"select f from DepartmentRoomFeature f, DepartmentRoomFeature o where o.uniqueId = :originalId and f.department.session.uniqueId = :sessionId "
operator|+
literal|"and f.abbv = o.abbv and f.department.deptCode = o.department.deptCode"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"sessionId"
argument_list|,
name|sessionId
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"originalId"
argument_list|,
name|featureId
argument_list|)
operator|.
name|setCacheable
argument_list|(
literal|true
argument_list|)
operator|.
name|setMaxResults
argument_list|(
literal|1
argument_list|)
operator|.
name|uniqueResult
argument_list|()
decl_stmt|;
if|if
condition|(
name|feature
operator|==
literal|null
condition|)
name|feature
operator|=
operator|(
name|GlobalRoomFeature
operator|)
name|hibSession
operator|.
name|createQuery
argument_list|(
literal|"select f from GlobalRoomFeature f, GlobalRoomFeature o where o.uniqueId = :originalId and f.session.uniqueId = :sessionId "
operator|+
literal|"and f.abbv = o.abbv"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"sessionId"
argument_list|,
name|sessionId
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"originalId"
argument_list|,
name|featureId
argument_list|)
operator|.
name|setCacheable
argument_list|(
literal|true
argument_list|)
operator|.
name|setMaxResults
argument_list|(
literal|1
argument_list|)
operator|.
name|uniqueResult
argument_list|()
expr_stmt|;
return|return
name|feature
return|;
block|}
else|else
block|{
return|return
name|RoomFeatureDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|get
argument_list|(
name|featureId
argument_list|,
name|hibSession
argument_list|)
return|;
block|}
block|}
specifier|protected
name|Collection
argument_list|<
name|Location
argument_list|>
name|lookupLocations
parameter_list|(
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
parameter_list|,
name|List
argument_list|<
name|Long
argument_list|>
name|ids
parameter_list|,
name|boolean
name|future
parameter_list|,
name|Long
name|sessionId
parameter_list|)
block|{
if|if
condition|(
name|ids
operator|==
literal|null
operator|||
name|ids
operator|.
name|isEmpty
argument_list|()
condition|)
return|return
operator|new
name|ArrayList
argument_list|<
name|Location
argument_list|>
argument_list|()
return|;
if|if
condition|(
name|future
condition|)
block|{
return|return
name|Location
operator|.
name|lookupFutureLocations
argument_list|(
name|hibSession
argument_list|,
name|ids
argument_list|,
name|sessionId
argument_list|)
return|;
block|}
else|else
block|{
return|return
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
literal|"from Location where uniqueId in :ids"
argument_list|)
operator|.
name|setParameterList
argument_list|(
literal|"ids"
argument_list|,
name|ids
argument_list|)
operator|.
name|list
argument_list|()
return|;
block|}
block|}
specifier|protected
name|RoomFeature
name|createOrUpdateFeature
parameter_list|(
name|FeatureInterface
name|feature
parameter_list|,
name|List
argument_list|<
name|Long
argument_list|>
name|add
parameter_list|,
name|List
argument_list|<
name|Long
argument_list|>
name|drop
parameter_list|,
name|Long
name|sessionId
parameter_list|,
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
parameter_list|,
name|SessionContext
name|context
parameter_list|,
name|boolean
name|future
parameter_list|)
block|{
name|Department
name|d
init|=
name|feature
operator|.
name|isDepartmental
argument_list|()
condition|?
name|lookuDepartment
argument_list|(
name|hibSession
argument_list|,
name|feature
operator|.
name|getDepartment
argument_list|()
argument_list|,
name|future
argument_list|,
name|sessionId
argument_list|)
else|:
literal|null
decl_stmt|;
if|if
condition|(
name|feature
operator|.
name|isDepartmental
argument_list|()
operator|&&
name|d
operator|==
literal|null
condition|)
return|return
literal|null
return|;
name|RoomFeature
name|rf
init|=
operator|(
name|feature
operator|.
name|getId
argument_list|()
operator|==
literal|null
condition|?
literal|null
else|:
name|lookupFeature
argument_list|(
name|hibSession
argument_list|,
name|feature
argument_list|,
name|future
argument_list|,
name|sessionId
argument_list|)
operator|)
decl_stmt|;
if|if
condition|(
name|rf
operator|==
literal|null
condition|)
block|{
if|if
condition|(
operator|!
name|future
operator|&&
name|feature
operator|.
name|getId
argument_list|()
operator|!=
literal|null
condition|)
throw|throw
operator|new
name|GwtRpcException
argument_list|(
name|MESSAGES
operator|.
name|errorRoomFeatureDoesNotExist
argument_list|(
name|feature
operator|.
name|getId
argument_list|()
argument_list|)
argument_list|)
throw|;
if|if
condition|(
name|d
operator|==
literal|null
condition|)
block|{
name|context
operator|.
name|checkPermission
argument_list|(
name|Right
operator|.
name|GlobalRoomFeatureAdd
argument_list|)
expr_stmt|;
name|rf
operator|=
operator|new
name|GlobalRoomFeature
argument_list|()
expr_stmt|;
operator|(
operator|(
name|GlobalRoomFeature
operator|)
name|rf
operator|)
operator|.
name|setSession
argument_list|(
name|SessionDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|get
argument_list|(
name|sessionId
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|context
operator|.
name|checkPermission
argument_list|(
name|d
argument_list|,
name|Right
operator|.
name|DepartmentRoomFeatureAdd
argument_list|)
expr_stmt|;
name|rf
operator|=
operator|new
name|DepartmentRoomFeature
argument_list|()
expr_stmt|;
operator|(
operator|(
name|DepartmentRoomFeature
operator|)
name|rf
operator|)
operator|.
name|setDepartment
argument_list|(
name|d
argument_list|)
expr_stmt|;
block|}
name|rf
operator|.
name|setRooms
argument_list|(
operator|new
name|HashSet
argument_list|<
name|Location
argument_list|>
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
if|if
condition|(
name|rf
operator|instanceof
name|GlobalRoomFeature
condition|)
block|{
name|context
operator|.
name|checkPermission
argument_list|(
name|rf
argument_list|,
name|Right
operator|.
name|GlobalRoomFeatureEdit
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|context
operator|.
name|checkPermission
argument_list|(
name|rf
argument_list|,
name|Right
operator|.
name|DepartmenalRoomFeatureEdit
argument_list|)
expr_stmt|;
operator|(
operator|(
name|DepartmentRoomFeature
operator|)
name|rf
operator|)
operator|.
name|setDepartment
argument_list|(
name|d
argument_list|)
expr_stmt|;
block|}
block|}
for|for
control|(
name|Iterator
name|i
init|=
name|RoomFeature
operator|.
name|getAllGlobalRoomFeatures
argument_list|(
name|sessionId
argument_list|)
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
name|RoomFeature
name|x
init|=
operator|(
name|RoomFeature
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
operator|(
name|x
operator|.
name|getLabel
argument_list|()
operator|.
name|equalsIgnoreCase
argument_list|(
name|feature
operator|.
name|getLabel
argument_list|()
argument_list|)
operator|||
name|x
operator|.
name|getAbbv
argument_list|()
operator|.
name|equalsIgnoreCase
argument_list|(
name|feature
operator|.
name|getAbbreviation
argument_list|()
argument_list|)
operator|)
operator|&&
operator|!
name|x
operator|.
name|getUniqueId
argument_list|()
operator|.
name|equals
argument_list|(
name|rf
operator|.
name|getUniqueId
argument_list|()
argument_list|)
condition|)
throw|throw
operator|new
name|GwtRpcException
argument_list|(
name|MESSAGES
operator|.
name|errorRoomFeatureAlreadyExists
argument_list|(
name|feature
operator|.
name|getLabel
argument_list|()
argument_list|,
name|SessionDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|get
argument_list|(
name|sessionId
argument_list|)
operator|.
name|getLabel
argument_list|()
argument_list|)
argument_list|)
throw|;
block|}
if|if
condition|(
name|rf
operator|instanceof
name|DepartmentRoomFeature
condition|)
block|{
for|for
control|(
name|Iterator
name|i
init|=
name|RoomFeature
operator|.
name|getAllDepartmentRoomFeatures
argument_list|(
name|d
argument_list|)
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
name|RoomFeature
name|x
init|=
operator|(
name|RoomFeature
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
operator|(
name|x
operator|.
name|getLabel
argument_list|()
operator|.
name|equalsIgnoreCase
argument_list|(
name|feature
operator|.
name|getLabel
argument_list|()
argument_list|)
operator|||
name|x
operator|.
name|getAbbv
argument_list|()
operator|.
name|equalsIgnoreCase
argument_list|(
name|feature
operator|.
name|getAbbreviation
argument_list|()
argument_list|)
operator|)
operator|&&
operator|!
name|x
operator|.
name|getUniqueId
argument_list|()
operator|.
name|equals
argument_list|(
name|rf
operator|.
name|getUniqueId
argument_list|()
argument_list|)
condition|)
throw|throw
operator|new
name|GwtRpcException
argument_list|(
name|MESSAGES
operator|.
name|errorRoomFeatureAlreadyExists
argument_list|(
name|feature
operator|.
name|getLabel
argument_list|()
argument_list|,
name|d
operator|.
name|getSession
argument_list|()
operator|.
name|getLabel
argument_list|()
argument_list|)
argument_list|)
throw|;
block|}
block|}
name|rf
operator|.
name|setAbbv
argument_list|(
name|feature
operator|.
name|getAbbreviation
argument_list|()
argument_list|)
expr_stmt|;
name|rf
operator|.
name|setLabel
argument_list|(
name|feature
operator|.
name|getLabel
argument_list|()
argument_list|)
expr_stmt|;
name|rf
operator|.
name|setFeatureType
argument_list|(
name|feature
operator|.
name|getType
argument_list|()
operator|==
literal|null
condition|?
literal|null
else|:
name|RoomFeatureTypeDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|get
argument_list|(
name|feature
operator|.
name|getType
argument_list|()
operator|.
name|getId
argument_list|()
argument_list|,
name|hibSession
argument_list|)
argument_list|)
expr_stmt|;
name|hibSession
operator|.
name|saveOrUpdate
argument_list|(
name|rf
argument_list|)
expr_stmt|;
if|if
condition|(
name|add
operator|!=
literal|null
operator|&&
operator|!
name|add
operator|.
name|isEmpty
argument_list|()
condition|)
for|for
control|(
name|Location
name|location
range|:
name|lookupLocations
argument_list|(
name|hibSession
argument_list|,
name|add
argument_list|,
name|future
argument_list|,
name|sessionId
argument_list|)
control|)
block|{
name|rf
operator|.
name|getRooms
argument_list|()
operator|.
name|add
argument_list|(
name|location
argument_list|)
expr_stmt|;
name|location
operator|.
name|getFeatures
argument_list|()
operator|.
name|add
argument_list|(
name|rf
argument_list|)
expr_stmt|;
name|hibSession
operator|.
name|saveOrUpdate
argument_list|(
name|location
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|drop
operator|!=
literal|null
operator|&&
operator|!
name|drop
operator|.
name|isEmpty
argument_list|()
condition|)
for|for
control|(
name|Location
name|location
range|:
name|lookupLocations
argument_list|(
name|hibSession
argument_list|,
name|drop
argument_list|,
name|future
argument_list|,
name|sessionId
argument_list|)
control|)
block|{
name|rf
operator|.
name|getRooms
argument_list|()
operator|.
name|remove
argument_list|(
name|location
argument_list|)
expr_stmt|;
name|location
operator|.
name|getFeatures
argument_list|()
operator|.
name|remove
argument_list|(
name|rf
argument_list|)
expr_stmt|;
name|hibSession
operator|.
name|saveOrUpdate
argument_list|(
name|location
argument_list|)
expr_stmt|;
block|}
name|hibSession
operator|.
name|saveOrUpdate
argument_list|(
name|rf
argument_list|)
expr_stmt|;
name|ChangeLog
operator|.
name|addChange
argument_list|(
name|hibSession
argument_list|,
name|context
argument_list|,
name|rf
argument_list|,
name|ChangeLog
operator|.
name|Source
operator|.
name|ROOM_FEATURE_EDIT
argument_list|,
operator|(
name|feature
operator|.
name|getId
argument_list|()
operator|==
literal|null
condition|?
name|ChangeLog
operator|.
name|Operation
operator|.
name|CREATE
else|:
name|ChangeLog
operator|.
name|Operation
operator|.
name|UPDATE
operator|)
argument_list|,
literal|null
argument_list|,
name|rf
operator|instanceof
name|DepartmentRoomFeature
condition|?
operator|(
operator|(
name|DepartmentRoomFeature
operator|)
name|rf
operator|)
operator|.
name|getDepartment
argument_list|()
else|:
literal|null
argument_list|)
expr_stmt|;
return|return
name|rf
return|;
block|}
specifier|protected
name|boolean
name|dropFeature
parameter_list|(
name|Long
name|featureId
parameter_list|,
name|Long
name|sessionId
parameter_list|,
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
parameter_list|,
name|SessionContext
name|context
parameter_list|,
name|boolean
name|future
parameter_list|)
block|{
name|RoomFeature
name|rf
init|=
name|lookupFeature
argument_list|(
name|hibSession
argument_list|,
name|featureId
argument_list|,
name|future
argument_list|,
name|sessionId
argument_list|)
decl_stmt|;
if|if
condition|(
name|rf
operator|==
literal|null
condition|)
block|{
if|if
condition|(
operator|!
name|future
condition|)
throw|throw
operator|new
name|GwtRpcException
argument_list|(
name|MESSAGES
operator|.
name|errorRoomFeatureDoesNotExist
argument_list|(
name|featureId
argument_list|)
argument_list|)
throw|;
return|return
literal|false
return|;
block|}
if|if
condition|(
name|rf
operator|instanceof
name|GlobalRoomFeature
condition|)
name|context
operator|.
name|checkPermission
argument_list|(
name|rf
argument_list|,
name|Right
operator|.
name|GlobalRoomFeatureDelete
argument_list|)
expr_stmt|;
else|else
name|context
operator|.
name|checkPermission
argument_list|(
name|rf
argument_list|,
name|Right
operator|.
name|DepartmenalRoomFeatureDelete
argument_list|)
expr_stmt|;
name|ChangeLog
operator|.
name|addChange
argument_list|(
name|hibSession
argument_list|,
name|context
argument_list|,
name|rf
argument_list|,
name|ChangeLog
operator|.
name|Source
operator|.
name|ROOM_FEATURE_EDIT
argument_list|,
name|ChangeLog
operator|.
name|Operation
operator|.
name|DELETE
argument_list|,
literal|null
argument_list|,
name|rf
operator|instanceof
name|DepartmentRoomFeature
condition|?
operator|(
operator|(
name|DepartmentRoomFeature
operator|)
name|rf
operator|)
operator|.
name|getDepartment
argument_list|()
else|:
literal|null
argument_list|)
expr_stmt|;
for|for
control|(
name|Location
name|location
range|:
name|rf
operator|.
name|getRooms
argument_list|()
control|)
block|{
name|location
operator|.
name|getFeatures
argument_list|()
operator|.
name|remove
argument_list|(
name|rf
argument_list|)
expr_stmt|;
name|hibSession
operator|.
name|saveOrUpdate
argument_list|(
name|location
argument_list|)
expr_stmt|;
block|}
for|for
control|(
name|RoomFeaturePref
name|p
range|:
operator|(
name|List
argument_list|<
name|RoomFeaturePref
argument_list|>
operator|)
name|hibSession
operator|.
name|createQuery
argument_list|(
literal|"from RoomFeaturePref p where p.roomFeature.uniqueId = :id"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"id"
argument_list|,
name|rf
operator|.
name|getUniqueId
argument_list|()
argument_list|)
operator|.
name|list
argument_list|()
control|)
block|{
name|p
operator|.
name|getOwner
argument_list|()
operator|.
name|getPreferences
argument_list|()
operator|.
name|remove
argument_list|(
name|p
argument_list|)
expr_stmt|;
name|hibSession
operator|.
name|delete
argument_list|(
name|p
argument_list|)
expr_stmt|;
name|hibSession
operator|.
name|saveOrUpdate
argument_list|(
name|p
operator|.
name|getOwner
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|hibSession
operator|.
name|delete
argument_list|(
name|rf
argument_list|)
expr_stmt|;
return|return
literal|true
return|;
block|}
block|}
end_class

end_unit

