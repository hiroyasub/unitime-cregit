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
name|hibernate
operator|.
name|util
package|;
end_package

begin_import
import|import
name|org
operator|.
name|dom4j
operator|.
name|Document
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
name|ApplicationProperties
import|;
end_import

begin_comment
comment|/**  * @author says  *  */
end_comment

begin_class
specifier|public
class|class
name|UniTimeCoreDatabaseUpdate
extends|extends
name|DatabaseUpdate
block|{
comment|/** 	 * @param document 	 * @throws Exception 	 */
specifier|public
name|UniTimeCoreDatabaseUpdate
parameter_list|(
name|Document
name|document
parameter_list|)
throws|throws
name|Exception
block|{
name|super
argument_list|(
name|document
argument_list|)
expr_stmt|;
block|}
specifier|public
name|UniTimeCoreDatabaseUpdate
parameter_list|()
throws|throws
name|Exception
block|{
name|super
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Override
specifier|protected
name|String
name|findDbUpdateFileName
parameter_list|()
block|{
return|return
operator|(
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"tmtbl.db.update"
argument_list|,
literal|"dbupdate.xml"
argument_list|)
operator|)
return|;
block|}
annotation|@
name|Override
specifier|protected
name|String
name|versionParameterName
parameter_list|()
block|{
return|return
operator|(
literal|"tmtbl.db.version"
operator|)
return|;
block|}
annotation|@
name|Override
specifier|protected
name|String
name|updateName
parameter_list|()
block|{
return|return
operator|(
literal|"UniTime"
operator|)
return|;
block|}
block|}
end_class

end_unit

