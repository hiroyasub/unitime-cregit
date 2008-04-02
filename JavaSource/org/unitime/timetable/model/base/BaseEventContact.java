begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*   * UniTime 3.1 (University Course Timetabling& Student Sectioning Application)  * Copyright (C) 2008, UniTime.org  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 2 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program; if not, write to the Free Software Foundation, Inc.,  * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.  */
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
comment|/**  * This is an object that contains data related to the EVENT_CONTACT table.  * Do not modify this class because it will be overwritten if the configuration file  * related to this class is modified.  *  * @hibernate.class  *  table="EVENT_CONTACT"  */
end_comment

begin_class
specifier|public
specifier|abstract
class|class
name|BaseEventContact
implements|implements
name|Serializable
block|{
specifier|public
specifier|static
name|String
name|REF
init|=
literal|"EventContact"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_EXTERNAL_UNIQUE_ID
init|=
literal|"externalUniqueId"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_EMAIL_ADDRESS
init|=
literal|"emailAddress"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_PHONE
init|=
literal|"phone"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_FIRST_NAME
init|=
literal|"firstName"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_MIDDLE_NAME
init|=
literal|"middleName"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_LAST_NAME
init|=
literal|"lastName"
decl_stmt|;
comment|// constructors
specifier|public
name|BaseEventContact
parameter_list|()
block|{
name|initialize
argument_list|()
expr_stmt|;
block|}
comment|/** 	 * Constructor for primary key 	 */
specifier|public
name|BaseEventContact
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
name|BaseEventContact
parameter_list|(
name|java
operator|.
name|lang
operator|.
name|Long
name|uniqueId
parameter_list|,
name|java
operator|.
name|lang
operator|.
name|String
name|emailAddress
parameter_list|,
name|java
operator|.
name|lang
operator|.
name|String
name|phone
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
name|setEmailAddress
argument_list|(
name|emailAddress
argument_list|)
expr_stmt|;
name|this
operator|.
name|setPhone
argument_list|(
name|phone
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
name|String
name|externalUniqueId
decl_stmt|;
specifier|private
name|java
operator|.
name|lang
operator|.
name|String
name|emailAddress
decl_stmt|;
specifier|private
name|java
operator|.
name|lang
operator|.
name|String
name|phone
decl_stmt|;
specifier|private
name|java
operator|.
name|lang
operator|.
name|String
name|firstName
decl_stmt|;
specifier|private
name|java
operator|.
name|lang
operator|.
name|String
name|middleName
decl_stmt|;
specifier|private
name|java
operator|.
name|lang
operator|.
name|String
name|lastName
decl_stmt|;
comment|/** 	 * Return the unique identifier of this class      * @hibernate.id      *  generator-class="org.unitime.commons.hibernate.id.UniqueIdGenerator"      *  column="UNIQUEID"      */
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
comment|/** 	 * Return the value associated with the column: EXTERNAL_ID 	 */
specifier|public
name|java
operator|.
name|lang
operator|.
name|String
name|getExternalUniqueId
parameter_list|()
block|{
return|return
name|externalUniqueId
return|;
block|}
comment|/** 	 * Set the value related to the column: EXTERNAL_ID 	 * @param externalUniqueId the EXTERNAL_ID value 	 */
specifier|public
name|void
name|setExternalUniqueId
parameter_list|(
name|java
operator|.
name|lang
operator|.
name|String
name|externalUniqueId
parameter_list|)
block|{
name|this
operator|.
name|externalUniqueId
operator|=
name|externalUniqueId
expr_stmt|;
block|}
comment|/** 	 * Return the value associated with the column: EMAIL 	 */
specifier|public
name|java
operator|.
name|lang
operator|.
name|String
name|getEmailAddress
parameter_list|()
block|{
return|return
name|emailAddress
return|;
block|}
comment|/** 	 * Set the value related to the column: EMAIL 	 * @param emailAddress the EMAIL value 	 */
specifier|public
name|void
name|setEmailAddress
parameter_list|(
name|java
operator|.
name|lang
operator|.
name|String
name|emailAddress
parameter_list|)
block|{
name|this
operator|.
name|emailAddress
operator|=
name|emailAddress
expr_stmt|;
block|}
comment|/** 	 * Return the value associated with the column: PHONE 	 */
specifier|public
name|java
operator|.
name|lang
operator|.
name|String
name|getPhone
parameter_list|()
block|{
return|return
name|phone
return|;
block|}
comment|/** 	 * Set the value related to the column: PHONE 	 * @param phone the PHONE value 	 */
specifier|public
name|void
name|setPhone
parameter_list|(
name|java
operator|.
name|lang
operator|.
name|String
name|phone
parameter_list|)
block|{
name|this
operator|.
name|phone
operator|=
name|phone
expr_stmt|;
block|}
comment|/** 	 * Return the value associated with the column: FIRSTNAME 	 */
specifier|public
name|java
operator|.
name|lang
operator|.
name|String
name|getFirstName
parameter_list|()
block|{
return|return
name|firstName
return|;
block|}
comment|/** 	 * Set the value related to the column: FIRSTNAME 	 * @param firstName the FIRSTNAME value 	 */
specifier|public
name|void
name|setFirstName
parameter_list|(
name|java
operator|.
name|lang
operator|.
name|String
name|firstName
parameter_list|)
block|{
name|this
operator|.
name|firstName
operator|=
name|firstName
expr_stmt|;
block|}
comment|/** 	 * Return the value associated with the column: MIDDLENAME 	 */
specifier|public
name|java
operator|.
name|lang
operator|.
name|String
name|getMiddleName
parameter_list|()
block|{
return|return
name|middleName
return|;
block|}
comment|/** 	 * Set the value related to the column: MIDDLENAME 	 * @param middleName the MIDDLENAME value 	 */
specifier|public
name|void
name|setMiddleName
parameter_list|(
name|java
operator|.
name|lang
operator|.
name|String
name|middleName
parameter_list|)
block|{
name|this
operator|.
name|middleName
operator|=
name|middleName
expr_stmt|;
block|}
comment|/** 	 * Return the value associated with the column: LASTNAME 	 */
specifier|public
name|java
operator|.
name|lang
operator|.
name|String
name|getLastName
parameter_list|()
block|{
return|return
name|lastName
return|;
block|}
comment|/** 	 * Set the value related to the column: LASTNAME 	 * @param lastName the LASTNAME value 	 */
specifier|public
name|void
name|setLastName
parameter_list|(
name|java
operator|.
name|lang
operator|.
name|String
name|lastName
parameter_list|)
block|{
name|this
operator|.
name|lastName
operator|=
name|lastName
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
name|EventContact
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
name|EventContact
name|eventContact
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
name|EventContact
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
name|eventContact
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
name|eventContact
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

