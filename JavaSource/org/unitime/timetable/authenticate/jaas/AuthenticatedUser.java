begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.2 - 3.5 (University Timetabling Application)  * Copyright (C) 2008 - 2013, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *   */
end_comment

begin_package
package|package
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|authenticate
operator|.
name|jaas
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
name|security
operator|.
name|Principal
import|;
end_import

begin_comment
comment|/**  * Represents an authenticated and authorized timetable user  *  * @author Tomas Muller  */
end_comment

begin_class
specifier|final
specifier|public
class|class
name|AuthenticatedUser
implements|implements
name|Principal
implements|,
name|Serializable
implements|,
name|HasExternalId
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|11L
decl_stmt|;
name|String
name|iName
decl_stmt|,
name|iExternalId
decl_stmt|;
specifier|public
name|AuthenticatedUser
parameter_list|(
name|String
name|name
parameter_list|,
name|String
name|externalId
parameter_list|)
block|{
name|iName
operator|=
name|name
expr_stmt|;
name|iExternalId
operator|=
name|externalId
expr_stmt|;
block|}
specifier|public
name|boolean
name|equals
parameter_list|(
name|Object
name|obj
parameter_list|)
block|{
return|return
name|getName
argument_list|()
operator|.
name|equals
argument_list|(
operator|(
operator|(
name|Principal
operator|)
name|obj
operator|)
operator|.
name|getName
argument_list|()
argument_list|)
return|;
block|}
specifier|public
name|int
name|hashCode
parameter_list|()
block|{
return|return
name|getName
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
name|getName
argument_list|()
return|;
block|}
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
name|iName
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getExternalId
parameter_list|()
block|{
return|return
name|iExternalId
return|;
block|}
block|}
end_class

end_unit

