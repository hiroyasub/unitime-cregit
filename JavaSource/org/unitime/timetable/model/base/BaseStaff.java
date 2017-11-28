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
name|PositionType
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
name|Staff
import|;
end_import

begin_comment
comment|/**  * Do not change this class. It has been automatically generated using ant create-model.  * @see org.unitime.commons.ant.CreateBaseModelFromXml  */
end_comment

begin_class
specifier|public
specifier|abstract
class|class
name|BaseStaff
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
name|String
name|iExternalUniqueId
decl_stmt|;
specifier|private
name|String
name|iFirstName
decl_stmt|;
specifier|private
name|String
name|iMiddleName
decl_stmt|;
specifier|private
name|String
name|iLastName
decl_stmt|;
specifier|private
name|String
name|iDept
decl_stmt|;
specifier|private
name|String
name|iEmail
decl_stmt|;
specifier|private
name|String
name|iAcademicTitle
decl_stmt|;
specifier|private
name|String
name|iCampus
decl_stmt|;
specifier|private
name|PositionType
name|iPositionType
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
name|PROP_EXTERNAL_UID
init|=
literal|"externalUniqueId"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_FNAME
init|=
literal|"firstName"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_MNAME
init|=
literal|"middleName"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_LNAME
init|=
literal|"lastName"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_DEPT
init|=
literal|"dept"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_EMAIL
init|=
literal|"email"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_ACAD_TITLE
init|=
literal|"academicTitle"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_CAMPUS
init|=
literal|"campus"
decl_stmt|;
specifier|public
name|BaseStaff
parameter_list|()
block|{
name|initialize
argument_list|()
expr_stmt|;
block|}
specifier|public
name|BaseStaff
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
name|String
name|getExternalUniqueId
parameter_list|()
block|{
return|return
name|iExternalUniqueId
return|;
block|}
specifier|public
name|void
name|setExternalUniqueId
parameter_list|(
name|String
name|externalUniqueId
parameter_list|)
block|{
name|iExternalUniqueId
operator|=
name|externalUniqueId
expr_stmt|;
block|}
specifier|public
name|String
name|getFirstName
parameter_list|()
block|{
return|return
name|iFirstName
return|;
block|}
specifier|public
name|void
name|setFirstName
parameter_list|(
name|String
name|firstName
parameter_list|)
block|{
name|iFirstName
operator|=
name|firstName
expr_stmt|;
block|}
specifier|public
name|String
name|getMiddleName
parameter_list|()
block|{
return|return
name|iMiddleName
return|;
block|}
specifier|public
name|void
name|setMiddleName
parameter_list|(
name|String
name|middleName
parameter_list|)
block|{
name|iMiddleName
operator|=
name|middleName
expr_stmt|;
block|}
specifier|public
name|String
name|getLastName
parameter_list|()
block|{
return|return
name|iLastName
return|;
block|}
specifier|public
name|void
name|setLastName
parameter_list|(
name|String
name|lastName
parameter_list|)
block|{
name|iLastName
operator|=
name|lastName
expr_stmt|;
block|}
specifier|public
name|String
name|getDept
parameter_list|()
block|{
return|return
name|iDept
return|;
block|}
specifier|public
name|void
name|setDept
parameter_list|(
name|String
name|dept
parameter_list|)
block|{
name|iDept
operator|=
name|dept
expr_stmt|;
block|}
specifier|public
name|String
name|getEmail
parameter_list|()
block|{
return|return
name|iEmail
return|;
block|}
specifier|public
name|void
name|setEmail
parameter_list|(
name|String
name|email
parameter_list|)
block|{
name|iEmail
operator|=
name|email
expr_stmt|;
block|}
specifier|public
name|String
name|getAcademicTitle
parameter_list|()
block|{
return|return
name|iAcademicTitle
return|;
block|}
specifier|public
name|void
name|setAcademicTitle
parameter_list|(
name|String
name|academicTitle
parameter_list|)
block|{
name|iAcademicTitle
operator|=
name|academicTitle
expr_stmt|;
block|}
specifier|public
name|String
name|getCampus
parameter_list|()
block|{
return|return
name|iCampus
return|;
block|}
specifier|public
name|void
name|setCampus
parameter_list|(
name|String
name|campus
parameter_list|)
block|{
name|iCampus
operator|=
name|campus
expr_stmt|;
block|}
specifier|public
name|PositionType
name|getPositionType
parameter_list|()
block|{
return|return
name|iPositionType
return|;
block|}
specifier|public
name|void
name|setPositionType
parameter_list|(
name|PositionType
name|positionType
parameter_list|)
block|{
name|iPositionType
operator|=
name|positionType
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
name|Staff
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
name|Staff
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
name|Staff
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
literal|"Staff["
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
literal|"Staff["
operator|+
literal|"\n	AcademicTitle: "
operator|+
name|getAcademicTitle
argument_list|()
operator|+
literal|"\n	Campus: "
operator|+
name|getCampus
argument_list|()
operator|+
literal|"\n	Dept: "
operator|+
name|getDept
argument_list|()
operator|+
literal|"\n	Email: "
operator|+
name|getEmail
argument_list|()
operator|+
literal|"\n	ExternalUniqueId: "
operator|+
name|getExternalUniqueId
argument_list|()
operator|+
literal|"\n	FirstName: "
operator|+
name|getFirstName
argument_list|()
operator|+
literal|"\n	LastName: "
operator|+
name|getLastName
argument_list|()
operator|+
literal|"\n	MiddleName: "
operator|+
name|getMiddleName
argument_list|()
operator|+
literal|"\n	PositionType: "
operator|+
name|getPositionType
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

