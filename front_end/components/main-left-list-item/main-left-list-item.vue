<template>
	<view class="itemList" :style="{'padding': itemListPadding+'rpx 0', 'padding-left': itemListPadding+'rpx'}"
		@tap="showDiary()">
		<view id="diaryData">
			<!-- <view style="font-size: 40rpx; color: #2fcfd9;"> -->
			<view :style="{'font-size': dayFontSize+'rpx', 'color': '#2fcfd9'}">
				{{_printDay(diary.date.day)}}
			</view>
			<view style="font-size: 20rpx; color: #787878;" v-if="isShowMonth">
				{{diary.date.month}}月
			</view>
		</view>
		<view id="diaryContentTitle">
			<view style="color: #09181F; font-weight: bold;">
				{{diary.title}}
			</view>
			<view v-if="isShowContent" style="color: #949494;">
				{{_showContent(diary.content.list)}}
			</view>
			<view v-if='isShowGrey' style="display: flex; flex-direction: row;">
				<view
					style="background-color: #e2e2e2; border-radius: 100rpx; text-align: center; font-size: 20rpx; width: 80rpx; margin-right: 10rpx;">
					{{diary.weather.weather}}
				</view>
				<view
					style="background-color: #e2e2e2; border-radius: 100rpx; text-align: center; font-size: 20rpx; width: 80rpx; margin-left: 10rpx; margin-right: 10rpx;">
					{{diary.activities[0].activity}}
				</view>
				<view
					style="background-color: #e2e2e2; border-radius: 100rpx; text-align: center; font-size: 20rpx; width: 80rpx; margin-left: 10rpx;">
					{{diary.mood[0].mood}}
				</view>
			</view>

		</view>
		<view class="itemImg">
			<!-- <image :src="'/static/moodIcon/'+ icon + '.png'" mode="heightFix"></image> -->
			<image :src="diary.getMoodIcon(diary.mood[0].icon)" mode="heightFix"></image>
		</view>
	</view>
</template>

<script>
	import {
		Diary
	} from '@/common/Diary.js'
	import {
		storageWritingDiary
	} from '@/common/util/util.js'
	export default {
		name: "main-left-list-item",
		props: {
			isShowContent: {
				type: Boolean,
				default: true
			},
			isShowGrey: { // 是否显示活动、天气
				type: Boolean,
				default: true
			},
			isShowMonth: {
				type: Boolean,
				default: true
			},
			dayFontSize: {
				type: Number,
				default: 40
			},
			icon: {
				type: String,
				default: 'confused'
			},
			itemListPadding: {
				type: Number,
				default: 40
			},
			diary: {
				type: Diary,
				default: function() {
					return new Diary()
				}
			}
		},
		data() {
			return {};
		},
		methods: {
			_showContent(list) {
				if (list == undefined || list.length <= 0)
					return "没有正文"; // 没有内容怎么办
				var content = list[0].valueSync;
				let maxLength = 10;
				if (content.length <= maxLength) {
					return content;
				}
				let s = '';
				for (let i = 0; i < maxLength; i++) {
					s += content[i];
				}
				s += '...';
				return s;
			},
			_printDay(day) {
				let s = '';
				if (day < 10)
					s += '0';
				s += day;
				return s;
			},
			showDiary() {
				storageWritingDiary(this.diary);
				if (this.diary.isCompete) {
					uni.navigateTo({
						url: '/pages/readDiary/readDiary',
						success: res => {},
						fail: () => {},
						complete: () => {}
					});
					console.log('打开日记');
				} else {
					uni.navigateTo({
						url: '/pages/writeDiary/writeDiary/writeDiary',
						success: res => {},
						fail: () => {},
						complete: () => {}
					});
					console.log('打开草稿');
				}				
			}
		}
	}
</script>

<style>
	.itemList {
		display: flex;
		flex-direction: row;
	}

	.itemList #diaryData {
		padding-right: 30rpx;
		display: flex;
		flex-direction: column;
		justify-content: start;
		align-items: center;
	}

	.itemList #diaryContentTitle view {
		padding: 5rpx 0;
	}

	.itemImg {
		flex: 1;
		display: flex;
		flex-direction: row-reverse;
		align-items: center;
		padding-right: 80rpx;
		width: 100%;
	}

	.itemImg image {
		height: 70rpx;
	}
</style>
