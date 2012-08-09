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
name|ItypeDesc
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
name|ItypeDescDAO
import|;
end_import

begin_comment
comment|/**   *   * @author Tomas Muller  *   */
end_comment

begin_class
specifier|public
class|class
name|ItypeDescEditForm
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
literal|238147307633027599L
decl_stmt|;
specifier|private
name|Integer
name|iUniqueId
init|=
literal|null
decl_stmt|;
specifier|private
name|String
name|iId
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
name|iReference
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
name|int
name|iType
init|=
literal|1
decl_stmt|;
specifier|private
name|boolean
name|iOrganized
init|=
literal|false
decl_stmt|;
specifier|private
name|Integer
name|iParent
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
try|try
block|{
if|if
condition|(
name|iId
operator|==
literal|null
operator|||
name|iId
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
name|errors
operator|.
name|add
argument_list|(
literal|"id"
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
block|}
else|else
block|{
name|Integer
name|id
init|=
name|Integer
operator|.
name|valueOf
argument_list|(
name|iId
argument_list|)
decl_stmt|;
name|ItypeDesc
name|itype
init|=
operator|new
name|ItypeDescDAO
argument_list|()
operator|.
name|get
argument_list|(
name|id
argument_list|)
decl_stmt|;
if|if
condition|(
name|itype
operator|!=
literal|null
operator|&&
operator|(
name|iUniqueId
operator|==
literal|null
operator|||
name|iUniqueId
operator|<
literal|0
operator|||
name|itype
operator|.
name|equals
argument_list|(
name|iUniqueId
argument_list|)
operator|)
condition|)
name|errors
operator|.
name|add
argument_list|(
literal|"id"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.exists"
argument_list|,
name|iId
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
catch|catch
parameter_list|(
name|NumberFormatException
name|e
parameter_list|)
block|{
name|errors
operator|.
name|add
argument_list|(
literal|"id"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.numeric"
argument_list|,
name|iId
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
literal|"id"
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
name|iId
operator|=
literal|null
expr_stmt|;
name|iOp
operator|=
literal|null
expr_stmt|;
name|iUniqueId
operator|=
operator|-
literal|1
expr_stmt|;
name|iAbbreviation
operator|=
literal|null
expr_stmt|;
name|iReference
operator|=
literal|null
expr_stmt|;
name|iName
operator|=
literal|null
expr_stmt|;
name|iType
operator|=
literal|1
expr_stmt|;
name|iOrganized
operator|=
literal|false
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
name|Integer
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
name|Integer
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
name|String
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
name|getReference
parameter_list|()
block|{
return|return
name|iReference
return|;
block|}
specifier|public
name|void
name|setReference
parameter_list|(
name|String
name|reference
parameter_list|)
block|{
name|iReference
operator|=
name|reference
expr_stmt|;
block|}
specifier|public
name|String
name|getType
parameter_list|()
block|{
return|return
name|ItypeDesc
operator|.
name|sBasicTypes
index|[
name|iType
index|]
return|;
block|}
specifier|public
name|void
name|setType
parameter_list|(
name|String
name|type
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
name|ItypeDesc
operator|.
name|sBasicTypes
operator|.
name|length
condition|;
name|i
operator|++
control|)
if|if
condition|(
name|ItypeDesc
operator|.
name|sBasicTypes
index|[
name|i
index|]
operator|.
name|equals
argument_list|(
name|type
argument_list|)
condition|)
name|iType
operator|=
name|i
expr_stmt|;
block|}
specifier|public
name|int
name|getBasicType
parameter_list|()
block|{
return|return
name|iType
return|;
block|}
specifier|public
name|void
name|setBasicType
parameter_list|(
name|int
name|type
parameter_list|)
block|{
name|iType
operator|=
name|type
expr_stmt|;
block|}
specifier|public
name|String
index|[]
name|getTypes
parameter_list|()
block|{
return|return
name|ItypeDesc
operator|.
name|sBasicTypes
return|;
block|}
specifier|public
name|boolean
name|getOrganized
parameter_list|()
block|{
return|return
name|iOrganized
return|;
block|}
specifier|public
name|void
name|setOrganized
parameter_list|(
name|boolean
name|organized
parameter_list|)
block|{
name|iOrganized
operator|=
name|organized
expr_stmt|;
block|}
specifier|public
name|Integer
name|getParent
parameter_list|()
block|{
return|return
name|iParent
return|;
block|}
specifier|public
name|void
name|setParent
parameter_list|(
name|Integer
name|parent
parameter_list|)
block|{
name|iParent
operator|=
name|parent
expr_stmt|;
block|}
specifier|public
name|void
name|load
parameter_list|(
name|ItypeDesc
name|itype
parameter_list|)
block|{
name|setOp
argument_list|(
literal|"Update"
argument_list|)
expr_stmt|;
name|setId
argument_list|(
name|itype
operator|.
name|getItype
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
name|setUniqueId
argument_list|(
name|itype
operator|.
name|getItype
argument_list|()
argument_list|)
expr_stmt|;
name|setAbbreviation
argument_list|(
name|itype
operator|.
name|getAbbv
argument_list|()
argument_list|)
expr_stmt|;
name|setName
argument_list|(
name|itype
operator|.
name|getDesc
argument_list|()
argument_list|)
expr_stmt|;
name|setReference
argument_list|(
name|itype
operator|.
name|getSis_ref
argument_list|()
argument_list|)
expr_stmt|;
name|setBasicType
argument_list|(
name|itype
operator|.
name|getBasic
argument_list|()
argument_list|)
expr_stmt|;
name|setParent
argument_list|(
name|itype
operator|.
name|getParent
argument_list|()
operator|==
literal|null
condition|?
literal|null
else|:
name|itype
operator|.
name|getParent
argument_list|()
operator|.
name|getItype
argument_list|()
argument_list|)
expr_stmt|;
name|setOrganized
argument_list|(
name|itype
operator|.
name|isOrganized
argument_list|()
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|saveOrUpdate
parameter_list|(
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
parameter_list|)
throws|throws
name|Exception
block|{
name|ItypeDesc
name|itype
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|getUniqueId
argument_list|()
operator|!=
literal|null
condition|)
name|itype
operator|=
operator|new
name|ItypeDescDAO
argument_list|()
operator|.
name|get
argument_list|(
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|itype
operator|==
literal|null
condition|)
name|itype
operator|=
operator|new
name|ItypeDesc
argument_list|()
expr_stmt|;
name|itype
operator|.
name|setItype
argument_list|(
name|Integer
operator|.
name|valueOf
argument_list|(
name|getId
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|itype
operator|.
name|setAbbv
argument_list|(
name|getAbbreviation
argument_list|()
argument_list|)
expr_stmt|;
name|itype
operator|.
name|setDesc
argument_list|(
name|getName
argument_list|()
argument_list|)
expr_stmt|;
name|itype
operator|.
name|setSis_ref
argument_list|(
name|getReference
argument_list|()
argument_list|)
expr_stmt|;
name|itype
operator|.
name|setBasic
argument_list|(
name|getBasicType
argument_list|()
argument_list|)
expr_stmt|;
name|itype
operator|.
name|setParent
argument_list|(
name|getParent
argument_list|()
operator|==
literal|null
condition|?
literal|null
else|:
operator|new
name|ItypeDescDAO
argument_list|()
operator|.
name|get
argument_list|(
name|getParent
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|itype
operator|.
name|setOrganized
argument_list|(
name|getOrganized
argument_list|()
argument_list|)
expr_stmt|;
name|hibSession
operator|.
name|saveOrUpdate
argument_list|(
name|itype
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|delete
parameter_list|(
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
parameter_list|)
block|{
name|ItypeDesc
name|itype
init|=
operator|new
name|ItypeDescDAO
argument_list|()
operator|.
name|get
argument_list|(
name|getUniqueId
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|itype
operator|!=
literal|null
condition|)
name|hibSession
operator|.
name|delete
argument_list|(
name|itype
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

