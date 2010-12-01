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
name|dataexchange
package|;
end_package

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
name|org
operator|.
name|dom4j
operator|.
name|Element
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
name|AcadAreaReservation
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
name|AcademicArea
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
name|ReservationType
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

begin_comment
comment|/**  *   * @author Timothy Almon  *  */
end_comment

begin_class
specifier|public
class|class
name|AcadAreaReservationImport
extends|extends
name|BaseImport
block|{
specifier|public
name|void
name|loadXml
parameter_list|(
name|Element
name|root
parameter_list|)
throws|throws
name|Exception
block|{
if|if
condition|(
operator|!
name|root
operator|.
name|getName
argument_list|()
operator|.
name|equalsIgnoreCase
argument_list|(
literal|"academicAreaReservations"
argument_list|)
condition|)
block|{
throw|throw
operator|new
name|Exception
argument_list|(
literal|"Given XML file is not an Academic Area Reservation load file."
argument_list|)
throw|;
block|}
try|try
block|{
name|beginTransaction
argument_list|()
expr_stmt|;
name|String
name|campus
init|=
name|root
operator|.
name|attributeValue
argument_list|(
literal|"campus"
argument_list|)
decl_stmt|;
name|String
name|year
init|=
name|root
operator|.
name|attributeValue
argument_list|(
literal|"year"
argument_list|)
decl_stmt|;
name|String
name|term
init|=
name|root
operator|.
name|attributeValue
argument_list|(
literal|"term"
argument_list|)
decl_stmt|;
name|Session
name|session
init|=
name|Session
operator|.
name|getSessionUsingInitiativeYearTerm
argument_list|(
name|campus
argument_list|,
name|year
argument_list|,
name|term
argument_list|)
decl_stmt|;
if|if
condition|(
name|session
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|Exception
argument_list|(
literal|"No session found for the given campus, year, and term."
argument_list|)
throw|;
block|}
for|for
control|(
name|Iterator
name|iter
init|=
name|root
operator|.
name|elementIterator
argument_list|()
init|;
name|iter
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|Element
name|element
init|=
operator|(
name|Element
operator|)
name|iter
operator|.
name|next
argument_list|()
decl_stmt|;
name|String
name|subject
init|=
name|element
operator|.
name|attributeValue
argument_list|(
literal|"subject"
argument_list|)
decl_stmt|;
name|String
name|course
init|=
name|element
operator|.
name|attributeValue
argument_list|(
literal|"courseNumber"
argument_list|)
decl_stmt|;
for|for
control|(
name|Iterator
name|it
init|=
name|element
operator|.
name|elementIterator
argument_list|()
init|;
name|it
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|Element
name|el
init|=
operator|(
name|Element
operator|)
name|it
operator|.
name|next
argument_list|()
decl_stmt|;
name|String
name|academicArea
init|=
name|el
operator|.
name|attributeValue
argument_list|(
literal|"academicArea"
argument_list|)
decl_stmt|;
if|if
condition|(
name|academicArea
operator|==
literal|null
condition|)
block|{
name|error
argument_list|(
literal|"Academic area not provided for "
operator|+
name|subject
operator|+
literal|" "
operator|+
name|course
operator|+
literal|"."
argument_list|)
expr_stmt|;
continue|continue;
block|}
name|String
name|reservationType
init|=
name|el
operator|.
name|attributeValue
argument_list|(
literal|"reservationType"
argument_list|)
decl_stmt|;
if|if
condition|(
name|reservationType
operator|==
literal|null
condition|)
block|{
name|error
argument_list|(
literal|"Reservation type not provided for "
operator|+
name|subject
operator|+
literal|" "
operator|+
name|course
operator|+
literal|"."
argument_list|)
expr_stmt|;
continue|continue;
block|}
name|AcademicArea
name|area
init|=
name|fetchAcademicArea
argument_list|(
name|academicArea
argument_list|,
name|session
operator|.
name|getSessionId
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|area
operator|==
literal|null
condition|)
continue|continue;
name|String
name|priority
init|=
name|el
operator|.
name|attributeValue
argument_list|(
literal|"priority"
argument_list|)
decl_stmt|;
if|if
condition|(
name|priority
operator|==
literal|null
condition|)
name|priority
operator|=
literal|"1"
expr_stmt|;
name|AcadAreaReservation
name|reservation
init|=
operator|new
name|AcadAreaReservation
argument_list|()
decl_stmt|;
name|CourseOffering
name|offer
init|=
name|CourseOffering
operator|.
name|findBySessionSubjAreaAbbvCourseNbr
argument_list|(
name|session
operator|.
name|getUniqueId
argument_list|()
argument_list|,
name|subject
argument_list|,
name|course
argument_list|)
decl_stmt|;
if|if
condition|(
name|offer
operator|==
literal|null
condition|)
continue|continue;
name|reservation
operator|.
name|setOwner
argument_list|(
name|offer
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
name|reservation
operator|.
name|setReservationType
argument_list|(
name|fetchReservationType
argument_list|(
name|reservationType
argument_list|)
argument_list|)
expr_stmt|;
name|reservation
operator|.
name|setAcademicClassification
argument_list|(
literal|null
argument_list|)
expr_stmt|;
name|reservation
operator|.
name|setAcademicArea
argument_list|(
name|area
argument_list|)
expr_stmt|;
name|reservation
operator|.
name|setPriority
argument_list|(
name|Integer
operator|.
name|decode
argument_list|(
name|priority
argument_list|)
argument_list|)
expr_stmt|;
name|reservation
operator|.
name|setReserved
argument_list|(
name|Integer
operator|.
name|decode
argument_list|(
name|el
operator|.
name|attributeValue
argument_list|(
literal|"request"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|reservation
operator|.
name|setPriorEnrollment
argument_list|(
name|Integer
operator|.
name|decode
argument_list|(
name|el
operator|.
name|attributeValue
argument_list|(
literal|"priorEnrollment"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|reservation
operator|.
name|setProjectedEnrollment
argument_list|(
name|Integer
operator|.
name|decode
argument_list|(
name|el
operator|.
name|attributeValue
argument_list|(
literal|"projectedEnrollment"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|reservation
operator|.
name|setOwnerClassId
argument_list|(
literal|"U"
argument_list|)
expr_stmt|;
name|reservation
operator|.
name|setRequested
argument_list|(
name|reservation
operator|.
name|getReserved
argument_list|()
argument_list|)
expr_stmt|;
name|getHibSession
argument_list|()
operator|.
name|saveOrUpdate
argument_list|(
name|reservation
argument_list|)
expr_stmt|;
block|}
name|flushIfNeeded
argument_list|(
literal|false
argument_list|)
expr_stmt|;
block|}
name|commitTransaction
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|fatal
argument_list|(
literal|"Exception: "
operator|+
name|e
operator|.
name|getMessage
argument_list|()
argument_list|,
name|e
argument_list|)
expr_stmt|;
name|rollbackTransaction
argument_list|()
expr_stmt|;
throw|throw
name|e
throw|;
block|}
block|}
name|AcademicArea
name|fetchAcademicArea
parameter_list|(
name|String
name|academicArea
parameter_list|,
name|Long
name|sessionId
parameter_list|)
block|{
return|return
operator|(
name|AcademicArea
operator|)
name|getHibSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select distinct a from AcademicArea as a where a.academicAreaAbbreviation=:academicArea and a.session.uniqueId=:sessionId"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"sessionId"
argument_list|,
name|sessionId
operator|.
name|longValue
argument_list|()
argument_list|)
operator|.
name|setString
argument_list|(
literal|"academicArea"
argument_list|,
name|academicArea
argument_list|)
operator|.
name|setCacheable
argument_list|(
literal|true
argument_list|)
operator|.
name|uniqueResult
argument_list|()
return|;
block|}
name|ReservationType
name|fetchReservationType
parameter_list|(
name|String
name|type
parameter_list|)
block|{
return|return
operator|(
name|ReservationType
operator|)
name|getHibSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select distinct a from ReservationType as a where a.reference=:ref"
argument_list|)
operator|.
name|setString
argument_list|(
literal|"ref"
argument_list|,
name|type
argument_list|)
operator|.
name|setCacheable
argument_list|(
literal|true
argument_list|)
operator|.
name|uniqueResult
argument_list|()
return|;
block|}
block|}
end_class

end_unit

