<template>
	<view>
		<view class="tips">
			<view id="greet">{{greet}}好 {{name}}</view>
			<view style="font-weight: bold;">今天的你愿意分享什么故事呢</view>
			<view style="display: flex; flex-direction: row; justify-content: flex-end; width: 100%; ">
				<view style="margin: 0 0; font-weight: initial; ">“今天……”</view>
				<view style="text-decoration: underline; font-weight: initial; margin: 0 50rpx;"> {{today}}</view>
			</view>
			<view>想知道那时天气如何</view>
			<view class="slogan">
				<text>"{{slogan}}"</text>
			</view>
		</view>

		<view class="weather">
			<view v-for="(index) in 2" :key="index" class="weatherView">
				<view v-for="item in wL(index)" :key="item.id" @tap="chooseWeather(item)"
					:style="{'background-color': item.color, 'box-shadow' : item.shadow}">
					<image :src="baseUrl+item.icon+`.png`" mode="heightFix"></image>
				</view>
			</view>
		</view>
		<button @tap="chooserMood()"
			:style="{'width': 'fit-content', 'background-color': buttonColor, 'color': fontColor, 'border-radius': '100rpx', 'padding': '10rpx 60rpx', 'font-size': '35rpx'}">我想说这天的事</button>
	</view>
</template>

<script>
	import {storageWritingDiary} from '../../../common/util/util.js'
	import {
		Diary
	} from '../../../common/Diary.js'
	export default {
		data() {
			return {
				baseUrl: '../../../static/weather/',
				name: '',
				greet: '',
				today: '',
				slogan: '……',
				chose: null,
				buttonColor: '#F0F0F0',
				fontColor: '#787878',
				diary: new Diary(),
				weatherList: [{
					id: 1,
					shadow: '-1rpx 1rpx 11rpx -1rpx rgba(9, 24, 31, 0.47)',
					color: 'initial',
					icon: 'sunny',
					weather: '晴天',
					slogan: '阳光比心情更明媚'
				}, {
					id: 2,
					shadow: '-1rpx 1rpx 11rpx -1rpx rgba(9, 24, 31, 0.47)',
					color: 'initial',
					icon: 'cloudy',
					weather: '多云',
					slogan: '云如心事一般堆叠'
				}, {
					id: 3,
					shadow: '-1rpx 1rpx 11rpx -1rpx rgba(9, 24, 31, 0.47)',
					color: 'initial',
					icon: 'windy',
					weather: '有风',
					slogan: '风带来了远方的故事'
				}, {
					id: 4,
					shadow: '-1rpx 1rpx 11rpx -1rpx rgba(9, 24, 31, 0.47)',
					color: 'initial',
					icon: 'rainy',
					weather: '下雨',
					slogan: '雨滴落在心房'
				}, {
					id: 5,
					shadow: '-1rpx 1rpx 11rpx -1rpx rgba(9, 24, 31, 0.47)',
					color: 'initial',
					icon: 'sonwy',
					weather: '下雪',
					slogan: '雪诠释纯净的心'
				}, {
					id: 6,
					shadow: '-1rpx 1rpx 11rpx -1rpx rgba(9, 24, 31, 0.47)',
					color: 'initial',
					icon: 'star',
					weather: '繁星',
					slogan: '月夜星河落，星辰落深海'
				}]
			}
		},
		methods: {
			wL(index) {
				return this.weatherList.filter(function(obj) {
					return obj.id >= (index - 1) * 3 + 1 && obj.id <= index * 3;

				})
			},
			chooseWeather(item) {
				this.chose = item;
				this.buttonColor = "#09181F";
				this.fontColor = "white";
				let flag = 0;
				for (let i in this.weatherList) {
					// console.log(this.$refs[`${item.index}`]);
					let obj = this.weatherList[i];
					// console.log(this.$refs[`${item.index}`][0].src)
					if (obj === item) {
						if (obj.icon.indexOf("HL") === -1) {
							obj.color = "#09181F";
							this.slogan = item.slogan;
							obj.icon += "HL";
							// obj.shadow = 'unset';
							flag++;
							if (flag === 2) {
								break;
							}
						}
					} else if (obj.icon.indexOf("HL") !== -1) {
						obj.icon = obj.icon.replace("HL", "");
						obj.color = "initial";
						// obj.shadow = '-1rpx 1rpx 11rpx -1rpx rgba(9, 24, 31, 0.47)';
						flag++;
						if (flag === 2) {
							break;
						}
					}
				}
			},
			chooserMood() {
				console.log(this.chose);
				console.log("选择心情");
				if (this.chose === null) {
					// #ifdef APP-PLUS
					plus.nativeUI.toast("您还没有记录天气哦");
					// #endif
					console.log("您还没有记录天气哦");
				} else {
					this.diary.setWeather(this.chose);
					storageWritingDiary(this.diary);
					uni.navigateTo({
						url: "../chooseMood/chooseMood?name=" + this.name
					})
				}
			}
		},
		computed: {

		},
		onLoad(e) {
			this.name = e.name;
			this.greet = e.greet;
			this.today = e.today;
			this.diary.isCompete = false;
		}
	}
</script>

<style>
	* {
		/* overflow: hidden; */
	}

	.tips {
		/* border: #007AFF solid; */
		box-sizing: border-box; // 解决width: 100% 下padding溢出问题
		padding-top: 50rpx;
		padding-left: 70rpx;
		width: 100%;
	}

	.tips view {
		box-sizing: border-box;
		/* border: red solid; */
		margin: 20rpx 0;
		/* font-weight: bold; */
		font-style: oblique;
		width: fit-content;
		font-size: 30rpx;
	}

	.weather {
		padding-top: 70rpx;
		padding-bottom: 150rpx;
		/* border: #007AFF solid; */
	}

	.weatherView {
		justify-content: space-between;
		margin: 30rpx 50rpx;
		/* border: #007AFF solid; */
		display: flex;
		flex-direction: row;
	}

	.weatherView image {
		/* flex: 1; */
		/* border: #007AFF solid; */
		border-radius: 15rpx;
		height: 90rpx;
		padding: 0;
	}

	.weatherView view {
		display: flex;
		justify-content: center;
		align-items: center;
		height: 150rpx;
		width: 150rpx;
		border-radius: 15rpx;
		/* border: #007AFF solid; */
		margin: 0 20rpx;
		padding: 0;
	}

	.tips .slogan {
		font-weight: initial;
		font-style: oblique;
		display: flex;
		flex-direction: row;
		align-items: flex-end;
		justify-content: flex-end;
		text-align: center;
		width: 100%;
		height: 100%;

	}

	.tips .slogan text {
		width: 70%;
	}
</style>
