begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.2 (University Timetabling Application)  * Copyright (C) 2011, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
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
name|shared
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
name|Date
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
name|com
operator|.
name|google
operator|.
name|gwt
operator|.
name|user
operator|.
name|client
operator|.
name|rpc
operator|.
name|IsSerializable
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
specifier|abstract
class|class
name|ReservationInterface
implements|implements
name|IsSerializable
implements|,
name|Comparable
argument_list|<
name|ReservationInterface
argument_list|>
block|{
specifier|private
name|Long
name|iId
decl_stmt|;
specifier|private
name|Offering
name|iOffering
decl_stmt|;
specifier|private
name|List
argument_list|<
name|Config
argument_list|>
name|iConfigs
init|=
operator|new
name|ArrayList
argument_list|<
name|Config
argument_list|>
argument_list|()
decl_stmt|;
specifier|private
name|List
argument_list|<
name|Clazz
argument_list|>
name|iClasses
init|=
operator|new
name|ArrayList
argument_list|<
name|Clazz
argument_list|>
argument_list|()
decl_stmt|;
specifier|private
name|Integer
name|iLimit
init|=
literal|null
decl_stmt|,
name|iEnrollment
init|=
literal|null
decl_stmt|,
name|iLastLike
init|=
literal|null
decl_stmt|,
name|iProjection
init|=
literal|null
decl_stmt|;
specifier|private
name|Date
name|iExpirationDate
decl_stmt|;
specifier|private
name|boolean
name|iEditable
init|=
literal|false
decl_stmt|;
specifier|public
name|Long
name|getId
parameter_list|()
block|{
return|return
name|iId
return|;
block|}
specifier|public
name|void
name|setId
parameter_list|(
name|Long
name|id
parameter_list|)
block|{
name|iId
operator|=
name|id
expr_stmt|;
block|}
specifier|public
name|Offering
name|getOffering
parameter_list|()
block|{
return|return
name|iOffering
return|;
block|}
specifier|public
name|void
name|setOffering
parameter_list|(
name|Offering
name|offering
parameter_list|)
block|{
name|iOffering
operator|=
name|offering
expr_stmt|;
block|}
specifier|public
name|Integer
name|getLimit
parameter_list|()
block|{
return|return
name|iLimit
return|;
block|}
specifier|public
name|void
name|setLimit
parameter_list|(
name|Integer
name|limit
parameter_list|)
block|{
name|iLimit
operator|=
name|limit
expr_stmt|;
block|}
specifier|public
name|Integer
name|getLastLike
parameter_list|()
block|{
return|return
name|iLastLike
return|;
block|}
specifier|public
name|void
name|setLastLike
parameter_list|(
name|Integer
name|lastLike
parameter_list|)
block|{
name|iLastLike
operator|=
name|lastLike
expr_stmt|;
block|}
specifier|public
name|Integer
name|getEnrollment
parameter_list|()
block|{
return|return
name|iEnrollment
return|;
block|}
specifier|public
name|void
name|setEnrollment
parameter_list|(
name|Integer
name|enrollment
parameter_list|)
block|{
name|iEnrollment
operator|=
name|enrollment
expr_stmt|;
block|}
specifier|public
name|Integer
name|getProjection
parameter_list|()
block|{
return|return
name|iProjection
return|;
block|}
specifier|public
name|void
name|setProjection
parameter_list|(
name|Integer
name|projection
parameter_list|)
block|{
name|iProjection
operator|=
name|projection
expr_stmt|;
block|}
specifier|public
name|Date
name|getExpirationDate
parameter_list|()
block|{
return|return
name|iExpirationDate
return|;
block|}
specifier|public
name|void
name|setExpirationDate
parameter_list|(
name|Date
name|d
parameter_list|)
block|{
name|iExpirationDate
operator|=
name|d
expr_stmt|;
block|}
specifier|public
name|void
name|setEditable
parameter_list|(
name|boolean
name|editable
parameter_list|)
block|{
name|iEditable
operator|=
name|editable
expr_stmt|;
block|}
specifier|public
name|boolean
name|isEditable
parameter_list|()
block|{
return|return
name|iEditable
return|;
block|}
specifier|public
name|List
argument_list|<
name|Config
argument_list|>
name|getConfigs
parameter_list|()
block|{
return|return
name|iConfigs
return|;
block|}
specifier|public
name|List
argument_list|<
name|Clazz
argument_list|>
name|getClasses
parameter_list|()
block|{
return|return
name|iClasses
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
name|ReservationInterface
operator|)
condition|)
return|return
literal|false
return|;
return|return
name|getId
argument_list|()
operator|.
name|equals
argument_list|(
operator|(
operator|(
name|ReservationInterface
operator|)
name|o
operator|)
operator|.
name|getId
argument_list|()
argument_list|)
return|;
block|}
specifier|public
name|int
name|hashCode
parameter_list|()
block|{
return|return
name|getId
argument_list|()
operator|.
name|hashCode
argument_list|()
return|;
block|}
specifier|public
name|int
name|getPriority
parameter_list|()
block|{
if|if
condition|(
name|this
operator|instanceof
name|ReservationInterface
operator|.
name|IndividualReservation
condition|)
return|return
literal|0
return|;
if|if
condition|(
name|this
operator|instanceof
name|ReservationInterface
operator|.
name|GroupReservation
condition|)
return|return
literal|1
return|;
if|if
condition|(
name|this
operator|instanceof
name|ReservationInterface
operator|.
name|CourseReservation
condition|)
return|return
literal|2
return|;
if|if
condition|(
name|this
operator|instanceof
name|ReservationInterface
operator|.
name|CurriculumReservation
condition|)
return|return
literal|3
return|;
return|return
literal|4
return|;
block|}
specifier|public
name|int
name|compareTo
parameter_list|(
name|ReservationInterface
name|r2
parameter_list|)
block|{
name|int
name|cmp
init|=
name|getOffering
argument_list|()
operator|.
name|getAbbv
argument_list|()
operator|.
name|compareTo
argument_list|(
name|r2
operator|.
name|getOffering
argument_list|()
operator|.
name|getAbbv
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
operator|new
name|Integer
argument_list|(
name|getPriority
argument_list|()
argument_list|)
operator|.
name|compareTo
argument_list|(
name|r2
operator|.
name|getPriority
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
name|this
operator|.
name|toString
argument_list|()
operator|.
name|compareTo
argument_list|(
name|r2
operator|.
name|toString
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
return|return
name|this
operator|.
name|getId
argument_list|()
operator|.
name|compareTo
argument_list|(
name|r2
operator|.
name|getId
argument_list|()
argument_list|)
return|;
block|}
specifier|public
specifier|static
class|class
name|CourseReservation
extends|extends
name|ReservationInterface
block|{
specifier|private
name|Course
name|iCourse
decl_stmt|;
specifier|public
name|CourseReservation
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
block|}
specifier|public
name|Course
name|getCourse
parameter_list|()
block|{
return|return
name|iCourse
return|;
block|}
specifier|public
name|void
name|setCourse
parameter_list|(
name|Course
name|course
parameter_list|)
block|{
name|iCourse
operator|=
name|course
expr_stmt|;
block|}
specifier|public
name|Integer
name|getLimit
parameter_list|()
block|{
return|return
operator|(
name|iCourse
operator|==
literal|null
condition|?
literal|null
else|:
name|iCourse
operator|.
name|getLimit
argument_list|()
operator|)
return|;
block|}
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
name|getCourse
argument_list|()
operator|.
name|toString
argument_list|()
return|;
block|}
block|}
specifier|public
specifier|static
class|class
name|GroupReservation
extends|extends
name|ReservationInterface
block|{
specifier|private
name|IdName
name|iGroup
decl_stmt|;
specifier|public
name|GroupReservation
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
block|}
specifier|public
name|IdName
name|getGroup
parameter_list|()
block|{
return|return
name|iGroup
return|;
block|}
specifier|public
name|void
name|setGroup
parameter_list|(
name|IdName
name|group
parameter_list|)
block|{
name|iGroup
operator|=
name|group
expr_stmt|;
block|}
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
name|getGroup
argument_list|()
operator|.
name|toString
argument_list|()
return|;
block|}
block|}
specifier|public
specifier|static
class|class
name|IndividualReservation
extends|extends
name|ReservationInterface
block|{
specifier|private
name|List
argument_list|<
name|IdName
argument_list|>
name|iStudents
init|=
operator|new
name|ArrayList
argument_list|<
name|IdName
argument_list|>
argument_list|()
decl_stmt|;
specifier|public
name|IndividualReservation
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
block|}
specifier|public
name|List
argument_list|<
name|IdName
argument_list|>
name|getStudents
parameter_list|()
block|{
return|return
name|iStudents
return|;
block|}
specifier|public
name|Integer
name|getLimit
parameter_list|()
block|{
return|return
name|iStudents
operator|.
name|size
argument_list|()
return|;
block|}
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
name|getStudents
argument_list|()
operator|.
name|toString
argument_list|()
return|;
block|}
block|}
specifier|public
specifier|static
class|class
name|CurriculumReservation
extends|extends
name|ReservationInterface
block|{
specifier|private
name|Area
name|iCurriculum
decl_stmt|;
specifier|public
name|CurriculumReservation
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
block|}
specifier|public
name|Area
name|getCurriculum
parameter_list|()
block|{
return|return
name|iCurriculum
return|;
block|}
specifier|public
name|void
name|setCurriculum
parameter_list|(
name|Area
name|curriculum
parameter_list|)
block|{
name|iCurriculum
operator|=
name|curriculum
expr_stmt|;
block|}
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
name|getCurriculum
argument_list|()
operator|.
name|toString
argument_list|()
return|;
block|}
block|}
specifier|public
specifier|static
class|class
name|IdName
implements|implements
name|IsSerializable
implements|,
name|Comparable
argument_list|<
name|IdName
argument_list|>
block|{
specifier|private
name|Long
name|iId
decl_stmt|;
specifier|private
name|String
name|iAbbv
decl_stmt|;
specifier|private
name|String
name|iName
decl_stmt|;
specifier|private
name|Integer
name|iLimit
init|=
literal|null
decl_stmt|;
specifier|public
name|IdName
parameter_list|()
block|{
block|}
specifier|public
name|Long
name|getId
parameter_list|()
block|{
return|return
name|iId
return|;
block|}
specifier|public
name|void
name|setId
parameter_list|(
name|Long
name|id
parameter_list|)
block|{
name|iId
operator|=
name|id
expr_stmt|;
block|}
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
name|iName
return|;
block|}
specifier|public
name|void
name|setName
parameter_list|(
name|String
name|name
parameter_list|)
block|{
name|iName
operator|=
name|name
expr_stmt|;
block|}
specifier|public
name|String
name|getAbbv
parameter_list|()
block|{
return|return
name|iAbbv
return|;
block|}
specifier|public
name|void
name|setAbbv
parameter_list|(
name|String
name|abbv
parameter_list|)
block|{
name|iAbbv
operator|=
name|abbv
expr_stmt|;
block|}
specifier|public
name|Integer
name|getLimit
parameter_list|()
block|{
return|return
name|iLimit
return|;
block|}
specifier|public
name|void
name|setLimit
parameter_list|(
name|Integer
name|limit
parameter_list|)
block|{
name|iLimit
operator|=
name|limit
expr_stmt|;
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
name|IdName
operator|)
condition|)
return|return
literal|false
return|;
return|return
name|getId
argument_list|()
operator|.
name|equals
argument_list|(
operator|(
operator|(
name|IdName
operator|)
name|o
operator|)
operator|.
name|getId
argument_list|()
argument_list|)
return|;
block|}
specifier|public
name|int
name|hashCode
parameter_list|()
block|{
return|return
name|getId
argument_list|()
operator|.
name|hashCode
argument_list|()
return|;
block|}
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
operator|(
operator|(
name|iAbbv
operator|==
literal|null
operator|||
name|iAbbv
operator|.
name|isEmpty
argument_list|()
condition|?
literal|""
else|:
name|iAbbv
operator|)
operator|+
operator|(
name|iName
operator|==
literal|null
operator|||
name|iName
operator|.
name|isEmpty
argument_list|()
condition|?
literal|""
else|:
literal|" "
operator|+
name|iName
operator|)
operator|)
operator|.
name|trim
argument_list|()
return|;
block|}
specifier|public
name|int
name|compareTo
parameter_list|(
name|IdName
name|other
parameter_list|)
block|{
name|int
name|cmp
init|=
operator|(
name|getAbbv
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
name|getAbbv
argument_list|()
operator|)
operator|.
name|compareTo
argument_list|(
name|other
operator|.
name|getAbbv
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
name|other
operator|.
name|getAbbv
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
operator|(
name|getName
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
name|getName
argument_list|()
operator|)
operator|.
name|compareTo
argument_list|(
name|other
operator|.
name|getName
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
name|other
operator|.
name|getName
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
return|return
name|getId
argument_list|()
operator|.
name|compareTo
argument_list|(
name|other
operator|.
name|getId
argument_list|()
argument_list|)
return|;
block|}
block|}
specifier|public
specifier|static
class|class
name|Course
extends|extends
name|IdName
block|{
specifier|private
name|boolean
name|iControl
init|=
literal|true
decl_stmt|;
specifier|public
name|Course
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
block|}
specifier|public
name|boolean
name|isControl
parameter_list|()
block|{
return|return
name|iControl
return|;
block|}
specifier|public
name|void
name|setControl
parameter_list|(
name|boolean
name|control
parameter_list|)
block|{
name|iControl
operator|=
name|control
expr_stmt|;
block|}
block|}
specifier|public
specifier|static
class|class
name|Config
extends|extends
name|IdName
block|{
specifier|private
name|List
argument_list|<
name|Subpart
argument_list|>
name|iSubparts
init|=
operator|new
name|ArrayList
argument_list|<
name|Subpart
argument_list|>
argument_list|()
decl_stmt|;
specifier|public
name|Config
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
block|}
specifier|public
name|List
argument_list|<
name|Subpart
argument_list|>
name|getSubparts
parameter_list|()
block|{
return|return
name|iSubparts
return|;
block|}
block|}
specifier|public
specifier|static
class|class
name|Subpart
extends|extends
name|IdName
block|{
specifier|private
name|Long
name|iParentId
init|=
literal|null
decl_stmt|;
specifier|private
name|List
argument_list|<
name|Clazz
argument_list|>
name|iClasses
init|=
operator|new
name|ArrayList
argument_list|<
name|Clazz
argument_list|>
argument_list|()
decl_stmt|;
specifier|private
name|Config
name|iConfig
decl_stmt|;
specifier|public
name|Subpart
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
block|}
specifier|public
name|Config
name|getConfig
parameter_list|()
block|{
return|return
name|iConfig
return|;
block|}
specifier|public
name|void
name|setConfig
parameter_list|(
name|Config
name|config
parameter_list|)
block|{
name|iConfig
operator|=
name|config
expr_stmt|;
block|}
specifier|public
name|List
argument_list|<
name|Clazz
argument_list|>
name|getClasses
parameter_list|()
block|{
return|return
name|iClasses
return|;
block|}
specifier|public
name|Long
name|getParentId
parameter_list|()
block|{
return|return
name|iParentId
return|;
block|}
specifier|public
name|void
name|setParentId
parameter_list|(
name|Long
name|parentId
parameter_list|)
block|{
name|iParentId
operator|=
name|parentId
expr_stmt|;
block|}
block|}
specifier|public
specifier|static
class|class
name|Clazz
extends|extends
name|IdName
block|{
specifier|private
name|Subpart
name|iSubpart
init|=
literal|null
decl_stmt|;
specifier|private
name|Long
name|iParentId
init|=
literal|null
decl_stmt|;
specifier|public
name|Clazz
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
block|}
specifier|public
name|Long
name|getParentId
parameter_list|()
block|{
return|return
name|iParentId
return|;
block|}
specifier|public
name|void
name|setParentId
parameter_list|(
name|Long
name|parentId
parameter_list|)
block|{
name|iParentId
operator|=
name|parentId
expr_stmt|;
block|}
specifier|public
name|Subpart
name|getSubpart
parameter_list|()
block|{
return|return
name|iSubpart
return|;
block|}
specifier|public
name|void
name|setSubpart
parameter_list|(
name|Subpart
name|subpart
parameter_list|)
block|{
name|iSubpart
operator|=
name|subpart
expr_stmt|;
block|}
block|}
specifier|public
specifier|static
class|class
name|Offering
extends|extends
name|IdName
block|{
specifier|private
name|List
argument_list|<
name|Course
argument_list|>
name|iCourses
init|=
operator|new
name|ArrayList
argument_list|<
name|Course
argument_list|>
argument_list|()
decl_stmt|;
specifier|private
name|List
argument_list|<
name|Config
argument_list|>
name|iConfigs
init|=
operator|new
name|ArrayList
argument_list|<
name|Config
argument_list|>
argument_list|()
decl_stmt|;
specifier|public
name|Offering
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
block|}
specifier|public
name|List
argument_list|<
name|Course
argument_list|>
name|getCourses
parameter_list|()
block|{
return|return
name|iCourses
return|;
block|}
specifier|public
name|List
argument_list|<
name|Config
argument_list|>
name|getConfigs
parameter_list|()
block|{
return|return
name|iConfigs
return|;
block|}
block|}
specifier|public
specifier|static
class|class
name|Area
extends|extends
name|IdName
block|{
specifier|private
name|List
argument_list|<
name|IdName
argument_list|>
name|iClassifications
init|=
operator|new
name|ArrayList
argument_list|<
name|IdName
argument_list|>
argument_list|()
decl_stmt|;
specifier|private
name|List
argument_list|<
name|IdName
argument_list|>
name|iMajors
init|=
operator|new
name|ArrayList
argument_list|<
name|IdName
argument_list|>
argument_list|()
decl_stmt|;
specifier|public
name|Area
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
block|}
specifier|public
name|List
argument_list|<
name|IdName
argument_list|>
name|getClassifications
parameter_list|()
block|{
return|return
name|iClassifications
return|;
block|}
specifier|public
name|List
argument_list|<
name|IdName
argument_list|>
name|getMajors
parameter_list|()
block|{
return|return
name|iMajors
return|;
block|}
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
name|super
operator|.
name|toString
argument_list|()
operator|+
literal|" "
operator|+
name|getClassifications
argument_list|()
operator|.
name|toString
argument_list|()
operator|+
literal|" "
operator|+
name|getMajors
argument_list|()
operator|.
name|toString
argument_list|()
return|;
block|}
block|}
specifier|public
specifier|static
class|class
name|Curriculum
extends|extends
name|IdName
block|{
specifier|private
name|List
argument_list|<
name|IdName
argument_list|>
name|iClassifications
init|=
operator|new
name|ArrayList
argument_list|<
name|IdName
argument_list|>
argument_list|()
decl_stmt|;
specifier|private
name|List
argument_list|<
name|IdName
argument_list|>
name|iMajors
init|=
operator|new
name|ArrayList
argument_list|<
name|IdName
argument_list|>
argument_list|()
decl_stmt|;
specifier|private
name|IdName
name|iArea
init|=
literal|null
decl_stmt|;
specifier|private
name|Integer
name|iLimit
init|=
literal|null
decl_stmt|;
specifier|public
name|Curriculum
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
block|}
specifier|public
name|List
argument_list|<
name|IdName
argument_list|>
name|getClassifications
parameter_list|()
block|{
return|return
name|iClassifications
return|;
block|}
specifier|public
name|List
argument_list|<
name|IdName
argument_list|>
name|getMajors
parameter_list|()
block|{
return|return
name|iMajors
return|;
block|}
specifier|public
name|IdName
name|getArea
parameter_list|()
block|{
return|return
name|iArea
return|;
block|}
specifier|public
name|void
name|setArea
parameter_list|(
name|IdName
name|area
parameter_list|)
block|{
name|iArea
operator|=
name|area
expr_stmt|;
block|}
specifier|public
name|Integer
name|getLimit
parameter_list|()
block|{
return|return
name|iLimit
return|;
block|}
specifier|public
name|void
name|setLimit
parameter_list|(
name|Integer
name|limit
parameter_list|)
block|{
name|iLimit
operator|=
name|limit
expr_stmt|;
block|}
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
name|super
operator|.
name|toString
argument_list|()
operator|+
literal|" "
operator|+
name|getArea
argument_list|()
operator|.
name|toString
argument_list|()
operator|+
literal|" "
operator|+
name|getClassifications
argument_list|()
operator|.
name|toString
argument_list|()
operator|+
literal|" "
operator|+
name|getMajors
argument_list|()
operator|.
name|toString
argument_list|()
return|;
block|}
block|}
block|}
end_class

end_unit

