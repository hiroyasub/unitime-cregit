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
comment|/**  * This is an object that contains data related to the curricula_course table.  * Do not modify this class because it will be overwritten if the configuration file  * related to this class is modified.  *  * @hibernate.class  *  table="curricula_course"  */
end_comment

begin_class
specifier|public
specifier|abstract
class|class
name|BaseCurriculaCourse
implements|implements
name|Serializable
block|{
specifier|public
specifier|static
name|String
name|REF
init|=
literal|"CurriculaCourse"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_PERC_SHARE
init|=
literal|"percShare"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_LL_SHARE
init|=
literal|"llShare"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_GROUP
init|=
literal|"group"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_ORD
init|=
literal|"ord"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_LL_ENROLLMENT
init|=
literal|"llEnrollment"
decl_stmt|;
comment|// constructors
specifier|public
name|BaseCurriculaCourse
parameter_list|()
block|{
name|initialize
argument_list|()
expr_stmt|;
block|}
comment|/** 	 * Constructor for primary key 	 */
specifier|public
name|BaseCurriculaCourse
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
name|BaseCurriculaCourse
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
name|CurriculaClassification
name|classification
parameter_list|,
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|CourseOffering
name|course
parameter_list|,
name|java
operator|.
name|lang
operator|.
name|Float
name|percShare
parameter_list|,
name|java
operator|.
name|lang
operator|.
name|Integer
name|ord
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
name|setClassification
argument_list|(
name|classification
argument_list|)
expr_stmt|;
name|this
operator|.
name|setCourse
argument_list|(
name|course
argument_list|)
expr_stmt|;
name|this
operator|.
name|setPercShare
argument_list|(
name|percShare
argument_list|)
expr_stmt|;
name|this
operator|.
name|setOrd
argument_list|(
name|ord
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
name|Float
name|percShare
decl_stmt|;
specifier|private
name|java
operator|.
name|lang
operator|.
name|Float
name|llShare
decl_stmt|;
specifier|private
name|java
operator|.
name|lang
operator|.
name|String
name|group
decl_stmt|;
specifier|private
name|java
operator|.
name|lang
operator|.
name|Integer
name|ord
decl_stmt|;
specifier|private
name|java
operator|.
name|lang
operator|.
name|Integer
name|llEnrollment
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
name|CurriculaClassification
name|classification
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
name|CourseOffering
name|course
decl_stmt|;
comment|/** 	 * Return the unique identifier of this class      * @hibernate.id      *  generator-class="org.unitime.commons.hibernate.id.UniqueIdGenerator"      *  column="uniqueid"      */
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
comment|/** 	 * Return the value associated with the column: pr_share 	 */
specifier|public
name|java
operator|.
name|lang
operator|.
name|Float
name|getPercShare
parameter_list|()
block|{
return|return
name|percShare
return|;
block|}
comment|/** 	 * Set the value related to the column: pr_share 	 * @param percShare the pr_share value 	 */
specifier|public
name|void
name|setPercShare
parameter_list|(
name|java
operator|.
name|lang
operator|.
name|Float
name|percShare
parameter_list|)
block|{
name|this
operator|.
name|percShare
operator|=
name|percShare
expr_stmt|;
block|}
comment|/** 	 * Return the value associated with the column: ll_share 	 */
specifier|public
name|java
operator|.
name|lang
operator|.
name|Float
name|getLlShare
parameter_list|()
block|{
return|return
name|llShare
return|;
block|}
comment|/** 	 * Set the value related to the column: ll_share 	 * @param llShare the ll_share value 	 */
specifier|public
name|void
name|setLlShare
parameter_list|(
name|java
operator|.
name|lang
operator|.
name|Float
name|llShare
parameter_list|)
block|{
name|this
operator|.
name|llShare
operator|=
name|llShare
expr_stmt|;
block|}
comment|/** 	 * Return the value associated with the column: group_nr 	 */
specifier|public
name|java
operator|.
name|lang
operator|.
name|String
name|getGroup
parameter_list|()
block|{
return|return
name|group
return|;
block|}
comment|/** 	 * Set the value related to the column: group_nr 	 * @param group the group_nr value 	 */
specifier|public
name|void
name|setGroup
parameter_list|(
name|java
operator|.
name|lang
operator|.
name|String
name|group
parameter_list|)
block|{
name|this
operator|.
name|group
operator|=
name|group
expr_stmt|;
block|}
comment|/** 	 * Return the value associated with the column: ord 	 */
specifier|public
name|java
operator|.
name|lang
operator|.
name|Integer
name|getOrd
parameter_list|()
block|{
return|return
name|ord
return|;
block|}
comment|/** 	 * Set the value related to the column: ord 	 * @param ord the ord value 	 */
specifier|public
name|void
name|setOrd
parameter_list|(
name|java
operator|.
name|lang
operator|.
name|Integer
name|ord
parameter_list|)
block|{
name|this
operator|.
name|ord
operator|=
name|ord
expr_stmt|;
block|}
comment|/** 	 * Return the value associated with the column: llEnrollment 	 */
specifier|public
name|java
operator|.
name|lang
operator|.
name|Integer
name|getLlEnrollment
parameter_list|()
block|{
return|return
name|llEnrollment
return|;
block|}
comment|/** 	 * Set the value related to the column: llEnrollment 	 * @param llEnrollment the llEnrollment value 	 */
specifier|public
name|void
name|setLlEnrollment
parameter_list|(
name|java
operator|.
name|lang
operator|.
name|Integer
name|llEnrollment
parameter_list|)
block|{
name|this
operator|.
name|llEnrollment
operator|=
name|llEnrollment
expr_stmt|;
block|}
comment|/** 	 * Return the value associated with the column: cur_clasf_id 	 */
specifier|public
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|CurriculaClassification
name|getClassification
parameter_list|()
block|{
return|return
name|classification
return|;
block|}
comment|/** 	 * Set the value related to the column: cur_clasf_id 	 * @param classification the cur_clasf_id value 	 */
specifier|public
name|void
name|setClassification
parameter_list|(
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|CurriculaClassification
name|classification
parameter_list|)
block|{
name|this
operator|.
name|classification
operator|=
name|classification
expr_stmt|;
block|}
comment|/** 	 * Return the value associated with the column: course_id 	 */
specifier|public
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|CourseOffering
name|getCourse
parameter_list|()
block|{
return|return
name|course
return|;
block|}
comment|/** 	 * Set the value related to the column: course_id 	 * @param course the course_id value 	 */
specifier|public
name|void
name|setCourse
parameter_list|(
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|CourseOffering
name|course
parameter_list|)
block|{
name|this
operator|.
name|course
operator|=
name|course
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
name|CurriculaCourse
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
name|CurriculaCourse
name|curriculaCourse
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
name|CurriculaCourse
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
name|curriculaCourse
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
name|curriculaCourse
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

