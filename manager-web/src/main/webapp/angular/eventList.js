lostAndFoundApp.controller('eventListCtrl', function ($scope, $http) {
    console.log('calling  /pa165/rest/events/');
    $http.get('/pa165/rest/events/').then(function (response) {
        var events = response.data;
        console.log('AJAX loaded all events');
        $scope.events = events;
    });
});