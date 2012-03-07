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
name|client
package|;
end_package

begin_import
import|import
name|com
operator|.
name|google
operator|.
name|gwt
operator|.
name|user
operator|.
name|client
operator|.
name|rpc
operator|.
name|IsSerializable
import|;
end_import

begin_class
specifier|public
class|class
name|GwtRpcException
extends|extends
name|RuntimeException
implements|implements
name|IsSerializable
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
name|Throwable
name|iCause
decl_stmt|;
specifier|public
name|GwtRpcException
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
block|}
specifier|public
name|GwtRpcException
parameter_list|(
name|String
name|message
parameter_list|)
block|{
name|super
argument_list|(
name|message
argument_list|)
expr_stmt|;
block|}
specifier|public
name|GwtRpcException
parameter_list|(
name|String
name|message
parameter_list|,
name|Throwable
name|cause
parameter_list|)
block|{
name|super
argument_list|(
name|message
argument_list|,
name|cause
argument_list|)
expr_stmt|;
if|if
condition|(
name|cause
operator|instanceof
name|IsSerializable
condition|)
name|iCause
operator|=
name|cause
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|Throwable
name|getCause
parameter_list|()
block|{
return|return
operator|(
name|iCause
operator|!=
literal|null
condition|?
name|iCause
else|:
name|super
operator|.
name|getCause
argument_list|()
operator|)
return|;
block|}
block|}
end_class

end_unit

