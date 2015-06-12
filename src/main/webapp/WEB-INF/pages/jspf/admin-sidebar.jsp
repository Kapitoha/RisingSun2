<%@ page import="com.springapp.mvc.domain.Right" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<div class="navbar-default sidebar" role="navigation">
                <div class="sidebar-nav navbar-collapse">
                    <ul class="nav" id="side-menu">
                        <li>
                            <a href="admin"><i class="fa fa-dashboard fa-fw"></i> Dashboard</a>
                        </li>
                        <sec:authorize access="hasAnyAuthority('EDIT_USER')">
                            <li class="">
                                <a href="#"><i class="fa fa-user"></i> Manage Users<span class="fa arrow"></span></a>
                                <ul class="nav nav-second-level">
                                    <li>
                                        <a href="admin-users">Print Users</a>
                                    </li>
                                    <li>
                                        <a href="admin-user-create">Crate User</a>
                                    </li>
                                </ul>
                                <!-- /.nav-second-level -->
                            </li>

                        </sec:authorize>
                        <li class="">
                            <a href="#"><i class="fa fa-files-o fa-fw"></i> Manage Articles<span class="fa arrow"></span></a>
                            <ul class="nav nav-second-level">
                                <li>
                                    <a href="admin-articles-print">Print Articles</a>
                                </li>
                                <li>
                                    <a href="admin-article-create">Create Article</a>
                                </li>
                            </ul>
                            <!-- /.nav-second-level -->
                        </li>
                    </ul>
                </div>
                <!-- /.sidebar-collapse -->
            </div>