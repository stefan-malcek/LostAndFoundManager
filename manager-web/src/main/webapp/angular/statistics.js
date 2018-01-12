lostAndFoundApp.controller('statisticsCtrl', function ($scope, $http) {
    console.log('calling statisticsCtrl');

    $scope.eventTypes = ['Loss', 'Find'];
    $scope.type = 'Loss';

    $scope.options = {
        legend: {
            display: false
        },
        scales: {
            yAxes: [{
                    display: true,
                    ticks: {
                        suggestedMin: 0, // minimum will be 0, unless there is a lower value.
                        stepSize: 1
                    }
                }]
        }
    };


    $scope.$watch("type", function (newValue, oldValue) {
        loadStatistics($http, newValue);
    });

    function loadStatistics($http, type) {
        $http.get('/pa165/rest/events/statistics?type=' + type).then(function (response) {
            var data = response.data;
            console.log('AJAX loaded all items');
            
            $scope.labels = [];
            $scope.data = [];

            for (i = 0; i < data.length; i++) {
                $scope.labels.push(data[i].city);
                $scope.data.push(data[i].times);
            }

            console.log(data);
        });
    }

    /*$scope.chartOptions = {
     dataSource: null,
     series: {
     argumentField: "day",
     valueField: "oranges",
     name: "Loss",
     type: "bar",
     color: '#ffaa66'
     }
     };
     
     $http.get('/pa165/rest/events/statistics?type=loss').then(function (response) {
     $scope.chartOptions.dataSource = response.data;
     console.log('AJAX loaded all items');
     });*/



    /*$http.get('/pa165/rest/items/').then(function (response) {
     var items = response.data;
     console.log('AJAX loaded all items');
     $scope.items = items;
     });
     
     $scope.formatDate = function(date){
     var dateOut = new Date(date);
     return dateOut;
     };*/
});