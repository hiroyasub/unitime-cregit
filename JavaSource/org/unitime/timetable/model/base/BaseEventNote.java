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
name|model
operator|.
name|base
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|Serializable
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
name|HashSet
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
name|EventNote
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

begin_comment
comment|/**  * Do not change this class. It has been automatically generated using ant create-model.  * @see org.unitime.commons.ant.CreateBaseModelFromXml  */
end_comment

begin_class
specifier|public
specifier|abstract
class|class
name|BaseEventNote
implements|implements
name|Serializable
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
name|Long
name|iUniqueId
decl_stmt|;
specifier|private
name|Integer
name|iNoteType
decl_stmt|;
specifier|private
name|String
name|iTextNote
decl_stmt|;
specifier|private
name|Date
name|iTimeStamp
decl_stmt|;
specifier|private
name|String
name|iUser
decl_stmt|;
specifier|private
name|String
name|iUserId
decl_stmt|;
specifier|private
name|String
name|iMeetings
decl_stmt|;
specifier|private
name|byte
index|[]
name|iAttachedFile
decl_stmt|;
specifier|private
name|String
name|iAttachedName
decl_stmt|;
specifier|private
name|String
name|iAttachedContentType
decl_stmt|;
specifier|private
name|Event
name|iEvent
decl_stmt|;
specifier|private
name|Set
argument_list|<
name|Meeting
argument_list|>
name|iAffectedMeetings
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_UNIQUEID
init|=
literal|"uniqueId"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_NOTE_TYPE
init|=
literal|"noteType"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_TEXT_NOTE
init|=
literal|"textNote"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_TIME_STAMP
init|=
literal|"timeStamp"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_UNAME
init|=
literal|"user"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_USER_ID
init|=
literal|"userId"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_MEETINGS
init|=
literal|"meetings"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_ATTACHED_FILE
init|=
literal|"attachedFile"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_ATTACHED_NAME
init|=
literal|"attachedName"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_ATTACHED_CONTENT
init|=
literal|"attachedContentType"
decl_stmt|;
specifier|public
name|BaseEventNote
parameter_list|()
block|{
name|initialize
argument_list|()
expr_stmt|;
block|}
specifier|public
name|BaseEventNote
parameter_list|(
name|Long
name|uniqueId
parameter_list|)
block|{
name|setUniqueId
argument_list|(
name|uniqueId
argument_list|)
expr_stmt|;
name|initialize
argument_list|()
expr_stmt|;
block|}
specifier|protected
name|void
name|initialize
parameter_list|()
block|{
block|}
specifier|public
name|Long
name|getUniqueId
parameter_list|()
block|{
return|return
name|iUniqueId
return|;
block|}
specifier|public
name|void
name|setUniqueId
parameter_list|(
name|Long
name|uniqueId
parameter_list|)
block|{
name|iUniqueId
operator|=
name|uniqueId
expr_stmt|;
block|}
specifier|public
name|Integer
name|getNoteType
parameter_list|()
block|{
return|return
name|iNoteType
return|;
block|}
specifier|public
name|void
name|setNoteType
parameter_list|(
name|Integer
name|noteType
parameter_list|)
block|{
name|iNoteType
operator|=
name|noteType
expr_stmt|;
block|}
specifier|public
name|String
name|getTextNote
parameter_list|()
block|{
return|return
name|iTextNote
return|;
block|}
specifier|public
name|void
name|setTextNote
parameter_list|(
name|String
name|textNote
parameter_list|)
block|{
name|iTextNote
operator|=
name|textNote
expr_stmt|;
block|}
specifier|public
name|Date
name|getTimeStamp
parameter_list|()
block|{
return|return
name|iTimeStamp
return|;
block|}
specifier|public
name|void
name|setTimeStamp
parameter_list|(
name|Date
name|timeStamp
parameter_list|)
block|{
name|iTimeStamp
operator|=
name|timeStamp
expr_stmt|;
block|}
specifier|public
name|String
name|getUser
parameter_list|()
block|{
return|return
name|iUser
return|;
block|}
specifier|public
name|void
name|setUser
parameter_list|(
name|String
name|user
parameter_list|)
block|{
name|iUser
operator|=
name|user
expr_stmt|;
block|}
specifier|public
name|String
name|getUserId
parameter_list|()
block|{
return|return
name|iUserId
return|;
block|}
specifier|public
name|void
name|setUserId
parameter_list|(
name|String
name|userId
parameter_list|)
block|{
name|iUserId
operator|=
name|userId
expr_stmt|;
block|}
specifier|public
name|String
name|getMeetings
parameter_list|()
block|{
return|return
name|iMeetings
return|;
block|}
specifier|public
name|void
name|setMeetings
parameter_list|(
name|String
name|meetings
parameter_list|)
block|{
name|iMeetings
operator|=
name|meetings
expr_stmt|;
block|}
specifier|public
name|byte
index|[]
name|getAttachedFile
parameter_list|()
block|{
return|return
name|iAttachedFile
return|;
block|}
specifier|public
name|void
name|setAttachedFile
parameter_list|(
name|byte
index|[]
name|attachedFile
parameter_list|)
block|{
name|iAttachedFile
operator|=
name|attachedFile
expr_stmt|;
block|}
specifier|public
name|String
name|getAttachedName
parameter_list|()
block|{
return|return
name|iAttachedName
return|;
block|}
specifier|public
name|void
name|setAttachedName
parameter_list|(
name|String
name|attachedName
parameter_list|)
block|{
name|iAttachedName
operator|=
name|attachedName
expr_stmt|;
block|}
specifier|public
name|String
name|getAttachedContentType
parameter_list|()
block|{
return|return
name|iAttachedContentType
return|;
block|}
specifier|public
name|void
name|setAttachedContentType
parameter_list|(
name|String
name|attachedContentType
parameter_list|)
block|{
name|iAttachedContentType
operator|=
name|attachedContentType
expr_stmt|;
block|}
specifier|public
name|Event
name|getEvent
parameter_list|()
block|{
return|return
name|iEvent
return|;
block|}
specifier|public
name|void
name|setEvent
parameter_list|(
name|Event
name|event
parameter_list|)
block|{
name|iEvent
operator|=
name|event
expr_stmt|;
block|}
specifier|public
name|Set
argument_list|<
name|Meeting
argument_list|>
name|getAffectedMeetings
parameter_list|()
block|{
return|return
name|iAffectedMeetings
return|;
block|}
specifier|public
name|void
name|setAffectedMeetings
parameter_list|(
name|Set
argument_list|<
name|Meeting
argument_list|>
name|affectedMeetings
parameter_list|)
block|{
name|iAffectedMeetings
operator|=
name|affectedMeetings
expr_stmt|;
block|}
specifier|public
name|void
name|addToaffectedMeetings
parameter_list|(
name|Meeting
name|meeting
parameter_list|)
block|{
if|if
condition|(
name|iAffectedMeetings
operator|==
literal|null
condition|)
name|iAffectedMeetings
operator|=
operator|new
name|HashSet
argument_list|<
name|Meeting
argument_list|>
argument_list|()
expr_stmt|;
name|iAffectedMeetings
operator|.
name|add
argument_list|(
name|meeting
argument_list|)
expr_stmt|;
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
name|EventNote
operator|)
condition|)
return|return
literal|false
return|;
if|if
condition|(
name|getUniqueId
argument_list|()
operator|==
literal|null
operator|||
operator|(
operator|(
name|EventNote
operator|)
name|o
operator|)
operator|.
name|getUniqueId
argument_list|()
operator|==
literal|null
condition|)
return|return
literal|false
return|;
return|return
name|getUniqueId
argument_list|()
operator|.
name|equals
argument_list|(
operator|(
operator|(
name|EventNote
operator|)
name|o
operator|)
operator|.
name|getUniqueId
argument_list|()
argument_list|)
return|;
block|}
specifier|public
name|int
name|hashCode
parameter_list|()
block|{
if|if
condition|(
name|getUniqueId
argument_list|()
operator|==
literal|null
condition|)
return|return
name|super
operator|.
name|hashCode
argument_list|()
return|;
return|return
name|getUniqueId
argument_list|()
operator|.
name|hashCode
argument_list|()
return|;
block|}
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
literal|"EventNote["
operator|+
name|getUniqueId
argument_list|()
operator|+
literal|"]"
return|;
block|}
specifier|public
name|String
name|toDebugString
parameter_list|()
block|{
return|return
literal|"EventNote["
operator|+
literal|"\n	AttachedContentType: "
operator|+
name|getAttachedContentType
argument_list|()
operator|+
literal|"\n	AttachedFile: "
operator|+
name|getAttachedFile
argument_list|()
operator|+
literal|"\n	AttachedName: "
operator|+
name|getAttachedName
argument_list|()
operator|+
literal|"\n	Event: "
operator|+
name|getEvent
argument_list|()
operator|+
literal|"\n	Meetings: "
operator|+
name|getMeetings
argument_list|()
operator|+
literal|"\n	NoteType: "
operator|+
name|getNoteType
argument_list|()
operator|+
literal|"\n	TextNote: "
operator|+
name|getTextNote
argument_list|()
operator|+
literal|"\n	TimeStamp: "
operator|+
name|getTimeStamp
argument_list|()
operator|+
literal|"\n	UniqueId: "
operator|+
name|getUniqueId
argument_list|()
operator|+
literal|"\n	User: "
operator|+
name|getUser
argument_list|()
operator|+
literal|"\n	UserId: "
operator|+
name|getUserId
argument_list|()
operator|+
literal|"]"
return|;
block|}
block|}
end_class

end_unit

