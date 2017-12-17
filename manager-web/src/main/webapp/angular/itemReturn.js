lostAndFoundApp.controller('itemReturnCtrl', function ($scope, $rootScope, $http, $routeParams, $location) {
    var itemId = $routeParams.itemId;
    console.log('calling  /pa165/rest/items/' + itemId + '/can_return');

    $scope.answer = {
        'color': 'BLUE',
        'weight': 0.0,
        'height': 0,
        'width': 0,
        'depth': 0
    };

    // function called when submit button is clicked, creates category on server
    $scope.check = function (answer) {
        console.log('check answer called');

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
                });
                console.log('The item was returned');
                $rootScope.successAlert = 'The item was returned';
            }
            if (canReturn === false) {
                $rootScope.successAlert = 'The item could not be returned';
            }
            console.log(response);
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
                    $rootScope.errorAlert = 'Cannot create category ! Reason given by the server: ' + response.data.message;
                    break;
            }
        });
        $location.path("/admin/items/" + itemId);
    };
});