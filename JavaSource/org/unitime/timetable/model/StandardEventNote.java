begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*   * UniTime 3.1 - 3.5 (University Timetabling Application)  * Copyright (C) 2008 - 2013, UniTime LLC  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *   */
end_comment

begin_package
package|package
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|List
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
name|model
operator|.
name|base
operator|.
name|BaseStandardEventNote
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
name|model
operator|.
name|dao
operator|.
name|StandardEventNoteDAO
import|;
end_import

begin_comment
comment|/**  * @author Stephanie Schluttenhofer, Zuzana Mullerova, Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|StandardEventNote
extends|extends
name|BaseStandardEventNote
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|1L
decl_stmt|;
comment|/*[CONSTRUCTOR MARKER BEGIN]*/
specifier|public
name|StandardEventNote
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
block|}
comment|/** 	 * Constructor for primary key 	 */
specifier|public
name|StandardEventNote
parameter_list|(
name|java
operator|.
name|lang
operator|.
name|Long
name|uniqueId
parameter_list|)
block|{
name|super
argument_list|(
name|uniqueId
argument_list|)
expr_stmt|;
block|}
comment|/*[CONSTRUCTOR MARKER END]*/
specifier|public
specifier|static
name|List
name|findAll
parameter_list|()
block|{
return|return
operator|new
name|StandardEventNoteDAO
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select sen from StandardEventNote sen order by sen.reference"
argument_list|)
operator|.
name|setCacheable
argument_list|(
literal|true
argument_list|)
operator|.
name|list
argument_list|()
return|;
block|}
specifier|public
name|String
name|getLabel
parameter_list|()
block|{
return|return
name|getReference
argument_list|()
operator|+
literal|": "
operator|+
name|getNote
argument_list|()
return|;
block|}
block|}
end_class

end_unit

