<template>
	<view style="position: relative;">
		<!-- <uni-icons type="more" size="50" style="position: absolute; top: 20rpx; right: 30rpx;" @tap="more" v-if="false"></uni-icons> -->
		<view class="diaryCover" :style="'background-image: url(' + diary.cover + ')'" @tap="readDiary">
			<view class="blur"
				:style="[{'justify-content': isWrite ? 'flex-end': 'center', 'align-items': isWrite ? 'flex-end': 'center'}]">
				<button v-if="!isWrite" @tap="onclickNewDiary()" class="button">记录我的今天</button>
				<view v-else
					style="width: 100%; height: 100%; display: flex; flex-direction: row; justify-content: flex-end; align-items: flex-end;">
					<view style="flex: 1; height: 100%;">
						<view style="font-size: 20rpx; padding: 20rpx 40rpx;">{{_printDate}}</view>
						<view style="font-size: 30rpx; padding: 10rpx 40rpx; font-weight: bold;">{{_printTitle}}</view>
						<view style="font-size: 20rpx; padding: 20rpx 40rpx;">{{_printActivity}}</view>
					</view>
					<image :src="'../../static/moodIcon/cover/'+_showMood+'.png'"></image>
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	import {
		Diary
	} from '../../common/Diary.js'
	import {
		Mood
	} from '../../common/Mood.js'
	export default {
		name: "diary-card",
		props: {
			imageName: {
				type: String,
				default: 'https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3260214634,2926460972&fm=26&gp=0.jpg'
			},
			isWrite: {
				type: Boolean,
				default: false
			},
			diary: {
				type: Diary,
				default: function() {
					var a = new Diary();
					a.cover =
						'https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3260214634,2926460972&fm=26&gp=0.jpg'
					return a;
				}
			}
		},
		data() {
			return {
			};
		},
		methods: {
			onclickNewDiary() {
				this.$emit('onClick')
				//console.log('点击');
			},
			readDiary() {
				/* console.log('查看日记')
				return; */
				this.$emit('readDiary')
			},
			more() {
				// TODO: 菜单
				console.log(123);
			}
		},
		computed: {
			_printActivity() {
				return '活动：' + this.diary.activities[0].activity
			},
			_printDate() {
				return this.diary.getDate();
			},
			_printTitle() {
				let title = this.diary.title;
				let maxLength = 7;
				if (title.length <= maxLength) {
					return title;
				}
				let s = '';
				for (let i = 0; i < maxLength; i++) {
					s += title[i];
				}
				s += '...';
				return s;
			},
			_showMood() {
				let mood = this.diary.mood[0].mood;
				let positive = ["开心", "感动", "得意", "甜蜜", "惊喜"];
				let calm = ["吃瓜", "充实", "不知道"];
				let negative = ["委屈", "烦燥", "迷惘", "难过"];
				if (positive.indexOf(mood) != -1) {
					return "positive";
				} else if (calm.indexOf(mood) != -1) {
					return "calm";
				}
				return "negative";
			}
		}
	}
</script>

<style>
	.diaryCover {
		box-shadow: 0px 0px 5px 2px rgba(0, 0, 0, 0.10);
		background-size: cover;
		background-position: center center;
		background-repeat: no-repeat;
		display: flex;
		align-items: flex-end;
		justify-content: center;
		width: 500rpx;
		height: 800rpx;
		border-radius: 30rpx;
	}

	.blur {
		width: 100%;
		height: 200rpx;
		display: flex;
		flex-direction: row;
		border-radius: inherit;
		border-top-right-radius: 0;
		border-top-left-radius: 0;
		backdrop-filter: blur(100rpx);
		/*为一个元素后面区域添加高斯模糊*/
		/* background-color: white; */
	}

	.blur .button {
		background-color: #09181F;
		color: white;
		padding: 5rpx 50rpx;
		font-weight: bold;
		text-align: center;
		font-size: 27rpx;
		border-radius: 1000rpx;
		background-size: 100% 100%;
	}

	image {
		height: 180rpx;
		width: 180rpx;
		transform: rotate(-45deg);
	}
</style>
