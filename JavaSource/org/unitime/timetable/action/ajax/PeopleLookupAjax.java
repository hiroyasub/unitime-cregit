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
operator|.
name|ajax
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|IOException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|PrintWriter
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Hashtable
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Iterator
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|StringTokenizer
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|TreeSet
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|naming
operator|.
name|Context
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|naming
operator|.
name|NamingEnumeration
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|naming
operator|.
name|NamingException
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|naming
operator|.
name|directory
operator|.
name|Attribute
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|naming
operator|.
name|directory
operator|.
name|Attributes
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|naming
operator|.
name|directory
operator|.
name|InitialDirContext
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|naming
operator|.
name|directory
operator|.
name|SearchControls
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|naming
operator|.
name|directory
operator|.
name|SearchResult
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
name|ExternalUidTranslation
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
name|ExternalUidTranslation
operator|.
name|Source
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
name|EventContact
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
name|Staff
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
name|Student
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
name|TimetableManager
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
name|EventContactDAO
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
name|StaffDAO
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
name|StudentDAO
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
name|TimetableManagerDAO
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

begin_comment
comment|/**  *   * @author Tomas Muller  *  */
end_comment

begin_class
specifier|public
class|class
name|PeopleLookupAjax
extends|extends
name|Action
block|{
specifier|public
specifier|static
name|ExternalUidTranslation
name|sTranslation
decl_stmt|;
static|static
block|{
if|if
condition|(
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"tmtbl.externalUid.translation"
argument_list|)
operator|!=
literal|null
condition|)
block|{
try|try
block|{
name|sTranslation
operator|=
operator|(
name|ExternalUidTranslation
operator|)
name|Class
operator|.
name|forName
argument_list|(
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"tmtbl.externalUid.translation"
argument_list|)
argument_list|)
operator|.
name|getConstructor
argument_list|()
operator|.
name|newInstance
argument_list|()
expr_stmt|;
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
literal|"Unable to instantiate external uid translation class, "
operator|+
name|e
operator|.
name|getMessage
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
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
name|response
operator|.
name|addHeader
argument_list|(
literal|"Content-Type"
argument_list|,
literal|"text/xml; charset=UTF-8"
argument_list|)
expr_stmt|;
name|request
operator|.
name|setCharacterEncoding
argument_list|(
literal|"UTF-8"
argument_list|)
expr_stmt|;
name|PrintWriter
name|out
init|=
name|response
operator|.
name|getWriter
argument_list|()
decl_stmt|;
try|try
block|{
name|out
operator|.
name|print
argument_list|(
literal|"<?xml version=\"1.0\" encoding=\"utf-8\" ?>\n"
argument_list|)
expr_stmt|;
name|out
operator|.
name|print
argument_list|(
literal|"<results>"
argument_list|)
expr_stmt|;
if|if
condition|(
name|request
operator|.
name|getParameter
argument_list|(
literal|"query"
argument_list|)
operator|!=
literal|null
condition|)
for|for
control|(
name|Person
name|p
range|:
name|findPeople
argument_list|(
name|request
operator|.
name|getParameter
argument_list|(
literal|"query"
argument_list|)
operator|.
name|toLowerCase
argument_list|()
argument_list|,
name|request
operator|.
name|getParameter
argument_list|(
literal|"session"
argument_list|)
argument_list|)
control|)
name|out
operator|.
name|print
argument_list|(
name|p
operator|.
name|toXML
argument_list|()
argument_list|)
expr_stmt|;
name|out
operator|.
name|print
argument_list|(
literal|"</results>"
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
return|return
literal|null
return|;
block|}
specifier|public
specifier|static
name|String
name|translate
parameter_list|(
name|String
name|uid
parameter_list|,
name|Source
name|source
parameter_list|)
block|{
if|if
condition|(
name|sTranslation
operator|==
literal|null
operator|||
name|uid
operator|==
literal|null
operator|||
name|source
operator|.
name|equals
argument_list|(
name|Source
operator|.
name|User
argument_list|)
condition|)
return|return
name|uid
return|;
if|if
condition|(
name|uid
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
operator|(
literal|null
operator|)
return|;
return|return
name|sTranslation
operator|.
name|translate
argument_list|(
name|uid
argument_list|,
name|source
argument_list|,
name|Source
operator|.
name|User
argument_list|)
return|;
block|}
specifier|protected
name|void
name|print
parameter_list|(
name|PrintWriter
name|out
parameter_list|,
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
name|source
parameter_list|)
throws|throws
name|IOException
block|{
name|out
operator|.
name|print
argument_list|(
literal|"<result id=\""
operator|+
name|id
operator|+
literal|"\" "
argument_list|)
expr_stmt|;
if|if
condition|(
name|fname
operator|!=
literal|null
condition|)
name|out
operator|.
name|print
argument_list|(
literal|"fname=\""
operator|+
name|fname
operator|+
literal|"\" "
argument_list|)
expr_stmt|;
if|if
condition|(
name|mname
operator|!=
literal|null
condition|)
name|out
operator|.
name|print
argument_list|(
literal|"mname=\""
operator|+
name|mname
operator|+
literal|"\" "
argument_list|)
expr_stmt|;
if|if
condition|(
name|lname
operator|!=
literal|null
condition|)
name|out
operator|.
name|print
argument_list|(
literal|"lname=\""
operator|+
name|lname
operator|+
literal|"\" "
argument_list|)
expr_stmt|;
if|if
condition|(
name|email
operator|!=
literal|null
condition|)
name|out
operator|.
name|print
argument_list|(
literal|"email=\""
operator|+
name|email
operator|+
literal|"\" "
argument_list|)
expr_stmt|;
if|if
condition|(
name|phone
operator|!=
literal|null
condition|)
name|out
operator|.
name|print
argument_list|(
literal|"phone=\""
operator|+
name|phone
operator|+
literal|"\" "
argument_list|)
expr_stmt|;
if|if
condition|(
name|source
operator|!=
literal|null
condition|)
name|out
operator|.
name|print
argument_list|(
literal|"source=\""
operator|+
name|source
operator|+
literal|"\" "
argument_list|)
expr_stmt|;
name|out
operator|.
name|println
argument_list|(
literal|"/>"
argument_list|)
expr_stmt|;
block|}
specifier|public
name|TreeSet
argument_list|<
name|Person
argument_list|>
name|findPeople
parameter_list|(
name|String
name|query
parameter_list|,
name|String
name|session
parameter_list|)
throws|throws
name|Exception
block|{
name|TreeSet
argument_list|<
name|Person
argument_list|>
name|people
init|=
operator|new
name|TreeSet
argument_list|()
decl_stmt|;
name|people
operator|.
name|addAll
argument_list|(
name|findPeopleFromStaff
argument_list|(
name|query
argument_list|)
argument_list|)
expr_stmt|;
name|people
operator|.
name|addAll
argument_list|(
name|findPeopleFromStudents
argument_list|(
name|query
argument_list|,
name|session
argument_list|)
argument_list|)
expr_stmt|;
name|people
operator|.
name|addAll
argument_list|(
name|findPeopleFromEventContact
argument_list|(
name|query
argument_list|)
argument_list|)
expr_stmt|;
name|people
operator|.
name|addAll
argument_list|(
name|findPeopleFromTimetableManagers
argument_list|(
name|query
argument_list|)
argument_list|)
expr_stmt|;
name|people
operator|.
name|addAll
argument_list|(
name|findPeopleFromLdap
argument_list|(
name|query
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|people
return|;
block|}
specifier|protected
name|TreeSet
argument_list|<
name|Person
argument_list|>
name|findPeopleFromStaff
parameter_list|(
name|String
name|query
parameter_list|)
throws|throws
name|Exception
block|{
name|TreeSet
argument_list|<
name|Person
argument_list|>
name|people
init|=
operator|new
name|TreeSet
argument_list|()
decl_stmt|;
name|String
name|q
init|=
literal|"select s from Staff s where "
decl_stmt|;
for|for
control|(
name|StringTokenizer
name|stk
init|=
operator|new
name|StringTokenizer
argument_list|(
name|query
argument_list|,
literal|" ,"
argument_list|)
init|;
name|stk
operator|.
name|hasMoreTokens
argument_list|()
condition|;
control|)
block|{
name|String
name|t
init|=
name|stk
operator|.
name|nextToken
argument_list|()
operator|.
name|replace
argument_list|(
literal|"'"
argument_list|,
literal|"''"
argument_list|)
decl_stmt|;
name|q
operator|+=
literal|"(lower(s.firstName) like '"
operator|+
name|t
operator|+
literal|"%' or lower(s.middleName) like '"
operator|+
name|t
operator|+
literal|"%' or lower(s.lastName) like '"
operator|+
name|t
operator|+
literal|"%')"
expr_stmt|;
if|if
condition|(
name|stk
operator|.
name|hasMoreTokens
argument_list|()
condition|)
name|q
operator|+=
literal|" and "
expr_stmt|;
block|}
for|for
control|(
name|Iterator
name|i
init|=
name|StaffDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
name|q
argument_list|)
operator|.
name|iterate
argument_list|()
init|;
name|i
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|Staff
name|s
init|=
operator|(
name|Staff
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|people
operator|.
name|add
argument_list|(
operator|new
name|Person
argument_list|(
name|s
argument_list|)
argument_list|)
expr_stmt|;
block|}
return|return
name|people
return|;
block|}
specifier|protected
name|TreeSet
argument_list|<
name|Person
argument_list|>
name|findPeopleFromEventContact
parameter_list|(
name|String
name|query
parameter_list|)
throws|throws
name|Exception
block|{
name|TreeSet
argument_list|<
name|Person
argument_list|>
name|people
init|=
operator|new
name|TreeSet
argument_list|()
decl_stmt|;
name|String
name|q
init|=
literal|"select s from EventContact s where "
decl_stmt|;
for|for
control|(
name|StringTokenizer
name|stk
init|=
operator|new
name|StringTokenizer
argument_list|(
name|query
argument_list|,
literal|" ,"
argument_list|)
init|;
name|stk
operator|.
name|hasMoreTokens
argument_list|()
condition|;
control|)
block|{
name|String
name|t
init|=
name|stk
operator|.
name|nextToken
argument_list|()
operator|.
name|replace
argument_list|(
literal|"'"
argument_list|,
literal|"''"
argument_list|)
decl_stmt|;
name|q
operator|+=
literal|"(lower(s.firstName) like '"
operator|+
name|t
operator|+
literal|"%' or lower(s.middleName) like '"
operator|+
name|t
operator|+
literal|"%' or lower(s.lastName) like '"
operator|+
name|t
operator|+
literal|"%')"
expr_stmt|;
if|if
condition|(
name|stk
operator|.
name|hasMoreTokens
argument_list|()
condition|)
name|q
operator|+=
literal|" and "
expr_stmt|;
block|}
for|for
control|(
name|Iterator
name|i
init|=
name|EventContactDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
name|q
argument_list|)
operator|.
name|iterate
argument_list|()
init|;
name|i
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|EventContact
name|s
init|=
operator|(
name|EventContact
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|people
operator|.
name|add
argument_list|(
operator|new
name|Person
argument_list|(
name|s
argument_list|)
argument_list|)
expr_stmt|;
block|}
return|return
name|people
return|;
block|}
specifier|protected
name|TreeSet
argument_list|<
name|Person
argument_list|>
name|findPeopleFromStudents
parameter_list|(
name|String
name|query
parameter_list|,
name|String
name|session
parameter_list|)
throws|throws
name|Exception
block|{
name|TreeSet
argument_list|<
name|Person
argument_list|>
name|people
init|=
operator|new
name|TreeSet
argument_list|()
decl_stmt|;
if|if
condition|(
name|session
operator|==
literal|null
operator|||
name|session
operator|.
name|length
argument_list|()
operator|==
literal|0
condition|)
return|return
name|people
return|;
name|String
name|q
init|=
literal|"select s from Student s where s.session.uniqueId="
operator|+
name|session
operator|+
literal|" and "
decl_stmt|;
for|for
control|(
name|StringTokenizer
name|stk
init|=
operator|new
name|StringTokenizer
argument_list|(
name|query
argument_list|,
literal|" ,"
argument_list|)
init|;
name|stk
operator|.
name|hasMoreTokens
argument_list|()
condition|;
control|)
block|{
name|String
name|t
init|=
name|stk
operator|.
name|nextToken
argument_list|()
operator|.
name|replace
argument_list|(
literal|"'"
argument_list|,
literal|"''"
argument_list|)
decl_stmt|;
name|q
operator|+=
literal|"(lower(s.firstName) like '"
operator|+
name|t
operator|+
literal|"%' or lower(s.middleName) like '"
operator|+
name|t
operator|+
literal|"%' or lower(s.lastName) like '"
operator|+
name|t
operator|+
literal|"%')"
expr_stmt|;
if|if
condition|(
name|stk
operator|.
name|hasMoreTokens
argument_list|()
condition|)
name|q
operator|+=
literal|" and "
expr_stmt|;
block|}
for|for
control|(
name|Iterator
name|i
init|=
name|StudentDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
name|q
argument_list|)
operator|.
name|iterate
argument_list|()
init|;
name|i
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|Student
name|s
init|=
operator|(
name|Student
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|people
operator|.
name|add
argument_list|(
operator|new
name|Person
argument_list|(
name|s
argument_list|)
argument_list|)
expr_stmt|;
block|}
return|return
name|people
return|;
block|}
specifier|protected
name|TreeSet
argument_list|<
name|Person
argument_list|>
name|findPeopleFromTimetableManagers
parameter_list|(
name|String
name|query
parameter_list|)
throws|throws
name|Exception
block|{
name|TreeSet
argument_list|<
name|Person
argument_list|>
name|people
init|=
operator|new
name|TreeSet
argument_list|()
decl_stmt|;
name|String
name|q
init|=
literal|"select s from TimetableManager s where "
decl_stmt|;
for|for
control|(
name|StringTokenizer
name|stk
init|=
operator|new
name|StringTokenizer
argument_list|(
name|query
argument_list|,
literal|" ,"
argument_list|)
init|;
name|stk
operator|.
name|hasMoreTokens
argument_list|()
condition|;
control|)
block|{
name|String
name|t
init|=
name|stk
operator|.
name|nextToken
argument_list|()
operator|.
name|replace
argument_list|(
literal|"'"
argument_list|,
literal|"''"
argument_list|)
decl_stmt|;
name|q
operator|+=
literal|"(lower(s.firstName) like '"
operator|+
name|t
operator|+
literal|"%' or lower(s.middleName) like '"
operator|+
name|t
operator|+
literal|"%' or lower(s.lastName) like '"
operator|+
name|t
operator|+
literal|"%')"
expr_stmt|;
if|if
condition|(
name|stk
operator|.
name|hasMoreTokens
argument_list|()
condition|)
name|q
operator|+=
literal|" and "
expr_stmt|;
block|}
for|for
control|(
name|Iterator
name|i
init|=
name|TimetableManagerDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
name|q
argument_list|)
operator|.
name|iterate
argument_list|()
init|;
name|i
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|TimetableManager
name|s
init|=
operator|(
name|TimetableManager
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|people
operator|.
name|add
argument_list|(
operator|new
name|Person
argument_list|(
name|s
argument_list|)
argument_list|)
expr_stmt|;
block|}
return|return
name|people
return|;
block|}
specifier|protected
name|TreeSet
argument_list|<
name|Person
argument_list|>
name|findPeopleFromLdap
parameter_list|(
name|String
name|query
parameter_list|)
throws|throws
name|Exception
block|{
name|TreeSet
argument_list|<
name|Person
argument_list|>
name|people
init|=
operator|new
name|TreeSet
argument_list|()
decl_stmt|;
if|if
condition|(
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"tmtbl.lookup.ldap"
argument_list|)
operator|==
literal|null
condition|)
return|return
name|people
return|;
name|InitialDirContext
name|ctx
init|=
literal|null
decl_stmt|;
try|try
block|{
name|Hashtable
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|env
init|=
operator|new
name|Hashtable
argument_list|()
decl_stmt|;
name|env
operator|.
name|put
argument_list|(
name|Context
operator|.
name|INITIAL_CONTEXT_FACTORY
argument_list|,
literal|"com.sun.jndi.ldap.LdapCtxFactory"
argument_list|)
expr_stmt|;
name|env
operator|.
name|put
argument_list|(
name|Context
operator|.
name|PROVIDER_URL
argument_list|,
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"tmtbl.lookup.ldap"
argument_list|)
argument_list|)
expr_stmt|;
name|env
operator|.
name|put
argument_list|(
name|Context
operator|.
name|REFERRAL
argument_list|,
literal|"ignore"
argument_list|)
expr_stmt|;
name|env
operator|.
name|put
argument_list|(
literal|"java.naming.ldap.version"
argument_list|,
literal|"3"
argument_list|)
expr_stmt|;
name|env
operator|.
name|put
argument_list|(
name|Context
operator|.
name|SECURITY_AUTHENTICATION
argument_list|,
literal|"simple"
argument_list|)
expr_stmt|;
name|ctx
operator|=
operator|new
name|InitialDirContext
argument_list|(
name|env
argument_list|)
expr_stmt|;
name|SearchControls
name|ctls
init|=
operator|new
name|SearchControls
argument_list|()
decl_stmt|;
name|ctls
operator|.
name|setCountLimit
argument_list|(
literal|100
argument_list|)
expr_stmt|;
name|String
name|filter
init|=
literal|""
decl_stmt|;
for|for
control|(
name|StringTokenizer
name|stk
init|=
operator|new
name|StringTokenizer
argument_list|(
name|query
argument_list|,
literal|" ,"
argument_list|)
init|;
name|stk
operator|.
name|hasMoreTokens
argument_list|()
condition|;
control|)
block|{
name|String
name|t
init|=
name|stk
operator|.
name|nextToken
argument_list|()
decl_stmt|;
if|if
condition|(
name|filter
operator|.
name|length
argument_list|()
operator|==
literal|0
condition|)
name|filter
operator|=
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"tmtbl.lookup.ldap.query"
argument_list|,
literal|"(|(|(sn=%*)(uid=%))(givenName=%*))"
argument_list|)
operator|.
name|replaceAll
argument_list|(
literal|"%"
argument_list|,
name|t
argument_list|)
expr_stmt|;
else|else
name|filter
operator|=
literal|"(&"
operator|+
name|filter
operator|+
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"tmtbl.lookup.ldap.query"
argument_list|,
literal|"(|(|(sn=%*)(uid=%))(givenName=%*))"
argument_list|)
operator|.
name|replaceAll
argument_list|(
literal|"%"
argument_list|,
name|t
argument_list|)
operator|+
literal|")"
expr_stmt|;
block|}
for|for
control|(
name|NamingEnumeration
argument_list|<
name|SearchResult
argument_list|>
name|e
init|=
name|ctx
operator|.
name|search
argument_list|(
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"tmtbl.lookup.ldap.name"
argument_list|,
literal|""
argument_list|)
argument_list|,
name|filter
argument_list|,
name|ctls
argument_list|)
init|;
name|e
operator|.
name|hasMore
argument_list|()
condition|;
control|)
name|people
operator|.
name|add
argument_list|(
operator|new
name|Person
argument_list|(
name|e
operator|.
name|next
argument_list|()
operator|.
name|getAttributes
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|NamingException
name|e
parameter_list|)
block|{
name|e
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
finally|finally
block|{
try|try
block|{
if|if
condition|(
name|ctx
operator|!=
literal|null
condition|)
name|ctx
operator|.
name|close
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
block|}
return|return
name|people
return|;
block|}
specifier|public
specifier|static
name|String
name|getAttribute
parameter_list|(
name|Attributes
name|attrs
parameter_list|,
name|String
name|name
parameter_list|)
block|{
if|if
condition|(
name|attrs
operator|==
literal|null
condition|)
return|return
literal|null
return|;
for|for
control|(
name|StringTokenizer
name|stk
init|=
operator|new
name|StringTokenizer
argument_list|(
name|name
argument_list|,
literal|","
argument_list|)
init|;
name|stk
operator|.
name|hasMoreTokens
argument_list|()
condition|;
control|)
block|{
name|Attribute
name|a
init|=
name|attrs
operator|.
name|get
argument_list|(
name|stk
operator|.
name|nextToken
argument_list|()
argument_list|)
decl_stmt|;
try|try
block|{
if|if
condition|(
name|a
operator|!=
literal|null
operator|&&
name|a
operator|.
name|get
argument_list|()
operator|!=
literal|null
condition|)
return|return
name|a
operator|.
name|get
argument_list|()
operator|.
name|toString
argument_list|()
return|;
block|}
catch|catch
parameter_list|(
name|NamingException
name|e
parameter_list|)
block|{
block|}
block|}
return|return
literal|null
return|;
block|}
specifier|public
specifier|static
class|class
name|Person
implements|implements
name|Comparable
argument_list|<
name|Person
argument_list|>
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
specifier|private
name|Person
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
comment|//if (iPhone!=null) iPhone = iPhone.replaceAll("\\+? ?\\-?\\(?\\)?","");
block|}
specifier|public
name|Person
parameter_list|(
name|Staff
name|staff
parameter_list|)
block|{
name|this
argument_list|(
name|translate
argument_list|(
name|staff
operator|.
name|getExternalUniqueId
argument_list|()
argument_list|,
name|Source
operator|.
name|Staff
argument_list|)
argument_list|,
name|staff
operator|.
name|getFirstName
argument_list|()
argument_list|,
name|staff
operator|.
name|getMiddleName
argument_list|()
argument_list|,
name|staff
operator|.
name|getLastName
argument_list|()
argument_list|,
name|staff
operator|.
name|getEmail
argument_list|()
argument_list|,
literal|null
argument_list|,
name|staff
operator|.
name|getDept
argument_list|()
argument_list|,
operator|(
name|staff
operator|.
name|getPositionCode
argument_list|()
operator|==
literal|null
condition|?
literal|null
else|:
name|staff
operator|.
name|getPositionCode
argument_list|()
operator|.
name|getPositionType
argument_list|()
operator|==
literal|null
condition|?
name|staff
operator|.
name|getPositionCode
argument_list|()
operator|.
name|getPositionCode
argument_list|()
else|:
name|staff
operator|.
name|getPositionCode
argument_list|()
operator|.
name|getPositionType
argument_list|()
operator|.
name|getLabel
argument_list|()
operator|)
argument_list|,
literal|"Staff"
argument_list|)
expr_stmt|;
block|}
specifier|public
name|Person
parameter_list|(
name|Student
name|student
parameter_list|)
block|{
name|this
argument_list|(
name|translate
argument_list|(
name|student
operator|.
name|getExternalUniqueId
argument_list|()
argument_list|,
name|Source
operator|.
name|Student
argument_list|)
argument_list|,
name|student
operator|.
name|getFirstName
argument_list|()
argument_list|,
name|student
operator|.
name|getMiddleName
argument_list|()
argument_list|,
name|student
operator|.
name|getLastName
argument_list|()
argument_list|,
name|student
operator|.
name|getEmail
argument_list|()
argument_list|,
literal|null
argument_list|,
literal|null
argument_list|,
literal|"Student"
argument_list|,
literal|"Students"
argument_list|)
expr_stmt|;
block|}
specifier|public
name|Person
parameter_list|(
name|EventContact
name|contact
parameter_list|)
block|{
name|this
argument_list|(
name|translate
argument_list|(
name|contact
operator|.
name|getExternalUniqueId
argument_list|()
argument_list|,
name|Source
operator|.
name|User
argument_list|)
argument_list|,
name|contact
operator|.
name|getFirstName
argument_list|()
argument_list|,
name|contact
operator|.
name|getMiddleName
argument_list|()
argument_list|,
name|contact
operator|.
name|getLastName
argument_list|()
argument_list|,
name|contact
operator|.
name|getEmailAddress
argument_list|()
argument_list|,
name|contact
operator|.
name|getPhone
argument_list|()
argument_list|,
literal|null
argument_list|,
literal|null
argument_list|,
literal|"Event Contacts"
argument_list|)
expr_stmt|;
block|}
specifier|public
name|Person
parameter_list|(
name|TimetableManager
name|manager
parameter_list|)
block|{
name|this
argument_list|(
name|translate
argument_list|(
name|manager
operator|.
name|getExternalUniqueId
argument_list|()
argument_list|,
name|Source
operator|.
name|User
argument_list|)
argument_list|,
name|manager
operator|.
name|getFirstName
argument_list|()
argument_list|,
name|manager
operator|.
name|getMiddleName
argument_list|()
argument_list|,
name|manager
operator|.
name|getLastName
argument_list|()
argument_list|,
name|manager
operator|.
name|getEmailAddress
argument_list|()
argument_list|,
literal|null
argument_list|,
literal|null
argument_list|,
operator|(
name|manager
operator|.
name|getPrimaryRole
argument_list|()
operator|==
literal|null
condition|?
literal|null
else|:
name|manager
operator|.
name|getPrimaryRole
argument_list|()
operator|.
name|getAbbv
argument_list|()
operator|)
argument_list|,
literal|"Timetable Managers"
argument_list|)
expr_stmt|;
block|}
specifier|public
name|Person
parameter_list|(
name|Attributes
name|a
parameter_list|)
throws|throws
name|NamingException
block|{
name|this
argument_list|(
name|translate
argument_list|(
name|getAttribute
argument_list|(
name|a
argument_list|,
literal|"uid"
argument_list|)
argument_list|,
name|Source
operator|.
name|LDAP
argument_list|)
argument_list|,
name|Constants
operator|.
name|toInitialCase
argument_list|(
name|getAttribute
argument_list|(
name|a
argument_list|,
literal|"givenName"
argument_list|)
argument_list|)
argument_list|,
name|Constants
operator|.
name|toInitialCase
argument_list|(
name|getAttribute
argument_list|(
name|a
argument_list|,
literal|"cn"
argument_list|)
argument_list|)
argument_list|,
name|Constants
operator|.
name|toInitialCase
argument_list|(
name|getAttribute
argument_list|(
name|a
argument_list|,
literal|"sn"
argument_list|)
argument_list|)
argument_list|,
name|getAttribute
argument_list|(
name|a
argument_list|,
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"tmtbl.lookup.ldap.email"
argument_list|,
literal|"mail"
argument_list|)
argument_list|)
argument_list|,
name|getAttribute
argument_list|(
name|a
argument_list|,
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"tmtbl.lookup.ldap.phone"
argument_list|,
literal|"phone,officePhone,homePhone,telephoneNumber"
argument_list|)
argument_list|)
argument_list|,
name|Constants
operator|.
name|toInitialCase
argument_list|(
name|getAttribute
argument_list|(
name|a
argument_list|,
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"tmtbl.lookup.ldap.department"
argument_list|,
literal|"department"
argument_list|)
argument_list|)
argument_list|)
argument_list|,
name|Constants
operator|.
name|toInitialCase
argument_list|(
name|getAttribute
argument_list|(
name|a
argument_list|,
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"tmtbl.lookup.ldap.position"
argument_list|,
literal|"position,title"
argument_list|)
argument_list|)
argument_list|)
argument_list|,
literal|"Directory"
argument_list|)
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
name|String
name|getSource
parameter_list|()
block|{
return|return
name|iSource
return|;
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
name|String
name|getMiddleName
parameter_list|()
block|{
return|return
name|iMName
return|;
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
name|String
name|getPhone
parameter_list|()
block|{
return|return
name|iPhone
return|;
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
name|String
name|getDepartment
parameter_list|()
block|{
return|return
name|iDept
return|;
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
name|int
name|compareTo
parameter_list|(
name|Person
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
name|String
name|toXML
parameter_list|()
block|{
return|return
literal|"<result id=\""
operator|+
name|getId
argument_list|()
operator|+
literal|"\" "
operator|+
operator|(
name|getFirstName
argument_list|()
operator|!=
literal|null
condition|?
literal|"fname=\""
operator|+
name|getFirstName
argument_list|()
operator|+
literal|"\" "
else|:
literal|""
operator|)
operator|+
operator|(
name|getMiddleName
argument_list|()
operator|!=
literal|null
condition|?
literal|"mname=\""
operator|+
name|getMiddleName
argument_list|()
operator|+
literal|"\" "
else|:
literal|""
operator|)
operator|+
operator|(
name|getLastName
argument_list|()
operator|!=
literal|null
condition|?
literal|"lname=\""
operator|+
name|getLastName
argument_list|()
operator|+
literal|"\" "
else|:
literal|""
operator|)
operator|+
operator|(
name|getEmail
argument_list|()
operator|!=
literal|null
condition|?
literal|"email=\""
operator|+
name|getEmail
argument_list|()
operator|+
literal|"\" "
else|:
literal|""
operator|)
operator|+
operator|(
name|getPhone
argument_list|()
operator|!=
literal|null
condition|?
literal|"phone=\""
operator|+
name|getPhone
argument_list|()
operator|+
literal|"\" "
else|:
literal|""
operator|)
operator|+
operator|(
name|getDepartment
argument_list|()
operator|!=
literal|null
condition|?
literal|"dept=\""
operator|+
name|getDepartment
argument_list|()
operator|+
literal|"\" "
else|:
literal|""
operator|)
operator|+
operator|(
name|getPosition
argument_list|()
operator|!=
literal|null
condition|?
literal|"pos=\""
operator|+
name|getPosition
argument_list|()
operator|+
literal|"\" "
else|:
literal|""
operator|)
operator|+
operator|(
name|getSource
argument_list|()
operator|!=
literal|null
condition|?
literal|"source=\""
operator|+
name|getSource
argument_list|()
operator|+
literal|"\" "
else|:
literal|""
operator|)
operator|+
literal|"/>"
return|;
block|}
block|}
block|}
end_class

end_unit

