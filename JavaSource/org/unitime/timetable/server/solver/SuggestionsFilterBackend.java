begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
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
name|solver
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
name|cpsolver
operator|.
name|coursett
operator|.
name|model
operator|.
name|Lecture
import|;
end_import

begin_import
import|import
name|org
operator|.
name|cpsolver
operator|.
name|coursett
operator|.
name|model
operator|.
name|RoomLocation
import|;
end_import

begin_import
import|import
name|org
operator|.
name|cpsolver
operator|.
name|coursett
operator|.
name|model
operator|.
name|TimeLocation
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
name|SuggestionsInterface
operator|.
name|DayCode
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
name|SuggestionsInterface
operator|.
name|SuggestionsFilterRpcRequest
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
name|server
operator|.
name|FilterBoxBackend
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
name|SolverProxy
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
name|SolverService
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
name|Constants
import|;
end_import

begin_class
annotation|@
name|GwtRpcImplements
argument_list|(
name|SuggestionsFilterRpcRequest
operator|.
name|class
argument_list|)
specifier|public
class|class
name|SuggestionsFilterBackend
extends|extends
name|FilterBoxBackend
argument_list|<
name|SuggestionsFilterRpcRequest
argument_list|>
block|{
specifier|protected
specifier|static
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
name|Autowired
name|SolverService
argument_list|<
name|SolverProxy
argument_list|>
name|courseTimetablingSolverService
decl_stmt|;
annotation|@
name|Override
specifier|public
name|FilterRpcResponse
name|execute
parameter_list|(
name|SuggestionsFilterRpcRequest
name|request
parameter_list|,
name|SessionContext
name|context
parameter_list|)
block|{
name|context
operator|.
name|checkPermission
argument_list|(
name|Right
operator|.
name|Suggestions
argument_list|)
expr_stmt|;
return|return
name|super
operator|.
name|execute
argument_list|(
name|request
argument_list|,
name|context
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|load
parameter_list|(
name|SuggestionsFilterRpcRequest
name|request
parameter_list|,
name|FilterRpcResponse
name|response
parameter_list|,
name|SessionContext
name|context
parameter_list|)
block|{
if|if
condition|(
name|request
operator|.
name|getClassId
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|SolverProxy
name|solver
init|=
name|courseTimetablingSolverService
operator|.
name|getSolver
argument_list|()
decl_stmt|;
if|if
condition|(
name|solver
operator|!=
literal|null
condition|)
block|{
name|Map
argument_list|<
name|String
argument_list|,
name|Collection
argument_list|<
name|Entity
argument_list|>
argument_list|>
name|entities
init|=
name|solver
operator|.
name|loadSuggestionFilter
argument_list|(
name|request
operator|.
name|getClassId
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|entities
operator|!=
literal|null
condition|)
for|for
control|(
name|Map
operator|.
name|Entry
argument_list|<
name|String
argument_list|,
name|Collection
argument_list|<
name|Entity
argument_list|>
argument_list|>
name|entry
range|:
name|entities
operator|.
name|entrySet
argument_list|()
control|)
name|response
operator|.
name|add
argument_list|(
name|entry
operator|.
name|getKey
argument_list|()
argument_list|,
name|entry
operator|.
name|getValue
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
specifier|public
specifier|static
name|String
name|getDaysName
parameter_list|(
name|int
name|days
parameter_list|)
block|{
name|String
name|ret
init|=
literal|""
decl_stmt|;
for|for
control|(
name|DayCode
name|dc
range|:
name|DayCode
operator|.
name|values
argument_list|()
control|)
if|if
condition|(
operator|(
name|dc
operator|.
name|getCode
argument_list|()
operator|&
name|days
operator|)
operator|!=
literal|0
condition|)
name|ret
operator|+=
name|CONSTANTS
operator|.
name|shortDays
argument_list|()
index|[
name|dc
operator|.
name|ordinal
argument_list|()
index|]
expr_stmt|;
return|return
name|ret
return|;
block|}
specifier|public
specifier|static
name|String
name|slot2time
parameter_list|(
name|int
name|timeSinceMidnight
parameter_list|,
name|boolean
name|useAmPm
parameter_list|)
block|{
name|int
name|hour
init|=
name|timeSinceMidnight
operator|/
literal|60
decl_stmt|;
name|int
name|min
init|=
name|timeSinceMidnight
operator|%
literal|60
decl_stmt|;
if|if
condition|(
name|useAmPm
condition|)
return|return
operator|(
name|hour
operator|==
literal|0
condition|?
literal|12
else|:
name|hour
operator|>
literal|12
condition|?
name|hour
operator|-
literal|12
else|:
name|hour
operator|)
operator|+
literal|":"
operator|+
operator|(
name|min
operator|<
literal|10
condition|?
literal|"0"
else|:
literal|""
operator|)
operator|+
name|min
operator|+
operator|(
name|hour
operator|<
literal|24
operator|&&
name|hour
operator|>=
literal|12
condition|?
literal|"p"
else|:
literal|"a"
operator|)
return|;
else|else
return|return
name|hour
operator|+
literal|":"
operator|+
operator|(
name|min
operator|<
literal|10
condition|?
literal|"0"
else|:
literal|""
operator|)
operator|+
name|min
return|;
block|}
specifier|public
specifier|static
name|Map
argument_list|<
name|String
argument_list|,
name|Collection
argument_list|<
name|Entity
argument_list|>
argument_list|>
name|load
parameter_list|(
name|Lecture
name|lecture
parameter_list|)
block|{
name|Map
argument_list|<
name|String
argument_list|,
name|Collection
argument_list|<
name|Entity
argument_list|>
argument_list|>
name|entities
init|=
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|Collection
argument_list|<
name|Entity
argument_list|>
argument_list|>
argument_list|()
decl_stmt|;
if|if
condition|(
name|lecture
operator|.
name|getNrRooms
argument_list|()
operator|>
literal|0
operator|&&
name|lecture
operator|.
name|roomLocations
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|List
argument_list|<
name|Entity
argument_list|>
name|rooms
init|=
operator|new
name|ArrayList
argument_list|<
name|Entity
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|RoomLocation
name|r
range|:
name|lecture
operator|.
name|roomLocations
argument_list|()
control|)
block|{
if|if
condition|(
name|r
operator|.
name|getPreference
argument_list|()
operator|>
literal|500
condition|)
continue|continue;
name|Entity
name|e
init|=
operator|new
name|Entity
argument_list|(
name|r
operator|.
name|getId
argument_list|()
argument_list|,
name|r
operator|.
name|getName
argument_list|()
argument_list|,
name|r
operator|.
name|getName
argument_list|()
argument_list|)
decl_stmt|;
name|e
operator|.
name|setCount
argument_list|(
name|r
operator|.
name|getRoomSize
argument_list|()
argument_list|)
expr_stmt|;
name|rooms
operator|.
name|add
argument_list|(
name|e
argument_list|)
expr_stmt|;
block|}
name|entities
operator|.
name|put
argument_list|(
literal|"room"
argument_list|,
name|rooms
argument_list|)
expr_stmt|;
block|}
name|int
name|allDays
init|=
literal|0
decl_stmt|;
if|if
condition|(
name|lecture
operator|.
name|timeLocations
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|Set
argument_list|<
name|Entity
argument_list|>
name|times
init|=
operator|new
name|TreeSet
argument_list|<
name|Entity
argument_list|>
argument_list|()
decl_stmt|;
name|Set
argument_list|<
name|Entity
argument_list|>
name|dates
init|=
operator|new
name|TreeSet
argument_list|<
name|Entity
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|TimeLocation
name|t
range|:
name|lecture
operator|.
name|timeLocations
argument_list|()
control|)
block|{
if|if
condition|(
name|t
operator|.
name|getPreference
argument_list|()
operator|>
literal|500
condition|)
continue|continue;
name|dates
operator|.
name|add
argument_list|(
operator|new
name|Entity
argument_list|(
name|t
operator|.
name|getDatePatternId
argument_list|()
argument_list|,
name|t
operator|.
name|getDatePatternName
argument_list|()
argument_list|,
name|t
operator|.
name|getDatePatternName
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|times
operator|.
name|add
argument_list|(
operator|new
name|Entity
argument_list|(
operator|new
name|Long
argument_list|(
operator|(
name|t
operator|.
name|getDayCode
argument_list|()
operator|-
literal|0xff
operator|)
operator|*
literal|288
operator|-
name|t
operator|.
name|getStartSlot
argument_list|()
argument_list|)
argument_list|,
name|getDaysName
argument_list|(
name|t
operator|.
name|getDayCode
argument_list|()
argument_list|)
operator|+
literal|" "
operator|+
name|t
operator|.
name|getStartTimeHeader
argument_list|(
name|CONSTANTS
operator|.
name|useAmPm
argument_list|()
argument_list|)
argument_list|,
name|getDaysName
argument_list|(
name|t
operator|.
name|getDayCode
argument_list|()
argument_list|)
operator|+
literal|" "
operator|+
name|t
operator|.
name|getStartTimeHeader
argument_list|(
name|CONSTANTS
operator|.
name|useAmPm
argument_list|()
argument_list|)
operator|+
literal|" - "
operator|+
name|t
operator|.
name|getEndTimeHeader
argument_list|(
name|CONSTANTS
operator|.
name|useAmPm
argument_list|()
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|allDays
operator|=
operator|(
name|allDays
operator||
name|t
operator|.
name|getDayCode
argument_list|()
operator|)
expr_stmt|;
block|}
name|entities
operator|.
name|put
argument_list|(
literal|"date"
argument_list|,
name|dates
argument_list|)
expr_stmt|;
name|entities
operator|.
name|put
argument_list|(
literal|"time"
argument_list|,
name|times
argument_list|)
expr_stmt|;
block|}
name|List
argument_list|<
name|Entity
argument_list|>
name|daysOfWeek
init|=
operator|new
name|ArrayList
argument_list|<
name|Entity
argument_list|>
argument_list|()
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
name|Constants
operator|.
name|DAY_CODES
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
if|if
condition|(
operator|(
name|allDays
operator|&
name|Constants
operator|.
name|DAY_CODES
index|[
name|i
index|]
operator|)
operator|!=
literal|0
condition|)
name|daysOfWeek
operator|.
name|add
argument_list|(
operator|new
name|Entity
argument_list|(
operator|new
name|Long
argument_list|(
name|i
argument_list|)
argument_list|,
name|Constants
operator|.
name|DAY_NAMES_FULL
index|[
name|i
index|]
argument_list|,
name|CONSTANTS
operator|.
name|longDays
argument_list|()
index|[
name|i
index|]
argument_list|,
literal|"translated-value"
argument_list|,
name|CONSTANTS
operator|.
name|longDays
argument_list|()
index|[
name|i
index|]
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|entities
operator|.
name|put
argument_list|(
literal|"day"
argument_list|,
name|daysOfWeek
argument_list|)
expr_stmt|;
name|List
argument_list|<
name|Entity
argument_list|>
name|flags
init|=
operator|new
name|ArrayList
argument_list|<
name|Entity
argument_list|>
argument_list|()
decl_stmt|;
name|flags
operator|.
name|add
argument_list|(
operator|new
name|Entity
argument_list|(
literal|1l
argument_list|,
literal|"Same Room"
argument_list|,
name|MESSAGES
operator|.
name|suggestionsSameRoom
argument_list|()
argument_list|,
literal|"translated-value"
argument_list|,
name|MESSAGES
operator|.
name|suggestionsSameRoom
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|flags
operator|.
name|add
argument_list|(
operator|new
name|Entity
argument_list|(
literal|2l
argument_list|,
literal|"Same Time"
argument_list|,
name|MESSAGES
operator|.
name|suggestionsSameTime
argument_list|()
argument_list|,
literal|"translated-value"
argument_list|,
name|MESSAGES
operator|.
name|suggestionsSameTime
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|flags
operator|.
name|add
argument_list|(
operator|new
name|Entity
argument_list|(
literal|3l
argument_list|,
literal|"Allow Break Hard"
argument_list|,
name|MESSAGES
operator|.
name|suggestionsAllowBreakHard
argument_list|()
argument_list|,
literal|"translated-value"
argument_list|,
name|MESSAGES
operator|.
name|suggestionsAllowBreakHard
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|entities
operator|.
name|put
argument_list|(
literal|"flag"
argument_list|,
name|flags
argument_list|)
expr_stmt|;
name|List
argument_list|<
name|Entity
argument_list|>
name|modes
init|=
operator|new
name|ArrayList
argument_list|<
name|Entity
argument_list|>
argument_list|()
decl_stmt|;
name|modes
operator|.
name|add
argument_list|(
operator|new
name|Entity
argument_list|(
literal|1l
argument_list|,
literal|"Suggestions"
argument_list|,
name|MESSAGES
operator|.
name|suggestionsSuggestions
argument_list|()
argument_list|,
literal|"translated-value"
argument_list|,
name|MESSAGES
operator|.
name|suggestionsSuggestions
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|modes
operator|.
name|add
argument_list|(
operator|new
name|Entity
argument_list|(
literal|2l
argument_list|,
literal|"Placements"
argument_list|,
name|MESSAGES
operator|.
name|suggestionsPlacements
argument_list|()
argument_list|,
literal|"translated-value"
argument_list|,
name|MESSAGES
operator|.
name|suggestionsPlacements
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|entities
operator|.
name|put
argument_list|(
literal|"mode"
argument_list|,
name|modes
argument_list|)
expr_stmt|;
return|return
name|entities
return|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|suggestions
parameter_list|(
name|SuggestionsFilterRpcRequest
name|request
parameter_list|,
name|FilterRpcResponse
name|response
parameter_list|,
name|SessionContext
name|context
parameter_list|)
block|{
block|}
annotation|@
name|Override
specifier|public
name|void
name|enumarate
parameter_list|(
name|SuggestionsFilterRpcRequest
name|request
parameter_list|,
name|FilterRpcResponse
name|response
parameter_list|,
name|SessionContext
name|context
parameter_list|)
block|{
block|}
block|}
end_class

end_unit

