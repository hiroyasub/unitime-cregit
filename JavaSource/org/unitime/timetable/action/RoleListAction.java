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
name|action
package|;
end_package

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
name|Set
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
name|javax
operator|.
name|servlet
operator|.
name|http
operator|.
name|HttpServletResponse
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
name|Action
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
name|ActionForward
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
name|springframework
operator|.
name|beans
operator|.
name|factory
operator|.
name|annotation
operator|.
name|Autowired
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
name|context
operator|.
name|SecurityContextHolder
import|;
end_import

begin_import
import|import
name|org
operator|.
name|springframework
operator|.
name|stereotype
operator|.
name|Service
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
name|MultiComparable
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
name|WebTable
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
name|defaults
operator|.
name|SessionAttribute
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
name|form
operator|.
name|RoleListForm
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
name|Roles
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
name|SessionDAO
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
name|UserContext
import|;
end_import

begin_comment
comment|/**  * MyEclipse Struts  * Creation date: 03-17-2005  *  * XDoclet definition:  * @struts:action path="/selectPrimaryRole" name="roleListForm" input="/selectPrimaryRole.jsp" scope="request" validate="true"  * @struts:action-forward name="success" path="/main.jsp" contextRelative="true"  * @struts:action-forward name="fail" path="/selectPrimaryRole.jsp" contextRelative="true"  */
end_comment

begin_class
annotation|@
name|Service
argument_list|(
literal|"/selectPrimaryRole"
argument_list|)
specifier|public
class|class
name|RoleListAction
extends|extends
name|Action
block|{
annotation|@
name|Autowired
name|SessionContext
name|sessionContext
decl_stmt|;
comment|/**      * Method execute      * @param mapping      * @param form      * @param request      * @param response      * @return ActionForward      */
specifier|public
name|ActionForward
name|execute
parameter_list|(
name|ActionMapping
name|mapping
parameter_list|,
name|ActionForm
name|form
parameter_list|,
name|HttpServletRequest
name|request
parameter_list|,
name|HttpServletResponse
name|response
parameter_list|)
throws|throws
name|Exception
block|{
name|RoleListForm
name|roleListForm
init|=
operator|(
name|RoleListForm
operator|)
name|form
decl_stmt|;
name|UserContext
name|user
init|=
literal|null
decl_stmt|;
try|try
block|{
name|user
operator|=
operator|(
name|UserContext
operator|)
name|SecurityContextHolder
operator|.
name|getContext
argument_list|()
operator|.
name|getAuthentication
argument_list|()
operator|.
name|getPrincipal
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
block|}
if|if
condition|(
name|user
operator|==
literal|null
condition|)
return|return
name|mapping
operator|.
name|findForward
argument_list|(
literal|"loginRequired"
argument_list|)
return|;
if|if
condition|(
name|user
operator|.
name|getAuthorities
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
return|return
name|mapping
operator|.
name|findForward
argument_list|(
literal|"norole"
argument_list|)
return|;
comment|// Form submitted
if|if
condition|(
name|roleListForm
operator|.
name|getAuthority
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|UserAuthority
name|authority
init|=
name|user
operator|.
name|getAuthority
argument_list|(
name|roleListForm
operator|.
name|getAuthority
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|authority
operator|!=
literal|null
condition|)
block|{
name|user
operator|.
name|setCurrentAuthority
argument_list|(
name|authority
argument_list|)
expr_stmt|;
for|for
control|(
name|SessionAttribute
name|s
range|:
name|SessionAttribute
operator|.
name|values
argument_list|()
control|)
name|sessionContext
operator|.
name|removeAttribute
argument_list|(
name|s
argument_list|)
expr_stmt|;
block|}
return|return
name|mapping
operator|.
name|findForward
argument_list|(
literal|"success"
argument_list|)
return|;
block|}
name|UserAuthority
name|authority
init|=
name|setupAuthorities
argument_list|(
name|request
argument_list|,
name|user
argument_list|)
decl_stmt|;
comment|// Role/session list not requested -- try assign default role/session first
if|if
condition|(
operator|!
literal|"Y"
operator|.
name|equals
argument_list|(
name|request
operator|.
name|getParameter
argument_list|(
literal|"list"
argument_list|)
argument_list|)
operator|&&
name|authority
operator|!=
literal|null
condition|)
block|{
name|user
operator|.
name|setCurrentAuthority
argument_list|(
name|authority
argument_list|)
expr_stmt|;
return|return
name|mapping
operator|.
name|findForward
argument_list|(
literal|"success"
argument_list|)
return|;
block|}
name|Set
argument_list|<
name|String
argument_list|>
name|roles
init|=
operator|new
name|HashSet
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|UserAuthority
name|a
range|:
name|user
operator|.
name|getAuthorities
argument_list|()
control|)
name|roles
operator|.
name|add
argument_list|(
name|a
operator|.
name|getRole
argument_list|()
argument_list|)
expr_stmt|;
switch|switch
condition|(
name|roles
operator|.
name|size
argument_list|()
condition|)
block|{
case|case
literal|0
case|:
return|return
name|mapping
operator|.
name|findForward
argument_list|(
literal|"norole"
argument_list|)
return|;
case|case
literal|1
case|:
return|return
name|mapping
operator|.
name|findForward
argument_list|(
literal|"getDefaultAcadSession"
argument_list|)
return|;
default|default:
return|return
name|mapping
operator|.
name|findForward
argument_list|(
literal|"getUserSelectedRole"
argument_list|)
return|;
block|}
block|}
specifier|private
name|UserAuthority
name|setupAuthorities
parameter_list|(
name|HttpServletRequest
name|request
parameter_list|,
name|UserContext
name|user
parameter_list|)
block|{
name|WebTable
operator|.
name|setOrder
argument_list|(
name|sessionContext
argument_list|,
literal|"roleLists.ord"
argument_list|,
name|request
operator|.
name|getParameter
argument_list|(
literal|"ord"
argument_list|)
argument_list|,
operator|-
literal|2
argument_list|)
expr_stmt|;
name|Set
argument_list|<
name|String
argument_list|>
name|roles
init|=
operator|new
name|HashSet
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|UserAuthority
name|authority
range|:
name|user
operator|.
name|getAuthorities
argument_list|()
control|)
name|roles
operator|.
name|add
argument_list|(
name|authority
operator|.
name|getRole
argument_list|()
argument_list|)
expr_stmt|;
name|WebTable
name|table
init|=
operator|new
name|WebTable
argument_list|(
literal|4
argument_list|,
literal|"Select "
operator|+
operator|(
name|roles
operator|.
name|size
argument_list|()
operator|>
literal|1
condition|?
literal|"User Role&amp; "
else|:
literal|""
operator|)
operator|+
literal|"Academic Session"
argument_list|,
literal|"selectPrimaryRole.do?list=Y&ord=%%"
argument_list|,
operator|new
name|String
index|[]
block|{
literal|"User Role"
block|,
literal|"Academic Session"
block|,
literal|"Academic Initiative"
block|,
literal|"Academic Session Status"
block|}
argument_list|,
operator|new
name|String
index|[]
block|{
literal|"left"
block|,
literal|"left"
block|,
literal|"left"
block|,
literal|"left"
block|}
argument_list|,
operator|new
name|boolean
index|[]
block|{
literal|true
block|,
literal|true
block|,
literal|true
block|,
literal|true
block|}
argument_list|)
decl_stmt|;
name|int
name|nrLines
init|=
literal|0
decl_stmt|;
name|UserAuthority
name|firstAuthority
init|=
literal|null
decl_stmt|;
for|for
control|(
name|UserAuthority
name|authority
range|:
name|user
operator|.
name|getAuthorities
argument_list|()
control|)
block|{
name|Session
name|session
init|=
operator|(
name|authority
operator|.
name|getAcademicSession
argument_list|()
operator|==
literal|null
condition|?
literal|null
else|:
name|SessionDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|get
argument_list|(
operator|(
name|Long
operator|)
name|authority
operator|.
name|getAcademicSession
argument_list|()
operator|.
name|getQualifierId
argument_list|()
argument_list|)
operator|)
decl_stmt|;
if|if
condition|(
name|session
operator|==
literal|null
condition|)
continue|continue;
name|String
name|onClick
init|=
literal|"onClick=\"roleListForm.authority.value='"
operator|+
name|authority
operator|.
name|getAuthority
argument_list|()
operator|+
literal|"';roleListForm.submit();\""
decl_stmt|;
name|String
name|bgColor
init|=
operator|(
name|authority
operator|.
name|equals
argument_list|(
name|user
operator|.
name|getCurrentAuthority
argument_list|()
argument_list|)
condition|?
literal|"rgb(168,187,225)"
else|:
literal|null
operator|)
decl_stmt|;
name|table
operator|.
name|addLine
argument_list|(
name|onClick
argument_list|,
operator|new
name|String
index|[]
block|{
name|authority
operator|.
name|getLabel
argument_list|()
block|,
name|session
operator|.
name|getAcademicYear
argument_list|()
operator|+
literal|" "
operator|+
name|session
operator|.
name|getAcademicTerm
argument_list|()
block|,
name|session
operator|.
name|getAcademicInitiative
argument_list|()
block|,
operator|(
name|session
operator|.
name|getStatusType
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
name|session
operator|.
name|getStatusType
argument_list|()
operator|.
name|getLabel
argument_list|()
operator|)
block|}
argument_list|,
operator|new
name|Comparable
index|[]
block|{
operator|new
name|MultiComparable
argument_list|(
name|authority
operator|.
name|toString
argument_list|()
argument_list|,
name|session
operator|.
name|getSessionBeginDateTime
argument_list|()
argument_list|,
name|session
operator|.
name|getAcademicInitiative
argument_list|()
argument_list|)
block|,
operator|new
name|MultiComparable
argument_list|(
name|session
operator|.
name|getSessionBeginDateTime
argument_list|()
argument_list|,
name|session
operator|.
name|getAcademicInitiative
argument_list|()
argument_list|,
name|authority
operator|.
name|toString
argument_list|()
argument_list|)
block|,
operator|new
name|MultiComparable
argument_list|(
name|session
operator|.
name|getAcademicInitiative
argument_list|()
argument_list|,
name|session
operator|.
name|getSessionBeginDateTime
argument_list|()
argument_list|,
name|authority
operator|.
name|toString
argument_list|()
argument_list|)
block|,
operator|new
name|MultiComparable
argument_list|(
name|session
operator|.
name|getStatusType
argument_list|()
operator|==
literal|null
condition|?
operator|-
literal|1
else|:
name|session
operator|.
name|getStatusType
argument_list|()
operator|.
name|getOrd
argument_list|()
argument_list|,
name|session
operator|.
name|getAcademicInitiative
argument_list|()
argument_list|,
name|session
operator|.
name|getSessionBeginDateTime
argument_list|()
argument_list|,
name|authority
operator|.
name|toString
argument_list|()
argument_list|)
block|}
argument_list|)
operator|.
name|setBgColor
argument_list|(
name|bgColor
argument_list|)
expr_stmt|;
if|if
condition|(
name|firstAuthority
operator|==
literal|null
condition|)
name|firstAuthority
operator|=
name|authority
expr_stmt|;
name|nrLines
operator|++
expr_stmt|;
block|}
if|if
condition|(
name|user
operator|.
name|getCurrentAuthority
argument_list|()
operator|==
literal|null
operator|&&
name|nrLines
operator|==
literal|0
condition|)
name|table
operator|.
name|addLine
argument_list|(
operator|new
name|String
index|[]
block|{
literal|"<i><font color='red'>No user role and/or academic session associated with the user "
operator|+
operator|(
name|user
operator|.
name|getName
argument_list|()
operator|==
literal|null
condition|?
name|user
operator|.
name|getUsername
argument_list|()
else|:
name|user
operator|.
name|getName
argument_list|()
operator|)
operator|+
literal|".</font></i>"
block|,
literal|null
block|,
literal|null
block|,
literal|null
block|}
argument_list|,
literal|null
argument_list|)
expr_stmt|;
if|if
condition|(
name|nrLines
operator|==
literal|1
operator|&&
name|firstAuthority
operator|!=
literal|null
condition|)
name|user
operator|.
name|setCurrentAuthority
argument_list|(
name|firstAuthority
argument_list|)
expr_stmt|;
name|request
operator|.
name|setAttribute
argument_list|(
name|Roles
operator|.
name|USER_ROLES_ATTR_NAME
argument_list|,
name|table
operator|.
name|printTable
argument_list|(
name|WebTable
operator|.
name|getOrder
argument_list|(
name|sessionContext
argument_list|,
literal|"roleLists.ord"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|user
operator|.
name|getCurrentAuthority
argument_list|()
return|;
block|}
block|}
end_class

end_unit

