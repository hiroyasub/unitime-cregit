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
name|reports
operator|.
name|exam
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|File
import|;
end_import

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
name|Collection
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
name|TreeSet
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Vector
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
name|ExamType
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
name|SubjectArea
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
name|solver
operator|.
name|exam
operator|.
name|ui
operator|.
name|ExamAssignment
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
name|solver
operator|.
name|exam
operator|.
name|ui
operator|.
name|ExamAssignmentInfo
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
name|solver
operator|.
name|exam
operator|.
name|ui
operator|.
name|ExamRoomInfo
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
name|solver
operator|.
name|exam
operator|.
name|ui
operator|.
name|ExamInfo
operator|.
name|ExamSectionInfo
import|;
end_import

begin_import
import|import
name|com
operator|.
name|lowagie
operator|.
name|text
operator|.
name|DocumentException
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|AbbvExamScheduleByCourseReport
extends|extends
name|PdfLegacyExamReport
block|{
specifier|public
name|AbbvExamScheduleByCourseReport
parameter_list|(
name|int
name|mode
parameter_list|,
name|File
name|file
parameter_list|,
name|Session
name|session
parameter_list|,
name|ExamType
name|examType
parameter_list|,
name|Collection
argument_list|<
name|SubjectArea
argument_list|>
name|subjectAreas
parameter_list|,
name|Collection
argument_list|<
name|ExamAssignmentInfo
argument_list|>
name|exams
parameter_list|)
throws|throws
name|IOException
throws|,
name|DocumentException
block|{
name|super
argument_list|(
name|mode
argument_list|,
name|file
argument_list|,
literal|"SCHEDULE BY COURSE"
argument_list|,
name|session
argument_list|,
name|examType
argument_list|,
name|subjectAreas
argument_list|,
name|exams
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|printReport
parameter_list|()
throws|throws
name|DocumentException
block|{
name|Vector
argument_list|<
name|String
argument_list|>
name|lines
init|=
operator|new
name|Vector
argument_list|()
decl_stmt|;
name|int
name|n
init|=
name|iNrLines
operator|-
literal|2
decl_stmt|;
if|if
condition|(
operator|!
name|iDispRooms
condition|)
block|{
name|ExamSectionInfo
name|last
init|=
literal|null
decl_stmt|;
name|int
name|lx
init|=
literal|0
decl_stmt|;
for|for
control|(
name|ExamAssignment
name|exam
range|:
operator|new
name|TreeSet
argument_list|<
name|ExamAssignment
argument_list|>
argument_list|(
name|getExams
argument_list|()
argument_list|)
control|)
block|{
if|if
condition|(
name|exam
operator|.
name|getPeriod
argument_list|()
operator|==
literal|null
operator|||
operator|!
name|hasSubjectArea
argument_list|(
name|exam
argument_list|)
condition|)
continue|continue;
name|boolean
name|firstSection
init|=
literal|true
decl_stmt|;
name|TreeSet
argument_list|<
name|ExamSectionInfo
argument_list|>
name|sections
init|=
operator|new
name|TreeSet
argument_list|<
name|ExamSectionInfo
argument_list|>
argument_list|(
operator|new
name|Comparator
argument_list|<
name|ExamSectionInfo
argument_list|>
argument_list|()
block|{
specifier|public
name|int
name|compare
parameter_list|(
name|ExamSectionInfo
name|s1
parameter_list|,
name|ExamSectionInfo
name|s2
parameter_list|)
block|{
if|if
condition|(
operator|!
name|hasSubjectAreas
argument_list|()
condition|)
return|return
name|s1
operator|.
name|compareTo
argument_list|(
name|s2
argument_list|)
return|;
if|if
condition|(
name|hasSubjectArea
argument_list|(
name|s1
operator|.
name|getOwner
argument_list|()
operator|.
name|getCourse
argument_list|()
operator|.
name|getSubjectArea
argument_list|()
argument_list|)
condition|)
block|{
if|if
condition|(
operator|!
name|hasSubjectArea
argument_list|(
name|s2
operator|.
name|getOwner
argument_list|()
operator|.
name|getCourse
argument_list|()
operator|.
name|getSubjectArea
argument_list|()
argument_list|)
condition|)
return|return
operator|-
literal|1
return|;
block|}
if|else if
condition|(
name|hasSubjectArea
argument_list|(
name|s2
operator|.
name|getOwner
argument_list|()
operator|.
name|getCourse
argument_list|()
operator|.
name|getSubjectArea
argument_list|()
argument_list|)
condition|)
return|return
literal|1
return|;
return|return
name|s1
operator|.
name|compareTo
argument_list|(
name|s2
argument_list|)
return|;
block|}
block|}
argument_list|)
decl_stmt|;
name|sections
operator|.
name|addAll
argument_list|(
name|exam
operator|.
name|getSectionsIncludeCrosslistedDummies
argument_list|()
argument_list|)
expr_stmt|;
for|for
control|(
name|ExamSectionInfo
name|section
range|:
name|sections
control|)
block|{
name|boolean
name|sameSubj
init|=
literal|false
decl_stmt|,
name|sameCrs
init|=
literal|false
decl_stmt|,
name|sameSct
init|=
literal|false
decl_stmt|,
name|sameItype
init|=
literal|false
decl_stmt|;
if|if
condition|(
operator|(
name|lx
operator|%
name|n
operator|)
operator|!=
literal|0
operator|&&
name|last
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
name|last
operator|.
name|getSubject
argument_list|()
operator|.
name|equals
argument_list|(
name|section
operator|.
name|getSubject
argument_list|()
argument_list|)
condition|)
block|{
name|sameSubj
operator|=
literal|true
expr_stmt|;
if|if
condition|(
name|last
operator|.
name|getCourseNbr
argument_list|()
operator|.
name|equals
argument_list|(
name|section
operator|.
name|getCourseNbr
argument_list|()
argument_list|)
condition|)
block|{
name|sameCrs
operator|=
literal|true
expr_stmt|;
if|if
condition|(
name|last
operator|.
name|getSection
argument_list|()
operator|.
name|equals
argument_list|(
name|section
operator|.
name|getSection
argument_list|()
argument_list|)
condition|)
name|sameSct
operator|=
literal|true
expr_stmt|;
if|if
condition|(
name|last
operator|.
name|getItype
argument_list|()
operator|.
name|equals
argument_list|(
name|section
operator|.
name|getItype
argument_list|()
argument_list|)
condition|)
name|sameItype
operator|=
literal|true
expr_stmt|;
block|}
block|}
block|}
name|last
operator|=
name|section
expr_stmt|;
name|lx
operator|++
expr_stmt|;
if|if
condition|(
name|firstSection
condition|)
block|{
if|if
condition|(
name|iItype
condition|)
block|{
name|lines
operator|.
name|add
argument_list|(
name|rpad
argument_list|(
name|sameSubj
condition|?
literal|""
else|:
name|section
operator|.
name|getSubject
argument_list|()
argument_list|,
literal|4
argument_list|)
operator|+
literal|" "
operator|+
name|rpad
argument_list|(
name|sameCrs
condition|?
literal|""
else|:
name|section
operator|.
name|getCourseNbr
argument_list|()
argument_list|,
literal|5
argument_list|)
operator|+
literal|" "
operator|+
name|rpad
argument_list|(
name|sameItype
condition|?
literal|""
else|:
name|section
operator|.
name|getItype
argument_list|()
operator|.
name|length
argument_list|()
operator|==
literal|0
condition|?
literal|"ALL"
else|:
name|section
operator|.
name|getItype
argument_list|()
argument_list|,
literal|5
argument_list|)
operator|+
literal|" "
operator|+
name|lpad
argument_list|(
name|sameSct
condition|?
literal|""
else|:
name|section
operator|.
name|getSection
argument_list|()
argument_list|,
literal|3
argument_list|)
operator|+
literal|" "
operator|+
name|formatShortPeriod
argument_list|(
name|section
operator|.
name|getExamAssignment
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|lines
operator|.
name|add
argument_list|(
name|rpad
argument_list|(
name|sameSubj
condition|?
literal|""
else|:
name|section
operator|.
name|getSubject
argument_list|()
argument_list|,
literal|4
argument_list|)
operator|+
literal|" "
operator|+
name|rpad
argument_list|(
name|sameCrs
condition|?
literal|""
else|:
name|section
operator|.
name|getCourseNbr
argument_list|()
argument_list|,
literal|5
argument_list|)
operator|+
literal|" "
operator|+
name|rpad
argument_list|(
name|sameSct
condition|?
literal|""
else|:
name|section
operator|.
name|getSection
argument_list|()
operator|.
name|length
argument_list|()
operator|==
literal|0
condition|?
literal|"ALL"
else|:
name|section
operator|.
name|getSection
argument_list|()
argument_list|,
literal|3
argument_list|)
operator|+
literal|"  "
operator|+
name|formatPeriod
argument_list|(
name|exam
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
if|if
condition|(
name|iItype
condition|)
block|{
name|String
name|w
init|=
literal|"w/"
operator|+
operator|(
name|sameCrs
condition|?
literal|""
else|:
name|section
operator|.
name|getSubject
argument_list|()
operator|+
literal|" "
operator|)
operator|+
operator|(
name|sameCrs
condition|?
literal|""
else|:
name|section
operator|.
name|getCourseNbr
argument_list|()
operator|+
literal|" "
operator|)
operator|+
operator|(
name|sameItype
condition|?
literal|""
else|:
operator|(
name|section
operator|.
name|getItype
argument_list|()
operator|.
name|length
argument_list|()
operator|==
literal|0
condition|?
literal|"ALL"
else|:
name|section
operator|.
name|getItype
argument_list|()
operator|)
operator|+
literal|" "
operator|)
operator|+
operator|(
name|sameSct
condition|?
literal|""
else|:
name|section
operator|.
name|getSection
argument_list|()
operator|)
decl_stmt|;
if|if
condition|(
name|w
operator|.
name|length
argument_list|()
operator|<
literal|20
condition|)
name|w
operator|=
name|lpad
argument_list|(
name|w
argument_list|,
literal|20
argument_list|)
expr_stmt|;
name|lines
operator|.
name|add
argument_list|(
name|w
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|String
name|w
init|=
literal|"w/"
operator|+
operator|(
name|sameCrs
condition|?
literal|""
else|:
name|section
operator|.
name|getSubject
argument_list|()
operator|+
literal|" "
operator|)
operator|+
operator|(
name|sameCrs
condition|?
literal|""
else|:
name|section
operator|.
name|getCourseNbr
argument_list|()
operator|+
literal|" "
operator|)
operator|+
operator|(
name|sameSct
condition|?
literal|""
else|:
name|section
operator|.
name|getSection
argument_list|()
operator|.
name|length
argument_list|()
operator|==
literal|0
condition|?
literal|"ALL"
else|:
name|section
operator|.
name|getSection
argument_list|()
operator|)
decl_stmt|;
if|if
condition|(
name|w
operator|.
name|length
argument_list|()
operator|<
literal|14
condition|)
name|w
operator|=
name|lpad
argument_list|(
name|w
argument_list|,
literal|14
argument_list|)
expr_stmt|;
name|lines
operator|.
name|add
argument_list|(
name|w
argument_list|)
expr_stmt|;
block|}
block|}
name|firstSection
operator|=
literal|false
expr_stmt|;
block|}
block|}
if|if
condition|(
name|iItype
condition|)
block|{
if|if
condition|(
name|iExternal
condition|)
name|setHeader
argument_list|(
operator|new
name|String
index|[]
block|{
literal|"Subj CrsNr ExtID Sct Date    Time          | Subj CrsNr ExtID Sct Date    Time          | Subj CrsNr ExtID Sct Date    Time         "
block|,
literal|"---- ----- ----- --- ------- ------------- | ---- ----- ----- --- ------- ------------- | ---- ----- ----- --- ------- -------------"
block|}
argument_list|)
expr_stmt|;
else|else
name|setHeader
argument_list|(
operator|new
name|String
index|[]
block|{
literal|"Subj CrsNr InsTp Sct Date    Time          | Subj CrsNr InsTp Sct Date    Time          | Subj CrsNr InsTp Sct Date    Time         "
block|,
literal|"---- ----- ----- --- ------- ------------- | ---- ----- ----- --- ------- ------------- | ---- ----- ----- --- ------- -------------"
block|}
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|setHeader
argument_list|(
operator|new
name|String
index|[]
block|{
literal|"  Subj CrsNr Sct  Date      Time            | Subj CrsNr Sct  Date      Time            | Subj CrsNr Sct  Date      Time           "
block|,
literal|"  ---- ----- ---  --------- --------------- | ---- ----- ---  --------- --------------- | ---- ----- ---  --------- ---------------"
block|}
argument_list|)
expr_stmt|;
block|}
name|printHeader
argument_list|()
expr_stmt|;
for|for
control|(
name|int
name|idx
init|=
literal|0
init|;
name|idx
operator|<
name|lines
operator|.
name|size
argument_list|()
condition|;
name|idx
operator|+=
literal|3
operator|*
name|n
control|)
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
name|n
condition|;
name|i
operator|++
control|)
block|{
name|String
name|a
init|=
operator|(
name|i
operator|+
name|idx
operator|+
literal|0
operator|*
name|n
operator|<
name|lines
operator|.
name|size
argument_list|()
condition|?
name|lines
operator|.
name|elementAt
argument_list|(
name|i
operator|+
name|idx
operator|+
literal|0
operator|*
name|n
argument_list|)
else|:
literal|""
operator|)
decl_stmt|;
name|String
name|b
init|=
operator|(
name|i
operator|+
name|idx
operator|+
literal|1
operator|*
name|n
operator|<
name|lines
operator|.
name|size
argument_list|()
condition|?
name|lines
operator|.
name|elementAt
argument_list|(
name|i
operator|+
name|idx
operator|+
literal|1
operator|*
name|n
argument_list|)
else|:
literal|""
operator|)
decl_stmt|;
name|String
name|c
init|=
operator|(
name|i
operator|+
name|idx
operator|+
literal|2
operator|*
name|n
operator|<
name|lines
operator|.
name|size
argument_list|()
condition|?
name|lines
operator|.
name|elementAt
argument_list|(
name|i
operator|+
name|idx
operator|+
literal|2
operator|*
name|n
argument_list|)
else|:
literal|""
operator|)
decl_stmt|;
if|if
condition|(
name|iItype
condition|)
name|println
argument_list|(
name|rpad
argument_list|(
name|a
argument_list|,
literal|42
argument_list|)
operator|+
literal|" | "
operator|+
name|rpad
argument_list|(
name|b
argument_list|,
literal|42
argument_list|)
operator|+
literal|" | "
operator|+
name|c
argument_list|)
expr_stmt|;
else|else
name|println
argument_list|(
literal|"  "
operator|+
name|rpad
argument_list|(
name|a
argument_list|,
literal|41
argument_list|)
operator|+
literal|" | "
operator|+
name|rpad
argument_list|(
name|b
argument_list|,
literal|41
argument_list|)
operator|+
literal|" | "
operator|+
name|c
argument_list|)
expr_stmt|;
block|}
block|}
block|}
else|else
block|{
name|ExamSectionInfo
name|last
init|=
literal|null
decl_stmt|;
name|int
name|lx
init|=
literal|0
decl_stmt|;
for|for
control|(
name|ExamAssignment
name|exam
range|:
operator|new
name|TreeSet
argument_list|<
name|ExamAssignment
argument_list|>
argument_list|(
name|getExams
argument_list|()
argument_list|)
control|)
block|{
if|if
condition|(
name|exam
operator|.
name|getPeriod
argument_list|()
operator|==
literal|null
operator|||
operator|!
name|hasSubjectArea
argument_list|(
name|exam
argument_list|)
condition|)
continue|continue;
name|Vector
argument_list|<
name|String
argument_list|>
name|rooms
init|=
operator|new
name|Vector
argument_list|()
decl_stmt|;
if|if
condition|(
name|exam
operator|.
name|getRooms
argument_list|()
operator|==
literal|null
operator|||
name|exam
operator|.
name|getRooms
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|rooms
operator|.
name|add
argument_list|(
name|rpad
argument_list|(
name|iNoRoom
argument_list|,
literal|11
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
for|for
control|(
name|ExamRoomInfo
name|room
range|:
name|exam
operator|.
name|getRooms
argument_list|()
control|)
block|{
name|rooms
operator|.
name|add
argument_list|(
name|formatRoom
argument_list|(
name|room
operator|.
name|getName
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|Vector
argument_list|<
name|ExamSectionInfo
argument_list|>
name|sections
init|=
operator|new
name|Vector
argument_list|(
name|exam
operator|.
name|getSectionsIncludeCrosslistedDummies
argument_list|()
argument_list|)
decl_stmt|;
name|Collections
operator|.
name|sort
argument_list|(
name|sections
argument_list|,
operator|new
name|Comparator
argument_list|<
name|ExamSectionInfo
argument_list|>
argument_list|()
block|{
specifier|public
name|int
name|compare
parameter_list|(
name|ExamSectionInfo
name|s1
parameter_list|,
name|ExamSectionInfo
name|s2
parameter_list|)
block|{
if|if
condition|(
operator|!
name|hasSubjectAreas
argument_list|()
condition|)
return|return
name|s1
operator|.
name|compareTo
argument_list|(
name|s2
argument_list|)
return|;
if|if
condition|(
name|hasSubjectArea
argument_list|(
name|s1
operator|.
name|getOwner
argument_list|()
operator|.
name|getCourse
argument_list|()
operator|.
name|getSubjectArea
argument_list|()
argument_list|)
condition|)
block|{
if|if
condition|(
operator|!
name|hasSubjectArea
argument_list|(
name|s2
operator|.
name|getOwner
argument_list|()
operator|.
name|getCourse
argument_list|()
operator|.
name|getSubjectArea
argument_list|()
argument_list|)
condition|)
return|return
operator|-
literal|1
return|;
block|}
if|else if
condition|(
name|hasSubjectArea
argument_list|(
name|s2
operator|.
name|getOwner
argument_list|()
operator|.
name|getCourse
argument_list|()
operator|.
name|getSubjectArea
argument_list|()
argument_list|)
condition|)
return|return
literal|1
return|;
return|return
name|s1
operator|.
name|compareTo
argument_list|(
name|s2
argument_list|)
return|;
block|}
block|}
argument_list|)
expr_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|Math
operator|.
name|max
argument_list|(
operator|(
name|rooms
operator|.
name|size
argument_list|()
operator|+
literal|1
operator|)
operator|/
literal|2
argument_list|,
name|sections
operator|.
name|size
argument_list|()
argument_list|)
condition|;
name|i
operator|++
control|)
block|{
name|String
name|a
init|=
operator|(
literal|2
operator|*
name|i
operator|+
literal|0
operator|<
name|rooms
operator|.
name|size
argument_list|()
condition|?
name|rooms
operator|.
name|elementAt
argument_list|(
literal|2
operator|*
name|i
operator|+
literal|0
argument_list|)
else|:
name|rpad
argument_list|(
literal|""
argument_list|,
literal|11
argument_list|)
operator|)
decl_stmt|;
name|String
name|b
init|=
operator|(
literal|2
operator|*
name|i
operator|+
literal|1
operator|<
name|rooms
operator|.
name|size
argument_list|()
condition|?
name|rooms
operator|.
name|elementAt
argument_list|(
literal|2
operator|*
name|i
operator|+
literal|1
argument_list|)
else|:
name|rpad
argument_list|(
literal|""
argument_list|,
literal|11
argument_list|)
operator|)
decl_stmt|;
name|ExamSectionInfo
name|section
init|=
operator|(
name|i
operator|<
name|sections
operator|.
name|size
argument_list|()
condition|?
name|sections
operator|.
name|elementAt
argument_list|(
name|i
argument_list|)
else|:
literal|null
operator|)
decl_stmt|;
name|boolean
name|sameSubj
init|=
literal|false
decl_stmt|,
name|sameCrs
init|=
literal|false
decl_stmt|,
name|sameSct
init|=
literal|false
decl_stmt|,
name|sameItype
init|=
literal|false
decl_stmt|;
if|if
condition|(
operator|(
name|lx
operator|%
name|n
operator|)
operator|!=
literal|0
operator|&&
name|last
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
name|section
operator|!=
literal|null
operator|&&
name|last
operator|.
name|getSubject
argument_list|()
operator|.
name|equals
argument_list|(
name|section
operator|.
name|getSubject
argument_list|()
argument_list|)
condition|)
block|{
name|sameSubj
operator|=
literal|true
expr_stmt|;
if|if
condition|(
name|last
operator|.
name|getCourseNbr
argument_list|()
operator|.
name|equals
argument_list|(
name|section
operator|.
name|getCourseNbr
argument_list|()
argument_list|)
condition|)
block|{
name|sameCrs
operator|=
literal|true
expr_stmt|;
if|if
condition|(
name|last
operator|.
name|getSection
argument_list|()
operator|.
name|equals
argument_list|(
name|section
operator|.
name|getSection
argument_list|()
argument_list|)
condition|)
name|sameSct
operator|=
literal|true
expr_stmt|;
if|if
condition|(
name|last
operator|.
name|getItype
argument_list|()
operator|.
name|equals
argument_list|(
name|section
operator|.
name|getItype
argument_list|()
argument_list|)
condition|)
name|sameItype
operator|=
literal|true
expr_stmt|;
block|}
block|}
block|}
if|if
condition|(
name|i
operator|==
literal|0
condition|)
block|{
if|if
condition|(
name|iItype
condition|)
block|{
name|lines
operator|.
name|add
argument_list|(
name|rpad
argument_list|(
name|sameSubj
condition|?
literal|""
else|:
name|section
operator|.
name|getSubject
argument_list|()
argument_list|,
literal|4
argument_list|)
operator|+
literal|" "
operator|+
name|rpad
argument_list|(
name|sameCrs
condition|?
literal|""
else|:
name|section
operator|.
name|getCourseNbr
argument_list|()
argument_list|,
literal|5
argument_list|)
operator|+
literal|" "
operator|+
name|rpad
argument_list|(
name|sameItype
condition|?
literal|""
else|:
name|section
operator|.
name|getItype
argument_list|()
operator|.
name|length
argument_list|()
operator|==
literal|0
condition|?
literal|"ALL"
else|:
name|section
operator|.
name|getItype
argument_list|()
argument_list|,
literal|5
argument_list|)
operator|+
literal|" "
operator|+
name|lpad
argument_list|(
name|sameSct
condition|?
literal|""
else|:
name|section
operator|.
name|getSection
argument_list|()
argument_list|,
literal|3
argument_list|)
operator|+
literal|" "
operator|+
name|formatShortPeriod
argument_list|(
name|section
operator|.
name|getExamAssignment
argument_list|()
argument_list|)
operator|+
literal|" "
operator|+
name|a
operator|+
literal|" "
operator|+
name|b
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|lines
operator|.
name|add
argument_list|(
operator|(
name|iItype
condition|?
name|rpad
argument_list|(
name|section
operator|.
name|getName
argument_list|()
argument_list|,
literal|14
argument_list|)
else|:
name|rpad
argument_list|(
name|sameSubj
condition|?
literal|""
else|:
name|section
operator|.
name|getSubject
argument_list|()
argument_list|,
literal|4
argument_list|)
operator|+
literal|" "
operator|+
name|rpad
argument_list|(
name|sameCrs
condition|?
literal|""
else|:
name|section
operator|.
name|getCourseNbr
argument_list|()
argument_list|,
literal|5
argument_list|)
operator|+
literal|" "
operator|+
name|rpad
argument_list|(
name|sameSct
condition|?
literal|""
else|:
name|section
operator|.
name|getSection
argument_list|()
operator|.
name|length
argument_list|()
operator|==
literal|0
condition|?
literal|"ALL"
else|:
name|section
operator|.
name|getSection
argument_list|()
argument_list|,
literal|3
argument_list|)
operator|)
operator|+
literal|"  "
operator|+
name|formatPeriod
argument_list|(
name|section
operator|.
name|getExamAssignment
argument_list|()
argument_list|)
operator|+
literal|" "
operator|+
name|a
operator|+
literal|" "
operator|+
name|b
argument_list|)
expr_stmt|;
block|}
block|}
if|else if
condition|(
name|section
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
name|iItype
condition|)
block|{
name|String
name|w
init|=
literal|"w/"
operator|+
operator|(
name|sameCrs
condition|?
literal|""
else|:
name|section
operator|.
name|getSubject
argument_list|()
operator|+
literal|" "
operator|)
operator|+
operator|(
name|sameCrs
condition|?
literal|""
else|:
name|section
operator|.
name|getCourseNbr
argument_list|()
operator|+
literal|" "
operator|)
operator|+
operator|(
name|sameItype
condition|?
literal|""
else|:
operator|(
name|section
operator|.
name|getItype
argument_list|()
operator|.
name|length
argument_list|()
operator|==
literal|0
condition|?
literal|"ALL"
else|:
name|section
operator|.
name|getItype
argument_list|()
operator|)
operator|+
literal|" "
operator|)
operator|+
operator|(
name|sameSct
condition|?
literal|""
else|:
name|section
operator|.
name|getSection
argument_list|()
operator|)
decl_stmt|;
if|if
condition|(
name|w
operator|.
name|length
argument_list|()
operator|<
literal|20
condition|)
name|w
operator|=
name|lpad
argument_list|(
name|w
argument_list|,
literal|20
argument_list|)
expr_stmt|;
name|lines
operator|.
name|add
argument_list|(
name|rpad
argument_list|(
name|w
argument_list|,
literal|43
argument_list|)
operator|+
name|a
operator|+
literal|" "
operator|+
name|b
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|String
name|w
init|=
literal|"w/"
operator|+
operator|(
name|sameCrs
condition|?
literal|""
else|:
name|section
operator|.
name|getSubject
argument_list|()
operator|+
literal|" "
operator|)
operator|+
operator|(
name|sameCrs
condition|?
literal|""
else|:
name|section
operator|.
name|getCourseNbr
argument_list|()
operator|+
literal|" "
operator|)
operator|+
operator|(
name|sameSct
condition|?
literal|""
else|:
name|section
operator|.
name|getSection
argument_list|()
operator|.
name|length
argument_list|()
operator|==
literal|0
condition|?
literal|"ALL"
else|:
name|section
operator|.
name|getSection
argument_list|()
operator|)
decl_stmt|;
if|if
condition|(
name|w
operator|.
name|length
argument_list|()
operator|<
literal|14
condition|)
name|w
operator|=
name|lpad
argument_list|(
name|w
argument_list|,
literal|14
argument_list|)
expr_stmt|;
name|lines
operator|.
name|add
argument_list|(
name|rpad
argument_list|(
name|w
argument_list|,
literal|42
argument_list|)
operator|+
name|a
operator|+
literal|" "
operator|+
name|b
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
name|lines
operator|.
name|add
argument_list|(
name|rpad
argument_list|(
literal|""
argument_list|,
operator|(
name|iItype
condition|?
literal|43
else|:
literal|42
operator|)
argument_list|)
operator|+
name|a
operator|+
literal|" "
operator|+
name|b
argument_list|)
expr_stmt|;
block|}
name|lx
operator|++
expr_stmt|;
block|}
block|}
if|if
condition|(
name|iItype
condition|)
block|{
if|if
condition|(
name|iExternal
condition|)
name|setHeader
argument_list|(
operator|new
name|String
index|[]
block|{
literal|"Subj CrsNr ExtID Sct Date    Time          Bldg  Room  Bldg  Room | Subj CrsNr ExtID Sct Date    Time          Bldg  Room  Bldg  Room"
block|,
literal|"---- ----- ----- --- ------- ------------- ----- ----- ----- ---- | ---- ----- ----- --- ------- ------------- ----- ----- ----- ----"
block|}
argument_list|)
expr_stmt|;
else|else
name|setHeader
argument_list|(
operator|new
name|String
index|[]
block|{
literal|"Subj CrsNr InsTp Sct Date    Time          Bldg  Room  Bldg  Room | Subj CrsNr InsTp Sct Date    Time          Bldg  Room  Bldg  Room"
block|,
literal|"---- ----- ----- --- ------- ------------- ----- ----- ----- ---- | ---- ----- ----- --- ------- ------------- ----- ----- ----- ----"
block|}
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|setHeader
argument_list|(
operator|new
name|String
index|[]
block|{
literal|"Subj CrsNr Sct  Date      Time            Bldg  Room  Bldg  Room  | Subj CrsNr Sct  Date      Time            Bldg  Room  Bldg  Room "
block|,
literal|"---- ----- ---  --------- --------------- ----- ----- ----- ----- | ---- ----- ---  --------- --------------- ----- ----- ----- -----"
block|}
argument_list|)
expr_stmt|;
block|}
name|printHeader
argument_list|()
expr_stmt|;
for|for
control|(
name|int
name|idx
init|=
literal|0
init|;
name|idx
operator|<
name|lines
operator|.
name|size
argument_list|()
condition|;
name|idx
operator|+=
literal|2
operator|*
name|n
control|)
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
name|n
condition|;
name|i
operator|++
control|)
block|{
name|String
name|a
init|=
operator|(
name|i
operator|+
name|idx
operator|+
literal|0
operator|*
name|n
operator|<
name|lines
operator|.
name|size
argument_list|()
condition|?
name|lines
operator|.
name|elementAt
argument_list|(
name|i
operator|+
name|idx
operator|+
literal|0
operator|*
name|n
argument_list|)
else|:
name|rpad
argument_list|(
literal|""
argument_list|,
literal|65
argument_list|)
operator|)
decl_stmt|;
name|String
name|b
init|=
operator|(
name|i
operator|+
name|idx
operator|+
literal|1
operator|*
name|n
operator|<
name|lines
operator|.
name|size
argument_list|()
condition|?
name|lines
operator|.
name|elementAt
argument_list|(
name|i
operator|+
name|idx
operator|+
literal|1
operator|*
name|n
argument_list|)
else|:
name|rpad
argument_list|(
literal|""
argument_list|,
literal|65
argument_list|)
operator|)
decl_stmt|;
name|println
argument_list|(
name|rpad
argument_list|(
name|a
argument_list|,
literal|66
argument_list|)
operator|+
literal|"| "
operator|+
name|rpad
argument_list|(
name|b
argument_list|,
literal|65
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
block|}
end_class

end_unit

