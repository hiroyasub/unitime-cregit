begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.2 (University Timetabling Application)  * Copyright (C) 2010, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *  * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
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
operator|.
name|base
package|;
end_package

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
name|BuildingAbbreviationHistory
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
name|_RootDAO
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
name|BuildingAbbreviationHistoryDAO
import|;
end_import

begin_class
specifier|public
specifier|abstract
class|class
name|BaseBuildingAbbreviationHistoryDAO
extends|extends
name|_RootDAO
argument_list|<
name|BuildingAbbreviationHistory
argument_list|,
name|Long
argument_list|>
block|{
specifier|private
specifier|static
name|BuildingAbbreviationHistoryDAO
name|sInstance
decl_stmt|;
specifier|public
specifier|static
name|BuildingAbbreviationHistoryDAO
name|getInstance
parameter_list|()
block|{
if|if
condition|(
name|sInstance
operator|==
literal|null
condition|)
name|sInstance
operator|=
operator|new
name|BuildingAbbreviationHistoryDAO
argument_list|()
expr_stmt|;
return|return
name|sInstance
return|;
block|}
specifier|public
name|Class
argument_list|<
name|BuildingAbbreviationHistory
argument_list|>
name|getReferenceClass
parameter_list|()
block|{
return|return
name|BuildingAbbreviationHistory
operator|.
name|class
return|;
block|}
block|}
end_class

end_unit

