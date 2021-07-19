<template>
	<view>
		<view class="myHead">
			<view style="font-size: 40rpx;">
				搜索历史
			</view>
			<uni-icons type="trash" color="#787878" size="40" @tap="clearHistory()" />
		</view>
		<view class="historyItem" v-for="(item, id) in historyList" :key="id">
			<view style="flex: 1;" @tap="search(item.msg)">
				{{item.msg}}
			</view>
			<uni-icons type="closeempty" color="#787878" size="30" style="margin-right: 10rpx;"
				@tap="deleteHistory(item)" />
		</view>
		<uni-drawer ref="showRight" mode="right" :width="600" @change="change($event,'showRight')">
			<view class="screenTitle">按标签筛选</view>
			<scroll-view scroll-y="true" style="height: 80vh;">
				<view class="screenTitle2">天气分类</view>
				<view class="screenItem" v-for="(itemRow, index) in weatherList" :key="'weather'+index">
					<view v-for="itemView in itemRow" @tap="onClickItem(itemView)"
						:style="{'color': itemView.fontColor[itemView.type], 'background-color': itemView.backgroundColor[itemView.type]}"
						:key="itemView.name">
						{{itemView.name}}
					</view>
				</view>
				<view class="screenTitle2">心情分类</view>
				<view class="screenItem" v-for="(itemRow, index) in moodList" :key="'mood'+index">
					<view v-for="itemView in itemRow" @tap="onClickItem(itemView)"
						:style="{'color': itemView.fontColor[itemView.type], 'background-color': itemView.backgroundColor[itemView.type]}"
						:key="itemView.name">
						{{itemView.name}}
					</view>
				</view>
				<view class="screenTitle2">活动分类</view>
				<view class="screenItem" v-for="(itemRow, index) in activityList" :key="'activity'+index">
					<view v-for="itemView in itemRow" @tap="onClickItem(itemView)"
						:style="{'color': itemView.fontColor[itemView.type], 'background-color': itemView.backgroundColor[itemView.type]}"
						:key="itemView.name">
						{{itemView.name}}
					</view>
				</view>
			</scroll-view>
			<view class="buttonView">
				<button class="reset" :style="{}" @tap="onClickReset()">重置</button>
				<button class="confirm"
					:style="{'color':buttonStyle.fontColor[buttonType], 'background-color':buttonStyle.backgroundColor[buttonType]}"
					@tap="onClickConfirm()">确认</button>
			</view>
		</uni-drawer>
	</view>
</template>

<script>
	import {
		Activity
	} from '../../../common/Activity.js'

	import {
		Weather
	} from '../../../common/Weather.js'

	import {
		Mood
	}
	from '../../../common/Mood.js'
	import {
		getUrl,
		getGetHead
	} from '../../../common/util/util.js'
	export default {
		data() {
			return {
				historyList: [],
				historyId: 0,
				buttonType: 0,
				buttonStyle: {
					fontColor: ['#787878', '#F0F0F0'],
					backgroundColor: ['#F5F5F5', '#09181F']
				},
				activityList: [
					[{}, {}, {}]
				],
				weatherList: [
					[{}, {}, {}]
				],
				moodList: [
					[{}, {}, {}]
				],
				selectList: []
			}
		},
		methods: {
			deleteHistory(item) {
				let index = this.historyList.indexOf(item);
				this.historyList.splice(index, 1);
				this.storageHistory();
			},
			clearHistory() {
				console.log("清空历史");
				try {
					uni.removeStorageSync('historyList');
					this.historyList = [];
				} catch (e) {
					console.log(e);
				}
			},
			search(msg) {
				let length = this.historyList.length;
				for (let i in this.historyList) {
					// 如果有重复的则删除
					if (this.historyList[i].msg === msg) {
						this.historyList.splice(i, 1);
						break;
					}
				}
				// 在行首插入新记录
				this.historyList.splice(0, 0, {
					id: this.historyId++,
					msg: msg
				});
				uni.hideKeyboard()
				uni.navigateTo({
					url: "detail/detail?msg=" + msg
				});
				var _this = this;
				
			},
			storageHistory() {
				uni.setStorage({
					key: 'historyList',
					data: this.historyList
				});
				uni.setStorage({
					key: 'historyId',
					data: this.historyId
				});
			},
			showDrawer() {
				this.$refs.showRight.open();
			},
			closeDrawer() {
				this.$refs.showRight.close();
			},
			change(e, type) {
				this[type] = e;
			},
			setItem() {
				let i = 0;
				let j = 0;
				// 获得活动
				let activities = new Activity();
				while (i + j * 3 <= activities.activity.length) {
					this.$set(this.activityList, j, []);
					for (i = 0; i < 3; i++) {
						let obj = activities.activity[i + j * 3];
						obj.backgroundColor = [];
						obj.fontColor = [];
						obj.backgroundColor[0] = '#F5F5F5';
						obj.backgroundColor[1] = '#25d3dc';
						obj.fontColor[0] = '#787878';
						obj.fontColor[1] = '#FFFFFF';
						obj.type = 0;
						this.$set(this.activityList[j], i, obj);
					}
					j++;
				}
				// 获得天气
				let weather = new Weather();
				i = j = 0;
				while (i + j * 3 <= weather.weather.length) {
					this.$set(this.weatherList, j, []);
					for (i = 0; i < 3; i++) {
						let obj = weather.weather[i + j * 3];
						obj.backgroundColor = [];
						obj.fontColor = [];
						obj.type = 0;
						obj.backgroundColor[0] = '#F5F5F5';
						obj.backgroundColor[1] = '#25d3dc';
						obj.fontColor[0] = '#787878';
						obj.fontColor[1] = '#FFFFFF';
						this.$set(this.weatherList[j], i, obj);
					}
					j++;
				}
				// 获得心情
				let mood = new Mood();
				i = j = 0;
				while (i + j * 3 <= mood.mood.length) {
					this.$set(this.moodList, j, []);
					for (i = 0; i < 3; i++) {
						let obj = mood.mood[i + j * 3];
						obj.backgroundColor = [];
						obj.fontColor = [];
						obj.type = 0;
						obj.backgroundColor[0] = '#F5F5F5';
						obj.backgroundColor[1] = '#25d3dc';
						obj.fontColor[0] = '#787878';
						obj.fontColor[1] = '#FFFFFF';
						this.$set(this.moodList[j], i, obj);
					}
					j++;
				}
				console.log(mood);
			},
			onClickItem(obj) {
				obj.type = obj.type == 1 ? 0 : 1;
				let index = this.selectList.indexOf(obj);
				if (index == -1) {
					this.selectList.splice(-1, 0, obj)
				} else {
					this.selectList.splice(index, 1)
				}
				if (this.selectList.length == 0) {
					this.buttonType = 0;
				} else {
					this.buttonType = 1;
				}
				/* console.log("select:");
				for(let t in this.selectList){
					console.log(this.selectList[t].name) 
				} */
			},
			onClickReset() {
				while (this.selectList.length != 0) {
					this.selectList[this.selectList.length - 1].type = 0;
					this.selectList.splice(-1, 1);
				}
				this.buttonType = 0;
			},
			onClickConfirm() {
				// TODO: 确认
				console.log("标签搜索");
				console.log(this.selectList);
				var str = '';
				for (let i in this.selectList) {
					let item = this.selectList[i];
					str += item.name;
					if(i < this.selectList.length - 1){
						str += '-';
					}
				}
				this.search(str);
			}
		},
		/**
		 *  点击导航栏 buttons 时触发
		 */
		onNavigationBarButtonTap() {
			uni.hideKeyboard();
			this.showDrawer()
		},
		/**
		 * 当 searchInput 输入时触发
		 */
		onNavigationBarSearchInputChanged(e) {
			let text = e.text;
			// console.log(e, text.length, text[text.length - 1]);
			if (text.length >= 10) {
				// TODO: 最大长度限制
			}
		},
		/**
		 * 点击软键盘搜索按键触发
		 */
		onNavigationBarSearchInputConfirmed(e) {
			let text = e.text;
			if (!text) {
				// #ifdef APP-PLUS
				plus.nativeUI.toast("请输入搜索内容");
				// #endif

				// #ifndef APP-PLUS
				uni.showToast({
					title: '请输入搜索内容',
					icon: 'none'
				});
				// #endif

				return;
			} else {
				this.search(text);
				this.storageHistory();

			}
		},
		onLoad() {
			// 获取历史记录
			this.historyList = uni.getStorageSync('historyList');
			if (this.historyList.length === 0) {
				console.log("空历史");
				this.historyList = [];
				this.historyId = 0;
			} else {
				this.historyId = uni.getStorageSync('historyId');
			}
			console.log("历史：", this.historyList, this.historyId);
			this.setItem()
		}
	}
</script>

<style>
	.myHead {
		padding: 0 40rpx;
		padding-top: 30rpx;
		justify-content: space-between;
		align-items: center;
		display: flex;
		flex-direction: row;
	}

	.historyItem {
		font-size: 30rpx;
		display: flex;
		flex-direction: row;
		justify-content: space-between;
		align-items: center;
		margin: 0 40rpx;
		margin-top: 20rpx;
		padding-bottom: 20rpx;
		border-bottom: #a6a6a6 solid 1rpx;
	}

	.screenTitle {
		/* border: #007AFF solid; */
		padding: 70rpx 0 30rpx 30rpx;
	}

	.screenTitle2 {
		font-size: 23rpx;
		color: #787878;
		padding: 40rpx 0 10rpx 30rpx;
	}

	.screenItem {
		/* #25d3dc */
		padding: 10rpx;
		/* border: #007AFF solid; */
		display: flex;
		flex-direction: row;
		text-align: center;
	}

	.screenItem view {
		border-radius: 100rpx;
		flex: 1;
		font-size: 20rpx;
		background-color: #F5F5F5;
		color: #787878;
		margin: 5rpx 10rpx 5rpx 10rpx;
		padding-top: 10rpx;
		padding-bottom: 10rpx;
	}

	.buttonView {
		padding-top: 20rpx;
		display: flex;
		flex-direction: row;
	}

	.buttonView button {
		flex: 1;
		border-radius: 100rpx;
		font-size: 30rpx;
	}

	.reset {
		margin-left: 30rpx;
		margin-right: 30rpx;
		color: #787878;
		border: 2rpx #787878 solid;
		background-color: white;
	}

	.confirm {
		margin-right: 20rpx;
	}
</style>
