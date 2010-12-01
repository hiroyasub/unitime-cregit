begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.2 (University Timetabling Application)  * Copyright (C) 2008 - 2010, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
end_comment

begin_package
package|package
name|org
operator|.
name|unitime
operator|.
name|commons
operator|.
name|web
operator|.
name|htmlgen
package|;
end_package

begin_comment
comment|/**  * @author Stephanie Schluttenhofer  *  * TODO To change the template for this generated type comment go to  * Window - Preferences - Java - Code Style - Code Templates  */
end_comment

begin_class
specifier|public
class|class
name|Span
extends|extends
name|GeneralHtmlWithJavascript
block|{
comment|/** 	 *  	 */
specifier|public
name|Span
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
name|this
operator|.
name|setTag
argument_list|(
literal|"span"
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

