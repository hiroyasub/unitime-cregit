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
name|cpsolver
operator|.
name|studentsct
operator|.
name|model
operator|.
name|Course
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
name|commons
operator|.
name|NaturalOrderComparator
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
name|CourseOffering
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
annotation|@
name|SerializeWith
argument_list|(
name|XCourseId
operator|.
name|XCourseIdSerializer
operator|.
name|class
argument_list|)
specifier|public
class|class
name|XCourseId
implements|implements
name|Serializable
implements|,
name|Comparable
argument_list|<
name|XCourseId
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
name|iOfferingId
decl_stmt|;
specifier|private
name|Long
name|iCourseId
decl_stmt|;
specifier|private
name|String
name|iCourseName
decl_stmt|;
specifier|private
name|String
name|iTitle
init|=
literal|null
decl_stmt|;
specifier|private
name|boolean
name|iHasUniqueName
init|=
literal|true
decl_stmt|;
specifier|private
name|String
name|iType
init|=
literal|null
decl_stmt|;
specifier|public
name|XCourseId
parameter_list|()
block|{
block|}
specifier|public
name|XCourseId
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
name|XCourseId
parameter_list|(
name|CourseOffering
name|course
parameter_list|)
block|{
name|iOfferingId
operator|=
name|course
operator|.
name|getInstructionalOffering
argument_list|()
operator|.
name|getUniqueId
argument_list|()
expr_stmt|;
name|iCourseId
operator|=
name|course
operator|.
name|getUniqueId
argument_list|()
expr_stmt|;
name|iCourseName
operator|=
name|course
operator|.
name|getCourseName
argument_list|()
operator|.
name|trim
argument_list|()
expr_stmt|;
name|iTitle
operator|=
operator|(
name|course
operator|.
name|getTitle
argument_list|()
operator|==
literal|null
condition|?
literal|null
else|:
name|course
operator|.
name|getTitle
argument_list|()
operator|.
name|trim
argument_list|()
operator|)
expr_stmt|;
name|iType
operator|=
operator|(
name|course
operator|.
name|getCourseType
argument_list|()
operator|==
literal|null
condition|?
literal|null
else|:
name|course
operator|.
name|getCourseType
argument_list|()
operator|.
name|getReference
argument_list|()
operator|)
expr_stmt|;
block|}
specifier|public
name|XCourseId
parameter_list|(
name|Long
name|offeringId
parameter_list|,
name|Long
name|courseId
parameter_list|,
name|String
name|courseName
parameter_list|)
block|{
name|iOfferingId
operator|=
name|offeringId
expr_stmt|;
name|iCourseId
operator|=
name|courseId
expr_stmt|;
name|iCourseName
operator|=
name|courseName
expr_stmt|;
block|}
specifier|public
name|XCourseId
parameter_list|(
name|XCourseId
name|course
parameter_list|)
block|{
name|iOfferingId
operator|=
name|course
operator|.
name|getOfferingId
argument_list|()
expr_stmt|;
name|iCourseId
operator|=
name|course
operator|.
name|getCourseId
argument_list|()
expr_stmt|;
name|iCourseName
operator|=
name|course
operator|.
name|getCourseName
argument_list|()
expr_stmt|;
name|iTitle
operator|=
name|course
operator|.
name|getTitle
argument_list|()
expr_stmt|;
block|}
specifier|public
name|XCourseId
parameter_list|(
name|Course
name|course
parameter_list|)
block|{
name|iOfferingId
operator|=
name|course
operator|.
name|getOffering
argument_list|()
operator|.
name|getId
argument_list|()
expr_stmt|;
name|iCourseId
operator|=
name|course
operator|.
name|getId
argument_list|()
expr_stmt|;
name|iCourseName
operator|=
name|course
operator|.
name|getName
argument_list|()
expr_stmt|;
block|}
comment|/** Instructional offering unique id */
specifier|public
name|Long
name|getOfferingId
parameter_list|()
block|{
return|return
name|iOfferingId
return|;
block|}
comment|/** Course offering unique id */
specifier|public
name|Long
name|getCourseId
parameter_list|()
block|{
return|return
name|iCourseId
return|;
block|}
comment|/** Course name */
specifier|public
name|String
name|getCourseName
parameter_list|()
block|{
return|return
name|iCourseName
return|;
block|}
comment|/** Course title */
specifier|public
name|String
name|getTitle
parameter_list|()
block|{
return|return
name|iTitle
return|;
block|}
specifier|public
name|boolean
name|hasType
parameter_list|()
block|{
return|return
name|iType
operator|!=
literal|null
operator|&&
operator|!
name|iType
operator|.
name|isEmpty
argument_list|()
return|;
block|}
specifier|public
name|String
name|getType
parameter_list|()
block|{
return|return
name|iType
return|;
block|}
specifier|public
name|String
name|getCourseNameInLowerCase
parameter_list|()
block|{
return|return
name|getCourseName
argument_list|()
operator|.
name|toLowerCase
argument_list|()
return|;
block|}
specifier|public
name|boolean
name|hasUniqueName
parameter_list|()
block|{
return|return
name|iHasUniqueName
return|;
block|}
specifier|public
name|void
name|setHasUniqueName
parameter_list|(
name|boolean
name|hasUniqueName
parameter_list|)
block|{
name|iHasUniqueName
operator|=
name|hasUniqueName
expr_stmt|;
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
name|XCourseId
operator|)
condition|)
return|return
literal|false
return|;
return|return
name|getCourseId
argument_list|()
operator|.
name|equals
argument_list|(
operator|(
operator|(
name|XCourseId
operator|)
name|o
operator|)
operator|.
name|getCourseId
argument_list|()
argument_list|)
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
name|getCourseId
argument_list|()
operator|^
operator|(
name|getCourseId
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
name|String
name|toString
parameter_list|()
block|{
return|return
name|getCourseName
argument_list|()
return|;
block|}
specifier|public
name|int
name|compareTo
parameter_list|(
name|XCourseId
name|c
parameter_list|)
block|{
name|int
name|cmp
init|=
name|NaturalOrderComparator
operator|.
name|getInstance
argument_list|()
operator|.
name|compare
argument_list|(
name|getCourseName
argument_list|()
argument_list|,
name|c
operator|.
name|getCourseName
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
name|cmp
operator|=
operator|(
name|getTitle
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
name|getTitle
argument_list|()
operator|)
operator|.
name|compareToIgnoreCase
argument_list|(
name|c
operator|.
name|getTitle
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
name|c
operator|.
name|getTitle
argument_list|()
argument_list|)
expr_stmt|;
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
operator|(
name|getCourseId
argument_list|()
operator|==
literal|null
condition|?
operator|new
name|Long
argument_list|(
operator|-
literal|1
argument_list|)
else|:
name|getCourseId
argument_list|()
operator|)
operator|.
name|compareTo
argument_list|(
name|c
operator|.
name|getCourseId
argument_list|()
operator|==
literal|null
condition|?
operator|-
literal|1
else|:
name|c
operator|.
name|getCourseId
argument_list|()
argument_list|)
return|;
block|}
specifier|public
name|boolean
name|matchCourseName
parameter_list|(
name|String
name|queryInLowerCase
parameter_list|)
block|{
if|if
condition|(
name|getCourseName
argument_list|()
operator|.
name|toLowerCase
argument_list|()
operator|.
name|startsWith
argument_list|(
name|queryInLowerCase
argument_list|)
condition|)
return|return
literal|true
return|;
if|if
condition|(
name|getTitle
argument_list|()
operator|==
literal|null
condition|)
return|return
literal|false
return|;
if|if
condition|(
operator|(
name|getCourseName
argument_list|()
operator|+
literal|" "
operator|+
name|getTitle
argument_list|()
operator|)
operator|.
name|toLowerCase
argument_list|()
operator|.
name|startsWith
argument_list|(
name|queryInLowerCase
argument_list|)
condition|)
return|return
literal|true
return|;
if|if
condition|(
operator|(
name|getCourseName
argument_list|()
operator|+
literal|" - "
operator|+
name|getTitle
argument_list|()
operator|)
operator|.
name|toLowerCase
argument_list|()
operator|.
name|startsWith
argument_list|(
name|queryInLowerCase
argument_list|)
condition|)
return|return
literal|true
return|;
return|return
literal|false
return|;
block|}
specifier|public
name|boolean
name|matchTitle
parameter_list|(
name|String
name|queryInLowerCase
parameter_list|)
block|{
if|if
condition|(
name|getTitle
argument_list|()
operator|==
literal|null
condition|)
return|return
literal|false
return|;
if|if
condition|(
operator|!
name|matchCourseName
argument_list|(
name|queryInLowerCase
argument_list|)
operator|&&
name|getTitle
argument_list|()
operator|.
name|toLowerCase
argument_list|()
operator|.
name|contains
argument_list|(
name|queryInLowerCase
argument_list|)
condition|)
return|return
literal|true
return|;
return|return
literal|false
return|;
block|}
specifier|public
name|boolean
name|matchType
parameter_list|(
name|boolean
name|allCourseTypes
parameter_list|,
name|boolean
name|noCourseType
parameter_list|,
name|Set
argument_list|<
name|String
argument_list|>
name|allowedCourseTypes
parameter_list|)
block|{
if|if
condition|(
name|allCourseTypes
condition|)
return|return
literal|true
return|;
if|if
condition|(
name|hasType
argument_list|()
condition|)
block|{
return|return
name|allowedCourseTypes
operator|!=
literal|null
operator|&&
name|allowedCourseTypes
operator|.
name|contains
argument_list|(
name|getType
argument_list|()
argument_list|)
return|;
block|}
else|else
block|{
return|return
name|noCourseType
return|;
block|}
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
name|iOfferingId
operator|=
name|in
operator|.
name|readLong
argument_list|()
expr_stmt|;
name|iCourseId
operator|=
name|in
operator|.
name|readLong
argument_list|()
expr_stmt|;
name|iCourseName
operator|=
operator|(
name|String
operator|)
name|in
operator|.
name|readObject
argument_list|()
expr_stmt|;
name|iTitle
operator|=
operator|(
name|String
operator|)
name|in
operator|.
name|readObject
argument_list|()
expr_stmt|;
name|iHasUniqueName
operator|=
name|in
operator|.
name|readBoolean
argument_list|()
expr_stmt|;
name|iType
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
name|iOfferingId
argument_list|)
expr_stmt|;
name|out
operator|.
name|writeLong
argument_list|(
name|iCourseId
argument_list|)
expr_stmt|;
name|out
operator|.
name|writeObject
argument_list|(
name|iCourseName
argument_list|)
expr_stmt|;
name|out
operator|.
name|writeObject
argument_list|(
name|iTitle
argument_list|)
expr_stmt|;
name|out
operator|.
name|writeBoolean
argument_list|(
name|iHasUniqueName
argument_list|)
expr_stmt|;
name|out
operator|.
name|writeObject
argument_list|(
name|iType
argument_list|)
expr_stmt|;
block|}
specifier|public
specifier|static
class|class
name|XCourseIdSerializer
implements|implements
name|Externalizer
argument_list|<
name|XCourseId
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
name|XCourseId
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
name|XCourseId
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
name|XCourseId
argument_list|(
name|input
argument_list|)
return|;
block|}
block|}
block|}
end_class

end_unit

