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
name|ArrayList
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
name|HttpSession
import|;
end_import

begin_import
import|import
name|org
operator|.
name|hibernate
operator|.
name|NonUniqueResultException
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
name|hibernate
operator|.
name|criterion
operator|.
name|Restrictions
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
name|timetable
operator|.
name|model
operator|.
name|base
operator|.
name|BaseDepartmentalInstructor
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
name|DepartmentalInstructorDAO
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

begin_class
specifier|public
class|class
name|DepartmentalInstructor
extends|extends
name|BaseDepartmentalInstructor
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
comment|/*[CONSTRUCTOR MARKER BEGIN]*/
specifier|public
name|DepartmentalInstructor
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
block|}
comment|/** 	 * Constructor for primary key 	 */
specifier|public
name|DepartmentalInstructor
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
comment|/*[CONSTRUCTOR MARKER END]*/
comment|/// Copied from Instructor& InstructorDept
comment|/** Request attribute name for available instructors **/
specifier|public
specifier|static
name|String
name|INSTR_LIST_ATTR_NAME
init|=
literal|"instructorsList"
decl_stmt|;
specifier|public
specifier|static
name|String
name|INSTR_HAS_PREF_ATTR_NAME
init|=
literal|"instructorsHasPrefs"
decl_stmt|;
comment|/** Request attribute name for instructor departments  **/
specifier|public
specifier|static
name|String
name|INSTRDEPT_LIST_ATTR_NAME
init|=
literal|"instructorDeptList"
decl_stmt|;
comment|/** Instructor List **/
specifier|private
specifier|static
name|Vector
name|instructorDeptList
init|=
literal|null
decl_stmt|;
comment|/** Name Format */
specifier|public
specifier|static
specifier|final
name|String
name|sNameFormatLastFist
init|=
literal|"last-first"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|sNameFormatFirstLast
init|=
literal|"first-last"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|sNameFormatInitialLast
init|=
literal|"initial-last"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|sNameFormatLastInitial
init|=
literal|"last-initial"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|sNameFormatFirstMiddleLast
init|=
literal|"first-middle-last"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|sNameFormatShort
init|=
literal|"short"
decl_stmt|;
comment|/** 	 *  	 * @return 	 */
specifier|public
name|String
name|nameLastNameFirst
parameter_list|()
block|{
return|return
operator|(
operator|(
name|getLastName
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
name|getLastName
argument_list|()
operator|.
name|trim
argument_list|()
operator|)
operator|+
literal|", "
operator|+
operator|(
name|getFirstName
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
name|getFirstName
argument_list|()
operator|.
name|trim
argument_list|()
operator|)
operator|+
literal|" "
operator|+
operator|(
name|getMiddleName
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
name|getMiddleName
argument_list|()
operator|.
name|trim
argument_list|()
operator|)
operator|)
return|;
block|}
comment|/** 	 *  	 * @return 	 */
specifier|public
name|String
name|nameFirstNameFirst
parameter_list|()
block|{
name|StringBuffer
name|sb
init|=
operator|new
name|StringBuffer
argument_list|()
decl_stmt|;
if|if
condition|(
name|this
operator|.
name|getFirstName
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
name|this
operator|.
name|getFirstName
argument_list|()
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|this
operator|.
name|getMiddleName
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
literal|" "
operator|+
name|this
operator|.
name|getMiddleName
argument_list|()
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|this
operator|.
name|getLastName
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
literal|" "
operator|+
name|this
operator|.
name|getLastName
argument_list|()
argument_list|)
expr_stmt|;
block|}
return|return
operator|(
name|sb
operator|.
name|toString
argument_list|()
operator|)
return|;
block|}
specifier|public
name|String
name|nameShort
parameter_list|()
block|{
name|StringBuffer
name|sb
init|=
operator|new
name|StringBuffer
argument_list|()
decl_stmt|;
if|if
condition|(
name|getFirstName
argument_list|()
operator|!=
literal|null
operator|&&
name|getFirstName
argument_list|()
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
name|getFirstName
argument_list|()
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
literal|1
argument_list|)
operator|.
name|toUpperCase
argument_list|()
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|". "
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|getLastName
argument_list|()
operator|!=
literal|null
operator|&&
name|getLastName
argument_list|()
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
name|getLastName
argument_list|()
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
literal|1
argument_list|)
operator|.
name|toUpperCase
argument_list|()
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|getLastName
argument_list|()
operator|.
name|substring
argument_list|(
literal|1
argument_list|,
name|Math
operator|.
name|min
argument_list|(
literal|10
argument_list|,
name|getLastName
argument_list|()
operator|.
name|length
argument_list|()
argument_list|)
argument_list|)
operator|.
name|toLowerCase
argument_list|()
operator|.
name|trim
argument_list|()
argument_list|)
expr_stmt|;
block|}
return|return
name|sb
operator|.
name|toString
argument_list|()
return|;
block|}
comment|/** 	 * Property nameFirst used in JSPs 	 * Gets full name with first name first  	 * @return 	 */
specifier|public
name|String
name|getNameFirst
parameter_list|()
block|{
return|return
name|nameFirstNameFirst
argument_list|()
return|;
block|}
comment|/** 	 * Property nameLast used in JSPs 	 * Gets full name with last name first  	 * @return 	 */
specifier|public
name|String
name|getNameLast
parameter_list|()
block|{
return|return
name|nameLastNameFirst
argument_list|()
return|;
block|}
comment|/** 	 *  	 * @return 	 */
specifier|public
name|String
name|getNameLastFirst
parameter_list|()
block|{
return|return
name|nameLastFirst
argument_list|()
return|;
block|}
comment|/** 	 *  	 * @return 	 */
specifier|public
name|String
name|getNameInitLast
parameter_list|()
block|{
return|return
name|nameInitLast
argument_list|()
return|;
block|}
comment|/** 	 *  	 * @return 	 */
specifier|private
name|String
name|nameInitLast
parameter_list|()
block|{
return|return
operator|(
operator|(
name|this
operator|.
name|getFirstName
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
name|this
operator|.
name|getFirstName
argument_list|()
operator|.
name|trim
argument_list|()
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
literal|1
argument_list|)
operator|.
name|toUpperCase
argument_list|()
operator|)
operator|+
operator|(
name|this
operator|.
name|getMiddleName
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
literal|" "
operator|+
name|this
operator|.
name|getMiddleName
argument_list|()
operator|.
name|trim
argument_list|()
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
literal|1
argument_list|)
operator|.
name|toUpperCase
argument_list|()
operator|)
operator|+
literal|" "
operator|+
operator|(
name|Constants
operator|.
name|toInitialCase
argument_list|(
name|this
operator|.
name|getLastName
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
name|this
operator|.
name|getLastName
argument_list|()
operator|.
name|trim
argument_list|()
argument_list|)
operator|)
operator|)
return|;
block|}
comment|/** 	 *  	 * @return 	 */
specifier|private
name|String
name|nameLastInit
parameter_list|()
block|{
return|return
operator|(
name|Constants
operator|.
name|toInitialCase
argument_list|(
name|this
operator|.
name|getLastName
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
name|this
operator|.
name|getLastName
argument_list|()
operator|.
name|trim
argument_list|()
argument_list|)
operator|+
literal|", "
operator|+
operator|(
name|this
operator|.
name|getFirstName
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
name|this
operator|.
name|getFirstName
argument_list|()
operator|.
name|trim
argument_list|()
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
literal|1
argument_list|)
operator|.
name|toUpperCase
argument_list|()
operator|)
operator|+
operator|(
name|this
operator|.
name|getMiddleName
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
literal|" "
operator|+
name|this
operator|.
name|getMiddleName
argument_list|()
operator|.
name|trim
argument_list|()
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
literal|1
argument_list|)
operator|.
name|toUpperCase
argument_list|()
operator|)
operator|)
return|;
block|}
comment|/** 	 *  	 * @return 	 */
specifier|private
name|String
name|nameLastFirst
parameter_list|()
block|{
return|return
operator|(
name|Constants
operator|.
name|toInitialCase
argument_list|(
operator|(
name|this
operator|.
name|getLastName
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
name|this
operator|.
name|getLastName
argument_list|()
operator|.
name|trim
argument_list|()
operator|)
operator|+
literal|", "
operator|+
operator|(
name|this
operator|.
name|getFirstName
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
name|this
operator|.
name|getFirstName
argument_list|()
operator|.
name|trim
argument_list|()
operator|)
argument_list|)
operator|)
return|;
block|}
specifier|public
name|String
name|nameFirstLast
parameter_list|()
block|{
return|return
operator|(
name|Constants
operator|.
name|toInitialCase
argument_list|(
operator|(
name|this
operator|.
name|getFirstName
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
name|this
operator|.
name|getFirstName
argument_list|()
operator|.
name|trim
argument_list|()
operator|)
operator|+
literal|" "
operator|+
operator|(
name|this
operator|.
name|getLastName
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
name|this
operator|.
name|getLastName
argument_list|()
operator|.
name|trim
argument_list|()
operator|)
argument_list|)
operator|)
return|;
block|}
comment|/** 	 *  	 * @param user 	 * @return 	 */
specifier|public
name|String
name|getName
parameter_list|(
name|User
name|user
parameter_list|)
block|{
return|return
name|getName
argument_list|(
name|Settings
operator|.
name|getSettingValue
argument_list|(
name|user
argument_list|,
name|Constants
operator|.
name|SETTINGS_INSTRUCTOR_NAME_FORMAT
argument_list|)
argument_list|)
return|;
block|}
specifier|public
name|String
name|getName
parameter_list|(
name|String
name|instructorNameFormat
parameter_list|)
block|{
if|if
condition|(
name|sNameFormatLastFist
operator|.
name|equals
argument_list|(
name|instructorNameFormat
argument_list|)
condition|)
return|return
name|getNameLastFirst
argument_list|()
return|;
if|if
condition|(
name|sNameFormatFirstLast
operator|.
name|equals
argument_list|(
name|instructorNameFormat
argument_list|)
condition|)
return|return
name|nameFirstLast
argument_list|()
return|;
if|if
condition|(
name|sNameFormatInitialLast
operator|.
name|equals
argument_list|(
name|instructorNameFormat
argument_list|)
condition|)
return|return
name|getNameInitLast
argument_list|()
return|;
if|if
condition|(
name|sNameFormatLastInitial
operator|.
name|equals
argument_list|(
name|instructorNameFormat
argument_list|)
condition|)
return|return
name|nameLastInit
argument_list|()
return|;
if|if
condition|(
name|sNameFormatFirstMiddleLast
operator|.
name|equals
argument_list|(
name|instructorNameFormat
argument_list|)
condition|)
return|return
name|nameFirstMiddleLast
argument_list|()
return|;
if|if
condition|(
name|sNameFormatShort
operator|.
name|equals
argument_list|(
name|instructorNameFormat
argument_list|)
condition|)
return|return
name|nameShort
argument_list|()
return|;
return|return
name|nameFirstMiddleLast
argument_list|()
return|;
block|}
comment|/** 	 *  	 * @return 	 */
specifier|private
name|String
name|nameFirstMiddleLast
parameter_list|()
block|{
return|return
operator|(
name|Constants
operator|.
name|toInitialCase
argument_list|(
operator|(
name|this
operator|.
name|getFirstName
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
name|this
operator|.
name|getFirstName
argument_list|()
operator|.
name|trim
argument_list|()
operator|)
operator|+
literal|" "
operator|+
operator|(
name|this
operator|.
name|getMiddleName
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
name|this
operator|.
name|getMiddleName
argument_list|()
operator|.
name|trim
argument_list|()
operator|)
operator|+
literal|" "
operator|+
operator|(
name|this
operator|.
name|getLastName
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
name|this
operator|.
name|getLastName
argument_list|()
operator|.
name|trim
argument_list|()
operator|)
argument_list|)
operator|)
return|;
block|}
comment|/** 	 * Remove class from instructor list 	 * @param ci 	 */
specifier|public
name|void
name|removeClassInstructor
parameter_list|(
name|ClassInstructor
name|classInstr
parameter_list|)
block|{
name|Set
name|s
init|=
name|this
operator|.
name|getClasses
argument_list|()
decl_stmt|;
for|for
control|(
name|Iterator
name|iter
init|=
name|s
operator|.
name|iterator
argument_list|()
init|;
name|iter
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|ClassInstructor
name|ci
init|=
operator|(
name|ClassInstructor
operator|)
name|iter
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|ci
operator|.
name|getUniqueId
argument_list|()
operator|.
name|intValue
argument_list|()
operator|==
name|classInstr
operator|.
name|getUniqueId
argument_list|()
operator|.
name|intValue
argument_list|()
condition|)
block|{
name|s
operator|.
name|remove
argument_list|(
name|ci
argument_list|)
expr_stmt|;
break|break;
block|}
block|}
block|}
specifier|protected
name|boolean
name|canUserEdit
parameter_list|(
name|User
name|user
parameter_list|)
block|{
return|return
name|getDepartment
argument_list|()
operator|.
name|canUserEdit
argument_list|(
name|user
argument_list|)
return|;
block|}
specifier|protected
name|boolean
name|canUserView
parameter_list|(
name|User
name|user
parameter_list|)
block|{
return|return
name|getDepartment
argument_list|()
operator|.
name|canUserView
argument_list|(
name|user
argument_list|)
return|;
block|}
specifier|public
name|String
name|htmlLabel
parameter_list|()
block|{
return|return
operator|(
name|this
operator|.
name|nameFirstNameFirst
argument_list|()
operator|+
literal|", "
operator|+
name|this
operator|.
name|getDepartment
argument_list|()
operator|.
name|getDeptCode
argument_list|()
operator|)
return|;
block|}
specifier|public
name|Set
name|getAvailableRooms
parameter_list|()
block|{
name|Set
name|rooms
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
name|getDepartment
argument_list|()
operator|.
name|getRoomDepts
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
name|RoomDept
name|roomDept
init|=
operator|(
name|RoomDept
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|rooms
operator|.
name|add
argument_list|(
name|roomDept
operator|.
name|getRoom
argument_list|()
argument_list|)
expr_stmt|;
block|}
return|return
name|rooms
return|;
block|}
specifier|public
name|Set
name|getAvailableRoomFeatures
parameter_list|()
block|{
name|Set
name|features
init|=
name|super
operator|.
name|getAvailableRoomFeatures
argument_list|()
decl_stmt|;
name|features
operator|.
name|addAll
argument_list|(
operator|(
name|DepartmentRoomFeature
operator|.
name|getAllDepartmentRoomFeatures
argument_list|(
name|getDepartment
argument_list|()
argument_list|)
operator|)
argument_list|)
expr_stmt|;
return|return
name|features
return|;
block|}
specifier|public
name|Set
name|getAvailableRoomGroups
parameter_list|()
block|{
name|Set
name|groups
init|=
name|super
operator|.
name|getAvailableRoomGroups
argument_list|()
decl_stmt|;
name|groups
operator|.
name|addAll
argument_list|(
name|RoomGroup
operator|.
name|getAllDepartmentRoomGroups
argument_list|(
name|getDepartment
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|groups
return|;
block|}
specifier|public
name|Set
name|prefsOfTypeForDepartment
parameter_list|(
name|Class
name|type
parameter_list|,
name|Department
name|dept
parameter_list|)
block|{
if|if
condition|(
name|dept
operator|==
literal|null
operator|||
name|dept
operator|.
name|equals
argument_list|(
name|getDepartment
argument_list|()
argument_list|)
condition|)
return|return
name|getPreferences
argument_list|(
name|type
argument_list|)
return|;
else|else
return|return
literal|null
return|;
block|}
comment|/** 	 *  	 * @param sessionId 	 * @param di 	 * @return 	 */
specifier|public
specifier|static
name|List
name|getAllForInstructor
parameter_list|(
name|DepartmentalInstructor
name|di
parameter_list|,
name|Long
name|sessionId
parameter_list|)
block|{
if|if
condition|(
name|di
operator|==
literal|null
operator|||
name|di
operator|.
name|getExternalUniqueId
argument_list|()
operator|==
literal|null
operator|||
name|di
operator|.
name|getExternalUniqueId
argument_list|()
operator|.
name|trim
argument_list|()
operator|.
name|length
argument_list|()
operator|==
literal|0
condition|)
block|{
name|ArrayList
name|list1
init|=
operator|new
name|ArrayList
argument_list|()
decl_stmt|;
name|list1
operator|.
name|add
argument_list|(
name|di
argument_list|)
expr_stmt|;
return|return
operator|(
name|list1
operator|)
return|;
block|}
name|DepartmentalInstructorDAO
name|ddao
init|=
operator|new
name|DepartmentalInstructorDAO
argument_list|()
decl_stmt|;
name|String
name|query
init|=
literal|"from DepartmentalInstructor where externalUniqueId=:puid and department.session.uniqueId=:sessionId"
decl_stmt|;
name|Query
name|q
init|=
name|ddao
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
name|query
argument_list|)
decl_stmt|;
name|q
operator|.
name|setString
argument_list|(
literal|"puid"
argument_list|,
name|di
operator|.
name|getExternalUniqueId
argument_list|()
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
name|List
name|list
init|=
name|q
operator|.
name|list
argument_list|()
decl_stmt|;
if|if
condition|(
name|list
operator|==
literal|null
condition|)
block|{
name|list
operator|=
operator|new
name|ArrayList
argument_list|()
expr_stmt|;
name|list
operator|.
name|add
argument_list|(
name|di
argument_list|)
expr_stmt|;
block|}
return|return
name|list
return|;
block|}
comment|/** 	 *  	 * @param deptCode 	 * @return 	 */
specifier|public
specifier|static
name|List
name|getInstructorByDept
parameter_list|(
name|Long
name|sessionId
parameter_list|,
name|Long
name|deptId
parameter_list|)
throws|throws
name|Exception
block|{
name|StringBuffer
name|query
init|=
operator|new
name|StringBuffer
argument_list|()
decl_stmt|;
name|query
operator|.
name|append
argument_list|(
literal|"select distinct i "
argument_list|)
expr_stmt|;
name|query
operator|.
name|append
argument_list|(
literal|"  from DepartmentalInstructor i "
argument_list|)
expr_stmt|;
name|query
operator|.
name|append
argument_list|(
literal|" where i.department.session.uniqueId = :acadSessionId "
argument_list|)
expr_stmt|;
if|if
condition|(
name|deptId
operator|!=
literal|null
condition|)
name|query
operator|.
name|append
argument_list|(
literal|" and i.department.uniqueId = :deptId"
argument_list|)
expr_stmt|;
name|query
operator|.
name|append
argument_list|(
literal|" order by i.lastName "
argument_list|)
expr_stmt|;
name|DepartmentalInstructorDAO
name|idao
init|=
operator|new
name|DepartmentalInstructorDAO
argument_list|()
decl_stmt|;
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
init|=
name|idao
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
operator|.
name|toString
argument_list|()
argument_list|)
decl_stmt|;
name|q
operator|.
name|setFetchSize
argument_list|(
literal|5000
argument_list|)
expr_stmt|;
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
literal|"acadSessionId"
argument_list|,
name|sessionId
operator|.
name|longValue
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|deptId
operator|!=
literal|null
condition|)
name|q
operator|.
name|setLong
argument_list|(
literal|"deptId"
argument_list|,
name|deptId
operator|.
name|longValue
argument_list|()
argument_list|)
expr_stmt|;
name|List
name|result
init|=
name|q
operator|.
name|list
argument_list|()
decl_stmt|;
return|return
name|result
return|;
block|}
comment|/** 	 *  	 * @param o 	 * @return 	 */
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
name|o
operator|==
literal|null
operator|||
operator|!
operator|(
name|o
operator|instanceof
name|DepartmentalInstructor
operator|)
condition|)
return|return
operator|-
literal|1
return|;
name|DepartmentalInstructor
name|i
init|=
operator|(
name|DepartmentalInstructor
operator|)
name|o
decl_stmt|;
name|int
name|cmp
init|=
name|nameLastNameFirst
argument_list|()
operator|.
name|compareToIgnoreCase
argument_list|(
name|i
operator|.
name|nameLastNameFirst
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
name|getUniqueId
argument_list|()
operator|.
name|compareTo
argument_list|(
name|i
operator|.
name|getUniqueId
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
name|Department
name|deptO
init|=
name|getDepartment
argument_list|()
decl_stmt|;
name|Department
name|deptI
init|=
name|i
operator|.
name|getDepartment
argument_list|()
decl_stmt|;
return|return
name|deptO
operator|.
name|getAbbreviation
argument_list|()
operator|.
name|compareTo
argument_list|(
name|deptI
operator|.
name|getAbbreviation
argument_list|()
argument_list|)
return|;
block|}
comment|/** 	 *  	 * @param puid 	 * @return 	 */
specifier|public
specifier|static
name|boolean
name|existInst
parameter_list|(
name|String
name|puid
parameter_list|)
block|{
if|if
condition|(
name|puid
operator|==
literal|null
condition|)
block|{
return|return
operator|(
literal|false
operator|)
return|;
block|}
name|DepartmentalInstructorDAO
name|ddao
init|=
operator|new
name|DepartmentalInstructorDAO
argument_list|()
decl_stmt|;
name|List
name|list
init|=
name|ddao
operator|.
name|getSession
argument_list|()
operator|.
name|createCriteria
argument_list|(
name|DepartmentalInstructor
operator|.
name|class
argument_list|)
operator|.
name|add
argument_list|(
name|Restrictions
operator|.
name|eq
argument_list|(
literal|"externalUniqueId"
argument_list|,
name|puid
argument_list|)
argument_list|)
operator|.
name|list
argument_list|()
decl_stmt|;
if|if
condition|(
name|list
operator|.
name|size
argument_list|()
operator|!=
literal|0
condition|)
block|{
return|return
literal|true
return|;
block|}
else|else
block|{
return|return
literal|false
return|;
block|}
block|}
specifier|public
specifier|static
name|DepartmentalInstructor
name|findByPuidDepartmentId
parameter_list|(
name|String
name|puid
parameter_list|,
name|Long
name|deptId
parameter_list|)
block|{
try|try
block|{
return|return
operator|(
name|DepartmentalInstructor
operator|)
operator|(
operator|new
name|DepartmentalInstructorDAO
argument_list|()
operator|)
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select d from DepartmentalInstructor d where d.externalUniqueId=:puid and d.department.uniqueId=:deptId"
argument_list|)
operator|.
name|setString
argument_list|(
literal|"puid"
argument_list|,
name|puid
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"deptId"
argument_list|,
name|deptId
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
name|uniqueResult
argument_list|()
return|;
block|}
catch|catch
parameter_list|(
name|NonUniqueResultException
name|e
parameter_list|)
block|{
name|Debug
operator|.
name|warning
argument_list|(
literal|"There are two or more instructors with puid "
operator|+
name|puid
operator|+
literal|" for department "
operator|+
name|deptId
operator|+
literal|" -- returning the first one."
argument_list|)
expr_stmt|;
return|return
operator|(
name|DepartmentalInstructor
operator|)
operator|(
operator|new
name|DepartmentalInstructorDAO
argument_list|()
operator|)
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select d from DepartmentalInstructor d where d.externalUniqueId=:puid and d.department.uniqueId=:deptId"
argument_list|)
operator|.
name|setString
argument_list|(
literal|"puid"
argument_list|,
name|puid
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"deptId"
argument_list|,
name|deptId
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
operator|.
name|get
argument_list|(
literal|0
argument_list|)
return|;
block|}
block|}
specifier|public
name|DepartmentalInstructor
name|findThisInstructorInSession
parameter_list|(
name|Long
name|sessionId
parameter_list|)
block|{
name|Department
name|newDept
init|=
name|this
operator|.
name|getDepartment
argument_list|()
operator|.
name|findSameDepartmentInSession
argument_list|(
name|sessionId
argument_list|)
decl_stmt|;
if|if
condition|(
name|newDept
operator|!=
literal|null
condition|)
block|{
return|return
operator|(
name|findByPuidDepartmentId
argument_list|(
name|this
operator|.
name|getExternalUniqueId
argument_list|()
argument_list|,
name|newDept
operator|.
name|getUniqueId
argument_list|()
argument_list|)
operator|)
return|;
block|}
return|return
operator|(
literal|null
operator|)
return|;
block|}
specifier|public
name|DepartmentalInstructor
name|getNextDepartmentalInstructor
parameter_list|(
name|HttpSession
name|session
parameter_list|,
name|User
name|user
parameter_list|,
name|boolean
name|canEdit
parameter_list|,
name|boolean
name|canView
parameter_list|)
throws|throws
name|Exception
block|{
name|List
name|instructors
init|=
name|DepartmentalInstructor
operator|.
name|getInstructorByDept
argument_list|(
name|getDepartment
argument_list|()
operator|.
name|getSessionId
argument_list|()
argument_list|,
name|getDepartment
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|)
decl_stmt|;
name|DepartmentalInstructor
name|next
init|=
literal|null
decl_stmt|;
for|for
control|(
name|Iterator
name|i
init|=
name|instructors
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
name|DepartmentalInstructor
name|di
init|=
operator|(
name|DepartmentalInstructor
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|canEdit
operator|&&
operator|!
name|di
operator|.
name|isEditableBy
argument_list|(
name|user
argument_list|)
condition|)
continue|continue;
if|if
condition|(
name|canView
operator|&&
operator|!
name|di
operator|.
name|isViewableBy
argument_list|(
name|user
argument_list|)
condition|)
continue|continue;
if|if
condition|(
name|this
operator|.
name|compareTo
argument_list|(
name|di
argument_list|)
operator|>=
literal|0
condition|)
continue|continue;
if|if
condition|(
name|next
operator|==
literal|null
operator|||
name|next
operator|.
name|compareTo
argument_list|(
name|di
argument_list|)
operator|>
literal|0
condition|)
name|next
operator|=
name|di
expr_stmt|;
block|}
return|return
name|next
return|;
block|}
specifier|public
name|DepartmentalInstructor
name|getPreviousDepartmentalInstructor
parameter_list|(
name|HttpSession
name|session
parameter_list|,
name|User
name|user
parameter_list|,
name|boolean
name|canEdit
parameter_list|,
name|boolean
name|canView
parameter_list|)
throws|throws
name|Exception
block|{
name|List
name|instructors
init|=
name|DepartmentalInstructor
operator|.
name|getInstructorByDept
argument_list|(
name|getDepartment
argument_list|()
operator|.
name|getSessionId
argument_list|()
argument_list|,
name|getDepartment
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|)
decl_stmt|;
name|DepartmentalInstructor
name|prev
init|=
literal|null
decl_stmt|;
for|for
control|(
name|Iterator
name|i
init|=
name|instructors
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
name|DepartmentalInstructor
name|di
init|=
operator|(
name|DepartmentalInstructor
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|canEdit
operator|&&
operator|!
name|di
operator|.
name|isEditableBy
argument_list|(
name|user
argument_list|)
condition|)
continue|continue;
if|if
condition|(
name|canView
operator|&&
operator|!
name|di
operator|.
name|isViewableBy
argument_list|(
name|user
argument_list|)
condition|)
continue|continue;
if|if
condition|(
name|this
operator|.
name|compareTo
argument_list|(
name|di
argument_list|)
operator|<=
literal|0
condition|)
continue|continue;
if|if
condition|(
name|prev
operator|==
literal|null
operator|||
name|prev
operator|.
name|compareTo
argument_list|(
name|di
argument_list|)
operator|<
literal|0
condition|)
name|prev
operator|=
name|di
expr_stmt|;
block|}
return|return
name|prev
return|;
block|}
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
name|nameShort
argument_list|()
return|;
block|}
specifier|public
name|boolean
name|hasPreferences
parameter_list|()
block|{
if|if
condition|(
name|getPreferences
argument_list|()
operator|==
literal|null
operator|||
name|getPreferences
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
return|return
literal|false
return|;
for|for
control|(
name|Iterator
name|i
init|=
name|getPreferences
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
name|Preference
name|preference
init|=
operator|(
name|Preference
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|preference
operator|instanceof
name|TimePref
condition|)
block|{
name|TimePref
name|timePref
init|=
operator|(
name|TimePref
operator|)
name|preference
decl_stmt|;
if|if
condition|(
name|timePref
operator|.
name|getTimePatternModel
argument_list|()
operator|.
name|isDefault
argument_list|()
condition|)
continue|continue;
block|}
return|return
literal|true
return|;
block|}
return|return
literal|false
return|;
block|}
specifier|public
name|Object
name|clone
parameter_list|()
block|{
name|DepartmentalInstructor
name|newDepartmentalInstructor
init|=
operator|new
name|DepartmentalInstructor
argument_list|()
decl_stmt|;
name|newDepartmentalInstructor
operator|.
name|setCareerAcct
argument_list|(
name|getCareerAcct
argument_list|()
argument_list|)
expr_stmt|;
name|newDepartmentalInstructor
operator|.
name|setDepartment
argument_list|(
name|getDepartment
argument_list|()
argument_list|)
expr_stmt|;
name|newDepartmentalInstructor
operator|.
name|setExternalUniqueId
argument_list|(
name|getExternalUniqueId
argument_list|()
argument_list|)
expr_stmt|;
name|newDepartmentalInstructor
operator|.
name|setFirstName
argument_list|(
name|getFirstName
argument_list|()
argument_list|)
expr_stmt|;
name|newDepartmentalInstructor
operator|.
name|setMiddleName
argument_list|(
name|getMiddleName
argument_list|()
argument_list|)
expr_stmt|;
name|newDepartmentalInstructor
operator|.
name|setLastName
argument_list|(
name|getLastName
argument_list|()
argument_list|)
expr_stmt|;
name|newDepartmentalInstructor
operator|.
name|setIgnoreToFar
argument_list|(
name|isIgnoreToFar
argument_list|()
argument_list|)
expr_stmt|;
name|newDepartmentalInstructor
operator|.
name|setNote
argument_list|(
name|getNote
argument_list|()
argument_list|)
expr_stmt|;
name|newDepartmentalInstructor
operator|.
name|setPositionType
argument_list|(
name|getPositionType
argument_list|()
argument_list|)
expr_stmt|;
return|return
operator|(
name|newDepartmentalInstructor
operator|)
return|;
block|}
block|}
end_class

end_unit

