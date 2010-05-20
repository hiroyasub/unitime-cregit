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
comment|/**  * This is an object that contains data related to the curricula table.  * Do not modify this class because it will be overwritten if the configuration file  * related to this class is modified.  *  * @hibernate.class  *  table="curricula"  */
end_comment

begin_class
specifier|public
specifier|abstract
class|class
name|BaseCurriculum
implements|implements
name|Serializable
block|{
specifier|public
specifier|static
name|String
name|REF
init|=
literal|"Curriculum"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_ABBV
init|=
literal|"abbv"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_NAME
init|=
literal|"name"
decl_stmt|;
comment|// constructors
specifier|public
name|BaseCurriculum
parameter_list|()
block|{
name|initialize
argument_list|()
expr_stmt|;
block|}
comment|/** 	 * Constructor for primary key 	 */
specifier|public
name|BaseCurriculum
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
name|BaseCurriculum
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
name|Department
name|department
parameter_list|,
name|java
operator|.
name|lang
operator|.
name|String
name|abbv
parameter_list|,
name|java
operator|.
name|lang
operator|.
name|String
name|name
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
name|setDepartment
argument_list|(
name|department
argument_list|)
expr_stmt|;
name|this
operator|.
name|setAbbv
argument_list|(
name|abbv
argument_list|)
expr_stmt|;
name|this
operator|.
name|setName
argument_list|(
name|name
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
name|String
name|abbv
decl_stmt|;
specifier|private
name|java
operator|.
name|lang
operator|.
name|String
name|name
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
name|AcademicArea
name|academicArea
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
name|Department
name|department
decl_stmt|;
comment|// collections
specifier|private
name|java
operator|.
name|util
operator|.
name|Set
name|classifications
decl_stmt|;
specifier|private
name|java
operator|.
name|util
operator|.
name|Set
name|majors
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
comment|/** 	 * Return the value associated with the column: abbv 	 */
specifier|public
name|java
operator|.
name|lang
operator|.
name|String
name|getAbbv
parameter_list|()
block|{
return|return
name|abbv
return|;
block|}
comment|/** 	 * Set the value related to the column: abbv 	 * @param abbv the abbv value 	 */
specifier|public
name|void
name|setAbbv
parameter_list|(
name|java
operator|.
name|lang
operator|.
name|String
name|abbv
parameter_list|)
block|{
name|this
operator|.
name|abbv
operator|=
name|abbv
expr_stmt|;
block|}
comment|/** 	 * Return the value associated with the column: name 	 */
specifier|public
name|java
operator|.
name|lang
operator|.
name|String
name|getName
parameter_list|()
block|{
return|return
name|name
return|;
block|}
comment|/** 	 * Set the value related to the column: name 	 * @param name the name value 	 */
specifier|public
name|void
name|setName
parameter_list|(
name|java
operator|.
name|lang
operator|.
name|String
name|name
parameter_list|)
block|{
name|this
operator|.
name|name
operator|=
name|name
expr_stmt|;
block|}
comment|/** 	 * Return the value associated with the column: acad_area_id 	 */
specifier|public
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|AcademicArea
name|getAcademicArea
parameter_list|()
block|{
return|return
name|academicArea
return|;
block|}
comment|/** 	 * Set the value related to the column: acad_area_id 	 * @param academicArea the acad_area_id value 	 */
specifier|public
name|void
name|setAcademicArea
parameter_list|(
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|AcademicArea
name|academicArea
parameter_list|)
block|{
name|this
operator|.
name|academicArea
operator|=
name|academicArea
expr_stmt|;
block|}
comment|/** 	 * Return the value associated with the column: dept_id 	 */
specifier|public
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|Department
name|getDepartment
parameter_list|()
block|{
return|return
name|department
return|;
block|}
comment|/** 	 * Set the value related to the column: dept_id 	 * @param department the dept_id value 	 */
specifier|public
name|void
name|setDepartment
parameter_list|(
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|Department
name|department
parameter_list|)
block|{
name|this
operator|.
name|department
operator|=
name|department
expr_stmt|;
block|}
comment|/** 	 * Return the value associated with the column: classifications 	 */
specifier|public
name|java
operator|.
name|util
operator|.
name|Set
name|getClassifications
parameter_list|()
block|{
return|return
name|classifications
return|;
block|}
comment|/** 	 * Set the value related to the column: classifications 	 * @param classifications the classifications value 	 */
specifier|public
name|void
name|setClassifications
parameter_list|(
name|java
operator|.
name|util
operator|.
name|Set
name|classifications
parameter_list|)
block|{
name|this
operator|.
name|classifications
operator|=
name|classifications
expr_stmt|;
block|}
specifier|public
name|void
name|addToclassifications
parameter_list|(
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|CurriculumClassification
name|curriculumClassification
parameter_list|)
block|{
if|if
condition|(
literal|null
operator|==
name|getClassifications
argument_list|()
condition|)
name|setClassifications
argument_list|(
operator|new
name|java
operator|.
name|util
operator|.
name|HashSet
argument_list|()
argument_list|)
expr_stmt|;
name|getClassifications
argument_list|()
operator|.
name|add
argument_list|(
name|curriculumClassification
argument_list|)
expr_stmt|;
block|}
comment|/** 	 * Return the value associated with the column: majors 	 */
specifier|public
name|java
operator|.
name|util
operator|.
name|Set
name|getMajors
parameter_list|()
block|{
return|return
name|majors
return|;
block|}
comment|/** 	 * Set the value related to the column: majors 	 * @param majors the majors value 	 */
specifier|public
name|void
name|setMajors
parameter_list|(
name|java
operator|.
name|util
operator|.
name|Set
name|majors
parameter_list|)
block|{
name|this
operator|.
name|majors
operator|=
name|majors
expr_stmt|;
block|}
specifier|public
name|void
name|addTomajors
parameter_list|(
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|PosMajor
name|major
parameter_list|)
block|{
if|if
condition|(
literal|null
operator|==
name|getMajors
argument_list|()
condition|)
name|setMajors
argument_list|(
operator|new
name|java
operator|.
name|util
operator|.
name|HashSet
argument_list|()
argument_list|)
expr_stmt|;
name|getMajors
argument_list|()
operator|.
name|add
argument_list|(
name|major
argument_list|)
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
name|Curriculum
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
name|Curriculum
name|curriculum
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
name|Curriculum
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
name|curriculum
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
name|curriculum
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

