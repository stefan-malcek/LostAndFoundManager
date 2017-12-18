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


lostAndFoundApp.controller('newCategoryCtrl',
    function ($scope, $routeParams, $http, $location, $rootScope) {
        //set object bound to form fields
        $scope.category = {
            'name': '',
            'description': ''
        };
        // function called when submit button is clicked, creates category on server
        $scope.create = function (category) {
            $http({
                method: 'POST',
                url: '/pa165/rest/categories/create',
                data: category
            }).then(function success(response) {
                var createdCategory = response.data;
                //display confirmation alert
               $rootScope.successAlert = 'A new category "' + createdCategory.name + '" was created';
                //change view to list of products
                $location.path("/categories");
            }, function error(response) {
                //display error
                console.log("error when creating category");
                console.log(response);
                switch (response.data.code) {
                    case 'PersistenceException':
                        $rootScope.errorAlert = 'Category with the same name already exists ! ';
                        break;
                    case 'InvalidRequestException':
                        $rootScope.errorAlert = 'Sent data were found to be invalid by server ! ';
                        break;
                    default:
                        $rootScope.errorAlert = 'Cannot create category ! Reason given by the server: '+response.data.message;
                        break;
                }
            });
        };
    });
    
    
    function loadCategory($http, category, prodLink) {
    $http.get(prodLink).then(function (response) {
        category.name = response.data.name;
        category.description = response.data.description; 
        category.id = response.data.id;
         console.log('AJAX loaded ' + category.name + ' category.');
    });
}
    
    lostAndFoundApp.controller('editCategoryCtrl',
    function ($scope, $routeParams, $http, $location, $rootScope) {
 
        $scope.category = {
            'name': '',
            'description': '',
            'id':''
        };
       loadCategory($http, $scope.category, '/pa165/rest/categories/' +  $routeParams.categoryId);
        
        //set object bound to form fields
        
        // function called when submit button is clicked, creates category on server
        $scope.create = function (category) {
            $http({
                method: 'PUT',
                url: '/pa165/rest/categories/' + category.id,
                data: category
            }).then(function success(response) {
                var createdCategory = response.data;
                //display confirmation alert
               $rootScope.successAlert = 'A categpry "' + createdCategory.name + '" was updated';
                //change view to list of products
                $location.path("/categories");
            }, function error(response) {
                //display error
                console.log("error when creating category");
                console.log(response);
                switch (response.data.code) {
                    case 'PersistenceException':
                        $rootScope.errorAlert = 'Category with the same name already exists ! ';
                        break;
                    case 'InvalidRequestException':
                        $rootScope.errorAlert = 'Sent data were found to be invalid by server ! ';
                        break;
                    default:
                        $rootScope.errorAlert = 'Cannot create category ! Reason given by the server: '+response.data.message;
                        break;
                }
            });
        };
    });