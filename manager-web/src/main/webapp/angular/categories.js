lostAndFoundApp.controller('categoriesListCtrl', function ($scope, $http) {
 $http.get('/pa165/rest/categories/').then(function (response) {
        var categories = response.data;
        console.log('AJAX loaded all categories');
        $scope.categories = categories;
    });
});

lostAndFoundApp.controller('CategoryDetailCtrl', ['$scope', '$routeParams', '$http',
    function ($scope, $routeParams, $http) {
        var categoryId = $routeParams.categoryId;
        $http.get('/pa165/rest/categories/' + categoryId).then(function (response) {
            var category = response.data;
            $scope.category = category;
            console.log('AJAX loaded detail of category ' + category.name);
           loadCategoryItems($http, category, '/pa165/rest/items/by_category_id/'+ categoryId);
        });
    }]);

function loadCategoryItems($http, category, prodLink) {
    $http.get(prodLink).then(function (response) {
        category.items = response.data;
        console.log('AJAX loaded ' + category.items.length + ' items to category ' + category.name);
    });
}