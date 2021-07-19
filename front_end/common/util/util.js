/**
 * Created by wangxin on 2018/6/12.
 */
import Vue from 'vue'
/**
 * json拼接为字符串
 * @param json
 * @returns {string}
 */
export function qs(json) {
	let str = ''
	for (let key in json) {
		if (json.hasOwnProperty(key)) {
			str += key + '=' + json[key] + '&'
		}
	}
	str = str.substr(0, str.lastIndexOf('&'))
	return str
}

/**
 * 生成指定长度的随机字符
 * @param length
 * @returns {string}
 */
export function genRandomStr(length) {
	let arr = ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
		'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F',
		'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'
	]
	let str = ''
	for (let i = 0; i < length; i++) {
		str += arr[parseInt(Math.random() * 36)]
	}
	return str
}

/**
 * 判断字符串是否为手机号
 * @param phone
 * @return {bool}
 */
export function isPhoneNo(phone) {
	var reg = /^1\d{10}$/
	var flag = reg.test(phone)
	return flag
}

/**
 * 计算日期差
 * @param start
 * @param end
 * @returns {*}
 */
export function getDateDiff(start, end) {
	if (!end) {
		return '借阅中'
	}
	let startTime = new Date(start)
	let endTime = new Date(end)
	let time = endTime - startTime
	let seconds = time / (1000 * 60)
	let minutes
	let hours
	let resultDate = `${seconds}分钟`
	if (seconds >= 60) {
		minutes = parseInt(seconds / 60)
		seconds = seconds % 60
		resultDate = `${minutes}小时${seconds}分钟`
	}
	if (minutes >= 60) {
		hours = parseInt(minutes / 60)
		minutes = minutes % 60
		resultDate = `${hours}天${minutes}小时`
	}
	return resultDate
}

/**
 * 格式化数值为","形式
 * @param number
 * @returns {string}
 */
export function formatNumber(number) {
	return String(number).replace(/(\d)(?=(\d{3})+$)/g, '$1,')
}

/**
 * 在指定闭区间生成一个随机数
 * @param rangeL
 * @param rangeR
 */
export function genRandom(rangeL, rangeR) {
	return parseInt(Math.random() * (rangeR - rangeL + 1) + rangeL)
}

/**
 * 将对象转换成数组(类似于map结构)
 * @param obj
 * @returns {Array}
 * @constructor
 */
export function obj2Array(obj) {
	let arr = []
	for (let key in obj) {
		if (obj.hasOwnProperty(key)) {
			let newObj = {}
			newObj.key = key
			newObj.value = obj[key]
			arr.push(newObj)
		}
	}
	return arr
}

/**
 * 时间格式化
 * @param date 时间对象
 * @param fmt 格式：'yyyy-MM-dd hh:mm:ss'
 * @returns {*} 格式化后的时间
 */
export function formatDate(date, fmt) {
	var o = {
		'M+': date.getMonth() + 1, // 月份
		'd+': date.getDate(), // 日
		'h+': date.getHours(), // 小时
		'm+': date.getMinutes(), // 分
		's+': date.getSeconds(), // 秒
		'q+': Math.floor((date.getMonth() + 3) / 3), // 季度
		'S': date.getMilliseconds() // 毫秒
	}
	if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (date.getFullYear() + '').substr(4 - RegExp.$1.length))
	for (var k in o) {
		if (new RegExp('(' + k + ')').test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length === 1) ? (o[k]) : ((
			'00' + o[k]).substr(('' + o[k]).length)))
	}
	return fmt
}

/**
 * 去掉字符串首尾的空格
 * @param str
 */
export function trim(str) {
	return str.replace(/(^\s*)|(\s*$)/g, '')
}

/**
 * 获取字符串的长度
 * @param str
 * @returns {number}
 */
export function getLength(str) {
	let len = 0
	for (let i = 0; i < str.length; i++) {
		if (str.charCodeAt(i) > 127 || str.charCodeAt(i) === 94) {
			len += 2
		} else {
			len += 1
		}
	}
	return len
}

/**
 * 价格校验
 * @param price
 * @returns {Number}  如果是小数，只保留两位小数
 */
export function getPrice(price) {
	if (typeof price !== 'number') {
		return
	}
	if (price <= 0) {
		return
	}
	price += ''
	let intPart = ''
	let decPart = ''
	if (price.indexOf('.') !== -1) {
		intPart = price.substr(0, price.indexOf('.'))
		decPart = price.substr(price.indexOf('.') + 1, 2)
		return parseFloat(intPart + '.' + decPart)
	} else {
		return parseFloat(price)
	}
}

/**
 *  正则判断是字符串是否位数字
 *  @param string
 *  @returns {bool}
 */
export function isNumber(value) {
	var patrn = /^(-)?\d+(\.\d+)?$/
	if (patrn.exec(value) == null || value === '') {
		return false
	} else {
		return true
	}
}

/**
 * 个性化自定义打印
 */
export function customPrint(value) {
	if (Vue.prototype.$printEnable) {
		console.log(value);
	}
}

/**
 * 格式化请求url
 * 将其转换为对象
 * 从而直接取各个参数的值
 */
export function confirmUrl(url) {
	var theRequest = {};
	if (url.indexOf("?") != -1) {
		var str = url.substring(url.indexOf("?") + 1);
		// var str = str.substr(1); 
		var strs = str.split("&");
		for (var i = 0; i < strs.length; i++) {
			theRequest[strs[i].split("=")[0]] = unescape(strs[i].split("=")[1]);
		}
	}
	return theRequest;
}

/* 
 * 获取正在编写或者需要查看的日记，替代url传参
 */

import {
	Diary
} from '../Diary.js'
export function getWritingDiary() {
	var diary = new Diary();
	let t = uni.getStorageSync('writingDiary');
	// Object.assign(diary, JSON.parse(t));
	Object.assign(diary, t);
	return diary;
}

/* 
 * 存入正在编写或者需要查看的日记，替代url传参
 */
export function storageWritingDiary(diary) {
	// uni.setStorageSync('writingDiary',JSON.stringify(diary));
	uni.setStorageSync('writingDiary', diary);
}

/* 
 * 获取get方法头
 */
export function getGetHead() {
	var head = {
		'Authorization': getToken()
	}
	return head;
}

/* 
 * 获取post方法头
 */
export function getPostHead() {
	var head = {
		'Authorization': getToken(),
		'content-type': 'application/x-www-form-urlencoded'
	}
	return head;
}

/* 
 * 获取token
 */
export function getToken() {
	return uni.getStorageSync('token');
}

/* 
 * 拼接Url
 */
export function getUrl(path) {
	var baseUrl = 'https://zk123.top/mye'
	return baseUrl + path
}

/*
 * 检验日期是否是同一天
 */
export function compareDate(date1, date2) {
	var oldDate = new Date();
	Object.assign(oldDate, date1);
	date1 = oldDate;
	return date1.getFullYear() == date2.getFullYear() && date1.getMonth() == date2.getMonth() && date1.getDate() ==
		date2.getDate()
}


/* 
 * 获取本地日记列表
 */
import {
	DiaryList
} from '../DiaryList.js'
export function getDiaryList() {
	var diaryList = uni.getStorageSync('diaryList');
	if (diaryList == '') {
		diaryList = new DiaryList();
	}
	for (let i in diaryList.list) {
		var item = diaryList.list[i];
		var diary = new Diary();
		Object.assign(diary, item);
		diaryList.list[i] = diary;
	}
	return diaryList;
}

export function storageDiaryList(diaryList) {
	uni.setStorageSync('diaryList', diaryList);
}

/* 
 * 格式化后端的日记列表数据(没有日记列表的maxId)
 */
export function getDiaryListFromBack(diaryList) {
	var list = [];
	for (let index in diaryList) {
		let item = diaryList[index];
		list.push(getDiaryFromBack(item));
	}
	return list;
}

/* 
 * 格式化后端日记项目
 */
export function getDiaryFromBack(item) {
	/* item = {
		"emotions": "[{\"mood\":\"吃瓜\",\"icon\":\"\"}]",
		"uer_id": 10028,
		"activity": "[{\"id\":8,\"activity\":\"爱情\",\"icon\":\"love\"}]",
		"background": "https://www.zk123.top/src/mye/diary/2021-05-05/699f6df7-182a-454f-967a-0370f85c7d12.png",
		"weather": "{\"icon\":\"cloudy\",\"weather\":\"多云\"}",
		"time": "2021-1-1",
		"title": "\"123123312123\"",
		"favorite": false,
		"content": "{\"maxId\":2,\"list\":[{\"itemId\":\"dragBox0\",\"posX\":0,\"posY\":36,\"width\":200,\"height\":200,\"valueSync\":\"心想的事儿都能成12312123123\",\"isShowBorder\":false},{\"itemId\":\"dragBox1\",\"posX\":197,\"posY\":158,\"width\":200,\"height\":200,\"valueSync\":\"das\",\"isShowBorder\":true}]}",
		"diary_id": 19
	} */
	var diary = new Diary();
	diary.diaryId = item.diary_id;
	diary.mood = JSON.parse(item.emotions);
	diary.cover = item.background;
	diary.activities = JSON.parse(item.activity);
	diary.weather = JSON.parse(item.weather);
	var dateF = item.time.split('-');
	// diary.date = JSON.parse(item.time);
	diary.date.year = dateF[0];
	diary.date.month = dateF[1];
	diary.date.day = dateF[2];
	diary.title = item.title;
	diary.isLike = item.favorite;
	diary.content = JSON.parse(item.content);
	diary.isCompete = true;
	return diary;
}

/* 
 * 刷新日记在本地日记表中的状态
 */

export function refreshDiary(diary) {
	var diaryList = getDiaryList();
	let list = diaryList.list;
	for (let i in list) {
		if (list[i].id == diary.id) {
			list[i] = diary;
			if (diaryList.list.length <= 0) {
				var isTodayWrited = uni.getStorageSync('isTodayWrited');
				isTodayWrited.isTodayWrited = true;
			}
			storageDiaryList(diaryList);
			uni.setStorageSync('isNeedRefresh', true);
			return;
		}
	}
	console.log('日记不存在');
}


/* 
 * 收藏日记 
 */
export function likeDiary(diary) {
	diary.isLike = !diary.isLike;
	uni.showLoading({
		title: "请稍等"
	})
	refreshDiary(diary);
	uni.request({
		url: getUrl('/diary/favorite'),
		method: "POST",
		header: getPostHead(),
		data: {
			diaryId: diary.diaryId
		},
		success(res) {
			console.log(res);
			uni.hideLoading();
		}
	})
}

/* 
 * 删除日记
 */
export function deleteDiary(diary) {
	// 删除后需要更改
	uni.showLoading({
		title: "删除中"
	})
	var diaryList = getDiaryList();
	let list = diaryList.list;
	for (let i in list) {
		if (list[i].id == diary.id) {
			list.splice(i, 1);
			storageDiaryList(diaryList);
			uni.request({
				url: getUrl('/diary/delete'),
				method: 'POST',
				header: getPostHead(),
				data: {
					diaryId: diary.diaryId
				},
				success(res) {
					console.log(res);
					// #ifdef APP-PLUS
					plus.nativeUI.toast(res.data.msg);
					// #endif
					// #ifndef APP-PLUS
					uni.showToast({
						title: 'res.data.msg',
						position: true,
						icon: 'none',
						success() {
							// console.log(res)
						}
					});
					// #endif
					uni.reLaunch({
						url: '../index/index'
					});
					uni.hideLoading();
				}
			})
			return;
		}
	}
	console.log('日记不存在');
	uni.hideLoading();
}

/* 
 * 获得草稿本
 * draftList:[{new Diary()}]
 */
export function getDraftList() {
	var draftList = uni.getStorageSync('draftList');
	if (draftList == '') {
		draftList = new DiaryList();
	} else {
		for (let i in draftList.list) {
			var item = draftList.list[i];
			var diary = new Diary();
			Object.assign(diary, item);
			draftList.list[i] = diary;
		}
	}
	return draftList;
}

/* 
 * 存入/刷新草稿本
 */
export function storageDraftItem(draftItem) {
	var draftList = getDraftList();
	for (let i in draftList.list) {
		let item = draftList.list[i];
		if (item.id == draftItem.id) {
			item = draftItem;
			uni.setStorageSync('draftList', draftList);
			return;
		}
	}
	// 这是新的草稿
	draftItem.id = draftItem.maxId++;
	draftList.list.push(draftItem);
	uni.setStorageSync('draftList', draftList);
}


/* 
 * 删除草稿本项目
 */
export function deleteDraftItem(draftItem) {
	var draftList = getDraftList();
	for (let i in draftList.list) {
		let item = draftList.list[i];
		if (item.id == draftItem.id) {
			draftList.list.splice(i, 1);
			uni.setStorageSync('draftList', draftList);
			return;
		}
	}
	console.log("删除项目不存在")
}
