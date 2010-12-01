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
name|form
package|;
end_package

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
name|org
operator|.
name|apache
operator|.
name|struts
operator|.
name|action
operator|.
name|ActionErrors
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
name|solver
operator|.
name|interactive
operator|.
name|SuggestionsModel
import|;
end_import

begin_comment
comment|/**   * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|SuggestionsForm
extends|extends
name|ActionForm
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|3222409910704167703L
decl_stmt|;
specifier|private
name|String
name|iOp
init|=
literal|null
decl_stmt|;
specifier|private
name|String
name|iFilter
init|=
literal|null
decl_stmt|;
specifier|private
name|boolean
name|iShowFilter
init|=
literal|false
decl_stmt|;
specifier|private
name|boolean
name|iCanAllowBreakHard
init|=
literal|false
decl_stmt|;
specifier|private
name|boolean
name|iAllowBreakHard
init|=
literal|false
decl_stmt|;
specifier|private
name|boolean
name|iDisplayCBS
init|=
literal|false
decl_stmt|;
specifier|private
name|boolean
name|iDisplayPlacements
init|=
literal|false
decl_stmt|;
specifier|private
name|boolean
name|iSimpleMode
init|=
literal|false
decl_stmt|;
specifier|private
name|int
name|iLimit
init|=
literal|100
decl_stmt|;
specifier|private
name|int
name|iDepth
init|=
literal|2
decl_stmt|;
specifier|private
name|long
name|iTimeout
init|=
literal|5
decl_stmt|;
specifier|private
name|Long
name|iId
init|=
literal|null
decl_stmt|;
specifier|private
name|String
name|iFilterText
init|=
literal|null
decl_stmt|;
specifier|private
name|boolean
name|iTimeoutReached
init|=
literal|false
decl_stmt|;
specifier|private
name|long
name|iNrCombinationsConsidered
init|=
literal|0
decl_stmt|;
specifier|private
name|long
name|iNrSolutions
init|=
literal|0
decl_stmt|;
specifier|private
name|long
name|iNrSuggestions
init|=
literal|0
decl_stmt|;
specifier|private
name|int
name|iMinRoomSize
init|=
operator|-
literal|1
decl_stmt|;
specifier|private
name|int
name|iMaxRoomSize
init|=
operator|-
literal|1
decl_stmt|;
specifier|private
name|boolean
name|iDisplaySuggestions
init|=
literal|false
decl_stmt|;
specifier|private
name|boolean
name|iDisplayConfTable
init|=
literal|false
decl_stmt|;
specifier|public
name|ActionErrors
name|validate
parameter_list|(
name|ActionMapping
name|mapping
parameter_list|,
name|HttpServletRequest
name|request
parameter_list|)
block|{
name|ActionErrors
name|errors
init|=
operator|new
name|ActionErrors
argument_list|()
decl_stmt|;
return|return
name|errors
return|;
block|}
specifier|public
name|void
name|reset
parameter_list|(
name|ActionMapping
name|mapping
parameter_list|,
name|HttpServletRequest
name|request
parameter_list|)
block|{
name|iOp
operator|=
literal|null
expr_stmt|;
name|iFilter
operator|=
name|SuggestionsModel
operator|.
name|sFilters
index|[
literal|0
index|]
expr_stmt|;
name|iAllowBreakHard
operator|=
literal|false
expr_stmt|;
name|iDisplayCBS
operator|=
literal|false
expr_stmt|;
name|iCanAllowBreakHard
operator|=
literal|false
expr_stmt|;
name|iShowFilter
operator|=
literal|false
expr_stmt|;
name|iId
operator|=
literal|null
expr_stmt|;
name|iTimeoutReached
operator|=
literal|false
expr_stmt|;
name|iNrCombinationsConsidered
operator|=
literal|0
expr_stmt|;
name|iNrSolutions
operator|=
literal|0
expr_stmt|;
name|iNrSuggestions
operator|=
literal|0
expr_stmt|;
name|iLimit
operator|=
literal|100
expr_stmt|;
name|iDisplayPlacements
operator|=
literal|false
expr_stmt|;
name|iSimpleMode
operator|=
literal|false
expr_stmt|;
name|iFilterText
operator|=
literal|null
expr_stmt|;
name|iMinRoomSize
operator|=
operator|-
literal|1
expr_stmt|;
name|iMaxRoomSize
operator|=
operator|-
literal|1
expr_stmt|;
name|iDisplaySuggestions
operator|=
literal|false
expr_stmt|;
name|iDisplayConfTable
operator|=
literal|false
expr_stmt|;
block|}
specifier|public
name|void
name|load
parameter_list|(
name|SuggestionsModel
name|model
parameter_list|)
block|{
name|iShowFilter
operator|=
name|model
operator|.
name|canDisplayFilter
argument_list|()
expr_stmt|;
name|iFilter
operator|=
name|SuggestionsModel
operator|.
name|sFilters
index|[
name|model
operator|.
name|getFilter
argument_list|()
index|]
expr_stmt|;
name|iCanAllowBreakHard
operator|=
name|model
operator|.
name|getCanAllowBreakHard
argument_list|()
expr_stmt|;
name|iAllowBreakHard
operator|=
name|model
operator|.
name|getAllowBreakHard
argument_list|()
expr_stmt|;
name|iDisplayCBS
operator|=
name|model
operator|.
name|getDisplayCBS
argument_list|()
expr_stmt|;
name|iDepth
operator|=
name|model
operator|.
name|getDepth
argument_list|()
expr_stmt|;
name|iTimeout
operator|=
name|model
operator|.
name|getTimeout
argument_list|()
expr_stmt|;
name|iId
operator|=
name|model
operator|.
name|getClassId
argument_list|()
expr_stmt|;
name|iTimeoutReached
operator|=
name|model
operator|.
name|getTimeoutReached
argument_list|()
expr_stmt|;
name|iNrSolutions
operator|=
name|model
operator|.
name|getNrSolutions
argument_list|()
expr_stmt|;
name|iNrCombinationsConsidered
operator|=
name|model
operator|.
name|getNrCombinationsConsidered
argument_list|()
expr_stmt|;
name|iNrSuggestions
operator|=
operator|(
name|model
operator|.
name|getSuggestions
argument_list|()
operator|==
literal|null
condition|?
literal|0
else|:
name|model
operator|.
name|getSuggestions
argument_list|()
operator|.
name|size
argument_list|()
operator|)
expr_stmt|;
name|iDisplayPlacements
operator|=
name|model
operator|.
name|getDisplayPlacements
argument_list|()
expr_stmt|;
name|iSimpleMode
operator|=
name|model
operator|.
name|getSimpleMode
argument_list|()
expr_stmt|;
name|iLimit
operator|=
name|model
operator|.
name|getLimit
argument_list|()
expr_stmt|;
name|iFilterText
operator|=
name|model
operator|.
name|getFilterText
argument_list|()
expr_stmt|;
name|iMinRoomSize
operator|=
name|model
operator|.
name|getMinRoomSize
argument_list|()
expr_stmt|;
name|iMaxRoomSize
operator|=
name|model
operator|.
name|getMaxRoomSize
argument_list|()
expr_stmt|;
name|iDisplayConfTable
operator|=
name|model
operator|.
name|getDisplayConfTable
argument_list|()
expr_stmt|;
name|iDisplaySuggestions
operator|=
name|model
operator|.
name|getDisplaySuggestions
argument_list|()
expr_stmt|;
block|}
specifier|public
name|void
name|save
parameter_list|(
name|SuggestionsModel
name|model
parameter_list|)
block|{
name|model
operator|.
name|setFilter
argument_list|(
name|getFilterInt
argument_list|()
argument_list|)
expr_stmt|;
name|model
operator|.
name|setAllowBreakHard
argument_list|(
name|getAllowBreakHard
argument_list|()
argument_list|)
expr_stmt|;
name|model
operator|.
name|setDisplayCBS
argument_list|(
name|getDisplayCBS
argument_list|()
argument_list|)
expr_stmt|;
name|model
operator|.
name|setLimit
argument_list|(
name|getLimit
argument_list|()
argument_list|)
expr_stmt|;
name|model
operator|.
name|setDisplayPlacements
argument_list|(
name|getDisplayPlacements
argument_list|()
argument_list|)
expr_stmt|;
name|model
operator|.
name|setSimpleMode
argument_list|(
name|getSimpleMode
argument_list|()
argument_list|)
expr_stmt|;
name|model
operator|.
name|setFilterText
argument_list|(
name|getFilterText
argument_list|()
argument_list|)
expr_stmt|;
name|model
operator|.
name|setMinRoomSize
argument_list|(
name|getMinRoomSize
argument_list|()
argument_list|)
expr_stmt|;
name|model
operator|.
name|setMaxRoomSize
argument_list|(
name|getMaxRoomSize
argument_list|()
argument_list|)
expr_stmt|;
name|model
operator|.
name|setDisplayConfTable
argument_list|(
name|getDisplayConfTable
argument_list|()
argument_list|)
expr_stmt|;
name|model
operator|.
name|setDisplaySuggestions
argument_list|(
name|getDisplaySuggestions
argument_list|()
argument_list|)
expr_stmt|;
block|}
specifier|public
name|String
name|getOp
parameter_list|()
block|{
return|return
name|iOp
return|;
block|}
specifier|public
name|void
name|setOp
parameter_list|(
name|String
name|op
parameter_list|)
block|{
name|iOp
operator|=
name|op
expr_stmt|;
block|}
specifier|public
name|String
name|getFilter
parameter_list|()
block|{
return|return
name|iFilter
return|;
block|}
specifier|public
name|void
name|setFilter
parameter_list|(
name|String
name|filter
parameter_list|)
block|{
name|iFilter
operator|=
name|filter
expr_stmt|;
block|}
specifier|public
name|String
index|[]
name|getFilters
parameter_list|()
block|{
return|return
name|SuggestionsModel
operator|.
name|sFilters
return|;
block|}
specifier|public
name|int
name|getFilterInt
parameter_list|()
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
name|SuggestionsModel
operator|.
name|sFilters
operator|.
name|length
condition|;
name|i
operator|++
control|)
if|if
condition|(
name|SuggestionsModel
operator|.
name|sFilters
index|[
name|i
index|]
operator|.
name|equals
argument_list|(
name|iFilter
argument_list|)
condition|)
return|return
name|i
return|;
return|return
literal|0
return|;
block|}
specifier|public
name|boolean
name|getAllowBreakHard
parameter_list|()
block|{
return|return
name|iAllowBreakHard
return|;
block|}
specifier|public
name|void
name|setAllowBreakHard
parameter_list|(
name|boolean
name|allowBreakHard
parameter_list|)
block|{
name|iAllowBreakHard
operator|=
name|allowBreakHard
expr_stmt|;
block|}
specifier|public
name|boolean
name|getDisplayCBS
parameter_list|()
block|{
return|return
name|iDisplayCBS
return|;
block|}
specifier|public
name|void
name|setDisplayCBS
parameter_list|(
name|boolean
name|displayCBS
parameter_list|)
block|{
name|iDisplayCBS
operator|=
name|displayCBS
expr_stmt|;
block|}
specifier|public
name|boolean
name|getDisplayPlacements
parameter_list|()
block|{
return|return
name|iDisplayPlacements
return|;
block|}
specifier|public
name|void
name|setDisplayPlacements
parameter_list|(
name|boolean
name|displayPlacements
parameter_list|)
block|{
name|iDisplayPlacements
operator|=
name|displayPlacements
expr_stmt|;
block|}
specifier|public
name|int
name|getLimit
parameter_list|()
block|{
return|return
name|iLimit
return|;
block|}
specifier|public
name|void
name|setLimit
parameter_list|(
name|int
name|limit
parameter_list|)
block|{
name|iLimit
operator|=
name|limit
expr_stmt|;
block|}
specifier|public
name|boolean
name|getSimpleMode
parameter_list|()
block|{
return|return
name|iSimpleMode
return|;
block|}
specifier|public
name|void
name|setSimpleMode
parameter_list|(
name|boolean
name|simpleMode
parameter_list|)
block|{
name|iSimpleMode
operator|=
name|simpleMode
expr_stmt|;
block|}
specifier|public
name|String
name|getFilterText
parameter_list|()
block|{
return|return
name|iFilterText
return|;
block|}
specifier|public
name|void
name|setFilterText
parameter_list|(
name|String
name|text
parameter_list|)
block|{
name|iFilterText
operator|=
name|text
expr_stmt|;
block|}
specifier|public
name|int
name|getDepth
parameter_list|()
block|{
return|return
name|iDepth
return|;
block|}
specifier|public
name|void
name|setDepth
parameter_list|(
name|int
name|depth
parameter_list|)
block|{
name|iDepth
operator|=
name|depth
expr_stmt|;
block|}
specifier|public
name|long
name|getTimeout
parameter_list|()
block|{
return|return
name|iTimeout
operator|/
literal|1000
return|;
block|}
specifier|public
name|void
name|setTimeout
parameter_list|(
name|long
name|timeout
parameter_list|)
block|{
name|iTimeout
operator|=
literal|1000
operator|*
name|timeout
expr_stmt|;
block|}
specifier|public
name|Long
name|getId
parameter_list|()
block|{
return|return
name|iId
return|;
block|}
specifier|public
name|void
name|setId
parameter_list|(
name|Long
name|id
parameter_list|)
block|{
name|iId
operator|=
name|id
expr_stmt|;
block|}
specifier|public
name|boolean
name|getShowFilter
parameter_list|()
block|{
return|return
name|iShowFilter
return|;
block|}
specifier|public
name|void
name|setShowFilter
parameter_list|(
name|boolean
name|showFilter
parameter_list|)
block|{
name|iShowFilter
operator|=
name|showFilter
expr_stmt|;
block|}
specifier|public
name|boolean
name|getCanAllowBreakHard
parameter_list|()
block|{
return|return
name|iCanAllowBreakHard
return|;
block|}
specifier|public
name|void
name|setCanAllowBreakHard
parameter_list|(
name|boolean
name|canAllowBreakHard
parameter_list|)
block|{
name|iCanAllowBreakHard
operator|=
name|canAllowBreakHard
expr_stmt|;
block|}
specifier|public
name|boolean
name|getTimeoutReached
parameter_list|()
block|{
return|return
name|iTimeoutReached
return|;
block|}
specifier|public
name|long
name|getNrCombinationsConsidered
parameter_list|()
block|{
return|return
name|iNrCombinationsConsidered
return|;
block|}
specifier|public
name|long
name|getNrSolutions
parameter_list|()
block|{
return|return
name|iNrSolutions
return|;
block|}
specifier|public
name|long
name|getNrSuggestions
parameter_list|()
block|{
return|return
name|iNrSuggestions
return|;
block|}
specifier|public
name|int
name|getMinRoomSize
parameter_list|()
block|{
return|return
name|iMinRoomSize
return|;
block|}
specifier|public
name|int
name|getMaxRoomSize
parameter_list|()
block|{
return|return
name|iMaxRoomSize
return|;
block|}
specifier|public
name|void
name|setMinRoomSize
parameter_list|(
name|int
name|minRoomSize
parameter_list|)
block|{
name|iMinRoomSize
operator|=
name|minRoomSize
expr_stmt|;
block|}
specifier|public
name|void
name|setMaxRoomSize
parameter_list|(
name|int
name|maxRoomSize
parameter_list|)
block|{
name|iMaxRoomSize
operator|=
name|maxRoomSize
expr_stmt|;
block|}
specifier|public
name|boolean
name|getDisplayConfTable
parameter_list|()
block|{
return|return
name|iDisplayConfTable
return|;
block|}
specifier|public
name|void
name|setDisplayConfTable
parameter_list|(
name|boolean
name|displayConfTable
parameter_list|)
block|{
name|iDisplayConfTable
operator|=
name|displayConfTable
expr_stmt|;
block|}
specifier|public
name|boolean
name|getDisplaySuggestions
parameter_list|()
block|{
return|return
name|iDisplaySuggestions
return|;
block|}
specifier|public
name|void
name|setDisplaySuggestions
parameter_list|(
name|boolean
name|displaySuggestions
parameter_list|)
block|{
name|iDisplaySuggestions
operator|=
name|displaySuggestions
expr_stmt|;
block|}
specifier|public
name|String
name|getMinRoomSizeText
parameter_list|()
block|{
return|return
operator|(
name|iMinRoomSize
operator|<
literal|0
condition|?
literal|""
else|:
name|String
operator|.
name|valueOf
argument_list|(
name|iMinRoomSize
argument_list|)
operator|)
return|;
block|}
specifier|public
name|String
name|getMaxRoomSizeText
parameter_list|()
block|{
return|return
operator|(
name|iMaxRoomSize
operator|<
literal|0
condition|?
literal|""
else|:
name|String
operator|.
name|valueOf
argument_list|(
name|iMaxRoomSize
argument_list|)
operator|)
return|;
block|}
specifier|public
name|void
name|setMinRoomSizeText
parameter_list|(
name|String
name|minRoomSizeText
parameter_list|)
block|{
try|try
block|{
name|iMinRoomSize
operator|=
name|Integer
operator|.
name|parseInt
argument_list|(
name|minRoomSizeText
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|NumberFormatException
name|e
parameter_list|)
block|{
name|iMinRoomSize
operator|=
operator|-
literal|1
expr_stmt|;
block|}
block|}
specifier|public
name|void
name|setMaxRoomSizeText
parameter_list|(
name|String
name|maxRoomSizeText
parameter_list|)
block|{
try|try
block|{
name|iMaxRoomSize
operator|=
name|Integer
operator|.
name|parseInt
argument_list|(
name|maxRoomSizeText
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|NumberFormatException
name|e
parameter_list|)
block|{
name|iMaxRoomSize
operator|=
operator|-
literal|1
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

