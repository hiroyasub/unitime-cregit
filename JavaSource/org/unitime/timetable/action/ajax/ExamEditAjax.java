begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.1 (University Timetabling Application)  * Copyright (C) 2008, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 2 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program; if not, write to the Free Software Foundation, Inc.,  * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
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
name|List
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
name|servlet
operator|.
name|ServletOutputStream
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
name|timetable
operator|.
name|model
operator|.
name|Class_
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
name|CourseOffering
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
name|InstrOfferingConfig
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
name|Preference
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
name|SchedulingSubpart
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
name|comparators
operator|.
name|ClassComparator
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
name|comparators
operator|.
name|InstrOfferingConfigComparator
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
name|comparators
operator|.
name|SchedulingSubpartComparator
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
name|Class_DAO
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
name|CourseOfferingDAO
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
name|InstrOfferingConfigDAO
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
name|SchedulingSubpartDAO
import|;
end_import

begin_comment
comment|/**  *   * @author Tomas Muller  *  */
end_comment

begin_class
specifier|public
class|class
name|ExamEditAjax
extends|extends
name|Action
block|{
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
literal|"text/xml"
argument_list|)
expr_stmt|;
name|ServletOutputStream
name|out
init|=
name|response
operator|.
name|getOutputStream
argument_list|()
decl_stmt|;
name|out
operator|.
name|print
argument_list|(
literal|"<?xml version=\"1.0\" encoding=\"ISO-8859-1\" ?>\n"
argument_list|)
expr_stmt|;
name|out
operator|.
name|print
argument_list|(
literal|"<results>"
argument_list|)
expr_stmt|;
name|coumputeSuggestionList
argument_list|(
name|request
argument_list|,
name|out
argument_list|)
expr_stmt|;
name|out
operator|.
name|print
argument_list|(
literal|"</results>"
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
block|}
specifier|protected
name|void
name|print
parameter_list|(
name|ServletOutputStream
name|out
parameter_list|,
name|String
name|id
parameter_list|,
name|String
name|value
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
literal|"\" value=\""
operator|+
name|value
operator|+
literal|"\" />"
argument_list|)
expr_stmt|;
block|}
specifier|protected
name|void
name|print
parameter_list|(
name|ServletOutputStream
name|out
parameter_list|,
name|String
name|id
parameter_list|,
name|String
name|value
parameter_list|,
name|String
name|extra
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
literal|"\" value=\""
operator|+
name|value
operator|+
literal|"\" extra=\""
operator|+
name|extra
operator|+
literal|"\" />"
argument_list|)
expr_stmt|;
block|}
specifier|protected
name|void
name|coumputeSuggestionList
parameter_list|(
name|HttpServletRequest
name|request
parameter_list|,
name|ServletOutputStream
name|out
parameter_list|)
throws|throws
name|Exception
block|{
if|if
condition|(
literal|"subjectArea"
operator|.
name|equals
argument_list|(
name|request
operator|.
name|getParameter
argument_list|(
literal|"type"
argument_list|)
argument_list|)
condition|)
block|{
name|coumputeCourseNumbers
argument_list|(
name|request
operator|.
name|getParameter
argument_list|(
literal|"id"
argument_list|)
argument_list|,
name|out
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
literal|"courseNbr"
operator|.
name|equals
argument_list|(
name|request
operator|.
name|getParameter
argument_list|(
literal|"type"
argument_list|)
argument_list|)
condition|)
block|{
name|coumputeSubparts
argument_list|(
name|request
operator|.
name|getParameter
argument_list|(
literal|"id"
argument_list|)
argument_list|,
name|out
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
literal|"itype"
operator|.
name|equals
argument_list|(
name|request
operator|.
name|getParameter
argument_list|(
literal|"type"
argument_list|)
argument_list|)
condition|)
block|{
name|coumputeClasses
argument_list|(
name|request
operator|.
name|getParameter
argument_list|(
literal|"id"
argument_list|)
argument_list|,
name|out
argument_list|)
expr_stmt|;
block|}
block|}
specifier|protected
name|void
name|coumputeCourseNumbers
parameter_list|(
name|String
name|subjectAreaId
parameter_list|,
name|ServletOutputStream
name|out
parameter_list|)
throws|throws
name|Exception
block|{
if|if
condition|(
name|subjectAreaId
operator|==
literal|null
operator|||
name|subjectAreaId
operator|.
name|length
argument_list|()
operator|==
literal|0
operator|||
name|subjectAreaId
operator|.
name|equals
argument_list|(
name|Preference
operator|.
name|BLANK_PREF_VALUE
argument_list|)
condition|)
block|{
name|print
argument_list|(
name|out
argument_list|,
literal|"-1"
argument_list|,
literal|"N/A"
argument_list|)
expr_stmt|;
return|return;
block|}
name|List
name|courseNumbers
init|=
operator|new
name|CourseOfferingDAO
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select co.uniqueId, co.courseNbr, co.title from CourseOffering co "
operator|+
literal|"where co.subjectArea.uniqueId = :subjectAreaId "
operator|+
literal|"and co.instructionalOffering.notOffered = false "
operator|+
literal|"order by co.courseNbr "
argument_list|)
operator|.
name|setFetchSize
argument_list|(
literal|200
argument_list|)
operator|.
name|setCacheable
argument_list|(
literal|true
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"subjectAreaId"
argument_list|,
name|Long
operator|.
name|parseLong
argument_list|(
name|subjectAreaId
argument_list|)
argument_list|)
operator|.
name|list
argument_list|()
decl_stmt|;
if|if
condition|(
name|courseNumbers
operator|.
name|isEmpty
argument_list|()
condition|)
name|print
argument_list|(
name|out
argument_list|,
literal|"-1"
argument_list|,
literal|"N/A"
argument_list|)
expr_stmt|;
if|if
condition|(
name|courseNumbers
operator|.
name|size
argument_list|()
operator|>
literal|1
condition|)
name|print
argument_list|(
name|out
argument_list|,
literal|"-1"
argument_list|,
literal|"-"
argument_list|)
expr_stmt|;
for|for
control|(
name|Iterator
name|i
init|=
name|courseNumbers
operator|.
name|iterator
argument_list|()
init|;
name|i
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|Object
index|[]
name|o
init|=
operator|(
name|Object
index|[]
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|print
argument_list|(
name|out
argument_list|,
name|o
index|[
literal|0
index|]
operator|.
name|toString
argument_list|()
argument_list|,
operator|(
name|o
index|[
literal|1
index|]
operator|.
name|toString
argument_list|()
operator|+
literal|" - "
operator|+
operator|(
name|o
index|[
literal|2
index|]
operator|==
literal|null
condition|?
literal|""
else|:
name|o
index|[
literal|2
index|]
operator|.
name|toString
argument_list|()
operator|.
name|replaceAll
argument_list|(
literal|">"
argument_list|,
literal|"&gt;"
argument_list|)
operator|.
name|replaceAll
argument_list|(
literal|"<"
argument_list|,
literal|"&lt;"
argument_list|)
operator|.
name|replaceAll
argument_list|(
literal|"'"
argument_list|,
literal|"&quot;"
argument_list|)
operator|.
name|replaceAll
argument_list|(
literal|"&"
argument_list|,
literal|"&amp;"
argument_list|)
operator|)
operator|)
argument_list|)
expr_stmt|;
block|}
block|}
specifier|protected
name|void
name|coumputeSubparts
parameter_list|(
name|String
name|courseOfferingId
parameter_list|,
name|ServletOutputStream
name|out
parameter_list|)
throws|throws
name|Exception
block|{
if|if
condition|(
name|courseOfferingId
operator|==
literal|null
operator|||
name|courseOfferingId
operator|.
name|length
argument_list|()
operator|==
literal|0
operator|||
name|courseOfferingId
operator|.
name|equals
argument_list|(
name|Preference
operator|.
name|BLANK_PREF_VALUE
argument_list|)
condition|)
block|{
name|print
argument_list|(
name|out
argument_list|,
literal|"0"
argument_list|,
literal|"N/A"
argument_list|)
expr_stmt|;
return|return;
block|}
name|CourseOffering
name|course
init|=
operator|new
name|CourseOfferingDAO
argument_list|()
operator|.
name|get
argument_list|(
name|Long
operator|.
name|parseLong
argument_list|(
name|courseOfferingId
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|course
operator|==
literal|null
condition|)
block|{
name|print
argument_list|(
name|out
argument_list|,
literal|"0"
argument_list|,
literal|"N/A"
argument_list|)
expr_stmt|;
return|return;
block|}
if|if
condition|(
name|course
operator|.
name|isIsControl
argument_list|()
condition|)
name|print
argument_list|(
name|out
argument_list|,
name|String
operator|.
name|valueOf
argument_list|(
name|Long
operator|.
name|MIN_VALUE
operator|+
literal|1
argument_list|)
argument_list|,
literal|"Offering"
argument_list|)
expr_stmt|;
name|print
argument_list|(
name|out
argument_list|,
name|String
operator|.
name|valueOf
argument_list|(
name|Long
operator|.
name|MIN_VALUE
argument_list|)
argument_list|,
literal|"Course"
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
name|course
operator|.
name|isIsControl
argument_list|()
condition|)
return|return;
name|TreeSet
name|configs
init|=
operator|new
name|TreeSet
argument_list|(
operator|new
name|InstrOfferingConfigComparator
argument_list|(
literal|null
argument_list|)
argument_list|)
decl_stmt|;
name|configs
operator|.
name|addAll
argument_list|(
operator|new
name|InstrOfferingConfigDAO
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select distinct c from "
operator|+
literal|"InstrOfferingConfig c inner join c.instructionalOffering.courseOfferings co "
operator|+
literal|"where co.uniqueId = :courseOfferingId"
argument_list|)
operator|.
name|setFetchSize
argument_list|(
literal|200
argument_list|)
operator|.
name|setCacheable
argument_list|(
literal|true
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"courseOfferingId"
argument_list|,
name|course
operator|.
name|getUniqueId
argument_list|()
argument_list|)
operator|.
name|list
argument_list|()
argument_list|)
expr_stmt|;
name|TreeSet
name|subparts
init|=
operator|new
name|TreeSet
argument_list|(
operator|new
name|SchedulingSubpartComparator
argument_list|(
literal|null
argument_list|)
argument_list|)
decl_stmt|;
name|subparts
operator|.
name|addAll
argument_list|(
operator|new
name|SchedulingSubpartDAO
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select distinct s from "
operator|+
literal|"SchedulingSubpart s inner join s.instrOfferingConfig.instructionalOffering.courseOfferings co "
operator|+
literal|"where co.uniqueId = :courseOfferingId"
argument_list|)
operator|.
name|setFetchSize
argument_list|(
literal|200
argument_list|)
operator|.
name|setCacheable
argument_list|(
literal|true
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"courseOfferingId"
argument_list|,
name|course
operator|.
name|getUniqueId
argument_list|()
argument_list|)
operator|.
name|list
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
name|configs
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|print
argument_list|(
name|out
argument_list|,
name|String
operator|.
name|valueOf
argument_list|(
name|Long
operator|.
name|MIN_VALUE
operator|+
literal|2
argument_list|)
argument_list|,
literal|"-- Configurations --"
argument_list|)
expr_stmt|;
for|for
control|(
name|Iterator
name|i
init|=
name|configs
operator|.
name|iterator
argument_list|()
init|;
name|i
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|InstrOfferingConfig
name|c
init|=
operator|(
name|InstrOfferingConfig
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|print
argument_list|(
name|out
argument_list|,
name|String
operator|.
name|valueOf
argument_list|(
operator|-
name|c
operator|.
name|getUniqueId
argument_list|()
argument_list|)
argument_list|,
name|c
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
if|if
condition|(
operator|!
name|configs
operator|.
name|isEmpty
argument_list|()
operator|&&
operator|!
name|subparts
operator|.
name|isEmpty
argument_list|()
condition|)
name|print
argument_list|(
name|out
argument_list|,
name|String
operator|.
name|valueOf
argument_list|(
name|Long
operator|.
name|MIN_VALUE
operator|+
literal|2
argument_list|)
argument_list|,
literal|"-- Subparts --"
argument_list|)
expr_stmt|;
for|for
control|(
name|Iterator
name|i
init|=
name|subparts
operator|.
name|iterator
argument_list|()
init|;
name|i
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|SchedulingSubpart
name|s
init|=
operator|(
name|SchedulingSubpart
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|String
name|id
init|=
name|s
operator|.
name|getUniqueId
argument_list|()
operator|.
name|toString
argument_list|()
decl_stmt|;
name|String
name|name
init|=
name|s
operator|.
name|getItype
argument_list|()
operator|.
name|getAbbv
argument_list|()
decl_stmt|;
name|String
name|sufix
init|=
name|s
operator|.
name|getSchedulingSubpartSuffix
argument_list|()
decl_stmt|;
while|while
condition|(
name|s
operator|.
name|getParentSubpart
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|name
operator|=
literal|"_"
operator|+
name|name
expr_stmt|;
name|s
operator|=
name|s
operator|.
name|getParentSubpart
argument_list|()
expr_stmt|;
block|}
if|if
condition|(
name|s
operator|.
name|getInstrOfferingConfig
argument_list|()
operator|.
name|getInstructionalOffering
argument_list|()
operator|.
name|getInstrOfferingConfigs
argument_list|()
operator|.
name|size
argument_list|()
operator|>
literal|1
condition|)
name|name
operator|+=
literal|" ["
operator|+
name|s
operator|.
name|getInstrOfferingConfig
argument_list|()
operator|.
name|getName
argument_list|()
operator|+
literal|"]"
expr_stmt|;
name|print
argument_list|(
name|out
argument_list|,
name|id
argument_list|,
name|name
operator|+
operator|(
name|sufix
operator|==
literal|null
operator|||
name|sufix
operator|.
name|length
argument_list|()
operator|==
literal|0
condition|?
literal|""
else|:
literal|" ("
operator|+
name|sufix
operator|+
literal|")"
operator|)
argument_list|)
expr_stmt|;
block|}
block|}
specifier|protected
name|void
name|coumputeClasses
parameter_list|(
name|String
name|schedulingSubpartId
parameter_list|,
name|ServletOutputStream
name|out
parameter_list|)
throws|throws
name|Exception
block|{
if|if
condition|(
name|schedulingSubpartId
operator|==
literal|null
operator|||
name|schedulingSubpartId
operator|.
name|length
argument_list|()
operator|==
literal|0
operator|||
name|schedulingSubpartId
operator|.
name|equals
argument_list|(
name|Preference
operator|.
name|BLANK_PREF_VALUE
argument_list|)
condition|)
block|{
name|print
argument_list|(
name|out
argument_list|,
literal|"-1"
argument_list|,
literal|"N/A"
argument_list|)
expr_stmt|;
return|return;
block|}
name|SchedulingSubpart
name|subpart
init|=
operator|(
name|Long
operator|.
name|parseLong
argument_list|(
name|schedulingSubpartId
argument_list|)
operator|>
literal|0
condition|?
operator|new
name|SchedulingSubpartDAO
argument_list|()
operator|.
name|get
argument_list|(
name|Long
operator|.
name|valueOf
argument_list|(
name|schedulingSubpartId
argument_list|)
argument_list|)
else|:
literal|null
operator|)
decl_stmt|;
if|if
condition|(
name|subpart
operator|==
literal|null
condition|)
block|{
name|print
argument_list|(
name|out
argument_list|,
literal|"-1"
argument_list|,
literal|"N/A"
argument_list|)
expr_stmt|;
return|return;
block|}
name|TreeSet
name|classes
init|=
operator|new
name|TreeSet
argument_list|(
operator|new
name|ClassComparator
argument_list|(
name|ClassComparator
operator|.
name|COMPARE_BY_HIERARCHY
argument_list|)
argument_list|)
decl_stmt|;
name|classes
operator|.
name|addAll
argument_list|(
operator|new
name|Class_DAO
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select distinct c from Class_ c "
operator|+
literal|"where c.schedulingSubpart.uniqueId=:schedulingSubpartId"
argument_list|)
operator|.
name|setFetchSize
argument_list|(
literal|200
argument_list|)
operator|.
name|setCacheable
argument_list|(
literal|true
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"schedulingSubpartId"
argument_list|,
name|Long
operator|.
name|parseLong
argument_list|(
name|schedulingSubpartId
argument_list|)
argument_list|)
operator|.
name|list
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|classes
operator|.
name|size
argument_list|()
operator|>
literal|1
condition|)
name|print
argument_list|(
name|out
argument_list|,
literal|"-1"
argument_list|,
literal|"-"
argument_list|)
expr_stmt|;
for|for
control|(
name|Iterator
name|i
init|=
name|classes
operator|.
name|iterator
argument_list|()
init|;
name|i
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|Class_
name|c
init|=
operator|(
name|Class_
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|print
argument_list|(
name|out
argument_list|,
name|c
operator|.
name|getUniqueId
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|,
name|c
operator|.
name|getSectionNumberString
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

