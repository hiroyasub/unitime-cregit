begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.5 (University Timetabling Application)  * Copyright (C) 2014, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
end_comment

begin_package
package|package
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|solver
operator|.
name|service
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

begin_class
specifier|public
class|class
name|ProxyHolder
parameter_list|<
name|U
extends|extends
name|Serializable
parameter_list|,
name|T
parameter_list|>
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
name|U
name|iId
init|=
literal|null
decl_stmt|;
specifier|private
specifier|transient
name|T
name|iProxy
init|=
literal|null
decl_stmt|;
specifier|public
name|ProxyHolder
parameter_list|(
name|U
name|id
parameter_list|,
name|T
name|proxy
parameter_list|)
block|{
name|iId
operator|=
name|id
expr_stmt|;
name|iProxy
operator|=
name|proxy
expr_stmt|;
block|}
specifier|public
name|U
name|getId
parameter_list|()
block|{
return|return
name|iId
return|;
block|}
specifier|public
name|T
name|getProxy
parameter_list|()
block|{
return|return
name|iProxy
return|;
block|}
specifier|public
name|boolean
name|isValid
parameter_list|()
block|{
return|return
name|iProxy
operator|!=
literal|null
return|;
block|}
specifier|public
name|boolean
name|isValid
parameter_list|(
name|U
name|id
parameter_list|)
block|{
return|return
name|iProxy
operator|!=
literal|null
operator|&&
name|getId
argument_list|()
operator|.
name|equals
argument_list|(
name|id
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
literal|"ProxyHolder{id = "
operator|+
name|getId
argument_list|()
operator|+
literal|", valid = "
operator|+
name|isValid
argument_list|()
operator|+
operator|(
name|iProxy
operator|!=
literal|null
condition|?
literal|", type = "
operator|+
name|iProxy
operator|.
name|getClass
argument_list|()
operator|.
name|getSimpleName
argument_list|()
else|:
literal|""
operator|)
operator|+
literal|"}"
return|;
block|}
block|}
end_class

end_unit
