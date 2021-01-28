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
name|iArea
decl_stmt|,
name|iClassification
decl_stmt|,
name|iMajor
decl_stmt|,
name|iConcentration
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
name|iArea
operator|=
name|area
expr_stmt|;
name|iClassification
operator|=
name|classification
expr_stmt|;
name|iMajor
operator|=
name|major
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
parameter_list|,
name|String
name|concentration
parameter_list|)
block|{
name|iArea
operator|=
name|area
expr_stmt|;
name|iClassification
operator|=
name|classification
expr_stmt|;
name|iMajor
operator|=
name|major
expr_stmt|;
name|iConcentration
operator|=
name|concentration
expr_stmt|;
block|}
comment|/** Academic area */
specifier|public
name|String
name|getArea
parameter_list|()
block|{
return|return
name|iArea
return|;
block|}
comment|/** Classification */
specifier|public
name|String
name|getClassification
parameter_list|()
block|{
return|return
name|iClassification
return|;
block|}
comment|/** Major */
specifier|public
name|String
name|getMajor
parameter_list|()
block|{
return|return
name|iMajor
return|;
block|}
comment|/** Concentration */
specifier|public
name|String
name|getConcentration
parameter_list|()
block|{
return|return
name|iConcentration
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
name|iArea
operator|+
literal|":"
operator|+
name|iClassification
operator|+
literal|":"
operator|+
name|iMajor
operator|)
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
name|iArea
operator|=
operator|(
name|String
operator|)
name|in
operator|.
name|readObject
argument_list|()
expr_stmt|;
name|iClassification
operator|=
operator|(
name|String
operator|)
name|in
operator|.
name|readObject
argument_list|()
expr_stmt|;
name|iMajor
operator|=
operator|(
name|String
operator|)
name|in
operator|.
name|readObject
argument_list|()
expr_stmt|;
name|iConcentration
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
name|writeObject
argument_list|(
name|iArea
argument_list|)
expr_stmt|;
name|out
operator|.
name|writeObject
argument_list|(
name|iClassification
argument_list|)
expr_stmt|;
name|out
operator|.
name|writeObject
argument_list|(
name|iMajor
argument_list|)
expr_stmt|;
name|out
operator|.
name|writeObject
argument_list|(
name|iConcentration
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
block|}
block|}
end_class

end_unit

