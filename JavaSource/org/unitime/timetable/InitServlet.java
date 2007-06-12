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
package|;
end_package

begin_import
import|import
name|javax
operator|.
name|servlet
operator|.
name|Servlet
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|servlet
operator|.
name|ServletException
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|servlet
operator|.
name|http
operator|.
name|HttpServlet
import|;
end_import

begin_import
import|import
name|org
operator|.
name|unitime
operator|.
name|commons
operator|.
name|Debug
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
name|SolverInfo
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
name|solver
operator|.
name|remote
operator|.
name|SolverRegisterService
import|;
end_import

begin_comment
comment|/**  * Application Initialization Servlet  * @version 1.0  * @author Heston Fernandes  */
end_comment

begin_class
specifier|public
class|class
name|InitServlet
extends|extends
name|HttpServlet
implements|implements
name|Servlet
block|{
comment|/** 	 *  	 */
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|3258415014804142137L
decl_stmt|;
comment|/** 	* Initializes the application 	*/
specifier|public
name|void
name|init
parameter_list|()
throws|throws
name|ServletException
block|{
name|logMessage
argument_list|(
literal|"******* Initializing Timetabling Application : START *******"
argument_list|)
expr_stmt|;
name|super
operator|.
name|init
argument_list|()
expr_stmt|;
try|try
block|{
name|logMessage
argument_list|(
literal|" - Initializing Debugger ... "
argument_list|)
expr_stmt|;
name|Debug
operator|.
name|init
argument_list|(
name|ApplicationProperties
operator|.
name|getProperties
argument_list|()
argument_list|)
expr_stmt|;
name|logMessage
argument_list|(
literal|" - Initializing Hibernate ... "
argument_list|)
expr_stmt|;
name|_RootDAO
operator|.
name|initialize
argument_list|()
expr_stmt|;
name|logMessage
argument_list|(
literal|" - Initializing Solver Register ... "
argument_list|)
expr_stmt|;
name|SolverRegisterService
operator|.
name|startService
argument_list|()
expr_stmt|;
name|SolverRegisterService
operator|.
name|addShutdownHook
argument_list|()
expr_stmt|;
name|logMessage
argument_list|(
literal|"******* Timetabling Application : Initializing DONE *******"
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|logError
argument_list|(
literal|"Servlet Initialization Failed : "
operator|+
name|e
operator|.
name|getMessage
argument_list|()
argument_list|)
expr_stmt|;
name|e
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
block|}
comment|/** 	* Terminates the application 	*/
specifier|public
name|void
name|destroy
parameter_list|()
block|{
try|try
block|{
name|logMessage
argument_list|(
literal|"******* Shutting down Timetabling Application *******"
argument_list|)
expr_stmt|;
name|super
operator|.
name|destroy
argument_list|()
expr_stmt|;
name|logMessage
argument_list|(
literal|" - Stopping Solver Register ... "
argument_list|)
expr_stmt|;
name|SolverRegisterService
operator|.
name|stopService
argument_list|()
expr_stmt|;
try|try
block|{
name|SolverRegisterService
operator|.
name|removeShutdownHook
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IllegalStateException
name|e
parameter_list|)
block|{
block|}
name|SolverInfo
operator|.
name|stopInfoCacheCleanup
argument_list|()
expr_stmt|;
name|logMessage
argument_list|(
literal|" - Stopping Property File Change Listener ... "
argument_list|)
expr_stmt|;
name|ApplicationProperties
operator|.
name|stopListener
argument_list|()
expr_stmt|;
name|logMessage
argument_list|(
literal|"******* Timetabling Application : Shut down DONE *******"
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|Debug
operator|.
name|error
argument_list|(
name|e
argument_list|)
expr_stmt|;
if|if
condition|(
name|e
operator|instanceof
name|RuntimeException
condition|)
throw|throw
operator|(
name|RuntimeException
operator|)
name|e
throw|;
else|else
throw|throw
operator|new
name|RuntimeException
argument_list|(
literal|"Shut down failed"
argument_list|,
name|e
argument_list|)
throw|;
block|}
block|}
comment|/** 	 * Gets servlet information 	 * @return String containing servlet info  	 */
specifier|public
name|String
name|getServletInfo
parameter_list|()
block|{
return|return
literal|"Timetabling Initialization Servlet"
return|;
block|}
comment|/* 	 * Writes message to log 	 */
specifier|private
specifier|static
name|void
name|logMessage
parameter_list|(
name|String
name|message
parameter_list|)
block|{
name|Debug
operator|.
name|info
argument_list|(
name|message
argument_list|)
expr_stmt|;
block|}
comment|/* 	 * Write error to log 	 */
specifier|private
specifier|static
name|void
name|logError
parameter_list|(
name|String
name|message
parameter_list|)
block|{
name|Debug
operator|.
name|error
argument_list|(
name|message
argument_list|)
expr_stmt|;
name|System
operator|.
name|err
operator|.
name|println
argument_list|(
name|message
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

