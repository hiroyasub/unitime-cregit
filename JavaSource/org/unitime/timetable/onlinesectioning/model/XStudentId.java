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
name|org
operator|.
name|infinispan
operator|.
name|commons
operator|.
name|marshall
operator|.
name|Externalizer
import|;
end_import

begin_import
import|import
name|org
operator|.
name|infinispan
operator|.
name|commons
operator|.
name|marshall
operator|.
name|SerializeWith
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

begin_import
import|import
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|onlinesectioning
operator|.
name|OnlineSectioningHelper
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
annotation|@
name|SerializeWith
argument_list|(
name|XStudentId
operator|.
name|XStudentIdSerializer
operator|.
name|class
argument_list|)
specifier|public
class|class
name|XStudentId
implements|implements
name|Serializable
implements|,
name|Comparable
argument_list|<
name|XStudentId
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
specifier|private
name|Long
name|iStudentId
decl_stmt|;
specifier|private
name|String
name|iExternalId
init|=
literal|null
decl_stmt|,
name|iName
init|=
literal|null
decl_stmt|;
specifier|public
name|XStudentId
parameter_list|()
block|{
block|}
specifier|public
name|XStudentId
parameter_list|(
name|ObjectInput
name|in
parameter_list|)
throws|throws
name|IOException
throws|,
name|ClassNotFoundException
block|{
name|readExternal
argument_list|(
name|in
argument_list|)
expr_stmt|;
block|}
specifier|public
name|XStudentId
parameter_list|(
name|Student
name|student
parameter_list|,
name|OnlineSectioningHelper
name|helper
parameter_list|)
block|{
name|iStudentId
operator|=
name|student
operator|.
name|getUniqueId
argument_list|()
expr_stmt|;
name|iExternalId
operator|=
name|student
operator|.
name|getExternalUniqueId
argument_list|()
expr_stmt|;
name|iName
operator|=
name|helper
operator|.
name|getStudentNameFormat
argument_list|()
operator|.
name|format
argument_list|(
name|student
argument_list|)
expr_stmt|;
block|}
specifier|public
name|XStudentId
parameter_list|(
name|org
operator|.
name|cpsolver
operator|.
name|studentsct
operator|.
name|model
operator|.
name|Student
name|student
parameter_list|)
block|{
name|iStudentId
operator|=
name|student
operator|.
name|getId
argument_list|()
expr_stmt|;
name|iExternalId
operator|=
name|student
operator|.
name|getExternalId
argument_list|()
expr_stmt|;
name|iName
operator|=
name|student
operator|.
name|getName
argument_list|()
expr_stmt|;
block|}
specifier|public
name|XStudentId
parameter_list|(
name|XStudentId
name|student
parameter_list|)
block|{
name|iStudentId
operator|=
name|student
operator|.
name|getStudentId
argument_list|()
expr_stmt|;
name|iExternalId
operator|=
name|student
operator|.
name|getExternalId
argument_list|()
expr_stmt|;
name|iName
operator|=
name|student
operator|.
name|getName
argument_list|()
expr_stmt|;
block|}
comment|/** Student unique id */
specifier|public
name|Long
name|getStudentId
parameter_list|()
block|{
return|return
name|iStudentId
return|;
block|}
comment|/**      * Get student external id      */
specifier|public
name|String
name|getExternalId
parameter_list|()
block|{
return|return
name|iExternalId
return|;
block|}
comment|/**      * Get student name      */
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
name|iName
return|;
block|}
comment|/**      * Compare two students for equality. Two students are considered equal if      * they have the same id.      */
annotation|@
name|Override
specifier|public
name|boolean
name|equals
parameter_list|(
name|Object
name|object
parameter_list|)
block|{
if|if
condition|(
name|object
operator|==
literal|null
operator|||
operator|!
operator|(
name|object
operator|instanceof
name|XStudentId
operator|)
condition|)
return|return
literal|false
return|;
return|return
name|getStudentId
argument_list|()
operator|.
name|equals
argument_list|(
operator|(
operator|(
name|XStudentId
operator|)
name|object
operator|)
operator|.
name|getStudentId
argument_list|()
argument_list|)
return|;
block|}
comment|/**      * Hash code (base only on student id)      */
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
name|getStudentId
argument_list|()
operator|^
operator|(
name|getStudentId
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
name|XStudentId
name|id
parameter_list|)
block|{
name|int
name|cmp
init|=
name|getName
argument_list|()
operator|.
name|compareToIgnoreCase
argument_list|(
name|id
operator|.
name|getName
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|cmp
operator|!=
literal|0
condition|)
return|return
name|cmp
return|;
return|return
name|getStudentId
argument_list|()
operator|.
name|compareTo
argument_list|(
name|id
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
name|getName
argument_list|()
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
name|iStudentId
operator|=
name|in
operator|.
name|readLong
argument_list|()
expr_stmt|;
name|iExternalId
operator|=
operator|(
name|String
operator|)
name|in
operator|.
name|readObject
argument_list|()
expr_stmt|;
name|iName
operator|=
operator|(
name|String
operator|)
name|in
operator|.
name|readObject
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
name|iStudentId
argument_list|)
expr_stmt|;
name|out
operator|.
name|writeObject
argument_list|(
name|iExternalId
argument_list|)
expr_stmt|;
name|out
operator|.
name|writeObject
argument_list|(
name|iName
argument_list|)
expr_stmt|;
block|}
specifier|public
specifier|static
class|class
name|XStudentIdSerializer
implements|implements
name|Externalizer
argument_list|<
name|XStudentId
argument_list|>
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|1L
decl_stmt|;
annotation|@
name|Override
specifier|public
name|void
name|writeObject
parameter_list|(
name|ObjectOutput
name|output
parameter_list|,
name|XStudentId
name|object
parameter_list|)
throws|throws
name|IOException
block|{
name|object
operator|.
name|writeExternal
argument_list|(
name|output
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|XStudentId
name|readObject
parameter_list|(
name|ObjectInput
name|input
parameter_list|)
throws|throws
name|IOException
throws|,
name|ClassNotFoundException
block|{
return|return
operator|new
name|XStudentId
argument_list|(
name|input
argument_list|)
return|;
block|}
block|}
block|}
end_class

end_unit

