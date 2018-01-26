var interval = 1000;
function ShowCountDown(year, month, day, divname) {
	var cc = document.getElementById(divname);
	if(year != 1 && year != 2 && year != 3){
		var now = new Date();
		var endDate = new Date(year, month - 1, day);
		var leftTime = endDate.getTime() - now.getTime();
		var leftsecond = parseInt(leftTime / 1000);
		// var day1=parseInt(leftsecond/(24*60*60*6));
		var day1 = Math.floor(leftsecond / (60 * 60 * 24));
		var hour = Math.floor((leftsecond - day1 * 24 * 60 * 60) / 3600);
		var minute = Math
				.floor((leftsecond - day1 * 24 * 60 * 60 - hour * 3600) / 60);
		var second = Math.floor(leftsecond - day1 * 24 * 60 * 60 - hour * 3600
				- minute * 60);
		if(second < 10){
			second = '0' + second;
		}
		cc.innerHTML = day1 + " 天 " + hour + " 小时 " + minute + " 分 " + second + " 秒";
	}else{
		if(year == 1){
			cc.innerHTML = "高考进行中（第一天）...";
		}else if(year == 2){
			cc.innerHTML = "高考进行中（第二天）...";
		}else if(year == 3){
			cc.innerHTML = "高考进行中（第三天）...";
		}
	}
}
window.setInterval(function() {
	var gaoKaoYear = document.getElementById('gaoKaoYear');
	ShowCountDown(gaoKaoYear.value, 6, 7, 'gaoKaoTime');
}, interval);