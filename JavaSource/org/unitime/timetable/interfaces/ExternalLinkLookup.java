begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.0 (University Course Timetabling& Student Sectioning Application)  * Copyright (C) 2007, UniTime.org, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 2 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program; if not, write to the Free Software Foundation, Inc.,  * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
package|package
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|interfaces
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Map
import|;
end_import

begin_comment
comment|/**  * Interface to generate external links  *   * @author Heston Fernandes  */
end_comment

begin_interface
specifier|public
interface|interface
name|ExternalLinkLookup
block|{
comment|/** Attribute for the link label */
specifier|public
specifier|final
name|String
name|LINK_LABEL
init|=
literal|"label"
decl_stmt|;
comment|/** Attribute for the link location */
specifier|public
specifier|final
name|String
name|LINK_LOCATION
init|=
literal|"href"
decl_stmt|;
comment|/** 	 * Generate the link based on the attributes of the object 	 * @param obj object whose attributes may be used in constructing the link 	 * @return Map object containing two elements LINK_LABEL and LINK LOCATION 	 */
specifier|public
name|Map
name|getLink
parameter_list|(
name|Object
name|obj
parameter_list|)
throws|throws
name|Exception
function_decl|;
comment|/** 	 * Sets the error message (if any) 	 * @return 	 */
specifier|public
name|String
name|getErrorMessage
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

