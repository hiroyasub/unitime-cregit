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
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|ManagerRole
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
name|Roles
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
name|TimetableManager
import|;
end_import

begin_class
specifier|public
specifier|abstract
class|class
name|BaseManagerRole
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
name|Boolean
name|iPrimary
decl_stmt|;
specifier|private
name|Boolean
name|iReceiveEmails
decl_stmt|;
specifier|private
name|Roles
name|iRole
decl_stmt|;
specifier|private
name|TimetableManager
name|iTimetableManager
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
name|PROP_IS_PRIMARY
init|=
literal|"primary"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_RECEIVE_EMAILS
init|=
literal|"receiveEmails"
decl_stmt|;
specifier|public
name|BaseManagerRole
parameter_list|()
block|{
name|initialize
argument_list|()
expr_stmt|;
block|}
specifier|public
name|BaseManagerRole
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
name|Boolean
name|isPrimary
parameter_list|()
block|{
return|return
name|iPrimary
return|;
block|}
specifier|public
name|Boolean
name|getPrimary
parameter_list|()
block|{
return|return
name|iPrimary
return|;
block|}
specifier|public
name|void
name|setPrimary
parameter_list|(
name|Boolean
name|primary
parameter_list|)
block|{
name|iPrimary
operator|=
name|primary
expr_stmt|;
block|}
specifier|public
name|Boolean
name|isReceiveEmails
parameter_list|()
block|{
return|return
name|iReceiveEmails
return|;
block|}
specifier|public
name|Boolean
name|getReceiveEmails
parameter_list|()
block|{
return|return
name|iReceiveEmails
return|;
block|}
specifier|public
name|void
name|setReceiveEmails
parameter_list|(
name|Boolean
name|receiveEmails
parameter_list|)
block|{
name|iReceiveEmails
operator|=
name|receiveEmails
expr_stmt|;
block|}
specifier|public
name|Roles
name|getRole
parameter_list|()
block|{
return|return
name|iRole
return|;
block|}
specifier|public
name|void
name|setRole
parameter_list|(
name|Roles
name|role
parameter_list|)
block|{
name|iRole
operator|=
name|role
expr_stmt|;
block|}
specifier|public
name|TimetableManager
name|getTimetableManager
parameter_list|()
block|{
return|return
name|iTimetableManager
return|;
block|}
specifier|public
name|void
name|setTimetableManager
parameter_list|(
name|TimetableManager
name|timetableManager
parameter_list|)
block|{
name|iTimetableManager
operator|=
name|timetableManager
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
name|ManagerRole
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
name|ManagerRole
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
name|ManagerRole
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
literal|"ManagerRole["
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
literal|"ManagerRole["
operator|+
literal|"\n	Primary: "
operator|+
name|getPrimary
argument_list|()
operator|+
literal|"\n	ReceiveEmails: "
operator|+
name|getReceiveEmails
argument_list|()
operator|+
literal|"\n	Role: "
operator|+
name|getRole
argument_list|()
operator|+
literal|"\n	TimetableManager: "
operator|+
name|getTimetableManager
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

