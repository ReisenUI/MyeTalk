<template>
	<view>
		<view class="tips">
			<view>那么 {{name}}</view>
			<view>这一天做了些什么呢</view>
		</view>
		<view>
			<swiper class="swiper" indicator-dots=true>
				<swiper-item v-for="i in 2" :key="i">
					<view class="activityItem" v-for="j in 3" :key="j">
						<view v-for="(item) in getItem(i,j)" :key=item.id
							:style="{'color': item.fontColor[item.type], 'background-color': item.backgroundColor[item.type]}"
							class="activityItemView" @tap="chooseActivity(item)">
							<image :src="baseUrl+item.icon[item.type]+`.png`" mode="heightFix"></image>
							<view>{{item.activity}}</view>
						</view>
					</view>
				</swiper-item>
			</swiper>
			<button @tap="writeDaily()"
				:style="{'width': 'fit-content', 'background-color': buttonColor, 'color': fontColor, 'border-radius': '100rpx', 'padding': '10rpx 60rpx', 'font-size': '35rpx', 'margin-top' : '100rpx', 'border': 'red solid 0rpx'}">也许做了这些事</button>
		</view>
	</view>
</template>

<script>
	import {
		Diary
	} from '../../../common/Diary.js'
	import {
		getWritingDiary,
		storageWritingDiary
	} from '../../../common/util/util.js'
	export default {
		data() {
			return {
				diary: new Diary(),
				baseUrl: '../../../static/activity/',
				name: '',
				lineIndex: 0,
				activities: [], // 存放已选择的活动  {"activity": "", "i": 0, "j": 0}
				slogan: '……',
				buttonColor: '#F0F0F0',
				fontColor: '#787878',
				activityList: [
					[
						[{
								"id": 0,
								"activity": "游戏",
								"icon": [
									"game",
									"gameHL"
								],
								"backgroundColor": [
									"white",
									"#09181F"
								],
								"fontColor": [
									"#808080",
									"white"
								],
								"type": 0
							},
							{
								"id": 1,
								"activity": "出差",
								"icon": [
									"business",
									"businessHL"
								],
								"backgroundColor": [
									"white",
									"#09181F"
								],
								"fontColor": [
									"#808080",
									"white"
								],
								"type": 0
							},
							{
								"id": 2,
								"activity": "旅游",
								"icon": [
									"travel",
									"travelHL"
								],
								"backgroundColor": [
									"white",
									"#09181F"
								],
								"fontColor": [
									"#808080",
									"white"
								],
								"type": 0
							}
						],
						[{
								"id": 3,
								"activity": "食物",
								"icon": [
									"food",
									"foodHL"
								],
								"backgroundColor": [
									"white",
									"#09181F"
								],
								"fontColor": [
									"#808080",
									"white"
								],
								"type": 0
							},
							{
								"id": 4,
								"activity": "购物",
								"icon": [
									"shopping",
									"shoppingHL"
								],
								"backgroundColor": [
									"white",
									"#09181F"
								],
								"fontColor": [
									"#808080",
									"white"
								],
								"type": 0
							},
							{
								"id": 5,
								"activity": "家庭",
								"icon": [
									"home",
									"homeHL"
								],
								"backgroundColor": [
									"white",
									"#09181F"
								],
								"fontColor": [
									"#808080",
									"white"
								],
								"type": 0
							}
						],
						[{
								"id": 6,
								"activity": "运动",
								"icon": [
									"run",
									"runHL"
								],
								"backgroundColor": [
									"white",
									"#09181F"
								],
								"fontColor": [
									"#808080",
									"white"
								],
								"type": 0
							},
							{
								"id": 7,
								"activity": "工作",
								"icon": [
									"work",
									"workHL"
								],
								"backgroundColor": [
									"white",
									"#09181F"
								],
								"fontColor": [
									"#808080",
									"white"
								],
								"type": 0
							},
							{
								"id": 8,
								"activity": "爱情",
								"icon": [
									"love",
									"loveHL"
								],
								"backgroundColor": [
									"white",
									"#09181F"
								],
								"fontColor": [
									"#808080",
									"white"
								],
								"type": 0
							}
						]
					],
					[
						[{
								"id": 9,
								"activity": "电影",
								"icon": [
									"movie",
									"movieHL"
								],
								"backgroundColor": [
									"white",
									"#09181F"
								],
								"fontColor": [
									"#808080",
									"white"
								],
								"type": 0
							},
							{
								"id": 10,
								"activity": "摸鱼",
								"icon": [
									"fish",
									"fishHL"
								],
								"backgroundColor": [
									"white",
									"#09181F"
								],
								"fontColor": [
									"#808080",
									"white"
								],
								"type": 0
							},
							{
								"id": 11,
								"activity": "休闲",
								"icon": [
									"coffee",
									"coffeeHL"
								],
								"backgroundColor": [
									"white",
									"#09181F"
								],
								"fontColor": [
									"#808080",
									"white"
								],
								"type": 0
							}
						],
						[{
								"id": 12,
								"activity": "阅读",
								"icon": [
									"read",
									"readHL"
								],
								"backgroundColor": [
									"white",
									"#09181F"
								],
								"fontColor": [
									"#808080",
									"white"
								],
								"type": 0
							},
							{
								"id": 13,
								"activity": "摄影",
								"icon": [
									"photo",
									"photoHL"
								],
								"backgroundColor": [
									"white",
									"#09181F"
								],
								"fontColor": [
									"#808080",
									"white"
								],
								"type": 0
							},
							{
								"id": 14,
								"activity": "研究",
								"icon": [
									"research",
									"researchHL"
								],
								"backgroundColor": [
									"white",
									"#09181F"
								],
								"fontColor": [
									"#808080",
									"white"
								],
								"type": 0
							}
						],
						[{
								"id": 15,
								"activity": "睡觉",
								"icon": [
									"sleep",
									"sleepHL"
								],
								"backgroundColor": [
									"white",
									"#09181F"
								],
								"fontColor": [
									"#808080",
									"white"
								],
								"type": 0
							},
							{
								"id": 16,
								"activity": "吃瓜",
								"icon": [
									"watermelon",
									"watermelonHL"
								],
								"backgroundColor": [
									"white",
									"#09181F"
								],
								"fontColor": [
									"#808080",
									"white"
								],
								"type": 0
							},
							{
								"id": 17,
								"activity": "其它",
								"icon": [
									"other",
									"otherHL"
								],
								"backgroundColor": [
									"white",
									"#09181F"
								],
								"fontColor": [
									"#808080",
									"white"
								],
								"type": 0
							}
						]
					]
				]
			}
		},
		methods: {
			getItem(i, j) {
				return this.activityList[i - 1][j - 1];
			},
			chooseActivity(item) {
				// 满了就替换，没满就插入
				let max = 5; // 每次可以选择的最大数量
				if (this.activities.indexOf(item) !== -1) {
					let temp = this.activities.indexOf(item);
					item.type = 0;
					this.activities.splice(temp, 1);
					if (this.lineIndex < temp)
						return;
					this.lineIndex--;
					if (this.lineIndex < 0) {
						this.lineIndex += max;
					}
					console.log("重复");

					return;
				}

				item.type = 1;
				if (this.activities.length == max) {
					this.activities[this.lineIndex].type = 0;
					this.activities.splice(this.lineIndex, 1, item) // 索引位置（可负），替换次数，替换对象
				} else {
					this.activities.splice(this.lineIndex, 0, item)
				}
				this.lineIndex++;
				this.lineIndex %= max;
				// 按钮变色 可优化
				this.buttonColor = "#09181F";
				this.fontColor = "white";
				// 可优化
			},
			writeDaily() {
				// console.log(this.activities);
				for (let i in this.activities) {
					console.log(this.activities[i].id);
				}

				if (this.activities.length === 0) {
					// #ifdef APP-PLUS
					plus.nativeUI.toast("您还没有记录活动哦");
					// #endif
					console.log("您还没有记录活动哦");
				} else {
					this.diary.setActivities(this.activities);
					console.log(this.diary);
					storageWritingDiary(this.diary);
					uni.navigateTo({
						url: '../writeDiary/writeDiary?name=' + this.name
					});
				}

			}
		},
		computed: {

		},
		onLoad(e) {
			this.diary = getWritingDiary();
		}
	}
</script>

<style>
	* {
		//overflow: hidden;
	}

	.tips {
		/* border: #007AFF solid; */
		box-sizing: border-box; // 解决width: 100% 下padding溢出问题
		padding-top: 50rpx;
		padding-left: 50rpx;
		width: 100%;
	}

	.tips view {
		box-sizing: border-box;
		/* border: red solid; */
		margin: 20rpx 0;
		font-style: oblique;
		width: fit-content;
		font-size: 30rpx;
	}

	.swiper {
		padding-top: 30rpx;
		height: 660rpx;
	}

	.activityItem {
		/* border: #007AFF solid; */
		margin: 30rpx 50rpx;
		display: flex;
		flex-direction: row;
		justify-content: space-between
	}


	.activityItem image {
		/* flex: 1; */
		/* border: #007AFF solid; */
		border-radius: 15rpx;
		height: 90rpx;
		padding: 0;
	}

	.activityItemView {
		display: flex;
		flex-direction: column;
		display: flex;
		box-shadow: -1rpx 1rpx 11rpx -1rpx rgba(9, 24, 31, 0.47);
		justify-content: center;
		align-items: center;
		height: 150rpx;
		width: 150rpx;
		border-radius: 15rpx;
		/* border: #007AFF solid; */
		margin: 0 20rpx;
		padding: 0;
	}
</style>
