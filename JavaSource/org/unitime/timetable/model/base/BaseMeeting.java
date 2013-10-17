begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.2 - 3.5 (University Timetabling Application)  * Copyright (C) 2010 - 2013, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *  * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
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

begin_comment
comment|/**  * @author Tomas Muller, Stephanie Schluttenhofer  */
end_comment

begin_class
specifier|public
specifier|abstract
class|class
name|BaseMeeting
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
name|Date
name|iMeetingDate
decl_stmt|;
specifier|private
name|Integer
name|iStartPeriod
decl_stmt|;
specifier|private
name|Integer
name|iStartOffset
decl_stmt|;
specifier|private
name|Integer
name|iStopPeriod
decl_stmt|;
specifier|private
name|Integer
name|iStopOffset
decl_stmt|;
specifier|private
name|Long
name|iLocationPermanentId
decl_stmt|;
specifier|private
name|Boolean
name|iClassCanOverride
decl_stmt|;
specifier|private
name|Integer
name|iApprovalStatus
decl_stmt|;
specifier|private
name|Date
name|iApprovalDate
decl_stmt|;
specifier|private
name|Event
name|iEvent
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
name|PROP_MEETING_DATE
init|=
literal|"meetingDate"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_START_PERIOD
init|=
literal|"startPeriod"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_START_OFFSET
init|=
literal|"startOffset"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_STOP_PERIOD
init|=
literal|"stopPeriod"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_STOP_OFFSET
init|=
literal|"stopOffset"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_LOCATION_PERM_ID
init|=
literal|"locationPermanentId"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_CLASS_CAN_OVERRIDE
init|=
literal|"classCanOverride"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_APPROVAL_STATUS
init|=
literal|"approvalStatus"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_APPROVAL_DATE
init|=
literal|"approvalDate"
decl_stmt|;
specifier|public
name|BaseMeeting
parameter_list|()
block|{
name|initialize
argument_list|()
expr_stmt|;
block|}
specifier|public
name|BaseMeeting
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
name|Date
name|getMeetingDate
parameter_list|()
block|{
return|return
name|iMeetingDate
return|;
block|}
specifier|public
name|void
name|setMeetingDate
parameter_list|(
name|Date
name|meetingDate
parameter_list|)
block|{
name|iMeetingDate
operator|=
name|meetingDate
expr_stmt|;
block|}
specifier|public
name|Integer
name|getStartPeriod
parameter_list|()
block|{
return|return
name|iStartPeriod
return|;
block|}
specifier|public
name|void
name|setStartPeriod
parameter_list|(
name|Integer
name|startPeriod
parameter_list|)
block|{
name|iStartPeriod
operator|=
name|startPeriod
expr_stmt|;
block|}
specifier|public
name|Integer
name|getStartOffset
parameter_list|()
block|{
return|return
name|iStartOffset
return|;
block|}
specifier|public
name|void
name|setStartOffset
parameter_list|(
name|Integer
name|startOffset
parameter_list|)
block|{
name|iStartOffset
operator|=
name|startOffset
expr_stmt|;
block|}
specifier|public
name|Integer
name|getStopPeriod
parameter_list|()
block|{
return|return
name|iStopPeriod
return|;
block|}
specifier|public
name|void
name|setStopPeriod
parameter_list|(
name|Integer
name|stopPeriod
parameter_list|)
block|{
name|iStopPeriod
operator|=
name|stopPeriod
expr_stmt|;
block|}
specifier|public
name|Integer
name|getStopOffset
parameter_list|()
block|{
return|return
name|iStopOffset
return|;
block|}
specifier|public
name|void
name|setStopOffset
parameter_list|(
name|Integer
name|stopOffset
parameter_list|)
block|{
name|iStopOffset
operator|=
name|stopOffset
expr_stmt|;
block|}
specifier|public
name|Long
name|getLocationPermanentId
parameter_list|()
block|{
return|return
name|iLocationPermanentId
return|;
block|}
specifier|public
name|void
name|setLocationPermanentId
parameter_list|(
name|Long
name|locationPermanentId
parameter_list|)
block|{
name|iLocationPermanentId
operator|=
name|locationPermanentId
expr_stmt|;
block|}
specifier|public
name|Boolean
name|isClassCanOverride
parameter_list|()
block|{
return|return
name|iClassCanOverride
return|;
block|}
specifier|public
name|Boolean
name|getClassCanOverride
parameter_list|()
block|{
return|return
name|iClassCanOverride
return|;
block|}
specifier|public
name|void
name|setClassCanOverride
parameter_list|(
name|Boolean
name|classCanOverride
parameter_list|)
block|{
name|iClassCanOverride
operator|=
name|classCanOverride
expr_stmt|;
block|}
specifier|public
name|Integer
name|getApprovalStatus
parameter_list|()
block|{
return|return
name|iApprovalStatus
return|;
block|}
specifier|public
name|void
name|setApprovalStatus
parameter_list|(
name|Integer
name|approvalStatus
parameter_list|)
block|{
name|iApprovalStatus
operator|=
name|approvalStatus
expr_stmt|;
block|}
specifier|public
name|Date
name|getApprovalDate
parameter_list|()
block|{
return|return
name|iApprovalDate
return|;
block|}
specifier|public
name|void
name|setApprovalDate
parameter_list|(
name|Date
name|approvalDate
parameter_list|)
block|{
name|iApprovalDate
operator|=
name|approvalDate
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
name|Meeting
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
name|Meeting
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
name|Meeting
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
literal|"Meeting["
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
literal|"Meeting["
operator|+
literal|"\n	ApprovalDate: "
operator|+
name|getApprovalDate
argument_list|()
operator|+
literal|"\n	ApprovalStatus: "
operator|+
name|getApprovalStatus
argument_list|()
operator|+
literal|"\n	ClassCanOverride: "
operator|+
name|getClassCanOverride
argument_list|()
operator|+
literal|"\n	Event: "
operator|+
name|getEvent
argument_list|()
operator|+
literal|"\n	LocationPermanentId: "
operator|+
name|getLocationPermanentId
argument_list|()
operator|+
literal|"\n	MeetingDate: "
operator|+
name|getMeetingDate
argument_list|()
operator|+
literal|"\n	StartOffset: "
operator|+
name|getStartOffset
argument_list|()
operator|+
literal|"\n	StartPeriod: "
operator|+
name|getStartPeriod
argument_list|()
operator|+
literal|"\n	StopOffset: "
operator|+
name|getStopOffset
argument_list|()
operator|+
literal|"\n	StopPeriod: "
operator|+
name|getStopPeriod
argument_list|()
operator|+
literal|"\n	UniqueId: "
operator|+
name|getUniqueId
argument_list|()
operator|+
literal|"]"
return|;
block|}
block|}
end_class

end_unit

