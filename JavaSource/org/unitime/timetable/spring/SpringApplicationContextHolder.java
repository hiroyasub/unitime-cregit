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
name|spring
package|;
end_package

begin_import
import|import
name|org
operator|.
name|springframework
operator|.
name|beans
operator|.
name|BeansException
import|;
end_import

begin_import
import|import
name|org
operator|.
name|springframework
operator|.
name|context
operator|.
name|ApplicationContext
import|;
end_import

begin_import
import|import
name|org
operator|.
name|springframework
operator|.
name|context
operator|.
name|ApplicationContextAware
import|;
end_import

begin_import
import|import
name|org
operator|.
name|springframework
operator|.
name|stereotype
operator|.
name|Service
import|;
end_import

begin_comment
comment|/**  * Simple application context wrapper to be used for accessing Spring beans from legacy code.  */
end_comment

begin_class
annotation|@
name|Service
argument_list|(
literal|"springApplicationContext"
argument_list|)
specifier|public
class|class
name|SpringApplicationContextHolder
implements|implements
name|ApplicationContextAware
block|{
specifier|private
specifier|static
name|ApplicationContext
name|sApplicationContext
decl_stmt|;
annotation|@
name|Override
specifier|public
name|void
name|setApplicationContext
parameter_list|(
name|ApplicationContext
name|applicationContext
parameter_list|)
throws|throws
name|BeansException
block|{
name|sApplicationContext
operator|=
name|applicationContext
expr_stmt|;
block|}
specifier|public
specifier|static
name|Object
name|getBean
parameter_list|(
name|String
name|beanName
parameter_list|)
block|{
return|return
name|sApplicationContext
operator|.
name|getBean
argument_list|(
name|beanName
argument_list|)
return|;
block|}
block|}
end_class

end_unit

