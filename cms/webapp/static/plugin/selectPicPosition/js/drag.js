/*-------------------------- +
  拖拽函数
 +-------------------------- */
function drag(oDrag, handle)
{
	var disX = dixY = 0;
	var oMin = get.byClass("min", oDrag)[0];
	var oMax = get.byClass("max", oDrag)[0];
	var oRevert = get.byClass("revert", oDrag)[0];
	var oClose = get.byClass("close", oDrag)[0];
	var contentText = get.byClass("content",oDrag)[0];
	handle = handle || oDrag;
	handle.style.cursor = "move";
	handle.onmousedown = function (event)
	{
		var event = event || window.event;
		disX = event.clientX - oDrag.offsetLeft;
		disY = event.clientY - oDrag.offsetTop;
		
		document.onmousemove = function (event)
		{
			var event = event || window.event;
			var iL = event.clientX - disX;
			var iT = event.clientY - disY;
			var maxL = document.documentElement.clientWidth - oDrag.offsetWidth;
			var maxT = document.documentElement.clientHeight - oDrag.offsetHeight;
			
			/*iL <= 0 && (iL = 0);
			iT <= 0 && (iT = 0);
			iL >= maxL && (iL = maxL);
			iT >= maxT && (iT = maxT);*/
			
			oDrag.style.left = iL + "px";
			oDrag.style.top = iT + "px";
			//更新位置信息
			div_position_start_X = (oDrag.offsetLeft-news_pic_offset_left)/news_pic_client_width;
			div_position_start_Y = (oDrag.offsetTop-news_pic_offset_top)/news_pic_client_height;
			div_position_end_X = (oDrag.clientWidth)/news_pic_client_width + div_position_start_X ;
			div_position_end_Y = (oDrag.clientHeight)/news_pic_client_height + div_position_start_Y;
			setValAndRe(div_position_start_X,div_position_start_Y,div_position_end_X,div_position_end_Y,oDrag);
			/*//更新位置信息
			var contentText = get.byClass("content",oDrag)[0];
			div_position_top = (oDrag.offsetTop-news_pic_offset_top)/news_pic_client_height*100;
			div_position_left = (oDrag.offsetLeft-news_pic_offset_left)/news_pic_client_width*100;
			div_client_height = (oDrag.clientHeight)/news_pic_client_height*100;
			div_client_width = (oDrag.clientWidth)/news_pic_client_width*100;
			contentText.innerHTML="起始位置(X);"+div_position_left.toFixed(3)+"%<br>起始位置(Y)："+div_position_top.toFixed(3)+"%<br>结束位置(X)："+(div_position_left+div_client_width).toFixed(3)+"%<br>结束位置(Y)："+(div_client_height+div_position_top).toFixed(3)+"%</div>";
			*/return false
		};
		
		document.onmouseup = function ()
		{
			document.onmousemove = null;
			document.onmouseup = null;
			this.releaseCapture && this.releaseCapture()
		};
		this.setCapture && this.setCapture();
		
	
		return false
	};	
	//完善区域指向信息
	/*oRevert.onclick = function ()
	{		
		cover = get.byClass("cover", document.body)[0];
		cover.style.display = "block";
		document.body.style.overflow = "hidden";
		replenishFloatDiv.show(oDrag)
		
	};*/
	
	/*//最大化按钮
	oMax.onclick = function ()
	{
		oDrag.style.top = oDrag.style.left = 0;
		oDrag.style.width = document.documentElement.clientWidth - 2 + "px";
		oDrag.style.height = document.documentElement.clientHeight - 2 + "px";
		this.style.display = "none";
		oRevert.style.display = "block";
	};
	//还原按钮
	oRevert.onclick = function ()
	{		
		oDrag.style.width = dragMinWidth + "px";
		oDrag.style.height = dragMinHeight + "px";
		oDrag.style.left = (document.documentElement.clientWidth - oDrag.offsetWidth) / 2 + "px";
		oDrag.style.top = (document.documentElement.clientHeight - oDrag.offsetHeight) / 2 + "px";
		this.style.display = "none";
		oMax.style.display = "block";
	};*/
	//最小化按钮
	/*oClose.onclick = function ()
	{
		document.body.removeChild(oDrag);
	};*/
	/*//阻止冒泡
	oMin.onmousedown = oMax.onmousedown = oClose.onmousedown = function (event)
	{
		this.onfocus = function () {this.blur()};
		(event || window.event).cancelBubble = true	
	};*/
}
/*-------------------------- +
  改变大小函数
 +-------------------------- */
function resize(oParent, handle, isLeft, isTop, lockX, lockY)
{
	handle.onmousedown = function (event)
	{
		var event = event || window.event;
		var disX = event.clientX - handle.offsetLeft;
		var disY = event.clientY - handle.offsetTop;	
		var iParentTop = oParent.offsetTop;
		var iParentLeft = oParent.offsetLeft;
		var iParentWidth = oParent.offsetWidth;
		var iParentHeight = oParent.offsetHeight;
		
		document.onmousemove = function (event)
		{
			var event = event || window.event;
			
			var iL = event.clientX - disX;
			var iT = event.clientY - disY;
			var maxW = document.documentElement.clientWidth - oParent.offsetLeft - 2;
			var maxH = document.documentElement.clientHeight - oParent.offsetTop - 2;			
			var iW = isLeft ? iParentWidth - iL : handle.offsetWidth + iL;
			var iH = isTop ? iParentHeight - iT : handle.offsetHeight + iT;
			
			isLeft && (oParent.style.left = iParentLeft + iL + "px");
			isTop && (oParent.style.top = iParentTop + iT + "px");
			
			iW < dragMinWidth && (iW = dragMinWidth);
			//iW > maxW && (iW = maxW);
			lockX || (oParent.style.width = iW + "px");
			
			iH < dragMinHeight && (iH = dragMinHeight);
			//iH > maxH && (iH = maxH);
			lockY || (oParent.style.height = iH + "px");
			
			if((isLeft && iW == dragMinWidth) || (isTop && iH == dragMinHeight)) document.onmousemove = null;
			//更新位置信息
			div_position_start_X = (oParent.offsetLeft-news_pic_offset_left)/news_pic_client_width;
			div_position_start_Y = (oParent.offsetTop-news_pic_offset_top)/news_pic_client_height;
			div_position_end_X = (oParent.clientWidth)/news_pic_client_width + div_position_start_X ;
			div_position_end_Y = (oParent.clientHeight)/news_pic_client_height + div_position_start_Y;
			setValAndRe(div_position_start_X,div_position_start_Y,div_position_end_X,div_position_end_Y,oParent);
			return false;	
			
		};
		document.onmouseup = function ()
		{
			document.onmousemove = null;
			document.onmouseup = null;
		};
		return false;
	}
};
/**
 * 调用父页面的方法更新父页面信息
 */
function updatePpagePositionData()
{
	if(div_position_start_X>1||div_position_start_X<0)
		window.parent.showMsg("起始X坐标越界",2000);     
	else if(div_position_start_Y>1||div_position_start_Y<0)
		window.parent.showMsg("起始Y坐标越界",2000);
	else if(div_position_end_X>1||div_position_end_X<0)
		window.parent.showMsg("结束X坐标越界",2000);
	else if(div_position_end_Y>1||div_position_end_Y<0)
		window.parent.showMsg("结束Y坐标越界",2000);
	else
	{
		window.parent.setPosition(""+div_position_start_X.toFixed(3)+","+div_position_start_Y.toFixed(3)+"|"+div_position_end_X.toFixed(3)+","+div_position_end_Y.toFixed(3))
		window.parent.hideSPosition();
	}
}
/**
 * 刷新div信息
 */
function setValAndRe(div_position_start_X,div_position_start_Y,div_position_end_X,div_position_end_Y,oParent)
{
	//向隐藏域赋值
	document.getElementById("div_position_start_X").value=div_position_start_X;
	document.getElementById("div_position_start_Y").value=div_position_start_Y;
	document.getElementById("div_position_end_X").value=div_position_end_X;
	document.getElementById("div_position_end_Y").value=div_position_end_Y;
	//更新div信息
	var contentText = get.byClass("content",oParent)[0];
	h = "期数：" + "第"+document.getElementById("issue").value+"期<br>";
	h+= "版位："+ "第"+document.getElementById("pageNum").value+"版<br>";
	h+= "起始位置(X)："+(div_position_start_X*100).toFixed(3) + "%<br>";
	h+= "起始位置(Y)："+(div_position_start_Y*100).toFixed(3) + "%<br>";
	h+= "结束位置(X)："+(div_position_end_X*100).toFixed(3) + "%<br>";
	h+= "结束位置(Y)："+(div_position_end_Y*100).toFixed(3) + "%<br>";
	contentText.innerHTML=h;
}
/**
*	添加新的悬浮div
*/
function addNewDiv(count){
		var active_box = document.createElement("div");
  //active_box.id = "active_box";
  //active_box.innerHTML = '<span class="close-buton" onclick="removeCurrentElement(this)"></span><label class="label-left"/>';
active_box.innerHTML ='<div class="title"><h2>区域选择器</h2>'
   +'<div>'
   +'<a class="save" href="javascript:updatePpagePositionData();" title="保存"></a>保存'
   +'</div></div><div class="resizeL"></div>'
   +'<div class="resizeT"></div>'
   +'<div class="resizeR"></div>'
   +'<div class="resizeB"></div>'
   +'<div class="resizeLT"></div>'
   +'<div class="resizeTR"></div>'
   +'<div class="resizeBR"></div>'
   +'<div class="resizeLB"></div>'
   +'<div class="content">期数：第'+document.getElementById("issue").value+'期<br>版位：第'+document.getElementById("pageNum").value+'版<br>起始位置(X):-1<br>起始位置(Y)：-1<br>结束位置(X)：-1<br>结束位置(Y)：-1<br></div>';
   active_box.className = "drag";
   active_box.style.background = "rgb(102, 255, 255)";
   document.body.appendChild(active_box);

	var oTitle = get.byClass("title", active_box)[0];
	var oL = get.byClass("resizeL", active_box)[0];
	var oT = get.byClass("resizeT", active_box)[0];
	var oR = get.byClass("resizeR", active_box)[0];
	var oB = get.byClass("resizeB", active_box)[0];
	var oLT = get.byClass("resizeLT", active_box)[0];
	var oTR = get.byClass("resizeTR", active_box)[0];
	var oBR = get.byClass("resizeBR", active_box)[0];
	var oLB = get.byClass("resizeLB", active_box)[0];
	
	drag(active_box, oTitle);
	//四角
	resize(active_box, oLT, true, true, false, false);
	resize(active_box, oTR, false, true, false, false);
	resize(active_box, oBR, false, false, false, false);
	resize(active_box, oLB, true, false, false, false);
	//四边
	resize(active_box, oL, true, false, false, true);
	resize(active_box, oT, false, true, true, false);
	resize(active_box, oR, false, false, false, true);
	resize(active_box, oB, false, false, true, false);
	active_box = null;
	}
	/**
	* 获取随机颜色
	*/
	function getRandomColor()
	{
		colorArray = ['#00FF99','#00CCFF','#006699','#339999','#666666','#6699FF','#66FFFF','#99CCFF','#CCCC66','#CCCCFF','#FF9966','#FFCC66','#FFFF66'];
		return colorArray[Math.floor(Math.random()*13)];
	}
	