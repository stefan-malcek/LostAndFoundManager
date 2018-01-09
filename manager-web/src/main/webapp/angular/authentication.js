/**
 * @author Adam Bananka
 */
lostAndFoundApp.factory('AuthService', function AuthService($http, $cookies, $rootScope) {
    var service = {};

    service.login = login;
    service.ClearCredentials = ClearCredentials;
    service.isAuthenticated = isAuthenticated;
    service.isAdmin = isAdmin;
    service.getCurrentUserEmail = getCurrentUserEmail;

    return service;

    function login(email) {
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
    }

    function ClearCredentials() {
        $rootScope.globals = {};
        // $cookies.remove('globals');
        // $http.defaults.headers.common.Authorization = 'Basic';
    }

    function isAuthenticated() {
        return $rootScope.globals !== {};
    }

    function isAdmin() {
        return isAuthenticated() && $rootScope.globals.currentUser.isAdmin;
    }

    function getCurrentUserEmail() {
        return $rootScope.globals.currentUser.email;
    }
});
