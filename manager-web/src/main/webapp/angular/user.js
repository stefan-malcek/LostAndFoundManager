/**
 * @author Adam Bananka
 */
lostAndFoundApp.controller('usersListCtrl', function ($scope, $http) {
    $http.get('/pa165/rest/users/').then(function (response) {
        var users = response.data;
        console.log('AJAX loaded all users');
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


lostAndFoundApp.controller('loginCtrl', function loginCtrl($scope, $http, $location, $rootScope) {
    $scope.user = {
        'email': '',
        'password': ''
    };

    $scope.login = function (user) {
        $http.put('/pa165/rest/users/auth', user)
                .then(function success(response) {
                    console.log(response.data);
                    console.log(user);
                    if (response.data) {
                        $http.get('/pa165/rest/users/by_email/' + user.email)
                                .then(function (response) {
                                    var userId = response.data.id;
                                    $http.get('/pa165/rest/users/' + userId + '/is_admin')
                                            .then(function (resp) {
                                                $rootScope.currentUser = {
                                                    email: user.email,
                                                    isAdmin: resp.data
                                                };
                                            });
                                });
                        console.log($scope.user.email + ' logged in');
                        $rootScope.successAlert = 'Successfully logged in';
                        $location.path("/");
                    } else {
                        $scope.errorAlert = 'Wrong email or password';
                        $location.path("/login");
                    }
                }, function error(response) {
                    //display error
                    $rootScope.errorAlert = 'Error during login';
                    console.log("error during login");
                    console.log(response);
                    $location.path("/login");
                });
    };
});


lostAndFoundApp.controller('registerCtrl',
        function ($scope, $routeParams, $http, $location, $rootScope) {
            //set object bound to form fields
            $scope.user = {
                'name': '',
                'email': '',
                'userRole': 'MEMBER'
            };
            $scope.password = '';
            $scope.register = function (user, password) {
                var params = {'user': user, 'password': password};
                $http({
                    method: 'POST',
                    url: '/pa165/rest/users/register',
                    params: params
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
                            $rootScope.errorAlert = 'Cannot create product ! Reason given by the server: ' + response.data.message;
                            break;
                    }
                });
            };
        });
