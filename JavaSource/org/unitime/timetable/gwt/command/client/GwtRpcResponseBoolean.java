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
name|gwt
operator|.
name|command
operator|.
name|client
package|;
end_package

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|GwtRpcResponseBoolean
implements|implements
name|GwtRpcResponse
block|{
name|Boolean
name|iValue
decl_stmt|;
specifier|public
name|GwtRpcResponseBoolean
parameter_list|()
block|{
block|}
specifier|public
name|GwtRpcResponseBoolean
parameter_list|(
name|Boolean
name|value
parameter_list|)
block|{
name|iValue
operator|=
name|value
expr_stmt|;
block|}
specifier|public
name|Boolean
name|getValue
parameter_list|()
block|{
return|return
name|iValue
return|;
block|}
specifier|public
name|void
name|setValue
parameter_list|(
name|Boolean
name|value
parameter_list|)
block|{
name|iValue
operator|=
name|value
expr_stmt|;
block|}
block|}
end_class

end_unit

