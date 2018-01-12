lostAndFoundApp.controller('eventListCtrl', function ($scope, $http) {
    console.log('calling  /pa165/rest/events/');
    $http.get('/pa165/rest/events/').then(function (response) {
        var events = response.data;
        console.log('AJAX loaded all events');
        $scope.events = events;
    });
});

lostAndFoundApp.controller('eventListWithoutLossCtrl', function ($scope, $http) {
    console.log('calling  /pa165/rest/events/without_loss');
    $http.get('/pa165/rest/events/without_loss').then(function (response) {
        var events = response.data;
        console.log('AJAX loaded all events');
        $scope.events = events;
    });
});

lostAndFoundApp.controller('eventListWithoutFindCtrl', function ($scope, $http) {
    console.log('calling  /pa165/rest/events/without_find');
    $http.get('/pa165/rest/events/without_find').then(function (response) {
        var events = response.data;
        console.log('AJAX loaded all events');
        $scope.events = events;
    });
});