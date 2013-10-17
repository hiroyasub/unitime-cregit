begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.3 - 3.5 (University Timetabling Application)  * Copyright (C) 2012 - 2013, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
end_comment

begin_package
package|package
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|util
package|;
end_package

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|logging
operator|.
name|Log
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|logging
operator|.
name|LogFactory
import|;
end_import

begin_import
import|import
name|org
operator|.
name|hibernate
operator|.
name|Transaction
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
name|LogCleaner
block|{
specifier|private
specifier|static
name|Log
name|sLog
init|=
name|LogFactory
operator|.
name|getLog
argument_list|(
name|LogCleaner
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|public
specifier|static
name|void
name|cleanupQueryLog
parameter_list|(
name|int
name|days
parameter_list|)
block|{
if|if
condition|(
name|days
operator|<
literal|0
condition|)
return|return;
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
name|createNewSession
argument_list|()
decl_stmt|;
name|Transaction
name|tx
init|=
literal|null
decl_stmt|;
try|try
block|{
name|tx
operator|=
name|hibSession
operator|.
name|beginTransaction
argument_list|()
expr_stmt|;
name|int
name|rows
init|=
name|hibSession
operator|.
name|createQuery
argument_list|(
literal|"delete from QueryLog where timeStamp< "
operator|+
name|HibernateUtil
operator|.
name|addDate
argument_list|(
literal|"current_date()"
argument_list|,
literal|":days"
argument_list|)
argument_list|)
operator|.
name|setInteger
argument_list|(
literal|"days"
argument_list|,
operator|-
name|days
argument_list|)
operator|.
name|executeUpdate
argument_list|()
decl_stmt|;
if|if
condition|(
name|rows
operator|>
literal|0
condition|)
name|sLog
operator|.
name|info
argument_list|(
literal|"All records older than "
operator|+
name|days
operator|+
literal|" days deleted from the query log ("
operator|+
name|rows
operator|+
literal|" records)."
argument_list|)
expr_stmt|;
name|tx
operator|.
name|commit
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Throwable
name|t
parameter_list|)
block|{
name|sLog
operator|.
name|warn
argument_list|(
literal|"Failed to cleanup query log: "
operator|+
name|t
operator|.
name|getMessage
argument_list|()
argument_list|,
name|t
argument_list|)
expr_stmt|;
if|if
condition|(
name|tx
operator|!=
literal|null
condition|)
name|tx
operator|.
name|rollback
argument_list|()
expr_stmt|;
block|}
finally|finally
block|{
name|hibSession
operator|.
name|close
argument_list|()
expr_stmt|;
block|}
block|}
specifier|public
specifier|static
name|void
name|cleanupChangeLog
parameter_list|(
name|int
name|days
parameter_list|)
block|{
if|if
condition|(
name|days
operator|<
literal|0
condition|)
return|return;
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
name|createNewSession
argument_list|()
decl_stmt|;
name|Transaction
name|tx
init|=
literal|null
decl_stmt|;
try|try
block|{
name|tx
operator|=
name|hibSession
operator|.
name|beginTransaction
argument_list|()
expr_stmt|;
name|int
name|rows
init|=
name|hibSession
operator|.
name|createQuery
argument_list|(
literal|"delete from ChangeLog where timeStamp< "
operator|+
name|HibernateUtil
operator|.
name|addDate
argument_list|(
literal|"current_date()"
argument_list|,
literal|":days"
argument_list|)
argument_list|)
operator|.
name|setInteger
argument_list|(
literal|"days"
argument_list|,
operator|-
name|days
argument_list|)
operator|.
name|executeUpdate
argument_list|()
decl_stmt|;
if|if
condition|(
name|rows
operator|>
literal|0
condition|)
name|sLog
operator|.
name|info
argument_list|(
literal|"All records older than "
operator|+
name|days
operator|+
literal|" days deleted from the change log ("
operator|+
name|rows
operator|+
literal|" records)."
argument_list|)
expr_stmt|;
name|tx
operator|.
name|commit
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Throwable
name|t
parameter_list|)
block|{
name|sLog
operator|.
name|warn
argument_list|(
literal|"Failed to cleanup query log: "
operator|+
name|t
operator|.
name|getMessage
argument_list|()
argument_list|,
name|t
argument_list|)
expr_stmt|;
if|if
condition|(
name|tx
operator|!=
literal|null
condition|)
name|tx
operator|.
name|rollback
argument_list|()
expr_stmt|;
block|}
finally|finally
block|{
name|hibSession
operator|.
name|close
argument_list|()
expr_stmt|;
block|}
block|}
specifier|public
specifier|static
name|void
name|cleanupOnlineSectioningLog
parameter_list|(
name|int
name|days
parameter_list|)
block|{
if|if
condition|(
name|days
operator|<
literal|0
condition|)
return|return;
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
name|createNewSession
argument_list|()
decl_stmt|;
name|Transaction
name|tx
init|=
literal|null
decl_stmt|;
try|try
block|{
name|tx
operator|=
name|hibSession
operator|.
name|beginTransaction
argument_list|()
expr_stmt|;
name|int
name|rows
init|=
name|hibSession
operator|.
name|createQuery
argument_list|(
literal|"delete from OnlineSectioningLog where timeStamp< "
operator|+
name|HibernateUtil
operator|.
name|addDate
argument_list|(
literal|"current_date()"
argument_list|,
literal|":days"
argument_list|)
argument_list|)
operator|.
name|setInteger
argument_list|(
literal|"days"
argument_list|,
operator|-
name|days
argument_list|)
operator|.
name|executeUpdate
argument_list|()
decl_stmt|;
if|if
condition|(
name|rows
operator|>
literal|0
condition|)
name|sLog
operator|.
name|info
argument_list|(
literal|"All records older than "
operator|+
name|days
operator|+
literal|" days deleted from the online sectioning log ("
operator|+
name|rows
operator|+
literal|" records)."
argument_list|)
expr_stmt|;
name|tx
operator|.
name|commit
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Throwable
name|t
parameter_list|)
block|{
name|sLog
operator|.
name|warn
argument_list|(
literal|"Failed to cleanup query log: "
operator|+
name|t
operator|.
name|getMessage
argument_list|()
argument_list|,
name|t
argument_list|)
expr_stmt|;
if|if
condition|(
name|tx
operator|!=
literal|null
condition|)
name|tx
operator|.
name|rollback
argument_list|()
expr_stmt|;
block|}
finally|finally
block|{
name|hibSession
operator|.
name|close
argument_list|()
expr_stmt|;
block|}
block|}
specifier|public
specifier|static
name|void
name|cleanupMessageLog
parameter_list|(
name|int
name|days
parameter_list|)
block|{
if|if
condition|(
name|days
operator|<
literal|0
condition|)
return|return;
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
name|createNewSession
argument_list|()
decl_stmt|;
name|Transaction
name|tx
init|=
literal|null
decl_stmt|;
try|try
block|{
name|tx
operator|=
name|hibSession
operator|.
name|beginTransaction
argument_list|()
expr_stmt|;
name|int
name|rows
init|=
name|hibSession
operator|.
name|createQuery
argument_list|(
literal|"delete from MessageLog where timeStamp< "
operator|+
name|HibernateUtil
operator|.
name|addDate
argument_list|(
literal|"current_date()"
argument_list|,
literal|":days"
argument_list|)
argument_list|)
operator|.
name|setInteger
argument_list|(
literal|"days"
argument_list|,
operator|-
name|days
argument_list|)
operator|.
name|executeUpdate
argument_list|()
decl_stmt|;
if|if
condition|(
name|rows
operator|>
literal|0
condition|)
name|sLog
operator|.
name|info
argument_list|(
literal|"All records older than "
operator|+
name|days
operator|+
literal|" days deleted from the message log ("
operator|+
name|rows
operator|+
literal|" records)."
argument_list|)
expr_stmt|;
name|tx
operator|.
name|commit
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Throwable
name|t
parameter_list|)
block|{
name|sLog
operator|.
name|warn
argument_list|(
literal|"Failed to cleanup message log: "
operator|+
name|t
operator|.
name|getMessage
argument_list|()
argument_list|,
name|t
argument_list|)
expr_stmt|;
if|if
condition|(
name|tx
operator|!=
literal|null
condition|)
name|tx
operator|.
name|rollback
argument_list|()
expr_stmt|;
block|}
finally|finally
block|{
name|hibSession
operator|.
name|close
argument_list|()
expr_stmt|;
block|}
block|}
specifier|public
specifier|static
name|void
name|cleanupLogs
parameter_list|()
block|{
name|cleanupChangeLog
argument_list|(
name|Integer
operator|.
name|parseInt
argument_list|(
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"unitime.cleanup.changeLog"
argument_list|,
literal|"366"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|cleanupQueryLog
argument_list|(
name|Integer
operator|.
name|parseInt
argument_list|(
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"unitime.cleanup.queryLog"
argument_list|,
literal|"92"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|cleanupOnlineSectioningLog
argument_list|(
name|Integer
operator|.
name|parseInt
argument_list|(
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"unitime.cleanup.sectioningLog"
argument_list|,
literal|"366"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|cleanupMessageLog
argument_list|(
name|Integer
operator|.
name|parseInt
argument_list|(
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"unitime.message.log.cleanup.days"
argument_list|,
literal|"14"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

