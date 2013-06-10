begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.3 (University Timetabling Application)  * Copyright (C) 2010 - 2011, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
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
name|client
package|;
end_package

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
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|gwt
operator|.
name|client
operator|.
name|widgets
operator|.
name|UniTimeFrameDialog
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
name|gwt
operator|.
name|command
operator|.
name|client
operator|.
name|GwtRpcException
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
name|gwt
operator|.
name|resources
operator|.
name|GwtConstants
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
name|gwt
operator|.
name|shared
operator|.
name|PageAccessException
import|;
end_import

begin_import
import|import
name|com
operator|.
name|google
operator|.
name|gwt
operator|.
name|core
operator|.
name|client
operator|.
name|GWT
import|;
end_import

begin_import
import|import
name|com
operator|.
name|google
operator|.
name|gwt
operator|.
name|dom
operator|.
name|client
operator|.
name|Style
import|;
end_import

begin_import
import|import
name|com
operator|.
name|google
operator|.
name|gwt
operator|.
name|event
operator|.
name|shared
operator|.
name|UmbrellaException
import|;
end_import

begin_import
import|import
name|com
operator|.
name|google
operator|.
name|gwt
operator|.
name|http
operator|.
name|client
operator|.
name|URL
import|;
end_import

begin_import
import|import
name|com
operator|.
name|google
operator|.
name|gwt
operator|.
name|i18n
operator|.
name|client
operator|.
name|DateTimeFormat
import|;
end_import

begin_import
import|import
name|com
operator|.
name|google
operator|.
name|gwt
operator|.
name|i18n
operator|.
name|client
operator|.
name|DateTimeFormat
operator|.
name|PredefinedFormat
import|;
end_import

begin_import
import|import
name|com
operator|.
name|google
operator|.
name|gwt
operator|.
name|user
operator|.
name|client
operator|.
name|DOM
import|;
end_import

begin_import
import|import
name|com
operator|.
name|google
operator|.
name|gwt
operator|.
name|user
operator|.
name|client
operator|.
name|Element
import|;
end_import

begin_import
import|import
name|com
operator|.
name|google
operator|.
name|gwt
operator|.
name|user
operator|.
name|client
operator|.
name|ui
operator|.
name|Widget
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|ToolBox
block|{
specifier|public
specifier|static
specifier|final
name|GwtConstants
name|CONSTANTS
init|=
name|GWT
operator|.
name|create
argument_list|(
name|GwtConstants
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|public
specifier|native
specifier|static
name|void
name|disableTextSelectInternal
parameter_list|(
name|Element
name|e
parameter_list|)
comment|/*-{ 		e.ondrag = function () { return false; }; 		e.onselectstart = function () { return false; }; 		e.style.MozUserSelect="none" 	}-*/
function_decl|;
specifier|public
specifier|native
specifier|static
name|int
name|getScrollBarWidth
parameter_list|()
comment|/*-{ 	 		var inner = document.createElement("p"); 		inner.style.width = "100%"; 		inner.style.height = "200px"; 		 		var outer = document.createElement("div"); 		outer.style.position = "absolute"; 		outer.style.top = "0px"; 		outer.style.left = "0px"; 		outer.style.visibility = "hidden"; 		outer.style.width = "200px"; 		outer.style.height = "150px"; 		outer.style.overflow = "hidden"; 		outer.appendChild (inner); 		 		document.body.appendChild (outer); 		var w1 = inner.offsetWidth; 		outer.style.overflow = "scroll"; 		var w2 = inner.offsetWidth; 		if (w1 == w2) w2 = outer.clientWidth; 		 		document.body.removeChild (outer); 		  		return (w1 - w2); 	}-*/
function_decl|;
specifier|public
specifier|static
specifier|native
name|void
name|printw
parameter_list|(
name|String
name|html
parameter_list|)
comment|/*-{ 		var win = (html ? $wnd.open("about:blank", "__printingWindow") : $wnd); 		var doc = win.document; 		 		if (html) { 			doc.open();  			doc.write(html); 			doc.write("<script type=\"text/javascript\" language=\"javascript\">" +  				"function invokePrint() { " + 				"if (document.readyState&& document.readyState!='complete') " + 				"setTimeout(function() { invokePrint(); }, 50); " + 				"else if (document.body&& document.body.innerHTML=='false') " + 				"setTimeout(function() { invokePrint(); }, 50); " + 				"else { focus(); print(); }}" +  				"setTimeout(function() { invokePrint(); }, 500);" + 				"</script>"); 			doc.close(); 		} 		 		win.focus(); 	}-*/
function_decl|;
specifier|public
specifier|static
specifier|native
name|void
name|printf
parameter_list|(
name|String
name|html
parameter_list|)
comment|/*-{ 		if (navigator.userAgent.toLowerCase().indexOf('chrome')> -1) { 			@org.unitime.timetable.gwt.client.ToolBox::printw(Ljava/lang/String;)(html); 			return; 		} 		     	var frame = $doc.frames ? $doc.frames['__printingFrame'] : $doc.getElementById('__printingFrame');         if (!frame) { 			@org.unitime.timetable.gwt.client.ToolBox::printw(Ljava/lang/String;)(html);             return;          }                  var doc = null;         if (frame.contentDocument)             doc = frame.contentDocument;         else if (frame.contentWindow)             doc = frame.contentWindow.document;         else if (frame.document)             doc = frame.document;         if (!doc)  { 			@org.unitime.timetable.gwt.client.ToolBox::printw(Ljava/lang/String;)(html);             return;          }                  if (html) {         	doc.open();          	doc.write(html);          	doc.close();         }                  if (doc.readyState&& doc.readyState!='complete') {         	setTimeout(function() {         		@org.unitime.timetable.gwt.client.ToolBox::printf(Ljava/lang/String;)(null);         	}, 50);         } else if (doc.body&& doc.body.innerHTML=='false') {         	setTimeout(function() {         		@org.unitime.timetable.gwt.client.ToolBox::printf(Ljava/lang/String;)(null);         	}, 50);         } else {         	if (frame.contentWindow) frame = frame.contentWindow;         	frame.focus();         	frame.print();         }     }-*/
function_decl|;
specifier|public
specifier|static
name|void
name|print
parameter_list|(
name|String
name|title
parameter_list|,
name|String
name|user
parameter_list|,
name|String
name|session
parameter_list|,
name|Widget
modifier|...
name|widgets
parameter_list|)
block|{
name|String
name|content
init|=
literal|""
decl_stmt|;
for|for
control|(
name|Widget
name|w
range|:
name|widgets
control|)
name|content
operator|+=
literal|"<div class=\"unitime-PrintedComponent\">"
operator|+
name|DOM
operator|.
name|toString
argument_list|(
name|w
operator|.
name|getElement
argument_list|()
argument_list|)
operator|+
literal|"</div>"
expr_stmt|;
name|String
name|html
init|=
literal|"<html><header>"
operator|+
literal|"<meta http-equiv=\"content-type\" content=\"text/html; charset=UTF-8\">"
operator|+
literal|"<link type=\"text/css\" rel=\"stylesheet\" href=\""
operator|+
name|GWT
operator|.
name|getHostPageBaseURL
argument_list|()
operator|+
literal|"unitime/gwt/standard/standard.css\">"
operator|+
literal|"<link type=\"text/css\" rel=\"stylesheet\" href=\""
operator|+
name|GWT
operator|.
name|getHostPageBaseURL
argument_list|()
operator|+
literal|"styles/unitime.css\">"
operator|+
literal|"<link rel=\"shortcut icon\" href=\""
operator|+
name|GWT
operator|.
name|getHostPageBaseURL
argument_list|()
operator|+
literal|"images/timetabling.ico\">"
operator|+
literal|"<title>UniTime "
operator|+
name|CONSTANTS
operator|.
name|version
argument_list|()
operator|+
literal|"| University Timetabling Application</title>"
operator|+
literal|"</header><body class='unitime-Body'>"
operator|+
literal|"<table align=\"center\"><tr><td>"
operator|+
literal|"<table class=\"unitime-Page\"><tr><td>"
operator|+
literal|"<table id=\"header\" class=\"unitime-MainTable\" cellpadding=\"2\" cellspacing=\"0\" width=\"100%\">"
operator|+
literal|"<tr><td rowspan=\"2\"><img src=\""
operator|+
name|GWT
operator|.
name|getHostPageBaseURL
argument_list|()
operator|+
literal|"images/unitime.png\" border=\"0\"/></td>"
operator|+
literal|"<td nowrap=\"nowrap\" class=\"unitime-Title\" width=\"100%\" align=\"center\" colspan=\"2\">"
operator|+
name|title
operator|+
literal|"</td></tr>"
operator|+
literal|"<tr><td nowrap=\"nowrap\" class=\"unitime-SubTitle\" width=\"50%\" align=\"center\">"
operator|+
name|user
operator|+
literal|"</td>"
operator|+
literal|"<td nowrap=\"nowrap\" class=\"unitime-SubTitle\" width=\"50%\" align=\"center\">"
operator|+
name|session
operator|+
literal|"</td></tr></table>"
operator|+
name|content
operator|+
literal|"</td></tr></table>"
operator|+
literal|"</td></tr><tr><td>"
operator|+
literal|"<table class=\"unitime-Footer\"><tr>"
operator|+
literal|"<td width=\"33%\" align=\"left\" nowrap=\"nowrap\">Printed from UniTime "
operator|+
name|CONSTANTS
operator|.
name|version
argument_list|()
operator|+
literal|" | University Timetabling Application</td>"
operator|+
literal|"<td width=\"34%\" align=\"center\">"
operator|+
name|CONSTANTS
operator|.
name|copyright
argument_list|()
operator|+
literal|"</td>"
operator|+
literal|"<td width=\"33%\" align=\"right\">"
operator|+
name|DateTimeFormat
operator|.
name|getFormat
argument_list|(
name|PredefinedFormat
operator|.
name|DATE_TIME_MEDIUM
argument_list|)
operator|.
name|format
argument_list|(
operator|new
name|Date
argument_list|()
argument_list|)
operator|+
literal|"</td>"
operator|+
literal|"</tr></table></td></tr></table>"
operator|+
literal|"</body></html>"
decl_stmt|;
name|printf
argument_list|(
name|html
argument_list|)
expr_stmt|;
block|}
specifier|public
specifier|native
specifier|static
name|void
name|open
parameter_list|(
name|String
name|url
parameter_list|)
comment|/*-{ 		$wnd.location = url; 	}-*/
function_decl|;
specifier|public
specifier|native
specifier|static
name|void
name|eval
parameter_list|(
name|String
name|script
parameter_list|)
comment|/*-{ 		eval(script); 	}-*/
function_decl|;
specifier|public
specifier|static
name|void
name|checkAccess
parameter_list|(
name|Throwable
name|t
parameter_list|)
block|{
if|if
condition|(
name|t
operator|!=
literal|null
operator|&&
name|t
operator|instanceof
name|GwtRpcException
operator|&&
name|t
operator|.
name|getCause
argument_list|()
operator|!=
literal|null
condition|)
name|t
operator|=
name|t
operator|.
name|getCause
argument_list|()
expr_stmt|;
if|if
condition|(
name|t
operator|!=
literal|null
operator|&&
name|t
operator|instanceof
name|PageAccessException
condition|)
block|{
name|UniTimeFrameDialog
operator|.
name|openDialog
argument_list|(
literal|"UniTime "
operator|+
name|CONSTANTS
operator|.
name|version
argument_list|()
operator|+
literal|"| Log In"
argument_list|,
literal|"login.jsp?menu=hide&m="
operator|+
name|URL
operator|.
name|encodeQueryString
argument_list|(
name|t
operator|.
name|getMessage
argument_list|()
argument_list|)
argument_list|,
literal|"700px"
argument_list|,
literal|"420px"
argument_list|)
expr_stmt|;
block|}
block|}
specifier|public
specifier|native
specifier|static
name|void
name|setWhiteSpace
parameter_list|(
name|Style
name|style
parameter_list|,
name|String
name|value
parameter_list|)
comment|/*-{ 		style.whiteSpace = value; 	}-*/
function_decl|;
specifier|public
specifier|native
specifier|static
name|void
name|setMaxHeight
parameter_list|(
name|Style
name|style
parameter_list|,
name|String
name|value
parameter_list|)
comment|/*-{ 		style.maxHeight = value; 	}-*/
function_decl|;
specifier|public
specifier|native
specifier|static
name|void
name|setMaxWidth
parameter_list|(
name|Style
name|style
parameter_list|,
name|String
name|value
parameter_list|)
comment|/*-{ 		style.maxWidth = value; 	}-*/
function_decl|;
specifier|public
specifier|native
specifier|static
name|void
name|setMinWidth
parameter_list|(
name|Style
name|style
parameter_list|,
name|String
name|value
parameter_list|)
comment|/*-{ 		style.minWidth = value; 	}-*/
function_decl|;
specifier|public
specifier|native
specifier|static
name|String
name|getMinWidth
parameter_list|(
name|Style
name|style
parameter_list|)
comment|/*-{ 		return style.minWidth; 	}-*/
function_decl|;
specifier|public
specifier|native
specifier|static
name|int
name|getClientWidth
parameter_list|()
comment|/*-{ 		var sideMenu = $doc.getElementById("unitime-SideMenu").getElementsByTagName("span");     	if (sideMenu.length> 0) {     		return $doc.body.clientWidth - sideMenu[0].clientWidth;     	} else {     		return $doc.body.clientWidth;     	} 	}-*/
function_decl|;
specifier|public
specifier|native
specifier|static
name|void
name|scrollToElement
parameter_list|(
name|Element
name|element
parameter_list|)
comment|/*-{ 		element.scrollIntoView(); 	}-*/
function_decl|;
specifier|public
specifier|static
name|Throwable
name|unwrap
parameter_list|(
name|Throwable
name|e
parameter_list|)
block|{
if|if
condition|(
name|e
operator|==
literal|null
condition|)
return|return
literal|null
return|;
if|if
condition|(
name|e
operator|instanceof
name|UmbrellaException
condition|)
block|{
name|UmbrellaException
name|ue
init|=
operator|(
name|UmbrellaException
operator|)
name|e
decl_stmt|;
if|if
condition|(
name|ue
operator|.
name|getCauses
argument_list|()
operator|.
name|size
argument_list|()
operator|==
literal|1
condition|)
block|{
return|return
name|unwrap
argument_list|(
name|ue
operator|.
name|getCauses
argument_list|()
operator|.
name|iterator
argument_list|()
operator|.
name|next
argument_list|()
argument_list|)
return|;
block|}
block|}
return|return
name|e
return|;
block|}
block|}
end_class

end_unit

