/**
 * @author Adam Bananka
 */
lostAndFoundApp.controller('usersListCtrl', function ($scope, $http) {
    $http.get('/pa165/rest/users/').then(function (response) {
        var users = response.data;
        console.log('AJAX loaded all categories');
        $scope.users = users;
    });
});


lostAndFoundApp.controller('userDetailCtrl', function ($scope, $routeParams, $http) {
        var userId = $routeParams.userId;
        $http.get('/pa165/rest/users/' + userId).then(function (response) {
            var user = response.data;
            $scope.user = user;
            $http.get('/pa165/rest/events/by_owner_id/' + user.id).then(function (response) {
                $scope.lost = response.data;
            });
            $http.get('/pa165/rest/events/by_finder_id/' + user.id).then(function (response) {
                $scope.found = response.data;
            });
            console.log('AJAX loaded detail of user ' + user.name);
        });
    });


lostAndFoundApp.controller('loginCtrl',
    function ($scope, $routeParams, $http, $location, $rootScope) {
        $scope.user = {
                'email': '',
                'password': ''
        };
        // function called when submit button is clicked
        $scope.login = function (user) {
            $http({
                method: 'PUT',
                url: '/pa165/rest/users/auth',
                data: user
            }).then(function success(response) {
                // switch (response.data) {
                //
                // }
                //display confirmation alert

                $rootScope.successAlert = 'Successfully logged in';
                //change view to list of products
                $location.path("/items");
            }, function error(response) {
                //display error
                console.log("error during login");
                console.log(response);
                $location.path("/lostItem");
                // switch (response.data.code) {
                //     case 'PersistenceException':
                //         $rootScope.errorAlert = 'Category with the same name already exists ! ';
                //         break;
                //     case 'InvalidRequestException':
                //         $rootScope.errorAlert = 'Sent data were found to be invalid by server ! ';
                //         break;
                //     default:
                //         $rootScope.errorAlert = 'Cannot create category ! Reason given by the server: '+response.data.message;
                //         break;
                // }
            });
        };
    });


lostAndFoundApp.controller('registerCtrl',
    function ($scope, $routeParams, $http, $location, $rootScope) {
        //set object bound to form fields
        $scope.reg = {
            'user': {
                'name': '',
                'email': '',
                'userRole': 'MEMBER'
            },
            'password': ''
        };
        // function called when submit button is clicked, creates product on server
        $scope.register = function (reg) {
            $http({
                method: 'POST',
                url: '/pa165/rest/users/register',
                data: reg
            }).then(function success(response) {
                console.log('registered');
                var createdProduct = response.data;
                //display confirmation alert
                $rootScope.successAlert = 'A new product "' + createdProduct.name + '" was created';
                //change view to list of products
                $location.path("/");
            }, function error(response) {
                //display error
                console.log("error when registering");
                console.log(response);
                switch (response.data.code) {
                    case 'InvalidRequestException':
                        $rootScope.errorAlert = 'Sent data were found to be invalid by server ! ';
                        break;
                    default:
                        $rootScope.errorAlert = 'Cannot create product ! Reason given by the server: '+response.data.message;
                        break;
                }
            });
        };
    });
