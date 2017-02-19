/**
 * Created by huxuemin on 2016-5-18.
 */
(function($){
    $.simpleBlog = function(){};

    $.simpleBlog.asynSubmit = function(el, options) {
        var base = this;
        base.$el = $(el);
        base.el = el;
        base.$el.data("asynSubmit", base);
        base.init = function() {
            base.options = $.extend({}, $.simpleBlog.asynSubmit.defaultOptions, options);
            if (base.options.submitElement !== false) {
                var $submitElement = base.options.submitElement;
            } else {
                var $submitElement = base.$el.find(":input:submit");
            }
            $submitElement.bind(base.options.submitEvent,function() {
                $(this).attr("disabled",true);
                if(base.options.beforeSubmit()) {
                    $.ajax({
                        url: base.$el.attr("action"),
                        type: base.$el.attr("method"),
                        dataType:'json',
                        data: base.$el.serializeArray(),
                        success: base.options.successSubmit,
                        error: base.options.errorSubmit,
                        complete: function(XMLHttpRequest, textStatus){
                            $submitElement.attr("disabled",false);
                            base.options.completeSubmit(XMLHttpRequest, textStatus);
                        }
                    });
                }else{
                    $(this).attr("disabled",false);
                }
                return false;
            });
        };
        base.init();
    };

    $.simpleBlog.asynSubmit.defaultOptions = {
        submitElement:false,
        submitEvent:"click",
        beforeSubmit:function(){return true},
        successSubmit:function(data){},
        errorSubmit:function(XMLHttpRequest, textStatus, errorThrown){},
        completeSubmit:function(XMLHttpRequest, textStatus){}
    };

    $.simpleBlog.jump = function (second,urlstr) {
        window.setTimeout(function(){
            second--;
            if(second > 0) {
                $.simpleBlog.jump(second,urlstr);
            } else {
                location.href=urlstr;
            }
        }, 1000);
    };

    $.simpleBlog.getArticleId = function (){
        var reg = new RegExp(".*/articles/(\\\d+)(?=(?:/|\\\?|\\\.|$)).*","");
        var r = window.location.pathname.match(reg);
        if(r!=null){
            return r[1];
        } else{
            return null;
        }
    };
    
    $.simpleBlog.getPublishId = function (){
        var reg = new RegExp(".*/publish/(\\\d+)(?=(?:/|\\\?|\\\.|$)).*","");
        var r = window.location.pathname.match(reg);
        if(r!=null){
            return r[1];
        } else{
            return null;
        }
    };
    
    $.simpleBlog.getUserName = function (){
        var reg = new RegExp(".*/user/(\\\w+)(?=(?:/|\\\?|\\\.|$)).*","");
        var r = window.location.pathname.match(reg);
        if(r!=null){
            return r[1];
        } else{
            return null;
        }
    };

    $.simpleBlog.htmlEscaptForJson = function (text){
        text = text.replace(/:/g, "&#58;");
        text = text.replace(/"/g, "&quot;");
        text = text.replace(/{/g, "&#123;");
        text = text.replace(/}/gm, "&#125;");
        return text;
    };

    $.simpleBlog.refreshUserNameArea = function(){
        $.ajax({
            url: "/api/login",
            type: "GET",
            dataType: 'json',
            data: {},
            success: function (data) {
                $("#login_register").hide();
                $("#logoutArea").show();
                $("#userDisplayName").attr("href","/user/"+$.trim(data.userName));
                $("#userDisplayName").text(data.displayName);
            },
            error: function() {
                $("#login_register").show();
                $("#logoutArea").hide();
                $("#userDisplayName").attr("href","#");
                $("#userDisplayName").text("");
            }
        });
        // Command.interface?command=Logout
        $("#logout").click(function(){
            $.ajax({
                url: "/api/login",
                type: "DELETE",
                dataType: 'json',
                data: {},
                success: function () {
                    $("#login_register").show();
                    $("#logoutArea").hide();
                    $("#userDisplayName").attr("href","#");
                    $("#userDisplayName").text("");
                },
                error: function() {
                }
            });
        });
    };

    $.fn.asynSubmit = function(options) {
        return this.each(function(){
            (new $.simpleBlog.asynSubmit(this, options));
        });
    };
    
    Date.prototype.Format = function (fmt) { // author: meizz
        var o = {
            "M+": this.getMonth() + 1, // 月份
            "d+": this.getDate(), // 日
            "H+": this.getHours(), // 小时
            "m+": this.getMinutes(), // 分
            "s+": this.getSeconds(), // 秒
            "q+": Math.floor((this.getMonth() + 3) / 3), // 季度
            "S": this.getMilliseconds() // 毫秒
        };
        if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
        for (var k in o)
        	if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
        return fmt;
    }

})(jQuery);
