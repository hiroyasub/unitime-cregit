begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.2 - 3.5 (University Timetabling Application)  * Copyright (C) 2010 - 2013, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
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
name|java
operator|.
name|io
operator|.
name|PrintWriter
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Properties
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
name|org
operator|.
name|apache
operator|.
name|log4j
operator|.
name|PropertyConfigurator
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
name|model
operator|.
name|Building
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
name|Room
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
name|BuildingDAO
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
name|RoomDAO
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

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|RoomCoordinates
block|{
specifier|protected
specifier|static
name|Logger
name|sLog
init|=
name|Logger
operator|.
name|getLogger
argument_list|(
name|AssignFirstAvailableTimePattern
operator|.
name|class
argument_list|)
decl_stmt|;
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
name|Properties
name|props
init|=
operator|new
name|Properties
argument_list|()
decl_stmt|;
name|props
operator|.
name|setProperty
argument_list|(
literal|"log4j.rootLogger"
argument_list|,
literal|"DEBUG, A1"
argument_list|)
expr_stmt|;
name|props
operator|.
name|setProperty
argument_list|(
literal|"log4j.appender.A1"
argument_list|,
literal|"org.apache.log4j.ConsoleAppender"
argument_list|)
expr_stmt|;
name|props
operator|.
name|setProperty
argument_list|(
literal|"log4j.appender.A1.layout"
argument_list|,
literal|"org.apache.log4j.PatternLayout"
argument_list|)
expr_stmt|;
name|props
operator|.
name|setProperty
argument_list|(
literal|"log4j.appender.A1.layout.ConversionPattern"
argument_list|,
literal|"%-5p %c{2}: %m%n"
argument_list|)
expr_stmt|;
name|props
operator|.
name|setProperty
argument_list|(
literal|"log4j.logger.org.hibernate"
argument_list|,
literal|"INFO"
argument_list|)
expr_stmt|;
name|props
operator|.
name|setProperty
argument_list|(
literal|"log4j.logger.org.hibernate.cfg"
argument_list|,
literal|"WARN"
argument_list|)
expr_stmt|;
name|props
operator|.
name|setProperty
argument_list|(
literal|"log4j.logger.org.hibernate.cache.EhCacheProvider"
argument_list|,
literal|"ERROR"
argument_list|)
expr_stmt|;
name|props
operator|.
name|setProperty
argument_list|(
literal|"log4j.logger.org.unitime.commons.hibernate"
argument_list|,
literal|"INFO"
argument_list|)
expr_stmt|;
name|props
operator|.
name|setProperty
argument_list|(
literal|"log4j.logger.net"
argument_list|,
literal|"INFO"
argument_list|)
expr_stmt|;
name|PropertyConfigurator
operator|.
name|configure
argument_list|(
name|props
argument_list|)
expr_stmt|;
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
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
init|=
operator|new
name|_RootDAO
argument_list|()
operator|.
name|getSession
argument_list|()
decl_stmt|;
name|PrintWriter
name|pw
init|=
operator|new
name|PrintWriter
argument_list|(
literal|"rooms.sql"
argument_list|)
decl_stmt|;
for|for
control|(
name|Building
name|b
range|:
name|BuildingDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|findAll
argument_list|()
control|)
block|{
if|if
condition|(
name|b
operator|.
name|getCoordinateX
argument_list|()
operator|!=
literal|null
operator|&&
name|b
operator|.
name|getCoordinateY
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|pw
operator|.
name|println
argument_list|(
literal|"update building set coordinate_x = "
operator|+
name|b
operator|.
name|getCoordinateX
argument_list|()
operator|+
literal|", coordinate_y = "
operator|+
name|b
operator|.
name|getCoordinateY
argument_list|()
operator|+
literal|" where uniqueid = "
operator|+
name|b
operator|.
name|getUniqueId
argument_list|()
operator|+
literal|";"
argument_list|)
expr_stmt|;
block|}
for|for
control|(
name|Room
name|r
range|:
name|RoomDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|findByBuilding
argument_list|(
name|hibSession
argument_list|,
name|b
operator|.
name|getUniqueId
argument_list|()
argument_list|)
control|)
block|{
if|if
condition|(
name|r
operator|.
name|getCoordinateX
argument_list|()
operator|!=
literal|null
operator|&&
name|r
operator|.
name|getCoordinateY
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|pw
operator|.
name|println
argument_list|(
literal|"update room set coordinate_x = "
operator|+
name|r
operator|.
name|getCoordinateX
argument_list|()
operator|+
literal|", coordinate_y = "
operator|+
name|r
operator|.
name|getCoordinateY
argument_list|()
operator|+
literal|" where uniqueid = "
operator|+
name|r
operator|.
name|getUniqueId
argument_list|()
operator|+
literal|";"
argument_list|)
expr_stmt|;
block|}
block|}
block|}
name|pw
operator|.
name|flush
argument_list|()
expr_stmt|;
name|pw
operator|.
name|close
argument_list|()
expr_stmt|;
name|hibSession
operator|.
name|flush
argument_list|()
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

