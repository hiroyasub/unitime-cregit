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
name|events
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
name|events
operator|.
name|EventMeetingTable
operator|.
name|EventMeetingRow
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
name|events
operator|.
name|EventMeetingTable
operator|.
name|MeetingFilter
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
name|events
operator|.
name|EventMeetingTable
operator|.
name|OperationType
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
name|widgets
operator|.
name|SimpleForm
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
name|widgets
operator|.
name|UniTimeDialogBox
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
name|widgets
operator|.
name|UniTimeFileUpload
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
name|widgets
operator|.
name|UniTimeHeaderPanel
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
name|ApproveEventRpcRequest
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
name|EventPropertiesRpcResponse
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
name|MeetingInterface
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
name|dom
operator|.
name|client
operator|.
name|Style
operator|.
name|Overflow
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
name|dom
operator|.
name|client
operator|.
name|ClickEvent
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
name|dom
operator|.
name|client
operator|.
name|ClickHandler
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
name|dom
operator|.
name|client
operator|.
name|DoubleClickEvent
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
name|dom
operator|.
name|client
operator|.
name|DoubleClickHandler
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
name|dom
operator|.
name|client
operator|.
name|KeyCodes
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
name|dom
operator|.
name|client
operator|.
name|KeyPressEvent
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
name|dom
operator|.
name|client
operator|.
name|KeyPressHandler
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
name|CloseEvent
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
name|CloseHandler
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
name|ListBox
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
name|PopupPanel
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
name|ScrollPanel
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
name|TextArea
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
name|Widget
import|;
end_import

begin_class
specifier|public
specifier|abstract
class|class
name|ApproveDialog
extends|extends
name|UniTimeDialogBox
implements|implements
name|EventMeetingTable
operator|.
name|Implementation
block|{
specifier|private
specifier|static
specifier|final
name|GwtMessages
name|MESSAGES
init|=
name|GWT
operator|.
name|create
argument_list|(
name|GwtMessages
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|private
name|SimpleForm
name|iForm
decl_stmt|;
specifier|private
name|TextArea
name|iNotes
decl_stmt|;
specifier|private
name|EventMeetingTable
name|iTable
decl_stmt|;
specifier|private
name|ListBox
name|iStandardNotes
decl_stmt|;
specifier|private
name|UniTimeFileUpload
name|iFileUpload
decl_stmt|;
specifier|private
name|UniTimeHeaderPanel
name|iFooter
decl_stmt|;
specifier|public
name|ApproveDialog
parameter_list|()
block|{
name|super
argument_list|(
literal|true
argument_list|,
literal|false
argument_list|)
expr_stmt|;
name|iTable
operator|=
operator|new
name|EventMeetingTable
argument_list|(
name|EventMeetingTable
operator|.
name|Mode
operator|.
name|ApprovalOfSingleEventMeetings
argument_list|,
literal|false
argument_list|)
expr_stmt|;
name|iForm
operator|=
operator|new
name|SimpleForm
argument_list|()
expr_stmt|;
if|if
condition|(
name|iTable
operator|instanceof
name|Widget
condition|)
block|{
name|ScrollPanel
name|scroll
init|=
operator|new
name|ScrollPanel
argument_list|(
operator|(
name|Widget
operator|)
name|iTable
argument_list|)
decl_stmt|;
name|scroll
operator|.
name|addStyleName
argument_list|(
literal|"scroll"
argument_list|)
expr_stmt|;
name|iForm
operator|.
name|addRow
argument_list|(
name|MESSAGES
operator|.
name|propMeetings
argument_list|()
argument_list|,
name|scroll
argument_list|)
expr_stmt|;
block|}
name|iStandardNotes
operator|=
operator|new
name|ListBox
argument_list|()
expr_stmt|;
name|iStandardNotes
operator|.
name|setVisibleItemCount
argument_list|(
literal|4
argument_list|)
expr_stmt|;
name|iStandardNotes
operator|.
name|setWidth
argument_list|(
literal|"480px"
argument_list|)
expr_stmt|;
name|iForm
operator|.
name|addRow
argument_list|(
name|MESSAGES
operator|.
name|propStandardNotes
argument_list|()
argument_list|,
name|iStandardNotes
argument_list|)
expr_stmt|;
name|iStandardNotes
operator|.
name|addDoubleClickHandler
argument_list|(
operator|new
name|DoubleClickHandler
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|onDoubleClick
parameter_list|(
name|DoubleClickEvent
name|event
parameter_list|)
block|{
if|if
condition|(
name|iStandardNotes
operator|.
name|getItemCount
argument_list|()
operator|<=
literal|0
condition|)
return|return;
name|String
name|text
init|=
name|iNotes
operator|.
name|getText
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
name|text
operator|.
name|isEmpty
argument_list|()
operator|&&
operator|!
name|text
operator|.
name|endsWith
argument_list|(
literal|"\n"
argument_list|)
condition|)
name|text
operator|+=
literal|"\n"
expr_stmt|;
name|text
operator|+=
name|iStandardNotes
operator|.
name|getItemText
argument_list|(
name|iStandardNotes
operator|.
name|getSelectedIndex
argument_list|()
argument_list|)
expr_stmt|;
name|iNotes
operator|.
name|setText
argument_list|(
name|text
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|iStandardNotes
operator|.
name|addKeyPressHandler
argument_list|(
operator|new
name|KeyPressHandler
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|onKeyPress
parameter_list|(
name|KeyPressEvent
name|event
parameter_list|)
block|{
if|if
condition|(
name|event
operator|.
name|getNativeEvent
argument_list|()
operator|.
name|getKeyCode
argument_list|()
operator|==
name|KeyCodes
operator|.
name|KEY_ENTER
condition|)
block|{
name|String
name|text
init|=
name|iNotes
operator|.
name|getText
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
name|text
operator|.
name|isEmpty
argument_list|()
operator|&&
operator|!
name|text
operator|.
name|endsWith
argument_list|(
literal|"\n"
argument_list|)
condition|)
name|text
operator|+=
literal|"\n"
expr_stmt|;
name|text
operator|+=
name|iStandardNotes
operator|.
name|getItemText
argument_list|(
name|iStandardNotes
operator|.
name|getSelectedIndex
argument_list|()
argument_list|)
expr_stmt|;
name|iNotes
operator|.
name|setText
argument_list|(
name|text
argument_list|)
expr_stmt|;
name|event
operator|.
name|preventDefault
argument_list|()
expr_stmt|;
name|event
operator|.
name|stopPropagation
argument_list|()
expr_stmt|;
block|}
block|}
block|}
argument_list|)
expr_stmt|;
name|iNotes
operator|=
operator|new
name|TextArea
argument_list|()
expr_stmt|;
name|iNotes
operator|.
name|setStyleName
argument_list|(
literal|"unitime-TextArea"
argument_list|)
expr_stmt|;
name|iNotes
operator|.
name|setVisibleLines
argument_list|(
literal|5
argument_list|)
expr_stmt|;
name|iNotes
operator|.
name|setCharacterWidth
argument_list|(
literal|80
argument_list|)
expr_stmt|;
name|iForm
operator|.
name|addRow
argument_list|(
name|MESSAGES
operator|.
name|propNotes
argument_list|()
argument_list|,
name|iNotes
argument_list|)
expr_stmt|;
name|iFileUpload
operator|=
operator|new
name|UniTimeFileUpload
argument_list|()
expr_stmt|;
name|iForm
operator|.
name|addRow
argument_list|(
name|MESSAGES
operator|.
name|propAttachment
argument_list|()
argument_list|,
name|iFileUpload
argument_list|)
expr_stmt|;
name|iFooter
operator|=
operator|new
name|UniTimeHeaderPanel
argument_list|()
expr_stmt|;
name|iFooter
operator|.
name|addButton
argument_list|(
literal|"approve"
argument_list|,
name|MESSAGES
operator|.
name|opApprove
argument_list|()
argument_list|,
operator|new
name|ClickHandler
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|onClick
parameter_list|(
name|ClickEvent
name|event
parameter_list|)
block|{
name|onSubmit
argument_list|(
name|ApproveEventRpcRequest
operator|.
name|Operation
operator|.
name|APPROVE
argument_list|,
name|iTable
operator|.
name|getValue
argument_list|()
argument_list|,
name|iNotes
operator|.
name|getText
argument_list|()
argument_list|)
expr_stmt|;
name|hide
argument_list|()
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|iFooter
operator|.
name|addButton
argument_list|(
literal|"inquire"
argument_list|,
name|MESSAGES
operator|.
name|opInquire
argument_list|()
argument_list|,
operator|new
name|ClickHandler
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|onClick
parameter_list|(
name|ClickEvent
name|event
parameter_list|)
block|{
name|onSubmit
argument_list|(
name|ApproveEventRpcRequest
operator|.
name|Operation
operator|.
name|INQUIRE
argument_list|,
name|iTable
operator|.
name|getValue
argument_list|()
argument_list|,
name|iNotes
operator|.
name|getText
argument_list|()
argument_list|)
expr_stmt|;
name|hide
argument_list|()
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|iFooter
operator|.
name|addButton
argument_list|(
literal|"reject"
argument_list|,
name|MESSAGES
operator|.
name|opReject
argument_list|()
argument_list|,
operator|new
name|ClickHandler
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|onClick
parameter_list|(
name|ClickEvent
name|event
parameter_list|)
block|{
name|onSubmit
argument_list|(
name|ApproveEventRpcRequest
operator|.
name|Operation
operator|.
name|REJECT
argument_list|,
name|iTable
operator|.
name|getValue
argument_list|()
argument_list|,
name|iNotes
operator|.
name|getText
argument_list|()
argument_list|)
expr_stmt|;
name|hide
argument_list|()
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|iFooter
operator|.
name|addButton
argument_list|(
literal|"cancel"
argument_list|,
name|MESSAGES
operator|.
name|opCancel
argument_list|()
argument_list|,
operator|new
name|ClickHandler
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|onClick
parameter_list|(
name|ClickEvent
name|event
parameter_list|)
block|{
name|onSubmit
argument_list|(
name|ApproveEventRpcRequest
operator|.
name|Operation
operator|.
name|CANCEL
argument_list|,
name|iTable
operator|.
name|getValue
argument_list|()
argument_list|,
name|iNotes
operator|.
name|getText
argument_list|()
argument_list|)
expr_stmt|;
name|hide
argument_list|()
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|iFooter
operator|.
name|addButton
argument_list|(
literal|"back"
argument_list|,
name|MESSAGES
operator|.
name|opBack
argument_list|()
argument_list|,
operator|new
name|ClickHandler
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|onClick
parameter_list|(
name|ClickEvent
name|event
parameter_list|)
block|{
name|hide
argument_list|()
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|iForm
operator|.
name|addBottomRow
argument_list|(
name|iFooter
argument_list|)
expr_stmt|;
name|addCloseHandler
argument_list|(
operator|new
name|CloseHandler
argument_list|<
name|PopupPanel
argument_list|>
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|onClose
parameter_list|(
name|CloseEvent
argument_list|<
name|PopupPanel
argument_list|>
name|event
parameter_list|)
block|{
name|RootPanel
operator|.
name|getBodyElement
argument_list|()
operator|.
name|getStyle
argument_list|()
operator|.
name|setOverflow
argument_list|(
name|Overflow
operator|.
name|AUTO
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|setWidget
argument_list|(
name|iForm
argument_list|)
expr_stmt|;
block|}
specifier|protected
specifier|abstract
name|void
name|onSubmit
parameter_list|(
name|ApproveEventRpcRequest
operator|.
name|Operation
name|operation
parameter_list|,
name|List
argument_list|<
name|EventMeetingRow
argument_list|>
name|items
parameter_list|,
name|String
name|message
parameter_list|)
function_decl|;
specifier|public
name|void
name|reset
parameter_list|(
name|EventPropertiesRpcResponse
name|properties
parameter_list|)
block|{
name|iNotes
operator|.
name|setText
argument_list|(
literal|""
argument_list|)
expr_stmt|;
name|iStandardNotes
operator|.
name|clear
argument_list|()
expr_stmt|;
name|iForm
operator|.
name|getRowFormatter
argument_list|()
operator|.
name|setVisible
argument_list|(
name|iForm
operator|.
name|getRow
argument_list|(
name|MESSAGES
operator|.
name|propStandardNotes
argument_list|()
argument_list|)
argument_list|,
name|properties
operator|!=
literal|null
operator|&&
name|properties
operator|.
name|hasStandardNotes
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|properties
operator|!=
literal|null
operator|&&
name|properties
operator|.
name|hasStandardNotes
argument_list|()
condition|)
block|{
for|for
control|(
name|String
name|note
range|:
name|properties
operator|.
name|getStandardNotes
argument_list|()
control|)
name|iStandardNotes
operator|.
name|addItem
argument_list|(
name|note
argument_list|)
expr_stmt|;
block|}
name|iFileUpload
operator|.
name|reset
argument_list|()
expr_stmt|;
block|}
specifier|public
name|void
name|show
parameter_list|(
name|List
argument_list|<
name|EventMeetingRow
argument_list|>
name|meetings
parameter_list|,
name|ApproveEventRpcRequest
operator|.
name|Operation
name|operation
parameter_list|)
block|{
name|iTable
operator|.
name|setValue
argument_list|(
name|meetings
argument_list|)
expr_stmt|;
switch|switch
condition|(
name|operation
condition|)
block|{
case|case
name|APPROVE
case|:
name|setText
argument_list|(
name|MESSAGES
operator|.
name|dialogApprove
argument_list|()
argument_list|)
expr_stmt|;
case|case
name|REJECT
case|:
name|setText
argument_list|(
name|MESSAGES
operator|.
name|dialogReject
argument_list|()
argument_list|)
expr_stmt|;
case|case
name|INQUIRE
case|:
name|setText
argument_list|(
name|MESSAGES
operator|.
name|dialogInquire
argument_list|()
argument_list|)
expr_stmt|;
case|case
name|CANCEL
case|:
name|setText
argument_list|(
name|MESSAGES
operator|.
name|dialogCancel
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|iFooter
operator|.
name|setEnabled
argument_list|(
literal|"approve"
argument_list|,
name|operation
operator|==
name|ApproveEventRpcRequest
operator|.
name|Operation
operator|.
name|APPROVE
argument_list|)
expr_stmt|;
name|iFooter
operator|.
name|setEnabled
argument_list|(
literal|"reject"
argument_list|,
name|operation
operator|==
name|ApproveEventRpcRequest
operator|.
name|Operation
operator|.
name|REJECT
argument_list|)
expr_stmt|;
name|iFooter
operator|.
name|setEnabled
argument_list|(
literal|"inquire"
argument_list|,
name|operation
operator|==
name|ApproveEventRpcRequest
operator|.
name|Operation
operator|.
name|INQUIRE
argument_list|)
expr_stmt|;
name|iFooter
operator|.
name|setEnabled
argument_list|(
literal|"cancel"
argument_list|,
name|operation
operator|==
name|ApproveEventRpcRequest
operator|.
name|Operation
operator|.
name|CANCEL
argument_list|)
expr_stmt|;
name|iFileUpload
operator|.
name|check
argument_list|()
expr_stmt|;
name|center
argument_list|()
expr_stmt|;
if|if
condition|(
name|iStandardNotes
operator|.
name|getItemCount
argument_list|()
operator|==
literal|0
condition|)
name|iNotes
operator|.
name|setFocus
argument_list|(
literal|true
argument_list|)
expr_stmt|;
else|else
name|iStandardNotes
operator|.
name|setFocus
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|RootPanel
operator|.
name|getBodyElement
argument_list|()
operator|.
name|getStyle
argument_list|()
operator|.
name|setOverflow
argument_list|(
name|Overflow
operator|.
name|HIDDEN
argument_list|)
expr_stmt|;
block|}
specifier|public
name|String
name|getNote
parameter_list|()
block|{
return|return
name|iNotes
operator|.
name|getText
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|execute
parameter_list|(
specifier|final
name|EventMeetingTable
name|source
parameter_list|,
name|OperationType
name|operation
parameter_list|,
name|List
argument_list|<
name|EventMeetingRow
argument_list|>
name|selection
parameter_list|)
block|{
switch|switch
condition|(
name|source
operator|.
name|getMode
argument_list|()
condition|)
block|{
case|case
name|ListOfEvents
case|:
name|iTable
operator|.
name|setMode
argument_list|(
name|EventMeetingTable
operator|.
name|Mode
operator|.
name|ApprovalOfEvents
argument_list|)
expr_stmt|;
break|break;
case|case
name|ListOfMeetings
case|:
name|iTable
operator|.
name|setMode
argument_list|(
name|EventMeetingTable
operator|.
name|Mode
operator|.
name|ApprovalOfMeetings
argument_list|)
expr_stmt|;
break|break;
case|case
name|MeetingsOfAnEvent
case|:
name|iTable
operator|.
name|setMode
argument_list|(
name|EventMeetingTable
operator|.
name|Mode
operator|.
name|ApprovalOfSingleEventMeetings
argument_list|)
expr_stmt|;
break|break;
block|}
switch|switch
condition|(
name|operation
condition|)
block|{
case|case
name|Approve
case|:
case|case
name|Reject
case|:
name|iTable
operator|.
name|setMeetingFilter
argument_list|(
operator|new
name|MeetingFilter
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|boolean
name|filter
parameter_list|(
name|EventInterface
name|event
parameter_list|,
name|MeetingInterface
name|meeting
parameter_list|)
block|{
return|return
operator|!
name|meeting
operator|.
name|isCanApprove
argument_list|()
operator|||
operator|(
name|source
operator|.
name|getMeetingFilter
argument_list|()
operator|!=
literal|null
operator|&&
name|source
operator|.
name|getMeetingFilter
argument_list|()
operator|.
name|filter
argument_list|(
name|event
argument_list|,
name|meeting
argument_list|)
operator|)
return|;
block|}
block|}
argument_list|)
expr_stmt|;
break|break;
case|case
name|Inquire
case|:
name|iTable
operator|.
name|setMeetingFilter
argument_list|(
operator|new
name|MeetingFilter
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|boolean
name|filter
parameter_list|(
name|EventInterface
name|event
parameter_list|,
name|MeetingInterface
name|meeting
parameter_list|)
block|{
return|return
operator|!
name|meeting
operator|.
name|isCanInquire
argument_list|()
operator|||
operator|(
name|source
operator|.
name|getMeetingFilter
argument_list|()
operator|!=
literal|null
operator|&&
name|source
operator|.
name|getMeetingFilter
argument_list|()
operator|.
name|filter
argument_list|(
name|event
argument_list|,
name|meeting
argument_list|)
operator|)
return|;
block|}
block|}
argument_list|)
expr_stmt|;
break|break;
case|case
name|Cancel
case|:
name|iTable
operator|.
name|setMeetingFilter
argument_list|(
operator|new
name|MeetingFilter
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|boolean
name|filter
parameter_list|(
name|EventInterface
name|event
parameter_list|,
name|MeetingInterface
name|meeting
parameter_list|)
block|{
return|return
operator|!
name|meeting
operator|.
name|isCanCancel
argument_list|()
operator|||
operator|(
name|source
operator|.
name|getMeetingFilter
argument_list|()
operator|!=
literal|null
operator|&&
name|source
operator|.
name|getMeetingFilter
argument_list|()
operator|.
name|filter
argument_list|(
name|event
argument_list|,
name|meeting
argument_list|)
operator|)
return|;
block|}
block|}
argument_list|)
expr_stmt|;
break|break;
block|}
switch|switch
condition|(
name|operation
condition|)
block|{
case|case
name|Approve
case|:
name|show
argument_list|(
name|selection
argument_list|,
name|ApproveEventRpcRequest
operator|.
name|Operation
operator|.
name|APPROVE
argument_list|)
expr_stmt|;
break|break;
case|case
name|Reject
case|:
name|show
argument_list|(
name|selection
argument_list|,
name|ApproveEventRpcRequest
operator|.
name|Operation
operator|.
name|REJECT
argument_list|)
expr_stmt|;
break|break;
case|case
name|Cancel
case|:
name|show
argument_list|(
name|selection
argument_list|,
name|ApproveEventRpcRequest
operator|.
name|Operation
operator|.
name|CANCEL
argument_list|)
expr_stmt|;
break|break;
case|case
name|Inquire
case|:
name|show
argument_list|(
name|selection
argument_list|,
name|ApproveEventRpcRequest
operator|.
name|Operation
operator|.
name|INQUIRE
argument_list|)
expr_stmt|;
break|break;
block|}
block|}
block|}
end_class

end_unit

