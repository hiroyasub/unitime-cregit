begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.2 - 3.5 (University Timetabling Application)  * Copyright (C) 2010 - 2013, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
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
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|gwt
operator|.
name|command
operator|.
name|client
operator|.
name|GwtRpcRequest
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
name|gwt
operator|.
name|command
operator|.
name|client
operator|.
name|GwtRpcResponseList
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
class|class
name|PersonInterface
implements|implements
name|Comparable
argument_list|<
name|PersonInterface
argument_list|>
implements|,
name|IsSerializable
block|{
specifier|private
name|String
name|iId
decl_stmt|,
name|iFName
decl_stmt|,
name|iMName
decl_stmt|,
name|iLName
decl_stmt|,
name|iEmail
decl_stmt|,
name|iPhone
decl_stmt|,
name|iDept
decl_stmt|,
name|iPos
decl_stmt|,
name|iSource
decl_stmt|;
specifier|public
name|PersonInterface
parameter_list|()
block|{
block|}
specifier|public
name|PersonInterface
parameter_list|(
name|String
name|id
parameter_list|,
name|String
name|fname
parameter_list|,
name|String
name|mname
parameter_list|,
name|String
name|lname
parameter_list|,
name|String
name|email
parameter_list|,
name|String
name|phone
parameter_list|,
name|String
name|dept
parameter_list|,
name|String
name|pos
parameter_list|,
name|String
name|source
parameter_list|)
block|{
name|iId
operator|=
name|id
expr_stmt|;
name|iSource
operator|=
name|source
expr_stmt|;
name|iFName
operator|=
name|fname
expr_stmt|;
name|iMName
operator|=
name|mname
expr_stmt|;
name|iLName
operator|=
name|lname
expr_stmt|;
if|if
condition|(
name|iMName
operator|!=
literal|null
operator|&&
name|iFName
operator|!=
literal|null
operator|&&
name|iMName
operator|.
name|indexOf
argument_list|(
name|iFName
argument_list|)
operator|>=
literal|0
condition|)
name|iMName
operator|=
name|iMName
operator|.
name|replaceAll
argument_list|(
name|iFName
operator|+
literal|" ?"
argument_list|,
literal|""
argument_list|)
expr_stmt|;
if|if
condition|(
name|iMName
operator|!=
literal|null
operator|&&
name|iLName
operator|!=
literal|null
operator|&&
name|iMName
operator|.
name|indexOf
argument_list|(
name|iLName
argument_list|)
operator|>=
literal|0
condition|)
name|iMName
operator|=
name|iMName
operator|.
name|replaceAll
argument_list|(
literal|" ?"
operator|+
name|iLName
argument_list|,
literal|""
argument_list|)
expr_stmt|;
name|iEmail
operator|=
name|email
expr_stmt|;
name|iPhone
operator|=
name|phone
expr_stmt|;
name|iDept
operator|=
name|dept
expr_stmt|;
name|iPos
operator|=
name|pos
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
name|getSource
parameter_list|()
block|{
return|return
name|iSource
return|;
block|}
specifier|public
name|void
name|setSource
parameter_list|(
name|String
name|source
parameter_list|)
block|{
name|iSource
operator|=
name|source
expr_stmt|;
block|}
specifier|public
name|String
name|getFirstName
parameter_list|()
block|{
return|return
name|iFName
return|;
block|}
specifier|public
name|void
name|setFirstName
parameter_list|(
name|String
name|fname
parameter_list|)
block|{
name|iFName
operator|=
name|fname
expr_stmt|;
block|}
specifier|public
name|String
name|getMiddleName
parameter_list|()
block|{
return|return
name|iMName
return|;
block|}
specifier|public
name|void
name|setMiddleName
parameter_list|(
name|String
name|mname
parameter_list|)
block|{
name|iMName
operator|=
name|mname
expr_stmt|;
block|}
specifier|public
name|String
name|getLastName
parameter_list|()
block|{
return|return
name|iLName
return|;
block|}
specifier|public
name|void
name|setLastName
parameter_list|(
name|String
name|lname
parameter_list|)
block|{
name|iLName
operator|=
name|lname
expr_stmt|;
block|}
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
operator|(
operator|(
name|iLName
operator|==
literal|null
operator|||
name|iLName
operator|.
name|isEmpty
argument_list|()
condition|?
literal|""
else|:
name|iLName
operator|)
operator|+
operator|(
name|iFName
operator|==
literal|null
operator|||
name|iFName
operator|.
name|isEmpty
argument_list|()
condition|?
literal|""
else|:
literal|", "
operator|+
name|iFName
operator|)
operator|+
operator|(
name|iMName
operator|==
literal|null
operator|||
name|iMName
operator|.
name|isEmpty
argument_list|()
condition|?
literal|""
else|:
literal|" "
operator|+
name|iMName
operator|)
operator|)
operator|.
name|trim
argument_list|()
return|;
block|}
specifier|public
name|String
name|getShortName
parameter_list|()
block|{
name|String
name|name
init|=
literal|""
decl_stmt|;
if|if
condition|(
name|iFName
operator|!=
literal|null
operator|&&
operator|!
name|iFName
operator|.
name|isEmpty
argument_list|()
condition|)
name|name
operator|+=
name|iFName
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
literal|1
argument_list|)
operator|+
literal|" "
expr_stmt|;
if|if
condition|(
name|iMName
operator|!=
literal|null
operator|&&
operator|!
name|iMName
operator|.
name|isEmpty
argument_list|()
condition|)
name|name
operator|+=
name|iMName
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
literal|1
argument_list|)
operator|+
literal|" "
expr_stmt|;
if|if
condition|(
name|iLName
operator|!=
literal|null
operator|&&
operator|!
name|iLName
operator|.
name|isEmpty
argument_list|()
condition|)
name|name
operator|+=
name|iLName
expr_stmt|;
return|return
name|name
operator|.
name|trim
argument_list|()
return|;
block|}
specifier|public
name|String
name|getPhone
parameter_list|()
block|{
return|return
name|iPhone
return|;
block|}
specifier|public
name|void
name|setPhone
parameter_list|(
name|String
name|phone
parameter_list|)
block|{
name|iPhone
operator|=
name|phone
expr_stmt|;
block|}
specifier|public
name|String
name|getEmail
parameter_list|()
block|{
return|return
name|iEmail
return|;
block|}
specifier|public
name|void
name|setEmail
parameter_list|(
name|String
name|email
parameter_list|)
block|{
name|iEmail
operator|=
name|email
expr_stmt|;
block|}
specifier|public
name|String
name|getDepartment
parameter_list|()
block|{
return|return
name|iDept
return|;
block|}
specifier|public
name|void
name|setDepartment
parameter_list|(
name|String
name|dept
parameter_list|)
block|{
name|iDept
operator|=
name|dept
expr_stmt|;
block|}
specifier|public
name|String
name|getPosition
parameter_list|()
block|{
return|return
name|iPos
return|;
block|}
specifier|public
name|void
name|setPosition
parameter_list|(
name|String
name|pos
parameter_list|)
block|{
name|iPos
operator|=
name|pos
expr_stmt|;
block|}
specifier|public
name|int
name|compareTo
parameter_list|(
name|PersonInterface
name|p
parameter_list|)
block|{
name|int
name|cmp
init|=
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
operator|)
operator|.
name|compareToIgnoreCase
argument_list|(
name|p
operator|.
name|getLastName
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
name|p
operator|.
name|getLastName
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
name|getFirstName
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
name|getFirstName
argument_list|()
operator|)
operator|.
name|compareToIgnoreCase
argument_list|(
name|p
operator|.
name|getFirstName
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
name|p
operator|.
name|getFirstName
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
operator|)
operator|.
name|compareToIgnoreCase
argument_list|(
name|p
operator|.
name|getMiddleName
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
name|p
operator|.
name|getMiddleName
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
name|getSource
argument_list|()
operator|.
name|compareToIgnoreCase
argument_list|(
name|p
operator|.
name|getSource
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
if|if
condition|(
name|getId
argument_list|()
operator|!=
literal|null
condition|)
return|return
name|getId
argument_list|()
operator|.
name|compareTo
argument_list|(
name|p
operator|.
name|getId
argument_list|()
argument_list|)
return|;
if|if
condition|(
name|getId
argument_list|()
operator|==
literal|null
operator|&&
name|p
operator|.
name|getId
argument_list|()
operator|==
literal|null
condition|)
return|return
operator|(
literal|0
operator|)
return|;
return|return
operator|(
literal|1
operator|)
return|;
block|}
specifier|public
name|void
name|merge
parameter_list|(
name|PersonInterface
name|person
parameter_list|)
block|{
if|if
condition|(
name|iId
operator|==
literal|null
operator|||
name|iId
operator|.
name|isEmpty
argument_list|()
condition|)
name|iId
operator|=
name|person
operator|.
name|getId
argument_list|()
expr_stmt|;
if|if
condition|(
name|iFName
operator|==
literal|null
operator|||
name|iFName
operator|.
name|isEmpty
argument_list|()
condition|)
name|iFName
operator|=
name|person
operator|.
name|getFirstName
argument_list|()
expr_stmt|;
if|if
condition|(
name|iMName
operator|==
literal|null
operator|||
name|iMName
operator|.
name|isEmpty
argument_list|()
condition|)
name|iMName
operator|=
name|person
operator|.
name|getMiddleName
argument_list|()
expr_stmt|;
if|if
condition|(
name|iLName
operator|==
literal|null
operator|||
name|iLName
operator|.
name|isEmpty
argument_list|()
condition|)
name|iLName
operator|=
name|person
operator|.
name|getLastName
argument_list|()
expr_stmt|;
if|if
condition|(
name|iEmail
operator|==
literal|null
operator|||
name|iEmail
operator|.
name|isEmpty
argument_list|()
condition|)
name|iEmail
operator|=
name|person
operator|.
name|getEmail
argument_list|()
expr_stmt|;
if|if
condition|(
name|iPhone
operator|==
literal|null
operator|||
name|iPhone
operator|.
name|isEmpty
argument_list|()
condition|)
name|iPhone
operator|=
name|person
operator|.
name|getPhone
argument_list|()
expr_stmt|;
if|if
condition|(
name|iDept
operator|==
literal|null
operator|||
name|iDept
operator|.
name|isEmpty
argument_list|()
condition|)
name|iDept
operator|=
name|person
operator|.
name|getDepartment
argument_list|()
expr_stmt|;
if|if
condition|(
operator|!
name|iSource
operator|.
name|contains
argument_list|(
name|person
operator|.
name|getSource
argument_list|()
argument_list|)
condition|)
name|iSource
operator|+=
literal|", "
operator|+
name|person
operator|.
name|getSource
argument_list|()
expr_stmt|;
block|}
specifier|public
specifier|static
class|class
name|LookupRequest
implements|implements
name|GwtRpcRequest
argument_list|<
name|GwtRpcResponseList
argument_list|<
name|PersonInterface
argument_list|>
argument_list|>
block|{
specifier|private
name|String
name|iQuery
decl_stmt|,
name|iOptions
decl_stmt|;
specifier|public
name|LookupRequest
parameter_list|()
block|{
block|}
specifier|public
name|LookupRequest
parameter_list|(
name|String
name|query
parameter_list|,
name|String
name|options
parameter_list|)
block|{
name|iQuery
operator|=
name|query
expr_stmt|;
name|iOptions
operator|=
name|options
expr_stmt|;
block|}
specifier|public
name|String
name|getQuery
parameter_list|()
block|{
return|return
name|iQuery
return|;
block|}
specifier|public
name|void
name|setQuery
parameter_list|(
name|String
name|query
parameter_list|)
block|{
name|iQuery
operator|=
name|query
expr_stmt|;
block|}
specifier|public
name|String
name|getOptions
parameter_list|()
block|{
return|return
name|iOptions
return|;
block|}
specifier|public
name|void
name|setOptions
parameter_list|(
name|String
name|options
parameter_list|)
block|{
name|iOptions
operator|=
name|options
expr_stmt|;
block|}
specifier|public
name|boolean
name|hasOptions
parameter_list|()
block|{
return|return
name|iOptions
operator|!=
literal|null
operator|&&
operator|!
name|iOptions
operator|.
name|isEmpty
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
name|getQuery
argument_list|()
operator|+
operator|(
name|hasOptions
argument_list|()
condition|?
literal|" ("
operator|+
name|getOptions
argument_list|()
operator|+
literal|")"
else|:
literal|""
operator|)
return|;
block|}
block|}
block|}
end_class

end_unit

