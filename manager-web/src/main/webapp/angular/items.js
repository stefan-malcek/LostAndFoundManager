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