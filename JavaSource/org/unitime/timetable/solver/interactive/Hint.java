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
name|solver
operator|.
name|interactive
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|Serializable
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
name|Vector
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
name|HttpSession
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
name|solver
operator|.
name|WebSolver
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
name|ui
operator|.
name|AssignmentPreferenceInfo
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|cpsolver
operator|.
name|coursett
operator|.
name|model
operator|.
name|Lecture
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|cpsolver
operator|.
name|coursett
operator|.
name|model
operator|.
name|Placement
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|cpsolver
operator|.
name|coursett
operator|.
name|model
operator|.
name|RoomLocation
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|cpsolver
operator|.
name|coursett
operator|.
name|model
operator|.
name|TimeLocation
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|cpsolver
operator|.
name|coursett
operator|.
name|model
operator|.
name|TimetableModel
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|cpsolver
operator|.
name|ifs
operator|.
name|solver
operator|.
name|Solver
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|Hint
implements|implements
name|Serializable
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
name|iClassId
decl_stmt|;
specifier|private
name|int
name|iDays
decl_stmt|;
specifier|private
name|int
name|iStartSlot
decl_stmt|;
specifier|private
name|List
argument_list|<
name|Long
argument_list|>
name|iRoomIds
decl_stmt|;
specifier|private
name|Long
name|iPatternId
decl_stmt|;
specifier|private
name|AssignmentPreferenceInfo
name|iInfo
init|=
literal|null
decl_stmt|;
specifier|private
name|ClassAssignmentDetails
name|iDetails
init|=
literal|null
decl_stmt|;
specifier|public
name|Hint
parameter_list|(
name|Long
name|classId
parameter_list|,
name|int
name|days
parameter_list|,
name|int
name|startSlot
parameter_list|,
name|List
argument_list|<
name|Long
argument_list|>
name|roomIds
parameter_list|,
name|Long
name|patternId
parameter_list|)
block|{
name|iClassId
operator|=
name|classId
expr_stmt|;
name|iDays
operator|=
name|days
expr_stmt|;
name|iStartSlot
operator|=
name|startSlot
expr_stmt|;
name|iRoomIds
operator|=
name|roomIds
expr_stmt|;
name|iPatternId
operator|=
name|patternId
expr_stmt|;
block|}
specifier|public
name|Hint
parameter_list|(
name|Solver
name|solver
parameter_list|,
name|Placement
name|placement
parameter_list|)
block|{
name|this
argument_list|(
name|solver
argument_list|,
name|placement
argument_list|,
literal|false
argument_list|)
expr_stmt|;
block|}
specifier|public
name|Hint
parameter_list|(
name|Solver
name|solver
parameter_list|,
name|Placement
name|placement
parameter_list|,
name|boolean
name|populateInfo
parameter_list|)
block|{
name|iClassId
operator|=
operator|(
operator|(
name|Lecture
operator|)
name|placement
operator|.
name|variable
argument_list|()
operator|)
operator|.
name|getClassId
argument_list|()
expr_stmt|;
name|iDays
operator|=
name|placement
operator|.
name|getTimeLocation
argument_list|()
operator|.
name|getDayCode
argument_list|()
expr_stmt|;
name|iStartSlot
operator|=
name|placement
operator|.
name|getTimeLocation
argument_list|()
operator|.
name|getStartSlot
argument_list|()
expr_stmt|;
name|iRoomIds
operator|=
name|placement
operator|.
name|getRoomIds
argument_list|()
expr_stmt|;
name|iPatternId
operator|=
name|placement
operator|.
name|getTimeLocation
argument_list|()
operator|.
name|getTimePatternId
argument_list|()
expr_stmt|;
if|if
condition|(
name|populateInfo
operator|&&
name|solver
operator|!=
literal|null
condition|)
name|iInfo
operator|=
operator|new
name|AssignmentPreferenceInfo
argument_list|(
name|solver
argument_list|,
name|placement
argument_list|,
literal|false
argument_list|)
expr_stmt|;
if|if
condition|(
name|placement
operator|.
name|variable
argument_list|()
operator|.
name|isCommitted
argument_list|()
operator|&&
name|solver
operator|!=
literal|null
condition|)
name|iDetails
operator|=
operator|new
name|ClassAssignmentDetails
argument_list|(
name|solver
argument_list|,
name|placement
operator|.
name|variable
argument_list|()
argument_list|,
name|placement
argument_list|,
literal|false
argument_list|)
expr_stmt|;
block|}
specifier|public
name|Placement
name|getPlacement
parameter_list|(
name|TimetableModel
name|model
parameter_list|)
block|{
return|return
name|getPlacement
argument_list|(
name|model
argument_list|,
literal|true
argument_list|)
return|;
block|}
specifier|public
name|Placement
name|getPlacement
parameter_list|(
name|TimetableModel
name|model
parameter_list|,
name|boolean
name|checkValidity
parameter_list|)
block|{
for|for
control|(
name|Lecture
name|lecture
range|:
name|model
operator|.
name|variables
argument_list|()
control|)
block|{
if|if
condition|(
operator|!
name|lecture
operator|.
name|getClassId
argument_list|()
operator|.
name|equals
argument_list|(
name|iClassId
argument_list|)
condition|)
continue|continue;
name|TimeLocation
name|timeLocation
init|=
literal|null
decl_stmt|;
for|for
control|(
name|TimeLocation
name|t
range|:
name|lecture
operator|.
name|timeLocations
argument_list|()
control|)
block|{
if|if
condition|(
name|t
operator|.
name|getDayCode
argument_list|()
operator|!=
name|iDays
condition|)
continue|continue;
if|if
condition|(
name|t
operator|.
name|getStartSlot
argument_list|()
operator|!=
name|iStartSlot
condition|)
continue|continue;
if|if
condition|(
operator|!
name|t
operator|.
name|getTimePatternId
argument_list|()
operator|.
name|equals
argument_list|(
name|iPatternId
argument_list|)
condition|)
continue|continue;
name|timeLocation
operator|=
name|t
expr_stmt|;
break|break;
block|}
name|Vector
name|roomLocations
init|=
operator|new
name|Vector
argument_list|()
decl_stmt|;
if|if
condition|(
name|lecture
operator|.
name|getNrRooms
argument_list|()
operator|>
literal|0
condition|)
block|{
for|for
control|(
name|Long
name|roomId
range|:
name|iRoomIds
control|)
block|{
for|for
control|(
name|RoomLocation
name|r
range|:
name|lecture
operator|.
name|roomLocations
argument_list|()
control|)
block|{
if|if
condition|(
name|r
operator|.
name|getId
argument_list|()
operator|.
name|equals
argument_list|(
name|roomId
argument_list|)
condition|)
name|roomLocations
operator|.
name|add
argument_list|(
name|r
argument_list|)
expr_stmt|;
block|}
block|}
block|}
if|if
condition|(
name|timeLocation
operator|!=
literal|null
operator|&&
name|roomLocations
operator|.
name|size
argument_list|()
operator|==
name|lecture
operator|.
name|getNrRooms
argument_list|()
condition|)
block|{
name|Placement
name|placement
init|=
operator|new
name|Placement
argument_list|(
name|lecture
argument_list|,
name|timeLocation
argument_list|,
name|roomLocations
argument_list|)
decl_stmt|;
if|if
condition|(
name|checkValidity
operator|&&
operator|!
name|placement
operator|.
name|isValid
argument_list|()
condition|)
return|return
literal|null
return|;
return|return
name|placement
return|;
block|}
comment|/* 			Vector values = lecture.values(); 	    	if (!lecture.allowBreakHard()) values = lecture.computeValues(true); 			for (Enumeration e2=values.elements();e2.hasMoreElements();) { 				Placement placement = (Placement)e2.nextElement(); 				if (placement.getTimeLocation().getDayCode()!=iDays) continue; 				if (placement.getTimeLocation().getStartSlot()!=iStartSlot) continue; 				boolean sameRooms = true; 				for (Enumeration e3=iRoomIds.elements();sameRooms&& e3.hasMoreElements();) { 					Long roomId = (Integer)e3.nextElement(); 					if (!placement.hasRoomLocation(roomId)) sameRooms = false; 				} 				if (!sameRooms) continue; 				return placement; 			} 			*/
block|}
return|return
literal|null
return|;
block|}
specifier|public
name|AssignmentPreferenceInfo
name|getInfo
parameter_list|(
name|Solver
name|solver
parameter_list|)
block|{
name|Placement
name|p
init|=
name|getPlacement
argument_list|(
operator|(
name|TimetableModel
operator|)
name|solver
operator|.
name|currentSolution
argument_list|()
operator|.
name|getModel
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|p
operator|==
literal|null
condition|)
return|return
literal|null
return|;
return|return
operator|new
name|AssignmentPreferenceInfo
argument_list|(
name|solver
argument_list|,
name|p
argument_list|,
literal|false
argument_list|)
return|;
block|}
specifier|public
name|String
name|getNotValidReason
parameter_list|(
name|Solver
name|solver
parameter_list|)
block|{
name|Placement
name|p
init|=
name|getPlacement
argument_list|(
operator|(
name|TimetableModel
operator|)
name|solver
operator|.
name|currentSolution
argument_list|()
operator|.
name|getModel
argument_list|()
argument_list|,
literal|false
argument_list|)
decl_stmt|;
if|if
condition|(
name|p
operator|==
literal|null
condition|)
return|return
literal|"Selected placement is not valid (room or instructor not avaiable)."
return|;
if|if
condition|(
name|p
operator|.
name|isValid
argument_list|()
condition|)
return|return
literal|"Selected placement is valid."
return|;
name|String
name|reason
init|=
name|p
operator|.
name|getNotValidReason
argument_list|()
decl_stmt|;
return|return
operator|(
name|reason
operator|==
literal|null
condition|?
literal|"Selected placement is not valid (room or instructor not avaiable)."
else|:
literal|"Selected placement is not valid ("
operator|+
name|reason
operator|+
literal|")"
operator|)
return|;
block|}
specifier|public
name|Long
name|getClassId
parameter_list|()
block|{
return|return
name|iClassId
return|;
block|}
specifier|public
name|int
name|getDays
parameter_list|()
block|{
return|return
name|iDays
return|;
block|}
specifier|public
name|int
name|getStartSlot
parameter_list|()
block|{
return|return
name|iStartSlot
return|;
block|}
specifier|public
name|List
argument_list|<
name|Long
argument_list|>
name|getRoomIds
parameter_list|()
block|{
return|return
name|iRoomIds
return|;
block|}
specifier|public
name|Long
name|getPatternId
parameter_list|()
block|{
return|return
name|iPatternId
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
name|Hint
operator|)
condition|)
return|return
literal|false
return|;
return|return
name|iClassId
operator|.
name|equals
argument_list|(
operator|(
operator|(
name|Hint
operator|)
name|o
operator|)
operator|.
name|getClassId
argument_list|()
argument_list|)
return|;
block|}
specifier|public
name|int
name|hashCode
parameter_list|()
block|{
return|return
name|iClassId
operator|.
name|hashCode
argument_list|()
return|;
block|}
specifier|public
name|ClassAssignmentDetails
name|getDetails
parameter_list|(
name|HttpSession
name|session
parameter_list|,
name|boolean
name|includeConstraints
parameter_list|)
throws|throws
name|Exception
block|{
if|if
condition|(
name|iDetails
operator|!=
literal|null
condition|)
return|return
name|iDetails
return|;
if|if
condition|(
name|iInfo
operator|==
literal|null
condition|)
name|iInfo
operator|=
name|WebSolver
operator|.
name|getSolver
argument_list|(
name|session
argument_list|)
operator|.
name|getInfo
argument_list|(
name|this
argument_list|)
expr_stmt|;
name|iDetails
operator|=
name|ClassAssignmentDetails
operator|.
name|createClassAssignmentDetails
argument_list|(
name|session
argument_list|,
name|iClassId
argument_list|,
name|includeConstraints
argument_list|)
expr_stmt|;
if|if
condition|(
name|iDetails
operator|!=
literal|null
condition|)
name|iDetails
operator|.
name|setAssigned
argument_list|(
name|iInfo
argument_list|,
name|iRoomIds
argument_list|,
name|iDays
argument_list|,
name|iStartSlot
argument_list|)
expr_stmt|;
return|return
name|iDetails
return|;
block|}
specifier|public
name|ClassAssignmentDetails
name|getDetailsUnassign
parameter_list|(
name|HttpSession
name|session
parameter_list|,
name|boolean
name|includeConstraints
parameter_list|)
throws|throws
name|Exception
block|{
if|if
condition|(
name|iDetails
operator|!=
literal|null
condition|)
return|return
name|iDetails
return|;
name|iDetails
operator|=
name|ClassAssignmentDetails
operator|.
name|createClassAssignmentDetails
argument_list|(
name|session
argument_list|,
name|iClassId
argument_list|,
name|includeConstraints
argument_list|)
expr_stmt|;
return|return
name|iDetails
return|;
block|}
specifier|public
name|void
name|setDetails
parameter_list|(
name|ClassAssignmentDetails
name|details
parameter_list|)
block|{
name|iDetails
operator|=
name|details
expr_stmt|;
block|}
specifier|public
specifier|static
class|class
name|HintComparator
implements|implements
name|Comparator
block|{
specifier|private
name|Vector
name|iOrder
decl_stmt|;
specifier|public
name|HintComparator
parameter_list|(
name|Vector
name|order
parameter_list|)
block|{
name|iOrder
operator|=
name|order
expr_stmt|;
block|}
specifier|public
name|int
name|compare
parameter_list|(
name|Object
name|o1
parameter_list|,
name|Object
name|o2
parameter_list|)
block|{
name|Hint
name|p1
init|=
operator|(
name|Hint
operator|)
name|o1
decl_stmt|;
name|Hint
name|p2
init|=
operator|(
name|Hint
operator|)
name|o2
decl_stmt|;
name|int
name|i1
init|=
name|iOrder
operator|.
name|indexOf
argument_list|(
name|p1
operator|.
name|getClassId
argument_list|()
argument_list|)
decl_stmt|;
name|int
name|i2
init|=
name|iOrder
operator|.
name|indexOf
argument_list|(
name|p2
operator|.
name|getClassId
argument_list|()
argument_list|)
decl_stmt|;
return|return
operator|(
operator|new
name|Integer
argument_list|(
name|i1
argument_list|)
operator|)
operator|.
name|compareTo
argument_list|(
operator|new
name|Integer
argument_list|(
name|i2
argument_list|)
argument_list|)
return|;
block|}
block|}
specifier|public
name|void
name|toXml
parameter_list|(
name|Element
name|element
parameter_list|)
block|{
name|element
operator|.
name|addAttribute
argument_list|(
literal|"id"
argument_list|,
name|String
operator|.
name|valueOf
argument_list|(
name|iClassId
argument_list|)
argument_list|)
expr_stmt|;
name|element
operator|.
name|addAttribute
argument_list|(
literal|"days"
argument_list|,
name|String
operator|.
name|valueOf
argument_list|(
name|iDays
argument_list|)
argument_list|)
expr_stmt|;
name|element
operator|.
name|addAttribute
argument_list|(
literal|"start"
argument_list|,
name|String
operator|.
name|valueOf
argument_list|(
name|iStartSlot
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|iRoomIds
operator|!=
literal|null
condition|)
block|{
for|for
control|(
name|Long
name|roomId
range|:
name|iRoomIds
control|)
block|{
if|if
condition|(
name|roomId
operator|!=
literal|null
condition|)
name|element
operator|.
name|addElement
argument_list|(
literal|"room"
argument_list|)
operator|.
name|addAttribute
argument_list|(
literal|"id"
argument_list|,
name|roomId
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
if|if
condition|(
name|iPatternId
operator|!=
literal|null
condition|)
name|element
operator|.
name|addAttribute
argument_list|(
literal|"pattern"
argument_list|,
name|iPatternId
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
specifier|public
specifier|static
name|Hint
name|fromXml
parameter_list|(
name|Element
name|element
parameter_list|)
block|{
name|Vector
name|roomIds
init|=
operator|new
name|Vector
argument_list|()
decl_stmt|;
for|for
control|(
name|Iterator
name|i
init|=
name|element
operator|.
name|elementIterator
argument_list|(
literal|"room"
argument_list|)
init|;
name|i
operator|.
name|hasNext
argument_list|()
condition|;
control|)
name|roomIds
operator|.
name|addElement
argument_list|(
name|Long
operator|.
name|valueOf
argument_list|(
operator|(
operator|(
name|Element
operator|)
name|i
operator|.
name|next
argument_list|()
operator|)
operator|.
name|attributeValue
argument_list|(
literal|"id"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
return|return
operator|new
name|Hint
argument_list|(
name|Long
operator|.
name|valueOf
argument_list|(
name|element
operator|.
name|attributeValue
argument_list|(
literal|"id"
argument_list|)
argument_list|)
argument_list|,
name|Integer
operator|.
name|parseInt
argument_list|(
name|element
operator|.
name|attributeValue
argument_list|(
literal|"days"
argument_list|)
argument_list|)
argument_list|,
name|Integer
operator|.
name|parseInt
argument_list|(
name|element
operator|.
name|attributeValue
argument_list|(
literal|"start"
argument_list|)
argument_list|)
argument_list|,
name|roomIds
argument_list|,
operator|(
name|element
operator|.
name|attributeValue
argument_list|(
literal|"pattern"
argument_list|)
operator|==
literal|null
condition|?
literal|null
else|:
name|Long
operator|.
name|valueOf
argument_list|(
name|element
operator|.
name|attributeValue
argument_list|(
literal|"pattern"
argument_list|)
argument_list|)
operator|)
argument_list|)
return|;
block|}
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
literal|"Hint{classId = "
operator|+
name|iClassId
operator|+
literal|", days = "
operator|+
name|iDays
operator|+
literal|", startSlot = "
operator|+
name|iStartSlot
operator|+
literal|", roomIds = "
operator|+
name|iRoomIds
operator|+
literal|"}"
return|;
block|}
block|}
end_class

end_unit

