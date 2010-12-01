begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.2 (University Timetabling Application)  * Copyright (C) 2010, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
end_comment

begin_package
package|package
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|gwt
operator|.
name|server
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|ArrayList
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
name|List
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|Query
block|{
specifier|private
name|Term
name|iQuery
init|=
literal|null
decl_stmt|;
specifier|public
name|Query
parameter_list|(
name|String
name|query
parameter_list|)
block|{
name|iQuery
operator|=
name|parse
argument_list|(
name|query
operator|.
name|trim
argument_list|()
argument_list|)
expr_stmt|;
block|}
specifier|public
name|boolean
name|match
parameter_list|(
name|TermMatcher
name|m
parameter_list|)
block|{
return|return
name|iQuery
operator|.
name|match
argument_list|(
name|m
argument_list|)
return|;
block|}
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
name|iQuery
operator|.
name|toString
argument_list|()
return|;
block|}
specifier|private
specifier|static
name|List
argument_list|<
name|String
argument_list|>
name|split
parameter_list|(
name|String
name|query
parameter_list|,
name|String
modifier|...
name|splits
parameter_list|)
block|{
name|List
argument_list|<
name|String
argument_list|>
name|ret
init|=
operator|new
name|ArrayList
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
name|int
name|bracket
init|=
literal|0
decl_stmt|;
name|boolean
name|quot
init|=
literal|false
decl_stmt|;
name|int
name|last
init|=
literal|0
decl_stmt|;
name|boolean
name|white
init|=
literal|false
decl_stmt|;
name|loop
label|:
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|query
operator|.
name|length
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
if|if
condition|(
name|query
operator|.
name|charAt
argument_list|(
name|i
argument_list|)
operator|==
literal|'"'
condition|)
block|{
name|quot
operator|=
operator|!
name|quot
expr_stmt|;
name|white
operator|=
operator|!
name|quot
expr_stmt|;
continue|continue;
block|}
if|if
condition|(
operator|!
name|quot
operator|&&
name|query
operator|.
name|charAt
argument_list|(
name|i
argument_list|)
operator|==
literal|'('
condition|)
block|{
name|bracket
operator|++
expr_stmt|;
name|white
operator|=
literal|false
expr_stmt|;
continue|continue;
block|}
if|if
condition|(
operator|!
name|quot
operator|&&
name|query
operator|.
name|charAt
argument_list|(
name|i
argument_list|)
operator|==
literal|')'
condition|)
block|{
name|bracket
operator|--
expr_stmt|;
name|white
operator|=
literal|true
expr_stmt|;
continue|continue;
block|}
if|if
condition|(
name|quot
operator|||
name|bracket
operator|>
literal|0
operator|||
operator|(
operator|!
name|white
operator|&&
name|query
operator|.
name|charAt
argument_list|(
name|i
argument_list|)
operator|!=
literal|' '
operator|)
condition|)
block|{
name|white
operator|=
operator|(
name|query
operator|.
name|charAt
argument_list|(
name|i
argument_list|)
operator|==
literal|' '
operator|)
expr_stmt|;
continue|continue;
block|}
name|white
operator|=
operator|(
name|query
operator|.
name|charAt
argument_list|(
name|i
argument_list|)
operator|==
literal|' '
operator|)
expr_stmt|;
name|String
name|q
init|=
name|query
operator|.
name|substring
argument_list|(
name|i
argument_list|)
operator|.
name|toLowerCase
argument_list|()
decl_stmt|;
for|for
control|(
name|String
name|split
range|:
name|splits
control|)
block|{
if|if
condition|(
name|split
operator|.
name|isEmpty
argument_list|()
operator|||
name|q
operator|.
name|startsWith
argument_list|(
name|split
operator|+
literal|" "
argument_list|)
operator|||
name|q
operator|.
name|startsWith
argument_list|(
name|split
operator|+
literal|"\""
argument_list|)
operator|||
name|q
operator|.
name|startsWith
argument_list|(
name|split
operator|+
literal|"("
argument_list|)
condition|)
block|{
name|String
name|x
init|=
name|query
operator|.
name|substring
argument_list|(
name|last
argument_list|,
name|i
argument_list|)
operator|.
name|trim
argument_list|()
decl_stmt|;
if|if
condition|(
name|split
operator|.
name|isEmpty
argument_list|()
operator|&&
name|x
operator|.
name|endsWith
argument_list|(
literal|":"
argument_list|)
condition|)
continue|continue;
if|if
condition|(
operator|!
name|x
operator|.
name|isEmpty
argument_list|()
condition|)
name|ret
operator|.
name|add
argument_list|(
name|x
argument_list|)
expr_stmt|;
name|last
operator|=
name|i
operator|+
name|split
operator|.
name|length
argument_list|()
expr_stmt|;
if|if
condition|(
operator|!
name|split
operator|.
name|isEmpty
argument_list|()
condition|)
name|i
operator|+=
name|split
operator|.
name|length
argument_list|()
operator|-
literal|1
expr_stmt|;
continue|continue
name|loop
continue|;
block|}
block|}
block|}
name|String
name|x
init|=
name|query
operator|.
name|substring
argument_list|(
name|last
argument_list|)
operator|.
name|trim
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
name|x
operator|.
name|isEmpty
argument_list|()
condition|)
name|ret
operator|.
name|add
argument_list|(
name|x
argument_list|)
expr_stmt|;
return|return
name|ret
return|;
block|}
specifier|private
specifier|static
name|Term
name|parse
parameter_list|(
name|String
name|query
parameter_list|)
block|{
name|List
argument_list|<
name|String
argument_list|>
name|splits
decl_stmt|;
name|splits
operator|=
name|split
argument_list|(
name|query
argument_list|,
literal|"and"
argument_list|,
literal|"&&"
argument_list|,
literal|"&"
argument_list|)
expr_stmt|;
if|if
condition|(
name|splits
operator|.
name|size
argument_list|()
operator|>
literal|1
condition|)
block|{
name|CompositeTerm
name|t
init|=
operator|new
name|AndTerm
argument_list|()
decl_stmt|;
for|for
control|(
name|String
name|q
range|:
name|splits
control|)
name|t
operator|.
name|add
argument_list|(
name|parse
argument_list|(
name|q
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|t
return|;
block|}
name|splits
operator|=
name|split
argument_list|(
name|query
argument_list|,
literal|"or"
argument_list|,
literal|"||"
argument_list|,
literal|"|"
argument_list|)
expr_stmt|;
if|if
condition|(
name|splits
operator|.
name|size
argument_list|()
operator|>
literal|1
condition|)
block|{
name|CompositeTerm
name|t
init|=
operator|new
name|OrTerm
argument_list|()
decl_stmt|;
for|for
control|(
name|String
name|q
range|:
name|splits
control|)
name|t
operator|.
name|add
argument_list|(
name|parse
argument_list|(
name|q
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|t
return|;
block|}
name|splits
operator|=
name|split
argument_list|(
name|query
argument_list|,
literal|""
argument_list|)
expr_stmt|;
if|if
condition|(
name|splits
operator|.
name|size
argument_list|()
operator|>
literal|1
condition|)
block|{
name|CompositeTerm
name|t
init|=
operator|new
name|AndTerm
argument_list|()
decl_stmt|;
name|boolean
name|not
init|=
literal|false
decl_stmt|;
for|for
control|(
name|String
name|q
range|:
name|splits
control|)
block|{
if|if
condition|(
name|q
operator|.
name|equalsIgnoreCase
argument_list|(
literal|"not"
argument_list|)
operator|||
name|q
operator|.
name|equals
argument_list|(
literal|"!"
argument_list|)
condition|)
block|{
name|not
operator|=
literal|true
expr_stmt|;
continue|continue;
block|}
if|if
condition|(
name|q
operator|.
name|startsWith
argument_list|(
literal|"!("
argument_list|)
condition|)
block|{
name|q
operator|=
name|q
operator|.
name|substring
argument_list|(
literal|1
argument_list|)
expr_stmt|;
name|not
operator|=
literal|true
expr_stmt|;
block|}
if|else if
condition|(
name|q
operator|.
name|toLowerCase
argument_list|()
operator|.
name|startsWith
argument_list|(
literal|"not("
argument_list|)
condition|)
block|{
name|q
operator|=
name|q
operator|.
name|substring
argument_list|(
literal|3
argument_list|)
expr_stmt|;
name|not
operator|=
literal|true
expr_stmt|;
block|}
if|if
condition|(
name|not
condition|)
block|{
name|t
operator|.
name|add
argument_list|(
operator|new
name|NotTerm
argument_list|(
name|parse
argument_list|(
name|q
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|not
operator|=
literal|false
expr_stmt|;
block|}
else|else
block|{
name|t
operator|.
name|add
argument_list|(
name|parse
argument_list|(
name|q
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|t
return|;
block|}
if|if
condition|(
name|query
operator|.
name|startsWith
argument_list|(
literal|"("
argument_list|)
operator|&&
name|query
operator|.
name|endsWith
argument_list|(
literal|")"
argument_list|)
condition|)
return|return
name|parse
argument_list|(
name|query
operator|.
name|substring
argument_list|(
literal|1
argument_list|,
name|query
operator|.
name|length
argument_list|()
operator|-
literal|1
argument_list|)
operator|.
name|trim
argument_list|()
argument_list|)
return|;
if|if
condition|(
name|query
operator|.
name|startsWith
argument_list|(
literal|"\""
argument_list|)
operator|&&
name|query
operator|.
name|endsWith
argument_list|(
literal|"\""
argument_list|)
condition|)
return|return
operator|new
name|AtomTerm
argument_list|(
literal|null
argument_list|,
name|query
operator|.
name|substring
argument_list|(
literal|1
argument_list|,
name|query
operator|.
name|length
argument_list|()
operator|-
literal|1
argument_list|)
operator|.
name|trim
argument_list|()
argument_list|)
return|;
name|int
name|idx
init|=
name|query
operator|.
name|indexOf
argument_list|(
literal|':'
argument_list|)
decl_stmt|;
if|if
condition|(
name|idx
operator|>=
literal|0
condition|)
block|{
return|return
operator|new
name|AtomTerm
argument_list|(
name|query
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|idx
argument_list|)
operator|.
name|trim
argument_list|()
operator|.
name|toLowerCase
argument_list|()
argument_list|,
name|query
operator|.
name|substring
argument_list|(
name|idx
operator|+
literal|1
argument_list|)
operator|.
name|trim
argument_list|()
argument_list|)
return|;
block|}
else|else
block|{
return|return
operator|new
name|AtomTerm
argument_list|(
literal|null
argument_list|,
name|query
argument_list|)
return|;
block|}
block|}
specifier|public
specifier|static
interface|interface
name|Term
block|{
specifier|public
name|boolean
name|match
parameter_list|(
name|TermMatcher
name|m
parameter_list|)
function_decl|;
block|}
specifier|public
specifier|static
specifier|abstract
class|class
name|CompositeTerm
implements|implements
name|Term
block|{
specifier|private
name|List
argument_list|<
name|Term
argument_list|>
name|iTerms
init|=
operator|new
name|ArrayList
argument_list|<
name|Term
argument_list|>
argument_list|()
decl_stmt|;
specifier|public
name|CompositeTerm
parameter_list|()
block|{
block|}
specifier|public
name|CompositeTerm
parameter_list|(
name|Term
modifier|...
name|terms
parameter_list|)
block|{
for|for
control|(
name|Term
name|t
range|:
name|terms
control|)
name|add
argument_list|(
name|t
argument_list|)
expr_stmt|;
block|}
specifier|public
name|CompositeTerm
parameter_list|(
name|Collection
argument_list|<
name|Term
argument_list|>
name|terms
parameter_list|)
block|{
for|for
control|(
name|Term
name|t
range|:
name|terms
control|)
name|add
argument_list|(
name|t
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|add
parameter_list|(
name|Term
name|t
parameter_list|)
block|{
name|iTerms
operator|.
name|add
argument_list|(
name|t
argument_list|)
expr_stmt|;
block|}
specifier|protected
name|List
argument_list|<
name|Term
argument_list|>
name|terms
parameter_list|()
block|{
return|return
name|iTerms
return|;
block|}
specifier|public
specifier|abstract
name|String
name|getOp
parameter_list|()
function_decl|;
specifier|public
name|String
name|toString
parameter_list|()
block|{
name|String
name|ret
init|=
literal|""
decl_stmt|;
for|for
control|(
name|Term
name|t
range|:
name|terms
argument_list|()
control|)
block|{
if|if
condition|(
operator|!
name|ret
operator|.
name|isEmpty
argument_list|()
condition|)
name|ret
operator|+=
literal|" "
operator|+
name|getOp
argument_list|()
operator|+
literal|" "
expr_stmt|;
name|ret
operator|+=
name|t
expr_stmt|;
block|}
return|return
operator|(
name|terms
argument_list|()
operator|.
name|size
argument_list|()
operator|>
literal|1
condition|?
literal|"("
operator|+
name|ret
operator|+
literal|")"
else|:
name|ret
operator|)
return|;
block|}
block|}
specifier|public
specifier|static
class|class
name|OrTerm
extends|extends
name|CompositeTerm
block|{
specifier|public
name|OrTerm
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
block|}
specifier|public
name|OrTerm
parameter_list|(
name|Term
modifier|...
name|terms
parameter_list|)
block|{
name|super
argument_list|(
name|terms
argument_list|)
expr_stmt|;
block|}
specifier|public
name|OrTerm
parameter_list|(
name|Collection
argument_list|<
name|Term
argument_list|>
name|terms
parameter_list|)
block|{
name|super
argument_list|(
name|terms
argument_list|)
expr_stmt|;
block|}
specifier|public
name|String
name|getOp
parameter_list|()
block|{
return|return
literal|"OR"
return|;
block|}
specifier|public
name|boolean
name|match
parameter_list|(
name|TermMatcher
name|m
parameter_list|)
block|{
if|if
condition|(
name|terms
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
return|return
literal|true
return|;
for|for
control|(
name|Term
name|t
range|:
name|terms
argument_list|()
control|)
if|if
condition|(
name|t
operator|.
name|match
argument_list|(
name|m
argument_list|)
condition|)
return|return
literal|true
return|;
return|return
literal|false
return|;
block|}
block|}
specifier|public
specifier|static
class|class
name|AndTerm
extends|extends
name|CompositeTerm
block|{
specifier|public
name|AndTerm
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
block|}
specifier|public
name|AndTerm
parameter_list|(
name|Term
modifier|...
name|terms
parameter_list|)
block|{
name|super
argument_list|(
name|terms
argument_list|)
expr_stmt|;
block|}
specifier|public
name|AndTerm
parameter_list|(
name|Collection
argument_list|<
name|Term
argument_list|>
name|terms
parameter_list|)
block|{
name|super
argument_list|(
name|terms
argument_list|)
expr_stmt|;
block|}
specifier|public
name|String
name|getOp
parameter_list|()
block|{
return|return
literal|"AND"
return|;
block|}
specifier|public
name|boolean
name|match
parameter_list|(
name|TermMatcher
name|m
parameter_list|)
block|{
for|for
control|(
name|Term
name|t
range|:
name|terms
argument_list|()
control|)
if|if
condition|(
operator|!
name|t
operator|.
name|match
argument_list|(
name|m
argument_list|)
condition|)
return|return
literal|false
return|;
return|return
literal|true
return|;
block|}
block|}
specifier|public
specifier|static
class|class
name|NotTerm
implements|implements
name|Term
block|{
specifier|private
name|Term
name|iTerm
decl_stmt|;
specifier|public
name|NotTerm
parameter_list|(
name|Term
name|t
parameter_list|)
block|{
name|iTerm
operator|=
name|t
expr_stmt|;
block|}
specifier|public
name|boolean
name|match
parameter_list|(
name|TermMatcher
name|m
parameter_list|)
block|{
return|return
operator|!
name|iTerm
operator|.
name|match
argument_list|(
name|m
argument_list|)
return|;
block|}
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
literal|"NOT "
operator|+
name|iTerm
operator|.
name|toString
argument_list|()
return|;
block|}
block|}
specifier|public
specifier|static
class|class
name|AtomTerm
implements|implements
name|Term
block|{
specifier|private
name|String
name|iAttr
decl_stmt|,
name|iBody
decl_stmt|;
specifier|public
name|AtomTerm
parameter_list|(
name|String
name|attr
parameter_list|,
name|String
name|body
parameter_list|)
block|{
if|if
condition|(
name|body
operator|.
name|startsWith
argument_list|(
literal|"\""
argument_list|)
operator|&&
name|body
operator|.
name|endsWith
argument_list|(
literal|"\""
argument_list|)
condition|)
name|body
operator|=
name|body
operator|.
name|substring
argument_list|(
literal|1
argument_list|,
name|body
operator|.
name|length
argument_list|()
operator|-
literal|1
argument_list|)
expr_stmt|;
name|iAttr
operator|=
name|attr
expr_stmt|;
name|iBody
operator|=
name|body
expr_stmt|;
block|}
specifier|public
name|boolean
name|match
parameter_list|(
name|TermMatcher
name|m
parameter_list|)
block|{
return|return
name|m
operator|.
name|match
argument_list|(
name|iAttr
argument_list|,
name|iBody
argument_list|)
return|;
block|}
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
operator|(
name|iAttr
operator|==
literal|null
condition|?
literal|""
else|:
name|iAttr
operator|+
literal|":"
operator|)
operator|+
name|iBody
return|;
block|}
block|}
specifier|public
specifier|static
interface|interface
name|TermMatcher
block|{
specifier|public
name|boolean
name|match
parameter_list|(
name|String
name|attr
parameter_list|,
name|String
name|term
parameter_list|)
function_decl|;
block|}
specifier|public
specifier|static
name|void
name|main
parameter_list|(
name|String
index|[]
name|args
parameter_list|)
block|{
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
name|parse
argument_list|(
literal|"(dept:1124 or dept:1125) and area:bio"
argument_list|)
argument_list|)
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
name|parse
argument_list|(
literal|"a \"b c\" or ddd f \"x:x\" x: s !(band or org) (a)or(b)"
argument_list|)
argument_list|)
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
name|parse
argument_list|(
literal|"! f (a)or(b) d !d not x s"
argument_list|)
argument_list|)
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
name|parse
argument_list|(
literal|""
argument_list|)
argument_list|)
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
name|split
argument_list|(
literal|"(a \"b c\")  ddd f"
argument_list|,
literal|""
argument_list|)
argument_list|)
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
name|split
argument_list|(
literal|"a \"b c\" OR not ddd f"
argument_list|,
literal|"or"
argument_list|)
argument_list|)
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
name|split
argument_list|(
literal|"a or((\"b c\" or dddor) f) q"
argument_list|,
literal|"or"
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

