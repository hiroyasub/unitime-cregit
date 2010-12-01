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
name|CourseDemand
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
name|StudentEnrollmentMessage
import|;
end_import

begin_class
specifier|public
specifier|abstract
class|class
name|BaseStudentEnrollmentMessage
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
name|String
name|iMessage
decl_stmt|;
specifier|private
name|Integer
name|iLevel
decl_stmt|;
specifier|private
name|Integer
name|iType
decl_stmt|;
specifier|private
name|Date
name|iTimestamp
decl_stmt|;
specifier|private
name|Integer
name|iOrder
decl_stmt|;
specifier|private
name|CourseDemand
name|iCourseDemand
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
name|PROP_MESSAGE
init|=
literal|"message"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_MSG_LEVEL
init|=
literal|"level"
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
name|PROP_TIMESTAMP
init|=
literal|"timestamp"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_ORD
init|=
literal|"order"
decl_stmt|;
specifier|public
name|BaseStudentEnrollmentMessage
parameter_list|()
block|{
name|initialize
argument_list|()
expr_stmt|;
block|}
specifier|public
name|BaseStudentEnrollmentMessage
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
name|Date
name|getTimestamp
parameter_list|()
block|{
return|return
name|iTimestamp
return|;
block|}
specifier|public
name|void
name|setTimestamp
parameter_list|(
name|Date
name|timestamp
parameter_list|)
block|{
name|iTimestamp
operator|=
name|timestamp
expr_stmt|;
block|}
specifier|public
name|Integer
name|getOrder
parameter_list|()
block|{
return|return
name|iOrder
return|;
block|}
specifier|public
name|void
name|setOrder
parameter_list|(
name|Integer
name|order
parameter_list|)
block|{
name|iOrder
operator|=
name|order
expr_stmt|;
block|}
specifier|public
name|CourseDemand
name|getCourseDemand
parameter_list|()
block|{
return|return
name|iCourseDemand
return|;
block|}
specifier|public
name|void
name|setCourseDemand
parameter_list|(
name|CourseDemand
name|courseDemand
parameter_list|)
block|{
name|iCourseDemand
operator|=
name|courseDemand
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
name|StudentEnrollmentMessage
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
name|StudentEnrollmentMessage
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
name|StudentEnrollmentMessage
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
literal|"StudentEnrollmentMessage["
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
literal|"StudentEnrollmentMessage["
operator|+
literal|"\n	CourseDemand: "
operator|+
name|getCourseDemand
argument_list|()
operator|+
literal|"\n	Level: "
operator|+
name|getLevel
argument_list|()
operator|+
literal|"\n	Message: "
operator|+
name|getMessage
argument_list|()
operator|+
literal|"\n	Order: "
operator|+
name|getOrder
argument_list|()
operator|+
literal|"\n	Timestamp: "
operator|+
name|getTimestamp
argument_list|()
operator|+
literal|"\n	Type: "
operator|+
name|getType
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

