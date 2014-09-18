begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.3 - 3.5 (University Timetabling Application)  * Copyright (C) 2011 - 2013, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
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
name|Collections
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Comparator
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
name|List
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Locale
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Properties
import|;
end_import

begin_import
import|import
name|org
operator|.
name|dom4j
operator|.
name|Document
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
name|AcademicClassification
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
name|CourseReservation
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
name|CurriculumReservation
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
name|IndividualReservation
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
name|OverrideReservation
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
name|PosMajor
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
name|StudentGroup
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
name|StudentGroupReservation
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|ReservationExport
extends|extends
name|BaseExport
block|{
specifier|public
specifier|static
name|String
name|sDateFormat
init|=
literal|"MM/dd/yyyy"
decl_stmt|;
annotation|@
name|Override
specifier|public
name|void
name|saveXml
parameter_list|(
name|Document
name|document
parameter_list|,
name|Session
name|session
parameter_list|,
name|Properties
name|parameters
parameter_list|)
throws|throws
name|Exception
block|{
try|try
block|{
name|beginTransaction
argument_list|()
expr_stmt|;
name|Element
name|root
init|=
name|document
operator|.
name|addElement
argument_list|(
literal|"reservations"
argument_list|)
decl_stmt|;
name|root
operator|.
name|addAttribute
argument_list|(
literal|"campus"
argument_list|,
name|session
operator|.
name|getAcademicInitiative
argument_list|()
argument_list|)
expr_stmt|;
name|root
operator|.
name|addAttribute
argument_list|(
literal|"year"
argument_list|,
name|session
operator|.
name|getAcademicYear
argument_list|()
argument_list|)
expr_stmt|;
name|root
operator|.
name|addAttribute
argument_list|(
literal|"term"
argument_list|,
name|session
operator|.
name|getAcademicTerm
argument_list|()
argument_list|)
expr_stmt|;
name|root
operator|.
name|addAttribute
argument_list|(
literal|"dateFormat"
argument_list|,
name|sDateFormat
argument_list|)
expr_stmt|;
name|root
operator|.
name|addAttribute
argument_list|(
literal|"created"
argument_list|,
operator|new
name|Date
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
name|List
argument_list|<
name|Reservation
argument_list|>
name|reservations
init|=
operator|(
name|List
argument_list|<
name|Reservation
argument_list|>
operator|)
name|getHibSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select r from Reservation r where r.instructionalOffering.session.uniqueId = :sessionId"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"sessionId"
argument_list|,
name|session
operator|.
name|getUniqueId
argument_list|()
argument_list|)
operator|.
name|list
argument_list|()
decl_stmt|;
name|Collections
operator|.
name|sort
argument_list|(
name|reservations
argument_list|,
operator|new
name|Comparator
argument_list|<
name|Reservation
argument_list|>
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|int
name|compare
parameter_list|(
name|Reservation
name|r1
parameter_list|,
name|Reservation
name|r2
parameter_list|)
block|{
name|int
name|cmp
init|=
name|r1
operator|.
name|getInstructionalOffering
argument_list|()
operator|.
name|getControllingCourseOffering
argument_list|()
operator|.
name|compareTo
argument_list|(
name|r2
operator|.
name|getInstructionalOffering
argument_list|()
operator|.
name|getControllingCourseOffering
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
name|r1
operator|.
name|getClass
argument_list|()
operator|.
name|getName
argument_list|()
operator|.
name|compareTo
argument_list|(
name|r2
operator|.
name|getClass
argument_list|()
operator|.
name|getName
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
return|return
name|r1
operator|.
name|getUniqueId
argument_list|()
operator|.
name|compareTo
argument_list|(
name|r2
operator|.
name|getUniqueId
argument_list|()
argument_list|)
return|;
block|}
block|}
argument_list|)
expr_stmt|;
name|SimpleDateFormat
name|df
init|=
operator|new
name|SimpleDateFormat
argument_list|(
name|sDateFormat
argument_list|,
name|Locale
operator|.
name|US
argument_list|)
decl_stmt|;
for|for
control|(
name|Reservation
name|reservation
range|:
operator|(
name|List
argument_list|<
name|Reservation
argument_list|>
operator|)
name|getHibSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select r from Reservation r where r.instructionalOffering.session.uniqueId = :sessionId"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"sessionId"
argument_list|,
name|session
operator|.
name|getUniqueId
argument_list|()
argument_list|)
operator|.
name|list
argument_list|()
control|)
block|{
name|Element
name|reservationEl
init|=
name|root
operator|.
name|addElement
argument_list|(
literal|"reservation"
argument_list|)
decl_stmt|;
name|CourseOffering
name|course
init|=
name|reservation
operator|.
name|getInstructionalOffering
argument_list|()
operator|.
name|getControllingCourseOffering
argument_list|()
decl_stmt|;
if|if
condition|(
name|reservation
operator|instanceof
name|CourseReservation
condition|)
name|course
operator|=
operator|(
operator|(
name|CourseReservation
operator|)
name|reservation
operator|)
operator|.
name|getCourse
argument_list|()
expr_stmt|;
name|reservationEl
operator|.
name|addAttribute
argument_list|(
literal|"subject"
argument_list|,
name|course
operator|.
name|getSubjectAreaAbbv
argument_list|()
argument_list|)
expr_stmt|;
name|reservationEl
operator|.
name|addAttribute
argument_list|(
literal|"courseNbr"
argument_list|,
name|course
operator|.
name|getCourseNbr
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|reservation
operator|.
name|getLimit
argument_list|()
operator|!=
literal|null
operator|&&
operator|!
operator|(
name|reservation
operator|instanceof
name|IndividualReservation
operator|)
condition|)
name|reservationEl
operator|.
name|addAttribute
argument_list|(
literal|"limit"
argument_list|,
name|reservation
operator|.
name|getLimit
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|reservation
operator|.
name|getExpirationDate
argument_list|()
operator|!=
literal|null
condition|)
name|reservationEl
operator|.
name|addAttribute
argument_list|(
literal|"expire"
argument_list|,
name|df
operator|.
name|format
argument_list|(
name|reservation
operator|.
name|getExpirationDate
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
for|for
control|(
name|InstrOfferingConfig
name|config
range|:
name|reservation
operator|.
name|getConfigurations
argument_list|()
control|)
block|{
name|reservationEl
operator|.
name|addElement
argument_list|(
literal|"configuration"
argument_list|)
operator|.
name|addAttribute
argument_list|(
literal|"name"
argument_list|,
name|config
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
block|}
for|for
control|(
name|Class_
name|clazz
range|:
name|reservation
operator|.
name|getClasses
argument_list|()
control|)
block|{
name|Element
name|classEl
init|=
name|reservationEl
operator|.
name|addElement
argument_list|(
literal|"class"
argument_list|)
decl_stmt|;
if|if
condition|(
name|clazz
operator|.
name|getExternalUniqueId
argument_list|()
operator|!=
literal|null
condition|)
name|classEl
operator|.
name|addAttribute
argument_list|(
literal|"externalId"
argument_list|,
name|clazz
operator|.
name|getExternalUniqueId
argument_list|()
argument_list|)
expr_stmt|;
name|classEl
operator|.
name|addAttribute
argument_list|(
literal|"type"
argument_list|,
name|clazz
operator|.
name|getItypeDesc
argument_list|()
operator|.
name|trim
argument_list|()
argument_list|)
expr_stmt|;
name|classEl
operator|.
name|addAttribute
argument_list|(
literal|"suffix"
argument_list|,
name|clazz
operator|.
name|getSectionNumberString
argument_list|(
name|getHibSession
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|reservation
operator|instanceof
name|OverrideReservation
condition|)
block|{
name|reservationEl
operator|.
name|addAttribute
argument_list|(
literal|"type"
argument_list|,
operator|(
operator|(
name|OverrideReservation
operator|)
name|reservation
operator|)
operator|.
name|getOverrideType
argument_list|()
operator|.
name|getReference
argument_list|()
argument_list|)
expr_stmt|;
for|for
control|(
name|Student
name|student
range|:
operator|(
operator|(
name|OverrideReservation
operator|)
name|reservation
operator|)
operator|.
name|getStudents
argument_list|()
control|)
block|{
name|reservationEl
operator|.
name|addElement
argument_list|(
literal|"student"
argument_list|)
operator|.
name|addAttribute
argument_list|(
literal|"externalId"
argument_list|,
name|student
operator|.
name|getExternalUniqueId
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
if|else if
condition|(
name|reservation
operator|instanceof
name|IndividualReservation
condition|)
block|{
name|reservationEl
operator|.
name|addAttribute
argument_list|(
literal|"type"
argument_list|,
literal|"individual"
argument_list|)
expr_stmt|;
for|for
control|(
name|Student
name|student
range|:
operator|(
operator|(
name|IndividualReservation
operator|)
name|reservation
operator|)
operator|.
name|getStudents
argument_list|()
control|)
block|{
name|reservationEl
operator|.
name|addElement
argument_list|(
literal|"student"
argument_list|)
operator|.
name|addAttribute
argument_list|(
literal|"externalId"
argument_list|,
name|student
operator|.
name|getExternalUniqueId
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
if|else if
condition|(
name|reservation
operator|instanceof
name|StudentGroupReservation
condition|)
block|{
name|reservationEl
operator|.
name|addAttribute
argument_list|(
literal|"type"
argument_list|,
literal|"group"
argument_list|)
expr_stmt|;
name|StudentGroup
name|group
init|=
operator|(
operator|(
name|StudentGroupReservation
operator|)
name|reservation
operator|)
operator|.
name|getGroup
argument_list|()
decl_stmt|;
name|Element
name|groupEl
init|=
name|reservationEl
operator|.
name|addElement
argument_list|(
literal|"studentGroup"
argument_list|)
decl_stmt|;
if|if
condition|(
name|group
operator|.
name|getExternalUniqueId
argument_list|()
operator|!=
literal|null
condition|)
name|groupEl
operator|.
name|addAttribute
argument_list|(
literal|"externalId"
argument_list|,
name|group
operator|.
name|getExternalUniqueId
argument_list|()
argument_list|)
expr_stmt|;
name|groupEl
operator|.
name|addAttribute
argument_list|(
literal|"code"
argument_list|,
name|group
operator|.
name|getGroupAbbreviation
argument_list|()
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|reservation
operator|instanceof
name|CurriculumReservation
condition|)
block|{
name|reservationEl
operator|.
name|addAttribute
argument_list|(
literal|"type"
argument_list|,
literal|"curriculum"
argument_list|)
expr_stmt|;
name|CurriculumReservation
name|curRes
init|=
operator|(
name|CurriculumReservation
operator|)
name|reservation
decl_stmt|;
name|Element
name|acadAreaEl
init|=
name|reservationEl
operator|.
name|addElement
argument_list|(
literal|"academicArea"
argument_list|)
decl_stmt|;
if|if
condition|(
name|curRes
operator|.
name|getArea
argument_list|()
operator|.
name|getExternalUniqueId
argument_list|()
operator|!=
literal|null
condition|)
name|acadAreaEl
operator|.
name|addAttribute
argument_list|(
literal|"externalId"
argument_list|,
name|curRes
operator|.
name|getArea
argument_list|()
operator|.
name|getExternalUniqueId
argument_list|()
argument_list|)
expr_stmt|;
name|acadAreaEl
operator|.
name|addAttribute
argument_list|(
literal|"abbreviation"
argument_list|,
name|curRes
operator|.
name|getArea
argument_list|()
operator|.
name|getAcademicAreaAbbreviation
argument_list|()
argument_list|)
expr_stmt|;
for|for
control|(
name|AcademicClassification
name|clasf
range|:
name|curRes
operator|.
name|getClassifications
argument_list|()
control|)
block|{
name|Element
name|clasfEl
init|=
name|reservationEl
operator|.
name|addElement
argument_list|(
literal|"academicClassification"
argument_list|)
decl_stmt|;
if|if
condition|(
name|clasf
operator|.
name|getExternalUniqueId
argument_list|()
operator|!=
literal|null
condition|)
name|clasfEl
operator|.
name|addAttribute
argument_list|(
literal|"externalId"
argument_list|,
name|clasf
operator|.
name|getExternalUniqueId
argument_list|()
argument_list|)
expr_stmt|;
name|clasfEl
operator|.
name|addAttribute
argument_list|(
literal|"code"
argument_list|,
name|clasf
operator|.
name|getCode
argument_list|()
argument_list|)
expr_stmt|;
block|}
for|for
control|(
name|PosMajor
name|major
range|:
name|curRes
operator|.
name|getMajors
argument_list|()
control|)
block|{
name|Element
name|majorEl
init|=
name|reservationEl
operator|.
name|addElement
argument_list|(
literal|"major"
argument_list|)
decl_stmt|;
if|if
condition|(
name|major
operator|.
name|getExternalUniqueId
argument_list|()
operator|!=
literal|null
condition|)
name|majorEl
operator|.
name|addAttribute
argument_list|(
literal|"externalId"
argument_list|,
name|major
operator|.
name|getExternalUniqueId
argument_list|()
argument_list|)
expr_stmt|;
name|majorEl
operator|.
name|addAttribute
argument_list|(
literal|"code"
argument_list|,
name|major
operator|.
name|getCode
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
if|else if
condition|(
name|reservation
operator|instanceof
name|CourseReservation
condition|)
block|{
if|if
condition|(
name|course
operator|.
name|getReservation
argument_list|()
operator|!=
literal|null
condition|)
name|reservationEl
operator|.
name|addAttribute
argument_list|(
literal|"limit"
argument_list|,
name|course
operator|.
name|getReservation
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
name|reservationEl
operator|.
name|addAttribute
argument_list|(
literal|"type"
argument_list|,
literal|"course"
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|reservation
operator|instanceof
name|OverrideReservation
condition|)
block|{
name|OverrideReservation
name|ovRes
init|=
operator|(
name|OverrideReservation
operator|)
name|reservation
decl_stmt|;
name|reservationEl
operator|.
name|addAttribute
argument_list|(
literal|"type"
argument_list|,
name|ovRes
operator|.
name|getOverrideType
argument_list|()
operator|.
name|getReference
argument_list|()
argument_list|)
expr_stmt|;
for|for
control|(
name|Student
name|student
range|:
name|ovRes
operator|.
name|getStudents
argument_list|()
control|)
block|{
name|reservationEl
operator|.
name|addElement
argument_list|(
literal|"student"
argument_list|)
operator|.
name|addAttribute
argument_list|(
literal|"externalId"
argument_list|,
name|student
operator|.
name|getExternalUniqueId
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
name|reservationEl
operator|.
name|addAttribute
argument_list|(
literal|"type"
argument_list|,
literal|"unknown"
argument_list|)
expr_stmt|;
block|}
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
block|}
block|}
block|}
end_class

end_unit

