(function(){
    $(function(){

            //初始化菜单及点击菜单事件
            Common.util.switchNav($(".nav_ul").find("li").eq(0));
            Common.util.switchMenu($(".menu").eq(0).find("li").eq(0));
            $(".nav_ul").find("li").click(function(){
                var index=$(this).index();
                Common.util.switchNav($(this));
                Common.util.switchMenu($(".menu").eq(index).find("li").eq(0));
            });
            $(".menu").find("li").click(function(){
                Common.util.switchMenu($(this));
            });

            //自适应iframe的高度
            function setHeight(){
                    var h=$(window).height();
                    var top_h=$("#top").height();
                    $("#my_iframe").css('height',h-top_h-15);
                    $("#left").css('height',h-top_h-15);
                    $("#open_close").css('height',h-top_h-15);
            };
            setHeight();
            $(window).resize(function() {
                    setHeight();
            });

            //左侧显示切换
            $(".toggle").click(function(){
               if($(this).attr('class')=='toggle'){
                    $("#left").hide();
                    $("#right").css('margin-left',15);
                    $(this).addClass("open_show");
               }else{
                    $("#left").show();
                    $("#right").css('margin-left',175);
                    $(this).removeClass("open_show");
               }
            });
       
    });
    
})()
   

