// JavaScript Document
$(function(){

	////////////////////////////////////////////////////////////////////////
	$(".content_One_shgs_rmgs div").eq(0).show().siblings(".content_One_shgs_rmgs div").hide();
	$(".content_One_shgs_rmgs_tilte span").each(function(index, element) {
        $(this).click(function(){
			changeIco2(index);
		});
    });
	function changeIco2(index){
		if(index == 0){
			$(".content_One_shgs_rmgs_tilteIco").animate({left:'90px'},function(){
				$(".content_One_shgs_rmgs div").eq(index).show().siblings(".content_One_shgs_rmgs div").hide();
			});
		}
		else if(index == 1){
			$(".content_One_shgs_rmgs_tilteIco").animate({left:'280px'},function(){
				$(".content_One_shgs_rmgs div").eq(index).show().siblings(".content_One_shgs_rmgs div").hide();
			});
		}
	}
	/////////////////////////////////////////////////////////////////////////////////////
	var posright = $(".bannerright");
	var posleft = $(".bannerleft");
	var poswidt = $(".bannerDiv ul li").width() + 6;
	var posmove = $(".bannerDiv ul");
	var ii = 0;
	var time2;
	var num = $(".bannerDiv ul li").length - 5;
	posright.click(function(){  
		ii++;
		if(ii > num){
			ii=0;
			pospic(ii);
		}
		else{
			pospic(ii);			
		}
	})
	posleft.click(function(){
		ii--;
		if(ii<0){
			ii=num;
			pospic(ii);
		}
		else{
			pospic(ii);
		}
	})
	function pospic(m){
		var stance =m*poswidt;
		posmove.animate({left:-stance},300);
	}
	
	starttime2();
	function cleartime2(){
		clearInterval(time2);//清除计时器time
	};
	function starttime2(){
		time2 = setInterval(function(){//给time赋计时器功能
			ii++;
			if(ii > num){
				ii = 0;	
			}
			pospic(ii);
		},2000);
	};
	$(".content_Two_Text_banner").hover(function(){
		cleartime2();
	},function(){
		starttime2();
	});
	
	
	
	
	///////////////////////////图片切换/////////////////////////////////////////////////
	$(".content_One_photo .content_One_photoA").eq(0).show();
	$(".content_One_photo p").eq(0).show();
	$(".content_One_photo div span").eq(0).css("background-color","red");
	var time;
	var i = 0;
	$(".content_One_photo div span").each(function(index, element) {
        $(this).click(function(){
			ChangeImg(index);
			i = index;
		});
    });
	
	function ChangeImg(index){
		$(".content_One_photo .content_One_photoA").eq(index).show().siblings(".content_One_photo .content_One_photoA").hide();
		$(".content_One_photo p").eq(index).show().siblings(".content_One_photo p").hide();
		$(".content_One_photo div span").eq(index).css("background-color","red").siblings(".content_One_photo div span").css("background-color","");
	}
	
	
	starttime();
	function cleartime(){
		clearInterval(time);//清除计时器time
	};
	function starttime(){
		time = setInterval(function(){//给time赋计时器功能
			i++;
			if(i > 4){
				i = 0;	
			}
			ChangeImg(i);
		},4000);
	};
	$(".content_One_photo").hover(function(){
		cleartime();
	},function(){
		starttime();
	});
	
})