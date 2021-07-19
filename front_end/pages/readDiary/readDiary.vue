<template>
	<view>
		<view class="image-box">
			<image :src="imgUrl" mode="aspectFill" style="width: 100%; height: 100%;"></image>
		</view>
		<view class="content">
			<textarea class="content_title" placeholder="起个标题叭..." :value="diary.title" disabled="true" />
			<view class="showItem">心情:{{diary.getMoodStr()}}</view>
			<view class="showItem">活动:{{diary.getActivityStr()}}</view>
			<movable-area :id="writeArea.writeAreaId"
				:style="{'border': 'red dashed ' + writeArea.writeAreaBorder + 'px', 'width': '100%', 'height': '1500rpx'}">
				<movable-view :style="{'width': item.width+'rpx', 'height': item.height+'rpx'}" :id="item.itemId"
					:x="item.posX" :y="item.posY" v-for="item in dragBoxList.list" :key="item.itemId">
					<textarea :id="item.itemId" class="textArea" :value="item.valueSync"
						:style="{ 'width': item.width + 'rpx', 'height': item.height+ 'rpx'}" maxlength="-1"
						disabled="true" />
				</movable-view>
			</movable-area>
		</view>
		<uni-popup type="bottom" ref="popUp">
			<view class="popUpBack">
				<view class="line1">
					<view class="viewItem" @click="editDiary()">
						<button style="background-image: url(../../static/readDiary/edit.png);"></button>
						<view>编辑图文</view>
					</view>
					<view class="viewItem" @click="editMood()">
						<button style="background-image: url(../../static/readDiary/weather.png);"></button>
						<view>编辑选项</view>
					</view>
					<view class="viewItem" @click="likeDiary()">
						<button
							:style="{'background-image': 'url(../../static/readDiary/' + getLikeIcon +'.png)'}"></button>
						<view>收藏日记</view>
					</view>
					<view class="viewItem" @click="deleteDiary()">
						<button style="background-image: url(../../static/readDiary/delete.png);"></button>
						<view>删除日记</view>
					</view>
				</view>
				<!-- <view style="width: 100%; text-align: center; font-size: 2rpx; margin-top: 10rpx; color: #787878;">分享至
				</view>
				<view class="line2">
					<view class="viewItem">
						<button style="background-image: url(../../static/readDiary/community.png);"></button>
						<view>圈子</view>
					</view>
					<view class="viewItem">
						<button style="background-image: url(../../static/readDiary/qq.png);"></button>
						<view>QQ</view>
					</view>
					<view class="viewItem">
						<button style="background-image: url(../../static/readDiary/wechat.png);"></button>
						<view>微信</view>
					</view>
					<view class="viewItem">
						<button style="background-image: url(../../static/readDiary/circle.png);"></button>
						<view>朋友圈</view>
					</view>
				</view> -->
			</view>
		</uni-popup>
		<uni-drawer ref="showRight" mode="right" :width="600" @change="change($event,'showRight')">
			<view class="screenTitle">按标签分类</view>
			<scroll-view scroll-y="true" style="height: 80vh;">
				<view class="screenTitle2">天气分类</view>
				<view class="screenItem" v-for="(itemRow, index) in weatherList" :key="'weather'+index">
					<view v-for="itemView in itemRow" @tap="onClickItem(itemView, 0)"
						:style="{'color': itemView.fontColor[itemView.type], 'background-color': itemView.backgroundColor[itemView.type]}"
						:key="itemView.name">
						{{itemView.name}}
					</view>
				</view>
				<view class="screenTitle2">心情分类</view>
				<view class="screenItem" v-for="(itemRow, index) in moodList" :key="'mood'+index">
					<view v-for="itemView in itemRow" @tap="onClickItem(itemView, 1)"
						:style="{'color': itemView.fontColor[itemView.type], 'background-color': itemView.backgroundColor[itemView.type]}"
						:key="itemView.name">
						{{itemView.name}}
					</view>
				</view>
				<view class="screenTitle2">活动分类</view>
				<view class="screenItem" v-for="(itemRow, index) in activityList" :key="'activity'+index">
					<view v-for="itemView in itemRow" @tap="onClickItem(itemView, 2)"
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
		Diary
	} from '../../common/Diary.js'
	import {
		Activity
	} from '../../common/Activity.js'
	import {
		Weather
	} from '../../common/Weather.js'
	import {
		Mood
	}
	from '../../common/Mood.js'
	import {
		getWritingDiary,
		storageWritingDiary,
		likeDiary,
		deleteDiary,
		refreshDiary
	} from '../../common/util/util.js'

	class WriteArea {
		constructor(id) {
			this.writeAreaId = "writeArea" + id;
			this.writeAreaBorder = 0; // px
		}
	}
	class DragBoxItem {
		constructor(id) {
			this.itemId = "dragBox" + id; // dragBoxList.list的长度-1
			this.posX = 197;
			this.posY = 158;
			this.setPosX = 100;
			this.setPosY = 100;
			this.width = 200; // rpx
			this.height = 200; // rpx
			this.valueSync = '';
			this.isShowBorder = 'false';
		}
	}
	export default {
		data() {
			return {
				diary: null,
				imgUrl: 'https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3260214634,2926460972&fm=26&gp=0.jpg',
				writeArea: new WriteArea(0),
				dragBoxList: {
					maxId: 0,
					list: []
				},
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
				select: {
					activity: {
						maxSize: 5,
						list: []
					},
					weather: {
						maxSize: 1,
						list: []
					},
					mood: {
						maxSize: 2,
						list: []
					}
				}
			}
		},
		methods: {
			showDrawer() {
				this.$refs.showRight.open();
			},
			closeDrawer() {
				this.$refs.showRight.close();
			},
			change(e, type) {
				this[type] = e;
			},
			showPopup() {
				this.$refs.popUp.open()
			},
			onNavigationBarButtonTap(e) {
				this.showPopup()
			},
			editDiary() {
				console.log(this.diary);
				storageWritingDiary(this.diary);
				/* uni.navigateTo({
					url: '../writeDiary/writeDiary/writeDiary',
					success: res => {},
					fail: () => {},
					complete: () => {}
				}); */
				uni.redirectTo({
					url: '../writeDiary/writeDiary/writeDiary',
					success() {
						
					}
				});
				console.log('编辑选项');
			},
			likeDiary() {
				likeDiary(this.diary);
			},
			deleteDiary() {
				var _this = this;
				uni.showModal({
					content: '是否确定删除日记？',
					showCancel: true,
					cancelText: '取消',
					confirmText: '删除',
					success: res => {
						if (res.confirm) {
							deleteDiary(_this.diary);
						} else if (res.cancel) {
							console.log('用户点击取消');
						}
					},
					fail: () => {},
					complete: () => {}
				});
			},
			editMood() {
				console.log('编辑选项');
				this.showDrawer();
				this.$refs.popUp.close();
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
						for (let i in this.diary.activities) {
							let item = this.diary.activities[i].activity;
							if (item == obj.name) {
								obj.type = 1;
								this.select.activity.list.push(obj);
								this.buttonType = 1;
								break;
							}
						}
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
						obj.backgroundColor[0] = '#F5F5F5';
						obj.backgroundColor[1] = '#25d3dc';
						obj.fontColor[0] = '#787878';
						obj.fontColor[1] = '#FFFFFF';
						obj.type = 0;
						if (obj.name == this.diary.weather.weather) {
							obj.type = 1;
							this.select.weather.list.push(obj);
							this.buttonType = 1;
						}
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
						obj.backgroundColor[0] = '#F5F5F5';
						obj.backgroundColor[1] = '#25d3dc';
						obj.fontColor[0] = '#787878';
						obj.fontColor[1] = '#FFFFFF';
						obj.type = 0;
						for (let i in this.diary.mood) {
							let item = this.diary.mood[i].mood;
							if (item == obj.name) {
								obj.type = 1;
								this.select.mood.list.push(obj);
								this.buttonType = 1;
								break;
							}
						}
						this.$set(this.moodList[j], i, obj);
					}
					j++;
				}
			},
			onClickItem(obj, chooseType) {
				obj.type = obj.type == 1 ? 0 : 1;
				var select;
				switch (chooseType) {
					case 0:
						select = this.select.weather;
						break;
					case 1:
						select = this.select.mood;
						break;
					case 2:
						select = this.select.activity;
						break;
				}

				let index = select.list.indexOf(obj);
				if (index == -1) {
					if (select.list.length >= select.maxSize) {
						select.list[0].type = 0;
						select.list.splice(0, 1);
					}
					select.list.push(obj);
					this.buttonType = 1;
				} else {
					select.list.splice(index, 1);
					if (this.select.activity.list.length == 0 &&
						this.select.mood.list.length == 0 &&
						this.select.weather.list.length == 0) {
						this.buttonType = 0;
					}
				}
			},
			onClickConfirm(){
				this.diary.setActivities(this.select.activity.list);
				this.diary.mood = []
				for (let i in this.select.mood.list) {
					let item = this.select.mood.list[i];
					let tempMood = {
						mood: item.name,
						icon: item.icon
					};
					this.diary.mood.push(tempMood);
				}
				this.diary.activities = [];
				for (let i in this.select.activity.list) {
					let item = this.select.activity.list[i];
					let tempActivity = {
						id: item.id,
						activity: item.name,
						icon: item.icon
					};
					this.diary.activities.push(tempActivity);
				}
				this.diary.weather.icon = this.select.weather.list[0].icon;
				this.diary.weather.weather = this.select.weather.list[0].name;
				refreshDiary(this.diary);
				this.setTitle();
				this.closeDrawer();
			},
			setTitle(){
				uni.setNavigationBarTitle({
					title: this.diary.date.year + '年' + this.diary.date.month + '月' + this.diary.date.day + '日 ' +this.diary.weather.weather
				});
			},
			onClickReset() {
				// 我现在需要干什么：已经把select清空、状态归零，需要选出diary初始的状态
				let list;
				list = this.select.activity.list;
				list.forEach(function(item) {
					item.type = 0;
				});
				this.select.activity.list = [];

				list = this.select.mood.list;
				list.forEach(function(item) {
					item.type = 0;
				});
				this.select.mood.list = [];

				list = this.select.weather.list;
				list.forEach(function(item) {
					item.type = 0;
				});
				this.select.weather.list = [];

				this.buttonType = 0;

				list = this.weatherList;
				for (let i in list) {
					for (let j in list[i]) {
						let obj = list[i][j];
						if (obj.name == this.diary.weather.weather) {
							obj.type = 1;
							this.select.weather.list.push(obj);
							this.buttonType = 1;
						}
					}
				}

				list = this.activityList;
				for (let i in list) {
					for (let j in list[i]) {
						let obj = list[i][j];
						for (let t in this.diary.activities) {
							let item = this.diary.activities[t].activity;
							if (item == obj.name) {
								obj.type = 1;
								this.select.activity.list.push(obj);
								this.buttonType = 1;
								break;
							}
						}
					}
				}
				
				list = this.moodList;
				for (let i in list) {
					for (let j in list[i]) {
						let obj = list[i][j];
						for (let t in this.diary.mood) {
							let item = this.diary.mood[t].mood;
							if (item == obj.name) {
								obj.type = 1;
								this.select.mood.list.push(obj);
								this.buttonType = 1;
								break;
							}
						}
					}
				}
				console.log('select:',this.select);
			}
		},
		computed: {
			getLikeIcon() {
				if (this.diary.isLike) {
					return 'like';
				} else {
					return 'unlike';
				}
			}
		},
		onLoad() {
			this.diary = getWritingDiary();
			this.dragBoxList = this.diary.content;
			this.imgUrl = this.diary.cover;
			this.setTitle();
			this.setItem();
		}
	}
</script>

<style lang="scss">
	.image-box {
		border-radius: 10rpx;
		height: 500rpx;
		width: 100%;
		margin: 10rpx;
		display: flex;
		align-items: center;
		box-sizing: border-box;
		justify-content: center;
	}

	.content {
		box-sizing: border-box;
		display: flex;
		padding: 20rpx 40rpx;
		flex-direction: column;

		.content_title {
			margin-bottom: 10rpx;
			box-sizing: border-box;
			width: 100%;
			height: 80rpx;
			line-height: 100rpx;
			font-size: 35rpx;
			border-bottom: 1px solid #ececec;
		}

		.content_info {
			width: 100%;
			font-size: 30rpx;
			line-height: 1.8;
			height: 700rpx;
		}
	}

	.showItem {
		margin-top: 5rpx;
		color: #787878;
		font-size: 20rpx;
	}

	.popUpBack {
		box-sizing: border-box;
		display: flex;
		flex-direction: column;
		border-radius: 20rpx;
		// padding: 20rpx;
		height: 250rpx;
		justify-content: center;
		background-color: #f0f0f0;

		.line1 {
			display: flex;
			height: 250rpx;
			flex: row;
			align-items: center;
			// border-bottom: #bfbfbf solid 1rpx;

		}

		.line2 {
			padding-top: 20rpx;
			display: flex;
			height: 270rpx;
			flex: row;
			align-items: flex-start;
		}

		.viewItem {
			display: flex;
			flex-direction: column;
			font-size: 20rpx;
			color: #787878;
			flex: 1;
			align-items: center;
			justify-content: center;
			width: 150rpx;
			// border: red solid;
		}

		button {
			height: 100rpx;
			width: 100rpx;
			border-radius: 20rpx;
			// background-image: url(../../static/readDiary/community.png);
			background-position: center;
			background-repeat: no-repeat;
			background-color: white;
			background-size: 70% 70%;
			margin-bottom: 10rpx;
		}
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
