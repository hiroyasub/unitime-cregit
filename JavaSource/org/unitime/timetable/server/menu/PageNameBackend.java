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
name|server
operator|.
name|menu
package|;
end_package

begin_import
import|import
name|org
operator|.
name|unitime
operator|.
name|localization
operator|.
name|impl
operator|.
name|Localization
import|;
end_import

begin_import
import|import
name|org
operator|.
name|unitime
operator|.
name|localization
operator|.
name|messages
operator|.
name|PageNames
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
name|defaults
operator|.
name|ApplicationProperty
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
name|gwt
operator|.
name|command
operator|.
name|server
operator|.
name|GwtRpcImplementation
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
name|gwt
operator|.
name|command
operator|.
name|server
operator|.
name|GwtRpcImplements
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
name|gwt
operator|.
name|shared
operator|.
name|MenuInterface
operator|.
name|PageNameInterface
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
name|gwt
operator|.
name|shared
operator|.
name|MenuInterface
operator|.
name|PageNameRpcRequest
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
name|security
operator|.
name|SessionContext
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
annotation|@
name|GwtRpcImplements
argument_list|(
name|PageNameRpcRequest
operator|.
name|class
argument_list|)
specifier|public
class|class
name|PageNameBackend
implements|implements
name|GwtRpcImplementation
argument_list|<
name|PageNameRpcRequest
argument_list|,
name|PageNameInterface
argument_list|>
block|{
specifier|private
specifier|static
name|PageNames
name|sPageNames
init|=
name|Localization
operator|.
name|create
argument_list|(
name|PageNames
operator|.
name|class
argument_list|)
decl_stmt|;
annotation|@
name|Override
specifier|public
name|PageNameInterface
name|execute
parameter_list|(
name|PageNameRpcRequest
name|request
parameter_list|,
name|SessionContext
name|context
parameter_list|)
block|{
name|String
name|name
init|=
name|request
operator|.
name|getName
argument_list|()
operator|.
name|trim
argument_list|()
operator|.
name|replace
argument_list|(
literal|' '
argument_list|,
literal|'_'
argument_list|)
operator|.
name|replace
argument_list|(
literal|"("
argument_list|,
literal|""
argument_list|)
operator|.
name|replace
argument_list|(
literal|")"
argument_list|,
literal|""
argument_list|)
operator|.
name|replace
argument_list|(
literal|':'
argument_list|,
literal|'_'
argument_list|)
decl_stmt|;
name|PageNameInterface
name|ret
init|=
operator|new
name|PageNameInterface
argument_list|()
decl_stmt|;
if|if
condition|(
name|ApplicationProperty
operator|.
name|PageHelpEnabled
operator|.
name|isTrue
argument_list|()
operator|&&
name|ApplicationProperty
operator|.
name|PageHelpUrl
operator|.
name|value
argument_list|()
operator|!=
literal|null
condition|)
name|ret
operator|.
name|setHelpUrl
argument_list|(
name|ApplicationProperty
operator|.
name|PageHelpUrl
operator|.
name|value
argument_list|()
operator|+
name|name
argument_list|)
expr_stmt|;
name|ret
operator|.
name|setName
argument_list|(
name|sPageNames
operator|.
name|translateMessage
argument_list|(
name|name
argument_list|,
name|request
operator|.
name|getName
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|ret
return|;
block|}
block|}
end_class

end_unit
