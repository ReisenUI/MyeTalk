<template>
	<view>
		<uni-drawer ref="showLeft" mode="left" :width="450" @change="change($event,'showLeft')">
			<view class="leftList">
				<text style="font-weight: bold; font-size: 35rpx; text-align: start; padding-top: 200rpx;">
					{{name}}，{{judgeTime}}好</text>
				<text
					style="margin-top: 20rpx; margin-bottom: 100rpx; color: #808080; font-size: 25rpx;">{{today}}</text>
				<view id="leftItem" style="margin-top: 100rpx;" @tap="search()">
					<image src="../../static/homepage/search.png" mode=""></image>
					<text>搜索</text>
				</view>
				<view id="leftItem" @tap="showList()">
					<image src="../../static/homepage/menu.png" mode="aspectFit"></image>
					<text>列表</text>
				</view>
				<view id="leftItem" @tap="showCollection()">
					<image src="../../static/homepage/collection.png" mode="aspectFit"></image>
					<text>收藏</text>
				</view>
				<view id="leftItem" @tap="showDraft()">
					<image src="../../static/homepage/draft.png" mode="aspectFit"></image>
					<text>草稿</text>
				</view>
			</view>
		</uni-drawer>
		<view class="navigationBar">
			<image src="../../static/menu.png" mode="aspectFit" @tap="showDrawer('showLeft')"></image>
			<view style="display: flex; align-items: center;">
				<text style="font-size: 40rpx; text-align: center; font-weight: bold;"> {{date}} </text>
				<image src="../../static/homepage/down.png" mode="heightFix" @tap="showCalender()"
					style="height: 30rpx;"></image>
			</view>
			<image src="../../static/homepage/plus.png" mode="aspectFit" @tap="onclickNewDiary()"></image>
		</view>
		<swiper class="mainCss" previous-margin="90rpx" next-margin="90rpx" :current="current">
			<swiper-item v-for="item in diaryList.list" :key="item.id">
				<diary-card :isWrite=item.isCompete @onClick="onclickNewDiary()" :diary="item"
					@readDiary="readDiary(item)"></diary-card>
			</swiper-item>
		</swiper>
		<!-- <view class="contentBox" @tap="showPopupFrom('top')"></view> -->
		<hqs-popup title="popUpCalendar" :from="popFrom" :mask-click="maskClick" v-model="showPop">
			<ren-calendar ref="ren" :markDays="markDays" :headerBar="true" @onDayClick='onDayClick'></ren-calendar>
		</hqs-popup>

	</view>
</template>

<script>
	import {
		Diary
	} from '../../common/Diary.js'
	import {
		DiaryList
	} from '../../common/DiaryList.js'
	import {
		compareDate,
		storageWritingDiary,
		storageDiaryList,
		getDiaryList,
		getUrl,
		getGetHead,
		getDiaryListFromBack
	} from '../../common/util/util.js'
	export default {
		data() {
			return {
				showLeft: false,
				date: "",
				today: "",
				name: "ReisenU",
				greet: "早上",
				showPop: false,
				popFrom: 'top',
				maskClick: true,
				toggle: true,
				curDate: '',
				markDays: [],
				diaryList: new DiaryList(),
				current: 0
			}
		},
		onShow() {
			if(!uni.getStorageSync('isNeedRefresh'))
				return;
			this.diaryList = getDiaryList();
			var isTodayWrited = uni.getStorageSync('isTodayWrited');
			var today = new Date();
			if (isTodayWrited == '' || !compareDate(isTodayWrited.date, today) || !isTodayWrited
				.isTodayWrited) {
				isTodayWrited = {
					date: new Date(),
					isTodayWrited: false
				}
				let t = new Diary();
				t.cover =
					'https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3260214634,2926460972&fm=26&gp=0.jpg';
				this.diaryList.list.splice(0, 0, t);
				uni.setStorageSync('isTodayWrited', isTodayWrited);
			} else {
				let today = new Date();
			}
			uni.setStorageSync('isNeedRefresh', false);
		},
		onLoad() {
			uni.setStorageSync('isNeedRefresh', false);
			const _this = this;
			uni.request({
				url: 'https://zk123.top/mye/people/info',
				data: {},
				header: {
					Authorization: uni.getStorageSync('token')
				},
				method: 'GET',
				success: (res) => {
					_this.name = res.data.data.nickname;
				}
			});
			//this.name = uni.getStorageSync('nickname'); 
			// 从服务器获得日记列表
			uni.showLoading({
				mask: true,
				title: "请稍等"
			})
			uni.request({
				url: getUrl('/diary'),
				method: 'GET',
				header: getGetHead(),
				data: {
					pageNum: 1,
					pageSize: 100
				},
				success: res => {
					var list = getDiaryListFromBack(res.data.data.diaryList);
					// _this.diaryList = getDiaryList();
					_this.diaryList.list = list;
					for (let i in _this.diaryList.list) {
						let item = _this.diaryList.list[i];
						item.id = _this.diaryList.maxId++;
					}
					storageDiaryList(_this.diaryList);
					// 初始化

					var isTodayWrited = uni.getStorageSync('isTodayWrited');
					var today = new Date();
					if (isTodayWrited == '' || !compareDate(isTodayWrited.date, today) || !isTodayWrited
						.isTodayWrited) {
						isTodayWrited = {
							date: new Date(),
							isTodayWrited: false
						}
						let t = new Diary();
						t.cover =
							'https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3260214634,2926460972&fm=26&gp=0.jpg';
						_this.diaryList.list.splice(0, 0, t);
						uni.setStorageSync('isTodayWrited', isTodayWrited);
					} else {
						let today = new Date();
						/* if(!compareDate(isTodayWrited.date, today) || !isTodayWrited.isTodayWrited){
						} */
					}
					uni.hideLoading();
				},
				fail: () => {},
				complete: () => {}
			});
		},
		methods: {
			onclickNewDiary(e) {
				uni.navigateTo({
					url: '../writeDiary/chooseWeather/chooseWeather?name=' + this.name + "&greet=" + this.greet +
						"&today=" +
						this.today
				})
				console.log("New Dirary");
			},
			showCalender() {
				/* if(this.current >= this.diaryList.list.length - 1){
					return;
				}
				this.current++;
				return; */
				this.showPopupFrom('top');
			},
			showDrawer() {
				this.$refs.showLeft.open();
			},
			closeDrawer() {
				this.$refs.showLeft.close();
			},
			change(e, type) {
				this[type] = e;
			},
			search() {
				uni.navigateTo({
					url: '../mainLeft/search/search'
				})
			},
			showCollection() {
				uni.navigateTo({
					url: '../mainLeft/collection/collection'
				})
			},
			showDraft() {
				uni.navigateTo({
					url: '/pages/mainLeft/draft/draft'
				})
				console.log('草稿');
			},
			showList() {
				uni.navigateTo({
					url: '../mainLeft/list/list'
				})
				console.log('列表');
			},
			readDiary(item) {
				// TODO: 读日记界面
				if (!item.isCompete)
					return;
				storageWritingDiary(item);

				uni.navigateTo({
					url: '../readDiary/readDiary',
					success: res => {},
					fail: () => {},
					complete: () => {}
				});
			},
			// 日历
			toggleCalendar() {
				this.toggle = !this.toggle;
			},
			showPopupFrom(from) {
				this.popFrom = from;
				this.showPop = true;
			},
			onDayClick(data) {
				this.curDate = data.date;
			}
		},
		computed: {
			judgeTime() {
				let d = new Date();
				this.date = d.getFullYear() + "年" + (d.getMonth() + 1) + "月";
				this.today = d.getFullYear() + "年" + (d.getMonth() + 1) + "月" + d.getDate() + '日';
				let hour = d.getHours();
				if (hour >= 5 && hour < 10)
					this.greet = "早上";
				else if (hour >= 10 && hour < 13)
					this.greet = "中午";
				else if (hour >= 13 && hour < 18)
					this.greet = "下午";
				else if (hour >= 18 && hour < 24)
					this.greet = "晚上";
				else if (hour < 5)
					this.greet = "凌晨";
				return this.greet;
			}
		}
	}
</script>
<!-- 日历-接口-页面跳转-复杂页面 -->
<style>
	* {
		//border: #DD524D solid;
		/* background-color: #f9f9f9; */
		padding: 0;
		margin: 0;
	}



	uni-drawer {
		display: flex;
		flex-direction: row;
		justify-content: space-between;
		width: 100%;
		align-items: center;
		text-align: center;
	}

	uni-drawer,
	image {
		width: 50rpx;
		height: 50rpx;
		/* background-color: #ff55ff; */
		margin: 10rpx;
	}

	.leftList {
		padding-left: 50rpx;
		display: flex;
		flex-direction: column;
		/* justify-content: center; */
		align-items: flex-start;
		height: 100%;
	}

	.leftList #leftItem {
		display: flex;
		flex-direction: row;
		text-align: center;
		margin: 30rpx 0;
		justify-content: center;
		align-items: center;
	}

	.leftList #leftItem text {
		font-size: 30rpx;
		padding-left: 40rpx;
	}

	.navigationBar {
		position: absolute;
		z-index: 100;
		box-sizing: border-box; // 解决width: 100% 下padding溢出问题
		padding: 20rpx;
		background-color: #FFFFFF;
		display: flex;
		flex-direction: row;
		justify-content: space-between;
		width: 100%;
		align-items: center;
		text-align: center;
	}

	.navigationBar image {
		width: 50rpx;
		height: 50rpx;
		/* background-color: #4CD964; */
		margin: 10rpx;
	}

	.mainCss {
		padding-top: 100rpx;
		height: 80vh;
		display: flex;
		flex-direction: row;
		justify-content: center;
		align-items: center;
	}

	.mainCss swiper-item {
		display: flex;
		justify-content: center;
		align-items: center;
	}
</style>
