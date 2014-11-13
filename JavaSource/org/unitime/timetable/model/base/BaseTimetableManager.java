begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.2 - 3.5 (University Timetabling Application)  * Copyright (C) 2010 - 2014, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *  * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
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
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|Department
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
name|ManagerRole
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
name|ManagerSettings
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
name|SolverGroup
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
name|TimetableManager
import|;
end_import

begin_comment
comment|/**  * Do not change this class. It has been automatically generated using ant create-model.  * @see org.unitime.commons.ant.CreateBaseModelFromXml  */
end_comment

begin_class
specifier|public
specifier|abstract
class|class
name|BaseTimetableManager
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
name|iAcademicTitle
decl_stmt|;
specifier|private
name|String
name|iEmailAddress
decl_stmt|;
specifier|private
name|Set
argument_list|<
name|ManagerSettings
argument_list|>
name|iSettings
decl_stmt|;
specifier|private
name|Set
argument_list|<
name|Department
argument_list|>
name|iDepartments
decl_stmt|;
specifier|private
name|Set
argument_list|<
name|ManagerRole
argument_list|>
name|iManagerRoles
decl_stmt|;
specifier|private
name|Set
argument_list|<
name|SolverGroup
argument_list|>
name|iSolverGroups
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
name|PROP_FIRST_NAME
init|=
literal|"firstName"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_MIDDLE_NAME
init|=
literal|"middleName"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_LAST_NAME
init|=
literal|"lastName"
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
name|PROP_EMAIL_ADDRESS
init|=
literal|"emailAddress"
decl_stmt|;
specifier|public
name|BaseTimetableManager
parameter_list|()
block|{
name|initialize
argument_list|()
expr_stmt|;
block|}
specifier|public
name|BaseTimetableManager
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
name|getEmailAddress
parameter_list|()
block|{
return|return
name|iEmailAddress
return|;
block|}
specifier|public
name|void
name|setEmailAddress
parameter_list|(
name|String
name|emailAddress
parameter_list|)
block|{
name|iEmailAddress
operator|=
name|emailAddress
expr_stmt|;
block|}
specifier|public
name|Set
argument_list|<
name|ManagerSettings
argument_list|>
name|getSettings
parameter_list|()
block|{
return|return
name|iSettings
return|;
block|}
specifier|public
name|void
name|setSettings
parameter_list|(
name|Set
argument_list|<
name|ManagerSettings
argument_list|>
name|settings
parameter_list|)
block|{
name|iSettings
operator|=
name|settings
expr_stmt|;
block|}
specifier|public
name|void
name|addTosettings
parameter_list|(
name|ManagerSettings
name|managerSettings
parameter_list|)
block|{
if|if
condition|(
name|iSettings
operator|==
literal|null
condition|)
name|iSettings
operator|=
operator|new
name|HashSet
argument_list|<
name|ManagerSettings
argument_list|>
argument_list|()
expr_stmt|;
name|iSettings
operator|.
name|add
argument_list|(
name|managerSettings
argument_list|)
expr_stmt|;
block|}
specifier|public
name|Set
argument_list|<
name|Department
argument_list|>
name|getDepartments
parameter_list|()
block|{
return|return
name|iDepartments
return|;
block|}
specifier|public
name|void
name|setDepartments
parameter_list|(
name|Set
argument_list|<
name|Department
argument_list|>
name|departments
parameter_list|)
block|{
name|iDepartments
operator|=
name|departments
expr_stmt|;
block|}
specifier|public
name|void
name|addTodepartments
parameter_list|(
name|Department
name|department
parameter_list|)
block|{
if|if
condition|(
name|iDepartments
operator|==
literal|null
condition|)
name|iDepartments
operator|=
operator|new
name|HashSet
argument_list|<
name|Department
argument_list|>
argument_list|()
expr_stmt|;
name|iDepartments
operator|.
name|add
argument_list|(
name|department
argument_list|)
expr_stmt|;
block|}
specifier|public
name|Set
argument_list|<
name|ManagerRole
argument_list|>
name|getManagerRoles
parameter_list|()
block|{
return|return
name|iManagerRoles
return|;
block|}
specifier|public
name|void
name|setManagerRoles
parameter_list|(
name|Set
argument_list|<
name|ManagerRole
argument_list|>
name|managerRoles
parameter_list|)
block|{
name|iManagerRoles
operator|=
name|managerRoles
expr_stmt|;
block|}
specifier|public
name|void
name|addTomanagerRoles
parameter_list|(
name|ManagerRole
name|managerRole
parameter_list|)
block|{
if|if
condition|(
name|iManagerRoles
operator|==
literal|null
condition|)
name|iManagerRoles
operator|=
operator|new
name|HashSet
argument_list|<
name|ManagerRole
argument_list|>
argument_list|()
expr_stmt|;
name|iManagerRoles
operator|.
name|add
argument_list|(
name|managerRole
argument_list|)
expr_stmt|;
block|}
specifier|public
name|Set
argument_list|<
name|SolverGroup
argument_list|>
name|getSolverGroups
parameter_list|()
block|{
return|return
name|iSolverGroups
return|;
block|}
specifier|public
name|void
name|setSolverGroups
parameter_list|(
name|Set
argument_list|<
name|SolverGroup
argument_list|>
name|solverGroups
parameter_list|)
block|{
name|iSolverGroups
operator|=
name|solverGroups
expr_stmt|;
block|}
specifier|public
name|void
name|addTosolverGroups
parameter_list|(
name|SolverGroup
name|solverGroup
parameter_list|)
block|{
if|if
condition|(
name|iSolverGroups
operator|==
literal|null
condition|)
name|iSolverGroups
operator|=
operator|new
name|HashSet
argument_list|<
name|SolverGroup
argument_list|>
argument_list|()
expr_stmt|;
name|iSolverGroups
operator|.
name|add
argument_list|(
name|solverGroup
argument_list|)
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
name|TimetableManager
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
name|TimetableManager
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
name|TimetableManager
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
literal|"TimetableManager["
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
literal|"TimetableManager["
operator|+
literal|"\n	AcademicTitle: "
operator|+
name|getAcademicTitle
argument_list|()
operator|+
literal|"\n	EmailAddress: "
operator|+
name|getEmailAddress
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

