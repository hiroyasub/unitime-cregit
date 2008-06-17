begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.1 (University Timetabling Application)  * Copyright (C) 2008, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 2 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program; if not, write to the Free Software Foundation, Inc.,  * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
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
package|;
end_package

begin_comment
comment|/** Simple extension of java.io.OutputStream. \n character is replaced by&lt;br&gt;  *  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|WebOutputStream
extends|extends
name|java
operator|.
name|io
operator|.
name|OutputStream
block|{
comment|/** buffer */
name|StringBuffer
name|iBuffer
init|=
literal|null
decl_stmt|;
comment|/** constructor */
specifier|public
name|WebOutputStream
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
name|iBuffer
operator|=
operator|new
name|StringBuffer
argument_list|()
expr_stmt|;
block|}
comment|/** writes a byte to stream */
specifier|public
name|void
name|write
parameter_list|(
name|int
name|b
parameter_list|)
throws|throws
name|java
operator|.
name|io
operator|.
name|IOException
block|{
if|if
condition|(
name|b
operator|==
literal|'\n'
condition|)
block|{
name|iBuffer
operator|.
name|append
argument_list|(
literal|"<br>"
argument_list|)
expr_stmt|;
block|}
name|iBuffer
operator|.
name|append
argument_list|(
operator|(
name|char
operator|)
name|b
argument_list|)
expr_stmt|;
block|}
comment|/** returns content -- characters \n are replaced by tag&lt;br&gt; */
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
name|iBuffer
operator|.
name|toString
argument_list|()
return|;
block|}
block|}
end_class

end_unit

