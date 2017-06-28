/***
 * @param $
 */
function Map() {

    this.elements = new Array();

    this.size = function() {
        return this.elements.length;
    }

    this.isEmpty = function() {
        return (this.elements.length < 1);
    }

    this.clear = function() {
        this.elements = new Array();
    }

    this.put = function(_key, _value) {
        if (!this.containsKey(_key))
            this.elements.push({
                key : _key,
                value : _value
            });
    }

    this.remove = function(_key) {
        var bln = false;

        try {
            for (i = 0; i < this.elements.length; i++) {
                if (this.elements[i].key == _key) {
                    this.elements.splice(i, 1);
                    return true;
                }
            }
        } catch (e) {
            bln = false;
        }
        return bln;
    }

    this.get = function(_key) {
        try {
            for (i = 0; i < this.elements.length; i++) {
                if (this.elements[i].key == _key) {
                    return this.elements[i].value;
                }
            }
        } catch (e) {
            return null;
        }
    }

    this.element = function(_index) {
        if (_index < 0 || _index >= this.elements.length) {
            return null;
        }
        return this.elements[_index];
    }

    this.containsKey = function(_key) {
        var bln = false;
        try {
            for (i = 0; i < this.elements.length; i++) {
                if (this.elements[i].key == _key) {
                    bln = true;
                }
            }
        } catch (e) {
            bln = false;
        }
        return bln;
    }

    this.containsValue = function(_value) {
        var bln = false;
        try {
            for (i = 0; i < this.elements.length; i++) {
                if (this.elements[i].value == _value) {
                    bln = true;
                }
            }
        } catch (e) {
            bln = false;
        }
        return bln;
    }

    this.values = function() {
        var arr = new Array();
        for (i = 0; i < this.elements.length; i++) {
            arr.push(this.elements[i].value);
        }
        return arr;
    }

    this.keys = function() {
        var arr = new Array();
        for (i = 0; i < this.elements.length; i++) {
            arr.push(this.elements[i].key);
        }
        return arr;
    }
}

(function ($) {
    $.ajaxSettings.traditional = true;//设置ajax请求防止数组参数多个中括号
    kendo.culture("zh-CN");//切换语言
    if (parent.layer) {
        $.extend({layer: parent.layer});
    } else {
        $.extend({layer: layer});
    }
    $.photos = function (array) {
        if (parent.hasOwnProperty("photos")) {
            parent.photos(array);
        } else {
            $("body").lightGallery({
                dynamic: true,
                dynamicEl: array
            });
        }
    }
    $.queryString = function (name) {
        var reg = new RegExp('(^|&)' + name + '=([^&]*)(&|$)', 'i');
        var r = window.location.search.substr(1).match(reg);
        if (r != null)return unescape(r[2]);
        return null;
    }
    $.isString = function (str) {
        return (typeof str == 'string') && str.constructor == String;
    }
    $.hashCode = function (str) {
        var hash = 0, i, chr;
        if (str.length === 0) return hash;
        for (i = 0; i < str.length; i++) {
            chr = str.charCodeAt(i);
            hash = ((hash << 5) - hash) + chr;
            hash |= 0; // Convert to 32bit integer
        }
        return hash;
    };
}
)(jQuery)

/******
 * 
 * 新建map
 * 
 * 
 */
var map=new Map();

/*//设置map里面的值
function setMap(key,newsObj)
{
    //如果key也是动态的，则如下处理
    var key=key;
    map[key]=newsObj;
}

//删除map里面的元素
function deleteMap(key)
{
    delete map[key];  
}


//获取map里面的值
function getListforMap()
{
    for(var i in map)
    {
        alert("map:"+map[i].title);
    }
}*/

/*****************************************************
 * 加载数据   小灯泡
 ****************************************************/
/*$(function () {*/
   /* var data = {cid: $.queryString("cid")};
    if (data.cid) {
        $.getJSON("/visa/customer/show", data, function (result) {
            bind(result);
        });
        
    }*/
    $(document).on('click', '.input-group-addon', function () {
        var tip = $(this);
        
        var labelName=tip.parents('.form-group').find("label").text();//获取对应的字段名称
        console.log(labelName);
        
        tip.find("i").toggleClass("fa-pulse fa-spinner fa-lightbulb-o");//变转圈圈效果~
        var node = tip.parent().find(":text,select");

      /*  $.each(node.data("bind").split(","), function (i, e) {
       *  refs/remotes/origin/master
            if (e.startsWith("value:")) {
                var source = node.closest(".row").find("*[data-params]").data("params");
                var bind = e.substring(e.indexOf("value:") + 6, e.length);
                var key = (source ? source + "." + bind : bind) + ":" + node.val();
                $.getJSON("visa/help/get", {key: key, cid: $.queryString("cid")}, function (resp) {
                    if ($.queryString("check")) {
                        layer.prompt({
                            formType: 2,
                            title: "问题描述",
                            value: (resp.data) ? resp.data.msg : ''
                        }, function (msg, index, elem) {
                            var data = {
                                "id.key": key, msg: msg,
                                "id.cid": $.queryString("cid"),
                            };
                            $.post("visa/help/add", data, function (result) {
                                layer.closeAll();
                                layer.msg(result.msg);
                            }, "JSON");
                        });
                    } else if (resp.data.msg) {
                        layer.tips(resp.data.msg, tip);
                    }
                    tip.find("i").toggleClass("fa-pulse fa-spinner fa-lightbulb-o");//变灯泡效果~
                });
            }
        });*/
        /*--  --*/
        var key=labelName.substring(0,labelName.indexOf(":"));
        if(tip.find("i").hasClass('fa-lightbulb-o')){
        	map.remove(key);
        	console.log(JSON.stringify(map));
        	/*alert("灯泡亮~~");*/
        }else{
        	map.put(key,"1");
        	/*map[labelName]=1;*/
        	console.log(JSON.stringify(map));
        	/*alert("转圈圈~~");*/
        }
    });
/*});*/
