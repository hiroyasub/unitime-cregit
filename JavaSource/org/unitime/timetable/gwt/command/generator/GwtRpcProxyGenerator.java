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
name|generator
package|;
end_package

begin_import
import|import
name|com
operator|.
name|google
operator|.
name|gwt
operator|.
name|core
operator|.
name|ext
operator|.
name|GeneratorContext
import|;
end_import

begin_import
import|import
name|com
operator|.
name|google
operator|.
name|gwt
operator|.
name|core
operator|.
name|ext
operator|.
name|GeneratorContextExt
import|;
end_import

begin_import
import|import
name|com
operator|.
name|google
operator|.
name|gwt
operator|.
name|core
operator|.
name|ext
operator|.
name|TreeLogger
import|;
end_import

begin_import
import|import
name|com
operator|.
name|google
operator|.
name|gwt
operator|.
name|core
operator|.
name|ext
operator|.
name|UnableToCompleteException
import|;
end_import

begin_import
import|import
name|com
operator|.
name|google
operator|.
name|gwt
operator|.
name|core
operator|.
name|ext
operator|.
name|typeinfo
operator|.
name|JClassType
import|;
end_import

begin_import
import|import
name|com
operator|.
name|google
operator|.
name|gwt
operator|.
name|dev
operator|.
name|javac
operator|.
name|rebind
operator|.
name|RebindResult
import|;
end_import

begin_import
import|import
name|com
operator|.
name|google
operator|.
name|gwt
operator|.
name|user
operator|.
name|rebind
operator|.
name|rpc
operator|.
name|ProxyCreator
import|;
end_import

begin_import
import|import
name|com
operator|.
name|google
operator|.
name|gwt
operator|.
name|user
operator|.
name|rebind
operator|.
name|rpc
operator|.
name|ServiceInterfaceProxyGenerator
import|;
end_import

begin_class
specifier|public
class|class
name|GwtRpcProxyGenerator
extends|extends
name|ServiceInterfaceProxyGenerator
block|{
specifier|private
name|GeneratorContext
name|iGeneratorContext
init|=
literal|null
decl_stmt|;
specifier|public
name|GwtRpcProxyGenerator
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|RebindResult
name|generateIncrementally
parameter_list|(
name|TreeLogger
name|logger
parameter_list|,
name|GeneratorContextExt
name|ctx
parameter_list|,
name|String
name|requestedClass
parameter_list|)
throws|throws
name|UnableToCompleteException
block|{
if|if
condition|(
name|iGeneratorContext
operator|==
literal|null
condition|)
name|iGeneratorContext
operator|=
name|ctx
expr_stmt|;
return|return
name|super
operator|.
name|generateIncrementally
argument_list|(
name|logger
argument_list|,
name|ctx
argument_list|,
name|requestedClass
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|protected
name|ProxyCreator
name|createProxyCreator
parameter_list|(
name|JClassType
name|remoteService
parameter_list|)
block|{
return|return
operator|new
name|GwtRpcProxyCreator
argument_list|(
name|remoteService
argument_list|,
name|iGeneratorContext
argument_list|)
return|;
block|}
block|}
end_class

end_unit

