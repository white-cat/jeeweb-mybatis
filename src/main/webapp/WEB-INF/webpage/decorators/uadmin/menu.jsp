<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<li <c:if test="${currentMenu == null}">class="active"</c:if>><a href="${adminPath}"><i class="fa fa-tachometer fa-fw"><div class="icon-bg bg-orange"></div></i><span class="menu-title">控制台</span></a></li>
<c:forEach items="${menus}" var="menu">
     <c:if test="${menu.parentId == null && menu.isshow eq '1'}">
		<li <c:if test="${fn:contains(pmenuids,menu.id)==true||menu.id == currentMenu.id}">class="active"</c:if>>
		    <c:choose>
			   <c:when test="${menu.hasChildren}">
			      <a href="#"><i class="fa <c:if test="${empty menu.menuIcon}">fa-desktop</c:if> ${menu.menuIcon} fa-fw"><div class="icon-bg bg-dark"></div></i>
			      <span class="menu-title">${menu.name}</span><span class="fa arrow"></span></a>
					 <ul class="nav nav-second-level collapse<c:if test="${fn:contains(pmenuids,menu.id)==true||menu.id == currentMenu.id}"> in</c:if>">
					 <c:forEach items="${menus}" var="secondMenu">
					     <c:if test="${secondMenu.parentId == menu.id && secondMenu.isshow eq '1'}">
						      <li <c:if test="${fn:contains(pmenuids,secondMenu.id)==true||secondMenu.id == currentMenu.id}">class="active"</c:if>>
						        <c:choose>
								   <c:when test="${secondMenu.hasChildren}">  
									    <a href="#"><i class="fa <c:if test="${empty secondMenu.menuIcon}">fa-angle-right</c:if>${secondMenu.menuIcon}"></i>
									    <span class="submenu-title">${secondMenu.name}</span><span class="fa arrow"></span></a>
									    <ul class="nav nav-third-level collapse <c:if test="${fn:contains(pmenuids,secondMenu.id)==true||secondMenu.id == currentMenu.id}">in</c:if>">
									    <c:forEach items="${menus}" var="thirdMenu">
										       <c:if test="${thirdMenu.parentId == secondMenu.id && thirdMenu.isshow eq '1'}">
												   <li <c:if test="${fn:contains(pmenuids,thirdMenu.id)==true||thirdMenu.id == currentMenu.id}">class="active"</c:if>>
												      <a href="${adminPath}/${thirdMenu.url}"><i class="fa <c:if test="${empty thirdMenu.menuIcon}">fa-angle-double-right</c:if>${thirdMenu.menuIcon}"></i>
												      <span class="submenu-title">${thirdMenu.name}</span></a>
												    </li>
											   </c:if>
											</c:forEach>
							            </ul>
								   </c:when>
								   <c:otherwise> 
									   <a href="${adminPath}/${secondMenu.url}"><i class="fa <c:if test="${empty secondMenu.menuIcon}">fa-angle-right</c:if>${secondMenu.menuIcon}"></i>
									   <span class="submenu-title">${secondMenu.name}</span></a>
								   </c:otherwise>
								</c:choose>
							 </li>
			  		  </c:if>
					</c:forEach>
				    </ul>
			   </c:when>
			   <c:otherwise>
			      <a href="${adminPath}/${menu.url}"><i class="fa ${menu.menuIcon}"><div class="icon-bg bg-orange"></div></i><span class="menu-title">${menu.name}</span></a>
			   </c:otherwise>
			</c:choose>
		</li>
    </c:if>
</c:forEach>