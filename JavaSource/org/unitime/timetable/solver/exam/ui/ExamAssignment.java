begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
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
name|exam
operator|.
name|ui
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
name|Enumeration
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
name|Exam
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
name|ExamPeriod
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
name|Location
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
name|PreferenceLevel
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
name|ExamPeriodDAO
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
name|LocationDAO
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
name|preference
operator|.
name|MinMaxPreferenceCombination
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
name|exam
operator|.
name|model
operator|.
name|ExamDistributionConstraint
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
name|exam
operator|.
name|model
operator|.
name|ExamPlacement
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
name|exam
operator|.
name|model
operator|.
name|ExamRoom
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
name|util
operator|.
name|ToolBox
import|;
end_import

begin_class
specifier|public
class|class
name|ExamAssignment
extends|extends
name|ExamInfo
implements|implements
name|Serializable
implements|,
name|Comparable
block|{
specifier|protected
name|Long
name|iPeriodId
init|=
literal|null
decl_stmt|;
specifier|protected
name|Vector
name|iRoomIds
init|=
literal|null
decl_stmt|;
specifier|protected
name|String
name|iPeriodPref
init|=
literal|null
decl_stmt|;
specifier|protected
name|int
name|iPeriodIdx
init|=
operator|-
literal|1
decl_stmt|;
specifier|protected
name|Hashtable
name|iRoomPrefs
init|=
literal|null
decl_stmt|;
specifier|protected
specifier|transient
name|ExamPeriod
name|iPeriod
init|=
literal|null
decl_stmt|;
specifier|protected
specifier|transient
name|TreeSet
name|iRooms
init|=
literal|null
decl_stmt|;
specifier|protected
name|ExamInfo
name|iExam
init|=
literal|null
decl_stmt|;
specifier|protected
name|String
name|iDistPref
init|=
literal|null
decl_stmt|;
specifier|public
name|ExamAssignment
parameter_list|(
name|ExamPlacement
name|placement
parameter_list|)
block|{
name|this
argument_list|(
operator|(
name|net
operator|.
name|sf
operator|.
name|cpsolver
operator|.
name|exam
operator|.
name|model
operator|.
name|Exam
operator|)
name|placement
operator|.
name|variable
argument_list|()
argument_list|,
name|placement
argument_list|)
expr_stmt|;
block|}
specifier|public
name|ExamAssignment
parameter_list|(
name|net
operator|.
name|sf
operator|.
name|cpsolver
operator|.
name|exam
operator|.
name|model
operator|.
name|Exam
name|exam
parameter_list|,
name|ExamPlacement
name|placement
parameter_list|)
block|{
name|super
argument_list|(
name|exam
argument_list|)
expr_stmt|;
if|if
condition|(
name|placement
operator|!=
literal|null
condition|)
block|{
name|iPeriodId
operator|=
name|placement
operator|.
name|getPeriod
argument_list|()
operator|.
name|getId
argument_list|()
expr_stmt|;
name|iPeriodIdx
operator|=
name|placement
operator|.
name|getPeriod
argument_list|()
operator|.
name|getIndex
argument_list|()
expr_stmt|;
name|iRoomIds
operator|=
operator|new
name|Vector
argument_list|(
name|placement
operator|.
name|getRooms
argument_list|()
operator|==
literal|null
condition|?
literal|0
else|:
name|placement
operator|.
name|getRooms
argument_list|()
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
name|iPeriodPref
operator|=
name|PreferenceLevel
operator|.
name|int2prolog
argument_list|(
name|placement
operator|.
name|getPeriodPenalty
argument_list|()
argument_list|)
expr_stmt|;
name|iRoomPrefs
operator|=
operator|new
name|Hashtable
argument_list|()
expr_stmt|;
if|if
condition|(
name|placement
operator|.
name|getRooms
argument_list|()
operator|!=
literal|null
condition|)
for|for
control|(
name|Iterator
name|i
init|=
name|placement
operator|.
name|getRooms
argument_list|()
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
name|ExamRoom
name|room
init|=
operator|(
name|ExamRoom
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|iRoomIds
operator|.
name|add
argument_list|(
name|room
operator|.
name|getId
argument_list|()
argument_list|)
expr_stmt|;
name|iRoomPrefs
operator|.
name|put
argument_list|(
name|room
operator|.
name|getId
argument_list|()
argument_list|,
name|PreferenceLevel
operator|.
name|int2prolog
argument_list|(
operator|(
operator|(
name|net
operator|.
name|sf
operator|.
name|cpsolver
operator|.
name|exam
operator|.
name|model
operator|.
name|Exam
operator|)
name|placement
operator|.
name|variable
argument_list|()
operator|)
operator|.
name|getWeight
argument_list|(
name|room
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|MinMaxPreferenceCombination
name|pc
init|=
operator|new
name|MinMaxPreferenceCombination
argument_list|()
decl_stmt|;
for|for
control|(
name|Enumeration
name|e
init|=
operator|(
operator|(
name|net
operator|.
name|sf
operator|.
name|cpsolver
operator|.
name|exam
operator|.
name|model
operator|.
name|Exam
operator|)
name|placement
operator|.
name|variable
argument_list|()
operator|)
operator|.
name|getDistributionConstraints
argument_list|()
operator|.
name|elements
argument_list|()
init|;
name|e
operator|.
name|hasMoreElements
argument_list|()
condition|;
control|)
block|{
name|ExamDistributionConstraint
name|dc
init|=
operator|(
name|ExamDistributionConstraint
operator|)
name|e
operator|.
name|nextElement
argument_list|()
decl_stmt|;
if|if
condition|(
name|dc
operator|.
name|isHard
argument_list|()
operator|||
name|dc
operator|.
name|isSatisfied
argument_list|()
condition|)
continue|continue;
name|pc
operator|.
name|addPreferenceInt
argument_list|(
name|dc
operator|.
name|getWeight
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|iDistPref
operator|=
name|pc
operator|.
name|getPreferenceProlog
argument_list|()
expr_stmt|;
block|}
block|}
specifier|public
name|ExamAssignment
parameter_list|(
name|Exam
name|exam
parameter_list|)
block|{
name|super
argument_list|(
name|exam
argument_list|)
expr_stmt|;
if|if
condition|(
name|exam
operator|.
name|getAssignedPeriod
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|iPeriod
operator|=
name|exam
operator|.
name|getAssignedPeriod
argument_list|()
expr_stmt|;
name|iPeriodId
operator|=
name|exam
operator|.
name|getAssignedPeriod
argument_list|()
operator|.
name|getUniqueId
argument_list|()
expr_stmt|;
name|iRooms
operator|=
operator|new
name|TreeSet
argument_list|()
expr_stmt|;
name|iRoomIds
operator|=
operator|new
name|Vector
argument_list|(
name|exam
operator|.
name|getAssignedRooms
argument_list|()
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
for|for
control|(
name|Iterator
name|i
init|=
name|exam
operator|.
name|getAssignedRooms
argument_list|()
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
name|Location
name|location
init|=
operator|(
name|Location
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|iRooms
operator|.
name|add
argument_list|(
name|location
argument_list|)
expr_stmt|;
name|iRoomIds
operator|.
name|add
argument_list|(
name|location
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|exam
operator|.
name|getAssignedPreference
argument_list|()
operator|!=
literal|null
operator|&&
name|exam
operator|.
name|getAssignedPreference
argument_list|()
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
block|{
name|iRoomPrefs
operator|=
operator|new
name|Hashtable
argument_list|()
expr_stmt|;
name|StringTokenizer
name|stk
init|=
operator|new
name|StringTokenizer
argument_list|(
name|exam
operator|.
name|getAssignedPreference
argument_list|()
argument_list|,
literal|":"
argument_list|)
decl_stmt|;
if|if
condition|(
name|stk
operator|.
name|hasMoreTokens
argument_list|()
condition|)
name|iPeriodPref
operator|=
name|stk
operator|.
name|nextToken
argument_list|()
expr_stmt|;
if|if
condition|(
name|stk
operator|.
name|hasMoreTokens
argument_list|()
condition|)
name|iDistPref
operator|=
name|stk
operator|.
name|nextToken
argument_list|()
expr_stmt|;
for|for
control|(
name|Enumeration
name|e
init|=
name|ToolBox
operator|.
name|sortEnumeration
argument_list|(
name|iRoomIds
operator|.
name|elements
argument_list|()
argument_list|)
init|;
name|e
operator|.
name|hasMoreElements
argument_list|()
operator|&&
name|stk
operator|.
name|hasMoreTokens
argument_list|()
condition|;
control|)
block|{
name|Long
name|roomId
init|=
operator|(
name|Long
operator|)
name|e
operator|.
name|nextElement
argument_list|()
decl_stmt|;
name|iRoomPrefs
operator|.
name|put
argument_list|(
name|roomId
argument_list|,
name|stk
operator|.
name|nextToken
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
specifier|public
name|String
name|getAssignedPreferenceString
parameter_list|()
block|{
name|String
name|ret
init|=
name|getPeriodPref
argument_list|()
operator|+
literal|":"
operator|+
name|getDistributionPref
argument_list|()
decl_stmt|;
for|for
control|(
name|Enumeration
name|e
init|=
name|ToolBox
operator|.
name|sortEnumeration
argument_list|(
name|iRoomIds
operator|.
name|elements
argument_list|()
argument_list|)
init|;
name|e
operator|.
name|hasMoreElements
argument_list|()
condition|;
control|)
block|{
name|Long
name|roomId
init|=
operator|(
name|Long
operator|)
name|e
operator|.
name|nextElement
argument_list|()
decl_stmt|;
name|ret
operator|+=
literal|":"
operator|+
name|getRoomPref
argument_list|(
name|roomId
argument_list|)
expr_stmt|;
block|}
return|return
name|ret
return|;
block|}
specifier|public
name|Long
name|getPeriodId
parameter_list|()
block|{
return|return
name|iPeriodId
return|;
block|}
specifier|public
name|ExamPeriod
name|getPeriod
parameter_list|()
block|{
if|if
condition|(
name|iPeriod
operator|==
literal|null
condition|)
name|iPeriod
operator|=
operator|new
name|ExamPeriodDAO
argument_list|()
operator|.
name|get
argument_list|(
name|getPeriodId
argument_list|()
argument_list|)
expr_stmt|;
return|return
name|iPeriod
return|;
block|}
specifier|public
name|Comparable
name|getPeriodOrd
parameter_list|()
block|{
if|if
condition|(
name|iPeriodIdx
operator|>=
literal|0
condition|)
return|return
operator|new
name|Integer
argument_list|(
name|iPeriodIdx
argument_list|)
return|;
else|else
return|return
name|iPeriod
return|;
block|}
specifier|public
name|String
name|getPeriodName
parameter_list|()
block|{
name|ExamPeriod
name|period
init|=
name|getPeriod
argument_list|()
decl_stmt|;
return|return
name|period
operator|==
literal|null
condition|?
literal|""
else|:
name|period
operator|.
name|getName
argument_list|()
return|;
block|}
specifier|public
name|String
name|getPeriodAbbreviation
parameter_list|()
block|{
name|ExamPeriod
name|period
init|=
name|getPeriod
argument_list|()
decl_stmt|;
return|return
name|period
operator|==
literal|null
condition|?
literal|""
else|:
name|period
operator|.
name|getAbbreviation
argument_list|()
return|;
block|}
specifier|public
name|String
name|getPeriodNameWithPref
parameter_list|()
block|{
if|if
condition|(
name|iPeriodPref
operator|==
literal|null
operator|||
name|PreferenceLevel
operator|.
name|sNeutral
operator|.
name|equals
argument_list|(
name|iPeriodPref
argument_list|)
condition|)
return|return
name|getPeriodName
argument_list|()
return|;
return|return
literal|"<span title='"
operator|+
name|PreferenceLevel
operator|.
name|prolog2string
argument_list|(
name|iPeriodPref
argument_list|)
operator|+
literal|" "
operator|+
name|getPeriodName
argument_list|()
operator|+
literal|"' style='color:"
operator|+
name|PreferenceLevel
operator|.
name|prolog2color
argument_list|(
name|iPeriodPref
argument_list|)
operator|+
literal|";'>"
operator|+
name|getPeriodName
argument_list|()
operator|+
literal|"</span>"
return|;
block|}
specifier|public
name|String
name|getPeriodAbbreviationWithPref
parameter_list|()
block|{
if|if
condition|(
name|iPeriodPref
operator|==
literal|null
operator|||
name|PreferenceLevel
operator|.
name|sNeutral
operator|.
name|equals
argument_list|(
name|iPeriodPref
argument_list|)
condition|)
return|return
name|getPeriodAbbreviation
argument_list|()
return|;
return|return
literal|"<span title='"
operator|+
name|PreferenceLevel
operator|.
name|prolog2string
argument_list|(
name|iPeriodPref
argument_list|)
operator|+
literal|" "
operator|+
name|getPeriodName
argument_list|()
operator|+
literal|"' style='color:"
operator|+
name|PreferenceLevel
operator|.
name|prolog2color
argument_list|(
name|iPeriodPref
argument_list|)
operator|+
literal|";'>"
operator|+
name|getPeriodAbbreviation
argument_list|()
operator|+
literal|"</span>"
return|;
block|}
specifier|public
name|Vector
name|getRoomIds
parameter_list|()
block|{
return|return
name|iRoomIds
return|;
block|}
specifier|public
name|TreeSet
name|getRooms
parameter_list|()
block|{
if|if
condition|(
name|iRooms
operator|==
literal|null
condition|)
block|{
name|iRooms
operator|=
operator|new
name|TreeSet
argument_list|()
expr_stmt|;
for|for
control|(
name|Enumeration
name|e
init|=
name|getRoomIds
argument_list|()
operator|.
name|elements
argument_list|()
init|;
name|e
operator|.
name|hasMoreElements
argument_list|()
condition|;
control|)
block|{
name|Location
name|location
init|=
operator|new
name|LocationDAO
argument_list|()
operator|.
name|get
argument_list|(
operator|(
name|Long
operator|)
name|e
operator|.
name|nextElement
argument_list|()
argument_list|)
decl_stmt|;
name|iRooms
operator|.
name|add
argument_list|(
name|location
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|iRooms
return|;
block|}
specifier|public
name|String
name|getRoomsName
parameter_list|(
name|String
name|delim
parameter_list|)
block|{
name|String
name|rooms
init|=
literal|""
decl_stmt|;
for|for
control|(
name|Iterator
name|j
init|=
name|getRooms
argument_list|()
operator|.
name|iterator
argument_list|()
init|;
name|j
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|Location
name|location
init|=
operator|(
name|Location
operator|)
name|j
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|rooms
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
name|rooms
operator|+=
name|delim
expr_stmt|;
name|rooms
operator|+=
name|location
operator|.
name|getLabel
argument_list|()
expr_stmt|;
block|}
return|return
name|rooms
return|;
block|}
specifier|public
name|String
name|getRoomsNameWithPref
parameter_list|(
name|String
name|delim
parameter_list|)
block|{
name|String
name|rooms
init|=
literal|""
decl_stmt|;
for|for
control|(
name|Iterator
name|j
init|=
name|getRooms
argument_list|()
operator|.
name|iterator
argument_list|()
init|;
name|j
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|Location
name|location
init|=
operator|(
name|Location
operator|)
name|j
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|rooms
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
name|rooms
operator|+=
name|delim
expr_stmt|;
name|String
name|roomPref
init|=
operator|(
name|iRoomPrefs
operator|==
literal|null
condition|?
literal|null
else|:
operator|(
name|String
operator|)
name|iRoomPrefs
operator|.
name|get
argument_list|(
name|location
operator|.
name|getUniqueId
argument_list|()
argument_list|)
operator|)
decl_stmt|;
if|if
condition|(
name|roomPref
operator|==
literal|null
condition|)
block|{
name|rooms
operator|+=
name|location
operator|.
name|getLabel
argument_list|()
expr_stmt|;
block|}
else|else
block|{
name|rooms
operator|+=
literal|"<span title='"
operator|+
name|PreferenceLevel
operator|.
name|prolog2string
argument_list|(
name|roomPref
argument_list|)
operator|+
literal|" "
operator|+
name|location
operator|.
name|getLabel
argument_list|()
operator|+
literal|"' style='color:"
operator|+
name|PreferenceLevel
operator|.
name|prolog2color
argument_list|(
name|roomPref
argument_list|)
operator|+
literal|";'>"
operator|+
name|location
operator|.
name|getLabel
argument_list|()
operator|+
literal|"</span>"
expr_stmt|;
block|}
block|}
return|return
name|rooms
return|;
block|}
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
name|getExamName
argument_list|()
operator|+
literal|" "
operator|+
name|getPeriodAbbreviation
argument_list|()
operator|+
literal|" "
operator|+
name|getRoomsName
argument_list|(
literal|","
argument_list|)
return|;
block|}
specifier|public
name|String
name|getPeriodPref
parameter_list|()
block|{
return|return
operator|(
name|iPeriodPref
operator|==
literal|null
condition|?
name|PreferenceLevel
operator|.
name|sNeutral
else|:
name|iPeriodPref
operator|)
return|;
block|}
specifier|public
name|String
name|getRoomPref
parameter_list|(
name|Long
name|roomId
parameter_list|)
block|{
if|if
condition|(
name|iRoomPrefs
operator|==
literal|null
condition|)
return|return
name|PreferenceLevel
operator|.
name|sNeutral
return|;
name|String
name|pref
init|=
operator|(
name|String
operator|)
name|iRoomPrefs
operator|.
name|get
argument_list|(
name|roomId
argument_list|)
decl_stmt|;
return|return
operator|(
name|pref
operator|==
literal|null
condition|?
name|PreferenceLevel
operator|.
name|sNeutral
else|:
name|pref
operator|)
return|;
block|}
specifier|public
name|String
name|getDistributionPref
parameter_list|()
block|{
return|return
operator|(
name|iDistPref
operator|==
literal|null
condition|?
name|PreferenceLevel
operator|.
name|sNeutral
else|:
name|iDistPref
operator|)
return|;
block|}
specifier|public
name|String
name|getRoomPref
parameter_list|()
block|{
if|if
condition|(
name|iRoomPrefs
operator|==
literal|null
condition|)
return|return
literal|null
return|;
name|MinMaxPreferenceCombination
name|c
init|=
operator|new
name|MinMaxPreferenceCombination
argument_list|()
decl_stmt|;
for|for
control|(
name|Enumeration
name|e
init|=
name|iRoomPrefs
operator|.
name|elements
argument_list|()
init|;
name|e
operator|.
name|hasMoreElements
argument_list|()
condition|;
control|)
block|{
name|c
operator|.
name|addPreferenceProlog
argument_list|(
operator|(
name|String
operator|)
name|e
operator|.
name|nextElement
argument_list|()
argument_list|)
expr_stmt|;
block|}
return|return
name|c
operator|.
name|getPreferenceProlog
argument_list|()
return|;
block|}
block|}
end_class

end_unit

