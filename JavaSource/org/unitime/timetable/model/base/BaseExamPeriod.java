begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_package
package|package
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|base
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

begin_comment
comment|/**  * This is an object that contains data related to the EXAM_PERIOD table.  * Do not modify this class because it will be overwritten if the configuration file  * related to this class is modified.  *  * @hibernate.class  *  table="EXAM_PERIOD"  */
end_comment

begin_class
specifier|public
specifier|abstract
class|class
name|BaseExamPeriod
implements|implements
name|Serializable
block|{
specifier|public
specifier|static
name|String
name|REF
init|=
literal|"ExamPeriod"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_DATE_OFFSET
init|=
literal|"dateOffset"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_START_SLOT
init|=
literal|"startSlot"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_LENGTH
init|=
literal|"length"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_EXAM_TYPE
init|=
literal|"examType"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_EVENT_START_OFFSET
init|=
literal|"eventStartOffset"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_EVENT_STOP_OFFSET
init|=
literal|"eventStopOffset"
decl_stmt|;
comment|// constructors
specifier|public
name|BaseExamPeriod
parameter_list|()
block|{
name|initialize
argument_list|()
expr_stmt|;
block|}
comment|/** 	 * Constructor for primary key 	 */
specifier|public
name|BaseExamPeriod
parameter_list|(
name|java
operator|.
name|lang
operator|.
name|Long
name|uniqueId
parameter_list|)
block|{
name|this
operator|.
name|setUniqueId
argument_list|(
name|uniqueId
argument_list|)
expr_stmt|;
name|initialize
argument_list|()
expr_stmt|;
block|}
comment|/** 	 * Constructor for required fields 	 */
specifier|public
name|BaseExamPeriod
parameter_list|(
name|java
operator|.
name|lang
operator|.
name|Long
name|uniqueId
parameter_list|,
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|PreferenceLevel
name|prefLevel
parameter_list|,
name|java
operator|.
name|lang
operator|.
name|Integer
name|dateOffset
parameter_list|,
name|java
operator|.
name|lang
operator|.
name|Integer
name|startSlot
parameter_list|,
name|java
operator|.
name|lang
operator|.
name|Integer
name|length
parameter_list|,
name|java
operator|.
name|lang
operator|.
name|Integer
name|examType
parameter_list|,
name|java
operator|.
name|lang
operator|.
name|Integer
name|eventStartOffset
parameter_list|,
name|java
operator|.
name|lang
operator|.
name|Integer
name|eventStopOffset
parameter_list|)
block|{
name|this
operator|.
name|setUniqueId
argument_list|(
name|uniqueId
argument_list|)
expr_stmt|;
name|this
operator|.
name|setPrefLevel
argument_list|(
name|prefLevel
argument_list|)
expr_stmt|;
name|this
operator|.
name|setDateOffset
argument_list|(
name|dateOffset
argument_list|)
expr_stmt|;
name|this
operator|.
name|setStartSlot
argument_list|(
name|startSlot
argument_list|)
expr_stmt|;
name|this
operator|.
name|setLength
argument_list|(
name|length
argument_list|)
expr_stmt|;
name|this
operator|.
name|setExamType
argument_list|(
name|examType
argument_list|)
expr_stmt|;
name|this
operator|.
name|setEventStartOffset
argument_list|(
name|eventStartOffset
argument_list|)
expr_stmt|;
name|this
operator|.
name|setEventStopOffset
argument_list|(
name|eventStopOffset
argument_list|)
expr_stmt|;
name|initialize
argument_list|()
expr_stmt|;
block|}
specifier|protected
name|void
name|initialize
parameter_list|()
block|{
block|}
specifier|private
name|int
name|hashCode
init|=
name|Integer
operator|.
name|MIN_VALUE
decl_stmt|;
comment|// primary key
specifier|private
name|java
operator|.
name|lang
operator|.
name|Long
name|uniqueId
decl_stmt|;
comment|// fields
specifier|private
name|java
operator|.
name|lang
operator|.
name|Integer
name|dateOffset
decl_stmt|;
specifier|private
name|java
operator|.
name|lang
operator|.
name|Integer
name|startSlot
decl_stmt|;
specifier|private
name|java
operator|.
name|lang
operator|.
name|Integer
name|length
decl_stmt|;
specifier|private
name|java
operator|.
name|lang
operator|.
name|Integer
name|examType
decl_stmt|;
specifier|private
name|java
operator|.
name|lang
operator|.
name|Integer
name|eventStartOffset
decl_stmt|;
specifier|private
name|java
operator|.
name|lang
operator|.
name|Integer
name|eventStopOffset
decl_stmt|;
comment|// many to one
specifier|private
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|Session
name|session
decl_stmt|;
specifier|private
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|PreferenceLevel
name|prefLevel
decl_stmt|;
comment|/** 	 * Return the unique identifier of this class      * @hibernate.id      *  generator-class="org.unitime.commons.hibernate.id.UniqueIdGenerator"      *  column="UNIQUEID"      */
specifier|public
name|java
operator|.
name|lang
operator|.
name|Long
name|getUniqueId
parameter_list|()
block|{
return|return
name|uniqueId
return|;
block|}
comment|/** 	 * Set the unique identifier of this class 	 * @param uniqueId the new ID 	 */
specifier|public
name|void
name|setUniqueId
parameter_list|(
name|java
operator|.
name|lang
operator|.
name|Long
name|uniqueId
parameter_list|)
block|{
name|this
operator|.
name|uniqueId
operator|=
name|uniqueId
expr_stmt|;
name|this
operator|.
name|hashCode
operator|=
name|Integer
operator|.
name|MIN_VALUE
expr_stmt|;
block|}
comment|/** 	 * Return the value associated with the column: DATE_OFS 	 */
specifier|public
name|java
operator|.
name|lang
operator|.
name|Integer
name|getDateOffset
parameter_list|()
block|{
return|return
name|dateOffset
return|;
block|}
comment|/** 	 * Set the value related to the column: DATE_OFS 	 * @param dateOffset the DATE_OFS value 	 */
specifier|public
name|void
name|setDateOffset
parameter_list|(
name|java
operator|.
name|lang
operator|.
name|Integer
name|dateOffset
parameter_list|)
block|{
name|this
operator|.
name|dateOffset
operator|=
name|dateOffset
expr_stmt|;
block|}
comment|/** 	 * Return the value associated with the column: START_SLOT 	 */
specifier|public
name|java
operator|.
name|lang
operator|.
name|Integer
name|getStartSlot
parameter_list|()
block|{
return|return
name|startSlot
return|;
block|}
comment|/** 	 * Set the value related to the column: START_SLOT 	 * @param startSlot the START_SLOT value 	 */
specifier|public
name|void
name|setStartSlot
parameter_list|(
name|java
operator|.
name|lang
operator|.
name|Integer
name|startSlot
parameter_list|)
block|{
name|this
operator|.
name|startSlot
operator|=
name|startSlot
expr_stmt|;
block|}
comment|/** 	 * Return the value associated with the column: LENGTH 	 */
specifier|public
name|java
operator|.
name|lang
operator|.
name|Integer
name|getLength
parameter_list|()
block|{
return|return
name|length
return|;
block|}
comment|/** 	 * Set the value related to the column: LENGTH 	 * @param length the LENGTH value 	 */
specifier|public
name|void
name|setLength
parameter_list|(
name|java
operator|.
name|lang
operator|.
name|Integer
name|length
parameter_list|)
block|{
name|this
operator|.
name|length
operator|=
name|length
expr_stmt|;
block|}
comment|/** 	 * Return the value associated with the column: EXAM_TYPE 	 */
specifier|public
name|java
operator|.
name|lang
operator|.
name|Integer
name|getExamType
parameter_list|()
block|{
return|return
name|examType
return|;
block|}
comment|/** 	 * Set the value related to the column: EXAM_TYPE 	 * @param examType the EXAM_TYPE value 	 */
specifier|public
name|void
name|setExamType
parameter_list|(
name|java
operator|.
name|lang
operator|.
name|Integer
name|examType
parameter_list|)
block|{
name|this
operator|.
name|examType
operator|=
name|examType
expr_stmt|;
block|}
comment|/** 	 * Return the value associated with the column: event_start_offset 	 */
specifier|public
name|java
operator|.
name|lang
operator|.
name|Integer
name|getEventStartOffset
parameter_list|()
block|{
return|return
name|eventStartOffset
return|;
block|}
comment|/** 	 * Set the value related to the column: event_start_offset 	 * @param eventStartOffset the event_start_offset value 	 */
specifier|public
name|void
name|setEventStartOffset
parameter_list|(
name|java
operator|.
name|lang
operator|.
name|Integer
name|eventStartOffset
parameter_list|)
block|{
name|this
operator|.
name|eventStartOffset
operator|=
name|eventStartOffset
expr_stmt|;
block|}
comment|/** 	 * Return the value associated with the column: event_stop_offset 	 */
specifier|public
name|java
operator|.
name|lang
operator|.
name|Integer
name|getEventStopOffset
parameter_list|()
block|{
return|return
name|eventStopOffset
return|;
block|}
comment|/** 	 * Set the value related to the column: event_stop_offset 	 * @param eventStopOffset the event_stop_offset value 	 */
specifier|public
name|void
name|setEventStopOffset
parameter_list|(
name|java
operator|.
name|lang
operator|.
name|Integer
name|eventStopOffset
parameter_list|)
block|{
name|this
operator|.
name|eventStopOffset
operator|=
name|eventStopOffset
expr_stmt|;
block|}
comment|/** 	 * Return the value associated with the column: SESSION_ID 	 */
specifier|public
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|Session
name|getSession
parameter_list|()
block|{
return|return
name|session
return|;
block|}
comment|/** 	 * Set the value related to the column: SESSION_ID 	 * @param session the SESSION_ID value 	 */
specifier|public
name|void
name|setSession
parameter_list|(
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|Session
name|session
parameter_list|)
block|{
name|this
operator|.
name|session
operator|=
name|session
expr_stmt|;
block|}
comment|/** 	 * Return the value associated with the column: PREF_LEVEL_ID 	 */
specifier|public
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|PreferenceLevel
name|getPrefLevel
parameter_list|()
block|{
return|return
name|prefLevel
return|;
block|}
comment|/** 	 * Set the value related to the column: PREF_LEVEL_ID 	 * @param prefLevel the PREF_LEVEL_ID value 	 */
specifier|public
name|void
name|setPrefLevel
parameter_list|(
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|PreferenceLevel
name|prefLevel
parameter_list|)
block|{
name|this
operator|.
name|prefLevel
operator|=
name|prefLevel
expr_stmt|;
block|}
specifier|public
name|boolean
name|equals
parameter_list|(
name|Object
name|obj
parameter_list|)
block|{
if|if
condition|(
literal|null
operator|==
name|obj
condition|)
return|return
literal|false
return|;
if|if
condition|(
operator|!
operator|(
name|obj
operator|instanceof
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|ExamPeriod
operator|)
condition|)
return|return
literal|false
return|;
else|else
block|{
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|ExamPeriod
name|examPeriod
init|=
operator|(
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|ExamPeriod
operator|)
name|obj
decl_stmt|;
if|if
condition|(
literal|null
operator|==
name|this
operator|.
name|getUniqueId
argument_list|()
operator|||
literal|null
operator|==
name|examPeriod
operator|.
name|getUniqueId
argument_list|()
condition|)
return|return
literal|false
return|;
else|else
return|return
operator|(
name|this
operator|.
name|getUniqueId
argument_list|()
operator|.
name|equals
argument_list|(
name|examPeriod
operator|.
name|getUniqueId
argument_list|()
argument_list|)
operator|)
return|;
block|}
block|}
specifier|public
name|int
name|hashCode
parameter_list|()
block|{
if|if
condition|(
name|Integer
operator|.
name|MIN_VALUE
operator|==
name|this
operator|.
name|hashCode
condition|)
block|{
if|if
condition|(
literal|null
operator|==
name|this
operator|.
name|getUniqueId
argument_list|()
condition|)
return|return
name|super
operator|.
name|hashCode
argument_list|()
return|;
else|else
block|{
name|String
name|hashStr
init|=
name|this
operator|.
name|getClass
argument_list|()
operator|.
name|getName
argument_list|()
operator|+
literal|":"
operator|+
name|this
operator|.
name|getUniqueId
argument_list|()
operator|.
name|hashCode
argument_list|()
decl_stmt|;
name|this
operator|.
name|hashCode
operator|=
name|hashStr
operator|.
name|hashCode
argument_list|()
expr_stmt|;
block|}
block|}
return|return
name|this
operator|.
name|hashCode
return|;
block|}
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
name|super
operator|.
name|toString
argument_list|()
return|;
block|}
block|}
end_class

end_unit

