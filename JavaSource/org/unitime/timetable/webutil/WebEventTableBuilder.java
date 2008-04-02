begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_package
package|package
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|webutil
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
name|ArrayList
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
name|javax
operator|.
name|servlet
operator|.
name|jsp
operator|.
name|JspWriter
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
name|web
operator|.
name|htmlgen
operator|.
name|TableCell
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
name|web
operator|.
name|htmlgen
operator|.
name|TableHeaderCell
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
name|web
operator|.
name|htmlgen
operator|.
name|TableRow
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
name|web
operator|.
name|htmlgen
operator|.
name|TableStream
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
name|form
operator|.
name|EventListForm
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
name|Event
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
name|util
operator|.
name|Constants
import|;
end_import

begin_class
specifier|public
class|class
name|WebEventTableBuilder
block|{
specifier|public
specifier|static
name|SimpleDateFormat
name|sDateFormat
init|=
operator|new
name|SimpleDateFormat
argument_list|(
literal|"MM/dd/yy (EEE)"
argument_list|,
name|Locale
operator|.
name|US
argument_list|)
decl_stmt|;
comment|//Colors
specifier|protected
specifier|static
name|String
name|indent
init|=
literal|"&nbsp;&nbsp;&nbsp;&nbsp;"
decl_stmt|;
specifier|protected
specifier|static
name|String
name|oddRowBGColor
init|=
literal|"#DFE7F2"
decl_stmt|;
specifier|protected
specifier|static
name|String
name|oddRowBGColorChild
init|=
literal|"#EFEFEF"
decl_stmt|;
specifier|protected
specifier|static
name|String
name|oddRowMouseOverBGColor
init|=
literal|"#8EACD0"
decl_stmt|;
specifier|protected
specifier|static
name|String
name|evenRowMouseOverBGColor
init|=
literal|"#8EACD0"
decl_stmt|;
specifier|protected
name|String
name|disabledColor
init|=
literal|"gray"
decl_stmt|;
specifier|protected
specifier|static
name|String
name|formName
init|=
literal|"eventListForm"
decl_stmt|;
specifier|protected
specifier|static
name|String
name|LABEL
init|=
literal|"&nbsp;"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|EVENT_CAPACITY
init|=
literal|"Capacity"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|EVENT_TYPE
init|=
literal|"Type"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|SPONSORING_ORG
init|=
literal|"Sponsoring Org"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|MEETING_DATE
init|=
literal|"Date"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|MEETING_TIME
init|=
literal|"Time"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|MEETING_LOCATION
init|=
literal|"Location"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|APPROVED_DATE
init|=
literal|"Approved"
decl_stmt|;
specifier|public
name|WebEventTableBuilder
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
block|}
specifier|protected
name|String
name|getRowMouseOver
parameter_list|(
name|boolean
name|isHeaderRow
parameter_list|,
name|boolean
name|isControl
parameter_list|)
block|{
return|return
operator|(
literal|"this.style.backgroundColor='"
operator|+
operator|(
name|isHeaderRow
condition|?
name|oddRowMouseOverBGColor
else|:
name|evenRowMouseOverBGColor
operator|)
operator|+
literal|"';this.style.cursor='"
operator|+
operator|(
name|isControl
condition|?
literal|"hand"
else|:
literal|"default"
operator|)
operator|+
literal|"';this.style.cursor='"
operator|+
operator|(
name|isControl
condition|?
literal|"pointer"
else|:
literal|"default"
operator|)
operator|+
literal|"';"
operator|)
return|;
block|}
specifier|protected
name|String
name|getRowMouseOut
parameter_list|(
name|boolean
name|isHeaderRow
parameter_list|)
block|{
return|return
operator|(
literal|"this.style.backgroundColor='"
operator|+
operator|(
name|isHeaderRow
condition|?
name|oddRowBGColor
else|:
literal|"transparent"
operator|)
operator|+
literal|"';"
operator|)
return|;
block|}
specifier|protected
name|TableRow
name|initRow
parameter_list|(
name|boolean
name|isHeaderRow
parameter_list|)
block|{
name|TableRow
name|row
init|=
operator|new
name|TableRow
argument_list|()
decl_stmt|;
if|if
condition|(
name|isHeaderRow
condition|)
block|{
name|row
operator|.
name|setBgColor
argument_list|(
name|oddRowBGColor
argument_list|)
expr_stmt|;
block|}
return|return
operator|(
name|row
operator|)
return|;
block|}
specifier|protected
name|TableHeaderCell
name|headerCell
parameter_list|(
name|String
name|content
parameter_list|,
name|int
name|rowSpan
parameter_list|,
name|int
name|colSpan
parameter_list|)
block|{
name|TableHeaderCell
name|cell
init|=
operator|new
name|TableHeaderCell
argument_list|()
decl_stmt|;
name|cell
operator|.
name|setRowSpan
argument_list|(
name|rowSpan
argument_list|)
expr_stmt|;
name|cell
operator|.
name|setColSpan
argument_list|(
name|colSpan
argument_list|)
expr_stmt|;
name|cell
operator|.
name|setAlign
argument_list|(
literal|"left"
argument_list|)
expr_stmt|;
name|cell
operator|.
name|setValign
argument_list|(
literal|"bottom"
argument_list|)
expr_stmt|;
name|cell
operator|.
name|addContent
argument_list|(
literal|"<font size=\"-1\">"
argument_list|)
expr_stmt|;
name|cell
operator|.
name|addContent
argument_list|(
name|content
argument_list|)
expr_stmt|;
name|cell
operator|.
name|addContent
argument_list|(
literal|"</font>"
argument_list|)
expr_stmt|;
return|return
operator|(
name|cell
operator|)
return|;
block|}
specifier|private
name|TableCell
name|initCell
parameter_list|(
name|boolean
name|isEditable
parameter_list|,
name|String
name|onClick
parameter_list|)
block|{
return|return
operator|(
name|initCell
argument_list|(
name|isEditable
argument_list|,
name|onClick
argument_list|,
literal|1
argument_list|,
literal|false
argument_list|)
operator|)
return|;
block|}
specifier|private
name|TableCell
name|initCell
parameter_list|(
name|boolean
name|isEditable
parameter_list|,
name|String
name|onClick
parameter_list|,
name|int
name|cols
parameter_list|)
block|{
return|return
operator|(
name|initCell
argument_list|(
name|isEditable
argument_list|,
name|onClick
argument_list|,
name|cols
argument_list|,
literal|false
argument_list|)
operator|)
return|;
block|}
specifier|private
name|TableCell
name|initCell
parameter_list|(
name|boolean
name|isEditable
parameter_list|,
name|String
name|onClick
parameter_list|,
name|int
name|cols
parameter_list|,
name|boolean
name|nowrap
parameter_list|)
block|{
name|TableCell
name|cell
init|=
operator|new
name|TableCell
argument_list|()
decl_stmt|;
name|cell
operator|.
name|setValign
argument_list|(
literal|"top"
argument_list|)
expr_stmt|;
if|if
condition|(
name|cols
operator|>
literal|1
condition|)
block|{
name|cell
operator|.
name|setColSpan
argument_list|(
name|cols
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|nowrap
condition|)
block|{
name|cell
operator|.
name|setNoWrap
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|onClick
operator|!=
literal|null
operator|&&
name|onClick
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
block|{
name|cell
operator|.
name|setOnClick
argument_list|(
name|onClick
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
operator|!
name|isEditable
condition|)
block|{
name|cell
operator|.
name|addContent
argument_list|(
literal|"<font color="
operator|+
name|disabledColor
operator|+
literal|">"
argument_list|)
expr_stmt|;
block|}
return|return
operator|(
name|cell
operator|)
return|;
block|}
specifier|private
name|void
name|endCell
parameter_list|(
name|TableCell
name|cell
parameter_list|,
name|boolean
name|isEditable
parameter_list|)
block|{
if|if
condition|(
operator|!
name|isEditable
condition|)
block|{
name|cell
operator|.
name|addContent
argument_list|(
literal|"</font>"
argument_list|)
expr_stmt|;
block|}
block|}
specifier|protected
name|TableCell
name|initNormalCell
parameter_list|(
name|String
name|text
parameter_list|,
name|boolean
name|isEditable
parameter_list|)
block|{
return|return
operator|(
name|initColSpanCell
argument_list|(
name|text
argument_list|,
name|isEditable
argument_list|,
literal|1
argument_list|)
operator|)
return|;
block|}
specifier|private
name|TableCell
name|initColSpanCell
parameter_list|(
name|String
name|text
parameter_list|,
name|boolean
name|isEditable
parameter_list|,
name|int
name|cols
parameter_list|)
block|{
name|TableCell
name|cell
init|=
name|initCell
argument_list|(
name|isEditable
argument_list|,
literal|null
argument_list|,
name|cols
argument_list|)
decl_stmt|;
name|cell
operator|.
name|addContent
argument_list|(
name|text
argument_list|)
expr_stmt|;
name|endCell
argument_list|(
name|cell
argument_list|,
name|isEditable
argument_list|)
expr_stmt|;
return|return
operator|(
name|cell
operator|)
return|;
block|}
specifier|protected
name|void
name|buildTableHeader
parameter_list|(
name|TableStream
name|table
parameter_list|)
block|{
name|TableRow
name|row
init|=
operator|new
name|TableRow
argument_list|()
decl_stmt|;
name|TableRow
name|row2
init|=
operator|new
name|TableRow
argument_list|()
decl_stmt|;
name|TableHeaderCell
name|cell
init|=
literal|null
decl_stmt|;
comment|//    	if (isShowLabel()){
name|cell
operator|=
name|this
operator|.
name|headerCell
argument_list|(
name|LABEL
argument_list|,
literal|2
argument_list|,
literal|1
argument_list|)
expr_stmt|;
name|cell
operator|.
name|addContent
argument_list|(
literal|"<hr>"
argument_list|)
expr_stmt|;
name|row
operator|.
name|addContent
argument_list|(
name|cell
argument_list|)
expr_stmt|;
comment|//    	}
comment|//    	if (isShowDivSec()){
name|cell
operator|=
name|this
operator|.
name|headerCell
argument_list|(
name|EVENT_CAPACITY
argument_list|,
literal|2
argument_list|,
literal|1
argument_list|)
expr_stmt|;
name|cell
operator|.
name|addContent
argument_list|(
literal|"<hr>"
argument_list|)
expr_stmt|;
name|row
operator|.
name|addContent
argument_list|(
name|cell
argument_list|)
expr_stmt|;
comment|//    	}
comment|//    	if (isShowDemand()){
name|cell
operator|=
name|this
operator|.
name|headerCell
argument_list|(
name|SPONSORING_ORG
argument_list|,
literal|2
argument_list|,
literal|1
argument_list|)
expr_stmt|;
name|cell
operator|.
name|addContent
argument_list|(
literal|"<hr>"
argument_list|)
expr_stmt|;
name|row
operator|.
name|addContent
argument_list|(
name|cell
argument_list|)
expr_stmt|;
comment|//    	}
comment|//    	if (isShowProjectedDemand()){
name|cell
operator|=
name|this
operator|.
name|headerCell
argument_list|(
name|MEETING_DATE
argument_list|,
literal|2
argument_list|,
literal|1
argument_list|)
expr_stmt|;
name|cell
operator|.
name|addContent
argument_list|(
literal|"<hr>"
argument_list|)
expr_stmt|;
name|row
operator|.
name|addContent
argument_list|(
name|cell
argument_list|)
expr_stmt|;
comment|//    	}
comment|//    	if (isShowLimit()){
name|cell
operator|=
name|this
operator|.
name|headerCell
argument_list|(
name|MEETING_TIME
argument_list|,
literal|2
argument_list|,
literal|1
argument_list|)
expr_stmt|;
name|cell
operator|.
name|addContent
argument_list|(
literal|"<hr>"
argument_list|)
expr_stmt|;
name|row
operator|.
name|addContent
argument_list|(
name|cell
argument_list|)
expr_stmt|;
comment|//    	}
comment|//    	if (isShowRoomRatio()){
name|cell
operator|=
name|this
operator|.
name|headerCell
argument_list|(
name|MEETING_LOCATION
argument_list|,
literal|2
argument_list|,
literal|1
argument_list|)
expr_stmt|;
name|cell
operator|.
name|addContent
argument_list|(
literal|"<hr>"
argument_list|)
expr_stmt|;
name|row
operator|.
name|addContent
argument_list|(
name|cell
argument_list|)
expr_stmt|;
comment|//    	}
comment|//    	if (isShowManager()){
name|cell
operator|=
name|this
operator|.
name|headerCell
argument_list|(
name|APPROVED_DATE
argument_list|,
literal|2
argument_list|,
literal|1
argument_list|)
expr_stmt|;
name|cell
operator|.
name|addContent
argument_list|(
literal|"<hr>"
argument_list|)
expr_stmt|;
name|row
operator|.
name|addContent
argument_list|(
name|cell
argument_list|)
expr_stmt|;
comment|//    	}
name|table
operator|.
name|addContent
argument_list|(
name|row
argument_list|)
expr_stmt|;
name|table
operator|.
name|addContent
argument_list|(
name|row2
argument_list|)
expr_stmt|;
block|}
specifier|private
name|String
name|subjectOnClickAction
parameter_list|(
name|Long
name|eventId
parameter_list|)
block|{
return|return
operator|(
literal|"document.location='eventDetail.do?op=view&id="
operator|+
name|eventId
operator|+
literal|"';"
operator|)
return|;
block|}
specifier|private
name|TableCell
name|buildEventName
parameter_list|(
name|Event
name|e
parameter_list|)
block|{
name|TableCell
name|cell
init|=
name|this
operator|.
name|initCell
argument_list|(
literal|true
argument_list|,
literal|null
argument_list|,
literal|1
argument_list|,
literal|true
argument_list|)
decl_stmt|;
name|cell
operator|.
name|addContent
argument_list|(
name|e
operator|.
name|getEventName
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
literal|"<b>"
operator|+
name|e
operator|.
name|getEventName
argument_list|()
operator|+
literal|"</b>"
argument_list|)
expr_stmt|;
name|this
operator|.
name|endCell
argument_list|(
name|cell
argument_list|,
literal|true
argument_list|)
expr_stmt|;
return|return
operator|(
name|cell
operator|)
return|;
block|}
specifier|private
name|TableCell
name|buildEventCapacity
parameter_list|(
name|Event
name|e
parameter_list|)
block|{
name|TableCell
name|cell
init|=
name|this
operator|.
name|initCell
argument_list|(
literal|true
argument_list|,
literal|null
argument_list|,
literal|1
argument_list|,
literal|true
argument_list|)
decl_stmt|;
name|int
name|minCap
init|=
operator|(
name|e
operator|.
name|getMinCapacity
argument_list|()
operator|==
literal|null
condition|?
operator|-
literal|1
else|:
name|e
operator|.
name|getMinCapacity
argument_list|()
operator|)
decl_stmt|;
name|int
name|maxCap
init|=
operator|(
name|e
operator|.
name|getMaxCapacity
argument_list|()
operator|==
literal|null
condition|?
operator|-
literal|1
else|:
name|e
operator|.
name|getMaxCapacity
argument_list|()
operator|)
decl_stmt|;
if|if
condition|(
name|minCap
operator|==
operator|-
literal|1
condition|)
block|{
name|cell
operator|.
name|addContent
argument_list|(
literal|""
argument_list|)
expr_stmt|;
block|}
else|else
block|{
if|if
condition|(
name|maxCap
operator|!=
operator|-
literal|1
condition|)
block|{
if|if
condition|(
name|maxCap
operator|!=
name|minCap
condition|)
block|{
name|cell
operator|.
name|addContent
argument_list|(
name|minCap
operator|+
literal|"-"
operator|+
name|maxCap
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|cell
operator|.
name|addContent
argument_list|(
name|minCap
argument_list|)
expr_stmt|;
block|}
block|}
block|}
name|this
operator|.
name|endCell
argument_list|(
name|cell
argument_list|,
literal|true
argument_list|)
expr_stmt|;
return|return
operator|(
name|cell
operator|)
return|;
block|}
specifier|private
name|TableCell
name|buildSponsoringOrg
parameter_list|(
name|Event
name|e
parameter_list|)
block|{
name|TableCell
name|cell
init|=
name|this
operator|.
name|initCell
argument_list|(
literal|true
argument_list|,
literal|null
argument_list|,
literal|1
argument_list|,
literal|true
argument_list|)
decl_stmt|;
name|cell
operator|.
name|addContent
argument_list|(
literal|""
argument_list|)
expr_stmt|;
return|return
operator|(
name|cell
operator|)
return|;
block|}
specifier|private
name|TableCell
name|buildEmptyEventInfo
parameter_list|()
block|{
name|TableCell
name|cell
init|=
name|this
operator|.
name|initCell
argument_list|(
literal|true
argument_list|,
literal|null
argument_list|,
literal|1
argument_list|,
literal|true
argument_list|)
decl_stmt|;
name|cell
operator|.
name|addContent
argument_list|(
literal|""
argument_list|)
expr_stmt|;
return|return
operator|(
name|cell
operator|)
return|;
block|}
specifier|private
name|TableCell
name|buildEmptyMeetingInfo
parameter_list|()
block|{
name|TableCell
name|cell
init|=
name|this
operator|.
name|initCell
argument_list|(
literal|true
argument_list|,
literal|null
argument_list|,
literal|1
argument_list|,
literal|true
argument_list|)
decl_stmt|;
name|cell
operator|.
name|addContent
argument_list|(
literal|""
argument_list|)
expr_stmt|;
return|return
operator|(
name|cell
operator|)
return|;
block|}
specifier|private
name|TableCell
name|buildDate
parameter_list|(
name|Meeting
name|m
parameter_list|)
block|{
name|TableCell
name|cell
init|=
name|this
operator|.
name|initCell
argument_list|(
literal|true
argument_list|,
literal|null
argument_list|,
literal|1
argument_list|,
literal|true
argument_list|)
decl_stmt|;
name|SimpleDateFormat
name|df
init|=
operator|new
name|SimpleDateFormat
argument_list|(
literal|"MM/dd/yy (EEE)"
argument_list|,
name|Locale
operator|.
name|US
argument_list|)
decl_stmt|;
name|cell
operator|.
name|addContent
argument_list|(
name|df
operator|.
name|format
argument_list|(
name|m
operator|.
name|getMeetingDate
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
comment|//date cannot be null
return|return
operator|(
name|cell
operator|)
return|;
block|}
specifier|private
name|TableCell
name|buildTime
parameter_list|(
name|Meeting
name|m
parameter_list|)
block|{
name|TableCell
name|cell
init|=
name|this
operator|.
name|initCell
argument_list|(
literal|true
argument_list|,
literal|null
argument_list|,
literal|1
argument_list|,
literal|true
argument_list|)
decl_stmt|;
name|int
name|start
init|=
name|Constants
operator|.
name|SLOT_LENGTH_MIN
operator|*
name|m
operator|.
name|getStartPeriod
argument_list|()
operator|+
name|Constants
operator|.
name|FIRST_SLOT_TIME_MIN
operator|+
operator|(
name|m
operator|.
name|getStartOffset
argument_list|()
operator|==
literal|null
condition|?
literal|0
else|:
name|m
operator|.
name|getStartOffset
argument_list|()
operator|)
decl_stmt|;
name|int
name|startHour
init|=
name|start
operator|/
literal|60
decl_stmt|;
name|int
name|startMin
init|=
name|start
operator|%
literal|60
decl_stmt|;
name|int
name|end
init|=
name|Constants
operator|.
name|SLOT_LENGTH_MIN
operator|*
name|m
operator|.
name|getStopPeriod
argument_list|()
operator|+
name|Constants
operator|.
name|FIRST_SLOT_TIME_MIN
operator|+
operator|(
name|m
operator|.
name|getStopOffset
argument_list|()
operator|==
literal|null
condition|?
literal|0
else|:
name|m
operator|.
name|getStopOffset
argument_list|()
operator|)
decl_stmt|;
name|int
name|endHour
init|=
name|end
operator|/
literal|60
decl_stmt|;
name|int
name|endMin
init|=
name|end
operator|%
literal|60
decl_stmt|;
name|cell
operator|.
name|addContent
argument_list|(
operator|(
name|startHour
operator|>
literal|12
condition|?
name|startHour
operator|-
literal|12
else|:
name|startHour
operator|)
operator|+
literal|":"
operator|+
operator|(
name|startMin
operator|<
literal|10
condition|?
literal|"0"
else|:
literal|""
operator|)
operator|+
name|startMin
operator|+
operator|(
name|startHour
operator|>=
literal|12
condition|?
literal|"p"
else|:
literal|"a"
operator|)
operator|+
literal|"-"
operator|+
operator|(
name|endHour
operator|>
literal|12
condition|?
name|endHour
operator|-
literal|12
else|:
name|endHour
operator|)
operator|+
literal|":"
operator|+
operator|(
name|endMin
operator|<
literal|10
condition|?
literal|"0"
else|:
literal|""
operator|)
operator|+
name|endMin
operator|+
operator|(
name|endHour
operator|>=
literal|12
condition|?
literal|"p"
else|:
literal|"a"
operator|)
argument_list|)
expr_stmt|;
comment|//time cannot be null
return|return
operator|(
name|cell
operator|)
return|;
block|}
specifier|private
name|TableCell
name|buildLocation
parameter_list|(
name|Meeting
name|m
parameter_list|)
block|{
name|TableCell
name|cell
init|=
name|this
operator|.
name|initCell
argument_list|(
literal|true
argument_list|,
literal|null
argument_list|,
literal|1
argument_list|,
literal|true
argument_list|)
decl_stmt|;
name|cell
operator|.
name|addContent
argument_list|(
name|m
operator|.
name|getLocation
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
name|m
operator|.
name|getLocation
argument_list|()
operator|.
name|getLabel
argument_list|()
argument_list|)
expr_stmt|;
return|return
operator|(
name|cell
operator|)
return|;
block|}
specifier|private
name|TableCell
name|buildApproved
parameter_list|(
name|Meeting
name|m
parameter_list|)
block|{
name|TableCell
name|cell
init|=
name|this
operator|.
name|initCell
argument_list|(
literal|true
argument_list|,
literal|null
argument_list|,
literal|1
argument_list|,
literal|true
argument_list|)
decl_stmt|;
name|SimpleDateFormat
name|df
init|=
operator|new
name|SimpleDateFormat
argument_list|(
literal|"MM/dd/yy"
argument_list|,
name|Locale
operator|.
name|US
argument_list|)
decl_stmt|;
name|cell
operator|.
name|addContent
argument_list|(
name|m
operator|.
name|getApprovedDate
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
name|df
operator|.
name|format
argument_list|(
name|m
operator|.
name|getApprovedDate
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
return|return
operator|(
name|cell
operator|)
return|;
block|}
specifier|private
name|void
name|addEventsRowsToTable
parameter_list|(
name|TableStream
name|table
parameter_list|,
name|Event
name|e
parameter_list|)
block|{
name|TableRow
name|row
init|=
operator|(
name|this
operator|.
name|initRow
argument_list|(
literal|true
argument_list|)
operator|)
decl_stmt|;
name|row
operator|.
name|setOnMouseOver
argument_list|(
name|this
operator|.
name|getRowMouseOver
argument_list|(
literal|true
argument_list|,
literal|true
argument_list|)
argument_list|)
expr_stmt|;
name|row
operator|.
name|setOnMouseOut
argument_list|(
name|this
operator|.
name|getRowMouseOut
argument_list|(
literal|true
argument_list|)
argument_list|)
expr_stmt|;
name|row
operator|.
name|setOnClick
argument_list|(
name|subjectOnClickAction
argument_list|(
name|e
operator|.
name|getUniqueId
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|TableCell
name|cell
init|=
literal|null
decl_stmt|;
name|row
operator|.
name|addContent
argument_list|(
name|buildEventName
argument_list|(
name|e
argument_list|)
argument_list|)
expr_stmt|;
name|row
operator|.
name|addContent
argument_list|(
name|buildEventCapacity
argument_list|(
name|e
argument_list|)
argument_list|)
expr_stmt|;
name|row
operator|.
name|addContent
argument_list|(
name|buildSponsoringOrg
argument_list|(
name|e
argument_list|)
argument_list|)
expr_stmt|;
name|row
operator|.
name|addContent
argument_list|(
name|buildEmptyEventInfo
argument_list|()
argument_list|)
expr_stmt|;
name|row
operator|.
name|addContent
argument_list|(
name|buildEmptyEventInfo
argument_list|()
argument_list|)
expr_stmt|;
name|row
operator|.
name|addContent
argument_list|(
name|buildEmptyEventInfo
argument_list|()
argument_list|)
expr_stmt|;
name|row
operator|.
name|addContent
argument_list|(
name|buildEmptyEventInfo
argument_list|()
argument_list|)
expr_stmt|;
name|table
operator|.
name|addContent
argument_list|(
name|row
argument_list|)
expr_stmt|;
block|}
specifier|private
name|void
name|addMeetingRowsToTable
parameter_list|(
name|TableStream
name|table
parameter_list|,
name|Meeting
name|m
parameter_list|)
block|{
name|TableRow
name|row
init|=
operator|(
name|this
operator|.
name|initRow
argument_list|(
literal|false
argument_list|)
operator|)
decl_stmt|;
name|row
operator|.
name|setOnMouseOver
argument_list|(
name|this
operator|.
name|getRowMouseOver
argument_list|(
literal|false
argument_list|,
literal|true
argument_list|)
argument_list|)
expr_stmt|;
name|row
operator|.
name|setOnMouseOut
argument_list|(
name|this
operator|.
name|getRowMouseOut
argument_list|(
literal|false
argument_list|)
argument_list|)
expr_stmt|;
name|row
operator|.
name|setOnClick
argument_list|(
name|subjectOnClickAction
argument_list|(
name|m
operator|.
name|getEvent
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|TableCell
name|cell
init|=
literal|null
decl_stmt|;
name|row
operator|.
name|addContent
argument_list|(
name|buildEmptyMeetingInfo
argument_list|()
argument_list|)
expr_stmt|;
name|row
operator|.
name|addContent
argument_list|(
name|buildEmptyMeetingInfo
argument_list|()
argument_list|)
expr_stmt|;
name|row
operator|.
name|addContent
argument_list|(
name|buildEmptyMeetingInfo
argument_list|()
argument_list|)
expr_stmt|;
name|row
operator|.
name|addContent
argument_list|(
name|buildDate
argument_list|(
name|m
argument_list|)
argument_list|)
expr_stmt|;
name|row
operator|.
name|addContent
argument_list|(
name|buildTime
argument_list|(
name|m
argument_list|)
argument_list|)
expr_stmt|;
name|row
operator|.
name|addContent
argument_list|(
name|buildLocation
argument_list|(
name|m
argument_list|)
argument_list|)
expr_stmt|;
name|row
operator|.
name|addContent
argument_list|(
name|buildApproved
argument_list|(
name|m
argument_list|)
argument_list|)
expr_stmt|;
name|table
operator|.
name|addContent
argument_list|(
name|row
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|htmlTableForEvents
parameter_list|(
name|EventListForm
name|form
parameter_list|,
name|JspWriter
name|outputStream
parameter_list|)
block|{
name|ArrayList
name|eventIds
init|=
operator|new
name|ArrayList
argument_list|()
decl_stmt|;
name|Event
name|event
init|=
operator|new
name|Event
argument_list|()
decl_stmt|;
name|List
name|events
init|=
name|Event
operator|.
name|findAll
argument_list|()
decl_stmt|;
name|TableStream
name|eventsTable
init|=
name|this
operator|.
name|initTable
argument_list|(
name|outputStream
argument_list|)
decl_stmt|;
name|Iterator
name|it
init|=
name|events
operator|.
name|iterator
argument_list|()
decl_stmt|;
while|while
condition|(
name|it
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|event
operator|=
operator|(
name|Event
operator|)
name|it
operator|.
name|next
argument_list|()
expr_stmt|;
name|eventIds
operator|.
name|add
argument_list|(
name|event
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
name|this
operator|.
name|addEventsRowsToTable
argument_list|(
name|eventsTable
argument_list|,
name|event
argument_list|)
expr_stmt|;
for|for
control|(
name|Iterator
name|i
init|=
operator|new
name|TreeSet
argument_list|(
name|event
operator|.
name|getMeetings
argument_list|()
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
name|Meeting
name|meeting
init|=
operator|(
name|Meeting
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|this
operator|.
name|addMeetingRowsToTable
argument_list|(
name|eventsTable
argument_list|,
name|meeting
argument_list|)
expr_stmt|;
block|}
block|}
name|eventsTable
operator|.
name|tableComplete
argument_list|()
expr_stmt|;
block|}
specifier|protected
name|TableStream
name|initTable
parameter_list|(
name|JspWriter
name|outputStream
parameter_list|)
block|{
name|TableStream
name|table
init|=
operator|new
name|TableStream
argument_list|(
name|outputStream
argument_list|)
decl_stmt|;
name|table
operator|.
name|setWidth
argument_list|(
literal|"90%"
argument_list|)
expr_stmt|;
name|table
operator|.
name|setBorder
argument_list|(
literal|0
argument_list|)
expr_stmt|;
name|table
operator|.
name|setCellSpacing
argument_list|(
literal|0
argument_list|)
expr_stmt|;
name|table
operator|.
name|setCellPadding
argument_list|(
literal|3
argument_list|)
expr_stmt|;
name|table
operator|.
name|tableDefComplete
argument_list|()
expr_stmt|;
name|this
operator|.
name|buildTableHeader
argument_list|(
name|table
argument_list|)
expr_stmt|;
return|return
operator|(
name|table
operator|)
return|;
block|}
block|}
end_class

end_unit

