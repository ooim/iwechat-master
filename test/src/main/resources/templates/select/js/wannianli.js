// JavaScript Document
var item = [
    {
        "title": "1、题目1",
        "item": {
            "a": "a、是",
            "b": "b、否"
        },
        "istrueItemKey": "a"
    },
    {
        "title": "2、题目2",
        "item": {
            "a": "a、是",
            "b": "b、否"
        },
        "istrueItemKey": "b"
    },
    {
        "title": "3、题目3",
        "item": {
            "a": "a、否",
            "b": "b、是"
        },
        "istrueItemKey": "b"
    },
    {
        "title": "4、题目4",
        "item": {
            "a": "a、是",
            "b": "b、否"
        },
        "istrueItemKey": "a"
    },
    {
        "title": "5、题目5",
        "item": {
            "a": "a、否",
            "b": "b、否"
        },
        "istrueItemKey": "a"
    }
]

function slidedata(i){
	var select = item[i].istrueItemKey;
	var tbl = "2";
	var a= select=="a"?tbl:"";
    var b= select=="b"?tbl:"";
	return '<div class="swiper-slide">\n    <div class="wannianli-card">\n        <div class="wannianli-info">\n '+item[i].title+'\n        </div>\n      ' +
		'  <div class="wannianli-other">\n        ' +
		'  <div class="weui-cells weui-cells_radio">\n      ' +
		'  <label class="weui-cell weui-check__label" for="x11'+i+'">\n        ' +
		'  <div class="weui-cell__bd">\n ' +
		' <p>'+item[i].item["a"]+'</p>\n </div>\n ' +
		'<div class="weui-cell__ft">\n ' +
		'<input type="radio" class="weui-check" name="radio1" id="x11'+i+'"/>\n' +
		'<span class="weui-icon-checked'+a+'"></span>\n</div>\n' +
		'</label>\n <label class="weui-cell weui-check__label" for="x12'+i+'">\n\n' +
		'<div class="weui-cell__bd">\n<p>'+item[i].item["b"]+'</p>\n' +
		'</div><div class="weui-cell__ft">' +
		'<input type="radio" name="radio1" class="weui-check" id="x12'+i+'"/><span class="weui-icon-checked'+b+'"></span></div></label></div></div>' +
		'<i class="wannianli-ico wannianli-lt"></i><i class="wannianli-ico wannianli-rt"></i><i class="wannianli-ico wannianli-rb"></i></div></div>';
	}

var mySwiper = new Swiper('.swiper-container', {
    roundLengths : true, 
	initialSlide :0,
	speed:600,
	slidesPerView:"auto",
	centeredSlides : true,
	followFinger : false,
})
today=0;//默认显示今天
pre=2;
next=2;
for(var i=0;i<item.length;i++){
    mySwiper.appendSlide(slidedata(i));
}
$(".weui-check").one("click",function () {
    if($(this). next().hasClass("weui-icon-checked")){
        mySwiper.slideTo(mySwiper.activeIndex+1, 2000, false)
    }if($(this). next().hasClass("weui-icon-checked2")){
        alert("错误")
    }
})
mySwiper.on('slideChangeStart',function(swiper){
	//swiper.params.allowSwipeToPrev = false;
    if (swiper.activeIndex==item.length){
        alert("已经是最后一题了")
    }
	swiper.lockSwipes();

})

mySwiper.on('slideChangeEnd',function(swiper){
    swiper.unlockSwipes();


//swiper.params.allowSwipeToPrev = true;
})
