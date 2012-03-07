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
name|commons
operator|.
name|User
import|;
end_import

begin_import
import|import
name|org
operator|.
name|unitime
operator|.
name|commons
operator|.
name|web
operator|.
name|Web
import|;
end_import

begin_class
specifier|public
class|class
name|GwtRpcHelper
block|{
specifier|private
name|HttpSession
name|iHttpSession
decl_stmt|;
specifier|protected
name|GwtRpcHelper
parameter_list|(
name|HttpSession
name|httpSession
parameter_list|)
block|{
name|iHttpSession
operator|=
name|httpSession
expr_stmt|;
block|}
specifier|public
name|User
name|getUser
parameter_list|()
block|{
return|return
name|Web
operator|.
name|getUser
argument_list|(
name|iHttpSession
argument_list|)
return|;
block|}
block|}
end_class

end_unit

