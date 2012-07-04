begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.2 (University Timetabling Application)  * Copyright (C) 2009 - 2010, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
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
name|course
operator|.
name|ui
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|Serializable
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Collection
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Date
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|HashSet
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
name|java
operator|.
name|util
operator|.
name|TreeSet
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
name|Assignment
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
name|Class_
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
name|PreferenceLevel
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
name|Student
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
name|Class_DAO
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|ClassAssignmentInfo
extends|extends
name|ClassAssignment
implements|implements
name|Serializable
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
operator|-
literal|4277344877497509285L
decl_stmt|;
specifier|private
name|TreeSet
argument_list|<
name|StudentConflict
argument_list|>
name|iStudentConflicts
init|=
operator|new
name|TreeSet
argument_list|()
decl_stmt|;
specifier|public
name|ClassAssignmentInfo
parameter_list|(
name|Assignment
name|assignment
parameter_list|)
block|{
name|super
argument_list|(
name|assignment
argument_list|)
expr_stmt|;
name|findStudentConflicts
argument_list|(
literal|null
argument_list|)
expr_stmt|;
block|}
specifier|public
name|ClassAssignmentInfo
parameter_list|(
name|Class_
name|clazz
parameter_list|,
name|ClassTimeInfo
name|time
parameter_list|,
name|ClassDateInfo
name|date
parameter_list|,
name|Collection
argument_list|<
name|ClassRoomInfo
argument_list|>
name|rooms
parameter_list|)
block|{
name|super
argument_list|(
name|clazz
argument_list|,
name|time
argument_list|,
name|date
argument_list|,
name|rooms
argument_list|)
expr_stmt|;
name|findStudentConflicts
argument_list|(
literal|null
argument_list|)
expr_stmt|;
block|}
specifier|public
name|ClassAssignmentInfo
parameter_list|(
name|Class_
name|clazz
parameter_list|,
name|ClassTimeInfo
name|time
parameter_list|,
name|ClassDateInfo
name|date
parameter_list|,
name|Collection
argument_list|<
name|ClassRoomInfo
argument_list|>
name|rooms
parameter_list|,
name|Hashtable
argument_list|<
name|Long
argument_list|,
name|ClassAssignment
argument_list|>
name|assignmentTable
parameter_list|)
block|{
name|super
argument_list|(
name|clazz
argument_list|,
name|time
argument_list|,
name|date
argument_list|,
name|rooms
argument_list|)
expr_stmt|;
name|findStudentConflicts
argument_list|(
name|assignmentTable
argument_list|)
expr_stmt|;
block|}
specifier|private
name|void
name|findStudentConflicts
parameter_list|(
name|Hashtable
argument_list|<
name|Long
argument_list|,
name|ClassAssignment
argument_list|>
name|assignmentTable
parameter_list|)
block|{
if|if
condition|(
operator|!
name|hasTime
argument_list|()
condition|)
return|return;
comment|//TODO: This might be done much faster
name|Hashtable
argument_list|<
name|Long
argument_list|,
name|Set
argument_list|<
name|Long
argument_list|>
argument_list|>
name|conflicts
init|=
name|Student
operator|.
name|findConflictingStudents
argument_list|(
name|getClassId
argument_list|()
argument_list|,
name|getTime
argument_list|()
operator|.
name|getStartSlot
argument_list|()
argument_list|,
name|getTime
argument_list|()
operator|.
name|getLength
argument_list|()
argument_list|,
name|getTime
argument_list|()
operator|.
name|getDates
argument_list|()
argument_list|)
decl_stmt|;
for|for
control|(
name|Map
operator|.
name|Entry
argument_list|<
name|Long
argument_list|,
name|Set
argument_list|<
name|Long
argument_list|>
argument_list|>
name|entry
range|:
name|conflicts
operator|.
name|entrySet
argument_list|()
control|)
block|{
if|if
condition|(
name|getClassId
argument_list|()
operator|.
name|equals
argument_list|(
name|entry
operator|.
name|getKey
argument_list|()
argument_list|)
condition|)
continue|continue;
if|if
condition|(
name|assignmentTable
operator|!=
literal|null
operator|&&
name|assignmentTable
operator|.
name|containsKey
argument_list|(
name|entry
operator|.
name|getKey
argument_list|()
argument_list|)
condition|)
continue|continue;
name|Class_
name|clazz
init|=
name|Class_DAO
operator|.
name|getInstance
argument_list|()
operator|.
name|get
argument_list|(
name|entry
operator|.
name|getKey
argument_list|()
argument_list|)
decl_stmt|;
name|iStudentConflicts
operator|.
name|add
argument_list|(
operator|new
name|StudentConflict
argument_list|(
operator|new
name|ClassAssignment
argument_list|(
name|clazz
operator|.
name|getCommittedAssignment
argument_list|()
argument_list|)
argument_list|,
name|entry
operator|.
name|getValue
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|assignmentTable
operator|!=
literal|null
condition|)
for|for
control|(
name|Map
operator|.
name|Entry
argument_list|<
name|Long
argument_list|,
name|ClassAssignment
argument_list|>
name|entry
range|:
name|assignmentTable
operator|.
name|entrySet
argument_list|()
control|)
block|{
if|if
condition|(
name|getClassId
argument_list|()
operator|.
name|equals
argument_list|(
name|entry
operator|.
name|getKey
argument_list|()
argument_list|)
condition|)
continue|continue;
if|if
condition|(
operator|!
name|entry
operator|.
name|getValue
argument_list|()
operator|.
name|hasTime
argument_list|()
condition|)
continue|continue;
if|if
condition|(
operator|!
name|getTime
argument_list|()
operator|.
name|overlaps
argument_list|(
name|entry
operator|.
name|getValue
argument_list|()
operator|.
name|getTime
argument_list|()
argument_list|)
condition|)
continue|continue;
name|Set
argument_list|<
name|Long
argument_list|>
name|conf
init|=
name|merge
argument_list|(
name|getStudents
argument_list|()
argument_list|,
name|entry
operator|.
name|getValue
argument_list|()
operator|.
name|getStudents
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|conf
operator|.
name|isEmpty
argument_list|()
condition|)
name|iStudentConflicts
operator|.
name|add
argument_list|(
operator|new
name|StudentConflict
argument_list|(
name|entry
operator|.
name|getValue
argument_list|()
argument_list|,
name|conf
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
specifier|public
name|Set
argument_list|<
name|StudentConflict
argument_list|>
name|getStudentConflicts
parameter_list|()
block|{
return|return
name|iStudentConflicts
return|;
block|}
specifier|public
name|int
name|getNrStudentCounflicts
parameter_list|()
block|{
name|Set
argument_list|<
name|Long
argument_list|>
name|all
init|=
operator|new
name|HashSet
argument_list|<
name|Long
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|StudentConflict
name|c
range|:
name|iStudentConflicts
control|)
name|all
operator|.
name|addAll
argument_list|(
name|c
operator|.
name|getConflictingStudents
argument_list|()
argument_list|)
expr_stmt|;
return|return
name|all
operator|.
name|size
argument_list|()
return|;
block|}
specifier|public
name|String
name|getConflictTable
parameter_list|()
block|{
return|return
name|getConflictTable
argument_list|(
literal|true
argument_list|)
return|;
block|}
specifier|public
name|String
name|getConflictTable
parameter_list|(
name|boolean
name|header
parameter_list|)
block|{
name|String
name|ret
init|=
literal|"<table border='0' width='100%' cellspacing='0' cellpadding='3'>"
decl_stmt|;
if|if
condition|(
name|header
condition|)
block|{
name|ret
operator|+=
literal|"<tr>"
expr_stmt|;
name|ret
operator|+=
literal|"<td><i>Students</i></td>"
expr_stmt|;
name|ret
operator|+=
literal|"<td><i>Class</i></td>"
expr_stmt|;
name|ret
operator|+=
literal|"<td><i>Date</i></td>"
expr_stmt|;
name|ret
operator|+=
literal|"<td><i>Time</i></td>"
expr_stmt|;
name|ret
operator|+=
literal|"<td><i>Room</i></td>"
expr_stmt|;
name|ret
operator|+=
literal|"</tr>"
expr_stmt|;
block|}
for|for
control|(
name|StudentConflict
name|conf
range|:
name|getStudentConflicts
argument_list|()
control|)
name|ret
operator|+=
name|conf
operator|.
name|toHtml
argument_list|()
expr_stmt|;
name|ret
operator|+=
literal|"</table>"
expr_stmt|;
return|return
name|ret
return|;
block|}
specifier|public
specifier|static
name|Set
argument_list|<
name|Long
argument_list|>
name|merge
parameter_list|(
name|Set
argument_list|<
name|Long
argument_list|>
name|a
parameter_list|,
name|Set
argument_list|<
name|Long
argument_list|>
name|b
parameter_list|)
block|{
name|Set
argument_list|<
name|Long
argument_list|>
name|ret
init|=
operator|new
name|HashSet
argument_list|<
name|Long
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|Long
name|x
range|:
name|a
control|)
if|if
condition|(
name|b
operator|.
name|contains
argument_list|(
name|x
argument_list|)
condition|)
name|ret
operator|.
name|add
argument_list|(
name|x
argument_list|)
expr_stmt|;
return|return
name|ret
return|;
block|}
specifier|public
class|class
name|StudentConflict
implements|implements
name|Serializable
implements|,
name|Comparable
argument_list|<
name|StudentConflict
argument_list|>
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
operator|-
literal|4480647127446582658L
decl_stmt|;
specifier|private
name|ClassAssignment
name|iOtherClass
init|=
literal|null
decl_stmt|;
specifier|private
name|Set
argument_list|<
name|Long
argument_list|>
name|iConflictingStudents
init|=
literal|null
decl_stmt|;
specifier|public
name|StudentConflict
parameter_list|(
name|ClassAssignment
name|other
parameter_list|,
name|Set
argument_list|<
name|Long
argument_list|>
name|students
parameter_list|)
block|{
name|iOtherClass
operator|=
name|other
expr_stmt|;
name|iConflictingStudents
operator|=
name|students
expr_stmt|;
block|}
specifier|public
name|ClassAssignment
name|getOtherClass
parameter_list|()
block|{
return|return
name|iOtherClass
return|;
block|}
specifier|public
name|Set
argument_list|<
name|Long
argument_list|>
name|getConflictingStudents
parameter_list|()
block|{
return|return
name|iConflictingStudents
return|;
block|}
specifier|public
name|int
name|hashCode
parameter_list|()
block|{
return|return
name|getClassId
argument_list|()
operator|.
name|hashCode
argument_list|()
operator|^
name|getOtherClass
argument_list|()
operator|.
name|getClassId
argument_list|()
operator|.
name|hashCode
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
name|o
operator|==
literal|null
operator|||
operator|!
operator|(
name|o
operator|instanceof
name|StudentConflict
operator|)
condition|)
return|return
literal|false
return|;
return|return
name|getOtherClass
argument_list|()
operator|.
name|equals
argument_list|(
operator|(
operator|(
name|StudentConflict
operator|)
name|o
operator|)
operator|.
name|getOtherClass
argument_list|()
argument_list|)
return|;
block|}
specifier|public
name|int
name|compareTo
parameter_list|(
name|StudentConflict
name|c
parameter_list|)
block|{
name|int
name|cmp
init|=
name|c
operator|.
name|getConflictingStudents
argument_list|()
operator|.
name|size
argument_list|()
operator|-
name|getConflictingStudents
argument_list|()
operator|.
name|size
argument_list|()
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
return|return
name|getOtherClass
argument_list|()
operator|.
name|compareTo
argument_list|(
name|c
operator|.
name|getOtherClass
argument_list|()
argument_list|)
return|;
block|}
specifier|public
name|String
name|toHtml
parameter_list|()
block|{
name|String
name|ret
init|=
literal|""
decl_stmt|;
name|ret
operator|+=
literal|"<tr onmouseover=\"this.style.backgroundColor='rgb(223,231,242)';this.style.cursor='hand';this.style.cursor='pointer';\" onmouseout=\"this.style.backgroundColor='transparent';\" onclick=\"document.location='classInfo.do?classId="
operator|+
name|getOtherClass
argument_list|()
operator|.
name|getClassId
argument_list|()
operator|+
literal|"&op=Select&noCacheTS="
operator|+
operator|new
name|Date
argument_list|()
operator|.
name|getTime
argument_list|()
operator|+
literal|"';\">"
expr_stmt|;
name|ret
operator|+=
literal|"<td style='font-weight:bold;color:"
operator|+
name|PreferenceLevel
operator|.
name|prolog2color
argument_list|(
literal|"P"
argument_list|)
operator|+
literal|";'>"
expr_stmt|;
name|ret
operator|+=
name|String
operator|.
name|valueOf
argument_list|(
name|getConflictingStudents
argument_list|()
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
name|ret
operator|+=
literal|"<td>"
operator|+
name|getOtherClass
argument_list|()
operator|.
name|getClassNameHtml
argument_list|()
operator|+
literal|"</td>"
expr_stmt|;
name|ret
operator|+=
literal|"<td>"
operator|+
name|getOtherClass
argument_list|()
operator|.
name|getDate
argument_list|()
operator|.
name|toHtml
argument_list|()
operator|+
literal|"</td>"
expr_stmt|;
name|ret
operator|+=
literal|"<td>"
operator|+
name|getOtherClass
argument_list|()
operator|.
name|getTime
argument_list|()
operator|.
name|getLongNameHtml
argument_list|()
operator|+
literal|"</td>"
expr_stmt|;
name|ret
operator|+=
literal|"<td>"
operator|+
name|getOtherClass
argument_list|()
operator|.
name|getRoomNamesHtml
argument_list|(
literal|", "
argument_list|)
operator|+
literal|"</td>"
expr_stmt|;
name|ret
operator|+=
literal|"</tr>"
expr_stmt|;
return|return
name|ret
return|;
block|}
specifier|public
name|String
name|toHtml2
parameter_list|()
block|{
name|String
name|ret
init|=
literal|""
decl_stmt|;
name|ret
operator|+=
literal|"<tr onmouseover=\"this.style.backgroundColor='rgb(223,231,242)';this.style.cursor='hand';this.style.cursor='pointer';\" onmouseout=\"this.style.backgroundColor='transparent';\" onclick=\"document.location='classInfo.do?classId="
operator|+
name|getOtherClass
argument_list|()
operator|.
name|getClassId
argument_list|()
operator|+
literal|"&op=Select&noCacheTS="
operator|+
operator|new
name|Date
argument_list|()
operator|.
name|getTime
argument_list|()
operator|+
literal|"';\">"
expr_stmt|;
name|ret
operator|+=
literal|"<td nowrap style='font-weight:bold;color:"
operator|+
name|PreferenceLevel
operator|.
name|prolog2color
argument_list|(
literal|"P"
argument_list|)
operator|+
literal|";'>"
expr_stmt|;
name|ret
operator|+=
name|String
operator|.
name|valueOf
argument_list|(
name|getConflictingStudents
argument_list|()
operator|.
name|size
argument_list|()
argument_list|)
operator|+
literal|"<br>"
expr_stmt|;
name|ret
operator|+=
literal|"<td nowrap>"
operator|+
name|getClassNameHtml
argument_list|()
operator|+
literal|"<br>"
operator|+
name|getOtherClass
argument_list|()
operator|.
name|getClassNameHtml
argument_list|()
operator|+
literal|"</td>"
expr_stmt|;
name|ret
operator|+=
literal|"<td nowrap>"
operator|+
name|getDate
argument_list|()
operator|.
name|toHtml
argument_list|()
operator|+
literal|"<br>"
operator|+
name|getOtherClass
argument_list|()
operator|.
name|getDate
argument_list|()
operator|.
name|toHtml
argument_list|()
operator|+
literal|"</td>"
expr_stmt|;
name|ret
operator|+=
literal|"<td nowrap>"
operator|+
name|getTime
argument_list|()
operator|.
name|getLongNameHtml
argument_list|()
operator|+
literal|"<br>"
operator|+
name|getOtherClass
argument_list|()
operator|.
name|getTime
argument_list|()
operator|.
name|getLongNameHtml
argument_list|()
operator|+
literal|"</td>"
expr_stmt|;
name|ret
operator|+=
literal|"<td nowrap>"
operator|+
name|getRoomNamesHtml
argument_list|(
literal|", "
argument_list|)
operator|+
literal|"<br>"
operator|+
name|getOtherClass
argument_list|()
operator|.
name|getRoomNamesHtml
argument_list|(
literal|", "
argument_list|)
operator|+
literal|"</td>"
expr_stmt|;
name|ret
operator|+=
literal|"</tr>"
expr_stmt|;
return|return
name|ret
return|;
block|}
block|}
block|}
end_class

end_unit

