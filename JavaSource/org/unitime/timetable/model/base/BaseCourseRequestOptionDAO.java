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
operator|.
name|base
package|;
end_package

begin_import
import|import
name|org
operator|.
name|hibernate
operator|.
name|Hibernate
import|;
end_import

begin_import
import|import
name|org
operator|.
name|hibernate
operator|.
name|Session
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
name|Order
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
name|CourseRequestOptionDAO
import|;
end_import

begin_comment
comment|/**  * This is an automatically generated DAO class which should not be edited.  */
end_comment

begin_class
specifier|public
specifier|abstract
class|class
name|BaseCourseRequestOptionDAO
extends|extends
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
block|{
comment|// query name references
specifier|public
specifier|static
name|CourseRequestOptionDAO
name|instance
decl_stmt|;
comment|/** 	 * Return a singleton of the DAO 	 */
specifier|public
specifier|static
name|CourseRequestOptionDAO
name|getInstance
parameter_list|()
block|{
if|if
condition|(
literal|null
operator|==
name|instance
condition|)
name|instance
operator|=
operator|new
name|CourseRequestOptionDAO
argument_list|()
expr_stmt|;
return|return
name|instance
return|;
block|}
specifier|public
name|Class
name|getReferenceClass
parameter_list|()
block|{
return|return
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|CourseRequestOption
operator|.
name|class
return|;
block|}
specifier|public
name|Order
name|getDefaultOrder
parameter_list|()
block|{
return|return
literal|null
return|;
block|}
comment|/** 	 * Cast the object as a org.unitime.timetable.model.CourseRequestOption 	 */
specifier|public
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|CourseRequestOption
name|cast
parameter_list|(
name|Object
name|object
parameter_list|)
block|{
return|return
operator|(
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|CourseRequestOption
operator|)
name|object
return|;
block|}
specifier|public
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|CourseRequestOption
name|get
parameter_list|(
name|java
operator|.
name|lang
operator|.
name|Long
name|key
parameter_list|)
block|{
return|return
operator|(
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|CourseRequestOption
operator|)
name|get
argument_list|(
name|getReferenceClass
argument_list|()
argument_list|,
name|key
argument_list|)
return|;
block|}
specifier|public
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|CourseRequestOption
name|get
parameter_list|(
name|java
operator|.
name|lang
operator|.
name|Long
name|key
parameter_list|,
name|Session
name|s
parameter_list|)
block|{
return|return
operator|(
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|CourseRequestOption
operator|)
name|get
argument_list|(
name|getReferenceClass
argument_list|()
argument_list|,
name|key
argument_list|,
name|s
argument_list|)
return|;
block|}
specifier|public
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|CourseRequestOption
name|load
parameter_list|(
name|java
operator|.
name|lang
operator|.
name|Long
name|key
parameter_list|)
block|{
return|return
operator|(
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|CourseRequestOption
operator|)
name|load
argument_list|(
name|getReferenceClass
argument_list|()
argument_list|,
name|key
argument_list|)
return|;
block|}
specifier|public
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|CourseRequestOption
name|load
parameter_list|(
name|java
operator|.
name|lang
operator|.
name|Long
name|key
parameter_list|,
name|Session
name|s
parameter_list|)
block|{
return|return
operator|(
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|CourseRequestOption
operator|)
name|load
argument_list|(
name|getReferenceClass
argument_list|()
argument_list|,
name|key
argument_list|,
name|s
argument_list|)
return|;
block|}
specifier|public
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|CourseRequestOption
name|loadInitialize
parameter_list|(
name|java
operator|.
name|lang
operator|.
name|Long
name|key
parameter_list|,
name|Session
name|s
parameter_list|)
block|{
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|CourseRequestOption
name|obj
init|=
name|load
argument_list|(
name|key
argument_list|,
name|s
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|Hibernate
operator|.
name|isInitialized
argument_list|(
name|obj
argument_list|)
condition|)
block|{
name|Hibernate
operator|.
name|initialize
argument_list|(
name|obj
argument_list|)
expr_stmt|;
block|}
return|return
name|obj
return|;
block|}
comment|/** 	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value 	 * of the identifier property if the assigned generator is used.)  	 * @param courseRequestOption a transient instance of a persistent class  	 * @return the class identifier 	 */
specifier|public
name|java
operator|.
name|lang
operator|.
name|Long
name|save
parameter_list|(
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|CourseRequestOption
name|courseRequestOption
parameter_list|)
block|{
return|return
operator|(
name|java
operator|.
name|lang
operator|.
name|Long
operator|)
name|super
operator|.
name|save
argument_list|(
name|courseRequestOption
argument_list|)
return|;
block|}
comment|/** 	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value 	 * of the identifier property if the assigned generator is used.)  	 * Use the Session given. 	 * @param courseRequestOption a transient instance of a persistent class 	 * @param s the Session 	 * @return the class identifier 	 */
specifier|public
name|java
operator|.
name|lang
operator|.
name|Long
name|save
parameter_list|(
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|CourseRequestOption
name|courseRequestOption
parameter_list|,
name|Session
name|s
parameter_list|)
block|{
return|return
operator|(
name|java
operator|.
name|lang
operator|.
name|Long
operator|)
name|save
argument_list|(
operator|(
name|Object
operator|)
name|courseRequestOption
argument_list|,
name|s
argument_list|)
return|;
block|}
comment|/** 	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default 	 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the 	 * identifier property mapping.  	 * @param courseRequestOption a transient instance containing new or updated state  	 */
specifier|public
name|void
name|saveOrUpdate
parameter_list|(
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|CourseRequestOption
name|courseRequestOption
parameter_list|)
block|{
name|saveOrUpdate
argument_list|(
operator|(
name|Object
operator|)
name|courseRequestOption
argument_list|)
expr_stmt|;
block|}
comment|/** 	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default the 	 * instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the identifier 	 * property mapping.  	 * Use the Session given. 	 * @param courseRequestOption a transient instance containing new or updated state. 	 * @param s the Session. 	 */
specifier|public
name|void
name|saveOrUpdate
parameter_list|(
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|CourseRequestOption
name|courseRequestOption
parameter_list|,
name|Session
name|s
parameter_list|)
block|{
name|saveOrUpdate
argument_list|(
operator|(
name|Object
operator|)
name|courseRequestOption
argument_list|,
name|s
argument_list|)
expr_stmt|;
block|}
comment|/** 	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent 	 * instance with the same identifier in the current session. 	 * @param courseRequestOption a transient instance containing updated state 	 */
specifier|public
name|void
name|update
parameter_list|(
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|CourseRequestOption
name|courseRequestOption
parameter_list|)
block|{
name|update
argument_list|(
operator|(
name|Object
operator|)
name|courseRequestOption
argument_list|)
expr_stmt|;
block|}
comment|/** 	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent 	 * instance with the same identifier in the current session. 	 * Use the Session given. 	 * @param courseRequestOption a transient instance containing updated state 	 * @param the Session 	 */
specifier|public
name|void
name|update
parameter_list|(
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|CourseRequestOption
name|courseRequestOption
parameter_list|,
name|Session
name|s
parameter_list|)
block|{
name|update
argument_list|(
operator|(
name|Object
operator|)
name|courseRequestOption
argument_list|,
name|s
argument_list|)
expr_stmt|;
block|}
comment|/** 	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving 	 * Session or a transient instance with an identifier associated with existing persistent state.  	 * @param id the instance ID to be removed 	 */
specifier|public
name|void
name|delete
parameter_list|(
name|java
operator|.
name|lang
operator|.
name|Long
name|id
parameter_list|)
block|{
name|delete
argument_list|(
operator|(
name|Object
operator|)
name|load
argument_list|(
name|id
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|/** 	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving 	 * Session or a transient instance with an identifier associated with existing persistent state.  	 * Use the Session given. 	 * @param id the instance ID to be removed 	 * @param s the Session 	 */
specifier|public
name|void
name|delete
parameter_list|(
name|java
operator|.
name|lang
operator|.
name|Long
name|id
parameter_list|,
name|Session
name|s
parameter_list|)
block|{
name|delete
argument_list|(
operator|(
name|Object
operator|)
name|load
argument_list|(
name|id
argument_list|,
name|s
argument_list|)
argument_list|,
name|s
argument_list|)
expr_stmt|;
block|}
comment|/** 	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving 	 * Session or a transient instance with an identifier associated with existing persistent state.  	 * @param courseRequestOption the instance to be removed 	 */
specifier|public
name|void
name|delete
parameter_list|(
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|CourseRequestOption
name|courseRequestOption
parameter_list|)
block|{
name|delete
argument_list|(
operator|(
name|Object
operator|)
name|courseRequestOption
argument_list|)
expr_stmt|;
block|}
comment|/** 	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving 	 * Session or a transient instance with an identifier associated with existing persistent state.  	 * Use the Session given. 	 * @param courseRequestOption the instance to be removed 	 * @param s the Session 	 */
specifier|public
name|void
name|delete
parameter_list|(
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|CourseRequestOption
name|courseRequestOption
parameter_list|,
name|Session
name|s
parameter_list|)
block|{
name|delete
argument_list|(
operator|(
name|Object
operator|)
name|courseRequestOption
argument_list|,
name|s
argument_list|)
expr_stmt|;
block|}
comment|/** 	 * Re-read the state of the given instance from the underlying database. It is inadvisable to use this to implement 	 * long-running sessions that span many business tasks. This method is, however, useful in certain special circumstances. 	 * For example  	 *<ul>  	 *<li>where a database trigger alters the object state upon insert or update</li> 	 *<li>after executing direct SQL (eg. a mass update) in the same session</li> 	 *<li>after inserting a Blob or Clob</li> 	 *</ul> 	 */
specifier|public
name|void
name|refresh
parameter_list|(
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|CourseRequestOption
name|courseRequestOption
parameter_list|,
name|Session
name|s
parameter_list|)
block|{
name|refresh
argument_list|(
operator|(
name|Object
operator|)
name|courseRequestOption
argument_list|,
name|s
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

