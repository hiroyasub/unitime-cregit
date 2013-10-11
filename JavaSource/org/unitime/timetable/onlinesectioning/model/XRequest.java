begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.5 (University Timetabling Application)  * Copyright (C) 2013, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
end_comment

begin_package
package|package
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|onlinesectioning
operator|.
name|model
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|Externalizable
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|IOException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|ObjectInput
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|ObjectOutput
import|;
end_import

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
name|net
operator|.
name|sf
operator|.
name|cpsolver
operator|.
name|studentsct
operator|.
name|model
operator|.
name|Request
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

begin_class
specifier|public
specifier|abstract
class|class
name|XRequest
implements|implements
name|Serializable
implements|,
name|Comparable
argument_list|<
name|XRequest
argument_list|>
implements|,
name|Externalizable
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|1L
decl_stmt|;
specifier|protected
name|Long
name|iRequestId
init|=
literal|null
decl_stmt|;
specifier|protected
name|int
name|iPriority
init|=
literal|0
decl_stmt|;
specifier|protected
name|boolean
name|iAlternative
init|=
literal|false
decl_stmt|;
specifier|protected
name|Long
name|iStudentId
decl_stmt|;
specifier|public
name|XRequest
parameter_list|()
block|{
block|}
specifier|public
name|XRequest
parameter_list|(
name|CourseDemand
name|demand
parameter_list|)
block|{
name|iRequestId
operator|=
name|demand
operator|.
name|getUniqueId
argument_list|()
expr_stmt|;
name|iPriority
operator|=
name|demand
operator|.
name|getPriority
argument_list|()
expr_stmt|;
name|iAlternative
operator|=
name|demand
operator|.
name|isAlternative
argument_list|()
expr_stmt|;
name|iStudentId
operator|=
name|demand
operator|.
name|getStudent
argument_list|()
operator|.
name|getUniqueId
argument_list|()
expr_stmt|;
block|}
specifier|public
name|XRequest
parameter_list|(
name|Request
name|request
parameter_list|)
block|{
name|iRequestId
operator|=
name|request
operator|.
name|getId
argument_list|()
expr_stmt|;
name|iPriority
operator|=
name|request
operator|.
name|getPriority
argument_list|()
expr_stmt|;
name|iAlternative
operator|=
name|request
operator|.
name|isAlternative
argument_list|()
expr_stmt|;
name|iStudentId
operator|=
name|request
operator|.
name|getStudent
argument_list|()
operator|.
name|getId
argument_list|()
expr_stmt|;
block|}
comment|/** Request id */
specifier|public
name|Long
name|getRequestId
parameter_list|()
block|{
return|return
name|iRequestId
return|;
block|}
comment|/**      * Request priority -- if there is a choice, request with lower priority is      * more preferred to be assigned      */
specifier|public
name|int
name|getPriority
parameter_list|()
block|{
return|return
name|iPriority
return|;
block|}
comment|/**      * True, if the request is alternative (alternative request can be assigned      * instead of a non-alternative course requests, if it is left unassigned)      */
specifier|public
name|boolean
name|isAlternative
parameter_list|()
block|{
return|return
name|iAlternative
return|;
block|}
comment|/** Student to which this request belongs */
specifier|public
name|Long
name|getStudentId
parameter_list|()
block|{
return|return
name|iStudentId
return|;
block|}
annotation|@
name|Override
specifier|public
name|int
name|hashCode
parameter_list|()
block|{
return|return
operator|(
name|int
operator|)
operator|(
name|getRequestId
argument_list|()
operator|^
operator|(
name|getRequestId
argument_list|()
operator|>>>
literal|32
operator|)
operator|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|int
name|compareTo
parameter_list|(
name|XRequest
name|request
parameter_list|)
block|{
return|return
operator|(
name|isAlternative
argument_list|()
operator|!=
name|request
operator|.
name|isAlternative
argument_list|()
condition|?
name|isAlternative
argument_list|()
condition|?
literal|1
else|:
operator|-
literal|1
else|:
name|getPriority
argument_list|()
operator|<
name|request
operator|.
name|getPriority
argument_list|()
condition|?
operator|-
literal|1
else|:
literal|1
operator|)
return|;
block|}
annotation|@
name|Override
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
name|XRequest
operator|)
condition|)
return|return
literal|false
return|;
return|return
name|getRequestId
argument_list|()
operator|.
name|equals
argument_list|(
operator|(
operator|(
name|XRequest
operator|)
name|o
operator|)
operator|.
name|getRequestId
argument_list|()
argument_list|)
operator|&&
name|getStudentId
argument_list|()
operator|.
name|equals
argument_list|(
operator|(
operator|(
name|XRequest
operator|)
name|o
operator|)
operator|.
name|getStudentId
argument_list|()
argument_list|)
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
operator|(
name|isAlternative
argument_list|()
condition|?
literal|"A"
else|:
literal|""
operator|)
operator|+
name|getPriority
argument_list|()
operator|+
literal|"."
return|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|readExternal
parameter_list|(
name|ObjectInput
name|in
parameter_list|)
throws|throws
name|IOException
throws|,
name|ClassNotFoundException
block|{
name|iRequestId
operator|=
name|in
operator|.
name|readLong
argument_list|()
expr_stmt|;
name|iPriority
operator|=
name|in
operator|.
name|readInt
argument_list|()
expr_stmt|;
name|iAlternative
operator|=
name|in
operator|.
name|readBoolean
argument_list|()
expr_stmt|;
name|iStudentId
operator|=
name|in
operator|.
name|readLong
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|writeExternal
parameter_list|(
name|ObjectOutput
name|out
parameter_list|)
throws|throws
name|IOException
block|{
name|out
operator|.
name|writeLong
argument_list|(
name|iRequestId
argument_list|)
expr_stmt|;
name|out
operator|.
name|writeInt
argument_list|(
name|iPriority
argument_list|)
expr_stmt|;
name|out
operator|.
name|writeBoolean
argument_list|(
name|iAlternative
argument_list|)
expr_stmt|;
name|out
operator|.
name|writeLong
argument_list|(
name|iStudentId
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

