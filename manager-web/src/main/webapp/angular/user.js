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


lostAndFoundApp.controller('loginCtrl', function ($scope, $http, $location, $rootScope) {
    //console.log('id ' + $rootScope.currentUser.id + ' mail ' + $rootScope.currentUser.email +' admin ' +  $rootScope.currentUser.isAdmin);
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
                            //console.log('by_email response: ' + response.data.id + response.data.email);
                            var userId = response.data.id;
                            $http.get('/pa165/rest/users/' + userId + '/is_admin')
                                .then(function (resp) {
                                    $rootScope.currentUser.id = userId;
                                    $rootScope.currentUser.email = user.email;
                                    $rootScope.currentUser.isAdmin = resp.data;

                                    //console.log('id ' + $rootScope.currentUser.id + ' mail ' + $rootScope.currentUser.email +' admin ' +  $rootScope.currentUser.isAdmin);
                                    console.log($scope.user.email + ' logged in');
                                    $rootScope.successAlert = 'Successfully logged in.';
                                    $location.path("/");
                                });
                        });
                } else {
                    $scope.errorAlert = 'Wrong email or password';
                }
            }, function error(response) {
                //display error
                $rootScope.errorAlert = 'Error during login';
                console.log("error during login");
                console.log(response);
            });
    };
});

lostAndFoundApp.controller('logoutCtrl', function ($scope, $location, $rootScope) {
    $scope.logout = function () {
        $rootScope.currentUser.id = undefined;
        $rootScope.currentUser.email = undefined;
        $rootScope.currentUser.isAdmin = false;
        console.log('user logged out');
        $rootScope.successAlert = 'Successfully logged out.';
        $location.path("/");
    }
});

lostAndFoundApp.controller('userUpdatePasswordCtrl', function ($scope, $rootScope, $http, $location) {
    $scope.user = {
        'email': $rootScope.currentUser.email,
        'password': ''
    };
    $scope.newPassword = '';
    $scope.newPasswordRepeat = '';

    $scope.update = function (user, newPassword, newPasswordRepeat) {
        if (newPassword === newPasswordRepeat) {
            var params = {'user': user, 'newPassword': newPassword};
            $http.put('/pa165/rest/users/' + $rootScope.currentUser.id + '/pw', params)
                .then(function success(response) {
                    console.log('user password changed');
                    $rootScope.successAlert = 'Password successfully updated.';
                    $location.path('/user/' + $rootScope.currentUser.id);
                }, function error(response) {
                    console.log('error changing user password');
                    console.log(response);
                })
        } else {
            $rootScope.errorAlert = 'new passwords are not same';
        }
    };
});

lostAndFoundApp.controller('userUpdateNameCtrl', function ($scope, $rootScope, $http, $location, $routeParams) {
    var userId = $routeParams.userId;
    $scope.user = {
        'id': userId,
        'name': ''
    };

    $scope.update = function (user) {
        $http.put('/pa165/rest/users/' + user.id, user)
            .then(function success(response) {
                console.log('user updated');
                $rootScope.successAlert = 'User successfully updated.';
                $location.path('/user/' + user.id);
            }, function error(response) {
                console.log('error updating user');
                console.log(response);
            })
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
