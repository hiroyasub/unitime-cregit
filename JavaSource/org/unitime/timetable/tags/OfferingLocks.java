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
name|tags
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|ArrayList
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Collections
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|List
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|servlet
operator|.
name|jsp
operator|.
name|tagext
operator|.
name|TagSupport
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
name|commons
operator|.
name|User
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
name|web
operator|.
name|Web
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
name|CourseMessages
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
name|InstructionalOffering
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
name|Session
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
name|TimetableManager
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
name|comparators
operator|.
name|InstructionalOfferingComparator
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
name|InstructionalOfferingDAO
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|OfferingLocks
extends|extends
name|TagSupport
block|{
specifier|private
specifier|static
name|CourseMessages
name|MSG
init|=
name|Localization
operator|.
name|create
argument_list|(
name|CourseMessages
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|7947787141769725429L
decl_stmt|;
specifier|public
name|String
name|getOfferingLocksWarning
parameter_list|(
name|User
name|user
parameter_list|,
name|Session
name|session
parameter_list|)
block|{
if|if
condition|(
operator|!
name|session
operator|.
name|getStatusType
argument_list|()
operator|.
name|canLockOfferings
argument_list|()
condition|)
return|return
literal|null
return|;
name|List
argument_list|<
name|InstructionalOffering
argument_list|>
name|lockedOfferings
init|=
operator|new
name|ArrayList
argument_list|<
name|InstructionalOffering
argument_list|>
argument_list|()
decl_stmt|;
if|if
condition|(
name|session
operator|.
name|getLockedOfferings
argument_list|()
operator|==
literal|null
condition|)
return|return
literal|null
return|;
for|for
control|(
name|Long
name|offeringId
range|:
name|session
operator|.
name|getLockedOfferings
argument_list|()
control|)
block|{
name|InstructionalOffering
name|io
init|=
name|InstructionalOfferingDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|get
argument_list|(
name|offeringId
argument_list|)
decl_stmt|;
if|if
condition|(
name|io
operator|!=
literal|null
operator|&&
name|io
operator|.
name|isLockableBy
argument_list|(
name|user
argument_list|)
condition|)
name|lockedOfferings
operator|.
name|add
argument_list|(
name|io
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|lockedOfferings
operator|.
name|isEmpty
argument_list|()
condition|)
return|return
literal|null
return|;
name|Collections
operator|.
name|sort
argument_list|(
name|lockedOfferings
argument_list|,
operator|new
name|InstructionalOfferingComparator
argument_list|(
literal|null
argument_list|)
argument_list|)
expr_stmt|;
name|String
name|course1
init|=
literal|null
decl_stmt|;
name|String
name|course2
init|=
literal|null
decl_stmt|;
for|for
control|(
name|InstructionalOffering
name|io
range|:
name|lockedOfferings
control|)
block|{
if|if
condition|(
name|course1
operator|==
literal|null
condition|)
block|{
name|course1
operator|=
literal|"<a href='instructionalOfferingDetail.do?io="
operator|+
name|io
operator|.
name|getUniqueId
argument_list|()
operator|+
literal|"'>"
operator|+
name|io
operator|.
name|getCourseName
argument_list|()
operator|+
literal|"</a>"
expr_stmt|;
block|}
else|else
block|{
if|if
condition|(
name|course2
operator|!=
literal|null
condition|)
name|course1
operator|+=
literal|", "
operator|+
name|course2
expr_stmt|;
name|course2
operator|=
literal|"<a href='instructionalOfferingDetail.do?io="
operator|+
name|io
operator|.
name|getUniqueId
argument_list|()
operator|+
literal|"'>"
operator|+
name|io
operator|.
name|getCourseName
argument_list|()
operator|+
literal|"</a>"
expr_stmt|;
block|}
block|}
return|return
operator|(
name|course2
operator|==
literal|null
condition|?
name|MSG
operator|.
name|lockedCourse
argument_list|(
name|course1
argument_list|)
else|:
name|MSG
operator|.
name|lockedCourses
argument_list|(
name|course1
argument_list|,
name|course2
argument_list|)
operator|)
return|;
block|}
specifier|public
name|int
name|doStartTag
parameter_list|()
block|{
try|try
block|{
name|User
name|user
init|=
name|Web
operator|.
name|getUser
argument_list|(
name|pageContext
operator|.
name|getSession
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|user
operator|==
literal|null
condition|)
return|return
name|SKIP_BODY
return|;
name|TimetableManager
name|manager
init|=
name|TimetableManager
operator|.
name|getManager
argument_list|(
name|user
argument_list|)
decl_stmt|;
if|if
condition|(
name|manager
operator|==
literal|null
condition|)
return|return
name|SKIP_BODY
return|;
name|Session
name|acadSession
init|=
name|Session
operator|.
name|getCurrentAcadSession
argument_list|(
name|user
argument_list|)
decl_stmt|;
if|if
condition|(
name|acadSession
operator|==
literal|null
condition|)
return|return
name|SKIP_BODY
return|;
name|String
name|warns
init|=
name|getOfferingLocksWarning
argument_list|(
name|user
argument_list|,
name|acadSession
argument_list|)
decl_stmt|;
if|if
condition|(
name|warns
operator|!=
literal|null
condition|)
block|{
name|pageContext
operator|.
name|getOut
argument_list|()
operator|.
name|println
argument_list|(
literal|"<table width='100%' border='0' cellpadding='3' cellspacing='0'><tr>"
argument_list|)
expr_stmt|;
name|pageContext
operator|.
name|getOut
argument_list|()
operator|.
name|println
argument_list|(
literal|"<td class=\"unitime-MessageYellow\" width='5'>&nbsp;</td>"
argument_list|)
expr_stmt|;
name|pageContext
operator|.
name|getOut
argument_list|()
operator|.
name|println
argument_list|(
literal|"<td class=\"unitime-MessageYellow\">"
argument_list|)
expr_stmt|;
name|pageContext
operator|.
name|getOut
argument_list|()
operator|.
name|println
argument_list|(
name|warns
argument_list|)
expr_stmt|;
name|pageContext
operator|.
name|getOut
argument_list|()
operator|.
name|println
argument_list|(
literal|"</td></tr></table>"
argument_list|)
expr_stmt|;
block|}
return|return
name|SKIP_BODY
return|;
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
return|return
name|SKIP_BODY
return|;
block|}
block|}
specifier|public
name|int
name|doEndTag
parameter_list|()
block|{
return|return
name|EVAL_PAGE
return|;
block|}
block|}
end_class

end_unit

