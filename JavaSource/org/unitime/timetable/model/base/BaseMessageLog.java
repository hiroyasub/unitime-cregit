begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.2 (University Timetabling Application)  * Copyright (C) 2010, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *  * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
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
name|MessageLog
import|;
end_import

begin_class
specifier|public
specifier|abstract
class|class
name|BaseMessageLog
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
name|iTimeStamp
decl_stmt|;
specifier|private
name|Integer
name|iLevel
decl_stmt|;
specifier|private
name|String
name|iMessage
decl_stmt|;
specifier|private
name|String
name|iLogger
decl_stmt|;
specifier|private
name|String
name|iThread
decl_stmt|;
specifier|private
name|String
name|iNdc
decl_stmt|;
specifier|private
name|String
name|iException
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
name|PROP_TIME_STAMP
init|=
literal|"timeStamp"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_LOG_LEVEL
init|=
literal|"level"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_MESSAGE
init|=
literal|"message"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_LOGGER
init|=
literal|"logger"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_THREAD
init|=
literal|"thread"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_NDC
init|=
literal|"ndc"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_EXCEPTION
init|=
literal|"exception"
decl_stmt|;
specifier|public
name|BaseMessageLog
parameter_list|()
block|{
name|initialize
argument_list|()
expr_stmt|;
block|}
specifier|public
name|BaseMessageLog
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
name|Integer
name|getLevel
parameter_list|()
block|{
return|return
name|iLevel
return|;
block|}
specifier|public
name|void
name|setLevel
parameter_list|(
name|Integer
name|level
parameter_list|)
block|{
name|iLevel
operator|=
name|level
expr_stmt|;
block|}
specifier|public
name|String
name|getMessage
parameter_list|()
block|{
return|return
name|iMessage
return|;
block|}
specifier|public
name|void
name|setMessage
parameter_list|(
name|String
name|message
parameter_list|)
block|{
name|iMessage
operator|=
name|message
expr_stmt|;
block|}
specifier|public
name|String
name|getLogger
parameter_list|()
block|{
return|return
name|iLogger
return|;
block|}
specifier|public
name|void
name|setLogger
parameter_list|(
name|String
name|logger
parameter_list|)
block|{
name|iLogger
operator|=
name|logger
expr_stmt|;
block|}
specifier|public
name|String
name|getThread
parameter_list|()
block|{
return|return
name|iThread
return|;
block|}
specifier|public
name|void
name|setThread
parameter_list|(
name|String
name|thread
parameter_list|)
block|{
name|iThread
operator|=
name|thread
expr_stmt|;
block|}
specifier|public
name|String
name|getNdc
parameter_list|()
block|{
return|return
name|iNdc
return|;
block|}
specifier|public
name|void
name|setNdc
parameter_list|(
name|String
name|ndc
parameter_list|)
block|{
name|iNdc
operator|=
name|ndc
expr_stmt|;
block|}
specifier|public
name|String
name|getException
parameter_list|()
block|{
return|return
name|iException
return|;
block|}
specifier|public
name|void
name|setException
parameter_list|(
name|String
name|exception
parameter_list|)
block|{
name|iException
operator|=
name|exception
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
name|MessageLog
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
name|MessageLog
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
name|MessageLog
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
literal|"MessageLog["
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
literal|"MessageLog["
operator|+
literal|"\n	Exception: "
operator|+
name|getException
argument_list|()
operator|+
literal|"\n	Level: "
operator|+
name|getLevel
argument_list|()
operator|+
literal|"\n	Logger: "
operator|+
name|getLogger
argument_list|()
operator|+
literal|"\n	Message: "
operator|+
name|getMessage
argument_list|()
operator|+
literal|"\n	Ndc: "
operator|+
name|getNdc
argument_list|()
operator|+
literal|"\n	Thread: "
operator|+
name|getThread
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
literal|"]"
return|;
block|}
block|}
end_class

end_unit

