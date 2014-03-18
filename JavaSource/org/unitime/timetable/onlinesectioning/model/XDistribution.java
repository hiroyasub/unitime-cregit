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
name|Collection
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|HashSet
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
name|constraint
operator|.
name|LinkedSections
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
name|Offering
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
name|Section
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
name|Class_
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
annotation|@
name|SerializeWith
argument_list|(
name|XDistribution
operator|.
name|XDistributionSerializer
operator|.
name|class
argument_list|)
specifier|public
class|class
name|XDistribution
implements|implements
name|Serializable
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
name|iDistributionId
init|=
literal|null
decl_stmt|;
specifier|private
name|int
name|iVariant
init|=
literal|0
decl_stmt|;
specifier|private
name|XDistributionType
name|iType
init|=
literal|null
decl_stmt|;
specifier|private
name|Set
argument_list|<
name|Long
argument_list|>
name|iOfferingIds
init|=
operator|new
name|HashSet
argument_list|<
name|Long
argument_list|>
argument_list|()
decl_stmt|;
specifier|private
name|Set
argument_list|<
name|Long
argument_list|>
name|iSectionIds
init|=
operator|new
name|HashSet
argument_list|<
name|Long
argument_list|>
argument_list|()
decl_stmt|;
specifier|public
name|XDistribution
parameter_list|()
block|{
block|}
empty_stmt|;
specifier|public
name|XDistribution
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
name|XDistribution
parameter_list|(
name|XDistributionType
name|type
parameter_list|,
name|Long
name|id
parameter_list|,
name|int
name|variant
parameter_list|,
name|Collection
argument_list|<
name|Class_
argument_list|>
name|sections
parameter_list|)
block|{
name|iType
operator|=
name|type
expr_stmt|;
name|iDistributionId
operator|=
name|id
expr_stmt|;
name|iVariant
operator|=
name|variant
expr_stmt|;
for|for
control|(
name|Class_
name|clazz
range|:
name|sections
control|)
block|{
name|iOfferingIds
operator|.
name|add
argument_list|(
name|clazz
operator|.
name|getSchedulingSubpart
argument_list|()
operator|.
name|getInstrOfferingConfig
argument_list|()
operator|.
name|getInstructionalOffering
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
name|iSectionIds
operator|.
name|add
argument_list|(
name|clazz
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
specifier|public
name|XDistribution
parameter_list|(
name|XDistributionType
name|type
parameter_list|,
name|Long
name|id
parameter_list|,
name|Long
name|offeringId
parameter_list|,
name|Collection
argument_list|<
name|Long
argument_list|>
name|sectionIds
parameter_list|)
block|{
name|iType
operator|=
name|type
expr_stmt|;
name|iDistributionId
operator|=
name|id
expr_stmt|;
name|iVariant
operator|=
literal|0
expr_stmt|;
name|iOfferingIds
operator|.
name|add
argument_list|(
name|offeringId
argument_list|)
expr_stmt|;
name|iSectionIds
operator|.
name|addAll
argument_list|(
name|sectionIds
argument_list|)
expr_stmt|;
block|}
specifier|public
name|XDistribution
parameter_list|(
name|LinkedSections
name|link
parameter_list|,
name|long
name|id
parameter_list|)
block|{
name|iType
operator|=
name|XDistributionType
operator|.
name|LinkedSections
expr_stmt|;
name|iDistributionId
operator|=
operator|-
name|id
expr_stmt|;
name|iVariant
operator|=
literal|0
expr_stmt|;
for|for
control|(
name|Offering
name|offering
range|:
name|link
operator|.
name|getOfferings
argument_list|()
control|)
block|{
name|iOfferingIds
operator|.
name|add
argument_list|(
name|offering
operator|.
name|getId
argument_list|()
argument_list|)
expr_stmt|;
for|for
control|(
name|Subpart
name|subpart
range|:
name|link
operator|.
name|getSubparts
argument_list|(
name|offering
argument_list|)
control|)
for|for
control|(
name|Section
name|section
range|:
name|link
operator|.
name|getSections
argument_list|(
name|subpart
argument_list|)
control|)
name|iSectionIds
operator|.
name|add
argument_list|(
name|section
operator|.
name|getId
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
specifier|public
name|XDistributionType
name|getDistributionType
parameter_list|()
block|{
return|return
name|iType
return|;
block|}
specifier|public
name|Long
name|getDistributionId
parameter_list|()
block|{
return|return
name|iDistributionId
return|;
block|}
specifier|public
name|int
name|getVariant
parameter_list|()
block|{
return|return
name|iVariant
return|;
block|}
specifier|public
name|Set
argument_list|<
name|Long
argument_list|>
name|getOfferingIds
parameter_list|()
block|{
return|return
name|iOfferingIds
return|;
block|}
specifier|public
name|Set
argument_list|<
name|Long
argument_list|>
name|getSectionIds
parameter_list|()
block|{
return|return
name|iSectionIds
return|;
block|}
specifier|public
name|boolean
name|hasSection
parameter_list|(
name|Long
name|sectionId
parameter_list|)
block|{
return|return
name|iSectionIds
operator|.
name|contains
argument_list|(
name|sectionId
argument_list|)
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
name|XDistribution
operator|)
condition|)
return|return
literal|false
return|;
return|return
name|getDistributionId
argument_list|()
operator|.
name|equals
argument_list|(
operator|(
operator|(
name|XDistribution
operator|)
name|o
operator|)
operator|.
name|getDistributionId
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
name|getDistributionId
argument_list|()
operator|^
operator|(
name|getDistributionId
argument_list|()
operator|>>>
literal|32
operator|)
operator|^
name|getVariant
argument_list|()
operator|)
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
name|iDistributionId
operator|=
name|in
operator|.
name|readLong
argument_list|()
expr_stmt|;
name|iVariant
operator|=
name|in
operator|.
name|readInt
argument_list|()
expr_stmt|;
name|iType
operator|=
name|XDistributionType
operator|.
name|values
argument_list|()
index|[
name|in
operator|.
name|readInt
argument_list|()
index|]
expr_stmt|;
name|int
name|nrOfferings
init|=
name|in
operator|.
name|readInt
argument_list|()
decl_stmt|;
name|iOfferingIds
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
name|nrOfferings
condition|;
name|i
operator|++
control|)
name|iOfferingIds
operator|.
name|add
argument_list|(
name|in
operator|.
name|readLong
argument_list|()
argument_list|)
expr_stmt|;
name|int
name|nrSections
init|=
name|in
operator|.
name|readInt
argument_list|()
decl_stmt|;
name|iSectionIds
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
name|nrSections
condition|;
name|i
operator|++
control|)
name|iSectionIds
operator|.
name|add
argument_list|(
name|in
operator|.
name|readLong
argument_list|()
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
name|iDistributionId
argument_list|)
expr_stmt|;
name|out
operator|.
name|writeInt
argument_list|(
name|iVariant
argument_list|)
expr_stmt|;
name|out
operator|.
name|writeInt
argument_list|(
name|iType
operator|.
name|ordinal
argument_list|()
argument_list|)
expr_stmt|;
name|out
operator|.
name|writeInt
argument_list|(
name|iOfferingIds
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
for|for
control|(
name|Long
name|offeringId
range|:
name|iOfferingIds
control|)
name|out
operator|.
name|writeLong
argument_list|(
name|offeringId
argument_list|)
expr_stmt|;
name|out
operator|.
name|writeInt
argument_list|(
name|iSectionIds
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
for|for
control|(
name|Long
name|sectionId
range|:
name|iSectionIds
control|)
name|out
operator|.
name|writeLong
argument_list|(
name|sectionId
argument_list|)
expr_stmt|;
block|}
specifier|public
specifier|static
class|class
name|XDistributionSerializer
implements|implements
name|Externalizer
argument_list|<
name|XDistribution
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
name|XDistribution
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
name|XDistribution
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
name|XDistribution
argument_list|(
name|input
argument_list|)
return|;
block|}
block|}
block|}
end_class

end_unit

