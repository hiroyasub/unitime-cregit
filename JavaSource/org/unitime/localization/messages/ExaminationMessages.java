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
name|localization
operator|.
name|messages
package|;
end_package

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_interface
specifier|public
interface|interface
name|ExaminationMessages
extends|extends
name|Messages
block|{
annotation|@
name|DefaultMessage
argument_list|(
literal|"Normal"
argument_list|)
name|String
name|seatingNormal
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Exam"
argument_list|)
name|String
name|seatingExam
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Final"
argument_list|)
name|String
name|typeFinal
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Midterm"
argument_list|)
name|String
name|typeMidterm
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

