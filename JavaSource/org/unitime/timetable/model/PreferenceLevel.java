begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * Licensed to The Apereo Foundation under one or more contributor license  * agreements. See the NOTICE file distributed with this work for  * additional information regarding copyright ownership.  *  * The Apereo Foundation licenses this file to you under the Apache License,  * Version 2.0 (the "License"); you may not use this file except in  * compliance with the License. You may obtain a copy of the License at:  *  * http://www.apache.org/licenses/LICENSE-2.0  *  * Unless required by applicable law or agreed to in writing, software  * distributed under the License is distributed on an "AS IS" BASIS,  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  *  * See the License for the specific language governing permissions and  * limitations under the License.  *  */
end_comment

begin_package
package|package
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
package|;
end_package

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|Color
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
name|List
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
name|model
operator|.
name|base
operator|.
name|BasePreferenceLevel
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
name|PreferenceLevelDAO
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
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|PreferenceLevel
extends|extends
name|BasePreferenceLevel
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|1L
decl_stmt|;
comment|/** Request attribute name for available preference levels  **/
specifier|public
specifier|static
name|String
name|PREF_LEVEL_ATTR_NAME
init|=
literal|"prefLevelsList"
decl_stmt|;
comment|/** Level for neutral preference **/
specifier|public
specifier|static
name|String
name|PREF_LEVEL_NEUTRAL
init|=
literal|"4"
decl_stmt|;
comment|/** Level for required preference **/
specifier|public
specifier|static
name|String
name|PREF_LEVEL_REQUIRED
init|=
literal|"1"
decl_stmt|;
comment|/** preference to color conversion */
specifier|private
specifier|static
name|Hashtable
name|sPref2color
init|=
literal|null
decl_stmt|;
comment|/** preference to color conversion (hexadecimal) */
specifier|private
specifier|static
name|Hashtable
name|sHexPref2color
init|=
literal|null
decl_stmt|;
comment|/** preference to awt color conversion */
specifier|private
specifier|static
name|Hashtable
name|sAwtPref2color
init|=
literal|null
decl_stmt|;
comment|/** preference to background color conversion */
specifier|private
specifier|static
name|Hashtable
name|sBgPref2color
init|=
literal|null
decl_stmt|;
specifier|public
specifier|static
name|String
name|sProhibited
init|=
name|Constants
operator|.
name|sPreferenceProhibited
decl_stmt|;
specifier|public
specifier|static
name|String
name|sRequired
init|=
name|Constants
operator|.
name|sPreferenceRequired
decl_stmt|;
specifier|public
specifier|static
name|String
name|sStronglyDiscouraged
init|=
name|Constants
operator|.
name|sPreferenceStronglyDiscouraged
decl_stmt|;
specifier|public
specifier|static
name|String
name|sDiscouraged
init|=
name|Constants
operator|.
name|sPreferenceDiscouraged
decl_stmt|;
specifier|public
specifier|static
name|String
name|sPreferred
init|=
name|Constants
operator|.
name|sPreferencePreferred
decl_stmt|;
specifier|public
specifier|static
name|String
name|sStronglyPreferred
init|=
name|Constants
operator|.
name|sPreferenceStronglyPreferred
decl_stmt|;
specifier|public
specifier|static
name|String
name|sNeutral
init|=
name|Constants
operator|.
name|sPreferenceNeutral
decl_stmt|;
specifier|public
specifier|static
name|String
name|sNotAvailable
init|=
literal|"N"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|char
name|sCharLevelProhibited
init|=
literal|'P'
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|char
name|sCharLevelRequired
init|=
literal|'R'
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|char
name|sCharLevelStronglyDiscouraged
init|=
literal|'4'
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|char
name|sCharLevelDiscouraged
init|=
literal|'3'
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|char
name|sCharLevelPreferred
init|=
literal|'1'
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|char
name|sCharLevelStronglyPreferred
init|=
literal|'0'
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|char
name|sCharLevelNeutral
init|=
literal|'2'
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|char
name|sCharLevelNotAvailable
init|=
literal|'N'
decl_stmt|;
specifier|public
specifier|static
name|int
name|sIntLevelProhibited
init|=
name|Constants
operator|.
name|sPreferenceLevelProhibited
decl_stmt|;
specifier|public
specifier|static
name|int
name|sIntLevelRequired
init|=
name|Constants
operator|.
name|sPreferenceLevelRequired
decl_stmt|;
specifier|public
specifier|static
name|int
name|sIntLevelStronglyDiscouraged
init|=
name|Constants
operator|.
name|sPreferenceLevelStronglyDiscouraged
decl_stmt|;
specifier|public
specifier|static
name|int
name|sIntLevelDiscouraged
init|=
name|Constants
operator|.
name|sPreferenceLevelDiscouraged
decl_stmt|;
specifier|public
specifier|static
name|int
name|sIntLevelPreferred
init|=
name|Constants
operator|.
name|sPreferenceLevelDiscouraged
decl_stmt|;
specifier|public
specifier|static
name|int
name|sIntLevelStronglyPreferred
init|=
name|Constants
operator|.
name|sPreferenceLevelStronglyPreferred
decl_stmt|;
specifier|public
specifier|static
name|int
name|sIntLevelNeutral
init|=
name|Constants
operator|.
name|sPreferenceLevelNeutral
decl_stmt|;
specifier|public
specifier|static
name|int
name|sIntLevelNotAvailable
init|=
name|Constants
operator|.
name|sPreferenceLevelProhibited
decl_stmt|;
comment|/** static initialization */
static|static
block|{
name|sPref2color
operator|=
operator|new
name|Hashtable
argument_list|()
expr_stmt|;
name|sPref2color
operator|.
name|put
argument_list|(
name|sRequired
argument_list|,
literal|"rgb(60,60,180)"
argument_list|)
expr_stmt|;
name|sPref2color
operator|.
name|put
argument_list|(
name|sStronglyPreferred
argument_list|,
literal|"rgb(15,130,30)"
argument_list|)
expr_stmt|;
comment|// rgb(20,160,40)
name|sPref2color
operator|.
name|put
argument_list|(
name|sPreferred
argument_list|,
literal|"rgb(50,200,20)"
argument_list|)
expr_stmt|;
comment|// rgb(110,200,20), rgb(150,240,40)
name|sPref2color
operator|.
name|put
argument_list|(
name|sNeutral
argument_list|,
literal|"rgb(240,240,240)"
argument_list|)
expr_stmt|;
name|sPref2color
operator|.
name|put
argument_list|(
name|sDiscouraged
argument_list|,
literal|"rgb(220,180,20)"
argument_list|)
expr_stmt|;
comment|//rgb(240,200,40)
name|sPref2color
operator|.
name|put
argument_list|(
name|sStronglyDiscouraged
argument_list|,
literal|"rgb(240,100,40)"
argument_list|)
expr_stmt|;
name|sPref2color
operator|.
name|put
argument_list|(
name|sProhibited
argument_list|,
literal|"rgb(200,30,20)"
argument_list|)
expr_stmt|;
name|sPref2color
operator|.
name|put
argument_list|(
name|sNotAvailable
argument_list|,
literal|"rgb(150,150,150)"
argument_list|)
expr_stmt|;
name|sAwtPref2color
operator|=
operator|new
name|Hashtable
argument_list|()
expr_stmt|;
name|sAwtPref2color
operator|.
name|put
argument_list|(
name|sRequired
argument_list|,
operator|new
name|Color
argument_list|(
literal|60
argument_list|,
literal|60
argument_list|,
literal|180
argument_list|)
argument_list|)
expr_stmt|;
name|sAwtPref2color
operator|.
name|put
argument_list|(
name|sStronglyPreferred
argument_list|,
operator|new
name|Color
argument_list|(
literal|15
argument_list|,
literal|130
argument_list|,
literal|30
argument_list|)
argument_list|)
expr_stmt|;
name|sAwtPref2color
operator|.
name|put
argument_list|(
name|sPreferred
argument_list|,
operator|new
name|Color
argument_list|(
literal|50
argument_list|,
literal|200
argument_list|,
literal|20
argument_list|)
argument_list|)
expr_stmt|;
name|sAwtPref2color
operator|.
name|put
argument_list|(
name|sNeutral
argument_list|,
operator|new
name|Color
argument_list|(
literal|240
argument_list|,
literal|240
argument_list|,
literal|240
argument_list|)
argument_list|)
expr_stmt|;
name|sAwtPref2color
operator|.
name|put
argument_list|(
name|sDiscouraged
argument_list|,
operator|new
name|Color
argument_list|(
literal|220
argument_list|,
literal|180
argument_list|,
literal|20
argument_list|)
argument_list|)
expr_stmt|;
name|sAwtPref2color
operator|.
name|put
argument_list|(
name|sStronglyDiscouraged
argument_list|,
operator|new
name|Color
argument_list|(
literal|240
argument_list|,
literal|100
argument_list|,
literal|40
argument_list|)
argument_list|)
expr_stmt|;
name|sAwtPref2color
operator|.
name|put
argument_list|(
name|sProhibited
argument_list|,
operator|new
name|Color
argument_list|(
literal|200
argument_list|,
literal|30
argument_list|,
literal|20
argument_list|)
argument_list|)
expr_stmt|;
name|sAwtPref2color
operator|.
name|put
argument_list|(
name|sNotAvailable
argument_list|,
operator|new
name|Color
argument_list|(
literal|150
argument_list|,
literal|150
argument_list|,
literal|150
argument_list|)
argument_list|)
expr_stmt|;
name|sBgPref2color
operator|=
operator|new
name|Hashtable
argument_list|()
expr_stmt|;
name|sBgPref2color
operator|.
name|put
argument_list|(
name|sRequired
argument_list|,
literal|"rgb(80,80,200)"
argument_list|)
expr_stmt|;
name|sBgPref2color
operator|.
name|put
argument_list|(
name|sStronglyPreferred
argument_list|,
literal|"rgb(30,160,60)"
argument_list|)
expr_stmt|;
comment|//rgb(40,180,60)
name|sBgPref2color
operator|.
name|put
argument_list|(
name|sPreferred
argument_list|,
literal|"rgb(70,230,30)"
argument_list|)
expr_stmt|;
comment|//rgb(170,240,60)
name|sBgPref2color
operator|.
name|put
argument_list|(
name|sNeutral
argument_list|,
literal|"rgb(240,240,240)"
argument_list|)
expr_stmt|;
name|sBgPref2color
operator|.
name|put
argument_list|(
name|sDiscouraged
argument_list|,
literal|"rgb(240,210,60)"
argument_list|)
expr_stmt|;
name|sBgPref2color
operator|.
name|put
argument_list|(
name|sStronglyDiscouraged
argument_list|,
literal|"rgb(240,120,60)"
argument_list|)
expr_stmt|;
name|sBgPref2color
operator|.
name|put
argument_list|(
name|sProhibited
argument_list|,
literal|"rgb(220,50,40)"
argument_list|)
expr_stmt|;
name|sBgPref2color
operator|.
name|put
argument_list|(
name|sNotAvailable
argument_list|,
literal|"rgb(150,150,150)"
argument_list|)
expr_stmt|;
name|sHexPref2color
operator|=
operator|new
name|Hashtable
argument_list|()
expr_stmt|;
name|sHexPref2color
operator|.
name|put
argument_list|(
name|sRequired
argument_list|,
literal|"#3c3cb4"
argument_list|)
expr_stmt|;
name|sHexPref2color
operator|.
name|put
argument_list|(
name|sStronglyPreferred
argument_list|,
literal|"#0f821e"
argument_list|)
expr_stmt|;
comment|//14a028
name|sHexPref2color
operator|.
name|put
argument_list|(
name|sPreferred
argument_list|,
literal|"#32c814"
argument_list|)
expr_stmt|;
comment|//6ec814, 96f028
name|sHexPref2color
operator|.
name|put
argument_list|(
name|sNeutral
argument_list|,
literal|"#0a0a0a"
argument_list|)
expr_stmt|;
name|sHexPref2color
operator|.
name|put
argument_list|(
name|sDiscouraged
argument_list|,
literal|"#dcb414"
argument_list|)
expr_stmt|;
comment|//f0c828
name|sHexPref2color
operator|.
name|put
argument_list|(
name|sStronglyDiscouraged
argument_list|,
literal|"#f06428"
argument_list|)
expr_stmt|;
name|sHexPref2color
operator|.
name|put
argument_list|(
name|sProhibited
argument_list|,
literal|"#c81e14"
argument_list|)
expr_stmt|;
name|sHexPref2color
operator|.
name|put
argument_list|(
name|sNotAvailable
argument_list|,
literal|"#696969"
argument_list|)
expr_stmt|;
block|}
comment|/*[CONSTRUCTOR MARKER BEGIN]*/
specifier|public
name|PreferenceLevel
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
block|}
comment|/** 	 * Constructor for primary key 	 */
specifier|public
name|PreferenceLevel
parameter_list|(
name|java
operator|.
name|lang
operator|.
name|Long
name|uniqueId
parameter_list|)
block|{
name|super
argument_list|(
name|uniqueId
argument_list|)
expr_stmt|;
block|}
comment|/*[CONSTRUCTOR MARKER END]*/
comment|/* Values             1,R,Required             2,-2,Strongly Preferred             3,-1,Preferred             4,0,Neutral             5,1,Discouraged             6,2,Strongly Discouraged             7,P,Prohibited 	 */
comment|/** 	 * @return Returns the sAwtPref2color. 	 */
specifier|public
specifier|static
name|Hashtable
name|getSAwtPref2color
parameter_list|()
block|{
return|return
name|sAwtPref2color
return|;
block|}
comment|/** 	 * @return Returns the sPref2color. 	 */
specifier|public
specifier|static
name|Hashtable
name|getSPref2color
parameter_list|()
block|{
return|return
name|sPref2color
return|;
block|}
comment|/** 	 * Retrieves all preference levels in the database 	 * ordered by column pref_id 	 * @return Vector of PreferenceLevel objects 	 */
specifier|public
specifier|static
specifier|synchronized
name|List
argument_list|<
name|PreferenceLevel
argument_list|>
name|getPreferenceLevelList
parameter_list|()
block|{
return|return
name|getPreferenceLevelList
argument_list|(
literal|false
argument_list|)
return|;
block|}
specifier|public
specifier|static
specifier|synchronized
name|List
argument_list|<
name|PreferenceLevel
argument_list|>
name|getPreferenceLevelList
parameter_list|(
name|boolean
name|includeNotAvailable
parameter_list|)
block|{
if|if
condition|(
name|includeNotAvailable
condition|)
block|{
return|return
operator|(
name|List
argument_list|<
name|PreferenceLevel
argument_list|>
operator|)
name|PreferenceLevelDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"from PreferenceLevel order by prefId"
argument_list|)
operator|.
name|setCacheable
argument_list|(
literal|true
argument_list|)
operator|.
name|list
argument_list|()
return|;
block|}
else|else
block|{
return|return
operator|(
name|List
argument_list|<
name|PreferenceLevel
argument_list|>
operator|)
name|PreferenceLevelDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"from PreferenceLevel where prefProlog != :na order by prefId"
argument_list|)
operator|.
name|setString
argument_list|(
literal|"na"
argument_list|,
name|sNotAvailable
argument_list|)
operator|.
name|setCacheable
argument_list|(
literal|true
argument_list|)
operator|.
name|list
argument_list|()
return|;
block|}
block|}
specifier|public
specifier|static
name|List
argument_list|<
name|PreferenceLevel
argument_list|>
name|getPreferenceLevelListSoftOnly
parameter_list|()
block|{
name|List
argument_list|<
name|PreferenceLevel
argument_list|>
name|ret
init|=
name|getPreferenceLevelList
argument_list|(
literal|false
argument_list|)
decl_stmt|;
for|for
control|(
name|Iterator
argument_list|<
name|PreferenceLevel
argument_list|>
name|i
init|=
name|ret
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
name|PreferenceLevel
name|level
init|=
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|sRequired
operator|.
name|equals
argument_list|(
name|level
operator|.
name|getPrefProlog
argument_list|()
argument_list|)
operator|||
name|sProhibited
operator|.
name|equals
argument_list|(
name|level
operator|.
name|getPrefProlog
argument_list|()
argument_list|)
condition|)
name|i
operator|.
name|remove
argument_list|()
expr_stmt|;
block|}
return|return
name|ret
return|;
block|}
comment|/**      * Override default equals() behavior - compares prefId      */
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
name|PreferenceLevel
operator|)
condition|)
return|return
literal|false
return|;
return|return
name|getPrefId
argument_list|()
operator|.
name|intValue
argument_list|()
operator|==
operator|(
operator|(
name|PreferenceLevel
operator|)
name|o
operator|)
operator|.
name|getPrefId
argument_list|()
operator|.
name|intValue
argument_list|()
return|;
block|}
comment|/**      * Override default hashCode() behavior      */
specifier|public
name|int
name|hashCode
parameter_list|()
block|{
return|return
name|getPrefId
argument_list|()
operator|.
name|intValue
argument_list|()
return|;
block|}
comment|/**      * Override default toString() behavior      */
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
literal|"PreferenceLevel{id="
operator|+
name|getPrefId
argument_list|()
operator|.
name|intValue
argument_list|()
operator|+
literal|",prolog="
operator|+
name|getPrefProlog
argument_list|()
operator|+
literal|",name="
operator|+
name|getPrefName
argument_list|()
operator|+
literal|"}"
return|;
block|}
comment|/**      * Retrieves PreferenceLevel for given Pref Id (not uniqueid)      * @param id Preference Id      * @return PreferenceLevel object      */
specifier|public
specifier|static
name|PreferenceLevel
name|getPreferenceLevel
parameter_list|(
name|int
name|id
parameter_list|)
block|{
for|for
control|(
name|PreferenceLevel
name|p
range|:
name|getPreferenceLevelList
argument_list|()
control|)
if|if
condition|(
name|p
operator|.
name|getPrefId
argument_list|()
operator|==
name|id
condition|)
return|return
name|p
return|;
return|return
literal|null
return|;
block|}
comment|/**      * Retrieves PreferenceLevel for given Prolog Id      * @param id Prolog Id      * @return PreferenceLevel object      */
specifier|public
specifier|static
name|PreferenceLevel
name|getPreferenceLevel
parameter_list|(
name|String
name|prologId
parameter_list|)
block|{
for|for
control|(
name|PreferenceLevel
name|p
range|:
name|getPreferenceLevelList
argument_list|(
literal|true
argument_list|)
control|)
if|if
condition|(
name|p
operator|.
name|getPrefProlog
argument_list|()
operator|.
name|equalsIgnoreCase
argument_list|(
name|prologId
argument_list|)
condition|)
return|return
name|p
return|;
return|return
literal|null
return|;
block|}
comment|/**      * Combines two preference levels to give an effective preference level (???)       * @param another PreferenceLevel object to be combined      * @return Effective PreferenceLevel object      */
specifier|public
name|PreferenceLevel
name|combine
parameter_list|(
name|PreferenceLevel
name|another
parameter_list|)
block|{
if|if
condition|(
name|getPrefProlog
argument_list|()
operator|.
name|equals
argument_list|(
name|another
operator|.
name|getPrefProlog
argument_list|()
argument_list|)
condition|)
return|return
name|this
return|;
if|if
condition|(
name|getPrefProlog
argument_list|()
operator|.
name|equals
argument_list|(
name|sProhibited
argument_list|)
operator|||
name|another
operator|.
name|getPrefProlog
argument_list|()
operator|.
name|equals
argument_list|(
name|sProhibited
argument_list|)
condition|)
return|return
name|getPreferenceLevel
argument_list|(
name|sProhibited
argument_list|)
return|;
if|if
condition|(
name|getPrefProlog
argument_list|()
operator|.
name|equals
argument_list|(
name|sRequired
argument_list|)
condition|)
return|return
name|another
return|;
if|if
condition|(
name|another
operator|.
name|getPrefProlog
argument_list|()
operator|.
name|equals
argument_list|(
name|sRequired
argument_list|)
condition|)
return|return
name|this
return|;
return|return
name|getPreferenceLevel
argument_list|(
name|String
operator|.
name|valueOf
argument_list|(
name|Math
operator|.
name|max
argument_list|(
name|Integer
operator|.
name|parseInt
argument_list|(
name|getPrefProlog
argument_list|()
argument_list|)
argument_list|,
name|Integer
operator|.
name|parseInt
argument_list|(
name|another
operator|.
name|getPrefProlog
argument_list|()
argument_list|)
argument_list|)
argument_list|)
argument_list|)
return|;
block|}
comment|/** preference to color conversion */
specifier|public
name|String
name|prefcolor
parameter_list|()
block|{
if|if
condition|(
name|getSPref2color
argument_list|()
operator|.
name|containsKey
argument_list|(
name|this
operator|.
name|getPrefProlog
argument_list|()
argument_list|)
condition|)
return|return
operator|(
name|String
operator|)
name|getSPref2color
argument_list|()
operator|.
name|get
argument_list|(
name|this
operator|.
name|getPrefProlog
argument_list|()
argument_list|)
return|;
else|else
block|{
name|Debug
operator|.
name|log
argument_list|(
literal|"Unknown color for preference "
operator|+
name|this
operator|.
name|getPrefName
argument_list|()
operator|+
literal|"."
argument_list|)
expr_stmt|;
return|return
literal|"rgb(200,200,200)"
return|;
block|}
block|}
specifier|public
name|String
name|prefcolorNeutralBlack
parameter_list|()
block|{
if|if
condition|(
name|sNeutral
operator|.
name|equals
argument_list|(
name|getPrefProlog
argument_list|()
argument_list|)
condition|)
return|return
literal|"black"
return|;
else|else
return|return
name|prefcolor
argument_list|()
return|;
block|}
comment|/** preference to color conversion */
specifier|public
name|Color
name|awtPrefcolor
parameter_list|()
block|{
if|if
condition|(
name|getSAwtPref2color
argument_list|()
operator|.
name|containsKey
argument_list|(
name|this
operator|.
name|getPrefProlog
argument_list|()
argument_list|)
condition|)
return|return
operator|(
name|Color
operator|)
name|getSAwtPref2color
argument_list|()
operator|.
name|get
argument_list|(
name|this
operator|.
name|getPrefProlog
argument_list|()
argument_list|)
return|;
else|else
block|{
name|Debug
operator|.
name|log
argument_list|(
literal|"Unknown color for preference "
operator|+
name|this
operator|.
name|getPrefName
argument_list|()
operator|+
literal|"."
argument_list|)
expr_stmt|;
return|return
operator|new
name|Color
argument_list|(
literal|200
argument_list|,
literal|200
argument_list|,
literal|200
argument_list|)
return|;
block|}
block|}
specifier|public
specifier|static
name|String
name|int2color
parameter_list|(
name|int
name|intPref
parameter_list|)
block|{
return|return
name|prolog2color
argument_list|(
name|int2prolog
argument_list|(
name|intPref
argument_list|)
argument_list|)
return|;
block|}
specifier|public
specifier|static
name|int
name|prolog2int
parameter_list|(
name|String
name|prologPref
parameter_list|)
block|{
return|return
name|Constants
operator|.
name|preference2preferenceLevel
argument_list|(
name|prologPref
argument_list|)
return|;
block|}
specifier|public
specifier|static
name|String
name|prolog2color
parameter_list|(
name|String
name|prologPref
parameter_list|)
block|{
name|String
name|ret
init|=
operator|(
name|String
operator|)
name|sHexPref2color
operator|.
name|get
argument_list|(
name|prologPref
argument_list|)
decl_stmt|;
if|if
condition|(
name|ret
operator|==
literal|null
condition|)
name|ret
operator|=
operator|(
name|String
operator|)
name|sHexPref2color
operator|.
name|get
argument_list|(
name|sNeutral
argument_list|)
expr_stmt|;
return|return
name|ret
return|;
block|}
specifier|public
specifier|static
name|String
name|prolog2abbv
parameter_list|(
name|String
name|prologPref
parameter_list|)
block|{
name|PreferenceLevel
name|pref
init|=
name|getPreferenceLevel
argument_list|(
name|prologPref
argument_list|)
decl_stmt|;
return|return
operator|(
name|pref
operator|==
literal|null
condition|?
literal|null
else|:
name|pref
operator|.
name|getPrefAbbv
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
name|pref
operator|.
name|getPrefAbbv
argument_list|()
operator|)
return|;
block|}
specifier|public
specifier|static
name|String
name|prolog2colorNohex
parameter_list|(
name|String
name|prologPref
parameter_list|)
block|{
name|String
name|ret
init|=
operator|(
name|String
operator|)
name|sPref2color
operator|.
name|get
argument_list|(
name|prologPref
argument_list|)
decl_stmt|;
if|if
condition|(
name|ret
operator|==
literal|null
condition|)
name|ret
operator|=
operator|(
name|String
operator|)
name|sPref2color
operator|.
name|get
argument_list|(
name|sNeutral
argument_list|)
expr_stmt|;
return|return
name|ret
return|;
block|}
specifier|public
specifier|static
name|Color
name|prolog2awtColor
parameter_list|(
name|String
name|prologPref
parameter_list|)
block|{
name|Color
name|ret
init|=
operator|(
name|Color
operator|)
name|sAwtPref2color
operator|.
name|get
argument_list|(
name|prologPref
argument_list|)
decl_stmt|;
if|if
condition|(
name|ret
operator|==
literal|null
condition|)
name|ret
operator|=
operator|(
name|Color
operator|)
name|sAwtPref2color
operator|.
name|get
argument_list|(
name|sNeutral
argument_list|)
expr_stmt|;
return|return
name|ret
return|;
block|}
specifier|public
specifier|static
name|Color
name|int2awtColor
parameter_list|(
name|int
name|intPref
parameter_list|,
name|Color
name|neutralPref
parameter_list|)
block|{
name|String
name|prologPref
init|=
name|int2prolog
argument_list|(
name|intPref
argument_list|)
decl_stmt|;
if|if
condition|(
name|prologPref
operator|==
literal|null
operator|||
name|prologPref
operator|.
name|equals
argument_list|(
name|sNeutral
argument_list|)
condition|)
return|return
name|neutralPref
return|;
return|return
name|prolog2awtColor
argument_list|(
name|prologPref
argument_list|)
return|;
block|}
specifier|public
specifier|static
name|String
name|int2prolog
parameter_list|(
name|int
name|intPref
parameter_list|)
block|{
return|return
name|Constants
operator|.
name|preferenceLevel2preference
argument_list|(
name|intPref
argument_list|)
return|;
block|}
specifier|public
specifier|static
name|String
name|int2bgColor
parameter_list|(
name|int
name|intPref
parameter_list|)
block|{
return|return
name|prolog2bgColor
argument_list|(
name|int2prolog
argument_list|(
name|intPref
argument_list|)
argument_list|)
return|;
block|}
specifier|public
specifier|static
name|String
name|prolog2bgColor
parameter_list|(
name|String
name|prologPref
parameter_list|)
block|{
name|String
name|ret
init|=
operator|(
name|String
operator|)
name|sBgPref2color
operator|.
name|get
argument_list|(
name|prologPref
argument_list|)
decl_stmt|;
if|if
condition|(
name|ret
operator|==
literal|null
condition|)
name|ret
operator|=
operator|(
name|String
operator|)
name|sBgPref2color
operator|.
name|get
argument_list|(
name|sNeutral
argument_list|)
expr_stmt|;
return|return
name|ret
return|;
block|}
specifier|public
specifier|static
name|String
name|prolog2string
parameter_list|(
name|String
name|prologPref
parameter_list|)
block|{
return|return
name|getPreferenceLevel
argument_list|(
name|prologPref
argument_list|)
operator|.
name|getPrefName
argument_list|()
return|;
block|}
specifier|public
specifier|static
name|String
name|int2string
parameter_list|(
name|int
name|intPref
parameter_list|)
block|{
return|return
name|prolog2string
argument_list|(
name|int2prolog
argument_list|(
name|intPref
argument_list|)
argument_list|)
return|;
block|}
specifier|public
specifier|static
name|char
name|prolog2char
parameter_list|(
name|String
name|prologPref
parameter_list|)
block|{
if|if
condition|(
name|sRequired
operator|.
name|equals
argument_list|(
name|prologPref
argument_list|)
condition|)
return|return
name|sCharLevelRequired
return|;
if|if
condition|(
name|sStronglyPreferred
operator|.
name|equals
argument_list|(
name|prologPref
argument_list|)
condition|)
return|return
name|sCharLevelStronglyPreferred
return|;
if|if
condition|(
name|sPreferred
operator|.
name|equals
argument_list|(
name|prologPref
argument_list|)
condition|)
return|return
name|sCharLevelPreferred
return|;
if|if
condition|(
name|sDiscouraged
operator|.
name|equals
argument_list|(
name|prologPref
argument_list|)
condition|)
return|return
name|sCharLevelDiscouraged
return|;
if|if
condition|(
name|sStronglyDiscouraged
operator|.
name|equals
argument_list|(
name|prologPref
argument_list|)
condition|)
return|return
name|sCharLevelStronglyDiscouraged
return|;
if|if
condition|(
name|sProhibited
operator|.
name|equals
argument_list|(
name|prologPref
argument_list|)
condition|)
return|return
name|sCharLevelProhibited
return|;
if|if
condition|(
name|sNotAvailable
operator|.
name|equals
argument_list|(
name|prologPref
argument_list|)
condition|)
return|return
name|sCharLevelNotAvailable
return|;
return|return
name|sCharLevelNeutral
return|;
block|}
specifier|public
specifier|static
name|String
name|char2prolog
parameter_list|(
name|char
name|charPref
parameter_list|)
block|{
switch|switch
condition|(
name|charPref
condition|)
block|{
case|case
name|sCharLevelProhibited
case|:
return|return
name|sProhibited
return|;
case|case
name|sCharLevelStronglyDiscouraged
case|:
return|return
name|sStronglyDiscouraged
return|;
case|case
name|sCharLevelDiscouraged
case|:
return|return
name|sDiscouraged
return|;
case|case
name|sCharLevelNeutral
case|:
return|return
name|sNeutral
return|;
case|case
name|sCharLevelPreferred
case|:
return|return
name|sPreferred
return|;
case|case
name|sCharLevelStronglyPreferred
case|:
return|return
name|sStronglyPreferred
return|;
case|case
name|sCharLevelRequired
case|:
return|return
name|sRequired
return|;
case|case
name|sCharLevelNotAvailable
case|:
return|return
name|sNotAvailable
return|;
default|default :
return|return
name|sNeutral
return|;
block|}
block|}
specifier|public
name|boolean
name|isHard
parameter_list|()
block|{
return|return
name|sProhibited
operator|.
name|equals
argument_list|(
name|getPrefProlog
argument_list|()
argument_list|)
operator|||
name|sRequired
operator|.
name|equals
argument_list|(
name|getPrefProlog
argument_list|()
argument_list|)
return|;
block|}
specifier|public
name|String
name|getAbbreviation
parameter_list|()
block|{
return|return
name|getPrefAbbv
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
name|getPrefAbbv
argument_list|()
return|;
block|}
block|}
end_class

end_unit

