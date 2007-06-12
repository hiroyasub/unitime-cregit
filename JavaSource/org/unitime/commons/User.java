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
name|commons
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|HashMap
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
name|Properties
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
name|interfaces
operator|.
name|ExternalUidLookup
import|;
end_import

begin_comment
comment|/**  * Authenticates a user through I2A2  * Contains all attributes of the logged in user.  *   * @author Heston Fernandes   */
end_comment

begin_class
specifier|public
class|class
name|User
block|{
specifier|private
name|String
name|iId
init|=
literal|null
decl_stmt|;
specifier|private
name|String
name|iLogin
init|=
literal|null
decl_stmt|;
specifier|private
name|String
name|iName
init|=
literal|null
decl_stmt|;
specifier|private
name|String
name|iRole
init|=
literal|null
decl_stmt|;
specifier|private
name|boolean
name|iIsAdmin
init|=
literal|false
decl_stmt|;
specifier|private
name|Vector
name|roles
init|=
literal|null
decl_stmt|;
specifier|private
name|Vector
name|iDepartments
init|=
operator|new
name|Vector
argument_list|()
decl_stmt|;
specifier|private
name|Properties
name|otherAttributes
init|=
operator|new
name|Properties
argument_list|()
decl_stmt|;
specifier|public
name|User
parameter_list|()
block|{
block|}
comment|/** 	 * Set the ID of the user 	 * @param name PUID 	 */
specifier|public
name|void
name|setId
parameter_list|(
name|String
name|id
parameter_list|)
block|{
name|iId
operator|=
name|id
expr_stmt|;
block|}
comment|/** 	 * Retrieve ID of current user 	 * @return id 	 */
specifier|public
name|String
name|getId
parameter_list|()
block|{
return|return
name|iId
return|;
block|}
comment|/**      * Set the full name of the user      * @param name Full Name      */
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
comment|/** 	 * Retrieve full name of current user 	 * @return String {first} [{middle}] {last} 	 */
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
name|iName
return|;
block|}
comment|/** 	 * Set the username of the logged in user 	 * @param login Login id 	 */
specifier|public
name|void
name|setLogin
parameter_list|(
name|String
name|login
parameter_list|)
block|{
name|iLogin
operator|=
name|login
operator|.
name|toLowerCase
argument_list|()
expr_stmt|;
block|}
comment|/** 	 * Retrieve login id of the current user 	 * @return Login Id String 	 */
specifier|public
name|String
name|getLogin
parameter_list|()
block|{
return|return
name|iLogin
return|;
block|}
comment|/** 	 * Set the role assigned to the user 	 * Typically "Administrator" or "Schedule Deputy" 	 * @param name Role Name 	 */
specifier|public
name|void
name|setRole
parameter_list|(
name|String
name|role
parameter_list|)
block|{
name|iRole
operator|=
name|role
expr_stmt|;
block|}
comment|/** 	 * Retrieve role of current user 	 * @return Role Name 	 */
specifier|public
name|String
name|getRole
parameter_list|()
block|{
return|return
name|iRole
return|;
block|}
comment|/** 	 * Checks if current user has the specified role 	 * @param role String 	 * @return true if the user has the specified role 	 */
specifier|public
name|boolean
name|hasRole
parameter_list|(
name|String
name|role
parameter_list|)
block|{
return|return
name|getRole
argument_list|()
operator|.
name|equalsIgnoreCase
argument_list|(
name|role
argument_list|)
return|;
block|}
comment|/** 	 * Checks if current user is an administrator 	 * @return true if admin, false otherwise 	 */
specifier|public
name|boolean
name|isAdmin
parameter_list|()
block|{
return|return
name|iIsAdmin
return|;
block|}
comment|/** 	 * Set the current user as an administrator 	 * @param isAdmin true indicates current user is admin, false otherwise 	 */
specifier|public
name|void
name|setAdmin
parameter_list|(
name|boolean
name|isAdmin
parameter_list|)
block|{
name|iIsAdmin
operator|=
name|isAdmin
expr_stmt|;
block|}
comment|/** 	 * Retrieves all departments that a user belongs to 	 * @return Vector of department numbers 	 */
specifier|public
name|Vector
name|getDepartments
parameter_list|()
block|{
return|return
name|iDepartments
return|;
block|}
comment|/** 	 * Set the user department(s) 	 * @param _depts Vector of department numbers 	 */
specifier|public
name|void
name|setDepartments
parameter_list|(
name|Vector
name|_depts
parameter_list|)
block|{
name|iDepartments
operator|=
name|_depts
expr_stmt|;
block|}
comment|/** 	 * @return Current Role 	 */
specifier|public
name|String
name|getCurrentRole
parameter_list|()
block|{
return|return
name|iRole
return|;
block|}
comment|/**      * Gets all roles that a user may possess      * @return Vector of Role Names      * @see ADMIN_ROLE, etc 	 */
specifier|public
name|Vector
name|getRoles
parameter_list|()
block|{
return|return
name|roles
return|;
block|}
comment|/**      * Sets all roles that a user may possess      * @param roles Vector of Role Names      * @see ADMIN_ROLE, etc      */
specifier|public
name|void
name|setRoles
parameter_list|(
name|Vector
name|roles
parameter_list|)
block|{
name|this
operator|.
name|roles
operator|=
name|roles
expr_stmt|;
block|}
comment|/**      * @return Returns other attributes in a Properties object.      */
specifier|public
name|Properties
name|getOtherAttributes
parameter_list|()
block|{
return|return
name|otherAttributes
return|;
block|}
comment|/**      * Enables other application to store other user attributes      * @param otherAttributes Store other attributes via a properties object      */
specifier|public
name|void
name|setOtherAttributes
parameter_list|(
name|Properties
name|otherAttributes
parameter_list|)
block|{
name|this
operator|.
name|otherAttributes
operator|=
name|otherAttributes
expr_stmt|;
block|}
comment|/**      * Looks up other attribute name      * @param attributeName Attribute name      * @return Object representing the attribute value. Null if not found      */
specifier|public
name|Object
name|getAttribute
parameter_list|(
name|String
name|attributeName
parameter_list|)
block|{
return|return
name|otherAttributes
operator|.
name|get
argument_list|(
name|attributeName
argument_list|)
return|;
block|}
comment|/**      * Store additional user attribute      * @param attributeName Attribute name      * @param attributeName Attribute value      */
specifier|public
name|void
name|setAttribute
parameter_list|(
name|String
name|attributeName
parameter_list|,
name|Object
name|attributeValue
parameter_list|)
block|{
name|otherAttributes
operator|.
name|put
argument_list|(
name|attributeName
argument_list|,
name|attributeValue
argument_list|)
expr_stmt|;
block|}
specifier|public
specifier|static
name|boolean
name|canIdentify
parameter_list|()
block|{
return|return
literal|"true"
operator|.
name|equals
argument_list|(
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"tmtbl.instructor.externalId.lookup.enabled"
argument_list|,
literal|"false"
argument_list|)
argument_list|)
return|;
block|}
specifier|public
specifier|static
name|User
name|identify
parameter_list|(
name|String
name|externalId
parameter_list|)
block|{
if|if
condition|(
name|externalId
operator|==
literal|null
operator|||
name|externalId
operator|.
name|trim
argument_list|()
operator|.
name|length
argument_list|()
operator|==
literal|0
condition|)
return|return
literal|null
return|;
if|if
condition|(
name|canIdentify
argument_list|()
condition|)
block|{
try|try
block|{
name|HashMap
name|attributes
init|=
operator|new
name|HashMap
argument_list|()
decl_stmt|;
name|attributes
operator|.
name|put
argument_list|(
name|ExternalUidLookup
operator|.
name|SEARCH_ID
argument_list|,
name|externalId
argument_list|)
expr_stmt|;
name|String
name|className
init|=
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"tmtbl.manager.externalId.lookup.class"
argument_list|)
decl_stmt|;
name|ExternalUidLookup
name|lookup
init|=
operator|(
name|ExternalUidLookup
operator|)
operator|(
name|Class
operator|.
name|forName
argument_list|(
name|className
argument_list|)
operator|.
name|newInstance
argument_list|()
operator|)
decl_stmt|;
name|Map
name|results
init|=
name|lookup
operator|.
name|doLookup
argument_list|(
name|attributes
argument_list|)
decl_stmt|;
if|if
condition|(
name|results
operator|==
literal|null
condition|)
return|return
literal|null
return|;
name|User
name|user
init|=
operator|new
name|User
argument_list|()
decl_stmt|;
name|user
operator|.
name|setId
argument_list|(
operator|(
name|String
operator|)
name|results
operator|.
name|get
argument_list|(
name|ExternalUidLookup
operator|.
name|EXTERNAL_ID
argument_list|)
argument_list|)
expr_stmt|;
name|user
operator|.
name|setLogin
argument_list|(
operator|(
name|String
operator|)
name|results
operator|.
name|get
argument_list|(
name|ExternalUidLookup
operator|.
name|USERNAME
argument_list|)
argument_list|)
expr_stmt|;
name|user
operator|.
name|setName
argument_list|(
operator|(
name|String
operator|)
name|results
operator|.
name|get
argument_list|(
name|ExternalUidLookup
operator|.
name|FIRST_NAME
argument_list|)
operator|+
literal|" "
operator|+
operator|(
name|String
operator|)
name|results
operator|.
name|get
argument_list|(
name|ExternalUidLookup
operator|.
name|MIDDLE_NAME
argument_list|)
operator|+
literal|" "
operator|+
operator|(
name|String
operator|)
name|results
operator|.
name|get
argument_list|(
name|ExternalUidLookup
operator|.
name|LAST_NAME
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|user
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
block|}
block|}
return|return
literal|null
return|;
block|}
block|}
end_class

end_unit

