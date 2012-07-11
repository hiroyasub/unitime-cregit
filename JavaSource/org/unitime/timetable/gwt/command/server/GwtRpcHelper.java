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
name|gwt
operator|.
name|command
operator|.
name|server
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
name|javax
operator|.
name|servlet
operator|.
name|http
operator|.
name|HttpSession
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
name|defaults
operator|.
name|SessionAttribute
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
name|SessionContext
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
specifier|public
class|class
name|GwtRpcHelper
implements|implements
name|SessionContext
block|{
specifier|private
name|UserContext
name|iUser
decl_stmt|;
specifier|private
name|String
name|iHttpSessionId
decl_stmt|;
specifier|private
name|boolean
name|iHttpSessionNew
decl_stmt|;
specifier|private
name|PermissionCheck
name|iCheck
decl_stmt|;
specifier|public
name|GwtRpcHelper
parameter_list|(
name|SessionContext
name|context
parameter_list|,
name|PermissionCheck
name|check
parameter_list|)
block|{
name|iUser
operator|=
name|context
operator|.
name|getUser
argument_list|()
expr_stmt|;
name|iHttpSessionId
operator|=
name|context
operator|.
name|getHttpSessionId
argument_list|()
expr_stmt|;
name|iHttpSessionNew
operator|=
name|context
operator|.
name|isHttpSessionNew
argument_list|()
expr_stmt|;
name|iCheck
operator|=
name|check
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|isAuthenticated
parameter_list|()
block|{
return|return
name|iUser
operator|!=
literal|null
return|;
block|}
annotation|@
name|Override
specifier|public
name|UserContext
name|getUser
parameter_list|()
block|{
return|return
name|iUser
return|;
block|}
annotation|@
name|Override
specifier|public
name|HttpSession
name|getHttpSession
parameter_list|()
block|{
throw|throw
operator|new
name|RuntimeException
argument_list|(
literal|"Operation not supported."
argument_list|)
throw|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|isHttpSessionNew
parameter_list|()
block|{
return|return
name|iHttpSessionNew
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getHttpSessionId
parameter_list|()
block|{
return|return
name|iHttpSessionId
return|;
block|}
annotation|@
name|Override
specifier|public
name|Object
name|getAttribute
parameter_list|(
name|String
name|name
parameter_list|)
block|{
throw|throw
operator|new
name|RuntimeException
argument_list|(
literal|"Operation not supported."
argument_list|)
throw|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|removeAttribute
parameter_list|(
name|String
name|name
parameter_list|)
block|{
throw|throw
operator|new
name|RuntimeException
argument_list|(
literal|"Operation not supported."
argument_list|)
throw|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|setAttribute
parameter_list|(
name|String
name|name
parameter_list|,
name|Object
name|value
parameter_list|)
block|{
throw|throw
operator|new
name|RuntimeException
argument_list|(
literal|"Operation not supported."
argument_list|)
throw|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|removeAttribute
parameter_list|(
name|SessionAttribute
name|attribute
parameter_list|)
block|{
name|removeAttribute
argument_list|(
name|attribute
operator|.
name|name
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|setAttribute
parameter_list|(
name|SessionAttribute
name|attribute
parameter_list|,
name|Object
name|value
parameter_list|)
block|{
name|setAttribute
argument_list|(
name|attribute
operator|.
name|name
argument_list|()
argument_list|,
name|value
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|Object
name|getAttribute
parameter_list|(
name|SessionAttribute
name|attribute
parameter_list|)
block|{
name|Object
name|value
init|=
name|getAttribute
argument_list|(
name|attribute
operator|.
name|name
argument_list|()
argument_list|)
decl_stmt|;
return|return
operator|(
name|value
operator|!=
literal|null
condition|?
name|value
else|:
name|attribute
operator|.
name|defaultValue
argument_list|()
operator|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|HttpServletRequest
name|getHttpServletRequest
parameter_list|()
block|{
return|return
literal|null
return|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|checkPermission
parameter_list|(
name|Right
name|right
parameter_list|)
block|{
name|iCheck
operator|.
name|checkPermission
argument_list|(
name|getUser
argument_list|()
argument_list|,
literal|null
argument_list|,
literal|null
argument_list|,
name|right
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|checkPermission
parameter_list|(
name|Serializable
name|targetId
parameter_list|,
name|String
name|targetType
parameter_list|,
name|Right
name|right
parameter_list|)
block|{
name|iCheck
operator|.
name|checkPermission
argument_list|(
name|getUser
argument_list|()
argument_list|,
name|targetId
argument_list|,
name|targetType
argument_list|,
name|right
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|checkPermission
parameter_list|(
name|Object
name|targetObject
parameter_list|,
name|Right
name|right
parameter_list|)
block|{
name|iCheck
operator|.
name|checkPermission
argument_list|(
name|getUser
argument_list|()
argument_list|,
name|targetObject
argument_list|,
name|right
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|hasPermission
parameter_list|(
name|Right
name|right
parameter_list|)
block|{
try|try
block|{
name|iCheck
operator|.
name|checkPermission
argument_list|(
name|getUser
argument_list|()
argument_list|,
literal|null
argument_list|,
literal|null
argument_list|,
name|right
argument_list|)
expr_stmt|;
return|return
literal|true
return|;
block|}
catch|catch
parameter_list|(
name|SecurityException
name|e
parameter_list|)
block|{
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
name|Serializable
name|targetId
parameter_list|,
name|String
name|targetType
parameter_list|,
name|Right
name|right
parameter_list|)
block|{
try|try
block|{
name|iCheck
operator|.
name|checkPermission
argument_list|(
name|getUser
argument_list|()
argument_list|,
name|targetId
argument_list|,
name|targetType
argument_list|,
name|right
argument_list|)
expr_stmt|;
return|return
literal|true
return|;
block|}
catch|catch
parameter_list|(
name|SecurityException
name|e
parameter_list|)
block|{
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
name|Object
name|targetObject
parameter_list|,
name|Right
name|right
parameter_list|)
block|{
try|try
block|{
name|iCheck
operator|.
name|checkPermission
argument_list|(
name|getUser
argument_list|()
argument_list|,
name|targetObject
argument_list|,
name|right
argument_list|)
expr_stmt|;
return|return
literal|true
return|;
block|}
catch|catch
parameter_list|(
name|SecurityException
name|e
parameter_list|)
block|{
return|return
literal|false
return|;
block|}
block|}
block|}
end_class

end_unit

