begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.4 - 3.5 (University Timetabling Application)  * Copyright (C) 2012 - 2013, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *   */
end_comment

begin_package
package|package
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|security
operator|.
name|authority
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
name|HashSet
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
name|java
operator|.
name|util
operator|.
name|Set
import|;
end_import

begin_import
import|import
name|org
operator|.
name|springframework
operator|.
name|security
operator|.
name|core
operator|.
name|GrantedAuthority
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
name|Qualifiable
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
name|UserAuthority
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
name|UserQualifier
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
name|qualifiers
operator|.
name|SimpleQualifier
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
name|rights
operator|.
name|HasRights
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
name|rights
operator|.
name|Right
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
specifier|abstract
class|class
name|AbstractAuthority
implements|implements
name|UserAuthority
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|1L
decl_stmt|;
specifier|private
name|Long
name|iUniqueId
decl_stmt|;
specifier|private
name|String
name|iRole
decl_stmt|;
specifier|private
name|String
name|iLabel
decl_stmt|;
specifier|private
name|List
argument_list|<
name|UserQualifier
argument_list|>
name|iQualifiers
init|=
operator|new
name|ArrayList
argument_list|<
name|UserQualifier
argument_list|>
argument_list|()
decl_stmt|;
specifier|private
name|UserQualifier
name|iSession
init|=
literal|null
decl_stmt|;
specifier|private
name|Set
argument_list|<
name|Right
argument_list|>
name|iRights
init|=
operator|new
name|HashSet
argument_list|<
name|Right
argument_list|>
argument_list|()
decl_stmt|;
specifier|public
name|AbstractAuthority
parameter_list|(
name|Long
name|uniqueId
parameter_list|,
name|String
name|role
parameter_list|,
name|String
name|label
parameter_list|,
name|HasRights
name|permissions
parameter_list|)
block|{
name|iUniqueId
operator|=
name|uniqueId
expr_stmt|;
name|iRole
operator|=
name|role
expr_stmt|;
name|iLabel
operator|=
name|label
expr_stmt|;
for|for
control|(
name|Right
name|right
range|:
name|Right
operator|.
name|values
argument_list|()
control|)
if|if
condition|(
name|permissions
operator|.
name|hasRight
argument_list|(
name|right
argument_list|)
condition|)
name|iRights
operator|.
name|add
argument_list|(
name|right
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|Long
name|getUniqueId
parameter_list|()
block|{
return|return
name|iUniqueId
return|;
block|}
annotation|@
name|Override
specifier|public
name|UserQualifier
name|getAcademicSession
parameter_list|()
block|{
return|return
name|iSession
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getLabel
parameter_list|()
block|{
return|return
name|iLabel
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getRole
parameter_list|()
block|{
return|return
name|iRole
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getAuthority
parameter_list|()
block|{
name|UserQualifier
name|session
init|=
name|getAcademicSession
argument_list|()
decl_stmt|;
return|return
operator|(
name|getRole
argument_list|()
operator|+
operator|(
name|session
operator|==
literal|null
condition|?
literal|""
else|:
literal|"_"
operator|+
name|session
operator|.
name|getQualifierReference
argument_list|()
operator|)
operator|)
operator|.
name|toUpperCase
argument_list|()
operator|.
name|replace
argument_list|(
literal|' '
argument_list|,
literal|'_'
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|hasRight
parameter_list|(
name|Right
name|right
parameter_list|)
block|{
return|return
name|iRights
operator|.
name|contains
argument_list|(
name|right
argument_list|)
return|;
block|}
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
name|getAuthority
argument_list|()
operator|+
literal|" "
operator|+
name|getQualifiers
argument_list|()
return|;
block|}
specifier|public
name|int
name|hashCode
parameter_list|()
block|{
return|return
name|getAuthority
argument_list|()
operator|.
name|hashCode
argument_list|()
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
name|GrantedAuthority
operator|)
condition|)
return|return
literal|false
return|;
return|return
name|getAuthority
argument_list|()
operator|.
name|equals
argument_list|(
operator|(
operator|(
name|GrantedAuthority
operator|)
name|o
operator|)
operator|.
name|getAuthority
argument_list|()
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|List
argument_list|<
name|?
extends|extends
name|UserQualifier
argument_list|>
name|getQualifiers
parameter_list|()
block|{
return|return
name|iQualifiers
return|;
block|}
annotation|@
name|Override
specifier|public
name|List
argument_list|<
name|?
extends|extends
name|UserQualifier
argument_list|>
name|getQualifiers
parameter_list|(
name|String
name|type
parameter_list|)
block|{
name|List
argument_list|<
name|UserQualifier
argument_list|>
name|ret
init|=
operator|new
name|ArrayList
argument_list|<
name|UserQualifier
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|UserQualifier
name|q
range|:
name|getQualifiers
argument_list|()
control|)
if|if
condition|(
name|type
operator|==
literal|null
operator|||
name|type
operator|.
name|equals
argument_list|(
name|q
operator|.
name|getQualifierType
argument_list|()
argument_list|)
condition|)
name|ret
operator|.
name|add
argument_list|(
name|q
argument_list|)
expr_stmt|;
return|return
name|ret
return|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|hasQualifier
parameter_list|(
name|Qualifiable
name|qualifiable
parameter_list|)
block|{
return|return
name|getQualifier
argument_list|(
name|qualifiable
argument_list|)
operator|!=
literal|null
return|;
block|}
annotation|@
name|Override
specifier|public
name|UserQualifier
name|getQualifier
parameter_list|(
name|Qualifiable
name|qualifiable
parameter_list|)
block|{
for|for
control|(
name|UserQualifier
name|q
range|:
name|getQualifiers
argument_list|()
control|)
if|if
condition|(
name|q
operator|.
name|equals
argument_list|(
name|qualifiable
argument_list|)
condition|)
return|return
name|q
return|;
return|return
literal|null
return|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|addQualifier
parameter_list|(
name|UserQualifier
name|qualifier
parameter_list|)
block|{
if|if
condition|(
literal|"Session"
operator|.
name|equalsIgnoreCase
argument_list|(
name|qualifier
operator|.
name|getQualifierType
argument_list|()
argument_list|)
condition|)
name|iSession
operator|=
name|qualifier
expr_stmt|;
name|iQualifiers
operator|.
name|add
argument_list|(
name|qualifier
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|addQualifier
parameter_list|(
name|Qualifiable
name|qualifiable
parameter_list|)
block|{
name|addQualifier
argument_list|(
operator|new
name|SimpleQualifier
argument_list|(
name|qualifiable
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

