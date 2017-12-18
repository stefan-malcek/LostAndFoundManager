lostAndFoundApp.controller('eventDetailCtrl', function ($scope, $http, $routeParams) {    
    var eventId = $routeParams.eventId;
    $http.get('/pa165/rest/events/' + eventId).then(function (response) {       
        var event = response.data;
        $scope.event = event;
        console.log('AJAX loaded detail of event ');
    });
});
 