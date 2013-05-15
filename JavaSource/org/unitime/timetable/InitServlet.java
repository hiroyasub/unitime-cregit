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
name|events
operator|.
name|EventExpirationService
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
name|onlinesectioning
operator|.
name|OnlineSectioningService
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

begin_import
import|import
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|util
operator|.
name|Constants
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
name|util
operator|.
name|LogCleaner
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
name|util
operator|.
name|RoomAvailability
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
name|util
operator|.
name|queue
operator|.
name|QueueProcessor
import|;
end_import

begin_comment
comment|/**  * Application Initialization Servlet  * @version 1.0  * @author Heston Fernandes, Tomas Muller  */
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
specifier|private
specifier|static
name|Exception
name|sInitializationException
init|=
literal|null
decl_stmt|;
comment|/** 	* Initializes the application 	*/
specifier|public
name|void
name|init
parameter_list|()
throws|throws
name|ServletException
block|{
name|Debug
operator|.
name|info
argument_list|(
literal|"******* UniTime "
operator|+
name|Constants
operator|.
name|getVersion
argument_list|()
operator|+
literal|" build on "
operator|+
name|Constants
operator|.
name|getReleaseDate
argument_list|()
operator|+
literal|" is starting up *******"
argument_list|)
expr_stmt|;
name|super
operator|.
name|init
argument_list|()
expr_stmt|;
try|try
block|{
name|Debug
operator|.
name|info
argument_list|(
literal|" - Initializing Logging ... "
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
name|Debug
operator|.
name|info
argument_list|(
literal|" - Initializing Hibernate ... "
argument_list|)
expr_stmt|;
name|_RootDAO
operator|.
name|initialize
argument_list|()
expr_stmt|;
comment|// Now, when hibernate is initialized, we can re-initialize logging with application configuration included
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
name|Debug
operator|.
name|info
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
if|if
condition|(
name|RoomAvailability
operator|.
name|getInstance
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|Debug
operator|.
name|info
argument_list|(
literal|" - Initializing Room Availability Service ... "
argument_list|)
expr_stmt|;
name|RoomAvailability
operator|.
name|getInstance
argument_list|()
operator|.
name|startService
argument_list|()
expr_stmt|;
block|}
name|Debug
operator|.
name|info
argument_list|(
literal|" - Cleaning Logs ..."
argument_list|)
expr_stmt|;
name|LogCleaner
operator|.
name|cleanupLogs
argument_list|()
expr_stmt|;
name|Debug
operator|.
name|info
argument_list|(
literal|" - Starting Online Sectioning Service ..."
argument_list|)
expr_stmt|;
name|OnlineSectioningService
operator|.
name|startService
argument_list|()
expr_stmt|;
name|Debug
operator|.
name|info
argument_list|(
literal|" - Starting Event Expiration Service ..."
argument_list|)
expr_stmt|;
name|EventExpirationService
operator|.
name|getInstance
argument_list|()
operator|.
name|start
argument_list|()
expr_stmt|;
name|Debug
operator|.
name|info
argument_list|(
literal|"******* UniTime "
operator|+
name|Constants
operator|.
name|getVersion
argument_list|()
operator|+
literal|" build on "
operator|+
name|Constants
operator|.
name|getReleaseDate
argument_list|()
operator|+
literal|" initialized successfully *******"
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
literal|"UniTime Initialization Failed : "
operator|+
name|e
operator|.
name|getMessage
argument_list|()
argument_list|,
name|e
argument_list|)
expr_stmt|;
name|sInitializationException
operator|=
name|e
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
name|Debug
operator|.
name|info
argument_list|(
literal|"******* UniTime "
operator|+
name|Constants
operator|.
name|getVersion
argument_list|()
operator|+
literal|" build on "
operator|+
name|Constants
operator|.
name|getReleaseDate
argument_list|()
operator|+
literal|" is going down *******"
argument_list|)
expr_stmt|;
name|super
operator|.
name|destroy
argument_list|()
expr_stmt|;
name|Debug
operator|.
name|info
argument_list|(
literal|" - Stopping Event Expiration Service ..."
argument_list|)
expr_stmt|;
name|EventExpirationService
operator|.
name|getInstance
argument_list|()
operator|.
name|interrupt
argument_list|()
expr_stmt|;
name|Debug
operator|.
name|info
argument_list|(
literal|" - Stopping Online Sectioning Service ..."
argument_list|)
expr_stmt|;
name|OnlineSectioningService
operator|.
name|stopService
argument_list|()
expr_stmt|;
name|Debug
operator|.
name|info
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
name|ApplicationProperties
operator|.
name|stopListener
argument_list|()
expr_stmt|;
if|if
condition|(
name|RoomAvailability
operator|.
name|getInstance
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|Debug
operator|.
name|info
argument_list|(
literal|" - Stopping Room Availability Service ... "
argument_list|)
expr_stmt|;
name|RoomAvailability
operator|.
name|getInstance
argument_list|()
operator|.
name|stopService
argument_list|()
expr_stmt|;
block|}
name|QueueProcessor
operator|.
name|stopProcessor
argument_list|()
expr_stmt|;
name|Debug
operator|.
name|info
argument_list|(
literal|"******* UniTime "
operator|+
name|Constants
operator|.
name|getVersion
argument_list|()
operator|+
literal|" shut down successfully *******"
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
literal|"UniTime Shutdown Failed : "
operator|+
name|e
operator|.
name|getMessage
argument_list|()
argument_list|,
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
literal|"UniTime Shutdown Failed : "
operator|+
name|e
operator|.
name|getMessage
argument_list|()
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
literal|"UniTime "
operator|+
name|Constants
operator|.
name|getVersion
argument_list|()
operator|+
literal|" Initialization Servlet"
return|;
block|}
specifier|public
specifier|static
name|Exception
name|getInitializationException
parameter_list|()
block|{
return|return
name|sInitializationException
return|;
block|}
block|}
end_class

end_unit

