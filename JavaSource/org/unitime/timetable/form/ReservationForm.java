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
name|form
package|;
end_package

begin_import
import|import
name|java
operator|.
name|text
operator|.
name|ParseException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|text
operator|.
name|SimpleDateFormat
import|;
end_import

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
name|HashMap
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
name|http
operator|.
name|HttpServletRequest
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|struts
operator|.
name|action
operator|.
name|ActionErrors
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|struts
operator|.
name|action
operator|.
name|ActionForm
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|struts
operator|.
name|action
operator|.
name|ActionMapping
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|struts
operator|.
name|action
operator|.
name|ActionMessage
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
name|Reservation
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
name|DynamicList
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
name|DynamicListObjectFactory
import|;
end_import

begin_comment
comment|/**  * Base Reservation Form  * Subclasses: IndividualReservation, CharacteristicReservation forms  *   * @author Heston Fernandes  */
end_comment

begin_class
specifier|public
class|class
name|ReservationForm
extends|extends
name|ActionForm
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|7595942422717942120L
decl_stmt|;
specifier|public
specifier|final
name|int
name|RESV_ROWS_ADDED
init|=
literal|1
decl_stmt|;
comment|// --------------------------------------------------------- Instance Variables
specifier|private
name|String
name|op
decl_stmt|;
specifier|private
name|List
name|reservationId
decl_stmt|;
specifier|private
name|List
name|priority
decl_stmt|;
specifier|private
name|List
name|reservationType
decl_stmt|;
specifier|private
name|Long
name|ownerId
decl_stmt|;
specifier|private
name|String
name|ownerName
decl_stmt|;
specifier|private
name|String
name|ownerType
decl_stmt|;
specifier|private
name|String
name|ownerTypeLabel
decl_stmt|;
specifier|private
name|String
name|reservationClass
decl_stmt|;
specifier|private
name|Boolean
name|addBlankRow
decl_stmt|;
specifier|private
name|String
name|ioLimit
decl_stmt|;
specifier|private
name|String
name|crsLimit
decl_stmt|;
specifier|private
name|Boolean
name|unlimited
decl_stmt|;
comment|// --------------------------------------------------------- Classes
comment|/** Factory to create dynamic list element for Preference */
specifier|protected
name|DynamicListObjectFactory
name|factoryResv
init|=
operator|new
name|DynamicListObjectFactory
argument_list|()
block|{
specifier|public
name|Object
name|create
parameter_list|()
block|{
return|return
operator|new
name|String
argument_list|(
name|Constants
operator|.
name|BLANK_OPTION_VALUE
argument_list|)
return|;
block|}
block|}
decl_stmt|;
comment|// --------------------------------------------------------- Methods
comment|/**       * Method reset      * @param mapping      * @param request      */
specifier|public
name|void
name|reset
parameter_list|(
name|ActionMapping
name|mapping
parameter_list|,
name|HttpServletRequest
name|request
parameter_list|)
block|{
name|op
operator|=
literal|null
expr_stmt|;
name|ownerId
operator|=
literal|null
expr_stmt|;
name|ownerType
operator|=
literal|null
expr_stmt|;
name|ownerName
operator|=
literal|null
expr_stmt|;
name|ownerTypeLabel
operator|=
literal|null
expr_stmt|;
name|reservationClass
operator|=
literal|null
expr_stmt|;
name|addBlankRow
operator|=
operator|new
name|Boolean
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|reservationId
operator|=
name|DynamicList
operator|.
name|getInstance
argument_list|(
operator|new
name|ArrayList
argument_list|()
argument_list|,
name|factoryResv
argument_list|)
expr_stmt|;
name|priority
operator|=
name|DynamicList
operator|.
name|getInstance
argument_list|(
operator|new
name|ArrayList
argument_list|()
argument_list|,
name|factoryResv
argument_list|)
expr_stmt|;
name|reservationType
operator|=
name|DynamicList
operator|.
name|getInstance
argument_list|(
operator|new
name|ArrayList
argument_list|()
argument_list|,
name|factoryResv
argument_list|)
expr_stmt|;
block|}
comment|/**       * Method validate      * @param mapping      * @param request      * @return ActionErrors      */
specifier|public
name|ActionErrors
name|validate
parameter_list|(
name|ActionMapping
name|mapping
parameter_list|,
name|HttpServletRequest
name|request
parameter_list|)
block|{
name|ActionErrors
name|errors
init|=
operator|new
name|ActionErrors
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
name|checkListNumber
argument_list|(
name|priority
argument_list|,
literal|false
argument_list|,
operator|new
name|Integer
argument_list|(
literal|1
argument_list|)
argument_list|,
operator|new
name|Integer
argument_list|(
literal|10
argument_list|)
argument_list|)
condition|)
block|{
name|errors
operator|.
name|add
argument_list|(
literal|"priority"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.range"
argument_list|,
literal|"Priority"
argument_list|,
literal|"1"
argument_list|,
literal|"10"
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
operator|!
name|checkList
argument_list|(
name|reservationType
argument_list|,
literal|true
argument_list|)
condition|)
block|{
name|errors
operator|.
name|add
argument_list|(
literal|"reservationType"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.generic"
argument_list|,
literal|"Invalid Reservation Type: Check for blank values. "
argument_list|)
argument_list|)
expr_stmt|;
block|}
return|return
name|errors
return|;
block|}
specifier|public
name|Long
name|getOwnerId
parameter_list|()
block|{
return|return
name|ownerId
return|;
block|}
specifier|public
name|void
name|setOwnerId
parameter_list|(
name|Long
name|ownerId
parameter_list|)
block|{
name|this
operator|.
name|ownerId
operator|=
name|ownerId
expr_stmt|;
block|}
specifier|public
name|String
name|getOwnerType
parameter_list|()
block|{
return|return
name|ownerType
return|;
block|}
specifier|public
name|void
name|setOwnerType
parameter_list|(
name|String
name|ownerType
parameter_list|)
block|{
name|this
operator|.
name|ownerType
operator|=
name|ownerType
expr_stmt|;
block|}
specifier|public
name|String
name|getReservationClass
parameter_list|()
block|{
return|return
name|reservationClass
return|;
block|}
specifier|public
name|void
name|setReservationClass
parameter_list|(
name|String
name|reservationClass
parameter_list|)
block|{
name|this
operator|.
name|reservationClass
operator|=
name|reservationClass
expr_stmt|;
block|}
specifier|public
name|String
name|getOp
parameter_list|()
block|{
return|return
name|op
return|;
block|}
specifier|public
name|void
name|setOp
parameter_list|(
name|String
name|op
parameter_list|)
block|{
name|this
operator|.
name|op
operator|=
name|op
expr_stmt|;
block|}
specifier|public
name|String
name|getOwnerName
parameter_list|()
block|{
return|return
name|ownerName
return|;
block|}
specifier|public
name|void
name|setOwnerName
parameter_list|(
name|String
name|ownerName
parameter_list|)
block|{
name|this
operator|.
name|ownerName
operator|=
name|ownerName
expr_stmt|;
block|}
specifier|public
name|String
name|getOwnerTypeLabel
parameter_list|()
block|{
return|return
name|ownerTypeLabel
return|;
block|}
specifier|public
name|void
name|setOwnerTypeLabel
parameter_list|(
name|String
name|ownerTypeLabel
parameter_list|)
block|{
name|this
operator|.
name|ownerTypeLabel
operator|=
name|ownerTypeLabel
expr_stmt|;
block|}
specifier|public
name|Boolean
name|getAddBlankRow
parameter_list|()
block|{
return|return
name|addBlankRow
return|;
block|}
specifier|public
name|void
name|setAddBlankRow
parameter_list|(
name|Boolean
name|addBlankRow
parameter_list|)
block|{
name|this
operator|.
name|addBlankRow
operator|=
name|addBlankRow
expr_stmt|;
block|}
specifier|public
name|String
name|getCrsLimit
parameter_list|()
block|{
return|return
name|crsLimit
return|;
block|}
specifier|public
name|void
name|setCrsLimit
parameter_list|(
name|String
name|crsLimit
parameter_list|)
block|{
name|this
operator|.
name|crsLimit
operator|=
name|crsLimit
expr_stmt|;
block|}
specifier|public
name|String
name|getIoLimit
parameter_list|()
block|{
return|return
name|ioLimit
return|;
block|}
specifier|public
name|void
name|setIoLimit
parameter_list|(
name|String
name|ioLimit
parameter_list|)
block|{
name|this
operator|.
name|ioLimit
operator|=
name|ioLimit
expr_stmt|;
block|}
specifier|public
name|Boolean
name|getUnlimited
parameter_list|()
block|{
return|return
name|unlimited
return|;
block|}
specifier|public
name|void
name|setUnlimited
parameter_list|(
name|Boolean
name|unlimited
parameter_list|)
block|{
name|this
operator|.
name|unlimited
operator|=
name|unlimited
expr_stmt|;
block|}
specifier|public
name|List
name|getPriority
parameter_list|()
block|{
return|return
name|priority
return|;
block|}
specifier|public
name|void
name|setPriority
parameter_list|(
name|List
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
specifier|public
name|String
name|getPriority
parameter_list|(
name|int
name|key
parameter_list|)
block|{
return|return
name|priority
operator|.
name|get
argument_list|(
name|key
argument_list|)
operator|.
name|toString
argument_list|()
return|;
block|}
specifier|public
name|void
name|setPriority
parameter_list|(
name|int
name|key
parameter_list|,
name|Object
name|value
parameter_list|)
block|{
name|this
operator|.
name|priority
operator|.
name|set
argument_list|(
name|key
argument_list|,
name|value
argument_list|)
expr_stmt|;
block|}
specifier|public
name|List
name|getReservationId
parameter_list|()
block|{
return|return
name|reservationId
return|;
block|}
specifier|public
name|void
name|setReservationId
parameter_list|(
name|List
name|reservationId
parameter_list|)
block|{
name|this
operator|.
name|reservationId
operator|=
name|reservationId
expr_stmt|;
block|}
specifier|public
name|String
name|getReservationId
parameter_list|(
name|int
name|key
parameter_list|)
block|{
return|return
name|reservationId
operator|.
name|get
argument_list|(
name|key
argument_list|)
operator|.
name|toString
argument_list|()
return|;
block|}
specifier|public
name|void
name|setReservationId
parameter_list|(
name|int
name|key
parameter_list|,
name|Object
name|value
parameter_list|)
block|{
name|this
operator|.
name|reservationId
operator|.
name|set
argument_list|(
name|key
argument_list|,
name|value
argument_list|)
expr_stmt|;
block|}
specifier|public
name|List
name|getReservationType
parameter_list|()
block|{
return|return
name|reservationType
return|;
block|}
specifier|public
name|void
name|setReservationType
parameter_list|(
name|List
name|reservationType
parameter_list|)
block|{
name|this
operator|.
name|reservationType
operator|=
name|reservationType
expr_stmt|;
block|}
specifier|public
name|String
name|getReservationType
parameter_list|(
name|int
name|key
parameter_list|)
block|{
return|return
name|reservationType
operator|.
name|get
argument_list|(
name|key
argument_list|)
operator|.
name|toString
argument_list|()
return|;
block|}
specifier|public
name|void
name|setReservationType
parameter_list|(
name|int
name|key
parameter_list|,
name|Object
name|value
parameter_list|)
block|{
name|this
operator|.
name|reservationType
operator|.
name|set
argument_list|(
name|key
argument_list|,
name|value
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|addToPriority
parameter_list|(
name|String
name|priority
parameter_list|)
block|{
name|this
operator|.
name|priority
operator|.
name|add
argument_list|(
name|priority
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|addToReservationId
parameter_list|(
name|String
name|reservationId
parameter_list|)
block|{
name|this
operator|.
name|reservationId
operator|.
name|add
argument_list|(
name|reservationId
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|addToReservationType
parameter_list|(
name|String
name|reservationType
parameter_list|)
block|{
name|this
operator|.
name|reservationType
operator|.
name|add
argument_list|(
name|reservationType
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|addBlankRows
parameter_list|()
block|{
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|RESV_ROWS_ADDED
condition|;
name|i
operator|++
control|)
block|{
name|addToPriority
argument_list|(
literal|""
operator|+
name|Constants
operator|.
name|RESV_DEFAULT_PRIORITY
argument_list|)
expr_stmt|;
name|addToReservationId
argument_list|(
name|Constants
operator|.
name|BLANK_OPTION_VALUE
argument_list|)
expr_stmt|;
name|addToReservationType
argument_list|(
name|Constants
operator|.
name|BLANK_OPTION_VALUE
argument_list|)
expr_stmt|;
block|}
block|}
specifier|public
name|void
name|clear
parameter_list|()
block|{
name|this
operator|.
name|reservationId
operator|.
name|clear
argument_list|()
expr_stmt|;
name|this
operator|.
name|reservationType
operator|.
name|clear
argument_list|()
expr_stmt|;
name|this
operator|.
name|priority
operator|.
name|clear
argument_list|()
expr_stmt|;
block|}
specifier|public
name|void
name|addReservation
parameter_list|(
name|Reservation
name|resv
parameter_list|)
block|{
name|addToReservationId
argument_list|(
name|resv
operator|.
name|getUniqueId
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
name|addToPriority
argument_list|(
name|resv
operator|.
name|getPriority
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
name|addToReservationType
argument_list|(
name|resv
operator|.
name|getReservationType
argument_list|()
operator|.
name|getUniqueId
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|removeRow
parameter_list|(
name|int
name|rowNum
parameter_list|)
block|{
if|if
condition|(
name|rowNum
operator|>=
literal|0
condition|)
block|{
name|reservationId
operator|.
name|remove
argument_list|(
name|rowNum
argument_list|)
expr_stmt|;
name|reservationType
operator|.
name|remove
argument_list|(
name|rowNum
argument_list|)
expr_stmt|;
name|priority
operator|.
name|remove
argument_list|(
name|rowNum
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * Checks that there are no duplicates and that all elements have a value      * @param lst List of values      * @param ignoreDuplicates true ignores duplicate values      * @return true if checks ok, false otherwise      */
specifier|public
name|boolean
name|checkList
parameter_list|(
name|List
name|lst
parameter_list|,
name|boolean
name|ignoreDuplicates
parameter_list|)
block|{
name|HashMap
name|map
init|=
operator|new
name|HashMap
argument_list|()
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|lst
operator|.
name|size
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|String
name|value
init|=
operator|(
operator|(
name|String
operator|)
name|lst
operator|.
name|get
argument_list|(
name|i
argument_list|)
operator|)
decl_stmt|;
comment|// No selection made
if|if
condition|(
name|value
operator|==
literal|null
operator|||
name|value
operator|.
name|trim
argument_list|()
operator|.
name|equals
argument_list|(
name|Constants
operator|.
name|BLANK_OPTION_VALUE
argument_list|)
condition|)
block|{
return|return
literal|false
return|;
block|}
comment|// Duplicate selection made
if|if
condition|(
operator|!
name|ignoreDuplicates
operator|&&
name|map
operator|.
name|get
argument_list|(
name|value
operator|.
name|trim
argument_list|()
argument_list|)
operator|!=
literal|null
condition|)
block|{
name|lst
operator|.
name|set
argument_list|(
name|i
argument_list|,
name|Constants
operator|.
name|BLANK_OPTION_VALUE
argument_list|)
expr_stmt|;
return|return
literal|false
return|;
block|}
name|map
operator|.
name|put
argument_list|(
name|value
argument_list|,
name|value
argument_list|)
expr_stmt|;
block|}
return|return
literal|true
return|;
block|}
comment|/**      * Checks list for invalid numbers      * @param lst      * @param ignoreNulls ignore null / blank values      * @param minValue check for min value if not null      * @param maxValue check for max value if not null      * @return true if all checks pass, false otherwise      */
specifier|public
name|boolean
name|checkListNumber
parameter_list|(
name|List
name|lst
parameter_list|,
name|boolean
name|ignoreNulls
parameter_list|,
name|Integer
name|minValue
parameter_list|,
name|Integer
name|maxValue
parameter_list|)
block|{
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|lst
operator|.
name|size
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|String
name|value
init|=
operator|(
operator|(
name|String
operator|)
name|lst
operator|.
name|get
argument_list|(
name|i
argument_list|)
operator|)
decl_stmt|;
name|int
name|intval
init|=
literal|0
decl_stmt|;
comment|// No selection made
if|if
condition|(
name|value
operator|==
literal|null
operator|||
name|value
operator|.
name|trim
argument_list|()
operator|.
name|equals
argument_list|(
name|Constants
operator|.
name|BLANK_OPTION_VALUE
argument_list|)
condition|)
block|{
if|if
condition|(
operator|!
name|ignoreNulls
condition|)
return|return
literal|false
return|;
else|else
continue|continue;
block|}
comment|// Check is number
try|try
block|{
name|intval
operator|=
name|Integer
operator|.
name|parseInt
argument_list|(
name|value
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|NumberFormatException
name|nfe
parameter_list|)
block|{
return|return
literal|false
return|;
block|}
comment|// Check min value
if|if
condition|(
name|minValue
operator|!=
literal|null
operator|&&
name|intval
operator|<
name|minValue
operator|.
name|intValue
argument_list|()
condition|)
block|{
return|return
literal|false
return|;
block|}
comment|// Check max value
if|if
condition|(
name|maxValue
operator|!=
literal|null
operator|&&
name|intval
operator|>
name|maxValue
operator|.
name|intValue
argument_list|()
condition|)
block|{
return|return
literal|false
return|;
block|}
block|}
return|return
literal|true
return|;
block|}
comment|/**      * Checks list for invalid dates      * @param lst      * @param ignoreNulls ignore null / blank values      * @return true if all checks pass, false otherwise      */
specifier|public
name|boolean
name|checkListDate
parameter_list|(
name|List
name|lst
parameter_list|,
name|boolean
name|ignoreNulls
parameter_list|)
block|{
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|lst
operator|.
name|size
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|String
name|value
init|=
operator|(
operator|(
name|String
operator|)
name|lst
operator|.
name|get
argument_list|(
name|i
argument_list|)
operator|)
decl_stmt|;
comment|// No selection made
if|if
condition|(
name|value
operator|==
literal|null
operator|||
name|value
operator|.
name|trim
argument_list|()
operator|.
name|equals
argument_list|(
name|Constants
operator|.
name|BLANK_OPTION_VALUE
argument_list|)
condition|)
block|{
if|if
condition|(
operator|!
name|ignoreNulls
condition|)
return|return
literal|false
return|;
else|else
continue|continue;
block|}
comment|// Check valid date
name|Date
name|dateval
init|=
literal|null
decl_stmt|;
name|SimpleDateFormat
name|sdf
init|=
operator|new
name|SimpleDateFormat
argument_list|(
literal|"MM/dd/yyyy"
argument_list|)
decl_stmt|;
name|sdf
operator|.
name|setLenient
argument_list|(
literal|false
argument_list|)
expr_stmt|;
try|try
block|{
name|dateval
operator|=
name|sdf
operator|.
name|parse
argument_list|(
name|value
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|ParseException
name|pe
parameter_list|)
block|{
return|return
literal|false
return|;
block|}
comment|// Check date is in future
name|Date
name|now
init|=
operator|new
name|Date
argument_list|()
decl_stmt|;
if|if
condition|(
name|dateval
operator|.
name|before
argument_list|(
name|now
argument_list|)
condition|)
return|return
literal|false
return|;
block|}
return|return
literal|true
return|;
block|}
block|}
end_class

end_unit

