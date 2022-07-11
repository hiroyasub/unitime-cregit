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
name|form
package|;
end_package

begin_import
import|import
name|javax
operator|.
name|servlet
operator|.
name|http
operator|.
name|HttpServletRequest
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
name|action
operator|.
name|UniTimeAction
import|;
end_import

begin_comment
comment|/**   * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|LastChangesForm
implements|implements
name|UniTimeForm
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|3633681949556250656L
decl_stmt|;
specifier|private
name|String
name|iOp
decl_stmt|;
specifier|private
name|int
name|iN
decl_stmt|;
specifier|private
name|Long
name|iDepartmentId
decl_stmt|,
name|iSubjAreaId
decl_stmt|,
name|iManagerId
decl_stmt|;
annotation|@
name|Override
specifier|public
name|void
name|reset
parameter_list|()
block|{
name|iOp
operator|=
literal|null
expr_stmt|;
name|iN
operator|=
literal|100
expr_stmt|;
name|iDepartmentId
operator|=
name|Long
operator|.
name|valueOf
argument_list|(
operator|-
literal|1
argument_list|)
expr_stmt|;
name|iSubjAreaId
operator|=
name|Long
operator|.
name|valueOf
argument_list|(
operator|-
literal|1
argument_list|)
expr_stmt|;
name|iManagerId
operator|=
name|Long
operator|.
name|valueOf
argument_list|(
operator|-
literal|1
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|validate
parameter_list|(
name|UniTimeAction
name|action
parameter_list|)
block|{
block|}
specifier|public
name|String
name|getOp
parameter_list|()
block|{
return|return
name|iOp
return|;
block|}
specifier|public
name|void
name|setOp
parameter_list|(
name|String
name|op
parameter_list|)
block|{
name|iOp
operator|=
name|op
expr_stmt|;
block|}
specifier|public
name|int
name|getN
parameter_list|()
block|{
return|return
name|iN
return|;
block|}
specifier|public
name|void
name|setN
parameter_list|(
name|int
name|n
parameter_list|)
block|{
name|iN
operator|=
name|n
expr_stmt|;
block|}
specifier|public
name|Long
name|getDepartmentId
parameter_list|()
block|{
return|return
name|iDepartmentId
return|;
block|}
specifier|public
name|void
name|setDepartmentId
parameter_list|(
name|Long
name|departmentId
parameter_list|)
block|{
name|iDepartmentId
operator|=
name|departmentId
expr_stmt|;
block|}
specifier|public
name|Long
name|getSubjAreaId
parameter_list|()
block|{
return|return
name|iSubjAreaId
return|;
block|}
specifier|public
name|void
name|setSubjAreaId
parameter_list|(
name|Long
name|subjAreaId
parameter_list|)
block|{
name|iSubjAreaId
operator|=
name|subjAreaId
expr_stmt|;
block|}
specifier|public
name|Long
name|getManagerId
parameter_list|()
block|{
return|return
name|iManagerId
return|;
block|}
specifier|public
name|void
name|setManagerId
parameter_list|(
name|Long
name|managerId
parameter_list|)
block|{
name|iManagerId
operator|=
name|managerId
expr_stmt|;
block|}
specifier|public
name|void
name|load
parameter_list|(
name|HttpServletRequest
name|request
parameter_list|)
block|{
name|Integer
name|n
init|=
operator|(
name|Integer
operator|)
name|request
operator|.
name|getSession
argument_list|()
operator|.
name|getAttribute
argument_list|(
literal|"LastChanges.N"
argument_list|)
decl_stmt|;
name|setN
argument_list|(
name|n
operator|==
literal|null
condition|?
literal|100
else|:
name|n
operator|.
name|intValue
argument_list|()
argument_list|)
expr_stmt|;
name|setDepartmentId
argument_list|(
operator|(
name|Long
operator|)
name|request
operator|.
name|getSession
argument_list|()
operator|.
name|getAttribute
argument_list|(
literal|"LastChanges.DepartmentId"
argument_list|)
argument_list|)
expr_stmt|;
name|setSubjAreaId
argument_list|(
operator|(
name|Long
operator|)
name|request
operator|.
name|getSession
argument_list|()
operator|.
name|getAttribute
argument_list|(
literal|"LastChanges.SubjAreaId"
argument_list|)
argument_list|)
expr_stmt|;
name|setManagerId
argument_list|(
operator|(
name|Long
operator|)
name|request
operator|.
name|getSession
argument_list|()
operator|.
name|getAttribute
argument_list|(
literal|"LastChanges.ManagerId"
argument_list|)
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|save
parameter_list|(
name|HttpServletRequest
name|request
parameter_list|)
block|{
name|request
operator|.
name|getSession
argument_list|()
operator|.
name|setAttribute
argument_list|(
literal|"LastChanges.N"
argument_list|,
name|Integer
operator|.
name|valueOf
argument_list|(
name|getN
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|getDepartmentId
argument_list|()
operator|==
literal|null
condition|)
name|request
operator|.
name|getSession
argument_list|()
operator|.
name|removeAttribute
argument_list|(
literal|"LastChanges.DepartmentId"
argument_list|)
expr_stmt|;
else|else
name|request
operator|.
name|getSession
argument_list|()
operator|.
name|setAttribute
argument_list|(
literal|"LastChanges.DepartmentId"
argument_list|,
name|getDepartmentId
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|getSubjAreaId
argument_list|()
operator|==
literal|null
condition|)
name|request
operator|.
name|getSession
argument_list|()
operator|.
name|removeAttribute
argument_list|(
literal|"LastChanges.SubjAreaId"
argument_list|)
expr_stmt|;
else|else
name|request
operator|.
name|getSession
argument_list|()
operator|.
name|setAttribute
argument_list|(
literal|"LastChanges.SubjAreaId"
argument_list|,
name|getSubjAreaId
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|getManagerId
argument_list|()
operator|==
literal|null
condition|)
name|request
operator|.
name|getSession
argument_list|()
operator|.
name|removeAttribute
argument_list|(
literal|"LastChanges.ManagerId"
argument_list|)
expr_stmt|;
else|else
name|request
operator|.
name|getSession
argument_list|()
operator|.
name|setAttribute
argument_list|(
literal|"LastChanges.ManagerId"
argument_list|,
name|getManagerId
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

