lostAndFoundApp.controller('eventFindCtrl',
    function ($scope, $routeParams, $http, $location, $rootScope) {         
        
        var eventId = $routeParams.eventId;
        
        var retrievedEvent;
        $http.get('/pa165/rest/events/' + eventId).then(function (response) { 
            retrievedEvent = response.data;
            console.log(retrievedEvent);            
        });
        
         var user;
         $http.get('/pa165/rest/users/' + 2).then(function (response) {
             user = response.data;
         });
           
        
        $scope.event = {
            placeOfFind: "Brno",
            dateOfFind: new Date(2018, 01, 10, 5, 6, 1, 1)
        };
        
        $scope.addFinding = function (event) {
            var eventData = {
                id: retrievedEvent.id,
                item: retrievedEvent.item,
                owner: user,
                placeOfFind: event.placeOfFind,
                dateOfFind: toJSONLocal(event.dateOfFind)
            };
            
            console.log("hello");
            console.log(eventData);     
            
            $http({
                method: 'POST',
                url: '/pa165/rest/events/add_finding',
                data: eventData
            }).then(function success(response) {            
                $rootScope.successAlert = 'Finding was reported';                
                $location.path("/events");
            }, function error(response) {                
                console.log("Error when reporting finding");
                console.log(response);
                switch (response.data.code) {
                    case 'ResourceNotFoundException':
                        $rootScope.errorAlert = 'Event could not be found';
                        break;                    
                    default:
                        $rootScope.errorAlert = 'Cannot report finding: '+response.data.message;
                        break;
                }
            });
        };
        
        function toJSONLocal(date) {
            var local = new Date(date);
            local.setMinutes(date.getMinutes() - date.getTimezoneOffset());
            return local.toJSON().slice(0, 10);
        }
});