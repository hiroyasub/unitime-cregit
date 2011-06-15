begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.2 (University Timetabling Application)  * Copyright (C) 2010, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *  * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
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
name|ArrayList
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Hashtable
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
name|Map
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
name|timetable
operator|.
name|model
operator|.
name|base
operator|.
name|BaseSavedHQL
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
name|SavedHQLDAO
import|;
end_import

begin_class
specifier|public
class|class
name|SavedHQL
extends|extends
name|BaseSavedHQL
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|2532519378106863655L
decl_stmt|;
specifier|public
name|SavedHQL
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
block|}
specifier|public
specifier|static
enum|enum
name|Flag
block|{
name|APPEARANCE_COURSES
argument_list|(
literal|"Appearance: Courses"
argument_list|,
literal|true
argument_list|)
block|,
name|APPEARANCE_EXAMS
argument_list|(
literal|"Appearance: Examinations"
argument_list|,
literal|true
argument_list|)
block|,
name|APPEARANCE_SECTIONING
argument_list|(
literal|"Appearance: Student Sectioning"
argument_list|,
literal|true
argument_list|)
block|,
name|APPEARANCE_EVENTS
argument_list|(
literal|"Appearance: Events"
argument_list|,
literal|true
argument_list|)
block|,
name|APPEARANCE_ADMINISTRATION
argument_list|(
literal|"Appearance: Administration"
argument_list|,
literal|true
argument_list|)
block|,
name|ADMIN_ONLY
argument_list|(
literal|"Restrictions: Administrator Only"
argument_list|,
literal|false
argument_list|)
block|;
specifier|private
name|String
name|iDescription
decl_stmt|;
specifier|private
name|boolean
name|iAppearance
decl_stmt|;
name|Flag
parameter_list|(
name|String
name|desc
parameter_list|,
name|boolean
name|appearance
parameter_list|)
block|{
name|iDescription
operator|=
name|desc
expr_stmt|;
name|iAppearance
operator|=
name|appearance
expr_stmt|;
block|}
specifier|public
name|int
name|flag
parameter_list|()
block|{
return|return
literal|1
operator|<<
name|ordinal
argument_list|()
return|;
block|}
specifier|public
name|boolean
name|isSet
parameter_list|(
name|int
name|type
parameter_list|)
block|{
return|return
operator|(
name|type
operator|&
name|flag
argument_list|()
operator|)
operator|!=
literal|0
return|;
block|}
specifier|public
name|String
name|description
parameter_list|()
block|{
return|return
name|iDescription
return|;
block|}
specifier|public
name|boolean
name|isAppearance
parameter_list|()
block|{
return|return
name|iAppearance
return|;
block|}
block|}
specifier|private
specifier|static
interface|interface
name|OptionImplementation
block|{
specifier|public
name|Map
argument_list|<
name|Long
argument_list|,
name|String
argument_list|>
name|getValues
parameter_list|(
name|User
name|user
parameter_list|)
function_decl|;
block|}
specifier|public
specifier|static
enum|enum
name|Option
block|{
name|SESSION
argument_list|(
literal|"Academic Session"
argument_list|,
literal|false
argument_list|,
literal|false
argument_list|,
operator|new
name|OptionImplementation
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|Map
argument_list|<
name|Long
argument_list|,
name|String
argument_list|>
name|getValues
parameter_list|(
name|User
name|user
parameter_list|)
block|{
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
if|if
condition|(
name|session
operator|==
literal|null
condition|)
return|return
literal|null
return|;
name|Map
argument_list|<
name|Long
argument_list|,
name|String
argument_list|>
name|ret
init|=
operator|new
name|Hashtable
argument_list|<
name|Long
argument_list|,
name|String
argument_list|>
argument_list|()
decl_stmt|;
name|ret
operator|.
name|put
argument_list|(
name|session
operator|.
name|getUniqueId
argument_list|()
argument_list|,
name|session
operator|.
name|getLabel
argument_list|()
argument_list|)
expr_stmt|;
return|return
name|ret
return|;
block|}
block|}
argument_list|)
block|,
name|DEPARTMENT
argument_list|(
literal|"Department"
argument_list|,
literal|true
argument_list|,
literal|false
argument_list|,
operator|new
name|OptionImplementation
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|Map
argument_list|<
name|Long
argument_list|,
name|String
argument_list|>
name|getValues
parameter_list|(
name|User
name|user
parameter_list|)
block|{
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
if|if
condition|(
name|session
operator|==
literal|null
condition|)
return|return
literal|null
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
literal|null
return|;
name|Map
argument_list|<
name|Long
argument_list|,
name|String
argument_list|>
name|ret
init|=
operator|new
name|Hashtable
argument_list|<
name|Long
argument_list|,
name|String
argument_list|>
argument_list|()
decl_stmt|;
if|if
condition|(
name|user
operator|.
name|isAdmin
argument_list|()
condition|)
block|{
for|for
control|(
name|Department
name|d
range|:
operator|(
name|Set
argument_list|<
name|Department
argument_list|>
operator|)
name|Department
operator|.
name|findAll
argument_list|(
name|session
operator|.
name|getUniqueId
argument_list|()
argument_list|)
control|)
name|ret
operator|.
name|put
argument_list|(
name|d
operator|.
name|getUniqueId
argument_list|()
argument_list|,
name|d
operator|.
name|htmlLabel
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
for|for
control|(
name|Department
name|d
range|:
operator|(
name|Set
argument_list|<
name|Department
argument_list|>
operator|)
name|Department
operator|.
name|findAllOwned
argument_list|(
name|session
operator|.
name|getUniqueId
argument_list|()
argument_list|,
name|manager
argument_list|,
literal|true
argument_list|)
control|)
name|ret
operator|.
name|put
argument_list|(
name|d
operator|.
name|getUniqueId
argument_list|()
argument_list|,
name|d
operator|.
name|htmlLabel
argument_list|()
argument_list|)
expr_stmt|;
block|}
return|return
name|ret
return|;
block|}
block|}
argument_list|)
block|,
name|DEPARTMENTS
argument_list|(
literal|"Departments"
argument_list|,
literal|true
argument_list|,
literal|true
argument_list|,
name|DEPARTMENT
operator|.
name|iImplementation
argument_list|)
block|,
name|SUBJECT
argument_list|(
literal|"Subject Area"
argument_list|,
literal|true
argument_list|,
literal|false
argument_list|,
operator|new
name|OptionImplementation
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|Map
argument_list|<
name|Long
argument_list|,
name|String
argument_list|>
name|getValues
parameter_list|(
name|User
name|user
parameter_list|)
block|{
name|Map
argument_list|<
name|Long
argument_list|,
name|String
argument_list|>
name|ret
init|=
operator|new
name|Hashtable
argument_list|<
name|Long
argument_list|,
name|String
argument_list|>
argument_list|()
decl_stmt|;
try|try
block|{
for|for
control|(
name|SubjectArea
name|s
range|:
operator|(
name|Set
argument_list|<
name|SubjectArea
argument_list|>
operator|)
name|TimetableManager
operator|.
name|getSubjectAreas
argument_list|(
name|user
argument_list|)
control|)
block|{
name|ret
operator|.
name|put
argument_list|(
name|s
operator|.
name|getUniqueId
argument_list|()
argument_list|,
name|s
operator|.
name|getSubjectAreaAbbreviation
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
return|return
literal|null
return|;
block|}
return|return
name|ret
return|;
block|}
block|}
argument_list|)
block|,
name|SUBJECTS
argument_list|(
literal|"Subject Areas"
argument_list|,
literal|true
argument_list|,
literal|true
argument_list|,
name|SUBJECT
operator|.
name|iImplementation
argument_list|)
block|,
name|BUILDING
argument_list|(
literal|"Buildings"
argument_list|,
literal|true
argument_list|,
literal|false
argument_list|,
operator|new
name|OptionImplementation
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|Map
argument_list|<
name|Long
argument_list|,
name|String
argument_list|>
name|getValues
parameter_list|(
name|User
name|user
parameter_list|)
block|{
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
if|if
condition|(
name|session
operator|==
literal|null
condition|)
return|return
literal|null
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
literal|null
return|;
name|Map
argument_list|<
name|Long
argument_list|,
name|String
argument_list|>
name|ret
init|=
operator|new
name|Hashtable
argument_list|<
name|Long
argument_list|,
name|String
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|Building
name|b
range|:
operator|(
name|List
argument_list|<
name|Building
argument_list|>
operator|)
name|Building
operator|.
name|findAll
argument_list|(
name|session
operator|.
name|getUniqueId
argument_list|()
argument_list|)
control|)
name|ret
operator|.
name|put
argument_list|(
name|b
operator|.
name|getUniqueId
argument_list|()
argument_list|,
name|b
operator|.
name|getAbbrName
argument_list|()
argument_list|)
expr_stmt|;
return|return
name|ret
return|;
block|}
block|}
argument_list|)
block|,
name|BUILDINGS
argument_list|(
literal|"Buildings"
argument_list|,
literal|true
argument_list|,
literal|true
argument_list|,
name|BUILDING
operator|.
name|iImplementation
argument_list|)
block|,
name|ROOM
argument_list|(
literal|"Room"
argument_list|,
literal|true
argument_list|,
literal|false
argument_list|,
operator|new
name|OptionImplementation
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|Map
argument_list|<
name|Long
argument_list|,
name|String
argument_list|>
name|getValues
parameter_list|(
name|User
name|user
parameter_list|)
block|{
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
if|if
condition|(
name|session
operator|==
literal|null
condition|)
return|return
literal|null
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
literal|null
return|;
name|Map
argument_list|<
name|Long
argument_list|,
name|String
argument_list|>
name|ret
init|=
operator|new
name|Hashtable
argument_list|<
name|Long
argument_list|,
name|String
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|Room
name|r
range|:
operator|(
name|List
argument_list|<
name|Room
argument_list|>
operator|)
name|Room
operator|.
name|findAllRooms
argument_list|(
name|session
operator|.
name|getUniqueId
argument_list|()
argument_list|)
control|)
block|{
name|ret
operator|.
name|put
argument_list|(
name|r
operator|.
name|getUniqueId
argument_list|()
argument_list|,
name|r
operator|.
name|getLabel
argument_list|()
argument_list|)
expr_stmt|;
block|}
return|return
name|ret
return|;
block|}
block|}
argument_list|)
block|,
name|ROOMS
argument_list|(
literal|"Rooms"
argument_list|,
literal|true
argument_list|,
literal|true
argument_list|,
name|ROOM
operator|.
name|iImplementation
argument_list|)
block|;
name|String
name|iName
decl_stmt|;
name|OptionImplementation
name|iImplementation
decl_stmt|;
name|boolean
name|iAllowSelection
decl_stmt|,
name|iMultiSelect
decl_stmt|;
name|Option
parameter_list|(
name|String
name|name
parameter_list|,
name|boolean
name|allowSelection
parameter_list|,
name|boolean
name|multiSelect
parameter_list|,
name|OptionImplementation
name|impl
parameter_list|)
block|{
name|iName
operator|=
name|name
expr_stmt|;
name|iAllowSelection
operator|=
name|allowSelection
expr_stmt|;
name|iMultiSelect
operator|=
name|multiSelect
expr_stmt|;
name|iImplementation
operator|=
name|impl
expr_stmt|;
block|}
specifier|public
name|String
name|text
parameter_list|()
block|{
return|return
name|iName
return|;
block|}
specifier|public
name|boolean
name|allowSingleSelection
parameter_list|()
block|{
return|return
name|iAllowSelection
return|;
block|}
specifier|public
name|boolean
name|allowMultiSelection
parameter_list|()
block|{
return|return
name|iAllowSelection
operator|&&
name|iMultiSelect
return|;
block|}
specifier|public
name|Map
argument_list|<
name|Long
argument_list|,
name|String
argument_list|>
name|values
parameter_list|(
name|User
name|user
parameter_list|)
block|{
return|return
name|iImplementation
operator|.
name|getValues
argument_list|(
name|user
argument_list|)
return|;
block|}
block|}
specifier|public
specifier|static
name|void
name|main
parameter_list|(
name|String
name|args
index|[]
parameter_list|)
block|{
for|for
control|(
name|Flag
name|f
range|:
name|Flag
operator|.
name|values
argument_list|()
control|)
block|{
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
name|f
operator|.
name|name
argument_list|()
operator|+
literal|": "
operator|+
name|f
operator|.
name|flag
argument_list|()
operator|+
literal|" ("
operator|+
name|f
operator|.
name|isSet
argument_list|(
literal|0xFF
argument_list|)
operator|+
literal|")"
argument_list|)
expr_stmt|;
block|}
block|}
specifier|public
name|boolean
name|isSet
parameter_list|(
name|Flag
name|f
parameter_list|)
block|{
return|return
name|getType
argument_list|()
operator|!=
literal|null
operator|&&
name|f
operator|.
name|isSet
argument_list|(
name|getType
argument_list|()
argument_list|)
return|;
block|}
specifier|public
name|void
name|set
parameter_list|(
name|Flag
name|f
parameter_list|)
block|{
if|if
condition|(
operator|!
name|isSet
argument_list|(
name|f
argument_list|)
condition|)
name|setType
argument_list|(
operator|(
name|getType
argument_list|()
operator|==
literal|null
condition|?
literal|0
else|:
name|getType
argument_list|()
operator|)
operator|+
name|f
operator|.
name|flag
argument_list|()
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|clear
parameter_list|(
name|Flag
name|f
parameter_list|)
block|{
if|if
condition|(
name|isSet
argument_list|(
name|f
argument_list|)
condition|)
name|setType
argument_list|(
name|getType
argument_list|()
operator|-
name|f
operator|.
name|flag
argument_list|()
argument_list|)
expr_stmt|;
block|}
specifier|public
specifier|static
name|List
argument_list|<
name|SavedHQL
argument_list|>
name|listAll
parameter_list|(
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
parameter_list|,
name|Flag
name|appearance
parameter_list|,
name|boolean
name|admin
parameter_list|)
block|{
synchronized|synchronized
init|(
name|sHasQueriesCache
init|)
block|{
name|List
argument_list|<
name|SavedHQL
argument_list|>
name|ret
init|=
operator|new
name|ArrayList
argument_list|<
name|SavedHQL
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|SavedHQL
name|hql
range|:
operator|(
name|List
argument_list|<
name|SavedHQL
argument_list|>
operator|)
operator|(
name|hibSession
operator|==
literal|null
condition|?
name|SavedHQLDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|getSession
argument_list|()
else|:
name|hibSession
operator|)
operator|.
name|createQuery
argument_list|(
literal|"from SavedHQL order by name"
argument_list|)
operator|.
name|setCacheable
argument_list|(
literal|true
argument_list|)
operator|.
name|list
argument_list|()
control|)
block|{
if|if
condition|(
operator|!
name|appearance
operator|.
name|isSet
argument_list|(
name|hql
operator|.
name|getType
argument_list|()
argument_list|)
condition|)
continue|continue;
if|if
condition|(
operator|!
name|admin
operator|&&
name|Flag
operator|.
name|ADMIN_ONLY
operator|.
name|isSet
argument_list|(
name|hql
operator|.
name|getType
argument_list|()
argument_list|)
condition|)
continue|continue;
name|ret
operator|.
name|add
argument_list|(
name|hql
argument_list|)
expr_stmt|;
block|}
name|sHasQueriesCache
operator|.
name|put
argument_list|(
name|appearance
operator|.
name|flag
argument_list|()
operator||
operator|(
name|admin
condition|?
name|Flag
operator|.
name|ADMIN_ONLY
operator|.
name|flag
argument_list|()
else|:
literal|0
operator|)
argument_list|,
operator|!
name|ret
operator|.
name|isEmpty
argument_list|()
argument_list|)
expr_stmt|;
return|return
name|ret
return|;
block|}
block|}
specifier|private
specifier|static
name|Hashtable
argument_list|<
name|Integer
argument_list|,
name|Boolean
argument_list|>
name|sHasQueriesCache
init|=
operator|new
name|Hashtable
argument_list|<
name|Integer
argument_list|,
name|Boolean
argument_list|>
argument_list|()
decl_stmt|;
specifier|public
specifier|static
name|boolean
name|hasQueries
parameter_list|(
name|Flag
name|appearance
parameter_list|,
name|boolean
name|admin
parameter_list|)
block|{
synchronized|synchronized
init|(
name|sHasQueriesCache
init|)
block|{
name|Boolean
name|ret
init|=
name|sHasQueriesCache
operator|.
name|get
argument_list|(
name|appearance
operator|.
name|flag
argument_list|()
operator||
operator|(
name|admin
condition|?
name|Flag
operator|.
name|ADMIN_ONLY
operator|.
name|flag
argument_list|()
else|:
literal|0
operator|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|ret
operator|==
literal|null
condition|)
name|ret
operator|=
operator|!
name|listAll
argument_list|(
literal|null
argument_list|,
name|appearance
argument_list|,
name|admin
argument_list|)
operator|.
name|isEmpty
argument_list|()
expr_stmt|;
return|return
name|ret
return|;
block|}
block|}
block|}
end_class

end_unit

