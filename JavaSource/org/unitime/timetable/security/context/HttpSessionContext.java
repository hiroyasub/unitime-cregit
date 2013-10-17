begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.4 - 3.5 (University Timetabling Application)  * Copyright (C) 2012 - 2013, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *   */
end_comment

begin_package
package|package
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|security
operator|.
name|context
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
name|ServletContext
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
name|security
operator|.
name|core
operator|.
name|context
operator|.
name|SecurityContextHolder
import|;
end_import

begin_import
import|import
name|org
operator|.
name|springframework
operator|.
name|web
operator|.
name|context
operator|.
name|support
operator|.
name|WebApplicationContextUtils
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
name|Qualifiable
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

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|HttpSessionContext
implements|implements
name|SessionContext
block|{
annotation|@
name|Autowired
specifier|private
name|HttpSession
name|iSession
decl_stmt|;
annotation|@
name|Autowired
name|PermissionCheck
name|unitimePermissionCheck
decl_stmt|;
specifier|public
name|HttpSessionContext
parameter_list|()
block|{
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
return|return
name|iSession
operator|.
name|getAttribute
argument_list|(
name|name
argument_list|)
return|;
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
name|iSession
operator|.
name|removeAttribute
argument_list|(
name|name
argument_list|)
expr_stmt|;
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
name|iSession
operator|.
name|setAttribute
argument_list|(
name|name
argument_list|,
name|value
argument_list|)
expr_stmt|;
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
name|key
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
name|key
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
name|key
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
name|boolean
name|isHttpSessionNew
parameter_list|()
block|{
return|return
name|iSession
operator|.
name|isNew
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|UserContext
name|getUser
parameter_list|()
block|{
name|Authentication
name|authentication
init|=
name|SecurityContextHolder
operator|.
name|getContext
argument_list|()
operator|.
name|getAuthentication
argument_list|()
decl_stmt|;
if|if
condition|(
name|authentication
operator|!=
literal|null
operator|&&
name|authentication
operator|.
name|isAuthenticated
argument_list|()
operator|&&
name|authentication
operator|.
name|getPrincipal
argument_list|()
operator|instanceof
name|UserContext
condition|)
return|return
operator|(
name|UserContext
operator|)
name|authentication
operator|.
name|getPrincipal
argument_list|()
return|;
return|return
literal|null
return|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|isAuthenticated
parameter_list|()
block|{
return|return
name|getUser
argument_list|()
operator|!=
literal|null
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getHttpSessionId
parameter_list|()
block|{
try|try
block|{
return|return
name|iSession
operator|.
name|getId
argument_list|()
return|;
block|}
catch|catch
parameter_list|(
name|IllegalStateException
name|e
parameter_list|)
block|{
return|return
literal|null
return|;
block|}
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
name|unitimePermissionCheck
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
name|unitimePermissionCheck
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
name|unitimePermissionCheck
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
return|return
name|unitimePermissionCheck
operator|.
name|hasPermission
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
return|;
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
return|return
name|unitimePermissionCheck
operator|.
name|hasPermission
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
return|;
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
return|return
name|unitimePermissionCheck
operator|.
name|hasPermission
argument_list|(
name|getUser
argument_list|()
argument_list|,
name|targetObject
argument_list|,
name|right
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|checkPermissionAnyAuthority
parameter_list|(
name|Right
name|right
parameter_list|,
name|Qualifiable
modifier|...
name|filter
parameter_list|)
block|{
name|unitimePermissionCheck
operator|.
name|checkPermissionAnyAuthority
argument_list|(
name|getUser
argument_list|()
argument_list|,
literal|null
argument_list|,
literal|null
argument_list|,
name|right
argument_list|,
name|filter
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|checkPermissionAnyAuthority
parameter_list|(
name|Serializable
name|targetId
parameter_list|,
name|String
name|targetType
parameter_list|,
name|Right
name|right
parameter_list|,
name|Qualifiable
modifier|...
name|filter
parameter_list|)
block|{
name|unitimePermissionCheck
operator|.
name|checkPermissionAnyAuthority
argument_list|(
name|getUser
argument_list|()
argument_list|,
name|targetId
argument_list|,
name|targetType
argument_list|,
name|right
argument_list|,
name|filter
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|checkPermissionAnyAuthority
parameter_list|(
name|Object
name|targetObject
parameter_list|,
name|Right
name|right
parameter_list|,
name|Qualifiable
modifier|...
name|filter
parameter_list|)
block|{
name|unitimePermissionCheck
operator|.
name|checkPermissionAnyAuthority
argument_list|(
name|getUser
argument_list|()
argument_list|,
name|targetObject
argument_list|,
name|right
argument_list|,
name|filter
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|hasPermissionAnyAuthority
parameter_list|(
name|Right
name|right
parameter_list|,
name|Qualifiable
modifier|...
name|filter
parameter_list|)
block|{
return|return
name|unitimePermissionCheck
operator|.
name|hasPermissionAnyAuthority
argument_list|(
name|getUser
argument_list|()
argument_list|,
literal|null
argument_list|,
literal|null
argument_list|,
name|right
argument_list|,
name|filter
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|hasPermissionAnyAuthority
parameter_list|(
name|Serializable
name|targetId
parameter_list|,
name|String
name|targetType
parameter_list|,
name|Right
name|right
parameter_list|,
name|Qualifiable
modifier|...
name|filter
parameter_list|)
block|{
return|return
name|unitimePermissionCheck
operator|.
name|hasPermissionAnyAuthority
argument_list|(
name|getUser
argument_list|()
argument_list|,
name|targetId
argument_list|,
name|targetType
argument_list|,
name|right
argument_list|,
name|filter
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|hasPermissionAnyAuthority
parameter_list|(
name|Object
name|targetObject
parameter_list|,
name|Right
name|right
parameter_list|,
name|Qualifiable
modifier|...
name|filter
parameter_list|)
block|{
return|return
name|unitimePermissionCheck
operator|.
name|hasPermissionAnyAuthority
argument_list|(
name|getUser
argument_list|()
argument_list|,
name|targetObject
argument_list|,
name|right
argument_list|,
name|filter
argument_list|)
return|;
block|}
specifier|public
specifier|static
name|SessionContext
name|getSessionContext
parameter_list|(
name|ServletContext
name|context
parameter_list|)
block|{
return|return
operator|(
name|SessionContext
operator|)
name|WebApplicationContextUtils
operator|.
name|getWebApplicationContext
argument_list|(
name|context
argument_list|)
operator|.
name|getBean
argument_list|(
literal|"sessionContext"
argument_list|)
return|;
block|}
block|}
end_class

end_unit

