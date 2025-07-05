app = angular.module("admin-app", ["ngRoute"]);

app.config(function ($routeProvider) {
    $routeProvider
        .when("/nhan-vien", {
            templateUrl: "/admin/nhan-vien/list.html",
            controller: "nhan-vien-ctrl"
        })

        .otherwise({
            template: "<h1 class='text-center'>Quản trị viên</h1>"
        });
})