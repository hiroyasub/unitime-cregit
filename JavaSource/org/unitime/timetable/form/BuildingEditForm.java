begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * Licensed to The Apereo Foundation under one or more contributor license  * agreements. See the NOTICE file distributed with this work for  * additional information regarding copyright ownership.  *  * The Apereo Foundation licenses this file to you under the Apache License,  * Version 2.0 (the "License"); you may not use this file except in  * compliance with the License. You may obtain a copy of the License at:  *  * http://www.apache.org/licenses/LICENSE-2.0  *  * Unless required by applicable law or agreed to in writing, software  * distributed under the License is distributed on an "AS IS" BASIS,  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  *  * See the License for the specific language governing permissions and  * limitations under the License.  *  */
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
name|timetable
operator|.
name|model
operator|.
name|Building
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
name|security
operator|.
name|SessionContext
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
name|security
operator|.
name|context
operator|.
name|HttpSessionContext
import|;
end_import

begin_comment
comment|/**   *   * @author Tomas Muller  *   */
end_comment

begin_class
specifier|public
class|class
name|BuildingEditForm
extends|extends
name|ActionForm
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
operator|-
literal|4104780400760573687L
decl_stmt|;
specifier|private
name|Long
name|iUniqueId
init|=
literal|null
decl_stmt|;
specifier|private
name|Long
name|iSessionId
init|=
literal|null
decl_stmt|;
specifier|private
name|String
name|iOp
init|=
literal|null
decl_stmt|;
specifier|private
name|String
name|iExternalId
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
name|iAbbreviation
init|=
literal|null
decl_stmt|;
specifier|private
name|String
name|iCoordX
init|=
literal|null
decl_stmt|,
name|iCoordY
init|=
literal|null
decl_stmt|;
specifier|private
name|Boolean
name|iUpdateRoomCoordinates
init|=
literal|null
decl_stmt|;
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
if|if
condition|(
name|iSessionId
operator|==
literal|null
operator|||
name|iSessionId
operator|<=
literal|0
condition|)
block|{
name|SessionContext
name|context
init|=
name|HttpSessionContext
operator|.
name|getSessionContext
argument_list|(
name|request
operator|.
name|getSession
argument_list|()
operator|.
name|getServletContext
argument_list|()
argument_list|)
decl_stmt|;
name|iSessionId
operator|=
operator|(
name|context
operator|.
name|isAuthenticated
argument_list|()
condition|?
name|context
operator|.
name|getUser
argument_list|()
operator|.
name|getCurrentAcademicSessionId
argument_list|()
else|:
literal|null
operator|)
expr_stmt|;
block|}
name|ActionErrors
name|errors
init|=
operator|new
name|ActionErrors
argument_list|()
decl_stmt|;
try|try
block|{
if|if
condition|(
name|iName
operator|==
literal|null
operator|||
name|iName
operator|.
name|trim
argument_list|()
operator|.
name|length
argument_list|()
operator|==
literal|0
condition|)
name|errors
operator|.
name|add
argument_list|(
literal|"name"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.required"
argument_list|,
literal|""
argument_list|)
argument_list|)
expr_stmt|;
else|else
block|{
try|try
block|{
name|Building
name|building
init|=
name|Building
operator|.
name|findByName
argument_list|(
name|iName
argument_list|,
name|iSessionId
argument_list|)
decl_stmt|;
if|if
condition|(
name|building
operator|!=
literal|null
operator|&&
operator|!
name|building
operator|.
name|getUniqueId
argument_list|()
operator|.
name|equals
argument_list|(
name|iUniqueId
argument_list|)
condition|)
name|errors
operator|.
name|add
argument_list|(
literal|"name"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.exists"
argument_list|,
name|iName
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
name|errors
operator|.
name|add
argument_list|(
literal|"name"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.generic"
argument_list|,
name|e
operator|.
name|getMessage
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
if|if
condition|(
name|iAbbreviation
operator|==
literal|null
operator|||
name|iAbbreviation
operator|.
name|trim
argument_list|()
operator|.
name|length
argument_list|()
operator|==
literal|0
condition|)
name|errors
operator|.
name|add
argument_list|(
literal|"abbreviation"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.required"
argument_list|,
literal|""
argument_list|)
argument_list|)
expr_stmt|;
else|else
block|{
try|try
block|{
name|Building
name|building
init|=
name|Building
operator|.
name|findByBldgAbbv
argument_list|(
name|iAbbreviation
argument_list|,
name|iSessionId
argument_list|)
decl_stmt|;
if|if
condition|(
name|building
operator|!=
literal|null
operator|&&
operator|!
name|building
operator|.
name|getUniqueId
argument_list|()
operator|.
name|equals
argument_list|(
name|iUniqueId
argument_list|)
condition|)
name|errors
operator|.
name|add
argument_list|(
literal|"abbreviation"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.exists"
argument_list|,
name|iAbbreviation
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
name|errors
operator|.
name|add
argument_list|(
literal|"abbreviation"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.generic"
argument_list|,
name|e
operator|.
name|getMessage
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
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
name|errors
operator|.
name|add
argument_list|(
literal|"name"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.generic"
argument_list|,
name|e
operator|.
name|getMessage
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
return|return
name|errors
return|;
block|}
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
name|iUniqueId
operator|=
literal|null
expr_stmt|;
name|iAbbreviation
operator|=
literal|null
expr_stmt|;
name|iOp
operator|=
literal|null
expr_stmt|;
name|iExternalId
operator|=
literal|null
expr_stmt|;
name|iName
operator|=
literal|null
expr_stmt|;
name|iCoordX
operator|=
literal|null
expr_stmt|;
name|iCoordY
operator|=
literal|null
expr_stmt|;
name|iUpdateRoomCoordinates
operator|=
literal|null
expr_stmt|;
block|}
specifier|public
name|String
name|getOp
parameter_list|()
block|{
return|return
name|iOp
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
name|iOp
operator|=
name|op
expr_stmt|;
block|}
specifier|public
name|Long
name|getUniqueId
parameter_list|()
block|{
return|return
name|iUniqueId
return|;
block|}
specifier|public
name|void
name|setUniqueId
parameter_list|(
name|Long
name|uniqueId
parameter_list|)
block|{
name|iUniqueId
operator|=
name|uniqueId
expr_stmt|;
block|}
specifier|public
name|String
name|getExternalId
parameter_list|()
block|{
return|return
name|iExternalId
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
name|iExternalId
operator|=
name|externalId
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
name|getAbbreviation
parameter_list|()
block|{
return|return
name|iAbbreviation
return|;
block|}
specifier|public
name|void
name|setAbbreviation
parameter_list|(
name|String
name|abbreviation
parameter_list|)
block|{
name|iAbbreviation
operator|=
name|abbreviation
expr_stmt|;
block|}
specifier|public
name|String
name|getCoordX
parameter_list|()
block|{
return|return
name|iCoordX
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
name|iCoordX
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
name|iCoordY
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
name|iCoordY
operator|=
name|coordY
expr_stmt|;
block|}
specifier|public
name|Long
name|getSessionId
parameter_list|()
block|{
return|return
name|iSessionId
return|;
block|}
specifier|public
name|void
name|setSessionId
parameter_list|(
name|Long
name|sessionId
parameter_list|)
block|{
name|iSessionId
operator|=
name|sessionId
expr_stmt|;
block|}
specifier|public
name|void
name|setUpdateRoomCoordinates
parameter_list|(
name|Boolean
name|updateRoomCoordinates
parameter_list|)
block|{
name|iUpdateRoomCoordinates
operator|=
name|updateRoomCoordinates
expr_stmt|;
block|}
specifier|public
name|Boolean
name|getUpdateRoomCoordinates
parameter_list|()
block|{
return|return
name|iUpdateRoomCoordinates
return|;
block|}
specifier|public
name|void
name|load
parameter_list|(
name|Building
name|building
parameter_list|)
block|{
name|setOp
argument_list|(
literal|"Update"
argument_list|)
expr_stmt|;
name|setUniqueId
argument_list|(
name|building
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
name|setSessionId
argument_list|(
name|building
operator|.
name|getSession
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
name|setExternalId
argument_list|(
name|building
operator|.
name|getExternalUniqueId
argument_list|()
argument_list|)
expr_stmt|;
name|setName
argument_list|(
name|building
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
name|setAbbreviation
argument_list|(
name|building
operator|.
name|getAbbreviation
argument_list|()
argument_list|)
expr_stmt|;
name|setCoordX
argument_list|(
name|building
operator|.
name|getCoordinateX
argument_list|()
operator|==
literal|null
condition|?
literal|null
else|:
name|building
operator|.
name|getCoordinateX
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
name|setCoordY
argument_list|(
name|building
operator|.
name|getCoordinateY
argument_list|()
operator|==
literal|null
condition|?
literal|null
else|:
name|building
operator|.
name|getCoordinateY
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
name|setUpdateRoomCoordinates
argument_list|(
literal|false
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

