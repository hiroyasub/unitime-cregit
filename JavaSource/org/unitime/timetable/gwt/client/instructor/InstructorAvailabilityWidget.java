begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_package
package|package
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
name|instructor
package|;
end_package

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
name|page
operator|.
name|UniTimeNotifications
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
name|RoomSharingWidget
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
name|GwtRpcRequest
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
name|GwtRpcService
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
name|GwtRpcServiceAsync
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
name|RoomSharingModel
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
name|RoomSharingOption
import|;
end_import

begin_import
import|import
name|com
operator|.
name|google
operator|.
name|gwt
operator|.
name|core
operator|.
name|client
operator|.
name|GWT
import|;
end_import

begin_import
import|import
name|com
operator|.
name|google
operator|.
name|gwt
operator|.
name|event
operator|.
name|logical
operator|.
name|shared
operator|.
name|ValueChangeEvent
import|;
end_import

begin_import
import|import
name|com
operator|.
name|google
operator|.
name|gwt
operator|.
name|event
operator|.
name|logical
operator|.
name|shared
operator|.
name|ValueChangeHandler
import|;
end_import

begin_import
import|import
name|com
operator|.
name|google
operator|.
name|gwt
operator|.
name|user
operator|.
name|client
operator|.
name|rpc
operator|.
name|AsyncCallback
import|;
end_import

begin_import
import|import
name|com
operator|.
name|google
operator|.
name|gwt
operator|.
name|user
operator|.
name|client
operator|.
name|ui
operator|.
name|Hidden
import|;
end_import

begin_import
import|import
name|com
operator|.
name|google
operator|.
name|gwt
operator|.
name|user
operator|.
name|client
operator|.
name|ui
operator|.
name|RootPanel
import|;
end_import

begin_class
specifier|public
class|class
name|InstructorAvailabilityWidget
extends|extends
name|RoomSharingWidget
block|{
specifier|private
specifier|static
name|GwtRpcServiceAsync
name|RPC
init|=
name|GWT
operator|.
name|create
argument_list|(
name|GwtRpcService
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|private
name|Hidden
name|iPattern
init|=
literal|null
decl_stmt|;
specifier|public
name|InstructorAvailabilityWidget
parameter_list|()
block|{
name|super
argument_list|(
literal|false
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|insert
parameter_list|(
specifier|final
name|RootPanel
name|panel
parameter_list|)
block|{
name|RPC
operator|.
name|execute
argument_list|(
name|InstructorAvailabilityRequest
operator|.
name|load
argument_list|(
literal|null
argument_list|)
argument_list|,
operator|new
name|AsyncCallback
argument_list|<
name|InstructorAvailabilityModel
argument_list|>
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|onFailure
parameter_list|(
name|Throwable
name|caught
parameter_list|)
block|{
name|UniTimeNotifications
operator|.
name|error
argument_list|(
name|caught
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|onSuccess
parameter_list|(
specifier|final
name|InstructorAvailabilityModel
name|model
parameter_list|)
block|{
if|if
condition|(
name|panel
operator|.
name|getElement
argument_list|()
operator|.
name|getFirstChildElement
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|iPattern
operator|=
name|Hidden
operator|.
name|wrap
argument_list|(
name|panel
operator|.
name|getElement
argument_list|()
operator|.
name|getFirstChildElement
argument_list|()
argument_list|)
expr_stmt|;
name|model
operator|.
name|setPattern
argument_list|(
name|iPattern
operator|.
name|getValue
argument_list|()
argument_list|)
expr_stmt|;
name|addValueChangeHandler
argument_list|(
operator|new
name|ValueChangeHandler
argument_list|<
name|RoomInterface
operator|.
name|RoomSharingModel
argument_list|>
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|onValueChange
parameter_list|(
name|ValueChangeEvent
argument_list|<
name|RoomSharingModel
argument_list|>
name|event
parameter_list|)
block|{
name|iPattern
operator|.
name|setValue
argument_list|(
name|event
operator|.
name|getValue
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|iEditable
operator|=
literal|true
expr_stmt|;
block|}
else|else
block|{
name|String
name|pattern
init|=
name|panel
operator|.
name|getElement
argument_list|()
operator|.
name|getInnerText
argument_list|()
operator|.
name|trim
argument_list|()
decl_stmt|;
name|panel
operator|.
name|getElement
argument_list|()
operator|.
name|setInnerText
argument_list|(
literal|null
argument_list|)
expr_stmt|;
name|model
operator|.
name|setPattern
argument_list|(
name|pattern
argument_list|)
expr_stmt|;
name|iEditable
operator|=
literal|false
expr_stmt|;
block|}
name|setModel
argument_list|(
name|model
argument_list|)
expr_stmt|;
name|panel
operator|.
name|add
argument_list|(
name|InstructorAvailabilityWidget
operator|.
name|this
argument_list|)
expr_stmt|;
name|panel
operator|.
name|setVisible
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
block|}
specifier|public
specifier|static
class|class
name|InstructorAvailabilityRequest
implements|implements
name|GwtRpcRequest
argument_list|<
name|InstructorAvailabilityModel
argument_list|>
block|{
specifier|private
name|String
name|iInstructorId
decl_stmt|;
specifier|public
name|InstructorAvailabilityRequest
parameter_list|()
block|{
block|}
specifier|public
name|String
name|getInstructorId
parameter_list|()
block|{
return|return
name|iInstructorId
return|;
block|}
specifier|public
name|void
name|setInstructorId
parameter_list|(
name|String
name|instructorId
parameter_list|)
block|{
name|iInstructorId
operator|=
name|instructorId
expr_stmt|;
block|}
specifier|public
specifier|static
name|InstructorAvailabilityRequest
name|load
parameter_list|(
name|String
name|instructorId
parameter_list|)
block|{
name|InstructorAvailabilityRequest
name|request
init|=
operator|new
name|InstructorAvailabilityRequest
argument_list|()
decl_stmt|;
name|request
operator|.
name|setInstructorId
argument_list|(
name|instructorId
argument_list|)
expr_stmt|;
return|return
name|request
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
name|iInstructorId
operator|==
literal|null
condition|?
literal|"NULL"
else|:
name|iInstructorId
return|;
block|}
block|}
empty_stmt|;
specifier|public
specifier|static
class|class
name|InstructorAvailabilityModel
extends|extends
name|RoomInterface
operator|.
name|RoomSharingModel
block|{
specifier|public
name|char
name|id2char
parameter_list|(
name|Long
name|id
parameter_list|)
block|{
if|if
condition|(
name|id
operator|==
literal|null
condition|)
return|return
literal|'2'
return|;
switch|switch
condition|(
name|id
operator|.
name|intValue
argument_list|()
condition|)
block|{
case|case
operator|-
literal|1
case|:
return|return
literal|'R'
return|;
case|case
operator|-
literal|2
case|:
return|return
literal|'0'
return|;
case|case
operator|-
literal|3
case|:
return|return
literal|'1'
return|;
case|case
operator|-
literal|4
case|:
return|return
literal|'2'
return|;
case|case
operator|-
literal|5
case|:
return|return
literal|'3'
return|;
case|case
operator|-
literal|6
case|:
return|return
literal|'4'
return|;
case|case
operator|-
literal|7
case|:
return|return
literal|'P'
return|;
default|default:
return|return
literal|'2'
return|;
block|}
block|}
specifier|public
name|Long
name|char2id
parameter_list|(
name|char
name|ch
parameter_list|)
block|{
switch|switch
condition|(
name|ch
condition|)
block|{
case|case
literal|'R'
case|:
return|return
operator|-
literal|1l
return|;
case|case
literal|'0'
case|:
return|return
operator|-
literal|2l
return|;
case|case
literal|'1'
case|:
return|return
operator|-
literal|3l
return|;
case|case
literal|'2'
case|:
return|return
operator|-
literal|4l
return|;
case|case
literal|'3'
case|:
return|return
operator|-
literal|5l
return|;
case|case
literal|'4'
case|:
return|return
operator|-
literal|6l
return|;
case|case
literal|'P'
case|:
return|return
operator|-
literal|7l
return|;
default|default:
return|return
operator|-
literal|4l
return|;
block|}
block|}
specifier|public
name|String
name|getPattern
parameter_list|()
block|{
name|String
name|pattern
init|=
literal|""
decl_stmt|;
for|for
control|(
name|int
name|d
init|=
literal|0
init|;
name|d
operator|<
literal|7
condition|;
name|d
operator|++
control|)
for|for
control|(
name|int
name|s
init|=
literal|0
init|;
name|s
operator|<
literal|288
condition|;
name|s
operator|++
control|)
block|{
name|RoomSharingOption
name|option
init|=
name|getOption
argument_list|(
name|d
argument_list|,
name|s
argument_list|)
decl_stmt|;
name|pattern
operator|+=
name|id2char
argument_list|(
name|option
operator|==
literal|null
condition|?
literal|null
else|:
name|option
operator|.
name|getId
argument_list|()
argument_list|)
expr_stmt|;
block|}
return|return
name|pattern
return|;
block|}
specifier|public
name|void
name|setPattern
parameter_list|(
name|String
name|pattern
parameter_list|)
block|{
if|if
condition|(
name|pattern
operator|.
name|length
argument_list|()
operator|<=
literal|336
condition|)
block|{
name|boolean
name|req
init|=
name|pattern
operator|.
name|indexOf
argument_list|(
literal|'R'
argument_list|)
operator|>=
literal|0
decl_stmt|;
for|for
control|(
name|int
name|d
init|=
literal|0
init|;
name|d
operator|<
literal|7
condition|;
name|d
operator|++
control|)
for|for
control|(
name|int
name|s
init|=
literal|0
init|;
name|s
operator|<
literal|288
condition|;
name|s
operator|++
control|)
block|{
name|char
name|ch
init|=
literal|'2'
decl_stmt|;
try|try
block|{
name|ch
operator|=
name|pattern
operator|.
name|charAt
argument_list|(
literal|48
operator|*
name|d
operator|+
name|s
operator|/
literal|6
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IndexOutOfBoundsException
name|e
parameter_list|)
block|{
block|}
name|setOption
argument_list|(
name|d
argument_list|,
name|s
argument_list|,
name|char2id
argument_list|(
name|req
condition|?
name|ch
operator|==
literal|'R'
condition|?
literal|'2'
else|:
literal|'P'
else|:
name|ch
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
for|for
control|(
name|int
name|d
init|=
literal|0
init|;
name|d
operator|<
literal|7
condition|;
name|d
operator|++
control|)
for|for
control|(
name|int
name|s
init|=
literal|0
init|;
name|s
operator|<
literal|288
condition|;
name|s
operator|++
control|)
block|{
name|char
name|ch
init|=
literal|'2'
decl_stmt|;
try|try
block|{
name|ch
operator|=
name|pattern
operator|.
name|charAt
argument_list|(
literal|288
operator|*
name|d
operator|+
name|s
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IndexOutOfBoundsException
name|e
parameter_list|)
block|{
block|}
name|setOption
argument_list|(
name|d
argument_list|,
name|s
argument_list|,
name|char2id
argument_list|(
name|ch
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
annotation|@
name|Override
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
name|getPattern
argument_list|()
return|;
block|}
block|}
block|}
end_class

end_unit

