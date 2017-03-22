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
name|AcademicClassification
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
name|CurriculumReservation
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
name|PosMajor
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
annotation|@
name|SerializeWith
argument_list|(
name|XCurriculumReservation
operator|.
name|XCurriculumReservationSerializer
operator|.
name|class
argument_list|)
specifier|public
class|class
name|XCurriculumReservation
extends|extends
name|XReservation
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
name|int
name|iLimit
decl_stmt|;
specifier|private
name|String
name|iAcadArea
decl_stmt|;
specifier|private
name|Set
argument_list|<
name|String
argument_list|>
name|iClassifications
init|=
operator|new
name|HashSet
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
specifier|private
name|Set
argument_list|<
name|String
argument_list|>
name|iMajors
init|=
operator|new
name|HashSet
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
specifier|public
name|XCurriculumReservation
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
block|}
specifier|public
name|XCurriculumReservation
parameter_list|(
name|ObjectInput
name|in
parameter_list|)
throws|throws
name|IOException
throws|,
name|ClassNotFoundException
block|{
name|super
argument_list|()
expr_stmt|;
name|readExternal
argument_list|(
name|in
argument_list|)
expr_stmt|;
block|}
specifier|public
name|XCurriculumReservation
parameter_list|(
name|XOffering
name|offering
parameter_list|,
name|CurriculumReservation
name|reservation
parameter_list|)
block|{
name|super
argument_list|(
name|XReservationType
operator|.
name|Curriculum
argument_list|,
name|offering
argument_list|,
name|reservation
argument_list|)
expr_stmt|;
name|iLimit
operator|=
operator|(
name|reservation
operator|.
name|getLimit
argument_list|()
operator|==
literal|null
condition|?
operator|-
literal|1
else|:
name|reservation
operator|.
name|getLimit
argument_list|()
operator|)
expr_stmt|;
name|iAcadArea
operator|=
name|reservation
operator|.
name|getArea
argument_list|()
operator|.
name|getAcademicAreaAbbreviation
argument_list|()
expr_stmt|;
for|for
control|(
name|AcademicClassification
name|clasf
range|:
name|reservation
operator|.
name|getClassifications
argument_list|()
control|)
name|iClassifications
operator|.
name|add
argument_list|(
name|clasf
operator|.
name|getCode
argument_list|()
argument_list|)
expr_stmt|;
for|for
control|(
name|PosMajor
name|major
range|:
name|reservation
operator|.
name|getMajors
argument_list|()
control|)
name|iMajors
operator|.
name|add
argument_list|(
name|major
operator|.
name|getCode
argument_list|()
argument_list|)
expr_stmt|;
block|}
specifier|public
name|XCurriculumReservation
parameter_list|(
name|org
operator|.
name|cpsolver
operator|.
name|studentsct
operator|.
name|reservation
operator|.
name|CurriculumReservation
name|reservation
parameter_list|)
block|{
name|super
argument_list|(
name|XReservationType
operator|.
name|Curriculum
argument_list|,
name|reservation
argument_list|)
expr_stmt|;
name|iLimit
operator|=
operator|(
name|int
operator|)
name|Math
operator|.
name|round
argument_list|(
name|reservation
operator|.
name|getReservationLimit
argument_list|()
argument_list|)
expr_stmt|;
name|iAcadArea
operator|=
name|reservation
operator|.
name|getAcademicArea
argument_list|()
expr_stmt|;
if|if
condition|(
name|reservation
operator|.
name|getClassifications
argument_list|()
operator|!=
literal|null
condition|)
name|iClassifications
operator|.
name|addAll
argument_list|(
name|reservation
operator|.
name|getClassifications
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|reservation
operator|.
name|getMajors
argument_list|()
operator|!=
literal|null
condition|)
name|iMajors
operator|.
name|addAll
argument_list|(
name|reservation
operator|.
name|getMajors
argument_list|()
argument_list|)
expr_stmt|;
block|}
comment|/**      * Reservation limit (-1 for unlimited)      */
annotation|@
name|Override
specifier|public
name|int
name|getReservationLimit
parameter_list|()
block|{
return|return
name|iLimit
return|;
block|}
comment|/**      * Academic area      */
specifier|public
name|String
name|getAcademicArea
parameter_list|()
block|{
return|return
name|iAcadArea
return|;
block|}
comment|/**      * Majors      */
specifier|public
name|Set
argument_list|<
name|String
argument_list|>
name|getMajors
parameter_list|()
block|{
return|return
name|iMajors
return|;
block|}
comment|/**      * Academic classifications      */
specifier|public
name|Set
argument_list|<
name|String
argument_list|>
name|getClassifications
parameter_list|()
block|{
return|return
name|iClassifications
return|;
block|}
comment|/**      * Check the area, classifications and majors      */
annotation|@
name|Override
specifier|public
name|boolean
name|isApplicable
parameter_list|(
name|XStudent
name|student
parameter_list|)
block|{
for|for
control|(
name|XAreaClassificationMajor
name|acm
range|:
name|student
operator|.
name|getMajors
argument_list|()
control|)
block|{
if|if
condition|(
name|getAcademicArea
argument_list|()
operator|.
name|equals
argument_list|(
name|acm
operator|.
name|getArea
argument_list|()
argument_list|)
operator|&&
operator|(
name|getClassifications
argument_list|()
operator|.
name|isEmpty
argument_list|()
operator|||
name|getClassifications
argument_list|()
operator|.
name|contains
argument_list|(
name|acm
operator|.
name|getClassification
argument_list|()
argument_list|)
operator|)
operator|&&
operator|(
name|getMajors
argument_list|()
operator|.
name|isEmpty
argument_list|()
operator|||
name|getMajors
argument_list|()
operator|.
name|contains
argument_list|(
name|acm
operator|.
name|getMajor
argument_list|()
argument_list|)
operator|)
condition|)
return|return
literal|true
return|;
block|}
return|return
literal|false
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
name|super
operator|.
name|readExternal
argument_list|(
name|in
argument_list|)
expr_stmt|;
name|iAcadArea
operator|=
operator|(
name|String
operator|)
name|in
operator|.
name|readObject
argument_list|()
expr_stmt|;
name|int
name|nrClassifications
init|=
name|in
operator|.
name|readInt
argument_list|()
decl_stmt|;
name|iClassifications
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
name|nrClassifications
condition|;
name|i
operator|++
control|)
name|iClassifications
operator|.
name|add
argument_list|(
operator|(
name|String
operator|)
name|in
operator|.
name|readObject
argument_list|()
argument_list|)
expr_stmt|;
name|int
name|nrMajors
init|=
name|in
operator|.
name|readInt
argument_list|()
decl_stmt|;
name|iMajors
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
name|nrMajors
condition|;
name|i
operator|++
control|)
name|iMajors
operator|.
name|add
argument_list|(
operator|(
name|String
operator|)
name|in
operator|.
name|readObject
argument_list|()
argument_list|)
expr_stmt|;
name|iLimit
operator|=
name|in
operator|.
name|readInt
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
name|super
operator|.
name|writeExternal
argument_list|(
name|out
argument_list|)
expr_stmt|;
name|out
operator|.
name|writeObject
argument_list|(
name|iAcadArea
argument_list|)
expr_stmt|;
name|out
operator|.
name|writeInt
argument_list|(
name|iClassifications
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
for|for
control|(
name|String
name|clasf
range|:
name|iClassifications
control|)
name|out
operator|.
name|writeObject
argument_list|(
name|clasf
argument_list|)
expr_stmt|;
name|out
operator|.
name|writeInt
argument_list|(
name|iMajors
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
for|for
control|(
name|String
name|major
range|:
name|iMajors
control|)
name|out
operator|.
name|writeObject
argument_list|(
name|major
argument_list|)
expr_stmt|;
name|out
operator|.
name|writeInt
argument_list|(
name|iLimit
argument_list|)
expr_stmt|;
block|}
specifier|public
specifier|static
class|class
name|XCurriculumReservationSerializer
implements|implements
name|Externalizer
argument_list|<
name|XCurriculumReservation
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
name|XCurriculumReservation
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
name|XCurriculumReservation
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
name|XCurriculumReservation
argument_list|(
name|input
argument_list|)
return|;
block|}
block|}
block|}
end_class

end_unit

