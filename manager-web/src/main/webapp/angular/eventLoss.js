lostAndFoundApp.controller('eventLossCtrl',
    function ($scope, $routeParams, $http, $location, $rootScope) {  
        var itemId = $routeParams.itemId;
        var retrievedItem;
        $http.get('/pa165/rest/items/' + itemId).then(function (response) { 
            retrievedItem = response.data;
        });
        
        var retrievedOwner = {id:2, name:"jonSnow", email:"youknow@nothing.ws"};
        // Get owner     
                
        $scope.event = {
            'item': $scope.item,
            'owner': '',
            'placeOfLoss': '',
            'dateOfLoss': ''
        };               
        $scope.addLoosing = function (event) {            
            event.item = retrievedItem;            
            event.owner = retrievedOwner;
            $http({
                method: 'POST',
                url: '/pa165/rest/events/add_loosing',
                data: event
            }).then(function success(response) {            
                $rootScope.successAlert = 'Loosing was reported';                
                $location.path("/events");
            }, function error(response) {                
                console.log("Error when reporting loosing");
                console.log(response);
                switch (response.data.code) {
                    case 'ResourceNotFoundException':
                        $rootScope.errorAlert = 'Event could not be found';
                        break;                    
                    default:
                        $rootScope.errorAlert = 'Cannot report loosing: '+response.data.message;
                        break;
                }
            });
        };
});