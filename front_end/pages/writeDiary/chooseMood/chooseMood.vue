<template>
	<view>
		<view class="tips">
			<view>
				那么 {{name}}
			</view>
			<view>
				这一天的心情是怎么样的呢
			</view>
		</view>
		<view class="moodPic">
			<image :src="baseUrl + this.moodIcon + `.png`" mode="heightFix"></image>
		</view>
		<view class="mood">
			<swiper class="swiper" indicator-dots=true>
				<swiper-item v-for="i in 2" :key=i>
					<view class="moodItem" v-for="j in 2" :key=j>
						<view v-for="(item) in getItem(i,j)" :key=item.mood
							:style="{'color': item.fontColor, 'background-color': item.viewColor}"
							@tap="chooseMood(item)">{{item.mood}}</view>
					</view>
				</swiper-item>
			</swiper>
			<button @tap="chooserAct()"
				:style="{'width': 'fit-content', 'background-color': buttonColor, 'color': fontColor, 'border-radius': '100rpx', 'padding': '10rpx 60rpx', 'font-size': '35rpx', 'margin-top' : '100rpx'}">我的心情是这样的</button>
		</view>
	</view>
</template>

<script>
	import {getWritingDiary, storageWritingDiary} from '../../../common/util/util.js'
	import {
		Diary
	} from '../../../common/Diary.js'
	export default {
		data() {
			return {
				baseUrl: '../../../static/moodIcon/',
				moodIcon: 'confused',
				imgBaseUrl: '',
				weather: '',
				name: 'zcx',
				moodList: [
					[
						[{
							"mood": "开心",
							"icon": "happy",
							"id": 0,
							"fontColor": "#09181F",
							"viewColor": "white"
						}, {
							"mood": "感动",
							"icon": "moved",
							"id": 1,
							"fontColor": "#09181F",
							"viewColor": "white"
						}, {
							"mood": "吃瓜",
							"icon": "chigua",
							"id": 2,
							"fontColor": "#09181F",
							"viewColor": "white"
						}],
						[{
							"mood": "委屈",
							"icon": "sad",
							"id": 3,
							"fontColor": "#09181F",
							"viewColor": "white"
						}, {
							"mood": "烦燥",
							"icon": "angry",
							"id": 4,
							"fontColor": "#09181F",
							"viewColor": "white"
						}, {
							"mood": "迷惘",
							"icon": "confused",
							"id": 5,
							"fontColor": "#09181F",
							"viewColor": "white"
						}]
					],
					[
						[{
							"mood": "得意",
							"icon": "deyi",
							"id": 6,
							"fontColor": "#09181F",
							"viewColor": "white"
						}, {
							"mood": "甜蜜",
							"icon": "sweet",
							"id": 7,
							"fontColor": "#09181F",
							"viewColor": "white"
						}, {
							"mood": "惊喜",
							"icon": "surprise",
							"id": 8,
							"fontColor": "#09181F",
							"viewColor": "white"
						}],
						[{
							"mood": "充实",
							"icon": "chongshi",
							"id": 9,
							"fontColor": "#09181F",
							"viewColor": "white"
						}, {
							"mood": "难过",
							"icon": "nanguo",
							"id": 10,
							"fontColor": "#09181F",
							"viewColor": "white"
						}, {
							"mood": "不知道",
							"icon": "unknow",
							"id": 11,
							"fontColor": "#09181F",
							"viewColor": "white"
						}]
					]/* ,
					[
						[{
							"mood": "调皮",
							"icon": "tiaopi",
							"id": 12,
							"fontColor": "#09181F",
							"viewColor": "white"
						}, {
							"mood": "尴尬",
							"icon": "ganga",
							"id": 13,
							"fontColor": "#09181F",
							"viewColor": "white"
						}, {
							"mood": "开心3",
							"icon": "",
							"id": 14,
							"fontColor": "#09181F",
							"viewColor": "white"
						}],
						[{
							"mood": "开心4",
							"icon": "",
							"id": 15,
							"fontColor": "#09181F",
							"viewColor": "white"
						}, {
							"mood": "开心5",
							"icon": "",
							"id": 16,
							"fontColor": "#09181F",
							"viewColor": "white"
						}, {
							"mood": "开心6",
							"icon": "",
							"id": 17,
							"fontColor": "#09181F",
							"viewColor": "white"
						}] 
					]*/
				],
				moodIndex: 0,
				mood: [],
				buttonColor: '#F0F0F0',
				fontColor: '#787878',
				diary: new Diary()
			}
		},
		onLoad(e) {
			console.log(e);
			this.diary = getWritingDiary();
			this.name = e.name
		},
		methods: {
			chooseMood(item) {
				let index = this.mood.indexOf(item);
				if (index !== -1) {
					console.log("重复");
					this.mood.splice(index, 1);
					this.moodIndex = this.mood.length;
					console.log(this.moodIndex);
					item.fontColor = '#09181F';
					item.viewColor = 'white';
					if (this.mood.length === 0) {
						this.buttonColor = "#F0F0F0";
						this.fontColor = "#787878";
					}
					return;
				}

				if (this.mood.length === 2) {
					// 去掉第一个进入队列的情绪
					this.mood[this.moodIndex].fontColor = '#09181F';
					this.mood[this.moodIndex].viewColor = "white";
				}
				this.$set(this.mood, this.moodIndex, item); // 强制更新渲染
				this.moodIndex++;
				this.moodIndex %= 2;
				this.moodIcon = item.icon;
				item.fontColor = "white";
				item.viewColor = "#09181F";
				this.buttonColor = "#09181F";
				this.fontColor = "white";
			},
			chooserAct() {
				console.log("选择心情");
				if (this.mood.length === 0) {
					// #ifdef APP-PLUS
					plus.nativeUI.toast("您还没有记录心情哦");
					// #endif
					console.log("您还没有记录心情哦");
				} else {
					this.diary.setMood(this.mood);
					storageWritingDiary(this.diary);
					uni.navigateTo({
						url: "../chooseActivity/chooseActivity?name=" + this.name
					})
					console.log("跳转");
				}
			},
			getItem(i, j) {
				return this.moodList[i - 1][j - 1];
			}
		}
	}
</script>

<style>
	* {
		//overflow: hidden;
	}

	.tips {
		/* border: #007AFF solid; */
		padding-left: 60rpx;
		padding-top: 50rpx;
		padding-bottom: 30rpx;
	}

	.tips view {
		margin-top: 20rpx;
		font-style: oblique;
		/* font-weight: bold; */
		font-size: 30rpx;
	}

	.moodPic {
		display: flex;
		flex-direction: row;
		justify-content: center;
		margin: 80rpx 0;
	}

	.moodPic image {
		height: 300rpx;
		border-radius: 5px;
	}

	.mood {}

	.swiper {
		height: 260rpx;
	}

	.moodItem {
		margin: 35rpx 60rpx;
		display: flex;
		flex-direction: row;
		justify-content: space-between
	}

	.moodItem view {
		font-size: 28rpx;
		text-align: center;
		width: 170rpx;
		height: 70rpx;
		line-height: 62rpx;
		border-radius: 10rpx;
		box-shadow: -1rpx 1rpx 11rpx -1rpx rgba(9, 24, 31, 0.47);
	}
</style>
