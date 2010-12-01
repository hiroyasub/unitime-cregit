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
name|timetable
operator|.
name|solver
operator|.
name|remote
operator|.
name|core
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|File
import|;
end_import

begin_import
import|import
name|java
operator|.
name|security
operator|.
name|Permission
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|StartupMinimal
block|{
specifier|public
specifier|static
class|class
name|MySecurityManager
extends|extends
name|SecurityManager
block|{
specifier|public
name|void
name|checkPermission
parameter_list|(
name|Permission
name|perm
parameter_list|)
block|{
block|}
specifier|public
name|void
name|checkPermission
parameter_list|(
name|Permission
name|perm
parameter_list|,
name|Object
name|context
parameter_list|)
block|{
block|}
block|}
specifier|public
specifier|static
name|void
name|main
parameter_list|(
name|String
index|[]
name|args
parameter_list|)
block|{
name|SolverTray
operator|.
name|init
argument_list|()
expr_stmt|;
try|try
block|{
name|String
name|solverHome
init|=
name|System
operator|.
name|getProperty
argument_list|(
literal|"tmtbl.solver.home"
argument_list|)
decl_stmt|;
if|if
condition|(
name|solverHome
operator|==
literal|null
condition|)
name|solverHome
operator|=
name|System
operator|.
name|getProperty
argument_list|(
literal|"user.home"
argument_list|)
operator|+
name|File
operator|.
name|separator
operator|+
literal|"solver"
expr_stmt|;
name|System
operator|.
name|setProperty
argument_list|(
literal|"java.io.tmpdir"
argument_list|,
name|solverHome
operator|+
name|File
operator|.
name|separator
operator|+
literal|"tmp"
argument_list|)
expr_stmt|;
name|System
operator|.
name|setSecurityManager
argument_list|(
operator|new
name|MySecurityManager
argument_list|()
argument_list|)
expr_stmt|;
try|try
block|{
operator|(
operator|new
name|File
argument_list|(
name|System
operator|.
name|getProperty
argument_list|(
literal|"java.io.tmpdir"
argument_list|)
argument_list|)
operator|)
operator|.
name|mkdirs
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
block|}
name|ServerClassLoader
operator|.
name|getInstance
argument_list|()
operator|.
name|findClass
argument_list|(
literal|"org.unitime.timetable.solver.remote.core.RemoteSolverServer"
argument_list|)
operator|.
name|getMethod
argument_list|(
literal|"main"
argument_list|,
operator|new
name|Class
index|[]
block|{
name|String
index|[]
operator|.
expr|class
block|}
argument_list|)
operator|.
name|invoke
argument_list|(
literal|null
argument_list|,
operator|new
name|Object
index|[]
block|{
name|args
block|}
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|e
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

