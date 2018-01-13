<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<!DOCTYPE html>
<html  lang="en">
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="shortcut icon" href="assets/ico/favicon.ico">
        <title>LostFoundmanager</title>
        <link href="assets/css/bootstrap.css" rel="stylesheet">
        <link href="assets/css/style.css" rel="stylesheet">
        <link href="assets/css/font-awesome.min.css" rel="stylesheet">
        <link href="assets/css/custom.css" rel="stylesheet">
        <script src="assets/js/jquery.min.js"></script>
        <script src="assets/js/angular.min.js"></script>
        <script src="assets/js/angular-route.min.js"></script>
        <script src="assets/js/Chart.min.js"></script>
        <script src="assets/js/angular-chart.min.js"></script>
        <script src="assets/js/bootstrap.min.js"></script>
        <script src="angular/app.js"></script>
        <script src="angular/lostItemList.js"></script>
        <script src="angular/createLostItem.js"></script>
        <script src="angular/foundItemList.js"></script>
        <script src="angular/items.js"></script>
        <script src="angular/categories.js"></script>
        <script src="assets/js/custom.js"></script>
        <script src="angular/eventList.js"></script>
        <script src="angular/eventDetail.js"></script>
        <script src="angular/eventFind.js"></script>
        <script src="angular/eventLoss.js"></script>
        <script src="angular/eventCreate.js"></script>
        <script src="angular/user.js"></script>
        <script src="angular/statistics.js"></script>
    </head>
    <body style>
        <div ng-app="lostAndFoundApp"><!-- AngularJS takes care of this element -->
            <!-- navigation bar -->
            <nav class="navbar navbar-default navbar-fixed-top">
                <div class="container">
                    <div class="navbar-header">
                        <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
                            <span class="sr-only">Toggle navigation</span>
                            <span class="icon-bar"></span>
                            <span class="icon-bar"></span>
                            <span class="icon-bar"></span>
                        </button>
                        <a class="navbar-brand" href="#">Lost&AMP;Found Manager</a>
                    </div>
                    <div id="navbar" class="navbar-collapse collapse navbar-right">
                        <ul class="nav navbar-nav" role="navigation">
                            <li><a href="#!/statistics">Statistics</a></li>
                            <li ng-if="isAdmin()" class="dropdown">
                                <a href="" class="dropdown-toggle" data-toggle="dropdown">Admin <b class="caret"></b></a>
                                <ul class="dropdown-menu" role="menu">
                                    <li><a href="#!/categories">Categories</a></li>
                                    <li><a href="#!/events">Events</a></li>
                                    <li><a href="#!/admin/items">Items</a></li>
                                    <li><a href="#!/users">Users</a></li>
                                </ul>
                            </li>
                            <li class="dropdown">
                                <a href="" class="dropdown-toggle" data-toggle="dropdown">User<b class="caret"></b></a>
                                <ul ng-if="!isAuthenticated()" class="dropdown-menu" role="menu">
                                    <li><a href="#!/login">Login</a></li>
                                    <li><a href="#!/register">Register</a></li>
                                </ul>
                                <ul ng-if="isAuthenticated()" class="dropdown-menu" role="menu">
                                    <li><a href="#!/user/{{currentUser.id}}">Profile</a></li>
                                    <li><a href="#!/logout">Logout</a></li>
                                </ul>
                            </li>
                        </ul>
                    </div>
                </div>
                            <!-- Bootstrap-styled alerts, visible when $rootScope.xxxAlert is defined -->
            </nav>
            <div class="clearfix"></div>
            <div class="container centered alertBack">
                <div ng-show="warningAlert" class="alert alert-warning alert-dismissible" role="alert">
                    <button type="button" class="close" aria-label="Close" ng-click="hideWarningAlert()"> <span aria-hidden="true">&times;</span></button>
                    <span>{{warningAlert}}</span>
                </div>
                <div ng-show="errorAlert" class="alert alert-danger alert-dismissible" role="alert">
                    <button type="button" class="close" aria-label="Close" ng-click="hideErrorAlert()"> <span aria-hidden="true">&times;</span></button>
                    <span>{{errorAlert}}</span>
                </div>
                <div ng-show="successAlert" class="alert alert-success alert-dismissible" role="alert">
                    <button type="button" class="close" aria-label="Close" ng-click="hideSuccessAlert()"> <span aria-hidden="true">&times;</span></button>
                    <span>{{successAlert}}</span>
                </div>
            </div>
<div class="clearfix"></div>
            <!-- the place where HTML templates are replaced by AngularJS routing -->
            <div ng-view class="view"></div>
        </div>
        <div id="footerwrap">
        <div class="container">
            <p>&copy;&nbsp;Masaryk University</p>
        </div>
    </div>
    </body>
</html>
