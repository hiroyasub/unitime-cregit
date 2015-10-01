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
name|ArrayList
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Collections
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Comparator
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|List
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|TreeSet
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
name|Config
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
name|cpsolver
operator|.
name|studentsct
operator|.
name|model
operator|.
name|Subpart
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
name|CourseOffering
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
name|InstrOfferingConfig
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
name|SchedulingSubpart
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
name|comparators
operator|.
name|SchedulingSubpartComparator
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
name|XConfig
operator|.
name|XConfigSerializer
operator|.
name|class
argument_list|)
specifier|public
class|class
name|XConfig
implements|implements
name|Serializable
implements|,
name|Comparable
argument_list|<
name|XConfig
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
name|iUniqueId
init|=
literal|null
decl_stmt|;
specifier|private
name|String
name|iName
init|=
literal|null
decl_stmt|;
specifier|private
name|Long
name|iOfferingId
init|=
literal|null
decl_stmt|;
specifier|private
name|int
name|iLimit
init|=
operator|-
literal|1
decl_stmt|;
specifier|private
name|List
argument_list|<
name|XSubpart
argument_list|>
name|iSubparts
init|=
operator|new
name|ArrayList
argument_list|<
name|XSubpart
argument_list|>
argument_list|()
decl_stmt|;
specifier|public
name|XConfig
parameter_list|()
block|{
block|}
specifier|public
name|XConfig
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
name|XConfig
parameter_list|(
name|InstrOfferingConfig
name|config
parameter_list|,
name|OnlineSectioningHelper
name|helper
parameter_list|)
block|{
name|iUniqueId
operator|=
name|config
operator|.
name|getUniqueId
argument_list|()
expr_stmt|;
name|iName
operator|=
name|config
operator|.
name|getName
argument_list|()
expr_stmt|;
name|iOfferingId
operator|=
name|config
operator|.
name|getInstructionalOffering
argument_list|()
operator|.
name|getUniqueId
argument_list|()
expr_stmt|;
name|iLimit
operator|=
operator|(
name|config
operator|.
name|isUnlimitedEnrollment
argument_list|()
condition|?
operator|-
literal|1
else|:
name|config
operator|.
name|getLimit
argument_list|()
operator|)
expr_stmt|;
if|if
condition|(
name|iLimit
operator|>=
literal|9999
condition|)
name|iLimit
operator|=
operator|-
literal|1
expr_stmt|;
name|TreeSet
argument_list|<
name|SchedulingSubpart
argument_list|>
name|subparts
init|=
operator|new
name|TreeSet
argument_list|<
name|SchedulingSubpart
argument_list|>
argument_list|(
operator|new
name|SchedulingSubpartComparator
argument_list|()
argument_list|)
decl_stmt|;
name|subparts
operator|.
name|addAll
argument_list|(
name|config
operator|.
name|getSchedulingSubparts
argument_list|()
argument_list|)
expr_stmt|;
name|boolean
name|credit
init|=
literal|false
decl_stmt|;
for|for
control|(
name|CourseOffering
name|co
range|:
name|config
operator|.
name|getInstructionalOffering
argument_list|()
operator|.
name|getCourseOfferings
argument_list|()
control|)
block|{
if|if
condition|(
name|co
operator|.
name|getCredit
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|credit
operator|=
literal|true
expr_stmt|;
break|break;
block|}
block|}
for|for
control|(
name|SchedulingSubpart
name|subpart
range|:
name|subparts
control|)
block|{
name|iSubparts
operator|.
name|add
argument_list|(
operator|new
name|XSubpart
argument_list|(
name|subpart
argument_list|,
name|credit
argument_list|,
name|helper
argument_list|)
argument_list|)
expr_stmt|;
name|credit
operator|=
literal|false
expr_stmt|;
block|}
name|Collections
operator|.
name|sort
argument_list|(
name|iSubparts
argument_list|,
operator|new
name|XSubpartComparator
argument_list|()
argument_list|)
expr_stmt|;
block|}
specifier|public
name|XConfig
parameter_list|(
name|Config
name|config
parameter_list|)
block|{
name|iUniqueId
operator|=
name|config
operator|.
name|getId
argument_list|()
expr_stmt|;
name|iName
operator|=
name|config
operator|.
name|getName
argument_list|()
expr_stmt|;
name|iOfferingId
operator|=
name|config
operator|.
name|getOffering
argument_list|()
operator|.
name|getId
argument_list|()
expr_stmt|;
name|iLimit
operator|=
name|config
operator|.
name|getLimit
argument_list|()
expr_stmt|;
name|boolean
name|credit
init|=
literal|false
decl_stmt|;
for|for
control|(
name|Course
name|course
range|:
name|config
operator|.
name|getOffering
argument_list|()
operator|.
name|getCourses
argument_list|()
control|)
block|{
if|if
condition|(
name|course
operator|.
name|getCredit
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|credit
operator|=
literal|true
expr_stmt|;
break|break;
block|}
block|}
for|for
control|(
name|Subpart
name|subpart
range|:
name|config
operator|.
name|getSubparts
argument_list|()
control|)
block|{
name|iSubparts
operator|.
name|add
argument_list|(
operator|new
name|XSubpart
argument_list|(
name|subpart
argument_list|,
name|credit
argument_list|)
argument_list|)
expr_stmt|;
name|credit
operator|=
literal|false
expr_stmt|;
block|}
block|}
comment|/** Configuration id */
specifier|public
name|Long
name|getConfigId
parameter_list|()
block|{
return|return
name|iUniqueId
return|;
block|}
comment|/** Get subpart of the given id */
specifier|public
name|XSubpart
name|getSubpart
parameter_list|(
name|Long
name|subpartId
parameter_list|)
block|{
for|for
control|(
name|XSubpart
name|subpart
range|:
name|iSubparts
control|)
if|if
condition|(
name|subpart
operator|.
name|getSubpartId
argument_list|()
operator|.
name|equals
argument_list|(
name|subpartId
argument_list|)
condition|)
return|return
name|subpart
return|;
return|return
literal|null
return|;
block|}
comment|/**      * Configuration limit. This is defines the maximal number of students that can be      * enrolled into this configuration at the same time. It is -1 in the case of an      * unlimited configuration      */
specifier|public
name|int
name|getLimit
parameter_list|()
block|{
return|return
name|iLimit
return|;
block|}
comment|/** Set configuration limit */
specifier|public
name|void
name|setLimit
parameter_list|(
name|int
name|limit
parameter_list|)
block|{
name|iLimit
operator|=
name|limit
expr_stmt|;
block|}
comment|/** Configuration name */
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
name|iName
return|;
block|}
comment|/** Instructional offering to which this configuration belongs. */
specifier|public
name|Long
name|getOfferingId
parameter_list|()
block|{
return|return
name|iOfferingId
return|;
block|}
comment|/** List of subparts */
specifier|public
name|List
argument_list|<
name|XSubpart
argument_list|>
name|getSubparts
parameter_list|()
block|{
return|return
name|iSubparts
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
name|XConfig
operator|)
condition|)
return|return
literal|false
return|;
return|return
name|getConfigId
argument_list|()
operator|.
name|equals
argument_list|(
operator|(
operator|(
name|XConfig
operator|)
name|o
operator|)
operator|.
name|getConfigId
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
operator|new
name|Long
argument_list|(
name|getConfigId
argument_list|()
argument_list|)
operator|.
name|hashCode
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|int
name|compareTo
parameter_list|(
name|XConfig
name|config
parameter_list|)
block|{
return|return
name|getName
argument_list|()
operator|.
name|equals
argument_list|(
name|config
operator|.
name|getName
argument_list|()
argument_list|)
condition|?
name|getConfigId
argument_list|()
operator|.
name|compareTo
argument_list|(
name|config
operator|.
name|getConfigId
argument_list|()
argument_list|)
else|:
name|getName
argument_list|()
operator|.
name|compareTo
argument_list|(
name|config
operator|.
name|getName
argument_list|()
argument_list|)
return|;
block|}
specifier|private
class|class
name|XSubpartComparator
implements|implements
name|Serializable
implements|,
name|Comparator
argument_list|<
name|XSubpart
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
specifier|public
name|boolean
name|isParent
parameter_list|(
name|XSubpart
name|s1
parameter_list|,
name|XSubpart
name|s2
parameter_list|)
block|{
name|Long
name|p1
init|=
name|s1
operator|.
name|getParentId
argument_list|()
decl_stmt|;
if|if
condition|(
name|p1
operator|==
literal|null
condition|)
return|return
literal|false
return|;
if|if
condition|(
name|p1
operator|.
name|equals
argument_list|(
name|s2
operator|.
name|getSubpartId
argument_list|()
argument_list|)
condition|)
return|return
literal|true
return|;
return|return
name|isParent
argument_list|(
name|getSubpart
argument_list|(
name|p1
argument_list|)
argument_list|,
name|s2
argument_list|)
return|;
block|}
specifier|public
name|int
name|compare
parameter_list|(
name|XSubpart
name|a
parameter_list|,
name|XSubpart
name|b
parameter_list|)
block|{
if|if
condition|(
name|isParent
argument_list|(
name|a
argument_list|,
name|b
argument_list|)
condition|)
return|return
literal|1
return|;
if|if
condition|(
name|isParent
argument_list|(
name|b
argument_list|,
name|a
argument_list|)
condition|)
return|return
operator|-
literal|1
return|;
name|int
name|cmp
init|=
name|a
operator|.
name|getInstructionalType
argument_list|()
operator|.
name|compareToIgnoreCase
argument_list|(
name|b
operator|.
name|getInstructionalType
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
name|Double
operator|.
name|compare
argument_list|(
name|a
operator|.
name|getSubpartId
argument_list|()
argument_list|,
name|b
operator|.
name|getSubpartId
argument_list|()
argument_list|)
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
name|iUniqueId
operator|=
name|in
operator|.
name|readLong
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
name|iOfferingId
operator|=
name|in
operator|.
name|readLong
argument_list|()
expr_stmt|;
name|iLimit
operator|=
name|in
operator|.
name|readInt
argument_list|()
expr_stmt|;
name|int
name|nrSubparts
init|=
name|in
operator|.
name|readInt
argument_list|()
decl_stmt|;
name|iSubparts
operator|.
name|clear
argument_list|()
expr_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|nrSubparts
condition|;
name|i
operator|++
control|)
name|iSubparts
operator|.
name|add
argument_list|(
operator|new
name|XSubpart
argument_list|(
name|in
argument_list|)
argument_list|)
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
name|iUniqueId
argument_list|)
expr_stmt|;
name|out
operator|.
name|writeObject
argument_list|(
name|iName
argument_list|)
expr_stmt|;
name|out
operator|.
name|writeLong
argument_list|(
name|iOfferingId
argument_list|)
expr_stmt|;
name|out
operator|.
name|writeInt
argument_list|(
name|iLimit
argument_list|)
expr_stmt|;
name|out
operator|.
name|writeInt
argument_list|(
name|iSubparts
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
for|for
control|(
name|XSubpart
name|subpart
range|:
name|iSubparts
control|)
name|subpart
operator|.
name|writeExternal
argument_list|(
name|out
argument_list|)
expr_stmt|;
block|}
specifier|public
specifier|static
class|class
name|XConfigSerializer
implements|implements
name|Externalizer
argument_list|<
name|XConfig
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
name|XConfig
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
name|XConfig
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
name|XConfig
argument_list|(
name|input
argument_list|)
return|;
block|}
block|}
block|}
end_class

end_unit

