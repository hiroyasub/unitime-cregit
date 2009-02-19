begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.1 (University Timetabling Application)  * Copyright (C) 2008-2009, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 2 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program; if not, write to the Free Software Foundation, Inc.,  * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
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
name|regex
operator|.
name|Matcher
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|regex
operator|.
name|Pattern
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
name|model
operator|.
name|Room
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
name|RoomType
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
name|Session
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
name|RoomDAO
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
name|WebTextValidation
import|;
end_import

begin_comment
comment|/**   * MyEclipse Struts  * Creation date: 07-05-2006  *   * XDoclet definition:  * @struts.form name="editRoomForm"  */
end_comment

begin_class
specifier|public
class|class
name|EditRoomForm
extends|extends
name|ActionForm
block|{
comment|/** 	 *  	 */
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|9208856268545264291L
decl_stmt|;
comment|// --------------------------------------------------------- Instance Variables
specifier|private
name|String
name|doit
decl_stmt|;
specifier|private
name|String
name|id
decl_stmt|;
specifier|private
name|String
name|name
decl_stmt|;
specifier|private
name|String
name|capacity
decl_stmt|;
specifier|private
name|Boolean
name|ignoreTooFar
decl_stmt|;
specifier|private
name|Boolean
name|ignoreRoomCheck
decl_stmt|;
specifier|private
name|String
name|controlDept
decl_stmt|;
specifier|private
name|String
name|bldgName
decl_stmt|;
specifier|private
name|String
name|bldgId
decl_stmt|;
specifier|private
name|String
name|coordX
decl_stmt|,
name|coordY
decl_stmt|;
specifier|private
name|String
name|externalId
decl_stmt|;
specifier|private
name|Long
name|type
decl_stmt|;
specifier|private
name|boolean
name|owner
decl_stmt|;
specifier|private
name|boolean
name|room
decl_stmt|;
specifier|private
name|Boolean
name|examEnabled
decl_stmt|;
specifier|private
name|Boolean
name|examEEnabled
decl_stmt|;
specifier|private
name|String
name|examCapacity
decl_stmt|;
comment|// --------------------------------------------------------- Methods
specifier|public
name|Boolean
name|getIgnoreTooFar
parameter_list|()
block|{
return|return
name|ignoreTooFar
return|;
block|}
specifier|public
name|Boolean
name|getIgnoreRoomCheck
parameter_list|()
block|{
return|return
name|ignoreRoomCheck
return|;
block|}
specifier|public
name|String
name|getCapacity
parameter_list|()
block|{
return|return
name|capacity
return|;
block|}
specifier|public
name|void
name|setCapacity
parameter_list|(
name|String
name|capacity
parameter_list|)
block|{
name|this
operator|.
name|capacity
operator|=
name|capacity
expr_stmt|;
block|}
specifier|public
name|Boolean
name|isIgnoreTooFar
parameter_list|()
block|{
return|return
name|ignoreTooFar
return|;
block|}
specifier|public
name|void
name|setIgnoreTooFar
parameter_list|(
name|Boolean
name|ignoreTooFar
parameter_list|)
block|{
name|this
operator|.
name|ignoreTooFar
operator|=
name|ignoreTooFar
expr_stmt|;
block|}
specifier|public
name|Boolean
name|isIgnoreRoomCheck
parameter_list|()
block|{
return|return
name|ignoreRoomCheck
return|;
block|}
specifier|public
name|void
name|setIgnoreRoomCheck
parameter_list|(
name|Boolean
name|ignoreRoomCheck
parameter_list|)
block|{
name|this
operator|.
name|ignoreRoomCheck
operator|=
name|ignoreRoomCheck
expr_stmt|;
block|}
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
name|name
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
name|this
operator|.
name|name
operator|=
name|name
expr_stmt|;
block|}
specifier|public
name|void
name|setControlDept
parameter_list|(
name|String
name|controlDept
parameter_list|)
block|{
name|this
operator|.
name|controlDept
operator|=
name|controlDept
expr_stmt|;
block|}
specifier|public
name|String
name|getControlDept
parameter_list|()
block|{
return|return
name|controlDept
return|;
block|}
specifier|public
name|String
name|getBldgName
parameter_list|()
block|{
return|return
name|bldgName
return|;
block|}
specifier|public
name|void
name|setBldgName
parameter_list|(
name|String
name|bldgName
parameter_list|)
block|{
name|this
operator|.
name|bldgName
operator|=
name|bldgName
expr_stmt|;
block|}
specifier|public
name|String
name|getCoordX
parameter_list|()
block|{
return|return
name|coordX
return|;
block|}
specifier|public
name|void
name|setCoordX
parameter_list|(
name|String
name|coordX
parameter_list|)
block|{
name|this
operator|.
name|coordX
operator|=
name|coordX
expr_stmt|;
block|}
specifier|public
name|String
name|getCoordY
parameter_list|()
block|{
return|return
name|coordY
return|;
block|}
specifier|public
name|void
name|setCoordY
parameter_list|(
name|String
name|coordY
parameter_list|)
block|{
name|this
operator|.
name|coordY
operator|=
name|coordY
expr_stmt|;
block|}
specifier|public
name|String
name|getId
parameter_list|()
block|{
return|return
name|id
return|;
block|}
specifier|public
name|void
name|setId
parameter_list|(
name|String
name|id
parameter_list|)
block|{
name|this
operator|.
name|id
operator|=
name|id
expr_stmt|;
block|}
specifier|public
name|String
name|getDoit
parameter_list|()
block|{
return|return
name|doit
return|;
block|}
specifier|public
name|void
name|setDoit
parameter_list|(
name|String
name|doit
parameter_list|)
block|{
name|this
operator|.
name|doit
operator|=
name|doit
expr_stmt|;
block|}
specifier|public
name|boolean
name|isOwner
parameter_list|()
block|{
return|return
name|owner
return|;
block|}
specifier|public
name|void
name|setOwner
parameter_list|(
name|boolean
name|owner
parameter_list|)
block|{
name|this
operator|.
name|owner
operator|=
name|owner
expr_stmt|;
block|}
specifier|public
name|boolean
name|isRoom
parameter_list|()
block|{
return|return
name|room
return|;
block|}
specifier|public
name|void
name|setRoom
parameter_list|(
name|boolean
name|room
parameter_list|)
block|{
name|this
operator|.
name|room
operator|=
name|room
expr_stmt|;
block|}
specifier|public
name|String
name|getExternalId
parameter_list|()
block|{
return|return
name|externalId
return|;
block|}
specifier|public
name|void
name|setExternalId
parameter_list|(
name|String
name|externalId
parameter_list|)
block|{
name|this
operator|.
name|externalId
operator|=
name|externalId
expr_stmt|;
block|}
specifier|public
name|Long
name|getType
parameter_list|()
block|{
return|return
name|type
return|;
block|}
specifier|public
name|void
name|setType
parameter_list|(
name|Long
name|type
parameter_list|)
block|{
name|this
operator|.
name|type
operator|=
name|type
expr_stmt|;
block|}
specifier|public
name|String
name|getBldgId
parameter_list|()
block|{
return|return
name|bldgId
return|;
block|}
specifier|public
name|void
name|setBldgId
parameter_list|(
name|String
name|bldgId
parameter_list|)
block|{
name|this
operator|.
name|bldgId
operator|=
name|bldgId
expr_stmt|;
block|}
comment|/**  	 * Method validate 	 * @param mapping 	 * @param request 	 * @return ActionErrors 	 */
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
operator|(
name|id
operator|==
literal|null
operator|||
name|id
operator|.
name|length
argument_list|()
operator|==
literal|0
operator|)
operator|&&
operator|(
name|bldgId
operator|==
literal|null
operator|||
name|bldgId
operator|.
name|length
argument_list|()
operator|==
literal|0
operator|)
condition|)
block|{
name|errors
operator|.
name|add
argument_list|(
literal|"Building"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.required"
argument_list|,
literal|"Building"
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|name
operator|==
literal|null
operator|||
name|name
operator|.
name|equalsIgnoreCase
argument_list|(
literal|""
argument_list|)
condition|)
block|{
name|errors
operator|.
name|add
argument_list|(
literal|"Name"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.required"
argument_list|,
literal|"Name"
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
operator|!
name|room
operator|&&
name|name
operator|!=
literal|null
operator|&&
name|name
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
block|{
name|Debug
operator|.
name|info
argument_list|(
literal|"checking location regex 2"
argument_list|)
expr_stmt|;
name|String
name|nonUniversityLocationRegex
init|=
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"tmtbl.nonUniversityLocation.pattern"
argument_list|)
decl_stmt|;
name|String
name|nonUniversityLocationInfo
init|=
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"tmtbl.nonUniversityLocation.patternInfo"
argument_list|)
decl_stmt|;
if|if
condition|(
name|nonUniversityLocationRegex
operator|!=
literal|null
operator|&&
name|nonUniversityLocationRegex
operator|.
name|trim
argument_list|()
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
block|{
try|try
block|{
name|Pattern
name|pattern
init|=
name|Pattern
operator|.
name|compile
argument_list|(
name|nonUniversityLocationRegex
argument_list|)
decl_stmt|;
name|Matcher
name|matcher
init|=
name|pattern
operator|.
name|matcher
argument_list|(
name|name
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|matcher
operator|.
name|find
argument_list|()
condition|)
block|{
name|errors
operator|.
name|add
argument_list|(
literal|"nonUniversityLocation"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.generic"
argument_list|,
name|nonUniversityLocationInfo
argument_list|)
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
name|errors
operator|.
name|add
argument_list|(
literal|"nonUniversityLocation"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.generic"
argument_list|,
literal|"Non University Location cannot be matched to regular expression: "
operator|+
name|nonUniversityLocationRegex
operator|+
literal|". Reason: "
operator|+
name|e
operator|.
name|getMessage
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
if|if
condition|(
name|WebTextValidation
operator|.
name|isTextValid
argument_list|(
name|name
argument_list|,
literal|true
argument_list|)
condition|)
block|{
name|errors
operator|.
name|add
argument_list|(
literal|"nonUniversityLocation"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.invalidCharacters"
argument_list|,
literal|"Name"
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
if|if
condition|(
name|room
operator|&&
name|name
operator|!=
literal|null
operator|&&
name|name
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
block|{
if|if
condition|(
name|id
operator|==
literal|null
operator|||
name|id
operator|.
name|length
argument_list|()
operator|==
literal|0
condition|)
block|{
if|if
condition|(
name|bldgId
operator|!=
literal|null
operator|&&
name|bldgId
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
block|{
try|try
block|{
name|Room
name|room
init|=
name|Room
operator|.
name|findByBldgIdRoomNbr
argument_list|(
name|Long
operator|.
name|valueOf
argument_list|(
name|bldgId
argument_list|)
argument_list|,
name|name
argument_list|,
name|Session
operator|.
name|getCurrentAcadSession
argument_list|(
name|Web
operator|.
name|getUser
argument_list|(
name|request
operator|.
name|getSession
argument_list|()
argument_list|)
argument_list|)
operator|.
name|getUniqueId
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|room
operator|!=
literal|null
condition|)
name|errors
operator|.
name|add
argument_list|(
literal|"Name"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.exists"
argument_list|,
name|room
operator|.
name|getLabel
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|e
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
block|}
block|}
else|else
block|{
try|try
block|{
name|Room
name|room
init|=
name|Room
operator|.
name|findByBldgIdRoomNbr
argument_list|(
operator|new
name|RoomDAO
argument_list|()
operator|.
name|get
argument_list|(
name|Long
operator|.
name|valueOf
argument_list|(
name|id
argument_list|)
argument_list|)
operator|.
name|getBuilding
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|,
name|name
argument_list|,
name|Session
operator|.
name|getCurrentAcadSession
argument_list|(
name|Web
operator|.
name|getUser
argument_list|(
name|request
operator|.
name|getSession
argument_list|()
argument_list|)
argument_list|)
operator|.
name|getUniqueId
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|room
operator|!=
literal|null
operator|&&
operator|!
name|room
operator|.
name|getUniqueId
argument_list|()
operator|.
name|toString
argument_list|()
operator|.
name|equals
argument_list|(
name|id
argument_list|)
condition|)
name|errors
operator|.
name|add
argument_list|(
literal|"Name"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.exists"
argument_list|,
name|room
operator|.
name|getLabel
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
block|}
block|}
block|}
if|if
condition|(
name|capacity
operator|==
literal|null
operator|||
name|capacity
operator|.
name|equalsIgnoreCase
argument_list|(
literal|""
argument_list|)
condition|)
block|{
name|errors
operator|.
name|add
argument_list|(
literal|"Capacity"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.required"
argument_list|,
literal|"Capacity"
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|examCapacity
operator|==
literal|null
operator|||
name|examCapacity
operator|.
name|equalsIgnoreCase
argument_list|(
literal|""
argument_list|)
condition|)
block|{
name|errors
operator|.
name|add
argument_list|(
literal|"examCapacity"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.required"
argument_list|,
literal|"Examination Seating Capacity"
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|/*         if(room&& coordX==null || coordX.equalsIgnoreCase("") || coordY==null || coordY.equalsIgnoreCase("")) {             errors.add("Coordinates",                      new ActionMessage("errors.required", "Coordinates") );         }         */
return|return
name|errors
return|;
block|}
comment|/**  	 * Method reset 	 * @param mapping 	 * @param request 	 */
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
name|bldgName
operator|=
literal|null
expr_stmt|;
name|capacity
operator|=
literal|null
expr_stmt|;
name|coordX
operator|=
literal|null
expr_stmt|;
name|coordY
operator|=
literal|null
expr_stmt|;
name|doit
operator|=
literal|null
expr_stmt|;
name|externalId
operator|=
literal|null
expr_stmt|;
name|id
operator|=
literal|null
expr_stmt|;
name|name
operator|=
literal|null
expr_stmt|;
name|owner
operator|=
literal|false
expr_stmt|;
name|room
operator|=
literal|true
expr_stmt|;
name|type
operator|=
literal|null
expr_stmt|;
name|bldgId
operator|=
literal|null
expr_stmt|;
name|ignoreTooFar
operator|=
name|Boolean
operator|.
name|FALSE
expr_stmt|;
name|ignoreRoomCheck
operator|=
name|Boolean
operator|.
name|FALSE
expr_stmt|;
name|examEnabled
operator|=
name|Boolean
operator|.
name|FALSE
expr_stmt|;
name|examEEnabled
operator|=
name|Boolean
operator|.
name|FALSE
expr_stmt|;
name|examCapacity
operator|=
literal|null
expr_stmt|;
block|}
specifier|public
name|Boolean
name|getExamEnabled
parameter_list|()
block|{
return|return
name|examEnabled
return|;
block|}
specifier|public
name|void
name|setExamEnabled
parameter_list|(
name|Boolean
name|examEnabled
parameter_list|)
block|{
name|this
operator|.
name|examEnabled
operator|=
name|examEnabled
expr_stmt|;
block|}
specifier|public
name|Boolean
name|getExamEEnabled
parameter_list|()
block|{
return|return
name|examEEnabled
return|;
block|}
specifier|public
name|void
name|setExamEEnabled
parameter_list|(
name|Boolean
name|examEEnabled
parameter_list|)
block|{
name|this
operator|.
name|examEEnabled
operator|=
name|examEEnabled
expr_stmt|;
block|}
specifier|public
name|String
name|getExamCapacity
parameter_list|()
block|{
return|return
name|examCapacity
return|;
block|}
specifier|public
name|void
name|setExamCapacity
parameter_list|(
name|String
name|examCapacity
parameter_list|)
block|{
name|this
operator|.
name|examCapacity
operator|=
name|examCapacity
expr_stmt|;
block|}
specifier|public
name|Set
argument_list|<
name|RoomType
argument_list|>
name|getRoomTypes
parameter_list|()
block|{
return|return
name|RoomType
operator|.
name|findAll
argument_list|(
name|room
argument_list|)
return|;
block|}
block|}
end_class

end_unit

