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
name|org
operator|.
name|cpsolver
operator|.
name|ifs
operator|.
name|util
operator|.
name|ToolBox
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
name|AreaClassificationMajor
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
name|StudentAreaClassificationMajor
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
name|StudentAreaClassificationMinor
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
annotation|@
name|SerializeWith
argument_list|(
name|XAreaClassificationMajor
operator|.
name|XAreaClassificationMajorSerializer
operator|.
name|class
argument_list|)
specifier|public
class|class
name|XAreaClassificationMajor
implements|implements
name|Serializable
implements|,
name|Externalizable
implements|,
name|Comparable
argument_list|<
name|XAreaClassificationMajor
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
specifier|private
name|String
name|iAreaCode
decl_stmt|,
name|iClassificationCode
decl_stmt|,
name|iMajorCode
decl_stmt|,
name|iConcentrationCode
decl_stmt|,
name|iDegreeCode
decl_stmt|,
name|iProgramCode
decl_stmt|;
specifier|private
name|String
name|iAreaLabel
decl_stmt|,
name|iClassificationLabel
decl_stmt|,
name|iMajorLabel
decl_stmt|,
name|iConcentrationLabel
decl_stmt|,
name|iDegreeLabel
decl_stmt|,
name|iProgramLabel
decl_stmt|;
specifier|private
name|double
name|iWeight
init|=
literal|1.0
decl_stmt|;
specifier|public
name|XAreaClassificationMajor
parameter_list|()
block|{
block|}
specifier|public
name|XAreaClassificationMajor
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
name|XAreaClassificationMajor
parameter_list|(
name|String
name|area
parameter_list|,
name|String
name|classification
parameter_list|,
name|String
name|major
parameter_list|)
block|{
name|iAreaCode
operator|=
name|area
expr_stmt|;
name|iClassificationCode
operator|=
name|classification
expr_stmt|;
name|iMajorCode
operator|=
name|major
expr_stmt|;
block|}
specifier|public
name|XAreaClassificationMajor
parameter_list|(
name|StudentAreaClassificationMinor
name|acm
parameter_list|)
block|{
name|iAreaCode
operator|=
name|acm
operator|.
name|getAcademicArea
argument_list|()
operator|.
name|getAcademicAreaAbbreviation
argument_list|()
expr_stmt|;
name|iAreaLabel
operator|=
name|acm
operator|.
name|getAcademicArea
argument_list|()
operator|.
name|getTitle
argument_list|()
expr_stmt|;
name|iClassificationCode
operator|=
name|acm
operator|.
name|getAcademicClassification
argument_list|()
operator|.
name|getCode
argument_list|()
expr_stmt|;
name|iClassificationLabel
operator|=
name|acm
operator|.
name|getAcademicClassification
argument_list|()
operator|.
name|getName
argument_list|()
expr_stmt|;
name|iMajorCode
operator|=
name|acm
operator|.
name|getMinor
argument_list|()
operator|.
name|getCode
argument_list|()
expr_stmt|;
name|iMajorLabel
operator|=
name|acm
operator|.
name|getMinor
argument_list|()
operator|.
name|getName
argument_list|()
expr_stmt|;
block|}
specifier|public
name|XAreaClassificationMajor
parameter_list|(
name|StudentAreaClassificationMajor
name|acm
parameter_list|)
block|{
name|iAreaCode
operator|=
name|acm
operator|.
name|getAcademicArea
argument_list|()
operator|.
name|getAcademicAreaAbbreviation
argument_list|()
expr_stmt|;
name|iAreaLabel
operator|=
name|acm
operator|.
name|getAcademicArea
argument_list|()
operator|.
name|getTitle
argument_list|()
expr_stmt|;
name|iClassificationCode
operator|=
name|acm
operator|.
name|getAcademicClassification
argument_list|()
operator|.
name|getCode
argument_list|()
expr_stmt|;
name|iClassificationLabel
operator|=
name|acm
operator|.
name|getAcademicClassification
argument_list|()
operator|.
name|getName
argument_list|()
expr_stmt|;
name|iMajorCode
operator|=
name|acm
operator|.
name|getMajor
argument_list|()
operator|.
name|getCode
argument_list|()
expr_stmt|;
name|iMajorLabel
operator|=
name|acm
operator|.
name|getMajor
argument_list|()
operator|.
name|getName
argument_list|()
expr_stmt|;
if|if
condition|(
name|acm
operator|.
name|getConcentration
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|iConcentrationCode
operator|=
name|acm
operator|.
name|getConcentration
argument_list|()
operator|.
name|getCode
argument_list|()
expr_stmt|;
name|iConcentrationLabel
operator|=
name|acm
operator|.
name|getConcentration
argument_list|()
operator|.
name|getName
argument_list|()
expr_stmt|;
block|}
if|if
condition|(
name|acm
operator|.
name|getDegree
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|iDegreeCode
operator|=
name|acm
operator|.
name|getDegree
argument_list|()
operator|.
name|getReference
argument_list|()
expr_stmt|;
name|iDegreeLabel
operator|=
name|acm
operator|.
name|getDegree
argument_list|()
operator|.
name|getLabel
argument_list|()
expr_stmt|;
block|}
if|if
condition|(
name|acm
operator|.
name|getProgram
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|iProgramCode
operator|=
name|acm
operator|.
name|getProgram
argument_list|()
operator|.
name|getReference
argument_list|()
expr_stmt|;
name|iProgramLabel
operator|=
name|acm
operator|.
name|getProgram
argument_list|()
operator|.
name|getLabel
argument_list|()
expr_stmt|;
block|}
if|if
condition|(
name|acm
operator|.
name|getWeight
argument_list|()
operator|!=
literal|null
condition|)
name|iWeight
operator|=
name|acm
operator|.
name|getWeight
argument_list|()
expr_stmt|;
block|}
specifier|public
name|XAreaClassificationMajor
parameter_list|(
name|AreaClassificationMajor
name|acm
parameter_list|)
block|{
name|iAreaCode
operator|=
name|acm
operator|.
name|getArea
argument_list|()
expr_stmt|;
name|iAreaLabel
operator|=
name|acm
operator|.
name|getAreaName
argument_list|()
expr_stmt|;
name|iClassificationCode
operator|=
name|acm
operator|.
name|getClassification
argument_list|()
expr_stmt|;
name|iClassificationLabel
operator|=
name|acm
operator|.
name|getClassificationName
argument_list|()
expr_stmt|;
name|iMajorCode
operator|=
name|acm
operator|.
name|getMajor
argument_list|()
expr_stmt|;
name|iMajorLabel
operator|=
name|acm
operator|.
name|getMajorName
argument_list|()
expr_stmt|;
name|iConcentrationCode
operator|=
name|acm
operator|.
name|getConcentration
argument_list|()
expr_stmt|;
name|iConcentrationLabel
operator|=
name|acm
operator|.
name|getConcentrationName
argument_list|()
expr_stmt|;
name|iDegreeCode
operator|=
name|acm
operator|.
name|getDegree
argument_list|()
expr_stmt|;
name|iDegreeLabel
operator|=
name|acm
operator|.
name|getDegreeName
argument_list|()
expr_stmt|;
name|iProgramCode
operator|=
name|acm
operator|.
name|getProgram
argument_list|()
expr_stmt|;
name|iProgramLabel
operator|=
name|acm
operator|.
name|getProgramName
argument_list|()
expr_stmt|;
name|iWeight
operator|=
name|acm
operator|.
name|getWeight
argument_list|()
expr_stmt|;
block|}
comment|/** Academic area */
specifier|public
name|String
name|getArea
parameter_list|()
block|{
return|return
name|iAreaCode
return|;
block|}
specifier|public
name|String
name|getAreaLabel
parameter_list|()
block|{
return|return
name|iAreaLabel
return|;
block|}
comment|/** Classification */
specifier|public
name|String
name|getClassification
parameter_list|()
block|{
return|return
name|iClassificationCode
return|;
block|}
specifier|public
name|String
name|getClassificationLabel
parameter_list|()
block|{
return|return
name|iClassificationLabel
return|;
block|}
comment|/** Major */
specifier|public
name|String
name|getMajor
parameter_list|()
block|{
return|return
name|iMajorCode
return|;
block|}
specifier|public
name|String
name|getMajorLabel
parameter_list|()
block|{
return|return
name|iMajorLabel
return|;
block|}
comment|/** Concentration */
specifier|public
name|String
name|getConcentration
parameter_list|()
block|{
return|return
name|iConcentrationCode
return|;
block|}
specifier|public
name|String
name|getConcentrationNotNull
parameter_list|()
block|{
return|return
name|iConcentrationCode
operator|==
literal|null
condition|?
literal|""
else|:
name|iConcentrationCode
return|;
block|}
specifier|public
name|String
name|getConcentrationLabel
parameter_list|()
block|{
return|return
name|iConcentrationLabel
return|;
block|}
comment|/** Degree */
specifier|public
name|String
name|getDegree
parameter_list|()
block|{
return|return
name|iDegreeCode
return|;
block|}
specifier|public
name|String
name|getDegreeNotNull
parameter_list|()
block|{
return|return
name|iDegreeCode
operator|==
literal|null
condition|?
literal|""
else|:
name|iDegreeCode
return|;
block|}
specifier|public
name|String
name|getDegreeLabel
parameter_list|()
block|{
return|return
name|iDegreeLabel
return|;
block|}
comment|/** Program */
specifier|public
name|String
name|getProgram
parameter_list|()
block|{
return|return
name|iProgramCode
return|;
block|}
specifier|public
name|String
name|getProgramNotNull
parameter_list|()
block|{
return|return
name|iProgramCode
operator|==
literal|null
condition|?
literal|""
else|:
name|iProgramCode
return|;
block|}
specifier|public
name|String
name|getProgramLabel
parameter_list|()
block|{
return|return
name|iProgramLabel
return|;
block|}
comment|/** Weight */
specifier|public
name|double
name|getWeight
parameter_list|()
block|{
return|return
name|iWeight
return|;
block|}
empty_stmt|;
annotation|@
name|Override
specifier|public
name|int
name|hashCode
parameter_list|()
block|{
return|return
name|toString
argument_list|()
operator|.
name|hashCode
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
name|XAreaClassificationMajor
operator|)
condition|)
return|return
literal|false
return|;
name|XAreaClassificationMajor
name|acm
init|=
operator|(
name|XAreaClassificationMajor
operator|)
name|o
decl_stmt|;
return|return
name|ToolBox
operator|.
name|equals
argument_list|(
name|acm
operator|.
name|getArea
argument_list|()
argument_list|,
name|getArea
argument_list|()
argument_list|)
operator|&&
name|ToolBox
operator|.
name|equals
argument_list|(
name|acm
operator|.
name|getClassification
argument_list|()
argument_list|,
name|getClassification
argument_list|()
argument_list|)
operator|&&
name|ToolBox
operator|.
name|equals
argument_list|(
name|acm
operator|.
name|getMajor
argument_list|()
argument_list|,
name|getMajor
argument_list|()
argument_list|)
operator|&&
name|ToolBox
operator|.
name|equals
argument_list|(
name|acm
operator|.
name|getConcentration
argument_list|()
argument_list|,
name|getConcentration
argument_list|()
argument_list|)
operator|&&
name|ToolBox
operator|.
name|equals
argument_list|(
name|acm
operator|.
name|getDegree
argument_list|()
argument_list|,
name|getDegree
argument_list|()
argument_list|)
operator|&&
name|ToolBox
operator|.
name|equals
argument_list|(
name|acm
operator|.
name|getProgram
argument_list|()
argument_list|,
name|getProgram
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
name|getArea
argument_list|()
operator|+
literal|"/"
operator|+
name|getMajor
argument_list|()
operator|+
operator|(
name|getConcentration
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
literal|"-"
operator|+
name|getConcentration
argument_list|()
operator|)
operator|+
literal|" "
operator|+
name|getClassification
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
name|iAreaCode
operator|=
operator|(
name|String
operator|)
name|in
operator|.
name|readObject
argument_list|()
expr_stmt|;
name|iAreaLabel
operator|=
operator|(
name|String
operator|)
name|in
operator|.
name|readObject
argument_list|()
expr_stmt|;
name|iClassificationCode
operator|=
operator|(
name|String
operator|)
name|in
operator|.
name|readObject
argument_list|()
expr_stmt|;
name|iClassificationLabel
operator|=
operator|(
name|String
operator|)
name|in
operator|.
name|readObject
argument_list|()
expr_stmt|;
name|iMajorCode
operator|=
operator|(
name|String
operator|)
name|in
operator|.
name|readObject
argument_list|()
expr_stmt|;
name|iMajorLabel
operator|=
operator|(
name|String
operator|)
name|in
operator|.
name|readObject
argument_list|()
expr_stmt|;
name|iConcentrationCode
operator|=
operator|(
name|String
operator|)
name|in
operator|.
name|readObject
argument_list|()
expr_stmt|;
name|iConcentrationLabel
operator|=
operator|(
name|String
operator|)
name|in
operator|.
name|readObject
argument_list|()
expr_stmt|;
name|iDegreeCode
operator|=
operator|(
name|String
operator|)
name|in
operator|.
name|readObject
argument_list|()
expr_stmt|;
name|iDegreeLabel
operator|=
operator|(
name|String
operator|)
name|in
operator|.
name|readObject
argument_list|()
expr_stmt|;
name|iProgramCode
operator|=
operator|(
name|String
operator|)
name|in
operator|.
name|readObject
argument_list|()
expr_stmt|;
name|iProgramLabel
operator|=
operator|(
name|String
operator|)
name|in
operator|.
name|readObject
argument_list|()
expr_stmt|;
name|iWeight
operator|=
name|in
operator|.
name|readDouble
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
name|writeObject
argument_list|(
name|iAreaCode
argument_list|)
expr_stmt|;
name|out
operator|.
name|writeObject
argument_list|(
name|iAreaLabel
argument_list|)
expr_stmt|;
name|out
operator|.
name|writeObject
argument_list|(
name|iClassificationCode
argument_list|)
expr_stmt|;
name|out
operator|.
name|writeObject
argument_list|(
name|iClassificationLabel
argument_list|)
expr_stmt|;
name|out
operator|.
name|writeObject
argument_list|(
name|iMajorCode
argument_list|)
expr_stmt|;
name|out
operator|.
name|writeObject
argument_list|(
name|iMajorLabel
argument_list|)
expr_stmt|;
name|out
operator|.
name|writeObject
argument_list|(
name|iConcentrationCode
argument_list|)
expr_stmt|;
name|out
operator|.
name|writeObject
argument_list|(
name|iConcentrationLabel
argument_list|)
expr_stmt|;
name|out
operator|.
name|writeObject
argument_list|(
name|iDegreeCode
argument_list|)
expr_stmt|;
name|out
operator|.
name|writeObject
argument_list|(
name|iDegreeLabel
argument_list|)
expr_stmt|;
name|out
operator|.
name|writeObject
argument_list|(
name|iProgramCode
argument_list|)
expr_stmt|;
name|out
operator|.
name|writeObject
argument_list|(
name|iProgramLabel
argument_list|)
expr_stmt|;
name|out
operator|.
name|writeDouble
argument_list|(
name|iWeight
argument_list|)
expr_stmt|;
block|}
specifier|public
specifier|static
class|class
name|XAreaClassificationMajorSerializer
implements|implements
name|Externalizer
argument_list|<
name|XAreaClassificationMajor
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
name|XAreaClassificationMajor
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
name|XAreaClassificationMajor
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
name|XAreaClassificationMajor
argument_list|(
name|input
argument_list|)
return|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|int
name|compareTo
parameter_list|(
name|XAreaClassificationMajor
name|acm
parameter_list|)
block|{
if|if
condition|(
name|getWeight
argument_list|()
operator|!=
name|acm
operator|.
name|getWeight
argument_list|()
condition|)
return|return
name|getWeight
argument_list|()
operator|>
name|acm
operator|.
name|getWeight
argument_list|()
condition|?
operator|-
literal|1
else|:
literal|1
return|;
if|if
condition|(
operator|!
name|getArea
argument_list|()
operator|.
name|equals
argument_list|(
name|acm
operator|.
name|getArea
argument_list|()
argument_list|)
condition|)
return|return
name|getArea
argument_list|()
operator|.
name|compareTo
argument_list|(
name|acm
operator|.
name|getArea
argument_list|()
argument_list|)
return|;
if|if
condition|(
operator|!
name|getClassification
argument_list|()
operator|.
name|equals
argument_list|(
name|acm
operator|.
name|getClassification
argument_list|()
argument_list|)
condition|)
return|return
name|getClassification
argument_list|()
operator|.
name|compareTo
argument_list|(
name|acm
operator|.
name|getClassification
argument_list|()
argument_list|)
return|;
if|if
condition|(
operator|!
name|getDegreeNotNull
argument_list|()
operator|.
name|equals
argument_list|(
name|acm
operator|.
name|getDegreeNotNull
argument_list|()
argument_list|)
condition|)
return|return
name|getDegreeNotNull
argument_list|()
operator|.
name|compareTo
argument_list|(
name|acm
operator|.
name|getDegreeNotNull
argument_list|()
argument_list|)
return|;
if|if
condition|(
operator|!
name|getProgramNotNull
argument_list|()
operator|.
name|equals
argument_list|(
name|acm
operator|.
name|getProgramNotNull
argument_list|()
argument_list|)
condition|)
return|return
name|getProgramNotNull
argument_list|()
operator|.
name|compareTo
argument_list|(
name|acm
operator|.
name|getProgramNotNull
argument_list|()
argument_list|)
return|;
if|if
condition|(
operator|!
name|getMajor
argument_list|()
operator|.
name|equals
argument_list|(
name|acm
operator|.
name|getMajor
argument_list|()
argument_list|)
condition|)
return|return
name|getMajor
argument_list|()
operator|.
name|compareTo
argument_list|(
name|acm
operator|.
name|getMajor
argument_list|()
argument_list|)
return|;
return|return
name|getConcentrationNotNull
argument_list|()
operator|.
name|compareTo
argument_list|(
name|acm
operator|.
name|getConcentrationNotNull
argument_list|()
argument_list|)
return|;
block|}
block|}
end_class

end_unit

