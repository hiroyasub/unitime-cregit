begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.4 (University Timetabling Application)  * Copyright (C) 2012, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
end_comment

begin_package
package|package
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|spring
operator|.
name|security
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
name|apache
operator|.
name|commons
operator|.
name|logging
operator|.
name|Log
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|logging
operator|.
name|LogFactory
import|;
end_import

begin_import
import|import
name|org
operator|.
name|springframework
operator|.
name|beans
operator|.
name|factory
operator|.
name|annotation
operator|.
name|Autowired
import|;
end_import

begin_import
import|import
name|org
operator|.
name|springframework
operator|.
name|security
operator|.
name|access
operator|.
name|PermissionEvaluator
import|;
end_import

begin_import
import|import
name|org
operator|.
name|springframework
operator|.
name|security
operator|.
name|core
operator|.
name|Authentication
import|;
end_import

begin_import
import|import
name|org
operator|.
name|springframework
operator|.
name|stereotype
operator|.
name|Service
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
name|security
operator|.
name|UserContext
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
name|security
operator|.
name|evaluation
operator|.
name|PermissionCheck
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
name|security
operator|.
name|rights
operator|.
name|Right
import|;
end_import

begin_class
annotation|@
name|Service
argument_list|(
literal|"unitimePermissionEvaluator"
argument_list|)
specifier|public
class|class
name|UniTimePermissionEvaluator
implements|implements
name|PermissionEvaluator
block|{
specifier|private
specifier|static
name|Log
name|sLog
init|=
name|LogFactory
operator|.
name|getLog
argument_list|(
name|UniTimePermissionEvaluator
operator|.
name|class
argument_list|)
decl_stmt|;
annotation|@
name|Autowired
name|PermissionCheck
name|unitimePermissionCheck
decl_stmt|;
annotation|@
name|Override
specifier|public
name|boolean
name|hasPermission
parameter_list|(
name|Authentication
name|authentication
parameter_list|,
name|Object
name|domainObject
parameter_list|,
name|Object
name|permission
parameter_list|)
block|{
try|try
block|{
name|UserContext
name|user
init|=
operator|(
name|UserContext
operator|)
name|authentication
operator|.
name|getPrincipal
argument_list|()
decl_stmt|;
name|Right
name|right
init|=
operator|(
name|permission
operator|instanceof
name|Right
condition|?
operator|(
name|Right
operator|)
name|permission
else|:
name|Right
operator|.
name|valueOf
argument_list|(
name|permission
operator|.
name|toString
argument_list|()
argument_list|)
operator|)
decl_stmt|;
return|return
name|unitimePermissionCheck
operator|.
name|checkPermission
argument_list|(
name|user
argument_list|,
name|domainObject
argument_list|,
name|right
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|sLog
operator|.
name|warn
argument_list|(
literal|"Failed to evaluate permission "
operator|+
name|permission
operator|+
literal|" for "
operator|+
name|domainObject
operator|+
literal|": "
operator|+
name|e
operator|.
name|getMessage
argument_list|()
argument_list|)
expr_stmt|;
return|return
literal|false
return|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|hasPermission
parameter_list|(
name|Authentication
name|authentication
parameter_list|,
name|Serializable
name|targetId
parameter_list|,
name|String
name|targetType
parameter_list|,
name|Object
name|permission
parameter_list|)
block|{
try|try
block|{
name|UserContext
name|user
init|=
operator|(
name|UserContext
operator|)
name|authentication
operator|.
name|getPrincipal
argument_list|()
decl_stmt|;
name|Right
name|right
init|=
operator|(
name|permission
operator|instanceof
name|Right
condition|?
operator|(
name|Right
operator|)
name|permission
else|:
name|Right
operator|.
name|valueOf
argument_list|(
name|permission
operator|.
name|toString
argument_list|()
argument_list|)
operator|)
decl_stmt|;
return|return
name|unitimePermissionCheck
operator|.
name|checkPermission
argument_list|(
name|user
argument_list|,
name|targetId
argument_list|,
name|targetType
argument_list|,
name|right
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|sLog
operator|.
name|warn
argument_list|(
literal|"Failed to evaluate permission "
operator|+
name|permission
operator|+
literal|" for "
operator|+
name|targetType
operator|+
literal|"@ "
operator|+
name|targetId
operator|+
literal|": "
operator|+
name|e
operator|.
name|getMessage
argument_list|()
argument_list|)
expr_stmt|;
return|return
literal|false
return|;
block|}
block|}
block|}
end_class

end_unit

