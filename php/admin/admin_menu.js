(function () {
    var AdminPanelViewModel, bind = function (fn, me) {
            return function () {
                return fn.apply(me, arguments);
            };
        };
    AdminPanelViewModel = function () {
        function AdminPanelViewModel() {
            this.collapse = bind(this.collapse, this);
            this.changeTab = bind(this.changeTab, this);
            this.displayTab = ko.observable('users');
            this.collapsedNav = ko.observable(false);
        }
        AdminPanelViewModel.prototype.changeTab = function (tab) {
            return this.displayTab(tab);
        };
        AdminPanelViewModel.prototype.collapse = function () {
            return this.collapsedNav(!this.collapsedNav());
        };
        return AdminPanelViewModel;
    }();
    $(function () {
        return ko.applyBindings(new AdminPanelViewModel());
    });
}.call(this));