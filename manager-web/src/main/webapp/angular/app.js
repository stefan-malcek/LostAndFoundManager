'use strict';

/* Defines application and its dependencies */
var lostAndFoundApp = angular.module('lostAndFoundApp', ['ngRoute']);

/* Configures URL fragment routing, e.g. #/product/1  */
lostAndFoundApp.config(function ($routeProvider) {
    $routeProvider.
            when('/', {templateUrl: 'partials/intro.html'}).
            when('/lostItem', {templateUrl: 'partials/lostItem.html'}).
            when('/lostItemList', {templateUrl: 'partials/lostItemList.html', controller: 'lostItemListCtrl'}).
            when('/items/create', {templateUrl: 'partials/itemCreate.html', controller: 'itemCreateCtrl'}).
            when('/createLostItem', {templateUrl: 'partials/createLostItem.html', controller: 'createLostItemCtrl'}).
            when('/foundItemList', {templateUrl: 'partials/foundItemList.html', controller: 'foundItemListCtrl'}).
            when('/admin/items', {templateUrl: 'partials/admin_items.html', controller: 'itemListCtrl'}).
            when('/admin/items/:itemId', {templateUrl: 'partials/admin_item_detail.html', controller: 'itemDetailCtrl'}).
            when('/categories', {templateUrl: 'partials/categoriesList.html', controller: 'categoriesListCtrl'}).
            when('/category/:categoryId', {templateUrl: 'partials/category_detail.html', controller: 'CategoryDetailCtrl'}).
            when('/admin/items/:itemId/return', {templateUrl: 'partials/admin_item_return.html', controller: 'itemReturnCtrl'}).
            when('/categories/create', {templateUrl: 'partials/category_new.html', controller: 'newCategoryCtrl'}).
            when('/category/edit/:categoryId', {templateUrl: 'partials/category_edit.html', controller: 'editCategoryCtrl'}).
            when('/events', {templateUrl: 'partials/eventList.html', controller: 'eventListCtrl'}).
            when('/events/without_loss', {templateUrl: 'partials/eventListWithoutLoss.html', controller: 'eventListWithoutLossCtrl'}).
            when('/events/without_find', {templateUrl: 'partials/eventListWithoutFind.html', controller: 'eventListWithoutFindCtrl'}).
            when('/events/:eventId', {templateUrl: 'partials/eventDetail.html', controller: 'eventDetailCtrl'}).
            when('/events/add_find/:itemId', {templateUrl: 'partials/eventFind.html', controller: 'eventFindCtrl'}).
            when('/events/add_loss/:itemId', {templateUrl: 'partials/eventLoss.html', controller: 'eventLossCtrl'}).
            when('/register', {templateUrl: 'partials/register.html', controller: 'registerCtrl'}).
            when('/login', {templateUrl: 'partials/login.html', controller: 'loginCtrl'}).
            when('/users', {templateUrl: 'partials/userList.html', controller: 'usersListCtrl'}).
            when('/user/:userId', {templateUrl: 'partials/userDetail.html', controller: 'userDetailCtrl'}).
            otherwise({redirectTo: '/'});
});
/*
 * alert closing functions defined in root scope to be available in every template
 */
lostAndFoundApp.run(function ($rootScope) {
    $rootScope.hideSuccessAlert = function () {
        $rootScope.successAlert = undefined;
    };
    $rootScope.hideWarningAlert = function () {
        $rootScope.warningAlert = undefined;
    };
    $rootScope.hideErrorAlert = function () {
        $rootScope.errorAlert = undefined;
    };

    $rootScope.$on('$locationChangeStart', function (event, newUrl, oldUrl) {
        // both newUrl and oldUrl are strings
        console.log('Starting to leave %s to go to %s', oldUrl, newUrl);
    });
});