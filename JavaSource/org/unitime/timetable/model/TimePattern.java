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
operator|.
name|model
package|;
end_package

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
name|Iterator
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
name|java
operator|.
name|util
operator|.
name|Set
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|TreeSet
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Vector
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
name|HttpServletRequest
import|;
end_import

begin_import
import|import
name|org
operator|.
name|hibernate
operator|.
name|Query
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
name|timetable
operator|.
name|model
operator|.
name|base
operator|.
name|BaseTimePattern
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
name|TimePatternDAO
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
name|webutil
operator|.
name|RequiredTimeTable
import|;
end_import

begin_class
specifier|public
class|class
name|TimePattern
extends|extends
name|BaseTimePattern
implements|implements
name|Comparable
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|1L
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|int
name|sTypeStandard
init|=
literal|0
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|int
name|sTypeEvening
init|=
literal|1
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|int
name|sTypeSaturday
init|=
literal|2
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|int
name|sTypeMorning
init|=
literal|3
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|int
name|sTypeExtended
init|=
literal|4
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|int
name|sTypeExactTime
init|=
literal|5
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
index|[]
name|sTypes
init|=
operator|new
name|String
index|[]
block|{
literal|"Standard"
block|,
literal|"Evening"
block|,
literal|"Saturday"
block|,
literal|"Morning"
block|,
literal|"Extended"
block|,
literal|"Exact Time"
block|}
decl_stmt|;
comment|/** Request attribute name for available time patterns **/
specifier|public
specifier|static
name|String
name|TIME_PATTERN_ATTR_NAME
init|=
literal|"timePatternsList"
decl_stmt|;
comment|/*[CONSTRUCTOR MARKER BEGIN]*/
specifier|public
name|TimePattern
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
block|}
comment|/** 	 * Constructor for primary key 	 */
specifier|public
name|TimePattern
parameter_list|(
name|java
operator|.
name|lang
operator|.
name|Long
name|uniqueId
parameter_list|)
block|{
name|super
argument_list|(
name|uniqueId
argument_list|)
expr_stmt|;
block|}
comment|/** 	 * Constructor for required fields 	 */
specifier|public
name|TimePattern
parameter_list|(
name|java
operator|.
name|lang
operator|.
name|Long
name|uniqueId
parameter_list|,
name|Session
name|session
parameter_list|)
block|{
name|super
argument_list|(
name|uniqueId
argument_list|,
name|session
argument_list|)
expr_stmt|;
block|}
comment|/*[CONSTRUCTOR MARKER END]*/
specifier|public
specifier|static
name|Vector
name|findAll
parameter_list|(
name|HttpServletRequest
name|request
parameter_list|,
name|Boolean
name|visible
parameter_list|)
throws|throws
name|Exception
block|{
name|User
name|user
init|=
name|Web
operator|.
name|getUser
argument_list|(
name|request
operator|.
name|getSession
argument_list|()
argument_list|)
decl_stmt|;
name|Session
name|session
init|=
name|Session
operator|.
name|getCurrentAcadSession
argument_list|(
name|user
argument_list|)
decl_stmt|;
return|return
name|findAll
argument_list|(
name|session
argument_list|,
name|visible
argument_list|)
return|;
block|}
specifier|public
specifier|static
name|Vector
name|findAll
parameter_list|(
name|Session
name|session
parameter_list|,
name|Boolean
name|visible
parameter_list|)
block|{
return|return
name|findAll
argument_list|(
name|session
operator|.
name|getUniqueId
argument_list|()
argument_list|,
name|visible
argument_list|)
return|;
block|}
specifier|public
specifier|static
name|Vector
name|findAll
parameter_list|(
name|Long
name|sessionId
parameter_list|,
name|Boolean
name|visible
parameter_list|)
block|{
name|String
name|query
init|=
literal|"from TimePattern tp "
operator|+
literal|"where tp.session.uniqueId=:sessionId"
decl_stmt|;
if|if
condition|(
name|visible
operator|!=
literal|null
condition|)
name|query
operator|+=
literal|" and visible=:visible"
expr_stmt|;
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
init|=
operator|new
name|TimePatternDAO
argument_list|()
operator|.
name|getSession
argument_list|()
decl_stmt|;
name|Query
name|q
init|=
name|hibSession
operator|.
name|createQuery
argument_list|(
name|query
argument_list|)
decl_stmt|;
name|q
operator|.
name|setCacheable
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|q
operator|.
name|setLong
argument_list|(
literal|"sessionId"
argument_list|,
name|sessionId
operator|.
name|longValue
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|visible
operator|!=
literal|null
condition|)
name|q
operator|.
name|setBoolean
argument_list|(
literal|"visible"
argument_list|,
name|visible
operator|.
name|booleanValue
argument_list|()
argument_list|)
expr_stmt|;
name|Vector
name|v
init|=
operator|new
name|Vector
argument_list|(
name|q
operator|.
name|list
argument_list|()
argument_list|)
decl_stmt|;
name|Collections
operator|.
name|sort
argument_list|(
name|v
argument_list|)
expr_stmt|;
return|return
name|v
return|;
block|}
specifier|public
specifier|static
name|Vector
name|findApplicable
parameter_list|(
name|HttpServletRequest
name|request
parameter_list|,
name|int
name|minPerWeek
parameter_list|,
name|boolean
name|includeExactTime
parameter_list|,
name|Department
name|department
parameter_list|)
throws|throws
name|Exception
block|{
name|User
name|user
init|=
name|Web
operator|.
name|getUser
argument_list|(
name|request
operator|.
name|getSession
argument_list|()
argument_list|)
decl_stmt|;
name|Session
name|session
init|=
name|Session
operator|.
name|getCurrentAcadSession
argument_list|(
name|user
argument_list|)
decl_stmt|;
name|TimetableManager
name|mgr
init|=
name|TimetableManager
operator|.
name|getManager
argument_list|(
name|user
argument_list|)
decl_stmt|;
name|boolean
name|includeExtended
init|=
name|user
operator|.
name|isAdmin
argument_list|()
operator|||
operator|(
name|mgr
operator|!=
literal|null
operator|&&
name|mgr
operator|.
name|isExternalManager
argument_list|()
operator|)
decl_stmt|;
return|return
name|findByMinPerWeek
argument_list|(
name|session
argument_list|,
literal|false
argument_list|,
name|includeExtended
argument_list|,
name|includeExactTime
argument_list|,
name|minPerWeek
argument_list|,
operator|(
name|includeExtended
condition|?
literal|null
else|:
name|department
operator|)
argument_list|)
return|;
block|}
specifier|public
specifier|static
name|Vector
name|findByMinPerWeek
parameter_list|(
name|Session
name|session
parameter_list|,
name|boolean
name|includeHidden
parameter_list|,
name|boolean
name|includeExtended
parameter_list|,
name|boolean
name|includeExactTime
parameter_list|,
name|int
name|minPerWeek
parameter_list|,
name|Department
name|department
parameter_list|)
block|{
return|return
name|findByMinPerWeek
argument_list|(
name|session
operator|.
name|getUniqueId
argument_list|()
argument_list|,
name|includeHidden
argument_list|,
name|includeExtended
argument_list|,
name|includeExactTime
argument_list|,
name|minPerWeek
argument_list|,
name|department
argument_list|)
return|;
block|}
specifier|public
specifier|static
name|Vector
name|findByMinPerWeek
parameter_list|(
name|Long
name|sessionId
parameter_list|,
name|boolean
name|includeHidden
parameter_list|,
name|boolean
name|includeExtended
parameter_list|,
name|boolean
name|includeExactTime
parameter_list|,
name|int
name|minPerWeek
parameter_list|,
name|Department
name|department
parameter_list|)
block|{
name|Vector
name|list
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|includeExactTime
operator|&&
name|department
operator|==
literal|null
condition|)
block|{
name|list
operator|=
operator|new
name|Vector
argument_list|(
operator|(
operator|new
name|TimePatternDAO
argument_list|()
operator|)
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select distinct p from TimePattern as p "
operator|+
literal|"where p.session.uniqueId=:sessionId and "
operator|+
operator|(
operator|!
name|includeHidden
condition|?
literal|"p.visible=true and "
else|:
literal|""
operator|)
operator|+
literal|"(p.type="
operator|+
name|sTypeExactTime
operator|+
literal|" or ( p.type!="
operator|+
name|sTypeExactTime
operator|+
literal|" and "
operator|+
operator|(
operator|!
name|includeExtended
condition|?
literal|"p.type!="
operator|+
name|sTypeExtended
operator|+
literal|" and "
else|:
literal|""
operator|)
operator|+
literal|"p.minPerMtg * p.nrMeetings = :minPerWeek ))"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"sessionId"
argument_list|,
name|sessionId
operator|.
name|longValue
argument_list|()
argument_list|)
operator|.
name|setInteger
argument_list|(
literal|"minPerWeek"
argument_list|,
name|minPerWeek
argument_list|)
operator|.
name|setCacheable
argument_list|(
literal|true
argument_list|)
operator|.
name|list
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|list
operator|=
operator|new
name|Vector
argument_list|(
operator|(
operator|new
name|TimePatternDAO
argument_list|()
operator|)
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select distinct p from TimePattern as p "
operator|+
literal|"where p.session.uniqueId=:sessionId and "
operator|+
literal|"p.type!="
operator|+
name|sTypeExactTime
operator|+
literal|" and "
operator|+
operator|(
operator|!
name|includeHidden
condition|?
literal|"p.visible=true and "
else|:
literal|""
operator|)
operator|+
operator|(
operator|!
name|includeExtended
condition|?
literal|"p.type!="
operator|+
name|sTypeExtended
operator|+
literal|" and "
else|:
literal|""
operator|)
operator|+
literal|"p.minPerMtg * p.nrMeetings = :minPerWeek"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"sessionId"
argument_list|,
name|sessionId
operator|.
name|longValue
argument_list|()
argument_list|)
operator|.
name|setInteger
argument_list|(
literal|"minPerWeek"
argument_list|,
name|minPerWeek
argument_list|)
operator|.
name|setCacheable
argument_list|(
literal|true
argument_list|)
operator|.
name|list
argument_list|()
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
operator|!
name|includeExtended
operator|&&
name|department
operator|!=
literal|null
condition|)
block|{
for|for
control|(
name|Iterator
name|i
init|=
name|department
operator|.
name|getTimePatterns
argument_list|()
operator|.
name|iterator
argument_list|()
init|;
name|i
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|TimePattern
name|tp
init|=
operator|(
name|TimePattern
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|tp
operator|.
name|getMinPerMtg
argument_list|()
operator|.
name|intValue
argument_list|()
operator|*
name|tp
operator|.
name|getNrMeetings
argument_list|()
operator|.
name|intValue
argument_list|()
operator|!=
name|minPerWeek
condition|)
continue|continue;
if|if
condition|(
name|tp
operator|.
name|getType
argument_list|()
operator|.
name|intValue
argument_list|()
operator|!=
name|sTypeExtended
condition|)
continue|continue;
if|if
condition|(
operator|!
name|includeHidden
operator|&&
operator|!
name|tp
operator|.
name|isVisible
argument_list|()
operator|.
name|booleanValue
argument_list|()
condition|)
continue|continue;
name|list
operator|.
name|add
argument_list|(
name|tp
argument_list|)
expr_stmt|;
block|}
block|}
if|if
condition|(
name|includeExactTime
operator|&&
name|department
operator|!=
literal|null
condition|)
block|{
for|for
control|(
name|Iterator
name|i
init|=
name|department
operator|.
name|getTimePatterns
argument_list|()
operator|.
name|iterator
argument_list|()
init|;
name|i
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|TimePattern
name|tp
init|=
operator|(
name|TimePattern
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|tp
operator|.
name|getType
argument_list|()
operator|.
name|intValue
argument_list|()
operator|!=
name|sTypeExactTime
condition|)
continue|continue;
name|list
operator|.
name|add
argument_list|(
name|tp
argument_list|)
expr_stmt|;
break|break;
block|}
block|}
name|Collections
operator|.
name|sort
argument_list|(
name|list
argument_list|)
expr_stmt|;
return|return
name|list
return|;
block|}
specifier|public
specifier|static
name|TimePattern
name|findByName
parameter_list|(
name|HttpServletRequest
name|request
parameter_list|,
name|String
name|name
parameter_list|)
throws|throws
name|Exception
block|{
name|User
name|user
init|=
name|Web
operator|.
name|getUser
argument_list|(
name|request
operator|.
name|getSession
argument_list|()
argument_list|)
decl_stmt|;
name|Session
name|session
init|=
name|Session
operator|.
name|getCurrentAcadSession
argument_list|(
name|user
argument_list|)
decl_stmt|;
name|boolean
name|includeExtended
init|=
name|user
operator|.
name|isAdmin
argument_list|()
decl_stmt|;
return|return
name|findByName
argument_list|(
name|session
argument_list|,
name|name
argument_list|)
return|;
block|}
specifier|public
specifier|static
name|TimePattern
name|findByName
parameter_list|(
name|Session
name|session
parameter_list|,
name|String
name|name
parameter_list|)
block|{
return|return
name|findByName
argument_list|(
name|session
operator|.
name|getUniqueId
argument_list|()
argument_list|,
name|name
argument_list|)
return|;
block|}
specifier|public
specifier|static
name|TimePattern
name|findByName
parameter_list|(
name|Long
name|sessionId
parameter_list|,
name|String
name|name
parameter_list|)
block|{
name|List
name|list
init|=
operator|(
operator|new
name|TimePatternDAO
argument_list|()
operator|)
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select distinct p from TimePattern as p "
operator|+
literal|"where p.session.uniqueId=:sessionId and "
operator|+
literal|"p.name=:name"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"sessionId"
argument_list|,
name|sessionId
operator|.
name|longValue
argument_list|()
argument_list|)
operator|.
name|setText
argument_list|(
literal|"name"
argument_list|,
name|name
argument_list|)
operator|.
name|setCacheable
argument_list|(
literal|true
argument_list|)
operator|.
name|list
argument_list|()
decl_stmt|;
if|if
condition|(
name|list
operator|==
literal|null
operator|||
name|list
operator|.
name|isEmpty
argument_list|()
condition|)
return|return
literal|null
return|;
return|return
operator|(
name|TimePattern
operator|)
name|list
operator|.
name|get
argument_list|(
literal|0
argument_list|)
return|;
block|}
comment|/**      * Returns time string only. The subclasses append the type       */
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
name|getName
argument_list|()
return|;
block|}
specifier|public
name|boolean
name|equals
parameter_list|(
name|Object
name|o
parameter_list|)
block|{
if|if
condition|(
operator|(
name|o
operator|==
literal|null
operator|)
operator|||
operator|!
operator|(
name|o
operator|instanceof
name|TimePattern
operator|)
condition|)
return|return
literal|false
return|;
return|return
name|getUniqueId
argument_list|()
operator|.
name|equals
argument_list|(
operator|(
operator|(
name|TimePattern
operator|)
name|o
operator|)
operator|.
name|getUniqueId
argument_list|()
argument_list|)
return|;
block|}
specifier|public
name|int
name|compareTo
parameter_list|(
name|Object
name|o
parameter_list|)
block|{
if|if
condition|(
operator|(
name|o
operator|==
literal|null
operator|)
operator|||
operator|!
operator|(
name|o
operator|instanceof
name|TimePattern
operator|)
condition|)
return|return
operator|-
literal|1
return|;
name|TimePattern
name|t
init|=
operator|(
name|TimePattern
operator|)
name|o
decl_stmt|;
name|int
name|cmp
init|=
name|getType
argument_list|()
operator|.
name|compareTo
argument_list|(
name|t
operator|.
name|getType
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|cmp
operator|!=
literal|0
condition|)
return|return
name|cmp
return|;
name|cmp
operator|=
operator|-
name|getNrMeetings
argument_list|()
operator|.
name|compareTo
argument_list|(
name|t
operator|.
name|getNrMeetings
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|cmp
operator|!=
literal|0
condition|)
return|return
name|cmp
return|;
name|cmp
operator|=
name|getMinPerMtg
argument_list|()
operator|.
name|compareTo
argument_list|(
name|t
operator|.
name|getMinPerMtg
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|cmp
operator|!=
literal|0
condition|)
return|return
name|cmp
return|;
name|int
name|nrComb
init|=
name|getTimes
argument_list|()
operator|.
name|size
argument_list|()
operator|*
name|getDays
argument_list|()
operator|.
name|size
argument_list|()
decl_stmt|;
name|int
name|nrCombT
init|=
name|t
operator|.
name|getTimes
argument_list|()
operator|.
name|size
argument_list|()
operator|*
name|t
operator|.
name|getDays
argument_list|()
operator|.
name|size
argument_list|()
decl_stmt|;
name|cmp
operator|=
name|Double
operator|.
name|compare
argument_list|(
name|nrComb
argument_list|,
name|nrCombT
argument_list|)
expr_stmt|;
if|if
condition|(
name|cmp
operator|!=
literal|0
condition|)
return|return
name|cmp
return|;
return|return
name|getName
argument_list|()
operator|.
name|compareTo
argument_list|(
name|t
operator|.
name|getName
argument_list|()
argument_list|)
return|;
block|}
specifier|public
name|TimePatternModel
name|getTimePatternModel
parameter_list|()
block|{
return|return
name|getTimePatternModel
argument_list|(
literal|null
argument_list|,
literal|true
argument_list|)
return|;
block|}
specifier|public
name|TimePatternModel
name|getTimePatternModel
parameter_list|(
name|boolean
name|allowHardPreferences
parameter_list|)
block|{
return|return
name|getTimePatternModel
argument_list|(
literal|null
argument_list|,
name|allowHardPreferences
argument_list|)
return|;
block|}
specifier|public
name|TimePatternModel
name|getTimePatternModel
parameter_list|(
name|Assignment
name|assignment
parameter_list|,
name|boolean
name|allowHardPreferences
parameter_list|)
block|{
return|return
operator|new
name|TimePatternModel
argument_list|(
name|this
argument_list|,
name|assignment
argument_list|,
name|allowHardPreferences
argument_list|)
return|;
block|}
specifier|public
specifier|static
name|Set
name|findAllUsed
parameter_list|(
name|Session
name|session
parameter_list|)
block|{
return|return
name|findAllUsed
argument_list|(
name|session
operator|.
name|getUniqueId
argument_list|()
argument_list|)
return|;
block|}
specifier|public
specifier|static
name|Set
name|findAllUsed
parameter_list|(
name|Long
name|sessionId
parameter_list|)
block|{
name|TreeSet
name|ret
init|=
operator|new
name|TreeSet
argument_list|(
operator|(
operator|new
name|TimePatternDAO
argument_list|()
operator|)
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select distinct tp from TimePref as p inner join p.timePattern as tp where tp.session.uniqueId=:sessionId"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"sessionId"
argument_list|,
name|sessionId
operator|.
name|longValue
argument_list|()
argument_list|)
operator|.
name|setCacheable
argument_list|(
literal|true
argument_list|)
operator|.
name|list
argument_list|()
argument_list|)
decl_stmt|;
name|ret
operator|.
name|addAll
argument_list|(
operator|(
operator|new
name|TimePatternDAO
argument_list|()
operator|)
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select distinct tp from Assignment as a inner join a.timePattern as tp where tp.session.uniqueId=:sessionId"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"sessionId"
argument_list|,
name|sessionId
operator|.
name|longValue
argument_list|()
argument_list|)
operator|.
name|setCacheable
argument_list|(
literal|true
argument_list|)
operator|.
name|list
argument_list|()
argument_list|)
expr_stmt|;
return|return
name|ret
return|;
block|}
specifier|public
name|boolean
name|isEditable
parameter_list|()
block|{
return|return
operator|!
name|findAllUsed
argument_list|(
name|getSession
argument_list|()
argument_list|)
operator|.
name|contains
argument_list|(
name|this
argument_list|)
return|;
block|}
specifier|public
specifier|static
name|RequiredTimeTable
name|getDefaultRequiredTimeTable
parameter_list|()
block|{
return|return
operator|new
name|RequiredTimeTable
argument_list|(
operator|new
name|TimePatternModel
argument_list|()
argument_list|)
return|;
block|}
specifier|public
name|RequiredTimeTable
name|getRequiredTimeTable
parameter_list|(
name|boolean
name|allowHardPreferences
parameter_list|)
block|{
return|return
name|getRequiredTimeTable
argument_list|(
literal|null
argument_list|,
name|allowHardPreferences
argument_list|)
return|;
block|}
specifier|public
name|RequiredTimeTable
name|getRequiredTimeTable
parameter_list|(
name|Assignment
name|assignment
parameter_list|,
name|boolean
name|allowHardPreferences
parameter_list|)
block|{
return|return
operator|new
name|RequiredTimeTable
argument_list|(
name|getTimePatternModel
argument_list|(
name|assignment
argument_list|,
name|allowHardPreferences
argument_list|)
argument_list|)
return|;
block|}
specifier|public
name|Set
name|getDepartments
parameter_list|(
name|Long
name|sessionId
parameter_list|)
block|{
name|TreeSet
name|ret
init|=
operator|new
name|TreeSet
argument_list|()
decl_stmt|;
for|for
control|(
name|Iterator
name|i
init|=
name|getDepartments
argument_list|()
operator|.
name|iterator
argument_list|()
init|;
name|i
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|Department
name|d
init|=
operator|(
name|Department
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|sessionId
operator|==
literal|null
operator|||
name|d
operator|.
name|getSession
argument_list|()
operator|.
name|getUniqueId
argument_list|()
operator|.
name|equals
argument_list|(
name|sessionId
argument_list|)
condition|)
name|ret
operator|.
name|add
argument_list|(
name|d
argument_list|)
expr_stmt|;
block|}
return|return
name|ret
return|;
block|}
specifier|public
name|Integer
name|getBreakTime
parameter_list|()
block|{
name|Integer
name|breakTime
init|=
name|super
operator|.
name|getBreakTime
argument_list|()
decl_stmt|;
if|if
condition|(
name|breakTime
operator|!=
literal|null
condition|)
return|return
name|breakTime
return|;
if|if
condition|(
name|getSlotsPerMtg
argument_list|()
operator|==
literal|null
condition|)
return|return
operator|new
name|Integer
argument_list|(
literal|10
argument_list|)
return|;
if|if
condition|(
name|getSlotsPerMtg
argument_list|()
operator|.
name|intValue
argument_list|()
operator|%
literal|12
operator|==
literal|0
condition|)
return|return
operator|new
name|Integer
argument_list|(
literal|10
argument_list|)
return|;
if|if
condition|(
name|getSlotsPerMtg
argument_list|()
operator|.
name|intValue
argument_list|()
operator|>
literal|6
condition|)
return|return
operator|new
name|Integer
argument_list|(
literal|15
argument_list|)
return|;
if|if
condition|(
name|getType
argument_list|()
operator|.
name|intValue
argument_list|()
operator|==
name|sTypeExactTime
condition|)
return|return
operator|new
name|Integer
argument_list|(
literal|10
argument_list|)
return|;
return|return
operator|new
name|Integer
argument_list|(
literal|0
argument_list|)
return|;
block|}
specifier|public
name|Object
name|clone
parameter_list|()
block|{
name|TimePattern
name|newTimePattern
init|=
operator|new
name|TimePattern
argument_list|()
decl_stmt|;
name|newTimePattern
operator|.
name|setBreakTime
argument_list|(
name|getBreakTime
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|getDays
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|TimePatternDays
name|origTpDays
init|=
literal|null
decl_stmt|;
name|TimePatternDays
name|newTpDays
init|=
literal|null
decl_stmt|;
for|for
control|(
name|Iterator
name|dIt
init|=
name|getDays
argument_list|()
operator|.
name|iterator
argument_list|()
init|;
name|dIt
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|origTpDays
operator|=
operator|(
name|TimePatternDays
operator|)
name|dIt
operator|.
name|next
argument_list|()
expr_stmt|;
name|newTpDays
operator|=
operator|new
name|TimePatternDays
argument_list|()
expr_stmt|;
name|newTpDays
operator|.
name|setDayCode
argument_list|(
name|origTpDays
operator|.
name|getDayCode
argument_list|()
argument_list|)
expr_stmt|;
name|newTimePattern
operator|.
name|addTodays
argument_list|(
name|newTpDays
argument_list|)
expr_stmt|;
block|}
block|}
name|newTimePattern
operator|.
name|setMinPerMtg
argument_list|(
name|getMinPerMtg
argument_list|()
argument_list|)
expr_stmt|;
name|newTimePattern
operator|.
name|setName
argument_list|(
name|getName
argument_list|()
argument_list|)
expr_stmt|;
name|newTimePattern
operator|.
name|setNrMeetings
argument_list|(
name|getNrMeetings
argument_list|()
argument_list|)
expr_stmt|;
name|newTimePattern
operator|.
name|setSlotsPerMtg
argument_list|(
name|getSlotsPerMtg
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|getTimes
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|TimePatternTime
name|origTpTime
init|=
literal|null
decl_stmt|;
name|TimePatternTime
name|newTpTime
init|=
literal|null
decl_stmt|;
for|for
control|(
name|Iterator
name|it
init|=
name|getTimes
argument_list|()
operator|.
name|iterator
argument_list|()
init|;
name|it
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|origTpTime
operator|=
operator|(
name|TimePatternTime
operator|)
name|it
operator|.
name|next
argument_list|()
expr_stmt|;
name|newTpTime
operator|=
operator|new
name|TimePatternTime
argument_list|()
expr_stmt|;
name|newTpTime
operator|.
name|setStartSlot
argument_list|(
name|origTpTime
operator|.
name|getStartSlot
argument_list|()
argument_list|)
expr_stmt|;
name|newTimePattern
operator|.
name|addTotimes
argument_list|(
name|newTpTime
argument_list|)
expr_stmt|;
block|}
block|}
name|newTimePattern
operator|.
name|setSession
argument_list|(
name|getSession
argument_list|()
argument_list|)
expr_stmt|;
name|newTimePattern
operator|.
name|setType
argument_list|(
name|getType
argument_list|()
argument_list|)
expr_stmt|;
name|newTimePattern
operator|.
name|setVisible
argument_list|(
name|isVisible
argument_list|()
argument_list|)
expr_stmt|;
return|return
name|newTimePattern
return|;
block|}
block|}
end_class

end_unit

