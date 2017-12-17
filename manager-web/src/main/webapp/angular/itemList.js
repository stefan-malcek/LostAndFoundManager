lostAndFoundApp.controller('itemListCtrl', function ($scope, $http) {
    console.log('calling  /pa165/rest/items/');
    $http.get('/pa165/rest/items/').then(function (response) {
        var items = response.data;
        console.log('AJAX loaded all items');
        $scope.items = items;
//        for (var i = 0; i < items.length; i++) {
//            var item = items[i];
//            var categoryProductsLink = item['_links'].products.href;
//            loadCategoryProducts($http, item, categoryProductsLink);
//        }
    });
});