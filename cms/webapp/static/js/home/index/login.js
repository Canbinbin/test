/*
   登录相关，采用ajax
*/
//json定义可能的错误信息
var errorMsg = {
    "userNull": "用户名不能为空!",
    "passNull": "密码不能为空!",
    "isExist": "用户名或密码错误!",
}
//前台退出登录
function LogOut() {
    var temp = document.createElement("form");
    temp.action = "/Account/LogOff";
    temp.method = "post";
    temp.style.display = "none";
    document.body.appendChild(temp);
    temp.submit();
    return temp;
}
function LogIn() {
    //获取表单基本信息
    var username = $('#txt_user').val();
    var password = $('#txt_pass').val();
    var params = { username: username, password: password };
    //验证非空
    if ((username == null) || (username == "")) {
        //$('#error').text(errorMsg.userNull);
        alert(errorMsg.userNull);
        return false;
    }
    else if ((password == null) || (password == "")) {
        //$('#error').text(errorMsg.passNull);
        alert(errorMsg.passNull);
        return false;
    }
        //通过ajax发送数据，服务器在进行验证
    else {
        $.ajax({
            url: "/Ajax/Login.ashx",                             //控制器名称
            type: "POST",                                            //发送方式
            dataType: "json",                                        //返回数据类型
            data: params,                                              //发送的数据
            success: function (result) {                             //成功时执行逻辑
                if (result == 0) {//超级管理员
                    window.location.href = "../Manage/SuperAdmin";
                }
                else if (result == 1) {//管理员
                    window.location.href = "../Manage/Admin";
                }
                else if (result == 2) {//普通用户
                    window.location.href = "../Manage/Users";
                }
                else {//登录失败
                    //$('#error').text(errorMsg.isExist);
                    alert(errorMsg.isExist);
                }
            }
        })
    }

    //进入后台管理页面操作
    //根据不同身份进入不同的后台管理页面
    $('#admin').click(function () {
        $('#manage').submit();
    })

    //退出操作
    $("#logout").click(function () {
        $("#logged").hide();
        //调用后台方法清除Session和浏览器缓存
        $('#logoutForm').submit();
        $("#login").show();
    });
}
$(function () {
    //初始化页面时判断用户是否已经登录
    $.ajax({
        url: "/Ajax/CheckLogin.ashx",
        dataType: "json",
        success: function (json) {
            if (json.isCheck == 1) {
                $('#loginDiv').hide();
                $('#loggedDiv').show();
                $("#loggerUserName").attr("href","../Manage/" + json.userType);
                $("#loggerUserName").text(json.userName);
            }
            else {
                $('#loggedDiv').hide();
                $('#loginDiv').show();
            }
        }
    })

    //document.getElementById("txt_user").focus();//自动聚焦到用户名输入框
    $('#txt_pass').bind('keypress', function (event) {//回车登录
        if (event.keyCode == "13") {
            LogIn();
        }
    });
    $("#log").click(function () { LogIn(); });//手动点击按钮登录
})