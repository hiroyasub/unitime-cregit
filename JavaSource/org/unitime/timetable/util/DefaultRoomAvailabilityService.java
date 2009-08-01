begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_package
package|package
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|util
package|;
end_package

begin_import
import|import
name|java
operator|.
name|text
operator|.
name|SimpleDateFormat
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Calendar
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
name|Date
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Hashtable
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
name|Locale
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
name|java
operator|.
name|util
operator|.
name|Vector
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
name|Query
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
name|interfaces
operator|.
name|RoomAvailabilityInterface
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
name|Meeting
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
name|_RootDAO
import|;
end_import

begin_class
specifier|public
class|class
name|DefaultRoomAvailabilityService
implements|implements
name|RoomAvailabilityInterface
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
name|DefaultRoomAvailabilityService
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|private
name|Vector
argument_list|<
name|CacheElement
argument_list|>
name|iCache
init|=
operator|new
name|Vector
argument_list|<
name|CacheElement
argument_list|>
argument_list|()
decl_stmt|;
specifier|public
name|String
name|getTimeStamp
parameter_list|(
name|Date
name|startTime
parameter_list|,
name|Date
name|endTime
parameter_list|,
name|String
name|excludeType
parameter_list|)
block|{
name|TimeFrame
name|time
init|=
operator|new
name|TimeFrame
argument_list|(
name|startTime
argument_list|,
name|endTime
argument_list|)
decl_stmt|;
name|CacheElement
name|cache
init|=
name|get
argument_list|(
name|time
argument_list|,
name|excludeType
argument_list|)
decl_stmt|;
return|return
operator|(
name|cache
operator|==
literal|null
condition|?
literal|null
else|:
name|cache
operator|.
name|getTimestamp
argument_list|()
operator|)
return|;
block|}
specifier|public
name|CacheElement
name|get
parameter_list|(
name|TimeFrame
name|time
parameter_list|,
name|String
name|excludeType
parameter_list|)
block|{
synchronized|synchronized
init|(
name|iCache
init|)
block|{
for|for
control|(
name|CacheElement
name|cache
range|:
name|iCache
control|)
if|if
condition|(
name|cache
operator|.
name|cover
argument_list|(
name|time
argument_list|)
operator|&&
name|cache
operator|.
name|exclude
argument_list|(
name|excludeType
argument_list|)
condition|)
return|return
name|cache
return|;
block|}
return|return
literal|null
return|;
block|}
specifier|public
name|Collection
argument_list|<
name|TimeBlock
argument_list|>
name|getRoomAvailability
parameter_list|(
name|Location
name|location
parameter_list|,
name|Date
name|startTime
parameter_list|,
name|Date
name|endTime
parameter_list|,
name|String
name|excludeType
parameter_list|)
block|{
if|if
condition|(
name|location
operator|.
name|getPermanentId
argument_list|()
operator|==
literal|null
condition|)
return|return
literal|null
return|;
name|TimeFrame
name|time
init|=
operator|new
name|TimeFrame
argument_list|(
name|startTime
argument_list|,
name|endTime
argument_list|)
decl_stmt|;
synchronized|synchronized
init|(
name|iCache
init|)
block|{
name|CacheElement
name|cache
init|=
name|get
argument_list|(
name|time
argument_list|,
name|excludeType
argument_list|)
decl_stmt|;
if|if
condition|(
name|cache
operator|!=
literal|null
condition|)
return|return
name|cache
operator|.
name|get
argument_list|(
name|location
operator|.
name|getPermanentId
argument_list|()
argument_list|,
name|excludeType
argument_list|)
return|;
name|Calendar
name|start
init|=
name|Calendar
operator|.
name|getInstance
argument_list|(
name|Locale
operator|.
name|US
argument_list|)
decl_stmt|;
name|start
operator|.
name|setTime
argument_list|(
name|startTime
argument_list|)
expr_stmt|;
name|int
name|startMin
init|=
literal|60
operator|*
name|start
operator|.
name|get
argument_list|(
name|Calendar
operator|.
name|HOUR_OF_DAY
argument_list|)
operator|+
name|start
operator|.
name|get
argument_list|(
name|Calendar
operator|.
name|MINUTE
argument_list|)
decl_stmt|;
name|start
operator|.
name|add
argument_list|(
name|Calendar
operator|.
name|MINUTE
argument_list|,
operator|-
name|startMin
argument_list|)
expr_stmt|;
name|Calendar
name|end
init|=
name|Calendar
operator|.
name|getInstance
argument_list|(
name|Locale
operator|.
name|US
argument_list|)
decl_stmt|;
name|end
operator|.
name|setTime
argument_list|(
name|endTime
argument_list|)
expr_stmt|;
name|int
name|endMin
init|=
literal|60
operator|*
name|end
operator|.
name|get
argument_list|(
name|Calendar
operator|.
name|HOUR_OF_DAY
argument_list|)
operator|+
name|start
operator|.
name|get
argument_list|(
name|Calendar
operator|.
name|MINUTE
argument_list|)
decl_stmt|;
name|end
operator|.
name|add
argument_list|(
name|Calendar
operator|.
name|MINUTE
argument_list|,
operator|-
name|endMin
argument_list|)
expr_stmt|;
name|int
name|startSlot
init|=
operator|(
name|startMin
operator|-
name|Constants
operator|.
name|FIRST_SLOT_TIME_MIN
operator|)
operator|/
name|Constants
operator|.
name|SLOT_LENGTH_MIN
decl_stmt|;
name|int
name|endSlot
init|=
operator|(
name|endMin
operator|-
name|Constants
operator|.
name|FIRST_SLOT_TIME_MIN
operator|)
operator|/
name|Constants
operator|.
name|SLOT_LENGTH_MIN
decl_stmt|;
name|TreeSet
argument_list|<
name|TimeBlock
argument_list|>
name|ret
init|=
operator|new
name|TreeSet
argument_list|<
name|TimeBlock
argument_list|>
argument_list|()
decl_stmt|;
name|String
name|exclude
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|excludeType
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
name|sFinalExamType
operator|.
name|equals
argument_list|(
name|excludeType
argument_list|)
condition|)
name|exclude
operator|=
literal|"FinalExamEvent"
expr_stmt|;
if|else if
condition|(
name|sMidtermExamType
operator|.
name|equals
argument_list|(
name|excludeType
argument_list|)
condition|)
name|exclude
operator|=
literal|"MidtermExamEvent"
expr_stmt|;
if|else if
condition|(
name|sClassType
operator|.
name|equals
argument_list|(
name|excludeType
argument_list|)
condition|)
name|exclude
operator|=
literal|"ClassEvent"
expr_stmt|;
block|}
name|Query
name|q
init|=
operator|new
name|_RootDAO
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select m from Meeting m where m.locationPermanentId=:locPermId and "
operator|+
literal|"m.approvedDate is not null and "
operator|+
literal|"m.meetingDate>=:startDate and m.meetingDate<=:endDate and "
operator|+
literal|"m.startPeriod<:endSlot and m.stopPeriod>:startSlot"
operator|+
operator|(
name|exclude
operator|!=
literal|null
condition|?
literal|" and m.event.class!="
operator|+
name|exclude
else|:
literal|""
operator|)
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"locPermId"
argument_list|,
name|location
operator|.
name|getPermanentId
argument_list|()
argument_list|)
operator|.
name|setDate
argument_list|(
literal|"startDate"
argument_list|,
name|start
operator|.
name|getTime
argument_list|()
argument_list|)
operator|.
name|setDate
argument_list|(
literal|"endDate"
argument_list|,
name|end
operator|.
name|getTime
argument_list|()
argument_list|)
operator|.
name|setInteger
argument_list|(
literal|"startSlot"
argument_list|,
name|startSlot
argument_list|)
operator|.
name|setInteger
argument_list|(
literal|"endSlot"
argument_list|,
name|endSlot
argument_list|)
operator|.
name|setCacheable
argument_list|(
literal|true
argument_list|)
decl_stmt|;
for|for
control|(
name|Iterator
name|i
init|=
name|q
operator|.
name|iterate
argument_list|()
init|;
name|i
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|Meeting
name|m
init|=
operator|(
name|Meeting
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|ret
operator|.
name|add
argument_list|(
operator|new
name|MeetingTimeBlock
argument_list|(
name|m
argument_list|)
argument_list|)
expr_stmt|;
block|}
return|return
name|ret
return|;
block|}
block|}
specifier|public
name|void
name|activate
parameter_list|(
name|Session
name|session
parameter_list|,
name|Date
name|startTime
parameter_list|,
name|Date
name|endTime
parameter_list|,
name|String
name|excludeType
parameter_list|,
name|boolean
name|waitForSync
parameter_list|)
block|{
name|TimeFrame
name|time
init|=
operator|new
name|TimeFrame
argument_list|(
name|startTime
argument_list|,
name|endTime
argument_list|)
decl_stmt|;
synchronized|synchronized
init|(
name|iCache
init|)
block|{
name|CacheElement
name|cache
init|=
name|get
argument_list|(
name|time
argument_list|,
name|excludeType
argument_list|)
decl_stmt|;
if|if
condition|(
name|cache
operator|==
literal|null
condition|)
block|{
name|cache
operator|=
operator|new
name|CacheElement
argument_list|(
name|time
argument_list|,
name|excludeType
argument_list|)
expr_stmt|;
name|iCache
operator|.
name|add
argument_list|(
name|cache
argument_list|)
expr_stmt|;
block|}
name|cache
operator|.
name|update
argument_list|()
expr_stmt|;
block|}
block|}
specifier|public
name|void
name|startService
parameter_list|()
block|{
block|}
specifier|public
name|void
name|stopService
parameter_list|()
block|{
block|}
specifier|public
specifier|static
class|class
name|TimeFrame
block|{
specifier|private
name|Date
name|iStart
decl_stmt|,
name|iEnd
decl_stmt|;
specifier|private
name|int
name|iStartSlot
decl_stmt|,
name|iEndSlot
decl_stmt|;
specifier|public
name|TimeFrame
parameter_list|(
name|Date
name|startTime
parameter_list|,
name|Date
name|endTime
parameter_list|)
block|{
name|Calendar
name|start
init|=
name|Calendar
operator|.
name|getInstance
argument_list|(
name|Locale
operator|.
name|US
argument_list|)
decl_stmt|;
name|start
operator|.
name|setTime
argument_list|(
name|startTime
argument_list|)
expr_stmt|;
name|int
name|startMin
init|=
literal|60
operator|*
name|start
operator|.
name|get
argument_list|(
name|Calendar
operator|.
name|HOUR_OF_DAY
argument_list|)
operator|+
name|start
operator|.
name|get
argument_list|(
name|Calendar
operator|.
name|MINUTE
argument_list|)
decl_stmt|;
name|start
operator|.
name|add
argument_list|(
name|Calendar
operator|.
name|MINUTE
argument_list|,
operator|-
name|startMin
argument_list|)
expr_stmt|;
name|Calendar
name|end
init|=
name|Calendar
operator|.
name|getInstance
argument_list|(
name|Locale
operator|.
name|US
argument_list|)
decl_stmt|;
name|end
operator|.
name|setTime
argument_list|(
name|endTime
argument_list|)
expr_stmt|;
name|int
name|endMin
init|=
literal|60
operator|*
name|end
operator|.
name|get
argument_list|(
name|Calendar
operator|.
name|HOUR_OF_DAY
argument_list|)
operator|+
name|start
operator|.
name|get
argument_list|(
name|Calendar
operator|.
name|MINUTE
argument_list|)
decl_stmt|;
name|end
operator|.
name|add
argument_list|(
name|Calendar
operator|.
name|MINUTE
argument_list|,
operator|-
name|endMin
argument_list|)
expr_stmt|;
name|iStartSlot
operator|=
operator|(
name|startMin
operator|-
name|Constants
operator|.
name|FIRST_SLOT_TIME_MIN
operator|)
operator|/
name|Constants
operator|.
name|SLOT_LENGTH_MIN
expr_stmt|;
name|iEndSlot
operator|=
operator|(
name|endMin
operator|-
name|Constants
operator|.
name|FIRST_SLOT_TIME_MIN
operator|)
operator|/
name|Constants
operator|.
name|SLOT_LENGTH_MIN
expr_stmt|;
name|iStart
operator|=
name|start
operator|.
name|getTime
argument_list|()
expr_stmt|;
name|iEnd
operator|=
name|end
operator|.
name|getTime
argument_list|()
expr_stmt|;
block|}
specifier|public
name|Date
name|getStartDate
parameter_list|()
block|{
return|return
name|iStart
return|;
block|}
specifier|public
name|Date
name|getEndDate
parameter_list|()
block|{
return|return
name|iEnd
return|;
block|}
specifier|public
name|int
name|getStartSlot
parameter_list|()
block|{
return|return
name|iStartSlot
return|;
block|}
specifier|public
name|int
name|getEndSlot
parameter_list|()
block|{
return|return
name|iEndSlot
return|;
block|}
specifier|public
name|int
name|hashCode
parameter_list|()
block|{
return|return
name|iStart
operator|.
name|hashCode
argument_list|()
operator|^
name|iEnd
operator|.
name|hashCode
argument_list|()
return|;
block|}
specifier|public
name|boolean
name|equals
parameter_list|(
name|Object
name|o
parameter_list|)
block|{
if|if
condition|(
name|o
operator|==
literal|null
operator|||
operator|!
operator|(
name|o
operator|instanceof
name|TimeFrame
operator|)
condition|)
return|return
literal|false
return|;
name|TimeFrame
name|t
init|=
operator|(
name|TimeFrame
operator|)
name|o
decl_stmt|;
return|return
name|getStartDate
argument_list|()
operator|.
name|equals
argument_list|(
name|t
operator|.
name|getStartDate
argument_list|()
argument_list|)
operator|&&
name|getEndDate
argument_list|()
operator|.
name|equals
argument_list|(
name|t
operator|.
name|getEndDate
argument_list|()
argument_list|)
operator|&&
name|getStartSlot
argument_list|()
operator|==
name|t
operator|.
name|getStartSlot
argument_list|()
operator|&&
name|getEndSlot
argument_list|()
operator|==
name|t
operator|.
name|getEndSlot
argument_list|()
return|;
block|}
specifier|public
name|String
name|toString
parameter_list|()
block|{
name|SimpleDateFormat
name|df
init|=
operator|new
name|SimpleDateFormat
argument_list|(
literal|"MM/dd/yy"
argument_list|)
decl_stmt|;
name|int
name|start
init|=
name|getStartSlot
argument_list|()
operator|*
name|Constants
operator|.
name|SLOT_LENGTH_MIN
operator|+
name|Constants
operator|.
name|FIRST_SLOT_TIME_MIN
decl_stmt|;
name|int
name|end
init|=
name|getEndSlot
argument_list|()
operator|*
name|Constants
operator|.
name|SLOT_LENGTH_MIN
operator|+
name|Constants
operator|.
name|FIRST_SLOT_TIME_MIN
decl_stmt|;
return|return
name|df
operator|.
name|format
argument_list|(
name|getStartDate
argument_list|()
argument_list|)
operator|+
literal|" - "
operator|+
name|df
operator|.
name|format
argument_list|(
name|getEndDate
argument_list|()
argument_list|)
operator|+
literal|" "
operator|+
name|Constants
operator|.
name|toTime
argument_list|(
name|start
argument_list|)
operator|+
literal|" - "
operator|+
name|Constants
operator|.
name|toTime
argument_list|(
name|end
argument_list|)
return|;
block|}
block|}
specifier|public
specifier|static
class|class
name|CacheElement
block|{
specifier|private
name|TimeFrame
name|iTime
decl_stmt|;
specifier|private
name|Hashtable
argument_list|<
name|Long
argument_list|,
name|TreeSet
argument_list|<
name|TimeBlock
argument_list|>
argument_list|>
name|iAvailability
init|=
operator|new
name|Hashtable
argument_list|()
decl_stmt|;
specifier|private
name|String
name|iTimestamp
init|=
literal|null
decl_stmt|;
specifier|private
name|String
name|iExcludeType
init|=
literal|null
decl_stmt|;
specifier|public
name|CacheElement
parameter_list|(
name|TimeFrame
name|time
parameter_list|,
name|String
name|excludeType
parameter_list|)
block|{
name|iTime
operator|=
name|time
expr_stmt|;
name|iExcludeType
operator|=
name|excludeType
expr_stmt|;
block|}
empty_stmt|;
specifier|public
name|void
name|update
parameter_list|()
block|{
name|iAvailability
operator|.
name|clear
argument_list|()
expr_stmt|;
name|String
name|exclude
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|iExcludeType
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
name|sFinalExamType
operator|.
name|equals
argument_list|(
name|iExcludeType
argument_list|)
condition|)
name|exclude
operator|=
literal|"FinalExamEvent"
expr_stmt|;
if|else if
condition|(
name|sMidtermExamType
operator|.
name|equals
argument_list|(
name|iExcludeType
argument_list|)
condition|)
name|exclude
operator|=
literal|"MidtermExamEvent"
expr_stmt|;
if|else if
condition|(
name|sClassType
operator|.
name|equals
argument_list|(
name|iExcludeType
argument_list|)
condition|)
name|exclude
operator|=
literal|"ClassEvent"
expr_stmt|;
block|}
name|Query
name|q
init|=
operator|new
name|_RootDAO
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select m from Meeting m where m.locationPermanentId!=null and "
operator|+
literal|"m.approvedDate is not null and "
operator|+
literal|"m.meetingDate>=:startDate and m.meetingDate<=:endDate and "
operator|+
literal|"m.startPeriod<:endSlot and m.stopPeriod>:startSlot"
operator|+
operator|(
name|exclude
operator|==
literal|null
condition|?
literal|""
else|:
literal|" and m.event.class!="
operator|+
name|exclude
operator|)
argument_list|)
operator|.
name|setDate
argument_list|(
literal|"startDate"
argument_list|,
name|iTime
operator|.
name|getStartDate
argument_list|()
argument_list|)
operator|.
name|setDate
argument_list|(
literal|"endDate"
argument_list|,
name|iTime
operator|.
name|getEndDate
argument_list|()
argument_list|)
operator|.
name|setInteger
argument_list|(
literal|"startSlot"
argument_list|,
name|iTime
operator|.
name|getStartSlot
argument_list|()
argument_list|)
operator|.
name|setInteger
argument_list|(
literal|"endSlot"
argument_list|,
name|iTime
operator|.
name|getEndSlot
argument_list|()
argument_list|)
operator|.
name|setCacheable
argument_list|(
literal|true
argument_list|)
decl_stmt|;
for|for
control|(
name|Iterator
name|i
init|=
name|q
operator|.
name|iterate
argument_list|()
init|;
name|i
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|Meeting
name|m
init|=
operator|(
name|Meeting
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|TreeSet
argument_list|<
name|TimeBlock
argument_list|>
name|blocks
init|=
name|iAvailability
operator|.
name|get
argument_list|(
name|m
operator|.
name|getLocationPermanentId
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|blocks
operator|==
literal|null
condition|)
block|{
name|blocks
operator|=
operator|new
name|TreeSet
argument_list|()
expr_stmt|;
name|iAvailability
operator|.
name|put
argument_list|(
name|m
operator|.
name|getLocationPermanentId
argument_list|()
argument_list|,
name|blocks
argument_list|)
expr_stmt|;
block|}
name|blocks
operator|.
name|add
argument_list|(
operator|new
name|MeetingTimeBlock
argument_list|(
name|m
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|iTimestamp
operator|=
operator|new
name|Date
argument_list|()
operator|.
name|toString
argument_list|()
expr_stmt|;
block|}
specifier|public
name|TreeSet
argument_list|<
name|TimeBlock
argument_list|>
name|get
parameter_list|(
name|Long
name|roomPermId
parameter_list|,
name|String
name|excludeType
parameter_list|)
block|{
name|TreeSet
argument_list|<
name|TimeBlock
argument_list|>
name|roomAvailability
init|=
name|iAvailability
operator|.
name|get
argument_list|(
name|roomPermId
argument_list|)
decl_stmt|;
if|if
condition|(
name|roomAvailability
operator|==
literal|null
operator|||
name|excludeType
operator|==
literal|null
operator|||
name|excludeType
operator|.
name|equals
argument_list|(
name|iExcludeType
argument_list|)
condition|)
return|return
name|roomAvailability
return|;
name|TreeSet
argument_list|<
name|TimeBlock
argument_list|>
name|ret
init|=
operator|new
name|TreeSet
argument_list|()
decl_stmt|;
for|for
control|(
name|TimeBlock
name|block
range|:
name|roomAvailability
control|)
block|{
if|if
condition|(
name|excludeType
operator|.
name|equals
argument_list|(
name|block
operator|.
name|getEventType
argument_list|()
argument_list|)
condition|)
continue|continue;
name|ret
operator|.
name|add
argument_list|(
name|block
argument_list|)
expr_stmt|;
block|}
return|return
name|ret
return|;
block|}
specifier|public
name|TimeFrame
name|getTimeFrame
parameter_list|()
block|{
return|return
name|iTime
return|;
block|}
specifier|public
name|String
name|getExcludeType
parameter_list|()
block|{
return|return
name|iExcludeType
return|;
block|}
specifier|public
name|boolean
name|exclude
parameter_list|(
name|String
name|type
parameter_list|)
block|{
return|return
operator|(
name|iExcludeType
operator|==
literal|null
operator|||
name|iExcludeType
operator|.
name|equals
argument_list|(
name|type
argument_list|)
operator|)
return|;
block|}
specifier|public
name|boolean
name|cover
parameter_list|(
name|TimeFrame
name|time
parameter_list|)
block|{
return|return
operator|(
name|iTime
operator|.
name|getStartDate
argument_list|()
operator|.
name|compareTo
argument_list|(
name|time
operator|.
name|getStartDate
argument_list|()
argument_list|)
operator|<=
literal|0
operator|&&
name|time
operator|.
name|getEndDate
argument_list|()
operator|.
name|compareTo
argument_list|(
name|iTime
operator|.
name|getEndDate
argument_list|()
argument_list|)
operator|<=
literal|0
operator|&&
name|iTime
operator|.
name|getStartSlot
argument_list|()
operator|<=
name|time
operator|.
name|getStartSlot
argument_list|()
operator|&&
name|time
operator|.
name|getEndSlot
argument_list|()
operator|<=
name|iTime
operator|.
name|getEndSlot
argument_list|()
operator|)
return|;
block|}
specifier|public
name|String
name|getTimestamp
parameter_list|()
block|{
return|return
name|iTimestamp
return|;
block|}
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
name|iTime
operator|.
name|toString
argument_list|()
return|;
block|}
block|}
specifier|public
specifier|static
class|class
name|MeetingTimeBlock
implements|implements
name|TimeBlock
implements|,
name|Comparable
argument_list|<
name|TimeBlock
argument_list|>
block|{
name|Long
name|iMeetingId
decl_stmt|;
name|String
name|iEventName
decl_stmt|,
name|iEventType
decl_stmt|;
name|Date
name|iStart
decl_stmt|,
name|iEnd
decl_stmt|;
specifier|public
name|MeetingTimeBlock
parameter_list|(
name|Meeting
name|m
parameter_list|)
block|{
name|iMeetingId
operator|=
name|m
operator|.
name|getUniqueId
argument_list|()
expr_stmt|;
name|iEventName
operator|=
name|m
operator|.
name|getEvent
argument_list|()
operator|.
name|getEventName
argument_list|()
expr_stmt|;
name|iEventType
operator|=
name|m
operator|.
name|getEvent
argument_list|()
operator|.
name|getEventTypeLabel
argument_list|()
expr_stmt|;
name|iStart
operator|=
name|m
operator|.
name|getTrueStartTime
argument_list|()
expr_stmt|;
name|iEnd
operator|=
name|m
operator|.
name|getTrueStopTime
argument_list|()
expr_stmt|;
block|}
specifier|public
name|Long
name|getId
parameter_list|()
block|{
return|return
name|iMeetingId
return|;
block|}
specifier|public
name|String
name|getEventName
parameter_list|()
block|{
return|return
name|iEventName
return|;
block|}
specifier|public
name|String
name|getEventType
parameter_list|()
block|{
return|return
name|iEventType
return|;
block|}
specifier|public
name|Date
name|getStartTime
parameter_list|()
block|{
return|return
name|iStart
return|;
block|}
specifier|public
name|Date
name|getEndTime
parameter_list|()
block|{
return|return
name|iEnd
return|;
block|}
specifier|public
name|String
name|toString
parameter_list|()
block|{
name|SimpleDateFormat
name|df
init|=
operator|new
name|SimpleDateFormat
argument_list|(
literal|"MM/dd/yy HH:mm"
argument_list|)
decl_stmt|;
name|SimpleDateFormat
name|df2
init|=
operator|new
name|SimpleDateFormat
argument_list|(
literal|"HH:mm"
argument_list|)
decl_stmt|;
return|return
name|getEventName
argument_list|()
operator|+
literal|" ("
operator|+
name|getEventType
argument_list|()
operator|+
literal|") "
operator|+
name|df
operator|.
name|format
argument_list|(
name|getStartTime
argument_list|()
argument_list|)
operator|+
literal|" - "
operator|+
name|df2
operator|.
name|format
argument_list|(
name|getEndTime
argument_list|()
argument_list|)
return|;
block|}
specifier|public
name|boolean
name|equals
parameter_list|(
name|Object
name|o
parameter_list|)
block|{
if|if
condition|(
name|o
operator|==
literal|null
operator|||
operator|!
operator|(
name|o
operator|instanceof
name|MeetingTimeBlock
operator|)
condition|)
return|return
literal|false
return|;
name|MeetingTimeBlock
name|m
init|=
operator|(
name|MeetingTimeBlock
operator|)
name|o
decl_stmt|;
return|return
name|getId
argument_list|()
operator|.
name|equals
argument_list|(
name|m
operator|.
name|getId
argument_list|()
argument_list|)
return|;
block|}
specifier|public
name|int
name|hashCode
parameter_list|()
block|{
return|return
name|getId
argument_list|()
operator|.
name|hashCode
argument_list|()
return|;
block|}
specifier|public
name|int
name|compareTo
parameter_list|(
name|TimeBlock
name|block
parameter_list|)
block|{
name|int
name|cmp
init|=
name|getStartTime
argument_list|()
operator|.
name|compareTo
argument_list|(
name|block
operator|.
name|getStartTime
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|cmp
operator|!=
literal|0
condition|)
return|return
name|cmp
return|;
name|cmp
operator|=
name|getEndTime
argument_list|()
operator|.
name|compareTo
argument_list|(
name|block
operator|.
name|getEndTime
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|cmp
operator|!=
literal|0
condition|)
return|return
name|cmp
return|;
return|return
name|getEventName
argument_list|()
operator|.
name|compareTo
argument_list|(
name|block
operator|.
name|getEventName
argument_list|()
argument_list|)
return|;
block|}
block|}
block|}
end_class

end_unit

