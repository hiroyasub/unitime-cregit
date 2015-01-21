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
name|ExactTimeMins
import|;
end_import

begin_comment
comment|/**  * Do not change this class. It has been automatically generated using ant create-model.  * @see org.unitime.commons.ant.CreateBaseModelFromXml  */
end_comment

begin_class
specifier|public
specifier|abstract
class|class
name|BaseExactTimeMins
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
name|iMinsPerMtgMin
decl_stmt|;
specifier|private
name|Integer
name|iMinsPerMtgMax
decl_stmt|;
specifier|private
name|Integer
name|iNrSlots
decl_stmt|;
specifier|private
name|Integer
name|iBreakTime
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
name|PROP_MINS_MIN
init|=
literal|"minsPerMtgMin"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_MINS_MAX
init|=
literal|"minsPerMtgMax"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_NR_SLOTS
init|=
literal|"nrSlots"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_BREAK_TIME
init|=
literal|"breakTime"
decl_stmt|;
specifier|public
name|BaseExactTimeMins
parameter_list|()
block|{
name|initialize
argument_list|()
expr_stmt|;
block|}
specifier|public
name|BaseExactTimeMins
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
name|getMinsPerMtgMin
parameter_list|()
block|{
return|return
name|iMinsPerMtgMin
return|;
block|}
specifier|public
name|void
name|setMinsPerMtgMin
parameter_list|(
name|Integer
name|minsPerMtgMin
parameter_list|)
block|{
name|iMinsPerMtgMin
operator|=
name|minsPerMtgMin
expr_stmt|;
block|}
specifier|public
name|Integer
name|getMinsPerMtgMax
parameter_list|()
block|{
return|return
name|iMinsPerMtgMax
return|;
block|}
specifier|public
name|void
name|setMinsPerMtgMax
parameter_list|(
name|Integer
name|minsPerMtgMax
parameter_list|)
block|{
name|iMinsPerMtgMax
operator|=
name|minsPerMtgMax
expr_stmt|;
block|}
specifier|public
name|Integer
name|getNrSlots
parameter_list|()
block|{
return|return
name|iNrSlots
return|;
block|}
specifier|public
name|void
name|setNrSlots
parameter_list|(
name|Integer
name|nrSlots
parameter_list|)
block|{
name|iNrSlots
operator|=
name|nrSlots
expr_stmt|;
block|}
specifier|public
name|Integer
name|getBreakTime
parameter_list|()
block|{
return|return
name|iBreakTime
return|;
block|}
specifier|public
name|void
name|setBreakTime
parameter_list|(
name|Integer
name|breakTime
parameter_list|)
block|{
name|iBreakTime
operator|=
name|breakTime
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
name|ExactTimeMins
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
name|ExactTimeMins
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
name|ExactTimeMins
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
literal|"ExactTimeMins["
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
literal|"ExactTimeMins["
operator|+
literal|"\n	BreakTime: "
operator|+
name|getBreakTime
argument_list|()
operator|+
literal|"\n	MinsPerMtgMax: "
operator|+
name|getMinsPerMtgMax
argument_list|()
operator|+
literal|"\n	MinsPerMtgMin: "
operator|+
name|getMinsPerMtgMin
argument_list|()
operator|+
literal|"\n	NrSlots: "
operator|+
name|getNrSlots
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

