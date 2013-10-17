begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.4 - 3.5 (University Timetabling Application)  * Copyright (C) 2012 - 2013, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
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
name|server
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|logging
operator|.
name|Level
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|logging
operator|.
name|LogRecord
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|servlet
operator|.
name|ServletConfig
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
name|org
operator|.
name|apache
operator|.
name|log4j
operator|.
name|Logger
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
name|logging
operator|.
name|server
operator|.
name|StackTraceDeobfuscator
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
name|logging
operator|.
name|shared
operator|.
name|RemoteLoggingService
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
name|server
operator|.
name|rpc
operator|.
name|RemoteServiceServlet
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|UniTimeRemoteLoggingService
extends|extends
name|RemoteServiceServlet
implements|implements
name|RemoteLoggingService
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|1L
decl_stmt|;
specifier|private
name|StackTraceDeobfuscator
name|iDeobfuscator
init|=
literal|null
decl_stmt|;
annotation|@
name|Override
specifier|public
name|void
name|init
parameter_list|(
name|ServletConfig
name|config
parameter_list|)
throws|throws
name|ServletException
block|{
name|super
operator|.
name|init
argument_list|(
name|config
argument_list|)
expr_stmt|;
name|String
name|path
init|=
name|config
operator|.
name|getServletContext
argument_list|()
operator|.
name|getRealPath
argument_list|(
literal|"/WEB-INF/deploy/unitime/symbolMaps/"
argument_list|)
decl_stmt|;
if|if
condition|(
name|path
operator|!=
literal|null
condition|)
name|iDeobfuscator
operator|=
operator|new
name|StackTraceDeobfuscator
argument_list|(
name|path
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|logOnServer
parameter_list|(
name|LogRecord
name|record
parameter_list|)
block|{
try|try
block|{
if|if
condition|(
name|iDeobfuscator
operator|!=
literal|null
condition|)
name|record
operator|=
name|iDeobfuscator
operator|.
name|deobfuscateLogRecord
argument_list|(
name|record
argument_list|,
name|getPermutationStrongName
argument_list|()
argument_list|)
expr_stmt|;
name|Logger
name|logger
init|=
name|Logger
operator|.
name|getLogger
argument_list|(
name|record
operator|.
name|getLoggerName
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|record
operator|.
name|getLevel
argument_list|()
operator|.
name|intValue
argument_list|()
operator|>=
name|Level
operator|.
name|SEVERE
operator|.
name|intValue
argument_list|()
condition|)
block|{
name|logger
operator|.
name|error
argument_list|(
name|record
operator|.
name|getMessage
argument_list|()
argument_list|,
name|record
operator|.
name|getThrown
argument_list|()
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|record
operator|.
name|getLevel
argument_list|()
operator|.
name|intValue
argument_list|()
operator|>=
name|Level
operator|.
name|WARNING
operator|.
name|intValue
argument_list|()
condition|)
block|{
name|logger
operator|.
name|warn
argument_list|(
name|record
operator|.
name|getMessage
argument_list|()
argument_list|,
name|record
operator|.
name|getThrown
argument_list|()
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|record
operator|.
name|getLevel
argument_list|()
operator|.
name|intValue
argument_list|()
operator|>=
name|Level
operator|.
name|INFO
operator|.
name|intValue
argument_list|()
condition|)
block|{
name|logger
operator|.
name|info
argument_list|(
name|record
operator|.
name|getMessage
argument_list|()
argument_list|,
name|record
operator|.
name|getThrown
argument_list|()
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|record
operator|.
name|getLevel
argument_list|()
operator|.
name|intValue
argument_list|()
operator|>=
name|Level
operator|.
name|FINE
operator|.
name|intValue
argument_list|()
condition|)
block|{
name|logger
operator|.
name|debug
argument_list|(
name|record
operator|.
name|getMessage
argument_list|()
argument_list|,
name|record
operator|.
name|getThrown
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|logger
operator|.
name|trace
argument_list|(
name|record
operator|.
name|getMessage
argument_list|()
argument_list|,
name|record
operator|.
name|getThrown
argument_list|()
argument_list|)
expr_stmt|;
block|}
return|return
literal|null
return|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
return|return
literal|"Logging failed: "
operator|+
name|e
operator|.
name|getMessage
argument_list|()
return|;
block|}
block|}
block|}
end_class

end_unit

