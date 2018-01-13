lostAndFoundApp.controller('eventCreateCtrl',  function ($scope, $routeParams, $http, $location, $rootScope) {    
    var itemId = $routeParams.itemId;
    var lost = $routeParams.lost;
    $scope.lost = lost === "true" ? true : false;
 $http.get('/pa165/rest/items/' + itemId).then(function (response) {       
                var item = response.data;
                 $scope.item = item;
                });

     $scope.eventCreate = {
            'placeOfFind': '',
            'dateOfFind': '',
            'placeOfLoss': '',
            'dateOfLoss': '',
            'finder': null,
            'owner': null,
            'item': $scope.item
        };    
        
        // function called when submit button is clicked, creates item on server
        $scope.create = function (eventCreate,lost) {
              var eventData = {
                    id: 0,
                    item:  $scope.item,
                    owner: null,
                    finder: null,
                    placeOfLoss: eventCreate.placeOfLoss === "" ? null : eventCreate.placeOfLoss,
                    dateOfLoss: toJSONLocal(eventCreate.dateOfLoss),
                    dateOfFind: toJSONLocal(eventCreate.dateOfFind),
                    placeOfFind: eventCreate.placeOfFind === "" ? null : eventCreate.placeOfFind
                };
            if(lost===true) {
                eventData.owner = $rootScope.currentUser;
            } else {
                eventData.finder = $rootScope.currentUser;
            }
            console.log(eventData);
            $http({
                method: 'POST',
                url: '/pa165/rest/events/create',
                data: eventData
            }).then(function success(response) {
                   $location.path("/admin/items/"+$scope.item.id);
                   $rootScope.successAlert = 'Event created!';
            }, function error(response) {
                switch (response.data.code) {
                    case 'PersistenceException':
                        $rootScope.errorAlert = 'Item with the same attributes already exists ! ';
                        break;
                    case 'InvalidRequestException':
                        $rootScope.errorAlert = 'Sent data were found to be invalid by server ! ';
                        break;
                    default:
                        $rootScope.errorAlert = 'Cannot create item ! Reason given by the server: '+response.data.message;
                        break;
                }
            });
        };
           function toJSONLocal(date) {
               if(date==="") return "";
                var local = new Date(date);
                local.setMinutes(date.getMinutes() - date.getTimezoneOffset());
                return local.toJSON().slice(0, 10);
            }
});
         