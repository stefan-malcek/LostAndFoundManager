/**
 * @author Adam Bananka
 */
lostAndFoundApp.factory('AuthService', function ($http, $cookies, $rootScope) {
    var service = {};

    service.login = function (email) {
        var isAdmin = false;
        $http.get('/pa165/rest/users/by_email/'+ email).then(function (response) {
            var userId = response.data.id;
            $http.get('/pa165/rest/users/' + userId + '/is_admin').then(function (resp) {
                isAdmin = resp.data;
            })
        });

        $rootScope.globals = {
            currentUser: {
                email: email,
                isAdmin: isAdmin
            }
        };

        // // set default auth header for http requests
        // $http.defaults.headers.common['Authorization'] = 'Basic ' + email;
        //
        // // store user details in globals cookie that keeps user logged in for 1 week (or until they logout)
        // var cookieExp = new Date();
        // cookieExp.setDate(cookieExp.getDate() + 7);
        // $cookies.putObject('globals', $rootScope.globals, {expires: cookieExp});
    };

    service.ClearCredentials = function () {
        $rootScope.globals = {};
        // $cookies.remove('globals');
        // $http.defaults.headers.common.Authorization = 'Basic';
    };

    service.isAuthenticated = function () {
        return $rootScope.globals !== {};
    };

    service.isAdmin = function () {
        return service.isAuthenticated() && $rootScope.globals.currentUser.isAdmin;
    };

    service.getCurrentUserEmail = function () {
        return $rootScope.globals.currentUser.email;
    };

    $rootScope.authService = service;
    return service;
});
