lostAndFoundApp.controller('itemDetailCtrl', function ($scope, $http, $routeParams) {
    var itemId = $routeParams.itemId;
    console.log('calling  /pa165/rest/items/' + itemId);
    $http.get('/pa165/rest/items/' + itemId).then(function (response) {
        var item = response.data;
        $scope.item = item;
        $scope.date = new Date(item.returned);
        console.log(item);
//        for (var i = 0; i < items.length; i++) {
//            var item = items[i];
//            var categoryProductsLink = item['_links'].products.href;
//            loadCategoryProducts($http, item, categoryProductsLink);
//        }
    });
});