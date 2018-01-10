lostAndFoundApp.controller('itemListCtrl', function ($scope, $http) {
    console.log('calling  /pa165/rest/items/');
    $http.get('/pa165/rest/items/').then(function (response) {
        var items = response.data;
        console.log('AJAX loaded all items');
        $scope.items = items;
    });
    
    $scope.formatDate = function(date){
          var dateOut = new Date(date);
          return dateOut;
    };
});

lostAndFoundApp.controller('itemDetailCtrl', function ($scope, $rootScope, $http, $routeParams) {
    var itemId = $routeParams.itemId;
    console.log('calling  /pa165/rest/items/' + itemId);
    $http.get('/pa165/rest/items/' + itemId).then(function (response) {
        var item = response.data;
        $scope.item = item;
        $scope.date = new Date(item.returned);
        console.log(item);
    });

    $scope.resetAlers = function () {
        $rootScope.successAlert = undefined;
        $rootScope.warningAlert = undefined;
        $rootScope.errorAlert = undefined;
    };
});

lostAndFoundApp.controller('itemReturnCtrl', function ($scope, $rootScope, $http, $routeParams, $location) {
    var itemId = $routeParams.itemId;
    console.log('calling  /pa165/rest/items/' + itemId + '/can_return');

    $scope.colors = ['BLACK', 'GRAY', 'WHITE', 'RED', 'GREEN', 'BLUE',
        'YELLOW', 'ORANGE', 'BROWN', 'PURPLE'];

    $scope.answer = {
        'color': 'BLACK',
        'weight': 0.0,
        'height': 0,
        'width': 0,
        'depth': 0
    };

    // function called when submit button is clicked, creates category on server
    $scope.check = function (answer) {
        console.log('check answer called' + answer);
        console.log(answer);

        $http({
            method: 'PUT',
            url: '/pa165/rest/items/' + itemId + '/can_return',
            data: answer
        }).then(function success(response) {
            var canReturn = response.data;
            if (canReturn === true) {
                $http({
                    method: 'PUT',
                    url: '/pa165/rest/items/' + itemId + '/return',
                    data: ''
                }).then(function success(response) {
                    console.log('The item was returned');
                    $rootScope.successAlert = 'The item was returned';
                    $location.path("/admin/items/" + itemId);
                });
            } else {
                $rootScope.errorAlert = 'The item could not be returned. The answers have not matched.';
                $location.path("/admin/items/" + itemId);
            }
            
            console.log(response);
        }, function error(response) {
            console.log(response);
            $rootScope.errorAlert = 'An error has occured during the request.';
            $location.path("/admin/items/" + itemId);
        });
    };
});

lostAndFoundApp.controller('itemCreateCtrl',
    function ($scope, $routeParams, $http, $location, $rootScope) {
        //set object bound to form fields
        
        $scope.colors = ['BLACK', 'GRAY', 'WHITE', 'RED', 'GREEN', 'BLUE',
        'YELLOW', 'ORANGE', 'BROWN', 'PURPLE'];
        $scope.categories = {};
        
        $http.get('/pa165/rest/items/').then(function (categories) {
             $scope.categories = categories.data;
             console.log('Loaded categories for item create');
         });
        
        $scope.item = {
            'name': '',
            'category': '',
            'color': 'BLACK',
            'description': '',
            'weight': 0.0,
            'height': 0,
            'width': 0,
            'depth': 0
        };
        
        $scope.event = {
            'item': $scope.item,
            'finder': '',
            'placeOfFind': '',
            'dateOfFind': '',
            'owner': '',
            'placeOfLoss': '',
            'dateOfLoss': ''
        };
        
        // function called when submit button is clicked, creates item on server
        $scope.create = function (item) {
            $http({
                method: 'POST',
                url: '/pa165/rest/items/create',
                data: item
            }).then(function success(response) {
                var createdItem = response.data;
                //display confirmation alert
               $rootScope.successAlert = 'A new item "' + createdItem.name + '" was created';
               
               $scope.createevent = function (event) {
                   $http({
                       method: 'POST',
                       url: '/pa165/rest/items/create',
                       data: item
                   }).then(function success(response) {
                       $rootScope.successAlert = 'Event for new item was created';               
                   });
               };
            
                //change view to main page
                $location.path("/");
            }, function error(response) {
                //display error
                console.log("error when creating item");
                console.log(response);
                switch (response.data.code) {
                    case 'PersistenceException':
                        $rootScope.errorAlert = 'Item with the same attributes already exists ! ';
                        break;
                    case 'InvalidRequestException':
                        $rootScope.errorAlert = 'Sent data were found to be invalid by server ! ';
                        break;
                    default:
                        $rootScope.errorAlert = 'Cannot create item ! Reason given by the server: '+response.data.message;
                        break;
                }
            });
        };
    });
