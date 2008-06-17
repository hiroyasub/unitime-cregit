begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.1 (University Timetabling Application)  * Copyright (C) 2008, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 2 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program; if not, write to the Free Software Foundation, Inc.,  * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
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
operator|.
name|base
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

begin_comment
comment|/**  * This is an object that contains data related to the COURSE_DEMAND table.  * Do not modify this class because it will be overwritten if the configuration file  * related to this class is modified.  *  * @hibernate.class  *  table="COURSE_DEMAND"  */
end_comment

begin_class
specifier|public
specifier|abstract
class|class
name|BaseCourseDemand
implements|implements
name|Serializable
block|{
specifier|public
specifier|static
name|String
name|REF
init|=
literal|"CourseDemand"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_PRIORITY
init|=
literal|"priority"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_WAITLIST
init|=
literal|"waitlist"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_ALTERNATIVE
init|=
literal|"alternative"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_TIMESTAMP
init|=
literal|"timestamp"
decl_stmt|;
comment|// constructors
specifier|public
name|BaseCourseDemand
parameter_list|()
block|{
name|initialize
argument_list|()
expr_stmt|;
block|}
comment|/** 	 * Constructor for primary key 	 */
specifier|public
name|BaseCourseDemand
parameter_list|(
name|java
operator|.
name|lang
operator|.
name|Long
name|uniqueId
parameter_list|)
block|{
name|this
operator|.
name|setUniqueId
argument_list|(
name|uniqueId
argument_list|)
expr_stmt|;
name|initialize
argument_list|()
expr_stmt|;
block|}
comment|/** 	 * Constructor for required fields 	 */
specifier|public
name|BaseCourseDemand
parameter_list|(
name|java
operator|.
name|lang
operator|.
name|Long
name|uniqueId
parameter_list|,
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|Student
name|student
parameter_list|,
name|java
operator|.
name|lang
operator|.
name|Integer
name|priority
parameter_list|,
name|java
operator|.
name|lang
operator|.
name|Boolean
name|waitlist
parameter_list|,
name|java
operator|.
name|lang
operator|.
name|Boolean
name|alternative
parameter_list|,
name|java
operator|.
name|util
operator|.
name|Date
name|timestamp
parameter_list|)
block|{
name|this
operator|.
name|setUniqueId
argument_list|(
name|uniqueId
argument_list|)
expr_stmt|;
name|this
operator|.
name|setStudent
argument_list|(
name|student
argument_list|)
expr_stmt|;
name|this
operator|.
name|setPriority
argument_list|(
name|priority
argument_list|)
expr_stmt|;
name|this
operator|.
name|setWaitlist
argument_list|(
name|waitlist
argument_list|)
expr_stmt|;
name|this
operator|.
name|setAlternative
argument_list|(
name|alternative
argument_list|)
expr_stmt|;
name|this
operator|.
name|setTimestamp
argument_list|(
name|timestamp
argument_list|)
expr_stmt|;
name|initialize
argument_list|()
expr_stmt|;
block|}
specifier|protected
name|void
name|initialize
parameter_list|()
block|{
block|}
specifier|private
name|int
name|hashCode
init|=
name|Integer
operator|.
name|MIN_VALUE
decl_stmt|;
comment|// primary key
specifier|private
name|java
operator|.
name|lang
operator|.
name|Long
name|uniqueId
decl_stmt|;
comment|// fields
specifier|private
name|java
operator|.
name|lang
operator|.
name|Integer
name|priority
decl_stmt|;
specifier|private
name|java
operator|.
name|lang
operator|.
name|Boolean
name|waitlist
decl_stmt|;
specifier|private
name|java
operator|.
name|lang
operator|.
name|Boolean
name|alternative
decl_stmt|;
specifier|private
name|java
operator|.
name|util
operator|.
name|Date
name|timestamp
decl_stmt|;
comment|// many to one
specifier|private
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|Student
name|student
decl_stmt|;
specifier|private
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|FreeTime
name|freeTime
decl_stmt|;
comment|// collections
specifier|private
name|java
operator|.
name|util
operator|.
name|Set
name|courseRequests
decl_stmt|;
specifier|private
name|java
operator|.
name|util
operator|.
name|Set
name|enrollmentMessages
decl_stmt|;
comment|/** 	 * Return the unique identifier of this class      * @hibernate.id      *  generator-class="sequence"      *  column="UNIQUEID"      */
specifier|public
name|java
operator|.
name|lang
operator|.
name|Long
name|getUniqueId
parameter_list|()
block|{
return|return
name|uniqueId
return|;
block|}
comment|/** 	 * Set the unique identifier of this class 	 * @param uniqueId the new ID 	 */
specifier|public
name|void
name|setUniqueId
parameter_list|(
name|java
operator|.
name|lang
operator|.
name|Long
name|uniqueId
parameter_list|)
block|{
name|this
operator|.
name|uniqueId
operator|=
name|uniqueId
expr_stmt|;
name|this
operator|.
name|hashCode
operator|=
name|Integer
operator|.
name|MIN_VALUE
expr_stmt|;
block|}
comment|/** 	 * Return the value associated with the column: PRIORITY 	 */
specifier|public
name|java
operator|.
name|lang
operator|.
name|Integer
name|getPriority
parameter_list|()
block|{
return|return
name|priority
return|;
block|}
comment|/** 	 * Set the value related to the column: PRIORITY 	 * @param priority the PRIORITY value 	 */
specifier|public
name|void
name|setPriority
parameter_list|(
name|java
operator|.
name|lang
operator|.
name|Integer
name|priority
parameter_list|)
block|{
name|this
operator|.
name|priority
operator|=
name|priority
expr_stmt|;
block|}
comment|/** 	 * Return the value associated with the column: WAITLIST 	 */
specifier|public
name|java
operator|.
name|lang
operator|.
name|Boolean
name|isWaitlist
parameter_list|()
block|{
return|return
name|waitlist
return|;
block|}
comment|/** 	 * Set the value related to the column: WAITLIST 	 * @param waitlist the WAITLIST value 	 */
specifier|public
name|void
name|setWaitlist
parameter_list|(
name|java
operator|.
name|lang
operator|.
name|Boolean
name|waitlist
parameter_list|)
block|{
name|this
operator|.
name|waitlist
operator|=
name|waitlist
expr_stmt|;
block|}
comment|/** 	 * Return the value associated with the column: IS_ALTERNATIVE 	 */
specifier|public
name|java
operator|.
name|lang
operator|.
name|Boolean
name|isAlternative
parameter_list|()
block|{
return|return
name|alternative
return|;
block|}
comment|/** 	 * Set the value related to the column: IS_ALTERNATIVE 	 * @param alternative the IS_ALTERNATIVE value 	 */
specifier|public
name|void
name|setAlternative
parameter_list|(
name|java
operator|.
name|lang
operator|.
name|Boolean
name|alternative
parameter_list|)
block|{
name|this
operator|.
name|alternative
operator|=
name|alternative
expr_stmt|;
block|}
comment|/** 	 * Return the value associated with the column: TIMESTAMP 	 */
specifier|public
name|java
operator|.
name|util
operator|.
name|Date
name|getTimestamp
parameter_list|()
block|{
return|return
name|timestamp
return|;
block|}
comment|/** 	 * Set the value related to the column: TIMESTAMP 	 * @param timestamp the TIMESTAMP value 	 */
specifier|public
name|void
name|setTimestamp
parameter_list|(
name|java
operator|.
name|util
operator|.
name|Date
name|timestamp
parameter_list|)
block|{
name|this
operator|.
name|timestamp
operator|=
name|timestamp
expr_stmt|;
block|}
comment|/** 	 * Return the value associated with the column: STUDENT_ID 	 */
specifier|public
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|Student
name|getStudent
parameter_list|()
block|{
return|return
name|student
return|;
block|}
comment|/** 	 * Set the value related to the column: STUDENT_ID 	 * @param student the STUDENT_ID value 	 */
specifier|public
name|void
name|setStudent
parameter_list|(
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|Student
name|student
parameter_list|)
block|{
name|this
operator|.
name|student
operator|=
name|student
expr_stmt|;
block|}
comment|/** 	 * Return the value associated with the column: FREE_TIME_ID 	 */
specifier|public
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|FreeTime
name|getFreeTime
parameter_list|()
block|{
return|return
name|freeTime
return|;
block|}
comment|/** 	 * Set the value related to the column: FREE_TIME_ID 	 * @param freeTime the FREE_TIME_ID value 	 */
specifier|public
name|void
name|setFreeTime
parameter_list|(
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|FreeTime
name|freeTime
parameter_list|)
block|{
name|this
operator|.
name|freeTime
operator|=
name|freeTime
expr_stmt|;
block|}
comment|/** 	 * Return the value associated with the column: courseRequests 	 */
specifier|public
name|java
operator|.
name|util
operator|.
name|Set
name|getCourseRequests
parameter_list|()
block|{
return|return
name|courseRequests
return|;
block|}
comment|/** 	 * Set the value related to the column: courseRequests 	 * @param courseRequests the courseRequests value 	 */
specifier|public
name|void
name|setCourseRequests
parameter_list|(
name|java
operator|.
name|util
operator|.
name|Set
name|courseRequests
parameter_list|)
block|{
name|this
operator|.
name|courseRequests
operator|=
name|courseRequests
expr_stmt|;
block|}
specifier|public
name|void
name|addTocourseRequests
parameter_list|(
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|CourseRequest
name|courseRequest
parameter_list|)
block|{
if|if
condition|(
literal|null
operator|==
name|getCourseRequests
argument_list|()
condition|)
name|setCourseRequests
argument_list|(
operator|new
name|java
operator|.
name|util
operator|.
name|HashSet
argument_list|()
argument_list|)
expr_stmt|;
name|getCourseRequests
argument_list|()
operator|.
name|add
argument_list|(
name|courseRequest
argument_list|)
expr_stmt|;
block|}
comment|/**      * Return the value associated with the column: enrollmentMessages      */
specifier|public
name|java
operator|.
name|util
operator|.
name|Set
name|getEnrollmentMessages
parameter_list|()
block|{
return|return
name|enrollmentMessages
return|;
block|}
comment|/**      * Set the value related to the column: enrollmentMessages      * @param enrollmentMessages the enrollmentMessages value      */
specifier|public
name|void
name|setEnrollmentMessages
parameter_list|(
name|java
operator|.
name|util
operator|.
name|Set
name|enrollmentMessages
parameter_list|)
block|{
name|this
operator|.
name|enrollmentMessages
operator|=
name|enrollmentMessages
expr_stmt|;
block|}
specifier|public
name|void
name|addToenrollmentMessages
parameter_list|(
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|StudentEnrollmentMessage
name|studentEnrollmentMessage
parameter_list|)
block|{
if|if
condition|(
literal|null
operator|==
name|getEnrollmentMessages
argument_list|()
condition|)
name|setEnrollmentMessages
argument_list|(
operator|new
name|java
operator|.
name|util
operator|.
name|HashSet
argument_list|()
argument_list|)
expr_stmt|;
name|getEnrollmentMessages
argument_list|()
operator|.
name|add
argument_list|(
name|studentEnrollmentMessage
argument_list|)
expr_stmt|;
block|}
specifier|public
name|boolean
name|equals
parameter_list|(
name|Object
name|obj
parameter_list|)
block|{
if|if
condition|(
literal|null
operator|==
name|obj
condition|)
return|return
literal|false
return|;
if|if
condition|(
operator|!
operator|(
name|obj
operator|instanceof
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|CourseDemand
operator|)
condition|)
return|return
literal|false
return|;
else|else
block|{
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|CourseDemand
name|courseDemand
init|=
operator|(
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|CourseDemand
operator|)
name|obj
decl_stmt|;
if|if
condition|(
literal|null
operator|==
name|this
operator|.
name|getUniqueId
argument_list|()
operator|||
literal|null
operator|==
name|courseDemand
operator|.
name|getUniqueId
argument_list|()
condition|)
return|return
literal|false
return|;
else|else
return|return
operator|(
name|this
operator|.
name|getUniqueId
argument_list|()
operator|.
name|equals
argument_list|(
name|courseDemand
operator|.
name|getUniqueId
argument_list|()
argument_list|)
operator|)
return|;
block|}
block|}
specifier|public
name|int
name|hashCode
parameter_list|()
block|{
if|if
condition|(
name|Integer
operator|.
name|MIN_VALUE
operator|==
name|this
operator|.
name|hashCode
condition|)
block|{
if|if
condition|(
literal|null
operator|==
name|this
operator|.
name|getUniqueId
argument_list|()
condition|)
return|return
name|super
operator|.
name|hashCode
argument_list|()
return|;
else|else
block|{
name|String
name|hashStr
init|=
name|this
operator|.
name|getClass
argument_list|()
operator|.
name|getName
argument_list|()
operator|+
literal|":"
operator|+
name|this
operator|.
name|getUniqueId
argument_list|()
operator|.
name|hashCode
argument_list|()
decl_stmt|;
name|this
operator|.
name|hashCode
operator|=
name|hashStr
operator|.
name|hashCode
argument_list|()
expr_stmt|;
block|}
block|}
return|return
name|this
operator|.
name|hashCode
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
return|;
block|}
block|}
end_class

end_unit

