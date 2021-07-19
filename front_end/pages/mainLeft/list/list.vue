<template>
	<view style="padding-top: 50rpx;">
		<view class="border" v-for="(item, index) in order" :key="index">
			<view class="dot">
			</view>
			<view class="monthItem">
				{{item.month}}月
			</view>
			<view class="diaryList" v-for="diary in item.list">
				<main-left-list-item style="padding-top: 10rpx;" :isShowMonth='false' :itemListPadding='0'
					:dayFontSize='35' :diary='diary'>
				</main-left-list-item>
			</view>
		</view>
	</view>
</template>

<script>
	// TODO: 不能区分月
	import {
		Diary
	} from '../../../common/Diary.js';
	import {
		getDiaryList
	} from '../../../common/util/util.js'
	export default {
		data() {
			return {
				// TODO: 等待用户信息
				name: "empty",
				greet: "empty",
				diaryList: null,
				order: []
			}
		},
		methods: {
			onNavigationBarButtonTap() {
				this.onclickNewDiary()
			},
			onclickNewDiary(e) {
				uni.navigateTo({
					url: '../../writeDiary/chooseWeather/chooseWeather?name=' + this.name + "&greet=" + this
						.greet +
						"&today=" +
						this.today
				})
			},
			changeNavigationBarTitle() {
				let today = new Date();
				let title = today.getFullYear() + '年';
				uni.setNavigationBarTitle({
					title: title
				});
			},
			orderDiary() {
				for (let i = 1; i <= 12; i++) {
					this.order.push({
						month: i,
						list: []
					})
				}
				console.log(this.order);
				var _this = this;
				var today = new Date();
				this.diaryList.list.forEach(function(item, index) {
					if (item.date.year == today.getFullYear()) {
						_this.order[item.date.month - 1].list.push(item);
					}
				});
				for (let i = 0; i < this.order.length; i++) {
					let item = this.order[i];
					if (item.list.length == 0) {
						this.order.splice(i--, 1);
					}
				}
			}
		},
		computed: {

		},
		onLoad() {
			this.changeNavigationBarTitle();
			this.diaryList = getDiaryList();
			this.orderDiary();
			/* for (let i = 0; i < 10; i++) {
				this.diaryList.splice(-1, 0, new Diary());
			}
			this.diaryList[5].title = 'qwe'; */
		}
	}
</script>

<style>
	.monthItem {
		padding-left: 10rpx;
		padding-bottom: 10rpx;
		font-size: 35rpx;
		font-weight: bold;
		position: relative;
		top: -18rpx;
		/* border: #F0AD4E solid; */
	}

	.border {
		width: 90%;
		border-left: 4rpx solid #2fcfd9;
		/* margin-top: 50rpx; */
		margin-left: 10%;
		position: relative;

	}

	.dot {
		width: 10rpx;
		height: 10rpx;
		border-radius: 50%;
		background-color: #2fcfd9;
		position: absolute;
		left: -8rpx;
	}

	.diaryList {
		padding-left: 30rpx;
	}
</style>
