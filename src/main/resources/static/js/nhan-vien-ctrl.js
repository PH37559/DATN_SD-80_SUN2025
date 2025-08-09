app.controller("nhan-vien-ctrl", function ($scope, $http) {
    $scope.items = [];
    $scope.chucVu = [];
    $scope.form = {};
    $scope.errors ={}

    $scope.initialize = function () {
        //hiện thị ds nhân viên
        $http.get("/nhan-vien/hien-thi").then(resp => {
            $scope.items = resp.data;
        });
        //hiện thị ds chức vụ
        $http.get("/chuc-vu/hien-thi").then(resp => {
            $scope.chucVu = resp.data;
        })
    }

    //Khởi đầu
    $scope.initialize();

    //Xóa form
    $scope.reset = function () {
        $scope.form = {
            ngayLamViec: new Date(),
            trangThai: true,
            chucVu: null
        };
        const quanLy = $scope.chucVu.find(c => c.id === 1);
        if (quanLy) {
            $scope.form.chucVu = quanLy;
        }
        $scope.errors ={}
    }

    //Hiện thị thông tin nhân viên lên form
    $scope.edit = function (item) {
        $scope.form = angular.copy(item);
        $scope.form.ngayLamViec = new Date(item.ngayLamViec);
        $scope.form.chucVu = $scope.chucVu.find(c => c.id === item.chucVu.id);
        $scope.errors = {};
        $(".nav-pills button:eq(1)").tab('show');
    }

    //Thêm nhân viên mới
    $scope.store = function () {
        var item = angular.copy($scope.form);
        item.idChucVu = $scope.form.chucVu.id;
        item.trangThai = parseInt(item.trangThai);
        $http.post('/nhan-vien', item).then(resp => {
            resp.data.ngayLamViec = new Date(resp.data.ngayLamViec)
            $scope.items.push(resp.data);
            $scope.reset();
            alert("Thêm mới nhân viên thành công!");
        }).catch(error => {
            if (error.data) {
                $scope.errors = error.data;
            } else {
                alert("Lỗi thêm mới nhân viên");
                console.log("Error", error);
            }
        })
    };

    // Cập nhật nhân viên
    $scope.update = function () {
        var item = angular.copy($scope.form);
        item.idChucVu = $scope.form.chucVu.id;
        item.trangThai = parseInt(item.trangThai);
        $http.put(`/nhan-vien/${item.id}`, item).then(resp => {
            // Nếu thành công thì confirm cập nhật
            if (confirm("Bạn có chắc chắn muốn cập nhật thông tin nhân viên không?")) {
                var index = $scope.items.findIndex(p => p.id == item.id);
                resp.data.ngayLamViec = new Date(resp.data.ngayLamViec);
                $scope.items[index] = resp.data;
                alert("Cập nhật nhân viên thành công!");
            }
        }).catch(error => {
            if (error.data) {
                $scope.errors = error.data; // hiện thị lỗi dưới input
            } else {
                alert("Lỗi cập nhật nhân viên!");
                console.log("Error", error);
            }
        });
    }
    // Xóa nhân viên
    $scope.delete = function (item) {
        if (confirm(`Bạn có chắc chắn muốn xóa nhân viên "${item.hoTen}" không?`)) {
            $http.delete(`/nhan-vien/${item.id}`).then(resp => {
                var index = $scope.items.findIndex(p => p.id == item.id);
                $scope.items.splice(index, 1);
                $scope.reset();
                alert("Xóa nhân viên thành công!");
            }).catch(error => {
                alert("Lỗi khi xóa nhân viên!");
                console.log("Error", error);
            });
        }
    };


    $scope.pager = {
        page: 0,
        size: 10,
        get items() {
            var start = this.page * this.size;
            return $scope.items.slice(start, start + this.size);
        },
        get count() {
            return Math.ceil(1 * $scope.items.length / this.size);
        },
        first() {
            this.page = 0;
        },
        prev() {
            this.page--;
            if (this.page < 0) {
                this.first();
            }
        },
        next() {
            this.page++;
            if (this.page >= this.count) {
                this.last();
            }
        },
        last() {
            this.page = this.count - 1;
        }

    }
});