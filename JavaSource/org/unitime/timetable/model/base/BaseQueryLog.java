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
name|QueryLog
import|;
end_import

begin_class
specifier|public
specifier|abstract
class|class
name|BaseQueryLog
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
name|Long
name|iTimeSpent
decl_stmt|;
specifier|private
name|String
name|iUri
decl_stmt|;
specifier|private
name|Integer
name|iType
decl_stmt|;
specifier|private
name|String
name|iSessionId
decl_stmt|;
specifier|private
name|String
name|iUid
decl_stmt|;
specifier|private
name|String
name|iQuery
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
name|PROP_TIME_SPENT
init|=
literal|"timeSpent"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_URI
init|=
literal|"uri"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_TYPE
init|=
literal|"type"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_SESSION_ID
init|=
literal|"sessionId"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_USERID
init|=
literal|"uid"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_QUERY
init|=
literal|"query"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_EXCEPTION
init|=
literal|"exception"
decl_stmt|;
specifier|public
name|BaseQueryLog
parameter_list|()
block|{
name|initialize
argument_list|()
expr_stmt|;
block|}
specifier|public
name|BaseQueryLog
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
name|Long
name|getTimeSpent
parameter_list|()
block|{
return|return
name|iTimeSpent
return|;
block|}
specifier|public
name|void
name|setTimeSpent
parameter_list|(
name|Long
name|timeSpent
parameter_list|)
block|{
name|iTimeSpent
operator|=
name|timeSpent
expr_stmt|;
block|}
specifier|public
name|String
name|getUri
parameter_list|()
block|{
return|return
name|iUri
return|;
block|}
specifier|public
name|void
name|setUri
parameter_list|(
name|String
name|uri
parameter_list|)
block|{
name|iUri
operator|=
name|uri
expr_stmt|;
block|}
specifier|public
name|Integer
name|getType
parameter_list|()
block|{
return|return
name|iType
return|;
block|}
specifier|public
name|void
name|setType
parameter_list|(
name|Integer
name|type
parameter_list|)
block|{
name|iType
operator|=
name|type
expr_stmt|;
block|}
specifier|public
name|String
name|getSessionId
parameter_list|()
block|{
return|return
name|iSessionId
return|;
block|}
specifier|public
name|void
name|setSessionId
parameter_list|(
name|String
name|sessionId
parameter_list|)
block|{
name|iSessionId
operator|=
name|sessionId
expr_stmt|;
block|}
specifier|public
name|String
name|getUid
parameter_list|()
block|{
return|return
name|iUid
return|;
block|}
specifier|public
name|void
name|setUid
parameter_list|(
name|String
name|uid
parameter_list|)
block|{
name|iUid
operator|=
name|uid
expr_stmt|;
block|}
specifier|public
name|String
name|getQuery
parameter_list|()
block|{
return|return
name|iQuery
return|;
block|}
specifier|public
name|void
name|setQuery
parameter_list|(
name|String
name|query
parameter_list|)
block|{
name|iQuery
operator|=
name|query
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
name|QueryLog
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
name|QueryLog
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
name|QueryLog
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
literal|"QueryLog["
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
literal|"QueryLog["
operator|+
literal|"\n	Exception: "
operator|+
name|getException
argument_list|()
operator|+
literal|"\n	Query: "
operator|+
name|getQuery
argument_list|()
operator|+
literal|"\n	SessionId: "
operator|+
name|getSessionId
argument_list|()
operator|+
literal|"\n	TimeSpent: "
operator|+
name|getTimeSpent
argument_list|()
operator|+
literal|"\n	TimeStamp: "
operator|+
name|getTimeStamp
argument_list|()
operator|+
literal|"\n	Type: "
operator|+
name|getType
argument_list|()
operator|+
literal|"\n	Uid: "
operator|+
name|getUid
argument_list|()
operator|+
literal|"\n	UniqueId: "
operator|+
name|getUniqueId
argument_list|()
operator|+
literal|"\n	Uri: "
operator|+
name|getUri
argument_list|()
operator|+
literal|"]"
return|;
block|}
block|}
end_class

end_unit

