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
name|List
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
name|cpsolver
operator|.
name|ifs
operator|.
name|util
operator|.
name|DataProperties
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
name|DistanceMetric
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
name|gwt
operator|.
name|client
operator|.
name|rooms
operator|.
name|TravelTimes
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
name|TravelTimes
operator|.
name|TravelTimeResponse
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
name|TravelTimes
operator|.
name|TravelTimesRequest
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
name|TravelTime
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
name|TravelTimesRequest
operator|.
name|class
argument_list|)
specifier|public
class|class
name|TravelTimesBackend
implements|implements
name|GwtRpcImplementation
argument_list|<
name|TravelTimesRequest
argument_list|,
name|TravelTimeResponse
argument_list|>
block|{
specifier|protected
specifier|static
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
name|TravelTimeResponse
name|execute
parameter_list|(
name|TravelTimesRequest
name|request
parameter_list|,
name|SessionContext
name|context
parameter_list|)
block|{
name|TravelTimeResponse
name|response
init|=
operator|new
name|TravelTimeResponse
argument_list|()
decl_stmt|;
switch|switch
condition|(
name|request
operator|.
name|getCommand
argument_list|()
condition|)
block|{
case|case
name|INIT
case|:
name|context
operator|.
name|checkPermission
argument_list|(
name|Right
operator|.
name|TravelTimesLoad
argument_list|)
expr_stmt|;
return|return
operator|new
name|TravelTimeResponse
argument_list|(
name|context
operator|.
name|getUser
argument_list|()
operator|.
name|getCurrentAcademicSessionId
argument_list|()
argument_list|,
name|SessionDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|get
argument_list|(
name|context
operator|.
name|getUser
argument_list|()
operator|.
name|getCurrentAcademicSessionId
argument_list|()
argument_list|)
operator|.
name|getLabel
argument_list|()
argument_list|)
return|;
case|case
name|LOAD
case|:
name|context
operator|.
name|checkPermission
argument_list|(
name|Right
operator|.
name|TravelTimesLoad
argument_list|)
expr_stmt|;
name|load
argument_list|(
name|context
operator|.
name|getUser
argument_list|()
operator|.
name|getCurrentAcademicSessionId
argument_list|()
argument_list|,
name|request
argument_list|,
name|response
argument_list|)
expr_stmt|;
break|break;
case|case
name|SAVE
case|:
name|context
operator|.
name|checkPermission
argument_list|(
name|Right
operator|.
name|TravelTimesSave
argument_list|)
expr_stmt|;
name|save
argument_list|(
name|context
operator|.
name|getUser
argument_list|()
operator|.
name|getCurrentAcademicSessionId
argument_list|()
argument_list|,
name|request
argument_list|)
expr_stmt|;
block|}
return|return
name|response
return|;
block|}
annotation|@
name|SuppressWarnings
argument_list|(
literal|"deprecation"
argument_list|)
specifier|protected
name|void
name|load
parameter_list|(
name|Long
name|sessionId
parameter_list|,
name|TravelTimesRequest
name|request
parameter_list|,
name|TravelTimeResponse
name|response
parameter_list|)
block|{
name|DataProperties
name|config
init|=
operator|new
name|DataProperties
argument_list|()
decl_stmt|;
name|config
operator|.
name|setProperty
argument_list|(
literal|"Distances.Ellipsoid"
argument_list|,
name|ApplicationProperty
operator|.
name|DistanceEllipsoid
operator|.
name|value
argument_list|()
argument_list|)
expr_stmt|;
name|config
operator|.
name|setProperty
argument_list|(
literal|"Distances.Speed"
argument_list|,
name|ApplicationProperty
operator|.
name|EventDistanceSpeed
operator|.
name|value
argument_list|()
argument_list|)
expr_stmt|;
name|DistanceMetric
name|metric
init|=
operator|new
name|DistanceMetric
argument_list|(
name|config
argument_list|)
decl_stmt|;
name|TravelTime
operator|.
name|populateTravelTimes
argument_list|(
name|metric
argument_list|,
name|sessionId
argument_list|)
expr_stmt|;
name|String
name|ids
init|=
literal|""
decl_stmt|;
name|int
name|count
init|=
literal|0
decl_stmt|;
if|if
condition|(
name|request
operator|.
name|hasRooms
argument_list|()
condition|)
block|{
for|for
control|(
name|TravelTimes
operator|.
name|Room
name|r
range|:
name|request
operator|.
name|getRooms
argument_list|()
control|)
block|{
name|ids
operator|+=
operator|(
name|ids
operator|.
name|isEmpty
argument_list|()
condition|?
literal|""
else|:
literal|","
operator|)
operator|+
name|r
operator|.
name|getId
argument_list|()
expr_stmt|;
name|count
operator|++
expr_stmt|;
if|if
condition|(
name|count
operator|==
literal|100
condition|)
break|break;
block|}
block|}
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
name|TreeSet
argument_list|<
name|Location
argument_list|>
name|locations
init|=
operator|new
name|TreeSet
argument_list|<
name|Location
argument_list|>
argument_list|(
name|hibSession
operator|.
name|createQuery
argument_list|(
literal|"select distinct l from Location l "
operator|+
literal|"where l.session.uniqueId = :sessionId"
operator|+
operator|(
name|ids
operator|.
name|isEmpty
argument_list|()
condition|?
literal|""
else|:
literal|" and l.uniqueId in ("
operator|+
name|ids
operator|+
literal|")"
operator|)
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"sessionId"
argument_list|,
name|sessionId
argument_list|)
operator|.
name|setCacheable
argument_list|(
literal|true
argument_list|)
operator|.
name|list
argument_list|()
argument_list|)
decl_stmt|;
name|List
argument_list|<
name|TravelTime
argument_list|>
name|times
init|=
operator|(
name|List
argument_list|<
name|TravelTime
argument_list|>
operator|)
name|hibSession
operator|.
name|createQuery
argument_list|(
literal|"select t from TravelTime t "
operator|+
literal|"where t.session.uniqueId = :sessionId"
operator|+
operator|(
name|ids
operator|.
name|isEmpty
argument_list|()
condition|?
literal|""
else|:
literal|" and t.location1Id in ("
operator|+
name|ids
operator|+
literal|") and t.location1Id in ("
operator|+
name|ids
operator|+
literal|")"
operator|)
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"sessionId"
argument_list|,
name|sessionId
argument_list|)
operator|.
name|setCacheable
argument_list|(
literal|true
argument_list|)
operator|.
name|list
argument_list|()
decl_stmt|;
for|for
control|(
name|Location
name|location
range|:
name|locations
control|)
block|{
name|TravelTimes
operator|.
name|Room
name|room
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|location
operator|instanceof
name|Room
condition|)
block|{
name|Room
name|r
init|=
operator|(
name|Room
operator|)
name|location
decl_stmt|;
name|room
operator|=
operator|new
name|TravelTimes
operator|.
name|Room
argument_list|(
name|r
operator|.
name|getUniqueId
argument_list|()
argument_list|,
name|r
operator|.
name|getLabel
argument_list|()
argument_list|,
operator|new
name|TravelTimes
operator|.
name|Building
argument_list|(
name|r
operator|.
name|getBuilding
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|,
name|r
operator|.
name|getBuilding
argument_list|()
operator|.
name|getAbbreviation
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|room
operator|=
operator|new
name|TravelTimes
operator|.
name|Room
argument_list|(
name|location
operator|.
name|getUniqueId
argument_list|()
argument_list|,
name|location
operator|.
name|getLabel
argument_list|()
argument_list|)
expr_stmt|;
block|}
for|for
control|(
name|Location
name|other
range|:
name|locations
control|)
block|{
if|if
condition|(
name|location
operator|.
name|equals
argument_list|(
name|other
argument_list|)
condition|)
continue|continue;
if|if
condition|(
name|location
operator|.
name|getCoordinateX
argument_list|()
operator|!=
literal|null
operator|&&
name|location
operator|.
name|getCoordinateY
argument_list|()
operator|!=
literal|null
operator|&&
name|other
operator|.
name|getCoordinateX
argument_list|()
operator|!=
literal|null
operator|&&
name|other
operator|.
name|getCoordinateY
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|room
operator|.
name|setDistance
argument_list|(
name|other
operator|.
name|getUniqueId
argument_list|()
argument_list|,
name|metric
operator|.
name|getDistanceInMinutes
argument_list|(
name|location
operator|.
name|getCoordinateX
argument_list|()
argument_list|,
name|location
operator|.
name|getCoordinateY
argument_list|()
argument_list|,
name|other
operator|.
name|getCoordinateX
argument_list|()
argument_list|,
name|other
operator|.
name|getCoordinateY
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|room
operator|.
name|setTravelTime
argument_list|(
name|other
operator|.
name|getUniqueId
argument_list|()
argument_list|,
name|metric
operator|.
name|getTravelTimeInMinutes
argument_list|(
name|location
operator|.
name|getUniqueId
argument_list|()
argument_list|,
name|other
operator|.
name|getUniqueId
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
for|for
control|(
name|TravelTime
name|t
range|:
name|times
control|)
block|{
if|if
condition|(
name|t
operator|.
name|getLocation1Id
argument_list|()
operator|.
name|equals
argument_list|(
name|location
operator|.
name|getUniqueId
argument_list|()
argument_list|)
condition|)
name|room
operator|.
name|setTravelTime
argument_list|(
name|t
operator|.
name|getLocation2Id
argument_list|()
argument_list|,
name|t
operator|.
name|getDistance
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|t
operator|.
name|getLocation2Id
argument_list|()
operator|.
name|equals
argument_list|(
name|location
operator|.
name|getUniqueId
argument_list|()
argument_list|)
condition|)
name|room
operator|.
name|setTravelTime
argument_list|(
name|t
operator|.
name|getLocation1Id
argument_list|()
argument_list|,
name|t
operator|.
name|getDistance
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|response
operator|.
name|addRoom
argument_list|(
name|room
argument_list|)
expr_stmt|;
if|if
condition|(
name|response
operator|.
name|getRooms
argument_list|()
operator|.
name|size
argument_list|()
operator|>=
literal|100
condition|)
break|break;
block|}
block|}
specifier|protected
name|void
name|save
parameter_list|(
name|Long
name|sessionId
parameter_list|,
name|TravelTimesRequest
name|request
parameter_list|)
block|{
if|if
condition|(
operator|!
name|request
operator|.
name|hasRooms
argument_list|()
condition|)
return|return;
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
name|String
name|ids
init|=
literal|""
decl_stmt|;
for|for
control|(
name|TravelTimes
operator|.
name|Room
name|r
range|:
name|request
operator|.
name|getRooms
argument_list|()
control|)
name|ids
operator|+=
operator|(
name|ids
operator|.
name|isEmpty
argument_list|()
condition|?
literal|""
else|:
literal|","
operator|)
operator|+
name|r
operator|.
name|getId
argument_list|()
expr_stmt|;
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
name|hibSession
operator|.
name|createQuery
argument_list|(
literal|"delete from TravelTime where session.uniqueId = :sessionId"
operator|+
operator|(
name|ids
operator|.
name|isEmpty
argument_list|()
condition|?
literal|""
else|:
literal|" and location1Id in ("
operator|+
name|ids
operator|+
literal|") and location2Id in ("
operator|+
name|ids
operator|+
literal|")"
operator|)
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"sessionId"
argument_list|,
name|sessionId
argument_list|)
operator|.
name|executeUpdate
argument_list|()
expr_stmt|;
for|for
control|(
name|TravelTimes
operator|.
name|Room
name|room
range|:
name|request
operator|.
name|getRooms
argument_list|()
control|)
block|{
for|for
control|(
name|TravelTimes
operator|.
name|Room
name|other
range|:
name|request
operator|.
name|getRooms
argument_list|()
control|)
block|{
if|if
condition|(
name|room
operator|.
name|getId
argument_list|()
operator|.
name|compareTo
argument_list|(
name|other
operator|.
name|getId
argument_list|()
argument_list|)
operator|<
literal|0
condition|)
block|{
name|Integer
name|distance
init|=
name|room
operator|.
name|getTravelTime
argument_list|(
name|other
argument_list|)
decl_stmt|;
if|if
condition|(
name|distance
operator|!=
literal|null
condition|)
block|{
name|TravelTime
name|time
init|=
operator|new
name|TravelTime
argument_list|()
decl_stmt|;
name|time
operator|.
name|setSession
argument_list|(
name|session
argument_list|)
expr_stmt|;
name|time
operator|.
name|setLocation1Id
argument_list|(
name|room
operator|.
name|getId
argument_list|()
argument_list|)
expr_stmt|;
name|time
operator|.
name|setLocation2Id
argument_list|(
name|other
operator|.
name|getId
argument_list|()
argument_list|)
expr_stmt|;
name|time
operator|.
name|setDistance
argument_list|(
name|distance
argument_list|)
expr_stmt|;
name|hibSession
operator|.
name|saveOrUpdate
argument_list|(
name|time
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
name|hibSession
operator|.
name|flush
argument_list|()
expr_stmt|;
block|}
block|}
end_class

end_unit

