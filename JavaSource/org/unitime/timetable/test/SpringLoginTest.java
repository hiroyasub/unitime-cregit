begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.4 - 3.5 (University Timetabling Application)  * Copyright (C) 2013, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
end_comment

begin_package
package|package
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|test
package|;
end_package

begin_import
import|import
name|org
operator|.
name|cpsolver
operator|.
name|ifs
operator|.
name|util
operator|.
name|ToolBox
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
name|support
operator|.
name|ClassPathXmlApplicationContext
import|;
end_import

begin_import
import|import
name|org
operator|.
name|springframework
operator|.
name|security
operator|.
name|authentication
operator|.
name|AuthenticationManager
import|;
end_import

begin_import
import|import
name|org
operator|.
name|springframework
operator|.
name|security
operator|.
name|authentication
operator|.
name|UsernamePasswordAuthenticationToken
import|;
end_import

begin_import
import|import
name|org
operator|.
name|springframework
operator|.
name|security
operator|.
name|core
operator|.
name|context
operator|.
name|SecurityContextHolder
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
name|hibernate
operator|.
name|util
operator|.
name|HibernateUtil
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
name|UserContext
import|;
end_import

begin_comment
comment|/**  * Simple login test.  * Example usage:  * 		java \  * 			-Dtmtbl.custom.properties=${TOMCAT_HOME}/custom.properties \  * 			-cp "${TOMCAT_HOME}/webapps/UniTime/WEB-INF/lib/*:${TOMCAT_HOME}/webapps/UniTime/WEB-INF/classes:${TOMCAT_HOME}/webapps/UniTime/WEB-INF" \  * 			org.unitime.timetable.test.SpringLoginTest  *  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|SpringLoginTest
block|{
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
try|try
block|{
comment|// Configure logging
name|ToolBox
operator|.
name|configureLogging
argument_list|()
expr_stmt|;
comment|// Configure hibernate
name|HibernateUtil
operator|.
name|configureHibernate
argument_list|(
name|ApplicationProperties
operator|.
name|getProperties
argument_list|()
argument_list|)
expr_stmt|;
comment|// Setup application context
name|ApplicationContext
name|context
init|=
operator|new
name|ClassPathXmlApplicationContext
argument_list|(
literal|"/applicationContext.xml"
argument_list|,
literal|"/securityContext.xml"
argument_list|)
decl_stmt|;
comment|// Get username and password
name|String
name|username
init|=
name|System
operator|.
name|console
argument_list|()
operator|.
name|readLine
argument_list|(
literal|"[%s]"
argument_list|,
literal|"Username:"
argument_list|)
decl_stmt|;
name|char
index|[]
name|passwd
init|=
name|System
operator|.
name|console
argument_list|()
operator|.
name|readPassword
argument_list|(
literal|"[%s]"
argument_list|,
literal|"Password:"
argument_list|)
decl_stmt|;
comment|// Try to authenticate
name|SecurityContextHolder
operator|.
name|getContext
argument_list|()
operator|.
name|setAuthentication
argument_list|(
name|context
operator|.
name|getBean
argument_list|(
literal|"authenticationManager"
argument_list|,
name|AuthenticationManager
operator|.
name|class
argument_list|)
operator|.
name|authenticate
argument_list|(
operator|new
name|UsernamePasswordAuthenticationToken
argument_list|(
name|username
argument_list|,
operator|new
name|String
argument_list|(
name|passwd
argument_list|)
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
comment|// Print authentication
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"Authentication: "
operator|+
name|SecurityContextHolder
operator|.
name|getContext
argument_list|()
operator|.
name|getAuthentication
argument_list|()
argument_list|)
expr_stmt|;
comment|// Get user context
name|UserContext
name|user
init|=
operator|(
name|UserContext
operator|)
name|SecurityContextHolder
operator|.
name|getContext
argument_list|()
operator|.
name|getAuthentication
argument_list|()
operator|.
name|getPrincipal
argument_list|()
decl_stmt|;
comment|// Print user name and his/her authorities
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"User name:"
operator|+
name|user
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"Authorities:"
operator|+
name|user
operator|.
name|getAuthorities
argument_list|()
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

