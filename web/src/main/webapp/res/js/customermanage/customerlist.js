//注册命令
function regCmd(command) {
    var select = function (e) {
        var data = grid.dataItem($(e.currentTarget).closest("tr"));
        if (!data) {
            $.layer.alert("请先选择需要操作的数据行");
        }
        return data;
    };
    return {
        name: command,
        className: "k-grid-" + command,
        attr: "style='display:none;'",
        click: function (e) {
            e.preventDefault();
            var data;
            switch (command) {
                case "edit":
                	var data = grid.dataItem($(e.currentTarget).closest("tr"));
                    if (!(data = select(e))){
                    	return;
                    }else{
                    	layer.open({
                    		type: 2,
                    		title: '编辑',
                    		maxmin: true, //开启最大化最小化按钮
                    		area: ['700px', '290px'],
                    		content: '/custmanagement/updateCustomer.html' + (data ? '?cid=' + data.id : '')
                    	});
                    }
                    break;
                default:
                    $.layer.alert(command);
                    break;
            }
        }
    };
}
//添加基本资料
function addCustomer(){
  layer.open({
	    type: 2,
	    title:false,
	    closeBtn:true,
	    fix: false,
	    maxmin: true,
	    shadeClose: false,
	    title: '添加',
	    area: ['700px', '290px'],
	    content: '/custmanagement/addCustomer.html',
	    end: function(){//添加完页面点击返回的时候自动加载表格数据
	    	var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
			parent.layer.close(index);
	    }
	 });
}

//客户来源
var customersourceEnum=[
    {text:"线上",value:1},
    {text:"OTS",value:2},
    {text:"直客",value:3},
    {text:"线下",value:4}
    
  ];
//初始化上部的表格布局
var grid = $("#grid").kendoGrid({
    pageSize: 20,
    height: "100%",
    sortable: true,
    editable: true,
    resizable: true,
    filterable: true,
    selectable: "row",
    serverPaging: true,
    serverSorting: true,
    serverFiltering: true,
    pageable: {
        refresh: true,
        pageSizes: true,
        buttonCount: 5
    },
    sortable: {
        mode: "multiple",
    },
    dataSource: {
        transport: {
            read: {
                type: "POST",
                dataType: "json",
                url: "/visa/customermanagement/customerlist",
                contentType: 'application/json;charset=UTF-8',
            },
            parameterMap: function (options, type) {
            		var parameter = {
                        pageNumber : options.page,    //当前页
                        pageSize : options.pageSize,//每页显示个数
                    };
               return kendo.stringify(parameter);
            }
        },
        schema: {
        	data : function(d) {
                return d.list;  //响应到页面的数据
            },
            total : function(d) {
                return d.recordCount;//总条数
            },
            model: {
                id: "id",
            }
        },
        pageSize: 20,
        serverPaging: true,
        serverFiltering: true
    },
    columns: [
        {
        	title: '序号',
        	field: 'serialnumber'
        	//template: '#= data.serialnumber#'
        },
        {
        	title: '公司名称',
        	field: 'fullcomname'
        	//template: '#= data.fullcomname#'
        },
        {
        	title: '客户来源', 
        	field: 'customersource', 
        	values:customersourceEnum
        },
        {
        	title: '联系人',
        	field: 'linkman' 
        	//template: '#= data.linkman#'
        },
        {
        	title: '手机',
        	field: 'telephone'
        	
        	//template: '#= data.telephone#'
        },
        {
        	title: '邮箱',
        	field: 'email'
        	//template: '#= data.email#'
        },
        {
            title: "操作", width: 98,
            command: [
                {name: "edit", imageClass: "base fa-pencil-square-o purple", text: "编辑"},
                regCmd("edit")
            ]
        }
    ]
}).data("kendoGrid");

//注册全局刷新以方便其他页面调用刷新
parent.refresh = function () {
    grid.dataSource.read();
    grid.refresh();
}
//事件提示
function successCallback(id){
	grid.dataSource.read();
	  if(id == '1'){
		  layer.msg("添加成功",{time:2000});
	  }else if(id == '2'){
		  layer.msg("修改成功",{time:2000});
	  }else if(id == '3'){
		  layer.msg("删除成功",{time:2000});
	  }else if(id == '4'){
		  layer.msg("初始化密码成功",{time:2000});
	  }
  }