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
name|ClassWaitList
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
name|Class_
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
name|CourseRequest
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
name|Student
import|;
end_import

begin_class
specifier|public
specifier|abstract
class|class
name|BaseClassWaitList
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
name|iType
decl_stmt|;
specifier|private
name|Date
name|iTimestamp
decl_stmt|;
specifier|private
name|Student
name|iStudent
decl_stmt|;
specifier|private
name|CourseRequest
name|iCourseRequest
decl_stmt|;
specifier|private
name|Class_
name|iClazz
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
name|BaseClassWaitList
parameter_list|()
block|{
name|initialize
argument_list|()
expr_stmt|;
block|}
specifier|public
name|BaseClassWaitList
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
name|Student
name|getStudent
parameter_list|()
block|{
return|return
name|iStudent
return|;
block|}
specifier|public
name|void
name|setStudent
parameter_list|(
name|Student
name|student
parameter_list|)
block|{
name|iStudent
operator|=
name|student
expr_stmt|;
block|}
specifier|public
name|CourseRequest
name|getCourseRequest
parameter_list|()
block|{
return|return
name|iCourseRequest
return|;
block|}
specifier|public
name|void
name|setCourseRequest
parameter_list|(
name|CourseRequest
name|courseRequest
parameter_list|)
block|{
name|iCourseRequest
operator|=
name|courseRequest
expr_stmt|;
block|}
specifier|public
name|Class_
name|getClazz
parameter_list|()
block|{
return|return
name|iClazz
return|;
block|}
specifier|public
name|void
name|setClazz
parameter_list|(
name|Class_
name|clazz
parameter_list|)
block|{
name|iClazz
operator|=
name|clazz
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
name|ClassWaitList
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
name|ClassWaitList
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
name|ClassWaitList
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
literal|"ClassWaitList["
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
literal|"ClassWaitList["
operator|+
literal|"\n	Clazz: "
operator|+
name|getClazz
argument_list|()
operator|+
literal|"\n	CourseRequest: "
operator|+
name|getCourseRequest
argument_list|()
operator|+
literal|"\n	Student: "
operator|+
name|getStudent
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

