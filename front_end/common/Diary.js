export class Diary {
	constructor(arg) {
		let a = new Date();
		this.id = 0;
		this.diaryId = -1;
		this.date = {
			year: a.getFullYear(),
			month: a.getMonth() + 1,
			day: a.getDate()
		};
		this.isCompete = false;
		this.isLike = false;
		this.mood = [{
			mood: '迷惘',
			icon: 'confused'
		}];
		this.weather = {
			icon: 'sunny',
			weather: '下雪'
		};
		/* this.cover =
			'https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3260214634,2926460972&fm=26&gp=0.jpg'; */
		this.cover = null;
		this.title = '今天是个好日子';
		this.content = {
			maxId: 1,
			list: [{
				itemId: "dragBox" + 0, // dragBoxList.list的长度-1
				posX: 197,
				posY: 158,
				width: 200, // rpx
				height: 200, // rpx
				valueSync: '心想的事儿都能成',
				isShowBorder: 'false'
			}]
		};
		this.activities = [{
			id: 0,
			activity: "游戏",
			icon: "game"
		}];
	}

	setTodyDate() {
		let a = new Date()
		this.date = {
			year: a.getFullYear(),
			month: a.getMonth(),
			day: a.getDate()
		}
	}

	setWeather(weatherChose) {
		this.weather = {
			icon: weatherChose.icon.slice(0, -2),
			weather: weatherChose.weather
		}
	}

	setMood(moodChose) {
		/* mood: '迷惘',
			icon: 'confused',
			i: 1,
			j: 1 */
		if (this.mood === null)
			this.mood = [];
		for (let i = 0; i < moodChose.length; i++) {
			this.mood[i] = {
				mood: moodChose[i].mood,
				icon: moodChose[i].icon
			};
		}
	}

	getMoodIcon(icon) {
		if (icon == '') {
			icon = "confused"
		}
		return '/static/moodIcon/' + icon + '.png';
	}

	getDate() {
		return this.date.year + '年' + this.date.month + '月' + this.date.day + '日';
	}

	setActivities(activitiesChose) {
		if (this.activities === null)
			this.activities = [];
		for (let i = 0; i < activitiesChose.length; i++) {
			this.activities[i] = {
				id: activitiesChose[i].id,
				activity: activitiesChose[i].activity,
				icon: activitiesChose[i].icon[0]
			};
		}
	}

	setContent(dragBoxList) {
		this.content = dragBoxList;
	}

	getContent() {
		return this.content;
	}

	setTitle(title) {
		this.title = title;
	}

	setCover(cover) {
		this.cover = cover;
	}

	getMoodStr() {
		var str = '';
		var flag = true;
		this.mood.filter(function(item) {
			if (!flag) {
				str += '/';
			}
			flag = false;
			str += item.mood;
		})
		return str;
	}

	getActivityStr() {
		var str = '';
		var flag = true;
		this.activities.filter(function(item) {
			if (!flag) {
				str += '/';
			}
			flag = false;
			str += item.activity;
		})
		return str;
	}

	getTitle() {
		return this.title;
	}
}
