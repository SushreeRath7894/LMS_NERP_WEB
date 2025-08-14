(function ($) {
    $.customTreeView = function (options) {
        var defaults = {
            jsonData: [],
            selectedIds: [],
            isDisplayChildren: true,
            mode: "deletable,updatable,autoSelectChildren",
            updateCallBack: null,
            deleteCallBack: null,
            icons: {
                expand: "fa fa-plus-square",
                collapse: "fa fa-minus-square",
                leaf: "fa fa-file",
                update: "fa fa-edit",
                delete: "fa fa-trash"
            }
        };
        
        var settings = $.extend({}, defaults, options);
        var treeViewHtml = '';
        
        function loadTreeView(parentId, isOnUpdate) {
            var childItems = getChildren(parentId);
            var treeViewEndTag = '';
            
            $.each(childItems, function (index, item) {
                var hasChildrenFlag = hasChildren(item.Id);
                if (hasChildrenFlag) treeViewEndTag = '</ul></li>';
                
                appendTreeItem(item, hasChildrenFlag, getCheckedAttribute(item.Id, isOnUpdate));
                loadTreeView(item.Id, isOnUpdate);
                treeViewHtml += treeViewEndTag;
            });
            
            displayTreeView();
        }
        
        function getCheckedAttribute(id, isOnUpdate) {
            return isOnUpdate && ($.inArray(id, settings.selectedIds) !== -1) ? 'checked' : '';
        }
        
        function getChildren(parentId) {
            return settings.jsonData.filter(item => item.ParentId === parentId);
        }
        
        function hasChildren(parentId) {
            return settings.jsonData.some(item => item.ParentId === parentId);
        }
        
        function appendTreeItem(obj, hasChildren, checkedAttribute) {
            var nodeIcon = hasChildren ? settings.icons.collapse : settings.icons.leaf;
            var updatableHtml = settings.mode.includes("updatable") ? `<i class='${settings.icons.update} node-update' data-id='${obj.Id}'></i>` : '';
            var deletableHtml = settings.mode.includes("deletable") ? `<i class='${settings.icons.delete} node-delete' data-id='${obj.Id}'></i>` : '';
            
            if (hasChildren) {
                treeViewHtml += `<li><i class='${nodeIcon} toggle-node'></i> <label><input class='form-check-input chk' type='checkbox' data-id='${obj.Id}' ${checkedAttribute} /> ${obj.Title}</label> ${updatableHtml} ${deletableHtml} <ul>`;
            } else {
                treeViewHtml += `<li><i class='${nodeIcon}'></i> <label><input class='form-check-input chk leaf' type='checkbox' data-id='${obj.Id}' ${checkedAttribute} /> ${obj.Title}</label> ${updatableHtml} ${deletableHtml}</li>`;
            }
        }
        
        function displayTreeView() {
            $(".treeview").html(treeViewHtml);
        }
        
        $(document).on('click', '.toggle-node', function () {
            var _this = $(this);
            _this.toggleClass(settings.icons.expand + ' ' + settings.icons.collapse);
            _this.siblings('ul').toggle();
        });
        
        if (settings.updateCallBack) {
            $(document).on('click', '.node-update', function () {
                var selectedId = parseInt($(this).data('id'));
                settings.updateCallBack(getTreeViewObjectById(selectedId));
            });
        }
        
        if (settings.deleteCallBack) {
            $(document).on('click', '.node-delete', function () {
                var selectedId = parseInt($(this).data('id'));
                settings.deleteCallBack(getTreeViewObjectById(selectedId));
            });
        }
        
        function getTreeViewObjectById(id) {
            return settings.jsonData.find(item => item.Id === id);
        }
        
        return {
            loadTreeView: function (rootId) {
                loadTreeView(rootId, false);
            },
            getSelectedIds: function () {
                return $(".treeview .chk:checked").map(function () {
                    return parseInt($(this).data('id'));
                }).get();
            }
        }
    };
}(jQuery));