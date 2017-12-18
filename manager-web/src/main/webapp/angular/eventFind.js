lostAndFoundApp.controller('eventFindCtrl',
    function ($scope, $routeParams, $http, $location, $rootScope) {  
        var itemId = $routeParams.itemId;
        var retrievedItem;
        $http.get('/pa165/rest/items/' + itemId).then(function (response) { 
            retrievedItem = response.data;
        });
        
        var retrievedFinder = {id:2, name:"jonSnow", email:"youknow@nothing.ws"};
        // Get finder   
                
        $scope.event = {
            'item': '',
            'finder': '',
            'placeOfFind': '',
            'dateOfFind': ''
        };               
        $scope.addFinding = function (event) {            
            event.item = retrievedItem;            
            event.finder = retrievedFinder;       
            
            $http({
                method: 'POST',
                url: '/pa165/rest/events/add_finding',
                data: event
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
});