<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html lang="en">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <!-- Meta, title, CSS, favicons, etc. -->
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>Penpi管理平台</title>

    <!-- Bootstrap -->
    <link href="${pageContext.request.contextPath }/vendors/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Font Awesome -->
    <link href="${pageContext.request.contextPath }/vendors/font-awesome/css/font-awesome.min.css" rel="stylesheet">
    <!-- NProgress -->
    <link href="${pageContext.request.contextPath }/vendors/nprogress/nprogress.css" rel="stylesheet">
    <!-- iCheck -->
    <link href="${pageContext.request.contextPath }/vendors/iCheck/skins/flat/green.css" rel="stylesheet">
    <!-- Datatables -->
    <link href="${pageContext.request.contextPath }/vendors/datatables.net-bs/css/dataTables.bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath }/vendors/datatables.net-buttons-bs/css/buttons.bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath }/vendors/datatables.net-fixedheader-bs/css/fixedHeader.bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath }/vendors/datatables.net-responsive-bs/css/responsive.bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath }/vendors/datatables.net-scroller-bs/css/scroller.bootstrap.min.css" rel="stylesheet">

    <!-- Custom Theme Style -->
    <link href="${pageContext.request.contextPath }/build/css/custom.min.css" rel="stylesheet">
  </head>

  <body class="nav-md">
    <div class="container body">
      <div class="main_container">
        <div class="col-md-3 left_col">
          <div class="left_col scroll-view">
            <div class="navbar nav_title" style="border: 0;">
              <a href="index.html" class="site_title"><i class="fa fa-paw"></i> <span>Penpi管理平台</span></a>
            </div>

            <div class="clearfix"></div>

            <!-- menu profile quick info -->
            <div class="profile clearfix">
              <div class="profile_pic">
                <img src="images/img.jpg" alt="..." class="img-circle profile_img">
              </div>
              <div class="profile_info">
                <span>欢迎你，</span>
                <h2>瓶小盖</h2>
              </div>
            </div>
            <!-- /menu profile quick info -->

            <br />

            <!-- sidebar menu -->
            <div id="sidebar-menu" class="main_menu_side hidden-print main_menu">
              <div class="menu_section">
                <h3>通用</h3>
                <ul class="nav side-menu">
                  <li><a><i class="fa fa-home"></i> 首页 <span class="fa fa-chevron-down"></span></a>
                    <ul class="nav child_menu">
                      <li><a href="${pageContext.request.contextPath }/production/index.jsp"> Home </a></li>
                    </ul>
                  </li>
                  <li><a><i class="fa fa-edit"></i> 推送 <span class="fa fa-chevron-down"></span></a>
                    <ul class="nav child_menu">
                      <li><a href="https://www.jiguang.cn/dev/#/app/list#dev"> 极光推送 </a></li>
                      <li><a href="${pageContext.request.contextPath }/production/form.jsp"> 自定义推送 </a></li>
                    </ul>
                  </li>
                  <li><a><i class="fa fa-edit"></i> 地图 <span class="fa fa-chevron-down"></span></a>
                    <ul class="nav child_menu">
                      <li><a href="http://lbs.amap.com/dev/#/"> 高德地图 </a></li>
                    </ul>
                  </li>
                  <li><a><i class="fa fa-bar-chart-o"></i> 数据统计 <span class="fa fa-chevron-down"></span></a>
                    <ul class="nav child_menu">
                      <li><a href="${pageContext.request.contextPath }/userAction_findAllUser"> 用户数据 </a></li>
                      <li><a href="${pageContext.request.contextPath }/orderAction_findAllOrder"> 订单数据 </a></li>
                    </ul>
                  </li>
                </ul>
              </div>
              <div class="menu_section">
                <h3>其他</h3>
                <ul class="nav side-menu">
                  <li><a><i class="fa fa-windows"></i> Extras <span class="fa fa-chevron-down"></span></a>
                    <ul class="nav child_menu">
                      <li><a href="${pageContext.request.contextPath }/production/page_403.jsp">403 Error</a></li>
                      <li><a href="${pageContext.request.contextPath }/production/page_404.jsp">404 Error</a></li>
                      <li><a href="${pageContext.request.contextPath }/production/page_500.jsp">500 Error</a></li>
                      <li><a href="${pageContext.request.contextPath }/production/login.jsp">Login Page</a></li>
                    </ul>
                  </li>
                </ul>
              </div>

            </div>
            <!-- /sidebar menu -->

            <!-- /menu footer buttons -->
            <div class="sidebar-footer hidden-small">
              <a data-toggle="tooltip" data-placement="top" title="Settings">
                <span class="glyphicon glyphicon-cog" aria-hidden="true"></span>
              </a>
              <a data-toggle="tooltip" data-placement="top" title="FullScreen">
                <span class="glyphicon glyphicon-fullscreen" aria-hidden="true"></span>
              </a>
              <a data-toggle="tooltip" data-placement="top" title="Lock">
                <span class="glyphicon glyphicon-eye-close" aria-hidden="true"></span>
              </a>
              <a data-toggle="tooltip" data-placement="top" title="Logout" href="login.html">
                <span class="glyphicon glyphicon-off" aria-hidden="true"></span>
              </a>
            </div>
            <!-- /menu footer buttons -->
          </div>
        </div>

        <!-- top navigation -->
        <div class="top_nav">
          <div class="nav_menu">
            <nav>
              <div class="nav toggle">
                <a id="menu_toggle"><i class="fa fa-bars"></i></a>
              </div>

              <ul class="nav navbar-nav navbar-right">
                <li class="">
                  <a href="javascript:;" class="user-profile dropdown-toggle" data-toggle="dropdown" aria-expanded="false">
                    <img src="images/img.jpg" alt="">瓶小盖
                    <span class=" fa fa-angle-down"></span>
                  </a>
                  <ul class="dropdown-menu dropdown-usermenu pull-right">
                    <li><a href="javascript:;"> Profile</a></li>
                    <li>
                      <a href="javascript:;">
                        <span class="badge bg-red pull-right">50%</span>
                        <span>Settings</span>
                      </a>
                    </li>
                    <li><a href="javascript:;">Help</a></li>
                    <li><a href="login.html"><i class="fa fa-sign-out pull-right"></i> Log Out</a></li>
                  </ul>
                </li>

                <li role="presentation" class="dropdown">
                  <a href="javascript:;" class="dropdown-toggle info-number" data-toggle="dropdown" aria-expanded="false">
                    <i class="fa fa-envelope-o"></i>
                    <span class="badge bg-green">6</span>
                  </a>
                  <ul id="menu1" class="dropdown-menu list-unstyled msg_list" role="menu">
                    <li>
                      <a>
                        <span class="image"><img src="images/img.jpg" alt="Profile Image" /></span>
                        <span>
                          <span>John Smith</span>
                          <span class="time">3 mins ago</span>
                        </span>
                        <span class="message">
                          Film festivals used to be do-or-die moments for movie makers. They were where...
                        </span>
                      </a>
                    </li>
                    <li>
                      <a>
                        <span class="image"><img src="images/img.jpg" alt="Profile Image" /></span>
                        <span>
                          <span>John Smith</span>
                          <span class="time">3 mins ago</span>
                        </span>
                        <span class="message">
                          Film festivals used to be do-or-die moments for movie makers. They were where...
                        </span>
                      </a>
                    </li>
                    <li>
                      <a>
                        <span class="image"><img src="images/img.jpg" alt="Profile Image" /></span>
                        <span>
                          <span>John Smith</span>
                          <span class="time">3 mins ago</span>
                        </span>
                        <span class="message">
                          Film festivals used to be do-or-die moments for movie makers. They were where...
                        </span>
                      </a>
                    </li>
                    <li>
                      <a>
                        <span class="image"><img src="images/img.jpg" alt="Profile Image" /></span>
                        <span>
                          <span>John Smith</span>
                          <span class="time">3 mins ago</span>
                        </span>
                        <span class="message">
                          Film festivals used to be do-or-die moments for movie makers. They were where...
                        </span>
                      </a>
                    </li>
                    <li>
                      <div class="text-center">
                        <a>
                          <strong>See All Alerts</strong>
                          <i class="fa fa-angle-right"></i>
                        </a>
                      </div>
                    </li>
                  </ul>
                </li>
              </ul>
            </nav>
          </div>
        </div>
        <!-- /top navigation -->

        <!-- page content -->
        <div class="right_col" role="main">
          <div class="">
            <div class="page-title">
              <div class="title_left">
                <h3>Users <small>Some examples to get you started</small></h3>
              </div>

              <div class="title_right">
                <div class="col-md-5 col-sm-5 col-xs-12 form-group pull-right top_search">
                  <div class="input-group">
                    <input type="text" class="form-control" placeholder="Search for...">
                    <span class="input-group-btn">
                      <button class="btn btn-default" type="button">Go!</button>
                    </span>
                  </div>
                </div>
              </div>
            </div>

            <div class="clearfix"></div>

            <div class="row">
              <div class="col-md-12 col-sm-12 col-xs-12">
                <div class="x_panel">
                  <div class="x_title">
                    <h2>Default Example <small>Users</small></h2>
                    <ul class="nav navbar-right panel_toolbox">
                      <li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
                      </li>
                      <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false"><i class="fa fa-wrench"></i></a>
                        <ul class="dropdown-menu" role="menu">
                          <li><a href="#">Settings 1</a>
                          </li>
                          <li><a href="#">Settings 2</a>
                          </li>
                        </ul>
                      </li>
                      <li><a class="close-link"><i class="fa fa-close"></i></a>
                      </li>
                    </ul>
                    <div class="clearfix"></div>
                  </div>
                  <div class="x_content">
                    <p class="text-muted font-13 m-b-30">
                      DataTables has most features enabled by default, so all you need to do to use it with your own tables is to call the construction function: <code>$().DataTable();</code>
                    </p>
                    <table id="datatable" class="table table-striped table-bordered">
                      <thead>
                        <tr>
                          <th>orderID</th>
                          <th>StartPlace</th>
                          <th>EndPlace</th>
                          <th>sendOrderPeopleID</th>
                          <th>sendOrderPeopleName</th>
                          <th>sendOrderDate</th>
                          <th>takeOrderPeopleID</th>
                          <th>takeOrderPeopleName</th>
                          <th>takeOrderDate</th>
                          <th>charges</th>
                          <th>state</th>
                          <th>remark</th>
                          <th>evaluate</th>
                        </tr>
                      </thead>

					  <tbody>
					  
					  <s:iterator value="#session.allOrders">
						  <tr> 
						  
						    <td><s:property value="orderID" />  </td>
						    <td><s:property value="startPlace" /></td>
						    <td><s:property value="endPlace" /></td>
						    <td><s:property value="sendOrderPeople.userID" /></td>
						    <td><s:property value="sendOrderPeopleName" /></td>
						    <td><s:date name="sendOrderDate" format="yyyy-MM-dd HH:mm:ss" /></td>
						    <td><s:property value="takeOrderPeople.userID" /></td>
						    <td><s:property value="takeOrderPeopleName" /></td>
						    <td><s:date name="takeOrderDate" format="yyyy-MM-dd HH:mm:ss" /></td>
						    <td><s:property value="charges" /></td>
						    <td><s:property value="state" /></td>
						    <td><s:property value="remark" /></td>
						    <td><s:property value="evaluate" /></td>
						  	
						  </tr>
					   </s:iterator>
					  </tbody>
                    </table>
                  </div>
                </div>
              </div>

            </div>
          </div>
        </div>
        <!-- /page content -->

        <!-- footer content -->
        <footer>
          <div class="pull-right">
            Gentelella - Bootstrap Admin Template by <a href="https://colorlib.com">Colorlib</a>
          </div>
          <div class="clearfix"></div>
        </footer>
        <!-- /footer content -->
      </div>
    </div>

    <!-- jQuery -->
    <script src="${pageContext.request.contextPath }/vendors/jquery/dist/jquery.min.js"></script>
    <!-- Bootstrap -->
    <script src="${pageContext.request.contextPath }/vendors/bootstrap/dist/js/bootstrap.min.js"></script>
    <!-- FastClick -->
    <script src="${pageContext.request.contextPath }/vendors/fastclick/lib/fastclick.js"></script>
    <!-- NProgress -->
    <script src="${pageContext.request.contextPath }/vendors/nprogress/nprogress.js"></script>
    <!-- iCheck -->
    <script src="${pageContext.request.contextPath }/vendors/iCheck/icheck.min.js"></script>
    <!-- Datatables -->
    <script src="${pageContext.request.contextPath }/vendors/datatables.net/js/jquery.dataTables.min.js"></script>
    <script src="${pageContext.request.contextPath }/vendors/datatables.net-bs/js/dataTables.bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath }/vendors/datatables.net-buttons/js/dataTables.buttons.min.js"></script>
    <script src="${pageContext.request.contextPath }/vendors/datatables.net-buttons-bs/js/buttons.bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath }/vendors/datatables.net-buttons/js/buttons.flash.min.js"></script>
    <script src="${pageContext.request.contextPath }/vendors/datatables.net-buttons/js/buttons.html5.min.js"></script>
    <script src="${pageContext.request.contextPath }/vendors/datatables.net-buttons/js/buttons.print.min.js"></script>
    <script src="${pageContext.request.contextPath }/vendors/datatables.net-fixedheader/js/dataTables.fixedHeader.min.js"></script>
    <script src="${pageContext.request.contextPath }/vendors/datatables.net-keytable/js/dataTables.keyTable.min.js"></script>
    <script src="${pageContext.request.contextPath }/vendors/datatables.net-responsive/js/dataTables.responsive.min.js"></script>
    <script src="${pageContext.request.contextPath }/vendors/datatables.net-responsive-bs/js/responsive.bootstrap.js"></script>
    <script src="${pageContext.request.contextPath }/vendors/datatables.net-scroller/js/dataTables.scroller.min.js"></script>
    <script src="${pageContext.request.contextPath }/vendors/jszip/dist/jszip.min.js"></script>
    <script src="${pageContext.request.contextPath }/vendors/pdfmake/build/pdfmake.min.js"></script>
    <script src="${pageContext.request.contextPath }/vendors/pdfmake/build/vfs_fonts.js"></script>

    <!-- Custom Theme Scripts -->
    <script src="${pageContext.request.contextPath }/build/js/custom.min.js"></script>

  </body>
</html>